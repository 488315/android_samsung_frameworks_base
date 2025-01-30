package com.android.server.accessibility.autoaction.actiontype;

import android.R;
import android.content.Context;
import android.content.Intent;

/* loaded from: classes.dex */
public class PowerOffMenu extends CornerActionType {
    public Context mContext;

    public static int getStringResId() {
        return R.string.activitychooserview_choose_application;
    }

    public PowerOffMenu(Context context) {
        this.mContext = context;
    }

    public static PowerOffMenu createAction(Context context) {
        return new PowerOffMenu(context);
    }

    @Override // com.android.server.accessibility.autoaction.actiontype.CornerActionType
    public void performCornerAction(int i) {
        this.mContext.sendBroadcast(new Intent("SYSTEM_ACTION_POWER_DIALOG"));
    }
}
