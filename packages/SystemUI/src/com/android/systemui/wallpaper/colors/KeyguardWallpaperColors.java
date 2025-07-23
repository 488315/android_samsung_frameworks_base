package com.android.systemui.wallpaper.colors;

import android.app.SemWallpaperColors;
import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import com.android.systemui.LsRune;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.wallpaper.utils.WhichChecker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class KeyguardWallpaperColors {
    public static final int NUM_SEPARATED_AREA;
    public static final long[] UPDATE_FLAGS;
    public final Context mContext;
    public final int mSelectedUserId;
    public final SparseArray mSemWallpaperColors = new SparseArray();
    public final SparseArray mSemWallpaperColorsCover = new SparseArray();
    private SettingsHelper mSettingsHelper;
    public static final long[] UPDATE_FLAGS_SHADOW = {4096, 8192, 16384, 32768, 65536, 131072};
    public static final long[] UPDATE_FLAGS_ADAPTIVE_CONTRAST = {1048576, 2097152, 4194304, 8388608, 16777216, 33554432};
    public static final String[] DEBUG_FLAG_NAMES = {"STATUSBAR ", "BODY_TOP ", "BODY_MID ", "BODY_BOTTOM ", "NAVIBAR ", "BACKGROUND "};

    static {
        long[] jArr = {16, 32, 64, 128, 256, 512};
        UPDATE_FLAGS = jArr;
        NUM_SEPARATED_AREA = jArr.length;
    }

    public KeyguardWallpaperColors(Context context, SettingsHelper settingsHelper, int i) {
        this.mContext = context;
        this.mSelectedUserId = i;
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

    public final long checkBaseUpdates(SemWallpaperColors semWallpaperColors) {
        return checkUpdates(new ColorData(SemWallpaperColors.getBlankWallpaperColors(), false, false, false), new ColorData(semWallpaperColors, this.mSettingsHelper.isOpenThemeLook(), this.mSettingsHelper.isOpenThemeLockWallpaper(), false));
    }

    public final long checkUpdates(ColorData colorData, ColorData colorData2) {
        long j;
        int i = 0;
        boolean z = colorData != null && colorData.isOpenTheme;
        boolean z2 = colorData2.isOpenTheme;
        long j2 = 0;
        long j3 = (!z2 && z == z2) ? 0L : 1L;
        if (this.mSettingsHelper.isColorThemeEnabled()) {
            j3 |= 1024;
        }
        if (WallpaperUtils.isAdaptiveColorEnabled()) {
            j3 |= 2;
        }
        SemWallpaperColors semWallpaperColors = colorData != null ? colorData.colors : null;
        SemWallpaperColors semWallpaperColors2 = colorData2.colors;
        if (semWallpaperColors != null || semWallpaperColors2 != null) {
            long[] jArr = UPDATE_FLAGS;
            long[] jArr2 = UPDATE_FLAGS_ADAPTIVE_CONTRAST;
            long[] jArr3 = UPDATE_FLAGS_SHADOW;
            int i2 = NUM_SEPARATED_AREA;
            if (semWallpaperColors == null && semWallpaperColors2 != null) {
                while (i < i2) {
                    if (semWallpaperColors2.get(jArr[i]) != null) {
                        j2 = j2 | jArr[i] | jArr3[i] | jArr2[i];
                    }
                    i++;
                }
            } else if (semWallpaperColors == null || semWallpaperColors2 != null) {
                for (int i3 = 0; i3 < i2; i3++) {
                    SemWallpaperColors.Item item = semWallpaperColors.get(jArr[i3]);
                    SemWallpaperColors.Item item2 = semWallpaperColors2.get(jArr[i3]);
                    if (item != null) {
                        if (LsRune.WALLPAPER_SUB_WATCHFACE && WhichChecker.isWatchFace(semWallpaperColors2.getWhich())) {
                            j2 = j2 | jArr[i3] | jArr3[i3];
                            j = jArr2[i3];
                        } else {
                            if (!item.equals(item2, 0)) {
                                j2 |= jArr[i3];
                            }
                            if (!item.equals(item2, 1)) {
                                j2 |= jArr3[i3];
                            }
                            if (!item.equals(item2, 2)) {
                                j = jArr2[i3];
                            }
                        }
                        j2 |= j;
                    } else if (item2 != null) {
                        j2 = j2 | jArr[i3] | jArr3[i3];
                        j = jArr2[i3];
                        j2 |= j;
                    }
                }
            } else {
                while (i < i2) {
                    if (semWallpaperColors.get(jArr[i]) != null) {
                        j2 = jArr[i] | j2 | jArr3[i] | jArr2[i];
                    }
                    i++;
                }
            }
        }
        return j3 | j2;
    }

    public final SemWallpaperColors getSemWallpaperColors(int i, boolean z) {
        try {
            return z ? ((ColorData) this.mSemWallpaperColorsCover.get(i)).colors : ((ColorData) this.mSemWallpaperColors.get(i)).colors;
        } catch (NullPointerException unused) {
            return null;
        }
    }

    public final long setSemWallpaperColors(SemWallpaperColors semWallpaperColors, int i) {
        long j;
        SemWallpaperColors.Item item;
        SemWallpaperColors.Item item2;
        SemWallpaperColors.Item item3;
        int i2 = this.mSelectedUserId;
        boolean z = LsRune.WALLPAPER_SUB_WATCHFACE;
        if (z || LsRune.WALLPAPER_VIRTUAL_DISPLAY) {
            if ((i & 2) == 0 && (i & 16) == 0 && (i & 32) == 0) {
                ListPopupWindow$$ExternalSyntheticOutline0.m(i, "setSemWallpaperColors: Nothing to do for which = ", "KeyguardWallpaperColors");
                return 0L;
            }
        } else if ((i & 2) != 2) {
            ListPopupWindow$$ExternalSyntheticOutline0.m(i, "setSemWallpaperColors: Nothing to do for which = ", "KeyguardWallpaperColors");
            return 0L;
        }
        SparseArray sparseArray = (WhichChecker.isWatchFace(i) || WhichChecker.isVirtualDisplay(i)) ? this.mSemWallpaperColorsCover : this.mSemWallpaperColors;
        if (semWallpaperColors != null) {
            StringBuilder m = MutableObjectList$$ExternalSyntheticOutline0.m(i, i2, "setSemWallpaperColors: which = ", ", userId = ", ", colors = ");
            m.append(semWallpaperColors.toSimpleString());
            Log.d("KeyguardWallpaperColors", m.toString());
            ColorData colorData = new ColorData(semWallpaperColors, this.mSettingsHelper.isOpenThemeLook(), this.mSettingsHelper.isOpenThemeLockWallpaper(), false);
            synchronized (sparseArray) {
                j = checkUpdates((ColorData) sparseArray.get(i2), colorData);
                sparseArray.put(i2, colorData);
            }
        } else {
            j = 0;
        }
        if (z && (WhichChecker.isWatchFace(i) || WhichChecker.isVirtualDisplay(i))) {
            return j;
        }
        if (j != 0) {
            Log.d("KeyguardWallpaperColors", "writeSettingsWallpaperColors() flags = " + j);
            if (j != 0 && semWallpaperColors != null) {
                if ((j & 512) != 0 && (item3 = semWallpaperColors.get(512L)) != null) {
                    this.mSettingsHelper.setWhiteKeyguardWallpaper(item3.getFontColor());
                }
                if ((j & 16) != 0 && (item2 = semWallpaperColors.get(16L)) != null) {
                    this.mSettingsHelper.setWhiteKeyguardStatusBar(item2.getFontColor());
                }
                if ((j & 256) != 0 && (item = semWallpaperColors.get(256L)) != null) {
                    this.mSettingsHelper.setWhiteKeyguardNavigationBar(item.getFontColor());
                }
            }
        }
        return j;
    }
}
