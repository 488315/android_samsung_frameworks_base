package com.samsung.android.knox.ucm.plugin.keystore;

import android.os.Bundle;
import java.security.KeyStore;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class KeyStoreParameter implements KeyStore.ProtectionParameter {
    public static int PRIVATE_RESOURCE = 0;
    public static int SHARED_KEYCHAIN_RESOURCE = 1;
    public static int SHARED_WIFI_RESOURCE = 2;
    public static int UID_SELF = -1;
    public final Bundle mOptions;
    public final int mOwnerUid;
    public final int mResourceId;

    public KeyStoreParameter(int i, int i2, Bundle bundle) {
        this.mResourceId = i2;
        this.mOwnerUid = i;
        this.mOptions = bundle;
    }

    public final Bundle getOptions() {
        return this.mOptions;
    }

    public final int getOwnerUid() {
        return this.mOwnerUid;
    }

    public final int getResourceId() {
        return this.mResourceId;
    }
}
