package com.android.systemui.wallpaper.colors;

import android.app.SemWallpaperColors;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        int hashCode = this.colors.hashCode() * 31;
        boolean z = this.isOpenTheme;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        int i2 = (hashCode + i) * 31;
        boolean z2 = this.isOpenThemeLockWallpaper;
        int i3 = z2;
        if (z2 != 0) {
            i3 = 1;
        }
        int i4 = (i2 + i3) * 31;
        boolean z3 = this.isLockStarEnabled;
        return i4 + (z3 ? 1 : z3 ? 1 : 0);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("ColorData(colors=");
        sb.append(this.colors);
        sb.append(", isOpenTheme=");
        sb.append(this.isOpenTheme);
        sb.append(", isOpenThemeLockWallpaper=");
        sb.append(this.isOpenThemeLockWallpaper);
        sb.append(", isLockStarEnabled=");
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m6m(sb, this.isLockStarEnabled, ")");
    }
}
