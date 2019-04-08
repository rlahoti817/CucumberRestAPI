Feature: Parsing the JSON response from RestAPI

Scenario: Calling the RestAPI through POST request type 
Given Application calls the RestFul API URL
When Application sends the resource with API call
Then JSON Response is tested for the given validation
And validation is done on the response object