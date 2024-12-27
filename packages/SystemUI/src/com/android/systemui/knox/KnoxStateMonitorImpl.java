package com.android.systemui.knox;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.UserInfo;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os.UserManager;
import android.sec.enterprise.EnterpriseDeviceManager;
import android.util.Log;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import androidx.core.app.NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecSecurityContainerController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.SettingsHelper;
import com.google.android.collect.Lists;
import com.samsung.android.knox.EnterpriseDeviceAdminInfo;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.custom.PrivateCustomDeviceManager;
import com.samsung.android.knox.dar.ddar.DualDarManager;
import com.samsung.android.knox.ucm.core.IUcmService;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class KnoxStateMonitorImpl implements KnoxStateMonitor, Dumpable {
    public final Context mContext;
    public final SelectedUserInteractor mSelectedUserInteractor;
    public final ArrayList mCallbacks = Lists.newArrayList();
    public final AnonymousClass1 mUCMReceiver = new BroadcastReceiver() { // from class: com.android.systemui.knox.KnoxStateMonitorImpl.1
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if ("com.samsung.ucs.ucsservice.stateblocked".equals(intent.getAction())) {
                String str = KnoxStateMonitorImpl.this.mUcmMonitor.mUCMVendor;
                String stringExtra = intent.getStringExtra("UCS_CSNAME");
                if (str == null || stringExtra == null || !stringExtra.equals(str)) {
                    return;
                }
                Log.d("KnoxStateMonitorImpl", "com.samsung.ucs.ucsservice.stateblocked intent!");
                sendEmptyMessage(5005);
            }
        }
    };
    public final AnonymousClass2 mBroadCastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.knox.KnoxStateMonitorImpl.2
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("com.sec.knox.keyguard.show".equals(action)) {
                if (SemPersonaManager.isKnoxId(intent.getIntExtra("personaId", -1))) {
                    sendMessage(obtainMessage(5002, Boolean.valueOf(intent.getBooleanExtra("isShown", false))));
                    return;
                }
                return;
            }
            if ("com.samsung.android.knox.intent.action.DO_KEYGUARD_INTERNAL".equals(action)) {
                sendMessage(obtainMessage(5003, Integer.valueOf(getSendingUserId())));
            } else if ("com.samsung.knox.app.action.DEVICE_POLICY_MANAGER_PASSWORD_CHANGED".equals(action)) {
                sendEmptyMessage(5004);
            }
        }
    };
    public final AnonymousClass3 mHandler = new Handler(Looper.getMainLooper()) { // from class: com.android.systemui.knox.KnoxStateMonitorImpl.3
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            int i2 = 0;
            KnoxStateMonitorImpl knoxStateMonitorImpl = KnoxStateMonitorImpl.this;
            switch (i) {
                case 5002:
                    ((Boolean) message.obj).booleanValue();
                    knoxStateMonitorImpl.getClass();
                    Log.d("KnoxStateMonitorImpl", "handleUpdateKnoxKeyguardState");
                    while (i2 < knoxStateMonitorImpl.mCallbacks.size()) {
                        i2++;
                    }
                    break;
                case 5003:
                    int intValue = ((Integer) message.obj).intValue();
                    knoxStateMonitorImpl.getClass();
                    Log.d("KnoxStateMonitorImpl", "handleDoKeyguard " + intValue);
                    while (i2 < knoxStateMonitorImpl.mCallbacks.size()) {
                        KnoxStateMonitorCallback knoxStateMonitorCallback = (KnoxStateMonitorCallback) ((WeakReference) knoxStateMonitorImpl.mCallbacks.get(i2)).get();
                        if (knoxStateMonitorCallback != null) {
                            knoxStateMonitorCallback.onDoKeyguard(intValue);
                        }
                        i2++;
                    }
                    break;
                case 5004:
                    knoxStateMonitorImpl.getClass();
                    Log.d("KnoxStateMonitorImpl", "handleDPMPasswordChanged");
                    while (i2 < knoxStateMonitorImpl.mCallbacks.size()) {
                        KnoxStateMonitorCallback knoxStateMonitorCallback2 = (KnoxStateMonitorCallback) ((WeakReference) knoxStateMonitorImpl.mCallbacks.get(i2)).get();
                        if (knoxStateMonitorCallback2 != null) {
                            knoxStateMonitorCallback2.onDPMPasswordChanged();
                        }
                        i2++;
                    }
                    break;
                case 5005:
                    knoxStateMonitorImpl.getClass();
                    Log.d("KnoxStateMonitorImpl", "handleEnableUCMLock");
                    while (i2 < knoxStateMonitorImpl.mCallbacks.size()) {
                        KnoxStateMonitorCallback knoxStateMonitorCallback3 = (KnoxStateMonitorCallback) ((WeakReference) knoxStateMonitorImpl.mCallbacks.get(i2)).get();
                        if (knoxStateMonitorCallback3 != null) {
                            knoxStateMonitorCallback3.onEnableUCMLock();
                        }
                        i2++;
                    }
                    break;
                case 5006:
                case 5007:
                case 5008:
                case 5009:
                case 5016:
                case 5021:
                default:
                    Log.d("KnoxStateMonitorImpl", "ignore message");
                    break;
                case 5010:
                    knoxStateMonitorImpl.getClass();
                    Log.d("KnoxStateMonitorImpl", "handleUpdateLockscreenHiddenItems");
                    while (i2 < knoxStateMonitorImpl.mCallbacks.size()) {
                        KnoxStateMonitorCallback knoxStateMonitorCallback4 = (KnoxStateMonitorCallback) ((WeakReference) knoxStateMonitorImpl.mCallbacks.get(i2)).get();
                        if (knoxStateMonitorCallback4 != null) {
                            knoxStateMonitorCallback4.onUpdateLockscreenHiddenItems();
                        }
                        i2++;
                    }
                    break;
                case 5011:
                    knoxStateMonitorImpl.getClass();
                    Log.d("KnoxStateMonitorImpl", "handleUpdateLockTypeOverride");
                    while (i2 < knoxStateMonitorImpl.mCallbacks.size()) {
                        i2++;
                    }
                    break;
                case 5012:
                    knoxStateMonitorImpl.getClass();
                    Log.d("KnoxStateMonitorImpl", "handleUpdateQuickPanelButtons");
                    while (i2 < knoxStateMonitorImpl.mCallbacks.size()) {
                        KnoxStateMonitorCallback knoxStateMonitorCallback5 = (KnoxStateMonitorCallback) ((WeakReference) knoxStateMonitorImpl.mCallbacks.get(i2)).get();
                        if (knoxStateMonitorCallback5 != null) {
                            knoxStateMonitorCallback5.onUpdateQuickPanelButtons();
                        }
                        i2++;
                    }
                    break;
                case 5013:
                    knoxStateMonitorImpl.getClass();
                    Log.d("KnoxStateMonitorImpl", "handleUpdateQuickPanelEdit");
                    while (i2 < knoxStateMonitorImpl.mCallbacks.size()) {
                        i2++;
                    }
                    break;
                case 5014:
                    knoxStateMonitorImpl.getClass();
                    Log.d("KnoxStateMonitorImpl", "handleUpdateQuickPanelItems");
                    while (i2 < knoxStateMonitorImpl.mCallbacks.size()) {
                        KnoxStateMonitorCallback knoxStateMonitorCallback6 = (KnoxStateMonitorCallback) ((WeakReference) knoxStateMonitorImpl.mCallbacks.get(i2)).get();
                        if (knoxStateMonitorCallback6 != null) {
                            knoxStateMonitorCallback6.onUpdateQuickPanelItems();
                        }
                        i2++;
                    }
                    break;
                case 5015:
                    knoxStateMonitorImpl.getClass();
                    Log.d("KnoxStateMonitorImpl", "handleUpdateStatusBarText");
                    while (i2 < knoxStateMonitorImpl.mCallbacks.size()) {
                        KnoxStateMonitorCallback knoxStateMonitorCallback7 = (KnoxStateMonitorCallback) ((WeakReference) knoxStateMonitorImpl.mCallbacks.get(i2)).get();
                        if (knoxStateMonitorCallback7 != null) {
                            knoxStateMonitorCallback7.onUpdateStatusBarText();
                        }
                        i2++;
                    }
                    break;
                case 5017:
                    knoxStateMonitorImpl.getClass();
                    Log.d("KnoxStateMonitorImpl", "handleUpdateStatusBarIcons");
                    while (i2 < knoxStateMonitorImpl.mCallbacks.size()) {
                        KnoxStateMonitorCallback knoxStateMonitorCallback8 = (KnoxStateMonitorCallback) ((WeakReference) knoxStateMonitorImpl.mCallbacks.get(i2)).get();
                        if (knoxStateMonitorCallback8 != null) {
                            knoxStateMonitorCallback8.onUpdateStatusBarIcons();
                        }
                        i2++;
                    }
                    break;
                case 5018:
                    knoxStateMonitorImpl.getClass();
                    Log.d("KnoxStateMonitorImpl", "handleUpdateStatusBarBatteryColour");
                    while (i2 < knoxStateMonitorImpl.mCallbacks.size()) {
                        i2++;
                    }
                    break;
                case 5019:
                    Log.d("KnoxStateMonitorImpl", "handleUpdateStatusBarHidden() - mCallbacks.size is " + knoxStateMonitorImpl.mCallbacks.size());
                    while (i2 < knoxStateMonitorImpl.mCallbacks.size()) {
                        KnoxStateMonitorCallback knoxStateMonitorCallback9 = (KnoxStateMonitorCallback) ((WeakReference) knoxStateMonitorImpl.mCallbacks.get(i2)).get();
                        if (knoxStateMonitorCallback9 != null) {
                            knoxStateMonitorCallback9.onUpdateStatusBarHidden();
                            Log.d("KnoxStateMonitorImpl", "         -" + i2 + "=" + knoxStateMonitorCallback9);
                        }
                        i2++;
                    }
                    break;
                case 5020:
                    knoxStateMonitorImpl.getClass();
                    Log.d("KnoxStateMonitorImpl", "handleUpdateNavigationBarHidden");
                    while (i2 < knoxStateMonitorImpl.mCallbacks.size()) {
                        KnoxStateMonitorCallback knoxStateMonitorCallback10 = (KnoxStateMonitorCallback) ((WeakReference) knoxStateMonitorImpl.mCallbacks.get(i2)).get();
                        if (knoxStateMonitorCallback10 != null) {
                            knoxStateMonitorCallback10.onUpdateNavigationBarHidden();
                        }
                        i2++;
                    }
                    break;
                case 5022:
                    knoxStateMonitorImpl.getClass();
                    Log.d("KnoxStateMonitorImpl", "handleDisableDeviceWhenReachMaxFailed");
                    while (i2 < knoxStateMonitorImpl.mCallbacks.size()) {
                        KnoxStateMonitorCallback knoxStateMonitorCallback11 = (KnoxStateMonitorCallback) ((WeakReference) knoxStateMonitorImpl.mCallbacks.get(i2)).get();
                        if (knoxStateMonitorCallback11 != null) {
                            knoxStateMonitorCallback11.onDisableDeviceWhenReachMaxFailed();
                        }
                        i2++;
                    }
                    break;
                case 5023:
                    knoxStateMonitorImpl.getClass();
                    Log.d("KnoxStateMonitorImpl", "handleUpdateQuickPanelButtonUsers");
                    while (i2 < knoxStateMonitorImpl.mCallbacks.size()) {
                        i2++;
                    }
                    break;
                case 5024:
                    knoxStateMonitorImpl.getClass();
                    Log.d("KnoxStateMonitorImpl", "handleEnableMDMWallpaper");
                    while (i2 < knoxStateMonitorImpl.mCallbacks.size()) {
                        KnoxStateMonitorCallback knoxStateMonitorCallback12 = (KnoxStateMonitorCallback) ((WeakReference) knoxStateMonitorImpl.mCallbacks.get(i2)).get();
                        if (knoxStateMonitorCallback12 != null) {
                            knoxStateMonitorCallback12.onEnableMDMWallpaper();
                        }
                        i2++;
                    }
                    break;
                case 5025:
                    knoxStateMonitorImpl.getClass();
                    Log.d("KnoxStateMonitorImpl", "handleMDMWallpaperChanged");
                    while (i2 < knoxStateMonitorImpl.mCallbacks.size()) {
                        KnoxStateMonitorCallback knoxStateMonitorCallback13 = (KnoxStateMonitorCallback) ((WeakReference) knoxStateMonitorImpl.mCallbacks.get(i2)).get();
                        if (knoxStateMonitorCallback13 != null) {
                            knoxStateMonitorCallback13.onMDMWallpaperChanged();
                        }
                        i2++;
                    }
                    break;
                case 5026:
                    knoxStateMonitorImpl.getClass();
                    Log.d("KnoxStateMonitorImpl", "handleUpdateAdminLock");
                    while (i2 < knoxStateMonitorImpl.mCallbacks.size()) {
                        KnoxStateMonitorCallback knoxStateMonitorCallback14 = (KnoxStateMonitorCallback) ((WeakReference) knoxStateMonitorImpl.mCallbacks.get(i2)).get();
                        if (knoxStateMonitorCallback14 != null) {
                            knoxStateMonitorCallback14.onUpdateAdminLock();
                        }
                        i2++;
                    }
                    break;
                case 5027:
                    boolean booleanValue = ((Boolean) message.obj).booleanValue();
                    knoxStateMonitorImpl.getClass();
                    Log.d("KnoxStateMonitorImpl", "handleSetHardKeyIntentState");
                    while (i2 < knoxStateMonitorImpl.mCallbacks.size()) {
                        KnoxStateMonitorCallback knoxStateMonitorCallback15 = (KnoxStateMonitorCallback) ((WeakReference) knoxStateMonitorImpl.mCallbacks.get(i2)).get();
                        if (knoxStateMonitorCallback15 != null) {
                            knoxStateMonitorCallback15.onSetHardKeyIntentState(booleanValue);
                        }
                        i2++;
                    }
                    break;
                case 5028:
                    knoxStateMonitorImpl.getClass();
                    Log.d("KnoxStateMonitorImpl", "handleUpdateQuickPanelUnavailableButtons");
                    while (i2 < knoxStateMonitorImpl.mCallbacks.size()) {
                        KnoxStateMonitorCallback knoxStateMonitorCallback16 = (KnoxStateMonitorCallback) ((WeakReference) knoxStateMonitorImpl.mCallbacks.get(i2)).get();
                        if (knoxStateMonitorCallback16 != null) {
                            knoxStateMonitorCallback16.onUpdateQuickPanelUnavailableButtons();
                        }
                        i2++;
                    }
                    break;
                case 5029:
                    knoxStateMonitorImpl.getClass();
                    Log.d("KnoxStateMonitorImpl", "handleDisableProfileWhenReachMaxFailed");
                    while (i2 < knoxStateMonitorImpl.mCallbacks.size()) {
                        KnoxStateMonitorCallback knoxStateMonitorCallback17 = (KnoxStateMonitorCallback) ((WeakReference) knoxStateMonitorImpl.mCallbacks.get(i2)).get();
                        if (knoxStateMonitorCallback17 != null) {
                            knoxStateMonitorCallback17.onDisableProfileWhenReachMaxFailed();
                        }
                        i2++;
                    }
                    break;
            }
        }
    };
    public final CustomSdkMonitor mCustomSdkMonitor = new CustomSdkMonitor(this);
    public final ContainerMonitor mContainerMonitor = new ContainerMonitor(this);
    public final DualDarMonitor mDualDarMonitor = new DualDarMonitor(this);
    public final EdmMonitor mEdmMonitor = new EdmMonitor(this);
    public final SdpMonitor mSdpMonitor = new SdpMonitor();
    public final UcmMonitor mUcmMonitor = new UcmMonitor();

    public KnoxStateMonitorImpl(Context context, DumpManager dumpManager, SelectedUserInteractor selectedUserInteractor) {
        this.mContext = context;
        this.mSelectedUserInteractor = selectedUserInteractor;
        if (DeviceState.isTesting()) {
            initKnoxClass();
        } else {
            Thread thread = new Thread(new Runnable() { // from class: com.android.systemui.knox.KnoxStateMonitorImpl$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    KnoxStateMonitorImpl knoxStateMonitorImpl = KnoxStateMonitorImpl.this;
                    knoxStateMonitorImpl.initKnoxClass();
                    IntentFilter intentFilter = new IntentFilter();
                    intentFilter.addAction("com.sec.knox.keyguard.show");
                    intentFilter.addAction("com.samsung.android.knox.intent.action.DO_KEYGUARD_INTERNAL");
                    intentFilter.addAction("com.samsung.knox.app.action.DEVICE_POLICY_MANAGER_PASSWORD_CHANGED");
                    ((BroadcastDispatcher) Dependency.sDependency.getDependencyInner(BroadcastDispatcher.class)).registerReceiver(intentFilter, knoxStateMonitorImpl.mBroadCastReceiver);
                    try {
                        IntentFilter intentFilter2 = new IntentFilter();
                        intentFilter2.addAction("com.samsung.ucs.ucsservice.stateblocked");
                        knoxStateMonitorImpl.mContext.registerReceiver(knoxStateMonitorImpl.mUCMReceiver, intentFilter2, EnterpriseDeviceAdminInfo.USES_POLICY_KNOX_UCM_MGMT_TAG, null, 2);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, "KnoxStateMonitorImpl");
            thread.setPriority(10);
            thread.start();
        }
        dumpManager.registerDumpable(this);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("KnoxStateMonitor state:");
        CustomSdkMonitor customSdkMonitor = this.mCustomSdkMonitor;
        if (customSdkMonitor != null) {
            customSdkMonitor.dump(printWriter, strArr);
        }
        ContainerMonitor containerMonitor = this.mContainerMonitor;
        if (containerMonitor != null) {
            containerMonitor.getClass();
        }
        DualDarMonitor dualDarMonitor = this.mDualDarMonitor;
        if (dualDarMonitor != null) {
            dualDarMonitor.getClass();
        }
        EdmMonitor edmMonitor = this.mEdmMonitor;
        if (edmMonitor != null) {
            edmMonitor.dump(printWriter, strArr);
        }
        UcmMonitor ucmMonitor = this.mUcmMonitor;
        if (ucmMonitor != null) {
            ucmMonitor.getClass();
        }
        SdpMonitor sdpMonitor = this.mSdpMonitor;
        if (sdpMonitor != null) {
            sdpMonitor.getClass();
        }
        printWriter.print("mCallback size=");
        printWriter.println(this.mCallbacks.size());
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            KnoxStateMonitorCallback knoxStateMonitorCallback = (KnoxStateMonitorCallback) ((WeakReference) this.mCallbacks.get(i)).get();
            if (knoxStateMonitorCallback != null) {
                printWriter.println("   -" + i + "=" + knoxStateMonitorCallback);
            }
        }
    }

    public final List getContainerIds() {
        ContainerMonitor containerMonitor = this.mContainerMonitor;
        ArrayList arrayList = null;
        if (containerMonitor == null) {
            return null;
        }
        UserManager userManager = containerMonitor.mUserManager;
        if (userManager != null) {
            containerMonitor.mPersonas = userManager.getUsers(true);
        }
        List list = containerMonitor.mPersonas;
        if (list != null && list.size() > 0) {
            arrayList = new ArrayList();
            Iterator it = containerMonitor.mPersonas.iterator();
            while (it.hasNext()) {
                arrayList.add(Integer.valueOf(((UserInfo) it.next()).id));
            }
        }
        return arrayList;
    }

    public final long getDualDarInnerLockoutAttemptDeadline$1() {
        DualDarMonitor dualDarMonitor = this.mDualDarMonitor;
        if (dualDarMonitor == null) {
            return -1L;
        }
        int selectedUserId = this.mSelectedUserInteractor.getSelectedUserId();
        dualDarMonitor.getClass();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        long j = dualDarMonitor.mLockoutAttemptDeadline;
        return (j >= elapsedRealtime || dualDarMonitor.mLockoutAttemptTimeout == 0) ? j > elapsedRealtime + dualDarMonitor.mLockoutAttemptTimeout ? dualDarMonitor.mLockPatternUtils.getLockoutAttemptDeadline(dualDarMonitor.getInnerAuthUserId(selectedUserId)) : j : dualDarMonitor.mLockPatternUtils.getLockoutAttemptDeadline(dualDarMonitor.getInnerAuthUserId(selectedUserId));
    }

    public final int getInnerAuthUserId(int i) {
        DualDarMonitor dualDarMonitor = this.mDualDarMonitor;
        return dualDarMonitor == null ? i : dualDarMonitor.getInnerAuthUserId(i);
    }

    public final int getMainUserId(int i) {
        DualDarMonitor dualDarMonitor = this.mDualDarMonitor;
        if (dualDarMonitor == null) {
            return i;
        }
        int mainUserId = dualDarMonitor.mDualDarAuthUtils.getMainUserId(i);
        SuggestionsAdapter$$ExternalSyntheticOutline0.m(i, mainUserId, "getMainUserId - userId : ", ", ret : ", "DualDarMonitor");
        return mainUserId;
    }

    public final List getQuickPanelItems() {
        CustomSdkMonitor customSdkMonitor = this.mCustomSdkMonitor;
        if (customSdkMonitor == null) {
            return null;
        }
        customSdkMonitor.getClass();
        ArrayList arrayList = new ArrayList();
        String str = customSdkMonitor.mQuickPanelItems;
        if (str == null) {
            return null;
        }
        for (String str2 : str.split(",")) {
            if (str2.length() > 0) {
                arrayList.add(str2);
            }
        }
        return arrayList;
    }

    public final List getQuickPanelUnavailableButtons() {
        CustomSdkMonitor customSdkMonitor = this.mCustomSdkMonitor;
        if (customSdkMonitor == null) {
            return null;
        }
        customSdkMonitor.getClass();
        ArrayList arrayList = new ArrayList();
        String str = customSdkMonitor.mQuickPanelUnavailableButtons;
        if (str == null) {
            return null;
        }
        for (String str2 : str.split(",")) {
            if (str2.length() > 0) {
                arrayList.add(str2);
            }
        }
        return arrayList;
    }

    public final void initKnoxClass() {
        CustomSdkMonitor customSdkMonitor = this.mCustomSdkMonitor;
        customSdkMonitor.getClass();
        if (!DeviceState.isTesting()) {
            PrivateCustomDeviceManager privateCustomDeviceManager = PrivateCustomDeviceManager.getInstance();
            try {
                if (privateCustomDeviceManager != null) {
                    Log.d("CustomSdkMonitor", "registerCallback this = " + customSdkMonitor);
                    privateCustomDeviceManager.registerSystemUICallback(customSdkMonitor);
                } else {
                    Log.d("CustomSdkMonitor", "registerSystemUICallback() cannot reference because privateCustomDeviceManager is null");
                }
            } catch (Exception e) {
                Log.e("CustomSdkMonitor", "registerSystemUICallback() Failed!", e);
            }
        }
        this.mContainerMonitor.getClass();
        this.mDualDarMonitor.getClass();
        EdmMonitor edmMonitor = this.mEdmMonitor;
        edmMonitor.getClass();
        if (!DeviceState.isTesting()) {
            EnterpriseDeviceManager enterpriseDeviceManager = EnterpriseDeviceManager.getInstance();
            try {
                if (enterpriseDeviceManager != null) {
                    enterpriseDeviceManager.registerSystemUICallback(edmMonitor);
                } else {
                    Log.d("EdmMonitor", "registerKnoxCallback() cannot reference because edm is null");
                }
            } catch (Exception e2) {
                Log.e("EdmMonitor", "registerKnoxCallback() Failed!", e2);
            }
            UserInfo userInfo = edmMonitor.mUserManager.getUserInfo(ActivityManager.getCurrentUser());
            if (userInfo != null) {
                edmMonitor.mEnableAdminLock = userInfo.isAdminLocked();
                edmMonitor.mLicenseExpired = false;
                StringBuilder sb = new StringBuilder("EdmMonitor registerCallback mEnableAdminLock:");
                sb.append(edmMonitor.mEnableAdminLock);
                sb.append("  mLicenseExpired:");
                ActionBarContextView$$ExternalSyntheticOutline0.m(sb, edmMonitor.mLicenseExpired, "EdmMonitor");
            }
        }
        this.mSdpMonitor.getClass();
        UcmMonitor ucmMonitor = this.mUcmMonitor;
        ucmMonitor.getClass();
        if (DeviceState.isTesting()) {
            return;
        }
        IUcmService asInterface = IUcmService.Stub.asInterface(ServiceManager.getService("com.samsung.ucs.ucsservice"));
        if (asInterface == null) {
            Log.d("SdpMonitor", "UcmMonitor failed to get UCM System service");
            return;
        }
        try {
            asInterface.registerSystemUICallback(ucmMonitor);
        } catch (Exception unused) {
            Log.d("SdpMonitor", "UcmMonitor failed to be registered");
        }
    }

    public final boolean isAdminLockEnabled() {
        EdmMonitor edmMonitor = this.mEdmMonitor;
        return edmMonitor != null && (edmMonitor.mEnableAdminLock || edmMonitor.mLicenseExpired);
    }

    public final boolean isAirplaneModeTileBlocked() {
        EdmMonitor edmMonitor = this.mEdmMonitor;
        if (edmMonitor != null) {
            Context context = edmMonitor.knoxStateMonitor.mContext;
            if ((!edmMonitor.mSettingsChangesAllowed) || !edmMonitor.mAirplaneModeAllowed) {
                return true;
            }
        }
        return false;
    }

    public final boolean isBluetoothTileBlocked() {
        EdmMonitor edmMonitor = this.mEdmMonitor;
        if (edmMonitor != null) {
            Context context = edmMonitor.knoxStateMonitor.mContext;
            if ((!edmMonitor.mSettingsChangesAllowed) || !edmMonitor.mBluetoothAllowed) {
                return true;
            }
        }
        return false;
    }

    public final boolean isBrightnessBlocked() {
        EdmMonitor edmMonitor = this.mEdmMonitor;
        if (edmMonitor != null) {
            Context context = edmMonitor.knoxStateMonitor.mContext;
            if (!edmMonitor.mSettingsChangesAllowed) {
                return true;
            }
        }
        return false;
    }

    public final boolean isDeviceDisabledForMaxFailedAttempt() {
        EdmMonitor edmMonitor = this.mEdmMonitor;
        if (edmMonitor == null) {
            return false;
        }
        if (edmMonitor.mIsDeviceDisableForMaxFailedAttempt && edmMonitor.mMaxNumFailedAttemptForDisable <= 0) {
            edmMonitor.mIsDeviceDisableForMaxFailedAttempt = false;
        }
        return edmMonitor.mIsDeviceDisableForMaxFailedAttempt;
    }

    public final void isDisableDeviceByMultifactor() {
        if (isMultifactorAuthEnforced()) {
            Log.d("KnoxStateMonitorImpl", "isDisableDeviceByMultifactor( = false");
        }
    }

    public final boolean isDualDarDeviceOwner(int i) {
        DualDarMonitor dualDarMonitor = this.mDualDarMonitor;
        if (dualDarMonitor != null) {
            dualDarMonitor.getClass();
            if (DualDarManager.isOnDeviceOwner(i)) {
                return true;
            }
        }
        return false;
    }

    public final boolean isDualDarInnerAuthRequired(int i) {
        DualDarMonitor dualDarMonitor = this.mDualDarMonitor;
        if (dualDarMonitor != null) {
            boolean isInnerAuthRequired = DualDarManager.getInstance(dualDarMonitor.mContext).isInnerAuthRequired(i);
            KeyguardSecSecurityContainerController$$ExternalSyntheticOutline0.m("isInnerAuthRequired - userId : ", i, ", ret : ", isInnerAuthRequired, "DualDarMonitor");
            if (isInnerAuthRequired) {
                return true;
            }
        }
        return false;
    }

    public final boolean isDualDarInnerLayerUnlocked(int i) {
        DualDarMonitor dualDarMonitor = this.mDualDarMonitor;
        if (dualDarMonitor != null) {
            boolean isInnerLayerUnlocked = DualDarManager.getInstance(dualDarMonitor.mContext).isInnerLayerUnlocked(i);
            KeyguardSecSecurityContainerController$$ExternalSyntheticOutline0.m("isDualDarInnerLayerUnlocked - userId : ", i, ", ret : ", isInnerLayerUnlocked, "DualDarMonitor");
            if (isInnerLayerUnlocked) {
                return true;
            }
        }
        return false;
    }

    public final boolean isFlashlightTileBlocked() {
        EdmMonitor edmMonitor = this.mEdmMonitor;
        if (edmMonitor != null) {
            Context context = edmMonitor.knoxStateMonitor.mContext;
            if (!edmMonitor.mSettingsChangesAllowed) {
                return true;
            }
        }
        return false;
    }

    public final boolean isLockScreenDisabledbyKNOX() {
        return (((KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class)).isForcedLock() || (this.mCustomSdkMonitor.mKnoxCustomLockScreenOverrideMode & 2) == 0) ? false : true;
    }

    public final boolean isMobileDataTileBlocked() {
        EdmMonitor edmMonitor = this.mEdmMonitor;
        if (edmMonitor != null) {
            Context context = edmMonitor.knoxStateMonitor.mContext;
            if ((!edmMonitor.mSettingsChangesAllowed) || !edmMonitor.mCellularDataAllowed) {
                return true;
            }
        }
        return false;
    }

    public final boolean isMultifactorAuthEnforced() {
        if (this.mEdmMonitor.mMultiFactorAuthEnabled) {
            return ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isLock2StepVerificationEnabled();
        }
        return false;
    }

    public final boolean isPersona(int i) {
        if (DeviceState.isTesting()) {
            return false;
        }
        ContainerMonitor containerMonitor = this.mContainerMonitor;
        if (i != 0) {
            containerMonitor.getClass();
            return SemPersonaManager.isKnoxId(i);
        }
        if (!containerMonitor.KNOX_DEBUG) {
            return false;
        }
        NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m(i, "isPersona for user ", " is false", "ContainerMonitor");
        return false;
    }

    public final boolean isRotationLockTileBlocked() {
        EdmMonitor edmMonitor = this.mEdmMonitor;
        if (edmMonitor != null) {
            Context context = edmMonitor.knoxStateMonitor.mContext;
            if (!edmMonitor.mSettingsChangesAllowed) {
                return true;
            }
        }
        return false;
    }

    public final boolean isSecureFolder(int i) {
        if (DeviceState.isTesting()) {
            return false;
        }
        ContainerMonitor containerMonitor = this.mContainerMonitor;
        if (i != 0) {
            containerMonitor.getClass();
            return SemPersonaManager.isSecureFolderId(i);
        }
        if (!containerMonitor.KNOX_DEBUG) {
            return false;
        }
        NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m(i, "isSecureFolder for user ", " is false", "ContainerMonitor");
        return false;
    }

    public final boolean isSoundModeTileBlocked() {
        EdmMonitor edmMonitor = this.mEdmMonitor;
        if (edmMonitor != null) {
            Context context = edmMonitor.knoxStateMonitor.mContext;
            if (!edmMonitor.mSettingsChangesAllowed) {
                return true;
            }
        }
        return false;
    }

    public final boolean isStatusBarHidden() {
        CustomSdkMonitor customSdkMonitor = this.mCustomSdkMonitor;
        return customSdkMonitor != null && (customSdkMonitor.mIsCustomSdkStatusBarHidden || this.mEdmMonitor.mIsStatusBarHidden);
    }

    public final boolean isUserMobileDataRestricted() {
        EdmMonitor edmMonitor = this.mEdmMonitor;
        return edmMonitor != null && edmMonitor.mUserManager.hasUserRestriction("no_config_mobile_networks", UserHandle.of(KeyguardUpdateMonitor.getCurrentUser()));
    }

    public final boolean isWifiHotspotTileBlocked() {
        EdmMonitor edmMonitor = this.mEdmMonitor;
        if (edmMonitor != null) {
            Context context = edmMonitor.knoxStateMonitor.mContext;
            if ((!edmMonitor.mSettingsChangesAllowed) || !edmMonitor.mWifiTetheringAllowed) {
                return true;
            }
        }
        return false;
    }

    public final boolean isWifiTileBlocked() {
        EdmMonitor edmMonitor = this.mEdmMonitor;
        if (edmMonitor != null) {
            Context context = edmMonitor.knoxStateMonitor.mContext;
            if ((!edmMonitor.mSettingsChangesAllowed) || !edmMonitor.mWifiAllowed || !edmMonitor.mWifiStateChangeAllowed) {
                return true;
            }
        }
        return false;
    }

    public final synchronized void registerCallback(KnoxStateMonitorCallback knoxStateMonitorCallback) {
        Log.d("KnoxStateMonitorImpl", "registerCallback() callback=" + knoxStateMonitorCallback);
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            if (((WeakReference) this.mCallbacks.get(i)).get() == knoxStateMonitorCallback) {
                Log.d("KnoxStateMonitorImpl", "removeCallback() mCallbacks has same callback");
                return;
            }
        }
        this.mCallbacks.add(new WeakReference(knoxStateMonitorCallback));
        removeCallback(null);
    }

    public final synchronized void removeCallback(KnoxStateMonitorCallback knoxStateMonitorCallback) {
        Log.d("KnoxStateMonitorImpl", "removeCallback() callback=" + knoxStateMonitorCallback);
        for (int size = this.mCallbacks.size() + (-1); size >= 0; size--) {
            if (((WeakReference) this.mCallbacks.get(size)).get() == knoxStateMonitorCallback) {
                Log.d("KnoxStateMonitorImpl", "removeCallback() mCallbacks has same callback");
                this.mCallbacks.remove(size);
            }
        }
    }

    public final long setLockoutAttemptDeadline(int i, int i2) {
        DualDarMonitor dualDarMonitor = this.mDualDarMonitor;
        if (dualDarMonitor == null) {
            return -1L;
        }
        long lockoutAttemptDeadline = dualDarMonitor.mLockPatternUtils.setLockoutAttemptDeadline(i, i2);
        if (lockoutAttemptDeadline != dualDarMonitor.mLockoutAttemptDeadline || i2 != dualDarMonitor.mLockoutAttemptTimeout) {
            dualDarMonitor.mLockoutAttemptDeadline = lockoutAttemptDeadline;
            dualDarMonitor.mLockoutAttemptTimeout = i2;
        }
        return dualDarMonitor.mLockoutAttemptDeadline;
    }
}
