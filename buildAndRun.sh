#!/bin/sh
mvn clean package && docker build -t net.xravn/examen2P42021S2 .
docker rm -f examen2P42021S2 || true && docker run -d -p 9080:9080 -p 9443:9443 --name examen2P42021S2 net.xravn/examen2P42021S2