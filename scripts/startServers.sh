#!/bin/bash
################################################################
# connect to a running locator, start server and create regions
################################################################
. ./setEnv.sh

gfsh	-e "connect" -e "start server --name=server1 --max-heap=4G"

# deploy listener jar
. ./deployJar.sh

gfsh stop server --dir=server1

gfsh	-e "connect" -e "start server --name=server1 --max-heap=4G --cache-xml-file=cache.xml"

