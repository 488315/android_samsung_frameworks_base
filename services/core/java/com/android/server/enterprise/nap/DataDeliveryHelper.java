package com.android.server.enterprise.nap;

import android.os.UserHandle;

import com.samsung.android.service.DeviceRootKeyService.DeviceRootKeyServiceManager;

import org.json.JSONObject;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class DataDeliveryHelper {
    public final String identifier;
    public final int operationUserId;
    public final NetworkAnalyticsConfigStore.NAPConfigProfile profile;
    public final NetworkAnalyticsService.NetworkAnalyticsServiceConnection serviceConnection;

    static {
        DeviceRootKeyServiceManager deviceRootKeyServiceManager =
                NetworkAnalyticsService.mDeviceRootKeyServiceManager;
    }

    public DataDeliveryHelper(
            NetworkAnalyticsConfigStore.NAPConfigProfile nAPConfigProfile,
            NetworkAnalyticsService.NetworkAnalyticsServiceConnection
                    networkAnalyticsServiceConnection,
            int i) {
        this.profile = nAPConfigProfile;
        this.serviceConnection = networkAnalyticsServiceConnection;
        this.operationUserId = i;
        this.identifier =
                NetworkAnalyticsService.getTransformedVendorName(i, nAPConfigProfile.profileName);
    }

    public static int blockDnsFlow(JSONObject jSONObject, int i, int i2) {
        try {
            if (!jSONObject.optString("dport", null).equals("53")) {
                return 1;
            }
            int userId =
                    UserHandle.getUserId(Integer.parseInt(jSONObject.optString("dnsuid", null)));
            return (i2 == 0 && i == 0 && userId != 0) ? 3 : userId != i ? 2 : 0;
        } catch (Exception unused) {
            return 1;
        }
    }
}
