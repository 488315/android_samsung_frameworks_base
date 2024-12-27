package com.android.server.content;

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
