package com.samsung.accessory.manager.authentication.cover;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.os.Bundle;
import android.os.Debug;
import android.os.FileUtils;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.util.TimeUtils;

import com.android.internal.telephony.ISemTelephony;
import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;
import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.ServiceKeeper$$ExternalSyntheticOutline0;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.UiModeManagerService$13$$ExternalSyntheticOutline0;
import com.android.server.VpnManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.FlashNotificationsController$$ExternalSyntheticOutline0;
import com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0;
import com.android.server.am.mars.MARsFreezeStateRecord$$ExternalSyntheticOutline0;
import com.android.server.input.InputManagerService;

import libcore.io.IoUtils;

import com.samsung.accessory.manager.DetachCheck;
import com.samsung.accessory.manager.SAccessoryManager;
import com.samsung.accessory.manager.authentication.AuthenticationResult;
import com.samsung.accessory.manager.authentication.AuthenticationSession;
import com.samsung.accessory.manager.authentication.CertBlocklister;
import com.samsung.accessory.manager.authentication.LocalAuthenticator;
import com.samsung.android.cover.CoverManager;
import com.samsung.android.cover.CoverState;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.sepunion.SemUnionManagerLocal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class CoverAuthenticator extends LocalAuthenticator
        implements SAccessoryManager.AuthenticationResultCallback,
                RestrictionPolicyOberver.NfcRestrictionPolicyListener,
                CertBlocklister.CertBlocklistListener {
    public final SAccessoryManager.AnonymousClass1 mAuthenticationTask;
    public CertBlocklister mBlocklister;
    public final Context mContext;
    public final CoverAuthHandler mCoverAttachProcessHanlder;
    public final CoverAuthHandler mCoverAuthHandler;
    public CoverAuthStateFile mCoverAuthStateFile;
    public PowerManager.WakeLock mCoverAuthWakeLock;
    public CoverBroadcaster mCoverBroadcaster;
    public final CoverManager mCoverManager;
    public final DetachCheck mDetachCheck;
    public final AnonymousClass5 mFactoryReceiver;
    public final InputManagerService mInputManager;
    public PaletteCover mPaletteCover;
    public PowerManager mPowerManager;
    public RestrictionPolicyOberver mRestrictionPolicyOberver;
    public PowerManager.WakeLock mSafetyDetachTimeoutWakeLock;
    public final AnonymousClass5 mScheduledAuthReceiver;
    public SemUnionManagerLocal mSemUnionManagerLocal;
    public Sensor mSensor;
    public static final boolean DBG = Debug.semIsProductDev();
    public static final String RUN_SCHEDULED_AUTH_ACTION =
            CoverAuthenticator.class.getPackage().getName()
                    + ".action.AUTHENTICATION_INTERVAL_ELAPSED";
    public static boolean USE_SCHEDULED_AUTHENTICATION_WEHN_CHARGING = true;
    public static final String BRAND_NAME =
            SemFloatingFeature.getInstance()
                    .getString("SEC_FLOATING_FEATURE_SETTINGS_CONFIG_BRAND_NAME");
    public byte[] mUriData = null;
    public boolean mSystemReady = false;
    public boolean mNfcServiceReady = false;
    public volatile boolean mIsShutingdown = false;
    public volatile boolean mPreparing = false;
    public String modelName = "Samsung Mobile";
    public int currentHall = 0;
    public boolean mIsFactoryBinary = false;
    public boolean mFactoryTestRequested = false;
    public int mAuthType = -1;
    public int mCurrentPowerSouce = 0;
    public volatile long mLastAuthenticationTime = 0;
    public long mLastAttachTime = 0;
    public long mLastDetachTime = 0;
    public long mCoverAttachedWhenNanos = 0;
    public int mRetryCounterWhenBusy = 0;
    public int mRetryCounterFactoryTest = 0;
    public int mFailuresCount = 0;
    public Intent mFactoryIntent = null;
    public CoverState mLastCoverState = null;
    public final AtomicReference mResult = new AtomicReference();
    public final LinkedList mAuthenticationHistory = new LinkedList();
    public final LinkedList mSensorHistory = new LinkedList();
    public AuthenticationSession mCurrentSession = null;
    public int mCoverType = 2;
    public int mCoverStateBySensor = -1;
    public int mCoverStateByNfc = -1;
    public boolean mIsSensor = false;
    public final AnonymousClass8 mSensorEventListener =
            new SensorEventListener() { // from class:
                                        // com.samsung.accessory.manager.authentication.cover.CoverAuthenticator.8
                @Override // android.hardware.SensorEventListener
                public final void onAccuracyChanged(Sensor sensor, int i) {}

                @Override // android.hardware.SensorEventListener
                public final void onSensorChanged(SensorEvent sensorEvent) {
                    int i = (int) sensorEvent.values[0];
                    long currentTimeMillis = System.currentTimeMillis() * 1000;
                    boolean z = CoverAuthenticator.DBG;
                    UiModeManagerService$13$$ExternalSyntheticOutline0.m(
                            BatteryService$$ExternalSyntheticOutline0.m(
                                    i, "onSensorChanged: ", " "),
                            CoverAuthenticator.this.mCoverStateBySensor,
                            "SAccessoryManager_CoverAuthenticator");
                    if (i == 1) {
                        CoverAuthenticator coverAuthenticator = CoverAuthenticator.this;
                        if (coverAuthenticator.mCoverStateBySensor != 1) {
                            coverAuthenticator.onCoverAttached(currentTimeMillis, true, true);
                            CoverAuthenticator coverAuthenticator2 = CoverAuthenticator.this;
                            coverAuthenticator2.mCoverStateBySensor = i;
                            CoverAuthenticator.m1132$$Nest$maddSensorRecord(coverAuthenticator2, i);
                            return;
                        }
                    }
                    if (i == 0) {
                        CoverAuthenticator coverAuthenticator3 = CoverAuthenticator.this;
                        if (coverAuthenticator3.mCoverStateBySensor != 0) {
                            coverAuthenticator3.onCoverAttached(currentTimeMillis, false, true);
                            CoverAuthenticator coverAuthenticator4 = CoverAuthenticator.this;
                            coverAuthenticator4.mCoverStateBySensor = i;
                            CoverAuthenticator.m1132$$Nest$maddSensorRecord(coverAuthenticator4, i);
                        }
                    }
                }
            };

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.samsung.accessory.manager.authentication.cover.CoverAuthenticator$1, reason: invalid class name */
    public final class AnonymousClass1 implements Runnable {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ Object this$0;
        public final /* synthetic */ Object val$session;

        public /* synthetic */ AnonymousClass1(int i, Object obj, Object obj2) {
            this.$r8$classId = i;
            this.this$0 = obj;
            this.val$session = obj2;
        }

        @Override // java.lang.Runnable
        public final void run() {
            switch (this.$r8$classId) {
                case 0:
                    boolean z = CoverAuthenticator.DBG;
                    Log.i("SAccessoryManager_CoverAuthenticator", "onAuthenticationStarting");
                    if (((CoverAuthenticator) this.this$0).getCoverSwitchState() < 1) {
                        CoverAuthenticator coverAuthenticator = (CoverAuthenticator) this.this$0;
                        if (!coverAuthenticator.mIsFactoryBinary) {
                            coverAuthenticator.mAuthenticationTask.stop(
                                    (AuthenticationSession) this.val$session);
                            break;
                        }
                    }
                    ((CoverAuthenticator) this.this$0).mCurrentSession =
                            (AuthenticationSession) this.val$session;
                    break;
                default:
                    CoverAuthenticator coverAuthenticator2 = ((AnonymousClass5) this.this$0).this$0;
                    boolean z2 = CoverAuthenticator.DBG;
                    if (coverAuthenticator2.getCoverSwitchState() < 1
                            && (true
                                    ^ TextUtils.isEmpty(
                                            Settings.System.getString(
                                                    ((Context) this.val$session)
                                                            .getContentResolver(),
                                                    "accessory_cover_uri")))) {
                        CoverAuthenticator coverAuthenticator3 =
                                ((AnonymousClass5) this.this$0).this$0;
                        if (coverAuthenticator3.mPaletteCover == null) {
                            coverAuthenticator3.mPaletteCover =
                                    new PaletteCover((Context) this.val$session);
                        }
                        coverAuthenticator3.mPaletteCover.disableSetting();
                        break;
                    }
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.samsung.accessory.manager.authentication.cover.CoverAuthenticator$2, reason: invalid class name */
    public final class AnonymousClass2 implements Runnable {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ CoverAuthenticator this$0;

        public /* synthetic */ AnonymousClass2(CoverAuthenticator coverAuthenticator, int i) {
            this.$r8$classId = i;
            this.this$0 = coverAuthenticator;
        }

        /* JADX WARN: Code restructure failed: missing block: B:22:0x007b, code lost:

           if (com.samsung.accessory.manager.authentication.CertBlocklister.isThisKeyBlocklisted(r2) != false) goto L21;
        */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void run() {
            /*
                r7 = this;
                r0 = 0
                r2 = 5
                r3 = 0
                java.lang.String r4 = "SAccessoryManager_CoverAuthenticator"
                r5 = 1
                int r6 = r7.$r8$classId
                switch(r6) {
                    case 0: goto L8c;
                    case 1: goto L4f;
                    case 2: goto L1c;
                    default: goto Lc;
                }
            Lc:
                com.samsung.accessory.manager.authentication.cover.CoverAuthenticator r0 = r7.this$0
                int r0 = r0.getCoverSwitchState()
                if (r0 < r5) goto L1b
                com.samsung.accessory.manager.authentication.cover.CoverAuthenticator r7 = r7.this$0
                com.samsung.accessory.manager.authentication.cover.RestrictionPolicyOberver r7 = r7.mRestrictionPolicyOberver
                r7.getClass()
            L1b:
                return
            L1c:
                boolean r5 = com.samsung.accessory.manager.authentication.cover.CoverAuthenticator.DBG
                java.lang.String r5 = "onAuthenticationStopped"
                android.util.Log.i(r4, r5)
                com.samsung.accessory.manager.authentication.cover.CoverAuthenticator r4 = r7.this$0
                r5 = 0
                r4.mCurrentSession = r5
                r4.mRetryCounterWhenBusy = r3
                android.content.Intent r4 = new android.content.Intent
                java.lang.String r5 = "com.samsung.android.intent.action.ACCESSORY_AUTHENTICATION_STOPPED"
                r4.<init>(r5)
                com.samsung.accessory.manager.authentication.cover.CoverAuthenticator r5 = r7.this$0
                android.content.Context r5 = r5.mContext
                r5.sendBroadcast(r4)
                com.samsung.accessory.manager.authentication.cover.CoverAuthenticator r4 = r7.this$0
                com.samsung.accessory.manager.authentication.cover.CoverAuthenticator$CoverAuthHandler r4 = r4.mCoverAuthHandler
                boolean r2 = r4.hasMessages(r2)
                if (r2 == 0) goto L49
                com.samsung.accessory.manager.authentication.cover.CoverAuthenticator r7 = r7.this$0
                r7.startAuthentication(r0, r3)
                goto L4e
            L49:
                com.samsung.accessory.manager.authentication.cover.CoverAuthenticator r7 = r7.this$0
                r0 = -1
                r7.mAuthType = r0
            L4e:
                return
            L4f:
                com.samsung.accessory.manager.authentication.cover.CoverAuthenticator r2 = r7.this$0
                java.util.concurrent.atomic.AtomicReference r2 = r2.mResult
                java.lang.Object r2 = r2.get()
                com.samsung.accessory.manager.authentication.AuthenticationResult r2 = (com.samsung.accessory.manager.authentication.AuthenticationResult) r2
                if (r2 == 0) goto L86
                int r3 = r2.mReason
                if (r3 != 0) goto L86
                com.samsung.accessory.manager.authentication.cover.CoverAuthenticator r3 = r7.this$0
                com.samsung.accessory.manager.authentication.CertBlocklister r3 = r3.mBlocklister
                java.lang.String r4 = r2.publicKey
                r3.getClass()
                boolean r3 = com.samsung.accessory.manager.authentication.CertBlocklister.isThisKeyBlocklisted(r4)
                if (r3 != 0) goto L7d
                com.samsung.accessory.manager.authentication.cover.CoverAuthenticator r3 = r7.this$0
                com.samsung.accessory.manager.authentication.CertBlocklister r3 = r3.mBlocklister
                java.lang.String r2 = r2.mStringManagerURI
                r3.getClass()
                boolean r2 = com.samsung.accessory.manager.authentication.CertBlocklister.isThisKeyBlocklisted(r2)
                if (r2 == 0) goto L86
            L7d:
                com.samsung.accessory.manager.authentication.cover.CoverAuthenticator r7 = r7.this$0
                com.samsung.accessory.manager.authentication.cover.CoverAuthenticator$CoverAuthHandler r7 = r7.mCoverAuthHandler
                r0 = 6
                r7.sendEmptyMessage(r0)
                goto L8b
            L86:
                com.samsung.accessory.manager.authentication.cover.CoverAuthenticator r7 = r7.this$0
                r7.startAuthentication(r0, r5)
            L8b:
                return
            L8c:
                boolean r0 = com.samsung.accessory.manager.authentication.cover.CoverAuthenticator.DBG
                java.lang.String r0 = "onAuthenticationStarted"
                android.util.Log.i(r4, r0)
                com.samsung.accessory.manager.authentication.cover.CoverAuthenticator r0 = r7.this$0
                boolean r0 = r0.mPreparing
                if (r0 == 0) goto Lb4
                com.samsung.accessory.manager.authentication.cover.CoverAuthenticator r0 = r7.this$0
                com.samsung.accessory.manager.authentication.cover.CoverAuthenticator$CoverAuthHandler r0 = r0.mCoverAuthHandler
                boolean r0 = r0.hasMessages(r2)
                if (r0 == 0) goto Lb4
                com.samsung.accessory.manager.authentication.cover.CoverAuthenticator r0 = r7.this$0
                r0.mPreparing = r3
                com.samsung.accessory.manager.authentication.cover.CoverAuthenticator r7 = r7.this$0
                r7.removeAuthenticationTimeOuts()
                com.samsung.accessory.manager.authentication.cover.CoverAuthenticator$CoverAuthHandler r7 = r7.mCoverAuthHandler
                r0 = 10000(0x2710, double:4.9407E-320)
                r7.sendEmptyMessageDelayed(r2, r0)
            Lb4:
                return
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.samsung.accessory.manager.authentication.cover.CoverAuthenticator.AnonymousClass2.run():void");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.samsung.accessory.manager.authentication.cover.CoverAuthenticator$5, reason: invalid class name */
    public final class AnonymousClass5 extends BroadcastReceiver {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ CoverAuthenticator this$0;

        public /* synthetic */ AnonymousClass5(CoverAuthenticator coverAuthenticator, int i) {
            this.$r8$classId = i;
            this.this$0 = coverAuthenticator;
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            int i = 1;
            switch (this.$r8$classId) {
                case 0:
                    boolean z = CoverAuthenticator.DBG;
                    Log.i("SAccessoryManager_CoverAuthenticator", "received " + intent.getAction());
                    CoverAuthenticator.m1133$$Nest$monNfcReady(this.this$0);
                    this.this$0.mContext.unregisterReceiver(this);
                    break;
                case 1:
                    boolean z2 = CoverAuthenticator.DBG;
                    Log.i("SAccessoryManager_CoverAuthenticator", "Received broadcast " + intent);
                    final String action = intent.getAction();
                    final String stringExtra = intent.getStringExtra("package_name");
                    if (stringExtra != null) {
                        this.this$0.mCoverAuthHandler.post(
                                new Runnable() { // from class:
                                    // com.samsung.accessory.manager.authentication.cover.CoverAuthenticator$12$1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        if (action.equals(
                                                "com.samsung.accessory.manager.action.AUTHENTICATION_STATE_REQUEST")) {
                                            CoverAuthenticator.AnonymousClass5.this
                                                            .this$0
                                                            .mFactoryIntent =
                                                    new Intent(
                                                            "com.samsung.accessory.manager.action.AUTHENTICATION_STATE_REPLY");
                                            CoverAuthenticator.AnonymousClass5.this.this$0
                                                    .mFactoryIntent.setPackage(stringExtra);
                                            CoverAuthenticator.AnonymousClass5.this.this$0
                                                    .sendFactoryResult();
                                            return;
                                        }
                                        if (action.equals(
                                                "com.samsung.accessory.manager.action.START_AUTHENTICATION")) {
                                            CoverAuthenticator.AnonymousClass5.this
                                                            .this$0
                                                            .mFactoryIntent =
                                                    new Intent(
                                                            "com.samsung.accessory.manager.action.AUTHENTICATION_RESULT");
                                            CoverAuthenticator.AnonymousClass5.this.this$0
                                                    .mFactoryIntent.setPackage(stringExtra);
                                            CoverAuthenticator coverAuthenticator =
                                                    CoverAuthenticator.AnonymousClass5.this.this$0;
                                            coverAuthenticator.mRetryCounterFactoryTest = 0;
                                            coverAuthenticator.mFactoryTestRequested = true;
                                            coverAuthenticator.startAuthentication(0L, false);
                                        }
                                    }
                                });
                        break;
                    } else {
                        StorageManagerService$$ExternalSyntheticOutline0.m(
                                "can't reply auth info, request package = ",
                                stringExtra,
                                "SAccessoryManager_CoverAuthenticator");
                        break;
                    }
                case 2:
                    boolean z3 = CoverAuthenticator.DBG;
                    Log.i("SAccessoryManager_CoverAuthenticator", "received " + intent.getAction());
                    CoverAuthenticator.m1133$$Nest$monNfcReady(this.this$0);
                    break;
                case 3:
                    String action2 = intent.getAction();
                    if (!"android.intent.action.BOOT_COMPLETED".equals(action2)) {
                        if ("android.intent.action.ACTION_SHUTDOWN".equals(action2)) {
                            this.this$0.mIsShutingdown = true;
                            this.this$0.stopAuthentication(0L);
                            break;
                        }
                    } else {
                        boolean z4 = CoverAuthenticator.DBG;
                        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                                "received ", action2, "SAccessoryManager_CoverAuthenticator");
                        this.this$0.getClass();
                        RestrictionPolicyOberver restrictionPolicyOberver =
                                this.this$0.mRestrictionPolicyOberver;
                        Log.i(
                                "SAccessoryManager_CoverAuthenticator",
                                "Cover Switch State  : " + this.this$0.getCoverSwitchState());
                        this.this$0.mCoverAuthHandler.postDelayed(
                                new AnonymousClass1(i, this, context), 5000L);
                        break;
                    }
                    break;
                default:
                    if (CoverAuthenticator.RUN_SCHEDULED_AUTH_ACTION.equals(intent.getAction())) {
                        Log.i(
                                "SAccessoryManager_CoverAuthenticator",
                                "Run scheduled authentication");
                        this.this$0.startAuthentication(0L, true);
                        break;
                    }
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CoverAuthHandler extends Handler {
        public final /* synthetic */ int $r8$classId = 1;

        public CoverAuthHandler() {}

        public CoverAuthHandler(Looper looper) {
            super(looper, null, true);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:26:0x009d  */
        /* JADX WARN: Removed duplicated region for block: B:29:0x00ac  */
        @Override // android.os.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void handleMessage(android.os.Message r17) {
            /*
                Method dump skipped, instructions count: 1518
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.samsung.accessory.manager.authentication.cover.CoverAuthenticator.CoverAuthHandler.handleMessage(android.os.Message):void");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CoverAuthStateFile {
        public final String TAG = CoverAuthStateFile.class.getSimpleName();
        public byte[] mExtraData;
        public byte[] mId;
        public final String mPath;
        public final File mTmpDir;
        public byte[] mUri;

        public CoverAuthStateFile() {
            String str = System.getenv("ANDROID_DATA") + "/misc/saccessory_manager/auth_state";
            this.mPath = str;
            this.mTmpDir = new File(str).getParentFile();
        }
    }

    /* renamed from: -$$Nest$maddSensorRecord, reason: not valid java name */
    public static void m1132$$Nest$maddSensorRecord(CoverAuthenticator coverAuthenticator, int i) {
        if (coverAuthenticator.mSensorHistory.size() > 60) {
            coverAuthenticator.mSensorHistory.removeFirst();
        }
        coverAuthenticator.mSensorHistory.add(
                String.valueOf(i) + "/" + TimeUtils.logTimeOfDay(System.currentTimeMillis()));
    }

    /* renamed from: -$$Nest$monNfcReady, reason: not valid java name */
    public static void m1133$$Nest$monNfcReady(CoverAuthenticator coverAuthenticator) {
        SensorManager sensorManager;
        coverAuthenticator.mNfcServiceReady = true;
        int coverSwitchState = coverAuthenticator.getCoverSwitchState();
        if (coverAuthenticator.isAuthenticationReady()) {
            if (coverSwitchState >= 1) {
                StringBuilder sb = new StringBuilder("ready to authenticate the cover ");
                sb.append(coverAuthenticator.mIsFactoryBinary);
                sb.append(" ");
                UiModeManagerService$13$$ExternalSyntheticOutline0.m(
                        sb, coverAuthenticator.currentHall, "SAccessoryManager_CoverAuthenticator");
                coverAuthenticator.mAuthType = 2;
                coverAuthenticator.mLastAttachTime = System.currentTimeMillis();
                coverAuthenticator.startAuthentication(0L, true);
            } else {
                DirEncryptService$$ExternalSyntheticOutline0.m(
                        coverSwitchState,
                        "ready to authenticate the cover but a cover attach state is ",
                        "SAccessoryManager_CoverAuthenticator");
            }
        }
        if (coverAuthenticator.mSensor != null
                || (sensorManager =
                                (SensorManager)
                                        coverAuthenticator.mContext.getSystemService("sensor"))
                        == null) {
            return;
        }
        Sensor defaultSensor = sensorManager.getDefaultSensor(65639);
        coverAuthenticator.mSensor = defaultSensor;
        if (defaultSensor != null) {
            Log.i("SAccessoryManager_CoverAuthenticator", "registerListener sensor");
            sensorManager.registerListener(
                    coverAuthenticator.mSensorEventListener, coverAuthenticator.mSensor, 3);
        }
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.samsung.accessory.manager.authentication.cover.CoverAuthenticator$8] */
    public CoverAuthenticator(
            Context context,
            InputManagerService inputManagerService,
            Looper looper,
            SAccessoryManager.AnonymousClass1 anonymousClass1) {
        new AnonymousClass5(this, 4);
        this.mFactoryReceiver = new AnonymousClass5(this, 1);
        this.mContext = context;
        this.mInputManager = inputManagerService;
        this.mAuthenticationTask = anonymousClass1;
        this.mCoverAuthHandler = new CoverAuthHandler(looper);
        this.mCoverAttachProcessHanlder = new CoverAuthHandler();
        this.mCoverManager = new CoverManager(context);
        this.mDetachCheck = new DetachCheck(context);
    }

    public static void setTransmitPower(boolean z) {
        try {
            Log.i(
                    "SAccessoryManager_CoverAuthenticator",
                    "Call SemTelephony API : is verified :" + z);
            ISemTelephony.Stub.asInterface(ServiceManager.getService("isemtelephony"))
                    .setTransmitPowerExt(16384L, z);
        } catch (RemoteException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    public final void addRecord(int i, int i2) {
        if (this.mAuthenticationHistory.size() > 60) {
            this.mAuthenticationHistory.removeFirst();
        }
        LinkedList linkedList = this.mAuthenticationHistory;
        StringBuilder sb = new StringBuilder();
        sb.append(String.valueOf(i));
        sb.append("/");
        sb.append(this.mAuthType);
        sb.append("/");
        ServiceKeeper$$ExternalSyntheticOutline0.m(this.mCurrentPowerSouce, i2, "/", "/", sb);
        sb.append(this.currentHall);
        sb.append("/");
        sb.append(this.mCoverStateBySensor);
        sb.append("/");
        sb.append(TimeUtils.logTimeOfDay(System.currentTimeMillis()));
        linkedList.add(sb.toString());
    }

    @Override // com.samsung.accessory.manager.authentication.LocalAuthenticator
    public final void dump(PrintWriter printWriter) {
        printWriter.println(" Current CoverAuthenticator state:");
        AuthenticationResult authenticationResult = (AuthenticationResult) this.mResult.get();
        if (authenticationResult != null) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            printWriter,
                            authenticationResult.mStringManagerURI,
                            "  auth extra data = ",
                            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                    new StringBuilder("  auth reason = "),
                                    authenticationResult.mReason,
                                    printWriter,
                                    "  auth uri = ")),
                    authenticationResult.mStringExtraData,
                    printWriter);
        }
        printWriter.println("  Historical authentication: ");
        for (int i = 0; i < this.mAuthenticationHistory.size(); i++) {
            printWriter.println("    " + ((String) this.mAuthenticationHistory.get(i)));
        }
        if (this.mSensor != null) {
            printWriter.println("  Magnetic sensor: ");
            for (int i2 = 0; i2 < this.mSensorHistory.size(); i2++) {
                printWriter.println("    " + ((String) this.mSensorHistory.get(i2)));
            }
        }
        StringBuilder m =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                                BinaryTransparencyService$$ExternalSyntheticOutline0
                                                        .m(
                                                                new StringBuilder(
                                                                        "  mNfcServiceReady = "),
                                                                this.mNfcServiceReady,
                                                                printWriter,
                                                                "  mFactoryTestRequested = "),
                                                this.mFactoryTestRequested,
                                                printWriter,
                                                "  mLastAuthenticationTime = "),
                                        this.mLastAuthenticationTime,
                                        printWriter,
                                        "  isAuthenticatonAllowed? = "),
                                RestrictionPolicyOberver.sIsFelicaAllowed,
                                printWriter,
                                "  getBlockedType? = "),
                        RestrictionPolicyOberver.sIsFelicaAllowed ? 0 : 2,
                        printWriter,
                        "  isAuthenticationBlocked? = ");
        this.mBlocklister.getClass();
        StringBuilder m2 =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        m, CertBlocklister.mIsBlocked, printWriter, "  mLastAttachTime = ");
        m2.append(TimeUtils.logTimeOfDay(this.mLastAttachTime));
        printWriter.println(m2.toString());
        printWriter.println("  mLastDetachTime = " + TimeUtils.logTimeOfDay(this.mLastDetachTime));
        StringBuilder m3 =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                new StringBuilder("  mFailuresCount = "),
                                this.mFailuresCount,
                                printWriter,
                                "  mIsFactoryBinary = "),
                        this.mIsFactoryBinary,
                        printWriter,
                        "  mSensor = ");
        m3.append(this.mSensor);
        printWriter.println(m3.toString());
        StringBuilder m4 =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        new StringBuilder("  mIsSensor = "),
                        this.mIsSensor,
                        printWriter,
                        "  mLastCoverState = ");
        m4.append(this.mLastCoverState);
        printWriter.println(m4.toString());
        if (this.mCoverBroadcaster != null) {
            AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                    new StringBuilder("  mRealCoverType = "),
                    this.mCoverBroadcaster.mRealCoverType,
                    printWriter);
        }
    }

    public final int getCoverSwitchState() {
        int i = this.currentHall;
        InputManagerService inputManagerService = this.mInputManager;
        return i == 0
                ? (this.mSensor == null || !this.mIsSensor)
                        ? inputManagerService.mNative.getSwitchState(-1, -256, 27)
                        : this.mCoverStateBySensor
                : inputManagerService.mNative.getSwitchState(-1, -256, 21);
    }

    public final boolean isAuthenticationReady() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.mSystemReady);
        sb.append(" ");
        sb.append(this.mNfcServiceReady);
        sb.append(" ");
        FlashNotificationsController$$ExternalSyntheticOutline0.m(
                "SAccessoryManager_CoverAuthenticator", sb, this.mIsShutingdown);
        return this.mSystemReady && this.mNfcServiceReady && !this.mIsShutingdown;
    }

    public final void notifyFriendsStateChanged(boolean z, byte[] bArr, byte[] bArr2) {
        if (this.mSemUnionManagerLocal == null) {
            this.mSemUnionManagerLocal =
                    (SemUnionManagerLocal) LocalServices.getService(SemUnionManagerLocal.class);
        }
        if (this.mSemUnionManagerLocal != null) {
            if (this.mCoverType == 255 && this.mContext != null) {
                Intent intent = new Intent();
                intent.setAction("com.samsung.accessory.manager.action.SEND_ATTACH_INFORMATION");
                intent.putExtra("isAttached", z);
                this.mContext.sendBroadcast(intent);
            }
            this.mSemUnionManagerLocal.accessoryStateChanged(z, bArr, bArr2);
            try {
                Intent component =
                        new Intent()
                                .setComponent(
                                        new ComponentName(
                                                "com.sec.enterprise.knox.cloudmdm.smdms",
                                                "com.sec.enterprise.knox.cloudmdm.smdms.core.AccessoryStateChangeReceiver"));
                component.putExtra("accessoryType", this.mCoverType);
                component.putExtra("accessoryState", z ? 1001 : 1002);
                component.putExtra("accessoryUid", bArr);
                component.putExtra("from", "com.samsung.accessory.manager.authentication");
                this.mContext.sendBroadcast(component);
            } catch (Exception unused) {
                Log.d("SAccessoryManager_CoverAuthenticator", "error during starting KES service");
            }
        }
    }

    @Override // com.samsung.accessory.manager.SAccessoryManager.AuthenticationResultCallback
    public final void onAuthenticationComplted(AuthenticationResult authenticationResult) {
        CoverAuthHandler coverAuthHandler = this.mCoverAuthHandler;
        Message obtainMessage = coverAuthHandler.obtainMessage(3);
        obtainMessage.obj = authenticationResult;
        coverAuthHandler.sendMessage(obtainMessage);
    }

    @Override // com.samsung.accessory.manager.SAccessoryManager.AuthenticationResultCallback
    public final void onAuthenticationStarted() {
        this.mCoverAuthHandler.post(new AnonymousClass2(this, 0));
    }

    @Override // com.samsung.accessory.manager.SAccessoryManager.AuthenticationResultCallback
    public final void onAuthenticationStarting(AuthenticationSession authenticationSession) {
        this.mCoverAuthHandler.post(new AnonymousClass1(0, this, authenticationSession));
    }

    @Override // com.samsung.accessory.manager.SAccessoryManager.AuthenticationResultCallback
    public final void onAuthenticationStopped() {
        this.mCoverAuthHandler.post(new AnonymousClass2(this, 2));
    }

    public final void onCoverAttached(long j, boolean z, boolean z2) {
        this.currentHall = 0;
        StringBuilder sb = new StringBuilder("onCoverAttached ");
        BatteryService$$ExternalSyntheticOutline0.m(
                sb, this.mIsFactoryBinary, " ", z2, ",mSensor=");
        sb.append(this.mSensor);
        Log.i("SAccessoryManager_CoverAuthenticator", sb.toString());
        if (this.mIsFactoryBinary || !isAuthenticationReady()) {
            return;
        }
        CoverState coverState = this.mLastCoverState;
        if (coverState != null && coverState.attached && this.mIsSensor != z2) {
            Log.e("SAccessoryManager_CoverAuthenticator", "onCoverAttached event is skip.");
            addRecord(-3, 0);
            return;
        }
        this.mIsSensor = z2;
        if (z2 && this.mSensor == null) {
            Log.e("SAccessoryManager_CoverAuthenticator", "onCoverAttached: mSensor is null.");
            addRecord(-4, 0);
            return;
        }
        Message obtain = Message.obtain();
        Bundle bundle = new Bundle();
        bundle.putLong("when", j);
        obtain.setData(bundle);
        CoverAuthHandler coverAuthHandler = this.mCoverAttachProcessHanlder;
        if (z) {
            if (coverAuthHandler.hasMessages(1)
                    && this.mCoverManager.getCoverState() != null
                    && this.mCoverManager.getCoverState().attached) {
                coverAuthHandler.removeMessages(1);
                addRecord(-2, 0);
                return;
            } else {
                obtain.what = 0;
                coverAuthHandler.sendMessage(obtain);
                return;
            }
        }
        obtain.what = 1;
        obtain.obj = Boolean.valueOf(z2);
        if (z2) {
            this.mSafetyDetachTimeoutWakeLock.acquire(2000L);
        } else if (this.mPaletteCover != null
                && PaletteCover.isDataChanged(this.mContext, this.mUriData)) {
            this.mPaletteCover.disableSetting();
        }
        if (this.mPaletteCover == null
                || !(!TextUtils.isEmpty(
                        Settings.System.getString(
                                this.mContext.getContentResolver(), "accessory_cover_uri")))) {
            coverAuthHandler.sendMessageDelayed(obtain, 1000L);
        } else {
            coverAuthHandler.sendMessageDelayed(obtain, 50L);
        }
    }

    public final void removeAuthenticationTimeOuts() {
        this.mPreparing = false;
        this.mCoverAuthHandler.removeMessages(5);
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x005c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void sendCoverStateToSensorhub(char r5) {
        /*
            r4 = this;
            java.lang.String r0 = "File write fail "
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "sendCoverStateToSensorhub: "
            r1.<init>(r2)
            r1.append(r5)
            java.lang.String r1 = r1.toString()
            java.lang.String r2 = "SAccessoryManager_CoverAuthenticator"
            android.util.Log.i(r2, r1)
            boolean r1 = r4.mIsSensor
            if (r1 != 0) goto L21
            java.lang.String r4 = "sendCoverStateToSensorhub:skip"
            android.util.Log.i(r2, r4)
            return
        L21:
            r1 = 48
            if (r5 != r1) goto L27
            r1 = 0
            goto L28
        L27:
            r1 = 1
        L28:
            r4.mCoverStateByNfc = r1
            r4 = 0
            java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L44
            java.lang.String r3 = "/sys/class/sensors/flip_cover_detector_sensor/nfc_cover_status"
            r1.<init>(r3)     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L44
            r1.write(r5)     // Catch: java.lang.Throwable -> L3c java.lang.Exception -> L3e
            r1.close()     // Catch: java.lang.Throwable -> L3c java.lang.Exception -> L3e
        L38:
            r1.close()     // Catch: java.io.IOException -> L59
            goto L59
        L3c:
            r4 = move-exception
            goto L5a
        L3e:
            r4 = move-exception
            goto L47
        L40:
            r5 = move-exception
            r1 = r4
            r4 = r5
            goto L5a
        L44:
            r5 = move-exception
            r1 = r4
            r4 = r5
        L47:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L3c
            r5.<init>(r0)     // Catch: java.lang.Throwable -> L3c
            r5.append(r4)     // Catch: java.lang.Throwable -> L3c
            java.lang.String r4 = r5.toString()     // Catch: java.lang.Throwable -> L3c
            android.util.Log.e(r2, r4)     // Catch: java.lang.Throwable -> L3c
            if (r1 == 0) goto L59
            goto L38
        L59:
            return
        L5a:
            if (r1 == 0) goto L5f
            r1.close()     // Catch: java.io.IOException -> L5f
        L5f:
            throw r4
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.accessory.manager.authentication.cover.CoverAuthenticator.sendCoverStateToSensorhub(char):void");
    }

    public final void sendFactoryResult() {
        String str;
        String str2;
        if (this.mFactoryIntent == null) {
            Log.e("SAccessoryManager_CoverAuthenticator", "factory intent is null");
            return;
        }
        AuthenticationResult authenticationResult = (AuthenticationResult) this.mResult.get();
        byte[] bArr = this.mUriData;
        if (bArr == null || bArr.length < 23) {
            str = "";
        } else {
            if (bArr != null) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0;
                        i < bArr.length;
                        i =
                                MARsFreezeStateRecord$$ExternalSyntheticOutline0.m(
                                        "%02x", new Object[] {Byte.valueOf(bArr[i])}, sb, i, 1)) {}
                str2 = sb.toString();
            } else {
                str2 = "null";
            }
            str = str2.substring(10, 18);
            ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                    "Service ID = ", str, "SAccessoryManager_CoverAuthenticator");
        }
        if (authenticationResult != null) {
            CoverInfo coverInfo = new CoverInfo(authenticationResult.mExtraID);
            int i2 = authenticationResult.mReason;
            if (i2 == 0 && coverInfo.valid) {
                setCoverResultForIntent("OK", coverInfo.serial, coverInfo.id, str);
            } else if (i2 == 0 && !coverInfo.valid) {
                setCoverResultForIntent("NG_ID", "", "", "");
            } else if (i2 == 1) {
                setCoverResultForIntent("NG_KEY", "", "", "");
            } else {
                setCoverResultForIntent("NG_NFC", "", "", "");
            }
        } else {
            setCoverResultForIntent("NG_NFC", "", "", "");
        }
        this.mContext.sendBroadcastAsUser(this.mFactoryIntent, UserHandle.CURRENT);
    }

    public final void setCoverResultForIntent(String str, String str2, String str3, String str4) {
        String str5;
        this.mFactoryIntent.putExtra("result_factory", str);
        this.mFactoryIntent.putExtra("serial_factory", str2);
        this.mFactoryIntent.putExtra("id_factory", str3);
        this.mFactoryIntent.putExtra("serviceid_factory", str4);
        String str6 = getCoverSwitchState() >= 1 ? "OK" : "NG";
        Intent intent = this.mFactoryIntent;
        StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str6, ", ");
        long j = this.mLastAttachTime;
        Calendar calendar = Calendar.getInstance();
        String str7 = "";
        if (j > 0) {
            calendar.setTimeInMillis(j);
            str5 =
                    String.format(
                            "%ty-%tm-%td %tH:%tM:%tS.%tL",
                            calendar, calendar, calendar, calendar, calendar, calendar, calendar);
        } else {
            str5 = "";
        }
        m.append(str5);
        intent.putExtra("attachInfo", m.toString());
        Intent intent2 = this.mFactoryIntent;
        long j2 = this.mLastDetachTime;
        Calendar calendar2 = Calendar.getInstance();
        if (j2 > 0) {
            calendar2.setTimeInMillis(j2);
            str7 =
                    String.format(
                            "%ty-%tm-%td %tH:%tM:%tS.%tL",
                            calendar2, calendar2, calendar2, calendar2, calendar2, calendar2,
                            calendar2);
        }
        intent2.putExtra("detachInfo", str7);
    }

    public final void setCoverVerified(
            boolean z, CoverInfo coverInfo, AuthenticationResult authenticationResult) {
        CoverAuthStateFile coverAuthStateFile;
        byte[] bArr;
        byte[] bArr2;
        byte b;
        byte b2;
        byte b3;
        byte[] bArr3;
        PaletteCover paletteCover;
        CoverBroadcaster coverBroadcaster = this.mCoverBroadcaster;
        if (coverBroadcaster != null && z) {
            int i = coverInfo.type;
            coverBroadcaster.mRealCoverType = i;
            if (i == 4) {
                coverInfo.type = 0;
            }
        }
        CoverState coverState = new CoverState();
        coverState.setFakeCover(false);
        if (coverInfo != null && coverInfo.type == 6) {
            setTransmitPower(z);
        }
        boolean z2 = getCoverSwitchState() >= 1;
        StringBuilder m =
                BatteryService$$ExternalSyntheticOutline0.m(
                        "setCoverVerified isVerified: ", ", type:", z);
        m.append(coverInfo != null ? Integer.valueOf(coverInfo.type) : "null");
        m.append(", isCoverAttached:");
        m.append(z2);
        Log.d("SAccessoryManager_CoverAuthenticator", m.toString());
        boolean z3 =
                coverInfo != null
                        && coverInfo.type == 14
                        && this.mContext
                                .getPackageManager()
                                .hasSystemFeature(
                                        "com.sec.feature.nfc_suitled_authentication_cover");
        boolean z4 = (coverInfo != null && coverInfo.type == 18) || z3;
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                "is_SUITE_LED = ", "SAccessoryManager_CoverAuthenticator", z3);
        if (z4 && ((paletteCover = this.mPaletteCover) == null || z2)) {
            if (paletteCover == null) {
                this.mPaletteCover = new PaletteCover(this.mContext);
            }
            byte[] bArr4 = authenticationResult.mByteArrayManagerURI;
            this.mUriData = bArr4;
            if (this.mPaletteCover.mCoverAttached
                    && PaletteCover.isDataChanged(this.mContext, bArr4)) {
                Log.d(
                        "SAccessoryManager_CoverAuthenticator",
                        "Palette Cover changed, force detach first");
                com.samsung.android.sepunion.Log.addLogString(
                        "CoverManager_", "palette cover detach by data change");
                this.mPaletteCover.setCoverVerified(false, null);
            }
            StringBuilder sb = new StringBuilder("Palette Cover attached info = ");
            byte[] bArr5 = this.mUriData;
            VpnManagerService$$ExternalSyntheticOutline0.m(
                    sb,
                    bArr5 != null ? Arrays.toString(bArr5) : "",
                    "SAccessoryManager_CoverAuthenticator");
            if (!this.mPaletteCover.mCoverAttached) {
                StringBuilder sb2 = new StringBuilder("palette cover attach");
                byte[] bArr6 = this.mUriData;
                sb2.append(bArr6 != null ? Arrays.toString(bArr6) : "");
                com.samsung.android.sepunion.Log.addLogString("CoverManager_", sb2.toString());
                this.mCoverType = coverInfo.type;
                this.mPaletteCover.setCoverVerified(z, authenticationResult);
                if (!z3) {
                    coverState.setType(2);
                    coverInfo.type = 2;
                }
            }
            if (!z3) {
                PaletteCover paletteCover2 = this.mPaletteCover;
                if (paletteCover2 == null || !paletteCover2.mCoverAttached) {
                    return;
                }
                this.mLastCoverState = coverState;
                coverState.setAttachState(true);
                return;
            }
        } else {
            PaletteCover paletteCover3 = this.mPaletteCover;
            if (paletteCover3 != null && paletteCover3.mCoverAttached && (!z2 || !z4 || !z)) {
                Log.d("SAccessoryManager_CoverAuthenticator", "Palette Cover detached");
                com.samsung.android.sepunion.Log.addLogString(
                        "CoverManager_", "palette cover detach");
                this.mPaletteCover.setCoverVerified(false, null);
                this.mPaletteCover = null;
                this.mUriData = null;
                if (!z4 || !z) {
                    Log.d(
                            "SAccessoryManager_CoverAuthenticator",
                            "Palette Cover detached by other cover");
                } else if (!z3) {
                    this.mLastCoverState = null;
                    return;
                }
            }
        }
        if (z) {
            coverState.setAttachState(true);
            coverState.setColor(coverInfo.color);
            coverState.setModel(coverInfo.model);
            coverState.setSmartCoverAppUri(authenticationResult.mStringManagerURI);
            coverState.setSmartCoverCookie(authenticationResult.mByteArrayExtraData);
            byte[] bArr7 = authenticationResult.mByteArrayManagerURI;
            this.mUriData = bArr7;
            if (bArr7 == null || bArr7.length != 23) {
                b2 = -1;
                b3 = -1;
            } else {
                byte b4 = bArr7[4];
                b2 = bArr7[5];
                b3 = b4;
            }
            coverState.setFriendsType(0);
            if (coverInfo.type == 8
                    && this.mContext
                            .getPackageManager()
                            .hasSystemFeature("com.sec.feature.cover.clearsideviewcover")) {
                coverInfo.type = 15;
            }
            if (this.mSensor != null && this.mCoverType != coverInfo.type) {
                StringBuilder sb3 = new StringBuilder("force detach event, ");
                sb3.append(this.mCoverType);
                sb3.append(" ");
                GestureWakeup$$ExternalSyntheticOutline0.m(
                        sb3, coverInfo.type, "SAccessoryManager_CoverAuthenticator");
                setCoverVerified(false, null, null);
            }
            int i2 = coverInfo.type;
            this.mCoverType = i2;
            int i3 = coverInfo.smapp;
            if (i3 == 0) {
                coverState.setType(i2);
                coverState.setSerialNumber(coverInfo.serial);
            } else if (i3 == 1) {
                coverState.setType(IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT);
            } else if (this.mUriData == null || b3 != 1 || b2 == -1) {
                coverState.setType(2);
            } else {
                coverState.setType(2);
                if (b2 == 1) {
                    coverState.setFriendsType(1);
                } else if (b2 == 3) {
                    coverState.setType(0);
                    coverState.setFriendsType(2);
                }
            }
            this.mLastCoverState = coverState;
            if (this.currentHall == 0) {
                this.mInputManager.setCoverVerify(coverState.getType());
                this.mPowerManager.setCoverType(coverState.getType());
                if (this.mSemUnionManagerLocal == null) {
                    this.mSemUnionManagerLocal =
                            (SemUnionManagerLocal)
                                    LocalServices.getService(SemUnionManagerLocal.class);
                }
                SemUnionManagerLocal semUnionManagerLocal = this.mSemUnionManagerLocal;
                if (semUnionManagerLocal != null) {
                    semUnionManagerLocal.notifySmartCoverAttachStateChanged(
                            System.currentTimeMillis(), z, coverState);
                }
                if (coverInfo.type == 0) {
                    Iterator<ApplicationInfo> it =
                            this.mContext
                                    .getPackageManager()
                                    .getInstalledApplications(0)
                                    .iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        if (it.next().packageName.equals("com.samsung.android.isag.issmanager")) {
                            Log.i("SAccessoryManager_CoverAuthenticator", "send to iss manager");
                            Intent intent = new Intent();
                            intent.setAction(
                                    "com.samsung.android.isag.issmanager.ACTION_COVER_AUTH");
                            intent.setPackage("com.samsung.android.isag.issmanager");
                            this.mContext.sendBroadcast(
                                    intent,
                                    "com.samsung.android.isag.issmanager.permission.COVER_AUTH");
                            break;
                        }
                    }
                }
            }
            int i4 = this.mAuthType;
            if (i4 == 1 || i4 == 2 || i4 == 3) {
                Log.d("SAccessoryManager_CoverAuthenticator", "coverInfo.getUrl: " + coverInfo.url);
                Intent intent2 =
                        new Intent(
                                "com.samsung.android.intent.action.ACCESSORY_AUTHENTICATION_COMPLETE");
                if (coverInfo.url != 0) {
                    byte[] bArr8 = this.mUriData;
                    if (bArr8 == null || bArr8.length < 23) {
                        Log.i("SAccessoryManager_CoverAuthenticator", "uri data is null");
                    } else {
                        byte b5 = bArr8[1];
                        if (b5 < 17 || b5 >= 32) {
                            intent2.putExtra("DEVICE_TYPE", "smapp");
                        } else {
                            if (bArr8[4] == 2) {
                                intent2.putExtra("DEVICE_TYPE", "cover");
                            } else {
                                intent2.putExtra("DEVICE_TYPE", "friends");
                            }
                            notifyFriendsStateChanged(true, this.mUriData, coverInfo.chip_id);
                        }
                    }
                } else {
                    intent2.putExtra("DEVICE_TYPE", "cover");
                }
                this.mContext.sendBroadcast(intent2);
                CoverBroadcaster coverBroadcaster2 = this.mCoverBroadcaster;
                if (coverBroadcaster2 != null) {
                    coverBroadcaster2.broadcastCoverAttachStatus(true);
                }
            }
            final CoverAuthStateFile coverAuthStateFile2 = this.mCoverAuthStateFile;
            final byte[] bArr9 = coverInfo.chip_id;
            final byte[] bArr10 = authenticationResult.mByteArrayManagerURI;
            final byte[] bArr11 = authenticationResult.mByteArrayExtraData;
            String str = coverAuthStateFile2.TAG;
            if (bArr9 == null) {
                Log.e(str, "ID is null.. can not write");
            } else if (Arrays.equals(coverAuthStateFile2.mId, bArr9)
                    && Arrays.equals(coverAuthStateFile2.mUri, bArr10)
                    && Arrays.equals(coverAuthStateFile2.mExtraData, bArr11)) {
                Log.e(str, "same state.. ");
            } else {
                byte[] bArr12 = new byte[bArr9.length];
                coverAuthStateFile2.mId = bArr12;
                System.arraycopy(bArr9, 0, bArr12, 0, bArr9.length);
                if (bArr10 != null) {
                    byte[] bArr13 = new byte[bArr10.length];
                    coverAuthStateFile2.mUri = bArr13;
                    System.arraycopy(bArr10, 0, bArr13, 0, bArr10.length);
                    bArr3 = null;
                } else {
                    bArr3 = null;
                    coverAuthStateFile2.mUri = null;
                }
                if (bArr11 != null) {
                    byte[] bArr14 = new byte[bArr11.length];
                    coverAuthStateFile2.mExtraData = bArr14;
                    System.arraycopy(bArr11, 0, bArr14, 0, bArr11.length);
                } else {
                    coverAuthStateFile2.mExtraData = bArr3;
                }
                new Thread() { // from class:
                               // com.samsung.accessory.manager.authentication.cover.CoverAuthenticator.CoverAuthStateFile.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super("AuthStateUpdater");
                    }

                    /* JADX WARN: Not initialized variable reg: 3, insn: 0x003a: MOVE (r1 I:??[OBJECT, ARRAY]) = (r3 I:??[OBJECT, ARRAY]), block:B:30:0x003a */
                    @Override // java.lang.Thread, java.lang.Runnable
                    public final void run() {
                        FileOutputStream fileOutputStream;
                        IOException e;
                        FileOutputStream fileOutputStream2;
                        File createTempFile;
                        synchronized (CoverAuthStateFile.this.mTmpDir) {
                            Log.d(
                                    CoverAuthStateFile.this.TAG,
                                    "An authentication state changed, updating...");
                            FileOutputStream fileOutputStream3 = null;
                            try {
                            } catch (Throwable th) {
                                th = th;
                            }
                            try {
                                try {
                                    createTempFile =
                                            File.createTempFile(
                                                    "journal", "", CoverAuthStateFile.this.mTmpDir);
                                    fileOutputStream = new FileOutputStream(createTempFile);
                                } catch (FileNotFoundException unused) {
                                } catch (IOException e2) {
                                    fileOutputStream = null;
                                    e = e2;
                                }
                                try {
                                    fileOutputStream.write(bArr9.length);
                                    fileOutputStream.write(bArr9);
                                    byte[] bArr15 = bArr10;
                                    if (bArr15 != null) {
                                        fileOutputStream.write(bArr15.length);
                                        fileOutputStream.write(bArr10);
                                    }
                                    byte[] bArr16 = bArr11;
                                    if (bArr16 != null) {
                                        fileOutputStream.write(bArr16.length);
                                        fileOutputStream.write(bArr11);
                                    }
                                    FileUtils.sync(fileOutputStream);
                                    createTempFile.renameTo(
                                            new File(CoverAuthStateFile.this.mPath));
                                    Log.d(
                                            CoverAuthStateFile.this.TAG,
                                            "An authentication state updated");
                                } catch (FileNotFoundException unused2) {
                                    fileOutputStream3 = fileOutputStream;
                                    Log.e(CoverAuthStateFile.this.TAG, "File does not exist");
                                    IoUtils.closeQuietly(fileOutputStream3);
                                } catch (IOException e3) {
                                    e = e3;
                                    Log.e(
                                            CoverAuthStateFile.this.TAG,
                                            "Failed to write authentication state",
                                            e);
                                    IoUtils.closeQuietly(fileOutputStream);
                                }
                                IoUtils.closeQuietly(fileOutputStream);
                            } catch (Throwable th2) {
                                th = th2;
                                fileOutputStream3 = fileOutputStream2;
                                IoUtils.closeQuietly(fileOutputStream3);
                                throw th;
                            }
                        }
                    }
                }.start();
            }
            this.mLastAuthenticationTime = SystemClock.elapsedRealtime();
            this.mFailuresCount = 0;
        } else {
            this.mInputManager.setCoverVerify(-1);
            this.mPowerManager.setCoverType(-1);
            CoverState coverState2 = this.mLastCoverState;
            if (coverState2 != null) {
                coverState2.setAttachState(false);
            }
            CoverState coverState3 = this.mLastCoverState;
            if (this.mSemUnionManagerLocal == null) {
                this.mSemUnionManagerLocal =
                        (SemUnionManagerLocal) LocalServices.getService(SemUnionManagerLocal.class);
            }
            SemUnionManagerLocal semUnionManagerLocal2 = this.mSemUnionManagerLocal;
            if (semUnionManagerLocal2 != null) {
                semUnionManagerLocal2.notifySmartCoverAttachStateChanged(
                        System.currentTimeMillis(), z, coverState3);
            }
            this.mLastCoverState = null;
            if (this.mCoverType == 17) {
                setTransmitPower(false);
            }
            if (this.mAuthType != 4
                    && (bArr = (coverAuthStateFile = this.mCoverAuthStateFile).mId) != null
                    && bArr[21] == 1
                    && (bArr2 = coverAuthStateFile.mUri) != null
                    && (b = bArr2[1]) >= 17
                    && b < 32) {
                notifyFriendsStateChanged(false, this.mUriData, null);
                this.mUriData = null;
            }
        }
        Settings.System.putString(
                this.mContext.getContentResolver(),
                "cover_type_id",
                z
                        ? AmFmBandRange$$ExternalSyntheticOutline0.m(
                                this.mCoverType, new StringBuilder(), "")
                        : "");
    }

    public final void setFriendsVerified(
            boolean z, CoverInfo coverInfo, AuthenticationResult authenticationResult) {
        byte b;
        Log.i("SAccessoryManager_CoverAuthenticator", "setAccessoryVerified");
        if (!z) {
            notifyFriendsStateChanged(false, this.mUriData, null);
            this.mUriData = null;
            return;
        }
        byte[] bArr = authenticationResult.mByteArrayManagerURI;
        this.mUriData = bArr;
        int i = this.mAuthType;
        if ((i == 1 || i == 2 || i == 3)
                && coverInfo.url != 0
                && bArr != null
                && bArr.length >= 2
                && (b = bArr[1]) >= 17
                && b < 32) {
            notifyFriendsStateChanged(true, bArr, coverInfo.chip_id);
        }
    }

    public final void startAuthentication(long j, boolean z) {
        if (isAuthenticationReady()) {
            this.mCoverAuthHandler.removeMessages(4);
            if (z) {
                removeAuthenticationTimeOuts();
                this.mCoverAuthHandler.sendEmptyMessageDelayed(5, 27000L);
                this.mPreparing = true;
            }
            this.mCoverAuthHandler.sendEmptyMessageDelayed(2, j);
        }
    }

    public final void stopAuthentication(long j) {
        removeAuthenticationTimeOuts();
        CoverAuthHandler coverAuthHandler = this.mCoverAuthHandler;
        coverAuthHandler.removeMessages(2);
        coverAuthHandler.sendEmptyMessageDelayed(4, j);
    }

    @Override // com.samsung.accessory.manager.authentication.LocalAuthenticator
    public final void systemReady() {
        this.mCoverAuthHandler.sendEmptyMessage(1);
    }
}
