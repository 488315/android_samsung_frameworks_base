package com.android.internal.org.bouncycastle.jcajce.spec;

import java.security.spec.EncodedKeySpec;

/* loaded from: classes5.dex */
public class OpenSSHPrivateKeySpec extends EncodedKeySpec {
    private final String format;

    public OpenSSHPrivateKeySpec(byte[] bArr) {
        super(bArr);
        byte b = bArr[0];
        if (b == 48) {
            this.format = "ASN.1";
        } else {
            if (b == 111) {
                this.format = "OpenSSH";
                return;
            }
            throw new IllegalArgumentException("unknown byte encoding");
        }
    }

    @Override // java.security.spec.EncodedKeySpec
    public String getFormat() {
        return this.format;
    }
}
