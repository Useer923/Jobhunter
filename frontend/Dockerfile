# Étape 1 — Build Angular
FROM node:22-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

# Étape 2 — Serveur Nginx pour servir Angular
FROM nginx:alpine
COPY --from=build /app/dist/jobhunter-front/browser /usr/share/nginx/html
EXPOSE 80