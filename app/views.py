from flask import render_template, g, redirect, flash, request
from flask.ext.login import login_user, logout_user, current_user, login_required

from app import app, bcrypt, db, login_manager, cas
from app.forms import SignupForm, LoginForm, ApplicationForm
from app.models import User


@app.route('/')
@app.route('/index')
def index():

    return render_template('index.html')


@app.route('/home')
@login_required
def home():

    return render_template('home.html')


@app.route('/application', methods=['GET', 'POST'])
@login_required
def application():

    form = ApplicationForm()
    if request.method == 'POST' and form.validate_on_submit():
        return redirect('/home')

    return render_template('application.html', form=form)


@login_manager.user_loader
def load_user(id):
    return User.query.get(int(id))


@app.route('/signup', methods=['GET', 'POST'])
def signup():
    """On GET displays a signup page, on POST validates signup data and adds new user to db."""
    form = SignupForm()
    if request.method == 'POST' and form.validate_on_submit():

        user = User.query.filter_by(email=form.email.data).first()
        if user:
            flash('An account with the same e-mail address already exists.')
        else:
            password_hash = bcrypt.generate_password_hash(form.password.data)
            new_user = User(form.email.data, password_hash)
            new_user.authenticated = True
            new_user.is_cas_authenticated = False
            db.session.add(new_user)
            db.session.commit()
            login_user(new_user, remember=True)
            return redirect('/application')

    elif request.method == 'POST':

        if len(form.password.data) < 8:
            flash('Your password has to be at least 8 characters in length.\n')
        if form.password.data != form.confirmation.data:
            flash('Passwords must match.\n')

    return render_template('signup.html', form=form)


@app.route('/login_user', methods=['GET', 'POST'])
def login():
    """On GET displays a login form, on POST validates login data and logs in the user."""
    if current_user and current_user.is_authenticated():
        return redirect('/application')

    form = LoginForm()
    if request.method == 'POST' and form.validate_on_submit():
        user = User.query.filter_by(email=form.email.data).first()

        if user and user.is_cas_authenticated:
            flash('To log in using Rice netid, use the second login button on the home page.')
        elif user and bcrypt.check_password_hash(user.password_hash, form.password.data):
            user.authenticated = True
            db.session.add(user)
            db.session.commit()
            login_user(user, remember=True)
            return redirect('/application')
        else:
            flash('Incorrect e-mail or password.')

    return render_template('login.html', form=form)


@app.route('/logout_user', methods=['GET'])
@login_required
def logout():
    """ Logs out current user and disables her/his authentication flag."""
    user = current_user
    user.authenticated = False
    db.session.add(user)
    db.session.commit()
    logout_user()
    return redirect('/')


@app.route('/cas_after_login')
def cas_after_login():
    # If CAS returned netid, check if corresponding rice email is in database
    if cas.username:
        rice_email = cas.username + "@rice.edu"
        user = User.query.filter_by(email=rice_email).first()

        # Create new rice user
        if not user:
            user = User(rice_email, "No password hash")
            user.is_cas_authenticated = True

        user.authenticated = True
        db.session.add(user)
        db.session.commit()
        login_user(user, remember=True)
        return redirect('/application')
    else:
        return # TODO: ERROR


@app.before_request
def before_request():
    g.user = current_user
