@echo off
echo Building Main Project (FastMemory)...
call mvn clean install -DskipTests -q
if %ERRORLEVEL% NEQ 0 (
    echo Main build failed.
    exit /b %ERRORLEVEL%
)

echo Building Benchmark Uber-JAR...
cd examples
call mvn clean package -DskipTests -q
if %ERRORLEVEL% NEQ 0 (
    echo Benchmark build failed.
    cd ..
    exit /b %ERRORLEVEL%
)

echo Running Official JMH Benchmarks...
java --add-opens=java.base/jdk.internal.misc=ALL-UNNAMED --add-exports=java.base/jdk.internal.misc=ALL-UNNAMED -jar target\benchmarks.jar -jvmArgs "-Xmx4g"

cd ..
