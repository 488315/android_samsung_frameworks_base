package com.android.server.policy;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Slog;
import android.view.KeyEvent;
import com.android.server.wm.WmCoverState;
import com.samsung.android.rune.CoreRune;

/* loaded from: classes3.dex */
public abstract class SideKeyDoublePress {
  public static Behavior mBehavior;

  public abstract class Behavior {
    public PhoneWindowManagerExt mPolicyExt;
    public final String mTargetAppName;

    public int getAction() {
      return 0;
    }

    public abstract void startTargetApp(
        KeyEvent keyEvent, boolean z, boolean z2, Intent intent, Intent intent2);

    public void updateTargetComponent(Intent intent) {}

    public Behavior(String str) {
      this.mTargetAppName = str;
    }

    public String getTargetAppName() {
      return this.mTargetAppName;
    }

    public boolean equalTargetAppName(String str) {
      if (TextUtils.isEmpty(str)) {
        Slog.d("SideKeyDoublePress", "appName is empty.");
        return false;
      }
      return str.equals(this.mTargetAppName);
    }

    public Intent getIntent() {
      return new Intent("android.intent.action.MAIN")
          .addCategory("android.intent.category.LAUNCHER")
          .addFlags(270532608)
          .setComponent(ComponentName.unflattenFromString(this.mTargetAppName));
    }

    public void launch(
        PhoneWindowManagerExt phoneWindowManagerExt, Intent intent, KeyEvent keyEvent, boolean z) {
      this.mPolicyExt = phoneWindowManagerExt;
      if (preCondition(intent, z)) {
        return;
      }
      updateTargetComponent(intent);
      Intent fillInIntent = phoneWindowManagerExt.getFillInIntent();
      boolean showCoverToast = showCoverToast(fillInIntent, intent);
      Slog.d("SideKeyDoublePress", "launch, showCoverToast=" + showCoverToast + " " + this);
      startTargetApp(keyEvent, z, showCoverToast, intent, fillInIntent);
    }

    public boolean doublePressLaunchPolicy(boolean z) {
      return this.mPolicyExt.doublePressLaunchPolicy(26);
    }

    public boolean showCoverToast(Intent intent, Intent intent2) {
      if (!WmCoverState.isEnabled() || !WmCoverState.getInstance().isClearTypeCoverClosed()) {
        return false;
      }
      intent.putExtra("showCoverToast", true);
      Slog.d("SideKeyDoublePress", "neededShowCoverToast for cover");
      return true;
    }

    public boolean preCondition(Intent intent, boolean z) {
      if (doublePressLaunchPolicy(z)) {
        return true;
      }
      return this.mPolicyExt.showToastIfNeeded(intent);
    }

    public String toString() {
      StringBuilder sb = new StringBuilder(256);
      sb.append("Behavior=");
      sb.append(getClass().getSimpleName());
      sb.append(" keyCode=");
      sb.append(26);
      sb.append(" appName=");
      sb.append(this.mTargetAppName);
      return sb.toString();
    }
  }

  public static Behavior typeToBehavior(int i, String str) {
    if (i == 0) {
      return getBehavior("com.sec.android.app.camera/com.sec.android.app.camera.Camera");
    }
    if (i == 2) {
      return getBehavior(str);
    }
    if (i == 3) {
      return getBehavior("secureFolder/secureFolder");
    }
    if (i == 4) {
      return getBehavior("samsungpay://simplepay/sidekey");
    }
    Slog.d("SideKeyDoublePress", "type is not properly.");
    return null;
  }

  /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
  public static Behavior getBehavior(String str) {
    Behavior openingTvMode;
    Behavior behavior = mBehavior;
    if (behavior != null && behavior.equalTargetAppName(str)) {
      return mBehavior;
    }
    str.hashCode();
    char c = 65535;
    switch (str.hashCode()) {
      case -1236848504:
        if (str.equals("com.samsung.tvmode/com.samsung.tvmode.activity.MainActivity")) {
          c = 0;
          break;
        }
        break;
      case -785655453:
        if (str.equals("samsungpay://simplepay/sidekey")) {
          c = 1;
          break;
        }
        break;
      case 5922692:
        if (str.equals("com.sec.android.app.camera/com.sec.android.app.camera.Camera")) {
          c = 2;
          break;
        }
        break;
      case 1517559055:
        if (str.equals("wakeBixby_openApps/wakeBixby_openApps")) {
          c = 3;
          break;
        }
        break;
      case 1784250409:
        if (str.equals("torch/torch")) {
          c = 4;
          break;
        }
        break;
      case 1980045775:
        if (str.equals("secureFolder/secureFolder")) {
          c = 5;
          break;
        }
        break;
    }
    switch (c) {
      case 0:
        openingTvMode =
            CoreRune.FW_DOUBLE_PRESS_POWER_ATT_TV_MODE
                ? new PhoneWindowManagerExt.OpeningTvMode(str)
                : null;
        mBehavior = openingTvMode;
        return openingTvMode;
      case 1:
        PhoneWindowManagerExt.OpeningSamsungPay openingSamsungPay =
            new PhoneWindowManagerExt.OpeningSamsungPay(str);
        mBehavior = openingSamsungPay;
        return openingSamsungPay;
      case 2:
        PhoneWindowManagerExt.OpeningQuickLaunchCamera openingQuickLaunchCamera =
            new PhoneWindowManagerExt.OpeningQuickLaunchCamera(str);
        mBehavior = openingQuickLaunchCamera;
        return openingQuickLaunchCamera;
      case 3:
        openingTvMode =
            CoreRune.FW_WAKE_UP_BIXBY_SIDE_KEY ? new PhoneWindowManagerExt.OpeningBixby(str) : null;
        mBehavior = openingTvMode;
        return openingTvMode;
      case 4:
        openingTvMode = CoreRune.FW_TORCH ? new PhoneWindowManagerExt.OpeningTorch(str) : null;
        mBehavior = openingTvMode;
        return openingTvMode;
      case 5:
        openingTvMode =
            CoreRune.FW_SUPPORT_QUICK_SWITCH_PRIVATE_MODE
                ? new PhoneWindowManagerExt.OpeningSecureFolder(str)
                : null;
        mBehavior = openingTvMode;
        return openingTvMode;
      default:
        if (TextUtils.isEmpty(str)) {
          return null;
        }
        PhoneWindowManagerExt.OpeningApps openingApps = new PhoneWindowManagerExt.OpeningApps(str);
        mBehavior = openingApps;
        return openingApps;
    }
  }

  public static void launch(
      PhoneWindowManagerExt phoneWindowManagerExt, KeyEvent keyEvent, boolean z) {
    Behavior behavior;
    Intent lastIntentClone =
        phoneWindowManagerExt.mKeyCustomizationPolicy.getLastIntentClone(8, 26);
    if (lastIntentClone == null
        || (behavior = getBehavior(getTargetAppName(lastIntentClone))) == null) {
      return;
    }
    behavior.launch(phoneWindowManagerExt, lastIntentClone, keyEvent, z);
  }

  public static String getTargetAppName(Intent intent) {
    ComponentName component = intent.getComponent();
    if (component != null) {
      return component.flattenToString();
    }
    Uri data = intent.getData();
    if (data != null) {
      return data.toString();
    }
    return null;
  }
}
