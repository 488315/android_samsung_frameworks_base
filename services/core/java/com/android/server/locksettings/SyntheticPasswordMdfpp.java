package com.android.server.locksettings;

import android.util.SparseArray;

/* loaded from: classes2.dex */
public abstract class SyntheticPasswordMdfpp {
  public static final SparseArray mSecureModeCache = new SparseArray();
  public static final SparseArray mCredentialTypeCache = new SparseArray();

  public class KeyingMaterial {
    public static final KeyingMaterial NULL = new KeyingMaterial(null, -1);
    public byte[] pivot;
    public boolean pivotSafe = false;
    public int secureMode;

    public KeyingMaterial(byte[] bArr, int i) {
      this.pivot = bArr;
      this.secureMode = i;
    }

    public static KeyingMaterial getNull() {
      return NULL;
    }
  }

  public class EmptySlotException extends SecurityException {
    private static final long serialVersionUID = 1;

    public EmptySlotException(String str) {
      super(str);
    }
  }

  public static int getSecureMode(int i) {
    int intValue;
    SparseArray sparseArray = mSecureModeCache;
    synchronized (sparseArray) {
      Integer num = (Integer) sparseArray.get(i);
      if (num == null) {
        throw new EmptySlotException("Empty secure mode for user " + i);
      }
      intValue = num.intValue();
    }
    return intValue;
  }

  public static void cacheSecureMode(int i, int i2) {
    SparseArray sparseArray = mSecureModeCache;
    synchronized (sparseArray) {
      sparseArray.put(i, Integer.valueOf(i2));
    }
  }

  public static byte[] deriveResetTokenForDualDAR(byte[] bArr) {
    return (bArr == null || bArr.length == 0)
        ? bArr
        : doSp800Derivation(
            bArr, "KeyEncryptionKey".getBytes(), "ForResetPasswordToken".getBytes());
  }

  public static byte[] doSp800Derivation(byte[] bArr, byte[] bArr2, byte[] bArr3) {
    return new SP800Derive(bArr).withContext(bArr2, bArr3);
  }
}
