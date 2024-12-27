package com.samsung.android.biometrics.app.setting.face;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.DisplayCutout;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView;

import com.samsung.android.biometrics.app.setting.Utils;
import com.samsung.android.feature.SemFloatingFeature;

public final class PunchHoleVIHelper {
    public static final String PUNCH_HOLE_VI_INFO =
            SemFloatingFeature.getInstance()
                    .getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_PUNCHHOLE_VI");
    public PointF mCameraLocPercent;
    public final Context mContext;
    public final Display mDisplay;
    public final DisplayManager mDm;
    public PointF mFaceVISizePercent;
    public boolean mIsBasedOnType;
    public final boolean mIsFolderOpened;
    public final boolean mIsSupportDualDisplay = Utils.Config.FEATURE_SUPPORT_DUAL_DISPLAY;
    public String mVIFileName;
    public String mVIType;

    public PunchHoleVIHelper(Context context) {
        this.mContext = context;
        this.mIsFolderOpened = context.getResources().getConfiguration().semDisplayDeviceType == 0;
        DisplayManager displayManager = (DisplayManager) context.getSystemService("display");
        this.mDm = displayManager;
        this.mDisplay = displayManager.getDisplay(0);
    }

    public final String getAnimationName(boolean z) {
        String str = this.mVIFileName;
        String m =
                (z || Utils.isNightThemeOn(this.mContext))
                        ? AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, ".json")
                        : AbstractResolvableFuture$$ExternalSyntheticOutline0.m(
                                str, "_whitebg.json");
        Log.d("BSS_PunchHoleVIHelper", "Punch hole name : " + m);
        return m;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final Rect getPunchHoleVIRect() {
        char c;
        PointF pointF;
        PointF pointF2;
        int identifier;
        if (!this.mIsBasedOnType) {
            Rect rect = new Rect();
            PointF pointF3 = this.mCameraLocPercent;
            PointF pointF4 = this.mFaceVISizePercent;
            Point point = new Point();
            this.mDm.getDisplay(0).getRealSize(point);
            int rotation = this.mDisplay.getRotation();
            if (rotation == 1) {
                float f = point.x;
                float f2 = pointF3.y * f;
                float f3 = point.y;
                float f4 = pointF4.x * f3;
                int i = (int) (f2 - (f4 * 0.5f));
                rect.left = i;
                float f5 = (1.0f - pointF3.x) * f3;
                float f6 = f * pointF4.y;
                int i2 = (int) (f5 - (f6 * 0.5f));
                rect.top = i2;
                rect.right = (int) (f4 + i);
                rect.bottom = (int) (f6 + i2);
            } else if (rotation != 3) {
                float f7 = point.x;
                float f8 = pointF3.x;
                float f9 = pointF4.x;
                int i3 = (int) ((f8 - (f9 * 0.5f)) * f7);
                rect.left = i3;
                float f10 = point.y;
                float f11 = pointF3.y;
                float f12 = pointF4.y;
                int i4 = (int) ((f11 - (0.5f * f12)) * f10);
                rect.top = i4;
                rect.right = (int) ((f7 * f9) + i3);
                rect.bottom = (int) ((f10 * f12) + i4);
            } else {
                float f13 = point.x;
                float f14 = (1.0f - pointF3.y) * f13;
                float f15 = point.y;
                float f16 = pointF4.x * f15;
                int i5 = (int) (f14 - (f16 * 0.5f));
                rect.left = i5;
                float f17 = f15 * pointF3.x;
                float f18 = f13 * pointF4.y;
                int i6 = (int) (f17 - (f18 * 0.5f));
                rect.top = i6;
                rect.right = (int) (f16 + i5);
                rect.bottom = (int) (f18 + i6);
            }
            return rect;
        }
        Rect rect2 = new Rect();
        DisplayCutout cutout = this.mDisplay.getCutout();
        if (cutout == null) {
            Log.i("BSS_PunchHoleVIHelper", "getCutout is NULL!");
        } else {
            for (Rect rect3 : cutout.getBoundingRects()) {
                Log.d("BSS_PunchHoleVIHelper", "BoundingRect = " + rect3);
                String str = this.mVIType;
                switch (str.hashCode()) {
                    case -1360216880:
                        if (str.equals("circle")) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1047363022:
                        if (str.equals("infinity-ucut")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 3584429:
                        if (str.equals("ucut")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 3614220:
                        if (str.equals("vcut")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    default:
                        c = 65535;
                        break;
                }
                if (c == 0 || c == 1 || c == 2) {
                    rect2 = new Rect();
                    if (this.mVIType.equals("vcut")) {
                        pointF = new PointF(0.5f, 0.015f);
                        pointF2 = new PointF(0.25f, 0.06875f);
                    } else {
                        pointF = new PointF(0.5f, 0.0274f);
                        pointF2 = new PointF(0.204f, 0.05f);
                    }
                    Point point2 = new Point();
                    this.mDm.getDisplay(0).getRealSize(point2);
                    int i7 = point2.x;
                    int i8 = point2.y;
                    int rotation2 = this.mDisplay.getRotation();
                    if (rotation2 == 1) {
                        float f19 = i7;
                        float f20 = pointF.y * f19;
                        float f21 = i8;
                        float f22 = pointF2.x * f21;
                        int i9 = (int) (f20 - (f22 * 0.5f));
                        rect2.left = i9;
                        float f23 = (1.0f - pointF.x) * f21;
                        float f24 = f19 * pointF2.y;
                        int i10 = (int) (f23 - (f24 * 0.5f));
                        rect2.top = i10;
                        rect2.right = (int) (f22 + i9);
                        rect2.bottom = (int) (f24 + i10);
                    } else if (rotation2 != 3) {
                        float f25 = i7;
                        float f26 = pointF.x;
                        float f27 = pointF2.x;
                        int i11 = (int) ((f26 - (f27 * 0.5f)) * f25);
                        rect2.left = i11;
                        float f28 = i8;
                        float f29 = pointF.y;
                        float f30 = pointF2.y;
                        int i12 = (int) ((f29 - (f30 * 0.5f)) * f28);
                        rect2.top = i12;
                        rect2.right = (int) ((f25 * f27) + i11);
                        rect2.bottom = (int) ((f28 * f30) + i12);
                    } else {
                        float f31 = i7;
                        float f32 = (1.0f - pointF.y) * f31;
                        float f33 = i8;
                        float f34 = pointF2.x * f33;
                        int i13 = (int) (f32 - (f34 * 0.5f));
                        rect2.left = i13;
                        float f35 = f33 * pointF.x;
                        float f36 = f31 * pointF2.y;
                        int i14 = (int) (f35 - (f36 * 0.5f));
                        rect2.top = i14;
                        rect2.right = (int) (f34 + i13);
                        rect2.bottom = (int) (f36 + i14);
                    }
                    Log.d("BSS_PunchHoleVIHelper", "viViewLocation = " + rect2);
                } else {
                    Resources resources = this.mContext.getResources();
                    Rect rect4 = new Rect();
                    float dimension = resources.getDimension(17106283);
                    int identifier2 =
                            this.mContext
                                    .getResources()
                                    .getIdentifier(
                                            "config_enableDisplayCutoutProtection",
                                            "bool",
                                            "com.android.systemui");
                    float dimension2 =
                            (identifier2 == 0
                                            || !resources.getBoolean(identifier2)
                                            || (identifier =
                                                            this.mContext
                                                                    .getResources()
                                                                    .getIdentifier(
                                                                            "camera_protection_stroke_width",
                                                                            "dimen",
                                                                            "com.android.systemui"))
                                                    == 0)
                                    ? 0.0f
                                    : resources.getDimension(identifier);
                    int rotation3 = this.mDisplay.getRotation();
                    Log.d(
                            "BSS_PunchHoleVIHelper",
                            "rotation = "
                                    + rotation3
                                    + " cameraTopMargin = "
                                    + dimension
                                    + " cameraProtectionStroke = "
                                    + dimension2);
                    int i15 =
                            rotation3 == 0
                                    ? (rect3.right - rect3.left) / 3
                                    : (rect3.bottom - rect3.top) / 3;
                    int i16 =
                            (int)
                                    (dimension2 == RecyclerView.DECELERATION_RATE
                                            ? 0.0f
                                            : (dimension2 / 2.0f) + 1.0f);
                    int i17 = (((int) dimension) - i16) - i15;
                    int i18 =
                            (int)
                                    (dimension2 == RecyclerView.DECELERATION_RATE
                                            ? RecyclerView.DECELERATION_RATE
                                            : (dimension2 - dimension) - i17);
                    Log.d(
                            "BSS_PunchHoleVIHelper",
                            "rect = "
                                    + rect3
                                    + " viStroke = "
                                    + i15
                                    + " gap = "
                                    + i17
                                    + " cameraStroke = "
                                    + i16
                                    + " topCameraStroke = "
                                    + i18);
                    if (rotation3 == 1) {
                        rect4.left = rect3.left - i18;
                        rect4.top = (rect3.top - i15) - i16;
                        rect4.right = ((rect3.right + i15) - i17) + i16;
                        rect4.bottom = rect3.bottom + i15 + i16;
                    } else if (rotation3 != 3) {
                        rect4.left = (rect3.left - i15) - i16;
                        rect4.top = rect3.top - i18;
                        rect4.right = rect3.right + i15 + i16;
                        rect4.bottom = ((rect3.bottom + i15) - i17) + i16;
                    } else {
                        rect4.left = (rect3.left - i15) - i16;
                        rect4.top = (rect3.top - i15) - i16;
                        rect4.right = rect3.right + i18;
                        rect4.bottom = rect3.bottom + i15 + i16;
                    }
                    Log.d("BSS_PunchHoleVIHelper", "viViewLocation = " + rect4);
                    rect2 = rect4;
                }
                Log.d("BSS_PunchHoleVIHelper", "viViewLocation = " + rect2);
            }
        }
        return rect2;
    }

    public final boolean initialize() {
        String str = PUNCH_HOLE_VI_INFO;
        if (TextUtils.isEmpty(str)) {
            Log.i("BSS_PunchHoleVIHelper", "There is no punch hole info!");
            return false;
        }
        for (String str2 : TextUtils.split(str, ",")) {
            boolean contains = str2.contains("pos");
            boolean z = this.mIsFolderOpened;
            boolean z2 = this.mIsSupportDualDisplay;
            if (contains) {
                int i = z2 ? 5 : 3;
                String[] split = TextUtils.split(str2, ":");
                if (split.length == i) {
                    if (z2) {
                        this.mCameraLocPercent =
                                new PointF(
                                        Float.parseFloat(split[z ? (char) 1 : (char) 3]),
                                        Float.parseFloat(split[z ? (char) 2 : (char) 4]));
                    } else {
                        this.mCameraLocPercent =
                                new PointF(Float.parseFloat(split[1]), Float.parseFloat(split[2]));
                    }
                }
            }
            if (str2.contains("size")) {
                String[] split2 = TextUtils.split(str2, ":");
                if (split2.length == (z2 ? 5 : 3)) {
                    if (z2) {
                        this.mFaceVISizePercent =
                                new PointF(
                                        Float.parseFloat(split2[z ? (char) 1 : (char) 3]),
                                        Float.parseFloat(split2[z ? (char) 2 : (char) 4]));
                    } else {
                        this.mFaceVISizePercent =
                                new PointF(
                                        Float.parseFloat(split2[1]), Float.parseFloat(split2[2]));
                    }
                }
            }
            if (str2.contains("type")) {
                String[] split3 = TextUtils.split(str2, ":");
                if (split3.length >= 2) {
                    this.mVIFileName = "punch_hole_ic_" + split3[1];
                    this.mVIType = split3[1];
                }
            }
        }
        boolean z3 = (str.contains("pos") || str.contains("size")) ? false : true;
        this.mIsBasedOnType = z3;
        return z3
                ? this.mVIFileName != null
                : (this.mCameraLocPercent == null
                                || this.mFaceVISizePercent == null
                                || this.mVIFileName == null)
                        ? false
                        : true;
    }
}
