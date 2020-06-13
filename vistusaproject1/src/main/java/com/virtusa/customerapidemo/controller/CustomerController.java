package com.virtusa.customerapidemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bohnman.squiggly.Squiggly;
import com.github.bohnman.squiggly.util.SquigglyUtils;

import com.virtusa.customerapidemo.Service.CustomerService;

import com.virtusa.customerapidemo.models.Customer;

@RestController
public class CustomerController {
	@Autowired
	private CustomerService customerService;
	
	//saving the customer details
	@CrossOrigin("*")
	@PostMapping("/customers")
	public @ResponseBody Customer addCustomer(@RequestBody Customer customer)
	{
		return this.customerService.saveCustomer(customer);
	}
	
	@CrossOrigin("*")
	@RequestMapping(value = "/customers", 
	  produces = { "application/json", "application/xml" }, 
	  method = RequestMethod.GET)
	public List<Customer> getCustomers()
	{
		return this.customerService.getAllCustomers();
	}
	
	@GetMapping("/customers/{pageNo}/{pageSize}")
    public List<Customer> getPaginatedCustomers(@PathVariable int pageNo, 
            @PathVariable int pageSize) {

        return customerService.findPaginated(pageNo, pageSize);
    }
	
	//http://localhost:6060/customers/filters?fields=customerId,name
	@GetMapping("/customers/filters")
    public String getFilteredCustomer(@RequestParam(name = "fields", required = false) 
    String fields) 
	{

		List<Customer> customersList =getCustomers();
		ObjectMapper mapper = Squiggly.init(new ObjectMapper(), fields);  
		return SquigglyUtils.stringify(mapper, customersList);
		
    }
	//http://localhost:6060/customers?pageSize=5&pageNo=1&sortBy=email
	@GetMapping("/customers")
    public ResponseEntity<?> getAllCustomers(
                        @RequestParam(defaultValue = "0") Integer pageNo, 
                        @RequestParam(defaultValue = "10") Integer pageSize,
                        @RequestParam(defaultValue = "customerId") String sortBy) 
    { 
        List<Customer> list = customerService.getAllCustomers(pageNo, pageSize, sortBy);
      
        
        if(list.size()>0)
        	return new ResponseEntity<List<Customer>>(list, new HttpHeaders(), HttpStatus.OK); 
        else
        	return ResponseEntity.ok("Customer not found!");
        
    }
	
	/*
	 * 
	 * http://localhost:7078/customers/rsql?condition=customerId>1

	 */
	
	 @GetMapping("/customers/rsql")
	    public Page<Customer> query(@RequestParam String condition,
	                                @RequestParam(required = false,defaultValue = "0") int page,
	                                @RequestParam(required = false,defaultValue = "2") int size,
	                                @RequestParam(defaultValue = "customerId") String sortBy){
	        return customerService.query(condition,PageRequest.of(page, size));
	    }

	 
	/*
	 * @PostMapping("/Addresses") public @ResponseBody Address
	 * addAddress(@RequestBody Address address) { return
	 * this.customerService.saveAddress(address); }
	 * 
	 * @GetMapping("/addresses/{pageNo}/{pageSize}") public List<Address>
	 * getPaginatedAddress(@PathVariable int pageNo,
	 * 
	 * @PathVariable int pageSize) {
	 * 
	 * return AddressService.findPaginated(pageNo, pageSize); }
	 */
	 
}
