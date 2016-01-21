#!/bin/bash
################################################################
# connect to a running locator, start server and create regions
################################################################
. ./setEnv.sh

gfsh	-e "connect" -e "start server --name=server1 --max-heap=4G"

# deploy listener jar
. ./deployJar.sh

# create async event-queue
gfsh	-e "connect" \
	-e "create async-event-queue --id=frequentRouteQueue --batch-time-interval=5 --listener=org.apache.geode.example.debs.listeners.FrequentRouterListener"\
	-e "create async-event-queue --id=cellGridQueue --batch-time-interval=5 --listener=org.apache.geode.example.debs.listeners.CellGridListener"\
	-e "create region --name=TaxiTrip --type=PARTITION --cache-writer=org.apache.geode.example.debs.listeners.CellWriter --async-event-queue-id=frequentRouteQueue,cellGridQueue"\
	-e "create region --name=FrequentRoute --type=REPLICATE"\
	-e "create region --name=ProfitableArea --type=REPLICATE"\
