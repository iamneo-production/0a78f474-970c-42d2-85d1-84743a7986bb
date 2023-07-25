#!/bin/bash

# Command 1
echo "Installing zip and unzip ..."
sudo apt update
sudo apt install -y zip unzip

# Command 2
echo "Fetching sdkman and extracting ..."
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"

# Command 3
echo "Installing Java ..."
sdk install java

# Command 4
echo "Installing Spring Boot ..."
sdk install springboot

# Command 5
echo "Installing Redis ..."
sudo apt install redis -y
sudo service redis-server start


# Additional commands can be added as needed
