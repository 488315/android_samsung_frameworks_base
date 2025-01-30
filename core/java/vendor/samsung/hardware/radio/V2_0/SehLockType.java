package vendor.samsung.hardware.radio.V2_0;

import android.security.keystore.KeyProperties;
import java.util.ArrayList;

/* loaded from: classes6.dex */
public final class SehLockType {
  public static final int ACL = 11;

  /* renamed from: FD */
  public static final int f3121FD = 4;
  public static final int NONE = 0;
  public static final int NO_SIM = 16;
  public static final int ONE = 13;

  /* renamed from: PC */
  public static final int f3122PC = 8;

  /* renamed from: PF */
  public static final int f3123PF = 2;

  /* renamed from: PN */
  public static final int f3124PN = 5;
  public static final int POLICY = 15;

  /* renamed from: PP */
  public static final int f3125PP = 7;

  /* renamed from: PS */
  public static final int f3126PS = 1;

  /* renamed from: PU */
  public static final int f3127PU = 6;
  public static final int PUK2 = 10;
  public static final int REG = 12;

  /* renamed from: SC */
  public static final int f3128SC = 3;
  public static final int SC2 = 9;
  public static final int SEP = 14;
  public static final int UNAVAIL = 17;
  public static final int UNSPECIFIED = -1;

  public static final String toString(int o) {
    if (o == -1) {
      return "UNSPECIFIED";
    }
    if (o == 0) {
      return KeyProperties.DIGEST_NONE;
    }
    if (o == 1) {
      return "PS";
    }
    if (o == 2) {
      return "PF";
    }
    if (o == 3) {
      return "SC";
    }
    if (o == 4) {
      return "FD";
    }
    if (o == 5) {
      return "PN";
    }
    if (o == 6) {
      return "PU";
    }
    if (o == 7) {
      return "PP";
    }
    if (o == 8) {
      return "PC";
    }
    if (o == 9) {
      return "SC2";
    }
    if (o == 10) {
      return "PUK2";
    }
    if (o == 11) {
      return "ACL";
    }
    if (o == 12) {
      return "REG";
    }
    if (o == 13) {
      return "ONE";
    }
    if (o == 14) {
      return "SEP";
    }
    if (o == 15) {
      return "POLICY";
    }
    if (o == 16) {
      return "NO_SIM";
    }
    if (o == 17) {
      return "UNAVAIL";
    }
    return "0x" + Integer.toHexString(o);
  }

  public static final String dumpBitfield(int o) {
    ArrayList<String> list = new ArrayList<>();
    int flipped = 0;
    if ((o & (-1)) == -1) {
      list.add("UNSPECIFIED");
      flipped = 0 | (-1);
    }
    list.add(KeyProperties.DIGEST_NONE);
    if ((o & 1) == 1) {
      list.add("PS");
      flipped |= 1;
    }
    if ((o & 2) == 2) {
      list.add("PF");
      flipped |= 2;
    }
    if ((o & 3) == 3) {
      list.add("SC");
      flipped |= 3;
    }
    if ((o & 4) == 4) {
      list.add("FD");
      flipped |= 4;
    }
    if ((o & 5) == 5) {
      list.add("PN");
      flipped |= 5;
    }
    if ((o & 6) == 6) {
      list.add("PU");
      flipped |= 6;
    }
    if ((o & 7) == 7) {
      list.add("PP");
      flipped |= 7;
    }
    if ((o & 8) == 8) {
      list.add("PC");
      flipped |= 8;
    }
    if ((o & 9) == 9) {
      list.add("SC2");
      flipped |= 9;
    }
    if ((o & 10) == 10) {
      list.add("PUK2");
      flipped |= 10;
    }
    if ((o & 11) == 11) {
      list.add("ACL");
      flipped |= 11;
    }
    if ((o & 12) == 12) {
      list.add("REG");
      flipped |= 12;
    }
    if ((o & 13) == 13) {
      list.add("ONE");
      flipped |= 13;
    }
    if ((o & 14) == 14) {
      list.add("SEP");
      flipped |= 14;
    }
    if ((o & 15) == 15) {
      list.add("POLICY");
      flipped |= 15;
    }
    if ((o & 16) == 16) {
      list.add("NO_SIM");
      flipped |= 16;
    }
    if ((o & 17) == 17) {
      list.add("UNAVAIL");
      flipped |= 17;
    }
    if (o != flipped) {
      list.add("0x" + Integer.toHexString((~flipped) & o));
    }
    return String.join(" | ", list);
  }
}
