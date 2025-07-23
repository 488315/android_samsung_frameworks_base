package android.mtp;

/* loaded from: classes3.dex */
public class MtpDeviceInfo {
    private int[] mDevicePropertySupported;
    private int[] mEventsSupported;
    private String mManufacturer;
    private String mModel;
    private int[] mOperationsSupported;
    private String mSerialNumber;
    private String mVersion;

    private MtpDeviceInfo() {
    }

    public final String getManufacturer() {
        return this.mManufacturer;
    }

    public final String getModel() {
        return this.mModel;
    }

    public final String getVersion() {
        return this.mVersion;
    }

    public final String getSerialNumber() {
        return this.mSerialNumber;
    }

    public final int[] getOperationsSupported() {
        return this.mOperationsSupported;
    }

    public final int[] getEventsSupported() {
        return this.mEventsSupported;
    }

    public final int[] getDevicePropertySupported() {
        return this.mDevicePropertySupported;
    }

    public boolean isOperationSupported(int i) {
        return isSupported(this.mOperationsSupported, i);
    }

    public boolean isEventSupported(int i) {
        return isSupported(this.mEventsSupported, i);
    }

    public boolean isDevicePropertySupported(int i) {
        return isSupported(this.mDevicePropertySupported, i);
    }

    private static boolean isSupported(int[] iArr, int i) {
        for (int i2 : iArr) {
            if (i2 == i) {
                return true;
            }
        }
        return false;
    }
}
