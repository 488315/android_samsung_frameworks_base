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

    public SemWallpaperColorsArea(Context context, int which, int rotation) {
        this(context, which, rotation, null);
    }

    public SemWallpaperColorsArea(Context context, int which, int rotation, WallpaperColorOverrideAreas baseOverrideColorArea) {
        int width;
        int height;
        this.mKeyMap = new HashMap();
        this.mInit = true;
        this.mContext = context;
        this.mWhich = which;
        this.mRotation = rotation;
        if (context != null) {
            if (baseOverrideColorArea != null) {
                this.mWallpaperColorOverrideAreas = new WallpaperColorOverrideAreas(context, which, baseOverrideColorArea);
            } else {
                this.mWallpaperColorOverrideAreas = new WallpaperColorOverrideAreas(context, which);
                this.mWallpaperColorOverrideAreas.load();
            }
        }
        Resources resource = Resources.getSystem();
        if (this.mContext == null) {
            width = resource.getDisplayMetrics().widthPixels;
            height = resource.getDisplayMetrics().heightPixels;
        } else {
            Point size = getCoverScreenSize(context, which);
            size = size == null ? getDisplaySize(which) : size;
            width = size.x;
            height = size.y;
        }
        Log.d(TAG, "SemWallpaperColorsArea [" + width + ", " + height + "] which: " + which + " rotation: " + rotation + " has Base: " + (baseOverrideColorArea != null));
        boolean isLandscapeMode = width > height;
        this.mWidth = width;
        this.mHeight = height;
        int statusBarHeight = 0;
        int navigationBarHeight = 0;
        int statusBarTopMargin = 0;
        if ((Rune.SUPPORT_SUB_DISPLAY_MODE && Rune.SUPPORT_COVER_DISPLAY_WATCHFACE && (this.mWhich & 16) == 16) || (Rune.VIRTUAL_DISPLAY_WALLPAPER && (this.mWhich & 32) == 32)) {
            if (Rune.SUPPORT_LARGE_FRONT_SUB_DISPLAY) {
                statusBarHeight = 30;
                navigationBarHeight = 67;
                statusBarTopMargin = resource.getDimensionPixelSize(R.dimen.status_bar_camera_top_margin);
            }
        } else if (rotation != 0) {
            if (!isLandscapeMode) {
                this.mWidth = height;
                this.mHeight = width;
            }
            statusBarHeight = resource.getDimensionPixelSize(R.dimen.status_bar_height_landscape);
            navigationBarHeight = resource.getDimensionPixelSize(R.dimen.navigation_bar_height);
        } else {
            if (isLandscapeMode) {
                this.mWidth = height;
                this.mHeight = width;
            }
            statusBarHeight = resource.getDimensionPixelSize(R.dimen.status_bar_height_portrait);
            navigationBarHeight = resource.getDimensionPixelSize(R.dimen.navigation_bar_height);
            statusBarTopMargin = resource.getDimensionPixelSize(R.dimen.status_bar_camera_top_margin);
        }
        if (!Rune.SUPPORT_SUB_DISPLAY_MODE || !Rune.SUPPORT_COVER_DISPLAY_WATCHFACE || (this.mWhich & 16) != 16) {
            this.mDensity = resource.getDisplayMetrics().density;
        } else if (Rune.SUPPORT_LARGE_FRONT_SUB_DISPLAY) {
            this.mDensity = 2.125f;
        } else {
            this.mDensity = 1.0f;
        }
        this.mDpWidth = (int) (this.mWidth / this.mDensity);
        this.mDpHeight = (int) (this.mHeight / this.mDensity);
        this.mDpStatusBarHeight = (int) (statusBarHeight / this.mDensity);
        this.mDpNavigationBarHeight = (int) (navigationBarHeight / this.mDensity);
        this.mDpStatusBarTopMargin = (int) (statusBarTopMargin / this.mDensity);
        Log.d(TAG, "SemWallpaperColorsArea which = " + this.mWhich + ", mDensity : " + this.mDensity + ", " + this.mWidth + "x" + this.mHeight + "," + this.mDpWidth + "x" + this.mDpHeight + "," + this.mDpStatusBarHeight + "," + this.mDpNavigationBarHeight + ", " + this.mDpStatusBarTopMargin);
    }

    public Rect get(int area) {
        return get(area, 0, 0);
    }

    public Rect get(int area, int width, int height) {
        int displayType;
        int rotationType;
        Rect rect = new Rect();
        if (Rune.isTablet()) {
            displayType = 1;
            Log.d(TAG, "tablet mode");
            if (this.mRotation != 0) {
                rotationType = 3;
                switch (area) {
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
                        rect.top = ((int) (this.mDpHeight * 0.15d)) + 138 + 139;
                        rect.bottom = rect.top + 20;
                        break;
                    case 4:
                        rect.left = ((int) (this.mDpWidth * 0.06d)) + 25;
                        rect.right = this.mDpWidth - rect.left;
                        rect.top = (int) (this.mDpHeight * 0.85d);
                        rect.bottom = (int) (this.mDpHeight * 0.96d);
                        break;
                    case 5:
                        rect.left = (this.mDpWidth - ((this.mDpWidth * 22) / 100)) - 11;
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
                        Log.e(TAG, "unhandle area " + area);
                        break;
                }
            } else {
                rotationType = 0;
                switch (area) {
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
                        rect.top = ((int) (this.mDpHeight * 0.16d)) + 163 + 143;
                        rect.bottom = rect.top + 20;
                        break;
                    case 4:
                        rect.left = ((int) (this.mDpWidth * 0.04d)) + 25;
                        rect.right = this.mDpWidth - rect.left;
                        rect.top = (int) (this.mDpHeight * 0.875d);
                        rect.bottom = (int) (this.mDpHeight * 0.975d);
                        break;
                    case 5:
                        rect.left = (this.mDpWidth - ((this.mDpWidth * 22) / 100)) - 11;
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
                        Log.e(TAG, "unhandle area " + area);
                        break;
                }
            }
        } else if (Rune.VIRTUAL_DISPLAY_WALLPAPER && (this.mWhich & 32) == 32) {
            displayType = 4;
            rotationType = 0;
            switch (area) {
                case 0:
                    rect.left = (int) (this.mDpWidth * 0.104575165f);
                    rect.right = this.mDpWidth - rect.left;
                    rect.top = (int) (this.mDpHeight * 0.045081966f);
                    rect.bottom = (int) (rect.top + (this.mDpHeight * 0.06557377f));
                    break;
                case 2:
                    rect.left = (int) (this.mDpWidth * 0.104575165f);
                    rect.right = this.mDpWidth - rect.left;
                    rect.top = (int) (this.mDpHeight * 0.20081967f);
                    rect.bottom = (int) (rect.top + (this.mDpHeight * 0.64344263f));
                    break;
            }
        } else if (Rune.SUPPORT_SUB_DISPLAY_MODE && Rune.SUPPORT_COVER_DISPLAY_WATCHFACE && Rune.SUPPORT_LARGE_FRONT_SUB_DISPLAY && (this.mWhich & 16) == 16) {
            displayType = 3;
            rotationType = 0;
            switch (area) {
                case 0:
                    rect.left = (int) (this.mDpWidth * 0.045d);
                    rect.right = this.mDpWidth - rect.left;
                    rect.top = this.mDpStatusBarTopMargin;
                    rect.bottom = this.mDpStatusBarTopMargin + this.mDpStatusBarHeight;
                    break;
                case 1:
                case 6:
                default:
                    Log.e(TAG, "unhandle area " + area);
                    break;
                case 2:
                    rect.left = (int) (this.mDpWidth * 0.35d);
                    rect.right = (int) (this.mDpWidth * 0.65d);
                    rect.top = (int) (this.mDpHeight * 0.213d);
                    rect.bottom = (int) (this.mDpHeight * 0.685d);
                    break;
                case 3:
                    rect.left = (int) (this.mDpWidth * 0.469d);
                    rect.right = (int) (this.mDpWidth * 0.532d);
                    rect.top = (int) (this.mDpHeight * 0.018d);
                    rect.bottom = (int) (this.mDpHeight * 0.082d);
                    break;
                case 4:
                    rect.left = (int) (this.mDpWidth * 0.846d);
                    rect.right = (int) (this.mDpWidth * 0.953d);
                    rect.top = (int) (this.mDpHeight * 0.776d);
                    rect.bottom = (int) (this.mDpHeight * 0.888d);
                    break;
                case 5:
                    rect.left = (int) (this.mDpWidth * 0.058d);
                    rect.right = (int) (this.mDpWidth * 0.414d);
                    rect.top = this.mDpHeight - this.mDpNavigationBarHeight;
                    rect.bottom = this.mDpHeight;
                    break;
                case 7:
                    rect.left = 0;
                    rect.right = this.mDpWidth;
                    rect.top = 0;
                    rect.bottom = this.mDpHeight;
                    break;
            }
        } else if (Rune.SUPPORT_SUB_DISPLAY_MODE && Rune.SUPPORT_COVER_DISPLAY_WATCHFACE && (this.mWhich & 16) == 16) {
            displayType = 3;
            rotationType = 0;
            switch (area) {
                case 2:
                    rect.left = (this.mDpWidth - 487) / 2;
                    rect.right = this.mDpWidth - rect.left;
                    rect.top = 7;
                    rect.bottom = rect.top + 42;
                    break;
                case 3:
                case 5:
                case 6:
                default:
                    Log.e(TAG, "unhandle area " + area);
                    break;
                case 4:
                    rect.left = ((int) (this.mDpWidth * 0.5d)) - 150;
                    rect.right = rect.left + 300;
                    rect.top = this.mDpHeight - 42;
                    rect.bottom = this.mDpHeight;
                    break;
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
            }
        } else if (Rune.SUPPORT_SUB_DISPLAY_MODE && !Rune.SUPPORT_COVER_DISPLAY_WATCHFACE && (this.mWhich & 16) == 0) {
            displayType = 0;
            if (this.mRotation != 0) {
                rotationType = 3;
                switch (area) {
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
                        rect.top = ((int) (this.mDpHeight * 0.15d)) + 103 + 138;
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
                        Log.e(TAG, "unhandle area " + area);
                        break;
                }
            } else {
                rotationType = 0;
                switch (area) {
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
                        rect.top = ((int) (this.mDpHeight * 0.2d)) + 103 + 138;
                        rect.bottom = rect.top + 20;
                        break;
                    case 4:
                        rect.left = 50;
                        rect.right = this.mDpWidth - rect.left;
                        rect.top = ((int) (this.mDpHeight * 0.92d)) - 24;
                        rect.bottom = this.mDpHeight - 24;
                        break;
                    case 5:
                        rect.left = (this.mDpWidth - ((this.mDpWidth * 22) / 100)) - 11;
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
                        Log.e(TAG, "unhandle area " + area);
                        break;
                }
            }
        } else {
            displayType = 0;
            if (Rune.SUPPORT_SUB_DISPLAY_MODE && !Rune.SUPPORT_COVER_DISPLAY_WATCHFACE && (this.mWhich & 16) == 16) {
                displayType = 2;
            }
            if (this.mRotation != 0) {
                switch (area) {
                    case 0:
                        rect.left = 24;
                        rect.right = this.mDpWidth - 24;
                        rect.top = 0;
                        rect.bottom = this.mDpStatusBarHeight;
                        rotationType = 3;
                        break;
                    case 1:
                        int margin = (int) ((this.mDpWidth - this.mDpNavigationBarHeight) * 0.07d);
                        if (this.mRotation == 90) {
                            rotationType = 1;
                            rect.left = margin;
                            rect.right = (this.mDpWidth - this.mDpNavigationBarHeight) - margin;
                            rect.top = (int) (this.mDpHeight * 0.086d);
                            rect.bottom = (int) (this.mDpHeight * 0.789d);
                            break;
                        } else if (this.mRotation != 270) {
                            Log.e(TAG, "invalid rotation " + this.mRotation);
                            rect.left = 0;
                            rect.right = this.mDpWidth;
                            rect.top = 0;
                            rect.bottom = this.mDpHeight;
                            rotationType = 3;
                            break;
                        } else {
                            rotationType = 2;
                            rect.left = this.mDpNavigationBarHeight + margin;
                            rect.right = this.mDpWidth - margin;
                            rect.top = (int) (this.mDpHeight * 0.086d);
                            rect.bottom = (int) (this.mDpHeight * 0.789d);
                            break;
                        }
                    case 2:
                        rect.left = (this.mDpWidth / 2) - 140;
                        rect.right = rect.left + 280;
                        rect.top = this.mDpStatusBarHeight + 32;
                        rect.bottom = rect.top + 103;
                        rotationType = 3;
                        break;
                    case 3:
                        rect.left = (this.mDpWidth / 2) - 140;
                        rect.right = rect.left + 280;
                        rect.top = this.mDpStatusBarHeight + 32 + 103 + 26;
                        rect.bottom = rect.top + 20;
                        rotationType = 3;
                        break;
                    case 4:
                        rect.left = ((int) (this.mDpWidth * 0.15d)) + 18;
                        rect.right = (this.mDpWidth - ((int) (this.mDpWidth * 0.15d))) - 18;
                        rect.top = ((int) (this.mDpHeight * 0.833d)) - 19;
                        rect.bottom = this.mDpHeight - 19;
                        rotationType = 3;
                        break;
                    case 5:
                        rect.left = (this.mDpWidth - (this.mDpNavigationBarHeight / 2)) - 11;
                        rect.right = rect.left + 22;
                        rect.top = ((this.mDpHeight * 22) / 100) - 11;
                        rect.bottom = rect.top + 22;
                        rotationType = 3;
                        break;
                    case 6:
                        if (this.mRotation == 90) {
                            rotationType = 1;
                            rect.left = this.mDpWidth - this.mDpNavigationBarHeight;
                            rect.right = this.mDpWidth;
                            rect.top = 0;
                            rect.bottom = this.mDpHeight;
                            break;
                        } else if (this.mRotation != 270) {
                            Log.e(TAG, "invalid rotation " + this.mRotation);
                            rect.left = 0;
                            rect.right = this.mDpWidth;
                            rect.top = 0;
                            rect.bottom = this.mDpHeight;
                            rotationType = 3;
                            break;
                        } else {
                            rotationType = 2;
                            rect.left = 0;
                            rect.right = this.mDpNavigationBarHeight;
                            rect.top = 0;
                            rect.bottom = this.mDpHeight;
                            break;
                        }
                    case 7:
                        rect.left = 0;
                        rect.right = this.mDpWidth;
                        rect.top = 0;
                        rect.bottom = this.mDpHeight;
                        rotationType = 3;
                        break;
                    default:
                        Log.e(TAG, "unhandle area " + area);
                        rotationType = 3;
                        break;
                }
            } else {
                rotationType = 0;
                switch (area) {
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
                        rect.top = ((int) (this.mDpHeight * 0.2d)) + 103 + 184;
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
                        Log.e(TAG, "unhandle area " + area);
                        break;
                }
            }
        }
        if (this.mWallpaperColorOverrideAreas != null) {
            Long areaFlag = this.mKeyMap.get(Integer.valueOf(area));
            long safeAreaFlag = areaFlag == null ? 0L : areaFlag.longValue();
            Log.i(TAG, "Get custom area. display type = " + displayType + ", rotation = " + rotationType + ", area = " + area + " areaFlag = " + safeAreaFlag + " rect = " + rect);
            RectF customRectF = this.mWallpaperColorOverrideAreas.get(displayType, rotationType, safeAreaFlag);
            if (customRectF != null) {
                rect.left = (int) (this.mDpWidth * customRectF.left);
                rect.right = (int) (this.mDpWidth * customRectF.right);
                rect.top = (int) (this.mDpHeight * customRectF.top);
                rect.bottom = (int) (this.mDpHeight * customRectF.bottom);
                Log.i(TAG, "Has custom area. Original : " + customRectF + ", calculated = " + rect);
            }
        }
        rect.left = (int) (rect.left * this.mDensity);
        rect.right = (int) (rect.right * this.mDensity);
        rect.top = (int) (rect.top * this.mDensity);
        rect.bottom = (int) (rect.bottom * this.mDensity);
        if (width != 0 && height != 0) {
            rect.left = (int) (rect.left * (width / this.mWidth));
            rect.right = (int) (rect.right * (width / this.mWidth));
            rect.top = (int) (rect.top * (height / this.mHeight));
            rect.bottom = (int) (rect.bottom * (height / this.mHeight));
        }
        Log.i(TAG, "Final area : " + rect);
        return rect;
    }

    static String name(int area) {
        if (area < 0 || area >= NAME.length) {
            return "";
        }
        return NAME[area];
    }

    private int getDisplayId(int which) {
        int displayId = 0;
        if ((which & 8) == 8) {
            return 2;
        }
        if (Rune.SUPPORT_SUB_DISPLAY_MODE) {
            boolean isSubDisplay = (which & 16) == 16;
            if (Rune.SUPPORT_COVER_DISPLAY_WATCHFACE) {
                displayId = isSubDisplay ? 1 : 0;
            } else {
                WallpaperManager wallpaperManager = WallpaperManager.getInstance(this.mContext);
                if (wallpaperManager.getLidState() == 0) {
                    if (!isSubDisplay) {
                        displayId = 1;
                    }
                } else if (isSubDisplay) {
                    displayId = 1;
                }
            }
        }
        Log.d(TAG, "getDisplayId " + displayId);
        return displayId;
    }

    private Point getDisplaySize(int which) {
        int displayId = getDisplayId(which);
        DisplayInfo displayInfo = getDisplayInfo(displayId, which);
        Point size = new Point();
        size.x = displayInfo.logicalWidth;
        size.y = displayInfo.logicalHeight;
        Log.d(TAG, "getDisplaySize() which:" + which + ", displayId: " + displayId + ", size: " + size);
        if (!Rune.SUPPORT_SUB_DISPLAY_MODE || Rune.SUPPORT_COVER_DISPLAY_WATCHFACE || (which & 8) == 8 || WallpaperManager.getInstance(this.mContext).getLidState() != 0) {
            return size;
        }
        float ratio = size.y / size.x;
        boolean isSubDisplay = (which & 16) == 16;
        Log.d(TAG, "getDisplaySize() ratio: " + ratio + ", isSubDisplay:" + isSubDisplay);
        if ((isSubDisplay && ratio < 2.0f) || (!isSubDisplay && ratio > 2.0f)) {
            int displayId2 = displayId != 0 ? 0 : 1;
            DisplayInfo display = getDisplayInfo(displayId2, which);
            size.x = display.logicalWidth;
            size.y = display.logicalHeight;
            Log.d(TAG, "getDisplaySize() wrong displayInfo, changed to displayId: " + displayId2 + ", size: " + size);
        }
        return size;
    }

    private DisplayInfo getDisplayInfo(int displayId, int which) {
        DisplayManager displayManager = (DisplayManager) this.mContext.getSystemService(DisplayManager.class);
        Display display = displayManager.getDisplay(displayId);
        DisplayInfo displayInfo = new DisplayInfo();
        if (display != null) {
            if (Rune.VIRTUAL_DISPLAY_WALLPAPER && (which & 32) == 32) {
                Display[] displays = displayManager.getDisplays(DisplayManager.DISPLAY_CATEGORY_VIEW_COVER_DISPLAY);
                if (displays.length > 0) {
                    Display display2 = displays[0];
                    display2.getDisplayInfo(displayInfo);
                    Point screenSize = new Point();
                    display2.getRealSize(screenSize);
                    int i = screenSize.x;
                    int i2 = screenSize.y;
                }
            } else {
                display.getDisplayInfo(displayInfo);
            }
        } else {
            Log.d(TAG, "getDisplayInfo display == null");
            WindowManager wm = (WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE);
            Display defaultDisplay = wm.getDefaultDisplay();
            defaultDisplay.getDisplayInfo(displayInfo);
        }
        return displayInfo;
    }

    private Point getCoverScreenSize(Context context, int which) {
        Point screenSize = null;
        if (Rune.SUPPORT_SUB_DISPLAY_MODE && Rune.SUPPORT_COVER_DISPLAY_WATCHFACE && (which & 16) == 16 && Rune.SUPPORT_LARGE_FRONT_SUB_DISPLAY) {
            DisplayManager displayManager = (DisplayManager) context.getSystemService(DisplayManager.class);
            Display[] displays = displayManager.getDisplays("com.samsung.android.hardware.display.category.BUILTIN");
            for (Display display : displays) {
                int id = display.getDisplayId();
                if (id == 1) {
                    screenSize = new Point();
                    display.getRealSize(screenSize);
                }
            }
        }
        return screenSize;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public SemWallpaperColorsArea m553clone() {
        try {
            return (SemWallpaperColorsArea) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            Log.d(TAG, "clone: " + e.getMessage());
            return null;
        }
    }

    public void buildKeyMap(ArrayList<SemWallpaperColors.WallpaperColorsData> dataList) {
        if (dataList == null || dataList.isEmpty()) {
            return;
        }
        Iterator<SemWallpaperColors.WallpaperColorsData> it = dataList.iterator();
        while (it.hasNext()) {
            SemWallpaperColors.WallpaperColorsData data = it.next();
            if (data != null) {
                this.mKeyMap.put(Integer.valueOf(data.getInternalKey()), Long.valueOf(data.getExternalKey()));
            }
        }
    }
}
