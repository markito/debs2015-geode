#!/bin/bash
################################################################
# connect to a running locator, start server and create regions
################################################################
. ./setEnv.sh

gfsh	-e "connect" -e "start server --name=server1 --max-heap=4G"

# deploy listener jar
. ./deployJar.sh

gfsh stop server --dir=server1
gfsh stop server --dir=server2

gfsh	-e "connect" -e "start server --server-port=0 --name=server1 --max-heap=2G --cache-xml-file=cache.xml"
gfsh	-e "connect" -e "start server --server-port=0 --name=server2 --max-heap=2G --cache-xml-file=cache.xml"

