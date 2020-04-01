#!/bin/bash

tag=$(git symbolic-ref HEAD 2>/dev/null | awk -F '/' '{print $NF}')
mvn clean package docker:build -DdockerImageTags=$tag
docker run -p 8082:8082 --rm --network service -ti atech-server-register:$tag