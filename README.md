# iDempiere FitNesse
The iDempiere FitNesse is a set of project that aims to easy the integration and use of the FitNesse by the iDempiere project fot automating tests and populating.

This project was started with the [IDEMPIERE-206](https://idempiere.atlassian.net/browse/IDEMPIERE-206) and was moved from core with the [IDEMPIERE-4138](https://idempiere.atlassian.net/browse/IDEMPIERE-4138).

## What is new ? 
* upgrade [FitnNesse](http://fitnesse.org/FrontPage) from v20111026 to v20200501 and [Selenium](https://www.selenium.dev/) from 2.42.2 to 3.141.59
  *  fork and small fixes on outdated projetct fitlibrary and fitlibraryweb for make it work with newest versions
* JIRA contributions integrated
  * [FitNesse Rollback and InfoWindow read/run process](https://idempiere.atlassian.net/browse/IDEMPIERE-2860)
  * [FitNesse Update Fixture now understands and evaluate variables on where clause](https://idempiere.atlassian.net/browse/IDEMPIERE-3827)
  * removed the ZTL project as it's discontinued. [Read more...](https://code.google.com/archive/p/zk-ztl/)
  * [Fixture and test examples for screenshots automation](https://idempiere.atlassian.net/browse/IDEMPIERE-4029)

## Projects:
* fitnesse - fitnesse server console
* org.idempiere.fitnesse.fixture - fitnesse fixture for idempiere
* org.idempiere.fitnesse.p2 - project to build p2 repository
* org.idempiere.fitnesse.parent - parent pom project
* org.idempiere.fitnesse.server - web server for fitnesse fixture
* org.idempiere.fitnesse-feature - main feature for P2
* org.idempiere.fitrecorder - recorder to help build fixture
* org.idempiere.ui.zk.selenium - selenium support for zk web client

## Usage

Here you can find some instructions FitNesse integration usage for development and deployment on already existing servers.

### Folder layout for development environment
* idempiere
* idempiere-fitnesse
  * org.idempiere.fitnesse.*
  * org.idempiere.fitrecorder
  * org.idempiere.ui.zk.selenium

### Deployment on existing servers
* at idempiere-fitnesse, run mvn verify 
* copy deploy-fitnesse.sh to your idempiere instance's root folder
* at your idempiere instance's root folder (for instance, /opt/idempiere), run ./deploy-fitnesse.sh <file or url path to org.idempiere.fitnesse.p2/target/repository>
* for e.g, if your source is at /ws/idempiere-fitnesse, ./deploy-fitnesse.sh file:////ws/idempiere-fitnesse/org.idempiere.fitnesse.p2/target/repository
* to uninstall, copy remove-fitnesse.sh to your idempiere instance's root folder. Run ./remove-fitnesse.sh to uninstall it
* if the bundle doesn't auto start after deployment (with STARTING status), at osgi console, run "sta org.idempiere.fitnesse.server" to activate the plugin

## TODO

* Test and create SLIM test cases 
* Create more test suites for core System and Garden World
* Integrate [ZATS](https://www.zkoss.org/product/zats) for ZK interface automated tests

## Find more information at:
* [Jira Tracker](https://idempiere.atlassian.net/secure/QuickSearch.jspa?searchString=fitnesse)
* [Google Groups](https://groups.google.com/g/idempiere/search?q=fitnesse)