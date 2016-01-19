#!/bin/bash
######
##  deploy listener jars to GemFire
######

gfsh -e 'connect' -e 'deploy --jar=..//listeners/build/libs/debs2015-geode-listeners-1.0-SNAPSHOT.jar'
