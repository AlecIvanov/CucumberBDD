package step_definitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import step_impl.HomePageImpl;
import utils.Driver;

public class HomePageTest extends HomePageImpl {
    @Given("User opens home page")
    public void user_opens_home_page() {
        Driver.getDriver().get("http//automation.techleadacademy.io");

    }
    @Then("User verifis title is TLA Automation")
    public void user_verifies_title_is_tla_automation() {
        Assert.assertEquals("TLA Automation", Driver.getDriver().getTitle());
    }

    @And("User navigate to UserMgt page")
    public void userNavigateToUserMgtPage() {

    }
}
