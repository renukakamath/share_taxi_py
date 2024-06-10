from flask import *
from database import *
from search import *
import demjson
import qrcode
import uuid
from math import sin, cos, sqrt, atan2, radians, ceil




api=Blueprint('api',__name__)


@api.route('/login')
def login():
	data={}
	uname=request.args['username']
	pwd=request.args['password']
	q="select * from login where username='%s' and password='%s'"%(uname,pwd)
	print(q)
	w=select(q)
	if w:
		data['status']="success"
		data['data']=w
	else:
		data['status']="failed"
	return demjson.encode(data)

@api.route('/userregister')	
def userregister():
	data={}
	fn=request.args['fname']
	ln=request.args['lname']
	e=request.args['email']
	ad=request.args['address']
	ph=request.args['phone'] 
	pl=request.args['place']
	uname=request.args['username']
	pwd=request.args['password']
	lati=request.args['lati']
	longi=request.args['longi']
	q="select * from login where username='%s'"%(uname)
	res=select(q)
	if res:
		data['status']="duplicate"
	else:

		q="insert into login values (null,'%s','%s','user')"%(uname,pwd)
		id=insert(q)
		q="insert into user values (null,'%s','%s','%s','%s','%s','%s','%s','%s','%s')"%(id,fn,ln,e,ad,ph,pl,longi,lati)
		insert(q)
		data['status']="success"
	return demjson.encode(data)


@api.route('/driver')	
def driver():
	data={}
	fn=request.args['fname']
	ln=request.args['lname']
	e=request.args['email']
	ad=request.args['address']
	ph=request.args['phone'] 
	pl=request.args['place']
	uname=request.args['username']
	pwd=request.args['password']
	lati=request.args['lati']
	longi=request.args['longi']
	q="select * from login where username='%s'"%(uname)
	res=select(q)
	if res:
		data['status']="duplicate"
	else:

		q="insert into login values (null,'%s','%s','driver')"%(uname,pwd)
		id=insert(q)
		q="insert into driver values (null,'%s','%s','%s','%s','%s','%s','%s','%s','%s')"%(id,fn,ln,e,ad,ph,pl,longi,lati)
		insert(q)
		data['status']="success"
	return demjson.encode(data)



@api.route('/chatdetail1')
def chatdetail1():
	data={}
	sender_id=request.args['sender_id']
	receiver_id=request.args['receiver_id']

	q="SELECT * FROM `chat` INNER JOIN login ON `login`.`Login_id`=`chat`.`sender_id` where `sender_id`='%s' AND `receiver_id`='%s'  or (`sender_id`='%s' AND `receiver_id`='%s' )" %(sender_id,receiver_id,receiver_id,sender_id)
	print(q)
	res=select(q)
	if res:
		data['status']='success'
		data['data']=res
	else:
		data['status']='failed'
	data['method']='chatdetail'
	return str(data)



@api.route('/chat1',methods=['get','post'])
def chat1():
	data={}
	
	sender_id=request.args['sender_id']
	receiver_id=request.args['receiver_id']
	details=request.args['details']

	

	q="insert into chat values(null,'%s','%s','%s',curdate())"%(sender_id,receiver_id,details)
	insert(q)


	data['status']='success'
	data['method']='chat'


	return str(data)


@api.route('/chatdetail')
def chatdetail():
	data={}
	sender_id=request.args['sender_id']
	receiver_id=request.args['receiver_id']

	q="SELECT * FROM `chat` INNER JOIN login ON `login`.`Login_id`=`chat`.`sender_id` where `sender_id`='%s' AND `receiver_id`='%s'  or (`sender_id`='%s' AND `receiver_id`='%s' )" %(sender_id,receiver_id,receiver_id,sender_id)
	print(q)
	res=select(q)
	if res:
		data['status']='success'
		data['data']=res
	else:
		data['status']='failed'
	data['method']='chatdetail'
	return str(data)



@api.route('/chat',methods=['get','post'])
def chat():
	data={}
	
	sender_id=request.args['sender_id']
	receiver_id=request.args['receiver_id']
	details=request.args['details']

	

	q="insert into chat values(null,'%s','%s','%s',curdate())"%(sender_id,receiver_id,details)
	insert(q)


	data['status']='success'
	data['method']='chat'


	return str(data)


@api.route('/addplace')
def addplace():
	data={}
	log=request.args['login_id']
	
	Activity=request.args['Activity']
	# lati=request.args['lati']
	# longi=request.args['longi']
	cid=request.args['cid']
	

	

	q="insert into place values(null,'%s','0','0','%s')"%(Activity,cid)
	insert(q)
	
	data['status']="success"
	data['method']="addplace"
	return demjson.encode(data)



@api.route('/Viewplace')
def Viewplace():
	data={}
	cid=request.args['cid']
	q="select * from place  where car_id='%s'"%(cid)
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	data['method']="Viewplace"
	return demjson.encode(data)







@api.route('/addcar')
def addcar():
	data={}
	log=request.args['login_id']
	
	Activity=request.args['Activity']
	details=request.args['details']
	regs=request.args['regs']
	splaces=request.args['splaces']
	eplaces=request.args['eplaces']
	q="select * from car where driver_id=(select driver_id from driver where login_id='%s')"%(log)
	res=select(q)
	if res:

		data['status']="failed"

	else:
		
	

		q="insert into car values(null,'%s','%s','Available',(select driver_id from driver where login_id='%s'),'%s','%s','%s')"%(Activity,details,log,splaces,eplaces,regs)
		insert(q)
		
		data['status']="success"
	data['method']="addcar"
	return demjson.encode(data)



@api.route('/Viewcar')
def Viewcar():
	data={}
	log=request.args['login_id']
	q="select * from car  where driver_id=(select driver_id from driver where login_id='%s')"%(log)
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	data['method']="Viewcar"
	return demjson.encode(data)



@api.route('/Makepayment')
def Makepayment():
	data={}
	
	amt=request.args['amt']
	# fromplace=request.args['fromplace']
	# toplace=request.args['toplace']
	rid=request.args['rid']

	# q="insert into transaction values(null,'%s','%s','%s','%s',curdate())"%(rid,amt,fromplace,toplace)
	# id=insert(q)

	q="insert into payment values(null,'%s','%s',now())"%(rid,amt)
	insert(q)
	# q="update booking set status='paid' where booking_id='%s'"%(bid)
	# update(q)
	data['status']="success"
	return demjson.encode(data)


@api.route('/userviewstaff')
def userviewstaff():
	data={}
	q="select *,`staffs`.`place` as splace from staffs inner join stations using(station_id)"
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	return demjson.encode(data)





@api.route('/usersendmessage')
def usersendmessage():
	data={}
	types=request.args['type']
	log=request.args['log']
	logid=request.args['logid']
	message=request.args['message']
	q="insert into message values(null,'%s','user','%s','%s','%s','pending',now(),'0')"%(log,logid,types,message)
	insert(q)
	data['status']="success"
	data['method']="usersendmessage"
	return demjson.encode(data)


@api.route('/userviewmessage')
def userviewmessage():
	data={}
	types=request.args['type']
	log=request.args['log']
	logid=request.args['logid']
	q="select * from message where sender_id='%s' and receiver_id='%s' and receiver_type='%s'"%(log,logid,types)
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	data['method']="userviewmessage"
	return demjson.encode(data)



# @api.route('/getstaffdetails')
# def getstaffdetails():
# 	data={}
# 	sid=request.args['contents']
# 	q="select * from request where request_id='%s'"%(sid)
# 	res=select(q)
# 	print(q)

	
# 	data['status']="success"
# 	data['method']="getstaffdetails"

# 	return demjson.encode(data)


@api.route('/getstaffdetails2')
def getstaffdetails2():
	data={}
	sid=request.args['contents']
	q="select * from staffs inner join login using (login_id) inner join stations using (station_id) where staff_id='%s'"%(sid)
	res=select(q)
	print(q)

	station_name=res[0]['station_name']
	# geolocator = Nominatim(user_agent="my-custom-user-agen")
	# place1 = geolocator.geocode(station_name)
	
	# latitude = (place1.latitude)

	# longitude = (place1.longitude)
	
	data['data']=station_name
	data['status']="success"
	data['method']="getstaffdetails"

	return demjson.encode(data)





@api.route('/AndroidBarcodeQrExample4')
def AndroidBarcodeQrExample4():
	data={}
	qty=request.args['qty']
	cid=request.args['cic']
	rid=request.args['rid']
	amts=request.args['amts']

	logid=request.args['logid']





	t="insert into payment values(null,(select user_id from user where login_id='%s'),'%s','%s',curdate())"%(logid,rid,amts)
	
	id=insert(t)
	

	q="update request set status='Paid' where request_id='%s'"%(rid)
	update(q)
	q="update car set seat=seat+'%s'  , status='Available'  where car_id='%s'"%(qty,cid)
	update(q)
	data['status']="success"
	data['method']="AndroidBarcodeQrExample"



	return str(data)






@api.route('/View_my_qr')
def View_my_qr():
	data={}
	rid=request.args['rid']
	q="SELECT * FROM `request`   WHERE `request_id`='%s'"%(rid)
	res=select(q)
	data['data']=res[0]['qrcode']
	data['status']="success"
	return str(data)



@api.route('/viewspinnertoplace')
def viewspinnertoplace():
	data={}
	
	q="SELECT * FROM `stations` "
	res=select(q)

	data['data']=res

	data['status']="success"

	data['method']="viewspinnertoplace"
	return str(data)


@api.route('/viewspinnerfromplace')
def viewspinnerfromplace():
	data={}
	
	q="SELECT * FROM `stations` "
	res=select(q)

	data['data']=res

	data['status']="success"
	data['method']="viewspinnerfromplace"
	return str(data)



@api.route('/Userviewrequestssss')
def Userviewrequestssss():
	data={}
	rid=request.args['login_id']
	q="SELECT * FROM `requestdetails` inner join car using (car_id) inner join driver using (driver_id) where user_id=(select user_id from user where login_id='%s')" %(rid)
	print(q)
	res=select(q)
	data['data']=res

	data['status']="success"
	data['method']="Userviewrequestssss"
	return str(data)



@api.route('/BookCycle')
def BookCycle():
	data={}
	lid=request.args['login_id']
	qty=request.args['quantity']
	tot=request.args['total']
	fp=request.args['fplace']
	tp=request.args['tplace']
	rid=request.args['rid']
	st=request.args['st']
	car_id=request.args['car_id']

	
	w="insert into `request` values(null,'%s','pending','%s','%s','%s','%s','0')"%(rid,tot,fp,tp,qty)
	insert(w)
	q="update car set status='Not Available' , seat=seat-'%s' where car_id='%s'"%(qty,car_id)
	update(q)



	data['status']='success'
	return str(data)



@api.route('/userviewrequest')
def userviewrequest():
	data={}
	rid=request.args['login_id']
	
	q="SELECT *,concat(request.status) as rstatus FROM `request` INNER JOIN `requestdetails` USING (`requestdetails_id`) INNER JOIN `car` USING (car_id) INNER JOIN USER USING (user_id)  where user_id=(select user_id from user where login_id='%s') " %(rid)
	res=select(q)

	data['data']=res

	data['status']="success"
	data['method']="userviewrequest"
	return str(data)


@api.route('/viewrequest')
def viewrequest():
	data={}
	
	q="SELECT *,concat(request.status) as rstatus FROM `request` INNER JOIN `requestdetails` USING (`requestdetails_id`) INNER JOIN `car` USING (car_id) INNER JOIN USER USING (user_id)  " 
	res=select(q)

	data['data']=res

	data['status']="success"
	data['method']="viewrequest"
	return str(data)


@api.route('/searchRequest')
def searchRequest():
	data={}
	
	search=request.args['search']+'%'
	

	q="SELECT *,concat(request.status) as rstatus FROM `request` INNER JOIN `requestdetails` USING (`requestdetails_id`) INNER JOIN `car` USING (car_id) INNER JOIN USER USING (user_id) where fplaces like '%s' or tplaces like '%s' "%(search,search) 
	res=select(q)
	print(q)
	
	data['data']=res

	data['status']="success"
	data['method']="viewrequest"
	
	return str(data)


@api.route('/PickUp')
def PickUp():
	data={}
	rid=request.args['rid']
	q="update request set status='PickUp' where request_id='%s'"%(rid)
	update(q)
	
	data['status']="success"
	data['method']="PickUp"
	
	return str(data)


@api.route('/Drop')
def Drop():
	data={}
	rid=request.args['rid']

	path="static/"+str(uuid.uuid4())+".png"
	s=qrcode.make(str(rid))
	s.save(path)
	print(s)
	q="update request set status='Drop', qrcode='%s' where request_id='%s'"%(path,rid)
	update(q)
	
	data['status']="success"
	data['method']="Drop"
	
	return str(data)




@api.route('/AddRequestss')
def AddRequestss():
	data={}
	rid=request.args['login_id']
	
	fplace=request.args['fplace']
	tplace=request.args['tplace']
	types=request.args['types']
	cid=request.args['cid']
	flatis,flongis=checkplace(fplace)
	print("from : ",flatis,flongis)
	
	tlatis,tlongis=checkplace(tplace)
	print("to : ",tlatis,tlongis)

	# fromlati=request.args['fromlati']
	# fromlongi=request.args['fromlongi']
	# tolati=request.args['tolati']
	# tolongi=request.args['tolongi']

	if flatis=="" or flongis=="" or tlatis=="" or tlongis=="":
		data['status']="failed"
	else:

		from math import sin, cos, sqrt, atan2, radians
		import math

		# Approximate radius of earth in km
		R = 6373.0

		lat1 = radians(float(flatis))
		lon1 = radians(float(flongis))
		lat2 = radians(float(tlatis))
		lon2 = radians(float(tlongis))

		dlon = lon2 - lon1
		dlat = lat2 - lat1

		a = sin(dlat / 2)**2 + cos(lat1) * cos(lat2) * sin(dlon / 2)**2
		c = 2 * atan2(sqrt(a), sqrt(1 - a))

		distance = R * c

		print("Result: ", distance)
		rounded_number = math.ceil(distance*100)/100  
		print(rounded_number) 





		if types=="Metro":
			ss=rounded_number*10
		elif types=="Water":
			ss=rounded_number*6
		elif types=="Bus":
			ss=rounded_number*10
		elif types=="Car":
			ss=rounded_number*30
		elif types=="Auto":
			ss=rounded_number*30

			print(ss)

		rounded_numbers = math.ceil(ss*100)/100  
		print(rounded_numbers)
		amounts=rounded_numbers


		amounts=str(amounts).split(".")

		# Define average speeds for each transportation type in km/h
		transport_speeds = {
		    "Metro": 30,
		    "Water": 20,
		    "Bus": 20,
		    "Car": 60,
		    "Auto": 40
		}

		# Specify the transportation type
		transport_type = "Car"  # Change this to the desired transportation type

		if transport_type in transport_speeds:
		    speed = transport_speeds[transport_type]
		    time_hours = distance / speed  # Calculate time in hours
		    time_minutes = time_hours * 60  # Convert to minutes
		    print("Estimated Time (Minutes):", time_minutes)

		    # Calculate cost based on time and transportation type
		    if transport_type == "Metro":
		        cost_per_minute = 0.10
		    elif transport_type == "Water":
		        cost_per_minute = 0.060
		    elif transport_type == "Bus":
		        cost_per_minute = 0.10
		    elif transport_type == "Car":
		        cost_per_minute = 0.30
		    elif transport_type == "Auto":
		        cost_per_minute = 0.30

		    cost = time_minutes * cost_per_minute
		    print("Estimated Cost:", cost)
		    output_str = f" Time: {time_minutes} minutes"
		    q="insert into requestdetails values(null,(select user_id from user where login_id='%s'),'%s','%s','%s','%s','%s','%s','%s')"%(rid,fplace,tplace,output_str,types,amounts[0],amounts[0],cid)
		    insert(q)

	
		data['status']="success"
	data['method']="AddRequestss"
	return str(data)


@api.route('/booknow')
def booknow():
	data={}
	login_id=request.args['rid']
	
	fplace=request.args['fplace']
	tplace=request.args['tplace']
	rid=request.args['rid']
	amt=request.args['amt']
	q="insert into request values(null,'%s','pending','%s','%s','%s')"%(login_id,amt,fplace,tplace)
	id=insert(q)
	data['rid']=id
	data['status']="success"
	data['method']="AddRequest"
	return str(data)

@api.route('/viewcarr')
def viewcarr():
	data={}
	q="select * from car WHERE seat!=0"
	res=select(q)

	
	data['data']=res

	data['status']="success"
	data['method']="viewcar"


	return str(data)



@api.route('/viewuser')
def viewuser():
	data={}
	uid=request.args['uid']
	q="select * from user WHERE user_id='%s'"%(uid)
	res=select(q)

	
	data['data']=res

	data['status']="success"
	data['method']="viewuser"


	return str(data)