package com.api.itsec_test.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class SchemaSwitcherService {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void switchSchema(String schema){
        if(schemaExists(schema)){
            entityManager.createNativeQuery("SET search_path to " + schema).executeUpdate();
        }
    }

    public boolean schemaExists(String schema) {
        Object result = entityManager
                .createNativeQuery("SELECT EXISTS(SELECT 1 FROM information_schema.schemata WHERE schema_name = :schemaName)")
                .setParameter("schemaName", schema)
                .getSingleResult();

        return Boolean.parseBoolean(result.toString());
    }
}
