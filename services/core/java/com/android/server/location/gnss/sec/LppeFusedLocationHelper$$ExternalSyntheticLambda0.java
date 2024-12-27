package com.android.server.location.gnss.sec;

import android.util.Log;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class LppeFusedLocationHelper$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ LppeFusedLocationHelper f$0;

    public /* synthetic */ LppeFusedLocationHelper$$ExternalSyntheticLambda0(
            LppeFusedLocationHelper lppeFusedLocationHelper, int i) {
        this.$r8$classId = i;
        this.f$0 = lppeFusedLocationHelper;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        LppeFusedLocationHelper lppeFusedLocationHelper = this.f$0;
        switch (i) {
            case 0:
                if (lppeFusedLocationHelper.mIsWifiScanRequested) {
                    Log.d("LocationX", "wlan measurement is timeout");
                    lppeFusedLocationHelper.handleUpdateWlanError();
                    lppeFusedLocationHelper.mIsWifiScanRequested = false;
                    break;
                }
                break;
            case 1:
                if (lppeFusedLocationHelper.mIsUbpRequested) {
                    Log.d("LocationX", "UBP measurement is timeout");
                    Log.d("LocationX", "handleUpdateUBPError :  4");
                    lppeFusedLocationHelper.mGnssNative.injectUbpError(4);
                    lppeFusedLocationHelper.mSensorManager.unregisterListener(
                            lppeFusedLocationHelper.mSensorEventListener);
                    lppeFusedLocationHelper.mIsUbpRequested = false;
                    break;
                }
                break;
            default:
                if (lppeFusedLocationHelper.mIsFlpRequested) {
                    Log.d("LocationX", "FLP measurement is timeout");
                    Log.d("LocationX", "handleUpdateFLPError :  2");
                    lppeFusedLocationHelper.mGnssNative.injectFlpError(2);
                    lppeFusedLocationHelper.mIsFlpRequested = false;
                    break;
                }
                break;
        }
    }
}
