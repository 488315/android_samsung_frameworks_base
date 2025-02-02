package com.samsung.android.biometrics.app.setting.fingerprint;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import com.samsung.android.biometrics.app.setting.DisplayStateManager$Injector$$ExternalSyntheticOutline0;
import com.samsung.android.biometrics.app.setting.Utils;
import java.util.Arrays;

/* loaded from: classes.dex */
public final class UdfpsInfo {
    private float mBrightnessNitForOptical;
    private String mCalibrationLightColor;
    private final Context mContext;
    private boolean mIsNaviHide;
    private String mLightColor;
    private float mNitsForOptical;
    private int mSensorAreaHeight;
    private final String[] mSensorAreaSizeInfo;
    private int mSensorAreaWidth;
    private int mSensorImageSize;
    private int mSensorMarginBottom;
    private int mSensorMarginLeft;

    public UdfpsInfo(Context context, Bundle bundle) {
        this.mLightColor = "00ff00";
        this.mContext = context;
        this.mSensorAreaSizeInfo = bundle.getStringArray("sem_area");
        if (Utils.Config.FP_FEATURE_SENSOR_IS_OPTICAL) {
            String lowerCase = bundle.getString("nits", "00ff00").toLowerCase();
            this.mLightColor = lowerCase;
            this.mCalibrationLightColor = lowerCase;
            this.mBrightnessNitForOptical = bundle.getFloat("brightness", 319.0f);
            this.mNitsForOptical = bundle.getFloat("lightColor", 525.0f);
        }
        updateSensorInfo();
    }

    public final int getAreaHeight() {
        return this.mSensorAreaHeight;
    }

    public final int getAreaWidth() {
        return this.mSensorAreaWidth;
    }

    public final float getBrightnessNitForOptical() {
        return this.mBrightnessNitForOptical;
    }

    public final String getCalibrationLightColor() {
        return this.mCalibrationLightColor;
    }

    public final Point getCenterPointInPortraitMode(boolean z) {
        Point maximumWindowSize = Utils.getMaximumWindowSize(this.mContext);
        int i = maximumWindowSize.x;
        int i2 = maximumWindowSize.y;
        if (z) {
            i = i2;
            i2 = i;
        }
        Point point = new Point();
        point.x = (i / 2) - this.mSensorMarginLeft;
        point.y = i2 - ((this.mSensorAreaHeight / 2) + this.mSensorMarginBottom);
        return point;
    }

    public final int getImageSize() {
        return this.mSensorImageSize;
    }

    public final String getLightColor() {
        return this.mLightColor;
    }

    public final int getMarginBottom() {
        return this.mSensorMarginBottom;
    }

    public final int getMarginLeft() {
        return this.mSensorMarginLeft;
    }

    public final float getNitsForOptical() {
        return this.mNitsForOptical;
    }

    public final Point getSensorImagePoint(Point point) {
        Point point2 = new Point();
        Point point3 = new Point();
        int i = this.mSensorImageSize / 2;
        int i2 = i - this.mSensorMarginLeft;
        point3.x = i2;
        int i3 = (this.mSensorAreaHeight / 2) + this.mSensorMarginBottom + i;
        point3.y = i3;
        point2.x = (point.x / 2) - i2;
        point2.y = point.y - i3;
        return point2;
    }

    public final boolean isNaviBarHide() {
        return this.mIsNaviHide;
    }

    public final void setCalibrationLightColor(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.mCalibrationLightColor = str.toLowerCase();
    }

    public final String toString() {
        return "SensorInfo{raw=" + Arrays.toString(this.mSensorAreaSizeInfo) + ", Width=" + this.mSensorAreaWidth + ", Height=" + this.mSensorAreaHeight + ", Bottom=" + this.mSensorMarginBottom + ", Left=" + this.mSensorMarginLeft + ", Size=" + this.mSensorImageSize + ", NaviHide=" + this.mIsNaviHide + ", LightColor=" + this.mLightColor + ", B=" + this.mBrightnessNitForOptical + ", N=" + this.mNitsForOptical + '}';
    }

    public final void updateSensorInfo() {
        String[] strArr = this.mSensorAreaSizeInfo;
        if (strArr == null) {
            return;
        }
        DisplayMetrics displayMetrics = Utils.getDisplayMetrics(this.mContext);
        boolean z = true;
        try {
            this.mSensorAreaHeight = (int) TypedValue.applyDimension(5, Float.parseFloat(strArr[1]), displayMetrics);
            this.mSensorAreaWidth = (int) TypedValue.applyDimension(5, Float.parseFloat(strArr[0]), displayMetrics);
            this.mSensorMarginBottom = (int) TypedValue.applyDimension(5, Float.parseFloat(strArr[2]), displayMetrics);
            this.mSensorMarginLeft = (int) TypedValue.applyDimension(5, Float.parseFloat(strArr[3]), displayMetrics);
            this.mSensorImageSize = (int) TypedValue.applyDimension(5, Float.parseFloat(strArr[4]), displayMetrics);
            if (this.mSensorMarginBottom >= ((int) TypedValue.applyDimension(5, Float.parseFloat(Utils.Config.SENSOR_BOTTOM_MARGIN_BOUNDARY_RECENT_HOME_KEY), displayMetrics))) {
                z = false;
            }
            this.mIsNaviHide = z;
        } catch (Exception e) {
            DisplayStateManager$Injector$$ExternalSyntheticOutline0.m9m(e, new StringBuilder("updateSensorInfo: "), "BSS_UdfpsInfo");
        }
        if (Utils.DEBUG) {
            Log.i("BSS_UdfpsInfo", "updateSensorInfo: " + toString());
        }
    }

    public final Point getSensorImagePoint(Point point, int i) {
        Point point2 = new Point();
        Point point3 = new Point();
        int i2 = this.mSensorImageSize;
        int i3 = i2 / 2;
        int i4 = i3 - this.mSensorMarginLeft;
        point3.x = i4;
        int i5 = (this.mSensorAreaHeight / 2) + this.mSensorMarginBottom + i3;
        point3.y = i5;
        if (i == 0) {
            point2.x = (point.x / 2) - i4;
            point2.y = point.y - i5;
        } else if (i == 1) {
            point2.x = point.x - i5;
            point2.y = ((point.y / 2) + point3.x) - i2;
        } else if (i == 2) {
            point2.x = ((point.x / 2) + i4) - i2;
            point2.y = i5 - i2;
        } else if (i == 3) {
            point2.x = i5 - i2;
            point2.y = (point.y / 2) - point3.x;
        }
        return point2;
    }
}
