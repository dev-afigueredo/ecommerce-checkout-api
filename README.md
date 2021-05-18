<h2>API para gestão de Checkout orientado a eventos</h2>

API de gestão de pagamentos: https://github.com/dev-afigueredo/ecommerce-payment-api

São necessários os seguintes pré-requisitos para a execução do projeto desenvolvido:

* Java 14 ou versões superiores.
* Gradle 6.8.3 ou versões superiores.
* IntelliJ IDEA Community Edition ou sua IDE favorita.
* Controle de versão GIT instalado na sua máquina.
* PostgreSQL
* Docker
* Kafka
* Zookeeper
* Schema-register

Lembrando que essa API criará um evento para que a API de pagamento ouça todas as entradas, depois processará os dados
da compra e salvará no banco os dados as informações de entrega e do comprador. Assim, a API de pagamentos processará 
os dados de pagamento e salvará no banco de dados as informações enviadas pelo evento da API de Checkout.

Comando para subir a 'infra' no Docker localizado em /docker/docker-compose.yml:
```shell script
docker-compose up -d
```

Comando para derrubar a 'infra':
```shell script
docker-compose down
```