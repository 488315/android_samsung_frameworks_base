package com.samsung.android.knox.zt.devicetrust.monitor;

import android.content.Context;
import android.os.Bundle;
import com.android.systemui.statusbar.notification.row.RowInflaterTask$$ExternalSyntheticOutline0;
import com.samsung.android.knox.zt.KnoxZtException;
import com.samsung.android.knox.zt.service.KnoxZtService;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class EndpointMonitoringManager {
    public static volatile EndpointMonitoringManager sInstance;
    public final KnoxZtService mService;

    private EndpointMonitoringManager(Context context) throws KnoxZtException {
        try {
            this.mService = new KnoxZtService(context);
        } catch (Throwable th) {
            throw new KnoxZtException(RowInflaterTask$$ExternalSyntheticOutline0.m("EndpointMonitoringManager failed : ", th));
        }
    }

    public static EndpointMonitoringManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (EndpointMonitoringManager.class) {
                try {
                    if (sInstance == null) {
                        sInstance = new EndpointMonitoringManager(context);
                    }
                } finally {
                }
            }
        }
        return sInstance;
    }

    public String ackSignal(long[] jArr) {
        try {
            return this.mService.ackSignal(jArr);
        } catch (Throwable th) {
            throw new KnoxZtException(RowInflaterTask$$ExternalSyntheticOutline0.m("ackSignal failed : ", th));
        }
    }

    public String getMonitoringSnapshot(int i) {
        try {
            return this.mService.getMonitoringSnapshot(i);
        } catch (Throwable th) {
            throw new KnoxZtException(RowInflaterTask$$ExternalSyntheticOutline0.m("getMonitoringSnapshot failed : ", th));
        }
    }

    public String getVersion() {
        try {
            return this.mService.getVersion();
        } catch (Throwable th) {
            throw new KnoxZtException(RowInflaterTask$$ExternalSyntheticOutline0.m("getVersion failed : ", th));
        }
    }

    public int queryAllSignals(IChunkedStringCallback iChunkedStringCallback) {
        try {
            return this.mService.queryAllSignals(iChunkedStringCallback);
        } catch (Throwable th) {
            throw new KnoxZtException(RowInflaterTask$$ExternalSyntheticOutline0.m("queryAllSignals failed : ", th));
        }
    }

    public int querySignals(String str, IChunkedStringCallback iChunkedStringCallback) {
        try {
            return this.mService.querySignals(str, iChunkedStringCallback);
        } catch (Throwable th) {
            throw new KnoxZtException(RowInflaterTask$$ExternalSyntheticOutline0.m("querySignals failed : ", th));
        }
    }

    public int startMonitoringDomains(List<String> list, List<String> list2, IMonitoringListener iMonitoringListener) {
        try {
            return this.mService.startMonitoringDomains(list, list2, iMonitoringListener);
        } catch (Throwable th) {
            throw new KnoxZtException(RowInflaterTask$$ExternalSyntheticOutline0.m("startMonitoringFiles failed : ", th));
        }
    }

    public int startMonitoringFiles(List<String> list, List<String> list2, IMonitoringListener iMonitoringListener) {
        try {
            return this.mService.startMonitoringFiles(list, list2, iMonitoringListener);
        } catch (Throwable th) {
            throw new KnoxZtException(RowInflaterTask$$ExternalSyntheticOutline0.m("startMonitoringFiles failed : ", th));
        }
    }

    public int startMonitoringSignals(SignalMonitoringListener signalMonitoringListener) {
        try {
            return this.mService.startMonitoringSignals(signalMonitoringListener);
        } catch (Throwable th) {
            throw new KnoxZtException(RowInflaterTask$$ExternalSyntheticOutline0.m("startMonitoringSignals failed : ", th));
        }
    }

    public int startTracing(int i, Bundle bundle, IMonitoringListener iMonitoringListener) {
        try {
            return this.mService.startTracing(i, bundle, iMonitoringListener);
        } catch (Throwable th) {
            throw new KnoxZtException(RowInflaterTask$$ExternalSyntheticOutline0.m("startTracing failed : ", th));
        }
    }

    public int stopMonitoringDomains() {
        try {
            return this.mService.stopMonitoringDomains();
        } catch (Throwable th) {
            throw new KnoxZtException(RowInflaterTask$$ExternalSyntheticOutline0.m("stopMonitoringDomains failed : ", th));
        }
    }

    public int stopMonitoringFiles() {
        try {
            return this.mService.stopMonitoringFiles();
        } catch (Throwable th) {
            throw new KnoxZtException(RowInflaterTask$$ExternalSyntheticOutline0.m("stopMonitoringFiles failed : ", th));
        }
    }

    public int stopMonitoringSignals(SignalMonitoringListener signalMonitoringListener) {
        try {
            return this.mService.stopMonitoringSignals(signalMonitoringListener);
        } catch (Throwable th) {
            throw new KnoxZtException(RowInflaterTask$$ExternalSyntheticOutline0.m("startMonitoringSignals failed : ", th));
        }
    }

    public int stopTracing(int i) {
        return stopTracing(i, null);
    }

    public int stopTracing(int i, IMonitoringListener iMonitoringListener) {
        try {
            return this.mService.stopTracing(i, iMonitoringListener);
        } catch (Throwable th) {
            throw new KnoxZtException(RowInflaterTask$$ExternalSyntheticOutline0.m("stopTracing failed : ", th));
        }
    }
}
