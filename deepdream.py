# -*- coding: utf-8 -*-
"""
Created on Fri Apr  9 21:00:34 2021

@author: Onni
"""

import requests
import os
from dotenv import load_dotenv



"""
r = requests.post(
    "https://api.deepai.org/api/deepdream",
    data={
        'image': "https://im.mtv.fi/image/7122978/landscape16_9/1600/900/bdb71c0e9b3690fe87f0f2108f0b2b80/gQ/kalle-lamberg.jpg"
    },
    headers={'api-key': 'quickstart-QUdJIGlzIGNvbWluZy4uLi4K'}
)
print(r.json())
"""

def getdeepdream(URL):

    load_dotenv()
    TOKEN = os.getenv('API_KEY')
    r = requests.post(
    "https://api.deepai.org/api/deepdream",
    data={
        'image': URL
    },
    headers={'api-key': TOKEN}
    )
    return r.json()

