#!/bin/bash
. ./setEnv.sh

gfsh stop server --dir=server1
gfsh stop server --dir=server2
gfsh stop locator --dir=locator1

