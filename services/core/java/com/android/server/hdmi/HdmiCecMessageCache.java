package com.android.server.hdmi;

import android.util.FastImmutableArraySet;
import android.util.SparseArray;
import com.android.internal.util.FrameworkStatsLog;

/* loaded from: classes2.dex */
public final class HdmiCecMessageCache {
  public static final FastImmutableArraySet CACHEABLE_OPCODES =
      new FastImmutableArraySet(
          new Integer[] {71, 132, 135, Integer.valueOf(FrameworkStatsLog.f679x663f9746)});
  public final SparseArray mCache = new SparseArray();

  public HdmiCecMessage getMessage(int i, int i2) {
    SparseArray sparseArray = (SparseArray) this.mCache.get(i);
    if (sparseArray == null) {
      return null;
    }
    return (HdmiCecMessage) sparseArray.get(i2);
  }

  public void flushMessagesFrom(int i) {
    this.mCache.remove(i);
  }

  public void flushAll() {
    this.mCache.clear();
  }

  public void cacheMessage(HdmiCecMessage hdmiCecMessage) {
    int opcode = hdmiCecMessage.getOpcode();
    if (isCacheable(opcode)) {
      int source = hdmiCecMessage.getSource();
      SparseArray sparseArray = (SparseArray) this.mCache.get(source);
      if (sparseArray == null) {
        sparseArray = new SparseArray();
        this.mCache.put(source, sparseArray);
      }
      sparseArray.put(opcode, hdmiCecMessage);
    }
  }

  public final boolean isCacheable(int i) {
    return CACHEABLE_OPCODES.contains(Integer.valueOf(i));
  }
}
