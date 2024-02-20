package alessiapalmieri.U5W3D2.services;

import alessiapalmieri.U5W3D2.DTO.NewDeviceDTO;
import alessiapalmieri.U5W3D2.DTO.NewEmployeeDeviceDTO;
import alessiapalmieri.U5W3D2.DTO.NewStatusDTO;
import alessiapalmieri.U5W3D2.Enum.DeviceStatus;
import alessiapalmieri.U5W3D2.entities.Device;
import alessiapalmieri.U5W3D2.entities.Employee;
import alessiapalmieri.U5W3D2.exceptions.BadRequestException;
import alessiapalmieri.U5W3D2.exceptions.NotFoundException;
import alessiapalmieri.U5W3D2.repositories.DeviceRepository;
import alessiapalmieri.U5W3D2.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class DeviceService {
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    public Page<Device> getDevices(int page, int size, String orderBy){
        Pageable pageable = PageRequest.of(page,size, Sort.by(orderBy));
        return deviceRepository.findAll(pageable);
    }

    public Device findById(UUID id){
        return deviceRepository.findById(id).orElseThrow(()->new NotFoundException(id));
    }
    public void findByIdAndDelete(UUID id) throws NotFoundException {
        Device found = this.findById(id);
        deviceRepository.delete(found);
    }

    public Device save(NewDeviceDTO body) {
        Device newDevice = new Device();
        newDevice.setDeviceType(body.deviceType());
        newDevice.setDeviceStatus(DeviceStatus.AVAILABLE);
        return deviceRepository.save(newDevice);
    }

    public Device findByIdAndUpdateStatus(UUID id, NewStatusDTO body) {
        Device found = deviceRepository.findById(id).orElseThrow(() -> new NotFoundException(id));

        switch (body.deviceStatus().trim().toUpperCase()) {
            case "AVAILABLE":
                found.setEmployee(null);
                found.setDeviceType(found.getDeviceType());
                found.setDeviceStatus(DeviceStatus.AVAILABLE);
                found.setId(found.getId());
                return deviceRepository.save(found);

            case "DISPOSED":
                found.setEmployee(null);
                found.setDeviceType(found.getDeviceType());
                found.setDeviceStatus(DeviceStatus.DISPOSED);
                found.setId(found.getId());
                return deviceRepository.save(found);

            case "MAINTENANCE":
                found.setEmployee(null);
                found.setDeviceType(found.getDeviceType());
                found.setDeviceStatus(DeviceStatus.MAINTENANCE);
                found.setId(found.getId());
                return deviceRepository.save(found);

            case "ASSIGNED":
                throw new BadRequestException("To assign a device, change its user and it will be done automatically");

            default:
                throw new BadRequestException("Value entered is invalid or not of type String! Choose between AVAILABLE, MAINTENANCE, DISPOSED");
        }
    }
    public Device findByIdAndUpdateEmployee(UUID id, NewEmployeeDeviceDTO body) {
        Device found = deviceRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        Optional<Device> idFound = deviceRepository.findByEmployee_id(body.id());

        if (idFound.isEmpty()) {
            Employee employeeFound = employeeRepository.findById(body.id()).orElseThrow(() -> new NotFoundException(body.id()));
            found.setEmployee(employeeFound);
            found.setDeviceType(found.getDeviceType());
            found.setDeviceStatus(DeviceStatus.ASSIGNED);
            found.setId(found.getId());
            return deviceRepository.save(found);
        } else if(found.getDeviceStatus() == DeviceStatus.ASSIGNED) {
                throw new BadRequestException("Device already ASSIGNED! If you want to change user make it AVAILABLE first!");
            } else {
            throw new BadRequestException("The employee already has a Device!");
        }
    }
}
