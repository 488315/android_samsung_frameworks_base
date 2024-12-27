package com.android.server.enterprise.dualdar;

import android.app.AppGlobals;
import android.app.admin.DevicePolicyManager;
import android.app.admin.PasswordMetrics;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.os.UserHandle;
import android.sec.enterprise.auditlog.AuditLog;

import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockSettingsInternal;
import com.android.internal.widget.LockscreenCredential;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.storage.SettingNotFoundException;
import com.android.server.knox.dar.ddar.DDLog;
import com.android.server.pm.PackageManagerShellCommandDataLoader;
import com.android.server.pm.PersonaServiceHelper;

import com.samsung.android.knox.AppIdentity;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.analytics.KnoxAnalytics;
import com.samsung.android.knox.analytics.KnoxAnalyticsData;
import com.samsung.android.knox.dar.VirtualLockUtils;
import com.samsung.android.knox.dar.ddar.DualDarAuthUtils;
import com.samsung.android.knox.dar.ddar.DualDarManager;
import com.samsung.android.knox.ddar.IDualDARPolicy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public final class DualDARPolicy extends IDualDARPolicy.Stub implements EnterpriseServiceCallback {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context context;
    public final EdmStorageProvider edmStorageProvider;
    public final DualDarAuthUtils mDualDarAuthUtils;
    public final Injector mInjector;
    public EnterpriseDeviceManager edm = null;
    public final Map dualDarConfigCache = new HashMap();
    public final VirtualLockUtils mVirtualLockUtils = new VirtualLockUtils();

    public final class Injector {
        public final Context mContext;

        public Injector(Context context) {
            this.mContext = context;
        }
    }

    public DualDARPolicy(Context context) {
        this.mInjector = new Injector(context);
        this.context = context;
        this.edmStorageProvider = new EdmStorageProvider(context);
        this.mDualDarAuthUtils = new DualDarAuthUtils(context);
    }

    public static String flatten(List list) {
        StringBuilder sb = new StringBuilder("");
        ArrayList arrayList = (ArrayList) list;
        Iterator it = arrayList.iterator();
        int i = 0;
        while (it.hasNext()) {
            sb.append((String) it.next());
            i++;
            if (i < arrayList.size()) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    public static String flattenPackages(List list) {
        ArrayList arrayList = new ArrayList();
        Iterator it = ((ArrayList) list).iterator();
        while (it.hasNext()) {
            String packageName = ((AppIdentity) it.next()).getPackageName();
            if (packageName != null) {
                packageName = packageName.trim();
            }
            arrayList.add(packageName);
        }
        return flatten(arrayList);
    }

    public static String flattenSignatures(List list) {
        ArrayList arrayList = new ArrayList();
        Iterator it = ((ArrayList) list).iterator();
        while (it.hasNext()) {
            String signature = ((AppIdentity) it.next()).getSignature();
            String trim = signature != null ? signature.trim() : "";
            if (trim.length() == 0) {
                trim = PackageManagerShellCommandDataLoader.STDIN_PATH;
            }
            arrayList.add(trim);
        }
        return flatten(arrayList);
    }

    public static String getWhitelistPkgesFromConfig(Bundle bundle) {
        ArrayList arrayList = new ArrayList();
        AppIdentity[] parcelableArray =
                bundle.getParcelableArray("dualdar-config-datalock-whitelistpackages");
        if (parcelableArray != null && parcelableArray.length > 0) {
            for (AppIdentity appIdentity : parcelableArray) {
                if (appIdentity != null
                        && appIdentity.getPackageName() != null
                        && appIdentity.getPackageName().length() != 0) {
                    arrayList.add(appIdentity.getPackageName());
                }
            }
        }
        return flatten(arrayList);
    }

    public static void logDualDarAnalytics(ContextInfo contextInfo, Bundle bundle, String str) {
        DDLog.d(
                "DualDARPolicy:DualDARAnalytics",
                AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                        contextInfo.mContainerId, "logDualDarAnalytics, userID", ", event: ", str),
                new Object[0]);
        if (str.equals("datalock_timeout")) {
            KnoxAnalyticsData knoxAnalyticsData =
                    new KnoxAnalyticsData("KNOX_DUALDAR", 1, "DATALOCK_TIMEOUT");
            long j = bundle.getLong("dualdar-config-datalock-timeout");
            knoxAnalyticsData.setProperty("datalckTmoutV", j);
            DDLog.d(
                    "DualDARPolicy:DualDARAnalytics",
                    "EVENT : DATALOCK_TIMEOUT TIME: " + j,
                    new Object[0]);
            DDLog.d(
                    "DualDARPolicy:DualDARAnalytics",
                    "Payload / " + knoxAnalyticsData.toString(),
                    new Object[0]);
            KnoxAnalytics.log(knoxAnalyticsData);
            return;
        }
        if (!str.equals("whitelisted_apps")) {
            if (str.equals("de_access_restriction")) {
                DDLog.d(
                        "DualDARPolicy:DualDARAnalytics",
                        "DE Restriction Value: "
                                + bundle.getBoolean("dualdar-config-de-restriction"),
                        new Object[0]);
                return;
            }
            return;
        }
        KnoxAnalyticsData knoxAnalyticsData2 =
                new KnoxAnalyticsData("KNOX_DUALDAR", 1, "WHITELISTED_APPS");
        String whitelistPkgesFromConfig = getWhitelistPkgesFromConfig(bundle);
        knoxAnalyticsData2.setProperty("wPkgLst", whitelistPkgesFromConfig);
        DDLog.d(
                "DualDARPolicy:DualDARAnalytics",
                "EVENT:WHITELISTED_APPS, WhiteListed Packages: " + whitelistPkgesFromConfig,
                new Object[0]);
        DDLog.d(
                "DualDARPolicy:DualDARAnalytics",
                "Payload / " + knoxAnalyticsData2.toString(),
                new Object[0]);
        KnoxAnalytics.log(knoxAnalyticsData2);
    }

    public final boolean clearPolicy(ContextInfo contextInfo) {
        ContextInfo enforceDualDARPermission = enforceDualDARPermission(contextInfo);
        int i = enforceDualDARPermission.mCallerUid;
        int i2 = enforceDualDARPermission.mContainerId;
        try {
            boolean removeByAdmin = this.edmStorageProvider.removeByAdmin(i, i2, "DUAL_DAR");
            ((HashMap) this.dualDarConfigCache).remove(Integer.valueOf(i2));
            DDLog.d(
                    "DualDARPolicy",
                    "clearPolicy policy passed? : " + removeByAdmin,
                    new Object[0]);
            return removeByAdmin;
        } catch (Exception e) {
            DDLog.e("DualDARPolicy", "Exception while clearing policy, " + e, new Object[0]);
            e.printStackTrace();
            return false;
        }
    }

    public final boolean clearResetPasswordTokenForInner(ContextInfo contextInfo) {
        ContextInfo enforceDualDAROnDOPermission = enforceDualDAROnDOPermission(contextInfo);
        DDLog.d(
                "DualDARPolicy",
                "clearResetPasswordTokenForInner: " + enforceDualDAROnDOPermission.mContainerId,
                new Object[0]);
        int innerAuthUserId =
                this.mDualDarAuthUtils.getInnerAuthUserId(
                        UserHandle.getUserId(enforceDualDAROnDOPermission.mCallerUid));
        if (innerAuthUserId < 0) {
            return false;
        }
        return this.mVirtualLockUtils.clearResetPasswordToken(innerAuthUserId);
    }

    public final ContextInfo enforceDualDAROnDOPermission(ContextInfo contextInfo) {
        ContextInfo enforceDualDARPermission = enforceDualDARPermission(contextInfo);
        if (DualDarManager.isOnDeviceOwner(enforceDualDARPermission.mContainerId)) {
            return enforceDualDARPermission;
        }
        throw new SecurityException("This API works only with managed device (DO enabled)");
    }

    public final ContextInfo enforceDualDARPermission(ContextInfo contextInfo) {
        if (this.edm == null) {
            this.edm = EnterpriseDeviceManager.getInstance(this.mInjector.mContext);
        }
        return this.edm.enforceActiveAdminPermissionByContext(
                contextInfo,
                new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_DUAL_DAR")));
    }

    public final void getClientAndProfileOwnerApps(ContextInfo contextInfo, List list, List list2) {
        List<PackageInfo> list3;
        String nameForUid = this.context.getPackageManager().getNameForUid(contextInfo.mCallerUid);
        if (nameForUid != null) {
            ((ArrayList) list2).add(nameForUid);
            ((ArrayList) list).add(new AppIdentity(nameForUid, ""));
        }
        try {
            list3 =
                    AppGlobals.getPackageManager()
                            .getPackagesHoldingPermissions(
                                    new String[] {
                                        "com.samsung.android.knox.permission.KNOX_KPU_INTERNAL"
                                    },
                                    0L,
                                    contextInfo.mContainerId)
                            .getList();
        } catch (RemoteException e) {
            DDLog.d(
                    "DualDARPolicy",
                    "RemoteException in getClientAndProfileOwnerApps while checking permissions "
                            + e.getMessage(),
                    new Object[0]);
            list3 = null;
        }
        if (list3 != null) {
            for (PackageInfo packageInfo : list3) {
                ((ArrayList) list2).add(packageInfo.packageName);
                ((ArrayList) list).add(new AppIdentity(packageInfo.packageName, ""));
            }
        }
        Bundle configFromDb = getConfigFromDb(contextInfo.mCallerUid, contextInfo.mContainerId);
        if (configFromDb != null) {
            String string = configFromDb.getString("dualdar-config-client-package");
            String string2 = configFromDb.getString("dualdar-config-client-signature");
            if (string != null) {
                ((ArrayList) list2).add(string);
                ((ArrayList) list).add(new AppIdentity(string, string2));
            }
        }
    }

    public final String getClientInfo(int i) {
        if (((HashMap) this.dualDarConfigCache).containsKey(Integer.valueOf(i))) {
            if (((HashMap) this.dualDarConfigCache).get(Integer.valueOf(i)) != null) {
                return ((Bundle) ((HashMap) this.dualDarConfigCache).get(Integer.valueOf(i)))
                        .getString("dualdar-config-client-package");
            }
        }
        return "";
    }

    public final Bundle getConfig(ContextInfo contextInfo) {
        int i = enforceDualDARPermission(contextInfo).mContainerId;
        return (Bundle) ((HashMap) this.dualDarConfigCache).get(Integer.valueOf(i));
    }

    public final Bundle getConfigFromDb(int i, int i2) {
        int i3;
        String str;
        String str2;
        try {
            DDLog.d(
                    "DualDARPolicy",
                    "getConfigFromDb adminId : " + i + " userId : " + i2,
                    new Object[0]);
            String string = this.edmStorageProvider.getString(i, i2, "DUAL_DAR", "dataLockTimeOut");
            if (string == null) {
                if (!DualDarManager.isOnDeviceOwner(i2)) {
                    DDLog.e(
                            "DualDARPolicy",
                            "getConfig : DualDAR not enabled for: " + i2,
                            new Object[0]);
                    return null;
                }
                DDLog.d("DualDARPolicy", "getConfig : DualDAR at DO case.", new Object[0]);
                string = "-1";
            }
            long parseLong = Long.parseLong(string);
            boolean z = true;
            try {
                i3 = this.edmStorageProvider.getInt(i, i2, "DUAL_DAR", "deRestriction");
            } catch (SettingNotFoundException unused) {
                DDLog.e("DualDARPolicy", "Unable to read deRestriction field", new Object[0]);
                i3 = 1;
            }
            if (SemPersonaManager.isDualDARNativeCrypto(i2)) {
                str = "";
                str2 = "";
            } else {
                str = this.edmStorageProvider.getString(i, i2, "DUAL_DAR", "clientAppPackageName");
                str2 = this.edmStorageProvider.getString(i, i2, "DUAL_DAR", "clientAppSignature");
            }
            Bundle bundle = new Bundle();
            bundle.putString("dualdar-config-client-package", str);
            bundle.putString("dualdar-config-client-signature", str2);
            if (i3 <= 0) {
                z = false;
            }
            bundle.putBoolean("dualdar-config-de-restriction", z);
            bundle.putLong("dualdar-config-datalock-timeout", parseLong);
            bundle.putParcelableArray(
                    "dualdar-config-datalock-whitelistpackages",
                    getWhitelistedAppsFromStorage(i, i2));
            return bundle;
        } catch (Exception e) {
            DDLog.e("DualDARPolicy", "getConfigFromDb has an exception: " + e, new Object[0]);
            e.printStackTrace();
            return null;
        }
    }

    public final int getPasswordMinimumLengthForInner(ContextInfo contextInfo) {
        if (((DevicePolicyManager) this.mInjector.mContext.getSystemService("device_policy"))
                        .getPasswordQuality(null)
                >= 131072) {
            return getPasswordMinimumLengthForInnerInternal();
        }
        return 0;
    }

    public final int getPasswordMinimumLengthForInnerInternal() {
        ArrayList intListAsUser =
                this.edmStorageProvider.getIntListAsUser(0, 0, "DUAL_DAR", "innerPasswordMinLen");
        if (intListAsUser.size() != 0) {
            return ((Integer) Collections.max(intListAsUser)).intValue();
        }
        DDLog.e("DualDARPolicy", "Unable to read field", new Object[0]);
        return 0;
    }

    public final PasswordMetrics getPasswordMinimumMetrics(int i) {
        DevicePolicyManager devicePolicyManager =
                (DevicePolicyManager) this.context.getSystemService("device_policy");
        if (devicePolicyManager == null) {
            DDLog.e("DualDARPolicy", "Couldn't access service", new Object[0]);
            return null;
        }
        int passwordMinimumLengthForInnerInternal = getPasswordMinimumLengthForInnerInternal();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            PasswordMetrics passwordMinimumMetrics =
                    devicePolicyManager.getPasswordMinimumMetrics(i);
            if (passwordMinimumMetrics == null) {
                DDLog.e(
                        "DualDARPolicy",
                        "Failed to dpm.getPasswordMinimumMetrics()",
                        new Object[0]);
                return null;
            }
            passwordMinimumMetrics.length =
                    Math.min(256, Math.max(passwordMinimumLengthForInnerInternal, 4));
            return passwordMinimumMetrics;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final AppIdentity[] getWhitelistedAppsFromStorage(int i, int i2) {
        String string =
                this.edmStorageProvider.getString(
                        i, i2, "DUAL_DAR", "whiteListPkgsForDataLockState");
        ArrayList arrayList = new ArrayList();
        if (string != null && string.length() > 0) {
            StringTokenizer stringTokenizer = new StringTokenizer(string, ", ");
            while (stringTokenizer.hasMoreTokens()) {
                arrayList.add(stringTokenizer.nextToken().trim());
            }
        }
        String string2 =
                this.edmStorageProvider.getString(
                        i, i2, "DUAL_DAR", "whiteListPkgSignaturesForDataLockState");
        ArrayList arrayList2 = new ArrayList();
        if (string2 != null && string2.length() > 0) {
            StringTokenizer stringTokenizer2 = new StringTokenizer(string2, ", ");
            while (stringTokenizer2.hasMoreTokens()) {
                String trim = stringTokenizer2.nextToken().trim();
                if (trim.compareTo(PackageManagerShellCommandDataLoader.STDIN_PATH) == 0) {
                    trim = "";
                }
                arrayList2.add(trim);
            }
        }
        ArrayList arrayList3 = new ArrayList();
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            arrayList3.add(
                    new AppIdentity((String) arrayList.get(i3), (String) arrayList2.get(i3)));
        }
        return (AppIdentity[]) arrayList3.toArray(new AppIdentity[0]);
    }

    public final boolean isActivePasswordSufficientForInner(ContextInfo contextInfo) {
        PasswordMetrics passwordMetrics;
        int userId = UserHandle.getUserId(enforceDualDAROnDOPermission(contextInfo).mCallerUid);
        LockSettingsInternal lockSettingsInternal =
                (LockSettingsInternal) LocalServices.getService(LockSettingsInternal.class);
        if (lockSettingsInternal != null) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                passwordMetrics =
                        lockSettingsInternal.getUserPasswordMetrics(
                                this.mDualDarAuthUtils.getInnerAuthUserId(userId));
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } else {
            DDLog.e("DualDARPolicy", "Couldn't access service", new Object[0]);
            passwordMetrics = null;
        }
        PasswordMetrics passwordMinimumMetrics = getPasswordMinimumMetrics(userId);
        if (passwordMetrics == null || passwordMinimumMetrics == null) {
            DDLog.e("DualDARPolicy", "Invalid metrics", new Object[0]);
            return false;
        }
        List validatePasswordMetrics =
                PasswordMetrics.validatePasswordMetrics(passwordMinimumMetrics, 0, passwordMetrics);
        boolean isEmpty = validatePasswordMetrics.isEmpty();
        if (!isEmpty) {
            DDLog.d(
                    "DualDARPolicy",
                    "Password validation errors: " + validatePasswordMetrics,
                    new Object[0]);
        }
        return isEmpty;
    }

    public final boolean isResetPasswordTokenActiveForInner(ContextInfo contextInfo) {
        int innerAuthUserId =
                this.mDualDarAuthUtils.getInnerAuthUserId(
                        UserHandle.getUserId(enforceDualDAROnDOPermission(contextInfo).mCallerUid));
        if (innerAuthUserId < 0) {
            return false;
        }
        return this.mVirtualLockUtils.isResetPasswordTokenActive(innerAuthUserId);
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void notifyToAddSystemService(String str, IBinder iBinder) {}

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminAdded(int i) {}

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminRemoved(int i) {}

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onPreAdminRemoval(int i) {}

    public final void refreshConfig(int i, int i2) {
        DDLog.d(
                "DualDARPolicy",
                ArrayUtils$$ExternalSyntheticOutline0.m(
                        i, i2, "refreshConfig adminId : ", ", userId : "),
                new Object[0]);
        Bundle configFromDb = getConfigFromDb(i, i2);
        if (configFromDb != null) {
            DDLog.d("DualDARPolicy", "refreshConfig cache", new Object[0]);
            ((HashMap) this.dualDarConfigCache).put(Integer.valueOf(i2), configFromDb);
        }
    }

    public final boolean resetPasswordWithTokenForInner(
            ContextInfo contextInfo, final String str, final byte[] bArr) {
        ContextInfo enforceDualDAROnDOPermission = enforceDualDAROnDOPermission(contextInfo);
        DDLog.d(
                "DualDARPolicy",
                "resetPasswordWithTokenForInner: " + enforceDualDAROnDOPermission.mContainerId,
                new Object[0]);
        final int userId = UserHandle.getUserId(enforceDualDAROnDOPermission.mCallerUid);
        final int innerAuthUserId = this.mDualDarAuthUtils.getInnerAuthUserId(userId);
        if (innerAuthUserId >= 0) {
            return ((Boolean)
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils.ThrowingSupplier() { // from class:
                                        // com.android.server.enterprise.dualdar.DualDARPolicy$$ExternalSyntheticLambda0
                                        public final Object getOrThrow() {
                                            DualDARPolicy dualDARPolicy = DualDARPolicy.this;
                                            int i = userId;
                                            String str2 = str;
                                            byte[] bArr2 = bArr;
                                            int i2 = innerAuthUserId;
                                            dualDARPolicy.getClass();
                                            if (!new LockPatternUtils(
                                                            dualDARPolicy.mInjector.mContext)
                                                    .isSecure(i)) {
                                                DDLog.e(
                                                        "DualDARPolicy",
                                                        "resetPasswordWithTokenForInner : Not"
                                                            + " permitted while device insecure",
                                                        new Object[0]);
                                                return Boolean.FALSE;
                                            }
                                            if (str2 == null) {
                                                str2 = "";
                                            }
                                            boolean isNumericOnly =
                                                    PasswordMetrics.isNumericOnly(str2);
                                            PasswordMetrics passwordMinimumMetrics =
                                                    dualDARPolicy.getPasswordMinimumMetrics(i);
                                            if (str2.isEmpty()) {
                                                DDLog.e(
                                                        "DualDARPolicy",
                                                        "resetPasswordWithTokenForInner : Not"
                                                            + " permitted for empty password",
                                                        new Object[0]);
                                                return Boolean.FALSE;
                                            }
                                            if (passwordMinimumMetrics != null) {
                                                LockscreenCredential createPin =
                                                        isNumericOnly
                                                                ? LockscreenCredential.createPin(
                                                                        str2)
                                                                : LockscreenCredential
                                                                        .createPassword(str2);
                                                try {
                                                    List validateCredential =
                                                            PasswordMetrics.validateCredential(
                                                                    passwordMinimumMetrics,
                                                                    0,
                                                                    createPin);
                                                    if (!validateCredential.isEmpty()) {
                                                        DDLog.e(
                                                                "DualDARPolicy",
                                                                "Failed to reset password due to"
                                                                    + " constraint violation: "
                                                                        + validateCredential.get(0),
                                                                new Object[0]);
                                                        Boolean bool = Boolean.FALSE;
                                                        if (createPin == null) {
                                                            return bool;
                                                        }
                                                        createPin.close();
                                                        return bool;
                                                    }
                                                    if (createPin != null) {
                                                        createPin.close();
                                                    }
                                                } catch (Throwable th) {
                                                    if (createPin != null) {
                                                        try {
                                                            createPin.close();
                                                        } catch (Throwable th2) {
                                                            th.addSuppressed(th2);
                                                        }
                                                    }
                                                    throw th;
                                                }
                                            }
                                            return Boolean.valueOf(
                                                    dualDARPolicy.mVirtualLockUtils
                                                            .resetPasswordWithToken(
                                                                    str2, bArr2, i2));
                                        }
                                    }))
                    .booleanValue();
        }
        DDLog.e(
                "DualDARPolicy",
                VibrationParam$1$$ExternalSyntheticOutline0.m(
                        innerAuthUserId,
                        "resetPasswordWithTokenForInner: Invalid inner auth user : "),
                new Object[0]);
        return false;
    }

    public final void setClientInfo(ContextInfo contextInfo, String str, String str2, String str3) {
        boolean z;
        DDLog.d(
                "DualDARPolicy",
                ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                        "setClientInfo called, clientPkgName: ", str),
                new Object[0]);
        ContextInfo enforceDualDARPermission = enforceDualDARPermission(contextInfo);
        ContentValues contentValues = new ContentValues();
        if (SemPersonaManager.isDualDARNativeCrypto(enforceDualDARPermission.mContainerId)
                || str == null) {
            z = true;
        } else {
            DDLog.d("DualDARPolicy", "setClientInfo called, pkgName not null", new Object[0]);
            contentValues.put("clientAppPackageName", str);
            if (str2 != null) {
                DDLog.d(
                        "DualDARPolicy",
                        "setClientInfo called, pkgSignature not null",
                        new Object[0]);
                contentValues.put("clientAppSignature", str2);
            }
            DDLog.d(
                    "DualDARPolicy",
                    "setClientInfo CallerUid : "
                            + enforceDualDARPermission.mCallerUid
                            + ", ContainerId = "
                            + enforceDualDARPermission.mContainerId,
                    new Object[0]);
            z =
                    this.edmStorageProvider.putValues(
                            enforceDualDARPermission.mCallerUid,
                            enforceDualDARPermission.mContainerId,
                            "DUAL_DAR",
                            contentValues);
        }
        if (z) {
            DDLog.d("DualDARPolicy", "Add client app and PO pkgs to whitelist", new Object[0]);
            ArrayList arrayList = new ArrayList();
            getClientAndProfileOwnerApps(enforceDualDARPermission, arrayList, new ArrayList());
            DDLog.d(
                    "DualDARPolicy",
                    "setDefaultWhitelistedApps called, whitelistAppCount: " + arrayList.size(),
                    new Object[0]);
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put("whiteListPkgsForDataLockState", flattenPackages(arrayList));
            contentValues2.put(
                    "whiteListPkgSignaturesForDataLockState", flattenSignatures(arrayList));
            if (this.edmStorageProvider.putValues(
                    enforceDualDARPermission.mCallerUid,
                    enforceDualDARPermission.mContainerId,
                    "DUAL_DAR",
                    contentValues2)) {
                DDLog.d("DualDARPolicy", "successfully added whitelist packages", new Object[0]);
            } else {
                DDLog.e(
                        "DualDARPolicy",
                        "failed to write new whitelist packages to persistent storage",
                        new Object[0]);
            }
            DDLog.d(
                    "DualDARPolicy",
                    "Force refresh the in-memory cache, callerUid = "
                            + enforceDualDARPermission.mCallerUid
                            + ", mContainerId = "
                            + enforceDualDARPermission.mContainerId,
                    new Object[0]);
            refreshConfig(
                    enforceDualDARPermission.mCallerUid, enforceDualDARPermission.mContainerId);
        }
        if (SemPersonaManager.isDualDARNativeCrypto(enforceDualDARPermission.mContainerId)) {
            String dualDARVersion = com.samsung.android.knox.ddar.DualDARPolicy.getDualDARVersion();
            if (dualDARVersion == null) {
                dualDARVersion = "";
            }
            str3 = dualDARVersion;
            str = "Samsung";
        }
        DDLog.d(
                "DualDARPolicy:DualDARAnalytics",
                BootReceiver$$ExternalSyntheticOutline0.m(
                        "Final Logging Crypto: pkg Name :", str, ", clientVersion: ", str3),
                new Object[0]);
        AuditLog.logAsUser(
                5,
                1,
                true,
                Process.myPid(),
                "DualDARPolicy",
                BootReceiver$$ExternalSyntheticOutline0.m(
                        "Admin created DualDAR with Third Party client package: ",
                        str,
                        ", client library version: ",
                        str3),
                enforceDualDARPermission.mContainerId);
    }

    /* JADX WARN: Code restructure failed: missing block: B:84:0x0204, code lost:

       if (r4 != false) goto L82;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int setConfig(com.samsung.android.knox.ContextInfo r25, android.os.Bundle r26) {
        /*
            Method dump skipped, instructions count: 808
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.dualdar.DualDARPolicy.setConfig(com.samsung.android.knox.ContextInfo,"
                    + " android.os.Bundle):int");
    }

    public final boolean setPasswordMinimumLengthForInner(ContextInfo contextInfo, int i) {
        ContextInfo enforceDualDAROnDOPermission = enforceDualDAROnDOPermission(contextInfo);
        if (((DevicePolicyManager) this.mInjector.mContext.getSystemService("device_policy"))
                        .getPasswordQuality(null)
                < 131072) {
            throw new IllegalStateException(
                    String.format(
                            "password quality should be at least %d for"
                                + " setPasswordMinimumLengthForInner",
                            131072));
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("innerPasswordMinLen", Integer.valueOf(i));
        return this.edmStorageProvider.putValues(
                enforceDualDAROnDOPermission.mCallerUid,
                enforceDualDAROnDOPermission.mContainerId,
                "DUAL_DAR",
                contentValues);
    }

    public final boolean setResetPasswordTokenForInner(ContextInfo contextInfo, byte[] bArr) {
        ContextInfo enforceDualDAROnDOPermission = enforceDualDAROnDOPermission(contextInfo);
        DDLog.d(
                "DualDARPolicy",
                "setResetPasswordTokenForInner: " + enforceDualDAROnDOPermission.mContainerId,
                new Object[0]);
        int innerAuthUserId =
                this.mDualDarAuthUtils.getInnerAuthUserId(
                        UserHandle.getUserId(enforceDualDAROnDOPermission.mCallerUid));
        if (innerAuthUserId < 0) {
            return false;
        }
        return this.mVirtualLockUtils.setResetPasswordToken(bArr, innerAuthUserId);
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void systemReady() {
        int dualDARUser = PersonaServiceHelper.getDualDARUser();
        if (dualDARUser != -1) {
            refreshConfig(
                    this.edmStorageProvider.getMUMContainerOwnerUid(dualDARUser), dualDARUser);
        }
    }
}
