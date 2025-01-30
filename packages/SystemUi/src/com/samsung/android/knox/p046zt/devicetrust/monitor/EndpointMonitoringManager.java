package com.samsung.android.knox.p046zt.devicetrust.monitor;

import android.content.Context;
import android.os.Bundle;
import com.android.systemui.statusbar.notification.row.RowInflaterTask$$ExternalSyntheticOutline0;
import com.samsung.android.knox.p046zt.KnoxZtException;
import com.samsung.android.knox.p046zt.service.KnoxZtService;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class EndpointMonitoringManager {
    public static volatile EndpointMonitoringManager sInstance;
    public final KnoxZtService mService;

    private EndpointMonitoringManager(Context context) {
        try {
            this.mService = new KnoxZtService(context);
        } catch (Throwable th) {
            throw new KnoxZtException(RowInflaterTask$$ExternalSyntheticOutline0.m206m("EndpointMonitoringManager failed : ", th));
        }
    }

    public static EndpointMonitoringManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (EndpointMonitoringManager.class) {
                if (sInstance == null) {
                    sInstance = new EndpointMonitoringManager(context);
                }
            }
        }
        return sInstance;
    }

    public final int startMonitoringDomains(List<String> list, List<String> list2, IMonitoringListener iMonitoringListener) {
        try {
            return this.mService.startMonitoringDomains(list, list2, iMonitoringListener);
        } catch (Throwable th) {
            throw new KnoxZtException(RowInflaterTask$$ExternalSyntheticOutline0.m206m("startMonitoringFiles failed : ", th));
        }
    }

    public final int startMonitoringFiles(List<String> list, List<String> list2, IMonitoringListener iMonitoringListener) {
        try {
            return this.mService.startMonitoringFiles(list, list2, iMonitoringListener);
        } catch (Throwable th) {
            throw new KnoxZtException(RowInflaterTask$$ExternalSyntheticOutline0.m206m("startMonitoringFiles failed : ", th));
        }
    }

    public final int startTracing(int i, Bundle bundle, IMonitoringListener iMonitoringListener) {
        try {
            return this.mService.startTracing(i, bundle, iMonitoringListener);
        } catch (Throwable th) {
            throw new KnoxZtException(RowInflaterTask$$ExternalSyntheticOutline0.m206m("startTracing failed : ", th));
        }
    }

    public final int stopMonitoringDomains() {
        try {
            return this.mService.stopMonitoringDomains();
        } catch (Throwable th) {
            throw new KnoxZtException(RowInflaterTask$$ExternalSyntheticOutline0.m206m("stopMonitoringDomains failed : ", th));
        }
    }

    public final int stopMonitoringFiles() {
        try {
            return this.mService.stopMonitoringFiles();
        } catch (Throwable th) {
            throw new KnoxZtException(RowInflaterTask$$ExternalSyntheticOutline0.m206m("stopMonitoringFiles failed : ", th));
        }
    }

    public final int stopTracing(int i) {
        return stopTracing(i, null);
    }

    public final int stopTracing(int i, IMonitoringListener iMonitoringListener) {
        try {
            return this.mService.stopTracing(i, iMonitoringListener);
        } catch (Throwable th) {
            throw new KnoxZtException(RowInflaterTask$$ExternalSyntheticOutline0.m206m("stopTracing failed : ", th));
        }
    }
}
