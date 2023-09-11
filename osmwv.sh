#!/bin/bash
#
SCRIPTPATH=$(cd `dirname $0` && pwd)
echo $SCRIPTPATH
for filename in ${SCRIPTPATH}/lib/*.jar
 do CLASSPATH=${CLASSPATH}:${filename}
done
echo $CLASSPATH
export CLASSPATH
java -Xmx1024M -Xms128M osm.viewer.Main
java pack.Pause
exit
