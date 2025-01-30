package com.android.server.pm;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ISystemPersonaObserver;
import android.content.pm.UserInfo;
import android.os.Bundle;
import android.os.ContainerStateReceiver;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.SemPersonaState;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import java.util.HashMap;

/* loaded from: classes3.dex */
public class PersonaLegacyStateMonitor {
    public Context mContext;
    public EnterpriseDeviceManager mEdm;
    public EdmStorageProvider mEdmStorageProvider;
    public KeyguardManager mKeyguardManager;
    public UserManager mUserManager;
    public HashMap mStateMap = new HashMap();
    public ContainerStateReceiver receiver = new ContainerStateReceiver() { // from class: com.android.server.pm.PersonaLegacyStateMonitor.1
        public void onContainerCreated(Context context, int i, Bundle bundle) {
            PersonaLegacyStateMonitor.this.notifyStateChange(i, SemPersonaState.CREATING);
        }

        public void onContainerRunning(Context context, int i, Bundle bundle) {
            UserInfo userInfo = PersonaLegacyStateMonitor.this.mUserManager.getUserInfo(i);
            if (userInfo == null) {
                PersonaLegacyStateMonitor.this.notifyStateChange(i, SemPersonaState.INVALID);
                return;
            }
            if ((userInfo.getAttributes() & 8) > 0) {
                PersonaLegacyStateMonitor.this.notifyStateChange(userInfo.id, SemPersonaState.ADMIN_LOCKED);
                return;
            }
            if ((userInfo.getAttributes() & 16) > 0) {
                PersonaLegacyStateMonitor.this.notifyStateChange(userInfo.id, SemPersonaState.LICENSE_LOCKED);
            } else if ((userInfo.getAttributes() & 4) > 0) {
                PersonaLegacyStateMonitor.this.notifyStateChange(userInfo.id, SemPersonaState.TIMA_COMPROMISED);
            } else {
                PersonaLegacyStateMonitor.this.notifyStateChange(i, SemPersonaState.LOCKED);
            }
        }

        public void onContainerShutdown(Context context, int i, Bundle bundle) {
            UserInfo userInfo = PersonaLegacyStateMonitor.this.mUserManager.getUserInfo(i);
            if (userInfo == null) {
                PersonaLegacyStateMonitor.this.notifyStateChange(i, SemPersonaState.INVALID);
                return;
            }
            if ((userInfo.getAttributes() & 8) > 0) {
                PersonaLegacyStateMonitor.this.notifyStateChange(userInfo.id, SemPersonaState.ADMIN_LOCKED);
            } else if ((userInfo.getAttributes() & 16) > 0) {
                PersonaLegacyStateMonitor.this.notifyStateChange(userInfo.id, SemPersonaState.LICENSE_LOCKED);
            } else if ((userInfo.getAttributes() & 4) > 0) {
                PersonaLegacyStateMonitor.this.notifyStateChange(userInfo.id, SemPersonaState.TIMA_COMPROMISED);
            }
        }

        public void onContainerLocked(Context context, int i, Bundle bundle) {
            PersonaLegacyStateMonitor.this.notifyStateChange(i, SemPersonaState.LOCKED);
        }

        public void onContainerUnlocked(Context context, int i, Bundle bundle) {
            PersonaLegacyStateMonitor.this.notifyStateChange(i, SemPersonaState.ACTIVE);
        }

        public void onContainerRemoved(Context context, int i, Bundle bundle) {
            PersonaLegacyStateMonitor.this.notifyStateChange(i, SemPersonaState.DELETING);
            PersonaLegacyStateMonitor.this.notifyStateChange(i, SemPersonaState.INVALID);
            int mUMContainerOwnerUid = PersonaLegacyStateMonitor.this.mEdmStorageProvider.getMUMContainerOwnerUid(i);
            int userId = UserHandle.getUserId(mUMContainerOwnerUid);
            String[] packagesForUid = PersonaLegacyStateMonitor.this.mContext.getPackageManager().getPackagesForUid(mUMContainerOwnerUid);
            if (packagesForUid != null) {
                for (String str : packagesForUid) {
                    PersonaLegacyStateMonitor.this.sendIntentForRemoveContainer(str, i, userId);
                    Log.i("PersonaManagerService::LegacyStateMonitor", "Sending container removed intent to MDM for user " + i + " Package is " + str);
                }
                return;
            }
            PersonaLegacyStateMonitor.this.sendIntentForRemoveContainer(null, i, userId);
            Log.i("PersonaManagerService::LegacyStateMonitor", "Sending container removed intent to MDM for user " + i);
        }
    };
    public final RemoteCallbackList mObserverList = new RemoteCallbackList();

    public PersonaLegacyStateMonitor(Context context) {
        this.mEdmStorageProvider = null;
        this.mEdm = null;
        this.mContext = context;
        this.mUserManager = (UserManager) context.getSystemService("user");
        this.mKeyguardManager = (KeyguardManager) this.mContext.getSystemService("keyguard");
        this.mEdmStorageProvider = new EdmStorageProvider(this.mContext);
        this.mEdm = EnterpriseDeviceManager.getInstance(this.mContext);
        init();
    }

    public void init() {
        Log.d("PersonaManagerService::LegacyStateMonitor", "initialized");
        for (UserInfo userInfo : ((UserManager) this.mContext.getSystemService("user")).getUsers()) {
            if (userInfo.id != 0 && userInfo.isManagedProfile()) {
                if ((userInfo.getAttributes() & 8) > 0) {
                    this.mStateMap.put(Integer.valueOf(userInfo.id), SemPersonaState.ADMIN_LOCKED);
                } else if ((userInfo.getAttributes() & 16) > 0) {
                    this.mStateMap.put(Integer.valueOf(userInfo.id), SemPersonaState.LICENSE_LOCKED);
                } else if ((userInfo.getAttributes() & 4) > 0) {
                    this.mStateMap.put(Integer.valueOf(userInfo.id), SemPersonaState.TIMA_COMPROMISED);
                } else if (!this.mKeyguardManager.isDeviceSecure(userInfo.id)) {
                    this.mStateMap.put(Integer.valueOf(userInfo.id), SemPersonaState.LOCKED);
                } else if (this.mKeyguardManager.isDeviceLocked(userInfo.id)) {
                    this.mStateMap.put(Integer.valueOf(userInfo.id), SemPersonaState.ACTIVE);
                }
            }
        }
        ContainerStateReceiver.register(this.mContext, this.receiver);
    }

    public boolean register(ISystemPersonaObserver iSystemPersonaObserver) {
        RemoteCallbackList remoteCallbackList = this.mObserverList;
        if (remoteCallbackList != null) {
            return remoteCallbackList.register(iSystemPersonaObserver);
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x0146  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x016d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void notifyStateChange(int i, SemPersonaState semPersonaState) {
        int i2;
        SemPersonaState semPersonaState2;
        String[] packagesForUid;
        SemPersonaState semPersonaState3 = (SemPersonaState) this.mStateMap.get(Integer.valueOf(i));
        if (semPersonaState3 == null) {
            semPersonaState3 = SemPersonaState.INVALID;
        }
        SemPersonaState semPersonaState4 = semPersonaState3;
        if (semPersonaState == semPersonaState4) {
            return;
        }
        int beginBroadcast = this.mObserverList.beginBroadcast();
        Log.d("PersonaManagerService::LegacyStateMonitor", "notifyStateChange(id:" + i + ", state:" + semPersonaState + ") from old state:" + semPersonaState4 + " i:" + beginBroadcast);
        while (beginBroadcast > 0) {
            int i3 = beginBroadcast - 1;
            try {
                this.mObserverList.getBroadcastItem(i3).onStateChange(i, semPersonaState4, semPersonaState);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            beginBroadcast = i3;
        }
        this.mObserverList.finishBroadcast();
        Intent intent = new Intent("com.sec.knox.container.action.observer");
        intent.addCategory("com.sec.knox.container.category.observer.containerid" + i);
        intent.addCategory("com.sec.knox.container.category.observer.onstatechange");
        intent.putExtra("com.sec.knox.container.extra.observer.newstate", semPersonaState.toString());
        intent.putExtra("com.sec.knox.container.extra.observer.previousstate", semPersonaState4.toString());
        this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL, "com.samsung.container.OBSERVER");
        Log.d("PersonaManagerService::LegacyStateMonitor", "propagateNewStateChange() sendBroadcast()");
        int mUMContainerOwnerUid = this.mEdmStorageProvider.getMUMContainerOwnerUid(i);
        int userId = UserHandle.getUserId(mUMContainerOwnerUid);
        int translateStatus = translateStatus(semPersonaState4);
        int translateStatus2 = translateStatus(semPersonaState);
        if (translateStatus != translateStatus2) {
            String[] packagesForUid2 = this.mContext.getPackageManager().getPackagesForUid(mUMContainerOwnerUid);
            String str = "Sending admin lock intent to MDM for user ";
            if (packagesForUid2 != null) {
                int length = packagesForUid2.length;
                int i4 = 0;
                while (i4 < length) {
                    String str2 = packagesForUid2[i4];
                    int i5 = mUMContainerOwnerUid;
                    String str3 = str;
                    sendContainerStateChangeIntent(str2, i, userId, translateStatus, translateStatus2);
                    Log.i("PersonaManagerService::LegacyStateMonitor", str3 + i + " Package is " + str2);
                    i4++;
                    str = str3;
                    length = length;
                    mUMContainerOwnerUid = i5;
                    packagesForUid2 = packagesForUid2;
                }
            } else {
                i2 = mUMContainerOwnerUid;
                sendContainerStateChangeIntent(null, i, userId, translateStatus, translateStatus2);
                Log.i("PersonaManagerService::LegacyStateMonitor", "Sending admin lock intent to MDM for user " + i);
                semPersonaState2 = SemPersonaState.ADMIN_LOCKED;
                if (semPersonaState == semPersonaState2 && semPersonaState4 != semPersonaState2) {
                    packagesForUid = this.mContext.getPackageManager().getPackagesForUid(i2);
                    if (packagesForUid == null) {
                        for (String str4 : packagesForUid) {
                            sendIntentForAdminLock(str4, i, userId);
                            Log.i("PersonaManagerService::LegacyStateMonitor", "Sending admin lock change intent to MDM for user " + i + " Package is " + str4);
                        }
                    } else {
                        sendIntentForAdminLock(null, i, userId);
                    }
                }
                this.mStateMap.put(Integer.valueOf(i), semPersonaState);
            }
        }
        i2 = mUMContainerOwnerUid;
        semPersonaState2 = SemPersonaState.ADMIN_LOCKED;
        if (semPersonaState == semPersonaState2) {
            packagesForUid = this.mContext.getPackageManager().getPackagesForUid(i2);
            if (packagesForUid == null) {
            }
        }
        this.mStateMap.put(Integer.valueOf(i), semPersonaState);
    }

    /* renamed from: com.android.server.pm.PersonaLegacyStateMonitor$2 */
    public abstract /* synthetic */ class AbstractC21642 {
        public static final /* synthetic */ int[] $SwitchMap$com$samsung$android$knox$SemPersonaState;

        static {
            int[] iArr = new int[SemPersonaState.values().length];
            $SwitchMap$com$samsung$android$knox$SemPersonaState = iArr;
            try {
                iArr[SemPersonaState.INVALID.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$samsung$android$knox$SemPersonaState[SemPersonaState.TIMA_COMPROMISED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$samsung$android$knox$SemPersonaState[SemPersonaState.ADMIN_LOCKED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$samsung$android$knox$SemPersonaState[SemPersonaState.LICENSE_LOCKED.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$samsung$android$knox$SemPersonaState[SemPersonaState.ADMIN_LICENSE_LOCKED.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$samsung$android$knox$SemPersonaState[SemPersonaState.ACTIVE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$samsung$android$knox$SemPersonaState[SemPersonaState.LOCKED.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$samsung$android$knox$SemPersonaState[SemPersonaState.CREATING.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$samsung$android$knox$SemPersonaState[SemPersonaState.DELETING.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
        }
    }

    public static int translateStatus(SemPersonaState semPersonaState) {
        if (semPersonaState == null) {
            return -1;
        }
        switch (AbstractC21642.$SwitchMap$com$samsung$android$knox$SemPersonaState[semPersonaState.ordinal()]) {
        }
        return -1;
    }

    public final void sendContainerStateChangeIntent(String str, int i, int i2, int i3, int i4) {
        Intent intent = new Intent("com.samsung.android.knox.intent.action.CONTAINER_STATE_CHANGED");
        if (str != null && !str.isEmpty()) {
            intent.setPackage(str);
        }
        Bundle bundle = new Bundle();
        bundle.putInt("containerid", i);
        bundle.putInt("container_old_state", i3);
        bundle.putInt("container_new_state", i4);
        intent.putExtra(KnoxCustomManagerService.INTENT, bundle);
        this.mContext.sendBroadcastAsUser(intent, new UserHandle(i2), "com.samsung.android.knox.permission.KNOX_CONTAINER");
        if (str == null || str.isEmpty()) {
            return;
        }
        String kPUPackageName = this.mEdm.getKPUPackageName();
        Intent intent2 = new Intent(intent);
        intent2.setPackage(kPUPackageName);
        this.mContext.sendBroadcastAsUser(intent2, new UserHandle(i2), "com.samsung.android.knox.permission.KNOX_CONTAINER");
    }

    public final void sendIntentForAdminLock(String str, int i, int i2) {
        Intent intent = new Intent("com.samsung.android.knox.intent.action.CONTAINER_ADMIN_LOCK");
        if (str != null && !str.isEmpty()) {
            intent.setPackage(str);
        }
        Bundle bundle = new Bundle();
        bundle.putInt("containerid", i);
        intent.putExtra(KnoxCustomManagerService.INTENT, bundle);
        if (str != null) {
            this.mContext.sendBroadcastAsUser(intent, new UserHandle(UserHandle.getUserId(i2)), "com.samsung.android.knox.permission.KNOX_CONTAINER");
        } else {
            this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL, "com.samsung.android.knox.permission.KNOX_CONTAINER");
        }
        if (str != null) {
            String kPUPackageName = this.mEdm.getKPUPackageName();
            Intent intent2 = new Intent(intent);
            intent2.setPackage(kPUPackageName);
            this.mContext.sendBroadcastAsUser(intent2, new UserHandle(UserHandle.getUserId(i2)), "com.samsung.android.knox.permission.KNOX_CONTAINER");
        }
    }

    public final void sendIntentForRemoveContainer(String str, int i, int i2) {
        Intent intent = new Intent("com.samsung.android.knox.intent.action.CONTAINER_REMOVED");
        if (str != null && !str.isEmpty()) {
            intent.setPackage(str);
        }
        Bundle bundle = new Bundle();
        bundle.putInt("containerid", i);
        intent.putExtra(KnoxCustomManagerService.INTENT, bundle);
        if (str != null) {
            this.mContext.sendBroadcastAsUser(intent, new UserHandle(UserHandle.getUserId(i2)), "com.samsung.android.knox.permission.KNOX_CONTAINER");
        } else {
            this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL, "com.samsung.android.knox.permission.KNOX_CONTAINER");
        }
        if (str != null) {
            String kPUPackageName = this.mEdm.getKPUPackageName();
            Intent intent2 = new Intent(intent);
            intent2.setPackage(kPUPackageName);
            this.mContext.sendBroadcastAsUser(intent2, new UserHandle(UserHandle.getUserId(i2)), "com.samsung.android.knox.permission.KNOX_CONTAINER");
        }
    }
}
