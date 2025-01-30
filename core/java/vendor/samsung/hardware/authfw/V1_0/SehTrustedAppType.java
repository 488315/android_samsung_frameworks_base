package vendor.samsung.hardware.authfw.V1_0;

import java.util.ArrayList;

/* loaded from: classes6.dex */
public final class SehTrustedAppType {
  public static final int ASSET_PASS_AUTHENTICATOR = 10000;
  public static final int ASSET_PASS_ESE = 10001;
  public static final int PRELOAD_ASSET_DOWNLOADER = 3;
  public static final int PRELOAD_PASS_DEVICE_ROOT_KEY = 2;
  public static final int PRELOAD_PASS_FINGERPRINT = 1;

  public static final String toString(int o) {
    if (o == 1) {
      return "PRELOAD_PASS_FINGERPRINT";
    }
    if (o == 2) {
      return "PRELOAD_PASS_DEVICE_ROOT_KEY";
    }
    if (o == 3) {
      return "PRELOAD_ASSET_DOWNLOADER";
    }
    if (o == 10000) {
      return "ASSET_PASS_AUTHENTICATOR";
    }
    if (o == 10001) {
      return "ASSET_PASS_ESE";
    }
    return "0x" + Integer.toHexString(o);
  }

  public static final String dumpBitfield(int o) {
    ArrayList<String> list = new ArrayList<>();
    int flipped = 0;
    if ((o & 1) == 1) {
      list.add("PRELOAD_PASS_FINGERPRINT");
      flipped = 0 | 1;
    }
    if ((o & 2) == 2) {
      list.add("PRELOAD_PASS_DEVICE_ROOT_KEY");
      flipped |= 2;
    }
    if ((o & 3) == 3) {
      list.add("PRELOAD_ASSET_DOWNLOADER");
      flipped |= 3;
    }
    if ((o & 10000) == 10000) {
      list.add("ASSET_PASS_AUTHENTICATOR");
      flipped |= 10000;
    }
    if ((o & 10001) == 10001) {
      list.add("ASSET_PASS_ESE");
      flipped |= 10001;
    }
    if (o != flipped) {
      list.add("0x" + Integer.toHexString((~flipped) & o));
    }
    return String.join(" | ", list);
  }
}
