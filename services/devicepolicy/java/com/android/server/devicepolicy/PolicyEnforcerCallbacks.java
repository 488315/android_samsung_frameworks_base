package com.android.server.devicepolicy;

import android.app.AppGlobals;
import android.app.admin.DevicePolicyCache;
import android.app.admin.IntentFilterPolicyKey;
import android.app.admin.LockTaskPolicy;
import android.app.admin.PackagePermissionPolicyKey;
import android.app.admin.PackagePolicyKey;
import android.app.admin.PolicyKey;
import android.app.admin.UserRestrictionPolicyKey;
import android.app.usage.UsageStatsManagerInternal;
import android.content.ComponentName;
import android.content.Context;
import android.content.IntentFilter;
import android.content.p000pm.PackageManagerInternal;
import android.content.pm.IPackageManager;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.permission.AdminPermissionControlParams;
import android.permission.PermissionControllerManager;
import android.provider.Settings;
import android.util.ArraySet;
import android.util.Slog;
import android.view.IWindowManager;
import com.android.internal.os.BackgroundThread;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.FunctionalUtils;
import com.android.server.LocalServices;
import com.android.server.devicepolicy.PolicyEnforcerCallbacks;
import com.android.server.am.mars.filter.filter.ProtectedPackagesFilter;
import com.android.server.pm.UserManagerInternal;
import com.android.server.utils.Slogf;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

/* loaded from: classes2.dex */
public abstract class PolicyEnforcerCallbacks {
    public static boolean setAutoTimezoneEnabled(final Boolean bool, final Context context) {
        return ((Boolean) Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda6
            public final Object getOrThrow() {
                Boolean lambda$setAutoTimezoneEnabled$0;
                lambda$setAutoTimezoneEnabled$0 = PolicyEnforcerCallbacks.lambda$setAutoTimezoneEnabled$0(context, bool);
                return lambda$setAutoTimezoneEnabled$0;
            }
        })).booleanValue();
    }

    public static /* synthetic */ Boolean lambda$setAutoTimezoneEnabled$0(Context context, Boolean bool) {
        Objects.requireNonNull(context);
        return Boolean.valueOf(Settings.Global.putInt(context.getContentResolver(), "auto_time_zone", (bool == null || !bool.booleanValue()) ? 0 : 1));
    }

    public static boolean setPermissionGrantState(final Integer num, final Context context, final int i, final PolicyKey policyKey) {
        return Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda2
            public final Object getOrThrow() {
                Boolean lambda$setPermissionGrantState$1;
                lambda$setPermissionGrantState$1 = PolicyEnforcerCallbacks.lambda$setPermissionGrantState$1(policyKey, context, num, i);
                return lambda$setPermissionGrantState$1;
            }
        }));
    }

    public static /* synthetic */ Boolean lambda$setPermissionGrantState$1(PolicyKey policyKey, Context context, Integer num, int i) {
        if (!(policyKey instanceof PackagePermissionPolicyKey)) {
            throw new IllegalArgumentException("policyKey is not of type PermissionGrantStatePolicyKey, passed in policyKey is: " + policyKey);
        }
        PackagePermissionPolicyKey packagePermissionPolicyKey = (PackagePermissionPolicyKey) policyKey;
        Objects.requireNonNull(packagePermissionPolicyKey.getPermissionName());
        Objects.requireNonNull(packagePermissionPolicyKey.getPackageName());
        Objects.requireNonNull(context);
        int intValue = num == null ? 0 : num.intValue();
        final BlockingCallback blockingCallback = new BlockingCallback();
        getPermissionControllerManager(context, UserHandle.of(i)).setRuntimePermissionGrantStateByDeviceAdmin(context.getPackageName(), new AdminPermissionControlParams(packagePermissionPolicyKey.getPackageName(), packagePermissionPolicyKey.getPermissionName(), intValue, true), context.getMainExecutor(), new Consumer() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                PolicyEnforcerCallbacks.BlockingCallback.this.trigger((Boolean) obj);
            }
        });
        try {
            return blockingCallback.await(20000L, TimeUnit.MILLISECONDS);
        } catch (Exception unused) {
            return Boolean.FALSE;
        }
    }

    public static PermissionControllerManager getPermissionControllerManager(Context context, UserHandle userHandle) {
        if (userHandle.equals(context.getUser())) {
            return (PermissionControllerManager) context.getSystemService(PermissionControllerManager.class);
        }
        try {
            return (PermissionControllerManager) context.createPackageContextAsUser(context.getPackageName(), 0, userHandle).getSystemService(PermissionControllerManager.class);
        } catch (PackageManager.NameNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    public static boolean setLockTask(LockTaskPolicy lockTaskPolicy, Context context, int i) {
        int i2;
        List emptyList = Collections.emptyList();
        if (lockTaskPolicy != null) {
            emptyList = List.copyOf(lockTaskPolicy.getPackages());
            i2 = lockTaskPolicy.getFlags();
        } else {
            i2 = 16;
        }
        DevicePolicyManagerService.updateLockTaskPackagesLocked(context, emptyList, i);
        DevicePolicyManagerService.updateLockTaskFeaturesLocked(i2, i);
        return true;
    }

    public class BlockingCallback {
        public final CountDownLatch mLatch;
        public final AtomicReference mValue;

        public BlockingCallback() {
            this.mLatch = new CountDownLatch(1);
            this.mValue = new AtomicReference();
        }

        public void trigger(Boolean bool) {
            this.mValue.set(bool);
            this.mLatch.countDown();
        }

        public Boolean await(long j, TimeUnit timeUnit) {
            if (!this.mLatch.await(j, timeUnit)) {
                Slogf.m90e("PolicyEnforcerCallbacks", "Callback was not received");
            }
            return (Boolean) this.mValue.get();
        }
    }

    public static boolean setUserControlDisabledPackages(final Set set, final int i) {
        ProtectedPackagesFilter.getInstance().updateProtectedPackages(i, set == null ? null : set.stream().toList());
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda5
            public final void runOrThrow() {
                PolicyEnforcerCallbacks.lambda$setUserControlDisabledPackages$2(i, set);
            }
        });
        return true;
    }

    public static /* synthetic */ void lambda$setUserControlDisabledPackages$2(int i, Set set) {
        ((PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class)).setOwnerProtectedPackages(i, set == null ? null : set.stream().toList());
        ((UsageStatsManagerInternal) LocalServices.getService(UsageStatsManagerInternal.class)).setAdminProtectedPackages(set != null ? new ArraySet(set) : null, i);
    }

    public static boolean addPersistentPreferredActivity(final ComponentName componentName, Context context, final int i, final PolicyKey policyKey) {
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda3
            public final void runOrThrow() {
                PolicyEnforcerCallbacks.lambda$addPersistentPreferredActivity$3(policyKey, componentName, i);
            }
        });
        return true;
    }

    public static /* synthetic */ void lambda$addPersistentPreferredActivity$3(PolicyKey policyKey, ComponentName componentName, int i) {
        try {
            if (!(policyKey instanceof IntentFilterPolicyKey)) {
                throw new IllegalArgumentException("policyKey is not of type IntentFilterPolicyKey, passed in policyKey is: " + policyKey);
            }
            IntentFilter intentFilter = ((IntentFilterPolicyKey) policyKey).getIntentFilter();
            Objects.requireNonNull(intentFilter);
            IntentFilter intentFilter2 = intentFilter;
            IPackageManager packageManager = AppGlobals.getPackageManager();
            if (componentName != null) {
                packageManager.addPersistentPreferredActivity(intentFilter, componentName, i);
            } else {
                packageManager.clearPersistentPreferredActivity(intentFilter, i);
            }
            packageManager.flushPackageRestrictionsAsUser(i);
        } catch (RemoteException e) {
            Slog.wtf("PolicyEnforcerCallbacks", "Error adding/removing persistent preferred activity", e);
        }
    }

    public static boolean setUninstallBlocked(final Boolean bool, Context context, final int i, final PolicyKey policyKey) {
        return Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda0
            public final Object getOrThrow() {
                Boolean lambda$setUninstallBlocked$4;
                lambda$setUninstallBlocked$4 = PolicyEnforcerCallbacks.lambda$setUninstallBlocked$4(policyKey, bool, i);
                return lambda$setUninstallBlocked$4;
            }
        }));
    }

    public static /* synthetic */ Boolean lambda$setUninstallBlocked$4(PolicyKey policyKey, Boolean bool, int i) {
        if (!(policyKey instanceof PackagePolicyKey)) {
            throw new IllegalArgumentException("policyKey is not of type PackagePolicyKey, passed in policyKey is: " + policyKey);
        }
        String packageName = ((PackagePolicyKey) policyKey).getPackageName();
        Objects.requireNonNull(packageName);
        DevicePolicyManagerService.setUninstallBlockedUnchecked(packageName, bool != null && bool.booleanValue(), i);
        return Boolean.TRUE;
    }

    public static boolean setUserRestriction(final Boolean bool, Context context, final int i, final PolicyKey policyKey) {
        return Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda10
            public final Object getOrThrow() {
                Boolean lambda$setUserRestriction$5;
                lambda$setUserRestriction$5 = PolicyEnforcerCallbacks.lambda$setUserRestriction$5(policyKey, i, bool);
                return lambda$setUserRestriction$5;
            }
        }));
    }

    public static /* synthetic */ Boolean lambda$setUserRestriction$5(PolicyKey policyKey, int i, Boolean bool) {
        if (!(policyKey instanceof UserRestrictionPolicyKey)) {
            throw new IllegalArgumentException("policyKey is not of type UserRestrictionPolicyKey, passed in policyKey is: " + policyKey);
        }
        ((UserManagerInternal) LocalServices.getService(UserManagerInternal.class)).setUserRestriction(i, ((UserRestrictionPolicyKey) policyKey).getRestriction(), bool != null && bool.booleanValue());
        return Boolean.TRUE;
    }

    public static boolean setApplicationHidden(final Boolean bool, Context context, final int i, final PolicyKey policyKey) {
        return Boolean.TRUE.equals(Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda4
            public final Object getOrThrow() {
                Boolean lambda$setApplicationHidden$6;
                lambda$setApplicationHidden$6 = PolicyEnforcerCallbacks.lambda$setApplicationHidden$6(policyKey, bool, i);
                return lambda$setApplicationHidden$6;
            }
        }));
    }

    public static /* synthetic */ Boolean lambda$setApplicationHidden$6(PolicyKey policyKey, Boolean bool, int i) {
        if (!(policyKey instanceof PackagePolicyKey)) {
            throw new IllegalArgumentException("policyKey is not of type PackagePolicyKey, passed in policyKey is: " + policyKey);
        }
        String packageName = ((PackagePolicyKey) policyKey).getPackageName();
        Objects.requireNonNull(packageName);
        return Boolean.valueOf(AppGlobals.getPackageManager().setApplicationHiddenSettingAsUser(packageName, bool != null && bool.booleanValue(), i));
    }

    public static boolean setScreenCaptureDisabled(final Boolean bool, Context context, final int i, PolicyKey policyKey) {
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda1
            public final void runOrThrow() {
                PolicyEnforcerCallbacks.lambda$setScreenCaptureDisabled$7(i, bool);
            }
        });
        return true;
    }

    public static /* synthetic */ void lambda$setScreenCaptureDisabled$7(int i, Boolean bool) {
        DevicePolicyCache devicePolicyCache = DevicePolicyCache.getInstance();
        if (devicePolicyCache instanceof DevicePolicyCacheImpl) {
            ((DevicePolicyCacheImpl) devicePolicyCache).setScreenCaptureDisallowedUser(i, bool != null && bool.booleanValue());
            updateScreenCaptureDisabled();
        }
    }

    public static void updateScreenCaptureDisabled() {
        BackgroundThread.getHandler().post(new Runnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                PolicyEnforcerCallbacks.lambda$updateScreenCaptureDisabled$8();
            }
        });
    }

    public static /* synthetic */ void lambda$updateScreenCaptureDisabled$8() {
        try {
            IWindowManager.Stub.asInterface(ServiceManager.getService("window")).refreshScreenCaptureDisabled();
        } catch (RemoteException e) {
            Slogf.m103w("PolicyEnforcerCallbacks", "Unable to notify WindowManager.", e);
        }
    }

    public static boolean setPersonalAppsSuspended(final Boolean bool, final Context context, final int i, PolicyKey policyKey) {
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda7
            public final void runOrThrow() {
                PolicyEnforcerCallbacks.lambda$setPersonalAppsSuspended$9(bool, context, i);
            }
        });
        return true;
    }

    public static /* synthetic */ void lambda$setPersonalAppsSuspended$9(Boolean bool, Context context, int i) {
        if (bool != null && bool.booleanValue()) {
            suspendPersonalAppsInPackageManager(context, i);
        } else {
            ((PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class)).unsuspendForSuspendingPackage("android", i);
        }
    }

    public static void suspendPersonalAppsInPackageManager(Context context, int i) {
        String[] packagesSuspendedByAdmin = ((PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class)).setPackagesSuspendedByAdmin(i, PersonalAppsSuspensionHelper.forUser(context, i).getPersonalAppsForSuspension(), true);
        if (ArrayUtils.isEmpty(packagesSuspendedByAdmin)) {
            return;
        }
        Slogf.wtf("PolicyEnforcerCallbacks", "Failed to suspend apps: " + String.join(",", packagesSuspendedByAdmin));
    }
}
