package com.android.server.accessibility.autoaction.actiontype;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.widget.Toast;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class TalkToBixby extends CornerActionType {
    public Context mContext;
    public int mUserId;

    @Override // com.android.server.accessibility.autoaction.actiontype.CornerActionType
    public final void performCornerAction(int i) {
        Context context = this.mContext;
        boolean z =
                Settings.Global.getInt(context.getContentResolver(), "device_provisioned", 0) != 0;
        ContentResolver contentResolver = context.getContentResolver();
        int i2 = this.mUserId;
        boolean z2 =
                Settings.Secure.getIntForUser(contentResolver, "user_setup_complete", 0, i2) != 0;
        if (!z || !z2) {
            Context context2 = this.mContext;
            String str = SystemProperties.get("ro.build.characteristics");
            Toast.makeText(
                            context2,
                            (TextUtils.isEmpty(str) || !str.contains("tablet"))
                                    ? 17043245
                                    : 17043246,
                            0)
                    .show();
        } else {
            Intent intent = new Intent();
            intent.setAction("com.samsung.android.bixby.action.START_WITH_EPD_BIXBY");
            intent.setComponent(
                    new ComponentName(
                            "com.samsung.android.bixby.agent",
                            "com.samsung.android.bixby.receiver.WakeupReceiver"));
            this.mContext.sendBroadcastAsUser(intent, new UserHandle(i2));
        }
    }
}
