#!/bin/bash
######
##  deploy listener jars to GemFire
######

. ./setEnv.sh

gfsh -e 'connect' \
      -e 'deploy --jar=..//listeners/build/libs/debs2015-geode-listeners-1.0-SNAPSHOT.jar' \
      -e 'deploy --jar=..//model/build/libs/debs2015-geode-model-1.0-SNAPSHOT.jar'
