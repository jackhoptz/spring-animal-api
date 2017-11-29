package application.pet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PetController {

    @Autowired
    private PetRepository petRepository;

    @RequestMapping("/pet")
    public Iterable<Pet> listPets() {
        return petRepository.findAll();
    }

    @RequestMapping(value = "/pet", method = RequestMethod.POST)
    public Pet addPet(@RequestBody Pet pet) {
        petRepository.save(pet);
        return pet;
    }

    @RequestMapping("/pet/{id}")
    public Pet getPet(@PathVariable("id")long id) {
        return petRepository.findOne(id);
    }

    @RequestMapping(value = "/pet/{id}", method = RequestMethod.DELETE)
    public void deletePet(@PathVariable("id")long id) {
        petRepository.delete(id);
    }

    @RequestMapping(value = "/pet/{id}", method = RequestMethod.PUT)
    public Pet updatePet(@PathVariable("id")long id, @RequestBody Pet pet) {
        Pet existingPet = petRepository.findOne(id);
        if (existingPet == null) {
            return null;
        }

        pet.setId(id);
        petRepository.save(pet);

        return pet;
    }
}
