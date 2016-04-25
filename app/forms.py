from flask.ext.wtf import Form
from wtforms import StringField, DateField, BooleanField, TextAreaField, PasswordField, validators
from wtforms.validators import DataRequired

class LoginForm(Form):
    username = StringField('username', validators=[DataRequired()])
    password = PasswordField('password', validators=[DataRequired()])


class SignupForm(Form):
    username = StringField('username', validators=[validators.Length(min=4, max=25)])
    password = PasswordField('password', validators=[validators.Length(min=8, max=25),
                                                     validators.EqualTo('confirmation', message='Passwords must match')])
    confirmation = PasswordField('Repeat Password')

# class ApplicationForm(Form):
#     first_name = StringField('first_name', validators=[DataRequired()])
#     last_name = StringField('last_name', validators=[DataRequired()])
#     birth_date = DateField('birth_date', validators=[DataRequired()])
#     has_team = BooleanField('has_team',  validators=[DataRequired()])
#     email = StringField('email', validators=[DataRequired()])
#     from_rice = BooleanField('from_rice',  validators=[DataRequired()])
#     school = StringField('school', validators=[DataRequired()])
#     traveling_from = StringField('traveling_from', validators=[DataRequired()])
#     travel_plan = StringField('travel_plan', validators=[DataRequired()])
#     graduation = DateField('graduation', validators=[DataRequired()])
#     gender = StringField('gender', validators=[DataRequired()])
#     links = TextAreaField('links', validators=[DataRequired()])
#     is_first_hackathon = db.Column(db.Integer, index=True)
#     resume_hash = db.Column(db.String(256))