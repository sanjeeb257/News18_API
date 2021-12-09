@News18API
Feature: Bengali : Validate Stories in all Sections by comparing Mobile Web and Mobile App
   
    @Bengali
    Scenario: Validate existing Top stories for Both Mobile Web and Mobile App 
    
    Given  User Launches the Mobile Web browser
    When User navigates to News18 mobile web "Bengali" home page
	Then Gets all top stories list in Bengali language
	And User hits News18 mobile app Bengali language api
	Then Compare the stories of Mobile Web and Mobile App in Bengali language
	
	@Bengali
    Scenario Outline: Validate existing Top stories for Both Mobile Web and Mobile App 

    Given User navigates to News18 mobile web "Bengali" home page
	When Gets all "<Section>" list in Bengali language
	Then User hits News18 mobile app Bengali language api for "<Section>" section
	And Compare the stories of Mobile Web and Mobile App in Bengali language
	
	Examples:
	|Section|
	|	স্থানীয়	|
	|	ছবি	|
	|	খেলা	|
	|	বিনোদন	|
	|	লাইফস্টাইল	|
	|	প্রযুক্তি	|
	|	ব্যবসা-বাণিজ্য|
	|	ক্রাইম	|
	|	বিদেশ	|
	|	ফিচার	|
	|	পাঁচমিশালি|
	|	ভিডিও	|
	|	শো	|
	
	
	@Bengali
	Scenario: Validate the sections present in Both Mobile Web and Mobile App 
	
    Given User navigates to News18 mobile web "Bengali" home page
	When Gets all sections list in Bengali language
	Then User hits News18 mobile app Bengali api for getting sections
	And Compare the sections in Mobile Web and Mobile App for Bengali language