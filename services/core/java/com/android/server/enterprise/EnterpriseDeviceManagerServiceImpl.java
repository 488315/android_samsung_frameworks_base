package com.android.server.enterprise;

import android.R;
import android.app.ActivityManager;
import android.app.AppGlobals;
import android.app.AppOpsManager;
import android.app.admin.DevicePolicyManager;
import android.app.admin.IDevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.UserInfo;
import android.database.sqlite.SQLiteDatabase;
import android.net.NetworkInfo;
import android.os.Binder;
import android.os.Build;
import android.os.ConditionVariable;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.sec.enterprise.auditlog.AuditLog;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.content.PackageMonitor;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.FunctionalUtils;
import com.android.server.LocalServices;
import com.android.server.LockGuard;
import com.android.server.SystemService;
import com.android.server.enterprise.adapter.AdapterRegistry;
import com.android.server.enterprise.adapter.IPackageManagerAdapter;
import com.android.server.enterprise.adapter.IPersonaManagerAdapter;
import com.android.server.enterprise.adapter.IStorageManagerAdapter;
import com.android.server.enterprise.adapter.ISystemUIAdapter;
import com.android.server.enterprise.adapter.IWindowManagerAdapter;
import com.android.server.enterprise.adapterlayer.PackageManagerAdapter;
import com.android.server.enterprise.adapterlayer.PersonaManagerAdapter;
import com.android.server.enterprise.adapterlayer.StorageManagerAdapter;
import com.android.server.enterprise.adapterlayer.SystemUIAdapter;
import com.android.server.enterprise.adapterlayer.WindowManagerAdapter;
import com.android.server.enterprise.apn.ApnSettingsPolicy;
import com.android.server.enterprise.appconfig.ApplicationRestrictionsService;
import com.android.server.enterprise.application.ApplicationPolicy;
import com.android.server.enterprise.auditlog.AuditLogService;
import com.android.server.enterprise.bluetooth.BluetoothPolicy;
import com.android.server.enterprise.browser.BrowserPolicy;
import com.android.server.enterprise.certificate.CertificatePolicy;
import com.android.server.enterprise.common.EnterprisePermissionChecker;
import com.android.server.enterprise.common.KeyCodeRestrictionCallback;
import com.android.server.enterprise.constrained.ConstrainedModeService;
import com.android.server.enterprise.datetime.DateTimePolicy;
import com.android.server.enterprise.device.DeviceInfo;
import com.android.server.enterprise.dex.DexPolicy;
import com.android.server.enterprise.dualdar.DualDARPolicy;
import com.android.server.enterprise.email.AccountsReceiver;
import com.android.server.enterprise.email.EmailAccountPolicy;
import com.android.server.enterprise.email.EmailPolicy;
import com.android.server.enterprise.email.ExchangeAccountPolicy;
import com.android.server.enterprise.email.LDAPAccountPolicy;
import com.android.server.enterprise.filter.KnoxNetworkFilterService;
import com.android.server.enterprise.firewall.Firewall;
import com.android.server.enterprise.general.MiscPolicy;
import com.android.server.enterprise.geofencing.GeofenceService;
import com.android.server.enterprise.hdm.HdmService;
import com.android.server.enterprise.impl.KeyCodeMediatorImpl;
import com.android.server.enterprise.kioskmode.KioskModeService;
import com.android.server.enterprise.license.EnterpriseLicenseService;
import com.android.server.enterprise.license.LicenseLogService;
import com.android.server.enterprise.location.LocationPolicy;
import com.android.server.enterprise.lso.LSOService;
import com.android.server.enterprise.multiuser.MultiUserManagerService;
import com.android.server.enterprise.nap.NetworkAnalyticsService;
import com.android.server.enterprise.plm.ProcessLifecycleManager;
import com.android.server.enterprise.plm.StartReason;
import com.android.server.enterprise.profile.ProfilePolicyService;
import com.android.server.enterprise.remotecontrol.RemoteInjectionService;
import com.android.server.enterprise.restriction.PhoneRestrictionPolicy;
import com.android.server.enterprise.restriction.RestrictionPolicy;
import com.android.server.enterprise.restriction.RoamingPolicy;
import com.android.server.enterprise.scpm.CloudConfigurationManagerService;
import com.android.server.enterprise.security.DeviceAccountPolicy;
import com.android.server.enterprise.security.PasswordPolicy;
import com.android.server.enterprise.security.SecurityPolicy;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.storage.EdmStorageProviderBase;
import com.android.server.enterprise.threatdefense.ThreatDefenseService;
import com.android.server.enterprise.utils.EnterpriseDumpHelper;
import com.android.server.enterprise.utils.KpuHelper;
import com.android.server.enterprise.utils.SecContentProviderUtil;
import com.android.server.enterprise.utils.Utils;
import com.android.server.enterprise.vpn.VpnInfoPolicy;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.enterprise.wifi.WifiPolicy;
import com.android.server.knox.dar.ddar.proxy.DualDARComnService;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EdmConstants;
import com.samsung.android.knox.EnterpriseDeviceAdminInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.EnterpriseKnoxManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.analytics.activation.ActivationMonitor;
import com.samsung.android.knox.analytics.activation.DevicePolicyListener;
import com.samsung.android.knox.analytics.activation.KESListener;
import com.samsung.android.knox.container.IKnoxContainerManager;
import com.samsung.android.knox.custom.CustomDeviceManager;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.dar.ddar.fsm.StateMachine;
import com.samsung.android.knox.license.IEnterpriseLicense;
import com.samsung.android.knox.license.LicenseInfo;
import com.samsung.android.knox.localservice.ConstrainedModeInternal;
import com.samsung.android.service.RemoteLockControl.KnoxGuard.KnoxGuardVaultException;
import com.samsung.android.service.RemoteLockControl.KnoxGuard.KnoxGuardVaultManager;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes2.dex */
public class EnterpriseDeviceManagerServiceImpl extends EnterpriseDeviceManagerService {
    public static final List CONTAINER_ALLOWED_DEVICE_PERMISSION_LIST;
    public static final List EXCLUDED_ADMINS;
    public static final int MY_PID;
    public static final List NON_MDM_ADMINS;
    public static final String[] allowToSkipRuntimePermission;
    public static final String[] allowToUsingDirectPermissionCheckAPI;
    public static EnterpriseDeviceManagerServiceImpl mInstance;
    public static boolean mIsFirmwareUpgrade;
    public static PackageManagerAdapter mPackageManagerAdapter;
    public static ConditionVariable mServiceAdditionCondition;
    public final ArrayList mAdminList;
    public final HashMap mAdminMap;
    public boolean mAndroidApiLevelUpgraded;
    public ConstrainedModeInternal mConstrainedState;
    public IKnoxContainerManager mContainerService;
    public final Context mContext;
    public int mCurrentUserId;
    public final IDevicePolicyManager mDPMS;
    public boolean mDeferredServicesCreated;
    public EdmStorageProvider mEdmStorageProvider;
    public EnterpriseDumpHelper mEnterpriseDumpHelper;
    public final Injector mInjector;
    public Handler mInternalHandler;
    public final KeyCodeMediatorImpl mKeyCodeMediator;
    public final Object mLockDoNoUseDirectly;
    public MyPackageMonitor mMonitor;
    public final IPackageManager mPMS;
    public IPersonaManagerAdapter mPersonaManagerAdapter;
    public int mPseudoAdminUid;
    public BroadcastReceiver mReceiver;
    public StorageManagerAdapter mStorageManagerAdapter;
    public SystemUIAdapter mSystemUIAdapter;
    public WindowManagerAdapter mWindowManagerAdapter;

    private static native void startRemoteDesktopService();

    public final void sendMigrationIntent(boolean z) {
    }

    public int setB2BMode(boolean z) {
        return 0;
    }

    static {
        ArrayList arrayList = new ArrayList();
        EXCLUDED_ADMINS = arrayList;
        ArrayList arrayList2 = new ArrayList();
        NON_MDM_ADMINS = arrayList2;
        mIsFirmwareUpgrade = false;
        allowToSkipRuntimePermission = new String[]{"com.samsung.android.app.smartscan", "com.samsung.android.knox.dai", "com.samsung.android.knox.kpu", "com.sec.knox.kccagent", "com.samsung.android.knoxcts.test"};
        allowToUsingDirectPermissionCheckAPI = new String[]{"com.samsung.android.knox.kpecore", "com.samsung.android.peripheral.framework", "com.samsung.android.app.kfa"};
        if (isPackageInstalled("com.samsung.android.email.provider", 0)) {
            arrayList.add("com.samsung.android.email.provider");
        } else {
            arrayList.add("com.android.email");
            arrayList.add("com.android.exchange");
        }
        arrayList.add("com.sec.esdk.elm");
        String str = SystemProperties.get(ActivationMonitor.COUNTRY_CODE_PROPERTY, "");
        if (str != null && !str.equalsIgnoreCase(ActivationMonitor.CHINA_COUNTRY_CODE)) {
            arrayList.add(KnoxCustomManagerService.KG_PKG_NAME);
        }
        arrayList2.add("com.samsung.android.email.provider");
        arrayList2.add("com.android.email");
        arrayList2.add("com.android.exchange");
        arrayList2.add("com.nttdocomo.android.wipe");
        arrayList2.add("com.nttdocomo.android.remotelock");
        arrayList2.add("com.sec.enterprise.knox.cloudmdm.smdms");
        arrayList2.add(KnoxCustomManagerService.KG_PKG_NAME);
        mServiceAdditionCondition = new ConditionVariable();
        MY_PID = Process.myPid();
        ArrayList arrayList3 = new ArrayList();
        CONTAINER_ALLOWED_DEVICE_PERMISSION_LIST = arrayList3;
        arrayList3.add("com.samsung.android.knox.permission.KNOX_WIFI");
        arrayList3.add("com.samsung.android.knox.permission.KNOX_SECURITY");
    }

    public final Object getLockObject() {
        LockGuard.guard(9);
        return this.mLockDoNoUseDirectly;
    }

    public final class Lifecycle extends SystemService {
        public EnterpriseDeviceManagerServiceImpl mService;

        public void onCleanupUser(int i) {
        }

        public void onStopUser(int i) {
        }

        public void onUnlockUser(int i) {
        }

        public Lifecycle(Context context) {
            super(context);
            this.mService = new EnterpriseDeviceManagerServiceImpl(context);
        }

        @Override // com.android.server.SystemService
        public void onStart() {
            publishBinderService("enterprise_policy", this.mService);
        }

        @Override // com.android.server.SystemService
        public void onBootPhase(int i) {
            this.mService.systemReady(i);
        }

        public void onStartUser(int i) {
            this.mService.onStartUser(i);
            this.mService.updateCurrentUser();
        }

        public void onSwitchUser(int i) {
            this.mService.updateCurrentUser();
        }
    }

    public class MyPackageMonitor extends PackageMonitor {
        public MyPackageMonitor() {
        }

        /* JADX WARN: Removed duplicated region for block: B:28:0x00ca A[Catch: all -> 0x0162, TryCatch #0 {, blocks: (B:4:0x0007, B:6:0x002d, B:8:0x003d, B:13:0x004d, B:18:0x0074, B:21:0x007a, B:23:0x0082, B:26:0x008b, B:28:0x00ca, B:30:0x0100, B:32:0x009b, B:35:0x00ac, B:37:0x0127, B:12:0x015c, B:41:0x0160), top: B:3:0x0007, inners: #1 }] */
        /* JADX WARN: Removed duplicated region for block: B:30:0x0100 A[Catch: all -> 0x0162, TryCatch #0 {, blocks: (B:4:0x0007, B:6:0x002d, B:8:0x003d, B:13:0x004d, B:18:0x0074, B:21:0x007a, B:23:0x0082, B:26:0x008b, B:28:0x00ca, B:30:0x0100, B:32:0x009b, B:35:0x00ac, B:37:0x0127, B:12:0x015c, B:41:0x0160), top: B:3:0x0007, inners: #1 }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void onSomePackagesChanged() {
            ActivityInfo activityInfo;
            synchronized (EnterpriseDeviceManagerServiceImpl.this.getLockObject()) {
                int changingUserId = getChangingUserId();
                Log.d("EnterpriseDeviceManagerService", "Package has changed for user " + changingUserId);
                for (int size = EnterpriseDeviceManagerServiceImpl.this.mAdminList.size() - 1; size >= 0; size--) {
                    EnterpriseDeviceAdminInfo enterpriseDeviceAdminInfo = (EnterpriseDeviceAdminInfo) EnterpriseDeviceManagerServiceImpl.this.mAdminList.get(size);
                    if (!enterpriseDeviceAdminInfo.isPseudo() && UserHandle.getUserId(enterpriseDeviceAdminInfo.getActivityInfo().applicationInfo.uid) == changingUserId) {
                        String packageName = enterpriseDeviceAdminInfo.getPackageName();
                        Log.d("EnterpriseDeviceManagerService", "Admin package name: " + packageName);
                        int isPackageDisappearing = isPackageDisappearing(packageName);
                        if (isPackageDisappearing != 3 && isPackageDisappearing != 2) {
                            if (isPackageModified(packageName)) {
                                try {
                                } catch (Exception e) {
                                    Log.w("EnterpriseDeviceManagerService", "onSomePackagesChanged EX:" + e.getMessage());
                                    activityInfo = null;
                                }
                                if (!packageName.equals("com.sec.knox.containeragent2") && !packageName.equals("com.samsung.knox.securefolder")) {
                                    activityInfo = EnterpriseDeviceManagerServiceImpl.this.mPMS.getReceiverInfo(enterpriseDeviceAdminInfo.getComponent(), 786432L, changingUserId);
                                    if (activityInfo != null) {
                                        Log.w("EnterpriseDeviceManagerService", "Admin package change removed component: " + enterpriseDeviceAdminInfo.getComponent());
                                        EnterpriseDeviceManagerServiceImpl.this.setAdminRemovable(new ContextInfo(enterpriseDeviceAdminInfo.getActivityInfo().applicationInfo.uid), true, null);
                                        EnterpriseDeviceManagerServiceImpl.this.removeActiveAdminDelayed(enterpriseDeviceAdminInfo.getComponent(), changingUserId);
                                    } else {
                                        Log.w("EnterpriseDeviceManagerService", "Admin package component found: " + enterpriseDeviceAdminInfo.getComponent() + " info:" + activityInfo.toString());
                                    }
                                }
                                activityInfo = EnterpriseDeviceManagerServiceImpl.this.mPMS.getReceiverInfo(enterpriseDeviceAdminInfo.getComponent(), 819328L, changingUserId);
                                if (activityInfo != null) {
                                }
                            }
                        }
                        Log.w("EnterpriseDeviceManagerService", "Admin unexpectedly uninstalled: " + enterpriseDeviceAdminInfo.getComponent());
                        EnterpriseDeviceManagerServiceImpl.this.setAdminRemovable(new ContextInfo(enterpriseDeviceAdminInfo.getActivityInfo().applicationInfo.uid), true, null);
                        EnterpriseDeviceManagerServiceImpl.this.removeActiveAdminDelayed(enterpriseDeviceAdminInfo.getComponent(), changingUserId);
                    }
                }
            }
        }

        public void onPackageUpdateFinished(String str, int i) {
            EnterpriseDeviceAdminInfo enterpriseDeviceAdminInfo;
            EnterpriseDeviceAdminInfo enterpriseDeviceAdminInfo2;
            Log.d("EnterpriseDeviceManagerService", "onPackageUpdateFinished - packageName: " + str + ", uid: " + i);
            synchronized (EnterpriseDeviceManagerServiceImpl.this.getLockObject()) {
                Iterator it = EnterpriseDeviceManagerServiceImpl.this.mAdminMap.entrySet().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        enterpriseDeviceAdminInfo = null;
                        break;
                    }
                    Map.Entry entry = (Map.Entry) it.next();
                    if (!((EnterpriseDeviceAdminInfo) entry.getValue()).isPseudo() && ((EnterpriseDeviceAdminInfo) entry.getValue()).getComponent().getPackageName().equals(str) && i == ((EnterpriseDeviceAdminInfo) entry.getValue()).getActivityInfo().applicationInfo.uid) {
                        Log.d("EnterpriseDeviceManagerService", "Admin found on map with same package name!");
                        enterpriseDeviceAdminInfo = (EnterpriseDeviceAdminInfo) entry.getValue();
                        break;
                    }
                }
                if (enterpriseDeviceAdminInfo != null) {
                    try {
                        enterpriseDeviceAdminInfo2 = EnterpriseDeviceManagerServiceImpl.this.findAdmin(enterpriseDeviceAdminInfo.getComponent(), UserHandle.getUserId(i));
                    } catch (IllegalArgumentException unused) {
                        enterpriseDeviceAdminInfo2 = null;
                    }
                    if (enterpriseDeviceAdminInfo2 == null) {
                        Log.d("EnterpriseDeviceManagerService", "Removing Admin as component name changed");
                        EnterpriseDeviceManagerServiceImpl.this.setAdminRemovable(new ContextInfo(enterpriseDeviceAdminInfo.getActivityInfo().applicationInfo.uid), true, null);
                        EnterpriseDeviceManagerServiceImpl.this.removeActiveAdminDelayed(enterpriseDeviceAdminInfo.getComponent(), UserHandle.getUserId(enterpriseDeviceAdminInfo.getActivityInfo().applicationInfo.uid));
                    } else {
                        EnterpriseDeviceManagerServiceImpl.this.mAdminList.remove(enterpriseDeviceAdminInfo);
                        EnterpriseDeviceManagerServiceImpl.this.mAdminMap.remove(Integer.valueOf(enterpriseDeviceAdminInfo.getActivityInfo().applicationInfo.uid));
                        EnterpriseDeviceManagerServiceImpl.this.mAdminList.add(enterpriseDeviceAdminInfo2);
                        EnterpriseDeviceManagerServiceImpl.this.mAdminMap.put(Integer.valueOf(enterpriseDeviceAdminInfo2.getActivityInfo().applicationInfo.uid), enterpriseDeviceAdminInfo2);
                    }
                }
            }
        }
    }

    public class InternalHandler extends Handler {
        public InternalHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            Log.i("EnterpriseDeviceManagerService", "handleMessage : " + message.what + " " + message.arg1 + " " + message.obj);
            if (message.what != 2) {
                return;
            }
            int i = message.arg2;
            Log.d("EnterpriseDeviceManagerService", "User " + i + " has been removed!");
            EnterpriseDeviceManagerServiceImpl.this.removeActiveAdminDelayed((ComponentName) message.obj, i);
        }
    }

    public final void cleanGenericTableOnUserRemoved(int i) {
        String[] strArr = {String.valueOf(i)};
        this.mEdmStorageProvider.deleteDataByFields("generic", new String[]{"userID"}, strArr);
    }

    public void enforceComponentCheck(ContextInfo contextInfo, ComponentName componentName) {
        ComponentName activeAdminComponent;
        if (componentName != null) {
            int callingUid = Binder.getCallingUid();
            if (UserHandle.getAppId(callingUid) == 1000 || contextInfo.mCallerUid != callingUid || (activeAdminComponent = getActiveAdminComponent()) == null || activeAdminComponent.equals(componentName)) {
                return;
            }
            throw new SecurityException("Component name violation " + componentName.flattenToString());
        }
    }

    public void enforceActiveAdminPermission(List list) {
        enforcePermissionByContext(new ContextInfo(Binder.getCallingUid()), list, false, true, false);
    }

    public ContextInfo enforceActiveAdminPermissionByContext(ContextInfo contextInfo, List list) {
        return enforcePermissionByContext(contextInfo, list, true, true, false);
    }

    public ContextInfo enforcePermissionByContext(ContextInfo contextInfo, List list) {
        return enforcePermissionByContext(contextInfo, list, false, false, false);
    }

    public ContextInfo enforceContainerOwnerShipPermissionByContext(ContextInfo contextInfo, List list) {
        return enforcePermissionByContext(contextInfo, list, true, false, false);
    }

    public ContextInfo enforceDoPoOnlyPermissionByContext(ContextInfo contextInfo, List list) {
        return enforcePermissionByContext(contextInfo, list, true, true, true);
    }

    public ContextInfo enforceOwnerOnlyAndActiveAdminPermission(ContextInfo contextInfo, List list) {
        if (contextInfo == null) {
            contextInfo = new ContextInfo(Binder.getCallingUid());
        }
        ContextInfo contextInfo2 = contextInfo;
        if (!contextInfo2.mParent && !isTargetUserSystem(contextInfo2)) {
            throw new SecurityException("Operation supported only on owner space");
        }
        return enforcePermissionByContext(contextInfo2, list, false, true, false);
    }

    public final boolean isTargetUserSystem(ContextInfo contextInfo) {
        int userHandleGetCallingUserId = this.mInjector.userHandleGetCallingUserId();
        if (contextInfo.mContainerId < 0) {
            Log.e("EnterpriseDeviceManagerService", "Need to check if this is an abnormal case.");
        }
        return userHandleGetCallingUserId == 0 && UserHandle.getUserId(contextInfo.mCallerUid) == 0 && contextInfo.mContainerId == 0;
    }

    public void enforceKnoxV2Permission(String str, String str2) {
        if (str == null || this.mContext.checkPermission(str, Binder.getCallingPid(), Binder.getCallingUid()) == -1) {
            if (this.mInjector.getUserManager().isManagedProfile(UserHandle.getUserId(Binder.getCallingUid()))) {
                if (!this.mInjector.isOrganizationOwnedDeviceWithManagedProfile()) {
                    throw new SecurityException("This API is works only with managedProfile(WPC)");
                }
            } else if (!this.mInjector.hasDeviceOwner()) {
                throw new SecurityException("This API is works only with managed device(DO)");
            }
        }
        if (str2 == null || this.mContext.checkPermission(str2, Binder.getCallingPid(), Binder.getCallingUid()) == -1) {
            throw new SecurityException("Application doesn't have this permission:" + str2);
        }
    }

    public boolean enforceKnoxV2VerifyCaller(int i) {
        int callingUid = Binder.getCallingUid();
        IPackageManager packageManager = AppGlobals.getPackageManager();
        try {
            if (UserHandle.getAppId(callingUid) != 1000) {
                String nameForUid = this.mContext.getPackageManager().getNameForUid(callingUid);
                if (!Arrays.asList(allowToUsingDirectPermissionCheckAPI).contains(nameForUid) || packageManager.checkSignatures("android", nameForUid, UserHandle.getUserId(callingUid)) != 0) {
                    throw new SecurityException("This API can be called by platform signed app only, callerUid:" + callingUid + ",packageName:" + nameForUid);
                }
            }
            String nameForUid2 = this.mContext.getPackageManager().getNameForUid(i);
            return isKpuPermissionGranted(nameForUid2, UserHandle.getUserId(i)) || (Arrays.asList(allowToSkipRuntimePermission).contains(nameForUid2) && packageManager.checkSignatures("android", nameForUid2, UserHandle.getUserId(i)) == 0);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public final boolean isKpuPermissionGranted(String str, int i) {
        try {
            if (AppGlobals.getPackageManager().checkPermission("com.samsung.android.knox.permission.KNOX_KPU_INTERNAL", str, i) == 0) {
                return true;
            }
            Log.d("EnterpriseDeviceManagerService", "Caller does not have KPU permission");
            return false;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean hasDelegatedPermission(String str, int i, String str2) {
        int callingUid = Binder.getCallingUid();
        boolean z = false;
        if (!checkCallerIsKPECore(this.mContext.getPackageManager().getNameForUid(callingUid), UserHandle.getUserId(callingUid))) {
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                z = this.mDPMS.hasDelegatedPermission(str, i, str2);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return z;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void setDelegatorAdminUid(ContextInfo contextInfo, int i) {
        try {
            Field declaredField = contextInfo.getClass().getDeclaredField("mCallerUid");
            declaredField.setAccessible(true);
            declaredField.setInt(contextInfo, i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isCallerValidKPU(ContextInfo contextInfo) {
        return KpuHelper.getInstance(this.mContext).isCallerValidKpu(contextInfo);
    }

    public String getKPUPackageName() {
        return KpuHelper.getInstance(this.mContext).getKpuPackageName();
    }

    public boolean isKPUPlatformSigned(String str, int i) {
        return KpuHelper.getInstance(this.mContext).isKpuPlatformSigned(str, i);
    }

    public final boolean isDelegationEnabledForDO(ContextInfo contextInfo) {
        RestrictionPolicy restrictionPolicy = (RestrictionPolicy) EnterpriseService.getPolicyService("restriction_policy");
        if (restrictionPolicy != null) {
            return restrictionPolicy.isKnoxDelegationEnabled(contextInfo);
        }
        return false;
    }

    public final int getDeviceOwnerUid() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                ComponentName deviceOwnerComponentOnAnyUser = ((DevicePolicyManager) this.mContext.getSystemService(DevicePolicyManager.class)).getDeviceOwnerComponentOnAnyUser();
                if (deviceOwnerComponentOnAnyUser != null) {
                    return this.mContext.getPackageManager().getApplicationInfo(deviceOwnerComponentOnAnyUser.getPackageName(), 0).uid;
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                Log.e("EnterpriseDeviceManagerService", "Failed to get application info for DO component.");
            } catch (Exception e2) {
                e2.printStackTrace();
                Log.e("EnterpriseDeviceManagerService", "Failed to retrieve DO component on device");
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return -1;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:104:0x01de  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x0221  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final ContextInfo enforcePermissionByContext(ContextInfo contextInfo, List list, boolean z, boolean z2, boolean z3) {
        boolean z4;
        int i;
        int callingUid = Binder.getCallingUid();
        int callingPid = Binder.getCallingPid();
        if (contextInfo == null) {
            contextInfo = new ContextInfo(callingUid);
        }
        int callingOrUserUid = Utils.getCallingOrUserUid(contextInfo);
        String nameForUid = this.mContext.getPackageManager().getNameForUid(callingUid);
        if ((callingUid == 1000 && callingPid == MY_PID) || (nameForUid != null && nameForUid.equals("android.uid.system:1000"))) {
            return (z && this.mPersonaManagerAdapter.isValidKnoxId(contextInfo.mContainerId)) ? new ContextInfo(callingOrUserUid) : contextInfo;
        }
        if (this.mPersonaManagerAdapter.isContainerService(callingPid)) {
            return new ContextInfo(callingOrUserUid);
        }
        boolean isCallerValidKPU = isCallerValidKPU(contextInfo);
        if ("com.samsung.android.appseparation".equals(this.mContext.getPackageManager().getNameForUid(callingUid)) && (UserHandle.getUserId(callingUid) == 0 || SemPersonaManager.isAppSeparationUserId(this.mContext, UserHandle.getUserId(callingUid)))) {
            Log.d("EnterpriseDeviceManagerService", "Called from Separated Apps agent. return Context.");
            return new ContextInfo(callingOrUserUid);
        }
        if (nameForUid != null && checkCallerIsKPECore(nameForUid, UserHandle.getUserId(callingUid))) {
            return new ContextInfo((contextInfo.mContainerId * 100000) + 1000);
        }
        if (z2 && ((EnterpriseDeviceAdminInfo) this.mAdminMap.get(Integer.valueOf(callingOrUserUid))) == null) {
            throw new SecurityException("No active admin owned by uid " + callingOrUserUid);
        }
        if (checkCallerIsUMC()) {
            enforceUMCSignature();
            if (z) {
                checkContainerOwnerShipForUMC(contextInfo);
            }
            if (list != null && list.size() > 0 && checkProxyAdminPermission(contextInfo, list)) {
                return (z && this.mPersonaManagerAdapter.isValidKnoxId(contextInfo.mContainerId)) ? new ContextInfo(callingOrUserUid) : contextInfo;
            }
        }
        if (!isCallerValidKPU && contextInfo.mCallerUid != callingUid) {
            if (contextInfo.mCallerUid == getAuthorizedAdminUid(callingUid)) {
                if (z) {
                    checkContainerOwnerShipForUMC(contextInfo);
                }
                if (list != null && list.size() > 0 && checkProxyAdminPermission(contextInfo, list)) {
                    return (z && this.mPersonaManagerAdapter.isValidKnoxId(contextInfo.mContainerId)) ? new ContextInfo(callingOrUserUid) : contextInfo;
                }
            }
        }
        if (!isCallerValidKPU && !contextInfo.mParent && (i = contextInfo.mCallerUid) != callingUid && callingUid != Utils.getProxyAdminOwnerUid(this.mEdmStorageProvider, i)) {
            throw new SecurityException("ContextInfo UID violation info is " + contextInfo.mCallerUid + " but caller is " + callingUid);
        }
        if (z3 && !isCallerDeviceOwner() && !isProfileOwnerApp(contextInfo) && !isCallerValidKPU && (!contextInfo.mParent || contextInfo.mCallerUid != this.mPseudoAdminUid)) {
            throw new SecurityException("caller is not DO or PO or KPU: " + contextInfo.mCallerUid);
        }
        if (z) {
            checkContainerOwnerShip(contextInfo, isCallerValidKPU);
        }
        if (list != null) {
            try {
                if (list.size() > 0) {
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        if (this.mContext.checkCallingPermission((String) it.next()) == 0) {
                            z4 = true;
                            break;
                        }
                    }
                }
            } catch (Exception unused) {
                z4 = false;
                Log.w("EnterpriseDeviceManagerService", "could not check calling permission");
                StringBuilder sb = new StringBuilder();
                if (!z4) {
                }
            }
        }
        z4 = false;
        try {
            Log.d("EnterpriseDeviceManagerService", "is permission granted: " + z4);
        } catch (Exception unused2) {
            Log.w("EnterpriseDeviceManagerService", "could not check calling permission");
            StringBuilder sb2 = new StringBuilder();
            if (!z4) {
            }
        }
        StringBuilder sb22 = new StringBuilder();
        if (!z4) {
            if (list != null && list.size() > 0) {
                sb22.append((String) list.get(0));
                for (int i2 = 1; i2 < list.size(); i2++) {
                    sb22.append(" OR ");
                    sb22.append((String) list.get(i2));
                }
            }
            throw new SecurityException("Admin  does not have " + sb22.toString());
        }
        if (contextInfo.mParent && this.mPseudoAdminUid != -1) {
            enforceWpcod(callingUid, false);
            setDelegatorAdminUid(contextInfo, this.mPseudoAdminUid);
            Log.d("EnterpriseDeviceManagerService", "enforceActiveAdminPermissionByContext() - return contextInfo with PseudoUid = " + this.mPseudoAdminUid);
            return new ContextInfo(this.mPseudoAdminUid, true);
        }
        if (z && this.mPersonaManagerAdapter.isValidKnoxId(contextInfo.mContainerId)) {
            if (isCallerValidKPU) {
                int mUMContainerOwnerUid = this.mEdmStorageProvider.getMUMContainerOwnerUid(contextInfo.mContainerId);
                setDelegatorAdminUid(contextInfo, mUMContainerOwnerUid);
                return new ContextInfo(mUMContainerOwnerUid);
            }
            return new ContextInfo(callingOrUserUid);
        }
        if (isCallerValidKPU && isDelegationEnabledForDO(contextInfo)) {
            int deviceOwnerUid = getDeviceOwnerUid();
            setDelegatorAdminUid(contextInfo, deviceOwnerUid);
            return new ContextInfo(deviceOwnerUid);
        }
        Log.d("EnterpriseDeviceManagerService", "enforceActiveAdminDualPermission >>>");
        return contextInfo;
    }

    public ContextInfo getAdminContextIfCallerInCertWhiteList(List list) {
        boolean z;
        Integer asInteger;
        int callingUid = Binder.getCallingUid();
        int userId = UserHandle.getUserId(callingUid);
        Context createContextAsUser = Utils.createContextAsUser(this.mContext, "android", 0, userId);
        ContentValues contentValues = new ContentValues();
        contentValues.put(EdmStorageProviderBase.getAdminLUIDWhereIn(0, userId), "#SelectClause#");
        for (ContentValues contentValues2 : this.mEdmStorageProvider.getValues("CertificateWhiteListTable", new String[]{"adminUid", "packageName", "signature"}, contentValues)) {
            String asString = contentValues2.getAsString("packageName");
            String asString2 = contentValues2.getAsString("signature");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            if (createContextAsUser != null) {
                try {
                    try {
                        PackageManager packageManager = createContextAsUser.getPackageManager();
                        if (packageManager != null && packageManager.getPackageUid(asString, 0) == callingUid) {
                            if (TextUtils.isEmpty(asString2)) {
                                z = true;
                            } else {
                                String[] packagesForUid = packageManager.getPackagesForUid(callingUid);
                                if (packagesForUid != null) {
                                    z = false;
                                    for (String str : packagesForUid) {
                                        z = Utils.comparePackageSignature(createContextAsUser, str, asString2);
                                        if (z) {
                                            break;
                                        }
                                    }
                                } else {
                                    z = false;
                                }
                            }
                            if (z && (asInteger = contentValues2.getAsInteger("adminUid")) != null && list != null && !list.isEmpty()) {
                                Iterator it = list.iterator();
                                while (it.hasNext()) {
                                    String str2 = (String) it.next();
                                    try {
                                    } catch (RemoteException unused) {
                                        Log.w("EnterpriseDeviceManagerService", "Could not check permission " + str2 + " of the admin that has added caller to cert white list");
                                    }
                                    if (AppGlobals.getPackageManager().checkUidPermission(str2, asInteger.intValue()) == 0) {
                                        return new ContextInfo(asInteger.intValue());
                                    }
                                    continue;
                                }
                            }
                        }
                    } catch (Exception unused2) {
                        Log.d("EnterpriseDeviceManagerService", "Package added to certificate whitelisted not installed");
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }
        return null;
    }

    public final void checkContainerOwnerShip(ContextInfo contextInfo, boolean z) {
        if (contextInfo.mParent) {
            return;
        }
        if ((!this.mPersonaManagerAdapter.isValidKnoxId(UserHandle.getUserId(contextInfo.mCallerUid)) && !this.mPersonaManagerAdapter.isValidKnoxId(contextInfo.mContainerId)) || z || this.mEdmStorageProvider.getMUMContainerOwnerUid(contextInfo.mContainerId) == Binder.getCallingUid()) {
            return;
        }
        throw new SecurityException("Admin doesn't own container " + contextInfo.mContainerId + " ContextInfo.uid " + contextInfo.mCallerUid);
    }

    public final void checkContainerOwnerShipForUMC(ContextInfo contextInfo) {
        if (contextInfo.mParent) {
            return;
        }
        int userId = UserHandle.getUserId(contextInfo.mCallerUid);
        if (this.mPersonaManagerAdapter.isValidKnoxId(userId) || this.mPersonaManagerAdapter.isValidKnoxId(contextInfo.mContainerId)) {
            if (this.mPersonaManagerAdapter.isValidKnoxId(contextInfo.mContainerId)) {
                userId = contextInfo.mContainerId;
            }
            if (this.mEdmStorageProvider.getMUMContainerOwnerUid(userId) == contextInfo.mCallerUid) {
                return;
            }
            throw new SecurityException("Admin doesn't own container id " + userId + " ContextInfo.uid " + contextInfo.mCallerUid);
        }
    }

    public final EnterpriseDeviceAdminInfo getActiveAdminLocked(ComponentName componentName, int i) {
        Iterator it = this.mAdminList.iterator();
        while (it.hasNext()) {
            EnterpriseDeviceAdminInfo enterpriseDeviceAdminInfo = (EnterpriseDeviceAdminInfo) it.next();
            if (enterpriseDeviceAdminInfo.getComponent().equals(componentName) && UserHandle.getUserId(enterpriseDeviceAdminInfo.getActivityInfo().applicationInfo.uid) == i) {
                Log.d("EnterpriseDeviceManagerService", "Admin found on user " + i + ": " + componentName);
                return enterpriseDeviceAdminInfo;
            }
        }
        return null;
    }

    public ComponentName getActiveAdminComponent() {
        EnterpriseDeviceAdminInfo enterpriseDeviceAdminInfo = (EnterpriseDeviceAdminInfo) this.mAdminMap.get(Integer.valueOf(Binder.getCallingUid()));
        if (enterpriseDeviceAdminInfo != null) {
            return enterpriseDeviceAdminInfo.getComponent();
        }
        return null;
    }

    public boolean hasGrantedPolicy(ComponentName componentName, int i) {
        return hasGrantedPolicy(componentName, i, this.mInjector.userHandleGetCallingUserId());
    }

    public boolean hasGrantedPolicy(ComponentName componentName, int i, int i2) {
        boolean usesPolicy;
        if (i < 22) {
            return this.mDPMS.hasGrantedPolicy(componentName, i, i2);
        }
        synchronized (getLockObject()) {
            EnterpriseDeviceAdminInfo activeAdminLocked = getActiveAdminLocked(componentName, i2);
            if (activeAdminLocked == null) {
                throw new SecurityException("No active admin " + componentName + " on user " + i2);
            }
            usesPolicy = activeAdminLocked.usesPolicy(i);
        }
        return usesPolicy;
    }

    public void setActiveAdmin(ComponentName componentName, boolean z) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.BIND_DEVICE_ADMIN", null);
        activateAdmin(componentName, z);
    }

    public final void activateAdmin(ComponentName componentName, boolean z) {
        boolean z2;
        int userHandleGetCallingUserId = this.mInjector.userHandleGetCallingUserId();
        Log.d("EnterpriseDeviceManagerService", "Activating admin on user!!!!!! " + userHandleGetCallingUserId);
        EnterpriseDeviceAdminInfo findAdmin = findAdmin(componentName, userHandleGetCallingUserId);
        if (findAdmin == null) {
            throw new IllegalArgumentException("Bad admin: " + componentName);
        }
        RestrictionPolicy restrictionPolicy = (RestrictionPolicy) EnterpriseService.getPolicyService("restriction_policy");
        if (restrictionPolicy != null && !restrictionPolicy.checkAdminActivationEnabled(userHandleGetCallingUserId, componentName.getPackageName())) {
            throw new IllegalArgumentException("Admin cannot be activated");
        }
        boolean z3 = false;
        if (SystemProperties.getInt("ro.product.first_api_level", 0) < 30 || KnoxCustomManagerService.KG_PKG_NAME.equals(componentName.getPackageName())) {
            adjustKnoxGuardAdmin();
        }
        if (findAdmin.usesMDMPolicy() && !EXCLUDED_ADMINS.contains(componentName.getPackageName())) {
            startDeferredServicesIfNeeded();
        }
        int i = findAdmin.getActivityInfo().applicationInfo.uid;
        ComponentName component = findAdmin.getComponent();
        Log.d("EnterpriseDeviceManagerService", "Admin uid: " + i + ", Component name: " + component);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                synchronized (getLockObject()) {
                    if (!z) {
                        try {
                            if (getActiveAdminLocked(component, UserHandle.getUserId(i)) != null) {
                                throw new IllegalArgumentException("Admin is already added");
                            }
                        } finally {
                        }
                    }
                    if (z) {
                        z2 = true;
                    } else {
                        Log.d("EnterpriseDeviceManagerService", "Adding admin " + i + " to lists");
                        this.mAdminMap.put(Integer.valueOf(i), findAdmin);
                        this.mAdminList.add(findAdmin);
                        z2 = this.mEdmStorageProvider.addorUpdateAdmin(i, component.flattenToString(), KnoxCustomManagerService.KG_PKG_NAME.equals(componentName.getPackageName()) ^ true, 0);
                    }
                    if (!z2) {
                        Log.d("EnterpriseDeviceManagerService", "Removing admin " + i + " from lists");
                        this.mAdminList.remove(findAdmin);
                        this.mAdminMap.remove(Integer.valueOf(i));
                        throw new IllegalArgumentException("Unable to activate admin");
                    }
                    Iterator it = EnterpriseService.getPolicyServices().entrySet().iterator();
                    while (it.hasNext()) {
                        ((EnterpriseServiceCallback) ((Map.Entry) it.next()).getValue()).onAdminAdded(i);
                    }
                }
                int i2 = 1;
                while (true) {
                    if (i2 > 2) {
                        break;
                    }
                    Log.d("EnterpriseDeviceManagerService", "Attempt " + i2 + " to active admin in DPM");
                    this.mDPMS.setActiveAdmin(componentName, z, userHandleGetCallingUserId);
                    if (this.mDPMS.isAdminActive(componentName, userHandleGetCallingUserId)) {
                        Log.d("EnterpriseDeviceManagerService", "Admin sucessfully activated in DPM for user " + userHandleGetCallingUserId);
                        z3 = true;
                        break;
                    }
                    Log.d("EnterpriseDeviceManagerService", "Admin activation failed for user " + userHandleGetCallingUserId);
                    i2++;
                }
                Log.d("EnterpriseDeviceManagerService", "EDM setActiveAdmin activationStatus -" + z3 + " for user - " + userHandleGetCallingUserId);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        if (!z3) {
            Log.d("EnterpriseDeviceManagerService", "Started removing admin information...");
            setAdminRemovable(new ContextInfo(i), true, null);
            removeActiveAdminDelayed(componentName, userHandleGetCallingUserId);
            throw new IllegalArgumentException("EDM - Admin activation failed for user -" + userHandleGetCallingUserId);
        }
        Log.d("EnterpriseDeviceManagerService", "Admin added to DPM!");
    }

    public void removeActiveAdmin(ComponentName componentName) {
        removeActiveAdmin(componentName, this.mInjector.userHandleGetCallingUserId());
    }

    public final void removeActiveAdmin(ComponentName componentName, int i) {
        Log.d("EnterpriseDeviceManagerService", "removeActiveAdmin() : Removing admin " + componentName + " from user " + i + ", caller : " + Binder.getCallingUid());
        try {
            this.mDPMS.removeActiveAdmin(componentName, i);
        } catch (IllegalArgumentException e) {
            Log.e("EnterpriseDeviceManagerService", "failed to remove action admin " + e.getMessage());
        } catch (IllegalStateException e2) {
            Log.e("EnterpriseDeviceManagerService", "failed to remove action admin " + e2.getMessage());
        } catch (SecurityException e3) {
            Log.e("EnterpriseDeviceManagerService", "failed to remove action admin " + e3.getMessage());
        }
        Log.d("EnterpriseDeviceManagerService", "Admin removed from DPM!");
    }

    public void removeActiveAdminFromDpm(ComponentName componentName, int i) {
        Log.d("EnterpriseDeviceManagerService", "Removing admin " + componentName + " from user " + i + " from DPM");
        removeActiveAdminDelayed(componentName, i);
    }

    public final void removeActiveAdminDelayed(ComponentName componentName, int i) {
        removeActiveAdminDelayed(componentName, i, false);
    }

    public final void removeActiveAdminDelayed(ComponentName componentName, int i, boolean z) {
        List activeAdmins;
        Log.d("EnterpriseDeviceManagerService", "removeActiveAdminDelayed - adminReceiver: " + componentName + ", userId: " + i);
        EnterpriseDeviceAdminInfo activeAdminLocked = getActiveAdminLocked(componentName, i);
        if (activeAdminLocked == null) {
            return;
        }
        int i2 = activeAdminLocked.getActivityInfo().applicationInfo.uid;
        int callingUid = Binder.getCallingUid();
        Log.d("EnterpriseDeviceManagerService", "Admin uid: " + i2 + ", calling uid: " + callingUid);
        if (i2 != callingUid) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.BIND_DEVICE_ADMIN", "Only system or itself can remove an EDM admin");
        }
        if (!this.mEdmStorageProvider.canRemoveAdmin(i2)) {
            Log.d("EnterpriseDeviceManagerService", "Admin " + i2 + " cannot be removed!");
            return;
        }
        this.mConstrainedState.cleanUpConstrainedState(i2);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                try {
                    boolean checkPseudoAdminForUid = this.mEdmStorageProvider.checkPseudoAdminForUid(i2);
                    if (!checkPseudoAdminForUid) {
                        this.mKeyCodeMediator.onPreAdminRemoval(i2);
                        Iterator it = EnterpriseService.getPolicyServices().entrySet().iterator();
                        while (it.hasNext()) {
                            try {
                                ((EnterpriseServiceCallback) ((Map.Entry) it.next()).getValue()).onPreAdminRemoval(i2);
                            } catch (Exception e) {
                                Log.w("EnterpriseDeviceManagerService", "removeActiveAdminDelayed Ex1:", e);
                            }
                        }
                    }
                    synchronized (getLockObject()) {
                        try {
                            Log.d("EnterpriseDeviceManagerService", "Removing Admin with uid" + i2);
                            this.mAdminList.remove(activeAdminLocked);
                            if (!checkPseudoAdminForUid) {
                                this.mAdminMap.remove(Integer.valueOf(i2));
                                this.mEdmStorageProvider.removeAdminFromDatabase(i2);
                            }
                        } catch (Exception e2) {
                            Log.w("EnterpriseDeviceManagerService", "FATAL: Admin failed to remove lets try during next boot up", e2);
                        }
                    }
                    if (!checkPseudoAdminForUid) {
                        Iterator it2 = EnterpriseService.getPolicyServices().entrySet().iterator();
                        while (it2.hasNext()) {
                            try {
                                ((EnterpriseServiceCallback) ((Map.Entry) it2.next()).getValue()).onAdminRemoved(i2, z);
                            } catch (Exception e3) {
                                Log.w("EnterpriseDeviceManagerService", "removeActiveAdminDelayed Ex2:", e3);
                            }
                        }
                        this.mKeyCodeMediator.onAdminRemoved(i2);
                    }
                    if (componentName != null && i2 == 1000 && (activeAdmins = this.mDPMS.getActiveAdmins(UserHandle.getUserId(i2))) != null) {
                        Iterator it3 = activeAdmins.iterator();
                        while (true) {
                            if (!it3.hasNext()) {
                                break;
                            }
                            ComponentName componentName2 = (ComponentName) it3.next();
                            EnterpriseDeviceAdminInfo activeAdminLocked2 = getActiveAdminLocked(componentName2, i);
                            if (activeAdminLocked2 == null || activeAdminLocked2.getActivityInfo().applicationInfo.uid == 1000) {
                                if (!componentName.getPackageName().equals(componentName2.getPackageName())) {
                                    reconcileAdmin(componentName2, UserHandle.getUserId(i2));
                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put("adminUid", Integer.valueOf(i2));
                                    List<ContentValues> values = this.mEdmStorageProvider.getValues("PROXY_ADMIN_INFO", new String[]{"adminUid", "proxyUid"}, contentValues);
                                    if (values != null) {
                                        for (ContentValues contentValues2 : values) {
                                            Integer asInteger = contentValues2.getAsInteger("adminUid");
                                            Integer asInteger2 = contentValues2.getAsInteger("proxyUid");
                                            if (asInteger != null && asInteger2 != null && asInteger.intValue() == i2) {
                                                this.mEdmStorageProvider.addMUMContainer(UserHandle.getUserId(asInteger2.intValue()), asInteger.intValue());
                                                Log.d("EnterpriseDeviceManagerService", "MUMContainer relation rebuilt : " + UserHandle.getUserId(asInteger2.intValue()) + " - " + asInteger);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } catch (RuntimeException e4) {
                    throw e4;
                }
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        } catch (Exception e5) {
            e5.printStackTrace();
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        this.mInjector.SecContentProviderNotifyPolicyChangesAsUser("ADMIN_REMOVED", i);
    }

    public EnterpriseDeviceAdminInfo findAdmin(ComponentName componentName, int i) {
        ActivityInfo activityInfo;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            activityInfo = this.mPMS.getReceiverInfo(componentName, 819328L, i);
        } catch (Exception unused) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            activityInfo = null;
        } catch (Throwable th) {
            throw th;
        }
        if (activityInfo == null) {
            throw new IllegalArgumentException("Unknown admin: " + componentName);
        }
        clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ResolveInfo resolveInfo = new ResolveInfo();
            resolveInfo.activityInfo = activityInfo;
            return new EnterpriseDeviceAdminInfo(this.mContext, resolveInfo);
        } catch (IOException unused2) {
            return null;
        } catch (XmlPullParserException unused3) {
            return null;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void reconcileAdmin(ComponentName componentName, int i) {
        if (Binder.getCallingPid() != MY_PID) {
            throw new SecurityException("Need to be System Process");
        }
        try {
            EnterpriseDeviceAdminInfo findAdmin = findAdmin(componentName, i);
            if (findAdmin != null) {
                int i2 = findAdmin.getActivityInfo().applicationInfo.uid;
                synchronized (getLockObject()) {
                    if (!this.mAdminMap.containsKey(Integer.valueOf(i2))) {
                        this.mAdminMap.put(Integer.valueOf(i2), findAdmin);
                        this.mAdminList.add(findAdmin);
                        if (!this.mEdmStorageProvider.addorUpdateAdmin(i2, componentName.flattenToString(), !KnoxCustomManagerService.KG_PKG_NAME.equals(componentName.getPackageName()), 0)) {
                            this.mAdminList.remove(findAdmin);
                            this.mAdminMap.remove(Integer.valueOf(i2));
                        } else {
                            Iterator it = EnterpriseService.getPolicyServices().entrySet().iterator();
                            while (it.hasNext()) {
                                ((EnterpriseServiceCallback) ((Map.Entry) it.next()).getValue()).onAdminAdded(i2);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.w("EnterpriseDeviceManagerService", "Failed reconcileAdmin for " + componentName.getPackageName() + "for user id " + i);
            e.printStackTrace();
        }
    }

    public void transferOwnerShip(ComponentName componentName, ComponentName componentName2, int i) {
        reconcileAdmin(componentName2, i);
        removeActiveAdminDelayed(componentName, i, true);
        try {
            int packageUidAsUser = this.mContext.getPackageManager().getPackageUidAsUser(componentName2.getPackageName(), i);
            ContentValues contentValues = new ContentValues();
            contentValues.put("adminUid", Integer.valueOf(packageUidAsUser));
            if (this.mEdmStorageProvider.getCount("ADMIN", contentValues) > 0) {
                this.mEdmStorageProvider.addMUMContainer(i, packageUidAsUser);
            } else {
                Log.d("EnterpriseDeviceManagerService", "targetUID : " + packageUidAsUser + " not present in db");
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("EnterpriseDeviceManagerService", "transfer Ownership failed", e);
        }
    }

    public boolean isPossibleTransferOwenerShip(ComponentName componentName) {
        return !checkAdminExistsInELMDB(componentName.getPackageName());
    }

    public final void loadActiveAdmins() {
        List<ComponentName> list;
        boolean z;
        EnterpriseDeviceAdminInfo findAdmin;
        synchronized (getLockObject()) {
            Log.w("EnterpriseDeviceManagerService", "loadActiveAdmins");
            Iterator it = this.mEdmStorageProvider.getAdminUidList().iterator();
            while (true) {
                boolean z2 = false;
                if (!it.hasNext()) {
                    break;
                }
                int intValue = ((Integer) it.next()).intValue();
                try {
                    z2 = this.mEdmStorageProvider.checkPseudoAdminForUid(intValue);
                } catch (Exception e) {
                    Log.d("EnterpriseDeviceManagerService", "mEdmStorageProvider.checkPseudoAdminForUid: error " + e.getMessage());
                }
                if (z2) {
                    EnterpriseDeviceAdminInfo enterpriseDeviceAdminInfo = new EnterpriseDeviceAdminInfo(true);
                    this.mPseudoAdminUid = intValue;
                    this.mAdminMap.put(Integer.valueOf(intValue), enterpriseDeviceAdminInfo);
                } else {
                    ComponentName componentNameForUid = this.mEdmStorageProvider.getComponentNameForUid(intValue);
                    if (componentNameForUid != null) {
                        try {
                            EnterpriseDeviceAdminInfo findAdmin2 = findAdmin(componentNameForUid, UserHandle.getUserId(intValue));
                            if (findAdmin2 != null) {
                                int i = findAdmin2.getActivityInfo().applicationInfo.uid;
                                this.mAdminMap.put(Integer.valueOf(i), findAdmin2);
                                this.mAdminList.add(findAdmin2);
                                if (i == intValue) {
                                    try {
                                        if (!this.mDPMS.isAdminActive(componentNameForUid, UserHandle.getUserId(intValue))) {
                                            setAdminRemovable(new ContextInfo(intValue), true, null);
                                            removeActiveAdminDelayed(findAdmin2.getComponent(), UserHandle.getUserId(intValue));
                                            Log.d("EnterpriseDeviceManagerService", "Admin invalid on DPM, removing from EDM:   " + componentNameForUid.flattenToString() + ", uid: " + intValue);
                                        }
                                    } catch (RemoteException e2) {
                                        e2.printStackTrace();
                                    }
                                } else if (this.mEdmStorageProvider.putInt(intValue, "ADMIN_INFO", "adminUid", i)) {
                                    Log.d("EnterpriseDeviceManagerService", "Admin " + componentNameForUid.flattenToString() + ", updated with new currentUid: " + i + ", old storedUid: " + intValue);
                                    try {
                                        if (!this.mDPMS.isAdminActive(componentNameForUid, UserHandle.getUserId(intValue))) {
                                            setAdminRemovable(new ContextInfo(intValue), true, null);
                                            removeActiveAdminDelayed(findAdmin2.getComponent(), UserHandle.getUserId(intValue));
                                            Log.d("EnterpriseDeviceManagerService", "Admin invalid on DPM, removing from EDM: " + componentNameForUid.flattenToString() + ", uid: " + i);
                                        }
                                    } catch (RemoteException e3) {
                                        e3.printStackTrace();
                                    }
                                } else {
                                    findAdmin2.getActivityInfo().applicationInfo.uid = intValue;
                                    setAdminRemovable(new ContextInfo(intValue), true, null);
                                    removeActiveAdminDelayed(findAdmin2.getComponent(), UserHandle.getUserId(intValue));
                                    Log.d("EnterpriseDeviceManagerService", "Failed updating uid, removed: " + componentNameForUid.flattenToString() + ", uid: " + intValue);
                                }
                            } else {
                                setAdminRemovable(new ContextInfo(intValue), true, null);
                                removeActiveAdminDelayed(componentNameForUid, UserHandle.getUserId(intValue));
                                Log.d("EnterpriseDeviceManagerService", "Admin loaded null, removed: " + componentNameForUid.flattenToString() + ", uid: " + intValue);
                            }
                        } catch (RuntimeException e4) {
                            Log.e("EnterpriseDeviceManagerService", "Exception occured while loading active admins " + e4.getMessage());
                        }
                    }
                }
            }
            for (UserInfo userInfo : ((UserManager) this.mContext.getSystemService("user")).getUsers()) {
                List activeAdminsInfo = getActiveAdminsInfo(userInfo.id);
                try {
                    list = this.mDPMS.getActiveAdmins(userInfo.id);
                } catch (RemoteException e5) {
                    Log.e("EnterpriseDeviceManagerService", "Failed to get active admins from dpm " + e5.getMessage());
                    list = null;
                }
                if (list != null) {
                    if (activeAdminsInfo != null && !activeAdminsInfo.isEmpty()) {
                        for (ComponentName componentName : list) {
                            Iterator it2 = activeAdminsInfo.iterator();
                            while (true) {
                                if (it2.hasNext()) {
                                    if (((EnterpriseDeviceAdminInfo) it2.next()).getComponent().equals(componentName)) {
                                        z = true;
                                        break;
                                    }
                                } else {
                                    z = false;
                                    break;
                                }
                            }
                            if (!z && (findAdmin = findAdmin(componentName, userInfo.id)) != null) {
                                Log.d("EnterpriseDeviceManagerService", "Adding missing admin to EDM : " + componentName);
                                int i2 = findAdmin.getActivityInfo().applicationInfo.uid;
                                this.mAdminMap.put(Integer.valueOf(i2), findAdmin);
                                this.mAdminList.add(findAdmin);
                                this.mEdmStorageProvider.addAdmin(i2, componentName.flattenToString(), true, 0);
                            }
                        }
                    }
                    Log.d("EnterpriseDeviceManagerService", "Adding all admins from DPM for user : " + userInfo.id);
                    for (ComponentName componentName2 : list) {
                        EnterpriseDeviceAdminInfo findAdmin3 = findAdmin(componentName2, userInfo.id);
                        if (findAdmin3 != null) {
                            Log.d("EnterpriseDeviceManagerService", "Adding missing admin to EDM : " + componentName2);
                            int i3 = findAdmin3.getActivityInfo().applicationInfo.uid;
                            this.mAdminMap.put(Integer.valueOf(i3), findAdmin3);
                            this.mAdminList.add(findAdmin3);
                            this.mEdmStorageProvider.addAdmin(i3, componentName2.flattenToString(), true, 0);
                        }
                    }
                }
            }
        }
    }

    public boolean isAdminActive(ComponentName componentName) {
        boolean z;
        synchronized (getLockObject()) {
            z = getActiveAdminLocked(componentName, this.mInjector.userHandleGetCallingUserId()) != null;
        }
        return z;
    }

    @Override // com.android.server.enterprise.EnterpriseDeviceManagerService
    public List getActiveAdmins(int i) {
        enforceCallingOrSelfPermissions(this.mContext, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_PROXY_ADMIN_INTERNAL")), null);
        synchronized (getLockObject()) {
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = this.mAdminList;
            if (arrayList2 != null && !arrayList2.isEmpty()) {
                Iterator it = this.mAdminList.iterator();
                while (it.hasNext()) {
                    EnterpriseDeviceAdminInfo enterpriseDeviceAdminInfo = (EnterpriseDeviceAdminInfo) it.next();
                    if (-10000 == i || -1 == i || UserHandle.getUserId(enterpriseDeviceAdminInfo.getActivityInfo().applicationInfo.uid) == i) {
                        arrayList.add(enterpriseDeviceAdminInfo.getComponent());
                    }
                }
                return arrayList;
            }
            return arrayList;
        }
    }

    @Override // com.android.server.enterprise.EnterpriseDeviceManagerService
    public List getActiveAdminsInfo(int i) {
        enforceCallingOrSelfPermissions(this.mContext, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_PROXY_ADMIN_INTERNAL")), null);
        synchronized (getLockObject()) {
            if (i == -10000) {
                return new ArrayList(this.mAdminList);
            }
            ArrayList arrayList = new ArrayList();
            Iterator it = this.mAdminList.iterator();
            while (it.hasNext()) {
                EnterpriseDeviceAdminInfo enterpriseDeviceAdminInfo = (EnterpriseDeviceAdminInfo) it.next();
                if (UserHandle.getUserId(enterpriseDeviceAdminInfo.getActivityInfo().applicationInfo.uid) == i) {
                    arrayList.add(enterpriseDeviceAdminInfo);
                }
            }
            return arrayList;
        }
    }

    public void getRemoveWarning(ComponentName componentName, RemoteCallback remoteCallback) {
        this.mDPMS.getRemoveWarning(componentName, remoteCallback, this.mInjector.userHandleGetCallingUserId());
    }

    @Override // com.android.server.enterprise.EnterpriseDeviceManagerService
    public boolean setAdminRemovable(ContextInfo contextInfo, boolean z, String str) {
        int i;
        ArrayList arrayList = new ArrayList();
        arrayList.add("com.samsung.android.knox.permission.KNOX_ENTERPRISE_DEVICE_ADMIN");
        ContextInfo enforceActiveAdminPermissionByContext = enforceActiveAdminPermissionByContext(contextInfo, arrayList);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceActiveAdminPermissionByContext);
        if (str == null) {
            i = enforceActiveAdminPermissionByContext.mCallerUid;
        } else {
            ApplicationInfo applicationInfo = mPackageManagerAdapter.getApplicationInfo(str, 0, callingOrCurrentUserId);
            if (applicationInfo == null) {
                Log.w("EnterpriseDeviceManagerService", "Can't found packageName");
                return false;
            }
            i = applicationInfo != null ? applicationInfo.uid : -1;
        }
        if (this.mAdminMap.get(Integer.valueOf(i)) == null) {
            Log.d("EnterpriseDeviceManagerService", "Admin is not active");
            return false;
        }
        if (i != enforceActiveAdminPermissionByContext.mCallerUid && hasKnoxInternalExceptionPermission(str, callingOrCurrentUserId) && !isCallerValidKPU(enforceActiveAdminPermissionByContext)) {
            Log.d("EnterpriseDeviceManagerService", "Samsung internal services cannot be controlled by other admin");
            return false;
        }
        Log.w("EnterpriseDeviceManagerService", "setAdminRemovable : callingUid = " + i);
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(i, "ADMIN_INFO", "canRemove", z);
        if (putBoolean) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (str == null) {
                    AuditLog.logAsUser(5, 1, true, Process.myPid(), "EnterpriseDeviceManagerService", String.format(z ? "Admin %d has set itself as removable" : "Admin %d has set itself as not removable", Integer.valueOf(enforceActiveAdminPermissionByContext.mCallerUid)), callingOrCurrentUserId);
                } else {
                    AuditLog.logAsUser(5, 1, true, Process.myPid(), "EnterpriseDeviceManagerService", String.format(z ? "Admin %d has set %s as removable" : "Admin %d has set %s as not removable", Integer.valueOf(enforceActiveAdminPermissionByContext.mCallerUid), str), callingOrCurrentUserId);
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return putBoolean;
    }

    @Override // com.android.server.enterprise.EnterpriseDeviceManagerService
    public boolean getAdminRemovable(ContextInfo contextInfo, String str) {
        int i;
        if (str == null) {
            i = contextInfo.mCallerUid;
        } else {
            ApplicationInfo applicationInfo = mPackageManagerAdapter.getApplicationInfo(str, 0, Utils.getCallingOrCurrentUserId(contextInfo));
            if (applicationInfo == null) {
                return true;
            }
            i = applicationInfo.uid;
        }
        return this.mEdmStorageProvider.canRemoveAdmin(i);
    }

    public final boolean hasKnoxInternalExceptionPermission(String str, int i) {
        try {
            return AppGlobals.getPackageManager().checkPermission("com.samsung.android.knox.permission.KNOX_INTERNAL_EXCEPTION", str, i) == 0;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public final void systemReady(int i) {
        Log.d("EnterpriseDeviceManagerService", "systemReady() : mCurrentPhase = " + i);
        if (i == 480) {
            onStartUser(0);
        } else if (i == 500) {
            systemReady();
        }
        RestrictionPolicy restrictionPolicy = (RestrictionPolicy) EnterpriseService.getPolicyService("restriction_policy");
        if (restrictionPolicy != null) {
            restrictionPolicy.systemReady(i);
        }
    }

    public final void onStartUser(int i) {
        Log.d("EnterpriseDeviceManagerService", "onStartUser() : userId = " + i);
    }

    public final void updateCurrentUser() {
        int i;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                i = ActivityManager.getCurrentUser();
            } catch (Exception e) {
                Log.e("EnterpriseDeviceManagerService", "updateCurrentUser() : cannot get current userId. ", e);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                i = 0;
            }
            int i2 = this.mCurrentUserId;
            if (i2 != i) {
                this.mCurrentUserId = i;
                Log.d("EnterpriseDeviceManagerService", "updateCurrentUser() : User changed ( " + i2 + ", " + this.mCurrentUserId + ")");
                this.mSystemUIAdapter.updateSystemUIMonitor(this.mCurrentUserId);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void systemReady() {
        ConstrainedModeInternal constrainedModeInternal;
        Log.w("EnterpriseDeviceManagerService", "systemReady()");
        Log.d("EnterpriseDeviceManagerService", "ConstrainedModeService move to system ready ");
        this.mInjector.addConstrainedModeService();
        this.mConstrainedState = (ConstrainedModeInternal) LocalServices.getService(ConstrainedModeInternal.class);
        if (SystemProperties.getInt("ro.product.first_api_level", 0) < 30) {
            adjustKnoxGuardAdmin();
        }
        if (deviceOwnerExists() || isMdmAdminPresentInternal() || ((constrainedModeInternal = this.mConstrainedState) != null && constrainedModeInternal.checkConstrainedState())) {
            createDeferredServices();
        }
        ProcessLifecycleManager.getInstance(this.mContext).start(StartReason.EDM_SYSTEM_READY);
        RestrictionToastManager.init(this.mContext);
        new AccountsReceiver(this.mContext);
        HandlerThread handlerThread = new HandlerThread("InternalHandlerThread", 10);
        handlerThread.start();
        InternalHandler internalHandler = new InternalHandler(handlerThread.getLooper());
        this.mInternalHandler = internalHandler;
        internalHandler.post(new Runnable() { // from class: com.android.server.enterprise.EnterpriseDeviceManagerServiceImpl.2
            @Override // java.lang.Runnable
            public void run() {
                try {
                    System.loadLibrary("edmnativehelperservice");
                } catch (Error e) {
                    e.printStackTrace();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
        loadActiveAdmins();
        this.mInjector.invokeSystemReadyIfNeeded();
        try {
            startRemoteDesktopService();
        } catch (Error e) {
            Log.e("EnterpriseDeviceManagerService", "Failed to start remote desktop service.", e);
        } catch (Exception e2) {
            Log.e("EnterpriseDeviceManagerService", "Failed to start remote desktop service.", e2);
        }
        Handler handler = this.mInternalHandler;
        if (handler != null) {
            handler.post(new Runnable() { // from class: com.android.server.enterprise.EnterpriseDeviceManagerServiceImpl$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    EnterpriseDeviceManagerServiceImpl.this.lambda$systemReady$0();
                }
            });
        }
        handleLegacyAccessoryHandler();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$systemReady$0() {
        this.mKeyCodeMediator.onSystemReady();
    }

    public final void handleLegacyAccessoryHandler() {
        new Thread() { // from class: com.android.server.enterprise.EnterpriseDeviceManagerServiceImpl.3
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                try {
                    Log.d("EnterpriseDeviceManagerService", "Start the handling for mateagent");
                    Settings.Secure.putInt(EnterpriseDeviceManagerServiceImpl.this.mContext.getContentResolver(), "mate_setting_activation", 0);
                    if (EnterpriseDeviceManagerServiceImpl.mPackageManagerAdapter.isApplicationInstalled("com.samsung.android.mateagent", 0)) {
                        Log.d("EnterpriseDeviceManagerService", "Start the deleting for mateagent");
                        EnterpriseDeviceManagerServiceImpl.mPackageManagerAdapter.deletePackage("com.samsung.android.mateagent", 0);
                    }
                } catch (Exception e) {
                    Log.e("EnterpriseDeviceManagerService", "Failed to delete mateagent package", e);
                }
            }
        }.start();
    }

    public final boolean deviceOwnerExists() {
        if (((DevicePolicyManager) this.mContext.getSystemService(DevicePolicyManager.class)).getDeviceOwner() == null) {
            return false;
        }
        Log.d("EnterpriseDeviceManagerService", "deviceOwnerExists.");
        return true;
    }

    public final void registerDeferredBroadcastReceiver() {
        if (this.mMonitor == null) {
            MyPackageMonitor myPackageMonitor = new MyPackageMonitor();
            this.mMonitor = myPackageMonitor;
            myPackageMonitor.register(this.mContext, (Looper) null, new UserHandle(-1), true);
        }
        for (Map.Entry entry : EnterpriseService.getPolicyServices().entrySet()) {
            if (((EnterpriseServiceCallback) entry.getValue()).hasDeferredBroadcastReceiverToRegister()) {
                ((EnterpriseServiceCallback) entry.getValue()).registerDeferredBoradcastReceiver();
            }
        }
    }

    public final boolean checkAdminExistsInELMDB(String str) {
        LicenseInfo[] allLicenseInfo;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                EnterpriseLicenseService enterpriseLicenseService = (EnterpriseLicenseService) EnterpriseService.getPolicyService("enterprise_license_policy");
                if (enterpriseLicenseService != null && (allLicenseInfo = enterpriseLicenseService.getAllLicenseInfo()) != null) {
                    for (LicenseInfo licenseInfo : allLicenseInfo) {
                        if (licenseInfo.getPackageName().equals(str)) {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            return true;
                        }
                    }
                }
            } catch (Exception e) {
                Log.w("EnterpriseDeviceManagerService", "checkAdminExistsInELMDB Ex: " + e);
            }
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void handleDowngrade() {
        Log.i("EnterpriseDeviceManagerService", "handleDowngrade: Checking Downgrade...");
        SQLiteDatabase sQLiteDatabase = null;
        try {
            sQLiteDatabase = this.mContext.openOrCreateDatabase("enterprise.db", 0, null);
        } catch (Exception e) {
            Log.w("EnterpriseDeviceManagerService", "could not open or create databse" + e);
        }
        if (sQLiteDatabase != null) {
            int version = sQLiteDatabase.getVersion();
            sQLiteDatabase.close();
            if (version > 9) {
                Log.i("EnterpriseDeviceManagerService", "Current Platform Version is older than the previous DB version");
                Log.i("EnterpriseDeviceManagerService", "Deleting EDM Databases - enterprise.db and dmapprmgr.db");
                new File("/data/system/enterprise.db").delete();
                new File("/data/system/dmappmgr.db").delete();
                sendMigrationIntent(false);
            }
        }
    }

    public static EnterpriseDeviceManagerServiceImpl getInstance() {
        return mInstance;
    }

    @Override // com.android.server.enterprise.EnterpriseDeviceManagerService
    public boolean getFirmwareUpgrade() {
        return mIsFirmwareUpgrade;
    }

    public class Injector {
        public final Context mContext;

        public Injector(Context context) {
            this.mContext = context;
        }

        public IPackageManager getPackageManagerInstance() {
            return IPackageManager.Stub.asInterface(ServiceManager.getService("package"));
        }

        public IDevicePolicyManager getDpmInstance() {
            return IDevicePolicyManager.Stub.asInterface(ServiceManager.getService("device_policy"));
        }

        public IEnterpriseLicense getEnterpriseLicenseManager() {
            return IEnterpriseLicense.Stub.asInterface(ServiceManager.getService("enterprise_license_policy"));
        }

        public SystemUIAdapter getSystemUIAdapterInstance() {
            return SystemUIAdapter.getInstance(this.mContext);
        }

        public PackageManagerAdapter getPackageManagerAdapterInstance() {
            return PackageManagerAdapter.getInstance(this.mContext);
        }

        public StorageManagerAdapter getStorageManagerAdapterInstance() {
            return StorageManagerAdapter.getInstance(this.mContext);
        }

        public WindowManagerAdapter getWindowManagerAdapterInstance() {
            return WindowManagerAdapter.getInstance();
        }

        public IPersonaManagerAdapter getPersonaManagerAdapterInstance() {
            return PersonaManagerAdapter.getInstance(this.mContext);
        }

        public void serviceManagerAddService(String str, IBinder iBinder) {
            ServiceManager.addService(str, iBinder);
        }

        public void addSystemService(String str, EnterpriseServiceCallback enterpriseServiceCallback) {
            EnterpriseService.addSystemService(str, enterpriseServiceCallback);
        }

        public boolean isFirmwareChanged() {
            return EnterpriseDeviceManagerServiceImpl.mInstance.isFirmwareChanged();
        }

        public EdmStorageProvider getStorageProvider() {
            return new EdmStorageProvider(this.mContext);
        }

        public EnterpriseLicenseService getEnterpriseLicenseService() {
            return new EnterpriseLicenseService(this.mContext, getPackageManagerInstance());
        }

        public ApplicationPolicy getApplicationPolicy() {
            return new ApplicationPolicy(this.mContext);
        }

        public WifiPolicy getWifiPolicy() {
            return new WifiPolicy(this.mContext);
        }

        public PhoneRestrictionPolicy getPhoneRestrictionPolicy() {
            return new PhoneRestrictionPolicy(this.mContext);
        }

        public RemoteInjectionService getRemoteInjectionService() {
            return new RemoteInjectionService(this.mContext);
        }

        public RestrictionPolicy getRestrictionPolicy() {
            return new RestrictionPolicy(this.mContext);
        }

        public HdmService getHdmService() {
            return new HdmService(this.mContext);
        }

        public PasswordPolicy getPasswordPolicy() {
            return new PasswordPolicy(this.mContext);
        }

        public int userHandleGetCallingUserId() {
            return UserHandle.getCallingUserId();
        }

        public DevicePolicyManager getDevicePolicyManager() {
            return (DevicePolicyManager) this.mContext.getSystemService("device_policy");
        }

        public boolean hasDeviceOwner() {
            return getDevicePolicyManager().semGetDeviceOwner() != null;
        }

        public boolean isOrganizationOwnedDeviceWithManagedProfile() {
            return getDevicePolicyManager().isOrganizationOwnedDeviceWithManagedProfile();
        }

        public UserManager getUserManager() {
            return UserManager.get(this.mContext);
        }

        public void addLazySystemService(String str, EnterpriseServiceCallback enterpriseServiceCallback) {
            EnterpriseService.addLazySystemService(str, enterpriseServiceCallback);
        }

        public void addConstrainedModeService() {
            ConstrainedModeService.addService(this.mContext);
        }

        public void SecContentProviderNotifyPolicyChangesAsUser(String str, int i) {
            SecContentProviderUtil.notifyPolicyChangesAsUser(this.mContext, str, i);
        }

        public void invokeSystemReadyIfNeeded() {
            EnterpriseService.invokeSystemReadyIfNeeded();
        }

        public void invokeSystemReadyIfNeeded(EnterpriseServiceCallback enterpriseServiceCallback, String str) {
            EnterpriseService.invokeSystemReadyIfNeeded(enterpriseServiceCallback, str);
        }

        public AppOpsManager getAppOpsManager() {
            return (AppOpsManager) this.mContext.getSystemService(AppOpsManager.class);
        }

        public void binderWithCleanCallingIdentity(FunctionalUtils.ThrowingRunnable throwingRunnable) {
            Binder.withCleanCallingIdentity(throwingRunnable);
        }
    }

    public EnterpriseDeviceManagerServiceImpl(Context context) {
        this(new Injector(context));
    }

    public EnterpriseDeviceManagerServiceImpl(Injector injector) {
        this.mKeyCodeMediator = new KeyCodeMediatorImpl();
        this.mCurrentUserId = -1;
        this.mLockDoNoUseDirectly = LockGuard.installNewLock(9, true);
        this.mAndroidApiLevelUpgraded = false;
        this.mDeferredServicesCreated = false;
        this.mContainerService = null;
        this.mAdminMap = new HashMap();
        this.mAdminList = new ArrayList();
        this.mPseudoAdminUid = -1;
        this.mReceiver = new BroadcastReceiver() { // from class: com.android.server.enterprise.EnterpriseDeviceManagerServiceImpl.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (action == null) {
                    Log.w("EnterpriseDeviceManagerService", "action is null!");
                    return;
                }
                boolean z = false;
                if (action.equals("android.intent.action.USER_REMOVED")) {
                    int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
                    if (intExtra < 1) {
                        return;
                    }
                    EnterpriseDeviceManagerServiceImpl.this.cleanGenericTableOnUserRemoved(intExtra);
                    if (EnterpriseDeviceManagerServiceImpl.this.mPersonaManagerAdapter.isValidKnoxId(intExtra) && EnterpriseDeviceManagerServiceImpl.this.mPersonaManagerAdapter.isLegacyContainer(intExtra)) {
                        z = true;
                    }
                    if (z) {
                        return;
                    }
                    Log.d("EnterpriseDeviceManagerService", "ACTION_USER_REMOVED removing pseudo admin since associated profile is getting removed.");
                    EnterpriseDeviceManagerServiceImpl.this.removePseudoAdmin();
                    return;
                }
                if (action.equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                    NetworkInfo networkInfo = (NetworkInfo) intent.getParcelableExtra("networkInfo");
                    if (networkInfo == null) {
                        Log.d("EnterpriseDeviceManagerService", "networkInfo is null");
                        return;
                    } else {
                        if (intent.getBooleanExtra("noConnectivity", false) || !networkInfo.isConnected()) {
                            return;
                        }
                        Intent intent2 = new Intent("com.samsung.android.knox.intent.action.KES_TRIGGER");
                        intent2.setPackage("com.sec.enterprise.knox.cloudmdm.smdms");
                        EnterpriseDeviceManagerServiceImpl.this.mContext.sendBroadcastAsUser(intent2, UserHandle.OWNER);
                        return;
                    }
                }
                if (action.equals(DevicePolicyListener.ACTION_DEVICE_OWNER_CHANGED)) {
                    EnterpriseDeviceManagerServiceImpl.this.startDeferredServicesIfNeeded();
                    EnterpriseDeviceManagerServiceImpl.this.mContext.unregisterReceiver(this);
                    EnterpriseDeviceManagerServiceImpl.this.registerBroadcastReceiver();
                }
            }
        };
        this.mInjector = injector;
        mInstance = this;
        EnterpriseService.setEdmsInstance(this);
        Context context = injector.mContext;
        this.mContext = context;
        this.mPMS = injector.getPackageManagerInstance();
        this.mDPMS = injector.getDpmInstance();
        registerAdaptors();
        handleDowngrade();
        registerBroadcastReceiver();
        this.mEdmStorageProvider = injector.getStorageProvider();
        if (injector.isFirmwareChanged()) {
            Log.i("EnterpriseDeviceManagerService", "handleUpgrade: Checking Upgrade...");
            mIsFirmwareUpgrade = true;
            this.mEdmStorageProvider.handleFirmwareUpgrade();
            sendMigrationIntent(true);
            String str = SystemProperties.get("ro.build.fingerprint", "unknown");
            if (!str.equals("unknown")) {
                this.mEdmStorageProvider.setDatabaseUpgradeValue("PlatformSoftwareVersion", str);
            }
        }
        updateDbForAndroidApiLevel();
        injector.addLazySystemService("enterprise_license_policy", injector.getEnterpriseLicenseService());
        injector.addSystemService("application_policy", injector.getApplicationPolicy());
        injector.addSystemService("profilepolicy", new ProfilePolicyService(context));
        EnterpriseService.addPolicyService("wifi_policy", injector.getWifiPolicy());
        EnterpriseService.addPolicyService("phone_restriction_policy", injector.getPhoneRestrictionPolicy());
        injector.addLazySystemService("remoteinjection", injector.getRemoteInjectionService());
        injector.addSystemService("restriction_policy", injector.getRestrictionPolicy());
        injector.addLazySystemService("password_policy", injector.getPasswordPolicy());
        injector.serviceManagerAddService("edm_proxy", new EDMProxyService(context));
        injector.addLazySystemService("hdm_service", injector.getHdmService());
        ApplicationRestrictionsService.addService(context);
        CloudConfigurationManagerService.addService(context);
        setMediators();
    }

    public final void updateDbForAndroidApiLevel() {
        int i = Build.VERSION.SDK_INT;
        if (getAndroidApiLevelFromDb() < i) {
            Log.d("EnterpriseDeviceManagerService", "Android API Level is just changed to " + i);
            this.mAndroidApiLevelUpgraded = true;
            this.mEdmStorageProvider.setDatabaseUpgradeValue("PlatformSdkApiLevel", String.valueOf(i));
            return;
        }
        Log.d("EnterpriseDeviceManagerService", "Android API Level is " + i);
    }

    public final int getAndroidApiLevelFromDb() {
        try {
            return Integer.valueOf(this.mEdmStorageProvider.getDatabaseUpgradeValue("PlatformSdkApiLevel")).intValue();
        } catch (NumberFormatException unused) {
            Log.d("EnterpriseDeviceManagerService", "No written value");
            return 30;
        }
    }

    private void registerAdaptors() {
        this.mSystemUIAdapter = this.mInjector.getSystemUIAdapterInstance();
        mPackageManagerAdapter = this.mInjector.getPackageManagerAdapterInstance();
        this.mStorageManagerAdapter = this.mInjector.getStorageManagerAdapterInstance();
        this.mWindowManagerAdapter = this.mInjector.getWindowManagerAdapterInstance();
        this.mPersonaManagerAdapter = this.mInjector.getPersonaManagerAdapterInstance();
        AdapterRegistry.registerAdapter(ISystemUIAdapter.class, this.mSystemUIAdapter);
        AdapterRegistry.registerAdapter(IPackageManagerAdapter.class, mPackageManagerAdapter);
        AdapterRegistry.registerAdapter(IStorageManagerAdapter.class, this.mStorageManagerAdapter);
        AdapterRegistry.registerAdapter(IWindowManagerAdapter.class, this.mWindowManagerAdapter);
        AdapterRegistry.registerAdapter(IPersonaManagerAdapter.class, this.mPersonaManagerAdapter);
    }

    public final void registerBroadcastReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.USER_REMOVED");
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        if (!deviceOwnerExists()) {
            intentFilter.addAction(DevicePolicyListener.ACTION_DEVICE_OWNER_CHANGED);
        }
        this.mContext.registerReceiver(this.mReceiver, intentFilter);
    }

    public final void createDeferredServices() {
        if (this.mDeferredServicesCreated) {
            Log.d("EnterpriseDeviceManagerService", "createDeferredServices() : Skip to create");
            return;
        }
        Log.d("EnterpriseDeviceManagerService", "createDeferredServices() : Adding services ... ");
        try {
            EnterpriseService.wakeUpLazyServices();
            this.mInjector.addSystemService("wifi_policy", (EnterpriseServiceCallback) EnterpriseService.getPolicyService("wifi_policy"));
            this.mInjector.addSystemService("phone_restriction_policy", (EnterpriseServiceCallback) EnterpriseService.getPolicyService("phone_restriction_policy"));
            this.mInjector.addSystemService("device_info", new DeviceInfo(this.mContext));
            this.mInjector.addSystemService("license_log_service", new LicenseLogService(this.mContext));
            this.mInjector.addSystemService("auditlog", new AuditLogService(this.mContext));
            this.mInjector.addSystemService("eas_account_policy", new ExchangeAccountPolicy(this.mContext));
            this.mInjector.addSystemService("email_account_policy", new EmailAccountPolicy(this.mContext));
            this.mInjector.addSystemService("location_policy", new LocationPolicy(this.mContext));
            this.mInjector.addSystemService("misc_policy", new MiscPolicy(this.mContext));
            this.mInjector.addLazySystemService("vpn_policy", new VpnInfoPolicy(this.mContext));
            this.mInjector.addSystemService("security_policy", new SecurityPolicy(this.mContext));
            this.mInjector.addSystemService("roaming_policy", new RoamingPolicy(this.mContext));
            this.mInjector.addSystemService("bluetooth_policy", new BluetoothPolicy(this.mContext));
            this.mInjector.addSystemService("email_policy", new EmailPolicy(this.mContext));
            this.mInjector.addSystemService("firewall", new Firewall(this.mContext));
            this.mInjector.addSystemService("certificate_policy", new CertificatePolicy(this.mContext));
            this.mInjector.addSystemService("apn_settings_policy", new ApnSettingsPolicy(this.mContext));
            this.mInjector.addSystemService("browser_policy", new BrowserPolicy(this.mContext));
            this.mInjector.addSystemService("date_time_policy", new DateTimePolicy(this.mContext));
            this.mInjector.addSystemService("kioskmode", new KioskModeService(this.mContext));
            this.mInjector.addSystemService("ldap_account_policy", new LDAPAccountPolicy(this.mContext));
            this.mInjector.addSystemService("lockscreen_overlay", new LSOService(this.mContext));
            this.mInjector.addSystemService("geofencing", new GeofenceService(this.mContext));
            this.mInjector.addSystemService("device_account_policy", new DeviceAccountPolicy(this.mContext));
            this.mInjector.addSystemService("multi_user_manager_service", new MultiUserManagerService(this.mContext));
            this.mInjector.addSystemService("dex_policy", new DexPolicy(this.mContext));
            if (EnterpriseService.getPolicyService("knox_adapter_service") == null) {
                this.mInjector.addSystemService("knox_adapter_service", new DualDARComnService(this.mContext));
                this.mInjector.addSystemService("DualDARPolicy", new DualDARPolicy(this.mContext));
            }
            EnterpriseKnoxManager.createInstance(new ContextInfo());
            EdmConstants.EnterpriseKnoxSdkVersion enterpriseKnoxSdkVersion = EdmConstants.getEnterpriseKnoxSdkVersion();
            if (enterpriseKnoxSdkVersion.ordinal() >= 24) {
                Log.d("EnterpriseDeviceManagerService", "ThreatDefenseService - knoxSdkVersion : " + enterpriseKnoxSdkVersion.ordinal());
                this.mInjector.addSystemService("threat_defense_service", new ThreatDefenseService(this.mContext));
            }
            this.mInjector.addLazySystemService("knoxnap", new NetworkAnalyticsService(this.mContext));
            this.mInjector.addLazySystemService("knox_nwFilterMgr_policy", new KnoxNetworkFilterService(this.mContext));
            ProcessLifecycleManager.getInstance(this.mContext).start(StartReason.EDM_SERVICE_READY);
        } catch (Throwable th) {
            Log.e("EnterpriseDeviceManagerService", "addServices() : Failure creating Policy services" + th);
            th.printStackTrace();
        }
        this.mDeferredServicesCreated = true;
        setMediators();
        this.mInjector.invokeSystemReadyIfNeeded();
        registerDeferredBroadcastReceiver();
    }

    public final void setMediators() {
        for (EnterpriseServiceCallback enterpriseServiceCallback : EnterpriseService.getPolicyServices().values()) {
            if (enterpriseServiceCallback instanceof KeyCodeRestrictionCallback) {
                ((KeyCodeRestrictionCallback) enterpriseServiceCallback).setMediator(this.mKeyCodeMediator);
            }
        }
    }

    public boolean hasAnyActiveAdmin() {
        HashMap hashMap = this.mAdminMap;
        return hashMap != null && hashMap.size() > 0;
    }

    public boolean packageHasActiveAdmins(String str) {
        return packageHasActiveAdminsAsUser(str, this.mInjector.userHandleGetCallingUserId());
    }

    public boolean packageHasActiveAdminsAsUser(String str, int i) {
        if (!hasFullCrossUsersPermission(i)) {
            Log.d("EnterpriseDeviceManagerService", "packageHasActiveAdminsAsUser caller need INTERACT_ACROSS_USERS_FULL permission");
            return false;
        }
        synchronized (getLockObject()) {
            int size = this.mAdminList.size();
            for (int i2 = 0; i2 < size; i2++) {
                if (((EnterpriseDeviceAdminInfo) this.mAdminList.get(i2)).getComponent().getPackageName().equals(str) && UserHandle.getUserId(((EnterpriseDeviceAdminInfo) this.mAdminList.get(i2)).getActivityInfo().applicationInfo.uid) == i) {
                    Log.d("EnterpriseDeviceManagerService", " packageHasActiveAdminsAsUser " + ((EnterpriseDeviceAdminInfo) this.mAdminList.get(i2)).getComponent().getPackageName() + " userID" + i);
                    return true;
                }
            }
            return false;
        }
    }

    public final boolean hasFullCrossUsersPermission(int i) {
        int callingUid = Binder.getCallingUid();
        return i == UserHandle.getUserId(callingUid) || UserHandle.isSameApp(callingUid, 1000) || UserHandle.isSameApp(callingUid, 0) || hasCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL");
    }

    public final boolean hasCallingOrSelfPermission(String str) {
        return this.mContext.checkCallingOrSelfPermission(str) == 0;
    }

    @Override // com.android.server.enterprise.EnterpriseDeviceManagerService
    public boolean isAdminRemovable(ComponentName componentName) {
        return isAdminRemovableInternal(componentName, this.mInjector.userHandleGetCallingUserId());
    }

    public boolean isAdminRemovableInternal(ComponentName componentName, int i) {
        Log.d("EnterpriseDeviceManagerService", "isAdminRemovableInternal: " + componentName + ", userHandle = " + i);
        EnterpriseDeviceAdminInfo findAdmin = findAdmin(componentName, i);
        if (findAdmin == null) {
            throw new IllegalArgumentException("Bad admin: " + componentName);
        }
        boolean canRemoveAdmin = this.mEdmStorageProvider.canRemoveAdmin(findAdmin.getActivityInfo().applicationInfo.uid);
        Log.d("EnterpriseDeviceManagerService", "isAdminRemovableInternal : " + canRemoveAdmin);
        return canRemoveAdmin;
    }

    public final boolean isFirmwareChanged() {
        String databaseUpgradeValue = this.mEdmStorageProvider.getDatabaseUpgradeValue("PlatformSoftwareVersion");
        String str = SystemProperties.get("ro.build.fingerprint", "unknown");
        if (str.equalsIgnoreCase("unknown")) {
            str = null;
        }
        if (databaseUpgradeValue != null) {
            return (str == null || str.equals(databaseUpgradeValue)) ? false : true;
        }
        return true;
    }

    public final boolean isMdmAdminPresentInternal() {
        try {
            Iterator it = this.mEdmStorageProvider.getAdminUidList().iterator();
            while (it.hasNext()) {
                int intValue = ((Integer) it.next()).intValue();
                ComponentName componentNameForUid = this.mEdmStorageProvider.getComponentNameForUid(intValue);
                if (componentNameForUid != null && !EXCLUDED_ADMINS.contains(componentNameForUid.getPackageName())) {
                    if (findAdmin(componentNameForUid, UserHandle.getUserId(intValue)) != null && findAdmin(componentNameForUid, UserHandle.getUserId(intValue)).usesMDMPolicy()) {
                        Log.d("EnterpriseDeviceManagerService", "isMdmAdminPresentInternal() : MDM Admin Found - " + componentNameForUid.getPackageName());
                        return true;
                    }
                    IEnterpriseLicense enterpriseLicenseManager = this.mInjector.getEnterpriseLicenseManager();
                    List arrayList = new ArrayList();
                    if (enterpriseLicenseManager != null) {
                        try {
                            arrayList = enterpriseLicenseManager.getELMPermissions(componentNameForUid.getPackageName());
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                    if (arrayList != null && arrayList.size() > 0) {
                        Log.d("EnterpriseDeviceManagerService", "isMdmAdminPresentInternal() : MDM Admin Found(2) - " + componentNameForUid.getPackageName());
                        return true;
                    }
                }
            }
            return false;
        } catch (Exception e2) {
            Log.e("EnterpriseDeviceManagerService", "isMdmAdminPresentInternal() : failed. ", e2);
            return true;
        }
    }

    @Override // com.android.server.enterprise.EnterpriseDeviceManagerService
    public boolean isMdmAdminPresent() {
        return isMdmAdminPresentInternal();
    }

    public boolean isMdmAdminPresentAsUser(int i) {
        try {
            Iterator it = this.mEdmStorageProvider.getAdminUidListAsUser(i).iterator();
            while (it.hasNext()) {
                int intValue = ((Integer) it.next()).intValue();
                ComponentName componentNameForUid = this.mEdmStorageProvider.getComponentNameForUid(intValue);
                if (componentNameForUid != null && !EXCLUDED_ADMINS.contains(componentNameForUid.getPackageName())) {
                    if (findAdmin(componentNameForUid, UserHandle.getUserId(intValue)) != null && findAdmin(componentNameForUid, UserHandle.getUserId(intValue)).usesMDMPolicy()) {
                        Log.d("EnterpriseDeviceManagerService", "isMdmAdminPresentAsUser() : MDM Admin Found - " + componentNameForUid.getPackageName());
                        return true;
                    }
                    IEnterpriseLicense enterpriseLicenseManager = this.mInjector.getEnterpriseLicenseManager();
                    List arrayList = new ArrayList();
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    if (enterpriseLicenseManager != null) {
                        try {
                            try {
                                arrayList = enterpriseLicenseManager.getELMPermissions(componentNameForUid.getPackageName());
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        } catch (Throwable th) {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            throw th;
                        }
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    if (arrayList != null && arrayList.size() > 0) {
                        Log.d("EnterpriseDeviceManagerService", "isMdmAdminPresentAsUser() : MDM Admin Found(2) - " + componentNameForUid.getPackageName());
                        return true;
                    }
                }
            }
            Log.d("EnterpriseDeviceManagerService", "isMdmAdminPresentAsUser() : MDM Admin is not present.");
            return false;
        } catch (Exception e2) {
            Log.e("EnterpriseDeviceManagerService", "isMdmAdminPresentAsUser() : failed. ", e2);
            return true;
        }
    }

    @Override // com.android.server.enterprise.EnterpriseDeviceManagerService
    public void startDeferredServicesIfNeeded() {
        Handler handler;
        if (this.mDeferredServicesCreated || (handler = this.mInternalHandler) == null) {
            return;
        }
        handler.postAtFrontOfQueue(new Runnable() { // from class: com.android.server.enterprise.EnterpriseDeviceManagerServiceImpl$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                EnterpriseDeviceManagerServiceImpl.this.lambda$startDeferredServicesIfNeeded$1();
            }
        });
        mServiceAdditionCondition.block();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startDeferredServicesIfNeeded$1() {
        createDeferredServices();
        this.mContext.sendBroadcast(new Intent("com.samsung.android.knox.intent.action.EDM_BOOT_COMPLETED_INTERNAL"));
        this.mContext.sendBroadcast(new Intent("edm.intent.action.ACTION_EDM_BOOT_COMPLETED"));
        mServiceAdditionCondition.open();
    }

    public void startDualDARServices() {
        if (UserHandle.getAppId(Binder.getCallingUid()) != 5250) {
            throw new SecurityException("Only KnoxCore app can start DualDAR services");
        }
        Log.d("EnterpriseDeviceManagerService", "Start DualDAR Services");
        if (!this.mDeferredServicesCreated) {
            if (this.mInternalHandler != null) {
                Log.d("EnterpriseDeviceManagerService", "Add DualDAR services and request to be ready");
                this.mInternalHandler.postAtFrontOfQueue(new Runnable() { // from class: com.android.server.enterprise.EnterpriseDeviceManagerServiceImpl$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        EnterpriseDeviceManagerServiceImpl.this.lambda$startDualDARServices$2();
                    }
                });
                mServiceAdditionCondition.block();
                return;
            }
            return;
        }
        Log.d("EnterpriseDeviceManagerService", "Deferred services have already created. set initial state DualDAR");
        try {
            StateMachine.setInitialState();
        } catch (Exception e) {
            Log.e("EnterpriseDeviceManagerService", "DualDAR stateMachine initiate failed!");
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startDualDARServices$2() {
        DualDARComnService dualDARComnService = new DualDARComnService(this.mContext);
        this.mInjector.addSystemService("knox_adapter_service", dualDARComnService);
        this.mInjector.invokeSystemReadyIfNeeded(dualDARComnService, "knox_adapter_service");
        DualDARPolicy dualDARPolicy = new DualDARPolicy(this.mContext);
        this.mInjector.addSystemService("DualDARPolicy", dualDARPolicy);
        this.mInjector.invokeSystemReadyIfNeeded(dualDARPolicy, "DualDARPolicy");
        mServiceAdditionCondition.open();
    }

    public void setActiveAdminSilent(ComponentName componentName) {
        enforceCallingOrSelfPermissions(this.mContext, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_SILENT_ACTIVATION_INTERNAL")), null);
        activateAdmin(componentName, false);
    }

    public final boolean checkCallerIsUMC() {
        int i;
        int callingUid = Binder.getCallingUid();
        try {
            i = this.mContext.getPackageManager().getPackageUidAsUser("com.sec.enterprise.knox.cloudmdm.smdms", this.mInjector.userHandleGetCallingUserId());
        } catch (Exception e) {
            System.out.println(e);
            i = -1;
        }
        return callingUid == i;
    }

    public final boolean checkCallerIsKPECore(String str, int i) {
        IPackageManager packageManager = AppGlobals.getPackageManager();
        try {
            if (!"com.samsung.android.knox.kpecore".equals(str) || packageManager.checkPermission("com.samsung.android.knox.permission.KNOX_KPECORE_INTERNAL", str, i) != 0) {
                return false;
            }
            Log.d("EnterpriseDeviceManagerService", "call from kpecore");
            return true;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public final boolean checkCallerIsKG() {
        int i;
        int callingUid = Binder.getCallingUid();
        try {
            i = this.mContext.getPackageManager().getPackageUidAsUser(KnoxCustomManagerService.KG_PKG_NAME, this.mInjector.userHandleGetCallingUserId());
        } catch (Exception e) {
            System.out.println(e);
            i = -1;
        }
        return callingUid == i;
    }

    public final void enforceUMCSignature() {
        try {
            if (Utils.compareSystemSignature(this.mContext, mPackageManagerAdapter.getPackageInfo("com.sec.enterprise.knox.cloudmdm.smdms", 64, UserHandle.getUserId(Binder.getCallingUid())).signatures)) {
            } else {
                throw new SecurityException("Caller is not real UMC. Signature Verification failed.");
            }
        } catch (Exception unused) {
            Log.w("EnterpriseDeviceManagerService", "package not found");
        }
    }

    public final boolean checkProxyAdminPermission(ContextInfo contextInfo, List list) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("proxyUid", Integer.valueOf(contextInfo.mCallerUid));
        List values = this.mEdmStorageProvider.getValues("PROXY_ADMIN_INFO", new String[]{"permissions"}, contentValues);
        if (values != null && !values.isEmpty()) {
            String asString = ((ContentValues) values.get(0)).getAsString("permissions");
            if (asString != null && !asString.isEmpty()) {
                HashSet hashSet = new HashSet(Arrays.asList(asString.split(KnoxVpnFirewallHelper.DELIMITER)));
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    if (hashSet.contains((String) it.next())) {
                        return true;
                    }
                }
            }
        } else {
            try {
                Iterator it2 = list.iterator();
                while (it2.hasNext()) {
                    if (AppGlobals.getPackageManager().checkUidPermission((String) it2.next(), contextInfo.mCallerUid) == 0) {
                        return true;
                    }
                }
            } catch (Exception unused) {
                Log.w("EnterpriseDeviceManagerService", "could not check calling permission");
            }
        }
        return false;
    }

    public boolean activateDevicePermissions(List list) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("com.samsung.android.knox.permission.KNOX_ACTIVATE_DEVICE_PERMISSIONS_INTERNAL");
        enforceActiveAdminPermissionByContext(null, arrayList);
        int callingUid = Binder.getCallingUid();
        int appId = UserHandle.getAppId(callingUid);
        if (this.mAdminMap.containsKey(Integer.valueOf(appId))) {
            throw new SecurityException("Admin already present and active");
        }
        EnterpriseDeviceAdminInfo enterpriseDeviceAdminInfo = (EnterpriseDeviceAdminInfo) this.mAdminMap.get(Integer.valueOf(callingUid));
        if (enterpriseDeviceAdminInfo != null) {
            ComponentName component = enterpriseDeviceAdminInfo.getComponent();
            if (component == null) {
                Log.e("EnterpriseDeviceManagerService", "failed due to abnormal admin information: " + callingUid);
                return false;
            }
            if (mPackageManagerAdapter.isApplicationInstalled(component.getPackageName(), UserHandle.getUserId(appId))) {
                Log.e("EnterpriseDeviceManagerService", "failed due to application is installed in device as user:" + UserHandle.getUserId(appId));
                return false;
            }
            Iterator it = list.iterator();
            while (it.hasNext()) {
                String str = (String) it.next();
                if (!CONTAINER_ALLOWED_DEVICE_PERMISSION_LIST.contains(str)) {
                    Log.e("EnterpriseDeviceManagerService", "This permission cannot be set on device level from the container: " + str);
                    return false;
                }
            }
            if (!list.contains("com.samsung.android.knox.permission.KNOX_SECURITY")) {
                return true;
            }
            if (enterpriseDeviceAdminInfo.usesPolicy(7)) {
                list.add("encrypted-storage");
                Log.i("EnterpriseDeviceManagerService", "add device permission: encrypted-storage, " + enterpriseDeviceAdminInfo.getTagForPolicy(7));
            }
            if (!enterpriseDeviceAdminInfo.usesPolicy(20)) {
                return true;
            }
            list.add("require-storagecard-encryption");
            Log.i("EnterpriseDeviceManagerService", "add device permission: require-storagecard-encryption, " + enterpriseDeviceAdminInfo.getTagForPolicy(20));
            return true;
        }
        throw new SecurityException("No active admin");
    }

    public boolean enableConstrainedState(ContextInfo contextInfo, String str, String str2, String str3, String str4, int i) {
        return this.mConstrainedState.enableConstrainedState(enforceOwnerOnlyAndActiveAdminPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_ADVANCED_RESTRICTION"))).mCallerUid, str, str2, str3, str4, i);
    }

    public boolean disableConstrainedState(ContextInfo contextInfo) {
        return this.mConstrainedState.disableConstrainedState(enforceOwnerOnlyAndActiveAdminPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_ADVANCED_RESTRICTION"))).mCallerUid);
    }

    public boolean isRestrictedByConstrainedState(int i) {
        return this.mConstrainedState.isRestrictedByConstrainedState(i);
    }

    public int getConstrainedState() {
        return this.mConstrainedState.getConstrainedState();
    }

    public void sendIntent(int i) {
        int i2;
        if (i == 1) {
            i2 = R.string.config_gnssLocationProviderPackageName;
        } else if (i == 2) {
            i2 = R.string.permlab_invokeCarrierSetup;
        } else if (i != 3) {
            return;
        } else {
            i2 = R.string.config_defaultRotationResolverService;
        }
        RestrictionToastManager.show(i2);
    }

    public boolean isCameraEnabledNative(ContextInfo contextInfo) {
        Log.d("EnterpriseDeviceManagerService", "isCameraEnabledNative");
        RestrictionPolicy restrictionPolicy = (RestrictionPolicy) EnterpriseService.getPolicyService("restriction_policy");
        if (restrictionPolicy != null) {
            try {
                Log.d("EnterpriseDeviceManagerService", "checking for camera in restriction policy");
                return restrictionPolicy.isCameraEnabled(contextInfo, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public boolean addAuthorizedUid(int i, int i2) {
        Log.d("EnterpriseDeviceManagerService", "addAuthorizedUid");
        if (!checkCallerIsUMC()) {
            return false;
        }
        enforceUMCSignature();
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Integer.valueOf(i2));
        contentValues.put("authorizedUid", Integer.valueOf(i));
        return this.mEdmStorageProvider.putValuesNoUpdate("ADMIN_UID_AUTHORIZATION_INFO", contentValues);
    }

    public boolean removeAuthorizedUid(int i, int i2) {
        Log.d("EnterpriseDeviceManagerService", "removeAuthorizedUid");
        if (!checkCallerIsUMC()) {
            return false;
        }
        enforceUMCSignature();
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Integer.valueOf(i2));
        contentValues.put("authorizedUid", Integer.valueOf(i));
        int delete = this.mEdmStorageProvider.delete("ADMIN_UID_AUTHORIZATION_INFO", contentValues);
        Log.d("EnterpriseDeviceManagerService", "removeAuthorizedUid : " + delete);
        return delete > 1;
    }

    public int getAuthorizedUidForAdminUid(int i) {
        Integer asInteger;
        Log.d("EnterpriseDeviceManagerService", "getAuthorizedUidForAdminUid");
        if (!checkCallerIsUMC()) {
            return -1;
        }
        enforceUMCSignature();
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Integer.valueOf(i));
        ContentValues value = this.mEdmStorageProvider.getValue("ADMIN_UID_AUTHORIZATION_INFO", "authorizedUid", contentValues);
        if (value == null || (asInteger = value.getAsInteger("authorizedUid")) == null) {
            return -1;
        }
        return asInteger.intValue();
    }

    public int getAdminUidForAuthorizedUid(int i) {
        Integer asInteger;
        Log.d("EnterpriseDeviceManagerService", "getAdminUidForAuthorizedUid");
        if (!checkCallerIsUMC()) {
            return -1;
        }
        enforceUMCSignature();
        ContentValues contentValues = new ContentValues();
        contentValues.put("authorizedUid", Integer.valueOf(i));
        ContentValues value = this.mEdmStorageProvider.getValue("ADMIN_UID_AUTHORIZATION_INFO", "adminUid", contentValues);
        if (value == null || (asInteger = value.getAsInteger("adminUid")) == null) {
            return -1;
        }
        return asInteger.intValue();
    }

    public final int getAuthorizedAdminUid(int i) {
        Integer asInteger;
        Log.d("EnterpriseDeviceManagerService", "getAuthorizedAdminUid");
        ContentValues contentValues = new ContentValues();
        contentValues.put("authorizedUid", Integer.valueOf(i));
        ContentValues value = this.mEdmStorageProvider.getValue("ADMIN_UID_AUTHORIZATION_INFO", "adminUid", contentValues);
        if (value == null || (asInteger = value.getAsInteger("adminUid")) == null) {
            return -1;
        }
        return asInteger.intValue();
    }

    public boolean writeUmcEnrollmentData(ContextInfo contextInfo, String str) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(KESListener.KME_BROADCAST_PERMISSION);
        enforcePermissionByContext(contextInfo, arrayList);
        if (!checkCallerIsUMC()) {
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        FileOutputStream fileOutputStream = null;
        try {
            try {
                File file = new File("/efs/umc");
                if (!file.exists()) {
                    file.mkdirs();
                }
                File file2 = new File("/efs/umc", "BulkEnrollmentProfile");
                if (file2.exists()) {
                    file2.delete();
                }
                if (str != null && !str.isEmpty()) {
                    FileOutputStream fileOutputStream2 = new FileOutputStream(file2);
                    try {
                        fileOutputStream2.write(str.getBytes());
                        fileOutputStream2.flush();
                        fileOutputStream2.close();
                    } catch (Exception e) {
                        e = e;
                        fileOutputStream = fileOutputStream2;
                        e.printStackTrace();
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (Exception e2) {
                                e2.printStackTrace();
                            }
                        }
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return false;
                    } catch (Throwable th) {
                        th = th;
                        fileOutputStream = fileOutputStream2;
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (Exception e3) {
                                e3.printStackTrace();
                            }
                        }
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e4) {
            e = e4;
        }
    }

    public String readUmcEnrollmentData(ContextInfo contextInfo) {
        FileInputStream fileInputStream;
        File file;
        ArrayList arrayList = new ArrayList();
        arrayList.add(KESListener.KME_BROADCAST_PERMISSION);
        enforcePermissionByContext(contextInfo, arrayList);
        FileInputStream fileInputStream2 = null;
        try {
            if (!checkCallerIsUMC()) {
                return null;
            }
            try {
                file = new File("/efs/umc", "BulkEnrollmentProfile");
            } catch (Exception e) {
                e = e;
                fileInputStream = null;
            } catch (Throwable th) {
                th = th;
                if (fileInputStream2 != null) {
                    try {
                        fileInputStream2.close();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
                throw th;
            }
            if (file.exists() && file.length() != 0) {
                int length = (int) file.length();
                byte[] bArr = new byte[length];
                fileInputStream = new FileInputStream(file);
                try {
                    int read = fileInputStream.read(bArr);
                    fileInputStream.close();
                    if (read > 0) {
                        return new String(bArr, 0, length);
                    }
                } catch (Exception e3) {
                    e = e3;
                    e.printStackTrace();
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (Exception e4) {
                            e4.printStackTrace();
                        }
                    }
                    return null;
                }
                return null;
            }
            return null;
        } catch (Throwable th2) {
            th = th2;
            fileInputStream2 = fileInputStream;
        }
    }

    public byte[] captureUmcLogs(ContextInfo contextInfo, String str, List list) {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses;
        ArrayList arrayList = new ArrayList();
        arrayList.add(KESListener.KME_BROADCAST_PERMISSION);
        enforcePermissionByContext(contextInfo, arrayList);
        byte[] bArr = null;
        if (!checkCallerIsUMC()) {
            return null;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(Integer.toString(MY_PID));
        try {
            runningAppProcesses = ((ActivityManager) this.mContext.getSystemService("activity")).getRunningAppProcesses();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (runningAppProcesses == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
            if (runningAppProcessInfo != null && list.contains(runningAppProcessInfo.processName)) {
                arrayList2.add(Integer.toString(runningAppProcessInfo.pid));
                Log.d("EnterpriseDeviceManagerService", runningAppProcessInfo.processName + " " + runningAppProcessInfo.pid + ((String) arrayList2.get(0)));
            }
        }
        try {
            Process exec = Runtime.getRuntime().exec(new String[]{"logcat", "-d", "-v", "threadtime"});
            String property = System.getProperty("line.separator");
            StringBuilder sb = new StringBuilder();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream);
            zipOutputStream.putNextEntry(new ZipEntry("BELogs.txt"));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exec.getInputStream()));
            sb.append("---------------------------------------------------------------\n");
            sb.append(str);
            sb.append("---------------------------------------------------------------\n");
            zipOutputStream.write(sb.toString().getBytes());
            sb.setLength(0);
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                String[] split = readLine.split(" +");
                if (!arrayList2.isEmpty() && split.length > 2 && arrayList2.contains(split[2])) {
                    sb.append(readLine);
                    sb.append(property);
                    zipOutputStream.write(sb.toString().getBytes());
                    sb.setLength(0);
                }
            }
            bufferedReader.close();
            zipOutputStream.closeEntry();
            zipOutputStream.close();
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            bArr = byteArray;
        } catch (Exception e2) {
            Log.e("EnterpriseDeviceManagerService", e2.getMessage(), e2);
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return bArr;
    }

    public static boolean isPackageInstalled(String str, int i) {
        IPackageManager packageManager = AppGlobals.getPackageManager();
        if (packageManager != null) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (packageManager.getApplicationInfo(str, 0L, i) != null) {
                    Log.i("EnterpriseDeviceManagerService", "isPackageInstalled() : package present. application : " + str);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return true;
                }
                Log.i("EnterpriseDeviceManagerService", "isPackageInstalled() : package is not present. application : " + str);
                return false;
            } catch (Exception e) {
                Log.e("EnterpriseDeviceManagerService", "Exception in isPackageInstalled()", e);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return false;
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
            printWriter.println("Permission Denial: can't dump Enterprise Device Manager Service");
            return;
        }
        printWriter.write("EnterpriseDeviceManagerService Knox Info:" + System.lineSeparator());
        printWriter.write(showKnoxVersion());
        printWriter.write("EnterpriseDeviceManagerService SystemUIAdapter Info:" + System.lineSeparator());
        if (this.mCurrentUserId != SystemUIAdapter.getInstance(this.mContext).getAdapterId()) {
            printWriter.write("mCurrentUserId : " + this.mCurrentUserId + System.lineSeparator());
        }
        printWriter.write("SystemUIAdapter adapterId : " + SystemUIAdapter.getInstance(this.mContext).getAdapterId() + System.lineSeparator());
        printWriter.write("SystemUIAdapter registered count : " + SystemUIAdapter.getInstance(this.mContext).getRegisteredCount() + System.lineSeparator());
        if (!SystemUIAdapter.getInstance(this.mContext).isKnoxStateMonitorRegistered()) {
            printWriter.write("SystemUIAdapter is not registed. " + System.lineSeparator());
        }
        getEnterpriseDumpHelper().dumpTable(printWriter, "ADMIN_INFO", new String[]{"adminUid", "adminName", "canRemove", "isPseudoAdmin"});
    }

    public final EnterpriseDumpHelper getEnterpriseDumpHelper() {
        if (this.mEnterpriseDumpHelper == null) {
            this.mEnterpriseDumpHelper = new EnterpriseDumpHelper(this.mContext);
        }
        return this.mEnterpriseDumpHelper;
    }

    /* JADX WARN: Type inference failed for: r0v9, types: [boolean, int] */
    public final String showKnoxVersion() {
        String str;
        StringBuilder sb = new StringBuilder();
        try {
            String knoxContainerVersionString = this.mPersonaManagerAdapter.getKnoxContainerVersionString();
            if (knoxContainerVersionString != null && !knoxContainerVersionString.isEmpty()) {
                int parseInt = Integer.parseInt(Character.toString(knoxContainerVersionString.charAt(knoxContainerVersionString.length() - 1)));
                sb.append("Knox ");
                if (parseInt > 0) {
                    sb.append(knoxContainerVersionString);
                } else {
                    sb.append(knoxContainerVersionString.substring(0, knoxContainerVersionString.lastIndexOf(46)));
                }
                sb.append(System.lineSeparator());
            }
            String internalVersion = EnterpriseDeviceManager.getEnterpriseSdkVerInternal().getInternalVersion();
            if (internalVersion != null && !internalVersion.isEmpty()) {
                sb.append("Standard SDK ");
                sb.append(internalVersion);
                sb.append(System.lineSeparator());
            }
            String internalVersion2 = EdmConstants.getEnterpriseKnoxSdkVersion().getInternalVersion();
            if (internalVersion2 != null && !internalVersion2.isEmpty()) {
                sb.append("Premium SDK ");
                sb.append(internalVersion2);
                sb.append(System.lineSeparator());
            }
            String internalSdkVersion = CustomDeviceManager.getInstance().getSdkVersion().getInternalSdkVersion();
            if (internalSdkVersion != null && !internalSdkVersion.isEmpty()) {
                sb.append("Customization SDK ");
                sb.append(internalSdkVersion);
                sb.append(System.lineSeparator());
            }
            String knoxContainerVersionString2 = this.mPersonaManagerAdapter.getKnoxContainerVersionString();
            if (knoxContainerVersionString2 != null && !knoxContainerVersionString2.isEmpty()) {
                sb.append("Container  ");
                sb.append(knoxContainerVersionString2);
                sb.append(System.lineSeparator());
            }
            String str2 = SystemProperties.get("net.knoxisl.version", (String) null);
            if (str2 != null && !str2.isEmpty()) {
                sb.append("IM ");
                sb.append(str2);
                sb.append(System.lineSeparator());
            }
            String str3 = SystemProperties.get("net.knoxscep.version", (String) null);
            if (str3 != null && !str3.isEmpty()) {
                sb.append("CEP ");
                sb.append(str3);
                sb.append(System.lineSeparator());
            }
            String str4 = SystemProperties.get("sys.enterprise.billing.version", (String) null);
            if (str4 != null && !str4.isEmpty()) {
                sb.append("Enterprise Billing ");
                sb.append(str4);
                sb.append(System.lineSeparator());
            }
            if (!"".equals(SystemProperties.get("ro.config.timaversion", "No Policy Version"))) {
                if ("3.0".equals(SystemProperties.get("ro.config.timaversion", "No Policy Version"))) {
                    String str5 = this.mPersonaManagerAdapter.isKnoxVersionSupported(FrameworkStatsLog.f392x1636cc72) ? "3.3" : "3.2";
                    ?? equals = "true".equals(SystemProperties.get("ro.config.dmverity").toLowerCase());
                    int i = equals;
                    if ("true".equals(SystemProperties.get("ro.config.rkp"))) {
                        i = equals + 2;
                    }
                    int i2 = i;
                    if ("true".equals(SystemProperties.get("ro.config.kap"))) {
                        i2 = i + 4;
                    }
                    int i3 = i2;
                    if ("true".equals(SystemProperties.get("ro.config.kap_default_on"))) {
                        i3 = i2 + 8;
                    }
                    if (i3 < 10) {
                        str = str5 + ".00" + Integer.toString(i3);
                    } else {
                        str = str5 + ".0" + Integer.toString(i3);
                    }
                } else {
                    str = SystemProperties.get("ro.config.timaversion", "No Policy Version");
                }
                sb.append("TIMA ");
                sb.append(str);
                sb.append(System.lineSeparator());
            }
            String str6 = SystemProperties.get("net.knoxvpn.version", (String) null);
            if (str6 != null && !str6.isEmpty()) {
                sb.append("VPN ");
                sb.append(str6);
                sb.append(System.lineSeparator());
            }
        } catch (Exception e) {
            Log.e("EnterpriseDeviceManagerService", "Failed to get Knox Version ");
            e.printStackTrace();
        }
        return sb.toString();
    }

    public final void enforceCallingOrSelfPermissions(Context context, List list, String str) {
        EnterprisePermissionChecker.getInstance(context).enforceCallingOrSelfPermissions(list, str);
    }

    @Override // com.android.server.enterprise.EnterpriseDeviceManagerService
    public String getActiveAdminPackageName(int i) {
        EnterpriseDeviceAdminInfo enterpriseDeviceAdminInfo;
        HashMap hashMap = this.mAdminMap;
        if (hashMap == null || (enterpriseDeviceAdminInfo = (EnterpriseDeviceAdminInfo) hashMap.get(Integer.valueOf(i))) == null || enterpriseDeviceAdminInfo.isPseudo() || enterpriseDeviceAdminInfo.getComponent() == null) {
            return null;
        }
        return enterpriseDeviceAdminInfo.getComponent().getPackageName();
    }

    public boolean isProfileOwnerApp(ContextInfo contextInfo) {
        boolean isValidKnoxId;
        int i = contextInfo.mContainerId;
        if (contextInfo.mParent) {
            i = UserHandle.getUserId(contextInfo.mCallerUid);
            isValidKnoxId = this.mPersonaManagerAdapter.isValidKnoxId(i);
        } else {
            isValidKnoxId = this.mPersonaManagerAdapter.isValidKnoxId(i);
        }
        return isValidKnoxId && !this.mPersonaManagerAdapter.isLegacyContainer(i) && this.mEdmStorageProvider.getMUMContainerOwnerUid(i) == contextInfo.mCallerUid;
    }

    public boolean isCallerDeviceOwner() {
        return ((DevicePolicyManager) this.mContext.getSystemService(DevicePolicyManager.class)).isDeviceOwnerApp(this.mContext.getPackageManager().getNameForUid(Binder.getCallingUid()));
    }

    public int getUserStatus(int i) {
        String packageName;
        Log.d("EnterpriseDeviceManagerService", "getUserStatus is called for userid " + i);
        int callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        int i2 = -1;
        try {
            try {
                if (i == 0) {
                    String[] packagesForUid = this.mContext.getPackageManager().getPackagesForUid(callingUid);
                    List activeAdmins = getActiveAdmins(i);
                    if (activeAdmins != null) {
                        Iterator it = activeAdmins.iterator();
                        while (it.hasNext()) {
                            try {
                                packageName = ((ComponentName) it.next()).getPackageName();
                            } catch (Exception e) {
                                Log.e("EnterpriseDeviceManagerService", "Failed to getUserStatus" + e);
                            }
                            if (Arrays.asList(packagesForUid).contains(packageName)) {
                                Log.d("EnterpriseDeviceManagerService", "Valid Admin " + packageName + " in User 0");
                                if (getContainerService() == null) {
                                    break;
                                }
                                i2 = getContainerService().getStatusInternal(0);
                                break;
                            }
                            continue;
                        }
                    }
                } else if (callingUid == this.mEdmStorageProvider.getMUMContainerOwnerUid(i)) {
                    Log.d("EnterpriseDeviceManagerService", "Valid Admin for User " + i);
                    if (getContainerService() != null) {
                        i2 = getContainerService().getStatusInternal(i);
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                Log.e("EnterpriseDeviceManagerService", "Failed to getUserStatus");
            }
            Log.d("EnterpriseDeviceManagerService", "status " + i2);
            return i2;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final synchronized IKnoxContainerManager getContainerService() {
        if (this.mContainerService == null) {
            this.mContainerService = IKnoxContainerManager.Stub.asInterface(ServiceManager.getService("mum_container_policy"));
        }
        return this.mContainerService;
    }

    public final void adjustKnoxGuardAdmin() {
        String str;
        if ((!checkCallerIsKG() && Binder.getCallingUid() != 1000) || (str = SystemProperties.get(ActivationMonitor.COUNTRY_CODE_PROPERTY, "")) == null || str.equalsIgnoreCase(ActivationMonitor.CHINA_COUNTRY_CODE)) {
            return;
        }
        if (SystemProperties.getInt("ro.product.first_api_level", 0) >= 30) {
            List list = EXCLUDED_ADMINS;
            if (list.contains(KnoxCustomManagerService.KG_PKG_NAME)) {
                list.remove(KnoxCustomManagerService.KG_PKG_NAME);
                Log.d("EnterpriseDeviceManagerService", "adjustKnoxGuardAdmin kgclient is removed from EXCLUDED_ADMINS by " + Binder.getCallingUid());
                return;
            }
            return;
        }
        if (!isKgTurnedOn()) {
            List list2 = EXCLUDED_ADMINS;
            if (list2.contains(KnoxCustomManagerService.KG_PKG_NAME)) {
                return;
            }
            list2.add(KnoxCustomManagerService.KG_PKG_NAME);
            Log.d("EnterpriseDeviceManagerService", "adjustKnoxGuardAdmin kgclient is added in EXCLUDED_ADMINS by " + Binder.getCallingUid());
            return;
        }
        List list3 = EXCLUDED_ADMINS;
        if (list3.contains(KnoxCustomManagerService.KG_PKG_NAME)) {
            list3.remove(KnoxCustomManagerService.KG_PKG_NAME);
            Log.d("EnterpriseDeviceManagerService", "adjustKnoxGuardAdmin kgclient is removed from EXCLUDED_ADMINS by " + Binder.getCallingUid());
        }
    }

    public final boolean isKgTurnedOn() {
        Log.d("EnterpriseDeviceManagerService", "isKgTurnedOn.");
        try {
            boolean isKgTurnedOn = new KnoxGuardVaultManager(this.mContext).isKgTurnedOn();
            Log.d("EnterpriseDeviceManagerService", "isKgTurnedOn : " + isKgTurnedOn);
            return isKgTurnedOn;
        } catch (KnoxGuardVaultException unused) {
            Log.d("EnterpriseDeviceManagerService", "KnoxGuardVaultManager not supported (KnoxGuardVaultException)");
            return false;
        } catch (Throwable th) {
            Log.d("EnterpriseDeviceManagerService", "isKgTurnedOn() : Failure getting KGVM " + th);
            return false;
        }
    }

    public boolean migrateKnoxPoliciesForWpcod(int i) {
        int callingUid = Binder.getCallingUid();
        int callingPid = Binder.getCallingPid();
        if (callingUid != 1000 || callingPid != MY_PID) {
            throw new SecurityException("Caller is not a system process..");
        }
        try {
            Log.d("EnterpriseDeviceManagerService", "migrateKnoxPoliciesForWpcod() containerId received = " + i);
            if (addPseudoAdminForParent(i) != -1) {
                resetAPILevelPrivacyPolicies();
                resetClassLevelPrivacyPolicies();
                Log.d("EnterpriseDeviceManagerService", "migrateKnoxPoliciesForWpcod() complete..");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("EnterpriseDeviceManagerService", "migrateKnoxPoliciesForWpcod() returning false..");
        return false;
    }

    public int addPseudoAdminForParent(int i) {
        int callingUid = Binder.getCallingUid();
        int callingPid = Binder.getCallingPid();
        if ((callingUid != 1000 || callingPid != MY_PID) && this.mContext.checkCallingPermission("android.permission.MARK_DEVICE_ORGANIZATION_OWNED") != 0) {
            throw new SecurityException("Caller must be a system process or have permission android.permission.MARK_DEVICE_ORGANIZATION_OWNED..");
        }
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (this.mDPMS.isOrganizationOwnedDeviceWithManagedProfile() && getOrganizationOwnedProfileUserId() == i) {
            int mUMContainerOwnerUid = this.mEdmStorageProvider.getMUMContainerOwnerUid(i);
            int appId = UserHandle.getAppId(mUMContainerOwnerUid);
            ComponentName componentNameForUid = this.mEdmStorageProvider.getComponentNameForUid(mUMContainerOwnerUid);
            if (componentNameForUid != null ? this.mEdmStorageProvider.addorUpdateAdminWithPseudo(appId, componentNameForUid.flattenToString(), false, 0, true) : false) {
                this.mAdminMap.put(Integer.valueOf(appId), new EnterpriseDeviceAdminInfo(true));
                this.mPseudoAdminUid = appId;
                Log.d("EnterpriseDeviceManagerService", "addPseudoAdminForParent() success with Pseudo =  " + appId);
                return appId;
            }
            Log.d("EnterpriseDeviceManagerService", "addPseudoAdminForParent() failed returning -1..");
            return -1;
        }
        Log.d("EnterpriseDeviceManagerService", "addPseudoAdminForParent() returning 0..");
        return 0;
    }

    public void enforceWpcod(int i, boolean z) {
        int callingUid = Binder.getCallingUid();
        if (i != callingUid) {
            throw new SecurityException("ContextInfo UID voilation info is " + i + " but caller is " + callingUid);
        }
        try {
            if (!this.mDPMS.isOrganizationOwnedDeviceWithManagedProfile()) {
                throw new SecurityException("Device is not organization owned managed profile..");
            }
            if (getOrganizationOwnedProfileUserId() != UserHandle.getUserId(callingUid)) {
                throw new SecurityException("Organization owned managed profile userId and caller userId does not match..");
            }
            if (!z) {
                Log.d("EnterpriseDeviceManagerService", "enforceWpcod(), caller is a valid KPU..");
                return;
            }
            ContextInfo contextInfo = new ContextInfo(callingUid);
            String nameForUid = this.mContext.getPackageManager().getNameForUid(callingUid);
            if (!isProfileOwnerApp(contextInfo) && !isCallerValidKPU(contextInfo) && (nameForUid == null || !checkCallerIsKPECore(nameForUid, UserHandle.getUserId(callingUid)))) {
                throw new SecurityException("Caller is not either organization owned PO or KSP inside org owned profile..");
            }
            Log.d("EnterpriseDeviceManagerService", "enforceWpcod(), caller is a either WPCOD PO or valid KPU..");
        } catch (RemoteException e) {
            Log.d("EnterpriseDeviceManagerService", "enforceWpcod(), failed to talk to DPMS..");
            e.printStackTrace();
        }
    }

    @Override // com.android.server.enterprise.EnterpriseDeviceManagerService
    public int getPseudoAdminUid() {
        return this.mPseudoAdminUid;
    }

    public final void removePseudoAdmin() {
        int i = this.mPseudoAdminUid;
        if (i == -1) {
            i = this.mEdmStorageProvider.getPseudoAdminUid();
        }
        this.mPseudoAdminUid = i;
        if (i == -1) {
            return;
        }
        try {
            setAdminRemovable(i, true);
            this.mEdmStorageProvider.removeAdminFromDatabase(this.mPseudoAdminUid);
            this.mPseudoAdminUid = -1;
            this.mAdminMap.remove(-1);
        } catch (Exception e) {
            Log.d("EnterpriseDeviceManagerService", "removePseudoAdmin exception " + e.getMessage());
        }
    }

    public final void setAdminRemovable(int i, boolean z) {
        try {
            this.mEdmStorageProvider.putBoolean(i, "ADMIN_INFO", "canRemove", z);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.android.server.enterprise.EnterpriseDeviceManagerService
    public int getOrganizationOwnedProfileUserId() {
        UserManager userManager = (UserManager) this.mContext.getSystemService("user");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                for (UserInfo userInfo : userManager.getUsers()) {
                    if (userInfo.isManagedProfile() && this.mDPMS.isProfileOwnerOfOrganizationOwnedDeviceMDM(userInfo.id)) {
                        return userInfo.id;
                    }
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return -10000;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void resetAPILevelPrivacyPolicies() {
        String packageNameForUid;
        try {
            this.mEdmStorageProvider.resetControlStateBits(this.mPseudoAdminUid, -15329266);
            updateApplicationCacheForWpcod();
        } catch (Exception e) {
            Log.d("EnterpriseDeviceManagerService", "resetAPILevelPrivacyPolicies (Application Group): Exception - " + e.getMessage());
        }
        try {
            this.mEdmStorageProvider.putInt(this.mPseudoAdminUid, "APPLICATION_MISC", "appNotificationMode", 2);
        } catch (Exception e2) {
            e2.printStackTrace();
            Log.d("EnterpriseDeviceManagerService", "resetAPILevelPrivacyPolicies : Failed to reset App Notification Mode: " + e2.getMessage());
        }
        try {
            this.mEdmStorageProvider.putBoolean(this.mPseudoAdminUid, "RESTRICTION", "backupEnabled", true);
            this.mEdmStorageProvider.putBoolean(this.mPseudoAdminUid, "RESTRICTION", "clipboardEnabled", true);
            this.mEdmStorageProvider.putBoolean(this.mPseudoAdminUid, "RESTRICTION", "factoryresetallowed", true);
            this.mEdmStorageProvider.putBoolean(this.mPseudoAdminUid, "RESTRICTION", "allowClipboardShare", true);
            this.mEdmStorageProvider.putBoolean(this.mPseudoAdminUid, "RESTRICTION", "allowGoogleAccountsAutoSync", true);
            updateRestrictionCacheForWpcod();
        } catch (Exception e3) {
            Log.d("EnterpriseDeviceManagerService", "resetAPILevelPrivacyPolicies (Restriction Group): Exception - " + e3.getMessage());
        }
        try {
            this.mEdmStorageProvider.putBoolean(this.mPseudoAdminUid, "RESTRICTION", "globalProxy", false);
        } catch (Exception e4) {
            Log.d("EnterpriseDeviceManagerService", "resetAPILevelPrivacyPolicies (Global Proxy): Exception - " + e4.getMessage());
        }
        try {
            this.mEdmStorageProvider.putBoolean(this.mPseudoAdminUid, "MULTI_USER_MGMT", "multiUserCreationAllowed", true);
            this.mEdmStorageProvider.putBoolean(this.mPseudoAdminUid, "MULTI_USER_MGMT", "multiUserRemovalAllowed", true);
        } catch (Exception e5) {
            Log.d("EnterpriseDeviceManagerService", "resetAPILevelPrivacyPolicies (MultiUser policy Group): Exception - " + e5.getMessage());
        }
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("bluetoothLogEnabled", "false");
            this.mEdmStorageProvider.putValues(this.mPseudoAdminUid, "BLUETOOTH", contentValues);
        } catch (Exception e6) {
            Log.d("EnterpriseDeviceManagerService", "resetAPILevelPrivacyPolicies (Bluetooth policy Group): Exception - " + e6.getMessage());
        }
        IPackageManager packageManager = AppGlobals.getPackageManager();
        try {
            this.mEdmStorageProvider.putBoolean(this.mPseudoAdminUid, "ADMIN_INFO", "canRemove", true);
            ArrayList adminUidList = this.mEdmStorageProvider.getAdminUidList();
            String kPUPackageName = getKPUPackageName();
            Iterator it = adminUidList.iterator();
            while (it.hasNext()) {
                int intValue = ((Integer) it.next()).intValue();
                if (UserHandle.getUserId(intValue) == 0 && (packageNameForUid = this.mEdmStorageProvider.getPackageNameForUid(intValue)) != null && (packageNameForUid.equals(kPUPackageName) || packageManager.checkPermission("com.samsung.android.knox.permission.KNOX_KPU_INTERNAL", packageNameForUid, UserHandle.getUserId(intValue)) == 0)) {
                    this.mEdmStorageProvider.putBoolean(intValue, "ADMIN_INFO", "canRemove", true);
                }
            }
        } catch (Exception e7) {
            Log.d("EnterpriseDeviceManagerService", "resetAPILevelPrivacyPolicies (EDMPolicy.setAdminRemovable): Exception - " + e7.getMessage());
        }
    }

    public final void resetClassLevelPrivacyPolicies() {
        String[] strArr = {"GEOFENCING", "BROWSER", "GEOFENCINGSETTINGS", "DomainFilterReportStatus"};
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Integer.valueOf(this.mPseudoAdminUid));
        for (int i = 0; i < 4; i++) {
            String str = strArr[i];
            try {
                this.mEdmStorageProvider.delete(str, contentValues);
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("EnterpriseDeviceManagerService", "resetClassLevelPrivacyPolicies : Failed to reset table " + str + " : " + e.getMessage());
            }
        }
    }

    public void updateNotificationExemption(ContextInfo contextInfo, String str) {
        int callingUid = Binder.getCallingUid();
        int userId = UserHandle.getUserId(callingUid);
        if (!hasKnoxInternalExceptionPermission(this.mContext.getPackageManager().getNameForUid(callingUid), userId)) {
            Log.d("EnterpriseDeviceManagerService", "Only Knox Internal package can grant notification exemption");
            return;
        }
        if (str == null || str.isEmpty()) {
            return;
        }
        if (!mPackageManagerAdapter.isApplicationInstalled(str, userId)) {
            Log.d("EnterpriseDeviceManagerService", "Target package is not installed : " + str);
            return;
        }
        updateNotificationExemptionInternal(contextInfo, str, userId);
    }

    public final void updateNotificationExemptionInternal(ContextInfo contextInfo, final String str, int i) {
        try {
            final int packageUidAsUser = this.mContext.getPackageManager().getPackageUidAsUser(str, i);
            this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.EnterpriseDeviceManagerServiceImpl$$ExternalSyntheticLambda0
                public final void runOrThrow() {
                    EnterpriseDeviceManagerServiceImpl.this.lambda$updateNotificationExemptionInternal$3(packageUidAsUser, str);
                }
            });
            Log.d("EnterpriseDeviceManagerService", "updateNotificationExemptionInner PackageName : " + str + " Notification Exemption");
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("EnterpriseDeviceManagerService", "updateNotificationExemption : Failed to provide notification exemption  for " + str + " : " + e.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateNotificationExemptionInternal$3(int i, String str) {
        if (this.mInjector.getAppOpsManager().unsafeCheckOpNoThrow("android:system_exempt_from_dismissible_notifications", i, str) != 0) {
            this.mInjector.getAppOpsManager().setMode("android:system_exempt_from_dismissible_notifications", i, str, 0);
        }
    }

    public final void updateRestrictionCacheForWpcod() {
        Log.d("EnterpriseDeviceManagerService", "updateRestrictionCacheForWpcod() called..");
        try {
            RestrictionPolicy restrictionPolicy = (RestrictionPolicy) EnterpriseService.getPolicyService("restriction_policy");
            if (restrictionPolicy != null) {
                restrictionPolicy.updateRestrictionCacheForWpcod();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("EnterpriseDeviceManagerService", "updateRestrictionCacheForWpcod error: " + e.getMessage());
        }
    }

    public final void updateApplicationCacheForWpcod() {
        Log.d("EnterpriseDeviceManagerService", "updateApplicationCacheForWpcod() called");
        try {
            ApplicationPolicy applicationPolicy = (ApplicationPolicy) EnterpriseService.getPolicyService("application_policy");
            if (applicationPolicy != null) {
                applicationPolicy.updateApplicationCacheForWpcod(Long.valueOf(this.mPseudoAdminUid).longValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("EnterpriseDeviceManagerService", "updateApplicationCacheForWpcod error: " + e.getMessage());
        }
    }

    public boolean isUserSelectable(String str) {
        Log.d("EnterpriseDeviceManagerService", "isUserSelectable called for alias: " + str);
        boolean z = false;
        if (str != null && str.length() != 0) {
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put("alias", str);
                if (this.mEdmStorageProvider.getCount("CCMUserSelectableTable", contentValues) != 0) {
                    ContentValues contentValues2 = new ContentValues();
                    contentValues2.put("alias", str);
                    List intList = this.mEdmStorageProvider.getIntList("CCMUserSelectableTable", "isSelectable", contentValues2);
                    Log.d("EnterpriseDeviceManagerService", "isUserSelectable - allow :" + intList);
                    if (intList != null && intList.size() > 0) {
                        Log.d("EnterpriseDeviceManagerService", "isUserSelectable - allow :" + intList);
                        z = intList.contains(2);
                    }
                } else {
                    Log.d("EnterpriseDeviceManagerService", "isUserSelectable - uidCount = 0");
                }
            } catch (Exception e) {
                Log.d("EnterpriseDeviceManagerService", "isUserSelectable - Exception" + e.getMessage());
            }
        }
        return z;
    }

    public void setUserSelectable(int i, String str, boolean z) {
        if (str == null || str.length() == 0) {
            return;
        }
        Log.d("EnterpriseDeviceManagerService", "In setGrant - uid" + i + ",alias:" + str + ",value:" + z);
        int i2 = z ? 2 : 1;
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("alias", str);
            contentValues.put("isSelectable", Integer.valueOf(i2));
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put("alias", str);
            this.mEdmStorageProvider.putValues("CCMUserSelectableTable", contentValues, contentValues2);
        } catch (Exception unused) {
            Log.w("EnterpriseDeviceManagerService", "setUserSelectable() failed");
        }
    }

    public boolean keychainMarkedReset(ContextInfo contextInfo) {
        Log.d("EnterpriseDeviceManagerService", "in keychainMarkedReset");
        if (UserHandle.getAppId(Binder.getCallingUid()) != 1000) {
            Log.d("EnterpriseDeviceManagerService", "keychainMarkedReset - Cannot clear credentials, not a system app");
            return false;
        }
        if (contextInfo == null) {
            Log.d("EnterpriseDeviceManagerService", "keychainMarkedReset - Invalid Arguments");
            return false;
        }
        this.mEdmStorageProvider.deleteDataByFields("CCMCertTable", new String[]{"csr"}, new String[]{String.valueOf(0)});
        try {
            this.mEdmStorageProvider.deleteDataByFields("CCMCertGrantTable", null, null);
        } catch (Exception e) {
            Log.d("EnterpriseDeviceManagerService", "removeAllGrants - Exception" + e.getMessage());
        }
        try {
            this.mEdmStorageProvider.deleteDataByFields("CCMUserSelectableTable", null, null);
            return true;
        } catch (Exception e2) {
            Log.d("EnterpriseDeviceManagerService", "removeAllUserSelectable - Exception" + e2.getMessage());
            return true;
        }
    }

    public boolean isEmailAdminPkg(String str) {
        return "com.samsung.android.email.provider".equals(str);
    }
}
