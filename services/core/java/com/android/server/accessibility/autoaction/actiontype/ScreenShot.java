package com.android.server.accessibility.autoaction.actiontype;

import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ScreenShot extends CornerActionType {
    public Context mContext;

    @Override // com.android.server.accessibility.autoaction.actiontype.CornerActionType
    public final void performCornerAction(int i) {
        if (i == 0) {
            this.mContext.sendBroadcast(new Intent("SYSTEM_ACTION_TAKE_SCREENSHOT"));
            return;
        }
        Intent intent = new Intent("com.samsung.android.capture.ScreenshotExecutor");
        intent.putExtra("capturedOrigin", 100);
        intent.putExtra("callingPackageName", "com.samsung.accessibility");
        intent.putExtra("displayId", Integer.toString(i));
        intent.addFlags(268435456);
        this.mContext.sendBroadcastAsUser(intent, UserHandle.SEM_CURRENT, "com.samsung.permission.CAPTURE");
    }
}
