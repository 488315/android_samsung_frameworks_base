package com.android.settingslib.net;

import android.content.Context;
import android.net.NetworkPolicyManager;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.android.internal.util.ArrayUtils;
import com.android.systemui.statusbar.connectivity.NetworkControllerImpl;
import java.util.Formatter;
import java.util.Locale;

/* loaded from: classes.dex */
public class DataUsageController {
    public NetworkControllerImpl.AnonymousClass3 mCallback;
    public final Context mContext;
    public final int mSubscriptionId;

    static {
        new Formatter(new StringBuilder(50), Locale.getDefault());
    }

    public DataUsageController(Context context) {
        this.mContext = context;
        NetworkPolicyManager.from(context);
        this.mSubscriptionId = -1;
    }

    public TelephonyManager getTelephonyManager() {
        int defaultDataSubscriptionId = this.mSubscriptionId;
        if (!SubscriptionManager.isValidSubscriptionId(defaultDataSubscriptionId)) {
            defaultDataSubscriptionId = SubscriptionManager.getDefaultDataSubscriptionId();
        }
        if (!SubscriptionManager.isValidSubscriptionId(defaultDataSubscriptionId)) {
            int[] activeSubscriptionIdList = SubscriptionManager.from(this.mContext).getActiveSubscriptionIdList();
            if (!ArrayUtils.isEmpty(activeSubscriptionIdList)) {
                defaultDataSubscriptionId = activeSubscriptionIdList[0];
            }
        }
        return ((TelephonyManager) this.mContext.getSystemService(TelephonyManager.class)).createForSubscriptionId(defaultDataSubscriptionId);
    }

    public final boolean isMobileDataEnabled() {
        return getTelephonyManager().isDataEnabled();
    }

    public final boolean isMobileDataSupported() {
        return getTelephonyManager().isDataCapable() && getTelephonyManager().getSimState() == 5;
    }

    public final void setMobileDataEnabled(boolean z) {
        Log.d("DataUsageController", "setMobileDataEnabled: enabled=" + z);
        getTelephonyManager().setDataEnabled(z);
        NetworkControllerImpl.AnonymousClass3 anonymousClass3 = this.mCallback;
        if (anonymousClass3 != null) {
            anonymousClass3.onMobileDataEnabled(z);
        }
    }
}
