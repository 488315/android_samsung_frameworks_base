package android.security.keystore2;

import android.security.KeyStoreException;

/* loaded from: classes3.dex */
interface KeyStoreCryptoOperationStreamer {
  byte[] doFinal(byte[] bArr, int i, int i2, byte[] bArr2) throws KeyStoreException;

  long getConsumedInputSizeBytes();

  long getProducedOutputSizeBytes();

  byte[] update(byte[] bArr, int i, int i2) throws KeyStoreException;
}
