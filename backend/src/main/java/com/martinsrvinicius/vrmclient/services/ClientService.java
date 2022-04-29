package com.martinsrvinicius.vrmclient.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.martinsrvinicius.vrmclient.dto.ClientDTO;
import com.martinsrvinicius.vrmclient.entities.Client;
import com.martinsrvinicius.vrmclient.repositories.ClientRepository;
import com.martinsrvinicius.vrmclient.services.exceptions.EntityNotFoundException;

@Service
public class ClientService {
	
	@Autowired
	private ClientRepository repository;
	
	@Transactional(readOnly = true)
	public List<ClientDTO> findAll(){
		 List<Client> list = repository.findAll();
		 return list.stream().map(x -> new ClientDTO (x)).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		Optional<Client> obj = repository.findById(id);
		Client entity = obj.orElseThrow(() -> new EntityNotFoundException("Entity not found"));
		return  new ClientDTO(entity);
	}

	@Transactional
	public ClientDTO insert(ClientDTO dto) {
		Client entity = new Client();
		entity.setName(dto.getName());
		entity.setIncome(dto.getIncome());
		entity.setCpf(dto.getCpf());
		entity.setChildren(dto.getChildren());
		entity.setBirthDate(dto.getBirthDate());
		entity = repository.save(entity);
		return new ClientDTO(entity);
	}
	
	
	
}
