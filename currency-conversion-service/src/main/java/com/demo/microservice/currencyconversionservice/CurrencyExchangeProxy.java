package com.demo.microservice.currencyconversionservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="currency-exchange", url="localhost:8000")
public interface CurrencyExchangeProxy {

	/**
	 * Call and pass variable "from" and "to" to currentcy-exchange method,
	 * and return back in form of CurrencyConverison.class
	 * @param from
	 * @param to
	 * @return : CurrencyConverison
	 */
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyConverison retrieveExchangeValue(
			@PathVariable String from,
			@PathVariable String to);
}
