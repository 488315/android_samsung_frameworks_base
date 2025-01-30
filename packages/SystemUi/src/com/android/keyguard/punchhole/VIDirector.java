package com.android.keyguard.punchhole;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.PointF;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.Log;
import androidx.picker.adapter.layoutmanager.AutoFitGridLayoutManager$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.util.DeviceState;
import com.samsung.android.feature.SemFloatingFeature;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class VIDirector {
    public static final String PUNCH_HOLE_VI_INFO = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_PUNCHHOLE_VI");
    public PointF mCameraLocPercent;
    public final Context mContext;
    public PointF mFaceVISizePercent;
    public boolean mIsBasedOnType;
    public boolean mIsBouncer;
    public boolean mIsFolderOpened;
    public String mVIFileName;
    public String mVIType;

    public VIDirector(Context context) {
        this.mContext = context;
    }

    public final int getScreenHeight() {
        Context context = this.mContext;
        Rect bounds = context.getResources().getConfiguration().windowConfiguration.getBounds();
        return DeviceState.shouldEnableKeyguardScreenRotation(context) ? bounds.height() : Math.max(bounds.width(), bounds.height());
    }

    public final int getScreenRotation() {
        Context context = this.mContext;
        if (DeviceState.shouldEnableKeyguardScreenRotation(context)) {
            return context.getResources().getConfiguration().windowConfiguration.getRotation();
        }
        return 0;
    }

    public final int getScreenWidth() {
        Context context = this.mContext;
        Rect bounds = context.getResources().getConfiguration().windowConfiguration.getBounds();
        return DeviceState.shouldEnableKeyguardScreenRotation(context) ? bounds.width() : Math.min(bounds.width(), bounds.height());
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final Rect getVIViewLocation(Rect rect, boolean z) {
        char c;
        Rect rect2;
        PointF pointF;
        PointF pointF2;
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
            setViViewLocation(rect2, pointF, pointF2);
        } else {
            Resources resources = this.mContext.getResources();
            rect2 = new Rect();
            float dimension = resources.getDimension(R.dimen.status_bar_height);
            float dimension2 = resources.getDimension(17106174);
            float dimension3 = resources.getBoolean(R.bool.config_enableDisplayCutoutProtection) ? resources.getDimension(R.dimen.camera_protection_stroke_width) : 0.0f;
            StringBuilder m88m = VIDirector$$ExternalSyntheticOutline0.m88m("statusBarHeight = ", dimension, " cameraTopMargin = ", dimension2, " cameraProtectionStroke = ");
            m88m.append(dimension3);
            Log.d("KeyguardPunchHoleVIView_VIDirector", m88m.toString());
            int screenRotation = getScreenRotation();
            int i = screenRotation == 0 ? (rect.right - rect.left) / 3 : (rect.bottom - rect.top) / 3;
            int i2 = (int) (dimension3 == 0.0f ? 0.0f : (dimension3 / 2.0f) + 1.0f);
            int i3 = (((int) dimension2) - i2) - i;
            int i4 = (int) (dimension3 != 0.0f ? (dimension3 - dimension2) - i3 : 0.0f);
            StringBuilder sb = new StringBuilder("rect = ");
            sb.append(rect);
            sb.append(" viStroke = ");
            sb.append(i);
            sb.append(" gap = ");
            AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m40m(sb, i3, " cameraStroke = ", i2, " topCameraStroke = ");
            RecyclerView$$ExternalSyntheticOutline0.m46m(sb, i4, "KeyguardPunchHoleVIView_VIDirector");
            if (screenRotation == 1) {
                rect2.left = rect.left - i4;
                rect2.top = (rect.top - i) - i2;
                rect2.right = ((rect.right + i) - i3) + i2;
                rect2.bottom = rect.bottom + i + i2;
            } else if (screenRotation != 3) {
                rect2.left = (rect.left - i) - i2;
                rect2.top = rect.top - i4;
                rect2.right = rect.right + i + i2;
                rect2.bottom = ((rect.bottom + i) - i3) + i2;
            } else {
                rect2.left = (rect.left - i) - i2;
                rect2.top = (rect.top - i) - i2;
                rect2.right = rect.right + i4;
                rect2.bottom = rect.bottom + i + i2;
            }
            Log.d("KeyguardPunchHoleVIView_VIDirector", "viViewLocation = " + rect2);
        }
        if (z) {
            int i5 = rect2.left;
            int screenWidth = getScreenWidth();
            rect2.left = rect2.right - screenWidth;
            rect2.right = i5 - screenWidth;
        }
        return rect2;
    }

    public final boolean initialize() {
        String str = PUNCH_HOLE_VI_INFO;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        for (String str2 : TextUtils.split(str, ",")) {
            if (str2.contains("pos")) {
                String[] split = TextUtils.split(str2, ":");
                boolean z = LsRune.SECURITY_SUB_DISPLAY_LOCK;
                if (split.length == (z ? 5 : 3)) {
                    if (z) {
                        this.mCameraLocPercent = new PointF(Float.valueOf(split[this.mIsFolderOpened ? (char) 1 : (char) 3]).floatValue(), Float.valueOf(split[this.mIsFolderOpened ? (char) 2 : (char) 4]).floatValue());
                    } else {
                        this.mCameraLocPercent = new PointF(Float.valueOf(split[1]).floatValue(), Float.valueOf(split[2]).floatValue());
                    }
                }
            }
            if (str2.contains("size")) {
                String[] split2 = TextUtils.split(str2, ":");
                boolean z2 = LsRune.SECURITY_SUB_DISPLAY_LOCK;
                if (split2.length == (z2 ? 5 : 3)) {
                    if (z2) {
                        this.mFaceVISizePercent = new PointF(Float.valueOf(split2[this.mIsFolderOpened ? (char) 1 : (char) 3]).floatValue(), Float.valueOf(split2[this.mIsFolderOpened ? (char) 2 : (char) 4]).floatValue());
                    } else {
                        this.mFaceVISizePercent = new PointF(Float.valueOf(split2[1]).floatValue(), Float.valueOf(split2[2]).floatValue());
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
        return z3 ? this.mVIFileName != null : (this.mCameraLocPercent == null || this.mFaceVISizePercent == null || this.mVIFileName == null) ? false : true;
    }

    public final void setViViewLocation(Rect rect, PointF pointF, PointF pointF2) {
        int screenWidth = getScreenWidth();
        int screenHeight = getScreenHeight();
        int screenRotation = getScreenRotation();
        if (screenRotation == 1) {
            float f = screenWidth;
            float f2 = pointF.y * f;
            float f3 = screenHeight;
            float f4 = pointF2.x * f3;
            int i = (int) (f2 - (f4 * 0.5f));
            rect.left = i;
            float f5 = (1.0f - pointF.x) * f3;
            float f6 = f * pointF2.y;
            int i2 = (int) (f5 - (0.5f * f6));
            rect.top = i2;
            rect.right = (int) (f4 + i);
            rect.bottom = (int) (f6 + i2);
            return;
        }
        if (screenRotation == 3) {
            float f7 = screenWidth;
            float f8 = (1.0f - pointF.y) * f7;
            float f9 = screenHeight;
            float f10 = pointF2.x * f9;
            int i3 = (int) (f8 - (f10 * 0.5f));
            rect.left = i3;
            float f11 = pointF.x * f9;
            float f12 = f7 * pointF2.y;
            int i4 = (int) (f11 - (0.5f * f12));
            rect.top = i4;
            rect.right = (int) (f10 + i3);
            rect.bottom = (int) (f12 + i4);
            return;
        }
        float f13 = screenWidth;
        float f14 = pointF.x;
        float f15 = pointF2.x;
        int i5 = (int) ((f14 - (f15 * 0.5f)) * f13);
        rect.left = i5;
        float f16 = screenHeight;
        float f17 = pointF.y;
        float f18 = pointF2.y;
        int i6 = (int) ((f17 - (0.5f * f18)) * f16);
        rect.top = i6;
        rect.right = (int) ((f13 * f15) + i5);
        rect.bottom = (int) ((f16 * f18) + i6);
    }
}
