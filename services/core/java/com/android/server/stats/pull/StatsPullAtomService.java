package com.android.server.stats.pull;

import android.app.ActivityManagerInternal;
import android.app.AppOpsManager;
import android.app.INotificationManager;
import android.app.PendingIntentStats;
import android.app.ProcessMemoryState;
import android.app.RuntimeAppOpAccessMessage;
import android.app.StatsManager;
import android.app.usage.NetworkStatsManager;
import android.bluetooth.BluetoothActivityEnergyInfo;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.UidTraffic;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.IncrementalStatesInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.content.pm.PermissionInfo;
import android.content.pm.UserInfo;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.hardware.display.DisplayManager;
import android.hardware.face.FaceManager;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.health.HealthInfo;
import android.icu.util.TimeZone;
import android.media.AudioManager;
import android.media.MediaDrm;
import android.media.UnsupportedSchemeException;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.net.NetworkStats;
import android.net.NetworkTemplate;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.BatteryStatsInternal;
import android.os.BatteryStatsManager;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.CoolingDevice;
import android.os.Debug;
import android.os.Environment;
import android.os.FileUtils;
import android.os.IStoraged;
import android.os.IThermalEventListener;
import android.os.IThermalService;
import android.os.OutcomeReceiver;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.ServiceSpecificException;
import android.os.StatFs;
import android.os.SynchronousResultReceiver;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Temperature;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.connectivity.WifiActivityEnergyInfo;
import android.os.incremental.IncrementalManager;
import android.os.storage.DiskInfo;
import android.os.storage.StorageManager;
import android.os.storage.VolumeInfo;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.security.metrics.IKeystoreMetrics;
import android.security.metrics.KeyCreationWithAuthInfo;
import android.security.metrics.KeyCreationWithGeneralInfo;
import android.security.metrics.KeyCreationWithPurposeAndModesInfo;
import android.security.metrics.KeyOperationWithGeneralInfo;
import android.security.metrics.KeyOperationWithPurposeAndModesInfo;
import android.security.metrics.KeystoreAtom;
import android.security.metrics.RkpErrorStats;
import android.security.metrics.StorageStats;
import android.telephony.ModemActivityInfo;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.MathUtils;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.StatsEvent;
import android.util.proto.ProtoOutputStream;
import android.uwb.UwbActivityEnergyInfo;
import android.uwb.UwbManager;
import android.view.Display;

import com.android.internal.app.procstats.IProcessStats;
import com.android.internal.app.procstats.ProcessStats;
import com.android.internal.app.procstats.StatsEventOutput;
import com.android.internal.hidden_from_bootclasspath.com.android.server.power.optimization.Flags;
import com.android.internal.os.BackgroundThread;
import com.android.internal.os.BinderCallsStats;
import com.android.internal.os.KernelAllocationStats;
import com.android.internal.os.KernelCpuBpfTracking;
import com.android.internal.os.KernelCpuThreadReader;
import com.android.internal.os.KernelCpuThreadReaderDiff;
import com.android.internal.os.KernelCpuThreadReaderSettingsObserver;
import com.android.internal.os.KernelCpuTotalBpfMapReader;
import com.android.internal.os.KernelCpuUidTimeReader;
import com.android.internal.os.KernelSingleProcessCpuThreadReader;
import com.android.internal.os.LooperStats;
import com.android.internal.os.PowerProfile;
import com.android.internal.os.ProcessCpuTracker;
import com.android.internal.os.SelectedProcessCpuThreadReader;
import com.android.internal.os.StoragedUidIoStatsReader;
import com.android.internal.util.CollectionUtils;
import com.android.internal.util.ConcurrentUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.net.module.util.NetworkStatsUtils;
import com.android.role.RoleManagerLocal;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinderCallsStatsService;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.FileDescriptorWatcher$FileDescriptorLeakWatcher$$ExternalSyntheticOutline0;
import com.android.server.HeapdumpWatcher$$ExternalSyntheticOutline0;
import com.android.server.LocalManagerRegistry;
import com.android.server.LocalServices;
import com.android.server.PinnerService;
import com.android.server.SystemServerInitThreadPool$$ExternalSyntheticLambda0;
import com.android.server.SystemService;
import com.android.server.SystemServiceManager;
import com.android.server.SystemServiceManager$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.am.MemoryStatUtil;
import com.android.server.health.HealthServiceWrapper;
import com.android.server.pm.UserManagerInternal;
import com.android.server.power.stats.KernelWakelockReader;
import com.android.server.power.stats.KernelWakelockStats;
import com.android.server.power.stats.SystemServerCpuThreadReader;
import com.android.server.stats.pull.netstats.NetworkStatsExt;
import com.android.server.stats.pull.netstats.SubInfo;
import com.android.server.timezonedetector.ConfigurationInternal;
import com.android.server.timezonedetector.MetricsTimeZoneDetectorState;
import com.android.server.timezonedetector.TimeZoneDetectorInternalImpl;
import com.android.server.timezonedetector.TimeZoneDetectorStrategyImpl;

import libcore.io.IoUtils;

import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.regex.Pattern;

public class StatsPullAtomService extends SystemService {
    public static final boolean ENABLE_MOBILE_DATA_STATS_AGGREGATED_PULLER;
    public AggregatedMobileDataStatsPuller mAggregatedMobileDataStatsPuller;
    public final Object mAppOpsLock;
    public int mAppOpsSamplingRate;
    public final Object mAppSizeLock;
    public final Object mAppsOnExternalStorageInfoLock;
    public final Object mAttributedAppOpsLock;
    public File mBaseDir;
    public final Object mBinderCallsStatsExceptionsLock;
    public final Object mBinderCallsStatsLock;
    public final Object mBluetoothActivityInfoLock;
    public final Object mBluetoothBytesTransferLock;
    public final Object mBuildInformationLock;
    public final Object mCategorySizeLock;
    public final Context mContext;
    public final Object mCooldownDeviceLock;
    public final Object mCpuActiveTimeLock;
    public final Object mCpuClusterTimeLock;
    public final Object mCpuTimePerClusterFreqLock;
    public final Object mCpuTimePerThreadFreqLock;
    public final Object mCpuTimePerUidFreqLock;
    public final Object mCpuTimePerUidLock;
    public KernelCpuUidTimeReader.KernelCpuUidActiveTimeReader mCpuUidActiveTimeReader;
    public KernelCpuUidTimeReader.KernelCpuUidClusterTimeReader mCpuUidClusterTimeReader;
    public KernelCpuUidTimeReader.KernelCpuUidFreqTimeReader mCpuUidFreqTimeReader;
    public KernelCpuUidTimeReader.KernelCpuUidUserSysTimeReader mCpuUidUserSysTimeReader;
    public final ArraySet mDangerousAppOpsList;
    public final Object mDangerousPermissionStateLock;
    public final Object mDataBytesTransferLock;
    public final Object mDebugElapsedClockLock;
    public long mDebugElapsedClockPreviousValue;
    public long mDebugElapsedClockPullCount;
    public final Object mDebugFailingElapsedClockLock;
    public long mDebugFailingElapsedClockPreviousValue;
    public long mDebugFailingElapsedClockPullCount;
    public final Object mDeviceCalculatedPowerUseLock;
    public final Object mDirectoryUsageLock;
    public final Object mDiskIoLock;
    public final Object mDiskStatsLock;
    public final Object mExternalStorageInfoLock;
    public final Object mFaceSettingsLock;
    public final Object mHealthHalLock;
    public HealthServiceWrapper mHealthService;
    public final ArrayList mHistoricalSubs;
    public IKeystoreMetrics mIKeystoreMetrics;
    public final Object mInstalledIncrementalPackagesLock;
    public final Object mIonHeapSizeLock;
    public KernelCpuThreadReaderDiff mKernelCpuThreadReader;
    public final Object mKernelWakelockLock;
    public KernelWakelockReader mKernelWakelockReader;
    public final Object mKeystoreLock;
    public final Object mLooperStatsLock;
    public final Object mModemActivityInfoLock;
    public final ArrayList mNetworkStatsBaselines;
    public NetworkStatsManager mNetworkStatsManager;
    public INotificationManager mNotificationManagerService;
    public final Object mNotificationRemoteViewsLock;
    public final Object mNotificationStatsLock;
    public final Object mNumBiometricsEnrolledLock;
    public final Object mPowerProfileLock;
    public final Object mProcStatsLock;
    public final Object mProcessCpuTimeLock;
    public ProcessCpuTracker mProcessCpuTracker;
    public final Object mProcessMemoryHighWaterMarkLock;
    public final Object mProcessMemoryStateLock;
    public IProcessStats mProcessStatsService;
    public final Object mProcessSystemIonHeapSizeLock;
    public final Object mRoleHolderLock;
    public final Object mRuntimeAppOpAccessMessageLock;
    public final Object mSettingsStatsLock;
    public StatsPullAtomCallbackImpl mStatsCallbackImpl;
    public StatsManager mStatsManager;
    public StatsSubscriptionsListener mStatsSubscriptionsListener;
    public StorageManager mStorageManager;
    public IStoraged mStorageService;
    public final Object mStoragedLock;
    public StoragedUidIoStatsReader mStoragedUidIoStatsReader;
    public SubscriptionManager mSubscriptionManager;
    public SelectedProcessCpuThreadReader mSurfaceFlingerProcessCpuThreadReader;
    public final Object mSystemElapsedRealtimeLock;
    public final Object mSystemIonHeapSizeLock;
    public final Object mSystemUptimeLock;
    public TelephonyManager mTelephony;
    public final Object mTemperatureLock;
    public final Object mThermalLock;
    public IThermalService mThermalService;
    public final Object mTimeZoneDataInfoLock;
    public final Object mTimeZoneDetectionInfoLock;
    public KernelWakelockStats mTmpWakelockStats;
    public final Object mUwbActivityInfoLock;
    public UwbManager mUwbManager;
    public final Object mWifiActivityInfoLock;
    public WifiManager mWifiManager;
    public static final int RANDOM_SEED = new Random().nextInt();
    public static final long NETSTATS_UID_DEFAULT_BUCKET_DURATION_MS = TimeUnit.HOURS.toMillis(2);

    /* renamed from: com.android.server.stats.pull.StatsPullAtomService$2, reason: invalid class name */
    public final class AnonymousClass2 implements Executor {
        @Override // java.util.concurrent.Executor
        public final void execute(Runnable runnable) {
            runnable.run();
        }
    }

    public final class AppOpEntry {
        public final String mAttributionTag;
        public final int mHash;
        public final AppOpsManager.HistoricalOp mOp;
        public final String mPackageName;
        public final int mUid;

        public AppOpEntry(String str, String str2, AppOpsManager.HistoricalOp historicalOp, int i) {
            this.mPackageName = str;
            this.mAttributionTag = str2;
            this.mUid = i;
            this.mOp = historicalOp;
            this.mHash =
                    ((str.hashCode() + StatsPullAtomService.RANDOM_SEED) & Integer.MAX_VALUE) % 100;
        }
    }

    public final class ConnectivityStatsCallback extends ConnectivityManager.NetworkCallback {
        @Override // android.net.ConnectivityManager.NetworkCallback
        public final void onAvailable(Network network) {
            FrameworkStatsLog.write(98, network.getNetId(), 1);
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public final void onLost(Network network) {
            FrameworkStatsLog.write(98, network.getNetId(), 2);
        }
    }

    public final class StatsPullAtomCallbackImpl implements StatsManager.StatsPullAtomCallback {
        public StatsPullAtomCallbackImpl() {}

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Removed duplicated region for block: B:428:0x0396 A[Catch: all -> 0x009c, TryCatch #53 {all -> 0x009c, blocks: (B:56:0x008a, B:57:0x009b, B:59:0x009f, B:60:0x00a5, B:69:0x00b5, B:70:0x00b6, B:71:0x00bc, B:80:0x00ca, B:81:0x00cb, B:82:0x00d1, B:91:0x00df, B:92:0x00e0, B:93:0x00e6, B:102:0x00f4, B:103:0x00f5, B:104:0x00fb, B:113:0x0109, B:114:0x010a, B:115:0x0110, B:124:0x0120, B:125:0x0121, B:126:0x0127, B:135:0x0135, B:136:0x0136, B:137:0x013a, B:146:0x0149, B:147:0x014a, B:148:0x0150, B:157:0x015e, B:158:0x015f, B:159:0x0165, B:168:0x0172, B:169:0x0173, B:170:0x0179, B:179:0x0189, B:180:0x018a, B:181:0x0190, B:190:0x01a0, B:191:0x01a1, B:194:0x01ad, B:196:0x01b1, B:198:0x01b7, B:201:0x01be, B:204:0x01c7, B:207:0x01d1, B:208:0x01d7, B:217:0x01e5, B:218:0x01e6, B:221:0x01f3, B:224:0x01fd, B:227:0x0209, B:228:0x020f, B:237:0x021d, B:238:0x021e, B:239:0x0222, B:248:0x0230, B:249:0x0231, B:252:0x023d, B:253:0x0243, B:262:0x0251, B:263:0x0252, B:264:0x0258, B:273:0x0266, B:274:0x0267, B:275:0x026d, B:284:0x027a, B:285:0x027b, B:286:0x027f, B:295:0x028d, B:296:0x028e, B:297:0x0294, B:306:0x02a1, B:307:0x02a2, B:308:0x02a8, B:317:0x02b5, B:318:0x02b6, B:319:0x02ba, B:328:0x02c9, B:329:0x02ca, B:330:0x02ce, B:339:0x02dc, B:340:0x02dd, B:341:0x02e3, B:350:0x02f4, B:351:0x02f5, B:352:0x02fb, B:361:0x030c, B:362:0x030d, B:363:0x0313, B:372:0x0323, B:373:0x0324, B:374:0x032a, B:383:0x0338, B:384:0x0339, B:385:0x033f, B:394:0x0350, B:395:0x0351, B:396:0x0357, B:405:0x0368, B:406:0x0369, B:407:0x036f, B:416:0x0380, B:417:0x0381, B:418:0x0387, B:427:0x0395, B:428:0x0396, B:429:0x039c, B:438:0x03aa, B:439:0x03ab, B:442:0x03b4, B:443:0x03ba, B:452:0x03c8, B:453:0x03c9, B:454:0x03cd, B:463:0x03db, B:464:0x03dc, B:465:0x03e0, B:474:0x03ee, B:475:0x03ef, B:478:0x03f8, B:481:0x0404, B:484:0x040e, B:487:0x0418, B:490:0x0422, B:491:0x0428, B:500:0x0439, B:501:0x043a, B:502:0x043e, B:511:0x044b, B:512:0x044c, B:513:0x0452, B:522:0x0463, B:523:0x0464, B:524:0x046a, B:533:0x0477, B:534:0x0478, B:535:0x047e, B:544:0x048b, B:545:0x048c, B:546:0x0492, B:555:0x04a2, B:556:0x04a3, B:557:0x04a9, B:566:0x04b9, B:567:0x04ba, B:568:0x04c0, B:577:0x04d0, B:578:0x04d1, B:579:0x04d7, B:588:0x04e5, B:589:0x04e6, B:590:0x04ec, B:599:0x04fa, B:600:0x04fb, B:601:0x04ff, B:610:0x050c, B:611:0x050d, B:612:0x0513, B:621:0x0520, B:622:0x0521, B:623:0x0527, B:632:0x0534, B:633:0x0535, B:634:0x0539, B:643:0x0547, B:644:0x0548, B:645:0x054e, B:654:0x055e, B:655:0x055f, B:656:0x0565, B:665:0x0573, B:666:0x0574, B:667:0x057a, B:676:0x0588, B:677:0x0589, B:678:0x058f, B:687:0x059d, B:688:0x059e, B:689:0x05a4, B:698:0x05b2, B:699:0x05b3, B:700:0x05b9, B:709:0x05ca, B:710:0x05cb, B:711:0x05d1, B:720:0x05e2, B:721:0x05e3, B:722:0x05e9, B:731:0x05fa, B:548:0x0493, B:549:0x049b, B:365:0x0314, B:366:0x031c, B:431:0x039d, B:432:0x03a3, B:241:0x0223, B:242:0x0229, B:691:0x05a5, B:692:0x05ab, B:625:0x0528, B:626:0x052d, B:310:0x02a9, B:311:0x02ae, B:128:0x0128, B:129:0x012e, B:62:0x00a6, B:63:0x00ae, B:376:0x032b, B:377:0x0331, B:559:0x04aa, B:560:0x04b2, B:493:0x0429, B:494:0x0432, B:636:0x053a, B:637:0x0540, B:445:0x03bb, B:446:0x03c1, B:321:0x02bb, B:322:0x02c2, B:139:0x013b, B:140:0x0142, B:702:0x05ba, B:703:0x05c3, B:73:0x00bd, B:74:0x00c3, B:504:0x043f, B:505:0x0444, B:570:0x04c1, B:571:0x04c9, B:387:0x0340, B:388:0x0349, B:255:0x0244, B:256:0x024a, B:456:0x03ce, B:457:0x03d4, B:332:0x02cf, B:333:0x02d5, B:647:0x054f, B:648:0x0557, B:150:0x0151, B:151:0x0157, B:84:0x00d2, B:85:0x00d8, B:713:0x05d2, B:714:0x05db, B:515:0x0453, B:516:0x045c, B:581:0x04d8, B:582:0x04de, B:210:0x01d8, B:211:0x01de, B:266:0x0259, B:267:0x025f, B:398:0x0358, B:399:0x0361, B:467:0x03e1, B:468:0x03e7, B:161:0x0166, B:162:0x016b, B:658:0x0566, B:659:0x056c, B:343:0x02e4, B:344:0x02ed, B:95:0x00e7, B:96:0x00ed, B:526:0x046b, B:527:0x0470, B:724:0x05ea, B:725:0x05f3, B:592:0x04ed, B:593:0x04f3, B:277:0x026e, B:278:0x0273, B:409:0x0370, B:410:0x0379, B:669:0x057b, B:670:0x0581, B:172:0x017a, B:173:0x0182, B:106:0x00fc, B:107:0x0102, B:537:0x047f, B:538:0x0484, B:603:0x0500, B:604:0x0505, B:354:0x02fc, B:355:0x0305, B:288:0x0280, B:289:0x0286, B:420:0x0388, B:421:0x038e, B:680:0x0590, B:681:0x0596, B:230:0x0210, B:231:0x0216, B:614:0x0514, B:615:0x0519, B:183:0x0191, B:184:0x0199, B:117:0x0111, B:118:0x0119, B:299:0x0295, B:300:0x029a), top: B:5:0x001e, inners: #0, #1, #2, #3, #4, #5, #6, #7, #8, #9, #10, #11, #12, #13, #14, #15, #16, #17, #18, #19, #20, #21, #22, #23, #24, #25, #26, #27, #28, #29, #30, #31, #32, #33, #34, #35, #36, #37, #38, #39, #40, #41, #42, #43, #44, #45, #46, #47, #48, #49, #50, #51, #52, #54, #55, #56, #57 }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final int onPullAtom(int r6, java.util.List r7) {
            /*
                Method dump skipped, instructions count: 1728
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.stats.pull.StatsPullAtomService.StatsPullAtomCallbackImpl.onPullAtom(int,"
                        + " java.util.List):int");
        }
    }

    public final class StatsPullAtomServiceInternalImpl {
        public StatsPullAtomServiceInternalImpl() {}
    }

    public final class StatsSubscriptionsListener
            extends SubscriptionManager.OnSubscriptionsChangedListener {
        public final SubscriptionManager mSm;

        public StatsSubscriptionsListener(SubscriptionManager subscriptionManager) {
            this.mSm = subscriptionManager;
        }

        @Override // android.telephony.SubscriptionManager.OnSubscriptionsChangedListener
        public final void onSubscriptionsChanged() {
            for (SubscriptionInfo subscriptionInfo :
                    this.mSm.getCompleteActiveSubscriptionInfoList()) {
                if (((SubInfo)
                                CollectionUtils.find(
                                        StatsPullAtomService.this.mHistoricalSubs,
                                        new StatsPullAtomService$$ExternalSyntheticLambda15(
                                                1, subscriptionInfo)))
                        == null) {
                    int subscriptionId = subscriptionInfo.getSubscriptionId();
                    String mccString = subscriptionInfo.getMccString();
                    String mncString = subscriptionInfo.getMncString();
                    String subscriberId =
                            StatsPullAtomService.this.mTelephony.getSubscriberId(subscriptionId);
                    if (TextUtils.isEmpty(subscriberId)
                            || TextUtils.isEmpty(mccString)
                            || TextUtils.isEmpty(mncString)
                            || subscriptionInfo.getCarrierId() == -1) {
                        FileDescriptorWatcher$FileDescriptorLeakWatcher$$ExternalSyntheticOutline0
                                .m(
                                        subscriptionId,
                                        "subInfo of subId ",
                                        " is invalid, ignored.",
                                        "StatsPullAtomService");
                    } else {
                        SubInfo subInfo =
                                new SubInfo(
                                        mccString,
                                        subscriptionId,
                                        mncString,
                                        subscriberId,
                                        subscriptionInfo.getCarrierId(),
                                        subscriptionInfo.isOpportunistic());
                        BootReceiver$$ExternalSyntheticOutline0.m(
                                subscriptionId,
                                "subId ",
                                " added into historical sub list",
                                "StatsPullAtomService");
                        synchronized (StatsPullAtomService.this.mDataBytesTransferLock) {
                            StatsPullAtomService.this.mHistoricalSubs.add(subInfo);
                            StatsPullAtomService statsPullAtomService = StatsPullAtomService.this;
                            statsPullAtomService.mNetworkStatsBaselines.addAll(
                                    statsPullAtomService.getDataUsageBytesTransferSnapshotForSub(
                                            subInfo));
                        }
                    }
                }
            }
        }
    }

    public final class ThermalEventListener extends IThermalEventListener.Stub {
        public final void notifyThrottling(Temperature temperature) {
            FrameworkStatsLog.write(
                    189,
                    temperature.getType(),
                    temperature.getName(),
                    (int) (temperature.getValue() * 10.0f),
                    temperature.getStatus());
        }
    }

    public static void $r8$lambda$qb74jErDmbpYjoDRKkDDIHHyoHk(
            StatsPullAtomService statsPullAtomService) {
        statsPullAtomService.initializeNativePullers();
        statsPullAtomService.mStatsManager =
                (StatsManager) statsPullAtomService.mContext.getSystemService("stats");
        statsPullAtomService.mWifiManager =
                (WifiManager) statsPullAtomService.mContext.getSystemService("wifi");
        statsPullAtomService.mTelephony =
                (TelephonyManager) statsPullAtomService.mContext.getSystemService("phone");
        statsPullAtomService.mSubscriptionManager =
                (SubscriptionManager)
                        statsPullAtomService.mContext.getSystemService(
                                "telephony_subscription_service");
        statsPullAtomService.mStatsSubscriptionsListener =
                statsPullAtomService
                .new StatsSubscriptionsListener(statsPullAtomService.mSubscriptionManager);
        statsPullAtomService.mStorageManager =
                (StorageManager)
                        statsPullAtomService.mContext.getSystemService(StorageManager.class);
        statsPullAtomService.mStoragedUidIoStatsReader = new StoragedUidIoStatsReader();
        File file = new File(SystemServiceManager.ensureSystemDir(), "stats_pull");
        statsPullAtomService.mBaseDir = file;
        file.mkdirs();
        int i = 0;
        statsPullAtomService.mCpuUidUserSysTimeReader =
                new KernelCpuUidTimeReader.KernelCpuUidUserSysTimeReader(false);
        statsPullAtomService.mCpuUidFreqTimeReader =
                new KernelCpuUidTimeReader.KernelCpuUidFreqTimeReader(false);
        statsPullAtomService.mCpuUidActiveTimeReader =
                new KernelCpuUidTimeReader.KernelCpuUidActiveTimeReader(false);
        statsPullAtomService.mCpuUidClusterTimeReader =
                new KernelCpuUidTimeReader.KernelCpuUidClusterTimeReader(false);
        statsPullAtomService.mKernelWakelockReader = new KernelWakelockReader();
        statsPullAtomService.mTmpWakelockStats = new KernelWakelockStats();
        statsPullAtomService.mKernelCpuThreadReader =
                KernelCpuThreadReaderSettingsObserver.getSettingsModifiedReader(
                        statsPullAtomService.mContext);
        try {
            statsPullAtomService.mHealthService = HealthServiceWrapper.create(null);
        } catch (RemoteException | NoSuchElementException unused) {
            Slog.e("StatsPullAtomService", "failed to initialize healthHalWrapper");
        }
        PackageManager packageManager = statsPullAtomService.mContext.getPackageManager();
        int i2 = 0;
        while (i2 < 149) {
            String opToPermission = AppOpsManager.opToPermission(i2);
            if (opToPermission != null) {
                try {
                    if (packageManager.getPermissionInfo(opToPermission, i).getProtection() == 1) {
                        statsPullAtomService.mDangerousAppOpsList.add(Integer.valueOf(i2));
                    }
                } catch (PackageManager.NameNotFoundException unused2) {
                }
            }
            i2++;
            i = 0;
        }
        statsPullAtomService.mSurfaceFlingerProcessCpuThreadReader =
                new SelectedProcessCpuThreadReader("/system/bin/surfaceflinger");
        statsPullAtomService.getIKeystoreMetricsService();
        Slog.d("StatsPullAtomService", "Registering pullers with statsd");
        statsPullAtomService.mStatsCallbackImpl =
                statsPullAtomService.new StatsPullAtomCallbackImpl();
        StatsManager.PullAtomMetadata build =
                new StatsManager.PullAtomMetadata.Builder()
                        .setAdditiveFields(new int[] {2, 3})
                        .build();
        StatsManager statsManager = statsPullAtomService.mStatsManager;
        Executor executor = ConcurrentUtils.DIRECT_EXECUTOR;
        statsManager.setPullAtomCallback(
                FrameworkStatsLog.BLUETOOTH_BYTES_TRANSFER,
                build,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                10004,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        if (KernelCpuBpfTracking.isSupported()) {
            statsPullAtomService.mStatsManager.setPullAtomCallback(
                    FrameworkStatsLog.CPU_TIME_PER_CLUSTER_FREQ,
                    new StatsManager.PullAtomMetadata.Builder()
                            .setAdditiveFields(new int[] {3})
                            .build(),
                    executor,
                    statsPullAtomService.mStatsCallbackImpl);
        }
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.CPU_TIME_PER_UID,
                new StatsManager.PullAtomMetadata.Builder()
                        .setAdditiveFields(new int[] {2, 3})
                        .build(),
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        if (KernelCpuBpfTracking.isSupported() || KernelCpuBpfTracking.getClusters() > 0) {
            statsPullAtomService.mStatsManager.setPullAtomCallback(
                    FrameworkStatsLog.CPU_CYCLES_PER_UID_CLUSTER,
                    new StatsManager.PullAtomMetadata.Builder()
                            .setAdditiveFields(new int[] {3, 4, 5})
                            .build(),
                    executor,
                    statsPullAtomService.mStatsCallbackImpl);
        }
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.CPU_TIME_PER_UID_FREQ,
                new StatsManager.PullAtomMetadata.Builder()
                        .setAdditiveFields(new int[] {3})
                        .build(),
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        if (KernelCpuBpfTracking.isSupported() && !Flags.disableSystemServicePowerAttr()) {
            statsPullAtomService.mStatsManager.setPullAtomCallback(
                    FrameworkStatsLog.CPU_CYCLES_PER_THREAD_GROUP_CLUSTER,
                    new StatsManager.PullAtomMetadata.Builder()
                            .setAdditiveFields(new int[] {3, 4})
                            .build(),
                    executor,
                    statsPullAtomService.mStatsCallbackImpl);
        }
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.CPU_ACTIVE_TIME,
                new StatsManager.PullAtomMetadata.Builder()
                        .setAdditiveFields(new int[] {2})
                        .build(),
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.CPU_CLUSTER_TIME,
                new StatsManager.PullAtomMetadata.Builder()
                        .setAdditiveFields(new int[] {3})
                        .build(),
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.WIFI_ACTIVITY_INFO,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.MODEM_ACTIVITY_INFO,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.BLUETOOTH_ACTIVITY_INFO,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.SYSTEM_ELAPSED_REALTIME,
                new StatsManager.PullAtomMetadata.Builder()
                        .setCoolDownMillis(1000L)
                        .setTimeoutMillis(500L)
                        .build(),
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.SYSTEM_UPTIME,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.PROCESS_MEMORY_STATE,
                new StatsManager.PullAtomMetadata.Builder()
                        .setAdditiveFields(new int[] {4, 5, 6, 7, 8})
                        .build(),
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.PROCESS_MEMORY_HIGH_WATER_MARK,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.PROCESS_MEMORY_SNAPSHOT,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.SYSTEM_ION_HEAP_SIZE,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        if (BatteryService$$ExternalSyntheticOutline0.m45m("/sys/kernel/ion/total_heaps_kb")) {
            statsPullAtomService.mStatsManager.setPullAtomCallback(
                    FrameworkStatsLog.ION_HEAP_SIZE,
                    (StatsManager.PullAtomMetadata) null,
                    executor,
                    statsPullAtomService.mStatsCallbackImpl);
        }
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.PROCESS_SYSTEM_ION_HEAP_SIZE,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.SYSTEM_MEMORY,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.PROCESS_DMABUF_MEMORY,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.VMSTAT,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.TEMPERATURE,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.COOLING_DEVICE,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.BINDER_CALLS,
                new StatsManager.PullAtomMetadata.Builder()
                        .setAdditiveFields(new int[] {4, 5, 6, 8, 12})
                        .build(),
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.BINDER_CALLS_EXCEPTIONS,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.LOOPER_STATS,
                new StatsManager.PullAtomMetadata.Builder()
                        .setAdditiveFields(new int[] {5, 6, 7, 8, 9})
                        .build(),
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.DISK_STATS,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.DIRECTORY_USAGE,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.APP_SIZE,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.CATEGORY_SIZE,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.NUM_FINGERPRINTS_ENROLLED,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.NUM_FACES_ENROLLED,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.PROC_STATS,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.PROC_STATS_PKG_PROC,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.PROCESS_STATE,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.PROCESS_ASSOCIATION,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.DISK_IO,
                new StatsManager.PullAtomMetadata.Builder()
                        .setAdditiveFields(new int[] {2, 3, 4, 5, 6, 7, 8, 9, 10, 11})
                        .setCoolDownMillis(3000L)
                        .build(),
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.POWER_PROFILE,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.PROCESS_CPU_TIME,
                new StatsManager.PullAtomMetadata.Builder().setCoolDownMillis(5000L).build(),
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.CPU_TIME_PER_THREAD_FREQ,
                new StatsManager.PullAtomMetadata.Builder()
                        .setAdditiveFields(new int[] {7, 9, 11, 13, 15, 17, 19, 21})
                        .build(),
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.DEVICE_CALCULATED_POWER_USE,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.DEBUG_ELAPSED_CLOCK,
                new StatsManager.PullAtomMetadata.Builder()
                        .setAdditiveFields(new int[] {1, 2, 3, 4})
                        .build(),
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.DEBUG_FAILING_ELAPSED_CLOCK,
                new StatsManager.PullAtomMetadata.Builder()
                        .setAdditiveFields(new int[] {1, 2, 3, 4})
                        .build(),
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.BUILD_INFORMATION,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.ROLE_HOLDER,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.TIME_ZONE_DATA_INFO,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.TIME_ZONE_DETECTOR_STATE,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.EXTERNAL_STORAGE_INFO,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.APPS_ON_EXTERNAL_STORAGE_INFO,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.FACE_SETTINGS,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.APP_OPS,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.ATTRIBUTED_APP_OPS,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.RUNTIME_APP_OP_ACCESS,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.NOTIFICATION_REMOTE_VIEWS,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.DANGEROUS_PERMISSION_STATE,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.DANGEROUS_PERMISSION_STATE_SAMPLED,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.BATTERY_LEVEL,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.REMAINING_BATTERY_CAPACITY,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.FULL_BATTERY_CAPACITY,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.BATTERY_VOLTAGE,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.BATTERY_CYCLE_COUNT,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.SETTING_SNAPSHOT,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.INSTALLED_INCREMENTAL_PACKAGE,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.KEYSTORE2_STORAGE_STATS,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.KEYSTORE2_KEY_CREATION_WITH_GENERAL_INFO,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.KEYSTORE2_KEY_CREATION_WITH_AUTH_INFO,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.KEYSTORE2_KEY_CREATION_WITH_PURPOSE_AND_MODES_INFO,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.KEYSTORE2_ATOM_WITH_OVERFLOW,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.KEYSTORE2_KEY_OPERATION_WITH_PURPOSE_AND_MODES_INFO,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.KEYSTORE2_KEY_OPERATION_WITH_GENERAL_INFO,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.RKP_ERROR_STATS,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.KEYSTORE2_CRASH_STATS,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.ACCESSIBILITY_SHORTCUT_STATS,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.ACCESSIBILITY_FLOATING_MENU_STATS,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.MEDIA_CAPABILITIES,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.PENDING_INTENTS_PER_PACKAGE,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.PINNED_FILE_SIZES_PER_PACKAGE,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.HDR_CAPABILITIES,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        statsPullAtomService.mStatsManager.setPullAtomCallback(
                FrameworkStatsLog.CACHED_APPS_HIGH_WATERMARK,
                (StatsManager.PullAtomMetadata) null,
                executor,
                statsPullAtomService.mStatsCallbackImpl);
        ((ConnectivityManager) statsPullAtomService.mContext.getSystemService("connectivity"))
                .registerNetworkCallback(
                        new NetworkRequest.Builder().build(), new ConnectivityStatsCallback());
        IThermalService iThermalService = statsPullAtomService.getIThermalService();
        if (iThermalService != null) {
            try {
                iThermalService.registerThermalEventListener(new ThermalEventListener());
                Slog.i("StatsPullAtomService", "register thermal listener successfully");
            } catch (RemoteException unused3) {
                Slog.i("StatsPullAtomService", "failed to register thermal listener");
            }
        }
    }

    /* renamed from: -$$Nest$mestimateAppOpsSamplingRate, reason: not valid java name */
    public static void m951$$Nest$mestimateAppOpsSamplingRate(
            StatsPullAtomService statsPullAtomService) {
        statsPullAtomService.getClass();
        int i = DeviceConfig.getInt("permissions", "app_ops_target_collection_size", 2000);
        AppOpsManager appOpsManager =
                (AppOpsManager) statsPullAtomService.mContext.getSystemService(AppOpsManager.class);
        CompletableFuture completableFuture = new CompletableFuture();
        long j = 0;
        appOpsManager.getHistoricalOps(
                new AppOpsManager.HistoricalOpsRequest.Builder(
                                Math.max(
                                        Instant.now()
                                                .minus(1L, (TemporalUnit) ChronoUnit.DAYS)
                                                .toEpochMilli(),
                                        0L),
                                Long.MAX_VALUE)
                        .setFlags(9)
                        .build(),
                AsyncTask.THREAD_POOL_EXECUTOR,
                new StatsPullAtomService$$ExternalSyntheticLambda11(1, completableFuture));
        ArrayList arrayList =
                (ArrayList)
                        processHistoricalOps(
                                (AppOpsManager.HistoricalOps)
                                        completableFuture.get(2000L, TimeUnit.MILLISECONDS),
                                FrameworkStatsLog.ATTRIBUTED_APP_OPS,
                                100);
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            AppOpEntry appOpEntry = (AppOpEntry) arrayList.get(i2);
            int length = appOpEntry.mPackageName.length() + 32;
            j += length + (appOpEntry.mAttributionTag == null ? 1 : r5.length());
        }
        int constrain = (int) MathUtils.constrain((i * 100) / j, 0L, 100L);
        synchronized (statsPullAtomService.mAttributedAppOpsLock) {
            statsPullAtomService.mAppOpsSamplingRate =
                    Math.min(statsPullAtomService.mAppOpsSamplingRate, constrain);
        }
    }

    /* renamed from: -$$Nest$mpullCachedAppsHighWatermark, reason: not valid java name */
    public static void m952$$Nest$mpullCachedAppsHighWatermark(
            StatsPullAtomService statsPullAtomService, int i, List list) {
        statsPullAtomService.getClass();
        list.add(
                (StatsEvent)
                        ((ActivityManagerInternal)
                                        LocalServices.getService(ActivityManagerInternal.class))
                                .getCachedAppsHighWatermarkStats(i, true));
    }

    /* renamed from: -$$Nest$mpullDataBytesTransferLocked, reason: not valid java name */
    public static int m953$$Nest$mpullDataBytesTransferLocked(
            StatsPullAtomService statsPullAtomService, int i, List list) {
        boolean z;
        StatsEvent buildStatsEvent;
        Iterator it =
                ((ArrayList) statsPullAtomService.collectNetworkStatsSnapshotForAtom(i)).iterator();
        while (it.hasNext()) {
            NetworkStatsExt networkStatsExt = (NetworkStatsExt) it.next();
            NetworkStatsExt networkStatsExt2 =
                    (NetworkStatsExt)
                            CollectionUtils.find(
                                    statsPullAtomService.mNetworkStatsBaselines,
                                    new StatsPullAtomService$$ExternalSyntheticLambda15(
                                            0, networkStatsExt));
            boolean z2 = true;
            if (networkStatsExt2 == null) {
                FileDescriptorWatcher$FileDescriptorLeakWatcher$$ExternalSyntheticOutline0.m(
                        i, "baseline is null for ", ", return.", "StatsPullAtomService");
                return 1;
            }
            NetworkStats subtract = networkStatsExt.stats.subtract(networkStatsExt2.stats);
            NetworkStats networkStats = new NetworkStats(0L, 1);
            Iterator it2 = subtract.iterator();
            while (it2.hasNext()) {
                NetworkStats.Entry entry = (NetworkStats.Entry) it2.next();
                if (entry.getRxBytes() != 0
                        || entry.getRxPackets() != 0
                        || entry.getTxBytes() != 0
                        || entry.getTxPackets() != 0
                        || entry.getOperations() != 0) {
                    networkStats = networkStats.addEntry(entry);
                }
            }
            int[] iArr = networkStatsExt.transports;
            int[] copyOf = Arrays.copyOf(iArr, iArr.length);
            Arrays.sort(copyOf);
            if (networkStats.iterator().hasNext()) {
                int i2 = networkStatsExt.ratType;
                if (i == 10082) {
                    boolean z3 = true;
                    boolean z4 = i2 == -2;
                    if (!z4 && i2 != 20) {
                        z3 = false;
                    }
                    Iterator it3 = networkStats.iterator();
                    while (it3.hasNext()) {
                        NetworkStats.Entry entry2 = (NetworkStats.Entry) it3.next();
                        int set = entry2.getSet();
                        long rxBytes = entry2.getRxBytes();
                        long rxPackets = entry2.getRxPackets();
                        long txBytes = entry2.getTxBytes();
                        long txPackets = entry2.getTxPackets();
                        int i3 = z4 ? 13 : i2;
                        SubInfo subInfo = networkStatsExt.subInfo;
                        list.add(
                                FrameworkStatsLog.buildStatsEvent(
                                        FrameworkStatsLog.DATA_USAGE_BYTES_TRANSFER,
                                        set,
                                        rxBytes,
                                        rxPackets,
                                        txBytes,
                                        txPackets,
                                        i3,
                                        subInfo.mcc,
                                        subInfo.mnc,
                                        subInfo.carrierId,
                                        subInfo.isOpportunistic ? 2 : 3,
                                        z3));
                    }
                } else if (i == 10083) {
                    boolean z5 = i2 == -2;
                    Iterator it4 = networkStats.iterator();
                    while (it4.hasNext()) {
                        NetworkStats.Entry entry3 = (NetworkStats.Entry) it4.next();
                        list.add(
                                FrameworkStatsLog.buildStatsEvent(
                                        FrameworkStatsLog.BYTES_TRANSFER_BY_TAG_AND_METERED,
                                        entry3.getUid(),
                                        entry3.getMetered() == 1,
                                        entry3.getTag(),
                                        entry3.getRxBytes(),
                                        entry3.getRxPackets(),
                                        entry3.getTxBytes(),
                                        entry3.getTxPackets(),
                                        z5 ? 13 : i2));
                    }
                } else if (i != 10100) {
                    Iterator it5 = networkStats.iterator();
                    while (it5.hasNext()) {
                        NetworkStats.Entry entry4 = (NetworkStats.Entry) it5.next();
                        if (networkStatsExt.slicedByFgbg) {
                            z = z2;
                            buildStatsEvent =
                                    FrameworkStatsLog.buildStatsEvent(
                                            i,
                                            entry4.getUid(),
                                            entry4.getSet() > 0 ? z2 : false,
                                            entry4.getRxBytes(),
                                            entry4.getRxPackets(),
                                            entry4.getTxBytes(),
                                            entry4.getTxPackets());
                        } else {
                            z = z2;
                            buildStatsEvent =
                                    FrameworkStatsLog.buildStatsEvent(
                                            i,
                                            entry4.getUid(),
                                            entry4.getRxBytes(),
                                            entry4.getRxPackets(),
                                            entry4.getTxBytes(),
                                            entry4.getTxPackets());
                        }
                        list.add(buildStatsEvent);
                        z2 = z;
                    }
                } else {
                    for (int i4 : copyOf) {
                        Iterator it6 = networkStats.iterator();
                        while (it6.hasNext()) {
                            NetworkStats.Entry entry5 = (NetworkStats.Entry) it6.next();
                            list.add(
                                    FrameworkStatsLog.buildStatsEvent(
                                            FrameworkStatsLog.OEM_MANAGED_BYTES_TRANSFER,
                                            entry5.getUid(),
                                            entry5.getSet() > 0,
                                            networkStatsExt.oemManaged,
                                            i4,
                                            entry5.getRxBytes(),
                                            entry5.getRxPackets(),
                                            entry5.getTxBytes(),
                                            entry5.getTxPackets()));
                        }
                    }
                }
            }
        }
        return 0;
    }

    /* renamed from: -$$Nest$mpullHdrCapabilities, reason: not valid java name */
    public static void m954$$Nest$mpullHdrCapabilities(
            StatsPullAtomService statsPullAtomService, int i, List list) {
        DisplayManager displayManager =
                (DisplayManager)
                        statsPullAtomService.mContext.getSystemService(DisplayManager.class);
        Display display = displayManager.getDisplay(0);
        int conversionMode = displayManager.getHdrConversionMode().getConversionMode();
        int preferredHdrOutputType =
                displayManager.getHdrConversionMode().getPreferredHdrOutputType();
        boolean z = conversionMode == 1;
        int i2 = preferredHdrOutputType == -1 ? 0 : preferredHdrOutputType;
        AtomicInteger atomicInteger = new AtomicInteger();
        Arrays.stream(display.getSupportedModes())
                .map(new StatsPullAtomService$$ExternalSyntheticLambda2(4))
                .filter(new StatsPullAtomService$$ExternalSyntheticLambda10())
                .forEach(new StatsPullAtomService$$ExternalSyntheticLambda11(0, atomicInteger));
        list.add(
                FrameworkStatsLog.buildStatsEvent(
                        i,
                        toBytes(displayManager.getSupportedHdrOutputTypes()),
                        z,
                        i2,
                        atomicInteger.get() != 0
                                && atomicInteger.get() < display.getSupportedModes().length,
                        conversionMode != 0));
    }

    /* renamed from: -$$Nest$mpullNumBiometricsEnrolledLocked, reason: not valid java name */
    public static int m955$$Nest$mpullNumBiometricsEnrolledLocked(
            StatsPullAtomService statsPullAtomService, int i, int i2, List list) {
        UserManager userManager;
        int size;
        PackageManager packageManager = statsPullAtomService.mContext.getPackageManager();
        FingerprintManager fingerprintManager =
                packageManager.hasSystemFeature("android.hardware.fingerprint")
                        ? (FingerprintManager)
                                statsPullAtomService.mContext.getSystemService(
                                        FingerprintManager.class)
                        : null;
        FaceManager faceManager =
                packageManager.hasSystemFeature("android.hardware.biometrics.face")
                        ? (FaceManager)
                                statsPullAtomService.mContext.getSystemService(FaceManager.class)
                        : null;
        if (i == 1 && fingerprintManager == null) {
            return 1;
        }
        if ((i == 4 && faceManager == null)
                || (userManager =
                                (UserManager)
                                        statsPullAtomService.mContext.getSystemService(
                                                UserManager.class))
                        == null) {
            return 1;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Iterator it = userManager.getUsers().iterator();
            while (it.hasNext()) {
                int identifier = ((UserInfo) it.next()).getUserHandle().getIdentifier();
                if (i == 1) {
                    size = fingerprintManager.getEnrolledFingerprints(identifier).size();
                } else {
                    if (i != 4) {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return 1;
                    }
                    size = faceManager.getEnrolledFaces(identifier).size();
                }
                list.add(FrameworkStatsLog.buildStatsEvent(i2, identifier, size));
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return 0;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    /* renamed from: -$$Nest$mpullPendingIntentsPerPackage, reason: not valid java name */
    public static void m956$$Nest$mpullPendingIntentsPerPackage(
            StatsPullAtomService statsPullAtomService, int i, List list) {
        statsPullAtomService.getClass();
        for (PendingIntentStats pendingIntentStats :
                ((ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class))
                        .getPendingIntentStats()) {
            list.add(
                    FrameworkStatsLog.buildStatsEvent(
                            i,
                            pendingIntentStats.uid,
                            pendingIntentStats.count,
                            pendingIntentStats.sizeKb));
        }
    }

    /* renamed from: -$$Nest$mpullProcStatsLocked, reason: not valid java name */
    public static int m957$$Nest$mpullProcStatsLocked(
            StatsPullAtomService statsPullAtomService, int i, List list) {
        ProcessStats statsFromProcessStatsService =
                statsPullAtomService.getStatsFromProcessStatsService(i);
        if (statsFromProcessStatsService == null) {
            return 1;
        }
        ProtoOutputStream[] protoOutputStreamArr = new ProtoOutputStream[5];
        for (int i2 = 0; i2 < 5; i2++) {
            protoOutputStreamArr[i2] = new ProtoOutputStream();
        }
        statsFromProcessStatsService.dumpAggregatedProtoForStatsd(protoOutputStreamArr, 58982L);
        for (int i3 = 0; i3 < 5; i3++) {
            byte[] bytes = protoOutputStreamArr[i3].getBytes();
            if (bytes.length > 0) {
                list.add(FrameworkStatsLog.buildStatsEvent(i, bytes, i3));
            }
        }
        return 0;
    }

    /* renamed from: -$$Nest$mpullProcessAssociationLocked, reason: not valid java name */
    public static int m958$$Nest$mpullProcessAssociationLocked(
            StatsPullAtomService statsPullAtomService, int i, List list) {
        ProcessStats statsFromProcessStatsService =
                statsPullAtomService.getStatsFromProcessStatsService(i);
        if (statsFromProcessStatsService == null) {
            return 1;
        }
        statsFromProcessStatsService.dumpProcessAssociation(i, new StatsEventOutput(list));
        return 0;
    }

    /* renamed from: -$$Nest$mpullProcessStateLocked, reason: not valid java name */
    public static int m959$$Nest$mpullProcessStateLocked(
            StatsPullAtomService statsPullAtomService, int i, List list) {
        ProcessStats statsFromProcessStatsService =
                statsPullAtomService.getStatsFromProcessStatsService(i);
        if (statsFromProcessStatsService == null) {
            return 1;
        }
        statsFromProcessStatsService.dumpProcessState(i, new StatsEventOutput(list));
        return 0;
    }

    static {
        com.android.server.stats.Flags.addMobileBytesTransferByProcStatePuller();
        ENABLE_MOBILE_DATA_STATS_AGGREGATED_PULLER = true;
    }

    public StatsPullAtomService(Context context) {
        super(context);
        this.mThermalLock = new Object();
        this.mStoragedLock = new Object();
        this.mNotificationStatsLock = new Object();
        this.mDebugElapsedClockPreviousValue = 0L;
        this.mDebugElapsedClockPullCount = 0L;
        this.mDebugFailingElapsedClockPreviousValue = 0L;
        this.mDebugFailingElapsedClockPullCount = 0L;
        this.mNetworkStatsManager = null;
        this.mAppOpsSamplingRate = 0;
        this.mDangerousAppOpsList = new ArraySet();
        this.mNetworkStatsBaselines = new ArrayList();
        this.mHistoricalSubs = new ArrayList();
        this.mAggregatedMobileDataStatsPuller = null;
        this.mDataBytesTransferLock = new Object();
        this.mBluetoothBytesTransferLock = new Object();
        this.mKernelWakelockLock = new Object();
        this.mCpuTimePerClusterFreqLock = new Object();
        this.mCpuTimePerUidLock = new Object();
        this.mCpuTimePerUidFreqLock = new Object();
        this.mCpuActiveTimeLock = new Object();
        this.mCpuClusterTimeLock = new Object();
        this.mWifiActivityInfoLock = new Object();
        this.mModemActivityInfoLock = new Object();
        this.mBluetoothActivityInfoLock = new Object();
        this.mUwbActivityInfoLock = new Object();
        this.mSystemElapsedRealtimeLock = new Object();
        this.mSystemUptimeLock = new Object();
        this.mProcessMemoryStateLock = new Object();
        this.mProcessMemoryHighWaterMarkLock = new Object();
        this.mSystemIonHeapSizeLock = new Object();
        this.mIonHeapSizeLock = new Object();
        this.mProcessSystemIonHeapSizeLock = new Object();
        this.mTemperatureLock = new Object();
        this.mCooldownDeviceLock = new Object();
        this.mBinderCallsStatsLock = new Object();
        this.mBinderCallsStatsExceptionsLock = new Object();
        this.mLooperStatsLock = new Object();
        this.mDiskStatsLock = new Object();
        this.mDirectoryUsageLock = new Object();
        this.mAppSizeLock = new Object();
        this.mCategorySizeLock = new Object();
        this.mNumBiometricsEnrolledLock = new Object();
        this.mProcStatsLock = new Object();
        this.mDiskIoLock = new Object();
        this.mPowerProfileLock = new Object();
        this.mProcessCpuTimeLock = new Object();
        this.mCpuTimePerThreadFreqLock = new Object();
        this.mDeviceCalculatedPowerUseLock = new Object();
        this.mDebugElapsedClockLock = new Object();
        this.mDebugFailingElapsedClockLock = new Object();
        this.mBuildInformationLock = new Object();
        this.mRoleHolderLock = new Object();
        this.mTimeZoneDataInfoLock = new Object();
        this.mTimeZoneDetectionInfoLock = new Object();
        this.mExternalStorageInfoLock = new Object();
        this.mAppsOnExternalStorageInfoLock = new Object();
        this.mFaceSettingsLock = new Object();
        this.mAppOpsLock = new Object();
        this.mRuntimeAppOpAccessMessageLock = new Object();
        this.mNotificationRemoteViewsLock = new Object();
        this.mDangerousPermissionStateLock = new Object();
        this.mHealthHalLock = new Object();
        this.mAttributedAppOpsLock = new Object();
        this.mSettingsStatsLock = new Object();
        this.mInstalledIncrementalPackagesLock = new Object();
        this.mKeystoreLock = new Object();
        this.mContext = context;
    }

    public static void addCpuCyclesPerThreadGroupClusterAtoms(
            int i, List list, int i2, long[] jArr) {
        int[] freqsClusters = KernelCpuBpfTracking.getFreqsClusters();
        int clusters = KernelCpuBpfTracking.getClusters();
        long[] freqs = KernelCpuBpfTracking.getFreqs();
        long[] jArr2 = new long[clusters];
        long[] jArr3 = new long[clusters];
        for (int i3 = 0; i3 < jArr.length; i3++) {
            int i4 = freqsClusters[i3];
            jArr2[i4] = ((freqs[i3] * jArr[i3]) / 1000) + jArr2[i4];
            jArr3[i4] = jArr3[i4] + jArr[i3];
        }
        for (int i5 = 0; i5 < clusters; i5++) {
            list.add(
                    FrameworkStatsLog.buildStatsEvent(
                            i, i2, i5, jArr2[i5] / 1000000, jArr3[i5] / 1000));
        }
    }

    public static Parcelable awaitControllerInfo(
            SynchronousResultReceiver synchronousResultReceiver) {
        try {
            SynchronousResultReceiver.Result awaitResult =
                    synchronousResultReceiver.awaitResult(2000L);
            Bundle bundle = awaitResult.bundle;
            if (bundle == null) {
                return null;
            }
            bundle.setDefusable(true);
            Parcelable parcelable = awaitResult.bundle.getParcelable("controller_activity");
            if (parcelable != null) {
                return parcelable;
            }
            return null;
        } catch (TimeoutException unused) {
            Slog.w(
                    "StatsPullAtomService",
                    "timeout reading " + synchronousResultReceiver.getName() + " stats");
            return null;
        }
    }

    public static byte[] convertTimeZoneSuggestionToProtoBytes(
            MetricsTimeZoneDetectorState.MetricsTimeZoneSuggestion metricsTimeZoneSuggestion) {
        if (metricsTimeZoneSuggestion == null) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ProtoOutputStream protoOutputStream = new ProtoOutputStream(byteArrayOutputStream);
        int[] iArr = metricsTimeZoneSuggestion.mZoneIdOrdinals;
        protoOutputStream.write(1159641169921L, iArr != null ? 1 : 2);
        if (iArr != null) {
            for (int i : iArr) {
                protoOutputStream.write(2220498092034L, i);
            }
            String[] strArr = metricsTimeZoneSuggestion.mZoneIds;
            if (strArr != null) {
                for (String str : strArr) {
                    protoOutputStream.write(2237677961219L, str);
                }
            }
        }
        protoOutputStream.flush();
        IoUtils.closeQuietly(byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static int countAccessibilityServices(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        int count =
                (int)
                        str.chars()
                                .filter(new StatsPullAtomService$$ExternalSyntheticLambda20(0))
                                .count();
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        return count + 1;
    }

    public static BluetoothActivityEnergyInfo fetchBluetoothData() {
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter == null) {
            Slog.e("StatsPullAtomService", "Failed to get bluetooth adapter!");
            return null;
        }
        final SynchronousResultReceiver synchronousResultReceiver =
                new SynchronousResultReceiver("bluetooth");
        defaultAdapter.requestControllerActivityEnergyInfo(
                new SystemServerInitThreadPool$$ExternalSyntheticLambda0(),
                new BluetoothAdapter
                        .OnBluetoothActivityEnergyInfoCallback() { // from class:
                                                                   // com.android.server.stats.pull.StatsPullAtomService.1
                    public final void onBluetoothActivityEnergyInfoAvailable(
                            BluetoothActivityEnergyInfo bluetoothActivityEnergyInfo) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("controller_activity", bluetoothActivityEnergyInfo);
                        synchronousResultReceiver.send(0, bundle);
                    }

                    public final void onBluetoothActivityEnergyInfoError(int i) {
                        Slog.w("StatsPullAtomService", "error reading Bluetooth stats: " + i);
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("controller_activity", null);
                        synchronousResultReceiver.send(0, bundle);
                    }
                });
        return awaitControllerInfo(synchronousResultReceiver);
    }

    public static String highWaterMarkFilePrefix(int i) {
        return i == 10029
                ? String.valueOf(31)
                : i == 10034
                        ? String.valueOf(2)
                        : VibrationParam$1$$ExternalSyntheticOutline0.m(i, "atom-");
    }

    private native void initializeNativePullers();

    public static boolean isAccessibilityShortcutUser(Context context, int i) {
        ContentResolver contentResolver = context.getContentResolver();
        String stringForUser =
                Settings.Secure.getStringForUser(
                        contentResolver, "accessibility_button_targets", i);
        String stringForUser2 =
                Settings.Secure.getStringForUser(
                        contentResolver, "accessibility_shortcut_target_service", i);
        String stringForUser3 =
                Settings.Secure.getStringForUser(contentResolver, "accessibility_qs_targets", i);
        boolean z =
                Settings.Secure.getIntForUser(
                                contentResolver, "accessibility_shortcut_dialog_shown", 0, i)
                        == 1;
        return (TextUtils.isEmpty(stringForUser) ^ true)
                || (z && !TextUtils.isEmpty(stringForUser2))
                || (Settings.Secure.getIntForUser(
                                contentResolver,
                                "accessibility_display_magnification_enabled",
                                0,
                                i)
                        == 1)
                || (TextUtils.isEmpty(stringForUser3) ^ true);
    }

    public static int parseKeystoreAtomWithOverflow(KeystoreAtom[] keystoreAtomArr, List list) {
        for (KeystoreAtom keystoreAtom : keystoreAtomArr) {
            if (keystoreAtom.payload.getTag() != 4) {
                return 1;
            }
            list.add(
                    FrameworkStatsLog.buildStatsEvent(
                            FrameworkStatsLog.KEYSTORE2_ATOM_WITH_OVERFLOW,
                            keystoreAtom.payload.getKeystore2AtomWithOverflow().atom_id,
                            keystoreAtom.count));
        }
        return 0;
    }

    public static int parseKeystoreCrashStats(KeystoreAtom[] keystoreAtomArr, List list) {
        for (KeystoreAtom keystoreAtom : keystoreAtomArr) {
            if (keystoreAtom.payload.getTag() != 8) {
                return 1;
            }
            list.add(
                    FrameworkStatsLog.buildStatsEvent(
                            FrameworkStatsLog.KEYSTORE2_CRASH_STATS,
                            keystoreAtom.payload.getCrashStats().count_of_crash_events));
        }
        return 0;
    }

    public static int parseKeystoreKeyCreationWithAuthInfo(
            KeystoreAtom[] keystoreAtomArr, List list) {
        for (KeystoreAtom keystoreAtom : keystoreAtomArr) {
            if (keystoreAtom.payload.getTag() != 2) {
                return 1;
            }
            KeyCreationWithAuthInfo keyCreationWithAuthInfo =
                    keystoreAtom.payload.getKeyCreationWithAuthInfo();
            list.add(
                    FrameworkStatsLog.buildStatsEvent(
                            FrameworkStatsLog.KEYSTORE2_KEY_CREATION_WITH_AUTH_INFO,
                            keyCreationWithAuthInfo.user_auth_type,
                            keyCreationWithAuthInfo.log10_auth_key_timeout_seconds,
                            keyCreationWithAuthInfo.security_level,
                            keystoreAtom.count));
        }
        return 0;
    }

    public static int parseKeystoreKeyCreationWithGeneralInfo(
            KeystoreAtom[] keystoreAtomArr, List list) {
        for (KeystoreAtom keystoreAtom : keystoreAtomArr) {
            if (keystoreAtom.payload.getTag() != 1) {
                return 1;
            }
            KeyCreationWithGeneralInfo keyCreationWithGeneralInfo =
                    keystoreAtom.payload.getKeyCreationWithGeneralInfo();
            list.add(
                    FrameworkStatsLog.buildStatsEvent(
                            FrameworkStatsLog.KEYSTORE2_KEY_CREATION_WITH_GENERAL_INFO,
                            keyCreationWithGeneralInfo.algorithm,
                            keyCreationWithGeneralInfo.key_size,
                            keyCreationWithGeneralInfo.ec_curve,
                            keyCreationWithGeneralInfo.key_origin,
                            keyCreationWithGeneralInfo.error_code,
                            keyCreationWithGeneralInfo.attestation_requested,
                            keystoreAtom.count));
        }
        return 0;
    }

    public static int parseKeystoreKeyCreationWithPurposeModesInfo(
            KeystoreAtom[] keystoreAtomArr, List list) {
        for (KeystoreAtom keystoreAtom : keystoreAtomArr) {
            if (keystoreAtom.payload.getTag() != 3) {
                return 1;
            }
            KeyCreationWithPurposeAndModesInfo keyCreationWithPurposeAndModesInfo =
                    keystoreAtom.payload.getKeyCreationWithPurposeAndModesInfo();
            list.add(
                    FrameworkStatsLog.buildStatsEvent(
                            FrameworkStatsLog.KEYSTORE2_KEY_CREATION_WITH_PURPOSE_AND_MODES_INFO,
                            keyCreationWithPurposeAndModesInfo.algorithm,
                            keyCreationWithPurposeAndModesInfo.purpose_bitmap,
                            keyCreationWithPurposeAndModesInfo.padding_mode_bitmap,
                            keyCreationWithPurposeAndModesInfo.digest_bitmap,
                            keyCreationWithPurposeAndModesInfo.block_mode_bitmap,
                            keystoreAtom.count));
        }
        return 0;
    }

    public static int parseKeystoreKeyOperationWithGeneralInfo(
            KeystoreAtom[] keystoreAtomArr, List list) {
        for (KeystoreAtom keystoreAtom : keystoreAtomArr) {
            if (keystoreAtom.payload.getTag() != 6) {
                return 1;
            }
            KeyOperationWithGeneralInfo keyOperationWithGeneralInfo =
                    keystoreAtom.payload.getKeyOperationWithGeneralInfo();
            list.add(
                    FrameworkStatsLog.buildStatsEvent(
                            FrameworkStatsLog.KEYSTORE2_KEY_OPERATION_WITH_GENERAL_INFO,
                            keyOperationWithGeneralInfo.outcome,
                            keyOperationWithGeneralInfo.error_code,
                            keyOperationWithGeneralInfo.key_upgraded,
                            keyOperationWithGeneralInfo.security_level,
                            keystoreAtom.count));
        }
        return 0;
    }

    public static int parseKeystoreKeyOperationWithPurposeModesInfo(
            KeystoreAtom[] keystoreAtomArr, List list) {
        for (KeystoreAtom keystoreAtom : keystoreAtomArr) {
            if (keystoreAtom.payload.getTag() != 5) {
                return 1;
            }
            KeyOperationWithPurposeAndModesInfo keyOperationWithPurposeAndModesInfo =
                    keystoreAtom.payload.getKeyOperationWithPurposeAndModesInfo();
            list.add(
                    FrameworkStatsLog.buildStatsEvent(
                            FrameworkStatsLog.KEYSTORE2_KEY_OPERATION_WITH_PURPOSE_AND_MODES_INFO,
                            keyOperationWithPurposeAndModesInfo.purpose,
                            keyOperationWithPurposeAndModesInfo.padding_mode_bitmap,
                            keyOperationWithPurposeAndModesInfo.digest_bitmap,
                            keyOperationWithPurposeAndModesInfo.block_mode_bitmap,
                            keystoreAtom.count));
        }
        return 0;
    }

    public static int parseKeystoreStorageStats(KeystoreAtom[] keystoreAtomArr, List list) {
        for (KeystoreAtom keystoreAtom : keystoreAtomArr) {
            if (keystoreAtom.payload.getTag() != 0) {
                return 1;
            }
            StorageStats storageStats = keystoreAtom.payload.getStorageStats();
            list.add(
                    FrameworkStatsLog.buildStatsEvent(
                            FrameworkStatsLog.KEYSTORE2_STORAGE_STATS,
                            storageStats.storage_type,
                            storageStats.size,
                            storageStats.unused_size));
        }
        return 0;
    }

    public static int parseRkpErrorStats(KeystoreAtom[] keystoreAtomArr, List list) {
        for (KeystoreAtom keystoreAtom : keystoreAtomArr) {
            if (keystoreAtom.payload.getTag() != 7) {
                return 1;
            }
            RkpErrorStats rkpErrorStats = keystoreAtom.payload.getRkpErrorStats();
            list.add(
                    FrameworkStatsLog.buildStatsEvent(
                            FrameworkStatsLog.RKP_ERROR_STATS,
                            rkpErrorStats.rkpError,
                            keystoreAtom.count,
                            rkpErrorStats.security_level));
        }
        return 0;
    }

    public static void processHistoricalOp(
            AppOpsManager.HistoricalOp historicalOp,
            List list,
            int i,
            int i2,
            String str,
            String str2) {
        int i3;
        if (str2 == null || !str2.startsWith(str)) {
            i3 = 0;
        } else {
            i3 = str.length();
            if (i3 < str2.length() && str2.charAt(i3) == '.') {
                i3++;
            }
        }
        AppOpEntry appOpEntry =
                new AppOpEntry(str, str2 == null ? null : str2.substring(i3), historicalOp, i);
        if (appOpEntry.mHash < i2) {
            ((ArrayList) list).add(appOpEntry);
        }
    }

    public static List processHistoricalOps(
            AppOpsManager.HistoricalOps historicalOps, int i, int i2) {
        ArrayList arrayList = new ArrayList();
        for (int i3 = 0; i3 < historicalOps.getUidCount(); i3++) {
            AppOpsManager.HistoricalUidOps uidOpsAt = historicalOps.getUidOpsAt(i3);
            int uid = uidOpsAt.getUid();
            for (int i4 = 0; i4 < uidOpsAt.getPackageCount(); i4++) {
                AppOpsManager.HistoricalPackageOps packageOpsAt = uidOpsAt.getPackageOpsAt(i4);
                if (i == 10075) {
                    for (int i5 = 0; i5 < packageOpsAt.getAttributedOpsCount(); i5++) {
                        int i6 = 0;
                        for (AppOpsManager.AttributedHistoricalOps attributedOpsAt =
                                        packageOpsAt.getAttributedOpsAt(i5);
                                i6 < attributedOpsAt.getOpCount();
                                attributedOpsAt = attributedOpsAt) {
                            processHistoricalOp(
                                    attributedOpsAt.getOpAt(i6),
                                    arrayList,
                                    uid,
                                    i2,
                                    packageOpsAt.getPackageName(),
                                    attributedOpsAt.getTag());
                            i6++;
                        }
                    }
                } else if (i == 10060) {
                    for (int i7 = 0; i7 < packageOpsAt.getOpCount(); i7++) {
                        processHistoricalOp(
                                packageOpsAt.getOpAt(i7),
                                arrayList,
                                uid,
                                i2,
                                packageOpsAt.getPackageName(),
                                null);
                    }
                }
            }
        }
        return arrayList;
    }

    public static int pullAppSizeLocked(int i, List list) {
        try {
            JSONObject jSONObject =
                    new JSONObject(IoUtils.readFileAsString("/data/system/diskstats_cache.json"));
            long optLong = jSONObject.optLong("queryTime", -1L);
            JSONArray jSONArray = jSONObject.getJSONArray("packageNames");
            JSONArray jSONArray2 = jSONObject.getJSONArray("appSizes");
            JSONArray jSONArray3 = jSONObject.getJSONArray("appDataSizes");
            JSONArray jSONArray4 = jSONObject.getJSONArray("cacheSizes");
            int length = jSONArray.length();
            if (jSONArray2.length() == length
                    && jSONArray3.length() == length
                    && jSONArray4.length() == length) {
                int i2 = 0;
                while (i2 < length) {
                    list.add(
                            FrameworkStatsLog.buildStatsEvent(
                                    i,
                                    jSONArray.getString(i2),
                                    jSONArray2.optLong(i2, -1L),
                                    jSONArray3.optLong(i2, -1L),
                                    jSONArray4.optLong(i2, -1L),
                                    optLong));
                    i2++;
                    jSONArray2 = jSONArray2;
                    jSONArray3 = jSONArray3;
                    length = length;
                }
                return 0;
            }
            Slog.e("StatsPullAtomService", "formatting error in diskstats cache file!");
            return 1;
        } catch (IOException | JSONException unused) {
            Slog.w(
                    "StatsPullAtomService",
                    "Unable to read diskstats cache file within pullAppSize");
            return 1;
        }
    }

    public static int pullBinderCallsStatsExceptionsLocked(int i, List list) {
        BinderCallsStatsService.Internal internal =
                (BinderCallsStatsService.Internal)
                        LocalServices.getService(BinderCallsStatsService.Internal.class);
        if (internal == null) {
            Slog.e("StatsPullAtomService", "failed to get binderStats");
            return 1;
        }
        Iterator it = internal.mBinderCallsStats.getExportedExceptionStats().entrySet().iterator();
        while (it.hasNext()) {
            list.add(
                    FrameworkStatsLog.buildStatsEvent(
                            i,
                            (String) ((Map.Entry) it.next()).getKey(),
                            ((Integer) r1.getValue()).intValue()));
        }
        return 0;
    }

    public static int pullBinderCallsStatsLocked(int i, List list) {
        BinderCallsStatsService.Internal internal =
                (BinderCallsStatsService.Internal)
                        LocalServices.getService(BinderCallsStatsService.Internal.class);
        if (internal == null) {
            Slog.e("StatsPullAtomService", "failed to get binderStats");
            return 1;
        }
        ArrayList<BinderCallsStats.ExportedCallStat> exportedCallStats =
                internal.mBinderCallsStats.getExportedCallStats();
        int i2 = 0;
        internal.mBinderCallsStats.reset(new boolean[0]);
        for (BinderCallsStats.ExportedCallStat exportedCallStat : exportedCallStats) {
            list.add(
                    FrameworkStatsLog.buildStatsEvent(
                            i,
                            exportedCallStat.workSourceUid,
                            exportedCallStat.className,
                            exportedCallStat.methodName,
                            exportedCallStat.callCount,
                            exportedCallStat.exceptionCount,
                            exportedCallStat.latencyMicros,
                            exportedCallStat.maxLatencyMicros,
                            exportedCallStat.cpuTimeMicros,
                            exportedCallStat.maxCpuTimeMicros,
                            exportedCallStat.maxReplySizeBytes,
                            exportedCallStat.maxRequestSizeBytes,
                            exportedCallStat.recordedCallCount,
                            exportedCallStat.screenInteractive,
                            exportedCallStat.callingUid));
            i2 = 0;
        }
        return i2;
    }

    public static int pullBluetoothActivityInfoLocked(int i, List list) {
        BluetoothActivityEnergyInfo fetchBluetoothData = fetchBluetoothData();
        if (fetchBluetoothData == null) {
            return 1;
        }
        list.add(
                FrameworkStatsLog.buildStatsEvent(
                        i,
                        fetchBluetoothData.getTimestampMillis(),
                        fetchBluetoothData.getBluetoothStackState(),
                        fetchBluetoothData.getControllerTxTimeMillis(),
                        fetchBluetoothData.getControllerRxTimeMillis(),
                        fetchBluetoothData.getControllerIdleTimeMillis(),
                        fetchBluetoothData.getControllerEnergyUsed()));
        return 0;
    }

    public static int pullBluetoothBytesTransferLocked(int i, List list) {
        BluetoothActivityEnergyInfo fetchBluetoothData = fetchBluetoothData();
        if (fetchBluetoothData == null) {
            return 1;
        }
        for (UidTraffic uidTraffic : fetchBluetoothData.getUidTraffic()) {
            list.add(
                    FrameworkStatsLog.buildStatsEvent(
                            i,
                            uidTraffic.getUid(),
                            uidTraffic.getRxBytes(),
                            uidTraffic.getTxBytes()));
        }
        return 0;
    }

    public static void pullBuildInformationLocked(int i, List list) {
        list.add(
                FrameworkStatsLog.buildStatsEvent(
                        i,
                        Build.FINGERPRINT,
                        Build.BRAND,
                        Build.PRODUCT,
                        Build.DEVICE,
                        Build.VERSION.RELEASE_OR_CODENAME,
                        Build.ID,
                        Build.VERSION.INCREMENTAL,
                        Build.TYPE,
                        Build.TAGS));
    }

    public static int pullCategorySizeLocked(int i, List list) {
        try {
            JSONObject jSONObject =
                    new JSONObject(IoUtils.readFileAsString("/data/system/diskstats_cache.json"));
            long optLong = jSONObject.optLong("queryTime", -1L);
            list.add(
                    FrameworkStatsLog.buildStatsEvent(
                            i, 1, jSONObject.optLong("appSize", -1L), optLong));
            list.add(
                    FrameworkStatsLog.buildStatsEvent(
                            i, 2, jSONObject.optLong("appDataSize", -1L), optLong));
            list.add(
                    FrameworkStatsLog.buildStatsEvent(
                            i, 3, jSONObject.optLong("cacheSize", -1L), optLong));
            list.add(
                    FrameworkStatsLog.buildStatsEvent(
                            i, 4, jSONObject.optLong("photosSize", -1L), optLong));
            list.add(
                    FrameworkStatsLog.buildStatsEvent(
                            i, 5, jSONObject.optLong("videosSize", -1L), optLong));
            list.add(
                    FrameworkStatsLog.buildStatsEvent(
                            i, 6, jSONObject.optLong("audioSize", -1L), optLong));
            list.add(
                    FrameworkStatsLog.buildStatsEvent(
                            i, 7, jSONObject.optLong("downloadsSize", -1L), optLong));
            list.add(
                    FrameworkStatsLog.buildStatsEvent(
                            i, 8, jSONObject.optLong("systemSize", -1L), optLong));
            list.add(
                    FrameworkStatsLog.buildStatsEvent(
                            i, 9, jSONObject.optLong("otherSize", -1L), optLong));
            return 0;
        } catch (IOException | JSONException unused) {
            Slog.w(
                    "StatsPullAtomService",
                    "Unable to read diskstats cache file within pullCategorySize");
            return 1;
        }
    }

    public static int pullCpuTimePerClusterFreqLocked(int i, List list) {
        int[] freqsClusters = KernelCpuBpfTracking.getFreqsClusters();
        long[] freqs = KernelCpuBpfTracking.getFreqs();
        long[] read = KernelCpuTotalBpfMapReader.read();
        if (read == null) {
            return 1;
        }
        for (int i2 = 0; i2 < read.length; i2++) {
            list.add(
                    FrameworkStatsLog.buildStatsEvent(
                            i, freqsClusters[i2], (int) freqs[i2], read[i2]));
        }
        return 0;
    }

    public static void pullDirectoryUsageLocked(int i, List list) {
        StatFs statFs = new StatFs(Environment.getDataDirectory().getAbsolutePath());
        StatFs statFs2 = new StatFs(Environment.getRootDirectory().getAbsolutePath());
        StatFs statFs3 = new StatFs(Environment.getDownloadCacheDirectory().getAbsolutePath());
        StatFs statFs4 = new StatFs(Environment.getMetadataDirectory().getAbsolutePath());
        list.add(
                FrameworkStatsLog.buildStatsEvent(
                        i, 1, statFs.getAvailableBytes(), statFs.getTotalBytes()));
        list.add(
                FrameworkStatsLog.buildStatsEvent(
                        i, 2, statFs3.getAvailableBytes(), statFs3.getTotalBytes()));
        list.add(
                FrameworkStatsLog.buildStatsEvent(
                        i, 3, statFs2.getAvailableBytes(), statFs2.getTotalBytes()));
        list.add(
                FrameworkStatsLog.buildStatsEvent(
                        i, 4, statFs4.getAvailableBytes(), statFs4.getTotalBytes()));
    }

    public static void pullIonHeapSizeLocked(int i, List list) {
        list.add(FrameworkStatsLog.buildStatsEvent(i, (int) Debug.getIonHeapsSizeKb()));
    }

    public static int pullLooperStatsLocked(int i, List list) {
        LooperStats looperStats = (LooperStats) LocalServices.getService(LooperStats.class);
        if (looperStats == null) {
            return 1;
        }
        List<LooperStats.ExportedEntry> entries = looperStats.getEntries();
        looperStats.reset();
        for (LooperStats.ExportedEntry exportedEntry : entries) {
            list.add(
                    FrameworkStatsLog.buildStatsEvent(
                            i,
                            exportedEntry.workSourceUid,
                            exportedEntry.handlerClassName,
                            exportedEntry.threadName,
                            exportedEntry.messageName,
                            exportedEntry.messageCount,
                            exportedEntry.exceptionCount,
                            exportedEntry.recordedMessageCount,
                            exportedEntry.totalLatencyMicros,
                            exportedEntry.cpuUsageMicros,
                            exportedEntry.isInteractive,
                            exportedEntry.maxCpuUsageMicros,
                            exportedEntry.maxLatencyMicros,
                            exportedEntry.recordedDelayMessageCount,
                            exportedEntry.delayMillis,
                            exportedEntry.maxDelayMillis));
        }
        return 0;
    }

    public static int pullProcessDmabufMemory(int i, List list) {
        KernelAllocationStats.ProcessDmabuf[] dmabufAllocations =
                KernelAllocationStats.getDmabufAllocations();
        if (dmabufAllocations == null) {
            return 1;
        }
        for (KernelAllocationStats.ProcessDmabuf processDmabuf : dmabufAllocations) {
            list.add(
                    FrameworkStatsLog.buildStatsEvent(
                            i,
                            processDmabuf.uid,
                            processDmabuf.processName,
                            processDmabuf.oomScore,
                            processDmabuf.retainedSizeKb,
                            processDmabuf.retainedBuffersCount,
                            0,
                            0,
                            processDmabuf.surfaceFlingerSizeKb,
                            processDmabuf.surfaceFlingerCount));
        }
        return 0;
    }

    public static void pullProcessMemoryHighWaterMarkLocked(int i, List list) {
        List<ProcessMemoryState> memoryStateForProcesses =
                ((ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class))
                        .getMemoryStateForProcesses();
        for (ProcessMemoryState processMemoryState : memoryStateForProcesses) {
            ProcfsMemoryUtil.MemorySnapshot readMemorySnapshotFromProcfs =
                    ProcfsMemoryUtil.readMemorySnapshotFromProcfs(processMemoryState.pid);
            if (readMemorySnapshotFromProcfs != null) {
                int i2 = processMemoryState.uid;
                String str = processMemoryState.processName;
                int i3 = readMemorySnapshotFromProcfs.rssHighWaterMarkInKilobytes;
                list.add(FrameworkStatsLog.buildStatsEvent(i, i2, str, i3 * 1024, i3));
            }
        }
        SparseArray processCmdlines = ProcfsMemoryUtil.getProcessCmdlines();
        memoryStateForProcesses.forEach(
                new StatsPullAtomService$$ExternalSyntheticLambda18(0, processCmdlines));
        int size = processCmdlines.size();
        for (int i4 = 0; i4 < size; i4++) {
            ProcfsMemoryUtil.MemorySnapshot readMemorySnapshotFromProcfs2 =
                    ProcfsMemoryUtil.readMemorySnapshotFromProcfs(processCmdlines.keyAt(i4));
            if (readMemorySnapshotFromProcfs2 != null) {
                int i5 = readMemorySnapshotFromProcfs2.uid;
                String str2 = (String) processCmdlines.valueAt(i4);
                int i6 = readMemorySnapshotFromProcfs2.rssHighWaterMarkInKilobytes;
                list.add(FrameworkStatsLog.buildStatsEvent(i, i5, str2, i6 * 1024, i6));
            }
        }
        SystemProperties.set("sys.rss_hwm_reset.on", "1");
    }

    public static void pullProcessMemorySnapshot(int i, List list) {
        List<ProcessMemoryState> memoryStateForProcesses =
                ((ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class))
                        .getMemoryStateForProcesses();
        KernelAllocationStats.ProcessGpuMem[] gpuAllocations =
                KernelAllocationStats.getGpuAllocations();
        SparseIntArray sparseIntArray = new SparseIntArray(gpuAllocations.length);
        for (KernelAllocationStats.ProcessGpuMem processGpuMem : gpuAllocations) {
            sparseIntArray.put(processGpuMem.pid, processGpuMem.gpuMemoryKb);
        }
        for (ProcessMemoryState processMemoryState : memoryStateForProcesses) {
            ProcfsMemoryUtil.MemorySnapshot readMemorySnapshotFromProcfs =
                    ProcfsMemoryUtil.readMemorySnapshotFromProcfs(processMemoryState.pid);
            if (readMemorySnapshotFromProcfs != null) {
                int i2 = processMemoryState.uid;
                String str = processMemoryState.processName;
                int i3 = processMemoryState.pid;
                int i4 = processMemoryState.oomScore;
                int i5 = readMemorySnapshotFromProcfs.rssInKilobytes;
                int i6 = readMemorySnapshotFromProcfs.anonRssInKilobytes;
                int i7 = readMemorySnapshotFromProcfs.swapInKilobytes;
                list.add(
                        FrameworkStatsLog.buildStatsEvent(
                                i,
                                i2,
                                str,
                                i3,
                                i4,
                                i5,
                                i6,
                                i7,
                                i6 + i7,
                                sparseIntArray.get(i3),
                                processMemoryState.hasForegroundServices,
                                readMemorySnapshotFromProcfs.rssShmemKilobytes,
                                processMemoryState.mHostingComponentTypes,
                                processMemoryState.mHistoricalHostingComponentTypes));
            }
        }
        SparseArray processCmdlines = ProcfsMemoryUtil.getProcessCmdlines();
        memoryStateForProcesses.forEach(
                new StatsPullAtomService$$ExternalSyntheticLambda18(1, processCmdlines));
        int size = processCmdlines.size();
        for (int i8 = 0; i8 < size; i8++) {
            int keyAt = processCmdlines.keyAt(i8);
            ProcfsMemoryUtil.MemorySnapshot readMemorySnapshotFromProcfs2 =
                    ProcfsMemoryUtil.readMemorySnapshotFromProcfs(keyAt);
            if (readMemorySnapshotFromProcfs2 != null) {
                int i9 = readMemorySnapshotFromProcfs2.uid;
                String str2 = (String) processCmdlines.valueAt(i8);
                int i10 = readMemorySnapshotFromProcfs2.rssInKilobytes;
                int i11 = readMemorySnapshotFromProcfs2.anonRssInKilobytes;
                int i12 = readMemorySnapshotFromProcfs2.swapInKilobytes;
                list.add(
                        FrameworkStatsLog.buildStatsEvent(
                                i,
                                i9,
                                str2,
                                keyAt,
                                -1001,
                                i10,
                                i11,
                                i12,
                                i11 + i12,
                                sparseIntArray.get(keyAt),
                                false,
                                readMemorySnapshotFromProcfs2.rssShmemKilobytes,
                                0,
                                0));
            }
        }
    }

    public static void pullProcessMemoryStateLocked(int i, List list) {
        for (ProcessMemoryState processMemoryState :
                ((ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class))
                        .getMemoryStateForProcesses()) {
            MemoryStatUtil.MemoryStat readMemoryStatFromFilesystem =
                    MemoryStatUtil.readMemoryStatFromFilesystem(
                            processMemoryState.uid, processMemoryState.pid);
            if (readMemoryStatFromFilesystem != null) {
                list.add(
                        FrameworkStatsLog.buildStatsEvent(
                                i,
                                processMemoryState.uid,
                                processMemoryState.processName,
                                processMemoryState.oomScore,
                                readMemoryStatFromFilesystem.pgfault,
                                readMemoryStatFromFilesystem.pgmajfault,
                                readMemoryStatFromFilesystem.rssInBytes,
                                readMemoryStatFromFilesystem.cacheInBytes,
                                readMemoryStatFromFilesystem.swapInBytes,
                                -1L,
                                -1L,
                                -1));
            }
        }
    }

    public static void pullProcessSystemIonHeapSizeLocked(int i, List list) {
        String str;
        Pattern pattern = IonMemoryUtil.ION_HEAP_SIZE_IN_BYTES;
        try {
            str = FileUtils.readTextFile(new File("/sys/kernel/debug/ion/heaps/system"), 0, null);
        } catch (IOException e) {
            Slog.e("IonMemoryUtil", "Failed to read file", e);
            str = "";
        }
        for (IonMemoryUtil.IonAllocations ionAllocations :
                IonMemoryUtil.parseProcessIonHeapSizesFromDebugfs(str)) {
            list.add(
                    FrameworkStatsLog.buildStatsEvent(
                            i,
                            Process.getUidForPid(ionAllocations.pid),
                            ProcfsMemoryUtil.readCmdlineFromProcfs(ionAllocations.pid),
                            (int) (ionAllocations.totalSizeInBytes / 1024),
                            ionAllocations.count,
                            (int) (ionAllocations.maxSizeInBytes / 1024)));
        }
    }

    public static void pullSystemElapsedRealtimeLocked(int i, List list) {
        list.add(FrameworkStatsLog.buildStatsEvent(i, SystemClock.elapsedRealtime()));
    }

    public static void pullSystemIonHeapSizeLocked(int i, List list) {
        String str;
        Pattern pattern = IonMemoryUtil.ION_HEAP_SIZE_IN_BYTES;
        try {
            str = FileUtils.readTextFile(new File("/sys/kernel/debug/ion/heaps/system"), 0, null);
        } catch (IOException e) {
            Slog.e("IonMemoryUtil", "Failed to read file", e);
            str = "";
        }
        list.add(
                FrameworkStatsLog.buildStatsEvent(
                        i, IonMemoryUtil.parseIonHeapSizeFromDebugfs(str)));
    }

    public static void pullSystemMemory(int i, List list) {
        long j;
        int dmabufHeapTotalExportedKb = (int) Debug.getDmabufHeapTotalExportedKb();
        int gpuTotalUsageKb = (int) Debug.getGpuTotalUsageKb();
        int gpuPrivateMemoryKb = (int) Debug.getGpuPrivateMemoryKb();
        int dmabufTotalExportedKb = (int) Debug.getDmabufTotalExportedKb();
        long[] jArr = new long[36];
        Debug.getMemInfo(jArr);
        long j2 = jArr[19];
        if (j2 == 0) {
            j2 = jArr[10];
        }
        long j3 =
                jArr[1] + jArr[14] + jArr[2] + jArr[20] + jArr[21] + jArr[22] + jArr[11] + j2
                        + jArr[16] + jArr[17];
        if (!Debug.isVmapStack()) {
            j3 += jArr[18];
        }
        if (dmabufTotalExportedKb < 0 || gpuPrivateMemoryKb < 0) {
            j3 += Math.max(0, gpuTotalUsageKb);
            if (dmabufTotalExportedKb < 0) {
                if (dmabufHeapTotalExportedKb >= 0) {
                    j = dmabufHeapTotalExportedKb;
                }
                int i2 = (int) jArr[11];
                int i3 = (int) jArr[16];
                int i4 = (int) jArr[17];
                int i5 = (int) jArr[18];
                int i6 = (int) jArr[8];
                long j4 = jArr[0];
                list.add(
                        FrameworkStatsLog.buildStatsEvent(
                                i,
                                i2,
                                i3,
                                i4,
                                i5,
                                dmabufHeapTotalExportedKb,
                                (int) (j4 - j3),
                                gpuTotalUsageKb,
                                gpuPrivateMemoryKb,
                                dmabufTotalExportedKb,
                                i6,
                                (int) j4,
                                (int) jArr[1],
                                (int) jArr[23],
                                (int) jArr[20],
                                (int) jArr[21],
                                (int) jArr[24],
                                (int) jArr[25],
                                (int) jArr[26],
                                (int) jArr[27],
                                (int) jArr[12],
                                (int) jArr[13],
                                (int) jArr[28],
                                (int) jArr[29]));
            }
            j = dmabufTotalExportedKb;
        } else {
            j = dmabufTotalExportedKb + gpuPrivateMemoryKb;
        }
        j3 += j;
        int i22 = (int) jArr[11];
        int i32 = (int) jArr[16];
        int i42 = (int) jArr[17];
        int i52 = (int) jArr[18];
        int i62 = (int) jArr[8];
        long j42 = jArr[0];
        list.add(
                FrameworkStatsLog.buildStatsEvent(
                        i,
                        i22,
                        i32,
                        i42,
                        i52,
                        dmabufHeapTotalExportedKb,
                        (int) (j42 - j3),
                        gpuTotalUsageKb,
                        gpuPrivateMemoryKb,
                        dmabufTotalExportedKb,
                        i62,
                        (int) j42,
                        (int) jArr[1],
                        (int) jArr[23],
                        (int) jArr[20],
                        (int) jArr[21],
                        (int) jArr[24],
                        (int) jArr[25],
                        (int) jArr[26],
                        (int) jArr[27],
                        (int) jArr[12],
                        (int) jArr[13],
                        (int) jArr[28],
                        (int) jArr[29]));
    }

    public static void pullSystemServerPinnerStats(int i, List list) {
        PinnerService pinnerService = (PinnerService) LocalServices.getService(PinnerService.class);
        pinnerService.getClass();
        ArrayList arrayList = new ArrayList();
        synchronized (pinnerService) {
            try {
                Iterator it = pinnerService.mPinnedFiles.values().iterator();
                while (it.hasNext()) {
                    arrayList.add(
                            new PinnerService.PinnedFileStats(
                                    1000, (PinnerService.PinnedFile) it.next()));
                }
                for (Integer num : pinnerService.mPinnedApps.keySet()) {
                    num.getClass();
                    PinnerService.PinnedApp pinnedApp =
                            (PinnerService.PinnedApp) pinnerService.mPinnedApps.get(num);
                    Iterator it2 =
                            ((PinnerService.PinnedApp) pinnerService.mPinnedApps.get(num))
                                    .mFiles.iterator();
                    while (it2.hasNext()) {
                        arrayList.add(
                                new PinnerService.PinnedFileStats(
                                        pinnedApp.uid, (PinnerService.PinnedFile) it2.next()));
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        Iterator it3 = arrayList.iterator();
        while (it3.hasNext()) {
            PinnerService.PinnedFileStats pinnedFileStats =
                    (PinnerService.PinnedFileStats) it3.next();
            list.add(
                    FrameworkStatsLog.buildStatsEvent(
                            i,
                            pinnedFileStats.uid,
                            pinnedFileStats.filename,
                            pinnedFileStats.sizeKb));
        }
    }

    public static void pullSystemUptimeLocked(int i, List list) {
        list.add(FrameworkStatsLog.buildStatsEvent(i, SystemClock.uptimeMillis()));
    }

    public static int pullTimeZoneDataInfoLocked(int i, List list) {
        try {
            list.add(FrameworkStatsLog.buildStatsEvent(i, TimeZone.getTZDataVersion()));
            return 0;
        } catch (MissingResourceException e) {
            Slog.e("StatsPullAtomService", "Getting tzdb version failed: ", e);
            return 1;
        }
    }

    public static int pullTimeZoneDetectorStateLocked(int i, List list) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                MetricsTimeZoneDetectorState generateMetricsState =
                        ((TimeZoneDetectorStrategyImpl)
                                        ((TimeZoneDetectorInternalImpl)
                                                        LocalServices.getService(
                                                                TimeZoneDetectorInternalImpl.class))
                                                .mTimeZoneDetectorStrategy)
                                .generateMetricsState();
                ConfigurationInternal configurationInternal =
                        generateMetricsState.mConfigurationInternal;
                boolean z = configurationInternal.mTelephonyDetectionSupported;
                boolean z2 = configurationInternal.mGeoDetectionSupported;
                boolean z3 = configurationInternal.mLocationEnabledSetting;
                boolean z4 = configurationInternal.mAutoDetectionEnabledSetting;
                boolean z5 = configurationInternal.mGeoDetectionEnabledSetting;
                int detectionMode = configurationInternal.getDetectionMode();
                int i2 = 3;
                char c =
                        detectionMode != 1
                                ? detectionMode != 2
                                        ? detectionMode != 3 ? (char) 0 : (char) 3
                                        : (char) 2
                                : (char) 1;
                if (c == 1) {
                    i2 = 1;
                } else if (c != 2) {
                    i2 = c != 3 ? 0 : 2;
                }
                list.add(
                        FrameworkStatsLog.buildStatsEvent(
                                i,
                                z,
                                z2,
                                z3,
                                z4,
                                z5,
                                i2,
                                generateMetricsState.mDeviceTimeZoneIdOrdinal,
                                convertTimeZoneSuggestionToProtoBytes(
                                        generateMetricsState.mLatestManualSuggestion),
                                convertTimeZoneSuggestionToProtoBytes(
                                        generateMetricsState.mLatestTelephonySuggestion),
                                convertTimeZoneSuggestionToProtoBytes(
                                        generateMetricsState.mLatestGeolocationSuggestion),
                                configurationInternal.mTelephonyFallbackSupported,
                                generateMetricsState.mDeviceTimeZoneId,
                                configurationInternal.mEnhancedMetricsCollectionEnabled,
                                configurationInternal.mGeoDetectionRunInBackgroundEnabled));
                return 0;
            } catch (RuntimeException e) {
                Slog.e("StatsPullAtomService", "Getting time zone detection state failed: ", e);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return 1;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static void pullVmStat(int i, List list) {
        ProcfsMemoryUtil.VmStat vmStat;
        long[] jArr = {-1};
        Process.readProcLines("/proc/vmstat", ProcfsMemoryUtil.VMSTAT_KEYS, jArr);
        long j = jArr[0];
        if (j == -1) {
            vmStat = null;
        } else {
            vmStat = new ProcfsMemoryUtil.VmStat();
            vmStat.oomKillCount = (int) j;
        }
        if (vmStat != null) {
            list.add(FrameworkStatsLog.buildStatsEvent(i, vmStat.oomKillCount));
        }
    }

    public static NetworkStats sliceNetworkStats(NetworkStats networkStats, Function function) {
        NetworkStats networkStats2 = new NetworkStats(0L, 1);
        Iterator it = networkStats.iterator();
        while (it.hasNext()) {
            networkStats2 =
                    networkStats2.addEntry(
                            (NetworkStats.Entry) function.apply((NetworkStats.Entry) it.next()));
        }
        return networkStats2;
    }

    public static byte[] toBytes(List list) {
        ProtoOutputStream protoOutputStream = new ProtoOutputStream();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            protoOutputStream.write(2259152797697L, ((Integer) it.next()).intValue());
        }
        return protoOutputStream.getBytes();
    }

    public static byte[] toBytes(int[] iArr) {
        ProtoOutputStream protoOutputStream = new ProtoOutputStream();
        for (int i : iArr) {
            protoOutputStream.write(2259152797697L, i);
        }
        return protoOutputStream.getBytes();
    }

    public static void unpackStreamedData(int i, List list, List list2) {
        ParcelFileDescriptor.AutoCloseInputStream autoCloseInputStream =
                new ParcelFileDescriptor.AutoCloseInputStream(
                        (ParcelFileDescriptor) ((ArrayList) list2).get(0));
        int available = autoCloseInputStream.available();
        byte[] bArr =
                new byte
                        [available > 0
                                ? available + 1
                                : EndpointMonitorConst
                                        .FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION];
        int i2 = 0;
        while (true) {
            int read = autoCloseInputStream.read(bArr, i2, bArr.length - i2);
            SystemServiceManager$$ExternalSyntheticOutline0.m(
                    ArrayUtils$$ExternalSyntheticOutline0.m(
                            read, i2, "Read ", " bytes at ", " of avail "),
                    bArr.length,
                    "StatsPullAtomService");
            if (read < 0) {
                SystemServiceManager$$ExternalSyntheticOutline0.m(
                        BatteryService$$ExternalSyntheticOutline0.m(
                                i2, "**** FINISHED READING: pos=", " len="),
                        bArr.length,
                        "StatsPullAtomService");
                list.add(
                        FrameworkStatsLog.buildStatsEvent(
                                i, Arrays.copyOf(bArr, new int[] {i2}[0])));
                return;
            }
            i2 += read;
            if (i2 >= bArr.length) {
                int i3 = i2 + EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION;
                byte[] bArr2 = new byte[i3];
                Slog.i("StatsPullAtomService", "Copying " + i2 + " bytes to new array len " + i3);
                System.arraycopy(bArr, 0, bArr2, 0, i2);
                bArr = bArr2;
            }
        }
    }

    public final List collectNetworkStatsSnapshotForAtom(int i) {
        ArrayList arrayList = new ArrayList();
        if (i == 10082) {
            Iterator it = this.mHistoricalSubs.iterator();
            while (it.hasNext()) {
                arrayList.addAll(getDataUsageBytesTransferSnapshotForSub((SubInfo) it.next()));
            }
        } else if (i == 10083) {
            NetworkStats uidNetworkStatsSnapshotForTemplate =
                    getUidNetworkStatsSnapshotForTemplate(
                            new NetworkTemplate.Builder(4).build(), true);
            NetworkStats uidNetworkStatsSnapshotForTemplate2 =
                    getUidNetworkStatsSnapshotForTemplate(
                            new NetworkTemplate.Builder(1).setMeteredness(1).build(), true);
            if (uidNetworkStatsSnapshotForTemplate != null
                    && uidNetworkStatsSnapshotForTemplate2 != null) {
                arrayList.add(
                        new NetworkStatsExt(
                                sliceNetworkStats(
                                        uidNetworkStatsSnapshotForTemplate.add(
                                                uidNetworkStatsSnapshotForTemplate2),
                                        new StatsPullAtomService$$ExternalSyntheticLambda2(1)),
                                new int[] {1, 0},
                                false,
                                true,
                                true,
                                0,
                                null,
                                -1,
                                false));
            }
        } else if (i == 10100) {
            int i2 = 3;
            List<Pair> of = List.of(new Pair(5, 3), new Pair(1, 0), new Pair(4, 1));
            int[] iArr = {3, 1, 2};
            ArrayList arrayList2 = new ArrayList();
            for (Pair pair : of) {
                Integer num = (Integer) pair.first;
                int i3 = 0;
                while (i3 < i2) {
                    int i4 = iArr[i3];
                    NetworkStats uidNetworkStatsSnapshotForTemplate3 =
                            getUidNetworkStatsSnapshotForTemplate(
                                    new NetworkTemplate.Builder(num.intValue())
                                            .setOemManaged(i4)
                                            .build(),
                                    false);
                    Integer num2 = (Integer) pair.second;
                    if (uidNetworkStatsSnapshotForTemplate3 != null) {
                        arrayList2.add(
                                new NetworkStatsExt(
                                        sliceNetworkStats(
                                                uidNetworkStatsSnapshotForTemplate3,
                                                new StatsPullAtomService$$ExternalSyntheticLambda2(
                                                        3)),
                                        new int[] {num2.intValue()},
                                        true,
                                        false,
                                        false,
                                        0,
                                        null,
                                        i4,
                                        false));
                    }
                    i3++;
                    i2 = 3;
                }
            }
            arrayList.addAll(arrayList2);
        } else if (i != 10200) {
            switch (i) {
                case 10000:
                    NetworkStats uidNetworkStatsSnapshotForTransport =
                            getUidNetworkStatsSnapshotForTransport(1);
                    if (uidNetworkStatsSnapshotForTransport != null) {
                        arrayList.add(
                                new NetworkStatsExt(
                                        sliceNetworkStats(
                                                uidNetworkStatsSnapshotForTransport,
                                                new StatsPullAtomService$$ExternalSyntheticLambda2(
                                                        0)),
                                        new int[] {1},
                                        false));
                        break;
                    }
                    break;
                case 10001:
                    NetworkStats uidNetworkStatsSnapshotForTransport2 =
                            getUidNetworkStatsSnapshotForTransport(1);
                    if (uidNetworkStatsSnapshotForTransport2 != null) {
                        arrayList.add(
                                new NetworkStatsExt(
                                        sliceNetworkStats(
                                                uidNetworkStatsSnapshotForTransport2,
                                                new StatsPullAtomService$$ExternalSyntheticLambda2(
                                                        3)),
                                        new int[] {1},
                                        true));
                        break;
                    }
                    break;
                case 10002:
                    NetworkStats uidNetworkStatsSnapshotForTransport3 =
                            getUidNetworkStatsSnapshotForTransport(0);
                    if (uidNetworkStatsSnapshotForTransport3 != null) {
                        arrayList.add(
                                new NetworkStatsExt(
                                        sliceNetworkStats(
                                                uidNetworkStatsSnapshotForTransport3,
                                                new StatsPullAtomService$$ExternalSyntheticLambda2(
                                                        0)),
                                        new int[] {0},
                                        false));
                        break;
                    }
                    break;
                case 10003:
                    NetworkStats uidNetworkStatsSnapshotForTransport4 =
                            getUidNetworkStatsSnapshotForTransport(0);
                    if (uidNetworkStatsSnapshotForTransport4 != null) {
                        arrayList.add(
                                new NetworkStatsExt(
                                        sliceNetworkStats(
                                                uidNetworkStatsSnapshotForTransport4,
                                                new StatsPullAtomService$$ExternalSyntheticLambda2(
                                                        3)),
                                        new int[] {0},
                                        true));
                        break;
                    }
                    break;
                default:
                    throw new IllegalArgumentException(
                            VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Unknown atomTag "));
            }
        } else {
            NetworkStats uidNetworkStatsSnapshotForTemplate4 =
                    getUidNetworkStatsSnapshotForTemplate(
                            new NetworkTemplate.Builder(9).build(), true);
            if (uidNetworkStatsSnapshotForTemplate4 != null) {
                arrayList.add(
                        new NetworkStatsExt(
                                sliceNetworkStats(
                                        uidNetworkStatsSnapshotForTemplate4,
                                        new StatsPullAtomService$$ExternalSyntheticLambda2(1)),
                                new int[] {2},
                                true,
                                false,
                                false,
                                0,
                                null,
                                -1,
                                true));
            }
        }
        return arrayList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v0 */
    /* JADX WARN: Type inference failed for: r4v2 */
    public final List getDataUsageBytesTransferSnapshotForSub(SubInfo subInfo) {
        ArrayList arrayList = new ArrayList();
        int[] allNetworkTypes = TelephonyManager.getAllNetworkTypes();
        HashSet hashSet = new HashSet();
        int i = 0;
        for (int i2 : allNetworkTypes) {
            hashSet.add(Integer.valueOf(NetworkStatsManager.getCollapsedRatType(i2)));
        }
        hashSet.add(Integer.valueOf(NetworkStatsManager.getCollapsedRatType(-2)));
        hashSet.add(0);
        int[] intArray = com.android.net.module.util.CollectionUtils.toIntArray(hashSet);
        int length = intArray.length;
        int i3 = 0;
        while (i3 < length) {
            int i4 = intArray[i3];
            NetworkStats uidNetworkStatsSnapshotForTemplate =
                    getUidNetworkStatsSnapshotForTemplate(
                            new NetworkTemplate.Builder(1)
                                    .setSubscriberIds(Set.of(subInfo.subscriberId))
                                    .setRatType(i4)
                                    .setMeteredness(1)
                                    .build(),
                            i);
            if (uidNetworkStatsSnapshotForTemplate != null) {
                arrayList.add(
                        new NetworkStatsExt(
                                sliceNetworkStats(
                                        uidNetworkStatsSnapshotForTemplate,
                                        new StatsPullAtomService$$ExternalSyntheticLambda2(2)),
                                new int[] {i},
                                true,
                                false,
                                false,
                                i4,
                                subInfo,
                                -1,
                                false));
            }
            i3++;
            i = 0;
        }
        return arrayList;
    }

    public final IKeystoreMetrics getIKeystoreMetricsService() {
        IKeystoreMetrics iKeystoreMetrics;
        synchronized (this.mKeystoreLock) {
            if (this.mIKeystoreMetrics == null) {
                IKeystoreMetrics asInterface =
                        IKeystoreMetrics.Stub.asInterface(
                                ServiceManager.getService("android.security.metrics"));
                this.mIKeystoreMetrics = asInterface;
                if (asInterface != null) {
                    try {
                        asInterface
                                .asBinder()
                                .linkToDeath(
                                        new StatsPullAtomService$$ExternalSyntheticLambda6(this, 0),
                                        0);
                    } catch (RemoteException e) {
                        Slog.e(
                                "StatsPullAtomService",
                                "linkToDeath with IKeystoreMetrics failed",
                                e);
                        this.mIKeystoreMetrics = null;
                    }
                }
            }
            iKeystoreMetrics = this.mIKeystoreMetrics;
        }
        return iKeystoreMetrics;
    }

    public final IThermalService getIThermalService() {
        IThermalService iThermalService;
        synchronized (this.mThermalLock) {
            if (this.mThermalService == null) {
                IThermalService asInterface =
                        IThermalService.Stub.asInterface(
                                ServiceManager.getService("thermalservice"));
                this.mThermalService = asInterface;
                if (asInterface != null) {
                    try {
                        asInterface
                                .asBinder()
                                .linkToDeath(
                                        new StatsPullAtomService$$ExternalSyntheticLambda6(this, 3),
                                        0);
                    } catch (RemoteException e) {
                        Slog.e("StatsPullAtomService", "linkToDeath with thermalService failed", e);
                        this.mThermalService = null;
                    }
                }
            }
            iThermalService = this.mThermalService;
        }
        return iThermalService;
    }

    public final ProcessStats getStatsFromProcessStatsService(int i) {
        synchronized (this.mProcStatsLock) {
            try {
                if (this.mProcessStatsService == null) {
                    this.mProcessStatsService =
                            IProcessStats.Stub.asInterface(ServiceManager.getService("procstats"));
                }
                IProcessStats iProcessStats = this.mProcessStatsService;
                if (iProcessStats != null) {
                    try {
                        iProcessStats
                                .asBinder()
                                .linkToDeath(
                                        new StatsPullAtomService$$ExternalSyntheticLambda6(this, 4),
                                        0);
                    } catch (RemoteException e) {
                        Slog.e("StatsPullAtomService", "linkToDeath with ProcessStats failed", e);
                        this.mProcessStatsService = null;
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        IProcessStats iProcessStats2 = this.mProcessStatsService;
        if (iProcessStats2 == null) {
            return null;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            long readProcStatsHighWaterMark = readProcStatsHighWaterMark(i);
            ProcessStats processStats = new ProcessStats(false);
            long committedStatsMerged =
                    iProcessStats2.getCommittedStatsMerged(
                            readProcStatsHighWaterMark, 31, true, (List) null, processStats);
            new File(
                            this.mBaseDir.getAbsolutePath()
                                    + "/"
                                    + highWaterMarkFilePrefix(i)
                                    + "_"
                                    + readProcStatsHighWaterMark)
                    .delete();
            new File(
                            this.mBaseDir.getAbsolutePath()
                                    + "/"
                                    + highWaterMarkFilePrefix(i)
                                    + "_"
                                    + committedStatsMerged)
                    .createNewFile();
            return processStats;
        } catch (RemoteException | IOException e2) {
            Slog.e("StatsPullAtomService", "Getting procstats failed: ", e2);
            return null;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final NetworkStats getUidNetworkStatsSnapshotForTemplate(
            NetworkTemplate networkTemplate, boolean z) {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        long millis = TimeUnit.MICROSECONDS.toMillis(SystemClock.currentTimeMicro());
        long j =
                Settings.Global.getLong(
                        this.mContext.getContentResolver(),
                        "netstats_uid_bucket_duration",
                        NETSTATS_UID_DEFAULT_BUCKET_DURATION_MS);
        if (networkTemplate.getMatchRule() == 4 && networkTemplate.getSubscriberIds().isEmpty()) {
            NetworkStatsManager networkStatsManager = this.mNetworkStatsManager;
            if (networkStatsManager == null) {
                throw new IllegalStateException("NetworkStatsManager is not ready");
            }
            networkStatsManager.forceUpdate();
        }
        NetworkStatsManager networkStatsManager2 = this.mNetworkStatsManager;
        if (networkStatsManager2 == null) {
            throw new IllegalStateException("NetworkStatsManager is not ready");
        }
        long j2 = (millis - elapsedRealtime) - j;
        android.app.usage.NetworkStats querySummary =
                networkStatsManager2.querySummary(networkTemplate, j2, millis);
        NetworkStats fromPublicNetworkStats =
                NetworkStatsUtils.fromPublicNetworkStats(querySummary);
        querySummary.close();
        if (!z) {
            return fromPublicNetworkStats;
        }
        NetworkStatsManager networkStatsManager3 = this.mNetworkStatsManager;
        if (networkStatsManager3 == null) {
            throw new IllegalStateException("NetworkStatsManager is not ready");
        }
        android.app.usage.NetworkStats queryTaggedSummary =
                networkStatsManager3.queryTaggedSummary(networkTemplate, j2, millis);
        NetworkStats fromPublicNetworkStats2 =
                NetworkStatsUtils.fromPublicNetworkStats(queryTaggedSummary);
        queryTaggedSummary.close();
        return fromPublicNetworkStats.add(fromPublicNetworkStats2);
    }

    public final NetworkStats getUidNetworkStatsSnapshotForTransport(int i) {
        NetworkTemplate build;
        if (i == 0) {
            build = new NetworkTemplate.Builder(1).setMeteredness(1).build();
        } else if (i != 1) {
            Log.wtf("StatsPullAtomService", "Unexpected transport.");
            build = null;
        } else {
            build = new NetworkTemplate.Builder(4).build();
        }
        return getUidNetworkStatsSnapshotForTemplate(build, false);
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        super.onBootPhase(i);
        if (i == 500) {
            final int i2 = 0;
            BackgroundThread.getHandler()
                    .post(
                            new Runnable(this) { // from class:
                                // com.android.server.stats.pull.StatsPullAtomService$$ExternalSyntheticLambda0
                                public final /* synthetic */ StatsPullAtomService f$0;

                                {
                                    this.f$0 = this;
                                }

                                @Override // java.lang.Runnable
                                public final void run() {
                                    boolean z;
                                    int i3 = i2;
                                    StatsPullAtomService statsPullAtomService = this.f$0;
                                    switch (i3) {
                                        case 0:
                                            StatsPullAtomService
                                                    .$r8$lambda$qb74jErDmbpYjoDRKkDDIHHyoHk(
                                                            statsPullAtomService);
                                            return;
                                        default:
                                            int i4 = StatsPullAtomService.RANDOM_SEED;
                                            statsPullAtomService.getClass();
                                            Slog.d(
                                                    "StatsPullAtomService",
                                                    "Registering NetworkStats pullers with statsd");
                                            try {
                                                new NetworkTemplate.Builder(9).build();
                                                z = true;
                                            } catch (IllegalArgumentException unused) {
                                                Slog.w(
                                                        "StatsPullAtomService",
                                                        "Querying network stats for TYPE_PROXY is"
                                                            + " not allowed");
                                                z = false;
                                            }
                                            synchronized (
                                                    statsPullAtomService.mDataBytesTransferLock) {
                                                try {
                                                    statsPullAtomService.mNetworkStatsBaselines
                                                            .addAll(
                                                                    statsPullAtomService
                                                                            .collectNetworkStatsSnapshotForAtom(
                                                                                    10000));
                                                    statsPullAtomService.mNetworkStatsBaselines
                                                            .addAll(
                                                                    statsPullAtomService
                                                                            .collectNetworkStatsSnapshotForAtom(
                                                                                    10001));
                                                    statsPullAtomService.mNetworkStatsBaselines
                                                            .addAll(
                                                                    statsPullAtomService
                                                                            .collectNetworkStatsSnapshotForAtom(
                                                                                    10002));
                                                    statsPullAtomService.mNetworkStatsBaselines
                                                            .addAll(
                                                                    statsPullAtomService
                                                                            .collectNetworkStatsSnapshotForAtom(
                                                                                    10003));
                                                    statsPullAtomService.mNetworkStatsBaselines
                                                            .addAll(
                                                                    statsPullAtomService
                                                                            .collectNetworkStatsSnapshotForAtom(
                                                                                    FrameworkStatsLog
                                                                                            .BYTES_TRANSFER_BY_TAG_AND_METERED));
                                                    statsPullAtomService.mNetworkStatsBaselines
                                                            .addAll(
                                                                    statsPullAtomService
                                                                            .collectNetworkStatsSnapshotForAtom(
                                                                                    FrameworkStatsLog
                                                                                            .DATA_USAGE_BYTES_TRANSFER));
                                                    statsPullAtomService.mNetworkStatsBaselines
                                                            .addAll(
                                                                    statsPullAtomService
                                                                            .collectNetworkStatsSnapshotForAtom(
                                                                                    FrameworkStatsLog
                                                                                            .OEM_MANAGED_BYTES_TRANSFER));
                                                    if (z) {
                                                        statsPullAtomService.mNetworkStatsBaselines
                                                                .addAll(
                                                                        statsPullAtomService
                                                                                .collectNetworkStatsSnapshotForAtom(
                                                                                        FrameworkStatsLog
                                                                                                .PROXY_BYTES_TRANSFER_BY_FG_BG));
                                                    }
                                                } catch (Throwable th) {
                                                    throw th;
                                                }
                                            }
                                            statsPullAtomService.mSubscriptionManager
                                                    .addOnSubscriptionsChangedListener(
                                                            BackgroundThread.getExecutor(),
                                                            statsPullAtomService
                                                                    .mStatsSubscriptionsListener);
                                            StatsManager.PullAtomMetadata build =
                                                    new StatsManager.PullAtomMetadata.Builder()
                                                            .setAdditiveFields(
                                                                    new int[] {2, 3, 4, 5})
                                                            .build();
                                            StatsManager statsManager =
                                                    statsPullAtomService.mStatsManager;
                                            Executor executor = ConcurrentUtils.DIRECT_EXECUTOR;
                                            statsManager.setPullAtomCallback(
                                                    10000,
                                                    build,
                                                    executor,
                                                    statsPullAtomService.mStatsCallbackImpl);
                                            statsPullAtomService.mStatsManager.setPullAtomCallback(
                                                    10001,
                                                    new StatsManager.PullAtomMetadata.Builder()
                                                            .setAdditiveFields(
                                                                    new int[] {3, 4, 5, 6})
                                                            .build(),
                                                    executor,
                                                    statsPullAtomService.mStatsCallbackImpl);
                                            statsPullAtomService.mStatsManager.setPullAtomCallback(
                                                    10002,
                                                    new StatsManager.PullAtomMetadata.Builder()
                                                            .setAdditiveFields(
                                                                    new int[] {2, 3, 4, 5})
                                                            .build(),
                                                    executor,
                                                    statsPullAtomService.mStatsCallbackImpl);
                                            statsPullAtomService.mStatsManager.setPullAtomCallback(
                                                    10003,
                                                    new StatsManager.PullAtomMetadata.Builder()
                                                            .setAdditiveFields(
                                                                    new int[] {3, 4, 5, 6})
                                                            .build(),
                                                    executor,
                                                    statsPullAtomService.mStatsCallbackImpl);
                                            boolean z2 =
                                                    StatsPullAtomService
                                                            .ENABLE_MOBILE_DATA_STATS_AGGREGATED_PULLER;
                                            if (z2) {
                                                DeviceIdleController$$ExternalSyntheticOutline0.m(
                                                        "ENABLE_MOBILE_DATA_STATS_AGGREGATED_PULLER"
                                                            + " = ",
                                                        "StatsPullAtomService",
                                                        z2);
                                                if (z2) {
                                                    statsPullAtomService
                                                                    .mAggregatedMobileDataStatsPuller =
                                                            new AggregatedMobileDataStatsPuller(
                                                                    (NetworkStatsManager)
                                                                            statsPullAtomService
                                                                                    .mContext
                                                                                    .getSystemService(
                                                                                            NetworkStatsManager
                                                                                                    .class));
                                                }
                                                statsPullAtomService.mStatsManager
                                                        .setPullAtomCallback(
                                                                FrameworkStatsLog
                                                                        .MOBILE_BYTES_TRANSFER_BY_PROC_STATE,
                                                                new StatsManager.PullAtomMetadata
                                                                                .Builder()
                                                                        .setAdditiveFields(
                                                                                new int[] {
                                                                                    3, 4, 5, 6
                                                                                })
                                                                        .build(),
                                                                executor,
                                                                statsPullAtomService
                                                                        .mStatsCallbackImpl);
                                            }
                                            statsPullAtomService.mStatsManager.setPullAtomCallback(
                                                    FrameworkStatsLog
                                                            .BYTES_TRANSFER_BY_TAG_AND_METERED,
                                                    new StatsManager.PullAtomMetadata.Builder()
                                                            .setAdditiveFields(
                                                                    new int[] {4, 5, 6, 7})
                                                            .build(),
                                                    executor,
                                                    statsPullAtomService.mStatsCallbackImpl);
                                            statsPullAtomService.mStatsManager.setPullAtomCallback(
                                                    FrameworkStatsLog.DATA_USAGE_BYTES_TRANSFER,
                                                    new StatsManager.PullAtomMetadata.Builder()
                                                            .setAdditiveFields(
                                                                    new int[] {2, 3, 4, 5})
                                                            .build(),
                                                    executor,
                                                    statsPullAtomService.mStatsCallbackImpl);
                                            statsPullAtomService.mStatsManager.setPullAtomCallback(
                                                    FrameworkStatsLog.OEM_MANAGED_BYTES_TRANSFER,
                                                    new StatsManager.PullAtomMetadata.Builder()
                                                            .setAdditiveFields(
                                                                    new int[] {5, 6, 7, 8})
                                                            .build(),
                                                    executor,
                                                    statsPullAtomService.mStatsCallbackImpl);
                                            if (z) {
                                                statsPullAtomService.mStatsManager
                                                        .setPullAtomCallback(
                                                                FrameworkStatsLog
                                                                        .PROXY_BYTES_TRANSFER_BY_FG_BG,
                                                                new StatsManager.PullAtomMetadata
                                                                                .Builder()
                                                                        .setAdditiveFields(
                                                                                new int[] {
                                                                                    3, 4, 5, 6
                                                                                })
                                                                        .build(),
                                                                executor,
                                                                statsPullAtomService
                                                                        .mStatsCallbackImpl);
                                            }
                                            UwbManager uwbManager =
                                                    statsPullAtomService
                                                                    .mContext
                                                                    .getPackageManager()
                                                                    .hasSystemFeature(
                                                                            "android.hardware.uwb")
                                                            ? (UwbManager)
                                                                    statsPullAtomService.mContext
                                                                            .getSystemService(
                                                                                    UwbManager
                                                                                            .class)
                                                            : null;
                                            statsPullAtomService.mUwbManager = uwbManager;
                                            if (uwbManager == null) {
                                                return;
                                            }
                                            statsPullAtomService.mStatsManager.setPullAtomCallback(
                                                    FrameworkStatsLog.UWB_ACTIVITY_INFO,
                                                    (StatsManager.PullAtomMetadata) null,
                                                    executor,
                                                    statsPullAtomService.mStatsCallbackImpl);
                                            return;
                                    }
                                }
                            });
        } else if (i == 600) {
            this.mNetworkStatsManager =
                    (NetworkStatsManager) this.mContext.getSystemService(NetworkStatsManager.class);
            final int i3 = 1;
            BackgroundThread.getHandler()
                    .post(
                            new Runnable(this) { // from class:
                                // com.android.server.stats.pull.StatsPullAtomService$$ExternalSyntheticLambda0
                                public final /* synthetic */ StatsPullAtomService f$0;

                                {
                                    this.f$0 = this;
                                }

                                @Override // java.lang.Runnable
                                public final void run() {
                                    boolean z;
                                    int i32 = i3;
                                    StatsPullAtomService statsPullAtomService = this.f$0;
                                    switch (i32) {
                                        case 0:
                                            StatsPullAtomService
                                                    .$r8$lambda$qb74jErDmbpYjoDRKkDDIHHyoHk(
                                                            statsPullAtomService);
                                            return;
                                        default:
                                            int i4 = StatsPullAtomService.RANDOM_SEED;
                                            statsPullAtomService.getClass();
                                            Slog.d(
                                                    "StatsPullAtomService",
                                                    "Registering NetworkStats pullers with statsd");
                                            try {
                                                new NetworkTemplate.Builder(9).build();
                                                z = true;
                                            } catch (IllegalArgumentException unused) {
                                                Slog.w(
                                                        "StatsPullAtomService",
                                                        "Querying network stats for TYPE_PROXY is"
                                                            + " not allowed");
                                                z = false;
                                            }
                                            synchronized (
                                                    statsPullAtomService.mDataBytesTransferLock) {
                                                try {
                                                    statsPullAtomService.mNetworkStatsBaselines
                                                            .addAll(
                                                                    statsPullAtomService
                                                                            .collectNetworkStatsSnapshotForAtom(
                                                                                    10000));
                                                    statsPullAtomService.mNetworkStatsBaselines
                                                            .addAll(
                                                                    statsPullAtomService
                                                                            .collectNetworkStatsSnapshotForAtom(
                                                                                    10001));
                                                    statsPullAtomService.mNetworkStatsBaselines
                                                            .addAll(
                                                                    statsPullAtomService
                                                                            .collectNetworkStatsSnapshotForAtom(
                                                                                    10002));
                                                    statsPullAtomService.mNetworkStatsBaselines
                                                            .addAll(
                                                                    statsPullAtomService
                                                                            .collectNetworkStatsSnapshotForAtom(
                                                                                    10003));
                                                    statsPullAtomService.mNetworkStatsBaselines
                                                            .addAll(
                                                                    statsPullAtomService
                                                                            .collectNetworkStatsSnapshotForAtom(
                                                                                    FrameworkStatsLog
                                                                                            .BYTES_TRANSFER_BY_TAG_AND_METERED));
                                                    statsPullAtomService.mNetworkStatsBaselines
                                                            .addAll(
                                                                    statsPullAtomService
                                                                            .collectNetworkStatsSnapshotForAtom(
                                                                                    FrameworkStatsLog
                                                                                            .DATA_USAGE_BYTES_TRANSFER));
                                                    statsPullAtomService.mNetworkStatsBaselines
                                                            .addAll(
                                                                    statsPullAtomService
                                                                            .collectNetworkStatsSnapshotForAtom(
                                                                                    FrameworkStatsLog
                                                                                            .OEM_MANAGED_BYTES_TRANSFER));
                                                    if (z) {
                                                        statsPullAtomService.mNetworkStatsBaselines
                                                                .addAll(
                                                                        statsPullAtomService
                                                                                .collectNetworkStatsSnapshotForAtom(
                                                                                        FrameworkStatsLog
                                                                                                .PROXY_BYTES_TRANSFER_BY_FG_BG));
                                                    }
                                                } catch (Throwable th) {
                                                    throw th;
                                                }
                                            }
                                            statsPullAtomService.mSubscriptionManager
                                                    .addOnSubscriptionsChangedListener(
                                                            BackgroundThread.getExecutor(),
                                                            statsPullAtomService
                                                                    .mStatsSubscriptionsListener);
                                            StatsManager.PullAtomMetadata build =
                                                    new StatsManager.PullAtomMetadata.Builder()
                                                            .setAdditiveFields(
                                                                    new int[] {2, 3, 4, 5})
                                                            .build();
                                            StatsManager statsManager =
                                                    statsPullAtomService.mStatsManager;
                                            Executor executor = ConcurrentUtils.DIRECT_EXECUTOR;
                                            statsManager.setPullAtomCallback(
                                                    10000,
                                                    build,
                                                    executor,
                                                    statsPullAtomService.mStatsCallbackImpl);
                                            statsPullAtomService.mStatsManager.setPullAtomCallback(
                                                    10001,
                                                    new StatsManager.PullAtomMetadata.Builder()
                                                            .setAdditiveFields(
                                                                    new int[] {3, 4, 5, 6})
                                                            .build(),
                                                    executor,
                                                    statsPullAtomService.mStatsCallbackImpl);
                                            statsPullAtomService.mStatsManager.setPullAtomCallback(
                                                    10002,
                                                    new StatsManager.PullAtomMetadata.Builder()
                                                            .setAdditiveFields(
                                                                    new int[] {2, 3, 4, 5})
                                                            .build(),
                                                    executor,
                                                    statsPullAtomService.mStatsCallbackImpl);
                                            statsPullAtomService.mStatsManager.setPullAtomCallback(
                                                    10003,
                                                    new StatsManager.PullAtomMetadata.Builder()
                                                            .setAdditiveFields(
                                                                    new int[] {3, 4, 5, 6})
                                                            .build(),
                                                    executor,
                                                    statsPullAtomService.mStatsCallbackImpl);
                                            boolean z2 =
                                                    StatsPullAtomService
                                                            .ENABLE_MOBILE_DATA_STATS_AGGREGATED_PULLER;
                                            if (z2) {
                                                DeviceIdleController$$ExternalSyntheticOutline0.m(
                                                        "ENABLE_MOBILE_DATA_STATS_AGGREGATED_PULLER"
                                                            + " = ",
                                                        "StatsPullAtomService",
                                                        z2);
                                                if (z2) {
                                                    statsPullAtomService
                                                                    .mAggregatedMobileDataStatsPuller =
                                                            new AggregatedMobileDataStatsPuller(
                                                                    (NetworkStatsManager)
                                                                            statsPullAtomService
                                                                                    .mContext
                                                                                    .getSystemService(
                                                                                            NetworkStatsManager
                                                                                                    .class));
                                                }
                                                statsPullAtomService.mStatsManager
                                                        .setPullAtomCallback(
                                                                FrameworkStatsLog
                                                                        .MOBILE_BYTES_TRANSFER_BY_PROC_STATE,
                                                                new StatsManager.PullAtomMetadata
                                                                                .Builder()
                                                                        .setAdditiveFields(
                                                                                new int[] {
                                                                                    3, 4, 5, 6
                                                                                })
                                                                        .build(),
                                                                executor,
                                                                statsPullAtomService
                                                                        .mStatsCallbackImpl);
                                            }
                                            statsPullAtomService.mStatsManager.setPullAtomCallback(
                                                    FrameworkStatsLog
                                                            .BYTES_TRANSFER_BY_TAG_AND_METERED,
                                                    new StatsManager.PullAtomMetadata.Builder()
                                                            .setAdditiveFields(
                                                                    new int[] {4, 5, 6, 7})
                                                            .build(),
                                                    executor,
                                                    statsPullAtomService.mStatsCallbackImpl);
                                            statsPullAtomService.mStatsManager.setPullAtomCallback(
                                                    FrameworkStatsLog.DATA_USAGE_BYTES_TRANSFER,
                                                    new StatsManager.PullAtomMetadata.Builder()
                                                            .setAdditiveFields(
                                                                    new int[] {2, 3, 4, 5})
                                                            .build(),
                                                    executor,
                                                    statsPullAtomService.mStatsCallbackImpl);
                                            statsPullAtomService.mStatsManager.setPullAtomCallback(
                                                    FrameworkStatsLog.OEM_MANAGED_BYTES_TRANSFER,
                                                    new StatsManager.PullAtomMetadata.Builder()
                                                            .setAdditiveFields(
                                                                    new int[] {5, 6, 7, 8})
                                                            .build(),
                                                    executor,
                                                    statsPullAtomService.mStatsCallbackImpl);
                                            if (z) {
                                                statsPullAtomService.mStatsManager
                                                        .setPullAtomCallback(
                                                                FrameworkStatsLog
                                                                        .PROXY_BYTES_TRANSFER_BY_FG_BG,
                                                                new StatsManager.PullAtomMetadata
                                                                                .Builder()
                                                                        .setAdditiveFields(
                                                                                new int[] {
                                                                                    3, 4, 5, 6
                                                                                })
                                                                        .build(),
                                                                executor,
                                                                statsPullAtomService
                                                                        .mStatsCallbackImpl);
                                            }
                                            UwbManager uwbManager =
                                                    statsPullAtomService
                                                                    .mContext
                                                                    .getPackageManager()
                                                                    .hasSystemFeature(
                                                                            "android.hardware.uwb")
                                                            ? (UwbManager)
                                                                    statsPullAtomService.mContext
                                                                            .getSystemService(
                                                                                    UwbManager
                                                                                            .class)
                                                            : null;
                                            statsPullAtomService.mUwbManager = uwbManager;
                                            if (uwbManager == null) {
                                                return;
                                            }
                                            statsPullAtomService.mStatsManager.setPullAtomCallback(
                                                    FrameworkStatsLog.UWB_ACTIVITY_INFO,
                                                    (StatsManager.PullAtomMetadata) null,
                                                    executor,
                                                    statsPullAtomService.mStatsCallbackImpl);
                                            return;
                                    }
                                }
                            });
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        if (ENABLE_MOBILE_DATA_STATS_AGGREGATED_PULLER) {
            LocalServices.addService(
                    StatsPullAtomServiceInternalImpl.class, new StatsPullAtomServiceInternalImpl());
        }
    }

    public final int pullAccessibilityFloatingMenuStatsLocked(int i, List list) {
        UserManager userManager = (UserManager) this.mContext.getSystemService(UserManager.class);
        if (userManager == null) {
            return 1;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ContentResolver contentResolver = this.mContext.getContentResolver();
            Iterator it = userManager.getUsers().iterator();
            while (true) {
                if (!it.hasNext()) {
                    return 0;
                }
                int identifier = ((UserInfo) it.next()).getUserHandle().getIdentifier();
                ContentResolver contentResolver2 = this.mContext.getContentResolver();
                int intForUser =
                        Settings.Secure.getIntForUser(
                                contentResolver2, "accessibility_button_mode", 0, identifier);
                String stringForUser =
                        Settings.Secure.getStringForUser(
                                contentResolver2, "accessibility_button_targets", identifier);
                if (intForUser == 1 && !TextUtils.isEmpty(stringForUser)) {
                    list.add(
                            FrameworkStatsLog.buildStatsEvent(
                                    i,
                                    Settings.Secure.getIntForUser(
                                            contentResolver,
                                            "accessibility_floating_menu_size",
                                            0,
                                            identifier),
                                    Settings.Secure.getIntForUser(
                                            contentResolver,
                                            "accessibility_floating_menu_icon_type",
                                            0,
                                            identifier),
                                    Settings.Secure.getIntForUser(
                                                    contentResolver,
                                                    "accessibility_floating_menu_fade_enabled",
                                                    1,
                                                    identifier)
                                            == 1,
                                    Settings.Secure.getFloatForUser(
                                            contentResolver,
                                            "accessibility_floating_menu_opacity",
                                            0.55f,
                                            identifier)));
                }
            }
        } catch (RuntimeException e) {
            Slog.e(
                    "StatsPullAtomService",
                    "pulling accessibility floating menu stats failed at getUsers",
                    e);
            return 1;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final int pullAccessibilityShortcutStatsLocked(List list) {
        int i;
        int i2;
        UserManager userManager = (UserManager) this.mContext.getSystemService(UserManager.class);
        if (userManager == null) {
            return 1;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ContentResolver contentResolver = this.mContext.getContentResolver();
            Iterator it = userManager.getUsers().iterator();
            while (it.hasNext()) {
                int identifier = ((UserInfo) it.next()).getUserHandle().getIdentifier();
                if (isAccessibilityShortcutUser(this.mContext, identifier)) {
                    int intForUser =
                            Settings.Secure.getIntForUser(
                                    contentResolver, "accessibility_button_mode", 0, identifier);
                    if (intForUser != 0) {
                        if (intForUser == 1) {
                            i2 = 5;
                        } else if (intForUser != 2) {
                            i = 0;
                        } else {
                            i2 = 6;
                        }
                        i = i2;
                    } else {
                        i = 1;
                    }
                    list.add(
                            FrameworkStatsLog.buildStatsEvent(
                                    FrameworkStatsLog.ACCESSIBILITY_SHORTCUT_STATS,
                                    i,
                                    countAccessibilityServices(
                                            Settings.Secure.getStringForUser(
                                                    contentResolver,
                                                    "accessibility_button_targets",
                                                    identifier)),
                                    2,
                                    countAccessibilityServices(
                                            Settings.Secure.getStringForUser(
                                                    contentResolver,
                                                    "accessibility_shortcut_target_service",
                                                    identifier)),
                                    3,
                                    Settings.Secure.getIntForUser(
                                            contentResolver,
                                            "accessibility_display_magnification_enabled",
                                            0,
                                            identifier),
                                    9,
                                    !TextUtils.isEmpty(
                                            Settings.Secure.getStringForUser(
                                                    contentResolver,
                                                    "accessibility_qs_targets",
                                                    identifier))));
                }
            }
            return 0;
        } catch (RuntimeException e) {
            Slog.e(
                    "StatsPullAtomService",
                    "pulling accessibility shortcuts stats failed at getUsers",
                    e);
            return 1;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final int pullAppOpsLocked(int i, List list) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AppOpsManager appOpsManager =
                    (AppOpsManager) this.mContext.getSystemService(AppOpsManager.class);
            CompletableFuture completableFuture = new CompletableFuture();
            appOpsManager.getHistoricalOps(
                    new AppOpsManager.HistoricalOpsRequest.Builder(0L, Long.MAX_VALUE)
                            .setFlags(9)
                            .build(),
                    AsyncTask.THREAD_POOL_EXECUTOR,
                    new StatsPullAtomService$$ExternalSyntheticLambda11(1, completableFuture));
            if (sampleAppOps(
                            i,
                            list,
                            processHistoricalOps(
                                    (AppOpsManager.HistoricalOps)
                                            completableFuture.get(2000L, TimeUnit.MILLISECONDS),
                                    i,
                                    100),
                            100)
                    != 100) {
                Slog.e("StatsPullAtomService", "Atom 10060 downsampled - too many dimensions");
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return 0;
        } catch (Throwable th) {
            try {
                Slog.e("StatsPullAtomService", "Could not read appops", th);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return 1;
            } catch (Throwable th2) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th2;
            }
        }
    }

    public final int pullAppsOnExternalStorageInfoLocked(int i, List list) {
        VolumeInfo findVolumeByUuid;
        DiskInfo disk;
        if (this.mStorageManager == null) {
            return 1;
        }
        for (ApplicationInfo applicationInfo :
                this.mContext.getPackageManager().getInstalledApplications(0)) {
            UUID uuid = applicationInfo.storageUuid;
            if (uuid != null
                    && (findVolumeByUuid = this.mStorageManager.findVolumeByUuid(uuid.toString()))
                            != null
                    && (disk = findVolumeByUuid.getDisk()) != null) {
                int i2 = disk.isSd() ? 1 : disk.isUsb() ? 2 : applicationInfo.isExternal() ? 3 : -1;
                if (i2 != -1) {
                    list.add(FrameworkStatsLog.buildStatsEvent(i, i2, applicationInfo.packageName));
                }
            }
        }
        return 0;
    }

    public final int pullAttributedAppOpsLocked(int i, List list) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AppOpsManager appOpsManager =
                    (AppOpsManager) this.mContext.getSystemService(AppOpsManager.class);
            CompletableFuture completableFuture = new CompletableFuture();
            appOpsManager.getHistoricalOps(
                    new AppOpsManager.HistoricalOpsRequest.Builder(0L, Long.MAX_VALUE)
                            .setFlags(9)
                            .build(),
                    AsyncTask.THREAD_POOL_EXECUTOR,
                    new StatsPullAtomService$$ExternalSyntheticLambda11(1, completableFuture));
            AppOpsManager.HistoricalOps historicalOps =
                    (AppOpsManager.HistoricalOps)
                            completableFuture.get(2000L, TimeUnit.MILLISECONDS);
            if (this.mAppOpsSamplingRate == 0) {
                this.mContext
                        .getMainThreadHandler()
                        .postDelayed(
                                new Runnable() { // from class:
                                                 // com.android.server.stats.pull.StatsPullAtomService.4
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        try {
                                            StatsPullAtomService
                                                    .m951$$Nest$mestimateAppOpsSamplingRate(
                                                            StatsPullAtomService.this);
                                        } finally {
                                        }
                                    }
                                },
                                45000L);
                this.mAppOpsSamplingRate = 100;
            }
            this.mAppOpsSamplingRate =
                    Math.min(
                            this.mAppOpsSamplingRate,
                            sampleAppOps(
                                    i,
                                    list,
                                    processHistoricalOps(
                                            historicalOps, i, this.mAppOpsSamplingRate),
                                    this.mAppOpsSamplingRate));
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return 0;
        } catch (Throwable th) {
            try {
                Slog.e("StatsPullAtomService", "Could not read appops", th);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return 1;
            } catch (Throwable th2) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th2;
            }
        }
    }

    public final int pullCooldownDeviceLocked(int i, List list) {
        IThermalService iThermalService = getIThermalService();
        if (iThermalService == null) {
            return 1;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            for (CoolingDevice coolingDevice : iThermalService.getCurrentCoolingDevices()) {
                list.add(
                        FrameworkStatsLog.buildStatsEvent(
                                i,
                                coolingDevice.getType(),
                                coolingDevice.getName(),
                                (int) coolingDevice.getValue()));
            }
            return 0;
        } catch (RemoteException unused) {
            Slog.e(
                    "StatsPullAtomService",
                    "Disconnected from thermal service. Cannot pull temperatures.");
            return 1;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void pullCpuActiveTimeLocked(int i, List list) {
        this.mCpuUidActiveTimeReader.readAbsolute(
                new StatsPullAtomService$$ExternalSyntheticLambda13(i, 0, list));
    }

    public final void pullCpuClusterTimeLocked(int i, List list) {
        this.mCpuUidClusterTimeReader.readAbsolute(
                new StatsPullAtomService$$ExternalSyntheticLambda13(i, 2, list));
    }

    public final int pullCpuCyclesPerThreadGroupCluster(int i, List list) {
        SystemServerCpuThreadReader.SystemServiceCpuThreadTimes systemServiceCpuThreadTimes;
        long[] jArr;
        if (Flags.disableSystemServicePowerAttr()
                || (systemServiceCpuThreadTimes =
                                ((BatteryStatsInternal)
                                                LocalServices.getService(
                                                        BatteryStatsInternal.class))
                                        .getSystemServiceCpuThreadTimes())
                        == null) {
            return 1;
        }
        addCpuCyclesPerThreadGroupClusterAtoms(
                i, list, 2, systemServiceCpuThreadTimes.threadCpuTimesUs);
        addCpuCyclesPerThreadGroupClusterAtoms(
                i, list, 1, systemServiceCpuThreadTimes.binderThreadCpuTimesUs);
        KernelSingleProcessCpuThreadReader.ProcessCpuUsage readAbsolute =
                this.mSurfaceFlingerProcessCpuThreadReader.readAbsolute();
        if (readAbsolute != null && (jArr = readAbsolute.threadCpuTimesMillis) != null) {
            int length = jArr.length;
            long[] jArr2 = new long[length];
            for (int i2 = 0; i2 < length; i2++) {
                jArr2[i2] = readAbsolute.threadCpuTimesMillis[i2] * 1000;
            }
            addCpuCyclesPerThreadGroupClusterAtoms(i, list, 3, jArr2);
        }
        return 0;
    }

    public final void pullCpuCyclesPerUidClusterLocked(int i, List list) {
        PowerProfile powerProfile = new PowerProfile(this.mContext);
        final int[] freqsClusters = KernelCpuBpfTracking.getFreqsClusters();
        final int clusters = KernelCpuBpfTracking.getClusters();
        final long[] freqs = KernelCpuBpfTracking.getFreqs();
        final double[] dArr = new double[freqs.length];
        int i2 = -1;
        int i3 = 0;
        int i4 = 0;
        while (i3 < freqs.length) {
            int i5 = freqsClusters[i3];
            if (i5 != i2) {
                i4 = 0;
            }
            dArr[i3] = powerProfile.getAveragePowerForCpuCore(i5, i4);
            i3++;
            i4++;
            i2 = i5;
        }
        final SparseArray sparseArray = new SparseArray();
        this.mCpuUidFreqTimeReader.readAbsolute(
                new KernelCpuUidTimeReader
                        .Callback() { // from class:
                                      // com.android.server.stats.pull.StatsPullAtomService$$ExternalSyntheticLambda16
                    public final void onUidCpuTime(int i6, Object obj) {
                        SparseArray sparseArray2 = sparseArray;
                        int i7 = clusters;
                        int[] iArr = freqsClusters;
                        long[] jArr = freqs;
                        double[] dArr2 = dArr;
                        long[] jArr2 = (long[]) obj;
                        int i8 = StatsPullAtomService.RANDOM_SEED;
                        if (UserHandle.isIsolated(i6)) {
                            return;
                        }
                        int appId = UserHandle.isSharedAppGid(i6) ? 59999 : UserHandle.getAppId(i6);
                        double[] dArr3 = (double[]) sparseArray2.get(appId);
                        if (dArr3 == null) {
                            dArr3 = new double[i7 * 3];
                            sparseArray2.put(appId, dArr3);
                        }
                        for (int i9 = 0; i9 < jArr2.length; i9++) {
                            int i10 = iArr[i9];
                            long j = jArr2[i9];
                            int i11 = i10 * 3;
                            dArr3[i11] = dArr3[i11] + (jArr[i9] * j);
                            int i12 = i11 + 1;
                            double d = j;
                            dArr3[i12] = dArr3[i12] + d;
                            int i13 = i11 + 2;
                            dArr3[i13] = (dArr2[i9] * d) + dArr3[i13];
                        }
                    }
                });
        int size = sparseArray.size();
        for (int i6 = 0; i6 < size; i6++) {
            int keyAt = sparseArray.keyAt(i6);
            double[] dArr2 = (double[]) sparseArray.valueAt(i6);
            for (int i7 = 0; i7 < clusters; i7++) {
                int i8 = i7 * 3;
                list.add(
                        FrameworkStatsLog.buildStatsEvent(
                                i,
                                keyAt,
                                i7,
                                (long) (dArr2[i8] / 1000000.0d),
                                (long) dArr2[i8 + 1],
                                (long) (dArr2[i8 + 2] / 1000.0d)));
            }
        }
    }

    public final int pullCpuTimePerThreadFreqLocked(int i, List list) {
        KernelCpuThreadReaderDiff kernelCpuThreadReaderDiff = this.mKernelCpuThreadReader;
        if (kernelCpuThreadReaderDiff == null) {
            Slog.e("StatsPullAtomService", "mKernelCpuThreadReader is null");
            return 1;
        }
        ArrayList processCpuUsageDiffed = kernelCpuThreadReaderDiff.getProcessCpuUsageDiffed();
        if (processCpuUsageDiffed == null) {
            Slog.e("StatsPullAtomService", "processCpuUsages is null");
            return 1;
        }
        int[] cpuFrequenciesKhz = this.mKernelCpuThreadReader.getCpuFrequenciesKhz();
        if (cpuFrequenciesKhz.length > 8) {
            HeapdumpWatcher$$ExternalSyntheticOutline0.m(
                    new StringBuilder("Expected maximum 8 frequencies, but got "),
                    cpuFrequenciesKhz.length,
                    "StatsPullAtomService");
            return 1;
        }
        for (int i2 = 0; i2 < processCpuUsageDiffed.size(); i2++) {
            KernelCpuThreadReader.ProcessCpuUsage processCpuUsage =
                    (KernelCpuThreadReader.ProcessCpuUsage) processCpuUsageDiffed.get(i2);
            ArrayList arrayList = processCpuUsage.threadCpuUsages;
            for (int i3 = 0; i3 < arrayList.size(); i3++) {
                KernelCpuThreadReader.ThreadCpuUsage threadCpuUsage =
                        (KernelCpuThreadReader.ThreadCpuUsage) arrayList.get(i3);
                if (threadCpuUsage.usageTimesMillis.length != cpuFrequenciesKhz.length) {
                    StringBuilder sb =
                            new StringBuilder("Unexpected number of usage times, expected ");
                    sb.append(cpuFrequenciesKhz.length);
                    sb.append(" but got ");
                    HeapdumpWatcher$$ExternalSyntheticOutline0.m(
                            sb, threadCpuUsage.usageTimesMillis.length, "StatsPullAtomService");
                    return 1;
                }
                int[] iArr = new int[8];
                int[] iArr2 = new int[8];
                for (int i4 = 0; i4 < 8; i4++) {
                    if (i4 < cpuFrequenciesKhz.length) {
                        iArr[i4] = cpuFrequenciesKhz[i4];
                        iArr2[i4] = threadCpuUsage.usageTimesMillis[i4];
                    } else {
                        iArr[i4] = 0;
                        iArr2[i4] = 0;
                    }
                }
                list.add(
                        FrameworkStatsLog.buildStatsEvent(
                                i,
                                processCpuUsage.uid,
                                processCpuUsage.processId,
                                threadCpuUsage.threadId,
                                processCpuUsage.processName,
                                threadCpuUsage.threadName,
                                iArr[0],
                                iArr2[0],
                                iArr[1],
                                iArr2[1],
                                iArr[2],
                                iArr2[2],
                                iArr[3],
                                iArr2[3],
                                iArr[4],
                                iArr2[4],
                                iArr[5],
                                iArr2[5],
                                iArr[6],
                                iArr2[6],
                                iArr[7],
                                iArr2[7]));
            }
        }
        return 0;
    }

    public final void pullCpuTimePerUidFreqLocked(int i, List list) {
        final SparseArray sparseArray = new SparseArray();
        this.mCpuUidFreqTimeReader.readAbsolute(
                new KernelCpuUidTimeReader
                        .Callback() { // from class:
                                      // com.android.server.stats.pull.StatsPullAtomService$$ExternalSyntheticLambda21
                    public final void onUidCpuTime(int i2, Object obj) {
                        SparseArray sparseArray2 = sparseArray;
                        long[] jArr = (long[]) obj;
                        int i3 = StatsPullAtomService.RANDOM_SEED;
                        if (UserHandle.isIsolated(i2)) {
                            return;
                        }
                        int appId = UserHandle.isSharedAppGid(i2) ? 59999 : UserHandle.getAppId(i2);
                        long[] jArr2 = (long[]) sparseArray2.get(appId);
                        if (jArr2 == null) {
                            jArr2 = new long[jArr.length];
                            sparseArray2.put(appId, jArr2);
                        }
                        for (int i4 = 0; i4 < jArr.length; i4++) {
                            jArr2[i4] = jArr2[i4] + jArr[i4];
                        }
                    }
                });
        int size = sparseArray.size();
        for (int i2 = 0; i2 < size; i2++) {
            int keyAt = sparseArray.keyAt(i2);
            long[] jArr = (long[]) sparseArray.valueAt(i2);
            for (int i3 = 0; i3 < jArr.length; i3++) {
                long j = jArr[i3];
                if (j >= 10) {
                    list.add(FrameworkStatsLog.buildStatsEvent(i, keyAt, i3, j));
                }
            }
        }
    }

    public final void pullCpuTimePerUidLocked(int i, List list) {
        this.mCpuUidUserSysTimeReader.readAbsolute(
                new StatsPullAtomService$$ExternalSyntheticLambda13(i, 1, list));
    }

    public final int pullDangerousPermissionStateLocked(int i, List list) {
        PackageInfo packageInfo;
        int i2;
        List list2;
        UserHandle userHandle;
        int i3;
        int i4;
        StatsEvent buildStatsEvent;
        int i5 = i;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        float f =
                DeviceConfig.getFloat(
                        "permissions", "dangerous_permission_state_sample_rate", 0.015f);
        HashSet hashSet = new HashSet();
        try {
            PackageManager packageManager = this.mContext.getPackageManager();
            List users =
                    ((UserManager) this.mContext.getSystemService(UserManager.class)).getUsers();
            int size = users.size();
            int i6 = 0;
            while (i6 < size) {
                UserHandle userHandle2 = ((UserInfo) users.get(i6)).getUserHandle();
                List installedPackagesAsUser =
                        packageManager.getInstalledPackagesAsUser(
                                4096, userHandle2.getIdentifier());
                int size2 = installedPackagesAsUser.size();
                int i7 = 0;
                while (i7 < size2) {
                    PackageInfo packageInfo2 = (PackageInfo) installedPackagesAsUser.get(i7);
                    if (packageInfo2.requestedPermissions != null
                            && !hashSet.contains(
                                    Integer.valueOf(packageInfo2.applicationInfo.uid))) {
                        hashSet.add(Integer.valueOf(packageInfo2.applicationInfo.uid));
                        if (i5 != 10067 || ThreadLocalRandom.current().nextFloat() <= f) {
                            int length = packageInfo2.requestedPermissions.length;
                            int i8 = 0;
                            while (i8 < length) {
                                int i9 = i7;
                                String str = packageInfo2.requestedPermissions[i8];
                                float f2 = f;
                                try {
                                    PermissionInfo permissionInfo =
                                            packageManager.getPermissionInfo(str, 0);
                                    try {
                                        int permissionFlags =
                                                packageManager.getPermissionFlags(
                                                        str, packageInfo2.packageName, userHandle2);
                                        i2 = size2;
                                        if (str.startsWith("android.permission.")) {
                                            str = str.substring(19);
                                        }
                                        if (i5 == 10050) {
                                            int i10 = packageInfo2.applicationInfo.uid;
                                            list2 = installedPackagesAsUser;
                                            packageInfo = packageInfo2;
                                            userHandle = userHandle2;
                                            boolean z =
                                                    (packageInfo2.requestedPermissionsFlags[i8] & 2)
                                                            != 0;
                                            i3 = i6;
                                            i4 = i8;
                                            buildStatsEvent =
                                                    FrameworkStatsLog.buildStatsEvent(
                                                            i,
                                                            str,
                                                            i10,
                                                            "",
                                                            z,
                                                            permissionFlags,
                                                            permissionInfo.getProtection()
                                                                    | permissionInfo
                                                                            .getProtectionFlags());
                                        } else {
                                            packageInfo = packageInfo2;
                                            list2 = installedPackagesAsUser;
                                            userHandle = userHandle2;
                                            i3 = i6;
                                            i4 = i8;
                                            buildStatsEvent =
                                                    FrameworkStatsLog.buildStatsEvent(
                                                            i,
                                                            str,
                                                            packageInfo.applicationInfo.uid,
                                                            (packageInfo
                                                                                    .requestedPermissionsFlags[
                                                                                    i4]
                                                                            & 2)
                                                                    != 0,
                                                            permissionFlags,
                                                            permissionInfo.getProtection()
                                                                    | permissionInfo
                                                                            .getProtectionFlags());
                                        }
                                        list.add(buildStatsEvent);
                                    } catch (PackageManager.NameNotFoundException unused) {
                                        packageInfo = packageInfo2;
                                        i2 = size2;
                                        list2 = installedPackagesAsUser;
                                        userHandle = userHandle2;
                                        i3 = i6;
                                        i4 = i8;
                                    }
                                } catch (PackageManager.NameNotFoundException unused2) {
                                    packageInfo = packageInfo2;
                                    i2 = size2;
                                    list2 = installedPackagesAsUser;
                                    userHandle = userHandle2;
                                    i3 = i6;
                                    i4 = i8;
                                }
                                i8 = i4 + 1;
                                packageInfo2 = packageInfo;
                                i7 = i9;
                                f = f2;
                                size2 = i2;
                                userHandle2 = userHandle;
                                installedPackagesAsUser = list2;
                                i6 = i3;
                                i5 = i;
                            }
                        }
                    }
                    i5 = i;
                    i7++;
                    f = f;
                    size2 = size2;
                    userHandle2 = userHandle2;
                    installedPackagesAsUser = installedPackagesAsUser;
                    i6 = i6;
                }
                i6++;
                i5 = i;
            }
            return 0;
        } catch (Throwable th) {
            try {
                Log.e("StatsPullAtomService", "Could not read permissions", th);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return 1;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public final void pullDebugElapsedClockLocked(int i, List list) {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        long j = this.mDebugElapsedClockPreviousValue;
        long j2 = j == 0 ? 0L : elapsedRealtime - j;
        list.add(
                FrameworkStatsLog.buildStatsEvent(
                        i,
                        this.mDebugElapsedClockPullCount,
                        elapsedRealtime,
                        elapsedRealtime,
                        j2,
                        1));
        long j3 = this.mDebugElapsedClockPullCount;
        if (j3 % 2 == 1) {
            list.add(
                    FrameworkStatsLog.buildStatsEvent(
                            i, j3, elapsedRealtime, elapsedRealtime, j2, 2));
        }
        this.mDebugElapsedClockPullCount++;
        this.mDebugElapsedClockPreviousValue = elapsedRealtime;
    }

    public final int pullDebugFailingElapsedClockLocked(int i, List list) {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        long j = this.mDebugFailingElapsedClockPullCount;
        long j2 = 1 + j;
        this.mDebugFailingElapsedClockPullCount = j2;
        if (j % 5 == 0) {
            this.mDebugFailingElapsedClockPreviousValue = elapsedRealtime;
            Slog.e("StatsPullAtomService", "Failing debug elapsed clock");
            return 1;
        }
        long j3 = this.mDebugFailingElapsedClockPreviousValue;
        list.add(
                FrameworkStatsLog.buildStatsEvent(
                        i,
                        j2,
                        elapsedRealtime,
                        elapsedRealtime,
                        j3 == 0 ? 0L : elapsedRealtime - j3));
        this.mDebugFailingElapsedClockPreviousValue = elapsedRealtime;
        return 0;
    }

    public final int pullDeviceCalculatedPowerUseLocked(int i, List list) {
        try {
            list.add(
                    FrameworkStatsLog.buildStatsEvent(
                            i,
                            (long)
                                    ((((BatteryStatsManager)
                                                                    this.mContext.getSystemService(
                                                                            BatteryStatsManager
                                                                                    .class))
                                                            .getBatteryUsageStats()
                                                            .getConsumedPower()
                                                    * 3.6E9d)
                                            + 0.5d)));
            return 0;
        } catch (Exception e) {
            Log.e("StatsPullAtomService", "Could not obtain battery usage stats", e);
            return 1;
        }
    }

    public final void pullDiskIOLocked(final int i, final List list) {
        this.mStoragedUidIoStatsReader.readAbsolute(
                new StoragedUidIoStatsReader
                        .Callback() { // from class:
                                      // com.android.server.stats.pull.StatsPullAtomService$$ExternalSyntheticLambda17
                    public final void onUidStorageStats(
                            int i2,
                            long j,
                            long j2,
                            long j3,
                            long j4,
                            long j5,
                            long j6,
                            long j7,
                            long j8,
                            long j9,
                            long j10) {
                        List list2 = list;
                        int i3 = i;
                        int i4 = StatsPullAtomService.RANDOM_SEED;
                        list2.add(
                                FrameworkStatsLog.buildStatsEvent(
                                        i3, i2, j, j2, j3, j4, j5, j6, j7, j8, j9, j10));
                    }
                });
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0051  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0061 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int pullDiskStatsLocked(int r10, java.util.List r11) {
        /*
            r9 = this;
            r0 = 512(0x200, float:7.175E-43)
            byte[] r1 = new byte[r0]
            r2 = 0
            r3 = r2
        L6:
            if (r3 >= r0) goto Le
            byte r4 = (byte) r3
            r1[r3] = r4
            int r3 = r3 + 1
            goto L6
        Le:
            java.io.File r0 = new java.io.File
            java.io.File r3 = android.os.Environment.getDataDirectory()
            java.lang.String r4 = "system/statsdperftest.tmp"
            r0.<init>(r3, r4)
            long r3 = android.os.SystemClock.elapsedRealtime()
            r5 = 0
            java.io.FileOutputStream r6 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L31 java.io.IOException -> L33
            r6.<init>(r0)     // Catch: java.lang.Throwable -> L31 java.io.IOException -> L33
            r6.write(r1)     // Catch: java.lang.Throwable -> L2c java.io.IOException -> L2f
            r6.close()     // Catch: java.io.IOException -> L2a
        L2a:
            r1 = r5
            goto L41
        L2c:
            r9 = move-exception
            r5 = r6
            goto L36
        L2f:
            r1 = move-exception
            goto L3c
        L31:
            r9 = move-exception
            goto L36
        L33:
            r1 = move-exception
            r6 = r5
            goto L3c
        L36:
            if (r5 == 0) goto L3b
            r5.close()     // Catch: java.io.IOException -> L3b
        L3b:
            throw r9
        L3c:
            if (r6 == 0) goto L41
            r6.close()     // Catch: java.io.IOException -> L41
        L41:
            long r6 = android.os.SystemClock.elapsedRealtime()
            long r6 = r6 - r3
            boolean r3 = r0.exists()
            if (r3 == 0) goto L4f
            r0.delete()
        L4f:
            if (r1 == 0) goto L5a
            java.lang.String r0 = "StatsPullAtomService"
            java.lang.String r1 = "Error performing diskstats latency test"
            android.util.Slog.e(r0, r1)
            r6 = -1
        L5a:
            boolean r0 = android.os.storage.StorageManager.isFileEncrypted()
            java.lang.Object r1 = r9.mStoragedLock
            monitor-enter(r1)
            android.os.IStoraged r3 = r9.mStorageService     // Catch: java.lang.Throwable -> L73
            if (r3 != 0) goto L75
            java.lang.String r3 = "storaged"
            android.os.IBinder r3 = android.os.ServiceManager.getService(r3)     // Catch: java.lang.Throwable -> L73
            android.os.IStoraged r3 = android.os.IStoraged.Stub.asInterface(r3)     // Catch: java.lang.Throwable -> L73
            r9.mStorageService = r3     // Catch: java.lang.Throwable -> L73
            goto L75
        L73:
            r9 = move-exception
            goto Lae
        L75:
            android.os.IStoraged r3 = r9.mStorageService     // Catch: java.lang.Throwable -> L73
            if (r3 == 0) goto L91
            android.os.IBinder r3 = r3.asBinder()     // Catch: java.lang.Throwable -> L73 android.os.RemoteException -> L87
            com.android.server.stats.pull.StatsPullAtomService$$ExternalSyntheticLambda6 r4 = new com.android.server.stats.pull.StatsPullAtomService$$ExternalSyntheticLambda6     // Catch: java.lang.Throwable -> L73 android.os.RemoteException -> L87
            r8 = 2
            r4.<init>(r9, r8)     // Catch: java.lang.Throwable -> L73 android.os.RemoteException -> L87
            r3.linkToDeath(r4, r2)     // Catch: java.lang.Throwable -> L73 android.os.RemoteException -> L87
            goto L91
        L87:
            r3 = move-exception
            java.lang.String r4 = "StatsPullAtomService"
            java.lang.String r8 = "linkToDeath with storagedService failed"
            android.util.Slog.e(r4, r8, r3)     // Catch: java.lang.Throwable -> L73
            r9.mStorageService = r5     // Catch: java.lang.Throwable -> L73
        L91:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L73
            android.os.IStoraged r9 = r9.mStorageService
            if (r9 != 0) goto L98
            r9 = 1
            return r9
        L98:
            int r9 = r9.getRecentPerf()     // Catch: android.os.RemoteException -> L9d
            goto La6
        L9d:
            java.lang.String r9 = "StatsPullAtomService"
            java.lang.String r1 = "storaged not found"
            android.util.Slog.e(r9, r1)
            r9 = -1
        La6:
            android.util.StatsEvent r9 = com.android.internal.util.FrameworkStatsLog.buildStatsEvent(r10, r6, r0, r9)
            r11.add(r9)
            return r2
        Lae:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L73
            throw r9
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.stats.pull.StatsPullAtomService.pullDiskStatsLocked(int,"
                    + " java.util.List):int");
    }

    public final int pullExternalStorageInfoLocked(int i, List list) {
        StorageManager storageManager = this.mStorageManager;
        if (storageManager == null) {
            return 1;
        }
        for (VolumeInfo volumeInfo : storageManager.getVolumes()) {
            String environmentForState = VolumeInfo.getEnvironmentForState(volumeInfo.getState());
            DiskInfo disk = volumeInfo.getDisk();
            if (disk != null && environmentForState.equals("mounted")) {
                int i2 = 2;
                int i3 = volumeInfo.getType() == 0 ? 1 : volumeInfo.getType() == 1 ? 2 : 3;
                if (disk.isSd()) {
                    i2 = 1;
                } else if (!disk.isUsb()) {
                    i2 = 3;
                }
                list.add(FrameworkStatsLog.buildStatsEvent(i, i2, i3, disk.size));
            }
        }
        return 0;
    }

    public final int pullFaceSettingsLocked(int i, List list) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            UserManager userManager =
                    (UserManager) this.mContext.getSystemService(UserManager.class);
            if (userManager == null) {
                return 1;
            }
            List users = userManager.getUsers();
            int size = users.size();
            for (int i2 = 0; i2 < size; i2++) {
                int identifier = ((UserInfo) users.get(i2)).getUserHandle().getIdentifier();
                list.add(
                        FrameworkStatsLog.buildStatsEvent(
                                i,
                                Settings.Secure.getIntForUser(
                                                this.mContext.getContentResolver(),
                                                "face_unlock_keyguard_enabled",
                                                1,
                                                identifier)
                                        != 0,
                                Settings.Secure.getIntForUser(
                                                this.mContext.getContentResolver(),
                                                "face_unlock_dismisses_keyguard",
                                                1,
                                                identifier)
                                        != 0,
                                Settings.Secure.getIntForUser(
                                                this.mContext.getContentResolver(),
                                                "face_unlock_attention_required",
                                                0,
                                                identifier)
                                        != 0,
                                Settings.Secure.getIntForUser(
                                                this.mContext.getContentResolver(),
                                                "face_unlock_app_enabled",
                                                1,
                                                identifier)
                                        != 0,
                                Settings.Secure.getIntForUser(
                                                this.mContext.getContentResolver(),
                                                "face_unlock_always_require_confirmation",
                                                0,
                                                identifier)
                                        != 0,
                                Settings.Secure.getIntForUser(
                                                this.mContext.getContentResolver(),
                                                "face_unlock_diversity_required",
                                                1,
                                                identifier)
                                        != 0));
            }
            return 0;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final int pullHealthHalLocked(int i, List list) {
        int i2;
        HealthServiceWrapper healthServiceWrapper = this.mHealthService;
        if (healthServiceWrapper == null) {
            return 1;
        }
        try {
            HealthInfo healthInfo = healthServiceWrapper.getHealthInfo();
            if (healthInfo == null) {
                return 1;
            }
            if (i == 10019) {
                i2 = healthInfo.batteryChargeCounterUah;
            } else if (i == 10020) {
                i2 = healthInfo.batteryFullChargeUah;
            } else if (i == 10030) {
                i2 = healthInfo.batteryVoltageMillivolts;
            } else if (i == 10043) {
                i2 = healthInfo.batteryLevel;
            } else {
                if (i != 10045) {
                    return 1;
                }
                i2 = healthInfo.batteryCycleCount;
            }
            list.add(FrameworkStatsLog.buildStatsEvent(i, i2));
            return 0;
        } catch (RemoteException | IllegalStateException unused) {
            return 1;
        }
    }

    public final int pullInstalledIncrementalPackagesLocked(int i, List list) {
        PackageManager packageManager = this.mContext.getPackageManager();
        PackageManagerInternal packageManagerInternal =
                (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        if (!packageManager.hasSystemFeature("android.software.incremental_delivery")) {
            return 0;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                for (int i2 :
                        ((UserManagerInternal) LocalServices.getService(UserManagerInternal.class))
                                .getUserIds()) {
                    for (PackageInfo packageInfo :
                            packageManager.getInstalledPackagesAsUser(0, i2)) {
                        if (IncrementalManager.isIncrementalPath(
                                packageInfo.applicationInfo.getBaseCodePath())) {
                            IncrementalStatesInfo incrementalStatesInfo =
                                    packageManagerInternal.getIncrementalStatesInfo(
                                            1000, i2, packageInfo.packageName);
                            list.add(
                                    FrameworkStatsLog.buildStatsEvent(
                                            i,
                                            packageInfo.applicationInfo.uid,
                                            incrementalStatesInfo.isLoading(),
                                            incrementalStatesInfo.getLoadingCompletedTime()));
                        }
                    }
                }
                return 0;
            } catch (Exception e) {
                Slog.e(
                        "StatsPullAtomService",
                        "failed to pullInstalledIncrementalPackagesLocked",
                        e);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return 1;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void pullKernelWakelockLocked(int i, List list) {
        for (Map.Entry entry :
                this.mKernelWakelockReader
                        .readKernelWakelockStats(this.mTmpWakelockStats)
                        .entrySet()) {
            String str = (String) entry.getKey();
            KernelWakelockStats.Entry entry2 = (KernelWakelockStats.Entry) entry.getValue();
            list.add(
                    FrameworkStatsLog.buildStatsEvent(
                            i, str, entry2.count, entry2.version, entry2.totalTimeUs));
        }
    }

    public final int pullKeystoreAtoms(int i, List list) {
        IKeystoreMetrics iKeystoreMetricsService = getIKeystoreMetricsService();
        if (iKeystoreMetricsService == null) {
            Slog.w("StatsPullAtomService", "Keystore service is null");
            return 1;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            KeystoreAtom[] pullMetrics = iKeystoreMetricsService.pullMetrics(i);
            if (i == 10103) {
                return parseKeystoreStorageStats(pullMetrics, list);
            }
            switch (i) {
                case FrameworkStatsLog.KEYSTORE2_KEY_CREATION_WITH_GENERAL_INFO /* 10118 */:
                    return parseKeystoreKeyCreationWithGeneralInfo(pullMetrics, list);
                case FrameworkStatsLog.KEYSTORE2_KEY_CREATION_WITH_AUTH_INFO /* 10119 */:
                    return parseKeystoreKeyCreationWithAuthInfo(pullMetrics, list);
                case FrameworkStatsLog
                        .KEYSTORE2_KEY_CREATION_WITH_PURPOSE_AND_MODES_INFO /* 10120 */:
                    return parseKeystoreKeyCreationWithPurposeModesInfo(pullMetrics, list);
                case FrameworkStatsLog.KEYSTORE2_ATOM_WITH_OVERFLOW /* 10121 */:
                    return parseKeystoreAtomWithOverflow(pullMetrics, list);
                case FrameworkStatsLog
                        .KEYSTORE2_KEY_OPERATION_WITH_PURPOSE_AND_MODES_INFO /* 10122 */:
                    return parseKeystoreKeyOperationWithPurposeModesInfo(pullMetrics, list);
                case FrameworkStatsLog.KEYSTORE2_KEY_OPERATION_WITH_GENERAL_INFO /* 10123 */:
                    return parseKeystoreKeyOperationWithGeneralInfo(pullMetrics, list);
                case FrameworkStatsLog.RKP_ERROR_STATS /* 10124 */:
                    return parseRkpErrorStats(pullMetrics, list);
                case FrameworkStatsLog.KEYSTORE2_CRASH_STATS /* 10125 */:
                    return parseKeystoreCrashStats(pullMetrics, list);
                default:
                    Slog.w("StatsPullAtomService", "Unsupported keystore atom: " + i);
                    return 1;
            }
        } catch (ServiceSpecificException e) {
            Slog.e("StatsPullAtomService", "pulling keystore metrics failed", e);
            return 1;
        } catch (RemoteException e2) {
            Slog.e("StatsPullAtomService", "Disconnected from keystore service. Cannot pull.", e2);
            return 1;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final int pullMediaCapabilitiesStats(int i, List list) {
        AudioManager audioManager;
        int i2;
        boolean z;
        int i3;
        byte[] bArr;
        int i4;
        byte[] bArr2;
        int i5;
        if (!this.mContext.getPackageManager().hasSystemFeature("android.software.leanback")
                || (audioManager =
                                (AudioManager) this.mContext.getSystemService(AudioManager.class))
                        == null) {
            return 1;
        }
        Map surroundFormats = audioManager.getSurroundFormats();
        byte[] bytes = toBytes(new ArrayList(surroundFormats.keySet()));
        byte[] bytes2 = toBytes(audioManager.getReportedSurroundFormats());
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (Integer num : surroundFormats.keySet()) {
            if (audioManager.isSurroundFormatEnabled(num.intValue())) {
                arrayList2.add(num);
            } else {
                arrayList.add(num);
            }
        }
        byte[] bytes3 = toBytes(arrayList);
        byte[] bytes4 = toBytes(arrayList2);
        int encodedSurroundMode = audioManager.getEncodedSurroundMode();
        DisplayManager displayManager =
                (DisplayManager) this.mContext.getSystemService(DisplayManager.class);
        Display display = displayManager.getDisplay(0);
        Display.HdrCapabilities hdrCapabilities = display.getHdrCapabilities();
        byte[] bytes5 =
                hdrCapabilities != null
                        ? toBytes(hdrCapabilities.getSupportedHdrTypes())
                        : new byte[0];
        Display.Mode[] supportedModes = display.getSupportedModes();
        ArrayMap arrayMap = new ArrayMap();
        int length = supportedModes.length;
        int i6 = 1;
        int i7 = 0;
        while (i7 < length) {
            Display.Mode mode = supportedModes[i7];
            if (arrayMap.containsKey(Integer.valueOf(mode.getModeId()))) {
                i3 = length;
                bArr = bytes4;
                i4 = encodedSurroundMode;
                bArr2 = bytes5;
            } else {
                arrayMap.put(Integer.valueOf(mode.getModeId()), Integer.valueOf(i6));
                float[] alternativeRefreshRates = mode.getAlternativeRefreshRates();
                int length2 = alternativeRefreshRates.length;
                i3 = length;
                int i8 = 0;
                while (i8 < length2) {
                    int i9 = length2;
                    float f = alternativeRefreshRates[i8];
                    float[] fArr = alternativeRefreshRates;
                    int physicalWidth = mode.getPhysicalWidth();
                    byte[] bArr3 = bytes5;
                    int physicalHeight = mode.getPhysicalHeight();
                    int i10 = encodedSurroundMode;
                    int length3 = supportedModes.length;
                    byte[] bArr4 = bytes4;
                    int i11 = 0;
                    while (true) {
                        if (i11 >= length3) {
                            i5 = -1;
                            break;
                        }
                        int i12 = length3;
                        Display.Mode mode2 = supportedModes[i11];
                        if (mode2.matches(physicalWidth, physicalHeight, f)) {
                            i5 = mode2.getModeId();
                            break;
                        }
                        i11++;
                        length3 = i12;
                    }
                    if (i5 != -1 && !arrayMap.containsKey(Integer.valueOf(i5))) {
                        arrayMap.put(Integer.valueOf(i5), Integer.valueOf(i6));
                    }
                    i8++;
                    length2 = i9;
                    alternativeRefreshRates = fArr;
                    bytes5 = bArr3;
                    encodedSurroundMode = i10;
                    bytes4 = bArr4;
                }
                bArr = bytes4;
                i4 = encodedSurroundMode;
                bArr2 = bytes5;
                i6++;
            }
            i7++;
            length = i3;
            bytes5 = bArr2;
            encodedSurroundMode = i4;
            bytes4 = bArr;
        }
        byte[] bArr5 = bytes4;
        int i13 = encodedSurroundMode;
        byte[] bArr6 = bytes5;
        ProtoOutputStream protoOutputStream = new ProtoOutputStream();
        for (Display.Mode mode3 : supportedModes) {
            ProtoOutputStream protoOutputStream2 = new ProtoOutputStream();
            protoOutputStream2.write(1120986464257L, mode3.getPhysicalHeight());
            protoOutputStream2.write(1120986464258L, mode3.getPhysicalWidth());
            protoOutputStream2.write(1108101562371L, mode3.getRefreshRate());
            arrayMap = arrayMap;
            protoOutputStream2.write(
                    1120986464260L,
                    ((Integer) arrayMap.get(Integer.valueOf(mode3.getModeId()))).intValue());
            protoOutputStream.write(2246267895809L, protoOutputStream2.getBytes());
        }
        byte[] bytes6 = protoOutputStream.getBytes();
        List<UUID> supportedCryptoSchemes = MediaDrm.getSupportedCryptoSchemes();
        try {
            i2 =
                    !supportedCryptoSchemes.isEmpty()
                            ? new MediaDrm(supportedCryptoSchemes.get(0)).getConnectedHdcpLevel()
                            : -1;
        } catch (UnsupportedSchemeException e) {
            Slog.e("StatsPullAtomService", "pulling hdcp level failed.", e);
            i2 = -1;
        }
        int matchContentFrameRateUserPreference =
                displayManager.getMatchContentFrameRateUserPreference();
        byte[] bytes7 = toBytes(displayManager.getUserDisabledHdrTypes());
        Display.Mode globalUserPreferredDisplayMode =
                displayManager.getGlobalUserPreferredDisplayMode();
        int physicalWidth2 =
                globalUserPreferredDisplayMode != null
                        ? globalUserPreferredDisplayMode.getPhysicalWidth()
                        : -1;
        int physicalHeight2 =
                globalUserPreferredDisplayMode != null
                        ? globalUserPreferredDisplayMode.getPhysicalHeight()
                        : -1;
        float refreshRate =
                globalUserPreferredDisplayMode != null
                        ? globalUserPreferredDisplayMode.getRefreshRate()
                        : FullScreenMagnificationGestureHandler.MAX_SCALE;
        try {
            z =
                    Settings.Secure.getIntForUser(
                                    this.mContext.getContentResolver(),
                                    "minimal_post_processing_allowed",
                                    1)
                            == 0;
        } catch (Settings.SettingNotFoundException e2) {
            Slog.e(
                    "StatsPullAtomService",
                    "unable to find setting for MINIMAL_POST_PROCESSING_ALLOWED.",
                    e2);
            z = false;
        }
        list.add(
                FrameworkStatsLog.buildStatsEvent(
                        i,
                        bytes,
                        bytes2,
                        bytes3,
                        bArr5,
                        i13,
                        bArr6,
                        bytes6,
                        i2,
                        matchContentFrameRateUserPreference,
                        bytes7,
                        physicalWidth2,
                        physicalHeight2,
                        refreshRate,
                        z));
        return 0;
    }

    public final int pullModemActivityInfoLocked(int i, List list) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            final CompletableFuture completableFuture = new CompletableFuture();
            this.mTelephony.requestModemActivityInfo(
                    new SystemServerInitThreadPool$$ExternalSyntheticLambda0(),
                    new OutcomeReceiver() { // from class:
                                            // com.android.server.stats.pull.StatsPullAtomService.3
                        @Override // android.os.OutcomeReceiver
                        public final void onError(Throwable th) {
                            Slog.w(
                                    "StatsPullAtomService",
                                    "error reading modem stats:"
                                            + ((TelephonyManager.ModemActivityInfoException) th));
                            completableFuture.complete(null);
                        }

                        @Override // android.os.OutcomeReceiver
                        public final void onResult(Object obj) {
                            completableFuture.complete((ModemActivityInfo) obj);
                        }
                    });
            ModemActivityInfo modemActivityInfo =
                    (ModemActivityInfo) completableFuture.get(2000L, TimeUnit.MILLISECONDS);
            if (modemActivityInfo == null) {
                return 1;
            }
            list.add(
                    FrameworkStatsLog.buildStatsEvent(
                            i,
                            modemActivityInfo.getTimestampMillis(),
                            modemActivityInfo.getSleepTimeMillis(),
                            modemActivityInfo.getIdleTimeMillis(),
                            modemActivityInfo.getTransmitDurationMillisAtPowerLevel(0),
                            modemActivityInfo.getTransmitDurationMillisAtPowerLevel(1),
                            modemActivityInfo.getTransmitDurationMillisAtPowerLevel(2),
                            modemActivityInfo.getTransmitDurationMillisAtPowerLevel(3),
                            modemActivityInfo.getTransmitDurationMillisAtPowerLevel(4),
                            modemActivityInfo.getReceiveTimeMillis(),
                            -1L));
            return 0;
        } catch (InterruptedException | TimeoutException e) {
            Slog.w("StatsPullAtomService", "timeout or interrupt reading modem stats: " + e);
            return 1;
        } catch (ExecutionException e2) {
            Slog.w("StatsPullAtomService", "exception reading modem stats: " + e2.getCause());
            return 1;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final int pullNotificationRemoteViewsLocked(int i, List list) {
        synchronized (this.mNotificationStatsLock) {
            try {
                if (this.mNotificationManagerService == null) {
                    this.mNotificationManagerService =
                            INotificationManager.Stub.asInterface(
                                    ServiceManager.getService("notification"));
                }
                INotificationManager iNotificationManager = this.mNotificationManagerService;
                if (iNotificationManager != null) {
                    try {
                        iNotificationManager
                                .asBinder()
                                .linkToDeath(
                                        new StatsPullAtomService$$ExternalSyntheticLambda6(this, 1),
                                        0);
                    } catch (RemoteException e) {
                        Slog.e(
                                "StatsPullAtomService",
                                "linkToDeath with notificationManager failed",
                                e);
                        this.mNotificationManagerService = null;
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        INotificationManager iNotificationManager2 = this.mNotificationManagerService;
        if (iNotificationManager2 == null) {
            return 1;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            long currentTimeMicro =
                    (SystemClock.currentTimeMicro() * 1000)
                            - TimeUnit.NANOSECONDS.convert(1L, TimeUnit.DAYS);
            ArrayList arrayList = new ArrayList();
            iNotificationManager2.pullStats(currentTimeMicro, 1, true, arrayList);
            if (arrayList.size() != 1) {
                return 1;
            }
            unpackStreamedData(i, list, arrayList);
            return 0;
        } catch (RemoteException e2) {
            Slog.e("StatsPullAtomService", "Getting notistats failed: ", e2);
            return 1;
        } catch (IOException e3) {
            Slog.e("StatsPullAtomService", "Getting notistats failed: ", e3);
            return 1;
        } catch (SecurityException e4) {
            Slog.e("StatsPullAtomService", "Getting notistats failed: ", e4);
            return 1;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void pullPowerProfileLocked(int i, List list) {
        PowerProfile powerProfile = new PowerProfile(this.mContext);
        ProtoOutputStream protoOutputStream = new ProtoOutputStream();
        powerProfile.dumpDebug(protoOutputStream);
        protoOutputStream.flush();
        list.add(FrameworkStatsLog.buildStatsEvent(i, protoOutputStream.getBytes()));
    }

    public final void pullProcessCpuTimeLocked(int i, List list) {
        if (this.mProcessCpuTracker == null) {
            ProcessCpuTracker processCpuTracker = new ProcessCpuTracker(false);
            this.mProcessCpuTracker = processCpuTracker;
            processCpuTracker.init();
        }
        this.mProcessCpuTracker.update();
        for (int i2 = 0; i2 < this.mProcessCpuTracker.countStats(); i2++) {
            ProcessCpuTracker.Stats stats = this.mProcessCpuTracker.getStats(i2);
            list.add(
                    FrameworkStatsLog.buildStatsEvent(
                            i, stats.uid, stats.name, stats.base_utime, stats.base_stime));
        }
    }

    public final int pullRoleHolderLocked(int i, List list) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            PackageManager packageManager = this.mContext.getPackageManager();
            RoleManagerLocal roleManagerLocal =
                    (RoleManagerLocal) LocalManagerRegistry.getManager(RoleManagerLocal.class);
            List users =
                    ((UserManager) this.mContext.getSystemService(UserManager.class)).getUsers();
            int size = users.size();
            for (int i2 = 0; i2 < size; i2++) {
                int identifier = ((UserInfo) users.get(i2)).getUserHandle().getIdentifier();
                for (Map.Entry entry : roleManagerLocal.getRolesAndHolders(identifier).entrySet()) {
                    String str = (String) entry.getKey();
                    Set<String> set = (Set) entry.getValue();
                    if (set.isEmpty()) {
                        list.add(FrameworkStatsLog.buildStatsEvent(i, -1, "", str));
                    } else {
                        for (String str2 : set) {
                            try {
                                list.add(
                                        FrameworkStatsLog.buildStatsEvent(
                                                i,
                                                packageManager.getPackageInfoAsUser(
                                                                str2, 0, identifier)
                                                        .applicationInfo
                                                        .uid,
                                                str2,
                                                str));
                            } catch (PackageManager.NameNotFoundException unused) {
                                Slog.w(
                                        "StatsPullAtomService",
                                        "Role holder " + str2 + " not found");
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                                return 1;
                            }
                        }
                    }
                }
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return 0;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final int pullRuntimeAppOpAccessMessageLocked(int i, List list) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            RuntimeAppOpAccessMessage collectRuntimeAppOpAccessMessage =
                    ((AppOpsManager) this.mContext.getSystemService(AppOpsManager.class))
                            .collectRuntimeAppOpAccessMessage();
            if (collectRuntimeAppOpAccessMessage == null) {
                Slog.i("StatsPullAtomService", "No runtime appop access message collected");
                return 0;
            }
            list.add(
                    FrameworkStatsLog.buildStatsEvent(
                            i,
                            collectRuntimeAppOpAccessMessage.getUid(),
                            collectRuntimeAppOpAccessMessage.getPackageName(),
                            "",
                            collectRuntimeAppOpAccessMessage.getAttributionTag() == null
                                    ? ""
                                    : collectRuntimeAppOpAccessMessage.getAttributionTag(),
                            collectRuntimeAppOpAccessMessage.getMessage(),
                            collectRuntimeAppOpAccessMessage.getSamplingStrategy(),
                            AppOpsManager.strOpToOp(collectRuntimeAppOpAccessMessage.getOp())));
            return 0;
        } catch (Throwable th) {
            try {
                Slog.e("StatsPullAtomService", "Could not read runtime appop access message", th);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return 1;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public final int pullSettingsStatsLocked(int i, List list) {
        UserManager userManager = (UserManager) this.mContext.getSystemService(UserManager.class);
        if (userManager == null) {
            return 1;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                Iterator it = userManager.getUsers().iterator();
                while (it.hasNext()) {
                    int identifier = ((UserInfo) it.next()).getUserHandle().getIdentifier();
                    if (identifier == 0) {
                        list.addAll(SettingsStatsUtil.logGlobalSettings(this.mContext, i, 0));
                    }
                    list.addAll(SettingsStatsUtil.logSystemSettings(this.mContext, i, identifier));
                    list.addAll(SettingsStatsUtil.logSecureSettings(this.mContext, i, identifier));
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return 0;
            } catch (Exception e) {
                Slog.e("StatsPullAtomService", "failed to pullSettingsStats", e);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return 1;
            }
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final int pullTemperatureLocked(int i, List list) {
        IThermalService iThermalService = getIThermalService();
        if (iThermalService == null) {
            return 1;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            for (Temperature temperature : iThermalService.getCurrentTemperatures()) {
                list.add(
                        FrameworkStatsLog.buildStatsEvent(
                                i,
                                temperature.getType(),
                                temperature.getName(),
                                (int) (temperature.getValue() * 10.0f),
                                temperature.getStatus()));
            }
            return 0;
        } catch (RemoteException unused) {
            Slog.e(
                    "StatsPullAtomService",
                    "Disconnected from thermal service. Cannot pull temperatures.");
            return 1;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final int pullUwbActivityInfoLocked(int i, List list) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            SynchronousResultReceiver synchronousResultReceiver =
                    new SynchronousResultReceiver("uwb");
            this.mUwbManager.getUwbActivityEnergyInfoAsync(
                    new SystemServerInitThreadPool$$ExternalSyntheticLambda0(),
                    new StatsPullAtomService$$ExternalSyntheticLambda11(
                            2, synchronousResultReceiver));
            UwbActivityEnergyInfo awaitControllerInfo =
                    awaitControllerInfo(synchronousResultReceiver);
            if (awaitControllerInfo == null) {
                return 1;
            }
            list.add(
                    FrameworkStatsLog.buildStatsEvent(
                            i,
                            awaitControllerInfo.getControllerTxDurationMillis(),
                            awaitControllerInfo.getControllerRxDurationMillis(),
                            awaitControllerInfo.getControllerIdleDurationMillis(),
                            awaitControllerInfo.getControllerWakeCount()));
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return 0;
        } catch (RuntimeException e) {
            Slog.e("StatsPullAtomService", "failed to getUwbActivityEnergyInfoAsync", e);
            return 1;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final int pullWifiActivityInfoLocked(int i, List list) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            final SynchronousResultReceiver synchronousResultReceiver =
                    new SynchronousResultReceiver("wifi");
            this.mWifiManager.getWifiActivityEnergyInfoAsync(
                    new AnonymousClass2(),
                    new WifiManager
                            .OnWifiActivityEnergyInfoListener() { // from class:
                                                                  // com.android.server.stats.pull.StatsPullAtomService$$ExternalSyntheticLambda12
                        public final void onWifiActivityEnergyInfo(
                                WifiActivityEnergyInfo wifiActivityEnergyInfo) {
                            SynchronousResultReceiver synchronousResultReceiver2 =
                                    synchronousResultReceiver;
                            int i2 = StatsPullAtomService.RANDOM_SEED;
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("controller_activity", wifiActivityEnergyInfo);
                            synchronousResultReceiver2.send(0, bundle);
                        }
                    });
            WifiActivityEnergyInfo awaitControllerInfo =
                    awaitControllerInfo(synchronousResultReceiver);
            if (awaitControllerInfo == null) {
                return 1;
            }
            list.add(
                    FrameworkStatsLog.buildStatsEvent(
                            i,
                            awaitControllerInfo.getTimeSinceBootMillis(),
                            awaitControllerInfo.getStackState(),
                            awaitControllerInfo.getControllerTxDurationMillis(),
                            awaitControllerInfo.getControllerRxDurationMillis(),
                            awaitControllerInfo.getControllerIdleDurationMillis(),
                            awaitControllerInfo.getControllerEnergyUsedMicroJoules()));
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return 0;
        } catch (RuntimeException e) {
            Slog.e("StatsPullAtomService", "failed to getWifiActivityEnergyInfoAsync", e);
            return 1;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final long readProcStatsHighWaterMark(final int i) {
        try {
            File[] listFiles =
                    this.mBaseDir.listFiles(
                            new FilenameFilter() { // from class:
                                                   // com.android.server.stats.pull.StatsPullAtomService$$ExternalSyntheticLambda23
                                @Override // java.io.FilenameFilter
                                public final boolean accept(File file, String str) {
                                    StatsPullAtomService statsPullAtomService =
                                            StatsPullAtomService.this;
                                    int i2 = i;
                                    int i3 = StatsPullAtomService.RANDOM_SEED;
                                    statsPullAtomService.getClass();
                                    return str.toLowerCase()
                                            .startsWith(
                                                    StatsPullAtomService.highWaterMarkFilePrefix(i2)
                                                            + '_');
                                }
                            });
            if (listFiles != null && listFiles.length != 0) {
                if (listFiles.length > 1) {
                    Slog.e(
                            "StatsPullAtomService",
                            "Only 1 file expected for high water mark. Found " + listFiles.length);
                }
                return Long.valueOf(listFiles[0].getName().split("_")[1]).longValue();
            }
            return 0L;
        } catch (NumberFormatException e) {
            Slog.e("StatsPullAtomService", "Failed to parse file name.", e);
            return 0L;
        } catch (SecurityException e2) {
            Slog.e("StatsPullAtomService", "Failed to get procstats high watermark file.", e2);
            return 0L;
        }
    }

    public final int sampleAppOps(int i, List list, List list2, int i2) {
        int i3;
        ArrayList arrayList;
        int i4;
        StatsEvent buildStatsEvent;
        int i5 = i;
        List list3 = list;
        int i6 = i2;
        ArrayList arrayList2 = (ArrayList) list2;
        int size = arrayList2.size();
        int i7 = 0;
        while (i7 < size) {
            AppOpEntry appOpEntry = (AppOpEntry) arrayList2.get(i7);
            if (appOpEntry.mHash >= i6) {
                i3 = i7;
                arrayList = arrayList2;
                i4 = size;
            } else {
                if (i5 == 10075) {
                    i3 = i7;
                    arrayList = arrayList2;
                    i4 = size;
                    buildStatsEvent =
                            FrameworkStatsLog.buildStatsEvent(
                                    i,
                                    appOpEntry.mUid,
                                    appOpEntry.mPackageName,
                                    appOpEntry.mAttributionTag,
                                    appOpEntry.mOp.getOpCode(),
                                    appOpEntry.mOp.getForegroundAccessCount(9),
                                    appOpEntry.mOp.getBackgroundAccessCount(9),
                                    appOpEntry.mOp.getForegroundRejectCount(9),
                                    appOpEntry.mOp.getBackgroundRejectCount(9),
                                    appOpEntry.mOp.getForegroundAccessDuration(9),
                                    appOpEntry.mOp.getBackgroundAccessDuration(9),
                                    this.mDangerousAppOpsList.contains(
                                            Integer.valueOf(appOpEntry.mOp.getOpCode())),
                                    i2);
                } else {
                    i3 = i7;
                    arrayList = arrayList2;
                    i4 = size;
                    buildStatsEvent =
                            FrameworkStatsLog.buildStatsEvent(
                                    i,
                                    appOpEntry.mUid,
                                    appOpEntry.mPackageName,
                                    appOpEntry.mOp.getOpCode(),
                                    appOpEntry.mOp.getForegroundAccessCount(9),
                                    appOpEntry.mOp.getBackgroundAccessCount(9),
                                    appOpEntry.mOp.getForegroundRejectCount(9),
                                    appOpEntry.mOp.getBackgroundRejectCount(9),
                                    appOpEntry.mOp.getForegroundAccessDuration(9),
                                    appOpEntry.mOp.getBackgroundAccessDuration(9),
                                    this.mDangerousAppOpsList.contains(
                                            Integer.valueOf(appOpEntry.mOp.getOpCode())));
                }
                list3 = list;
                list3.add(buildStatsEvent);
            }
            i7 = i3 + 1;
            i5 = i;
            i6 = i2;
            arrayList2 = arrayList;
            size = i4;
        }
        if (list.size() <= 800) {
            return i2;
        }
        int constrain = MathUtils.constrain((i2 * 500) / list.size(), 0, i2 - 1);
        list.clear();
        return sampleAppOps(i, list3, list2, constrain);
    }
}
