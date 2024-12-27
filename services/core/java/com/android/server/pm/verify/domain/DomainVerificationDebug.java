package com.android.server.pm.verify.domain;

import android.content.pm.verify.domain.DomainVerificationState;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.IndentingPrintWriter;
import android.util.PackageUtils;
import android.util.SparseArray;

import com.android.internal.pm.parsing.pkg.AndroidPackageInternal;
import com.android.internal.util.CollectionUtils;
import com.android.server.pm.Computer;
import com.android.server.pm.PackageSetting;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.verify.domain.models.DomainVerificationInternalUserState;
import com.android.server.pm.verify.domain.models.DomainVerificationPkgState;
import com.android.server.pm.verify.domain.models.DomainVerificationStateMap;

import java.util.Arrays;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class DomainVerificationDebug {
    public final DomainVerificationCollector mCollector;

    public DomainVerificationDebug(DomainVerificationCollector domainVerificationCollector) {
        this.mCollector = domainVerificationCollector;
    }

    public static void printState(
            IndentingPrintWriter indentingPrintWriter,
            DomainVerificationPkgState domainVerificationPkgState,
            int i,
            DomainVerificationInternalUserState domainVerificationInternalUserState,
            ArraySet arraySet,
            ArraySet arraySet2,
            boolean z) {
        arraySet.clear();
        arraySet.addAll(arraySet2);
        if (domainVerificationInternalUserState != null) {
            arraySet.removeAll(domainVerificationInternalUserState.mEnabledHosts);
        }
        ArraySet arraySet3 =
                domainVerificationInternalUserState == null
                        ? null
                        : domainVerificationInternalUserState.mEnabledHosts;
        int size = CollectionUtils.size(arraySet3);
        int size2 = arraySet.size();
        if (size > 0 || size2 > 0) {
            if (!z) {
                indentingPrintWriter.println(
                        domainVerificationPkgState.mPackageName
                                + " "
                                + domainVerificationPkgState.mId
                                + ":");
            }
            boolean z2 =
                    domainVerificationInternalUserState == null
                            || domainVerificationInternalUserState.mLinkHandlingAllowed;
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.print("User ");
            indentingPrintWriter.print(i == -1 ? "all" : Integer.valueOf(i));
            indentingPrintWriter.println(":");
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.print("Verification link handling allowed: ");
            indentingPrintWriter.println(z2);
            indentingPrintWriter.println("Selection state:");
            indentingPrintWriter.increaseIndent();
            if (size > 0) {
                indentingPrintWriter.println("Enabled:");
                indentingPrintWriter.increaseIndent();
                for (int i2 = 0; i2 < size; i2++) {
                    indentingPrintWriter.println((String) arraySet3.valueAt(i2));
                }
                indentingPrintWriter.decreaseIndent();
            }
            if (size2 > 0) {
                indentingPrintWriter.println("Disabled:");
                indentingPrintWriter.increaseIndent();
                for (int i3 = 0; i3 < size2; i3++) {
                    indentingPrintWriter.println((String) arraySet.valueAt(i3));
                }
                indentingPrintWriter.decreaseIndent();
            }
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.decreaseIndent();
        }
    }

    public final void printState(
            IndentingPrintWriter indentingPrintWriter,
            DomainVerificationPkgState domainVerificationPkgState,
            AndroidPackage androidPackage,
            Integer num,
            ArraySet arraySet,
            boolean z) {
        if (num == null) {
            return;
        }
        ArraySet collectDomains = this.mCollector.collectDomains(androidPackage, false, true);
        SparseArray sparseArray = domainVerificationPkgState.mUserStates;
        if (num.intValue() != -1) {
            printState(
                    indentingPrintWriter,
                    domainVerificationPkgState,
                    num.intValue(),
                    (DomainVerificationInternalUserState) sparseArray.get(num.intValue()),
                    arraySet,
                    collectDomains,
                    z);
            return;
        }
        int size = sparseArray.size();
        if (size == 0) {
            printState(
                    indentingPrintWriter,
                    domainVerificationPkgState,
                    num.intValue(),
                    null,
                    arraySet,
                    collectDomains,
                    z);
            return;
        }
        for (int i = 0; i < size; i++) {
            DomainVerificationInternalUserState domainVerificationInternalUserState =
                    (DomainVerificationInternalUserState) sparseArray.valueAt(i);
            printState(
                    indentingPrintWriter,
                    domainVerificationPkgState,
                    domainVerificationInternalUserState.mUserId,
                    domainVerificationInternalUserState,
                    arraySet,
                    collectDomains,
                    z);
        }
    }

    public final void printState(
            IndentingPrintWriter indentingPrintWriter,
            String str,
            Integer num,
            Computer computer,
            DomainVerificationStateMap domainVerificationStateMap) {
        AndroidPackageInternal androidPackageInternal;
        AndroidPackageInternal androidPackageInternal2;
        ArrayMap arrayMap = new ArrayMap();
        ArraySet arraySet = new ArraySet();
        if (str == null) {
            int size = domainVerificationStateMap.mPackageNameMap.size();
            for (int i = 0; i < size; i++) {
                DomainVerificationPkgState domainVerificationPkgState =
                        (DomainVerificationPkgState)
                                domainVerificationStateMap.mPackageNameMap.valueAt(i);
                PackageSetting packageStateInternal =
                        computer.getPackageStateInternal(domainVerificationPkgState.mPackageName);
                if (packageStateInternal != null
                        && (androidPackageInternal2 = packageStateInternal.pkg) != null) {
                    printState(
                            indentingPrintWriter,
                            domainVerificationPkgState,
                            packageStateInternal.pkg,
                            num,
                            arraySet,
                            printState(
                                    indentingPrintWriter,
                                    domainVerificationPkgState,
                                    androidPackageInternal2,
                                    arrayMap));
                }
            }
            return;
        }
        DomainVerificationPkgState domainVerificationPkgState2 =
                (DomainVerificationPkgState) domainVerificationStateMap.mPackageNameMap.get(str);
        if (domainVerificationPkgState2 == null) {
            DomainVerificationUtils.throwPackageUnavailable(str);
            throw null;
        }
        PackageSetting packageStateInternal2 = computer.getPackageStateInternal(str);
        if (packageStateInternal2 == null
                || (androidPackageInternal = packageStateInternal2.pkg) == null) {
            DomainVerificationUtils.throwPackageUnavailable(str);
            throw null;
        }
        printState(
                indentingPrintWriter,
                domainVerificationPkgState2,
                androidPackageInternal,
                arrayMap);
        printState(
                indentingPrintWriter,
                domainVerificationPkgState2,
                androidPackageInternal,
                num,
                arraySet,
                true);
    }

    public final boolean printState(
            IndentingPrintWriter indentingPrintWriter,
            DomainVerificationPkgState domainVerificationPkgState,
            AndroidPackage androidPackage,
            ArrayMap arrayMap) {
        arrayMap.clear();
        arrayMap.putAll(domainVerificationPkgState.mStateMap);
        DomainVerificationCollector domainVerificationCollector = this.mCollector;
        ArraySet collectDomains =
                domainVerificationCollector.collectDomains(androidPackage, true, true);
        int size = collectDomains.size();
        for (int i = 0; i < size; i++) {
            arrayMap.putIfAbsent((String) collectDomains.valueAt(i), 0);
        }
        if (arrayMap.isEmpty()) {
            return false;
        }
        String arrays =
                androidPackage.getSigningDetails().getSignatures() == null
                        ? null
                        : Arrays.toString(
                                PackageUtils.computeSignaturesSha256Digests(
                                        androidPackage.getSigningDetails().getSignatures(), ":"));
        indentingPrintWriter.println(domainVerificationPkgState.mPackageName + ":");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("ID: " + domainVerificationPkgState.mId);
        indentingPrintWriter.println("Signatures: " + arrays);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.increaseIndent();
        ArraySet collectDomains2 =
                domainVerificationCollector.collectDomains(androidPackage, true, false);
        if (!collectDomains2.isEmpty()) {
            indentingPrintWriter.println("Invalid autoVerify domains:");
            indentingPrintWriter.increaseIndent();
            int size2 = collectDomains2.size();
            for (int i2 = 0; i2 < size2; i2++) {
                indentingPrintWriter.println((String) collectDomains2.valueAt(i2));
            }
            indentingPrintWriter.decreaseIndent();
        }
        indentingPrintWriter.println("Domain verification state:");
        indentingPrintWriter.increaseIndent();
        int size3 = arrayMap.size();
        for (int i3 = 0; i3 < size3; i3++) {
            String str = (String) arrayMap.keyAt(i3);
            Integer num = (Integer) arrayMap.valueAt(i3);
            indentingPrintWriter.print(str);
            indentingPrintWriter.print(": ");
            indentingPrintWriter.println(
                    DomainVerificationState.stateToDebugString(num.intValue()));
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.decreaseIndent();
        return true;
    }
}
