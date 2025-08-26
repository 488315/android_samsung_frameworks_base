package android.app;

import android.app.SemWallpaperColors;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.display.DisplayManager;
import android.util.Log;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.WindowManager;
import com.android.internal.R;
import com.samsung.android.wallpaper.Rune;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes.dex */
public class SemWallpaperColorsArea implements Cloneable {
    public static final int BACKGROUND = 7;
    public static final int BODY = 1;
    public static final int BODY_BOTTOM = 4;
    public static final int BODY_CENTER = 8;
    public static final int BODY_LEFT = 9;
    public static final int BODY_MID = 3;
    public static final int BODY_RIGHT = 10;
    public static final int BODY_TOP = 2;
    public static final float COVER_DISPLAY_DENSITY = 1.0f;
    public static final float COVER_LARGE_DISPLAY_DENSITY = 2.125f;
    private static final String DISPLAY_CATEGORY_BUILTIN = "com.samsung.android.hardware.display.category.BUILTIN";
    public static final String[] NAME = {"STATUSBAR", "BODY", "BODY_TOP", "BODY_MID", "BODY_BOTTOM", "NAVIBAR", "NAVIBAR", "BACKGROUND", "BODY_CENTER", "BODY_LEFT", "BODY_RIGHT"};
    public static final int NAVIBAR = 5;
    public static final int NAVIBAR_HOME = 6;
    public static final int RATIO_FOLD_SUB_CRITERIA = 2;
    public static final int STATUSBAR = 0;
    private static final String TAG = "SemWallpaperColorsArea";
    private static final int mVirtualDisplayHeightDp = 244;
    private static final int mVirtualDisplayWidthDp = 153;
    private Context mContext;
    float mDensity;
    int mDpHeight;
    int mDpNavigationBarHeight;
    int mDpStatusBarHeight;
    int mDpStatusBarTopMargin;
    int mDpWidth;
    int mHeight;
    boolean mInit;
    private Map<Integer, Long> mKeyMap;
    private int mRotation;
    private WallpaperColorOverrideAreas mWallpaperColorOverrideAreas;
    private int mWhich;
    int mWidth;

    public SemWallpaperColorsArea(Context context, int i, int i2) {
        this(context, i, i2, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:48:0x00f2  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0109  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public SemWallpaperColorsArea(Context context, int i, int i2, WallpaperColorOverrideAreas wallpaperColorOverrideAreas) throws Resources.NotFoundException {
        int i3;
        int i4;
        int dimensionPixelSize;
        int dimensionPixelSize2;
        int dimensionPixelSize3;
        int i5;
        this.mKeyMap = new HashMap();
        this.mInit = true;
        this.mContext = context;
        this.mWhich = i;
        this.mRotation = i2;
        if (context != null) {
            if (wallpaperColorOverrideAreas != null) {
                this.mWallpaperColorOverrideAreas = new WallpaperColorOverrideAreas(context, i, wallpaperColorOverrideAreas);
            } else {
                WallpaperColorOverrideAreas wallpaperColorOverrideAreas2 = new WallpaperColorOverrideAreas(context, i);
                this.mWallpaperColorOverrideAreas = wallpaperColorOverrideAreas2;
                wallpaperColorOverrideAreas2.load();
            }
        }
        Resources system = Resources.getSystem();
        if (this.mContext == null) {
            i4 = system.getDisplayMetrics().widthPixels;
            i3 = system.getDisplayMetrics().heightPixels;
        } else {
            Point coverScreenSize = getCoverScreenSize(context, i);
            coverScreenSize = coverScreenSize == null ? getDisplaySize(i) : coverScreenSize;
            int i6 = coverScreenSize.x;
            i3 = coverScreenSize.y;
            i4 = i6;
        }
        StringBuilder sb = new StringBuilder("SemWallpaperColorsArea [");
        sb.append(i4);
        sb.append(", ");
        sb.append(i3);
        sb.append("] which: ");
        sb.append(i);
        sb.append(" rotation: ");
        sb.append(i2);
        sb.append(" has Base: ");
        int dimensionPixelSize4 = 0;
        sb.append(wallpaperColorOverrideAreas != null);
        Log.d(TAG, sb.toString());
        boolean z = i4 > i3;
        this.mWidth = i4;
        this.mHeight = i3;
        if ((Rune.SUPPORT_SUB_DISPLAY_MODE && Rune.SUPPORT_COVER_DISPLAY_WATCHFACE && (this.mWhich & 16) == 16) || (Rune.VIRTUAL_DISPLAY_WALLPAPER && (this.mWhich & 32) == 32)) {
            if (Rune.SUPPORT_LARGE_FRONT_SUB_DISPLAY) {
                dimensionPixelSize4 = system.getDimensionPixelSize(R.dimen.status_bar_camera_top_margin);
                dimensionPixelSize3 = 30;
                dimensionPixelSize2 = 67;
            } else {
                i5 = 0;
                dimensionPixelSize2 = 0;
                if (Rune.SUPPORT_SUB_DISPLAY_MODE) {
                    this.mDensity = system.getDisplayMetrics().density;
                }
                float f = this.mWidth;
                float f2 = this.mDensity;
                this.mDpWidth = (int) (f / f2);
                this.mDpHeight = (int) (this.mHeight / f2);
                this.mDpStatusBarHeight = (int) (dimensionPixelSize4 / f2);
                this.mDpNavigationBarHeight = (int) (dimensionPixelSize2 / f2);
                this.mDpStatusBarTopMargin = (int) (i5 / f2);
                Log.d(TAG, "SemWallpaperColorsArea which = " + this.mWhich + ", mDensity : " + this.mDensity + ", " + this.mWidth + "x" + this.mHeight + "," + this.mDpWidth + "x" + this.mDpHeight + "," + this.mDpStatusBarHeight + "," + this.mDpNavigationBarHeight + ", " + this.mDpStatusBarTopMargin);
            }
        } else if (i2 != 0) {
            if (!z) {
                this.mWidth = i3;
                this.mHeight = i4;
            }
            dimensionPixelSize3 = system.getDimensionPixelSize(R.dimen.status_bar_height_landscape);
            dimensionPixelSize2 = system.getDimensionPixelSize(R.dimen.navigation_bar_height);
        } else {
            if (z) {
                this.mWidth = i3;
                this.mHeight = i4;
            }
            dimensionPixelSize4 = system.getDimensionPixelSize(R.dimen.status_bar_height_portrait);
            int dimensionPixelSize5 = system.getDimensionPixelSize(R.dimen.navigation_bar_height);
            dimensionPixelSize = system.getDimensionPixelSize(R.dimen.status_bar_camera_top_margin);
            dimensionPixelSize2 = dimensionPixelSize5;
            i5 = dimensionPixelSize;
            if (Rune.SUPPORT_SUB_DISPLAY_MODE && Rune.SUPPORT_COVER_DISPLAY_WATCHFACE && (this.mWhich & 16) == 16) {
                if (Rune.SUPPORT_LARGE_FRONT_SUB_DISPLAY) {
                    this.mDensity = 2.125f;
                } else {
                    this.mDensity = 1.0f;
                }
            } else {
                this.mDensity = system.getDisplayMetrics().density;
            }
            float f3 = this.mWidth;
            float f22 = this.mDensity;
            this.mDpWidth = (int) (f3 / f22);
            this.mDpHeight = (int) (this.mHeight / f22);
            this.mDpStatusBarHeight = (int) (dimensionPixelSize4 / f22);
            this.mDpNavigationBarHeight = (int) (dimensionPixelSize2 / f22);
            this.mDpStatusBarTopMargin = (int) (i5 / f22);
            Log.d(TAG, "SemWallpaperColorsArea which = " + this.mWhich + ", mDensity : " + this.mDensity + ", " + this.mWidth + "x" + this.mHeight + "," + this.mDpWidth + "x" + this.mDpHeight + "," + this.mDpStatusBarHeight + "," + this.mDpNavigationBarHeight + ", " + this.mDpStatusBarTopMargin);
        }
        dimensionPixelSize = dimensionPixelSize4;
        dimensionPixelSize4 = dimensionPixelSize3;
        i5 = dimensionPixelSize;
        if (Rune.SUPPORT_SUB_DISPLAY_MODE) {
        }
        float f32 = this.mWidth;
        float f222 = this.mDensity;
        this.mDpWidth = (int) (f32 / f222);
        this.mDpHeight = (int) (this.mHeight / f222);
        this.mDpStatusBarHeight = (int) (dimensionPixelSize4 / f222);
        this.mDpNavigationBarHeight = (int) (dimensionPixelSize2 / f222);
        this.mDpStatusBarTopMargin = (int) (i5 / f222);
        Log.d(TAG, "SemWallpaperColorsArea which = " + this.mWhich + ", mDensity : " + this.mDensity + ", " + this.mWidth + "x" + this.mHeight + "," + this.mDpWidth + "x" + this.mDpHeight + "," + this.mDpStatusBarHeight + "," + this.mDpNavigationBarHeight + ", " + this.mDpStatusBarTopMargin);
    }

    public Rect get(int i) {
        return get(i, 0, 0);
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:120:0x0696. Please report as an issue. */
    public Rect get(int i, int i2, int i3) {
        Rect rect = new Rect();
        int i4 = 3;
        int i5 = 0;
        if (Rune.isTablet()) {
            Log.d(TAG, "tablet mode");
            if (this.mRotation != 0) {
                switch (i) {
                    case 0:
                        rect.left = 24;
                        rect.right = this.mDpWidth - 24;
                        rect.top = 0;
                        rect.bottom = this.mDpStatusBarHeight;
                        break;
                    case 1:
                        rect.left = (int) (this.mDpWidth * 0.07d);
                        rect.right = this.mDpWidth - rect.left;
                        rect.top = (int) ((this.mDpHeight - this.mDpNavigationBarHeight) * 0.078d);
                        rect.bottom = (int) ((this.mDpHeight - this.mDpNavigationBarHeight) * 0.843d);
                        break;
                    case 2:
                        rect.left = ((int) (this.mDpWidth * 0.5d)) - 160;
                        rect.right = rect.left + 320;
                        rect.top = (int) (this.mDpHeight * 0.15d);
                        rect.bottom = rect.top + 138;
                        break;
                    case 3:
                        rect.left = ((int) (this.mDpWidth * 0.5d)) - 160;
                        rect.right = rect.left + 320;
                        rect.top = ((int) (this.mDpHeight * 0.15d)) + 277;
                        rect.bottom = rect.top + 20;
                        break;
                    case 4:
                        rect.left = ((int) (this.mDpWidth * 0.06d)) + 25;
                        rect.right = this.mDpWidth - rect.left;
                        rect.top = (int) (this.mDpHeight * 0.85d);
                        rect.bottom = (int) (this.mDpHeight * 0.96d);
                        break;
                    case 5:
                        int i6 = this.mDpWidth;
                        rect.left = (i6 - ((i6 * 22) / 100)) - 11;
                        rect.right = rect.left + 22;
                        rect.top = (this.mDpHeight - (this.mDpNavigationBarHeight / 2)) - 11;
                        rect.bottom = rect.top + 22;
                        break;
                    case 6:
                        rect.left = 0;
                        rect.right = this.mDpWidth;
                        rect.top = this.mDpHeight - this.mDpNavigationBarHeight;
                        rect.bottom = this.mDpHeight;
                        break;
                    case 7:
                        rect.left = 0;
                        rect.right = this.mDpWidth;
                        rect.top = 0;
                        rect.bottom = this.mDpHeight;
                        break;
                    default:
                        Log.e(TAG, "unhandle area " + i);
                        break;
                }
            } else {
                switch (i) {
                    case 0:
                        rect.left = 24;
                        rect.right = this.mDpWidth - 24;
                        rect.top = 0;
                        rect.bottom = this.mDpStatusBarHeight;
                        break;
                    case 1:
                        rect.left = (int) (this.mDpWidth * 0.025d);
                        rect.right = this.mDpWidth - rect.left;
                        rect.top = (int) ((this.mDpHeight - this.mDpNavigationBarHeight) * 0.08d);
                        rect.bottom = (int) ((this.mDpHeight - this.mDpNavigationBarHeight) * 0.844d);
                        break;
                    case 2:
                        rect.left = ((int) (this.mDpWidth * 0.5d)) - 160;
                        rect.right = rect.left + 320;
                        rect.top = (int) (this.mDpHeight * 0.16d);
                        rect.bottom = rect.top + 163;
                        break;
                    case 3:
                        rect.left = ((int) (this.mDpWidth * 0.5d)) - 160;
                        rect.right = rect.left + 320;
                        rect.top = ((int) (this.mDpHeight * 0.16d)) + 306;
                        rect.bottom = rect.top + 20;
                        break;
                    case 4:
                        rect.left = ((int) (this.mDpWidth * 0.04d)) + 25;
                        rect.right = this.mDpWidth - rect.left;
                        rect.top = (int) (this.mDpHeight * 0.875d);
                        rect.bottom = (int) (this.mDpHeight * 0.975d);
                        break;
                    case 5:
                        int i7 = this.mDpWidth;
                        rect.left = (i7 - ((i7 * 22) / 100)) - 11;
                        rect.right = rect.left + 22;
                        rect.top = (this.mDpHeight - (this.mDpNavigationBarHeight / 2)) - 11;
                        rect.bottom = rect.top + 22;
                        break;
                    case 6:
                        rect.left = 0;
                        rect.right = this.mDpWidth;
                        rect.top = this.mDpHeight - this.mDpNavigationBarHeight;
                        rect.bottom = this.mDpHeight;
                        break;
                    case 7:
                        rect.left = 0;
                        rect.right = this.mDpWidth;
                        rect.top = 0;
                        rect.bottom = this.mDpHeight;
                        break;
                    default:
                        Log.e(TAG, "unhandle area " + i);
                        break;
                }
                i4 = 0;
            }
            i5 = 1;
        } else if (Rune.VIRTUAL_DISPLAY_WALLPAPER && (this.mWhich & 32) == 32) {
            if (i == 0) {
                rect.left = (int) (this.mDpWidth * 0.104575165f);
                rect.right = this.mDpWidth - rect.left;
                rect.top = (int) (this.mDpHeight * 0.045081966f);
                rect.bottom = (int) (rect.top + (this.mDpHeight * 0.06557377f));
            } else if (i == 2) {
                rect.left = (int) (this.mDpWidth * 0.104575165f);
                rect.right = this.mDpWidth - rect.left;
                rect.top = (int) (this.mDpHeight * 0.20081967f);
                rect.bottom = (int) (rect.top + (this.mDpHeight * 0.64344263f));
            }
            i4 = 0;
            i5 = 4;
        } else {
            if (Rune.SUPPORT_SUB_DISPLAY_MODE && Rune.SUPPORT_COVER_DISPLAY_WATCHFACE && Rune.SUPPORT_LARGE_FRONT_SUB_DISPLAY && (this.mWhich & 16) == 16) {
                if (i == 0) {
                    rect.left = (int) (this.mDpWidth * 0.045d);
                    rect.right = this.mDpWidth - rect.left;
                    rect.top = this.mDpStatusBarTopMargin;
                    rect.bottom = this.mDpStatusBarTopMargin + this.mDpStatusBarHeight;
                } else if (i == 7) {
                    rect.left = 0;
                    rect.right = this.mDpWidth;
                    rect.top = 0;
                    rect.bottom = this.mDpHeight;
                } else if (i == 2) {
                    rect.left = (int) (this.mDpWidth * 0.35d);
                    rect.right = (int) (this.mDpWidth * 0.65d);
                    rect.top = (int) (this.mDpHeight * 0.213d);
                    rect.bottom = (int) (this.mDpHeight * 0.685d);
                } else if (i == 3) {
                    rect.left = (int) (this.mDpWidth * 0.469d);
                    rect.right = (int) (this.mDpWidth * 0.532d);
                    rect.top = (int) (this.mDpHeight * 0.018d);
                    rect.bottom = (int) (this.mDpHeight * 0.082d);
                } else if (i == 4) {
                    rect.left = (int) (this.mDpWidth * 0.846d);
                    rect.right = (int) (this.mDpWidth * 0.953d);
                    rect.top = (int) (this.mDpHeight * 0.776d);
                    rect.bottom = (int) (this.mDpHeight * 0.888d);
                } else if (i == 5) {
                    rect.left = (int) (this.mDpWidth * 0.058d);
                    rect.right = (int) (this.mDpWidth * 0.414d);
                    rect.top = this.mDpHeight - this.mDpNavigationBarHeight;
                    rect.bottom = this.mDpHeight;
                } else {
                    Log.e(TAG, "unhandle area " + i);
                }
            } else if (Rune.SUPPORT_SUB_DISPLAY_MODE && Rune.SUPPORT_COVER_DISPLAY_WATCHFACE && (this.mWhich & 16) == 16) {
                if (i == 2) {
                    rect.left = (this.mDpWidth - 487) / 2;
                    rect.right = this.mDpWidth - rect.left;
                    rect.top = 7;
                    rect.bottom = rect.top + 42;
                } else if (i == 4) {
                    rect.left = ((int) (this.mDpWidth * 0.5d)) - 150;
                    rect.right = rect.left + 300;
                    rect.top = this.mDpHeight - 42;
                    rect.bottom = this.mDpHeight;
                } else {
                    switch (i) {
                        case 7:
                            rect.left = 0;
                            rect.right = this.mDpWidth;
                            rect.top = 0;
                            rect.bottom = this.mDpHeight;
                            break;
                        case 8:
                            rect.left = 126;
                            rect.right = rect.left + 260;
                            rect.top = 38;
                            rect.bottom = rect.top + 139;
                            break;
                        case 9:
                            rect.left = 48;
                            rect.right = rect.left + 278;
                            rect.top = 33;
                            rect.bottom = rect.top + 157;
                            break;
                        case 10:
                            rect.left = 274;
                            rect.right = rect.left + 213;
                            rect.top = 54;
                            rect.bottom = rect.top + 163;
                            break;
                        default:
                            Log.e(TAG, "unhandle area " + i);
                            break;
                    }
                }
            } else if (Rune.SUPPORT_SUB_DISPLAY_MODE && !Rune.SUPPORT_COVER_DISPLAY_WATCHFACE && (this.mWhich & 16) == 0) {
                if (this.mRotation != 0) {
                    switch (i) {
                        case 0:
                            rect.left = 24;
                            rect.right = this.mDpWidth - 24;
                            rect.top = 0;
                            rect.bottom = this.mDpStatusBarHeight;
                            break;
                        case 1:
                            rect.left = (int) (this.mDpWidth * 0.036d);
                            rect.right = this.mDpWidth - rect.left;
                            rect.top = (int) ((this.mDpHeight - this.mDpNavigationBarHeight) * 0.072d);
                            rect.bottom = (int) ((this.mDpHeight - this.mDpNavigationBarHeight) * 0.855d);
                            break;
                        case 2:
                            rect.left = ((int) (this.mDpWidth * 0.5d)) - 160;
                            rect.right = rect.left + 320;
                            rect.top = (int) (this.mDpHeight * 0.15d);
                            rect.bottom = rect.top + 103;
                            break;
                        case 3:
                            rect.left = ((int) (this.mDpWidth * 0.5d)) - 160;
                            rect.right = rect.left + 320;
                            rect.top = ((int) (this.mDpHeight * 0.15d)) + 241;
                            rect.bottom = rect.top + 20;
                            break;
                        case 4:
                            rect.left = 64;
                            rect.right = this.mDpWidth - rect.left;
                            rect.top = ((int) (this.mDpHeight * 0.9d)) - 24;
                            rect.bottom = this.mDpHeight - 24;
                            break;
                        case 5:
                            rect.left = (this.mDpWidth - (this.mDpNavigationBarHeight / 2)) - 11;
                            rect.right = rect.left + 22;
                            rect.top = ((this.mDpHeight * 22) / 100) - 11;
                            rect.bottom = rect.top + 22;
                            break;
                        case 6:
                            rect.left = 0;
                            rect.right = this.mDpWidth;
                            rect.top = this.mDpHeight - this.mDpNavigationBarHeight;
                            rect.bottom = this.mDpHeight;
                            break;
                        case 7:
                            rect.left = 0;
                            rect.right = this.mDpWidth;
                            rect.top = 0;
                            rect.bottom = this.mDpHeight;
                            break;
                        default:
                            Log.e(TAG, "unhandle area " + i);
                            break;
                    }
                } else {
                    switch (i) {
                        case 0:
                            rect.left = 24;
                            rect.right = this.mDpWidth - 24;
                            if (Rune.isWinner()) {
                                rect.right += PackageManager.INSTALL_FAILED_SESSION_INVALID;
                            }
                            rect.top = this.mDpStatusBarTopMargin;
                            rect.bottom = this.mDpStatusBarHeight;
                            break;
                        case 1:
                            rect.left = (int) (this.mDpWidth * 0.036d);
                            rect.right = this.mDpWidth - rect.left;
                            rect.top = (int) ((this.mDpHeight - this.mDpNavigationBarHeight) * 0.074d);
                            rect.bottom = (int) ((this.mDpHeight - this.mDpNavigationBarHeight) * 0.855d);
                            break;
                        case 2:
                            rect.left = ((int) (this.mDpWidth * 0.5d)) - 160;
                            rect.right = rect.left + 320;
                            rect.top = (int) (this.mDpHeight * 0.2d);
                            rect.bottom = rect.top + 103;
                            break;
                        case 3:
                            rect.left = ((int) (this.mDpWidth * 0.5d)) - 160;
                            rect.right = rect.left + 320;
                            rect.top = ((int) (this.mDpHeight * 0.2d)) + 241;
                            rect.bottom = rect.top + 20;
                            break;
                        case 4:
                            rect.left = 50;
                            rect.right = this.mDpWidth - rect.left;
                            rect.top = ((int) (this.mDpHeight * 0.92d)) - 24;
                            rect.bottom = this.mDpHeight - 24;
                            break;
                        case 5:
                            int i8 = this.mDpWidth;
                            rect.left = (i8 - ((i8 * 22) / 100)) - 11;
                            rect.right = rect.left + 22;
                            rect.top = (this.mDpHeight - (this.mDpNavigationBarHeight / 2)) - 11;
                            rect.bottom = rect.top + 22;
                            break;
                        case 6:
                            rect.left = 0;
                            rect.right = this.mDpWidth;
                            rect.top = this.mDpHeight - this.mDpNavigationBarHeight;
                            rect.bottom = this.mDpHeight;
                            break;
                        case 7:
                            rect.left = 0;
                            rect.right = this.mDpWidth;
                            rect.top = 0;
                            rect.bottom = this.mDpHeight;
                            break;
                        default:
                            Log.e(TAG, "unhandle area " + i);
                            break;
                    }
                    i4 = 0;
                }
            } else {
                int i9 = (Rune.SUPPORT_SUB_DISPLAY_MODE && !Rune.SUPPORT_COVER_DISPLAY_WATCHFACE && (this.mWhich & 16) == 16) ? 2 : 0;
                int i10 = this.mRotation;
                if (i10 != 0) {
                    switch (i) {
                        case 0:
                            rect.left = 24;
                            rect.right = this.mDpWidth - 24;
                            rect.top = 0;
                            rect.bottom = this.mDpStatusBarHeight;
                            break;
                        case 1:
                            int i11 = this.mDpWidth;
                            int i12 = this.mDpNavigationBarHeight;
                            int i13 = (int) ((i11 - i12) * 0.07d);
                            if (i10 != 90) {
                                if (i10 == 270) {
                                    rect.left = i12 + i13;
                                    rect.right = this.mDpWidth - i13;
                                    rect.top = (int) (this.mDpHeight * 0.086d);
                                    rect.bottom = (int) (this.mDpHeight * 0.789d);
                                    i5 = i9;
                                    i4 = 2;
                                    break;
                                } else {
                                    Log.e(TAG, "invalid rotation " + this.mRotation);
                                    rect.left = 0;
                                    rect.right = this.mDpWidth;
                                    rect.top = 0;
                                    rect.bottom = this.mDpHeight;
                                    break;
                                }
                            } else {
                                rect.left = i13;
                                rect.right = (this.mDpWidth - this.mDpNavigationBarHeight) - i13;
                                rect.top = (int) (this.mDpHeight * 0.086d);
                                rect.bottom = (int) (this.mDpHeight * 0.789d);
                                i5 = i9;
                                i4 = 1;
                                break;
                            }
                        case 2:
                            rect.left = (this.mDpWidth / 2) - 140;
                            rect.right = rect.left + 280;
                            rect.top = this.mDpStatusBarHeight + 32;
                            rect.bottom = rect.top + 103;
                            break;
                        case 3:
                            rect.left = (this.mDpWidth / 2) - 140;
                            rect.right = rect.left + 280;
                            rect.top = this.mDpStatusBarHeight + 161;
                            rect.bottom = rect.top + 20;
                            break;
                        case 4:
                            rect.left = ((int) (this.mDpWidth * 0.15d)) + 18;
                            int i14 = this.mDpWidth;
                            rect.right = (i14 - ((int) (i14 * 0.15d))) - 18;
                            rect.top = ((int) (this.mDpHeight * 0.833d)) - 19;
                            rect.bottom = this.mDpHeight - 19;
                            break;
                        case 5:
                            rect.left = (this.mDpWidth - (this.mDpNavigationBarHeight / 2)) - 11;
                            rect.right = rect.left + 22;
                            rect.top = ((this.mDpHeight * 22) / 100) - 11;
                            rect.bottom = rect.top + 22;
                            break;
                        case 6:
                            if (i10 != 90) {
                                if (i10 == 270) {
                                    rect.left = 0;
                                    rect.right = this.mDpNavigationBarHeight;
                                    rect.top = 0;
                                    rect.bottom = this.mDpHeight;
                                    i5 = i9;
                                    i4 = 2;
                                    break;
                                } else {
                                    Log.e(TAG, "invalid rotation " + this.mRotation);
                                    rect.left = 0;
                                    rect.right = this.mDpWidth;
                                    rect.top = 0;
                                    rect.bottom = this.mDpHeight;
                                    break;
                                }
                            } else {
                                rect.left = this.mDpWidth - this.mDpNavigationBarHeight;
                                rect.right = this.mDpWidth;
                                rect.top = 0;
                                rect.bottom = this.mDpHeight;
                                i5 = i9;
                                i4 = 1;
                                break;
                            }
                        case 7:
                            rect.left = 0;
                            rect.right = this.mDpWidth;
                            rect.top = 0;
                            rect.bottom = this.mDpHeight;
                            break;
                        default:
                            Log.e(TAG, "unhandle area " + i);
                            break;
                    }
                } else {
                    switch (i) {
                        case 0:
                            rect.left = 24;
                            rect.right = this.mDpWidth - 24;
                            rect.top = this.mDpStatusBarTopMargin;
                            rect.bottom = this.mDpStatusBarHeight;
                            break;
                        case 1:
                            rect.left = (int) (this.mDpWidth * 0.036d);
                            rect.right = this.mDpWidth - rect.left;
                            rect.top = (int) ((this.mDpHeight - this.mDpNavigationBarHeight) * 0.074d);
                            rect.bottom = (int) ((this.mDpHeight - this.mDpNavigationBarHeight) * 0.854d);
                            break;
                        case 2:
                            rect.left = 40;
                            rect.right = this.mDpWidth - 40;
                            rect.top = (int) (this.mDpHeight * 0.2d);
                            rect.bottom = rect.top + 103;
                            break;
                        case 3:
                            rect.left = 40;
                            rect.right = this.mDpWidth - 40;
                            rect.top = ((int) (this.mDpHeight * 0.2d)) + 287;
                            rect.bottom = rect.top + 20;
                            break;
                        case 4:
                            rect.left = 40;
                            rect.right = this.mDpWidth - 40;
                            rect.top = (int) (this.mDpHeight * 0.58125d);
                            rect.bottom = (int) (this.mDpHeight * 0.78125d);
                            break;
                        case 5:
                            rect.left = 40;
                            rect.right = this.mDpWidth - 40;
                            rect.top = (int) (this.mDpHeight * 0.895d);
                            rect.bottom = (int) (this.mDpHeight * 0.965d);
                            break;
                        case 6:
                            rect.left = 0;
                            rect.right = this.mDpWidth;
                            rect.top = this.mDpHeight - this.mDpNavigationBarHeight;
                            rect.bottom = this.mDpHeight;
                            break;
                        case 7:
                            rect.left = 0;
                            rect.right = this.mDpWidth;
                            rect.top = 0;
                            rect.bottom = this.mDpHeight;
                            break;
                        default:
                            Log.e(TAG, "unhandle area " + i);
                            break;
                    }
                    i4 = 0;
                }
                i5 = i9;
            }
            i4 = 0;
            i5 = 3;
        }
        if (this.mWallpaperColorOverrideAreas != null) {
            Long l = this.mKeyMap.get(Integer.valueOf(i));
            long jLongValue = l == null ? 0L : l.longValue();
            Log.i(TAG, "Get custom area. display type = " + i5 + ", rotation = " + i4 + ", area = " + i + " areaFlag = " + jLongValue + " rect = " + rect);
            RectF rectF = this.mWallpaperColorOverrideAreas.get(i5, i4, jLongValue);
            if (rectF != null) {
                rect.left = (int) (this.mDpWidth * rectF.left);
                rect.right = (int) (this.mDpWidth * rectF.right);
                rect.top = (int) (this.mDpHeight * rectF.top);
                rect.bottom = (int) (this.mDpHeight * rectF.bottom);
                Log.i(TAG, "Has custom area. Original : " + rectF + ", calculated = " + rect);
            }
        }
        rect.left = (int) (rect.left * this.mDensity);
        rect.right = (int) (rect.right * this.mDensity);
        rect.top = (int) (rect.top * this.mDensity);
        rect.bottom = (int) (rect.bottom * this.mDensity);
        if (i2 != 0 && i3 != 0) {
            float f = i2;
            rect.left = (int) (rect.left * (f / this.mWidth));
            rect.right = (int) (rect.right * (f / this.mWidth));
            float f2 = i3;
            rect.top = (int) (rect.top * (f2 / this.mHeight));
            rect.bottom = (int) (rect.bottom * (f2 / this.mHeight));
        }
        Log.i(TAG, "Final area : " + rect);
        return rect;
    }

    static String name(int i) {
        if (i >= 0) {
            String[] strArr = NAME;
            if (i >= strArr.length) {
                return "";
            }
            return strArr[i];
        }
        return "";
    }

    private int getDisplayId(int i) {
        if (Rune.SUPPORT_DESKTOP_MODE && (i & 8) == 8) {
            return 2;
        }
        int i2 = 0;
        if (Rune.SUPPORT_SUB_DISPLAY_MODE) {
            int i3 = (i & 16) == 16 ? 1 : 0;
            if (Rune.SUPPORT_COVER_DISPLAY_WATCHFACE) {
                i2 = i3;
            } else if (WallpaperManager.getInstance(this.mContext).getLidState() != 0 ? i3 != 0 : i3 == 0) {
                i2 = 1;
            }
        }
        Log.d(TAG, "getDisplayId " + i2);
        return i2;
    }

    private Point getDisplaySize(int i) {
        int displayId = getDisplayId(i);
        DisplayInfo displayInfo = getDisplayInfo(displayId, i);
        Point point = new Point();
        point.x = displayInfo.logicalWidth;
        point.y = displayInfo.logicalHeight;
        Log.d(TAG, "getDisplaySize() which:" + i + ", displayId: " + displayId + ", size: " + point);
        if (Rune.SUPPORT_SUB_DISPLAY_MODE && !Rune.SUPPORT_COVER_DISPLAY_WATCHFACE && ((!Rune.SUPPORT_DESKTOP_MODE || (i & 8) != 8) && WallpaperManager.getInstance(this.mContext).getLidState() == 0)) {
            float f = point.y / point.x;
            boolean z = (i & 16) == 16;
            Log.d(TAG, "getDisplaySize() ratio: " + f + ", isSubDisplay:" + z);
            if ((z && f < 2.0f) || (!z && f > 2.0f)) {
                int i2 = displayId != 0 ? 0 : 1;
                DisplayInfo displayInfo2 = getDisplayInfo(i2, i);
                point.x = displayInfo2.logicalWidth;
                point.y = displayInfo2.logicalHeight;
                Log.d(TAG, "getDisplaySize() wrong displayInfo, changed to displayId: " + i2 + ", size: " + point);
            }
        }
        return point;
    }

    private DisplayInfo getDisplayInfo(int i, int i2) {
        DisplayManager displayManager = (DisplayManager) this.mContext.getSystemService(DisplayManager.class);
        Display display = displayManager.getDisplay(i);
        DisplayInfo displayInfo = new DisplayInfo();
        if (display != null) {
            if (Rune.VIRTUAL_DISPLAY_WALLPAPER && (i2 & 32) == 32) {
                Display[] displays = displayManager.getDisplays(DisplayManager.DISPLAY_CATEGORY_VIEW_COVER_DISPLAY);
                if (displays.length > 0) {
                    Display display2 = displays[0];
                    display2.getDisplayInfo(displayInfo);
                    Point point = new Point();
                    display2.getRealSize(point);
                    int i3 = point.x;
                    int i4 = point.y;
                }
                return displayInfo;
            }
            display.getDisplayInfo(displayInfo);
            return displayInfo;
        }
        Log.d(TAG, "getDisplayInfo display == null");
        ((WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getDisplayInfo(displayInfo);
        return displayInfo;
    }

    private Point getCoverScreenSize(Context context, int i) {
        Point point = null;
        if (Rune.SUPPORT_SUB_DISPLAY_MODE && Rune.SUPPORT_COVER_DISPLAY_WATCHFACE && (i & 16) == 16 && Rune.SUPPORT_LARGE_FRONT_SUB_DISPLAY) {
            for (Display display : ((DisplayManager) context.getSystemService(DisplayManager.class)).getDisplays("com.samsung.android.hardware.display.category.BUILTIN")) {
                if (display.getDisplayId() == 1) {
                    point = new Point();
                    display.getRealSize(point);
                }
            }
        }
        return point;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public SemWallpaperColorsArea m602clone() {
        try {
            return (SemWallpaperColorsArea) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            Log.d(TAG, "clone: " + e.getMessage());
            return null;
        }
    }

    public void buildKeyMap(ArrayList<SemWallpaperColors.WallpaperColorsData> arrayList) {
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }
        Iterator<SemWallpaperColors.WallpaperColorsData> it = arrayList.iterator();
        while (it.hasNext()) {
            SemWallpaperColors.WallpaperColorsData next = it.next();
            if (next != null) {
                this.mKeyMap.put(Integer.valueOf(next.getInternalKey()), Long.valueOf(next.getExternalKey()));
            }
        }
    }
}
