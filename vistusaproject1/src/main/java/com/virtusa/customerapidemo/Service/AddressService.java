package com.virtusa.customerapidemo.Service;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.virtusa.customerapidemo.models.Address;
import com.virtusa.customerapidemo.models.Customer;
import com.virtusa.customerapidemo.repository.AddressRepository;

@Service
public class AddressService {
   @Autowired
	private AddressRepository addressRepo;
   @Autowired
   private CustomerService customerService;
   @Autowired
   private EntityManager entityManager;
	
	//insert the customer
		public Address saveAddress(long customerId,Address address)
		{
			Customer customer=customerService.getCustomerById(customerId);
			if(customer!=null)
			{
				address.setCustomer(customer);
				addressRepo.save(address);
				return address;
			}
			else
				return null;
			
		}
		
		public List<Address> getAllAddresses(long customerId)
		{
			return this.addressRepo.findByCustomerId(customerId);
		}
		
}

  


