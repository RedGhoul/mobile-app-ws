FROM ubuntu:20.04
USER root
RUN apt update && \
    apt -y upgrade && \
    apt -y install nodejs && \
    apt -y install npm && \
    apt -y install openjdk-17-jdk
RUN apt -y install wget
RUN wget https://dlcdn.apache.org/maven/maven-3/3.8.5/binaries/apache-maven-3.8.5-bin.tar.gz -P /tmp
RUN tar xf /tmp/apache-maven-*.tar.gz -C /opt && \
    ln -s /opt/apache-maven-3.8.5 /opt/maven
 
EXPOSE 8080
ENV SPRING_PROFILES_ACTIVE=prod,api-docs
ENV JHIPSTER_SLEEP=30
COPY . ./app
WORKDIR "/app"
RUN ["chmod", "+x", "entry.sh"]
ENTRYPOINT "./entry.sh"
