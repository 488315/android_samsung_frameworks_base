package com.android.server.accessibility.autoaction.actiontype;

import android.R;
import android.content.Context;
import android.content.Intent;

/* loaded from: classes.dex */
public class ScreenOff extends CornerActionType {
  public Context mContext;

  public static int getStringResId() {
    return R.string.adbwifi_active_notification_title;
  }

  public ScreenOff(Context context) {
    this.mContext = context;
  }

  public static ScreenOff createAction(Context context) {
    return new ScreenOff(context);
  }

  @Override // com.android.server.accessibility.autoaction.actiontype.CornerActionType
  public void performCornerAction(int i) {
    this.mContext.sendBroadcast(new Intent("SYSTEM_ACTION_LOCK_SCREEN"));
  }
}
