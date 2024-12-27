package com.samsung.android.server.continuity;

import android.accounts.AccountManager;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.NetworkScoreService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.audio.AudioService$$ExternalSyntheticOutline0;

import com.samsung.android.mcfds.lib.AbstractDeviceSyncManager$1;
import com.samsung.android.mcfds.lib.DeviceSyncManager;
import com.samsung.android.server.continuity.autoswitch.AutoSwitchSettingHelper;
import com.samsung.android.server.continuity.autoswitch.AutoSwitchSettingHelper$$ExternalSyntheticLambda0;
import com.samsung.android.server.continuity.common.ExecutorUtil;
import com.samsung.android.server.continuity.common.Utils;
import com.samsung.android.server.continuity.sem.SemWrapper;

public final class McfDeviceSyncManager {
    static final int MSG_AVAILABLE_MCF = 5;
    static final int MSG_BIND_MCF = 2;
    static final int MSG_REPLACED_PKG = 4;
    static final int MSG_START_USER = 0;
    static final int MSG_STOP_USER = 1;
    static final int MSG_UNBIND_MCF = 3;
    public UserHandle mCurrentUserHandle;
    public final DeviceSyncManager mDsManager;
    public final AnonymousClass1 mHandler = new Handler(Looper.getMainLooper()) { // from class:
                // com.samsung.android.server.continuity.McfDeviceSyncManager.1
                @Override // android.os.Handler
                public final void handleMessage(Message message) {
                    int semGetIdentifier;
                    int i = message.what;
                    int i2 = message.arg1;
                    McfDeviceSyncManager mcfDeviceSyncManager = McfDeviceSyncManager.this;
                    if (i != 0) {
                        if (i == 1) {
                            mcfDeviceSyncManager.stopUser();
                            return;
                        }
                        if (i != 2) {
                            if (i == 3) {
                                mcfDeviceSyncManager.unbindMcf();
                                return;
                            }
                            if (i != 4) {
                                mcfDeviceSyncManager.getClass();
                                return;
                            }
                            AnonymousClass1 anonymousClass1 = mcfDeviceSyncManager.mHandler;
                            if (anonymousClass1.hasMessages(3)) {
                                Log.i(
                                        "[MCF_DS_SYS]_McfDsManager",
                                        "replacedPackage - remove unbind message");
                                mcfDeviceSyncManager.removeMessage(3);
                            }
                            if (anonymousClass1.hasMessages(2)) {
                                Log.i(
                                        "[MCF_DS_SYS]_McfDsManager",
                                        "replacedPackage - has bind message");
                                return;
                            } else {
                                Log.i("[MCF_DS_SYS]_McfDsManager", "replacedPackage");
                                mcfDeviceSyncManager.removeAndSendMessageDelayed(2, 5, 0L);
                                return;
                            }
                        }
                        if (!mcfDeviceSyncManager.mIsValidState) {
                            Log.d("[MCF_DS_SYS]_McfDsManager", "bindMcf - invalid state");
                            return;
                        }
                        DeviceSyncManager deviceSyncManager = mcfDeviceSyncManager.mDsManager;
                        int i3 = deviceSyncManager.mServiceState;
                        if (i3 != 0 && (i3 != 1 || i2 != 6)) {
                            AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                                    i3,
                                    i2,
                                    "bindMcf - invalid state ",
                                    ", reason ",
                                    "[MCF_DS_SYS]_McfDsManager");
                            if (i3 == 3) {
                                mcfDeviceSyncManager.initMcfDeviceSyncMainController(i2);
                                return;
                            }
                            return;
                        }
                        UserHandle userHandle = mcfDeviceSyncManager.mCurrentUserHandle;
                        if (userHandle == null) {
                            Log.w(
                                    "[MCF_DS_SYS]_McfDsManager",
                                    "bindMcf - null current user handle");
                            return;
                        }
                        AnonymousClass3 anonymousClass3 =
                                mcfDeviceSyncManager.new AnonymousClass3(i2);
                        if (i3 == 0 || i3 == 1) {
                            Intent intent = new Intent("com.samsung.android.mcfds.ACTION_START");
                            intent.setComponent(
                                    new ComponentName(
                                            "com.samsung.android.mcfds",
                                            "com.samsung.android.mcfds.McfDeviceSyncService"));
                            intent.putExtra("Caller", deviceSyncManager.mContext.getPackageName());
                            if (!deviceSyncManager.mContext.semBindServiceAsUser(
                                    intent, deviceSyncManager.mServiceConnection, 1, userHandle)) {
                                Log.w("[MCF_DS_LIB]_DeviceSyncManager", "connectService : failed");
                                Log.w(
                                        "[MCF_DS_SYS]_McfDsManager",
                                        "bindMcf - failed with reason: " + i2);
                                mcfDeviceSyncManager.removeAndSendMessageDelayed(2, i2, 3000L);
                                return;
                            }
                            Log.i(
                                    "[MCF_DS_LIB]_DeviceSyncManager",
                                    "connectServiceAsUser : success " + userHandle.toString());
                            deviceSyncManager.mServiceState = 2;
                            deviceSyncManager.mListener = anonymousClass3;
                        } else {
                            AudioService$$ExternalSyntheticOutline0.m(
                                    new StringBuilder("connectServiceAsUser : invalid request "),
                                    deviceSyncManager.mServiceState,
                                    "[MCF_DS_LIB]_DeviceSyncManager");
                        }
                        DirEncryptService$$ExternalSyntheticOutline0.m(
                                i2, "bindMcf - success with reason: ", "[MCF_DS_SYS]_McfDsManager");
                        return;
                    }
                    UserHandle userHandle2 = mcfDeviceSyncManager.mCurrentUserHandle;
                    if (userHandle2 == null) {
                        semGetIdentifier = -10000;
                    } else {
                        UserHandle userHandle3 = SemWrapper.SEM_ALL;
                        semGetIdentifier = userHandle2.semGetIdentifier();
                    }
                    if (semGetIdentifier == -10000) {
                        Log.w("[MCF_DS_SYS]_McfDsManager", "start : userId is USER_NULL");
                        return;
                    }
                    AnonymousClass2 anonymousClass2 = mcfDeviceSyncManager.new AnonymousClass2();
                    PreconditionObserver preconditionObserver =
                            mcfDeviceSyncManager.mPreconditionObserver;
                    preconditionObserver.mUserId = semGetIdentifier;
                    preconditionObserver.mListener = anonymousClass2;
                    IntentFilter intentFilter = new IntentFilter();
                    intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
                    intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
                    intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
                    intentFilter.addDataScheme("package");
                    intentFilter.addDataSchemeSpecificPart("com.samsung.android.scloud", 0);
                    intentFilter.addDataSchemeSpecificPart("com.samsung.android.mcfds", 0);
                    Context context = preconditionObserver.mContext;
                    UserHandle userHandleForUid =
                            UserHandle.getUserHandleForUid(preconditionObserver.mUserId);
                    UserHandle userHandle4 = SemWrapper.SEM_ALL;
                    context.semRegisterReceiverAsUser(
                            preconditionObserver.mPkgReceiver,
                            userHandleForUid,
                            intentFilter,
                            null,
                            null,
                            2);
                    IntentFilter intentFilter2 = new IntentFilter();
                    intentFilter2.addAction("android.intent.action.PACKAGE_RESTARTED");
                    intentFilter2.addDataScheme("package");
                    intentFilter2.addDataSchemeSpecificPart("com.samsung.android.mcfds", 0);
                    preconditionObserver.mContext.semRegisterReceiverAsUser(
                            preconditionObserver.mPkgReceiver,
                            UserHandle.getUserHandleForUid(preconditionObserver.mUserId),
                            intentFilter2,
                            null,
                            null,
                            2);
                    preconditionObserver.mIsPkgObserverRegistered = true;
                    if (Utils.isPackageInstalled(
                            preconditionObserver.mContext, "com.samsung.android.scloud")) {
                        preconditionObserver.setFlag(240);
                    }
                    if (Utils.isPackageInstalled(
                            preconditionObserver.mContext, "com.samsung.android.mcfds")) {
                        preconditionObserver.setFlag(3840);
                    }
                    preconditionObserver.mCurrentAccount = preconditionObserver.getSamsungAccount();
                    if (Settings.System.semGetIntForUser(
                                    preconditionObserver.mContext.getContentResolver(),
                                    "mcf_continuity",
                                    -1,
                                    preconditionObserver.mUserId)
                            == -1) {
                        preconditionObserver.setContinuitySetting(
                                (preconditionObserver.mCurrentAccount == null
                                                || Utils.isHighPowerConsumptionChipset())
                                        ? 0
                                        : 1);
                    }
                    preconditionObserver.updateSettings(preconditionObserver.isEnableSettings());
                    if (PreconditionObserver.FEATURE_CONTINUITY <= 0) {
                        Log.e(
                                "[MCF_DS_SYS]_PreconditionObserver",
                                "registerSettingsObserver - invalid feature value");
                    } else if (!preconditionObserver.mRegisterSettingsObserver) {
                        if (PreconditionObserver.isSupportedContinuity()) {
                            ContentResolver contentResolver =
                                    preconditionObserver.mContext.getContentResolver();
                            Uri uriFor = Settings.System.getUriFor("mcf_continuity");
                            AbstractPreconditionObserver$1 abstractPreconditionObserver$1 =
                                    preconditionObserver.mSettingObserver;
                            contentResolver.registerContentObserver(
                                    uriFor, false, abstractPreconditionObserver$1);
                            preconditionObserver
                                    .mContext
                                    .getContentResolver()
                                    .registerContentObserver(
                                            Settings.System.getUriFor("multi_control_enabled"),
                                            false,
                                            abstractPreconditionObserver$1);
                            preconditionObserver
                                    .mContext
                                    .getContentResolver()
                                    .registerContentObserver(
                                            Settings.System.getUriFor("vcc_continuity"),
                                            false,
                                            abstractPreconditionObserver$1);
                            preconditionObserver
                                    .mContext
                                    .getContentResolver()
                                    .registerContentObserver(
                                            Settings.System.getUriFor("hwrs_service"),
                                            false,
                                            abstractPreconditionObserver$1);
                        }
                        if (PreconditionObserver.isSupported(2)) {
                            AutoSwitchSettingHelper autoSwitchSettingHelper =
                                    preconditionObserver.mAutoSwitchSettingHelper;
                            autoSwitchSettingHelper.getClass();
                            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
                            if (defaultAdapter == null) {
                                Log.e(
                                        "[MCF_DS_SYS]_AutoSwitchSettingHelper",
                                        "start - null bluetoothAdapter");
                            } else {
                                boolean semIsBleEnabled = defaultAdapter.semIsBleEnabled();
                                AutoSwitchSettingHelper.AnonymousClass1 anonymousClass12 =
                                        autoSwitchSettingHelper.mBtStateReceiver;
                                if (semIsBleEnabled) {
                                    Log.i(
                                            "[MCF_DS_SYS]_AutoSwitchSettingHelper",
                                            "start - BleEnabled true");
                                    autoSwitchSettingHelper.setStandAloneBleMode(true);
                                    if (autoSwitchSettingHelper.mIsRegisterBtStateReceiver) {
                                        autoSwitchSettingHelper.mIsRegisterBtStateReceiver = false;
                                        autoSwitchSettingHelper.mContext.unregisterReceiver(
                                                anonymousClass12);
                                    }
                                    ExecutorUtil.sExecutorIO.execute(
                                            new ExecutorUtil.ThrowExceptionRunnable(
                                                    new AutoSwitchSettingHelper$$ExternalSyntheticLambda0(
                                                            autoSwitchSettingHelper)));
                                } else {
                                    if (!autoSwitchSettingHelper.mIsRegisterBtStateReceiver) {
                                        autoSwitchSettingHelper.mContext.registerReceiver(
                                                anonymousClass12,
                                                DirEncryptServiceHelper$$ExternalSyntheticOutline0
                                                        .m(
                                                                "com.samsung.bluetooth.adapter.action.BLE_STATE_CHANGED",
                                                                "android.bluetooth.adapter.action.STATE_CHANGED"),
                                                2);
                                        autoSwitchSettingHelper.mIsRegisterBtStateReceiver = true;
                                    }
                                    autoSwitchSettingHelper.setStandAloneBleMode(true);
                                }
                                autoSwitchSettingHelper.mContext.registerReceiver(
                                        autoSwitchSettingHelper.mReceiver,
                                        BatteryService$$ExternalSyntheticOutline0.m(
                                                "com.samsung.bluetooth.device.action.AUTO_SWITCH_MODE_CHANGED"),
                                        2);
                                IntentFilter intentFilter3 = new IntentFilter();
                                intentFilter3.addAction(
                                        "com.samsung.intent.action.SETTINGS_WIFI_BLUETOOTH_RESET");
                                autoSwitchSettingHelper.mContext.registerReceiver(
                                        autoSwitchSettingHelper.mReceiver,
                                        intentFilter3,
                                        "com.sec.android.settings.permission.WIFI_BLUETOOTH_RESET",
                                        null,
                                        2);
                                autoSwitchSettingHelper.mContext.registerReceiver(
                                        autoSwitchSettingHelper.mReceiver,
                                        BatteryService$$ExternalSyntheticOutline0.m(
                                                "android.intent.action.SETTINGS_SOFT_RESET"),
                                        "com.sec.android.settings.permission.SOFT_RESET",
                                        null,
                                        2);
                            }
                        }
                        preconditionObserver.mRegisterSettingsObserver = true;
                    }
                    if (preconditionObserver.mIsAddedAccountListener) {
                        Log.w(
                                "[MCF_DS_SYS]_PreconditionObserver",
                                "addOnAccountsUpdatedListener - already added");
                    } else {
                        Log.d("[MCF_DS_SYS]_PreconditionObserver", "addOnAccountsUpdatedListener");
                        AccountManager.get(preconditionObserver.mContext)
                                .addOnAccountsUpdatedListener(
                                        preconditionObserver.mOnAccountsUpdateListener,
                                        null,
                                        true,
                                        new String[] {PreconditionObserver.SAMSUNG_ACCOUNT_TYPE});
                        if (preconditionObserver.mUserId != 0) {
                            preconditionObserver.mContext.semRegisterReceiverAsUser(
                                    preconditionObserver.mAccountChangeReceiver,
                                    UserHandle.getUserHandleForUid(preconditionObserver.mUserId),
                                    BatteryService$$ExternalSyntheticOutline0.m(
                                            "android.accounts.LOGIN_ACCOUNTS_CHANGED"),
                                    null,
                                    null,
                                    2);
                        }
                        preconditionObserver.mIsAddedAccountListener = true;
                    }
                    if (preconditionObserver.mCurrentAccount != null) {
                        preconditionObserver.setFlag(15);
                    }
                    boolean meetConditions = preconditionObserver.meetConditions();
                    mcfDeviceSyncManager.mIsValidState = meetConditions;
                    if (meetConditions) {
                        mcfDeviceSyncManager.removeAndSendMessageDelayed(2, 1, 0L);
                    }
                }
            };
    public boolean mIsValidState;
    public final PreconditionObserver mPreconditionObserver;

    /* renamed from: com.samsung.android.server.continuity.McfDeviceSyncManager$2, reason: invalid class name */
    public final class AnonymousClass2 {
        public AnonymousClass2() {}
    }

    /* renamed from: com.samsung.android.server.continuity.McfDeviceSyncManager$3, reason: invalid class name */
    public final class AnonymousClass3 {
        public int mBindReason;

        public AnonymousClass3(int i) {
            this.mBindReason = i;
        }
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.samsung.android.server.continuity.McfDeviceSyncManager$1] */
    public McfDeviceSyncManager(
            PreconditionObserver preconditionObserver, DeviceSyncManager deviceSyncManager) {
        this.mPreconditionObserver = preconditionObserver;
        this.mDsManager = deviceSyncManager;
    }

    public Handler getHandler() {
        return this.mHandler;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0038  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x006c  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00db A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x003a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void initMcfDeviceSyncMainController(int r8) {
        /*
            r7 = this;
            r0 = 4
            com.samsung.android.server.continuity.PreconditionObserver r1 = r7.mPreconditionObserver
            r2 = 0
            r3 = 0
            if (r8 != r0) goto L20
            com.samsung.android.server.continuity.autoswitch.AutoSwitchSettingHelper r0 = r1.mAutoSwitchSettingHelper
            java.util.ArrayList r4 = r0.mAutoSwitchableDevices
            boolean r4 = r4.isEmpty()
            if (r4 == 0) goto L12
            goto L20
        L12:
            java.util.ArrayList r0 = r0.mAutoSwitchableDevices
            java.lang.Object r0 = r0.get(r2)
            com.samsung.android.server.continuity.autoswitch.BluetoothDeviceDb$DeviceProperty r0 = (com.samsung.android.server.continuity.autoswitch.BluetoothDeviceDb$DeviceProperty) r0
            if (r0 != 0) goto L1d
            goto L20
        L1d:
            java.lang.String r0 = r0.mAddress
            goto L21
        L20:
            r0 = r3
        L21:
            com.samsung.android.mcfds.lib.DeviceSyncManager r7 = r7.mDsManager
            r7.getClass()
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "initMcfDeviceSyncMainController, bindReason: "
            r4.<init>(r5)
            r4.append(r8)
            java.lang.String r5 = ", hasAutoSwitchDeviceMac: "
            r4.append(r5)
            r5 = 1
            if (r0 == 0) goto L3a
            r6 = r5
            goto L3b
        L3a:
            r6 = r2
        L3b:
            r4.append(r6)
            java.lang.String r4 = r4.toString()
            java.lang.String r6 = "[MCF_DS_LIB]_DeviceSyncManager"
            android.util.Log.i(r6, r4)
            android.os.Bundle r4 = new android.os.Bundle
            r4.<init>()
            java.lang.String r6 = "KEY_BIND_REASON"
            r4.putInt(r6, r8)
            java.lang.String r8 = "KEY_AUTO_SWITCH_DEVICE"
            r4.putString(r8, r0)
            com.samsung.android.mcfds.lib.AbstractDeviceSyncManager$1 r7 = r7.mCoreInterface
            r7.getClass()
            android.os.Message r8 = android.os.Message.obtain()
            r0 = 1002(0x3ea, float:1.404E-42)
            r8.what = r0
            r8.obj = r4
            int r7 = r7.sendMessage(r8)
            r8 = 3
            if (r7 != r8) goto Ldb
            r1.getClass()
            java.lang.String r7 = "[MCF_DS_SYS]_PreconditionObserver"
            java.lang.String r8 = "setAutoSwitchOff"
            android.util.Log.d(r7, r8)
            android.bluetooth.BluetoothAdapter r7 = android.bluetooth.BluetoothAdapter.getDefaultAdapter()
            if (r7 == 0) goto Ldb
            boolean r8 = r7.isEnabled()
            if (r8 != 0) goto L84
            goto Ldb
        L84:
            java.util.Set r7 = r7.getBondedDevices()
            if (r7 == 0) goto Ldb
            boolean r7 = r7.isEmpty()
            if (r7 != 0) goto Ldb
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            java.lang.String r8 = "disableAutoSwitchDevices - mAutoSwitchableDevices size:"
            r7.<init>(r8)
            com.samsung.android.server.continuity.autoswitch.AutoSwitchSettingHelper r8 = r1.mAutoSwitchSettingHelper
            java.util.ArrayList r0 = r8.mAutoSwitchableDevices
            int r0 = r0.size()
            r7.append(r0)
            java.lang.String r7 = r7.toString()
            java.lang.String r0 = "[MCF_DS_SYS]_AutoSwitchSettingHelper"
            android.util.Log.d(r0, r7)
            java.util.ArrayList r7 = r8.mAutoSwitchableDevices
            java.util.Iterator r7 = r7.iterator()
        Lb1:
            boolean r8 = r7.hasNext()
            if (r8 == 0) goto Ldb
            java.lang.Object r8 = r7.next()
            com.samsung.android.server.continuity.autoswitch.BluetoothDeviceDb$DeviceProperty r8 = (com.samsung.android.server.continuity.autoswitch.BluetoothDeviceDb$DeviceProperty) r8
            java.lang.String r8 = r8.mAddress
            if (r8 != 0) goto Lc3
        Lc1:
            r8 = r3
            goto Lcd
        Lc3:
            android.bluetooth.BluetoothAdapter r0 = android.bluetooth.BluetoothAdapter.getDefaultAdapter()
            if (r0 == 0) goto Lc1
            android.bluetooth.BluetoothDevice r8 = r0.getRemoteDevice(r8)
        Lcd:
            if (r8 == 0) goto Lb1
            android.os.UserHandle r0 = com.samsung.android.server.continuity.sem.SemWrapper.SEM_ALL
            int r0 = r8.semGetAutoSwitchMode()
            if (r0 != r5) goto Lb1
            r8.semSetAutoSwitchMode(r2)
            goto Lb1
        Ldb:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.server.continuity.McfDeviceSyncManager.initMcfDeviceSyncMainController(int):void");
    }

    public void removeAndSendMessageDelayed(int i, int i2, long j) {
        removeMessage(i);
        sendMessageDelayed(i, i2, j);
    }

    public void removeAndSendMessageDelayed(int i, long j) {
        removeMessage(i);
        sendEmptyMessageDelayed(i, j);
    }

    public final void removeMessage(int i) {
        AnonymousClass1 anonymousClass1 = this.mHandler;
        if (anonymousClass1.hasMessages(i)) {
            anonymousClass1.removeMessages(i);
        }
    }

    public final void sendMessageDelayed(int i, int i2, long j) {
        Message obtain = Message.obtain();
        obtain.what = i;
        obtain.arg1 = i2;
        sendMessageDelayed(obtain, j);
    }

    public final void startUser(UserHandle userHandle) {
        int semGetIdentifier;
        UserHandle userHandle2 = this.mCurrentUserHandle;
        if (userHandle2 == null) {
            semGetIdentifier = -10000;
        } else {
            UserHandle userHandle3 = SemWrapper.SEM_ALL;
            semGetIdentifier = userHandle2.semGetIdentifier();
        }
        if (semGetIdentifier != -10000) {
            Log.e("[MCF_DS_SYS]_McfDsManager", "startUser - invalid request!");
            return;
        }
        StringBuilder sb = new StringBuilder("startUser : ");
        UserHandle userHandle4 = SemWrapper.SEM_ALL;
        sb.append(userHandle.semGetIdentifier());
        Log.d("[MCF_DS_SYS]_McfDsManager", sb.toString());
        this.mCurrentUserHandle = userHandle;
        removeAndSendMessageDelayed(0, 0L);
    }

    public final void stopUser() {
        this.mCurrentUserHandle = null;
        PreconditionObserver preconditionObserver = this.mPreconditionObserver;
        if (preconditionObserver.mIsPkgObserverRegistered) {
            preconditionObserver.mContext.unregisterReceiver(preconditionObserver.mPkgReceiver);
            preconditionObserver.mIsPkgObserverRegistered = false;
        }
        if (preconditionObserver.mRegisterSettingsObserver) {
            if (PreconditionObserver.isSupportedContinuity()) {
                preconditionObserver
                        .mContext
                        .getContentResolver()
                        .unregisterContentObserver(preconditionObserver.mSettingObserver);
            }
            if (PreconditionObserver.isSupported(2)) {
                AutoSwitchSettingHelper autoSwitchSettingHelper =
                        preconditionObserver.mAutoSwitchSettingHelper;
                autoSwitchSettingHelper.mIsAutoSwitchModeEnabled = false;
                autoSwitchSettingHelper.mContext.unregisterReceiver(
                        autoSwitchSettingHelper.mReceiver);
                if (autoSwitchSettingHelper.mIsRegisterBtStateReceiver) {
                    autoSwitchSettingHelper.mIsRegisterBtStateReceiver = false;
                    autoSwitchSettingHelper.mContext.unregisterReceiver(
                            autoSwitchSettingHelper.mBtStateReceiver);
                }
            }
            preconditionObserver.mRegisterSettingsObserver = false;
        }
        if (preconditionObserver.mIsAddedAccountListener) {
            Log.d("[MCF_DS_SYS]_PreconditionObserver", "removeOnAccountsUpdatedListener");
            AccountManager.get(preconditionObserver.mContext)
                    .removeOnAccountsUpdatedListener(
                            preconditionObserver.mOnAccountsUpdateListener);
            if (preconditionObserver.mUserId != 0) {
                preconditionObserver.mContext.unregisterReceiver(
                        preconditionObserver.mAccountChangeReceiver);
            }
            preconditionObserver.mIsAddedAccountListener = false;
        } else {
            Log.w(
                    "[MCF_DS_SYS]_PreconditionObserver",
                    "removeOnAccountsUpdatedListener - already added");
        }
        preconditionObserver.mCurrentAccount = null;
        preconditionObserver.mState = 0;
        preconditionObserver.mUserId = -10000;
        unbindMcf();
    }

    public final void unbindMcf() {
        if (hasMessages(2)) {
            Log.d("[MCF_DS_SYS]_McfDsManager", "unbindMcf - remove bind message");
            removeMessage(2);
        }
        DeviceSyncManager deviceSyncManager = this.mDsManager;
        int i = deviceSyncManager.mServiceState;
        if (i == 0) {
            NetworkScoreService$$ExternalSyntheticOutline0.m(
                    i, "unbindMcf - invalid state ", "[MCF_DS_SYS]_McfDsManager");
            return;
        }
        Log.i("[MCF_DS_SYS]_McfDsManager", "unbindMcf");
        if (deviceSyncManager.mServiceState == 0) {
            Log.w("[MCF_DS_LIB]_DeviceSyncManager", "disconnectService : invalid request");
            return;
        }
        Log.i("[MCF_DS_LIB]_DeviceSyncManager", "disconnectService");
        AbstractDeviceSyncManager$1 abstractDeviceSyncManager$1 = deviceSyncManager.mCoreInterface;
        abstractDeviceSyncManager$1.getClass();
        Message obtain = Message.obtain();
        obtain.what = 11;
        obtain.obj = null;
        abstractDeviceSyncManager$1.sendMessage(obtain);
        try {
            deviceSyncManager.mContext.unbindService(deviceSyncManager.mServiceConnection);
        } catch (IllegalArgumentException unused) {
            Log.w("[MCF_DS_LIB]_DeviceSyncManager", "disconnectService : IllegalArgumentException");
        }
        deviceSyncManager.mServiceState = 0;
        deviceSyncManager.mService = null;
        deviceSyncManager.mListener = null;
    }
}
