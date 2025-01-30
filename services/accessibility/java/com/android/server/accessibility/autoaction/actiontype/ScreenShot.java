package com.android.server.accessibility.autoaction.actiontype;

import android.R;
import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;

/* loaded from: classes.dex */
public class ScreenShot extends CornerActionType {
  public Context mContext;

  public static int getStringResId() {
    return R.string.add_account_button_label;
  }

  public ScreenShot(Context context) {
    this.mContext = context;
  }

  public static ScreenShot createAction(Context context) {
    return new ScreenShot(context);
  }

  @Override // com.android.server.accessibility.autoaction.actiontype.CornerActionType
  public void performCornerAction(int i) {
    if (i == 0) {
      this.mContext.sendBroadcast(new Intent("SYSTEM_ACTION_TAKE_SCREENSHOT"));
      return;
    }
    Intent intent = new Intent("com.samsung.android.capture.ScreenshotExecutor");
    intent.putExtra("capturedOrigin", 100);
    intent.putExtra("callingPackageName", "com.samsung.accessibility");
    intent.putExtra("displayId", Integer.toString(i));
    intent.addFlags(268435456);
    this.mContext.sendBroadcastAsUser(
        intent, UserHandle.SEM_CURRENT, "com.samsung.permission.CAPTURE");
  }
}
