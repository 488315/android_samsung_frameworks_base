package com.samsung.android.desktopsystemui.sharedlib.system;

import android.app.AppGlobals;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class TelephoneManagerWrapper {
    private static final String TAG = "[DSU]TelephoneManagerWrapper";
    private static final TelephoneManagerWrapper sInstance = new TelephoneManagerWrapper();
    private static final TelephonyManager mTelephonyManager = (TelephonyManager) AppGlobals.getInitialApplication().getSystemService("phone");
    private static final SubscriptionManager mSubscriptionManager = (SubscriptionManager) AppGlobals.getInitialApplication().getSystemService(SubscriptionManager.class);

    private TelephoneManagerWrapper() {
        List<SubscriptionInfo> list;
        int defaultDataSubscriptionId = SubscriptionManager.isValidSubscriptionId(-1) ? -1 : SubscriptionManager.getDefaultDataSubscriptionId();
        if (!SubscriptionManager.isValidSubscriptionId(defaultDataSubscriptionId)) {
            try {
                list = mSubscriptionManager.getActiveSubscriptionInfoList();
            } catch (Exception e) {
                Log.w(TAG, "Failed to getActiveSubscriptionInfoList: subscriptionId=" + defaultDataSubscriptionId, e);
                e.printStackTrace();
                list = null;
            }
            if (list != null && !list.isEmpty()) {
                defaultDataSubscriptionId = list.get(0).getSubscriptionId();
            }
        }
        if (SubscriptionManager.isValidSubscriptionId(defaultDataSubscriptionId)) {
            try {
                mTelephonyManager.createForSubscriptionId(defaultDataSubscriptionId);
            } catch (Exception e2) {
                Log.w(TAG, "Failed to createForSubscriptionId: subscriptionId=" + defaultDataSubscriptionId, e2);
                e2.printStackTrace();
            }
        }
    }

    public static TelephoneManagerWrapper getInstance() {
        return sInstance;
    }

    public int getPhoneCount() {
        return mTelephonyManager.getPhoneCount();
    }

    public boolean in_ecm_mode() {
        return mTelephonyManager.semIsInEmergencyCallbackMode();
    }

    public void setDataEnabled(boolean z) {
        mTelephonyManager.setDataEnabled(z);
    }
}
