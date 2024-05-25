package dev.patika.libraryRest.business.concretes;

import dev.patika.libraryRest.business.abstracts.IAnimalService;
import dev.patika.libraryRest.core.exception.NotFoundException;
import dev.patika.libraryRest.core.result.Result;
import dev.patika.libraryRest.core.result.ResultData;
import dev.patika.libraryRest.core.utilities.Messages;
import dev.patika.libraryRest.core.utilities.ResultInfo;
import dev.patika.libraryRest.dao.AnimalRepo;
import dev.patika.libraryRest.dto.converter.AnimalConverter;
import dev.patika.libraryRest.dto.request.animal.AnimalSaveRequest;
import dev.patika.libraryRest.dto.request.animal.AnimalUpdateRequest;
import dev.patika.libraryRest.dto.response.animal.AnimalResponse;
import dev.patika.libraryRest.entities.Animal;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnimalManager implements IAnimalService {

    private final AnimalRepo animalRepo;
    private final CustomerManager customerManager;
    private final AnimalConverter converter;

    public AnimalManager(AnimalRepo animalRepo, CustomerManager customerManager, AnimalConverter animalConverter) {
        this.animalRepo = animalRepo;
        this.customerManager = customerManager;
        this.converter = animalConverter;
    }

    public ResultData<AnimalResponse> saveAnimal(AnimalSaveRequest animalSaveRequest) {
        checkIfAnimalExists(animalSaveRequest);
        this.customerManager.findCustomerId(animalSaveRequest.getCustomerId());
        Animal saveAnimal = this.converter.convertToAnimal(animalSaveRequest);
        this.animalRepo.save(saveAnimal);
        return ResultInfo.created(this.converter.toAnimalResponse(saveAnimal));
    }

    private void checkIfAnimalExists(AnimalSaveRequest animalSaveRequest) {
        Animal existingAnimal = animalRepo.findByNameAndSpeciesAndBreedAndGenderAndColourAndDateOfBirthAndCustomerId(
                animalSaveRequest.getName(),
                animalSaveRequest.getSpecies(),
                animalSaveRequest.getBreed(),
                animalSaveRequest.getGender(),
                animalSaveRequest.getColour(),
                animalSaveRequest.getDateOfBirth(),
                animalSaveRequest.getCustomerId()
        );

        if (existingAnimal != null) {
            throw new IllegalArgumentException("Animal with same properties already exists.");
        }
    }
    @Override
    public ResultData<AnimalResponse> updateAnimal(AnimalUpdateRequest animalUpdateRequest) {
        this.findAnimalById(animalUpdateRequest.getId());
        this.customerManager.findCustomerId(animalUpdateRequest.getCustomerId());
        Animal updateAnimal = this.converter.convertToUpdateAnimal(animalUpdateRequest);
        this.animalRepo.save(updateAnimal);
        return ResultInfo.success(this.converter.toAnimalResponse(updateAnimal));
    }

    @Override
    public ResultData<AnimalResponse> findAnimalById(Long id) {
        Animal animal = animalRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Animal not found with ID: " + id));
        return ResultInfo.success(this.converter.toAnimalResponse(animal));
    }

    @Override
    public ResultData<List<AnimalResponse>> findAnimalByName(String name) {
        List<Animal> animals = animalRepo.findByName(name);
        if (animals.isEmpty()) {
            throw new EntityNotFoundException("No animals found with name: " + name);
        }
        List<AnimalResponse> animalResponses = animals.stream()
                .map(this.converter::toAnimalResponse)
                .collect(Collectors.toList());
        return ResultInfo.success(animalResponses);
    }

    @Override
    public ResultData<List<AnimalResponse>> findAllAnimals() {
        List<Animal> allAnimals = this.animalRepo.findAll();
        List<AnimalResponse> animalResponses = allAnimals.stream()
                .map(this.converter::toAnimalResponse)
                .collect(Collectors.toList());
        return ResultInfo.success(animalResponses);
    }

    @Override
    public Result deleteAnimal(Long id) {
        Animal animal = this.findAnimal(id);
        this.animalRepo.delete(animal);
        return ResultInfo.ok();
    }

    @Override
    public ResultData<List<AnimalResponse>> findByCustomerId(Long customerId) {
        this.customerManager.findCustomerId(customerId);
        List<Animal> animals = this.animalRepo.findByCustomerId(customerId);
        if (animals.isEmpty()) {
            throw new EntityNotFoundException("No animals found for the customer with ID: " + customerId);
        }
        List<AnimalResponse> animalResponses = animals.stream()
                .map(this.converter::toAnimalResponse)
                .collect(Collectors.toList());
        return ResultInfo.success(animalResponses);
    }

    public Animal findAnimal(Long id) {
        return animalRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Animal not found with ID: " + id));
    }
}
