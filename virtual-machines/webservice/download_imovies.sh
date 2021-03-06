#!/bin/bash
cd /home/imovies-admin

eval "$(ssh-agent -s)"
ssh-add /home/imovies-admin/.ssh/id_rsa

echo -e "Host github.com\n\tStrictHostKeyChecking no\n" >> /home/imovies-admin/.ssh/config

chown imovies-admin:imovies-admin /home/imovies-admin/.ssh/
chown imovies-admin:imovies-admin /home/imovies-admin/.ssh/id_rsa
chown imovies-admin:imovies-admin /home/imovies-admin/.ssh/id_rsa.pub

chmod 700 /home/imovies-admin/.ssh/
chmod 600 /home/imovies-admin/.ssh/id_rsa

echo "##### DOWNLOAD FROM GITHUB #####"
rm -rf imovies
su imovies-admin << EOF
git clone git@github.com:hlycklama/imovies.git
EOF
cd imovies
find . -type f -exec chmod 600 {} +
find . -type d -exec chmod 700 {} +
chmod 700 ./mvnw

cp ./src/main/resources/application.properties.example ./src/main/resources/application.properties
chown imovies-admin:imovies-admin /home/imovies-admin/imovies/src/main/resources/application.properties

# shred files instead of only deleting them logically and mangle directory entry before unlinking
find /home/imovies-admin/imovies/virtual-machines -type f -exec shred --remove=wipesync -f {} \;
find /home/imovies-admin/imovies/.git -type f -exec shred --remove=wipesync -f {} \;
find /home/imovies-admin/.git -type f -exec shred --remove=wipesync -f {} \;
shred --remove=wipesync -f /home/imovies-admin/imovies/schema.sql
# remove git deploy key!!
shred --remove=wipesync -f /home/imovies-admin/.ssh/id_rsa
shred --remove=wipesync -f /home/imovies-admin/.ssh/id_rsa.pub
# remove the folder hierarchy
rm -rf /home/imovies-admin/imovies/virtual-machines

# run imovies in daemon

chmod 700 /home/imovies-admin/imovies/run_imovies.sh
#daemon --name="imovies-webservice" --output=webservice.txt sh /home/imovies-admin/imovies/run_imovies.sh
su imovies-admin << EOF
(cd /home/imovies-admin/imovies && (./run_imovies.sh & ))
EOF