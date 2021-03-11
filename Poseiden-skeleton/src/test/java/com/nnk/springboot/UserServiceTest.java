package com.nnk.springboot;

import com.nnk.springboot.Services.UserService;
import com.nnk.springboot.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class UserServiceTest {

    @Autowired
    private UserService userService;

    private String username = "Username";
    private String fullname = "Fullname";
    private String password = "Abc1234!";
    private String role = RoleEnum.USER.getCode();

    @Test
    public void addUserTest(){
        User user = new User(username, fullname, password, role);

        User inDb = userService.add(user);
        assertNotNull(inDb.getId());
        assertEquals(fullname, inDb.getFullname());
        assertEquals(username, inDb.getUsername());
        assertNotEquals(password, inDb.getPassword());
    }

    @Test
    public void findUserByIdTest(){
        User user = new User(username, fullname, password, role);
        user = userService.add(user);
        assertTrue(userService.findById(user.getId()).isPresent());
    }

    @Test
    public void findUserByUsernameTest(){
        User user = new User(username, fullname, password, role);
        user = userService.add(user);
        assertTrue(userService.findByUsername(username).isPresent());
    }

    @Test
    public void updateUserTest(){
        User user = new User(username, fullname, password, role);

        user = userService.add(user);
        UserDto nuUser = new UserDto(user.getId(), "motdepasse", "Nom", user.getRole());

        user = userService.update(nuUser, user.getId());

        User inDb = userService.findById(user.getId()).get();
        assertEquals("Nom", inDb.getFullname());
        assertEquals(username, inDb.getUsername());
        assertNotEquals(password, inDb.getPassword());
        assertNotEquals("motdepasse",inDb.getPassword());
    }

    @Test
    public void deleteUserTest(){
        int count = userService.findAll().size();
        User user = new User(username, fullname, password, role);

        user = userService.add(user);

        assertTrue(userService.findAll().size() > count);

        userService.delete(user.getId());

        assertEquals(count, userService.findAll().size());
    }
}
