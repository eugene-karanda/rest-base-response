package org.overmind.baseresponse.example.user.service;

import org.overmind.baseresponse.example.user.model.User;
import org.overmind.br.response.Response;
import org.overmind.br.response.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.overmind.br.response.ResponseUtil.*;

@RestController
@RequestMapping("user")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("{name}")
    public Response<User> findOne(@PathVariable("name") String name) {
        return userRepository.findOne(name)
                .map(ResponseUtil::ok)
                .orElseGet(() ->
                        notFound("user with name " + name)
                );
    }

    @GetMapping("me")
    public Response<User> findOne() {
        return usedCached(User.of("Eugene", 24), "user");
    }
}