package io.schinzel.jstranspiler

import io.github.bonigarcia.wdm.WebDriverManager
import io.schinzel.jstranspiler.example.WebServer
import io.schinzel.jstranspiler.example.dataclasses.dir1.Address
import io.schinzel.jstranspiler.example.dataclasses.dir1.Pet
import io.schinzel.jstranspiler.example.dataclasses.dir1.Species
import io.schinzel.jstranspiler.example.dataclasses.dir2.Person
import io.schinzel.jstranspiler.example.dataclasses.dir2.Trait
import io.schinzel.jstranspiler.example.generateJavaScriptClasses
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.openqa.selenium.By
import org.openqa.selenium.Dimension
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit

/**
 * This test:
 * - transpiles a set of Kotlin classes to JavaScript classes
 * - starts a web server
 * - loads an html-page
 * - the html-page requests a Person object from the server. The server serializes the Person kotlin object,
 * sends it to the client. The client de-serializes and recreates the Person as a transpiled JavaScript-object.
 * object.
 * - the html-page adds a pet to the Person object on the client, serializes and sends it to the server
 * - the server de-serializes and recreates the Person as a Kotlin object
 * - the test verifies that the number of pets of the person on the server has increased by one
 */
class ExamplePageTest {
    private lateinit var webDriver: WebDriver
    private lateinit var webServer: WebServer

    @Before
    fun before() {
        val chromeOptions = ChromeOptions()
        @Suppress("ConstantConditionIf")
        if (HEADLESS_CHROME) {
            chromeOptions.addArguments("--headless", "--disable-gpu", "--window-size=1024,2240")
        }
        webDriver = ChromeDriver(chromeOptions)
        webDriver.manage().window().size = Dimension(1024, 4240)
        //Start web server
        webServer = WebServer(generatePerson()).start()
    }


    /**
     * Quit webDriver between every test
     */
    @After
    fun after() {
        webDriver.quit()
    }


    companion object {
        //Flag that determines if Chrome will run headless (not visible on screen) or not
        private const val HEADLESS_CHROME: Boolean = false


        /**
         * Start web server and init Chrome driver
         */
        @BeforeClass
        @JvmStatic
        fun beforeClass() {
            // Generate js classes from Kotlin. These will be used in the test
            generateJavaScriptClasses()
            // Setup chrome WebDriver
            WebDriverManager.chromedriver().setup()
        }


        fun generatePerson(): Person {
            val address = Address("Funky street 25", "12345", "Malmö", "Sweden")
            val nicknames = listOf("Svenne", "Henke")
            val luckyNumbers = listOf(5, 25)
            val pet1 = Pet("Rufus", Species.DOG)
            val pet2 = Pet("Tiger", Species.CAT)
            val pets = listOf(pet1, pet2)
            val traits = listOf(Trait.KIND, Trait.LOYAL)
            return Person("Henrik", "Svensson", 25,
                    250000, 1.89f, true, address,
                    nicknames, luckyNumbers, pets, traits, Instant.now(), 110)
        }

    }

    /**
     * Click on the html button with the argument id
     */
    private fun clickButton(buttonId: String) =
            webDriver
                    .findElement(By.id(buttonId))
                    .click()


    /**
     * Click on the html button with the argument id and wait for the argument class name to appear.
     * Or to be more exact, wait for the number of elements with the argument class name to
     * increase by one.
     */
    private fun clickButtonAndWaitFor(buttonId: String, classNameToWaitFor: String) {
        //Get number of elements with argument name
        val initialNumberOfElements: Int = webDriver
                .findElements(By.className(classNameToWaitFor))
                .size
        clickButton(buttonId)
        val numberOfElementsToWaitFor: Int = initialNumberOfElements + 1
        val timeOutInSeconds = 60L
        //Wait for the number of elements with argument name to be one more
        val timeout = Duration.of(timeOutInSeconds, ChronoUnit.SECONDS)
        WebDriverWait(webDriver, timeout)
                .until(ExpectedConditions.numberOfElementsToBe(By.className(classNameToWaitFor), numberOfElementsToWaitFor))
    }


    @Test
    fun petCount_GetPetFromServerAddPetSendPetBackToServer_OneMoreThanInitially() {
        //Get the number of pets of person on server
        val initialNumberOfPets = webServer.api.person.pets.size
        //Load page
        webDriver.navigate().to("http://localhost:5555/")
        //Click button to get person from server
        clickButtonAndWaitFor("buttonGetPerson", "selenium_got_person")
        //Click button to add pet to the person on the client
        clickButton("buttonAddPet")
        //Send person to server
        clickButtonAndWaitFor("buttonSendPerson", "selenium_sent_person")
        //Get the number of pets of person on server
        val currentNumberOfPets = webServer.api.person.pets.size
        //Check that the number of pets has increased by one
        assertThat(currentNumberOfPets).isEqualTo(initialNumberOfPets + 1)
    }

}