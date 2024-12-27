package com.android.server.biometrics;

import com.android.server.biometrics.SemBioLoggingManager;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class SemBioLoggingManager$$ExternalSyntheticLambda5 implements Runnable {
    public final /* synthetic */ SemBioLoggingManager f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ long f$2;
    public final /* synthetic */ String f$3;
    public final /* synthetic */ int f$4;
    public final /* synthetic */ int f$5;

    public /* synthetic */ SemBioLoggingManager$$ExternalSyntheticLambda5(SemBioLoggingManager semBioLoggingManager, int i, long j, String str, int i2, int i3) {
        this.f$0 = semBioLoggingManager;
        this.f$1 = i;
        this.f$2 = j;
        this.f$3 = str;
        this.f$4 = i2;
        this.f$5 = i3;
    }

    @Override // java.lang.Runnable
    public final void run() {
        SemBioLoggingManager semBioLoggingManager = this.f$0;
        int i = this.f$1;
        long j = this.f$2;
        String str = this.f$3;
        int i2 = this.f$4;
        int i3 = this.f$5;
        synchronized (semBioLoggingManager.mFpLoggingInfo) {
            try {
                SemBioLoggingManager.LoggingInfo loggingInfo = (SemBioLoggingManager.LoggingInfo) semBioLoggingManager.mFpLoggingInfo.get(i);
                if (loggingInfo != null) {
                    loggingInfo.mResultTime = System.currentTimeMillis();
                    loggingInfo.mLatency = j;
                    loggingInfo.mResult = str;
                    loggingInfo.mExtra = i2;
                    loggingInfo.mBadQualityCount = i3;
                    semBioLoggingManager.fpAddLoggingInfo(loggingInfo);
                    semBioLoggingManager.mFpLoggingInfo.delete(i);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
