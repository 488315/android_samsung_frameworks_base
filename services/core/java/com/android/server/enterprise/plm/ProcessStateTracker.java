package com.android.server.enterprise.plm;

import android.content.Context;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0;
import com.android.server.enterprise.EnterpriseService;
import com.android.server.enterprise.license.EnterpriseLicenseService;
import com.android.server.enterprise.plm.SystemStateTracker.LicenseStateListener;
import com.android.server.enterprise.plm.SystemStateTracker.LockStateListener;
import com.android.server.enterprise.plm.common.HandlerObserver;
import com.android.server.enterprise.plm.common.PlmMessage;
import com.android.server.enterprise.plm.impl.BindServiceImpl;

import com.samsung.android.knox.analytics.activation.ActivationMonitor;
import com.samsung.android.knox.analytics.activation.DevicePolicyListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class ProcessStateTracker extends Handler implements IStateDelegate {
    public final Map mAdapters;
    public final Context mContext;
    public boolean mStarted;
    public final SystemStateTracker mSystemStateTracker;

    public ProcessStateTracker(Looper looper, Context context, List list) {
        super(looper);
        this.mAdapters = new HashMap();
        this.mSystemStateTracker = new SystemStateTracker(looper, context, list);
        this.mContext = context;
        Iterator it = ((ArrayList) list).iterator();
        while (it.hasNext()) {
            ProcessAdapter processAdapter = (ProcessAdapter) it.next();
            processAdapter.mStateDelegate = this;
            ((HashMap) this.mAdapters)
                    .put(
                            processAdapter.mKeepAliveImpl.mProcessContext.getPackageName(),
                            processAdapter);
        }
    }

    @Override // android.os.Handler
    public final void handleMessage(Message message) {
        GestureWakeup$$ExternalSyntheticOutline0.m(
                new StringBuilder("handleMessage : "), message.what, "ProcessStateTracker");
        try {
            switch (message.what) {
                case 1:
                    onTrackerStart(message);
                    break;
                case 2:
                    onTrackerStop();
                    break;
                case 3:
                    onLicenseUpdate(message);
                    break;
                case 4:
                    onPackageUpdate(message);
                    break;
                case 5:
                    if (!this.mStarted) {
                        break;
                    } else {
                        Log.d("ProcessStateTracker", "onUserUpdate");
                        PlmMessage plmMessage = (PlmMessage) message.obj;
                        if (plmMessage != null) {
                            Log.d("ProcessStateTracker", (String) plmMessage.obj1);
                            notifyUpdateToAdapters("");
                            break;
                        } else {
                            break;
                        }
                    }
                case 6:
                    if (!this.mStarted) {
                        break;
                    } else {
                        Log.d("ProcessStateTracker", "onBootUpdate");
                        PlmMessage plmMessage2 = (PlmMessage) message.obj;
                        if (plmMessage2 != null) {
                            Log.d("ProcessStateTracker", (String) plmMessage2.obj1);
                            notifyUpdateToAdapters("");
                            break;
                        } else {
                            break;
                        }
                    }
                case 7:
                    if (!this.mStarted) {
                        break;
                    } else {
                        Log.d("ProcessStateTracker", "onEdmUpdate");
                        PlmMessage plmMessage3 = (PlmMessage) message.obj;
                        if (plmMessage3 != null) {
                            Log.d("ProcessStateTracker", (String) plmMessage3.obj1);
                            notifyUpdateToAdapters("");
                            break;
                        } else {
                            break;
                        }
                    }
                case 8:
                    if (!this.mStarted) {
                        break;
                    } else {
                        Log.d("ProcessStateTracker", "onLockUpdate");
                        PlmMessage plmMessage4 = (PlmMessage) message.obj;
                        if (plmMessage4 != null) {
                            Log.d("ProcessStateTracker", (String) plmMessage4.obj1);
                            notifyUpdateToAdapters("");
                            break;
                        } else {
                            break;
                        }
                    }
                default:
                    Log.e("ProcessStateTracker", "Invalid message " + message.what);
                    break;
            }
        } catch (Throwable th) {
            Log.e("ProcessStateTracker", th.toString());
        }
    }

    public final boolean isKlmActivated() {
        SystemStateTracker systemStateTracker = this.mSystemStateTracker;
        systemStateTracker.getClass();
        try {
            r2 =
                    Settings.Secure.getInt(
                                    systemStateTracker.mContext.getContentResolver(),
                                    ActivationMonitor.SETTINGS_KEY_KLM_STATUS,
                                    0)
                            > 0;
            Log.d("SystemStateTracker", "isKlmActive : " + r2);
        } catch (Throwable th) {
            Log.e("SystemStateTracker", th.toString());
        }
        return r2;
    }

    public final void notifyUpdateToAdapters(String str) {
        DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                new StringBuilder("notify update for "),
                TextUtils.isEmpty(str) ? "all" : str,
                "ProcessStateTracker");
        Iterator it = ((HashMap) this.mAdapters).entrySet().iterator();
        while (it.hasNext()) {
            ProcessAdapter processAdapter = (ProcessAdapter) ((Map.Entry) it.next()).getValue();
            if (processAdapter != null) {
                boolean isEmpty = TextUtils.isEmpty(str);
                BindServiceImpl bindServiceImpl = processAdapter.mKeepAliveImpl;
                if (isEmpty
                        || TextUtils.equals(
                                bindServiceImpl.mProcessContext.getPackageName(), str)) {
                    Log.d(
                            "ProcessStateTracker",
                            "notify update to "
                                    .concat(bindServiceImpl.mProcessContext.getPackageName()));
                    if (!processAdapter.hasMessages(5)) {
                        processAdapter.sendEmptyMessage(5);
                    }
                }
            }
        }
    }

    public final void onLicenseUpdate(Message message) {
        if (this.mStarted) {
            Log.d("ProcessStateTracker", "onLicenseUpdate");
            PlmMessage plmMessage = (PlmMessage) message.obj;
            if (plmMessage == null) {
                return;
            }
            String str = (String) plmMessage.obj1;
            boolean booleanValue = ((Boolean) plmMessage.obj2).booleanValue();
            StringBuilder sb = new StringBuilder("license ");
            sb.append(booleanValue ? "activated" : "deactivated");
            sb.append(" on ");
            sb.append(str);
            Log.i("ProcessStateTracker", sb.toString());
            notifyUpdateToAdapters("");
        }
    }

    public final void onPackageUpdate(Message message) {
        int i;
        if (this.mStarted) {
            Log.d("ProcessStateTracker", "onPackageUpdate");
            PlmMessage plmMessage = (PlmMessage) message.obj;
            if (plmMessage == null) {
                return;
            }
            String str = (String) plmMessage.obj1;
            String str2 = (String) plmMessage.obj2;
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(" ");
            sb.append(str2);
            sb.append(":");
            try {
                i = this.mContext.getPackageManager().getApplicationEnabledSetting(str);
            } catch (Throwable th) {
                Log.e("Utils", th.toString());
                i = 0;
            }
            sb.append(i);
            Log.d("ProcessStateTracker", sb.toString());
            notifyUpdateToAdapters(str);
        }
    }

    public final void onTrackerStart(Message message) {
        PlmMessage plmMessage = (PlmMessage) message.obj;
        if (plmMessage == null) {
            return;
        }
        StartReason startReason = (StartReason) plmMessage.obj1;
        Log.d("ProcessStateTracker", "onTrackerStart : " + startReason);
        boolean z = this.mStarted;
        SystemStateTracker systemStateTracker = this.mSystemStateTracker;
        if (!z) {
            systemStateTracker.stopLicenseStateListener();
            EnterpriseLicenseService enterpriseLicenseService =
                    (EnterpriseLicenseService)
                            EnterpriseService.getPolicyService("enterprise_license_policy");
            if (enterpriseLicenseService != null) {
                SystemStateTracker.LicenseStateListener licenseStateListener =
                        systemStateTracker.new LicenseStateListener();
                systemStateTracker.mLicenseStateListener = licenseStateListener;
                enterpriseLicenseService.enforcePermission$1();
                ((ArrayList) enterpriseLicenseService.mKlmElmChangeList).add(licenseStateListener);
            }
            SystemStateTracker.BootStateListener bootStateListener =
                    systemStateTracker.mPackageStateListener;
            if (bootStateListener != null) {
                systemStateTracker.mContext.unregisterReceiver(bootStateListener);
                systemStateTracker.mPackageStateListener = null;
            }
            ((ArrayList) systemStateTracker.mPackageStateObservers).clear();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
            intentFilter.addDataScheme("package");
            Iterator it = ((ArrayList) systemStateTracker.mTargetPackageNames).iterator();
            while (it.hasNext()) {
                intentFilter.addDataSchemeSpecificPart((String) it.next(), 0);
            }
            if (intentFilter.countActions() != 0) {
                SystemStateTracker.BootStateListener bootStateListener2 =
                        new SystemStateTracker.BootStateListener(systemStateTracker, 1);
                systemStateTracker.mPackageStateListener = bootStateListener2;
                systemStateTracker.mContext.semRegisterReceiverAsUser(
                        bootStateListener2, UserHandle.SEM_ALL, intentFilter, null, null);
            }
            SystemStateTracker.BootStateListener bootStateListener3 =
                    systemStateTracker.mUserStateListener;
            if (bootStateListener3 != null) {
                systemStateTracker.mContext.unregisterReceiver(bootStateListener3);
                systemStateTracker.mUserStateListener = null;
            }
            ((ArrayList) systemStateTracker.mUserStateObservers).clear();
            IntentFilter intentFilter2 = new IntentFilter();
            intentFilter2.addAction(DevicePolicyListener.ACTION_DEVICE_OWNER_CHANGED);
            intentFilter2.addAction(DevicePolicyListener.ACTION_PROFILE_OWNER_ADDED);
            intentFilter2.addAction(DevicePolicyListener.ACTION_PROFILE_OWNER_REMOVED);
            intentFilter2.addAction("android.intent.action.USER_UNLOCKED");
            if (intentFilter2.countActions() != 0) {
                SystemStateTracker.BootStateListener bootStateListener4 =
                        new SystemStateTracker.BootStateListener(systemStateTracker, 2);
                systemStateTracker.mUserStateListener = bootStateListener4;
                systemStateTracker.mContext.registerReceiver(bootStateListener4, intentFilter2, 2);
            }
            SystemStateTracker.BootStateListener bootStateListener5 =
                    systemStateTracker.mBootStateListener;
            if (bootStateListener5 != null) {
                systemStateTracker.mContext.unregisterReceiver(bootStateListener5);
                systemStateTracker.mBootStateListener = null;
            }
            ((ArrayList) systemStateTracker.mBootStateObservers).clear();
            IntentFilter intentFilter3 = new IntentFilter();
            if (intentFilter3.countActions() != 0) {
                SystemStateTracker.BootStateListener bootStateListener6 =
                        new SystemStateTracker.BootStateListener(systemStateTracker, 0);
                systemStateTracker.mBootStateListener = bootStateListener6;
                systemStateTracker.mContext.registerReceiver(bootStateListener6, intentFilter3);
            }
            systemStateTracker.stopLockStateListener();
            SystemStateTracker.LockStateListener lockStateListener =
                    systemStateTracker.new LockStateListener();
            systemStateTracker.mLockStateListener = lockStateListener;
            ((ArrayList) systemStateTracker.mLockDetectionTracker.mLockDetectionEventCallbacks)
                    .add(lockStateListener);
            ((ArrayList) systemStateTracker.mLicenseStateObservers)
                    .add(new HandlerObserver(3, this));
            ((ArrayList) systemStateTracker.mPackageStateObservers)
                    .add(new HandlerObserver(4, this));
            ((ArrayList) systemStateTracker.mUserStateObservers).add(new HandlerObserver(5, this));
            ((ArrayList) systemStateTracker.mBootStateObservers).add(new HandlerObserver(6, this));
            ((ArrayList) systemStateTracker.mEdmStateObservers).add(new HandlerObserver(7, this));
            ((ArrayList) systemStateTracker.mLockStateObservers).add(new HandlerObserver(8, this));
            Iterator it2 = ((HashMap) this.mAdapters).entrySet().iterator();
            while (it2.hasNext()) {
                ProcessAdapter processAdapter =
                        (ProcessAdapter) ((Map.Entry) it2.next()).getValue();
                if (processAdapter != null) {
                    processAdapter.sendEmptyMessage(1);
                }
            }
            Log.i("ProcessStateTracker", "start : true");
            this.mStarted = true;
        }
        if (startReason == StartReason.EDM_SERVICE_READY) {
            systemStateTracker.getClass();
            systemStateTracker.sendMessage(PlmMessage.obtain(systemStateTracker, 5, Boolean.TRUE));
        }
    }

    public final void onTrackerStop() {
        Log.d("ProcessStateTracker", "onTrackerStop");
        if (this.mStarted) {
            removeCallbacksAndMessages(null);
            Iterator it = ((HashMap) this.mAdapters).entrySet().iterator();
            while (it.hasNext()) {
                ProcessAdapter processAdapter = (ProcessAdapter) ((Map.Entry) it.next()).getValue();
                if (processAdapter != null) {
                    processAdapter.sendEmptyMessage(2);
                }
            }
            SystemStateTracker systemStateTracker = this.mSystemStateTracker;
            Iterator it2 = ((ArrayList) systemStateTracker.mLockStateObservers).iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                } else if (((HandlerObserver) it2.next()).getHandler() == this) {
                    it2.remove();
                    break;
                }
            }
            Iterator it3 = ((ArrayList) systemStateTracker.mEdmStateObservers).iterator();
            while (true) {
                if (!it3.hasNext()) {
                    break;
                } else if (((HandlerObserver) it3.next()).getHandler() == this) {
                    it3.remove();
                    break;
                }
            }
            Iterator it4 = ((ArrayList) systemStateTracker.mBootStateObservers).iterator();
            while (true) {
                if (!it4.hasNext()) {
                    break;
                } else if (((HandlerObserver) it4.next()).getHandler() == this) {
                    it4.remove();
                    break;
                }
            }
            Iterator it5 = ((ArrayList) systemStateTracker.mUserStateObservers).iterator();
            while (true) {
                if (!it5.hasNext()) {
                    break;
                } else if (((HandlerObserver) it5.next()).getHandler() == this) {
                    it5.remove();
                    break;
                }
            }
            Iterator it6 = ((ArrayList) systemStateTracker.mPackageStateObservers).iterator();
            while (true) {
                if (!it6.hasNext()) {
                    break;
                } else if (((HandlerObserver) it6.next()).getHandler() == this) {
                    it6.remove();
                    break;
                }
            }
            Iterator it7 = ((ArrayList) systemStateTracker.mLicenseStateObservers).iterator();
            while (true) {
                if (!it7.hasNext()) {
                    break;
                } else if (((HandlerObserver) it7.next()).getHandler() == this) {
                    it7.remove();
                    break;
                }
            }
            systemStateTracker.stopLockStateListener();
            SystemStateTracker.BootStateListener bootStateListener =
                    systemStateTracker.mBootStateListener;
            if (bootStateListener != null) {
                systemStateTracker.mContext.unregisterReceiver(bootStateListener);
                systemStateTracker.mBootStateListener = null;
            }
            ((ArrayList) systemStateTracker.mBootStateObservers).clear();
            SystemStateTracker.BootStateListener bootStateListener2 =
                    systemStateTracker.mUserStateListener;
            if (bootStateListener2 != null) {
                systemStateTracker.mContext.unregisterReceiver(bootStateListener2);
                systemStateTracker.mUserStateListener = null;
            }
            ((ArrayList) systemStateTracker.mUserStateObservers).clear();
            SystemStateTracker.BootStateListener bootStateListener3 =
                    systemStateTracker.mPackageStateListener;
            if (bootStateListener3 != null) {
                systemStateTracker.mContext.unregisterReceiver(bootStateListener3);
                systemStateTracker.mPackageStateListener = null;
            }
            ((ArrayList) systemStateTracker.mPackageStateObservers).clear();
            systemStateTracker.stopLicenseStateListener();
            Log.i("ProcessStateTracker", "start : false");
            this.mStarted = false;
        }
    }
}
