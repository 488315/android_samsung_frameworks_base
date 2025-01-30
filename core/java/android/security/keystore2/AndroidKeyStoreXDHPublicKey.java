package android.security.keystore2;

import android.security.KeyStoreSecurityLevel;
import android.security.keystore.KeyProperties;
import android.system.keystore2.KeyDescriptor;
import android.system.keystore2.KeyMetadata;
import com.samsung.android.graphics.spr.document.animator.SprAnimatorBase;
import java.math.BigInteger;
import java.security.interfaces.XECPublicKey;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.NamedParameterSpec;
import java.util.Arrays;

/* loaded from: classes3.dex */
public class AndroidKeyStoreXDHPublicKey extends AndroidKeyStorePublicKey implements XECPublicKey {
  private static final int X25519_KEY_SIZE_BYTES = 32;
  private static final byte[] X509_PREAMBLE = {
    SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT90,
    SprAnimatorBase.INTERPOLATOR_TYPE_SINEIN33,
    SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT90,
    5,
    6,
    3,
    SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT33,
    101,
    110,
    3,
    SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEINOUT,
    0
  };
  private static final byte[] X509_PREAMBLE_WITH_NULL = {
    SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT90,
    SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT50,
    SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT90,
    7,
    6,
    3,
    SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT33,
    101,
    110,
    5,
    0,
    3,
    SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEINOUT,
    0
  };
  private final byte[] mEncodedKey;
  private final int mPreambleLength;

  public AndroidKeyStoreXDHPublicKey(
      KeyDescriptor descriptor,
      KeyMetadata metadata,
      String algorithm,
      KeyStoreSecurityLevel iSecurityLevel,
      byte[] encodedKey) {
    super(descriptor, metadata, encodedKey, algorithm, iSecurityLevel);
    this.mEncodedKey = encodedKey;
    if (encodedKey == null) {
      throw new IllegalArgumentException("empty encoded key.");
    }
    int matchesPreamble =
        matchesPreamble(X509_PREAMBLE, encodedKey)
            | matchesPreamble(X509_PREAMBLE_WITH_NULL, encodedKey);
    this.mPreambleLength = matchesPreamble;
    if (matchesPreamble == 0) {
      throw new IllegalArgumentException("Key size is not correct size");
    }
  }

  private static int matchesPreamble(byte[] preamble, byte[] encoded) {
    if (encoded.length == preamble.length + 32
        && Arrays.compare(preamble, 0, preamble.length, encoded, 0, preamble.length) == 0) {
      return preamble.length;
    }
    return 0;
  }

  @Override // android.security.keystore2.AndroidKeyStorePublicKey
  AndroidKeyStorePrivateKey getPrivateKey() {
    return new AndroidKeyStoreXDHPrivateKey(
        getUserKeyDescriptor(),
        getKeyIdDescriptor().nspace,
        getAuthorizations(),
        KeyProperties.KEY_ALGORITHM_XDH,
        getSecurityLevel());
  }

  @Override // java.security.interfaces.XECPublicKey
  public BigInteger getU() {
    byte[] bArr = this.mEncodedKey;
    return new BigInteger(Arrays.copyOfRange(bArr, this.mPreambleLength, bArr.length));
  }

  @Override // android.security.keystore2.AndroidKeyStorePublicKey,
            // android.security.keystore2.AndroidKeyStoreKey, java.security.Key
  public byte[] getEncoded() {
    return (byte[]) this.mEncodedKey.clone();
  }

  @Override // android.security.keystore2.AndroidKeyStoreKey, java.security.Key
  public String getAlgorithm() {
    return KeyProperties.KEY_ALGORITHM_XDH;
  }

  @Override // android.security.keystore2.AndroidKeyStorePublicKey,
            // android.security.keystore2.AndroidKeyStoreKey, java.security.Key
  public String getFormat() {
    return "x.509";
  }

  @Override // java.security.interfaces.XECKey
  public AlgorithmParameterSpec getParams() {
    return NamedParameterSpec.X25519;
  }
}
