package com.android.systemui.statusbar.policy;

import android.telephony.ServiceState;
import android.telephony.TelephonyCallback;
import android.util.Log;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.qs.tiles.AirplaneModeTile;
import com.samsung.android.feature.SemCarrierFeature;
import kotlin.Unit;

/* loaded from: classes3.dex */
public final class SatelliteModeObserverHelper$telephonyCallback$1 extends TelephonyCallback implements TelephonyCallback.CallAttributesListener, TelephonyCallback.ServiceStateListener, TelephonyCallback.CarrierRoamingNtnListener {
    public final /* synthetic */ SatelliteModeObserverHelper this$0;

    public SatelliteModeObserverHelper$telephonyCallback$1(SatelliteModeObserverHelper satelliteModeObserverHelper) {
        this.this$0 = satelliteModeObserverHelper;
    }

    public final void onCarrierRoamingNtnModeChanged(boolean z) {
        EmergencyButtonController$$ExternalSyntheticOutline0.m("onCarrierRoamingNtnModeChanged : ", "SatelliteModeObserver", z);
        SatelliteModeObserverHelper satelliteModeObserverHelper = this.this$0;
        synchronized (satelliteModeObserverHelper.esosListeners) {
            try {
                for (AirplaneModeTile.AnonymousClass2 anonymousClass2 : satelliteModeObserverHelper.esosListeners) {
                    boolean zIsSupportedESOS = satelliteModeObserverHelper.isSupportedESOS();
                    AirplaneModeTile airplaneModeTile = AirplaneModeTile.this;
                    airplaneModeTile.mIsSupportedEsos = zIsSupportedESOS;
                    airplaneModeTile.mIsSatelliteModeOn = (airplaneModeTile.mIsSatelliteEnabled || airplaneModeTile.mIsUsingTerrestrialNetwork) && zIsSupportedESOS;
                    airplaneModeTile.refreshState(null);
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // android.telephony.TelephonyCallback.ServiceStateListener
    public final void onServiceStateChanged(ServiceState serviceState) {
        Log.d("SatelliteModeObserver", "service state changed : " + serviceState);
        SatelliteModeObserverHelper satelliteModeObserverHelper = this.this$0;
        synchronized (satelliteModeObserverHelper.trtListeners) {
            try {
                for (SatelliteTrtListener satelliteTrtListener : satelliteModeObserverHelper.trtListeners) {
                    boolean z = false;
                    if ((SemCarrierFeature.getInstance().getBoolean(0, "CarrierFeature_Common_Support_Satellite", false, false) || SemCarrierFeature.getInstance().getBoolean(1, "CarrierFeature_Common_Support_Satellite", false, false)) && serviceState.isUsingNonTerrestrialNetwork()) {
                        z = true;
                    }
                    Log.d("SatelliteModeObserver", "isSatelliteNetworksOn(), isUsingTerrestrialNetwork: " + z);
                    satelliteTrtListener.onSatelliteTrtChanged(z);
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
