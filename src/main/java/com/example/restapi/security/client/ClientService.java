package com.example.restapi.security.client;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ClientService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ModelMapper modelMapper;

    @Transactional
    public Client createClient(ClientDto dto){
        dto.setClient_secret(passwordEncoder.encode(dto.getClient_secret()));
        Client clinet =  modelMapper.map(dto, Client.class);
        return clientRepository.save(clinet);
    }
}
