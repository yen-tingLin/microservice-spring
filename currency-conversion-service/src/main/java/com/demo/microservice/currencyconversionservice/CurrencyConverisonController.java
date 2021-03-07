package com.demo.microservice.currencyconversionservice;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyConverisonController {
	
	@Autowired
	private CurrencyExchangeProxy currencyExchangeProxy;

	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConverison calculateCurrencyConversion(
			@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) 
	{
		HashMap<String, String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		
		// call and pass variable "from" and "to" to currentcy-exchange method,
		// and return back in form of CurrencyConverison.class
		ResponseEntity<CurrencyConverison> responseEntity = new RestTemplate().getForEntity(
				"http://localhost:8000/currency-exchange/from/{from}/to/{to}", 
				CurrencyConverison.class, uriVariables);
		
		// get return value from responseEntity in form of CurrencyConverison.class
		CurrencyConverison currencyConversion = responseEntity.getBody();
		
		Long id = currencyConversion.getId();
		BigDecimal conversionMultiple = currencyConversion.getConversionMultiple();
		BigDecimal totalCalculatedAmount = quantity.multiply(currencyConversion.getConversionMultiple());
		String environment = currencyConversion.getEnvironment() + ", from rest template";
		
		return new CurrencyConverison(id, from, to, conversionMultiple, 
				quantity, totalCalculatedAmount, environment);
	}
	
	@GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConverison calculateCurrencyConversionFeign(
			@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) 
	{
		
		// get return value by proxy
		CurrencyConverison currencyConversion = currencyExchangeProxy.retrieveExchangeValue(from, to);
		
		Long id = currencyConversion.getId();
		BigDecimal conversionMultiple = currencyConversion.getConversionMultiple();
		BigDecimal totalCalculatedAmount = quantity.multiply(currencyConversion.getConversionMultiple());
		String environment = currencyConversion.getEnvironment() + ", from feign";
		
		return new CurrencyConverison(id, from, to, conversionMultiple, 
				quantity, totalCalculatedAmount, environment);
	}	
	
}
