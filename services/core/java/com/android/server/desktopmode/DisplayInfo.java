package com.android.server.desktopmode;

import android.graphics.Point;
import android.view.Display;
import android.view.Surface;
import com.samsung.android.desktopmode.DesktopModeFeature;
import java.util.Objects;

/* loaded from: classes2.dex */
public class DisplayInfo {
  public final int mDisplayId;
  public final Point mRealSize;
  public final int mRotation;
  public final int mType;

  public DisplayInfo(Display display) {
    android.view.DisplayInfo displayInfo = new android.view.DisplayInfo();
    display.getDisplayInfo(displayInfo);
    this.mDisplayId = display.getDisplayId();
    this.mRealSize = new Point(displayInfo.logicalWidth, displayInfo.logicalHeight);
    this.mRotation = displayInfo.rotation;
    int i = displayInfo.flags;
    if (DesktopModeFeature.SUPPORT_DEX_ON_PC && (134217728 & i) != 0) {
      this.mType = 1000;
    } else if (DesktopModeFeature.SUPPORT_WIRELESS_DEX && (i & 67108864) != 0) {
      this.mType = 1001;
    } else {
      this.mType = displayInfo.type;
    }
  }

  public int getDisplayId() {
    return this.mDisplayId;
  }

  public int getType() {
    return this.mType;
  }

  public Point getRealSize() {
    return new Point(this.mRealSize);
  }

  public int getRotation() {
    return this.mRotation;
  }

  public static String typeToString(int i) {
    return i != 1000 ? i != 1001 ? Display.typeToString(i) : "WIRELESS_DEX" : "DEX_ON_PC";
  }

  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    DisplayInfo displayInfo = (DisplayInfo) obj;
    return this.mDisplayId == displayInfo.mDisplayId
        && this.mType == displayInfo.mType
        && this.mRotation == displayInfo.mRotation
        && this.mRealSize.equals(displayInfo.mRealSize);
  }

  public int hashCode() {
    return Objects.hash(
        Integer.valueOf(this.mDisplayId),
        Integer.valueOf(this.mType),
        this.mRealSize,
        Integer.valueOf(this.mRotation));
  }

  public String toString() {
    return "DisplayInfo(id="
        + this.mDisplayId
        + ", "
        + typeToString(this.mType)
        + ", ("
        + this.mRealSize.x
        + "x"
        + this.mRealSize.y
        + "), "
        + Surface.rotationToString(this.mRotation)
        + ")";
  }
}
