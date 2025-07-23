package com.android.internal.org.bouncycastle.crypto.engines;

import com.android.internal.org.bouncycastle.crypto.CryptoServicePurpose;

/* loaded from: classes5.dex */
class Utils {
    Utils() {
    }

    static CryptoServicePurpose getPurpose(boolean z) {
        return z ? CryptoServicePurpose.ENCRYPTION : CryptoServicePurpose.DECRYPTION;
    }
}
