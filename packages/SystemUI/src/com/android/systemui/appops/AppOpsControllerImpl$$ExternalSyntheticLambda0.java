package com.android.systemui.appops;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        this.f$0.notifySuscribersWorker(this.f$1, this.f$4, this.f$2, this.f$3, this.f$5);
    }
}
