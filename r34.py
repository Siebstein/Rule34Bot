# -*- coding: utf-8 -*-
"""
Created on Tue Jul  6 14:46:56 2021

@author: Onni
"""

import urllib
import re

regex = "file_url=\"https:\/\/.{3,120}\..{1,7}\""
pituusURL = "https://rule34.xxx/index.php?page=dapi&s=post&q=index&limit=3&tags="
URL = "https://rule34.xxx/index.php?page=dapi&s=post&q=index&limit=1000&tags="

def haeR34(hakusana):
    formatoitu = hakusana.replace(' ', '_')
    formatoitu = formatoitu.replace('ä', '%C3%A4')
    formatoitu = formatoitu.replace('Ä', '%C3%A4')
    formatoitu = formatoitu.replace('ö', '%C3%B6')
    formatoitu = formatoitu.replace('Ö', '%C3%B6')
    formatoitu = formatoitu.replace('å', '%C3%A6')
    formatoitu = formatoitu.replace('Å', '%C3%A6')
    kokoURL = URL + formatoitu
    file = urllib.request.urlopen(kokoURL)
#    print(kokoURL)
    sivu = ''
    
    for line in file:
        sivu = sivu + line.decode("utf-8")
    
    
    
    osumat = re.findall(regex, sivu)
    
    if len(osumat) == 0:
        return "virhe"
    
#    print(osumat)
#    print(len(osumat))
  
    
    import random
    randint = random.randint(0, len(osumat) -1)
       
    urlex = "https:\/\/.{3,120}(?=\")"
    pictureURL = re.findall(urlex, osumat[randint])
    return pictureURL[0]
        