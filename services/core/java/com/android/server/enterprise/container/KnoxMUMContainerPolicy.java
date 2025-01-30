package com.android.server.enterprise.container;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityManagerNative;
import android.app.AppGlobals;
import android.app.IProcessObserver;
import android.app.PendingIntent;
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
import android.content.pm.ShortcutInfo;
import android.content.pm.Signature;
import android.content.pm.UserInfo;
import android.database.ContentObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.ContainerStateReceiver;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Parcelable;
import android.os.PersistableBundle;
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
import android.view.WindowManager;
import android.widget.Toast;
import com.android.internal.infra.AndroidFuture;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.Preconditions;
import com.android.internal.widget.ILockSettings;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.VerifyCredentialResponse;
import com.android.server.LocalServices;
import com.android.server.RCPManagerService;
import com.android.server.SEAMService;
import com.android.server.ServiceKeeper;
import com.android.server.display.DisplayPowerController2;
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
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.knox.dar.DarManagerService;
import com.android.server.knox.dar.SecureUtil;
import com.android.server.knox.dar.ddar.DDLog;
import com.android.server.knox.dar.sdp.SDPLog;
import com.android.server.knox.dar.sdp.SdpManagerImpl;
import com.android.server.am.ActivityManagerService;
import com.android.server.pm.KnoxMUMContainerPolicyInternal;
import com.android.server.pm.PersonaManagerService;
import com.android.server.pm.PersonaServiceHelper;
import com.android.server.wm.ActivityTaskManagerService;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.knox.AppIdentity;
import com.samsung.android.knox.ContainerProxy;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.IEnterpriseContainerCallback;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.SemPersonaState;
import com.samsung.android.knox.analytics.activation.DevicePolicyListener;
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
import com.samsung.android.knox.lockscreen.LSOUtils;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/* loaded from: classes2.dex */
public class KnoxMUMContainerPolicy extends IKnoxContainerManager.Stub implements EnterpriseServiceCallback {
    public static final boolean DEBUG = "eng".equals(SystemProperties.get("ro.build.type"));
    public static long FOTA_DEBUG_TIME = 0;
    public static final Uri REMOVE_SHORTCUT_CONTENT_URI;
    public static String TAG = "KnoxMUMContainerPolicy";
    public static final boolean isEngMode;
    public static final boolean isUserMode;
    public static Context mContext;
    public static List mDefaultPkgList;
    public static IEnterpriseContainerCallback mSetupCallback;
    public static List mbadgePolicylist;
    public final String BADGE_FILE_NAME;
    public final String CUSTOM_BASE_DIR;
    public final String CUSTOM_RES_DATA_PATH;
    public final String DEFAULT_KNOX_ICON_PATH;
    public final String KNOX_NFC_DISCOVERED_PACKAGE;
    public final String NAME_ICON_FILE_NAME;
    public final CrossProfileIntentFilter NFC_DATA;
    public final List NFC_FILTERS;
    public final CrossProfileIntentFilter NFC_MIME;
    public final String PROFILE_ICON_FILE_NAME;
    public final String SECURE_FOLDER_NFC_DISCOVERED_PACKAGE;
    public ContentObserver contentObserver;
    public boolean isPrivateModeUser;
    public ActivityManager mActivityManager;
    public IApplicationPolicy mAppService;
    public ContainerHandler mContainerHandler;
    public ProvisioningState mCurrentProvisioningState;
    public DarManagerService mDarManagerService;
    public DevicePolicyManager mDpm;
    public EnterpriseDeviceManager mEDM;
    public EdmStorageProvider mEdmStorageProvider;
    public String mFirmwareVersion;
    public final Handler mHandler;
    public final Injector mInjector;
    public final LocalService mLocalService;
    public ILockSettings mLockSettingsService;
    public PackageManager mPackageManager;
    public List mParamsList;
    public SemPersonaManager mPersona;
    public int mPrivateLockType;
    public Object mProvisioningLock;
    public ProvisioningProcessObserver mProvisioningObserver;
    public RequestIdGenerator mRIdGenerator;
    public final BroadcastReceiver mReceiver;
    public boolean mRestart;
    public SEAMService mSEAMS;
    public SdpManagerImpl mSdpManager;
    public Object mSetupCallbackLock;
    public IShortcutService mShortcutService;
    public List mTypeList;
    public UserManager mUms;

    public final boolean isSecureFolder(int i) {
        return i >= 150 && i <= 160;
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void notifyToAddSystemService(String str, IBinder iBinder) {
    }

    public boolean registerBroadcastReceiverIntent(ContextInfo contextInfo, String str, String str2) {
        return false;
    }

    public boolean setEnforceAuthForContainer(ContextInfo contextInfo, boolean z) {
        return false;
    }

    public boolean unregisterBroadcastReceiverIntent(ContextInfo contextInfo, String str, String str2) {
        return false;
    }

    static {
        String str = Build.TYPE;
        isEngMode = "eng".equals(str);
        isUserMode = "user".equalsIgnoreCase(str);
        mSetupCallback = null;
        mDefaultPkgList = new ArrayList();
        mbadgePolicylist = new ArrayList();
        FOTA_DEBUG_TIME = 0L;
        REMOVE_SHORTCUT_CONTENT_URI = Uri.parse("content://com.sec.android.app.launcher.settings/settings");
    }

    class Injector {
        public final Context mContext;

        public Injector(Context context) {
            this.mContext = context;
        }

        public EdmStorageProvider getEDMStorageProvider() {
            return new EdmStorageProvider(this.mContext);
        }

        public EnterpriseLicenseService getEnterpriseLicenseService() {
            Object policyService = EnterpriseService.getPolicyService("enterprise_license_policy");
            if (policyService != null) {
                return (EnterpriseLicenseService) policyService;
            }
            return null;
        }
    }

    public KnoxMUMContainerPolicy(Context context) {
        this(new Injector(context));
    }

    public KnoxMUMContainerPolicy(Injector injector) {
        this.DEFAULT_KNOX_ICON_PATH = "/system/container/resources/knox_icon.png";
        this.CUSTOM_RES_DATA_PATH = "/data/misc/container3.0/";
        this.PROFILE_ICON_FILE_NAME = "profileIcon.png";
        this.BADGE_FILE_NAME = "badgeIcon.png";
        this.NAME_ICON_FILE_NAME = "nameIcon.png";
        this.CUSTOM_BASE_DIR = "/data/misc/container2.0/";
        this.mSetupCallbackLock = new Object();
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
        this.mPrivateLockType = -1;
        this.isPrivateModeUser = false;
        this.mSEAMS = null;
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.server.enterprise.container.KnoxMUMContainerPolicy.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("android.bluetooth.adapter.action.STATE_CHANGED") && intent.getIntExtra("android.bluetooth.adapter.extra.STATE", Integer.MIN_VALUE) == 10 && KnoxMUMContainerPolicy.this.mRestart) {
                    Log.d(KnoxMUMContainerPolicy.TAG, "***** Restarting Bluetooth *****");
                    KnoxMUMContainerPolicy.this.mRestart = false;
                    if (BluetoothAdapter.getDefaultAdapter() != null) {
                        BluetoothAdapter.getDefaultAdapter().enable();
                    }
                }
            }
        };
        this.mReceiver = broadcastReceiver;
        this.contentObserver = new ContentObserver(new Handler()) { // from class: com.android.server.enterprise.container.KnoxMUMContainerPolicy.4
            @Override // android.database.ContentObserver
            public void onChange(boolean z, Uri uri, int i) {
                Log.d(KnoxMUMContainerPolicy.TAG, "onChange " + z + " / " + uri + " / " + i);
                int secureFolderId = KnoxMUMContainerPolicy.this.getSecureFolderId();
                if (uri.equals(Settings.Secure.getUriFor("hide_secure_folder_flag")) && i == 0) {
                    int intForUser = Settings.Secure.getIntForUser(KnoxMUMContainerPolicy.mContext.getContentResolver(), "hide_secure_folder_flag", 0, 0);
                    if (secureFolderId != -1) {
                        KnoxMUMContainerPolicy.this.showSecureFolder(intForUser);
                    }
                }
            }
        };
        this.mProvisioningLock = new Object();
        this.mProvisioningObserver = null;
        this.mCurrentProvisioningState = null;
        CrossProfileIntentFilter build = new CrossProfileIntentFilter.Builder(0).addAction("android.nfc.action.NDEF_DISCOVERED").addDataType("*/*").build();
        this.NFC_MIME = build;
        CrossProfileIntentFilter build2 = new CrossProfileIntentFilter.Builder(0).addAction("android.nfc.action.NDEF_DISCOVERED").addDataScheme("http").addDataScheme("https").addDataScheme("tel").addDataScheme("mailto").addDataScheme("geo").addDataScheme("tel").addDataScheme("voicemail").addDataScheme("sip").addDataScheme("sms").addDataScheme("smsto").addDataScheme("mms").addDataScheme("mmsto").addDataScheme("file").build();
        this.NFC_DATA = build2;
        this.NFC_FILTERS = Arrays.asList(build, build2);
        this.KNOX_NFC_DISCOVERED_PACKAGE = "com.samsung.android.knox.nfc.discovered";
        this.SECURE_FOLDER_NFC_DISCOVERED_PACKAGE = "com.samsung.android.securefolder.nfc.discovered";
        this.mInjector = injector;
        mContext = (Context) Preconditions.checkNotNull(injector.mContext);
        this.mEdmStorageProvider = injector.getEDMStorageProvider();
        this.mRestart = false;
        this.mPackageManager = mContext.getPackageManager();
        mContext.registerReceiver(broadcastReceiver, new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED"));
        this.mRIdGenerator = new RequestIdGenerator();
        this.mContainerHandler = new ContainerHandler();
        this.mHandler = new Handler();
        LocalService localService = new LocalService();
        this.mLocalService = localService;
        LocalServices.addService(KnoxMUMContainerPolicyInternal.class, localService);
    }

    public final SEAMService getSEAMSService() {
        if (this.mSEAMS == null) {
            this.mSEAMS = (SEAMService) ServiceManager.getService("SEAMService");
        }
        return this.mSEAMS;
    }

    public static int checkCallerPermissionFor(String str) {
        if (ServiceKeeper.isAuthorized(mContext, Binder.getCallingPid(), Binder.getCallingUid(), "KnoxMUMContainerPolicy", str) == 0) {
            return 0;
        }
        SecurityException securityException = new SecurityException("Security Exception Occurred while pid[" + Binder.getCallingPid() + "] with uid[" + Binder.getCallingUid() + "] trying to access methodName [" + str + "] in [KnoxMUMContainerPolicy] service");
        if (DEBUG) {
            securityException.printStackTrace();
            throw securityException;
        }
        throw securityException;
    }

    public final void registerContainerLicenseObserver() {
        EnterpriseLicenseService enterpriseLicenseService = this.mInjector.getEnterpriseLicenseService();
        if (enterpriseLicenseService != null) {
            enterpriseLicenseService.addElmKlmObserver(new ContainerLicenseObserver());
            return;
        }
        throw new RuntimeException("ContainerLicenseObserver is not added! Please invoke registerContainerLicenseObserver more later!");
    }

    public class ContainerLicenseObserver implements IActivationKlmElmObserver {
        public ContainerLicenseObserver() {
        }

        @Override // com.android.server.enterprise.license.IActivationKlmElmObserver
        public void onUpdateContainerLicenseStatus(String str) {
            KnoxMUMContainerPolicy.this.notifyLicenseStatus(str);
        }
    }

    public final boolean notifyLicenseStatus(String str) {
        Log.d(TAG, "License status updated");
        Message obtainMessage = this.mContainerHandler.obtainMessage(11);
        Bundle bundle = new Bundle();
        bundle.putString("packageName", str);
        obtainMessage.setData(bundle);
        this.mContainerHandler.sendMessage(obtainMessage);
        return true;
    }

    public class ContainerHandler extends Handler {
        public ContainerHandler() {
        }

        /* JADX WARN: Removed duplicated region for block: B:47:0x01aa A[Catch: Exception -> 0x01ef, TryCatch #0 {Exception -> 0x01ef, blocks: (B:32:0x00d4, B:34:0x00e9, B:35:0x0105, B:37:0x0131, B:40:0x0137, B:42:0x0164, B:44:0x0168, B:45:0x018c, B:47:0x01aa, B:49:0x01e5, B:52:0x0144, B:54:0x00f1, B:56:0x00fe), top: B:31:0x00d4, inners: #1 }] */
        /* JADX WARN: Removed duplicated region for block: B:49:0x01e5 A[Catch: Exception -> 0x01ef, TRY_LEAVE, TryCatch #0 {Exception -> 0x01ef, blocks: (B:32:0x00d4, B:34:0x00e9, B:35:0x0105, B:37:0x0131, B:40:0x0137, B:42:0x0164, B:44:0x0168, B:45:0x018c, B:47:0x01aa, B:49:0x01e5, B:52:0x0144, B:54:0x00f1, B:56:0x00fe), top: B:31:0x00d4, inners: #1 }] */
        @Override // android.os.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void handleMessage(Message message) {
            Intent intent;
            ApplicationInfo applicationInfo;
            int i = message.what;
            if (i != 1) {
                if (i == 8) {
                    Toast.makeText(KnoxMUMContainerPolicy.mContext, KnoxMUMContainerPolicy.mContext.getResources().getString(R.string.number_picker_increment_button), 0).show();
                    return;
                }
                if (i != 11) {
                    return;
                }
                Log.i(KnoxMUMContainerPolicy.TAG, "MSG_LICENSE_STATE_CHANGED received");
                String string = message.getData().getString("packageName");
                Log.d(KnoxMUMContainerPolicy.TAG, "package: " + string);
                for (UserInfo userInfo : KnoxMUMContainerPolicy.this.getUserManagerService().getUsers()) {
                    ComponentName deviceOwnerComponentOnAnyUser = SemPersonaManager.isDoEnabled(userInfo.id) ? KnoxMUMContainerPolicy.this.getDevicePolicyService().getDeviceOwnerComponentOnAnyUser() : SemPersonaManager.getAdminComponentName(userInfo.id);
                    if (deviceOwnerComponentOnAnyUser != null && string.equals(deviceOwnerComponentOnAnyUser.getPackageName())) {
                        Log.d(KnoxMUMContainerPolicy.TAG, "handle license activation / validation / deactivation for user: " + userInfo.id);
                        Bundle bundle = new Bundle();
                        bundle.putInt("android.intent.extra.user_handle", userInfo.id);
                        bundle.putString("knox.container.proxy.EXTRA_PACKAGE_NAME", string);
                        ContainerProxy.sendPolicyUpdate("knox.container.proxy.POLICY_KNOX_LICENSE_STATE_CHANGE", bundle);
                    }
                }
                return;
            }
            Log.d(KnoxMUMContainerPolicy.TAG, "ContainerHandler.MSG_START_SETUP_WIZARD is called ...");
            try {
                Parcelable parcelable = (ContainerCreationParams) message.obj;
                KnoxConfigurationType configurationType = parcelable.getConfigurationType();
                if (configurationType.getName().equalsIgnoreCase("knox-b2b")) {
                    intent = new Intent("android.app.action.PROVISION_MANAGED_PROFILE");
                } else if (!configurationType.getName().equalsIgnoreCase("knox-b2b-com")) {
                    return;
                } else {
                    intent = new Intent("android.app.action.PROVISION_MANAGED_DEVICE");
                }
                String adminParam = parcelable.getAdminParam();
                Log.d(KnoxMUMContainerPolicy.TAG, "Handle Message: AdminPackageName: " + adminParam);
                intent.putExtra("android.app.extra.PROVISIONING_DEVICE_ADMIN_PACKAGE_NAME", adminParam);
                intent.putExtra("com.samsung.knox.container.configType", configurationType.getName());
                if (adminParam != null && !adminParam.isEmpty()) {
                    try {
                        applicationInfo = KnoxMUMContainerPolicy.mContext.getPackageManager().getApplicationInfo(adminParam, 0);
                    } catch (PackageManager.NameNotFoundException unused) {
                        Log.e(KnoxMUMContainerPolicy.TAG, "Package '" + adminParam + "' is not found");
                    }
                    if (applicationInfo != null && applicationInfo.icon != 0) {
                        intent.putExtra("android.app.extra.PROVISIONING_LOGO_URI", Uri.parse("android.resource://" + adminParam + "/" + applicationInfo.icon));
                    }
                    intent.putExtra("android.app.extra.PROVISIONING_MAIN_COLOR", Color.parseColor("#3D6DCC"));
                    intent.putExtra("creation_params", parcelable);
                    if (intent.resolveActivity(KnoxMUMContainerPolicy.mContext.getPackageManager()) == null) {
                        KnoxMUMContainerPolicy.this.clearIdentityAndStartActivityAsUser(KnoxMUMContainerPolicy.mContext, intent, new UserHandle(UserHandle.myUserId()));
                        Log.d(KnoxMUMContainerPolicy.TAG, "createContainer: intent from User:" + UserHandle.myUserId() + " with requestid: " + parcelable.getRequestId());
                        return;
                    }
                    Log.d(KnoxMUMContainerPolicy.TAG, "Device provisioning is not enabled");
                    return;
                }
                applicationInfo = null;
                if (applicationInfo != null) {
                    intent.putExtra("android.app.extra.PROVISIONING_LOGO_URI", Uri.parse("android.resource://" + adminParam + "/" + applicationInfo.icon));
                }
                intent.putExtra("android.app.extra.PROVISIONING_MAIN_COLOR", Color.parseColor("#3D6DCC"));
                intent.putExtra("creation_params", parcelable);
                if (intent.resolveActivity(KnoxMUMContainerPolicy.mContext.getPackageManager()) == null) {
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public final void sendContainerStateChangeIntent(String str, int i, int i2, int i3, int i4) {
        Intent intent = new Intent("com.samsung.enterprise.container_state_changed");
        if (str != null && !str.isEmpty()) {
            intent.setPackage(str);
        }
        Bundle bundle = new Bundle();
        bundle.putInt("containerid", i);
        bundle.putInt("container_old_state", i3);
        bundle.putInt("container_new_state", i4);
        intent.putExtra(KnoxCustomManagerService.INTENT, bundle);
        mContext.sendBroadcastAsUser(intent, new UserHandle(i2), "com.samsung.android.knox.permission.KNOX_CONTAINER");
        Intent intent2 = new Intent("com.samsung.android.knox.intent.action.CONTAINER_STATE_CHANGED");
        if (str != null && !str.isEmpty()) {
            intent2.setPackage(str);
        }
        Bundle bundle2 = new Bundle();
        bundle2.putInt("containerid", i);
        bundle2.putInt("container_old_state", i3);
        bundle2.putInt("container_new_state", i4);
        intent2.putExtra(KnoxCustomManagerService.INTENT, bundle2);
        mContext.sendBroadcastAsUser(intent2, new UserHandle(i2), "com.samsung.android.knox.permission.KNOX_CONTAINER");
        if (str == null || str.isEmpty()) {
            return;
        }
        String kpuPackageName = KpuHelper.getInstance(mContext).getKpuPackageName();
        Intent intent3 = new Intent(intent2);
        intent3.setPackage(kpuPackageName);
        mContext.sendBroadcastAsUser(intent3, new UserHandle(i2), "com.samsung.android.knox.permission.KNOX_CONTAINER");
    }

    public final void sendContainerRemovalIntent(int i, int i2) {
        Intent intent;
        Intent intent2 = new Intent("enterprise.container.remove.progress");
        intent2.putExtra("containerid", i2);
        mContext.sendBroadcastAsUser(intent2, new UserHandle(UserHandle.myUserId()), "com.samsung.android.knox.permission.KNOX_CONTAINER");
        if (i == 0) {
            intent = new Intent("enterprise.container.uninstalled");
        } else {
            intent = new Intent("enterprise.container.unmountfailure");
        }
        intent.putExtra("containerid", i2);
        mContext.sendBroadcastAsUser(intent, new UserHandle(UserHandle.myUserId()), "com.samsung.android.knox.permission.KNOX_CONTAINER");
    }

    public final void sendContainerAdminLockIntent(String str, int i, int i2) {
        Intent intent = new Intent("enterprise.container.locked");
        if (str != null && !str.isEmpty()) {
            intent.setPackage(str);
        }
        intent.putExtra("containerid", i);
        mContext.sendBroadcastAsUser(intent, new UserHandle(i2), "com.samsung.android.knox.permission.KNOX_CONTAINER");
    }

    public class RequestIdGenerator {
        public int fraction = 0;
        public Random random = new Random();

        public int getNextContainerRequestId() {
            int i = this.fraction + 1;
            this.fraction = i;
            if (i > 10) {
                this.fraction = 1;
            }
            return this.fraction * this.random.nextInt(100000);
        }
    }

    /* renamed from: com.android.server.enterprise.container.KnoxMUMContainerPolicy$6 */
    public abstract /* synthetic */ class AbstractC14066 {
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
        switch (AbstractC14066.$SwitchMap$com$samsung$android$knox$SemPersonaState[semPersonaState.ordinal()]) {
        }
        return -1;
    }

    public final SemPersonaManager getService() {
        if (this.mPersona == null) {
            this.mPersona = (SemPersonaManager) mContext.getSystemService("persona");
        }
        return this.mPersona;
    }

    public final UserManager getUserManagerService() {
        if (this.mUms == null) {
            this.mUms = (UserManager) mContext.getSystemService("user");
        }
        return this.mUms;
    }

    public final DevicePolicyManager getDevicePolicyService() {
        if (this.mDpm == null) {
            this.mDpm = (DevicePolicyManager) mContext.getSystemService("device_policy");
        }
        return this.mDpm;
    }

    public final void sendIntentBroadcastForContainer(int i, String str) {
        Log.i(TAG, "sendIntentBroadcastForContainer : containerId " + i + " and action " + str);
        Intent intent = new Intent(str);
        intent.putExtra("container_id", i);
        intent.setPackage("com.samsung.klmsagent");
        mContext.sendBroadcastAsUser(intent, new UserHandle(0));
    }

    public final boolean isDualDarLicenseLockedCase() {
        if (SemPersonaManager.isDoEnabled(0) && SemPersonaManager.isDarDualEncryptionEnabled(0)) {
            return true;
        }
        if (getDevicePolicyService().isOrganizationOwnedDeviceWithManagedProfile()) {
            IDevicePolicyManager asInterface = IDevicePolicyManager.Stub.asInterface(ServiceManager.getService("device_policy"));
            Iterator it = getUserManagerService().getProfiles(0).iterator();
            while (it.hasNext()) {
                int identifier = ((UserInfo) it.next()).getUserHandle().getIdentifier();
                if (SemPersonaManager.isKnoxId(identifier) && SemPersonaManager.isDarDualEncryptionEnabled(identifier) && asInterface != null) {
                    try {
                        if (asInterface.isProfileOwnerOfOrganizationOwnedDeviceMDM(identifier)) {
                            return true;
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return false;
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void systemReady() {
        checkCallerPermissionFor("systemReady");
        Log.d(TAG, "System ready called....");
        registerContentObserver();
        registerContainerLicenseObserver();
        this.mActivityManager = (ActivityManager) mContext.getSystemService("activity");
        ContainerStateReceiver.register(mContext, new ContainerStateReceiver() { // from class: com.android.server.enterprise.container.KnoxMUMContainerPolicy.2
            public void onDeviceOwnerLicenseExpired(Context context, Bundle bundle) {
                Log.d(KnoxMUMContainerPolicy.TAG, "onDeviceOwnerLicenseExpired is called...");
                if (KnoxMUMContainerPolicy.this.isDualDarLicenseLockedCase()) {
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        try {
                            SystemUIAdapter.getInstance(KnoxMUMContainerPolicy.mContext).setAdminLockEnabled(0, true, true);
                        } catch (Exception e) {
                            Log.e(KnoxMUMContainerPolicy.TAG, "onDeviceOwnerLicenseExpired:Exception - ", e);
                        }
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                }
            }

            public void onDeviceOwnerActivated(Context context, Bundle bundle) {
                Log.d(KnoxMUMContainerPolicy.TAG, "onDeviceOwnerActivated is called...");
                try {
                    ComponentName deviceOwnerComponentOnAnyUser = KnoxMUMContainerPolicy.this.getDevicePolicyService().getDeviceOwnerComponentOnAnyUser();
                    if (deviceOwnerComponentOnAnyUser != null && deviceOwnerComponentOnAnyUser.getPackageName() != null) {
                        boolean addMUMContainer = KnoxMUMContainerPolicy.this.mEdmStorageProvider.addMUMContainer(0, KnoxMUMContainerPolicy.mContext.getPackageManager().getPackageUidAsUser(deviceOwnerComponentOnAnyUser.getPackageName(), 0));
                        Log.d(KnoxMUMContainerPolicy.TAG, "onDeviceOwnerActivated admin relationship result - " + addMUMContainer);
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    Log.d(KnoxMUMContainerPolicy.TAG, "onDeviceOwnerActivated:NameNotFoundException - " + e);
                } catch (Exception e2) {
                    Log.d(KnoxMUMContainerPolicy.TAG, "onDeviceOwnerActivated:Exception - " + e2);
                }
                KnoxMUMContainerPolicy.this.notifyDOPremiumActivation();
            }

            public void onDeviceOwnerLicenseActivated(Context context, Bundle bundle) {
                Log.d(KnoxMUMContainerPolicy.TAG, "onDeviceOwnerLicenseActivated called...");
                KnoxMUMContainerPolicy.this.notifyDOPremiumActivation();
                if (KnoxMUMContainerPolicy.this.isDualDarLicenseLockedCase()) {
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        try {
                            SystemUIAdapter.getInstance(KnoxMUMContainerPolicy.mContext).setAdminLockEnabled(0, false, false);
                        } catch (Exception e) {
                            Log.e(KnoxMUMContainerPolicy.TAG, "onDeviceOwnerLicenseActivated:Exception - ", e);
                        }
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                }
            }

            public void onContainerCreated(Context context, int i, Bundle bundle) {
                UserInfo userInfo = KnoxMUMContainerPolicy.this.getUserManagerService().getUserInfo(i);
                if (userInfo == null) {
                    Log.e(KnoxMUMContainerPolicy.TAG, "onContainerCreated(" + i + ") error, no user-info found");
                    return;
                }
                Log.d(KnoxMUMContainerPolicy.TAG, "onContainerCreated :: user: " + userInfo.id);
                KnoxMUMContainerPolicy.this.sendIntentBroadcastForContainer(userInfo.id, "com.sec.knox.containeragent.klms.created.b2b");
                KnoxConfigurationType filterTypeByContainerId = KnoxMUMContainerPolicy.this.filterTypeByContainerId(userInfo.id);
                Bundle bundle2 = new Bundle();
                if (filterTypeByContainerId != null && filterTypeByContainerId.isCustomizedContainerEnabled()) {
                    String customHomeScreenWallpaper = filterTypeByContainerId.getCustomHomeScreenWallpaper();
                    Log.i(KnoxMUMContainerPolicy.TAG, "homeScreenWallpaper = " + customHomeScreenWallpaper);
                    bundle2.putString("knox.container.proxy.EXTRA_KNOX_HOME_SCREEN_WALLPAPER", customHomeScreenWallpaper);
                    bundle2.putBoolean("knox.container.proxy.EXTRA_FLAG_IS_CUSTOM_CONTAINER", true);
                } else {
                    bundle2.putBoolean("knox.container.proxy.EXTRA_FLAG_IS_CUSTOM_CONTAINER", false);
                }
                bundle2.putInt("android.intent.extra.user_handle", userInfo.id);
                if (KnoxMUMContainerPolicy.this.getDarManagerService() != null) {
                    KnoxMUMContainerPolicy.this.getDarManagerService().setResetTokenForLegacyContainer(userInfo.id, bundle.getString("EXTRA_RESET_TOKEN", null));
                }
                if (SemPersonaManager.isDarDualEncryptionEnabled(userInfo.id)) {
                    DDLog.m33d(KnoxMUMContainerPolicy.TAG, "disableUnifiedLock user " + userInfo.id, new Object[0]);
                    KnoxMUMContainerPolicy.this.disableUnifiedLock(userInfo.id);
                }
            }
        });
        if (getService() != null) {
            getService().registerSystemPersonaObserver(new ISystemPersonaObserver.Stub() { // from class: com.android.server.enterprise.container.KnoxMUMContainerPolicy.3
                public void onKnoxContainerLaunch(int i) {
                }

                public void onPersonaActive(int i) {
                }

                public void onRemovePersona(int i) {
                }

                public void onResetPersona(int i) {
                }

                public void onStateChange(final int i, final SemPersonaState semPersonaState, final SemPersonaState semPersonaState2) {
                    Log.d(KnoxMUMContainerPolicy.TAG, " inside onstatechange " + i + " new " + semPersonaState2 + " old " + semPersonaState);
                    KnoxMUMContainerPolicy.this.mHandler.post(new Runnable() { // from class: com.android.server.enterprise.container.KnoxMUMContainerPolicy.3.1
                        @Override // java.lang.Runnable
                        public void run() {
                            Log.i(KnoxMUMContainerPolicy.TAG, "PersonaObserver.onStateChange() inside handler state: " + semPersonaState2 + " old state: " + semPersonaState);
                            int mUMContainerOwnerUid = KnoxMUMContainerPolicy.this.mEdmStorageProvider.getMUMContainerOwnerUid(i);
                            int userId = UserHandle.getUserId(mUMContainerOwnerUid);
                            int translateStatus = KnoxMUMContainerPolicy.translateStatus(semPersonaState);
                            int translateStatus2 = KnoxMUMContainerPolicy.translateStatus(semPersonaState2);
                            if (translateStatus2 != translateStatus) {
                                String[] packagesForUid = KnoxMUMContainerPolicy.mContext.getPackageManager().getPackagesForUid(mUMContainerOwnerUid);
                                if (packagesForUid != null) {
                                    for (String str : packagesForUid) {
                                        KnoxMUMContainerPolicy.this.sendContainerStateChangeIntent(str, i, userId, translateStatus, translateStatus2);
                                    }
                                } else {
                                    KnoxMUMContainerPolicy.this.sendContainerStateChangeIntent(null, i, userId, translateStatus, translateStatus2);
                                }
                            }
                            if (semPersonaState2.equals(SemPersonaState.ADMIN_LOCKED) || semPersonaState2.equals(SemPersonaState.ADMIN_LICENSE_LOCKED)) {
                                String[] packagesForUid2 = KnoxMUMContainerPolicy.mContext.getPackageManager().getPackagesForUid(mUMContainerOwnerUid);
                                if (packagesForUid2 != null) {
                                    for (String str2 : packagesForUid2) {
                                        KnoxMUMContainerPolicy.this.sendContainerAdminLockIntent(str2, i, userId);
                                    }
                                    return;
                                }
                                KnoxMUMContainerPolicy.this.sendContainerAdminLockIntent(null, i, userId);
                            }
                        }
                    });
                }
            });
        }
    }

    public final void notifyDOPremiumActivation() {
        if (getUserManagerService().getUserInfo(0).isPremiumContainer()) {
            Log.i(TAG, "DO'premium provisioning completed, sending intent to KLMS agent");
            sendIntentBroadcastForContainer(0, "com.sec.knox.containeragent.klms.created.b2b");
        } else {
            Log.i(TAG, "DO' license is not activated so ignoring the request...");
        }
    }

    public final void registerContentObserver() {
        Log.d(TAG, "registerContentObserver - 0");
        mContext.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("hide_secure_folder_flag"), false, this.contentObserver, 0);
    }

    public final int getSecureFolderId() {
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

    public final void showSecureFolder(int i) {
        ActivityTaskManagerService activityTaskManagerService;
        int secureFolderId = getSecureFolderId();
        Log.d(TAG, "showSecureFolder id " + secureFolderId + " newValue " + i);
        if (i == 1) {
            try {
                ActivityManagerService activityManagerService = (ActivityManagerService) ServiceManager.getService("activity");
                if (activityManagerService == null || (activityTaskManagerService = activityManagerService.mActivityTaskManager) == null) {
                    return;
                }
                if (activityTaskManagerService.getPersonaActivityHelper().isKnoxWindowVisibleLocked(false, secureFolderId, false, 1, 1)) {
                    Log.d(TAG, "showSecureFolder :: Exit from Multiwindow first");
                    Intent intent = new Intent("android.intent.action.MAIN");
                    intent.addCategory("android.intent.category.HOME");
                    intent.setFlags(335544320);
                    mContext.startActivityAsUser(intent, new UserHandle(0));
                }
                activityManagerService.mActivityTaskManager.getPersonaActivityHelper().exitAndLockSecureFolder(secureFolderId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public final KnoxConfigurationType filterType(int i, String str) {
        for (KnoxConfigurationType knoxConfigurationType : this.mTypeList) {
            if (knoxConfigurationType.getName().equals(str) && knoxConfigurationType.getAdminUid() == i) {
                return knoxConfigurationType;
            }
        }
        return null;
    }

    public final ArrayList filterType(int i) {
        ArrayList arrayList = new ArrayList();
        for (KnoxConfigurationType knoxConfigurationType : this.mTypeList) {
            if (knoxConfigurationType.getAdminUid() == i) {
                arrayList.add(knoxConfigurationType);
            }
        }
        return arrayList;
    }

    public final KnoxConfigurationType filterTypeByContainerId(int i) {
        for (KnoxConfigurationType knoxConfigurationType : this.mTypeList) {
            Iterator it = knoxConfigurationType.getPersonaList().iterator();
            while (it.hasNext()) {
                if (((Integer) it.next()).intValue() == i) {
                    return knoxConfigurationType;
                }
            }
        }
        return null;
    }

    public final EnterpriseDeviceManager getEDM() {
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(mContext);
        }
        return this.mEDM;
    }

    public final ContextInfo enforceSecurityPermission(ContextInfo contextInfo, List list) {
        return getEDM().enforceActiveAdminPermissionByContext(contextInfo, list);
    }

    public final ContextInfo enforceContainerOwnershipPermission(ContextInfo contextInfo, List list) {
        return getEDM().enforceContainerOwnerShipPermissionByContext(contextInfo, list);
    }

    public final void enforceCallingCheckPermission(Context context, List list, String str) {
        boolean z = false;
        if (list != null && list.size() > 0) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                if (context.checkCallingOrSelfPermission((String) it.next()) == 0) {
                    z |= true;
                }
            }
        }
        if (!z) {
            throw new SecurityException(str);
        }
    }

    public final ContextInfo enforceWifiPermission(ContextInfo contextInfo) {
        return getEDM().enforceActiveAdminPermissionByContext(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_WIFI")));
    }

    public List getKnoxCustomBadgePolicy() {
        return mbadgePolicylist;
    }

    public final ComponentName findAdminComponentName(String str) {
        List<ComponentName> activeAdmins = getDevicePolicyService().getActiveAdmins();
        if (activeAdmins == null) {
            return null;
        }
        for (ComponentName componentName : activeAdmins) {
            String packageName = componentName.getPackageName();
            Log.d(TAG, "Checking : " + packageName);
            if (packageName.equals(str)) {
                return componentName;
            }
        }
        return null;
    }

    public int createContainerInternal(ContainerCreationParams containerCreationParams) {
        checkCallerPermissionFor("createContainerInternal");
        int containerId = containerCreationParams.getContainerId();
        int adminUid = containerCreationParams.getAdminUid();
        Log.d(TAG, "createContainerInternal ::  uid : " + adminUid + ", containerId : " + containerId);
        if (containerId != 0 && !addContainerOwnerRelationship(containerId, adminUid)) {
            Log.d(TAG, "createContainerInternal: failed to addContainerOwnerRelationship(uid: " + adminUid + "containerId: " + containerId + ")");
            return -1014;
        }
        IDevicePolicyManager asInterface = IDevicePolicyManager.Stub.asInterface(ServiceManager.getService("device_policy"));
        if (asInterface == null) {
            return 0;
        }
        try {
            if (!asInterface.isOrganizationOwnedDeviceWithManagedProfile()) {
                return 0;
            }
            addPseudoAdminForWpcod(containerId);
            return 0;
        } catch (RemoteException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void onNewUserCreated(int i) {
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("onNewUserCreated: ");
        sb.append(i);
        sb.append(" provisioning state:");
        ProvisioningState provisioningState = this.mCurrentProvisioningState;
        sb.append(provisioningState == null ? "null" : provisioningState.toString());
        Log.i(str, sb.toString());
    }

    public final ContainerCreationParams getCreationParams(int i) {
        ContainerCreationParams containerCreationParams;
        synchronized (this.mParamsList) {
            containerCreationParams = null;
            if (!this.mParamsList.isEmpty()) {
                for (ContainerCreationParams containerCreationParams2 : this.mParamsList) {
                    if (containerCreationParams2.getConfigurationType() != null && containerCreationParams2.getConfigurationType().getPersonaList().contains(Integer.valueOf(i))) {
                        containerCreationParams = containerCreationParams2;
                    }
                }
            }
        }
        return containerCreationParams;
    }

    public class ProvisioningState {
        public String adminPackageName;
        public int containerId;
        public int creatorUid;
        public boolean isCLType;
        public int pidKnox;
        public int pidProvision;
        public String pwdRstToken;
        public int requestId;
        public int state;
        public String type;

        public ProvisioningState() {
            this.state = 0;
            this.requestId = 0;
            this.type = null;
            this.adminPackageName = null;
            this.creatorUid = -1;
            this.containerId = -1;
            this.pidProvision = -1;
            this.pidKnox = -1;
            this.isCLType = false;
            this.pwdRstToken = null;
        }

        public int getState() {
            int i;
            synchronized (KnoxMUMContainerPolicy.this.mProvisioningLock) {
                i = this.state;
            }
            return i;
        }

        public int getProcessId(String str) {
            if (str.equals("com.android.managedprovisioning")) {
                return this.pidProvision;
            }
            if (str.equals("com.samsung.android.knox.containercore")) {
                return this.pidKnox;
            }
            return -1;
        }

        public String toString() {
            return toString(toBundle());
        }

        public String toString(Bundle bundle) {
            if (bundle == null) {
                return new String("null");
            }
            StringBuilder sb = new StringBuilder();
            sb.append("{ ");
            for (String str : bundle.keySet()) {
                Object obj = bundle.get(str);
                if (obj != null) {
                    sb.append(String.format("%s:%s ", str, obj.toString(), obj.getClass().getName()));
                }
            }
            sb.append("}");
            return sb.toString();
        }

        public Bundle toBundle() {
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

        public boolean isDeviceOwnerProvisioning() {
            String str = this.type;
            if (str != null) {
                return "knox-do-basic".equals(str);
            }
            Log.e(KnoxMUMContainerPolicy.TAG, "isDeviceOwnerProvisioning(): type object is null");
            return false;
        }

        public boolean isBasicContainerProvisioning() {
            String str = this.type;
            if (str != null) {
                return "knox-po-basic".equals(str);
            }
            Log.e(KnoxMUMContainerPolicy.TAG, "isBasicContainerProvisioning(): type object is null");
            return false;
        }

        public boolean startProvisioningObserver() {
            if (KnoxMUMContainerPolicy.this.mProvisioningObserver == null) {
                KnoxMUMContainerPolicy.this.mProvisioningObserver = new ProvisioningProcessObserver();
            }
            try {
                ActivityManagerNative.getDefault().registerProcessObserver(KnoxMUMContainerPolicy.this.mProvisioningObserver);
                Log.d(KnoxMUMContainerPolicy.TAG, "Process kill observer registered.");
                return true;
            } catch (RemoteException e) {
                Log.e(KnoxMUMContainerPolicy.TAG, "RemoteException :(" + Log.getStackTraceString(e));
                return false;
            } catch (NullPointerException e2) {
                Log.e(KnoxMUMContainerPolicy.TAG, "NullPointerException :(" + Log.getStackTraceString(e2));
                return false;
            }
        }

        public boolean start(Bundle bundle) {
            Log.i(KnoxMUMContainerPolicy.TAG, "Provisioning started... " + toString(bundle));
            this.type = bundle.getString("type", null);
            this.requestId = bundle.getInt("requestId", -1);
            this.pidProvision = bundle.getInt("pidProvision", -1);
            this.adminPackageName = bundle.getString("adminPackageName", null);
            this.isCLType = bundle.getBoolean("isCLType", false);
            this.pwdRstToken = bundle.getString("pwdRstToken", null);
            this.creatorUid = bundle.getInt("creatorUid", -1);
            if (this.type == null) {
                Log.e(KnoxMUMContainerPolicy.TAG, "tyep not specified, provisioning fails");
                return false;
            }
            if (this.adminPackageName != null) {
                return true;
            }
            Log.e(KnoxMUMContainerPolicy.TAG, "admin not specified, provisioning fails");
            return false;
        }

        public boolean update(Bundle bundle) {
            Log.i(KnoxMUMContainerPolicy.TAG, "ProvisioningState.update():" + toBundle().toString());
            Log.i(KnoxMUMContainerPolicy.TAG, "ProvisioningState.update(): appying:" + toString(bundle));
            synchronized (KnoxMUMContainerPolicy.this.mProvisioningLock) {
                int i = bundle.getInt(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, -1);
                if (i == 2) {
                    this.state = 2;
                    Log.i(KnoxMUMContainerPolicy.TAG, "ManagedProvisioning service started");
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    if (!startProvisioningObserver()) {
                        Log.e(KnoxMUMContainerPolicy.TAG, "failed to start provisioning");
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return false;
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                } else if (i == 3) {
                    Log.i(KnoxMUMContainerPolicy.TAG, "KnoxCore extension service started");
                    this.state = 3;
                    this.containerId = bundle.getInt(KnoxCustomManagerService.CONTAINER_ID_ZERO, -1014);
                    this.pidKnox = bundle.getInt("pidKnox", -1);
                    if (this.containerId < 0) {
                        Log.e(KnoxMUMContainerPolicy.TAG, "container id is not provided");
                        return false;
                    }
                    if (this.type == null) {
                        if (bundle.containsKey("type")) {
                            this.type = bundle.getString("type");
                            if (bundle.containsKey("creatorUid")) {
                                this.creatorUid = bundle.getInt("creatorUid");
                            } else {
                                Log.e(KnoxMUMContainerPolicy.TAG, "creatorUid not provided");
                                return false;
                            }
                        } else {
                            Log.e(KnoxMUMContainerPolicy.TAG, "type not provided");
                            return false;
                        }
                    }
                } else {
                    switch (i) {
                        case 10:
                            Log.i(KnoxMUMContainerPolicy.TAG, "finished");
                            this.state = 10;
                            KnoxMUMContainerPolicy.this.provisioningFinished(this.containerId);
                            break;
                        case 11:
                            Log.i(KnoxMUMContainerPolicy.TAG, "failed");
                            this.state = 11;
                            KnoxMUMContainerPolicy.this.provisioningFinished(-1014);
                            break;
                        case 12:
                            Log.i(KnoxMUMContainerPolicy.TAG, "cancelled");
                            this.state = 12;
                            KnoxMUMContainerPolicy.this.provisioningFinished(-1017);
                            break;
                    }
                }
                return true;
            }
        }

        public final void notifyAdminCreationStatus(String str, int i) {
            int i2;
            Intent intent = new Intent("com.samsung.knox.container.creation.status");
            if (str != null && !str.isEmpty()) {
                intent.setPackage(str);
            }
            intent.putExtra("code", i);
            intent.putExtra("requestId", this.requestId);
            intent.putExtra("com.samsung.android.knox.intent.extra.ADMIN_UID", this.creatorUid);
            KnoxMUMContainerPolicy.mContext.sendBroadcastAsUser(intent, UserHandle.getUserHandleForUid(this.creatorUid), "com.samsung.android.knox.permission.KNOX_CONTAINER");
            KnoxMUMContainerPolicy.mContext.sendBroadcastAsUser(intent, UserHandle.getUserHandleForUid(this.creatorUid), "com.samsung.android.knox.permission.KNOX_CONTAINER");
            if (i > 0) {
                Intent intent2 = new Intent("enterprise.container.created.nonactive");
                intent2.putExtra("containerid", i);
                intent2.putExtra("com.samsung.android.knox.intent.extra.ADMIN_UID", this.creatorUid);
                KnoxMUMContainerPolicy.mContext.sendBroadcastAsUser(intent2, new UserHandle(this.creatorUid), "com.samsung.android.knox.permission.KNOX_CONTAINER");
                KnoxMUMContainerPolicy.mContext.sendBroadcastAsUser(intent2, new UserHandle(this.creatorUid), "com.samsung.android.knox.permission.KNOX_CONTAINER");
                Intent intent3 = new Intent("enterprise.container.setup.success");
                intent3.putExtra("containerid", i);
                KnoxMUMContainerPolicy.mContext.sendBroadcastAsUser(intent3, new UserHandle(this.creatorUid), "com.samsung.android.knox.permission.KNOX_CONTAINER");
                KnoxMUMContainerPolicy.mContext.sendBroadcastAsUser(intent3, new UserHandle(this.creatorUid), "com.samsung.android.knox.permission.KNOX_CONTAINER");
            } else if (i == -1017) {
                Intent intent4 = new Intent("enterprise.container.cancelled");
                intent4.putExtra("containerid", 1);
                intent4.putExtra("requestid", this.requestId);
                KnoxMUMContainerPolicy.mContext.sendBroadcastAsUser(intent4, new UserHandle(this.creatorUid), "com.samsung.android.knox.permission.KNOX_CONTAINER");
                KnoxMUMContainerPolicy.mContext.sendBroadcastAsUser(intent4, new UserHandle(this.creatorUid), "com.samsung.android.knox.permission.KNOX_CONTAINER");
            } else {
                Intent intent5 = new Intent("enterprise.container.setup.failure");
                KnoxMUMContainerPolicy.mContext.sendBroadcastAsUser(intent5, new UserHandle(this.creatorUid), "com.samsung.android.knox.permission.KNOX_CONTAINER");
                KnoxMUMContainerPolicy.mContext.sendBroadcastAsUser(intent5, new UserHandle(this.creatorUid), "com.samsung.android.knox.permission.KNOX_CONTAINER");
            }
            synchronized (KnoxMUMContainerPolicy.this.mSetupCallbackLock) {
                if (KnoxMUMContainerPolicy.mSetupCallback != null) {
                    Log.d(KnoxMUMContainerPolicy.TAG, "create callback found, updating callback..");
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
                        Log.e(KnoxMUMContainerPolicy.TAG, "Exception:" + Log.getStackTraceString(e));
                    }
                    KnoxMUMContainerPolicy.mSetupCallback = null;
                }
            }
        }

        public void notifyAdminCreationStatus(int i) {
            String[] packagesForUid = KnoxMUMContainerPolicy.mContext.getPackageManager().getPackagesForUid(this.creatorUid);
            if (packagesForUid != null) {
                for (String str : packagesForUid) {
                    notifyAdminCreationStatus(str, i);
                }
                return;
            }
            notifyAdminCreationStatus(null, i);
        }
    }

    public class ProvisioningProcessObserver extends IProcessObserver.Stub {
        public ProvisioningProcessObserver() {
        }

        public boolean maybeUnregister() {
            synchronized (KnoxMUMContainerPolicy.this.mProvisioningLock) {
                if (KnoxMUMContainerPolicy.this.mCurrentProvisioningState != null) {
                    return false;
                }
                Log.d(KnoxMUMContainerPolicy.TAG, "maybeUnregister() unregistering process observer");
                try {
                    if (KnoxMUMContainerPolicy.this.mProvisioningObserver != null) {
                        ActivityManagerNative.getDefault().unregisterProcessObserver(KnoxMUMContainerPolicy.this.mProvisioningObserver);
                        KnoxMUMContainerPolicy.this.mProvisioningObserver = null;
                        Log.i(KnoxMUMContainerPolicy.TAG, "provisioning observer unregistered");
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                    Log.e(KnoxMUMContainerPolicy.TAG, "Can't unregisterProcessObserver");
                }
                return true;
            }
        }

        public void onForegroundActivitiesChanged(int i, int i2, boolean z) {
            maybeUnregister();
        }

        public void onProcessDied(int i, int i2) {
            if (!maybeUnregister() && UserHandle.getUserId(i2) == 0) {
                synchronized (KnoxMUMContainerPolicy.this.mProvisioningLock) {
                    int state = KnoxMUMContainerPolicy.this.mCurrentProvisioningState.getState();
                    if (state == 1 || state == 2) {
                        if (KnoxMUMContainerPolicy.this.mCurrentProvisioningState.getProcessId("com.android.managedprovisioning") == i) {
                            Log.i(KnoxMUMContainerPolicy.TAG, "managedprovisioning died..");
                            Bundle bundle = new Bundle();
                            bundle.putInt(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, 12);
                            KnoxMUMContainerPolicy.this.mCurrentProvisioningState.update(bundle);
                            maybeUnregister();
                        }
                    } else if (state == 3 && KnoxMUMContainerPolicy.this.mCurrentProvisioningState.getProcessId("com.samsung.android.knox.containercore") == i) {
                        Log.i(KnoxMUMContainerPolicy.TAG, "KnoxCore died..");
                        Bundle bundle2 = new Bundle();
                        bundle2.putInt(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, 12);
                        KnoxMUMContainerPolicy.this.mCurrentProvisioningState.update(bundle2);
                        maybeUnregister();
                    }
                }
            }
        }

        public void onForegroundServicesChanged(int i, int i2, int i3) {
            maybeUnregister();
        }
    }

    public boolean updateProvisioningState(Bundle bundle) {
        checkCallerPermissionFor("updateProvisioningState");
        if (bundle == null) {
            Log.e(TAG, "updateProvisioningState() invalid input");
            return false;
        }
        int i = bundle.getInt(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, -1);
        Log.d(TAG, "updateProvisioningState called: state = " + i);
        if (i == -1) {
            Log.e(TAG, "updateProvisioningState() invalid state");
            return false;
        }
        synchronized (this.mProvisioningLock) {
            if (i == 1) {
                ProvisioningState provisioningState = this.mCurrentProvisioningState;
                if (provisioningState != null && provisioningState.state > 1) {
                    Log.e(TAG, "updateProvisioningState() provisioning already ongoing");
                    return false;
                }
                ProvisioningState provisioningState2 = new ProvisioningState();
                this.mCurrentProvisioningState = provisioningState2;
                return provisioningState2.start(bundle);
            }
            ProvisioningState provisioningState3 = this.mCurrentProvisioningState;
            if (provisioningState3 == null) {
                Log.e(TAG, "no ongoing provisioning");
                return false;
            }
            return provisioningState3.update(bundle);
        }
    }

    public Bundle getProvisioningState() {
        checkCallerPermissionFor("getProvisioningState");
        synchronized (this.mProvisioningLock) {
            ProvisioningState provisioningState = this.mCurrentProvisioningState;
            if (provisioningState == null) {
                Log.e(TAG, "no ongoing provisioning");
                return new Bundle();
            }
            return provisioningState.toBundle();
        }
    }

    public int checkProvisioningPreCondition(String str, int i) {
        return checkProvisioningPreCondition(str, i, true);
    }

    public final int checkProvisioningPreCondition(String str, int i, boolean z) {
        if (Binder.getCallingUid() != 1000) {
            checkCallerPermissionFor("checkProvisioningPreCondition");
        }
        Log.d(TAG, "checkProvisioningPreCondition called type:" + str + " flags:" + i);
        KnoxConfigurationType configurationTypeByName = KnoxContainerManager.getConfigurationTypeByName(str);
        if (str != null && !"secure-folder".equals(str)) {
            if (Integer.parseInt("2") == 2) {
                Log.e(TAG, "Cannot create Legacy container on PEACE products");
                return -9999;
            }
            if (Integer.parseInt("2") == 1 && (configurationTypeByName instanceof ContainerModeConfigurationType)) {
                Log.e(TAG, "Cannot create COM container on PEACE products");
                return -9999;
            }
        }
        List profiles = ((UserManager) mContext.getSystemService("user")).getProfiles(0);
        if ((configurationTypeByName instanceof ContainerModeConfigurationType) && SemDesktopModeManager.isDesktopMode()) {
            Log.e(TAG, "Cannot create COM container on DeskTopMode(DEX)");
            return -1014;
        }
        try {
            SemDesktopModeManager semDesktopModeManager = (SemDesktopModeManager) mContext.getSystemService("desktopmode");
            if (semDesktopModeManager != null) {
                SemDesktopModeState desktopModeState = semDesktopModeManager.getDesktopModeState();
                if ((configurationTypeByName instanceof ContainerModeConfigurationType) && desktopModeState.enabled == 4) {
                    Log.e(TAG, "Cannot create COM container on DeskTopMode(Dual mode)");
                    return -1014;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!PersonaServiceHelper.canAddMoreManagedProfiles(mContext, z, i, profiles)) {
            Log.e(TAG, "Cannot add more profiles");
            return -1021;
        }
        synchronized (this.mProvisioningLock) {
            ProvisioningState provisioningState = this.mCurrentProvisioningState;
            if (provisioningState != null && provisioningState.state > 1) {
                Log.e(TAG, "Another provisioning is ongoing.");
            }
        }
        Log.d(TAG, "checkProvisioningPreCondition allowed:" + str + " flags:" + i);
        return 0;
    }

    public final void setProfileEnabled(int i) {
        getUserManagerService().setUserEnabled(i);
        UserInfo profileParent = getUserManagerService().getProfileParent(i);
        Intent intent = new Intent(DevicePolicyListener.ACTION_PROFILE_OWNER_ADDED);
        intent.putExtra("android.intent.extra.USER", new UserHandle(i));
        intent.addFlags(1342177280);
        if (profileParent == null) {
            return;
        }
        mContext.sendBroadcastAsUser(intent, new UserHandle(profileParent.id));
    }

    public final void provisioningFinished(int i) {
        ProvisioningState provisioningState = this.mCurrentProvisioningState;
        if (provisioningState == null) {
            return;
        }
        provisioningState.notifyAdminCreationStatus(i);
        synchronized (this.mProvisioningLock) {
            if (this.mCurrentProvisioningState == null) {
                Log.e(TAG, "provisioningFinished() no ongoing provisioning, skip");
                return;
            }
            Log.i(TAG, "provisioningFinished()... " + this.mCurrentProvisioningState.toString());
            if (!this.mCurrentProvisioningState.isDeviceOwnerProvisioning() || this.mCurrentProvisioningState.containerId != 0) {
                ProvisioningState provisioningState2 = this.mCurrentProvisioningState;
                if (provisioningState2.state == 10) {
                    if (!provisioningState2.isBasicContainerProvisioning()) {
                        Bundle bundle = new Bundle();
                        KnoxConfigurationType filterTypeByContainerId = filterTypeByContainerId(this.mCurrentProvisioningState.containerId);
                        if (filterTypeByContainerId != null) {
                            bundle.putParcelable(ContainerStateReceiver.EXTRA_CONTIANER_CONFIGURATION_TYPE, filterTypeByContainerId);
                        }
                        String str = this.mCurrentProvisioningState.pwdRstToken;
                        if (str != null) {
                            bundle.putString("EXTRA_RESET_TOKEN", str);
                        }
                        SemPersonaManager.sendContainerEvent(mContext, this.mCurrentProvisioningState.containerId, 0, bundle);
                        Log.i(TAG, "container created via Knox API. enabling container in the framework");
                        setProfileEnabled(this.mCurrentProvisioningState.containerId);
                    } else {
                        Log.i(TAG, "contaienr created via AFW API. skip enabling container in the framework");
                        SemPersonaManager.sendContainerEvent(mContext, this.mCurrentProvisioningState.containerId, 0);
                    }
                } else {
                    Log.i(TAG, "provisioningFinished() not a normal finish, state:" + this.mCurrentProvisioningState.state);
                }
            }
            try {
                ContainerCreationParams containerCreationParams = new ContainerCreationParams();
                String packageName = ((DevicePolicyManager) mContext.getSystemService("device_policy")).getProfileOwnerAsUser(new UserHandle(this.mCurrentProvisioningState.containerId)).getPackageName();
                int packageUidAsUser = mContext.getPackageManager().getPackageUidAsUser(packageName, this.mCurrentProvisioningState.containerId);
                Log.d(TAG, "calling createContainerInternal " + packageName + " / " + this.mCurrentProvisioningState.containerId + " / " + packageUidAsUser);
                containerCreationParams.setAdminParam(packageName);
                containerCreationParams.setContainerId(this.mCurrentProvisioningState.containerId);
                containerCreationParams.setAdminUid(packageUidAsUser);
                createContainerInternal(containerCreationParams);
            } catch (Exception e) {
                Log.d(TAG, "calling createContainerInternal failed : " + e);
            }
            this.mCurrentProvisioningState = null;
            if (this.mProvisioningObserver != null) {
                try {
                    ActivityManager.getService().unregisterProcessObserver(this.mProvisioningObserver);
                    this.mProvisioningObserver = null;
                    Log.i(TAG, "provisioning observer unregistered");
                } catch (RemoteException e2) {
                    Log.e(TAG, "RemoteException :(" + Log.getStackTraceString(e2));
                }
            }
        }
    }

    public int createContainerWithCallback(ContextInfo contextInfo, CreationParams creationParams, int i, IEnterpriseContainerCallback iEnterpriseContainerCallback) {
        mSetupCallback = iEnterpriseContainerCallback;
        return createContainer(contextInfo, creationParams, i);
    }

    public int createContainer(ContextInfo contextInfo, CreationParams creationParams, int i) {
        int callingUid;
        boolean z;
        ApplicationInfo applicationInfo;
        if (creationParams == null) {
            Log.e(TAG, "provisioning failed. no creation param");
            return -1026;
        }
        List configurationTypeByName = getConfigurationTypeByName(null, creationParams.getConfigurationName());
        if (configurationTypeByName == null || configurationTypeByName.isEmpty()) {
            Log.e(TAG, "Invalid Knox Configuration Type!");
            return -1030;
        }
        KnoxConfigurationType knoxConfigurationType = (KnoxConfigurationType) configurationTypeByName.get(0);
        boolean z2 = knoxConfigurationType instanceof SecureFolderConfigurationType;
        if ((!z2 || getUserManagerService().hasUserRestriction("no_add_managed_profile", UserHandle.SYSTEM)) && SemPersonaManager.isDoEnabled(0)) {
            Log.d(TAG, "createContainer fails when Device Owner is enabled.");
            this.mContainerHandler.sendMessage(this.mContainerHandler.obtainMessage(8));
            return -1014;
        }
        enforceCallingCheckPermission(mContext, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CONTAINER")), "Activate Container permission");
        int nextContainerRequestId = this.mRIdGenerator.getNextContainerRequestId();
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
                Log.e(TAG, "Only primary profile (user 0) can activate PO/DO");
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
                Log.d(TAG, "admin : " + adminPackageName + ", callingUid - " + callingUid);
                callingUid = mContext.getPackageManager().getPackageUidAsUser(adminPackageName, UserHandle.getUserId(callingUid));
                z = false;
            }
            int checkProvisioningPreCondition = checkProvisioningPreCondition(configurationName, knoxConfigurationType instanceof SecureFolderConfigurationType ? 268566624 : 268435552, false);
            if (checkProvisioningPreCondition != 0) {
                Log.e(TAG, "provisioning not allowed: " + checkProvisioningPreCondition);
                return checkProvisioningPreCondition;
            }
            if (knoxConfigurationType instanceof ContainerModeConfigurationType) {
                if (!z) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return -1023;
                }
                if (!isContainerOnlyModeAllowed()) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return -1021;
                }
            }
            Log.d(TAG, "adminUid : " + callingUid);
            AuditLog.logAsUser(5, 1, true, Process.myPid(), "KnoxMUMContainerPolicy", String.format("Admin %d has successfully requested to create container.", Integer.valueOf(callingUid)), 0);
            if (z2) {
                Log.d(TAG, "Start to check secure folder");
                Bundle bundle = new Bundle();
                bundle.putString("type", configurationName);
                bundle.putInt("requestId", nextContainerRequestId);
                bundle.putBoolean("isCLType", false);
                bundle.putString("pwdRstToken", passwordResetToken);
                bundle.putInt("creatorUid", callingUid);
                bundle.putInt(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, 1);
                bundle.putString("adminPackageName", adminPackageName);
                this.mCurrentProvisioningState = new ProvisioningState();
                synchronized (this.mProvisioningLock) {
                    this.mCurrentProvisioningState.start(bundle);
                }
                Intent intent = new Intent("com.sec.knox.action.CREATE_SECURE_FOLDER");
                intent.setPackage("com.samsung.android.knox.containercore");
                mContext.startServiceAsUser(intent, UserHandle.of(UserHandle.myUserId()));
            } else {
                Intent intent2 = new Intent("android.app.action.PROVISION_MANAGED_PROFILE");
                ComponentName findAdminComponentName = z ? findAdminComponentName(adminPackageName) : null;
                if (findAdminComponentName != null) {
                    intent2.putExtra("android.app.extra.PROVISIONING_DEVICE_ADMIN_COMPONENT_NAME", findAdminComponentName);
                } else {
                    intent2.putExtra("android.app.extra.PROVISIONING_DEVICE_ADMIN_PACKAGE_NAME", adminPackageName);
                }
                intent2.putExtra("com.samsung.knox.container.configType", configurationName);
                intent2.putExtra("com.samsung.knox.container.requestId", nextContainerRequestId);
                intent2.putExtra("com.samsung.knox.container.isCLType", z);
                intent2.putExtra("com.samsung.knox.container.pwdRstToken", passwordResetToken);
                intent2.putExtra("com.samsung.knox.container.adminUid", callingUid);
                try {
                    applicationInfo = mContext.getPackageManager().getApplicationInfo(adminPackageName, 0);
                } catch (PackageManager.NameNotFoundException unused) {
                    Log.e(TAG, "Package '" + adminPackageName + "' is not found");
                    applicationInfo = null;
                }
                if (applicationInfo != null && applicationInfo.icon != 0) {
                    intent2.putExtra("android.app.extra.PROVISIONING_LOGO_URI", Uri.parse("android.resource://" + adminPackageName + "/" + applicationInfo.icon));
                }
                intent2.putExtra("android.app.extra.PROVISIONING_MAIN_COLOR", Color.parseColor("#3D6DCC"));
                intent2.setFlags(872546304);
                if (intent2.resolveActivity(mContext.getPackageManager()) != null) {
                    clearIdentityAndStartActivityAsUser(mContext, intent2, new UserHandle(UserHandle.myUserId()));
                    Log.d(TAG, "createContainer: intent from User:" + UserHandle.myUserId() + " with requestid: " + nextContainerRequestId);
                } else {
                    Log.d(TAG, "Device provisioning is not enabled");
                }
            }
            return nextContainerRequestId;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1026;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean createContainerMarkSuccess(ContainerCreationParams containerCreationParams) {
        checkCallerPermissionFor("createContainerMarkSuccess");
        Log.d(TAG, "createContainerMarkSuccess ->  : param: " + containerCreationParams);
        synchronized (this.mParamsList) {
            ContainerCreationParams matchCreationParams = matchCreationParams(containerCreationParams);
            if (matchCreationParams == null) {
                return false;
            }
            matchCreationParams.setRequestState(2);
            return true;
        }
    }

    public boolean cancelCreateContainer(ContainerCreationParams containerCreationParams) {
        checkCallerPermissionFor("cancelCreateContainer");
        Log.d(TAG, "cancelCreateContainer ->  :  adminParam: " + containerCreationParams);
        synchronized (this.mProvisioningLock) {
            if (this.mCurrentProvisioningState == null) {
                return false;
            }
            Bundle bundle = new Bundle();
            bundle.putInt(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, 12);
            this.mCurrentProvisioningState.update(bundle);
            return false;
        }
    }

    public final ContainerCreationParams matchCreationParams(ContainerCreationParams containerCreationParams) {
        ContainerCreationParams containerCreationParams2;
        List list = this.mParamsList;
        if (list != null && containerCreationParams != null) {
            Iterator it = list.iterator();
            while (true) {
                if (!it.hasNext()) {
                    containerCreationParams2 = null;
                    break;
                }
                containerCreationParams2 = (ContainerCreationParams) it.next();
                if (containerCreationParams2.getRequestId() == containerCreationParams.getRequestId()) {
                    break;
                }
            }
            if (containerCreationParams2 != null) {
                return containerCreationParams2;
            }
        }
        return null;
    }

    public final boolean addContainerOwnerRelationship(int i, int i2) {
        Log.d(TAG, "Add Container owner relationship.");
        try {
            if (this.mEdmStorageProvider.addMUMContainer(i, i2)) {
                if (isEngMode) {
                    Log.d(TAG, "Container Added to DB: " + i);
                }
            } else {
                Log.e(TAG, "Failed to add container to DB: " + i);
            }
            return true;
        } catch (Exception e) {
            Log.w(TAG, "Failed at addContainerToDB ", e);
            return true;
        }
    }

    public final boolean removeContainerOwnerRelationship(int i) {
        boolean z = false;
        try {
            z = this.mEdmStorageProvider.removeMUMContainer(i);
            if (z) {
                Log.d(TAG, "Container removed from ownership DB: " + i);
            } else {
                Log.e(TAG, "Container not found or Failed to remove container from DB: " + i);
            }
        } catch (Exception e) {
            Log.w(TAG, "Failed at removeContainerOwnerRelationship " + Log.getStackTraceString(e));
        }
        return z;
    }

    public int removeContainerInternal(int i) {
        UserInfo userInfo;
        checkCallerPermissionFor("removeContainerInternal");
        Binder.getCallingUid();
        Log.d(TAG, "removeContainerInternal:" + i);
        long j = 0;
        try {
            try {
                j = Binder.clearCallingIdentity();
                userInfo = getUserManagerService().getUserInfo(i);
            } catch (Exception e) {
                Log.e(TAG, "Exception:" + Log.getStackTraceString(e));
            }
            if (userInfo == null) {
                Log.e(TAG, "user:" + i + " not found");
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
            sendIntentBroadcastForContainer(userInfo.id, "com.sec.knox.containeragent.klms.removed.b2b");
            Binder.restoreCallingIdentity(j);
            Log.d(TAG, "removeContainer(" + i + ")");
            Log.d(TAG, "sendContainerRemovalIntent(containerId: " + i + ")");
            sendContainerRemovalIntent(-1201, i);
            return 0;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(0L);
            throw th;
        }
    }

    public int removeContainer(ContextInfo contextInfo) {
        enforceContainerOwnershipPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CONTAINER")));
        try {
            checkCallerPermissionFor("removeContainer");
        } catch (SecurityException e) {
            Log.e(TAG, "SEAMS invalidated caller. lets go for MDM check.." + Log.getStackTraceString(e));
            enforceSecurityPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CONTAINER")));
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        Log.d(TAG, "Container removal started");
        boolean removeUser = getUserManagerService().removeUser(contextInfo.mContainerId);
        AuditLog.logAsUser(5, 1, true, Process.myPid(), "KnoxMUMContainerPolicy", String.format("Admin %d has successfully removed Workspace %d", Integer.valueOf(contextInfo.mCallerUid), Integer.valueOf(contextInfo.mContainerId)), UserHandle.getUserId(contextInfo.mCallerUid));
        Binder.restoreCallingIdentity(clearCallingIdentity);
        if (removeUser) {
            Log.d(TAG, "Container removal success");
            return 0;
        }
        Log.d(TAG, "Container removal failed");
        return -1201;
    }

    public final List getContainers(int i) {
        Log.d(TAG, "getContainers: admin uid: " + i);
        ArrayList arrayList = new ArrayList();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                List<UserInfo> users = getUserManagerService() != null ? getUserManagerService().getUsers(true) : null;
                if (users != null) {
                    for (UserInfo userInfo : users) {
                        if (userInfo.isManagedProfile()) {
                            int mUMContainerOwnerUid = this.mEdmStorageProvider.getMUMContainerOwnerUid(userInfo.id);
                            Log.d(TAG, "Persona found with id , creator uid, passed uid: " + userInfo.id + " " + mUMContainerOwnerUid + " " + i);
                            if (i == mUMContainerOwnerUid) {
                                arrayList.add(Integer.valueOf(userInfo.id));
                            }
                        }
                    }
                }
            } catch (Exception e) {
                Log.d(TAG, "getContainers exception: " + Log.getStackTraceString(e));
            }
            return arrayList;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public List getContainers(ContextInfo contextInfo) {
        int callingUid;
        if (contextInfo != null && contextInfo.mCallerUid > 0) {
            enforceSecurityPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CONTAINER")));
            callingUid = contextInfo.mCallerUid;
        } else {
            callingUid = Binder.getCallingUid();
        }
        return getContainers(callingUid);
    }

    public EnterpriseContainerObject[] getOwnContainers() {
        ArrayList arrayList;
        int callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                List<UserInfo> users = getUserManagerService() != null ? getUserManagerService().getUsers(true) : null;
                if (users != null) {
                    arrayList = new ArrayList();
                    try {
                        for (UserInfo userInfo : users) {
                            int mUMContainerOwnerUid = this.mEdmStorageProvider.getMUMContainerOwnerUid(userInfo.id);
                            if (DEBUG) {
                                Log.d(TAG, "Persona found with id , creator uid, passed uid: " + userInfo.id + " " + mUMContainerOwnerUid + " " + callingUid);
                            }
                            if (mUMContainerOwnerUid == callingUid) {
                                EnterpriseContainerObject enterpriseContainerObject = new EnterpriseContainerObject();
                                enterpriseContainerObject.setContainerId(userInfo.id);
                                enterpriseContainerObject.setContainerAdmin(callingUid);
                                enterpriseContainerObject.setContainerName(getUserManagerService().getUserInfo(userInfo.id).name);
                                arrayList.add(enterpriseContainerObject);
                            }
                        }
                    } catch (Exception e) {
                        e = e;
                        Log.d(TAG, "getOwnContainers exception: " + Log.getStackTraceString(e));
                        return arrayList == null ? null : null;
                    }
                } else {
                    arrayList = null;
                }
            } catch (Exception e2) {
                e = e2;
                arrayList = null;
            }
            if (arrayList == null && !arrayList.isEmpty()) {
                return (EnterpriseContainerObject[]) arrayList.toArray(new EnterpriseContainerObject[arrayList.size()]);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public int getStatus(ContextInfo contextInfo) {
        enforceContainerOwnershipPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CONTAINER")));
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                return getStatusInternal(contextInfo.mContainerId);
            } catch (Exception e) {
                Log.d(TAG, "getStatus exception: " + Log.getStackTraceString(e));
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -1;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public int getStatusInternal(int i) {
        int callingUid = Binder.getCallingUid();
        Log.d(TAG, "getStatusInternal callerUid : " + callingUid);
        if (callingUid != 1000) {
            return -1;
        }
        try {
            UserManager userManagerService = getUserManagerService();
            UserInfo userInfo = userManagerService.getUserInfo(i);
            Log.d(TAG, "getStatusInternal of user: " + i);
            if (userInfo != null) {
                synchronized (this.mProvisioningLock) {
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
                }
            }
        } catch (Exception e) {
            Log.d(TAG, "getStatusInternal exception: " + Log.getStackTraceString(e));
        }
        return -1;
    }

    public boolean lockContainer(ContextInfo contextInfo, String str) {
        Log.i(TAG, "lockContainer is called....");
        ContextInfo enforceContainerOwnershipPermission = enforceContainerOwnershipPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CONTAINER")));
        PasswordPolicy passwordPolicy = (PasswordPolicy) ServiceManager.getService("password_policy");
        boolean z = false;
        if (passwordPolicy != null) {
            try {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                boolean lock = passwordPolicy.lock(enforceContainerOwnershipPermission);
                if (lock) {
                    try {
                        AuditLog.logAsUser(5, 1, true, Process.myPid(), "KnoxMUMContainerPolicy", String.format("Admin %d has locked Workspace.", Integer.valueOf(enforceContainerOwnershipPermission.mCallerUid)), enforceContainerOwnershipPermission.mContainerId);
                    } catch (Exception e) {
                        e = e;
                        z = lock;
                        Log.w(TAG, "Failed at KnoxMUMContainerPolicy API lockContainer ", e);
                        Log.i(TAG, "lockContainer result - " + z);
                        return z;
                    }
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                z = lock;
            } catch (Exception e2) {
                e = e2;
            }
        }
        Log.i(TAG, "lockContainer result - " + z);
        return z;
    }

    public boolean unlockContainer(ContextInfo contextInfo) {
        Log.i(TAG, "unlockContainer is called....");
        ContextInfo enforceContainerOwnershipPermission = enforceContainerOwnershipPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CONTAINER")));
        PasswordPolicy passwordPolicy = (PasswordPolicy) ServiceManager.getService("password_policy");
        boolean z = false;
        if (passwordPolicy != null) {
            try {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                boolean unlock = passwordPolicy.unlock(enforceContainerOwnershipPermission);
                if (unlock) {
                    try {
                        AuditLog.logAsUser(5, 1, true, Process.myPid(), "KnoxMUMContainerPolicy", String.format("Admin %d has unlocked Workspace.", Integer.valueOf(enforceContainerOwnershipPermission.mCallerUid)), enforceContainerOwnershipPermission.mContainerId);
                    } catch (Exception e) {
                        e = e;
                        z = unlock;
                        Log.w(TAG, "Failed at KnoxMUMContainerPolicy API unlockContainer ", e);
                        Log.i(TAG, "unlockContainer result - " + z);
                        return z;
                    }
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                z = unlock;
            } catch (Exception e2) {
                e = e2;
            }
        }
        Log.i(TAG, "unlockContainer result - " + z);
        return z;
    }

    public int forceResetPassword(ContextInfo contextInfo, String str, int i) {
        if (contextInfo == null) {
            return -2;
        }
        Log.d(TAG, "forceResetPassword: containerId: " + contextInfo.mContainerId);
        enforceContainerOwnershipPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CONTAINER")));
        long clearCallingIdentity = Binder.clearCallingIdentity();
        int i2 = contextInfo.mContainerId;
        int activePasswordQuality = new LockPatternUtils(mContext).getActivePasswordQuality(i2);
        Log.d(TAG, "UCS enabled for user = " + i2);
        Log.d(TAG, "current quality = " + activePasswordQuality + ", SMART CARD Quality = 458752");
        if (activePasswordQuality == 458752) {
            Log.d(TAG, "forceResetPassword declined because Lock Quality set to Smartcard for user = " + i2);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return -2;
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        SDPLog.m43i("Reset Password requested for container " + contextInfo.mContainerId);
        SDPLog.m46p("cxtInfo", contextInfo, "resetPwdKey", str, "timeout", Integer.valueOf(i));
        if (getLockSettings() == null || getSdpManager() == null) {
            return -2;
        }
        VerifyCredentialResponse verifyCredentialResponse = VerifyCredentialResponse.ERROR;
        SdpManagerImpl sdpManager = getSdpManager();
        int i3 = contextInfo.mContainerId;
        long clearCallingIdentity2 = Binder.clearCallingIdentity();
        try {
            try {
            } catch (Exception e) {
                e.printStackTrace();
                SDPLog.m41e("Unexpected exception while force reset password", e);
            }
            if (!SemPersonaManager.isKnoxId(i3)) {
                return -2;
            }
            sdpManager.cancelLegacyResetTimeout(i3);
            sdpManager.clearLegacyResetStatus(i3);
            SDPLog.m38d("Prepare escrow token");
            long tokenHandle = sdpManager.getTokenHandle(i3);
            byte[] bytes = str != null ? str.getBytes() : sdpManager.getResetToken(i3);
            SDPLog.m38d("Verify token with token handle");
            if (tokenHandle != 0 && bytes != null) {
                verifyCredentialResponse = getLockSettings().verifyToken(bytes, tokenHandle, i3);
                if (!verifyCredentialResponse.isMatched()) {
                    SDPLog.m43i("Reset password failed (Invalid legacy token)");
                } else if (verifyCredentialResponse != VerifyCredentialResponse.ERROR) {
                    SDPLog.m43i("Reset password start");
                    sdpManager.onLegacyResetCredentialRequested(bytes, i3, i);
                    SecureUtil.clear(bytes);
                    Bundle bundle = new Bundle();
                    bundle.putInt("android.intent.extra.user_handle", i3);
                    Bundle sendCommand = ContainerProxy.sendCommand("knox.container.proxy.COMMAND_RESET_PASSWORD", bundle);
                    int i4 = sendCommand != null ? sendCommand.getInt("android.intent.extra.RETURN_RESULT") : 99;
                    SDPLog.m43i("Reset password result: " + i4);
                    if (i4 != 0) {
                        sdpManager.cancelLegacyResetTimeout(i3);
                        sdpManager.clearLegacyResetStatus(i3);
                        return -2;
                    }
                    sdpManager.handleLegacyResetPassword(i3);
                    verifyCredentialResponse.destroy();
                    Binder.restoreCallingIdentity(clearCallingIdentity2);
                    return 0;
                }
                return -1;
            }
            SDPLog.m43i("Reset password failed (Legacy token not found)");
            return -1;
        } finally {
            verifyCredentialResponse.destroy();
            Binder.restoreCallingIdentity(clearCallingIdentity2);
        }
    }

    public final DarManagerService getDarManagerService() {
        if (this.mDarManagerService == null) {
            this.mDarManagerService = (DarManagerService) ServiceManager.getService("dar");
        }
        return this.mDarManagerService;
    }

    public final ILockSettings getLockSettings() {
        if (this.mLockSettingsService == null) {
            this.mLockSettingsService = ILockSettings.Stub.asInterface(ServiceManager.getService("lock_settings"));
        }
        return this.mLockSettingsService;
    }

    public final SdpManagerImpl getSdpManager() {
        if (this.mSdpManager == null && getDarManagerService() != null) {
            this.mSdpManager = this.mDarManagerService.getSdpManager();
        }
        return this.mSdpManager;
    }

    public final void disableUnifiedLock(int i) {
        if (getDevicePolicyService().getProfileOwnerAsUser(i) == null) {
            DDLog.m34e(TAG, "failed to get active admin. failed to disallow unified password.", new Object[0]);
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                Bundle bundle = new Bundle();
                bundle.putInt("android.intent.extra.user_handle", i);
                bundle.putString("knox.container.proxy.EXTRA_KEY", "no_unified_password");
                Bundle sendPolicyUpdate = ContainerProxy.sendPolicyUpdate("knox.container.proxy.POLICY_ADD_USER_RESTRICTION", bundle);
                if (sendPolicyUpdate != null) {
                    DDLog.m33d(TAG, "disableUnifiedLock user result : " + sendPolicyUpdate.getInt("android.intent.extra.RETURN_RESULT", 1), new Object[0]);
                } else {
                    DDLog.m34e(TAG, "disableUnifiedLock user failed!! cannot get response ", new Object[0]);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public List getConfigurationTypeByName(ContextInfo contextInfo, String str) {
        int callingUid;
        String str2;
        if (contextInfo != null && contextInfo.mCallerUid > 0) {
            enforceSecurityPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CONTAINER")));
            callingUid = contextInfo.mCallerUid;
        } else {
            callingUid = Binder.getCallingUid();
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
            for (KnoxConfigurationType knoxConfigurationType : this.mTypeList) {
                if (knoxConfigurationType.getName().equals(str)) {
                    Log.d(TAG, "getConfigurationTypeByName type " + str + " adminUid " + knoxConfigurationType.getAdminUid() + " callingUid " + callingUid);
                    if ("com.samsung.android.knox.containercore".equals(str2) || "com.android.managedprovisioning".equals(str2) || callingUid == 1000 || knoxConfigurationType.getAdminUid() == 0 || knoxConfigurationType.getAdminUid() == callingUid) {
                        return Arrays.asList(knoxConfigurationType);
                    }
                    return Arrays.asList(knoxConfigurationType);
                }
            }
            return null;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public String getCustomResource(int i, String str) {
        String str2;
        String str3 = "/data/misc/container3.0/" + i + "/";
        if ("custom-container-icon".equals(str)) {
            str2 = str3 + "profileIcon.png";
        } else if ("custom-badge-icon".equals(str)) {
            str2 = str3 + "badgeIcon.png";
        } else if ("custom-name-icon".equals(str)) {
            str2 = str3 + "nameIcon.png";
        } else {
            str2 = null;
        }
        if (DEBUG) {
            Log.d(TAG, "PATH = " + str2);
        }
        if (str2 != null && new File(str2).exists()) {
            return str2;
        }
        KnoxConfigurationType filterTypeByContainerId = filterTypeByContainerId(i);
        if (filterTypeByContainerId != null && filterTypeByContainerId.isCustomizedContainerEnabled()) {
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

    public final boolean processConfigurationType(KnoxConfigurationType knoxConfigurationType) {
        String name = knoxConfigurationType.getName();
        if (name == null || "".equals(name)) {
            return false;
        }
        if (!knoxConfigurationType.isDefaultConfigType()) {
            knoxConfigurationType.setContainerLayout(-1);
            if (knoxConfigurationType instanceof ContainerModeConfigurationType) {
                knoxConfigurationType.allowLayoutSwitching(false);
            } else if (knoxConfigurationType instanceof LightweightConfigurationType) {
                String folderDisabledChangeLayout = ((LightweightConfigurationType) knoxConfigurationType).getFolderDisabledChangeLayout();
                if (folderDisabledChangeLayout != null && "true".compareTo(folderDisabledChangeLayout) == 0) {
                    knoxConfigurationType.allowLayoutSwitching(false);
                } else {
                    knoxConfigurationType.allowLayoutSwitching(true);
                }
            } else {
                knoxConfigurationType.allowLayoutSwitching(true);
            }
        } else {
            int containerLayout = knoxConfigurationType.getContainerLayout();
            if (containerLayout != 1 && containerLayout != 2) {
                knoxConfigurationType.setContainerLayout(1);
            }
        }
        return true;
    }

    public boolean addConfigurationType(ContextInfo contextInfo, List list) {
        int callingUid;
        KnoxConfigurationType knoxConfigurationType;
        enforceCallingCheckPermission(mContext, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CONTAINER")), "Activate Container permission");
        if (contextInfo == null || (callingUid = contextInfo.mCallerUid) <= 0) {
            callingUid = Binder.getCallingUid();
        }
        if (list == null || list.isEmpty() || (knoxConfigurationType = (KnoxConfigurationType) list.get(0)) == null) {
            return false;
        }
        try {
            Log.d(TAG, "Parameter name :" + knoxConfigurationType.getName());
            Log.d(TAG, "getConfigurationTypeByName value :" + getConfigurationTypeByName(contextInfo, knoxConfigurationType.getName()));
            if (processConfigurationType(knoxConfigurationType) && getConfigurationTypeByName(contextInfo, knoxConfigurationType.getName()) == null) {
                return addConfigurationTypeToList(callingUid, knoxConfigurationType);
            }
        } catch (IOException e) {
            Log.e(TAG, "IOException : " + Log.getStackTraceString(e));
        }
        return false;
    }

    public final boolean addConfigurationTypeToList(int i, KnoxConfigurationType knoxConfigurationType) {
        if (!processNewTypeObject(i, knoxConfigurationType)) {
            return false;
        }
        this.mTypeList.add(knoxConfigurationType);
        return true;
    }

    public boolean removeConfigurationType(ContextInfo contextInfo, String str) {
        int callingUid;
        enforceSecurityPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CONTAINER")));
        if (contextInfo == null || (callingUid = contextInfo.mCallerUid) <= 0) {
            callingUid = Binder.getCallingUid();
        }
        return removeConfigurationTypeInternal(callingUid, str);
    }

    public final boolean removeConfigurationTypeInternal(int i, String str) {
        synchronized (this.mTypeList) {
            KnoxConfigurationType filterType = filterType(i, str);
            if (filterType == null || i == 0 || !(filterType.getPersonaList() == null || filterType.getPersonaList().size() == 0)) {
                return false;
            }
            return removeConfigurationTypeInternal(filterType);
        }
    }

    public final boolean removeConfigurationTypeInternal(KnoxConfigurationType knoxConfigurationType) {
        if (knoxConfigurationType == null || knoxConfigurationType.getAdminUid() == 0) {
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                String customBadgeIcon = knoxConfigurationType.getCustomBadgeIcon();
                boolean z = DEBUG;
                if (z) {
                    Log.d(TAG, "badge icon file : " + customBadgeIcon);
                }
                if (customBadgeIcon != null) {
                    boolean delete = new File(customBadgeIcon).delete();
                    Log.d(TAG, "badge icon file deletion status: " + delete);
                }
                String customHomeScreenWallpaper = knoxConfigurationType.getCustomHomeScreenWallpaper();
                if (z) {
                    Log.d(TAG, "getCustomHomeScreenWallpaper icon file : " + customHomeScreenWallpaper);
                }
                if (customHomeScreenWallpaper != null) {
                    boolean delete2 = new File(customHomeScreenWallpaper).delete();
                    Log.d(TAG, "home screen wall paper icon file deletion status: " + delete2);
                }
                String customizedContainerNameIcon = knoxConfigurationType.getCustomizedContainerNameIcon();
                if (z) {
                    Log.d(TAG, "getCustomizedContainerNameIcon icon file : " + customizedContainerNameIcon);
                }
                if (customizedContainerNameIcon != null) {
                    boolean delete3 = new File(customizedContainerNameIcon).delete();
                    Log.d(TAG, "home screen wall paper icon file deletion status: " + delete3);
                }
                String customizedContainerIcon = knoxConfigurationType.getCustomizedContainerIcon();
                Log.d(TAG, "getECIcon icon file : " + customizedContainerIcon);
                if (customizedContainerIcon != null) {
                    boolean delete4 = new File(customizedContainerIcon).delete();
                    Log.d(TAG, "ec icon file deletion status: " + delete4);
                }
                String customizedContainerBadge = knoxConfigurationType.getCustomizedContainerBadge();
                Log.d(TAG, "getECBadge icon file : " + customizedContainerBadge);
                if (customizedContainerBadge != null) {
                    boolean delete5 = new File(customizedContainerBadge).delete();
                    Log.d(TAG, "ecbadge icon file deletion status: " + delete5);
                }
                String customLockScreenWallpaper = knoxConfigurationType.getCustomLockScreenWallpaper();
                if (z) {
                    Log.d(TAG, "getCustomLockScreenWallpaper icon file : " + customLockScreenWallpaper);
                }
                if (customLockScreenWallpaper != null) {
                    boolean delete6 = new File(customLockScreenWallpaper).delete();
                    Log.d(TAG, "lock screen wall paper icon file deletion status: " + delete6);
                }
                String customStatusIcon = knoxConfigurationType.getCustomStatusIcon();
                if (z) {
                    Log.d(TAG, "getCustomStatusIcon icon file : " + customStatusIcon);
                }
                if (customStatusIcon != null) {
                    boolean delete7 = new File(customStatusIcon).delete();
                    Log.d(TAG, "custom status icon file deletion status: " + delete7);
                }
                if (knoxConfigurationType instanceof LightweightConfigurationType) {
                    String folderHeaderIcon = ((LightweightConfigurationType) knoxConfigurationType).getFolderHeaderIcon();
                    Log.d(TAG, "getFolderHeaderIcon icon file : " + folderHeaderIcon);
                    if (folderHeaderIcon != null) {
                        boolean delete8 = new File(folderHeaderIcon).delete();
                        Log.d(TAG, "folder header icon file deletion status: " + delete8);
                    }
                }
                this.mTypeList.remove(knoxConfigurationType);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            } catch (Exception e) {
                Log.e(TAG, "IOException : " + Log.getStackTraceString(e));
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:111:0x0596  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x062f  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x067c  */
    /* JADX WARN: Removed duplicated region for block: B:135:0x06a2  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x06c0  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x06c2  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x06ca  */
    /* JADX WARN: Removed duplicated region for block: B:144:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:146:0x0675  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x0627  */
    /* JADX WARN: Removed duplicated region for block: B:155:0x058e  */
    /* JADX WARN: Removed duplicated region for block: B:184:0x0226  */
    /* JADX WARN: Removed duplicated region for block: B:185:0x01f0  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x01cf  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0201  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0441  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean processNewTypeObject(int i, KnoxConfigurationType knoxConfigurationType) {
        String str;
        Point point;
        String str2;
        String str3;
        String str4;
        String str5;
        String str6;
        String str7;
        String str8;
        String str9;
        LightweightConfigurationType lightweightConfigurationType;
        String folderHeaderIcon;
        String str10;
        Bitmap createScaledBitmap;
        byte[] readECFile;
        Bitmap decodeByteArray;
        int intrinsicWidth;
        int intrinsicHeight;
        String str11;
        StringBuilder sb;
        Bitmap createScaledBitmap2;
        String str12;
        int userId = UserHandle.getUserId(i);
        knoxConfigurationType.setAdminUid(i);
        knoxConfigurationType.setUserId(userId);
        knoxConfigurationType.setVersion(getDeviceFirmwareVersion());
        Point point2 = new Point();
        Point point3 = new Point();
        point3.set(90, 17);
        float dimension = mContext.getResources().getDimension(R.dimen.keyguard_muliuser_selector_margin);
        float dimension2 = mContext.getResources().getDimension(R.dimen.keyguard_lockscreen_status_line_font_size);
        Log.d(TAG, "app_icon_width app_icon_height" + dimension + " " + dimension2);
        ((WindowManager) mContext.getSystemService("window")).getDefaultDisplay().getSize(point2);
        Log.e(TAG, "Screen dimension: " + point2.x + " " + point2.y);
        File file = new File("/data/misc/container2.0/");
        if (file.exists() || file.mkdirs()) {
            file.setReadable(true, false);
            file.setWritable(true);
            file.setExecutable(true, false);
            File file2 = new File("/data/misc/container2.0/icon/");
            if (file2.exists() || file2.mkdirs()) {
                file2.setReadable(true, false);
                file2.setWritable(true);
                file2.setExecutable(true, false);
                String str13 = "/data/misc/container2.0/icon/" + knoxConfigurationType.getUserId() + "_" + knoxConfigurationType.getAdminUid() + "_" + knoxConfigurationType.getName() + "_";
                RCPManagerService rCPManagerService = (RCPManagerService) ServiceManager.getService("rcp");
                if (knoxConfigurationType.getCustomBadgeIcon() != null) {
                    String str14 = str13 + "CustomBadgeIcon.png";
                    boolean convertImageFormat = LSOUtils.convertImageFormat(knoxConfigurationType.getCustomBadgeIcon(), Bitmap.CompressFormat.PNG, str14, (Point) null);
                    if (DEBUG) {
                        Log.d(TAG, "Dest image paths: " + str14 + " status value:" + convertImageFormat);
                    }
                    if (!convertImageFormat) {
                        str14 = null;
                    }
                    knoxConfigurationType.setCustomBadgeIcon(str14);
                }
                if (knoxConfigurationType.getCustomHomeScreenWallpaper() != null) {
                    String customHomeScreenWallpaper = knoxConfigurationType.getCustomHomeScreenWallpaper();
                    point = point3;
                    if (customHomeScreenWallpaper != null) {
                        str2 = " status value:";
                        if (customHomeScreenWallpaper.contains("CustomHomeWallpaper-b2b.jpg") || customHomeScreenWallpaper.contains("DefaultHomeWallpaper.jpeg")) {
                            str12 = str13 + "DefaultHomeWallpaper.jpeg";
                            if (DEBUG) {
                                str = "containerBadgeModified.png";
                            } else {
                                String str15 = TAG;
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append("Dest image paths: ");
                                sb2.append(str12);
                                sb2.append(" and source path ");
                                str = "containerBadgeModified.png";
                                sb2.append(knoxConfigurationType.getCustomHomeScreenWallpaper());
                                Log.d(str15, sb2.toString());
                            }
                            if (!new File(knoxConfigurationType.getCustomHomeScreenWallpaper()).exists()) {
                                long clearCallingIdentity = Binder.clearCallingIdentity();
                                if (rCPManagerService.copyFileInternal(0, knoxConfigurationType.getCustomHomeScreenWallpaper(), 0, str12) != 0) {
                                    Log.d(TAG, "getCustomHomeScreenWallpaper::Copying file is failed");
                                } else {
                                    setFilePermission(str12);
                                    Log.d(TAG, "getCustomHomeScreenWallpaper::Copying file is processed");
                                }
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                            } else {
                                Log.d(TAG, "source file " + knoxConfigurationType.getCustomHomeScreenWallpaper() + " doesn't exist");
                                str12 = null;
                            }
                            knoxConfigurationType.setCustomHomeScreenWallpaper(str12);
                        }
                    } else {
                        str2 = " status value:";
                    }
                    str12 = str13 + "CustomHomeScreenWallpaper.png";
                    if (DEBUG) {
                    }
                    if (!new File(knoxConfigurationType.getCustomHomeScreenWallpaper()).exists()) {
                    }
                    knoxConfigurationType.setCustomHomeScreenWallpaper(str12);
                } else {
                    str = "containerBadgeModified.png";
                    point = point3;
                    str2 = " status value:";
                }
                if (knoxConfigurationType.getCustomizedContainerNameIcon() != null) {
                    knoxConfigurationType.getCustomizedContainerNameIcon();
                    String str16 = str13 + "NameIcon.png";
                    if (DEBUG) {
                        Log.d(TAG, "Dest image paths: " + str16 + " and source path " + knoxConfigurationType.getCustomizedContainerNameIcon());
                    }
                    if (new File(knoxConfigurationType.getCustomizedContainerNameIcon()).exists()) {
                        long clearCallingIdentity2 = Binder.clearCallingIdentity();
                        if (rCPManagerService.copyFileInternal(0, knoxConfigurationType.getCustomizedContainerNameIcon(), 0, str16) != 0) {
                            Log.d(TAG, "getCustomizedContainerNameIcon::Copying file is failed");
                        } else {
                            setFilePermission(str16);
                            Log.d(TAG, "getCustomizedContainerNameIcon::Copying file is processed");
                        }
                        Binder.restoreCallingIdentity(clearCallingIdentity2);
                    } else {
                        Log.d(TAG, "source file " + knoxConfigurationType.getCustomizedContainerNameIcon() + " doesn't exist");
                        str16 = null;
                    }
                    knoxConfigurationType.setCustomizedContainerNameIcon(str16);
                }
                if (knoxConfigurationType.getCustomizedContainerIcon() != null) {
                    knoxConfigurationType.getCustomizedContainerIcon();
                    String str17 = str13 + "containerIconTemp.png";
                    if (new File(knoxConfigurationType.getCustomizedContainerIcon()).exists()) {
                        long clearCallingIdentity3 = Binder.clearCallingIdentity();
                        if (rCPManagerService.copyFileInternal(0, knoxConfigurationType.getCustomizedContainerIcon(), 0, str17) != 0) {
                            Log.d(TAG, "getCustomizedContainerIcon::Copying file is failed");
                        } else {
                            setFilePermission(str17);
                            Log.d(TAG, "getCustomizedContainerIcon::Copying file is processed");
                        }
                        Binder.restoreCallingIdentity(clearCallingIdentity3);
                        knoxConfigurationType.setCustomizedContainerIcon(str17);
                    } else {
                        Log.d(TAG, "source file " + knoxConfigurationType.getCustomizedContainerIcon() + " doesn't exist");
                        knoxConfigurationType.setCustomizedContainerIcon((String) null);
                        str17 = null;
                    }
                    try {
                        readECFile = readECFile(str17);
                    } catch (Exception e) {
                        e = e;
                        str3 = " and source path ";
                    }
                    if (readECFile != null) {
                        str3 = " and source path ";
                        try {
                            str5 = "Dest image paths: ";
                            try {
                                BitmapDrawable bitmapDrawable = new BitmapDrawable(mContext.getResources(), BitmapFactory.decodeByteArray(readECFile, 0, readECFile.length));
                                decodeByteArray = BitmapFactory.decodeByteArray(readECFile, 0, readECFile.length);
                                intrinsicWidth = bitmapDrawable.getIntrinsicWidth();
                                intrinsicHeight = bitmapDrawable.getIntrinsicHeight();
                                str11 = TAG;
                                sb = new StringBuilder();
                                str4 = " doesn't exist";
                            } catch (Exception e2) {
                                e = e2;
                                str4 = " doesn't exist";
                            }
                        } catch (Exception e3) {
                            e = e3;
                            str4 = " doesn't exist";
                            str5 = "Dest image paths: ";
                            e.printStackTrace();
                            if (knoxConfigurationType.getCustomizedContainerBadge() == null) {
                            }
                            if (knoxConfigurationType.getCustomLockScreenWallpaper() == null) {
                            }
                            if (knoxConfigurationType.getCustomStatusIcon() == null) {
                            }
                            if (knoxConfigurationType instanceof LightweightConfigurationType) {
                            }
                            if (DEBUG) {
                            }
                        }
                        try {
                            sb.append("containerIcon dimensions ");
                            sb.append(intrinsicWidth);
                            sb.append(" ");
                            sb.append(intrinsicHeight);
                            Log.d(str11, sb.toString());
                            int i2 = (int) dimension;
                            if ((intrinsicWidth > i2 || intrinsicHeight > ((int) dimension2)) && dimension2 > DisplayPowerController2.RATE_FROM_DOZE_TO_ON && dimension2 > DisplayPowerController2.RATE_FROM_DOZE_TO_ON && (createScaledBitmap2 = Bitmap.createScaledBitmap(decodeByteArray, i2, (int) dimension2, false)) != null) {
                                Log.d(TAG, "resizedBitmap getCustomizedContainerIcon !null ");
                                if (Boolean.valueOf(writeFile(createScaledBitmap2, str13 + "containerIconModified.png")).booleanValue()) {
                                    Log.d(TAG, "success getCustomizedContainerIcon ");
                                    knoxConfigurationType.setCustomizedContainerIcon(str13 + "containerIconModified.png");
                                    if (DEBUG) {
                                        Log.d(TAG, " icon file : " + str17);
                                    }
                                    if (str17 != null) {
                                        boolean delete = new File(str17).delete();
                                        Log.d(TAG, "container icon file deletion status: " + delete);
                                    }
                                }
                            }
                        } catch (Exception e4) {
                            e = e4;
                            e.printStackTrace();
                            if (knoxConfigurationType.getCustomizedContainerBadge() == null) {
                            }
                            if (knoxConfigurationType.getCustomLockScreenWallpaper() == null) {
                            }
                            if (knoxConfigurationType.getCustomStatusIcon() == null) {
                            }
                            if (knoxConfigurationType instanceof LightweightConfigurationType) {
                            }
                            if (DEBUG) {
                            }
                        }
                        if (knoxConfigurationType.getCustomizedContainerBadge() == null) {
                            knoxConfigurationType.getCustomizedContainerBadge();
                            String str18 = str13 + "containerBadgeTemp.png";
                            if (new File(knoxConfigurationType.getCustomizedContainerBadge()).exists()) {
                                long clearCallingIdentity4 = Binder.clearCallingIdentity();
                                if (rCPManagerService.copyFileInternal(0, knoxConfigurationType.getCustomizedContainerBadge(), 0, str18) != 0) {
                                    Log.d(TAG, "getCustomizedContainerBadge::Copying file is failed");
                                } else {
                                    setFilePermission(str18);
                                    Log.d(TAG, "getCustomizedContainerBadge::Copying file is processed");
                                }
                                Binder.restoreCallingIdentity(clearCallingIdentity4);
                                knoxConfigurationType.setCustomizedContainerBadge(str18);
                                str10 = str18;
                                str6 = str4;
                            } else {
                                String str19 = TAG;
                                StringBuilder sb3 = new StringBuilder();
                                sb3.append("source file ");
                                sb3.append(knoxConfigurationType.getCustomizedContainerBadge());
                                str6 = str4;
                                sb3.append(str6);
                                Log.d(str19, sb3.toString());
                                knoxConfigurationType.setCustomizedContainerBadge((String) null);
                                str10 = null;
                            }
                            try {
                                byte[] readECFile2 = readECFile(str10);
                                if (readECFile2 != null) {
                                    BitmapDrawable bitmapDrawable2 = new BitmapDrawable(mContext.getResources(), BitmapFactory.decodeByteArray(readECFile2, 0, readECFile2.length));
                                    Bitmap decodeByteArray2 = BitmapFactory.decodeByteArray(readECFile2, 0, readECFile2.length);
                                    int intrinsicWidth2 = bitmapDrawable2.getIntrinsicWidth();
                                    int intrinsicHeight2 = bitmapDrawable2.getIntrinsicHeight();
                                    Log.d(TAG, "containerBadge dimensions " + intrinsicWidth2 + " " + intrinsicHeight2);
                                    int i3 = (int) (dimension / 2.0f);
                                    if ((intrinsicWidth2 > i3 || intrinsicHeight2 > ((int) (dimension2 / 2.0f))) && dimension2 > DisplayPowerController2.RATE_FROM_DOZE_TO_ON && dimension2 > DisplayPowerController2.RATE_FROM_DOZE_TO_ON && (createScaledBitmap = Bitmap.createScaledBitmap(decodeByteArray2, i3, (int) (dimension2 / 2.0f), false)) != null) {
                                        Log.d(TAG, "resizedBitmap containerBadgeTemp !null ");
                                        StringBuilder sb4 = new StringBuilder();
                                        sb4.append(str13);
                                        String str20 = str;
                                        sb4.append(str20);
                                        if (Boolean.valueOf(writeFile(createScaledBitmap, sb4.toString())).booleanValue()) {
                                            Log.d(TAG, "success getCustomizedContainerBadge ");
                                            knoxConfigurationType.setCustomizedContainerBadge(str13 + str20);
                                            if (DEBUG) {
                                                Log.d(TAG, " badge file : " + str10);
                                            }
                                            if (str10 != null) {
                                                boolean delete2 = new File(str10).delete();
                                                Log.d(TAG, "badge icon file deletion status: " + delete2);
                                            }
                                        }
                                    }
                                }
                            } catch (Exception e5) {
                                e5.printStackTrace();
                            }
                        } else {
                            str6 = str4;
                        }
                        if (knoxConfigurationType.getCustomLockScreenWallpaper() == null) {
                            knoxConfigurationType.getCustomLockScreenWallpaper();
                            String str21 = str13 + "CustomisedLockScreenWallpaper.png";
                            if (DEBUG) {
                                String str22 = TAG;
                                StringBuilder sb5 = new StringBuilder();
                                str7 = str5;
                                sb5.append(str7);
                                sb5.append(str21);
                                sb5.append(str3);
                                sb5.append(knoxConfigurationType.getCustomLockScreenWallpaper());
                                Log.d(str22, sb5.toString());
                            } else {
                                str7 = str5;
                            }
                            if (new File(knoxConfigurationType.getCustomLockScreenWallpaper()).exists()) {
                                long clearCallingIdentity5 = Binder.clearCallingIdentity();
                                if (rCPManagerService.copyFileInternal(0, knoxConfigurationType.getCustomLockScreenWallpaper(), 0, str21) != 0) {
                                    Log.d(TAG, "getCustomLockScreenWallpaper::Copying file is failed");
                                } else {
                                    setFilePermission(str21);
                                    Log.d(TAG, "getCustomLockScreenWallpaper::Copying file is processed");
                                }
                                Binder.restoreCallingIdentity(clearCallingIdentity5);
                            } else {
                                Log.d(TAG, "source file " + knoxConfigurationType.getCustomLockScreenWallpaper() + str6);
                                str21 = null;
                            }
                            knoxConfigurationType.setCustomLockScreenWallpaper(str21);
                        } else {
                            str7 = str5;
                        }
                        if (knoxConfigurationType.getCustomStatusIcon() == null) {
                            String str23 = str13 + "CustomStatusIcon.png";
                            str9 = null;
                            boolean convertImageFormat2 = LSOUtils.convertImageFormat(knoxConfigurationType.getCustomStatusIcon(), Bitmap.CompressFormat.PNG, str23, (Point) null);
                            if (DEBUG) {
                                String str24 = TAG;
                                StringBuilder sb6 = new StringBuilder();
                                sb6.append(str7);
                                sb6.append(str23);
                                str8 = str2;
                                sb6.append(str8);
                                sb6.append(convertImageFormat2);
                                Log.d(str24, sb6.toString());
                            } else {
                                str8 = str2;
                            }
                            if (!convertImageFormat2) {
                                str23 = null;
                            }
                            knoxConfigurationType.setCustomStatusIcon(str23);
                        } else {
                            str8 = str2;
                            str9 = null;
                        }
                        if ((knoxConfigurationType instanceof LightweightConfigurationType) && (folderHeaderIcon = (lightweightConfigurationType = (LightweightConfigurationType) knoxConfigurationType).getFolderHeaderIcon()) != null) {
                            String str25 = str13 + "FolderHeaderIcon.png";
                            boolean convertImageFormat3 = LSOUtils.convertImageFormat(folderHeaderIcon, Bitmap.CompressFormat.PNG, str25, point);
                            if (DEBUG) {
                                Log.d(TAG, "Dest image path folder header icon: " + str25 + str8 + convertImageFormat3);
                            }
                            lightweightConfigurationType.setFolderHeaderIcon(convertImageFormat3 ? str9 : str25);
                        }
                        if (DEBUG) {
                            return true;
                        }
                        Log.d(TAG, "Images after copy in services: " + knoxConfigurationType.getCustomHomeScreenWallpaper() + " " + knoxConfigurationType.getCustomLockScreenWallpaper() + "  " + knoxConfigurationType.getCustomizedContainerNameIcon() + " " + knoxConfigurationType.getCustomizedContainerIcon() + " " + knoxConfigurationType.getCustomizedContainerBadge());
                        return true;
                    }
                }
                str3 = " and source path ";
                str4 = " doesn't exist";
                str5 = "Dest image paths: ";
                if (knoxConfigurationType.getCustomizedContainerBadge() == null) {
                }
                if (knoxConfigurationType.getCustomLockScreenWallpaper() == null) {
                }
                if (knoxConfigurationType.getCustomStatusIcon() == null) {
                }
                if (knoxConfigurationType instanceof LightweightConfigurationType) {
                    String str252 = str13 + "FolderHeaderIcon.png";
                    boolean convertImageFormat32 = LSOUtils.convertImageFormat(folderHeaderIcon, Bitmap.CompressFormat.PNG, str252, point);
                    if (DEBUG) {
                    }
                    lightweightConfigurationType.setFolderHeaderIcon(convertImageFormat32 ? str9 : str252);
                }
                if (DEBUG) {
                }
            } else {
                Log.e(TAG, "cannot create folder: " + file2);
                return false;
            }
        } else {
            if (DEBUG) {
                Log.e(TAG, "cannot create folder: " + file);
            }
            return false;
        }
    }

    public List getConfigurationType(ContextInfo contextInfo, int i) {
        int callingUid;
        if (contextInfo != null && contextInfo.mCallerUid > 0) {
            enforceContainerOwnershipPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CONTAINER")));
            callingUid = contextInfo.mCallerUid;
        } else {
            callingUid = Binder.getCallingUid();
        }
        try {
            int userId = UserHandle.getUserId(callingUid);
            if (getService() != null && getService().exists(userId)) {
                if (i == userId) {
                    return Arrays.asList(filterTypeByContainerId(i));
                }
                Log.d(TAG, "Caller inside persona ? : false throw exception");
                throw new SecurityException("No priviledge on containerId ");
            }
            KnoxConfigurationType filterTypeByContainerId = filterTypeByContainerId(i);
            if (callingUid != 1000 && callingUid != 5250 && (filterTypeByContainerId == null || (filterTypeByContainerId.getAdminUid() != callingUid && filterTypeByContainerId.getAdminUid() != 0))) {
                return null;
            }
            return Arrays.asList(filterTypeByContainerId);
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + Log.getStackTraceString(e));
            return null;
        }
    }

    public List getDefaultConfigurationTypes() {
        checkCallerPermissionFor("getDefaultConfigurationTypes");
        ArrayList arrayList = null;
        for (KnoxConfigurationType knoxConfigurationType : this.mTypeList) {
            if (knoxConfigurationType.getAdminUid() == 0) {
                Log.d(TAG, "KnoxConfigurationType: name, uid: " + knoxConfigurationType.getName() + " " + knoxConfigurationType.getAdminUid());
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                arrayList.add(knoxConfigurationType);
            }
        }
        return arrayList;
    }

    public ContainerCreationParams getContainerCreationParams(int i) {
        checkCallerPermissionFor("getContainerCreationParams");
        ContainerCreationParams creationParams = getCreationParams(i);
        if (creationParams != null) {
            return creationParams.clone();
        }
        return null;
    }

    public List getConfigurationTypes(ContextInfo contextInfo) {
        int callingUid;
        if (contextInfo != null && contextInfo.mCallerUid > 0) {
            enforceSecurityPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CONTAINER")));
            callingUid = contextInfo.mCallerUid;
        } else {
            callingUid = Binder.getCallingUid();
        }
        Log.d(TAG, "KnoxConfigurationType: input uid: " + callingUid);
        ArrayList arrayList = null;
        for (KnoxConfigurationType knoxConfigurationType : this.mTypeList) {
            if (knoxConfigurationType.getAdminUid() == callingUid || knoxConfigurationType.getAdminUid() == 0) {
                Log.d(TAG, "KnoxConfigurationType: name, uid: " + knoxConfigurationType.getName() + " " + knoxConfigurationType.getAdminUid());
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

    public boolean getEnforceAuthForContainer(ContextInfo contextInfo) {
        Log.e(TAG, "getEnforceAuthForContainer failed > returning true");
        return true;
    }

    public boolean isMultifactorAuthenticationEnforced(ContextInfo contextInfo) {
        Log.i(TAG, "isMultifactorAuthenticationEnforced is called....");
        PasswordPolicy passwordPolicy = (PasswordPolicy) ServiceManager.getService("password_policy");
        boolean z = false;
        if (passwordPolicy != null) {
            try {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                z = passwordPolicy.isMultifactorAuthenticationEnabled(contextInfo);
                Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (Exception e) {
                Log.w(TAG, "Failed at ContainerConfigurationPolicy API isMultifactorAuthenticationEnabled ", e);
            }
        }
        Log.i(TAG, "isMultifactorAuthenticationEnabled result - " + z);
        return z;
    }

    public boolean enforceMultifactorAuthentication(ContextInfo contextInfo, boolean z) {
        Log.i(TAG, "enforceMultifactorAuthentication is called....");
        ContextInfo enforceContainerOwnershipPermission = enforceContainerOwnershipPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CONTAINER")));
        PasswordPolicy passwordPolicy = (PasswordPolicy) ServiceManager.getService("password_policy");
        boolean z2 = false;
        if (passwordPolicy != null) {
            try {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                z2 = passwordPolicy.setMultifactorAuthenticationEnabled(enforceContainerOwnershipPermission, z);
                Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (Exception e) {
                Log.w(TAG, "Failed at ContainerConfigurationPolicy API enforceMultifactorAuthentication ", e);
            }
        }
        Log.i(TAG, "enforceMultifactorAuthentication result - " + z2);
        return z2;
    }

    public boolean resetContainerOnReboot(ContextInfo contextInfo, boolean z) {
        Log.e(TAG, "Not support Container Only mode");
        return false;
    }

    public boolean isResetContainerOnRebootEnabled(ContextInfo contextInfo) {
        UserInfo userInfo;
        boolean z = false;
        if (contextInfo == null) {
            return false;
        }
        enforceContainerOwnershipPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CONTAINER")));
        int i = contextInfo.mContainerId;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                Log.d(TAG, "isResetPersonaOnRebootEnabled personaId " + i);
                if (getUserManagerService() != null && (userInfo = getUserManagerService().getUserInfo(i)) != null) {
                    if ((userInfo.getAttributes() & 64) == 64) {
                        z = true;
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, "Failed at KnoxMUMContainerPolicy API isResetContainerOnRebootEnabled ", e);
            }
            return z;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void doSelfUninstall() {
        enforceCallingCheckPermission(mContext, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CONTAINER")), "Activate Container permission");
        final int callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            final String nameForUid = mContext.getPackageManager().getNameForUid(callingUid);
            new Thread() { // from class: com.android.server.enterprise.container.KnoxMUMContainerPolicy.5
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() {
                    if (KnoxMUMContainerPolicy.this.mActivityManager != null) {
                        KnoxMUMContainerPolicy.this.mActivityManager.forceStopPackageAsUser(nameForUid, UserHandle.getUserId(callingUid));
                    }
                    try {
                        Thread.sleep(500L);
                    } catch (InterruptedException unused) {
                    }
                    PackageManagerAdapter.getInstance(KnoxMUMContainerPolicy.mContext).deletePackageAsUser(nameForUid, UserHandle.getUserId(callingUid), 0);
                }
            }.start();
        } catch (Exception e) {
            Log.e(TAG, "Fail doSelfUninstall " + e.getMessage());
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0034 A[Catch: all -> 0x0048, Exception -> 0x004a, TryCatch #0 {Exception -> 0x004a, blocks: (B:4:0x0018, B:6:0x001f, B:8:0x0027, B:11:0x0034, B:15:0x003e), top: B:3:0x0018, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:15:0x003e A[Catch: all -> 0x0048, Exception -> 0x004a, TRY_LEAVE, TryCatch #0 {Exception -> 0x004a, blocks: (B:4:0x0018, B:6:0x001f, B:8:0x0027, B:11:0x0034, B:15:0x003e), top: B:3:0x0018, outer: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean enableExternalStorage(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceContainerOwnershipPermission = enforceContainerOwnershipPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CONTAINER")));
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z2 = false;
        try {
            try {
            } catch (Exception e) {
                Log.e(TAG, "Fail enableExternalStorage ", e);
            }
            if (z != isExternalStorageEnabled(enforceContainerOwnershipPermission)) {
                if (setFeatureAccessPermission(enforceContainerOwnershipPermission, "ExternalStorage", z)) {
                    notifyContainerServiceForPolicyUpdate("knox.container.proxy.POLICY_SDCARD_POLICY_CHANGED", enforceContainerOwnershipPermission.mContainerId);
                }
                if (!z) {
                    getPersonaManagerLocked().setAttributes(enforceContainerOwnershipPermission.mContainerId, 128);
                } else {
                    getPersonaManagerLocked().clearAttributes(enforceContainerOwnershipPermission.mContainerId, 128);
                }
                return z2;
            }
            z2 = true;
            if (!z) {
            }
            return z2;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean isExternalStorageEnabled(ContextInfo contextInfo) {
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

    public final void restartBluetooth() {
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        Log.d(TAG, "restartBluetooth called! ba = " + defaultAdapter);
        if (defaultAdapter == null || !defaultAdapter.isEnabled()) {
            return;
        }
        this.mRestart = true;
        defaultAdapter.disable();
    }

    public boolean enableBluetooth(ContextInfo contextInfo, boolean z, Bundle bundle) {
        boolean z2 = false;
        if (BluetoothAdapter.getDefaultAdapter() == null) {
            Log.d(TAG, "enableBluetooth: bluetooth adapter is null! BT not supported on this device!");
            return false;
        }
        ContextInfo enforceContainerOwnershipPermission = enforceContainerOwnershipPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CONTAINER")));
        if (enforceContainerOwnershipPermission == null) {
            return false;
        }
        int i = contextInfo.mContainerId;
        ComponentName componentName = new ComponentName("com.android.bluetooth", "com.android.bluetooth.opp.BluetoothOppLauncherActivity");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                z2 = setFeatureAccessPermission(enforceContainerOwnershipPermission, "Bluetooth", z);
                Log.d(TAG, "enableBluetooth status - " + z2);
                IPackageManager packageManager = AppGlobals.getPackageManager();
                if (packageManager != null) {
                    if (packageManager.getPackageInfo("com.android.bluetooth", 64L, i) == null && mContext != null) {
                        try {
                            installPackage("com.android.bluetooth", i);
                        } catch (Exception e) {
                            Log.d(TAG, "Exception occured in installing bluetooth package inside container: " + e.getMessage());
                        }
                    }
                    if (z) {
                        packageManager.setComponentEnabledSetting(componentName, 1, 0, i, (String) null);
                    } else {
                        packageManager.setComponentEnabledSetting(componentName, 2, 0, i, (String) null);
                    }
                }
                if (z2) {
                    restartBluetooth();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            return z2;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean isBluetoothEnabled(ContextInfo contextInfo) {
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

    public boolean isBluetoothEnabledBeforeFOTA(ContextInfo contextInfo) {
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

    public boolean enableNFC(ContextInfo contextInfo, boolean z, Bundle bundle) {
        ContextInfo enforceContainerOwnershipPermission = enforceContainerOwnershipPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CONTAINER")));
        boolean z2 = false;
        if (enforceContainerOwnershipPermission == null) {
            return false;
        }
        int i = contextInfo.mContainerId;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                z2 = setFeatureAccessPermission(enforceContainerOwnershipPermission, "NFC", z);
                Log.d(TAG, "enableNFC status - " + z2);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (z2) {
                Log.d(TAG, "broadcast NFC policy change event " + z + " for " + i);
                notifyPeripheralPolicyUpdate("com.samsung.android.knox.nfc.policy", i, z);
                String str = isSecureFolder(i) ? "com.samsung.android.securefolder.nfc.discovered" : "com.samsung.android.knox.nfc.discovered";
                if (z) {
                    addCrossProfileIntentFilter(i, str);
                } else {
                    clearCrossProfileIntentFilters(i, str);
                }
            }
            return z2;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public class CrossProfileIntentFilter {
        public IntentFilter filter;
        public final int flags;

        public CrossProfileIntentFilter(IntentFilter intentFilter, int i) {
            this.filter = (IntentFilter) Preconditions.checkNotNull(intentFilter);
            this.flags = i;
        }

        public final class Builder {
            public IntentFilter mFilter = new IntentFilter();
            public int mFlags;

            public Builder(int i) {
                this.mFlags = i;
            }

            public Builder addAction(String str) {
                this.mFilter.addAction(str);
                return this;
            }

            public Builder addDataType(String str) {
                try {
                    this.mFilter.addDataType(str);
                } catch (IntentFilter.MalformedMimeTypeException e) {
                    Log.d(KnoxMUMContainerPolicy.TAG, "MalformedMimeTypeException: " + e);
                }
                return this;
            }

            public Builder addDataScheme(String str) {
                this.mFilter.addDataScheme(str);
                return this;
            }

            public CrossProfileIntentFilter build() {
                return new CrossProfileIntentFilter(this.mFilter, this.mFlags);
            }
        }
    }

    public final void addCrossProfileIntentFilter(int i, String str) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                for (CrossProfileIntentFilter crossProfileIntentFilter : this.NFC_FILTERS) {
                    IPackageManager packageManager = AppGlobals.getPackageManager();
                    if (packageManager != null) {
                        packageManager.addCrossProfileIntentFilter(crossProfileIntentFilter.filter, str, 0, i, 0);
                    }
                }
            } catch (RemoteException e) {
                Log.d(TAG, "addCrossProfileIntentFilter RemoteException: " + e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void clearCrossProfileIntentFilters(int i, String str) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                IPackageManager packageManager = AppGlobals.getPackageManager();
                if (packageManager != null) {
                    packageManager.clearCrossProfileIntentFilters(0, str);
                }
            } catch (RemoteException e) {
                Log.d(TAG, "clearCrossProfileIntentFilters RemoteException: " + e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean enableUsbAccess(ContextInfo contextInfo, boolean z, Bundle bundle) {
        ContextInfo enforceContainerOwnershipPermission = enforceContainerOwnershipPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CONTAINER")));
        boolean z2 = false;
        if (enforceContainerOwnershipPermission == null) {
            return false;
        }
        int i = contextInfo.mContainerId;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                z2 = setFeatureAccessPermission(enforceContainerOwnershipPermission, "USB", z);
                Log.d(TAG, "enableUsbAccess status - " + z2);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return z2;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean isUsbAccessEnabled(ContextInfo contextInfo) {
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

    public boolean setContactsSharingEnabled(ContextInfo contextInfo, boolean z) {
        boolean z2 = false;
        if (enforceContainerOwnershipPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CONTAINER"))) == null) {
            return false;
        }
        int i = contextInfo.mContainerId;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                if (getDevicePolicyService() != null) {
                    getDevicePolicyService().setBluetoothContactSharingEnabledForKnox(i, z);
                    if (getDevicePolicyService().getBluetoothContactSharingEnabledForKnox(i) == z) {
                        z2 = true;
                    }
                }
                Log.d(TAG, "setContactsSharingEnabled value : " + z + ", status : " + z2);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return z2;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean isContactsSharingEnabled(ContextInfo contextInfo) {
        boolean z = false;
        if (contextInfo == null) {
            return false;
        }
        int i = contextInfo.mContainerId;
        if (SemPersonaManager.isSecureFolderId(i)) {
            ContentResolver contentResolver = mContext.getContentResolver();
            StringBuilder sb = new StringBuilder();
            sb.append("caller_id_to_show_");
            sb.append(getUserManagerService().getUserInfo(i).name);
            return Settings.System.getIntForUser(contentResolver, sb.toString(), 0, 0) == 1;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                if (getDevicePolicyService() != null) {
                    z = getDevicePolicyService().getBluetoothContactSharingEnabledForKnox(i);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return z;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void notifyContainerServiceForPolicyUpdate(String str, int i) {
        Bundle bundle = new Bundle();
        bundle.putInt("android.intent.extra.user_handle", i);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        ContainerProxy.sendPolicyUpdate(str, bundle);
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public final void notifyPeripheralPolicyUpdate(String str, int i, boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        Intent intent = new Intent(str);
        intent.putExtra(KnoxCustomManagerService.CONTAINER_ID_ZERO, i);
        intent.putExtra("com.sec.knox.container.extra.updated.value", z);
        mContext.sendBroadcast(intent);
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public boolean isNFCEnabled(ContextInfo contextInfo) {
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
            Log.d(TAG, "isNFCEnabled exception: " + Log.getStackTraceString(e));
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

    public final boolean setFeatureAccessPermission(ContextInfo contextInfo, String str, boolean z) {
        boolean putValuesNoUpdate;
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Integer.valueOf(contextInfo.mCallerUid));
        contentValues.put("featureType", str);
        if (this.mEdmStorageProvider.getCount("KnoxFeatureAccess", contentValues) > 0) {
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put("featureValue", z + "");
            putValuesNoUpdate = this.mEdmStorageProvider.putValues("KnoxFeatureAccess", contentValues2, contentValues);
        } else {
            contentValues.put("featureValue", z + "");
            putValuesNoUpdate = this.mEdmStorageProvider.putValuesNoUpdate("KnoxFeatureAccess", contentValues);
        }
        if (!putValuesNoUpdate) {
            return false;
        }
        Log.d(TAG, "setFeatureAccessPermission policy passed");
        return true;
    }

    public final boolean getFeatureAccessPermission(int i, String str) {
        String[] strArr = {"featureValue"};
        boolean z = false;
        try {
            int uid = UserHandle.getUid(i, this.mEdmStorageProvider.getMUMContainerOwnerUid(i));
            ContentValues contentValues = new ContentValues();
            contentValues.put("adminUid", Integer.valueOf(uid));
            contentValues.put("featureType", str);
            List valuesList = this.mEdmStorageProvider.getValuesList("KnoxFeatureAccess", strArr, contentValues);
            if (valuesList != null && valuesList.size() > 0) {
                Iterator it = valuesList.iterator();
                while (it.hasNext()) {
                    String asString = ((ContentValues) it.next()).getAsString("featureValue");
                    if (asString != null && asString.length() > 0) {
                        z = Boolean.parseBoolean(asString);
                    }
                }
                return z;
            }
            boolean equals = "LAYOUT_SWITCH".equals(str);
            if ("Bluetooth".equals(str) || "USB".equals(str) || "NFC".equals(str)) {
                return true;
            }
            return equals;
        } catch (Exception e) {
            Log.e(TAG, "getFeatureAccessPermission exception " + e);
            return false;
        }
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onAdminAdded(int i) {
        checkCallerPermissionFor("onAdminAdded");
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onAdminRemoved(int i) {
        checkCallerPermissionFor("onAdminRemoved");
        Log.d(TAG, "onAdminRemoval:" + i);
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onPreAdminRemoval(int i) {
        checkCallerPermissionFor("onPreAdminRemoval");
        Log.d(TAG, "onPreAdminRemoval:" + i);
        List containers = getContainers(i);
        if (containers != null) {
            Iterator it = containers.iterator();
            while (it.hasNext()) {
                int intValue = ((Integer) it.next()).intValue();
                Log.d(TAG, "onPreAdminRemoval: removing container " + intValue);
                getUserManagerService().removeUser(intValue);
            }
        }
        if (i != 0) {
            Iterator it2 = filterType(i).iterator();
            while (it2.hasNext()) {
                KnoxConfigurationType knoxConfigurationType = (KnoxConfigurationType) it2.next();
                if (knoxConfigurationType != null) {
                    Log.d(TAG, "onPreAdminRemoval: removing type" + knoxConfigurationType.getName());
                }
                removeConfigurationTypeInternal(knoxConfigurationType);
            }
        }
    }

    public final String getDeviceFirmwareVersion() {
        String str = this.mFirmwareVersion;
        if (str != null) {
            return str;
        }
        String str2 = SystemProperties.get("ro.build.PDA", "Unknown");
        Log.i(TAG, "1. pdaVersion = " + str2);
        String trimHiddenVersion = trimHiddenVersion(str2);
        Log.i(TAG, "2. pdaVersion = " + trimHiddenVersion);
        this.mFirmwareVersion = trimHiddenVersion;
        return trimHiddenVersion;
    }

    public final String trimHiddenVersion(String str) {
        Log.d(TAG, "trimHiddenVersion(" + str + ")");
        return str.indexOf(95) != -1 ? str.substring(0, str.indexOf(95)) : str;
    }

    public long getHibernationTimeout(ContextInfo contextInfo) {
        enforceContainerOwnershipPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CONTAINER")));
        if (contextInfo != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("cid", Integer.valueOf(contextInfo.mContainerId));
            contentValues.put("propertyName", "HibernationTimeout");
            contentValues.put("adminUid", Integer.valueOf(this.mEdmStorageProvider.getMUMContainerOwnerUid(contextInfo.mContainerId)));
            List valuesList = this.mEdmStorageProvider.getValuesList("CONTAINER_POLICY", new String[]{"propertyValue"}, contentValues);
            if (valuesList != null && valuesList.size() > 0) {
                Log.d(TAG, "time=- " + ((ContentValues) valuesList.get(0)).getAsString("propertyValue"));
                String asString = ((ContentValues) valuesList.get(0)).getAsString("propertyValue");
                if (asString == null || asString.equalsIgnoreCase("0")) {
                    return 0L;
                }
                return Long.parseLong(asString);
            }
            Log.e(TAG, "getHibernationTimeout failed to get value from DB > returning default value");
            return 5000L;
        }
        Log.e(TAG, "getHibernationTimeout failed > returning default value.");
        return 5000L;
    }

    public boolean setHibernationTimeout(ContextInfo contextInfo, long j) {
        enforceContainerOwnershipPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CONTAINER")));
        return false;
    }

    public boolean addNetworkSSID(ContextInfo contextInfo, String str) {
        ContextInfo enforceWifiPermission = enforceWifiPermission(contextInfo);
        Log.d(TAG, "addNetworkSSID - ssid : " + str);
        String validStr = getValidStr(str);
        if (validStr == null) {
            return false;
        }
        int i = enforceWifiPermission.mCallerUid;
        Set ssid = getSSID(i);
        if (ssid.add(convertToQuotedString(validStr))) {
            return saveBlockedList(i, ssid);
        }
        Log.e(TAG, "addNetworkSSID failed : already exist");
        return false;
    }

    public final boolean saveBlockedList(int i, Set set) {
        if (set.isEmpty()) {
            return this.mEdmStorageProvider.removeByAdmin("ContainerOnly_wifiAP", i);
        }
        StringBuilder sb = new StringBuilder();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            sb.append(((String) it.next()) + ",");
        }
        return this.mEdmStorageProvider.putString(i, "ContainerOnly_wifiAP", "wifiSSIDforKNOX", sb.toString());
    }

    public boolean removeNetworkSSID(ContextInfo contextInfo, String str) {
        ContextInfo enforceWifiPermission = enforceWifiPermission(contextInfo);
        Log.d(TAG, "removeNetworkSSID - SSID : " + str);
        String validStr = getValidStr(str);
        if (validStr == null) {
            Log.d(TAG, "removeNetworkSSID - invalid Str " + validStr);
            return false;
        }
        int i = enforceWifiPermission.mCallerUid;
        String convertToQuotedString = convertToQuotedString(validStr);
        Set ssid = getSSID(i);
        if (ssid.remove(convertToQuotedString)) {
            return saveBlockedList(i, ssid);
        }
        Log.e(TAG, "addNetworkSSID failed : no exist.");
        return false;
    }

    public List getNetworkSSID(ContextInfo contextInfo) {
        ContextInfo enforceWifiPermission = enforceWifiPermission(contextInfo);
        Log.d(TAG, "getNetworkSSID - adminUid : " + enforceWifiPermission.mCallerUid);
        LinkedList linkedList = new LinkedList();
        Iterator it = getSSID(enforceWifiPermission.mCallerUid).iterator();
        while (it.hasNext()) {
            linkedList.add(removeDoubleQuotes((String) it.next()));
        }
        return linkedList;
    }

    public final Set getSSID(int i) {
        String string = this.mEdmStorageProvider.getString(i, "ContainerOnly_wifiAP", "wifiSSIDforKNOX");
        HashSet hashSet = new HashSet();
        if (string != null && string.length() > 0) {
            for (String str : string.split(",")) {
                hashSet.add(str);
            }
        }
        return hashSet;
    }

    public boolean clearNetworkSSID(ContextInfo contextInfo) {
        ContextInfo enforceWifiPermission = enforceWifiPermission(contextInfo);
        Log.d(TAG, "clearNetworkSSID - admin UID : " + enforceWifiPermission.mCallerUid);
        return this.mEdmStorageProvider.deleteDataByFields("ContainerOnly_wifiAP", new String[]{"adminUid"}, new String[]{String.valueOf(enforceWifiPermission.mCallerUid)});
    }

    public static String convertToQuotedString(String str) {
        if (str == null) {
            return null;
        }
        int length = str.length();
        if (length > 1 && str.charAt(0) == '\"' && str.charAt(length - 1) == '\"') {
            return str;
        }
        return '\"' + str + '\"';
    }

    public static String removeDoubleQuotes(String str) {
        if (str == null) {
            return null;
        }
        int length = str.length();
        if (length <= 1 || str.charAt(0) != '\"') {
            return str;
        }
        int i = length - 1;
        return str.charAt(i) == '\"' ? str.substring(1, i) : str;
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
            printWriter.println("Permission Denial: can't dump KnoxContainerManager");
            return;
        }
        List users = this.mUms.getUsers(true);
        if (users == null) {
            Log.i(TAG, "persona list is null");
            return;
        }
        Iterator it = users.iterator();
        while (it.hasNext()) {
            dumpConfigurationForPersona(((UserInfo) it.next()).id, printWriter);
        }
    }

    public final void dumpConfigurationForPersona(int i, PrintWriter printWriter) {
        printWriter.println("Persona:" + i);
        KnoxConfigurationType filterTypeByContainerId = filterTypeByContainerId(i);
        if (filterTypeByContainerId != null) {
            printWriter.write(" Object dump :{ mName :" + filterTypeByContainerId.getName());
            printWriter.write(" mVersion :" + filterTypeByContainerId.getVersion());
            printWriter.write(" mPasswordMinimumNonLetters :" + filterTypeByContainerId.getPasswordMinimumNonLetters());
            printWriter.write(" mPasswordMinimumLetters : " + filterTypeByContainerId.getPasswordMinimumLetters());
            printWriter.write(" mPasswordMinimumNumeric : " + filterTypeByContainerId.getPasswordMinimumNumeric());
            printWriter.write(" mPasswordMinimumUpperCase : " + filterTypeByContainerId.getPasswordMinimumUpperCase());
            printWriter.write(" mPasswordMinimumLowerCase : " + filterTypeByContainerId.getPasswordMinimumLowerCase());
            printWriter.write(" mPasswordMinimumSymbols : " + filterTypeByContainerId.getPasswordMinimumSymbols());
            printWriter.write(" mPasswordQuality : " + filterTypeByContainerId.getPasswordQuality());
            printWriter.write(" mMaximumFailedPasswordsForWipe : " + filterTypeByContainerId.getMaximumFailedPasswordsForWipe());
            printWriter.write(" mManagedType : " + filterTypeByContainerId.getManagedType());
            printWriter.write(" mMaximumTimeToLock : " + filterTypeByContainerId.getMaximumTimeToLock());
            printWriter.write(" mCustomBadgeIcon : " + filterTypeByContainerId.getCustomBadgeIcon());
            printWriter.write(" mCustomHomeScreenWallpaper : " + filterTypeByContainerId.getCustomHomeScreenWallpaper());
            printWriter.write(" mEC : " + filterTypeByContainerId.isCustomizedContainerEnabled());
            printWriter.write(" mNameIcon : " + filterTypeByContainerId.getCustomizedContainerNameIcon());
            printWriter.write(" mECName  : " + filterTypeByContainerId.getCustomizedContainerName());
            printWriter.write(" mECIcon : " + filterTypeByContainerId.getCustomizedContainerIcon());
            printWriter.write(" mECBadge : " + filterTypeByContainerId.getCustomizedContainerBadge());
            printWriter.write(" mCustomLockScreenWallpaper : " + filterTypeByContainerId.getCustomLockScreenWallpaper());
            printWriter.write(" mCustomStatusLabel : " + filterTypeByContainerId.getCustomStatusLabel());
            printWriter.write(" mCustomStatusIcon : " + filterTypeByContainerId.getCustomStatusIcon());
            printWriter.write(" mAppInstallationList : " + filterTypeByContainerId.getAppInstallationList());
            printWriter.write(" mForbiddenStrings : " + filterTypeByContainerId.getForbiddenStrings());
            printWriter.write(" mProtectedList : " + filterTypeByContainerId.getProtectedPackageList());
            printWriter.write(" mGoogleAppsList : " + filterTypeByContainerId.getGoogleAppsList());
            printWriter.write(" mMaximumCharacterOccurences : " + filterTypeByContainerId.getMaximumCharacterOccurences());
            printWriter.write(" mMaximumCharacterSequenceLength : " + filterTypeByContainerId.getMaximumCharacterSequenceLength());
            printWriter.write(" mMaximumNumericSequenceLength : " + filterTypeByContainerId.getMaximumNumericSequenceLength());
            printWriter.write(" mPasswordMinimumLength : " + filterTypeByContainerId.getPasswordMinimumLength());
            printWriter.write(" mSimplePasswordEnabled : " + filterTypeByContainerId.getSimplePasswordEnabled());
            printWriter.write(" mMultifactorAuthEnabled : " + filterTypeByContainerId.isMultifactorAuthenticationEnforced());
            printWriter.write(" mPasswordPattern : " + filterTypeByContainerId.getRequiredPwdPatternRestrictions());
            printWriter.write(" mAllowMultiwindowMode : " + filterTypeByContainerId.isMultiwindowModeAllowed());
            printWriter.write(" mAllowTaskManager : " + filterTypeByContainerId.isTaskManagerAllowed());
            printWriter.write(" mAllowUSBDebugging : " + filterTypeByContainerId.isUSBDebuggingAllowed());
            printWriter.write(" RCP Data sync settings : ");
            dumpRCPSettings(printWriter, filterTypeByContainerId.getDataSyncPolicy());
            printWriter.write(" RCP Allow User change Data sync settings : ");
            dumpRCPSettings(printWriter, filterTypeByContainerId.getAllowChangeDataSyncPolicy());
            printWriter.write(" RCP Notification sync settings : ");
            dumpRCPSettings(printWriter, filterTypeByContainerId.getNotificationSyncPolicy());
            printWriter.write(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        }
    }

    public final void dumpRCPSettings(PrintWriter printWriter, HashMap hashMap) {
        Set<String> keySet = hashMap.keySet();
        if (keySet == null || keySet.isEmpty()) {
            return;
        }
        for (String str : keySet) {
            printWriter.write(" " + str + " {");
            List<Pair> list = (List) hashMap.get(str);
            if (list != null) {
                for (Pair pair : list) {
                    printWriter.write("  ( " + ((String) pair.first) + "," + ((String) pair.second) + " )");
                }
            }
            printWriter.write(" }");
        }
    }

    public final String getValidStr(String str) {
        if (str == null) {
            return null;
        }
        try {
            String trim = str.trim();
            if (trim.length() > 0) {
                return trim;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean setSettingsOptionEnabled(ContextInfo contextInfo, String str, boolean z) {
        Log.d(TAG, "setSettingsOptionEnabled()");
        boolean z2 = false;
        if (str == null || str.isEmpty()) {
            Log.e(TAG, "Error from setSettingsOptionEnabled(): option is null or empty");
            return false;
        }
        ContextInfo enforceContainerOwnershipPermission = enforceContainerOwnershipPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CONTAINER")));
        try {
            if (str.equals("option_callerinfo")) {
                int userId = UserHandle.getUserId(enforceContainerOwnershipPermission.mCallerUid);
                long clearCallingIdentity = Binder.clearCallingIdentity();
                ComponentName adminComponentName = SemPersonaManager.getAdminComponentName(userId);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                if (getDevicePolicyService() != null) {
                    getDevicePolicyService().setCrossProfileCallerIdDisabled(adminComponentName, !z);
                    z2 = true;
                }
            }
        } catch (Exception unused) {
            Log.e(TAG, "setSettingsOptionEnabled failed : result = false");
        }
        Log.d(TAG, "setSettingsOptionEnabled() : enable = " + z);
        return z2;
    }

    public boolean isSettingsOptionEnabled(ContextInfo contextInfo, String str) {
        Log.d(TAG, "isSettingsOptionEnabled");
        if (str == null || str.isEmpty()) {
            Log.e(TAG, "Error from isSettingsOptionEnabled(): option is null or empty");
            return false;
        }
        try {
            if (str.equals("option_callerinfo")) {
                int i = contextInfo.mContainerId;
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    try {
                        if (getUserManagerService().getUserInfo(i).isSuperLocked()) {
                            Log.d(TAG, "Target container is superlocked. return false for " + str);
                            return false;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (getDevicePolicyService() != null) {
                        boolean z = !getDevicePolicyService().getCrossProfileCallerIdDisabled(new UserHandle(i));
                        Log.d(TAG, "isSettingsOptionEnabled(): Return result: " + z);
                        return z;
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        } catch (Exception e2) {
            Log.e(TAG, "isSettingsOptionEnabled() exception: " + e2.getMessage());
        }
        return false;
    }

    public boolean isSettingsOptionEnabledInternal(int i, String str, boolean z) {
        boolean z2 = DEBUG;
        if (z2) {
            Log.d(TAG, "isSettingsOptionEnabledInternal for personaId=" + i + "; option=" + str);
        }
        if (str == null || str.isEmpty()) {
            if (z2) {
                Log.e(TAG, "Error from isSettingsOptionEnabledInternal: option is null or empty");
            }
            return false;
        }
        try {
            if (str.equals("option_callerinfo")) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    try {
                        if (getUserManagerService().getUserInfo(i).isSuperLocked()) {
                            Log.d(TAG, "Target container is superlocked. return false for " + str);
                            return false;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (getDevicePolicyService() != null) {
                        boolean z3 = !getDevicePolicyService().getCrossProfileCallerIdDisabled(new UserHandle(i));
                        if (DEBUG) {
                            Log.d(TAG, "isSettingsOptionEnabledInternal: Return result: " + z3);
                        }
                        return z3;
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            if (DEBUG) {
                Log.d(TAG, "isSettingsOptionEnabledInternal: no record found for " + i + ":  Return default value: " + z);
            }
            return z;
        } catch (Exception e2) {
            Log.d(TAG, "isSettingsOptionEnabledInternal: Exception " + e2.getMessage());
            return false;
        }
    }

    public final boolean removeShortcutFromPersonal(ContextInfo contextInfo, String str, String str2) {
        Log.i(TAG, " removeShortcutFromPersonal");
        ComponentName componentName = (str2 == null || str2.length() <= 0) ? null : new ComponentName(str, str2);
        Intent intent = new Intent("android.intent.action.MAIN");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (componentName == null) {
                intent.setPackage(str);
                ResolveInfo resolveInfo = this.mPackageManager.queryIntentActivities(intent, 0).get(0);
                if (resolveInfo == null) {
                    return false;
                }
                componentName = new ComponentName(str, resolveInfo.activityInfo.name);
                intent.setComponent(componentName);
            } else {
                intent.setComponent(componentName);
            }
            int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
            Bundle bundle = new Bundle();
            bundle.putParcelable("component", componentName);
            bundle.putInt("userid", callingOrCurrentUserId);
            mContext.getContentResolver().call(REMOVE_SHORTCUT_CONTENT_URI, "remove_shortcut", (String) null, bundle);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x0161 A[Catch: RemoteException -> 0x016a, all -> 0x0176, Exception -> 0x0178, Merged into TryCatch #1 {all -> 0x0176, Exception -> 0x0178, blocks: (B:13:0x004b, B:16:0x0056, B:18:0x005c, B:19:0x0063, B:21:0x006c, B:23:0x0082, B:25:0x009d, B:27:0x0117, B:40:0x0129, B:30:0x0138, B:32:0x0143, B:34:0x0161, B:38:0x016a, B:48:0x008c, B:56:0x0179), top: B:12:0x004b }, TRY_LEAVE] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean addShortcutToPersonal(ContextInfo contextInfo, String str, String str2) {
        ResolveInfo resolveInfo;
        Intent intent;
        Intent intent2;
        AndroidFuture androidFuture;
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        Log.i(TAG, " addShortcutToPersonal:" + callingOrCurrentUserId);
        Context createContextAsUser = Utils.createContextAsUser(mContext, str, 0, callingOrCurrentUserId);
        if (createContextAsUser == null) {
            Log.e(TAG, "contextAsUser is null. given package may not exist");
            return false;
        }
        if (this.mShortcutService == null) {
            IShortcutService asInterface = IShortcutService.Stub.asInterface(ServiceManager.getService("shortcut"));
            this.mShortcutService = asInterface;
            if (asInterface == null) {
                return false;
            }
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (this.mShortcutService.isRequestPinItemSupported(0, 1)) {
                ComponentName componentName = (str2 == null || str2.length() <= 0) ? null : new ComponentName(str, str2);
                Intent intent3 = new Intent("android.intent.action.MAIN");
                if (componentName == null) {
                    intent3.addCategory("android.intent.category.LAUNCHER");
                    intent3.setPackage(str);
                    resolveInfo = (ResolveInfo) this.mPackageManager.queryIntentActivitiesAsUser(intent3, 0, callingOrCurrentUserId).get(0);
                    if (resolveInfo != null) {
                        componentName = new ComponentName(str, resolveInfo.activityInfo.name);
                    }
                } else {
                    intent3.setComponent(componentName);
                    resolveInfo = (ResolveInfo) this.mPackageManager.queryIntentActivitiesAsUser(intent3, 0, callingOrCurrentUserId).get(0);
                }
                if (resolveInfo != null) {
                    String charSequence = resolveInfo.loadLabel(this.mPackageManager).toString();
                    Icon createWithBitmap = Icon.createWithBitmap(drawableToBitmap(resolveInfo.loadIcon(this.mPackageManager)));
                    Intent launchIntentForPackage = createContextAsUser.getPackageManager().getLaunchIntentForPackage(str);
                    launchIntentForPackage.setComponent(componentName);
                    Log.i(TAG, " label:" + charSequence + ",pkgName:" + str + ",userid" + createContextAsUser.getUserId());
                    ShortcutInfo.Builder icon = new ShortcutInfo.Builder(createContextAsUser, str).setShortLabel(charSequence).setIntent(launchIntentForPackage).setActivity(componentName).setIcon(createWithBitmap);
                    PersistableBundle persistableBundle = new PersistableBundle();
                    persistableBundle.putString("do_not_show_popup", "com.android.server.enterprise.application.ApplicationPolicy");
                    persistableBundle.putBoolean("add_to_shortcut_personal", true);
                    icon.setExtras(persistableBundle);
                    ShortcutInfo build = icon.build();
                    try {
                        AndroidFuture androidFuture2 = new AndroidFuture();
                        this.mShortcutService.createShortcutResultIntent(str, build, callingOrCurrentUserId, androidFuture2);
                        intent2 = (Intent) getFutureOrThrow(androidFuture2);
                        if (intent2 == null) {
                            try {
                                Log.e(TAG, "The current launcher doesn't support shortcuts.");
                                return false;
                            } catch (RemoteException unused) {
                                intent = intent2;
                                intent2 = intent;
                                PendingIntent broadcast = PendingIntent.getBroadcast(createContextAsUser, 0, intent2, 67108864);
                                Log.d(TAG, "<<<--->>> before calling requestPinShortcut");
                                androidFuture = new AndroidFuture();
                                this.mShortcutService.requestPinShortcut(str, build, broadcast.getIntentSender(), 0, androidFuture);
                                if (!Boolean.parseBoolean((String) getFutureOrThrow(androidFuture))) {
                                }
                                return true;
                            }
                        }
                    } catch (RemoteException unused2) {
                        intent = null;
                    }
                    PendingIntent broadcast2 = PendingIntent.getBroadcast(createContextAsUser, 0, intent2, 67108864);
                    Log.d(TAG, "<<<--->>> before calling requestPinShortcut");
                    try {
                        androidFuture = new AndroidFuture();
                        this.mShortcutService.requestPinShortcut(str, build, broadcast2.getIntentSender(), 0, androidFuture);
                        if (!Boolean.parseBoolean((String) getFutureOrThrow(androidFuture))) {
                            Log.e(TAG, "The current launcher doesn't support shortcuts.");
                            return false;
                        }
                    } catch (RemoteException unused3) {
                        Log.e(TAG, "Shortcut with the same ID exists and is disabled.");
                    }
                }
            }
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Exception occurred while adding home shortcut " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
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

    public boolean addHomeShortcutToPersonal(ContextInfo contextInfo, String str, String str2) {
        if (!SemPersonaManager.isKnoxVersionSupported(FrameworkStatsLog.f392x1636cc72)) {
            Log.d(TAG, "Only above Knox version 2.7 can support");
            return false;
        }
        enforceContainerOwnershipPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CONTAINER")));
        return addShortcutToPersonal(contextInfo, str, str2);
    }

    public boolean deleteHomeShortcutFromPersonal(ContextInfo contextInfo, String str, String str2) {
        if (!SemPersonaManager.isKnoxVersionSupported(FrameworkStatsLog.f392x1636cc72)) {
            Log.d(TAG, "Only above Knox version 2.7 can support");
            return false;
        }
        return removeShortcutFromPersonal(enforceContainerOwnershipPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CONTAINER"))), str, str2);
    }

    public boolean isPackageAllowedToAccessExternalSdcard(ContextInfo contextInfo, int i) {
        Log.d(TAG, "isPackageAllowedToAccessExternalSdcard");
        return false;
    }

    public PersonaManagerService getPersonaManagerLocked() {
        return (PersonaManagerService) ServiceManager.getService("persona");
    }

    public boolean isEmergencyModeSupported() {
        synchronized (this.mProvisioningLock) {
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
        }
    }

    public byte[] readECFile(String str) {
        if (str == null || str.length() == 0) {
            Log.d(TAG, "filename is null  " + ((Object) null));
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
            Log.d(TAG, "Bytes : " + bArr);
            return bArr;
        } catch (Throwable th) {
            fileInputStream.close();
            throw th;
        }
    }

    public final Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        Bitmap createBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return createBitmap;
    }

    public boolean setFIDOInfo(ContextInfo contextInfo, Bundle bundle) {
        boolean putValuesNoUpdate;
        Log.d(TAG, "setFIDOInfo()");
        enforceContainerOwnershipPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CONTAINER")));
        try {
            if (bundle == null) {
                Log.e(TAG, "setFIDOinfo() : fidoinfo is null. remove row.");
                HashMap hashMap = new HashMap();
                hashMap.put("adminUid", Integer.toString(contextInfo.mCallerUid));
                hashMap.put("cid", Integer.toString(contextInfo.mContainerId));
                this.mEdmStorageProvider.removeByFields("KnoxFIDOSettingTable", hashMap);
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
                putValuesNoUpdate = this.mEdmStorageProvider.putValues("KnoxFIDOSettingTable", contentValues2, contentValues);
            } else {
                contentValues.put("request", string);
                contentValues.put("respond", string2);
                putValuesNoUpdate = this.mEdmStorageProvider.putValuesNoUpdate("KnoxFIDOSettingTable", contentValues);
            }
            if (!putValuesNoUpdate) {
                return false;
            }
            Log.d(TAG, "setFIDOInfo passed");
            return true;
        } catch (Exception e) {
            Log.d(TAG, "setFIDOInfo() exception: " + e.getMessage());
            return false;
        }
    }

    public Bundle getFIDOInfo(ContextInfo contextInfo) {
        Log.d(TAG, "getFIDOInfo()");
        enforceContainerOwnershipPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CONTAINER")));
        int i = contextInfo.mCallerUid;
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("cid", Integer.valueOf(contextInfo.mContainerId));
            if (this.mEdmStorageProvider.getCount("KnoxFIDOSettingTable", contentValues) == 0) {
                Log.d(TAG, "getFIDOInfo(): no record. Return null");
                return null;
            }
            Bundle bundle = new Bundle();
            bundle.putString("fido_request_uri", this.mEdmStorageProvider.getString("KnoxFIDOSettingTable", "request", contentValues));
            bundle.putString("fido_response_uri", this.mEdmStorageProvider.getString("KnoxFIDOSettingTable", "respond", contentValues));
            return bundle;
        } catch (Exception e) {
            Log.d(TAG, "getFIDOInfo() exception: " + e.getMessage());
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0030  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean writeFile(Bitmap bitmap, String str) {
        boolean z;
        FileOutputStream fileOutputStream;
        FileOutputStream fileOutputStream2 = null;
        try {
            try {
                fileOutputStream = new FileOutputStream(str);
            } catch (Exception e) {
                e = e;
            }
        } catch (Throwable th) {
            th = th;
        }
        try {
            z = bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            try {
                fileOutputStream.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        } catch (Exception e3) {
            e = e3;
            fileOutputStream2 = fileOutputStream;
            e.printStackTrace();
            if (fileOutputStream2 != null) {
                try {
                    fileOutputStream2.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
            z = false;
            if (z) {
            }
            return z;
        } catch (Throwable th2) {
            th = th2;
            fileOutputStream2 = fileOutputStream;
            if (fileOutputStream2 != null) {
                try {
                    fileOutputStream2.close();
                } catch (IOException e5) {
                    e5.printStackTrace();
                }
            }
            throw th;
        }
        if (z) {
            setFilePermission(str);
        }
        return z;
    }

    public final void setFilePermission(String str) {
        File file = new File(str);
        file.setReadable(true, false);
        file.setWritable(true);
        file.setExecutable(true, false);
    }

    public boolean addSecureKeyPad(int i, String str) {
        boolean z;
        checkCallerPermissionFor("addSecureKeyPad");
        Log.d(TAG, "addSecureKeyPad called: " + str);
        List secureKeyPad = getSecureKeyPad(i);
        if (secureKeyPad != null && secureKeyPad.contains(str)) {
            Log.d(TAG, "entry exists: " + str);
            return true;
        }
        ContextInfo contextInfo = new ContextInfo(this.mEdmStorageProvider.getMUMContainerOwnerUid(i), i);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                z = setFeatureAccessIME(contextInfo, str);
            } catch (Exception e) {
                e.printStackTrace();
                Binder.restoreCallingIdentity(clearCallingIdentity);
                z = false;
            }
            Log.d(TAG, "addSecureKeyPad return value:  " + z);
            return z;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean removeSecureKeyPad(int i, String str) {
        checkCallerPermissionFor("removeSecureKeyPad");
        Log.d(TAG, "removeSecureKeyPad called: " + str);
        EdmStorageProvider edmStorageProvider = new EdmStorageProvider(mContext);
        List secureKeyPad = getSecureKeyPad(i);
        if (secureKeyPad == null) {
            Log.e(TAG, "list is empty");
            return true;
        }
        if (!secureKeyPad.contains(str)) {
            Log.d(TAG, "cannot find: " + str);
            return true;
        }
        ListIterator listIterator = secureKeyPad.listIterator();
        while (true) {
            if (!listIterator.hasNext()) {
                break;
            }
            if (((String) listIterator.next()).equals(str)) {
                Log.d(TAG, "removed: " + str);
                listIterator.remove();
                break;
            }
        }
        Log.d(TAG, "next remove from DB");
        ContextInfo contextInfo = new ContextInfo(edmStorageProvider.getMUMContainerOwnerUid(i), i);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z = false;
        try {
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put("adminUid", Integer.valueOf(contextInfo.mCallerUid));
                contentValues.put("featureType", "KEYPAD");
                if (secureKeyPad.size() == 0) {
                    Log.d(TAG, "remove secure key pad entry in DB: " + i);
                    if (edmStorageProvider.delete("KnoxFeatureAccess", contentValues) > 0) {
                        Log.d(TAG, "entry deleted");
                    } else {
                        Log.e(TAG, "failed to delete");
                    }
                } else {
                    String str2 = "";
                    int size = secureKeyPad.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        str2 = i2 == size - 1 ? str2 + ((String) secureKeyPad.get(i2)) : str2 + ((String) secureKeyPad.get(i2)) + ",";
                    }
                    ContentValues contentValues2 = new ContentValues();
                    contentValues2.put("featureValue", str2);
                    z = edmStorageProvider.putValues("KnoxFeatureAccess", contentValues2, contentValues);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            Log.d(TAG, "addSecureKeyPad return value:  " + z);
            return z;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public List getSecureKeyPad(int i) {
        checkCallerPermissionFor("getSecureKeyPad");
        Log.d(TAG, "getSecureKeyPad called: userId=" + i);
        ContextInfo contextInfo = new ContextInfo(this.mEdmStorageProvider.getMUMContainerOwnerUid(i), i);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        ArrayList arrayList = null;
        try {
            try {
                String featureAccessIME = getFeatureAccessIME(contextInfo);
                Log.d(TAG, "getSecureKeyPad: " + featureAccessIME);
                if (featureAccessIME != null) {
                    arrayList = new ArrayList(Arrays.asList(featureAccessIME.split(",")));
                } else {
                    Log.d(TAG, "no entry found for container : " + contextInfo.mContainerId);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return arrayList;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean setFeatureAccessIME(ContextInfo contextInfo, String str) {
        boolean putValuesNoUpdate;
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Integer.valueOf(contextInfo.mCallerUid));
        contentValues.put("featureType", "KEYPAD");
        if (this.mEdmStorageProvider.getCount("KnoxFeatureAccess", contentValues) > 0) {
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put("featureValue", getFeatureAccessIME(contextInfo) + "," + str);
            putValuesNoUpdate = this.mEdmStorageProvider.putValues("KnoxFeatureAccess", contentValues2, contentValues);
        } else {
            contentValues.put("featureValue", str);
            putValuesNoUpdate = this.mEdmStorageProvider.putValuesNoUpdate("KnoxFeatureAccess", contentValues);
        }
        if (!putValuesNoUpdate) {
            return false;
        }
        Log.d(TAG, "setFeatureAccessIME policy passed");
        return true;
    }

    public final String getFeatureAccessIME(ContextInfo contextInfo) {
        String[] strArr = {"featureValue"};
        try {
            Log.d(TAG, "getFeatureAccessIME combinedUid-" + contextInfo.mCallerUid);
            ContentValues contentValues = new ContentValues();
            contentValues.put("adminUid", Integer.valueOf(contextInfo.mCallerUid));
            contentValues.put("featureType", "KEYPAD");
            List valuesList = this.mEdmStorageProvider.getValuesList("KnoxFeatureAccess", strArr, contentValues);
            if (valuesList == null || valuesList.size() <= 0) {
                return null;
            }
            Iterator it = valuesList.iterator();
            while (it.hasNext()) {
                String asString = ((ContentValues) it.next()).getAsString("featureValue");
                Log.d(TAG, "getFeatureAccessIME value- " + asString);
                if (asString != null && asString.length() > 0) {
                    return asString;
                }
            }
            return null;
        } catch (Exception e) {
            Log.e(TAG, "getFeatureAccessIME exception " + e);
            return null;
        }
    }

    public final boolean isContainerOnlyModeAllowed() {
        if (getDevicePolicyService() != null && getDevicePolicyService().isDeviceManaged()) {
            Log.d(TAG, "isContainerOnlyModeAllowed return false, reason: the device is managed by any device owner. ");
            return false;
        }
        if (getPersonaManagerLocked() == null) {
            return false;
        }
        Iterator it = getPersonaManagerLocked().getProfiles(0, false).iterator();
        while (it.hasNext()) {
            if (((UserInfo) it.next()).isManagedProfile()) {
                Log.d(TAG, "isContainerOnlyModeAllowed return false, reason: managed profile exists on the device. ");
                return false;
            }
        }
        return true;
    }

    public final IApplicationPolicy getAppService() {
        if (this.mAppService == null) {
            this.mAppService = IApplicationPolicy.Stub.asInterface(ServiceManager.getService("application_policy"));
        }
        return this.mAppService;
    }

    public int removePackageFromInstallWhiteList(ContextInfo contextInfo, AppIdentity appIdentity) {
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("removePackageFromInstallWhiteList is called for package - ");
        sb.append(appIdentity != null ? appIdentity.getPackageName() : "null");
        Log.i(str, sb.toString());
        ContextInfo enforceContainerOwnershipPermission = enforceContainerOwnershipPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CONTAINER")));
        IApplicationPolicy appService = getAppService();
        int i = -1;
        if (appService == null) {
            Log.e(TAG, "Application PolicyService is not yet ready!!!");
            return -1;
        }
        if (appIdentity == null || appIdentity.getPackageName() == null) {
            return -1;
        }
        try {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            i = appService.removePackageFromWhiteList(enforceContainerOwnershipPermission, 1, appIdentity.getPackageName());
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return i;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at ContainerConfigurationPolicy API removePackageFromInstallWhiteList ", e);
            return i;
        }
    }

    public boolean isPackageInInstallWhiteList(ContextInfo contextInfo, String str) {
        Log.i(TAG, "isPackageInInstallWhiteList is called for pkgName - " + str);
        ContextInfo enforceContainerOwnershipPermission = enforceContainerOwnershipPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CONTAINER")));
        IApplicationPolicy appService = getAppService();
        boolean z = false;
        if (appService == null) {
            Log.e(TAG, "Application PolicyService is not yet ready!!!");
            return false;
        }
        try {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            z = appService.isPackageInApprovedInstallerWhiteList(enforceContainerOwnershipPermission, str);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return z;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at ContainerConfigurationPolicy API isPackageInInstallWhiteList ", e);
            return z;
        }
    }

    public int addPackageToInstallWhiteList(ContextInfo contextInfo, AppIdentity appIdentity) {
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("addPackageToInstallWhiteList is called for package - ");
        sb.append(appIdentity != null ? appIdentity.getPackageName() : "null");
        Log.i(str, sb.toString());
        ContextInfo enforceContainerOwnershipPermission = enforceContainerOwnershipPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CONTAINER")));
        IApplicationPolicy appService = getAppService();
        int i = -1;
        if (appService == null) {
            Log.e(TAG, "Application PolicyService is not yet ready!!!");
            return -1;
        }
        if (appIdentity == null || appIdentity.getPackageName() == null) {
            return -1;
        }
        try {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            i = appService.addPackageToWhiteList(enforceContainerOwnershipPermission, 1, appIdentity);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return i;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at ContainerConfigurationPolicy API addPackageToInstallWhiteList ", e);
            return i;
        }
    }

    public List getPackagesFromInstallWhiteList(ContextInfo contextInfo) {
        Log.i(TAG, "getPackagesFromInstallWhiteList is called...");
        ContextInfo enforceContainerOwnershipPermission = enforceContainerOwnershipPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CONTAINER")));
        IApplicationPolicy appService = getAppService();
        List list = null;
        if (appService == null) {
            Log.e(TAG, "Application PolicyService is not yet ready!!!");
            return null;
        }
        try {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            list = appService.getPackagesFromWhiteList(enforceContainerOwnershipPermission, 1);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return list;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at ContainerConfigurationPolicy API getPackagesFromInstallWhiteList ", e);
            return list;
        }
    }

    public int clearPackagesFromExternalStorageBlackList(ContextInfo contextInfo) {
        Log.i(TAG, "clearPackagesFromExternalStorageBlackList is not available.");
        return -1;
    }

    public List getPackagesFromExternalStorageBlackList(ContextInfo contextInfo) {
        Log.i(TAG, "getPackagesFromExternalStorageBlackList ");
        ContextInfo enforceSecurityPermission = enforceSecurityPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CONTAINER")));
        if (enforceSecurityPermission != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("adminUid", Integer.valueOf(enforceSecurityPermission.mCallerUid));
            List valuesList = this.mEdmStorageProvider.getValuesList("KnoxExternalStorageSBABlacklist", new String[]{"packageName"}, contentValues);
            if (valuesList != null && valuesList.size() > 0) {
                ArrayList arrayList = new ArrayList();
                Iterator it = valuesList.iterator();
                while (it.hasNext()) {
                    String asString = ((ContentValues) it.next()).getAsString("packageName");
                    if (asString != null) {
                        arrayList.add(asString);
                    }
                }
                if (DEBUG) {
                    Log.d(TAG, "getPackagesFromExternalStorageSBABlackList SUCCESS : " + arrayList);
                }
                if (arrayList.size() > 0) {
                    return arrayList;
                }
                return null;
            }
        }
        Log.e(TAG, "getPackagesFromExternalStorageSBABlackList policy returning null");
        return null;
    }

    public int removePackageFromExternalStorageBlackList(ContextInfo contextInfo, AppIdentity appIdentity) {
        Log.i(TAG, "removePackageFromExternalStorageBlackList is not available.");
        return -1;
    }

    public final boolean addPackageToExternalStorageSBABlackListInternal(ContextInfo contextInfo, String str, Signature[] signatureArr) {
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
        contentValues.put("adminUid", Integer.valueOf(contextInfo.mCallerUid));
        contentValues.put("packageName", str);
        if (this.mEdmStorageProvider.getCount("KnoxExternalStorageSBABlacklist", contentValues) > 0) {
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put("signatures", str2);
            putValuesNoUpdate = this.mEdmStorageProvider.putValues("KnoxExternalStorageSBABlacklist", contentValues2, contentValues);
        } else {
            contentValues.put("signatures", str2);
            putValuesNoUpdate = this.mEdmStorageProvider.putValuesNoUpdate("KnoxExternalStorageSBABlacklist", contentValues);
        }
        if (!putValuesNoUpdate) {
            return false;
        }
        Log.e(TAG, "addPackageToExternalStorageSBABlackListInternal policy passed");
        return true;
    }

    public int addPackageToExternalStorageBlackList(ContextInfo contextInfo, AppIdentity appIdentity) {
        boolean z;
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("addPackageToExternalStorageBlackList ");
        sb.append(appIdentity != null ? appIdentity.getPackageName() : "null");
        Log.i(str, sb.toString());
        if (appIdentity == null) {
            return -1;
        }
        ContextInfo enforceSecurityPermission = enforceSecurityPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CONTAINER")));
        String packageName = appIdentity.getPackageName();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        if (enforceSecurityPermission != null) {
            try {
                if (packageName != null) {
                    try {
                        if (!packageName.equals("")) {
                            try {
                                PackageInfo packageInfo = AppGlobals.getPackageManager().getPackageInfo(packageName, 64L, contextInfo.mContainerId);
                                Log.d(TAG, "Package Info: " + packageInfo);
                                if (packageInfo != null) {
                                    if (DEBUG) {
                                        Log.d(TAG, "Package Info: " + Arrays.toString(packageInfo.signatures));
                                    }
                                    z = true;
                                } else {
                                    z = false;
                                }
                                if (addPackageToExternalStorageSBABlackListInternal(enforceSecurityPermission, packageName, z ? packageInfo.signatures : null)) {
                                    if (z) {
                                        Signature[] signatureArr = packageInfo.signatures;
                                        if (getSEAMSService() == null) {
                                            Log.e(TAG, "addPackageToExternalStorageSBABlackList : SEAMS service cannot be null.");
                                            return -1;
                                        }
                                        if (signatureArr != null && signatureArr.length != 0) {
                                            String[] strArr = new String[signatureArr.length];
                                            for (int i = 0; i < signatureArr.length; i++) {
                                                strArr[i] = signatureArr[i].toCharsString();
                                            }
                                            notifyContainerServiceForPolicyUpdate(contextInfo.mContainerId, packageName);
                                        }
                                        Log.e(TAG, "addPackageToExternalStorageSBABlackList : package signature cannot be null.");
                                        return -1;
                                    }
                                    return 0;
                                }
                            } catch (RemoteException e) {
                                Log.e(TAG, Log.getStackTraceString(e));
                                return -1;
                            } catch (Exception e2) {
                                Log.e(TAG, Log.getStackTraceString(e2));
                                return -1;
                            }
                        }
                    } catch (Exception e3) {
                        Log.e(TAG, Log.getStackTraceString(e3));
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        Log.e(TAG, "addPackageToExternalStorageSBABlackList policy failed");
        return -1;
    }

    public final void notifyContainerServiceForPolicyUpdate(int i, String str) {
        Bundle bundle = new Bundle();
        bundle.putString("knox.container.proxy.EXTRA_PACKAGE_NAME", str);
        bundle.putInt("android.intent.extra.user_handle", i);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        ContainerProxy.sendPolicyUpdate("knox.container.proxy.POLICY_SDCARD_POLICY_CHANGED", bundle);
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public int addPackageToExternalStorageWhiteList(ContextInfo contextInfo, AppIdentity appIdentity) {
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("addPackageToExternalStorageWhiteList ");
        sb.append(appIdentity != null ? appIdentity.getPackageName() : "null");
        Log.i(str, sb.toString());
        ContextInfo enforceContainerOwnershipPermission = enforceContainerOwnershipPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CONTAINER")));
        IApplicationPolicy appService = getAppService();
        int i = -1;
        if (appService == null) {
            Log.e(TAG, "Application PolicyService is not yet ready!!!");
            return -1;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                i = appService.addPackageToWhiteList(enforceContainerOwnershipPermission, 2, appIdentity);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed at ContainerConfigurationPolicy API addPackageToExternalStorageWhiteList ", e);
            }
            return i;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public int removePackageFromExternalStorageWhiteList(ContextInfo contextInfo, AppIdentity appIdentity) {
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("removePackageFromExternalStorageWhiteList ");
        sb.append(appIdentity != null ? appIdentity.getPackageName() : "null");
        Log.i(str, sb.toString());
        ContextInfo enforceContainerOwnershipPermission = enforceContainerOwnershipPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CONTAINER")));
        IApplicationPolicy appService = getAppService();
        int i = -1;
        if (appService == null) {
            Log.e(TAG, "Application PolicyService is not yet ready!!!");
            return -1;
        }
        if (appIdentity == null || appIdentity.getPackageName() == null) {
            return -1;
        }
        try {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            i = appService.removePackageFromWhiteList(enforceContainerOwnershipPermission, 2, appIdentity.getPackageName());
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return i;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at ContainerConfigurationPolicy API removePackageFromExternalStorageWhiteList ", e);
            return i;
        }
    }

    public List getPackagesFromExternalStorageWhiteList(ContextInfo contextInfo) {
        Log.i(TAG, "getPackagesFromExternalStorageWhiteList ");
        ContextInfo enforceContainerOwnershipPermission = enforceContainerOwnershipPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CONTAINER")));
        IApplicationPolicy appService = getAppService();
        List list = null;
        if (appService == null) {
            Log.e(TAG, "Application PolicyService is not yet ready!!!");
            return null;
        }
        try {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            list = appService.getPackagesFromWhiteList(enforceContainerOwnershipPermission, 2);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return list;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at ContainerConfigurationPolicy API getPackagesFromExternalStorageWhiteList ", e);
            return list;
        }
    }

    public Signature[] getPackageSignaturesFromExternalStorageWhiteList(ContextInfo contextInfo, String str) {
        Log.d(TAG, "getPackageSignaturesFromExternalStorageWhiteList");
        ContextInfo enforceContainerOwnershipPermission = enforceContainerOwnershipPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CONTAINER")));
        IApplicationPolicy appService = getAppService();
        Signature[] signatureArr = null;
        if (appService == null) {
            Log.e(TAG, "Application PolicyService is not yet ready!!!");
            return null;
        }
        try {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            signatureArr = appService.getPackageSignaturesFromExternalStorageWhiteList(enforceContainerOwnershipPermission, str);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return signatureArr;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at ContainerConfigurationPolicy API getPackageSignaturesFromExternalStorageWhiteList ", e);
            return signatureArr;
        }
    }

    public int clearPackagesFromExternalStorageWhiteList(ContextInfo contextInfo) {
        ContextInfo enforceContainerOwnershipPermission = enforceContainerOwnershipPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CONTAINER")));
        IApplicationPolicy appService = getAppService();
        int i = -1;
        if (appService == null) {
            Log.e(TAG, "Application PolicyService is not yet ready!!!");
            return -1;
        }
        try {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            i = appService.clearPackagesFromExternalStorageWhiteList(enforceContainerOwnershipPermission);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return i;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at ContainerConfigurationPolicy API clearPackagesFromExternalStorageWhiteList ", e);
            return i;
        }
    }

    public boolean allowLayoutSwitching(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceContainerOwnershipPermission = enforceContainerOwnershipPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CONTAINER")));
        boolean z2 = false;
        if (enforceContainerOwnershipPermission == null) {
            return false;
        }
        int i = contextInfo.mContainerId;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                z2 = setFeatureAccessPermission(enforceContainerOwnershipPermission, "LAYOUT_SWITCH", z);
                Log.d(TAG, "allowLayoutSwitching status - " + z2 + ", personaId - " + i);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return z2;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean isLayoutSwitchingAllowed(ContextInfo contextInfo) {
        boolean z = true;
        if (contextInfo == null) {
            return true;
        }
        int i = contextInfo.mContainerId;
        Log.d(TAG, "isLayoutSwitchingAllowed API is called for personaId - " + i);
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

    public int setCustomResource(int i, ContextInfo contextInfo, Bundle bundle) {
        String str;
        Bitmap bitmap;
        if (enforceContainerOwnershipPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CONTAINER"))) == null) {
            return -2;
        }
        int i2 = contextInfo.mContainerId;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        String str2 = "/data/misc/container3.0/" + i2 + "/";
        try {
            int dimension = (int) mContext.getResources().getDimension(R.dimen.keyguard_muliuser_selector_margin);
            int dimension2 = (int) mContext.getResources().getDimension(R.dimen.keyguard_lockscreen_status_line_font_size);
            if (i == 1) {
                str = str2 + "badgeIcon.png";
                bitmap = bundle != null ? (Bitmap) bundle.getParcelable("key-image") : null;
                if (bitmap != null) {
                    int width = bitmap.getWidth();
                    int height = bitmap.getHeight();
                    Log.d(TAG, "container badge dimensions " + width + " " + height);
                    if (width > dimension / 2 || height > dimension2 / 2) {
                        bitmap = Bitmap.createScaledBitmap(bitmap, dimension / 2, dimension2 / 2, false);
                    }
                    str = str;
                }
            } else if (i == 2) {
                str = str2 + "profileIcon.png";
                bitmap = bundle != null ? (Bitmap) bundle.getParcelable("key-image") : null;
                if (bitmap != null) {
                    int width2 = bitmap.getWidth();
                    int height2 = bitmap.getHeight();
                    Log.d(TAG, "container icon dimensions " + width2 + " " + height2);
                    if (width2 > dimension || height2 > dimension2) {
                        bitmap = Bitmap.createScaledBitmap(bitmap, dimension, dimension2, false);
                    }
                    str = str;
                }
            } else {
                if (i != 3) {
                    if (i == 4) {
                        String string = bundle != null ? bundle.getString("key-name", null) : null;
                        getService();
                        if (!SemPersonaManager.setCustomName(i2, string)) {
                            Log.d(TAG, "setting Custom Profile name unsuccessful");
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            return -2;
                        }
                        refreshLauncherUI(getLauncherRefreshIntent(i2));
                        Log.d(TAG, "container name : " + string);
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return 0;
                    }
                    if (i != 5) {
                        Log.d(TAG, "setCustomResource - not a valid type " + i);
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return -1;
                    }
                    String string2 = bundle != null ? bundle.getString("key-name", null) : null;
                    getService();
                    if (!SemPersonaManager.setPersonalModeName(i2, string2)) {
                        Log.d(TAG, "setting personal tab name unsuccessful");
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return -2;
                    }
                    refreshLauncherUI(getLauncherRefreshIntent(i2));
                    Log.d(TAG, "Personal mode tab : " + string2);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return 0;
                }
                str = str2 + "nameIcon.png";
                bitmap = bundle != null ? (Bitmap) bundle.getParcelable("key-image") : null;
            }
            if (bitmap == null) {
                return new File(str).delete() ? 0 : -2;
            }
            File file = new File("/data/misc/container3.0/");
            if (!file.exists()) {
                file.mkdirs();
                file.setReadable(true, false);
                file.setWritable(true);
                file.setExecutable(true, false);
            }
            File file2 = new File(str2);
            if (!file2.exists()) {
                file2.mkdirs();
                file2.setReadable(true, false);
                file2.setWritable(true);
                file2.setExecutable(true, false);
            }
            int i3 = writeFile(bitmap, str) ? 0 : -2;
            if (i3 == 0 && i == 1) {
                Intent launcherRefreshIntent = getLauncherRefreshIntent(i2);
                launcherRefreshIntent.putExtra("com.samsung.android.knox.container.block_create_shortcut", true);
                launcherRefreshIntent.putExtra("com.samsung.android.knox.container.name", SemPersonaManager.getPersonaName(mContext, i2));
                refreshLauncherUI(launcherRefreshIntent);
            }
            return i3;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final Intent getLauncherRefreshIntent(int i) {
        Intent intent = new Intent("com.samsung.android.knox.container.MANAGED_PROFILE_REFRESH");
        intent.putExtra("com.samsung.android.knox.container.userid", i);
        intent.putExtra("com.samsung.sec.knox.EXTRA_PERSONA_ID", 0);
        return intent;
    }

    public final void refreshLauncherUI(Intent intent) {
        Log.d(TAG, "refreshLauncherUI launcherRefresh");
        mContext.sendBroadcastAsUser(intent, UserHandle.SEM_OWNER);
    }

    public final int installPackage(String str, int i) {
        try {
            return mContext.getPackageManager().installExistingPackageAsUser(str, i);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public class LocalService extends KnoxMUMContainerPolicyInternal {
        public LocalService() {
        }

        @Override // com.android.server.pm.KnoxMUMContainerPolicyInternal
        public void onNewUserCreated(int i) {
            KnoxMUMContainerPolicy.this.onNewUserCreated(i);
        }

        @Override // com.android.server.pm.KnoxMUMContainerPolicyInternal
        public void setAppSeparationOwnership(ContainerCreationParams containerCreationParams) {
            Log.d(KnoxMUMContainerPolicy.TAG, "setAppSeparationOwnership.");
            KnoxMUMContainerPolicy.this.createContainerInternal(containerCreationParams);
        }
    }

    public Bundle getAppSeparationConfig() {
        try {
            ContentValues contentValues = new ContentValues();
            if (this.mEdmStorageProvider.getCount("AppSeparationTable", contentValues) == 0) {
                Log.d(TAG, "getAppSeparationConfig(): no record. Return null");
                return null;
            }
            Bundle bundle = new Bundle();
            boolean z = true;
            if (this.mEdmStorageProvider.getInt("AppSeparationTable", "AppSeparationOutside", contentValues) != 1) {
                z = false;
            }
            bundle.putBoolean("APP_SEPARATION_OUTSIDE", z);
            bundle.putStringArrayList("APP_SEPARATION_APP_LIST", convertStringCommaDelimitedToList(this.mEdmStorageProvider.getString("AppSeparationTable", "AppSeparationApplist", contentValues)));
            bundle.putStringArrayList("APP_SEPARATION_COEXISTANCE_LIST", convertStringCommaDelimitedToList(this.mEdmStorageProvider.getString("AppSeparationTable", "AppSeparationCoexistenceList", contentValues)));
            return bundle;
        } catch (Exception e) {
            Log.d(TAG, "getAppSeparationConfig() exception: " + e.getMessage());
            return null;
        }
    }

    public boolean setAppSeparationConfig(ContextInfo contextInfo, Bundle bundle) {
        int callingUid;
        int deviceOwnerUid;
        String nameForUid = mContext.getPackageManager().getNameForUid(Binder.getCallingUid());
        Log.d(TAG, "setAppSeparationConfig: calling package : " + nameForUid);
        if (isIgnoreKSPCall(nameForUid)) {
            Log.d(TAG, "ignoring call from KSP as call from AppSeparation Agent was received earlier");
            return false;
        }
        if (isUserMode && !SemPersonaManager.isDoEnabled(0)) {
            Log.e(TAG, "setAppSeparationConfig() : Activate DO!");
            return false;
        }
        if (DualDarManager.isOnDeviceOwnerEnabled()) {
            Log.e(TAG, "Failed to activate AppSeparation - on DeviceOwner DualDAR mode");
            return false;
        }
        if (contextInfo == null || (callingUid = contextInfo.mCallerUid) <= 0) {
            callingUid = Binder.getCallingUid();
        }
        try {
            enforceAppSeparationPermission(nameForUid, callingUid);
            deviceOwnerUid = getDeviceOwnerUid();
        } catch (Exception e) {
            Log.d(TAG, "setAppSeparationConfig() exception: " + e.getMessage());
        }
        if (bundle == null) {
            Log.e(TAG, "setAppSeparationConfig() : config is null. remove row and enforce.");
            HashMap hashMap = new HashMap();
            hashMap.put("adminUid", Integer.toString(deviceOwnerUid));
            this.mEdmStorageProvider.removeByFields("AppSeparationTable", hashMap);
            getPersonaManagerLocked().enforceAppSeparationDeletion();
            AuditLog.logAsUser(5, 1, true, Process.myPid(), "KnoxMUMContainerPolicy", String.format("Admin %s has de-activated Separated Apps.", mContext.getPackageManager().getNameForUid(deviceOwnerUid)), 0);
            return true;
        }
        boolean z = bundle.getBoolean("APP_SEPARATION_OUTSIDE", true);
        int i = z ? 1 : 0;
        String join = String.join(",", bundle.getStringArrayList("APP_SEPARATION_APP_LIST"));
        ArrayList<String> stringArrayList = bundle.getStringArrayList("APP_SEPARATION_COEXISTANCE_LIST");
        if (stringArrayList == null) {
            stringArrayList = new ArrayList<>();
        }
        String join2 = String.join(",", stringArrayList);
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Integer.valueOf(deviceOwnerUid));
        if (this.mEdmStorageProvider.getCount("AppSeparationTable", contentValues) > 0) {
            Log.d(TAG, "AppSeparation is already enabled. Use setAppSeparationWhitelistedApps() to update app list.");
            return false;
        }
        contentValues.put("AppSeparationOutside", Integer.valueOf(i));
        contentValues.put("AppSeparationApplist", join);
        contentValues.put("AppSeparationCoexistenceList", join2);
        if (this.mEdmStorageProvider.putValuesNoUpdate("AppSeparationTable", contentValues)) {
            Log.d(TAG, "setAppSeparationConfig passed");
            getPersonaManagerLocked().enforceAppSeparationAllowListUpdate();
            AuditLog.logAsUser(5, 1, true, Process.myPid(), "KnoxMUMContainerPolicy", String.format(z ? "Admin %s has located Apps (outside) of Separated Apps" : "Admin %s has located Apps (inside) of Separated Apps", mContext.getPackageManager().getNameForUid(deviceOwnerUid)), 0);
            AuditLog.logAsUser(5, 1, true, Process.myPid(), "KnoxMUMContainerPolicy", String.format("Application %s installation is allowed by admin %s for Separated Apps", join, mContext.getPackageManager().getNameForUid(deviceOwnerUid)), 0);
            AuditLog.logAsUser(5, 1, true, Process.myPid(), "KnoxMUMContainerPolicy", "Application " + join2 + " coexistence installation is allowed by admin " + mContext.getPackageManager().getNameForUid(deviceOwnerUid) + " for Separated Apps", 0);
            return true;
        }
        return false;
    }

    public boolean setAppSeparationWhitelistedApps(ContextInfo contextInfo, List list) {
        int callingUid;
        int deviceOwnerUid;
        String nameForUid = mContext.getPackageManager().getNameForUid(Binder.getCallingUid());
        Log.d(TAG, "setAppSeparationWhitelistedApps: calling package : " + nameForUid);
        if (isIgnoreKSPCall(nameForUid)) {
            Log.d(TAG, "ignoring call from KSP as call from AppSeparation Agent was received earlier");
            return false;
        }
        if (contextInfo == null || (callingUid = contextInfo.mCallerUid) <= 0) {
            callingUid = Binder.getCallingUid();
        }
        try {
            enforceAppSeparationPermission(nameForUid, callingUid);
            deviceOwnerUid = getDeviceOwnerUid();
        } catch (Exception e) {
            Log.d(TAG, "setAppSeparationWhitelistedApps() exception: " + e.getMessage());
        }
        if (list == null) {
            Log.e(TAG, "setAppSeparationWhitelistedApps() : AppList cannot be null. Please use setAppSeperationConfig to wipe app separation policy");
            return false;
        }
        String join = String.join(",", list);
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Integer.valueOf(deviceOwnerUid));
        if (this.mEdmStorageProvider.getCount("AppSeparationTable", contentValues) > 0) {
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put("AppSeparationApplist", join);
            if (this.mEdmStorageProvider.putValues("AppSeparationTable", contentValues2, contentValues)) {
                Log.d(TAG, "setAppSeparationWhitelistedApps passed");
                getPersonaManagerLocked().enforceAppSeparationAllowListUpdate();
                AuditLog.logAsUser(5, 1, true, Process.myPid(), "KnoxMUMContainerPolicy", String.format("Application %s installation is allowed by admin %s for Separated Apps", join, mContext.getPackageManager().getNameForUid(deviceOwnerUid)), 0);
                return true;
            }
            return false;
        }
        Log.d(TAG, "No default policy applied. Use setAppSeperationConfig()");
        return false;
    }

    public boolean setAppSeparationCoexistentApps(ContextInfo contextInfo, List list) {
        int callingUid;
        int deviceOwnerUid;
        String nameForUid = mContext.getPackageManager().getNameForUid(Binder.getCallingUid());
        Log.d(TAG, "setApppSeparationCoexistentApps: calling package : " + nameForUid);
        if (isIgnoreKSPCall(nameForUid)) {
            Log.d(TAG, "ignoring call from KSP as call from AppSeparation Agent was received earlier");
            return false;
        }
        if (contextInfo == null || (callingUid = contextInfo.mCallerUid) <= 0) {
            callingUid = Binder.getCallingUid();
        }
        try {
            enforceAppSeparationPermission(nameForUid, callingUid);
            deviceOwnerUid = getDeviceOwnerUid();
        } catch (Exception e) {
            Log.d(TAG, "setAppSeparationCoexistentApps() exception: " + e.getMessage());
        }
        if (list == null) {
            Log.e(TAG, "setAppSeparationCoexistentApps() : AppList cannot be null");
            return false;
        }
        String join = String.join(",", list);
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Integer.valueOf(deviceOwnerUid));
        if (this.mEdmStorageProvider.getCount("AppSeparationTable", contentValues) > 0) {
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put("AppSeparationCoexistenceList", join);
            if (this.mEdmStorageProvider.putValues("AppSeparationTable", contentValues2, contentValues)) {
                Log.d(TAG, "setAppSeparationCoexistentApps passed");
                getPersonaManagerLocked().enforceAppSeparationCoexistenceAllowListUpdate();
                AuditLog.logAsUser(5, 1, true, Process.myPid(), "KnoxMUMContainerPolicy", "Application " + join + " installation is allowed by admin " + mContext.getPackageManager().getNameForUid(deviceOwnerUid) + " for Separated Apps", 0);
                return true;
            }
            return false;
        }
        Log.d(TAG, "No default policy applied. Use setAppSeperationConfig()");
        return false;
    }

    public final boolean isIgnoreKSPCall(String str) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            boolean z = SystemProperties.getBoolean("persist.sys.knox.ignore_ksp_call", false);
            if (!z || str.equals("com.samsung.android.appseparation")) {
                if (str.equals("com.samsung.android.appseparation") && !z) {
                    SystemProperties.set("persist.sys.knox.ignore_ksp_call", "true");
                }
                return false;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return true;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void enforceAppSeparationPermission(String str, int i) {
        int userId = UserHandle.getUserId(i);
        boolean z = false;
        try {
            IPackageManager packageManager = AppGlobals.getPackageManager();
            if (packageManager.checkPermission("com.samsung.android.knox.permission.KNOX_APP_SEPARATION", str, userId) == 0) {
                if (packageManager.checkPermission("com.samsung.android.knox.permission.KNOX_APP_SEPARATION", getDeviceOwnerPackage(), 0) == 0) {
                    z = true;
                }
            }
        } catch (Exception unused) {
            Log.d(TAG, "Error in checking AppSeparation Permission");
        }
        if (!z) {
            throw new SecurityException("Knox App Separation Permission");
        }
    }

    public final int getDeviceOwnerUid() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                ComponentName deviceOwnerComponentOnAnyUser = getDevicePolicyService().getDeviceOwnerComponentOnAnyUser();
                if (deviceOwnerComponentOnAnyUser != null) {
                    return mContext.getPackageManager().getApplicationInfo(deviceOwnerComponentOnAnyUser.getPackageName(), 0).uid;
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                Log.e(TAG, "Failed to get application info for DO component.");
            } catch (Exception e2) {
                e2.printStackTrace();
                Log.e(TAG, "Failed to retrieve DO component on device");
            }
            return -1;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final String getDeviceOwnerPackage() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                ComponentName deviceOwnerComponentOnAnyUser = getDevicePolicyService().getDeviceOwnerComponentOnAnyUser();
                if (deviceOwnerComponentOnAnyUser != null) {
                    return deviceOwnerComponentOnAnyUser.getPackageName();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "Failed to retrieve DO component on device");
            }
            return "";
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final ArrayList convertStringCommaDelimitedToList(String str) {
        if (str == null || str.isEmpty()) {
            return new ArrayList();
        }
        return new ArrayList(Arrays.asList(str.trim().split("\\s*,\\s*")));
    }

    public final void clearIdentityAndStartActivityAsUser(Context context, Intent intent, UserHandle userHandle) {
        if (mContext == null || intent == null || userHandle == null) {
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            context.startActivityAsUser(intent, userHandle);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final int addPseudoAdminForWpcod(int i) {
        EnterpriseDeviceManagerService enterpriseDeviceManagerService;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                enterpriseDeviceManagerService = EnterpriseDeviceManagerService.getInstance();
            } catch (Exception e) {
                Log.d(TAG, "addPseudoAdminForWpcod: exception: " + e.getMessage());
                e.printStackTrace();
            }
            if (enterpriseDeviceManagerService != null && enterpriseDeviceManagerService.getOrganizationOwnedProfileUserId() == i) {
                Log.d(TAG, "addPseudoAdminForWpcod: containerId-" + i);
                return enterpriseDeviceManagerService.addPseudoAdminForParent(i);
            }
            Log.d(TAG, "addPseudoAdminForWpcod: containerId-" + i + " is not WP-C");
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return -1;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
