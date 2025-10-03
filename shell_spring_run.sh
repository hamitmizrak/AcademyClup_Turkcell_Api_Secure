#!/bin/bash
echo "Other Programming "

# User Variable
INSTALL="Spring Boot Yükleme Yapılıyor..."


###################################################################
###################################################################
sudo chmod +x ./shell_countdown.sh

###################################################################
###################################################################

# Port And Version
portVersion() {
    sudo ./shell_countdown.sh
    java -v
    javac -v
    git -v
    npm -v
    node -v
    mvn --version
    docker -v
     # docker Version
    docker-compose -v
}
portVersion

##################################################################
###################################################################
# Spring Boot
springboot_run_install() {
    sleep 2
    echo -e "\n###### ${INSTALL} ######  "
    read -p "Spring boot bash terminal üzerinden başlatmak ister misiniz ? e/h " installResult
    if [[ $installResult == "e" || $installResult == "E" ]]; then
        echo -e "Spring Boot Başlatıyor..."

        # Geri Sayım
        sudo ./shell_countdown.sh

        # Spring Boot
        mvn spring-boot:run

    else
        echo -e "Spring Boot Başlamadı"
    fi
}
springboot_run_install

###################################################################
###################################################################
# Port And Version
portVersion() {
    java -v
    javac -v
    git -v
    npm -v
    node -v
    mvn --version
    docker -v
     # docker Version
    docker-compose -v
}
portVersion
