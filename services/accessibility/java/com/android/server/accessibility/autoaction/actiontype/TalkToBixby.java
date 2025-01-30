package com.android.server.accessibility.autoaction.actiontype;

import android.R;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.widget.Toast;

/* loaded from: classes.dex */
public class TalkToBixby extends CornerActionType {
    public Context mContext;
    public int mUserId;

    public static int getStringResId() {
        return R.string.aerr_process_repeated;
    }

    public TalkToBixby(Context context, int i) {
        this.mContext = context;
        this.mUserId = i;
    }

    public static TalkToBixby createAction(Context context, int i) {
        return new TalkToBixby(context, i);
    }

    @Override // com.android.server.accessibility.autoaction.actiontype.CornerActionType
    public void performCornerAction(int i) {
        if (isSetupWizard(this.mContext)) {
            Toast.makeText(this.mContext, isTablet() ? 17043031 : 17043030, 0).show();
            return;
        }
        Intent intent = new Intent();
        intent.setAction("com.samsung.android.bixby.action.START_WITH_EPD_BIXBY");
        intent.setComponent(new ComponentName("com.samsung.android.bixby.agent", "com.samsung.android.bixby.receiver.WakeupReceiver"));
        this.mContext.sendBroadcastAsUser(intent, new UserHandle(this.mUserId));
    }

    public final boolean isSetupWizard(Context context) {
        return ((Settings.Global.getInt(context.getContentResolver(), "device_provisioned", 0) != 0) && (Settings.Secure.getIntForUser(context.getContentResolver(), "user_setup_complete", 0, this.mUserId) != 0)) ? false : true;
    }

    public static boolean isTablet() {
        String str = SystemProperties.get("ro.build.characteristics");
        return !TextUtils.isEmpty(str) && str.contains("tablet");
    }
}
