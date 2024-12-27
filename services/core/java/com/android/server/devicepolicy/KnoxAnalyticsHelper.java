package com.android.server.devicepolicy;

import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;

import com.samsung.android.knox.analytics.KnoxAnalytics;
import com.samsung.android.knox.analytics.KnoxAnalyticsData;
import com.samsung.android.knox.analytics.activation.DevicePolicyListener;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class KnoxAnalyticsHelper {
    public final Context mContext;

    public KnoxAnalyticsHelper(Context context) {
        this.mContext = context;
    }

    public static void setKnoxAnalyticsData(String str, String str2) {
        KnoxAnalyticsData knoxAnalyticsData =
                new KnoxAnalyticsData("KNOX_AKS", 1, "API:DPM-".concat(str));
        knoxAnalyticsData.setProperty("pN", str2);
        KnoxAnalytics.log(knoxAnalyticsData);
    }

    public final void sendOwnerChangedBroadcastWithExtra(int i, String str, boolean z) {
        Intent addFlags =
                new Intent(DevicePolicyListener.ACTION_DEVICE_OWNER_CHANGED).addFlags(16777216);
        addFlags.putExtra(DevicePolicyListener.EXTRA_DO_PO_PACKAGE_NAME, str);
        addFlags.putExtra(DevicePolicyListener.EXTRA_DO_CHANGED_STATUS, z);
        this.mContext.sendBroadcastAsUser(addFlags, UserHandle.of(i));
    }
}
