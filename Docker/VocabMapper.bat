@echo off
REM --------------------------------------------------------------
REM - Script to start MY SQL Service
REM --------------------------------------------------------------
if not defined DOCKER_ENV_LOCATION set DOCKER_ENV_LOCATION=config

REM Copy the environment file for docker to resolve
copy %DOCKER_ENV_LOCATION%\ENV.env .env > NUL

REM Now run Docker Compose for DB Service
docker-compose -f MySQLDocker.yml %*

REM Delay for DB to be up, for 5 minutes
timeout 300 /nobreak

REM Now run Docker Compose for API Service
docker-compose -f VocabMapper.yml %*