FROM alpine
# This command will be executed every time the container is run
CMD gradle bootRun
RUN apk update && apk add gradle
RUN mkdir /workdir
WORKDIR /workdir
ADD . .
# Gradle is run without daemon because performance is better this way when only one compilation
# is made. Also, the daemon used during the compilation seems to not be compatible with the bootRun daemon,
# which creates its own regardless of the previous existance of this one.
RUN gradle build --no-daemon
EXPOSE 8081

