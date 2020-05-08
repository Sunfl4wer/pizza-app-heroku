FROM node:12.13.0-alpine as builder

RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

ENV PATH /usr/src/app/node_modules/.bin:$PATH

COPY package.json /usr/src/app/package.json

RUN npm install --silent
RUN npm install react-scripts@3.4.1 -g --silent

COPY ./package-lock.json /usr/src/app/
COPY ./public /usr/src/app/public
COPY ./src /usr/src/app/src

RUN npm run build

# production environment
FROM nginx:1.15.10-alpine
COPY --from=builder /usr/src/app/build /usr/share/nginx/html
RUN rm /etc/nginx/conf.d/default.conf
COPY config/nginx.conf /etc/nginx/conf.d/nginx.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]