package com.samsung.android.biometrics.app.setting.fingerprint;

import android.content.Context;
import android.graphics.Point;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.hardware.fingerprint.IFingerprintService;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;

import com.samsung.android.biometrics.app.setting.FocusableWindow$$ExternalSyntheticOutline0;
import com.samsung.android.biometrics.app.setting.Utils;

import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
public final class FingerprintSensorInfo {
    public final float mBrightnessNitForOptical;
    public String mCalibrationLightColor;
    public final Context mContext;
    public final boolean mIsAnyUdfps;
    public boolean mIsNaviHide;
    public final boolean mIsOptical;
    public final boolean mIsUltrasonic;
    public final String mLightColor;
    public final float mNitsForOptical;
    public int mSensorAreaHeight;
    public final String[] mSensorAreaSizeInfo;
    public int mSensorAreaWidth;
    public int mSensorImageSize;
    public int mSensorMarginBottom;
    public int mSensorMarginLeft;

    public FingerprintSensorInfo(Context context, IFingerprintService iFingerprintService) {
        this.mLightColor = "00ff00";
        this.mContext = context;
        if (iFingerprintService != null) {
            Bundle bundle = new Bundle();
            try {
                iFingerprintService.semGetSensorData(bundle);
                List<FingerprintSensorPropertiesInternal> sensorPropertiesInternal =
                        iFingerprintService.getSensorPropertiesInternal(context.getOpPackageName());
                if (sensorPropertiesInternal != null) {
                    for (FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal :
                            sensorPropertiesInternal) {
                        if (fingerprintSensorPropertiesInternal.isUltrasonicType()) {
                            this.mIsUltrasonic = true;
                        }
                        if (fingerprintSensorPropertiesInternal.isOpticalType()) {
                            this.mIsOptical = true;
                        }
                        if (fingerprintSensorPropertiesInternal.isAnyUdfpsType()) {
                            this.mIsAnyUdfps = true;
                        }
                    }
                }
            } catch (RemoteException e) {
                Log.w("BSS_FingerprintSensorInfo", "FingerprintSensorInfo: " + e.getMessage());
            }
            this.mSensorAreaSizeInfo = bundle.getStringArray("sem_area");
            if (this.mIsOptical) {
                String lowerCase = bundle.getString("nits", "00ff00").toLowerCase();
                this.mLightColor = lowerCase;
                this.mCalibrationLightColor = lowerCase;
                this.mBrightnessNitForOptical = bundle.getFloat("brightness", 319.0f);
                this.mNitsForOptical = bundle.getFloat("lightColor", 525.0f);
            }
            updateSensorInfo();
        }
    }

    public final Point getSensorImagePoint$2() {
        Point point = new Point();
        if (this.mIsAnyUdfps) {
            int i = this.mSensorImageSize;
            point.x = (i / 2) - this.mSensorMarginLeft;
            int i2 = i / 2;
            point.y = i2 + (this.mSensorAreaHeight / 2) + this.mSensorMarginBottom;
        }
        return point;
    }

    public final String toString() {
        return "SensorInfo{raw="
                + Arrays.toString(this.mSensorAreaSizeInfo)
                + ", Width="
                + this.mSensorAreaWidth
                + ", Height="
                + this.mSensorAreaHeight
                + ", Bottom="
                + this.mSensorMarginBottom
                + ", Left="
                + this.mSensorMarginLeft
                + ", Size="
                + this.mSensorImageSize
                + ", NaviHide="
                + this.mIsNaviHide
                + ", LightColor="
                + this.mLightColor
                + ", B="
                + this.mBrightnessNitForOptical
                + ", N="
                + this.mNitsForOptical
                + '}';
    }

    public final void updateSensorInfo() {
        String[] strArr;
        if (!this.mIsAnyUdfps || (strArr = this.mSensorAreaSizeInfo) == null) {
            return;
        }
        DisplayMetrics displayMetrics = Utils.getDisplayMetrics(this.mContext);
        boolean z = true;
        try {
            this.mSensorAreaHeight =
                    (int) TypedValue.applyDimension(5, Float.parseFloat(strArr[1]), displayMetrics);
            this.mSensorAreaWidth =
                    (int) TypedValue.applyDimension(5, Float.parseFloat(strArr[0]), displayMetrics);
            this.mSensorMarginBottom =
                    (int) TypedValue.applyDimension(5, Float.parseFloat(strArr[2]), displayMetrics);
            this.mSensorMarginLeft =
                    (int) TypedValue.applyDimension(5, Float.parseFloat(strArr[3]), displayMetrics);
            this.mSensorImageSize =
                    (int) TypedValue.applyDimension(5, Float.parseFloat(strArr[4]), displayMetrics);
            if (this.mSensorMarginBottom
                    >= ((int)
                            TypedValue.applyDimension(
                                    5,
                                    Float.parseFloat(
                                            Utils.Config
                                                    .SENSOR_BOTTOM_MARGIN_BOUNDARY_RECENT_HOME_KEY),
                                    displayMetrics))) {
                z = false;
            }
            this.mIsNaviHide = z;
        } catch (Exception e) {
            FocusableWindow$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("updateSensorInfo: "), "BSS_FingerprintSensorInfo");
        }
        if (Utils.DEBUG) {
            Log.i("BSS_FingerprintSensorInfo", "updateSensorInfo: " + toString());
        }
    }
}
