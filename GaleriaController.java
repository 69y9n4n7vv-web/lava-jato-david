package com.lavajato.controller;

import com.lavajato.model.FotoGaleria;
import com.lavajato.repository.AgendamentoRepository;
import com.lavajato.repository.FotoGaleriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Controller
public class GaleriaController {

    @Autowired
    private FotoGaleriaRepository repository;

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @GetMapping("/galeria")
    public String galeriaPublica(Model model) {
        model.addAttribute("fotos", repository.findAll());
        return "galeria";
    }

    @GetMapping("/admin")
    public String painelAdmin(@RequestParam(required = false) String senha, Model model) {
        if ("david123".equals(senha)) {
            model.addAttribute("fotos", repository.findAll());
            model.addAttribute("agendamentos", agendamentoRepository.findAll());
            model.addAttribute("autenticado", true);
        } else {
            model.addAttribute("autenticado", false);
        }
        return "admin";
    }

    @PostMapping("/admin/galeria/adicionar")
    public String adicionarFoto(@RequestParam String senha,
                                @RequestParam String titulo,
                                @RequestParam String categoria,
                                @RequestParam("arquivoImagem") MultipartFile arquivo) throws IOException {
        if ("david123".equals(senha) && !arquivo.isEmpty()) {
            String base64Image = Base64.getEncoder().encodeToString(arquivo.getBytes());
            String urlImagem = "data:" + arquivo.getContentType() + ";base64," + base64Image;

            FotoGaleria foto = new FotoGaleria(titulo, categoria, urlImagem);
            repository.save(foto);
        }
        return "redirect:/admin?senha=" + senha;
    }

    @PostMapping("/admin/galeria/deletar")
    public String deletarFoto(@RequestParam String senha, @RequestParam Long id) {
        if ("david123".equals(senha)) {
            repository.deleteById(id);
        }
        return "redirect:/admin?senha=" + senha;
    }

    @PostMapping("/admin/agendamento/deletar")
    public String deletarAgendamento(@RequestParam String senha, @RequestParam Long id) {
        if ("david123".equals(senha)) {
            agendamentoRepository.deleteById(id);
        }
        return "redirect:/admin?senha=" + senha;
    }
}