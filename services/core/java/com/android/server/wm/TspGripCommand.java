package com.android.server.wm;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Slog;
import com.android.internal.util.FrameworkStatsLog;
import com.samsung.android.rune.CoreRune;

/* loaded from: classes3.dex */
public class TspGripCommand {
  public static final float MIN_REJECT_ZONE_RATIO;
  public String mCommand;
  public TspStateController.DeviceSize mDeviceSize;
  public int mLandBottomGripWidth;
  public int mLandBottomRejectWidth;
  public int mLandEdgeZone;
  public int mLandTopGripWidth;
  public int mLandTopRejectWidth;
  public int mLandX1;
  public int mPortEdgeZone;
  public int mPortX1;
  public int mPortX2;
  public int mPortX3;
  public int mPortY1;
  public int mPortY2;
  public final int mType;

  static {
    MIN_REJECT_ZONE_RATIO = !CoreRune.IS_TABLET_DEVICE ? 0.0069f : 0.0035f;
  }

  public TspGripCommand(int i) {
    this.mCommand = null;
    this.mType = i;
    reset();
    switch (i) {
      case 1:
        this.mCommand = "0.69%,2.22%,30%,1.39%,4.17%,4.17%,1.05%,1.05%,1.97%,1.97%";
        break;
      case 2:
        this.mCommand = "0.35%,0.35%,0,0,2.11%,0,0.22%,0.22%,1.32%,1.32%";
        break;
      case 3:
        this.mCommand = "0.35%,0.35%,0,0,2.11%,0,0.22%,0.22%,1.32%,1.32%";
        break;
      case 4:
        this.mCommand = "0.69%,1.39%,30%,1.39%,4.17%,4.17%,0.66%,0.66%,1.97%,1.97%";
        reset3rdParty();
        break;
      case 5:
      case 8:
        this.mCommand = "0.35%,0.35%,0,0,1.41%,0,0.22%,0.22%,0.88%,0.88%";
        reset3rdParty();
        break;
      case 6:
      case 9:
        this.mCommand = "0.35%,0.35%,0,0,1.41%,0,0.22%,0.22%,0.88%,0.88%";
        reset3rdParty();
        break;
      case 7:
        this.mCommand = "0.69%,0.69%,30%,1.39%,2.78%,4.17%,0.66%,0.66%,1.97%,1.97%";
        reset3rdParty();
        break;
      case 10:
        this.mCommand = "0.69%,1.39%,30%,1.39%,4.17%,2.55%,0.66%,0.66%,1.97%,1.97%";
        reset3rdParty();
        break;
    }
  }

  public void reset() {
    this.mPortX1 = 10;
    this.mPortX2 = 32;
    this.mPortX3 = 32;
    this.mPortY1 = FrameworkStatsLog.APP_STANDBY_BUCKET_CHANGED__MAIN_REASON__MAIN_USAGE;
    this.mPortY2 = 2560;
    this.mLandX1 = 10;
    this.mPortEdgeZone = 60;
    this.mLandEdgeZone = 60;
    this.mLandTopRejectWidth = 0;
    this.mLandBottomRejectWidth = 0;
    this.mLandTopGripWidth = 0;
    this.mLandBottomGripWidth = 0;
  }

  public void reset3rdParty() {
    this.mPortX1 = 10;
    this.mPortX2 = 10;
    this.mPortX3 = 10;
    this.mPortY1 = 0;
    this.mPortY2 = 0;
    this.mLandX1 = 10;
    this.mPortEdgeZone = 40;
    this.mLandEdgeZone = 40;
  }

  public boolean parse(TspStateController.DeviceSize deviceSize, Bundle bundle) {
    if (bundle == null) {
      return false;
    }
    if ((CoreRune.FW_TSP_DEADZONE_V3
            && parseForV3(deviceSize, bundle.getString("deadzone_v3", null)))
        || parse(deviceSize, bundle.getString("deadzone_v2", null))) {
      return true;
    }
    int i = deviceSize.width;
    int i2 = deviceSize.height;
    int i3 = deviceSize.initWidth;
    int i4 = deviceSize.initHeight;
    int valueFromBundle = getValueFromBundle(bundle, "dead_zone_port_x1", -1, i, i3, 1440);
    int valueFromBundle2 = getValueFromBundle(bundle, "dead_zone_port_x2", -1, i, i3, 1440);
    if (valueFromBundle != -1 && valueFromBundle2 == -1) {
      this.mPortX2 = valueFromBundle;
    } else if (valueFromBundle != -1) {
      this.mPortX1 = valueFromBundle;
    }
    if (valueFromBundle2 != -1) {
      this.mPortX2 = valueFromBundle2;
    }
    this.mPortY1 = getValueFromBundle(bundle, "dead_zone_port_y1", this.mPortY1, i2, i4, 2560);
    int i5 = bundle.getInt("dead_zone_port_real_y1", -1);
    if (i5 != -1) {
      this.mPortY1 = getTspHeightPixel(i2, i4, i5);
    }
    this.mLandX1 = getValueFromBundle(bundle, "dead_zone_land_x1", this.mLandX1, i, i3, 1440);
    int valueFromBundle3 =
        getValueFromBundle(bundle, "edge_zone_width", this.mPortEdgeZone, i, i3, 1440);
    this.mPortEdgeZone = valueFromBundle3;
    this.mLandEdgeZone = valueFromBundle3;
    this.mLandEdgeZone =
        getValueFromBundle(bundle, "edge_zone_land", valueFromBundle3, i, i3, 1440);
    this.mPortEdgeZone =
        getValueFromBundle(bundle, "edge_zone_port", this.mPortEdgeZone, i, i3, 1440);
    setMinimumValue(i3);
    return true;
  }

  public boolean parseForV3(TspStateController.DeviceSize deviceSize, String str) {
    this.mDeviceSize = deviceSize;
    int i = deviceSize.initWidth;
    int i2 = deviceSize.initHeight;
    if (CoreRune.SAFE_DEBUG) {
      StringBuffer stringBuffer = new StringBuffer();
      stringBuffer.append("parseForV3: command=");
      stringBuffer.append(str);
      stringBuffer.append(" applyMin=true");
      Slog.d("TspGripCommand", stringBuffer.toString());
    }
    if (TextUtils.isEmpty(str)) {
      return false;
    }
    String[] split = str.split(",");
    int length = split.length;
    try {
      this.mPortX1 = convertToIntWidth(getValueOf(split, 0));
      if (length >= 3) {
        this.mPortX2 = convertToIntWidth(getValueOf(split, 1));
        this.mPortY1 = convertToIntHeight(getValueOf(split, 2));
      } else {
        this.mPortY1 = convertToIntHeight("100%");
      }
      if (length >= 5) {
        this.mPortX3 = convertToIntWidth(getValueOf(split, 3));
        int convertToIntHeight = convertToIntHeight(getValueOf(split, 4));
        if (convertToIntHeight >= this.mPortY1) {
          this.mPortY2 = convertToIntHeight;
        }
      } else {
        this.mPortY2 = convertToIntHeight("100%");
      }
      if (length >= 6) {
        this.mLandX1 = convertToIntWidth(getValueOf(split, 5));
      }
      if (length >= 7) {
        this.mPortEdgeZone = convertToIntWidth(getValueOf(split, 6));
      }
      this.mLandEdgeZone = this.mPortEdgeZone;
      if (length >= 8) {
        this.mLandEdgeZone = convertToIntWidth(getValueOf(split, 7));
      }
      if (length >= 9) {
        this.mLandTopRejectWidth = convertToIntLandscapeHeight(getValueOf(split, 8));
      }
      if (length >= 10) {
        this.mLandBottomRejectWidth = convertToIntLandscapeHeight(getValueOf(split, 9));
      }
      if (length >= 11) {
        this.mLandTopGripWidth = convertToIntLandscapeHeight(getValueOf(split, 10));
      }
      if (length >= 12) {
        this.mLandBottomGripWidth = convertToIntLandscapeHeight(getValueOf(split, 11));
      }
      setMinimumValue(i);
      if (CoreRune.SAFE_DEBUG) {
        Slog.d("TspGripCommand", "parse:" + toString());
      }
    } catch (NumberFormatException e) {
      e.printStackTrace();
    }
    return true;
  }

  public final boolean parse(TspStateController.DeviceSize deviceSize, String str) {
    return parse(deviceSize, str, true);
  }

  public boolean parse(TspStateController.DeviceSize deviceSize, String str, boolean z) {
    this.mDeviceSize = deviceSize;
    if (str == null) {
      str = this.mCommand;
    }
    int i = deviceSize.height;
    int i2 = deviceSize.initWidth;
    int i3 = deviceSize.initHeight;
    if (CoreRune.SAFE_DEBUG) {
      StringBuffer stringBuffer = new StringBuffer();
      stringBuffer.append("parse: command=");
      stringBuffer.append(str);
      stringBuffer.append(" applyMin=");
      stringBuffer.append(z);
      Slog.d("TspGripCommand", stringBuffer.toString());
    }
    if (TextUtils.isEmpty(str)) {
      return false;
    }
    String[] split = str.split(",");
    int length = split.length;
    try {
      if (length <= 1) {
        this.mPortX2 = convertToIntWidth(getValueOf(split, 0));
      } else {
        this.mPortX1 = convertToIntWidth(getValueOf(split, 0));
        if (length >= 3) {
          String valueOf = getValueOf(split, 1);
          String valueOf2 = getValueOf(split, 2);
          this.mPortX2 = convertToIntWidth(valueOf);
          this.mPortY1 = convertToIntHeight(valueOf2);
        }
        if (length >= 4) {
          this.mLandX1 = convertToIntWidth(getValueOf(split, 3));
        }
        if (length >= 5) {
          this.mPortEdgeZone = convertToIntWidth(getValueOf(split, 4));
        }
        this.mLandEdgeZone = this.mPortEdgeZone;
        if (length >= 6) {
          this.mLandEdgeZone = convertToIntWidth(getValueOf(split, 5));
        }
        if (length >= 7) {
          this.mLandTopRejectWidth = convertToIntLandscapeHeight(getValueOf(split, 6));
        }
        if (length >= 8) {
          this.mLandBottomRejectWidth = convertToIntLandscapeHeight(getValueOf(split, 7));
        }
        if (length >= 9) {
          this.mLandTopGripWidth = convertToIntLandscapeHeight(getValueOf(split, 8));
        }
        if (length >= 10) {
          this.mLandBottomGripWidth = convertToIntLandscapeHeight(getValueOf(split, 9));
        }
      }
      if (CoreRune.FW_TSP_DEADZONE_V3) {
        this.mPortX3 = this.mPortX2;
        this.mPortY2 = convertToInt(i, i3, "100%", 2560);
      }
    } catch (NumberFormatException e) {
      e.printStackTrace();
    }
    if (z) {
      setMinimumValue(i2);
    }
    if (CoreRune.SAFE_DEBUG) {
      Slog.d("TspGripCommand", "parse:" + toString());
    }
    return true;
  }

  public void set(TspGripCommand tspGripCommand) {
    this.mPortX1 = tspGripCommand.mPortX1;
    this.mPortX2 = tspGripCommand.mPortX2;
    this.mPortY1 = tspGripCommand.mPortY1;
    this.mLandX1 = tspGripCommand.mLandX1;
    if (CoreRune.FW_TSP_DEADZONE_V3) {
      this.mPortX3 = tspGripCommand.mPortX3;
      this.mPortY2 = tspGripCommand.mPortY2;
    }
    this.mPortEdgeZone = tspGripCommand.mPortEdgeZone;
    this.mLandEdgeZone = tspGripCommand.mLandEdgeZone;
    this.mLandTopRejectWidth = tspGripCommand.mLandTopRejectWidth;
    this.mLandBottomRejectWidth = tspGripCommand.mLandBottomRejectWidth;
    this.mLandTopGripWidth = tspGripCommand.mLandTopGripWidth;
    this.mLandBottomGripWidth = tspGripCommand.mLandBottomGripWidth;
  }

  public final int getValueFromBundle(Bundle bundle, String str, int i, int i2, int i3, int i4) {
    int i5 = bundle.getInt(str, -1);
    if (i5 >= 0) {
      return convertRatio(i5, i3, i4);
    }
    String string = bundle.getString(str);
    return TextUtils.isEmpty(string) ? i : convertToInt(i2, i3, string, i4);
  }

  public static int getTspHeightPixel(int i, int i2, int i3) {
    int i4 = (i3 * i2) / i;
    if (CoreRune.SAFE_DEBUG) {
      StringBuffer stringBuffer = new StringBuffer();
      stringBuffer.append("getTspHeightPixel: init=");
      stringBuffer.append(i2);
      stringBuffer.append(" h=");
      stringBuffer.append(i);
      stringBuffer.append(" ret=");
      stringBuffer.append(i4);
      Slog.d("TspGripCommand", stringBuffer.toString());
    }
    return i4;
  }

  public final String getValueOf(String[] strArr, int i) {
    String str = strArr[i];
    if (str != null) {
      return str.trim();
    }
    return null;
  }

  public final int convertToIntWidth(String str) {
    TspStateController.DeviceSize deviceSize = this.mDeviceSize;
    return convertToInt(deviceSize.width, deviceSize.initWidth, str, 1440);
  }

  public final int convertToIntHeight(String str) {
    TspStateController.DeviceSize deviceSize = this.mDeviceSize;
    return convertToInt(deviceSize.height, deviceSize.initHeight, str, 2560);
  }

  public final int convertToIntLandscapeHeight(String str) {
    TspStateController.DeviceSize deviceSize = this.mDeviceSize;
    return convertToInt(deviceSize.height, deviceSize.initHeight, str, 1440);
  }

  public final int convertToInt(int i, int i2, String str, int i3) {
    if (TextUtils.isEmpty(str)) {
      return 0;
    }
    String lowerCase = str.toLowerCase();
    int lastIndexOf = lowerCase.lastIndexOf("%");
    if (lastIndexOf >= 0) {
      return Math.round(
          i2 * (Float.parseFloat(lowerCase.subSequence(0, lastIndexOf).toString()) / 100.0f));
    }
    int lastIndexOf2 = lowerCase.lastIndexOf("px");
    if (lastIndexOf2 >= 0) {
      return (int) ((Float.parseFloat(lowerCase.subSequence(0, lastIndexOf2).toString()) * i2) / i);
    }
    return convertRatio(Integer.parseInt(str), i2, i3);
  }

  public final int convertRatio(int i, int i2, int i3) {
    return i2 < i3 ? (i * i2) / i3 : i;
  }

  public final void setMinimumValue(int i) {
    int min = Math.min((int) (i * MIN_REJECT_ZONE_RATIO), 10);
    if (this.mPortX1 < min) {
      this.mPortX1 = min;
    }
    if (this.mPortX2 < min) {
      this.mPortX2 = min;
    }
  }

  public String toString() {
    return "portX1="
        + this.mPortX1
        + ", portX2="
        + this.mPortX2
        + ", portX3="
        + this.mPortX3
        + ", portY1="
        + this.mPortY1
        + ", portY2="
        + this.mPortY2
        + ", landX1="
        + this.mLandX1
        + ", portEdge="
        + this.mPortEdgeZone
        + ", landEdge="
        + this.mLandEdgeZone
        + ", mLandTopRejectWidth="
        + this.mLandTopRejectWidth
        + ", mLandBottomRejectWidth="
        + this.mLandBottomRejectWidth
        + ", mLandTopGripWidth="
        + this.mLandTopGripWidth
        + ", mLandBottomGripWidth="
        + this.mLandBottomGripWidth;
  }
}
