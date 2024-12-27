package com.android.server.location.gnss;

import android.location.Location;
import android.location.LocationListener;
import android.util.Log;
import com.android.server.location.gnss.sec.GnssVendorConfig;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class GnssLocationProvider$$ExternalSyntheticLambda23 implements LocationListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ GnssLocationProvider f$0;

    public /* synthetic */ GnssLocationProvider$$ExternalSyntheticLambda23(GnssLocationProvider gnssLocationProvider, int i) {
        this.$r8$classId = i;
        this.f$0 = gnssLocationProvider;
    }

    @Override // android.location.LocationListener
    public final void onLocationChanged(Location location) {
        int i = this.$r8$classId;
        GnssLocationProvider gnssLocationProvider = this.f$0;
        gnssLocationProvider.getClass();
        switch (i) {
            case 0:
                Log.d("GnssLocationProvider", "injectBestLocation: ");
                if (!location.isMock()) {
                    gnssLocationProvider.mGnssVendorConfig.getClass();
                    if (!GnssVendorConfig.isIzatServiceEnabled() && gnssLocationProvider.isEquipmentTestModeEnabled()) {
                        Log.w("GnssLocationProvider", "skip injectBestLocation in Equipment Test Mode");
                        break;
                    } else {
                        gnssLocationProvider.changeNlpAccuracyInForce(location);
                        gnssLocationProvider.mGnssNative.injectBestLocation(location);
                        break;
                    }
                }
                break;
            default:
                if (!location.isMock()) {
                    gnssLocationProvider.mGnssVendorConfig.getClass();
                    if (!GnssVendorConfig.isIzatServiceEnabled() || !gnssLocationProvider.mNIHandler.getInEmergency()) {
                        if (!GnssVendorConfig.isIzatServiceEnabled() && gnssLocationProvider.isEquipmentTestModeEnabled()) {
                            Log.w("GnssLocationProvider", "skip injectLocation in Equipment Test Mode");
                            break;
                        } else {
                            gnssLocationProvider.changeNlpAccuracyInForce(location);
                            gnssLocationProvider.mGnssNative.injectLocation(location);
                            break;
                        }
                    }
                }
                break;
        }
    }
}
