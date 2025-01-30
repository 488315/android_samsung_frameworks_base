package com.android.server.location.nsflp;

import android.location.GnssStatus;
import android.location.INSLocationManager;
import android.location.Location;
import android.location.LocationConstants;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.android.server.ServiceThread;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;

/* loaded from: classes2.dex */
public class NSConnectionHelper {
    public String mBdmsgFormatMessage;
    public boolean mHasNsflpFeature;
    public ServiceThread mNsflpThread;
    public INSLocationManager mMonitorService = null;
    public Handler mHandler = null;

    public NSConnectionHelper() {
        Log.i("NSConnectionHelper", "constructor");
    }

    public void setFeature(boolean z) {
        this.mHasNsflpFeature = z;
    }

    public void onServiceConnected(INSLocationManager iNSLocationManager) {
        if (this.mNsflpThread == null) {
            this.mNsflpThread = new ServiceThread("NsflpThread", 0, true);
        }
        if (this.mHandler == null) {
            this.mNsflpThread.start();
            this.mHandler = new Handler(this.mNsflpThread.getLooper());
        }
        this.mMonitorService = iNSLocationManager;
    }

    public void onServiceDisconnected() {
        this.mMonitorService = null;
        this.mNsflpThread.quit();
        this.mNsflpThread = null;
        this.mHandler = null;
    }

    public void onStateUpdated(final LocationConstants.STATE_TYPE state_type, final Bundle bundle) {
        Handler handler;
        if (this.mHasNsflpFeature && (handler = this.mHandler) != null) {
            handler.post(new Runnable() { // from class: com.android.server.location.nsflp.NSConnectionHelper$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    NSConnectionHelper.this.lambda$onStateUpdated$0(state_type, bundle);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onStateUpdated$0(LocationConstants.STATE_TYPE state_type, Bundle bundle) {
        try {
            INSLocationManager iNSLocationManager = this.mMonitorService;
            if (iNSLocationManager != null) {
                iNSLocationManager.onStateUpdated(state_type, bundle);
            }
        } catch (Exception e) {
            Log.e("NSConnectionHelper", e.toString());
        }
    }

    public void onPassiveLocationReported(final Location location) {
        Handler handler;
        if (!this.mHasNsflpFeature || (handler = this.mHandler) == null || location == null) {
            return;
        }
        handler.post(new Runnable() { // from class: com.android.server.location.nsflp.NSConnectionHelper$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                NSConnectionHelper.this.lambda$onPassiveLocationReported$1(location);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onPassiveLocationReported$1(Location location) {
        try {
            INSLocationManager iNSLocationManager = this.mMonitorService;
            if (iNSLocationManager != null) {
                iNSLocationManager.onPassiveLocationReported(location);
            }
        } catch (Exception e) {
            Log.e("NSConnectionHelper", e.toString());
        }
    }

    public void onGnssEngineStatusUpdated(final boolean z) {
        Handler handler;
        if (this.mHasNsflpFeature && (handler = this.mHandler) != null) {
            handler.post(new Runnable() { // from class: com.android.server.location.nsflp.NSConnectionHelper$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    NSConnectionHelper.this.lambda$onGnssEngineStatusUpdated$2(z);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onGnssEngineStatusUpdated$2(boolean z) {
        try {
            INSLocationManager iNSLocationManager = this.mMonitorService;
            if (iNSLocationManager != null) {
                iNSLocationManager.onGnssEngineStatusUpdated(z);
            }
        } catch (Exception e) {
            Log.e("NSConnectionHelper", e.toString());
        }
    }

    public void onSatelliteStatusUpdated(final GnssStatus gnssStatus) {
        Handler handler;
        if (!this.mHasNsflpFeature || (handler = this.mHandler) == null || gnssStatus == null) {
            return;
        }
        handler.post(new Runnable() { // from class: com.android.server.location.nsflp.NSConnectionHelper$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                NSConnectionHelper.this.lambda$onSatelliteStatusUpdated$3(gnssStatus);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSatelliteStatusUpdated$3(GnssStatus gnssStatus) {
        try {
            INSLocationManager iNSLocationManager = this.mMonitorService;
            if (iNSLocationManager != null) {
                iNSLocationManager.onSatelliteStatusUpdated(gnssStatus);
            }
        } catch (Exception e) {
            Log.e("NSConnectionHelper", e.toString());
        }
    }

    public void onGnssEventUpdated(final String str) {
        if (this.mHasNsflpFeature) {
            Handler handler = this.mHandler;
            if (handler != null) {
                handler.post(new Runnable() { // from class: com.android.server.location.nsflp.NSConnectionHelper$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        NSConnectionHelper.this.lambda$onGnssEventUpdated$4(str);
                    }
                });
            } else if (this.mBdmsgFormatMessage == null && isBdmsgFormatMessage(str)) {
                this.mBdmsgFormatMessage = str;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onGnssEventUpdated$4(String str) {
        try {
            INSLocationManager iNSLocationManager = this.mMonitorService;
            if (iNSLocationManager != null) {
                iNSLocationManager.onGnssEventUpdated(str);
            }
        } catch (Exception e) {
            Log.e("NSConnectionHelper", e.toString());
        }
    }

    public final boolean isBdmsgFormatMessage(String str) {
        if (str == null) {
            return false;
        }
        String[] split = str.replaceAll(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE, "").split("[,*]");
        return split.length >= 1 && "FORMAT_MSG".equals(split[1]);
    }

    public void onMessageUpdated(final Message message) {
        Handler handler;
        if (this.mHasNsflpFeature && (handler = this.mHandler) != null) {
            handler.post(new Runnable() { // from class: com.android.server.location.nsflp.NSConnectionHelper$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    NSConnectionHelper.this.lambda$onMessageUpdated$5(message);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onMessageUpdated$5(Message message) {
        try {
            INSLocationManager iNSLocationManager = this.mMonitorService;
            if (iNSLocationManager != null) {
                iNSLocationManager.onMessageUpdated(message);
            }
        } catch (Exception e) {
            Log.e("NSConnectionHelper", e.toString());
        }
    }

    public void sendSupportedBdmsgFormat() {
        String str = this.mBdmsgFormatMessage;
        if (str != null) {
            onGnssEventUpdated(str);
        }
    }
}
