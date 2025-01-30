package com.android.server.enterprise.plm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.util.Log;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.enterprise.EnterpriseService;
import com.android.server.enterprise.license.EnterpriseLicenseService;
import com.android.server.enterprise.license.IActivationKlmElmObserver;
import com.android.server.enterprise.plm.LockDetectionTracker;
import com.android.server.enterprise.plm.common.HandlerObserver;
import com.android.server.enterprise.plm.common.PlmMessage;
import com.samsung.android.knox.analytics.activation.ActivationMonitor;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.license.LicenseResult;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class SystemStateTracker extends Handler {
    public static final String TAG = SystemStateTracker.class.getSimpleName();
    public BroadcastReceiver mBootStateListener;
    public final List mBootStateObservers;
    public final Context mContext;
    public final List mEbpfStateObservers;
    public boolean mEdmServiceReady;
    public final List mEdmStateObservers;
    public LicenseStateListener mLicenseStateListener;
    public final List mLicenseStateObservers;
    public LockDetectionTracker mLockDetectionTracker;
    public LockStateListener mLockStateListener;
    public final List mLockStateObservers;
    public BroadcastReceiver mPackageStateListener;
    public final List mPackageStateObservers;
    public final List mTargetPackageNames;
    public BroadcastReceiver mUserStateListener;
    public final List mUserStateObservers;

    enum EbpfType {
        CPU_UID_SYSTEM_TIME,
        CPU_UID_ACTIVE_TIME
    }

    public final String getFactorTypeToString(int i) {
        return i != -1 ? i != 6 ? i != 1 ? i != 2 ? i != 3 ? i != 4 ? "" : "PASSWORD" : "PIN" : "PASSWORD_OR_PIN" : "PATTERN" : "SMARTCARDNUMERIC" : "NONE";
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
        if (list != null) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                this.mTargetPackageNames.add(((ProcessAdapter) it.next()).getPackageName());
            }
        }
        this.mLicenseStateListener = null;
        this.mPackageStateListener = null;
        this.mUserStateListener = null;
        this.mBootStateListener = null;
        this.mEdmServiceReady = false;
        this.mLockDetectionTracker = LockDetectionTracker.getInstance(this.mContext);
        this.mLockStateListener = null;
    }

    public void start() {
        startLicenseStateListener();
        startPackageStateListener();
        startUserStateListener();
        startBootStateListener();
        startLockStateListener();
    }

    public void stop() {
        stopLockStateListener();
        stopBootStateListener();
        stopUserStateListener();
        stopPackageStateListener();
        stopLicenseStateListener();
    }

    public void notifyEvent(InternalEvent internalEvent) {
        if (internalEvent == InternalEvent.EDM_SERVICE_READY) {
            sendMessage(PlmMessage.obtain(this, 5, Boolean.TRUE, null));
        }
    }

    public void registerLicenseStateObserver(Handler handler, int i) {
        this.mLicenseStateObservers.add(new HandlerObserver(handler, i));
    }

    public void unregisterLicenseStateObserver(Handler handler) {
        Iterator it = this.mLicenseStateObservers.iterator();
        while (it.hasNext()) {
            if (((HandlerObserver) it.next()).getHandler() == handler) {
                it.remove();
                return;
            }
        }
    }

    public void registerPackageObserver(Handler handler, int i) {
        this.mPackageStateObservers.add(new HandlerObserver(handler, i));
    }

    public void unregisterPackageObserver(Handler handler) {
        Iterator it = this.mPackageStateObservers.iterator();
        while (it.hasNext()) {
            if (((HandlerObserver) it.next()).getHandler() == handler) {
                it.remove();
                return;
            }
        }
    }

    public void registerUserStateObserver(Handler handler, int i) {
        this.mUserStateObservers.add(new HandlerObserver(handler, i));
    }

    public void unregisterUserStateObserver(Handler handler) {
        Iterator it = this.mUserStateObservers.iterator();
        while (it.hasNext()) {
            if (((HandlerObserver) it.next()).getHandler() == handler) {
                it.remove();
                return;
            }
        }
    }

    public void registerBootStateObserver(Handler handler, int i) {
        this.mBootStateObservers.add(new HandlerObserver(handler, i));
    }

    public void unregisterBootStateObserver(Handler handler) {
        Iterator it = this.mBootStateObservers.iterator();
        while (it.hasNext()) {
            if (((HandlerObserver) it.next()).getHandler() == handler) {
                it.remove();
                return;
            }
        }
    }

    public void registerEdmStateObserver(Handler handler, int i) {
        this.mEdmStateObservers.add(new HandlerObserver(handler, i));
    }

    public void unregisterEdmStateObserver(Handler handler) {
        Iterator it = this.mEdmStateObservers.iterator();
        while (it.hasNext()) {
            if (((HandlerObserver) it.next()).getHandler() == handler) {
                it.remove();
                return;
            }
        }
    }

    public void registerLockStateObserver(Handler handler, int i) {
        this.mLockStateObservers.add(new HandlerObserver(handler, i));
    }

    public void unregisterLockStateObserver(Handler handler) {
        Iterator it = this.mLockStateObservers.iterator();
        while (it.hasNext()) {
            if (((HandlerObserver) it.next()).getHandler() == handler) {
                it.remove();
                return;
            }
        }
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        String str = TAG;
        Log.d(str, "handleMessage : " + message.what);
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
                    Log.e(str, "Invalid message " + message.what);
                    break;
            }
        } catch (Throwable th) {
            Log.e(TAG, th.toString());
        }
    }

    public final void onLicenseStateChange(Message message) {
        PlmMessage plmMessage = (PlmMessage) message.obj;
        String str = (String) plmMessage.obj1;
        boolean booleanValue = ((Boolean) plmMessage.obj2).booleanValue();
        Log.d(TAG, "onLicenseStateChange(" + str + ", " + booleanValue + ")");
        Iterator it = this.mLicenseStateObservers.iterator();
        while (it.hasNext()) {
            ((HandlerObserver) it.next()).notifyMessage(str, Boolean.valueOf(booleanValue));
        }
    }

    public final void onPackageStateChange(Message message) {
        PlmMessage plmMessage = (PlmMessage) message.obj;
        String str = (String) plmMessage.obj1;
        String str2 = (String) plmMessage.obj2;
        Log.d(TAG, "onPackageStateChange(" + str + ", " + str2 + ")");
        Iterator it = this.mPackageStateObservers.iterator();
        while (it.hasNext()) {
            ((HandlerObserver) it.next()).notifyMessage(str, str2);
        }
    }

    public final void onUserStateChange(Message message) {
        String str = (String) ((PlmMessage) message.obj).obj1;
        Log.d(TAG, "onUserStateChange(" + str + ")");
        Iterator it = this.mUserStateObservers.iterator();
        while (it.hasNext()) {
            ((HandlerObserver) it.next()).notifyMessage(str);
        }
    }

    public final void onBootStateChange(Message message) {
        String str = (String) ((PlmMessage) message.obj).obj1;
        Log.d(TAG, "onBootStateChange(" + str + ")");
        Iterator it = this.mBootStateObservers.iterator();
        while (it.hasNext()) {
            ((HandlerObserver) it.next()).notifyMessage(str);
        }
    }

    public final void onEdmStateChange(Message message) {
        boolean booleanValue = ((Boolean) ((PlmMessage) message.obj).obj1).booleanValue();
        Log.d(TAG, "onEdmStateChange(" + booleanValue + ")");
        setEdmServiceReady(booleanValue);
        Iterator it = this.mEdmStateObservers.iterator();
        while (it.hasNext()) {
            ((HandlerObserver) it.next()).notifyMessage(booleanValue ? "ready" : "not ready");
        }
    }

    public final void onEbpfStateChange(Message message) {
        PlmMessage plmMessage = (PlmMessage) message.obj;
        EbpfType ebpfType = (EbpfType) plmMessage.obj1;
        int i = AbstractC14541.f1696x2806582e[ebpfType.ordinal()];
        if (i != 1) {
            if (i != 2) {
                return;
            }
            int intValue = ((Integer) plmMessage.obj2).intValue();
            long longValue = ((Long) plmMessage.obj3).longValue();
            Log.d(TAG, "onEbpfStateChange(" + ebpfType + ", " + intValue + ", " + longValue + ")");
            Iterator it = this.mEbpfStateObservers.iterator();
            while (it.hasNext()) {
                ((HandlerObserver) it.next()).notifyMessage(ebpfType, Integer.valueOf(intValue), Long.valueOf(longValue));
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
        int intValue2 = ((Integer) plmMessage.obj2).intValue();
        long longValue2 = ((Long) plmMessage.obj3).longValue();
        long longValue3 = ((Long) plmMessage.obj4).longValue();
        Log.d(TAG, "onEbpfStateChange(" + ebpfType + ", " + intValue2 + ", " + longValue2 + ", " + longValue3 + ")");
        Iterator it2 = this.mEbpfStateObservers.iterator();
        while (it2.hasNext()) {
            ((HandlerObserver) it2.next()).notifyMessage(ebpfType, Integer.valueOf(intValue2), Long.valueOf(longValue2), Long.valueOf(longValue3));
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

    /* renamed from: com.android.server.enterprise.plm.SystemStateTracker$1 */
    public abstract /* synthetic */ class AbstractC14541 {

        /* renamed from: $SwitchMap$com$android$server$enterprise$plm$SystemStateTracker$EbpfType */
        public static final /* synthetic */ int[] f1696x2806582e;

        static {
            int[] iArr = new int[EbpfType.values().length];
            f1696x2806582e = iArr;
            try {
                iArr[EbpfType.CPU_UID_SYSTEM_TIME.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f1696x2806582e[EbpfType.CPU_UID_ACTIVE_TIME.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public final void onLockStateChange(Message message) {
        PlmMessage plmMessage = (PlmMessage) message.obj;
        String str = (String) plmMessage.obj1;
        boolean booleanValue = ((Boolean) plmMessage.obj2).booleanValue();
        int intValue = ((Integer) plmMessage.obj3).intValue();
        String factorTypeToString = getFactorTypeToString(intValue);
        String str2 = (String) plmMessage.obj4;
        Log.d(TAG, "onLockStateChange(" + booleanValue + ", " + intValue + str2 + ")");
        Iterator it = this.mLockStateObservers.iterator();
        while (it.hasNext()) {
            ((HandlerObserver) it.next()).notifyMessage(Boolean.valueOf(booleanValue), Integer.valueOf(intValue), str2);
        }
        Intent intent = new Intent();
        intent.setAction(str);
        intent.setPackage("com.samsung.android.cmfa.framework");
        intent.putExtra(KnoxCustomManagerService.SPCM_KEY_RESULT, booleanValue);
        intent.putExtra("factorType", factorTypeToString);
        intent.putExtra("localTime", str2);
        this.mContext.sendBroadcast(intent);
    }

    public final void setEdmServiceReady(boolean z) {
        Log.i(TAG, "edm service ready : " + z);
        this.mEdmServiceReady = z;
    }

    public boolean isKlmActive() {
        try {
            r0 = Settings.Secure.getInt(this.mContext.getContentResolver(), ActivationMonitor.SETTINGS_KEY_KLM_STATUS, 0) > 0;
            Log.d(TAG, "isKlmActive : " + r0);
        } catch (Throwable th) {
            Log.e(TAG, th.toString());
        }
        return r0;
    }

    public boolean isEdmServiceReady() {
        boolean z = this.mEdmServiceReady;
        Log.d(TAG, "isEdmServiceReady : " + z);
        return z;
    }

    public boolean isUserUnlocked() {
        UserManager userManager = (UserManager) this.mContext.getSystemService("user");
        boolean z = userManager != null && userManager.isUserUnlocked();
        Log.d(TAG, "isUserUnlocked : " + z);
        return z;
    }

    public final EnterpriseLicenseService getLicenseService() {
        return (EnterpriseLicenseService) EnterpriseService.getPolicyService("enterprise_license_policy");
    }

    public final void startLicenseStateListener() {
        stopLicenseStateListener();
        EnterpriseLicenseService licenseService = getLicenseService();
        if (licenseService != null) {
            LicenseStateListener licenseStateListener = new LicenseStateListener();
            this.mLicenseStateListener = licenseStateListener;
            licenseService.addElmKlmObserver(licenseStateListener);
        }
    }

    public final void stopLicenseStateListener() {
        LicenseStateListener licenseStateListener;
        EnterpriseLicenseService licenseService = getLicenseService();
        if (licenseService != null && (licenseStateListener = this.mLicenseStateListener) != null) {
            licenseService.removeElmKlmObserver(licenseStateListener);
            this.mLicenseStateListener = null;
        }
        this.mLicenseStateObservers.clear();
    }

    public final IntentFilter providePackageIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter.addDataScheme("package");
        Iterator it = this.mTargetPackageNames.iterator();
        while (it.hasNext()) {
            intentFilter.addDataSchemeSpecificPart((String) it.next(), 0);
        }
        return intentFilter;
    }

    public final IntentFilter provideUserIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.USER_UNLOCKED");
        return intentFilter;
    }

    public final IntentFilter provideBootIntentFilter() {
        return new IntentFilter();
    }

    public final void startPackageStateListener() {
        stopPackageStateListener();
        IntentFilter providePackageIntentFilter = providePackageIntentFilter();
        if (providePackageIntentFilter.countActions() == 0) {
            return;
        }
        PackageStateListener packageStateListener = new PackageStateListener();
        this.mPackageStateListener = packageStateListener;
        this.mContext.semRegisterReceiverAsUser(packageStateListener, UserHandle.SEM_ALL, providePackageIntentFilter, null, null);
    }

    public final void stopPackageStateListener() {
        BroadcastReceiver broadcastReceiver = this.mPackageStateListener;
        if (broadcastReceiver != null) {
            this.mContext.unregisterReceiver(broadcastReceiver);
            this.mPackageStateListener = null;
        }
        this.mPackageStateObservers.clear();
    }

    public final void startUserStateListener() {
        stopUserStateListener();
        IntentFilter provideUserIntentFilter = provideUserIntentFilter();
        if (provideUserIntentFilter.countActions() == 0) {
            return;
        }
        UserStateListener userStateListener = new UserStateListener();
        this.mUserStateListener = userStateListener;
        this.mContext.registerReceiver(userStateListener, provideUserIntentFilter);
    }

    public final void stopUserStateListener() {
        BroadcastReceiver broadcastReceiver = this.mUserStateListener;
        if (broadcastReceiver != null) {
            this.mContext.unregisterReceiver(broadcastReceiver);
            this.mUserStateListener = null;
        }
        this.mUserStateObservers.clear();
    }

    public final void startBootStateListener() {
        stopBootStateListener();
        IntentFilter provideBootIntentFilter = provideBootIntentFilter();
        if (provideBootIntentFilter.countActions() == 0) {
            return;
        }
        BootStateListener bootStateListener = new BootStateListener();
        this.mBootStateListener = bootStateListener;
        this.mContext.registerReceiver(bootStateListener, provideBootIntentFilter);
    }

    public final void stopBootStateListener() {
        BroadcastReceiver broadcastReceiver = this.mBootStateListener;
        if (broadcastReceiver != null) {
            this.mContext.unregisterReceiver(broadcastReceiver);
            this.mBootStateListener = null;
        }
        this.mBootStateObservers.clear();
    }

    public final void startLockStateListener() {
        stopLockStateListener();
        LockStateListener lockStateListener = new LockStateListener();
        this.mLockStateListener = lockStateListener;
        this.mLockDetectionTracker.registerLockDetectionEventCallback(lockStateListener);
    }

    public final void stopLockStateListener() {
        LockStateListener lockStateListener = this.mLockStateListener;
        if (lockStateListener != null) {
            this.mLockDetectionTracker.unregisterLockDetectionEventCallback(lockStateListener);
            this.mLockStateListener = null;
        }
        this.mLockStateObservers.clear();
    }

    public class LicenseStateListener implements IActivationKlmElmObserver {
        @Override // com.android.server.enterprise.license.IActivationKlmElmObserver
        public void onUpdateContainerLicenseStatus(String str) {
        }

        @Override // com.android.server.enterprise.license.IActivationKlmElmObserver
        public void onUpdateElm(String str, LicenseResult licenseResult) {
        }

        public LicenseStateListener() {
        }

        @Override // com.android.server.enterprise.license.IActivationKlmElmObserver
        public void onUpdateKlm(String str, LicenseResult licenseResult) {
            SystemStateTracker systemStateTracker = SystemStateTracker.this;
            systemStateTracker.sendMessage(PlmMessage.obtain(systemStateTracker, 1, str, Boolean.valueOf(licenseResult.isActivation()), null));
        }
    }

    public class PackageStateListener extends BroadcastReceiver {
        public PackageStateListener() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
            Log.d(SystemStateTracker.TAG, action + XmlUtils.STRING_ARRAY_SEPARATOR + intExtra);
            if (intent.getData() == null) {
                return;
            }
            SystemStateTracker systemStateTracker = SystemStateTracker.this;
            systemStateTracker.sendMessage(PlmMessage.obtain(systemStateTracker, 2, intent.getData().getSchemeSpecificPart(), intent.getAction(), null));
        }
    }

    public class UserStateListener extends BroadcastReceiver {
        public UserStateListener() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Log.d(SystemStateTracker.TAG, intent.getAction());
            SystemStateTracker systemStateTracker = SystemStateTracker.this;
            systemStateTracker.sendMessage(PlmMessage.obtain(systemStateTracker, 3, intent.getAction(), null));
        }
    }

    public class BootStateListener extends BroadcastReceiver {
        public BootStateListener() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Log.d(SystemStateTracker.TAG, intent.getAction());
            SystemStateTracker systemStateTracker = SystemStateTracker.this;
            systemStateTracker.sendMessage(PlmMessage.obtain(systemStateTracker, 4, intent.getAction(), null));
        }
    }

    public class LockStateListener implements LockDetectionTracker.LockDetectionEventCallback {
        public LockStateListener() {
        }

        @Override // com.android.server.enterprise.plm.LockDetectionTracker.LockDetectionEventCallback
        public void onLockStateChange(String str, boolean z, int i, String str2) {
            SystemStateTracker systemStateTracker = SystemStateTracker.this;
            systemStateTracker.sendMessage(PlmMessage.obtain(systemStateTracker, 7, str, Boolean.valueOf(z), Integer.valueOf(i), str2, null));
        }
    }
}
