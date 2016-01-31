== DEBS 2015 Apache Geode Demo

Apache Geode (incubating) is a distributed key-value store built for scale and performance. The ACM Distributed Event-Based Systems conference always create interesting challenges for data processing and in this talk we will present a solution for analysing taxi trip information completely based on Apache Geode and some other key features that the project offers being beyond other key-value stores.

== Project structure

The modules for the implementation goes as follows:


| Module  | Description  |
|---|---|---|---|---| 
| model | data model | 
| listeners | CacheListeners, Functions, PartitionResolvers |
| data-loader | Data loader that read the files and import in Geode |
| scripts | Set of scripts to start a local Geode 2 node cluster |
| config | Configuration file for the system |
| data | Sample data files  |

== Building

As a gradle based project just go to the root directory and execute: `gradle build`
For Gradle download or installation instructions please check https://gradle.org/gradle-download/

== References

* http://debs2015.org/call-grand-challenge.html
* http://www.cosmopolitan.com/sex-love/news/a49615/nyc-sexiest-cab-drivers/
* http://geode.incubator.apache.org

Presented at:

* FOSDEM 2016 - https://fosdem.org/2016/schedule/event/hpc_bigdata_debs/


== Steps

* Start the Geode cluster

    `scripts/startAll.sh`

* Stop the Geode cluster

    `scripts/stopAll.sh`

* Loading the file

TBD 

* Starting the clients

TBD

Other commands to explore on `gfsh`