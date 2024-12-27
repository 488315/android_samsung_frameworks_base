package com.samsung.android.biometrics.app.setting.fingerprint.enroll;

import android.content.Context;
import android.hardware.fingerprint.IFingerprintService;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;

import com.samsung.android.biometrics.app.setting.Utils;

public final class FingerprintEnrollSensorHelper {
    public final int bottomMargin;
    public final int foldTopMargin;
    public final int height;
    public final String[] mSensorInfo;
    public final int offset;
    public final int topMargin;
    public final int width;

    public FingerprintEnrollSensorHelper(Context context) {
        this.bottomMargin = 0;
        this.topMargin = 0;
        this.foldTopMargin = 0;
        this.offset = 0;
        this.width = 0;
        this.height = 0;
        if (context == null) {
            throw new IllegalArgumentException("context is null");
        }
        boolean z = Utils.Config.FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE;
        if (z || Utils.Config.FP_FEATURE_SENSOR_IS_SIDE) {
            IFingerprintService asInterface =
                    IFingerprintService.Stub.asInterface(ServiceManager.getService("fingerprint"));
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            try {
                Bundle bundle = new Bundle();
                asInterface.semGetSensorData(bundle);
                String[] stringArray = bundle.getStringArray("sem_area");
                this.mSensorInfo = stringArray;
                if (stringArray != null) {
                    if (z) {
                        this.bottomMargin =
                                getPixelFromMilli(displayMetrics, Float.parseFloat(stringArray[2]));
                        this.offset =
                                getPixelFromMilli(
                                        displayMetrics, Float.parseFloat(this.mSensorInfo[3]));
                        this.width =
                                getPixelFromMilli(
                                        displayMetrics, Float.parseFloat(this.mSensorInfo[0]));
                        this.height =
                                getPixelFromMilli(
                                        displayMetrics, Float.parseFloat(this.mSensorInfo[1]));
                    } else if (Utils.Config.FP_FEATURE_SENSOR_IS_SIDE) {
                        this.topMargin =
                                getPixelFromMilli(displayMetrics, Float.parseFloat(stringArray[0]));
                        this.height =
                                getPixelFromMilli(
                                        displayMetrics, Float.parseFloat(this.mSensorInfo[1]));
                        this.foldTopMargin =
                                getPixelFromMilli(
                                        displayMetrics, Float.parseFloat(this.mSensorInfo[2]));
                    }
                }
            } catch (RemoteException e) {
                Log.w("BSS_SensorHelper", "FingerprintEnrollSensorHelper: " + e.getMessage());
            }
        }
    }

    public static int getPixelFromMilli(DisplayMetrics displayMetrics, float f) {
        try {
            return (int) TypedValue.applyDimension(5, f, displayMetrics);
        } catch (NumberFormatException e) {
            Log.e("BSS_SensorHelper", e.getMessage() + " (" + f + ")");
            return 0;
        }
    }
}
