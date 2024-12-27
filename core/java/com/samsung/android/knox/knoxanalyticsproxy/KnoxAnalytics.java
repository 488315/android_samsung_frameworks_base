package com.samsung.android.knox.knoxanalyticsproxy;

import android.os.Binder;
import android.os.RemoteException;
import android.os.SemSystemProperties;
import android.os.ServiceManager;
import android.util.Log;

public class KnoxAnalytics {
    public static final String KNOXANALYTICS_PROXY_SERVICE = "knox_analytics_proxy";
    private static IKnoxAnalyticsProxy mService;
    private static String TAG = "[KnoxAnalyticsProxySDK] ";
    private static final boolean DEBUG = !SemSystemProperties.getBoolean("ro.product_ship", true);

    public static void log(KnoxAnalyticsData event) {
        if (DEBUG) {
            Log.d(TAG, event.toString());
        }
        if (!getService()) {
            if (DEBUG) {
                Log.d(TAG, "log(): service not running!");
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
                    Log.e(TAG, "log(): Remote Exception in log - ", e);
                }
            } catch (SecurityException e2) {
                if (DEBUG) {
                    Log.e(TAG, "log(): Security Exception in log - ", e2);
                }
            }
        } finally {
            Binder.restoreCallingIdentity(token);
        }
    }

    public static boolean getService() {
        if (mService == null) {
            mService =
                    IKnoxAnalyticsProxy.Stub.asInterface(
                            ServiceManager.getService(KNOXANALYTICS_PROXY_SERVICE));
        }
        return mService != null;
    }
}
