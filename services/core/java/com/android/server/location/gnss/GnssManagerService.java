package com.android.server.location.gnss;

import android.content.Context;
import android.content.Intent;
import android.hardware.location.GeofenceHardwareImpl;
import android.location.GnssCapabilities;
import android.os.Binder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.connectivity.GpsBatteryStats;
import android.util.Base64;
import android.util.IndentingPrintWriter;
import android.util.Log;

import com.android.internal.app.IBatteryStats;
import com.android.internal.location.nano.GnssLogsProto;
import com.android.server.location.LocationManagerService;
import com.android.server.location.gnss.hal.GnssNative;
import com.android.server.location.gnss.sec.SLocationProxy;
import com.android.server.location.injector.Injector;

import com.samsung.android.knox.custom.KnoxCustomManagerService;

import java.io.FileDescriptor;

public final class GnssManagerService {
    public final Context mContext;
    public final GnssAntennaInfoProvider mGnssAntennaInfoProvider;
    public final GnssGeofenceProxy mGnssGeofenceProxy;
    public final GnssLocationProviderSec mGnssLocationProvider;
    public final GnssMeasurementsProvider mGnssMeasurementsProvider;
    public final GnssMetrics mGnssMetrics;
    public final GnssNative mGnssNative;
    public final GnssNavigationMessageProvider mGnssNavigationMessageProvider;
    public final GnssNmeaProvider mGnssNmeaProvider;
    public final GnssStatusProvider mGnssStatusProvider;
    public final SLocationProxy mSLocationProxy;

    public final class GnssCapabilitiesHalModule implements GnssNative.BaseCallbacks {
        public GnssCapabilitiesHalModule(GnssNative gnssNative) {
            gnssNative.addBaseCallbacks(this);
        }

        @Override // com.android.server.location.gnss.hal.GnssNative.BaseCallbacks
        public final void onCapabilitiesChanged(GnssCapabilities gnssCapabilities) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                GnssManagerService.this.mContext.sendBroadcastAsUser(
                        new Intent("android.location.action.GNSS_CAPABILITIES_CHANGED")
                                .putExtra(
                                        "android.location.extra.GNSS_CAPABILITIES",
                                        gnssCapabilities)
                                .addFlags(1073741824)
                                .addFlags(268435456),
                        UserHandle.ALL);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @Override // com.android.server.location.gnss.hal.GnssNative.BaseCallbacks
        public final void onHalRestarted() {}
    }

    public final class GnssGeofenceHalModule implements GnssNative.GeofenceCallbacks {
        public GeofenceHardwareImpl mGeofenceHardwareImpl;

        public GnssGeofenceHalModule(GnssNative gnssNative) {
            gnssNative.setGeofenceCallbacks(this);
        }

        public static int translateGeofenceStatus(int i) {
            if (i == -149) {
                return 5;
            }
            if (i == 0) {
                return 0;
            }
            if (i == 100) {
                return 1;
            }
            switch (i) {
                case -103:
                    return 4;
                case -102:
                    return 3;
                case KnoxCustomManagerService.DOCK_SHORTCUT_CONTAINER_ID /* -101 */:
                    return 2;
                default:
                    return -1;
            }
        }

        public final synchronized GeofenceHardwareImpl getGeofenceHardware() {
            try {
                if (this.mGeofenceHardwareImpl == null) {
                    this.mGeofenceHardwareImpl =
                            GeofenceHardwareImpl.getInstance(GnssManagerService.this.mContext);
                }
            } catch (Throwable th) {
                throw th;
            }
            return this.mGeofenceHardwareImpl;
        }
    }

    public GnssManagerService(Context context, Injector injector, GnssNative gnssNative) {
        Context createAttributionContext = context.createAttributionContext("GnssService");
        this.mContext = createAttributionContext;
        this.mGnssNative = gnssNative;
        GnssMetrics gnssMetrics =
                new GnssMetrics(
                        createAttributionContext,
                        IBatteryStats.Stub.asInterface(ServiceManager.getService("batterystats")),
                        gnssNative);
        this.mGnssMetrics = gnssMetrics;
        this.mGnssLocationProvider =
                new GnssLocationProviderSec(
                        createAttributionContext, injector, gnssNative, gnssMetrics);
        this.mGnssStatusProvider = new GnssStatusProvider(injector, gnssNative);
        this.mGnssNmeaProvider = new GnssNmeaProvider(injector, gnssNative);
        this.mGnssMeasurementsProvider = new GnssMeasurementsProvider(injector, gnssNative);
        this.mGnssNavigationMessageProvider =
                new GnssNavigationMessageProvider(injector, gnssNative);
        GnssAntennaInfoProvider gnssAntennaInfoProvider = new GnssAntennaInfoProvider(gnssNative);
        this.mGnssAntennaInfoProvider = gnssAntennaInfoProvider;
        gnssAntennaInfoProvider.mNSLocationProviderHelper =
                ((LocationManagerService.SystemInjector) injector).mNSLocationProviderHelper;
        this.mGnssGeofenceProxy = new GnssGeofenceProxy(gnssNative);
        new GnssGeofenceHalModule(gnssNative);
        new GnssCapabilitiesHalModule(gnssNative);
        gnssNative.register();
        SLocationProxy sLocationProxy = new SLocationProxy();
        sLocationProxy.mSLocationService = null;
        this.mSLocationProxy = sLocationProxy;
    }

    public final void dump(
            FileDescriptor fileDescriptor,
            IndentingPrintWriter indentingPrintWriter,
            String[] strArr) {
        GpsBatteryStats gpsBatteryStats;
        if (strArr.length <= 0 || !strArr[0].equals("--gnssmetrics")) {
            indentingPrintWriter.println("Capabilities: " + this.mGnssNative.mCapabilities);
            indentingPrintWriter.println(
                    "GNSS Hardware Model Name: " + this.mGnssNative.mHardwareModelName);
            this.mGnssStatusProvider.getClass();
            indentingPrintWriter.println("Status Provider:");
            indentingPrintWriter.increaseIndent();
            this.mGnssStatusProvider.dump(fileDescriptor, indentingPrintWriter, strArr);
            indentingPrintWriter.decreaseIndent();
            if (this.mGnssMeasurementsProvider.mGnssNative.isMeasurementSupported()) {
                indentingPrintWriter.println("Measurements Provider:");
                indentingPrintWriter.increaseIndent();
                this.mGnssMeasurementsProvider.dump(fileDescriptor, indentingPrintWriter, strArr);
                indentingPrintWriter.decreaseIndent();
            }
            if (this.mGnssNavigationMessageProvider.mGnssNative
                    .isNavigationMessageCollectionSupported()) {
                indentingPrintWriter.println("Navigation Message Provider:");
                indentingPrintWriter.increaseIndent();
                this.mGnssNavigationMessageProvider.dump(
                        fileDescriptor, indentingPrintWriter, strArr);
                indentingPrintWriter.decreaseIndent();
            }
            if (this.mGnssAntennaInfoProvider.mGnssNative.isAntennaInfoSupported()) {
                indentingPrintWriter.println("Antenna Info Provider:");
                indentingPrintWriter.increaseIndent();
                indentingPrintWriter.println(
                        "Antenna Infos: " + this.mGnssAntennaInfoProvider.mAntennaInfos);
                this.mGnssAntennaInfoProvider.dump(fileDescriptor, indentingPrintWriter, strArr);
                indentingPrintWriter.decreaseIndent();
            }
            this.mGnssNmeaProvider.getClass();
            indentingPrintWriter.println("NMEA Provider:");
            indentingPrintWriter.increaseIndent();
            this.mGnssNmeaProvider.dump(fileDescriptor, indentingPrintWriter, strArr);
            indentingPrintWriter.decreaseIndent();
            GnssPowerStats gnssPowerStats = this.mGnssNative.mLastKnownPowerStats;
            if (gnssPowerStats != null) {
                indentingPrintWriter.println("Last Known Power Stats:");
                indentingPrintWriter.increaseIndent();
                gnssPowerStats.dump(
                        fileDescriptor,
                        indentingPrintWriter,
                        strArr,
                        this.mGnssNative.mCapabilities);
                indentingPrintWriter.decreaseIndent();
                return;
            }
            return;
        }
        GnssMetrics gnssMetrics = this.mGnssMetrics;
        gnssMetrics.getClass();
        GnssLogsProto.GnssLog gnssLog = new GnssLogsProto.GnssLog();
        GnssMetrics.Statistics statistics = gnssMetrics.mLocationFailureStatistics;
        if (statistics.getCount() > 0) {
            gnssLog.numLocationReportProcessed = statistics.getCount();
            gnssLog.percentageLocationFailure = (int) (statistics.getMean() * 100.0d);
        }
        GnssMetrics.Statistics statistics2 = gnssMetrics.mTimeToFirstFixSecStatistics;
        if (statistics2.getCount() > 0) {
            gnssLog.numTimeToFirstFixProcessed = statistics2.getCount();
            gnssLog.meanTimeToFirstFixSecs = (int) statistics2.getMean();
            gnssLog.standardDeviationTimeToFirstFixSecs = (int) statistics2.getStandardDeviation();
        }
        GnssMetrics.Statistics statistics3 = gnssMetrics.mPositionAccuracyMeterStatistics;
        if (statistics3.getCount() > 0) {
            gnssLog.numPositionAccuracyProcessed = statistics3.getCount();
            gnssLog.meanPositionAccuracyMeters = (int) statistics3.getMean();
            gnssLog.standardDeviationPositionAccuracyMeters =
                    (int) statistics3.getStandardDeviation();
        }
        GnssMetrics.Statistics statistics4 = gnssMetrics.mTopFourAverageCn0Statistics;
        if (statistics4.getCount() > 0) {
            gnssLog.numTopFourAverageCn0Processed = statistics4.getCount();
            gnssLog.meanTopFourAverageCn0DbHz = statistics4.getMean();
            gnssLog.standardDeviationTopFourAverageCn0DbHz = statistics4.getStandardDeviation();
        }
        int i = gnssMetrics.mNumSvStatus;
        if (i > 0) {
            gnssLog.numSvStatusProcessed = i;
        }
        int i2 = gnssMetrics.mNumL5SvStatus;
        if (i2 > 0) {
            gnssLog.numL5SvStatusProcessed = i2;
        }
        int i3 = gnssMetrics.mNumSvStatusUsedInFix;
        if (i3 > 0) {
            gnssLog.numSvStatusUsedInFix = i3;
        }
        int i4 = gnssMetrics.mNumL5SvStatusUsedInFix;
        if (i4 > 0) {
            gnssLog.numL5SvStatusUsedInFix = i4;
        }
        GnssMetrics.Statistics statistics5 = gnssMetrics.mTopFourAverageCn0StatisticsL5;
        if (statistics5.getCount() > 0) {
            gnssLog.numL5TopFourAverageCn0Processed = statistics5.getCount();
            gnssLog.meanL5TopFourAverageCn0DbHz = statistics5.getMean();
            gnssLog.standardDeviationL5TopFourAverageCn0DbHz = statistics5.getStandardDeviation();
        }
        GnssMetrics.GnssPowerMetrics gnssPowerMetrics = gnssMetrics.mGnssPowerMetrics;
        gnssPowerMetrics.getClass();
        GnssLogsProto.PowerMetrics powerMetrics = new GnssLogsProto.PowerMetrics();
        GnssMetrics.GnssPowerMetrics gnssPowerMetrics2 = GnssMetrics.this.mGnssPowerMetrics;
        gnssPowerMetrics2.getClass();
        try {
            gpsBatteryStats = gnssPowerMetrics2.mBatteryStats.getGpsBatteryStats();
        } catch (RemoteException e) {
            Log.w("GnssMetrics", e);
            gpsBatteryStats = null;
        }
        if (gpsBatteryStats != null) {
            powerMetrics.loggingDurationMs = gpsBatteryStats.getLoggingDurationMs();
            powerMetrics.energyConsumedMah = gpsBatteryStats.getEnergyConsumedMaMs() / 3600000.0d;
            long[] timeInGpsSignalQualityLevel = gpsBatteryStats.getTimeInGpsSignalQualityLevel();
            long[] jArr = new long[timeInGpsSignalQualityLevel.length];
            powerMetrics.timeInSignalQualityLevelMs = jArr;
            System.arraycopy(
                    timeInGpsSignalQualityLevel, 0, jArr, 0, timeInGpsSignalQualityLevel.length);
        }
        gnssLog.powerMetrics = powerMetrics;
        gnssLog.hardwareRevision = SystemProperties.get("ro.boot.revision", "");
        String encodeToString =
                Base64.encodeToString(GnssLogsProto.GnssLog.toByteArray(gnssLog), 0);
        gnssMetrics.reset();
        indentingPrintWriter.append(encodeToString);
    }
}
