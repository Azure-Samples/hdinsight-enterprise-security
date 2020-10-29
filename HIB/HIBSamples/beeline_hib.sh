#! /bin/bash

currentUser=$(logname)

command=$(java -cp /home/$currentUser/beelinewithhib-1.0-SNAPSHOT-jar-with-dependencies.jar:/home/$currentUser/oauthwithhib-1.0-SNAPSHOT-jar-with-dependencies.jar com.microsoft.azure.hdinsight.utils.BeelineWithHib $1 2>&1 | tee /dev/tty)

beestring=$(echo $command | awk -F'JDBC URL for beeline connection: ' '{print $2}')

if [[ ! $command == *"authentication is not successful"* ]] && [[ ! $command == *"Caught exception"* ]]; then
    if [ -z "$beestring" ]; then
        echo "JDBC connection string is empty, exiting beeline connection"
    else
        echo "Connecting to beeline"
        beeline -u $beestring
    fi
fi