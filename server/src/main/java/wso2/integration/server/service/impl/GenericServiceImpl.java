package wso2.integration.server.service.impl;

import wso2.integration.server.domain.User;
import wso2.integration.server.repository.UserRepository;
import wso2.integration.server.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenericServiceImpl implements GenericService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
