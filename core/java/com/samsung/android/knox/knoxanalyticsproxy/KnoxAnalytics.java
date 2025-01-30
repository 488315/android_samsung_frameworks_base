package com.samsung.android.knox.knoxanalyticsproxy;

import android.p009os.Binder;
import android.p009os.RemoteException;
import android.p009os.SemSystemProperties;
import android.p009os.ServiceManager;
import android.util.Log;
import com.samsung.android.knox.knoxanalyticsproxy.IKnoxAnalyticsProxy;

/* loaded from: classes5.dex */
public class KnoxAnalytics {
    public static final String KNOXANALYTICS_PROXY_SERVICE = "knox_analytics_proxy";
    private static IKnoxAnalyticsProxy mService;
    private static String TAG = "[KnoxAnalyticsProxySDK] ";
    private static final boolean DEBUG = !SemSystemProperties.getBoolean("ro.product_ship", true);

    public static void log(KnoxAnalyticsData event) {
        boolean z = DEBUG;
        if (z) {
            Log.m94d(TAG, event.toString());
        }
        if (!getService()) {
            if (z) {
                Log.m94d(TAG, "log(): service not running!");
                return;
            }
            return;
        }
        long token = Binder.clearCallingIdentity();
        try {
            try {
                mService.log(event);
            } catch (RemoteException e) {
                if (DEBUG) {
                    Log.m97e(TAG, "log(): Remote Exception in log - ", e);
                }
            } catch (SecurityException e2) {
                if (DEBUG) {
                    Log.m97e(TAG, "log(): Security Exception in log - ", e2);
                }
            }
        } finally {
            Binder.restoreCallingIdentity(token);
        }
    }

    public static boolean getService() {
        if (mService == null) {
            mService = IKnoxAnalyticsProxy.Stub.asInterface(ServiceManager.getService(KNOXANALYTICS_PROXY_SERVICE));
        }
        return mService != null;
    }
}
