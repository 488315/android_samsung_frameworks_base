package android.security;

/* loaded from: classes3.dex */
public class Scrypt {
    native byte[] nativeScrypt(byte[] bArr, byte[] bArr2, int i, int i2, int i3, int i4);

    public byte[] scrypt(byte[] bArr, byte[] bArr2, int i, int i2, int i3, int i4) {
        return nativeScrypt(bArr, bArr2, i, i2, i3, i4);
    }
}
