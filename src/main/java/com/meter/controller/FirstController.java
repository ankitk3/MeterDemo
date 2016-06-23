package com.meter.controller;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;

@Controller
public class FirstController {
	@Autowired
	private MetricRegistry metrics;
	private Meter requestsHomePage;
	private Meter requestsHello;
	private Histogram requestTime;
	@PostConstruct
	public void postConstruct(){
		//requestsHomePage = metrics.meter("HomePage");
		//requestsHello = metrics.meter("Hello");
		requestTime = metrics.histogram(FirstController.class+" response-time");
	}
	private int i = 0;
	
	@ResponseBody
	@RequestMapping(value= "/", method = {RequestMethod.GET, RequestMethod.POST})
	public String index() {
		long startTime = System.currentTimeMillis();
		if(i==0){
			startReport();
			i++;
		}
		try{
			Thread.sleep((long)(10000 * Math.random()));
		} catch(Exception e){
			e.printStackTrace();
		}
		//requestsHomePage.mark();
		long time = (System.currentTimeMillis()-startTime)/1000;
		System.out.println(time);
		requestTime.update(time);
		return "<html><body>I am up and running</body></html>";
	}
	@ResponseBody
	@RequestMapping("/hello")
	public String hello() {
		if(i==0){
			startReport();
			i++;
		}
		//requestsHello.mark();
		return "hello World";
	}
	private void startReport() {
	      ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics)
	          .convertRatesTo(TimeUnit.SECONDS)
	          .convertDurationsTo(TimeUnit.MILLISECONDS)
	          .build();
	      reporter.start(1, TimeUnit.SECONDS);
	  }
}
