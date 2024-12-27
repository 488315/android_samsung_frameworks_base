package com.android.server.devicepolicy;

import android.app.ActivityTaskManager;
import android.app.admin.AccountTypePolicyKey;
import android.app.admin.BooleanPolicyValue;
import android.app.admin.DevicePolicyCache;
import android.app.admin.DevicePolicyIdentifiers;
import android.app.admin.DevicePolicyManagerInternal;
import android.app.admin.IntegerPolicyValue;
import android.app.admin.IntentFilterPolicyKey;
import android.app.admin.LockTaskPolicy;
import android.app.admin.NoArgsPolicyKey;
import android.app.admin.PackagePermissionPolicyKey;
import android.app.admin.PackagePolicyKey;
import android.app.admin.PolicyKey;
import android.app.admin.UserRestrictionPolicyKey;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.ArraySet;
import android.util.Slog;
import com.android.internal.hidden_from_bootclasspath.com.android.net.thread.platform.flags.Flags;
import com.android.internal.os.BackgroundThread;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.function.QuadFunction;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.am.MARsPolicyManager;
import com.android.server.am.mars.filter.filter.ProtectedPackagesFilter;
import com.android.server.utils.Slogf;
import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class PolicyDefinition {
    public static final PolicyDefinition AUDIT_LOGGING;
    public static final PolicyDefinition CONTENT_PROTECTION;
    public static final PolicyDefinition GENERIC_ACCOUNT_MANAGEMENT_DISABLED;
    public static final PolicyDefinition GENERIC_APPLICATION_HIDDEN;
    public static final PolicyDefinition GENERIC_APPLICATION_RESTRICTIONS;
    public static final PolicyDefinition GENERIC_PACKAGE_UNINSTALL_BLOCKED;
    public static final PolicyDefinition GENERIC_PERSISTENT_PREFERRED_ACTIVITY;
    public static final PolicyDefinition LOCK_TASK;
    public static final PolicyDefinition PACKAGES_SUSPENDED;
    public static final PolicyDefinition PASSWORD_COMPLEXITY;
    public static final PolicyDefinition PERMITTED_INPUT_METHODS;
    public static final PolicyDefinition PERSONAL_APPS_SUSPENDED;
    public static final Map POLICY_DEFINITIONS;
    public static final PolicyDefinition SCREEN_CAPTURE_DISABLED;
    public static final PolicyDefinition SECURITY_LOGGING;
    public static final MostRestrictive TRUE_MORE_RESTRICTIVE;
    public static final PolicyDefinition USB_DATA_SIGNALING;
    public static final PolicyDefinition USER_CONTROLLED_DISABLED_PACKAGES;
    public static final Map USER_RESTRICTION_FLAGS;
    public final QuadFunction mPolicyEnforcerCallback;
    public final int mPolicyFlags;
    public final PolicyKey mPolicyKey;
    public final PolicySerializer mPolicySerializer;
    public final ResolutionMechanism mResolutionMechanism;

    static {
        MostRestrictive mostRestrictive = new MostRestrictive(List.of(new BooleanPolicyValue(false), new BooleanPolicyValue(true)));
        MostRestrictive mostRestrictive2 = new MostRestrictive(List.of(new BooleanPolicyValue(true), new BooleanPolicyValue(false)));
        TRUE_MORE_RESTRICTIVE = mostRestrictive2;
        final int i = 0;
        PolicyDefinition policyDefinition = new PolicyDefinition(new NoArgsPolicyKey("autoTimezone"), mostRestrictive2, 1, new QuadFunction() { // from class: com.android.server.devicepolicy.PolicyDefinition$$ExternalSyntheticLambda0
            public final Object apply(Object obj, Object obj2, Object obj3, Object obj4) {
                final int i2;
                int i3 = 0;
                switch (i) {
                    case 0:
                        String[] strArr = DevicePolicyManagerService.DELEGATIONS;
                        Slogf.w("PolicyEnforcerCallbacks", "Trying to enforce setAutoTimezoneEnabled while flag is off.");
                        return Boolean.TRUE;
                    case 1:
                        ((Integer) obj3).intValue();
                        DevicePolicyManagerInternal devicePolicyManagerInternal = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
                        Boolean bool = Boolean.TRUE;
                        devicePolicyManagerInternal.enforceSecurityLoggingPolicy(bool.equals((Boolean) obj));
                        return bool;
                    case 2:
                        ((Integer) obj3).intValue();
                        DevicePolicyManagerInternal devicePolicyManagerInternal2 = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
                        Boolean bool2 = Boolean.TRUE;
                        devicePolicyManagerInternal2.enforceAuditLoggingPolicy(bool2.equals((Boolean) obj));
                        return bool2;
                    case 3:
                        LockTaskPolicy lockTaskPolicy = (LockTaskPolicy) obj;
                        Context context = (Context) obj2;
                        final int intValue = ((Integer) obj3).intValue();
                        List emptyList = Collections.emptyList();
                        if (lockTaskPolicy != null) {
                            emptyList = List.copyOf(lockTaskPolicy.getPackages());
                            i2 = lockTaskPolicy.getFlags();
                        } else {
                            i2 = 16;
                        }
                        String[] strArr2 = DevicePolicyManagerService.DELEGATIONS;
                        Binder.withCleanCallingIdentity(new DevicePolicyManagerService$$ExternalSyntheticLambda101(intValue, context, emptyList));
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.DevicePolicyManagerService$$ExternalSyntheticLambda204
                            public final void runOrThrow() {
                                try {
                                    ActivityTaskManager.getService().updateLockTaskFeatures(intValue, i2);
                                } catch (RemoteException e) {
                                    Slog.wtf("DevicePolicyManager", "Remote Exception: ", e);
                                }
                            }
                        });
                        return Boolean.TRUE;
                    case 4:
                        Set set = (Set) obj;
                        Context context2 = (Context) obj2;
                        int intValue2 = ((Integer) obj3).intValue();
                        ProtectedPackagesFilter protectedPackagesFilter = ProtectedPackagesFilter.ProtectedPackagesFilterHolder.INSTANCE;
                        List<String> list = set == null ? null : set.stream().toList();
                        protectedPackagesFilter.getClass();
                        Slog.d("ProtectedPackagesFilter", "updateProtectedPackages is called. userId " + intValue2 + " package names : " + list);
                        synchronized (protectedPackagesFilter.mProtectedPackages) {
                            try {
                                if (list == null) {
                                    protectedPackagesFilter.mProtectedPackages.remove(intValue2);
                                } else {
                                    protectedPackagesFilter.mProtectedPackages.put(intValue2, new ArraySet(list));
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        if (list != null) {
                            for (String str : list) {
                                boolean z = MARsPolicyManager.MARs_ENABLE;
                                if (!MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.cancelDisablePolicy(str, intValue2, 0)) {
                                    BootReceiver$$ExternalSyntheticOutline0.m("cancelDisablePolicy failed. package : ", str, "MARsPolicyManager");
                                }
                            }
                        }
                        Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(context2, intValue2, set));
                        return Boolean.TRUE;
                    case 5:
                        Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(((Integer) obj3).intValue(), 2, (PolicyKey) obj4, (ComponentName) obj));
                        return Boolean.TRUE;
                    case 6:
                        return Boolean.valueOf(Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda3((PolicyKey) obj4, (Boolean) obj, ((Integer) obj3).intValue(), 1))));
                    case 7:
                        final Context context3 = (Context) obj2;
                        final Integer num = (Integer) obj3;
                        final PolicyKey policyKey = (PolicyKey) obj4;
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda4
                            public final void runOrThrow() {
                                PackagePolicyKey packagePolicyKey = policyKey;
                                Context context4 = context3;
                                Integer num2 = num;
                                String packageName = packagePolicyKey.getPackageName();
                                Objects.requireNonNull(packageName);
                                Intent intent = new Intent("android.intent.action.APPLICATION_RESTRICTIONS_CHANGED");
                                intent.setPackage(packageName);
                                intent.addFlags(1073741824);
                                context4.sendBroadcastAsUser(intent, UserHandle.of(num2.intValue()));
                            }
                        });
                        return Boolean.TRUE;
                    case 8:
                        return Boolean.TRUE;
                    case 9:
                        return Boolean.valueOf(Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda3((PolicyKey) obj4, ((Integer) obj3).intValue(), (Boolean) obj))));
                    case 10:
                        return Boolean.TRUE;
                    case 11:
                        return Boolean.valueOf(Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda3((PolicyKey) obj4, (Boolean) obj, ((Integer) obj3).intValue(), i3))));
                    case 12:
                        return Boolean.TRUE;
                    case 13:
                        return Boolean.TRUE;
                    case 14:
                        final Boolean bool3 = (Boolean) obj;
                        final int intValue3 = ((Integer) obj3).intValue();
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda1
                            public final void runOrThrow() {
                                int i4 = intValue3;
                                Boolean bool4 = bool3;
                                DevicePolicyCache devicePolicyCache = DevicePolicyCache.getInstance();
                                if (devicePolicyCache instanceof DevicePolicyCacheImpl) {
                                    DevicePolicyCacheImpl devicePolicyCacheImpl = (DevicePolicyCacheImpl) devicePolicyCache;
                                    boolean z2 = bool4 != null && bool4.booleanValue();
                                    synchronized (devicePolicyCacheImpl.mLock) {
                                        try {
                                            if (z2) {
                                                ((HashSet) devicePolicyCacheImpl.mScreenCaptureDisallowedUsers).add(Integer.valueOf(i4));
                                            } else {
                                                ((HashSet) devicePolicyCacheImpl.mScreenCaptureDisallowedUsers).remove(Integer.valueOf(i4));
                                            }
                                        } catch (Throwable th2) {
                                            throw th2;
                                        }
                                    }
                                    BackgroundThread.getHandler().post(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda10());
                                }
                            }
                        });
                        return Boolean.TRUE;
                    case 15:
                        Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(((Integer) obj3).intValue(), i3, (Boolean) obj, (Context) obj2));
                        return Boolean.TRUE;
                    case 16:
                        final Boolean bool4 = (Boolean) obj;
                        final Context context4 = (Context) obj2;
                        Boolean bool5 = (Boolean) Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda2
                            public final Object getOrThrow() {
                                Context context5 = context4;
                                Boolean bool6 = bool4;
                                Objects.requireNonNull(context5);
                                DevicePolicyManagerService.updateUsbDataSignal(context5, bool6 == null || bool6.booleanValue());
                                return Boolean.TRUE;
                            }
                        });
                        bool5.getClass();
                        return bool5;
                    case 17:
                        final Integer num2 = (Integer) obj;
                        final Integer num3 = (Integer) obj3;
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda6
                            public final void runOrThrow() {
                                Integer num4 = num3;
                                Integer num5 = num2;
                                DevicePolicyCache devicePolicyCache = DevicePolicyCache.getInstance();
                                if (devicePolicyCache instanceof DevicePolicyCacheImpl) {
                                    ((DevicePolicyCacheImpl) devicePolicyCache).setContentProtectionPolicy(num4.intValue(), num5);
                                }
                            }
                        });
                        return Boolean.TRUE;
                    default:
                        ((Integer) obj3).intValue();
                        String[] strArr3 = DevicePolicyManagerService.DELEGATIONS;
                        Slogf.w("PolicyEnforcerCallbacks", "Trying to enforce setPermissionGrantState while flag is off.");
                        return Boolean.TRUE;
                }
            }
        }, new BooleanPolicySerializer());
        final int i2 = 18;
        PolicyDefinition policyDefinition2 = new PolicyDefinition(new PackagePermissionPolicyKey("permissionGrant"), new MostRestrictive(List.of(new IntegerPolicyValue(2), new IntegerPolicyValue(1), new IntegerPolicyValue(0))), 2, new QuadFunction() { // from class: com.android.server.devicepolicy.PolicyDefinition$$ExternalSyntheticLambda0
            public final Object apply(Object obj, Object obj2, Object obj3, Object obj4) {
                final int i22;
                int i3 = 0;
                switch (i2) {
                    case 0:
                        String[] strArr = DevicePolicyManagerService.DELEGATIONS;
                        Slogf.w("PolicyEnforcerCallbacks", "Trying to enforce setAutoTimezoneEnabled while flag is off.");
                        return Boolean.TRUE;
                    case 1:
                        ((Integer) obj3).intValue();
                        DevicePolicyManagerInternal devicePolicyManagerInternal = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
                        Boolean bool = Boolean.TRUE;
                        devicePolicyManagerInternal.enforceSecurityLoggingPolicy(bool.equals((Boolean) obj));
                        return bool;
                    case 2:
                        ((Integer) obj3).intValue();
                        DevicePolicyManagerInternal devicePolicyManagerInternal2 = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
                        Boolean bool2 = Boolean.TRUE;
                        devicePolicyManagerInternal2.enforceAuditLoggingPolicy(bool2.equals((Boolean) obj));
                        return bool2;
                    case 3:
                        LockTaskPolicy lockTaskPolicy = (LockTaskPolicy) obj;
                        Context context = (Context) obj2;
                        final int intValue = ((Integer) obj3).intValue();
                        List emptyList = Collections.emptyList();
                        if (lockTaskPolicy != null) {
                            emptyList = List.copyOf(lockTaskPolicy.getPackages());
                            i22 = lockTaskPolicy.getFlags();
                        } else {
                            i22 = 16;
                        }
                        String[] strArr2 = DevicePolicyManagerService.DELEGATIONS;
                        Binder.withCleanCallingIdentity(new DevicePolicyManagerService$$ExternalSyntheticLambda101(intValue, context, emptyList));
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.DevicePolicyManagerService$$ExternalSyntheticLambda204
                            public final void runOrThrow() {
                                try {
                                    ActivityTaskManager.getService().updateLockTaskFeatures(intValue, i22);
                                } catch (RemoteException e) {
                                    Slog.wtf("DevicePolicyManager", "Remote Exception: ", e);
                                }
                            }
                        });
                        return Boolean.TRUE;
                    case 4:
                        Set set = (Set) obj;
                        Context context2 = (Context) obj2;
                        int intValue2 = ((Integer) obj3).intValue();
                        ProtectedPackagesFilter protectedPackagesFilter = ProtectedPackagesFilter.ProtectedPackagesFilterHolder.INSTANCE;
                        List<String> list = set == null ? null : set.stream().toList();
                        protectedPackagesFilter.getClass();
                        Slog.d("ProtectedPackagesFilter", "updateProtectedPackages is called. userId " + intValue2 + " package names : " + list);
                        synchronized (protectedPackagesFilter.mProtectedPackages) {
                            try {
                                if (list == null) {
                                    protectedPackagesFilter.mProtectedPackages.remove(intValue2);
                                } else {
                                    protectedPackagesFilter.mProtectedPackages.put(intValue2, new ArraySet(list));
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        if (list != null) {
                            for (String str : list) {
                                boolean z = MARsPolicyManager.MARs_ENABLE;
                                if (!MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.cancelDisablePolicy(str, intValue2, 0)) {
                                    BootReceiver$$ExternalSyntheticOutline0.m("cancelDisablePolicy failed. package : ", str, "MARsPolicyManager");
                                }
                            }
                        }
                        Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(context2, intValue2, set));
                        return Boolean.TRUE;
                    case 5:
                        Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(((Integer) obj3).intValue(), 2, (PolicyKey) obj4, (ComponentName) obj));
                        return Boolean.TRUE;
                    case 6:
                        return Boolean.valueOf(Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda3((PolicyKey) obj4, (Boolean) obj, ((Integer) obj3).intValue(), 1))));
                    case 7:
                        final Context context3 = (Context) obj2;
                        final Integer num = (Integer) obj3;
                        final PolicyKey policyKey = (PolicyKey) obj4;
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda4
                            public final void runOrThrow() {
                                PackagePolicyKey packagePolicyKey = policyKey;
                                Context context4 = context3;
                                Integer num2 = num;
                                String packageName = packagePolicyKey.getPackageName();
                                Objects.requireNonNull(packageName);
                                Intent intent = new Intent("android.intent.action.APPLICATION_RESTRICTIONS_CHANGED");
                                intent.setPackage(packageName);
                                intent.addFlags(1073741824);
                                context4.sendBroadcastAsUser(intent, UserHandle.of(num2.intValue()));
                            }
                        });
                        return Boolean.TRUE;
                    case 8:
                        return Boolean.TRUE;
                    case 9:
                        return Boolean.valueOf(Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda3((PolicyKey) obj4, ((Integer) obj3).intValue(), (Boolean) obj))));
                    case 10:
                        return Boolean.TRUE;
                    case 11:
                        return Boolean.valueOf(Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda3((PolicyKey) obj4, (Boolean) obj, ((Integer) obj3).intValue(), i3))));
                    case 12:
                        return Boolean.TRUE;
                    case 13:
                        return Boolean.TRUE;
                    case 14:
                        final Boolean bool3 = (Boolean) obj;
                        final int intValue3 = ((Integer) obj3).intValue();
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda1
                            public final void runOrThrow() {
                                int i4 = intValue3;
                                Boolean bool4 = bool3;
                                DevicePolicyCache devicePolicyCache = DevicePolicyCache.getInstance();
                                if (devicePolicyCache instanceof DevicePolicyCacheImpl) {
                                    DevicePolicyCacheImpl devicePolicyCacheImpl = (DevicePolicyCacheImpl) devicePolicyCache;
                                    boolean z2 = bool4 != null && bool4.booleanValue();
                                    synchronized (devicePolicyCacheImpl.mLock) {
                                        try {
                                            if (z2) {
                                                ((HashSet) devicePolicyCacheImpl.mScreenCaptureDisallowedUsers).add(Integer.valueOf(i4));
                                            } else {
                                                ((HashSet) devicePolicyCacheImpl.mScreenCaptureDisallowedUsers).remove(Integer.valueOf(i4));
                                            }
                                        } catch (Throwable th2) {
                                            throw th2;
                                        }
                                    }
                                    BackgroundThread.getHandler().post(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda10());
                                }
                            }
                        });
                        return Boolean.TRUE;
                    case 15:
                        Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(((Integer) obj3).intValue(), i3, (Boolean) obj, (Context) obj2));
                        return Boolean.TRUE;
                    case 16:
                        final Boolean bool4 = (Boolean) obj;
                        final Context context4 = (Context) obj2;
                        Boolean bool5 = (Boolean) Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda2
                            public final Object getOrThrow() {
                                Context context5 = context4;
                                Boolean bool6 = bool4;
                                Objects.requireNonNull(context5);
                                DevicePolicyManagerService.updateUsbDataSignal(context5, bool6 == null || bool6.booleanValue());
                                return Boolean.TRUE;
                            }
                        });
                        bool5.getClass();
                        return bool5;
                    case 17:
                        final Integer num2 = (Integer) obj;
                        final Integer num3 = (Integer) obj3;
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda6
                            public final void runOrThrow() {
                                Integer num4 = num3;
                                Integer num5 = num2;
                                DevicePolicyCache devicePolicyCache = DevicePolicyCache.getInstance();
                                if (devicePolicyCache instanceof DevicePolicyCacheImpl) {
                                    ((DevicePolicyCacheImpl) devicePolicyCache).setContentProtectionPolicy(num4.intValue(), num5);
                                }
                            }
                        });
                        return Boolean.TRUE;
                    default:
                        ((Integer) obj3).intValue();
                        String[] strArr3 = DevicePolicyManagerService.DELEGATIONS;
                        Slogf.w("PolicyEnforcerCallbacks", "Trying to enforce setPermissionGrantState while flag is off.");
                        return Boolean.TRUE;
                }
            }
        }, new IntegerPolicySerializer());
        final int i3 = 1;
        PolicyDefinition policyDefinition3 = new PolicyDefinition(new NoArgsPolicyKey("securityLogging"), mostRestrictive2, 1, new QuadFunction() { // from class: com.android.server.devicepolicy.PolicyDefinition$$ExternalSyntheticLambda0
            public final Object apply(Object obj, Object obj2, Object obj3, Object obj4) {
                final int i22;
                int i32 = 0;
                switch (i3) {
                    case 0:
                        String[] strArr = DevicePolicyManagerService.DELEGATIONS;
                        Slogf.w("PolicyEnforcerCallbacks", "Trying to enforce setAutoTimezoneEnabled while flag is off.");
                        return Boolean.TRUE;
                    case 1:
                        ((Integer) obj3).intValue();
                        DevicePolicyManagerInternal devicePolicyManagerInternal = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
                        Boolean bool = Boolean.TRUE;
                        devicePolicyManagerInternal.enforceSecurityLoggingPolicy(bool.equals((Boolean) obj));
                        return bool;
                    case 2:
                        ((Integer) obj3).intValue();
                        DevicePolicyManagerInternal devicePolicyManagerInternal2 = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
                        Boolean bool2 = Boolean.TRUE;
                        devicePolicyManagerInternal2.enforceAuditLoggingPolicy(bool2.equals((Boolean) obj));
                        return bool2;
                    case 3:
                        LockTaskPolicy lockTaskPolicy = (LockTaskPolicy) obj;
                        Context context = (Context) obj2;
                        final int intValue = ((Integer) obj3).intValue();
                        List emptyList = Collections.emptyList();
                        if (lockTaskPolicy != null) {
                            emptyList = List.copyOf(lockTaskPolicy.getPackages());
                            i22 = lockTaskPolicy.getFlags();
                        } else {
                            i22 = 16;
                        }
                        String[] strArr2 = DevicePolicyManagerService.DELEGATIONS;
                        Binder.withCleanCallingIdentity(new DevicePolicyManagerService$$ExternalSyntheticLambda101(intValue, context, emptyList));
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.DevicePolicyManagerService$$ExternalSyntheticLambda204
                            public final void runOrThrow() {
                                try {
                                    ActivityTaskManager.getService().updateLockTaskFeatures(intValue, i22);
                                } catch (RemoteException e) {
                                    Slog.wtf("DevicePolicyManager", "Remote Exception: ", e);
                                }
                            }
                        });
                        return Boolean.TRUE;
                    case 4:
                        Set set = (Set) obj;
                        Context context2 = (Context) obj2;
                        int intValue2 = ((Integer) obj3).intValue();
                        ProtectedPackagesFilter protectedPackagesFilter = ProtectedPackagesFilter.ProtectedPackagesFilterHolder.INSTANCE;
                        List<String> list = set == null ? null : set.stream().toList();
                        protectedPackagesFilter.getClass();
                        Slog.d("ProtectedPackagesFilter", "updateProtectedPackages is called. userId " + intValue2 + " package names : " + list);
                        synchronized (protectedPackagesFilter.mProtectedPackages) {
                            try {
                                if (list == null) {
                                    protectedPackagesFilter.mProtectedPackages.remove(intValue2);
                                } else {
                                    protectedPackagesFilter.mProtectedPackages.put(intValue2, new ArraySet(list));
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        if (list != null) {
                            for (String str : list) {
                                boolean z = MARsPolicyManager.MARs_ENABLE;
                                if (!MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.cancelDisablePolicy(str, intValue2, 0)) {
                                    BootReceiver$$ExternalSyntheticOutline0.m("cancelDisablePolicy failed. package : ", str, "MARsPolicyManager");
                                }
                            }
                        }
                        Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(context2, intValue2, set));
                        return Boolean.TRUE;
                    case 5:
                        Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(((Integer) obj3).intValue(), 2, (PolicyKey) obj4, (ComponentName) obj));
                        return Boolean.TRUE;
                    case 6:
                        return Boolean.valueOf(Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda3((PolicyKey) obj4, (Boolean) obj, ((Integer) obj3).intValue(), 1))));
                    case 7:
                        final Context context3 = (Context) obj2;
                        final Integer num = (Integer) obj3;
                        final PolicyKey policyKey = (PolicyKey) obj4;
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda4
                            public final void runOrThrow() {
                                PackagePolicyKey packagePolicyKey = policyKey;
                                Context context4 = context3;
                                Integer num2 = num;
                                String packageName = packagePolicyKey.getPackageName();
                                Objects.requireNonNull(packageName);
                                Intent intent = new Intent("android.intent.action.APPLICATION_RESTRICTIONS_CHANGED");
                                intent.setPackage(packageName);
                                intent.addFlags(1073741824);
                                context4.sendBroadcastAsUser(intent, UserHandle.of(num2.intValue()));
                            }
                        });
                        return Boolean.TRUE;
                    case 8:
                        return Boolean.TRUE;
                    case 9:
                        return Boolean.valueOf(Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda3((PolicyKey) obj4, ((Integer) obj3).intValue(), (Boolean) obj))));
                    case 10:
                        return Boolean.TRUE;
                    case 11:
                        return Boolean.valueOf(Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda3((PolicyKey) obj4, (Boolean) obj, ((Integer) obj3).intValue(), i32))));
                    case 12:
                        return Boolean.TRUE;
                    case 13:
                        return Boolean.TRUE;
                    case 14:
                        final Boolean bool3 = (Boolean) obj;
                        final int intValue3 = ((Integer) obj3).intValue();
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda1
                            public final void runOrThrow() {
                                int i4 = intValue3;
                                Boolean bool4 = bool3;
                                DevicePolicyCache devicePolicyCache = DevicePolicyCache.getInstance();
                                if (devicePolicyCache instanceof DevicePolicyCacheImpl) {
                                    DevicePolicyCacheImpl devicePolicyCacheImpl = (DevicePolicyCacheImpl) devicePolicyCache;
                                    boolean z2 = bool4 != null && bool4.booleanValue();
                                    synchronized (devicePolicyCacheImpl.mLock) {
                                        try {
                                            if (z2) {
                                                ((HashSet) devicePolicyCacheImpl.mScreenCaptureDisallowedUsers).add(Integer.valueOf(i4));
                                            } else {
                                                ((HashSet) devicePolicyCacheImpl.mScreenCaptureDisallowedUsers).remove(Integer.valueOf(i4));
                                            }
                                        } catch (Throwable th2) {
                                            throw th2;
                                        }
                                    }
                                    BackgroundThread.getHandler().post(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda10());
                                }
                            }
                        });
                        return Boolean.TRUE;
                    case 15:
                        Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(((Integer) obj3).intValue(), i32, (Boolean) obj, (Context) obj2));
                        return Boolean.TRUE;
                    case 16:
                        final Boolean bool4 = (Boolean) obj;
                        final Context context4 = (Context) obj2;
                        Boolean bool5 = (Boolean) Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda2
                            public final Object getOrThrow() {
                                Context context5 = context4;
                                Boolean bool6 = bool4;
                                Objects.requireNonNull(context5);
                                DevicePolicyManagerService.updateUsbDataSignal(context5, bool6 == null || bool6.booleanValue());
                                return Boolean.TRUE;
                            }
                        });
                        bool5.getClass();
                        return bool5;
                    case 17:
                        final Integer num2 = (Integer) obj;
                        final Integer num3 = (Integer) obj3;
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda6
                            public final void runOrThrow() {
                                Integer num4 = num3;
                                Integer num5 = num2;
                                DevicePolicyCache devicePolicyCache = DevicePolicyCache.getInstance();
                                if (devicePolicyCache instanceof DevicePolicyCacheImpl) {
                                    ((DevicePolicyCacheImpl) devicePolicyCache).setContentProtectionPolicy(num4.intValue(), num5);
                                }
                            }
                        });
                        return Boolean.TRUE;
                    default:
                        ((Integer) obj3).intValue();
                        String[] strArr3 = DevicePolicyManagerService.DELEGATIONS;
                        Slogf.w("PolicyEnforcerCallbacks", "Trying to enforce setPermissionGrantState while flag is off.");
                        return Boolean.TRUE;
                }
            }
        }, new BooleanPolicySerializer());
        SECURITY_LOGGING = policyDefinition3;
        final int i4 = 2;
        PolicyDefinition policyDefinition4 = new PolicyDefinition(new NoArgsPolicyKey("auditLogging"), mostRestrictive2, 1, new QuadFunction() { // from class: com.android.server.devicepolicy.PolicyDefinition$$ExternalSyntheticLambda0
            public final Object apply(Object obj, Object obj2, Object obj3, Object obj4) {
                final int i22;
                int i32 = 0;
                switch (i4) {
                    case 0:
                        String[] strArr = DevicePolicyManagerService.DELEGATIONS;
                        Slogf.w("PolicyEnforcerCallbacks", "Trying to enforce setAutoTimezoneEnabled while flag is off.");
                        return Boolean.TRUE;
                    case 1:
                        ((Integer) obj3).intValue();
                        DevicePolicyManagerInternal devicePolicyManagerInternal = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
                        Boolean bool = Boolean.TRUE;
                        devicePolicyManagerInternal.enforceSecurityLoggingPolicy(bool.equals((Boolean) obj));
                        return bool;
                    case 2:
                        ((Integer) obj3).intValue();
                        DevicePolicyManagerInternal devicePolicyManagerInternal2 = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
                        Boolean bool2 = Boolean.TRUE;
                        devicePolicyManagerInternal2.enforceAuditLoggingPolicy(bool2.equals((Boolean) obj));
                        return bool2;
                    case 3:
                        LockTaskPolicy lockTaskPolicy = (LockTaskPolicy) obj;
                        Context context = (Context) obj2;
                        final int intValue = ((Integer) obj3).intValue();
                        List emptyList = Collections.emptyList();
                        if (lockTaskPolicy != null) {
                            emptyList = List.copyOf(lockTaskPolicy.getPackages());
                            i22 = lockTaskPolicy.getFlags();
                        } else {
                            i22 = 16;
                        }
                        String[] strArr2 = DevicePolicyManagerService.DELEGATIONS;
                        Binder.withCleanCallingIdentity(new DevicePolicyManagerService$$ExternalSyntheticLambda101(intValue, context, emptyList));
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.DevicePolicyManagerService$$ExternalSyntheticLambda204
                            public final void runOrThrow() {
                                try {
                                    ActivityTaskManager.getService().updateLockTaskFeatures(intValue, i22);
                                } catch (RemoteException e) {
                                    Slog.wtf("DevicePolicyManager", "Remote Exception: ", e);
                                }
                            }
                        });
                        return Boolean.TRUE;
                    case 4:
                        Set set = (Set) obj;
                        Context context2 = (Context) obj2;
                        int intValue2 = ((Integer) obj3).intValue();
                        ProtectedPackagesFilter protectedPackagesFilter = ProtectedPackagesFilter.ProtectedPackagesFilterHolder.INSTANCE;
                        List<String> list = set == null ? null : set.stream().toList();
                        protectedPackagesFilter.getClass();
                        Slog.d("ProtectedPackagesFilter", "updateProtectedPackages is called. userId " + intValue2 + " package names : " + list);
                        synchronized (protectedPackagesFilter.mProtectedPackages) {
                            try {
                                if (list == null) {
                                    protectedPackagesFilter.mProtectedPackages.remove(intValue2);
                                } else {
                                    protectedPackagesFilter.mProtectedPackages.put(intValue2, new ArraySet(list));
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        if (list != null) {
                            for (String str : list) {
                                boolean z = MARsPolicyManager.MARs_ENABLE;
                                if (!MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.cancelDisablePolicy(str, intValue2, 0)) {
                                    BootReceiver$$ExternalSyntheticOutline0.m("cancelDisablePolicy failed. package : ", str, "MARsPolicyManager");
                                }
                            }
                        }
                        Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(context2, intValue2, set));
                        return Boolean.TRUE;
                    case 5:
                        Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(((Integer) obj3).intValue(), 2, (PolicyKey) obj4, (ComponentName) obj));
                        return Boolean.TRUE;
                    case 6:
                        return Boolean.valueOf(Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda3((PolicyKey) obj4, (Boolean) obj, ((Integer) obj3).intValue(), 1))));
                    case 7:
                        final Context context3 = (Context) obj2;
                        final Integer num = (Integer) obj3;
                        final PolicyKey policyKey = (PolicyKey) obj4;
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda4
                            public final void runOrThrow() {
                                PackagePolicyKey packagePolicyKey = policyKey;
                                Context context4 = context3;
                                Integer num2 = num;
                                String packageName = packagePolicyKey.getPackageName();
                                Objects.requireNonNull(packageName);
                                Intent intent = new Intent("android.intent.action.APPLICATION_RESTRICTIONS_CHANGED");
                                intent.setPackage(packageName);
                                intent.addFlags(1073741824);
                                context4.sendBroadcastAsUser(intent, UserHandle.of(num2.intValue()));
                            }
                        });
                        return Boolean.TRUE;
                    case 8:
                        return Boolean.TRUE;
                    case 9:
                        return Boolean.valueOf(Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda3((PolicyKey) obj4, ((Integer) obj3).intValue(), (Boolean) obj))));
                    case 10:
                        return Boolean.TRUE;
                    case 11:
                        return Boolean.valueOf(Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda3((PolicyKey) obj4, (Boolean) obj, ((Integer) obj3).intValue(), i32))));
                    case 12:
                        return Boolean.TRUE;
                    case 13:
                        return Boolean.TRUE;
                    case 14:
                        final Boolean bool3 = (Boolean) obj;
                        final int intValue3 = ((Integer) obj3).intValue();
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda1
                            public final void runOrThrow() {
                                int i42 = intValue3;
                                Boolean bool4 = bool3;
                                DevicePolicyCache devicePolicyCache = DevicePolicyCache.getInstance();
                                if (devicePolicyCache instanceof DevicePolicyCacheImpl) {
                                    DevicePolicyCacheImpl devicePolicyCacheImpl = (DevicePolicyCacheImpl) devicePolicyCache;
                                    boolean z2 = bool4 != null && bool4.booleanValue();
                                    synchronized (devicePolicyCacheImpl.mLock) {
                                        try {
                                            if (z2) {
                                                ((HashSet) devicePolicyCacheImpl.mScreenCaptureDisallowedUsers).add(Integer.valueOf(i42));
                                            } else {
                                                ((HashSet) devicePolicyCacheImpl.mScreenCaptureDisallowedUsers).remove(Integer.valueOf(i42));
                                            }
                                        } catch (Throwable th2) {
                                            throw th2;
                                        }
                                    }
                                    BackgroundThread.getHandler().post(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda10());
                                }
                            }
                        });
                        return Boolean.TRUE;
                    case 15:
                        Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(((Integer) obj3).intValue(), i32, (Boolean) obj, (Context) obj2));
                        return Boolean.TRUE;
                    case 16:
                        final Boolean bool4 = (Boolean) obj;
                        final Context context4 = (Context) obj2;
                        Boolean bool5 = (Boolean) Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda2
                            public final Object getOrThrow() {
                                Context context5 = context4;
                                Boolean bool6 = bool4;
                                Objects.requireNonNull(context5);
                                DevicePolicyManagerService.updateUsbDataSignal(context5, bool6 == null || bool6.booleanValue());
                                return Boolean.TRUE;
                            }
                        });
                        bool5.getClass();
                        return bool5;
                    case 17:
                        final Integer num2 = (Integer) obj;
                        final Integer num3 = (Integer) obj3;
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda6
                            public final void runOrThrow() {
                                Integer num4 = num3;
                                Integer num5 = num2;
                                DevicePolicyCache devicePolicyCache = DevicePolicyCache.getInstance();
                                if (devicePolicyCache instanceof DevicePolicyCacheImpl) {
                                    ((DevicePolicyCacheImpl) devicePolicyCache).setContentProtectionPolicy(num4.intValue(), num5);
                                }
                            }
                        });
                        return Boolean.TRUE;
                    default:
                        ((Integer) obj3).intValue();
                        String[] strArr3 = DevicePolicyManagerService.DELEGATIONS;
                        Slogf.w("PolicyEnforcerCallbacks", "Trying to enforce setPermissionGrantState while flag is off.");
                        return Boolean.TRUE;
                }
            }
        }, new BooleanPolicySerializer());
        AUDIT_LOGGING = policyDefinition4;
        final int i5 = 3;
        PolicyDefinition policyDefinition5 = new PolicyDefinition(new NoArgsPolicyKey("lockTask"), new TopPriority(List.of("role:android.app.role.SYSTEM_FINANCED_DEVICE_CONTROLLER", "enterprise")), 2, new QuadFunction() { // from class: com.android.server.devicepolicy.PolicyDefinition$$ExternalSyntheticLambda0
            public final Object apply(Object obj, Object obj2, Object obj3, Object obj4) {
                final int i22;
                int i32 = 0;
                switch (i5) {
                    case 0:
                        String[] strArr = DevicePolicyManagerService.DELEGATIONS;
                        Slogf.w("PolicyEnforcerCallbacks", "Trying to enforce setAutoTimezoneEnabled while flag is off.");
                        return Boolean.TRUE;
                    case 1:
                        ((Integer) obj3).intValue();
                        DevicePolicyManagerInternal devicePolicyManagerInternal = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
                        Boolean bool = Boolean.TRUE;
                        devicePolicyManagerInternal.enforceSecurityLoggingPolicy(bool.equals((Boolean) obj));
                        return bool;
                    case 2:
                        ((Integer) obj3).intValue();
                        DevicePolicyManagerInternal devicePolicyManagerInternal2 = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
                        Boolean bool2 = Boolean.TRUE;
                        devicePolicyManagerInternal2.enforceAuditLoggingPolicy(bool2.equals((Boolean) obj));
                        return bool2;
                    case 3:
                        LockTaskPolicy lockTaskPolicy = (LockTaskPolicy) obj;
                        Context context = (Context) obj2;
                        final int intValue = ((Integer) obj3).intValue();
                        List emptyList = Collections.emptyList();
                        if (lockTaskPolicy != null) {
                            emptyList = List.copyOf(lockTaskPolicy.getPackages());
                            i22 = lockTaskPolicy.getFlags();
                        } else {
                            i22 = 16;
                        }
                        String[] strArr2 = DevicePolicyManagerService.DELEGATIONS;
                        Binder.withCleanCallingIdentity(new DevicePolicyManagerService$$ExternalSyntheticLambda101(intValue, context, emptyList));
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.DevicePolicyManagerService$$ExternalSyntheticLambda204
                            public final void runOrThrow() {
                                try {
                                    ActivityTaskManager.getService().updateLockTaskFeatures(intValue, i22);
                                } catch (RemoteException e) {
                                    Slog.wtf("DevicePolicyManager", "Remote Exception: ", e);
                                }
                            }
                        });
                        return Boolean.TRUE;
                    case 4:
                        Set set = (Set) obj;
                        Context context2 = (Context) obj2;
                        int intValue2 = ((Integer) obj3).intValue();
                        ProtectedPackagesFilter protectedPackagesFilter = ProtectedPackagesFilter.ProtectedPackagesFilterHolder.INSTANCE;
                        List<String> list = set == null ? null : set.stream().toList();
                        protectedPackagesFilter.getClass();
                        Slog.d("ProtectedPackagesFilter", "updateProtectedPackages is called. userId " + intValue2 + " package names : " + list);
                        synchronized (protectedPackagesFilter.mProtectedPackages) {
                            try {
                                if (list == null) {
                                    protectedPackagesFilter.mProtectedPackages.remove(intValue2);
                                } else {
                                    protectedPackagesFilter.mProtectedPackages.put(intValue2, new ArraySet(list));
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        if (list != null) {
                            for (String str : list) {
                                boolean z = MARsPolicyManager.MARs_ENABLE;
                                if (!MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.cancelDisablePolicy(str, intValue2, 0)) {
                                    BootReceiver$$ExternalSyntheticOutline0.m("cancelDisablePolicy failed. package : ", str, "MARsPolicyManager");
                                }
                            }
                        }
                        Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(context2, intValue2, set));
                        return Boolean.TRUE;
                    case 5:
                        Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(((Integer) obj3).intValue(), 2, (PolicyKey) obj4, (ComponentName) obj));
                        return Boolean.TRUE;
                    case 6:
                        return Boolean.valueOf(Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda3((PolicyKey) obj4, (Boolean) obj, ((Integer) obj3).intValue(), 1))));
                    case 7:
                        final Context context3 = (Context) obj2;
                        final Integer num = (Integer) obj3;
                        final PolicyKey policyKey = (PolicyKey) obj4;
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda4
                            public final void runOrThrow() {
                                PackagePolicyKey packagePolicyKey = policyKey;
                                Context context4 = context3;
                                Integer num2 = num;
                                String packageName = packagePolicyKey.getPackageName();
                                Objects.requireNonNull(packageName);
                                Intent intent = new Intent("android.intent.action.APPLICATION_RESTRICTIONS_CHANGED");
                                intent.setPackage(packageName);
                                intent.addFlags(1073741824);
                                context4.sendBroadcastAsUser(intent, UserHandle.of(num2.intValue()));
                            }
                        });
                        return Boolean.TRUE;
                    case 8:
                        return Boolean.TRUE;
                    case 9:
                        return Boolean.valueOf(Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda3((PolicyKey) obj4, ((Integer) obj3).intValue(), (Boolean) obj))));
                    case 10:
                        return Boolean.TRUE;
                    case 11:
                        return Boolean.valueOf(Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda3((PolicyKey) obj4, (Boolean) obj, ((Integer) obj3).intValue(), i32))));
                    case 12:
                        return Boolean.TRUE;
                    case 13:
                        return Boolean.TRUE;
                    case 14:
                        final Boolean bool3 = (Boolean) obj;
                        final int intValue3 = ((Integer) obj3).intValue();
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda1
                            public final void runOrThrow() {
                                int i42 = intValue3;
                                Boolean bool4 = bool3;
                                DevicePolicyCache devicePolicyCache = DevicePolicyCache.getInstance();
                                if (devicePolicyCache instanceof DevicePolicyCacheImpl) {
                                    DevicePolicyCacheImpl devicePolicyCacheImpl = (DevicePolicyCacheImpl) devicePolicyCache;
                                    boolean z2 = bool4 != null && bool4.booleanValue();
                                    synchronized (devicePolicyCacheImpl.mLock) {
                                        try {
                                            if (z2) {
                                                ((HashSet) devicePolicyCacheImpl.mScreenCaptureDisallowedUsers).add(Integer.valueOf(i42));
                                            } else {
                                                ((HashSet) devicePolicyCacheImpl.mScreenCaptureDisallowedUsers).remove(Integer.valueOf(i42));
                                            }
                                        } catch (Throwable th2) {
                                            throw th2;
                                        }
                                    }
                                    BackgroundThread.getHandler().post(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda10());
                                }
                            }
                        });
                        return Boolean.TRUE;
                    case 15:
                        Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(((Integer) obj3).intValue(), i32, (Boolean) obj, (Context) obj2));
                        return Boolean.TRUE;
                    case 16:
                        final Boolean bool4 = (Boolean) obj;
                        final Context context4 = (Context) obj2;
                        Boolean bool5 = (Boolean) Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda2
                            public final Object getOrThrow() {
                                Context context5 = context4;
                                Boolean bool6 = bool4;
                                Objects.requireNonNull(context5);
                                DevicePolicyManagerService.updateUsbDataSignal(context5, bool6 == null || bool6.booleanValue());
                                return Boolean.TRUE;
                            }
                        });
                        bool5.getClass();
                        return bool5;
                    case 17:
                        final Integer num2 = (Integer) obj;
                        final Integer num3 = (Integer) obj3;
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda6
                            public final void runOrThrow() {
                                Integer num4 = num3;
                                Integer num5 = num2;
                                DevicePolicyCache devicePolicyCache = DevicePolicyCache.getInstance();
                                if (devicePolicyCache instanceof DevicePolicyCacheImpl) {
                                    ((DevicePolicyCacheImpl) devicePolicyCache).setContentProtectionPolicy(num4.intValue(), num5);
                                }
                            }
                        });
                        return Boolean.TRUE;
                    default:
                        ((Integer) obj3).intValue();
                        String[] strArr3 = DevicePolicyManagerService.DELEGATIONS;
                        Slogf.w("PolicyEnforcerCallbacks", "Trying to enforce setPermissionGrantState while flag is off.");
                        return Boolean.TRUE;
                }
            }
        }, new LockTaskPolicySerializer());
        LOCK_TASK = policyDefinition5;
        final int i6 = 4;
        PolicyDefinition policyDefinition6 = new PolicyDefinition(new NoArgsPolicyKey("userControlDisabledPackages"), new PackageSetUnion(), 0, new QuadFunction() { // from class: com.android.server.devicepolicy.PolicyDefinition$$ExternalSyntheticLambda0
            public final Object apply(Object obj, Object obj2, Object obj3, Object obj4) {
                final int i22;
                int i32 = 0;
                switch (i6) {
                    case 0:
                        String[] strArr = DevicePolicyManagerService.DELEGATIONS;
                        Slogf.w("PolicyEnforcerCallbacks", "Trying to enforce setAutoTimezoneEnabled while flag is off.");
                        return Boolean.TRUE;
                    case 1:
                        ((Integer) obj3).intValue();
                        DevicePolicyManagerInternal devicePolicyManagerInternal = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
                        Boolean bool = Boolean.TRUE;
                        devicePolicyManagerInternal.enforceSecurityLoggingPolicy(bool.equals((Boolean) obj));
                        return bool;
                    case 2:
                        ((Integer) obj3).intValue();
                        DevicePolicyManagerInternal devicePolicyManagerInternal2 = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
                        Boolean bool2 = Boolean.TRUE;
                        devicePolicyManagerInternal2.enforceAuditLoggingPolicy(bool2.equals((Boolean) obj));
                        return bool2;
                    case 3:
                        LockTaskPolicy lockTaskPolicy = (LockTaskPolicy) obj;
                        Context context = (Context) obj2;
                        final int intValue = ((Integer) obj3).intValue();
                        List emptyList = Collections.emptyList();
                        if (lockTaskPolicy != null) {
                            emptyList = List.copyOf(lockTaskPolicy.getPackages());
                            i22 = lockTaskPolicy.getFlags();
                        } else {
                            i22 = 16;
                        }
                        String[] strArr2 = DevicePolicyManagerService.DELEGATIONS;
                        Binder.withCleanCallingIdentity(new DevicePolicyManagerService$$ExternalSyntheticLambda101(intValue, context, emptyList));
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.DevicePolicyManagerService$$ExternalSyntheticLambda204
                            public final void runOrThrow() {
                                try {
                                    ActivityTaskManager.getService().updateLockTaskFeatures(intValue, i22);
                                } catch (RemoteException e) {
                                    Slog.wtf("DevicePolicyManager", "Remote Exception: ", e);
                                }
                            }
                        });
                        return Boolean.TRUE;
                    case 4:
                        Set set = (Set) obj;
                        Context context2 = (Context) obj2;
                        int intValue2 = ((Integer) obj3).intValue();
                        ProtectedPackagesFilter protectedPackagesFilter = ProtectedPackagesFilter.ProtectedPackagesFilterHolder.INSTANCE;
                        List<String> list = set == null ? null : set.stream().toList();
                        protectedPackagesFilter.getClass();
                        Slog.d("ProtectedPackagesFilter", "updateProtectedPackages is called. userId " + intValue2 + " package names : " + list);
                        synchronized (protectedPackagesFilter.mProtectedPackages) {
                            try {
                                if (list == null) {
                                    protectedPackagesFilter.mProtectedPackages.remove(intValue2);
                                } else {
                                    protectedPackagesFilter.mProtectedPackages.put(intValue2, new ArraySet(list));
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        if (list != null) {
                            for (String str : list) {
                                boolean z = MARsPolicyManager.MARs_ENABLE;
                                if (!MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.cancelDisablePolicy(str, intValue2, 0)) {
                                    BootReceiver$$ExternalSyntheticOutline0.m("cancelDisablePolicy failed. package : ", str, "MARsPolicyManager");
                                }
                            }
                        }
                        Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(context2, intValue2, set));
                        return Boolean.TRUE;
                    case 5:
                        Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(((Integer) obj3).intValue(), 2, (PolicyKey) obj4, (ComponentName) obj));
                        return Boolean.TRUE;
                    case 6:
                        return Boolean.valueOf(Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda3((PolicyKey) obj4, (Boolean) obj, ((Integer) obj3).intValue(), 1))));
                    case 7:
                        final Context context3 = (Context) obj2;
                        final Integer num = (Integer) obj3;
                        final PolicyKey policyKey = (PolicyKey) obj4;
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda4
                            public final void runOrThrow() {
                                PackagePolicyKey packagePolicyKey = policyKey;
                                Context context4 = context3;
                                Integer num2 = num;
                                String packageName = packagePolicyKey.getPackageName();
                                Objects.requireNonNull(packageName);
                                Intent intent = new Intent("android.intent.action.APPLICATION_RESTRICTIONS_CHANGED");
                                intent.setPackage(packageName);
                                intent.addFlags(1073741824);
                                context4.sendBroadcastAsUser(intent, UserHandle.of(num2.intValue()));
                            }
                        });
                        return Boolean.TRUE;
                    case 8:
                        return Boolean.TRUE;
                    case 9:
                        return Boolean.valueOf(Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda3((PolicyKey) obj4, ((Integer) obj3).intValue(), (Boolean) obj))));
                    case 10:
                        return Boolean.TRUE;
                    case 11:
                        return Boolean.valueOf(Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda3((PolicyKey) obj4, (Boolean) obj, ((Integer) obj3).intValue(), i32))));
                    case 12:
                        return Boolean.TRUE;
                    case 13:
                        return Boolean.TRUE;
                    case 14:
                        final Boolean bool3 = (Boolean) obj;
                        final int intValue3 = ((Integer) obj3).intValue();
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda1
                            public final void runOrThrow() {
                                int i42 = intValue3;
                                Boolean bool4 = bool3;
                                DevicePolicyCache devicePolicyCache = DevicePolicyCache.getInstance();
                                if (devicePolicyCache instanceof DevicePolicyCacheImpl) {
                                    DevicePolicyCacheImpl devicePolicyCacheImpl = (DevicePolicyCacheImpl) devicePolicyCache;
                                    boolean z2 = bool4 != null && bool4.booleanValue();
                                    synchronized (devicePolicyCacheImpl.mLock) {
                                        try {
                                            if (z2) {
                                                ((HashSet) devicePolicyCacheImpl.mScreenCaptureDisallowedUsers).add(Integer.valueOf(i42));
                                            } else {
                                                ((HashSet) devicePolicyCacheImpl.mScreenCaptureDisallowedUsers).remove(Integer.valueOf(i42));
                                            }
                                        } catch (Throwable th2) {
                                            throw th2;
                                        }
                                    }
                                    BackgroundThread.getHandler().post(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda10());
                                }
                            }
                        });
                        return Boolean.TRUE;
                    case 15:
                        Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(((Integer) obj3).intValue(), i32, (Boolean) obj, (Context) obj2));
                        return Boolean.TRUE;
                    case 16:
                        final Boolean bool4 = (Boolean) obj;
                        final Context context4 = (Context) obj2;
                        Boolean bool5 = (Boolean) Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda2
                            public final Object getOrThrow() {
                                Context context5 = context4;
                                Boolean bool6 = bool4;
                                Objects.requireNonNull(context5);
                                DevicePolicyManagerService.updateUsbDataSignal(context5, bool6 == null || bool6.booleanValue());
                                return Boolean.TRUE;
                            }
                        });
                        bool5.getClass();
                        return bool5;
                    case 17:
                        final Integer num2 = (Integer) obj;
                        final Integer num3 = (Integer) obj3;
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda6
                            public final void runOrThrow() {
                                Integer num4 = num3;
                                Integer num5 = num2;
                                DevicePolicyCache devicePolicyCache = DevicePolicyCache.getInstance();
                                if (devicePolicyCache instanceof DevicePolicyCacheImpl) {
                                    ((DevicePolicyCacheImpl) devicePolicyCache).setContentProtectionPolicy(num4.intValue(), num5);
                                }
                            }
                        });
                        return Boolean.TRUE;
                    default:
                        ((Integer) obj3).intValue();
                        String[] strArr3 = DevicePolicyManagerService.DELEGATIONS;
                        Slogf.w("PolicyEnforcerCallbacks", "Trying to enforce setPermissionGrantState while flag is off.");
                        return Boolean.TRUE;
                }
            }
        }, new PackageSetPolicySerializer());
        USER_CONTROLLED_DISABLED_PACKAGES = policyDefinition6;
        final int i7 = 5;
        PolicyDefinition policyDefinition7 = new PolicyDefinition(new IntentFilterPolicyKey("persistentPreferredActivity"), new TopPriority(List.of("role:android.app.role.SYSTEM_FINANCED_DEVICE_CONTROLLER", "enterprise")), 2, new QuadFunction() { // from class: com.android.server.devicepolicy.PolicyDefinition$$ExternalSyntheticLambda0
            public final Object apply(Object obj, Object obj2, Object obj3, Object obj4) {
                final int i22;
                int i32 = 0;
                switch (i7) {
                    case 0:
                        String[] strArr = DevicePolicyManagerService.DELEGATIONS;
                        Slogf.w("PolicyEnforcerCallbacks", "Trying to enforce setAutoTimezoneEnabled while flag is off.");
                        return Boolean.TRUE;
                    case 1:
                        ((Integer) obj3).intValue();
                        DevicePolicyManagerInternal devicePolicyManagerInternal = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
                        Boolean bool = Boolean.TRUE;
                        devicePolicyManagerInternal.enforceSecurityLoggingPolicy(bool.equals((Boolean) obj));
                        return bool;
                    case 2:
                        ((Integer) obj3).intValue();
                        DevicePolicyManagerInternal devicePolicyManagerInternal2 = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
                        Boolean bool2 = Boolean.TRUE;
                        devicePolicyManagerInternal2.enforceAuditLoggingPolicy(bool2.equals((Boolean) obj));
                        return bool2;
                    case 3:
                        LockTaskPolicy lockTaskPolicy = (LockTaskPolicy) obj;
                        Context context = (Context) obj2;
                        final int intValue = ((Integer) obj3).intValue();
                        List emptyList = Collections.emptyList();
                        if (lockTaskPolicy != null) {
                            emptyList = List.copyOf(lockTaskPolicy.getPackages());
                            i22 = lockTaskPolicy.getFlags();
                        } else {
                            i22 = 16;
                        }
                        String[] strArr2 = DevicePolicyManagerService.DELEGATIONS;
                        Binder.withCleanCallingIdentity(new DevicePolicyManagerService$$ExternalSyntheticLambda101(intValue, context, emptyList));
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.DevicePolicyManagerService$$ExternalSyntheticLambda204
                            public final void runOrThrow() {
                                try {
                                    ActivityTaskManager.getService().updateLockTaskFeatures(intValue, i22);
                                } catch (RemoteException e) {
                                    Slog.wtf("DevicePolicyManager", "Remote Exception: ", e);
                                }
                            }
                        });
                        return Boolean.TRUE;
                    case 4:
                        Set set = (Set) obj;
                        Context context2 = (Context) obj2;
                        int intValue2 = ((Integer) obj3).intValue();
                        ProtectedPackagesFilter protectedPackagesFilter = ProtectedPackagesFilter.ProtectedPackagesFilterHolder.INSTANCE;
                        List<String> list = set == null ? null : set.stream().toList();
                        protectedPackagesFilter.getClass();
                        Slog.d("ProtectedPackagesFilter", "updateProtectedPackages is called. userId " + intValue2 + " package names : " + list);
                        synchronized (protectedPackagesFilter.mProtectedPackages) {
                            try {
                                if (list == null) {
                                    protectedPackagesFilter.mProtectedPackages.remove(intValue2);
                                } else {
                                    protectedPackagesFilter.mProtectedPackages.put(intValue2, new ArraySet(list));
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        if (list != null) {
                            for (String str : list) {
                                boolean z = MARsPolicyManager.MARs_ENABLE;
                                if (!MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.cancelDisablePolicy(str, intValue2, 0)) {
                                    BootReceiver$$ExternalSyntheticOutline0.m("cancelDisablePolicy failed. package : ", str, "MARsPolicyManager");
                                }
                            }
                        }
                        Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(context2, intValue2, set));
                        return Boolean.TRUE;
                    case 5:
                        Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(((Integer) obj3).intValue(), 2, (PolicyKey) obj4, (ComponentName) obj));
                        return Boolean.TRUE;
                    case 6:
                        return Boolean.valueOf(Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda3((PolicyKey) obj4, (Boolean) obj, ((Integer) obj3).intValue(), 1))));
                    case 7:
                        final Context context3 = (Context) obj2;
                        final Integer num = (Integer) obj3;
                        final PolicyKey policyKey = (PolicyKey) obj4;
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda4
                            public final void runOrThrow() {
                                PackagePolicyKey packagePolicyKey = policyKey;
                                Context context4 = context3;
                                Integer num2 = num;
                                String packageName = packagePolicyKey.getPackageName();
                                Objects.requireNonNull(packageName);
                                Intent intent = new Intent("android.intent.action.APPLICATION_RESTRICTIONS_CHANGED");
                                intent.setPackage(packageName);
                                intent.addFlags(1073741824);
                                context4.sendBroadcastAsUser(intent, UserHandle.of(num2.intValue()));
                            }
                        });
                        return Boolean.TRUE;
                    case 8:
                        return Boolean.TRUE;
                    case 9:
                        return Boolean.valueOf(Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda3((PolicyKey) obj4, ((Integer) obj3).intValue(), (Boolean) obj))));
                    case 10:
                        return Boolean.TRUE;
                    case 11:
                        return Boolean.valueOf(Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda3((PolicyKey) obj4, (Boolean) obj, ((Integer) obj3).intValue(), i32))));
                    case 12:
                        return Boolean.TRUE;
                    case 13:
                        return Boolean.TRUE;
                    case 14:
                        final Boolean bool3 = (Boolean) obj;
                        final int intValue3 = ((Integer) obj3).intValue();
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda1
                            public final void runOrThrow() {
                                int i42 = intValue3;
                                Boolean bool4 = bool3;
                                DevicePolicyCache devicePolicyCache = DevicePolicyCache.getInstance();
                                if (devicePolicyCache instanceof DevicePolicyCacheImpl) {
                                    DevicePolicyCacheImpl devicePolicyCacheImpl = (DevicePolicyCacheImpl) devicePolicyCache;
                                    boolean z2 = bool4 != null && bool4.booleanValue();
                                    synchronized (devicePolicyCacheImpl.mLock) {
                                        try {
                                            if (z2) {
                                                ((HashSet) devicePolicyCacheImpl.mScreenCaptureDisallowedUsers).add(Integer.valueOf(i42));
                                            } else {
                                                ((HashSet) devicePolicyCacheImpl.mScreenCaptureDisallowedUsers).remove(Integer.valueOf(i42));
                                            }
                                        } catch (Throwable th2) {
                                            throw th2;
                                        }
                                    }
                                    BackgroundThread.getHandler().post(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda10());
                                }
                            }
                        });
                        return Boolean.TRUE;
                    case 15:
                        Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(((Integer) obj3).intValue(), i32, (Boolean) obj, (Context) obj2));
                        return Boolean.TRUE;
                    case 16:
                        final Boolean bool4 = (Boolean) obj;
                        final Context context4 = (Context) obj2;
                        Boolean bool5 = (Boolean) Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda2
                            public final Object getOrThrow() {
                                Context context5 = context4;
                                Boolean bool6 = bool4;
                                Objects.requireNonNull(context5);
                                DevicePolicyManagerService.updateUsbDataSignal(context5, bool6 == null || bool6.booleanValue());
                                return Boolean.TRUE;
                            }
                        });
                        bool5.getClass();
                        return bool5;
                    case 17:
                        final Integer num2 = (Integer) obj;
                        final Integer num3 = (Integer) obj3;
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda6
                            public final void runOrThrow() {
                                Integer num4 = num3;
                                Integer num5 = num2;
                                DevicePolicyCache devicePolicyCache = DevicePolicyCache.getInstance();
                                if (devicePolicyCache instanceof DevicePolicyCacheImpl) {
                                    ((DevicePolicyCacheImpl) devicePolicyCache).setContentProtectionPolicy(num4.intValue(), num5);
                                }
                            }
                        });
                        return Boolean.TRUE;
                    default:
                        ((Integer) obj3).intValue();
                        String[] strArr3 = DevicePolicyManagerService.DELEGATIONS;
                        Slogf.w("PolicyEnforcerCallbacks", "Trying to enforce setPermissionGrantState while flag is off.");
                        return Boolean.TRUE;
                }
            }
        }, new ComponentNamePolicySerializer());
        GENERIC_PERSISTENT_PREFERRED_ACTIVITY = policyDefinition7;
        final int i8 = 6;
        PolicyDefinition policyDefinition8 = new PolicyDefinition(new PackagePolicyKey("packageUninstallBlocked"), mostRestrictive2, 2, new QuadFunction() { // from class: com.android.server.devicepolicy.PolicyDefinition$$ExternalSyntheticLambda0
            public final Object apply(Object obj, Object obj2, Object obj3, Object obj4) {
                final int i22;
                int i32 = 0;
                switch (i8) {
                    case 0:
                        String[] strArr = DevicePolicyManagerService.DELEGATIONS;
                        Slogf.w("PolicyEnforcerCallbacks", "Trying to enforce setAutoTimezoneEnabled while flag is off.");
                        return Boolean.TRUE;
                    case 1:
                        ((Integer) obj3).intValue();
                        DevicePolicyManagerInternal devicePolicyManagerInternal = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
                        Boolean bool = Boolean.TRUE;
                        devicePolicyManagerInternal.enforceSecurityLoggingPolicy(bool.equals((Boolean) obj));
                        return bool;
                    case 2:
                        ((Integer) obj3).intValue();
                        DevicePolicyManagerInternal devicePolicyManagerInternal2 = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
                        Boolean bool2 = Boolean.TRUE;
                        devicePolicyManagerInternal2.enforceAuditLoggingPolicy(bool2.equals((Boolean) obj));
                        return bool2;
                    case 3:
                        LockTaskPolicy lockTaskPolicy = (LockTaskPolicy) obj;
                        Context context = (Context) obj2;
                        final int intValue = ((Integer) obj3).intValue();
                        List emptyList = Collections.emptyList();
                        if (lockTaskPolicy != null) {
                            emptyList = List.copyOf(lockTaskPolicy.getPackages());
                            i22 = lockTaskPolicy.getFlags();
                        } else {
                            i22 = 16;
                        }
                        String[] strArr2 = DevicePolicyManagerService.DELEGATIONS;
                        Binder.withCleanCallingIdentity(new DevicePolicyManagerService$$ExternalSyntheticLambda101(intValue, context, emptyList));
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.DevicePolicyManagerService$$ExternalSyntheticLambda204
                            public final void runOrThrow() {
                                try {
                                    ActivityTaskManager.getService().updateLockTaskFeatures(intValue, i22);
                                } catch (RemoteException e) {
                                    Slog.wtf("DevicePolicyManager", "Remote Exception: ", e);
                                }
                            }
                        });
                        return Boolean.TRUE;
                    case 4:
                        Set set = (Set) obj;
                        Context context2 = (Context) obj2;
                        int intValue2 = ((Integer) obj3).intValue();
                        ProtectedPackagesFilter protectedPackagesFilter = ProtectedPackagesFilter.ProtectedPackagesFilterHolder.INSTANCE;
                        List<String> list = set == null ? null : set.stream().toList();
                        protectedPackagesFilter.getClass();
                        Slog.d("ProtectedPackagesFilter", "updateProtectedPackages is called. userId " + intValue2 + " package names : " + list);
                        synchronized (protectedPackagesFilter.mProtectedPackages) {
                            try {
                                if (list == null) {
                                    protectedPackagesFilter.mProtectedPackages.remove(intValue2);
                                } else {
                                    protectedPackagesFilter.mProtectedPackages.put(intValue2, new ArraySet(list));
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        if (list != null) {
                            for (String str : list) {
                                boolean z = MARsPolicyManager.MARs_ENABLE;
                                if (!MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.cancelDisablePolicy(str, intValue2, 0)) {
                                    BootReceiver$$ExternalSyntheticOutline0.m("cancelDisablePolicy failed. package : ", str, "MARsPolicyManager");
                                }
                            }
                        }
                        Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(context2, intValue2, set));
                        return Boolean.TRUE;
                    case 5:
                        Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(((Integer) obj3).intValue(), 2, (PolicyKey) obj4, (ComponentName) obj));
                        return Boolean.TRUE;
                    case 6:
                        return Boolean.valueOf(Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda3((PolicyKey) obj4, (Boolean) obj, ((Integer) obj3).intValue(), 1))));
                    case 7:
                        final Context context3 = (Context) obj2;
                        final Integer num = (Integer) obj3;
                        final PolicyKey policyKey = (PolicyKey) obj4;
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda4
                            public final void runOrThrow() {
                                PackagePolicyKey packagePolicyKey = policyKey;
                                Context context4 = context3;
                                Integer num2 = num;
                                String packageName = packagePolicyKey.getPackageName();
                                Objects.requireNonNull(packageName);
                                Intent intent = new Intent("android.intent.action.APPLICATION_RESTRICTIONS_CHANGED");
                                intent.setPackage(packageName);
                                intent.addFlags(1073741824);
                                context4.sendBroadcastAsUser(intent, UserHandle.of(num2.intValue()));
                            }
                        });
                        return Boolean.TRUE;
                    case 8:
                        return Boolean.TRUE;
                    case 9:
                        return Boolean.valueOf(Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda3((PolicyKey) obj4, ((Integer) obj3).intValue(), (Boolean) obj))));
                    case 10:
                        return Boolean.TRUE;
                    case 11:
                        return Boolean.valueOf(Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda3((PolicyKey) obj4, (Boolean) obj, ((Integer) obj3).intValue(), i32))));
                    case 12:
                        return Boolean.TRUE;
                    case 13:
                        return Boolean.TRUE;
                    case 14:
                        final Boolean bool3 = (Boolean) obj;
                        final int intValue3 = ((Integer) obj3).intValue();
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda1
                            public final void runOrThrow() {
                                int i42 = intValue3;
                                Boolean bool4 = bool3;
                                DevicePolicyCache devicePolicyCache = DevicePolicyCache.getInstance();
                                if (devicePolicyCache instanceof DevicePolicyCacheImpl) {
                                    DevicePolicyCacheImpl devicePolicyCacheImpl = (DevicePolicyCacheImpl) devicePolicyCache;
                                    boolean z2 = bool4 != null && bool4.booleanValue();
                                    synchronized (devicePolicyCacheImpl.mLock) {
                                        try {
                                            if (z2) {
                                                ((HashSet) devicePolicyCacheImpl.mScreenCaptureDisallowedUsers).add(Integer.valueOf(i42));
                                            } else {
                                                ((HashSet) devicePolicyCacheImpl.mScreenCaptureDisallowedUsers).remove(Integer.valueOf(i42));
                                            }
                                        } catch (Throwable th2) {
                                            throw th2;
                                        }
                                    }
                                    BackgroundThread.getHandler().post(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda10());
                                }
                            }
                        });
                        return Boolean.TRUE;
                    case 15:
                        Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(((Integer) obj3).intValue(), i32, (Boolean) obj, (Context) obj2));
                        return Boolean.TRUE;
                    case 16:
                        final Boolean bool4 = (Boolean) obj;
                        final Context context4 = (Context) obj2;
                        Boolean bool5 = (Boolean) Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda2
                            public final Object getOrThrow() {
                                Context context5 = context4;
                                Boolean bool6 = bool4;
                                Objects.requireNonNull(context5);
                                DevicePolicyManagerService.updateUsbDataSignal(context5, bool6 == null || bool6.booleanValue());
                                return Boolean.TRUE;
                            }
                        });
                        bool5.getClass();
                        return bool5;
                    case 17:
                        final Integer num2 = (Integer) obj;
                        final Integer num3 = (Integer) obj3;
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda6
                            public final void runOrThrow() {
                                Integer num4 = num3;
                                Integer num5 = num2;
                                DevicePolicyCache devicePolicyCache = DevicePolicyCache.getInstance();
                                if (devicePolicyCache instanceof DevicePolicyCacheImpl) {
                                    ((DevicePolicyCacheImpl) devicePolicyCache).setContentProtectionPolicy(num4.intValue(), num5);
                                }
                            }
                        });
                        return Boolean.TRUE;
                    default:
                        ((Integer) obj3).intValue();
                        String[] strArr3 = DevicePolicyManagerService.DELEGATIONS;
                        Slogf.w("PolicyEnforcerCallbacks", "Trying to enforce setPermissionGrantState while flag is off.");
                        return Boolean.TRUE;
                }
            }
        }, new BooleanPolicySerializer());
        GENERIC_PACKAGE_UNINSTALL_BLOCKED = policyDefinition8;
        final int i9 = 7;
        PolicyDefinition policyDefinition9 = new PolicyDefinition(new PackagePolicyKey("applicationRestrictions"), new MostRecent(), 46, new QuadFunction() { // from class: com.android.server.devicepolicy.PolicyDefinition$$ExternalSyntheticLambda0
            public final Object apply(Object obj, Object obj2, Object obj3, Object obj4) {
                final int i22;
                int i32 = 0;
                switch (i9) {
                    case 0:
                        String[] strArr = DevicePolicyManagerService.DELEGATIONS;
                        Slogf.w("PolicyEnforcerCallbacks", "Trying to enforce setAutoTimezoneEnabled while flag is off.");
                        return Boolean.TRUE;
                    case 1:
                        ((Integer) obj3).intValue();
                        DevicePolicyManagerInternal devicePolicyManagerInternal = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
                        Boolean bool = Boolean.TRUE;
                        devicePolicyManagerInternal.enforceSecurityLoggingPolicy(bool.equals((Boolean) obj));
                        return bool;
                    case 2:
                        ((Integer) obj3).intValue();
                        DevicePolicyManagerInternal devicePolicyManagerInternal2 = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
                        Boolean bool2 = Boolean.TRUE;
                        devicePolicyManagerInternal2.enforceAuditLoggingPolicy(bool2.equals((Boolean) obj));
                        return bool2;
                    case 3:
                        LockTaskPolicy lockTaskPolicy = (LockTaskPolicy) obj;
                        Context context = (Context) obj2;
                        final int intValue = ((Integer) obj3).intValue();
                        List emptyList = Collections.emptyList();
                        if (lockTaskPolicy != null) {
                            emptyList = List.copyOf(lockTaskPolicy.getPackages());
                            i22 = lockTaskPolicy.getFlags();
                        } else {
                            i22 = 16;
                        }
                        String[] strArr2 = DevicePolicyManagerService.DELEGATIONS;
                        Binder.withCleanCallingIdentity(new DevicePolicyManagerService$$ExternalSyntheticLambda101(intValue, context, emptyList));
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.DevicePolicyManagerService$$ExternalSyntheticLambda204
                            public final void runOrThrow() {
                                try {
                                    ActivityTaskManager.getService().updateLockTaskFeatures(intValue, i22);
                                } catch (RemoteException e) {
                                    Slog.wtf("DevicePolicyManager", "Remote Exception: ", e);
                                }
                            }
                        });
                        return Boolean.TRUE;
                    case 4:
                        Set set = (Set) obj;
                        Context context2 = (Context) obj2;
                        int intValue2 = ((Integer) obj3).intValue();
                        ProtectedPackagesFilter protectedPackagesFilter = ProtectedPackagesFilter.ProtectedPackagesFilterHolder.INSTANCE;
                        List<String> list = set == null ? null : set.stream().toList();
                        protectedPackagesFilter.getClass();
                        Slog.d("ProtectedPackagesFilter", "updateProtectedPackages is called. userId " + intValue2 + " package names : " + list);
                        synchronized (protectedPackagesFilter.mProtectedPackages) {
                            try {
                                if (list == null) {
                                    protectedPackagesFilter.mProtectedPackages.remove(intValue2);
                                } else {
                                    protectedPackagesFilter.mProtectedPackages.put(intValue2, new ArraySet(list));
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        if (list != null) {
                            for (String str : list) {
                                boolean z = MARsPolicyManager.MARs_ENABLE;
                                if (!MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.cancelDisablePolicy(str, intValue2, 0)) {
                                    BootReceiver$$ExternalSyntheticOutline0.m("cancelDisablePolicy failed. package : ", str, "MARsPolicyManager");
                                }
                            }
                        }
                        Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(context2, intValue2, set));
                        return Boolean.TRUE;
                    case 5:
                        Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(((Integer) obj3).intValue(), 2, (PolicyKey) obj4, (ComponentName) obj));
                        return Boolean.TRUE;
                    case 6:
                        return Boolean.valueOf(Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda3((PolicyKey) obj4, (Boolean) obj, ((Integer) obj3).intValue(), 1))));
                    case 7:
                        final Context context3 = (Context) obj2;
                        final Integer num = (Integer) obj3;
                        final PolicyKey policyKey = (PolicyKey) obj4;
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda4
                            public final void runOrThrow() {
                                PackagePolicyKey packagePolicyKey = policyKey;
                                Context context4 = context3;
                                Integer num2 = num;
                                String packageName = packagePolicyKey.getPackageName();
                                Objects.requireNonNull(packageName);
                                Intent intent = new Intent("android.intent.action.APPLICATION_RESTRICTIONS_CHANGED");
                                intent.setPackage(packageName);
                                intent.addFlags(1073741824);
                                context4.sendBroadcastAsUser(intent, UserHandle.of(num2.intValue()));
                            }
                        });
                        return Boolean.TRUE;
                    case 8:
                        return Boolean.TRUE;
                    case 9:
                        return Boolean.valueOf(Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda3((PolicyKey) obj4, ((Integer) obj3).intValue(), (Boolean) obj))));
                    case 10:
                        return Boolean.TRUE;
                    case 11:
                        return Boolean.valueOf(Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda3((PolicyKey) obj4, (Boolean) obj, ((Integer) obj3).intValue(), i32))));
                    case 12:
                        return Boolean.TRUE;
                    case 13:
                        return Boolean.TRUE;
                    case 14:
                        final Boolean bool3 = (Boolean) obj;
                        final int intValue3 = ((Integer) obj3).intValue();
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda1
                            public final void runOrThrow() {
                                int i42 = intValue3;
                                Boolean bool4 = bool3;
                                DevicePolicyCache devicePolicyCache = DevicePolicyCache.getInstance();
                                if (devicePolicyCache instanceof DevicePolicyCacheImpl) {
                                    DevicePolicyCacheImpl devicePolicyCacheImpl = (DevicePolicyCacheImpl) devicePolicyCache;
                                    boolean z2 = bool4 != null && bool4.booleanValue();
                                    synchronized (devicePolicyCacheImpl.mLock) {
                                        try {
                                            if (z2) {
                                                ((HashSet) devicePolicyCacheImpl.mScreenCaptureDisallowedUsers).add(Integer.valueOf(i42));
                                            } else {
                                                ((HashSet) devicePolicyCacheImpl.mScreenCaptureDisallowedUsers).remove(Integer.valueOf(i42));
                                            }
                                        } catch (Throwable th2) {
                                            throw th2;
                                        }
                                    }
                                    BackgroundThread.getHandler().post(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda10());
                                }
                            }
                        });
                        return Boolean.TRUE;
                    case 15:
                        Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(((Integer) obj3).intValue(), i32, (Boolean) obj, (Context) obj2));
                        return Boolean.TRUE;
                    case 16:
                        final Boolean bool4 = (Boolean) obj;
                        final Context context4 = (Context) obj2;
                        Boolean bool5 = (Boolean) Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda2
                            public final Object getOrThrow() {
                                Context context5 = context4;
                                Boolean bool6 = bool4;
                                Objects.requireNonNull(context5);
                                DevicePolicyManagerService.updateUsbDataSignal(context5, bool6 == null || bool6.booleanValue());
                                return Boolean.TRUE;
                            }
                        });
                        bool5.getClass();
                        return bool5;
                    case 17:
                        final Integer num2 = (Integer) obj;
                        final Integer num3 = (Integer) obj3;
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda6
                            public final void runOrThrow() {
                                Integer num4 = num3;
                                Integer num5 = num2;
                                DevicePolicyCache devicePolicyCache = DevicePolicyCache.getInstance();
                                if (devicePolicyCache instanceof DevicePolicyCacheImpl) {
                                    ((DevicePolicyCacheImpl) devicePolicyCache).setContentProtectionPolicy(num4.intValue(), num5);
                                }
                            }
                        });
                        return Boolean.TRUE;
                    default:
                        ((Integer) obj3).intValue();
                        String[] strArr3 = DevicePolicyManagerService.DELEGATIONS;
                        Slogf.w("PolicyEnforcerCallbacks", "Trying to enforce setPermissionGrantState while flag is off.");
                        return Boolean.TRUE;
                }
            }
        }, new BundlePolicySerializer());
        GENERIC_APPLICATION_RESTRICTIONS = policyDefinition9;
        final int i10 = 8;
        PolicyDefinition policyDefinition10 = new PolicyDefinition(new NoArgsPolicyKey("resetPasswordToken"), new MostRecent(), 10, new QuadFunction() { // from class: com.android.server.devicepolicy.PolicyDefinition$$ExternalSyntheticLambda0
            public final Object apply(Object obj, Object obj2, Object obj3, Object obj4) {
                final int i22;
                int i32 = 0;
                switch (i10) {
                    case 0:
                        String[] strArr = DevicePolicyManagerService.DELEGATIONS;
                        Slogf.w("PolicyEnforcerCallbacks", "Trying to enforce setAutoTimezoneEnabled while flag is off.");
                        return Boolean.TRUE;
                    case 1:
                        ((Integer) obj3).intValue();
                        DevicePolicyManagerInternal devicePolicyManagerInternal = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
                        Boolean bool = Boolean.TRUE;
                        devicePolicyManagerInternal.enforceSecurityLoggingPolicy(bool.equals((Boolean) obj));
                        return bool;
                    case 2:
                        ((Integer) obj3).intValue();
                        DevicePolicyManagerInternal devicePolicyManagerInternal2 = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
                        Boolean bool2 = Boolean.TRUE;
                        devicePolicyManagerInternal2.enforceAuditLoggingPolicy(bool2.equals((Boolean) obj));
                        return bool2;
                    case 3:
                        LockTaskPolicy lockTaskPolicy = (LockTaskPolicy) obj;
                        Context context = (Context) obj2;
                        final int intValue = ((Integer) obj3).intValue();
                        List emptyList = Collections.emptyList();
                        if (lockTaskPolicy != null) {
                            emptyList = List.copyOf(lockTaskPolicy.getPackages());
                            i22 = lockTaskPolicy.getFlags();
                        } else {
                            i22 = 16;
                        }
                        String[] strArr2 = DevicePolicyManagerService.DELEGATIONS;
                        Binder.withCleanCallingIdentity(new DevicePolicyManagerService$$ExternalSyntheticLambda101(intValue, context, emptyList));
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.DevicePolicyManagerService$$ExternalSyntheticLambda204
                            public final void runOrThrow() {
                                try {
                                    ActivityTaskManager.getService().updateLockTaskFeatures(intValue, i22);
                                } catch (RemoteException e) {
                                    Slog.wtf("DevicePolicyManager", "Remote Exception: ", e);
                                }
                            }
                        });
                        return Boolean.TRUE;
                    case 4:
                        Set set = (Set) obj;
                        Context context2 = (Context) obj2;
                        int intValue2 = ((Integer) obj3).intValue();
                        ProtectedPackagesFilter protectedPackagesFilter = ProtectedPackagesFilter.ProtectedPackagesFilterHolder.INSTANCE;
                        List<String> list = set == null ? null : set.stream().toList();
                        protectedPackagesFilter.getClass();
                        Slog.d("ProtectedPackagesFilter", "updateProtectedPackages is called. userId " + intValue2 + " package names : " + list);
                        synchronized (protectedPackagesFilter.mProtectedPackages) {
                            try {
                                if (list == null) {
                                    protectedPackagesFilter.mProtectedPackages.remove(intValue2);
                                } else {
                                    protectedPackagesFilter.mProtectedPackages.put(intValue2, new ArraySet(list));
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        if (list != null) {
                            for (String str : list) {
                                boolean z = MARsPolicyManager.MARs_ENABLE;
                                if (!MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.cancelDisablePolicy(str, intValue2, 0)) {
                                    BootReceiver$$ExternalSyntheticOutline0.m("cancelDisablePolicy failed. package : ", str, "MARsPolicyManager");
                                }
                            }
                        }
                        Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(context2, intValue2, set));
                        return Boolean.TRUE;
                    case 5:
                        Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(((Integer) obj3).intValue(), 2, (PolicyKey) obj4, (ComponentName) obj));
                        return Boolean.TRUE;
                    case 6:
                        return Boolean.valueOf(Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda3((PolicyKey) obj4, (Boolean) obj, ((Integer) obj3).intValue(), 1))));
                    case 7:
                        final Context context3 = (Context) obj2;
                        final Integer num = (Integer) obj3;
                        final PolicyKey policyKey = (PolicyKey) obj4;
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda4
                            public final void runOrThrow() {
                                PackagePolicyKey packagePolicyKey = policyKey;
                                Context context4 = context3;
                                Integer num2 = num;
                                String packageName = packagePolicyKey.getPackageName();
                                Objects.requireNonNull(packageName);
                                Intent intent = new Intent("android.intent.action.APPLICATION_RESTRICTIONS_CHANGED");
                                intent.setPackage(packageName);
                                intent.addFlags(1073741824);
                                context4.sendBroadcastAsUser(intent, UserHandle.of(num2.intValue()));
                            }
                        });
                        return Boolean.TRUE;
                    case 8:
                        return Boolean.TRUE;
                    case 9:
                        return Boolean.valueOf(Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda3((PolicyKey) obj4, ((Integer) obj3).intValue(), (Boolean) obj))));
                    case 10:
                        return Boolean.TRUE;
                    case 11:
                        return Boolean.valueOf(Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda3((PolicyKey) obj4, (Boolean) obj, ((Integer) obj3).intValue(), i32))));
                    case 12:
                        return Boolean.TRUE;
                    case 13:
                        return Boolean.TRUE;
                    case 14:
                        final Boolean bool3 = (Boolean) obj;
                        final int intValue3 = ((Integer) obj3).intValue();
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda1
                            public final void runOrThrow() {
                                int i42 = intValue3;
                                Boolean bool4 = bool3;
                                DevicePolicyCache devicePolicyCache = DevicePolicyCache.getInstance();
                                if (devicePolicyCache instanceof DevicePolicyCacheImpl) {
                                    DevicePolicyCacheImpl devicePolicyCacheImpl = (DevicePolicyCacheImpl) devicePolicyCache;
                                    boolean z2 = bool4 != null && bool4.booleanValue();
                                    synchronized (devicePolicyCacheImpl.mLock) {
                                        try {
                                            if (z2) {
                                                ((HashSet) devicePolicyCacheImpl.mScreenCaptureDisallowedUsers).add(Integer.valueOf(i42));
                                            } else {
                                                ((HashSet) devicePolicyCacheImpl.mScreenCaptureDisallowedUsers).remove(Integer.valueOf(i42));
                                            }
                                        } catch (Throwable th2) {
                                            throw th2;
                                        }
                                    }
                                    BackgroundThread.getHandler().post(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda10());
                                }
                            }
                        });
                        return Boolean.TRUE;
                    case 15:
                        Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(((Integer) obj3).intValue(), i32, (Boolean) obj, (Context) obj2));
                        return Boolean.TRUE;
                    case 16:
                        final Boolean bool4 = (Boolean) obj;
                        final Context context4 = (Context) obj2;
                        Boolean bool5 = (Boolean) Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda2
                            public final Object getOrThrow() {
                                Context context5 = context4;
                                Boolean bool6 = bool4;
                                Objects.requireNonNull(context5);
                                DevicePolicyManagerService.updateUsbDataSignal(context5, bool6 == null || bool6.booleanValue());
                                return Boolean.TRUE;
                            }
                        });
                        bool5.getClass();
                        return bool5;
                    case 17:
                        final Integer num2 = (Integer) obj;
                        final Integer num3 = (Integer) obj3;
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda6
                            public final void runOrThrow() {
                                Integer num4 = num3;
                                Integer num5 = num2;
                                DevicePolicyCache devicePolicyCache = DevicePolicyCache.getInstance();
                                if (devicePolicyCache instanceof DevicePolicyCacheImpl) {
                                    ((DevicePolicyCacheImpl) devicePolicyCache).setContentProtectionPolicy(num4.intValue(), num5);
                                }
                            }
                        });
                        return Boolean.TRUE;
                    default:
                        ((Integer) obj3).intValue();
                        String[] strArr3 = DevicePolicyManagerService.DELEGATIONS;
                        Slogf.w("PolicyEnforcerCallbacks", "Trying to enforce setPermissionGrantState while flag is off.");
                        return Boolean.TRUE;
                }
            }
        }, new LongPolicySerializer());
        final int i11 = 10;
        PolicyDefinition policyDefinition11 = new PolicyDefinition(new NoArgsPolicyKey("keyguardDisabledFeatures"), new FlagUnion(), 2, new QuadFunction() { // from class: com.android.server.devicepolicy.PolicyDefinition$$ExternalSyntheticLambda0
            public final Object apply(Object obj, Object obj2, Object obj3, Object obj4) {
                final int i22;
                int i32 = 0;
                switch (i11) {
                    case 0:
                        String[] strArr = DevicePolicyManagerService.DELEGATIONS;
                        Slogf.w("PolicyEnforcerCallbacks", "Trying to enforce setAutoTimezoneEnabled while flag is off.");
                        return Boolean.TRUE;
                    case 1:
                        ((Integer) obj3).intValue();
                        DevicePolicyManagerInternal devicePolicyManagerInternal = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
                        Boolean bool = Boolean.TRUE;
                        devicePolicyManagerInternal.enforceSecurityLoggingPolicy(bool.equals((Boolean) obj));
                        return bool;
                    case 2:
                        ((Integer) obj3).intValue();
                        DevicePolicyManagerInternal devicePolicyManagerInternal2 = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
                        Boolean bool2 = Boolean.TRUE;
                        devicePolicyManagerInternal2.enforceAuditLoggingPolicy(bool2.equals((Boolean) obj));
                        return bool2;
                    case 3:
                        LockTaskPolicy lockTaskPolicy = (LockTaskPolicy) obj;
                        Context context = (Context) obj2;
                        final int intValue = ((Integer) obj3).intValue();
                        List emptyList = Collections.emptyList();
                        if (lockTaskPolicy != null) {
                            emptyList = List.copyOf(lockTaskPolicy.getPackages());
                            i22 = lockTaskPolicy.getFlags();
                        } else {
                            i22 = 16;
                        }
                        String[] strArr2 = DevicePolicyManagerService.DELEGATIONS;
                        Binder.withCleanCallingIdentity(new DevicePolicyManagerService$$ExternalSyntheticLambda101(intValue, context, emptyList));
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.DevicePolicyManagerService$$ExternalSyntheticLambda204
                            public final void runOrThrow() {
                                try {
                                    ActivityTaskManager.getService().updateLockTaskFeatures(intValue, i22);
                                } catch (RemoteException e) {
                                    Slog.wtf("DevicePolicyManager", "Remote Exception: ", e);
                                }
                            }
                        });
                        return Boolean.TRUE;
                    case 4:
                        Set set = (Set) obj;
                        Context context2 = (Context) obj2;
                        int intValue2 = ((Integer) obj3).intValue();
                        ProtectedPackagesFilter protectedPackagesFilter = ProtectedPackagesFilter.ProtectedPackagesFilterHolder.INSTANCE;
                        List<String> list = set == null ? null : set.stream().toList();
                        protectedPackagesFilter.getClass();
                        Slog.d("ProtectedPackagesFilter", "updateProtectedPackages is called. userId " + intValue2 + " package names : " + list);
                        synchronized (protectedPackagesFilter.mProtectedPackages) {
                            try {
                                if (list == null) {
                                    protectedPackagesFilter.mProtectedPackages.remove(intValue2);
                                } else {
                                    protectedPackagesFilter.mProtectedPackages.put(intValue2, new ArraySet(list));
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        if (list != null) {
                            for (String str : list) {
                                boolean z = MARsPolicyManager.MARs_ENABLE;
                                if (!MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.cancelDisablePolicy(str, intValue2, 0)) {
                                    BootReceiver$$ExternalSyntheticOutline0.m("cancelDisablePolicy failed. package : ", str, "MARsPolicyManager");
                                }
                            }
                        }
                        Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(context2, intValue2, set));
                        return Boolean.TRUE;
                    case 5:
                        Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(((Integer) obj3).intValue(), 2, (PolicyKey) obj4, (ComponentName) obj));
                        return Boolean.TRUE;
                    case 6:
                        return Boolean.valueOf(Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda3((PolicyKey) obj4, (Boolean) obj, ((Integer) obj3).intValue(), 1))));
                    case 7:
                        final Context context3 = (Context) obj2;
                        final Integer num = (Integer) obj3;
                        final PolicyKey policyKey = (PolicyKey) obj4;
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda4
                            public final void runOrThrow() {
                                PackagePolicyKey packagePolicyKey = policyKey;
                                Context context4 = context3;
                                Integer num2 = num;
                                String packageName = packagePolicyKey.getPackageName();
                                Objects.requireNonNull(packageName);
                                Intent intent = new Intent("android.intent.action.APPLICATION_RESTRICTIONS_CHANGED");
                                intent.setPackage(packageName);
                                intent.addFlags(1073741824);
                                context4.sendBroadcastAsUser(intent, UserHandle.of(num2.intValue()));
                            }
                        });
                        return Boolean.TRUE;
                    case 8:
                        return Boolean.TRUE;
                    case 9:
                        return Boolean.valueOf(Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda3((PolicyKey) obj4, ((Integer) obj3).intValue(), (Boolean) obj))));
                    case 10:
                        return Boolean.TRUE;
                    case 11:
                        return Boolean.valueOf(Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda3((PolicyKey) obj4, (Boolean) obj, ((Integer) obj3).intValue(), i32))));
                    case 12:
                        return Boolean.TRUE;
                    case 13:
                        return Boolean.TRUE;
                    case 14:
                        final Boolean bool3 = (Boolean) obj;
                        final int intValue3 = ((Integer) obj3).intValue();
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda1
                            public final void runOrThrow() {
                                int i42 = intValue3;
                                Boolean bool4 = bool3;
                                DevicePolicyCache devicePolicyCache = DevicePolicyCache.getInstance();
                                if (devicePolicyCache instanceof DevicePolicyCacheImpl) {
                                    DevicePolicyCacheImpl devicePolicyCacheImpl = (DevicePolicyCacheImpl) devicePolicyCache;
                                    boolean z2 = bool4 != null && bool4.booleanValue();
                                    synchronized (devicePolicyCacheImpl.mLock) {
                                        try {
                                            if (z2) {
                                                ((HashSet) devicePolicyCacheImpl.mScreenCaptureDisallowedUsers).add(Integer.valueOf(i42));
                                            } else {
                                                ((HashSet) devicePolicyCacheImpl.mScreenCaptureDisallowedUsers).remove(Integer.valueOf(i42));
                                            }
                                        } catch (Throwable th2) {
                                            throw th2;
                                        }
                                    }
                                    BackgroundThread.getHandler().post(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda10());
                                }
                            }
                        });
                        return Boolean.TRUE;
                    case 15:
                        Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(((Integer) obj3).intValue(), i32, (Boolean) obj, (Context) obj2));
                        return Boolean.TRUE;
                    case 16:
                        final Boolean bool4 = (Boolean) obj;
                        final Context context4 = (Context) obj2;
                        Boolean bool5 = (Boolean) Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda2
                            public final Object getOrThrow() {
                                Context context5 = context4;
                                Boolean bool6 = bool4;
                                Objects.requireNonNull(context5);
                                DevicePolicyManagerService.updateUsbDataSignal(context5, bool6 == null || bool6.booleanValue());
                                return Boolean.TRUE;
                            }
                        });
                        bool5.getClass();
                        return bool5;
                    case 17:
                        final Integer num2 = (Integer) obj;
                        final Integer num3 = (Integer) obj3;
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda6
                            public final void runOrThrow() {
                                Integer num4 = num3;
                                Integer num5 = num2;
                                DevicePolicyCache devicePolicyCache = DevicePolicyCache.getInstance();
                                if (devicePolicyCache instanceof DevicePolicyCacheImpl) {
                                    ((DevicePolicyCacheImpl) devicePolicyCache).setContentProtectionPolicy(num4.intValue(), num5);
                                }
                            }
                        });
                        return Boolean.TRUE;
                    default:
                        ((Integer) obj3).intValue();
                        String[] strArr3 = DevicePolicyManagerService.DELEGATIONS;
                        Slogf.w("PolicyEnforcerCallbacks", "Trying to enforce setPermissionGrantState while flag is off.");
                        return Boolean.TRUE;
                }
            }
        }, new IntegerPolicySerializer());
        final int i12 = 11;
        PolicyDefinition policyDefinition12 = new PolicyDefinition(new PackagePolicyKey("applicationHidden"), mostRestrictive2, 6, new QuadFunction() { // from class: com.android.server.devicepolicy.PolicyDefinition$$ExternalSyntheticLambda0
            public final Object apply(Object obj, Object obj2, Object obj3, Object obj4) {
                final int i22;
                int i32 = 0;
                switch (i12) {
                    case 0:
                        String[] strArr = DevicePolicyManagerService.DELEGATIONS;
                        Slogf.w("PolicyEnforcerCallbacks", "Trying to enforce setAutoTimezoneEnabled while flag is off.");
                        return Boolean.TRUE;
                    case 1:
                        ((Integer) obj3).intValue();
                        DevicePolicyManagerInternal devicePolicyManagerInternal = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
                        Boolean bool = Boolean.TRUE;
                        devicePolicyManagerInternal.enforceSecurityLoggingPolicy(bool.equals((Boolean) obj));
                        return bool;
                    case 2:
                        ((Integer) obj3).intValue();
                        DevicePolicyManagerInternal devicePolicyManagerInternal2 = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
                        Boolean bool2 = Boolean.TRUE;
                        devicePolicyManagerInternal2.enforceAuditLoggingPolicy(bool2.equals((Boolean) obj));
                        return bool2;
                    case 3:
                        LockTaskPolicy lockTaskPolicy = (LockTaskPolicy) obj;
                        Context context = (Context) obj2;
                        final int intValue = ((Integer) obj3).intValue();
                        List emptyList = Collections.emptyList();
                        if (lockTaskPolicy != null) {
                            emptyList = List.copyOf(lockTaskPolicy.getPackages());
                            i22 = lockTaskPolicy.getFlags();
                        } else {
                            i22 = 16;
                        }
                        String[] strArr2 = DevicePolicyManagerService.DELEGATIONS;
                        Binder.withCleanCallingIdentity(new DevicePolicyManagerService$$ExternalSyntheticLambda101(intValue, context, emptyList));
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.DevicePolicyManagerService$$ExternalSyntheticLambda204
                            public final void runOrThrow() {
                                try {
                                    ActivityTaskManager.getService().updateLockTaskFeatures(intValue, i22);
                                } catch (RemoteException e) {
                                    Slog.wtf("DevicePolicyManager", "Remote Exception: ", e);
                                }
                            }
                        });
                        return Boolean.TRUE;
                    case 4:
                        Set set = (Set) obj;
                        Context context2 = (Context) obj2;
                        int intValue2 = ((Integer) obj3).intValue();
                        ProtectedPackagesFilter protectedPackagesFilter = ProtectedPackagesFilter.ProtectedPackagesFilterHolder.INSTANCE;
                        List<String> list = set == null ? null : set.stream().toList();
                        protectedPackagesFilter.getClass();
                        Slog.d("ProtectedPackagesFilter", "updateProtectedPackages is called. userId " + intValue2 + " package names : " + list);
                        synchronized (protectedPackagesFilter.mProtectedPackages) {
                            try {
                                if (list == null) {
                                    protectedPackagesFilter.mProtectedPackages.remove(intValue2);
                                } else {
                                    protectedPackagesFilter.mProtectedPackages.put(intValue2, new ArraySet(list));
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        if (list != null) {
                            for (String str : list) {
                                boolean z = MARsPolicyManager.MARs_ENABLE;
                                if (!MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.cancelDisablePolicy(str, intValue2, 0)) {
                                    BootReceiver$$ExternalSyntheticOutline0.m("cancelDisablePolicy failed. package : ", str, "MARsPolicyManager");
                                }
                            }
                        }
                        Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(context2, intValue2, set));
                        return Boolean.TRUE;
                    case 5:
                        Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(((Integer) obj3).intValue(), 2, (PolicyKey) obj4, (ComponentName) obj));
                        return Boolean.TRUE;
                    case 6:
                        return Boolean.valueOf(Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda3((PolicyKey) obj4, (Boolean) obj, ((Integer) obj3).intValue(), 1))));
                    case 7:
                        final Context context3 = (Context) obj2;
                        final Integer num = (Integer) obj3;
                        final PolicyKey policyKey = (PolicyKey) obj4;
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda4
                            public final void runOrThrow() {
                                PackagePolicyKey packagePolicyKey = policyKey;
                                Context context4 = context3;
                                Integer num2 = num;
                                String packageName = packagePolicyKey.getPackageName();
                                Objects.requireNonNull(packageName);
                                Intent intent = new Intent("android.intent.action.APPLICATION_RESTRICTIONS_CHANGED");
                                intent.setPackage(packageName);
                                intent.addFlags(1073741824);
                                context4.sendBroadcastAsUser(intent, UserHandle.of(num2.intValue()));
                            }
                        });
                        return Boolean.TRUE;
                    case 8:
                        return Boolean.TRUE;
                    case 9:
                        return Boolean.valueOf(Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda3((PolicyKey) obj4, ((Integer) obj3).intValue(), (Boolean) obj))));
                    case 10:
                        return Boolean.TRUE;
                    case 11:
                        return Boolean.valueOf(Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda3((PolicyKey) obj4, (Boolean) obj, ((Integer) obj3).intValue(), i32))));
                    case 12:
                        return Boolean.TRUE;
                    case 13:
                        return Boolean.TRUE;
                    case 14:
                        final Boolean bool3 = (Boolean) obj;
                        final int intValue3 = ((Integer) obj3).intValue();
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda1
                            public final void runOrThrow() {
                                int i42 = intValue3;
                                Boolean bool4 = bool3;
                                DevicePolicyCache devicePolicyCache = DevicePolicyCache.getInstance();
                                if (devicePolicyCache instanceof DevicePolicyCacheImpl) {
                                    DevicePolicyCacheImpl devicePolicyCacheImpl = (DevicePolicyCacheImpl) devicePolicyCache;
                                    boolean z2 = bool4 != null && bool4.booleanValue();
                                    synchronized (devicePolicyCacheImpl.mLock) {
                                        try {
                                            if (z2) {
                                                ((HashSet) devicePolicyCacheImpl.mScreenCaptureDisallowedUsers).add(Integer.valueOf(i42));
                                            } else {
                                                ((HashSet) devicePolicyCacheImpl.mScreenCaptureDisallowedUsers).remove(Integer.valueOf(i42));
                                            }
                                        } catch (Throwable th2) {
                                            throw th2;
                                        }
                                    }
                                    BackgroundThread.getHandler().post(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda10());
                                }
                            }
                        });
                        return Boolean.TRUE;
                    case 15:
                        Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(((Integer) obj3).intValue(), i32, (Boolean) obj, (Context) obj2));
                        return Boolean.TRUE;
                    case 16:
                        final Boolean bool4 = (Boolean) obj;
                        final Context context4 = (Context) obj2;
                        Boolean bool5 = (Boolean) Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda2
                            public final Object getOrThrow() {
                                Context context5 = context4;
                                Boolean bool6 = bool4;
                                Objects.requireNonNull(context5);
                                DevicePolicyManagerService.updateUsbDataSignal(context5, bool6 == null || bool6.booleanValue());
                                return Boolean.TRUE;
                            }
                        });
                        bool5.getClass();
                        return bool5;
                    case 17:
                        final Integer num2 = (Integer) obj;
                        final Integer num3 = (Integer) obj3;
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda6
                            public final void runOrThrow() {
                                Integer num4 = num3;
                                Integer num5 = num2;
                                DevicePolicyCache devicePolicyCache = DevicePolicyCache.getInstance();
                                if (devicePolicyCache instanceof DevicePolicyCacheImpl) {
                                    ((DevicePolicyCacheImpl) devicePolicyCache).setContentProtectionPolicy(num4.intValue(), num5);
                                }
                            }
                        });
                        return Boolean.TRUE;
                    default:
                        ((Integer) obj3).intValue();
                        String[] strArr3 = DevicePolicyManagerService.DELEGATIONS;
                        Slogf.w("PolicyEnforcerCallbacks", "Trying to enforce setPermissionGrantState while flag is off.");
                        return Boolean.TRUE;
                }
            }
        }, new BooleanPolicySerializer());
        GENERIC_APPLICATION_HIDDEN = policyDefinition12;
        final int i13 = 12;
        PolicyDefinition policyDefinition13 = new PolicyDefinition(new AccountTypePolicyKey("accountManagementDisabled"), mostRestrictive2, 6, new QuadFunction() { // from class: com.android.server.devicepolicy.PolicyDefinition$$ExternalSyntheticLambda0
            public final Object apply(Object obj, Object obj2, Object obj3, Object obj4) {
                final int i22;
                int i32 = 0;
                switch (i13) {
                    case 0:
                        String[] strArr = DevicePolicyManagerService.DELEGATIONS;
                        Slogf.w("PolicyEnforcerCallbacks", "Trying to enforce setAutoTimezoneEnabled while flag is off.");
                        return Boolean.TRUE;
                    case 1:
                        ((Integer) obj3).intValue();
                        DevicePolicyManagerInternal devicePolicyManagerInternal = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
                        Boolean bool = Boolean.TRUE;
                        devicePolicyManagerInternal.enforceSecurityLoggingPolicy(bool.equals((Boolean) obj));
                        return bool;
                    case 2:
                        ((Integer) obj3).intValue();
                        DevicePolicyManagerInternal devicePolicyManagerInternal2 = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
                        Boolean bool2 = Boolean.TRUE;
                        devicePolicyManagerInternal2.enforceAuditLoggingPolicy(bool2.equals((Boolean) obj));
                        return bool2;
                    case 3:
                        LockTaskPolicy lockTaskPolicy = (LockTaskPolicy) obj;
                        Context context = (Context) obj2;
                        final int intValue = ((Integer) obj3).intValue();
                        List emptyList = Collections.emptyList();
                        if (lockTaskPolicy != null) {
                            emptyList = List.copyOf(lockTaskPolicy.getPackages());
                            i22 = lockTaskPolicy.getFlags();
                        } else {
                            i22 = 16;
                        }
                        String[] strArr2 = DevicePolicyManagerService.DELEGATIONS;
                        Binder.withCleanCallingIdentity(new DevicePolicyManagerService$$ExternalSyntheticLambda101(intValue, context, emptyList));
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.DevicePolicyManagerService$$ExternalSyntheticLambda204
                            public final void runOrThrow() {
                                try {
                                    ActivityTaskManager.getService().updateLockTaskFeatures(intValue, i22);
                                } catch (RemoteException e) {
                                    Slog.wtf("DevicePolicyManager", "Remote Exception: ", e);
                                }
                            }
                        });
                        return Boolean.TRUE;
                    case 4:
                        Set set = (Set) obj;
                        Context context2 = (Context) obj2;
                        int intValue2 = ((Integer) obj3).intValue();
                        ProtectedPackagesFilter protectedPackagesFilter = ProtectedPackagesFilter.ProtectedPackagesFilterHolder.INSTANCE;
                        List<String> list = set == null ? null : set.stream().toList();
                        protectedPackagesFilter.getClass();
                        Slog.d("ProtectedPackagesFilter", "updateProtectedPackages is called. userId " + intValue2 + " package names : " + list);
                        synchronized (protectedPackagesFilter.mProtectedPackages) {
                            try {
                                if (list == null) {
                                    protectedPackagesFilter.mProtectedPackages.remove(intValue2);
                                } else {
                                    protectedPackagesFilter.mProtectedPackages.put(intValue2, new ArraySet(list));
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        if (list != null) {
                            for (String str : list) {
                                boolean z = MARsPolicyManager.MARs_ENABLE;
                                if (!MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.cancelDisablePolicy(str, intValue2, 0)) {
                                    BootReceiver$$ExternalSyntheticOutline0.m("cancelDisablePolicy failed. package : ", str, "MARsPolicyManager");
                                }
                            }
                        }
                        Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(context2, intValue2, set));
                        return Boolean.TRUE;
                    case 5:
                        Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(((Integer) obj3).intValue(), 2, (PolicyKey) obj4, (ComponentName) obj));
                        return Boolean.TRUE;
                    case 6:
                        return Boolean.valueOf(Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda3((PolicyKey) obj4, (Boolean) obj, ((Integer) obj3).intValue(), 1))));
                    case 7:
                        final Context context3 = (Context) obj2;
                        final Integer num = (Integer) obj3;
                        final PolicyKey policyKey = (PolicyKey) obj4;
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda4
                            public final void runOrThrow() {
                                PackagePolicyKey packagePolicyKey = policyKey;
                                Context context4 = context3;
                                Integer num2 = num;
                                String packageName = packagePolicyKey.getPackageName();
                                Objects.requireNonNull(packageName);
                                Intent intent = new Intent("android.intent.action.APPLICATION_RESTRICTIONS_CHANGED");
                                intent.setPackage(packageName);
                                intent.addFlags(1073741824);
                                context4.sendBroadcastAsUser(intent, UserHandle.of(num2.intValue()));
                            }
                        });
                        return Boolean.TRUE;
                    case 8:
                        return Boolean.TRUE;
                    case 9:
                        return Boolean.valueOf(Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda3((PolicyKey) obj4, ((Integer) obj3).intValue(), (Boolean) obj))));
                    case 10:
                        return Boolean.TRUE;
                    case 11:
                        return Boolean.valueOf(Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda3((PolicyKey) obj4, (Boolean) obj, ((Integer) obj3).intValue(), i32))));
                    case 12:
                        return Boolean.TRUE;
                    case 13:
                        return Boolean.TRUE;
                    case 14:
                        final Boolean bool3 = (Boolean) obj;
                        final int intValue3 = ((Integer) obj3).intValue();
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda1
                            public final void runOrThrow() {
                                int i42 = intValue3;
                                Boolean bool4 = bool3;
                                DevicePolicyCache devicePolicyCache = DevicePolicyCache.getInstance();
                                if (devicePolicyCache instanceof DevicePolicyCacheImpl) {
                                    DevicePolicyCacheImpl devicePolicyCacheImpl = (DevicePolicyCacheImpl) devicePolicyCache;
                                    boolean z2 = bool4 != null && bool4.booleanValue();
                                    synchronized (devicePolicyCacheImpl.mLock) {
                                        try {
                                            if (z2) {
                                                ((HashSet) devicePolicyCacheImpl.mScreenCaptureDisallowedUsers).add(Integer.valueOf(i42));
                                            } else {
                                                ((HashSet) devicePolicyCacheImpl.mScreenCaptureDisallowedUsers).remove(Integer.valueOf(i42));
                                            }
                                        } catch (Throwable th2) {
                                            throw th2;
                                        }
                                    }
                                    BackgroundThread.getHandler().post(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda10());
                                }
                            }
                        });
                        return Boolean.TRUE;
                    case 15:
                        Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(((Integer) obj3).intValue(), i32, (Boolean) obj, (Context) obj2));
                        return Boolean.TRUE;
                    case 16:
                        final Boolean bool4 = (Boolean) obj;
                        final Context context4 = (Context) obj2;
                        Boolean bool5 = (Boolean) Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda2
                            public final Object getOrThrow() {
                                Context context5 = context4;
                                Boolean bool6 = bool4;
                                Objects.requireNonNull(context5);
                                DevicePolicyManagerService.updateUsbDataSignal(context5, bool6 == null || bool6.booleanValue());
                                return Boolean.TRUE;
                            }
                        });
                        bool5.getClass();
                        return bool5;
                    case 17:
                        final Integer num2 = (Integer) obj;
                        final Integer num3 = (Integer) obj3;
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda6
                            public final void runOrThrow() {
                                Integer num4 = num3;
                                Integer num5 = num2;
                                DevicePolicyCache devicePolicyCache = DevicePolicyCache.getInstance();
                                if (devicePolicyCache instanceof DevicePolicyCacheImpl) {
                                    ((DevicePolicyCacheImpl) devicePolicyCache).setContentProtectionPolicy(num4.intValue(), num5);
                                }
                            }
                        });
                        return Boolean.TRUE;
                    default:
                        ((Integer) obj3).intValue();
                        String[] strArr3 = DevicePolicyManagerService.DELEGATIONS;
                        Slogf.w("PolicyEnforcerCallbacks", "Trying to enforce setPermissionGrantState while flag is off.");
                        return Boolean.TRUE;
                }
            }
        }, new BooleanPolicySerializer());
        GENERIC_ACCOUNT_MANAGEMENT_DISABLED = policyDefinition13;
        final int i14 = 13;
        PolicyDefinition policyDefinition14 = new PolicyDefinition(new NoArgsPolicyKey("permittedInputMethods"), new MostRecent(), 6, new QuadFunction() { // from class: com.android.server.devicepolicy.PolicyDefinition$$ExternalSyntheticLambda0
            public final Object apply(Object obj, Object obj2, Object obj3, Object obj4) {
                final int i22;
                int i32 = 0;
                switch (i14) {
                    case 0:
                        String[] strArr = DevicePolicyManagerService.DELEGATIONS;
                        Slogf.w("PolicyEnforcerCallbacks", "Trying to enforce setAutoTimezoneEnabled while flag is off.");
                        return Boolean.TRUE;
                    case 1:
                        ((Integer) obj3).intValue();
                        DevicePolicyManagerInternal devicePolicyManagerInternal = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
                        Boolean bool = Boolean.TRUE;
                        devicePolicyManagerInternal.enforceSecurityLoggingPolicy(bool.equals((Boolean) obj));
                        return bool;
                    case 2:
                        ((Integer) obj3).intValue();
                        DevicePolicyManagerInternal devicePolicyManagerInternal2 = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
                        Boolean bool2 = Boolean.TRUE;
                        devicePolicyManagerInternal2.enforceAuditLoggingPolicy(bool2.equals((Boolean) obj));
                        return bool2;
                    case 3:
                        LockTaskPolicy lockTaskPolicy = (LockTaskPolicy) obj;
                        Context context = (Context) obj2;
                        final int intValue = ((Integer) obj3).intValue();
                        List emptyList = Collections.emptyList();
                        if (lockTaskPolicy != null) {
                            emptyList = List.copyOf(lockTaskPolicy.getPackages());
                            i22 = lockTaskPolicy.getFlags();
                        } else {
                            i22 = 16;
                        }
                        String[] strArr2 = DevicePolicyManagerService.DELEGATIONS;
                        Binder.withCleanCallingIdentity(new DevicePolicyManagerService$$ExternalSyntheticLambda101(intValue, context, emptyList));
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.DevicePolicyManagerService$$ExternalSyntheticLambda204
                            public final void runOrThrow() {
                                try {
                                    ActivityTaskManager.getService().updateLockTaskFeatures(intValue, i22);
                                } catch (RemoteException e) {
                                    Slog.wtf("DevicePolicyManager", "Remote Exception: ", e);
                                }
                            }
                        });
                        return Boolean.TRUE;
                    case 4:
                        Set set = (Set) obj;
                        Context context2 = (Context) obj2;
                        int intValue2 = ((Integer) obj3).intValue();
                        ProtectedPackagesFilter protectedPackagesFilter = ProtectedPackagesFilter.ProtectedPackagesFilterHolder.INSTANCE;
                        List<String> list = set == null ? null : set.stream().toList();
                        protectedPackagesFilter.getClass();
                        Slog.d("ProtectedPackagesFilter", "updateProtectedPackages is called. userId " + intValue2 + " package names : " + list);
                        synchronized (protectedPackagesFilter.mProtectedPackages) {
                            try {
                                if (list == null) {
                                    protectedPackagesFilter.mProtectedPackages.remove(intValue2);
                                } else {
                                    protectedPackagesFilter.mProtectedPackages.put(intValue2, new ArraySet(list));
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        if (list != null) {
                            for (String str : list) {
                                boolean z = MARsPolicyManager.MARs_ENABLE;
                                if (!MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.cancelDisablePolicy(str, intValue2, 0)) {
                                    BootReceiver$$ExternalSyntheticOutline0.m("cancelDisablePolicy failed. package : ", str, "MARsPolicyManager");
                                }
                            }
                        }
                        Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(context2, intValue2, set));
                        return Boolean.TRUE;
                    case 5:
                        Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(((Integer) obj3).intValue(), 2, (PolicyKey) obj4, (ComponentName) obj));
                        return Boolean.TRUE;
                    case 6:
                        return Boolean.valueOf(Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda3((PolicyKey) obj4, (Boolean) obj, ((Integer) obj3).intValue(), 1))));
                    case 7:
                        final Context context3 = (Context) obj2;
                        final Integer num = (Integer) obj3;
                        final PolicyKey policyKey = (PolicyKey) obj4;
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda4
                            public final void runOrThrow() {
                                PackagePolicyKey packagePolicyKey = policyKey;
                                Context context4 = context3;
                                Integer num2 = num;
                                String packageName = packagePolicyKey.getPackageName();
                                Objects.requireNonNull(packageName);
                                Intent intent = new Intent("android.intent.action.APPLICATION_RESTRICTIONS_CHANGED");
                                intent.setPackage(packageName);
                                intent.addFlags(1073741824);
                                context4.sendBroadcastAsUser(intent, UserHandle.of(num2.intValue()));
                            }
                        });
                        return Boolean.TRUE;
                    case 8:
                        return Boolean.TRUE;
                    case 9:
                        return Boolean.valueOf(Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda3((PolicyKey) obj4, ((Integer) obj3).intValue(), (Boolean) obj))));
                    case 10:
                        return Boolean.TRUE;
                    case 11:
                        return Boolean.valueOf(Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda3((PolicyKey) obj4, (Boolean) obj, ((Integer) obj3).intValue(), i32))));
                    case 12:
                        return Boolean.TRUE;
                    case 13:
                        return Boolean.TRUE;
                    case 14:
                        final Boolean bool3 = (Boolean) obj;
                        final int intValue3 = ((Integer) obj3).intValue();
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda1
                            public final void runOrThrow() {
                                int i42 = intValue3;
                                Boolean bool4 = bool3;
                                DevicePolicyCache devicePolicyCache = DevicePolicyCache.getInstance();
                                if (devicePolicyCache instanceof DevicePolicyCacheImpl) {
                                    DevicePolicyCacheImpl devicePolicyCacheImpl = (DevicePolicyCacheImpl) devicePolicyCache;
                                    boolean z2 = bool4 != null && bool4.booleanValue();
                                    synchronized (devicePolicyCacheImpl.mLock) {
                                        try {
                                            if (z2) {
                                                ((HashSet) devicePolicyCacheImpl.mScreenCaptureDisallowedUsers).add(Integer.valueOf(i42));
                                            } else {
                                                ((HashSet) devicePolicyCacheImpl.mScreenCaptureDisallowedUsers).remove(Integer.valueOf(i42));
                                            }
                                        } catch (Throwable th2) {
                                            throw th2;
                                        }
                                    }
                                    BackgroundThread.getHandler().post(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda10());
                                }
                            }
                        });
                        return Boolean.TRUE;
                    case 15:
                        Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(((Integer) obj3).intValue(), i32, (Boolean) obj, (Context) obj2));
                        return Boolean.TRUE;
                    case 16:
                        final Boolean bool4 = (Boolean) obj;
                        final Context context4 = (Context) obj2;
                        Boolean bool5 = (Boolean) Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda2
                            public final Object getOrThrow() {
                                Context context5 = context4;
                                Boolean bool6 = bool4;
                                Objects.requireNonNull(context5);
                                DevicePolicyManagerService.updateUsbDataSignal(context5, bool6 == null || bool6.booleanValue());
                                return Boolean.TRUE;
                            }
                        });
                        bool5.getClass();
                        return bool5;
                    case 17:
                        final Integer num2 = (Integer) obj;
                        final Integer num3 = (Integer) obj3;
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda6
                            public final void runOrThrow() {
                                Integer num4 = num3;
                                Integer num5 = num2;
                                DevicePolicyCache devicePolicyCache = DevicePolicyCache.getInstance();
                                if (devicePolicyCache instanceof DevicePolicyCacheImpl) {
                                    ((DevicePolicyCacheImpl) devicePolicyCache).setContentProtectionPolicy(num4.intValue(), num5);
                                }
                            }
                        });
                        return Boolean.TRUE;
                    default:
                        ((Integer) obj3).intValue();
                        String[] strArr3 = DevicePolicyManagerService.DELEGATIONS;
                        Slogf.w("PolicyEnforcerCallbacks", "Trying to enforce setPermissionGrantState while flag is off.");
                        return Boolean.TRUE;
                }
            }
        }, new PackageSetPolicySerializer());
        PERMITTED_INPUT_METHODS = policyDefinition14;
        final int i15 = 14;
        PolicyDefinition policyDefinition15 = new PolicyDefinition(new NoArgsPolicyKey("screenCaptureDisabled"), mostRestrictive2, 4, new QuadFunction() { // from class: com.android.server.devicepolicy.PolicyDefinition$$ExternalSyntheticLambda0
            public final Object apply(Object obj, Object obj2, Object obj3, Object obj4) {
                final int i22;
                int i32 = 0;
                switch (i15) {
                    case 0:
                        String[] strArr = DevicePolicyManagerService.DELEGATIONS;
                        Slogf.w("PolicyEnforcerCallbacks", "Trying to enforce setAutoTimezoneEnabled while flag is off.");
                        return Boolean.TRUE;
                    case 1:
                        ((Integer) obj3).intValue();
                        DevicePolicyManagerInternal devicePolicyManagerInternal = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
                        Boolean bool = Boolean.TRUE;
                        devicePolicyManagerInternal.enforceSecurityLoggingPolicy(bool.equals((Boolean) obj));
                        return bool;
                    case 2:
                        ((Integer) obj3).intValue();
                        DevicePolicyManagerInternal devicePolicyManagerInternal2 = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
                        Boolean bool2 = Boolean.TRUE;
                        devicePolicyManagerInternal2.enforceAuditLoggingPolicy(bool2.equals((Boolean) obj));
                        return bool2;
                    case 3:
                        LockTaskPolicy lockTaskPolicy = (LockTaskPolicy) obj;
                        Context context = (Context) obj2;
                        final int intValue = ((Integer) obj3).intValue();
                        List emptyList = Collections.emptyList();
                        if (lockTaskPolicy != null) {
                            emptyList = List.copyOf(lockTaskPolicy.getPackages());
                            i22 = lockTaskPolicy.getFlags();
                        } else {
                            i22 = 16;
                        }
                        String[] strArr2 = DevicePolicyManagerService.DELEGATIONS;
                        Binder.withCleanCallingIdentity(new DevicePolicyManagerService$$ExternalSyntheticLambda101(intValue, context, emptyList));
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.DevicePolicyManagerService$$ExternalSyntheticLambda204
                            public final void runOrThrow() {
                                try {
                                    ActivityTaskManager.getService().updateLockTaskFeatures(intValue, i22);
                                } catch (RemoteException e) {
                                    Slog.wtf("DevicePolicyManager", "Remote Exception: ", e);
                                }
                            }
                        });
                        return Boolean.TRUE;
                    case 4:
                        Set set = (Set) obj;
                        Context context2 = (Context) obj2;
                        int intValue2 = ((Integer) obj3).intValue();
                        ProtectedPackagesFilter protectedPackagesFilter = ProtectedPackagesFilter.ProtectedPackagesFilterHolder.INSTANCE;
                        List<String> list = set == null ? null : set.stream().toList();
                        protectedPackagesFilter.getClass();
                        Slog.d("ProtectedPackagesFilter", "updateProtectedPackages is called. userId " + intValue2 + " package names : " + list);
                        synchronized (protectedPackagesFilter.mProtectedPackages) {
                            try {
                                if (list == null) {
                                    protectedPackagesFilter.mProtectedPackages.remove(intValue2);
                                } else {
                                    protectedPackagesFilter.mProtectedPackages.put(intValue2, new ArraySet(list));
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        if (list != null) {
                            for (String str : list) {
                                boolean z = MARsPolicyManager.MARs_ENABLE;
                                if (!MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.cancelDisablePolicy(str, intValue2, 0)) {
                                    BootReceiver$$ExternalSyntheticOutline0.m("cancelDisablePolicy failed. package : ", str, "MARsPolicyManager");
                                }
                            }
                        }
                        Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(context2, intValue2, set));
                        return Boolean.TRUE;
                    case 5:
                        Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(((Integer) obj3).intValue(), 2, (PolicyKey) obj4, (ComponentName) obj));
                        return Boolean.TRUE;
                    case 6:
                        return Boolean.valueOf(Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda3((PolicyKey) obj4, (Boolean) obj, ((Integer) obj3).intValue(), 1))));
                    case 7:
                        final Context context3 = (Context) obj2;
                        final Integer num = (Integer) obj3;
                        final PolicyKey policyKey = (PolicyKey) obj4;
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda4
                            public final void runOrThrow() {
                                PackagePolicyKey packagePolicyKey = policyKey;
                                Context context4 = context3;
                                Integer num2 = num;
                                String packageName = packagePolicyKey.getPackageName();
                                Objects.requireNonNull(packageName);
                                Intent intent = new Intent("android.intent.action.APPLICATION_RESTRICTIONS_CHANGED");
                                intent.setPackage(packageName);
                                intent.addFlags(1073741824);
                                context4.sendBroadcastAsUser(intent, UserHandle.of(num2.intValue()));
                            }
                        });
                        return Boolean.TRUE;
                    case 8:
                        return Boolean.TRUE;
                    case 9:
                        return Boolean.valueOf(Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda3((PolicyKey) obj4, ((Integer) obj3).intValue(), (Boolean) obj))));
                    case 10:
                        return Boolean.TRUE;
                    case 11:
                        return Boolean.valueOf(Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda3((PolicyKey) obj4, (Boolean) obj, ((Integer) obj3).intValue(), i32))));
                    case 12:
                        return Boolean.TRUE;
                    case 13:
                        return Boolean.TRUE;
                    case 14:
                        final Boolean bool3 = (Boolean) obj;
                        final int intValue3 = ((Integer) obj3).intValue();
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda1
                            public final void runOrThrow() {
                                int i42 = intValue3;
                                Boolean bool4 = bool3;
                                DevicePolicyCache devicePolicyCache = DevicePolicyCache.getInstance();
                                if (devicePolicyCache instanceof DevicePolicyCacheImpl) {
                                    DevicePolicyCacheImpl devicePolicyCacheImpl = (DevicePolicyCacheImpl) devicePolicyCache;
                                    boolean z2 = bool4 != null && bool4.booleanValue();
                                    synchronized (devicePolicyCacheImpl.mLock) {
                                        try {
                                            if (z2) {
                                                ((HashSet) devicePolicyCacheImpl.mScreenCaptureDisallowedUsers).add(Integer.valueOf(i42));
                                            } else {
                                                ((HashSet) devicePolicyCacheImpl.mScreenCaptureDisallowedUsers).remove(Integer.valueOf(i42));
                                            }
                                        } catch (Throwable th2) {
                                            throw th2;
                                        }
                                    }
                                    BackgroundThread.getHandler().post(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda10());
                                }
                            }
                        });
                        return Boolean.TRUE;
                    case 15:
                        Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(((Integer) obj3).intValue(), i32, (Boolean) obj, (Context) obj2));
                        return Boolean.TRUE;
                    case 16:
                        final Boolean bool4 = (Boolean) obj;
                        final Context context4 = (Context) obj2;
                        Boolean bool5 = (Boolean) Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda2
                            public final Object getOrThrow() {
                                Context context5 = context4;
                                Boolean bool6 = bool4;
                                Objects.requireNonNull(context5);
                                DevicePolicyManagerService.updateUsbDataSignal(context5, bool6 == null || bool6.booleanValue());
                                return Boolean.TRUE;
                            }
                        });
                        bool5.getClass();
                        return bool5;
                    case 17:
                        final Integer num2 = (Integer) obj;
                        final Integer num3 = (Integer) obj3;
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda6
                            public final void runOrThrow() {
                                Integer num4 = num3;
                                Integer num5 = num2;
                                DevicePolicyCache devicePolicyCache = DevicePolicyCache.getInstance();
                                if (devicePolicyCache instanceof DevicePolicyCacheImpl) {
                                    ((DevicePolicyCacheImpl) devicePolicyCache).setContentProtectionPolicy(num4.intValue(), num5);
                                }
                            }
                        });
                        return Boolean.TRUE;
                    default:
                        ((Integer) obj3).intValue();
                        String[] strArr3 = DevicePolicyManagerService.DELEGATIONS;
                        Slogf.w("PolicyEnforcerCallbacks", "Trying to enforce setPermissionGrantState while flag is off.");
                        return Boolean.TRUE;
                }
            }
        }, new BooleanPolicySerializer());
        SCREEN_CAPTURE_DISABLED = policyDefinition15;
        final int i16 = 15;
        PolicyDefinition policyDefinition16 = new PolicyDefinition(new NoArgsPolicyKey("personalAppsSuspended"), new MostRecent(), 6, new QuadFunction() { // from class: com.android.server.devicepolicy.PolicyDefinition$$ExternalSyntheticLambda0
            public final Object apply(Object obj, Object obj2, Object obj3, Object obj4) {
                final int i22;
                int i32 = 0;
                switch (i16) {
                    case 0:
                        String[] strArr = DevicePolicyManagerService.DELEGATIONS;
                        Slogf.w("PolicyEnforcerCallbacks", "Trying to enforce setAutoTimezoneEnabled while flag is off.");
                        return Boolean.TRUE;
                    case 1:
                        ((Integer) obj3).intValue();
                        DevicePolicyManagerInternal devicePolicyManagerInternal = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
                        Boolean bool = Boolean.TRUE;
                        devicePolicyManagerInternal.enforceSecurityLoggingPolicy(bool.equals((Boolean) obj));
                        return bool;
                    case 2:
                        ((Integer) obj3).intValue();
                        DevicePolicyManagerInternal devicePolicyManagerInternal2 = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
                        Boolean bool2 = Boolean.TRUE;
                        devicePolicyManagerInternal2.enforceAuditLoggingPolicy(bool2.equals((Boolean) obj));
                        return bool2;
                    case 3:
                        LockTaskPolicy lockTaskPolicy = (LockTaskPolicy) obj;
                        Context context = (Context) obj2;
                        final int intValue = ((Integer) obj3).intValue();
                        List emptyList = Collections.emptyList();
                        if (lockTaskPolicy != null) {
                            emptyList = List.copyOf(lockTaskPolicy.getPackages());
                            i22 = lockTaskPolicy.getFlags();
                        } else {
                            i22 = 16;
                        }
                        String[] strArr2 = DevicePolicyManagerService.DELEGATIONS;
                        Binder.withCleanCallingIdentity(new DevicePolicyManagerService$$ExternalSyntheticLambda101(intValue, context, emptyList));
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.DevicePolicyManagerService$$ExternalSyntheticLambda204
                            public final void runOrThrow() {
                                try {
                                    ActivityTaskManager.getService().updateLockTaskFeatures(intValue, i22);
                                } catch (RemoteException e) {
                                    Slog.wtf("DevicePolicyManager", "Remote Exception: ", e);
                                }
                            }
                        });
                        return Boolean.TRUE;
                    case 4:
                        Set set = (Set) obj;
                        Context context2 = (Context) obj2;
                        int intValue2 = ((Integer) obj3).intValue();
                        ProtectedPackagesFilter protectedPackagesFilter = ProtectedPackagesFilter.ProtectedPackagesFilterHolder.INSTANCE;
                        List<String> list = set == null ? null : set.stream().toList();
                        protectedPackagesFilter.getClass();
                        Slog.d("ProtectedPackagesFilter", "updateProtectedPackages is called. userId " + intValue2 + " package names : " + list);
                        synchronized (protectedPackagesFilter.mProtectedPackages) {
                            try {
                                if (list == null) {
                                    protectedPackagesFilter.mProtectedPackages.remove(intValue2);
                                } else {
                                    protectedPackagesFilter.mProtectedPackages.put(intValue2, new ArraySet(list));
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        if (list != null) {
                            for (String str : list) {
                                boolean z = MARsPolicyManager.MARs_ENABLE;
                                if (!MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.cancelDisablePolicy(str, intValue2, 0)) {
                                    BootReceiver$$ExternalSyntheticOutline0.m("cancelDisablePolicy failed. package : ", str, "MARsPolicyManager");
                                }
                            }
                        }
                        Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(context2, intValue2, set));
                        return Boolean.TRUE;
                    case 5:
                        Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(((Integer) obj3).intValue(), 2, (PolicyKey) obj4, (ComponentName) obj));
                        return Boolean.TRUE;
                    case 6:
                        return Boolean.valueOf(Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda3((PolicyKey) obj4, (Boolean) obj, ((Integer) obj3).intValue(), 1))));
                    case 7:
                        final Context context3 = (Context) obj2;
                        final Integer num = (Integer) obj3;
                        final PolicyKey policyKey = (PolicyKey) obj4;
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda4
                            public final void runOrThrow() {
                                PackagePolicyKey packagePolicyKey = policyKey;
                                Context context4 = context3;
                                Integer num2 = num;
                                String packageName = packagePolicyKey.getPackageName();
                                Objects.requireNonNull(packageName);
                                Intent intent = new Intent("android.intent.action.APPLICATION_RESTRICTIONS_CHANGED");
                                intent.setPackage(packageName);
                                intent.addFlags(1073741824);
                                context4.sendBroadcastAsUser(intent, UserHandle.of(num2.intValue()));
                            }
                        });
                        return Boolean.TRUE;
                    case 8:
                        return Boolean.TRUE;
                    case 9:
                        return Boolean.valueOf(Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda3((PolicyKey) obj4, ((Integer) obj3).intValue(), (Boolean) obj))));
                    case 10:
                        return Boolean.TRUE;
                    case 11:
                        return Boolean.valueOf(Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda3((PolicyKey) obj4, (Boolean) obj, ((Integer) obj3).intValue(), i32))));
                    case 12:
                        return Boolean.TRUE;
                    case 13:
                        return Boolean.TRUE;
                    case 14:
                        final Boolean bool3 = (Boolean) obj;
                        final int intValue3 = ((Integer) obj3).intValue();
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda1
                            public final void runOrThrow() {
                                int i42 = intValue3;
                                Boolean bool4 = bool3;
                                DevicePolicyCache devicePolicyCache = DevicePolicyCache.getInstance();
                                if (devicePolicyCache instanceof DevicePolicyCacheImpl) {
                                    DevicePolicyCacheImpl devicePolicyCacheImpl = (DevicePolicyCacheImpl) devicePolicyCache;
                                    boolean z2 = bool4 != null && bool4.booleanValue();
                                    synchronized (devicePolicyCacheImpl.mLock) {
                                        try {
                                            if (z2) {
                                                ((HashSet) devicePolicyCacheImpl.mScreenCaptureDisallowedUsers).add(Integer.valueOf(i42));
                                            } else {
                                                ((HashSet) devicePolicyCacheImpl.mScreenCaptureDisallowedUsers).remove(Integer.valueOf(i42));
                                            }
                                        } catch (Throwable th2) {
                                            throw th2;
                                        }
                                    }
                                    BackgroundThread.getHandler().post(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda10());
                                }
                            }
                        });
                        return Boolean.TRUE;
                    case 15:
                        Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(((Integer) obj3).intValue(), i32, (Boolean) obj, (Context) obj2));
                        return Boolean.TRUE;
                    case 16:
                        final Boolean bool4 = (Boolean) obj;
                        final Context context4 = (Context) obj2;
                        Boolean bool5 = (Boolean) Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda2
                            public final Object getOrThrow() {
                                Context context5 = context4;
                                Boolean bool6 = bool4;
                                Objects.requireNonNull(context5);
                                DevicePolicyManagerService.updateUsbDataSignal(context5, bool6 == null || bool6.booleanValue());
                                return Boolean.TRUE;
                            }
                        });
                        bool5.getClass();
                        return bool5;
                    case 17:
                        final Integer num2 = (Integer) obj;
                        final Integer num3 = (Integer) obj3;
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda6
                            public final void runOrThrow() {
                                Integer num4 = num3;
                                Integer num5 = num2;
                                DevicePolicyCache devicePolicyCache = DevicePolicyCache.getInstance();
                                if (devicePolicyCache instanceof DevicePolicyCacheImpl) {
                                    ((DevicePolicyCacheImpl) devicePolicyCache).setContentProtectionPolicy(num4.intValue(), num5);
                                }
                            }
                        });
                        return Boolean.TRUE;
                    default:
                        ((Integer) obj3).intValue();
                        String[] strArr3 = DevicePolicyManagerService.DELEGATIONS;
                        Slogf.w("PolicyEnforcerCallbacks", "Trying to enforce setPermissionGrantState while flag is off.");
                        return Boolean.TRUE;
                }
            }
        }, new BooleanPolicySerializer());
        PERSONAL_APPS_SUSPENDED = policyDefinition16;
        final int i17 = 16;
        PolicyDefinition policyDefinition17 = new PolicyDefinition(new NoArgsPolicyKey("usbDataSignaling"), mostRestrictive, 1, new QuadFunction() { // from class: com.android.server.devicepolicy.PolicyDefinition$$ExternalSyntheticLambda0
            public final Object apply(Object obj, Object obj2, Object obj3, Object obj4) {
                final int i22;
                int i32 = 0;
                switch (i17) {
                    case 0:
                        String[] strArr = DevicePolicyManagerService.DELEGATIONS;
                        Slogf.w("PolicyEnforcerCallbacks", "Trying to enforce setAutoTimezoneEnabled while flag is off.");
                        return Boolean.TRUE;
                    case 1:
                        ((Integer) obj3).intValue();
                        DevicePolicyManagerInternal devicePolicyManagerInternal = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
                        Boolean bool = Boolean.TRUE;
                        devicePolicyManagerInternal.enforceSecurityLoggingPolicy(bool.equals((Boolean) obj));
                        return bool;
                    case 2:
                        ((Integer) obj3).intValue();
                        DevicePolicyManagerInternal devicePolicyManagerInternal2 = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
                        Boolean bool2 = Boolean.TRUE;
                        devicePolicyManagerInternal2.enforceAuditLoggingPolicy(bool2.equals((Boolean) obj));
                        return bool2;
                    case 3:
                        LockTaskPolicy lockTaskPolicy = (LockTaskPolicy) obj;
                        Context context = (Context) obj2;
                        final int intValue = ((Integer) obj3).intValue();
                        List emptyList = Collections.emptyList();
                        if (lockTaskPolicy != null) {
                            emptyList = List.copyOf(lockTaskPolicy.getPackages());
                            i22 = lockTaskPolicy.getFlags();
                        } else {
                            i22 = 16;
                        }
                        String[] strArr2 = DevicePolicyManagerService.DELEGATIONS;
                        Binder.withCleanCallingIdentity(new DevicePolicyManagerService$$ExternalSyntheticLambda101(intValue, context, emptyList));
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.DevicePolicyManagerService$$ExternalSyntheticLambda204
                            public final void runOrThrow() {
                                try {
                                    ActivityTaskManager.getService().updateLockTaskFeatures(intValue, i22);
                                } catch (RemoteException e) {
                                    Slog.wtf("DevicePolicyManager", "Remote Exception: ", e);
                                }
                            }
                        });
                        return Boolean.TRUE;
                    case 4:
                        Set set = (Set) obj;
                        Context context2 = (Context) obj2;
                        int intValue2 = ((Integer) obj3).intValue();
                        ProtectedPackagesFilter protectedPackagesFilter = ProtectedPackagesFilter.ProtectedPackagesFilterHolder.INSTANCE;
                        List<String> list = set == null ? null : set.stream().toList();
                        protectedPackagesFilter.getClass();
                        Slog.d("ProtectedPackagesFilter", "updateProtectedPackages is called. userId " + intValue2 + " package names : " + list);
                        synchronized (protectedPackagesFilter.mProtectedPackages) {
                            try {
                                if (list == null) {
                                    protectedPackagesFilter.mProtectedPackages.remove(intValue2);
                                } else {
                                    protectedPackagesFilter.mProtectedPackages.put(intValue2, new ArraySet(list));
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        if (list != null) {
                            for (String str : list) {
                                boolean z = MARsPolicyManager.MARs_ENABLE;
                                if (!MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.cancelDisablePolicy(str, intValue2, 0)) {
                                    BootReceiver$$ExternalSyntheticOutline0.m("cancelDisablePolicy failed. package : ", str, "MARsPolicyManager");
                                }
                            }
                        }
                        Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(context2, intValue2, set));
                        return Boolean.TRUE;
                    case 5:
                        Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(((Integer) obj3).intValue(), 2, (PolicyKey) obj4, (ComponentName) obj));
                        return Boolean.TRUE;
                    case 6:
                        return Boolean.valueOf(Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda3((PolicyKey) obj4, (Boolean) obj, ((Integer) obj3).intValue(), 1))));
                    case 7:
                        final Context context3 = (Context) obj2;
                        final Integer num = (Integer) obj3;
                        final PolicyKey policyKey = (PolicyKey) obj4;
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda4
                            public final void runOrThrow() {
                                PackagePolicyKey packagePolicyKey = policyKey;
                                Context context4 = context3;
                                Integer num2 = num;
                                String packageName = packagePolicyKey.getPackageName();
                                Objects.requireNonNull(packageName);
                                Intent intent = new Intent("android.intent.action.APPLICATION_RESTRICTIONS_CHANGED");
                                intent.setPackage(packageName);
                                intent.addFlags(1073741824);
                                context4.sendBroadcastAsUser(intent, UserHandle.of(num2.intValue()));
                            }
                        });
                        return Boolean.TRUE;
                    case 8:
                        return Boolean.TRUE;
                    case 9:
                        return Boolean.valueOf(Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda3((PolicyKey) obj4, ((Integer) obj3).intValue(), (Boolean) obj))));
                    case 10:
                        return Boolean.TRUE;
                    case 11:
                        return Boolean.valueOf(Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda3((PolicyKey) obj4, (Boolean) obj, ((Integer) obj3).intValue(), i32))));
                    case 12:
                        return Boolean.TRUE;
                    case 13:
                        return Boolean.TRUE;
                    case 14:
                        final Boolean bool3 = (Boolean) obj;
                        final int intValue3 = ((Integer) obj3).intValue();
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda1
                            public final void runOrThrow() {
                                int i42 = intValue3;
                                Boolean bool4 = bool3;
                                DevicePolicyCache devicePolicyCache = DevicePolicyCache.getInstance();
                                if (devicePolicyCache instanceof DevicePolicyCacheImpl) {
                                    DevicePolicyCacheImpl devicePolicyCacheImpl = (DevicePolicyCacheImpl) devicePolicyCache;
                                    boolean z2 = bool4 != null && bool4.booleanValue();
                                    synchronized (devicePolicyCacheImpl.mLock) {
                                        try {
                                            if (z2) {
                                                ((HashSet) devicePolicyCacheImpl.mScreenCaptureDisallowedUsers).add(Integer.valueOf(i42));
                                            } else {
                                                ((HashSet) devicePolicyCacheImpl.mScreenCaptureDisallowedUsers).remove(Integer.valueOf(i42));
                                            }
                                        } catch (Throwable th2) {
                                            throw th2;
                                        }
                                    }
                                    BackgroundThread.getHandler().post(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda10());
                                }
                            }
                        });
                        return Boolean.TRUE;
                    case 15:
                        Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(((Integer) obj3).intValue(), i32, (Boolean) obj, (Context) obj2));
                        return Boolean.TRUE;
                    case 16:
                        final Boolean bool4 = (Boolean) obj;
                        final Context context4 = (Context) obj2;
                        Boolean bool5 = (Boolean) Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda2
                            public final Object getOrThrow() {
                                Context context5 = context4;
                                Boolean bool6 = bool4;
                                Objects.requireNonNull(context5);
                                DevicePolicyManagerService.updateUsbDataSignal(context5, bool6 == null || bool6.booleanValue());
                                return Boolean.TRUE;
                            }
                        });
                        bool5.getClass();
                        return bool5;
                    case 17:
                        final Integer num2 = (Integer) obj;
                        final Integer num3 = (Integer) obj3;
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda6
                            public final void runOrThrow() {
                                Integer num4 = num3;
                                Integer num5 = num2;
                                DevicePolicyCache devicePolicyCache = DevicePolicyCache.getInstance();
                                if (devicePolicyCache instanceof DevicePolicyCacheImpl) {
                                    ((DevicePolicyCacheImpl) devicePolicyCache).setContentProtectionPolicy(num4.intValue(), num5);
                                }
                            }
                        });
                        return Boolean.TRUE;
                    default:
                        ((Integer) obj3).intValue();
                        String[] strArr3 = DevicePolicyManagerService.DELEGATIONS;
                        Slogf.w("PolicyEnforcerCallbacks", "Trying to enforce setPermissionGrantState while flag is off.");
                        return Boolean.TRUE;
                }
            }
        }, new BooleanPolicySerializer());
        USB_DATA_SIGNALING = policyDefinition17;
        final int i18 = 17;
        PolicyDefinition policyDefinition18 = new PolicyDefinition(new NoArgsPolicyKey("contentProtection"), new MostRecent(), 2, new QuadFunction() { // from class: com.android.server.devicepolicy.PolicyDefinition$$ExternalSyntheticLambda0
            public final Object apply(Object obj, Object obj2, Object obj3, Object obj4) {
                final int i22;
                int i32 = 0;
                switch (i18) {
                    case 0:
                        String[] strArr = DevicePolicyManagerService.DELEGATIONS;
                        Slogf.w("PolicyEnforcerCallbacks", "Trying to enforce setAutoTimezoneEnabled while flag is off.");
                        return Boolean.TRUE;
                    case 1:
                        ((Integer) obj3).intValue();
                        DevicePolicyManagerInternal devicePolicyManagerInternal = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
                        Boolean bool = Boolean.TRUE;
                        devicePolicyManagerInternal.enforceSecurityLoggingPolicy(bool.equals((Boolean) obj));
                        return bool;
                    case 2:
                        ((Integer) obj3).intValue();
                        DevicePolicyManagerInternal devicePolicyManagerInternal2 = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
                        Boolean bool2 = Boolean.TRUE;
                        devicePolicyManagerInternal2.enforceAuditLoggingPolicy(bool2.equals((Boolean) obj));
                        return bool2;
                    case 3:
                        LockTaskPolicy lockTaskPolicy = (LockTaskPolicy) obj;
                        Context context = (Context) obj2;
                        final int intValue = ((Integer) obj3).intValue();
                        List emptyList = Collections.emptyList();
                        if (lockTaskPolicy != null) {
                            emptyList = List.copyOf(lockTaskPolicy.getPackages());
                            i22 = lockTaskPolicy.getFlags();
                        } else {
                            i22 = 16;
                        }
                        String[] strArr2 = DevicePolicyManagerService.DELEGATIONS;
                        Binder.withCleanCallingIdentity(new DevicePolicyManagerService$$ExternalSyntheticLambda101(intValue, context, emptyList));
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.DevicePolicyManagerService$$ExternalSyntheticLambda204
                            public final void runOrThrow() {
                                try {
                                    ActivityTaskManager.getService().updateLockTaskFeatures(intValue, i22);
                                } catch (RemoteException e) {
                                    Slog.wtf("DevicePolicyManager", "Remote Exception: ", e);
                                }
                            }
                        });
                        return Boolean.TRUE;
                    case 4:
                        Set set = (Set) obj;
                        Context context2 = (Context) obj2;
                        int intValue2 = ((Integer) obj3).intValue();
                        ProtectedPackagesFilter protectedPackagesFilter = ProtectedPackagesFilter.ProtectedPackagesFilterHolder.INSTANCE;
                        List<String> list = set == null ? null : set.stream().toList();
                        protectedPackagesFilter.getClass();
                        Slog.d("ProtectedPackagesFilter", "updateProtectedPackages is called. userId " + intValue2 + " package names : " + list);
                        synchronized (protectedPackagesFilter.mProtectedPackages) {
                            try {
                                if (list == null) {
                                    protectedPackagesFilter.mProtectedPackages.remove(intValue2);
                                } else {
                                    protectedPackagesFilter.mProtectedPackages.put(intValue2, new ArraySet(list));
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        if (list != null) {
                            for (String str : list) {
                                boolean z = MARsPolicyManager.MARs_ENABLE;
                                if (!MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.cancelDisablePolicy(str, intValue2, 0)) {
                                    BootReceiver$$ExternalSyntheticOutline0.m("cancelDisablePolicy failed. package : ", str, "MARsPolicyManager");
                                }
                            }
                        }
                        Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(context2, intValue2, set));
                        return Boolean.TRUE;
                    case 5:
                        Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(((Integer) obj3).intValue(), 2, (PolicyKey) obj4, (ComponentName) obj));
                        return Boolean.TRUE;
                    case 6:
                        return Boolean.valueOf(Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda3((PolicyKey) obj4, (Boolean) obj, ((Integer) obj3).intValue(), 1))));
                    case 7:
                        final Context context3 = (Context) obj2;
                        final Integer num = (Integer) obj3;
                        final PolicyKey policyKey = (PolicyKey) obj4;
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda4
                            public final void runOrThrow() {
                                PackagePolicyKey packagePolicyKey = policyKey;
                                Context context4 = context3;
                                Integer num2 = num;
                                String packageName = packagePolicyKey.getPackageName();
                                Objects.requireNonNull(packageName);
                                Intent intent = new Intent("android.intent.action.APPLICATION_RESTRICTIONS_CHANGED");
                                intent.setPackage(packageName);
                                intent.addFlags(1073741824);
                                context4.sendBroadcastAsUser(intent, UserHandle.of(num2.intValue()));
                            }
                        });
                        return Boolean.TRUE;
                    case 8:
                        return Boolean.TRUE;
                    case 9:
                        return Boolean.valueOf(Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda3((PolicyKey) obj4, ((Integer) obj3).intValue(), (Boolean) obj))));
                    case 10:
                        return Boolean.TRUE;
                    case 11:
                        return Boolean.valueOf(Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda3((PolicyKey) obj4, (Boolean) obj, ((Integer) obj3).intValue(), i32))));
                    case 12:
                        return Boolean.TRUE;
                    case 13:
                        return Boolean.TRUE;
                    case 14:
                        final Boolean bool3 = (Boolean) obj;
                        final int intValue3 = ((Integer) obj3).intValue();
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda1
                            public final void runOrThrow() {
                                int i42 = intValue3;
                                Boolean bool4 = bool3;
                                DevicePolicyCache devicePolicyCache = DevicePolicyCache.getInstance();
                                if (devicePolicyCache instanceof DevicePolicyCacheImpl) {
                                    DevicePolicyCacheImpl devicePolicyCacheImpl = (DevicePolicyCacheImpl) devicePolicyCache;
                                    boolean z2 = bool4 != null && bool4.booleanValue();
                                    synchronized (devicePolicyCacheImpl.mLock) {
                                        try {
                                            if (z2) {
                                                ((HashSet) devicePolicyCacheImpl.mScreenCaptureDisallowedUsers).add(Integer.valueOf(i42));
                                            } else {
                                                ((HashSet) devicePolicyCacheImpl.mScreenCaptureDisallowedUsers).remove(Integer.valueOf(i42));
                                            }
                                        } catch (Throwable th2) {
                                            throw th2;
                                        }
                                    }
                                    BackgroundThread.getHandler().post(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda10());
                                }
                            }
                        });
                        return Boolean.TRUE;
                    case 15:
                        Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(((Integer) obj3).intValue(), i32, (Boolean) obj, (Context) obj2));
                        return Boolean.TRUE;
                    case 16:
                        final Boolean bool4 = (Boolean) obj;
                        final Context context4 = (Context) obj2;
                        Boolean bool5 = (Boolean) Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda2
                            public final Object getOrThrow() {
                                Context context5 = context4;
                                Boolean bool6 = bool4;
                                Objects.requireNonNull(context5);
                                DevicePolicyManagerService.updateUsbDataSignal(context5, bool6 == null || bool6.booleanValue());
                                return Boolean.TRUE;
                            }
                        });
                        bool5.getClass();
                        return bool5;
                    case 17:
                        final Integer num2 = (Integer) obj;
                        final Integer num3 = (Integer) obj3;
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda6
                            public final void runOrThrow() {
                                Integer num4 = num3;
                                Integer num5 = num2;
                                DevicePolicyCache devicePolicyCache = DevicePolicyCache.getInstance();
                                if (devicePolicyCache instanceof DevicePolicyCacheImpl) {
                                    ((DevicePolicyCacheImpl) devicePolicyCache).setContentProtectionPolicy(num4.intValue(), num5);
                                }
                            }
                        });
                        return Boolean.TRUE;
                    default:
                        ((Integer) obj3).intValue();
                        String[] strArr3 = DevicePolicyManagerService.DELEGATIONS;
                        Slogf.w("PolicyEnforcerCallbacks", "Trying to enforce setPermissionGrantState while flag is off.");
                        return Boolean.TRUE;
                }
            }
        }, new IntegerPolicySerializer());
        CONTENT_PROTECTION = policyDefinition18;
        final int i19 = 10;
        PolicyDefinition policyDefinition19 = new PolicyDefinition(new NoArgsPolicyKey("passwordComplexity"), new MostRestrictive(List.of(new IntegerPolicyValue(327680), new IntegerPolicyValue(196608), new IntegerPolicyValue(EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT), new IntegerPolicyValue(0))), 2, new QuadFunction() { // from class: com.android.server.devicepolicy.PolicyDefinition$$ExternalSyntheticLambda0
            public final Object apply(Object obj, Object obj2, Object obj3, Object obj4) {
                final int i22;
                int i32 = 0;
                switch (i19) {
                    case 0:
                        String[] strArr = DevicePolicyManagerService.DELEGATIONS;
                        Slogf.w("PolicyEnforcerCallbacks", "Trying to enforce setAutoTimezoneEnabled while flag is off.");
                        return Boolean.TRUE;
                    case 1:
                        ((Integer) obj3).intValue();
                        DevicePolicyManagerInternal devicePolicyManagerInternal = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
                        Boolean bool = Boolean.TRUE;
                        devicePolicyManagerInternal.enforceSecurityLoggingPolicy(bool.equals((Boolean) obj));
                        return bool;
                    case 2:
                        ((Integer) obj3).intValue();
                        DevicePolicyManagerInternal devicePolicyManagerInternal2 = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
                        Boolean bool2 = Boolean.TRUE;
                        devicePolicyManagerInternal2.enforceAuditLoggingPolicy(bool2.equals((Boolean) obj));
                        return bool2;
                    case 3:
                        LockTaskPolicy lockTaskPolicy = (LockTaskPolicy) obj;
                        Context context = (Context) obj2;
                        final int intValue = ((Integer) obj3).intValue();
                        List emptyList = Collections.emptyList();
                        if (lockTaskPolicy != null) {
                            emptyList = List.copyOf(lockTaskPolicy.getPackages());
                            i22 = lockTaskPolicy.getFlags();
                        } else {
                            i22 = 16;
                        }
                        String[] strArr2 = DevicePolicyManagerService.DELEGATIONS;
                        Binder.withCleanCallingIdentity(new DevicePolicyManagerService$$ExternalSyntheticLambda101(intValue, context, emptyList));
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.DevicePolicyManagerService$$ExternalSyntheticLambda204
                            public final void runOrThrow() {
                                try {
                                    ActivityTaskManager.getService().updateLockTaskFeatures(intValue, i22);
                                } catch (RemoteException e) {
                                    Slog.wtf("DevicePolicyManager", "Remote Exception: ", e);
                                }
                            }
                        });
                        return Boolean.TRUE;
                    case 4:
                        Set set = (Set) obj;
                        Context context2 = (Context) obj2;
                        int intValue2 = ((Integer) obj3).intValue();
                        ProtectedPackagesFilter protectedPackagesFilter = ProtectedPackagesFilter.ProtectedPackagesFilterHolder.INSTANCE;
                        List<String> list = set == null ? null : set.stream().toList();
                        protectedPackagesFilter.getClass();
                        Slog.d("ProtectedPackagesFilter", "updateProtectedPackages is called. userId " + intValue2 + " package names : " + list);
                        synchronized (protectedPackagesFilter.mProtectedPackages) {
                            try {
                                if (list == null) {
                                    protectedPackagesFilter.mProtectedPackages.remove(intValue2);
                                } else {
                                    protectedPackagesFilter.mProtectedPackages.put(intValue2, new ArraySet(list));
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        if (list != null) {
                            for (String str : list) {
                                boolean z = MARsPolicyManager.MARs_ENABLE;
                                if (!MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.cancelDisablePolicy(str, intValue2, 0)) {
                                    BootReceiver$$ExternalSyntheticOutline0.m("cancelDisablePolicy failed. package : ", str, "MARsPolicyManager");
                                }
                            }
                        }
                        Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(context2, intValue2, set));
                        return Boolean.TRUE;
                    case 5:
                        Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(((Integer) obj3).intValue(), 2, (PolicyKey) obj4, (ComponentName) obj));
                        return Boolean.TRUE;
                    case 6:
                        return Boolean.valueOf(Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda3((PolicyKey) obj4, (Boolean) obj, ((Integer) obj3).intValue(), 1))));
                    case 7:
                        final Context context3 = (Context) obj2;
                        final Integer num = (Integer) obj3;
                        final PolicyKey policyKey = (PolicyKey) obj4;
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda4
                            public final void runOrThrow() {
                                PackagePolicyKey packagePolicyKey = policyKey;
                                Context context4 = context3;
                                Integer num2 = num;
                                String packageName = packagePolicyKey.getPackageName();
                                Objects.requireNonNull(packageName);
                                Intent intent = new Intent("android.intent.action.APPLICATION_RESTRICTIONS_CHANGED");
                                intent.setPackage(packageName);
                                intent.addFlags(1073741824);
                                context4.sendBroadcastAsUser(intent, UserHandle.of(num2.intValue()));
                            }
                        });
                        return Boolean.TRUE;
                    case 8:
                        return Boolean.TRUE;
                    case 9:
                        return Boolean.valueOf(Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda3((PolicyKey) obj4, ((Integer) obj3).intValue(), (Boolean) obj))));
                    case 10:
                        return Boolean.TRUE;
                    case 11:
                        return Boolean.valueOf(Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda3((PolicyKey) obj4, (Boolean) obj, ((Integer) obj3).intValue(), i32))));
                    case 12:
                        return Boolean.TRUE;
                    case 13:
                        return Boolean.TRUE;
                    case 14:
                        final Boolean bool3 = (Boolean) obj;
                        final int intValue3 = ((Integer) obj3).intValue();
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda1
                            public final void runOrThrow() {
                                int i42 = intValue3;
                                Boolean bool4 = bool3;
                                DevicePolicyCache devicePolicyCache = DevicePolicyCache.getInstance();
                                if (devicePolicyCache instanceof DevicePolicyCacheImpl) {
                                    DevicePolicyCacheImpl devicePolicyCacheImpl = (DevicePolicyCacheImpl) devicePolicyCache;
                                    boolean z2 = bool4 != null && bool4.booleanValue();
                                    synchronized (devicePolicyCacheImpl.mLock) {
                                        try {
                                            if (z2) {
                                                ((HashSet) devicePolicyCacheImpl.mScreenCaptureDisallowedUsers).add(Integer.valueOf(i42));
                                            } else {
                                                ((HashSet) devicePolicyCacheImpl.mScreenCaptureDisallowedUsers).remove(Integer.valueOf(i42));
                                            }
                                        } catch (Throwable th2) {
                                            throw th2;
                                        }
                                    }
                                    BackgroundThread.getHandler().post(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda10());
                                }
                            }
                        });
                        return Boolean.TRUE;
                    case 15:
                        Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(((Integer) obj3).intValue(), i32, (Boolean) obj, (Context) obj2));
                        return Boolean.TRUE;
                    case 16:
                        final Boolean bool4 = (Boolean) obj;
                        final Context context4 = (Context) obj2;
                        Boolean bool5 = (Boolean) Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda2
                            public final Object getOrThrow() {
                                Context context5 = context4;
                                Boolean bool6 = bool4;
                                Objects.requireNonNull(context5);
                                DevicePolicyManagerService.updateUsbDataSignal(context5, bool6 == null || bool6.booleanValue());
                                return Boolean.TRUE;
                            }
                        });
                        bool5.getClass();
                        return bool5;
                    case 17:
                        final Integer num2 = (Integer) obj;
                        final Integer num3 = (Integer) obj3;
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda6
                            public final void runOrThrow() {
                                Integer num4 = num3;
                                Integer num5 = num2;
                                DevicePolicyCache devicePolicyCache = DevicePolicyCache.getInstance();
                                if (devicePolicyCache instanceof DevicePolicyCacheImpl) {
                                    ((DevicePolicyCacheImpl) devicePolicyCache).setContentProtectionPolicy(num4.intValue(), num5);
                                }
                            }
                        });
                        return Boolean.TRUE;
                    default:
                        ((Integer) obj3).intValue();
                        String[] strArr3 = DevicePolicyManagerService.DELEGATIONS;
                        Slogf.w("PolicyEnforcerCallbacks", "Trying to enforce setPermissionGrantState while flag is off.");
                        return Boolean.TRUE;
                }
            }
        }, new IntegerPolicySerializer());
        PASSWORD_COMPLEXITY = policyDefinition19;
        final int i20 = 13;
        PolicyDefinition policyDefinition20 = new PolicyDefinition(new NoArgsPolicyKey("packagesSuspended"), new PackageSetUnion(), 0, new QuadFunction() { // from class: com.android.server.devicepolicy.PolicyDefinition$$ExternalSyntheticLambda0
            public final Object apply(Object obj, Object obj2, Object obj3, Object obj4) {
                final int i22;
                int i32 = 0;
                switch (i20) {
                    case 0:
                        String[] strArr = DevicePolicyManagerService.DELEGATIONS;
                        Slogf.w("PolicyEnforcerCallbacks", "Trying to enforce setAutoTimezoneEnabled while flag is off.");
                        return Boolean.TRUE;
                    case 1:
                        ((Integer) obj3).intValue();
                        DevicePolicyManagerInternal devicePolicyManagerInternal = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
                        Boolean bool = Boolean.TRUE;
                        devicePolicyManagerInternal.enforceSecurityLoggingPolicy(bool.equals((Boolean) obj));
                        return bool;
                    case 2:
                        ((Integer) obj3).intValue();
                        DevicePolicyManagerInternal devicePolicyManagerInternal2 = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
                        Boolean bool2 = Boolean.TRUE;
                        devicePolicyManagerInternal2.enforceAuditLoggingPolicy(bool2.equals((Boolean) obj));
                        return bool2;
                    case 3:
                        LockTaskPolicy lockTaskPolicy = (LockTaskPolicy) obj;
                        Context context = (Context) obj2;
                        final int intValue = ((Integer) obj3).intValue();
                        List emptyList = Collections.emptyList();
                        if (lockTaskPolicy != null) {
                            emptyList = List.copyOf(lockTaskPolicy.getPackages());
                            i22 = lockTaskPolicy.getFlags();
                        } else {
                            i22 = 16;
                        }
                        String[] strArr2 = DevicePolicyManagerService.DELEGATIONS;
                        Binder.withCleanCallingIdentity(new DevicePolicyManagerService$$ExternalSyntheticLambda101(intValue, context, emptyList));
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.DevicePolicyManagerService$$ExternalSyntheticLambda204
                            public final void runOrThrow() {
                                try {
                                    ActivityTaskManager.getService().updateLockTaskFeatures(intValue, i22);
                                } catch (RemoteException e) {
                                    Slog.wtf("DevicePolicyManager", "Remote Exception: ", e);
                                }
                            }
                        });
                        return Boolean.TRUE;
                    case 4:
                        Set set = (Set) obj;
                        Context context2 = (Context) obj2;
                        int intValue2 = ((Integer) obj3).intValue();
                        ProtectedPackagesFilter protectedPackagesFilter = ProtectedPackagesFilter.ProtectedPackagesFilterHolder.INSTANCE;
                        List<String> list = set == null ? null : set.stream().toList();
                        protectedPackagesFilter.getClass();
                        Slog.d("ProtectedPackagesFilter", "updateProtectedPackages is called. userId " + intValue2 + " package names : " + list);
                        synchronized (protectedPackagesFilter.mProtectedPackages) {
                            try {
                                if (list == null) {
                                    protectedPackagesFilter.mProtectedPackages.remove(intValue2);
                                } else {
                                    protectedPackagesFilter.mProtectedPackages.put(intValue2, new ArraySet(list));
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        if (list != null) {
                            for (String str : list) {
                                boolean z = MARsPolicyManager.MARs_ENABLE;
                                if (!MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.cancelDisablePolicy(str, intValue2, 0)) {
                                    BootReceiver$$ExternalSyntheticOutline0.m("cancelDisablePolicy failed. package : ", str, "MARsPolicyManager");
                                }
                            }
                        }
                        Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(context2, intValue2, set));
                        return Boolean.TRUE;
                    case 5:
                        Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(((Integer) obj3).intValue(), 2, (PolicyKey) obj4, (ComponentName) obj));
                        return Boolean.TRUE;
                    case 6:
                        return Boolean.valueOf(Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda3((PolicyKey) obj4, (Boolean) obj, ((Integer) obj3).intValue(), 1))));
                    case 7:
                        final Context context3 = (Context) obj2;
                        final Integer num = (Integer) obj3;
                        final PolicyKey policyKey = (PolicyKey) obj4;
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda4
                            public final void runOrThrow() {
                                PackagePolicyKey packagePolicyKey = policyKey;
                                Context context4 = context3;
                                Integer num2 = num;
                                String packageName = packagePolicyKey.getPackageName();
                                Objects.requireNonNull(packageName);
                                Intent intent = new Intent("android.intent.action.APPLICATION_RESTRICTIONS_CHANGED");
                                intent.setPackage(packageName);
                                intent.addFlags(1073741824);
                                context4.sendBroadcastAsUser(intent, UserHandle.of(num2.intValue()));
                            }
                        });
                        return Boolean.TRUE;
                    case 8:
                        return Boolean.TRUE;
                    case 9:
                        return Boolean.valueOf(Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda3((PolicyKey) obj4, ((Integer) obj3).intValue(), (Boolean) obj))));
                    case 10:
                        return Boolean.TRUE;
                    case 11:
                        return Boolean.valueOf(Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda3((PolicyKey) obj4, (Boolean) obj, ((Integer) obj3).intValue(), i32))));
                    case 12:
                        return Boolean.TRUE;
                    case 13:
                        return Boolean.TRUE;
                    case 14:
                        final Boolean bool3 = (Boolean) obj;
                        final int intValue3 = ((Integer) obj3).intValue();
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda1
                            public final void runOrThrow() {
                                int i42 = intValue3;
                                Boolean bool4 = bool3;
                                DevicePolicyCache devicePolicyCache = DevicePolicyCache.getInstance();
                                if (devicePolicyCache instanceof DevicePolicyCacheImpl) {
                                    DevicePolicyCacheImpl devicePolicyCacheImpl = (DevicePolicyCacheImpl) devicePolicyCache;
                                    boolean z2 = bool4 != null && bool4.booleanValue();
                                    synchronized (devicePolicyCacheImpl.mLock) {
                                        try {
                                            if (z2) {
                                                ((HashSet) devicePolicyCacheImpl.mScreenCaptureDisallowedUsers).add(Integer.valueOf(i42));
                                            } else {
                                                ((HashSet) devicePolicyCacheImpl.mScreenCaptureDisallowedUsers).remove(Integer.valueOf(i42));
                                            }
                                        } catch (Throwable th2) {
                                            throw th2;
                                        }
                                    }
                                    BackgroundThread.getHandler().post(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda10());
                                }
                            }
                        });
                        return Boolean.TRUE;
                    case 15:
                        Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(((Integer) obj3).intValue(), i32, (Boolean) obj, (Context) obj2));
                        return Boolean.TRUE;
                    case 16:
                        final Boolean bool4 = (Boolean) obj;
                        final Context context4 = (Context) obj2;
                        Boolean bool5 = (Boolean) Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda2
                            public final Object getOrThrow() {
                                Context context5 = context4;
                                Boolean bool6 = bool4;
                                Objects.requireNonNull(context5);
                                DevicePolicyManagerService.updateUsbDataSignal(context5, bool6 == null || bool6.booleanValue());
                                return Boolean.TRUE;
                            }
                        });
                        bool5.getClass();
                        return bool5;
                    case 17:
                        final Integer num2 = (Integer) obj;
                        final Integer num3 = (Integer) obj3;
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda6
                            public final void runOrThrow() {
                                Integer num4 = num3;
                                Integer num5 = num2;
                                DevicePolicyCache devicePolicyCache = DevicePolicyCache.getInstance();
                                if (devicePolicyCache instanceof DevicePolicyCacheImpl) {
                                    ((DevicePolicyCacheImpl) devicePolicyCache).setContentProtectionPolicy(num4.intValue(), num5);
                                }
                            }
                        });
                        return Boolean.TRUE;
                    default:
                        ((Integer) obj3).intValue();
                        String[] strArr3 = DevicePolicyManagerService.DELEGATIONS;
                        Slogf.w("PolicyEnforcerCallbacks", "Trying to enforce setPermissionGrantState while flag is off.");
                        return Boolean.TRUE;
                }
            }
        }, new PackageSetPolicySerializer());
        PACKAGES_SUSPENDED = policyDefinition20;
        HashMap hashMap = new HashMap();
        POLICY_DEFINITIONS = hashMap;
        HashMap hashMap2 = new HashMap();
        USER_RESTRICTION_FLAGS = hashMap2;
        hashMap.put("autoTimezone", policyDefinition);
        hashMap.put("permissionGrant", policyDefinition2);
        hashMap.put("securityLogging", policyDefinition3);
        hashMap.put("auditLogging", policyDefinition4);
        hashMap.put("lockTask", policyDefinition5);
        hashMap.put("userControlDisabledPackages", policyDefinition6);
        hashMap.put("persistentPreferredActivity", policyDefinition7);
        hashMap.put("packageUninstallBlocked", policyDefinition8);
        hashMap.put("applicationRestrictions", policyDefinition9);
        hashMap.put("resetPasswordToken", policyDefinition10);
        hashMap.put("keyguardDisabledFeatures", policyDefinition11);
        hashMap.put("applicationHidden", policyDefinition12);
        hashMap.put("accountManagementDisabled", policyDefinition13);
        hashMap.put("permittedInputMethods", policyDefinition14);
        hashMap.put("screenCaptureDisabled", policyDefinition15);
        hashMap.put("personalAppsSuspended", policyDefinition16);
        hashMap.put("usbDataSignaling", policyDefinition17);
        hashMap.put("contentProtection", policyDefinition18);
        hashMap.put("passwordComplexity", policyDefinition19);
        hashMap.put("packagesSuspended", policyDefinition20);
        hashMap2.put("no_modify_accounts", 0);
        hashMap2.put("no_config_wifi", 0);
        hashMap2.put("no_change_wifi_state", 1);
        hashMap2.put("no_wifi_tethering", 1);
        hashMap2.put("no_grant_admin", 0);
        hashMap2.put("no_sharing_admin_configured_wifi", 0);
        hashMap2.put("no_wifi_direct", 1);
        hashMap2.put("no_add_wifi_config", 1);
        hashMap2.put("no_config_locale", 0);
        hashMap2.put("no_install_apps", 0);
        hashMap2.put("no_uninstall_apps", 0);
        hashMap2.put("no_share_location", 0);
        hashMap2.put("no_airplane_mode", 1);
        hashMap2.put("no_config_brightness", 0);
        hashMap2.put("no_ambient_display", 0);
        hashMap2.put("no_config_screen_timeout", 0);
        hashMap2.put("no_install_unknown_sources", 0);
        hashMap2.put("no_install_unknown_sources_globally", 1);
        hashMap2.put("no_config_bluetooth", 0);
        hashMap2.put("no_bluetooth", 0);
        hashMap2.put("no_bluetooth_sharing", 0);
        hashMap2.put("no_usb_file_transfer", 0);
        hashMap2.put("no_config_credentials", 0);
        hashMap2.put("no_remove_user", 0);
        hashMap2.put("no_remove_managed_profile", 0);
        hashMap2.put("no_debugging_features", 0);
        hashMap2.put("no_config_vpn", 0);
        hashMap2.put("no_config_location", 0);
        hashMap2.put("no_config_date_time", 0);
        hashMap2.put("no_config_tethering", 0);
        hashMap2.put("no_network_reset", 0);
        hashMap2.put("no_factory_reset", 0);
        hashMap2.put("no_add_user", 0);
        hashMap2.put("no_add_managed_profile", 0);
        hashMap2.put("no_add_clone_profile", 0);
        hashMap2.put("no_add_private_profile", 0);
        hashMap2.put("ensure_verify_apps", 1);
        hashMap2.put("no_config_cell_broadcasts", 0);
        hashMap2.put("no_config_mobile_networks", 0);
        hashMap2.put("no_control_apps", 0);
        hashMap2.put("no_physical_media", 0);
        hashMap2.put("no_unmute_microphone", 0);
        hashMap2.put("no_adjust_volume", 0);
        hashMap2.put("no_outgoing_calls", 0);
        hashMap2.put("no_sms", 0);
        hashMap2.put("no_fun", 0);
        hashMap2.put("no_create_windows", 0);
        hashMap2.put("no_system_error_dialogs", 0);
        hashMap2.put("no_cross_profile_copy_paste", 0);
        hashMap2.put("no_outgoing_beam", 0);
        hashMap2.put("no_wallpaper", 0);
        hashMap2.put("no_set_wallpaper", 0);
        hashMap2.put("no_safe_boot", 0);
        hashMap2.put("no_record_audio", 0);
        hashMap2.put("no_run_in_background", 0);
        hashMap2.put("no_camera", 0);
        hashMap2.put("disallow_unmute_device", 0);
        hashMap2.put("no_data_roaming", 0);
        hashMap2.put("no_set_user_icon", 0);
        hashMap2.put("no_oem_unlock", 0);
        hashMap2.put("no_unified_password", 0);
        hashMap2.put("allow_parent_profile_app_linking", 0);
        hashMap2.put("no_autofill", 0);
        hashMap2.put("no_content_capture", 0);
        hashMap2.put("no_content_suggestions", 0);
        hashMap2.put("no_user_switch", 1);
        hashMap2.put("no_sharing_into_profile", 0);
        hashMap2.put("no_printing", 0);
        hashMap2.put("disallow_config_private_dns", 1);
        hashMap2.put("disallow_microphone_toggle", 0);
        hashMap2.put("disallow_camera_toggle", 0);
        hashMap2.put("disallow_biometric", 0);
        hashMap2.put("disallow_config_default_apps", 0);
        hashMap2.put("no_cellular_2g", 1);
        hashMap2.put("no_ultra_wideband_radio", 1);
        hashMap2.put("no_sim_globally", 1);
        hashMap2.put("no_assist_content", 0);
        if (Flags.threadUserRestrictionEnabled()) {
            hashMap2.put("no_thread_network", 1);
        }
        hashMap2.put("no_assist_content", 0);
        for (String str : hashMap2.keySet()) {
            int intValue = ((Integer) ((HashMap) USER_RESTRICTION_FLAGS).get(str)).intValue();
            UserRestrictionPolicyKey userRestrictionPolicyKey = new UserRestrictionPolicyKey(DevicePolicyIdentifiers.getIdentifierForUserRestriction(str), str);
            int i21 = intValue | 20;
            final int i22 = 9;
            PolicyDefinition policyDefinition21 = new PolicyDefinition(userRestrictionPolicyKey, TRUE_MORE_RESTRICTIVE, i21, new QuadFunction() { // from class: com.android.server.devicepolicy.PolicyDefinition$$ExternalSyntheticLambda0
                public final Object apply(Object obj, Object obj2, Object obj3, Object obj4) {
                    final int i222;
                    int i32 = 0;
                    switch (i22) {
                        case 0:
                            String[] strArr = DevicePolicyManagerService.DELEGATIONS;
                            Slogf.w("PolicyEnforcerCallbacks", "Trying to enforce setAutoTimezoneEnabled while flag is off.");
                            return Boolean.TRUE;
                        case 1:
                            ((Integer) obj3).intValue();
                            DevicePolicyManagerInternal devicePolicyManagerInternal = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
                            Boolean bool = Boolean.TRUE;
                            devicePolicyManagerInternal.enforceSecurityLoggingPolicy(bool.equals((Boolean) obj));
                            return bool;
                        case 2:
                            ((Integer) obj3).intValue();
                            DevicePolicyManagerInternal devicePolicyManagerInternal2 = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
                            Boolean bool2 = Boolean.TRUE;
                            devicePolicyManagerInternal2.enforceAuditLoggingPolicy(bool2.equals((Boolean) obj));
                            return bool2;
                        case 3:
                            LockTaskPolicy lockTaskPolicy = (LockTaskPolicy) obj;
                            Context context = (Context) obj2;
                            final int intValue2 = ((Integer) obj3).intValue();
                            List emptyList = Collections.emptyList();
                            if (lockTaskPolicy != null) {
                                emptyList = List.copyOf(lockTaskPolicy.getPackages());
                                i222 = lockTaskPolicy.getFlags();
                            } else {
                                i222 = 16;
                            }
                            String[] strArr2 = DevicePolicyManagerService.DELEGATIONS;
                            Binder.withCleanCallingIdentity(new DevicePolicyManagerService$$ExternalSyntheticLambda101(intValue2, context, emptyList));
                            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.DevicePolicyManagerService$$ExternalSyntheticLambda204
                                public final void runOrThrow() {
                                    try {
                                        ActivityTaskManager.getService().updateLockTaskFeatures(intValue2, i222);
                                    } catch (RemoteException e) {
                                        Slog.wtf("DevicePolicyManager", "Remote Exception: ", e);
                                    }
                                }
                            });
                            return Boolean.TRUE;
                        case 4:
                            Set set = (Set) obj;
                            Context context2 = (Context) obj2;
                            int intValue22 = ((Integer) obj3).intValue();
                            ProtectedPackagesFilter protectedPackagesFilter = ProtectedPackagesFilter.ProtectedPackagesFilterHolder.INSTANCE;
                            List<String> list = set == null ? null : set.stream().toList();
                            protectedPackagesFilter.getClass();
                            Slog.d("ProtectedPackagesFilter", "updateProtectedPackages is called. userId " + intValue22 + " package names : " + list);
                            synchronized (protectedPackagesFilter.mProtectedPackages) {
                                try {
                                    if (list == null) {
                                        protectedPackagesFilter.mProtectedPackages.remove(intValue22);
                                    } else {
                                        protectedPackagesFilter.mProtectedPackages.put(intValue22, new ArraySet(list));
                                    }
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                            if (list != null) {
                                for (String str2 : list) {
                                    boolean z = MARsPolicyManager.MARs_ENABLE;
                                    if (!MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.cancelDisablePolicy(str2, intValue22, 0)) {
                                        BootReceiver$$ExternalSyntheticOutline0.m("cancelDisablePolicy failed. package : ", str2, "MARsPolicyManager");
                                    }
                                }
                            }
                            Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(context2, intValue22, set));
                            return Boolean.TRUE;
                        case 5:
                            Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(((Integer) obj3).intValue(), 2, (PolicyKey) obj4, (ComponentName) obj));
                            return Boolean.TRUE;
                        case 6:
                            return Boolean.valueOf(Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda3((PolicyKey) obj4, (Boolean) obj, ((Integer) obj3).intValue(), 1))));
                        case 7:
                            final Context context3 = (Context) obj2;
                            final Integer num = (Integer) obj3;
                            final PolicyKey policyKey = (PolicyKey) obj4;
                            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda4
                                public final void runOrThrow() {
                                    PackagePolicyKey packagePolicyKey = policyKey;
                                    Context context4 = context3;
                                    Integer num2 = num;
                                    String packageName = packagePolicyKey.getPackageName();
                                    Objects.requireNonNull(packageName);
                                    Intent intent = new Intent("android.intent.action.APPLICATION_RESTRICTIONS_CHANGED");
                                    intent.setPackage(packageName);
                                    intent.addFlags(1073741824);
                                    context4.sendBroadcastAsUser(intent, UserHandle.of(num2.intValue()));
                                }
                            });
                            return Boolean.TRUE;
                        case 8:
                            return Boolean.TRUE;
                        case 9:
                            return Boolean.valueOf(Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda3((PolicyKey) obj4, ((Integer) obj3).intValue(), (Boolean) obj))));
                        case 10:
                            return Boolean.TRUE;
                        case 11:
                            return Boolean.valueOf(Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda3((PolicyKey) obj4, (Boolean) obj, ((Integer) obj3).intValue(), i32))));
                        case 12:
                            return Boolean.TRUE;
                        case 13:
                            return Boolean.TRUE;
                        case 14:
                            final Boolean bool3 = (Boolean) obj;
                            final int intValue3 = ((Integer) obj3).intValue();
                            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda1
                                public final void runOrThrow() {
                                    int i42 = intValue3;
                                    Boolean bool4 = bool3;
                                    DevicePolicyCache devicePolicyCache = DevicePolicyCache.getInstance();
                                    if (devicePolicyCache instanceof DevicePolicyCacheImpl) {
                                        DevicePolicyCacheImpl devicePolicyCacheImpl = (DevicePolicyCacheImpl) devicePolicyCache;
                                        boolean z2 = bool4 != null && bool4.booleanValue();
                                        synchronized (devicePolicyCacheImpl.mLock) {
                                            try {
                                                if (z2) {
                                                    ((HashSet) devicePolicyCacheImpl.mScreenCaptureDisallowedUsers).add(Integer.valueOf(i42));
                                                } else {
                                                    ((HashSet) devicePolicyCacheImpl.mScreenCaptureDisallowedUsers).remove(Integer.valueOf(i42));
                                                }
                                            } catch (Throwable th2) {
                                                throw th2;
                                            }
                                        }
                                        BackgroundThread.getHandler().post(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda10());
                                    }
                                }
                            });
                            return Boolean.TRUE;
                        case 15:
                            Binder.withCleanCallingIdentity(new PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(((Integer) obj3).intValue(), i32, (Boolean) obj, (Context) obj2));
                            return Boolean.TRUE;
                        case 16:
                            final Boolean bool4 = (Boolean) obj;
                            final Context context4 = (Context) obj2;
                            Boolean bool5 = (Boolean) Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda2
                                public final Object getOrThrow() {
                                    Context context5 = context4;
                                    Boolean bool6 = bool4;
                                    Objects.requireNonNull(context5);
                                    DevicePolicyManagerService.updateUsbDataSignal(context5, bool6 == null || bool6.booleanValue());
                                    return Boolean.TRUE;
                                }
                            });
                            bool5.getClass();
                            return bool5;
                        case 17:
                            final Integer num2 = (Integer) obj;
                            final Integer num3 = (Integer) obj3;
                            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda6
                                public final void runOrThrow() {
                                    Integer num4 = num3;
                                    Integer num5 = num2;
                                    DevicePolicyCache devicePolicyCache = DevicePolicyCache.getInstance();
                                    if (devicePolicyCache instanceof DevicePolicyCacheImpl) {
                                        ((DevicePolicyCacheImpl) devicePolicyCache).setContentProtectionPolicy(num4.intValue(), num5);
                                    }
                                }
                            });
                            return Boolean.TRUE;
                        default:
                            ((Integer) obj3).intValue();
                            String[] strArr3 = DevicePolicyManagerService.DELEGATIONS;
                            Slogf.w("PolicyEnforcerCallbacks", "Trying to enforce setPermissionGrantState while flag is off.");
                            return Boolean.TRUE;
                    }
                }
            }, new BooleanPolicySerializer());
            ((HashMap) POLICY_DEFINITIONS).put(userRestrictionPolicyKey.getIdentifier(), policyDefinition21);
        }
    }

    public PolicyDefinition(PolicyKey policyKey, ResolutionMechanism resolutionMechanism, int i, QuadFunction quadFunction, PolicySerializer policySerializer) {
        this.mPolicyKey = policyKey;
        this.mResolutionMechanism = resolutionMechanism;
        this.mPolicyFlags = i;
        this.mPolicyEnforcerCallback = quadFunction;
        this.mPolicySerializer = policySerializer;
        if (isNonCoexistablePolicy() && (i & 2) == 0) {
            throw new UnsupportedOperationException("Non-coexistable global policies not supported,please add support.");
        }
    }

    public static PolicyDefinition ACCOUNT_MANAGEMENT_DISABLED(String str) {
        PolicyDefinition policyDefinition = GENERIC_ACCOUNT_MANAGEMENT_DISABLED;
        return str == null ? policyDefinition : policyDefinition.createPolicyDefinition(new AccountTypePolicyKey("accountManagementDisabled", str));
    }

    public static PolicyDefinition APPLICATION_RESTRICTIONS(String str) {
        PolicyDefinition policyDefinition = GENERIC_APPLICATION_RESTRICTIONS;
        return str == null ? policyDefinition : policyDefinition.createPolicyDefinition(new PackagePolicyKey("applicationRestrictions", str));
    }

    public static PolicyDefinition PERSISTENT_PREFERRED_ACTIVITY(IntentFilter intentFilter) {
        PolicyDefinition policyDefinition = GENERIC_PERSISTENT_PREFERRED_ACTIVITY;
        return intentFilter == null ? policyDefinition : policyDefinition.createPolicyDefinition(new IntentFilterPolicyKey("persistentPreferredActivity", intentFilter));
    }

    public static PolicyDefinition getPolicyDefinitionForUserRestriction(String str) {
        String identifierForUserRestriction = DevicePolicyIdentifiers.getIdentifierForUserRestriction(str);
        Map map = POLICY_DEFINITIONS;
        if (((HashMap) map).containsKey(identifierForUserRestriction)) {
            return (PolicyDefinition) ((HashMap) map).get(identifierForUserRestriction);
        }
        throw new IllegalArgumentException(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("Unsupported user restriction ", str));
    }

    public static PolicyKey readPolicyKeyFromXml(TypedXmlPullParser typedXmlPullParser) {
        PolicyKey readGenericPolicyKeyFromXml = PolicyKey.readGenericPolicyKeyFromXml(typedXmlPullParser);
        if (readGenericPolicyKeyFromXml == null) {
            Slogf.wtf("PolicyDefinition", "Error parsing PolicyKey, GenericPolicyKey is null");
            return null;
        }
        PolicyDefinition policyDefinition = (PolicyDefinition) ((HashMap) POLICY_DEFINITIONS).get(readGenericPolicyKeyFromXml.getIdentifier());
        if (policyDefinition != null) {
            return policyDefinition.mPolicyKey.readFromXml(typedXmlPullParser);
        }
        Slogf.wtf("PolicyDefinition", "Error parsing PolicyKey, Unknown generic policy key: " + readGenericPolicyKeyFromXml);
        return null;
    }

    public final PolicyDefinition createPolicyDefinition(PolicyKey policyKey) {
        return new PolicyDefinition(policyKey, this.mResolutionMechanism, this.mPolicyFlags, this.mPolicyEnforcerCallback, this.mPolicySerializer);
    }

    public final boolean isGlobalOnlyPolicy() {
        return (this.mPolicyFlags & 1) != 0;
    }

    public final boolean isNonCoexistablePolicy() {
        return (this.mPolicyFlags & 8) != 0;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("PolicyDefinition{ mPolicyKey= ");
        sb.append(this.mPolicyKey);
        sb.append(", mResolutionMechanism= ");
        sb.append(this.mResolutionMechanism);
        sb.append(", mPolicyFlags= ");
        return AmFmBandRange$$ExternalSyntheticOutline0.m(this.mPolicyFlags, sb, " }");
    }
}
