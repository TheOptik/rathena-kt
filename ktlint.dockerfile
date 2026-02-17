FROM eclipse-temurin:25-jdk-alpine

ARG KTLINT_VERSION=1.8.0

RUN wget -O ktlint "https://github.com/pinterest/ktlint/releases/download/${KTLINT_VERSION}/ktlint" && chmod a+x ktlint && mv ktlint /usr/local/bin

ENTRYPOINT ["ktlint"]