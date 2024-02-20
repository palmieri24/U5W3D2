package alessiapalmieri.U5W3D2.DTO;

import jakarta.validation.constraints.NotEmpty;

public record NewDeviceDTO(
        @NotEmpty(message = "Device type is a mandatory field! Choose between SMARTPHONE, TABLET, LAPTOP")
        String deviceType
) {
}
