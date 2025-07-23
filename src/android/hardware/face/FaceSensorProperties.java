package android.hardware.face;

import android.hardware.biometrics.ComponentInfoInternal;
import android.hardware.biometrics.SensorProperties;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class FaceSensorProperties extends SensorProperties {
    public static final int TYPE_IR = 2;
    public static final int TYPE_RGB = 1;
    public static final int TYPE_UNKNOWN = 0;
    final int mSensorType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface SensorType {
    }

    public static FaceSensorProperties from(FaceSensorPropertiesInternal faceSensorPropertiesInternal) {
        ArrayList arrayList = new ArrayList();
        Iterator<ComponentInfoInternal> it = faceSensorPropertiesInternal.componentInfo.iterator();
        while (it.hasNext()) {
            arrayList.add(SensorProperties.ComponentInfo.from(it.next()));
        }
        return new FaceSensorProperties(faceSensorPropertiesInternal.sensorId, faceSensorPropertiesInternal.sensorStrength, arrayList, faceSensorPropertiesInternal.sensorType);
    }

    public FaceSensorProperties(int i, int i2, List<SensorProperties.ComponentInfo> list, int i3) {
        super(i, i2, list);
        this.mSensorType = i3;
    }

    public int getSensorType() {
        return this.mSensorType;
    }
}
