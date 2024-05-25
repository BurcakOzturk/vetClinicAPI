package dev.patika.libraryRest.business.concretes;

import dev.patika.libraryRest.business.abstracts.ICustomerService;
import dev.patika.libraryRest.core.result.Result;
import dev.patika.libraryRest.core.result.ResultData;
import dev.patika.libraryRest.core.utilities.ResultInfo;
import dev.patika.libraryRest.dao.CustomerRepo;
import dev.patika.libraryRest.dto.converter.CustomerConverter;
import dev.patika.libraryRest.dto.request.customer.CustomerSaveRequest;
import dev.patika.libraryRest.dto.request.customer.CustomerUpdateRequest;
import dev.patika.libraryRest.dto.response.customer.CustomerResponse;
import dev.patika.libraryRest.entities.Customer;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerManager implements ICustomerService {

    private final CustomerRepo customerRepo;
    private final CustomerConverter customerConverter;

    public CustomerManager(CustomerRepo customerRepo, CustomerConverter customerConverter) {
        this.customerRepo = customerRepo;
        this.customerConverter = customerConverter;
    }


    @Override
    public ResultData<CustomerResponse> saveCustomer(CustomerSaveRequest customerSaveRequest) {
        Customer saveCustomer = this.customerConverter.convertToCustomer(customerSaveRequest);

        checkCustomerExistence(saveCustomer);

        this.customerRepo.save(saveCustomer);

        return ResultInfo.success(this.customerConverter.toCustomerResponse(saveCustomer));
    }

    @Override
    public ResultData<CustomerResponse> updateCustomer(CustomerUpdateRequest customerUpdateRequest) {
        findCustomerById(customerUpdateRequest.getId());

        Customer updatedCustomer = this.customerConverter.convertToUpdatedCustomer(customerUpdateRequest);

        updatedCustomer.setId(customerUpdateRequest.getId());

        this.customerRepo.save(updatedCustomer);

        return ResultInfo.success(this.customerConverter.toCustomerResponse(updatedCustomer));
    }


    // Finds a customer by their ID.
    public Customer findCustomerId(Long customerId) {
        return this.customerRepo.findById(customerId).orElseThrow(()
                -> new EntityNotFoundException("Customer with ID " + customerId + " not found"));
    }

    @Override
    public ResultData<CustomerResponse> findCustomerById(Long id) {
        Customer customer = findCustomerId(id);
        return ResultInfo.success(this.customerConverter.toCustomerResponse(customer));
    }

    @Override
    public ResultData<List<CustomerResponse>> findCustomerByName(String name) {
        List<Customer> customers = customerRepo.findByName(name);
        if (customers.isEmpty()) {
            throw new EntityNotFoundException("Customer with name " + name + " not found.");
        }
        List<CustomerResponse> customerResponses = customers.stream()
                .map(this.customerConverter::toCustomerResponse).collect(Collectors.toList());
        return ResultInfo.success(customerResponses);
    }

    @Override
    public ResultData<List<CustomerResponse>> findAllCustomers() {
        List<Customer> allCustomers = this.customerRepo.findAll();
        List<CustomerResponse> customerResponses = allCustomers.stream()
                .map(this.customerConverter::toCustomerResponse).collect(Collectors.toList());
        return ResultInfo.success(customerResponses);
    }

    @Override
    public Result deleteCustomer(Long id) {
        Customer customer = findCustomerId(id);
        this.customerRepo.delete(customer);
        return ResultInfo.ok();
    }

    private void checkCustomerExistence(Customer customer) {
        if (customerRepo.existsByMail(customer.getMail())) {
            throw new IllegalArgumentException("Email address " + customer.getMail() + " is already registered.");
        }
        if (customerRepo.existsByPhone(customer.getPhone())) {
            throw new IllegalArgumentException("Phone " + customer.getPhone() + " is already registered.");
        }
    }
}
