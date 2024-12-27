package com.android.server.remoteappmode;

import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.os.IBinder;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class ListenerInfo implements IBinder.DeathRecipient {
    public final String name;
    public final int pid;
    public final int uid;

    public ListenerInfo(int i, int i2, String str) {
        this.name = str;
        this.pid = i;
        this.uid = i2;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append("(name=");
        sb.append(this.name);
        sb.append(", pid=");
        sb.append(this.pid);
        sb.append(", uid=");
        return AmFmBandRange$$ExternalSyntheticOutline0.m(this.uid, sb, ")");
    }
}
