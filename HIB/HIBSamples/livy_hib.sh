#! /bin/bash

currentUser=$(logname)

java -cp /home/$currentUser/livywithhib-1.0-SNAPSHOT-jar-with-dependencies.jar:/home/$currentUser/oauthwithhib-1.0-SNAPSHOT-jar-with-dependencies.jar com.microsoft.azure.hdinsight.utils.LivyWithHib $1 $2