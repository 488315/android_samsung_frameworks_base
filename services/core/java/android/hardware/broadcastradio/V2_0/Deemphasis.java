package android.hardware.broadcastradio.V2_0;

import java.util.ArrayList;

/* loaded from: classes.dex */
public abstract class Deemphasis {
  public static final String dumpBitfield(byte b) {
    byte b2;
    ArrayList arrayList = new ArrayList();
    if ((b & 1) == 1) {
      arrayList.add("D50");
      b2 = (byte) 1;
    } else {
      b2 = 0;
    }
    if ((b & 2) == 2) {
      arrayList.add("D75");
      b2 = (byte) (b2 | 2);
    }
    if (b != b2) {
      arrayList.add("0x" + Integer.toHexString(Byte.toUnsignedInt((byte) (b & (~b2)))));
    }
    return String.join(" | ", arrayList);
  }
}
