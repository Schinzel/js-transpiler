package io.schinzel.jstranspiler

import io.github.bonigarcia.wdm.ChromeDriverManager
import io.schinzel.jstranspiler.example.WebServer
import io.schinzel.jstranspiler.example.dataclasses.dir1.Address
import io.schinzel.jstranspiler.example.dataclasses.dir1.Pet
import io.schinzel.jstranspiler.example.dataclasses.dir1.Species
import io.schinzel.jstranspiler.example.dataclasses.dir2.Person
import io.schinzel.jstranspiler.example.dataclasses.dir2.Trait
import io.schinzel.jstranspiler.example.generateJavaScript
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
import java.time.Instant

/**
 * Purpose of this file is ...
 */
class ExamplePageTest {
    //Used to find elements on HTML page
    lateinit var webDriver: WebDriver
        private set
    lateinit var webServer: WebServer
        private set

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
        private const val URL = "http://localhost:5555/"
        private const val TIMEOUT_IN_SECONDS = 60L


        /**
         * Start web server and init Chrome driver
         */
        @BeforeClass
        @JvmStatic
        fun beforeClass() {
            generateJavaScript()
            // Setup chrome WebDriver
            ChromeDriverManager.chromedriver().setup()
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
                    nicknames, luckyNumbers, pets, traits, Instant.now())
        }

    }

    private fun clickButton(buttonId: String) =
            webDriver
                    .findElement(By.id(buttonId))
                    .click()


    private fun clickButtonAndWaitFor(buttonId: String, className: String) {
        //Get number of elements with argument name
        val initialNumberOfElements: Int = webDriver
                .findElements(By.className(className))
                .size
        clickButton(buttonId)
        val numberOfElementsToWaitFor: Int = initialNumberOfElements + 1
        //Wait for the number of elements with argument name to be one more
        WebDriverWait(webDriver, TIMEOUT_IN_SECONDS)
                .until(ExpectedConditions.numberOfElementsToBe(By.className(className), numberOfElementsToWaitFor))
    }


    @Test
    fun petCount_GetPetFromServerAddPetSendPetBackToServer_OneMoreThanInitially() {
        //Get the number of pets of person on server
        val initialNumberOfPets = webServer.api.person.pets.size
        //Load page
        webDriver.navigate().to(URL)
        //Click button to get person from server
        clickButtonAndWaitFor("buttonGetPerson", "selenium_got_person")
        //Click button to add pet
        clickButton("buttonAddPet")
        //Wait for response from server
        clickButtonAndWaitFor("buttonSendPerson", "selenium_sent_person")
        //Get the number of pets of person on server
        val currentNumberOfPets = webServer.api.person.pets.size
        //Check that the number of pets has increased by one
        assertThat(currentNumberOfPets).isEqualTo(initialNumberOfPets + 1)
    }

}