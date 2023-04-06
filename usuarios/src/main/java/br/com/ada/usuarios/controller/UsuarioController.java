package br.com.ada.usuarios.controller;

import br.com.ada.usuarios.model.dto.PhoneDTO;
import br.com.ada.usuarios.model.dto.UsuarioCreationDTO;
import br.com.ada.usuarios.model.dto.UsuarioDTO;
import br.com.ada.usuarios.model.dto.UsuarioUpdateDTO;
import br.com.ada.usuarios.model.mapper.PhoneMapper;
import br.com.ada.usuarios.service.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@Slf4j
public class UsuarioController {

    protected final UsuarioService service;
    private final PhoneMapper phoneMapper;

    public UsuarioController(final UsuarioService service, final PhoneMapper phoneMapper) {
        this.service = service;
        this.phoneMapper = phoneMapper;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> findAll() {
        List<UsuarioDTO> result = service.findAll();
        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(result);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> findById(@PathVariable("id") String id) {
        try {
            return ResponseEntity.ok(service.findById(id));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> create(@RequestBody @Valid UsuarioCreationDTO creationDTO) {
        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(service.create(creationDTO));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> edit(@PathVariable("id") String id,
                                        @RequestBody @Valid UsuarioUpdateDTO updateDTO) {
        try {
            return ResponseEntity.ok(service.edit(id, updateDTO));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        try {
            service.delete(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/{id}/phones")
    public ResponseEntity<List<PhoneDTO>> findPhones(@PathVariable("id") String id) {
        try {
            List<PhoneDTO> phonesDTO = phoneMapper.parseListDTO(service.findPhones(id));
            return ResponseEntity.ok(phonesDTO);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
