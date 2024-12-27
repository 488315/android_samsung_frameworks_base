package com.android.server.location.gnss;

import android.content.IntentFilter;
import android.location.LocationManager;
import android.location.LocationRequest;

import com.android.internal.util.ConcurrentUtils;

import java.util.Objects;

public final /* synthetic */ class GnssLocationProvider$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ GnssLocationProvider f$0;

    public /* synthetic */ GnssLocationProvider$$ExternalSyntheticLambda0(
            GnssLocationProvider gnssLocationProvider, int i) {
        this.$r8$classId = i;
        this.f$0 = gnssLocationProvider;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        GnssLocationProvider gnssLocationProvider = this.f$0;
        switch (i) {
            case 0:
                boolean hasOnDemandTime =
                        gnssLocationProvider.mGnssNative.mCapabilities.hasOnDemandTime();
                TimeDetectorNetworkTimeHelper timeDetectorNetworkTimeHelper =
                        gnssLocationProvider.mNetworkTimeHelper;
                synchronized (timeDetectorNetworkTimeHelper) {
                    timeDetectorNetworkTimeHelper.mPeriodicTimeInjectionEnabled = hasOnDemandTime;
                    if (!hasOnDemandTime) {
                        TimeDetectorNetworkTimeHelper.EnvironmentImpl environmentImpl =
                                timeDetectorNetworkTimeHelper.mEnvironment;
                        synchronized (environmentImpl) {
                            environmentImpl.mHandler.removeCallbacksAndMessages(
                                    environmentImpl.mScheduledRunnableToken);
                        }
                    }
                    timeDetectorNetworkTimeHelper.mEnvironment.requestImmediateTimeQueryCallback(
                            timeDetectorNetworkTimeHelper,
                            "setPeriodicTimeInjectionMode(" + hasOnDemandTime + ")");
                }
                if (hasOnDemandTime) {
                    gnssLocationProvider.demandUtcTimeInjection();
                }
                gnssLocationProvider.restartLocationRequest();
                return;
            case 1:
                if (gnssLocationProvider.mGnssNative.isGnssVisibilityControlSupported()) {
                    gnssLocationProvider.mGnssVisibilityControl =
                            new GnssVisibilityControl(
                                    gnssLocationProvider.mContext,
                                    gnssLocationProvider.mHandler.getLooper(),
                                    gnssLocationProvider.mNIHandler);
                }
                gnssLocationProvider.reloadGpsProperties();
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.telephony.action.CARRIER_CONFIG_CHANGED");
                intentFilter.addAction(
                        "android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED");
                gnssLocationProvider.mContext.registerReceiver(
                        gnssLocationProvider.mIntentReceiver,
                        intentFilter,
                        null,
                        gnssLocationProvider.mHandler);
                gnssLocationProvider.mNetworkConnectivityHandler.registerNetworkCallbacks();
                LocationManager locationManager =
                        (LocationManager)
                                gnssLocationProvider.mContext.getSystemService(
                                        LocationManager.class);
                Objects.requireNonNull(locationManager);
                if (locationManager.getAllProviders().contains("network")) {
                    locationManager.requestLocationUpdates(
                            "network",
                            new LocationRequest.Builder(Long.MAX_VALUE)
                                    .setMinUpdateIntervalMillis(0L)
                                    .setHiddenFromAppOps(true)
                                    .build(),
                            ConcurrentUtils.DIRECT_EXECUTOR,
                            new GnssLocationProvider$$ExternalSyntheticLambda23(
                                    (GnssLocationProviderSec) gnssLocationProvider, 1));
                }
                gnssLocationProvider.updateEnabled();
                synchronized (gnssLocationProvider.mLock) {
                    gnssLocationProvider.mInitialized = true;
                }
                return;
            case 2:
                gnssLocationProvider.handleDownloadPsdsData(1);
                return;
            case 3:
                gnssLocationProvider.gnssConfigurationUpdateSec("XTRA_THROTTLE=0");
                return;
            default:
                gnssLocationProvider.updateEnabled();
                return;
        }
    }
}
