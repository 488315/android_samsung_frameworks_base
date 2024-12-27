package com.android.server.connectivity;

import android.security.LegacyVpnProfileStore;

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
