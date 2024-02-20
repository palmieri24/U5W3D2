package alessiapalmieri.U5W3D2.entities;

import alessiapalmieri.U5W3D2.Enum.DeviceStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "devices")
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    private String deviceType;
    @Enumerated(EnumType.STRING)
    private DeviceStatus deviceStatus;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public Device(String deviceType, DeviceStatus deviceStatus, Employee employee) {
        this.deviceType = deviceType;
        this.deviceStatus = deviceStatus;
        this.employee = employee;
    }
}
