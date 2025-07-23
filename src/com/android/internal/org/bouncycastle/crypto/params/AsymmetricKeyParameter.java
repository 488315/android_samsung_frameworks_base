package com.android.internal.org.bouncycastle.crypto.params;

import com.android.internal.org.bouncycastle.crypto.CipherParameters;

/* loaded from: classes5.dex */
public class AsymmetricKeyParameter implements CipherParameters {
    boolean privateKey;

    public AsymmetricKeyParameter(boolean z) {
        this.privateKey = z;
    }

    public boolean isPrivate() {
        return this.privateKey;
    }
}
