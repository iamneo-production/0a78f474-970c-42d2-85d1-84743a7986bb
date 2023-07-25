#!/bin/bash

# Command 1
echo "Executing Command 1: sudo apt install zip unzip"
sudo apt update
sudo apt install -y zip unzip

# Command 2
echo "Executing Command 2: Installing SDKMAN"
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"

# Command 3
echo "Executing Command 3: Installing Java"
sdk install java

# Command 4
echo "Executing Command 4: Installing Spring Boot"
sdk install springboot

# Additional commands can be added as needed
