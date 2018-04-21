package com.akriuchk.application.domain;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public abstract class AbstractService {

    private AbstractDao repository;



}
