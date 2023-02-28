FROM mcr.microsoft.com/playwright/java:v1.30.0-focal
COPY . /e2e
WORKDIR /e2e
ENTRYPOINT /bin/bash
# ENV BROWSER=chrome
# ADD /host/file image/file
# RUN mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install"
# CMD [ "mvn", "clean", "verify", "-DBROWSER=chrome", "-DHEADLESS=true" ]

# EXPOSE 5000
# RUN npm install
# RUN npx playwright install
# Run playwright test
# CMD [ "npx", "playwright", "test", "--reporter=list" ]

#docker run -v $PWD:/e2e -w /e2e --rm -it mcr.microsoft.com/playwright/java:v1.30.0-focal /bin/bash
#docker run -v %CD%:/e2e -w /e2e --rm -it mcr.microsoft.com/playwright/java:v1.30.0-focal /bin/bash

# https://www.oddbird.net/2022/11/30/headed-playwright-in-docker/
# docker run --rm mcr.microsoft.com/playwright:v1.28.0 npx -y playwright open google.com
# docker run --rm -e DISPLAY=:0 -v /tmp/.X11-unix:/tmp/.X11-unix mcr.microsoft.com/playwright:v1.28.0 npx -y playwright open google.com

#-------------------------
# DOCKER - STANDALONE GRID
#-------------------------
# docker run -d -p 4444:4444 --shm-size="2g" -e SE_NODE_GRID_URL="http://localhost:4444/wd/hub" selenium/standalone-chrome:4.3.0-20220726
# docker run -d -p 4444:4444 --shm-size="2g" -e SE_NODE_GRID_URL="http://localhost:4444/wd/hub" seleniarm/standalone-chromium:103.0
# cmd run:// SELENIUM_REMOTE_URL=http://localhost:4444/wd/hub mvn test

# docker run -p 80:80 -v /var/run/docker.sock:/tmp/docker.sock:ro --restart always --log-opt max-size=1g nginx --entrypoint=/bin/sh