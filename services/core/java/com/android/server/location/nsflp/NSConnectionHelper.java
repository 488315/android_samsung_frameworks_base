package com.android.server.location.nsflp;

import android.location.INSLocationManager;
import android.location.LocationConstants;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public final class NSConnectionHelper {
    public String mBdmsgFormatMessage;
    public Handler mHandler;
    public boolean mHasNsflpFeature;
    public INSLocationManager mMonitorService;

    public final void onGnssEventUpdated(String str) {
        if (this.mHasNsflpFeature) {
            Handler handler = this.mHandler;
            if (handler != null) {
                handler.post(new NSConnectionHelper$$ExternalSyntheticLambda0(this, str, 2));
                return;
            }
            if (this.mBdmsgFormatMessage != null || str == null) {
                return;
            }
            String[] split = str.replaceAll("\n", "").split("[,*]");
            if (split.length < 1 || !"FORMAT_MSG".equals(split[1])) {
                return;
            }
            this.mBdmsgFormatMessage = str;
        }
    }

    public final void onStateUpdated(
            final LocationConstants.STATE_TYPE state_type, final Bundle bundle) {
        Handler handler;
        if (this.mHasNsflpFeature && (handler = this.mHandler) != null) {
            handler.post(
                    new Runnable() { // from class:
                                     // com.android.server.location.nsflp.NSConnectionHelper$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            NSConnectionHelper nSConnectionHelper = NSConnectionHelper.this;
                            LocationConstants.STATE_TYPE state_type2 = state_type;
                            Bundle bundle2 = bundle;
                            nSConnectionHelper.getClass();
                            try {
                                INSLocationManager iNSLocationManager =
                                        nSConnectionHelper.mMonitorService;
                                if (iNSLocationManager != null) {
                                    iNSLocationManager.onStateUpdated(state_type2, bundle2);
                                }
                            } catch (Exception e) {
                                Log.e("NSConnectionHelper", e.toString());
                            }
                        }
                    });
        }
    }
}
