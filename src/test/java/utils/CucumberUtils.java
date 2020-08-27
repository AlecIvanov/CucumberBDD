package utils;

import io.cucumber.java.Scenario;
import pages.CommonPage;
import step_definitions.Hooks;

public class CucumberUtils {
    public  static void logInfo(String msg, boolean takeScreenShot){
        Scenario scenario = Hooks.currentScenario;
        scenario.log(CommonPage.CURRENT_DATETIME + " INFO: " + msg);
        if (takeScreenShot == true){
            Screenshot.takeScreenshot();
        }

    }
}
