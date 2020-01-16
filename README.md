# Upnorway Partner App Automated Tests

Steps to run the test:
----------------------
1) Install mvn
2) Clone the project
3) Go to root of hte project directory
4) Open command prompt and execute: mvn test
5) Find the execution results in: target/surefire-reports/html/extent.html#!

PS: If report contents are not showing properly,

a) Type 'about:config' in address bar and search for property called 'security.csp.enable'.

b) Set above property ('security.csp.enable') value to false.

c) Refresh page.

Important locations:
--------------------
Test data:
src/test/resources/excel/testdata.xlsx

Test site URL:
src/test/resources/properties/Config.properties

Database credentials:
src/test/resources/properties/Config.properties

Databse script to delete testdata:
src/test/resources/properties/db-delete-test-automation-records.sql

XPATHs of elements:
src/test/resources/properties/OR.properties


Test report document configuration:
src/test/resources/extentconfig/ReportsConfig.xml
