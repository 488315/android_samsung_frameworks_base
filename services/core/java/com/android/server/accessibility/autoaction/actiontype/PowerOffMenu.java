package com.android.server.accessibility.autoaction.actiontype;

import android.content.Context;
import android.content.Intent;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class PowerOffMenu extends CornerActionType {
    public Context mContext;

    @Override // com.android.server.accessibility.autoaction.actiontype.CornerActionType
    public final void performCornerAction(int i) {
        this.mContext.sendBroadcast(new Intent("SYSTEM_ACTION_POWER_DIALOG"));
    }
}
