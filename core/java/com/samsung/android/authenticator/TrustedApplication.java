package com.samsung.android.authenticator;

interface TrustedApplication {
    byte[] execute(byte[] bArr);

    int getHandle();

    int load();

    int unload();
}
