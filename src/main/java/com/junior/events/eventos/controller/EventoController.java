package com.junior.events.eventos.controller;

import com.junior.events.eventos.models.Convidados;
import com.junior.events.eventos.models.Evento;
import com.junior.events.eventos.repository.ConvidadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.junior.events.eventos.repository.EventoRepository;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class EventoController {

    @Autowired
    private EventoRepository eventoRepository;
    @Autowired
    private ConvidadoRepository convidadoRepository;

    @RequestMapping(value = "/cadastrarEvento", method = RequestMethod.GET)
    public String form() {
        return "evento/formEvento";
    }

    @RequestMapping(value = "/cadastrarEvento", method = RequestMethod.POST)
    public String form(@Valid Evento evento, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()){
            attributes.addFlashAttribute("mensagem", "Preencha os campos corretamente");
        }else {
            eventoRepository.save(evento);
            attributes.addFlashAttribute("mensagem","Evento cadastrado com sucesso");
        }
        return "redirect:/cadastrarEvento";
    }

    @RequestMapping("/eventos")
    public ModelAndView listaEventos() {
        ModelAndView mv = new ModelAndView("index");
        Iterable<Evento> eventos = eventoRepository.findAll();
        mv.addObject("eventos", eventos);
        return mv;
    }

    @RequestMapping(value = "/{codigo}", method = RequestMethod.GET)
    public ModelAndView detalhesEvento(@PathVariable("codigo") long codigo) {
        Evento evento = eventoRepository.findByCodigo(codigo);
        ModelAndView mv = new ModelAndView("evento/detalhesEvento");
        mv.addObject("evento", evento);
        Iterable<Convidados> convidados = convidadoRepository .findByEvento(evento);
        mv.addObject("convidados", convidados);
        return mv;
    }

    @RequestMapping("/deletarEvento")
    public String deletarEvento(long codigo){
        Evento evento = eventoRepository.findByCodigo(codigo);
        eventoRepository.delete(evento);
        return "redirect:/eventos";


    }

    @RequestMapping(value = "/{codigo}", method = RequestMethod.POST)
    public String detalhesEventoPost(@PathVariable("codigo") long codigo, @Valid Convidados convidados, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()){
            attributes.addFlashAttribute("mensagem","Verifique os campos!");

        }else {
            Evento evento = eventoRepository.findByCodigo(codigo);
            convidados.setEvento(evento);
            convidadoRepository.save(convidados);
            attributes.addFlashAttribute("mensagem","Convidado enviado com sucesso");
        }
        return "redirect:/{codigo}";
    }
    @RequestMapping("/deletarConvidado")
    public String deletarConvidado(String rg){
        Convidados convidados = convidadoRepository.findByRg(rg);
        convidadoRepository.delete(convidados);
        Evento evento = convidados.getEvento();
        long codigoLong = evento.getCodigo();
        String codigo = "redirect:/" + Long.toString(codigoLong);
        return codigo;
    }
}
