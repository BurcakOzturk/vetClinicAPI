package dev.patika.libraryRest.api;

import dev.patika.libraryRest.business.abstracts.IAnimalService;
import dev.patika.libraryRest.business.abstracts.ICustomerService;
import dev.patika.libraryRest.core.result.Result;
import dev.patika.libraryRest.core.result.ResultData;
import dev.patika.libraryRest.core.utilities.ResultHelper;
import dev.patika.libraryRest.dto.converter.AnimalConverter;
import dev.patika.libraryRest.dto.request.animal.AnimalSaveRequest;
import dev.patika.libraryRest.dto.request.animal.AnimalUpdateRequest;
import dev.patika.libraryRest.dto.response.CursorResponse;
import dev.patika.libraryRest.dto.response.animal.AnimalResponse;
import dev.patika.libraryRest.entities.Animal;
import dev.patika.libraryRest.entities.Customer;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/animal")
public class AnimalController {
    private final IAnimalService animalService;


    public AnimalController(
            IAnimalService animalService,
            ICustomerService customerService, AnimalConverter animalConverter
    ) {
        this.animalService = animalService;
    }


    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<AnimalResponse> saveAnimal(@Valid @RequestBody AnimalSaveRequest animalSaveRequest) {
        return this.animalService.saveAnimal(animalSaveRequest);
    }


    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AnimalResponse> updateAnimal(@Valid @RequestBody AnimalUpdateRequest animalUpdateRequest) {
        return this.animalService.updateAnimal(animalUpdateRequest);
    }

    @GetMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AnimalResponse> findAnimalById(@PathVariable("id") Long id) {
        return this.animalService.findAnimalById(id);
    }

    @GetMapping("/name/{animalName}")
    public ResultData<List<AnimalResponse>> findAnimalByName(@PathVariable("animalName") String name) {
        return this.animalService.findAnimalByName(name);
    }

    @GetMapping()
    public ResultData<List<AnimalResponse>> findAllAnimals() {
        return this.animalService.findAllAnimals();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result deleteAnimal(@PathVariable("id") Long id) {
        return this.animalService.deleteAnimal(id);
    }

    @GetMapping("/customer/{customerId}")
    public ResultData<List<AnimalResponse>> findByCustomerId(@PathVariable("customerId") Long customerId) {
        return this.animalService.findByCustomerId(customerId);
    }

}
