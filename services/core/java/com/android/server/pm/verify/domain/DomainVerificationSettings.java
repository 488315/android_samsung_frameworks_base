package com.android.server.pm.verify.domain;

import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.SparseArray;

import com.android.modules.utils.TypedXmlPullParser;
import com.android.server.pm.Computer;
import com.android.server.pm.ComputerLocked;
import com.android.server.pm.PackageSetting;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.verify.domain.models.DomainVerificationInternalUserState;
import com.android.server.pm.verify.domain.models.DomainVerificationPkgState;
import com.android.server.pm.verify.domain.models.DomainVerificationStateMap;

import java.util.Collections;
import java.util.Set;

public final class DomainVerificationSettings {
    public final DomainVerificationCollector mCollector;
    public final ArrayMap mPendingPkgStates = new ArrayMap();
    public final ArrayMap mRestoredPkgStates = new ArrayMap();
    public final Object mLock = new Object();

    public DomainVerificationSettings(DomainVerificationCollector domainVerificationCollector) {
        this.mCollector = domainVerificationCollector;
    }

    public void mergePkgState(
            DomainVerificationPkgState domainVerificationPkgState,
            DomainVerificationPkgState domainVerificationPkgState2,
            Computer computer) {
        Integer num;
        PackageSetting packageStateInternal =
                computer.getPackageStateInternal(domainVerificationPkgState.mPackageName);
        AndroidPackage androidPackage =
                packageStateInternal == null ? null : packageStateInternal.pkg;
        Set emptySet =
                androidPackage == null
                        ? Collections.emptySet()
                        : this.mCollector.collectDomains(androidPackage, true, true);
        ArrayMap arrayMap = domainVerificationPkgState.mStateMap;
        ArrayMap arrayMap2 = domainVerificationPkgState2.mStateMap;
        int size = arrayMap2.size();
        for (int i = 0; i < size; i++) {
            String str = (String) arrayMap2.keyAt(i);
            Integer num2 = (Integer) arrayMap2.valueAt(i);
            if (emptySet.contains(str)
                    && (((num = (Integer) arrayMap.get(str)) == null || num.intValue() == 0)
                            && (num2.intValue() == 1 || num2.intValue() == 5))) {
                arrayMap.put(str, 5);
            }
        }
        SparseArray sparseArray = domainVerificationPkgState.mUserStates;
        SparseArray sparseArray2 = domainVerificationPkgState2.mUserStates;
        int size2 = sparseArray2.size();
        for (int i2 = 0; i2 < size2; i2++) {
            int keyAt = sparseArray2.keyAt(i2);
            DomainVerificationInternalUserState domainVerificationInternalUserState =
                    (DomainVerificationInternalUserState) sparseArray2.valueAt(i2);
            if (domainVerificationInternalUserState != null) {
                ArraySet arraySet = domainVerificationInternalUserState.mEnabledHosts;
                DomainVerificationInternalUserState domainVerificationInternalUserState2 =
                        (DomainVerificationInternalUserState) sparseArray.get(keyAt);
                boolean z = domainVerificationInternalUserState.mLinkHandlingAllowed;
                if (domainVerificationInternalUserState2 == null) {
                    sparseArray.put(
                            keyAt, new DomainVerificationInternalUserState(keyAt, arraySet, z));
                } else {
                    domainVerificationInternalUserState2.mEnabledHosts.addAll(arraySet);
                    domainVerificationInternalUserState2.mLinkHandlingAllowed = z;
                }
            }
        }
    }

    public final void readSettings(
            TypedXmlPullParser typedXmlPullParser,
            DomainVerificationStateMap domainVerificationStateMap,
            ComputerLocked computerLocked) {
        DomainVerificationPersistence.ReadResult readFromXml =
                DomainVerificationPersistence.readFromXml(typedXmlPullParser);
        ArrayMap arrayMap = readFromXml.active;
        ArrayMap arrayMap2 = readFromXml.restored;
        synchronized (this.mLock) {
            try {
                int size = arrayMap.size();
                for (int i = 0; i < size; i++) {
                    DomainVerificationPkgState domainVerificationPkgState =
                            (DomainVerificationPkgState) arrayMap.valueAt(i);
                    String str = domainVerificationPkgState.mPackageName;
                    DomainVerificationPkgState domainVerificationPkgState2 =
                            (DomainVerificationPkgState)
                                    domainVerificationStateMap.mPackageNameMap.get(str);
                    if (domainVerificationPkgState2 == null) {
                        this.mPendingPkgStates.put(str, domainVerificationPkgState);
                    } else if (!domainVerificationPkgState2.mId.equals(
                            domainVerificationPkgState.mId)) {
                        mergePkgState(
                                domainVerificationPkgState2,
                                domainVerificationPkgState,
                                computerLocked);
                    }
                }
                int size2 = arrayMap2.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    DomainVerificationPkgState domainVerificationPkgState3 =
                            (DomainVerificationPkgState) arrayMap2.valueAt(i2);
                    this.mRestoredPkgStates.put(
                            domainVerificationPkgState3.mPackageName, domainVerificationPkgState3);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void removeUser(int i) {
        synchronized (this.mLock) {
            try {
                int size = this.mPendingPkgStates.size();
                for (int i2 = 0; i2 < size; i2++) {
                    ((DomainVerificationPkgState) this.mPendingPkgStates.valueAt(i2)).removeUser(i);
                }
                int size2 = this.mRestoredPkgStates.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    ((DomainVerificationPkgState) this.mRestoredPkgStates.valueAt(i3))
                            .removeUser(i);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void restoreSettings(
            TypedXmlPullParser typedXmlPullParser,
            DomainVerificationStateMap domainVerificationStateMap,
            Computer computer) {
        DomainVerificationPersistence.ReadResult readFromXml =
                DomainVerificationPersistence.readFromXml(typedXmlPullParser);
        ArrayMap arrayMap = readFromXml.restored;
        arrayMap.putAll(readFromXml.active);
        synchronized (this.mLock) {
            for (int i = 0; i < arrayMap.size(); i++) {
                try {
                    DomainVerificationPkgState domainVerificationPkgState =
                            (DomainVerificationPkgState) arrayMap.valueAt(i);
                    String str = domainVerificationPkgState.mPackageName;
                    DomainVerificationPkgState domainVerificationPkgState2 =
                            (DomainVerificationPkgState)
                                    domainVerificationStateMap.mPackageNameMap.get(str);
                    if (domainVerificationPkgState2 == null) {
                        domainVerificationPkgState2 =
                                (DomainVerificationPkgState) this.mPendingPkgStates.get(str);
                    }
                    if (domainVerificationPkgState2 == null) {
                        domainVerificationPkgState2 =
                                (DomainVerificationPkgState) this.mRestoredPkgStates.get(str);
                    }
                    if (domainVerificationPkgState2 != null) {
                        mergePkgState(
                                domainVerificationPkgState2, domainVerificationPkgState, computer);
                    } else {
                        ArrayMap arrayMap2 = domainVerificationPkgState.mStateMap;
                        for (int size = arrayMap2.size() - 1; size >= 0; size--) {
                            Integer num = (Integer) arrayMap2.valueAt(size);
                            if (num != null) {
                                int intValue = num.intValue();
                                if (intValue != 1 && intValue != 5) {
                                    arrayMap2.removeAt(size);
                                }
                                arrayMap2.setValueAt(size, 5);
                            }
                        }
                        this.mRestoredPkgStates.put(str, domainVerificationPkgState);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }
}
