package com.android.server.biometrics;


/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class SemBioLoggingManager$$ExternalSyntheticLambda4
        implements Runnable {
    public final /* synthetic */ SemBioLoggingManager f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ String f$2;
    public final /* synthetic */ int f$3;

    public /* synthetic */ SemBioLoggingManager$$ExternalSyntheticLambda4(
            SemBioLoggingManager semBioLoggingManager, String str, String str2, int i) {
        this.f$0 = semBioLoggingManager;
        this.f$1 = str;
        this.f$2 = str2;
        this.f$3 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        SemBioLoggingManager semBioLoggingManager = this.f$0;
        String str = this.f$1;
        String str2 = this.f$2;
        int i = this.f$3;
        synchronized (semBioLoggingManager.mFpLoggingInfo) {
            try {
                SemBioLoggingManager.LoggingInfo loggingInfo =
                        new SemBioLoggingManager.LoggingInfo();
                loggingInfo.mStartTime = System.currentTimeMillis();
                loggingInfo.mType = str;
                loggingInfo.mPackageName = str2;
                semBioLoggingManager.mFpLoggingInfo.append(i, loggingInfo);
                if (semBioLoggingManager.mFpLoggingInfo.size()
                        >= SemBioLoggingManager.LOG_ARRAY_SIZE) {
                    semBioLoggingManager.mFpLoggingInfo.removeAt(0);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
