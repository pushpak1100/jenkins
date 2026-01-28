jenkis-projects Pipeline Automation of the JOBs

4 Jobs - Pipeline PULL -> BUILD -> TEST -> DEPLOYMENT (CICD) Plugins (Pipeline)

Pipeline -> Groovy Language (DSL)Domain-Specific Language, which is a scripting language used to define and manage Jenkins jobs.

Scripted Pipeline .groovy | node Declarative Pipeline .groovy/.jdp | More easy to Use | pipeline

build stage a. code integration b. code validation c. code compilation d. code package

build tool:- (select devlopers, before start project and development) -apache maven(pom.xml),gradel(gradlew),ant

repo create--maven--project

Maven
1.basic of maven 2. installation of maven 3. how to create a project 4. read the pom.xml file

maven phsases maven lifecycle(bunch of phases)---maven phases---maven goles

combine multiple gols like war:war----and create lifecycle

1.default = 8 phases 2. clean 3. site

java version switch
update-alternatives --config java   ##for ubuntu

 alternatives --config java ##for linux machine
There are 3 choices for the alternative java (providing /usr/bin/java).

  Selection    Path                                            Priority   Status
------------------------------------------------------------
*  1           /usr/lib/jvm/java-11-openjdk-amd64/bin/java       1111      auto mode
   2           /usr/lib/jvm/java-17-openjdk-amd64/bin/java       1112      manual mode
   3           /usr/lib/jvm/java-8-openjdk-amd64/jre/bin/java      1110      manual mode

Press <enter> to keep the current choice[*], or type selection number: 2
install older version of jenkins
sudo apt-cache search openjdk

sudo apt-get install openjdk-11-jdk
install reposetory first
sudo wget -O /usr/share/keyrings/jenkins-keyring.asc \
  https://pkg.jenkins.io/debian-stable/jenkins.io-2023.key
echo "deb [signed-by=/usr/share/keyrings/jenkins-keyring.asc]" \
  https://pkg.jenkins.io/debian-stable binary/ | sudo tee \
  /etc/apt/sources.list.d/jenkins.list > /dev/null
sudo apt-get update
then refer this and install older versions

https://directdevops.blog/2019/01/04/installing-specific-lts-version-of-jenkins-on-ubuntu/

sudo apt-cache madison jenkins
sudo apt-get install jenkins=2.426.1  -y
https://www.jenkins.io/doc/book/platform-information/support-policy-java/

Sonarqube
quality Anlysis (selenium,sonarqube)

vulnerability (security risk) code smell : syntex --code undustandeble dupliocation : code % duplication bugs : some feature not work proparly

(error:- if error come s ur hole code not working)

Java based application

port 9000

sed -i s/'demo'/'root'/ abc.txt demo replace with root without entering into abc.txt file.

SonarQube Installation
Prerequisites

SonarQube server will require 3GB+ RAM to work effeciently

Install Database
rpm -ivh http://repo.mysql.com/mysql57-community-release-el7.rpm
rpm --import https://repo.mysql.com/RPM-GPG-KEY-mysql-2022
yum install mysql-server -y
systemctl start mysqld
systemctl enable mysqld
grep 'temporary password' /var/log/mysqld.log
mysql_secure_installation
Install Java
yum install wget epel-release -y
yum install java -y
wget https://download.bell-sw.com/java/11.0.4/bellsoft-jdk11.0.4-linux-amd64.rpm
rpm -ivh bellsoft-jdk11.0.4-linux-amd64.rpm
#alternatives --config java
Configure Linux System for Sonarqube

echo 'vm.max_map_count=262144' >/etc/sysctl.conf
sysctl -p
echo '* - nofile 80000' >>/etc/security/limits.conf
sed -i -e '/query_cache_size/ d' -e '$ a query_cache_size = 15M' /etc/my.cnf
systemctl restart mysqld
Configure Database for Sonarqube

mysql -p -u root
mysql>
    create database sonarqube;
    create user 'sonarqube'@'localhost' identified by 'Redhat@123';
    grant all privileges on sonarqube.* to 'sonarqube'@'localhost';
    flush privileges;
Install Sonarqube

yum install unzip -y
wget https://binaries.sonarsource.com/Distribution/sonarqube/sonarqube-7.9.1.zip
cd /opt
unzip ~/sonarqube-7.9.1.zip
mv sonarqube-7.9.1 sonar
Configure Sonarqube

sed -i -e '/^sonar.jdbc.username/ d' -e '/^sonar.jdbc.password/ d' -e '/^sonar.jdbc.url/ d' -e '/^sonar.web.host/ d' -e '/^sonar.web.port/ d' /opt/sonar/conf/sonar.properties
sed -i -e '/#sonar.jdbc.username/ a sonar.jdbc.username=sonarqube' -e '/#sonar.jdbc.password/ a sonar.jdbc.password=Redhat@123' -e '/InnoDB/ a sonar.jdbc.url=jdbc.mysql://localhost:3306/sonarqube?useUnicode=true&characterEncoding=utf&rewriteBatchedStatements=true&useConfigs=maxPerformance' -e '/#sonar.web.host/ a sonar.web.host=0.0.0.0' /opt/sonar/conf/sonar.properties
useradd sonar
chown sonar:sonar /opt/sonar/ -R
sed -i -e '/^#RUN_AS_USER/ c RUN_AS_USER=sonar' sonar.sh
Start Sonarqube

/opt/sonar/bin/linux*/sonar.sh start
/opt/sonar/bin/linux*/sonar.sh status
/opt/sonar/logs
##6c387f93b2f66e644f55a697941b16c9b71cee29 hyxwyb-1sufpi-vaNdad

mvn sonar:sonar -Dsonar.projectKey=studentapp -Dsonar.host.url=http://18.234.80.25:9000 -Dsonar.login=6c387f93b2f66e644f55a697941b16c9b71cee29
