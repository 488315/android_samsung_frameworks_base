package com.android.server.location.gnss;

import android.content.pm.ApplicationInfo;

import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class GnssVisibilityControl$$ExternalSyntheticLambda1
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ GnssVisibilityControl f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ GnssVisibilityControl$$ExternalSyntheticLambda1(
            GnssVisibilityControl gnssVisibilityControl, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = gnssVisibilityControl;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                GnssVisibilityControl gnssVisibilityControl = this.f$0;
                List<String> list = (List) this.f$1;
                gnssVisibilityControl.getClass();
                if (list.size() == gnssVisibilityControl.mProxyAppsState.size()) {
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        if (!gnssVisibilityControl.mProxyAppsState.containsKey(
                                (String) it.next())) {}
                    }
                    return;
                }
                if (list.isEmpty()) {
                    if (gnssVisibilityControl.mProxyAppsState.isEmpty()) {
                        return;
                    }
                    gnssVisibilityControl.mPackageManager.removeOnPermissionsChangeListener(
                            gnssVisibilityControl.mOnPermissionsChangedListener);
                    gnssVisibilityControl.resetProxyAppsState();
                    gnssVisibilityControl.updateNfwLocationAccessProxyAppsInGnssHal();
                    return;
                }
                if (gnssVisibilityControl.mProxyAppsState.isEmpty()) {
                    gnssVisibilityControl.mPackageManager.addOnPermissionsChangeListener(
                            gnssVisibilityControl.mOnPermissionsChangedListener);
                } else {
                    gnssVisibilityControl.resetProxyAppsState();
                }
                for (String str : list) {
                    boolean shouldEnableLocationPermissionInGnssHal =
                            gnssVisibilityControl.shouldEnableLocationPermissionInGnssHal(str);
                    GnssVisibilityControl.ProxyAppState proxyAppState =
                            new GnssVisibilityControl.ProxyAppState();
                    proxyAppState.mHasLocationPermission = shouldEnableLocationPermissionInGnssHal;
                    gnssVisibilityControl.mProxyAppsState.put(str, proxyAppState);
                }
                gnssVisibilityControl.updateNfwLocationAccessProxyAppsInGnssHal();
                return;
            case 1:
                GnssVisibilityControl gnssVisibilityControl2 = this.f$0;
                Runnable runnable = (Runnable) this.f$1;
                gnssVisibilityControl2.getClass();
                try {
                    runnable.run();
                    return;
                } finally {
                    gnssVisibilityControl2.mWakeLock.release();
                }
            default:
                GnssVisibilityControl gnssVisibilityControl3 = this.f$0;
                String str2 = (String) this.f$1;
                ApplicationInfo proxyAppInfo = gnssVisibilityControl3.getProxyAppInfo(str2);
                if (proxyAppInfo != null) {
                    gnssVisibilityControl3.clearLocationIcon(
                            (GnssVisibilityControl.ProxyAppState)
                                    gnssVisibilityControl3.mProxyAppsState.get(str2),
                            proxyAppInfo.uid,
                            str2);
                    return;
                }
                return;
        }
    }
}
