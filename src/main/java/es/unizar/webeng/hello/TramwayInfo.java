package es.unizar.webeng.hello;

import java.io.*;

import java.util.Map;

import com.jayway.jsonpath.JsonPath;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;


@Controller
public class TramwayInfo {

    Logger logger = LoggerFactory.getLogger(TramwayInfo.class);

	public final String CAMPUS_EBRO_TO_MAGO_OZ =
                "https://www.zaragoza.es/sede/servicio/"
                + "urbanismo-infraestructuras/transporte-urbano/"
                + "parada-tranvia/401?rf=html&srsname=utm30n";

	public final String CAMPUS_EBRO_TO_AVENIDA_ACADEMIA =
                "https://www.zaragoza.es/sede/servicio/"
                + "urbanismo-infraestructuras/transporte-urbano/"
                + "parada-tranvia/402?rf=html&srsname=utm30n";

   /**
    * Performs a GET request to get tramway info.
    * @see: https://stackoverflow.com/questions/22816335/java-httprequest-json-response-handling
    * @param url, url used to query, not null
    * @return json string response
    */
   public String getRequest(String url) {
	   String json = null;

	   try (CloseableHttpClient httpClient = HttpClientBuilder.create().build())
	   {
	       HttpGet request = new HttpGet(url);

	       /** Add header info: json app */
	       request.addHeader("Accept", "application/json");

	       /** Execute GET request */
           HttpResponse result = httpClient.execute(request);

	       /** Transform http response in a JSON String */
	       json = EntityUtils.toString(result.getEntity(), "UTF-8");
	   }
	   catch (IOException ex)
	   {
		   logger.info("Error trying to make a http request");
	   }

	   return json;
   }

   /**
    * Parse JSON string and returns the minutes remaining before
    * the next tramway arrives.
    * @param String json
    * @return JSON property: "minutos"
    */
    public int getRemainingTime(String json)
    {
        return JsonPath.read(json, "$.destinos[0].minutos");
    }

   /**
    * Makes a request to an API built by Zaragoza's town hall in order
    * to get the next tranway arrival at the EINA nearest tramway stop.
    * After getting times, set view attributes with them.
    * @param model, list of attributes, time of arrival
    * @return redirects main page
    */
    @GetMapping("/tramway")
   	public String nextTramway(Map<String, Object> model)
   	{
        /** Get arrival times */
        String response1 = getRequest(CAMPUS_EBRO_TO_MAGO_OZ);
        String response2 = getRequest(CAMPUS_EBRO_TO_AVENIDA_ACADEMIA);
        int timeToMagoOz = getRemainingTime(response1);
        int timeToAvenidaAcademia = getRemainingTime(response2);

        /** Set alertness if time is short */
        String alert = "You have to run!!!";
        if (timeToMagoOz <= 2)
            model.put("alert_mago_oz", alert);

        if (timeToAvenidaAcademia <= 2)
            model.put("alert_avenida_academia", alert);

        /** Set time attributes */
        String infoToMagoOz = timeToMagoOz +
                " minutes before tramway arrives with destination: Mago de Oz.";
        String infoToAvenidaAcademia = timeToAvenidaAcademia +
                " minutes before tramway arrives with destination: Avenida Academia.";

        model.put("time_to_mago_oz", infoToMagoOz);
        model.put("time_to_avenida_academia", infoToAvenidaAcademia);

        return "wellcome";
    }
}
