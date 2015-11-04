#!/bin/bash

gfsh	-e "connect" -e "start server --name=server1 --max-heap=4G" \
	-e "create region --name=TaxiTrip --type=PARTITION" \
	-e "create region --name=FrequentRoute --type=REPLICATE" \
	-e "create region --name=ProfitableArea --type=REPLICATE" \
