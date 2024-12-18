FROM node:23-alpine AS builder
WORKDIR /app
COPY . .
RUN npm i
RUN npm run build


FROM node:23.4.0-alpine
WORKDIR /app
COPY --from=builder /app/.next /app/.next
COPY --from=builder /app/package.json /app/package.json
RUN npm install --omit=dev

EXPOSE 3000

ENTRYPOINT [ "npm", "start" ]
