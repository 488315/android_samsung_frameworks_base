package com.android.server.ibs;

import android.R;
import android.app.ActivityManagerNative;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.hardware.SensorManager;
import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.IIntelligentBatterySaverService;
import android.os.INetworkManagementService;
import android.os.Looper;
import android.os.Parcel;
import android.os.PowerManagerInternal;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.ArraySet;
import android.util.LocalLog;
import android.util.Log;
import android.util.Slog;
import android.view.IWindowManager;
import com.android.internal.os.PowerProfile;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.LocalManagerRegistry;
import com.android.server.LocalServices;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountManagerServiceShellCommand$$ExternalSyntheticOutline0;
import com.android.server.am.KillPolicyManager$$ExternalSyntheticOutline0;
import com.android.server.art.ArtManagerLocal;
import com.android.server.ibs.IntelligentBatterySaverGather.IntelligentBatterySaverGatherReceiver;
import com.android.server.ibs.IntelligentBatterySaverGoogleAppPolicy.IBSGoogleAppPolicyReceiver;
import com.android.server.ibs.IntelligentBatterySaverSettingsObserver.SettingsObserver;
import com.android.server.ibs.sleepmode.SharePrefUtils;
import com.android.server.ibs.sleepmode.SleepModeLogger;
import com.android.server.ibs.sleepmode.SleepModePolicyController;
import com.android.server.ibs.sleepmode.SleepModeUtil;
import com.android.server.ibs.sqd.IbsQuickDim;
import com.android.server.ibs.sqd.IbsQuickDim$$ExternalSyntheticLambda0;
import com.android.server.ibs.sqd.IbsQuickDim$$ExternalSyntheticLambda1;
import com.android.server.ibs.sqd.IbsQuickDim.IntentReceiver;
import com.android.server.ibs.sqd.IbsQuickDim.SettingsObserver;
import com.android.server.ibs.sqd.IbsQuickDim.qkDimHandler;
import com.android.server.pm.PackageManagerLocal;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class IntelligentBatterySaverService extends IIntelligentBatterySaverService.Stub {
    public static boolean mIBSEnable;
    public final Context mContext;
    public final HandlerThread mHandlerThread;
    public final IntelligentBatterySaverDexoptManager mIBSDexoptManager;
    public final IntelligentBatterySaverFastDrainPolicy mIBSFastDrainPolicy;
    public final IntelligentBatterySaverGoogleAppPolicy mIBSGoogleAppPolicy;
    public final IntelligentBatterySaverLogger mIBSLogger;
    public final IbsQuickDim mIBSQuickDim;
    public final IntelligentBatterySaverScpmManager mIBSScpmManager;
    public final ServiceHandler mServiceHandler;
    public final SleepModeLogger mSleepModeLogger;
    public final SleepModePolicyController mSleepModePolicyController;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SCPMReceiver extends BroadcastReceiver {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ IntelligentBatterySaverService this$0;

        public SCPMReceiver(IntelligentBatterySaverService intelligentBatterySaverService, int i) {
            this.$r8$classId = i;
            switch (i) {
                case 1:
                    this.this$0 = intelligentBatterySaverService;
                    intelligentBatterySaverService.mContext.registerReceiver(this, BatteryService$$ExternalSyntheticOutline0.m("android.intent.action.BOOT_COMPLETED"), 2);
                    break;
                default:
                    this.this$0 = intelligentBatterySaverService;
                    intelligentBatterySaverService.mContext.registerReceiver(this, DirEncryptServiceHelper$$ExternalSyntheticOutline0.m("com.samsung.android.scpm.policy.UPDATE.ibs", KnoxCustomManagerService.ACTION_SCPM_POLICY_CLEAR_DATA), 2);
                    break;
            }
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            switch (this.$r8$classId) {
                case 0:
                    String action = intent.getAction();
                    if (!"com.samsung.android.scpm.policy.UPDATE.ibs".equals(action)) {
                        if (KnoxCustomManagerService.ACTION_SCPM_POLICY_CLEAR_DATA.equals(action)) {
                            Slog.d("IntelligentBatterySaverService", "SCPM clear broadcast received, policy updated 1 min later!");
                            this.this$0.mServiceHandler.postDelayed(new Runnable() { // from class: com.android.server.ibs.IntelligentBatterySaverService.SCPMReceiver.1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    IntelligentBatterySaverScpmManager intelligentBatterySaverScpmManager = SCPMReceiver.this.this$0.mIBSScpmManager;
                                    if (intelligentBatterySaverScpmManager != null) {
                                        intelligentBatterySaverScpmManager.registerScpm();
                                    }
                                }
                            }, 60000L);
                            break;
                        }
                    } else {
                        Slog.d("IntelligentBatterySaverService", "SCPM update broadcast received!");
                        this.this$0.mServiceHandler.sendEmptyMessage(1);
                        break;
                    }
                    break;
                default:
                    if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
                        Slog.d("IntelligentBatterySaverService", "ACTION_BOOT_COMPLETED broadcast received: " + intent.getAction());
                        IntelligentBatterySaverScpmManager intelligentBatterySaverScpmManager = this.this$0.mIBSScpmManager;
                        if (intelligentBatterySaverScpmManager != null) {
                            intelligentBatterySaverScpmManager.registerScpm();
                        }
                        Optional.ofNullable(this.this$0.mIBSFastDrainPolicy).ifPresent(new IntelligentBatterySaverService$BootCompleteReceiver$$ExternalSyntheticLambda0());
                        this.this$0.mServiceHandler.sendEmptyMessage(1);
                        break;
                    }
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        /* JADX WARN: Removed duplicated region for block: B:42:0x010f A[Catch: all -> 0x0142, Exception -> 0x0144, TRY_ENTER, TryCatch #6 {Exception -> 0x0144, blocks: (B:42:0x010f, B:48:0x0146), top: B:40:0x010d, outer: #1 }] */
        /* JADX WARN: Removed duplicated region for block: B:44:0x016e A[Catch: IOException -> 0x0172, TRY_ENTER, TRY_LEAVE, TryCatch #4 {IOException -> 0x0172, blocks: (B:44:0x016e, B:64:0x0182), top: B:40:0x010d }] */
        /* JADX WARN: Removed duplicated region for block: B:48:0x0146 A[Catch: all -> 0x0142, Exception -> 0x0144, TRY_LEAVE, TryCatch #6 {Exception -> 0x0144, blocks: (B:42:0x010f, B:48:0x0146), top: B:40:0x010d, outer: #1 }] */
        /* JADX WARN: Removed duplicated region for block: B:76:0x01a5  */
        /* JADX WARN: Removed duplicated region for block: B:79:0x01d5  */
        /* JADX WARN: Removed duplicated region for block: B:81:0x01e0  */
        /* JADX WARN: Removed duplicated region for block: B:83:0x01b3  */
        @Override // android.os.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void handleMessage(android.os.Message r14) {
            /*
                Method dump skipped, instructions count: 492
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.ibs.IntelligentBatterySaverService.ServiceHandler.handleMessage(android.os.Message):void");
        }
    }

    /* JADX WARN: Type inference failed for: r12v0, types: [com.android.server.ibs.IntelligentBatterySaverSettingsObserver$$ExternalSyntheticLambda0] */
    public IntelligentBatterySaverService(Context context) {
        IntelligentBatterySaverLogger intelligentBatterySaverLogger;
        this.mContext = context;
        mIBSEnable = Boolean.parseBoolean(SystemProperties.get("sys.config.ibs.enable"));
        HandlerThread handlerThread = new HandlerThread("IntelligentBatterySaverService", 1);
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        IBinder iBinder = null;
        if (mIBSEnable) {
            ServiceHandler serviceHandler = new ServiceHandler(handlerThread.getLooper());
            this.mServiceHandler = serviceHandler;
            synchronized (IntelligentBatterySaverLogger.class) {
                try {
                    if (IntelligentBatterySaverLogger.sInstance == null) {
                        IntelligentBatterySaverLogger intelligentBatterySaverLogger2 = new IntelligentBatterySaverLogger();
                        intelligentBatterySaverLogger2.mIsUsed = false;
                        intelligentBatterySaverLogger2.mIBSLog = new LocalLog(3000);
                        IntelligentBatterySaverLogger.sInstance = intelligentBatterySaverLogger2;
                    }
                    intelligentBatterySaverLogger = IntelligentBatterySaverLogger.sInstance;
                } catch (Throwable th) {
                    throw th;
                }
            }
            this.mIBSLogger = intelligentBatterySaverLogger;
            IntelligentBatterySaverSurvey intelligentBatterySaverSurvey = new IntelligentBatterySaverSurvey();
            intelligentBatterySaverSurvey.mBigdataEnable = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_CONTEXTSERVICE_ENABLE_SURVEY_MODE");
            intelligentBatterySaverSurvey.mContext = context;
            IntelligentBatterySaverGather intelligentBatterySaverGather = new IntelligentBatterySaverGather(context, this);
            final IntelligentBatterySaverSettingsObserver intelligentBatterySaverSettingsObserver = new IntelligentBatterySaverSettingsObserver();
            intelligentBatterySaverSettingsObserver.mRunnable = new Runnable() { // from class: com.android.server.ibs.IntelligentBatterySaverSettingsObserver$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    IntelligentBatterySaverSettingsObserver.this.updateTimeSetting();
                }
            };
            intelligentBatterySaverSettingsObserver.mContext = context;
            intelligentBatterySaverSettingsObserver.mHandler = serviceHandler;
            intelligentBatterySaverSettingsObserver.mIBSService = this;
            IntelligentBatterySaverFastDrainPolicy intelligentBatterySaverFastDrainPolicy = new IntelligentBatterySaverFastDrainPolicy(context, handlerThread, intelligentBatterySaverLogger, intelligentBatterySaverSurvey);
            this.mIBSFastDrainPolicy = intelligentBatterySaverFastDrainPolicy;
            IntelligentBatterySaverGoogleAppPolicy intelligentBatterySaverGoogleAppPolicy = new IntelligentBatterySaverGoogleAppPolicy(context, handlerThread);
            this.mIBSGoogleAppPolicy = intelligentBatterySaverGoogleAppPolicy;
            this.mIBSScpmManager = IntelligentBatterySaverScpmManager.getInstance(context);
            new SCPMReceiver(this, 1);
            new SCPMReceiver(this, 0);
            intelligentBatterySaverGather.mReceiver = intelligentBatterySaverGather.new IntelligentBatterySaverGatherReceiver();
            IntentFilter intentFilter = new IntentFilter();
            intelligentBatterySaverGather.mFilter = intentFilter;
            intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
            context.registerReceiver(intelligentBatterySaverGather.mReceiver, intelligentBatterySaverGather.mFilter, 2);
            ((DisplayManager) context.getSystemService("display")).registerDisplayListener(intelligentBatterySaverGather.mDisplayListener, null);
            if (intelligentBatterySaverSettingsObserver.mSettingsObserver == null) {
                intelligentBatterySaverSettingsObserver.mSettingsObserver = intelligentBatterySaverSettingsObserver.new SettingsObserver();
                final ContentResolver contentResolver = context.getContentResolver();
                final int i = 0;
                Optional.ofNullable(Settings.Global.getUriFor("ibs_switch")).ifPresent(new Consumer() { // from class: com.android.server.ibs.IntelligentBatterySaverSettingsObserver$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        switch (i) {
                            case 0:
                                contentResolver.registerContentObserver((Uri) obj, false, intelligentBatterySaverSettingsObserver.mSettingsObserver, -1);
                                break;
                            case 1:
                                contentResolver.registerContentObserver((Uri) obj, false, intelligentBatterySaverSettingsObserver.mSettingsObserver, -1);
                                break;
                            case 2:
                                contentResolver.registerContentObserver((Uri) obj, false, intelligentBatterySaverSettingsObserver.mSettingsObserver, -1);
                                break;
                            case 3:
                                contentResolver.registerContentObserver((Uri) obj, false, intelligentBatterySaverSettingsObserver.mSettingsObserver, -1);
                                break;
                            default:
                                contentResolver.registerContentObserver((Uri) obj, false, intelligentBatterySaverSettingsObserver.mSettingsObserver, -1);
                                break;
                        }
                    }
                });
                final int i2 = 1;
                Optional.ofNullable(Settings.Global.getUriFor("ibs_start_hour")).ifPresent(new Consumer() { // from class: com.android.server.ibs.IntelligentBatterySaverSettingsObserver$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        switch (i2) {
                            case 0:
                                contentResolver.registerContentObserver((Uri) obj, false, intelligentBatterySaverSettingsObserver.mSettingsObserver, -1);
                                break;
                            case 1:
                                contentResolver.registerContentObserver((Uri) obj, false, intelligentBatterySaverSettingsObserver.mSettingsObserver, -1);
                                break;
                            case 2:
                                contentResolver.registerContentObserver((Uri) obj, false, intelligentBatterySaverSettingsObserver.mSettingsObserver, -1);
                                break;
                            case 3:
                                contentResolver.registerContentObserver((Uri) obj, false, intelligentBatterySaverSettingsObserver.mSettingsObserver, -1);
                                break;
                            default:
                                contentResolver.registerContentObserver((Uri) obj, false, intelligentBatterySaverSettingsObserver.mSettingsObserver, -1);
                                break;
                        }
                    }
                });
                final int i3 = 2;
                Optional.ofNullable(Settings.Global.getUriFor("ibs_start_minute")).ifPresent(new Consumer() { // from class: com.android.server.ibs.IntelligentBatterySaverSettingsObserver$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        switch (i3) {
                            case 0:
                                contentResolver.registerContentObserver((Uri) obj, false, intelligentBatterySaverSettingsObserver.mSettingsObserver, -1);
                                break;
                            case 1:
                                contentResolver.registerContentObserver((Uri) obj, false, intelligentBatterySaverSettingsObserver.mSettingsObserver, -1);
                                break;
                            case 2:
                                contentResolver.registerContentObserver((Uri) obj, false, intelligentBatterySaverSettingsObserver.mSettingsObserver, -1);
                                break;
                            case 3:
                                contentResolver.registerContentObserver((Uri) obj, false, intelligentBatterySaverSettingsObserver.mSettingsObserver, -1);
                                break;
                            default:
                                contentResolver.registerContentObserver((Uri) obj, false, intelligentBatterySaverSettingsObserver.mSettingsObserver, -1);
                                break;
                        }
                    }
                });
                final int i4 = 3;
                Optional.ofNullable(Settings.Global.getUriFor("ibs_end_hour")).ifPresent(new Consumer() { // from class: com.android.server.ibs.IntelligentBatterySaverSettingsObserver$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        switch (i4) {
                            case 0:
                                contentResolver.registerContentObserver((Uri) obj, false, intelligentBatterySaverSettingsObserver.mSettingsObserver, -1);
                                break;
                            case 1:
                                contentResolver.registerContentObserver((Uri) obj, false, intelligentBatterySaverSettingsObserver.mSettingsObserver, -1);
                                break;
                            case 2:
                                contentResolver.registerContentObserver((Uri) obj, false, intelligentBatterySaverSettingsObserver.mSettingsObserver, -1);
                                break;
                            case 3:
                                contentResolver.registerContentObserver((Uri) obj, false, intelligentBatterySaverSettingsObserver.mSettingsObserver, -1);
                                break;
                            default:
                                contentResolver.registerContentObserver((Uri) obj, false, intelligentBatterySaverSettingsObserver.mSettingsObserver, -1);
                                break;
                        }
                    }
                });
                final int i5 = 4;
                Optional.ofNullable(Settings.Global.getUriFor("ibs_end_minute")).ifPresent(new Consumer() { // from class: com.android.server.ibs.IntelligentBatterySaverSettingsObserver$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        switch (i5) {
                            case 0:
                                contentResolver.registerContentObserver((Uri) obj, false, intelligentBatterySaverSettingsObserver.mSettingsObserver, -1);
                                break;
                            case 1:
                                contentResolver.registerContentObserver((Uri) obj, false, intelligentBatterySaverSettingsObserver.mSettingsObserver, -1);
                                break;
                            case 2:
                                contentResolver.registerContentObserver((Uri) obj, false, intelligentBatterySaverSettingsObserver.mSettingsObserver, -1);
                                break;
                            case 3:
                                contentResolver.registerContentObserver((Uri) obj, false, intelligentBatterySaverSettingsObserver.mSettingsObserver, -1);
                                break;
                            default:
                                contentResolver.registerContentObserver((Uri) obj, false, intelligentBatterySaverSettingsObserver.mSettingsObserver, -1);
                                break;
                        }
                    }
                });
            }
            intelligentBatterySaverSettingsObserver.updateSwitchSetting();
            intelligentBatterySaverFastDrainPolicy.mDisplayManager = (DisplayManager) intelligentBatterySaverFastDrainPolicy.mContext.getSystemService("display");
            intelligentBatterySaverFastDrainPolicy.mSensorManager = (SensorManager) intelligentBatterySaverFastDrainPolicy.mContext.getSystemService("sensor");
            int integer = intelligentBatterySaverFastDrainPolicy.mContext.getResources().getInteger(R.integer.config_carDockKeepsScreenOn);
            if (integer > 0) {
                intelligentBatterySaverFastDrainPolicy.mMotionSensor = intelligentBatterySaverFastDrainPolicy.mSensorManager.getDefaultSensor(integer, true);
            }
            if (intelligentBatterySaverFastDrainPolicy.mMotionSensor == null && intelligentBatterySaverFastDrainPolicy.mContext.getResources().getBoolean(R.bool.config_autoResetAirplaneMode)) {
                intelligentBatterySaverFastDrainPolicy.mMotionSensor = intelligentBatterySaverFastDrainPolicy.mSensorManager.getDefaultSensor(26, true);
            }
            if (intelligentBatterySaverFastDrainPolicy.mMotionSensor == null) {
                intelligentBatterySaverFastDrainPolicy.mMotionSensor = intelligentBatterySaverFastDrainPolicy.mSensorManager.getDefaultSensor(17, true);
            }
            Slog.d("IntelligentBatterySaverGoogleAppPolicy", "addPackageNameGoogleAppsList pkg= com.android.vendingvalue = " + ((Object) 1));
            synchronized (intelligentBatterySaverGoogleAppPolicy.mLockGoogleAppsList) {
                try {
                    intelligentBatterySaverGoogleAppPolicy.mGoogleAppsList.put(Integer.valueOf(intelligentBatterySaverGoogleAppPolicy.mContext.getPackageManager().getPackageUid("com.android.vending", 0)), 1);
                } catch (PackageManager.NameNotFoundException e) {
                    Slog.v("IntelligentBatterySaverGoogleAppPolicy", "NameNotFoundException" + e);
                    intelligentBatterySaverGoogleAppPolicy.mGoogleAppsList.put(-1, -1);
                }
            }
            intelligentBatterySaverGoogleAppPolicy.new IBSGoogleAppPolicyReceiver();
            IBinder service = ServiceManager.getService("network_management");
            if (service != null) {
                intelligentBatterySaverGoogleAppPolicy.mNetworkService = INetworkManagementService.Stub.asInterface(service);
            }
        }
        IbsQuickDim ibsQuickDim = new IbsQuickDim(this.mContext);
        this.mIBSQuickDim = ibsQuickDim;
        HandlerThread handlerThread2 = new HandlerThread("IbsQuickDim", 1);
        ibsQuickDim.mHandlerThread = handlerThread2;
        handlerThread2.start();
        ibsQuickDim.mQkDimHandler = ibsQuickDim.new qkDimHandler(ibsQuickDim.mHandlerThread.getLooper());
        ibsQuickDim.mResolver = ibsQuickDim.mContext.getContentResolver();
        try {
            IWindowManager.Stub.asInterface(ServiceManager.getService("window")).registerPointerEventListener(ibsQuickDim.mPointerEventListener, 0);
        } catch (Exception e2) {
            Log.e("IbsQuickDim", "Exception - registerPointerEventListener");
            e2.printStackTrace();
        }
        ibsQuickDim.mPowerManagerInternal = (PowerManagerInternal) LocalServices.getService(PowerManagerInternal.class);
        ibsQuickDim.mBlockUnDimUidSet = new ArraySet();
        ibsQuickDim.mAllowDimUidSet = new ArraySet();
        ibsQuickDim.mScreenOffTimeoutSetting = Settings.System.getIntForUser(ibsQuickDim.mResolver, "screen_off_timeout", 60000, -2);
        ibsQuickDim.mSmartStayEnabledSetting = Settings.System.getIntForUser(ibsQuickDim.mResolver, "intelligent_sleep_mode", 0, -2) != 0;
        ibsQuickDim.mBrightness = Settings.System.getIntForUser(ibsQuickDim.mResolver, "screen_brightness", 0, -2);
        ibsQuickDim.mSettingsObserver = ibsQuickDim.new SettingsObserver(ibsQuickDim.mQkDimHandler);
        IbsQuickDim.IntentReceiver intentReceiver = ibsQuickDim.new IntentReceiver();
        ibsQuickDim.receiver = intentReceiver;
        ibsQuickDim.mContext.registerReceiver(intentReceiver, ibsQuickDim.mFilter, 2);
        ibsQuickDim.mContext.registerReceiver(ibsQuickDim.receiver, ibsQuickDim.mPkgFilter, 2);
        ibsQuickDim.mResolver.registerContentObserver(Settings.System.getUriFor("screen_off_timeout"), false, ibsQuickDim.mSettingsObserver, -1);
        ibsQuickDim.mResolver.registerContentObserver(Settings.System.getUriFor("intelligent_sleep_mode"), false, ibsQuickDim.mSettingsObserver, -1);
        ibsQuickDim.mResolver.registerContentObserver(Settings.System.getUriFor("screen_brightness"), false, ibsQuickDim.mSettingsObserver, -1);
        IbsQuickDim.SQLiteSQDwhilteList sQLiteSQDwhilteList = new IbsQuickDim.SQLiteSQDwhilteList(ibsQuickDim.mContext, "SQD_whilte_list", null, 2);
        sQLiteSQDwhilteList.mDb = null;
        sQLiteSQDwhilteList.mDb = sQLiteSQDwhilteList.getWritableDatabase();
        sQLiteSQDwhilteList.mBlockDataOperator = new IbsQuickDim.SQLiteSQDwhilteList.AllowDataOperator(sQLiteSQDwhilteList, 1);
        sQLiteSQDwhilteList.mAllowDataOperator = new IbsQuickDim.SQLiteSQDwhilteList.AllowDataOperator(sQLiteSQDwhilteList, 0);
        ibsQuickDim.mSQLiteSQDwhilteList = sQLiteSQDwhilteList;
        try {
            ActivityManagerNative.getDefault().registerProcessObserver(ibsQuickDim.mProcessObserver);
        } catch (RemoteException e3) {
            Log.e("IbsQuickDim", "RemoteException - registerProcessObserver");
            e3.printStackTrace();
        }
        ibsQuickDim.mQkDimHandler.post(new IbsQuickDim$$ExternalSyntheticLambda0(ibsQuickDim, 0));
        if (ibsQuickDim.mSFSevices == null) {
            IBinder service2 = ServiceManager.getService("SurfaceFlinger");
            ibsQuickDim.mSFSevices = service2;
            if (service2 != null) {
                iBinder = service2;
            }
        }
        ibsQuickDim.mSFSevices = iBinder;
        SleepModeLogger sleepModeLogger = SleepModeLogger.SleepModeLoggerHolder.INSTANCE;
        this.mSleepModeLogger = sleepModeLogger;
        this.mSleepModePolicyController = new SleepModePolicyController(context, this.mHandlerThread, sleepModeLogger);
        IntelligentBatterySaverDexoptManager intelligentBatterySaverDexoptManager = new IntelligentBatterySaverDexoptManager();
        this.mIBSDexoptManager = intelligentBatterySaverDexoptManager;
        try {
            intelligentBatterySaverDexoptManager.mArtManagerLocal = (ArtManagerLocal) LocalManagerRegistry.getManagerOrThrow(ArtManagerLocal.class);
            intelligentBatterySaverDexoptManager.mPackageManagerLocal = (PackageManagerLocal) LocalManagerRegistry.getManagerOrThrow(PackageManagerLocal.class);
        } catch (LocalManagerRegistry.ManagerNotFoundException e4) {
            Slog.e("IntelligentBatterySaverDexoptManager", "failed to get local manager " + e4.getMessage());
        }
    }

    public final boolean addScreenQuickDimApp(String str, int i) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "IntelligentBatterySaverService");
        IbsQuickDim ibsQuickDim = this.mIBSQuickDim;
        ibsQuickDim.getClass();
        Log.d("IbsQuickDim", " addScreenQuickDimApp uid = " + i + " pkgName = " + str);
        if (!ibsQuickDim.mAllowDimUidSet.contains(Integer.valueOf(i))) {
            ibsQuickDim.mAllowDimUidSet.add(Integer.valueOf(i));
        }
        ContentValues m = AccountManagerService$$ExternalSyntheticOutline0.m("PackageName", str);
        m.put("Uid", Integer.valueOf(i));
        long insert = ibsQuickDim.mSQLiteSQDwhilteList.getDataOperator(1).insert(m);
        Log.d("IbsQuickDim", " ret = " + insert);
        return insert != -1;
    }

    public final boolean addSqdBlockList(int i, String str) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "IntelligentBatterySaverService");
        IbsQuickDim ibsQuickDim = this.mIBSQuickDim;
        ibsQuickDim.getClass();
        Log.d("IbsQuickDim", " addBlockList uid = " + i + " pkgName = " + str);
        if (!ibsQuickDim.mBlockUnDimUidSet.contains(Integer.valueOf(i))) {
            ibsQuickDim.mBlockUnDimUidSet.add(Integer.valueOf(i));
        }
        ContentValues m = AccountManagerService$$ExternalSyntheticOutline0.m("PackageName", str);
        m.put("Uid", Integer.valueOf(i));
        long insert = ibsQuickDim.mSQLiteSQDwhilteList.getDataOperator(0).insert(m);
        Log.d("IbsQuickDim", " ret = " + insert);
        return insert != -1;
    }

    public final List dexoptPackages(List list) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "IntelligentBatterySaverService");
        return this.mIBSDexoptManager.dexoptPackages(list);
    }

    public final void dump(FileDescriptor fileDescriptor, final PrintWriter printWriter, final String[] strArr) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
            printWriter.println("Permission Denial: can't dump IntelligentBatterySaverService from pid=" + Binder.getCallingPid() + ", uid=" + Binder.getCallingUid() + " without permission android.permission.DUMP");
            return;
        }
        if (mIBSEnable) {
            printWriter.println("");
            printWriter.println("IBS Version: 1.0");
            IntelligentBatterySaverFastDrainPolicy intelligentBatterySaverFastDrainPolicy = this.mIBSFastDrainPolicy;
            intelligentBatterySaverFastDrainPolicy.getClass();
            printWriter.println("");
            printWriter.println("IntelligentBatterySaverFastDrainPolicy ");
            printWriter.println("get current mSysState :" + Integer.toBinaryString(intelligentBatterySaverFastDrainPolicy.mSysState));
            StringBuilder sb = new StringBuilder("getBatteryCapacity :");
            if (intelligentBatterySaverFastDrainPolicy.mPowerProfile == null) {
                intelligentBatterySaverFastDrainPolicy.mPowerProfile = new PowerProfile(intelligentBatterySaverFastDrainPolicy.mContext);
            }
            sb.append(intelligentBatterySaverFastDrainPolicy.mPowerProfile.getBatteryCapacity());
            printWriter.println(sb.toString());
            if (strArr.length == 2 && "ibs".equals(strArr[0])) {
                int parseInt = Integer.parseInt(strArr[1]);
                printWriter.println("set new mSysState :" + Integer.toBinaryString(parseInt));
                intelligentBatterySaverFastDrainPolicy.setSysState(parseInt);
                printWriter.println("get updated mSysState :" + Integer.toBinaryString(intelligentBatterySaverFastDrainPolicy.mSysState));
            }
            IntelligentBatterySaverGoogleAppPolicy intelligentBatterySaverGoogleAppPolicy = this.mIBSGoogleAppPolicy;
            intelligentBatterySaverGoogleAppPolicy.getClass();
            printWriter.println("");
            printWriter.println("IntelligentBatterySaverGoogleAppPolicy ");
            for (int i = 0; i < intelligentBatterySaverGoogleAppPolicy.mGoogleAppsList.size(); i++) {
                StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i, "SCPM GoogleApps uid ", "is ");
                m.append(intelligentBatterySaverGoogleAppPolicy.mGoogleAppsList.keyAt(i));
                printWriter.println(m.toString());
                printWriter.println("SCPM GoogleApps stats " + i + "= " + intelligentBatterySaverGoogleAppPolicy.mGoogleAppsList.valueAt(i));
            }
            IntelligentBatterySaverLogger intelligentBatterySaverLogger = this.mIBSLogger;
            if (intelligentBatterySaverLogger.mIsUsed) {
                printWriter.println();
                printWriter.println("IntelligentBatterySaverLogger history Log:");
                intelligentBatterySaverLogger.mIBSLog.dump((FileDescriptor) null, printWriter, (String[]) null);
                printWriter.println();
            }
        }
        IbsQuickDim ibsQuickDim = this.mIBSQuickDim;
        if (ibsQuickDim != null) {
            printWriter.println("");
            printWriter.println("SQD Version: 1.0");
            printWriter.println("SQD swich status : ");
            StringBuilder m2 = BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("UI on-off : "), ibsQuickDim.mUiControlEnabled, printWriter, "mCharging : ");
            m2.append(ibsQuickDim.mCharging);
            m2.append(" mSQDPowerSave : ");
            StringBuilder m3 = KillPolicyManager$$ExternalSyntheticOutline0.m(m2, ibsQuickDim.mSQDPowerSave, printWriter, "policy status : ");
            m3.append(ibsQuickDim.mQuickDimMode);
            printWriter.println(m3.toString());
            printWriter.println("block List:");
            ibsQuickDim.mBlockUnDimUidSet.forEach(new IbsQuickDim$$ExternalSyntheticLambda1(0, printWriter));
            printWriter.println("allow List:");
            ibsQuickDim.mAllowDimUidSet.forEach(new IbsQuickDim$$ExternalSyntheticLambda1(0, printWriter));
            if (strArr.length > 1 && strArr[0].equals("sqd_swich")) {
                if (strArr[1].equals("true")) {
                    ibsQuickDim.mUiControlEnabled = true;
                } else {
                    ibsQuickDim.mUiControlEnabled = false;
                }
            }
        }
        IntelligentBatterySaverScpmManager intelligentBatterySaverScpmManager = this.mIBSScpmManager;
        if (intelligentBatterySaverScpmManager != null) {
            printWriter.println("");
            printWriter.println("IntelligentBatterySaverScpmManager adapt to scpm v2 ");
            printWriter.println("IBS 's mPolicyControlSwitch:" + Integer.toBinaryString(intelligentBatterySaverScpmManager.mPolicyControlSwitch));
        }
        final int i2 = 0;
        Optional.ofNullable(this.mSleepModePolicyController).ifPresent(new Consumer() { // from class: com.android.server.ibs.IntelligentBatterySaverService$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                switch (i2) {
                    case 0:
                        PrintWriter printWriter2 = printWriter;
                        String[] strArr2 = strArr;
                        SleepModePolicyController sleepModePolicyController = (SleepModePolicyController) obj;
                        sleepModePolicyController.getClass();
                        printWriter2.println("");
                        printWriter2.println("SleepModePolicy ");
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("get current state :"), sleepModePolicyController.mSleepModeEnabled, printWriter2);
                        if (sleepModePolicyController.mSleepModeEnabled) {
                            if (SleepModeUtil.getTime(sleepModePolicyController.mStartTime.toString()) > SleepModeUtil.getTime(sleepModePolicyController.mEndTime.toString())) {
                                printWriter2.println("Set time from " + sleepModePolicyController.mStartTime + " to next day " + sleepModePolicyController.mEndTime);
                            } else {
                                printWriter2.println("Set time from " + sleepModePolicyController.mStartTime + " to " + sleepModePolicyController.mEndTime);
                            }
                        }
                        if (strArr2.length != 2 || !"sleepMode".equals(strArr2[0])) {
                            if (strArr2.length != 4 || !"sleepMode".equals(strArr2[0])) {
                                if (strArr2.length != 1 || !"getSleepTime".equals(strArr2[0])) {
                                    if (strArr2.length == 2 && "rubinEvent".equals(strArr2[0])) {
                                        sleepModePolicyController.setRubinEvent(strArr2[1]);
                                        printWriter2.println("set runbin event");
                                        break;
                                    }
                                } else {
                                    printWriter2.println("get sleep time");
                                    Bundle sleepTime = sleepModePolicyController.getSleepTime();
                                    if (sleepTime != null) {
                                        printWriter2.println("start time is: " + sleepTime.getLong("bundle_start_sleep_time_key"));
                                        printWriter2.println("end time is: " + sleepTime.getLong("bundle_end_sleep_time_key"));
                                        break;
                                    }
                                }
                            } else {
                                int parseInt2 = Integer.parseInt(strArr2[1]);
                                AccountManagerServiceShellCommand$$ExternalSyntheticOutline0.m(printWriter2, "set new state :", parseInt2);
                                if (parseInt2 == 1) {
                                    sleepModePolicyController.setSleepModeEnable(true);
                                    printWriter2.println("enable sleep mode");
                                    sleepModePolicyController.setSleepTime(SleepModeUtil.getTime(strArr2[2]), SleepModeUtil.getTime(strArr2[3]));
                                    printWriter2.println("set sleep time");
                                    break;
                                }
                            }
                        } else {
                            int parseInt3 = Integer.parseInt(strArr2[1]);
                            AccountManagerServiceShellCommand$$ExternalSyntheticOutline0.m(printWriter2, "set new state :", parseInt3);
                            if (parseInt3 == 0) {
                                sleepModePolicyController.setSleepModeEnable(false);
                                printWriter2.println("diable sleep mode");
                                break;
                            }
                        }
                        break;
                    default:
                        PrintWriter printWriter3 = printWriter;
                        String[] strArr3 = strArr;
                        IntelligentBatterySaverDexoptManager intelligentBatterySaverDexoptManager = (IntelligentBatterySaverDexoptManager) obj;
                        intelligentBatterySaverDexoptManager.getClass();
                        if (strArr3.length == 3 && "dexopt".equals(strArr3[0])) {
                            printWriter3.println("");
                            printWriter3.println("do dexopt");
                            if (!"1".equals(strArr3[1])) {
                                if ("2".equals(strArr3[1])) {
                                    ArrayList arrayList = new ArrayList();
                                    arrayList.add(strArr3[2]);
                                    printWriter3.println("results are " + intelligentBatterySaverDexoptManager.dexoptPackages(arrayList));
                                    break;
                                }
                            } else {
                                printWriter3.println("result is " + ((ArrayList) intelligentBatterySaverDexoptManager.dexoptPackages(Collections.singletonList(strArr3[2]))).get(0));
                                break;
                            }
                        }
                        break;
                }
            }
        });
        Optional.ofNullable(this.mSleepModeLogger).ifPresent(new Consumer(printWriter, strArr) { // from class: com.android.server.ibs.IntelligentBatterySaverService$$ExternalSyntheticLambda1
            public final /* synthetic */ PrintWriter f$0;

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                PrintWriter printWriter2 = this.f$0;
                SleepModeLogger sleepModeLogger = (SleepModeLogger) obj;
                if (sleepModeLogger.mIsUsed) {
                    printWriter2.println();
                    printWriter2.println("SleepModeLogger history Log:");
                    sleepModeLogger.mSleepModeLog.dump((FileDescriptor) null, printWriter2, (String[]) null);
                    printWriter2.println();
                }
            }
        });
        final int i3 = 1;
        Optional.ofNullable(this.mIBSDexoptManager).ifPresent(new Consumer() { // from class: com.android.server.ibs.IntelligentBatterySaverService$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                switch (i3) {
                    case 0:
                        PrintWriter printWriter2 = printWriter;
                        String[] strArr2 = strArr;
                        SleepModePolicyController sleepModePolicyController = (SleepModePolicyController) obj;
                        sleepModePolicyController.getClass();
                        printWriter2.println("");
                        printWriter2.println("SleepModePolicy ");
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("get current state :"), sleepModePolicyController.mSleepModeEnabled, printWriter2);
                        if (sleepModePolicyController.mSleepModeEnabled) {
                            if (SleepModeUtil.getTime(sleepModePolicyController.mStartTime.toString()) > SleepModeUtil.getTime(sleepModePolicyController.mEndTime.toString())) {
                                printWriter2.println("Set time from " + sleepModePolicyController.mStartTime + " to next day " + sleepModePolicyController.mEndTime);
                            } else {
                                printWriter2.println("Set time from " + sleepModePolicyController.mStartTime + " to " + sleepModePolicyController.mEndTime);
                            }
                        }
                        if (strArr2.length != 2 || !"sleepMode".equals(strArr2[0])) {
                            if (strArr2.length != 4 || !"sleepMode".equals(strArr2[0])) {
                                if (strArr2.length != 1 || !"getSleepTime".equals(strArr2[0])) {
                                    if (strArr2.length == 2 && "rubinEvent".equals(strArr2[0])) {
                                        sleepModePolicyController.setRubinEvent(strArr2[1]);
                                        printWriter2.println("set runbin event");
                                        break;
                                    }
                                } else {
                                    printWriter2.println("get sleep time");
                                    Bundle sleepTime = sleepModePolicyController.getSleepTime();
                                    if (sleepTime != null) {
                                        printWriter2.println("start time is: " + sleepTime.getLong("bundle_start_sleep_time_key"));
                                        printWriter2.println("end time is: " + sleepTime.getLong("bundle_end_sleep_time_key"));
                                        break;
                                    }
                                }
                            } else {
                                int parseInt2 = Integer.parseInt(strArr2[1]);
                                AccountManagerServiceShellCommand$$ExternalSyntheticOutline0.m(printWriter2, "set new state :", parseInt2);
                                if (parseInt2 == 1) {
                                    sleepModePolicyController.setSleepModeEnable(true);
                                    printWriter2.println("enable sleep mode");
                                    sleepModePolicyController.setSleepTime(SleepModeUtil.getTime(strArr2[2]), SleepModeUtil.getTime(strArr2[3]));
                                    printWriter2.println("set sleep time");
                                    break;
                                }
                            }
                        } else {
                            int parseInt3 = Integer.parseInt(strArr2[1]);
                            AccountManagerServiceShellCommand$$ExternalSyntheticOutline0.m(printWriter2, "set new state :", parseInt3);
                            if (parseInt3 == 0) {
                                sleepModePolicyController.setSleepModeEnable(false);
                                printWriter2.println("diable sleep mode");
                                break;
                            }
                        }
                        break;
                    default:
                        PrintWriter printWriter3 = printWriter;
                        String[] strArr3 = strArr;
                        IntelligentBatterySaverDexoptManager intelligentBatterySaverDexoptManager = (IntelligentBatterySaverDexoptManager) obj;
                        intelligentBatterySaverDexoptManager.getClass();
                        if (strArr3.length == 3 && "dexopt".equals(strArr3[0])) {
                            printWriter3.println("");
                            printWriter3.println("do dexopt");
                            if (!"1".equals(strArr3[1])) {
                                if ("2".equals(strArr3[1])) {
                                    ArrayList arrayList = new ArrayList();
                                    arrayList.add(strArr3[2]);
                                    printWriter3.println("results are " + intelligentBatterySaverDexoptManager.dexoptPackages(arrayList));
                                    break;
                                }
                            } else {
                                printWriter3.println("result is " + ((ArrayList) intelligentBatterySaverDexoptManager.dexoptPackages(Collections.singletonList(strArr3[2]))).get(0));
                                break;
                            }
                        }
                        break;
                }
            }
        });
    }

    public final long[] getGain() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "IntelligentBatterySaverService");
        IbsQuickDim ibsQuickDim = this.mIBSQuickDim;
        return new long[]{ibsQuickDim.mChargingFinishTime, ((long) ibsQuickDim.mSQDPowerSave) + 1};
    }

    public final Bundle getOperationHistory() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "IntelligentBatterySaverService");
        SleepModePolicyController sleepModePolicyController = this.mSleepModePolicyController;
        sleepModePolicyController.getClass();
        Slog.d("SleepModePolicyController", "getOperationHistory");
        long j = SharePrefUtils.getLong(sleepModePolicyController.mContext, "pref_sleep_mode_trigger_time_key", 0L);
        long j2 = SharePrefUtils.getLong(sleepModePolicyController.mContext, "pref_sleep_mode_cancel_time_key", 0L);
        if (j == 0 || j2 == 0) {
            return null;
        }
        int i = 0;
        try {
            i = sleepModePolicyController.mContext.getSharedPreferences("sleep_mode_pref", 0).getInt("pref_sleep_mode_policy_state_key", 0);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        sleepModePolicyController.mSysState = i;
        Bundle bundle = new Bundle();
        bundle.putLong("bundle_start_time_key", j);
        bundle.putLong("bundle_end_time_key", j2);
        bundle.putBoolean("bundle_psm_key", sleepModePolicyController.testState(1));
        bundle.putBoolean("bundle_gps_key", sleepModePolicyController.testState(4));
        bundle.putBoolean("bundle_bt_key", sleepModePolicyController.testState(8));
        bundle.putBoolean("bundle_wifi_key", sleepModePolicyController.testState(2));
        bundle.putBoolean("bundle_nearby_key", sleepModePolicyController.testState(16));
        bundle.putBoolean("bundle_master_sync_key", sleepModePolicyController.testState(32));
        bundle.putBoolean("bundle_notification_key", sleepModePolicyController.testState(64));
        bundle.putBoolean("bundle_camera_flash_notification_key", sleepModePolicyController.testState(128));
        return bundle;
    }

    public final Map getScreenQuickDimApps() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "IntelligentBatterySaverService");
        IbsQuickDim ibsQuickDim = this.mIBSQuickDim;
        ibsQuickDim.getClass();
        HashMap hashMap = new HashMap();
        Cursor query = ibsQuickDim.mSQLiteSQDwhilteList.getDataOperator(1).query(ibsQuickDim.mSQLiteSQDwhilteList.mDb);
        if (query != null) {
            while (query.moveToNext()) {
                try {
                    String string = query.getString(query.getColumnIndex("PackageName"));
                    String string2 = query.getString(query.getColumnIndex("Uid"));
                    hashMap.put(string + "_" + string2, Integer.valueOf(string2));
                } finally {
                    query.close();
                }
            }
        }
        return hashMap;
    }

    public final Bundle getSleepTime() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "IntelligentBatterySaverService");
        return this.mSleepModePolicyController.getSleepTime();
    }

    public final Map getSqdBlockList() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "IntelligentBatterySaverService");
        IbsQuickDim ibsQuickDim = this.mIBSQuickDim;
        ibsQuickDim.getClass();
        HashMap hashMap = new HashMap();
        Cursor query = ibsQuickDim.mSQLiteSQDwhilteList.getDataOperator(0).query(ibsQuickDim.mSQLiteSQDwhilteList.mDb);
        if (query != null) {
            while (query.moveToNext()) {
                try {
                    String string = query.getString(query.getColumnIndex("PackageName"));
                    String string2 = query.getString(query.getColumnIndex("Uid"));
                    hashMap.put(string + "_" + string2, string2);
                } finally {
                    query.close();
                }
            }
        }
        return hashMap;
    }

    public final boolean isEnableSerive() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "IntelligentBatterySaverService");
        SleepModePolicyController sleepModePolicyController = this.mSleepModePolicyController;
        AnyMotionDetector$$ExternalSyntheticOutline0.m("SleepModePolicyController", new StringBuilder("isEnableSerive: "), sleepModePolicyController.mSleepModeEnabled);
        return sleepModePolicyController.mSleepModeEnabled;
    }

    public final boolean isSqdSupport() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "IntelligentBatterySaverService");
        IbsQuickDim ibsQuickDim = this.mIBSQuickDim;
        ibsQuickDim.getClass();
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        boolean z = false;
        try {
            try {
                IBinder iBinder = ibsQuickDim.mSFSevices;
                if (iBinder == null && iBinder == null) {
                    ibsQuickDim.mSFSevices = ServiceManager.getService("SurfaceFlinger");
                }
                if (ibsQuickDim.mSFSevices != null) {
                    obtain.writeInterfaceToken("android.ui.ISurfaceComposer");
                    z = ibsQuickDim.mSFSevices.transact(1013, obtain, obtain2, 0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return z;
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }

    public final boolean isSqdUiControlEnabled() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "IntelligentBatterySaverService");
        return this.mIBSQuickDim.mUiControlEnabled;
    }

    public final boolean removeScreenQuickDimApp(String str, int i) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "IntelligentBatterySaverService");
        IbsQuickDim ibsQuickDim = this.mIBSQuickDim;
        ibsQuickDim.getClass();
        Log.d("IbsQuickDim", " removeScreenQuickDimApp uid = " + i + " pkgName = " + str);
        if (ibsQuickDim.mAllowDimUidSet.contains(Integer.valueOf(i))) {
            ibsQuickDim.mAllowDimUidSet.remove(Integer.valueOf(i));
        }
        int delete = ibsQuickDim.mSQLiteSQDwhilteList.getDataOperator(1).delete(new String[]{str, Integer.toString(i)});
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(delete, " ret = ", "IbsQuickDim");
        return delete != -1;
    }

    public final boolean removeSqdBlockList(int i, String str) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "IntelligentBatterySaverService");
        IbsQuickDim ibsQuickDim = this.mIBSQuickDim;
        ibsQuickDim.getClass();
        Log.d("IbsQuickDim", " removeBlockList uid = " + i + " pkgName = " + str);
        if (ibsQuickDim.mBlockUnDimUidSet.contains(Integer.valueOf(i))) {
            ibsQuickDim.mBlockUnDimUidSet.remove(Integer.valueOf(i));
        }
        int delete = ibsQuickDim.mSQLiteSQDwhilteList.getDataOperator(0).delete(new String[]{str, Integer.toString(i)});
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(delete, " ret = ", "IbsQuickDim");
        return delete != -1;
    }

    public final void setRubinEvent(String str) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "IntelligentBatterySaverService");
        this.mSleepModePolicyController.setRubinEvent(str);
    }

    public final void setSarrUiControlEnable(boolean z) {
    }

    public final void setSleepModeEnabled(boolean z) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "IntelligentBatterySaverService");
        this.mSleepModePolicyController.setSleepModeEnable(z);
    }

    public final void setSleepTime(long j, long j2) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "IntelligentBatterySaverService");
        this.mSleepModePolicyController.setSleepTime(j, j2);
    }

    public final void setSqdUiControlEnabled(boolean z) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", "IntelligentBatterySaverService");
        this.mIBSQuickDim.setUicontrolEnable(z);
    }
}
