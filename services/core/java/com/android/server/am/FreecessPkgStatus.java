package com.android.server.am;

import android.os.SystemClock;
import com.android.server.am.mars.MARsFreezeStateRecord;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class FreecessPkgStatus {
    public boolean isFreezedByCalm;
    public boolean monitorPacketFlag;
    public String name;
    public boolean restrictNetworkFlag;
    public int targetSdkVersion;
    public int uid;
    public int userId;
    public Long protectionElapsedRealtime = 0L;
    public String sharedUidName = null;
    public boolean isPendingUFZ = false;
    public boolean isInAllowList = false;
    public boolean isPidFreezed = false;
    public boolean isUidFreezed = false;
    public boolean isOlafAllowList = false;
    public int freezedState = 1;
    public int freezedType = 0;
    public ArrayList isolatedPids = null;
    public ArrayList frozenPids = null;
    public int isDoingGC = 0;
    public boolean isKilledByChimera = false;
    public long killedTime = 0;
    public int serviceTypes = 0;
    public MARsFreezeStateRecord freezedRecord = new MARsFreezeStateRecord(this);

    public FreecessPkgStatus(String str, int i, int i2, int i3) {
        this.name = str;
        this.uid = i;
        this.userId = i2;
        this.targetSdkVersion = i3;
    }

    public boolean isFreezeProtected() {
        synchronized (this.protectionElapsedRealtime) {
            return SystemClock.elapsedRealtime() < this.protectionElapsedRealtime.longValue();
        }
    }

    public void updateProtectionTime(long j) {
        synchronized (this.protectionElapsedRealtime) {
            long elapsedRealtime = SystemClock.elapsedRealtime() + j;
            if (elapsedRealtime > this.protectionElapsedRealtime.longValue()) {
                this.protectionElapsedRealtime = Long.valueOf(elapsedRealtime);
            }
        }
    }
}
