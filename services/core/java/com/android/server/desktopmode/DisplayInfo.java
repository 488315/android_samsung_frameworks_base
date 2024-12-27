package com.android.server.desktopmode;

import android.graphics.Point;
import android.view.Display;
import android.view.Surface;

import com.samsung.android.desktopmode.DesktopModeFeature;

import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class DisplayInfo {
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
        } else if (!DesktopModeFeature.SUPPORT_WIRELESS_DEX || (i & 67108864) == 0) {
            this.mType = displayInfo.type;
        } else {
            this.mType = 1001;
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || DisplayInfo.class != obj.getClass()) {
            return false;
        }
        DisplayInfo displayInfo = (DisplayInfo) obj;
        return this.mDisplayId == displayInfo.mDisplayId
                && this.mType == displayInfo.mType
                && this.mRotation == displayInfo.mRotation
                && this.mRealSize.equals(displayInfo.mRealSize);
    }

    public final int hashCode() {
        return Objects.hash(
                Integer.valueOf(this.mDisplayId),
                Integer.valueOf(this.mType),
                this.mRealSize,
                Integer.valueOf(this.mRotation));
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("DisplayInfo(id=");
        sb.append(this.mDisplayId);
        sb.append(", ");
        int i = this.mType;
        sb.append(i != 1000 ? i != 1001 ? Display.typeToString(i) : "WIRELESS_DEX" : "DEX_ON_PC");
        sb.append(", (");
        sb.append(this.mRealSize.x);
        sb.append("x");
        sb.append(this.mRealSize.y);
        sb.append("), ");
        sb.append(Surface.rotationToString(this.mRotation));
        sb.append(")");
        return sb.toString();
    }
}
