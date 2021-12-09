@News18API
Feature: Assam : Validate Stories in all Sections by comparing Mobile Web and Mobile App

  @Assam
  Scenario: Validate existing Top stories for Both Mobile Web and Mobile App
  
  	Given User Launches the Mobile Web browser
    When User navigates to News18 mobile web "Assam" home page
    Then Gets all top stories list in Assam language
    And User hits News18 mobile app Assam language api
    Then Compare the stories of Mobile Web and Mobile App in Assam language

  @Assam
  Scenario Outline: Validate existing Top stories for Both Mobile Web and Mobile App
    Given User navigates to News18 mobile web "Assam" home page
    When Gets all "<Section>" list in Assam language
    And User hits News18 mobile app Assam language api for "<Section>" section
    Then Compare the stories of Mobile Web and Mobile App in Assam language

    Examples: 
      | Section   |
      | অসম       |
      | দেশ       |
      |ভিডিঅ   |
      | বিশ্ব     |
      | ফটো       |
      | জীৱন শৈলী |
 @Assam
  Scenario: Validate the sections present in Both Mobile Web and Mobile App
    Given User navigates to News18 mobile web "Assam" home page
    When Gets all sections list in Assam language
    And User hits News18 mobile app Assam api for getting sections
    Then Compare the sections in Mobile Web and Mobile App for Assam language