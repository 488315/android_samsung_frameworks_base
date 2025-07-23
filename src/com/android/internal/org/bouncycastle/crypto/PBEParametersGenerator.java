package com.android.internal.org.bouncycastle.crypto;

import com.android.internal.org.bouncycastle.util.Strings;

/* loaded from: classes5.dex */
public abstract class PBEParametersGenerator {
    protected int iterationCount;
    protected byte[] password;
    protected byte[] salt;

    public abstract CipherParameters generateDerivedMacParameters(int i);

    public abstract CipherParameters generateDerivedParameters(int i);

    public abstract CipherParameters generateDerivedParameters(int i, int i2);

    protected PBEParametersGenerator() {
    }

    public void init(byte[] bArr, byte[] bArr2, int i) {
        this.password = bArr;
        this.salt = bArr2;
        this.iterationCount = i;
    }

    public byte[] getPassword() {
        return this.password;
    }

    public byte[] getSalt() {
        return this.salt;
    }

    public int getIterationCount() {
        return this.iterationCount;
    }

    public static byte[] PKCS5PasswordToBytes(char[] cArr) {
        if (cArr != null) {
            int length = cArr.length;
            byte[] bArr = new byte[length];
            for (int i = 0; i != length; i++) {
                bArr[i] = (byte) cArr[i];
            }
            return bArr;
        }
        return new byte[0];
    }

    public static byte[] PKCS5PasswordToUTF8Bytes(char[] cArr) {
        if (cArr != null) {
            return Strings.toUTF8ByteArray(cArr);
        }
        return new byte[0];
    }

    public static byte[] PKCS12PasswordToBytes(char[] cArr) {
        if (cArr != null && cArr.length > 0) {
            byte[] bArr = new byte[(cArr.length + 1) * 2];
            for (int i = 0; i != cArr.length; i++) {
                int i2 = i * 2;
                char c = cArr[i];
                bArr[i2] = (byte) (c >>> '\b');
                bArr[i2 + 1] = (byte) c;
            }
            return bArr;
        }
        return new byte[0];
    }
}
