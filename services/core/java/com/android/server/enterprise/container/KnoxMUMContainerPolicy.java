package com.android.server.enterprise.container;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityManagerNative;
import android.app.AppGlobals;
import android.app.IProcessObserver;
import android.app.admin.DevicePolicyManager;
import android.app.admin.IDevicePolicyManager;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.IShortcutService;
import android.content.pm.ISystemPersonaObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.content.pm.UserInfo;
import android.database.ContentObserver;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.ContainerStateReceiver;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.sec.enterprise.auditlog.AuditLog;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;

import com.android.internal.infra.AndroidFuture;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.Preconditions;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.internal.widget.ILockSettings;
import com.android.internal.widget.LockPatternUtils;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.ExplicitHealthCheckController$$ExternalSyntheticOutline0;
import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.NetworkScoreService$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.RCPManagerService$$ExternalSyntheticOutline0;
import com.android.server.SEAMService;
import com.android.server.ServiceKeeper;
import com.android.server.VpnManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.am.ActivityManagerService;
import com.android.server.am.FreecessController$$ExternalSyntheticOutline0;
import com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticOutline0;
import com.android.server.audio.AudioServiceExt$ResetSettingsReceiver$$ExternalSyntheticOutline0;
import com.android.server.connectivity.EnterpriseVpn$$ExternalSyntheticOutline0;
import com.android.server.enterprise.EnterpriseDeviceManagerService;
import com.android.server.enterprise.EnterpriseService;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.adapterlayer.PackageManagerAdapter;
import com.android.server.enterprise.adapterlayer.SystemUIAdapter;
import com.android.server.enterprise.license.EnterpriseLicenseService;
import com.android.server.enterprise.license.IActivationKlmElmObserver;
import com.android.server.enterprise.security.PasswordPolicy;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.utils.KpuHelper;
import com.android.server.enterprise.utils.Utils;
import com.android.server.knox.dar.DarManagerService;
import com.android.server.knox.dar.ddar.DDLog;
import com.android.server.knox.dar.sdp.SDPLog;
import com.android.server.pm.PersonaManagerService;
import com.android.server.pm.PersonaServiceHelper;
import com.android.server.wm.ActivityTaskManagerService;
import com.android.server.wm.PersonaActivityHelper;

import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.knox.AppIdentity;
import com.samsung.android.knox.ContainerProxy;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.IEnterpriseContainerCallback;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.SemPersonaState;
import com.samsung.android.knox.application.IApplicationPolicy;
import com.samsung.android.knox.container.ContainerCreationParams;
import com.samsung.android.knox.container.ContainerModeConfigurationType;
import com.samsung.android.knox.container.CreationParams;
import com.samsung.android.knox.container.EnterpriseContainerObject;
import com.samsung.android.knox.container.IKnoxContainerManager;
import com.samsung.android.knox.container.KnoxConfigurationType;
import com.samsung.android.knox.container.KnoxContainerManager;
import com.samsung.android.knox.container.LightweightConfigurationType;
import com.samsung.android.knox.container.SecureFolderConfigurationType;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import com.samsung.android.knox.dar.ddar.DualDarManager;
import com.samsung.android.knox.license.LicenseResult;
import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class KnoxMUMContainerPolicy extends IKnoxContainerManager.Stub
        implements EnterpriseServiceCallback {
    public static final boolean DEBUG = "eng".equals(SystemProperties.get("ro.build.type"));
    public static final Uri REMOVE_SHORTCUT_CONTENT_URI;
    public static final boolean isEngMode;
    public static final boolean isUserMode;
    public static Context mContext;
    public static IEnterpriseContainerCallback mSetupCallback;
    public static final Object mSetupCallbackLock;
    public static final List mbadgePolicylist;
    public final List NFC_FILTERS;
    public final AnonymousClass4 contentObserver;
    public ActivityManager mActivityManager;
    public IApplicationPolicy mAppService;
    public final ContainerHandler mContainerHandler;
    public ProvisioningState mCurrentProvisioningState;
    public DarManagerService mDarManagerService;
    public DevicePolicyManager mDpm;
    public EnterpriseDeviceManager mEDM;
    public final EdmStorageProvider mEdmStorageProvider;
    public String mFirmwareVersion;
    public final Handler mHandler;
    public final Injector mInjector;
    public ILockSettings mLockSettingsService;
    public final PackageManager mPackageManager;
    public final List mParamsList;
    public SemPersonaManager mPersona;
    public final Object mProvisioningLock;
    public ProvisioningProcessObserver mProvisioningObserver;
    public final RequestIdGenerator mRIdGenerator;
    public final AnonymousClass1 mReceiver;
    public boolean mRestart;
    public SEAMService mSEAMS;
    public IShortcutService mShortcutService;
    public final List mTypeList;
    public UserManager mUms;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.enterprise.container.KnoxMUMContainerPolicy$6, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass6 {
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

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ContainerHandler extends Handler {
        public ContainerHandler() {}

        /* JADX WARN: Removed duplicated region for block: B:47:0x018a A[Catch: Exception -> 0x00e5, TryCatch #2 {Exception -> 0x00e5, blocks: (B:32:0x00c8, B:34:0x00dd, B:35:0x00fc, B:37:0x0120, B:40:0x0126, B:42:0x014a, B:44:0x014e, B:45:0x016d, B:47:0x018a, B:49:0x019c, B:52:0x01a3, B:55:0x01a8, B:56:0x01ab, B:57:0x01ac, B:59:0x01cc, B:62:0x0131, B:64:0x00e8, B:66:0x00f5, B:51:0x01a0), top: B:31:0x00c8, inners: #0, #1 }] */
        /* JADX WARN: Removed duplicated region for block: B:59:0x01cc A[Catch: Exception -> 0x00e5, TRY_LEAVE, TryCatch #2 {Exception -> 0x00e5, blocks: (B:32:0x00c8, B:34:0x00dd, B:35:0x00fc, B:37:0x0120, B:40:0x0126, B:42:0x014a, B:44:0x014e, B:45:0x016d, B:47:0x018a, B:49:0x019c, B:52:0x01a3, B:55:0x01a8, B:56:0x01ab, B:57:0x01ac, B:59:0x01cc, B:62:0x0131, B:64:0x00e8, B:66:0x00f5, B:51:0x01a0), top: B:31:0x00c8, inners: #0, #1 }] */
        @Override // android.os.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void handleMessage(android.os.Message r11) {
            /*
                Method dump skipped, instructions count: 470
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.enterprise.container.KnoxMUMContainerPolicy.ContainerHandler.handleMessage(android.os.Message):void");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ContainerLicenseObserver implements IActivationKlmElmObserver {
        public ContainerLicenseObserver() {}

        public static void notifyAppSeparationLicense(String str, LicenseResult licenseResult) {
            if (SemPersonaManager.isDoEnabled(0)) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    try {
                        Context context = KnoxMUMContainerPolicy.mContext;
                        Log.d("KnoxMUMContainerPolicy", "notifyAppSeparationLicense");
                        Intent intent =
                                new Intent(
                                        "com.samsung.android.knox.intent.action.APPSEPARATION_LICENSE_CHANGED");
                        intent.putExtra("packageName", str);
                        intent.putExtra("type", licenseResult.getType().name());
                        intent.putExtra("errorCode", licenseResult.getErrorCode());
                        intent.putExtra(
                                "grantedPermissions", licenseResult.getGrantedPermissions());
                        intent.putExtra("licenseKey", licenseResult.getLicenseKey());
                        intent.setPackage("com.samsung.android.appseparation");
                        KnoxMUMContainerPolicy.mContext.sendBroadcastAsUser(
                                intent, UserHandle.SEM_OWNER);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        @Override // com.android.server.enterprise.license.IActivationKlmElmObserver
        public final void onUpdateContainerLicenseStatus(String str) {
            Context context = KnoxMUMContainerPolicy.mContext;
            Log.d("KnoxMUMContainerPolicy", "onUpdateContainerLicenseStatus");
            KnoxMUMContainerPolicy knoxMUMContainerPolicy = KnoxMUMContainerPolicy.this;
            knoxMUMContainerPolicy.getClass();
            Log.d("KnoxMUMContainerPolicy", "License status updated");
            Message obtainMessage = knoxMUMContainerPolicy.mContainerHandler.obtainMessage(11);
            Bundle bundle = new Bundle();
            bundle.putString("packageName", str);
            obtainMessage.setData(bundle);
            knoxMUMContainerPolicy.mContainerHandler.sendMessage(obtainMessage);
        }

        @Override // com.android.server.enterprise.license.IActivationKlmElmObserver
        public final void onUpdateElm(String str, LicenseResult licenseResult) {
            Context context = KnoxMUMContainerPolicy.mContext;
            Log.d("KnoxMUMContainerPolicy", "onUpdateElm");
            notifyAppSeparationLicense(str, licenseResult);
        }

        @Override // com.android.server.enterprise.license.IActivationKlmElmObserver
        public final void onUpdateKlm(String str, LicenseResult licenseResult) {
            Context context = KnoxMUMContainerPolicy.mContext;
            Log.d("KnoxMUMContainerPolicy", "onUpdateKlm");
            notifyAppSeparationLicense(str, licenseResult);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CrossProfileIntentFilter {
        public IntentFilter filter;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class Injector {
        public final Context mContext;

        public Injector(Context context) {
            this.mContext = context;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LocalService {
        public LocalService() {}
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ProvisioningProcessObserver extends IProcessObserver.Stub {
        public ProvisioningProcessObserver() {}

        public final boolean maybeUnregister() {
            synchronized (KnoxMUMContainerPolicy.this.mProvisioningLock) {
                if (KnoxMUMContainerPolicy.this.mCurrentProvisioningState != null) {
                    return false;
                }
                Context context = KnoxMUMContainerPolicy.mContext;
                Log.d("KnoxMUMContainerPolicy", "maybeUnregister() unregistering process observer");
                try {
                    if (KnoxMUMContainerPolicy.this.mProvisioningObserver != null) {
                        ActivityManagerNative.getDefault()
                                .unregisterProcessObserver(
                                        KnoxMUMContainerPolicy.this.mProvisioningObserver);
                        KnoxMUMContainerPolicy.this.mProvisioningObserver = null;
                        Log.i("KnoxMUMContainerPolicy", "provisioning observer unregistered");
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                    Context context2 = KnoxMUMContainerPolicy.mContext;
                    Log.e("KnoxMUMContainerPolicy", "Can't unregisterProcessObserver");
                }
                return true;
            }
        }

        public final void onForegroundActivitiesChanged(int i, int i2, boolean z) {
            maybeUnregister();
        }

        public final void onForegroundServicesChanged(int i, int i2, int i3) {
            maybeUnregister();
        }

        public final void onProcessDied(int i, int i2) {
            int i3;
            if (!maybeUnregister() && UserHandle.getUserId(i2) == 0) {
                synchronized (KnoxMUMContainerPolicy.this.mProvisioningLock) {
                    try {
                        ProvisioningState provisioningState =
                                KnoxMUMContainerPolicy.this.mCurrentProvisioningState;
                        synchronized (KnoxMUMContainerPolicy.this.mProvisioningLock) {
                            i3 = provisioningState.state;
                        }
                        if (i3 == 1 || i3 == 2) {
                            ProvisioningState provisioningState2 =
                                    KnoxMUMContainerPolicy.this.mCurrentProvisioningState;
                            provisioningState2.getClass();
                            if (provisioningState2.pidProvision == i) {
                                Context context = KnoxMUMContainerPolicy.mContext;
                                Log.i("KnoxMUMContainerPolicy", "managedprovisioning died..");
                                Bundle bundle = new Bundle();
                                bundle.putInt(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, 12);
                                KnoxMUMContainerPolicy.this.mCurrentProvisioningState.update(
                                        bundle);
                                maybeUnregister();
                            }
                        } else if (i3 == 3) {
                            ProvisioningState provisioningState3 =
                                    KnoxMUMContainerPolicy.this.mCurrentProvisioningState;
                            provisioningState3.getClass();
                            if (provisioningState3.pidKnox == i) {
                                Context context2 = KnoxMUMContainerPolicy.mContext;
                                Log.i("KnoxMUMContainerPolicy", "KnoxCore died..");
                                Bundle bundle2 = new Bundle();
                                bundle2.putInt(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, 12);
                                KnoxMUMContainerPolicy.this.mCurrentProvisioningState.update(
                                        bundle2);
                                maybeUnregister();
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }

        public final void onProcessStarted(int i, int i2, int i3, String str, String str2) {}
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ProvisioningState {
        public int state = 0;
        public int requestId = 0;
        public String type = null;
        public String adminPackageName = null;
        public int creatorUid = -1;
        public int containerId = -1;
        public int pidProvision = -1;
        public int pidKnox = -1;
        public boolean isCLType = false;
        public String pwdRstToken = null;

        public ProvisioningState() {}

        public static String toString(Bundle bundle) {
            if (bundle == null) {
                return new String("null");
            }
            StringBuilder sb = new StringBuilder("{ ");
            for (String str : bundle.keySet()) {
                Object obj = bundle.get(str);
                if (obj != null) {
                    sb.append(str + ":" + obj.toString() + " ");
                }
            }
            sb.append("}");
            return sb.toString();
        }

        public final void notifyAdminCreationStatus(int i, String str) {
            int i2;
            Intent intent = new Intent("com.samsung.knox.container.creation.status");
            if (str != null && !str.isEmpty()) {
                intent.setPackage(str);
            }
            intent.putExtra("code", i);
            intent.putExtra("requestId", this.requestId);
            intent.putExtra("com.samsung.android.knox.intent.extra.ADMIN_UID", this.creatorUid);
            KnoxMUMContainerPolicy.mContext.sendBroadcastAsUser(
                    intent,
                    UserHandle.getUserHandleForUid(this.creatorUid),
                    "com.samsung.android.knox.permission.KNOX_CONTAINER");
            KnoxMUMContainerPolicy.mContext.sendBroadcastAsUser(
                    intent,
                    UserHandle.getUserHandleForUid(this.creatorUid),
                    "com.samsung.android.knox.permission.KNOX_CONTAINER");
            if (i > 0) {
                Intent intent2 = new Intent("enterprise.container.created.nonactive");
                intent2.putExtra("containerid", i);
                intent2.putExtra(
                        "com.samsung.android.knox.intent.extra.ADMIN_UID", this.creatorUid);
                KnoxMUMContainerPolicy.mContext.sendBroadcastAsUser(
                        intent2,
                        new UserHandle(this.creatorUid),
                        "com.samsung.android.knox.permission.KNOX_CONTAINER");
                KnoxMUMContainerPolicy.mContext.sendBroadcastAsUser(
                        intent2,
                        new UserHandle(this.creatorUid),
                        "com.samsung.android.knox.permission.KNOX_CONTAINER");
                Intent intent3 = new Intent("enterprise.container.setup.success");
                intent3.putExtra("containerid", i);
                KnoxMUMContainerPolicy.mContext.sendBroadcastAsUser(
                        intent3,
                        new UserHandle(this.creatorUid),
                        "com.samsung.android.knox.permission.KNOX_CONTAINER");
                KnoxMUMContainerPolicy.mContext.sendBroadcastAsUser(
                        intent3,
                        new UserHandle(this.creatorUid),
                        "com.samsung.android.knox.permission.KNOX_CONTAINER");
            } else if (i == -1017) {
                Intent intent4 = new Intent("enterprise.container.cancelled");
                intent4.putExtra("containerid", 1);
                intent4.putExtra("requestid", this.requestId);
                KnoxMUMContainerPolicy.mContext.sendBroadcastAsUser(
                        intent4,
                        new UserHandle(this.creatorUid),
                        "com.samsung.android.knox.permission.KNOX_CONTAINER");
                KnoxMUMContainerPolicy.mContext.sendBroadcastAsUser(
                        intent4,
                        new UserHandle(this.creatorUid),
                        "com.samsung.android.knox.permission.KNOX_CONTAINER");
            } else {
                Intent intent5 = new Intent("enterprise.container.setup.failure");
                KnoxMUMContainerPolicy.mContext.sendBroadcastAsUser(
                        intent5,
                        new UserHandle(this.creatorUid),
                        "com.samsung.android.knox.permission.KNOX_CONTAINER");
                KnoxMUMContainerPolicy.mContext.sendBroadcastAsUser(
                        intent5,
                        new UserHandle(this.creatorUid),
                        "com.samsung.android.knox.permission.KNOX_CONTAINER");
            }
            synchronized (KnoxMUMContainerPolicy.mSetupCallbackLock) {
                try {
                    if (KnoxMUMContainerPolicy.mSetupCallback != null) {
                        Log.d(
                                "KnoxMUMContainerPolicy",
                                "create callback found, updating callback..");
                        Bundle bundle = new Bundle();
                        int i3 = this.containerId;
                        if (i3 > 0) {
                            bundle.putInt("containerid", i3);
                            i2 = 1001;
                        } else if (i3 == -1017) {
                            bundle.putInt("containerid", 1);
                            bundle.putInt("requestid", this.requestId);
                            i2 = 1016;
                        } else {
                            bundle.putInt("containerid", 1);
                            i2 = 1002;
                        }
                        try {
                            KnoxMUMContainerPolicy.mSetupCallback.updateStatus(i2, bundle);
                        } catch (RemoteException e) {
                            Context context = KnoxMUMContainerPolicy.mContext;
                            Log.e(
                                    "KnoxMUMContainerPolicy",
                                    "Exception:" + Log.getStackTraceString(e));
                        }
                        KnoxMUMContainerPolicy.mSetupCallback = null;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final boolean start(Bundle bundle) {
            Context context = KnoxMUMContainerPolicy.mContext;
            Log.i("KnoxMUMContainerPolicy", "Provisioning started... " + toString(bundle));
            this.type = bundle.getString("type", null);
            this.requestId = bundle.getInt("requestId", -1);
            this.pidProvision = bundle.getInt("pidProvision", -1);
            this.adminPackageName = bundle.getString("adminPackageName", null);
            this.isCLType = bundle.getBoolean("isCLType", false);
            this.pwdRstToken = bundle.getString("pwdRstToken", null);
            this.creatorUid = bundle.getInt("creatorUid", -1);
            if (this.type == null) {
                Log.e("KnoxMUMContainerPolicy", "tyep not specified, provisioning fails");
                return false;
            }
            if (this.adminPackageName != null) {
                return true;
            }
            Log.e("KnoxMUMContainerPolicy", "admin not specified, provisioning fails");
            return false;
        }

        public final boolean startProvisioningObserver() {
            KnoxMUMContainerPolicy knoxMUMContainerPolicy = KnoxMUMContainerPolicy.this;
            if (knoxMUMContainerPolicy.mProvisioningObserver == null) {
                knoxMUMContainerPolicy.mProvisioningObserver =
                        knoxMUMContainerPolicy.new ProvisioningProcessObserver();
            }
            try {
                ActivityManagerNative.getDefault()
                        .registerProcessObserver(knoxMUMContainerPolicy.mProvisioningObserver);
                Context context = KnoxMUMContainerPolicy.mContext;
                Log.d("KnoxMUMContainerPolicy", "Process kill observer registered.");
                return true;
            } catch (RemoteException e) {
                Context context2 = KnoxMUMContainerPolicy.mContext;
                Log.e("KnoxMUMContainerPolicy", "RemoteException :(" + Log.getStackTraceString(e));
                return false;
            } catch (NullPointerException e2) {
                Context context3 = KnoxMUMContainerPolicy.mContext;
                Log.e(
                        "KnoxMUMContainerPolicy",
                        "NullPointerException :(" + Log.getStackTraceString(e2));
                return false;
            }
        }

        public final Bundle toBundle() {
            Bundle bundle = new Bundle();
            synchronized (KnoxMUMContainerPolicy.this.mProvisioningLock) {
                bundle.putInt(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, this.state);
                bundle.putInt("requestId", this.requestId);
                bundle.putString("type", this.type);
                bundle.putString("adminPackageName", this.adminPackageName);
                bundle.putInt("creatorUid", this.creatorUid);
                bundle.putInt(KnoxCustomManagerService.CONTAINER_ID_ZERO, this.containerId);
                bundle.putInt("pidProvision", this.pidProvision);
                bundle.putInt("pidKnox", this.pidKnox);
                bundle.putBoolean("isCLType", this.isCLType);
                bundle.putString("pwdRstToken", this.pwdRstToken);
            }
            return bundle;
        }

        public final String toString() {
            return toString(toBundle());
        }

        public final boolean update(Bundle bundle) {
            Context context = KnoxMUMContainerPolicy.mContext;
            Log.i("KnoxMUMContainerPolicy", "ProvisioningState.update():" + toBundle().toString());
            Log.i(
                    "KnoxMUMContainerPolicy",
                    "ProvisioningState.update(): appying:" + toString(bundle));
            synchronized (KnoxMUMContainerPolicy.this.mProvisioningLock) {
                try {
                    int i = bundle.getInt(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, -1);
                    if (i == 2) {
                        this.state = 2;
                        Log.i("KnoxMUMContainerPolicy", "ManagedProvisioning service started");
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        if (!startProvisioningObserver()) {
                            Log.e("KnoxMUMContainerPolicy", "failed to start provisioning");
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            return false;
                        }
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    } else if (i != 3) {
                        switch (i) {
                            case 10:
                                Log.i("KnoxMUMContainerPolicy", "finished");
                                this.state = 10;
                                KnoxMUMContainerPolicy.m502$$Nest$mprovisioningFinished(
                                        KnoxMUMContainerPolicy.this, this.containerId);
                                break;
                            case 11:
                                Log.i("KnoxMUMContainerPolicy", "failed");
                                this.state = 11;
                                KnoxMUMContainerPolicy.m502$$Nest$mprovisioningFinished(
                                        KnoxMUMContainerPolicy.this, -1014);
                                break;
                            case 12:
                                Log.i("KnoxMUMContainerPolicy", "cancelled");
                                this.state = 12;
                                KnoxMUMContainerPolicy.m502$$Nest$mprovisioningFinished(
                                        KnoxMUMContainerPolicy.this, -1017);
                                break;
                        }
                    } else {
                        Log.i("KnoxMUMContainerPolicy", "KnoxCore extension service started");
                        this.state = 3;
                        this.containerId =
                                bundle.getInt(KnoxCustomManagerService.CONTAINER_ID_ZERO, -1014);
                        this.pidKnox = bundle.getInt("pidKnox", -1);
                        if (this.containerId < 0) {
                            Log.e("KnoxMUMContainerPolicy", "container id is not provided");
                            return false;
                        }
                        if (this.type == null) {
                            if (!bundle.containsKey("type")) {
                                Log.e("KnoxMUMContainerPolicy", "type not provided");
                                return false;
                            }
                            this.type = bundle.getString("type");
                            if (!bundle.containsKey("creatorUid")) {
                                Log.e("KnoxMUMContainerPolicy", "creatorUid not provided");
                                return false;
                            }
                            this.creatorUid = bundle.getInt("creatorUid");
                        }
                    }
                    return true;
                } finally {
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RequestIdGenerator {
        public int fraction;
        public Random random;
    }

    /* renamed from: -$$Nest$misDualDarLicenseLockedCase, reason: not valid java name */
    public static boolean m500$$Nest$misDualDarLicenseLockedCase(
            KnoxMUMContainerPolicy knoxMUMContainerPolicy) {
        if (knoxMUMContainerPolicy.getUserManagerService().getUserInfo(0).isAdminLocked()) {
            return false;
        }
        if (!SemPersonaManager.isDoEnabled(0) || !SemPersonaManager.isDarDualEncryptionEnabled(0)) {
            if (!knoxMUMContainerPolicy
                    .getDevicePolicyService()
                    .isOrganizationOwnedDeviceWithManagedProfile()) {
                return false;
            }
            IDevicePolicyManager asInterface =
                    IDevicePolicyManager.Stub.asInterface(
                            ServiceManager.getService("device_policy"));
            Iterator it = knoxMUMContainerPolicy.getUserManagerService().getProfiles(0).iterator();
            while (it.hasNext()) {
                int identifier = ((UserInfo) it.next()).getUserHandle().getIdentifier();
                if (SemPersonaManager.isKnoxId(identifier)
                        && SemPersonaManager.isDarDualEncryptionEnabled(identifier)
                        && asInterface != null) {
                    try {
                        if (asInterface.isProfileOwnerOfOrganizationOwnedDeviceMDM(identifier)) {}
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
            return false;
        }
        return true;
    }

    /* renamed from: -$$Nest$mnotifyDOPremiumActivation, reason: not valid java name */
    public static void m501$$Nest$mnotifyDOPremiumActivation(
            KnoxMUMContainerPolicy knoxMUMContainerPolicy) {
        if (!knoxMUMContainerPolicy.getUserManagerService().getUserInfo(0).isPremiumContainer()) {
            Log.i(
                    "KnoxMUMContainerPolicy",
                    "DO' license is not activated so ignoring the request...");
        } else {
            Log.i(
                    "KnoxMUMContainerPolicy",
                    "DO'premium provisioning completed, sending intent to KLMS agent");
            sendIntentBroadcastForContainer(0, "com.sec.knox.containeragent.klms.created.b2b");
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:51:0x0061, code lost:

       if (r9.mCurrentProvisioningState.containerId == 0) goto L49;
    */
    /* renamed from: -$$Nest$mprovisioningFinished, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void m502$$Nest$mprovisioningFinished(
            com.android.server.enterprise.container.KnoxMUMContainerPolicy r9, int r10) {
        /*
            Method dump skipped, instructions count: 430
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.container.KnoxMUMContainerPolicy.m502$$Nest$mprovisioningFinished(com.android.server.enterprise.container.KnoxMUMContainerPolicy,"
                    + " int):void");
    }

    /* renamed from: -$$Nest$msendContainerAdminLockIntent, reason: not valid java name */
    public static void m503$$Nest$msendContainerAdminLockIntent(
            KnoxMUMContainerPolicy knoxMUMContainerPolicy, String str, int i, int i2) {
        knoxMUMContainerPolicy.getClass();
        Intent intent = new Intent("enterprise.container.locked");
        if (str != null && !str.isEmpty()) {
            intent.setPackage(str);
        }
        intent.putExtra("containerid", i);
        mContext.sendBroadcastAsUser(
                intent, new UserHandle(i2), "com.samsung.android.knox.permission.KNOX_CONTAINER");
    }

    /* renamed from: -$$Nest$msendContainerStateChangeIntent, reason: not valid java name */
    public static void m504$$Nest$msendContainerStateChangeIntent(
            KnoxMUMContainerPolicy knoxMUMContainerPolicy,
            String str,
            int i,
            int i2,
            int i3,
            int i4) {
        knoxMUMContainerPolicy.getClass();
        Intent intent = new Intent("com.samsung.enterprise.container_state_changed");
        if (str != null && !str.isEmpty()) {
            intent.setPackage(str);
        }
        Bundle bundle = new Bundle();
        bundle.putInt("containerid", i);
        bundle.putInt("container_old_state", i3);
        bundle.putInt("container_new_state", i4);
        intent.putExtra(KnoxCustomManagerService.INTENT, bundle);
        mContext.sendBroadcastAsUser(
                intent, new UserHandle(i2), "com.samsung.android.knox.permission.KNOX_CONTAINER");
        Intent intent2 =
                new Intent("com.samsung.android.knox.intent.action.CONTAINER_STATE_CHANGED");
        if (str != null && !str.isEmpty()) {
            intent2.setPackage(str);
        }
        Bundle bundle2 = new Bundle();
        bundle2.putInt("containerid", i);
        bundle2.putInt("container_old_state", i3);
        bundle2.putInt("container_new_state", i4);
        intent2.putExtra(KnoxCustomManagerService.INTENT, bundle2);
        mContext.sendBroadcastAsUser(
                intent2, new UserHandle(i2), "com.samsung.android.knox.permission.KNOX_CONTAINER");
        if (str == null || str.isEmpty()) {
            return;
        }
        String kpuPackageName = KpuHelper.getInstance(mContext).getKpuPackageName();
        Intent intent3 = new Intent(intent2);
        intent3.setPackage(kpuPackageName);
        mContext.sendBroadcastAsUser(
                intent3, new UserHandle(i2), "com.samsung.android.knox.permission.KNOX_CONTAINER");
    }

    static {
        String str = Build.TYPE;
        isEngMode = "eng".equals(str);
        isUserMode = "user".equalsIgnoreCase(str);
        mSetupCallback = null;
        mSetupCallbackLock = new Object();
        new ArrayList();
        mbadgePolicylist = new ArrayList();
        REMOVE_SHORTCUT_CONTENT_URI =
                Uri.parse("content://com.sec.android.app.launcher.settings/settings");
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.server.enterprise.container.KnoxMUMContainerPolicy$1] */
    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.server.enterprise.container.KnoxMUMContainerPolicy$4] */
    public KnoxMUMContainerPolicy(Context context) {
        Injector injector = new Injector(context);
        this.mEDM = null;
        this.mEdmStorageProvider = null;
        this.mTypeList = new ArrayList();
        this.mPersona = null;
        this.mUms = null;
        this.mDpm = null;
        this.mRIdGenerator = null;
        this.mParamsList = new ArrayList();
        this.mContainerHandler = null;
        this.mActivityManager = null;
        this.mFirmwareVersion = null;
        this.mSEAMS = null;
        this.mReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.android.server.enterprise.container.KnoxMUMContainerPolicy.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        if (intent.getAction()
                                        .equals("android.bluetooth.adapter.action.STATE_CHANGED")
                                && intent.getIntExtra(
                                                "android.bluetooth.adapter.extra.STATE",
                                                Integer.MIN_VALUE)
                                        == 10
                                && KnoxMUMContainerPolicy.this.mRestart) {
                            Context context3 = KnoxMUMContainerPolicy.mContext;
                            Log.d("KnoxMUMContainerPolicy", "***** Restarting Bluetooth *****");
                            KnoxMUMContainerPolicy.this.mRestart = false;
                            if (BluetoothAdapter.getDefaultAdapter() != null) {
                                BluetoothAdapter.getDefaultAdapter().enable();
                            }
                        }
                    }
                };
        this.contentObserver =
                new ContentObserver(new Handler()) { // from class:
                    // com.android.server.enterprise.container.KnoxMUMContainerPolicy.4
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z, Uri uri, int i) {
                        ActivityTaskManagerService activityTaskManagerService;
                        Context context2 = KnoxMUMContainerPolicy.mContext;
                        StringBuilder sb = new StringBuilder("onChange ");
                        sb.append(z);
                        sb.append(" / ");
                        sb.append(uri);
                        sb.append(" / ");
                        GestureWakeup$$ExternalSyntheticOutline0.m(sb, i, "KnoxMUMContainerPolicy");
                        KnoxMUMContainerPolicy.this.getClass();
                        int secureFolderId = KnoxMUMContainerPolicy.getSecureFolderId();
                        if (uri.equals(Settings.Secure.getUriFor("hide_secure_folder_flag"))
                                && i == 0) {
                            int intForUser =
                                    Settings.Secure.getIntForUser(
                                            KnoxMUMContainerPolicy.mContext.getContentResolver(),
                                            "hide_secure_folder_flag",
                                            0,
                                            0);
                            if (secureFolderId != -1) {
                                KnoxMUMContainerPolicy.this.getClass();
                                int secureFolderId2 = KnoxMUMContainerPolicy.getSecureFolderId();
                                AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                                        secureFolderId2,
                                        intForUser,
                                        "showSecureFolder id ",
                                        " newValue ",
                                        "KnoxMUMContainerPolicy");
                                if (intForUser == 1) {
                                    try {
                                        ActivityManagerService activityManagerService =
                                                (ActivityManagerService)
                                                        ServiceManager.getService("activity");
                                        if (activityManagerService == null
                                                || (activityTaskManagerService =
                                                                activityManagerService
                                                                        .mActivityTaskManager)
                                                        == null) {
                                            return;
                                        }
                                        if (activityTaskManagerService.mPersonaActivityHelper
                                                .isKnoxWindowVisibleLocked(secureFolderId2, 1)) {
                                            Log.d(
                                                    "KnoxMUMContainerPolicy",
                                                    "showSecureFolder :: Exit from Multiwindow"
                                                        + " first");
                                            Intent intent =
                                                    new Intent("android.intent.action.MAIN");
                                            intent.addCategory("android.intent.category.HOME");
                                            intent.setFlags(335544320);
                                            KnoxMUMContainerPolicy.mContext.startActivityAsUser(
                                                    intent, new UserHandle(0));
                                        }
                                        PersonaActivityHelper personaActivityHelper =
                                                activityManagerService
                                                        .mActivityTaskManager
                                                        .mPersonaActivityHelper;
                                        boolean checkKnoxFeatureEnabled =
                                                personaActivityHelper.checkKnoxFeatureEnabled();
                                        PersonaActivityHelper.PersonaActivityHandler
                                                personaActivityHandler =
                                                        personaActivityHelper
                                                                .mPersonaActivityHandler;
                                        if (checkKnoxFeatureEnabled
                                                && SemPersonaManager.isSecureFolderId(
                                                        secureFolderId2)) {
                                            personaActivityHandler.sendMessage(
                                                    personaActivityHandler.obtainMessage(
                                                            EndpointMonitorConst
                                                                    .TRACE_EVENT_APP_DYING));
                                        }
                                        personaActivityHandler.removeMessages(
                                                FrameworkStatsLog.AUTOFILL_FILL_REQUEST_REPORTED);
                                        Message obtainMessage =
                                                personaActivityHandler.obtainMessage(
                                                        FrameworkStatsLog
                                                                .AUTOFILL_FILL_REQUEST_REPORTED);
                                        obtainMessage.arg1 = secureFolderId2;
                                        personaActivityHandler.sendMessage(obtainMessage);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                };
        this.mProvisioningLock = new Object();
        this.mProvisioningObserver = null;
        this.mCurrentProvisioningState = null;
        IntentFilter m =
                BatteryService$$ExternalSyntheticOutline0.m("android.nfc.action.NDEF_DISCOVERED");
        try {
            m.addDataType("*/*");
        } catch (IntentFilter.MalformedMimeTypeException e) {
            Log.d("KnoxMUMContainerPolicy", "MalformedMimeTypeException: " + e);
        }
        CrossProfileIntentFilter crossProfileIntentFilter = new CrossProfileIntentFilter();
        crossProfileIntentFilter.filter = (IntentFilter) Preconditions.checkNotNull(m);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.nfc.action.NDEF_DISCOVERED");
        intentFilter.addDataScheme("http");
        intentFilter.addDataScheme("https");
        intentFilter.addDataScheme("tel");
        intentFilter.addDataScheme("mailto");
        intentFilter.addDataScheme("geo");
        intentFilter.addDataScheme("tel");
        intentFilter.addDataScheme("voicemail");
        intentFilter.addDataScheme("sip");
        intentFilter.addDataScheme("sms");
        intentFilter.addDataScheme("smsto");
        intentFilter.addDataScheme("mms");
        intentFilter.addDataScheme("mmsto");
        intentFilter.addDataScheme("file");
        CrossProfileIntentFilter crossProfileIntentFilter2 = new CrossProfileIntentFilter();
        crossProfileIntentFilter2.filter = (IntentFilter) Preconditions.checkNotNull(intentFilter);
        this.NFC_FILTERS = Arrays.asList(crossProfileIntentFilter, crossProfileIntentFilter2);
        this.mInjector = injector;
        mContext = (Context) Preconditions.checkNotNull(injector.mContext);
        this.mEdmStorageProvider = new EdmStorageProvider(injector.mContext);
        this.mRestart = false;
        this.mPackageManager = mContext.getPackageManager();
        mContext.registerReceiver(
                this.mReceiver, new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED"));
        RequestIdGenerator requestIdGenerator = new RequestIdGenerator();
        requestIdGenerator.fraction = 0;
        requestIdGenerator.random = new Random();
        this.mRIdGenerator = requestIdGenerator;
        this.mContainerHandler = new ContainerHandler();
        this.mHandler = new Handler();
        LocalServices.addService(LocalService.class, new LocalService());
    }

    public static void addPseudoAdminForWpcod(int i) {
        EnterpriseDeviceManagerService enterpriseDeviceManagerService;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                int i2 = EnterpriseDeviceManagerService.$r8$clinit;
                enterpriseDeviceManagerService =
                        (EnterpriseDeviceManagerService) EnterpriseService.sEdmsInstance;
            } catch (Exception e) {
                Log.d(
                        "KnoxMUMContainerPolicy",
                        "addPseudoAdminForWpcod: exception: " + e.getMessage());
                e.printStackTrace();
            }
            if (enterpriseDeviceManagerService == null
                    || enterpriseDeviceManagerService.getOrganizationOwnedProfileUserId() != i) {
                Log.d(
                        "KnoxMUMContainerPolicy",
                        "addPseudoAdminForWpcod: containerId-" + i + " is not WP-C");
                return;
            }
            Log.d("KnoxMUMContainerPolicy", "addPseudoAdminForWpcod: containerId-" + i);
            enterpriseDeviceManagerService.addPseudoAdminForParent(i);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static void checkCallerPermissionFor(String str) {
        if (ServiceKeeper.isAuthorized(
                        Binder.getCallingPid(),
                        Binder.getCallingUid(),
                        mContext,
                        "KnoxMUMContainerPolicy",
                        str)
                != 0) {
            SecurityException securityException =
                    new SecurityException(
                            "Security Exception Occurred while pid["
                                    + Binder.getCallingPid()
                                    + "] with uid["
                                    + Binder.getCallingUid()
                                    + "] trying to access methodName ["
                                    + str
                                    + "] in [KnoxMUMContainerPolicy] service");
            if (!DEBUG) {
                throw securityException;
            }
            securityException.printStackTrace();
            throw securityException;
        }
    }

    public static ArrayList convertStringCommaDelimitedToList(String str) {
        return (str == null || str.isEmpty())
                ? new ArrayList()
                : new ArrayList(Arrays.asList(str.trim().split("\\s*,\\s*")));
    }

    public static String convertToQuotedString(String str) {
        int length = str.length();
        if (length > 1 && str.charAt(0) == '\"' && str.charAt(length - 1) == '\"') {
            return str;
        }
        return "\"" + str + '\"';
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        Bitmap createBitmap =
                Bitmap.createBitmap(
                        drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight(),
                        Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return createBitmap;
    }

    public static void dumpRCPSettings(PrintWriter printWriter, HashMap hashMap) {
        Set<String> keySet = hashMap.keySet();
        if (keySet == null || keySet.isEmpty()) {
            return;
        }
        for (String str : keySet) {
            printWriter.write(" " + str + " {");
            List<Pair> list = (List) hashMap.get(str);
            if (list != null) {
                for (Pair pair : list) {
                    printWriter.write(
                            "  ( " + ((String) pair.first) + "," + ((String) pair.second) + " )");
                }
            }
            printWriter.write(" }");
        }
    }

    public static void enforceCallingCheckPermission(Context context, List list) {
        ArrayList arrayList = (ArrayList) list;
        boolean z = false;
        if (arrayList.size() > 0) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                if (context.checkCallingOrSelfPermission((String) it.next()) == 0) {
                    z = true;
                }
            }
        }
        if (!z) {
            throw new SecurityException("Activate Container permission");
        }
    }

    public static Object getFutureOrThrow(AndroidFuture androidFuture) {
        try {
            return androidFuture.get();
        } catch (Throwable th) {
            th = th;
            if (th instanceof ExecutionException) {
                th = th.getCause();
            }
            if (th instanceof RuntimeException) {
                throw ((RuntimeException) th);
            }
            if (th instanceof Error) {
                throw ((Error) th);
            }
            throw new RuntimeException(th);
        }
    }

    public static Intent getLauncherRefreshIntent(int i) {
        Intent intent = new Intent("com.samsung.android.knox.container.MANAGED_PROFILE_REFRESH");
        intent.putExtra("com.samsung.android.knox.container.userid", i);
        intent.putExtra("com.samsung.sec.knox.EXTRA_PERSONA_ID", 0);
        return intent;
    }

    public static PersonaManagerService getPersonaManagerLocked() {
        return (PersonaManagerService) ServiceManager.getService("persona");
    }

    public static int getSecureFolderId() {
        List<UserInfo> users = ((UserManager) mContext.getSystemService("user")).getUsers(true);
        if (users == null || users.isEmpty()) {
            return -1;
        }
        for (UserInfo userInfo : users) {
            if (userInfo.isSecureFolder()) {
                return userInfo.id;
            }
        }
        return -1;
    }

    public static boolean isIgnoreKSPCall(String str) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            boolean z = SystemProperties.getBoolean("persist.sys.knox.ignore_ksp_call", false);
            if (z && !str.equals("com.samsung.android.appseparation")) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            }
            if (str.equals("com.samsung.android.appseparation") && !z) {
                SystemProperties.set("persist.sys.knox.ignore_ksp_call", "true");
            }
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static boolean processConfigurationType(KnoxConfigurationType knoxConfigurationType) {
        String name = knoxConfigurationType.getName();
        if (name == null || "".equals(name)) {
            return false;
        }
        if (knoxConfigurationType.isDefaultConfigType()) {
            int containerLayout = knoxConfigurationType.getContainerLayout();
            if (containerLayout != 1 && containerLayout != 2) {
                knoxConfigurationType.setContainerLayout(1);
            }
        } else {
            knoxConfigurationType.setContainerLayout(-1);
            if (knoxConfigurationType instanceof ContainerModeConfigurationType) {
                knoxConfigurationType.allowLayoutSwitching(false);
            } else if (knoxConfigurationType instanceof LightweightConfigurationType) {
                String folderDisabledChangeLayout =
                        ((LightweightConfigurationType) knoxConfigurationType)
                                .getFolderDisabledChangeLayout();
                if (folderDisabledChangeLayout == null
                        || "true".compareTo(folderDisabledChangeLayout) != 0) {
                    knoxConfigurationType.allowLayoutSwitching(true);
                } else {
                    knoxConfigurationType.allowLayoutSwitching(false);
                }
            } else {
                knoxConfigurationType.allowLayoutSwitching(true);
            }
        }
        return true;
    }

    public static byte[] readECFile(String str) {
        if (str == null || str.length() == 0) {
            Log.d("KnoxMUMContainerPolicy", "filename is null  null");
            return null;
        }
        File file = new File(str);
        FileInputStream fileInputStream = new FileInputStream(file);
        try {
            long length = file.length();
            if (length > 2147483647L) {
                throw new IOException("The file is too big");
            }
            int i = (int) length;
            byte[] bArr = new byte[i];
            int i2 = 0;
            while (i2 < i) {
                int read = fileInputStream.read(bArr, i2, i - i2);
                if (read < 0) {
                    break;
                }
                i2 += read;
            }
            if (i2 < i) {
                throw new IOException("The file was not completely read: " + file.getName());
            }
            fileInputStream.close();
            Log.d("KnoxMUMContainerPolicy", "Bytes : " + bArr);
            return bArr;
        } catch (Throwable th) {
            fileInputStream.close();
            throw th;
        }
    }

    public static void sendIntentBroadcastForContainer(int i, String str) {
        Log.i(
                "KnoxMUMContainerPolicy",
                "sendIntentBroadcastForContainer : containerId " + i + " and action " + str);
        Intent intent = new Intent(str);
        intent.putExtra("container_id", i);
        intent.setPackage("com.samsung.klmsagent");
        mContext.sendBroadcastAsUser(intent, new UserHandle(0));
    }

    public static void setFilePermission(String str) {
        File file = new File(str);
        file.setReadable(true, false);
        file.setWritable(true);
        file.setExecutable(true, false);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0030  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean writeFile(android.graphics.Bitmap r3, java.lang.String r4) {
        /*
            r0 = 0
            java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L1d java.lang.Exception -> L1f
            r1.<init>(r4)     // Catch: java.lang.Throwable -> L1d java.lang.Exception -> L1f
            android.graphics.Bitmap$CompressFormat r0 = android.graphics.Bitmap.CompressFormat.PNG     // Catch: java.lang.Throwable -> L17 java.lang.Exception -> L1a
            r2 = 100
            boolean r3 = r3.compress(r0, r2, r1)     // Catch: java.lang.Throwable -> L17 java.lang.Exception -> L1a
            r1.close()     // Catch: java.io.IOException -> L12
            goto L2e
        L12:
            r0 = move-exception
            r0.printStackTrace()
            goto L2e
        L17:
            r3 = move-exception
            r0 = r1
            goto L34
        L1a:
            r3 = move-exception
            r0 = r1
            goto L20
        L1d:
            r3 = move-exception
            goto L34
        L1f:
            r3 = move-exception
        L20:
            r3.printStackTrace()     // Catch: java.lang.Throwable -> L1d
            if (r0 == 0) goto L2d
            r0.close()     // Catch: java.io.IOException -> L29
            goto L2d
        L29:
            r3 = move-exception
            r3.printStackTrace()
        L2d:
            r3 = 0
        L2e:
            if (r3 == 0) goto L33
            setFilePermission(r4)
        L33:
            return r3
        L34:
            if (r0 == 0) goto L3e
            r0.close()     // Catch: java.io.IOException -> L3a
            goto L3e
        L3a:
            r4 = move-exception
            r4.printStackTrace()
        L3e:
            throw r3
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.container.KnoxMUMContainerPolicy.writeFile(android.graphics.Bitmap,"
                    + " java.lang.String):boolean");
    }

    public final boolean addConfigurationType(ContextInfo contextInfo, List list) {
        int callingUid;
        KnoxConfigurationType knoxConfigurationType;
        enforceCallingCheckPermission(
                mContext,
                new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CONTAINER")));
        if (contextInfo == null || (callingUid = contextInfo.mCallerUid) <= 0) {
            callingUid = Binder.getCallingUid();
        }
        if (list == null
                || list.isEmpty()
                || (knoxConfigurationType = (KnoxConfigurationType) list.get(0)) == null) {
            return false;
        }
        try {
            Log.d("KnoxMUMContainerPolicy", "Parameter name :" + knoxConfigurationType.getName());
            Log.d(
                    "KnoxMUMContainerPolicy",
                    "getConfigurationTypeByName value :"
                            + getConfigurationTypeByName(
                                    contextInfo, knoxConfigurationType.getName()));
            if (processConfigurationType(knoxConfigurationType)
                    && getConfigurationTypeByName(contextInfo, knoxConfigurationType.getName())
                            == null) {
                return addConfigurationTypeToList(callingUid, knoxConfigurationType);
            }
        } catch (IOException e) {
            Log.e("KnoxMUMContainerPolicy", "IOException : " + Log.getStackTraceString(e));
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:119:0x0561  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x05e7  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x0624  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x063f  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x0658  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x065a  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x0662  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x061d  */
    /* JADX WARN: Removed duplicated region for block: B:158:0x05df  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x0559  */
    /* JADX WARN: Removed duplicated region for block: B:194:0x024d  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0209  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x022a  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x042b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean addConfigurationTypeToList(
            int r30, com.samsung.android.knox.container.KnoxConfigurationType r31) {
        /*
            Method dump skipped, instructions count: 1708
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.container.KnoxMUMContainerPolicy.addConfigurationTypeToList(int,"
                    + " com.samsung.android.knox.container.KnoxConfigurationType):boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x017c A[Catch: all -> 0x007b, Exception -> 0x007e, RemoteException -> 0x0183, TRY_LEAVE, TryCatch #1 {RemoteException -> 0x0183, blocks: (B:38:0x015e, B:40:0x017c), top: B:37:0x015e }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean addHomeShortcutToPersonal(
            com.samsung.android.knox.ContextInfo r17, java.lang.String r18, java.lang.String r19) {
        /*
            Method dump skipped, instructions count: 425
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.container.KnoxMUMContainerPolicy.addHomeShortcutToPersonal(com.samsung.android.knox.ContextInfo,"
                    + " java.lang.String, java.lang.String):boolean");
    }

    public final boolean addNetworkSSID(ContextInfo contextInfo, String str) {
        ContextInfo enforceWifiPermission = enforceWifiPermission(contextInfo);
        DualAppManagerService$$ExternalSyntheticOutline0.m(
                "addNetworkSSID - ssid : ", str, "KnoxMUMContainerPolicy");
        String str2 = null;
        if (str != null) {
            try {
                String trim = str.trim();
                if (trim.length() > 0) {
                    str2 = trim;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (str2 == null) {
            return false;
        }
        int i = enforceWifiPermission.mCallerUid;
        Set ssid = getSSID(i);
        if (((HashSet) ssid).add(convertToQuotedString(str2))) {
            return saveBlockedList(i, ssid);
        }
        Log.e("KnoxMUMContainerPolicy", "addNetworkSSID failed : already exist");
        return false;
    }

    public final int addPackageToExternalStorageBlackList(
            ContextInfo contextInfo, AppIdentity appIdentity) {
        boolean z;
        DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                new StringBuilder("addPackageToExternalStorageBlackList "),
                appIdentity != null ? appIdentity.getPackageName() : "null",
                "KnoxMUMContainerPolicy");
        if (appIdentity == null) {
            return -1;
        }
        ContextInfo enforceSecurityPermission =
                enforceSecurityPermission(
                        contextInfo,
                        new ArrayList(
                                Arrays.asList(
                                        "com.samsung.android.knox.permission.KNOX_CONTAINER")));
        String packageName = appIdentity.getPackageName();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        if (enforceSecurityPermission != null) {
            try {
                if (packageName != null) {
                    try {
                        if (!packageName.equals("")) {
                            try {
                                PackageInfo packageInfo =
                                        AppGlobals.getPackageManager()
                                                .getPackageInfo(
                                                        packageName, 64L, contextInfo.mContainerId);
                                Log.d("KnoxMUMContainerPolicy", "Package Info: " + packageInfo);
                                if (packageInfo != null) {
                                    if (DEBUG) {
                                        Log.d(
                                                "KnoxMUMContainerPolicy",
                                                "Package Info: "
                                                        + Arrays.toString(packageInfo.signatures));
                                    }
                                    z = true;
                                } else {
                                    z = false;
                                }
                                if (addPackageToExternalStorageSBABlackListInternal(
                                        enforceSecurityPermission,
                                        packageName,
                                        z ? packageInfo.signatures : null)) {
                                    if (z) {
                                        Signature[] signatureArr = packageInfo.signatures;
                                        if (this.mSEAMS == null) {
                                            this.mSEAMS =
                                                    (SEAMService)
                                                            ServiceManager.getService(
                                                                    "SEAMService");
                                        }
                                        if (this.mSEAMS == null) {
                                            Log.e(
                                                    "KnoxMUMContainerPolicy",
                                                    "addPackageToExternalStorageSBABlackList :"
                                                        + " SEAMS service cannot be null.");
                                            Binder.restoreCallingIdentity(clearCallingIdentity);
                                            return -1;
                                        }
                                        if (signatureArr != null && signatureArr.length != 0) {
                                            String[] strArr = new String[signatureArr.length];
                                            for (int i = 0; i < signatureArr.length; i++) {
                                                strArr[i] = signatureArr[i].toCharsString();
                                            }
                                            Bundle m =
                                                    FreecessController$$ExternalSyntheticOutline0.m(
                                                            contextInfo.mContainerId,
                                                            "knox.container.proxy.EXTRA_PACKAGE_NAME",
                                                            packageName,
                                                            "android.intent.extra.user_handle");
                                            long clearCallingIdentity2 =
                                                    Binder.clearCallingIdentity();
                                            ContainerProxy.sendPolicyUpdate(
                                                    "knox.container.proxy.POLICY_SDCARD_POLICY_CHANGED",
                                                    m);
                                            Binder.restoreCallingIdentity(clearCallingIdentity2);
                                        }
                                        Log.e(
                                                "KnoxMUMContainerPolicy",
                                                "addPackageToExternalStorageSBABlackList : package"
                                                    + " signature cannot be null.");
                                        Binder.restoreCallingIdentity(clearCallingIdentity);
                                        return -1;
                                    }
                                    Binder.restoreCallingIdentity(clearCallingIdentity);
                                    return 0;
                                }
                            } catch (RemoteException e) {
                                Log.e("KnoxMUMContainerPolicy", Log.getStackTraceString(e));
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                                return -1;
                            } catch (Exception e2) {
                                Log.e("KnoxMUMContainerPolicy", Log.getStackTraceString(e2));
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                                return -1;
                            }
                        }
                    } catch (Exception e3) {
                        Log.e("KnoxMUMContainerPolicy", Log.getStackTraceString(e3));
                    }
                }
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        Log.e("KnoxMUMContainerPolicy", "addPackageToExternalStorageSBABlackList policy failed");
        return -1;
    }

    public final boolean addPackageToExternalStorageSBABlackListInternal(
            ContextInfo contextInfo, String str, Signature[] signatureArr) {
        String str2;
        boolean putValuesNoUpdate;
        if (signatureArr == null || signatureArr.length <= 0) {
            str2 = "";
        } else {
            String[] strArr = new String[signatureArr.length];
            for (int i = 0; i < signatureArr.length; i++) {
                strArr[i] = signatureArr[i].toCharsString();
            }
            str2 = TextUtils.join(",", strArr);
        }
        ContentValues contentValues = new ContentValues();
        KnoxMUMContainerPolicy$$ExternalSyntheticOutline0.m(
                contextInfo.mCallerUid, contentValues, "adminUid", "packageName", str);
        if (this.mEdmStorageProvider.getCount("KnoxExternalStorageSBABlacklist", contentValues)
                > 0) {
            putValuesNoUpdate =
                    this.mEdmStorageProvider.putValues(
                            "KnoxExternalStorageSBABlacklist",
                            AccountManagerService$$ExternalSyntheticOutline0.m("signatures", str2),
                            contentValues);
        } else {
            contentValues.put("signatures", str2);
            putValuesNoUpdate =
                    this.mEdmStorageProvider.putValuesNoUpdate(
                            "KnoxExternalStorageSBABlacklist", contentValues);
        }
        if (!putValuesNoUpdate) {
            return false;
        }
        Log.e(
                "KnoxMUMContainerPolicy",
                "addPackageToExternalStorageSBABlackListInternal policy passed");
        return true;
    }

    public final int addPackageToExternalStorageWhiteList(
            ContextInfo contextInfo, AppIdentity appIdentity) {
        StringBuilder sb = new StringBuilder("addPackageToExternalStorageWhiteList ");
        sb.append(appIdentity != null ? appIdentity.getPackageName() : "null");
        Log.i("KnoxMUMContainerPolicy", sb.toString());
        ContextInfo enforceContainerOwnershipPermission =
                enforceContainerOwnershipPermission(
                        contextInfo,
                        new ArrayList(
                                Arrays.asList(
                                        "com.samsung.android.knox.permission.KNOX_CONTAINER")));
        IApplicationPolicy appService = getAppService();
        int i = -1;
        if (appService == null) {
            Log.e("KnoxMUMContainerPolicy", "Application PolicyService is not yet ready!!!");
            return -1;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                i =
                        appService.addPackageToWhiteList(
                                enforceContainerOwnershipPermission, 2, appIdentity);
            } catch (RemoteException e) {
                Log.w(
                        "KnoxMUMContainerPolicy",
                        "Failed at ContainerConfigurationPolicy API"
                            + " addPackageToExternalStorageWhiteList ",
                        e);
            }
            return i;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final int addPackageToInstallWhiteList(
            ContextInfo contextInfo, AppIdentity appIdentity) {
        StringBuilder sb =
                new StringBuilder("addPackageToInstallWhiteList is called for package - ");
        sb.append(appIdentity != null ? appIdentity.getPackageName() : "null");
        Log.i("KnoxMUMContainerPolicy", sb.toString());
        ContextInfo enforceContainerOwnershipPermission =
                enforceContainerOwnershipPermission(
                        contextInfo,
                        new ArrayList(
                                Arrays.asList(
                                        "com.samsung.android.knox.permission.KNOX_CONTAINER")));
        IApplicationPolicy appService = getAppService();
        int i = -1;
        if (appService == null) {
            Log.e("KnoxMUMContainerPolicy", "Application PolicyService is not yet ready!!!");
            return -1;
        }
        if (appIdentity == null || appIdentity.getPackageName() == null) {
            return -1;
        }
        try {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            i =
                    appService.addPackageToWhiteList(
                            enforceContainerOwnershipPermission, 1, appIdentity);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return i;
        } catch (RemoteException e) {
            Log.w(
                    "KnoxMUMContainerPolicy",
                    "Failed at ContainerConfigurationPolicy API addPackageToInstallWhiteList ",
                    e);
            return i;
        }
    }

    public final boolean allowLayoutSwitching(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceContainerOwnershipPermission =
                enforceContainerOwnershipPermission(
                        contextInfo,
                        new ArrayList(
                                Arrays.asList(
                                        "com.samsung.android.knox.permission.KNOX_CONTAINER")));
        boolean z2 = false;
        if (enforceContainerOwnershipPermission == null) {
            return false;
        }
        int i = contextInfo.mContainerId;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                z2 =
                        setFeatureAccessPermission(
                                enforceContainerOwnershipPermission, "LAYOUT_SWITCH", z);
                Log.d(
                        "KnoxMUMContainerPolicy",
                        "allowLayoutSwitching status - " + z2 + ", personaId - " + i);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return z2;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean cancelCreateContainer(ContainerCreationParams containerCreationParams) {
        checkCallerPermissionFor("cancelCreateContainer");
        Log.d(
                "KnoxMUMContainerPolicy",
                "cancelCreateContainer ->  :  adminParam: " + containerCreationParams);
        synchronized (this.mProvisioningLock) {
            try {
                if (this.mCurrentProvisioningState == null) {
                    return false;
                }
                Bundle bundle = new Bundle();
                bundle.putInt(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, 12);
                this.mCurrentProvisioningState.update(bundle);
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final int checkProvisioningPreCondition(int i, String str, boolean z) {
        if (Binder.getCallingUid() != 1000) {
            checkCallerPermissionFor("checkProvisioningPreCondition");
        }
        NetworkScoreService$$ExternalSyntheticOutline0.m(
                i,
                "checkProvisioningPreCondition called type:",
                str,
                " flags:",
                "KnoxMUMContainerPolicy");
        KnoxConfigurationType configurationTypeByName =
                KnoxContainerManager.getConfigurationTypeByName(str);
        if (str != null && !"secure-folder".equals(str)) {
            if (Integer.parseInt("2") == 2) {
                Log.e("KnoxMUMContainerPolicy", "Cannot create Legacy container on PEACE products");
                return -9999;
            }
            if (Integer.parseInt("2") == 1
                    && (configurationTypeByName instanceof ContainerModeConfigurationType)) {
                Log.e("KnoxMUMContainerPolicy", "Cannot create COM container on PEACE products");
                return -9999;
            }
        }
        List profiles = ((UserManager) mContext.getSystemService("user")).getProfiles(0);
        if ((configurationTypeByName instanceof ContainerModeConfigurationType)
                && SemDesktopModeManager.isDesktopMode()) {
            Log.e("KnoxMUMContainerPolicy", "Cannot create COM container on DeskTopMode(DEX)");
            return -1014;
        }
        try {
            SemDesktopModeManager semDesktopModeManager =
                    (SemDesktopModeManager) mContext.getSystemService("desktopmode");
            if (semDesktopModeManager != null) {
                SemDesktopModeState desktopModeState = semDesktopModeManager.getDesktopModeState();
                if ((configurationTypeByName instanceof ContainerModeConfigurationType)
                        && desktopModeState.enabled == 4) {
                    Log.e(
                            "KnoxMUMContainerPolicy",
                            "Cannot create COM container on DeskTopMode(Dual mode)");
                    return -1014;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!PersonaServiceHelper.canAddMoreManagedProfiles(i, z, profiles)) {
            Log.e("KnoxMUMContainerPolicy", "Cannot add more profiles");
            return -1021;
        }
        synchronized (this.mProvisioningLock) {
            try {
                ProvisioningState provisioningState = this.mCurrentProvisioningState;
                if (provisioningState != null && provisioningState.state > 1) {
                    Log.e("KnoxMUMContainerPolicy", "Another provisioning is ongoing.");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        NetworkScoreService$$ExternalSyntheticOutline0.m(
                i,
                "checkProvisioningPreCondition allowed:",
                str,
                " flags:",
                "KnoxMUMContainerPolicy");
        return 0;
    }

    public final int checkProvisioningPreCondition(String str, int i) {
        return checkProvisioningPreCondition(i, str, true);
    }

    public final boolean clearNetworkSSID(ContextInfo contextInfo) {
        ContextInfo enforceWifiPermission = enforceWifiPermission(contextInfo);
        GestureWakeup$$ExternalSyntheticOutline0.m(
                new StringBuilder("clearNetworkSSID - admin UID : "),
                enforceWifiPermission.mCallerUid,
                "KnoxMUMContainerPolicy");
        String[] strArr = {String.valueOf(enforceWifiPermission.mCallerUid)};
        return this.mEdmStorageProvider.deleteDataByFields(
                "ContainerOnly_wifiAP", new String[] {"adminUid"}, strArr);
    }

    public final int clearPackagesFromExternalStorageBlackList(ContextInfo contextInfo) {
        Log.i(
                "KnoxMUMContainerPolicy",
                "clearPackagesFromExternalStorageBlackList is not available.");
        return -1;
    }

    public final int clearPackagesFromExternalStorageWhiteList(ContextInfo contextInfo) {
        ContextInfo enforceContainerOwnershipPermission =
                enforceContainerOwnershipPermission(
                        contextInfo,
                        new ArrayList(
                                Arrays.asList(
                                        "com.samsung.android.knox.permission.KNOX_CONTAINER")));
        IApplicationPolicy appService = getAppService();
        int i = -1;
        if (appService == null) {
            Log.e("KnoxMUMContainerPolicy", "Application PolicyService is not yet ready!!!");
            return -1;
        }
        try {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            i =
                    appService.clearPackagesFromExternalStorageWhiteList(
                            enforceContainerOwnershipPermission);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return i;
        } catch (RemoteException e) {
            Log.w(
                    "KnoxMUMContainerPolicy",
                    "Failed at ContainerConfigurationPolicy API"
                        + " clearPackagesFromExternalStorageWhiteList ",
                    e);
            return i;
        }
    }

    public final int createContainer(
            ContextInfo contextInfo, CreationParams creationParams, int i) {
        int callingUid;
        boolean z;
        ComponentName componentName;
        ApplicationInfo applicationInfo;
        List<ComponentName> activeAdmins;
        if (creationParams == null) {
            Log.e("KnoxMUMContainerPolicy", "provisioning failed. no creation param");
            return -1026;
        }
        List configurationTypeByName =
                getConfigurationTypeByName(null, creationParams.getConfigurationName());
        if (configurationTypeByName == null || configurationTypeByName.isEmpty()) {
            Log.e("KnoxMUMContainerPolicy", "Invalid Knox Configuration Type!");
            return -1030;
        }
        KnoxConfigurationType knoxConfigurationType =
                (KnoxConfigurationType) configurationTypeByName.get(0);
        boolean z2 = knoxConfigurationType instanceof SecureFolderConfigurationType;
        if ((!z2
                        || getUserManagerService()
                                .hasUserRestriction("no_add_managed_profile", UserHandle.SYSTEM))
                && SemPersonaManager.isDoEnabled(0)) {
            Log.d("KnoxMUMContainerPolicy", "createContainer fails when Device Owner is enabled.");
            this.mContainerHandler.sendMessage(this.mContainerHandler.obtainMessage(8));
            return -1014;
        }
        enforceCallingCheckPermission(
                mContext,
                new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CONTAINER")));
        RequestIdGenerator requestIdGenerator = this.mRIdGenerator;
        int i2 = requestIdGenerator.fraction + 1;
        requestIdGenerator.fraction = i2;
        if (i2 > 10) {
            requestIdGenerator.fraction = 1;
        }
        int nextInt = requestIdGenerator.random.nextInt(100000) * requestIdGenerator.fraction;
        String adminPackageName = creationParams.getAdminPackageName();
        String configurationName = creationParams.getConfigurationName();
        String passwordResetToken = creationParams.getPasswordResetToken();
        if (contextInfo == null || (callingUid = contextInfo.mCallerUid) <= 0) {
            callingUid = Binder.getCallingUid();
        }
        int callingPid = Binder.getCallingPid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (UserHandle.getUserId(callingUid) != 0) {
                Log.e("KnoxMUMContainerPolicy", "Only primary profile (user 0) can activate PO/DO");
                return -1014;
            }
            if (!TextUtils.isEmpty(passwordResetToken)) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -1024;
            }
            if (adminPackageName == null || adminPackageName.isEmpty()) {
                if (this.mActivityManager == null) {
                    this.mActivityManager = (ActivityManager) mContext.getSystemService("activity");
                }
                ActivityManager activityManager = this.mActivityManager;
                if (activityManager != null) {
                    adminPackageName = activityManager.getPackageFromAppProcesses(callingPid);
                }
                z = true;
            } else {
                Log.d(
                        "KnoxMUMContainerPolicy",
                        "admin : " + adminPackageName + ", callingUid - " + callingUid);
                callingUid =
                        mContext.getPackageManager()
                                .getPackageUidAsUser(
                                        adminPackageName, UserHandle.getUserId(callingUid));
                z = false;
            }
            int checkProvisioningPreCondition =
                    checkProvisioningPreCondition(
                            knoxConfigurationType instanceof SecureFolderConfigurationType
                                    ? 268566624
                                    : 268435552,
                            configurationName,
                            false);
            if (checkProvisioningPreCondition != 0) {
                Log.e(
                        "KnoxMUMContainerPolicy",
                        "provisioning not allowed: " + checkProvisioningPreCondition);
                return checkProvisioningPreCondition;
            }
            if (knoxConfigurationType instanceof ContainerModeConfigurationType) {
                if (!z) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return -1023;
                }
                if (!isContainerOnlyModeAllowed()) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return -1021;
                }
            }
            Log.d("KnoxMUMContainerPolicy", "adminUid : " + callingUid);
            AuditLog.logAsUser(
                    5,
                    1,
                    true,
                    Process.myPid(),
                    "KnoxMUMContainerPolicy",
                    String.format(
                            "Admin %d has successfully requested to create container.",
                            Integer.valueOf(callingUid)),
                    0);
            if (z2) {
                Log.d("KnoxMUMContainerPolicy", "Start to check secure folder");
                Bundle bundle = new Bundle();
                bundle.putString("type", configurationName);
                bundle.putInt("requestId", nextInt);
                bundle.putBoolean("isCLType", false);
                bundle.putString("pwdRstToken", passwordResetToken);
                bundle.putInt("creatorUid", callingUid);
                bundle.putInt(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, 1);
                bundle.putString("adminPackageName", adminPackageName);
                this.mCurrentProvisioningState = new ProvisioningState();
                synchronized (this.mProvisioningLock) {
                    this.mCurrentProvisioningState.start(bundle);
                }
                mContext.startServiceAsUser(
                        ExplicitHealthCheckController$$ExternalSyntheticOutline0.m(
                                "com.sec.knox.action.CREATE_SECURE_FOLDER",
                                "com.samsung.android.knox.containercore"),
                        UserHandle.of(UserHandle.myUserId()));
            } else {
                Intent intent = new Intent("android.app.action.PROVISION_MANAGED_PROFILE");
                if (z && (activeAdmins = getDevicePolicyService().getActiveAdmins()) != null) {
                    Iterator<ComponentName> it = activeAdmins.iterator();
                    while (it.hasNext()) {
                        componentName = it.next();
                        String packageName = componentName.getPackageName();
                        Log.d("KnoxMUMContainerPolicy", "Checking : " + packageName);
                        if (packageName.equals(adminPackageName)) {
                            break;
                        }
                    }
                }
                componentName = null;
                if (componentName != null) {
                    intent.putExtra(
                            "android.app.extra.PROVISIONING_DEVICE_ADMIN_COMPONENT_NAME",
                            componentName);
                } else {
                    intent.putExtra(
                            "android.app.extra.PROVISIONING_DEVICE_ADMIN_PACKAGE_NAME",
                            adminPackageName);
                }
                intent.putExtra("com.samsung.knox.container.configType", configurationName);
                intent.putExtra("com.samsung.knox.container.requestId", nextInt);
                intent.putExtra("com.samsung.knox.container.isCLType", z);
                intent.putExtra("com.samsung.knox.container.pwdRstToken", passwordResetToken);
                intent.putExtra("com.samsung.knox.container.adminUid", callingUid);
                try {
                    applicationInfo =
                            mContext.getPackageManager().getApplicationInfo(adminPackageName, 0);
                } catch (PackageManager.NameNotFoundException unused) {
                    Log.e(
                            "KnoxMUMContainerPolicy",
                            "Package '" + adminPackageName + "' is not found");
                    applicationInfo = null;
                }
                if (applicationInfo != null && applicationInfo.icon != 0) {
                    StringBuilder m =
                            DumpUtils$$ExternalSyntheticOutline0.m(
                                    "android.resource://", adminPackageName, "/");
                    m.append(applicationInfo.icon);
                    intent.putExtra(
                            "android.app.extra.PROVISIONING_LOGO_URI", Uri.parse(m.toString()));
                }
                intent.putExtra(
                        "android.app.extra.PROVISIONING_MAIN_COLOR", Color.parseColor("#3D6DCC"));
                intent.setFlags(872546304);
                if (intent.resolveActivity(mContext.getPackageManager()) != null) {
                    Context context = mContext;
                    UserHandle userHandle = new UserHandle(UserHandle.myUserId());
                    if (mContext != null) {
                        clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            context.startActivityAsUser(intent, userHandle);
                        } finally {
                        }
                    }
                    Log.d(
                            "KnoxMUMContainerPolicy",
                            "createContainer: intent from User:"
                                    + UserHandle.myUserId()
                                    + " with requestid: "
                                    + nextInt);
                } else {
                    Log.d("KnoxMUMContainerPolicy", "Device provisioning is not enabled");
                }
            }
            return nextInt;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1026;
        } finally {
        }
    }

    public final int createContainerInternal(ContainerCreationParams containerCreationParams) {
        checkCallerPermissionFor("createContainerInternal");
        int containerId = containerCreationParams.getContainerId();
        int adminUid = containerCreationParams.getAdminUid();
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                adminUid,
                containerId,
                "createContainerInternal ::  uid : ",
                ", containerId : ",
                "KnoxMUMContainerPolicy");
        if (containerId != 0) {
            Log.d("KnoxMUMContainerPolicy", "Add Container owner relationship.");
            try {
                if (!this.mEdmStorageProvider.addMUMContainer(containerId, adminUid)) {
                    Log.e(
                            "KnoxMUMContainerPolicy",
                            "Failed to add container to DB: " + containerId);
                } else if (isEngMode) {
                    Log.d("KnoxMUMContainerPolicy", "Container Added to DB: " + containerId);
                }
            } catch (Exception e) {
                Log.w("KnoxMUMContainerPolicy", "Failed at addContainerToDB ", e);
            }
        }
        IDevicePolicyManager asInterface =
                IDevicePolicyManager.Stub.asInterface(ServiceManager.getService("device_policy"));
        if (asInterface == null) {
            return 0;
        }
        try {
            if (!asInterface.isOrganizationOwnedDeviceWithManagedProfile()) {
                return 0;
            }
            addPseudoAdminForWpcod(containerId);
            return 0;
        } catch (RemoteException e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public final boolean createContainerMarkSuccess(
            ContainerCreationParams containerCreationParams) {
        ContainerCreationParams containerCreationParams2;
        checkCallerPermissionFor("createContainerMarkSuccess");
        Log.d(
                "KnoxMUMContainerPolicy",
                "createContainerMarkSuccess ->  : param: " + containerCreationParams);
        synchronized (this.mParamsList) {
            try {
                List list = this.mParamsList;
                ContainerCreationParams containerCreationParams3 = null;
                if (list != null && containerCreationParams != null) {
                    Iterator it = ((ArrayList) list).iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            containerCreationParams2 = null;
                            break;
                        }
                        containerCreationParams2 = (ContainerCreationParams) it.next();
                        if (containerCreationParams2.getRequestId()
                                == containerCreationParams.getRequestId()) {}
                    }
                    if (containerCreationParams2 != null) {
                        containerCreationParams3 = containerCreationParams2;
                    }
                }
                if (containerCreationParams3 == null) {
                    return false;
                }
                containerCreationParams3.setRequestState(2);
                return true;
            } finally {
            }
        }
    }

    public final int createContainerWithCallback(
            ContextInfo contextInfo,
            CreationParams creationParams,
            int i,
            IEnterpriseContainerCallback iEnterpriseContainerCallback) {
        mSetupCallback = iEnterpriseContainerCallback;
        return createContainer(contextInfo, creationParams, i);
    }

    public final boolean deleteHomeShortcutFromPersonal(
            ContextInfo contextInfo, String str, String str2) {
        if (!SemPersonaManager.isKnoxVersionSupported(
                FrameworkStatsLog
                        .CAMERA_SHOT_LATENCY_REPORTED__MODE__CONTROL_DS_MODE_AI_CLEAR_ZOOM_MERGE_ZSL_ANCHOR_6)) {
            Log.d("KnoxMUMContainerPolicy", "Only above Knox version 2.7 can support");
            return false;
        }
        ContextInfo enforceContainerOwnershipPermission =
                enforceContainerOwnershipPermission(
                        contextInfo,
                        new ArrayList(
                                Arrays.asList(
                                        "com.samsung.android.knox.permission.KNOX_CONTAINER")));
        Log.i("KnoxMUMContainerPolicy", " removeShortcutFromPersonal");
        ComponentName componentName =
                (str2 == null || str2.length() <= 0) ? null : new ComponentName(str, str2);
        Intent intent = new Intent("android.intent.action.MAIN");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (componentName == null) {
                intent.setPackage(str);
                ResolveInfo resolveInfo =
                        this.mPackageManager.queryIntentActivities(intent, 0).get(0);
                if (resolveInfo != null) {
                    componentName = new ComponentName(str, resolveInfo.activityInfo.name);
                    intent.setComponent(componentName);
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
            intent.setComponent(componentName);
            int callingOrCurrentUserId =
                    Utils.getCallingOrCurrentUserId(enforceContainerOwnershipPermission);
            Bundle bundle = new Bundle();
            bundle.putParcelable("component", componentName);
            bundle.putInt("userid", callingOrCurrentUserId);
            mContext.getContentResolver()
                    .call(REMOVE_SHORTCUT_CONTENT_URI, "remove_shortcut", (String) null, bundle);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return true;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void doSelfUninstall() {
        enforceCallingCheckPermission(
                mContext,
                new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CONTAINER")));
        final int callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            final String nameForUid = mContext.getPackageManager().getNameForUid(callingUid);
            new Thread() { // from class:
                           // com.android.server.enterprise.container.KnoxMUMContainerPolicy.5
                @Override // java.lang.Thread, java.lang.Runnable
                public final void run() {
                    ActivityManager activityManager = KnoxMUMContainerPolicy.this.mActivityManager;
                    if (activityManager != null) {
                        activityManager.forceStopPackageAsUser(
                                nameForUid, UserHandle.getUserId(callingUid));
                    }
                    try {
                        Thread.sleep(500L);
                    } catch (InterruptedException unused) {
                    }
                    PackageManagerAdapter packageManagerAdapter =
                            PackageManagerAdapter.getInstance(KnoxMUMContainerPolicy.mContext);
                    String str = nameForUid;
                    int userId = UserHandle.getUserId(callingUid);
                    packageManagerAdapter.getClass();
                    PackageManagerAdapter.deletePackageAsUser(userId, 0, str);
                }
            }.start();
        } catch (Exception e) {
            RCPManagerService$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("Fail doSelfUninstall "), "KnoxMUMContainerPolicy");
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
            printWriter.println("Permission Denial: can't dump KnoxContainerManager");
            return;
        }
        List users = this.mUms.getUsers(true);
        if (users == null) {
            Log.i("KnoxMUMContainerPolicy", "persona list is null");
            return;
        }
        Iterator it = users.iterator();
        while (it.hasNext()) {
            int i = ((UserInfo) it.next()).id;
            printWriter.println("Persona:" + i);
            KnoxConfigurationType filterTypeByContainerId = filterTypeByContainerId(i);
            if (filterTypeByContainerId != null) {
                printWriter.write(" Object dump :{ mName :" + filterTypeByContainerId.getName());
                printWriter.write(" mVersion :" + filterTypeByContainerId.getVersion());
                printWriter.write(
                        " mPasswordMinimumNonLetters :"
                                + filterTypeByContainerId.getPasswordMinimumNonLetters());
                printWriter.write(
                        " mPasswordMinimumLetters : "
                                + filterTypeByContainerId.getPasswordMinimumLetters());
                printWriter.write(
                        " mPasswordMinimumNumeric : "
                                + filterTypeByContainerId.getPasswordMinimumNumeric());
                printWriter.write(
                        " mPasswordMinimumUpperCase : "
                                + filterTypeByContainerId.getPasswordMinimumUpperCase());
                printWriter.write(
                        " mPasswordMinimumLowerCase : "
                                + filterTypeByContainerId.getPasswordMinimumLowerCase());
                printWriter.write(
                        " mPasswordMinimumSymbols : "
                                + filterTypeByContainerId.getPasswordMinimumSymbols());
                printWriter.write(
                        " mPasswordQuality : " + filterTypeByContainerId.getPasswordQuality());
                printWriter.write(
                        " mMaximumFailedPasswordsForWipe : "
                                + filterTypeByContainerId.getMaximumFailedPasswordsForWipe());
                printWriter.write(" mManagedType : " + filterTypeByContainerId.getManagedType());
                printWriter.write(
                        " mMaximumTimeToLock : " + filterTypeByContainerId.getMaximumTimeToLock());
                printWriter.write(
                        " mCustomBadgeIcon : " + filterTypeByContainerId.getCustomBadgeIcon());
                printWriter.write(
                        " mCustomHomeScreenWallpaper : "
                                + filterTypeByContainerId.getCustomHomeScreenWallpaper());
                printWriter.write(
                        " mEC : " + filterTypeByContainerId.isCustomizedContainerEnabled());
                printWriter.write(
                        " mNameIcon : " + filterTypeByContainerId.getCustomizedContainerNameIcon());
                printWriter.write(
                        " mECName  : " + filterTypeByContainerId.getCustomizedContainerName());
                printWriter.write(
                        " mECIcon : " + filterTypeByContainerId.getCustomizedContainerIcon());
                printWriter.write(
                        " mECBadge : " + filterTypeByContainerId.getCustomizedContainerBadge());
                printWriter.write(
                        " mCustomLockScreenWallpaper : "
                                + filterTypeByContainerId.getCustomLockScreenWallpaper());
                printWriter.write(
                        " mCustomStatusLabel : " + filterTypeByContainerId.getCustomStatusLabel());
                printWriter.write(
                        " mCustomStatusIcon : " + filterTypeByContainerId.getCustomStatusIcon());
                printWriter.write(
                        " mAppInstallationList : "
                                + filterTypeByContainerId.getAppInstallationList());
                printWriter.write(
                        " mForbiddenStrings : " + filterTypeByContainerId.getForbiddenStrings());
                printWriter.write(
                        " mProtectedList : " + filterTypeByContainerId.getProtectedPackageList());
                printWriter.write(
                        " mGoogleAppsList : " + filterTypeByContainerId.getGoogleAppsList());
                printWriter.write(
                        " mMaximumCharacterOccurences : "
                                + filterTypeByContainerId.getMaximumCharacterOccurences());
                printWriter.write(
                        " mMaximumCharacterSequenceLength : "
                                + filterTypeByContainerId.getMaximumCharacterSequenceLength());
                printWriter.write(
                        " mMaximumNumericSequenceLength : "
                                + filterTypeByContainerId.getMaximumNumericSequenceLength());
                printWriter.write(
                        " mPasswordMinimumLength : "
                                + filterTypeByContainerId.getPasswordMinimumLength());
                printWriter.write(
                        " mSimplePasswordEnabled : "
                                + filterTypeByContainerId.getSimplePasswordEnabled());
                printWriter.write(
                        " mMultifactorAuthEnabled : "
                                + filterTypeByContainerId.isMultifactorAuthenticationEnforced());
                printWriter.write(
                        " mPasswordPattern : "
                                + filterTypeByContainerId.getRequiredPwdPatternRestrictions());
                printWriter.write(
                        " mAllowMultiwindowMode : "
                                + filterTypeByContainerId.isMultiwindowModeAllowed());
                printWriter.write(
                        " mAllowTaskManager : " + filterTypeByContainerId.isTaskManagerAllowed());
                printWriter.write(
                        " mAllowUSBDebugging : " + filterTypeByContainerId.isUSBDebuggingAllowed());
                printWriter.write(" RCP Data sync settings : ");
                dumpRCPSettings(printWriter, filterTypeByContainerId.getDataSyncPolicy());
                printWriter.write(" RCP Allow User change Data sync settings : ");
                dumpRCPSettings(
                        printWriter, filterTypeByContainerId.getAllowChangeDataSyncPolicy());
                printWriter.write(" RCP Notification sync settings : ");
                dumpRCPSettings(printWriter, filterTypeByContainerId.getNotificationSyncPolicy());
                printWriter.write("\n");
            }
        }
    }

    public final boolean enableBluetooth(ContextInfo contextInfo, boolean z, Bundle bundle) {
        Context context;
        boolean z2 = false;
        if (BluetoothAdapter.getDefaultAdapter() == null) {
            Log.d(
                    "KnoxMUMContainerPolicy",
                    "enableBluetooth: bluetooth adapter is null! BT not supported on this device!");
            return false;
        }
        ContextInfo enforceContainerOwnershipPermission =
                enforceContainerOwnershipPermission(
                        contextInfo,
                        new ArrayList(
                                Arrays.asList(
                                        "com.samsung.android.knox.permission.KNOX_CONTAINER")));
        if (enforceContainerOwnershipPermission == null) {
            return false;
        }
        int i = contextInfo.mContainerId;
        ComponentName componentName =
                new ComponentName(
                        "com.android.bluetooth",
                        "com.android.bluetooth.opp.BluetoothOppLauncherActivity");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                z2 =
                        setFeatureAccessPermission(
                                enforceContainerOwnershipPermission, "Bluetooth", z);
                Log.d("KnoxMUMContainerPolicy", "enableBluetooth status - " + z2);
                IPackageManager packageManager = AppGlobals.getPackageManager();
                if (packageManager != null) {
                    if (packageManager.getPackageInfo("com.android.bluetooth", 64L, i) == null
                            && (context = mContext) != null) {
                        try {
                            try {
                                context.getPackageManager()
                                        .installExistingPackageAsUser("com.android.bluetooth", i);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } catch (Exception e2) {
                            Log.d(
                                    "KnoxMUMContainerPolicy",
                                    "Exception occured in installing bluetooth package inside"
                                        + " container: "
                                            + e2.getMessage());
                        }
                    }
                    if (z) {
                        packageManager.setComponentEnabledSetting(
                                componentName, 1, 0, i, (String) null);
                    } else {
                        packageManager.setComponentEnabledSetting(
                                componentName, 2, 0, i, (String) null);
                    }
                }
                if (z2) {
                    restartBluetooth$1();
                }
            } catch (Exception e3) {
                e3.printStackTrace();
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return z2;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x004b A[Catch: all -> 0x0043, Exception -> 0x0045, TryCatch #1 {Exception -> 0x0045, blocks: (B:3:0x0019, B:5:0x0020, B:7:0x0028, B:10:0x004b, B:14:0x0055), top: B:2:0x0019, outer: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0055 A[Catch: all -> 0x0043, Exception -> 0x0045, TRY_LEAVE, TryCatch #1 {Exception -> 0x0045, blocks: (B:3:0x0019, B:5:0x0020, B:7:0x0028, B:10:0x004b, B:14:0x0055), top: B:2:0x0019, outer: #0 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean enableExternalStorage(
            com.samsung.android.knox.ContextInfo r9, boolean r10) {
        /*
            r8 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            java.lang.String r1 = "com.samsung.android.knox.permission.KNOX_CONTAINER"
            java.lang.String[] r1 = new java.lang.String[]{r1}
            java.util.List r1 = java.util.Arrays.asList(r1)
            r0.<init>(r1)
            com.samsung.android.knox.ContextInfo r9 = r8.enforceContainerOwnershipPermission(r9, r0)
            long r0 = android.os.Binder.clearCallingIdentity()
            r2 = 0
            boolean r3 = r8.isExternalStorageEnabled(r9)     // Catch: java.lang.Throwable -> L43 java.lang.Exception -> L45
            r4 = 1
            if (r10 == r3) goto L41
            java.lang.String r3 = "ExternalStorage"
            boolean r8 = r8.setFeatureAccessPermission(r9, r3, r10)     // Catch: java.lang.Throwable -> L43 java.lang.Exception -> L45
            if (r8 == 0) goto L47
            java.lang.String r8 = "knox.container.proxy.POLICY_SDCARD_POLICY_CHANGED"
            int r3 = r9.mContainerId     // Catch: java.lang.Throwable -> L43 java.lang.Exception -> L45
            android.os.Bundle r5 = new android.os.Bundle     // Catch: java.lang.Throwable -> L43 java.lang.Exception -> L45
            r5.<init>()     // Catch: java.lang.Throwable -> L43 java.lang.Exception -> L45
            java.lang.String r6 = "android.intent.extra.user_handle"
            r5.putInt(r6, r3)     // Catch: java.lang.Throwable -> L43 java.lang.Exception -> L45
            long r6 = android.os.Binder.clearCallingIdentity()     // Catch: java.lang.Throwable -> L43 java.lang.Exception -> L45
            com.samsung.android.knox.ContainerProxy.sendPolicyUpdate(r8, r5)     // Catch: java.lang.Throwable -> L43 java.lang.Exception -> L45
            android.os.Binder.restoreCallingIdentity(r6)     // Catch: java.lang.Throwable -> L43 java.lang.Exception -> L45
        L41:
            r2 = r4
            goto L47
        L43:
            r8 = move-exception
            goto L6b
        L45:
            r8 = move-exception
            goto L62
        L47:
            r8 = 128(0x80, float:1.794E-43)
            if (r10 == 0) goto L55
            com.android.server.pm.PersonaManagerService r10 = getPersonaManagerLocked()     // Catch: java.lang.Throwable -> L43 java.lang.Exception -> L45
            int r9 = r9.mContainerId     // Catch: java.lang.Throwable -> L43 java.lang.Exception -> L45
            r10.setAttributes(r9, r8)     // Catch: java.lang.Throwable -> L43 java.lang.Exception -> L45
            goto L5e
        L55:
            com.android.server.pm.PersonaManagerService r10 = getPersonaManagerLocked()     // Catch: java.lang.Throwable -> L43 java.lang.Exception -> L45
            int r9 = r9.mContainerId     // Catch: java.lang.Throwable -> L43 java.lang.Exception -> L45
            r10.clearAttributes(r9, r8)     // Catch: java.lang.Throwable -> L43 java.lang.Exception -> L45
        L5e:
            android.os.Binder.restoreCallingIdentity(r0)
            goto L6a
        L62:
            java.lang.String r9 = "KnoxMUMContainerPolicy"
            java.lang.String r10 = "Fail enableExternalStorage "
            android.util.Log.e(r9, r10, r8)     // Catch: java.lang.Throwable -> L43
            goto L5e
        L6a:
            return r2
        L6b:
            android.os.Binder.restoreCallingIdentity(r0)
            throw r8
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.container.KnoxMUMContainerPolicy.enableExternalStorage(com.samsung.android.knox.ContextInfo,"
                    + " boolean):boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0048  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean enableNFC(
            com.samsung.android.knox.ContextInfo r12, boolean r13, android.os.Bundle r14) {
        /*
            Method dump skipped, instructions count: 269
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.container.KnoxMUMContainerPolicy.enableNFC(com.samsung.android.knox.ContextInfo,"
                    + " boolean, android.os.Bundle):boolean");
    }

    public final boolean enableUsbAccess(ContextInfo contextInfo, boolean z, Bundle bundle) {
        ContextInfo enforceContainerOwnershipPermission =
                enforceContainerOwnershipPermission(
                        contextInfo,
                        new ArrayList(
                                Arrays.asList(
                                        "com.samsung.android.knox.permission.KNOX_CONTAINER")));
        boolean z2 = false;
        if (enforceContainerOwnershipPermission == null) {
            return false;
        }
        int i = contextInfo.mContainerId;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                z2 = setFeatureAccessPermission(enforceContainerOwnershipPermission, "USB", z);
                Log.d("KnoxMUMContainerPolicy", "enableUsbAccess status - " + z2);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return z2;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0043 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void enforceAppSeparationPermission(int r4, java.lang.String r5) {
        /*
            r3 = this;
            java.lang.String r0 = "KnoxMUMContainerPolicy"
            java.lang.String r1 = "com.samsung.android.knox.permission.KNOX_APP_SEPARATION"
            int r4 = android.os.UserHandle.getUserId(r4)
            android.content.pm.IPackageManager r2 = android.app.AppGlobals.getPackageManager()     // Catch: java.lang.Exception -> L48
            int r4 = r2.checkPermission(r1, r5, r4)     // Catch: java.lang.Exception -> L48
            if (r4 != 0) goto L4d
            long r4 = android.os.Binder.clearCallingIdentity()     // Catch: java.lang.Exception -> L48
            android.app.admin.DevicePolicyManager r3 = r3.getDevicePolicyService()     // Catch: java.lang.Throwable -> L29 java.lang.Exception -> L2b
            android.content.ComponentName r3 = r3.getDeviceOwnerComponentOnAnyUser()     // Catch: java.lang.Throwable -> L29 java.lang.Exception -> L2b
            if (r3 == 0) goto L2d
            java.lang.String r3 = r3.getPackageName()     // Catch: java.lang.Throwable -> L29 java.lang.Exception -> L2b
            android.os.Binder.restoreCallingIdentity(r4)     // Catch: java.lang.Exception -> L48
            goto L3c
        L29:
            r3 = move-exception
            goto L44
        L2b:
            r3 = move-exception
            goto L31
        L2d:
            android.os.Binder.restoreCallingIdentity(r4)     // Catch: java.lang.Exception -> L48
            goto L3a
        L31:
            r3.printStackTrace()     // Catch: java.lang.Throwable -> L29
            java.lang.String r3 = "Failed to retrieve DO component on device"
            android.util.Log.e(r0, r3)     // Catch: java.lang.Throwable -> L29
            goto L2d
        L3a:
            java.lang.String r3 = ""
        L3c:
            r4 = 0
            int r3 = r2.checkPermission(r1, r3, r4)     // Catch: java.lang.Exception -> L48
            if (r3 != 0) goto L4d
            return
        L44:
            android.os.Binder.restoreCallingIdentity(r4)     // Catch: java.lang.Exception -> L48
            throw r3     // Catch: java.lang.Exception -> L48
        L48:
            java.lang.String r3 = "Error in checking AppSeparation Permission"
            android.util.Log.d(r0, r3)
        L4d:
            java.lang.SecurityException r3 = new java.lang.SecurityException
            java.lang.String r4 = "Knox App Separation Permission"
            r3.<init>(r4)
            throw r3
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.container.KnoxMUMContainerPolicy.enforceAppSeparationPermission(int,"
                    + " java.lang.String):void");
    }

    public final ContextInfo enforceContainerOwnershipPermission(
            ContextInfo contextInfo, List list) {
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(mContext);
        }
        return this.mEDM.enforceContainerOwnerShipPermissionByContext(contextInfo, list);
    }

    public final boolean enforceMultifactorAuthentication(ContextInfo contextInfo, boolean z) {
        Log.i("KnoxMUMContainerPolicy", "enforceMultifactorAuthentication is called....");
        ContextInfo enforceContainerOwnershipPermission =
                enforceContainerOwnershipPermission(
                        contextInfo,
                        new ArrayList(
                                Arrays.asList(
                                        "com.samsung.android.knox.permission.KNOX_CONTAINER")));
        PasswordPolicy passwordPolicy =
                (PasswordPolicy) ServiceManager.getService("password_policy");
        boolean z2 = false;
        if (passwordPolicy != null) {
            try {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                z2 =
                        passwordPolicy.setMultifactorAuthenticationEnabled(
                                enforceContainerOwnershipPermission, z);
                Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (Exception e) {
                Log.w(
                        "KnoxMUMContainerPolicy",
                        "Failed at ContainerConfigurationPolicy API"
                            + " enforceMultifactorAuthentication ",
                        e);
            }
        }
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "enforceMultifactorAuthentication result - ", "KnoxMUMContainerPolicy", z2);
        return z2;
    }

    public final ContextInfo enforceSecurityPermission(ContextInfo contextInfo, List list) {
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(mContext);
        }
        return this.mEDM.enforceActiveAdminPermissionByContext(contextInfo, list);
    }

    public final ContextInfo enforceWifiPermission(ContextInfo contextInfo) {
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(mContext);
        }
        return this.mEDM.enforceActiveAdminPermissionByContext(
                contextInfo,
                new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_WIFI")));
    }

    public final KnoxConfigurationType filterTypeByContainerId(int i) {
        Iterator it = ((ArrayList) this.mTypeList).iterator();
        while (it.hasNext()) {
            KnoxConfigurationType knoxConfigurationType = (KnoxConfigurationType) it.next();
            Iterator it2 = knoxConfigurationType.getPersonaList().iterator();
            while (it2.hasNext()) {
                if (((Integer) it2.next()).intValue() == i) {
                    return knoxConfigurationType;
                }
            }
        }
        return null;
    }

    public final int forceResetPassword(ContextInfo contextInfo, String str, int i) {
        if (contextInfo == null) {
            return -2;
        }
        Log.d(
                "KnoxMUMContainerPolicy",
                "forceResetPassword: containerId: " + contextInfo.mContainerId);
        enforceContainerOwnershipPermission(
                contextInfo,
                new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CONTAINER")));
        long clearCallingIdentity = Binder.clearCallingIdentity();
        int i2 = contextInfo.mContainerId;
        int activePasswordQuality = new LockPatternUtils(mContext).getActivePasswordQuality(i2);
        Log.d("KnoxMUMContainerPolicy", "UCS enabled for user = " + i2);
        StringBuilder sb = new StringBuilder("current quality = ");
        sb.append(activePasswordQuality);
        VpnManagerService$$ExternalSyntheticOutline0.m(
                sb, ", SMART CARD Quality = 458752", "KnoxMUMContainerPolicy");
        if (activePasswordQuality == 458752) {
            Log.d(
                    "KnoxMUMContainerPolicy",
                    "forceResetPassword declined because Lock Quality set to Smartcard for user = "
                            + i2);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return -2;
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        SDPLog.i("Reset Password requested for container " + contextInfo.mContainerId);
        SDPLog.p("cxtInfo", contextInfo, "resetPwdKey", str, "timeout", Integer.valueOf(i));
        if (this.mLockSettingsService == null) {
            this.mLockSettingsService =
                    ILockSettings.Stub.asInterface(ServiceManager.getService("lock_settings"));
        }
        if (this.mLockSettingsService != null && getDarManagerService() != null) {
            this.mDarManagerService.getClass();
        }
        return -2;
    }

    public final Bundle getAppSeparationConfig() {
        try {
            ContentValues contentValues = new ContentValues();
            if (this.mEdmStorageProvider.getCount("AppSeparationTable", contentValues) == 0) {
                Log.d("KnoxMUMContainerPolicy", "getAppSeparationConfig(): no record. Return null");
                return null;
            }
            Bundle bundle = new Bundle();
            boolean z = true;
            if (this.mEdmStorageProvider.getInt(
                            contentValues, "AppSeparationTable", "AppSeparationOutside")
                    != 1) {
                z = false;
            }
            bundle.putBoolean("APP_SEPARATION_OUTSIDE", z);
            bundle.putStringArrayList(
                    "APP_SEPARATION_APP_LIST",
                    convertStringCommaDelimitedToList(
                            this.mEdmStorageProvider.getString(
                                    contentValues, "AppSeparationTable", "AppSeparationApplist")));
            bundle.putStringArrayList(
                    "APP_SEPARATION_COEXISTANCE_LIST",
                    convertStringCommaDelimitedToList(
                            this.mEdmStorageProvider.getString(
                                    contentValues,
                                    "AppSeparationTable",
                                    "AppSeparationCoexistenceList")));
            return bundle;
        } catch (Exception e) {
            AudioServiceExt$ResetSettingsReceiver$$ExternalSyntheticOutline0.m(
                    e,
                    new StringBuilder("getAppSeparationConfig() exception: "),
                    "KnoxMUMContainerPolicy");
            return null;
        }
    }

    public final IApplicationPolicy getAppService() {
        if (this.mAppService == null) {
            this.mAppService =
                    IApplicationPolicy.Stub.asInterface(
                            ServiceManager.getService("application_policy"));
        }
        return this.mAppService;
    }

    public final List getConfigurationType(ContextInfo contextInfo, int i) {
        int callingUid;
        if (contextInfo == null || contextInfo.mCallerUid <= 0) {
            callingUid = Binder.getCallingUid();
        } else {
            enforceContainerOwnershipPermission(
                    contextInfo,
                    new ArrayList(
                            Arrays.asList("com.samsung.android.knox.permission.KNOX_CONTAINER")));
            callingUid = contextInfo.mCallerUid;
        }
        try {
            int userId = UserHandle.getUserId(callingUid);
            if (getService() != null && getService().exists(userId)) {
                if (i == userId) {
                    return Arrays.asList(filterTypeByContainerId(i));
                }
                Log.d("KnoxMUMContainerPolicy", "Caller inside persona ? : false throw exception");
                throw new SecurityException("No priviledge on containerId ");
            }
            KnoxConfigurationType filterTypeByContainerId = filterTypeByContainerId(i);
            if (callingUid != 1000
                    && callingUid != 5250
                    && (filterTypeByContainerId == null
                            || (filterTypeByContainerId.getAdminUid() != callingUid
                                    && filterTypeByContainerId.getAdminUid() != 0))) {
                return null;
            }
            return Arrays.asList(filterTypeByContainerId);
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("Exception: "), "KnoxMUMContainerPolicy");
            return null;
        }
    }

    public final List getConfigurationTypeByName(ContextInfo contextInfo, String str) {
        int callingUid;
        String str2;
        if (contextInfo == null || contextInfo.mCallerUid <= 0) {
            callingUid = Binder.getCallingUid();
        } else {
            enforceSecurityPermission(
                    contextInfo,
                    new ArrayList(
                            Arrays.asList("com.samsung.android.knox.permission.KNOX_CONTAINER")));
            callingUid = contextInfo.mCallerUid;
        }
        int callingPid = Binder.getCallingPid();
        ActivityManager activityManager = (ActivityManager) mContext.getSystemService("activity");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                str2 = activityManager.getPackageFromAppProcesses(callingPid);
            } catch (Exception e) {
                e.printStackTrace();
                Binder.restoreCallingIdentity(clearCallingIdentity);
                str2 = null;
            }
            Iterator it = ((ArrayList) this.mTypeList).iterator();
            while (it.hasNext()) {
                KnoxConfigurationType knoxConfigurationType = (KnoxConfigurationType) it.next();
                if (knoxConfigurationType.getName().equals(str)) {
                    StringBuilder m =
                            DumpUtils$$ExternalSyntheticOutline0.m(
                                    "getConfigurationTypeByName type ", str, " adminUid ");
                    m.append(knoxConfigurationType.getAdminUid());
                    m.append(" callingUid ");
                    m.append(callingUid);
                    Log.d("KnoxMUMContainerPolicy", m.toString());
                    return ("com.samsung.android.knox.containercore".equals(str2)
                                    || "com.android.managedprovisioning".equals(str2)
                                    || callingUid == 1000
                                    || knoxConfigurationType.getAdminUid() == 0
                                    || knoxConfigurationType.getAdminUid() == callingUid)
                            ? Arrays.asList(knoxConfigurationType)
                            : Arrays.asList(knoxConfigurationType);
                }
            }
            return null;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final List getConfigurationTypes(ContextInfo contextInfo) {
        int callingUid;
        if (contextInfo == null || contextInfo.mCallerUid <= 0) {
            callingUid = Binder.getCallingUid();
        } else {
            enforceSecurityPermission(
                    contextInfo,
                    new ArrayList(
                            Arrays.asList("com.samsung.android.knox.permission.KNOX_CONTAINER")));
            callingUid = contextInfo.mCallerUid;
        }
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                callingUid, "KnoxConfigurationType: input uid: ", "KnoxMUMContainerPolicy");
        Iterator it = ((ArrayList) this.mTypeList).iterator();
        ArrayList arrayList = null;
        while (it.hasNext()) {
            KnoxConfigurationType knoxConfigurationType = (KnoxConfigurationType) it.next();
            if (knoxConfigurationType.getAdminUid() == callingUid
                    || knoxConfigurationType.getAdminUid() == 0) {
                Log.d(
                        "KnoxMUMContainerPolicy",
                        "KnoxConfigurationType: name, uid: "
                                + knoxConfigurationType.getName()
                                + " "
                                + knoxConfigurationType.getAdminUid());
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                if (!"secure-folder".equals(knoxConfigurationType.getName())) {
                    arrayList.add(knoxConfigurationType);
                }
            }
        }
        return arrayList;
    }

    public final ContainerCreationParams getContainerCreationParams(int i) {
        ContainerCreationParams containerCreationParams;
        checkCallerPermissionFor("getContainerCreationParams");
        synchronized (this.mParamsList) {
            try {
                if (((ArrayList) this.mParamsList).isEmpty()) {
                    containerCreationParams = null;
                } else {
                    Iterator it = ((ArrayList) this.mParamsList).iterator();
                    containerCreationParams = null;
                    while (it.hasNext()) {
                        ContainerCreationParams containerCreationParams2 =
                                (ContainerCreationParams) it.next();
                        if (containerCreationParams2.getConfigurationType() != null
                                && containerCreationParams2
                                        .getConfigurationType()
                                        .getPersonaList()
                                        .contains(Integer.valueOf(i))) {
                            containerCreationParams = containerCreationParams2;
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (containerCreationParams != null) {
            return containerCreationParams.clone();
        }
        return null;
    }

    public final List getContainers(int i) {
        Log.d("KnoxMUMContainerPolicy", "getContainers: admin uid: " + i);
        ArrayList arrayList = new ArrayList();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                List<UserInfo> users =
                        getUserManagerService() != null
                                ? getUserManagerService().getUsers(true)
                                : null;
                if (users != null) {
                    for (UserInfo userInfo : users) {
                        if (userInfo.isManagedProfile()) {
                            int mUMContainerOwnerUid =
                                    this.mEdmStorageProvider.getMUMContainerOwnerUid(userInfo.id);
                            Log.d(
                                    "KnoxMUMContainerPolicy",
                                    "Persona found with id , creator uid, passed uid: "
                                            + userInfo.id
                                            + " "
                                            + mUMContainerOwnerUid
                                            + " "
                                            + i);
                            if (i == mUMContainerOwnerUid) {
                                arrayList.add(Integer.valueOf(userInfo.id));
                            }
                        }
                    }
                }
            } catch (Exception e) {
                Log.d(
                        "KnoxMUMContainerPolicy",
                        "getContainers exception: " + Log.getStackTraceString(e));
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return arrayList;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final List getContainers(ContextInfo contextInfo) {
        int callingUid;
        if (contextInfo == null || contextInfo.mCallerUid <= 0) {
            callingUid = Binder.getCallingUid();
        } else {
            enforceSecurityPermission(
                    contextInfo,
                    new ArrayList(
                            Arrays.asList("com.samsung.android.knox.permission.KNOX_CONTAINER")));
            callingUid = contextInfo.mCallerUid;
        }
        return getContainers(callingUid);
    }

    public final String getCustomResource(int i, String str) {
        String m =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        i, "/data/misc/container3.0/", "/");
        String m$1 =
                "custom-container-icon".equals(str)
                        ? ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(
                                m, "profileIcon.png")
                        : "custom-badge-icon".equals(str)
                                ? ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(
                                        m, "badgeIcon.png")
                                : "custom-name-icon".equals(str)
                                        ? ConnectivityModuleConnector$$ExternalSyntheticOutline0
                                                .m$1(m, "nameIcon.png")
                                        : null;
        if (DEBUG) {
            DualAppManagerService$$ExternalSyntheticOutline0.m(
                    "PATH = ", m$1, "KnoxMUMContainerPolicy");
        }
        if (m$1 != null && BatteryService$$ExternalSyntheticOutline0.m45m(m$1)) {
            return m$1;
        }
        KnoxConfigurationType filterTypeByContainerId = filterTypeByContainerId(i);
        if (filterTypeByContainerId != null
                && filterTypeByContainerId.isCustomizedContainerEnabled()) {
            if ("custom-container-icon".equals(str)) {
                return filterTypeByContainerId.getCustomizedContainerIcon();
            }
            if ("custom-badge-icon".equals(str)) {
                return filterTypeByContainerId.getCustomizedContainerBadge();
            }
            if ("custom-name-icon".equals(str)) {
                return filterTypeByContainerId.getCustomizedContainerNameIcon();
            }
            if ("custom-lock-screen-wallpaper".equals(str)) {
                return filterTypeByContainerId.getCustomLockScreenWallpaper();
            }
            if ("custom-home-screen-wallpaper".equals(str)) {
                return filterTypeByContainerId.getCustomHomeScreenWallpaper();
            }
        }
        return null;
    }

    public final DarManagerService getDarManagerService() {
        if (this.mDarManagerService == null) {
            this.mDarManagerService = (DarManagerService) ServiceManager.getService("dar");
        }
        return this.mDarManagerService;
    }

    public final List getDefaultConfigurationTypes() {
        checkCallerPermissionFor("getDefaultConfigurationTypes");
        Iterator it = ((ArrayList) this.mTypeList).iterator();
        ArrayList arrayList = null;
        while (it.hasNext()) {
            KnoxConfigurationType knoxConfigurationType = (KnoxConfigurationType) it.next();
            if (knoxConfigurationType.getAdminUid() == 0) {
                Log.d(
                        "KnoxMUMContainerPolicy",
                        "KnoxConfigurationType: name, uid: "
                                + knoxConfigurationType.getName()
                                + " "
                                + knoxConfigurationType.getAdminUid());
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                arrayList.add(knoxConfigurationType);
            }
        }
        return arrayList;
    }

    public final int getDeviceOwnerUid() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                ComponentName deviceOwnerComponentOnAnyUser =
                        getDevicePolicyService().getDeviceOwnerComponentOnAnyUser();
                if (deviceOwnerComponentOnAnyUser != null) {
                    return mContext.getPackageManager()
                            .getApplicationInfo(deviceOwnerComponentOnAnyUser.getPackageName(), 0)
                            .uid;
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                Log.e("KnoxMUMContainerPolicy", "Failed to get application info for DO component.");
            } catch (Exception e2) {
                e2.printStackTrace();
                Log.e("KnoxMUMContainerPolicy", "Failed to retrieve DO component on device");
            }
            return -1;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final DevicePolicyManager getDevicePolicyService() {
        if (this.mDpm == null) {
            this.mDpm = (DevicePolicyManager) mContext.getSystemService("device_policy");
        }
        return this.mDpm;
    }

    public final boolean getEnforceAuthForContainer(ContextInfo contextInfo) {
        Log.e("KnoxMUMContainerPolicy", "getEnforceAuthForContainer failed > returning true");
        return true;
    }

    public final Bundle getFIDOInfo(ContextInfo contextInfo) {
        Log.d("KnoxMUMContainerPolicy", "getFIDOInfo()");
        enforceContainerOwnershipPermission(
                contextInfo,
                new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CONTAINER")));
        int i = contextInfo.mCallerUid;
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("cid", Integer.valueOf(contextInfo.mContainerId));
            if (this.mEdmStorageProvider.getCount("KnoxFIDOSettingTable", contentValues) == 0) {
                Log.d("KnoxMUMContainerPolicy", "getFIDOInfo(): no record. Return null");
                return null;
            }
            Bundle bundle = new Bundle();
            bundle.putString(
                    "fido_request_uri",
                    this.mEdmStorageProvider.getString(
                            contentValues, "KnoxFIDOSettingTable", "request"));
            bundle.putString(
                    "fido_response_uri",
                    this.mEdmStorageProvider.getString(
                            contentValues, "KnoxFIDOSettingTable", "respond"));
            return bundle;
        } catch (Exception e) {
            AudioServiceExt$ResetSettingsReceiver$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("getFIDOInfo() exception: "), "KnoxMUMContainerPolicy");
            return null;
        }
    }

    public final boolean getFeatureAccessPermission(int i, String str) {
        String[] strArr = {"featureValue"};
        boolean z = false;
        try {
            int uid = UserHandle.getUid(i, this.mEdmStorageProvider.getMUMContainerOwnerUid(i));
            ContentValues contentValues = new ContentValues();
            contentValues.put("adminUid", Integer.valueOf(uid));
            contentValues.put("featureType", str);
            ArrayList arrayList =
                    (ArrayList)
                            this.mEdmStorageProvider.getValuesList(
                                    "KnoxFeatureAccess", strArr, contentValues);
            if (arrayList.size() <= 0) {
                boolean equals = "LAYOUT_SWITCH".equals(str);
                if ("Bluetooth".equals(str) || "USB".equals(str) || "NFC".equals(str)) {
                    return true;
                }
                return equals;
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                String asString = ((ContentValues) it.next()).getAsString("featureValue");
                if (asString != null && asString.length() > 0) {
                    z = Boolean.parseBoolean(asString);
                }
            }
            return z;
        } catch (Exception e) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                    e, "getFeatureAccessPermission exception ", "KnoxMUMContainerPolicy");
            return false;
        }
    }

    public final long getHibernationTimeout(ContextInfo contextInfo) {
        enforceContainerOwnershipPermission(
                contextInfo,
                new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CONTAINER")));
        if (contextInfo == null) {
            Log.e(
                    "KnoxMUMContainerPolicy",
                    "getHibernationTimeout failed > returning default value.");
            return 5000L;
        }
        ContentValues contentValues = new ContentValues();
        KnoxMUMContainerPolicy$$ExternalSyntheticOutline0.m(
                contextInfo.mContainerId,
                contentValues,
                "cid",
                "propertyName",
                "HibernationTimeout");
        contentValues.put(
                "adminUid",
                Integer.valueOf(
                        this.mEdmStorageProvider.getMUMContainerOwnerUid(
                                contextInfo.mContainerId)));
        ArrayList arrayList =
                (ArrayList)
                        this.mEdmStorageProvider.getValuesList(
                                "CONTAINER_POLICY", new String[] {"propertyValue"}, contentValues);
        if (arrayList.size() <= 0) {
            Log.e(
                    "KnoxMUMContainerPolicy",
                    "getHibernationTimeout failed to get value from DB > returning default value");
            return 5000L;
        }
        Log.d(
                "KnoxMUMContainerPolicy",
                "time=- " + ((ContentValues) arrayList.get(0)).getAsString("propertyValue"));
        String asString = ((ContentValues) arrayList.get(0)).getAsString("propertyValue");
        if (asString == null || asString.equalsIgnoreCase("0")) {
            return 0L;
        }
        return Long.parseLong(asString);
    }

    public final List getKnoxCustomBadgePolicy() {
        return mbadgePolicylist;
    }

    public final List getNetworkSSID(ContextInfo contextInfo) {
        ContextInfo enforceWifiPermission = enforceWifiPermission(contextInfo);
        Log.d(
                "KnoxMUMContainerPolicy",
                "getNetworkSSID - adminUid : " + enforceWifiPermission.mCallerUid);
        LinkedList linkedList = new LinkedList();
        Iterator it = ((HashSet) getSSID(enforceWifiPermission.mCallerUid)).iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            if (str == null) {
                str = null;
            } else {
                int length = str.length();
                if (length > 1 && str.charAt(0) == '\"') {
                    int i = length - 1;
                    if (str.charAt(i) == '\"') {
                        str = str.substring(1, i);
                    }
                }
            }
            linkedList.add(str);
        }
        return linkedList;
    }

    public final EnterpriseContainerObject[] getOwnContainers() {
        ArrayList arrayList;
        int callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                List<UserInfo> users =
                        getUserManagerService() != null
                                ? getUserManagerService().getUsers(true)
                                : null;
                if (users != null) {
                    arrayList = new ArrayList();
                    try {
                        for (UserInfo userInfo : users) {
                            int mUMContainerOwnerUid =
                                    this.mEdmStorageProvider.getMUMContainerOwnerUid(userInfo.id);
                            if (DEBUG) {
                                Log.d(
                                        "KnoxMUMContainerPolicy",
                                        "Persona found with id , creator uid, passed uid: "
                                                + userInfo.id
                                                + " "
                                                + mUMContainerOwnerUid
                                                + " "
                                                + callingUid);
                            }
                            if (mUMContainerOwnerUid == callingUid) {
                                EnterpriseContainerObject enterpriseContainerObject =
                                        new EnterpriseContainerObject();
                                enterpriseContainerObject.setContainerId(userInfo.id);
                                enterpriseContainerObject.setContainerAdmin(callingUid);
                                enterpriseContainerObject.setContainerName(
                                        getUserManagerService().getUserInfo(userInfo.id).name);
                                arrayList.add(enterpriseContainerObject);
                            }
                        }
                    } catch (Exception e) {
                        e = e;
                        Log.d(
                                "KnoxMUMContainerPolicy",
                                "getOwnContainers exception: " + Log.getStackTraceString(e));
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return arrayList == null ? null : null;
                    }
                } else {
                    arrayList = null;
                }
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        } catch (Exception e2) {
            e = e2;
            arrayList = null;
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        if (arrayList == null && !arrayList.isEmpty()) {
            return (EnterpriseContainerObject[])
                    arrayList.toArray(new EnterpriseContainerObject[arrayList.size()]);
        }
    }

    public final List getPackageSignaturesFromExternalStorageWhiteList(
            ContextInfo contextInfo, String str) {
        Log.d("KnoxMUMContainerPolicy", "getPackageSignaturesFromExternalStorageWhiteList");
        ContextInfo enforceContainerOwnershipPermission =
                enforceContainerOwnershipPermission(
                        contextInfo,
                        new ArrayList(
                                Arrays.asList(
                                        "com.samsung.android.knox.permission.KNOX_CONTAINER")));
        List arrayList = new ArrayList();
        IApplicationPolicy appService = getAppService();
        if (appService == null) {
            Log.e("KnoxMUMContainerPolicy", "Application PolicyService is not yet ready!!!");
            return arrayList;
        }
        try {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            arrayList =
                    appService.getPackageSignaturesFromExternalStorageWhiteList(
                            enforceContainerOwnershipPermission, str);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return arrayList;
        } catch (RemoteException e) {
            Log.w(
                    "KnoxMUMContainerPolicy",
                    "Failed at ContainerConfigurationPolicy API"
                        + " getPackageSignaturesFromExternalStorageWhiteList ",
                    e);
            return arrayList;
        }
    }

    public final List getPackagesFromExternalStorageBlackList(ContextInfo contextInfo) {
        Log.i("KnoxMUMContainerPolicy", "getPackagesFromExternalStorageBlackList ");
        ContextInfo enforceSecurityPermission =
                enforceSecurityPermission(
                        contextInfo,
                        new ArrayList(
                                Arrays.asList(
                                        "com.samsung.android.knox.permission.KNOX_CONTAINER")));
        if (enforceSecurityPermission != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("adminUid", Integer.valueOf(enforceSecurityPermission.mCallerUid));
            ArrayList arrayList =
                    (ArrayList)
                            this.mEdmStorageProvider.getValuesList(
                                    "KnoxExternalStorageSBABlacklist",
                                    new String[] {"packageName"},
                                    contentValues);
            if (arrayList.size() > 0) {
                ArrayList arrayList2 = new ArrayList();
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    String asString = ((ContentValues) it.next()).getAsString("packageName");
                    if (asString != null) {
                        arrayList2.add(asString);
                    }
                }
                if (DEBUG) {
                    Log.d(
                            "KnoxMUMContainerPolicy",
                            "getPackagesFromExternalStorageSBABlackList SUCCESS : " + arrayList2);
                }
                if (arrayList2.size() > 0) {
                    return arrayList2;
                }
                return null;
            }
        }
        Log.e(
                "KnoxMUMContainerPolicy",
                "getPackagesFromExternalStorageSBABlackList policy returning null");
        return null;
    }

    public final List getPackagesFromExternalStorageWhiteList(ContextInfo contextInfo) {
        Log.i("KnoxMUMContainerPolicy", "getPackagesFromExternalStorageWhiteList ");
        ContextInfo enforceContainerOwnershipPermission =
                enforceContainerOwnershipPermission(
                        contextInfo,
                        new ArrayList(
                                Arrays.asList(
                                        "com.samsung.android.knox.permission.KNOX_CONTAINER")));
        IApplicationPolicy appService = getAppService();
        List list = null;
        if (appService == null) {
            Log.e("KnoxMUMContainerPolicy", "Application PolicyService is not yet ready!!!");
            return null;
        }
        try {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            list = appService.getPackagesFromWhiteList(enforceContainerOwnershipPermission, 2);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return list;
        } catch (RemoteException e) {
            Log.w(
                    "KnoxMUMContainerPolicy",
                    "Failed at ContainerConfigurationPolicy API"
                        + " getPackagesFromExternalStorageWhiteList ",
                    e);
            return list;
        }
    }

    public final List getPackagesFromInstallWhiteList(ContextInfo contextInfo) {
        Log.i("KnoxMUMContainerPolicy", "getPackagesFromInstallWhiteList is called...");
        ContextInfo enforceContainerOwnershipPermission =
                enforceContainerOwnershipPermission(
                        contextInfo,
                        new ArrayList(
                                Arrays.asList(
                                        "com.samsung.android.knox.permission.KNOX_CONTAINER")));
        IApplicationPolicy appService = getAppService();
        List list = null;
        if (appService == null) {
            Log.e("KnoxMUMContainerPolicy", "Application PolicyService is not yet ready!!!");
            return null;
        }
        try {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            list = appService.getPackagesFromWhiteList(enforceContainerOwnershipPermission, 1);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return list;
        } catch (RemoteException e) {
            Log.w(
                    "KnoxMUMContainerPolicy",
                    "Failed at ContainerConfigurationPolicy API getPackagesFromInstallWhiteList ",
                    e);
            return list;
        }
    }

    public final Bundle getProvisioningState() {
        checkCallerPermissionFor("getProvisioningState");
        synchronized (this.mProvisioningLock) {
            try {
                ProvisioningState provisioningState = this.mCurrentProvisioningState;
                if (provisioningState != null) {
                    return provisioningState.toBundle();
                }
                Log.e("KnoxMUMContainerPolicy", "no ongoing provisioning");
                return new Bundle();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final Set getSSID(int i) {
        String string =
                this.mEdmStorageProvider.getString(i, 0, "ContainerOnly_wifiAP", "wifiSSIDforKNOX");
        HashSet hashSet = new HashSet();
        if (string != null && string.length() > 0) {
            for (String str : string.split(",")) {
                hashSet.add(str);
            }
        }
        return hashSet;
    }

    public final SemPersonaManager getService() {
        if (this.mPersona == null) {
            this.mPersona = (SemPersonaManager) mContext.getSystemService("persona");
        }
        return this.mPersona;
    }

    public final int getStatus(ContextInfo contextInfo) {
        enforceContainerOwnershipPermission(
                contextInfo,
                new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CONTAINER")));
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                return getStatusInternal(contextInfo.mContainerId);
            } catch (Exception e) {
                Log.d(
                        "KnoxMUMContainerPolicy",
                        "getStatus exception: " + Log.getStackTraceString(e));
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -1;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final int getStatusInternal(int i) {
        int callingUid = Binder.getCallingUid();
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                callingUid, "getStatusInternal callerUid : ", "KnoxMUMContainerPolicy");
        if (callingUid != 1000) {
            return -1;
        }
        try {
            UserManager userManagerService = getUserManagerService();
            UserInfo userInfo = userManagerService.getUserInfo(i);
            Log.d("KnoxMUMContainerPolicy", "getStatusInternal of user: " + i);
            if (userInfo != null) {
                synchronized (this.mProvisioningLock) {
                    try {
                        ProvisioningState provisioningState = this.mCurrentProvisioningState;
                        if (provisioningState != null && provisioningState.containerId == i) {
                            return 93;
                        }
                        if (userInfo.isSuperLocked()) {
                            return 95;
                        }
                        if (userManagerService.isUserRunning(i)) {
                            return 91;
                        }
                    } finally {
                    }
                }
            }
        } catch (Exception e) {
            EnterpriseVpn$$ExternalSyntheticOutline0.m(
                    e,
                    new StringBuilder("getStatusInternal exception: "),
                    "KnoxMUMContainerPolicy");
        }
        return -1;
    }

    public final UserManager getUserManagerService() {
        if (this.mUms == null) {
            this.mUms = (UserManager) mContext.getSystemService("user");
        }
        return this.mUms;
    }

    public final boolean isBluetoothEnabled(ContextInfo contextInfo) {
        boolean z = false;
        if (contextInfo == null) {
            return false;
        }
        int i = contextInfo.mContainerId;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                z = getFeatureAccessPermission(i, "Bluetooth");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return z;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isBluetoothEnabledBeforeFOTA(ContextInfo contextInfo) {
        boolean z = false;
        if (contextInfo == null) {
            return false;
        }
        int i = contextInfo.mContainerId;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                z = getFeatureAccessPermission(i, "Bluetooth");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return z;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isContactsSharingEnabled(ContextInfo contextInfo) {
        if (contextInfo == null) {
            return false;
        }
        int i = contextInfo.mContainerId;
        if (!SemPersonaManager.isSecureFolderId(i)) {
            return false;
        }
        ContentResolver contentResolver = mContext.getContentResolver();
        StringBuilder sb = new StringBuilder("caller_id_to_show_");
        sb.append(getUserManagerService().getUserInfo(i).name);
        return Settings.System.getIntForUser(contentResolver, sb.toString(), 0, 0) == 1;
    }

    public final boolean isContainerOnlyModeAllowed() {
        if (getDevicePolicyService() != null && getDevicePolicyService().isDeviceManaged()) {
            Log.d(
                    "KnoxMUMContainerPolicy",
                    "isContainerOnlyModeAllowed return false, reason: the device is managed by any"
                        + " device owner. ");
            return false;
        }
        if (getPersonaManagerLocked() == null) {
            return false;
        }
        Iterator it = ((ArrayList) getPersonaManagerLocked().getProfiles(0, false)).iterator();
        while (it.hasNext()) {
            if (((UserInfo) it.next()).isManagedProfile()) {
                Log.d(
                        "KnoxMUMContainerPolicy",
                        "isContainerOnlyModeAllowed return false, reason: managed profile exists on"
                            + " the device. ");
                return false;
            }
        }
        return true;
    }

    public final boolean isEmergencyModeSupported() {
        synchronized (this.mProvisioningLock) {
            try {
                if (this.mCurrentProvisioningState == null && !SemPersonaManager.isDoEnabled(0)) {
                    List users = getUserManagerService().getUsers();
                    for (int i = 0; i < users.size(); i++) {
                        UserInfo userInfo = (UserInfo) users.get(i);
                        if (SemPersonaManager.isKnoxId(userInfo.id)) {
                            int i2 = userInfo.flags;
                            if ((i2 & 16) != 16 || (i2 & 64) == 64) {
                                return false;
                            }
                        }
                    }
                    return true;
                }
                return false;
            } finally {
            }
        }
    }

    public final boolean isExternalStorageEnabled(ContextInfo contextInfo) {
        if (contextInfo == null) {
            return true;
        }
        int i = contextInfo.mContainerId;
        if (!SemPersonaManager.isKnoxId(i)) {
            return true;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return getFeatureAccessPermission(i, "ExternalStorage");
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isLayoutSwitchingAllowed(ContextInfo contextInfo) {
        boolean z = true;
        if (contextInfo == null) {
            return true;
        }
        int i = contextInfo.mContainerId;
        Log.d(
                "KnoxMUMContainerPolicy",
                "isLayoutSwitchingAllowed API is called for personaId - " + i);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                z = getFeatureAccessPermission(i, "LAYOUT_SWITCH");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return z;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isMultifactorAuthenticationEnforced(ContextInfo contextInfo) {
        Log.i("KnoxMUMContainerPolicy", "isMultifactorAuthenticationEnforced is called....");
        PasswordPolicy passwordPolicy =
                (PasswordPolicy) ServiceManager.getService("password_policy");
        boolean z = false;
        if (passwordPolicy != null) {
            try {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                z = passwordPolicy.isMultifactorAuthenticationEnabled(contextInfo);
                Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (Exception e) {
                Log.w(
                        "KnoxMUMContainerPolicy",
                        "Failed at ContainerConfigurationPolicy API"
                            + " isMultifactorAuthenticationEnabled ",
                        e);
            }
        }
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "isMultifactorAuthenticationEnabled result - ", "KnoxMUMContainerPolicy", z);
        return z;
    }

    public final boolean isNFCEnabled(ContextInfo contextInfo) {
        boolean z = false;
        if (contextInfo == null) {
            return false;
        }
        int i = contextInfo.mContainerId;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            UserInfo userInfo = getUserManagerService().getUserInfo(i);
            if (userInfo != null) {
                if (userInfo.isSuperLocked()) {
                    return false;
                }
            }
        } catch (Exception e) {
            EnterpriseVpn$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("isNFCEnabled exception: "), "KnoxMUMContainerPolicy");
        }
        try {
            try {
                z = getFeatureAccessPermission(i, "NFC");
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            return z;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isPackageAllowedToAccessExternalSdcard(ContextInfo contextInfo, int i) {
        Log.d("KnoxMUMContainerPolicy", "isPackageAllowedToAccessExternalSdcard");
        return false;
    }

    public final boolean isPackageInInstallWhiteList(ContextInfo contextInfo, String str) {
        Log.i(
                "KnoxMUMContainerPolicy",
                "isPackageInInstallWhiteList is called for pkgName - " + str);
        ContextInfo enforceContainerOwnershipPermission =
                enforceContainerOwnershipPermission(
                        contextInfo,
                        new ArrayList(
                                Arrays.asList(
                                        "com.samsung.android.knox.permission.KNOX_CONTAINER")));
        IApplicationPolicy appService = getAppService();
        boolean z = false;
        if (appService == null) {
            Log.e("KnoxMUMContainerPolicy", "Application PolicyService is not yet ready!!!");
            return false;
        }
        try {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            z =
                    appService.isPackageInApprovedInstallerWhiteList(
                            enforceContainerOwnershipPermission, str);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return z;
        } catch (RemoteException e) {
            Log.w(
                    "KnoxMUMContainerPolicy",
                    "Failed at ContainerConfigurationPolicy API isPackageInInstallWhiteList ",
                    e);
            return z;
        }
    }

    public final boolean isResetContainerOnRebootEnabled(ContextInfo contextInfo) {
        UserInfo userInfo;
        boolean z = false;
        if (contextInfo == null) {
            return false;
        }
        enforceContainerOwnershipPermission(
                contextInfo,
                new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CONTAINER")));
        int i = contextInfo.mContainerId;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                Log.d("KnoxMUMContainerPolicy", "isResetPersonaOnRebootEnabled personaId " + i);
                if (getUserManagerService() != null
                        && (userInfo = getUserManagerService().getUserInfo(i)) != null) {
                    if ((userInfo.getAttributes() & 64) == 64) {
                        z = true;
                    }
                }
            } catch (Exception e) {
                Log.e(
                        "KnoxMUMContainerPolicy",
                        "Failed at KnoxMUMContainerPolicy API isResetContainerOnRebootEnabled ",
                        e);
            }
            return z;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isSettingsOptionEnabled(ContextInfo contextInfo, String str) {
        Log.d("KnoxMUMContainerPolicy", "isSettingsOptionEnabled");
        if (str == null || str.isEmpty()) {
            Log.e(
                    "KnoxMUMContainerPolicy",
                    "Error from isSettingsOptionEnabled(): option is null or empty");
            return false;
        }
        try {
            if (str.equals("option_callerinfo")) {
                int i = contextInfo.mContainerId;
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    try {
                        if (getUserManagerService().getUserInfo(i).isSuperLocked()) {
                            Log.d(
                                    "KnoxMUMContainerPolicy",
                                    "Target container is superlocked. return false for "
                                            .concat(str));
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            return false;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    if (getDevicePolicyService() != null) {
                        boolean z =
                                !getDevicePolicyService()
                                        .getCrossProfileCallerIdDisabled(new UserHandle(i));
                        Log.d(
                                "KnoxMUMContainerPolicy",
                                "isSettingsOptionEnabled(): Return result: " + z);
                        return z;
                    }
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            }
        } catch (Exception e2) {
            RCPManagerService$$ExternalSyntheticOutline0.m(
                    e2,
                    new StringBuilder("isSettingsOptionEnabled() exception: "),
                    "KnoxMUMContainerPolicy");
        }
        return false;
    }

    public final boolean isSettingsOptionEnabledInternal(int i, String str, boolean z) {
        boolean z2 = DEBUG;
        if (z2) {
            Log.d(
                    "KnoxMUMContainerPolicy",
                    "isSettingsOptionEnabledInternal for personaId=" + i + "; option=" + str);
        }
        if (str == null || str.isEmpty()) {
            if (z2) {
                Log.e(
                        "KnoxMUMContainerPolicy",
                        "Error from isSettingsOptionEnabledInternal: option is null or empty");
            }
            return false;
        }
        try {
            if (str.equals("option_callerinfo")) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    try {
                        if (getUserManagerService().getUserInfo(i).isSuperLocked()) {
                            Log.d(
                                    "KnoxMUMContainerPolicy",
                                    "Target container is superlocked. return false for "
                                            .concat(str));
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            return false;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    if (getDevicePolicyService() != null) {
                        boolean z3 =
                                !getDevicePolicyService()
                                        .getCrossProfileCallerIdDisabled(new UserHandle(i));
                        if (DEBUG) {
                            Log.d(
                                    "KnoxMUMContainerPolicy",
                                    "isSettingsOptionEnabledInternal: Return result: " + z3);
                        }
                        return z3;
                    }
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            }
            if (DEBUG) {
                Log.d(
                        "KnoxMUMContainerPolicy",
                        "isSettingsOptionEnabledInternal: no record found for "
                                + i
                                + ":  Return default value: "
                                + z);
            }
            return z;
        } catch (Exception e2) {
            AudioServiceExt$ResetSettingsReceiver$$ExternalSyntheticOutline0.m(
                    e2,
                    new StringBuilder("isSettingsOptionEnabledInternal: Exception "),
                    "KnoxMUMContainerPolicy");
            return false;
        }
    }

    public final boolean isUsbAccessEnabled(ContextInfo contextInfo) {
        boolean z = false;
        if (contextInfo == null) {
            return false;
        }
        int i = contextInfo.mContainerId;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                z = getFeatureAccessPermission(i, "USB");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return z;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean lockContainer(ContextInfo contextInfo, String str) {
        Log.i("KnoxMUMContainerPolicy", "lockContainer is called....");
        ContextInfo enforceContainerOwnershipPermission =
                enforceContainerOwnershipPermission(
                        contextInfo,
                        new ArrayList(
                                Arrays.asList(
                                        "com.samsung.android.knox.permission.KNOX_CONTAINER")));
        PasswordPolicy passwordPolicy =
                (PasswordPolicy) ServiceManager.getService("password_policy");
        boolean z = false;
        if (passwordPolicy != null) {
            try {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                z = passwordPolicy.lock(enforceContainerOwnershipPermission);
                if (z) {
                    AuditLog.logEventAsUser(
                            enforceContainerOwnershipPermission.mContainerId,
                            46,
                            new Object[] {
                                Integer.valueOf(enforceContainerOwnershipPermission.mCallerUid)
                            });
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (Exception e) {
                Log.w(
                        "KnoxMUMContainerPolicy",
                        "Failed at KnoxMUMContainerPolicy API lockContainer ",
                        e);
            }
        }
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "lockContainer result - ", "KnoxMUMContainerPolicy", z);
        return z;
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void notifyToAddSystemService(String str, IBinder iBinder) {}

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminAdded(int i) {}

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminRemoved(int i) {
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                i, "onAdminRemoval:", "KnoxMUMContainerPolicy");
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onPreAdminRemoval(int i) {
        checkCallerPermissionFor("onPreAdminRemoval");
        Log.d("KnoxMUMContainerPolicy", "onPreAdminRemoval:" + i);
        Iterator it = ((ArrayList) getContainers(i)).iterator();
        while (it.hasNext()) {
            int intValue = ((Integer) it.next()).intValue();
            Log.d("KnoxMUMContainerPolicy", "onPreAdminRemoval: removing container " + intValue);
            getUserManagerService().removeUser(intValue);
        }
        if (i != 0) {
            ArrayList arrayList = new ArrayList();
            Iterator it2 = ((ArrayList) this.mTypeList).iterator();
            while (it2.hasNext()) {
                KnoxConfigurationType knoxConfigurationType = (KnoxConfigurationType) it2.next();
                if (knoxConfigurationType.getAdminUid() == i) {
                    arrayList.add(knoxConfigurationType);
                }
            }
            Iterator it3 = arrayList.iterator();
            while (it3.hasNext()) {
                KnoxConfigurationType knoxConfigurationType2 = (KnoxConfigurationType) it3.next();
                if (knoxConfigurationType2 != null) {
                    Log.d(
                            "KnoxMUMContainerPolicy",
                            "onPreAdminRemoval: removing type" + knoxConfigurationType2.getName());
                }
                removeConfigurationTypeInternal(knoxConfigurationType2);
            }
        }
    }

    public final boolean registerBroadcastReceiverIntent(
            ContextInfo contextInfo, String str, String str2) {
        return false;
    }

    public final boolean removeConfigurationType(ContextInfo contextInfo, String str) {
        int callingUid;
        KnoxConfigurationType knoxConfigurationType;
        enforceSecurityPermission(
                contextInfo,
                new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CONTAINER")));
        if (contextInfo == null || (callingUid = contextInfo.mCallerUid) <= 0) {
            callingUid = Binder.getCallingUid();
        }
        synchronized (this.mTypeList) {
            try {
                Iterator it = ((ArrayList) this.mTypeList).iterator();
                while (true) {
                    if (!it.hasNext()) {
                        knoxConfigurationType = null;
                        break;
                    }
                    knoxConfigurationType = (KnoxConfigurationType) it.next();
                    if (!knoxConfigurationType.getName().equals(str)
                            || knoxConfigurationType.getAdminUid() != callingUid) {}
                }
                if (knoxConfigurationType == null
                        || callingUid == 0
                        || (knoxConfigurationType.getPersonaList() != null
                                && knoxConfigurationType.getPersonaList().size() != 0)) {
                    return false;
                }
                return removeConfigurationTypeInternal(knoxConfigurationType);
            } finally {
            }
        }
    }

    public final boolean removeConfigurationTypeInternal(
            KnoxConfigurationType knoxConfigurationType) {
        String str;
        if (knoxConfigurationType == null || knoxConfigurationType.getAdminUid() == 0) {
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                String customBadgeIcon = knoxConfigurationType.getCustomBadgeIcon();
                boolean z = DEBUG;
                if (z) {
                    str = "getFolderHeaderIcon icon file : ";
                    Log.d("KnoxMUMContainerPolicy", "badge icon file : " + customBadgeIcon);
                } else {
                    str = "getFolderHeaderIcon icon file : ";
                }
                if (customBadgeIcon != null) {
                    Log.d(
                            "KnoxMUMContainerPolicy",
                            "badge icon file deletion status: "
                                    + new File(customBadgeIcon).delete());
                }
                String customHomeScreenWallpaper =
                        knoxConfigurationType.getCustomHomeScreenWallpaper();
                if (z) {
                    Log.d(
                            "KnoxMUMContainerPolicy",
                            "getCustomHomeScreenWallpaper icon file : "
                                    + customHomeScreenWallpaper);
                }
                if (customHomeScreenWallpaper != null) {
                    Log.d(
                            "KnoxMUMContainerPolicy",
                            "home screen wall paper icon file deletion status: "
                                    + new File(customHomeScreenWallpaper).delete());
                }
                String customizedContainerNameIcon =
                        knoxConfigurationType.getCustomizedContainerNameIcon();
                if (z) {
                    Log.d(
                            "KnoxMUMContainerPolicy",
                            "getCustomizedContainerNameIcon icon file : "
                                    + customizedContainerNameIcon);
                }
                if (customizedContainerNameIcon != null) {
                    Log.d(
                            "KnoxMUMContainerPolicy",
                            "home screen wall paper icon file deletion status: "
                                    + new File(customizedContainerNameIcon).delete());
                }
                String customizedContainerIcon = knoxConfigurationType.getCustomizedContainerIcon();
                Log.d("KnoxMUMContainerPolicy", "getECIcon icon file : " + customizedContainerIcon);
                if (customizedContainerIcon != null) {
                    Log.d(
                            "KnoxMUMContainerPolicy",
                            "ec icon file deletion status: "
                                    + new File(customizedContainerIcon).delete());
                }
                String customizedContainerBadge =
                        knoxConfigurationType.getCustomizedContainerBadge();
                Log.d(
                        "KnoxMUMContainerPolicy",
                        "getECBadge icon file : " + customizedContainerBadge);
                if (customizedContainerBadge != null) {
                    Log.d(
                            "KnoxMUMContainerPolicy",
                            "ecbadge icon file deletion status: "
                                    + new File(customizedContainerBadge).delete());
                }
                String customLockScreenWallpaper =
                        knoxConfigurationType.getCustomLockScreenWallpaper();
                if (z) {
                    Log.d(
                            "KnoxMUMContainerPolicy",
                            "getCustomLockScreenWallpaper icon file : "
                                    + customLockScreenWallpaper);
                }
                if (customLockScreenWallpaper != null) {
                    Log.d(
                            "KnoxMUMContainerPolicy",
                            "lock screen wall paper icon file deletion status: "
                                    + new File(customLockScreenWallpaper).delete());
                }
                String customStatusIcon = knoxConfigurationType.getCustomStatusIcon();
                if (z) {
                    Log.d(
                            "KnoxMUMContainerPolicy",
                            "getCustomStatusIcon icon file : " + customStatusIcon);
                }
                if (customStatusIcon != null) {
                    Log.d(
                            "KnoxMUMContainerPolicy",
                            "custom status icon file deletion status: "
                                    + new File(customStatusIcon).delete());
                }
                if (knoxConfigurationType instanceof LightweightConfigurationType) {
                    String folderHeaderIcon =
                            ((LightweightConfigurationType) knoxConfigurationType)
                                    .getFolderHeaderIcon();
                    Log.d("KnoxMUMContainerPolicy", str + folderHeaderIcon);
                    if (folderHeaderIcon != null) {
                        Log.d(
                                "KnoxMUMContainerPolicy",
                                "folder header icon file deletion status: "
                                        + new File(folderHeaderIcon).delete());
                    }
                }
                ((ArrayList) this.mTypeList).remove(knoxConfigurationType);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            } catch (Exception e) {
                Log.e("KnoxMUMContainerPolicy", "IOException : " + Log.getStackTraceString(e));
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final int removeContainer(ContextInfo contextInfo) {
        enforceContainerOwnershipPermission(
                contextInfo,
                new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CONTAINER")));
        try {
            checkCallerPermissionFor("removeContainer");
        } catch (SecurityException e) {
            Log.e(
                    "KnoxMUMContainerPolicy",
                    "SEAMS invalidated caller. lets go for MDM check.."
                            + Log.getStackTraceString(e));
            enforceSecurityPermission(
                    contextInfo,
                    new ArrayList(
                            Arrays.asList("com.samsung.android.knox.permission.KNOX_CONTAINER")));
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        Log.d("KnoxMUMContainerPolicy", "Container removal started");
        boolean removeUser = getUserManagerService().removeUser(contextInfo.mContainerId);
        AuditLog.logAsUser(
                5,
                1,
                true,
                Process.myPid(),
                "KnoxMUMContainerPolicy",
                String.format(
                        "Admin %d has successfully removed Workspace %d",
                        Integer.valueOf(contextInfo.mCallerUid),
                        Integer.valueOf(contextInfo.mContainerId)),
                UserHandle.getUserId(contextInfo.mCallerUid));
        Binder.restoreCallingIdentity(clearCallingIdentity);
        if (removeUser) {
            Log.d("KnoxMUMContainerPolicy", "Container removal success");
            return 0;
        }
        Log.d("KnoxMUMContainerPolicy", "Container removal failed");
        return -1201;
    }

    public final int removeContainerInternal(int i) {
        UserInfo userInfo;
        checkCallerPermissionFor("removeContainerInternal");
        Binder.getCallingUid();
        Log.d("KnoxMUMContainerPolicy", "removeContainerInternal:" + i);
        long j = 0;
        try {
            try {
                j = Binder.clearCallingIdentity();
                userInfo = getUserManagerService().getUserInfo(i);
            } catch (Exception e) {
                Log.e("KnoxMUMContainerPolicy", "Exception:" + Log.getStackTraceString(e));
            }
            if (userInfo == null) {
                Log.e("KnoxMUMContainerPolicy", "user:" + i + " not found");
                Binder.restoreCallingIdentity(j);
                return -1014;
            }
            removeContainerOwnerRelationship(i);
            File file = new File("/data/misc/container3.0/" + i);
            if (file.exists()) {
                String[] list = file.list();
                if (list != null) {
                    for (String str : list) {
                        new File(file.getPath(), str).delete();
                    }
                }
                file.delete();
            }
            sendIntentBroadcastForContainer(
                    userInfo.id, "com.sec.knox.containeragent.klms.removed.b2b");
            Log.d("KnoxMUMContainerPolicy", "removeContainer(" + i + ")");
            Log.d("KnoxMUMContainerPolicy", "sendContainerRemovalIntent(containerId: " + i + ")");
            Intent intent = new Intent("enterprise.container.remove.progress");
            intent.putExtra("containerid", i);
            mContext.sendBroadcastAsUser(
                    intent,
                    new UserHandle(UserHandle.myUserId()),
                    "com.samsung.android.knox.permission.KNOX_CONTAINER");
            Intent intent2 = new Intent("enterprise.container.unmountfailure");
            intent2.putExtra("containerid", i);
            mContext.sendBroadcastAsUser(
                    intent2,
                    new UserHandle(UserHandle.myUserId()),
                    "com.samsung.android.knox.permission.KNOX_CONTAINER");
            return 0;
        } finally {
            Binder.restoreCallingIdentity(0L);
        }
    }

    public final void removeContainerOwnerRelationship(int i) {
        try {
            EdmStorageProvider edmStorageProvider = this.mEdmStorageProvider;
            edmStorageProvider.getClass();
            ContentValues contentValues = new ContentValues();
            contentValues.put("containerID", Integer.valueOf(i));
            if (edmStorageProvider.delete("MUMCONTAINER", contentValues) > 0) {
                Log.d("KnoxMUMContainerPolicy", "Container removed from ownership DB: " + i);
            } else {
                Log.e(
                        "KnoxMUMContainerPolicy",
                        "Container not found or Failed to remove container from DB: " + i);
            }
        } catch (Exception e) {
            Log.w(
                    "KnoxMUMContainerPolicy",
                    "Failed at removeContainerOwnerRelationship " + Log.getStackTraceString(e));
        }
    }

    public final boolean removeNetworkSSID(ContextInfo contextInfo, String str) {
        ContextInfo enforceWifiPermission = enforceWifiPermission(contextInfo);
        DualAppManagerService$$ExternalSyntheticOutline0.m(
                "removeNetworkSSID - SSID : ", str, "KnoxMUMContainerPolicy");
        String str2 = null;
        if (str != null) {
            try {
                String trim = str.trim();
                if (trim.length() > 0) {
                    str2 = trim;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (str2 == null) {
            DualAppManagerService$$ExternalSyntheticOutline0.m(
                    "removeNetworkSSID - invalid Str ", str2, "KnoxMUMContainerPolicy");
            return false;
        }
        int i = enforceWifiPermission.mCallerUid;
        String convertToQuotedString = convertToQuotedString(str2);
        Set ssid = getSSID(i);
        if (((HashSet) ssid).remove(convertToQuotedString)) {
            return saveBlockedList(i, ssid);
        }
        Log.e("KnoxMUMContainerPolicy", "addNetworkSSID failed : no exist.");
        return false;
    }

    public final int removePackageFromExternalStorageBlackList(
            ContextInfo contextInfo, AppIdentity appIdentity) {
        Log.i(
                "KnoxMUMContainerPolicy",
                "removePackageFromExternalStorageBlackList is not available.");
        return -1;
    }

    public final int removePackageFromExternalStorageWhiteList(
            ContextInfo contextInfo, AppIdentity appIdentity) {
        StringBuilder sb = new StringBuilder("removePackageFromExternalStorageWhiteList ");
        sb.append(appIdentity != null ? appIdentity.getPackageName() : "null");
        Log.i("KnoxMUMContainerPolicy", sb.toString());
        ContextInfo enforceContainerOwnershipPermission =
                enforceContainerOwnershipPermission(
                        contextInfo,
                        new ArrayList(
                                Arrays.asList(
                                        "com.samsung.android.knox.permission.KNOX_CONTAINER")));
        IApplicationPolicy appService = getAppService();
        int i = -1;
        if (appService == null) {
            Log.e("KnoxMUMContainerPolicy", "Application PolicyService is not yet ready!!!");
            return -1;
        }
        if (appIdentity == null || appIdentity.getPackageName() == null) {
            return -1;
        }
        try {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            i =
                    appService.removePackageFromWhiteList(
                            enforceContainerOwnershipPermission, 2, appIdentity.getPackageName());
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return i;
        } catch (RemoteException e) {
            Log.w(
                    "KnoxMUMContainerPolicy",
                    "Failed at ContainerConfigurationPolicy API"
                        + " removePackageFromExternalStorageWhiteList ",
                    e);
            return i;
        }
    }

    public final int removePackageFromInstallWhiteList(
            ContextInfo contextInfo, AppIdentity appIdentity) {
        StringBuilder sb =
                new StringBuilder("removePackageFromInstallWhiteList is called for package - ");
        sb.append(appIdentity != null ? appIdentity.getPackageName() : "null");
        Log.i("KnoxMUMContainerPolicy", sb.toString());
        ContextInfo enforceContainerOwnershipPermission =
                enforceContainerOwnershipPermission(
                        contextInfo,
                        new ArrayList(
                                Arrays.asList(
                                        "com.samsung.android.knox.permission.KNOX_CONTAINER")));
        IApplicationPolicy appService = getAppService();
        int i = -1;
        if (appService == null) {
            Log.e("KnoxMUMContainerPolicy", "Application PolicyService is not yet ready!!!");
            return -1;
        }
        if (appIdentity == null || appIdentity.getPackageName() == null) {
            return -1;
        }
        try {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            i =
                    appService.removePackageFromWhiteList(
                            enforceContainerOwnershipPermission, 1, appIdentity.getPackageName());
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return i;
        } catch (RemoteException e) {
            Log.w(
                    "KnoxMUMContainerPolicy",
                    "Failed at ContainerConfigurationPolicy API removePackageFromInstallWhiteList ",
                    e);
            return i;
        }
    }

    public final boolean resetContainerOnReboot(ContextInfo contextInfo, boolean z) {
        Log.e("KnoxMUMContainerPolicy", "Not support Container Only mode");
        return false;
    }

    public final void restartBluetooth$1() {
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        Log.d("KnoxMUMContainerPolicy", "restartBluetooth called! ba = " + defaultAdapter);
        if (defaultAdapter == null || !defaultAdapter.isEnabled()) {
            return;
        }
        this.mRestart = true;
        defaultAdapter.disable();
    }

    public final boolean saveBlockedList(int i, Set set) {
        HashSet hashSet = (HashSet) set;
        if (hashSet.isEmpty()) {
            return this.mEdmStorageProvider.removeByAdmin(i, 0, "ContainerOnly_wifiAP");
        }
        StringBuilder sb = new StringBuilder();
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            sb.append(((String) it.next()) + ",");
        }
        return this.mEdmStorageProvider.putString(
                i, 0, "ContainerOnly_wifiAP", "wifiSSIDforKNOX", sb.toString());
    }

    public final boolean setAppSeparationCoexistentApps(ContextInfo contextInfo, List list) {
        int callingUid;
        int deviceOwnerUid;
        String nameForUid = mContext.getPackageManager().getNameForUid(Binder.getCallingUid());
        Log.d(
                "KnoxMUMContainerPolicy",
                "setApppSeparationCoexistentApps: calling package : " + nameForUid);
        if (isIgnoreKSPCall(nameForUid)) {
            Log.d(
                    "KnoxMUMContainerPolicy",
                    "ignoring call from KSP as call from AppSeparation Agent was received earlier");
            return false;
        }
        if (contextInfo == null || (callingUid = contextInfo.mCallerUid) <= 0) {
            callingUid = Binder.getCallingUid();
        }
        try {
            enforceAppSeparationPermission(callingUid, nameForUid);
            deviceOwnerUid = getDeviceOwnerUid();
        } catch (Exception e) {
            AudioServiceExt$ResetSettingsReceiver$$ExternalSyntheticOutline0.m(
                    e,
                    new StringBuilder("setAppSeparationCoexistentApps() exception: "),
                    "KnoxMUMContainerPolicy");
        }
        if (list == null) {
            Log.e(
                    "KnoxMUMContainerPolicy",
                    "setAppSeparationCoexistentApps() : AppList cannot be null");
            return false;
        }
        String join = String.join(",", list);
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Integer.valueOf(deviceOwnerUid));
        if (this.mEdmStorageProvider.getCount("AppSeparationTable", contentValues) <= 0) {
            Log.d(
                    "KnoxMUMContainerPolicy",
                    "No default policy applied. Use setAppSeperationConfig()");
            return false;
        }
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("AppSeparationCoexistenceList", join);
        if (this.mEdmStorageProvider.putValues(
                "AppSeparationTable", contentValues2, contentValues)) {
            Log.d("KnoxMUMContainerPolicy", "setAppSeparationCoexistentApps passed");
            PersonaManagerService personaManagerLocked = getPersonaManagerLocked();
            personaManagerLocked.updateAppsListOnlyPresentInSeparatedApps();
            personaManagerLocked.mPersonaHandler.sendMessage(
                    personaManagerLocked.mPersonaHandler.obtainMessage(76));
            Log.d("PersonaManagerService", "enforceAppSeparationCoexistenceAllowListUpdate");
            AuditLog.logAsUser(
                    5,
                    1,
                    true,
                    Process.myPid(),
                    "KnoxMUMContainerPolicy",
                    "Application "
                            + join
                            + " installation is allowed by admin "
                            + mContext.getPackageManager().getNameForUid(deviceOwnerUid)
                            + " for Separated Apps",
                    0);
            return true;
        }
        return false;
    }

    public final boolean setAppSeparationConfig(ContextInfo contextInfo, Bundle bundle) {
        int callingUid;
        int deviceOwnerUid;
        String nameForUid = mContext.getPackageManager().getNameForUid(Binder.getCallingUid());
        Log.d("KnoxMUMContainerPolicy", "setAppSeparationConfig: calling package : " + nameForUid);
        if (isIgnoreKSPCall(nameForUid)) {
            Log.d(
                    "KnoxMUMContainerPolicy",
                    "ignoring call from KSP as call from AppSeparation Agent was received earlier");
            return false;
        }
        if (isUserMode && !SemPersonaManager.isDoEnabled(0)) {
            Log.e("KnoxMUMContainerPolicy", "setAppSeparationConfig() : Activate DO!");
            return false;
        }
        if (DualDarManager.isOnDeviceOwnerEnabled()) {
            Log.e(
                    "KnoxMUMContainerPolicy",
                    "Failed to activate AppSeparation - on DeviceOwner DualDAR mode");
            return false;
        }
        if (contextInfo == null || (callingUid = contextInfo.mCallerUid) <= 0) {
            callingUid = Binder.getCallingUid();
        }
        try {
            enforceAppSeparationPermission(callingUid, nameForUid);
            deviceOwnerUid = getDeviceOwnerUid();
        } catch (Exception e) {
            AudioServiceExt$ResetSettingsReceiver$$ExternalSyntheticOutline0.m(
                    e,
                    new StringBuilder("setAppSeparationConfig() exception: "),
                    "KnoxMUMContainerPolicy");
        }
        if (bundle == null) {
            Log.e(
                    "KnoxMUMContainerPolicy",
                    "setAppSeparationConfig() : config is null. remove row and enforce.");
            HashMap hashMap = new HashMap();
            hashMap.put("adminUid", Integer.toString(deviceOwnerUid));
            this.mEdmStorageProvider.removeByFieldsAsUser(0, "AppSeparationTable", hashMap);
            PersonaManagerService personaManagerLocked = getPersonaManagerLocked();
            personaManagerLocked.mPersonaHandler.sendMessage(
                    personaManagerLocked.mPersonaHandler.obtainMessage(72));
            Log.d("PersonaManagerService", "enforceAppSeparationDeletion");
            AuditLog.logAsUser(
                    5,
                    1,
                    true,
                    Process.myPid(),
                    "KnoxMUMContainerPolicy",
                    "Admin "
                            + mContext.getPackageManager().getNameForUid(deviceOwnerUid)
                            + " has de-activated Separated Apps.",
                    0);
            return true;
        }
        boolean z = bundle.getBoolean("APP_SEPARATION_OUTSIDE", true);
        String join = String.join(",", bundle.getStringArrayList("APP_SEPARATION_APP_LIST"));
        ArrayList<String> stringArrayList =
                bundle.getStringArrayList("APP_SEPARATION_COEXISTANCE_LIST");
        if (stringArrayList == null) {
            stringArrayList = new ArrayList<>();
        }
        String join2 = String.join(",", stringArrayList);
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Integer.valueOf(deviceOwnerUid));
        if (this.mEdmStorageProvider.getCount("AppSeparationTable", contentValues) > 0) {
            Log.d(
                    "KnoxMUMContainerPolicy",
                    "AppSeparation is already enabled. Use setAppSeparationWhitelistedApps() to"
                        + " update app list.");
            return false;
        }
        contentValues.put("AppSeparationOutside", Integer.valueOf(z ? 1 : 0));
        contentValues.put("AppSeparationApplist", join);
        contentValues.put("AppSeparationCoexistenceList", join2);
        if (this.mEdmStorageProvider.putValuesNoUpdate("AppSeparationTable", contentValues)) {
            Log.d("KnoxMUMContainerPolicy", "setAppSeparationConfig passed");
            PersonaManagerService personaManagerLocked2 = getPersonaManagerLocked();
            personaManagerLocked2.updateAppsListOnlyPresentInSeparatedApps();
            personaManagerLocked2.mPersonaHandler.sendMessage(
                    personaManagerLocked2.mPersonaHandler.obtainMessage(71));
            Log.d("PersonaManagerService", "enforceAppSeparationAllowListUpdate");
            AuditLog.logAsUser(
                    5,
                    1,
                    true,
                    Process.myPid(),
                    "KnoxMUMContainerPolicy",
                    String.format(
                            z
                                    ? "Admin %s has located Apps (outside) of Separated Apps"
                                    : "Admin %s has located Apps (inside) of Separated Apps",
                            mContext.getPackageManager().getNameForUid(deviceOwnerUid)),
                    0);
            AuditLog.logAsUser(
                    5,
                    1,
                    true,
                    Process.myPid(),
                    "KnoxMUMContainerPolicy",
                    "Application "
                            + join
                            + " installation is allowed by admin "
                            + mContext.getPackageManager().getNameForUid(deviceOwnerUid)
                            + " for Separated Apps",
                    0);
            AuditLog.logAsUser(
                    5,
                    1,
                    true,
                    Process.myPid(),
                    "KnoxMUMContainerPolicy",
                    "Application "
                            + join2
                            + " coexistence installation is allowed by admin "
                            + mContext.getPackageManager().getNameForUid(deviceOwnerUid)
                            + " for Separated Apps",
                    0);
            return true;
        }
        return false;
    }

    public final boolean setAppSeparationWhitelistedApps(ContextInfo contextInfo, List list) {
        int callingUid;
        int deviceOwnerUid;
        String nameForUid = mContext.getPackageManager().getNameForUid(Binder.getCallingUid());
        Log.d(
                "KnoxMUMContainerPolicy",
                "setAppSeparationWhitelistedApps: calling package : " + nameForUid);
        if (isIgnoreKSPCall(nameForUid)) {
            Log.d(
                    "KnoxMUMContainerPolicy",
                    "ignoring call from KSP as call from AppSeparation Agent was received earlier");
            return false;
        }
        if (contextInfo == null || (callingUid = contextInfo.mCallerUid) <= 0) {
            callingUid = Binder.getCallingUid();
        }
        try {
            enforceAppSeparationPermission(callingUid, nameForUid);
            deviceOwnerUid = getDeviceOwnerUid();
        } catch (Exception e) {
            AudioServiceExt$ResetSettingsReceiver$$ExternalSyntheticOutline0.m(
                    e,
                    new StringBuilder("setAppSeparationWhitelistedApps() exception: "),
                    "KnoxMUMContainerPolicy");
        }
        if (list == null) {
            Log.e(
                    "KnoxMUMContainerPolicy",
                    "setAppSeparationWhitelistedApps() : AppList cannot be null. Please use"
                        + " setAppSeperationConfig to wipe app separation policy");
            return false;
        }
        String join = String.join(",", list);
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Integer.valueOf(deviceOwnerUid));
        if (this.mEdmStorageProvider.getCount("AppSeparationTable", contentValues) <= 0) {
            Log.d(
                    "KnoxMUMContainerPolicy",
                    "No default policy applied. Use setAppSeperationConfig()");
            return false;
        }
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("AppSeparationApplist", join);
        if (this.mEdmStorageProvider.putValues(
                "AppSeparationTable", contentValues2, contentValues)) {
            Log.d("KnoxMUMContainerPolicy", "setAppSeparationWhitelistedApps passed");
            PersonaManagerService personaManagerLocked = getPersonaManagerLocked();
            personaManagerLocked.updateAppsListOnlyPresentInSeparatedApps();
            personaManagerLocked.mPersonaHandler.sendMessage(
                    personaManagerLocked.mPersonaHandler.obtainMessage(71));
            Log.d("PersonaManagerService", "enforceAppSeparationAllowListUpdate");
            AuditLog.logAsUser(
                    5,
                    1,
                    true,
                    Process.myPid(),
                    "KnoxMUMContainerPolicy",
                    "Application "
                            + join
                            + " installation is allowed by admin "
                            + mContext.getPackageManager().getNameForUid(deviceOwnerUid)
                            + " for Separated Apps",
                    0);
            return true;
        }
        return false;
    }

    public final boolean setContactsSharingEnabled(ContextInfo contextInfo, boolean z) {
        return false;
    }

    public final int setCustomResource(int i, ContextInfo contextInfo, Bundle bundle) {
        String str;
        Bitmap bitmap;
        if (enforceContainerOwnershipPermission(
                        contextInfo,
                        new ArrayList(
                                Arrays.asList(
                                        "com.samsung.android.knox.permission.KNOX_CONTAINER")))
                == null) {
            return -2;
        }
        int i2 = contextInfo.mContainerId;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        String m =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        i2, "/data/misc/container3.0/", "/");
        try {
            int dimension =
                    (int)
                            mContext.getResources()
                                    .getDimension(
                                            R.dimen.notification_actions_collapsed_priority_width);
            int dimension2 =
                    (int)
                            mContext.getResources()
                                    .getDimension(R.dimen.notification_action_list_margin_top);
            if (i == 1) {
                str = m + "badgeIcon.png";
                bitmap = bundle != null ? (Bitmap) bundle.getParcelable("key-image") : null;
                if (bitmap != null) {
                    int width = bitmap.getWidth();
                    int height = bitmap.getHeight();
                    Log.d(
                            "KnoxMUMContainerPolicy",
                            "container badge dimensions " + width + " " + height);
                    if (width > dimension / 2 || height > dimension2 / 2) {
                        bitmap =
                                Bitmap.createScaledBitmap(
                                        bitmap, dimension / 2, dimension2 / 2, false);
                    }
                }
            } else if (i == 2) {
                str = m + "profileIcon.png";
                bitmap = bundle != null ? (Bitmap) bundle.getParcelable("key-image") : null;
                if (bitmap != null) {
                    int width2 = bitmap.getWidth();
                    int height2 = bitmap.getHeight();
                    Log.d(
                            "KnoxMUMContainerPolicy",
                            "container icon dimensions " + width2 + " " + height2);
                    if (width2 > dimension || height2 > dimension2) {
                        bitmap = Bitmap.createScaledBitmap(bitmap, dimension, dimension2, false);
                    }
                }
            } else {
                if (i != 3) {
                    if (i == 4) {
                        String string = bundle != null ? bundle.getString("key-name", null) : null;
                        getService();
                        if (!SemPersonaManager.setCustomName(i2, string)) {
                            Log.d(
                                    "KnoxMUMContainerPolicy",
                                    "setting Custom Profile name unsuccessful");
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            return -2;
                        }
                        Intent launcherRefreshIntent = getLauncherRefreshIntent(i2);
                        Log.d("KnoxMUMContainerPolicy", "refreshLauncherUI launcherRefresh");
                        mContext.sendBroadcastAsUser(launcherRefreshIntent, UserHandle.SEM_OWNER);
                        Log.d("KnoxMUMContainerPolicy", "container name : " + string);
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return 0;
                    }
                    if (i != 5) {
                        Log.d(
                                "KnoxMUMContainerPolicy",
                                "setCustomResource - not a valid type " + i);
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return -1;
                    }
                    String string2 = bundle != null ? bundle.getString("key-name", null) : null;
                    getService();
                    if (!SemPersonaManager.setPersonalModeName(i2, string2)) {
                        Log.d("KnoxMUMContainerPolicy", "setting personal tab name unsuccessful");
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return -2;
                    }
                    Intent launcherRefreshIntent2 = getLauncherRefreshIntent(i2);
                    Log.d("KnoxMUMContainerPolicy", "refreshLauncherUI launcherRefresh");
                    mContext.sendBroadcastAsUser(launcherRefreshIntent2, UserHandle.SEM_OWNER);
                    Log.d("KnoxMUMContainerPolicy", "Personal mode tab : " + string2);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return 0;
                }
                str = m + "nameIcon.png";
                bitmap = bundle != null ? (Bitmap) bundle.getParcelable("key-image") : null;
            }
            if (bitmap == null) {
                int i3 = new File(str).delete() ? 0 : -2;
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return i3;
            }
            File file = new File("/data/misc/container3.0/");
            if (!file.exists()) {
                file.mkdirs();
                file.setReadable(true, false);
                file.setWritable(true);
                file.setExecutable(true, false);
            }
            File file2 = new File(m);
            if (!file2.exists()) {
                file2.mkdirs();
                file2.setReadable(true, false);
                file2.setWritable(true);
                file2.setExecutable(true, false);
            }
            int i4 = writeFile(bitmap, str) ? 0 : -2;
            if (i4 == 0 && i == 1) {
                Intent launcherRefreshIntent3 = getLauncherRefreshIntent(i2);
                launcherRefreshIntent3.putExtra(
                        "com.samsung.android.knox.container.block_create_shortcut", true);
                launcherRefreshIntent3.putExtra(
                        "com.samsung.android.knox.container.name",
                        SemPersonaManager.getPersonaName(mContext, i2));
                Log.d("KnoxMUMContainerPolicy", "refreshLauncherUI launcherRefresh");
                mContext.sendBroadcastAsUser(launcherRefreshIntent3, UserHandle.SEM_OWNER);
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return i4;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final boolean setEnforceAuthForContainer(ContextInfo contextInfo, boolean z) {
        return false;
    }

    public final boolean setFIDOInfo(ContextInfo contextInfo, Bundle bundle) {
        boolean putValuesNoUpdate;
        Log.d("KnoxMUMContainerPolicy", "setFIDOInfo()");
        enforceContainerOwnershipPermission(
                contextInfo,
                new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CONTAINER")));
        try {
        } catch (Exception e) {
            AudioServiceExt$ResetSettingsReceiver$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("setFIDOInfo() exception: "), "KnoxMUMContainerPolicy");
        }
        if (bundle == null) {
            Log.e("KnoxMUMContainerPolicy", "setFIDOinfo() : fidoinfo is null. remove row.");
            HashMap hashMap = new HashMap();
            hashMap.put("adminUid", Integer.toString(contextInfo.mCallerUid));
            hashMap.put("cid", Integer.toString(contextInfo.mContainerId));
            this.mEdmStorageProvider.removeByFieldsAsUser(0, "KnoxFIDOSettingTable", hashMap);
            return true;
        }
        String string = bundle.getString("fido_request_uri", "");
        String string2 = bundle.getString("fido_response_uri", "");
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Integer.valueOf(contextInfo.mCallerUid));
        contentValues.put("cid", Integer.valueOf(contextInfo.mContainerId));
        if (this.mEdmStorageProvider.getCount("KnoxFIDOSettingTable", contentValues) > 0) {
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put("request", string);
            contentValues2.put("respond", string2);
            putValuesNoUpdate =
                    this.mEdmStorageProvider.putValues(
                            "KnoxFIDOSettingTable", contentValues2, contentValues);
        } else {
            contentValues.put("request", string);
            contentValues.put("respond", string2);
            putValuesNoUpdate =
                    this.mEdmStorageProvider.putValuesNoUpdate(
                            "KnoxFIDOSettingTable", contentValues);
        }
        if (putValuesNoUpdate) {
            Log.d("KnoxMUMContainerPolicy", "setFIDOInfo passed");
            return true;
        }
        return false;
    }

    public final boolean setFeatureAccessPermission(
            ContextInfo contextInfo, String str, boolean z) {
        boolean putValuesNoUpdate;
        ContentValues contentValues = new ContentValues();
        KnoxMUMContainerPolicy$$ExternalSyntheticOutline0.m(
                contextInfo.mCallerUid, contentValues, "adminUid", "featureType", str);
        if (this.mEdmStorageProvider.getCount("KnoxFeatureAccess", contentValues) > 0) {
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put("featureValue", z + "");
            putValuesNoUpdate =
                    this.mEdmStorageProvider.putValues(
                            "KnoxFeatureAccess", contentValues2, contentValues);
        } else {
            contentValues.put("featureValue", z + "");
            putValuesNoUpdate =
                    this.mEdmStorageProvider.putValuesNoUpdate("KnoxFeatureAccess", contentValues);
        }
        if (!putValuesNoUpdate) {
            return false;
        }
        Log.d("KnoxMUMContainerPolicy", "setFeatureAccessPermission policy passed");
        return true;
    }

    public final boolean setHibernationTimeout(ContextInfo contextInfo, long j) {
        enforceContainerOwnershipPermission(
                contextInfo,
                new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CONTAINER")));
        return false;
    }

    public final boolean setSettingsOptionEnabled(ContextInfo contextInfo, String str, boolean z) {
        Log.d("KnoxMUMContainerPolicy", "setSettingsOptionEnabled()");
        boolean z2 = false;
        if (str == null || str.isEmpty()) {
            Log.e(
                    "KnoxMUMContainerPolicy",
                    "Error from setSettingsOptionEnabled(): option is null or empty");
            return false;
        }
        ContextInfo enforceContainerOwnershipPermission =
                enforceContainerOwnershipPermission(
                        contextInfo,
                        new ArrayList(
                                Arrays.asList(
                                        "com.samsung.android.knox.permission.KNOX_CONTAINER")));
        try {
            if (str.equals("option_callerinfo")) {
                int userId = UserHandle.getUserId(enforceContainerOwnershipPermission.mCallerUid);
                long clearCallingIdentity = Binder.clearCallingIdentity();
                ComponentName adminComponentName = SemPersonaManager.getAdminComponentName(userId);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                if (getDevicePolicyService() != null) {
                    getDevicePolicyService()
                            .setCrossProfileCallerIdDisabled(adminComponentName, !z);
                    z2 = true;
                }
            }
        } catch (Exception unused) {
            Log.e("KnoxMUMContainerPolicy", "setSettingsOptionEnabled failed : result = false");
        }
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                "setSettingsOptionEnabled() : enable = ", "KnoxMUMContainerPolicy", z);
        return z2;
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void systemReady() {
        checkCallerPermissionFor("systemReady");
        Log.d("KnoxMUMContainerPolicy", "System ready called....");
        Log.d("KnoxMUMContainerPolicy", "registerContentObserver - 0");
        mContext.getContentResolver()
                .registerContentObserver(
                        Settings.Secure.getUriFor("hide_secure_folder_flag"),
                        false,
                        this.contentObserver,
                        0);
        this.mInjector.getClass();
        Object policyService = EnterpriseService.getPolicyService("enterprise_license_policy");
        EnterpriseLicenseService enterpriseLicenseService =
                policyService != null ? (EnterpriseLicenseService) policyService : null;
        if (enterpriseLicenseService == null) {
            throw new RuntimeException(
                    "ContainerLicenseObserver is not added! Please invoke"
                        + " registerContainerLicenseObserver more later!");
        }
        ContainerLicenseObserver containerLicenseObserver = new ContainerLicenseObserver();
        enterpriseLicenseService.enforcePermission$1();
        ((ArrayList) enterpriseLicenseService.mKlmElmChangeList).add(containerLicenseObserver);
        this.mActivityManager = (ActivityManager) mContext.getSystemService("activity");
        ContainerStateReceiver.register(
                mContext,
                new ContainerStateReceiver() { // from class:
                    // com.android.server.enterprise.container.KnoxMUMContainerPolicy.2
                    public final void onContainerCreated(Context context, int i, Bundle bundle) {
                        UserInfo userInfo =
                                KnoxMUMContainerPolicy.this.getUserManagerService().getUserInfo(i);
                        if (userInfo == null) {
                            Context context2 = KnoxMUMContainerPolicy.mContext;
                            AudioDeviceInventory$$ExternalSyntheticOutline0.m(
                                    i,
                                    "onContainerCreated(",
                                    ") error, no user-info found",
                                    "KnoxMUMContainerPolicy");
                            return;
                        }
                        Context context3 = KnoxMUMContainerPolicy.mContext;
                        GestureWakeup$$ExternalSyntheticOutline0.m(
                                new StringBuilder("onContainerCreated :: user: "),
                                userInfo.id,
                                "KnoxMUMContainerPolicy");
                        KnoxMUMContainerPolicy knoxMUMContainerPolicy = KnoxMUMContainerPolicy.this;
                        int i2 = userInfo.id;
                        knoxMUMContainerPolicy.getClass();
                        KnoxMUMContainerPolicy.sendIntentBroadcastForContainer(
                                i2, "com.sec.knox.containeragent.klms.created.b2b");
                        KnoxConfigurationType filterTypeByContainerId =
                                KnoxMUMContainerPolicy.this.filterTypeByContainerId(userInfo.id);
                        Bundle bundle2 = new Bundle();
                        if (filterTypeByContainerId == null
                                || !filterTypeByContainerId.isCustomizedContainerEnabled()) {
                            bundle2.putBoolean(
                                    "knox.container.proxy.EXTRA_FLAG_IS_CUSTOM_CONTAINER", false);
                        } else {
                            String customHomeScreenWallpaper =
                                    filterTypeByContainerId.getCustomHomeScreenWallpaper();
                            ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                                    "homeScreenWallpaper = ",
                                    customHomeScreenWallpaper,
                                    "KnoxMUMContainerPolicy");
                            bundle2.putString(
                                    "knox.container.proxy.EXTRA_KNOX_HOME_SCREEN_WALLPAPER",
                                    customHomeScreenWallpaper);
                            bundle2.putBoolean(
                                    "knox.container.proxy.EXTRA_FLAG_IS_CUSTOM_CONTAINER", true);
                        }
                        bundle2.putInt("android.intent.extra.user_handle", userInfo.id);
                        if (KnoxMUMContainerPolicy.this.getDarManagerService() != null) {
                            String string = bundle.getString("EXTRA_RESET_TOKEN", null);
                            DarManagerService darManagerService =
                                    KnoxMUMContainerPolicy.this.getDarManagerService();
                            int i3 = userInfo.id;
                            darManagerService.checkSystemPermission();
                            Message obtainMessage =
                                    darManagerService.mDarHandler.obtainMessage(118, i3, 0);
                            obtainMessage.obj = string;
                            darManagerService.mDarHandler.sendMessage(obtainMessage);
                        }
                        if (SemPersonaManager.isDarDualEncryptionEnabled(userInfo.id)) {
                            DDLog.d(
                                    "KnoxMUMContainerPolicy",
                                    "disableUnifiedLock user " + userInfo.id,
                                    new Object[0]);
                            KnoxMUMContainerPolicy knoxMUMContainerPolicy2 =
                                    KnoxMUMContainerPolicy.this;
                            int i4 = userInfo.id;
                            if (knoxMUMContainerPolicy2
                                            .getDevicePolicyService()
                                            .getProfileOwnerAsUser(i4)
                                    == null) {
                                DDLog.e(
                                        "KnoxMUMContainerPolicy",
                                        "failed to get active admin. failed to disallow unified"
                                            + " password.",
                                        new Object[0]);
                                return;
                            }
                            long clearCallingIdentity = Binder.clearCallingIdentity();
                            try {
                                try {
                                    Bundle bundle3 = new Bundle();
                                    bundle3.putInt("android.intent.extra.user_handle", i4);
                                    bundle3.putString(
                                            "knox.container.proxy.EXTRA_KEY",
                                            "no_unified_password");
                                    Bundle sendPolicyUpdate =
                                            ContainerProxy.sendPolicyUpdate(
                                                    "knox.container.proxy.POLICY_ADD_USER_RESTRICTION",
                                                    bundle3);
                                    if (sendPolicyUpdate != null) {
                                        DDLog.d(
                                                "KnoxMUMContainerPolicy",
                                                "disableUnifiedLock user result : "
                                                        + sendPolicyUpdate.getInt(
                                                                "android.intent.extra.RETURN_RESULT",
                                                                1),
                                                new Object[0]);
                                    } else {
                                        DDLog.e(
                                                "KnoxMUMContainerPolicy",
                                                "disableUnifiedLock user failed!! cannot get"
                                                    + " response ",
                                                new Object[0]);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                            } catch (Throwable th) {
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                                throw th;
                            }
                        }
                    }

                    public final void onDeviceOwnerActivated(Context context, Bundle bundle) {
                        Context context2 = KnoxMUMContainerPolicy.mContext;
                        Log.d("KnoxMUMContainerPolicy", "onDeviceOwnerActivated is called...");
                        try {
                            ComponentName deviceOwnerComponentOnAnyUser =
                                    KnoxMUMContainerPolicy.this
                                            .getDevicePolicyService()
                                            .getDeviceOwnerComponentOnAnyUser();
                            if (deviceOwnerComponentOnAnyUser != null
                                    && deviceOwnerComponentOnAnyUser.getPackageName() != null) {
                                Log.d(
                                        "KnoxMUMContainerPolicy",
                                        "onDeviceOwnerActivated admin relationship result - "
                                                + KnoxMUMContainerPolicy.this.mEdmStorageProvider
                                                        .addMUMContainer(
                                                                0,
                                                                KnoxMUMContainerPolicy.mContext
                                                                        .getPackageManager()
                                                                        .getPackageUidAsUser(
                                                                                deviceOwnerComponentOnAnyUser
                                                                                        .getPackageName(),
                                                                                0)));
                            }
                        } catch (PackageManager.NameNotFoundException e) {
                            Context context3 = KnoxMUMContainerPolicy.mContext;
                            Log.d(
                                    "KnoxMUMContainerPolicy",
                                    "onDeviceOwnerActivated:NameNotFoundException - " + e);
                        } catch (Exception e2) {
                            Context context4 = KnoxMUMContainerPolicy.mContext;
                            Log.d(
                                    "KnoxMUMContainerPolicy",
                                    "onDeviceOwnerActivated:Exception - " + e2);
                        }
                        KnoxMUMContainerPolicy.m501$$Nest$mnotifyDOPremiumActivation(
                                KnoxMUMContainerPolicy.this);
                    }

                    public final void onDeviceOwnerLicenseActivated(
                            Context context, Bundle bundle) {
                        Context context2 = KnoxMUMContainerPolicy.mContext;
                        Log.d("KnoxMUMContainerPolicy", "onDeviceOwnerLicenseActivated called...");
                        KnoxMUMContainerPolicy.m501$$Nest$mnotifyDOPremiumActivation(
                                KnoxMUMContainerPolicy.this);
                        if (!KnoxMUMContainerPolicy.m500$$Nest$misDualDarLicenseLockedCase(
                                KnoxMUMContainerPolicy.this)) {
                            if (!(KnoxMUMContainerPolicy.this
                                            .getDevicePolicyService()
                                            .isOrganizationOwnedDeviceWithManagedProfile()
                                    ? SemPersonaManager.getUCMDAREncryption()
                                    : false)) {
                                return;
                            }
                        }
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            try {
                                SystemUIAdapter.getInstance(KnoxMUMContainerPolicy.mContext)
                                        .setAdminLockEnabled(0, false, false);
                            } catch (Exception e) {
                                Context context3 = KnoxMUMContainerPolicy.mContext;
                                Log.e(
                                        "KnoxMUMContainerPolicy",
                                        "onDeviceOwnerLicenseActivated:Exception - ",
                                        e);
                            }
                        } finally {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }

                    public final void onDeviceOwnerLicenseExpired(Context context, Bundle bundle) {
                        Context context2 = KnoxMUMContainerPolicy.mContext;
                        Log.d("KnoxMUMContainerPolicy", "onDeviceOwnerLicenseExpired is called...");
                        if (!KnoxMUMContainerPolicy.m500$$Nest$misDualDarLicenseLockedCase(
                                KnoxMUMContainerPolicy.this)) {
                            if (!(KnoxMUMContainerPolicy.this
                                            .getDevicePolicyService()
                                            .isOrganizationOwnedDeviceWithManagedProfile()
                                    ? SemPersonaManager.getUCMDAREncryption()
                                    : false)) {
                                return;
                            }
                        }
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            try {
                                SystemUIAdapter.getInstance(KnoxMUMContainerPolicy.mContext)
                                        .setAdminLockEnabled(0, true, true);
                            } catch (Exception e) {
                                Context context3 = KnoxMUMContainerPolicy.mContext;
                                Log.e(
                                        "KnoxMUMContainerPolicy",
                                        "onDeviceOwnerLicenseExpired:Exception - ",
                                        e);
                            }
                        } finally {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }
                });
        if (getService() != null) {
            getService()
                    .registerSystemPersonaObserver(
                            new ISystemPersonaObserver.Stub() { // from class:
                                // com.android.server.enterprise.container.KnoxMUMContainerPolicy.3
                                public final void onKnoxContainerLaunch(int i) {}

                                public final void onPersonaActive(int i) {}

                                public final void onRemovePersona(int i) {}

                                public final void onResetPersona(int i) {}

                                public final void onStateChange(
                                        final int i,
                                        final SemPersonaState semPersonaState,
                                        final SemPersonaState semPersonaState2) {
                                    Context context = KnoxMUMContainerPolicy.mContext;
                                    Log.d(
                                            "KnoxMUMContainerPolicy",
                                            " inside onstatechange "
                                                    + i
                                                    + " new "
                                                    + semPersonaState2
                                                    + " old "
                                                    + semPersonaState);
                                    KnoxMUMContainerPolicy.this.mHandler.post(
                                            new Runnable() { // from class:
                                                // com.android.server.enterprise.container.KnoxMUMContainerPolicy.3.1
                                                /* JADX WARN: Removed duplicated region for block: B:18:0x00a7  */
                                                /* JADX WARN: Removed duplicated region for block: B:24:0x00bd  */
                                                /* JADX WARN: Removed duplicated region for block: B:29:0x00ce  */
                                                /* JADX WARN: Removed duplicated region for block: B:31:0x0059  */
                                                /* JADX WARN: Removed duplicated region for block: B:9:0x006f  */
                                                @Override // java.lang.Runnable
                                                /*
                                                    Code decompiled incorrectly, please refer to instructions dump.
                                                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                                                */
                                                public final void run() {
                                                    /*
                                                        Method dump skipped, instructions count: 258
                                                        To view this dump change 'Code comments level' option to 'DEBUG'
                                                    */
                                                    throw new UnsupportedOperationException(
                                                            "Method not decompiled:"
                                                                + " com.android.server.enterprise.container.KnoxMUMContainerPolicy.AnonymousClass3.AnonymousClass1.run():void");
                                                }
                                            });
                                }
                            });
        }
    }

    public final boolean unlockContainer(ContextInfo contextInfo) {
        Log.i("KnoxMUMContainerPolicy", "unlockContainer is called....");
        ContextInfo enforceContainerOwnershipPermission =
                enforceContainerOwnershipPermission(
                        contextInfo,
                        new ArrayList(
                                Arrays.asList(
                                        "com.samsung.android.knox.permission.KNOX_CONTAINER")));
        PasswordPolicy passwordPolicy =
                (PasswordPolicy) ServiceManager.getService("password_policy");
        boolean z = false;
        if (passwordPolicy != null) {
            try {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                z = passwordPolicy.unlock(enforceContainerOwnershipPermission);
                if (z) {
                    AuditLog.logEventAsUser(
                            enforceContainerOwnershipPermission.mContainerId,
                            47,
                            new Object[] {
                                Integer.valueOf(enforceContainerOwnershipPermission.mCallerUid)
                            });
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (Exception e) {
                Log.w(
                        "KnoxMUMContainerPolicy",
                        "Failed at KnoxMUMContainerPolicy API unlockContainer ",
                        e);
            }
        }
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "unlockContainer result - ", "KnoxMUMContainerPolicy", z);
        return z;
    }

    public final boolean unregisterBroadcastReceiverIntent(
            ContextInfo contextInfo, String str, String str2) {
        return false;
    }

    public final boolean updateProvisioningState(Bundle bundle) {
        checkCallerPermissionFor("updateProvisioningState");
        if (bundle == null) {
            Log.e("KnoxMUMContainerPolicy", "updateProvisioningState() invalid input");
            return false;
        }
        int i = bundle.getInt(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, -1);
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                i, "updateProvisioningState called: state = ", "KnoxMUMContainerPolicy");
        if (i == -1) {
            Log.e("KnoxMUMContainerPolicy", "updateProvisioningState() invalid state");
            return false;
        }
        synchronized (this.mProvisioningLock) {
            try {
                if (i != 1) {
                    ProvisioningState provisioningState = this.mCurrentProvisioningState;
                    if (provisioningState == null) {
                        Log.e("KnoxMUMContainerPolicy", "no ongoing provisioning");
                        return false;
                    }
                    return provisioningState.update(bundle);
                }
                ProvisioningState provisioningState2 = this.mCurrentProvisioningState;
                if (provisioningState2 != null && provisioningState2.state > 1) {
                    Log.e(
                            "KnoxMUMContainerPolicy",
                            "updateProvisioningState() provisioning already ongoing");
                    return false;
                }
                ProvisioningState provisioningState3 = new ProvisioningState();
                this.mCurrentProvisioningState = provisioningState3;
                return provisioningState3.start(bundle);
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
