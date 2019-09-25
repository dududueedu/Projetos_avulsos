# -*- coding: utf-8 -*-
#UDPCliente.py
from socket import * #criacao de socket
from datetime import datetime #biblioteca para o temporizador de tempo limite

ServerName = 'localhost' #nome do servidor UDP no PC
ServerPort = 12000 #porta   
cont = 0; #variavel de interacao do laço WHILE

ClienteSocket = socket(AF_INET, SOCK_DGRAM) #atribui uma porta e um IP para o cliente socket
ClienteSocket.settimeout(1.0) #temporizador de 1s de espera de resposta 

while cont < 10:
		message = 'hello'

		try:
			ClienteSocket.sendto(message.encode(), (ServerName, ServerPort)) #cliente manda mensagem para o SERVIDOR
			InicioTempo = datetime.now() #dar inicio ao tempo
			ModifiedMessage, ServerAddress = ClienteSocket.recvfrom(1024) #msg recebida de 	volta p/ o cliente
			FimTempo = datetime.now().second #temporizador de limite final 					------ 
			print('Server return: '+ModifiedMessage.decode('ASCII'), cont + 1, 'HR: '+str(InicioTempo)[11:19] + ' RTT: 0.'+str(FimTempo)[:1] + 's')		

		except timeout: #exceção se o temporizador for interrrompido
			print('Solicitacao expirada.') #printa msg com tempo esgotado

		cont += 1
ClienteSocket.close()#ClienteSocket.close() #encerra a conexao socket

pass