package com.android.server.biometrics;

import android.provider.DeviceConfig;
import android.util.Slog;
import com.android.internal.os.BackgroundThread;
import com.android.internal.util.jobs.XmlUtils;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes.dex */
public class BiometricStrengthController {
  public DeviceConfig.OnPropertiesChangedListener mDeviceConfigListener =
      new DeviceConfig
          .OnPropertiesChangedListener() { // from class:
                                           // com.android.server.biometrics.BiometricStrengthController$$ExternalSyntheticLambda0
        public final void onPropertiesChanged(DeviceConfig.Properties properties) {
          BiometricStrengthController.this.lambda$new$0(properties);
        }
      };
  public final BiometricService mService;

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$new$0(DeviceConfig.Properties properties) {
    if (properties.getKeyset().contains("biometric_strengths")) {
      updateStrengths();
    }
  }

  public BiometricStrengthController(BiometricService biometricService) {
    this.mService = biometricService;
  }

  public void startListening() {
    DeviceConfig.addOnPropertiesChangedListener(
        "biometrics", BackgroundThread.getExecutor(), this.mDeviceConfigListener);
  }

  public void updateStrengths() {
    String string = DeviceConfig.getString("biometrics", "biometric_strengths", "null");
    if ("null".equals(string) || string.isEmpty()) {
      revertStrengths();
    } else {
      updateStrengths(string);
    }
  }

  public final void updateStrengths(String str) {
    Map idToStrengthMap = getIdToStrengthMap(str);
    if (idToStrengthMap == null) {
      return;
    }
    Iterator it = this.mService.mSensors.iterator();
    while (it.hasNext()) {
      BiometricSensor biometricSensor = (BiometricSensor) it.next();
      int i = biometricSensor.f1650id;
      if (idToStrengthMap.containsKey(Integer.valueOf(i))) {
        int intValue = ((Integer) idToStrengthMap.get(Integer.valueOf(i))).intValue();
        Slog.d(
            "BiometricStrengthController",
            "updateStrengths: update sensorId=" + i + " to newStrength=" + intValue);
        biometricSensor.updateStrength(intValue);
      }
    }
  }

  public final void revertStrengths() {
    Iterator it = this.mService.mSensors.iterator();
    while (it.hasNext()) {
      BiometricSensor biometricSensor = (BiometricSensor) it.next();
      Slog.d(
          "BiometricStrengthController",
          "updateStrengths: revert sensorId="
              + biometricSensor.f1650id
              + " to oemStrength="
              + biometricSensor.oemStrength);
      biometricSensor.updateStrength(biometricSensor.oemStrength);
    }
  }

  public static Map getIdToStrengthMap(String str) {
    if (str == null || str.isEmpty()) {
      Slog.d("BiometricStrengthController", "Flags are null or empty");
      return null;
    }
    HashMap hashMap = new HashMap();
    try {
      for (String str2 : str.split(",")) {
        String[] split = str2.split(XmlUtils.STRING_ARRAY_SEPARATOR);
        hashMap.put(
            Integer.valueOf(Integer.parseInt(split[0])),
            Integer.valueOf(Integer.parseInt(split[1])));
      }
      return hashMap;
    } catch (Exception unused) {
      Slog.e("BiometricStrengthController", "Can't parse flag: " + str);
      return null;
    }
  }
}
