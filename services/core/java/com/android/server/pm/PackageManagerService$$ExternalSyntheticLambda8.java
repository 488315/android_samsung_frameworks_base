package com.android.server.pm;

import android.content.IIntentReceiver;
import android.os.Bundle;
import android.util.SparseArray;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class PackageManagerService$$ExternalSyntheticLambda8 implements Runnable {
    public final /* synthetic */ PackageManagerService f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ String f$2;
    public final /* synthetic */ Bundle f$3;
    public final /* synthetic */ int[] f$7;
    public final /* synthetic */ int f$4 = 0;
    public final /* synthetic */ String f$5 = null;
    public final /* synthetic */ IIntentReceiver f$6 = null;
    public final /* synthetic */ int[] f$8 = null;
    public final /* synthetic */ SparseArray f$9 = null;
    public final /* synthetic */ Bundle f$10 = null;

    public /* synthetic */ PackageManagerService$$ExternalSyntheticLambda8(PackageManagerService packageManagerService, String str, String str2, Bundle bundle, int[] iArr) {
        this.f$0 = packageManagerService;
        this.f$1 = str;
        this.f$2 = str2;
        this.f$3 = bundle;
        this.f$7 = iArr;
    }

    @Override // java.lang.Runnable
    public final void run() {
        PackageManagerService packageManagerService = this.f$0;
        packageManagerService.mBroadcastHelper.sendPackageBroadcast(this.f$1, this.f$2, this.f$3, this.f$4, this.f$5, this.f$6, this.f$7, this.f$8, this.f$9, null, this.f$10);
    }
}
