# -*- coding: utf-8 -*-
"""
Created on Fri Apr  9 21:09:54 2021

@author: Onni
"""
import os
import discord
#import nest_asyncio
from dotenv import load_dotenv
from urllib.parse import urlparse
from deepdream import getdeepdream
from r34 import haeR34



#nest_asyncio.apply()


    


load_dotenv()
TOKEN = os.getenv('DISCORD_TOKEN')

client = discord.Client()

print("TESTI")


@client.event
async def on_ready():
    print('We have logged in as {0.user}'.format(client))
    print("Koodi: ", "https://discord.com/api/oauth2/authorize?client_id=830143437712916481&permissions=8&scope=bot")

@client.event
async def on_message(message):
    if message.author == client.user:
        return
    
    if message.content.startswith("!r34"):
        chunks = message.content.split(' ')
        chunks.pop(0)
        hakusana = ""
        for termi in chunks:
            hakusana = hakusana + termi
            
        url = haeR34(hakusana)
        if url == "virhe":
            await message.channel.send("No images found with your search terms")
            return
        
        await message.channel.send(url)
    
    
    #print("!dd" in message.content)
    
    
    if message.content.startswith("!dd"):
        
        chunks = message.content.split(' ')
        try:
            o = urlparse(chunks[1]) #TODO: voi olla out of bounds
            dd = getdeepdream(o.geturl())
            #await message.channel.send(message)
            await message.channel.send(dd["output_url"])
            await message.delete()
        except IndexError:
            print("indeksi")
        
        attachments = message.attachments
        try:
            #print(attachments[0])
            dd = getdeepdream(attachments[0])
            await message.channel.send(dd["output_url"])
            await message.delete
        except:
            print("Attachment") 




client.run(TOKEN)