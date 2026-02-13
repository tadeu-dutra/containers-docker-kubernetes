# Consider as base the latest stable version of the Debian image
FROM debian:trixie-slim

# Run Debian commands to update the apt-get repository, upgrade installed packages and get apache2 installed
RUN apt-get update && \
	apt-get upgrade && \
	apt-get install -y apache2

# Run apachectl command everytime the container is started
CMD ["apachectl", "-D", "FOREGROUND"]

# Expose port 80 to the outside world via -d docker run parameter
EXPOSE 80
