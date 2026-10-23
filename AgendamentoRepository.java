package com.lavajato.repository;

import com.lavajato.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    boolean existsByDataAgendamentoAndHorarioAgendamento(LocalDate dataAgendamento, LocalTime horarioAgendamento);
    List<Agendamento> findByDataAgendamento(LocalDate dataAgendamento);
}