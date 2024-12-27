package com.samsung.android.knox.analytics.activation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.HandlerThread;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;

import com.samsung.android.knox.analytics.util.B2CFeatures;
import com.samsung.android.knox.analytics.util.KnoxAnalyticsQueryResolver;
import com.samsung.android.knox.analytics.util.Log;

import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class B2CListener {
    public static final String PERMISSION_KNOX_ANALYTICS =
            "com.samsung.android.knox.permission.KNOX_ANALYTICS_INTERNAL";
    public static final String TAG = "[KnoxAnalytics] B2CListener";
    public final ActivationMonitor mActivationMonitor;
    public ActivationReceiver mActivationReceiver;
    public final Context mContext;
    public Handler mHandler;
    public PackageRemovedReceiver mPackageRemovedReceiver;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ActivationReceiver extends BroadcastReceiver {
        public ActivationReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            Log.d(B2CListener.TAG, "ActivationReceiver: onReceive() " + intent.getAction());
            if (intent.getAction().equals(B2CFeatures.ACTION_B2C_ACTIVATION)) {
                String stringExtra = intent.getStringExtra(B2CFeatures.EXTRA_B2C_PACKAGE_NAME);
                String stringExtra2 = intent.getStringExtra(B2CFeatures.EXTRA_B2C_FEATURE);
                boolean booleanExtra =
                        intent.getBooleanExtra(B2CFeatures.EXTRA_B2C_ACTIVATION_STATUS, false);
                B2CListener.this.mActivationMonitor.checkB2C(
                        stringExtra, stringExtra2, booleanExtra);
                if (booleanExtra) {
                    B2CListener b2CListener = B2CListener.this;
                    if (b2CListener.mPackageRemovedReceiver == null) {
                        b2CListener.mPackageRemovedReceiver =
                                b2CListener.new PackageRemovedReceiver();
                        IntentFilter intentFilter = new IntentFilter();
                        intentFilter.addAction("android.intent.action.PACKAGE_FULLY_REMOVED");
                        intentFilter.addDataScheme("package");
                        B2CListener b2CListener2 = B2CListener.this;
                        b2CListener2.mContext.registerReceiver(
                                b2CListener2.mPackageRemovedReceiver,
                                intentFilter,
                                null,
                                b2CListener2.mHandler);
                        return;
                    }
                }
                if (booleanExtra) {
                    return;
                }
                B2CListener.this.checkRemovedReceiver();
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PackageRemovedReceiver extends BroadcastReceiver {
        public PackageRemovedReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String str = B2CListener.TAG;
            Log.d(str, "PackageRemovedReceiver: onReceive() " + intent.getAction());
            if (intent.getAction().equals("android.intent.action.PACKAGE_FULLY_REMOVED")) {
                List b2CFeaturePackageList =
                        KnoxAnalyticsQueryResolver.getB2CFeaturePackageList(context);
                String schemeSpecificPart = intent.getData().getSchemeSpecificPart();
                if (b2CFeaturePackageList != null
                        && b2CFeaturePackageList.contains(schemeSpecificPart)) {
                    B2CListener.this.mActivationMonitor.checkB2C(schemeSpecificPart, null, false);
                    Log.d(str, "B2C application removed: " + schemeSpecificPart);
                }
                B2CListener.this.checkRemovedReceiver();
            }
        }
    }

    public B2CListener(ActivationMonitor activationMonitor, Context context) {
        this.mActivationMonitor = activationMonitor;
        this.mContext = context;
    }

    public final void checkRemovedReceiver() {
        if (this.mPackageRemovedReceiver != null) {
            List b2CFeaturePackageList =
                    KnoxAnalyticsQueryResolver.getB2CFeaturePackageList(this.mContext);
            if (b2CFeaturePackageList == null || b2CFeaturePackageList.isEmpty()) {
                this.mContext.unregisterReceiver(this.mPackageRemovedReceiver);
                this.mPackageRemovedReceiver = null;
            }
        }
    }

    public final void register() {
        IntentFilter m =
                BatteryService$$ExternalSyntheticOutline0.m(B2CFeatures.ACTION_B2C_ACTIVATION);
        this.mActivationReceiver = new ActivationReceiver();
        HandlerThread handlerThread = new HandlerThread("B2CListenerThread");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        this.mHandler = handler;
        this.mContext.registerReceiver(
                this.mActivationReceiver, m, PERMISSION_KNOX_ANALYTICS, handler, 2);
    }
}
