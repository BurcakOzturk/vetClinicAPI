package dev.patika.libraryRest.api;


import dev.patika.libraryRest.business.abstracts.ICustomerService;
import dev.patika.libraryRest.core.result.Result;
import dev.patika.libraryRest.core.result.ResultData;
import dev.patika.libraryRest.core.utilities.ResultHelper;
import dev.patika.libraryRest.dto.request.customer.CustomerSaveRequest;
import dev.patika.libraryRest.dto.request.customer.CustomerUpdateRequest;
import dev.patika.libraryRest.dto.response.CursorResponse;
import dev.patika.libraryRest.dto.response.customer.CustomerResponse;
import dev.patika.libraryRest.entities.Customer;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/customer")
public class CustomerController {
    private final ICustomerService customerService;

    public CustomerController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<CustomerResponse> saveCustomer(@Valid @RequestBody CustomerSaveRequest customerSaveRequest) {
        return this.customerService.saveCustomer(customerSaveRequest);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CustomerResponse> findCustomerById(@PathVariable("id") Long id) {
        return this.customerService.findCustomerById(id);
    }

    @GetMapping()
    public ResultData<List<CustomerResponse>> findAllCustomers() {
        return this.customerService.findAllCustomers();
    }


    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CustomerResponse> updateCustomer(@Valid @RequestBody CustomerUpdateRequest customerUpdateRequest) {
        return this.customerService.updateCustomer(customerUpdateRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result deleteCustomer(@PathVariable("id") Long id) {
        return this.customerService.deleteCustomer(id);
    }

}

