package com.android.server.devicepolicy;

import android.content.pm.PackageManagerInternal;
import android.util.ArraySet;
import com.android.server.utils.Slogf;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class PackageSuspender {
    public final List mExemptedPackages;
    public final PackageManagerInternal mPackageManager;
    public final Set mSuspendedPackageAfter;
    public final Set mSuspendedPackageBefore;
    public final int mUserId;

    public PackageSuspender(Set set, Set set2, List list, PackageManagerInternal packageManagerInternal, int i) {
        this.mSuspendedPackageBefore = set == null ? Collections.emptySet() : set;
        this.mSuspendedPackageAfter = set2 == null ? Collections.emptySet() : set2;
        this.mExemptedPackages = list;
        this.mPackageManager = packageManagerInternal;
        this.mUserId = i;
    }

    public final String[] suspend(Set set) {
        ArraySet arraySet = new ArraySet(set);
        ArraySet arraySet2 = new ArraySet(this.mExemptedPackages);
        arraySet2.retainAll(arraySet);
        arraySet.removeAll(this.mExemptedPackages);
        String[] strArr = (String[]) arraySet.toArray(new PackageSuspender$$ExternalSyntheticLambda0(2));
        String[] packagesSuspendedByAdmin = this.mPackageManager.setPackagesSuspendedByAdmin(this.mUserId, true, strArr);
        if (packagesSuspendedByAdmin == null) {
            Slogf.w("DevicePolicyManager", "PM failed to suspend packages (%s)", set);
        } else {
            arraySet2.addAll(Arrays.asList(packagesSuspendedByAdmin));
            set = arraySet2;
        }
        return (String[]) set.toArray(new PackageSuspender$$ExternalSyntheticLambda0(1));
    }

    public final String[] unsuspend(Set set) {
        ArraySet arraySet = new ArraySet(this.mSuspendedPackageBefore);
        arraySet.removeAll(this.mSuspendedPackageAfter);
        ArraySet arraySet2 = new ArraySet(set);
        arraySet2.retainAll(this.mSuspendedPackageAfter);
        arraySet2.removeAll(this.mExemptedPackages);
        String[] strArr = (String[]) arraySet.toArray(new PackageSuspender$$ExternalSyntheticLambda0(3));
        String[] packagesSuspendedByAdmin = this.mPackageManager.setPackagesSuspendedByAdmin(this.mUserId, false, strArr);
        if (packagesSuspendedByAdmin == null) {
            Slogf.w("DevicePolicyManager", "PM failed to unsuspend packages (%s)", arraySet);
        }
        arraySet2.addAll((Collection) new ArraySet(packagesSuspendedByAdmin));
        return (String[]) arraySet2.toArray(new PackageSuspender$$ExternalSyntheticLambda0(0));
    }
}
