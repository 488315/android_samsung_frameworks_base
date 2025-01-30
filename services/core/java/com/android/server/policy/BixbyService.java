package com.android.server.policy;

import android.R;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import com.samsung.android.rune.CoreRune;

/* loaded from: classes3.dex */
public class BixbyService {
  public final Context mContext;
  public final ComponentName mDefaultComponentName;
  public final ComponentName mOnboardingComponentName;
  public final PhoneWindowManagerExt mPolicyExt;
  public final PowerManager.WakeLock mWakeLock;

  public BixbyService(PhoneWindowManagerExt phoneWindowManagerExt, Context context) {
    this.mPolicyExt = phoneWindowManagerExt;
    this.mContext = context;
    this.mDefaultComponentName =
        !TextUtils.isEmpty("com.samsung.android.bixby.agent/com.samsung.android.bixby.WinkService")
            ? ComponentName.unflattenFromString(
                "com.samsung.android.bixby.agent/com.samsung.android.bixby.WinkService")
            : null;
    this.mOnboardingComponentName =
        TextUtils.isEmpty(
                "com.samsung.android.bixby.agent/com.samsung.android.bixby.BixbyKeyLService")
            ? null
            : ComponentName.unflattenFromString(
                "com.samsung.android.bixby.agent/com.samsung.android.bixby.BixbyKeyLService");
    this.mWakeLock =
        phoneWindowManagerExt.mPolicy.mPowerManager.newWakeLock(
            1, "PhoneWindowManager.BixbyService.WakeLock");
  }

  public void startService(Params params) {
    if (showToast(params.interactive, params.showToast) == null && isAvailable()) {
      this.mWakeLock.acquire();
      try {
        try {
          this.mContext.startServiceAsUser(getIntent(params), UserHandle.SYSTEM);
        } catch (Exception e) {
          Log.e("BixbyService", "Can not start BixbyService: " + e);
        }
      } finally {
        this.mWakeLock.release();
      }
    }
  }

  public final Intent getIntent(Params params) {
    KeyEvent keyEvent = params.event;
    boolean z = params.interactive;
    boolean z2 = params.longPress;
    boolean z3 = params.doublePress;
    boolean z4 = params.isUnlockFP;
    boolean z5 = params.isPowerCombination;
    Intent intent = new Intent();
    if (CoreRune.FW_SUPPORT_BIXBY_ONBOARDING_SERVICE && (z2 || z3)) {
      intent.setComponent(this.mOnboardingComponentName);
    } else {
      intent.setComponent(this.mDefaultComponentName);
    }
    if (keyEvent != null) {
      intent.putExtra("KEYEVENT", KeyEvent.obtain(keyEvent));
    }
    int i = 1;
    if (z5) {
      intent.putExtra("KEY_COMBINATION", true);
    }
    intent.putExtra("BIXBY_KEY_FW_VERSION", 2);
    intent.putExtra("INTERACTIVE", z);
    if (!CoreRune.FW_WAKE_UP_BIXBY_SIDE_KEY || !z2) {
      i = (CoreRune.FW_DOUBLE_PRESS_SIDE_KEY && z3) ? 2 : -1;
    }
    intent.putExtra("RESULT_BY_POWER", i);
    Log.d(
        "BixbyService",
        "startBixbyService, keyPressType="
            + i
            + " interactive="
            + z
            + " longPress="
            + z2
            + " doublePress="
            + z3
            + " isUnlockFP="
            + z4
            + " isPowerCombination="
            + z5);
    return intent;
  }

  public boolean isAvailable() {
    String str;
    if (CoreRune.IS_FACTORY_BINARY) {
      Log.d("BixbyService", "Do nothing regarding key event of bixby service. Factory Binary");
      return false;
    }
    if (!this.mPolicyExt.mPolicy.isUserSetupComplete()) {
      str = "UserSetup is not completed";
    } else if (this.mDefaultComponentName == null) {
      str = "bixbyComponentName is null";
    } else if (this.mPolicyExt.isCarrierLocked()) {
      str = "Carrier is Locked";
    } else if (this.mPolicyExt.mPolicy.mKeyguardDelegate.isSimLocked()) {
      str = "Sim is Locked";
    } else {
      str = this.mPolicyExt.isDomesticOtaStart() ? "DomesticOtaStart" : null;
    }
    if (TextUtils.isEmpty(str)) {
      return true;
    }
    Log.d("BixbyService", "Do nothing regarding key event of bixby service. reason=" + str);
    return false;
  }

  public String showToast(boolean z, boolean z2) {
    if (!z2) {
      return null;
    }
    String toast = getToast();
    if (!TextUtils.isEmpty(toast) && z) {
      this.mPolicyExt.showToast(this.mContext, toast);
    }
    return toast;
  }

  public final String getToast() {
    if (CoreRune.FW_SUPPORT_RESERVE_BATTERY_MODE && this.mPolicyExt.isReserveBatteryMode()) {
      return String.format(
          this.mContext
              .getResources()
              .getString(R.string.config_defaultOnDeviceIntelligenceDeviceConfigNamespace),
          new Object[0]);
    }
    return null;
  }

  public ComponentName getComponentName() {
    return this.mDefaultComponentName;
  }

  public class Params {
    public final boolean doublePress;
    public final KeyEvent event;
    public final boolean interactive;
    public final boolean isPowerCombination;
    public final boolean isUnlockFP;
    public final boolean longPress;
    public final boolean showToast;

    public Params(
        KeyEvent keyEvent, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6) {
      this.event = keyEvent;
      this.interactive = z;
      this.showToast = z2;
      this.longPress = z3;
      this.doublePress = z4;
      this.isUnlockFP = z5;
      this.isPowerCombination = z6;
    }

    public Params(Builder builder) {
      this(
          builder.event,
          builder.interactive,
          builder.showToast,
          builder.longPress,
          builder.doublePress,
          builder.isUnlockFP,
          builder.isPowerCombination);
    }

    public class Builder {
      public KeyEvent event;
      public boolean interactive;
      public boolean showToast = false;
      public boolean longPress = false;
      public boolean doublePress = false;
      public boolean isUnlockFP = false;
      public boolean isPowerCombination = false;

      public Builder(KeyEvent keyEvent, boolean z) {
        this.event = keyEvent;
        this.interactive = z;
      }

      public Params build() {
        return new Params(this);
      }

      public Builder showToast() {
        this.showToast = true;
        return this;
      }

      public Builder setLongPress() {
        this.longPress = true;
        return this;
      }

      public Builder setDoublePress() {
        this.doublePress = true;
        return this;
      }

      public Builder setPowerCombination(boolean z) {
        this.isPowerCombination = z;
        return this;
      }
    }
  }
}
