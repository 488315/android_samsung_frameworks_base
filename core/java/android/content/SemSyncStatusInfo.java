package android.content;

/* loaded from: classes.dex */
public class SemSyncStatusInfo {
    public boolean initialize;
    public long lastFailureTime;
    public long lastSuccessTime;
    public boolean pending;

    public SemSyncStatusInfo(SyncStatusInfo info) {
        this.lastSuccessTime = info.lastSuccessTime;
        this.lastFailureTime = info.lastFailureTime;
        this.pending = info.pending;
        this.initialize = info.initialize;
    }
}
