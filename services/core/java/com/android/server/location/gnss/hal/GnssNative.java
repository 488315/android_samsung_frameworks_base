package com.android.server.location.gnss.hal;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.location.FusedBatchOptions;
import android.location.GnssCapabilities;
import android.location.GnssMeasurementCorrections;
import android.location.GnssMeasurementsEvent;
import android.location.GnssNavigationMessage;
import android.location.GnssStatus;
import android.location.IGnssAntennaInfoListener;
import android.location.IGnssMeasurementsListener;
import android.location.IGnssNavigationMessageListener;
import android.location.IGnssStatusListener;
import android.location.INSLocationManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationRequest;
import android.location.LocationResult;
import android.location.util.identity.CallerIdentity;
import android.net.wifi.WifiScanner;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.Log;
import android.util.TimeUtils;
import com.android.internal.listeners.ListenerExecutor;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.ConcurrentUtils;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.Preconditions;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.NetworkScoreService$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.SystemServerInitThreadPool$$ExternalSyntheticLambda0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0;
import com.android.server.audio.AudioService$$ExternalSyntheticOutline0;
import com.android.server.location.LocationManagerService;
import com.android.server.location.LocationServiceThread;
import com.android.server.location.eventlog.LocationEventLog;
import com.android.server.location.gnss.GnssAntennaInfoProvider;
import com.android.server.location.gnss.GnssConfiguration;
import com.android.server.location.gnss.GnssListenerMultiplexer;
import com.android.server.location.gnss.GnssLocationProvider;
import com.android.server.location.gnss.GnssLocationProvider$$ExternalSyntheticLambda3;
import com.android.server.location.gnss.GnssLocationProviderSec;
import com.android.server.location.gnss.GnssManagerService;
import com.android.server.location.gnss.GnssManagerService$$ExternalSyntheticLambda0;
import com.android.server.location.gnss.GnssManagerService$GnssGeofenceHalModule$$ExternalSyntheticLambda0;
import com.android.server.location.gnss.GnssMeasurementsProvider;
import com.android.server.location.gnss.GnssMetrics;
import com.android.server.location.gnss.GnssNavigationMessageProvider;
import com.android.server.location.gnss.GnssNmeaProvider;
import com.android.server.location.gnss.GnssNmeaProvider.AnonymousClass1;
import com.android.server.location.gnss.GnssPowerStats;
import com.android.server.location.gnss.GnssStatusProvider;
import com.android.server.location.gnss.hal.GnssNative;
import com.android.server.location.gnss.sec.ActToolHelper;
import com.android.server.location.gnss.sec.CarrierConfig;
import com.android.server.location.gnss.sec.GnssVendorConfig;
import com.android.server.location.gnss.sec.LppeFusedLocationHelper;
import com.android.server.location.gnss.sec.LppeFusedLocationHelper$$ExternalSyntheticLambda0;
import com.android.server.location.gnss.sec.LppeFusedLocationHelper.LppeWlanScanListener;
import com.android.server.location.gnss.sec.LppeFusedLocationHelper.UBPSensorEventListener;
import com.android.server.location.injector.EmergencyHelper;
import com.android.server.location.injector.Injector;
import com.android.server.location.nsflp.NSConnectionHelper;
import com.samsung.android.location.ISLocationManager;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.ToLongFunction;

public class GnssNative {
    public static final int AGPS_REF_LOCATION_TYPE_GSM_CELLID = 1;
    public static final int AGPS_REF_LOCATION_TYPE_LTE_CELLID = 4;
    public static final int AGPS_REF_LOCATION_TYPE_NR_CELLID = 8;
    public static final int AGPS_REF_LOCATION_TYPE_UMTS_CELLID = 2;
    public static final int AGPS_SETID_TYPE_IMSI = 1;
    public static final int AGPS_SETID_TYPE_MSISDN = 2;
    public static final int AGPS_SETID_TYPE_NONE = 0;
    public static final int ALLOWED_ASSERT_COUNT_THRESHOLD = 4;
    public static final long DETECT_ASSERT_INTERVAL_MILLIS = 3000;
    public static final int GNSS_AIDING_TYPE_ALL = 65535;
    public static final int GNSS_AIDING_TYPE_ALMANAC = 2;
    public static final int GNSS_AIDING_TYPE_CELLDB_INFO = 32768;
    public static final int GNSS_AIDING_TYPE_EPHEMERIS = 1;
    public static final int GNSS_AIDING_TYPE_HEALTH = 64;
    public static final int GNSS_AIDING_TYPE_IONO = 16;
    public static final int GNSS_AIDING_TYPE_POSITION = 4;
    public static final int GNSS_AIDING_TYPE_RTI = 1024;
    public static final int GNSS_AIDING_TYPE_SADATA = 512;
    public static final int GNSS_AIDING_TYPE_SVDIR = 128;
    public static final int GNSS_AIDING_TYPE_SVSTEER = 256;
    public static final int GNSS_AIDING_TYPE_TIME = 8;
    public static final int GNSS_AIDING_TYPE_UTC = 32;
    public static final int GNSS_LOCATION_HAS_ALTITUDE = 2;
    public static final int GNSS_LOCATION_HAS_BEARING = 8;
    public static final int GNSS_LOCATION_HAS_BEARING_ACCURACY = 128;
    public static final int GNSS_LOCATION_HAS_HORIZONTAL_ACCURACY = 16;
    public static final int GNSS_LOCATION_HAS_LAT_LONG = 1;
    public static final int GNSS_LOCATION_HAS_SPEED = 4;
    public static final int GNSS_LOCATION_HAS_SPEED_ACCURACY = 64;
    public static final int GNSS_LOCATION_HAS_VERTICAL_ACCURACY = 32;
    public static final int GNSS_POSITION_MODE_MS_ASSISTED = 2;
    public static final int GNSS_POSITION_MODE_MS_BASED = 1;
    public static final int GNSS_POSITION_MODE_STANDALONE = 0;
    public static final int GNSS_POSITION_RECURRENCE_PERIODIC = 0;
    public static final int GNSS_POSITION_RECURRENCE_SINGLE = 1;
    public static final int GNSS_REALTIME_HAS_TIMESTAMP_NS = 1;
    public static final int GNSS_REALTIME_HAS_TIME_UNCERTAINTY_NS = 2;
    public static final float ITAR_SPEED_LIMIT_METERS_PER_SECOND = 400.0f;
    public static final int POWER_STATS_REQUEST_TIMEOUT_MILLIS = 100;
    public static GnssHal sGnssHal;
    public static boolean sGnssHalInitialized;
    public static GnssNative sInstance;
    public final String enable_detecting_gnss_assert;
    public AGpsCallbacks mAGpsCallbacks;
    public final ActToolHelper mActToolHelper;
    public final GnssConfiguration mConfiguration;
    public final Context mContext;
    public final EmergencyHelper mEmergencyHelper;
    public GeofenceCallbacks mGeofenceCallbacks;
    public final GnssHal mGnssHal;
    public volatile boolean mItarSpeedLimitExceeded;
    public LocationRequestCallbacks mLocationRequestCallbacks;
    public LppeHelperCallbacks mLppeHelperCallbacks;
    public final NSConnectionHelper mNSConnectionHelper;
    public NotificationCallbacks mNotificationCallbacks;
    public PsdsCallbacks mPsdsCallbacks;
    public boolean mRegistered;
    public TimeCallbacks mTimeCallbacks;
    public int mTopFlags;
    public static final GnssVendorConfig mGnssVendorConfig = GnssVendorConfig.getInstance();
    public static final CarrierConfig mCarrierConfig = CarrierConfig.getInstance();
    public static boolean isHalInitialted = false;
    public Handler mHandler = LocationServiceThread.getHandler();
    public BaseCallbacks[] mBaseCallbacks = new BaseCallbacks[0];
    public StatusCallbacks[] mStatusCallbacks = new StatusCallbacks[0];
    public SvStatusCallbacks[] mSvStatusCallbacks = new SvStatusCallbacks[0];
    public NmeaCallbacks[] mNmeaCallbacks = new NmeaCallbacks[0];
    public LocationCallbacks[] mLocationCallbacks = new LocationCallbacks[0];
    public MeasurementCallbacks[] mMeasurementCallbacks = new MeasurementCallbacks[0];
    public AntennaInfoCallbacks[] mAntennaInfoCallbacks = new AntennaInfoCallbacks[0];
    public NavigationMessageCallbacks[] mNavigationMessageCallbacks = new NavigationMessageCallbacks[0];
    public GnssPowerStats mLastKnownPowerStats = null;
    public final Object mPowerStatsLock = new Object();
    public final Runnable mPowerStatsTimeoutCallback = new GnssNative$$ExternalSyntheticLambda0(this, 0);
    public final List mPendingPowerStatsCallbacks = new ArrayList();
    public GnssCapabilities mCapabilities = new GnssCapabilities.Builder().build();
    public int mHardwareYear = 0;
    public String mHardwareModelName = null;
    public long mStartRealtimeMs = 0;
    public boolean mHasFirstFix = false;
    public long prevAssertTS = 0;
    public int continuousAssertCount = 0;

    public interface AGpsCallbacks {
        void onRequestSetID(int i);
    }

    public interface AntennaInfoCallbacks {
    }

    public interface BaseCallbacks {
        default void onCapabilitiesChanged(GnssCapabilities gnssCapabilities) {
        }

        void onHalRestarted();

        default void onHalStarted() {
        }
    }

    public interface GeofenceCallbacks {
    }

    public class GnssHal {
    }

    public interface LocationCallbacks {
    }

    public interface LocationRequestCallbacks {
        void onRequestRefLocation();
    }

    public interface LppeHelperCallbacks {
    }

    public interface MeasurementCallbacks {
    }

    public interface NavigationMessageCallbacks {
    }

    public interface NmeaCallbacks {
    }

    public interface NotificationCallbacks {
    }

    public interface PowerStatsCallback {
        void onReportPowerStats(GnssPowerStats gnssPowerStats);
    }

    public interface PsdsCallbacks {
    }

    public interface StatusCallbacks {
    }

    public interface SvStatusCallbacks {
        void onReportSvStatus(GnssStatus gnssStatus);
    }

    public interface TimeCallbacks {
    }

    public GnssNative(Context context, GnssHal gnssHal, Injector injector, GnssConfiguration gnssConfiguration) {
        this.mContext = context;
        Objects.requireNonNull(gnssHal);
        this.mGnssHal = gnssHal;
        LocationManagerService.SystemInjector systemInjector = (LocationManagerService.SystemInjector) injector;
        this.mEmergencyHelper = systemInjector.getEmergencyHelper();
        this.mConfiguration = gnssConfiguration;
        this.mNSConnectionHelper = systemInjector.mNSConnectionHelper;
        this.mActToolHelper = new ActToolHelper();
        String str = SystemProperties.get("enable_detecting_gnss_assert", "true");
        this.enable_detecting_gnss_assert = str;
        DualAppManagerService$$ExternalSyntheticOutline0.m("Set enable_detecting_gnss_assert value:", str, "GnssManager");
    }

    public static void checkInit() {
        if (isHalInitialted) {
            return;
        }
        Log.e("GnssManager", "initializeHal() failed. but it will be recovered.");
        SystemProperties.set("dev.gnss.initializehal", "ON");
    }

    public static synchronized GnssNative create(Context context, Injector injector, GnssConfiguration gnssConfiguration) {
        GnssNative gnssNative;
        synchronized (GnssNative.class) {
            Preconditions.checkState(isSupported());
            Preconditions.checkState(sInstance == null);
            gnssNative = new GnssNative(context, sGnssHal, injector, gnssConfiguration);
            sInstance = gnssNative;
        }
        return gnssNative;
    }

    public static synchronized void initializeHal() {
        synchronized (GnssNative.class) {
            try {
                if (!sGnssHalInitialized) {
                    if (sGnssHal == null) {
                        sGnssHal = new GnssHal();
                    }
                    sGnssHal.getClass();
                    native_class_init_once();
                    sGnssHalInitialized = true;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static synchronized boolean isSupported() {
        boolean native_is_supported;
        synchronized (GnssNative.class) {
            try {
                mGnssVendorConfig.getClass();
                if (!GnssVendorConfig.isIzatServiceEnabled()) {
                    Executors.newSingleThreadExecutor().execute(new GnssNative$$ExternalSyntheticLambda38());
                }
                isHalInitialted = false;
                initializeHal();
                sGnssHal.getClass();
                native_is_supported = native_is_supported();
                isHalInitialted = true;
            } catch (Throwable th) {
                throw th;
            }
        }
        return native_is_supported;
    }

    public static /* synthetic */ void lambda$isSupported$0() {
        try {
            Thread.sleep(5000L);
            checkInit();
        } catch (InterruptedException unused) {
            Log.e("GnssManager", "checkInit() failed.");
        }
    }

    public static void lambda$reportMeasurementData$11(MeasurementCallbacks measurementCallbacks, final GnssMeasurementsEvent gnssMeasurementsEvent) {
        final GnssMeasurementsProvider gnssMeasurementsProvider = (GnssMeasurementsProvider) measurementCallbacks;
        gnssMeasurementsProvider.getClass();
        gnssMeasurementsProvider.deliverToListeners(new Function() { // from class: com.android.server.location.gnss.GnssMeasurementsProvider$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                GnssMeasurementsProvider gnssMeasurementsProvider2 = GnssMeasurementsProvider.this;
                final GnssMeasurementsEvent gnssMeasurementsEvent2 = gnssMeasurementsEvent;
                GnssListenerMultiplexer.GnssListenerRegistration gnssListenerRegistration = (GnssListenerMultiplexer.GnssListenerRegistration) obj;
                if (!gnssMeasurementsProvider2.mAppOpsHelper.noteOpNoThrow(1, gnssListenerRegistration.mIdentity)) {
                    return null;
                }
                LocationEventLog locationEventLog = LocationEventLog.EVENT_LOG;
                int size = gnssMeasurementsEvent2.getMeasurements().size();
                CallerIdentity callerIdentity = gnssListenerRegistration.mIdentity;
                synchronized (locationEventLog) {
                    LocationEventLog.LocationsEventLog locationsEventLog = locationEventLog.mLocationsLog;
                    locationsEventLog.getClass();
                    locationsEventLog.addLog(SystemClock.elapsedRealtime(), new LocationEventLog.GnssMeasurementDeliverEvent(size, callerIdentity));
                }
                LocationEventLog.GnssMeasurementAggregateStats gnssMeasurementAggregateStats = locationEventLog.getGnssMeasurementAggregateStats(callerIdentity);
                synchronized (gnssMeasurementAggregateStats) {
                    gnssMeasurementAggregateStats.mReceivedMeasurementEventCount++;
                }
                return new ListenerExecutor.ListenerOperation() { // from class: com.android.server.location.gnss.GnssMeasurementsProvider$$ExternalSyntheticLambda1
                    public final void operate(Object obj2) {
                        ((IGnssMeasurementsListener) obj2).onGnssMeasurementsReceived(gnssMeasurementsEvent2);
                    }
                };
            }
        });
        synchronized (gnssMeasurementsProvider.mMultiplexerLock) {
            gnssMeasurementsProvider.mLastGnssMeasurementsEvent = gnssMeasurementsEvent;
        }
    }

    public static void lambda$reportNavigationMessage$14(NavigationMessageCallbacks navigationMessageCallbacks, final GnssNavigationMessage gnssNavigationMessage) {
        final GnssNavigationMessageProvider gnssNavigationMessageProvider = (GnssNavigationMessageProvider) navigationMessageCallbacks;
        gnssNavigationMessageProvider.getClass();
        gnssNavigationMessageProvider.deliverToListeners(new Function() { // from class: com.android.server.location.gnss.GnssNavigationMessageProvider$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                GnssNavigationMessageProvider gnssNavigationMessageProvider2 = GnssNavigationMessageProvider.this;
                final GnssNavigationMessage gnssNavigationMessage2 = gnssNavigationMessage;
                gnssNavigationMessageProvider2.getClass();
                if (gnssNavigationMessageProvider2.mAppOpsHelper.noteOpNoThrow(1, ((GnssListenerMultiplexer.GnssListenerRegistration) obj).mIdentity)) {
                    return new ListenerExecutor.ListenerOperation() { // from class: com.android.server.location.gnss.GnssNavigationMessageProvider$$ExternalSyntheticLambda1
                        public final void operate(Object obj2) {
                            ((IGnssNavigationMessageListener) obj2).onGnssNavigationMessageReceived(gnssNavigationMessage2);
                        }
                    };
                }
                return null;
            }
        });
    }

    public static /* synthetic */ void lambda$requestPowerStatsBlocking$5(AtomicReference atomicReference, CountDownLatch countDownLatch, GnssPowerStats gnssPowerStats) {
        atomicReference.set(gnssPowerStats);
        countDownLatch.countDown();
    }

    private static native boolean native_add_geofence(int i, double d, double d2, double d3, int i2, int i3, int i4, int i5);

    private static native void native_agps_set_id(int i, String str);

    private static native void native_agps_set_ref_location_cellid(int i, int i2, int i3, int i4, int i5, int i6);

    private static native void native_agps_set_ref_location_cellid(int i, int i2, int i3, int i4, long j, int i5, int i6);

    private static native void native_agps_set_ref_location_cellid(int i, int i2, int i3, int i4, long j, int i5, int i6, int i7);

    private static native void native_class_init_once();

    private static native void native_cleanup();

    private static native void native_cleanup_batching();

    private static native void native_configuration_update_extension(String str);

    private static native void native_delete_aiding_data(int i);

    private static native void native_flush_batch();

    private static native int native_get_batch_size();

    private static native String native_get_internal_state();

    private static native float native_get_seh_gnss_hal_version();

    private static native boolean native_init();

    private static native boolean native_init_batching();

    private static native boolean native_init_extension_location_off();

    private static native boolean native_init_extension_once();

    private native void native_init_once(boolean z);

    private static native void native_inject_best_location(int i, double d, double d2, double d3, float f, float f2, float f3, float f4, float f5, float f6, long j, int i2, long j2, double d4);

    private static native boolean native_inject_civic_address(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13);

    private static native void native_inject_flp_error(int i);

    private static native void native_inject_flp_location(int i, double d, double d2, double d3, float f, float f2, float f3, float f4, float f5, float f6, long j);

    private static native void native_inject_location(int i, double d, double d2, double d3, float f, float f2, float f3, float f4, float f5, float f6, long j, int i2, long j2, double d4);

    private static native void native_inject_lppe_com_ie_capability(int i, boolean z, boolean z2, boolean z3, int i2, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8);

    private static native boolean native_inject_measurement_corrections(GnssMeasurementCorrections gnssMeasurementCorrections);

    private static native void native_inject_ni_supl_message_data(byte[] bArr, int i, int i2);

    private static native void native_inject_psds_data(byte[] bArr, int i, int i2);

    private static native void native_inject_time(long j, long j2, int i);

    private static native void native_inject_ubp_capability(boolean z, boolean z2, boolean z3, boolean z4);

    private static native void native_inject_ubp_error(int i);

    private static native void native_inject_ubp_info(int i, int i2);

    private static native void native_inject_wlan_capability(int i, int i2, long j, int i3, int i4, int i5);

    private static native void native_inject_wlan_error(int i);

    private static native void native_inject_wlan_scan_info(long[] jArr, int[] iArr, int[] iArr2, int i);

    private static native boolean native_is_antenna_info_supported();

    private static native boolean native_is_geofence_supported();

    private static native boolean native_is_gnss_visibility_control_supported();

    private static native boolean native_is_measurement_corrections_supported();

    private static native boolean native_is_measurement_supported();

    private static native boolean native_is_navigation_message_supported();

    private static native boolean native_is_seh_gnss_aidl_hal();

    private static native boolean native_is_supported();

    private static native boolean native_pause_geofence(int i);

    private static native int native_read_nmea(byte[] bArr, int i);

    private static native boolean native_remove_geofence(int i);

    private static native void native_request_power_stats();

    private static native boolean native_resume_geofence(int i, int i2);

    private static native void native_send_supl_ni_message(byte[] bArr, int i);

    private static native void native_set_agps_server(int i, String str, int i2);

    private static native boolean native_set_position_mode(int i, int i2, int i3, int i4, int i5, boolean z);

    private static native boolean native_start();

    private static native boolean native_start_antenna_info_listening();

    private static native boolean native_start_batch(long j, float f, boolean z);

    private static native boolean native_start_measurement_collection(boolean z, boolean z2, int i);

    private static native boolean native_start_navigation_message_collection();

    private static native boolean native_start_nmea_message_collection();

    private static native boolean native_start_sv_status_collection();

    private static native boolean native_stop();

    private static native boolean native_stop_antenna_info_listening();

    private static native boolean native_stop_batch();

    private static native boolean native_stop_measurement_collection();

    private static native boolean native_stop_navigation_message_collection();

    private static native boolean native_stop_nmea_message_collection();

    private static native boolean native_stop_sv_status_collection();

    private static native boolean native_supports_psds();

    public static synchronized void setGnssHalForTest(GnssHal gnssHal) {
        synchronized (GnssNative.class) {
            Objects.requireNonNull(gnssHal);
            sGnssHal = gnssHal;
            sGnssHalInitialized = false;
            sInstance = null;
        }
    }

    public final void addAntennaInfoCallbacks(AntennaInfoCallbacks antennaInfoCallbacks) {
        Preconditions.checkState(!this.mRegistered);
        this.mAntennaInfoCallbacks = (AntennaInfoCallbacks[]) ArrayUtils.appendElement(AntennaInfoCallbacks.class, this.mAntennaInfoCallbacks, antennaInfoCallbacks);
    }

    public final void addBaseCallbacks(BaseCallbacks baseCallbacks) {
        Preconditions.checkState(!this.mRegistered);
        this.mBaseCallbacks = (BaseCallbacks[]) ArrayUtils.appendElement(BaseCallbacks.class, this.mBaseCallbacks, baseCallbacks);
    }

    public final boolean addGeofence(int i, double d, double d2, double d3, int i2, int i3, int i4, int i5) {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.getClass();
        return native_add_geofence(i, d, d2, d3, i2, i3, i4, i5);
    }

    public final void addLocationCallbacks(LocationCallbacks locationCallbacks) {
        Preconditions.checkState(!this.mRegistered);
        this.mLocationCallbacks = (LocationCallbacks[]) ArrayUtils.appendElement(LocationCallbacks.class, this.mLocationCallbacks, locationCallbacks);
    }

    public final void addMeasurementCallbacks(MeasurementCallbacks measurementCallbacks) {
        Preconditions.checkState(!this.mRegistered);
        this.mMeasurementCallbacks = (MeasurementCallbacks[]) ArrayUtils.appendElement(MeasurementCallbacks.class, this.mMeasurementCallbacks, measurementCallbacks);
    }

    public final void addNavigationMessageCallbacks(NavigationMessageCallbacks navigationMessageCallbacks) {
        Preconditions.checkState(!this.mRegistered);
        this.mNavigationMessageCallbacks = (NavigationMessageCallbacks[]) ArrayUtils.appendElement(NavigationMessageCallbacks.class, this.mNavigationMessageCallbacks, navigationMessageCallbacks);
    }

    public final void addNmeaCallbacks(NmeaCallbacks nmeaCallbacks) {
        Preconditions.checkState(!this.mRegistered);
        this.mNmeaCallbacks = (NmeaCallbacks[]) ArrayUtils.appendElement(NmeaCallbacks.class, this.mNmeaCallbacks, nmeaCallbacks);
    }

    public final void addStatusCallbacks(StatusCallbacks statusCallbacks) {
        Preconditions.checkState(!this.mRegistered);
        this.mStatusCallbacks = (StatusCallbacks[]) ArrayUtils.appendElement(StatusCallbacks.class, this.mStatusCallbacks, statusCallbacks);
    }

    public final void addSvStatusCallbacks(SvStatusCallbacks svStatusCallbacks) {
        Preconditions.checkState(!this.mRegistered);
        this.mSvStatusCallbacks = (SvStatusCallbacks[]) ArrayUtils.appendElement(SvStatusCallbacks.class, this.mSvStatusCallbacks, svStatusCallbacks);
    }

    public final void cleanup() {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.getClass();
        native_cleanup();
    }

    public final void cleanupBatching() {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.getClass();
        native_cleanup_batching();
    }

    public final void deleteAidingData(int i) {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.getClass();
        native_delete_aiding_data(i);
    }

    public final void flushBatch() {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.getClass();
        native_flush_batch();
    }

    public final int getBatchSize() {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.getClass();
        return native_get_batch_size();
    }

    public final GnssCapabilities getCapabilities() {
        return this.mCapabilities;
    }

    public final GnssConfiguration getConfiguration() {
        return this.mConfiguration;
    }

    public final String getHardwareModelName() {
        return this.mHardwareModelName;
    }

    public final int getHardwareYear() {
        return this.mHardwareYear;
    }

    public final String getInternalState() {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.getClass();
        return native_get_internal_state();
    }

    public final GnssPowerStats getLastKnownPowerStats() {
        return this.mLastKnownPowerStats;
    }

    public final float getSehGnssHalVersion() {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.getClass();
        return native_get_seh_gnss_hal_version();
    }

    public final void gnssConfigurationUpdateSec(String str) {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.getClass();
        native_configuration_update_extension(str);
    }

    public final boolean init() {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.getClass();
        return native_init();
    }

    public final boolean initBatching() {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.getClass();
        return native_init_batching();
    }

    public final boolean initLocationOff() {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.getClass();
        return native_init_extension_location_off();
    }

    public final void initializeGnss(boolean z) {
        Preconditions.checkState(this.mRegistered);
        this.mTopFlags = 0;
        this.mGnssHal.getClass();
        native_init_once(z);
        this.mGnssHal.getClass();
        if (!native_init()) {
            Log.e("GnssManager", "gnss hal initialization failed");
            return;
        }
        this.mGnssHal.getClass();
        native_cleanup();
        mGnssVendorConfig.getClass();
        if (GnssVendorConfig.isBroadcomGnss()) {
            this.mGnssHal.getClass();
            native_init_extension_location_off();
        }
        Log.i("GnssManager", "gnss hal initialized");
    }

    public final void injectBestLocation(Location location) {
        Preconditions.checkState(this.mRegistered);
        int i = (location.hasAltitude() ? 2 : 0) | 1 | (location.hasSpeed() ? 4 : 0) | (location.hasBearing() ? 8 : 0) | (location.hasAccuracy() ? 16 : 0) | (location.hasVerticalAccuracy() ? 32 : 0) | (location.hasSpeedAccuracy() ? 64 : 0) | (location.hasBearingAccuracy() ? 128 : 0);
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        double altitude = location.getAltitude();
        float speed = location.getSpeed();
        float bearing = location.getBearing();
        float accuracy = location.getAccuracy();
        float verticalAccuracyMeters = location.getVerticalAccuracyMeters();
        float speedAccuracyMetersPerSecond = location.getSpeedAccuracyMetersPerSecond();
        float bearingAccuracyDegrees = location.getBearingAccuracyDegrees();
        long time = location.getTime();
        int i2 = location.hasElapsedRealtimeUncertaintyNanos() ? 2 : 0;
        long elapsedRealtimeNanos = location.getElapsedRealtimeNanos();
        double elapsedRealtimeUncertaintyNanos = location.getElapsedRealtimeUncertaintyNanos();
        this.mGnssHal.getClass();
        native_inject_best_location(i, latitude, longitude, altitude, speed, bearing, accuracy, verticalAccuracyMeters, speedAccuracyMetersPerSecond, bearingAccuracyDegrees, time, i2 | 1, elapsedRealtimeNanos, elapsedRealtimeUncertaintyNanos);
    }

    public final void injectCivicAddress(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13) {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.getClass();
        native_inject_civic_address(str, str2, str3, str4, str5, str6, str7, str8, str9, str10, str11, str12, str13);
    }

    public final void injectFlpError(int i) {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.getClass();
        native_inject_flp_error(i);
    }

    public final void injectFlpLocation(int i, double d, double d2, double d3, float f, float f2, float f3, float f4, float f5, float f6, long j) {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.getClass();
        native_inject_flp_location(i, d, d2, d3, f, f2, f3, f4, f5, f6, j);
    }

    public final void injectLocation(Location location) {
        Preconditions.checkState(this.mRegistered);
        if (location.hasAccuracy()) {
            int i = (location.hasAltitude() ? 2 : 0) | 1 | (location.hasSpeed() ? 4 : 0) | (location.hasBearing() ? 8 : 0) | (location.hasAccuracy() ? 16 : 0) | (location.hasVerticalAccuracy() ? 32 : 0) | (location.hasSpeedAccuracy() ? 64 : 0) | (location.hasBearingAccuracy() ? 128 : 0);
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            double altitude = location.getAltitude();
            float speed = location.getSpeed();
            float bearing = location.getBearing();
            float accuracy = location.getAccuracy();
            float verticalAccuracyMeters = location.getVerticalAccuracyMeters();
            float speedAccuracyMetersPerSecond = location.getSpeedAccuracyMetersPerSecond();
            float bearingAccuracyDegrees = location.getBearingAccuracyDegrees();
            long time = location.getTime();
            int i2 = location.hasElapsedRealtimeUncertaintyNanos() ? 2 : 0;
            long elapsedRealtimeNanos = location.getElapsedRealtimeNanos();
            double elapsedRealtimeUncertaintyNanos = location.getElapsedRealtimeUncertaintyNanos();
            this.mGnssHal.getClass();
            native_inject_location(i, latitude, longitude, altitude, speed, bearing, accuracy, verticalAccuracyMeters, speedAccuracyMetersPerSecond, bearingAccuracyDegrees, time, i2 | 1, elapsedRealtimeNanos, elapsedRealtimeUncertaintyNanos);
        }
    }

    public final void injectLppeComIeCapability(int i, boolean z, boolean z2, boolean z3, int i2, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8) {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.getClass();
        native_inject_lppe_com_ie_capability(i, z, z2, z3, i2, z4, z5, z6, z7, z8);
    }

    public final boolean injectMeasurementCorrections(GnssMeasurementCorrections gnssMeasurementCorrections) {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.getClass();
        return native_inject_measurement_corrections(gnssMeasurementCorrections);
    }

    public final void injectNiSuplMessageData(byte[] bArr, int i, int i2) {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.getClass();
        native_inject_ni_supl_message_data(bArr, i, i2);
    }

    public final void injectPsdsData(byte[] bArr, int i, int i2) {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.getClass();
        native_inject_psds_data(bArr, i, i2);
    }

    public final void injectTime(long j, long j2, int i) {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.getClass();
        native_inject_time(j, j2, i);
    }

    public final void injectUbpCapability(boolean z, boolean z2, boolean z3, boolean z4) {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.getClass();
        native_inject_ubp_capability(z, z2, z3, z4);
    }

    public final void injectUbpError(int i) {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.getClass();
        native_inject_ubp_error(i);
    }

    public final void injectUbpInfo(int i, int i2) {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.getClass();
        native_inject_ubp_info(i, i2);
    }

    public final void injectWlanCapability(int i, int i2, long j, int i3, int i4, int i5) {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.getClass();
        native_inject_wlan_capability(i, i2, j, i3, i4, i5);
    }

    public final void injectWlanError(int i) {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.getClass();
        native_inject_wlan_error(i);
    }

    public final void injectWlanScanInfo(long[] jArr, int[] iArr, int[] iArr2, int i) {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.getClass();
        native_inject_wlan_scan_info(jArr, iArr, iArr2, i);
    }

    public final boolean isAntennaInfoSupported() {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.getClass();
        return native_is_antenna_info_supported();
    }

    public final boolean isGeofencingSupported() {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.getClass();
        return native_is_geofence_supported();
    }

    public final boolean isGnssAssertMessage(String str) {
        return !str.startsWith("$");
    }

    public final boolean isGnssVisibilityControlSupported() {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.getClass();
        return native_is_gnss_visibility_control_supported();
    }

    public final boolean isInEmergencySession() {
        mGnssVendorConfig.getClass();
        if (!GnssVendorConfig.isIzatServiceEnabled()) {
            CarrierConfig carrierConfig = mCarrierConfig;
            if (carrierConfig.isUSAMarket() || carrierConfig.isCanadaMarket()) {
                final int i = 0;
                return ((Boolean) Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier(this) { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda4
                    public final /* synthetic */ GnssNative f$0;

                    {
                        this.f$0 = this;
                    }

                    public final Object getOrThrow() {
                        int i2 = i;
                        GnssNative gnssNative = this.f$0;
                        switch (i2) {
                            case 0:
                                int i3 = GnssNative.GNSS_POSITION_MODE_STANDALONE;
                                return gnssNative.lambda$isInEmergencySession$32();
                            default:
                                int i4 = GnssNative.GNSS_POSITION_MODE_STANDALONE;
                                return gnssNative.lambda$isInEmergencySession$33();
                        }
                    }
                })).booleanValue();
            }
        }
        final int i2 = 1;
        return ((Boolean) Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier(this) { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda4
            public final /* synthetic */ GnssNative f$0;

            {
                this.f$0 = this;
            }

            public final Object getOrThrow() {
                int i22 = i2;
                GnssNative gnssNative = this.f$0;
                switch (i22) {
                    case 0:
                        int i3 = GnssNative.GNSS_POSITION_MODE_STANDALONE;
                        return gnssNative.lambda$isInEmergencySession$32();
                    default:
                        int i4 = GnssNative.GNSS_POSITION_MODE_STANDALONE;
                        return gnssNative.lambda$isInEmergencySession$33();
                }
            }
        })).booleanValue();
    }

    public final boolean isItarSpeedLimitExceeded() {
        return this.mItarSpeedLimitExceeded;
    }

    public final boolean isMeasurementCorrectionsSupported() {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.getClass();
        return native_is_measurement_corrections_supported();
    }

    public final boolean isMeasurementSupported() {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.getClass();
        return native_is_measurement_supported();
    }

    public final boolean isNavigationMessageCollectionSupported() {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.getClass();
        return native_is_navigation_message_supported();
    }

    public final boolean isPsdsSupported() {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.getClass();
        return native_supports_psds();
    }

    public final boolean isSehGnssAidlHal() {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.getClass();
        return native_is_seh_gnss_aidl_hal();
    }

    public final Boolean lambda$isInEmergencySession$32() throws Exception {
        return Boolean.valueOf(((GnssLocationProviderSec) this.mNotificationCallbacks).mNIHandler.getInEmergency(0L));
    }

    public final Boolean lambda$isInEmergencySession$33() throws Exception {
        return Boolean.valueOf(this.mEmergencyHelper.isInEmergency(TimeUnit.SECONDS.toMillis(this.mConfiguration.mEsExtensionSec)));
    }

    public final /* synthetic */ void lambda$new$1() {
        Log.d("GnssManager", "Request for power stats timed out");
        reportGnssPowerStats(null);
    }

    public final /* synthetic */ void lambda$onCapabilitiesChanged$16(GnssCapabilities gnssCapabilities, GnssCapabilities gnssCapabilities2) throws Exception {
        if (gnssCapabilities.equals(gnssCapabilities2)) {
            return;
        }
        Log.i("GnssManager", "gnss capabilities changed to " + gnssCapabilities);
        int i = 0;
        while (true) {
            BaseCallbacks[] baseCallbacksArr = this.mBaseCallbacks;
            if (i >= baseCallbacksArr.length) {
                return;
            }
            baseCallbacksArr[i].onCapabilitiesChanged(gnssCapabilities);
            i++;
        }
    }

    public final void lambda$psdsDownloadRequest$19(int i) throws Exception {
        GnssLocationProvider gnssLocationProvider = (GnssLocationProvider) this.mPsdsCallbacks;
        gnssLocationProvider.isExtraCommandFromAllowedAppRequest = false;
        gnssLocationProvider.postWithWakeLockHeld(new GnssLocationProvider$$ExternalSyntheticLambda3(i, 0, gnssLocationProvider));
    }

    public final /* synthetic */ void lambda$reportAGpsStatus$9(int i, int i2, byte[] bArr) throws Exception {
        ((GnssLocationProvider) this.mAGpsCallbacks).onReportAGpsStatus(i, i2, bArr);
    }

    public final void lambda$reportAntennaInfo$13(final List list) throws Exception {
        int i = 0;
        while (true) {
            AntennaInfoCallbacks[] antennaInfoCallbacksArr = this.mAntennaInfoCallbacks;
            if (i >= antennaInfoCallbacksArr.length) {
                return;
            }
            GnssAntennaInfoProvider gnssAntennaInfoProvider = (GnssAntennaInfoProvider) antennaInfoCallbacksArr[i];
            if (!list.equals(gnssAntennaInfoProvider.mAntennaInfos)) {
                gnssAntennaInfoProvider.mAntennaInfos = list;
                gnssAntennaInfoProvider.deliverToListeners(new ListenerExecutor.ListenerOperation() { // from class: com.android.server.location.gnss.GnssAntennaInfoProvider$$ExternalSyntheticLambda0
                    public final void operate(Object obj) {
                        ((IGnssAntennaInfoListener) obj).onGnssAntennaInfoChanged(list);
                    }
                });
            }
            i++;
        }
    }

    public final void lambda$reportGeofenceAddStatus$22(int i, int i2) throws Exception {
        GnssManagerService.GnssGeofenceHalModule gnssGeofenceHalModule = (GnssManagerService.GnssGeofenceHalModule) this.mGeofenceCallbacks;
        gnssGeofenceHalModule.getClass();
        LocationServiceThread.getHandler().post(new GnssManagerService$GnssGeofenceHalModule$$ExternalSyntheticLambda0(gnssGeofenceHalModule, i, i2, 1));
    }

    public final void lambda$reportGeofencePauseStatus$24(int i, int i2) throws Exception {
        GnssManagerService.GnssGeofenceHalModule gnssGeofenceHalModule = (GnssManagerService.GnssGeofenceHalModule) this.mGeofenceCallbacks;
        gnssGeofenceHalModule.getClass();
        LocationServiceThread.getHandler().post(new GnssManagerService$GnssGeofenceHalModule$$ExternalSyntheticLambda0(gnssGeofenceHalModule, i, i2, 0));
    }

    public final void lambda$reportGeofenceRemoveStatus$23(int i, int i2) throws Exception {
        GnssManagerService.GnssGeofenceHalModule gnssGeofenceHalModule = (GnssManagerService.GnssGeofenceHalModule) this.mGeofenceCallbacks;
        gnssGeofenceHalModule.getClass();
        LocationServiceThread.getHandler().post(new GnssManagerService$GnssGeofenceHalModule$$ExternalSyntheticLambda0(gnssGeofenceHalModule, i, i2, 2));
    }

    public final void lambda$reportGeofenceResumeStatus$25(int i, int i2) throws Exception {
        GnssManagerService.GnssGeofenceHalModule gnssGeofenceHalModule = (GnssManagerService.GnssGeofenceHalModule) this.mGeofenceCallbacks;
        gnssGeofenceHalModule.getClass();
        LocationServiceThread.getHandler().post(new GnssManagerService$GnssGeofenceHalModule$$ExternalSyntheticLambda0(gnssGeofenceHalModule, i, i2, 3));
    }

    public final void lambda$reportGeofenceStatus$21(final int i, final Location location) throws Exception {
        final GnssManagerService.GnssGeofenceHalModule gnssGeofenceHalModule = (GnssManagerService.GnssGeofenceHalModule) this.mGeofenceCallbacks;
        gnssGeofenceHalModule.getClass();
        LocationServiceThread.getHandler().post(new Runnable() { // from class: com.android.server.location.gnss.GnssManagerService$GnssGeofenceHalModule$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                GnssManagerService.GnssGeofenceHalModule gnssGeofenceHalModule2 = GnssManagerService.GnssGeofenceHalModule.this;
                int i2 = i;
                Location location2 = location;
                gnssGeofenceHalModule2.getClass();
                gnssGeofenceHalModule2.getGeofenceHardware().reportGeofenceMonitorStatus(0, i2 == 2 ? 0 : 1, location2, FusedBatchOptions.SourceTechnologies.GNSS);
            }
        });
    }

    public final void lambda$reportGeofenceTransition$20(final int i, final Location location, final int i2, final long j) throws Exception {
        final GnssManagerService.GnssGeofenceHalModule gnssGeofenceHalModule = (GnssManagerService.GnssGeofenceHalModule) this.mGeofenceCallbacks;
        gnssGeofenceHalModule.getClass();
        LocationServiceThread.getHandler().post(new Runnable() { // from class: com.android.server.location.gnss.GnssManagerService$GnssGeofenceHalModule$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                GnssManagerService.GnssGeofenceHalModule gnssGeofenceHalModule2 = GnssManagerService.GnssGeofenceHalModule.this;
                gnssGeofenceHalModule2.getGeofenceHardware().reportGeofenceTransition(i, location, i2, j, 0, FusedBatchOptions.SourceTechnologies.GNSS);
            }
        });
    }

    public final void lambda$reportLocation$6(final boolean z, final Location location) throws Exception {
        int i = 0;
        if (z && !this.mHasFirstFix) {
            this.mHasFirstFix = true;
            final int elapsedRealtime = (int) (SystemClock.elapsedRealtime() - this.mStartRealtimeMs);
            int i2 = 0;
            while (true) {
                StatusCallbacks[] statusCallbacksArr = this.mStatusCallbacks;
                if (i2 >= statusCallbacksArr.length) {
                    break;
                }
                GnssStatusProvider gnssStatusProvider = (GnssStatusProvider) statusCallbacksArr[i2];
                gnssStatusProvider.getClass();
                gnssStatusProvider.deliverToListeners(new ListenerExecutor.ListenerOperation() { // from class: com.android.server.location.gnss.GnssStatusProvider$$ExternalSyntheticLambda0
                    public final void operate(Object obj) {
                        ((IGnssStatusListener) obj).onFirstFix(elapsedRealtime);
                    }
                });
                i2++;
            }
        }
        if (location.hasSpeed()) {
            boolean z2 = location.getSpeed() > 400.0f;
            if (!this.mItarSpeedLimitExceeded && z2) {
                Log.w("GnssManager", "speed nearing ITAR threshold - blocking further GNSS output");
            } else if (this.mItarSpeedLimitExceeded && !z2) {
                Log.w("GnssManager", "speed leaving ITAR threshold - allowing further GNSS output");
            }
            this.mItarSpeedLimitExceeded = z2;
        }
        if (this.mItarSpeedLimitExceeded) {
            return;
        }
        while (true) {
            LocationCallbacks[] locationCallbacksArr = this.mLocationCallbacks;
            if (i >= locationCallbacksArr.length) {
                return;
            }
            final GnssLocationProvider gnssLocationProvider = (GnssLocationProvider) locationCallbacksArr[i];
            gnssLocationProvider.getClass();
            gnssLocationProvider.postWithWakeLockHeld(new Runnable() { // from class: com.android.server.location.gnss.GnssLocationProvider$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    Bundle bundle;
                    GnssLocationProvider gnssLocationProvider2 = GnssLocationProvider.this;
                    boolean z3 = z;
                    Location location2 = location;
                    gnssLocationProvider2.getClass();
                    Log.d("GnssLocationProvider", "reportLocation");
                    long elapsedRealtimeNanos = SystemClock.elapsedRealtimeNanos();
                    GnssLocationProvider.LocationExtras locationExtras = gnssLocationProvider2.mLocationExtras;
                    synchronized (locationExtras) {
                        bundle = new Bundle(locationExtras.mBundle);
                    }
                    location2.setExtras(bundle);
                    try {
                        gnssLocationProvider2.mGnssVendorConfig.getClass();
                        if (GnssVendorConfig.isLsiGnss()) {
                            location2 = gnssLocationProvider2.mVSFilter.getFilteredLocation(location2);
                        }
                        gnssLocationProvider2.mGnssVendorConfig.getClass();
                        if (!GnssVendorConfig.isIzatServiceEnabled()) {
                            gnssLocationProvider2.secLocationValidate(location2, elapsedRealtimeNanos);
                        }
                        gnssLocationProvider2.reportLocation(LocationResult.wrap(new Location[]{location2}).validate());
                        if (gnssLocationProvider2.mStarted) {
                            GnssMetrics gnssMetrics = gnssLocationProvider2.mGnssMetrics;
                            GnssMetrics.Statistics statistics = gnssMetrics.mLocationFailureReportsStatistics;
                            GnssMetrics.Statistics statistics2 = gnssMetrics.mLocationFailureStatistics;
                            if (z3) {
                                statistics2.addItem(0.0d);
                                statistics.addItem(0.0d);
                            } else {
                                statistics2.addItem(1.0d);
                                statistics.addItem(1.0d);
                            }
                            if (z3) {
                                if (location2.hasAccuracy()) {
                                    GnssMetrics gnssMetrics2 = gnssLocationProvider2.mGnssMetrics;
                                    double accuracy = location2.getAccuracy();
                                    gnssMetrics2.mPositionAccuracyMeterStatistics.addItem(accuracy);
                                    gnssMetrics2.mPositionAccuracyMetersReportsStatistics.addItem(accuracy);
                                }
                                if (gnssLocationProvider2.mTimeToFirstFix > 0) {
                                    int elapsedRealtime2 = (int) (SystemClock.elapsedRealtime() - gnssLocationProvider2.mLastFixTime);
                                    GnssMetrics gnssMetrics3 = gnssLocationProvider2.mGnssMetrics;
                                    int i3 = gnssLocationProvider2.mFixInterval;
                                    gnssMetrics3.getClass();
                                    int max = (elapsedRealtime2 / Math.max(1000, i3)) - 1;
                                    if (max > 0) {
                                        for (int i4 = 0; i4 < max; i4++) {
                                            gnssMetrics3.mLocationFailureStatistics.addItem(1.0d);
                                            gnssMetrics3.mLocationFailureReportsStatistics.addItem(1.0d);
                                        }
                                    }
                                }
                            }
                        } else {
                            long elapsedRealtime3 = SystemClock.elapsedRealtime() - gnssLocationProvider2.mStartedChangedElapsedRealtime;
                            if (elapsedRealtime3 > 2000) {
                                String str = "Unexpected GNSS Location report " + TimeUtils.formatDuration(elapsedRealtime3) + " after location turned off";
                                if (elapsedRealtime3 > 15000) {
                                    Log.e("GnssLocationProvider", str);
                                } else {
                                    Log.w("GnssLocationProvider", str);
                                }
                            }
                        }
                        long elapsedRealtime4 = SystemClock.elapsedRealtime();
                        gnssLocationProvider2.mLastFixTime = elapsedRealtime4;
                        if (gnssLocationProvider2.mTimeToFirstFix == 0 && z3) {
                            gnssLocationProvider2.mTimeToFirstFix = (int) (elapsedRealtime4 - gnssLocationProvider2.mFixRequestTime);
                            GestureWakeup$$ExternalSyntheticOutline0.m(new StringBuilder("TTFF: "), gnssLocationProvider2.mTimeToFirstFix, "GnssLocationProvider");
                            if (gnssLocationProvider2.mStarted) {
                                GnssMetrics gnssMetrics4 = gnssLocationProvider2.mGnssMetrics;
                                double d = gnssLocationProvider2.mTimeToFirstFix;
                                gnssMetrics4.mTimeToFirstFixSecStatistics.addItem(d / 1000.0d);
                                gnssMetrics4.mTimeToFirstFixMilliSReportsStatistics.addItem(d);
                            }
                        }
                        if (gnssLocationProvider2.mStarted && !gnssLocationProvider2.mGnssNative.mCapabilities.hasScheduling() && gnssLocationProvider2.mFixInterval < 60000) {
                            gnssLocationProvider2.mAlarmManager.cancel(gnssLocationProvider2.mTimeoutListener);
                        }
                        if (gnssLocationProvider2.mGnssNative.mCapabilities.hasScheduling() || !gnssLocationProvider2.mStarted || gnssLocationProvider2.mFixInterval <= 10000) {
                            return;
                        }
                        Log.d("GnssLocationProvider", "got fix, hibernating");
                        gnssLocationProvider2.hibernate();
                    } catch (LocationResult.BadLocationException e) {
                        Log.e("GnssLocationProvider", "Dropping invalid location: " + e);
                    }
                }
            });
            i++;
        }
    }

    public final void lambda$reportLocationBatch$18(Location[] locationArr) throws Exception {
        Runnable[] runnableArr;
        int i = 0;
        while (true) {
            LocationCallbacks[] locationCallbacksArr = this.mLocationCallbacks;
            if (i >= locationCallbacksArr.length) {
                return;
            }
            GnssLocationProvider gnssLocationProvider = (GnssLocationProvider) locationCallbacksArr[i];
            gnssLocationProvider.getClass();
            AudioService$$ExternalSyntheticOutline0.m(new StringBuilder("Location batch of size "), locationArr.length, " reported", "GnssLocationProvider");
            if (locationArr.length > 0) {
                if (locationArr.length > 1) {
                    int length = locationArr.length - 2;
                    while (true) {
                        if (length < 0) {
                            final int i2 = 1;
                            Arrays.sort(locationArr, Comparator.comparingLong(new ToLongFunction() { // from class: com.android.server.location.gnss.GnssLocationProvider$$ExternalSyntheticLambda1
                                @Override // java.util.function.ToLongFunction
                                public final long applyAsLong(Object obj) {
                                    Location location = (Location) obj;
                                    switch (i2) {
                                        case 0:
                                            return location.getTime();
                                        default:
                                            return location.getElapsedRealtimeNanos();
                                    }
                                }
                            }));
                            break;
                        }
                        int i3 = length + 1;
                        if (Math.abs((locationArr[i3].getTime() - locationArr[length].getTime()) - (locationArr[i3].getElapsedRealtimeMillis() - locationArr[length].getElapsedRealtimeMillis())) > 500) {
                            final int i4 = 0;
                            Arrays.sort(locationArr, Comparator.comparingLong(new ToLongFunction() { // from class: com.android.server.location.gnss.GnssLocationProvider$$ExternalSyntheticLambda1
                                @Override // java.util.function.ToLongFunction
                                public final long applyAsLong(Object obj) {
                                    Location location = (Location) obj;
                                    switch (i4) {
                                        case 0:
                                            return location.getTime();
                                        default:
                                            return location.getElapsedRealtimeNanos();
                                    }
                                }
                            }));
                            long time = locationArr[locationArr.length - 1].getTime() - locationArr[locationArr.length - 1].getElapsedRealtimeMillis();
                            for (int length2 = locationArr.length - 2; length2 >= 0; length2--) {
                                Location location = locationArr[length2];
                                location.setElapsedRealtimeNanos(TimeUnit.MILLISECONDS.toNanos(Math.max(location.getTime() - time, 0L)));
                            }
                        } else {
                            length--;
                        }
                    }
                }
                try {
                    gnssLocationProvider.reportLocation(LocationResult.wrap(locationArr).validate());
                } catch (LocationResult.BadLocationException e) {
                    Log.e("GnssLocationProvider", "Dropping invalid locations: " + e);
                }
            }
            synchronized (gnssLocationProvider.mLock) {
                runnableArr = (Runnable[]) gnssLocationProvider.mFlushListeners.toArray(new Runnable[0]);
                gnssLocationProvider.mFlushListeners.clear();
            }
            for (Runnable runnable : runnableArr) {
                runnable.run();
            }
            i++;
        }
    }

    public final /* synthetic */ void lambda$reportMeasurementData$12(GnssMeasurementsEvent gnssMeasurementsEvent) throws Exception {
        if (this.mItarSpeedLimitExceeded) {
            return;
        }
        for (MeasurementCallbacks measurementCallbacks : this.mMeasurementCallbacks) {
            this.mHandler.post(new GnssNative$$ExternalSyntheticLambda24(2, measurementCallbacks, gnssMeasurementsEvent));
        }
    }

    public final /* synthetic */ void lambda$reportNavigationMessage$15(GnssNavigationMessage gnssNavigationMessage) throws Exception {
        if (this.mItarSpeedLimitExceeded) {
            return;
        }
        for (NavigationMessageCallbacks navigationMessageCallbacks : this.mNavigationMessageCallbacks) {
            this.mHandler.post(new GnssNative$$ExternalSyntheticLambda24(1, navigationMessageCallbacks, gnssNavigationMessage));
        }
    }

    public final /* synthetic */ void lambda$reportNfwNotification$31(String str, byte b, String str2, byte b2, String str3, byte b3, boolean z, boolean z2) throws Exception {
        ((GnssLocationProvider) this.mNotificationCallbacks).onReportNfwNotification(str, b, str2, b2, str3, b3, z, z2);
    }

    public final void lambda$reportNmea$10(long j) throws Exception {
        if (this.mItarSpeedLimitExceeded) {
            return;
        }
        int i = 0;
        while (true) {
            NmeaCallbacks[] nmeaCallbacksArr = this.mNmeaCallbacks;
            if (i >= nmeaCallbacksArr.length) {
                return;
            }
            GnssNmeaProvider gnssNmeaProvider = (GnssNmeaProvider) nmeaCallbacksArr[i];
            gnssNmeaProvider.getClass();
            gnssNmeaProvider.deliverToListeners(gnssNmeaProvider.new AnonymousClass1(j));
            i++;
        }
    }

    public final void lambda$reportStatus$7(int i) throws Exception {
        ISLocationManager iSLocationManager;
        Handler handler;
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "reportStatus : ", "GnssManager");
        int i2 = 0;
        while (true) {
            StatusCallbacks[] statusCallbacksArr = this.mStatusCallbacks;
            if (i2 >= statusCallbacksArr.length) {
                return;
            }
            GnssStatusProvider gnssStatusProvider = (GnssStatusProvider) statusCallbacksArr[i2];
            gnssStatusProvider.getClass();
            final boolean z = i != 1 ? (i == 2 || i == 4) ? false : gnssStatusProvider.mIsNavigating : true;
            if (z != gnssStatusProvider.mIsNavigating) {
                gnssStatusProvider.mIsNavigating = z;
                if (z) {
                    final int i3 = 0;
                    gnssStatusProvider.deliverToListeners(new ListenerExecutor.ListenerOperation() { // from class: com.android.server.location.gnss.GnssStatusProvider$$ExternalSyntheticLambda2
                        public final void operate(Object obj) {
                            IGnssStatusListener iGnssStatusListener = (IGnssStatusListener) obj;
                            switch (i3) {
                                case 0:
                                    iGnssStatusListener.onGnssStarted();
                                    break;
                                default:
                                    iGnssStatusListener.onGnssStopped();
                                    break;
                            }
                        }
                    });
                } else {
                    final int i4 = 1;
                    gnssStatusProvider.deliverToListeners(new ListenerExecutor.ListenerOperation() { // from class: com.android.server.location.gnss.GnssStatusProvider$$ExternalSyntheticLambda2
                        public final void operate(Object obj) {
                            IGnssStatusListener iGnssStatusListener = (IGnssStatusListener) obj;
                            switch (i4) {
                                case 0:
                                    iGnssStatusListener.onGnssStarted();
                                    break;
                                default:
                                    iGnssStatusListener.onGnssStopped();
                                    break;
                            }
                        }
                    });
                }
                final NSConnectionHelper nSConnectionHelper = gnssStatusProvider.mNSConnectionHelper;
                if (nSConnectionHelper.mHasNsflpFeature && (handler = nSConnectionHelper.mHandler) != null) {
                    handler.post(new Runnable() { // from class: com.android.server.location.nsflp.NSConnectionHelper$$ExternalSyntheticLambda5
                        @Override // java.lang.Runnable
                        public final void run() {
                            NSConnectionHelper nSConnectionHelper2 = NSConnectionHelper.this;
                            boolean z2 = z;
                            nSConnectionHelper2.getClass();
                            try {
                                INSLocationManager iNSLocationManager = nSConnectionHelper2.mMonitorService;
                                if (iNSLocationManager != null) {
                                    iNSLocationManager.onGnssEngineStatusUpdated(z2);
                                }
                            } catch (Exception e) {
                                Log.e("NSConnectionHelper", e.toString());
                            }
                        }
                    });
                }
                GnssManagerService$$ExternalSyntheticLambda0 gnssManagerService$$ExternalSyntheticLambda0 = gnssStatusProvider.mOnStatusChanged;
                if (gnssManagerService$$ExternalSyntheticLambda0 != null && (iSLocationManager = gnssManagerService$$ExternalSyntheticLambda0.f$0.mSLocationService) != null) {
                    try {
                        iSLocationManager.onGnssStatusChanged(z);
                    } catch (RemoteException e) {
                        Log.e("SLocationProxy", e.toString());
                    }
                }
            }
            i2++;
        }
    }

    public final /* synthetic */ void lambda$reportSvStatus$8(int i, int[] iArr, float[] fArr, float[] fArr2, float[] fArr3, float[] fArr4, float[] fArr5) throws Exception {
        GnssStatus wrap = GnssStatus.wrap(i, iArr, fArr, fArr2, fArr3, fArr4, fArr5);
        int i2 = 0;
        while (true) {
            SvStatusCallbacks[] svStatusCallbacksArr = this.mSvStatusCallbacks;
            if (i2 >= svStatusCallbacksArr.length) {
                return;
            }
            svStatusCallbacksArr[i2].onReportSvStatus(wrap);
            i2++;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v0, types: [com.android.server.location.gnss.sec.LppeFusedLocationHelper$$ExternalSyntheticLambda3, java.lang.Runnable] */
    public final void lambda$requestCivicAddress$40(final double d, final double d2, final double d3) throws Exception {
        final LppeFusedLocationHelper lppeFusedLocationHelper = (LppeFusedLocationHelper) this.mLppeHelperCallbacks;
        lppeFusedLocationHelper.getClass();
        Log.d("LocationX", "onRequestCivicAddress");
        lppeFusedLocationHelper.mIsCivicAddressRequested = true;
        ?? r10 = new Runnable(d, d2, d3) { // from class: com.android.server.location.gnss.sec.LppeFusedLocationHelper$$ExternalSyntheticLambda3
            public final /* synthetic */ double f$1;
            public final /* synthetic */ double f$2;

            /* JADX WARN: Removed duplicated region for block: B:12:0x0057  */
            /* JADX WARN: Removed duplicated region for block: B:15:0x0064  */
            /* JADX WARN: Removed duplicated region for block: B:18:0x0071  */
            /* JADX WARN: Removed duplicated region for block: B:21:0x007e  */
            /* JADX WARN: Removed duplicated region for block: B:24:0x008b  */
            /* JADX WARN: Removed duplicated region for block: B:27:0x0098  */
            /* JADX WARN: Removed duplicated region for block: B:30:0x00a5  */
            /* JADX WARN: Removed duplicated region for block: B:33:0x00b2  */
            /* JADX WARN: Removed duplicated region for block: B:36:0x00bf  */
            /* JADX WARN: Removed duplicated region for block: B:39:0x00d0  */
            /* JADX WARN: Removed duplicated region for block: B:42:0x00e1  */
            /* JADX WARN: Removed duplicated region for block: B:45:0x00f0  */
            /* JADX WARN: Removed duplicated region for block: B:48:0x0103  */
            /* JADX WARN: Removed duplicated region for block: B:50:0x00fb  */
            /* JADX WARN: Removed duplicated region for block: B:51:0x00e8  */
            /* JADX WARN: Removed duplicated region for block: B:52:0x00da  */
            /* JADX WARN: Removed duplicated region for block: B:53:0x00c5  */
            /* JADX WARN: Removed duplicated region for block: B:54:0x00b8  */
            /* JADX WARN: Removed duplicated region for block: B:55:0x00ab  */
            /* JADX WARN: Removed duplicated region for block: B:56:0x009e  */
            /* JADX WARN: Removed duplicated region for block: B:57:0x0091  */
            /* JADX WARN: Removed duplicated region for block: B:58:0x0084  */
            /* JADX WARN: Removed duplicated region for block: B:59:0x0077  */
            /* JADX WARN: Removed duplicated region for block: B:60:0x006a  */
            /* JADX WARN: Removed duplicated region for block: B:61:0x005d  */
            /* JADX WARN: Removed duplicated region for block: B:8:0x003a  */
            @Override // java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void run() {
                /*
                    Method dump skipped, instructions count: 277
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.server.location.gnss.sec.LppeFusedLocationHelper$$ExternalSyntheticLambda3.run():void");
            }
        };
        lppeFusedLocationHelper.mCivicAddressTimeout = r10;
        if (lppeFusedLocationHelper.mHandler.postDelayed(r10, 2000L)) {
            Log.w("LocationX", "failed to add CivicAddress timeout to message queue.");
        }
    }

    public final void lambda$requestFlpLocation$35(int i) throws Exception {
        LppeFusedLocationHelper lppeFusedLocationHelper = (LppeFusedLocationHelper) this.mLppeHelperCallbacks;
        if (lppeFusedLocationHelper.mIsFlpRequested) {
            Log.d("LocationX", "already requested FlpLocation");
            return;
        }
        lppeFusedLocationHelper.mIsFlpRequested = true;
        Log.d("LocationX", "requestFlpLocation response time = " + i + "sec");
        long responseTime = LppeFusedLocationHelper.getResponseTime(i - 1, 20);
        LocationRequest.Builder locationSettingsIgnored = new LocationRequest.Builder(1000L).setMaxUpdates(1).setDurationMillis(responseTime).setQuality(102).setLocationSettingsIgnored(true);
        if (lppeFusedLocationHelper.mLocationManager.getProvider("fused") == null) {
            Log.w("LocationX", "Unable to request location.");
            return;
        }
        Log.d("LocationX", "Start LocationManager.FUSED_PROVIDER");
        lppeFusedLocationHelper.mLocationManager.requestLocationUpdates("fused", locationSettingsIgnored.build(), ConcurrentUtils.DIRECT_EXECUTOR, lppeFusedLocationHelper.mLocationListener);
        LppeFusedLocationHelper$$ExternalSyntheticLambda0 lppeFusedLocationHelper$$ExternalSyntheticLambda0 = new LppeFusedLocationHelper$$ExternalSyntheticLambda0(lppeFusedLocationHelper, 2);
        lppeFusedLocationHelper.mFlpTimeout = lppeFusedLocationHelper$$ExternalSyntheticLambda0;
        if (lppeFusedLocationHelper.mHandler.postDelayed(lppeFusedLocationHelper$$ExternalSyntheticLambda0, responseTime)) {
            return;
        }
        Log.w("LocationX", "failed to add FLP timeout to message queue.");
    }

    public final void lambda$requestLocation$27(final boolean z, final boolean z2) throws Exception {
        final GnssLocationProvider gnssLocationProvider = (GnssLocationProvider) this.mLocationRequestCallbacks;
        gnssLocationProvider.getClass();
        Log.d("GnssLocationProvider", "requestLocation. independentFromGnss: " + z + ", isUserEmergency: " + z2);
        gnssLocationProvider.postWithWakeLockHeld(new Runnable() { // from class: com.android.server.location.gnss.GnssLocationProvider$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                LocationListener gnssLocationProvider$$ExternalSyntheticLambda22;
                String str;
                GnssLocationProvider gnssLocationProvider2 = GnssLocationProvider.this;
                boolean z3 = z;
                boolean z4 = z2;
                long j = Settings.Global.getLong(gnssLocationProvider2.mContext.getContentResolver(), "gnss_hal_location_request_duration_millis", 10000L);
                if (j == 0) {
                    Log.i("GnssLocationProvider", "GNSS HAL location request is disabled by Settings.");
                    return;
                }
                LocationManager locationManager = (LocationManager) gnssLocationProvider2.mContext.getSystemService("location");
                LocationRequest.Builder maxUpdates = new LocationRequest.Builder(1000L).setMaxUpdates(1);
                if (z3 || !gnssLocationProvider2.isDeviceBasedHybridSupported()) {
                    gnssLocationProvider$$ExternalSyntheticLambda22 = new GnssLocationProvider$$ExternalSyntheticLambda22();
                    maxUpdates.setQuality(104);
                    str = "network";
                } else {
                    gnssLocationProvider$$ExternalSyntheticLambda22 = new GnssLocationProvider$$ExternalSyntheticLambda23(gnssLocationProvider2, 0);
                    maxUpdates.setQuality(100);
                    str = "fused";
                }
                if (gnssLocationProvider2.mNIHandler.getInEmergency() || gnssLocationProvider2.isKOREmergency(z4)) {
                    gnssLocationProvider2.mGnssConfiguration.getClass();
                    GnssConfiguration.HalInterfaceVersion halInterfaceVersion = GnssConfiguration.getHalInterfaceVersion();
                    if (z4 || halInterfaceVersion.mMajor < 2) {
                        maxUpdates.setLocationSettingsIgnored(true);
                        j *= 3;
                    }
                    if (gnssLocationProvider2.isKOREmergency(z4)) {
                        gnssLocationProvider2.releaseDozeMode();
                    }
                }
                maxUpdates.setDurationMillis(j);
                Log.i("GnssLocationProvider", String.format("GNSS HAL Requesting location updates from %s provider for %d millis.", str, Long.valueOf(j)));
                if (locationManager.getProvider(str) != null) {
                    locationManager.requestLocationUpdates(str, maxUpdates.build(), ConcurrentUtils.DIRECT_EXECUTOR, gnssLocationProvider$$ExternalSyntheticLambda22);
                }
            }
        });
    }

    public final void lambda$requestLppeCommonIesCapability$34() throws Exception {
        LppeFusedLocationHelper lppeFusedLocationHelper = (LppeFusedLocationHelper) this.mLppeHelperCallbacks;
        lppeFusedLocationHelper.getClass();
        Log.d("LocationX", "requestLppeCommonIesCapability");
        Log.d("LocationX", "handleUpdateCommonIesCapability : highAccCapa Supported ");
        lppeFusedLocationHelper.mGnssNative.injectLppeComIeCapability(0, false, false, false, 128, false, false, false, false, false);
    }

    public final /* synthetic */ void lambda$requestRefLocation$29() throws Exception {
        this.mLocationRequestCallbacks.onRequestRefLocation();
    }

    public final void lambda$requestRefLocationSec$30() throws Exception {
        GnssLocationProviderSec gnssLocationProviderSec = (GnssLocationProviderSec) this.mLocationRequestCallbacks;
        gnssLocationProviderSec.getClass();
        Log.d("GnssLocationProvider_ex", "onRequestRefLocationSec");
        gnssLocationProviderSec.isSehRefLocation = true;
        gnssLocationProviderSec.requestRefLocationSec();
    }

    public final /* synthetic */ void lambda$requestSetID$26(int i) throws Exception {
        this.mAGpsCallbacks.onRequestSetID(i);
    }

    public final void lambda$requestUbpCapability$36() throws Exception {
        LppeFusedLocationHelper lppeFusedLocationHelper = (LppeFusedLocationHelper) this.mLppeHelperCallbacks;
        lppeFusedLocationHelper.getClass();
        Log.d("LocationX", "requestUbpCapability");
        Sensor defaultSensor = lppeFusedLocationHelper.mSensorManager.getDefaultSensor(6);
        lppeFusedLocationHelper.mPressureSensor = defaultSensor;
        boolean z = defaultSensor != null;
        AccessibilityManagerService$$ExternalSyntheticOutline0.m("handleUpdateUBPCapability : isUbpSupported  ", "LocationX", z);
        lppeFusedLocationHelper.mGnssNative.injectUbpCapability(false, false, false, z);
    }

    public final void lambda$requestUbpInfo$37(int i) throws Exception {
        LppeFusedLocationHelper lppeFusedLocationHelper = (LppeFusedLocationHelper) this.mLppeHelperCallbacks;
        if (lppeFusedLocationHelper.mIsUbpRequested) {
            Log.d("LocationX", "already requested UbpInfo");
            return;
        }
        lppeFusedLocationHelper.mIsUbpRequested = true;
        lppeFusedLocationHelper.mSensorEventListener = lppeFusedLocationHelper.new UBPSensorEventListener();
        NetworkScoreService$$ExternalSyntheticOutline0.m(i, "requestUbpInfo response time = ", "sec", "LocationX");
        if (lppeFusedLocationHelper.mPressureSensor == null) {
            Log.d("LocationX", "requestUBPInfo : Caution (PressureSensor is null) ");
            lppeFusedLocationHelper.mPressureSensor = lppeFusedLocationHelper.mSensorManager.getDefaultSensor(6);
        }
        lppeFusedLocationHelper.mSensorManager.registerListener(lppeFusedLocationHelper.mSensorEventListener, lppeFusedLocationHelper.mPressureSensor, 2);
        lppeFusedLocationHelper.mGnssVendorConfig.getClass();
        long responseTime = new File("vendor/etc/gnss/mnl.prop").exists() ? LppeFusedLocationHelper.getResponseTime(i, 15) : LppeFusedLocationHelper.getResponseTime(i - 1, 14);
        LppeFusedLocationHelper$$ExternalSyntheticLambda0 lppeFusedLocationHelper$$ExternalSyntheticLambda0 = new LppeFusedLocationHelper$$ExternalSyntheticLambda0(lppeFusedLocationHelper, 1);
        lppeFusedLocationHelper.mUbpTimeout = lppeFusedLocationHelper$$ExternalSyntheticLambda0;
        if (lppeFusedLocationHelper.mHandler.postDelayed(lppeFusedLocationHelper$$ExternalSyntheticLambda0, responseTime)) {
            return;
        }
        Log.w("LocationX", "failed to add UBP timeout to message queue.");
    }

    public final void lambda$requestUtcTime$28() throws Exception {
        ((GnssLocationProvider) this.mTimeCallbacks).demandUtcTimeInjection();
    }

    public final void lambda$requestWlanCapability$38() throws Exception {
        LppeFusedLocationHelper lppeFusedLocationHelper = (LppeFusedLocationHelper) this.mLppeHelperCallbacks;
        lppeFusedLocationHelper.getClass();
        Log.d("LocationX", "requestWlanCapability");
        Log.d("LocationX", "handleUpdateWLANCapbility : ecidMeasSupported - 35872, wlanTypesSupported - 64512");
        lppeFusedLocationHelper.mGnssNative.injectWlanCapability(35872, 64512, 0L, 0, 0, 0);
    }

    public final void lambda$requestWlanScanInfo$39(int i) throws Exception {
        LppeFusedLocationHelper lppeFusedLocationHelper = (LppeFusedLocationHelper) this.mLppeHelperCallbacks;
        if (lppeFusedLocationHelper.mIsWifiScanRequested) {
            Log.d("LocationX", "already requested WlanScanInfo.");
            return;
        }
        lppeFusedLocationHelper.mIsRetryWifiScan = false;
        lppeFusedLocationHelper.mIsWifiScanRequested = true;
        Log.d("LocationX", "requestWlanScanInfo. response time = " + i + "sec");
        WifiScanner.ScanSettings scanSettings = new WifiScanner.ScanSettings();
        scanSettings.band = 15;
        scanSettings.type = 0;
        scanSettings.ignoreLocationSettings = true;
        lppeFusedLocationHelper.mWifiScanner.startScan(scanSettings, lppeFusedLocationHelper.new LppeWlanScanListener());
        long responseTime = LppeFusedLocationHelper.getResponseTime(i - 1, 11);
        LppeFusedLocationHelper$$ExternalSyntheticLambda0 lppeFusedLocationHelper$$ExternalSyntheticLambda0 = new LppeFusedLocationHelper$$ExternalSyntheticLambda0(lppeFusedLocationHelper, 0);
        lppeFusedLocationHelper.mWlanTimeout = lppeFusedLocationHelper$$ExternalSyntheticLambda0;
        if (lppeFusedLocationHelper.mHandler.postDelayed(lppeFusedLocationHelper$$ExternalSyntheticLambda0, responseTime)) {
            return;
        }
        Log.w("LocationX", "failed to add WLAN timeout to message queue.");
    }

    public final void onCapabilitiesChanged(GnssCapabilities gnssCapabilities, GnssCapabilities gnssCapabilities2) {
        Binder.withCleanCallingIdentity(new GnssNative$$ExternalSyntheticLambda31(this, gnssCapabilities2, gnssCapabilities, 0));
    }

    public final boolean pauseGeofence(int i) {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.getClass();
        return native_pause_geofence(i);
    }

    public final void psdsDownloadRequest(int i) {
        Binder.withCleanCallingIdentity(new GnssNative$$ExternalSyntheticLambda1(this, i, 2));
    }

    public final int readNmea(byte[] bArr, int i) {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.getClass();
        return native_read_nmea(bArr, i);
    }

    public final void register() {
        Preconditions.checkState(!this.mRegistered);
        this.mRegistered = true;
        int i = 0;
        initializeGnss(false);
        Log.i("GnssManager", "gnss hal started");
        while (true) {
            BaseCallbacks[] baseCallbacksArr = this.mBaseCallbacks;
            if (i >= baseCallbacksArr.length) {
                return;
            }
            baseCallbacksArr[i].onHalStarted();
            i++;
        }
    }

    public final boolean removeGeofence(int i) {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.getClass();
        return native_remove_geofence(i);
    }

    public final void reportAGpsStatus(final int i, final int i2, final byte[] bArr) {
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda8
            public final void runOrThrow() {
                GnssNative gnssNative = GnssNative.this;
                int i3 = i;
                int i4 = i2;
                byte[] bArr2 = bArr;
                int i5 = GnssNative.GNSS_POSITION_MODE_STANDALONE;
                gnssNative.lambda$reportAGpsStatus$9(i3, i4, bArr2);
            }
        });
    }

    public final void reportAntennaInfo(List list) {
        Binder.withCleanCallingIdentity(new GnssNative$$ExternalSyntheticLambda10(this, list, 3));
    }

    public final void reportExtraAssertMessage(String str) {
        mGnssVendorConfig.getClass();
        if (!GnssVendorConfig.isIzatServiceEnabled()) {
            Log.d("GnssManager", "reportExtraMessage, message = " + str);
            if (isGnssAssertMessage(str)) {
                if ("true".equals(this.enable_detecting_gnss_assert) && SystemProperties.get("dev.gnss.silentloggingIssueTracker").equals("ON")) {
                    long currentTimeMillis = System.currentTimeMillis();
                    if (currentTimeMillis - this.prevAssertTS <= 3000) {
                        this.continuousAssertCount++;
                    } else {
                        this.continuousAssertCount = 0;
                    }
                    if (this.continuousAssertCount > 4) {
                        ActToolHelper actToolHelper = this.mActToolHelper;
                        Context context = this.mContext;
                        actToolHelper.getClass();
                        Log.i("ActToolHelper", "sendBroadcast to ActTool : event=" + str);
                        Intent intent = new Intent();
                        intent.setAction("com.salab.act.intent.LOG_ACT");
                        intent.putExtra("dumpname", "GNSS_ASSERT");
                        intent.putExtra("CONFI_GNSS_ASSERT", str);
                        context.sendBroadcast(intent);
                        this.continuousAssertCount = 0;
                    }
                    this.prevAssertTS = currentTimeMillis;
                }
                if (GnssVendorConfig.isBroadcomGnss()) {
                    Log.d("GnssManager", "GNSS report assert, reset it");
                    this.mGnssHal.getClass();
                    native_init();
                }
            }
        }
        this.mNSConnectionHelper.onGnssEventUpdated(str);
    }

    public final void reportGeofenceAddStatus(int i, int i2) {
        Binder.withCleanCallingIdentity(new GnssNative$$ExternalSyntheticLambda3(this, i, i2, 1));
    }

    public final void reportGeofencePauseStatus(int i, int i2) {
        Binder.withCleanCallingIdentity(new GnssNative$$ExternalSyntheticLambda3(this, i, i2, 0));
    }

    public final void reportGeofenceRemoveStatus(int i, int i2) {
        Binder.withCleanCallingIdentity(new GnssNative$$ExternalSyntheticLambda3(this, i, i2, 2));
    }

    public final void reportGeofenceResumeStatus(int i, int i2) {
        Binder.withCleanCallingIdentity(new GnssNative$$ExternalSyntheticLambda3(this, i, i2, 3));
    }

    public final void reportGeofenceStatus(final int i, final Location location) {
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda7
            public final void runOrThrow() {
                GnssNative gnssNative = GnssNative.this;
                int i2 = i;
                Location location2 = location;
                int i3 = GnssNative.GNSS_POSITION_MODE_STANDALONE;
                gnssNative.lambda$reportGeofenceStatus$21(i2, location2);
            }
        });
    }

    public final void reportGeofenceTransition(final int i, final Location location, final int i2, final long j) {
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda15
            public final void runOrThrow() {
                GnssNative gnssNative = GnssNative.this;
                int i3 = i;
                Location location2 = location;
                int i4 = i2;
                long j2 = j;
                int i5 = GnssNative.GNSS_POSITION_MODE_STANDALONE;
                gnssNative.lambda$reportGeofenceTransition$20(i3, location2, i4, j2);
            }
        });
    }

    public final void reportGnssPowerStats(final GnssPowerStats gnssPowerStats) {
        synchronized (this.mPowerStatsLock) {
            try {
                this.mHandler.removeCallbacks(this.mPowerStatsTimeoutCallback);
                if (gnssPowerStats != null) {
                    this.mLastKnownPowerStats = gnssPowerStats;
                }
                this.mPendingPowerStatsCallbacks.forEach(new Consumer() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda12
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        GnssPowerStats gnssPowerStats2 = GnssPowerStats.this;
                        int i = GnssNative.GNSS_POSITION_MODE_STANDALONE;
                        ((GnssNative.PowerStatsCallback) obj).onReportPowerStats(gnssPowerStats2);
                    }
                });
                this.mPendingPowerStatsCallbacks.clear();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void reportGnssServiceDied() {
        Log.e("GnssManager", "gnss hal died - restarting shortly...");
        LocationServiceThread.getExecutor().execute(new GnssNative$$ExternalSyntheticLambda0(this, 1));
    }

    public final void reportLocation(final boolean z, final Location location) {
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda36
            public final void runOrThrow() {
                GnssNative gnssNative = GnssNative.this;
                boolean z2 = z;
                Location location2 = location;
                int i = GnssNative.GNSS_POSITION_MODE_STANDALONE;
                gnssNative.lambda$reportLocation$6(z2, location2);
            }
        });
    }

    public final void reportLocationBatch(Location[] locationArr) {
        Binder.withCleanCallingIdentity(new GnssNative$$ExternalSyntheticLambda10(this, locationArr, 0));
    }

    public final void reportMeasurementData(GnssMeasurementsEvent gnssMeasurementsEvent) {
        Binder.withCleanCallingIdentity(new GnssNative$$ExternalSyntheticLambda10(this, gnssMeasurementsEvent, 1));
    }

    public final void reportNavigationMessage(GnssNavigationMessage gnssNavigationMessage) {
        Binder.withCleanCallingIdentity(new GnssNative$$ExternalSyntheticLambda10(this, gnssNavigationMessage, 2));
    }

    public final void reportNfwNotification(final String str, final byte b, final String str2, final byte b2, final String str3, final byte b3, final boolean z, final boolean z2) {
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda39
            public final void runOrThrow() {
                GnssNative gnssNative = GnssNative.this;
                String str4 = str;
                byte b4 = b;
                String str5 = str2;
                byte b5 = b2;
                String str6 = str3;
                byte b6 = b3;
                boolean z3 = z;
                boolean z4 = z2;
                int i = GnssNative.GNSS_POSITION_MODE_STANDALONE;
                gnssNative.lambda$reportNfwNotification$31(str4, b4, str5, b5, str6, b6, z3, z4);
            }
        });
    }

    public final void reportNmea(final long j) {
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda35
            public final void runOrThrow() {
                GnssNative gnssNative = GnssNative.this;
                long j2 = j;
                int i = GnssNative.GNSS_POSITION_MODE_STANDALONE;
                gnssNative.lambda$reportNmea$10(j2);
            }
        });
    }

    public final void reportStatus(int i) {
        Binder.withCleanCallingIdentity(new GnssNative$$ExternalSyntheticLambda1(this, i, 0));
    }

    public final void reportSvStatus(final int i, final int[] iArr, final float[] fArr, final float[] fArr2, final float[] fArr3, final float[] fArr4, final float[] fArr5) {
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda17
            public final void runOrThrow() {
                GnssNative gnssNative = GnssNative.this;
                int i2 = i;
                int[] iArr2 = iArr;
                float[] fArr6 = fArr;
                float[] fArr7 = fArr2;
                float[] fArr8 = fArr3;
                float[] fArr9 = fArr4;
                float[] fArr10 = fArr5;
                int i3 = GnssNative.GNSS_POSITION_MODE_STANDALONE;
                gnssNative.lambda$reportSvStatus$8(i2, iArr2, fArr6, fArr7, fArr8, fArr9, fArr10);
            }
        });
    }

    public final void requestCivicAddress(final double d, final double d2, final double d3) {
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda13
            public final void runOrThrow() {
                GnssNative gnssNative = GnssNative.this;
                double d4 = d;
                double d5 = d2;
                double d6 = d3;
                int i = GnssNative.GNSS_POSITION_MODE_STANDALONE;
                gnssNative.lambda$requestCivicAddress$40(d4, d5, d6);
            }
        });
    }

    public final void requestFlpLocation(int i) {
        Binder.withCleanCallingIdentity(new GnssNative$$ExternalSyntheticLambda1(this, i, 3));
    }

    public final void requestLocation(final boolean z, final boolean z2) {
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda19
            public final void runOrThrow() {
                GnssNative gnssNative = GnssNative.this;
                boolean z3 = z;
                boolean z4 = z2;
                int i = GnssNative.GNSS_POSITION_MODE_STANDALONE;
                gnssNative.lambda$requestLocation$27(z3, z4);
            }
        });
    }

    public final void requestLppeCommonIesCapability() {
        Binder.withCleanCallingIdentity(new GnssNative$$ExternalSyntheticLambda2(this, 2));
    }

    public final void requestPowerStats(Executor executor, PowerStatsCallback powerStatsCallback) {
        Preconditions.checkState(this.mRegistered);
        synchronized (this.mPowerStatsLock) {
            try {
                this.mPendingPowerStatsCallbacks.add(new GnssNative$$ExternalSyntheticLambda20(1, executor, powerStatsCallback));
                if (this.mPendingPowerStatsCallbacks.size() == 1) {
                    this.mGnssHal.getClass();
                    native_request_power_stats();
                    this.mHandler.postDelayed(this.mPowerStatsTimeoutCallback, 100L);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final GnssPowerStats requestPowerStatsBlocking() {
        AtomicReference atomicReference = new AtomicReference();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        requestPowerStats(new SystemServerInitThreadPool$$ExternalSyntheticLambda0(), new GnssNative$$ExternalSyntheticLambda20(0, atomicReference, countDownLatch));
        try {
            countDownLatch.await(100L, TimeUnit.MILLISECONDS);
        } catch (InterruptedException unused) {
            Log.d("GnssManager", "Interrupted while waiting for power stats");
            Thread.currentThread().interrupt();
        }
        return (GnssPowerStats) atomicReference.get();
    }

    public final void requestRefLocation() {
        Binder.withCleanCallingIdentity(new GnssNative$$ExternalSyntheticLambda2(this, 5));
    }

    public final void requestRefLocationSec() {
        Binder.withCleanCallingIdentity(new GnssNative$$ExternalSyntheticLambda2(this, 3));
    }

    public final void requestSetID(int i) {
        Binder.withCleanCallingIdentity(new GnssNative$$ExternalSyntheticLambda1(this, i, 4));
    }

    public final void requestUbpCapability() {
        Binder.withCleanCallingIdentity(new GnssNative$$ExternalSyntheticLambda2(this, 1));
    }

    public final void requestUbpInfo(int i) {
        Binder.withCleanCallingIdentity(new GnssNative$$ExternalSyntheticLambda1(this, i, 5));
    }

    public final void requestUtcTime() {
        Binder.withCleanCallingIdentity(new GnssNative$$ExternalSyntheticLambda2(this, 4));
    }

    public final void requestWlanCapability() {
        Binder.withCleanCallingIdentity(new GnssNative$$ExternalSyntheticLambda2(this, 0));
    }

    public final void requestWlanScanInfo(int i) {
        Binder.withCleanCallingIdentity(new GnssNative$$ExternalSyntheticLambda1(this, i, 1));
    }

    public void restartHal() {
        initializeGnss(true);
        Log.e("GnssManager", "gnss hal restarted");
        int i = 0;
        while (true) {
            BaseCallbacks[] baseCallbacksArr = this.mBaseCallbacks;
            if (i >= baseCallbacksArr.length) {
                return;
            }
            baseCallbacksArr[i].onHalRestarted();
            i++;
        }
    }

    public final boolean resumeGeofence(int i, int i2) {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.getClass();
        return native_resume_geofence(i, i2);
    }

    public final void sendSuplNiMessage(byte[] bArr, int i) {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.getClass();
        native_send_supl_ni_message(bArr, i);
    }

    public final void setAGpsCallbacks(AGpsCallbacks aGpsCallbacks) {
        Preconditions.checkState(!this.mRegistered);
        Preconditions.checkState(this.mAGpsCallbacks == null);
        Objects.requireNonNull(aGpsCallbacks);
        this.mAGpsCallbacks = aGpsCallbacks;
    }

    public final void setAgpsReferenceLocationCellId(int i, int i2, int i3, int i4, long j, int i5, int i6) {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.getClass();
        native_agps_set_ref_location_cellid(i, i2, i3, i4, j, i5, i6);
    }

    public final void setAgpsReferenceLocationCellId(int i, int i2, int i3, int i4, long j, int i5, int i6, int i7) {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.getClass();
        native_agps_set_ref_location_cellid(i, i2, i3, i4, j, i5, i6, i7);
    }

    public final void setAgpsServer(int i, String str, int i2) {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.getClass();
        native_set_agps_server(i, str, i2);
    }

    public final void setAgpsSetId(int i, String str) {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.getClass();
        native_agps_set_id(i, str);
    }

    public final void setGeofenceCallbacks(GeofenceCallbacks geofenceCallbacks) {
        Preconditions.checkState(!this.mRegistered);
        Preconditions.checkState(this.mGeofenceCallbacks == null);
        Objects.requireNonNull(geofenceCallbacks);
        this.mGeofenceCallbacks = geofenceCallbacks;
    }

    public final void setGnssHardwareModelName(String str) {
        this.mHardwareModelName = str;
    }

    public final void setGnssYearOfHardware(int i) {
        this.mHardwareYear = i;
    }

    public final void setLocationRequestCallbacks(LocationRequestCallbacks locationRequestCallbacks) {
        Preconditions.checkState(!this.mRegistered);
        Preconditions.checkState(this.mLocationRequestCallbacks == null);
        Objects.requireNonNull(locationRequestCallbacks);
        this.mLocationRequestCallbacks = locationRequestCallbacks;
    }

    public final void setLppeHelperCallbacks(LppeHelperCallbacks lppeHelperCallbacks) {
        Preconditions.checkState(!this.mRegistered);
        Preconditions.checkState(this.mLppeHelperCallbacks == null);
        Objects.requireNonNull(lppeHelperCallbacks);
        this.mLppeHelperCallbacks = lppeHelperCallbacks;
    }

    public final void setNotificationCallbacks(NotificationCallbacks notificationCallbacks) {
        Preconditions.checkState(!this.mRegistered);
        Preconditions.checkState(this.mNotificationCallbacks == null);
        Objects.requireNonNull(notificationCallbacks);
        this.mNotificationCallbacks = notificationCallbacks;
    }

    public final boolean setPositionMode(int i, int i2, int i3, int i4, int i5, boolean z) {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.getClass();
        return native_set_position_mode(i, i2, i3, i4, i5, z);
    }

    public final void setPsdsCallbacks(PsdsCallbacks psdsCallbacks) {
        Preconditions.checkState(!this.mRegistered);
        Preconditions.checkState(this.mPsdsCallbacks == null);
        Objects.requireNonNull(psdsCallbacks);
        this.mPsdsCallbacks = psdsCallbacks;
    }

    public final void setSignalTypeCapabilities(List list) {
        GnssCapabilities gnssCapabilities = this.mCapabilities;
        GnssCapabilities withSignalTypes = gnssCapabilities.withSignalTypes(list);
        this.mCapabilities = withSignalTypes;
        onCapabilitiesChanged(gnssCapabilities, withSignalTypes);
    }

    public final void setSubHalMeasurementCorrectionsCapabilities(int i) {
        GnssCapabilities gnssCapabilities = this.mCapabilities;
        GnssCapabilities withSubHalMeasurementCorrectionsFlags = gnssCapabilities.withSubHalMeasurementCorrectionsFlags(i);
        this.mCapabilities = withSubHalMeasurementCorrectionsFlags;
        onCapabilitiesChanged(gnssCapabilities, withSubHalMeasurementCorrectionsFlags);
    }

    public final void setSubHalPowerIndicationCapabilities(int i) {
        GnssCapabilities gnssCapabilities = this.mCapabilities;
        GnssCapabilities withSubHalPowerFlags = gnssCapabilities.withSubHalPowerFlags(i);
        this.mCapabilities = withSubHalPowerFlags;
        onCapabilitiesChanged(gnssCapabilities, withSubHalPowerFlags);
    }

    public final void setTimeCallbacks(TimeCallbacks timeCallbacks) {
        Preconditions.checkState(!this.mRegistered);
        Preconditions.checkState(this.mTimeCallbacks == null);
        Objects.requireNonNull(timeCallbacks);
        this.mTimeCallbacks = timeCallbacks;
    }

    public final void setTopHalCapabilities(int i, boolean z) {
        int i2 = i | this.mTopFlags;
        this.mTopFlags = i2;
        GnssCapabilities gnssCapabilities = this.mCapabilities;
        GnssCapabilities withTopHalFlags = gnssCapabilities.withTopHalFlags(i2, z);
        this.mCapabilities = withTopHalFlags;
        onCapabilitiesChanged(gnssCapabilities, withTopHalFlags);
    }

    public final boolean start() {
        Preconditions.checkState(this.mRegistered);
        this.mStartRealtimeMs = SystemClock.elapsedRealtime();
        this.mHasFirstFix = false;
        this.mGnssHal.getClass();
        return native_start();
    }

    public final boolean startAntennaInfoListening() {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.getClass();
        return native_start_antenna_info_listening();
    }

    public final boolean startBatch(long j, float f, boolean z) {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.getClass();
        return native_start_batch(j, f, z);
    }

    public final boolean startMeasurementCollection(boolean z, boolean z2, int i) {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.getClass();
        return native_start_measurement_collection(z, z2, i);
    }

    public final boolean startNavigationMessageCollection() {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.getClass();
        return native_start_navigation_message_collection();
    }

    public final boolean startNmeaMessageCollection() {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.getClass();
        return native_start_nmea_message_collection();
    }

    public final boolean startSvStatusCollection() {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.getClass();
        return native_start_sv_status_collection();
    }

    public final boolean stop() {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.getClass();
        return native_stop();
    }

    public final boolean stopAntennaInfoListening() {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.getClass();
        return native_stop_antenna_info_listening();
    }

    public final void stopBatch() {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.getClass();
        native_stop_batch();
    }

    public final boolean stopMeasurementCollection() {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.getClass();
        return native_stop_measurement_collection();
    }

    public final boolean stopNavigationMessageCollection() {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.getClass();
        return native_stop_navigation_message_collection();
    }

    public final boolean stopNmeaMessageCollection() {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.getClass();
        return native_stop_nmea_message_collection();
    }

    public final boolean stopSvStatusCollection() {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.getClass();
        return native_stop_sv_status_collection();
    }
}
