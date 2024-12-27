package com.android.server.knox;

import android.os.Binder;
import android.os.Bundle;
import android.os.SystemProperties;
import android.util.Log;

import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;

import com.samsung.android.knox.analytics.KnoxAnalyticsData;

public final class SeparatedAppsAnalytics {
    public static final boolean DEBUG = "eng".equals(SystemProperties.get("ro.build.type"));
    public final IKnoxAnalyticsContainerImpl ifKnoxAnalyticsContainer;

    public SeparatedAppsAnalytics(IKnoxAnalyticsContainerImpl iKnoxAnalyticsContainerImpl) {
        this.ifKnoxAnalyticsContainer = iKnoxAnalyticsContainerImpl;
    }

    public final void logEvent(Bundle bundle, String str) {
        long j;
        IKnoxAnalyticsContainerImpl iKnoxAnalyticsContainerImpl = this.ifKnoxAnalyticsContainer;
        iKnoxAnalyticsContainerImpl.getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                j =
                        iKnoxAnalyticsContainerImpl
                                .getUserManager()
                                .getUserInfo(
                                        iKnoxAnalyticsContainerImpl.personaManagerService
                                                .getAppSeparationId())
                                .creationTime;
            } catch (Exception e) {
                e.printStackTrace();
                Binder.restoreCallingIdentity(clearCallingIdentity);
                j = 0;
            }
            bundle.putLong("id", j);
            KnoxAnalyticsData knoxAnalyticsData =
                    new KnoxAnalyticsData("KNOX_APP_SEPARATION", 1, str);
            for (String str2 : bundle.keySet()) {
                Object obj = bundle.get(str2);
                if (obj instanceof Integer) {
                    knoxAnalyticsData.setProperty(str2, (Integer) obj);
                } else if (obj instanceof String) {
                    knoxAnalyticsData.setProperty(str2, (String) obj);
                } else if (obj instanceof Long) {
                    knoxAnalyticsData.setProperty(str2, (Long) obj);
                } else if (obj instanceof String[]) {
                    knoxAnalyticsData.setProperty(str2, (String[]) obj);
                }
            }
            IKnoxAnalyticsContainerImpl.sendAnalyticsLog(knoxAnalyticsData);
            if (DEBUG) {
                StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str, " / ");
                m.append(knoxAnalyticsData.toString());
                Log.d("SeparatedAppsAnalytics", m.toString());
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
