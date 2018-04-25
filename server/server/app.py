from flask import Flask
app = Flask(__name__)
from flask import json
from flask import Response
from flask import request
from flask import jsonify
import serial
import pyowm
from decimal import Decimal

port = "/dev/cu.usbmodemFD131"

ard = serial.Serial(port,9600,timeout=5)

apikey = "444e0032067ad19fa3be458acee9f17f"
owm = pyowm.OWM(apikey)

@app.route('/message/<msg>',methods = ['GET'])
def send_msg(msg):
  if request.method == 'GET':
    ard.write(msg.encode())
    return jsonify(
      success = True
    )

@app.route('/temp/<string:lo>/<string:la>',methods = ['GET'])
def get_temp(lo, la):
    if request.method == 'GET':
      obs = owm.weather_at_coords(float(la),float(lo))
      w = obs.get_weather()
      print "Temperatura:",w.get_temperature(unit='celsius')
      tempe = "Temp:"+str(w.get_temperature(unit='celsius'))
      ard.write(tempe.encode())
      return jsonify(
        success = True
      )

@app.route('/time_date/<string:lo>/<string:la>',methods = ['GET'])
def get_time_date(lo, la):
    if request.method == 'GET': 
      obs = owm.weather_at_coords(float(la),float(lo))
      w = obs.get_weather()
      print "Tiempo:",w.get_reference_time(timeformat='iso')
      time = "Fecha: "+ str(w.get_reference_time(timeformat='iso'))
      ard.write(time.encode())
      return "yes"

