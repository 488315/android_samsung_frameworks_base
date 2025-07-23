package android.security.keystore2;

import android.security.KeyStoreSecurityLevel;
import android.system.keystore2.KeyDescriptor;
import android.system.keystore2.KeyMetadata;
import com.samsung.android.graphics.spr.document.animator.SprAnimatorBase;
import com.samsung.android.graphics.spr.document.attribute.SprAttributeBase;
import java.math.BigInteger;
import java.security.interfaces.EdECPublicKey;
import java.security.spec.EdECPoint;
import java.security.spec.NamedParameterSpec;
import java.util.Arrays;
import java.util.Objects;

/* loaded from: classes3.dex */
public class AndroidKeyStoreEdECPublicKey extends AndroidKeyStorePublicKey implements EdECPublicKey {
    private static final byte[] DER_KEY_PREFIX = {SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT90, SprAnimatorBase.INTERPOLATOR_TYPE_SINEIN33, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT90, 5, 6, 3, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT33, 101, SprAttributeBase.TYPE_SHADOW, 3, SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEINOUT, 0};
    private static final int ED25519_KEY_SIZE_BYTES = 32;
    private byte[] mEncodedKey;
    private EdECPoint mPoint;

    public AndroidKeyStoreEdECPublicKey(KeyDescriptor keyDescriptor, KeyMetadata keyMetadata, String str, KeyStoreSecurityLevel keyStoreSecurityLevel, byte[] bArr) {
        super(keyDescriptor, keyMetadata, bArr, str, keyStoreSecurityLevel);
        this.mEncodedKey = bArr;
        int matchesPreamble = matchesPreamble(DER_KEY_PREFIX, bArr);
        if (matchesPreamble == 0) {
            throw new IllegalArgumentException("Key size is not correct size");
        }
        this.mPoint = pointFromKeyByteArray(Arrays.copyOfRange(bArr, matchesPreamble, bArr.length));
    }

    @Override // android.security.keystore2.AndroidKeyStorePublicKey
    AndroidKeyStorePrivateKey getPrivateKey() {
        return new AndroidKeyStoreEdECPrivateKey(getUserKeyDescriptor(), getKeyIdDescriptor().nspace, getAuthorizations(), "EdDSA", getSecurityLevel());
    }

    @Override // java.security.interfaces.EdECKey
    public NamedParameterSpec getParams() {
        return NamedParameterSpec.ED25519;
    }

    @Override // java.security.interfaces.EdECPublicKey
    public EdECPoint getPoint() {
        return this.mPoint;
    }

    private static int matchesPreamble(byte[] bArr, byte[] bArr2) {
        if (bArr2.length == bArr.length + 32 && Arrays.compare(bArr, Arrays.copyOf(bArr2, bArr.length)) == 0) {
            return bArr.length;
        }
        return 0;
    }

    private static EdECPoint pointFromKeyByteArray(byte[] bArr) {
        Objects.requireNonNull(bArr);
        boolean z = (bArr[bArr.length - 1] & 128) != 0;
        int length = bArr.length - 1;
        bArr[length] = (byte) (bArr[length] & Byte.MAX_VALUE);
        reverse(bArr);
        return new EdECPoint(z, new BigInteger(1, bArr));
    }

    private static void reverse(byte[] bArr) {
        int i = 0;
        for (int length = bArr.length - 1; i < length; length--) {
            byte b = bArr[i];
            bArr[i] = bArr[length];
            bArr[length] = b;
            i++;
        }
    }

    @Override // android.security.keystore2.AndroidKeyStorePublicKey, android.security.keystore2.AndroidKeyStoreKey, java.security.Key
    public byte[] getEncoded() {
        return (byte[]) this.mEncodedKey.clone();
    }
}
