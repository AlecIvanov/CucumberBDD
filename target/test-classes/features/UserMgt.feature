@userMgt
Feature: User Management page tests
  Background: Open user registration page
    Given I open "http://automation.techleadacademy.io/#/usermgt"

#  @smoke
#  Scenario: Verify initially table is empty
#    Given I open home page
#    And I navigate to UserMgt page

  @registerUser
  Scenario: Register new user
    When I input "Kuba" as a "firstname"
    And I input "Z" as a "lastname"
    And I input "444-444-4444" as a "phone number"
    And I input "kuba@tla.com" as a "email"
    And I input "Instructor" as a "role"
    And I click a button "Submit"

  @verifyButton
  Scenario: Verify Login button
    When I click a button "login"
    Then I verify title is "Login Page"

  @verifyButton
  Scenario: Verify User Database button
    When I click a button "access db"
    Then I verify title is "User DB"

  @dataTable @smoke
  Scenario: Register new user using DataTable
    And I enter following data:
      | firstname    | Kuba         |
      | lastname     | Z            |
      | phone number | 444-444-4444 |
      | email        | kuba@tla.com |
      | role         | Instructor   |

    @typeRegistry
    Scenarion Type Registry exmple
      And Creates new users with following data;
      |firstName|lastName|phoneNumber|    email   |  role     |
      |Kuba     |Z       |444-444-444|kuba@tla.com| Instructor|
