package com.android.server.accessibility.autoaction.actiontype;

import android.content.Context;
import android.content.Intent;

public final class ScreenOff extends CornerActionType {
    public Context mContext;

    @Override // com.android.server.accessibility.autoaction.actiontype.CornerActionType
    public final void performCornerAction(int i) {
        this.mContext.sendBroadcast(new Intent("SYSTEM_ACTION_LOCK_SCREEN"));
    }
}
