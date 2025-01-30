package vendor.samsung.hardware.radio.V2_0;

import java.util.ArrayList;

/* loaded from: classes6.dex */
public final class SehSimSwapState {
  public static final int SIM_SWAP_ADDED = 1;
  public static final int SIM_SWAP_REMOVED = 0;
  public static final int SIM_TRAY_ADDED = 3;
  public static final int SIM_TRAY_REMOVED = 2;

  public static final String toString(int o) {
    if (o == 0) {
      return "SIM_SWAP_REMOVED";
    }
    if (o == 1) {
      return "SIM_SWAP_ADDED";
    }
    if (o == 2) {
      return "SIM_TRAY_REMOVED";
    }
    if (o == 3) {
      return "SIM_TRAY_ADDED";
    }
    return "0x" + Integer.toHexString(o);
  }

  public static final String dumpBitfield(int o) {
    ArrayList<String> list = new ArrayList<>();
    int flipped = 0;
    list.add("SIM_SWAP_REMOVED");
    if ((o & 1) == 1) {
      list.add("SIM_SWAP_ADDED");
      flipped = 0 | 1;
    }
    if ((o & 2) == 2) {
      list.add("SIM_TRAY_REMOVED");
      flipped |= 2;
    }
    if ((o & 3) == 3) {
      list.add("SIM_TRAY_ADDED");
      flipped |= 3;
    }
    if (o != flipped) {
      list.add("0x" + Integer.toHexString((~flipped) & o));
    }
    return String.join(" | ", list);
  }
}
