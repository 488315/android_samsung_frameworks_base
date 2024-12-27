package com.android.server.location.gnss.sec;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.location.gnss.GnssLocationProviderSec;
import com.android.server.location.gnss.hal.GnssNative;

import java.util.HashMap;
import java.util.Map;

public final class SuplInitHandler {
    public static int mSubIdForSuplNi = -1;
    public final CarrierConfig mCarrierConfig;
    public final Context mContext;
    public final AnonymousClass1 mEmergencyNetworkConnectivityCallback;
    public final GnssNative mGnssNative;
    public final GnssLocationProviderSec mGnssProvider;
    public final GnssVendorConfig mGnssVendorConfig;
    public boolean mIsOpenUdpPort;
    public boolean mIsUdpListen;
    public final TelephonyManager mTelephonyManager;
    public final boolean[] mImsRegistered = {false, false, false};
    public boolean mNiSessionStarted = false;
    public boolean isEmergencyVowifiRegistration = false;

    public SuplInitHandler(
            Context context,
            GnssLocationProviderSec gnssLocationProviderSec,
            GnssNative gnssNative) {
        ConnectivityManager.NetworkCallback networkCallback =
                new ConnectivityManager
                        .NetworkCallback() { // from class:
                                             // com.android.server.location.gnss.sec.SuplInitHandler.1
                    @Override // android.net.ConnectivityManager.NetworkCallback
                    public final void onAvailable(Network network) {
                        Log.d(
                                "SuplInitHandler",
                                "mEmergencyNetworkConnectivityCallback : onAvailable");
                        SuplInitHandler suplInitHandler = SuplInitHandler.this;
                        if (suplInitHandler.mIsUdpListen) {
                            return;
                        }
                        suplInitHandler.mIsUdpListen = true;
                        if (suplInitHandler.mIsOpenUdpPort) {
                            Log.d("SuplInitHandler", "UDP port is already opened.");
                        } else {
                            Log.d("SuplInitHandler", "start UDP socket");
                            new Thread(
                                            new SuplInitHandler$$ExternalSyntheticLambda0(
                                                    suplInitHandler))
                                    .start();
                        }
                    }

                    @Override // android.net.ConnectivityManager.NetworkCallback
                    public final void onLost(Network network) {
                        Log.d("SuplInitHandler", "mEmergencyNetworkConnectivityCallback : onLost");
                        SuplInitHandler.this.mIsUdpListen = false;
                    }

                    @Override // android.net.ConnectivityManager.NetworkCallback
                    public final void onUnavailable() {
                        Log.d(
                                "SuplInitHandler",
                                "mEmergencyNetworkConnectivityCallback : onUnavailable");
                        SuplInitHandler.this.mIsUdpListen = false;
                    }
                };
        this.mContext = context;
        this.mGnssNative = gnssNative;
        this.mGnssProvider = gnssLocationProviderSec;
        GnssVendorConfig gnssVendorConfig = GnssVendorConfig.getInstance();
        this.mGnssVendorConfig = gnssVendorConfig;
        CarrierConfig carrierConfig = CarrierConfig.getInstance();
        this.mCarrierConfig = carrierConfig;
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService("connectivity");
        this.mTelephonyManager = (TelephonyManager) context.getSystemService("phone");
        gnssVendorConfig.getClass();
        if (GnssVendorConfig.isIzatServiceEnabled()) {
            return;
        }
        if (carrierConfig.mCarrier == CarrierConfig.Carrier.JPN_KDI
                || carrierConfig.isUsaVerizon()) {
            Log.d("SuplInitHandler", "register EmergencyNetworkConnectivityCallback");
            NetworkRequest.Builder builder = new NetworkRequest.Builder();
            builder.addCapability(10);
            connectivityManager.registerNetworkCallback(builder.build(), networkCallback);
        }
    }

    public static StringBuilder convertHashMapToStringBuilder(HashMap hashMap) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry entry : hashMap.entrySet()) {
            String str = (String) entry.getKey();
            String str2 = (String) entry.getValue();
            Log.d("SuplInitHandler", str + "=" + str2);
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(sb, str, "=", str2, "\n");
        }
        return sb;
    }

    public final void handleSuplInit(Runnable runnable, int i) {
        CarrierConfig.Carrier carrier;
        this.mGnssVendorConfig.getClass();
        boolean isIzatServiceEnabled = GnssVendorConfig.isIzatServiceEnabled();
        CarrierConfig carrierConfig = this.mCarrierConfig;
        if (isIzatServiceEnabled) {
            if (carrierConfig.isKoreaMarket()) {
                Log.d("SuplInitHandler", "handleSuplInit : slotId = " + i);
                this.mGnssNative.gnssConfigurationUpdateSec("NI_SUPL_SLOTID=" + i);
                runnable.run();
                return;
            }
            if (i < 0 || i > 2) {
                NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                        i, "SUPL INIT is rejected, slotId = ", "SuplInitHandler");
                return;
            } else {
                if (this.mImsRegistered[i]) {
                    runnable.run();
                    return;
                }
                return;
            }
        }
        StringBuilder sb = new StringBuilder("isSuplNiAvailable(), mGpsEnabled = ");
        GnssLocationProviderSec gnssLocationProviderSec = this.mGnssProvider;
        sb.append(gnssLocationProviderSec.isGpsEnabled());
        Log.d("SuplInitHandler", sb.toString());
        if (gnssLocationProviderSec.isGpsEnabled()
                || (carrier = carrierConfig.mCarrier) == CarrierConfig.Carrier.KOR_SKT
                || carrier == CarrierConfig.Carrier.KOR_KTT
                || carrier == CarrierConfig.Carrier.KOR_LGT
                || carrier == CarrierConfig.Carrier.USA_ATT
                || carrier == CarrierConfig.Carrier.USA_TMO
                || carrier == CarrierConfig.Carrier.USA_XAU
                || carrier == CarrierConfig.Carrier.USA_XAR
                || carrier == CarrierConfig.Carrier.USA_XAG
                || carrier == CarrierConfig.Carrier.USA_XAA
                || carrier == CarrierConfig.Carrier.USA_AIO
                || carrier == CarrierConfig.Carrier.USA_TFA
                || carrier == CarrierConfig.Carrier.USA_TFC
                || carrier == CarrierConfig.Carrier.USA_TFO
                || carrier == CarrierConfig.Carrier.USA_TMK
                || carrier == CarrierConfig.Carrier.USA_DSH
                || carrier == CarrierConfig.Carrier.USA_DSA
                || carrier == CarrierConfig.Carrier.USA_SPR
                || carrier == CarrierConfig.Carrier.ARG_UFN
                || carrier == CarrierConfig.Carrier.MEX_IUS
                || carrier == CarrierConfig.Carrier.MEX_MNX
                || carrier == CarrierConfig.Carrier.MEX_UNE
                || carrier == CarrierConfig.Carrier.PER_PEO
                || carrier == CarrierConfig.Carrier.PER_PNT
                || carrier == CarrierConfig.Carrier.PER_PET
                || carrier == CarrierConfig.Carrier.JPN_RKT
                || carrier == CarrierConfig.Carrier.JPN_SBM) {
            runnable.run();
        }
    }

    public final void updateSuplConfigurationForKorNi() {
        Log.d("SuplInitHandler", "updateSuplConfigurationForNI()");
        CarrierConfig carrierConfig = this.mCarrierConfig;
        boolean isKoreaSktSim = carrierConfig.isKoreaSktSim();
        GnssNative gnssNative = this.mGnssNative;
        if (isKoreaSktSim) {
            gnssNative.gnssConfigurationUpdateSec(
                    convertHashMapToStringBuilder(carrierConfig.getConfigMap()).toString());
            this.mGnssProvider.setSktSuplVer();
        } else if ("45008".equals(carrierConfig.mSimOperator)) {
            gnssNative.gnssConfigurationUpdateSec(
                    convertHashMapToStringBuilder(carrierConfig.getConfigMap()).toString());
            this.mGnssProvider.updateSuplServerConfiguration(3, 7275, 0, 0, 15, "221.148.242.107");
        } else if ("45006".equals(carrierConfig.mSimOperator)) {
            gnssNative.gnssConfigurationUpdateSec(
                    convertHashMapToStringBuilder(carrierConfig.getConfigMap()).toString());
            this.mGnssProvider.updateSuplServerConfiguration(
                    3, 7275, 1, 0, 15, "e-slp.uplus.co.kr");
        }
        this.mGnssVendorConfig.getClass();
        if (GnssVendorConfig.isIzatServiceEnabled()) {
            return;
        }
        if (carrierConfig.isKoreaSktSim() || "45008".equals(carrierConfig.mSimOperator)) {
            gnssNative.gnssConfigurationUpdateSec("ENABLE_SUPL_AGNSS_BEIDOU=TRUE");
            gnssNative.gnssConfigurationUpdateSec("ENABLE_SUPL_AGNSS_GALILEO=TRUE");
        } else if ("LGT".equals(carrierConfig.mSalesCode)
                || "LUC".equals(carrierConfig.mSalesCode)
                || "LUO".equals(carrierConfig.mSalesCode)
                || "45006".equals(carrierConfig.mSimOperator)) {
            gnssNative.gnssConfigurationUpdateSec("ENABLE_SUPL_AGNSS_BEIDOU=FALSE");
            gnssNative.gnssConfigurationUpdateSec("ENABLE_SUPL_AGNSS_GALILEO=FALSE");
        }
    }
}
