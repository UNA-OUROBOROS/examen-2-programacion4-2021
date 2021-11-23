@echo off
call mvn clean package
call docker build -t net.xravn/examen2P42021S2 .
call docker rm -f examen2P42021S2
call docker run -d -p 9080:9080 -p 9443:9443 --name examen2P42021S2 net.xravn/examen2P42021S2