package dev.patika.libraryRest.business.abstracts;

import dev.patika.libraryRest.core.result.Result;
import dev.patika.libraryRest.core.result.ResultData;
import dev.patika.libraryRest.dto.request.animal.AnimalSaveRequest;
import dev.patika.libraryRest.dto.request.animal.AnimalUpdateRequest;
import dev.patika.libraryRest.dto.response.animal.AnimalResponse;
import dev.patika.libraryRest.entities.Animal;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IAnimalService {
ResultData<AnimalResponse> saveAnimal(AnimalSaveRequest animalSaveRequest);

    ResultData<AnimalResponse> updateAnimal(AnimalUpdateRequest animalUpdateRequest);


    ResultData<AnimalResponse> findAnimalById(Long id);

    ResultData<List<AnimalResponse>> findAnimalByName(String name);

    ResultData<List<AnimalResponse>> findAllAnimals();

    // Deletes an animal by its ID
    Result deleteAnimal(Long id);

    ResultData<List<AnimalResponse>> findByCustomerId(Long customerId);
}

