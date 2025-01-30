package com.android.server.accessibility.autoaction.actiontype;

import android.R;
import android.app.Instrumentation;
import android.content.Context;
import android.provider.Settings;
import android.util.Log;

/* loaded from: classes.dex */
public class SendSOSMessages extends CornerActionType {
  public Context mContext;
  public int mUserId;

  public static int getStringResId() {
    return R.string.add_account_label;
  }

  public SendSOSMessages(Context context, int i) {
    this.mContext = context;
    this.mUserId = i;
  }

  public static SendSOSMessages createAction(Context context, int i) {
    return new SendSOSMessages(context, i);
  }

  @Override // com.android.server.accessibility.autoaction.actiontype.CornerActionType
  public void performCornerAction(int i) {
    new Thread(
            new Runnable() { // from class:
              // com.android.server.accessibility.autoaction.actiontype.SendSOSMessages.1
              @Override // java.lang.Runnable
              public void run() {
                try {
                  Thread.sleep(100L);
                  int intForUser =
                      Settings.System.getIntForUser(
                          SendSOSMessages.this.mContext.getContentResolver(),
                          "send_emergency_message_power_number",
                          5,
                          SendSOSMessages.this.mUserId);
                  for (int i2 = 0; i2 < intForUser; i2++) {
                    new Instrumentation().sendKeyDownUpSync(26);
                  }
                } catch (Exception e) {
                  Log.w("SendSOSMessages", "Exception!", e);
                }
              }
            })
        .start();
  }
}
