package com.android.server.knox.dar.sdp.security;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* loaded from: classes2.dex */
public class BytesUtil {
  public static final ByteOrder DEFAULT_BYTE_ORDER = ByteOrder.BIG_ENDIAN;

  public static byte[] longToBytes(long j) {
    ByteBuffer allocate = ByteBuffer.allocate(8);
    allocate.order(DEFAULT_BYTE_ORDER);
    allocate.putLong(j);
    return allocate.array();
  }

  public static long bytesToLong(byte[] bArr) {
    ByteBuffer allocate = ByteBuffer.allocate(8);
    allocate.order(DEFAULT_BYTE_ORDER);
    allocate.put(bArr);
    allocate.flip();
    return allocate.getLong();
  }
}
