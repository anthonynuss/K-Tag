/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.lazertag.users.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Map;

@Controller
class UserController {

    @Autowired
    private UserRepository usersRepository;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/users/new")
    public String initCreationForm(Map<String, Object> model) {
        logger.info("Entered into Controller Layer");
        Users user = new Users();
        model.put("user", user);
        return "users/createOrUpdateuserForm";
    }

    @PostMapping("/users/new")
    public String processCreationForm(@Valid Users user, BindingResult result) {
        if (result.hasErrors()) {
            return "users/createOrUpdateuserForm";
        } else {
            usersRepository.save(user);
            return "redirect:/users";
        }
    }

    @GetMapping("/users")
    public String getAllusers(Map<String, Object> model) {

        logger.info("Entered into Controller Layer");
        Collection<Users> results = usersRepository.findAll();
        logger.info("Number of Records Fetched:" + results.size());
        model.put("selections", results);
        return "users/usersList";
    }

    @GetMapping("/users/{userId}")
    public String finduserById(@PathVariable("userId") int id, Map<String, Object> model) {

        logger.info("Entered into Controller Layer");
        Collection<Users> results = usersRepository.findById(id);
        logger.info("Number of Records Fetched:" + results.size());
        model.put("selections", results);
        return "users/usersList";
    }

    @GetMapping("/users/find")
    public String finduser(Map<String, Object> model) {
        model.put("user", new Users());
        return "users/findusers";
    }

}
