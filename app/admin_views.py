from flask import render_template, g, url_for, redirect, flash, request
from flask.ext.login import login_user, logout_user, current_user, login_required

from app import app
from app.authentication import requires_auth


@app.route('/admin/')
@app.route('/admin/index')
@requires_auth
def admin_index():
    print(g.user)
    if g.user is None:
        return redirect(url_for('admin/login'))

    return render_template('admin.html')


@app.route('/admin/applications')
@requires_auth
def applications():

    return render_template('applications.html')