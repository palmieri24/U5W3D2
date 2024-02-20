package alessiapalmieri.U5W3D2.controllers;

import alessiapalmieri.U5W3D2.DTO.NewEmployeeDTO;
import alessiapalmieri.U5W3D2.entities.Employee;
import alessiapalmieri.U5W3D2.exceptions.BadRequestException;
import alessiapalmieri.U5W3D2.exceptions.NotFoundException;
import alessiapalmieri.U5W3D2.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public Page<Employee> getEmployee(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String orderBy) {
        return employeeService.getEmployees(page, size, orderBy);
    }

    @GetMapping(value = "/{id}")
    public Employee findById(@PathVariable UUID id){
        return employeeService.findById(id);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Employee findByIdAndUpdate(@PathVariable UUID id, @RequestBody @Validated Employee body, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        } else {
            return employeeService.findByIdAndUpdate(id, body);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void findByIdAndDelete(@PathVariable UUID id) throws NotFoundException {
        employeeService.findByIdAndDelete(id);
    }

//    @PutMapping("/update/img/{id}")
//    public String uploadImg(@RequestParam("avatar") MultipartFile file, @PathVariable UUID id) throws IOException {
//        Employee found = employeeService.findById(id);
//        return employeeService.uploadAvatar(file, id, found);
//    }

    @GetMapping("/me")
    public UserDetails getCurrentProfile(@AuthenticationPrincipal UserDetails currentEmployee) {
        return currentEmployee;
    }

    @DeleteMapping("/me")
    public void deleteCurrent(@AuthenticationPrincipal Employee currentEmployee) {
        employeeService.findByIdAndDelete(currentEmployee.getId());
    }
}

