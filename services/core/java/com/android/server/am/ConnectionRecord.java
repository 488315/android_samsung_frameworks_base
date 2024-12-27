package com.android.server.am;

import android.app.IServiceConnection;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.os.SystemClock;
import android.util.Slog;

import com.android.internal.app.procstats.AssociationState;
import com.android.internal.app.procstats.ProcessStats;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.wm.ActivityServiceConnectionsHolder;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ConnectionRecord implements OomAdjusterModernImpl.Connection {
    public static final int[] BIND_ORIG_ENUMS = {
        1,
        2,
        4,
        8388608,
        8,
        16,
        32,
        64,
        128,
        33554432,
        67108864,
        134217728,
        268435456,
        536870912,
        1073741824,
        256,
        4096,
        512
    };
    public static final int[] BIND_PROTO_ENUMS = {
        0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 16, 17, 18
    };
    public final ActivityServiceConnectionsHolder activity;
    public final ComponentName aliasComponent;
    public AssociationState.SourceState association;
    public final AppBindRecord binding;
    public final PendingIntent clientIntent;
    public final int clientLabel;
    public final String clientPackageName;
    public final String clientProcessName;
    public final int clientUid;
    public final IServiceConnection conn;
    public final long flags;
    public Object mProcStatsLock;
    public boolean serviceDead;
    public String stringName;

    public ConnectionRecord(
            AppBindRecord appBindRecord,
            ActivityServiceConnectionsHolder activityServiceConnectionsHolder,
            IServiceConnection iServiceConnection,
            long j,
            int i,
            PendingIntent pendingIntent,
            int i2,
            String str,
            String str2,
            ComponentName componentName) {
        this.binding = appBindRecord;
        this.activity = activityServiceConnectionsHolder;
        this.conn = iServiceConnection;
        this.flags = j;
        this.clientLabel = i;
        this.clientIntent = pendingIntent;
        this.clientUid = i2;
        this.clientProcessName = str;
        this.clientPackageName = str2;
        this.aliasComponent = componentName;
    }

    @Override // com.android.server.am.OomAdjusterModernImpl.Connection
    public final void computeHostOomAdjLSP(
            OomAdjuster oomAdjuster,
            ProcessRecord processRecord,
            ProcessRecord processRecord2,
            long j,
            ProcessRecord processRecord3,
            boolean z,
            int i) {
        oomAdjuster.computeServiceHostOomAdjLSP(
                this,
                processRecord,
                processRecord2,
                j,
                processRecord3,
                z,
                false,
                false,
                i,
                1001,
                false,
                false);
    }

    public final boolean hasFlag(int i) {
        return (Integer.toUnsignedLong(i) & this.flags) != 0;
    }

    public final boolean notHasFlag(int i) {
        return !hasFlag(i);
    }

    public final void startAssociationIfNeeded() {
        if (this.association == null) {
            ServiceRecord serviceRecord = this.binding.service;
            if (serviceRecord.app != null) {
                if (serviceRecord.appInfo.uid == this.clientUid
                        && serviceRecord.processName.equals(this.clientProcessName)) {
                    return;
                }
                ServiceRecord serviceRecord2 = this.binding.service;
                ProcessStats.ProcessStateHolder processStateHolder =
                        serviceRecord2.app.mPkgList.get(
                                serviceRecord2.instanceName.getPackageName());
                if (processStateHolder == null) {
                    Slog.wtf(
                            "ActivityManager",
                            "No package in referenced service "
                                    + this.binding.service.shortInstanceName
                                    + ": proc="
                                    + this.binding.service.app);
                    return;
                }
                if (processStateHolder.pkg != null) {
                    Object obj = this.binding.service.app.mService.mProcessStats.mLock;
                    this.mProcStatsLock = obj;
                    synchronized (obj) {
                        this.association =
                                processStateHolder
                                        .pkg
                                        .getAssociationStateLocked(
                                                processStateHolder.state,
                                                this.binding.service.instanceName.getClassName())
                                        .startSource(
                                                this.clientUid,
                                                this.clientProcessName,
                                                this.clientPackageName);
                    }
                    return;
                }
                Slog.wtf(
                        "ActivityManager",
                        "Inactive holder in referenced service "
                                + this.binding.service.shortInstanceName
                                + ": proc="
                                + this.binding.service.app);
            }
        }
    }

    public final void stopAssociation() {
        if (this.association != null) {
            synchronized (this.mProcStatsLock) {
                this.association.stop();
            }
            this.association = null;
        }
    }

    public final String toString() {
        String str = this.stringName;
        if (str != null) {
            return str;
        }
        StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(128, "ConnectionRecord{");
        m.append(Integer.toHexString(System.identityHashCode(this)));
        m.append(" u");
        AppBindRecord appBindRecord = this.binding;
        m.append(appBindRecord.client.userId);
        m.append(' ');
        if (hasFlag(1)) {
            m.append("CR ");
        }
        if (hasFlag(2)) {
            m.append("DBG ");
        }
        if (hasFlag(4)) {
            m.append("!FG ");
        }
        if (hasFlag(8388608)) {
            m.append("IMPB ");
        }
        if (hasFlag(8)) {
            m.append("ABCLT ");
        }
        if (hasFlag(16)) {
            m.append("OOM ");
        }
        if (hasFlag(32)) {
            m.append("WPRI ");
        }
        if (hasFlag(64)) {
            m.append("IMP ");
        }
        if (hasFlag(128)) {
            m.append("WACT ");
        }
        if (hasFlag(33554432)) {
            m.append("FGSA ");
        }
        if (hasFlag(67108864)) {
            m.append("FGS ");
        }
        if (hasFlag(134217728)) {
            m.append("LACT ");
        }
        if (hasFlag(524288)) {
            m.append("SLTA ");
        }
        if (hasFlag(268435456)) {
            m.append("VFGS ");
        }
        if (hasFlag(536870912)) {
            m.append("UI ");
        }
        if (hasFlag(1073741824)) {
            m.append("!VIS ");
        }
        if (hasFlag(256)) {
            m.append("!PRCP ");
        }
        if (hasFlag(512)) {
            m.append("BALF ");
        }
        if (hasFlag(4096)) {
            m.append("CAPS ");
        }
        if (this.serviceDead) {
            m.append("DEAD ");
        }
        m.append(appBindRecord.service.shortInstanceName);
        m.append(":@");
        m.append(Integer.toHexString(System.identityHashCode(this.conn.asBinder())));
        m.append(" flags=0x" + Long.toHexString(this.flags));
        m.append('}');
        String sb = m.toString();
        this.stringName = sb;
        return sb;
    }

    public final void trackProcState(int i, int i2) {
        if (this.association != null) {
            synchronized (this.mProcStatsLock) {
                this.association.trackProcState(i, i2, SystemClock.uptimeMillis());
            }
        }
    }
}
