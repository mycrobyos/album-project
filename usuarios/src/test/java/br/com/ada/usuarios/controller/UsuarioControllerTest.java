package br.com.ada.usuarios.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.ada.usuarios.model.dto.PhoneDTO;
import br.com.ada.usuarios.model.dto.UsuarioCreationDTO;
import br.com.ada.usuarios.model.dto.UsuarioDTO;
import br.com.ada.usuarios.model.dto.UsuarioUpdateDTO;
import br.com.ada.usuarios.model.entity.Phone;
import br.com.ada.usuarios.model.mapper.PhoneMapper;
import br.com.ada.usuarios.service.impl.UsuarioServiceImpl;
import jakarta.persistence.EntityNotFoundException;

@SpringBootTest
public class UsuarioControllerTest {

    @Mock
    private UsuarioServiceImpl usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    @Mock
    private PhoneMapper phoneMapper;

    @Test
    public void testFindAll() {
        //arrange
        List<UsuarioDTO> usuarioDTOS = new ArrayList<>();
        Mockito.when(usuarioService.findAll()).thenReturn(usuarioDTOS);
        //act
        ResponseEntity<List<UsuarioDTO>> response = usuarioController.findAll();
        //assert
        Mockito.verify(usuarioService).findAll();
        Assert.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        //Assert.assertEquals(usuarioDTOS, response.getBody());
    }

    @Test
    public void findById() {
        //arrange
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        Mockito.when(usuarioService.findById(anyString())).thenReturn(usuarioDTO);
        //act
        ResponseEntity<UsuarioDTO> response = usuarioController.findById("1");
        //assert
        Mockito.verify(usuarioService).findById("1");
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void create() {
        //arrange
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        UsuarioCreationDTO usuarioCreationDTO = new UsuarioCreationDTO();
        Mockito.when(usuarioService.create(usuarioCreationDTO)).thenReturn(usuarioDTO);
        //act
        ResponseEntity<UsuarioDTO> response = usuarioController.create(usuarioCreationDTO);
        //assert
        Mockito.verify(usuarioService).create(usuarioCreationDTO);
        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void edit() {
        //arrange
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        UsuarioUpdateDTO usuarioUpdateDTO = new UsuarioUpdateDTO();
        Mockito.when(usuarioService.edit("id", usuarioUpdateDTO)).thenReturn(usuarioDTO);
        //act
        ResponseEntity<UsuarioDTO> response = usuarioController.edit("id", usuarioUpdateDTO);
        //assert
        Mockito.verify(usuarioService).edit("id", usuarioUpdateDTO);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void delete() {
        //arrange
        //act
        ResponseEntity<Object> response = usuarioController.delete("id");
        //assert
        Mockito.verify(usuarioService).delete("id");
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void findPhones() {
        //arrange
        final String id = UUID.randomUUID().toString();
        final List<Phone> phones = new ArrayList<>();
        Mockito.when(usuarioService.findPhones(id)).thenReturn(phones);
        //act
        ResponseEntity<List<PhoneDTO>> response = (usuarioController.findPhones(id));
        //assert
        Mockito.verify(usuarioService).findPhones(id);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void findPhonesNoContent() {
        //arrange
        final String id = UUID.randomUUID().toString();
        Mockito.when(usuarioService.findPhones(id)).thenThrow(EntityNotFoundException.class);
        //act
        ResponseEntity<List<PhoneDTO>> response = usuarioController.findPhones(id);
        //assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

}

