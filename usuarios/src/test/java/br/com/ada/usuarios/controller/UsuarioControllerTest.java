package br.com.ada.users.controller;

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

import br.com.ada.users.model.dto.PhoneDTO;
import br.com.ada.users.model.dto.UserCreationDTO;
import br.com.ada.users.model.dto.UserDTO;
import br.com.ada.users.model.dto.UserUpdateDTO;
import br.com.ada.users.model.entity.Phone;
import br.com.ada.users.model.mapper.PhoneMapper;
import br.com.ada.users.service.impl.UserServiceImpl;
import jakarta.persistence.EntityNotFoundException;

@SpringBootTest
public class UserControllerTest {

    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private UserController userController;

    @Mock
    private PhoneMapper phoneMapper;

    @Test
    public void testFindAll() {
        //arrange
        List<UserDTO> userDTOS = new ArrayList<>();
        Mockito.when(userService.findAll()).thenReturn(userDTOS);
        //act
        ResponseEntity<List<UserDTO>> response = userController.findAll();
        //assert
        Mockito.verify(userService).findAll();
        Assert.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        //Assert.assertEquals(userDTOS, response.getBody());
    }

    @Test
    public void findById() {
        //arrange
        UserDTO userDTO = new UserDTO();
        Mockito.when(userService.findById(anyString())).thenReturn(userDTO);
        //act
        ResponseEntity<UserDTO> response = userController.findById("1");
        //assert
        Mockito.verify(userService).findById("1");
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void create() {
        //arrange
        UserDTO userDTO = new UserDTO();
        UserCreationDTO userCreationDTO = new UserCreationDTO();
        Mockito.when(userService.create(userCreationDTO)).thenReturn(userDTO);
        //act
        ResponseEntity<UserDTO> response = userController.create(userCreationDTO);
        //assert
        Mockito.verify(userService).create(userCreationDTO);
        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void edit() {
        //arrange
        UserDTO userDTO = new UserDTO();
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
        Mockito.when(userService.edit("id", userUpdateDTO)).thenReturn(userDTO);
        //act
        ResponseEntity<UserDTO> response = userController.edit("id", userUpdateDTO);
        //assert
        Mockito.verify(userService).edit("id", userUpdateDTO);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void delete() {
        //arrange
        //act
        ResponseEntity<Object> response = userController.delete("id");
        //assert
        Mockito.verify(userService).delete("id");
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void findPhones() {
        //arrange
        final String id = UUID.randomUUID().toString();
        final List<Phone> phones = new ArrayList<>();
        Mockito.when(userService.findPhones(id)).thenReturn(phones);
        //act
        ResponseEntity<List<PhoneDTO>> response = (userController.findPhones(id));
        //assert
        Mockito.verify(userService).findPhones(id);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void findPhonesNoContent() {
        //arrange
        final String id = UUID.randomUUID().toString();
        Mockito.when(userService.findPhones(id)).thenThrow(EntityNotFoundException.class);
        //act
        ResponseEntity<List<PhoneDTO>> response = userController.findPhones(id);
        //assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

}

