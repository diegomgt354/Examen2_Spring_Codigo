package com.example.demo.service;

import com.example.demo.dao.EmpresaRepository;
import com.example.demo.entity.Empresa;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmpresaService {

    private final EmpresaRepository repository;


    public Empresa getByIdEmpresa(Long id){
        return repository.findById(id).orElse(null);
    }

    public List<Empresa> getAllEmpresas(){
        return repository.findAll();
    }

    @Transactional
    public Empresa addEmpresa(Empresa empresa){
        return repository.save(empresa);
    }


}
