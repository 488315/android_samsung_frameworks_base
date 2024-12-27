package android.hardware.face;

import android.content.Context;
import android.hardware.biometrics.common.CommonProps;
import android.hardware.biometrics.face.SensorProps;
import com.android.internal.R;

/* loaded from: classes2.dex */
public final class HidlFaceSensorConfig extends SensorProps {
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
        mapHidlToAidlFaceSensorConfigurations(context);
    }

    public int getModality() {
        return this.mModality;
    }

    private void mapHidlToAidlFaceSensorConfigurations(Context context) {
        this.commonProps = new CommonProps();
        this.commonProps.sensorId = this.mSensorId;
        this.commonProps.sensorStrength = authenticatorStrengthToPropertyStrength(this.mStrength);
        this.halControlsPreview = context.getResources().getBoolean(R.bool.config_faceAuthSupportsSelfIllumination);
        this.commonProps.maxEnrollmentsPerUser = context.getResources().getInteger(R.integer.config_faceMaxTemplatesPerUser);
        this.commonProps.componentInfo = null;
        this.supportsDetectInteraction = false;
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
