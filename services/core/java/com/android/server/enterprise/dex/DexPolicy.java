package com.android.server.enterprise.dex;

import android.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.INetworkManagementService;
import android.os.ServiceManager;
import android.provider.Settings;
import android.util.Log;

import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.alarm.GmsAlarmManager$$ExternalSyntheticOutline0;
import com.android.server.am.OomAdjuster$$ExternalSyntheticOutline0;
import com.android.server.enterprise.EnterpriseService;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.application.ApplicationPolicy;
import com.android.server.enterprise.restriction.RestrictionPolicy;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.utils.EnterpriseDumpHelper;
import com.android.server.enterprise.utils.Utils;

import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.dex.IDexPolicy;
import com.samsung.android.knox.net.wifi.IWifiPolicy;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class DexPolicy extends IDexPolicy.Stub implements EnterpriseServiceCallback {
    public static boolean isBlockerRegistered;
    public final AnonymousClass1 blocker;
    public ApplicationPolicy mApplicationPolicy;
    public final Context mContext;
    public ContextInfo mContext_temp;
    public EnterpriseDeviceManager mEDM;
    public final EdmStorageProvider mEdmStorageProvider;
    public final EnterpriseDumpHelper mEnterpriseDumpHelper;
    public IWifiPolicy mIWifipolicy;
    public final Injector mInjector;
    public RestrictionPolicy restrictionPolicy;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DexStateChangeObserver extends ContentObserver {
        public DexStateChangeObserver() {
            super(new Handler());
            Settings.System.getUriFor("new_dex");
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            int intForUser =
                    Settings.System.getIntForUser(
                            DexPolicy.this.mContext.getContentResolver(), "new_dex", 0, -2);
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                    intForUser, "NEW_DEX state : ", "DexPolicyService");
            DexPolicy dexPolicy = DexPolicy.this;
            if (intForUser <= 0) {
                dexPolicy.getClass();
            } else if (dexPolicy.isDexDisabled()) {
                Log.d("DexPolicyService", "Currently oneUi dex blocker is not present");
            }
            DexPolicy dexPolicy2 = DexPolicy.this;
            if (intForUser > 0) {
                dexPolicy2.enterDexModeSetPackageState();
            } else {
                dexPolicy2.exitDexModeSetPackageState();
            }
            DexPolicy dexPolicy3 = DexPolicy.this;
            dexPolicy3.getClass();
            ContextInfo contextInfo =
                    new ContextInfo(
                            Utils.getAdminUidForEthernetOnly(dexPolicy3.mEdmStorageProvider));
            if (intForUser > 0) {
                if (dexPolicy3.isEthernetOnlyEnforced()) {
                    if (Utils.isEthernetOnlyApplied(dexPolicy3.mEdmStorageProvider)) {
                        dexPolicy3.showEthernetOnlyNotification(true);
                    } else {
                        dexPolicy3.applyEthernetOnly(contextInfo, true);
                    }
                }
            } else if (dexPolicy3.isEthernetOnlyEnforced()) {
                dexPolicy3.applyEthernetOnly(contextInfo, false);
            }
            super.onChange(z);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Injector {
        public final Context mContext;

        public Injector(Context context) {
            this.mContext = context;
        }
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.server.enterprise.dex.DexPolicy$1] */
    public DexPolicy(Context context) {
        Injector injector = new Injector(context);
        this.mEDM = null;
        this.mEdmStorageProvider = null;
        this.restrictionPolicy = null;
        this.mIWifipolicy = null;
        this.mApplicationPolicy = null;
        this.blocker =
                new SemDesktopModeManager
                        .DesktopModeBlocker() { // from class:
                                                // com.android.server.enterprise.dex.DexPolicy.1
                    public final String onBlocked() {
                        return DexPolicy.this.mContext.getString(
                                R.string.heavy_weight_switcher_text);
                    }
                };
        new DexStateChangeObserver();
        this.mInjector = injector;
        this.mContext = context;
        this.mEdmStorageProvider = new EdmStorageProvider(context);
        this.restrictionPolicy =
                (RestrictionPolicy) EnterpriseService.getPolicyService("restriction_policy");
        this.mEnterpriseDumpHelper = new EnterpriseDumpHelper(context);
        context.registerReceiver(
                new BroadcastReceiver() { // from class:
                                          // com.android.server.enterprise.dex.DexPolicy.2
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        String action = intent.getAction();
                        if (action == null) {
                            Log.d("DexPolicyService", "action is null!");
                        }
                        switch (action) {
                            case "android.intent.action.LOCKED_BOOT_COMPLETED":
                                Log.d("DexPolicyService", "ACTION_LOCKED_BOOT_COMPLETED");
                                if (Utils.isEthernetOnlyApplied(DexPolicy.this.mEdmStorageProvider)
                                        && !Utils.isDexActivated(DexPolicy.this.mContext)) {
                                    DexPolicy.this.mContext_temp =
                                            new ContextInfo(
                                                    Utils.getAdminUidForEthernetOnly(
                                                            DexPolicy.this.mEdmStorageProvider));
                                    DexPolicy dexPolicy = DexPolicy.this;
                                    dexPolicy.applyEthernetOnly(dexPolicy.mContext_temp, false);
                                }
                                if (!Utils.isDexActivated(DexPolicy.this.mContext)) {
                                    DexPolicy.this.exitDexModeSetPackageState();
                                }
                                if (DexPolicy.this.isDexDisabled()
                                        && !DexPolicy.isBlockerRegistered) {
                                    DexPolicy.this.registerDexBlocker();
                                    break;
                                }
                                break;
                            case "com.samsung.android.desktopmode.action.ENTER_DESKTOP_MODE":
                                Log.d("DexPolicyService", "dex enter ");
                                break;
                            case "com.samsung.android.desktopmode.action.EXIT_DESKTOP_MODE":
                                Log.d("DexPolicyService", "dex exit ");
                                break;
                        }
                    }
                },
                GmsAlarmManager$$ExternalSyntheticOutline0.m(
                        "com.samsung.android.desktopmode.action.ENTER_DESKTOP_MODE",
                        "com.samsung.android.desktopmode.action.EXIT_DESKTOP_MODE",
                        "android.intent.action.LOCKED_BOOT_COMPLETED"),
                2);
        Log.d("DexPolicyService", "SEC_PRODUCT_FEATURE_COMMON_SUPPORT_KNOX_DESKTOP is true");
        SemDesktopModeManager semDesktopModeManager =
                (SemDesktopModeManager) context.getSystemService("desktopmode");
        if (semDesktopModeManager != null) {
            semDesktopModeManager.registerListener(
                    new SemDesktopModeManager
                            .DesktopModeListener() { // from class:
                                                     // com.android.server.enterprise.dex.DexPolicy.3
                        public final void onDesktopModeStateChanged(
                                SemDesktopModeState semDesktopModeState) {
                            if (semDesktopModeState.state == 20) {
                                if (semDesktopModeState.enabled == 3) {
                                    Log.d("DexPolicyService", "listener - Dex Enabling");
                                    if (DexPolicy.this.isDexDisabled()) {
                                        DexPolicy.this.registerDexBlocker();
                                    }
                                    if (DexPolicy.this.isEthernetOnlyEnforced()) {
                                        if (Utils.isEthernetOnlyApplied(
                                                DexPolicy.this.mEdmStorageProvider)) {
                                            DexPolicy.this.showEthernetOnlyNotification(true);
                                        } else {
                                            DexPolicy.this.mContext_temp =
                                                    new ContextInfo(
                                                            Utils.getAdminUidForEthernetOnly(
                                                                    DexPolicy.this
                                                                            .mEdmStorageProvider));
                                            DexPolicy dexPolicy = DexPolicy.this;
                                            dexPolicy.applyEthernetOnly(
                                                    dexPolicy.mContext_temp, true);
                                        }
                                    }
                                    Log.d(
                                            "DexPolicyService",
                                            "is Dex Activated : "
                                                    + Utils.isDexActivated(
                                                            DexPolicy.this.mContext));
                                    DexPolicy.this.enterDexModeSetPackageState();
                                }
                                if (semDesktopModeState.enabled == 1) {
                                    Log.d("DexPolicyService", "listener - Dex Disabling");
                                    if (DexPolicy.this.isEthernetOnlyEnforced()) {
                                        DexPolicy.this.mContext_temp =
                                                new ContextInfo(
                                                        Utils.getAdminUidForEthernetOnly(
                                                                DexPolicy.this
                                                                        .mEdmStorageProvider));
                                        DexPolicy dexPolicy2 = DexPolicy.this;
                                        dexPolicy2.applyEthernetOnly(
                                                dexPolicy2.mContext_temp, false);
                                    }
                                    Log.d(
                                            "DexPolicyService",
                                            "is Dex Activated : "
                                                    + Utils.isDexActivated(
                                                            DexPolicy.this.mContext));
                                    DexPolicy.this.exitDexModeSetPackageState();
                                }
                            }
                        }
                    });
        }
    }

    public final int addPackageToDisableList(ContextInfo contextInfo, String str) {
        Log.d("DexPolicyService", "addPackageToDisableList");
        ContextInfo enforceOwnerOnlyAndDexPermission =
                enforceOwnerOnlyAndDexPermission(contextInfo);
        List packagesFromDisableList = getPackagesFromDisableList(enforceOwnerOnlyAndDexPermission);
        if (packagesFromDisableList.contains(str)) {
            Log.d("DexPolicyService", "addPackageToDisableList already blocked package");
            return 3;
        }
        if (!getApplicationPolicy$1()
                .isApplicationInstalled(enforceOwnerOnlyAndDexPermission, str)) {
            DualAppManagerService$$ExternalSyntheticOutline0.m(
                    "addPackageToDisableList : ", str, " is not installed", "DexPolicyService");
            return 2;
        }
        try {
            packagesFromDisableList.add(str);
            if (Utils.isDexActivated(this.mContext)) {
                disablePackage(enforceOwnerOnlyAndDexPermission, str);
            }
            return !writePackageDisableList(
                            enforceOwnerOnlyAndDexPermission, packagesFromDisableList)
                    ? 1
                    : 0;
        } catch (Exception e) {
            OomAdjuster$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("addPackageToDisableList : failed "), "DexPolicyService");
            return 1;
        }
    }

    public final boolean allowScreenTimeoutChange(ContextInfo contextInfo, boolean z) {
        return this.mEdmStorageProvider.putBoolean(
                "DEX_POLICY",
                enforceOwnerOnlyAndDexPermission(contextInfo).mCallerUid,
                z,
                0,
                "screenTimeoutChangeAllowed");
    }

    /* JADX WARN: Can't wrap try/catch for region: R(13:14|15|16|18|19|(8:20|21|23|24|26|27|28|29)|30|31|32|33|34|35|36) */
    /* JADX WARN: Can't wrap try/catch for region: R(36:58|59|60|(4:63|(1:147)(0)|65|61)|148|149|65|66|(4:69|(1:138)(0)|71|67)|139|140|71|72|(4:75|(1:132)(0)|77|73)|133|134|77|78|(4:81|(1:126)(0)|83|79)|127|128|83|84|(3:87|(1:89)(1:120)|85)|121|122|90|91|92|94|95|96|(3:98|99|100)(1:114)|(1:102)|104|105) */
    /* JADX WARN: Code restructure failed: missing block: B:142:0x0201, code lost:

       r0 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:143:0x0202, code lost:

       android.util.Log.w("DexPolicyService", "failed to set applyEthernetOnly", r0);
    */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0189, code lost:

       r0 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x018a, code lost:

       android.util.Log.w("DexPolicyService", "failed to set applyEthernetOnly", r0);
    */
    /* JADX WARN: Removed duplicated region for block: B:102:0x02f1 A[Catch: all -> 0x02ea, Exception -> 0x02ec, TRY_LEAVE, TryCatch #3 {Exception -> 0x02ec, blocks: (B:100:0x02e6, B:102:0x02f1), top: B:99:0x02e6, outer: #9 }] */
    /* JADX WARN: Removed duplicated region for block: B:114:0x02ee  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x02e5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void applyEthernetOnly(com.samsung.android.knox.ContextInfo r35, boolean r36) {
        /*
            Method dump skipped, instructions count: 788
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.dex.DexPolicy.applyEthernetOnly(com.samsung.android.knox.ContextInfo,"
                    + " boolean):void");
    }

    public final void disablePackage(ContextInfo contextInfo, String str) {
        try {
            int i = contextInfo.mCallerUid;
            int intByAdminAndField =
                    getApplicationPolicy$1()
                            .mEdmStorageProvider
                            .getIntByAdminAndField(
                                    i, "APPLICATION", "packageName", str, "controlState");
            if (intByAdminAndField == -1) {
                intByAdminAndField = 0;
            }
            if ((intByAdminAndField & 2) == 2) {
                return;
            }
            getApplicationPolicy$1().setApplicationState(contextInfo, str, false);
            if (getApplicationPolicy$1() != null) {
                getApplicationPolicy$1().updatePackageControlStateForDex(i, str, true);
            }
        } catch (Exception e) {
            OomAdjuster$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("setControlStateMask : failed "), "DexPolicyService");
        }
    }

    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
            printWriter.println("Permission Denial: can't dump DexPolicy");
        } else {
            this.mEnterpriseDumpHelper.dumpTable(
                    printWriter,
                    "DEX_POLICY",
                    new String[] {
                        "adminUid",
                        "dexDisabled",
                        "ethernetOnlyEnabled",
                        "prevCellularData",
                        "prevWifi",
                        "prevWifiTethering",
                        "prevUsbTethering",
                        "prevBtTethering",
                        "prevDataStatus",
                        "prevWifiStatus",
                        "ethernetOnlyApplied",
                        "screenTimeoutChangeAllowed",
                        "useDexStationMacAddress"
                    },
                    null);
        }
    }

    public final void enablePackage(ContextInfo contextInfo, String str) {
        int i;
        try {
            int i2 = contextInfo.mCallerUid;
            if (getApplicationPolicy$1() == null
                    || (i =
                                    getApplicationPolicy$1()
                                            .mEdmStorageProvider
                                            .getIntByAdminAndField(
                                                    i2,
                                                    "APPLICATION",
                                                    "packageName",
                                                    str,
                                                    "controlStateOnDex"))
                            == -1) {
                i = 0;
            }
            if ((i & 2) == 2) {
                getApplicationPolicy$1().setApplicationState(contextInfo, str, true);
            }
            if (getApplicationPolicy$1() != null) {
                getApplicationPolicy$1().updatePackageControlStateForDex(i2, str, false);
            }
        } catch (Exception e) {
            OomAdjuster$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("setControlStateMask : failed "), "DexPolicyService");
        }
    }

    public final boolean enforceEthernetOnly(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndDexPermission =
                enforceOwnerOnlyAndDexPermission(contextInfo);
        boolean putBoolean =
                this.mEdmStorageProvider.putBoolean(
                        "DEX_POLICY",
                        enforceOwnerOnlyAndDexPermission.mCallerUid,
                        z,
                        0,
                        "ethernetOnlyEnabled");
        if (Utils.isDexActivated(this.mContext)) {
            if (z) {
                applyEthernetOnly(enforceOwnerOnlyAndDexPermission, true);
            } else {
                applyEthernetOnly(enforceOwnerOnlyAndDexPermission, false);
            }
        }
        return putBoolean;
    }

    public final ContextInfo enforceOwnerOnlyAndDexPermission(ContextInfo contextInfo) {
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mInjector.mContext);
        }
        return this.mEDM.enforceOwnerOnlyAndActiveAdminPermission(
                contextInfo,
                new ArrayList(Arrays.asList(KnoxCustomManagerService.KNOX_DEX_PERMISSION)));
    }

    public final boolean enforceVirtualMacAddress(ContextInfo contextInfo, boolean z) {
        return this.mEdmStorageProvider.putBoolean(
                "DEX_POLICY",
                enforceOwnerOnlyAndDexPermission(contextInfo).mCallerUid,
                z,
                0,
                "useDexStationMacAddress");
    }

    public final void enterDexModeSetPackageState() {
        Log.d("DexPolicyService", "enterDexModeSetPackageState");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                ArrayList arrayList =
                        (ArrayList)
                                this.mEdmStorageProvider.getValuesListAsUser(
                                        0, 0, "ADMIN", new String[] {"adminUid"});
                if (!arrayList.isEmpty()) {
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        Integer asInteger = ((ContentValues) it.next()).getAsInteger("adminUid");
                        if (asInteger != null) {
                            ContextInfo contextInfo = new ContextInfo(asInteger.intValue());
                            Iterator it2 = getPackagesFromDisableList(contextInfo).iterator();
                            while (it2.hasNext()) {
                                disablePackage(contextInfo, (String) it2.next());
                            }
                        }
                    }
                }
            } catch (Exception e) {
                Log.e("DexPolicyService", "enterDexModeSetPackageState : failed " + e.getMessage());
                e.printStackTrace();
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void exitDexModeSetPackageState() {
        Log.d("DexPolicyService", "exitDexModeSetPackageState");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                List valuesListAsUser =
                        this.mEdmStorageProvider.getValuesListAsUser(
                                0, 0, "ADMIN", new String[] {"adminUid"});
                StringBuilder sb =
                        new StringBuilder("exitDexModeSetPackageState : Admin list size() : ");
                ArrayList arrayList = (ArrayList) valuesListAsUser;
                sb.append(arrayList.size());
                Log.d("DexPolicyService", sb.toString());
                if (!arrayList.isEmpty()) {
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        Integer asInteger = ((ContentValues) it.next()).getAsInteger("adminUid");
                        if (asInteger != null) {
                            ContextInfo contextInfo = new ContextInfo(asInteger.intValue());
                            List packagesFromDisableList = getPackagesFromDisableList(contextInfo);
                            writePackageDisableList(contextInfo, null);
                            Iterator it2 = packagesFromDisableList.iterator();
                            while (it2.hasNext()) {
                                enablePackage(contextInfo, (String) it2.next());
                            }
                            writePackageDisableList(contextInfo, packagesFromDisableList);
                        }
                    }
                }
            } catch (Exception e) {
                Log.e("DexPolicyService", "exitDexModeSetPackageState : failed " + e.getMessage());
                e.printStackTrace();
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final ApplicationPolicy getApplicationPolicy$1() {
        if (this.mApplicationPolicy == null) {
            this.mInjector.getClass();
            this.mApplicationPolicy =
                    (ApplicationPolicy) EnterpriseService.getPolicyService("application_policy");
        }
        return this.mApplicationPolicy;
    }

    public final List getPackagesFromDisableList(ContextInfo contextInfo) {
        Log.d("DexPolicyService", "getPackagesFromDisableList");
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mInjector.mContext);
        }
        return getApplicationPolicy$1() != null
                ? getApplicationPolicy$1()
                        .getPackagesFromDisableListForDex(
                                this.mEDM.enforceActiveAdminPermissionByContext(
                                                contextInfo,
                                                new ArrayList(
                                                        Arrays.asList(
                                                                KnoxCustomManagerService
                                                                        .KNOX_DEX_PERMISSION)))
                                        .mCallerUid)
                : new ArrayList();
    }

    public final String getVirtualMacAddress() {
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mInjector.mContext);
        }
        this.mEDM.enforceActiveAdminPermission(
                new ArrayList(Arrays.asList(KnoxCustomManagerService.KNOX_DEX_PERMISSION)));
        long clearCallingIdentity = Binder.clearCallingIdentity();
        String str = "";
        try {
            try {
                str =
                        INetworkManagementService.Stub.asInterface(
                                        ServiceManager.getService("network_management"))
                                .getInterfaceConfig("eth0")
                                .getHardwareAddress();
                Log.d("DexPolicyService", "getVirtualMacAddress : " + str);
            } catch (Exception e) {
                Log.e("DexPolicyService", "getVirtualMacAddress : failed " + e.getMessage());
                e.printStackTrace();
            }
            return str;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isDexActivated() {
        return Utils.isDexActivated(this.mContext);
    }

    public final boolean isDexDisabled() {
        try {
            Iterator it =
                    this.mEdmStorageProvider
                            .getBooleanListAsUser(0, "DEX_POLICY", "dexDisabled")
                            .iterator();
            while (it.hasNext()) {
                if (((Boolean) it.next()).booleanValue()) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            OomAdjuster$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("isDexDisabled : failed "), "DexPolicyService");
            return false;
        }
    }

    public final boolean isEthernetOnlyEnforced() {
        try {
            Iterator it =
                    this.mEdmStorageProvider
                            .getBooleanListAsUser(0, "DEX_POLICY", "ethernetOnlyEnabled")
                            .iterator();
            while (it.hasNext()) {
                if (((Boolean) it.next()).booleanValue()) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            OomAdjuster$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("isEthernetOnlyEnforced : failed "), "DexPolicyService");
            return false;
        }
    }

    public final boolean isScreenTimeoutChangeAllowed() {
        try {
            Iterator it =
                    this.mEdmStorageProvider
                            .getBooleanListAsUser(0, "DEX_POLICY", "screenTimeoutChangeAllowed")
                            .iterator();
            while (it.hasNext()) {
                if (!((Boolean) it.next()).booleanValue()) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            OomAdjuster$$ExternalSyntheticOutline0.m(
                    e,
                    new StringBuilder("isScreenTimeoutChangeAllowed : failed "),
                    "DexPolicyService");
            return true;
        }
    }

    public final boolean isVirtualMacAddressEnforced() {
        try {
            Iterator it =
                    this.mEdmStorageProvider
                            .getBooleanListAsUser(0, "DEX_POLICY", "useDexStationMacAddress")
                            .iterator();
            while (it.hasNext()) {
                if (((Boolean) it.next()).booleanValue()) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            OomAdjuster$$ExternalSyntheticOutline0.m(
                    e,
                    new StringBuilder("isVirtualMacAddressEnforced : failed "),
                    "DexPolicyService");
            return false;
        }
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void notifyToAddSystemService(String str, IBinder iBinder) {}

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminAdded(int i) {}

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminRemoved(int i) {
        if (isBlockerRegistered && !isDexDisabled()) {
            unRegisterDexBlocker();
        }
        if (Utils.isEthernetOnlyApplied(this.mEdmStorageProvider)) {
            return;
        }
        showEthernetOnlyNotification(false);
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onPreAdminRemoval(int i) {}

    public final void registerDexBlocker() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ((SemDesktopModeManager)
                            this.mContext.getApplicationContext().getSystemService("desktopmode"))
                    .registerBlocker(this.blocker);
            isBlockerRegistered = true;
            Log.d("DexPolicyService", "registerDexBlocker was registered");
        } catch (Exception unused) {
            Log.d("DexPolicyService", "registerDexBlocker was failed");
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public final int removePackageFromDisableList(ContextInfo contextInfo, String str) {
        Log.d("DexPolicyService", "removePackageFromDisableList");
        ContextInfo enforceOwnerOnlyAndDexPermission =
                enforceOwnerOnlyAndDexPermission(contextInfo);
        List packagesFromDisableList = getPackagesFromDisableList(enforceOwnerOnlyAndDexPermission);
        if (!packagesFromDisableList.contains(str)) {
            Log.d("DexPolicyService", "removePackageFromDisableList not find blocked package name");
            return 4;
        }
        try {
            packagesFromDisableList.remove(str);
            boolean writePackageDisableList =
                    writePackageDisableList(
                            enforceOwnerOnlyAndDexPermission, packagesFromDisableList);
            if (Utils.isDexActivated(this.mContext)) {
                enablePackage(enforceOwnerOnlyAndDexPermission, str);
            }
            return !writePackageDisableList ? 1 : 0;
        } catch (Exception e) {
            OomAdjuster$$ExternalSyntheticOutline0.m(
                    e,
                    new StringBuilder("removePackageFromDisableList : failed "),
                    "DexPolicyService");
            return 1;
        }
    }

    public final boolean setDexDisabled(ContextInfo contextInfo, boolean z) {
        boolean putBoolean =
                this.mEdmStorageProvider.putBoolean(
                        "DEX_POLICY",
                        enforceOwnerOnlyAndDexPermission(contextInfo).mCallerUid,
                        z,
                        0,
                        "dexDisabled");
        if (!isBlockerRegistered && z && putBoolean) {
            registerDexBlocker();
        }
        if (isBlockerRegistered && putBoolean && !isDexDisabled()) {
            unRegisterDexBlocker();
        }
        return putBoolean;
    }

    public final void setEthernetOnlyApplied(ContextInfo contextInfo, boolean z) {
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                "setEthernetOnlyApplied - ", "DexPolicyService", z);
        this.mEdmStorageProvider.putBoolean(
                "DEX_POLICY", contextInfo.mCallerUid, z, 0, "ethernetOnlyApplied");
    }

    public final void showEthernetOnlyNotification(boolean z) {
        String string = this.mContext.getString(R.string.heavy_weight_switcher_title);
        NotificationManager notificationManager =
                (NotificationManager) this.mContext.getSystemService("notification");
        if (notificationManager == null) {
            Log.d("DexPolicyService", "Failed to get NotificationManager");
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (z) {
                Notification.Builder builder =
                        new Notification.Builder(this.mContext, "MDM_DEXPOLICY");
                builder.setWhen(0L);
                builder.setSmallIcon(R.drawable.pointer_grabbing_icon);
                builder.setContentTitle("Dex Ethernet only mode");
                builder.setContentText(string);
                builder.setStyle(new Notification.BigTextStyle().bigText(string));
                builder.setPriority(2);
                builder.setOngoing(true);
                notificationManager.notify(4557, builder.build());
            } else {
                notificationManager.cancel(4557);
            }
        } catch (Exception unused) {
            Log.d("DexPolicyService", "showEthernetOnlyNotification was failed");
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void systemReady() {}

    public final void unRegisterDexBlocker() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ((SemDesktopModeManager)
                            this.mContext.getApplicationContext().getSystemService("desktopmode"))
                    .unregisterBlocker(this.blocker);
            isBlockerRegistered = false;
            Log.d("DexPolicyService", "registerDexBlocker was unregistered");
        } catch (Exception unused) {
            Log.d("DexPolicyService", "unRegisterDexBlocker was failed");
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public final boolean writePackageDisableList(ContextInfo contextInfo, List list) {
        int i = contextInfo.mCallerUid;
        boolean z = false;
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("adminUid", Integer.valueOf(i));
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put("dexApplicationDisableList", Utils.serializeObject(list));
            if (this.mEdmStorageProvider.getCount("DEX_POLICY", contentValues) > 0) {
                z = this.mEdmStorageProvider.putValues("DEX_POLICY", contentValues2, contentValues);
                Log.d(
                        "DexPolicyService",
                        "writePackageList(dexApplicationDisableList) : update : ret : " + z);
            } else {
                contentValues2.put("adminUid", Integer.valueOf(i));
                z = this.mEdmStorageProvider.putValuesNoUpdate("DEX_POLICY", contentValues2);
                Log.d(
                        "DexPolicyService",
                        "writePackageList(dexApplicationDisableList) : insert : ret : " + z);
            }
        } catch (Exception e) {
            OomAdjuster$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("writePackageList : failed "), "DexPolicyService");
        }
        return z;
    }
}
