package com.samsung.android.allshare.extension;

import java.util.StringTokenizer;

/* loaded from: classes5.dex */
public class DeviceExtractor {

  public static class Seed {
    private static final String DELIMITER = "+";
    private String mInterface = "";
    private String mUUID = "";

    private Seed() {}

    public String getInterface() {
      return this.mInterface;
    }

    public String getUUID() {
      return this.mUUID;
    }

    public static Seed parseSeedString(String seedString) {
      StringTokenizer st = new StringTokenizer(seedString, DELIMITER);
      int count = st.countTokens();
      if (count != 2) {
        return null;
      }
      String uuid = "";
      if (st.hasMoreElements()) {
        uuid = st.nextToken();
      }
      String netInterface = "";
      if (st.hasMoreElements()) {
        netInterface = st.nextToken();
      }
      Seed seed = new Seed();
      seed.mUUID = uuid;
      seed.mInterface = netInterface;
      return seed;
    }
  }
}
