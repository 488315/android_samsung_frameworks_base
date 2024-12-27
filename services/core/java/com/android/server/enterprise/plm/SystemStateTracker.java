package com.android.server.enterprise.plm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.VpnManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.FlashNotificationsController$$ExternalSyntheticOutline0;
import com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0;
import com.android.server.enterprise.EnterpriseService;
import com.android.server.enterprise.license.EnterpriseLicenseService;
import com.android.server.enterprise.license.IActivationKlmElmObserver;
import com.android.server.enterprise.plm.common.HandlerObserver;
import com.android.server.enterprise.plm.common.PlmMessage;

import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.license.LicenseResult;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class SystemStateTracker extends Handler {
    public static final /* synthetic */ int $r8$clinit = 0;
    public BootStateListener mBootStateListener;
    public final List mBootStateObservers;
    public final Context mContext;
    public final List mEbpfStateObservers;
    public boolean mEdmServiceReady;
    public final List mEdmStateObservers;
    public LicenseStateListener mLicenseStateListener;
    public final List mLicenseStateObservers;
    public final LockDetectionTracker mLockDetectionTracker;
    public LockStateListener mLockStateListener;
    public final List mLockStateObservers;
    public BootStateListener mPackageStateListener;
    public final List mPackageStateObservers;
    public final List mTargetPackageNames;
    public BootStateListener mUserStateListener;
    public final List mUserStateObservers;

    public final class BootStateListener extends BroadcastReceiver {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ SystemStateTracker this$0;

        public /* synthetic */ BootStateListener(SystemStateTracker systemStateTracker, int i) {
            this.$r8$classId = i;
            this.this$0 = systemStateTracker;
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            switch (this.$r8$classId) {
                case 0:
                    String action = intent.getAction();
                    int i = SystemStateTracker.$r8$clinit;
                    Log.d("SystemStateTracker", action);
                    SystemStateTracker systemStateTracker = this.this$0;
                    systemStateTracker.sendMessage(
                            PlmMessage.obtain(systemStateTracker, 4, intent.getAction()));
                    break;
                case 1:
                    String action2 = intent.getAction();
                    int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
                    int i2 = SystemStateTracker.$r8$clinit;
                    Log.d("SystemStateTracker", action2 + ":" + intExtra);
                    if (intent.getData() != null) {
                        SystemStateTracker systemStateTracker2 = this.this$0;
                        systemStateTracker2.sendMessage(
                                PlmMessage.obtain(
                                        systemStateTracker2,
                                        2,
                                        intent.getData().getSchemeSpecificPart(),
                                        intent.getAction()));
                        break;
                    }
                    break;
                default:
                    String action3 = intent.getAction();
                    int i3 = SystemStateTracker.$r8$clinit;
                    Log.d("SystemStateTracker", action3);
                    SystemStateTracker systemStateTracker3 = this.this$0;
                    systemStateTracker3.sendMessage(
                            PlmMessage.obtain(systemStateTracker3, 3, intent.getAction()));
                    break;
            }
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    final class EbpfType {
        public static final /* synthetic */ EbpfType[] $VALUES = {
            new EbpfType("CPU_UID_SYSTEM_TIME", 0), new EbpfType("CPU_UID_ACTIVE_TIME", 1)
        };

        /* JADX INFO: Fake field, exist only in values array */
        EbpfType EF5;

        public static EbpfType valueOf(String str) {
            return (EbpfType) Enum.valueOf(EbpfType.class, str);
        }

        public static EbpfType[] values() {
            return (EbpfType[]) $VALUES.clone();
        }
    }

    public final class LicenseStateListener implements IActivationKlmElmObserver {
        public LicenseStateListener() {}

        @Override // com.android.server.enterprise.license.IActivationKlmElmObserver
        public final void onUpdateContainerLicenseStatus(String str) {}

        @Override // com.android.server.enterprise.license.IActivationKlmElmObserver
        public final void onUpdateElm(String str, LicenseResult licenseResult) {}

        @Override // com.android.server.enterprise.license.IActivationKlmElmObserver
        public final void onUpdateKlm(String str, LicenseResult licenseResult) {
            Boolean valueOf = Boolean.valueOf(licenseResult.isActivation());
            SystemStateTracker systemStateTracker = SystemStateTracker.this;
            systemStateTracker.sendMessage(PlmMessage.obtain(systemStateTracker, 1, str, valueOf));
        }
    }

    public final class LockStateListener {
        public LockStateListener() {}
    }

    public SystemStateTracker(Looper looper, Context context, List list) {
        super(looper);
        this.mContext = context;
        this.mLicenseStateObservers = new ArrayList();
        this.mPackageStateObservers = new ArrayList();
        this.mUserStateObservers = new ArrayList();
        this.mBootStateObservers = new ArrayList();
        this.mEdmStateObservers = new ArrayList();
        this.mEbpfStateObservers = new ArrayList();
        this.mLockStateObservers = new ArrayList();
        this.mTargetPackageNames = new ArrayList();
        Iterator it = ((ArrayList) list).iterator();
        while (it.hasNext()) {
            ProcessAdapter processAdapter = (ProcessAdapter) it.next();
            ((ArrayList) this.mTargetPackageNames)
                    .add(processAdapter.mKeepAliveImpl.mProcessContext.getPackageName());
        }
        this.mLicenseStateListener = null;
        this.mPackageStateListener = null;
        this.mUserStateListener = null;
        this.mBootStateListener = null;
        this.mEdmServiceReady = false;
        synchronized (LockDetectionTracker.class) {
            try {
                if (LockDetectionTracker.sInstance == null) {
                    LockDetectionTracker.sInstance = new LockDetectionTracker();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        this.mLockDetectionTracker = LockDetectionTracker.sInstance;
        this.mLockStateListener = null;
    }

    @Override // android.os.Handler
    public final void handleMessage(Message message) {
        GestureWakeup$$ExternalSyntheticOutline0.m(
                new StringBuilder("handleMessage : "), message.what, "SystemStateTracker");
        try {
            switch (message.what) {
                case 1:
                    onLicenseStateChange(message);
                    break;
                case 2:
                    onPackageStateChange(message);
                    break;
                case 3:
                    onUserStateChange(message);
                    break;
                case 4:
                    onBootStateChange(message);
                    break;
                case 5:
                    onEdmStateChange(message);
                    break;
                case 6:
                    onEbpfStateChange(message);
                    break;
                case 7:
                    onLockStateChange(message);
                    break;
                default:
                    Log.e("SystemStateTracker", "Invalid message " + message.what);
                    break;
            }
        } catch (Throwable th) {
            Log.e("SystemStateTracker", th.toString());
        }
    }

    public final void onBootStateChange(Message message) {
        String str = (String) ((PlmMessage) message.obj).obj1;
        DualAppManagerService$$ExternalSyntheticOutline0.m(
                "onBootStateChange(", str, ")", "SystemStateTracker");
        Iterator it = ((ArrayList) this.mBootStateObservers).iterator();
        while (it.hasNext()) {
            HandlerObserver handlerObserver = (HandlerObserver) it.next();
            Handler handler = handlerObserver.getHandler();
            if (handler != null) {
                handler.sendMessage(PlmMessage.obtain(handler, handlerObserver.what, str));
            }
        }
    }

    public final void onEbpfStateChange(Message message) {
        PlmMessage plmMessage = (PlmMessage) message.obj;
        EbpfType ebpfType = (EbpfType) plmMessage.obj1;
        int ordinal = ebpfType.ordinal();
        if (ordinal != 0) {
            if (ordinal != 1) {
                return;
            }
            Integer num = (Integer) plmMessage.obj2;
            int intValue = num.intValue();
            Long l = (Long) plmMessage.obj3;
            long longValue = l.longValue();
            Log.d(
                    "SystemStateTracker",
                    "onEbpfStateChange(" + ebpfType + ", " + intValue + ", " + longValue + ")");
            Iterator it = ((ArrayList) this.mEbpfStateObservers).iterator();
            while (it.hasNext()) {
                ((HandlerObserver) it.next()).notifyMessage(ebpfType, num, l);
            }
            Intent intent = new Intent();
            intent.setAction("com.samsung.android.knox.intent.action.EBPF_STATE_CHANGE");
            intent.setPackage("com.samsung.android.cmfa.framework");
            intent.putExtra("type", ebpfType.name());
            intent.putExtra("uid", intValue);
            intent.putExtra("activeTimeMs", longValue);
            this.mContext.sendBroadcast(intent);
            return;
        }
        Integer num2 = (Integer) plmMessage.obj2;
        int intValue2 = num2.intValue();
        Long l2 = (Long) plmMessage.obj3;
        long longValue2 = l2.longValue();
        Long l3 = (Long) plmMessage.obj4;
        long longValue3 = l3.longValue();
        Log.d(
                "SystemStateTracker",
                "onEbpfStateChange("
                        + ebpfType
                        + ", "
                        + intValue2
                        + ", "
                        + longValue2
                        + ", "
                        + longValue3
                        + ")");
        Iterator it2 = ((ArrayList) this.mEbpfStateObservers).iterator();
        while (it2.hasNext()) {
            HandlerObserver handlerObserver = (HandlerObserver) it2.next();
            Handler handler = handlerObserver.getHandler();
            if (handler != null) {
                PlmMessage plmMessage2 = new PlmMessage();
                plmMessage2.obj1 = ebpfType;
                plmMessage2.obj2 = num2;
                plmMessage2.obj3 = l2;
                plmMessage2.obj4 = l3;
                Message obtain = Message.obtain(handler);
                obtain.what = handlerObserver.what;
                obtain.obj = plmMessage2;
                handler.sendMessage(obtain);
            }
        }
        Intent intent2 = new Intent();
        intent2.setAction("com.samsung.android.knox.intent.action.EBPF_STATE_CHANGE");
        intent2.setPackage("com.samsung.android.cmfa.framework");
        intent2.putExtra("type", ebpfType.name());
        intent2.putExtra("uid", intValue2);
        intent2.putExtra("userTimeUs", longValue2);
        intent2.putExtra("systemTimeUs", longValue3);
        this.mContext.sendBroadcast(intent2);
    }

    public final void onEdmStateChange(Message message) {
        boolean booleanValue = ((Boolean) ((PlmMessage) message.obj).obj1).booleanValue();
        Log.d("SystemStateTracker", "onEdmStateChange(" + booleanValue + ")");
        FlashNotificationsController$$ExternalSyntheticOutline0.m(
                "SystemStateTracker", new StringBuilder("edm service ready : "), booleanValue);
        this.mEdmServiceReady = booleanValue;
        Iterator it = ((ArrayList) this.mEdmStateObservers).iterator();
        while (it.hasNext()) {
            HandlerObserver handlerObserver = (HandlerObserver) it.next();
            String str = booleanValue ? "ready" : "not ready";
            Handler handler = handlerObserver.getHandler();
            if (handler != null) {
                handler.sendMessage(PlmMessage.obtain(handler, handlerObserver.what, str));
            }
        }
    }

    public final void onLicenseStateChange(Message message) {
        PlmMessage plmMessage = (PlmMessage) message.obj;
        String str = (String) plmMessage.obj1;
        Boolean bool = (Boolean) plmMessage.obj2;
        Log.d(
                "SystemStateTracker",
                "onLicenseStateChange(" + str + ", " + bool.booleanValue() + ")");
        Iterator it = ((ArrayList) this.mLicenseStateObservers).iterator();
        while (it.hasNext()) {
            HandlerObserver handlerObserver = (HandlerObserver) it.next();
            Handler handler = handlerObserver.getHandler();
            if (handler != null) {
                handler.sendMessage(PlmMessage.obtain(handler, handlerObserver.what, str, bool));
            }
        }
    }

    public final void onLockStateChange(Message message) {
        PlmMessage plmMessage = (PlmMessage) message.obj;
        String str = (String) plmMessage.obj1;
        Boolean bool = (Boolean) plmMessage.obj2;
        boolean booleanValue = bool.booleanValue();
        Integer num = (Integer) plmMessage.obj3;
        int intValue = num.intValue();
        String str2 =
                intValue != -1
                        ? intValue != 6
                                ? intValue != 1
                                        ? intValue != 2
                                                ? intValue != 3
                                                        ? intValue != 4 ? "" : "PASSWORD"
                                                        : "PIN"
                                                : "PASSWORD_OR_PIN"
                                        : "PATTERN"
                                : "SMARTCARDNUMERIC"
                        : "NONE";
        String str3 = (String) plmMessage.obj4;
        VpnManagerService$$ExternalSyntheticOutline0.m(
                AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                        intValue, "onLockStateChange(", ", ", str3, booleanValue),
                ")",
                "SystemStateTracker");
        Iterator it = ((ArrayList) this.mLockStateObservers).iterator();
        while (it.hasNext()) {
            ((HandlerObserver) it.next()).notifyMessage(bool, num, str3);
        }
        Intent intent = new Intent();
        intent.setAction(str);
        intent.setPackage("com.samsung.android.cmfa.framework");
        intent.putExtra(KnoxCustomManagerService.SPCM_KEY_RESULT, booleanValue);
        intent.putExtra("factorType", str2);
        intent.putExtra("localTime", str3);
        this.mContext.sendBroadcast(intent);
    }

    public final void onPackageStateChange(Message message) {
        PlmMessage plmMessage = (PlmMessage) message.obj;
        String str = (String) plmMessage.obj1;
        String str2 = (String) plmMessage.obj2;
        Log.d(
                "SystemStateTracker",
                XmlUtils$$ExternalSyntheticOutline0.m(
                        "onPackageStateChange(", str, ", ", str2, ")"));
        Iterator it = ((ArrayList) this.mPackageStateObservers).iterator();
        while (it.hasNext()) {
            HandlerObserver handlerObserver = (HandlerObserver) it.next();
            Handler handler = handlerObserver.getHandler();
            if (handler != null) {
                handler.sendMessage(PlmMessage.obtain(handler, handlerObserver.what, str, str2));
            }
        }
    }

    public final void onUserStateChange(Message message) {
        String str = (String) ((PlmMessage) message.obj).obj1;
        DualAppManagerService$$ExternalSyntheticOutline0.m(
                "onUserStateChange(", str, ")", "SystemStateTracker");
        Iterator it = ((ArrayList) this.mUserStateObservers).iterator();
        while (it.hasNext()) {
            HandlerObserver handlerObserver = (HandlerObserver) it.next();
            Handler handler = handlerObserver.getHandler();
            if (handler != null) {
                handler.sendMessage(PlmMessage.obtain(handler, handlerObserver.what, str));
            }
        }
    }

    public final void stopLicenseStateListener() {
        LicenseStateListener licenseStateListener;
        EnterpriseLicenseService enterpriseLicenseService =
                (EnterpriseLicenseService)
                        EnterpriseService.getPolicyService("enterprise_license_policy");
        if (enterpriseLicenseService != null
                && (licenseStateListener = this.mLicenseStateListener) != null) {
            enterpriseLicenseService.enforcePermission$1();
            ((ArrayList) enterpriseLicenseService.mKlmElmChangeList).remove(licenseStateListener);
            this.mLicenseStateListener = null;
        }
        ((ArrayList) this.mLicenseStateObservers).clear();
    }

    public final void stopLockStateListener() {
        LockStateListener lockStateListener = this.mLockStateListener;
        if (lockStateListener != null) {
            Iterator it =
                    ((ArrayList) this.mLockDetectionTracker.mLockDetectionEventCallbacks)
                            .iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                } else if (((LockStateListener) it.next()) == lockStateListener) {
                    it.remove();
                    break;
                }
            }
            this.mLockStateListener = null;
        }
        ((ArrayList) this.mLockStateObservers).clear();
    }
}
