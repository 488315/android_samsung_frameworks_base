package com.samsung.android.knox.dar.ddar.securesession;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.Arrays;

/* loaded from: classes5.dex */
public class Wiper {
  public static void wipe(byte[] bytes) {
    if (bytes == null) {
      return;
    }
    Arrays.fill(bytes, (byte) 0);
  }

  public static void wipe(char[] chars) {
    if (chars == null) {
      return;
    }
    Arrays.fill(chars, (char) 0);
  }

  public static void wipe(ByteBuffer bb) {
    if (bb == null) {
      return;
    }
    wipe(bb.array());
  }

  public static void wipe(CharBuffer cb) {
    if (cb == null) {
      return;
    }
    wipe(cb.array());
  }
}
