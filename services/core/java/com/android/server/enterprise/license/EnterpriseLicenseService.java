package com.android.server.enterprise.license;

import android.app.ActivityManager;
import android.app.AppGlobals;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.IPackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.enterprise.EnterpriseDeviceManagerService;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.adapter.AdapterRegistry;
import com.android.server.enterprise.adapter.IPersonaManagerAdapter;
import com.android.server.enterprise.license.EnterpriseLicenseService;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.utils.KpuHelper;
import com.android.server.enterprise.utils.Utils;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.pm.PackageManagerShellCommandDataLoader;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.license.ActivationInfo;
import com.samsung.android.knox.license.Error;
import com.samsung.android.knox.license.IEnterpriseLicense;
import com.samsung.android.knox.license.ILicenseResultCallback;
import com.samsung.android.knox.license.LicenseAgentDbContract;
import com.samsung.android.knox.license.LicenseInfo;
import com.samsung.android.knox.license.LicenseResult;
import com.samsung.android.knox.license.RightsObject;
import com.samsung.android.knox.ucm.core.IUcmService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
public class EnterpriseLicenseService extends IEnterpriseLicense.Stub implements EnterpriseServiceCallback, IDeviceProfileObserver {
    public static final int MY_PID = Process.myPid();
    public static EdmStorageProvider mEdmStorageProvider;
    public static IPackageManager mPMS;
    public final Context mContext;
    public DeviceProfileListener mDeviceProfileListener;
    public Map mElmPkgRecords;
    public final Injector mInjector;
    public List mKlmElmChangeList;
    public Map mKlmPkgRecords;
    public BroadcastReceiver mPackageRemovedReceiver;
    public IUcmService mUcmeService;
    public final List samsungSpecialPackages;

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void notifyToAddSystemService(String str, IBinder iBinder) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onAdminAdded(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onAdminRemoved(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onPreAdminRemoval(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void systemReady() {
    }

    class Injector {
        public final Context mContext;

        public Injector(Context context) {
            this.mContext = context;
        }
    }

    public EnterpriseLicenseService(Context context, IPackageManager iPackageManager) {
        this(new Injector(context), iPackageManager);
    }

    public EnterpriseLicenseService(Injector injector, IPackageManager iPackageManager) {
        this.mUcmeService = null;
        this.samsungSpecialPackages = new ArrayList(List.of("com.sec.enterprise.knox.cloudmdm.smdms", "com.sec.knox.kccagent", "com.sec.knox.klat.agent"));
        this.mPackageRemovedReceiver = new C14481();
        this.mInjector = injector;
        Context context = injector.mContext;
        Objects.requireNonNull(context);
        this.mContext = context;
        mPMS = iPackageManager;
        mEdmStorageProvider = new EdmStorageProvider(context);
        this.mElmPkgRecords = new ConcurrentHashMap();
        this.mKlmPkgRecords = new ConcurrentHashMap();
        IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGE_FULLY_REMOVED");
        intentFilter.addDataScheme("package");
        context.registerReceiverAsUser(this.mPackageRemovedReceiver, UserHandle.ALL, intentFilter, null, null);
        this.mKlmElmChangeList = new ArrayList();
        DeviceProfileListener deviceProfileListener = new DeviceProfileListener(context);
        this.mDeviceProfileListener = deviceProfileListener;
        deviceProfileListener.registerObserver(this);
    }

    /* renamed from: com.android.server.enterprise.license.EnterpriseLicenseService$1 */
    public class C14481 extends BroadcastReceiver {
        public C14481() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent == null || intent.getData() == null) {
                Log.e("EnterpriseLicenseService", "intent or getData are null. Skip.");
                return;
            }
            String schemeSpecificPart = intent.getData().getSchemeSpecificPart();
            if (EnterpriseLicenseService.this.isPackageInstalled(schemeSpecificPart)) {
                return;
            }
            if (EnterpriseLicenseService.this.getInstanceId(schemeSpecificPart) != null) {
                EnterpriseLicenseService.this.resetLicenseByAdmin(schemeSpecificPart);
            }
            final Bundle bundle = new Bundle();
            bundle.putString("packageName", schemeSpecificPart);
            new Thread(new Runnable() { // from class: com.android.server.enterprise.license.EnterpriseLicenseService$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    EnterpriseLicenseService.C14481.this.lambda$onReceive$0(bundle);
                }
            }).start();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReceive$0(Bundle bundle) {
            EnterpriseLicenseService.this.callLicenseAgent("packageRemovedInternal", null, bundle);
        }
    }

    public final void enforcePermission() {
        if (Binder.getCallingPid() == MY_PID) {
            return;
        }
        try {
            this.mContext.enforceCallingPermission("com.samsung.android.knox.permission.KNOX_LICENSE_INTERNAL", null);
        } catch (SecurityException e) {
            String message = e.getMessage();
            if (message != null) {
                message = message + ",com.samsung.android.knox.permission.KNOX_LICENSE_INTERNAL";
            }
            throw new SecurityException(message);
        }
    }

    public final synchronized IUcmService getUcmService() {
        if (this.mUcmeService == null) {
            this.mUcmeService = IUcmService.Stub.asInterface(ServiceManager.getService("com.samsung.ucs.ucsservice"));
        }
        return this.mUcmeService;
    }

    public final void notifyContainerLicenseStatus(String str) {
        Iterator it = this.mKlmElmChangeList.iterator();
        while (it.hasNext()) {
            ((IActivationKlmElmObserver) it.next()).onUpdateContainerLicenseStatus(str);
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(19:26|(2:27|28)|(1:30)(2:106|(1:108)(16:109|32|33|(5:58|59|60|61|62)|35|(1:(1:38)(1:39))|40|41|(3:43|(1:45)|46)(1:56)|47|(1:49)(1:55)|50|51|52|53|54))|31|32|33|(0)|35|(0)|40|41|(0)(0)|47|(0)(0)|50|51|52|53|54) */
    /* JADX WARN: Code restructure failed: missing block: B:105:0x01e1, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0030 A[Catch: all -> 0x02e5, TryCatch #2 {, blocks: (B:4:0x0009, B:6:0x0017, B:9:0x001e, B:13:0x0028, B:15:0x0030, B:17:0x003b, B:18:0x0041, B:20:0x004b, B:21:0x006e, B:25:0x0058, B:84:0x0270, B:86:0x0277, B:88:0x027d, B:89:0x028d, B:91:0x0295, B:92:0x02b8, B:93:0x02e4, B:94:0x02a2, B:69:0x01fa, B:71:0x0201, B:73:0x0207, B:75:0x0215, B:77:0x021d, B:78:0x0240, B:51:0x0268, B:79:0x022a, B:41:0x0161, B:43:0x0168, B:45:0x016e, B:47:0x017e, B:49:0x0186, B:50:0x01a9, B:55:0x0193), top: B:3:0x0009 }] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00a3  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x014f  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0168 A[Catch: all -> 0x02e5, TryCatch #2 {, blocks: (B:4:0x0009, B:6:0x0017, B:9:0x001e, B:13:0x0028, B:15:0x0030, B:17:0x003b, B:18:0x0041, B:20:0x004b, B:21:0x006e, B:25:0x0058, B:84:0x0270, B:86:0x0277, B:88:0x027d, B:89:0x028d, B:91:0x0295, B:92:0x02b8, B:93:0x02e4, B:94:0x02a2, B:69:0x01fa, B:71:0x0201, B:73:0x0207, B:75:0x0215, B:77:0x021d, B:78:0x0240, B:51:0x0268, B:79:0x022a, B:41:0x0161, B:43:0x0168, B:45:0x016e, B:47:0x017e, B:49:0x0186, B:50:0x01a9, B:55:0x0193), top: B:3:0x0009 }] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0186 A[Catch: all -> 0x02e5, TryCatch #2 {, blocks: (B:4:0x0009, B:6:0x0017, B:9:0x001e, B:13:0x0028, B:15:0x0030, B:17:0x003b, B:18:0x0041, B:20:0x004b, B:21:0x006e, B:25:0x0058, B:84:0x0270, B:86:0x0277, B:88:0x027d, B:89:0x028d, B:91:0x0295, B:92:0x02b8, B:93:0x02e4, B:94:0x02a2, B:69:0x01fa, B:71:0x0201, B:73:0x0207, B:75:0x0215, B:77:0x021d, B:78:0x0240, B:51:0x0268, B:79:0x022a, B:41:0x0161, B:43:0x0168, B:45:0x016e, B:47:0x017e, B:49:0x0186, B:50:0x01a9, B:55:0x0193), top: B:3:0x0009 }] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0193 A[Catch: all -> 0x02e5, TryCatch #2 {, blocks: (B:4:0x0009, B:6:0x0017, B:9:0x001e, B:13:0x0028, B:15:0x0030, B:17:0x003b, B:18:0x0041, B:20:0x004b, B:21:0x006e, B:25:0x0058, B:84:0x0270, B:86:0x0277, B:88:0x027d, B:89:0x028d, B:91:0x0295, B:92:0x02b8, B:93:0x02e4, B:94:0x02a2, B:69:0x01fa, B:71:0x0201, B:73:0x0207, B:75:0x0215, B:77:0x021d, B:78:0x0240, B:51:0x0268, B:79:0x022a, B:41:0x0161, B:43:0x0168, B:45:0x016e, B:47:0x017e, B:49:0x0186, B:50:0x01a9, B:55:0x0193), top: B:3:0x0009 }] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0178  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0125 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0277 A[Catch: all -> 0x02e5, TryCatch #2 {, blocks: (B:4:0x0009, B:6:0x0017, B:9:0x001e, B:13:0x0028, B:15:0x0030, B:17:0x003b, B:18:0x0041, B:20:0x004b, B:21:0x006e, B:25:0x0058, B:84:0x0270, B:86:0x0277, B:88:0x027d, B:89:0x028d, B:91:0x0295, B:92:0x02b8, B:93:0x02e4, B:94:0x02a2, B:69:0x01fa, B:71:0x0201, B:73:0x0207, B:75:0x0215, B:77:0x021d, B:78:0x0240, B:51:0x0268, B:79:0x022a, B:41:0x0161, B:43:0x0168, B:45:0x016e, B:47:0x017e, B:49:0x0186, B:50:0x01a9, B:55:0x0193), top: B:3:0x0009 }] */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0295 A[Catch: all -> 0x02e5, TryCatch #2 {, blocks: (B:4:0x0009, B:6:0x0017, B:9:0x001e, B:13:0x0028, B:15:0x0030, B:17:0x003b, B:18:0x0041, B:20:0x004b, B:21:0x006e, B:25:0x0058, B:84:0x0270, B:86:0x0277, B:88:0x027d, B:89:0x028d, B:91:0x0295, B:92:0x02b8, B:93:0x02e4, B:94:0x02a2, B:69:0x01fa, B:71:0x0201, B:73:0x0207, B:75:0x0215, B:77:0x021d, B:78:0x0240, B:51:0x0268, B:79:0x022a, B:41:0x0161, B:43:0x0168, B:45:0x016e, B:47:0x017e, B:49:0x0186, B:50:0x01a9, B:55:0x0193), top: B:3:0x0009 }] */
    /* JADX WARN: Removed duplicated region for block: B:94:0x02a2 A[Catch: all -> 0x02e5, TryCatch #2 {, blocks: (B:4:0x0009, B:6:0x0017, B:9:0x001e, B:13:0x0028, B:15:0x0030, B:17:0x003b, B:18:0x0041, B:20:0x004b, B:21:0x006e, B:25:0x0058, B:84:0x0270, B:86:0x0277, B:88:0x027d, B:89:0x028d, B:91:0x0295, B:92:0x02b8, B:93:0x02e4, B:94:0x02a2, B:69:0x01fa, B:71:0x0201, B:73:0x0207, B:75:0x0215, B:77:0x021d, B:78:0x0240, B:51:0x0268, B:79:0x022a, B:41:0x0161, B:43:0x0168, B:45:0x016e, B:47:0x017e, B:49:0x0186, B:50:0x01a9, B:55:0x0193), top: B:3:0x0009 }] */
    /* JADX WARN: Removed duplicated region for block: B:97:0x0288  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public synchronized boolean processLicenseActivationResponse(String str, String str2, String str3, String str4, RightsObject rightsObject, Error error, String str5, String str6, int i) {
        boolean z;
        long j;
        boolean z2;
        int i2;
        ArrayList arrayList;
        ArrayList arrayList2;
        int i3;
        ContentValues contentValues;
        ContentValues contentValues2;
        boolean z3;
        boolean putValuesNoUpdate;
        ArrayList arrayList3;
        IUcmService ucmService;
        ArrayList arrayList4;
        int i4;
        String str7 = str3;
        synchronized (this) {
            enforcePermission();
            Log.d("EnterpriseLicenseService", "processLicenseActivationResponse()");
            boolean z4 = false;
            if (str6 != null && !str6.isEmpty() && !str6.equals(str)) {
                z = true;
                long clearCallingIdentity = Binder.clearCallingIdentity();
                ArrayList arrayList5 = null;
                String str8 = null;
                if (rightsObject != null) {
                    ArrayList arrayList6 = new ArrayList();
                    int errorCode = error.getErrorCode();
                    if (z) {
                        arrayList6 = (ArrayList) getPermissions(str);
                    }
                    ArrayList arrayList7 = arrayList6;
                    if (this.mElmPkgRecords.containsKey(str)) {
                        str8 = ((LicenseResultRecord) this.mElmPkgRecords.get(str)).licenseKey;
                    } else {
                        Log.w("EnterpriseLicenseService", "elm activation record not found for " + str);
                    }
                    LicenseResult licenseResult = new LicenseResult(str, str3, errorCode, LicenseResult.Type.ELM_ACTIVATION, (ArrayList) null, getMaskedText(str8));
                    sendElmResult(str3, errorCode, 800, str, str6, str5, i, null, arrayList7);
                    notifyElmObservers(str, licenseResult);
                    sendDeviceRegistrationIntentToKpmAgent(str7, str);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return true;
                }
                try {
                    contentValues = new ContentValues();
                    contentValues.put("pkgName", str);
                    contentValues2 = new ContentValues();
                    contentValues2.put("rightsObject", Utils.serializeObject(rightsObject));
                    contentValues2.put("instanceId", str4);
                    contentValues2.put("pkgVersion", str2);
                } catch (Exception e) {
                    e = e;
                    j = clearCallingIdentity;
                    z2 = false;
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    if (mEdmStorageProvider.getCount("LICENSE", contentValues) > 0) {
                        putValuesNoUpdate = mEdmStorageProvider.putValues("LICENSE", contentValues2, contentValues);
                    } else {
                        contentValues2.put("pkgName", str);
                        boolean putValuesNoUpdate2 = mEdmStorageProvider.putValuesNoUpdate("LICENSE", contentValues2);
                        if (putValuesNoUpdate2) {
                            z3 = putValuesNoUpdate2;
                            Log.w("EnterpriseLicenseService", "processLicenseActivationResponse(): ret = " + z3);
                            if (z3) {
                                try {
                                    mPMS.setLicensePermissionsForMDM(str);
                                    arrayList3 = new ArrayList(mPMS.getPackageGrantedPermissionsForMDM(str));
                                } catch (Throwable th2) {
                                    th = th2;
                                }
                                try {
                                    EnterpriseDeviceManagerService.getInstance().startDeferredServicesIfNeeded();
                                    arrayList5 = arrayList3;
                                } catch (Exception e2) {
                                    e = e2;
                                    arrayList5 = arrayList3;
                                    j = clearCallingIdentity;
                                    z2 = z3;
                                    try {
                                        Log.w("EnterpriseLicenseService", "processLicenseActivationResponse() failed");
                                        e.printStackTrace();
                                        ArrayList arrayList8 = new ArrayList();
                                        if (z2) {
                                            int errorCode2 = error.getErrorCode();
                                            if (z) {
                                                arrayList8 = (ArrayList) getPermissions(str);
                                            }
                                            arrayList2 = arrayList8;
                                            i3 = errorCode2;
                                        } else {
                                            arrayList2 = arrayList8;
                                            str7 = "fail";
                                            i3 = 301;
                                        }
                                        if (this.mElmPkgRecords.containsKey(str)) {
                                            str8 = ((LicenseResultRecord) this.mElmPkgRecords.get(str)).licenseKey;
                                        } else {
                                            Log.w("EnterpriseLicenseService", "elm activation record not found for " + str);
                                        }
                                        LicenseResult licenseResult2 = new LicenseResult(str, str7, i3, LicenseResult.Type.ELM_ACTIVATION, arrayList5, getMaskedText(str8));
                                        sendElmResult(str7, i3, 800, str, str6, str5, i, arrayList5, arrayList2);
                                        notifyElmObservers(str, licenseResult2);
                                        sendDeviceRegistrationIntentToKpmAgent(str7, str);
                                        Binder.restoreCallingIdentity(j);
                                        return z2;
                                    } catch (Throwable th3) {
                                        th = th3;
                                        z4 = z2;
                                        ArrayList arrayList9 = new ArrayList();
                                        if (z4) {
                                        }
                                        arrayList = arrayList9;
                                        if (this.mElmPkgRecords.containsKey(str)) {
                                        }
                                        LicenseResult licenseResult3 = new LicenseResult(str, str7, i2, LicenseResult.Type.ELM_ACTIVATION, arrayList5, getMaskedText(str8));
                                        sendElmResult(str7, i2, 800, str, str6, str5, i, arrayList5, arrayList);
                                        notifyElmObservers(str, licenseResult3);
                                        sendDeviceRegistrationIntentToKpmAgent(str7, str);
                                        Binder.restoreCallingIdentity(j);
                                        throw th;
                                    }
                                } catch (Throwable th4) {
                                    th = th4;
                                    arrayList5 = arrayList3;
                                    z4 = z3;
                                    j = clearCallingIdentity;
                                    ArrayList arrayList92 = new ArrayList();
                                    if (z4) {
                                    }
                                    arrayList = arrayList92;
                                    if (this.mElmPkgRecords.containsKey(str)) {
                                    }
                                    LicenseResult licenseResult32 = new LicenseResult(str, str7, i2, LicenseResult.Type.ELM_ACTIVATION, arrayList5, getMaskedText(str8));
                                    sendElmResult(str7, i2, 800, str, str6, str5, i, arrayList5, arrayList);
                                    notifyElmObservers(str, licenseResult32);
                                    sendDeviceRegistrationIntentToKpmAgent(str7, str);
                                    Binder.restoreCallingIdentity(j);
                                    throw th;
                                }
                            }
                            ucmService = getUcmService();
                            if (ucmService != null) {
                                if (z3) {
                                    ucmService.notifyLicenseStatus(str, str7, error.getErrorCode());
                                } else {
                                    ucmService.notifyLicenseStatus(str, "fail", 301);
                                }
                            }
                            notifyContainerLicenseStatus(str);
                            ArrayList arrayList10 = new ArrayList();
                            if (z3) {
                                arrayList4 = arrayList10;
                                str7 = "fail";
                                i4 = 301;
                            } else {
                                int errorCode3 = error.getErrorCode();
                                if (z) {
                                    arrayList10 = (ArrayList) getPermissions(str);
                                }
                                arrayList4 = arrayList10;
                                i4 = errorCode3;
                            }
                            if (this.mElmPkgRecords.containsKey(str)) {
                                Log.w("EnterpriseLicenseService", "elm activation record not found for " + str);
                            } else {
                                str8 = ((LicenseResultRecord) this.mElmPkgRecords.get(str)).licenseKey;
                            }
                            j = clearCallingIdentity;
                            LicenseResult licenseResult4 = new LicenseResult(str, str7, i4, LicenseResult.Type.ELM_ACTIVATION, arrayList5, getMaskedText(str8));
                            z2 = z3;
                            sendElmResult(str7, i4, 800, str, str6, str5, i, arrayList5, arrayList4);
                            notifyElmObservers(str, licenseResult4);
                            sendDeviceRegistrationIntentToKpmAgent(str7, str);
                            Binder.restoreCallingIdentity(j);
                            return z2;
                        }
                        Log.w("EnterpriseLicenseService", "check key field");
                        contentValues2.put("licenseKey", "na");
                        putValuesNoUpdate = mEdmStorageProvider.putValuesNoUpdate("LICENSE", contentValues2);
                    }
                    Log.w("EnterpriseLicenseService", "processLicenseActivationResponse(): ret = " + z3);
                    if (z3) {
                    }
                    ucmService = getUcmService();
                    if (ucmService != null) {
                    }
                    notifyContainerLicenseStatus(str);
                    ArrayList arrayList102 = new ArrayList();
                    if (z3) {
                    }
                    if (this.mElmPkgRecords.containsKey(str)) {
                    }
                    j = clearCallingIdentity;
                    LicenseResult licenseResult42 = new LicenseResult(str, str7, i4, LicenseResult.Type.ELM_ACTIVATION, arrayList5, getMaskedText(str8));
                    z2 = z3;
                    sendElmResult(str7, i4, 800, str, str6, str5, i, arrayList5, arrayList4);
                    notifyElmObservers(str, licenseResult42);
                    sendDeviceRegistrationIntentToKpmAgent(str7, str);
                    Binder.restoreCallingIdentity(j);
                    return z2;
                } catch (Throwable th5) {
                    th = th5;
                    j = clearCallingIdentity;
                    z2 = z3;
                    z4 = z2;
                    ArrayList arrayList922 = new ArrayList();
                    if (z4) {
                        int errorCode4 = error.getErrorCode();
                        if (z) {
                            i2 = errorCode4;
                            arrayList = (ArrayList) getPermissions(str);
                            if (this.mElmPkgRecords.containsKey(str)) {
                                Log.w("EnterpriseLicenseService", "elm activation record not found for " + str);
                            } else {
                                str8 = ((LicenseResultRecord) this.mElmPkgRecords.get(str)).licenseKey;
                            }
                            LicenseResult licenseResult322 = new LicenseResult(str, str7, i2, LicenseResult.Type.ELM_ACTIVATION, arrayList5, getMaskedText(str8));
                            sendElmResult(str7, i2, 800, str, str6, str5, i, arrayList5, arrayList);
                            notifyElmObservers(str, licenseResult322);
                            sendDeviceRegistrationIntentToKpmAgent(str7, str);
                            Binder.restoreCallingIdentity(j);
                            throw th;
                        }
                        i2 = errorCode4;
                    } else {
                        str7 = "fail";
                        i2 = 301;
                    }
                    arrayList = arrayList922;
                    if (this.mElmPkgRecords.containsKey(str)) {
                    }
                    LicenseResult licenseResult3222 = new LicenseResult(str, str7, i2, LicenseResult.Type.ELM_ACTIVATION, arrayList5, getMaskedText(str8));
                    sendElmResult(str7, i2, 800, str, str6, str5, i, arrayList5, arrayList);
                    notifyElmObservers(str, licenseResult3222);
                    sendDeviceRegistrationIntentToKpmAgent(str7, str);
                    Binder.restoreCallingIdentity(j);
                    throw th;
                }
                z3 = putValuesNoUpdate;
            }
            z = false;
            long clearCallingIdentity2 = Binder.clearCallingIdentity();
            ArrayList arrayList52 = null;
            String str82 = null;
            if (rightsObject != null) {
            }
        }
    }

    public boolean processKnoxLicenseResponse(String str, String str2, String str3, Error error, int i, int i2, String str4, RightsObject rightsObject, int i3) {
        ArrayList arrayList;
        boolean z;
        ArrayList arrayList2;
        enforcePermission();
        Log.d("EnterpriseLicenseService", "processKnoxLicenseResponse()");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        String str5 = null;
        try {
            try {
                if (rightsObject != null) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("pkgName", str);
                    ContentValues contentValues2 = new ContentValues();
                    contentValues2.put("rightsObject", Utils.serializeObject(rightsObject));
                    if (mEdmStorageProvider.getCount("LICENSE", contentValues) > 0) {
                        z = mEdmStorageProvider.putValues("LICENSE", contentValues2, contentValues);
                    } else {
                        contentValues2.put("instanceId", "-1");
                        contentValues2.put("pkgVersion", str2);
                        contentValues2.put("pkgName", str);
                        z = mEdmStorageProvider.putValuesNoUpdate("LICENSE", contentValues2);
                    }
                    if (z) {
                        Log.d("EnterpriseLicenseService", "processKnoxLicenseResponse(): License Table update.");
                        Log.d("EnterpriseLicenseService", "result setLicensePermissionForMDM(" + str + "): " + mPMS.setLicensePermissionsForMDM(str));
                        arrayList2 = new ArrayList(mPMS.getPackageGrantedPermissionsForMDM(str));
                        EnterpriseDeviceManagerService.getInstance().startDeferredServicesIfNeeded();
                    } else {
                        arrayList2 = null;
                    }
                    arrayList = arrayList2;
                } else {
                    Log.w("EnterpriseLicenseService", "processKnoxLicenseResponse().RO is null");
                    arrayList = null;
                    z = false;
                }
                ArrayList arrayList3 = z ? (ArrayList) getPermissions(str) : new ArrayList();
                if (i2 != 801 && this.mKlmPkgRecords.containsKey(str)) {
                    str5 = ((LicenseResultRecord) this.mKlmPkgRecords.get(str)).licenseKey;
                } else if (i2 != 801) {
                    Log.w("EnterpriseLicenseService", "klm activation record not found for " + str);
                }
                LicenseResult licenseResult = new LicenseResult(str, str3, error.getErrorCode(), LicenseResult.Type.fromKlmStatus(i2), arrayList, getMaskedKlm(str5));
                sendKlmResult(str3, error.getErrorCode(), i2, str, str4, i3, arrayList, arrayList3);
                IUcmService ucmService = getUcmService();
                if (ucmService != null) {
                    ucmService.notifyLicenseStatus(str, str3, error.getErrorCode());
                }
                notifyContainerLicenseStatus(str);
                notifyKlmObservers(str, licenseResult);
                sendDeviceRegistrationIntentToKpmAgent(str3, str);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            } catch (Exception e) {
                Log.w("EnterpriseLicenseService", "processKnoxLicenseResponse() failed");
                e.printStackTrace();
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void sendDeviceRegistrationIntentToKpmAgent(String str, String str2) {
        Log.d("EnterpriseLicenseService", "sendDeviceRegistrationIntentToKpmAgent : status : " + str);
        if ("success".equals(str)) {
            Intent intent = new Intent("com.samsung.android.knox.intent.action.DEVICE_REGISTRATION_INTERNAL");
            intent.putExtra("packageName", str2);
            intent.setPackage("com.samsung.android.knox.attestation");
            intent.addFlags(32);
            this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL, "com.samsung.android.knox.permission.KNOX_DEVICE_REGISTRATION_REQUEST_INTENT_INTERNAL");
        }
    }

    public RightsObject getRightsObject(String str) {
        RightsObject rightsObject;
        Exception e;
        byte[] blob;
        enforcePermission();
        if (str == null || str.trim().isEmpty()) {
            return null;
        }
        try {
            blob = mEdmStorageProvider.getBlob("LICENSE", "instanceId", str, "rightsObject");
        } catch (Exception e2) {
            rightsObject = null;
            e = e2;
        }
        if (blob == null) {
            return null;
        }
        rightsObject = (RightsObject) Utils.deserializeObject(blob);
        try {
            Log.d("EnterpriseLicenseService", "getRightsObject() - deserializeObject");
        } catch (Exception e3) {
            e = e3;
            Log.w("EnterpriseLicenseService", "getRightsObject() failed");
            e.printStackTrace();
            return rightsObject;
        }
        return rightsObject;
    }

    public RightsObject getRightsObjectByAdmin(String str) {
        RightsObject rightsObject;
        Exception e;
        byte[] blob;
        enforcePermission();
        if (str == null || str.trim().isEmpty()) {
            return null;
        }
        try {
            blob = mEdmStorageProvider.getBlob("LICENSE", "pkgName", str, "rightsObject");
        } catch (Exception e2) {
            rightsObject = null;
            e = e2;
        }
        if (blob == null) {
            return null;
        }
        rightsObject = (RightsObject) Utils.deserializeObject(blob);
        try {
            Log.d("EnterpriseLicenseService", "getRightsObjectByAdmin() - deserializeObject");
        } catch (Exception e3) {
            e = e3;
            Log.w("EnterpriseLicenseService", "getRightsObjectByAdmin() failed");
            e.printStackTrace();
            return rightsObject;
        }
        return rightsObject;
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x0397  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0313  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x0338  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x0347  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x0329  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0153 A[Catch: all -> 0x00fd, Exception -> 0x010b, TRY_ENTER, TRY_LEAVE, TryCatch #23 {Exception -> 0x010b, all -> 0x00fd, blocks: (B:156:0x00f7, B:40:0x0121, B:49:0x0153), top: B:155:0x00f7 }] */
    /* JADX WARN: Removed duplicated region for block: B:68:0x01d8 A[Catch: all -> 0x01ba, Exception -> 0x01c9, TRY_LEAVE, TryCatch #21 {Exception -> 0x01c9, all -> 0x01ba, blocks: (B:52:0x0165, B:55:0x0172, B:58:0x0179, B:60:0x019d, B:62:0x01a8, B:64:0x01b2, B:68:0x01d8), top: B:51:0x0165 }] */
    /* JADX WARN: Removed duplicated region for block: B:76:0x01ed  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x021a  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0207  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0381  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x03a6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean processLicenseValidationResult(String str, String str2, RightsObject rightsObject, Error error, String str3, String str4, String str5, String str6) {
        String str7;
        boolean z;
        String str8;
        String str9;
        Throwable th;
        String str10;
        String str11;
        EnterpriseLicenseService enterpriseLicenseService;
        String str12;
        String str13;
        EnterpriseLicenseService enterpriseLicenseService2;
        ContentValues contentValues;
        ContentValues contentValues2;
        boolean putValues;
        IUcmService ucmService;
        String str14;
        List knoxIds;
        int i;
        String str15;
        boolean z2;
        enforcePermission();
        Log.d("EnterpriseLicenseService", "processLicenseValidationResult()");
        boolean z3 = (str6 == null || str6.isEmpty() || str6.equals(str4)) ? false : true;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        String str16 = "com.samsung.android.knox.intent.extra.LICENSE_PERM_GROUP";
        String str17 = "fail";
        if (rightsObject == null) {
            Intent intent = new Intent("com.samsung.android.knox.intent.action.LICENSE_STATUS");
            if (z3) {
                intent.putExtra("com.samsung.android.knox.intent.extra.LICENSE_DATA_PACKAGENAME", str4);
                intent.putExtra("com.samsung.android.knox.intent.extra.LICENSE_DATA_PACKAGEVERSION", str5);
            }
            intent.putExtra("com.samsung.android.knox.intent.extra.LICENSE_STATUS", str);
            intent.putExtra("com.samsung.android.knox.intent.extra.LICENSE_ERROR_CODE", error.getErrorCode());
            intent.putExtra("com.samsung.android.knox.intent.extra.LICENSE_PERM_GROUP", str3);
            intent.putExtra("com.samsung.android.knox.intent.extra.LICENSE_RESULT_TYPE", 801);
            if (z3) {
                intent.setPackage(str6);
                this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
            }
            intent.setPackage(str4);
            this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
            notifyElmObservers(str4, new LicenseResult(str4, str, error.getErrorCode(), LicenseResult.Type.ELM_VALIDATION, (ArrayList) null, (String) null));
            sendDeviceRegistrationIntentToKpmAgent(str, str4);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return true;
        }
        try {
            contentValues = new ContentValues();
            try {
                contentValues.put("pkgName", str4);
                contentValues2 = new ContentValues();
            } catch (Exception e) {
                e = e;
                str7 = "com.samsung.android.knox.intent.extra.LICENSE_PERM_GROUP";
                str9 = "com.samsung.android.knox.intent.extra.LICENSE_RESULT_TYPE";
                str12 = str17;
                z = false;
            } catch (Throwable th2) {
                th = th2;
                str7 = "com.samsung.android.knox.intent.extra.LICENSE_PERM_GROUP";
                str9 = "com.samsung.android.knox.intent.extra.LICENSE_RESULT_TYPE";
            }
        } catch (Exception e2) {
            e = e2;
            str7 = "com.samsung.android.knox.intent.extra.LICENSE_PERM_GROUP";
            str12 = str17;
            z = false;
            str8 = str3;
            str13 = "EnterpriseLicenseService";
            str9 = "com.samsung.android.knox.intent.extra.LICENSE_RESULT_TYPE";
        } catch (Throwable th3) {
            str7 = "com.samsung.android.knox.intent.extra.LICENSE_PERM_GROUP";
            z = false;
            str8 = str3;
            str9 = "com.samsung.android.knox.intent.extra.LICENSE_RESULT_TYPE";
            th = th3;
            str10 = str6;
            str11 = str17;
            enterpriseLicenseService = this;
        }
        try {
            try {
                try {
                    contentValues2.put("rightsObject", Utils.serializeObject(rightsObject));
                    contentValues2.put("pkgVersion", str5);
                    if (mEdmStorageProvider.getCount("LICENSE", contentValues) > 0) {
                        try {
                            putValues = mEdmStorageProvider.putValues("LICENSE", contentValues2, contentValues);
                        } catch (Throwable th4) {
                            th = th4;
                            enterpriseLicenseService = this;
                            str7 = "com.samsung.android.knox.intent.extra.LICENSE_PERM_GROUP";
                            str11 = str17;
                            str9 = "com.samsung.android.knox.intent.extra.LICENSE_RESULT_TYPE";
                            z = false;
                            str8 = str3;
                            str10 = str6;
                            Intent intent2 = new Intent("com.samsung.android.knox.intent.action.LICENSE_STATUS");
                            if (z) {
                                intent2.putExtra("com.samsung.android.knox.intent.extra.LICENSE_STATUS", str11);
                                intent2.putExtra("com.samsung.android.knox.intent.extra.LICENSE_ERROR_CODE", 301);
                            } else {
                                if (z3) {
                                    intent2.putExtra("com.samsung.android.knox.intent.extra.LICENSE_DATA_PACKAGENAME", str4);
                                    intent2.putExtra("com.samsung.android.knox.intent.extra.LICENSE_DATA_PACKAGEVERSION", str5);
                                }
                                intent2.putExtra("com.samsung.android.knox.intent.extra.LICENSE_STATUS", str);
                                intent2.putExtra("com.samsung.android.knox.intent.extra.LICENSE_ERROR_CODE", error.getErrorCode());
                                intent2.putExtra(str7, str8);
                            }
                            intent2.putExtra(str9, 801);
                            if (z3) {
                                intent2.setPackage(str10);
                                enterpriseLicenseService.mContext.sendBroadcastAsUser(intent2, UserHandle.ALL);
                            }
                            intent2.setPackage(str4);
                            enterpriseLicenseService.mContext.sendBroadcastAsUser(intent2, UserHandle.ALL);
                            enterpriseLicenseService.notifyElmObservers(str4, new LicenseResult(str4, str, error.getErrorCode(), LicenseResult.Type.ELM_VALIDATION, (ArrayList) null, (String) null));
                            enterpriseLicenseService.sendDeviceRegistrationIntentToKpmAgent(str, str4);
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            throw th;
                        }
                    } else {
                        putValues = false;
                    }
                    try {
                        try {
                            str13 = "EnterpriseLicenseService";
                            try {
                                Log.w(str13, "processLicenseValidationResult(): ret = " + putValues);
                                if (putValues) {
                                    try {
                                        mPMS.setLicensePermissionsForMDM(str4);
                                    } catch (Exception e3) {
                                        e = e3;
                                        str7 = str16;
                                        z = putValues;
                                        str12 = str17;
                                        str9 = "com.samsung.android.knox.intent.extra.LICENSE_RESULT_TYPE";
                                        str8 = str3;
                                        try {
                                            Log.w(str13, "processLicenseValidationResult() failed");
                                            e.printStackTrace();
                                            Intent intent3 = new Intent("com.samsung.android.knox.intent.action.LICENSE_STATUS");
                                            if (z) {
                                            }
                                            intent3.putExtra(str9, 801);
                                            if (z3) {
                                            }
                                            intent3.setPackage(str4);
                                            enterpriseLicenseService2.mContext.sendBroadcastAsUser(intent3, UserHandle.ALL);
                                            enterpriseLicenseService2.notifyElmObservers(str4, new LicenseResult(str4, str, error.getErrorCode(), LicenseResult.Type.ELM_VALIDATION, (ArrayList) null, (String) null));
                                            enterpriseLicenseService2.sendDeviceRegistrationIntentToKpmAgent(str, str4);
                                            Binder.restoreCallingIdentity(clearCallingIdentity);
                                            return z;
                                        } catch (Throwable th5) {
                                            enterpriseLicenseService = this;
                                            str11 = str12;
                                            str10 = str6;
                                            th = th5;
                                            Intent intent22 = new Intent("com.samsung.android.knox.intent.action.LICENSE_STATUS");
                                            if (z) {
                                            }
                                            intent22.putExtra(str9, 801);
                                            if (z3) {
                                            }
                                            intent22.setPackage(str4);
                                            enterpriseLicenseService.mContext.sendBroadcastAsUser(intent22, UserHandle.ALL);
                                            enterpriseLicenseService.notifyElmObservers(str4, new LicenseResult(str4, str, error.getErrorCode(), LicenseResult.Type.ELM_VALIDATION, (ArrayList) null, (String) null));
                                            enterpriseLicenseService.sendDeviceRegistrationIntentToKpmAgent(str, str4);
                                            Binder.restoreCallingIdentity(clearCallingIdentity);
                                            throw th;
                                        }
                                    } catch (Throwable th6) {
                                        th = th6;
                                        str7 = str16;
                                        z = putValues;
                                        str11 = str17;
                                        str9 = "com.samsung.android.knox.intent.extra.LICENSE_RESULT_TYPE";
                                        str8 = str3;
                                        enterpriseLicenseService = this;
                                        str10 = str6;
                                        Intent intent222 = new Intent("com.samsung.android.knox.intent.action.LICENSE_STATUS");
                                        if (z) {
                                        }
                                        intent222.putExtra(str9, 801);
                                        if (z3) {
                                        }
                                        intent222.setPackage(str4);
                                        enterpriseLicenseService.mContext.sendBroadcastAsUser(intent222, UserHandle.ALL);
                                        enterpriseLicenseService.notifyElmObservers(str4, new LicenseResult(str4, str, error.getErrorCode(), LicenseResult.Type.ELM_VALIDATION, (ArrayList) null, (String) null));
                                        enterpriseLicenseService.sendDeviceRegistrationIntentToKpmAgent(str, str4);
                                        Binder.restoreCallingIdentity(clearCallingIdentity);
                                        throw th;
                                    }
                                }
                                ucmService = getUcmService();
                            } catch (Exception e4) {
                                e = e4;
                            }
                        } catch (Exception e5) {
                            e = e5;
                            str7 = "com.samsung.android.knox.intent.extra.LICENSE_PERM_GROUP";
                            z = putValues;
                            str13 = "EnterpriseLicenseService";
                        }
                    } catch (Throwable th7) {
                        th = th7;
                    }
                } catch (Exception e6) {
                    e = e6;
                    str7 = "com.samsung.android.knox.intent.extra.LICENSE_PERM_GROUP";
                    str12 = str17;
                    str13 = "EnterpriseLicenseService";
                    str9 = "com.samsung.android.knox.intent.extra.LICENSE_RESULT_TYPE";
                    z = false;
                    str8 = str3;
                }
            } catch (Throwable th8) {
                th = th8;
                str7 = "com.samsung.android.knox.intent.extra.LICENSE_PERM_GROUP";
                str9 = "com.samsung.android.knox.intent.extra.LICENSE_RESULT_TYPE";
                z = false;
                str8 = str3;
                enterpriseLicenseService = this;
                str10 = str6;
                th = th;
                str11 = str17;
                Intent intent2222 = new Intent("com.samsung.android.knox.intent.action.LICENSE_STATUS");
                if (z) {
                }
                intent2222.putExtra(str9, 801);
                if (z3) {
                }
                intent2222.setPackage(str4);
                enterpriseLicenseService.mContext.sendBroadcastAsUser(intent2222, UserHandle.ALL);
                enterpriseLicenseService.notifyElmObservers(str4, new LicenseResult(str4, str, error.getErrorCode(), LicenseResult.Type.ELM_VALIDATION, (ArrayList) null, (String) null));
                enterpriseLicenseService.sendDeviceRegistrationIntentToKpmAgent(str, str4);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
            try {
                try {
                    try {
                        try {
                            if (ucmService != null) {
                                if (!putValues) {
                                    str14 = str17;
                                    try {
                                        ucmService.notifyLicenseStatus(str4, str14, 301);
                                        knoxIds = getPersonaManagerAdapter().getKnoxIds(false);
                                        str17 = str14;
                                        i = 0;
                                        while (i < knoxIds.size()) {
                                            int intValue = ((Integer) knoxIds.get(i)).intValue();
                                            List list = knoxIds;
                                            boolean isLicenseLocked = isLicenseLocked(intValue);
                                            str15 = str16;
                                            try {
                                                if (!getPersonaManagerAdapter().isPremiumContainer(intValue) && !isLicenseLocked) {
                                                    i++;
                                                    knoxIds = list;
                                                    str16 = str15;
                                                }
                                                Log.d(str13, "processLicenseValidationResult():  u" + intValue + " isLicenseLocked ? " + isLicenseLocked);
                                                z2 = true;
                                                break;
                                            } catch (Exception e7) {
                                                e = e7;
                                                str8 = str3;
                                                z = putValues;
                                                str12 = str17;
                                                str7 = str15;
                                                str9 = "com.samsung.android.knox.intent.extra.LICENSE_RESULT_TYPE";
                                                Log.w(str13, "processLicenseValidationResult() failed");
                                                e.printStackTrace();
                                                Intent intent32 = new Intent("com.samsung.android.knox.intent.action.LICENSE_STATUS");
                                                if (z) {
                                                }
                                                intent32.putExtra(str9, 801);
                                                if (z3) {
                                                }
                                                intent32.setPackage(str4);
                                                enterpriseLicenseService2.mContext.sendBroadcastAsUser(intent32, UserHandle.ALL);
                                                enterpriseLicenseService2.notifyElmObservers(str4, new LicenseResult(str4, str, error.getErrorCode(), LicenseResult.Type.ELM_VALIDATION, (ArrayList) null, (String) null));
                                                enterpriseLicenseService2.sendDeviceRegistrationIntentToKpmAgent(str, str4);
                                                Binder.restoreCallingIdentity(clearCallingIdentity);
                                                return z;
                                            } catch (Throwable th9) {
                                                str8 = str3;
                                                th = th9;
                                                z = putValues;
                                                str11 = str17;
                                                str7 = str15;
                                                str9 = "com.samsung.android.knox.intent.extra.LICENSE_RESULT_TYPE";
                                                enterpriseLicenseService = this;
                                                str10 = str6;
                                                Intent intent22222 = new Intent("com.samsung.android.knox.intent.action.LICENSE_STATUS");
                                                if (z) {
                                                }
                                                intent22222.putExtra(str9, 801);
                                                if (z3) {
                                                }
                                                intent22222.setPackage(str4);
                                                enterpriseLicenseService.mContext.sendBroadcastAsUser(intent22222, UserHandle.ALL);
                                                enterpriseLicenseService.notifyElmObservers(str4, new LicenseResult(str4, str, error.getErrorCode(), LicenseResult.Type.ELM_VALIDATION, (ArrayList) null, (String) null));
                                                enterpriseLicenseService.sendDeviceRegistrationIntentToKpmAgent(str, str4);
                                                Binder.restoreCallingIdentity(clearCallingIdentity);
                                                throw th;
                                            }
                                        }
                                        str15 = str16;
                                        z2 = false;
                                        if (!z2 && getPersonaManagerAdapter().isDoEnabled(0) && (getPersonaManagerAdapter().isPremiumContainer(0) || isLicenseLocked(0))) {
                                            z2 = true;
                                        }
                                        if (z2) {
                                            notifyContainerLicenseStatus(str4);
                                        }
                                        ArrayList arrayList = new ArrayList(mPMS.getPackageGrantedPermissionsForMDM(str4));
                                        Intent intent4 = new Intent("com.samsung.android.knox.intent.action.LICENSE_STATUS");
                                        if (putValues) {
                                            intent4.putExtra("com.samsung.android.knox.intent.extra.LICENSE_STATUS", str17);
                                            intent4.putExtra("com.samsung.android.knox.intent.extra.LICENSE_ERROR_CODE", 301);
                                        } else {
                                            if (z3) {
                                                intent4.putExtra("com.samsung.android.knox.intent.extra.LICENSE_DATA_PACKAGENAME", str4);
                                                intent4.putExtra("com.samsung.android.knox.intent.extra.LICENSE_DATA_PACKAGEVERSION", str5);
                                            }
                                            intent4.putExtra("com.samsung.android.knox.intent.extra.LICENSE_STATUS", str);
                                            intent4.putExtra("com.samsung.android.knox.intent.extra.LICENSE_ERROR_CODE", error.getErrorCode());
                                            intent4.putExtra(str15, str3);
                                        }
                                        intent4.putExtra("com.samsung.android.knox.intent.extra.LICENSE_RESULT_TYPE", 801);
                                        if (z3) {
                                            intent4.setPackage(str6);
                                            this.mContext.sendBroadcastAsUser(intent4, UserHandle.ALL);
                                        }
                                        intent4.setPackage(str4);
                                        this.mContext.sendBroadcastAsUser(intent4, UserHandle.ALL);
                                        notifyElmObservers(str4, new LicenseResult(str4, str, error.getErrorCode(), LicenseResult.Type.ELM_VALIDATION, arrayList, (String) null));
                                        sendDeviceRegistrationIntentToKpmAgent(str, str4);
                                        Binder.restoreCallingIdentity(clearCallingIdentity);
                                        return putValues;
                                    } catch (Exception e8) {
                                        e = e8;
                                        str12 = str14;
                                        str7 = "com.samsung.android.knox.intent.extra.LICENSE_PERM_GROUP";
                                        z = putValues;
                                        str9 = "com.samsung.android.knox.intent.extra.LICENSE_RESULT_TYPE";
                                        str8 = str3;
                                        Log.w(str13, "processLicenseValidationResult() failed");
                                        e.printStackTrace();
                                        Intent intent322 = new Intent("com.samsung.android.knox.intent.action.LICENSE_STATUS");
                                        if (z) {
                                        }
                                        intent322.putExtra(str9, 801);
                                        if (z3) {
                                        }
                                        intent322.setPackage(str4);
                                        enterpriseLicenseService2.mContext.sendBroadcastAsUser(intent322, UserHandle.ALL);
                                        enterpriseLicenseService2.notifyElmObservers(str4, new LicenseResult(str4, str, error.getErrorCode(), LicenseResult.Type.ELM_VALIDATION, (ArrayList) null, (String) null));
                                        enterpriseLicenseService2.sendDeviceRegistrationIntentToKpmAgent(str, str4);
                                        Binder.restoreCallingIdentity(clearCallingIdentity);
                                        return z;
                                    } catch (Throwable th10) {
                                        th = th10;
                                        str11 = str14;
                                        str7 = "com.samsung.android.knox.intent.extra.LICENSE_PERM_GROUP";
                                        z = putValues;
                                        str9 = "com.samsung.android.knox.intent.extra.LICENSE_RESULT_TYPE";
                                        str8 = str3;
                                        enterpriseLicenseService = this;
                                        str10 = str6;
                                        Intent intent222222 = new Intent("com.samsung.android.knox.intent.action.LICENSE_STATUS");
                                        if (z) {
                                        }
                                        intent222222.putExtra(str9, 801);
                                        if (z3) {
                                        }
                                        intent222222.setPackage(str4);
                                        enterpriseLicenseService.mContext.sendBroadcastAsUser(intent222222, UserHandle.ALL);
                                        enterpriseLicenseService.notifyElmObservers(str4, new LicenseResult(str4, str, error.getErrorCode(), LicenseResult.Type.ELM_VALIDATION, (ArrayList) null, (String) null));
                                        enterpriseLicenseService.sendDeviceRegistrationIntentToKpmAgent(str, str4);
                                        Binder.restoreCallingIdentity(clearCallingIdentity);
                                        throw th;
                                    }
                                }
                                ucmService.notifyLicenseStatus(str4, str, error.getErrorCode());
                            }
                            ArrayList arrayList2 = new ArrayList(mPMS.getPackageGrantedPermissionsForMDM(str4));
                            Intent intent42 = new Intent("com.samsung.android.knox.intent.action.LICENSE_STATUS");
                            if (putValues) {
                            }
                            intent42.putExtra("com.samsung.android.knox.intent.extra.LICENSE_RESULT_TYPE", 801);
                            if (z3) {
                            }
                            intent42.setPackage(str4);
                            this.mContext.sendBroadcastAsUser(intent42, UserHandle.ALL);
                            notifyElmObservers(str4, new LicenseResult(str4, str, error.getErrorCode(), LicenseResult.Type.ELM_VALIDATION, arrayList2, (String) null));
                            sendDeviceRegistrationIntentToKpmAgent(str, str4);
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            return putValues;
                        } catch (Throwable th11) {
                            str8 = str3;
                            str7 = str15;
                            str9 = "com.samsung.android.knox.intent.extra.LICENSE_RESULT_TYPE";
                            th = th11;
                            z = putValues;
                            str11 = str17;
                            enterpriseLicenseService = this;
                            str10 = str6;
                            Intent intent2222222 = new Intent("com.samsung.android.knox.intent.action.LICENSE_STATUS");
                            if (z) {
                            }
                            intent2222222.putExtra(str9, 801);
                            if (z3) {
                            }
                            intent2222222.setPackage(str4);
                            enterpriseLicenseService.mContext.sendBroadcastAsUser(intent2222222, UserHandle.ALL);
                            enterpriseLicenseService.notifyElmObservers(str4, new LicenseResult(str4, str, error.getErrorCode(), LicenseResult.Type.ELM_VALIDATION, (ArrayList) null, (String) null));
                            enterpriseLicenseService.sendDeviceRegistrationIntentToKpmAgent(str, str4);
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            throw th;
                        }
                    } catch (Exception e9) {
                        e = e9;
                        str8 = str3;
                        z = putValues;
                        str7 = str15;
                        str9 = "com.samsung.android.knox.intent.extra.LICENSE_RESULT_TYPE";
                        str12 = str17;
                        Log.w(str13, "processLicenseValidationResult() failed");
                        e.printStackTrace();
                        Intent intent3222 = new Intent("com.samsung.android.knox.intent.action.LICENSE_STATUS");
                        if (z) {
                            intent3222.putExtra("com.samsung.android.knox.intent.extra.LICENSE_STATUS", str12);
                            intent3222.putExtra("com.samsung.android.knox.intent.extra.LICENSE_ERROR_CODE", 301);
                        } else {
                            if (z3) {
                                intent3222.putExtra("com.samsung.android.knox.intent.extra.LICENSE_DATA_PACKAGENAME", str4);
                                intent3222.putExtra("com.samsung.android.knox.intent.extra.LICENSE_DATA_PACKAGEVERSION", str5);
                            }
                            intent3222.putExtra("com.samsung.android.knox.intent.extra.LICENSE_STATUS", str);
                            intent3222.putExtra("com.samsung.android.knox.intent.extra.LICENSE_ERROR_CODE", error.getErrorCode());
                            intent3222.putExtra(str7, str8);
                        }
                        intent3222.putExtra(str9, 801);
                        if (z3) {
                            enterpriseLicenseService2 = this;
                        } else {
                            intent3222.setPackage(str6);
                            enterpriseLicenseService2 = this;
                            enterpriseLicenseService2.mContext.sendBroadcastAsUser(intent3222, UserHandle.ALL);
                        }
                        intent3222.setPackage(str4);
                        enterpriseLicenseService2.mContext.sendBroadcastAsUser(intent3222, UserHandle.ALL);
                        enterpriseLicenseService2.notifyElmObservers(str4, new LicenseResult(str4, str, error.getErrorCode(), LicenseResult.Type.ELM_VALIDATION, (ArrayList) null, (String) null));
                        enterpriseLicenseService2.sendDeviceRegistrationIntentToKpmAgent(str, str4);
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return z;
                    }
                } catch (Throwable th12) {
                    th = th12;
                    str8 = str3;
                    z = putValues;
                    str7 = str15;
                    str9 = "com.samsung.android.knox.intent.extra.LICENSE_RESULT_TYPE";
                    th = th;
                    enterpriseLicenseService = this;
                    str11 = str17;
                    str10 = str6;
                    Intent intent22222222 = new Intent("com.samsung.android.knox.intent.action.LICENSE_STATUS");
                    if (z) {
                    }
                    intent22222222.putExtra(str9, 801);
                    if (z3) {
                    }
                    intent22222222.setPackage(str4);
                    enterpriseLicenseService.mContext.sendBroadcastAsUser(intent22222222, UserHandle.ALL);
                    enterpriseLicenseService.notifyElmObservers(str4, new LicenseResult(str4, str, error.getErrorCode(), LicenseResult.Type.ELM_VALIDATION, (ArrayList) null, (String) null));
                    enterpriseLicenseService.sendDeviceRegistrationIntentToKpmAgent(str, str4);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
                knoxIds = getPersonaManagerAdapter().getKnoxIds(false);
                str17 = str14;
                i = 0;
                while (i < knoxIds.size()) {
                }
                str15 = str16;
                z2 = false;
                if (!z2) {
                    z2 = true;
                }
                if (z2) {
                }
            } catch (Exception e10) {
                e = e10;
                str17 = str14;
                str7 = str16;
                z = putValues;
                str9 = "com.samsung.android.knox.intent.extra.LICENSE_RESULT_TYPE";
                str8 = str3;
                str12 = str17;
                Log.w(str13, "processLicenseValidationResult() failed");
                e.printStackTrace();
                Intent intent32222 = new Intent("com.samsung.android.knox.intent.action.LICENSE_STATUS");
                if (z) {
                }
                intent32222.putExtra(str9, 801);
                if (z3) {
                }
                intent32222.setPackage(str4);
                enterpriseLicenseService2.mContext.sendBroadcastAsUser(intent32222, UserHandle.ALL);
                enterpriseLicenseService2.notifyElmObservers(str4, new LicenseResult(str4, str, error.getErrorCode(), LicenseResult.Type.ELM_VALIDATION, (ArrayList) null, (String) null));
                enterpriseLicenseService2.sendDeviceRegistrationIntentToKpmAgent(str, str4);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return z;
            } catch (Throwable th13) {
                th = th13;
                str17 = str14;
                str7 = str16;
                z = putValues;
                str9 = "com.samsung.android.knox.intent.extra.LICENSE_RESULT_TYPE";
                str8 = str3;
                th = th;
                enterpriseLicenseService = this;
                str11 = str17;
                str10 = str6;
                Intent intent222222222 = new Intent("com.samsung.android.knox.intent.action.LICENSE_STATUS");
                if (z) {
                }
                intent222222222.putExtra(str9, 801);
                if (z3) {
                }
                intent222222222.setPackage(str4);
                enterpriseLicenseService.mContext.sendBroadcastAsUser(intent222222222, UserHandle.ALL);
                enterpriseLicenseService.notifyElmObservers(str4, new LicenseResult(str4, str, error.getErrorCode(), LicenseResult.Type.ELM_VALIDATION, (ArrayList) null, (String) null));
                enterpriseLicenseService.sendDeviceRegistrationIntentToKpmAgent(str, str4);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
            str14 = str17;
        } catch (Exception e11) {
            e = e11;
            str7 = "com.samsung.android.knox.intent.extra.LICENSE_PERM_GROUP";
            str12 = str17;
            str9 = "com.samsung.android.knox.intent.extra.LICENSE_RESULT_TYPE";
            z = false;
            str8 = str3;
            str13 = "EnterpriseLicenseService";
            Log.w(str13, "processLicenseValidationResult() failed");
            e.printStackTrace();
            Intent intent322222 = new Intent("com.samsung.android.knox.intent.action.LICENSE_STATUS");
            if (z) {
            }
            intent322222.putExtra(str9, 801);
            if (z3) {
            }
            intent322222.setPackage(str4);
            enterpriseLicenseService2.mContext.sendBroadcastAsUser(intent322222, UserHandle.ALL);
            enterpriseLicenseService2.notifyElmObservers(str4, new LicenseResult(str4, str, error.getErrorCode(), LicenseResult.Type.ELM_VALIDATION, (ArrayList) null, (String) null));
            enterpriseLicenseService2.sendDeviceRegistrationIntentToKpmAgent(str, str4);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return z;
        } catch (Throwable th14) {
            th = th14;
            str7 = "com.samsung.android.knox.intent.extra.LICENSE_PERM_GROUP";
            str9 = "com.samsung.android.knox.intent.extra.LICENSE_RESULT_TYPE";
            z = false;
            str8 = str3;
            enterpriseLicenseService = this;
            str10 = str6;
            th = th;
            str11 = str17;
            Intent intent2222222222 = new Intent("com.samsung.android.knox.intent.action.LICENSE_STATUS");
            if (z) {
            }
            intent2222222222.putExtra(str9, 801);
            if (z3) {
            }
            intent2222222222.setPackage(str4);
            enterpriseLicenseService.mContext.sendBroadcastAsUser(intent2222222222, UserHandle.ALL);
            enterpriseLicenseService.notifyElmObservers(str4, new LicenseResult(str4, str, error.getErrorCode(), LicenseResult.Type.ELM_VALIDATION, (ArrayList) null, (String) null));
            enterpriseLicenseService.sendDeviceRegistrationIntentToKpmAgent(str, str4);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public Bundle getApiCallData(String str) {
        enforcePermission();
        if (str != null && !str.trim().isEmpty()) {
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put("instanceId", str);
                ContentValues value = mEdmStorageProvider.getValue("LICENSE", "pkgName", contentValues);
                if (value == null) {
                    Log.w("EnterpriseLicenseService", "getApiCallData(): result is null, Record does not exist");
                    return null;
                }
                String asString = value.getAsString("pkgName");
                if (asString == null) {
                    Log.w("EnterpriseLicenseService", "getApiCallData(): pkgName is null, Record does not exist");
                    return null;
                }
                return LicenseLog.getLog(asString);
            } catch (Exception e) {
                Log.w("EnterpriseLicenseService", "getApiCallData() failed");
                e.printStackTrace();
            }
        }
        return null;
    }

    public boolean deleteApiCallData(String str, String str2, Error error) {
        enforcePermission();
        if (str2 != null && !str2.trim().isEmpty()) {
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put("instanceId", str2);
                ContentValues value = mEdmStorageProvider.getValue("LICENSE", "pkgName", contentValues);
                if (value == null) {
                    Log.w("EnterpriseLicenseService", "deleteApiCallData(): result is null");
                    return false;
                }
                String asString = value.getAsString("pkgName");
                if (asString == null) {
                    Log.w("EnterpriseLicenseService", "deleteApiCallData(): Record does not exist");
                    return false;
                }
                return LicenseLog.deleteLog(asString);
            } catch (Exception e) {
                Log.w("EnterpriseLicenseService", "deleteApiCallData() failed");
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean deleteApiCallDataByAdmin(String str) {
        enforcePermission();
        if (str != null && !str.trim().isEmpty()) {
            try {
                return LicenseLog.deleteLog(str);
            } catch (Exception e) {
                Log.w("EnterpriseLicenseService", "deleteApiCallDataByAdmin() failed");
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean deleteAllApiCallData() {
        enforcePermission();
        try {
            return LicenseLog.deleteAllLog();
        } catch (Exception e) {
            Log.w("EnterpriseLicenseService", "deleteAllApiCallData() failed");
            e.printStackTrace();
            return false;
        }
    }

    public Bundle getApiCallDataByAdmin(ContextInfo contextInfo, String str) {
        try {
            this.mContext.enforceCallingPermission("com.samsung.android.knox.permission.KNOX_LICENSE_LOG", null);
            if (str != null && !str.trim().isEmpty()) {
                try {
                    return LicenseLog.getLog(str);
                } catch (Exception unused) {
                    Log.w("EnterpriseLicenseService", "getApiCallDataByAdmin() failed");
                }
            }
            return null;
        } catch (SecurityException e) {
            String message = e.getMessage();
            if (message != null) {
                message = message + ",com.samsung.android.knox.permission.KNOX_LICENSE_LOG";
            }
            throw new SecurityException(message);
        }
    }

    public LicenseInfo getLicenseInfo(String str) {
        enforcePermission();
        if (str == null || str.trim().isEmpty()) {
            return null;
        }
        try {
            List<ContentValues> valuesList = mEdmStorageProvider.getValuesList("LICENSE", new String[]{"pkgName", "instanceId", "pkgVersion"}, null);
            if (valuesList == null || valuesList.isEmpty()) {
                return null;
            }
            for (ContentValues contentValues : valuesList) {
                String asString = contentValues.getAsString("instanceId");
                if (asString != null && asString.equals(str)) {
                    return new LicenseInfo(contentValues.getAsString("pkgName"), asString, contentValues.getAsString("pkgVersion"));
                }
            }
            return null;
        } catch (Exception unused) {
            Log.w("EnterpriseLicenseService", "getLicenseInfo() failed");
            return null;
        }
    }

    public LicenseInfo getLicenseInfoByAdmin(String str) {
        enforcePermission();
        if (str == null || str.trim().isEmpty()) {
            return null;
        }
        try {
            List<ContentValues> valuesList = mEdmStorageProvider.getValuesList("LICENSE", new String[]{"pkgName", "instanceId", "pkgVersion"}, null);
            if (valuesList == null || valuesList.isEmpty()) {
                return null;
            }
            for (ContentValues contentValues : valuesList) {
                String asString = contentValues.getAsString("pkgName");
                if (asString != null && asString.equals(str)) {
                    return new LicenseInfo(str, contentValues.getAsString("instanceId"), contentValues.getAsString("pkgVersion"));
                }
            }
            return null;
        } catch (Exception unused) {
            Log.w("EnterpriseLicenseService", "getLicenseInfoByAdmin() failed");
            return null;
        }
    }

    public LicenseInfo[] getAllLicenseInfo() {
        enforcePermission();
        try {
            List<ContentValues> valuesList = mEdmStorageProvider.getValuesList("LICENSE", new String[]{"pkgName", "instanceId", "pkgVersion"}, null);
            if (valuesList != null && !valuesList.isEmpty()) {
                ArrayList arrayList = new ArrayList();
                for (ContentValues contentValues : valuesList) {
                    arrayList.add(new LicenseInfo(contentValues.getAsString("pkgName"), contentValues.getAsString("instanceId"), contentValues.getAsString("pkgVersion")));
                }
                if (arrayList.size() > 0) {
                    return (LicenseInfo[]) arrayList.toArray(new LicenseInfo[arrayList.size()]);
                }
            }
        } catch (Exception unused) {
            Log.w("EnterpriseLicenseService", "getLicenseInfo() failed");
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0152 A[Catch: all -> 0x0193, TRY_ENTER, TryCatch #2 {, blocks: (B:4:0x0009, B:58:0x0085, B:70:0x00b4, B:34:0x0133, B:10:0x013f, B:13:0x0152, B:15:0x015e, B:17:0x0161, B:19:0x0166, B:24:0x017c, B:81:0x018f, B:82:0x0192, B:45:0x002a, B:48:0x0036, B:50:0x0046, B:52:0x005f, B:54:0x0070, B:57:0x0082, B:63:0x0090, B:64:0x0093, B:66:0x0098, B:26:0x00c7, B:30:0x00eb, B:32:0x00f3, B:33:0x010d, B:37:0x010a, B:38:0x00d6, B:40:0x00e7, B:9:0x0138, B:43:0x0145), top: B:3:0x0009, inners: #5 }] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x017c A[Catch: all -> 0x0193, TRY_LEAVE, TryCatch #2 {, blocks: (B:4:0x0009, B:58:0x0085, B:70:0x00b4, B:34:0x0133, B:10:0x013f, B:13:0x0152, B:15:0x015e, B:17:0x0161, B:19:0x0166, B:24:0x017c, B:81:0x018f, B:82:0x0192, B:45:0x002a, B:48:0x0036, B:50:0x0046, B:52:0x005f, B:54:0x0070, B:57:0x0082, B:63:0x0090, B:64:0x0093, B:66:0x0098, B:26:0x00c7, B:30:0x00eb, B:32:0x00f3, B:33:0x010d, B:37:0x010a, B:38:0x00d6, B:40:0x00e7, B:9:0x0138, B:43:0x0145), top: B:3:0x0009, inners: #5 }] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00c7 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0098 A[Catch: all -> 0x00bc, Exception -> 0x00bf, TRY_LEAVE, TryCatch #0 {Exception -> 0x00bf, blocks: (B:45:0x002a, B:48:0x0036, B:50:0x0046, B:63:0x0090, B:64:0x0093, B:66:0x0098), top: B:44:0x002a }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public synchronized void activateLicense(ContextInfo contextInfo, String str, String str2, String str3, ILicenseResultCallback iLicenseResultCallback) {
        String str4;
        String[] strArr;
        int length;
        int i;
        String str5;
        String str6;
        Log.d("EnterpriseLicenseService", "activateLicense()");
        int callingOrUserUid = Utils.getCallingOrUserUid(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        int callingPid = Binder.getCallingPid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        String packageName = getPackageName(callingOrUserUid, callingPid);
        if (str2 != null) {
            try {
                try {
                } catch (Exception e) {
                    e = e;
                    str4 = str2;
                    Log.e("EnterpriseLicenseService", "activateLicense() failed.");
                    e.printStackTrace();
                    str5 = str4;
                    if (str5 != null) {
                    }
                }
                if (!str2.trim().isEmpty()) {
                    String[] packagesForUid = this.mContext.getPackageManager().getPackagesForUid(callingOrUserUid);
                    if (!isActionAllowedForAnotherPackage(str2, packagesForUid, callingOrCurrentUserId)) {
                        Log.e("EnterpriseLicenseService", packageName + " is not authorized to activate license for " + str2);
                        try {
                        } catch (Exception e2) {
                            e = e2;
                            strArr = packagesForUid;
                        }
                        if (IPackageManager.Stub.asInterface(ServiceManager.getService("package")).getPackageInfo(str2, 0L, callingOrCurrentUserId) == null) {
                            strArr = packagesForUid;
                            length = strArr.length;
                            i = 0;
                            while (i < length) {
                                int i2 = i;
                                sendElmResult("fail", 204, 800, strArr[i], packageName, new LicenseResultRecord(str, iLicenseResultCallback));
                                i = i2 + 1;
                            }
                            return;
                        }
                        strArr = packagesForUid;
                        try {
                            sendElmResult("fail", 204, 800, str2, packageName, new LicenseResultRecord(str, iLicenseResultCallback));
                            return;
                        } catch (Exception e3) {
                            e = e3;
                            e.printStackTrace();
                            length = strArr.length;
                            i = 0;
                            while (i < length) {
                            }
                            return;
                        }
                    }
                    str4 = str2;
                    if (str4 != null) {
                        try {
                            if (!str4.trim().isEmpty()) {
                                if (str3 != null) {
                                    str6 = str3;
                                } else {
                                    PackageInfo packageInfo = IPackageManager.Stub.asInterface(ServiceManager.getService("package")).getPackageInfo(str4, 0L, callingOrCurrentUserId);
                                    str6 = packageInfo != null ? packageInfo.versionName : null;
                                }
                                if (this.mElmPkgRecords.containsKey(str4)) {
                                    Log.w("EnterpriseLicenseService", "Another operation is already in progress for " + str4);
                                } else {
                                    registerElmLicenseResultRecord(str4, str, iLicenseResultCallback);
                                }
                                final Bundle bundle = new Bundle();
                                bundle.putString("com.samsung.android.knox.intent.extra.LICENSE_DATA_PACKAGENAME", str4);
                                bundle.putString("com.samsung.android.knox.intent.extra.LICENSE_DATA_PACKAGEVERSION", str6);
                                bundle.putString("com.samsung.android.knox.intent.extra.LICENSE_DATA_REQUEST_PACKAGENAME", packageName);
                                bundle.putString("com.samsung.android.knox.intent.extra.KNOX_LICENSE_KEY", str);
                                new Thread(new Runnable() { // from class: com.android.server.enterprise.license.EnterpriseLicenseService$$ExternalSyntheticLambda1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        EnterpriseLicenseService.this.lambda$activateLicense$0(bundle);
                                    }
                                }).start();
                                return;
                            }
                        } catch (Exception e4) {
                            e = e4;
                            Log.e("EnterpriseLicenseService", "activateLicense() failed.");
                            e.printStackTrace();
                            str5 = str4;
                            if (str5 != null) {
                            }
                        }
                    }
                    Log.e("EnterpriseLicenseService", "Invalid targetPackageName.");
                    str5 = str4;
                    if (str5 != null) {
                        String[] packagesForUid2 = this.mContext.getPackageManager().getPackagesForUid(callingOrUserUid);
                        if (packagesForUid2 != null && packagesForUid2.length > 0) {
                            for (String str7 : packagesForUid2) {
                                sendElmResult("fail", 301, 800, str7, packageName, new LicenseResultRecord(str, iLicenseResultCallback));
                            }
                        }
                    } else {
                        sendElmResult("fail", 301, 800, str5, packageName, new LicenseResultRecord(str, iLicenseResultCallback));
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        str4 = packageName;
        if (str4 != null) {
        }
        Log.e("EnterpriseLicenseService", "Invalid targetPackageName.");
        str5 = str4;
        if (str5 != null) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$activateLicense$0(Bundle bundle) {
        callLicenseAgent("ELMRegistrationInternal", null, bundle);
    }

    public final IPersonaManagerAdapter getPersonaManagerAdapter() {
        return (IPersonaManagerAdapter) AdapterRegistry.getAdapter(IPersonaManagerAdapter.class);
    }

    public synchronized void activateLicenseForUMC(ContextInfo contextInfo, String str, String str2, String str3) {
        activateLicense(contextInfo, str, str2, str3, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0149 A[Catch: all -> 0x018a, TRY_ENTER, TryCatch #4 {, blocks: (B:4:0x0009, B:52:0x0085, B:64:0x00b4, B:32:0x012a, B:10:0x0136, B:13:0x0149, B:15:0x0155, B:17:0x0158, B:19:0x015d, B:24:0x0173, B:75:0x0186, B:76:0x0189, B:39:0x0028, B:42:0x0034, B:44:0x0044, B:46:0x005d, B:48:0x0070, B:51:0x0082, B:57:0x0090, B:58:0x0093, B:60:0x0098, B:26:0x00c7, B:28:0x00d1, B:30:0x00d9, B:31:0x00f3, B:35:0x00f0, B:9:0x012f, B:37:0x013c), top: B:3:0x0009, inners: #5 }] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0173 A[Catch: all -> 0x018a, TRY_LEAVE, TryCatch #4 {, blocks: (B:4:0x0009, B:52:0x0085, B:64:0x00b4, B:32:0x012a, B:10:0x0136, B:13:0x0149, B:15:0x0155, B:17:0x0158, B:19:0x015d, B:24:0x0173, B:75:0x0186, B:76:0x0189, B:39:0x0028, B:42:0x0034, B:44:0x0044, B:46:0x005d, B:48:0x0070, B:51:0x0082, B:57:0x0090, B:58:0x0093, B:60:0x0098, B:26:0x00c7, B:28:0x00d1, B:30:0x00d9, B:31:0x00f3, B:35:0x00f0, B:9:0x012f, B:37:0x013c), top: B:3:0x0009, inners: #5 }] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00c7 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0098 A[Catch: all -> 0x00bc, Exception -> 0x00bf, TRY_LEAVE, TryCatch #0 {Exception -> 0x00bf, blocks: (B:39:0x0028, B:42:0x0034, B:44:0x0044, B:57:0x0090, B:58:0x0093, B:60:0x0098), top: B:38:0x0028 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public synchronized void activateKnoxLicense(ContextInfo contextInfo, String str, String str2, ILicenseResultCallback iLicenseResultCallback) {
        String str3;
        String[] strArr;
        int length;
        int i;
        String str4;
        Log.d("EnterpriseLicenseService", "activateKnoxLicense()");
        int callingOrUserUid = Utils.getCallingOrUserUid(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        int callingPid = Binder.getCallingPid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        String packageName = getPackageName(callingOrUserUid, callingPid);
        if (str2 != null) {
            try {
                try {
                } catch (Exception e) {
                    e = e;
                    str3 = str2;
                    Log.e("EnterpriseLicenseService", "activateKnoxLicense() failed.");
                    e.printStackTrace();
                    str4 = str3;
                    if (str4 != null) {
                    }
                }
                if (!str2.trim().isEmpty()) {
                    String[] packagesForUid = this.mContext.getPackageManager().getPackagesForUid(callingOrUserUid);
                    if (!isActionAllowedForAnotherPackage(str2, packagesForUid, callingOrCurrentUserId)) {
                        Log.e("EnterpriseLicenseService", packageName + " is not authorized to activate license for " + str2);
                        try {
                        } catch (Exception e2) {
                            e = e2;
                            strArr = packagesForUid;
                        }
                        if (IPackageManager.Stub.asInterface(ServiceManager.getService("package")).getPackageInfo(str2, 0L, callingOrCurrentUserId) == null) {
                            strArr = packagesForUid;
                            length = strArr.length;
                            i = 0;
                            while (i < length) {
                                int i2 = i;
                                sendKlmResult("fail", 204, 800, strArr[i], packageName, new LicenseResultRecord(str, iLicenseResultCallback));
                                i = i2 + 1;
                            }
                            return;
                        }
                        strArr = packagesForUid;
                        try {
                            sendKlmResult("fail", 204, 800, str2, packageName, new LicenseResultRecord(str, iLicenseResultCallback));
                            return;
                        } catch (Exception e3) {
                            e = e3;
                            e.printStackTrace();
                            length = strArr.length;
                            i = 0;
                            while (i < length) {
                            }
                            return;
                        }
                    }
                    str3 = str2;
                    if (str3 != null) {
                        try {
                            if (!str3.trim().isEmpty()) {
                                if (this.mKlmPkgRecords.containsKey(str3)) {
                                    Log.w("EnterpriseLicenseService", "Another operation is already in progress for " + str3);
                                } else {
                                    registerKlmLicenseResultRecord(str3, str, iLicenseResultCallback);
                                }
                                Log.d("EnterpriseLicenseService", "activateKnoxLicense to " + str3);
                                final Bundle bundle = new Bundle();
                                bundle.putString("com.samsung.android.knox.intent.extra.KNOX_LICENSE_DATA_PACKAGENAME", str3);
                                bundle.putString("com.samsung.android.knox.intent.extra.KNOX_LICENSE_DATA_REQUEST_PACKAGENAME", packageName);
                                bundle.putString("com.samsung.android.knox.intent.extra.KNOX_LICENSE_KEY", str);
                                new Thread(new Runnable() { // from class: com.android.server.enterprise.license.EnterpriseLicenseService$$ExternalSyntheticLambda3
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        EnterpriseLicenseService.this.lambda$activateKnoxLicense$1(bundle);
                                    }
                                }).start();
                                return;
                            }
                        } catch (Exception e4) {
                            e = e4;
                            Log.e("EnterpriseLicenseService", "activateKnoxLicense() failed.");
                            e.printStackTrace();
                            str4 = str3;
                            if (str4 != null) {
                            }
                        }
                    }
                    Log.e("EnterpriseLicenseService", "Invalid targetPackageName.");
                    str4 = str3;
                    if (str4 != null) {
                        String[] packagesForUid2 = this.mContext.getPackageManager().getPackagesForUid(callingOrUserUid);
                        if (packagesForUid2 != null && packagesForUid2.length > 0) {
                            for (String str5 : packagesForUid2) {
                                sendKlmResult("fail", 301, 800, str5, packageName, new LicenseResultRecord(str, iLicenseResultCallback));
                            }
                        }
                    } else {
                        sendKlmResult("fail", 301, 800, str4, packageName, new LicenseResultRecord(str, iLicenseResultCallback));
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        str3 = packageName;
        if (str3 != null) {
        }
        Log.e("EnterpriseLicenseService", "Invalid targetPackageName.");
        str4 = str3;
        if (str4 != null) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$activateKnoxLicense$1(Bundle bundle) {
        callLicenseAgent("KLMRegistrationInternal", null, bundle);
    }

    public final void registerKlmLicenseResultRecord(String str, String str2, ILicenseResultCallback iLicenseResultCallback) {
        registerLicenseResultRecord(str, str2, iLicenseResultCallback, this.mKlmPkgRecords);
    }

    public final void registerElmLicenseResultRecord(String str, String str2, ILicenseResultCallback iLicenseResultCallback) {
        registerLicenseResultRecord(str, str2, iLicenseResultCallback, this.mElmPkgRecords);
    }

    public final void registerLicenseResultRecord(String str, String str2, ILicenseResultCallback iLicenseResultCallback, Map map) {
        Log.d("EnterpriseLicenseService", "registerLicenseResultRecord() for " + str);
        LicenseResultRecord licenseResultRecord = new LicenseResultRecord(str, str2, iLicenseResultCallback, map);
        if (iLicenseResultCallback != null) {
            try {
                iLicenseResultCallback.asBinder().linkToDeath(licenseResultRecord, 0);
                Log.d("EnterpriseLicenseService", "DeathRecipient successfully linked to " + str);
            } catch (RemoteException e) {
                Log.e("EnterpriseLicenseService", e.getMessage());
            }
        }
        map.put(str, licenseResultRecord);
    }

    public final void unregisterLicenseResultRecord(String str, Map map) {
        Log.d("EnterpriseLicenseService", "unregisterLicenseResultRecord() for " + str);
        if (map.containsKey(str)) {
            LicenseResultRecord licenseResultRecord = (LicenseResultRecord) map.get(str);
            if (licenseResultRecord != null && licenseResultRecord.callback != null) {
                licenseResultRecord.callback.asBinder().unlinkToDeath(licenseResultRecord, 0);
                Log.d("EnterpriseLicenseService", "DeathRecipient unlinked from " + str);
            }
            map.remove(str);
            return;
        }
        Log.e("EnterpriseLicenseService", "license record not found for " + str);
    }

    public synchronized void activateKnoxLicenseForUMC(ContextInfo contextInfo, String str, String str2) {
        activateKnoxLicense(contextInfo, str, str2, null);
    }

    public synchronized void validateLicenses() {
        validateLicenses(null);
    }

    public synchronized void validateLicenses(String str) {
        enforcePermission();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("validateLicenses to ");
            sb.append(str != null ? str : "all packages");
            Log.d("EnterpriseLicenseService", sb.toString());
            final Bundle bundle = new Bundle();
            bundle.putString("com.samsung.android.knox.intent.extra.KNOX_LICENSE_DATA_PACKAGENAME", str);
            new Thread(new Runnable() { // from class: com.android.server.enterprise.license.EnterpriseLicenseService$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    EnterpriseLicenseService.this.lambda$validateLicenses$2(bundle);
                }
            }).start();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$validateLicenses$2(Bundle bundle) {
        callLicenseAgent("licenseValidationInternal", null, bundle);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x013a A[Catch: all -> 0x017b, TRY_ENTER, TryCatch #5 {, blocks: (B:4:0x0009, B:54:0x0087, B:65:0x00b6, B:32:0x011b, B:10:0x0127, B:13:0x013a, B:15:0x0146, B:17:0x0149, B:19:0x014e, B:24:0x0164, B:76:0x0177, B:77:0x017a, B:39:0x0028, B:42:0x0034, B:44:0x0040, B:46:0x0046, B:48:0x005f, B:50:0x0072, B:53:0x0084, B:59:0x0092, B:60:0x0095, B:62:0x009a, B:26:0x00ce, B:28:0x00d8, B:30:0x00e0, B:31:0x00fa, B:35:0x00f7, B:9:0x0120, B:37:0x012d, B:73:0x00be, B:74:0x00c3), top: B:3:0x0009, inners: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0164 A[Catch: all -> 0x017b, TRY_LEAVE, TryCatch #5 {, blocks: (B:4:0x0009, B:54:0x0087, B:65:0x00b6, B:32:0x011b, B:10:0x0127, B:13:0x013a, B:15:0x0146, B:17:0x0149, B:19:0x014e, B:24:0x0164, B:76:0x0177, B:77:0x017a, B:39:0x0028, B:42:0x0034, B:44:0x0040, B:46:0x0046, B:48:0x005f, B:50:0x0072, B:53:0x0084, B:59:0x0092, B:60:0x0095, B:62:0x009a, B:26:0x00ce, B:28:0x00d8, B:30:0x00e0, B:31:0x00fa, B:35:0x00f7, B:9:0x0120, B:37:0x012d, B:73:0x00be, B:74:0x00c3), top: B:3:0x0009, inners: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00ce A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x009a A[Catch: all -> 0x00c4, Exception -> 0x00c7, TRY_LEAVE, TryCatch #3 {all -> 0x00c4, blocks: (B:39:0x0028, B:42:0x0034, B:44:0x0040, B:46:0x0046, B:48:0x005f, B:50:0x0072, B:53:0x0084, B:59:0x0092, B:60:0x0095, B:62:0x009a, B:26:0x00ce, B:28:0x00d8, B:30:0x00e0, B:31:0x00fa, B:35:0x00f7, B:9:0x0120, B:37:0x012d, B:73:0x00be, B:74:0x00c3), top: B:38:0x0028, outer: #5 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public synchronized void deActivateKnoxLicense(ContextInfo contextInfo, String str, String str2, ILicenseResultCallback iLicenseResultCallback) {
        String str3;
        String[] strArr;
        int length;
        int i;
        String str4;
        Log.d("EnterpriseLicenseService", "deActivateKnoxLicense()");
        int callingOrUserUid = Utils.getCallingOrUserUid(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        int callingPid = Binder.getCallingPid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        String packageName = getPackageName(callingOrUserUid, callingPid);
        if (str2 != null) {
            try {
                try {
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Exception e) {
                e = e;
                str3 = str2;
                Log.e("EnterpriseLicenseService", "deActivateKnoxLicense() failed.");
                e.printStackTrace();
                str4 = str3;
                if (str4 != null) {
                }
            }
            if (!str2.trim().isEmpty()) {
                String[] packagesForUid = this.mContext.getPackageManager().getPackagesForUid(callingOrUserUid);
                if (packagesForUid == null) {
                    throw new IllegalStateException();
                }
                if (!isActionAllowedForAnotherPackage(str2, packagesForUid, callingOrCurrentUserId)) {
                    Log.e("EnterpriseLicenseService", packageName + " is not authorized to deActivate license for " + str2);
                    try {
                    } catch (Exception e2) {
                        e = e2;
                        strArr = packagesForUid;
                    }
                    if (IPackageManager.Stub.asInterface(ServiceManager.getService("package")).getPackageInfo(str2, 0L, callingOrCurrentUserId) == null) {
                        strArr = packagesForUid;
                        length = strArr.length;
                        i = 0;
                        while (i < length) {
                            int i2 = i;
                            sendKlmResult("fail", 204, 802, strArr[i], packageName, new LicenseResultRecord(str, iLicenseResultCallback));
                            i = i2 + 1;
                        }
                        return;
                    }
                    strArr = packagesForUid;
                    try {
                        sendKlmResult("fail", 204, 802, str2, packageName, new LicenseResultRecord(str, iLicenseResultCallback));
                        return;
                    } catch (Exception e3) {
                        e = e3;
                        e.printStackTrace();
                        length = strArr.length;
                        i = 0;
                        while (i < length) {
                        }
                        return;
                    }
                }
                str3 = str2;
                if (str3 != null) {
                    try {
                        if (!str3.trim().isEmpty()) {
                            if (this.mKlmPkgRecords.containsKey(str3)) {
                                Log.w("EnterpriseLicenseService", "Another operation is already in progress for " + str3);
                            } else {
                                registerKlmLicenseResultRecord(str3, str, iLicenseResultCallback);
                            }
                            final Bundle bundle = new Bundle();
                            bundle.putString("com.samsung.android.knox.intent.extra.KNOX_LICENSE_DATA_PACKAGENAME", str3);
                            bundle.putString("com.samsung.android.knox.intent.extra.KNOX_LICENSE_DATA_REQUEST_PACKAGENAME", packageName);
                            bundle.putString("com.samsung.android.knox.intent.extra.KNOX_LICENSE_KEY", str);
                            new Thread(new Runnable() { // from class: com.android.server.enterprise.license.EnterpriseLicenseService$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    EnterpriseLicenseService.this.lambda$deActivateKnoxLicense$3(bundle);
                                }
                            }).start();
                            return;
                        }
                    } catch (Exception e4) {
                        e = e4;
                        Log.e("EnterpriseLicenseService", "deActivateKnoxLicense() failed.");
                        e.printStackTrace();
                        str4 = str3;
                        if (str4 != null) {
                        }
                    }
                }
                Log.e("EnterpriseLicenseService", "Invalid targetPackageName.");
                str4 = str3;
                if (str4 != null) {
                    String[] packagesForUid2 = this.mContext.getPackageManager().getPackagesForUid(callingOrUserUid);
                    if (packagesForUid2 != null && packagesForUid2.length > 0) {
                        for (String str5 : packagesForUid2) {
                            sendKlmResult("fail", 301, 802, str5, packageName, new LicenseResultRecord(str, iLicenseResultCallback));
                        }
                    }
                } else {
                    sendKlmResult("fail", 301, 802, str4, packageName, new LicenseResultRecord(str, iLicenseResultCallback));
                }
            }
        }
        str3 = packageName;
        if (str3 != null) {
        }
        Log.e("EnterpriseLicenseService", "Invalid targetPackageName.");
        str4 = str3;
        if (str4 != null) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deActivateKnoxLicense$3(Bundle bundle) {
        callLicenseAgent("KLMDeactivationInternal", null, bundle);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v5, types: [java.util.List] */
    public List getPermissions(String str) {
        String str2 = "EnterpriseLicenseService";
        int callingUid = Binder.getCallingUid();
        if (UserHandle.getAppId(callingUid) != 1000) {
            throw new SecurityException("Caller " + callingUid + " is not SYSTEM_SERVICE OR SYSTEM APP");
        }
        List list = null;
        if (str != null && !str.trim().isEmpty()) {
            try {
                byte[] blob = mEdmStorageProvider.getBlob("LICENSE", "pkgName", str, "rightsObject");
                str2 = str2;
                if (blob != null) {
                    RightsObject rightsObject = (RightsObject) Utils.deserializeObject(blob);
                    Log.d("EnterpriseLicenseService", "getPermissions() - deserializeObject");
                    if (rightsObject != null) {
                        ?? permissions = rightsObject.getPermissions();
                        list = permissions;
                        str2 = permissions;
                    } else {
                        Log.w("EnterpriseLicenseService", "ro is null!!");
                        str2 = str2;
                    }
                }
            } catch (Exception unused) {
                Log.w(str2, "getPermissions() failed");
            }
        }
        return list;
    }

    public List getELMPermissions(String str) {
        try {
            return getPermissions(str);
        } catch (SecurityException e) {
            Log.w("EnterpriseLicenseService", "getELMPermissions() failed: " + e.getMessage());
            return null;
        }
    }

    public void log(ContextInfo contextInfo, String str, boolean z, boolean z2) {
        LicenseLog.log(contextInfo, str, z, z2);
    }

    public boolean resetLicense(String str) {
        enforcePermission();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z = false;
        try {
            if (str != null) {
                try {
                } catch (Exception e) {
                    Log.w("EnterpriseLicenseService", "resetLicense() failed");
                    e.printStackTrace();
                }
                if (!str.trim().isEmpty()) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("instanceId", str);
                    ContentValues value = mEdmStorageProvider.getValue("LICENSE", "pkgName", contentValues);
                    if (value == null) {
                        Log.w("EnterpriseLicenseService", "resetLicense(): result is null");
                        return false;
                    }
                    String asString = value.getAsString("pkgName");
                    if (asString == null) {
                        Log.w("EnterpriseLicenseService", "resetLicense(): pkgName is null, Record does not exist");
                        return false;
                    }
                    z = resetELMInfo(asString);
                    if (z) {
                        mPMS.setLicensePermissionsForMDM(asString);
                    }
                    return z;
                }
            }
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean resetLicenseByAdmin(String str) {
        enforcePermission();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z = false;
        if (str != null) {
            try {
                try {
                } catch (Exception unused) {
                    Log.w("EnterpriseLicenseService", "resetLicenseByAdmin() failed");
                }
                if (!str.trim().isEmpty()) {
                    z = resetELMInfo(str);
                    if (z && isPackageInstalled(str)) {
                        mPMS.setLicensePermissionsForMDM(str);
                    }
                    return z;
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return false;
    }

    public boolean deleteLicense(String str) {
        enforcePermission();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z = false;
        try {
            if (str != null) {
                try {
                } catch (Exception e) {
                    Log.w("EnterpriseLicenseService", "deleteLicense() failed");
                    e.printStackTrace();
                }
                if (!str.trim().isEmpty()) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("instanceId", str);
                    ContentValues value = mEdmStorageProvider.getValue("LICENSE", "pkgName", contentValues);
                    if (value == null) {
                        Log.w("EnterpriseLicenseService", "deleteLicense(): result is null");
                        return false;
                    }
                    String asString = value.getAsString("pkgName");
                    if (asString == null) {
                        Log.w("EnterpriseLicenseService", "deleteLicense(): pkgName is null, Record does not exist");
                        return false;
                    }
                    z = deleteELMInfo(asString);
                    if (z) {
                        mPMS.setLicensePermissionsForMDM(asString);
                    }
                    return z;
                }
            }
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean deleteLicenseByAdmin(String str) {
        enforcePermission();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z = false;
        if (str != null) {
            try {
                try {
                } catch (Exception unused) {
                    Log.e("EnterpriseLicenseService", "deleteLicenseByAdmin() failed");
                }
                if (!str.trim().isEmpty()) {
                    String instanceId = getInstanceId(str);
                    z = deleteELMInfo(str);
                    if (z) {
                        if (isPackageInstalled(str)) {
                            mPMS.setLicensePermissionsForMDM(str);
                        }
                        if (isElmKey(instanceId)) {
                            Log.d("EnterpriseLicenseService", "deleteLicenseByAdmin(): notify ELM observers");
                            notifyElmObservers(str, new LicenseResult(str, LicenseResult.Status.SUCCESS, 0, LicenseResult.Type.ELM_DEACTIVATION, (ArrayList) null, (String) null));
                        }
                    }
                    return z;
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return false;
    }

    public final boolean isElmKey(String str) {
        try {
            if (Integer.parseInt(str) <= -1) {
                return false;
            }
            Log.d("EnterpriseLicenseService", "isElmKey(True)");
            return true;
        } catch (NumberFormatException unused) {
            Log.e("EnterpriseLicenseService", "isElmKey(False)");
            return false;
        }
    }

    public final boolean isPackageInstalled(String str) {
        Log.d("EnterpriseLicenseService", "isPackageInstalled()");
        for (UserInfo userInfo : ((UserManager) this.mContext.getSystemService("user")).getUsers()) {
            try {
                this.mContext.getPackageManager().getPackageInfoAsUser(str, 0, userInfo.id);
                Log.d("EnterpriseLicenseService", "isPackageInstalled() - " + str + " found in user " + userInfo.id);
                return true;
            } catch (PackageManager.NameNotFoundException unused) {
                Log.d("EnterpriseLicenseService", "isPackageInstalled() - " + str + " not found in user " + userInfo.id);
            }
        }
        return false;
    }

    public void updateAdminPermissions() {
        if (Binder.getCallingPid() != MY_PID) {
            throw new SecurityException("Caller is not SYSTEM_PROCESS");
        }
        try {
            List valuesList = mEdmStorageProvider.getValuesList("LICENSE", new String[]{"pkgName"}, null);
            if (valuesList == null || valuesList.isEmpty()) {
                return;
            }
            Iterator it = valuesList.iterator();
            while (it.hasNext()) {
                String asString = ((ContentValues) it.next()).getAsString("pkgName");
                if (asString != null) {
                    Log.w("EnterpriseLicenseService", "updateAdminPermissions() :" + asString);
                    try {
                        mPMS.setLicensePermissionsForMDM(asString);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e2) {
            Log.w("EnterpriseLicenseService", "updateAdminPermissions() failed");
            e2.printStackTrace();
        }
    }

    public final boolean resetELMInfo(String str) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("rightsObject", (byte[]) null);
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("pkgName", str);
        return mEdmStorageProvider.putValues("LICENSE", contentValues, contentValues2);
    }

    public final boolean deleteELMInfo(String str) {
        return mEdmStorageProvider.deleteDataByFields("LICENSE", new String[]{"pkgName"}, new String[]{str});
    }

    public String getInstanceId(String str) {
        enforcePermission();
        String str2 = null;
        if (str != null && !str.trim().isEmpty()) {
            try {
                List<ContentValues> valuesList = mEdmStorageProvider.getValuesList("LICENSE", new String[]{"pkgName", "instanceId"}, null);
                if (valuesList != null && !valuesList.isEmpty()) {
                    for (ContentValues contentValues : valuesList) {
                        String asString = contentValues.getAsString("pkgName");
                        if (asString != null && asString.equals(str)) {
                            str2 = contentValues.getAsString("instanceId");
                        }
                    }
                }
            } catch (Exception unused) {
                Log.w("EnterpriseLicenseService", "getInstanceId() failed");
            }
        }
        return str2;
    }

    public boolean isServiceAvailable(String str, String str2) {
        Log.d("EnterpriseLicenseService", "isServiceAvailable");
        if (str2 == null || str2.isEmpty()) {
            Log.d("EnterpriseLicenseService", "serviceName is null");
            return false;
        }
        if (str != null) {
            try {
            } catch (Exception unused) {
                Log.w("EnterpriseLicenseService", "check Service did not find permission");
            }
            if (!str.isEmpty()) {
                RightsObject rightsObject = (RightsObject) Utils.deserializeObject(mEdmStorageProvider.getBlob("LICENSE", "pkgName", str, "rightsObject"));
                return rightsObject != null && rightsObject.getPermissions().contains(str2);
            }
        }
        List valuesList = mEdmStorageProvider.getValuesList("LICENSE", new String[]{"rightsObject"}, null);
        if (valuesList != null && !valuesList.isEmpty()) {
            Iterator it = valuesList.iterator();
            while (it.hasNext()) {
                RightsObject rightsObject2 = (RightsObject) Utils.deserializeObject(((ContentValues) it.next()).getAsByteArray("rightsObject"));
                if (rightsObject2 != null && rightsObject2.getPermissions().contains(str2)) {
                    return true;
                }
            }
        }
    }

    public boolean isEulaBypassAllowed(String str) {
        Log.d("EnterpriseLicenseService", "isEulaBypassAllowed");
        enforcePermission();
        try {
            List valuesList = mEdmStorageProvider.getValuesList("KNOX_CUSTOM", new String[]{"mamPackageName"});
            if (valuesList != null && !valuesList.isEmpty()) {
                Iterator it = valuesList.iterator();
                while (it.hasNext()) {
                    String asString = ((ContentValues) it.next()).getAsString("mamPackageName");
                    if (asString != null) {
                        for (String str2 : asString.split(KnoxVpnFirewallHelper.DELIMITER)) {
                            if (str2.equals(str)) {
                                return true;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.w("EnterpriseLicenseService", "isEulaBypassAllowed() failed");
            e.printStackTrace();
        }
        return false;
    }

    public ActivationInfo getLicenseActivationInfo(ContextInfo contextInfo, String str) {
        String nameForUid = this.mContext.getPackageManager().getNameForUid(Utils.getCallingOrUserUid(contextInfo));
        if (str != null) {
            enforcePermission();
        } else {
            str = nameForUid;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Bundle callLicenseAgent = callLicenseAgent("getActivations", str, null);
            return callLicenseAgent != null ? (ActivationInfo) callLicenseAgent.getParcelable(KnoxCustomManagerService.SPCM_KEY_RESULT) : null;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public List getAllLicenseActivationsInfos() {
        ArrayList arrayList = new ArrayList();
        enforcePermission();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Bundle callLicenseAgent = callLicenseAgent("getAllActivations", null, null);
            if (callLicenseAgent != null) {
                arrayList = callLicenseAgent.getParcelableArrayList(KnoxCustomManagerService.SPCM_KEY_RESULT);
            }
            return arrayList;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void addElmKlmObserver(IActivationKlmElmObserver iActivationKlmElmObserver) {
        enforcePermission();
        this.mKlmElmChangeList.add(iActivationKlmElmObserver);
    }

    public void removeElmKlmObserver(IActivationKlmElmObserver iActivationKlmElmObserver) {
        enforcePermission();
        this.mKlmElmChangeList.remove(iActivationKlmElmObserver);
    }

    public void notifyKlmObservers(String str, LicenseResult licenseResult) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Iterator it = this.mKlmElmChangeList.iterator();
            while (it.hasNext()) {
                ((IActivationKlmElmObserver) it.next()).onUpdateKlm(str, licenseResult);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void notifyElmObservers(String str, LicenseResult licenseResult) {
        Iterator it = this.mKlmElmChangeList.iterator();
        while (it.hasNext()) {
            ((IActivationKlmElmObserver) it.next()).onUpdateElm(str, licenseResult);
        }
    }

    public final boolean isLicenseLocked(int i) {
        int attributes = getPersonaManagerAdapter().getAttributes(i);
        if (attributes == -1) {
            return false;
        }
        boolean z = (attributes & 16) > 0;
        Log.d("EnterpriseLicenseService", "isLicenseLocked : " + z);
        return z;
    }

    public final void sendElmResult(String str, int i, int i2, String str2, String str3, LicenseResultRecord licenseResultRecord) {
        sendElmResult(str, i, i2, str2, str3, null, -1, new ArrayList(), new ArrayList(), licenseResultRecord);
    }

    public final void sendElmResult(String str, int i, int i2, String str2, String str3) {
        sendElmResult(str, i, i2, str2, str3, null, -1, new ArrayList(), new ArrayList(), null);
    }

    public final void sendElmResult(String str, int i, int i2, String str2, String str3, String str4, int i3, ArrayList arrayList, ArrayList arrayList2) {
        sendElmResult(str, i, i2, str2, str3, str4, i3, arrayList, arrayList2, null);
    }

    public final void sendElmResult(String str, int i, int i2, String str2, String str3, String str4, int i3, ArrayList arrayList, ArrayList arrayList2, LicenseResultRecord licenseResultRecord) {
        LicenseResultRecord licenseResultRecord2;
        boolean z;
        if (licenseResultRecord != null || i == 602 || i2 == 801) {
            licenseResultRecord2 = licenseResultRecord;
        } else {
            LicenseResultRecord licenseResultRecord3 = (LicenseResultRecord) this.mElmPkgRecords.get(str2);
            if (licenseResultRecord3 != null) {
                unregisterLicenseResultRecord(str2, this.mElmPkgRecords);
            } else {
                Log.e("EnterpriseLicenseService", "ELM Record not found. Caller died or race condition for " + str2);
            }
            licenseResultRecord2 = licenseResultRecord3;
        }
        if (licenseResultRecord2 != null && licenseResultRecord2.callback != null && i2 != 801) {
            z = true;
            try {
                licenseResultRecord2.callback.onLicenseResult(new LicenseResult(str2, str, i, LicenseResult.Type.fromElmStatus(i2), arrayList, licenseResultRecord2.licenseKey));
                Log.i("EnterpriseLicenseService", "ELM result sent by callback to " + str2);
            } catch (DeadObjectException e) {
                Log.e("EnterpriseLicenseService", "DeadObjectException in sendElmResult", e);
            } catch (RemoteException e2) {
                Log.e("EnterpriseLicenseService", "RemoteException in sendElmResult", e2);
            }
        }
        z = false;
        if (licenseResultRecord2 == null || licenseResultRecord2.callback == null || z) {
            Intent intent = new Intent("com.samsung.android.knox.intent.action.LICENSE_STATUS");
            intent.putExtra("com.samsung.android.knox.intent.extra.LICENSE_STATUS", str);
            intent.putExtra("com.samsung.android.knox.intent.extra.LICENSE_ERROR_CODE", i);
            intent.putExtra("com.samsung.android.knox.intent.extra.LICENSE_RESULT_TYPE", i2);
            intent.putExtra("com.samsung.android.knox.intent.extra.LICENSE_DATA_PACKAGENAME", str2);
            intent.putExtra("com.samsung.android.knox.intent.extra.LICENSE_PERM_GROUP", str4);
            intent.putExtra("com.samsung.android.knox.intent.extra.LICENSE_ATTESTATION_STATUS", i3);
            intent.putStringArrayListExtra("com.samsung.android.knox.intent.extra.LICENSE_GRANTED_PERMISSIONS", arrayList);
            if (arrayList2 != null) {
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("Permissions", arrayList2);
                intent.putExtra("com.samsung.android.knox.intent.extra.LICENSE_DATA_LICENSE_PERMISSIONS", bundle);
            }
            if (str3 != null && !str3.equals(str2)) {
                intent.setPackage(str3);
                long clearCallingIdentity = Binder.clearCallingIdentity();
                this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                Log.i("EnterpriseLicenseService", "ELM result sent by Intent to " + str3);
            }
            intent.setPackage(str2);
            long clearCallingIdentity2 = Binder.clearCallingIdentity();
            this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
            Binder.restoreCallingIdentity(clearCallingIdentity2);
            Log.i("EnterpriseLicenseService", "ELM result sent by Intent to " + str2);
        }
    }

    public final void sendKlmResult(String str, int i, int i2, String str2, String str3, LicenseResultRecord licenseResultRecord) {
        sendKlmResult(str, i, i2, str2, str3, -1, new ArrayList(), new ArrayList(), licenseResultRecord);
    }

    public final void sendKlmResult(String str, int i, int i2, String str2, String str3) {
        sendKlmResult(str, i, i2, str2, str3, -1, new ArrayList(), new ArrayList(), null);
    }

    public final void sendKlmResult(String str, int i, int i2, String str2, String str3, int i3, ArrayList arrayList, ArrayList arrayList2) {
        sendKlmResult(str, i, i2, str2, str3, i3, arrayList, arrayList2, null);
    }

    public final void sendKlmResult(String str, int i, int i2, String str2, String str3, int i3, ArrayList arrayList, ArrayList arrayList2, LicenseResultRecord licenseResultRecord) {
        LicenseResultRecord licenseResultRecord2;
        boolean z;
        if (licenseResultRecord != null || i2 == 801 || i == 602) {
            licenseResultRecord2 = licenseResultRecord;
        } else {
            LicenseResultRecord licenseResultRecord3 = (LicenseResultRecord) this.mKlmPkgRecords.get(str2);
            if (licenseResultRecord3 != null) {
                unregisterLicenseResultRecord(str2, this.mKlmPkgRecords);
            } else {
                Log.w("EnterpriseLicenseService", "KLM Record not found. Caller died or race condition for " + str2);
            }
            licenseResultRecord2 = licenseResultRecord3;
        }
        if (licenseResultRecord2 != null && licenseResultRecord2.callback != null && i2 != 801) {
            z = true;
            try {
                licenseResultRecord2.callback.onLicenseResult(new LicenseResult(str2, str, i, LicenseResult.Type.fromKlmStatus(i2), arrayList, licenseResultRecord2.licenseKey));
                Log.i("EnterpriseLicenseService", "KLM result sent by callback to " + str2);
            } catch (DeadObjectException e) {
                Log.e("EnterpriseLicenseService", "DeadObjectException in sendKlmResult", e);
            } catch (RemoteException e2) {
                Log.e("EnterpriseLicenseService", "RemoteException in sendKlmResult", e2);
            }
        }
        z = false;
        if (licenseResultRecord2 == null || licenseResultRecord2.callback == null || z || i2 == 801) {
            Intent intent = new Intent("com.samsung.android.knox.intent.action.KNOX_LICENSE_STATUS");
            intent.putExtra("com.samsung.android.knox.intent.extra.KNOX_LICENSE_STATUS", str);
            intent.putExtra("com.samsung.android.knox.intent.extra.KNOX_LICENSE_ERROR_CODE", i);
            intent.putExtra("com.samsung.android.knox.intent.extra.KNOX_LICENSE_RESULT_TYPE", i2);
            intent.putExtra("com.samsung.android.knox.intent.extra.KNOX_LICENSE_DATA_PACKAGENAME", str2);
            intent.putStringArrayListExtra("com.samsung.android.knox.intent.extra.LICENSE_GRANTED_PERMISSIONS", arrayList);
            if (i2 == 800) {
                intent.putExtra("com.samsung.android.knox.intent.extra.LICENSE_ATTESTATION_STATUS", i3);
            }
            if (arrayList2 != null) {
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("Permissions", arrayList2);
                intent.putExtra("com.samsung.android.knox.intent.extra.LICENSE_DATA_LICENSE_PERMISSIONS", bundle);
            }
            if (str3 != null && !str3.equals(str2)) {
                intent.setPackage(str3);
                long clearCallingIdentity = Binder.clearCallingIdentity();
                this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                Log.i("EnterpriseLicenseService", "KLM result sent by intent to " + str3);
            }
            intent.setPackage(str2);
            long clearCallingIdentity2 = Binder.clearCallingIdentity();
            this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
            Binder.restoreCallingIdentity(clearCallingIdentity2);
            Log.i("EnterpriseLicenseService", "KLM result sent by intent to " + str2);
        }
    }

    public final String getMaskedText(String str) {
        if (TextUtils.isEmpty(str) || str.length() <= 24) {
            return str;
        }
        int length = str.length() - 24;
        String substring = str.substring(0, 12);
        StringBuilder sb = new StringBuilder();
        sb.append(substring);
        sb.append(new String(new char[length]).replace("\u0000", "*"));
        return sb.toString() + str.substring(str.length() - 12);
    }

    public final String getMaskedKlm(String str) {
        if (str == null) {
            return "";
        }
        String str2 = str.split("#")[0];
        if (TextUtils.isEmpty(str2)) {
            return "";
        }
        String[] split = str2.split(PackageManagerShellCommandDataLoader.STDIN_PATH);
        return split.length < 6 ? "" : getMaskedText(String.join(PackageManagerShellCommandDataLoader.STDIN_PATH, split[0], split[1], split[2], split[3], split[4], split[5]));
    }

    public final Bundle callLicenseAgent(String str, String str2, Bundle bundle) {
        Log.d("EnterpriseLicenseService", "callLicenseAgent() - " + str);
        try {
            return this.mContext.getContentResolver().call(LicenseAgentDbContract.DB_URI, str, str2, bundle);
        } catch (Exception e) {
            str.hashCode();
            switch (str) {
                case "ELMRegistrationInternal":
                    sendElmResult("fail", 301, 800, bundle.getString("com.samsung.android.knox.intent.extra.LICENSE_DATA_PACKAGENAME"), bundle.getString("com.samsung.android.knox.intent.extra.LICENSE_DATA_REQUEST_PACKAGENAME"));
                    break;
                case "KLMDeactivationInternal":
                    sendKlmResult("fail", 301, 802, bundle.getString("com.samsung.android.knox.intent.extra.KNOX_LICENSE_DATA_PACKAGENAME"), bundle.getString("com.samsung.android.knox.intent.extra.KNOX_LICENSE_DATA_REQUEST_PACKAGENAME"));
                    break;
                case "KLMRegistrationInternal":
                    sendKlmResult("fail", 301, 800, bundle.getString("com.samsung.android.knox.intent.extra.KNOX_LICENSE_DATA_PACKAGENAME"), bundle.getString("com.samsung.android.knox.intent.extra.KNOX_LICENSE_DATA_REQUEST_PACKAGENAME"));
                    break;
            }
            Log.e("EnterpriseLicenseService", "Exception calling KLMSAgent: " + Log.getStackTraceString(e));
            return null;
        }
    }

    public class LicenseResultRecord implements IBinder.DeathRecipient {
        public ILicenseResultCallback callback;
        public final String licenseKey;
        public String pkgName;
        public Map recordMap;

        public LicenseResultRecord(String str, ILicenseResultCallback iLicenseResultCallback) {
            this.licenseKey = str;
            this.callback = iLicenseResultCallback;
        }

        public LicenseResultRecord(String str, String str2, ILicenseResultCallback iLicenseResultCallback, Map map) {
            this.pkgName = str;
            this.licenseKey = str2;
            this.callback = iLicenseResultCallback;
            this.recordMap = map;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            Log.e("EnterpriseLicenseService", "binderDied() pkgName = " + this.pkgName);
            EnterpriseLicenseService.this.unregisterLicenseResultRecord(this.pkgName, this.recordMap);
        }
    }

    @Override // com.android.server.enterprise.license.IDeviceProfileObserver
    public void onProfileOwnerAdded(int i) {
        Log.d("EnterpriseLicenseService", "onProfileOwnerAdded " + i);
    }

    @Override // com.android.server.enterprise.license.IDeviceProfileObserver
    public void onProfileOwnerRemoved(int i) {
        Log.d("EnterpriseLicenseService", "onProfileOwnerRemoved " + i);
        revokeKnoxPermissionFromUninstalledPackages();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            new Thread(new Runnable() { // from class: com.android.server.enterprise.license.EnterpriseLicenseService$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    EnterpriseLicenseService.this.lambda$onProfileOwnerRemoved$4();
                }
            }).start();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onProfileOwnerRemoved$4() {
        callLicenseAgent("ProfileOwnerRemoved", null, null);
    }

    @Override // com.android.server.enterprise.license.IDeviceProfileObserver
    public void onDeviceOwnerAdded(String str) {
        Log.d("EnterpriseLicenseService", "onDeviceOwnerAdded " + str);
    }

    @Override // com.android.server.enterprise.license.IDeviceProfileObserver
    public void onDeviceOwnerRemoved(String str) {
        Log.d("EnterpriseLicenseService", "onDeviceOwnerRemoved " + str);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            new Thread(new Runnable() { // from class: com.android.server.enterprise.license.EnterpriseLicenseService$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    EnterpriseLicenseService.this.lambda$onDeviceOwnerRemoved$5();
                }
            }).start();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDeviceOwnerRemoved$5() {
        callLicenseAgent("DeviceOwnerRemoved", null, null);
    }

    public final String getPackageName(int i, int i2) {
        String nameForUid = this.mContext.getPackageManager().getNameForUid(i);
        return (nameForUid == null || !nameForUid.contains(XmlUtils.STRING_ARRAY_SEPARATOR)) ? nameForUid : getCallingPackageNameFromAppPid(i2, nameForUid);
    }

    public final String getCallingPackageNameFromAppPid(int i, String str) {
        ActivityManager activityManager = (ActivityManager) this.mContext.getSystemService("activity");
        String packageFromAppProcesses = activityManager != null ? activityManager.getPackageFromAppProcesses(i) : "";
        return TextUtils.isEmpty(packageFromAppProcesses) ? str : packageFromAppProcesses;
    }

    public final boolean isActionAllowedForAnotherPackage(String str, String[] strArr, int i) {
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (isRequestToCallerOrSharedUidApp(str, strArr)) {
            Log.d("EnterpriseLicenseService", "Request allowed from callerSharedPackages to targetPackageName");
            return true;
        }
        for (String str2 : strArr) {
            if (isCallerAllowedToPerformActionForAnotherPackage(str2, i)) {
                Log.d("EnterpriseLicenseService", "Request allowed by platform signature or license permission");
                return true;
            }
        }
        return false;
    }

    public final boolean isRequestToCallerOrSharedUidApp(String str, String[] strArr) {
        return Arrays.asList(strArr).contains(str);
    }

    public final boolean isCallerAllowedToPerformActionForAnotherPackage(String str, int i) {
        try {
            if (isSamsungSpecialPackage(str)) {
                return isPackagePlatformSigned(str, i);
            }
            if (isOfficialBuild()) {
                if (isRequestFromKSP(str, i)) {
                    return isKpuPlatformSigned(str, i);
                }
                return isPackagePlatformSigned(str, i) && isLicensePermissionGranted(str, i);
            }
            return isLicensePermissionGranted(str, i);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public final boolean isOfficialBuild() {
        return SystemProperties.getBoolean("ro.product_ship", true);
    }

    public final boolean isSamsungSpecialPackage(String str) {
        return this.samsungSpecialPackages.contains(str);
    }

    public final boolean isRequestFromKSP(String str, int i) {
        KpuHelper kpuHelper = KpuHelper.getInstance(this.mContext);
        return kpuHelper.isKpuPackage(str) || kpuHelper.isKpuPermissionGranted(str, i);
    }

    public final boolean isKpuPlatformSigned(String str, int i) {
        return KpuHelper.getInstance(this.mContext).isKpuPlatformSigned(str, i);
    }

    public final boolean isPackagePlatformSigned(String str, int i) {
        try {
            return AppGlobals.getPackageManager().checkSignatures("android", str, i) == 0;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public final boolean isPackageUninstalled(String str) {
        return !isPackageInstalled(str);
    }

    public final void revokeKnoxPermissionFromUninstalledPackages() {
        enforcePermission();
        Log.d("EnterpriseLicenseService", "revokeKnoxPermissionFromUninstalledPackages");
        for (String str : getPackageNameFromAllActivations()) {
            if (isPackageUninstalled(str)) {
                Log.d("EnterpriseLicenseService", "revoking permissions from uninstalled package: " + str);
                resetLicenseByAdmin(str);
            }
        }
    }

    public final List getPackageNameFromAllActivations() {
        enforcePermission();
        Log.d("EnterpriseLicenseService", "getPackageNameFromAllActivations");
        ArrayList arrayList = new ArrayList();
        List values = mEdmStorageProvider.getValues("LICENSE", new String[]{"pkgName"}, (ContentValues) null);
        if (values != null && !values.isEmpty()) {
            Iterator it = values.iterator();
            while (it.hasNext()) {
                String asString = ((ContentValues) it.next()).getAsString("pkgName");
                Log.d("EnterpriseLicenseService", "packageName found " + asString);
                if (!TextUtils.isEmpty(asString)) {
                    arrayList.add(asString);
                }
            }
        }
        return arrayList;
    }

    public final boolean isLicensePermissionGranted(String str, int i) {
        try {
            return AppGlobals.getPackageManager().checkPermission("com.samsung.android.knox.permission.KNOX_LICENSE_INTERNAL", str, i) == 0;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }
}
