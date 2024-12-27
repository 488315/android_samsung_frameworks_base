package com.android.server.connectivity;

import android.security.LegacyVpnProfileStore;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class VpnProfileStore {
    public byte[] get(String str) {
        return LegacyVpnProfileStore.get(str);
    }

    public String[] list(String str) {
        return LegacyVpnProfileStore.list(str);
    }

    public boolean put(String str, byte[] bArr) {
        return LegacyVpnProfileStore.put(str, bArr);
    }

    public boolean remove(String str) {
        return LegacyVpnProfileStore.remove(str);
    }
}
