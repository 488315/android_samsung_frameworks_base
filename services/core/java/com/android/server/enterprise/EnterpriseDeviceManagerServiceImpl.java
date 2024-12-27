package com.android.server.enterprise;

import android.R;
import android.app.ActivityManager;
import android.app.AppGlobals;
import android.app.AppOpsManager;
import android.app.admin.DevicePolicyManager;
import android.app.admin.IDevicePolicyManager;
import android.app.role.RoleManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.UserInfo;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.net.NetworkInfo;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.ConditionVariable;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.IServiceCreator;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.storage.StorageManager;
import android.sec.enterprise.auditlog.AuditLog;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import com.android.internal.content.PackageMonitor;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.ExplicitHealthCheckController$$ExternalSyntheticOutline0;
import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0;
import com.android.server.KnoxCaptureInputFilter$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.LockGuard;
import com.android.server.NetworkScoreService$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.RCPManagerService$$ExternalSyntheticOutline0;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.SystemService;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0;
import com.android.server.am.Pageboost$PageboostFileDBHelper$$ExternalSyntheticOutline0;
import com.android.server.ambientcontext.AmbientContextManagerPerUserService$$ExternalSyntheticOutline0;
import com.android.server.audio.AudioServiceExt$ResetSettingsReceiver$$ExternalSyntheticOutline0;
import com.android.server.enterprise.RestrictionToastManager;
import com.android.server.enterprise.accessControl.EnterpriseAccessController;
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
import com.android.server.enterprise.mam.MobileApplicationManagementService;
import com.android.server.enterprise.multiuser.MultiUserManagerService;
import com.android.server.enterprise.nap.NetworkAnalyticsService;
import com.android.server.enterprise.plm.ProcessLifecycleManager;
import com.android.server.enterprise.plm.ProcessStateTracker;
import com.android.server.enterprise.plm.StartReason;
import com.android.server.enterprise.plm.common.PlmMessage;
import com.android.server.enterprise.profile.ProfilePolicyService;
import com.android.server.enterprise.remotecontrol.RemoteInjectionService;
import com.android.server.enterprise.restriction.PhoneRestrictionPolicy;
import com.android.server.enterprise.restriction.RestrictionPolicy;
import com.android.server.enterprise.restriction.RoamingPolicy;
import com.android.server.enterprise.scpm.CloudConfigurationManagerService;
import com.android.server.enterprise.security.DeviceAccountPolicy;
import com.android.server.enterprise.security.PasswordPolicy;
import com.android.server.enterprise.security.SecurityPolicy;
import com.android.server.enterprise.storage.EdmStorageHelper;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.storage.EdmStorageProviderBase;
import com.android.server.enterprise.threatdefense.ThreatDefenseService;
import com.android.server.enterprise.utils.KpuHelper;
import com.android.server.enterprise.utils.SecContentProviderUtil;
import com.android.server.enterprise.utils.Utils;
import com.android.server.enterprise.vpn.VpnInfoPolicy;
import com.android.server.enterprise.wifi.WifiPolicy;
import com.android.server.knox.dar.ddar.proxy.DualDARComnService;
import com.android.server.knox.zt.devicetrust.EndpointMonitorImpl;
import com.android.server.pdb.PersistentDataBlockManagerInternal;
import com.android.server.pdb.PersistentDataBlockService;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EdmConstants;
import com.samsung.android.knox.EnterpriseDeviceAdminInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.EnterpriseKnoxManager;
import com.samsung.android.knox.KnoxInternalFeature;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.analytics.activation.ActivationMonitor;
import com.samsung.android.knox.analytics.activation.DevicePolicyListener;
import com.samsung.android.knox.analytics.activation.KESListener;
import com.samsung.android.knox.container.IKnoxContainerManager;
import com.samsung.android.knox.custom.CustomDeviceManager;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import com.samsung.android.knox.custom.utils.KnoxsdkFileLog;
import com.samsung.android.knox.dar.ddar.fsm.StateMachine;
import com.samsung.android.knox.knoxanalyticsproxy.KnoxAnalytics;
import com.samsung.android.knox.knoxanalyticsproxy.KnoxAnalyticsData;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import com.samsung.android.knox.license.IEnterpriseLicense;
import com.samsung.android.knox.license.LicenseInfo;
import com.samsung.android.knox.localservice.ApplicationRestrictionsInternal;
import com.samsung.android.knox.localservice.CloudConfigurationManagerInternal;
import com.samsung.android.knox.localservice.ConstrainedModeInternal;
import com.samsung.android.knox.localservice.MobileApplicationManagementInternal;
import com.samsung.android.knoxguard.service.utils.Constants;
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
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public class EnterpriseDeviceManagerServiceImpl extends EnterpriseDeviceManagerService {
    public static final List CONTAINER_ALLOWED_DEVICE_PERMISSION_LIST;
    public static final List EXCLUDED_ADMINS;
    public static final int MY_PID;
    public static final List NON_MDM_ADMINS;
    public static final String[] allowToSkipRuntimePermission;
    public static final String[] allowToUsingDirectPermissionCheckAPI;
    public static EnterpriseDeviceManagerServiceImpl mInstance;
    public static boolean mIsFirmwareUpgrade;
    public static final HashMap mKADelegationMapping;
    public static PackageManagerAdapter mPackageManagerAdapter;
    public static final ConditionVariable mServiceAdditionCondition;
    public final ArrayList mAdminList;
    public final HashMap mAdminMap;
    public ConstrainedModeInternal mConstrainedState;
    public IKnoxContainerManager mContainerService;
    public final Context mContext;
    public final int mCurrentUserId;
    public final IDevicePolicyManager mDPMS;
    public boolean mDeferredServicesCreated;
    public final EdmStorageProvider mEdmStorageProvider;
    public final Injector mInjector;
    public InternalHandler mInternalHandler;
    public final KeyCodeMediatorImpl mKeyCodeMediator;
    public final Object mLockDoNoUseDirectly;
    public MobileApplicationManagementInternal mMamState;
    public MyPackageMonitor mMonitor;
    public final IPackageManager mPMS;
    public PersonaManagerAdapter mPersonaManagerAdapter;
    public int mPseudoAdminUid;
    public final AnonymousClass1 mReceiver;
    public StorageManagerAdapter mStorageManagerAdapter;
    public SystemUIAdapter mSystemUIAdapter;
    public WindowManagerAdapter mWindowManagerAdapter;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.enterprise.EnterpriseDeviceManagerServiceImpl$2, reason: invalid class name */
    public final class AnonymousClass2 implements Runnable {
        @Override // java.lang.Runnable
        public final void run() {
            try {
                System.loadLibrary("edmnativehelperservice");
            } catch (Error e) {
                e.printStackTrace();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DeviceStatus {
        public int isKCClientActive;
        public int isKSPActive;
        public int isUserTypeAppSeparationExists;
        public int isUserTypeSecureFolderExists;
        public ArrayMap mDAs;
        public Bundle[] mDAsBundle;
        public ArrayMap mDPMRoleHolders;
        public Bundle[] mDPMRoleHoldersBundle;
        public int mDelegatedAdminContainerType;
        public ArrayMap mDelegatedAdmins;
        public Bundle[] mDelegatedAdminsBundle;
        public Bundle mDeviceStatusBundle;
        public int mInternalKnoxAdminCount;
        public String mKGClientState;
        public int mKnoxDACount;
        public ArrayMap mKnoxDAs;
        public Bundle[] mKnoxDAsBundle;
        public ArrayMap mKnoxInternalAdmins;
        public Bundle[] mKnoxInternalAdminsBundle;
        public String mOwnerPackageName;
        public int mOwnerType;
        public int mUserCount;

        public static void logDataBundle(Bundle bundle, String str) {
            KnoxAnalyticsData knoxAnalyticsData = new KnoxAnalyticsData("KNOX_CONTAINER", 6, str);
            for (String str2 : bundle.keySet()) {
                Object obj = bundle.get(str2);
                if (obj instanceof Integer) {
                    knoxAnalyticsData.setProperty(str2, ((Integer) obj).intValue());
                } else if (obj instanceof String) {
                    knoxAnalyticsData.setProperty(str2, (String) obj);
                }
            }
            KnoxAnalytics.log(knoxAnalyticsData);
        }

        public final void logData() {
            logDataBundle(this.mDeviceStatusBundle, "DEVICE_STATUS");
            int i = 0;
            int i2 = 0;
            while (true) {
                Bundle[] bundleArr = this.mDelegatedAdminsBundle;
                if (i2 >= bundleArr.length) {
                    break;
                }
                logDataBundle(bundleArr[i2], "DEVICE_STATUS_ADMIN_INFO");
                i2++;
            }
            int i3 = 0;
            while (true) {
                Bundle[] bundleArr2 = this.mDPMRoleHoldersBundle;
                if (i3 >= bundleArr2.length) {
                    break;
                }
                logDataBundle(bundleArr2[i3], "DEVICE_STATUS_ADMIN_INFO");
                i3++;
            }
            int i4 = 0;
            while (true) {
                Bundle[] bundleArr3 = this.mKnoxDAsBundle;
                if (i4 >= bundleArr3.length) {
                    break;
                }
                logDataBundle(bundleArr3[i4], "DEVICE_STATUS_ADMIN_INFO");
                i4++;
            }
            int i5 = 0;
            while (true) {
                Bundle[] bundleArr4 = this.mDAsBundle;
                if (i5 >= bundleArr4.length) {
                    break;
                }
                logDataBundle(bundleArr4[i5], "DEVICE_STATUS_ADMIN_INFO");
                i5++;
            }
            while (true) {
                Bundle[] bundleArr5 = this.mKnoxInternalAdminsBundle;
                if (i >= bundleArr5.length) {
                    return;
                }
                logDataBundle(bundleArr5[i], "DEVICE_STATUS_ADMIN_INFO");
                i++;
            }
        }

        public final void writeToBundle() {
            int i;
            Exception e;
            int i2;
            Exception e2;
            StringBuilder sb = new StringBuilder("OwnerType: ");
            sb.append(Integer.toString(this.mOwnerType));
            sb.append(" and its packageName: ");
            String str = this.mOwnerPackageName;
            if (str == null) {
                str = "";
            }
            sb.append(str);
            KnoxsdkFileLog.d("EDM_KnoxAnalytics", sb.toString());
            KnoxsdkFileLog.d("EDM_KnoxAnalytics", this.isUserTypeSecureFolderExists == 1 ? "SecureFolder exists" : "SecureFolder does not exist");
            KnoxsdkFileLog.d("EDM_KnoxAnalytics", this.isUserTypeAppSeparationExists == 1 ? "SeparatedApps exists" : "SeparatedApps does not exist");
            KnoxsdkFileLog.d("EDM_KnoxAnalytics", "Knox licensed admin count: " + Integer.toString(this.mKnoxDACount));
            KnoxsdkFileLog.d("EDM_KnoxAnalytics", "Knox internal admin count: " + Integer.toString(this.mInternalKnoxAdminCount));
            Bundle bundle = new Bundle();
            this.mDeviceStatusBundle = bundle;
            bundle.putInt("ucnt", this.mUserCount);
            this.mDeviceStatusBundle.putInt("KLAcnt", this.mKnoxDACount);
            this.mDeviceStatusBundle.putInt("iklacnt", this.mInternalKnoxAdminCount);
            this.mDeviceStatusBundle.putInt("ot", this.mOwnerType);
            this.mDeviceStatusBundle.putString("dapn", this.mOwnerPackageName);
            this.mDeviceStatusBundle.putInt("sa", this.isUserTypeAppSeparationExists);
            this.mDeviceStatusBundle.putInt("sf", this.isUserTypeSecureFolderExists);
            this.mDeviceStatusBundle.putString("kg", this.mKGClientState);
            this.mDeviceStatusBundle.putInt("kc", this.isKCClientActive);
            this.mDeviceStatusBundle.putInt("ksp", this.isKSPActive);
            this.mDelegatedAdminsBundle = new Bundle[this.mDelegatedAdmins.size()];
            for (int i3 = 0; i3 < this.mDelegatedAdmins.size(); i3++) {
                Bundle bundle2 = new Bundle();
                bundle2.putInt("cTp", this.mDelegatedAdminContainerType);
                bundle2.putInt("at", 3);
                bundle2.putString("dapn", (String) this.mDelegatedAdmins.keyAt(i3));
                Iterator it = EnterpriseDeviceManagerServiceImpl.mKADelegationMapping.values().iterator();
                while (it.hasNext()) {
                    bundle2.putInt((String) it.next(), 0);
                }
                List list = (List) this.mDelegatedAdmins.valueAt(i3);
                for (int i4 = 0; i4 < list.size(); i4++) {
                    bundle2.putInt((String) EnterpriseDeviceManagerServiceImpl.mKADelegationMapping.get(list.get(i4)), 1);
                }
                this.mDelegatedAdminsBundle[i3] = bundle2;
            }
            this.mDPMRoleHoldersBundle = new Bundle[this.mDPMRoleHolders.size()];
            for (int i5 = 0; i5 < this.mDPMRoleHolders.size(); i5++) {
                Bundle bundle3 = new Bundle();
                bundle3.putInt("cTp", ((Integer) this.mDPMRoleHolders.keyAt(i5)).intValue());
                bundle3.putInt("at", 2);
                bundle3.putString("dapn", (String) this.mDPMRoleHolders.valueAt(i5));
                this.mDPMRoleHoldersBundle[i5] = bundle3;
            }
            Iterator it2 = this.mKnoxDAs.entrySet().iterator();
            int i6 = 0;
            while (it2.hasNext()) {
                i6 += ((List) ((Map.Entry) it2.next()).getValue()).size();
            }
            this.mKnoxDAsBundle = new Bundle[i6];
            int i7 = 0;
            for (int i8 = 0; i8 < this.mKnoxDAs.size(); i8++) {
                int intValue = ((Integer) this.mKnoxDAs.keyAt(i8)).intValue();
                try {
                    for (String str2 : (List) this.mKnoxDAs.valueAt(i8)) {
                        Bundle bundle4 = new Bundle();
                        bundle4.putInt("cTp", intValue);
                        bundle4.putInt("at", 1);
                        bundle4.putString("dapn", str2);
                        i2 = i7 + 1;
                        try {
                            this.mKnoxDAsBundle[i7] = bundle4;
                            i7 = i2;
                        } catch (Exception e3) {
                            e2 = e3;
                            e2.printStackTrace();
                            i7 = i2;
                        }
                    }
                } catch (Exception e4) {
                    i2 = i7;
                    e2 = e4;
                }
            }
            Iterator it3 = this.mDAs.entrySet().iterator();
            int i9 = 0;
            while (it3.hasNext()) {
                i9 += ((List) ((Map.Entry) it3.next()).getValue()).size();
            }
            this.mDAsBundle = new Bundle[i9];
            int i10 = 0;
            for (int i11 = 0; i11 < this.mDAs.size(); i11++) {
                int intValue2 = ((Integer) this.mDAs.keyAt(i11)).intValue();
                try {
                    for (String str3 : (List) this.mDAs.valueAt(i11)) {
                        Bundle bundle5 = new Bundle();
                        bundle5.putInt("cTp", intValue2);
                        bundle5.putInt("at", 4);
                        bundle5.putString("dapn", str3);
                        i = i10 + 1;
                        try {
                            this.mDAsBundle[i10] = bundle5;
                            i10 = i;
                        } catch (Exception e5) {
                            e = e5;
                            e.printStackTrace();
                            i10 = i;
                        }
                    }
                } catch (Exception e6) {
                    i = i10;
                    e = e6;
                }
            }
            Iterator it4 = this.mKnoxInternalAdmins.entrySet().iterator();
            int i12 = 0;
            while (it4.hasNext()) {
                i12 += ((List) ((Map.Entry) it4.next()).getValue()).size();
            }
            this.mKnoxInternalAdminsBundle = new Bundle[i12];
            int i13 = 0;
            for (int i14 = 0; i14 < this.mKnoxInternalAdmins.size(); i14++) {
                int intValue3 = ((Integer) this.mKnoxInternalAdmins.keyAt(i14)).intValue();
                try {
                    for (String str4 : (List) this.mKnoxInternalAdmins.valueAt(i14)) {
                        Bundle bundle6 = new Bundle();
                        bundle6.putInt("cTp", intValue3);
                        bundle6.putInt("at", 0);
                        String[] split = str4.split(";");
                        String str5 = split[0];
                        if (str5 != null) {
                            bundle6.putString("dapn", str5);
                        }
                        String str6 = split[1];
                        if (str6 != null) {
                            bundle6.putString(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, str6);
                        }
                        int i15 = i13 + 1;
                        try {
                            this.mKnoxInternalAdminsBundle[i13] = bundle6;
                            i13 = i15;
                        } catch (Exception e7) {
                            e = e7;
                            i13 = i15;
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e8) {
                    e = e8;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Injector {
        public final Context mContext;

        public Injector(Context context) {
            this.mContext = context;
        }

        public static void addLazySystemService(EnterpriseServiceCallback enterpriseServiceCallback, final String str) {
            EnterpriseService.addPolicyService(str, enterpriseServiceCallback);
            ((ArrayList) EnterpriseService.sLazyServiceList).add(str);
            ServiceManager.addService(str, new IServiceCreator() { // from class: com.android.server.enterprise.EnterpriseService$$ExternalSyntheticLambda0
                public final IBinder createService(Context context) {
                    String str2 = str;
                    Log.d("EnterpriseService", "addLazySystemService : " + str2);
                    return (IBinder) EnterpriseService.getPolicyService(str2);
                }
            });
        }

        public static void addSystemService(EnterpriseServiceCallback enterpriseServiceCallback, String str) {
            Map map = EnterpriseService.sPolicyServices;
            IBinder iBinder = (IBinder) enterpriseServiceCallback;
            ServiceManager.addService(str, iBinder);
            enterpriseServiceCallback.notifyToAddSystemService(str, iBinder);
            if (((TreeMap) EnterpriseService.getPolicyServices()).containsKey(str)) {
                return;
            }
            EnterpriseService.addPolicyService(str, enterpriseServiceCallback);
        }

        public static void invokeSystemReadyIfNeeded() {
            for (Map.Entry entry : ((TreeMap) EnterpriseService.getPolicyServices()).entrySet()) {
                try {
                    EnterpriseService.invokeSystemReadyIfNeeded((EnterpriseServiceCallback) entry.getValue(), (String) entry.getKey());
                } catch (Exception e) {
                    Log.e("EnterpriseService", "invokeSystemReadyIfNeeded() failed in" + ((String) entry.getKey()), e);
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class InternalHandler extends Handler {
        public InternalHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            Log.i("EnterpriseDeviceManagerService", "handleMessage : " + message.what + " " + message.arg1 + " " + message.obj);
            if (message.what != 2) {
                return;
            }
            int i = message.arg2;
            NetworkScoreService$$ExternalSyntheticOutline0.m(i, "User ", " has been removed!", "EnterpriseDeviceManagerService");
            ComponentName componentName = (ComponentName) message.obj;
            List list = EnterpriseDeviceManagerServiceImpl.EXCLUDED_ADMINS;
            EnterpriseDeviceManagerServiceImpl.this.removeActiveAdminDelayed(i, componentName, false);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Lifecycle extends SystemService {
        public final EnterpriseDeviceManagerServiceImpl mService;

        public Lifecycle(Context context) {
            super(context);
            this.mService = new EnterpriseDeviceManagerServiceImpl(context);
        }

        @Override // com.android.server.SystemService
        public final void onBootPhase(int i) {
            EnterpriseDeviceManagerServiceImpl.m492$$Nest$msystemReady(this.mService, i);
        }

        @Override // com.android.server.SystemService
        public final void onStart() {
            publishBinderService("enterprise_policy", this.mService);
        }

        @Override // com.android.server.SystemService
        public final void onUserStarting(SystemService.TargetUser targetUser) {
            int userIdentifier = targetUser.getUserIdentifier();
            this.mService.getClass();
            Log.d("EnterpriseDeviceManagerService", "onUserStarting() : userId = " + userIdentifier);
            Iterator it = ((TreeMap) EnterpriseService.getPolicyServices()).values().iterator();
            while (it.hasNext()) {
                ((EnterpriseServiceCallback) it.next()).onUserStarting(userIdentifier);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MyPackageMonitor extends PackageMonitor {
        public MyPackageMonitor() {
        }

        public final void onPackageUpdateFinished(String str, int i) {
            EnterpriseDeviceAdminInfo enterpriseDeviceAdminInfo;
            EnterpriseDeviceAdminInfo enterpriseDeviceAdminInfo2;
            NetworkScoreService$$ExternalSyntheticOutline0.m(i, "onPackageUpdateFinished - packageName: ", str, ", uid: ", "EnterpriseDeviceManagerService");
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
                        EnterpriseDeviceManagerServiceImpl.this.removeActiveAdminDelayed(UserHandle.getUserId(enterpriseDeviceAdminInfo.getActivityInfo().applicationInfo.uid), enterpriseDeviceAdminInfo.getComponent(), false);
                        return;
                    }
                    EnterpriseDeviceManagerServiceImpl.this.mAdminList.remove(enterpriseDeviceAdminInfo);
                    EnterpriseDeviceManagerServiceImpl.this.mAdminMap.remove(Integer.valueOf(enterpriseDeviceAdminInfo.getActivityInfo().applicationInfo.uid));
                    EnterpriseDeviceManagerServiceImpl.this.mAdminList.add(enterpriseDeviceAdminInfo2);
                    EnterpriseDeviceManagerServiceImpl.this.mAdminMap.put(Integer.valueOf(enterpriseDeviceAdminInfo2.getActivityInfo().applicationInfo.uid), enterpriseDeviceAdminInfo2);
                }
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:28:0x00ce A[Catch: all -> 0x009b, TryCatch #1 {all -> 0x009b, blocks: (B:4:0x0009, B:6:0x002a, B:8:0x003a, B:13:0x004a, B:18:0x0072, B:21:0x0078, B:23:0x0081, B:26:0x008b, B:28:0x00ce, B:30:0x0104, B:32:0x00a0, B:36:0x00b0, B:38:0x012b, B:12:0x0160, B:42:0x0164), top: B:3:0x0009, inners: #0 }] */
        /* JADX WARN: Removed duplicated region for block: B:30:0x0104 A[Catch: all -> 0x009b, TryCatch #1 {all -> 0x009b, blocks: (B:4:0x0009, B:6:0x002a, B:8:0x003a, B:13:0x004a, B:18:0x0072, B:21:0x0078, B:23:0x0081, B:26:0x008b, B:28:0x00ce, B:30:0x0104, B:32:0x00a0, B:36:0x00b0, B:38:0x012b, B:12:0x0160, B:42:0x0164), top: B:3:0x0009, inners: #0 }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onSomePackagesChanged() {
            /*
                Method dump skipped, instructions count: 360
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.EnterpriseDeviceManagerServiceImpl.MyPackageMonitor.onSomePackagesChanged():void");
        }
    }

    /* renamed from: -$$Nest$msystemReady, reason: not valid java name */
    public static void m492$$Nest$msystemReady(EnterpriseDeviceManagerServiceImpl enterpriseDeviceManagerServiceImpl, int i) {
        List<ComponentName> list;
        boolean z;
        EnterpriseDeviceAdminInfo findAdmin;
        boolean z2;
        ConstrainedModeInternal constrainedModeInternal;
        enterpriseDeviceManagerServiceImpl.getClass();
        Log.d("EnterpriseDeviceManagerService", "systemReady() : mCurrentPhase = " + i);
        if (i == 480) {
            Log.d("EnterpriseDeviceManagerService", "onStartUser() : userId = 0");
        } else if (i == 500) {
            Log.w("EnterpriseDeviceManagerService", "systemReady()");
            Log.d("EnterpriseDeviceManagerService", "ConstrainedModeService move to system ready ");
            Context context = enterpriseDeviceManagerServiceImpl.mInjector.mContext;
            if (context == null) {
                byte[] bArr = ConstrainedModeService.CONSTRAINED_DELIMITER;
                throw new IllegalArgumentException("Context cannot be null");
            }
            if (ConstrainedModeService.sConstrainedService == null) {
                synchronized (ConstrainedModeService.lock) {
                    try {
                        if (ConstrainedModeService.sConstrainedService == null) {
                            ConstrainedModeService constrainedModeService = new ConstrainedModeService(context);
                            ConstrainedModeService.sConstrainedService = constrainedModeService;
                            LocalServices.addService(ConstrainedModeInternal.class, constrainedModeService);
                        }
                    } finally {
                    }
                }
            }
            Context context2 = enterpriseDeviceManagerServiceImpl.mInjector.mContext;
            if (context2 == null) {
                MobileApplicationManagementService mobileApplicationManagementService = MobileApplicationManagementService.mamService;
                throw new IllegalArgumentException("Context cannot be null");
            }
            if (MobileApplicationManagementService.mamService == null) {
                synchronized (MobileApplicationManagementService.lock) {
                    MobileApplicationManagementService mobileApplicationManagementService2 = new MobileApplicationManagementService(context2);
                    MobileApplicationManagementService.mamService = mobileApplicationManagementService2;
                    LocalServices.addService(MobileApplicationManagementInternal.class, mobileApplicationManagementService2);
                }
            }
            enterpriseDeviceManagerServiceImpl.mConstrainedState = (ConstrainedModeInternal) LocalServices.getService(ConstrainedModeInternal.class);
            enterpriseDeviceManagerServiceImpl.mMamState = (MobileApplicationManagementInternal) LocalServices.getService(MobileApplicationManagementInternal.class);
            if (enterpriseDeviceManagerServiceImpl.isDeviceOwnedByOrganization() || enterpriseDeviceManagerServiceImpl.isMdmAdminPresentInternal() || ((constrainedModeInternal = enterpriseDeviceManagerServiceImpl.mConstrainedState) != null && constrainedModeInternal.checkConstrainedState())) {
                enterpriseDeviceManagerServiceImpl.createDeferredServices();
            } else {
                try {
                    if (!((ArrayList) enterpriseDeviceManagerServiceImpl.mEdmStorageProvider.getValuesList("LICENSE", new String[]{"pkgName"}, null)).isEmpty()) {
                        Log.d("EnterpriseDeviceManagerService", "checkIfKnoxLicenseActivated() : License is activated");
                        Log.d("EnterpriseDeviceManagerService", "knox ztna service started");
                        Injector injector = enterpriseDeviceManagerServiceImpl.mInjector;
                        KnoxNetworkFilterService knoxNetworkFilterService = new KnoxNetworkFilterService(enterpriseDeviceManagerServiceImpl.mContext);
                        injector.getClass();
                        Injector.addLazySystemService(knoxNetworkFilterService, "knox_nwFilterMgr_policy");
                    }
                } catch (Exception e) {
                    Log.e("EnterpriseDeviceManagerService", "checkIfKnoxLicenseActivated() : failed. ", e);
                }
            }
            ProcessLifecycleManager processLifecycleManager = ProcessLifecycleManager.getInstance(enterpriseDeviceManagerServiceImpl.mContext);
            StartReason startReason = StartReason.EDM_SYSTEM_READY;
            ProcessStateTracker processStateTracker = processLifecycleManager.mStateTracker;
            processStateTracker.getClass();
            processStateTracker.sendMessage(PlmMessage.obtain(processStateTracker, 1, startReason));
            RestrictionToastManager.mContext = enterpriseDeviceManagerServiceImpl.mContext;
            RestrictionToastManager.mRestrictionToastHandler = new RestrictionToastManager.RestrictionToastHandler(KnoxCaptureInputFilter$$ExternalSyntheticOutline0.m("RestrictionToastManagerThread").getLooper());
            Context context3 = enterpriseDeviceManagerServiceImpl.mContext;
            AccountsReceiver accountsReceiver = new AccountsReceiver();
            AccountsReceiver.AnonymousClass1 anonymousClass1 = new BroadcastReceiver() { // from class: com.android.server.enterprise.email.AccountsReceiver.1
                public AnonymousClass1() {
                }

                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context4, Intent intent) {
                    String str;
                    try {
                        String action = intent.getAction();
                        int intExtra = intent.getIntExtra("com.samsung.android.knox.intent.extra.USER_HANDLE_ID_INTERNAL", intent.getIntExtra("user_handle_id", 0));
                        if (action == null) {
                            Map map = AccountsReceiver.mSmimeCerticateList;
                            Log.i("AccountsReceiver", "onReceive() : Action is null");
                            return;
                        }
                        Map map2 = AccountsReceiver.mSmimeCerticateList;
                        Log.i("AccountsReceiver", "onReceive() userId = " + intExtra);
                        if ("com.samsung.android.knox.intent.action.CBA_INSTALL_STATUS_INTERNAL".equals(action)) {
                            int intExtra2 = intent.getIntExtra("com.samsung.android.knox.intent.extra.STATUS", 1);
                            intent.getIntExtra("com.samsung.android.knox.intent.extra.USER_ID_INTERNAL", 0);
                            long longExtra = intent.getLongExtra("com.samsung.android.knox.intent.extra.ACCOUNT_ID_INTERNAL", -1L);
                            if (intExtra2 == 1) {
                                Log.i("AccountsReceiver", "onReceive() : failed to install cba on accountId : " + longExtra);
                            }
                            AccountsReceiver.m505$$Nest$msendClientAuthResultIntent(AccountsReceiver.this, longExtra, intExtra2);
                            return;
                        }
                        str = "com.samsung.android.knox.intent.extra.SERVICE_NAME_INTERNAL";
                        if (!action.equals("edm.intent.action.sec.MDM_ACCOUNT_SETUP_RESULT")) {
                            try {
                                if (!action.equals("com.samsung.android.knox.intent.action.MDM_ACCOUNT_SETUP_RESULT_INTERNAL")) {
                                    if (action.equals("edm.intent.action.sec.MDM_ACCOUNT_DELETE_RESULT") || action.equals("com.samsung.android.knox.intent.action.MDM_ACCOUNT_DELETE_RESULT_INTERNAL")) {
                                        int intExtra3 = intent.getIntExtra(Constants.JSON_CLIENT_DATA_STATUS, 8);
                                        String stringExtra = intent.getStringExtra("user_id");
                                        String stringExtra2 = intent.getStringExtra("service");
                                        long longExtra2 = intent.getLongExtra("account_id", -1L);
                                        int intExtra4 = intent.getIntExtra("com.samsung.android.knox.intent.extra.ACCOUNT_SETUP_RESULT_STATUS_INTERNAL", intExtra3);
                                        long longExtra3 = intent.getLongExtra("com.samsung.android.knox.intent.extra.ACCOUNT_ID_INTERNAL", longExtra2);
                                        if (stringExtra == null) {
                                            stringExtra = intent.getStringExtra("com.samsung.android.knox.intent.extra.USER_ID_INTERNAL");
                                        }
                                        if (stringExtra2 == null) {
                                            stringExtra2 = intent.getStringExtra("com.samsung.android.knox.intent.extra.SERVICE_INTERNAL");
                                        }
                                        if ("eas".equalsIgnoreCase(stringExtra2)) {
                                            String stringExtra3 = intent.getStringExtra("server_name");
                                            if (stringExtra3 == null) {
                                                stringExtra3 = intent.getStringExtra("com.samsung.android.knox.intent.extra.SERVICE_NAME_INTERNAL");
                                            }
                                            Intent intent2 = new Intent("com.samsung.android.knox.intent.action.EXCHANGE_ACCOUNT_DELETE_RESULT");
                                            intent2.putExtra("com.samsung.android.knox.intent.extra.EMAIL_ADDRESS", stringExtra);
                                            intent2.putExtra("com.samsung.android.knox.intent.extra.RESULT", intExtra4);
                                            intent2.putExtra("com.samsung.android.knox.intent.extra.ACCOUNT_ID", longExtra3);
                                            intent2.putExtra("com.samsung.android.knox.intent.extra.SERVER_ADDRESS", stringExtra3);
                                            intent2.putExtra("containerid", intExtra);
                                            AccountsReceiver.this.mContext.sendBroadcastAsUser(intent2, UserHandle.ALL, "com.samsung.android.knox.permission.KNOX_EXCHANGE");
                                            return;
                                        }
                                        String stringExtra4 = intent.getStringExtra("receive_host");
                                        if (stringExtra4 == null) {
                                            stringExtra4 = intent.getStringExtra("com.samsung.android.knox.intent.extra.RECEIVE_HOST_INTERNAL");
                                        }
                                        Intent intent3 = new Intent("com.samsung.android.knox.intent.action.EMAIL_ACCOUNT_DELETE_RESULT");
                                        intent3.putExtra("com.samsung.android.knox.intent.extra.EMAIL_ADDRESS", stringExtra);
                                        intent3.putExtra("com.samsung.android.knox.intent.extra.RESULT", intExtra4);
                                        intent3.putExtra("com.samsung.android.knox.intent.extra.ACCOUNT_ID", longExtra3);
                                        intent3.putExtra("com.samsung.android.knox.intent.extra.INCOMING_PROTOCOL", stringExtra2);
                                        intent3.putExtra("com.samsung.android.knox.intent.extra.INCOMING_SERVER_ADDRESS", stringExtra4);
                                        intent3.putExtra("containerid", intExtra);
                                        AccountsReceiver.this.mContext.sendBroadcastAsUser(intent3, UserHandle.ALL, "com.samsung.android.knox.permission.KNOX_EMAIL");
                                        return;
                                    }
                                    return;
                                }
                            } catch (Exception e2) {
                                e = e2;
                                str = "AccountsReceiver";
                                Map map3 = AccountsReceiver.mSmimeCerticateList;
                                Log.e(str, "onRecieve() failed. ", e);
                            }
                        }
                        int intExtra5 = intent.getIntExtra(Constants.JSON_CLIENT_DATA_STATUS, 8);
                        String stringExtra5 = intent.getStringExtra("user_id");
                        String stringExtra6 = intent.getStringExtra("service");
                        long longExtra4 = intent.getLongExtra("account_id", -1L);
                        int intExtra6 = intent.getIntExtra("com.samsung.android.knox.intent.extra.ACCOUNT_SETUP_RESULT_STATUS_INTERNAL", intExtra5);
                        long longExtra5 = intent.getLongExtra("com.samsung.android.knox.intent.extra.ACCOUNT_ID_INTERNAL", longExtra4);
                        if (stringExtra5 == null) {
                            stringExtra5 = intent.getStringExtra("com.samsung.android.knox.intent.extra.USER_ID_INTERNAL");
                        }
                        if (stringExtra6 == null) {
                            stringExtra6 = intent.getStringExtra("com.samsung.android.knox.intent.extra.SERVICE_INTERNAL");
                        }
                        try {
                            if (!"eas".equalsIgnoreCase(stringExtra6)) {
                                String stringExtra7 = intent.getStringExtra("receive_host");
                                if (stringExtra7 == null) {
                                    stringExtra7 = intent.getStringExtra("com.samsung.android.knox.intent.extra.RECEIVE_HOST_INTERNAL");
                                }
                                Intent intent4 = new Intent("com.samsung.android.knox.intent.action.EMAIL_ACCOUNT_ADD_RESULT");
                                intent4.putExtra("com.samsung.android.knox.intent.extra.EMAIL_ADDRESS", stringExtra5);
                                intent4.putExtra("com.samsung.android.knox.intent.extra.RESULT", intExtra6);
                                intent4.putExtra("com.samsung.android.knox.intent.extra.ACCOUNT_ID", longExtra5);
                                intent4.putExtra("com.samsung.android.knox.intent.extra.INCOMING_PROTOCOL", stringExtra6);
                                intent4.putExtra("com.samsung.android.knox.intent.extra.INCOMING_SERVER_ADDRESS", stringExtra7);
                                intent4.putExtra("containerid", intExtra);
                                AccountsReceiver.this.mContext.sendBroadcastAsUser(intent4, UserHandle.ALL, "com.samsung.android.knox.permission.KNOX_EMAIL");
                                return;
                            }
                            String stringExtra8 = intent.getStringExtra("server_name");
                            if (stringExtra8 == null) {
                                stringExtra8 = intent.getStringExtra("com.samsung.android.knox.intent.extra.SERVICE_NAME_INTERNAL");
                            }
                            Intent intent5 = new Intent("com.samsung.android.knox.intent.action.EXCHANGE_ACCOUNT_ADD_RESULT");
                            intent5.putExtra("com.samsung.android.knox.intent.extra.EMAIL_ADDRESS", stringExtra5);
                            intent5.putExtra("com.samsung.android.knox.intent.extra.RESULT", intExtra6);
                            intent5.putExtra("com.samsung.android.knox.intent.extra.ACCOUNT_ID", longExtra5);
                            intent5.putExtra("com.samsung.android.knox.intent.extra.SERVER_ADDRESS", stringExtra8);
                            intent5.putExtra("containerid", intExtra);
                            AccountsReceiver.this.mContext.sendBroadcastAsUser(intent5, UserHandle.ALL, "com.samsung.android.knox.permission.KNOX_EXCHANGE");
                            AccountSMIMECertificate sMIMECertificate = AccountsReceiver.getSMIMECertificate(intExtra + "#" + stringExtra5);
                            if (sMIMECertificate == null || intExtra6 != 0 || longExtra5 == -1) {
                                return;
                            }
                            SMIMEThread sMIMEThread = new SMIMEThread();
                            sMIMEThread.mSMIMECertificate = sMIMECertificate;
                            sMIMEThread.mAccId = longExtra5;
                            sMIMEThread.start();
                            Log.i("AccountsReceiver", "onRecieve() : SMIMECertificate install called.");
                        } catch (Exception e3) {
                            e = e3;
                            Map map32 = AccountsReceiver.mSmimeCerticateList;
                            Log.e(str, "onRecieve() failed. ", e);
                        }
                    } catch (Exception e4) {
                        e = e4;
                        str = "AccountsReceiver";
                    }
                }
            };
            accountsReceiver.mContext = context3;
            try {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("edm.intent.action.sec.MDM_ACCOUNT_SETUP_RESULT");
                intentFilter.addAction("edm.intent.action.sec.MDM_ACCOUNT_DELETE_RESULT");
                intentFilter.addAction("com.samsung.android.knox.intent.action.MDM_ACCOUNT_SETUP_RESULT_INTERNAL");
                intentFilter.addAction("com.samsung.android.knox.intent.action.MDM_ACCOUNT_DELETE_RESULT_INTERNAL");
                intentFilter.addAction("com.samsung.android.knox.intent.action.CBA_INSTALL_STATUS_INTERNAL");
                context3.registerReceiverAsUser(anonymousClass1, UserHandle.ALL, intentFilter, "com.samsung.android.knox.permission.KNOX_EMAIL", null, 2);
            } catch (Exception e2) {
                Log.e("AccountsReceiver", "register Account Receiver : failed. ", e2);
            }
            HandlerThread handlerThread = new HandlerThread("InternalHandlerThread", 10);
            handlerThread.start();
            InternalHandler internalHandler = enterpriseDeviceManagerServiceImpl.new InternalHandler(handlerThread.getLooper());
            enterpriseDeviceManagerServiceImpl.mInternalHandler = internalHandler;
            internalHandler.post(new AnonymousClass2());
            synchronized (enterpriseDeviceManagerServiceImpl.getLockObject()) {
                Log.w("EnterpriseDeviceManagerService", "loadActiveAdmins");
                Iterator it = enterpriseDeviceManagerServiceImpl.mEdmStorageProvider.getAdminUidList().iterator();
                while (it.hasNext()) {
                    Integer num = (Integer) it.next();
                    int intValue = num.intValue();
                    try {
                        z2 = enterpriseDeviceManagerServiceImpl.mEdmStorageProvider.checkPseudoAdminForUid(intValue);
                    } catch (Exception e3) {
                        Log.d("EnterpriseDeviceManagerService", "mEdmStorageProvider.checkPseudoAdminForUid: error " + e3.getMessage());
                        z2 = false;
                    }
                    if (z2) {
                        EnterpriseDeviceAdminInfo enterpriseDeviceAdminInfo = new EnterpriseDeviceAdminInfo(true);
                        enterpriseDeviceManagerServiceImpl.mPseudoAdminUid = intValue;
                        enterpriseDeviceManagerServiceImpl.mAdminMap.put(num, enterpriseDeviceAdminInfo);
                    } else {
                        ComponentName componentNameForUid = enterpriseDeviceManagerServiceImpl.mEdmStorageProvider.getComponentNameForUid(intValue);
                        if (componentNameForUid != null) {
                            try {
                                EnterpriseDeviceAdminInfo findAdmin2 = enterpriseDeviceManagerServiceImpl.findAdmin(componentNameForUid, UserHandle.getUserId(intValue));
                                if (findAdmin2 != null) {
                                    int i2 = findAdmin2.getActivityInfo().applicationInfo.uid;
                                    enterpriseDeviceManagerServiceImpl.mAdminMap.put(Integer.valueOf(i2), findAdmin2);
                                    enterpriseDeviceManagerServiceImpl.mAdminList.add(findAdmin2);
                                    if (i2 == intValue) {
                                        try {
                                            if (!enterpriseDeviceManagerServiceImpl.mDPMS.isAdminActive(componentNameForUid, UserHandle.getUserId(intValue))) {
                                                enterpriseDeviceManagerServiceImpl.setAdminRemovable(new ContextInfo(intValue), true, null);
                                                enterpriseDeviceManagerServiceImpl.removeActiveAdminDelayed(UserHandle.getUserId(intValue), findAdmin2.getComponent(), false);
                                                Log.d("EnterpriseDeviceManagerService", "Admin invalid on DPM, removing from EDM:   " + componentNameForUid.flattenToString() + ", uid: " + intValue);
                                            }
                                        } catch (RemoteException e4) {
                                            e4.printStackTrace();
                                        }
                                    } else if (enterpriseDeviceManagerServiceImpl.mEdmStorageProvider.putInt(intValue, 0, i2, "ADMIN_INFO", "adminUid")) {
                                        Log.d("EnterpriseDeviceManagerService", "Admin " + componentNameForUid.flattenToString() + ", updated with new currentUid: " + i2 + ", old storedUid: " + intValue);
                                        try {
                                            if (!enterpriseDeviceManagerServiceImpl.mDPMS.isAdminActive(componentNameForUid, UserHandle.getUserId(intValue))) {
                                                enterpriseDeviceManagerServiceImpl.setAdminRemovable(new ContextInfo(intValue), true, null);
                                                enterpriseDeviceManagerServiceImpl.removeActiveAdminDelayed(UserHandle.getUserId(intValue), findAdmin2.getComponent(), false);
                                                Log.d("EnterpriseDeviceManagerService", "Admin invalid on DPM, removing from EDM: " + componentNameForUid.flattenToString() + ", uid: " + i2);
                                            }
                                        } catch (RemoteException e5) {
                                            e5.printStackTrace();
                                        }
                                    } else {
                                        findAdmin2.getActivityInfo().applicationInfo.uid = intValue;
                                        enterpriseDeviceManagerServiceImpl.setAdminRemovable(new ContextInfo(intValue), true, null);
                                        enterpriseDeviceManagerServiceImpl.removeActiveAdminDelayed(UserHandle.getUserId(intValue), findAdmin2.getComponent(), false);
                                        Log.d("EnterpriseDeviceManagerService", "Failed updating uid, removed: " + componentNameForUid.flattenToString() + ", uid: " + intValue);
                                    }
                                } else {
                                    enterpriseDeviceManagerServiceImpl.setAdminRemovable(new ContextInfo(intValue), true, null);
                                    enterpriseDeviceManagerServiceImpl.removeActiveAdminDelayed(UserHandle.getUserId(intValue), componentNameForUid, false);
                                    Log.d("EnterpriseDeviceManagerService", "Admin loaded null, removed: " + componentNameForUid.flattenToString() + ", uid: " + intValue);
                                }
                            } catch (RuntimeException e6) {
                                Log.e("EnterpriseDeviceManagerService", "Exception occured while loading active admins " + e6.getMessage());
                            }
                        }
                    }
                }
                for (UserInfo userInfo : ((UserManager) enterpriseDeviceManagerServiceImpl.mContext.getSystemService("user")).getUsers()) {
                    List activeAdminsInfo = enterpriseDeviceManagerServiceImpl.getActiveAdminsInfo(userInfo.id);
                    try {
                        list = enterpriseDeviceManagerServiceImpl.mDPMS.getActiveAdmins(userInfo.id);
                    } catch (RemoteException e7) {
                        Log.e("EnterpriseDeviceManagerService", "Failed to get active admins from dpm " + e7.getMessage());
                        list = null;
                    }
                    if (list != null) {
                        ArrayList arrayList = (ArrayList) activeAdminsInfo;
                        if (arrayList.isEmpty()) {
                            Log.d("EnterpriseDeviceManagerService", "Adding all admins from DPM for user : " + userInfo.id);
                            for (ComponentName componentName : list) {
                                EnterpriseDeviceAdminInfo findAdmin3 = enterpriseDeviceManagerServiceImpl.findAdmin(componentName, userInfo.id);
                                if (findAdmin3 != null) {
                                    Log.d("EnterpriseDeviceManagerService", "Adding missing admin to EDM : " + componentName);
                                    int i3 = findAdmin3.getActivityInfo().applicationInfo.uid;
                                    enterpriseDeviceManagerServiceImpl.mAdminMap.put(Integer.valueOf(i3), findAdmin3);
                                    enterpriseDeviceManagerServiceImpl.mAdminList.add(findAdmin3);
                                    enterpriseDeviceManagerServiceImpl.mEdmStorageProvider.addAdmin(i3, componentName.flattenToString());
                                }
                            }
                        } else {
                            for (ComponentName componentName2 : list) {
                                Iterator it2 = arrayList.iterator();
                                while (true) {
                                    if (it2.hasNext()) {
                                        if (((EnterpriseDeviceAdminInfo) it2.next()).getComponent().equals(componentName2)) {
                                            z = true;
                                            break;
                                        }
                                    } else {
                                        z = false;
                                        break;
                                    }
                                }
                                if (!z && (findAdmin = enterpriseDeviceManagerServiceImpl.findAdmin(componentName2, userInfo.id)) != null) {
                                    Log.d("EnterpriseDeviceManagerService", "Adding missing admin to EDM : " + componentName2);
                                    int i4 = findAdmin.getActivityInfo().applicationInfo.uid;
                                    enterpriseDeviceManagerServiceImpl.mAdminMap.put(Integer.valueOf(i4), findAdmin);
                                    enterpriseDeviceManagerServiceImpl.mAdminList.add(findAdmin);
                                    enterpriseDeviceManagerServiceImpl.mEdmStorageProvider.addAdmin(i4, componentName2.flattenToString());
                                }
                            }
                        }
                    }
                }
            }
            enterpriseDeviceManagerServiceImpl.mInjector.getClass();
            Injector.invokeSystemReadyIfNeeded();
            try {
                startRemoteDesktopService();
            } catch (Error e8) {
                Log.e("EnterpriseDeviceManagerService", "Failed to start remote desktop service.", e8);
            } catch (Exception e9) {
                Log.e("EnterpriseDeviceManagerService", "Failed to start remote desktop service.", e9);
            }
            InternalHandler internalHandler2 = enterpriseDeviceManagerServiceImpl.mInternalHandler;
            if (internalHandler2 != null) {
                internalHandler2.post(new EnterpriseDeviceManagerServiceImpl$$ExternalSyntheticLambda0(enterpriseDeviceManagerServiceImpl, 2));
            }
        }
        RestrictionPolicy restrictionPolicy = (RestrictionPolicy) EnterpriseService.getPolicyService("restriction_policy");
        if (restrictionPolicy != null) {
            restrictionPolicy.systemReady(i);
        }
    }

    static {
        List list;
        String str;
        ArrayList arrayList = new ArrayList();
        EXCLUDED_ADMINS = arrayList;
        NON_MDM_ADMINS = new ArrayList();
        mIsFirmwareUpgrade = false;
        allowToSkipRuntimePermission = new String[]{"com.samsung.android.app.smartscan", "com.samsung.android.knox.dai", "com.samsung.android.knox.kpu", "com.sec.knox.kccagent", "com.samsung.android.knoxcts.test"};
        allowToUsingDirectPermissionCheckAPI = new String[]{"com.samsung.android.knox.kpecore", "com.samsung.android.peripheral.framework", "com.samsung.android.app.kfa"};
        IPackageManager packageManager = AppGlobals.getPackageManager();
        if (packageManager != null) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                } catch (Exception e) {
                    Log.e("EnterpriseDeviceManagerService", "Exception in isPackageInstalled()", e);
                }
                if (packageManager.getApplicationInfo("com.samsung.android.email.provider", 0L, 0) != null) {
                    Log.i("EnterpriseDeviceManagerService", "isPackageInstalled() : package present. application : com.samsung.android.email.provider");
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    arrayList.add("com.samsung.android.email.provider");
                    list = EXCLUDED_ADMINS;
                    list.add("com.sec.esdk.elm");
                    str = SystemProperties.get(ActivationMonitor.COUNTRY_CODE_PROPERTY, "");
                    if (str != null && !str.equalsIgnoreCase(ActivationMonitor.CHINA_COUNTRY_CODE)) {
                        list.add("com.samsung.android.kgclient");
                    }
                    List list2 = NON_MDM_ADMINS;
                    list2.add("com.samsung.android.email.provider");
                    list2.add("com.android.email");
                    list2.add("com.android.exchange");
                    list2.add("com.nttdocomo.android.wipe");
                    list2.add("com.nttdocomo.android.remotelock");
                    list2.add("com.sec.enterprise.knox.cloudmdm.smdms");
                    list2.add("com.samsung.android.kgclient");
                    mServiceAdditionCondition = new ConditionVariable();
                    MY_PID = Process.myPid();
                    ArrayList arrayList2 = new ArrayList();
                    CONTAINER_ALLOWED_DEVICE_PERMISSION_LIST = arrayList2;
                    arrayList2.add("com.samsung.android.knox.permission.KNOX_WIFI");
                    arrayList2.add("com.samsung.android.knox.permission.KNOX_SECURITY");
                    HashMap hashMap = new HashMap();
                    mKADelegationMapping = hashMap;
                    hashMap.put("delegation-cert-install", "dci");
                    hashMap.put("delegation-app-restrictions", "dar");
                    hashMap.put("delegation-block-uninstall", "dbu");
                    hashMap.put("delegation-permission-grant", "dpg");
                    hashMap.put("delegation-package-access", "dpa");
                    hashMap.put("delegation-enable-system-app", "desa");
                    hashMap.put("delegation-install-existing-package", "diep");
                    hashMap.put("delegation-keep-uninstalled-packages", "dkup");
                    hashMap.put("delegation-network-logging", "dnl");
                    hashMap.put("delegation-security-logging", "dsl");
                    hashMap.put("delegation-cert-selection", "dcs");
                }
                Log.i("EnterpriseDeviceManagerService", "isPackageInstalled() : package is not present. application : com.samsung.android.email.provider");
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        List list3 = EXCLUDED_ADMINS;
        list3.add("com.android.email");
        list3.add("com.android.exchange");
        list = EXCLUDED_ADMINS;
        list.add("com.sec.esdk.elm");
        str = SystemProperties.get(ActivationMonitor.COUNTRY_CODE_PROPERTY, "");
        if (str != null) {
            list.add("com.samsung.android.kgclient");
        }
        List list22 = NON_MDM_ADMINS;
        list22.add("com.samsung.android.email.provider");
        list22.add("com.android.email");
        list22.add("com.android.exchange");
        list22.add("com.nttdocomo.android.wipe");
        list22.add("com.nttdocomo.android.remotelock");
        list22.add("com.sec.enterprise.knox.cloudmdm.smdms");
        list22.add("com.samsung.android.kgclient");
        mServiceAdditionCondition = new ConditionVariable();
        MY_PID = Process.myPid();
        ArrayList arrayList22 = new ArrayList();
        CONTAINER_ALLOWED_DEVICE_PERMISSION_LIST = arrayList22;
        arrayList22.add("com.samsung.android.knox.permission.KNOX_WIFI");
        arrayList22.add("com.samsung.android.knox.permission.KNOX_SECURITY");
        HashMap hashMap2 = new HashMap();
        mKADelegationMapping = hashMap2;
        hashMap2.put("delegation-cert-install", "dci");
        hashMap2.put("delegation-app-restrictions", "dar");
        hashMap2.put("delegation-block-uninstall", "dbu");
        hashMap2.put("delegation-permission-grant", "dpg");
        hashMap2.put("delegation-package-access", "dpa");
        hashMap2.put("delegation-enable-system-app", "desa");
        hashMap2.put("delegation-install-existing-package", "diep");
        hashMap2.put("delegation-keep-uninstalled-packages", "dkup");
        hashMap2.put("delegation-network-logging", "dnl");
        hashMap2.put("delegation-security-logging", "dsl");
        hashMap2.put("delegation-cert-selection", "dcs");
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.server.enterprise.EnterpriseDeviceManagerServiceImpl$1] */
    public EnterpriseDeviceManagerServiceImpl(Context context) {
        SQLiteDatabase sQLiteDatabase;
        int i;
        Injector injector = new Injector(context);
        this.mKeyCodeMediator = new KeyCodeMediatorImpl();
        this.mCurrentUserId = -1;
        this.mLockDoNoUseDirectly = LockGuard.installNewLock(9, true);
        this.mDeferredServicesCreated = false;
        this.mContainerService = null;
        this.mAdminMap = new HashMap();
        this.mAdminList = new ArrayList();
        this.mPseudoAdminUid = -1;
        this.mReceiver = new BroadcastReceiver() { // from class: com.android.server.enterprise.EnterpriseDeviceManagerServiceImpl.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                if (action == null) {
                    Log.w("EnterpriseDeviceManagerService", "action is null!");
                    return;
                }
                if (!action.equals("android.intent.action.USER_REMOVED")) {
                    if (action.equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                        NetworkInfo networkInfo = (NetworkInfo) intent.getParcelableExtra("networkInfo");
                        if (networkInfo == null) {
                            Log.d("EnterpriseDeviceManagerService", "networkInfo is null");
                            return;
                        } else {
                            if (intent.getBooleanExtra("noConnectivity", false) || !networkInfo.isConnected()) {
                                return;
                            }
                            EnterpriseDeviceManagerServiceImpl.this.mContext.sendBroadcastAsUser(ExplicitHealthCheckController$$ExternalSyntheticOutline0.m("com.samsung.android.knox.intent.action.KES_TRIGGER", "com.sec.enterprise.knox.cloudmdm.smdms"), UserHandle.OWNER);
                            return;
                        }
                    }
                    if (action.equals(DevicePolicyListener.ACTION_DEVICE_OWNER_CHANGED) || action.equals(DevicePolicyListener.ACTION_PROFILE_OWNER_ADDED)) {
                        EnterpriseDeviceManagerServiceImpl enterpriseDeviceManagerServiceImpl = EnterpriseDeviceManagerServiceImpl.this;
                        List list = EnterpriseDeviceManagerServiceImpl.EXCLUDED_ADMINS;
                        if (enterpriseDeviceManagerServiceImpl.isDeviceOwnedByOrganization()) {
                            EnterpriseDeviceManagerServiceImpl.this.startDeferredServicesIfNeeded();
                            EnterpriseDeviceManagerServiceImpl.this.mContext.unregisterReceiver(this);
                            EnterpriseDeviceManagerServiceImpl.this.registerBroadcastReceiver();
                            return;
                        }
                        return;
                    }
                    return;
                }
                int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
                if (intExtra < 1) {
                    return;
                }
                EnterpriseDeviceManagerServiceImpl enterpriseDeviceManagerServiceImpl2 = EnterpriseDeviceManagerServiceImpl.this;
                List list2 = EnterpriseDeviceManagerServiceImpl.EXCLUDED_ADMINS;
                enterpriseDeviceManagerServiceImpl2.getClass();
                String[] strArr = {String.valueOf(intExtra)};
                enterpriseDeviceManagerServiceImpl2.mEdmStorageProvider.deleteDataByFields("generic", new String[]{"userID"}, strArr);
                EnterpriseDeviceManagerServiceImpl.this.mPersonaManagerAdapter.getClass();
                if (SemPersonaManager.isKnoxId(intExtra)) {
                    EnterpriseDeviceManagerServiceImpl.this.mPersonaManagerAdapter.getClass();
                    if (SemPersonaManager.isSecureFolderId(intExtra)) {
                        return;
                    }
                }
                Log.d("EnterpriseDeviceManagerService", "ACTION_USER_REMOVED removing pseudo admin since associated profile is getting removed.");
                EnterpriseDeviceManagerServiceImpl enterpriseDeviceManagerServiceImpl3 = EnterpriseDeviceManagerServiceImpl.this;
                int i2 = enterpriseDeviceManagerServiceImpl3.mPseudoAdminUid;
                if (i2 == -1) {
                    EdmStorageProvider edmStorageProvider = enterpriseDeviceManagerServiceImpl3.mEdmStorageProvider;
                    edmStorageProvider.getClass();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("isPseudoAdmin", Boolean.TRUE);
                    i2 = edmStorageProvider.getInt(contentValues, "ADMIN_INFO", "adminUid");
                }
                int i3 = i2;
                enterpriseDeviceManagerServiceImpl3.mPseudoAdminUid = i3;
                try {
                    if (i3 == -1) {
                        return;
                    }
                    try {
                        enterpriseDeviceManagerServiceImpl3.mEdmStorageProvider.putBoolean("ADMIN_INFO", i3, true, 0, "canRemove");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    enterpriseDeviceManagerServiceImpl3.mEdmStorageProvider.removeAdminFromDatabase(enterpriseDeviceManagerServiceImpl3.mPseudoAdminUid);
                    enterpriseDeviceManagerServiceImpl3.mPseudoAdminUid = -1;
                    enterpriseDeviceManagerServiceImpl3.mAdminMap.remove(-1);
                } catch (Exception e2) {
                    AudioServiceExt$ResetSettingsReceiver$$ExternalSyntheticOutline0.m(e2, new StringBuilder("removePseudoAdmin exception "), "EnterpriseDeviceManagerService");
                }
            }
        };
        this.mInjector = injector;
        mInstance = this;
        EnterpriseService.sEdmsInstance = this;
        this.mContext = context;
        this.mPMS = IPackageManager.Stub.asInterface(ServiceManager.getService("package"));
        this.mDPMS = IDevicePolicyManager.Stub.asInterface(ServiceManager.getService("device_policy"));
        registerAdaptors();
        Log.i("EnterpriseDeviceManagerService", "handleDowngrade: Checking Downgrade...");
        try {
            sQLiteDatabase = context.openOrCreateDatabase("enterprise.db", 0, null);
        } catch (Exception e) {
            Log.w("EnterpriseDeviceManagerService", "could not open or create databse" + e);
            sQLiteDatabase = null;
        }
        if (sQLiteDatabase != null) {
            int version = sQLiteDatabase.getVersion();
            sQLiteDatabase.close();
            if (version > 9) {
                Log.i("EnterpriseDeviceManagerService", "Current Platform Version is older than the previous DB version");
                Log.i("EnterpriseDeviceManagerService", "Deleting EDM Databases - enterprise.db and dmapprmgr.db");
                new File("/data/system/enterprise.db").delete();
                new File("/data/system/dmappmgr.db").delete();
            }
        }
        registerBroadcastReceiver();
        EdmStorageProvider edmStorageProvider = new EdmStorageProvider(injector.mContext);
        this.mEdmStorageProvider = edmStorageProvider;
        this.mInjector.getClass();
        String databaseUpgradeValue = mInstance.mEdmStorageProvider.getDatabaseUpgradeValue("PlatformSoftwareVersion");
        String str = SystemProperties.get("ro.build.fingerprint", "unknown");
        str = str.equalsIgnoreCase("unknown") ? null : str;
        if (databaseUpgradeValue == null || (str != null && !str.equals(databaseUpgradeValue))) {
            Log.i("EnterpriseDeviceManagerService", "handleUpgrade: Checking Upgrade...");
            mIsFirmwareUpgrade = true;
            try {
                EdmStorageHelper edmStorageHelper = edmStorageProvider.mEdmDbHelper;
                edmStorageHelper.doTablesCreationOrUpdate(edmStorageHelper.getWritableDatabase());
            } catch (Exception e2) {
                Log.e("EdmStorageProvider", "handleUpgrade EX:", e2);
            }
            String str2 = SystemProperties.get("ro.build.fingerprint", "unknown");
            if (!str2.equals("unknown")) {
                EdmStorageProvider edmStorageProvider2 = this.mEdmStorageProvider;
                edmStorageProvider2.getClass();
                ContentValues contentValues = new ContentValues();
                contentValues.put("name", "PlatformSoftwareVersion");
                ContentValues contentValues2 = new ContentValues();
                contentValues2.put("value", str2);
                edmStorageProvider2.put("generic", contentValues2, contentValues);
            }
        }
        int i2 = Build.VERSION.SDK_INT;
        try {
            i = Integer.valueOf(this.mEdmStorageProvider.getDatabaseUpgradeValue("PlatformSdkApiLevel")).intValue();
        } catch (NumberFormatException unused) {
            Log.d("EnterpriseDeviceManagerService", "No written value");
            i = 30;
        }
        if (i < i2) {
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i2, "Android API Level is just changed to ", "EnterpriseDeviceManagerService");
            EdmStorageProvider edmStorageProvider3 = this.mEdmStorageProvider;
            String valueOf = String.valueOf(Build.VERSION.SDK_INT);
            edmStorageProvider3.getClass();
            ContentValues contentValues3 = new ContentValues();
            contentValues3.put("name", "PlatformSdkApiLevel");
            ContentValues contentValues4 = new ContentValues();
            contentValues4.put("value", valueOf);
            edmStorageProvider3.put("generic", contentValues4, contentValues3);
        } else {
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i2, "Android API Level is ", "EnterpriseDeviceManagerService");
        }
        Injector.addLazySystemService(new EnterpriseLicenseService(injector.mContext, IPackageManager.Stub.asInterface(ServiceManager.getService("package"))), "enterprise_license_policy");
        Injector.addSystemService(new ApplicationPolicy(injector.mContext), "application_policy");
        Context context2 = this.mContext;
        ProfilePolicyService profilePolicyService = new ProfilePolicyService();
        profilePolicyService.mEdmStorageProvider = null;
        profilePolicyService.mDevicePolicyManager = null;
        profilePolicyService.mContext = context2;
        profilePolicyService.mEdmStorageProvider = new EdmStorageProvider(context2);
        profilePolicyService.mDevicePolicyManager = (DevicePolicyManager) context2.getSystemService("device_policy");
        Injector.addSystemService(profilePolicyService, "profilepolicy");
        EnterpriseService.addPolicyService("wifi_policy", new WifiPolicy(injector.mContext));
        EnterpriseService.addPolicyService("phone_restriction_policy", new PhoneRestrictionPolicy(injector.mContext));
        Injector.addLazySystemService(new RemoteInjectionService(injector.mContext), "remoteinjection");
        Injector.addSystemService(new RestrictionPolicy(injector.mContext), "restriction_policy");
        Injector.addLazySystemService(new PasswordPolicy(injector.mContext), "password_policy");
        ServiceManager.addService("edm_proxy", new EDMProxyService(this.mContext));
        Injector.addLazySystemService(new HdmService(injector.mContext), "hdm_service");
        Context context3 = this.mContext;
        int i3 = ApplicationRestrictionsService.$r8$clinit;
        LocalServices.addService(ApplicationRestrictionsInternal.class, new ApplicationRestrictionsService(context3));
        Context context4 = this.mContext;
        int i4 = CloudConfigurationManagerService.$r8$clinit;
        LocalServices.addService(CloudConfigurationManagerInternal.class, new CloudConfigurationManagerService(context4));
        setMediators();
    }

    public static boolean checkAdminExistsInELMDB(String str) {
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

    public static boolean checkCallerIsKPECore(int i, String str) {
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

    public static void enforceCallingOrSelfPermissions(Context context, List list) {
        if (EnterprisePermissionChecker.sInstance == null) {
            EnterprisePermissionChecker enterprisePermissionChecker = new EnterprisePermissionChecker();
            enterprisePermissionChecker.mContext = context;
            EnterprisePermissionChecker.sInstance = enterprisePermissionChecker;
        }
        EnterprisePermissionChecker enterprisePermissionChecker2 = EnterprisePermissionChecker.sInstance;
        enterprisePermissionChecker2.getClass();
        ArrayList arrayList = (ArrayList) list;
        if (arrayList.isEmpty()) {
            Log.e("EnterprisePermissionChecker", "no permission provided");
            return;
        }
        try {
            enterprisePermissionChecker2.mContext.enforceCallingOrSelfPermission((String) arrayList.get(0), null);
        } catch (SecurityException e) {
            if (arrayList.size() == 1) {
                throw e;
            }
            try {
                enterprisePermissionChecker2.mContext.enforceCallingOrSelfPermission((String) arrayList.get(1), null);
            } catch (SecurityException e2) {
                String message = e2.getMessage();
                if (message != null) {
                    StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(message, ",");
                    m.append((String) arrayList.get(0));
                    message = m.toString();
                }
                throw new SecurityException(message);
            }
        }
    }

    public static EnterpriseDeviceManagerServiceImpl getInstance() {
        return mInstance;
    }

    public static boolean hasKnoxInternalExceptionPermission(int i, String str) {
        try {
            return AppGlobals.getPackageManager().checkPermission("com.samsung.android.knox.permission.KNOX_INTERNAL_EXCEPTION", str, i) == 0;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void registerAdaptors() {
        StorageManagerAdapter storageManagerAdapter;
        WindowManagerAdapter windowManagerAdapter;
        PersonaManagerAdapter personaManagerAdapter;
        this.mSystemUIAdapter = SystemUIAdapter.getInstance(this.mInjector.mContext);
        mPackageManagerAdapter = PackageManagerAdapter.getInstance(this.mInjector.mContext);
        Context context = this.mInjector.mContext;
        synchronized (StorageManagerAdapter.class) {
            try {
                if (StorageManagerAdapter.mInstance == null) {
                    StorageManagerAdapter.mInstance = new StorageManagerAdapter();
                    StorageManagerAdapter.mStorageManager = (StorageManager) context.getSystemService("storage");
                }
                storageManagerAdapter = StorageManagerAdapter.mInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        this.mStorageManagerAdapter = storageManagerAdapter;
        this.mInjector.getClass();
        WindowManagerAdapter windowManagerAdapter2 = WindowManagerAdapter.sInstance;
        synchronized (WindowManagerAdapter.class) {
            try {
                if (WindowManagerAdapter.sInstance == null) {
                    WindowManagerAdapter.sInstance = new WindowManagerAdapter();
                }
                windowManagerAdapter = WindowManagerAdapter.sInstance;
            } catch (Throwable th2) {
                throw th2;
            }
        }
        this.mWindowManagerAdapter = windowManagerAdapter;
        Context context2 = this.mInjector.mContext;
        synchronized (PersonaManagerAdapter.class) {
            try {
                if (PersonaManagerAdapter.sInstance == null && context2 != null) {
                    PersonaManagerAdapter personaManagerAdapter2 = new PersonaManagerAdapter();
                    personaManagerAdapter2.mContext = context2;
                    PersonaManagerAdapter.sInstance = personaManagerAdapter2;
                }
                personaManagerAdapter = PersonaManagerAdapter.sInstance;
            } catch (Throwable th3) {
                throw th3;
            }
        }
        this.mPersonaManagerAdapter = personaManagerAdapter;
        AdapterRegistry.mAdapterHandles.put(ISystemUIAdapter.class, this.mSystemUIAdapter);
        AdapterRegistry.mAdapterHandles.put(IPackageManagerAdapter.class, mPackageManagerAdapter);
        AdapterRegistry.mAdapterHandles.put(IStorageManagerAdapter.class, this.mStorageManagerAdapter);
        AdapterRegistry.mAdapterHandles.put(IWindowManagerAdapter.class, this.mWindowManagerAdapter);
        AdapterRegistry.mAdapterHandles.put(IPersonaManagerAdapter.class, this.mPersonaManagerAdapter);
    }

    public static void setDelegatorAdminUid(ContextInfo contextInfo, int i) {
        try {
            Field declaredField = contextInfo.getClass().getDeclaredField("mCallerUid");
            declaredField.setAccessible(true);
            declaredField.setInt(contextInfo, i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static native void startRemoteDesktopService();

    public final void activateAdmin(ComponentName componentName, boolean z) {
        boolean z2;
        boolean z3;
        int i;
        String str;
        this.mInjector.getClass();
        int callingUserId = UserHandle.getCallingUserId();
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(callingUserId, "Activating admin on user!!!!!! ", "EnterpriseDeviceManagerService");
        EnterpriseDeviceAdminInfo findAdmin = findAdmin(componentName, callingUserId);
        if (findAdmin == null) {
            throw new IllegalArgumentException(AmbientContextManagerPerUserService$$ExternalSyntheticOutline0.m(componentName, "Bad admin: "));
        }
        RestrictionPolicy restrictionPolicy = (RestrictionPolicy) EnterpriseService.getPolicyService("restriction_policy");
        if (restrictionPolicy != null && !restrictionPolicy.checkAdminActivationEnabled(callingUserId, componentName.getPackageName())) {
            throw new IllegalArgumentException("Admin cannot be activated");
        }
        if ("com.samsung.android.kgclient".equals(componentName.getPackageName())) {
            int callingUid = Binder.getCallingUid();
            try {
                PackageManager packageManager = this.mContext.getPackageManager();
                this.mInjector.getClass();
                i = packageManager.getPackageUidAsUser("com.samsung.android.kgclient", UserHandle.getCallingUserId());
            } catch (Exception e) {
                System.out.println(e);
                i = -1;
            }
            if ((callingUid == i || Binder.getCallingUid() == 1000) && (str = SystemProperties.get(ActivationMonitor.COUNTRY_CODE_PROPERTY, "")) != null && !str.equalsIgnoreCase(ActivationMonitor.CHINA_COUNTRY_CODE)) {
                ArrayList arrayList = (ArrayList) EXCLUDED_ADMINS;
                if (arrayList.contains("com.samsung.android.kgclient")) {
                    arrayList.remove("com.samsung.android.kgclient");
                    Log.d("EnterpriseDeviceManagerService", "adjustKnoxGuardAdmin kgclient is removed from EXCLUDED_ADMINS by " + Binder.getCallingUid());
                }
            }
        }
        if (findAdmin.usesMDMPolicy()) {
            if (!((ArrayList) EXCLUDED_ADMINS).contains(componentName.getPackageName())) {
                startDeferredServicesIfNeeded();
            }
        }
        int i2 = findAdmin.getActivityInfo().applicationInfo.uid;
        ComponentName component = findAdmin.getComponent();
        Log.d("EnterpriseDeviceManagerService", "Admin uid: " + i2 + ", Component name: " + component);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                synchronized (getLockObject()) {
                    if (!z) {
                        try {
                            if (getActiveAdminLocked(UserHandle.getUserId(i2), component) != null) {
                                throw new IllegalArgumentException("Admin is already added");
                            }
                        } finally {
                        }
                    }
                    if (z) {
                        z2 = true;
                    } else {
                        Log.d("EnterpriseDeviceManagerService", "Adding admin " + i2 + " to lists");
                        this.mAdminMap.put(Integer.valueOf(i2), findAdmin);
                        this.mAdminList.add(findAdmin);
                        z2 = this.mEdmStorageProvider.addorUpdateAdmin(i2, component.flattenToString(), "com.samsung.android.kgclient".equals(componentName.getPackageName()) ^ true);
                    }
                    if (!z2) {
                        Log.d("EnterpriseDeviceManagerService", "Removing admin " + i2 + " from lists");
                        this.mAdminList.remove(findAdmin);
                        this.mAdminMap.remove(Integer.valueOf(i2));
                        throw new IllegalArgumentException("Unable to activate admin");
                    }
                    Iterator it = ((TreeMap) EnterpriseService.getPolicyServices()).entrySet().iterator();
                    while (it.hasNext()) {
                        ((EnterpriseServiceCallback) ((Map.Entry) it.next()).getValue()).onAdminAdded(i2);
                    }
                }
                int i3 = 1;
                while (true) {
                    if (i3 > 2) {
                        z3 = false;
                        break;
                    }
                    Log.d("EnterpriseDeviceManagerService", "Attempt " + i3 + " to active admin in DPM");
                    this.mDPMS.setActiveAdmin(componentName, z, callingUserId);
                    if (this.mDPMS.isAdminActive(componentName, callingUserId)) {
                        Log.d("EnterpriseDeviceManagerService", "Admin sucessfully activated in DPM for user " + callingUserId);
                        z3 = true;
                        break;
                    }
                    Log.d("EnterpriseDeviceManagerService", "Admin activation failed for user " + callingUserId);
                    i3++;
                }
                Log.d("EnterpriseDeviceManagerService", "EDM setActiveAdmin activationStatus -" + z3 + " for user - " + callingUserId);
            } catch (RemoteException e2) {
                e2.printStackTrace();
            }
            if (z3) {
                Log.d("EnterpriseDeviceManagerService", "Admin added to DPM!");
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return;
            }
            Log.d("EnterpriseDeviceManagerService", "Started removing admin information...");
            setAdminRemovable(new ContextInfo(i2), true, null);
            removeActiveAdminDelayed(callingUserId, componentName, false);
            throw new IllegalArgumentException("EDM - Admin activation failed for user -" + callingUserId);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final boolean activateDevicePermissions(List list) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("com.samsung.android.knox.permission.KNOX_ACTIVATE_DEVICE_PERMISSIONS_INTERNAL");
        enforceActiveAdminPermissionByContext(null, arrayList);
        int callingUid = Binder.getCallingUid();
        int appId = UserHandle.getAppId(callingUid);
        if (this.mAdminMap.containsKey(Integer.valueOf(appId))) {
            throw new SecurityException("Admin already present and active");
        }
        EnterpriseDeviceAdminInfo enterpriseDeviceAdminInfo = (EnterpriseDeviceAdminInfo) this.mAdminMap.get(Integer.valueOf(callingUid));
        if (enterpriseDeviceAdminInfo == null) {
            throw new SecurityException("No active admin");
        }
        ComponentName component = enterpriseDeviceAdminInfo.getComponent();
        if (component == null) {
            ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(callingUid, "failed due to abnormal admin information: ", "EnterpriseDeviceManagerService");
            return false;
        }
        String packageName = component.getPackageName();
        PackageManagerAdapter packageManagerAdapter = mPackageManagerAdapter;
        int userId = UserHandle.getUserId(appId);
        packageManagerAdapter.getClass();
        if (PackageManagerAdapter.isApplicationInstalled(userId, packageName)) {
            Log.e("EnterpriseDeviceManagerService", "failed due to application is installed in device as user:" + UserHandle.getUserId(appId));
            return false;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            if (!((ArrayList) CONTAINER_ALLOWED_DEVICE_PERMISSION_LIST).contains(str)) {
                StorageManagerService$$ExternalSyntheticOutline0.m("This permission cannot be set on device level from the container: ", str, "EnterpriseDeviceManagerService");
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

    public final boolean addAuthorizedUid(int i, int i2) {
        Log.d("EnterpriseDeviceManagerService", "addAuthorizedUid");
        if (!checkCallerIsUMC()) {
            return false;
        }
        enforceUMCSignature();
        ContentValues contentValues = new ContentValues();
        Pageboost$PageboostFileDBHelper$$ExternalSyntheticOutline0.m(i2, contentValues, "adminUid", i, "authorizedUid");
        return this.mEdmStorageProvider.putValuesNoUpdate("ADMIN_UID_AUTHORIZATION_INFO", contentValues);
    }

    public final int addPseudoAdminForParent(int i) {
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
            if (componentNameForUid != null ? this.mEdmStorageProvider.addorUpdateAdminWithPseudo(appId, componentNameForUid.flattenToString()) : false) {
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

    public final byte[] captureUmcLogs(ContextInfo contextInfo, String str, List list) {
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

    public final boolean checkCallerIsUMC() {
        int i;
        int callingUid = Binder.getCallingUid();
        try {
            PackageManager packageManager = this.mContext.getPackageManager();
            this.mInjector.getClass();
            i = packageManager.getPackageUidAsUser("com.sec.enterprise.knox.cloudmdm.smdms", UserHandle.getCallingUserId());
        } catch (Exception e) {
            System.out.println(e);
            i = -1;
        }
        return callingUid == i;
    }

    public final void checkContainerOwnerShipForUMC(ContextInfo contextInfo) {
        if (contextInfo.mParent) {
            return;
        }
        int userId = UserHandle.getUserId(contextInfo.mCallerUid);
        this.mPersonaManagerAdapter.getClass();
        if (!SemPersonaManager.isKnoxId(userId)) {
            PersonaManagerAdapter personaManagerAdapter = this.mPersonaManagerAdapter;
            int i = contextInfo.mContainerId;
            personaManagerAdapter.getClass();
            if (!SemPersonaManager.isKnoxId(i)) {
                return;
            }
        }
        PersonaManagerAdapter personaManagerAdapter2 = this.mPersonaManagerAdapter;
        int i2 = contextInfo.mContainerId;
        personaManagerAdapter2.getClass();
        if (SemPersonaManager.isKnoxId(i2)) {
            userId = contextInfo.mContainerId;
        }
        if (this.mEdmStorageProvider.getMUMContainerOwnerUid(userId) == contextInfo.mCallerUid) {
            return;
        }
        StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(userId, "Admin doesn't own container id ", " ContextInfo.uid ");
        m.append(contextInfo.mCallerUid);
        throw new SecurityException(m.toString());
    }

    public final boolean checkProxyAdminPermission(ContextInfo contextInfo, List list) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("proxyUid", Integer.valueOf(contextInfo.mCallerUid));
        ArrayList arrayList = (ArrayList) this.mEdmStorageProvider.getValues("PROXY_ADMIN_INFO", new String[]{"permissions"}, contentValues);
        if (arrayList.isEmpty()) {
            try {
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    if (AppGlobals.getPackageManager().checkUidPermission((String) it.next(), contextInfo.mCallerUid) == 0) {
                        return true;
                    }
                }
            } catch (Exception unused) {
                Log.w("EnterpriseDeviceManagerService", "could not check calling permission");
            }
        } else {
            String asString = ((ContentValues) arrayList.get(0)).getAsString("permissions");
            if (asString != null && !asString.isEmpty()) {
                HashSet hashSet = new HashSet(Arrays.asList(asString.split(";")));
                Iterator it2 = list.iterator();
                while (it2.hasNext()) {
                    if (hashSet.contains((String) it2.next())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public final void createDeferredServices() {
        if (this.mDeferredServicesCreated) {
            Log.d("EnterpriseDeviceManagerService", "createDeferredServices() : Skip to create");
            return;
        }
        Log.d("EnterpriseDeviceManagerService", "createDeferredServices() : Adding services ... ");
        try {
            Iterator it = ((ArrayList) EnterpriseService.sLazyServiceList).iterator();
            while (it.hasNext()) {
                String str = (String) it.next();
                ServiceManager.getService(str);
                EnterpriseServiceCallback enterpriseServiceCallback = (EnterpriseServiceCallback) EnterpriseService.getPolicyService(str);
                enterpriseServiceCallback.notifyToAddSystemService(str, (IBinder) enterpriseServiceCallback);
            }
            Injector injector = this.mInjector;
            EnterpriseServiceCallback enterpriseServiceCallback2 = (EnterpriseServiceCallback) EnterpriseService.getPolicyService("wifi_policy");
            injector.getClass();
            Injector.addSystemService(enterpriseServiceCallback2, "wifi_policy");
            Injector injector2 = this.mInjector;
            EnterpriseServiceCallback enterpriseServiceCallback3 = (EnterpriseServiceCallback) EnterpriseService.getPolicyService("phone_restriction_policy");
            injector2.getClass();
            Injector.addSystemService(enterpriseServiceCallback3, "phone_restriction_policy");
            Injector injector3 = this.mInjector;
            DeviceInfo deviceInfo = new DeviceInfo(this.mContext);
            injector3.getClass();
            Injector.addSystemService(deviceInfo, "device_info");
            Injector injector4 = this.mInjector;
            LicenseLogService licenseLogService = new LicenseLogService(this.mContext);
            injector4.getClass();
            Injector.addSystemService(licenseLogService, "license_log_service");
            Injector injector5 = this.mInjector;
            AuditLogService auditLogService = new AuditLogService(this.mContext);
            injector5.getClass();
            Injector.addSystemService(auditLogService, "auditlog");
            Injector injector6 = this.mInjector;
            ExchangeAccountPolicy exchangeAccountPolicy = new ExchangeAccountPolicy(this.mContext);
            injector6.getClass();
            Injector.addSystemService(exchangeAccountPolicy, "eas_account_policy");
            Injector injector7 = this.mInjector;
            Context context = this.mContext;
            EmailAccountPolicy emailAccountPolicy = new EmailAccountPolicy();
            emailAccountPolicy.mEDM = null;
            emailAccountPolicy.preCallingUid = -1;
            emailAccountPolicy.mEdmStorageProvider = null;
            emailAccountPolicy.mContext = context;
            emailAccountPolicy.mEdmStorageProvider = new EdmStorageProvider(context);
            injector7.getClass();
            Injector.addSystemService(emailAccountPolicy, "email_account_policy");
            Injector injector8 = this.mInjector;
            LocationPolicy locationPolicy = new LocationPolicy(this.mContext);
            injector8.getClass();
            Injector.addSystemService(locationPolicy, "location_policy");
            Injector injector9 = this.mInjector;
            MiscPolicy miscPolicy = new MiscPolicy(this.mContext);
            injector9.getClass();
            Injector.addSystemService(miscPolicy, "misc_policy");
            Injector injector10 = this.mInjector;
            Context context2 = this.mContext;
            VpnInfoPolicy vpnInfoPolicy = new VpnInfoPolicy();
            vpnInfoPolicy.mEDM = null;
            vpnInfoPolicy.mEDMStorageProvider = null;
            vpnInfoPolicy.mVpnManager = null;
            vpnInfoPolicy.mContext = context2;
            vpnInfoPolicy.mEDMStorageProvider = new EdmStorageProvider(context2);
            VpnInfoPolicy.retrieveVpnListFromStorage().size();
            injector10.getClass();
            Injector.addLazySystemService(vpnInfoPolicy, "vpn_policy");
            Injector injector11 = this.mInjector;
            SecurityPolicy securityPolicy = new SecurityPolicy(this.mContext);
            injector11.getClass();
            Injector.addSystemService(securityPolicy, "security_policy");
            Injector injector12 = this.mInjector;
            RoamingPolicy roamingPolicy = new RoamingPolicy(this.mContext);
            injector12.getClass();
            Injector.addSystemService(roamingPolicy, "roaming_policy");
            Injector injector13 = this.mInjector;
            BluetoothPolicy bluetoothPolicy = new BluetoothPolicy(this.mContext);
            injector13.getClass();
            Injector.addSystemService(bluetoothPolicy, "bluetooth_policy");
            Injector injector14 = this.mInjector;
            EmailPolicy emailPolicy = new EmailPolicy(this.mContext);
            injector14.getClass();
            Injector.addSystemService(emailPolicy, "email_policy");
            Injector injector15 = this.mInjector;
            Firewall firewall = new Firewall(this.mContext);
            injector15.getClass();
            Injector.addSystemService(firewall, "firewall");
            Injector injector16 = this.mInjector;
            CertificatePolicy certificatePolicy = new CertificatePolicy(this.mContext);
            injector16.getClass();
            Injector.addSystemService(certificatePolicy, "certificate_policy");
            Injector injector17 = this.mInjector;
            Context context3 = this.mContext;
            ApnSettingsPolicy apnSettingsPolicy = new ApnSettingsPolicy();
            apnSettingsPolicy.mEDM = null;
            apnSettingsPolicy.mMDMConfigVersion = KnoxInternalFeature.KNOX_CONFIG_MDM_VERSION;
            apnSettingsPolicy.mPendingGetApnList = new HashMap();
            apnSettingsPolicy.mContext = context3;
            injector17.getClass();
            Injector.addSystemService(apnSettingsPolicy, "apn_settings_policy");
            Injector injector18 = this.mInjector;
            BrowserPolicy browserPolicy = new BrowserPolicy(this.mContext);
            injector18.getClass();
            Injector.addSystemService(browserPolicy, "browser_policy");
            Injector injector19 = this.mInjector;
            DateTimePolicy dateTimePolicy = new DateTimePolicy(this.mContext);
            injector19.getClass();
            Injector.addSystemService(dateTimePolicy, "date_time_policy");
            Injector injector20 = this.mInjector;
            KioskModeService kioskModeService = new KioskModeService(this.mContext);
            injector20.getClass();
            Injector.addSystemService(kioskModeService, "kioskmode");
            Injector injector21 = this.mInjector;
            LDAPAccountPolicy lDAPAccountPolicy = new LDAPAccountPolicy(this.mContext);
            injector21.getClass();
            Injector.addSystemService(lDAPAccountPolicy, "ldap_account_policy");
            Injector injector22 = this.mInjector;
            LSOService lSOService = new LSOService(this.mContext);
            injector22.getClass();
            Injector.addSystemService(lSOService, "lockscreen_overlay");
            Injector injector23 = this.mInjector;
            GeofenceService geofenceService = new GeofenceService(this.mContext);
            injector23.getClass();
            Injector.addSystemService(geofenceService, "geofencing");
            Injector injector24 = this.mInjector;
            Context context4 = this.mContext;
            DeviceAccountPolicy deviceAccountPolicy = new DeviceAccountPolicy();
            deviceAccountPolicy.mEDM = null;
            deviceAccountPolicy.mAuditLogService = null;
            deviceAccountPolicy.mContext = context4;
            deviceAccountPolicy.mEdmStorageProvider = new EdmStorageProvider(context4);
            injector24.getClass();
            Injector.addSystemService(deviceAccountPolicy, "device_account_policy");
            Injector injector25 = this.mInjector;
            Context context5 = this.mContext;
            MultiUserManagerService multiUserManagerService = new MultiUserManagerService();
            multiUserManagerService.mEDM = null;
            multiUserManagerService.mContext = context5;
            multiUserManagerService.mEdmStorageProvider = new EdmStorageProvider(context5);
            multiUserManagerService.mUserManager = (UserManager) context5.getSystemService("user");
            injector25.getClass();
            Injector.addSystemService(multiUserManagerService, "multi_user_manager_service");
            Injector injector26 = this.mInjector;
            DexPolicy dexPolicy = new DexPolicy(this.mContext);
            injector26.getClass();
            Injector.addSystemService(dexPolicy, "dex_policy");
            if (EnterpriseService.getPolicyService("knox_adapter_service") == null) {
                Injector injector27 = this.mInjector;
                DualDARComnService dualDARComnService = new DualDARComnService(this.mContext);
                injector27.getClass();
                Injector.addSystemService(dualDARComnService, "knox_adapter_service");
                Injector injector28 = this.mInjector;
                DualDARPolicy dualDARPolicy = new DualDARPolicy(this.mContext);
                injector28.getClass();
                Injector.addSystemService(dualDARPolicy, "DualDARPolicy");
            }
            EnterpriseKnoxManager.createInstance(new ContextInfo());
            EdmConstants.EnterpriseKnoxSdkVersion enterpriseKnoxSdkVersion = EdmConstants.getEnterpriseKnoxSdkVersion();
            if (enterpriseKnoxSdkVersion.ordinal() >= 24) {
                Log.d("EnterpriseDeviceManagerService", "ThreatDefenseService - knoxSdkVersion : " + enterpriseKnoxSdkVersion.ordinal());
                Injector injector29 = this.mInjector;
                ThreatDefenseService threatDefenseService = new ThreatDefenseService(this.mContext);
                injector29.getClass();
                Injector.addSystemService(threatDefenseService, "threat_defense_service");
            }
            Injector injector30 = this.mInjector;
            NetworkAnalyticsService networkAnalyticsService = new NetworkAnalyticsService(this.mContext);
            injector30.getClass();
            Injector.addLazySystemService(networkAnalyticsService, "knoxnap");
            Log.d("EnterpriseDeviceManagerService", "knox ztna service started");
            Injector injector31 = this.mInjector;
            KnoxNetworkFilterService knoxNetworkFilterService = new KnoxNetworkFilterService(this.mContext);
            injector31.getClass();
            Injector.addLazySystemService(knoxNetworkFilterService, "knox_nwFilterMgr_policy");
            ProcessLifecycleManager processLifecycleManager = ProcessLifecycleManager.getInstance(this.mContext);
            StartReason startReason = StartReason.EDM_SERVICE_READY;
            ProcessStateTracker processStateTracker = processLifecycleManager.mStateTracker;
            processStateTracker.getClass();
            processStateTracker.sendMessage(PlmMessage.obtain(processStateTracker, 1, startReason));
        } catch (Throwable th) {
            Log.e("EnterpriseDeviceManagerService", "addServices() : Failure creating Policy services" + th);
            th.printStackTrace();
        }
        this.mDeferredServicesCreated = true;
        setMediators();
        this.mInjector.getClass();
        Injector.invokeSystemReadyIfNeeded();
        if (this.mMonitor == null) {
            MyPackageMonitor myPackageMonitor = new MyPackageMonitor();
            this.mMonitor = myPackageMonitor;
            myPackageMonitor.register(this.mContext, (Looper) null, new UserHandle(-1), true);
        }
        for (Map.Entry entry : ((TreeMap) EnterpriseService.getPolicyServices()).entrySet()) {
            if (((EnterpriseServiceCallback) entry.getValue()).hasDeferredBroadcastReceiverToRegister()) {
                ((EnterpriseServiceCallback) entry.getValue()).registerDeferredBoradcastReceiver();
            }
        }
    }

    public final boolean disableConstrainedState(ContextInfo contextInfo) {
        return this.mConstrainedState.disableConstrainedState(enforceOwnerOnlyAndActiveAdminPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_ADVANCED_RESTRICTION"))).mCallerUid);
    }

    /* JADX WARN: Type inference failed for: r8v37, types: [boolean, int] */
    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        String str;
        if (this.mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
            printWriter.println("Permission Denial: can't dump Enterprise Device Manager Service");
            return;
        }
        printWriter.write("EnterpriseDeviceManagerService Knox Info:" + System.lineSeparator());
        StringBuilder sb = new StringBuilder();
        try {
            this.mPersonaManagerAdapter.getClass();
            String knoxContainerVersion = SemPersonaManager.getKnoxContainerVersion().toString();
            if (knoxContainerVersion != null && !knoxContainerVersion.isEmpty()) {
                int parseInt = Integer.parseInt(Character.toString(knoxContainerVersion.charAt(knoxContainerVersion.length() - 1)));
                sb.append("Knox ");
                if (parseInt > 0) {
                    sb.append(knoxContainerVersion);
                } else {
                    sb.append(knoxContainerVersion.substring(0, knoxContainerVersion.lastIndexOf(46)));
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
            this.mPersonaManagerAdapter.getClass();
            String knoxContainerVersion2 = SemPersonaManager.getKnoxContainerVersion().toString();
            if (knoxContainerVersion2 != null && !knoxContainerVersion2.isEmpty()) {
                sb.append("Container  ");
                sb.append(knoxContainerVersion2);
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
                    this.mPersonaManagerAdapter.getClass();
                    String str5 = SemPersonaManager.isKnoxVersionSupported(FrameworkStatsLog.CAMERA_SHOT_LATENCY_REPORTED__MODE__CONTROL_DS_MODE_AI_CLEAR_ZOOM_MERGE_ZSL_ANCHOR_6) ? "3.3" : "3.2";
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
        printWriter.write(sb.toString());
        printWriter.write("EnterpriseDeviceManagerService SystemUIAdapter Info:" + System.lineSeparator());
        if (this.mCurrentUserId != SystemUIAdapter.getInstance(this.mContext).adapterUserId) {
            printWriter.write("mCurrentUserId : " + this.mCurrentUserId + System.lineSeparator());
        }
        printWriter.write("SystemUIAdapter adapterId : " + SystemUIAdapter.getInstance(this.mContext).adapterUserId + System.lineSeparator());
        printWriter.write("SystemUIAdapter registered count : " + SystemUIAdapter.getInstance(this.mContext).mRegisteredCount + System.lineSeparator());
        SystemUIAdapter systemUIAdapter = SystemUIAdapter.getInstance(this.mContext);
        if (systemUIAdapter.isCallbackDied || systemUIAdapter.mCallbacks.size() <= 0) {
            printWriter.write("SystemUIAdapter is not registed. " + System.lineSeparator());
        }
        printWriter.write("ConstrainedMode : " + this.mConstrainedState.checkConstrainedState() + System.lineSeparator());
        if (this.mConstrainedState.checkConstrainedState()) {
            this.mConstrainedState.dump(printWriter);
        }
    }

    public final boolean enableConstrainedState(ContextInfo contextInfo, String str, String str2, String str3, String str4, int i) {
        return this.mConstrainedState.enableConstrainedState(enforceOwnerOnlyAndActiveAdminPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_ADVANCED_RESTRICTION"))).mCallerUid, str, str2, str3, str4, i);
    }

    public final boolean enableWipe(ContextInfo contextInfo) {
        PersistentDataBlockManagerInternal persistentDataBlockManagerInternal;
        ArrayList arrayList = new ArrayList();
        arrayList.add(KESListener.KME_BROADCAST_PERMISSION);
        enforcePermissionByContext(contextInfo, arrayList);
        if (!checkCallerIsUMC()) {
            return false;
        }
        try {
            persistentDataBlockManagerInternal = (PersistentDataBlockManagerInternal) LocalServices.getService(PersistentDataBlockManagerInternal.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (persistentDataBlockManagerInternal == null) {
            Log.d("EnterpriseDeviceManagerService", "Failed to get PersistentDataBlockManagerInternal");
            return false;
        }
        EnterpriseLicenseManager.log(contextInfo, "EnterpriseDeviceManagerService.enableWipe");
        PersistentDataBlockService.InternalService internalService = (PersistentDataBlockService.InternalService) persistentDataBlockManagerInternal;
        synchronized (PersistentDataBlockService.this.mLock) {
            PersistentDataBlockService persistentDataBlockService = PersistentDataBlockService.this;
            persistentDataBlockService.mFrpActive = false;
            persistentDataBlockService.setOldSettingForBackworkCompatibility(false);
        }
        return true;
    }

    public final void enforceActiveAdminPermission(List list) {
        enforcePermissionByContext(new ContextInfo(Binder.getCallingUid()), list, false, true, false, false);
    }

    public final ContextInfo enforceActiveAdminPermissionByContext(ContextInfo contextInfo, List list) {
        return enforcePermissionByContext(contextInfo, list, true, true, false, false);
    }

    public final void enforceCaller(String str) {
        EnterpriseAccessController.enforceCaller(null, str);
    }

    public final boolean enforceCallerPermission(int i, List list) {
        try {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                if (this.mContext.checkPermission((String) it.next(), -1, i) == 0) {
                    return true;
                }
            }
            return false;
        } catch (Exception unused) {
            Log.w("EnterpriseDeviceManagerService", "Could not check calling permission");
            return false;
        }
    }

    public final void enforceComponentCheck(ContextInfo contextInfo, ComponentName componentName) {
        ComponentName activeAdminComponent;
        if (componentName != null) {
            int callingUid = Binder.getCallingUid();
            if (UserHandle.getAppId(callingUid) == 1000 || contextInfo.mCallerUid != callingUid || (activeAdminComponent = getActiveAdminComponent()) == null || activeAdminComponent.equals(componentName)) {
                return;
            }
            throw new SecurityException("Component name violation " + componentName.flattenToString());
        }
    }

    public final ContextInfo enforceContainerOwnerShipPermissionByContext(ContextInfo contextInfo, List list) {
        return enforcePermissionByContext(contextInfo, list, true, false, false, false);
    }

    public final ContextInfo enforceDoPoOnlyPermissionByContext(ContextInfo contextInfo, List list) {
        return enforcePermissionByContext(contextInfo, list, true, true, true, false);
    }

    public final boolean enforceKCS(int i) {
        if (((EnterpriseDeviceAdminInfo) this.mAdminMap.get(Integer.valueOf(i))) == null) {
            return false;
        }
        if (enforceCallerPermission(Binder.getCallingUid(), List.of("com.samsung.android.knox.permission.KNOX_INTERNAL_EXCEPTION"))) {
            return isKPUPlatformSigned(this.mContext.getPackageManager().getNameForUid(i), i);
        }
        return false;
    }

    public final void enforceKnoxV2Permission(String str, String str2) {
        if (str == null || this.mContext.checkPermission(str, Binder.getCallingPid(), Binder.getCallingUid()) == -1) {
            if (UserManager.get(this.mInjector.mContext).isManagedProfile(UserHandle.getUserId(Binder.getCallingUid()))) {
                if (!((DevicePolicyManager) this.mInjector.mContext.getSystemService("device_policy")).isOrganizationOwnedDeviceWithManagedProfile()) {
                    throw new SecurityException("This API is works only with managedProfile(WPC)");
                }
            } else if (((DevicePolicyManager) this.mInjector.mContext.getSystemService("device_policy")).semGetDeviceOwner() == null) {
                throw new SecurityException("This API is works only with managed device(DO)");
            }
        }
        if (str2 == null || this.mContext.checkPermission(str2, Binder.getCallingPid(), Binder.getCallingUid()) == -1) {
            throw new SecurityException(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("Application doesn't have this permission:", str2));
        }
    }

    public final boolean enforceKnoxV2VerifyCaller(int i) {
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
            try {
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            if (AppGlobals.getPackageManager().checkPermission("com.samsung.android.knox.permission.KNOX_KPU_INTERNAL", nameForUid2, UserHandle.getUserId(i)) != 0) {
                Log.d("EnterpriseDeviceManagerService", "Caller does not have KPU permission");
                if (!Arrays.asList(allowToSkipRuntimePermission).contains(nameForUid2) || packageManager.checkSignatures("android", nameForUid2, UserHandle.getUserId(i)) != 0) {
                    return false;
                }
            }
            return true;
        } catch (RemoteException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public final ContextInfo enforceOwnerOnly(ContextInfo contextInfo) {
        if (contextInfo == null) {
            contextInfo = new ContextInfo(Binder.getCallingUid());
        }
        if (!contextInfo.mParent) {
            this.mInjector.getClass();
            int callingUserId = UserHandle.getCallingUserId();
            if (contextInfo.mContainerId < 0) {
                Log.e("EnterpriseDeviceManagerService", "Need to check if this is an abnormal case.");
            }
            if (callingUserId != 0 || UserHandle.getUserId(contextInfo.mCallerUid) != 0 || contextInfo.mContainerId != 0) {
                throw new SecurityException("Operation supported only on owner space");
            }
        }
        return contextInfo;
    }

    public final ContextInfo enforceOwnerOnlyAndActiveAdminPermission(ContextInfo contextInfo, List list) {
        return enforcePermissionByContext(enforceOwnerOnly(contextInfo), list, false, true, false, false);
    }

    public final ContextInfo enforcePermissionByContext(ContextInfo contextInfo, List list) {
        return enforcePermissionByContext(contextInfo, list, false, false, false, false);
    }

    /* JADX WARN: Code restructure failed: missing block: B:126:0x0262, code lost:
    
        if (com.samsung.android.knox.SemPersonaManager.isKnoxId(r4) == false) goto L141;
     */
    /* JADX WARN: Removed duplicated region for block: B:138:0x02df  */
    /* JADX WARN: Removed duplicated region for block: B:148:0x031f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.samsung.android.knox.ContextInfo enforcePermissionByContext(com.samsung.android.knox.ContextInfo r10, java.util.List r11, boolean r12, boolean r13, boolean r14, boolean r15) {
        /*
            Method dump skipped, instructions count: 922
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.EnterpriseDeviceManagerServiceImpl.enforcePermissionByContext(com.samsung.android.knox.ContextInfo, java.util.List, boolean, boolean, boolean, boolean):com.samsung.android.knox.ContextInfo");
    }

    public final void enforceUMCSignature() {
        int callingUid = Binder.getCallingUid();
        try {
            PackageManagerAdapter packageManagerAdapter = mPackageManagerAdapter;
            int userId = UserHandle.getUserId(callingUid);
            packageManagerAdapter.getClass();
            if (Utils.compareSystemSignature(this.mContext, PackageManagerAdapter.getPackageInfo(64, userId, "com.sec.enterprise.knox.cloudmdm.smdms").signatures)) {
            } else {
                throw new SecurityException("Caller is not real UMC. Signature Verification failed.");
            }
        } catch (Exception unused) {
            Log.w("EnterpriseDeviceManagerService", "package not found");
        }
    }

    public final void enforceWpcod(int i, boolean z) {
        int callingUid = Binder.getCallingUid();
        if (i != callingUid) {
            throw new SecurityException(ArrayUtils$$ExternalSyntheticOutline0.m(i, callingUid, "ContextInfo UID voilation info is ", " but caller is "));
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
            if (!isProfileOwnerApp(contextInfo) && !isCallerValidKPU(contextInfo) && (nameForUid == null || !checkCallerIsKPECore(UserHandle.getUserId(callingUid), nameForUid))) {
                throw new SecurityException("Caller is not either organization owned PO or KSP inside org owned profile..");
            }
            Log.d("EnterpriseDeviceManagerService", "enforceWpcod(), caller is a either WPCOD PO or valid KPU..");
        } catch (RemoteException e) {
            Log.d("EnterpriseDeviceManagerService", "enforceWpcod(), failed to talk to DPMS..");
            e.printStackTrace();
        }
    }

    public final void enforceZtFwCaller(ContextInfo contextInfo, String str) {
        if (isKnoxZtFwCaller()) {
            EnterpriseAccessController.enforceCaller(contextInfo, str);
        } else {
            throw new SecurityException("Caller is not authorized - uid: " + Binder.getCallingUid());
        }
    }

    public EnterpriseDeviceAdminInfo findAdmin(ComponentName componentName, int i) throws IllegalArgumentException {
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
            throw new IllegalArgumentException(AmbientContextManagerPerUserService$$ExternalSyntheticOutline0.m(componentName, "Unknown admin: "));
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

    public final ComponentName getActiveAdminComponent() {
        EnterpriseDeviceAdminInfo enterpriseDeviceAdminInfo = (EnterpriseDeviceAdminInfo) this.mAdminMap.get(Integer.valueOf(Binder.getCallingUid()));
        if (enterpriseDeviceAdminInfo != null) {
            return enterpriseDeviceAdminInfo.getComponent();
        }
        return null;
    }

    public final EnterpriseDeviceAdminInfo getActiveAdminLocked(int i, ComponentName componentName) {
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

    public final List getActiveAdmins(int i) {
        enforceCallingOrSelfPermissions(this.mContext, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_PROXY_ADMIN_INTERNAL")));
        synchronized (getLockObject()) {
            try {
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = this.mAdminList;
                if (arrayList2 != null && !arrayList2.isEmpty()) {
                    Iterator it = this.mAdminList.iterator();
                    while (it.hasNext()) {
                        EnterpriseDeviceAdminInfo enterpriseDeviceAdminInfo = (EnterpriseDeviceAdminInfo) it.next();
                        if (-10000 != i && -1 != i && UserHandle.getUserId(enterpriseDeviceAdminInfo.getActivityInfo().applicationInfo.uid) != i) {
                        }
                        arrayList.add(enterpriseDeviceAdminInfo.getComponent());
                    }
                    return arrayList;
                }
                return arrayList;
            } finally {
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x00b3  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00b9 A[Catch: Exception -> 0x0064, TryCatch #1 {Exception -> 0x0064, blocks: (B:3:0x0015, B:4:0x001f, B:6:0x0025, B:50:0x003c, B:24:0x0087, B:33:0x00c4, B:35:0x00b6, B:36:0x00b9, B:37:0x009a, B:40:0x00a5, B:19:0x0106, B:48:0x0101, B:53:0x012f, B:55:0x0135, B:56:0x013e, B:58:0x0144, B:59:0x014d, B:61:0x0153, B:10:0x0067, B:45:0x0074, B:12:0x0077, B:14:0x007d), top: B:2:0x0015, inners: #0 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void getActiveAdminsInUser(com.android.server.enterprise.EnterpriseDeviceManagerServiceImpl.DeviceStatus r17, int r18, int r19, boolean r20) {
        /*
            Method dump skipped, instructions count: 353
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.EnterpriseDeviceManagerServiceImpl.getActiveAdminsInUser(com.android.server.enterprise.EnterpriseDeviceManagerServiceImpl$DeviceStatus, int, int, boolean):void");
    }

    public final List getActiveAdminsInfo(int i) {
        enforceCallingOrSelfPermissions(this.mContext, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_PROXY_ADMIN_INTERNAL")));
        synchronized (getLockObject()) {
            try {
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
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final ContextInfo getAdminContextIfCallerInCertWhiteList(List list) {
        boolean z;
        Integer asInteger;
        int callingUid = Binder.getCallingUid();
        int userId = UserHandle.getUserId(callingUid);
        Context createContextAsUser = Utils.createContextAsUser(this.mContext, "android", 0, userId);
        ContentValues contentValues = new ContentValues();
        contentValues.put(EdmStorageProviderBase.getAdminLUIDWhereIn(0, userId), "#SelectClause#");
        Iterator it = ((ArrayList) this.mEdmStorageProvider.getValues("CertificateWhiteListTable", new String[]{"adminUid", "packageName", "signature"}, contentValues)).iterator();
        while (it.hasNext()) {
            ContentValues contentValues2 = (ContentValues) it.next();
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
                                        z = Utils.comparePackageSignature(0, createContextAsUser, str, asString2);
                                        if (z) {
                                            break;
                                        }
                                    }
                                } else {
                                    z = false;
                                }
                            }
                            if (z && (asInteger = contentValues2.getAsInteger("adminUid")) != null && list != null && !list.isEmpty()) {
                                Iterator it2 = list.iterator();
                                while (it2.hasNext()) {
                                    String str2 = (String) it2.next();
                                    try {
                                    } catch (RemoteException unused) {
                                        Log.w("EnterpriseDeviceManagerService", "Could not check permission " + str2 + " of the admin that has added caller to cert white list");
                                    }
                                    if (AppGlobals.getPackageManager().checkUidPermission(str2, asInteger.intValue()) == 0) {
                                        ContextInfo contextInfo = new ContextInfo(asInteger.intValue());
                                        Binder.restoreCallingIdentity(clearCallingIdentity);
                                        return contextInfo;
                                    }
                                    continue;
                                }
                            }
                        }
                    } catch (Exception unused2) {
                        Log.d("EnterpriseDeviceManagerService", "Package added to certificate whitelisted not installed");
                    }
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
        return null;
    }

    public final boolean getAdminRemovable(ContextInfo contextInfo, String str) {
        int i;
        if (str == null) {
            i = contextInfo.mCallerUid;
        } else {
            int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
            mPackageManagerAdapter.getClass();
            ApplicationInfo applicationInfo = PackageManagerAdapter.getApplicationInfo(0, callingOrCurrentUserId, str);
            if (applicationInfo == null) {
                return true;
            }
            i = applicationInfo.uid;
        }
        return this.mEdmStorageProvider.canRemoveAdmin(i);
    }

    public final int getAdminUidForAuthorizedUid(int i) {
        Integer asInteger;
        Log.d("EnterpriseDeviceManagerService", "getAdminUidForAuthorizedUid");
        if (!checkCallerIsUMC()) {
            return -1;
        }
        enforceUMCSignature();
        ContentValues contentValues = new ContentValues();
        contentValues.put("authorizedUid", Integer.valueOf(i));
        ContentValues value = this.mEdmStorageProvider.getValue(contentValues, "ADMIN_UID_AUTHORIZATION_INFO", "adminUid");
        if (value == null || (asInteger = value.getAsInteger("adminUid")) == null) {
            return -1;
        }
        return asInteger.intValue();
    }

    public final int getAuthorizedUidForAdminUid(int i) {
        Integer asInteger;
        Log.d("EnterpriseDeviceManagerService", "getAuthorizedUidForAdminUid");
        if (!checkCallerIsUMC()) {
            return -1;
        }
        enforceUMCSignature();
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Integer.valueOf(i));
        ContentValues value = this.mEdmStorageProvider.getValue(contentValues, "ADMIN_UID_AUTHORIZATION_INFO", "authorizedUid");
        if (value == null || (asInteger = value.getAsInteger("authorizedUid")) == null) {
            return -1;
        }
        return asInteger.intValue();
    }

    public final int getConstrainedState() {
        return this.mConstrainedState.getConstrainedState();
    }

    public final synchronized IKnoxContainerManager getContainerService() {
        try {
            if (this.mContainerService == null) {
                this.mContainerService = IKnoxContainerManager.Stub.asInterface(ServiceManager.getService("mum_container_policy"));
            }
        } catch (Throwable th) {
            throw th;
        }
        return this.mContainerService;
    }

    public final void getDelegatedPackagesInUser(DeviceStatus deviceStatus, int i, int i2) {
        try {
            Map delegatedPackages = this.mDPMS.getDelegatedPackages(i);
            if (delegatedPackages == null || delegatedPackages.isEmpty()) {
                return;
            }
            deviceStatus.mDelegatedAdminContainerType = i2;
            for (Map.Entry entry : delegatedPackages.entrySet()) {
                String str = (String) entry.getKey();
                List list = (List) entry.getValue();
                Log.d("EDM_KnoxAnalytics", "Admin pkg name: " + str + ", type: DelegatedAdmin, in userId: " + i);
                deviceStatus.mDelegatedAdmins.put(str, list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void getDeviceManagementRoleHolderInUser(DeviceStatus deviceStatus, UserHandle userHandle, int i) {
        try {
            List roleHoldersAsUser = ((RoleManager) this.mContext.getSystemService(RoleManager.class)).getRoleHoldersAsUser("android.app.role.DEVICE_POLICY_MANAGEMENT", userHandle);
            if (roleHoldersAsUser.isEmpty()) {
                return;
            }
            Log.d("EDM_KnoxAnalytics", "Admin : " + ((String) roleHoldersAsUser.get(0)) + ", type: DPMRHolder, in userId: " + userHandle.getIdentifier());
            deviceStatus.mDPMRoleHolders.put(Integer.valueOf(i), (String) roleHoldersAsUser.get(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            return -1;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final String getKPUPackageName() {
        return KpuHelper.getInstance(this.mContext).getKpuPackageName();
    }

    public final Object getLockObject() {
        LockGuard.guard(9);
        return this.mLockDoNoUseDirectly;
    }

    public final List getMamPermissions(String str) {
        return this.mMamState.getPermission(str);
    }

    @Override // com.android.server.enterprise.EnterpriseDeviceManagerService
    public final int getOrganizationOwnedProfileUserId() {
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
            return -10000;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void getRemoveWarning(ComponentName componentName, RemoteCallback remoteCallback) {
        IDevicePolicyManager iDevicePolicyManager = this.mDPMS;
        this.mInjector.getClass();
        iDevicePolicyManager.getRemoveWarning(componentName, remoteCallback, UserHandle.getCallingUserId());
    }

    public final int getUserStatus(int i) {
        String packageName;
        Log.d("EnterpriseDeviceManagerService", "getUserStatus is called for userid " + i);
        int callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        int i2 = -1;
        try {
            try {
                if (i == 0) {
                    String[] packagesForUid = this.mContext.getPackageManager().getPackagesForUid(callingUid);
                    Iterator it = ((ArrayList) getActiveAdmins(i)).iterator();
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
                } else if (callingUid == this.mEdmStorageProvider.getMUMContainerOwnerUid(i)) {
                    Log.d("EnterpriseDeviceManagerService", "Valid Admin for User " + i);
                    if (getContainerService() != null) {
                        i2 = getContainerService().getStatusInternal(i);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            Log.e("EnterpriseDeviceManagerService", "Failed to getUserStatus");
        }
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i2, "status ", "EnterpriseDeviceManagerService");
        return i2;
    }

    public final boolean hasAnyActiveAdmin() {
        HashMap hashMap = this.mAdminMap;
        return hashMap != null && hashMap.size() > 0;
    }

    public final boolean hasDelegatedPermission(String str, int i, String str2) {
        int callingUid = Binder.getCallingUid();
        boolean z = false;
        if (!checkCallerIsKPECore(UserHandle.getUserId(callingUid), this.mContext.getPackageManager().getNameForUid(callingUid))) {
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

    public final boolean hasGrantedPolicy(ComponentName componentName, int i) {
        boolean usesPolicy;
        this.mInjector.getClass();
        int callingUserId = UserHandle.getCallingUserId();
        if (i < 22) {
            return this.mDPMS.hasGrantedPolicy(componentName, i, callingUserId);
        }
        synchronized (getLockObject()) {
            try {
                EnterpriseDeviceAdminInfo activeAdminLocked = getActiveAdminLocked(callingUserId, componentName);
                if (activeAdminLocked == null) {
                    throw new SecurityException("No active admin " + componentName + " on user " + callingUserId);
                }
                usesPolicy = activeAdminLocked.usesPolicy(i);
            } catch (Throwable th) {
                throw th;
            }
        }
        return usesPolicy;
    }

    public final boolean isAdminActive(ComponentName componentName) {
        boolean z;
        synchronized (getLockObject()) {
            this.mInjector.getClass();
            z = getActiveAdminLocked(UserHandle.getCallingUserId(), componentName) != null;
        }
        return z;
    }

    public final boolean isAdminRemovable(ComponentName componentName) {
        this.mInjector.getClass();
        return isAdminRemovableInternal(componentName, UserHandle.getCallingUserId());
    }

    public final boolean isAdminRemovableInternal(ComponentName componentName, int i) {
        Log.d("EnterpriseDeviceManagerService", "isAdminRemovableInternal: " + componentName + ", userHandle = " + i);
        EnterpriseDeviceAdminInfo findAdmin = findAdmin(componentName, i);
        if (findAdmin == null) {
            throw new IllegalArgumentException(AmbientContextManagerPerUserService$$ExternalSyntheticOutline0.m(componentName, "Bad admin: "));
        }
        boolean canRemoveAdmin = this.mEdmStorageProvider.canRemoveAdmin(findAdmin.getActivityInfo().applicationInfo.uid);
        AccessibilityManagerService$$ExternalSyntheticOutline0.m("isAdminRemovableInternal : ", "EnterpriseDeviceManagerService", canRemoveAdmin);
        return canRemoveAdmin;
    }

    public final boolean isCallerValidKPU(ContextInfo contextInfo) {
        KpuHelper kpuHelper = KpuHelper.getInstance(this.mContext);
        kpuHelper.getClass();
        return kpuHelper.isUidValidKpu(Binder.getCallingUid(), contextInfo.mContainerId, contextInfo.mParent);
    }

    public final boolean isCameraEnabledNative(ContextInfo contextInfo) {
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

    public final boolean isDeviceOwnedByOrganization() {
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) this.mContext.getSystemService(DevicePolicyManager.class);
        if (!devicePolicyManager.isDeviceManaged() && !devicePolicyManager.isOrganizationOwnedDeviceWithManagedProfile()) {
            return false;
        }
        Log.d("EnterpriseDeviceManagerService", "isDeviceOwnedByOrganization");
        return true;
    }

    public final boolean isEmailAdminPkg(String str) {
        return "com.samsung.android.email.provider".equals(str);
    }

    public final boolean isKPUPlatformSigned(String str, int i) {
        return KpuHelper.getInstance(this.mContext).isKpuPlatformSigned(i, str);
    }

    public final boolean isKnoxZtFwCaller() {
        Context context = this.mContext;
        int callingPid = Binder.getCallingPid();
        char[] cArr = Utils.HEX_DIGITS;
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses();
        String str = null;
        if (runningAppProcesses != null) {
            Iterator<ActivityManager.RunningAppProcessInfo> it = runningAppProcesses.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                ActivityManager.RunningAppProcessInfo next = it.next();
                if (next != null && next.pid == callingPid) {
                    str = next.processName;
                    break;
                }
            }
        }
        return EndpointMonitorImpl.KZT_FW_PKG_NAME.equals(str) && Binder.getCallingUid() == Process.myUid();
    }

    public final boolean isMdmAdminPresent() {
        return isMdmAdminPresentInternal();
    }

    public final boolean isMdmAdminPresentAsUser(int i) {
        try {
            Iterator it = this.mEdmStorageProvider.getAdminUidListAsUser(i).iterator();
            while (it.hasNext()) {
                int intValue = ((Integer) it.next()).intValue();
                ComponentName componentNameForUid = this.mEdmStorageProvider.getComponentNameForUid(intValue);
                if (componentNameForUid != null) {
                    if (!((ArrayList) EXCLUDED_ADMINS).contains(componentNameForUid.getPackageName())) {
                        if (findAdmin(componentNameForUid, UserHandle.getUserId(intValue)) != null && findAdmin(componentNameForUid, UserHandle.getUserId(intValue)).usesMDMPolicy()) {
                            Log.d("EnterpriseDeviceManagerService", "isMdmAdminPresentAsUser() : MDM Admin Found - " + componentNameForUid.getPackageName());
                            return true;
                        }
                        this.mInjector.getClass();
                        IEnterpriseLicense asInterface = IEnterpriseLicense.Stub.asInterface(ServiceManager.getService("enterprise_license_policy"));
                        List arrayList = new ArrayList();
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        if (asInterface != null) {
                            try {
                                try {
                                    arrayList = asInterface.getELMPermissions(componentNameForUid.getPackageName());
                                } catch (Throwable th) {
                                    Binder.restoreCallingIdentity(clearCallingIdentity);
                                    throw th;
                                }
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        }
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        if (arrayList != null && arrayList.size() > 0) {
                            Log.d("EnterpriseDeviceManagerService", "isMdmAdminPresentAsUser() : MDM Admin Found(2) - " + componentNameForUid.getPackageName());
                            return true;
                        }
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

    public final boolean isMdmAdminPresentInternal() {
        try {
            Iterator it = this.mEdmStorageProvider.getAdminUidList().iterator();
            while (it.hasNext()) {
                int intValue = ((Integer) it.next()).intValue();
                ComponentName componentNameForUid = this.mEdmStorageProvider.getComponentNameForUid(intValue);
                if (componentNameForUid != null) {
                    if (!((ArrayList) EXCLUDED_ADMINS).contains(componentNameForUid.getPackageName())) {
                        if (findAdmin(componentNameForUid, UserHandle.getUserId(intValue)) != null && findAdmin(componentNameForUid, UserHandle.getUserId(intValue)).usesMDMPolicy()) {
                            Log.d("EnterpriseDeviceManagerService", "isMdmAdminPresentInternal() : MDM Admin Found - " + componentNameForUid.getPackageName());
                            return true;
                        }
                        this.mInjector.getClass();
                        IEnterpriseLicense asInterface = IEnterpriseLicense.Stub.asInterface(ServiceManager.getService("enterprise_license_policy"));
                        List arrayList = new ArrayList();
                        if (asInterface != null) {
                            try {
                                arrayList = asInterface.getELMPermissions(componentNameForUid.getPackageName());
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
            }
            return false;
        } catch (Exception e2) {
            Log.e("EnterpriseDeviceManagerService", "isMdmAdminPresentInternal() : failed. ", e2);
            return true;
        }
    }

    public final boolean isPermissionIncludedOnManifest(String str) {
        String[] strArr;
        int callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                PackageInfo semGetPackageInfoAsUser = this.mContext.getPackageManager().semGetPackageInfoAsUser(this.mContext.getPackageManager().getNameForUid(callingUid), 4096, UserHandle.getUserId(callingUid));
                if (semGetPackageInfoAsUser != null && (strArr = semGetPackageInfoAsUser.requestedPermissions) != null) {
                    for (String str2 : strArr) {
                        if (str.equals(str2)) {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            return true;
                        }
                    }
                    return false;
                }
                return false;
            } catch (PackageManager.NameNotFoundException unused) {
                throw new SecurityException("Caller does not have all required permissions declared on Manifest");
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isPossibleTransferOwenerShip(ComponentName componentName) {
        return !checkAdminExistsInELMDB(componentName.getPackageName());
    }

    public final boolean isProfileOwnerApp(ContextInfo contextInfo) {
        boolean isKnoxId;
        int i = contextInfo.mContainerId;
        if (contextInfo.mParent) {
            i = UserHandle.getUserId(contextInfo.mCallerUid);
            this.mPersonaManagerAdapter.getClass();
            isKnoxId = SemPersonaManager.isKnoxId(i);
        } else {
            this.mPersonaManagerAdapter.getClass();
            isKnoxId = SemPersonaManager.isKnoxId(i);
        }
        if (!isKnoxId) {
            return false;
        }
        this.mPersonaManagerAdapter.getClass();
        return !SemPersonaManager.isSecureFolderId(i) && this.mEdmStorageProvider.getMUMContainerOwnerUid(i) == contextInfo.mCallerUid;
    }

    public final boolean isRestrictedByConstrainedState(int i) {
        return this.mConstrainedState.isRestrictedByConstrainedState(i);
    }

    public final boolean isUidDeviceOrProfileOwner(int i) {
        String nameForUid = this.mContext.getPackageManager().getNameForUid(i);
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) this.mContext.getSystemService(DevicePolicyManager.class);
        return devicePolicyManager.isDeviceOwnerApp(nameForUid) || devicePolicyManager.isProfileOwnerApp(nameForUid);
    }

    public final boolean isUserSelectable(String str) {
        DualAppManagerService$$ExternalSyntheticOutline0.m("isUserSelectable called for alias: ", str, "EnterpriseDeviceManagerService");
        boolean z = false;
        if (str != null && str.length() != 0) {
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put("alias", str);
                if (this.mEdmStorageProvider.getCount("CCMUserSelectableTable", contentValues) != 0) {
                    ContentValues contentValues2 = new ContentValues();
                    contentValues2.put("alias", str);
                    List intList = this.mEdmStorageProvider.getIntList(contentValues2, "CCMUserSelectableTable", "isSelectable");
                    Log.d("EnterpriseDeviceManagerService", "isUserSelectable - allow :" + intList);
                    ArrayList arrayList = (ArrayList) intList;
                    if (arrayList.size() > 0) {
                        Log.d("EnterpriseDeviceManagerService", "isUserSelectable - allow :" + intList);
                        z = arrayList.contains(2);
                    }
                } else {
                    Log.d("EnterpriseDeviceManagerService", "isUserSelectable - uidCount = 0");
                }
            } catch (Exception e) {
                AudioServiceExt$ResetSettingsReceiver$$ExternalSyntheticOutline0.m(e, new StringBuilder("isUserSelectable - Exception"), "EnterpriseDeviceManagerService");
            }
        }
        return z;
    }

    public final boolean keychainMarkedReset(ContextInfo contextInfo) {
        Log.d("EnterpriseDeviceManagerService", "in keychainMarkedReset");
        if (UserHandle.getAppId(Binder.getCallingUid()) != 1000) {
            Log.d("EnterpriseDeviceManagerService", "keychainMarkedReset - Cannot clear credentials, not a system app");
            return false;
        }
        if (contextInfo == null) {
            Log.d("EnterpriseDeviceManagerService", "keychainMarkedReset - Invalid Arguments");
            return false;
        }
        String[] strArr = {String.valueOf(0)};
        this.mEdmStorageProvider.deleteDataByFields("CCMCertTable", new String[]{"csr"}, strArr);
        try {
            this.mEdmStorageProvider.deleteDataByFields("CCMCertGrantTable", null, null);
        } catch (Exception e) {
            AudioServiceExt$ResetSettingsReceiver$$ExternalSyntheticOutline0.m(e, new StringBuilder("removeAllGrants - Exception"), "EnterpriseDeviceManagerService");
        }
        try {
            this.mEdmStorageProvider.deleteDataByFields("CCMUserSelectableTable", null, null);
            return true;
        } catch (Exception e2) {
            AudioServiceExt$ResetSettingsReceiver$$ExternalSyntheticOutline0.m(e2, new StringBuilder("removeAllUserSelectable - Exception"), "EnterpriseDeviceManagerService");
            return true;
        }
    }

    public final boolean migrateKnoxPoliciesForWpcod(int i) {
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

    public final boolean packageHasActiveAdmins(String str) {
        this.mInjector.getClass();
        return packageHasActiveAdminsAsUser(str, UserHandle.getCallingUserId());
    }

    public final boolean packageHasActiveAdminsAsUser(String str, int i) {
        int callingUid = Binder.getCallingUid();
        if (i != UserHandle.getUserId(callingUid) && !UserHandle.isSameApp(callingUid, 1000) && !UserHandle.isSameApp(callingUid, 0) && this.mContext.checkCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL") != 0) {
            Log.d("EnterpriseDeviceManagerService", "packageHasActiveAdminsAsUser caller need INTERACT_ACROSS_USERS_FULL permission");
            return false;
        }
        synchronized (getLockObject()) {
            try {
                int size = this.mAdminList.size();
                for (int i2 = 0; i2 < size; i2++) {
                    if (((EnterpriseDeviceAdminInfo) this.mAdminList.get(i2)).getComponent().getPackageName().equals(str) && UserHandle.getUserId(((EnterpriseDeviceAdminInfo) this.mAdminList.get(i2)).getActivityInfo().applicationInfo.uid) == i) {
                        Log.d("EnterpriseDeviceManagerService", " packageHasActiveAdminsAsUser " + ((EnterpriseDeviceAdminInfo) this.mAdminList.get(i2)).getComponent().getPackageName() + " userID" + i);
                        return true;
                    }
                }
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final String readUmcEnrollmentData(ContextInfo contextInfo) {
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

    public final void reconcileAdmin(ComponentName componentName, int i) {
        if (Binder.getCallingPid() != MY_PID) {
            throw new SecurityException("Need to be System Process");
        }
        try {
            EnterpriseDeviceAdminInfo findAdmin = findAdmin(componentName, i);
            if (findAdmin != null) {
                int i2 = findAdmin.getActivityInfo().applicationInfo.uid;
                synchronized (getLockObject()) {
                    try {
                        if (!this.mAdminMap.containsKey(Integer.valueOf(i2))) {
                            this.mAdminMap.put(Integer.valueOf(i2), findAdmin);
                            this.mAdminList.add(findAdmin);
                            if (!this.mEdmStorageProvider.addorUpdateAdmin(i2, componentName.flattenToString(), !"com.samsung.android.kgclient".equals(componentName.getPackageName()))) {
                                this.mAdminList.remove(findAdmin);
                                this.mAdminMap.remove(Integer.valueOf(i2));
                            } else {
                                Iterator it = ((TreeMap) EnterpriseService.getPolicyServices()).entrySet().iterator();
                                while (it.hasNext()) {
                                    ((EnterpriseServiceCallback) ((Map.Entry) it.next()).getValue()).onAdminAdded(i2);
                                }
                            }
                        }
                    } finally {
                    }
                }
            }
        } catch (Exception e) {
            Log.w("EnterpriseDeviceManagerService", "Failed reconcileAdmin for " + componentName.getPackageName() + "for user id " + i);
            e.printStackTrace();
        }
    }

    public final void registerBroadcastReceiver() {
        IntentFilter m = DirEncryptServiceHelper$$ExternalSyntheticOutline0.m("android.intent.action.USER_REMOVED", "android.net.conn.CONNECTIVITY_CHANGE");
        if (!isDeviceOwnedByOrganization()) {
            m.addAction(DevicePolicyListener.ACTION_DEVICE_OWNER_CHANGED);
            m.addAction(DevicePolicyListener.ACTION_PROFILE_OWNER_ADDED);
        }
        this.mContext.registerReceiver(this.mReceiver, m, 2);
    }

    public final void removeActiveAdmin(ComponentName componentName) {
        this.mInjector.getClass();
        int callingUserId = UserHandle.getCallingUserId();
        int callingUid = Binder.getCallingUid();
        StringBuilder sb = new StringBuilder("removeActiveAdmin() : Removing admin ");
        sb.append(componentName);
        sb.append(" from user ");
        sb.append(callingUserId);
        sb.append(", caller : ");
        GestureWakeup$$ExternalSyntheticOutline0.m(sb, callingUid, "EnterpriseDeviceManagerService");
        try {
            this.mDPMS.removeActiveAdmin(componentName, callingUserId);
        } catch (IllegalArgumentException e) {
            Log.e("EnterpriseDeviceManagerService", "failed to remove action admin " + e.getMessage());
        } catch (IllegalStateException e2) {
            Log.e("EnterpriseDeviceManagerService", "failed to remove action admin " + e2.getMessage());
        } catch (SecurityException e3) {
            Log.e("EnterpriseDeviceManagerService", "failed to remove action admin " + e3.getMessage());
        }
        Log.d("EnterpriseDeviceManagerService", "Admin removed from DPM!");
    }

    public final void removeActiveAdminDelayed(int i, ComponentName componentName, boolean z) {
        List activeAdmins;
        Log.d("EnterpriseDeviceManagerService", "removeActiveAdminDelayed - adminReceiver: " + componentName + ", userId: " + i);
        EnterpriseDeviceAdminInfo activeAdminLocked = getActiveAdminLocked(i, componentName);
        if (activeAdminLocked == null) {
            return;
        }
        int i2 = activeAdminLocked.getActivityInfo().applicationInfo.uid;
        int callingUid = Binder.getCallingUid();
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(i2, callingUid, "Admin uid: ", ", calling uid: ", "EnterpriseDeviceManagerService");
        if (i2 != callingUid) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.BIND_DEVICE_ADMIN", "Only system or itself can remove an EDM admin");
        }
        if (!this.mEdmStorageProvider.canRemoveAdmin(i2)) {
            NetworkScoreService$$ExternalSyntheticOutline0.m(i2, "Admin ", " cannot be removed!", "EnterpriseDeviceManagerService");
            return;
        }
        this.mConstrainedState.cleanUpConstrainedState(i2);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                boolean checkPseudoAdminForUid = this.mEdmStorageProvider.checkPseudoAdminForUid(i2);
                if (!checkPseudoAdminForUid) {
                    this.mKeyCodeMediator.onPreAdminRemoval(i2);
                    Iterator it = ((TreeMap) EnterpriseService.getPolicyServices()).entrySet().iterator();
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
                    Iterator it2 = ((TreeMap) EnterpriseService.getPolicyServices()).entrySet().iterator();
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
                        EnterpriseDeviceAdminInfo activeAdminLocked2 = getActiveAdminLocked(i, componentName2);
                        if (activeAdminLocked2 == null || activeAdminLocked2.getActivityInfo().applicationInfo.uid == 1000) {
                            if (!componentName.getPackageName().equals(componentName2.getPackageName())) {
                                reconcileAdmin(componentName2, UserHandle.getUserId(i2));
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("adminUid", Integer.valueOf(i2));
                                Iterator it4 = ((ArrayList) this.mEdmStorageProvider.getValues("PROXY_ADMIN_INFO", new String[]{"adminUid", "proxyUid"}, contentValues)).iterator();
                                while (it4.hasNext()) {
                                    ContentValues contentValues2 = (ContentValues) it4.next();
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
            } catch (RuntimeException e4) {
                throw e4;
            } catch (Exception e5) {
                e5.printStackTrace();
            }
            SecContentProviderUtil.notifyPolicyChangesAsUser(this.mInjector.mContext, "ADMIN_REMOVED", i);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void removeActiveAdminFromDpm(ComponentName componentName, int i) {
        Log.d("EnterpriseDeviceManagerService", "Removing admin " + componentName + " from user " + i + " from DPM");
        removeActiveAdminDelayed(i, componentName, false);
    }

    public final boolean removeAuthorizedUid(int i, int i2) {
        Log.d("EnterpriseDeviceManagerService", "removeAuthorizedUid");
        if (!checkCallerIsUMC()) {
            return false;
        }
        enforceUMCSignature();
        ContentValues contentValues = new ContentValues();
        Pageboost$PageboostFileDBHelper$$ExternalSyntheticOutline0.m(i2, contentValues, "adminUid", i, "authorizedUid");
        int delete = this.mEdmStorageProvider.delete("ADMIN_UID_AUTHORIZATION_INFO", contentValues);
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(delete, "removeAuthorizedUid : ", "EnterpriseDeviceManagerService");
        return delete > 1;
    }

    public final void resetAPILevelPrivacyPolicies() {
        try {
            this.mEdmStorageProvider.resetControlStateBits(this.mPseudoAdminUid);
            Log.d("EnterpriseDeviceManagerService", "updateApplicationCacheForWpcod() called");
            try {
                if (((ApplicationPolicy) EnterpriseService.getPolicyService("application_policy")) != null) {
                    ApplicationPolicy.updateApplicationCacheForWpcod(this.mPseudoAdminUid);
                }
            } catch (Exception e) {
                e.printStackTrace();
                AudioServiceExt$ResetSettingsReceiver$$ExternalSyntheticOutline0.m(e, new StringBuilder("updateApplicationCacheForWpcod error: "), "EnterpriseDeviceManagerService");
            }
        } catch (Exception e2) {
            AudioServiceExt$ResetSettingsReceiver$$ExternalSyntheticOutline0.m(e2, new StringBuilder("resetAPILevelPrivacyPolicies (Application Group): Exception - "), "EnterpriseDeviceManagerService");
        }
        try {
            this.mEdmStorageProvider.putInt(this.mPseudoAdminUid, 0, 2, "APPLICATION_MISC", "appNotificationMode");
        } catch (Exception e3) {
            e3.printStackTrace();
            AudioServiceExt$ResetSettingsReceiver$$ExternalSyntheticOutline0.m(e3, new StringBuilder("resetAPILevelPrivacyPolicies : Failed to reset App Notification Mode: "), "EnterpriseDeviceManagerService");
        }
        try {
            this.mEdmStorageProvider.putBoolean("RESTRICTION", this.mPseudoAdminUid, true, 0, "backupEnabled");
            this.mEdmStorageProvider.putBoolean("RESTRICTION", this.mPseudoAdminUid, true, 0, "clipboardEnabled");
            this.mEdmStorageProvider.putBoolean("RESTRICTION", this.mPseudoAdminUid, true, 0, "factoryresetallowed");
            this.mEdmStorageProvider.putBoolean("RESTRICTION", this.mPseudoAdminUid, true, 0, "allowClipboardShare");
            this.mEdmStorageProvider.putBoolean("RESTRICTION", this.mPseudoAdminUid, true, 0, "allowGoogleAccountsAutoSync");
            Log.d("EnterpriseDeviceManagerService", "updateRestrictionCacheForWpcod() called..");
            try {
                RestrictionPolicy restrictionPolicy = (RestrictionPolicy) EnterpriseService.getPolicyService("restriction_policy");
                if (restrictionPolicy != null) {
                    restrictionPolicy.updateRestrictionCacheForWpcod();
                }
            } catch (Exception e4) {
                e4.printStackTrace();
                AudioServiceExt$ResetSettingsReceiver$$ExternalSyntheticOutline0.m(e4, new StringBuilder("updateRestrictionCacheForWpcod error: "), "EnterpriseDeviceManagerService");
            }
        } catch (Exception e5) {
            AudioServiceExt$ResetSettingsReceiver$$ExternalSyntheticOutline0.m(e5, new StringBuilder("resetAPILevelPrivacyPolicies (Restriction Group): Exception - "), "EnterpriseDeviceManagerService");
        }
        try {
            this.mEdmStorageProvider.putBoolean("RESTRICTION", this.mPseudoAdminUid, false, 0, "globalProxy");
        } catch (Exception e6) {
            AudioServiceExt$ResetSettingsReceiver$$ExternalSyntheticOutline0.m(e6, new StringBuilder("resetAPILevelPrivacyPolicies (Global Proxy): Exception - "), "EnterpriseDeviceManagerService");
        }
        try {
            this.mEdmStorageProvider.putBoolean("MULTI_USER_MGMT", this.mPseudoAdminUid, true, 0, "multiUserCreationAllowed");
            this.mEdmStorageProvider.putBoolean("MULTI_USER_MGMT", this.mPseudoAdminUid, true, 0, "multiUserRemovalAllowed");
        } catch (Exception e7) {
            AudioServiceExt$ResetSettingsReceiver$$ExternalSyntheticOutline0.m(e7, new StringBuilder("resetAPILevelPrivacyPolicies (MultiUser policy Group): Exception - "), "EnterpriseDeviceManagerService");
        }
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("bluetoothLogEnabled", "false");
            this.mEdmStorageProvider.putValues(this.mPseudoAdminUid, 0, "BLUETOOTH", contentValues);
        } catch (Exception e8) {
            AudioServiceExt$ResetSettingsReceiver$$ExternalSyntheticOutline0.m(e8, new StringBuilder("resetAPILevelPrivacyPolicies (Bluetooth policy Group): Exception - "), "EnterpriseDeviceManagerService");
        }
        IPackageManager packageManager = AppGlobals.getPackageManager();
        try {
            this.mEdmStorageProvider.putBoolean("ADMIN_INFO", this.mPseudoAdminUid, true, 0, "canRemove");
            ArrayList adminUidList = this.mEdmStorageProvider.getAdminUidList();
            String kPUPackageName = getKPUPackageName();
            Iterator it = adminUidList.iterator();
            while (it.hasNext()) {
                int intValue = ((Integer) it.next()).intValue();
                if (UserHandle.getUserId(intValue) == 0) {
                    String packageNameForUid = this.mEdmStorageProvider.getPackageNameForUid(intValue);
                    if (packageNameForUid != null) {
                        if (!packageNameForUid.equals(kPUPackageName) && packageManager.checkPermission("com.samsung.android.knox.permission.KNOX_KPU_INTERNAL", packageNameForUid, UserHandle.getUserId(intValue)) != 0) {
                        }
                        this.mEdmStorageProvider.putBoolean("ADMIN_INFO", intValue, true, 0, "canRemove");
                    }
                }
            }
        } catch (Exception e9) {
            AudioServiceExt$ResetSettingsReceiver$$ExternalSyntheticOutline0.m(e9, new StringBuilder("resetAPILevelPrivacyPolicies (EDMPolicy.setAdminRemovable): Exception - "), "EnterpriseDeviceManagerService");
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
                StringBuilder sb = new StringBuilder("resetClassLevelPrivacyPolicies : Failed to reset table ");
                sb.append(str);
                sb.append(" : ");
                AudioServiceExt$ResetSettingsReceiver$$ExternalSyntheticOutline0.m(e, sb, "EnterpriseDeviceManagerService");
            }
        }
    }

    public final void sendIntent(int i) {
        int i2;
        if (i == 1) {
            i2 = R.string.config_isoImagePath;
        } else if (i == 2) {
            i2 = R.string.policydesc_setGlobalProxy;
        } else if (i != 3) {
            return;
        } else {
            i2 = R.string.config_devicePolicyManagementUpdater;
        }
        RestrictionToastManager.show(i2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r8v0 */
    /* JADX WARN: Type inference failed for: r8v1, types: [int] */
    /* JADX WARN: Type inference failed for: r8v2 */
    public final boolean sendKnoxAnalyticsDeviceStatus() {
        int deviceOwnerUserId;
        int callingUid = Binder.getCallingUid();
        String nameForUid = this.mContext.getPackageManager().getNameForUid(callingUid);
        Log.d("EDM_KnoxAnalytics", "KADeviceStatus caller: " + nameForUid);
        boolean equals = nameForUid.equals(KnoxCustomManagerService.KNOX_PP_AGENT_PKG_NAME);
        boolean isKPUPlatformSigned = isKPUPlatformSigned(nameForUid, UserHandle.getUserId(callingUid));
        if (callingUid != 1000 && (!equals || !isKPUPlatformSigned)) {
            throw new SecurityException("Caller is not DO or PO or KPU");
        }
        Log.d("EDM_KnoxAnalytics", "Device Status");
        StringBuilder sb = new StringBuilder();
        HashSet hashSet = new HashSet();
        HashSet hashSet2 = new HashSet();
        DeviceStatus deviceStatus = new DeviceStatus();
        ?? r8 = 1;
        deviceStatus.mUserCount = 1;
        deviceStatus.mOwnerType = -1;
        deviceStatus.isUserTypeAppSeparationExists = 0;
        deviceStatus.isUserTypeSecureFolderExists = 0;
        deviceStatus.mKGClientState = "Not_assigned";
        deviceStatus.isKCClientActive = 0;
        deviceStatus.isKSPActive = 0;
        ComponentName componentName = null;
        deviceStatus.mOwnerPackageName = null;
        deviceStatus.mKnoxDACount = 0;
        deviceStatus.mInternalKnoxAdminCount = 0;
        deviceStatus.mDeviceStatusBundle = null;
        deviceStatus.mDelegatedAdminContainerType = -1;
        deviceStatus.mDelegatedAdmins = new ArrayMap();
        deviceStatus.mDPMRoleHolders = new ArrayMap();
        deviceStatus.mKnoxDAs = new ArrayMap();
        deviceStatus.mDAs = new ArrayMap();
        deviceStatus.mKnoxInternalAdmins = new ArrayMap();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                if (SemPersonaManager.isDeviceOrProfileOwnerEnabled() && this.mDPMS.hasDeviceOwner()) {
                    deviceStatus.mOwnerType = 1;
                    SemPersonaManager semPersonaManager = (SemPersonaManager) this.mContext.getSystemService(SemPersonaManager.class);
                    if (semPersonaManager != null && semPersonaManager.isAppSeparationPresent()) {
                        deviceStatus.isUserTypeAppSeparationExists = 1;
                    }
                    componentName = this.mDPMS.getDeviceOwnerComponent(false);
                }
            } finally {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        clearCallingIdentity = Binder.clearCallingIdentity();
        if (componentName != null) {
            try {
                try {
                    deviceOwnerUserId = this.mDPMS.getDeviceOwnerUserId();
                    Log.d("EDM_KnoxAnalytics", "DeviceOwner component: " + componentName.flattenToString());
                } finally {
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
        } else {
            deviceOwnerUserId = -10000;
        }
        int i = deviceOwnerUserId;
        int i2 = 0;
        for (UserInfo userInfo : ((UserManager) this.mContext.getSystemService("user")).getUsers()) {
            int i3 = i2 + 1;
            try {
                Log.d("EDM_KnoxAnalytics", "Counting userId : " + userInfo.id + " , of type: " + userInfo.userType);
                sb.append(userInfo.userType);
                sb.append(";");
                if (!userInfo.isGuest() && !userInfo.isDualAppProfile() && !userInfo.isDemo() && userInfo.id != 77) {
                    if (userInfo.isUserTypeAppSeparation()) {
                        Log.d("EDM_KnoxAnalytics", "AppSeparation present");
                    } else if (userInfo.isSecureFolder()) {
                        Log.d("EDM_KnoxAnalytics", "SecureFolder present");
                        deviceStatus.isUserTypeSecureFolderExists = r8;
                    } else {
                        int i4 = i == userInfo.id ? 4 : -1;
                        boolean z = i4 == 4 ? r8 : false;
                        if (userInfo.isManagedProfile()) {
                            if (this.mDPMS.isProfileOwnerOfOrganizationOwnedDeviceMDM(userInfo.id)) {
                                deviceStatus.mOwnerType = 3;
                                i4 = 8;
                            } else {
                                deviceStatus.mOwnerType = 2;
                                i4 = 0;
                            }
                            componentName = this.mDPMS.getProfileOwnerAsUser(userInfo.id);
                            if (i4 == 8) {
                                Log.d("EDM_KnoxAnalytics", "WPCOD Owner component: " + componentName.flattenToString());
                            } else {
                                Log.d("EDM_KnoxAnalytics", "WP Owner component: " + componentName.flattenToString());
                            }
                            z = true;
                        } else {
                            userInfo.isMain();
                        }
                        getActiveAdminsInUser(deviceStatus, userInfo.id, i4, z);
                        if (z) {
                            getDelegatedPackagesInUser(deviceStatus, userInfo.id, i4);
                        }
                        getDeviceManagementRoleHolderInUser(deviceStatus, userInfo.getUserHandle(), i4);
                        if (deviceStatus.mKnoxDAs.containsKey(Integer.valueOf(i4))) {
                            hashSet.addAll((Collection) deviceStatus.mKnoxDAs.get(Integer.valueOf(i4)));
                        }
                        if (deviceStatus.mKnoxInternalAdmins.containsKey(Integer.valueOf(i4))) {
                            hashSet2.addAll((Collection) deviceStatus.mKnoxInternalAdmins.get(Integer.valueOf(i4)));
                        }
                    }
                }
            } catch (Exception e3) {
                e3.printStackTrace();
            }
            i2 = i3;
            r8 = 1;
        }
        deviceStatus.mUserCount = i2;
        deviceStatus.mKnoxDACount = hashSet.size();
        deviceStatus.mInternalKnoxAdminCount = hashSet2.size();
        if (componentName != null) {
            deviceStatus.mOwnerPackageName = componentName.getPackageName();
        }
        KnoxsdkFileLog.d("EDM_KnoxAnalytics", "userCount and their types: " + Integer.toString(i2) + ";" + sb.toString());
        deviceStatus.writeToBundle();
        deviceStatus.logData();
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return true;
    }

    public final void setActiveAdmin(ComponentName componentName, boolean z) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.BIND_DEVICE_ADMIN", null);
        activateAdmin(componentName, z);
    }

    public final void setActiveAdminSilent(ComponentName componentName) {
        enforceCallingOrSelfPermissions(this.mContext, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_SILENT_ACTIVATION_INTERNAL")));
        activateAdmin(componentName, false);
    }

    public final boolean setAdminRemovable(ContextInfo contextInfo, boolean z, String str) {
        int i;
        ContextInfo enforceCaller = EnterpriseAccessController.enforceCaller(contextInfo, z ? "SET_ADMIN_REMOVABLE_TRUE" : "SET_ADMIN_REMOVABLE_FALSE");
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceCaller);
        if (str == null) {
            i = enforceCaller.mCallerUid;
        } else {
            mPackageManagerAdapter.getClass();
            ApplicationInfo applicationInfo = PackageManagerAdapter.getApplicationInfo(0, callingOrCurrentUserId, str);
            if (applicationInfo == null) {
                Log.w("EnterpriseDeviceManagerService", "Can't found packageName");
                return false;
            }
            i = applicationInfo.uid;
        }
        if (this.mAdminMap.get(Integer.valueOf(i)) == null) {
            Log.d("EnterpriseDeviceManagerService", "Admin is not active");
            return false;
        }
        if (i != enforceCaller.mCallerUid && hasKnoxInternalExceptionPermission(callingOrCurrentUserId, str) && !isCallerValidKPU(enforceCaller)) {
            Log.d("EnterpriseDeviceManagerService", "Samsung internal services cannot be controlled by other admin");
            return false;
        }
        NetworkScoreService$$ExternalSyntheticOutline0.m(i, "setAdminRemovable : callingUid = ", "EnterpriseDeviceManagerService");
        boolean putBoolean = this.mEdmStorageProvider.putBoolean("ADMIN_INFO", i, z, 0, "canRemove");
        if (putBoolean) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (str == null) {
                    AuditLog.logAsUser(5, 1, true, Process.myPid(), "EnterpriseDeviceManagerService", String.format(z ? "Admin %d has set itself as removable" : "Admin %d has set itself as not removable", Integer.valueOf(enforceCaller.mCallerUid)), callingOrCurrentUserId);
                } else {
                    AuditLog.logAsUser(5, 1, true, Process.myPid(), "EnterpriseDeviceManagerService", String.format(z ? "Admin %d has set %s as removable" : "Admin %d has set %s as not removable", Integer.valueOf(enforceCaller.mCallerUid), str), callingOrCurrentUserId);
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
        return putBoolean;
    }

    public final void setAndroidLogProperty(String str) {
        if (isKnoxZtFwCaller()) {
            Utils.setSystemProperty("persist.sys.knox.zt.androidlog", str);
        } else {
            throw new SecurityException("Caller is not authorized to set property - uid: " + Binder.getCallingUid());
        }
    }

    public final int setB2BMode(boolean z) {
        return 0;
    }

    public final void setMediators() {
        for (EnterpriseServiceCallback enterpriseServiceCallback : ((TreeMap) EnterpriseService.getPolicyServices()).values()) {
            if (enterpriseServiceCallback instanceof KeyCodeRestrictionCallback) {
                ((KeyCodeRestrictionCallback) enterpriseServiceCallback).setMediator(this.mKeyCodeMediator);
            }
        }
    }

    public final void setUserSelectable(int i, String str, boolean z) {
        if (str == null || str.length() == 0) {
            return;
        }
        RCPManagerService$$ExternalSyntheticOutline0.m("EnterpriseDeviceManagerService", DirEncryptService$$ExternalSyntheticOutline0.m(i, "In setGrant - uid", ",alias:", str, ",value:"), z);
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

    @Override // com.android.server.enterprise.EnterpriseDeviceManagerService
    public final void startDeferredServicesIfNeeded() {
        InternalHandler internalHandler;
        if (this.mDeferredServicesCreated || (internalHandler = this.mInternalHandler) == null) {
            return;
        }
        internalHandler.postAtFrontOfQueue(new EnterpriseDeviceManagerServiceImpl$$ExternalSyntheticLambda0(this, 1));
        mServiceAdditionCondition.block();
    }

    public final void startDualDARServices() {
        if (UserHandle.getAppId(Binder.getCallingUid()) != 5250) {
            throw new SecurityException("Only KnoxCore app can start DualDAR services");
        }
        Log.d("EnterpriseDeviceManagerService", "Start DualDAR Services");
        if (!this.mDeferredServicesCreated) {
            if (this.mInternalHandler != null) {
                Log.d("EnterpriseDeviceManagerService", "Add DualDAR services and request to be ready");
                this.mInternalHandler.postAtFrontOfQueue(new EnterpriseDeviceManagerServiceImpl$$ExternalSyntheticLambda0(this, 0));
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

    public final void transferOwnerShip(ComponentName componentName, ComponentName componentName2, int i) {
        reconcileAdmin(componentName2, i);
        removeActiveAdminDelayed(i, componentName, true);
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

    public final void updateNotificationExemption(ContextInfo contextInfo, final String str) {
        int callingUid = Binder.getCallingUid();
        int userId = UserHandle.getUserId(callingUid);
        if (!hasKnoxInternalExceptionPermission(userId, this.mContext.getPackageManager().getNameForUid(callingUid))) {
            Log.d("EnterpriseDeviceManagerService", "Only Knox Internal package can grant notification exemption");
            return;
        }
        if (str == null || str.isEmpty()) {
            return;
        }
        mPackageManagerAdapter.getClass();
        if (!PackageManagerAdapter.isApplicationInstalled(userId, str)) {
            Log.d("EnterpriseDeviceManagerService", "Target package is not installed : ".concat(str));
            return;
        }
        try {
            final int packageUidAsUser = this.mContext.getPackageManager().getPackageUidAsUser(str, userId);
            Injector injector = this.mInjector;
            FunctionalUtils.ThrowingRunnable throwingRunnable = new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.EnterpriseDeviceManagerServiceImpl$$ExternalSyntheticLambda2
                public final void runOrThrow() {
                    EnterpriseDeviceManagerServiceImpl enterpriseDeviceManagerServiceImpl = EnterpriseDeviceManagerServiceImpl.this;
                    int i = packageUidAsUser;
                    String str2 = str;
                    if (((AppOpsManager) enterpriseDeviceManagerServiceImpl.mInjector.mContext.getSystemService(AppOpsManager.class)).unsafeCheckOpNoThrow("android:system_exempt_from_dismissible_notifications", i, str2) != 0) {
                        ((AppOpsManager) enterpriseDeviceManagerServiceImpl.mInjector.mContext.getSystemService(AppOpsManager.class)).setMode("android:system_exempt_from_dismissible_notifications", i, str2, 0);
                    }
                }
            };
            injector.getClass();
            Binder.withCleanCallingIdentity(throwingRunnable);
            Log.d("EnterpriseDeviceManagerService", "updateNotificationExemptionInner PackageName : " + str + " Notification Exemption");
        } catch (Exception e) {
            e.printStackTrace();
            StringBuilder sb = new StringBuilder("updateNotificationExemption : Failed to provide notification exemption  for ");
            sb.append(str);
            sb.append(" : ");
            AudioServiceExt$ResetSettingsReceiver$$ExternalSyntheticOutline0.m(e, sb, "EnterpriseDeviceManagerService");
        }
    }

    public final boolean writeUmcEnrollmentData(ContextInfo contextInfo, String str) {
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
}
