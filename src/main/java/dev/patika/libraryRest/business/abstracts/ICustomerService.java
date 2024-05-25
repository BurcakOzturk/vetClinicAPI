package dev.patika.libraryRest.business.abstracts;

import dev.patika.libraryRest.core.result.Result;
import dev.patika.libraryRest.core.result.ResultData;
import dev.patika.libraryRest.dto.request.customer.CustomerSaveRequest;
import dev.patika.libraryRest.dto.request.customer.CustomerUpdateRequest;
import dev.patika.libraryRest.dto.response.customer.CustomerResponse;
import dev.patika.libraryRest.entities.Customer;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ICustomerService {

    ResultData<CustomerResponse> saveCustomer(CustomerSaveRequest customerSaveRequest);

    ResultData<CustomerResponse> updateCustomer(CustomerUpdateRequest customerUpdateRequest);

    ResultData<CustomerResponse> findCustomerById(Long id);

    ResultData<List<CustomerResponse>> findCustomerByName(String name);

    ResultData<List<CustomerResponse>> findAllCustomers();

    Result deleteCustomer(Long id);

}

