
REM
REM Install autumo libs in local maven repository
REM

REM Vars
set COMMONS_VERSION=1.1.0
set IFACEX_VERSION=2.1.0
set BEETROOT_VERSION=3.1.3

REM Install
mvn install:install-file -Dfile=lib/autumo-commons-%COMMONS_VERSION%.jar -DgroupId=ch.autumo.commons -DartifactId=autumo-commons -Dversion=%COMMONS_VERSION% -Dpackaging=jar
mvn install:install-file -Dfile=lib/autumo-ifacex-%IFACEX_VERSION%.jar -DgroupId=ch.autumo.ifacex -DartifactId=autumo-ifacex -Dversion=%IFACEX_VERSION% -Dpackaging=jar
mvn install:install-file -Dfile=lib/autumo-beetroot-%BEETROOT_VERSION%.jar -DgroupId=ch.autumo.beetroot -DartifactId=autumo-beetroot -Dversion=%BEETROOT_VERSION% -Dpackaging=jar

