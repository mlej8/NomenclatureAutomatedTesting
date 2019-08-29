# NomenclatureAutomatedTesting
Test Automation Framework created during my summer internship 2019

Web Application under test: https://www.nomenclature.info/

## Framework Definitions
An automation framework consists of the integration tools, libraries, and utilities needed to interact with the application under test.

A FRAMEWORK is set of protocols, rules, standards and guidelines incorporated to organize your code/project in order to:
- Increase code re-usability
- Ease of script maintenance
- Better code readability
- Expandable test suite

## Nomenclature Automation Framework
This Test Automation Framework implements the Page Object Model (Design Pattern) using Page Factory. 
The idea is to represent each individual page of the application using a Java class. The latter contains WebElement locators, also called OR (Object repository -- a collection of all WebElements), and methods for each feature of that particular page.
In order to support the Page Object pattern, Selenium's library contains a factory class called "Page Factory". 
This class contains a method .initElement() to initialize all WebElements a Page Object.

### Separating Tests and Framework
Clean code is essential to the success of an automation framework.
Neutralizing the framework, and separating it from the tests, is the first step to ensuring a clean design.
Using design patterns such as the page object model allow for easy maintenance and extension. 

### Project Architecture
Tests > Framework > Selenium > Web application
The TEST interact with the FRAMEWORK which interacts with SELENIUM. Finally, Selenium interacts with the WEB APPLICATION.
The tests are not directly interacting with the web application. All WebElement locators (ids, xPath, cssSelectors, etc.) are containing inside the Framework.
If all the tests reference the framework, and the framework knows about your application, if your application changes, all thoses tests don't have to change, you simply need to update HTML Elements locators i.e. id or xPath field for a WebElement.

Every Layer is grouped in a PACKAGE (base, pages, tests, config, util, report, driver)
All the pages are interconnected to each other, they are often landing page of another page.
Page Object Model is also called Page Chaining Model, because each of the pages are interconnected to each other.

#### Layer 1: Base Layer (TestBase.java)
This class is the parent class of all layers (PageLibrary and TestNG) in the Framework. All the classes in Page and Test Packages extend TestBase.java
TestBase.java contains all the initialization steps i.e. the WebDriver, configurations (properties), implicit/explicit waits, pageLoadTimeOut, actions, maximize_window(), deleteAllCookies(), get(url), etc.
This class define the common (repeated) properties/methods ONCE which will be used by all of your pages and your test classes. 
In fact, every class in the framework needs to set up a WebDriver, implicit/explicit wait, maximize_window(), get(url), etc.
We use the concept of INHERITANCE to pass all those methods/properties from the BASE CLASS to all the CHILD CLASSES.
The same WebDriver, waits, actions, properties file will be shared among all pages, tests and utilities (code reused at its full potential).

THIS LAYER IS USED TO AVOID DUPLICATE CODE (i.e. avoid WebDriver driver = new ChromeDriver in EVERY PAGE).

#### Layer 2: Page Layer (PageLibrary)
For UI applications, you need classes that interact with the pages of the system.
These classes live within the Framework PAGE LIBRARY LAYER.

It is IMPORTANT to operate at HIGH LEVEL OF ABSTRACTION: Login(), ResetPassword() and ToggleRememberMe() NOT InputUsername(), InputPassword(), etc.
The functions should represent the FUNCTIONALITY of the page from the user's perspective.
The tests are going to use those functions

1. Create a SEPARATE JAVA CLASS for every page in the web application
2. Define WEBELEMENT for each page (i.e. submit button, login button, search box, etc.) also called OR -- Object repository, collection of all WebElements
3. Define METHODS for each feature of that particular page (i.e. register, login, forgot password, etc.)

#### Layer 3: Test Layer (TestNG)
1. Write a TEST CLASS for EVERY PAGE

#### Layer 4: Config.properties class (Environment variables)
Storing test URLs
Username and password
Proxy Configurations
Browser
Common properties

#### Layer 5: Utility classes
- screenshot()
- sendMail()
- Link Verifier
- StringProcessor

#### Layer 6: Test Report 
- HTML reports
- TestNG reports
- XML Reports
- Reporter
- BrowsePage Reporter
- SearchPage Reporter 

### Framework Architecture
Workflows > Pages > Navigation/UI Utilities > Selenium

Example of Workflows: In a E-Commerce website, customer orders an item (Go to this page > add to cart > go to checkout page).
                      Instead of having multiple, small tests, its better to have a workflow called .customerOrdersItem()

Workflows are built on top of your pages, might be actions that require to interact with multiple pages. 
Pages represent every individual page of the Web App.
Navigation/UI Utilities class to make it easier to navigate around your app

## TestNG Plugin
Download TestNG plugin for Eclipse: http://beust.com/eclipse/

## Features
- Screenshots
- Extent Reports
- Customized Reporter
- Link Verifier

## Built With
 * Selenium 
    - Page Object Model using Page Factory
 * TestNG
 * Maven
 * Java
 * SVN
 * Jenkins
 * xPath 

 ## Authors

* **Michael Li**
