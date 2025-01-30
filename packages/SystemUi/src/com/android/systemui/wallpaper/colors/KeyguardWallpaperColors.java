package com.android.systemui.wallpaper.colors;

import android.app.SemWallpaperColors;
import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import com.android.systemui.LsRune;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.wallpaper.utils.WhichChecker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class KeyguardWallpaperColors {
    public static final int NUM_SEPARATED_AREA;
    public static final long[] UPDATE_FLAGS;
    public final SparseArray mSemWallpaperColors = new SparseArray();
    public final SparseArray mSemWallpaperColorsCover = new SparseArray();
    public final SettingsHelper mSettingsHelper;
    public static final long[] UPDATE_FLAGS_SHADOW = {4096, 8192, 16384, 32768, 65536, 131072};
    public static final long[] UPDATE_FLAGS_ADAPTIVE_CONTRAST = {1048576, 2097152, 4194304, 8388608, 16777216, 33554432};
    public static final String[] DEBUG_FLAG_NAMES = {"STATUSBAR ", "BODY_TOP ", "BODY_MID ", "BODY_BOTTOM ", "NAVIBAR ", "BACKGROUND "};

    static {
        long[] jArr = {16, 32, 64, 128, 256, 512};
        UPDATE_FLAGS = jArr;
        NUM_SEPARATED_AREA = jArr.length;
    }

    public KeyguardWallpaperColors(Context context, SettingsHelper settingsHelper) {
        this.mSettingsHelper = settingsHelper;
    }

    public static String getChangeFlagsString(long j) {
        StringBuilder sb = new StringBuilder("[ ");
        if ((1 & j) != 0) {
            sb.append("THEME ");
        }
        sb.append("| ");
        if ((1024 & j) != 0) {
            sb.append("COLOR_THEME ");
        }
        sb.append("| ");
        if ((2 & j) != 0) {
            sb.append("ADAPTIVE ");
        }
        sb.append("| ");
        int i = 0;
        for (int i2 = 0; i2 < 6; i2++) {
            if ((UPDATE_FLAGS[i2] & j) != 0) {
                sb.append(DEBUG_FLAG_NAMES[i2]);
            }
        }
        sb.append("| ");
        while (true) {
            if (i >= 6) {
                break;
            }
            if ((UPDATE_FLAGS_SHADOW[i] & j) != 0) {
                sb.append("SHADOW ");
                break;
            }
            i++;
        }
        sb.append("]");
        return sb.toString();
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00ce  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0082  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final long checkUpdates(ColorData colorData, ColorData colorData2) {
        KeyguardWallpaperColors keyguardWallpaperColors;
        long j;
        boolean z;
        SemWallpaperColors semWallpaperColors;
        int i;
        int i2;
        long j2;
        int i3 = 0;
        boolean z2 = colorData != null && colorData.isOpenTheme;
        boolean z3 = colorData2.isOpenTheme;
        boolean z4 = z3 || z2 != z3;
        long j3 = 0;
        if (z4) {
            j = 1;
            keyguardWallpaperColors = this;
        } else {
            keyguardWallpaperColors = this;
            j = 0;
        }
        if (keyguardWallpaperColors.mSettingsHelper.isColorThemeEnabled$1()) {
            j |= 1024;
        }
        try {
        } catch (Exception e) {
            Log.d("WallpaperUtils", "isAdaptiveColorEnabled: Error while reading settings (" + e.getMessage() + ")");
        }
        if (WallpaperUtils.mSettingsHelper.isAdaptiveColorMode() && !WallpaperUtils.mIsEmergencyMode && !WallpaperUtils.mIsUltraPowerSavingMode && !WallpaperUtils.isExternalLiveWallpaper()) {
            if (!WallpaperUtils.mSettingsHelper.isOpenThemeLockWallpaper()) {
                z = true;
                if (z) {
                    j |= 2;
                }
                semWallpaperColors = colorData == null ? colorData.colors : null;
                SemWallpaperColors semWallpaperColors2 = colorData2.colors;
                if (semWallpaperColors == null || semWallpaperColors2 != null) {
                    long[] jArr = UPDATE_FLAGS;
                    long[] jArr2 = UPDATE_FLAGS_ADAPTIVE_CONTRAST;
                    long[] jArr3 = UPDATE_FLAGS_SHADOW;
                    i = NUM_SEPARATED_AREA;
                    if (semWallpaperColors != null && semWallpaperColors2 != null) {
                        while (i3 < i) {
                            if (semWallpaperColors2.get(jArr[i3]) != null) {
                                j3 = j3 | jArr[i3] | jArr3[i3] | jArr2[i3];
                            }
                            i3++;
                        }
                    } else if (semWallpaperColors == null && semWallpaperColors2 == null) {
                        while (i3 < i) {
                            if (semWallpaperColors.get(jArr[i3]) != null) {
                                j3 = j3 | jArr[i3] | jArr3[i3] | jArr2[i3];
                            }
                            i3++;
                        }
                    } else {
                        for (i2 = 0; i2 < i; i2++) {
                            SemWallpaperColors.Item item = semWallpaperColors.get(jArr[i2]);
                            SemWallpaperColors.Item item2 = semWallpaperColors2.get(jArr[i2]);
                            if (item != null) {
                                if (LsRune.WALLPAPER_SUB_WATCHFACE && WhichChecker.isWatchFace(semWallpaperColors2.getWhich())) {
                                    j3 = j3 | jArr[i2] | jArr3[i2];
                                    j2 = jArr2[i2];
                                } else {
                                    if (!item.equals(item2, 0)) {
                                        j3 |= jArr[i2];
                                    }
                                    if (!item.equals(item2, 1)) {
                                        j3 |= jArr3[i2];
                                    }
                                    if (!item.equals(item2, 2)) {
                                        j2 = jArr2[i2];
                                    }
                                }
                                j3 |= j2;
                            } else if (item2 != null) {
                                j3 = j3 | jArr[i2] | jArr3[i2];
                                j2 = jArr2[i2];
                                j3 |= j2;
                            }
                        }
                    }
                }
                return j | j3;
            }
        }
        z = false;
        if (z) {
        }
        if (colorData == null) {
        }
        SemWallpaperColors semWallpaperColors22 = colorData2.colors;
        if (semWallpaperColors == null) {
        }
        long[] jArr4 = UPDATE_FLAGS;
        long[] jArr22 = UPDATE_FLAGS_ADAPTIVE_CONTRAST;
        long[] jArr32 = UPDATE_FLAGS_SHADOW;
        i = NUM_SEPARATED_AREA;
        if (semWallpaperColors != null) {
        }
        if (semWallpaperColors == null) {
        }
        while (i2 < i) {
        }
        return j | j3;
    }
}
