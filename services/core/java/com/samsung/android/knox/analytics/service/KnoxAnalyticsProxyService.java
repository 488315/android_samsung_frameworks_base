package com.samsung.android.knox.analytics.service;

import android.content.Context;
import android.os.ServiceManager;
import com.samsung.android.knox.analytics.util.Log;
import com.samsung.android.knox.knoxanalyticsproxy.IKnoxAnalyticsProxy;
import com.samsung.android.knox.knoxanalyticsproxy.KnoxAnalyticsData;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class KnoxAnalyticsProxyService extends IKnoxAnalyticsProxy.Stub {
    public String TAG = "[KnoxAnalytics] KnoxAnalyticsProxyService";
    public final Context mContext;

    public KnoxAnalyticsProxyService(Context context) {
        this.mContext = context;
    }

    public final void log(KnoxAnalyticsData knoxAnalyticsData) {
        KnoxAnalyticsServiceImpl knoxAnalyticsServiceImpl = (KnoxAnalyticsServiceImpl) ServiceManager.getService(KnoxAnalyticsSystemService.ANALYTICS_SERVICE);
        if (knoxAnalyticsServiceImpl == null) {
            return;
        }
        try {
            knoxAnalyticsServiceImpl.log(knoxAnalyticsData);
        } catch (Exception unused) {
            Log.e(this.TAG, "log() proxy failed");
        }
    }
}
