@News18API
Feature: Malayalam : Validate Stories in all Sections by comparing Mobile Web and Mobile App
   
      @Malayalam
    Scenario: Validate existing Top stories for Both Mobile Web and Mobile App 
  
  	Given User Launches the Mobile Web browser  
	When User navigates to News18 mobile web "Malayalam" home page
	Then Gets all top stories list in Malayalam language
	And User hits News18 mobile app Malayalam language api
	Then Compare the stories of Mobile Web and Mobile App in Malayalam language
	
	    @Malayalam
    Scenario Outline: Validate existing Top stories for Both Mobile Web and Mobile App 

	Given User navigates to News18 mobile web "Malayalam" home page
	When Gets all "<Section>" list in Malayalam language
	And User hits News18 mobile app Malayalam language api for "<Section>" section
	Then Compare the stories of Mobile Web and Mobile App in Malayalam language
	
	Examples:
	|Section|
	|Film|
	|Buzz|
	|Photo Gallery|
	|VIDEOS|
	|Kerala|
	|Money|
	|Life|
	|Sports|
	|Crime|
	
		@Malayalam
	Scenario: Validate the sections present in Both Mobile Web and Mobile App 
	Given User navigates to News18 mobile web "Malayalam" home page
	When Gets all sections list in Malayalam language
	And User hits News18 mobile app Malayalam api for getting sections
	Then Compare the sections in Mobile Web and Mobile App for Malayalam language