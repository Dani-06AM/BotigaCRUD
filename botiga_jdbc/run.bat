@echo off
cd /d "%~dp0"
java -cp ".;lib/h2-2.2.224.jar" App
pause