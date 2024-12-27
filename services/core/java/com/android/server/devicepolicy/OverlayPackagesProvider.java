package com.android.server.devicepolicy;

import android.app.admin.flags.Flags;
import android.app.role.RoleManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.util.ArraySet;

import com.android.internal.util.FunctionalUtils;
import com.android.server.inputmethod.InputMethodManagerInternal;
import com.android.server.pm.ApexManager;

import com.samsung.android.feature.SemCscFeature;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public final class OverlayPackagesProvider {
    public static final Map sActionToMetadataKeyMap;
    public static final Set sAllowedActions;
    public final Context mContext;
    public final Injector mInjector;
    public final PackageManager mPm;
    public final RecursiveStringArrayResourceResolver mRecursiveStringArrayResourceResolver;
    public final String salesCode;

    public final class DefaultInjector implements Injector {
        @Override // com.android.server.devicepolicy.OverlayPackagesProvider.Injector
        public final String getActiveApexPackageNameContainingPackage(String str) {
            return ApexManager.getInstance().getActiveApexPackageNameContainingPackage(str);
        }

        @Override // com.android.server.devicepolicy.OverlayPackagesProvider.Injector
        public final String getDevicePolicyManagementRoleHolderPackageName(final Context context) {
            return (String)
                    Binder.withCleanCallingIdentity(
                            new FunctionalUtils.ThrowingSupplier() { // from class:
                                // com.android.server.devicepolicy.OverlayPackagesProvider$DefaultInjector$$ExternalSyntheticLambda0
                                public final Object getOrThrow() {
                                    List roleHolders =
                                            ((RoleManager)
                                                            context.getSystemService(
                                                                    RoleManager.class))
                                                    .getRoleHolders(
                                                            "android.app.role.DEVICE_POLICY_MANAGEMENT");
                                    if (roleHolders.isEmpty()) {
                                        return null;
                                    }
                                    return (String) roleHolders.get(0);
                                }
                            });
        }

        @Override // com.android.server.devicepolicy.OverlayPackagesProvider.Injector
        public final List getInputMethodListAsUser(int i) {
            return InputMethodManagerInternal.get().getInputMethodListAsUser(i);
        }
    }

    interface Injector {
        String getActiveApexPackageNameContainingPackage(String str);

        String getDevicePolicyManagementRoleHolderPackageName(Context context);

        List getInputMethodListAsUser(int i);
    }

    static {
        HashMap hashMap = new HashMap();
        sActionToMetadataKeyMap = hashMap;
        hashMap.put(
                "android.app.action.PROVISION_MANAGED_USER",
                "android.app.REQUIRED_APP_MANAGED_USER");
        hashMap.put(
                "android.app.action.PROVISION_MANAGED_PROFILE",
                "android.app.REQUIRED_APP_MANAGED_PROFILE");
        hashMap.put(
                "android.app.action.PROVISION_MANAGED_DEVICE",
                "android.app.REQUIRED_APP_MANAGED_DEVICE");
        HashSet hashSet = new HashSet();
        sAllowedActions = hashSet;
        hashSet.add("android.app.action.PROVISION_MANAGED_USER");
        hashSet.add("android.app.action.PROVISION_MANAGED_PROFILE");
        hashSet.add("android.app.action.PROVISION_MANAGED_DEVICE");
    }

    public OverlayPackagesProvider(
            Context context,
            Injector injector,
            RecursiveStringArrayResourceResolver recursiveStringArrayResourceResolver) {
        this.mContext = context;
        PackageManager packageManager = context.getPackageManager();
        Objects.requireNonNull(packageManager);
        this.mPm = packageManager;
        Objects.requireNonNull(injector);
        this.mInjector = injector;
        Objects.requireNonNull(recursiveStringArrayResourceResolver);
        this.mRecursiveStringArrayResourceResolver = recursiveStringArrayResourceResolver;
        this.salesCode = SemCscFeature.getInstance().getString("SalesCode", "");
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.Set getNonRequiredApps(
            android.content.ComponentName r31, int r32, java.lang.String r33) {
        /*
            Method dump skipped, instructions count: 1800
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.devicepolicy.OverlayPackagesProvider.getNonRequiredApps(android.content.ComponentName,"
                    + " int, java.lang.String):java.util.Set");
    }

    public final Set resolveStringArray(int i) {
        if (!Flags.isRecursiveRequiredAppMergingEnabled()) {
            return new ArraySet(Arrays.asList(this.mContext.getResources().getStringArray(i)));
        }
        String packageName = this.mContext.getPackageName();
        RecursiveStringArrayResourceResolver recursiveStringArrayResourceResolver =
                this.mRecursiveStringArrayResourceResolver;
        recursiveStringArrayResourceResolver.getClass();
        return recursiveStringArrayResourceResolver.resolve(i, packageName, List.of());
    }
}
