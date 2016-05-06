from flask import render_template, g, url_for, redirect, flash, request
from flask.ext.login import login_user, logout_user, current_user, login_required

from app import app, bcrypt, db
from app.forms import SignupForm, LoginForm, ApplicationForm
from app.models import User


@app.route('/')
@app.route('/index')
#@login_required
def index():
    print(g.user)
    if g.user is None:
        return redirect(url_for('admin/login'))

    return render_template('index.html')


@app.route('/home')
def home():

    return render_template('home.html')


@app.route('/application', methods = ['GET', 'POST'])
def application():

    form = ApplicationForm()
    if request.method == 'POST' and form.validate_on_submit():
        return redirect('/home')

    return render_template('application.html', form=form)


@app.route('/signup', methods=['GET', 'POST'])
def signup():
    print(g.user)
    # if g.user is not None and g.user.is_authenticated:
    #     return redirect(url_for('index'))
    form = SignupForm()
    if request.method == 'POST' and form.validate_on_submit():

        print(form.username.data, form.password.data, form.confirmation.data)
        password_hash = bcrypt.generate_password_hash(form.password.data)
        print (password_hash)
        new_user = User(form.username.data, password_hash)
        db.session.add(new_user)
        db.session.commit()
        return redirect('/home')

    elif request.method == 'POST':
        flash('Passwords must match')

    return render_template('signup.html', form=form)


@app.route('/login_user',methods=['GET', 'POST'])
def login():
    print(g.user)
    # if g.user is not None and g.user.is_authenticated:
    #     return redirect(url_for('index'))
    form = LoginForm()
    if request.method == 'POST' and form.validate_on_submit():

        print(form.username.data, form.password.data)
        return redirect('/home')

    return render_template('login.html', form=form)


@app.before_request
def before_request():
    g.user = current_user
