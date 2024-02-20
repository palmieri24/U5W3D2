package alessiapalmieri.U5W3D2.services;

import alessiapalmieri.U5W3D2.DTO.EmployeeLoginDTO;
import alessiapalmieri.U5W3D2.DTO.NewEmployeeDTO;
import alessiapalmieri.U5W3D2.entities.Employee;
import alessiapalmieri.U5W3D2.exceptions.BadRequestException;
import alessiapalmieri.U5W3D2.exceptions.UnauthorizedException;
import alessiapalmieri.U5W3D2.repositories.EmployeeRepository;
import alessiapalmieri.U5W3D2.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private PasswordEncoder bcrypt;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private JWTTools jwtTools;

    public String authenticateUserAndGenerateToken(EmployeeLoginDTO payload) {
        Employee employee = employeeService.findByEmail(payload.email());
        if (bcrypt.matches(payload.password(), employee.getPassword())) {
            return jwtTools.createToken(employee);
        } else {
            throw new UnauthorizedException("Credenziali sbagliate!");
        }
    }

    public Employee save(NewEmployeeDTO payload) {
        employeeRepository.findByEmail(payload.email()).ifPresent(employee -> {
            throw new BadRequestException("L'email " + employee.getEmail() + " è già in uso!");
        });

        Employee newEmployee = new Employee(payload.name(), payload.lastname(),
                payload.email(), bcrypt.encode(payload.password()),
                "https://ui-avatars.com/api/?name" + payload.name() + "+" + payload.lastname());
        return employeeRepository.save(newEmployee);
    }
}
