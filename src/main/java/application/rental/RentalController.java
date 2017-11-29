package application.rental;

import application.pet.Pet;
import application.pet.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@RestController
public class RentalController {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private PetRepository petRepository;

    @RequestMapping("/pet/{id}/rental")
    public Iterable<Rental> listRentals(@PathVariable("id")long petId) {
        Pet pet = petRepository.findOne(petId);
        return pet.getRentals();
    }


    @RequestMapping(value = "/pet/{id}/rental", method = RequestMethod.POST)
    public Rental addRental(@PathVariable("id")long petId, @RequestBody Rental rental) {
        Pet pet = petRepository.findOne(petId);
        rental.setPet(pet);
        rentalRepository.save(rental);
        return rental;
    }


    @RequestMapping(value = "/pet/{id}/rental/{rentalId}", method = RequestMethod.POST)
    public Rental getRental(@PathVariable("id") long petId, @PathVariable("rentalId") long id) {
        Rental rental = rentalRepository.findOne(id);
        if(rental.getPet().getId() == petId) {
            return rental;
        }
        return null;
    }

    @RequestMapping(value = "/pet/{id}/rental/{rentalId}", method = RequestMethod.DELETE)
    public void deleteRental(@PathVariable("id") long petId, @PathVariable("rentalId") long id) {
        if(rentalRepository.findOne(id).getPet().getId() == petId) {
            rentalRepository.delete(id);
        }
    }

    @RequestMapping(value = "/pet/{id}/rental/{rentalId}", method = RequestMethod.PUT)
    public Rental updateRental(@PathVariable("id") long petId, @PathVariable("rentalId") long id, @RequestBody Rental rental) {
        Rental existingRental = rentalRepository.findOne(id);
        if(rental.getPet().getId() == petId) {
            rental.setId(id);
            rentalRepository.save(rental);
        }

        return rental;
    }

}
