package new_butter.new_butter.repository;

import new_butter.new_butter.domain.Role;
import new_butter.new_butter.domain.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(ERole name);
}