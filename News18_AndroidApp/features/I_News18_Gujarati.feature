@News18API
Feature: Gujarati : Validate Stories in all Sections by comparing Mobile Web and Mobile App
   
      @Gujarati
    Scenario: Validate existing Top stories for Both Mobile Web and Mobile App 
    
	Given User Launches the Mobile Web browser
	When User navigates to News18 mobile web "Gujarati" home page
	Then Gets all top stories list in Gujarati language
	And User hits News18 mobile app Gujarati language api
	Then Compare the stories of Mobile Web and Mobile App in Gujarati language
	
	     @Gujarati
    Scenario Outline: Validate existing Top stories for Both Mobile Web and Mobile App 

	Given User navigates to News18 mobile web "Gujarati" home page
	When Gets all "<Section>" list in Gujarati language
	And User hits News18 mobile app Gujarati language api for "<Section>" section
	Then Compare the stories of Mobile Web and Mobile App in Gujarati language
	
	Examples:
	|Section|
	|ટ્રેન્ડીંગ|
	|ગુજરાત|
	|દેશવિદેશ|
	|મનોરંજન|
	|અજબગજબ|
	|વેપાર|
	|લાઇફ સ્ટાઇલ|
	|મોબાઇલ એન્ડ ટેક|	
	|ફોટો|
	|વીડિયો|
	|રમત-જગત|
	|ક્રાઇમ|
	|ધર્મભક્તિ|
	 
		@Gujarati
	Scenario: Validate the sections present in Both Mobile Web and Mobile App 
	Given User navigates to News18 mobile web "Gujarati" home page
	When Gets all sections list in Gujarati language
	And User hits News18 mobile app Gujarati api for getting sections
	Then Compare the sections in Mobile Web and Mobile App for Gujarati language