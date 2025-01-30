package com.samsung.android.globalactions.util;

import android.content.Context;
import android.os.SystemProperties;
import android.telephony.TelephonyManager;
import com.android.internal.telephony.TelephonyProperties;

/* loaded from: classes5.dex */
public class TelephonyManagerWrapper {
  private final boolean mHasTelephonyRadio;
  private final TelephonyManager mTelephonyManager;

  public TelephonyManagerWrapper(Context context) {
    TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
    this.mTelephonyManager = telephonyManager;
    this.mHasTelephonyRadio = telephonyManager.isVoiceCapable();
  }

  public boolean hasTelephonyRadio() {
    return this.mHasTelephonyRadio;
  }

  public boolean isDataEnabled() {
    return this.mTelephonyManager.isDataEnabled();
  }

  public boolean hasAnySim() {
    TelephonyManager telephonyManager = this.mTelephonyManager;
    if (telephonyManager != null) {
      int simCount = telephonyManager.getActiveModemCount();
      for (int i = 0; i < simCount; i++) {
        int state = this.mTelephonyManager.getSimState(i);
        if (state != 1 && state != 0) {
          return true;
        }
      }
      return false;
    }
    return false;
  }

  public boolean isSimLock() {
    for (int i = 0; i < this.mTelephonyManager.getActiveModemCount(); i++) {
      int simState = getSimState(i);
      if (simState == 2 || simState == 3 || simState == 12) {
        return true;
      }
    }
    return false;
  }

  public int getSimState(int slotId) {
    int simState = this.mTelephonyManager.getSimState(slotId);
    if (simState == 4) {
      return getPersoLockedState(slotId);
    }
    return simState;
  }

  private int getPersoLockedState(int slotId) {
    return "PERSO_LOCKED"
            .equals(
                getSimStateSystemProperty(
                    TelephonyProperties.PROPERTY_SIM_STATE, slotId, "NOT_READY"))
        ? 12
        : 0;
  }

  private String getSimStateSystemProperty(String property, int slot, String defaultVal) {
    String propVal = null;
    String prop = SystemProperties.get(property);
    if (prop != null && prop.length() > 0) {
      String[] values = prop.split(",");
      if (slot >= 0 && slot < values.length && values[slot] != null) {
        propVal = values[slot];
      }
    }
    return propVal == null ? defaultVal : propVal;
  }
}
