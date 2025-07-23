package android.hardware.fingerprint;

import android.hardware.biometrics.ComponentInfoInternal;
import android.hardware.biometrics.SensorProperties;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class FingerprintSensorProperties extends SensorProperties {
    public static final int TYPE_HOME_BUTTON = 5;
    public static final int TYPE_POWER_BUTTON = 4;
    public static final int TYPE_REAR = 1;
    public static final int TYPE_UDFPS_OPTICAL = 3;
    public static final int TYPE_UDFPS_ULTRASONIC = 2;
    public static final int TYPE_UNKNOWN = 0;
    final int mSensorType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface SensorType {
    }

    public static FingerprintSensorProperties from(FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal) {
        ArrayList arrayList = new ArrayList();
        Iterator<ComponentInfoInternal> it = fingerprintSensorPropertiesInternal.componentInfo.iterator();
        while (it.hasNext()) {
            arrayList.add(SensorProperties.ComponentInfo.from(it.next()));
        }
        return new FingerprintSensorProperties(fingerprintSensorPropertiesInternal.sensorId, fingerprintSensorPropertiesInternal.sensorStrength, arrayList, fingerprintSensorPropertiesInternal.sensorType);
    }

    public FingerprintSensorProperties(int i, int i2, List<SensorProperties.ComponentInfo> list, int i3) {
        super(i, i2, list);
        this.mSensorType = i3;
    }

    public int getSensorType() {
        return this.mSensorType;
    }
}
