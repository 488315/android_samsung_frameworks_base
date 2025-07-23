package android.app;

/* loaded from: classes.dex */
public class PendingIntentStats {
    public final int count;
    public final int sizeKb;
    public final int uid;

    public PendingIntentStats(int i, int i2, int i3) {
        this.uid = i;
        this.count = i2;
        this.sizeKb = i3;
    }
}
