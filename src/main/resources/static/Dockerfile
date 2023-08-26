FROM node:20-alpine3.18
WORKDIR /project
COPY ./public /project/public
COPY ./project /project
EXPOSE 8081
CMD npm i nodemon; npm install express; node index.js