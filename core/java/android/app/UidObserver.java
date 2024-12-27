package android.app;

public class UidObserver extends IUidObserver.Stub {
    @Override // android.app.IUidObserver
    public void onUidActive(int uid) {}

    @Override // android.app.IUidObserver
    public void onUidCachedChanged(int uid, boolean cached) {}

    public void onUidGone(int uid, boolean disabled) {}

    @Override // android.app.IUidObserver
    public void onUidIdle(int uid, boolean disabled) {}

    @Override // android.app.IUidObserver
    public void onUidProcAdjChanged(int uid, int adj) {}

    public void onUidStateChanged(int uid, int procState, long procStateSeq, int capability) {}
}
