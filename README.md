
# iDempiere Fitnesse Integration

## Projects:
* fitnesse - fitnesse server console
* org.idempiere.fitnesse.fixture - fitnesse fixture for idempiere
* org.idempiere.fitnesse.p2 - project to build p2 repository
* org.idempiere.fitnesse.parent - parent pom project
* org.idempiere.fitnesse.server - web server for fitnesse fixture
* org.idempiere.fitnesse-feature project
* org.idempiere.fitrecorder - recorder to help build fixture
* org.idempiere.ui.zk.selenium - selenium support for zk web client

## Folder layout:
* idempiere
* idempiere-fitnesse
  * org.idempiere.fitnesse.*
  * org.idempiere.fitrecorder
  * org.idempiere.ui.zk.selenium

## Deployment
* at idempiere-fitnesse, run mvn verify 
* copy deploy-fitnesse.sh to your idempiere instance's root folder
* at your idempiere instance's root folder (for instance, /opt/idempiere), run ./deploy-fitnesse.sh <file or url path to org.idempiere.fitnesse.p2/target/repository>
* for e.g, if your source is at /ws/idempiere-fitnesse, ./deploy-fitnesse.sh file:////ws/idempiere-fitnesse/org.idempiere.fitnesse.p2/target/repository
* to uninstall, copy remove-fitnesse.sh to your idempiere instance's root folder. Run ./remove-fitnesse.sh to uninstall it
* if the bundle doesn't auto start after deployment (with STARTING status), at osgi console, run "sta org.idempiere.fitnesse.server" to activate the plugin
