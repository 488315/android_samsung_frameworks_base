package com.android.internal.org.bouncycastle.crypto.engines;

import com.android.internal.midi.MidiConstants;
import com.android.internal.org.bouncycastle.crypto.BlockCipher;
import com.android.internal.org.bouncycastle.crypto.CipherParameters;
import com.android.internal.org.bouncycastle.crypto.DataLengthException;
import com.android.internal.org.bouncycastle.crypto.OutputLengthException;
import com.android.internal.org.bouncycastle.crypto.params.KeyParameter;
import com.android.internal.org.bouncycastle.crypto.params.RC2Parameters;
import com.samsung.android.graphics.spr.document.animator.SprAnimatorBase;
import com.samsung.android.graphics.spr.document.attribute.SprAttributeBase;

/* loaded from: classes5.dex */
public class RC2Engine implements BlockCipher {
  private static final int BLOCK_SIZE = 8;
  private static byte[] piTable = {
    -39,
    120,
    -7,
    -60,
    SprAnimatorBase.INTERPOLATOR_TYPE_EXPOEASEIN,
    -35,
    -75,
    -19,
    40,
    -23,
    -3,
    121,
    74,
    MidiConstants.STATUS_POLYPHONIC_AFTERTOUCH,
    -40,
    -99,
    -58,
    126,
    55,
    -125,
    SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT33,
    118,
    83,
    -114,
    98,
    76,
    100,
    -120,
    68,
    -117,
    -5,
    -94,
    SprAnimatorBase.INTERPOLATOR_TYPE_ELASTICEASEOUT,
    -102,
    89,
    -11,
    -121,
    -77,
    79,
    SprAnimatorBase.INTERPOLATOR_TYPE_CUBICEASEIN,
    SprAttributeBase.TYPE_ANIMATOR_SET,
    69,
    109,
    -115,
    9,
    -127,
    125,
    50,
    -67,
    -113,
    64,
    -21,
    -122,
    -73,
    123,
    11,
    -16,
    -107,
    SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEINOUT,
    SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEIN,
    92,
    107,
    78,
    -126,
    84,
    -42,
    101,
    -109,
    -50,
    SprAttributeBase.TYPE_DURATION,
    -78,
    SprAnimatorBase.INTERPOLATOR_TYPE_QUADEASEIN,
    115,
    86,
    MidiConstants.STATUS_PROGRAM_CHANGE,
    20,
    -89,
    -116,
    MidiConstants.STATUS_MIDI_TIME_CODE,
    -36,
    SprAnimatorBase.INTERPOLATOR_TYPE_CIRCEASEINOUT,
    117,
    -54,
    SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEIN,
    59,
    -66,
    -28,
    -47,
    66,
    61,
    -44,
    SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT90,
    -93,
    60,
    -74,
    38,
    111,
    -65,
    14,
    -38,
    70,
    105,
    7,
    87,
    SprAnimatorBase.INTERPOLATOR_TYPE_SINEEASEINOUT,
    MidiConstants.STATUS_SONG_POSITION,
    SprAnimatorBase.INTERPOLATOR_TYPE_QUADEASEOUT,
    -101,
    -68,
    -108,
    67,
    3,
    -8,
    17,
    -57,
    -10,
    MidiConstants.STATUS_NOTE_ON,
    -17,
    62,
    -25,
    6,
    -61,
    -43,
    SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT80,
    -56,
    102,
    SprAnimatorBase.INTERPOLATOR_TYPE_QUADEASEINOUT,
    -41,
    8,
    -24,
    -22,
    -34,
    Byte.MIN_VALUE,
    82,
    -18,
    -9,
    -124,
    -86,
    114,
    -84,
    53,
    77,
    106,
    SprAnimatorBase.INTERPOLATOR_TYPE_SINEIN33,
    -106,
    SprAnimatorBase.INTERPOLATOR_TYPE_EXPOEASEOUT,
    -46,
    113,
    90,
    SprAnimatorBase.INTERPOLATOR_TYPE_CUBICEASEINOUT,
    73,
    116,
    75,
    -97,
    MidiConstants.STATUS_CHANNEL_PRESSURE,
    94,
    4,
    SprAnimatorBase.INTERPOLATOR_TYPE_ELASTICEASEINOUT,
    -92,
    -20,
    -62,
    MidiConstants.STATUS_PITCH_BEND,
    65,
    110,
    15,
    81,
    -53,
    -52,
    SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT,
    -111,
    -81,
    80,
    -95,
    -12,
    SprAttributeBase.TYPE_SHADOW,
    57,
    -103,
    124,
    58,
    -123,
    35,
    -72,
    -76,
    122,
    -4,
    2,
    54,
    91,
    37,
    85,
    -105,
    SprAnimatorBase.INTERPOLATOR_TYPE_SINEOUT33,
    SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60,
    93,
    -6,
    -104,
    -29,
    -118,
    -110,
    -82,
    5,
    -33,
    41,
    16,
    103,
    108,
    -70,
    -55,
    -45,
    0,
    -26,
    -49,
    -31,
    -98,
    -88,
    SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT50,
    99,
    SprAnimatorBase.INTERPOLATOR_TYPE_ELASTICEASEIN,
    1,
    63,
    88,
    -30,
    -119,
    -87,
    13,
    56,
    52,
    27,
    -85,
    51,
    -1,
    MidiConstants.STATUS_CONTROL_CHANGE,
    -69,
    72,
    12,
    95,
    -71,
    -79,
    -51,
    SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT70,
    -59,
    MidiConstants.STATUS_SONG_SELECT,
    -37,
    71,
    -27,
    -91,
    -100,
    119,
    10,
    -90,
    32,
    104,
    -2,
    Byte.MAX_VALUE,
    -63,
    -83
  };
  private boolean encrypting;
  private int[] workingKey;

  private int[] generateWorkingKey(byte[] key, int bits) {
    int[] xKey = new int[128];
    for (int i = 0; i != key.length; i++) {
      xKey[i] = key[i] & 255;
    }
    int len = key.length;
    if (len < 128) {
      int len2 = 0;
      int x = xKey[len - 1];
      while (true) {
        byte[] bArr = piTable;
        int index = len2 + 1;
        int index2 = xKey[len2];
        x = bArr[(index2 + x) & 255] & 255;
        int len3 = len + 1;
        xKey[len] = x;
        len = len3;
        if (len3 >= 128) {
          break;
        }
        len2 = index;
      }
    }
    int len4 = (bits + 7) >> 3;
    int x2 = piTable[xKey[128 - len4] & (255 >> ((-bits) & 7))] & 255;
    xKey[128 - len4] = x2;
    for (int i2 = (128 - len4) - 1; i2 >= 0; i2--) {
      x2 = piTable[xKey[i2 + len4] ^ x2] & 255;
      xKey[i2] = x2;
    }
    int[] newKey = new int[64];
    for (int i3 = 0; i3 != newKey.length; i3++) {
      newKey[i3] = xKey[i3 * 2] + (xKey[(i3 * 2) + 1] << 8);
    }
    return newKey;
  }

  @Override // com.android.internal.org.bouncycastle.crypto.BlockCipher
  public void init(boolean encrypting, CipherParameters params) {
    this.encrypting = encrypting;
    if (params instanceof RC2Parameters) {
      RC2Parameters param = (RC2Parameters) params;
      this.workingKey = generateWorkingKey(param.getKey(), param.getEffectiveKeyBits());
    } else {
      if (params instanceof KeyParameter) {
        byte[] key = ((KeyParameter) params).getKey();
        this.workingKey = generateWorkingKey(key, key.length * 8);
        return;
      }
      throw new IllegalArgumentException(
          "invalid parameter passed to RC2 init - " + params.getClass().getName());
    }
  }

  @Override // com.android.internal.org.bouncycastle.crypto.BlockCipher
  public void reset() {}

  @Override // com.android.internal.org.bouncycastle.crypto.BlockCipher
  public String getAlgorithmName() {
    return "RC2";
  }

  @Override // com.android.internal.org.bouncycastle.crypto.BlockCipher
  public int getBlockSize() {
    return 8;
  }

  @Override // com.android.internal.org.bouncycastle.crypto.BlockCipher
  public final int processBlock(byte[] in, int inOff, byte[] out, int outOff) {
    if (this.workingKey == null) {
      throw new IllegalStateException("RC2 engine not initialised");
    }
    if (inOff + 8 > in.length) {
      throw new DataLengthException("input buffer too short");
    }
    if (outOff + 8 > out.length) {
      throw new OutputLengthException("output buffer too short");
    }
    if (this.encrypting) {
      encryptBlock(in, inOff, out, outOff);
      return 8;
    }
    decryptBlock(in, inOff, out, outOff);
    return 8;
  }

  private int rotateWordLeft(int x, int y) {
    int x2 = x & 65535;
    return (x2 << y) | (x2 >> (16 - y));
  }

  private void encryptBlock(byte[] in, int inOff, byte[] out, int outOff) {
    int x76 = ((in[inOff + 7] & 255) << 8) + (in[inOff + 6] & 255);
    int x54 = ((in[inOff + 5] & 255) << 8) + (in[inOff + 4] & 255);
    int x32 = ((in[inOff + 3] & 255) << 8) + (in[inOff + 2] & 255);
    int x10 = ((in[inOff + 1] & 255) << 8) + (in[inOff + 0] & 255);
    for (int i = 0; i <= 16; i += 4) {
      x10 = rotateWordLeft(((~x76) & x32) + x10 + (x54 & x76) + this.workingKey[i], 1);
      x32 = rotateWordLeft(((~x10) & x54) + x32 + (x76 & x10) + this.workingKey[i + 1], 2);
      x54 = rotateWordLeft(((~x32) & x76) + x54 + (x10 & x32) + this.workingKey[i + 2], 3);
      x76 = rotateWordLeft(((~x54) & x10) + x76 + (x32 & x54) + this.workingKey[i + 3], 5);
    }
    int[] iArr = this.workingKey;
    int x102 = x10 + iArr[x76 & 63];
    int x322 = x32 + iArr[x102 & 63];
    int x542 = x54 + iArr[x322 & 63];
    int x762 = x76 + iArr[x542 & 63];
    for (int i2 = 20; i2 <= 40; i2 += 4) {
      x102 = rotateWordLeft(((~x762) & x322) + x102 + (x542 & x762) + this.workingKey[i2], 1);
      x322 = rotateWordLeft(((~x102) & x542) + x322 + (x762 & x102) + this.workingKey[i2 + 1], 2);
      x542 = rotateWordLeft(((~x322) & x762) + x542 + (x102 & x322) + this.workingKey[i2 + 2], 3);
      x762 = rotateWordLeft(((~x542) & x102) + x762 + (x322 & x542) + this.workingKey[i2 + 3], 5);
    }
    int[] iArr2 = this.workingKey;
    int x103 = x102 + iArr2[x762 & 63];
    int x323 = x322 + iArr2[x103 & 63];
    int x543 = x542 + iArr2[x323 & 63];
    int x763 = x762 + iArr2[x543 & 63];
    for (int i3 = 44; i3 < 64; i3 += 4) {
      x103 = rotateWordLeft(((~x763) & x323) + x103 + (x543 & x763) + this.workingKey[i3], 1);
      x323 = rotateWordLeft(((~x103) & x543) + x323 + (x763 & x103) + this.workingKey[i3 + 1], 2);
      x543 = rotateWordLeft(((~x323) & x763) + x543 + (x103 & x323) + this.workingKey[i3 + 2], 3);
      x763 = rotateWordLeft(((~x543) & x103) + x763 + (x323 & x543) + this.workingKey[i3 + 3], 5);
    }
    int i4 = outOff + 0;
    out[i4] = (byte) x103;
    out[outOff + 1] = (byte) (x103 >> 8);
    out[outOff + 2] = (byte) x323;
    out[outOff + 3] = (byte) (x323 >> 8);
    out[outOff + 4] = (byte) x543;
    out[outOff + 5] = (byte) (x543 >> 8);
    out[outOff + 6] = (byte) x763;
    out[outOff + 7] = (byte) (x763 >> 8);
  }

  private void decryptBlock(byte[] in, int inOff, byte[] out, int outOff) {
    int x76 = ((in[inOff + 7] & 255) << 8) + (in[inOff + 6] & 255);
    int x54 = ((in[inOff + 5] & 255) << 8) + (in[inOff + 4] & 255);
    int x32 = ((in[inOff + 3] & 255) << 8) + (in[inOff + 2] & 255);
    int x10 = ((in[inOff + 1] & 255) << 8) + (in[inOff + 0] & 255);
    for (int i = 60; i >= 44; i -= 4) {
      x76 = rotateWordLeft(x76, 11) - ((((~x54) & x10) + (x32 & x54)) + this.workingKey[i + 3]);
      x54 = rotateWordLeft(x54, 13) - ((((~x32) & x76) + (x10 & x32)) + this.workingKey[i + 2]);
      x32 = rotateWordLeft(x32, 14) - ((((~x10) & x54) + (x76 & x10)) + this.workingKey[i + 1]);
      x10 = rotateWordLeft(x10, 15) - ((((~x76) & x32) + (x54 & x76)) + this.workingKey[i]);
    }
    int[] iArr = this.workingKey;
    int x762 = x76 - iArr[x54 & 63];
    int x542 = x54 - iArr[x32 & 63];
    int x322 = x32 - iArr[x10 & 63];
    int x102 = x10 - iArr[x762 & 63];
    for (int i2 = 40; i2 >= 20; i2 -= 4) {
      x762 =
          rotateWordLeft(x762, 11) - ((((~x542) & x102) + (x322 & x542)) + this.workingKey[i2 + 3]);
      x542 =
          rotateWordLeft(x542, 13) - ((((~x322) & x762) + (x102 & x322)) + this.workingKey[i2 + 2]);
      x322 =
          rotateWordLeft(x322, 14) - ((((~x102) & x542) + (x762 & x102)) + this.workingKey[i2 + 1]);
      x102 = rotateWordLeft(x102, 15) - ((((~x762) & x322) + (x542 & x762)) + this.workingKey[i2]);
    }
    int[] iArr2 = this.workingKey;
    int x763 = x762 - iArr2[x542 & 63];
    int x543 = x542 - iArr2[x322 & 63];
    int x323 = x322 - iArr2[x102 & 63];
    int x103 = x102 - iArr2[x763 & 63];
    for (int i3 = 16; i3 >= 0; i3 -= 4) {
      x763 =
          rotateWordLeft(x763, 11) - ((((~x543) & x103) + (x323 & x543)) + this.workingKey[i3 + 3]);
      x543 =
          rotateWordLeft(x543, 13) - ((((~x323) & x763) + (x103 & x323)) + this.workingKey[i3 + 2]);
      x323 =
          rotateWordLeft(x323, 14) - ((((~x103) & x543) + (x763 & x103)) + this.workingKey[i3 + 1]);
      x103 = rotateWordLeft(x103, 15) - ((((~x763) & x323) + (x543 & x763)) + this.workingKey[i3]);
    }
    int i4 = outOff + 0;
    out[i4] = (byte) x103;
    out[outOff + 1] = (byte) (x103 >> 8);
    out[outOff + 2] = (byte) x323;
    out[outOff + 3] = (byte) (x323 >> 8);
    out[outOff + 4] = (byte) x543;
    out[outOff + 5] = (byte) (x543 >> 8);
    out[outOff + 6] = (byte) x763;
    out[outOff + 7] = (byte) (x763 >> 8);
  }
}
