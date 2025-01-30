package com.android.server.location.gnss.hal;

import android.content.Context;
import android.location.GnssCapabilities;
import android.location.GnssMeasurementCorrections;
import android.location.GnssMeasurementsEvent;
import android.location.GnssNavigationMessage;
import android.location.GnssStatus;
import android.location.Location;
import android.os.Binder;
import android.os.Handler;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.util.Log;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.Preconditions;
import com.android.server.location.LocationServiceThread;
import com.android.server.location.gnss.GnssConfiguration;
import com.android.server.location.gnss.GnssPowerStats;
import com.android.server.location.gnss.hal.GnssNative;
import com.android.server.location.gnss.sec.ActToolHelper;
import com.android.server.location.gnss.sec.CarrierConfig;
import com.android.server.location.gnss.sec.GnssVendorConfig;
import com.android.server.location.injector.EmergencyHelper;
import com.android.server.location.injector.Injector;
import com.android.server.location.nsflp.NSConnectionHelper;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
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
    public GnssCapabilities mCapabilities = new GnssCapabilities.Builder().build();
    public GnssPowerStats mPowerStats = null;
    public int mHardwareYear = 0;
    public String mHardwareModelName = null;
    public long mStartRealtimeMs = 0;
    public boolean mHasFirstFix = false;
    public long prevAssertTS = 0;
    public int continuousAssertCount = 0;

    public interface AGpsCallbacks {
        void onReportAGpsStatus(int i, int i2, byte[] bArr);

        void onRequestSetID(int i);
    }

    public interface AntennaInfoCallbacks {
        void onReportAntennaInfo(List list);
    }

    public interface BaseCallbacks {
        default void onCapabilitiesChanged(GnssCapabilities gnssCapabilities, GnssCapabilities gnssCapabilities2) {
        }

        void onHalRestarted();

        default void onHalStarted() {
        }
    }

    public interface GeofenceCallbacks {
        void onReportGeofenceAddStatus(int i, int i2);

        void onReportGeofencePauseStatus(int i, int i2);

        void onReportGeofenceRemoveStatus(int i, int i2);

        void onReportGeofenceResumeStatus(int i, int i2);

        void onReportGeofenceStatus(int i, Location location);

        void onReportGeofenceTransition(int i, Location location, int i2, long j);
    }

    public interface LocationCallbacks {
        void onReportLocation(boolean z, Location location);

        void onReportLocations(Location[] locationArr);
    }

    public interface LocationRequestCallbacks {
        void onRequestLocation(boolean z, boolean z2);

        void onRequestRefLocation();

        void onRequestRefLocationSec();
    }

    public interface LppeHelperCallbacks {
        void onRequestCivicAddress(double d, double d2, double d3);

        void onRequestFlpLocation(int i);

        void onRequestLppeCommonIesCapability();

        void onRequestUbpCapability();

        void onRequestUbpInfo(int i);

        void onRequestWifiCapability();

        void onRequestWifiScan(int i);
    }

    public interface MeasurementCallbacks {
        void onReportMeasurements(GnssMeasurementsEvent gnssMeasurementsEvent);
    }

    public interface NavigationMessageCallbacks {
        void onReportNavigationMessage(GnssNavigationMessage gnssNavigationMessage);
    }

    public interface NmeaCallbacks {
        void onReportNmea(long j);
    }

    public interface NotificationCallbacks {
        boolean isInEmergencySessionSEC();

        void onReportNfwNotification(String str, byte b, String str2, byte b2, String str3, byte b3, boolean z, boolean z2);

        void onReportNiNotification(int i, int i2, int i3, int i4, int i5, String str, String str2, int i6, int i7);
    }

    public interface PsdsCallbacks {
        void onRequestPsdsDownload(int i);
    }

    public interface StatusCallbacks {
        void onReportFirstFix(int i);

        void onReportStatus(int i);
    }

    public interface SvStatusCallbacks {
        void onReportSvStatus(GnssStatus gnssStatus);
    }

    public interface TimeCallbacks {
        void onRequestUtcTime();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_add_geofence(int i, double d, double d2, double d3, int i2, int i3, int i4, int i5);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void native_agps_set_id(int i, String str);

    private static native void native_agps_set_ref_location_cellid(int i, int i2, int i3, int i4, int i5, int i6);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void native_agps_set_ref_location_cellid(int i, int i2, int i3, int i4, long j, int i5, int i6);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void native_agps_set_ref_location_cellid(int i, int i2, int i3, int i4, long j, int i5, int i6, int i7);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void native_class_init_once();

    /* JADX INFO: Access modifiers changed from: private */
    public static native void native_cleanup();

    /* JADX INFO: Access modifiers changed from: private */
    public static native void native_cleanup_batching();

    /* JADX INFO: Access modifiers changed from: private */
    public static native void native_configuration_update_extension(String str);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void native_delete_aiding_data(int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void native_flush_batch();

    /* JADX INFO: Access modifiers changed from: private */
    public static native int native_get_batch_size();

    /* JADX INFO: Access modifiers changed from: private */
    public static native String native_get_internal_state();

    /* JADX INFO: Access modifiers changed from: private */
    public static native float native_get_seh_gnss_hal_version();

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_init();

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_init_batching();

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_init_extension_location_off();

    private static native boolean native_init_extension_once();

    /* JADX INFO: Access modifiers changed from: private */
    public native void native_init_once(boolean z);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void native_inject_best_location(int i, double d, double d2, double d3, float f, float f2, float f3, float f4, float f5, float f6, long j, int i2, long j2, double d4);

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_inject_civic_address(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void native_inject_flp_error(int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void native_inject_flp_location(int i, double d, double d2, double d3, float f, float f2, float f3, float f4, float f5, float f6, long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void native_inject_location(int i, double d, double d2, double d3, float f, float f2, float f3, float f4, float f5, float f6, long j, int i2, long j2, double d4);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void native_inject_lppe_com_ie_capability(int i, boolean z, boolean z2, boolean z3, int i2, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8);

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_inject_measurement_corrections(GnssMeasurementCorrections gnssMeasurementCorrections);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void native_inject_ni_supl_message_data(byte[] bArr, int i, int i2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void native_inject_psds_data(byte[] bArr, int i, int i2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void native_inject_time(long j, long j2, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void native_inject_ubp_capability(boolean z, boolean z2, boolean z3, boolean z4);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void native_inject_ubp_error(int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void native_inject_ubp_info(int i, int i2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void native_inject_wlan_capability(int i, int i2, long j, int i3, int i4, int i5);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void native_inject_wlan_error(int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void native_inject_wlan_scan_info(long[] jArr, int[] iArr, int[] iArr2, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_is_antenna_info_supported();

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_is_geofence_supported();

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_is_gnss_visibility_control_supported();

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_is_measurement_corrections_supported();

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_is_measurement_supported();

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_is_navigation_message_supported();

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_is_seh_gnss_aidl_hal();

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_is_supported();

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_pause_geofence(int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native int native_read_nmea(byte[] bArr, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_remove_geofence(int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void native_request_power_stats();

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_resume_geofence(int i, int i2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void native_send_ni_response(int i, int i2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void native_send_supl_ni_message(byte[] bArr, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void native_set_agps_server(int i, String str, int i2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_set_position_mode(int i, int i2, int i3, int i4, int i5, boolean z);

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_start();

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_start_antenna_info_listening();

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_start_batch(long j, float f, boolean z);

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_start_measurement_collection(boolean z, boolean z2, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_start_navigation_message_collection();

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_start_nmea_message_collection();

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_start_sv_status_collection();

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_stop();

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_stop_antenna_info_listening();

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_stop_batch();

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_stop_measurement_collection();

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_stop_navigation_message_collection();

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_stop_nmea_message_collection();

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_stop_sv_status_collection();

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_supports_psds();

    public static synchronized void setGnssHalForTest(GnssHal gnssHal) {
        synchronized (GnssNative.class) {
            Objects.requireNonNull(gnssHal);
            sGnssHal = gnssHal;
            sGnssHalInitialized = false;
            sInstance = null;
        }
    }

    public static synchronized void initializeHal() {
        synchronized (GnssNative.class) {
            if (!sGnssHalInitialized) {
                if (sGnssHal == null) {
                    sGnssHal = new GnssHal();
                }
                sGnssHal.classInitOnce();
                sGnssHalInitialized = true;
            }
        }
    }

    public static synchronized boolean isSupported() {
        boolean isSupported;
        synchronized (GnssNative.class) {
            if (!mGnssVendorConfig.isIzatServiceEnabled()) {
                Executors.newSingleThreadExecutor().execute(new Runnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda30
                    @Override // java.lang.Runnable
                    public final void run() {
                        GnssNative.lambda$isSupported$0();
                    }
                });
            }
            isHalInitialted = false;
            initializeHal();
            isSupported = sGnssHal.isSupported();
            isHalInitialted = true;
        }
        return isSupported;
    }

    public static /* synthetic */ void lambda$isSupported$0() {
        try {
            Thread.sleep(5000L);
            checkInit();
        } catch (InterruptedException unused) {
            Log.e("GnssManager", "checkInit() failed.");
        }
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

    public GnssNative(Context context, GnssHal gnssHal, Injector injector, GnssConfiguration gnssConfiguration) {
        this.mContext = context;
        Objects.requireNonNull(gnssHal);
        this.mGnssHal = gnssHal;
        this.mEmergencyHelper = injector.getEmergencyHelper();
        this.mConfiguration = gnssConfiguration;
        this.mNSConnectionHelper = injector.getNSConnectionHelper();
        this.mActToolHelper = new ActToolHelper();
        String str = SystemProperties.get("enable_detecting_gnss_assert", "true");
        this.enable_detecting_gnss_assert = str;
        Log.d("GnssManager", "Set enable_detecting_gnss_assert value:" + str);
    }

    public void addBaseCallbacks(BaseCallbacks baseCallbacks) {
        Preconditions.checkState(!this.mRegistered);
        this.mBaseCallbacks = (BaseCallbacks[]) ArrayUtils.appendElement(BaseCallbacks.class, this.mBaseCallbacks, baseCallbacks);
    }

    public void addStatusCallbacks(StatusCallbacks statusCallbacks) {
        Preconditions.checkState(!this.mRegistered);
        this.mStatusCallbacks = (StatusCallbacks[]) ArrayUtils.appendElement(StatusCallbacks.class, this.mStatusCallbacks, statusCallbacks);
    }

    public void addSvStatusCallbacks(SvStatusCallbacks svStatusCallbacks) {
        Preconditions.checkState(!this.mRegistered);
        this.mSvStatusCallbacks = (SvStatusCallbacks[]) ArrayUtils.appendElement(SvStatusCallbacks.class, this.mSvStatusCallbacks, svStatusCallbacks);
    }

    public void addNmeaCallbacks(NmeaCallbacks nmeaCallbacks) {
        Preconditions.checkState(!this.mRegistered);
        this.mNmeaCallbacks = (NmeaCallbacks[]) ArrayUtils.appendElement(NmeaCallbacks.class, this.mNmeaCallbacks, nmeaCallbacks);
    }

    public void addLocationCallbacks(LocationCallbacks locationCallbacks) {
        Preconditions.checkState(!this.mRegistered);
        this.mLocationCallbacks = (LocationCallbacks[]) ArrayUtils.appendElement(LocationCallbacks.class, this.mLocationCallbacks, locationCallbacks);
    }

    public void addMeasurementCallbacks(MeasurementCallbacks measurementCallbacks) {
        Preconditions.checkState(!this.mRegistered);
        this.mMeasurementCallbacks = (MeasurementCallbacks[]) ArrayUtils.appendElement(MeasurementCallbacks.class, this.mMeasurementCallbacks, measurementCallbacks);
    }

    public void addAntennaInfoCallbacks(AntennaInfoCallbacks antennaInfoCallbacks) {
        Preconditions.checkState(!this.mRegistered);
        this.mAntennaInfoCallbacks = (AntennaInfoCallbacks[]) ArrayUtils.appendElement(AntennaInfoCallbacks.class, this.mAntennaInfoCallbacks, antennaInfoCallbacks);
    }

    public void addNavigationMessageCallbacks(NavigationMessageCallbacks navigationMessageCallbacks) {
        Preconditions.checkState(!this.mRegistered);
        this.mNavigationMessageCallbacks = (NavigationMessageCallbacks[]) ArrayUtils.appendElement(NavigationMessageCallbacks.class, this.mNavigationMessageCallbacks, navigationMessageCallbacks);
    }

    public void setGeofenceCallbacks(GeofenceCallbacks geofenceCallbacks) {
        Preconditions.checkState(!this.mRegistered);
        Preconditions.checkState(this.mGeofenceCallbacks == null);
        Objects.requireNonNull(geofenceCallbacks);
        this.mGeofenceCallbacks = geofenceCallbacks;
    }

    public void setTimeCallbacks(TimeCallbacks timeCallbacks) {
        Preconditions.checkState(!this.mRegistered);
        Preconditions.checkState(this.mTimeCallbacks == null);
        Objects.requireNonNull(timeCallbacks);
        this.mTimeCallbacks = timeCallbacks;
    }

    public void setLocationRequestCallbacks(LocationRequestCallbacks locationRequestCallbacks) {
        Preconditions.checkState(!this.mRegistered);
        Preconditions.checkState(this.mLocationRequestCallbacks == null);
        Objects.requireNonNull(locationRequestCallbacks);
        this.mLocationRequestCallbacks = locationRequestCallbacks;
    }

    public void setPsdsCallbacks(PsdsCallbacks psdsCallbacks) {
        Preconditions.checkState(!this.mRegistered);
        Preconditions.checkState(this.mPsdsCallbacks == null);
        Objects.requireNonNull(psdsCallbacks);
        this.mPsdsCallbacks = psdsCallbacks;
    }

    public void setAGpsCallbacks(AGpsCallbacks aGpsCallbacks) {
        Preconditions.checkState(!this.mRegistered);
        Preconditions.checkState(this.mAGpsCallbacks == null);
        Objects.requireNonNull(aGpsCallbacks);
        this.mAGpsCallbacks = aGpsCallbacks;
    }

    public void setNotificationCallbacks(NotificationCallbacks notificationCallbacks) {
        Preconditions.checkState(!this.mRegistered);
        Preconditions.checkState(this.mNotificationCallbacks == null);
        Objects.requireNonNull(notificationCallbacks);
        this.mNotificationCallbacks = notificationCallbacks;
    }

    public void setLppeHelperCallbacks(LppeHelperCallbacks lppeHelperCallbacks) {
        Preconditions.checkState(!this.mRegistered);
        Preconditions.checkState(this.mLppeHelperCallbacks == null);
        Objects.requireNonNull(lppeHelperCallbacks);
        this.mLppeHelperCallbacks = lppeHelperCallbacks;
    }

    public void register() {
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

    public final void initializeGnss(boolean z) {
        Preconditions.checkState(this.mRegistered);
        this.mTopFlags = 0;
        this.mGnssHal.initOnce(this, z);
        if (this.mGnssHal.init()) {
            this.mGnssHal.cleanup();
            this.mGnssHal.initLocationOff();
            Log.i("GnssManager", "gnss hal initialized");
            return;
        }
        Log.e("GnssManager", "gnss hal initialization failed");
    }

    public GnssConfiguration getConfiguration() {
        return this.mConfiguration;
    }

    public boolean init() {
        Preconditions.checkState(this.mRegistered);
        return this.mGnssHal.init();
    }

    public void cleanup() {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.cleanup();
    }

    public GnssPowerStats getPowerStats() {
        return this.mPowerStats;
    }

    public GnssCapabilities getCapabilities() {
        return this.mCapabilities;
    }

    public int getHardwareYear() {
        return this.mHardwareYear;
    }

    public String getHardwareModelName() {
        return this.mHardwareModelName;
    }

    public boolean isItarSpeedLimitExceeded() {
        return this.mItarSpeedLimitExceeded;
    }

    public boolean start() {
        Preconditions.checkState(this.mRegistered);
        this.mStartRealtimeMs = SystemClock.elapsedRealtime();
        this.mHasFirstFix = false;
        return this.mGnssHal.start();
    }

    public boolean stop() {
        Preconditions.checkState(this.mRegistered);
        return this.mGnssHal.stop();
    }

    public boolean setPositionMode(int i, int i2, int i3, int i4, int i5, boolean z) {
        Preconditions.checkState(this.mRegistered);
        return this.mGnssHal.setPositionMode(i, i2, i3, i4, i5, z);
    }

    public String getInternalState() {
        Preconditions.checkState(this.mRegistered);
        return this.mGnssHal.getInternalState();
    }

    public void deleteAidingData(int i) {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.deleteAidingData(i);
    }

    public int readNmea(byte[] bArr, int i) {
        Preconditions.checkState(this.mRegistered);
        return this.mGnssHal.readNmea(bArr, i);
    }

    public void injectLocation(Location location) {
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
            this.mGnssHal.injectLocation(i, latitude, longitude, altitude, speed, bearing, accuracy, verticalAccuracyMeters, speedAccuracyMetersPerSecond, bearingAccuracyDegrees, time, i2 | 1, location.getElapsedRealtimeNanos(), location.getElapsedRealtimeUncertaintyNanos());
        }
    }

    public void injectBestLocation(Location location) {
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
        this.mGnssHal.injectBestLocation(i, latitude, longitude, altitude, speed, bearing, accuracy, verticalAccuracyMeters, speedAccuracyMetersPerSecond, bearingAccuracyDegrees, time, i2 | 1, location.getElapsedRealtimeNanos(), location.getElapsedRealtimeUncertaintyNanos());
    }

    public void injectTime(long j, long j2, int i) {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.injectTime(j, j2, i);
    }

    public boolean isNavigationMessageCollectionSupported() {
        Preconditions.checkState(this.mRegistered);
        return this.mGnssHal.isNavigationMessageCollectionSupported();
    }

    public boolean startNavigationMessageCollection() {
        Preconditions.checkState(this.mRegistered);
        return this.mGnssHal.startNavigationMessageCollection();
    }

    public boolean stopNavigationMessageCollection() {
        Preconditions.checkState(this.mRegistered);
        return this.mGnssHal.stopNavigationMessageCollection();
    }

    public boolean isAntennaInfoSupported() {
        Preconditions.checkState(this.mRegistered);
        return this.mGnssHal.isAntennaInfoSupported();
    }

    public boolean startAntennaInfoListening() {
        Preconditions.checkState(this.mRegistered);
        return this.mGnssHal.startAntennaInfoListening();
    }

    public boolean stopAntennaInfoListening() {
        Preconditions.checkState(this.mRegistered);
        return this.mGnssHal.stopAntennaInfoListening();
    }

    public boolean isMeasurementSupported() {
        Preconditions.checkState(this.mRegistered);
        return this.mGnssHal.isMeasurementSupported();
    }

    public boolean startMeasurementCollection(boolean z, boolean z2, int i) {
        Preconditions.checkState(this.mRegistered);
        return this.mGnssHal.startMeasurementCollection(z, z2, i);
    }

    public boolean stopMeasurementCollection() {
        Preconditions.checkState(this.mRegistered);
        return this.mGnssHal.stopMeasurementCollection();
    }

    public boolean startSvStatusCollection() {
        Preconditions.checkState(this.mRegistered);
        return this.mGnssHal.startSvStatusCollection();
    }

    public boolean stopSvStatusCollection() {
        Preconditions.checkState(this.mRegistered);
        return this.mGnssHal.stopSvStatusCollection();
    }

    public boolean startNmeaMessageCollection() {
        Preconditions.checkState(this.mRegistered);
        return this.mGnssHal.startNmeaMessageCollection();
    }

    public boolean stopNmeaMessageCollection() {
        Preconditions.checkState(this.mRegistered);
        return this.mGnssHal.stopNmeaMessageCollection();
    }

    public boolean isMeasurementCorrectionsSupported() {
        Preconditions.checkState(this.mRegistered);
        return this.mGnssHal.isMeasurementCorrectionsSupported();
    }

    public boolean injectMeasurementCorrections(GnssMeasurementCorrections gnssMeasurementCorrections) {
        Preconditions.checkState(this.mRegistered);
        return this.mGnssHal.injectMeasurementCorrections(gnssMeasurementCorrections);
    }

    public boolean initBatching() {
        Preconditions.checkState(this.mRegistered);
        return this.mGnssHal.initBatching();
    }

    public void cleanupBatching() {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.cleanupBatching();
    }

    public boolean startBatch(long j, float f, boolean z) {
        Preconditions.checkState(this.mRegistered);
        return this.mGnssHal.startBatch(j, f, z);
    }

    public void flushBatch() {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.flushBatch();
    }

    public void stopBatch() {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.stopBatch();
    }

    public int getBatchSize() {
        Preconditions.checkState(this.mRegistered);
        return this.mGnssHal.getBatchSize();
    }

    public boolean isGeofencingSupported() {
        Preconditions.checkState(this.mRegistered);
        return this.mGnssHal.isGeofencingSupported();
    }

    public boolean addGeofence(int i, double d, double d2, double d3, int i2, int i3, int i4, int i5) {
        Preconditions.checkState(this.mRegistered);
        return this.mGnssHal.addGeofence(i, d, d2, d3, i2, i3, i4, i5);
    }

    public boolean resumeGeofence(int i, int i2) {
        Preconditions.checkState(this.mRegistered);
        return this.mGnssHal.resumeGeofence(i, i2);
    }

    public boolean pauseGeofence(int i) {
        Preconditions.checkState(this.mRegistered);
        return this.mGnssHal.pauseGeofence(i);
    }

    public boolean removeGeofence(int i) {
        Preconditions.checkState(this.mRegistered);
        return this.mGnssHal.removeGeofence(i);
    }

    public boolean isGnssVisibilityControlSupported() {
        Preconditions.checkState(this.mRegistered);
        return this.mGnssHal.isGnssVisibilityControlSupported();
    }

    public void sendNiResponse(int i, int i2) {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.sendNiResponse(i, i2);
    }

    public void requestPowerStats() {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.requestPowerStats();
    }

    public void setAgpsServer(int i, String str, int i2) {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.setAgpsServer(i, str, i2);
    }

    public void setAgpsSetId(int i, String str) {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.setAgpsSetId(i, str);
    }

    public void setAgpsReferenceLocationCellId(int i, int i2, int i3, int i4, long j, int i5, int i6, int i7) {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.setAgpsReferenceLocationCellId(i, i2, i3, i4, j, i5, i6, i7);
    }

    public boolean isPsdsSupported() {
        Preconditions.checkState(this.mRegistered);
        return this.mGnssHal.isPsdsSupported();
    }

    public void injectPsdsData(byte[] bArr, int i, int i2) {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.injectPsdsData(bArr, i, i2);
    }

    public void injectNiSuplMessageData(byte[] bArr, int i, int i2) {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.injectNiSuplMessageData(bArr, i, i2);
    }

    public void reportGnssServiceDied() {
        Log.e("GnssManager", "gnss hal died - restarting shortly...");
        LocationServiceThread.getExecutor().execute(new Runnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                GnssNative.this.restartHal();
            }
        });
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

    public void reportLocation(final boolean z, final Location location) {
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda1
            public final void runOrThrow() {
                GnssNative.this.lambda$reportLocation$1(z, location);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reportLocation$1(boolean z, Location location) {
        int i = 0;
        if (z && !this.mHasFirstFix) {
            this.mHasFirstFix = true;
            int elapsedRealtime = (int) (SystemClock.elapsedRealtime() - this.mStartRealtimeMs);
            int i2 = 0;
            while (true) {
                StatusCallbacks[] statusCallbacksArr = this.mStatusCallbacks;
                if (i2 >= statusCallbacksArr.length) {
                    break;
                }
                statusCallbacksArr[i2].onReportFirstFix(elapsedRealtime);
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
            locationCallbacksArr[i].onReportLocation(z, location);
            i++;
        }
    }

    public void reportStatus(final int i) {
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda33
            public final void runOrThrow() {
                GnssNative.this.lambda$reportStatus$2(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reportStatus$2(int i) {
        int i2 = 0;
        while (true) {
            StatusCallbacks[] statusCallbacksArr = this.mStatusCallbacks;
            if (i2 >= statusCallbacksArr.length) {
                return;
            }
            statusCallbacksArr[i2].onReportStatus(i);
            i2++;
        }
    }

    public void reportSvStatus(final int i, final int[] iArr, final float[] fArr, final float[] fArr2, final float[] fArr3, final float[] fArr4, final float[] fArr5) {
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda25
            public final void runOrThrow() {
                GnssNative.this.lambda$reportSvStatus$3(i, iArr, fArr, fArr2, fArr3, fArr4, fArr5);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reportSvStatus$3(int i, int[] iArr, float[] fArr, float[] fArr2, float[] fArr3, float[] fArr4, float[] fArr5) {
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

    public void reportAGpsStatus(final int i, final int i2, final byte[] bArr) {
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda22
            public final void runOrThrow() {
                GnssNative.this.lambda$reportAGpsStatus$4(i, i2, bArr);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reportAGpsStatus$4(int i, int i2, byte[] bArr) {
        this.mAGpsCallbacks.onReportAGpsStatus(i, i2, bArr);
    }

    public void reportNmea(final long j) {
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda7
            public final void runOrThrow() {
                GnssNative.this.lambda$reportNmea$5(j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reportNmea$5(long j) {
        if (this.mItarSpeedLimitExceeded) {
            return;
        }
        int i = 0;
        while (true) {
            NmeaCallbacks[] nmeaCallbacksArr = this.mNmeaCallbacks;
            if (i >= nmeaCallbacksArr.length) {
                return;
            }
            nmeaCallbacksArr[i].onReportNmea(j);
            i++;
        }
    }

    public void reportMeasurementData(final GnssMeasurementsEvent gnssMeasurementsEvent) {
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda35
            public final void runOrThrow() {
                GnssNative.this.lambda$reportMeasurementData$7(gnssMeasurementsEvent);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reportMeasurementData$7(final GnssMeasurementsEvent gnssMeasurementsEvent) {
        if (this.mItarSpeedLimitExceeded) {
            return;
        }
        for (final MeasurementCallbacks measurementCallbacks : this.mMeasurementCallbacks) {
            this.mHandler.post(new Runnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda17
                @Override // java.lang.Runnable
                public final void run() {
                    GnssNative.MeasurementCallbacks.this.onReportMeasurements(gnssMeasurementsEvent);
                }
            });
        }
    }

    public void reportAntennaInfo(final List list) {
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda13
            public final void runOrThrow() {
                GnssNative.this.lambda$reportAntennaInfo$8(list);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reportAntennaInfo$8(List list) {
        int i = 0;
        while (true) {
            AntennaInfoCallbacks[] antennaInfoCallbacksArr = this.mAntennaInfoCallbacks;
            if (i >= antennaInfoCallbacksArr.length) {
                return;
            }
            antennaInfoCallbacksArr[i].onReportAntennaInfo(list);
            i++;
        }
    }

    public void reportNavigationMessage(final GnssNavigationMessage gnssNavigationMessage) {
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda3
            public final void runOrThrow() {
                GnssNative.this.lambda$reportNavigationMessage$10(gnssNavigationMessage);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reportNavigationMessage$10(final GnssNavigationMessage gnssNavigationMessage) {
        if (this.mItarSpeedLimitExceeded) {
            return;
        }
        for (final NavigationMessageCallbacks navigationMessageCallbacks : this.mNavigationMessageCallbacks) {
            this.mHandler.post(new Runnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    GnssNative.NavigationMessageCallbacks.this.onReportNavigationMessage(gnssNavigationMessage);
                }
            });
        }
    }

    public void setTopHalCapabilities(int i, boolean z) {
        int i2 = i | this.mTopFlags;
        this.mTopFlags = i2;
        GnssCapabilities gnssCapabilities = this.mCapabilities;
        GnssCapabilities withTopHalFlags = gnssCapabilities.withTopHalFlags(i2, z);
        this.mCapabilities = withTopHalFlags;
        onCapabilitiesChanged(gnssCapabilities, withTopHalFlags);
    }

    public void setSubHalMeasurementCorrectionsCapabilities(int i) {
        GnssCapabilities gnssCapabilities = this.mCapabilities;
        GnssCapabilities withSubHalMeasurementCorrectionsFlags = gnssCapabilities.withSubHalMeasurementCorrectionsFlags(i);
        this.mCapabilities = withSubHalMeasurementCorrectionsFlags;
        onCapabilitiesChanged(gnssCapabilities, withSubHalMeasurementCorrectionsFlags);
    }

    public void setSubHalPowerIndicationCapabilities(int i) {
        GnssCapabilities gnssCapabilities = this.mCapabilities;
        GnssCapabilities withSubHalPowerFlags = gnssCapabilities.withSubHalPowerFlags(i);
        this.mCapabilities = withSubHalPowerFlags;
        onCapabilitiesChanged(gnssCapabilities, withSubHalPowerFlags);
    }

    public void setSignalTypeCapabilities(List list) {
        GnssCapabilities gnssCapabilities = this.mCapabilities;
        GnssCapabilities withSignalTypes = gnssCapabilities.withSignalTypes(list);
        this.mCapabilities = withSignalTypes;
        onCapabilitiesChanged(gnssCapabilities, withSignalTypes);
    }

    public final void onCapabilitiesChanged(final GnssCapabilities gnssCapabilities, final GnssCapabilities gnssCapabilities2) {
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda18
            public final void runOrThrow() {
                GnssNative.this.lambda$onCapabilitiesChanged$11(gnssCapabilities2, gnssCapabilities);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCapabilitiesChanged$11(GnssCapabilities gnssCapabilities, GnssCapabilities gnssCapabilities2) {
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
            baseCallbacksArr[i].onCapabilitiesChanged(gnssCapabilities2, gnssCapabilities);
            i++;
        }
    }

    public void reportGnssPowerStats(GnssPowerStats gnssPowerStats) {
        this.mPowerStats = gnssPowerStats;
    }

    public void setGnssYearOfHardware(int i) {
        this.mHardwareYear = i;
    }

    public final void setGnssHardwareModelName(String str) {
        this.mHardwareModelName = str;
    }

    public void reportLocationBatch(final Location[] locationArr) {
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda24
            public final void runOrThrow() {
                GnssNative.this.lambda$reportLocationBatch$12(locationArr);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reportLocationBatch$12(Location[] locationArr) {
        int i = 0;
        while (true) {
            LocationCallbacks[] locationCallbacksArr = this.mLocationCallbacks;
            if (i >= locationCallbacksArr.length) {
                return;
            }
            locationCallbacksArr[i].onReportLocations(locationArr);
            i++;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$psdsDownloadRequest$13(int i) {
        this.mPsdsCallbacks.onRequestPsdsDownload(i);
    }

    public void psdsDownloadRequest(final int i) {
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda34
            public final void runOrThrow() {
                GnssNative.this.lambda$psdsDownloadRequest$13(i);
            }
        });
    }

    public void reportGeofenceTransition(final int i, final Location location, final int i2, final long j) {
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda0
            public final void runOrThrow() {
                GnssNative.this.lambda$reportGeofenceTransition$14(i, location, i2, j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reportGeofenceTransition$14(int i, Location location, int i2, long j) {
        this.mGeofenceCallbacks.onReportGeofenceTransition(i, location, i2, j);
    }

    public void reportGeofenceStatus(final int i, final Location location) {
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda19
            public final void runOrThrow() {
                GnssNative.this.lambda$reportGeofenceStatus$15(i, location);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reportGeofenceStatus$15(int i, Location location) {
        this.mGeofenceCallbacks.onReportGeofenceStatus(i, location);
    }

    public void reportGeofenceAddStatus(final int i, final int i2) {
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda14
            public final void runOrThrow() {
                GnssNative.this.lambda$reportGeofenceAddStatus$16(i, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reportGeofenceAddStatus$16(int i, int i2) {
        this.mGeofenceCallbacks.onReportGeofenceAddStatus(i, i2);
    }

    public void reportGeofenceRemoveStatus(final int i, final int i2) {
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda15
            public final void runOrThrow() {
                GnssNative.this.lambda$reportGeofenceRemoveStatus$17(i, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reportGeofenceRemoveStatus$17(int i, int i2) {
        this.mGeofenceCallbacks.onReportGeofenceRemoveStatus(i, i2);
    }

    public void reportGeofencePauseStatus(final int i, final int i2) {
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda16
            public final void runOrThrow() {
                GnssNative.this.lambda$reportGeofencePauseStatus$18(i, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reportGeofencePauseStatus$18(int i, int i2) {
        this.mGeofenceCallbacks.onReportGeofencePauseStatus(i, i2);
    }

    public void reportGeofenceResumeStatus(final int i, final int i2) {
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda5
            public final void runOrThrow() {
                GnssNative.this.lambda$reportGeofenceResumeStatus$19(i, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reportGeofenceResumeStatus$19(int i, int i2) {
        this.mGeofenceCallbacks.onReportGeofenceResumeStatus(i, i2);
    }

    public void reportNiNotification(final int i, final int i2, final int i3, final int i4, final int i5, final String str, final String str2, final int i6, final int i7) {
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda36
            public final void runOrThrow() {
                GnssNative.this.lambda$reportNiNotification$20(i, i2, i3, i4, i5, str, str2, i6, i7);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reportNiNotification$20(int i, int i2, int i3, int i4, int i5, String str, String str2, int i6, int i7) {
        this.mNotificationCallbacks.onReportNiNotification(i, i2, i3, i4, i5, str, str2, i6, i7);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestSetID$21(int i) {
        this.mAGpsCallbacks.onRequestSetID(i);
    }

    public void requestSetID(final int i) {
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda11
            public final void runOrThrow() {
                GnssNative.this.lambda$requestSetID$21(i);
            }
        });
    }

    public void requestLocation(final boolean z, final boolean z2) {
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda28
            public final void runOrThrow() {
                GnssNative.this.lambda$requestLocation$22(z, z2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestLocation$22(boolean z, boolean z2) {
        this.mLocationRequestCallbacks.onRequestLocation(z, z2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestUtcTime$23() {
        this.mTimeCallbacks.onRequestUtcTime();
    }

    public void requestUtcTime() {
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda4
            public final void runOrThrow() {
                GnssNative.this.lambda$requestUtcTime$23();
            }
        });
    }

    public void requestRefLocation() {
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda12
            public final void runOrThrow() {
                GnssNative.this.lambda$requestRefLocation$24();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestRefLocation$24() {
        this.mLocationRequestCallbacks.onRequestRefLocation();
    }

    public void requestRefLocationSec() {
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda10
            public final void runOrThrow() {
                GnssNative.this.lambda$requestRefLocationSec$25();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestRefLocationSec$25() {
        this.mLocationRequestCallbacks.onRequestRefLocationSec();
    }

    public void reportNfwNotification(final String str, final byte b, final String str2, final byte b2, final String str3, final byte b3, final boolean z, final boolean z2) {
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda9
            public final void runOrThrow() {
                GnssNative.this.lambda$reportNfwNotification$26(str, b, str2, b2, str3, b3, z, z2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reportNfwNotification$26(String str, byte b, String str2, byte b2, String str3, byte b3, boolean z, boolean z2) {
        this.mNotificationCallbacks.onReportNfwNotification(str, b, str2, b2, str3, b3, z, z2);
    }

    public boolean isInEmergencySession() {
        if (!mGnssVendorConfig.isIzatServiceEnabled()) {
            CarrierConfig carrierConfig = mCarrierConfig;
            if (carrierConfig.isUSAMarket() || carrierConfig.isCanadaMarket()) {
                return ((Boolean) Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda20
                    public final Object getOrThrow() {
                        Boolean lambda$isInEmergencySession$27;
                        lambda$isInEmergencySession$27 = GnssNative.this.lambda$isInEmergencySession$27();
                        return lambda$isInEmergencySession$27;
                    }
                })).booleanValue();
            }
        }
        return ((Boolean) Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda21
            public final Object getOrThrow() {
                Boolean lambda$isInEmergencySession$28;
                lambda$isInEmergencySession$28 = GnssNative.this.lambda$isInEmergencySession$28();
                return lambda$isInEmergencySession$28;
            }
        })).booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Boolean lambda$isInEmergencySession$27() {
        return Boolean.valueOf(this.mNotificationCallbacks.isInEmergencySessionSEC());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Boolean lambda$isInEmergencySession$28() {
        return Boolean.valueOf(this.mEmergencyHelper.isInEmergency(TimeUnit.SECONDS.toMillis(this.mConfiguration.getEsExtensionSec())));
    }

    public final void reportExtraAssertMessage(String str) {
        GnssVendorConfig gnssVendorConfig = mGnssVendorConfig;
        if (!gnssVendorConfig.isIzatServiceEnabled()) {
            Log.d("GnssManager", "reportExtraMessage, message = " + str);
            if (isGnssAssertMessage(str)) {
                if ("true".equals(this.enable_detecting_gnss_assert)) {
                    long currentTimeMillis = System.currentTimeMillis();
                    if (currentTimeMillis - this.prevAssertTS <= 3000) {
                        this.continuousAssertCount++;
                    } else {
                        this.continuousAssertCount = 0;
                    }
                    if (this.continuousAssertCount > 4) {
                        this.mActToolHelper.notifyEvent(this.mContext, str);
                        this.continuousAssertCount = 0;
                    }
                    this.prevAssertTS = currentTimeMillis;
                }
                if (gnssVendorConfig.isBroadcomGnss()) {
                    Log.d("GnssManager", "GNSS report assert, reset it");
                    this.mGnssHal.init();
                }
            }
        }
        this.mNSConnectionHelper.onGnssEventUpdated(str);
    }

    public boolean isGnssAssertMessage(String str) {
        return !str.startsWith("$");
    }

    public void requestLppeCommonIesCapability() {
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda29
            public final void runOrThrow() {
                GnssNative.this.lambda$requestLppeCommonIesCapability$29();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestLppeCommonIesCapability$29() {
        this.mLppeHelperCallbacks.onRequestLppeCommonIesCapability();
    }

    public void requestFlpLocation(final int i) {
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda23
            public final void runOrThrow() {
                GnssNative.this.lambda$requestFlpLocation$30(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestFlpLocation$30(int i) {
        this.mLppeHelperCallbacks.onRequestFlpLocation(i);
    }

    public void requestUbpCapability() {
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda2
            public final void runOrThrow() {
                GnssNative.this.lambda$requestUbpCapability$31();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestUbpCapability$31() {
        this.mLppeHelperCallbacks.onRequestUbpCapability();
    }

    public void requestUbpInfo(final int i) {
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda32
            public final void runOrThrow() {
                GnssNative.this.lambda$requestUbpInfo$32(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestUbpInfo$32(int i) {
        this.mLppeHelperCallbacks.onRequestUbpInfo(i);
    }

    public void requestWlanCapability() {
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda26
            public final void runOrThrow() {
                GnssNative.this.lambda$requestWlanCapability$33();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestWlanCapability$33() {
        this.mLppeHelperCallbacks.onRequestWifiCapability();
    }

    public void requestWlanScanInfo(final int i) {
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda27
            public final void runOrThrow() {
                GnssNative.this.lambda$requestWlanScanInfo$34(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestWlanScanInfo$34(int i) {
        this.mLppeHelperCallbacks.onRequestWifiScan(i);
    }

    public void requestCivicAddress(final double d, final double d2, final double d3) {
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda31
            public final void runOrThrow() {
                GnssNative.this.lambda$requestCivicAddress$35(d, d2, d3);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestCivicAddress$35(double d, double d2, double d3) {
        this.mLppeHelperCallbacks.onRequestCivicAddress(d, d2, d3);
    }

    public boolean initLocationOff() {
        Preconditions.checkState(this.mRegistered);
        return this.mGnssHal.initLocationOff();
    }

    public void gnssConfigurationUpdateSec(String str) {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.gnssConfigurationUpdateExtension(str);
    }

    public void sendSuplNiMessage(byte[] bArr, int i) {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.sendSuplNiMessage(bArr, i);
    }

    public void setAgpsReferenceLocationCellId(int i, int i2, int i3, int i4, long j, int i5, int i6) {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.setAgpsReferenceLocationCellId(i, i2, i3, i4, j, i5, i6);
    }

    public float getSehGnssHalVersion() {
        Preconditions.checkState(this.mRegistered);
        return this.mGnssHal.getSehGnssHalVersion();
    }

    public boolean isSehGnssAidlHal() {
        Preconditions.checkState(this.mRegistered);
        return this.mGnssHal.isSehGnssAidlHal();
    }

    public void injectFlpLocation(int i, double d, double d2, double d3, float f, float f2, float f3, float f4, float f5, float f6, long j) {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.injectFlpLocation(i, d, d2, d3, f, f2, f3, f4, f5, f6, j);
    }

    public void injectFlpError(int i) {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.injectFlpError(i);
    }

    public void injectLppeComIeCapability(int i, boolean z, boolean z2, boolean z3, int i2, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8) {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.injectLppeComIeCapability(i, z, z2, z3, i2, z4, z5, z6, z7, z8);
    }

    public void injectUbpCapability(boolean z, boolean z2, boolean z3, boolean z4) {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.injectUbpCapability(z, z2, z3, z4);
    }

    public void injectUbpInfo(int i, int i2) {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.injectUbpInfo(i, i2);
    }

    public void injectUbpError(int i) {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.injectUbpError(i);
    }

    public void injectWlanCapability(int i, int i2, long j, int i3, int i4, int i5) {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.injectWlanCapability(i, i2, j, i3, i4, i5);
    }

    public void injectWlanScanInfo(long[] jArr, int[] iArr, int[] iArr2, int i) {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.injectWlanScanInfo(jArr, iArr, iArr2, i);
    }

    public void injectWlanError(int i) {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.injectWlanError(i);
    }

    public void injectCivicAddress(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13) {
        Preconditions.checkState(this.mRegistered);
        this.mGnssHal.injectCivicAddress(str, str2, str3, str4, str5, str6, str7, str8, str9, str10, str11, str12, str13);
    }

    public class GnssHal {
        public void classInitOnce() {
            GnssNative.native_class_init_once();
        }

        public boolean isSupported() {
            return GnssNative.native_is_supported();
        }

        public void initOnce(GnssNative gnssNative, boolean z) {
            gnssNative.native_init_once(z);
        }

        public boolean init() {
            return GnssNative.native_init();
        }

        public void cleanup() {
            GnssNative.native_cleanup();
        }

        public boolean start() {
            return GnssNative.native_start();
        }

        public boolean stop() {
            return GnssNative.native_stop();
        }

        public boolean setPositionMode(int i, int i2, int i3, int i4, int i5, boolean z) {
            return GnssNative.native_set_position_mode(i, i2, i3, i4, i5, z);
        }

        public String getInternalState() {
            return GnssNative.native_get_internal_state();
        }

        public void deleteAidingData(int i) {
            GnssNative.native_delete_aiding_data(i);
        }

        public int readNmea(byte[] bArr, int i) {
            return GnssNative.native_read_nmea(bArr, i);
        }

        public void injectLocation(int i, double d, double d2, double d3, float f, float f2, float f3, float f4, float f5, float f6, long j, int i2, long j2, double d4) {
            GnssNative.native_inject_location(i, d, d2, d3, f, f2, f3, f4, f5, f6, j, i2, j2, d4);
        }

        public void injectBestLocation(int i, double d, double d2, double d3, float f, float f2, float f3, float f4, float f5, float f6, long j, int i2, long j2, double d4) {
            GnssNative.native_inject_best_location(i, d, d2, d3, f, f2, f3, f4, f5, f6, j, i2, j2, d4);
        }

        public void injectTime(long j, long j2, int i) {
            GnssNative.native_inject_time(j, j2, i);
        }

        public boolean isNavigationMessageCollectionSupported() {
            return GnssNative.native_is_navigation_message_supported();
        }

        public boolean startNavigationMessageCollection() {
            return GnssNative.native_start_navigation_message_collection();
        }

        public boolean stopNavigationMessageCollection() {
            return GnssNative.native_stop_navigation_message_collection();
        }

        public boolean isAntennaInfoSupported() {
            return GnssNative.native_is_antenna_info_supported();
        }

        public boolean startAntennaInfoListening() {
            return GnssNative.native_start_antenna_info_listening();
        }

        public boolean stopAntennaInfoListening() {
            return GnssNative.native_stop_antenna_info_listening();
        }

        public boolean isMeasurementSupported() {
            return GnssNative.native_is_measurement_supported();
        }

        public boolean startMeasurementCollection(boolean z, boolean z2, int i) {
            return GnssNative.native_start_measurement_collection(z, z2, i);
        }

        public boolean stopMeasurementCollection() {
            return GnssNative.native_stop_measurement_collection();
        }

        public boolean isMeasurementCorrectionsSupported() {
            return GnssNative.native_is_measurement_corrections_supported();
        }

        public boolean injectMeasurementCorrections(GnssMeasurementCorrections gnssMeasurementCorrections) {
            return GnssNative.native_inject_measurement_corrections(gnssMeasurementCorrections);
        }

        public boolean startSvStatusCollection() {
            return GnssNative.native_start_sv_status_collection();
        }

        public boolean stopSvStatusCollection() {
            return GnssNative.native_stop_sv_status_collection();
        }

        public boolean startNmeaMessageCollection() {
            return GnssNative.native_start_nmea_message_collection();
        }

        public boolean stopNmeaMessageCollection() {
            return GnssNative.native_stop_nmea_message_collection();
        }

        public int getBatchSize() {
            return GnssNative.native_get_batch_size();
        }

        public boolean initBatching() {
            return GnssNative.native_init_batching();
        }

        public void cleanupBatching() {
            GnssNative.native_cleanup_batching();
        }

        public boolean startBatch(long j, float f, boolean z) {
            return GnssNative.native_start_batch(j, f, z);
        }

        public void flushBatch() {
            GnssNative.native_flush_batch();
        }

        public void stopBatch() {
            GnssNative.native_stop_batch();
        }

        public boolean isGeofencingSupported() {
            return GnssNative.native_is_geofence_supported();
        }

        public boolean addGeofence(int i, double d, double d2, double d3, int i2, int i3, int i4, int i5) {
            return GnssNative.native_add_geofence(i, d, d2, d3, i2, i3, i4, i5);
        }

        public boolean resumeGeofence(int i, int i2) {
            return GnssNative.native_resume_geofence(i, i2);
        }

        public boolean pauseGeofence(int i) {
            return GnssNative.native_pause_geofence(i);
        }

        public boolean removeGeofence(int i) {
            return GnssNative.native_remove_geofence(i);
        }

        public boolean isGnssVisibilityControlSupported() {
            return GnssNative.native_is_gnss_visibility_control_supported();
        }

        public void sendNiResponse(int i, int i2) {
            GnssNative.native_send_ni_response(i, i2);
        }

        public void requestPowerStats() {
            GnssNative.native_request_power_stats();
        }

        public void setAgpsServer(int i, String str, int i2) {
            GnssNative.native_set_agps_server(i, str, i2);
        }

        public void setAgpsSetId(int i, String str) {
            GnssNative.native_agps_set_id(i, str);
        }

        public void setAgpsReferenceLocationCellId(int i, int i2, int i3, int i4, long j, int i5, int i6, int i7) {
            GnssNative.native_agps_set_ref_location_cellid(i, i2, i3, i4, j, i5, i6, i7);
        }

        public boolean isPsdsSupported() {
            return GnssNative.native_supports_psds();
        }

        public void injectPsdsData(byte[] bArr, int i, int i2) {
            GnssNative.native_inject_psds_data(bArr, i, i2);
        }

        public boolean initLocationOff() {
            return GnssNative.native_init_extension_location_off();
        }

        public void gnssConfigurationUpdateExtension(String str) {
            GnssNative.native_configuration_update_extension(str);
        }

        public void sendSuplNiMessage(byte[] bArr, int i) {
            GnssNative.native_send_supl_ni_message(bArr, i);
        }

        public void injectNiSuplMessageData(byte[] bArr, int i, int i2) {
            GnssNative.native_inject_ni_supl_message_data(bArr, i, i2);
        }

        public void setAgpsReferenceLocationCellId(int i, int i2, int i3, int i4, long j, int i5, int i6) {
            GnssNative.native_agps_set_ref_location_cellid(i, i2, i3, i4, j, i5, i6);
        }

        public float getSehGnssHalVersion() {
            return GnssNative.native_get_seh_gnss_hal_version();
        }

        public boolean isSehGnssAidlHal() {
            return GnssNative.native_is_seh_gnss_aidl_hal();
        }

        public void injectFlpLocation(int i, double d, double d2, double d3, float f, float f2, float f3, float f4, float f5, float f6, long j) {
            GnssNative.native_inject_flp_location(i, d, d2, d3, f, f2, f3, f4, f5, f6, j);
        }

        public void injectFlpError(int i) {
            GnssNative.native_inject_flp_error(i);
        }

        public void injectLppeComIeCapability(int i, boolean z, boolean z2, boolean z3, int i2, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8) {
            GnssNative.native_inject_lppe_com_ie_capability(i, z, z2, z3, i2, z4, z5, z6, z7, z8);
        }

        public void injectUbpCapability(boolean z, boolean z2, boolean z3, boolean z4) {
            GnssNative.native_inject_ubp_capability(z, z2, z3, z4);
        }

        public void injectUbpInfo(int i, int i2) {
            GnssNative.native_inject_ubp_info(i, i2);
        }

        public void injectUbpError(int i) {
            GnssNative.native_inject_ubp_error(i);
        }

        public void injectWlanCapability(int i, int i2, long j, int i3, int i4, int i5) {
            GnssNative.native_inject_wlan_capability(i, i2, j, i3, i4, i5);
        }

        public void injectWlanScanInfo(long[] jArr, int[] iArr, int[] iArr2, int i) {
            GnssNative.native_inject_wlan_scan_info(jArr, iArr, iArr2, i);
        }

        public void injectWlanError(int i) {
            GnssNative.native_inject_wlan_error(i);
        }

        public boolean injectCivicAddress(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13) {
            return GnssNative.native_inject_civic_address(str, str2, str3, str4, str5, str6, str7, str8, str9, str10, str11, str12, str13);
        }
    }
}
