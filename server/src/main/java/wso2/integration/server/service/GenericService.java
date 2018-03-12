package wso2.integration.server.service;

import wso2.integration.server.domain.User;

public interface GenericService {
    User findByUsername(String username);
}
