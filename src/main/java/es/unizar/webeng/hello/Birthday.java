package es.unizar.webeng.hello;

import java.net.URL;
import java.util.Map;
import java.util.Arrays;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
* Controller class declaration.
*/
@Controller
public class Birthday {

	private static HttpURLConnection connection;

	/** Mapping with JSP and birthday data processing function. */
	@GetMapping("/birthdayMain")
	public String birthday(@RequestParam(value = "birthday", required=false) String bd, Map<String, Object> model) {

		if (bd == null) {
			return "birthdayMain";
		} else {
			int[] date = DatetoArray(bd);
			String response = GetEvent(date);
			model.put("event", response);
			return "birthdayInfo";
		}
	}

  /** Function that converts date String format to Array. */
	public int[] DatetoArray( String dt)
	{
		String [] parts = dt.split("-");
		int [] IntParts = new int[parts.length - 1];
		IntParts[0] = Integer.parseInt(parts[1]);
		IntParts[1] = Integer.parseInt(parts[2]);
		return IntParts;

	}

  /**
	* Function that retrieves data from the NumersAPI.
	* http://numbersapi.com/
	*/

	public String GetEvent( int date[])
	{
		BufferedReader reader;
		String line;
		StringBuffer responseContent = new StringBuffer();

		try{

			/** API request setup. */
			URL url = new URL ( "http://numbersapi.com/" + date[0] + "/" + date[1] + "/date" );
			connection = (HttpURLConnection) url.openConnection();

			connection.setRequestMethod("GET");
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);

			int sts = connection.getResponseCode();

			/** API's response processing. */
			if (sts > 299){
				reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
				while((line = reader.readLine()) != null){
					responseContent.append(line);
				}
				reader.close();
			} else {
				reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				while((line = reader.readLine()) != null){
					responseContent.append(line);
				}
				reader.close();
			}
		} catch (MalformedURLException e){
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
		return responseContent.toString();
	}
}
