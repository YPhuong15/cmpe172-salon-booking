package cmpe172.salonbooking.service;

import cmpe172.salonbooking.model.Client;
import cmpe172.salonbooking.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepo;

    public Client findOrCreateClient(String name, String email, String phone) {

        return clientRepo.findByEmail(email)
                .orElseGet(() -> {
                    Client c = new Client();
                    c.setName(name);
                    c.setEmail(email);
                    c.setPhoneNumber(phone);
                    return clientRepo.save(c);
                });
    }
}