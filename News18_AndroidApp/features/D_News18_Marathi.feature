@News18API
Feature: Marathi : Validate Stories in all Sections by comparing Mobile Web and Mobile App
   
      @Marathi
    Scenario: Validate existing Top stories for Both Mobile Web and Mobile App 
    
    Given User Launches the Mobile Web browser 
	When User navigates to News18 mobile web "Marathi" home page
	Then Gets all top stories list in Marathi language
	And User hits News18 mobile app Marathi language api
	Then Compare the stories of Mobile Web and Mobile App in Marathi language
	
	  @Marathi
    Scenario Outline: Validate existing Top stories for Both Mobile Web and Mobile App 

	Given User navigates to News18 mobile web "Marathi" home page
	When Gets all "<Section>" list in Marathi language
	And User hits News18 mobile app Marathi language api for "<Section>" section
	Then Compare the stories of Mobile Web and Mobile App in Marathi language
	
	Examples:
	|Section|
	|मनोरंजन|
	|महाराष्ट्र|
	|स्पोर्टस|
	|मनी|
	|टेक्नोलाॅजी|
	|लाईफस्टाईल|
	|व्हिडिओ|
	|फोटो|
	
	  @Marathi
	Scenario: Validate the sections present in Both Mobile Web and Mobile App 
	Given User navigates to News18 mobile web "Marathi" home page
	When Gets all sections list in Marathi language
	And User hits News18 mobile app Marathi api for getting sections
	Then Compare the sections in Mobile Web and Mobile App for Marathi language