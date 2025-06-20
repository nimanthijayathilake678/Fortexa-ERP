@echo off
echo =====================================
echo Starting Docker Compose...
echo =====================================

REM Run docker-compose and capture error
docker-compose up -d
IF %ERRORLEVEL% NEQ 0 (
    echo.
    echo ❌ ERROR: Docker Compose failed with exit code %ERRORLEVEL%.
    echo Please check your docker-compose.yml file and Docker daemon.
    echo.
) ELSE (
    echo.
    echo ✅ Docker Compose started successfully.
)

echo =====================================
echo Press any key to exit...
echo =====================================
pause >nul
