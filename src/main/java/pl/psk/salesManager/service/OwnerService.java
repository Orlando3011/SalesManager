package pl.psk.salesManager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.psk.salesManager.model.Owner;
import pl.psk.salesManager.repository.OwnerRepository;

@Service
public class OwnerService {
    @Autowired
    OwnerRepository ownerRepository;

    public Owner displayOwnerData() {
        Owner owner;
        if(ownerRepository.findAll().size() == 0) {
            owner = new Owner();
            owner.setAddress("empty");
            owner.setBankName("empty");
            owner.setBankNumber("empty");
            owner.setCompanyName("empty");
            owner.setEmail("empty");
            owner.setFamilyName("empty");
            owner.setFirstName("empty");
            owner.setPhone("empty");
            ownerRepository.save(owner);
        }
        else {
            owner = ownerRepository.findAll().get(0);
        }
        return owner;
    }

    public void editOwnerData(Owner owner) {
        ownerRepository.deleteAll();
        ownerRepository.save(owner);
    }
}
