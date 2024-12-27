package com.android.server.pm;

import android.app.ActivityManager;
import android.os.Bundle;
import android.util.Slog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class BroadcastHelper$$ExternalSyntheticLambda5 implements Runnable {
    public final /* synthetic */ BroadcastHelper f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ String[] f$3;
    public final /* synthetic */ Computer f$4;
    public final /* synthetic */ String f$5;

    public /* synthetic */ BroadcastHelper$$ExternalSyntheticLambda5(BroadcastHelper broadcastHelper, boolean z, int i, String[] strArr, Computer computer, String str) {
        this.f$0 = broadcastHelper;
        this.f$1 = z;
        this.f$2 = i;
        this.f$3 = strArr;
        this.f$4 = computer;
        this.f$5 = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        BroadcastHelper broadcastHelper = this.f$0;
        boolean z = this.f$1;
        int i = this.f$2;
        String[] strArr = this.f$3;
        Computer computer = this.f$4;
        String str = this.f$5;
        broadcastHelper.getClass();
        if (ActivityManager.getService() == null) {
            StringBuilder sb = new StringBuilder("IActivityManager null. Cannot send MY_PACKAGE_ ");
            sb.append(z ? "" : "UN");
            sb.append("SUSPENDED broadcasts");
            Slog.wtf("PackageManager", sb.toString());
            return;
        }
        int[] iArr = {i};
        int length = strArr.length;
        int i2 = 0;
        while (i2 < length) {
            String str2 = strArr[i2];
            Bundle bundle = null;
            Bundle suspendedPackageAppExtras = z ? SuspendPackageHelper.getSuspendedPackageAppExtras(i, 1000, computer, str2) : null;
            if (suspendedPackageAppExtras != null) {
                bundle = new Bundle(1);
                bundle.putBundle("android.intent.extra.SUSPENDED_PACKAGE_EXTRAS", suspendedPackageAppExtras);
            }
            broadcastHelper.doSendBroadcast(str, null, bundle, 16777216, str2, null, iArr, false, null, null, null);
            i2++;
            length = length;
            str = str;
            computer = computer;
        }
    }
}
