package android.os;

import android.content.Context;
import android.util.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes3.dex */
public class HardwarePropertiesManager {
    public static final int DEVICE_TEMPERATURE_BATTERY = 2;
    public static final int DEVICE_TEMPERATURE_CPU = 0;
    public static final int DEVICE_TEMPERATURE_GPU = 1;
    public static final int DEVICE_TEMPERATURE_SKIN = 3;
    private static final String TAG = "HardwarePropertiesManager";
    public static final int TEMPERATURE_CURRENT = 0;
    public static final int TEMPERATURE_SHUTDOWN = 2;
    public static final int TEMPERATURE_THROTTLING = 1;
    public static final int TEMPERATURE_THROTTLING_BELOW_VR_MIN = 3;
    public static final float UNDEFINED_TEMPERATURE = -3.4028235E38f;
    private final Context mContext;
    private final IHardwarePropertiesManager mService;

    @Retention(RetentionPolicy.SOURCE)
    public @interface DeviceTemperatureType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TemperatureSource {
    }

    public HardwarePropertiesManager(Context context, IHardwarePropertiesManager iHardwarePropertiesManager) {
        this.mContext = context;
        this.mService = iHardwarePropertiesManager;
    }

    public float[] getDeviceTemperatures(int i, int i2) {
        if (i != 0 && i != 1 && i != 2 && i != 3) {
            Log.w(TAG, "Unknown device temperature type.");
            return new float[0];
        }
        if (i2 == 0 || i2 == 1 || i2 == 2 || i2 == 3) {
            try {
                return this.mService.getDeviceTemperatures(this.mContext.getOpPackageName(), i, i2);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        Log.w(TAG, "Unknown device temperature source.");
        return new float[0];
    }

    public CpuUsageInfo[] getCpuUsages() {
        try {
            return this.mService.getCpuUsages(this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public float[] getFanSpeeds() {
        try {
            return this.mService.getFanSpeeds(this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
