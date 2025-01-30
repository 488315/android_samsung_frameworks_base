package com.samsung.android.globalactions.util;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.samsung.android.globalactions.presentation.features.Features;

/* loaded from: classes5.dex */
public class SystemConditionChecker implements ConditionChecker {
  private static final String TAG = "SystemConditionChecker";
  private final Features mFeatures;
  private final LogWrapper mLogWrapper;
  private final UtilFactory mUtilFactory;

  public SystemConditionChecker(UtilFactory utilFactory, Features features, LogWrapper logWrapper) {
    this.mUtilFactory = utilFactory;
    this.mFeatures = features;
    this.mLogWrapper = logWrapper;
  }

  /* renamed from: com.samsung.android.globalactions.util.SystemConditionChecker$1 */
  static /* synthetic */ class C50321 {

    /* renamed from: $SwitchMap$com$samsung$android$globalactions$util$SystemConditions */
    static final /* synthetic */ int[] f2969x921795bd;

    static {
      int[] iArr = new int[SystemConditions.values().length];
      f2969x921795bd = iArr;
      try {
        iArr[SystemConditions.GET_MOBILE_DATA_ENABLED.ordinal()] = 1;
      } catch (NoSuchFieldError e) {
      }
      try {
        f2969x921795bd[SystemConditions.IS_ENCRYPTION_STATUS_ACTIVE.ordinal()] = 2;
      } catch (NoSuchFieldError e2) {
      }
      try {
        f2969x921795bd[SystemConditions.IS_LOGOUT_ENABLED.ordinal()] = 3;
      } catch (NoSuchFieldError e3) {
      }
      try {
        f2969x921795bd[SystemConditions.IS_SECURE_KEYGUARD.ordinal()] = 4;
      } catch (NoSuchFieldError e4) {
      }
      try {
        f2969x921795bd[SystemConditions.IS_FMM_LOCKED.ordinal()] = 5;
      } catch (NoSuchFieldError e5) {
      }
      try {
        f2969x921795bd[SystemConditions.IS_CARRIRER_LOCK_PLUS_ENABLED.ordinal()] = 6;
      } catch (NoSuchFieldError e6) {
      }
      try {
        f2969x921795bd[SystemConditions.IS_RMM_LOCKED.ordinal()] = 7;
      } catch (NoSuchFieldError e7) {
      }
      try {
        f2969x921795bd[SystemConditions.IS_USER_UNLOCKED.ordinal()] = 8;
      } catch (NoSuchFieldError e8) {
      }
      try {
        f2969x921795bd[SystemConditions.IS_KIDS_HOME_MODE.ordinal()] = 9;
      } catch (NoSuchFieldError e9) {
      }
      try {
        f2969x921795bd[SystemConditions.IS_ATT_FOTA_CLIENT_PACKAGE_ENABLED.ordinal()] = 10;
      } catch (NoSuchFieldError e10) {
      }
      try {
        f2969x921795bd[SystemConditions.IS_SEC_FOTA_CLIENT_PACKAGE_ENABLED.ordinal()] = 11;
      } catch (NoSuchFieldError e11) {
      }
      try {
        f2969x921795bd[SystemConditions.CAN_SET_MODE.ordinal()] = 12;
      } catch (NoSuchFieldError e12) {
      }
      try {
        f2969x921795bd[SystemConditions.IS_UPSM_ENABLED.ordinal()] = 13;
      } catch (NoSuchFieldError e13) {
      }
      try {
        f2969x921795bd[SystemConditions.IS_EMERGENCY_MODE.ordinal()] = 14;
      } catch (NoSuchFieldError e14) {
      }
      try {
        f2969x921795bd[SystemConditions.IS_MODIFYING.ordinal()] = 15;
      } catch (NoSuchFieldError e15) {
      }
      try {
        f2969x921795bd[SystemConditions.IS_BUG_REPORT_MODE.ordinal()] = 16;
      } catch (NoSuchFieldError e16) {
      }
      try {
        f2969x921795bd[SystemConditions.IS_NAV_BAR_GESTURE_ENABLED.ordinal()] = 17;
      } catch (NoSuchFieldError e17) {
      }
      try {
        f2969x921795bd[SystemConditions.IS_TASK_BAR_ENABLED.ordinal()] = 18;
      } catch (NoSuchFieldError e18) {
      }
      try {
        f2969x921795bd[SystemConditions.IS_MISSING_PHONE_LOCK.ordinal()] = 19;
      } catch (NoSuchFieldError e19) {
      }
      try {
        f2969x921795bd[SystemConditions.IS_AIRPLANE_MODE.ordinal()] = 20;
      } catch (NoSuchFieldError e20) {
      }
      try {
        f2969x921795bd[SystemConditions.IS_LOCK_NETWORK_AND_SECURITY.ordinal()] = 21;
      } catch (NoSuchFieldError e21) {
      }
      try {
        f2969x921795bd[SystemConditions.IS_DOMESTIC_OTA_MODE.ordinal()] = 22;
      } catch (NoSuchFieldError e22) {
      }
      try {
        f2969x921795bd[SystemConditions.IS_TABLET_DEVICE.ordinal()] = 23;
      } catch (NoSuchFieldError e23) {
      }
      try {
        f2969x921795bd[SystemConditions.IS_TSAFE_LOCK.ordinal()] = 24;
      } catch (NoSuchFieldError e24) {
      }
      try {
        f2969x921795bd[SystemConditions.IS_SIM_LOCK.ordinal()] = 25;
      } catch (NoSuchFieldError e25) {
      }
      try {
        f2969x921795bd[SystemConditions.HAS_ANY_SIM.ordinal()] = 26;
      } catch (NoSuchFieldError e26) {
      }
      try {
        f2969x921795bd[SystemConditions.IS_ALLOWED_SAFE_BOOT.ordinal()] = 27;
      } catch (NoSuchFieldError e27) {
      }
      try {
        f2969x921795bd[SystemConditions.IS_LOCK_DOWN_IN_POWER_MENU.ordinal()] = 28;
      } catch (NoSuchFieldError e28) {
      }
      try {
        f2969x921795bd[SystemConditions.IS_DEVICE_OWNER.ordinal()] = 29;
      } catch (NoSuchFieldError e29) {
      }
      try {
        f2969x921795bd[SystemConditions.IS_CURRENT_USER_SECURE.ordinal()] = 30;
      } catch (NoSuchFieldError e30) {
      }
      try {
        f2969x921795bd[SystemConditions.IS_STRONG_AUTH_FOR_LOCK_DOWN.ordinal()] = 31;
      } catch (NoSuchFieldError e31) {
      }
      try {
        f2969x921795bd[SystemConditions.IS_VOICE_ASSISTANT_MODE.ordinal()] = 32;
      } catch (NoSuchFieldError e32) {
      }
      try {
        f2969x921795bd[SystemConditions.IS_WHITE_THEME.ordinal()] = 33;
      } catch (NoSuchFieldError e33) {
      }
      try {
        f2969x921795bd[SystemConditions.IS_ULTRA_POWER_SAVING_MODE.ordinal()] = 34;
      } catch (NoSuchFieldError e34) {
      }
      try {
        f2969x921795bd[SystemConditions.IS_SHOP_DEMO.ordinal()] = 35;
      } catch (NoSuchFieldError e35) {
      }
      try {
        f2969x921795bd[SystemConditions.IS_FOTA_AVAILABLE_FOR_GLOBALACTIONS.ordinal()] = 36;
      } catch (NoSuchFieldError e36) {
      }
      try {
        f2969x921795bd[SystemConditions.IS_SUPPORT_EMERGENCY_MODE.ordinal()] = 37;
      } catch (NoSuchFieldError e37) {
      }
      try {
        f2969x921795bd[SystemConditions.IS_SUPPORT_EMERGENCY_CALL.ordinal()] = 38;
      } catch (NoSuchFieldError e38) {
      }
      try {
        f2969x921795bd[SystemConditions.IS_SUPPORT_MEDICAL_INFO.ordinal()] = 39;
      } catch (NoSuchFieldError e39) {
      }
      try {
        f2969x921795bd[SystemConditions.IS_WIFI_ONLY_DEVICE.ordinal()] = 40;
      } catch (NoSuchFieldError e40) {
      }
      try {
        f2969x921795bd[SystemConditions.IS_SUPPORT_SF_EFFECT.ordinal()] = 41;
      } catch (NoSuchFieldError e41) {
      }
      try {
        f2969x921795bd[SystemConditions.IS_SUPPORT_CAPTURED_BLUR.ordinal()] = 42;
      } catch (NoSuchFieldError e42) {
      }
      try {
        f2969x921795bd[SystemConditions.IS_SUPPORT_POWER_OFF_LOCK.ordinal()] = 43;
      } catch (NoSuchFieldError e43) {
      }
      try {
        f2969x921795bd[SystemConditions.PWD_CHANGE_ENFORCED.ordinal()] = 44;
      } catch (NoSuchFieldError e44) {
      }
      try {
        f2969x921795bd[SystemConditions.IS_EMERGENCY_MODE_USER_AGREEMENT.ordinal()] = 45;
      } catch (NoSuchFieldError e45) {
      }
      try {
        f2969x921795bd[SystemConditions.IS_RBM_MODE.ordinal()] = 46;
      } catch (NoSuchFieldError e46) {
      }
      try {
        f2969x921795bd[SystemConditions.IS_POWER_OFF_UNLOCK_ALWAYS_REQUIRED.ordinal()] = 47;
      } catch (NoSuchFieldError e47) {
      }
      try {
        f2969x921795bd[SystemConditions.IS_POWER_OFF_UNLOCK_ONLY_LOCKSCREEN_REQUIRED.ordinal()] =
            48;
      } catch (NoSuchFieldError e48) {
      }
      try {
        f2969x921795bd[SystemConditions.IS_POWER_OFF_UNLOCK_NOT_REQUIRED.ordinal()] = 49;
      } catch (NoSuchFieldError e49) {
      }
      try {
        f2969x921795bd[SystemConditions.SUPPORT_SECONDARY_DISPLAY_AS_COVER.ordinal()] = 50;
      } catch (NoSuchFieldError e50) {
      }
      try {
        f2969x921795bd[SystemConditions.FRONT_LARGE_COVER_DISPLAY.ordinal()] = 51;
      } catch (NoSuchFieldError e51) {
      }
      try {
        f2969x921795bd[SystemConditions.IS_VALID_VERSION.ordinal()] = 52;
      } catch (NoSuchFieldError e52) {
      }
      try {
        f2969x921795bd[SystemConditions.IS_DO_PROVISIONING_MODE.ordinal()] = 53;
      } catch (NoSuchFieldError e53) {
      }
      try {
        f2969x921795bd[SystemConditions.IS_DESKTOP_MODE_STANDALONE.ordinal()] = 54;
      } catch (NoSuchFieldError e54) {
      }
      try {
        f2969x921795bd[SystemConditions.IS_DESKTOP_MODE_DUAL_VIEW.ordinal()] = 55;
      } catch (NoSuchFieldError e55) {
      }
      try {
        f2969x921795bd[SystemConditions.IS_FOLDED.ordinal()] = 56;
      } catch (NoSuchFieldError e56) {
      }
      try {
        f2969x921795bd[SystemConditions.IS_BIKE_MODE.ordinal()] = 57;
      } catch (NoSuchFieldError e57) {
      }
      try {
        f2969x921795bd[SystemConditions.IS_IN_LOCK_TASK_MODE.ordinal()] = 58;
      } catch (NoSuchFieldError e58) {
      }
      try {
        f2969x921795bd[SystemConditions.IS_REPAIR_MODE.ordinal()] = 59;
      } catch (NoSuchFieldError e59) {
      }
      try {
        f2969x921795bd[SystemConditions.IS_FOLD_DEVICE.ordinal()] = 60;
      } catch (NoSuchFieldError e60) {
      }
    }
  }

  private boolean isEnabled(SystemConditions facadeEnum) {
    switch (C50321.f2969x921795bd[facadeEnum.ordinal()]) {
    }
    return false;
  }

  @Override // com.samsung.android.globalactions.util.ConditionChecker
  public boolean isEnabled(Object name) {
    try {
      Long time = Long.valueOf(System.currentTimeMillis());
      SystemConditions condition = SystemConditions.valueOf(name.toString());
      boolean ret = isEnabled(condition);
      Long current = Long.valueOf(System.currentTimeMillis());
      this.mLogWrapper.m265i(
          TAG,
          NavigationBarInflaterView.SIZE_MOD_START
              + condition.name().toLowerCase()
              + "] "
              + ret
              + " ("
              + (current.longValue() - time.longValue())
              + NavigationBarInflaterView.KEY_CODE_END);
      return ret;
    } catch (Exception ex) {
      ex.printStackTrace();
      return false;
    }
  }
}
