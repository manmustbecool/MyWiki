from flask import Flask
from flask import request
from flask import jsonify

import os

app = Flask(__name__)

@app.route("/")
def hello():
    html = "<h3>JAVA_HOME: {test}</h3>"
    test = os.environ['JAVA_HOME']
    return html.format(test=test)


from py4j.java_gateway import JavaGateway

gateway = JavaGateway()  # connect to the JVM
addPy = gateway.jvm.AddPy() # AddPy is a java class
result = addPy.addition(3, 5)
print("py4j test:", str(result))


@app.route('/addpy_api', methods=['GET'])
def add():

    try:
        x = int(request.args.get('x', ''))
        y = int(request.args.get('y', ''))
    except:
        return notify_error("ERR_INVALID_TYPE:", HTTP_ERROR_CLIENT)

    gateway = JavaGateway()  # connect to the JVM
    addPy = gateway.jvm.AddPy() # AddPy is a java class
    result = addPy.addition(x, y)

    try:
        return jsonify(x=x, y=y, result=result)
    except Exception as ex:
        print(ex)
        return notify_error(ex, HTTP_ERROR_SERVER)


if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0', port=81)


