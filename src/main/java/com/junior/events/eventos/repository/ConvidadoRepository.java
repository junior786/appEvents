package com.junior.events.eventos.repository;

import com.junior.events.eventos.models.Convidados;
import com.junior.events.eventos.models.Evento;
import org.springframework.data.repository.CrudRepository;

public interface ConvidadoRepository extends CrudRepository<Convidados, String> {
    Iterable<Convidados> findByEvento(Evento evento);
    Convidados findByRg(String rg);
}
