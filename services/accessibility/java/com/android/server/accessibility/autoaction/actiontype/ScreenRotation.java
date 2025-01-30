package com.android.server.accessibility.autoaction.actiontype;

import android.R;
import android.app.ActivityThread;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.provider.Settings;
import android.view.ContextThemeWrapper;
import com.android.internal.accessibility.util.AccessibilityUtils;

/* loaded from: classes.dex */
public class ScreenRotation extends CornerActionType {
  public String mAccelerometerRotationUri;
  public Context mContext;
  public int mUserId;

  public static int getStringResId() {
    return R.string.addToDictionary;
  }

  public ScreenRotation(Context context, int i) {
    this.mContext = context;
    this.mUserId = i;
  }

  public static ScreenRotation createAction(Context context, int i) {
    return new ScreenRotation(context, i);
  }

  @Override // com.android.server.accessibility.autoaction.actiontype.CornerActionType
  public void performCornerAction(int i) {
    if (AccessibilityUtils.makeToastForCoverScreen(this.mContext, (String) null)) {
      return;
    }
    if (isAutoRotateScreen()) {
      initDialog();
    } else {
      rotateScreen();
    }
  }

  public final void rotateScreen() {
    int i = 0;
    int intForUser =
        Settings.System.getIntForUser(
            this.mContext.getContentResolver(), "user_rotation", 0, this.mUserId);
    if (intForUser == 0) {
      i = 1;
    } else if (intForUser != 1 && intForUser != 3) {
      i = intForUser;
    }
    Settings.System.putIntForUser(
        this.mContext.getContentResolver(), "user_rotation", i, this.mUserId);
  }

  public final boolean isAutoRotateScreen() {
    boolean z =
        this.mContext.getPackageManager().hasSystemFeature("com.sec.feature.folder_type")
            && this.mContext.getPackageManager().hasSystemFeature("com.sec.feature.dual_lcd");
    boolean z2 = this.mContext.getResources().getConfiguration().hardKeyboardHidden == 2;
    if (z && !z2) {
      this.mAccelerometerRotationUri = "accelerometer_rotation_second";
    } else {
      this.mAccelerometerRotationUri = "accelerometer_rotation";
    }
    return Settings.System.getIntForUser(
            this.mContext.getContentResolver(), this.mAccelerometerRotationUri, 0, this.mUserId)
        == 1;
  }

  public final void initDialog() {
    AlertDialog create =
        getAlertDialogBuilder(getSystemUiContext())
            .setTitle(17043028)
            .setMessage(R.string.config_qualified_networks_service_package)
            .setPositiveButton(
                17043038,
                new DialogInterface.OnClickListener() { // from class:
                  // com.android.server.accessibility.autoaction.actiontype.ScreenRotation.2
                  @Override // android.content.DialogInterface.OnClickListener
                  public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    Settings.System.putIntForUser(
                        ScreenRotation.this.mContext.getContentResolver(),
                        ScreenRotation.this.mAccelerometerRotationUri,
                        0,
                        ScreenRotation.this.mUserId);
                    ScreenRotation.this.rotateScreen();
                  }
                })
            .setNegativeButton(
                R.string.cancel,
                new DialogInterface.OnClickListener() { // from class:
                  // com.android.server.accessibility.autoaction.actiontype.ScreenRotation.1
                  @Override // android.content.DialogInterface.OnClickListener
                  public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                  }
                })
            .setCancelable(true)
            .create();
    create.getWindow().setType(2280);
    create.setCanceledOnTouchOutside(true);
    create.show();
  }

  public final AlertDialog.Builder getAlertDialogBuilder(Context context) {
    if ((context.getResources().getConfiguration().uiMode & 48) == 32) {
      context = new ContextThemeWrapper(context, R.style.Theme.DeviceDefault);
    }
    return new AlertDialog.Builder(context);
  }

  public Context getSystemUiContext() {
    return ActivityThread.currentActivityThread().getSystemUiContext();
  }
}
