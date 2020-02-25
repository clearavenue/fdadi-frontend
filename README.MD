FDADI
---
docker build -t clearavenue/fdadi-frontend .
docker run --rm -it --name fdadi-frontend --net fdadi-bridge -p 8081:8081 clearavenue/fdadi-frontend

