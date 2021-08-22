FROM adoptopenjdk/openjdk16:x86_64-alpine-jre16u-nightly
MAINTAINER Ivan Poltorakov <ivan@poltorakov.ru>
ADD ./target/fspoitmo.jar fspoitmo.jar
ENTRYPOINT java -jar fspoitmo.jar fspoitmo
EXPOSE 8071
