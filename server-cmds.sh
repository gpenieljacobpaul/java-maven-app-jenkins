#!/usr/bin/env bash
export IMAGES=$1

docker-compose -f docker-compose.yaml up --detach
echo "success"