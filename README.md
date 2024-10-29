To build:
docker build . -t webseek/backend

To run:
docker run -e BASE_URL=<url> -p 4567:4567 --rm webseek/backend