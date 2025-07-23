package com.samsung.android.knox.ucm.plugin.keystore;

import android.os.Bundle;
import java.security.KeyStore;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class KeyStoreParameter implements KeyStore.ProtectionParameter {
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

    public Bundle getOptions() {
        return this.mOptions;
    }

    public int getOwnerUid() {
        return this.mOwnerUid;
    }

    public int getResourceId() {
        return this.mResourceId;
    }
}
