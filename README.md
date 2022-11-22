# iDempiere FitNesse
The iDempiere FitNesse is a set of project that aims to easy the integration and use of the FitNesse by the iDempiere project fot automating tests and populating.

This project was started with the [IDEMPIERE-206](https://idempiere.atlassian.net/browse/IDEMPIERE-206) and was moved from core with the [IDEMPIERE-4138](https://idempiere.atlassian.net/browse/IDEMPIERE-4138).

## What is new ?
* upgrade [FitnNesse](http://fitnesse.org/FrontPage) from v20200501 to v20221102 and [Selenium](https://www.selenium.dev/) from 3.141.59 to 4.6.0
  *  own fork of [FitLibrary](https://github.com/muriloht/fitlibrary-fitlibrary) and [FitLibraryWeb](https://github.com/muriloht/fitlibrary-fitlibraryweb)  projects for compatibilization with newest versions
* JIRA contributions integrated
  * [FitNesse Rollback and InfoWindow read/run process](https://idempiere.atlassian.net/browse/IDEMPIERE-2860)
  * [FitNesse Update Fixture now understands and evaluate variables on where clause](https://idempiere.atlassian.net/browse/IDEMPIERE-3827)
  * removed the ZTL project as it's discontinued. [Read more...](https://code.google.com/archive/p/zk-ztl/)
  * [Fixture and test examples for screenshots automation](https://idempiere.atlassian.net/browse/IDEMPIERE-4029)
* branches reorganization
  * currently the project is supported only for usage with the iDempiere development version **>= 10 ** - for that you should use the **master** branch
  * for earlier versions (use at your own risk):
    * if you want to use the iDempiere Fitnesse project with an iDempiere version **<= 7.1** try the **release-7.1** branch
    * if you want to use the iDempiere Fitnesse project with an iDempiere version **= 9** try the **release-9** branch

  

## Projects:
* fitnesse - fitnesse server console
* org.idempiere.fitnesse.fixture - fitnesse fixture for idempiere
* org.idempiere.fitnesse.p2 - project to build p2 repository
* org.idempiere.fitnesse.parent - parent pom project
* org.idempiere.fitnesse.server - web server for fitnesse fixture
* org.idempiere.fitnesse-feature - main feature for P2
* org.idempiere.fitrecorder - recorder to help build fixture. [read more...](https://wiki.idempiere.org/en/Fitnesse_Recorder)
* org.idempiere.ui.zk.selenium - selenium support for zk web client
* selenese - example test cases exported from Selenium IDE ( JUnit4 + WebDriver ).[Read more...](https://wiki.idempiere.org/en/Selenium)

## Usage

Here you can find some instructions FitNesse integration usage for development and deployment on already existing servers.

### Development Environment
 You need to configure your [development environment](https://wiki.idempiere.org/en/Install_Development_Prerequisites) and then import the iDempiere Fitnesse project on your workspace taking care about the following folder structure:

* idempiere
* idempiere-fitnesse
  * org.idempiere.fitnesse.*
  * org.idempiere.fitrecorder
  * org.idempiere.ui.zk.selenium

Note that you will need select and start the fitnesse and selenium plugins when launching the server with **server.product.launch**  or **server.product.functionaltest.launch**.

For more instructions you can check [this page](https://wiki.idempiere.org/en/Fitnesse.HowTo)

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
