@News18API
Feature: Odia : Validate Stories in all Sections by comparing Mobile Web and Mobile App

  @Odia
  Scenario: Validate existing Top stories for Both Mobile Web and Mobile App
  
  	Given User Launches the Mobile Web browser 
    When User navigates to News18 mobile web "Odia" home page
    Then Gets all top stories list in Odia language
    And User hits News18 mobile app Odia language api
    Then Compare the stories of Mobile Web and Mobile App in Odia language

  @Odia
  Scenario Outline: Validate existing sections for Both Mobile Web and Mobile App
    Given User navigates to News18 mobile web "Odia" home page
    When Gets all "<Section>" list in Odia language
    And User hits News18 mobile app Odia language api for "<Section>" section
    Then Compare the stories of Mobile Web and Mobile App in Odia language

    Examples: 
      | Section       |
      | ନ୍ୟୁଜ୍        |
      | ଟ୍ରେଣ୍ଡିଙ୍ଗ୍‌ |
      |ଫଟୋ ଗ୍ୟାଲେରୀ|
      |ଭିଡିଓ|

  @Odia
  Scenario: Validate the sections present in Both Mobile Web and Mobile App
    Given User navigates to News18 mobile web "Odia" home page
    When Gets all sections list in Odia language
    And User hits News18 mobile app Odia api for getting sections
    Then Compare the sections in Mobile Web and Mobile App for Odia language
