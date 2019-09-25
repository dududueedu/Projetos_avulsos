import socket
import sys
from os import system

try:
    ip, port = ('0.0.0.0', 8000)

    server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    print('Ready server...')

    server.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)

    server.bind((ip, port))

    server.listen(1)

    print('Listen on %s:%s' % (ip, port))

    (obj, client) = server.accept()
    
    print('Connection received from %s' % client[0])

    msg = obj.recv(1024).decode()

    if msg:
        file = (msg.split()[1])[1:]
        try:
            bytes = open(file).read()
            response_body_raw = ''.join(bytes)
            obj.send('HTTP/1.1 200 OK\r\n'.encode())
            obj.send("Content-Type: text/html\r\n".encode())
            obj.send('\n'.encode())
            obj.send(response_body_raw.encode())
        except IOError:
            obj.send('HTTP/1.1 404 Not Found\r\n'.encode())
            obj.send("Content-Type: text/html\r\n".encode())
            obj.send("\n".encode())
            obj.send("FILE_NOT_FOUND\n".encode())
    else:
        obj.send('HTTP/1.1 400 Bad Request\r\n'.encode())
        obj.send("Content-Type: text/html\r\n".encode())
        obj.send("\n".encode())
        obj.send("BAD_REQUEST\n".encode())
            
    server.shutdown(socket.SHUT_RDWR)
    server.close()
    print("Connection closed")
    sys.exit()
except KeyboardInterrupt as error:
    print("Connection closed by user")