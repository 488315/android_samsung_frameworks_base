package com.samsung.android.knox.ucm.plugin.keystore;

import android.os.Bundle;
import java.security.KeyStore;

/* loaded from: classes4.dex */
public class KeyParameter implements KeyStore.ProtectionParameter {
    public boolean mIsManaged;
    public Bundle mOptions;
    public int mSourceUid;

    public KeyParameter(int i, boolean z, Bundle bundle) {
        this.mSourceUid = i;
        this.mIsManaged = z;
        this.mOptions = bundle;
    }

    public Bundle getOptions() {
        return this.mOptions;
    }

    public int getSourceUid() {
        return this.mSourceUid;
    }

    public boolean isManaged() {
        return this.mIsManaged;
    }
}
