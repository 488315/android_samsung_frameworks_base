package com.android.server.remoteappmode;

import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.os.IBinder;

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
