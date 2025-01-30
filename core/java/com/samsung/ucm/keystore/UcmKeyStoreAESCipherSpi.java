package com.samsung.ucm.keystore;

import android.security.keystore.KeyProperties;
import java.security.Key;
import javax.crypto.IllegalBlockSizeException;

/* loaded from: classes6.dex */
public class UcmKeyStoreAESCipherSpi extends UcmKeyStoreGenericCipher {
  private byte[] mBuffer;

  public UcmKeyStoreAESCipherSpi(int padding) {
    super(padding);
    this.mBuffer = null;
  }

  @Override // com.samsung.ucm.keystore.UcmKeyStoreGenericCipher
  public boolean isModeSupported(String mode) {
    return KeyProperties.BLOCK_MODE_CBC.equals(mode) || "GCM".equals(mode);
  }

  /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
  @Override // com.samsung.ucm.keystore.UcmKeyStoreGenericCipher
  public int isPaddingSupported(String padding) {
    boolean z;
    switch (padding.hashCode()) {
      case -235307549:
        if (padding.equals("ISO9797_M2")) {
          z = false;
          break;
        }
        z = -1;
        break;
      case 907178768:
        if (padding.equals("NOPADDING")) {
          z = true;
          break;
        }
        z = -1;
        break;
      default:
        z = -1;
        break;
    }
    switch (z) {
      case false:
        return 4;
      case true:
        return 1;
      default:
        return -1;
    }
  }

  @Override // com.samsung.ucm.keystore.UcmKeyStoreGenericCipher
  public UcmKeyStoreKey initInternal(Key key) {
    this.mBuffer = new byte[0];
    return (UcmKeyStoreSecretKey) key;
  }

  @Override // com.samsung.ucm.keystore.UcmKeyStoreGenericCipher
  public void update(byte[] input, int inputOffset, int inputLen) {
    byte[] bArr = this.mBuffer;
    byte[] auxBuffer = new byte[bArr.length];
    System.arraycopy(bArr, 0, auxBuffer, 0, bArr.length);
    byte[] bArr2 = new byte[this.mBuffer.length + inputLen];
    this.mBuffer = bArr2;
    System.arraycopy(auxBuffer, 0, bArr2, 0, auxBuffer.length);
    System.arraycopy(input, inputOffset, this.mBuffer, auxBuffer.length, inputLen);
  }

  @Override // com.samsung.ucm.keystore.UcmKeyStoreGenericCipher
  public byte[] doFinalInternal(int padding) throws IllegalBlockSizeException {
    return this.mBuffer;
  }

  @Override // com.samsung.ucm.keystore.UcmKeyStoreGenericCipher
  public int getModulusSize() {
    return 0;
  }
}
