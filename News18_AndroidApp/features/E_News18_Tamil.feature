@News18API
Feature: Tamil : Validate Stories in all Sections by comparing Mobile Web and Mobile App
   
      @Tamil
    Scenario: Validate existing Top stories for Both Mobile Web and Mobile App 
    
    Given User Launches the Mobile Web browser 
	When User navigates to News18 mobile web "Tamil" home page
	Then Gets all top stories list in Tamil language
	And User hits News18 mobile app Tamil language api
	Then Compare the stories of Mobile Web and Mobile App in Tamil language
	
	     @Tamil
    Scenario Outline: Validate existing Top stories for Both Mobile Web and Mobile App 

	Given User navigates to News18 mobile web "Tamil" home page
	When Gets all "<Section>" list in Tamil language
	And User hits News18 mobile app Tamil language api for "<Section>" section
	Then Compare the stories of Mobile Web and Mobile App in Tamil language
	
	Examples:
	|Section|
	|நியூஸ்18 ஸ்பெஷல்|
	|தமிழ்நாடு|
	|குற்றம்|
	|இந்தியா|
	|உலகம்|
	|வணிகம்|
	|பொழுதுபோக்கு|
	|விளையாட்டு|
	|லைஃப்ஸ்டைல்|
	|ஆட்டோமொபைல்ஸ்|
	|தொழில்நுட்பம்|
	|கல்வி|
	|வேலைவாய்ப்பு|
	|சிறப்புக் கட்டுரைகள்|
	|புகைப்படங்கள்|
	|காணொளி|

	 
		@Tamil
	Scenario: Validate the sections present in Both Mobile Web and Mobile App 
	Given User navigates to News18 mobile web "Tamil" home page
	When Gets all sections list in Tamil language
	And User hits News18 mobile app Tamil api for getting sections
	Then Compare the sections in Mobile Web and Mobile App for Tamil language