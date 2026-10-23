package com.lavajato.controller;

import com.lavajato.model.Agendamento;
import com.lavajato.repository.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AgendamentoController {

    @Autowired
    private AgendamentoRepository repository;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/agendar")
    public String telaAgendamento(Model model) {
        model.addAttribute("agendamento", new Agendamento());
        return "agendamento";
    }

    @GetMapping("/agendar/horarios-ocupados")
    @ResponseBody
    public ResponseEntity<List<String>> buscarHorariosOcupados(
            @RequestParam("data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {

        List<Agendamento> agendamentos = repository.findByDataAgendamento(data);
        List<String> horariosOcupados = agendamentos.stream()
                .map(a -> a.getHorarioAgendamento().toString())
                .collect(Collectors.toList());

        return ResponseEntity.ok(horariosOcupados);
    }

    @PostMapping("/agendar/salvar")
    public String salvarAgendamento(@ModelAttribute Agendamento agendamento, Model model) {
        LocalDate data = agendamento.getDataAgendamento();
        LocalTime horario = agendamento.getHorarioAgendamento();

        if (data != null && (data.getDayOfWeek() != DayOfWeek.SATURDAY && data.getDayOfWeek() != DayOfWeek.SUNDAY)) {
            model.addAttribute("erro", "Não funcionamos nesta data! O Lava Jato do David atende exclusivamente aos Sábados e Domingos.");
            model.addAttribute("agendamento", agendamento);
            return "agendamento";
        }

        if (horario != null && horario.getHour() == 12) {
            model.addAttribute("erro", "Horário das 12:00 às 13:00 reservado para o almoço! Escolha outro horário.");
            model.addAttribute("agendamento", agendamento);
            return "agendamento";
        }

        if (repository.existsByDataAgendamentoAndHorarioAgendamento(data, horario)) {
            model.addAttribute("erro", "Este horário já está ocupado por outro cliente! Escolha outro horário.");
            model.addAttribute("agendamento", agendamento);
            return "agendamento";
        }

        repository.save(agendamento);
        return "redirect:/?sucesso=true";
    }
}