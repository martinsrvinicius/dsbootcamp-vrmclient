package com.martinsrvinicius.vrmclient.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.martinsrvinicius.vrmclient.dto.ClientDTO;
import com.martinsrvinicius.vrmclient.entities.Client;
import com.martinsrvinicius.vrmclient.repositories.ClientRepository;
import com.martinsrvinicius.vrmclient.services.exceptions.DatabaseException;
import com.martinsrvinicius.vrmclient.services.exceptions.ResourceNotFoundException;

@Service
public class ClientService {
	
	@Autowired
	private ClientRepository repository;
	
	@Transactional(readOnly = true)
	public Page<ClientDTO> findAllPaged(PageRequest pageRequest){
		 Page<Client> list = repository.findAll(pageRequest);
		 return list.map(x -> new ClientDTO (x));
	}

	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		Optional<Client> obj = repository.findById(id);
		Client entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
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

	@Transactional 
	public ClientDTO update(Long id, ClientDTO dto) {
		try {
			Client entity = repository.getById(id);
			entity.setName(dto.getName());
			entity.setIncome(dto.getIncome());
			entity.setCpf(dto.getCpf());
			entity.setChildren(dto.getChildren());
			entity.setBirthDate(dto.getBirthDate());
			entity = repository.save(entity);
			return new ClientDTO(entity);
		}
		catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		}
		catch(EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}
	
	
	
}
