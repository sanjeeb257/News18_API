@News18API
Feature: Hindi : Validate Stories in all Sections by comparing Mobile Web and Mobile App
   
      @Hindi
    Scenario: Validate existing Top stories for Both Mobile Web and Mobile App 
    
    Given User Launches the Mobile Web browser
	When User navigates to News18 mobile web "Hindi" home page
	Then Gets all top stories list in Hindi language
	And User hits News18 mobile app Hindi language api
	Then Compare the stories of Mobile Web and Mobile App in Hindi language
	
	      @Hindi
    Scenario Outline: Validate existing Top stories for Both Mobile Web and Mobile App 

	Given User navigates to News18 mobile web "Hindi" home page
	When Gets all "<Section>" list in Hindi language
	And User hits News18 mobile app Hindi language api for "<Section>" section
	Then Compare the stories of Mobile Web and Mobile App in Hindi language
	
	Examples:
	|Section|
	|वीडियो|
	|लाइफ|
	|मोबाइल-टेक|
	|मनी|
	|क्रिकेट|
	|क्राइम|
	|करियर & जॉब्स|
	|नॉलेज|	
	|दुनिया|
	|मनोरंजन|
	|फोटो|
	
		@Hindi
	Scenario: Validate the sections present in Both Mobile Web and Mobile App 
	Given User navigates to News18 mobile web "Hindi" home page
	When Gets all sections list in Hindi language
	And User hits News18 mobile app Hindi api for getting sections
	Then Compare the sections in Mobile Web and Mobile App for Hindi language