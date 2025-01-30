package com.samsung.android.knox.analytics.service;

import android.content.Context;
import android.os.Binder;
import com.samsung.android.knox.analytics.IKnoxAnalyticsService;
import com.samsung.android.knox.analytics.KnoxAnalyticsData;
import com.samsung.android.knox.analytics.activation.ActivationMonitor;
import com.samsung.android.knox.analytics.activation.model.IActivationObserver;
import com.samsung.android.knox.analytics.util.Log;
import com.samsung.android.knox.analytics.util.SecurityUtils;

/* loaded from: classes2.dex */
public class KnoxAnalyticsServiceImpl extends IKnoxAnalyticsService.Stub {
    public static final String TAG = "[KnoxAnalytics] " + KnoxAnalyticsServiceImpl.class.getSimpleName();
    public IActivationObserver mActivationObserver;
    public final Context mContext;
    public EventQueue mEventQueue;
    public boolean mIsKnoxAnalyticsActivated = true;

    public KnoxAnalyticsServiceImpl(Context context, ActivationMonitor activationMonitor, EventQueue eventQueue) {
        IActivationObserver iActivationObserver = new IActivationObserver() { // from class: com.samsung.android.knox.analytics.service.KnoxAnalyticsServiceImpl.1
            @Override // com.samsung.android.knox.analytics.activation.model.IActivationObserver
            public void onStatusChanged(int i, boolean z, String str) {
            }

            @Override // com.samsung.android.knox.analytics.activation.model.IActivationObserver
            public void onTriggerChanged(int i, boolean z, String str) {
            }

            @Override // com.samsung.android.knox.analytics.activation.model.IActivationObserver
            public void onKnoxAnalyticsActivation(boolean z) {
                Log.d(KnoxAnalyticsServiceImpl.TAG, "onKnoxAnalyticsActivation()");
                KnoxAnalyticsServiceImpl.this.mIsKnoxAnalyticsActivated = true;
            }

            @Override // com.samsung.android.knox.analytics.activation.model.IActivationObserver
            public void onKnoxAnalyticsDeactivation(boolean z) {
                Log.d(KnoxAnalyticsServiceImpl.TAG, "onKnoxAnalyticsDeactivation()");
                KnoxAnalyticsServiceImpl.this.mIsKnoxAnalyticsActivated = false;
            }
        };
        this.mActivationObserver = iActivationObserver;
        this.mContext = context;
        this.mEventQueue = eventQueue;
        activationMonitor.registerObserver(iActivationObserver);
    }

    public void log(KnoxAnalyticsData knoxAnalyticsData) {
        if (knoxAnalyticsData == null) {
            Log.e(TAG, "log(): null data");
            return;
        }
        if (!this.mIsKnoxAnalyticsActivated) {
            Log.d(TAG, "KnoxAnalytics has been shutdown, can't log.");
            return;
        }
        SecurityUtils.enforceCallingPermissionForLog(this.mContext, Binder.getCallingPid(), Binder.getCallingUid());
        EventQueue eventQueue = this.mEventQueue;
        if (eventQueue != null) {
            eventQueue.postMessage(1, knoxAnalyticsData);
        } else {
            Log.e(TAG, "mEventQueue is null!");
        }
    }

    public void log(com.samsung.android.knox.knoxanalyticsproxy.KnoxAnalyticsData knoxAnalyticsData) {
        log(KnoxAnalyticsData.convertToKnoxAnalyticsSDK(knoxAnalyticsData));
    }
}
