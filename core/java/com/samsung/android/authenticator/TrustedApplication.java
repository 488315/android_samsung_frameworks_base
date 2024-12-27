package com.samsung.android.authenticator;

/* loaded from: classes5.dex */
interface TrustedApplication {
    byte[] execute(byte[] bArr);

    int getHandle();

    int load();

    int unload();
}
