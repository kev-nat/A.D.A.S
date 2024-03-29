import numpy as np
import pandas as pd
import firebase_admin
import time

from firebase import firebase
firebase = firebase.FirebaseApplication('https://adas1-e4441-default-rtdb.asia-southeast1.firebasedatabase.app', None) # Database 1

from sklearn.naive_bayes import GaussianNB
from sklearn.preprocessing import StandardScaler
from model import classifier

from firebase_admin import credentials
from firebase_admin import db

cred = credentials.Certificate('adas2-65e48-firebase-adminsdk-a29ay-a06deac1ff.json') # Database 2
firebase_admin.initialize_app(cred, {
        'databaseURL': 'https://adas2-65e48-default-rtdb.asia-southeast1.firebasedatabase.app' 
        })

i = 0
x = 5
a = 0
b = 199

count = 0

time.sleep(70)
    
while i <=x:
    result = firebase.get('/X', None )
    import pandas as pd
    df = pd.DataFrame.from_dict({"X": [result]})
    df

    data_x = []
    for key, value in result.items():
        data_x.append(value)

    size = len(data_x)
    print(size)
    x = int ((size/200)-1)

    size = len(data_x)
    x = int ((size/200)-1)  

    df = {'X' : data_x}
    df = pd.DataFrame.from_dict(df, orient='index')
    df = df.transpose()
    pd.set_option('display.max_rows', None)
    pd.set_option('display.max_columns', None)
    
    datanow = data_x[a:b]
    a += 200 
    b += 200
    i += 1
    print (i)
    
    array_list = np.array(datanow)
    resultant_string = np.reshape(array_list, (-1, 199))
    datanow = resultant_string      
    result1 = classifier.predict(datanow)
           
    df = pd.DataFrame(result1)
    json = df.to_json()
    
    if result1[0] == 1:
        count += 1

    if result1 == 1:
        display ='Drive Safely'
    
    else:
        display ="u're good"
    
    ref = db.reference('/')
    ref.set({
                'Result': {
                    'Warning': display,
                    'Count': count,
                },
        })
    
    time.sleep(70)