package android.hardware.fingerprint;

import android.content.Context;
import android.hardware.biometrics.common.CommonProps;
import android.hardware.biometrics.fingerprint.SensorLocation;
import android.hardware.biometrics.fingerprint.SensorProps;
import com.android.internal.R;
import com.samsung.android.bio.fingerprint.SemFingerprintManager;

/* loaded from: classes2.dex */
public final class HidlFingerprintSensorConfig extends SensorProps {
    private static final boolean FEATURE_USE_SENSOR_RESOURCE_CONFIG = false;
    private int mModality;
    private int mSensorId;
    private int mStrength;

    public void parse(String str, Context context) throws IllegalArgumentException {
        String[] split = str.split(":");
        if (split.length < 3) {
            throw new IllegalArgumentException();
        }
        this.mSensorId = Integer.parseInt(split[0]);
        this.mModality = Integer.parseInt(split[1]);
        this.mStrength = Integer.parseInt(split[2]);
        mapHidlToAidlSensorConfiguration(context);
    }

    public int getModality() {
        return this.mModality;
    }

    private void mapHidlToAidlSensorConfiguration(Context context) {
        this.commonProps = new CommonProps();
        this.commonProps.componentInfo = null;
        this.commonProps.sensorId = this.mSensorId;
        this.commonProps.sensorStrength = authenticatorStrengthToPropertyStrength(this.mStrength);
        this.commonProps.maxEnrollmentsPerUser = context.getResources().getInteger(R.integer.config_fingerprintMaxTemplatesPerUser);
        this.halControlsIllumination = false;
        this.sensorLocations = new SensorLocation[1];
        this.commonProps.maxEnrollmentsPerUser = SemFingerprintManager.getMaxTemplateNumberFromSPF();
        int semGetSensorPosition = FingerprintManager.semGetSensorPosition();
        if (semGetSensorPosition == 0) {
            this.sensorType = (byte) 0;
        } else if (semGetSensorPosition == 1) {
            this.sensorType = (byte) 5;
        } else if (semGetSensorPosition == 2) {
            this.sensorType = (byte) 2;
        } else if (semGetSensorPosition == 3) {
            this.sensorType = (byte) 1;
        } else if (semGetSensorPosition == 4) {
            this.sensorType = (byte) 4;
        }
        setSensorLocation(0, 0, 0);
    }

    private void setSensorLocation(int i, int i2, int i3) {
        this.sensorLocations[0] = new SensorLocation();
        this.sensorLocations[0].display = "";
        this.sensorLocations[0].sensorLocationX = i;
        this.sensorLocations[0].sensorLocationY = i2;
        this.sensorLocations[0].sensorRadius = i3;
    }

    private byte authenticatorStrengthToPropertyStrength(int i) {
        if (i == 15) {
            return (byte) 2;
        }
        if (i == 255) {
            return (byte) 1;
        }
        if (i == 4095) {
            return (byte) 0;
        }
        throw new IllegalArgumentException("Unknown strength: " + i);
    }
}
