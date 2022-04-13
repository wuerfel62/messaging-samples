activemq aufsetzen
	-im Ordner activemq befehl: docker-compose up -d
	-mq in browser �ffnen: localhost:8161
	-swagger ui: localhost:9080/openapi/ui/
	
	server start: liberty:dev
	
	http://localhost:9083/swagger-ui/index.html
	
kafka docker:
	docker-compose up -d
	
	version: die neuste (2.8)
	offset explorer: name: kafka-kocal z.B.
	zookeeeper port: 22181
	advanced:  bootstrap servers : localhost:29092