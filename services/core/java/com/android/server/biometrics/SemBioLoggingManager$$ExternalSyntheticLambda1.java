package com.android.server.biometrics;


/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class SemBioLoggingManager$$ExternalSyntheticLambda1
        implements Runnable {
    public final /* synthetic */ SemBioLoggingManager f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ int f$3;

    public /* synthetic */ SemBioLoggingManager$$ExternalSyntheticLambda1(
            SemBioLoggingManager semBioLoggingManager, int i, int i2, int i3) {
        this.f$0 = semBioLoggingManager;
        this.f$1 = i;
        this.f$2 = i2;
        this.f$3 = i3;
    }

    @Override // java.lang.Runnable
    public final void run() {
        SemBioLoggingManager semBioLoggingManager = this.f$0;
        int i = this.f$1;
        int i2 = this.f$2;
        int i3 = this.f$3;
        synchronized (semBioLoggingManager.mFpLoggingInfo) {
            try {
                SemBioLoggingManager.LoggingInfo loggingInfo =
                        (SemBioLoggingManager.LoggingInfo)
                                semBioLoggingManager.mFpLoggingInfo.get(i);
                if (loggingInfo != null) {
                    loggingInfo.mFpScreenStatus = i2;
                    loggingInfo.mFpAlphaMaskLvl = i3;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
