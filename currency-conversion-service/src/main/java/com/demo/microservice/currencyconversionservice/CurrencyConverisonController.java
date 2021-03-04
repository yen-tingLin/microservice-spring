package com.demo.microservice.currencyconversionservice;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyConverisonController {

	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConverison calculateCurrencyConversion(
			@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) 
	{
		HashMap<String, String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		
		ResponseEntity<CurrencyConverison> responseEntity = new RestTemplate().getForEntity(
				"http://localhost:8000/currency-exchange/from/{from}/to/{to}", 
				CurrencyConverison.class, uriVariables);
		
		CurrencyConverison currencyConversion = responseEntity.getBody();
		
		BigDecimal conversionMultiple = currencyConversion.getConversionMultiple();
		BigDecimal totalCalculatedAmount = quantity.multiply(currencyConversion.getConversionMultiple());
		String environment = currencyConversion.getEnvironment();
		
		return new CurrencyConverison(currencyConversion.getId(), 
				from, to, conversionMultiple, quantity, 
				totalCalculatedAmount, environment);
	}
	
}
