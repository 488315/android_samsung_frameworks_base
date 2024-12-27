package com.android.server.usb;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.usb.IDisplayPortAltModeInfoListener;
import android.hardware.usb.IUsbManager;
import android.hardware.usb.IUsbOperationInternal;
import android.hardware.usb.ParcelableUsbPort;
import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.hardware.usb.UsbPort;
import android.hardware.usb.UsbPortStatus;
import android.os.Binder;
import android.os.Bundle;
import android.os.FactoryTest;
import android.os.FileUtils;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.telephony.SubscriptionManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.sysfwutil.DexObserver;
import android.util.sysfwutil.Slog;
import com.android.internal.content.PackageMonitor;
import com.android.internal.hidden_from_bootclasspath.android.hardware.usb.flags.Flags;
import com.android.internal.os.BackgroundThread;
import com.android.internal.util.Preconditions;
import com.android.internal.widget.LockPatternUtils;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.FgThread;
import com.android.server.KnoxCaptureInputFilter$$ExternalSyntheticOutline0;
import com.android.server.SystemServerInitThreadPool;
import com.android.server.SystemService;
import com.android.server.pm.PersonaServiceHelper;
import com.android.server.usb.UsbDeviceManager;
import com.android.server.usb.UsbPortManager;
import com.android.server.usb.UsbService;
import com.android.server.usb.hal.port.RawPortInfo;
import com.android.server.usb.hal.port.UsbPortHal;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class UsbService extends IUsbManager.Stub {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final UsbAlsaManager mAlsaManager;
    public final Context mContext;
    public int mCurrentUserId;
    public final UsbDeviceManager mDeviceManager;
    public final DexObserver mDexObserver;
    public final UsbHostManager mHostManager;
    public final UsbHostRestrictor mHostRestrictor;
    public final UsbPermissionManager mPermissionManager;
    public final UsbPortManager mPortManager;
    public final UsbSettingsManager mSettingsManager;
    public final UsbMonitorImpl mUsbMonitorImpl;
    public final UsbUI mUsbUI;
    public final UserManager mUserManager;
    public final Object mLock = new Object();
    public final ArrayMap mUsbDisableRequesters = new ArrayMap();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.usb.UsbService$2, reason: invalid class name */
    public final class AnonymousClass2 implements Runnable {
        public AnonymousClass2() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            UsbDeviceManager usbDeviceManager = UsbService.this.mDeviceManager;
            if (usbDeviceManager != null) {
                usbDeviceManager.getClass();
                Slog.d("UsbDeviceManager", "setNextUsbModeToDefault");
                UsbDeviceManager.mSetNextUsbModeToDefault = true;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Lifecycle extends SystemService {
        public final CompletableFuture mOnActivityManagerPhaseFinished;
        public final CompletableFuture mOnStartFinished;
        public UsbService mUsbService;

        public Lifecycle(Context context) {
            super(context);
            this.mOnStartFinished = new CompletableFuture();
            this.mOnActivityManagerPhaseFinished = new CompletableFuture();
        }

        @Override // com.android.server.SystemService
        public final void onBootPhase(int i) {
            if (i == 550) {
                Slog.d("UsbService", "UsbService -> SystemService.PHASE_ACTIVITY_MANAGER_READY");
                SystemServerInitThreadPool.submit("UsbService$Lifecycle#onBootPhase", new UsbService$Lifecycle$$ExternalSyntheticLambda0(this, 1));
                return;
            }
            if (i == 1000) {
                Slog.d("UsbService", "UsbService -> SystemService.PHASE_BOOT_COMPLETED");
                this.mOnActivityManagerPhaseFinished.join();
                UsbService usbService = this.mUsbService;
                UsbUI usbUI = usbService.mUsbUI;
                if (usbUI != null) {
                    Slog.d("UsbUI", "boot completed");
                    usbUI.mBootCompleted = true;
                    if (!"".equals(usbUI.mUsbBlkPendingIntent)) {
                        usbUI.sendingUsbRestrictionStateIntent(usbUI.mUsbBlkPendingIntent);
                    }
                    for (UsbDevice usbDevice : ((UsbManager) usbUI.mContext.getSystemService("usb")).getDeviceList().values()) {
                        int interfaceCount = usbDevice.getInterfaceCount();
                        for (int i2 = 0; i2 < interfaceCount; i2++) {
                            UsbInterface usbInterface = usbDevice.getInterface(i2);
                            usbUI.notifyUsbInterface(usbInterface.getInterfaceClass(), usbInterface.getInterfaceSubclass(), usbInterface.getInterfaceProtocol(), "add");
                        }
                    }
                }
                UsbHostManager usbHostManager = usbService.mHostManager;
                if (usbHostManager != null) {
                    Slog.d("UsbHostManager", "boot completed");
                    usbHostManager.mBootCompleted = true;
                }
                UsbDeviceManager usbDeviceManager = usbService.mDeviceManager;
                if (usbDeviceManager != null) {
                    Slog.d("UsbDeviceManager", "boot completed");
                    usbDeviceManager.mHandler.sendEmptyMessage(4);
                }
                if (Flags.enableUsbDataSignalStaking()) {
                    usbService.new PackageUninstallMonitor().register(usbService.mContext, UserHandle.ALL, BackgroundThread.getHandler());
                    new LockPatternUtils(usbService.mContext).registerStrongAuthTracker(usbService.new StrongAuthTracker(usbService.mContext, BackgroundThread.getHandler().getLooper()));
                }
                UsbHostRestrictor usbHostRestrictor = usbService.mHostRestrictor;
                if (usbHostRestrictor != null) {
                    Slog.d("UsbHostRestrictor", "initSetUsbBlock STARTED");
                    synchronized (usbHostRestrictor.mUsbRestrictLock) {
                        try {
                            if (UsbHostRestrictor.mSecureKeyguardShowing) {
                                if (!UsbHostRestrictor.isAdbOnlyMode() && UsbHostRestrictor.mSettingBlockUsbLock != 0) {
                                    Slog.d("UsbHostRestrictor", "initSetUsbBlock LOCK_SCREEN_BLOCK : ON");
                                    if (UsbHostRestrictor.mLockStatus == 2) {
                                        Slog.d("UsbHostRestrictor", "initSetUsbBlock USB already restricted");
                                    } else {
                                        usbHostRestrictor.writeScrLckBlkSysNodetoFile("2");
                                        UsbHostRestrictor.mLockStatus = 1;
                                        if (!UsbHostRestrictor.mIsDeviceConnected && !UsbHostRestrictor.mIsHostConnected) {
                                            UsbHostRestrictor.startLockTimer();
                                        }
                                    }
                                }
                                Slog.d("UsbHostRestrictor", "initSetUsbBlock LOCK_SCREEN_BLOCK : OFF");
                                usbHostRestrictor.writeScrLckBlkSysNodetoFile("1");
                                UsbHostRestrictor.mLockStatus = 3;
                                UsbHostRestrictor.stopLockTimer();
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    File file = new File("/efs/usb_con_hist");
                    if (file.exists()) {
                        file.delete();
                    }
                    boolean equals = SystemProperties.get("sys.config.usbSIMblock", "true").equals("true");
                    String salesCode = UsbHostRestrictor.getSalesCode();
                    if ("null".equals(salesCode) || salesCode == null) {
                        Slog.d("UsbHostRestrictor", "checkUsbBlockingCondition : salesCode is null");
                        salesCode = "OXA";
                    }
                    int size = SubscriptionManager.from(usbHostRestrictor.mContext).getAllSubscriptionInfoList().size();
                    if (size != 0) {
                        if (size <= 0) {
                            if (size < 0) {
                                Slog.d("UsbHostRestrictor", "SIM COUNT value is abnormal");
                                return;
                            }
                            return;
                        } else {
                            Slog.d("UsbHostRestrictor", "SIM has been already inserted");
                            String checkWriteValue = UsbHostRestrictor.checkWriteValue();
                            if (UsbHostRestrictor.getUsbHostDisableSysNodeWritable()) {
                                usbHostRestrictor.writeDisableSysNodetoFile(checkWriteValue);
                                return;
                            } else {
                                Slog.d("UsbHostRestrictor", "Can NOT Write Disable Sys Node 2");
                                return;
                            }
                        }
                    }
                    Slog.d("UsbHostRestrictor", "SIM was never inserted");
                    if (equals && !FactoryTest.isFactoryBinary() && ("CHM".equals(salesCode) || "CBK".equals(salesCode))) {
                        Slog.d("UsbHostRestrictor", "NEED to BLOCK by NO SIM");
                        UsbHostRestrictor.isSIMBlock = true;
                    }
                    String checkWriteValue2 = UsbHostRestrictor.checkWriteValue();
                    if (UsbHostRestrictor.getUsbHostDisableSysNodeWritable()) {
                        usbHostRestrictor.writeDisableSysNodetoFile(checkWriteValue2);
                    } else {
                        Slog.d("UsbHostRestrictor", "Can NOT Write Disable Sys Node 1");
                    }
                }
            }
        }

        @Override // com.android.server.SystemService
        public final void onStart() {
            SystemServerInitThreadPool.submit("UsbService$Lifecycle#onStart", new UsbService$Lifecycle$$ExternalSyntheticLambda0(this, 0));
        }

        @Override // com.android.server.SystemService
        public final void onUserStopping(SystemService.TargetUser targetUser) {
            UsbService usbService = this.mUsbService;
            UserHandle userHandle = targetUser.getUserHandle();
            UsbSettingsManager usbSettingsManager = usbService.mSettingsManager;
            synchronized (usbSettingsManager.mSettingsByUser) {
                usbSettingsManager.mSettingsByUser.remove(userHandle.getIdentifier());
            }
            synchronized (usbSettingsManager.mSettingsByProfileGroup) {
                try {
                    if (usbSettingsManager.mSettingsByProfileGroup.indexOfKey(userHandle.getIdentifier()) >= 0) {
                        ((UsbProfileGroupSettingsManager) usbSettingsManager.mSettingsByProfileGroup.get(userHandle.getIdentifier())).mPackageMonitor.unregister();
                        usbSettingsManager.mSettingsByProfileGroup.remove(userHandle.getIdentifier());
                    } else {
                        int size = usbSettingsManager.mSettingsByProfileGroup.size();
                        for (int i = 0; i < size; i++) {
                            ((UsbProfileGroupSettingsManager) usbSettingsManager.mSettingsByProfileGroup.valueAt(i)).removeUser(userHandle);
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.SystemService
        public final void onUserSwitching(SystemService.TargetUser targetUser, final SystemService.TargetUser targetUser2) {
            FgThread.getHandler().postAtFrontOfQueue(new Runnable() { // from class: com.android.server.usb.UsbService$Lifecycle$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    UsbService.Lifecycle lifecycle = UsbService.Lifecycle.this;
                    SystemService.TargetUser targetUser3 = targetUser2;
                    UsbService usbService = lifecycle.mUsbService;
                    int userIdentifier = targetUser3.getUserIdentifier();
                    int i = UsbService.$r8$clinit;
                    usbService.onSwitchUser(userIdentifier);
                }
            });
        }

        @Override // com.android.server.SystemService
        public final void onUserUnlocking(SystemService.TargetUser targetUser) {
            UsbService usbService = this.mUsbService;
            int userIdentifier = targetUser.getUserIdentifier();
            UsbHostManager usbHostManager = usbService.mHostManager;
            if (usbHostManager != null) {
                Slog.d("UsbHostManager", "onUnlockUser: userHandle=" + userIdentifier);
                int i = usbHostManager.mCurrentUnlockedUser;
                usbHostManager.mCurrentUnlockedUser = userIdentifier;
                if (usbHostManager.mBootCompleted && i == -10000) {
                    synchronized (usbHostManager.mLock) {
                        try {
                            for (Map.Entry entry : usbHostManager.mDevices.entrySet()) {
                                Slog.d("UsbHostManager", "dealWithPendingDevices: deviceName=" + ((String) entry.getKey()));
                                usbHostManager.getCurrentUserSettings().deviceAttached((UsbDevice) entry.getValue());
                            }
                        } finally {
                        }
                    }
                }
            }
            UsbDeviceManager usbDeviceManager = usbService.mDeviceManager;
            if (usbDeviceManager != null) {
                usbDeviceManager.onKeyguardStateChanged(false);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PackageUninstallMonitor extends PackageMonitor {
        public PackageUninstallMonitor() {
        }

        public final void onUidRemoved(int i) {
            synchronized (UsbService.this.mUsbDisableRequesters) {
                try {
                    for (String str : UsbService.this.mUsbDisableRequesters.keySet()) {
                        ArraySet arraySet = (ArraySet) UsbService.this.mUsbDisableRequesters.get(str);
                        if (arraySet != null) {
                            arraySet.remove(Integer.valueOf(i));
                            if (arraySet.isEmpty()) {
                                UsbService.this.enableUsbData(str, true, 1, new IUsbOperationInternal.Default());
                            }
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class StrongAuthTracker extends LockPatternUtils.StrongAuthTracker {
        public boolean mLockdownModeStatus;

        public StrongAuthTracker(Context context, Looper looper) {
            super(context, looper);
        }

        public final synchronized void onStrongAuthRequiredChanged(int i) {
            boolean z = (getStrongAuthForUser(i) & 32) != 0;
            if (this.mLockdownModeStatus == z) {
                return;
            }
            this.mLockdownModeStatus = z;
            for (UsbPort usbPort : UsbService.this.mPortManager.getPorts()) {
                UsbService.this.enableUsbData(usbPort.getId(), !z, 2, new IUsbOperationInternal.Default());
            }
        }
    }

    public UsbService(Context context) {
        HandlerThread m = KnoxCaptureInputFilter$$ExternalSyntheticOutline0.m("UsbHostNotification");
        this.mUsbUI = new UsbUI(context, m.getLooper());
        this.mContext = context;
        this.mUserManager = (UserManager) context.getSystemService(UserManager.class);
        this.mSettingsManager = new UsbSettingsManager(context, this);
        UsbPermissionManager usbPermissionManager = new UsbPermissionManager(context, this);
        this.mPermissionManager = usbPermissionManager;
        UsbAlsaManager usbAlsaManager = new UsbAlsaManager(context);
        this.mAlsaManager = usbAlsaManager;
        PackageManager packageManager = context.getPackageManager();
        if (TextUtils.equals("sep_basic", "sep_basic")) {
            this.mDexObserver = new DexObserver();
        }
        if (packageManager.hasSystemFeature("android.hardware.usb.host")) {
            this.mHostManager = new UsbHostManager(context, usbAlsaManager, usbPermissionManager);
        }
        if (BatteryService$$ExternalSyntheticOutline0.m45m("/sys/class/android_usb")) {
            this.mDeviceManager = new UsbDeviceManager(context, usbAlsaManager, usbPermissionManager, this.mDexObserver);
        }
        if (this.mHostManager != null || this.mDeviceManager != null) {
            this.mPortManager = new UsbPortManager(context);
        }
        if (this.mDeviceManager != null) {
            Slog.d("UsbService", "At UsbService, UsbDeviceManager -> UsbHostRestrictor");
            this.mHostRestrictor = new UsbHostRestrictor(context, this.mDeviceManager);
            Slog.d("UsbService", "At UsbService, UsbDeviceManager -> UsbMonitorImpl");
            this.mUsbMonitorImpl = new UsbMonitorImpl(m.getLooper());
        }
        onSwitchUser(0);
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.server.usb.UsbService.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                UsbDeviceManager usbDeviceManager;
                String action = intent.getAction();
                if ("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED".equals(action) && (usbDeviceManager = UsbService.this.mDeviceManager) != null) {
                    usbDeviceManager.mHandler.sendEmptyMessage(6);
                }
                if (!"com.samsung.android.knox.intent.action.MTP_DISABLED_INTERNAL".equals(action) || UsbService.this.mDeviceManager == null) {
                    return;
                }
                Slog.d("UsbService", " updating MtpRestrictions for MDM ");
                UsbService.this.mDeviceManager.mHandler.sendEmptyMessage(24);
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.setPriority(1000);
        intentFilter.addAction("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED");
        intentFilter.addAction("com.samsung.android.knox.intent.action.MTP_DISABLED_INTERNAL");
        context.registerReceiverAsUser(broadcastReceiver, UserHandle.ALL, intentFilter, null, null);
    }

    public UsbService(Context context, UsbPortManager usbPortManager, UsbAlsaManager usbAlsaManager, UserManager userManager, UsbSettingsManager usbSettingsManager) {
        this.mContext = context;
        this.mPortManager = usbPortManager;
        this.mAlsaManager = usbAlsaManager;
        this.mUserManager = userManager;
        this.mSettingsManager = usbSettingsManager;
        this.mPermissionManager = new UsbPermissionManager(context, this);
    }

    public final void addAccessoryPackagesToPreferenceDenied(UsbAccessory usbAccessory, String[] strArr, UserHandle userHandle) {
        Objects.requireNonNull(usbAccessory);
        String[] strArr2 = (String[]) Preconditions.checkArrayElementsNotNull(strArr, "packageNames");
        Objects.requireNonNull(userHandle);
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_USB", null);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mSettingsManager.getSettingsForProfileGroup(userHandle).addAccessoryPackagesToDenied(usbAccessory, strArr2, userHandle);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void addDevicePackagesToPreferenceDenied(UsbDevice usbDevice, String[] strArr, UserHandle userHandle) {
        Objects.requireNonNull(usbDevice);
        String[] strArr2 = (String[]) Preconditions.checkArrayElementsNotNull(strArr, "packageNames");
        Objects.requireNonNull(userHandle);
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_USB", null);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mSettingsManager.getSettingsForProfileGroup(userHandle).addDevicePackagesToDenied(usbDevice, strArr2, userHandle);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void clearDefaults(String str, int i) {
        String str2 = (String) Preconditions.checkStringNotEmpty(str);
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_USB", null);
        UserHandle of = UserHandle.of(i);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mSettingsManager.getSettingsForProfileGroup(of).clearDefaults(str2, of);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:112:0x01ff  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x021b  */
    /* JADX WARN: Removed duplicated region for block: B:160:0x029a  */
    /* JADX WARN: Removed duplicated region for block: B:169:0x02c0 A[Catch: all -> 0x008e, TryCatch #0 {all -> 0x008e, blocks: (B:7:0x0024, B:9:0x0034, B:11:0x0037, B:14:0x0047, B:17:0x0066, B:19:0x006c, B:32:0x00ae, B:36:0x00cb, B:49:0x0108, B:53:0x0123, B:55:0x0127, B:60:0x00e1, B:63:0x00ec, B:66:0x00f6, B:71:0x0086, B:74:0x0091, B:77:0x009c, B:82:0x0158, B:85:0x016e, B:87:0x0174, B:88:0x017e, B:98:0x01b4, B:103:0x01d3, B:105:0x01d6, B:114:0x0202, B:116:0x021c, B:121:0x01e7, B:124:0x01f1, B:128:0x021f, B:130:0x0223, B:134:0x0182, B:137:0x018d, B:140:0x0197, B:143:0x019f, B:146:0x023f, B:148:0x024d, B:150:0x0250, B:152:0x025c, B:153:0x0277, B:162:0x029d, B:167:0x02b8, B:169:0x02c0, B:170:0x02d1, B:179:0x02f7, B:183:0x0312, B:185:0x031a, B:186:0x032b, B:195:0x0351, B:199:0x036c, B:201:0x0370, B:203:0x033a, B:206:0x0342, B:209:0x0328, B:211:0x02e0, B:214:0x02e8, B:217:0x02ce, B:219:0x0286, B:222:0x028e, B:225:0x0270, B:226:0x0395, B:228:0x03a3, B:230:0x03a7, B:232:0x03ae, B:233:0x03c7, B:235:0x03d3, B:237:0x03d7, B:239:0x03de, B:240:0x03f7, B:242:0x0403, B:244:0x0407, B:246:0x040b, B:247:0x0424, B:249:0x0430, B:251:0x0434, B:253:0x0442, B:254:0x045b, B:256:0x0467, B:258:0x046b, B:260:0x0475, B:261:0x048e, B:263:0x0499, B:265:0x049d, B:267:0x04a4, B:268:0x04bd, B:270:0x04c9, B:272:0x04cd, B:274:0x04f6, B:275:0x0512, B:277:0x051e, B:279:0x0522, B:281:0x0529, B:282:0x0549, B:284:0x0554, B:286:0x0558, B:288:0x0566, B:290:0x056e, B:294:0x057a, B:292:0x059e, B:296:0x05a3, B:298:0x05af, B:300:0x05b3, B:302:0x05b7, B:303:0x05c8, B:305:0x05d3, B:306:0x05da, B:311:0x0761, B:312:0x077d, B:314:0x0781, B:315:0x0784, B:317:0x0788, B:318:0x078b, B:320:0x078f, B:321:0x079a, B:323:0x07a1, B:325:0x07a5, B:326:0x07a8, B:328:0x07ac, B:329:0x07af, B:331:0x07b3, B:332:0x07b6, B:334:0x07ba, B:335:0x07bd, B:336:0x076e), top: B:6:0x0024 }] */
    /* JADX WARN: Removed duplicated region for block: B:172:0x02da  */
    /* JADX WARN: Removed duplicated region for block: B:177:0x02f4  */
    /* JADX WARN: Removed duplicated region for block: B:185:0x031a A[Catch: all -> 0x008e, TryCatch #0 {all -> 0x008e, blocks: (B:7:0x0024, B:9:0x0034, B:11:0x0037, B:14:0x0047, B:17:0x0066, B:19:0x006c, B:32:0x00ae, B:36:0x00cb, B:49:0x0108, B:53:0x0123, B:55:0x0127, B:60:0x00e1, B:63:0x00ec, B:66:0x00f6, B:71:0x0086, B:74:0x0091, B:77:0x009c, B:82:0x0158, B:85:0x016e, B:87:0x0174, B:88:0x017e, B:98:0x01b4, B:103:0x01d3, B:105:0x01d6, B:114:0x0202, B:116:0x021c, B:121:0x01e7, B:124:0x01f1, B:128:0x021f, B:130:0x0223, B:134:0x0182, B:137:0x018d, B:140:0x0197, B:143:0x019f, B:146:0x023f, B:148:0x024d, B:150:0x0250, B:152:0x025c, B:153:0x0277, B:162:0x029d, B:167:0x02b8, B:169:0x02c0, B:170:0x02d1, B:179:0x02f7, B:183:0x0312, B:185:0x031a, B:186:0x032b, B:195:0x0351, B:199:0x036c, B:201:0x0370, B:203:0x033a, B:206:0x0342, B:209:0x0328, B:211:0x02e0, B:214:0x02e8, B:217:0x02ce, B:219:0x0286, B:222:0x028e, B:225:0x0270, B:226:0x0395, B:228:0x03a3, B:230:0x03a7, B:232:0x03ae, B:233:0x03c7, B:235:0x03d3, B:237:0x03d7, B:239:0x03de, B:240:0x03f7, B:242:0x0403, B:244:0x0407, B:246:0x040b, B:247:0x0424, B:249:0x0430, B:251:0x0434, B:253:0x0442, B:254:0x045b, B:256:0x0467, B:258:0x046b, B:260:0x0475, B:261:0x048e, B:263:0x0499, B:265:0x049d, B:267:0x04a4, B:268:0x04bd, B:270:0x04c9, B:272:0x04cd, B:274:0x04f6, B:275:0x0512, B:277:0x051e, B:279:0x0522, B:281:0x0529, B:282:0x0549, B:284:0x0554, B:286:0x0558, B:288:0x0566, B:290:0x056e, B:294:0x057a, B:292:0x059e, B:296:0x05a3, B:298:0x05af, B:300:0x05b3, B:302:0x05b7, B:303:0x05c8, B:305:0x05d3, B:306:0x05da, B:311:0x0761, B:312:0x077d, B:314:0x0781, B:315:0x0784, B:317:0x0788, B:318:0x078b, B:320:0x078f, B:321:0x079a, B:323:0x07a1, B:325:0x07a5, B:326:0x07a8, B:328:0x07ac, B:329:0x07af, B:331:0x07b3, B:332:0x07b6, B:334:0x07ba, B:335:0x07bd, B:336:0x076e), top: B:6:0x0024 }] */
    /* JADX WARN: Removed duplicated region for block: B:188:0x0334  */
    /* JADX WARN: Removed duplicated region for block: B:193:0x034e  */
    /* JADX WARN: Removed duplicated region for block: B:201:0x0370 A[Catch: all -> 0x008e, TryCatch #0 {all -> 0x008e, blocks: (B:7:0x0024, B:9:0x0034, B:11:0x0037, B:14:0x0047, B:17:0x0066, B:19:0x006c, B:32:0x00ae, B:36:0x00cb, B:49:0x0108, B:53:0x0123, B:55:0x0127, B:60:0x00e1, B:63:0x00ec, B:66:0x00f6, B:71:0x0086, B:74:0x0091, B:77:0x009c, B:82:0x0158, B:85:0x016e, B:87:0x0174, B:88:0x017e, B:98:0x01b4, B:103:0x01d3, B:105:0x01d6, B:114:0x0202, B:116:0x021c, B:121:0x01e7, B:124:0x01f1, B:128:0x021f, B:130:0x0223, B:134:0x0182, B:137:0x018d, B:140:0x0197, B:143:0x019f, B:146:0x023f, B:148:0x024d, B:150:0x0250, B:152:0x025c, B:153:0x0277, B:162:0x029d, B:167:0x02b8, B:169:0x02c0, B:170:0x02d1, B:179:0x02f7, B:183:0x0312, B:185:0x031a, B:186:0x032b, B:195:0x0351, B:199:0x036c, B:201:0x0370, B:203:0x033a, B:206:0x0342, B:209:0x0328, B:211:0x02e0, B:214:0x02e8, B:217:0x02ce, B:219:0x0286, B:222:0x028e, B:225:0x0270, B:226:0x0395, B:228:0x03a3, B:230:0x03a7, B:232:0x03ae, B:233:0x03c7, B:235:0x03d3, B:237:0x03d7, B:239:0x03de, B:240:0x03f7, B:242:0x0403, B:244:0x0407, B:246:0x040b, B:247:0x0424, B:249:0x0430, B:251:0x0434, B:253:0x0442, B:254:0x045b, B:256:0x0467, B:258:0x046b, B:260:0x0475, B:261:0x048e, B:263:0x0499, B:265:0x049d, B:267:0x04a4, B:268:0x04bd, B:270:0x04c9, B:272:0x04cd, B:274:0x04f6, B:275:0x0512, B:277:0x051e, B:279:0x0522, B:281:0x0529, B:282:0x0549, B:284:0x0554, B:286:0x0558, B:288:0x0566, B:290:0x056e, B:294:0x057a, B:292:0x059e, B:296:0x05a3, B:298:0x05af, B:300:0x05b3, B:302:0x05b7, B:303:0x05c8, B:305:0x05d3, B:306:0x05da, B:311:0x0761, B:312:0x077d, B:314:0x0781, B:315:0x0784, B:317:0x0788, B:318:0x078b, B:320:0x078f, B:321:0x079a, B:323:0x07a1, B:325:0x07a5, B:326:0x07a8, B:328:0x07ac, B:329:0x07af, B:331:0x07b3, B:332:0x07b6, B:334:0x07ba, B:335:0x07bd, B:336:0x076e), top: B:6:0x0024 }] */
    /* JADX WARN: Removed duplicated region for block: B:202:0x036b  */
    /* JADX WARN: Removed duplicated region for block: B:206:0x0342 A[Catch: all -> 0x008e, TryCatch #0 {all -> 0x008e, blocks: (B:7:0x0024, B:9:0x0034, B:11:0x0037, B:14:0x0047, B:17:0x0066, B:19:0x006c, B:32:0x00ae, B:36:0x00cb, B:49:0x0108, B:53:0x0123, B:55:0x0127, B:60:0x00e1, B:63:0x00ec, B:66:0x00f6, B:71:0x0086, B:74:0x0091, B:77:0x009c, B:82:0x0158, B:85:0x016e, B:87:0x0174, B:88:0x017e, B:98:0x01b4, B:103:0x01d3, B:105:0x01d6, B:114:0x0202, B:116:0x021c, B:121:0x01e7, B:124:0x01f1, B:128:0x021f, B:130:0x0223, B:134:0x0182, B:137:0x018d, B:140:0x0197, B:143:0x019f, B:146:0x023f, B:148:0x024d, B:150:0x0250, B:152:0x025c, B:153:0x0277, B:162:0x029d, B:167:0x02b8, B:169:0x02c0, B:170:0x02d1, B:179:0x02f7, B:183:0x0312, B:185:0x031a, B:186:0x032b, B:195:0x0351, B:199:0x036c, B:201:0x0370, B:203:0x033a, B:206:0x0342, B:209:0x0328, B:211:0x02e0, B:214:0x02e8, B:217:0x02ce, B:219:0x0286, B:222:0x028e, B:225:0x0270, B:226:0x0395, B:228:0x03a3, B:230:0x03a7, B:232:0x03ae, B:233:0x03c7, B:235:0x03d3, B:237:0x03d7, B:239:0x03de, B:240:0x03f7, B:242:0x0403, B:244:0x0407, B:246:0x040b, B:247:0x0424, B:249:0x0430, B:251:0x0434, B:253:0x0442, B:254:0x045b, B:256:0x0467, B:258:0x046b, B:260:0x0475, B:261:0x048e, B:263:0x0499, B:265:0x049d, B:267:0x04a4, B:268:0x04bd, B:270:0x04c9, B:272:0x04cd, B:274:0x04f6, B:275:0x0512, B:277:0x051e, B:279:0x0522, B:281:0x0529, B:282:0x0549, B:284:0x0554, B:286:0x0558, B:288:0x0566, B:290:0x056e, B:294:0x057a, B:292:0x059e, B:296:0x05a3, B:298:0x05af, B:300:0x05b3, B:302:0x05b7, B:303:0x05c8, B:305:0x05d3, B:306:0x05da, B:311:0x0761, B:312:0x077d, B:314:0x0781, B:315:0x0784, B:317:0x0788, B:318:0x078b, B:320:0x078f, B:321:0x079a, B:323:0x07a1, B:325:0x07a5, B:326:0x07a8, B:328:0x07ac, B:329:0x07af, B:331:0x07b3, B:332:0x07b6, B:334:0x07ba, B:335:0x07bd, B:336:0x076e), top: B:6:0x0024 }] */
    /* JADX WARN: Removed duplicated region for block: B:209:0x0328 A[Catch: all -> 0x008e, TryCatch #0 {all -> 0x008e, blocks: (B:7:0x0024, B:9:0x0034, B:11:0x0037, B:14:0x0047, B:17:0x0066, B:19:0x006c, B:32:0x00ae, B:36:0x00cb, B:49:0x0108, B:53:0x0123, B:55:0x0127, B:60:0x00e1, B:63:0x00ec, B:66:0x00f6, B:71:0x0086, B:74:0x0091, B:77:0x009c, B:82:0x0158, B:85:0x016e, B:87:0x0174, B:88:0x017e, B:98:0x01b4, B:103:0x01d3, B:105:0x01d6, B:114:0x0202, B:116:0x021c, B:121:0x01e7, B:124:0x01f1, B:128:0x021f, B:130:0x0223, B:134:0x0182, B:137:0x018d, B:140:0x0197, B:143:0x019f, B:146:0x023f, B:148:0x024d, B:150:0x0250, B:152:0x025c, B:153:0x0277, B:162:0x029d, B:167:0x02b8, B:169:0x02c0, B:170:0x02d1, B:179:0x02f7, B:183:0x0312, B:185:0x031a, B:186:0x032b, B:195:0x0351, B:199:0x036c, B:201:0x0370, B:203:0x033a, B:206:0x0342, B:209:0x0328, B:211:0x02e0, B:214:0x02e8, B:217:0x02ce, B:219:0x0286, B:222:0x028e, B:225:0x0270, B:226:0x0395, B:228:0x03a3, B:230:0x03a7, B:232:0x03ae, B:233:0x03c7, B:235:0x03d3, B:237:0x03d7, B:239:0x03de, B:240:0x03f7, B:242:0x0403, B:244:0x0407, B:246:0x040b, B:247:0x0424, B:249:0x0430, B:251:0x0434, B:253:0x0442, B:254:0x045b, B:256:0x0467, B:258:0x046b, B:260:0x0475, B:261:0x048e, B:263:0x0499, B:265:0x049d, B:267:0x04a4, B:268:0x04bd, B:270:0x04c9, B:272:0x04cd, B:274:0x04f6, B:275:0x0512, B:277:0x051e, B:279:0x0522, B:281:0x0529, B:282:0x0549, B:284:0x0554, B:286:0x0558, B:288:0x0566, B:290:0x056e, B:294:0x057a, B:292:0x059e, B:296:0x05a3, B:298:0x05af, B:300:0x05b3, B:302:0x05b7, B:303:0x05c8, B:305:0x05d3, B:306:0x05da, B:311:0x0761, B:312:0x077d, B:314:0x0781, B:315:0x0784, B:317:0x0788, B:318:0x078b, B:320:0x078f, B:321:0x079a, B:323:0x07a1, B:325:0x07a5, B:326:0x07a8, B:328:0x07ac, B:329:0x07af, B:331:0x07b3, B:332:0x07b6, B:334:0x07ba, B:335:0x07bd, B:336:0x076e), top: B:6:0x0024 }] */
    /* JADX WARN: Removed duplicated region for block: B:210:0x0310  */
    /* JADX WARN: Removed duplicated region for block: B:214:0x02e8 A[Catch: all -> 0x008e, TryCatch #0 {all -> 0x008e, blocks: (B:7:0x0024, B:9:0x0034, B:11:0x0037, B:14:0x0047, B:17:0x0066, B:19:0x006c, B:32:0x00ae, B:36:0x00cb, B:49:0x0108, B:53:0x0123, B:55:0x0127, B:60:0x00e1, B:63:0x00ec, B:66:0x00f6, B:71:0x0086, B:74:0x0091, B:77:0x009c, B:82:0x0158, B:85:0x016e, B:87:0x0174, B:88:0x017e, B:98:0x01b4, B:103:0x01d3, B:105:0x01d6, B:114:0x0202, B:116:0x021c, B:121:0x01e7, B:124:0x01f1, B:128:0x021f, B:130:0x0223, B:134:0x0182, B:137:0x018d, B:140:0x0197, B:143:0x019f, B:146:0x023f, B:148:0x024d, B:150:0x0250, B:152:0x025c, B:153:0x0277, B:162:0x029d, B:167:0x02b8, B:169:0x02c0, B:170:0x02d1, B:179:0x02f7, B:183:0x0312, B:185:0x031a, B:186:0x032b, B:195:0x0351, B:199:0x036c, B:201:0x0370, B:203:0x033a, B:206:0x0342, B:209:0x0328, B:211:0x02e0, B:214:0x02e8, B:217:0x02ce, B:219:0x0286, B:222:0x028e, B:225:0x0270, B:226:0x0395, B:228:0x03a3, B:230:0x03a7, B:232:0x03ae, B:233:0x03c7, B:235:0x03d3, B:237:0x03d7, B:239:0x03de, B:240:0x03f7, B:242:0x0403, B:244:0x0407, B:246:0x040b, B:247:0x0424, B:249:0x0430, B:251:0x0434, B:253:0x0442, B:254:0x045b, B:256:0x0467, B:258:0x046b, B:260:0x0475, B:261:0x048e, B:263:0x0499, B:265:0x049d, B:267:0x04a4, B:268:0x04bd, B:270:0x04c9, B:272:0x04cd, B:274:0x04f6, B:275:0x0512, B:277:0x051e, B:279:0x0522, B:281:0x0529, B:282:0x0549, B:284:0x0554, B:286:0x0558, B:288:0x0566, B:290:0x056e, B:294:0x057a, B:292:0x059e, B:296:0x05a3, B:298:0x05af, B:300:0x05b3, B:302:0x05b7, B:303:0x05c8, B:305:0x05d3, B:306:0x05da, B:311:0x0761, B:312:0x077d, B:314:0x0781, B:315:0x0784, B:317:0x0788, B:318:0x078b, B:320:0x078f, B:321:0x079a, B:323:0x07a1, B:325:0x07a5, B:326:0x07a8, B:328:0x07ac, B:329:0x07af, B:331:0x07b3, B:332:0x07b6, B:334:0x07ba, B:335:0x07bd, B:336:0x076e), top: B:6:0x0024 }] */
    /* JADX WARN: Removed duplicated region for block: B:217:0x02ce A[Catch: all -> 0x008e, TryCatch #0 {all -> 0x008e, blocks: (B:7:0x0024, B:9:0x0034, B:11:0x0037, B:14:0x0047, B:17:0x0066, B:19:0x006c, B:32:0x00ae, B:36:0x00cb, B:49:0x0108, B:53:0x0123, B:55:0x0127, B:60:0x00e1, B:63:0x00ec, B:66:0x00f6, B:71:0x0086, B:74:0x0091, B:77:0x009c, B:82:0x0158, B:85:0x016e, B:87:0x0174, B:88:0x017e, B:98:0x01b4, B:103:0x01d3, B:105:0x01d6, B:114:0x0202, B:116:0x021c, B:121:0x01e7, B:124:0x01f1, B:128:0x021f, B:130:0x0223, B:134:0x0182, B:137:0x018d, B:140:0x0197, B:143:0x019f, B:146:0x023f, B:148:0x024d, B:150:0x0250, B:152:0x025c, B:153:0x0277, B:162:0x029d, B:167:0x02b8, B:169:0x02c0, B:170:0x02d1, B:179:0x02f7, B:183:0x0312, B:185:0x031a, B:186:0x032b, B:195:0x0351, B:199:0x036c, B:201:0x0370, B:203:0x033a, B:206:0x0342, B:209:0x0328, B:211:0x02e0, B:214:0x02e8, B:217:0x02ce, B:219:0x0286, B:222:0x028e, B:225:0x0270, B:226:0x0395, B:228:0x03a3, B:230:0x03a7, B:232:0x03ae, B:233:0x03c7, B:235:0x03d3, B:237:0x03d7, B:239:0x03de, B:240:0x03f7, B:242:0x0403, B:244:0x0407, B:246:0x040b, B:247:0x0424, B:249:0x0430, B:251:0x0434, B:253:0x0442, B:254:0x045b, B:256:0x0467, B:258:0x046b, B:260:0x0475, B:261:0x048e, B:263:0x0499, B:265:0x049d, B:267:0x04a4, B:268:0x04bd, B:270:0x04c9, B:272:0x04cd, B:274:0x04f6, B:275:0x0512, B:277:0x051e, B:279:0x0522, B:281:0x0529, B:282:0x0549, B:284:0x0554, B:286:0x0558, B:288:0x0566, B:290:0x056e, B:294:0x057a, B:292:0x059e, B:296:0x05a3, B:298:0x05af, B:300:0x05b3, B:302:0x05b7, B:303:0x05c8, B:305:0x05d3, B:306:0x05da, B:311:0x0761, B:312:0x077d, B:314:0x0781, B:315:0x0784, B:317:0x0788, B:318:0x078b, B:320:0x078f, B:321:0x079a, B:323:0x07a1, B:325:0x07a5, B:326:0x07a8, B:328:0x07ac, B:329:0x07af, B:331:0x07b3, B:332:0x07b6, B:334:0x07ba, B:335:0x07bd, B:336:0x076e), top: B:6:0x0024 }] */
    /* JADX WARN: Removed duplicated region for block: B:218:0x02b6  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00a8  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00d6  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0102  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0127 A[Catch: all -> 0x008e, TryCatch #0 {all -> 0x008e, blocks: (B:7:0x0024, B:9:0x0034, B:11:0x0037, B:14:0x0047, B:17:0x0066, B:19:0x006c, B:32:0x00ae, B:36:0x00cb, B:49:0x0108, B:53:0x0123, B:55:0x0127, B:60:0x00e1, B:63:0x00ec, B:66:0x00f6, B:71:0x0086, B:74:0x0091, B:77:0x009c, B:82:0x0158, B:85:0x016e, B:87:0x0174, B:88:0x017e, B:98:0x01b4, B:103:0x01d3, B:105:0x01d6, B:114:0x0202, B:116:0x021c, B:121:0x01e7, B:124:0x01f1, B:128:0x021f, B:130:0x0223, B:134:0x0182, B:137:0x018d, B:140:0x0197, B:143:0x019f, B:146:0x023f, B:148:0x024d, B:150:0x0250, B:152:0x025c, B:153:0x0277, B:162:0x029d, B:167:0x02b8, B:169:0x02c0, B:170:0x02d1, B:179:0x02f7, B:183:0x0312, B:185:0x031a, B:186:0x032b, B:195:0x0351, B:199:0x036c, B:201:0x0370, B:203:0x033a, B:206:0x0342, B:209:0x0328, B:211:0x02e0, B:214:0x02e8, B:217:0x02ce, B:219:0x0286, B:222:0x028e, B:225:0x0270, B:226:0x0395, B:228:0x03a3, B:230:0x03a7, B:232:0x03ae, B:233:0x03c7, B:235:0x03d3, B:237:0x03d7, B:239:0x03de, B:240:0x03f7, B:242:0x0403, B:244:0x0407, B:246:0x040b, B:247:0x0424, B:249:0x0430, B:251:0x0434, B:253:0x0442, B:254:0x045b, B:256:0x0467, B:258:0x046b, B:260:0x0475, B:261:0x048e, B:263:0x0499, B:265:0x049d, B:267:0x04a4, B:268:0x04bd, B:270:0x04c9, B:272:0x04cd, B:274:0x04f6, B:275:0x0512, B:277:0x051e, B:279:0x0522, B:281:0x0529, B:282:0x0549, B:284:0x0554, B:286:0x0558, B:288:0x0566, B:290:0x056e, B:294:0x057a, B:292:0x059e, B:296:0x05a3, B:298:0x05af, B:300:0x05b3, B:302:0x05b7, B:303:0x05c8, B:305:0x05d3, B:306:0x05da, B:311:0x0761, B:312:0x077d, B:314:0x0781, B:315:0x0784, B:317:0x0788, B:318:0x078b, B:320:0x078f, B:321:0x079a, B:323:0x07a1, B:325:0x07a5, B:326:0x07a8, B:328:0x07ac, B:329:0x07af, B:331:0x07b3, B:332:0x07b6, B:334:0x07ba, B:335:0x07bd, B:336:0x076e), top: B:6:0x0024 }] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0122  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x00f6 A[Catch: all -> 0x008e, TryCatch #0 {all -> 0x008e, blocks: (B:7:0x0024, B:9:0x0034, B:11:0x0037, B:14:0x0047, B:17:0x0066, B:19:0x006c, B:32:0x00ae, B:36:0x00cb, B:49:0x0108, B:53:0x0123, B:55:0x0127, B:60:0x00e1, B:63:0x00ec, B:66:0x00f6, B:71:0x0086, B:74:0x0091, B:77:0x009c, B:82:0x0158, B:85:0x016e, B:87:0x0174, B:88:0x017e, B:98:0x01b4, B:103:0x01d3, B:105:0x01d6, B:114:0x0202, B:116:0x021c, B:121:0x01e7, B:124:0x01f1, B:128:0x021f, B:130:0x0223, B:134:0x0182, B:137:0x018d, B:140:0x0197, B:143:0x019f, B:146:0x023f, B:148:0x024d, B:150:0x0250, B:152:0x025c, B:153:0x0277, B:162:0x029d, B:167:0x02b8, B:169:0x02c0, B:170:0x02d1, B:179:0x02f7, B:183:0x0312, B:185:0x031a, B:186:0x032b, B:195:0x0351, B:199:0x036c, B:201:0x0370, B:203:0x033a, B:206:0x0342, B:209:0x0328, B:211:0x02e0, B:214:0x02e8, B:217:0x02ce, B:219:0x0286, B:222:0x028e, B:225:0x0270, B:226:0x0395, B:228:0x03a3, B:230:0x03a7, B:232:0x03ae, B:233:0x03c7, B:235:0x03d3, B:237:0x03d7, B:239:0x03de, B:240:0x03f7, B:242:0x0403, B:244:0x0407, B:246:0x040b, B:247:0x0424, B:249:0x0430, B:251:0x0434, B:253:0x0442, B:254:0x045b, B:256:0x0467, B:258:0x046b, B:260:0x0475, B:261:0x048e, B:263:0x0499, B:265:0x049d, B:267:0x04a4, B:268:0x04bd, B:270:0x04c9, B:272:0x04cd, B:274:0x04f6, B:275:0x0512, B:277:0x051e, B:279:0x0522, B:281:0x0529, B:282:0x0549, B:284:0x0554, B:286:0x0558, B:288:0x0566, B:290:0x056e, B:294:0x057a, B:292:0x059e, B:296:0x05a3, B:298:0x05af, B:300:0x05b3, B:302:0x05b7, B:303:0x05c8, B:305:0x05d3, B:306:0x05da, B:311:0x0761, B:312:0x077d, B:314:0x0781, B:315:0x0784, B:317:0x0788, B:318:0x078b, B:320:0x078f, B:321:0x079a, B:323:0x07a1, B:325:0x07a5, B:326:0x07a8, B:328:0x07ac, B:329:0x07af, B:331:0x07b3, B:332:0x07b6, B:334:0x07ba, B:335:0x07bd, B:336:0x076e), top: B:6:0x0024 }] */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00c9  */
    @dalvik.annotation.optimization.NeverCompile
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void dump(java.io.FileDescriptor r31, java.io.PrintWriter r32, java.lang.String[] r33) {
        /*
            Method dump skipped, instructions count: 2020
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.usb.UsbService.dump(java.io.FileDescriptor, java.io.PrintWriter, java.lang.String[]):void");
    }

    public final void enableContaminantDetection(String str, boolean z) {
        Objects.requireNonNull(str, "portId must not be null");
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_USB", null);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            UsbPortManager usbPortManager = this.mPortManager;
            if (usbPortManager != null) {
                usbPortManager.enableContaminantDetection(str, z, null);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void enableLimitPowerTransfer(String str, boolean z, int i, IUsbOperationInternal iUsbOperationInternal) {
        Objects.requireNonNull(str, "portId must not be null. opID:" + i);
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_USB", null);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            UsbPortManager usbPortManager = this.mPortManager;
            if (usbPortManager != null) {
                usbPortManager.enableLimitPowerTransfer(str, z, i, iUsbOperationInternal, null);
            } else {
                try {
                    iUsbOperationInternal.onOperationComplete(1);
                } catch (RemoteException e) {
                    Slog.e("UsbService", "enableLimitPowerTransfer: Failed to call onOperationComplete", e);
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean enableUsbData(String str, boolean z, int i, IUsbOperationInternal iUsbOperationInternal) {
        return enableUsbDataInternal(str, z, i, iUsbOperationInternal, Binder.getCallingUid());
    }

    public boolean enableUsbDataInternal(String str, boolean z, int i, IUsbOperationInternal iUsbOperationInternal, int i2) {
        Objects.requireNonNull(str, "enableUsbData: portId must not be null. opId:" + i);
        Objects.requireNonNull(iUsbOperationInternal, "enableUsbData: callback must not be null. opId:" + i);
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_USB", null);
        boolean z2 = false;
        if (Flags.enableUsbDataSignalStaking() && !shouldUpdateUsbSignaling(i2, str, z)) {
            try {
                iUsbOperationInternal.onOperationComplete(1);
            } catch (RemoteException e) {
                Slog.e("UsbService", "enableUsbData: Failed to call onOperationComplete", e);
            }
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            UsbPortManager usbPortManager = this.mPortManager;
            if (usbPortManager != null) {
                z2 = usbPortManager.enableUsbData(str, z, i, iUsbOperationInternal);
            } else {
                try {
                    iUsbOperationInternal.onOperationComplete(1);
                } catch (RemoteException e2) {
                    Slog.e("UsbService", "enableUsbData: Failed to call onOperationComplete", e2);
                }
            }
            return z2;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void enableUsbDataWhileDocked(String str, int i, IUsbOperationInternal iUsbOperationInternal) {
        enableUsbDataWhileDockedInternal(str, i, iUsbOperationInternal, Binder.getCallingUid());
    }

    public void enableUsbDataWhileDockedInternal(String str, int i, IUsbOperationInternal iUsbOperationInternal, int i2) {
        Objects.requireNonNull(str, "enableUsbDataWhileDocked: portId must not be null. opId:" + i);
        Objects.requireNonNull(iUsbOperationInternal, "enableUsbDataWhileDocked: callback must not be null. opId:" + i);
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_USB", null);
        if (Flags.enableUsbDataSignalStaking() && !shouldUpdateUsbSignaling(i2, str, true)) {
            try {
                iUsbOperationInternal.onOperationComplete(1);
                return;
            } catch (RemoteException e) {
                Slog.e("UsbService", "enableUsbDataWhileDocked: Failed to call onOperationComplete", e);
                return;
            }
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            UsbPortManager usbPortManager = this.mPortManager;
            if (usbPortManager != null) {
                usbPortManager.enableUsbDataWhileDocked(str, i, iUsbOperationInternal);
            } else {
                try {
                    iUsbOperationInternal.onOperationComplete(1);
                } catch (RemoteException e2) {
                    Slog.e("UsbService", "enableUsbData: Failed to call onOperationComplete", e2);
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final ParcelFileDescriptor getControlFd(long j) {
        getControlFd_enforcePermission();
        FileDescriptor fileDescriptor = (FileDescriptor) this.mDeviceManager.mControlFds.get(Long.valueOf(j));
        if (fileDescriptor == null) {
            return null;
        }
        try {
            return ParcelFileDescriptor.dup(fileDescriptor);
        } catch (IOException unused) {
            Slog.e("UsbDeviceManager", "Could not dup fd for " + j);
            return null;
        }
    }

    public final UsbAccessory getCurrentAccessory() {
        UsbDeviceManager usbDeviceManager;
        if (PersonaServiceHelper.shouldBlockUsbHostMode(this.mContext) || (usbDeviceManager = this.mDeviceManager) == null) {
            return null;
        }
        return usbDeviceManager.mHandler.mCurrentAccessory;
    }

    public final long getCurrentFunctions() {
        if (PersonaServiceHelper.shouldBlockUsbHostMode(this.mContext)) {
            return 0L;
        }
        getCurrentFunctions_enforcePermission();
        Preconditions.checkState(this.mDeviceManager != null);
        return this.mDeviceManager.mHandler.mCurrentFunctions;
    }

    public final int getCurrentUsbSpeed() {
        getCurrentUsbSpeed_enforcePermission();
        Preconditions.checkNotNull(this.mDeviceManager, "DeviceManager must not be null");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mDeviceManager.mHandler.mUsbSpeed;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void getDeviceList(Bundle bundle) {
        UsbHostManager usbHostManager;
        if (PersonaServiceHelper.shouldBlockUsbHostMode(this.mContext) || (usbHostManager = this.mHostManager) == null) {
            return;
        }
        synchronized (usbHostManager.mLock) {
            try {
                for (String str : usbHostManager.mDevices.keySet()) {
                    bundle.putParcelable(str, (Parcelable) usbHostManager.mDevices.get(str));
                }
            } finally {
            }
        }
    }

    public final int getGadgetHalVersion() {
        getGadgetHalVersion_enforcePermission();
        Preconditions.checkNotNull(this.mDeviceManager, "DeviceManager must not be null");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mDeviceManager.mHandler.mCurrentGadgetHalVersion;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final UsbPortStatus getPortStatus(String str) {
        Objects.requireNonNull(str, "portId must not be null");
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_USB", null);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            UsbPortManager usbPortManager = this.mPortManager;
            return usbPortManager != null ? usbPortManager.getPortStatus(str) : null;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final List getPorts() {
        getPorts_enforcePermission();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            UsbPortManager usbPortManager = this.mPortManager;
            if (usbPortManager == null) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return null;
            }
            UsbPort[] ports = usbPortManager.getPorts();
            ArrayList arrayList = new ArrayList();
            for (UsbPort usbPort : ports) {
                arrayList.add(ParcelableUsbPort.of(usbPort));
            }
            return arrayList;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final long getScreenUnlockedFunctions() {
        getScreenUnlockedFunctions_enforcePermission();
        Preconditions.checkState(this.mDeviceManager != null);
        UsbDeviceManager.UsbHandler usbHandler = this.mDeviceManager.mHandler;
        usbHandler.getClass();
        if (UsbManager.usbFunctionsToString(UsbDeviceManager.m1015$$Nest$smgetDefaultUsbValue()).equals("adb")) {
            return 0L;
        }
        return usbHandler.mScreenUnlockedFunctions;
    }

    public final int getUsbHalVersion() {
        getUsbHalVersion_enforcePermission();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            UsbPortManager usbPortManager = this.mPortManager;
            if (usbPortManager == null) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -1;
            }
            UsbPortHal usbPortHal = usbPortManager.mUsbPortHal;
            int i = -2;
            if (usbPortHal != null) {
                try {
                    i = usbPortHal.getUsbHalVersion();
                } catch (RemoteException unused) {
                }
            }
            return i;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void grantAccessoryPermission(UsbAccessory usbAccessory, int i) {
        grantAccessoryPermission_enforcePermission();
        int userId = UserHandle.getUserId(i);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mPermissionManager.getPermissionsForUser(userId).grantAccessoryPermission(usbAccessory, i);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void grantDevicePermission(UsbDevice usbDevice, int i) {
        grantDevicePermission_enforcePermission();
        int userId = UserHandle.getUserId(i);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mPermissionManager.getPermissionsForUser(userId).grantDevicePermission(usbDevice, i);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean hasAccessoryPermission(UsbAccessory usbAccessory) {
        int callingUid = Binder.getCallingUid();
        int callingPid = Binder.getCallingPid();
        int userId = UserHandle.getUserId(callingUid);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mPermissionManager.getPermissionsForUser(userId).hasPermission(usbAccessory, callingPid, callingUid);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean hasAccessoryPermissionWithIdentity(UsbAccessory usbAccessory, int i, int i2) {
        hasAccessoryPermissionWithIdentity_enforcePermission();
        return this.mPermissionManager.getPermissionsForUser(UserHandle.getUserId(i2)).hasPermission(usbAccessory, i, i2);
    }

    public final boolean hasDefaults(String str, int i) {
        String str2 = (String) Preconditions.checkStringNotEmpty(str);
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_USB", null);
        UserHandle of = UserHandle.of(i);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mSettingsManager.getSettingsForProfileGroup(of).hasDefaults(str2, of);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean hasDevicePermission(UsbDevice usbDevice, String str) {
        int callingUid = Binder.getCallingUid();
        int callingPid = Binder.getCallingPid();
        int userId = UserHandle.getUserId(callingUid);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mPermissionManager.getPermissionsForUser(userId).hasPermission(usbDevice, str, callingPid, callingUid);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean hasDevicePermissionWithIdentity(UsbDevice usbDevice, String str, int i, int i2) {
        hasDevicePermissionWithIdentity_enforcePermission();
        return this.mPermissionManager.getPermissionsForUser(UserHandle.getUserId(i2)).hasPermission(usbDevice, str, i, i2);
    }

    public final boolean isFunctionEnabled(String str) {
        return (UsbManager.usbFunctionsFromString(str) & getCurrentFunctions()) != 0;
    }

    public final boolean isModeChangeSupported(String str) {
        isModeChangeSupported_enforcePermission();
        Objects.requireNonNull(str, "portId must not be null");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            UsbPortManager usbPortManager = this.mPortManager;
            if (usbPortManager != null) {
                synchronized (usbPortManager.mLock) {
                    try {
                        UsbPortManager.PortInfo portInfo = (UsbPortManager.PortInfo) usbPortManager.mPorts.get(str);
                        r2 = portInfo != null ? portInfo.mCanChangeMode : false;
                    } finally {
                    }
                }
            }
            return r2;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isSupportDexRestrict() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_USB", null);
        if (this.mHostRestrictor != null) {
            return UsbHostRestrictor.isSupportDexRestrict();
        }
        return false;
    }

    public final boolean isUsbBlocked() {
        if (PersonaServiceHelper.shouldBlockUsbHostMode(this.mContext)) {
            return true;
        }
        if (this.mHostRestrictor == null) {
            return false;
        }
        if (UsbHostRestrictor.mCurrentSysNodeValue.contains("ON")) {
            return true;
        }
        if (!UsbHostRestrictor.mCurrentSysNodeValue.equals("OFF")) {
            Slog.d("UsbHostRestrictor", "Current USB BLOCK STATE is UNKNOWN!! So USB is UNBLOCKED as a default value");
        }
        return false;
    }

    public final void onSwitchUser(int i) {
        synchronized (this.mLock) {
            this.mCurrentUserId = i;
            UsbProfileGroupSettingsManager settingsForProfileGroup = this.mSettingsManager.getSettingsForProfileGroup(UserHandle.of(i));
            UsbHostManager usbHostManager = this.mHostManager;
            if (usbHostManager != null) {
                synchronized (usbHostManager.mSettingsLock) {
                    usbHostManager.mCurrentSettings = settingsForProfileGroup;
                }
            }
            UsbDeviceManager usbDeviceManager = this.mDeviceManager;
            if (usbDeviceManager != null) {
                synchronized (usbDeviceManager.mLock) {
                    usbDeviceManager.mCurrentSettings = settingsForProfileGroup;
                    usbDeviceManager.mHandler.obtainMessage(5, i, 0).sendToTarget();
                }
            }
        }
    }

    public final ParcelFileDescriptor openAccessory(UsbAccessory usbAccessory) {
        if (!PersonaServiceHelper.shouldBlockUsbHostMode(this.mContext) && this.mDeviceManager != null) {
            int callingUid = Binder.getCallingUid();
            int callingPid = Binder.getCallingPid();
            int userId = UserHandle.getUserId(callingUid);
            long clearCallingIdentity = IUsbManager.Stub.clearCallingIdentity();
            try {
                synchronized (this.mLock) {
                    if (this.mUserManager.isSameProfileGroup(userId, this.mCurrentUserId)) {
                        return this.mDeviceManager.openAccessory(usbAccessory, this.mPermissionManager.getPermissionsForUser(userId), callingPid, callingUid);
                    }
                    Slog.w("UsbService", "Cannot open " + usbAccessory + " for user " + userId + " as user is not active.");
                }
            } finally {
                IUsbManager.Stub.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return null;
    }

    public final ParcelFileDescriptor openDevice(String str, String str2) {
        ParcelFileDescriptor parcelFileDescriptor = null;
        if (PersonaServiceHelper.shouldBlockUsbHostMode(this.mContext)) {
            return null;
        }
        if (this.mHostManager != null && str != null) {
            int callingUid = Binder.getCallingUid();
            int callingPid = Binder.getCallingPid();
            int userId = UserHandle.getUserId(callingUid);
            long clearCallingIdentity = IUsbManager.Stub.clearCallingIdentity();
            try {
                synchronized (this.mLock) {
                    try {
                        if (this.mUserManager.isSameProfileGroup(userId, this.mCurrentUserId)) {
                            parcelFileDescriptor = this.mHostManager.openDevice(str, this.mPermissionManager.getPermissionsForUser(userId), str2, callingPid, callingUid);
                        } else {
                            Slog.w("UsbService", "Cannot open " + str + " for user " + userId + " as user is not active.");
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            } finally {
                IUsbManager.Stub.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return parcelFileDescriptor;
    }

    public final boolean registerForDisplayPortEvents(IDisplayPortAltModeInfoListener iDisplayPortAltModeInfoListener) {
        Objects.requireNonNull(iDisplayPortAltModeInfoListener, "registerForDisplayPortEvents: listener must not be null.");
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_USB", null);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            UsbPortManager usbPortManager = this.mPortManager;
            if (usbPortManager != null) {
                return usbPortManager.registerForDisplayPortEvents(iDisplayPortAltModeInfoListener);
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void removeAccessoryPackagesFromPreferenceDenied(UsbAccessory usbAccessory, String[] strArr, UserHandle userHandle) {
        Objects.requireNonNull(usbAccessory);
        String[] strArr2 = (String[]) Preconditions.checkArrayElementsNotNull(strArr, "packageNames");
        Objects.requireNonNull(userHandle);
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_USB", null);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mSettingsManager.getSettingsForProfileGroup(userHandle).removeAccessoryPackagesFromDenied(usbAccessory, strArr2, userHandle);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void removeDevicePackagesFromPreferenceDenied(UsbDevice usbDevice, String[] strArr, UserHandle userHandle) {
        Objects.requireNonNull(usbDevice);
        String[] strArr2 = (String[]) Preconditions.checkArrayElementsNotNull(strArr, "packageNames");
        Objects.requireNonNull(userHandle);
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_USB", null);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mSettingsManager.getSettingsForProfileGroup(userHandle).removeDevicePackagesFromDenied(usbDevice, strArr2, userHandle);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void requestAccessoryPermission(UsbAccessory usbAccessory, String str, PendingIntent pendingIntent) {
        int callingUid = Binder.getCallingUid();
        int callingPid = Binder.getCallingPid();
        int userId = UserHandle.getUserId(callingUid);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mPermissionManager.getPermissionsForUser(userId).requestPermission(usbAccessory, str, pendingIntent, callingPid, callingUid);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void requestDevicePermission(UsbDevice usbDevice, String str, PendingIntent pendingIntent) {
        int callingUid = Binder.getCallingUid();
        int callingPid = Binder.getCallingPid();
        int userId = UserHandle.getUserId(callingUid);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mPermissionManager.getPermissionsForUser(userId).requestPermission(usbDevice, str, pendingIntent, callingPid, callingUid);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void resetUsbGadget() {
        resetUsbGadget_enforcePermission();
        Preconditions.checkNotNull(this.mDeviceManager, "DeviceManager must not be null");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            UsbDeviceManager usbDeviceManager = this.mDeviceManager;
            usbDeviceManager.getClass();
            Slog.d("UsbDeviceManager", "reset Usb Gadget");
            usbDeviceManager.mHandler.sendMessage(19, null);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void resetUsbPort(String str, int i, IUsbOperationInternal iUsbOperationInternal) {
        Objects.requireNonNull(str, "resetUsbPort: portId must not be null. opId:" + i);
        Objects.requireNonNull(iUsbOperationInternal, "resetUsbPort: callback must not be null. opId:" + i);
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_USB", null);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            UsbPortManager usbPortManager = this.mPortManager;
            if (usbPortManager != null) {
                usbPortManager.resetUsbPort(str, i, iUsbOperationInternal);
            } else {
                try {
                    iUsbOperationInternal.onOperationComplete(1);
                } catch (RemoteException e) {
                    Slog.e("UsbService", "resetUsbPort: Failed to call onOperationComplete", e);
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final int restrictUsbHostInterface(boolean z, String str) {
        if (Binder.getCallingUid() != 1000) {
            throw new SecurityException("no permission to call restrictUsbHostInterface()");
        }
        if (this.mHostRestrictor == null) {
            return -1;
        }
        Slog.d("UsbService", "restrictUsbHostInterface: enable=" + z);
        Slog.d("UsbHostRestrictor", "restrictUsbHostInterface(" + z + ", " + str + ")");
        if (!UsbHostRestrictor.isSupportDexRestrict()) {
            Slog.e("UsbHostRestrictor", "restrictUsbHostInterface() node error");
            return -1;
        }
        try {
            UsbHostRestrictor.bRestrictHostAPI = z;
            UsbHostRestrictor.mStrAllowList = str;
            FileUtils.stringToFile("/sys/class/usb_notify/usb_control/whitelist_for_mdm", str);
            return 0;
        } catch (Exception e) {
            Slog.e("UsbHostRestrictor", "restrictUsbHostInterface() fail - " + e);
            return -1;
        }
    }

    public final int semGetDataRoleStatus() {
        Slog.d("UsbService", "semGetDataRoleStatus++");
        UsbPortManager usbPortManager = this.mPortManager;
        int i = -1;
        if (usbPortManager == null) {
            Slog.d("UsbService", "semGetDataRoleStatus--, manager is null");
            return -1;
        }
        usbPortManager.getClass();
        Slog.d("UsbPortManager", "semGetDataRoleStatus++");
        UsbPort[] ports = usbPortManager.getPorts();
        int length = ports.length;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                break;
            }
            UsbPortStatus portStatus = usbPortManager.getPortStatus(ports[i2].getId());
            Slog.d("UsbPortManager", "semGetDataRoleStatus status of ports[" + i2 + "] = " + portStatus);
            if (portStatus != null && portStatus.isConnected()) {
                i = portStatus.getCurrentDataRole();
                Slog.d("UsbPortManager", "semGetDataRoleStatus status.isConnected() = " + portStatus.isConnected());
                break;
            }
            i2++;
        }
        Slog.d("UsbPortManager", "semGetDataRoleStatus-- with ret[" + i + "]");
        StringBuilder sb = new StringBuilder("semGetDataRoleStatus ret=");
        sb.append(i);
        Slog.d("UsbService", sb.toString());
        return i;
    }

    public final int semGetPowerRoleStatus() {
        Slog.d("UsbService", "semGetPowerRoleStatus++");
        UsbPortManager usbPortManager = this.mPortManager;
        int i = -1;
        if (usbPortManager == null) {
            Slog.d("UsbService", "semGetPowerRoleStatus--, manager is null");
            return -1;
        }
        usbPortManager.getClass();
        Slog.d("UsbPortManager", "semGetPowerRoleStatus++");
        UsbPort[] ports = usbPortManager.getPorts();
        int length = ports.length;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                break;
            }
            UsbPortStatus portStatus = usbPortManager.getPortStatus(ports[i2].getId());
            Slog.d("UsbPortManager", "semGetPowerRoleStatus status of ports[" + i2 + "] = " + portStatus);
            if (portStatus != null && portStatus.isConnected()) {
                i = portStatus.getCurrentPowerRole();
                Slog.d("UsbPortManager", "semGetPowerRoleStatus status.isConnected() = " + portStatus.isConnected());
                break;
            }
            i2++;
        }
        Slog.d("UsbPortManager", "semGetPowerRoleStatus-- with ret[" + i + "]");
        StringBuilder sb = new StringBuilder("semGetPowerRoleStatus ret=");
        sb.append(i);
        Slog.d("UsbService", sb.toString());
        return i;
    }

    public final void semGrantDevicePermission(UsbDevice usbDevice, int i) {
        grantDevicePermission(usbDevice, i);
    }

    public final void semSetDevicePackage(UsbDevice usbDevice, String str, int i) {
        setDevicePackage(usbDevice, str, i);
    }

    public final void semSetMode(int i) {
        int usbDataMode;
        SemUsbBackend semUsbBackend;
        int i2;
        int i3;
        UsbPortManager.PortInfo portInfo;
        int i4;
        Slog.d("UsbService", "semSetMode++, not imlpemtentd yet mode " + i);
        if (this.mPortManager == null || this.mDeviceManager == null) {
            Slog.d("UsbService", "semSetMode--, managers are null");
            return;
        }
        int callingUid = Binder.getCallingUid();
        PackageManager packageManager = this.mContext.getPackageManager();
        Slog.d("UsbService", "isAllowedPackage: " + packageManager.getNameForUid(callingUid));
        int i5 = 0;
        boolean z = packageManager.checkSignatures("android", packageManager.getNameForUid(callingUid)) == 0;
        Slog.w("UsbService", " ret " + z);
        if (callingUid == 1000) {
            z = true;
        }
        Slog.d("UsbService", "isAllowedPackage, ret " + z);
        if (!z) {
            Slog.d("UsbService", "semSetMode--, no permissions");
            return;
        }
        Context context = this.mContext;
        UsbPortManager usbPortManager = this.mPortManager;
        UsbDeviceManager usbDeviceManager = this.mDeviceManager;
        SemUsbBackend semUsbBackend2 = new SemUsbBackend();
        semUsbBackend2.mIsUnlocked = context.registerReceiver(null, new IntentFilter("android.hardware.usb.action.USB_STATE")).getBooleanExtra("unlocked", false);
        UserManager.get(context).hasUserRestriction("no_usb_file_transfer");
        semUsbBackend2.mPortManager = usbPortManager;
        semUsbBackend2.mDeviceManager = usbDeviceManager;
        semUsbBackend2.updateUsbPort();
        Slog.d("UsbService", "semSetMode: backend " + semUsbBackend2);
        semUsbBackend2.updateUsbPort();
        if (semUsbBackend2.mPort == null || semUsbBackend2.mPortStatus.getCurrentPowerRole() != 1) {
            Slog.d("SemUsbBackend", "USB connection getCurrentMode : " + semUsbBackend2.getUsbDataMode());
            usbDataMode = semUsbBackend2.getUsbDataMode();
        } else {
            Slog.d("SemUsbBackend", "USB connection getCurrentMode : 1");
            usbDataMode = 1;
        }
        Slog.d("UsbService", "semSetMode: currentUsbMode " + usbDataMode);
        if (usbDeviceManager != null) {
            Set set = UsbDeviceManager.sDenyInterfaces;
            Slog.d("UsbDeviceManager", "rmSetNextUsbModeToDefault");
            UsbDeviceManager.mSetNextUsbModeToDefault = false;
        } else {
            Slog.d("SemUsbBackend", "setMode : mDeviceManager is null");
        }
        if (semUsbBackend2.mPort != null) {
            i5 = (i & 1) == 1 ? 1 : 2;
            i3 = ((i & 14) == 0 && semUsbBackend2.mPortStatus.isRoleCombinationSupported(i5, 1)) ? 1 : 2;
            Slog.d("SemUsbBackend", "setMode : powerRole - " + i5 + " /dataRole - " + i3);
            String id = semUsbBackend2.mPort.getId();
            synchronized (usbPortManager.mLock) {
                try {
                    portInfo = (UsbPortManager.PortInfo) usbPortManager.mPorts.get(id);
                } finally {
                }
                if (portInfo != null) {
                    if (portInfo.mUsbPortStatus.isRoleCombinationSupported(i5, i3)) {
                        int currentDataRole = portInfo.mUsbPortStatus.getCurrentDataRole();
                        int currentPowerRole = portInfo.mUsbPortStatus.getCurrentPowerRole();
                        if (currentDataRole != i3 || currentPowerRole != i5) {
                            boolean z2 = portInfo.mCanChangeMode;
                            semUsbBackend = semUsbBackend2;
                            boolean z3 = portInfo.mCanChangePowerRole;
                            boolean z4 = portInfo.mCanChangeDataRole;
                            int currentMode = portInfo.mUsbPortStatus.getCurrentMode();
                            if ((z3 || currentPowerRole == i5) && (z4 || currentDataRole == i3)) {
                                i4 = currentMode;
                            } else if (z2 && i5 == 1 && i3 == 1) {
                                i4 = 2;
                            } else if (z2 && i5 == 2 && i3 == 2) {
                                i4 = 1;
                            } else {
                                Slog.println(6, "UsbPortManager", "Found mismatch in supported USB role combinations while attempting to change role: " + portInfo + ", newPowerRole=" + UsbPort.powerRoleToString(i5) + ", newDataRole=" + UsbPort.dataRoleToString(i3));
                            }
                            Slog.println(4, "UsbPortManager", "Setting USB port mode and role: portId=" + id + ", currentMode=" + UsbPort.modeToString(currentMode) + ", currentPowerRole=" + UsbPort.powerRoleToString(currentPowerRole) + ", currentDataRole=" + UsbPort.dataRoleToString(currentDataRole) + ", newMode=" + UsbPort.modeToString(i4) + ", newPowerRole=" + UsbPort.powerRoleToString(i5) + ", newDataRole=" + UsbPort.dataRoleToString(i3));
                            RawPortInfo rawPortInfo = (RawPortInfo) usbPortManager.mSimulatedPorts.get(id);
                            if (rawPortInfo != null) {
                                rawPortInfo.currentMode = i4;
                                rawPortInfo.currentPowerRole = i5;
                                rawPortInfo.currentDataRole = i3;
                                usbPortManager.updatePortsLocked(null, null);
                            } else {
                                UsbPortHal usbPortHal = usbPortManager.mUsbPortHal;
                                if (usbPortHal != null) {
                                    if (currentMode != i4) {
                                        Slog.println(6, "UsbPortManager", "Trying to set the USB port mode: portId=" + id + ", newMode=" + UsbPort.modeToString(i4));
                                        try {
                                            UsbPortHal usbPortHal2 = usbPortManager.mUsbPortHal;
                                            int i6 = i4 == 1 ? 1 : 2;
                                            long j = usbPortManager.mTransactionId + 1;
                                            usbPortManager.mTransactionId = j;
                                            usbPortHal2.switchMode(i6, id, j);
                                        } catch (Exception e) {
                                            Slog.e("UsbPortManager", "Failed to set the USB port mode: portId=" + id + ", newMode=" + UsbPort.modeToString(i4), e);
                                        }
                                    } else if (currentDataRole != i3) {
                                        int i7 = i3 == 2 ? 2 : 1;
                                        try {
                                            long j2 = usbPortManager.mTransactionId + 1;
                                            usbPortManager.mTransactionId = j2;
                                            usbPortHal.switchDataRole(i7, id, j2);
                                        } catch (Exception e2) {
                                            Slog.e("UsbPortManager", "Failed to set the USB port data role: portId=" + id + ", newDataRole=" + UsbPort.dataRoleToString(i3), e2);
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        Slog.println(6, "UsbPortManager", "Attempted to set USB port into unsupported role combination: portId=" + id + ", newPowerRole=" + UsbPort.powerRoleToString(i5) + ", newDataRole=" + UsbPort.dataRoleToString(i3));
                    }
                }
                semUsbBackend = semUsbBackend2;
            }
            i2 = 1;
        } else {
            semUsbBackend = semUsbBackend2;
            i2 = 1;
            i3 = 0;
        }
        if (i5 == i2 && i3 == i2) {
            Slog.d("SemUsbBackend", "USB connection setMode : " + i + " - skip!!");
        } else {
            Slog.d("SemUsbBackend", "USB connection setMode : " + i);
            int i8 = i & 14;
            int callingUid2 = Binder.getCallingUid() + SemUsbBackend.sUsbOperationCount.incrementAndGet();
            Slog.d("SemUsbBackend", "USB connection setUsbFunction : " + i8 + ", operationId : " + callingUid2);
            UsbDeviceManager usbDeviceManager2 = semUsbBackend.mDeviceManager;
            if (usbDeviceManager2 == null) {
                Slog.d("SemUsbBackend", "setUsbFunction : mDeviceManager is null");
            } else if (i8 == 2) {
                Slog.d("SemUsbBackend", "USB connection setUsbFunction : + UsbManager.USB_FUNCTION_MTP");
                usbDeviceManager2.setCurrentFunctions(UsbManager.usbFunctionsFromString("mtp"), callingUid2);
            } else if (i8 == 4) {
                Slog.d("SemUsbBackend", "USB connection setUsbFunction :  + UsbManager.USB_FUNCTION_PTP");
                usbDeviceManager2.setCurrentFunctions(UsbManager.usbFunctionsFromString("ptp"), callingUid2);
            } else if (i8 == 6) {
                Slog.d("SemUsbBackend", "USB connection setUsbFunction :  + UsbManager.USB_FUNCTION_MIDI");
                usbDeviceManager2.setCurrentFunctions(UsbManager.usbFunctionsFromString("midi"), callingUid2);
            } else if (i8 == 8) {
                Slog.d("SemUsbBackend", "USB connection setUsbFunction :  + UsbManager.USB_FUNCTION_MASS_STORAGE");
                usbDeviceManager2.setCurrentFunctions(UsbManager.usbFunctionsFromString("mass_storage"), callingUid2);
            } else if (i8 != 10) {
                Slog.d("SemUsbBackend", "USB connection setUsbFunction :  + UsbManager.USB_FUNCTION_SEC_CHARGING");
                usbDeviceManager2.setCurrentFunctions(UsbManager.usbFunctionsFromString("sec_charging"), callingUid2);
            } else {
                Slog.d("SemUsbBackend", "USB connection setUsbFunction :  + UsbManager.USB_FUNCTION_MTP_GADGET");
                usbDeviceManager2.setCurrentFunctions(UsbManager.usbFunctionsFromString("mtp,conn_gadget"), callingUid2);
            }
        }
        Slog.d("UsbService", "semSetMode--");
    }

    public final void setAccessoryPackage(UsbAccessory usbAccessory, String str, int i) {
        Objects.requireNonNull(usbAccessory);
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_USB", null);
        UserHandle of = UserHandle.of(i);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mSettingsManager.getSettingsForProfileGroup(of).setAccessoryPackage(usbAccessory, str, of);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void setAccessoryPersistentPermission(UsbAccessory usbAccessory, int i, UserHandle userHandle, boolean z) {
        Objects.requireNonNull(usbAccessory);
        Objects.requireNonNull(userHandle);
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_USB", null);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            UsbPermissionManager usbPermissionManager = this.mPermissionManager;
            usbPermissionManager.getClass();
            usbPermissionManager.getPermissionsForUser(userHandle.getIdentifier()).setAccessoryPersistentPermission(usbAccessory, i, z);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void setCurrentFunction(String str, boolean z, int i) {
        setCurrentFunctions(UsbManager.usbFunctionsFromString(str), i);
    }

    public final void setCurrentFunctions(long j, int i) {
        if (PersonaServiceHelper.shouldBlockUsbHostMode(this.mContext)) {
            return;
        }
        setCurrentFunctions_enforcePermission();
        Preconditions.checkArgument(UsbManager.areSettableFunctions(j));
        Preconditions.checkState(this.mDeviceManager != null);
        this.mDeviceManager.getClass();
        Slog.d("UsbDeviceManager", "rmSetNextUsbModeToDefault");
        UsbDeviceManager.mSetNextUsbModeToDefault = false;
        this.mDeviceManager.setCurrentFunctions(j, i);
    }

    public final void setDevicePackage(UsbDevice usbDevice, String str, int i) {
        Objects.requireNonNull(usbDevice);
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_USB", null);
        UserHandle of = UserHandle.of(i);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mSettingsManager.getSettingsForProfileGroup(of).setDevicePackage(usbDevice, str, of);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void setDevicePersistentPermission(UsbDevice usbDevice, int i, UserHandle userHandle, boolean z) {
        Objects.requireNonNull(usbDevice);
        Objects.requireNonNull(userHandle);
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_USB", null);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            UsbPermissionManager usbPermissionManager = this.mPermissionManager;
            usbPermissionManager.getClass();
            usbPermissionManager.getPermissionsForUser(userHandle.getIdentifier()).setDevicePersistentPermission(usbDevice, i, z);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void setPortRoles(String str, int i, int i2) {
        Objects.requireNonNull(str, "portId must not be null");
        UsbPort.checkRoles(i, i2);
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_USB", null);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            UsbPortManager usbPortManager = this.mPortManager;
            if (usbPortManager != null) {
                usbPortManager.setPortRoles(str, i, i2, null, new AnonymousClass2());
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void setScreenUnlockedFunctions(long j) {
        setScreenUnlockedFunctions_enforcePermission();
        Preconditions.checkArgument(UsbManager.areSettableFunctions(j));
        Preconditions.checkState(this.mDeviceManager != null);
        UsbDeviceManager usbDeviceManager = this.mDeviceManager;
        usbDeviceManager.getClass();
        Slog.d("UsbDeviceManager", "setScreenUnlockedFunctions(" + UsbManager.usbFunctionsToString(j) + ")");
        if ("adb".equals(SystemProperties.get(UsbDeviceManager.getPersistProp(), "none")) && j == 0) {
            Slog.d("UsbDeviceManager", "setScreenUnlockedFunctions keep the current state");
        } else {
            usbDeviceManager.mHandler.sendMessage(12, Long.valueOf(j));
        }
    }

    public final void setUsbDeviceConnectionHandler(ComponentName componentName) {
        setUsbDeviceConnectionHandler_enforcePermission();
        synchronized (this.mLock) {
            try {
                if (this.mCurrentUserId != UserHandle.getCallingUserId()) {
                    throw new IllegalArgumentException("Only the current user can register a usb connection handler");
                }
                UsbHostManager usbHostManager = this.mHostManager;
                if (usbHostManager != null) {
                    synchronized (usbHostManager.mHandlerLock) {
                        usbHostManager.mUsbDeviceConnectionHandler = componentName;
                    }
                }
            } finally {
            }
        }
    }

    public final void setUsbHiddenMenuState(boolean z) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_USB", null);
        if (this.mDeviceManager == null) {
            Slog.e("UsbService", "UsbService mDeviceManager is NULL");
            return;
        }
        Slog.d("UsbService", "setUsbHiddenMenuState: enable=" + z);
        this.mDeviceManager.getClass();
        UsbDeviceManager.mEnableUsbHiddenMenu = z;
    }

    public final boolean shouldUpdateUsbSignaling(int i, String str, boolean z) {
        synchronized (this.mUsbDisableRequesters) {
            try {
                if (!this.mUsbDisableRequesters.containsKey(str)) {
                    this.mUsbDisableRequesters.put(str, new ArraySet());
                }
                ArraySet arraySet = (ArraySet) this.mUsbDisableRequesters.get(str);
                if (!z) {
                    arraySet.add(Integer.valueOf(i));
                    return true;
                }
                arraySet.remove(Integer.valueOf(i));
                return arraySet.isEmpty();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void unregisterForDisplayPortEvents(IDisplayPortAltModeInfoListener iDisplayPortAltModeInfoListener) {
        Objects.requireNonNull(iDisplayPortAltModeInfoListener, "unregisterForDisplayPortEvents: listener must not be null.");
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_USB", null);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            UsbPortManager usbPortManager = this.mPortManager;
            if (usbPortManager != null) {
                synchronized (usbPortManager.mDisplayPortListenerLock) {
                    try {
                        if (usbPortManager.mDisplayPortListeners.remove(iDisplayPortAltModeInfoListener.asBinder()) != null) {
                            iDisplayPortAltModeInfoListener.asBinder().unlinkToDeath(usbPortManager, 0);
                        }
                    } finally {
                    }
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
