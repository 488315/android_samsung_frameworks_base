package com.android.systemui.wallpaper.colors;

import android.app.SemWallpaperColors;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

public final class ColorData {
    public final SemWallpaperColors colors;
    public final boolean isLockStarEnabled;
    public final boolean isOpenTheme;
    public final boolean isOpenThemeLockWallpaper;

    public ColorData(SemWallpaperColors semWallpaperColors, boolean z, boolean z2, boolean z3) {
        this.colors = semWallpaperColors;
        this.isOpenTheme = z;
        this.isOpenThemeLockWallpaper = z2;
        this.isLockStarEnabled = z3;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ColorData)) {
            return false;
        }
        ColorData colorData = (ColorData) obj;
        return Intrinsics.areEqual(this.colors, colorData.colors) && this.isOpenTheme == colorData.isOpenTheme && this.isOpenThemeLockWallpaper == colorData.isOpenThemeLockWallpaper && this.isLockStarEnabled == colorData.isLockStarEnabled;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.isLockStarEnabled) + TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(this.colors.hashCode() * 31, 31, this.isOpenTheme), 31, this.isOpenThemeLockWallpaper);
    }

    public final String toString() {
        SemWallpaperColors semWallpaperColors = this.colors;
        StringBuilder sb = new StringBuilder("ColorData(colors=");
        sb.append(semWallpaperColors);
        sb.append(", isOpenTheme=");
        sb.append(this.isOpenTheme);
        sb.append(", isOpenThemeLockWallpaper=");
        sb.append(this.isOpenThemeLockWallpaper);
        sb.append(", isLockStarEnabled=");
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(sb, this.isLockStarEnabled, ")");
    }
}
