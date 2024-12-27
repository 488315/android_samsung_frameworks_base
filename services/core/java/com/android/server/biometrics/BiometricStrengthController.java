package com.android.server.biometrics;

import android.provider.DeviceConfig;
import android.util.Slog;

import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.asks.ASKSManagerService$$ExternalSyntheticOutline0;

import java.util.HashMap;
import java.util.Iterator;

public final class BiometricStrengthController {
    public final BiometricStrengthController$$ExternalSyntheticLambda0 mDeviceConfigListener =
            new DeviceConfig
                    .OnPropertiesChangedListener() { // from class:
                                                     // com.android.server.biometrics.BiometricStrengthController$$ExternalSyntheticLambda0
                public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                    BiometricStrengthController biometricStrengthController =
                            BiometricStrengthController.this;
                    biometricStrengthController.getClass();
                    if (properties.getKeyset().contains("biometric_strengths")) {
                        biometricStrengthController.updateStrengths();
                    }
                }
            };
    public final BiometricService mService;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.server.biometrics.BiometricStrengthController$$ExternalSyntheticLambda0] */
    public BiometricStrengthController(BiometricService biometricService) {
        this.mService = biometricService;
    }

    public final void updateStrengths() {
        String string = DeviceConfig.getString("biometrics", "biometric_strengths", "null");
        boolean equals = "null".equals(string);
        BiometricService biometricService = this.mService;
        if (equals || string.isEmpty()) {
            Iterator it = biometricService.mSensors.iterator();
            while (it.hasNext()) {
                BiometricSensor biometricSensor = (BiometricSensor) it.next();
                StringBuilder sb = new StringBuilder("updateStrengths: revert sensorId=");
                sb.append(biometricSensor.id);
                sb.append(" to oemStrength=");
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        sb, biometricSensor.oemStrength, "BiometricStrengthController");
                biometricSensor.updateStrength(biometricSensor.oemStrength);
            }
            return;
        }
        HashMap hashMap = null;
        if (string.isEmpty()) {
            Slog.d("BiometricStrengthController", "Flags are null or empty");
        } else {
            HashMap hashMap2 = new HashMap();
            try {
                for (String str : string.split(",")) {
                    String[] split = str.split(":");
                    hashMap2.put(
                            Integer.valueOf(Integer.parseInt(split[0])),
                            Integer.valueOf(Integer.parseInt(split[1])));
                }
                hashMap = hashMap2;
            } catch (Exception unused) {
                Slog.e("BiometricStrengthController", "Can't parse flag: ".concat(string));
            }
        }
        if (hashMap == null) {
            return;
        }
        Iterator it2 = biometricService.mSensors.iterator();
        while (it2.hasNext()) {
            BiometricSensor biometricSensor2 = (BiometricSensor) it2.next();
            int i = biometricSensor2.id;
            if (hashMap.containsKey(Integer.valueOf(i))) {
                int intValue = ((Integer) hashMap.get(Integer.valueOf(i))).intValue();
                ASKSManagerService$$ExternalSyntheticOutline0.m(
                        i,
                        intValue,
                        "updateStrengths: update sensorId=",
                        " to newStrength=",
                        "BiometricStrengthController");
                biometricSensor2.updateStrength(intValue);
            }
        }
    }
}
