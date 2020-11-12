# Pre Setup
- Get the Ubuntu VM 16.04 (Same vnet as the clusters) 
- Configure /etc/resolv.conf in the VM to include the AAD DS domain name as a search domain 
	- Ping headnodes with hostnames to make sure that DNS works 
- Add Ubuntu VM (\<ipaddress> \<VMName>.\<AAD DS Domain name>) to /etc/hosts file of clusters hn0 and hn1
	- Ping Ubuntu VM from headnodes with hostname or ip address to test connection
- Install openjdk 8 and set JAVA_HOME 
	- sudo apt-get update
	- sudo apt-get install openjdk-8-jdk
	- java -version
	- export JAVA_HOME=path_to_java_home(/usr/lib/jvm/java-8-openjdk-amd64)
- Export the following, from one of the headnodes to the VM 
	- Sudo su 
	- Cd / 
	- tar -cvhf /home/sshuser/hadoop-conf.tar /etc/hadoop/conf 
	- tar -czvf /home/sshuser/hive.tar /usr/hdp/\<version-no>/hive 
	- tar -czvf /home/sshuser/hadoop.tar /usr/hdp/\<version-no>/hadoop 
	- tar -czvf /home/sshuser/hcatalog.tar /usr/hdp/\<version-no>/hive-hcatalog 
- For 4.0 cluster we need these additional folders
	- tar -czvf /home/sshuser/hadoop-hdfs.tar /usr/hdp/\<version-no>/hadoop-hdfs
	- tar -czvf /home/sshuser/hadoop-mapreduce.tar /usr/hdp/\<version-no>/hadoop-mapreduce
	- tar -czvf /home/sshuser/hadoop-yarn.tar /usr/hdp/\<version-no>/hadoop-yarn
	
- Copy these tar files to the VM from headnode
    - scp /home/sshuser/*.tar \<username>@\<VMIP>: 
- Untar the files on the VM 
	- Sudo su 
	- Cd / 
	- tar -xvf /home/\<username>/*.tar (untar each file if there is issue)
- Set the hadoop and hive home using below commands
	-  export HADOOP_HOME=/usr/hdp/\<version-no>/hadoop
	-  export HIVE_HOME=/usr/hdp/\<version-no>/hive
	-  PATH=\$PATH:$HIVE_HOME/bin
- Build the oauthwithhib model from repo (using ubuntu on widows or IDE with sudo user), copy the required jar files to the VM
	- cd src/oauthwithhib 
	- mvn package
- scp target/oauthwithhib-1.0-SNAPSHOT-jar-with-dependencies.jar \<username>@\<VMIP>:

# Launching Beeline from the VM
- Build the beelinewithhib model from repo (using ubuntu on widows or IDE with sudo user), copy the required jar files to the VM
	- cd src/beelinewithhib 
	- mvn package  
- scp target/beelinewithhib-1.0-SNAPSHOT-jar-with-dependencies.jar \<username>@\<VMIP>:
- Execute beeline_hib.sh script on the VM to launch beeline
- Note: Provide clusterdnsname as input to the script
- Note: The script expects the required jars placed under /home/\<username>, if the jars location is different please update the script before running
- Note: In case of gateway or authorization errors, the error message will be displayed and the beeline connection attempt would exit

# Launching Livy from the VM
- Build the livywithhib model from repo (using ubuntu on widows or IDE with sudo user), copy the required jar files to the VM
	- cd src/livywithhib 
	- mvn package  
- scp target/livywithhib-1.0-SNAPSHOT-jar-with-dependencies.jar \<username>@\<VMIP>:
- Execute livy_hib.sh script on the VM
- Note: Provide clusterdnsname, sample_livywithhib.json (present here src/livywithhib/src/main/resources/sample_livywithhib.json) as input to the script
- Note: The script expects the required jars placed under /home/\<username>, if the jars location is different please update the script before running
