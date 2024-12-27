package com.android.server.am.mars;

import android.net.INetd;
import android.os.Bundle;
import android.os.Message;
import android.os.UserHandle;
import android.util.ArraySet;
import android.util.Slog;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.am.FreecessController;
import com.android.server.am.FreecessController$$ExternalSyntheticOutline0;
import com.android.server.am.FreecessPkgStatus;
import com.android.server.am.MARsHandler;
import com.android.server.am.MARsPackageInfo;
import com.android.server.am.MARsPolicyManager;
import com.android.server.input.KeyboardMetricsCollector;

import java.text.SimpleDateFormat;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class MARsFreezeStateRecord {
    public long freezedTime;
    public long initialStateTime;
    public boolean isFrozen;
    public boolean isLcdOffFreezed;
    public boolean isLcdOnFreezed;
    public boolean isOLAFFreezed;
    public long mAvailableTokens;
    public int mDetectionVer;
    public FreecessPkgStatus mFreecessParent;
    public int[] mFreezeCounts;
    public long mTokensUpdateTime;
    public ArraySet mUnfreezeAbuseDetections;
    public int[] mUnfreezeCounts;
    public String unfreezedReason;
    public long unfreezedTime;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public enum FreezeReasonType {
        FREEZE_TOTAL("FREEZE_TOTAL", "Total"),
        /* JADX INFO: Fake field, exist only in values array */
        EF27("FREEZE_REASON_LCDON", "Bg"),
        /* JADX INFO: Fake field, exist only in values array */
        EF41("FREEZE_REASON_LCDOFF", "LEV"),
        /* JADX INFO: Fake field, exist only in values array */
        EF55("FREEZE_REASON_OLAF", "OLAF");

        private final String freezeType;
        private final int typeNum;

        FreezeReasonType(String str, String str2) {
            this.typeNum = r2.intValue();
            this.freezeType = str2;
        }

        public final int getTypeNum() {
            return this.typeNum;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public enum UnfreezeReasonType {
        UNFREEZE_TOTAL("UNFREEZE_TOTAL", "Total"),
        UNFREEZE_REASON_ALARM("UNFREEZE_REASON_ALARM", "Alarm"),
        UNFREEZE_REASON_PACKET("UNFREEZE_REASON_PACKET", "Packet"),
        /* JADX INFO: Fake field, exist only in values array */
        EF3("UNFREEZE_REASON_WAKELOCK", "Wakelock"),
        /* JADX INFO: Fake field, exist only in values array */
        EF4("UNFREEZE_REASON_BINDER_1", "Binder(1)"),
        /* JADX INFO: Fake field, exist only in values array */
        EF5("UNFREEZE_REASON_BINDER_0", "Binder(0)"),
        /* JADX INFO: Fake field, exist only in values array */
        EF6("UNFREEZE_REASON_DEVICE_IDLE_OFF", "DeviceIdleOFF"),
        /* JADX INFO: Fake field, exist only in values array */
        EF7("UNFREEZE_REASON_START_PROCESS", "StartProcessP"),
        /* JADX INFO: Fake field, exist only in values array */
        EF8("UNFREEZE_REASON_LAUNCHING_PROVIDER", "LaunchingProvider"),
        /* JADX INFO: Fake field, exist only in values array */
        EF9("UNFREEZE_REASON_RECEIVING_INTENT", "ReceivingIntent"),
        /* JADX INFO: Fake field, exist only in values array */
        EF10("UNFREEZE_REASON_EXECUITNG_SERVICE", "ExecutingService"),
        /* JADX INFO: Fake field, exist only in values array */
        EF11("UNFREEZE_REASON_UIDACTIVE", "UidActive"),
        /* JADX INFO: Fake field, exist only in values array */
        EF12("UNFREEZE_REASON_STARTSERVICE", "startService"),
        /* JADX INFO: Fake field, exist only in values array */
        EF13("UNFREEZE_REASON_BINDSERVICE", "bindService"),
        /* JADX INFO: Fake field, exist only in values array */
        EF14("UNFREEZE_REASON_ACTIVITY", "activity"),
        /* JADX INFO: Fake field, exist only in values array */
        EF15("UNFREEZE_REASON_BROADCAST", INetd.IF_FLAG_BROADCAST),
        /* JADX INFO: Fake field, exist only in values array */
        EF14("UNFREEZE_REASON_PROVIDER", "provider"),
        /* JADX INFO: Fake field, exist only in values array */
        EF15("UNFREEZE_REASON_UNBINDSERVICE", "unbindService"),
        /* JADX INFO: Fake field, exist only in values array */
        EF14("UNFREEZE_REASON_STARTPROCESS", "startProcess"),
        /* JADX INFO: Fake field, exist only in values array */
        EF15("UNFREEZE_REASON_SERVICEANR", "ServiceANR"),
        /* JADX INFO: Fake field, exist only in values array */
        EF14("UNFREEZE_REASON_SIGNAL", "Signal"),
        /* JADX INFO: Fake field, exist only in values array */
        EF15("UNFREEZE_REASON_CFB", "Cfb"),
        /* JADX INFO: Fake field, exist only in values array */
        EF14("UNFREEZE_REASON_PROC_STATE", "procstate"),
        /* JADX INFO: Fake field, exist only in values array */
        EF15("UNFREEZE_REASON_OLAF", "OLAF:"),
        UNFREEZE_REASON_NONE("UNFREEZE_REASON_NONE", KeyboardMetricsCollector.DEFAULT_LANGUAGE_TAG);

        private final int typeNum;
        private final String unfreezeType;

        UnfreezeReasonType(String str, String str2) {
            this.typeNum = r2.intValue();
            this.unfreezeType = str2;
        }

        public static UnfreezeReasonType reasonTypeOf(String str) {
            UnfreezeReasonType unfreezeReasonType = UNFREEZE_REASON_NONE;
            if (str != null && !str.isEmpty()) {
                for (UnfreezeReasonType unfreezeReasonType2 : values()) {
                    if (str.contains(unfreezeReasonType2.unfreezeType)) {
                        return unfreezeReasonType2;
                    }
                }
            }
            return unfreezeReasonType;
        }

        public final int getTypeNum() {
            return this.typeNum;
        }
    }

    public final boolean abnormalRealtimeDetectionVer0(long j) {
        long j2 = j - this.mTokensUpdateTime;
        if (MARsDebugConfig.DEBUG_FREECESS) {
            StringBuilder sb = new StringBuilder("abnormalRealtimeDetection ");
            sb.append(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS").format(Long.valueOf(j)));
            sb.append(" elapsed:");
            BatteryService$$ExternalSyntheticOutline0.m(sb, j2, "MARsFreezeStateRecord");
        }
        if (j2 < 0) {
            this.mTokensUpdateTime = j;
            return false;
        }
        long j3 = j2 >> 12;
        this.mAvailableTokens = Math.min(512L, this.mAvailableTokens + j3);
        this.mTokensUpdateTime = j;
        if (MARsDebugConfig.DEBUG_FREECESS) {
            BatteryService$$ExternalSyntheticOutline0.m(
                    BatteryService$$ExternalSyntheticOutline0.m(
                            "newTokens:", j3, " adjusted available tokens: "),
                    this.mAvailableTokens,
                    "MARsFreezeStateRecord");
        }
        long j4 = this.mAvailableTokens;
        if (j4 <= 0) {
            return true;
        }
        this.mAvailableTokens = j4 - 1;
        return false;
    }

    public final void cancelRestrictState() {
        if (MARsDebugConfig.DEBUG_MARs) {
            Slog.d("MARsFreezeStateRecord", "updateRestrictState");
        }
        boolean z = MARsPolicyManager.MARs_ENABLE;
        MARsPolicyManager mARsPolicyManager = MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
        FreecessPkgStatus freecessPkgStatus = this.mFreecessParent;
        String str = freecessPkgStatus.name;
        int i = freecessPkgStatus.uid;
        mARsPolicyManager.getClass();
        Slog.d("MARsPolicyManager", "cancelRestrict uid:" + i + " pkgname:" + str);
        MARsHandler mARsHandler = MARsHandler.MARsHandlerHolder.INSTANCE;
        if (mARsHandler.mMainHandler != null) {
            Bundle m = FreecessController$$ExternalSyntheticOutline0.m(i, "pkgName", str, "uid");
            Message obtainMessage = mARsHandler.mMainHandler.obtainMessage(19);
            obtainMessage.setData(m);
            mARsHandler.mMainHandler.sendMessage(obtainMessage);
        }
        mARsPolicyManager.addDebugInfoToHistory("Abusive", "[cancel_restrict]" + i);
        boolean z2 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
        FreecessController freecessController =
                FreecessController.FreecessControllerHolder.INSTANCE;
        FreecessPkgStatus freecessPkgStatus2 = this.mFreecessParent;
        freecessController.getClass();
        if (MARsDebugConfig.DEBUG_ENG) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    new StringBuilder("removeRestrictedPackages uid: "),
                    freecessPkgStatus2.uid,
                    "FreecessController");
        }
        synchronized (MARsPolicyManager.MARsLock) {
            try {
                if (freecessController.mRestrictedPackages.mUidMap.get(freecessPkgStatus2.uid)
                        != null) {
                    freecessController.mRestrictedPackages.remove(
                            freecessPkgStatus2.uid, freecessPkgStatus2.name);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final String dumpUfzContent() {
        StringBuilder sb = new StringBuilder();
        UnfreezeReasonType[] values = UnfreezeReasonType.values();
        int length = values.length;
        int i = 0;
        while (i < length) {
            i =
                    MARsFreezeStateRecord$$ExternalSyntheticOutline0.m(
                            "%-4d|",
                            new Object[] {
                                Integer.valueOf(this.mUnfreezeCounts[values[i].getTypeNum()])
                            },
                            sb,
                            i,
                            1);
        }
        return sb.toString();
    }

    public final void handleAbnormalApp(long j) {
        if (MARsDebugConfig.DEBUG_MARs) {
            Slog.d("MARsFreezeStateRecord", "handleAbnormalApp");
        }
        this.mUnfreezeAbuseDetections.add(Long.valueOf(j));
        int[] iArr = this.mUnfreezeCounts;
        int i = iArr[1];
        int i2 = i > 0 ? 1 : -1;
        for (int i3 = 2; i3 < iArr.length; i3++) {
            int i4 = iArr[i3];
            if (i < i4) {
                i2 = i3;
                i = i4;
            }
        }
        boolean z = MARsPolicyManager.MARs_ENABLE;
        MARsPolicyManager mARsPolicyManager = MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
        FreecessPkgStatus freecessPkgStatus = this.mFreecessParent;
        String str = freecessPkgStatus.name;
        int i5 = freecessPkgStatus.uid;
        mARsPolicyManager.getClass();
        MARsPolicyManager.Lock lock = MARsPolicyManager.MARsLock;
        synchronized (lock) {
            try {
                MARsPackageInfo mARsPackageInfo =
                        MARsPolicyManager.getMARsPackageInfo(
                                mARsPolicyManager.mMARsTargetPackages,
                                str,
                                UserHandle.getUserId(i5));
                if (mARsPackageInfo != null) {
                    Slog.d(
                            "MARsPolicyManager",
                            "updateAbusiveAppFromBartender uid:"
                                    + i5
                                    + " pkgname:"
                                    + str
                                    + " type:excessive_unfreez");
                    if (UnfreezeReasonType.UNFREEZE_REASON_ALARM.getTypeNum() == i2) {
                        mARsPackageInfo.optionFlag |= 8;
                    } else if (UnfreezeReasonType.UNFREEZE_REASON_PACKET.getTypeNum() == i2) {
                        mARsPackageInfo.optionFlag |= 16;
                        boolean z2 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                        FreecessController.FreecessControllerHolder.INSTANCE
                                .updateAbnormalAppFirewall(mARsPackageInfo.uid, false);
                        mARsPolicyManager.closeSocketsForUid(mARsPackageInfo.uid);
                    }
                    MARsHandler.MARsHandlerHolder.INSTANCE.sendAnomalyMsgToMainHandler(
                            mARsPackageInfo.uid, mARsPackageInfo.name, "excessive_unfreeze");
                    mARsPolicyManager.addDebugInfoToHistory("Abusive", "[excessive_unfreez]" + i5);
                }
            } finally {
            }
        }
        if (UnfreezeReasonType.UNFREEZE_REASON_ALARM.getTypeNum() == i2
                || UnfreezeReasonType.UNFREEZE_REASON_PACKET.getTypeNum() == i2) {
            boolean z3 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
            FreecessController freecessController =
                    FreecessController.FreecessControllerHolder.INSTANCE;
            FreecessPkgStatus freecessPkgStatus2 = this.mFreecessParent;
            freecessController.getClass();
            if (MARsDebugConfig.DEBUG_ENG) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        new StringBuilder("addRestrictedPackages uid: "),
                        freecessPkgStatus2.uid,
                        "FreecessController");
            }
            synchronized (lock) {
                try {
                    if (freecessController.mRestrictedPackages.mUidMap.get(freecessPkgStatus2.uid)
                            == null) {
                        freecessController.mRestrictedPackages.put(
                                freecessPkgStatus2.name,
                                freecessPkgStatus2.uid,
                                freecessPkgStatus2);
                    }
                } finally {
                }
            }
        }
        this.mAvailableTokens = 512L;
    }

    public final void updateUnfreezeState(long j, String str) {
        this.unfreezedTime = j;
        this.unfreezedReason = str;
        int typeNum = UnfreezeReasonType.UNFREEZE_TOTAL.getTypeNum();
        int[] iArr = this.mUnfreezeCounts;
        iArr[typeNum] = iArr[typeNum] + 1;
        try {
            int typeNum2 = UnfreezeReasonType.reasonTypeOf(str).getTypeNum();
            iArr[typeNum2] = iArr[typeNum2] + 1;
            boolean z = MARsPolicyManager.MARs_ENABLE;
            MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.getClass();
            if (MARsPolicyManager.isChinaPolicyEnabled()) {
                boolean z2 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                FreecessController freecessController =
                        FreecessController.FreecessControllerHolder.INSTANCE;
                FreecessPkgStatus freecessPkgStatus = this.mFreecessParent;
                freecessController.getClass();
                if (FreecessController.isInFreecessExcludeList(freecessPkgStatus)
                        || !abnormalRealtimeDetectionVer0(j)) {
                    return;
                }
                Slog.i(
                        "MARsFreezeStateRecord",
                        "ver:0 catch abnormal unfreeze detection at "
                                + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS")
                                        .format(Long.valueOf(j)));
                handleAbnormalApp(j);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
