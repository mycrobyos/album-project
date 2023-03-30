package br.com.ada.users.controller;

import static org.mockito.ArgumentMatchers.anyString;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.ada.users.model.dto.PhoneCreationDTO;
import br.com.ada.users.model.dto.PhoneDTO;
import br.com.ada.users.model.dto.PhoneUpdateDTO;
import br.com.ada.users.service.impl.PhoneServiceImpl;

@SpringBootTest
public class PhoneControllerTest {
    @Mock
    private PhoneServiceImpl phoneService;

    @InjectMocks
    private PhoneController phoneController;

    @Test
    public void testFindAll() {
        //arrange
        List<PhoneDTO> phoneDTOS = new ArrayList<>();
        Mockito.when(phoneService.findAll()).thenReturn(phoneDTOS);
        //act
        ResponseEntity<List<PhoneDTO>> response = phoneController.findAll();
        //assert
        Mockito.verify(phoneService).findAll();
        Assert.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        //Assert.assertEquals(phoneDTOS, response.getBody());
    }

    @Test
    public void findById() {
        //arrange
        PhoneDTO userDTO = new PhoneDTO();
        Mockito.when(phoneService.findById(anyString())).thenReturn(userDTO);
        //act
        ResponseEntity<PhoneDTO> response = phoneController.findById("1");
        //assert
        Mockito.verify(phoneService).findById("1");
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void create() {
        //arrange
        PhoneDTO userDTO = new PhoneDTO();
        PhoneCreationDTO phoneCreationDTO = new PhoneCreationDTO();
        Mockito.when(phoneService.create(phoneCreationDTO)).thenReturn(userDTO);
        //act
        ResponseEntity<PhoneDTO> response = phoneController.create(phoneCreationDTO);
        //assert
        Mockito.verify(phoneService).create(phoneCreationDTO);
        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void edit() {
        //arrange
        PhoneDTO userDTO = new PhoneDTO();
        PhoneUpdateDTO phoneUpdateDTO = new PhoneUpdateDTO();
        Mockito.when(phoneService.edit("id", phoneUpdateDTO)).thenReturn(userDTO);
        //act
        ResponseEntity<PhoneDTO> response = phoneController.edit("id", phoneUpdateDTO);
        //assert
        Mockito.verify(phoneService).edit("id", phoneUpdateDTO);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void delete() {
        //arrange
        //act
        ResponseEntity<Object> response = phoneController.delete("id");
        //assert
        Mockito.verify(phoneService).delete("id");
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}