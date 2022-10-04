package com.webclient.rest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import com.webclient.model.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/client")
public class SpringWebFluxClient {

	private WebClient client=WebClient.create("http://localhost:9292");
	
	@GetMapping("/getCustomer")
	public Flux<Customer> getCustomer(){
		
		return client.get().uri("/router/customer").accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToFlux(Customer.class)
				.log("retrive customer");
	}
	
	
	@GetMapping("/getCustomerById/{id}")
	public Mono<Customer> getCustomerById(@PathVariable("id") String id){
		
		return client.get().uri("router/customer/{input}",id).accept(MediaType.APPLICATION_JSON)
				.retrieve().bodyToMono(Customer.class);
	}
	

	
	@PostMapping("/update")
	public Mono<Customer> saveCustomer_sync(@RequestBody Customer customer){
		
		System.out.println(customer);
		
		return client.post().uri("/router/customer/save").accept(MediaType.ALL)
      			.bodyValue(customer)
				.retrieve()
				.bodyToMono(Customer.class)
				.log("save using body");
	}
	
	
	
	
	
}
