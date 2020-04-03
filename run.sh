#!/bin/bash

echo "Criando banco para testes"

docker stop authserverdbteste

docker rm authserverdbteste

docker run -p 5665:5432 --name authserverdbteste -e POSTGRES_USER=atech -e POSTGRES_PASSWORD=atech -e POSTGRES_DB=authserverdbteste -d postgres:10.5-alpine

echo "Iniciando compilacao dos servicos..."

mvn clean package

echo "Apagando banco para testes"

docker stop authserverdbteste

docker rm authserverdbteste

echo "Iniciando o docker compose..."

docker-compose up --build
