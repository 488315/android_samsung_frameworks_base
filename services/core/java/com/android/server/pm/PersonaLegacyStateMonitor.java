package com.android.server.pm;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.UserInfo;
import android.os.Bundle;
import android.os.ContainerStateReceiver;
import android.os.RemoteCallbackList;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;

import com.android.server.UiModeManagerService$13$$ExternalSyntheticOutline0;
import com.android.server.enterprise.storage.EdmStorageProvider;

import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.SemPersonaState;
import com.samsung.android.knox.custom.KnoxCustomManagerService;

import java.util.HashMap;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PersonaLegacyStateMonitor {
    public final Context mContext;
    public final EnterpriseDeviceManager mEdm;
    public final EdmStorageProvider mEdmStorageProvider;
    public final KeyguardManager mKeyguardManager;
    public final UserManager mUserManager;
    public final HashMap mStateMap = new HashMap();
    public final AnonymousClass1 receiver =
            new ContainerStateReceiver() { // from class:
                                           // com.android.server.pm.PersonaLegacyStateMonitor.1
                public final void onContainerCreated(Context context, int i, Bundle bundle) {
                    PersonaLegacyStateMonitor.m761$$Nest$mnotifyStateChange(
                            PersonaLegacyStateMonitor.this, i, SemPersonaState.CREATING);
                }

                public final void onContainerLocked(Context context, int i, Bundle bundle) {
                    PersonaLegacyStateMonitor.m761$$Nest$mnotifyStateChange(
                            PersonaLegacyStateMonitor.this, i, SemPersonaState.LOCKED);
                }

                public final void onContainerRemoved(Context context, int i, Bundle bundle) {
                    PersonaLegacyStateMonitor.m761$$Nest$mnotifyStateChange(
                            PersonaLegacyStateMonitor.this, i, SemPersonaState.DELETING);
                    PersonaLegacyStateMonitor.m761$$Nest$mnotifyStateChange(
                            PersonaLegacyStateMonitor.this, i, SemPersonaState.INVALID);
                    int mUMContainerOwnerUid =
                            PersonaLegacyStateMonitor.this.mEdmStorageProvider
                                    .getMUMContainerOwnerUid(i);
                    int userId = UserHandle.getUserId(mUMContainerOwnerUid);
                    String[] packagesForUid =
                            PersonaLegacyStateMonitor.this
                                    .mContext
                                    .getPackageManager()
                                    .getPackagesForUid(mUMContainerOwnerUid);
                    if (packagesForUid == null) {
                        PersonaLegacyStateMonitor.m762$$Nest$msendIntentForRemoveContainer(
                                PersonaLegacyStateMonitor.this, null, i, userId);
                        UiModeManagerService$13$$ExternalSyntheticOutline0.m(
                                new StringBuilder(
                                        "Sending container removed intent to MDM for user "),
                                i,
                                "PersonaManagerService::LegacyStateMonitor");
                        return;
                    }
                    for (String str : packagesForUid) {
                        PersonaLegacyStateMonitor.m762$$Nest$msendIntentForRemoveContainer(
                                PersonaLegacyStateMonitor.this, str, i, userId);
                        Log.i(
                                "PersonaManagerService::LegacyStateMonitor",
                                "Sending container removed intent to MDM for user "
                                        + i
                                        + " Package is "
                                        + str);
                    }
                }

                public final void onContainerRunning(Context context, int i, Bundle bundle) {
                    UserInfo userInfo = PersonaLegacyStateMonitor.this.mUserManager.getUserInfo(i);
                    if (userInfo == null) {
                        PersonaLegacyStateMonitor.m761$$Nest$mnotifyStateChange(
                                PersonaLegacyStateMonitor.this, i, SemPersonaState.INVALID);
                        return;
                    }
                    if ((userInfo.getAttributes() & 8) > 0) {
                        PersonaLegacyStateMonitor.m761$$Nest$mnotifyStateChange(
                                PersonaLegacyStateMonitor.this,
                                userInfo.id,
                                SemPersonaState.ADMIN_LOCKED);
                        return;
                    }
                    if ((userInfo.getAttributes() & 16) > 0) {
                        PersonaLegacyStateMonitor.m761$$Nest$mnotifyStateChange(
                                PersonaLegacyStateMonitor.this,
                                userInfo.id,
                                SemPersonaState.LICENSE_LOCKED);
                    } else if ((userInfo.getAttributes() & 4) > 0) {
                        PersonaLegacyStateMonitor.m761$$Nest$mnotifyStateChange(
                                PersonaLegacyStateMonitor.this,
                                userInfo.id,
                                SemPersonaState.TIMA_COMPROMISED);
                    } else {
                        PersonaLegacyStateMonitor.m761$$Nest$mnotifyStateChange(
                                PersonaLegacyStateMonitor.this, i, SemPersonaState.LOCKED);
                    }
                }

                public final void onContainerShutdown(Context context, int i, Bundle bundle) {
                    UserInfo userInfo = PersonaLegacyStateMonitor.this.mUserManager.getUserInfo(i);
                    if (userInfo == null) {
                        PersonaLegacyStateMonitor.m761$$Nest$mnotifyStateChange(
                                PersonaLegacyStateMonitor.this, i, SemPersonaState.INVALID);
                        return;
                    }
                    if ((userInfo.getAttributes() & 8) > 0) {
                        PersonaLegacyStateMonitor.m761$$Nest$mnotifyStateChange(
                                PersonaLegacyStateMonitor.this,
                                userInfo.id,
                                SemPersonaState.ADMIN_LOCKED);
                    } else if ((userInfo.getAttributes() & 16) > 0) {
                        PersonaLegacyStateMonitor.m761$$Nest$mnotifyStateChange(
                                PersonaLegacyStateMonitor.this,
                                userInfo.id,
                                SemPersonaState.LICENSE_LOCKED);
                    } else if ((userInfo.getAttributes() & 4) > 0) {
                        PersonaLegacyStateMonitor.m761$$Nest$mnotifyStateChange(
                                PersonaLegacyStateMonitor.this,
                                userInfo.id,
                                SemPersonaState.TIMA_COMPROMISED);
                    }
                }

                public final void onContainerUnlocked(Context context, int i, Bundle bundle) {
                    PersonaLegacyStateMonitor.m761$$Nest$mnotifyStateChange(
                            PersonaLegacyStateMonitor.this, i, SemPersonaState.ACTIVE);
                }
            };
    public final RemoteCallbackList mObserverList = new RemoteCallbackList();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.pm.PersonaLegacyStateMonitor$2, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass2 {
        public static final /* synthetic */ int[]
                $SwitchMap$com$samsung$android$knox$SemPersonaState;

        static {
            int[] iArr = new int[SemPersonaState.values().length];
            $SwitchMap$com$samsung$android$knox$SemPersonaState = iArr;
            try {
                iArr[SemPersonaState.INVALID.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$samsung$android$knox$SemPersonaState[
                                SemPersonaState.TIMA_COMPROMISED.ordinal()] =
                        2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$samsung$android$knox$SemPersonaState[
                                SemPersonaState.ADMIN_LOCKED.ordinal()] =
                        3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$samsung$android$knox$SemPersonaState[
                                SemPersonaState.LICENSE_LOCKED.ordinal()] =
                        4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$samsung$android$knox$SemPersonaState[
                                SemPersonaState.ADMIN_LICENSE_LOCKED.ordinal()] =
                        5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$samsung$android$knox$SemPersonaState[
                                SemPersonaState.ACTIVE.ordinal()] =
                        6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$samsung$android$knox$SemPersonaState[
                                SemPersonaState.LOCKED.ordinal()] =
                        7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$samsung$android$knox$SemPersonaState[
                                SemPersonaState.CREATING.ordinal()] =
                        8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$samsung$android$knox$SemPersonaState[
                                SemPersonaState.DELETING.ordinal()] =
                        9;
            } catch (NoSuchFieldError unused9) {
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:41:0x0159  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x017d  */
    /* renamed from: -$$Nest$mnotifyStateChange, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void m761$$Nest$mnotifyStateChange(
            com.android.server.pm.PersonaLegacyStateMonitor r22,
            int r23,
            com.samsung.android.knox.SemPersonaState r24) {
        /*
            Method dump skipped, instructions count: 436
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.PersonaLegacyStateMonitor.m761$$Nest$mnotifyStateChange(com.android.server.pm.PersonaLegacyStateMonitor,"
                    + " int, com.samsung.android.knox.SemPersonaState):void");
    }

    /* renamed from: -$$Nest$msendIntentForRemoveContainer, reason: not valid java name */
    public static void m762$$Nest$msendIntentForRemoveContainer(
            PersonaLegacyStateMonitor personaLegacyStateMonitor, String str, int i, int i2) {
        personaLegacyStateMonitor.getClass();
        Intent intent = new Intent("com.samsung.android.knox.intent.action.CONTAINER_REMOVED");
        if (str != null && !str.isEmpty()) {
            intent.setPackage(str);
        }
        Bundle bundle = new Bundle();
        bundle.putInt("containerid", i);
        intent.putExtra(KnoxCustomManagerService.INTENT, bundle);
        if (str != null) {
            personaLegacyStateMonitor.mContext.sendBroadcastAsUser(
                    intent,
                    new UserHandle(UserHandle.getUserId(i2)),
                    "com.samsung.android.knox.permission.KNOX_CONTAINER");
        } else {
            personaLegacyStateMonitor.mContext.sendBroadcastAsUser(
                    intent, UserHandle.ALL, "com.samsung.android.knox.permission.KNOX_CONTAINER");
        }
        if (str != null) {
            String kPUPackageName = personaLegacyStateMonitor.mEdm.getKPUPackageName();
            Intent intent2 = new Intent(intent);
            intent2.setPackage(kPUPackageName);
            personaLegacyStateMonitor.mContext.sendBroadcastAsUser(
                    intent2,
                    new UserHandle(UserHandle.getUserId(i2)),
                    "com.samsung.android.knox.permission.KNOX_CONTAINER");
        }
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.server.pm.PersonaLegacyStateMonitor$1] */
    public PersonaLegacyStateMonitor(Context context) {
        this.mEdmStorageProvider = null;
        this.mEdm = null;
        this.mContext = context;
        this.mUserManager = (UserManager) context.getSystemService("user");
        this.mKeyguardManager = (KeyguardManager) context.getSystemService("keyguard");
        this.mEdmStorageProvider = new EdmStorageProvider(context);
        this.mEdm = EnterpriseDeviceManager.getInstance(context);
        Log.d("PersonaManagerService::LegacyStateMonitor", "initialized");
        for (UserInfo userInfo : ((UserManager) context.getSystemService("user")).getUsers()) {
            if (userInfo.id != 0 && userInfo.isManagedProfile()) {
                if ((userInfo.getAttributes() & 8) > 0) {
                    this.mStateMap.put(Integer.valueOf(userInfo.id), SemPersonaState.ADMIN_LOCKED);
                } else if ((userInfo.getAttributes() & 16) > 0) {
                    this.mStateMap.put(
                            Integer.valueOf(userInfo.id), SemPersonaState.LICENSE_LOCKED);
                } else if ((userInfo.getAttributes() & 4) > 0) {
                    this.mStateMap.put(
                            Integer.valueOf(userInfo.id), SemPersonaState.TIMA_COMPROMISED);
                } else if (!this.mKeyguardManager.isDeviceSecure(userInfo.id)) {
                    this.mStateMap.put(Integer.valueOf(userInfo.id), SemPersonaState.LOCKED);
                } else if (this.mKeyguardManager.isDeviceLocked(userInfo.id)) {
                    this.mStateMap.put(Integer.valueOf(userInfo.id), SemPersonaState.ACTIVE);
                }
            }
        }
        ContainerStateReceiver.register(this.mContext, this.receiver);
    }

    public final void sendContainerStateChangeIntent(int i, int i2, int i3, int i4, String str) {
        Intent intent =
                new Intent("com.samsung.android.knox.intent.action.CONTAINER_STATE_CHANGED");
        if (str != null && !str.isEmpty()) {
            intent.setPackage(str);
        }
        Bundle bundle = new Bundle();
        bundle.putInt("containerid", i);
        bundle.putInt("container_old_state", i3);
        bundle.putInt("container_new_state", i4);
        intent.putExtra(KnoxCustomManagerService.INTENT, bundle);
        this.mContext.sendBroadcastAsUser(
                intent, new UserHandle(i2), "com.samsung.android.knox.permission.KNOX_CONTAINER");
        if (str == null || str.isEmpty()) {
            return;
        }
        String kPUPackageName = this.mEdm.getKPUPackageName();
        Intent intent2 = new Intent(intent);
        intent2.setPackage(kPUPackageName);
        this.mContext.sendBroadcastAsUser(
                intent2, new UserHandle(i2), "com.samsung.android.knox.permission.KNOX_CONTAINER");
    }

    public final void sendIntentForAdminLock(int i, int i2, String str) {
        Intent intent = new Intent("com.samsung.android.knox.intent.action.CONTAINER_ADMIN_LOCK");
        if (str != null && !str.isEmpty()) {
            intent.setPackage(str);
        }
        Bundle bundle = new Bundle();
        bundle.putInt("containerid", i);
        intent.putExtra(KnoxCustomManagerService.INTENT, bundle);
        if (str != null) {
            this.mContext.sendBroadcastAsUser(
                    intent,
                    new UserHandle(UserHandle.getUserId(i2)),
                    "com.samsung.android.knox.permission.KNOX_CONTAINER");
        } else {
            this.mContext.sendBroadcastAsUser(
                    intent, UserHandle.ALL, "com.samsung.android.knox.permission.KNOX_CONTAINER");
        }
        if (str != null) {
            String kPUPackageName = this.mEdm.getKPUPackageName();
            Intent intent2 = new Intent(intent);
            intent2.setPackage(kPUPackageName);
            this.mContext.sendBroadcastAsUser(
                    intent2,
                    new UserHandle(UserHandle.getUserId(i2)),
                    "com.samsung.android.knox.permission.KNOX_CONTAINER");
        }
    }
}
