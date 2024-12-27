package android.hardware.fingerprint;

import android.content.Context;
import android.hardware.biometrics.common.CommonProps;
import android.hardware.biometrics.fingerprint.SensorLocation;
import android.hardware.biometrics.fingerprint.SensorProps;

import com.android.internal.R;

import com.samsung.android.bio.fingerprint.SemFingerprintManager;

public final class HidlFingerprintSensorConfig extends SensorProps {
    private static final boolean FEATURE_USE_SENSOR_RESOURCE_CONFIG = false;
    private int mModality;
    private int mSensorId;
    private int mStrength;

    public void parse(String config, Context context) throws IllegalArgumentException {
        String[] elems = config.split(":");
        if (elems.length < 3) {
            throw new IllegalArgumentException();
        }
        this.mSensorId = Integer.parseInt(elems[0]);
        this.mModality = Integer.parseInt(elems[1]);
        this.mStrength = Integer.parseInt(elems[2]);
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
        this.commonProps.maxEnrollmentsPerUser =
                context.getResources().getInteger(R.integer.config_fingerprintMaxTemplatesPerUser);
        this.halControlsIllumination = false;
        this.sensorLocations = new SensorLocation[1];
        this.commonProps.maxEnrollmentsPerUser =
                SemFingerprintManager.getMaxTemplateNumberFromSPF();
        switch (FingerprintManager.semGetSensorPosition()) {
            case 0:
                this.sensorType = (byte) 0;
                break;
            case 1:
                this.sensorType = (byte) 5;
                break;
            case 2:
                if ("google_touch_display_ultrasonic".contains("ultrasonic")) {
                    this.sensorType = (byte) 2;
                    break;
                } else if ("google_touch_display_ultrasonic".contains("optical")) {
                    this.sensorType = (byte) 3;
                    break;
                }
                break;
            case 3:
                this.sensorType = (byte) 1;
                break;
            case 4:
                this.sensorType = (byte) 4;
                break;
        }
        setSensorLocation(0, 0, 0);
    }

    private void setSensorLocation(int sensorLocationX, int sensorLocationY, int sensorRadius) {
        this.sensorLocations[0] = new SensorLocation();
        this.sensorLocations[0].display = "";
        this.sensorLocations[0].sensorLocationX = sensorLocationX;
        this.sensorLocations[0].sensorLocationY = sensorLocationY;
        this.sensorLocations[0].sensorRadius = sensorRadius;
    }

    private byte authenticatorStrengthToPropertyStrength(int strength) {
        switch (strength) {
            case 15:
                return (byte) 2;
            case 255:
                return (byte) 1;
            case 4095:
                return (byte) 0;
            default:
                throw new IllegalArgumentException("Unknown strength: " + strength);
        }
    }
}
