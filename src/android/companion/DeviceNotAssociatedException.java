package android.companion;

/* loaded from: classes.dex */
public class DeviceNotAssociatedException extends RuntimeException {
    public DeviceNotAssociatedException(String str) {
        super("Device not associated with the current app: " + str);
    }
}
