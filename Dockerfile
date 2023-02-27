# FROM mcr.microsoft.com/playwright-java:latest
FROM maven:3.8.7-openjdk-18-slim
COPY . /e2e
WORKDIR /e2e
ENTRYPOINT /bin/bash
# ENV BROWSER=chrome
# RUN mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install"
# CMD [ "mvn", "clean", "verify", "-DBROWSER=chrome", "-DHEADLESS=true" ]

#docker run -v $PWD:/tests -w /tests --rm -it mcr.microsoft.com/playwright/java:latest /bin/bash
#EXPOSE 5000

# RUN npm install
# RUN npx playwright install
# Run playwright test
# CMD [ "npx", "playwright", "test", "--reporter=list" ]

# https://www.oddbird.net/2022/11/30/headed-playwright-in-docker/
# docker run --rm mcr.microsoft.com/playwright:v1.28.0 npx -y playwright open google.com
# docker run --rm -e DISPLAY=:0 -v /tmp/.X11-unix:/tmp/.X11-unix mcr.microsoft.com/playwright:v1.28.0 npx -y playwright open google.com
