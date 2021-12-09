@News18API
Feature: Telugu : Validate Stories in all Sections by comparing Mobile Web and Mobile App 

@Telugu 
Scenario: Validate existing Top stories for Both Mobile Web and Mobile App 

	Given User Launches the Mobile Web browser 
	When User navigates to News18 mobile web "Telugu" home page 
	Then Gets all top stories list in Telugu language 
	And User hits News18 mobile app Telugu language api 
	Then Compare the stories of Mobile Web and Mobile App in Telugu language 
@Telugu 
Scenario Outline: Validate existing Top stories for Both Mobile Web and Mobile App 
	Given User navigates to News18 mobile web "Telugu" home page 
	When Gets all "<Section>" list in Telugu language
	And User hits News18 mobile app Telugu language api for "<Section>" section
	Then Compare the stories of Mobile Web and Mobile App in Telugu language
	
	Examples:
	| Section | 
		|	అగ్ర కథనాలు	|
		|	తెలంగాణ	|
		|	ఆంధ్రప్రదేశ్	|
		|	సినిమా	|
		|	రాజకీయం	|
		|	క్రైమ్		|
		|	జాతీయం	|
		|	క్రీడలు	|
		|	బిజినెస్	|
		|	జాబ్స్		|
		|	టెక్నాలజి	|
		|	లైఫ్ స్టైల్	|
		|	అంతర్జాతీయం|
		|	ఫోటోలు	|
		|	వీడియోలు	|
      
 @Telugu
  Scenario: Validate the sections present in Both Mobile Web and Mobile App
    Given User navigates to News18 mobile web "Telugu" home page
    When Gets all sections list in Telugu language
    And User hits News18 mobile app Telugu api for getting sections
    Then Compare the sections in Mobile Web and Mobile App for Telugu language
    