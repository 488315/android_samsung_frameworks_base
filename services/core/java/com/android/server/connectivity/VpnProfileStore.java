package com.android.server.connectivity;

import android.security.LegacyVpnProfileStore;

/* loaded from: classes.dex */
public class VpnProfileStore {
    public boolean put(String str, byte[] bArr) {
        return LegacyVpnProfileStore.put(str, bArr);
    }

    public byte[] get(String str) {
        return LegacyVpnProfileStore.get(str);
    }

    public boolean remove(String str) {
        return LegacyVpnProfileStore.remove(str);
    }

    public String[] list(String str) {
        return LegacyVpnProfileStore.list(str);
    }
}
