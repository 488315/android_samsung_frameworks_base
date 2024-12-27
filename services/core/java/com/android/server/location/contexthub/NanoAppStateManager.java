package com.android.server.location.contexthub;

import android.hardware.location.NanoAppInstanceInfo;
import android.util.Log;

import java.util.HashMap;
import java.util.Iterator;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class NanoAppStateManager {
    public final HashMap mNanoAppHash = new HashMap();
    public int mNextHandle = 0;

    public final synchronized void addNanoAppInstance(int i, int i2, long j) {
        synchronized (this) {
            this.mNanoAppHash.remove(Integer.valueOf(getNanoAppHandle(i, j)));
        }
        if (this.mNanoAppHash.size() == Integer.MAX_VALUE) {
            Log.e("NanoAppStateManager", "Error adding nanoapp instance: max limit exceeded");
            return;
        }
        int i3 = 0;
        int i4 = this.mNextHandle;
        int i5 = 0;
        while (true) {
            if (i5 > Integer.MAX_VALUE) {
                break;
            }
            if (this.mNanoAppHash.containsKey(Integer.valueOf(i4))) {
                i4 = i4 == Integer.MAX_VALUE ? 0 : i4 + 1;
                i5++;
            } else {
                this.mNanoAppHash.put(Integer.valueOf(i4), new NanoAppInstanceInfo(i4, j, i2, i));
                if (i4 != Integer.MAX_VALUE) {
                    i3 = i4 + 1;
                }
                this.mNextHandle = i3;
            }
        }
        Log.v(
                "NanoAppStateManager",
                "Added app instance with handle "
                        + i4
                        + " to hub "
                        + i
                        + ": ID=0x"
                        + Long.toHexString(j)
                        + ", version=0x"
                        + Integer.toHexString(i2));
    }

    public final synchronized void foreachNanoAppInstanceInfo(Consumer consumer) {
        Iterator it = this.mNanoAppHash.values().iterator();
        while (it.hasNext()) {
            consumer.accept((NanoAppInstanceInfo) it.next());
        }
    }

    public final synchronized int getNanoAppHandle(int i, long j) {
        for (NanoAppInstanceInfo nanoAppInstanceInfo : this.mNanoAppHash.values()) {
            if (nanoAppInstanceInfo.getContexthubId() == i && nanoAppInstanceInfo.getAppId() == j) {
                return nanoAppInstanceInfo.getHandle();
            }
        }
        return -1;
    }

    public final void handleQueryAppEntry(int i, int i2, long j) {
        int nanoAppHandle = getNanoAppHandle(i, j);
        if (nanoAppHandle == -1) {
            addNanoAppInstance(i, i2, j);
            return;
        }
        if (((NanoAppInstanceInfo) this.mNanoAppHash.get(Integer.valueOf(nanoAppHandle)))
                        .getAppVersion()
                != i2) {
            this.mNanoAppHash.put(
                    Integer.valueOf(nanoAppHandle),
                    new NanoAppInstanceInfo(nanoAppHandle, j, i2, i));
            Log.v(
                    "NanoAppStateManager",
                    "Updated app instance with handle "
                            + nanoAppHandle
                            + " at hub "
                            + i
                            + ": ID=0x"
                            + Long.toHexString(j)
                            + ", version=0x"
                            + Integer.toHexString(i2));
        }
    }
}
