package com.android.server.display.brightness;

import android.p005os.IInstalld;
import android.util.Slog;
import com.android.internal.display.BrightnessSynchronizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/* loaded from: classes2.dex */
public final class BrightnessReason {
  public List changes = new ArrayList();
  public int mModifier;
  public int mReason;
  public int startBrightness;

  public void set(BrightnessReason brightnessReason) {
    setReason(
        brightnessReason == null ? 0 : brightnessReason.mReason,
        brightnessReason == null ? 0 : brightnessReason.startBrightness);
    setModifier(brightnessReason != null ? brightnessReason.mModifier : 0);
  }

  public void setReason(int i, float f) {
    setReason(i, BrightnessSynchronizer.brightnessFloatToInt(f));
  }

  public void setReason(int i, int i2) {
    if (i < 0 || i > 11) {
      Slog.w("BrightnessReason", "brightness reason out of bounds: " + i);
      return;
    }
    this.mReason = i;
    this.startBrightness = i2;
    this.changes.clear();
  }

  public void addModifier(int i, float f) {
    setModifier(this.mModifier | i);
    this.changes.add(new BrightnessChange(i, f));
  }

  public String changesToString() {
    final StringBuilder sb = new StringBuilder();
    sb.append(reasonToString(this.mReason));
    sb.append(String.format("[%d]", Integer.valueOf(this.startBrightness)));
    if (this.changes.size() > 0) {
      this.changes.forEach(
          new Consumer() { // from class:
                           // com.android.server.display.brightness.BrightnessReason$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
              sb.append((BrightnessReason.BrightnessChange) obj);
            }
          });
    }
    return sb.toString();
  }

  public boolean isReasonChanged(BrightnessReason brightnessReason, int i) {
    int i2 = this.mReason;
    int i3 = brightnessReason.mReason;
    if (i2 != i3) {
      return i2 == i || i3 == i;
    }
    return false;
  }

  public boolean isModifierChanged(BrightnessReason brightnessReason, int i) {
    return (this.mModifier == brightnessReason.mModifier
            || hasModifier(i) == brightnessReason.hasModifier(i))
        ? false
        : true;
  }

  public boolean isStartBrightnessChanged(BrightnessReason brightnessReason) {
    return this.startBrightness != brightnessReason.startBrightness;
  }

  public boolean hasLoggableChanges(BrightnessReason brightnessReason) {
    return isReasonChanged(brightnessReason, 6) || isModifierChanged(brightnessReason, 112);
  }

  public boolean hasModifier(int i) {
    return ((-131072) & i) == 0 && (this.mModifier & i) != 0;
  }

  public boolean equals(Object obj) {
    if (!(obj instanceof BrightnessReason)) {
      return false;
    }
    BrightnessReason brightnessReason = (BrightnessReason) obj;
    return brightnessReason.mReason == this.mReason && brightnessReason.mModifier == this.mModifier;
  }

  public int hashCode() {
    return Objects.hash(Integer.valueOf(this.mReason), Integer.valueOf(this.mModifier));
  }

  public String toString() {
    return toString(0);
  }

  public String toString(int i) {
    StringBuilder sb = new StringBuilder();
    sb.append(reasonToString(this.mReason));
    sb.append(" [");
    if ((i & 1) != 0) {
      sb.append(" temp_adj");
    }
    if ((i & 2) != 0) {
      sb.append(" auto_adj");
    }
    if ((this.mModifier & 2) != 0) {
      sb.append(" low_pwr");
    }
    if ((this.mModifier & 1) != 0) {
      sb.append(" dim");
    }
    if ((this.mModifier & 4) != 0) {
      sb.append(" hdr");
    }
    if ((this.mModifier & 8) != 0) {
      sb.append(" throttled");
    }
    int length = sb.length();
    if (sb.charAt(length - 1) == '[') {
      sb.setLength(length - 2);
    } else {
      sb.append(" ]");
    }
    return sb.toString();
  }

  public int getReason() {
    return this.mReason;
  }

  public int getModifier() {
    return this.mModifier;
  }

  public void setModifier(int i) {
    if (((-131072) & i) != 0) {
      Slog.w("BrightnessReason", "brightness modifier out of bounds: 0x" + Integer.toHexString(i));
      return;
    }
    this.mModifier = i;
  }

  public final String reasonToString(int i) {
    switch (i) {
      case 1:
        return "manual";
      case 2:
        return "doze";
      case 3:
        return "doze_default";
      case 4:
        return "automatic";
      case 5:
        return "screen_off";
      case 6:
        return "override";
      case 7:
        return "temporary";
      case 8:
        return "boost";
      case 9:
        return "screen_off_brightness_sensor";
      case 10:
        return "follower";
      case 11:
        return "last_target";
      default:
        return Integer.toString(i);
    }
  }

  public final String modifierToString(int i) {
    if (i == 1) {
      return "dim";
    }
    if (i == 2) {
      return "low_pwr";
    }
    switch (i) {
      case 4:
        return "hdr";
      case 8:
        return "throttled";
      case 16:
        return "scale_factor";
      case 32:
        return "mb_limit";
      case 64:
        return "ab_limit";
      case 128:
        return "cover_limit";
      case 256:
        return "hbm_block";
      case 512:
        return "outdoor";
      case 1024:
        return "curtain";
      case IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES /* 2048 */:
        return "doze_maximum";
      case IInstalld.FLAG_USE_QUOTA /* 4096 */:
        return "force_dim";
      case IInstalld.FLAG_FORCE /* 8192 */:
        return "restore_manual";
      case 16384:
        return "cover_display_demo";
      case 32768:
        return "screen_curtain";
      case 65536:
        return "freezing_brightness_mode";
      default:
        return "0x" + Integer.toHexString(i);
    }
  }

  public final class BrightnessChange {
    public final int adjustedBrightness;
    public final int modifier;

    public BrightnessChange(int i, float f) {
      this.modifier = i;
      this.adjustedBrightness = BrightnessSynchronizer.brightnessFloatToInt(f);
    }

    public String toString() {
      return String.format(
          " -> %s[%d]",
          BrightnessReason.this.modifierToString(this.modifier),
          Integer.valueOf(this.adjustedBrightness));
    }
  }
}
