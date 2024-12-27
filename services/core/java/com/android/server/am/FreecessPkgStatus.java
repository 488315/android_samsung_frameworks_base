package com.android.server.am;

import android.os.SystemClock;
import android.util.ArraySet;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.am.mars.MARsDebugConfig;
import com.android.server.am.mars.MARsFreezeStateRecord;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class FreecessPkgStatus {
    public final MARsFreezeStateRecord freezedRecord;
    public boolean isFreezedByCalm;
    public boolean monitorPacketFlag;
    public final String name;
    public boolean restrictNetworkFlag;
    public final int targetSdkVersion;
    public final int uid;
    public final int userId;
    public Long protectionElapsedRealtime = 0L;
    public int latestProcState = -1;
    public String sharedUidName = null;
    public boolean isPendingUFZ = false;
    public boolean isInAllowList = false;
    public boolean isOlafAllowList = false;
    public int freezedState = 1;
    public int freezedType = 0;
    public int isDoingGC = 0;
    public int serviceTypes = 0;

    public FreecessPkgStatus(String str, int i, int i2, int i3) {
        this.name = str;
        this.uid = i;
        this.userId = i2;
        this.targetSdkVersion = i3;
        MARsFreezeStateRecord mARsFreezeStateRecord = new MARsFreezeStateRecord();
        mARsFreezeStateRecord.mFreezeCounts = new int[MARsFreezeStateRecord.FreezeReasonType.values().length];
        mARsFreezeStateRecord.mUnfreezeCounts = new int[MARsFreezeStateRecord.UnfreezeReasonType.values().length];
        mARsFreezeStateRecord.mFreecessParent = this;
        mARsFreezeStateRecord.isFrozen = false;
        mARsFreezeStateRecord.isLcdOnFreezed = false;
        mARsFreezeStateRecord.isOLAFFreezed = false;
        mARsFreezeStateRecord.isLcdOffFreezed = false;
        mARsFreezeStateRecord.freezedTime = 0L;
        mARsFreezeStateRecord.unfreezedTime = 0L;
        mARsFreezeStateRecord.unfreezedReason = null;
        if (MARsDebugConfig.DEBUG_FREECESS) {
            DeviceIdleController$$ExternalSyntheticOutline0.m("initRealtimeDetection ", str, " rate:12", "MARsFreezeStateRecord");
        }
        mARsFreezeStateRecord.mAvailableTokens = 512L;
        mARsFreezeStateRecord.mDetectionVer = 0;
        long currentTimeMillis = System.currentTimeMillis();
        mARsFreezeStateRecord.mTokensUpdateTime = currentTimeMillis;
        mARsFreezeStateRecord.initialStateTime = currentTimeMillis;
        ArraySet arraySet = mARsFreezeStateRecord.mUnfreezeAbuseDetections;
        if (arraySet != null) {
            arraySet.clear();
        } else {
            mARsFreezeStateRecord.mUnfreezeAbuseDetections = new ArraySet();
        }
        this.freezedRecord = mARsFreezeStateRecord;
    }

    public final void updateProtectionTime(long j) {
        synchronized (this.protectionElapsedRealtime) {
            try {
                long elapsedRealtime = SystemClock.elapsedRealtime() + j;
                if (elapsedRealtime > this.protectionElapsedRealtime.longValue()) {
                    this.protectionElapsedRealtime = Long.valueOf(elapsedRealtime);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
