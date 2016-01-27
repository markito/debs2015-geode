#!/bin/bash
. ./setEnv.sh

mkdir -p locator1
gfsh -e "start locator --name=locator1" -e "configure pdx --read-serialized=true"
