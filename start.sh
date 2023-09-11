OSMWV=/home/christian/git/osmwv
CLASSPATH=${CLASSPATH}:${OSMWV}/lib/osmwv.jar
export CLASSPATH
cd ${OSMWV}
java -Xmx4096M -Xms128M osm.viewer.Main
exit

