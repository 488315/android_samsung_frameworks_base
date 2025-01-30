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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DataUsageController {
    public NetworkControllerImpl.C26233 mCallback;
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
        int i = this.mSubscriptionId;
        if (!SubscriptionManager.isValidSubscriptionId(i)) {
            i = SubscriptionManager.getDefaultDataSubscriptionId();
        }
        boolean isValidSubscriptionId = SubscriptionManager.isValidSubscriptionId(i);
        Context context = this.mContext;
        if (!isValidSubscriptionId) {
            int[] activeSubscriptionIdList = SubscriptionManager.from(context).getActiveSubscriptionIdList();
            if (!ArrayUtils.isEmpty(activeSubscriptionIdList)) {
                i = activeSubscriptionIdList[0];
            }
        }
        return ((TelephonyManager) context.getSystemService(TelephonyManager.class)).createForSubscriptionId(i);
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
        NetworkControllerImpl.C26233 c26233 = this.mCallback;
        if (c26233 != null) {
            c26233.onMobileDataEnabled(z);
        }
    }
}
