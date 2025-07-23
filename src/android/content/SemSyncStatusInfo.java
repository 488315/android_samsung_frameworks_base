package android.content;

/* loaded from: classes.dex */
public class SemSyncStatusInfo {
    public boolean initialize;
    public long lastFailureTime;
    public long lastSuccessTime;
    public boolean pending;

    public SemSyncStatusInfo(SyncStatusInfo syncStatusInfo) {
        this.lastSuccessTime = syncStatusInfo.lastSuccessTime;
        this.lastFailureTime = syncStatusInfo.lastFailureTime;
        this.pending = syncStatusInfo.pending;
        this.initialize = syncStatusInfo.initialize;
    }
}
