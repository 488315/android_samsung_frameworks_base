package com.android.server.content;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class SyncManager$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SyncManager f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ SyncManager$$ExternalSyntheticLambda3(
            SyncManager syncManager, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = syncManager;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                SyncManager syncManager = this.f$0;
                syncManager.mLogger.log("onStartUser: user=", Integer.valueOf(this.f$1));
                break;
            case 1:
                SyncManager syncManager2 = this.f$0;
                syncManager2.mLogger.log("onStopUser: user=", Integer.valueOf(this.f$1));
                break;
            default:
                SyncManager syncManager3 = this.f$0;
                syncManager3.mLogger.log("onUnlockUser: user=", Integer.valueOf(this.f$1));
                break;
        }
    }
}
