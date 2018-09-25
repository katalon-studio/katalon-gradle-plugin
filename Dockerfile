FROM gradle:jdk8 

RUN useradd jenkins --shell /bin/bash --create-home

USER jenkins
