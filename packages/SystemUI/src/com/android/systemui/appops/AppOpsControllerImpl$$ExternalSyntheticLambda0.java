package com.android.systemui.appops;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class AppOpsControllerImpl$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ AppOpsControllerImpl f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ String f$3;
    public final /* synthetic */ boolean f$4;
    public final /* synthetic */ String f$5;

    public /* synthetic */ AppOpsControllerImpl$$ExternalSyntheticLambda0(AppOpsControllerImpl appOpsControllerImpl, int i, int i2, String str, boolean z, String str2) {
        this.f$0 = appOpsControllerImpl;
        this.f$1 = i;
        this.f$2 = i2;
        this.f$3 = str;
        this.f$4 = z;
        this.f$5 = str2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        AppOpsControllerImpl appOpsControllerImpl = this.f$0;
        int i = this.f$1;
        int i2 = this.f$2;
        String str = this.f$3;
        boolean z = this.f$4;
        String str2 = this.f$5;
        int[] iArr = AppOpsControllerImpl.OPS_CAMERA;
        appOpsControllerImpl.notifySuscribersWorker(i, z, i2, str, str2);
    }
}
