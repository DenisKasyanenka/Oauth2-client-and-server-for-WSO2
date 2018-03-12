package wso2.integration.server.repository;

import org.springframework.stereotype.Repository;
import wso2.integration.server.domain.Role;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
}
