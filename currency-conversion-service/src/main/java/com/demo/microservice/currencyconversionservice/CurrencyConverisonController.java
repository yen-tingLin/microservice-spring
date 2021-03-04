package com.demo.microservice.currencyconversionservice;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyConverisonController {

	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConverison calculateCurrencyConversion(
			@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) 
	{
		return new CurrencyConverison(1L, from, to, BigDecimal.ONE, 
				quantity, BigDecimal.ONE, "");
	}
	
}
