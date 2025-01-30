package com.samsung.android.authnrservice.service;

import android.util.Base64;

/* loaded from: classes2.dex */
public abstract class Encoding$Base64 {
    public static String encode(byte[] bArr) {
        return Base64.encodeToString(bArr, 3);
    }
}
