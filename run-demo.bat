@echo off
setlocal enabledelayedexpansion

echo ====================================
echo Building and Running FastMemory Demo
echo ====================================

call mvn clean package -DskipTests
if errorlevel 1 (
    echo Build failed!
    exit /b 1
)

mkdir bin 2>nul
javac --add-exports=java.base/jdk.internal.misc=ALL-UNNAMED -cp "target/FastMemory-0.1.0.jar;..\FastPointer\target\FastPointer-0.1.0.jar;..\FastCore\target\classes" -d bin examples/Demo.java
if errorlevel 1 (
    echo Demo compilation failed!
    exit /b 1
)

echo.
echo Running Demo...
java --add-opens=java.base/jdk.internal.misc=ALL-UNNAMED --add-exports=java.base/jdk.internal.misc=ALL-UNNAMED -cp "bin;target/FastMemory-0.1.0.jar;..\FastPointer\target\FastPointer-0.1.0.jar;..\FastCore\target\classes" examples.Demo
