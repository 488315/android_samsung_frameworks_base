package com.android.internal.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.Settings;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import com.android.internal.C4337R;

/* loaded from: classes4.dex */
public abstract class AlertActivity extends Activity implements DialogInterface {
  private static final float DIALOG_POP_OVER_DARK_DIM_AMOUNT = 0.65f;
  private static final float DIALOG_POP_OVER_DIM_AMOUNT = 0.18f;
  private static final float DIALOG_POP_OVER_ELEVATION = 8.0f;
  private static final float DIALOG_REDUCE_TRANSPARENCY_DIM_AMOUNT = 0.35f;
  protected AlertController mAlert;
  protected AlertController.AlertParams mAlertParams;
  private boolean mIsDeviceDefault;
  private boolean mIsDeviceDefaultDark;

  @Override // android.app.Activity
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Window window = getWindow();
    this.mAlert = AlertController.create(this, this, window);
    this.mAlertParams = new AlertController.AlertParams(this);
    TypedValue themeValue = new TypedValue();
    getTheme().resolveAttribute(C4337R.attr.parentIsDeviceDefault, themeValue, true);
    if (themeValue.data != 0) {
      this.mIsDeviceDefault = true;
      TypedValue colorValue = new TypedValue();
      getTheme().resolveAttribute(C4337R.attr.parentIsDeviceDefaultDark, colorValue, true);
      this.mIsDeviceDefaultDark = colorValue.data != 0;
    }
    WindowManager.LayoutParams l = window.getAttributes();
    if (this.mIsDeviceDefault) {
      if (getResources().getBoolean(C4337R.bool.sem_config_dialogLargeScreen)) {
        if (getResources().getConfiguration().windowConfiguration.isPopOver()) {
          l.dimAmount = this.mIsDeviceDefaultDark ? DIALOG_POP_OVER_DARK_DIM_AMOUNT : 0.18f;
          l.width = getResources().getDisplayMetrics().widthPixels;
        }
        window.setAttributes(l);
      } else if (!this.mIsDeviceDefaultDark) {
        boolean isReduceTransparency =
            Settings.System.getInt(getContentResolver(), "accessibility_reduce_transparency", 0)
                == 1;
        if (isReduceTransparency) {
          l.dimAmount = DIALOG_REDUCE_TRANSPARENCY_DIM_AMOUNT;
          window.setAttributes(l);
        }
      }
    }
    boolean isReduceTransparency2 = this.mIsDeviceDefault;
    if (isReduceTransparency2) {
      float f = l.dimAmount;
    }
  }

  @Override // android.content.DialogInterface
  public void cancel() {
    finish();
  }

  @Override // android.content.DialogInterface
  public void dismiss() {
    if (!isFinishing()) {
      finish();
    }
  }

  @Override // android.app.Activity, android.view.Window.Callback
  public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
    return dispatchPopulateAccessibilityEvent(this, event);
  }

  public static boolean dispatchPopulateAccessibilityEvent(Activity act, AccessibilityEvent event) {
    event.setClassName(Dialog.class.getName());
    event.setPackageName(act.getPackageName());
    ViewGroup.LayoutParams params = act.getWindow().getAttributes();
    boolean isFullScreen = params.width == -1 && params.height == -1;
    event.setFullScreen(isFullScreen);
    return false;
  }

  protected void setupAlert() {
    this.mAlert.installContent(this.mAlertParams);
  }

  @Override // android.app.Activity, android.view.KeyEvent.Callback
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (this.mAlert.onKeyDown(keyCode, event)) {
      return true;
    }
    return super.onKeyDown(keyCode, event);
  }

  @Override // android.app.Activity, android.view.KeyEvent.Callback
  public boolean onKeyUp(int keyCode, KeyEvent event) {
    if (this.mAlert.onKeyUp(keyCode, event)) {
      return true;
    }
    return super.onKeyUp(keyCode, event);
  }
}
