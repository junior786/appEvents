package com.junior.events.eventos.repository;

import com.junior.events.eventos.models.Evento;
import org.springframework.data.repository.CrudRepository;



public interface EventoRepository  extends CrudRepository<Evento, String> {
        Evento findByCodigo(Long codigo);

}
