from flask.ext.wtf import Form
from wtforms import StringField, DateField, BooleanField, TextAreaField, PasswordField, FileField, RadioField, validators
from wtforms.validators import DataRequired


class LoginForm(Form):
    username = StringField('username', validators=[DataRequired()])
    password = PasswordField('password', validators=[DataRequired()])


class SignupForm(Form):
    username = StringField('username', validators=[validators.Length(min=4, max=25)])
    email = StringField('e-mail', validators=[DataRequired()])
    password = PasswordField('password', validators=[validators.Length(min=8),
                                                     validators.EqualTo('confirmation', message='Passwords must match')])
    confirmation = PasswordField('Repeat Password')


class ApplicationForm(Form):
    first_name = StringField(validators=[DataRequired()])
    last_name = StringField(validators=[DataRequired()])
    birth_date = DateField(validators=[DataRequired()])
    email = StringField(validators=[DataRequired()])
    has_team = BooleanField(validators=[DataRequired()])
    team_name = StringField()
    from_rice = BooleanField(validators=[DataRequired()])
    school = StringField(validators=[DataRequired()])
    traveling_from = StringField(validators=[DataRequired()])
    travel_plan = StringField(validators=[DataRequired()])
    graduation = DateField(validators=[DataRequired()])
    gender = RadioField(validators=[DataRequired()], choices=[('woman', 'Woman'), ('man', 'Man'), ('other', 'Other')])
    links = TextAreaField(validators=[DataRequired()])
    is_first_hackathon = BooleanField(validators=[DataRequired()])
    resume = FileField(validators=[DataRequired()])