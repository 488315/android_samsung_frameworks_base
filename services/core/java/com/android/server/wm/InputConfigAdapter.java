package com.android.server.wm;

import android.os.IInstalld;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class InputConfigAdapter {
  public static final List INPUT_FEATURE_TO_CONFIG_MAP;
  public static final int INPUT_FEATURE_TO_CONFIG_MASK;
  public static final List LAYOUT_PARAM_FLAG_TO_CONFIG_MAP;
  public static final int LAYOUT_PARAM_FLAG_TO_CONFIG_MASK;

  public class FlagMapping {
    public final int mFlag;
    public final int mInputConfig;
    public final boolean mInverted;

    public FlagMapping(int i, int i2, boolean z) {
      this.mFlag = i;
      this.mInputConfig = i2;
      this.mInverted = z;
    }
  }

  static {
    List of =
        List.of(
            new FlagMapping(1, 1, false),
            new FlagMapping(2, IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES, false),
            new FlagMapping(4, 16384, false));
    INPUT_FEATURE_TO_CONFIG_MAP = of;
    INPUT_FEATURE_TO_CONFIG_MASK = computeMask(of);
    List of2 =
        List.of(
            new FlagMapping(16, 8, false),
            new FlagMapping(8388608, 16, true),
            new FlagMapping(262144, 512, false),
            new FlagMapping(536870912, 1024, false));
    LAYOUT_PARAM_FLAG_TO_CONFIG_MAP = of2;
    LAYOUT_PARAM_FLAG_TO_CONFIG_MASK = computeMask(of2);
  }

  public static int getMask() {
    return LAYOUT_PARAM_FLAG_TO_CONFIG_MASK | INPUT_FEATURE_TO_CONFIG_MASK | 64;
  }

  public static int getInputConfigFromWindowParams(int i, int i2, int i3) {
    return (i == 2013 ? 64 : 0)
        | applyMapping(i2, LAYOUT_PARAM_FLAG_TO_CONFIG_MAP)
        | applyMapping(i3, INPUT_FEATURE_TO_CONFIG_MAP);
  }

  public static int applyMapping(int i, List list) {
    Iterator it = list.iterator();
    int i2 = 0;
    while (it.hasNext()) {
      FlagMapping flagMapping = (FlagMapping) it.next();
      if (((flagMapping.mFlag & i) != 0) != flagMapping.mInverted) {
        i2 |= flagMapping.mInputConfig;
      }
    }
    return i2;
  }

  public static int computeMask(List list) {
    Iterator it = list.iterator();
    int i = 0;
    while (it.hasNext()) {
      i |= ((FlagMapping) it.next()).mInputConfig;
    }
    return i;
  }
}
