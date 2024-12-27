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

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class OverlayPackagesProvider {
    public static final Map sActionToMetadataKeyMap;
    public static final Set sAllowedActions;
    public final Context mContext;
    public final Injector mInjector;
    public final PackageManager mPm;
    public final RecursiveStringArrayResourceResolver mRecursiveStringArrayResourceResolver;
    public final String salesCode;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    /* JADX WARN: Removed duplicated region for block: B:104:0x060f  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x0657  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x0654 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:139:0x0670  */
    /* JADX WARN: Removed duplicated region for block: B:165:0x054c  */
    /* JADX WARN: Removed duplicated region for block: B:166:0x0550  */
    /* JADX WARN: Removed duplicated region for block: B:205:0x048e  */
    /* JADX WARN: Removed duplicated region for block: B:208:0x0498  */
    /* JADX WARN: Removed duplicated region for block: B:211:0x04a2  */
    /* JADX WARN: Removed duplicated region for block: B:214:0x0476  */
    /* JADX WARN: Removed duplicated region for block: B:215:0x047a  */
    /* JADX WARN: Removed duplicated region for block: B:216:0x044b  */
    /* JADX WARN: Removed duplicated region for block: B:219:0x0454  */
    /* JADX WARN: Removed duplicated region for block: B:222:0x045d  */
    /* JADX WARN: Removed duplicated region for block: B:225:0x0423  */
    /* JADX WARN: Removed duplicated region for block: B:226:0x042f  */
    /* JADX WARN: Removed duplicated region for block: B:227:0x03dc  */
    /* JADX WARN: Removed duplicated region for block: B:230:0x03e9  */
    /* JADX WARN: Removed duplicated region for block: B:233:0x03f6  */
    /* JADX WARN: Removed duplicated region for block: B:239:0x03c1  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x034a  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x037a  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x03d6  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0405  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0413  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0449  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0468  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0472  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x048b  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x04ae  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x04b8  */
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
