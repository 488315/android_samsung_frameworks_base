package android.net;

/* loaded from: classes3.dex */
public class Credentials {
    private final int gid;
    private final int pid;
    private final int uid;

    public Credentials(int i, int i2, int i3) {
        this.pid = i;
        this.uid = i2;
        this.gid = i3;
    }

    public int getPid() {
        return this.pid;
    }

    public int getUid() {
        return this.uid;
    }

    public int getGid() {
        return this.gid;
    }
}
