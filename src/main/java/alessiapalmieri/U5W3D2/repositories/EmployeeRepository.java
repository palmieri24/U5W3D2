package alessiapalmieri.U5W3D2.repositories;

import alessiapalmieri.U5W3D2.entities.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    Optional<Employee> findByEmail(String email);
    Optional<Employee> findByUsername(String username);
    Page<Employee> findByName(String name, Pageable pageable);
    Page<Employee> findByLastname(String lastname, Pageable pageable);
    Page<Employee> findByNameAndLastname(String name, String lastname, Pageable pageable);
}
