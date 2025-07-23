package android.hardware.biometrics;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class SensorProperties {
    public static final int STRENGTH_CONVENIENCE = 0;
    public static final int STRENGTH_STRONG = 2;
    public static final int STRENGTH_WEAK = 1;
    private final List<ComponentInfo> mComponentInfo;
    private final int mSensorId;
    private final int mSensorStrength;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Strength {
    }

    public static final class ComponentInfo {
        private final String mComponentId;
        private final String mFirmwareVersion;
        private final String mHardwareVersion;
        private final String mSerialNumber;
        private final String mSoftwareVersion;

        public ComponentInfo(String str, String str2, String str3, String str4, String str5) {
            this.mComponentId = str;
            this.mHardwareVersion = str2;
            this.mFirmwareVersion = str3;
            this.mSerialNumber = str4;
            this.mSoftwareVersion = str5;
        }

        public String getComponentId() {
            return this.mComponentId;
        }

        public String getHardwareVersion() {
            return this.mHardwareVersion;
        }

        public String getFirmwareVersion() {
            return this.mFirmwareVersion;
        }

        public String getSerialNumber() {
            return this.mSerialNumber;
        }

        public String getSoftwareVersion() {
            return this.mSoftwareVersion;
        }

        public static ComponentInfo from(ComponentInfoInternal componentInfoInternal) {
            return new ComponentInfo(componentInfoInternal.componentId, componentInfoInternal.hardwareVersion, componentInfoInternal.firmwareVersion, componentInfoInternal.serialNumber, componentInfoInternal.softwareVersion);
        }
    }

    public SensorProperties(int i, int i2, List<ComponentInfo> list) {
        this.mSensorId = i;
        this.mSensorStrength = i2;
        this.mComponentInfo = list;
    }

    public int getSensorId() {
        return this.mSensorId;
    }

    public int getSensorStrength() {
        return this.mSensorStrength;
    }

    public List<ComponentInfo> getComponentInfo() {
        return this.mComponentInfo;
    }

    public static SensorProperties from(SensorPropertiesInternal sensorPropertiesInternal) {
        ArrayList arrayList = new ArrayList();
        Iterator<ComponentInfoInternal> it = sensorPropertiesInternal.componentInfo.iterator();
        while (it.hasNext()) {
            arrayList.add(ComponentInfo.from(it.next()));
        }
        return new SensorProperties(sensorPropertiesInternal.sensorId, sensorPropertiesInternal.sensorStrength, arrayList);
    }
}
