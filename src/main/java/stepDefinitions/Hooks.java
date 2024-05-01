			package stepDefinitions;

import io.cucumber.java.Before;

import java.io.IOException;

public class Hooks {

	@Before("@DeletePlace")
	public void beforeScenario() throws IOException
	{		//execute this code only when place id is null
		//write a code that will give you place id
		
		placeValidation m =new placeValidation();
		if(placeValidation.place_id==null)
		{
		
		m.addPlacePayloadWith("Shetty", "French", "Asia");
		m.userCallsWithHttpRequest("AddPlaceAPI", "POST");
		m.verifyPlace_IdCreatedMapsToUsing("Shetty", "getPlaceAPI");
		}
	}
}
