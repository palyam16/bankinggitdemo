package com.virtusa.customerapidemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.virtusa.customerapidemo.Service.AddressService;
import com.virtusa.customerapidemo.models.Address;

@RestController
public class AddressController {
	@Autowired
	private AddressService addressService;
	//saving the address details
		@CrossOrigin("*")
		@PostMapping("/customers/{customerId}/addresses")
		public @ResponseBody ResponseEntity<?> addAddress(@PathVariable("customerId") long customerId,
				@RequestBody Address address)
		{
			if(this.addressService.saveAddress(customerId,address)!=null)
				return ResponseEntity.ok(this.addressService.saveAddress(customerId,address));
			else
				return ResponseEntity.ok("Customer Not found, address cannot be created");
		}
		
		@CrossOrigin("*")
		@GetMapping("/customers/{customerId}/addresses")
		public ResponseEntity<?> getAllAddresses(@PathVariable("customerId") long customerId)
		{
			if(this.addressService.getAllAddresses(customerId)!=null)
				return ResponseEntity.ok(this.addressService.getAllAddresses(customerId));
			else
				return ResponseEntity.ok("Customer Not found, address cannot be created");
		}
		
		
}
