#
# OSMWV OpenStreetMapWebsiteViewer
#
$Dir = $PSScriptRoot
$Dir = "C:\git\osmwv"
$Lib = $Dir + "\lib\*.jar"
$jars = Get-ChildItem -Path $Lib
foreach ($jar in $jars)
{
 $Env:CLASSPATH=$Env:CLASSPATH + ";" + $jar
}
#
$Lib = $Dir + "\dll\"
$Env:PATH = $Env:PATH + ";" + $Lib
#
java -Xmx1024M -Xms128M osm.viewer.Main
java pack.Pause
