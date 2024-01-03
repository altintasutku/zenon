# Zenon
Zenon is a modern and simple open source java library for HTTP requests

# Installation
For maven package manager add this dependency:

    <dependency>
	  <groupId>org.altintas</groupId>
	  <artifactId>zenon</artifactId>
	  <version>0.1-BETA</version>
	</dependency>

if you are using maven cli run this command

    $ mvn install

## Getting Started

First, create a request

    Request request = Zenon.createRequest("http://example.com", RequestType.GET);
then execute with

    request.send()
Voilà! That's all you sent a request.

## Handle Response

First way to handle response is creating a response variable that is returning from send method

    Result result = request.send();
Result's class structure:

    class Result{
	    public boolean isSuccess(); //Returns is request success
	    public int getStatusCode(); //Returns status code, example:200,404
	    public String getData(); //Returns response as string
	    public JSONObject getJson(); //Returns response as json
	    public JSONArray getJsonArray(); //If your response a array use this instead of using 'getJson()'
	    public Exception getError(); //If there is a error while sending request it returns error
	    public HttpURLConnection getHttpConnection(); //For advanced usage
    }
   Second way to handle response is using 'OnCompleteListener'
  
  Call 'send' method with a lambda function
   

    request.send(result -> {  
     saveAllToDatabase(result.getJsonArray())
     });

## Post / Put / Delete
Use like get request but just change RequestType

    Zenon.createRequest("...", RequestType.POST);
    Zenon.createRequest("...", RequestType.PUT);
    Zenon.createRequest("...", RequestType.DELETE);
