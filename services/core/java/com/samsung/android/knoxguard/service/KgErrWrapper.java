package com.samsung.android.knoxguard.service;

/* loaded from: classes2.dex */
public class KgErrWrapper {
    public byte[] data;
    public int err;
    public int result;

    public String getStr() {
        byte[] bArr = this.data;
        if (bArr != null) {
            return new String(bArr);
        }
        return null;
    }
}
