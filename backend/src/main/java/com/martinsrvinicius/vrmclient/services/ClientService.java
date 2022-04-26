package com.martinsrvinicius.vrmclient.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.martinsrvinicius.vrmclient.dto.ClientDTO;
import com.martinsrvinicius.vrmclient.entities.Client;
import com.martinsrvinicius.vrmclient.repositories.ClientRepository;

@Service
public class ClientService {
	
	@Autowired
	private ClientRepository repository;
	
	@Transactional(readOnly = true)
	public List<ClientDTO> findAll(){
		 List<Client> list = repository.findAll();
		 return list.stream().map(x -> new ClientDTO (x)).collect(Collectors.toList());
	}
	
	
}
