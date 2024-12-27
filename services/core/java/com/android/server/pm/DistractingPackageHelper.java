package com.android.server.pm;

import android.os.UserHandle;
import android.util.IntArray;

import com.android.internal.util.ArrayUtils;
import com.android.server.pm.pkg.PackageUserStateImpl;
import com.android.server.pm.pkg.mutate.PackageStateMutator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class DistractingPackageHelper {
    public final BroadcastHelper mBroadcastHelper;
    public final PackageManagerService mPm;
    public final SuspendPackageHelper mSuspendPackageHelper;

    public DistractingPackageHelper(
            PackageManagerService packageManagerService,
            BroadcastHelper broadcastHelper,
            SuspendPackageHelper suspendPackageHelper) {
        this.mPm = packageManagerService;
        this.mBroadcastHelper = broadcastHelper;
        this.mSuspendPackageHelper = suspendPackageHelper;
    }

    public final void removeDistractingPackageRestrictions(
            Computer computer, String[] strArr, final int i) {
        if (ArrayUtils.isEmpty(strArr)) {
            return;
        }
        final ArrayList arrayList = new ArrayList(strArr.length);
        IntArray intArray = new IntArray(strArr.length);
        for (String str : strArr) {
            PackageSetting packageStateInternal = computer.getPackageStateInternal(str);
            if (packageStateInternal != null
                    && packageStateInternal.getUserStateOrDefault(i).getDistractionFlags() != 0) {
                arrayList.add(packageStateInternal.mName);
                intArray.add(UserHandle.getUid(i, packageStateInternal.mAppId));
            }
        }
        Consumer consumer =
                new Consumer() { // from class:
                                 // com.android.server.pm.DistractingPackageHelper$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        List list = arrayList;
                        int i2 = i;
                        PackageStateMutator packageStateMutator = (PackageStateMutator) obj;
                        for (int i3 = 0; i3 < list.size(); i3++) {
                            PackageUserStateImpl packageUserStateImpl =
                                    packageStateMutator
                                            .forPackage((String) list.get(i3))
                                            .userState(i2)
                                            .mUserState;
                            if (packageUserStateImpl != null) {
                                packageUserStateImpl.mDistractionFlags = 0;
                                packageUserStateImpl.onChanged$4();
                            }
                        }
                    }
                };
        PackageManagerService packageManagerService = this.mPm;
        packageManagerService.commitPackageStateMutation(consumer);
        if (arrayList.isEmpty()) {
            return;
        }
        this.mBroadcastHelper.sendDistractingPackagesChanged(
                packageManagerService.snapshotComputer(),
                (String[]) arrayList.toArray(new String[arrayList.size()]),
                intArray.toArray(),
                i,
                0);
        packageManagerService.scheduleWritePackageRestrictions(i);
    }
}
