package com.android.server.location.gnss;

import android.R;
import android.app.AlarmManager;
import android.app.AppOpsManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.hardware.usb.V1_1.PortStatus_1_1$$ExternalSyntheticOutline0;
import android.location.GnssCapabilities;
import android.location.GnssStatus;
import android.location.Location;
import android.location.LocationConstants;
import android.location.LocationManager;
import android.location.provider.ProviderProperties;
import android.location.provider.ProviderRequest;
import android.location.util.identity.CallerIdentity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.WorkSource;
import android.os.connectivity.GpsBatteryStats;
import android.provider.Settings;
import android.telephony.CellIdentity;
import android.telephony.CellIdentityGsm;
import android.telephony.CellIdentityLte;
import android.telephony.CellIdentityNr;
import android.telephony.CellIdentityWcdma;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.util.TimeUtils;

import com.android.internal.app.IBatteryStats;
import com.android.internal.location.GpsNetInitiatedHandler;
import com.android.internal.notification.SystemNotificationChannels;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.NetworkScoreService$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.RCPManagerService$$ExternalSyntheticOutline0;
import com.android.server.SystemServerInitThreadPool$$ExternalSyntheticLambda0;
import com.android.server.VpnManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.backup.BackupManagerConstants;
import com.android.server.input.KeyboardMetricsCollector;
import com.android.server.location.LocationManagerService;
import com.android.server.location.LocationServiceThread;
import com.android.server.location.gnss.hal.GnssNative;
import com.android.server.location.gnss.sec.GnssHalStatus;
import com.android.server.location.gnss.sec.GnssVendorConfig;
import com.android.server.location.gnss.sec.VelocitySmoothingFilter;
import com.android.server.location.injector.Injector;
import com.android.server.location.nsflp.NSConnectionHelper;
import com.android.server.location.nsflp.NSConnectionHelper$$ExternalSyntheticLambda0;
import com.android.server.location.nsflp.NSLocationProviderHelper;
import com.android.server.location.provider.AbstractLocationProvider;
import com.android.server.location.provider.AbstractLocationProvider$$ExternalSyntheticLambda0;
import com.android.server.location.provider.LocationProviderManager$Registration$$ExternalSyntheticLambda0;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public abstract class GnssLocationProvider extends AbstractLocationProvider
        implements NetworkTimeHelper.InjectTimeCallback,
                GnssSatelliteBlocklistHelper.GnssSatelliteBlocklistCallback,
                GnssNative.BaseCallbacks,
                GnssNative.LocationCallbacks,
                GnssNative.SvStatusCallbacks,
                GnssNative.AGpsCallbacks,
                GnssNative.PsdsCallbacks,
                GnssNative.NotificationCallbacks,
                GnssNative.LocationRequestCallbacks,
                GnssNative.TimeCallbacks {
    public boolean isExtraCommandFromAllowedAppRequest;
    public final AlarmManager mAlarmManager;
    public final AppOpsManager mAppOps;
    public boolean mAutomotiveSuspend;
    public GnssLocationProvider$$ExternalSyntheticLambda10 mBatchingAlarm;
    public boolean mBatchingEnabled;
    public boolean mBatchingStarted;
    public final IBatteryStats mBatteryStats;
    public final WorkSource mClientSource;
    public final Context mContext;
    public String mDeleteAidingHistory;
    public final Set mDownloadInProgressPsdsTypes;
    public final PowerManager.WakeLock mDownloadPsdsWakeLock;
    public int mFixInterval;
    public long mFixRequestTime;
    public final ArrayList mFlushListeners;
    public final GnssConfiguration mGnssConfiguration;
    public final GnssMetrics mGnssMetrics;
    public final GnssNative mGnssNative;
    public final GnssSatelliteBlocklistHelper mGnssSatelliteBlocklistHelper;
    public final GnssVendorConfig mGnssVendorConfig;
    public GnssVisibilityControl mGnssVisibilityControl;
    public boolean mGpsEnabled;
    public final Handler mHandler;
    public boolean mInitialized;
    public final AnonymousClass2 mIntentReceiver;
    public long mLastFixTime;
    public GnssPositionMode mLastPositionMode;
    public final LocationExtras mLocationExtras;
    public final Object mLock;
    public final GpsNetInitiatedHandler mNIHandler;
    public final NSConnectionHelper mNSConnectionHelper;
    public final NSLocationProviderHelper mNSLocationProviderHelper;
    public final GnssNetworkConnectivityHandler mNetworkConnectivityHandler;
    public final TimeDetectorNetworkTimeHelper mNetworkTimeHelper;
    public final Set mPendingDownloadPsdsTypes;
    public int mPositionMode;
    public ProviderRequest mProviderRequest;
    public final ExponentialBackOff mPsdsBackOff;
    public final Object mPsdsPeriodicDownloadToken;
    public final ArrayList mSecGpsDump;
    public final ArrayList mSecGpsSimHistoryDump;
    public boolean mShutdown;
    public int mSimSlotId;
    public boolean mStarted;
    public long mStartedChangedElapsedRealtime;
    public final SubscriptionManager mSubscriptionManager;
    public boolean mSuplEsEnabled;
    public boolean mSupportsPsds;
    public GnssManagerService$$ExternalSyntheticLambda0 mSvCallback;
    public int mTimeToFirstFix;
    public final AlarmManager.OnAlarmListener mTimeoutListener;
    public final VelocitySmoothingFilter mVSFilter;
    public final PowerManager.WakeLock mWakeLock;
    public final AlarmManager.OnAlarmListener mWakeupListener;
    public static final boolean VERBOSE = Log.isLoggable("GnssLocationProvider", 2);
    public static final ProviderProperties PROPERTIES =
            new ProviderProperties.Builder()
                    .setHasSatelliteRequirement(true)
                    .setHasAltitudeSupport(true)
                    .setHasSpeedSupport(true)
                    .setHasBearingSupport(true)
                    .setPowerUsage(3)
                    .setAccuracy(1)
                    .build();

    /* renamed from: com.android.server.location.gnss.GnssLocationProvider$1, reason: invalid class name */
    public final class AnonymousClass1 implements GpsNetInitiatedHandler.EmergencyCallCallback {
        public final /* synthetic */ GnssLocationProvider this$0;

        public AnonymousClass1(GnssLocationProviderSec gnssLocationProviderSec) {
            this.this$0 = gnssLocationProviderSec;
        }

        public final void onEmergencyCallEnd() {
            String property =
                    this.this$0.mGnssConfiguration.mProperties.getProperty(
                            "ENABLE_ACTIVE_SIM_EMERGENCY_SUPL");
            if (TextUtils.isEmpty(property) ? false : Boolean.parseBoolean(property)) {
                this.this$0.mHandler.postDelayed(
                        new GnssLocationProvider$$ExternalSyntheticLambda12(2, this),
                        TimeUnit.SECONDS.toMillis(r0.mGnssConfiguration.mEsExtensionSec));
            }
        }

        public final void onEmergencyCallStart(int i) {
            String property =
                    this.this$0.mGnssConfiguration.mProperties.getProperty(
                            "ENABLE_ACTIVE_SIM_EMERGENCY_SUPL");
            if (TextUtils.isEmpty(property) ? false : Boolean.parseBoolean(property)) {
                this.this$0.mHandler.post(
                        new GnssLocationProvider$$ExternalSyntheticLambda3(i, 5, this));
            }
        }
    }

    /* renamed from: com.android.server.location.gnss.GnssLocationProvider$2, reason: invalid class name */
    public final class AnonymousClass2 extends BroadcastReceiver {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ GnssLocationProvider this$0;

        public /* synthetic */ AnonymousClass2(GnssLocationProvider gnssLocationProvider, int i) {
            this.$r8$classId = i;
            this.this$0 = gnssLocationProvider;
        }

        /* JADX WARN: Removed duplicated region for block: B:32:0x0109  */
        /* JADX WARN: Removed duplicated region for block: B:37:0x011a  */
        @Override // android.content.BroadcastReceiver
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onReceive(android.content.Context r7, android.content.Intent r8) {
            /*
                Method dump skipped, instructions count: 322
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.location.gnss.GnssLocationProvider.AnonymousClass2.onReceive(android.content.Context,"
                        + " android.content.Intent):void");
        }
    }

    public final class LocationExtras {
        public final Bundle mBundle = new Bundle();
        public int mMaxCn0;
        public int mMeanCn0;
        public int mSvCount;

        public final void set(int i, int i2, int i3) {
            synchronized (this) {
                this.mSvCount = i;
                this.mMeanCn0 = i2;
                this.mMaxCn0 = i3;
            }
            Bundle bundle = this.mBundle;
            if (bundle != null) {
                synchronized (this) {
                    bundle.putInt("satellites", this.mSvCount);
                    bundle.putInt("meanCn0", this.mMeanCn0);
                    bundle.putInt("maxCn0", this.mMaxCn0);
                }
            }
        }
    }

    public GnssLocationProvider(Context context, GnssNative gnssNative, GnssMetrics gnssMetrics) {
        this.mLock = new Object();
        this.mPsdsBackOff = new ExponentialBackOff();
        this.mFixInterval = 1000;
        this.mFixRequestTime = 0L;
        this.mTimeToFirstFix = 0;
        this.mClientSource = new WorkSource();
        this.mPsdsPeriodicDownloadToken = new Object();
        this.mPendingDownloadPsdsTypes = new HashSet();
        this.mDownloadInProgressPsdsTypes = new HashSet();
        this.mGnssVendorConfig = GnssVendorConfig.getInstance();
        this.mSecGpsDump = new ArrayList();
        this.mSecGpsSimHistoryDump = new ArrayList();
        this.mDeleteAidingHistory = KeyboardMetricsCollector.DEFAULT_LANGUAGE_TAG;
        this.mSimSlotId = 0;
        this.isExtraCommandFromAllowedAppRequest = false;
        this.mSuplEsEnabled = false;
        this.mLocationExtras = new LocationExtras();
        final int i = 1;
        this.mWakeupListener =
                new AlarmManager.OnAlarmListener(
                        this) { // from class:
                                // com.android.server.location.gnss.GnssLocationProvider$$ExternalSyntheticLambda7
                    public final /* synthetic */ GnssLocationProvider f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.app.AlarmManager.OnAlarmListener
                    public final void onAlarm() {
                        int i2 = i;
                        GnssLocationProvider gnssLocationProvider = this.f$0;
                        switch (i2) {
                            case 0:
                                gnssLocationProvider.startNavigating();
                                break;
                            case 1:
                                gnssLocationProvider.startNavigating();
                                break;
                            case 2:
                                gnssLocationProvider.hibernate();
                                break;
                            default:
                                gnssLocationProvider.hibernate();
                                break;
                        }
                    }
                };
        final int i2 = 2;
        this.mTimeoutListener =
                new AlarmManager.OnAlarmListener(
                        this) { // from class:
                                // com.android.server.location.gnss.GnssLocationProvider$$ExternalSyntheticLambda7
                    public final /* synthetic */ GnssLocationProvider f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.app.AlarmManager.OnAlarmListener
                    public final void onAlarm() {
                        int i22 = i2;
                        GnssLocationProvider gnssLocationProvider = this.f$0;
                        switch (i22) {
                            case 0:
                                gnssLocationProvider.startNavigating();
                                break;
                            case 1:
                                gnssLocationProvider.startNavigating();
                                break;
                            case 2:
                                gnssLocationProvider.hibernate();
                                break;
                            default:
                                gnssLocationProvider.hibernate();
                                break;
                        }
                    }
                };
        this.mFlushListeners = new ArrayList(0);
        this.mIntentReceiver = new AnonymousClass2(this, 1);
        this.mContext = context;
        this.mHandler = LocationServiceThread.getHandler();
        this.mGnssNative = gnssNative;
        this.mGnssMetrics = gnssMetrics;
        this.mDownloadPsdsWakeLock = null;
        this.mWakeLock = null;
        this.mAlarmManager = (AlarmManager) context.getSystemService("alarm");
        this.mGnssConfiguration = gnssNative.mConfiguration;
        this.mNIHandler = null;
        this.mNetworkConnectivityHandler = null;
        this.mNetworkTimeHelper = null;
        this.mGnssSatelliteBlocklistHelper = null;
        this.mAppOps = (AppOpsManager) context.getSystemService(AppOpsManager.class);
        this.mBatteryStats = null;
        this.mNSConnectionHelper = null;
        this.mNSLocationProviderHelper = null;
    }

    public GnssLocationProvider(
            Context context, Injector injector, GnssNative gnssNative, GnssMetrics gnssMetrics) {
        super(
                LocationServiceThread.getExecutor(),
                CallerIdentity.fromContext(context),
                PROPERTIES,
                Collections.emptySet());
        this.mLock = new Object();
        this.mPsdsBackOff = new ExponentialBackOff();
        this.mFixInterval = 1000;
        this.mFixRequestTime = 0L;
        this.mTimeToFirstFix = 0;
        this.mClientSource = new WorkSource();
        this.mPsdsPeriodicDownloadToken = new Object();
        HashSet hashSet = new HashSet();
        this.mPendingDownloadPsdsTypes = hashSet;
        this.mDownloadInProgressPsdsTypes = new HashSet();
        this.mGnssVendorConfig = GnssVendorConfig.getInstance();
        this.mSecGpsDump = new ArrayList();
        this.mSecGpsSimHistoryDump = new ArrayList();
        this.mDeleteAidingHistory = KeyboardMetricsCollector.DEFAULT_LANGUAGE_TAG;
        this.mSimSlotId = 0;
        this.isExtraCommandFromAllowedAppRequest = false;
        this.mSuplEsEnabled = false;
        this.mLocationExtras = new LocationExtras();
        final GnssLocationProviderSec gnssLocationProviderSec = (GnssLocationProviderSec) this;
        final int i = 0;
        this.mWakeupListener =
                new AlarmManager.OnAlarmListener(
                        gnssLocationProviderSec) { // from class:
                                                   // com.android.server.location.gnss.GnssLocationProvider$$ExternalSyntheticLambda7
                    public final /* synthetic */ GnssLocationProvider f$0;

                    {
                        this.f$0 = gnssLocationProviderSec;
                    }

                    @Override // android.app.AlarmManager.OnAlarmListener
                    public final void onAlarm() {
                        int i22 = i;
                        GnssLocationProvider gnssLocationProvider = this.f$0;
                        switch (i22) {
                            case 0:
                                gnssLocationProvider.startNavigating();
                                break;
                            case 1:
                                gnssLocationProvider.startNavigating();
                                break;
                            case 2:
                                gnssLocationProvider.hibernate();
                                break;
                            default:
                                gnssLocationProvider.hibernate();
                                break;
                        }
                    }
                };
        final int i2 = 3;
        this.mTimeoutListener =
                new AlarmManager.OnAlarmListener(
                        gnssLocationProviderSec) { // from class:
                                                   // com.android.server.location.gnss.GnssLocationProvider$$ExternalSyntheticLambda7
                    public final /* synthetic */ GnssLocationProvider f$0;

                    {
                        this.f$0 = gnssLocationProviderSec;
                    }

                    @Override // android.app.AlarmManager.OnAlarmListener
                    public final void onAlarm() {
                        int i22 = i2;
                        GnssLocationProvider gnssLocationProvider = this.f$0;
                        switch (i22) {
                            case 0:
                                gnssLocationProvider.startNavigating();
                                break;
                            case 1:
                                gnssLocationProvider.startNavigating();
                                break;
                            case 2:
                                gnssLocationProvider.hibernate();
                                break;
                            default:
                                gnssLocationProvider.hibernate();
                                break;
                        }
                    }
                };
        this.mFlushListeners = new ArrayList(0);
        this.mIntentReceiver = new AnonymousClass2(this, 1);
        Log.i("GnssLocationProvider", "GnssLocationProvider()");
        this.mContext = context;
        this.mGnssNative = gnssNative;
        this.mGnssMetrics = gnssMetrics;
        LocationManagerService.SystemInjector systemInjector =
                (LocationManagerService.SystemInjector) injector;
        this.mNSConnectionHelper = systemInjector.mNSConnectionHelper;
        this.mNSLocationProviderHelper = systemInjector.mNSLocationProviderHelper;
        PowerManager powerManager = (PowerManager) context.getSystemService(PowerManager.class);
        Objects.requireNonNull(powerManager);
        PowerManager.WakeLock newWakeLock =
                powerManager.newWakeLock(1, "*location*:GnssLocationProvider");
        this.mWakeLock = newWakeLock;
        newWakeLock.setReferenceCounted(true);
        this.mSubscriptionManager =
                (SubscriptionManager) context.getSystemService("telephony_subscription_service");
        VelocitySmoothingFilter velocitySmoothingFilter = new VelocitySmoothingFilter();
        velocitySmoothingFilter.isDriving = false;
        velocitySmoothingFilter.mDriveCount = 0;
        velocitySmoothingFilter.mPrevLocation = null;
        this.mVSFilter = velocitySmoothingFilter;
        PowerManager.WakeLock newWakeLock2 = powerManager.newWakeLock(1, "*location*:PsdsDownload");
        this.mDownloadPsdsWakeLock = newWakeLock2;
        newWakeLock2.setReferenceCounted(true);
        this.mAlarmManager = (AlarmManager) context.getSystemService("alarm");
        this.mAppOps = (AppOpsManager) context.getSystemService(AppOpsManager.class);
        this.mBatteryStats =
                IBatteryStats.Stub.asInterface(ServiceManager.getService("batterystats"));
        Handler handler = LocationServiceThread.getHandler();
        this.mHandler = handler;
        this.mGnssConfiguration = gnssNative.mConfiguration;
        GpsNetInitiatedHandler gpsNetInitiatedHandler =
                new GpsNetInitiatedHandler(
                        context, new AnonymousClass1(gnssLocationProviderSec), this.mSuplEsEnabled);
        this.mNIHandler = gpsNetInitiatedHandler;
        hashSet.add(1);
        this.mNetworkConnectivityHandler =
                new GnssNetworkConnectivityHandler(
                        context,
                        new GnssLocationProvider$$ExternalSyntheticLambda9(gnssLocationProviderSec),
                        handler.getLooper(),
                        gpsNetInitiatedHandler);
        this.mNetworkTimeHelper =
                new TimeDetectorNetworkTimeHelper(
                        new TimeDetectorNetworkTimeHelper.EnvironmentImpl(handler.getLooper()),
                        gnssLocationProviderSec);
        this.mGnssSatelliteBlocklistHelper =
                new GnssSatelliteBlocklistHelper(
                        context, handler.getLooper(), gnssLocationProviderSec);
        setState(new AbstractLocationProvider$$ExternalSyntheticLambda0(true));
        gnssNative.addBaseCallbacks(this);
        gnssNative.addLocationCallbacks(this);
        gnssNative.addSvStatusCallbacks(this);
        gnssNative.setAGpsCallbacks(this);
        gnssNative.setPsdsCallbacks(this);
        gnssNative.setNotificationCallbacks(this);
        gnssNative.setLocationRequestCallbacks(this);
        gnssNative.setTimeCallbacks(this);
    }

    public static String getTimestamp() {
        return DateFormat.format("[yyyy-MM-dd HH:mm:ss]", Calendar.getInstance().getTime())
                .toString();
    }

    public abstract void changeNlpAccuracyInForce(Location location);

    public final void demandUtcTimeInjection() {
        Log.d("GnssLocationProvider", "demandUtcTimeInjection");
        TimeDetectorNetworkTimeHelper timeDetectorNetworkTimeHelper = this.mNetworkTimeHelper;
        Objects.requireNonNull(timeDetectorNetworkTimeHelper);
        postWithWakeLockHeld(
                new GnssLocationProvider$$ExternalSyntheticLambda12(
                        1, timeDetectorNetworkTimeHelper));
    }

    @Override // com.android.server.location.provider.AbstractLocationProvider
    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        boolean z;
        GpsBatteryStats gpsBatteryStats;
        String str;
        int i = 0;
        while (i < strArr.length
                && (str = strArr[i]) != null
                && str.length() > 0
                && str.charAt(0) == '-') {
            i++;
            if ("-a".equals(str)) {
                z = true;
                break;
            }
        }
        z = false;
        printWriter.print("mStarted=" + this.mStarted + "   (changed ");
        TimeUtils.formatDuration(
                SystemClock.elapsedRealtime() - this.mStartedChangedElapsedRealtime, printWriter);
        printWriter.println(" ago)");
        StringBuilder m =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                new StringBuilder("mBatchingEnabled="),
                                this.mBatchingEnabled,
                                printWriter,
                                "mBatchingStarted="),
                        this.mBatchingStarted,
                        printWriter,
                        "mBatchSize=");
        m.append(getBatchSize());
        printWriter.println(m.toString());
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                new StringBuilder("mFixInterval="), this.mFixInterval, printWriter);
        GnssMetrics gnssMetrics = this.mGnssMetrics;
        gnssMetrics.getClass();
        StringBuilder sb = new StringBuilder();
        sb.append("GNSS_KPI_START\n  KPI logging start time: ");
        TimeUtils.formatDuration(gnssMetrics.mLogStartInElapsedRealtimeMs, sb);
        sb.append("\n");
        sb.append("  KPI logging end time: ");
        TimeUtils.formatDuration(SystemClock.elapsedRealtime(), sb);
        sb.append("\n");
        sb.append("  Number of location reports: ");
        GnssMetrics.Statistics statistics = gnssMetrics.mLocationFailureStatistics;
        sb.append(statistics.getCount());
        sb.append("\n");
        if (statistics.getCount() > 0) {
            sb.append("  Percentage location failure: ");
            sb.append(statistics.getMean() * 100.0d);
            sb.append("\n");
        }
        sb.append("  Number of TTFF reports: ");
        GnssMetrics.Statistics statistics2 = gnssMetrics.mTimeToFirstFixSecStatistics;
        sb.append(statistics2.getCount());
        sb.append("\n");
        if (statistics2.getCount() > 0) {
            sb.append("  TTFF mean (sec): ");
            sb.append(statistics2.getMean());
            sb.append("\n");
            sb.append("  TTFF standard deviation (sec): ");
            sb.append(statistics2.getStandardDeviation());
            sb.append("\n");
        }
        sb.append("  Number of position accuracy reports: ");
        GnssMetrics.Statistics statistics3 = gnssMetrics.mPositionAccuracyMeterStatistics;
        sb.append(statistics3.getCount());
        sb.append("\n");
        if (statistics3.getCount() > 0) {
            sb.append("  Position accuracy mean (m): ");
            sb.append(statistics3.getMean());
            sb.append("\n");
            sb.append("  Position accuracy standard deviation (m): ");
            sb.append(statistics3.getStandardDeviation());
            sb.append("\n");
        }
        sb.append("  Number of CN0 reports: ");
        GnssMetrics.Statistics statistics4 = gnssMetrics.mTopFourAverageCn0Statistics;
        sb.append(statistics4.getCount());
        sb.append("\n");
        if (statistics4.getCount() > 0) {
            sb.append("  Top 4 Avg CN0 mean (dB-Hz): ");
            sb.append(statistics4.getMean());
            sb.append("\n");
            sb.append("  Top 4 Avg CN0 standard deviation (dB-Hz): ");
            sb.append(statistics4.getStandardDeviation());
            sb.append("\n");
        }
        sb.append("  Total number of sv status messages processed: ");
        sb.append(gnssMetrics.mNumSvStatus);
        sb.append("\n");
        sb.append("  Total number of L5 sv status messages processed: ");
        sb.append(gnssMetrics.mNumL5SvStatus);
        sb.append("\n");
        sb.append("  Total number of sv status messages processed, where sv is used in fix: ");
        sb.append(gnssMetrics.mNumSvStatusUsedInFix);
        sb.append("\n");
        sb.append("  Total number of L5 sv status messages processed, where sv is used in fix: ");
        sb.append(gnssMetrics.mNumL5SvStatusUsedInFix);
        sb.append("\n");
        sb.append("  Number of L5 CN0 reports: ");
        GnssMetrics.Statistics statistics5 = gnssMetrics.mTopFourAverageCn0StatisticsL5;
        sb.append(statistics5.getCount());
        sb.append("\n");
        if (statistics5.getCount() > 0) {
            sb.append("  L5 Top 4 Avg CN0 mean (dB-Hz): ");
            sb.append(statistics5.getMean());
            sb.append("\n");
            sb.append("  L5 Top 4 Avg CN0 standard deviation (dB-Hz): ");
            sb.append(statistics5.getStandardDeviation());
            sb.append("\n");
        }
        sb.append("  Used-in-fix constellation types: ");
        int i2 = 0;
        while (true) {
            boolean[] zArr = gnssMetrics.mConstellationTypes;
            if (i2 >= zArr.length) {
                break;
            }
            if (zArr[i2]) {
                sb.append(GnssStatus.constellationTypeToString(i2));
                sb.append(" ");
            }
            i2++;
        }
        RCPManagerService$$ExternalSyntheticOutline0.m$1(sb, "\n", "GNSS_KPI_END", "\n");
        GnssMetrics.GnssPowerMetrics gnssPowerMetrics = gnssMetrics.mGnssPowerMetrics;
        gnssPowerMetrics.getClass();
        try {
            gpsBatteryStats = gnssPowerMetrics.mBatteryStats.getGpsBatteryStats();
        } catch (RemoteException e) {
            Log.w("GnssMetrics", e);
            gpsBatteryStats = null;
        }
        if (gpsBatteryStats != null) {
            sb.append("Power Metrics");
            sb.append("\n");
            sb.append("  Time on battery (min): ");
            sb.append(gpsBatteryStats.getLoggingDurationMs() / 60000.0d);
            sb.append("\n");
            long[] timeInGpsSignalQualityLevel = gpsBatteryStats.getTimeInGpsSignalQualityLevel();
            if (timeInGpsSignalQualityLevel != null && timeInGpsSignalQualityLevel.length == 2) {
                sb.append("  Amount of time (while on battery) Top 4 Avg CN0 > 20.0 dB-Hz (min): ");
                sb.append(timeInGpsSignalQualityLevel[1] / 60000.0d);
                sb.append("\n");
                sb.append(
                        "  Amount of time (while on battery) Top 4 Avg CN0 <= 20.0 dB-Hz (min): ");
                sb.append(timeInGpsSignalQualityLevel[0] / 60000.0d);
                sb.append("\n");
            }
            sb.append("  Energy consumed while on battery (mAh): ");
            sb.append(gpsBatteryStats.getEnergyConsumedMaMs() / 3600000.0d);
            sb.append("\n");
        }
        sb.append("Hardware Version: ");
        sb.append(SystemProperties.get("ro.boot.revision", ""));
        sb.append("\n");
        printWriter.print(sb.toString());
        if (z) {
            TimeDetectorNetworkTimeHelper timeDetectorNetworkTimeHelper = this.mNetworkTimeHelper;
            timeDetectorNetworkTimeHelper.getClass();
            printWriter.println("TimeDetectorNetworkTimeHelper:");
            IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
            indentingPrintWriter.increaseIndent();
            synchronized (timeDetectorNetworkTimeHelper) {
                indentingPrintWriter.println(
                        "mPeriodicTimeInjectionEnabled="
                                + timeDetectorNetworkTimeHelper.mPeriodicTimeInjectionEnabled);
            }
            indentingPrintWriter.println("Debug log:");
            timeDetectorNetworkTimeHelper.mDumpLog.dump(indentingPrintWriter);
            StringBuilder m2 =
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            new StringBuilder("mSupportsPsds="),
                            this.mSupportsPsds,
                            printWriter,
                            "PsdsServerConfigured=");
            GnssConfiguration gnssConfiguration = this.mGnssConfiguration;
            m2.append(
                    (gnssConfiguration.mProperties.getProperty("LONGTERM_PSDS_SERVER_1") == null
                                    && gnssConfiguration.mProperties.getProperty(
                                                    "LONGTERM_PSDS_SERVER_2")
                                            == null
                                    && gnssConfiguration.mProperties.getProperty(
                                                    "LONGTERM_PSDS_SERVER_3")
                                            == null)
                            ? false
                            : true);
            printWriter.println(m2.toString());
            printWriter.println("native internal state: ");
            printWriter.println("  " + this.mGnssNative.getInternalState());
        }
        printWriter.println("\nSEC Dump for updateRequirements");
        printWriter.println(this.mSecGpsDump + "\n");
        printWriter.println("SEC Dump for Deleting aiding data");
        printWriter.println(this.mDeleteAidingHistory + "\n");
        printWriter.println("SEC Dump for sim state history");
        printWriter.println(this.mSecGpsSimHistoryDump + "\n");
        dumpSec(printWriter);
    }

    public abstract void dumpSec(PrintWriter printWriter);

    public final int getBatchSize() {
        if ("true".equals(SystemProperties.get("ro.location.hwflp"))) {
            return this.mGnssNative.getBatchSize();
        }
        Log.d("SLocationProxy", "GNSS batching is disabled.");
        return 0;
    }

    public abstract int getPositionModeSec(int i);

    public abstract void gnssConfigurationUpdateSec(String str);

    public final void handleDownloadPsdsData(int i) {
        try {
            String str = SystemProperties.get("persist.sys.xtra_time");
            long currentTimeMillis = System.currentTimeMillis();
            if ("user".equals(Build.TYPE)
                    && str != null
                    && !this.isExtraCommandFromAllowedAppRequest) {
                if (currentTimeMillis - Long.parseLong(str) < 21600000) {
                    Log.d("GnssLocationProvider", "Ignore xtra download request");
                    return;
                }
                Log.d("GnssLocationProvider", "xtra download request accepted from FW");
            }
        } catch (NumberFormatException unused) {
            Log.d("GnssLocationProvider", "NumberFormatException while using parseLong.");
        }
        if (!this.mSupportsPsds) {
            Log.d(
                    "GnssLocationProvider",
                    "handleDownloadPsdsData() called when PSDS not supported");
            return;
        }
        if (!this.mNetworkConnectivityHandler.isDataNetworkConnected()) {
            synchronized (this.mLock) {
                this.mPendingDownloadPsdsTypes.add(Integer.valueOf(i));
            }
            return;
        }
        synchronized (this.mLock) {
            try {
                if (this.mDownloadInProgressPsdsTypes.contains(Integer.valueOf(i))) {
                    Log.d(
                            "GnssLocationProvider",
                            "PSDS type " + i + " download in progress. Ignore the request.");
                    return;
                }
                this.mDownloadPsdsWakeLock.acquire(60000L);
                this.mDownloadInProgressPsdsTypes.add(Integer.valueOf(i));
                Log.i("GnssLocationProvider", "WakeLock acquired by handleDownloadPsdsData()");
                Executors.newSingleThreadExecutor()
                        .execute(new GnssLocationProvider$$ExternalSyntheticLambda3(i, 1, this));
                this.isExtraCommandFromAllowedAppRequest = false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public abstract void handleEnableSec();

    public abstract void handleReportSvStatusSec(GnssStatus gnssStatus);

    public final void hibernate() {
        stopNavigating();
        this.mAlarmManager.set(
                2,
                SystemClock.elapsedRealtime() + this.mFixInterval,
                "GnssLocationProvider",
                this.mWakeupListener,
                this.mHandler);
    }

    public abstract boolean isDeviceBasedHybridSupported();

    public abstract boolean isEquipmentTestModeEnabled();

    public abstract boolean isExtraCommandAllowed(int i);

    public final boolean isGpsEnabled() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mGpsEnabled;
        }
        return z;
    }

    public abstract boolean isKOREmergency(boolean z);

    @Override // com.android.server.location.gnss.hal.GnssNative.BaseCallbacks
    public final void onCapabilitiesChanged(GnssCapabilities gnssCapabilities) {
        this.mHandler.post(new GnssLocationProvider$$ExternalSyntheticLambda0(this, 0));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.server.location.provider.AbstractLocationProvider
    public void onExtraCommand(int i, String str, Bundle bundle, int i2) {
        if (!isExtraCommandAllowed(i)) {
            Log.w("GnssLocationProvider", "sendExtraCommand from uid " + i + " ignored.");
            return;
        }
        boolean equals = "delete_aiding_data".equals(str);
        GnssNative gnssNative = this.mGnssNative;
        if (!equals) {
            if ("force_time_injection".equals(str)) {
                demandUtcTimeInjection();
                return;
            }
            if ("force_psds_injection".equals(str)) {
                if (this.mSupportsPsds) {
                    postWithWakeLockHeld(
                            new GnssLocationProvider$$ExternalSyntheticLambda0(this, 2));
                    return;
                }
                return;
            } else if ("request_power_stats".equals(str)) {
                gnssNative.requestPowerStats(
                        new SystemServerInitThreadPool$$ExternalSyntheticLambda0(),
                        new GnssLocationProvider$$ExternalSyntheticLambda20());
                return;
            } else {
                NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                        "sendExtraCommand: unknown command ", str, "GnssLocationProvider");
                return;
            }
        }
        GnssVendorConfig gnssVendorConfig = this.mGnssVendorConfig;
        int i3 = GnssNative.GNSS_AIDING_TYPE_ALL;
        if (bundle == null) {
            gnssVendorConfig.getClass();
            if (!GnssVendorConfig.isIzatServiceEnabled()) {
                i3 = 65533;
            }
        } else {
            boolean z = bundle.getBoolean("ephemeris");
            boolean z2 = z;
            if (bundle.getBoolean("almanac")) {
                z2 = (z ? 1 : 0) | 2;
            }
            boolean z3 = z2;
            if (bundle.getBoolean("position")) {
                z3 = (z2 ? 1 : 0) | 4;
            }
            boolean z4 = z3;
            if (bundle.getBoolean("time")) {
                z4 = (z3 ? 1 : 0) | '\b';
            }
            boolean z5 = z4;
            if (bundle.getBoolean("iono")) {
                z5 = (z4 ? 1 : 0) | 16;
            }
            boolean z6 = z5;
            if (bundle.getBoolean("utc")) {
                z6 = (z5 ? 1 : 0) | ' ';
            }
            boolean z7 = z6;
            if (bundle.getBoolean("health")) {
                z7 = (z6 ? 1 : 0) | '@';
            }
            boolean z8 = z7;
            if (bundle.getBoolean("svdir")) {
                z8 = (z7 ? 1 : 0) | 128;
            }
            boolean z9 = z8;
            if (bundle.getBoolean("svsteer")) {
                z9 = (z8 ? 1 : 0) | 256;
            }
            boolean z10 = z9;
            if (bundle.getBoolean("sadata")) {
                z10 = (z9 ? 1 : 0) | 512;
            }
            boolean z11 = z10;
            if (bundle.getBoolean("rti")) {
                z11 = (z10 ? 1 : 0) | 1024;
            }
            boolean z12 = z11;
            if (bundle.getBoolean("celldb-info")) {
                z12 = (z11 ? 1 : 0) | 32768;
            }
            if (!bundle.getBoolean("all")) {
                i3 = z12;
            }
        }
        if (i3 != 0) {
            gnssNative.deleteAidingData(i3);
        }
        gnssVendorConfig.getClass();
        if (GnssVendorConfig.isIzatServiceEnabled()) {
            postWithWakeLockHeld(new GnssLocationProvider$$ExternalSyntheticLambda0(this, 3));
        }
        this.mDeleteAidingHistory = getTimestamp() + ": Delete Aiding data";
    }

    @Override // com.android.server.location.provider.AbstractLocationProvider
    public final void onFlush(
            LocationProviderManager$Registration$$ExternalSyntheticLambda0
                    locationProviderManager$Registration$$ExternalSyntheticLambda0) {
        boolean add;
        synchronized (this.mLock) {
            try {
                add =
                        this.mBatchingEnabled
                                ? this.mFlushListeners.add(
                                        locationProviderManager$Registration$$ExternalSyntheticLambda0)
                                : false;
            } catch (Throwable th) {
                throw th;
            }
        }
        if (add) {
            this.mGnssNative.flushBatch();
        } else {
            locationProviderManager$Registration$$ExternalSyntheticLambda0.run();
        }
    }

    @Override // com.android.server.location.gnss.hal.GnssNative.BaseCallbacks
    public final void onHalRestarted() {
        reloadGpsProperties();
        if (isGpsEnabled()) {
            setGpsEnabled(false);
            updateEnabled();
            restartLocationRequest();
        }
        synchronized (this.mLock) {
            try {
                if (this.mInitialized) {
                    GnssNetworkConnectivityHandler gnssNetworkConnectivityHandler =
                            this.mNetworkConnectivityHandler;
                    gnssNetworkConnectivityHandler.mConnMgr.unregisterNetworkCallback(
                            gnssNetworkConnectivityHandler.mNetworkConnectivityCallback);
                    gnssNetworkConnectivityHandler.mNetworkConnectivityCallback = null;
                    this.mNetworkConnectivityHandler.registerNetworkCallbacks();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onReportAGpsStatus(final int i, int i2, final byte[] bArr) {
        StringBuilder sb = new StringBuilder("AGPS_DATA_CONNECTION: ");
        final GnssNetworkConnectivityHandler gnssNetworkConnectivityHandler =
                this.mNetworkConnectivityHandler;
        gnssNetworkConnectivityHandler.getClass();
        VpnManagerService$$ExternalSyntheticOutline0.m(
                sb,
                GnssNetworkConnectivityHandler.agpsDataConnStatusAsString(i2),
                "GnssNetworkConnectivityHandler");
        Handler handler = gnssNetworkConnectivityHandler.mHandler;
        if (i2 == 1) {
            final Runnable runnable = new Runnable() { // from class:
                        // com.android.server.location.gnss.GnssNetworkConnectivityHandler$$ExternalSyntheticLambda0
                        /* JADX WARN: Code restructure failed: missing block: B:27:0x00b6, code lost:

                           if (com.android.server.location.gnss.sec.GnssVendorConfig.isIzatServiceEnabled() == false) goto L48;
                        */
                        /* JADX WARN: Removed duplicated region for block: B:51:0x017f  */
                        /* JADX WARN: Removed duplicated region for block: B:58:0x019b  */
                        /* JADX WARN: Removed duplicated region for block: B:63:? A[RETURN, SYNTHETIC] */
                        /* JADX WARN: Removed duplicated region for block: B:69:0x014d  */
                        @Override // java.lang.Runnable
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final void run() {
                            /*
                                Method dump skipped, instructions count: 441
                                To view this dump change 'Code comments level' option to 'DEBUG'
                            */
                            throw new UnsupportedOperationException(
                                    "Method not decompiled:"
                                        + " com.android.server.location.gnss.GnssNetworkConnectivityHandler$$ExternalSyntheticLambda0.run():void");
                        }
                    };
            gnssNetworkConnectivityHandler.mWakeLock.acquire(60000L);
            if (handler.post(
                    new Runnable() { // from class:
                                     // com.android.server.location.gnss.GnssNetworkConnectivityHandler$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            GnssNetworkConnectivityHandler gnssNetworkConnectivityHandler2 =
                                    GnssNetworkConnectivityHandler.this;
                            Runnable runnable2 = runnable;
                            boolean z = GnssNetworkConnectivityHandler.VERBOSE;
                            gnssNetworkConnectivityHandler2.getClass();
                            try {
                                runnable2.run();
                            } finally {
                                gnssNetworkConnectivityHandler2.mWakeLock.release();
                            }
                        }
                    })) {
                return;
            }
            gnssNetworkConnectivityHandler.mWakeLock.release();
            return;
        }
        if (i2 != 2) {
            if (i2 == 3 || i2 == 4 || i2 == 5) {
                return;
            }
            NetworkScoreService$$ExternalSyntheticOutline0.m(
                    i2, "Received unknown AGPS status: ", "GnssNetworkConnectivityHandler");
            return;
        }
        final GnssNetworkConnectivityHandler$$ExternalSyntheticLambda1
                gnssNetworkConnectivityHandler$$ExternalSyntheticLambda1 =
                        new GnssNetworkConnectivityHandler$$ExternalSyntheticLambda1(
                                gnssNetworkConnectivityHandler, 0);
        gnssNetworkConnectivityHandler.mWakeLock.acquire(60000L);
        if (handler.post(
                new Runnable() { // from class:
                                 // com.android.server.location.gnss.GnssNetworkConnectivityHandler$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        GnssNetworkConnectivityHandler gnssNetworkConnectivityHandler2 =
                                GnssNetworkConnectivityHandler.this;
                        Runnable runnable2 =
                                gnssNetworkConnectivityHandler$$ExternalSyntheticLambda1;
                        boolean z = GnssNetworkConnectivityHandler.VERBOSE;
                        gnssNetworkConnectivityHandler2.getClass();
                        try {
                            runnable2.run();
                        } finally {
                            gnssNetworkConnectivityHandler2.mWakeLock.release();
                        }
                    }
                })) {
            return;
        }
        gnssNetworkConnectivityHandler.mWakeLock.release();
    }

    public final void onReportNfwNotification(
            final String str,
            final byte b,
            final String str2,
            final byte b2,
            final String str3,
            final byte b3,
            final boolean z,
            final boolean z2) {
        final GnssVisibilityControl gnssVisibilityControl = this.mGnssVisibilityControl;
        if (gnssVisibilityControl == null) {
            Log.e(
                    "GnssLocationProvider",
                    "reportNfwNotification: mGnssVisibilityControl uninitialized.");
            return;
        }
        gnssVisibilityControl.getClass();
        gnssVisibilityControl.runOnHandler(
                new Runnable() { // from class:
                    // com.android.server.location.gnss.GnssVisibilityControl$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        boolean z3;
                        boolean z4;
                        GnssVisibilityControl gnssVisibilityControl2 = GnssVisibilityControl.this;
                        String str4 = str;
                        byte b4 = b;
                        String str5 = str2;
                        byte b5 = b2;
                        String str6 = str3;
                        byte b6 = b3;
                        boolean z5 = z;
                        boolean z6 = z2;
                        gnssVisibilityControl2.getClass();
                        GnssVisibilityControl.NfwNotification nfwNotification =
                                new GnssVisibilityControl.NfwNotification(
                                        str4, b4, str5, b5, str6, b6, z5, z6);
                        Log.d(
                                "GnssVisibilityControl",
                                "Non-framework location access notification: " + nfwNotification);
                        if (z5 && !(!TextUtils.isEmpty(str4))) {
                            if (b6 != 0) {
                                z3 = false;
                            } else {
                                Log.e(
                                        "GnssVisibilityControl",
                                        "Emergency non-framework location request incorrectly"
                                            + " rejected. Notification: "
                                                + nfwNotification);
                                z3 = true;
                            }
                            if (gnssVisibilityControl2.mNiHandler.getInEmergency(128000L)) {
                                z4 = z3;
                            } else {
                                Log.w(
                                        "GnssVisibilityControl",
                                        "Emergency state mismatch. Device currently not in user"
                                            + " initiated emergency session. Notification: "
                                                + nfwNotification);
                                z4 = true;
                            }
                            FrameworkStatsLog.write(131, str4, b4, str5, b5, str6, b6, z5, z6, z4);
                            if (b6 == 2) {
                                NotificationManager notificationManager =
                                        (NotificationManager)
                                                gnssVisibilityControl2.mContext.getSystemService(
                                                        "notification");
                                if (notificationManager == null) {
                                    Log.w(
                                            "GnssVisibilityControl",
                                            "Could not notify user of emergency location request."
                                                + " Notification: "
                                                    + nfwNotification);
                                    return;
                                }
                                Context context = gnssVisibilityControl2.mContext;
                                String string =
                                        context.getString(R.string.mime_type_compressed_ext);
                                String string2 = context.getString(R.string.mediasize_iso_c7);
                                notificationManager.notifyAsUser(
                                        null,
                                        0,
                                        new Notification.Builder(
                                                        context,
                                                        SystemNotificationChannels.NETWORK_STATUS)
                                                .setSmallIcon(17304491)
                                                .setWhen(0L)
                                                .setOngoing(false)
                                                .setAutoCancel(true)
                                                .setColor(
                                                        context.getColor(
                                                                R.color
                                                                        .system_notification_accent_color))
                                                .setDefaults(0)
                                                .setTicker(string + " (" + string2 + ")")
                                                .setContentTitle(string)
                                                .setContentText(string2)
                                                .build(),
                                        UserHandle.ALL);
                                return;
                            }
                            return;
                        }
                        GnssVisibilityControl.ProxyAppState proxyAppState =
                                (GnssVisibilityControl.ProxyAppState)
                                        gnssVisibilityControl2.mProxyAppsState.get(str4);
                        boolean z7 = b6 != 0;
                        boolean z8 = b6 != 0;
                        boolean z9 =
                                (proxyAppState == null || !gnssVisibilityControl2.mIsGpsEnabled)
                                        ? z8
                                        : proxyAppState.mHasLocationPermission != z8;
                        FrameworkStatsLog.write(131, str4, b4, str5, b5, str6, b6, z5, z6, z9);
                        if (!(!TextUtils.isEmpty(str4))) {
                            if (z7) {
                                Log.e(
                                        "GnssVisibilityControl",
                                        "ProxyAppPackageName field is not set. AppOps service not"
                                            + " notified for notification: "
                                                + nfwNotification);
                                return;
                            } else {
                                Log.d(
                                        "GnssVisibilityControl",
                                        "Non-framework location request rejected."
                                            + " ProxyAppPackageName field is not set in the"
                                            + " notification: "
                                                + nfwNotification
                                                + ". Number of configured proxy apps: "
                                                + gnssVisibilityControl2.mProxyAppsState.size());
                                return;
                            }
                        }
                        if (proxyAppState == null) {
                            Log.w(
                                    "GnssVisibilityControl",
                                    "Could not find proxy app "
                                            + str4
                                            + " in the value specified for config parameter:"
                                            + " NFW_PROXY_APPS. AppOps service not notified for"
                                            + " notification: "
                                            + nfwNotification);
                            return;
                        }
                        ApplicationInfo proxyAppInfo = gnssVisibilityControl2.getProxyAppInfo(str4);
                        if (proxyAppInfo == null) {
                            Log.e(
                                    "GnssVisibilityControl",
                                    "Proxy app "
                                            + str4
                                            + " is not found. AppOps service not notified for"
                                            + " notification: "
                                            + nfwNotification);
                            return;
                        }
                        if (b6 == 2) {
                            int i = proxyAppInfo.uid;
                            boolean z10 = proxyAppState.mIsLocationIconOn;
                            Handler handler = gnssVisibilityControl2.mHandler;
                            if (z10) {
                                handler.removeCallbacksAndMessages(proxyAppState);
                            } else if (gnssVisibilityControl2.updateLocationIcon(i, str4, true)) {
                                proxyAppState.mIsLocationIconOn = true;
                            } else {
                                Log.w(
                                        "GnssVisibilityControl",
                                        "Failed to show Location icon for notification: "
                                                + nfwNotification);
                                gnssVisibilityControl2.mAppOps.noteOpNoThrow(
                                        1, proxyAppInfo.uid, str4);
                            }
                            StringBuilder sb = new StringBuilder("Location icon on. ");
                            AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                                    i,
                                    z10 ? "Extending" : "Setting",
                                    " icon display timer. Uid: ",
                                    ", proxyAppPkgName: ",
                                    sb);
                            sb.append(str4);
                            Log.d("GnssVisibilityControl", sb.toString());
                            if (!handler.postDelayed(
                                    new GnssVisibilityControl$$ExternalSyntheticLambda1(
                                            gnssVisibilityControl2, str4, 2),
                                    proxyAppState,
                                    5000L)) {
                                gnssVisibilityControl2.clearLocationIcon(proxyAppState, i, str4);
                                Log.w(
                                        "GnssVisibilityControl",
                                        "Failed to show location icon for the full duration for"
                                            + " notification: "
                                                + nfwNotification);
                            }
                            gnssVisibilityControl2.mAppOps.noteOpNoThrow(1, proxyAppInfo.uid, str4);
                        }
                        if (z9) {
                            StringBuilder m =
                                    DumpUtils$$ExternalSyntheticOutline0.m(
                                            "Permission mismatch. Proxy app ",
                                            str4,
                                            " location permission is set to ");
                            m.append(proxyAppState.mHasLocationPermission);
                            m.append(" and GNSS HAL enabled is set to ");
                            m.append(gnssVisibilityControl2.mIsGpsEnabled);
                            m.append(" but GNSS non-framework location access response type is ");
                            m.append(
                                    b6 != 0
                                            ? b6 != 1
                                                    ? b6 != 2
                                                            ? "<Unknown>"
                                                            : "ACCEPTED_LOCATION_PROVIDED"
                                                    : "ACCEPTED_NO_LOCATION_PROVIDED"
                                            : "REJECTED");
                            m.append(" for notification: ");
                            m.append(nfwNotification);
                            Log.w("GnssVisibilityControl", m.toString());
                        }
                    }
                });
        Bundle bundle = new Bundle();
        bundle.putString("proxyAppPackageName", str);
        bundle.putByte("protocolStack", b);
        bundle.putString("otherProtocolStackName", str2);
        bundle.putByte("requestor", b2);
        bundle.putString("requestorId", str3);
        bundle.putByte("responseType", b3);
        bundle.putBoolean("inEmergencyMode", z);
        bundle.putBoolean("isCachedLocation", z2);
        this.mNSConnectionHelper.onStateUpdated(
                LocationConstants.STATE_TYPE.REPORT_NFW_NOTIFICATION, bundle);
    }

    @Override // com.android.server.location.gnss.hal.GnssNative.SvStatusCallbacks
    public final void onReportSvStatus(GnssStatus gnssStatus) {
        Handler handler;
        postWithWakeLockHeld(
                new GnssLocationProvider$$ExternalSyntheticLambda14(this, gnssStatus, 0));
        NSConnectionHelper nSConnectionHelper = this.mNSConnectionHelper;
        if (!nSConnectionHelper.mHasNsflpFeature
                || (handler = nSConnectionHelper.mHandler) == null
                || gnssStatus == null) {
            return;
        }
        handler.post(
                new NSConnectionHelper$$ExternalSyntheticLambda0(
                        nSConnectionHelper, gnssStatus, 0));
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x00b8, code lost:

       if (r8 != (r10.getType() != 6 ? Long.MAX_VALUE : 2147483647L)) goto L47;
    */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00ad  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00b3  */
    @Override // com.android.server.location.gnss.hal.GnssNative.LocationRequestCallbacks
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onRequestRefLocation() {
        /*
            Method dump skipped, instructions count: 320
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.location.gnss.GnssLocationProvider.onRequestRefLocation():void");
    }

    @Override // com.android.server.location.gnss.hal.GnssNative.AGpsCallbacks
    public void onRequestSetID(int i) {
        String str;
        int i2;
        TelephonyManager telephonyManager =
                (TelephonyManager) this.mContext.getSystemService("phone");
        int defaultDataSubscriptionId = SubscriptionManager.getDefaultDataSubscriptionId();
        String property =
                this.mGnssConfiguration.mProperties.getProperty("ENABLE_ACTIVE_SIM_EMERGENCY_SUPL");
        int i3 = 0;
        if ((TextUtils.isEmpty(property) ? false : Boolean.parseBoolean(property))
                && this.mNIHandler.getInEmergency()
                && (i2 = this.mNetworkConnectivityHandler.mActiveSubId) >= 0) {
            defaultDataSubscriptionId = i2;
        }
        if (SubscriptionManager.isValidSubscriptionId(defaultDataSubscriptionId)) {
            telephonyManager = telephonyManager.createForSubscriptionId(defaultDataSubscriptionId);
        }
        if ((i & 1) == 1) {
            str = telephonyManager.getSubscriberId();
            if (str != null) {
                i3 = 1;
            }
        } else if ((i & 2) == 2) {
            str = telephonyManager.getLine1Number();
            if (str != null) {
                i3 = 2;
            }
        } else {
            str = null;
        }
        if (str == null) {
            str = "";
        }
        this.mGnssNative.setAgpsSetId(i3, str);
    }

    @Override // com.android.server.location.provider.AbstractLocationProvider
    public final void onSetRequest(ProviderRequest providerRequest) {
        this.mProviderRequest = providerRequest;
        updateEnabled();
        updateRequirements();
    }

    public final void postWithWakeLockHeld(Runnable runnable) {
        this.mWakeLock.acquire(30000L);
        if (this.mHandler.post(
                new GnssLocationProvider$$ExternalSyntheticLambda14(this, runnable, 1))) {
            return;
        }
        this.mWakeLock.release();
    }

    public abstract void releaseDozeMode();

    public final void reloadGpsProperties() {
        this.mGnssVendorConfig.getClass();
        boolean isIzatServiceEnabled = GnssVendorConfig.isIzatServiceEnabled();
        GnssConfiguration gnssConfiguration = this.mGnssConfiguration;
        if (!isIzatServiceEnabled) {
            gnssConfiguration.reloadGpsProperties(-1, false);
            this.mNIHandler.setEmergencyExtensionSeconds(gnssConfiguration.mEsExtensionSec);
            this.mSuplEsEnabled = gnssConfiguration.getIntConfig("SUPL_ES") == 1;
            GnssVisibilityControl gnssVisibilityControl = this.mGnssVisibilityControl;
            if (gnssVisibilityControl != null) {
                gnssVisibilityControl.runOnHandler(
                        new GnssVisibilityControl$$ExternalSyntheticLambda1(
                                gnssVisibilityControl,
                                PortStatus_1_1$$ExternalSyntheticOutline0.m(
                                        "com.sec.location.nfwlocationprivacy"),
                                0));
                return;
            }
            return;
        }
        if (this.mGnssVisibilityControl != null) {
            gnssConfiguration.loadPropertiesFromCarrierConfig(-1, false);
            GnssVisibilityControl gnssVisibilityControl2 = this.mGnssVisibilityControl;
            gnssVisibilityControl2.getClass();
            ArrayList arrayList = new ArrayList();
            arrayList.add("com.sec.location.nfwlocationprivacy");
            gnssVisibilityControl2.runOnHandler(
                    new GnssVisibilityControl$$ExternalSyntheticLambda1(
                            gnssVisibilityControl2, arrayList, 0));
            GnssConfiguration.setEsExtensionSec();
            this.mNIHandler.setEmergencyExtensionSeconds(GnssConfiguration.getEsExtensionSecCSC());
        }
    }

    public final void restartLocationRequest() {
        Log.d("GnssLocationProvider", "restartLocationRequest");
        setStarted(false);
        if (this.mSecGpsDump.size() > 300) {
            this.mSecGpsDump.remove(0);
        }
        this.mSecGpsDump.add("\n" + getTimestamp() + ": restartLocationRequest");
        updateRequirements();
    }

    public abstract void secLocationValidate(Location location, long j);

    public final void setGpsEnabled(boolean z) {
        synchronized (this.mLock) {
            this.mGpsEnabled = z;
        }
    }

    public final boolean setPositionMode(int i, int i2, boolean z) {
        GnssPositionMode gnssPositionMode = new GnssPositionMode(i, i2, z);
        GnssPositionMode gnssPositionMode2 = this.mLastPositionMode;
        if (gnssPositionMode2 != null && gnssPositionMode2.equals(gnssPositionMode)) {
            return true;
        }
        boolean positionMode = this.mGnssNative.setPositionMode(i, 0, i2, 0, 0, z);
        if (positionMode) {
            this.mLastPositionMode = gnssPositionMode;
        } else {
            this.mLastPositionMode = null;
        }
        return positionMode;
    }

    public final void setRefLocation(int i, CellIdentity cellIdentity) {
        long cid;
        int lac;
        int i2;
        long j;
        int i3;
        int i4;
        int i5;
        String mccString = cellIdentity.getMccString();
        String mncString = cellIdentity.getMncString();
        int parseInt = mccString != null ? Integer.parseInt(mccString) : Integer.MAX_VALUE;
        int parseInt2 = mncString != null ? Integer.parseInt(mncString) : Integer.MAX_VALUE;
        if (i == 1) {
            CellIdentityGsm cellIdentityGsm = (CellIdentityGsm) cellIdentity;
            cid = cellIdentityGsm.getCid();
            lac = cellIdentityGsm.getLac();
        } else {
            if (i != 2) {
                if (i == 4) {
                    CellIdentityLte cellIdentityLte = (CellIdentityLte) cellIdentity;
                    long ci = cellIdentityLte.getCi();
                    int tac = cellIdentityLte.getTac();
                    i5 = cellIdentityLte.getPci();
                    j = ci;
                    i2 = Integer.MAX_VALUE;
                    i4 = Integer.MAX_VALUE;
                    i3 = tac;
                } else if (i != 8) {
                    j = Long.MAX_VALUE;
                    i2 = Integer.MAX_VALUE;
                    i3 = Integer.MAX_VALUE;
                    i5 = i3;
                    i4 = i5;
                } else {
                    CellIdentityNr cellIdentityNr = (CellIdentityNr) cellIdentity;
                    long nci = cellIdentityNr.getNci();
                    int tac2 = cellIdentityNr.getTac();
                    int pci = cellIdentityNr.getPci();
                    i4 = cellIdentityNr.getNrarfcn();
                    j = nci;
                    i2 = Integer.MAX_VALUE;
                    i3 = tac2;
                    i5 = pci;
                }
                this.mGnssNative.setAgpsReferenceLocationCellId(
                        i, parseInt, parseInt2, i2, j, i3, i5, i4);
            }
            CellIdentityWcdma cellIdentityWcdma = (CellIdentityWcdma) cellIdentity;
            cid = cellIdentityWcdma.getCid();
            lac = cellIdentityWcdma.getLac();
        }
        i2 = lac;
        j = cid;
        i3 = Integer.MAX_VALUE;
        i5 = i3;
        i4 = i5;
        this.mGnssNative.setAgpsReferenceLocationCellId(i, parseInt, parseInt2, i2, j, i3, i5, i4);
    }

    public final void setStarted(boolean z) {
        if (this.mStarted != z) {
            this.mStarted = z;
            this.mStartedChangedElapsedRealtime = SystemClock.elapsedRealtime();
        }
    }

    public abstract void setSuplServerSec();

    public abstract void setXtraDownloadedTime();

    public final void startNavigating() {
        int intConfig;
        if (this.mStarted) {
            return;
        }
        Log.d("GnssLocationProvider", "startNavigating");
        startNavigatingSec();
        this.mTimeToFirstFix = 0;
        this.mLastFixTime = 0L;
        setStarted(true);
        this.mPositionMode = 0;
        boolean z =
                Settings.Global.getInt(
                                this.mContext.getContentResolver(), "assisted_gps_enabled", 1)
                        != 0;
        GnssNative gnssNative = this.mGnssNative;
        int i =
                (!z
                                || (intConfig = this.mGnssConfiguration.getIntConfig("SUPL_MODE"))
                                        == 0
                                || !gnssNative.mCapabilities.hasMsb()
                                || (intConfig & 1) == 0)
                        ? 0
                        : 1;
        this.mPositionMode = i;
        if (i != 0) {
            this.mPositionMode = getPositionModeSec(i);
        }
        if (this.mPositionMode != 0) {
            updateSuplServerForNewSISession();
        }
        int i2 = this.mPositionMode;
        Log.d(
                "GnssLocationProvider",
                "setting position_mode to "
                        .concat(
                                i2 != 0
                                        ? i2 != 1 ? i2 != 2 ? "unknown" : "MS_ASSISTED" : "MS_BASED"
                                        : "standalone"));
        int i3 = gnssNative.mCapabilities.hasScheduling() ? this.mFixInterval : 1000;
        if (!setPositionMode(this.mPositionMode, i3, this.mProviderRequest.isLowPower())) {
            setStarted(false);
            Log.e("GnssLocationProvider", "set_position_mode failed in startNavigating()");
        } else {
            if (!gnssNative.start()) {
                setStarted(false);
                Log.e("GnssLocationProvider", "native_start failed in startNavigating()");
                return;
            }
            this.mLocationExtras.set(0, 0, 0);
            this.mFixRequestTime = SystemClock.elapsedRealtime();
            if (!gnssNative.mCapabilities.hasScheduling() && this.mFixInterval >= 60000) {
                this.mAlarmManager.set(
                        2,
                        SystemClock.elapsedRealtime() + 60000,
                        "GnssLocationProvider",
                        this.mTimeoutListener,
                        this.mHandler);
            }
            this.mNSLocationProviderHelper.reportProviderStatus(
                    LocationConstants.STATE_TYPE.NAVIGATING,
                    Integer.valueOf(this.mPositionMode),
                    Integer.valueOf(i3));
        }
    }

    public abstract void startNavigatingSec();

    public final void stopBatching() {
        Log.d("GnssLocationProvider", "stopBatching");
        if (this.mBatchingStarted) {
            GnssLocationProvider$$ExternalSyntheticLambda10
                    gnssLocationProvider$$ExternalSyntheticLambda10 = this.mBatchingAlarm;
            if (gnssLocationProvider$$ExternalSyntheticLambda10 != null) {
                this.mAlarmManager.cancel(gnssLocationProvider$$ExternalSyntheticLambda10);
                this.mBatchingAlarm = null;
            }
            GnssNative gnssNative = this.mGnssNative;
            gnssNative.flushBatch();
            gnssNative.stopBatch();
            this.mBatchingStarted = false;
        }
    }

    public final void stopNavigating() {
        GnssHalStatus gnssHalStatus;
        RCPManagerService$$ExternalSyntheticOutline0.m(
                "GnssLocationProvider",
                new StringBuilder("stopNavigating, mStarted="),
                this.mStarted);
        if (this.mStarted) {
            setStarted(false);
            this.mGnssVendorConfig.getClass();
            if (GnssVendorConfig.isLsiGnss()) {
                gnssHalStatus = new GnssHalStatus();
                gnssHalStatus.triggerCheckingHalStatus(3000L);
            } else {
                gnssHalStatus = null;
            }
            this.mGnssNative.stop();
            if (gnssHalStatus != null) {
                gnssHalStatus.updateHalStatusChecked();
            }
            this.mLastFixTime = 0L;
            this.mLastPositionMode = null;
            stopNavigatingSec();
            this.mLocationExtras.set(0, 0, 0);
        }
        this.mAlarmManager.cancel(this.mTimeoutListener);
        this.mAlarmManager.cancel(this.mWakeupListener);
        this.mNSLocationProviderHelper.reportProviderStatus(
                LocationConstants.STATE_TYPE.NAVIGATING, -1, null);
    }

    public abstract void stopNavigatingSec();

    public final void updateClientUids(WorkSource workSource) {
        if (workSource.equals(this.mClientSource)) {
            return;
        }
        try {
            this.mBatteryStats.noteGpsChanged(this.mClientSource, workSource);
        } catch (RemoteException e) {
            Log.w("GnssLocationProvider", "RemoteException", e);
        }
        ArrayList[] diffChains = WorkSource.diffChains(this.mClientSource, workSource);
        if (diffChains != null) {
            ArrayList<WorkSource.WorkChain> arrayList = diffChains[0];
            ArrayList<WorkSource.WorkChain> arrayList2 = diffChains[1];
            if (arrayList != null) {
                for (WorkSource.WorkChain workChain : arrayList) {
                    this.mAppOps.startOpNoThrow(
                            2, workChain.getAttributionUid(), workChain.getAttributionTag());
                }
            }
            if (arrayList2 != null) {
                for (WorkSource.WorkChain workChain2 : arrayList2) {
                    this.mAppOps.finishOp(
                            2, workChain2.getAttributionUid(), workChain2.getAttributionTag());
                }
            }
            this.mClientSource.transferWorkChains(workSource);
        }
        WorkSource[] returningDiffs = this.mClientSource.setReturningDiffs(workSource);
        if (returningDiffs != null) {
            WorkSource workSource2 = returningDiffs[0];
            WorkSource workSource3 = returningDiffs[1];
            if (workSource2 != null) {
                for (int i = 0; i < workSource2.size(); i++) {
                    this.mAppOps.startOpNoThrow(
                            2, workSource2.getUid(i), workSource2.getPackageName(i));
                }
            }
            if (workSource3 != null) {
                for (int i2 = 0; i2 < workSource3.size(); i2++) {
                    this.mAppOps.finishOp(
                            2, workSource3.getUid(i2), workSource3.getPackageName(i2));
                }
            }
        }
    }

    public final void updateEnabled() {
        boolean z;
        GnssHalStatus gnssHalStatus;
        LocationManager locationManager =
                (LocationManager) this.mContext.getSystemService(LocationManager.class);
        Iterator it =
                ((UserManager) this.mContext.getSystemService(UserManager.class))
                        .getVisibleUsers()
                        .iterator();
        final boolean z2 = false;
        boolean z3 = false;
        while (it.hasNext()) {
            z3 |= locationManager.isLocationEnabledForUser((UserHandle) it.next());
        }
        ProviderRequest providerRequest = this.mProviderRequest;
        final boolean z4 = true;
        boolean z5 =
                (providerRequest != null
                                && providerRequest.isActive()
                                && this.mProviderRequest.isBypass())
                        | z3;
        synchronized (this.mLock) {
            z = z5 & (!this.mAutomotiveSuspend);
        }
        boolean z6 = z & (!this.mShutdown);
        if (z6 == isGpsEnabled()) {
            return;
        }
        if (!z6) {
            Log.d("GnssLocationProvider", "handleDisable");
            setGpsEnabled(false);
            updateClientUids(new WorkSource());
            stopNavigating();
            stopBatching();
            final GnssVisibilityControl gnssVisibilityControl = this.mGnssVisibilityControl;
            if (gnssVisibilityControl != null
                    && !gnssVisibilityControl.mHandler.runWithScissors(
                            new Runnable() { // from class:
                                             // com.android.server.location.gnss.GnssVisibilityControl$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    GnssVisibilityControl gnssVisibilityControl2 =
                                            GnssVisibilityControl.this;
                                    boolean z7 = z2;
                                    Log.d(
                                            "GnssVisibilityControl",
                                            "handleGpsEnabledChanged, mIsGpsEnabled: "
                                                    + gnssVisibilityControl2.mIsGpsEnabled
                                                    + ", isGpsEnabled: "
                                                    + z7);
                                    gnssVisibilityControl2.mIsGpsEnabled = z7;
                                    if (z7) {
                                        gnssVisibilityControl2
                                                .setNfwLocationAccessProxyAppsInGnssHal(
                                                        gnssVisibilityControl2
                                                                .getLocationPermissionEnabledProxyApps());
                                    } else {
                                        gnssVisibilityControl2
                                                .setNfwLocationAccessProxyAppsInGnssHal(
                                                        GnssVisibilityControl
                                                                .NO_LOCATION_ENABLED_PROXY_APPS);
                                    }
                                }
                            },
                            3000L)) {
                Log.w(
                        "GnssVisibilityControl",
                        "Native call to disable non-framework location access in GNSS HAL may get"
                            + " executed after native_cleanup().");
            }
            GnssNative gnssNative = this.mGnssNative;
            gnssNative.cleanupBatching();
            gnssNative.cleanup();
            this.mGnssVendorConfig.getClass();
            if (GnssVendorConfig.isBroadcomGnss()) {
                gnssNative.initLocationOff();
                return;
            }
            return;
        }
        Log.d("GnssLocationProvider", "handleEnable");
        this.mGnssVendorConfig.getClass();
        if (GnssVendorConfig.isLsiGnss()) {
            gnssHalStatus = new GnssHalStatus();
            gnssHalStatus.triggerCheckingHalStatus(3000L);
        } else {
            gnssHalStatus = null;
        }
        GnssNative gnssNative2 = this.mGnssNative;
        boolean init = gnssNative2.init();
        if (gnssHalStatus != null) {
            gnssHalStatus.updateHalStatusChecked();
        }
        if (init) {
            setGpsEnabled(true);
            this.mSupportsPsds = gnssNative2.isPsdsSupported();
            int i = SystemProperties.getInt("ro.vendor.api_level", 0);
            if (GnssVendorConfig.isLsiGnss() && i > 33) {
                this.mSupportsPsds = false;
            }
            setSuplServerSec();
            if (!"true".equals(SystemProperties.get("ro.location.hwflp"))) {
                Log.d("SLocationProxy", "GNSS batching is disabled.");
            } else if (gnssNative2.initBatching() && gnssNative2.getBatchSize() > 1) {
                z2 = true;
            }
            this.mBatchingEnabled = z2;
            final GnssVisibilityControl gnssVisibilityControl2 = this.mGnssVisibilityControl;
            if (gnssVisibilityControl2 != null) {
                gnssVisibilityControl2.mHandler.runWithScissors(
                        new Runnable() { // from class:
                                         // com.android.server.location.gnss.GnssVisibilityControl$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                GnssVisibilityControl gnssVisibilityControl22 =
                                        GnssVisibilityControl.this;
                                boolean z7 = z4;
                                Log.d(
                                        "GnssVisibilityControl",
                                        "handleGpsEnabledChanged, mIsGpsEnabled: "
                                                + gnssVisibilityControl22.mIsGpsEnabled
                                                + ", isGpsEnabled: "
                                                + z7);
                                gnssVisibilityControl22.mIsGpsEnabled = z7;
                                if (z7) {
                                    gnssVisibilityControl22.setNfwLocationAccessProxyAppsInGnssHal(
                                            gnssVisibilityControl22
                                                    .getLocationPermissionEnabledProxyApps());
                                } else {
                                    gnssVisibilityControl22.setNfwLocationAccessProxyAppsInGnssHal(
                                            GnssVisibilityControl.NO_LOCATION_ENABLED_PROXY_APPS);
                                }
                            }
                        },
                        3000L);
            }
        } else {
            setGpsEnabled(false);
            Log.w("GnssLocationProvider", "Failed to enable location provider");
        }
        if (GnssVendorConfig.isIzatServiceEnabled()) {
            return;
        }
        handleEnableSec();
    }

    /* JADX WARN: Type inference failed for: r0v40, types: [com.android.server.location.gnss.GnssLocationProvider$$ExternalSyntheticLambda10] */
    public final void updateRequirements() {
        ProviderRequest providerRequest = this.mProviderRequest;
        if (providerRequest == null || providerRequest.getWorkSource() == null) {
            return;
        }
        Log.d("GnssLocationProvider", "setRequest " + this.mProviderRequest);
        if (this.mSecGpsDump.size() > 300) {
            this.mSecGpsDump.remove(0);
        }
        this.mSecGpsDump.add(
                "\n"
                        + getTimestamp()
                        + ": "
                        + this.mProviderRequest
                        + " "
                        + this.mProviderRequest.getWorkSource());
        StringBuilder sb = new StringBuilder("mStarted =  ");
        sb.append(this.mStarted);
        sb.append(", isEnabled = ");
        sb.append(isGpsEnabled());
        Log.d("GnssLocationProvider", sb.toString());
        if (!this.mProviderRequest.isActive() || !isGpsEnabled()) {
            updateClientUids(new WorkSource());
            stopNavigating();
            stopBatching();
            return;
        }
        updateClientUids(this.mProviderRequest.getWorkSource());
        if (this.mProviderRequest.getIntervalMillis() <= 2147483647L) {
            this.mFixInterval = (int) this.mProviderRequest.getIntervalMillis();
        } else {
            Log.w(
                    "GnssLocationProvider",
                    "interval overflow: " + this.mProviderRequest.getIntervalMillis());
            this.mFixInterval = Integer.MAX_VALUE;
        }
        int max = Math.max(this.mFixInterval, 1000);
        final long min =
                Math.min(
                        this.mProviderRequest.getMaxUpdateDelayMillis(),
                        BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS);
        boolean z = this.mBatchingEnabled;
        GnssNative gnssNative = this.mGnssNative;
        if (z) {
            long j = max;
            if (min / 2 >= j) {
                stopNavigating();
                this.mFixInterval = max;
                long j2 = min / j;
                Log.d("GnssLocationProvider", "startBatching " + this.mFixInterval + " " + min);
                if (!gnssNative.startBatch(
                        TimeUnit.MILLISECONDS.toNanos(this.mFixInterval),
                        FullScreenMagnificationGestureHandler.MAX_SCALE,
                        true)) {
                    Log.e("GnssLocationProvider", "native_start_batch failed in startBatching()");
                    return;
                }
                this.mBatchingStarted = true;
                if (j2 < getBatchSize()) {
                    this.mBatchingAlarm =
                            new AlarmManager
                                    .OnAlarmListener() { // from class:
                                                         // com.android.server.location.gnss.GnssLocationProvider$$ExternalSyntheticLambda10
                                @Override // android.app.AlarmManager.OnAlarmListener
                                public final void onAlarm() {
                                    boolean z2;
                                    GnssLocationProvider gnssLocationProvider =
                                            GnssLocationProvider.this;
                                    long j3 = min;
                                    synchronized (gnssLocationProvider.mLock) {
                                        try {
                                            if (gnssLocationProvider.mBatchingAlarm != null) {
                                                gnssLocationProvider.mAlarmManager.setExact(
                                                        2,
                                                        SystemClock.elapsedRealtime() + j3,
                                                        "GnssLocationProvider",
                                                        gnssLocationProvider.mBatchingAlarm,
                                                        LocationServiceThread.getHandler());
                                                z2 = true;
                                            } else {
                                                z2 = false;
                                            }
                                        } catch (Throwable th) {
                                            throw th;
                                        }
                                    }
                                    if (z2) {
                                        gnssLocationProvider.mGnssNative.flushBatch();
                                    }
                                }
                            };
                    this.mAlarmManager.setExact(
                            2,
                            SystemClock.elapsedRealtime() + min,
                            "GnssLocationProvider",
                            this.mBatchingAlarm,
                            LocationServiceThread.getHandler());
                    return;
                }
                return;
            }
        }
        stopBatching();
        if (this.mStarted) {
            this.mNSLocationProviderHelper.reportProviderStatus(
                    LocationConstants.STATE_TYPE.UPDATE_GNSS_INTERVAL,
                    null,
                    Integer.valueOf(this.mFixInterval));
        }
        if (this.mStarted && gnssNative.mCapabilities.hasScheduling()) {
            if (setPositionMode(
                    this.mPositionMode, this.mFixInterval, this.mProviderRequest.isLowPower())) {
                return;
            }
            Log.e("GnssLocationProvider", "set_position_mode failed in updateRequirements");
        } else {
            if (!this.mStarted) {
                startNavigating();
                return;
            }
            this.mAlarmManager.cancel(this.mTimeoutListener);
            if (this.mFixInterval >= 60000) {
                this.mAlarmManager.set(
                        2,
                        SystemClock.elapsedRealtime() + 60000,
                        "GnssLocationProvider",
                        this.mTimeoutListener,
                        this.mHandler);
            }
        }
    }

    public abstract void updateSuplServerForNewSISession();
}
