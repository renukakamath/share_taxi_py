from flask import Flask


from api import api
app=Flask (__name__)
app.secret_key="123456"

app.register_blueprint(api,url_prefix='/api')

app.run (debug=True,port=5674,host="0.0.0.0")