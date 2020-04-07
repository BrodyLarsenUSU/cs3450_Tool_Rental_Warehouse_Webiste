package com.softwareEngineering.Spring.Repositories;

import java.util.List;

import com.softwareEngineering.Spring.Models.*;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LedgerRepository extends MongoRepository<ledgerItem, String>{
    public List<ledgerItem> findAll();
}