package com.android.server.om;

import android.app.ActivityManagerInternal;
import android.content.IIntentReceiver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.android.internal.util.FunctionalUtils;

public final /* synthetic */ class OverlayManagerService$$ExternalSyntheticLambda1
        implements FunctionalUtils.ThrowingConsumer {
    public final /* synthetic */ int f$0;
    public final /* synthetic */ ActivityManagerInternal f$1;

    public /* synthetic */ OverlayManagerService$$ExternalSyntheticLambda1(
            int i, ActivityManagerInternal activityManagerInternal) {
        this.f$0 = i;
        this.f$1 = activityManagerInternal;
    }

    public final void acceptOrThrow(Object obj) {
        int i = this.f$0;
        ActivityManagerInternal activityManagerInternal = this.f$1;
        String str = (String) obj;
        Intent intent =
                new Intent(
                        "android.intent.action.OVERLAY_CHANGED",
                        Uri.fromParts("package", str, null));
        intent.setFlags(67108864);
        intent.putExtra("android.intent.extra.PACKAGE_NAME", str);
        intent.putExtra("android.intent.extra.USER_ID", i);
        activityManagerInternal.broadcastIntent(
                intent,
                (IIntentReceiver) null,
                (String[]) null,
                false,
                i,
                (int[]) null,
                new OverlayManagerService$$ExternalSyntheticLambda3(),
                (Bundle) null);
    }
}
