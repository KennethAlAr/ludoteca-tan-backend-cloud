@echo off

echo Iniciando Eureka...
start "EUREKA" cmd /k "cd /d ""%~dp0tutorialeureka"" && call mvnw spring-boot:run"

timeout /t 20 /nobreak

echo Iniciando Gateway...
start "GATEWAY" cmd /k "cd /d ""%~dp0tutorialgateway"" && call mvnw spring-boot:run"

timeout /t 10 /nobreak

echo Iniciando Category...
start "CATEGORY" cmd /k "cd /d ""%~dp0tutorialcategory"" && call mvnw spring-boot:run"

timeout /t 5 /nobreak

echo Iniciando Author...
start "AUTHOR" cmd /k "cd /d ""%~dp0tutorialauthor"" && call mvnw spring-boot:run"

timeout /t 5 /nobreak

echo Iniciando Client...
start "CLIENT" cmd /k "cd /d ""%~dp0tutorialclient"" && call mvnw spring-boot:run"

timeout /t 5 /nobreak

echo Iniciando Game...
start "GAME" cmd /k "cd /d ""%~dp0tutorialgame"" && call mvnw spring-boot:run"

timeout /t 5 /nobreak

echo Iniciando Reservation...
start "RESERVATION" cmd /k "cd /d ""%~dp0tutorialreservation"" && call mvnw spring-boot:run"

timeout /t 5 /nobreak

echo Iniciando Login...
start "LOGIN" cmd /k "cd /d ""%~dp0tutoriallogin"" && call mvnw spring-boot:run"

echo.
echo Todos los servicios han sido lanzados.
pause