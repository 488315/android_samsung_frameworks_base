package com.android.server.location.nsflp;

import android.location.GnssStatus;
import android.location.INSLocationManager;
import android.location.Location;
import android.os.Message;
import android.util.Log;

public final /* synthetic */ class NSConnectionHelper$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NSConnectionHelper f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ NSConnectionHelper$$ExternalSyntheticLambda0(
            NSConnectionHelper nSConnectionHelper, Location location) {
        this.$r8$classId = 1;
        this.f$0 = nSConnectionHelper;
        this.f$1 = location;
    }

    public /* synthetic */ NSConnectionHelper$$ExternalSyntheticLambda0(
            NSConnectionHelper nSConnectionHelper, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = nSConnectionHelper;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                NSConnectionHelper nSConnectionHelper = this.f$0;
                GnssStatus gnssStatus = (GnssStatus) this.f$1;
                nSConnectionHelper.getClass();
                try {
                    INSLocationManager iNSLocationManager = nSConnectionHelper.mMonitorService;
                    if (iNSLocationManager != null) {
                        iNSLocationManager.onSatelliteStatusUpdated(gnssStatus);
                        break;
                    }
                } catch (Exception e) {
                    Log.e("NSConnectionHelper", e.toString());
                    return;
                }
                break;
            case 1:
                NSConnectionHelper nSConnectionHelper2 = this.f$0;
                Location location = (Location) this.f$1;
                nSConnectionHelper2.getClass();
                try {
                    INSLocationManager iNSLocationManager2 = nSConnectionHelper2.mMonitorService;
                    if (iNSLocationManager2 != null) {
                        iNSLocationManager2.onPassiveLocationReported(location);
                        break;
                    }
                } catch (Exception e2) {
                    Log.e("NSConnectionHelper", e2.toString());
                    return;
                }
                break;
            case 2:
                NSConnectionHelper nSConnectionHelper3 = this.f$0;
                String str = (String) this.f$1;
                nSConnectionHelper3.getClass();
                try {
                    INSLocationManager iNSLocationManager3 = nSConnectionHelper3.mMonitorService;
                    if (iNSLocationManager3 != null) {
                        iNSLocationManager3.onGnssEventUpdated(str);
                        break;
                    }
                } catch (Exception e3) {
                    Log.e("NSConnectionHelper", e3.toString());
                    return;
                }
                break;
            default:
                NSConnectionHelper nSConnectionHelper4 = this.f$0;
                Message message = (Message) this.f$1;
                nSConnectionHelper4.getClass();
                try {
                    INSLocationManager iNSLocationManager4 = nSConnectionHelper4.mMonitorService;
                    if (iNSLocationManager4 != null) {
                        iNSLocationManager4.onMessageUpdated(message);
                        break;
                    }
                } catch (Exception e4) {
                    Log.e("NSConnectionHelper", e4.toString());
                }
                break;
        }
    }
}
