from app import db


class User(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    email = db.Column(db.String(64), index=True, unique=True)
    password_hash = db.Column(db.String(60))
    authenticated = db.Column(db.Boolean, default=False)
    is_cas_authenticated = db.Column(db.Boolean)

    def __init__(self, email, password_hash):
        self.email = email
        self.password_hash = password_hash

    def is_authenticated(self):
        return self.authenticated

    @staticmethod
    def is_active():
        return True

    @staticmethod
    def is_anonymous():
        return False

    def get_id(self):
        try:
            return unicode(self.id)  # python 2
        except NameError:
            return str(self.id)  # python 3


class Application(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    first_name = db.Column(db.String(64), index=True)
    last_name = db.Column(db.String(64), index=True)
    birth_date = db.Column(db.String(64), index=True)
    has_team = db.Column(db.Integer, index=True)
    email = db.Column(db.String(120), index=True, unique=True)
    from_rice = db.Column(db.Integer, index=True)
    school = db.Column(db.String(64), index=True)
    traveling_from = db.Column(db.String(64), index=True)
    travel_plan = db.Column(db.String(128))
    graduation = db.Column(db.String(64), index=True)
    gender = db.Column(db.String(64), index=True)
    links = db.Column(db.String(256), index=True)
    is_first_hackathon = db.Column(db.Integer, index=True)
    resume_hash = db.Column(db.String(256))

    def __repr__(self):
        return '<User %r>' % self.first_name
