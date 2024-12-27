package android.companion;

public class DeviceNotAssociatedException extends RuntimeException {
    public DeviceNotAssociatedException(String deviceName) {
        super("Device not associated with the current app: " + deviceName);
    }
}
