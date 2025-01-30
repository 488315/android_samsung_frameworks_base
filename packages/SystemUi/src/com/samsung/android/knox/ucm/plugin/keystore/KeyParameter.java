package com.samsung.android.knox.ucm.plugin.keystore;

import android.os.Bundle;
import java.security.KeyStore;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class KeyParameter implements KeyStore.ProtectionParameter {
    public boolean mIsManaged;
    public Bundle mOptions;
    public int mSourceUid;

    public KeyParameter(int i, boolean z, Bundle bundle) {
        this.mSourceUid = i;
        this.mIsManaged = z;
        this.mOptions = bundle;
    }

    public final Bundle getOptions() {
        return this.mOptions;
    }

    public final int getSourceUid() {
        return this.mSourceUid;
    }

    public final boolean isManaged() {
        return this.mIsManaged;
    }
}
