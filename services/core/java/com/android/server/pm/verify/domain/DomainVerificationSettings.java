package com.android.server.pm.verify.domain;

import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.SparseArray;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.pm.Computer;
import com.android.server.pm.parsing.pkg.AndroidPackageInternal;
import com.android.server.pm.pkg.PackageStateInternal;
import com.android.server.pm.verify.domain.DomainVerificationPersistence;
import com.android.server.pm.verify.domain.models.DomainVerificationInternalUserState;
import com.android.server.pm.verify.domain.models.DomainVerificationPkgState;
import com.android.server.pm.verify.domain.models.DomainVerificationStateMap;
import java.util.Collections;
import java.util.Set;
import java.util.function.Function;

/* loaded from: classes3.dex */
public class DomainVerificationSettings {
    public final DomainVerificationCollector mCollector;
    public final ArrayMap mPendingPkgStates = new ArrayMap();
    public final ArrayMap mRestoredPkgStates = new ArrayMap();
    public final Object mLock = new Object();

    public DomainVerificationSettings(DomainVerificationCollector domainVerificationCollector) {
        this.mCollector = domainVerificationCollector;
    }

    public void writeSettings(TypedXmlSerializer typedXmlSerializer, DomainVerificationStateMap domainVerificationStateMap, int i, Function function) {
        synchronized (this.mLock) {
            DomainVerificationPersistence.writeToXml(typedXmlSerializer, domainVerificationStateMap, this.mPendingPkgStates, this.mRestoredPkgStates, i, function);
        }
    }

    public void readSettings(TypedXmlPullParser typedXmlPullParser, DomainVerificationStateMap domainVerificationStateMap, Computer computer) {
        DomainVerificationPersistence.ReadResult readFromXml = DomainVerificationPersistence.readFromXml(typedXmlPullParser);
        ArrayMap arrayMap = readFromXml.active;
        ArrayMap arrayMap2 = readFromXml.restored;
        synchronized (this.mLock) {
            int size = arrayMap.size();
            for (int i = 0; i < size; i++) {
                DomainVerificationPkgState domainVerificationPkgState = (DomainVerificationPkgState) arrayMap.valueAt(i);
                String packageName = domainVerificationPkgState.getPackageName();
                DomainVerificationPkgState domainVerificationPkgState2 = (DomainVerificationPkgState) domainVerificationStateMap.get(packageName);
                if (domainVerificationPkgState2 != null) {
                    if (!domainVerificationPkgState2.getId().equals(domainVerificationPkgState.getId())) {
                        mergePkgState(domainVerificationPkgState2, domainVerificationPkgState, computer);
                    }
                } else {
                    this.mPendingPkgStates.put(packageName, domainVerificationPkgState);
                }
            }
            int size2 = arrayMap2.size();
            for (int i2 = 0; i2 < size2; i2++) {
                DomainVerificationPkgState domainVerificationPkgState3 = (DomainVerificationPkgState) arrayMap2.valueAt(i2);
                this.mRestoredPkgStates.put(domainVerificationPkgState3.getPackageName(), domainVerificationPkgState3);
            }
        }
    }

    public void restoreSettings(TypedXmlPullParser typedXmlPullParser, DomainVerificationStateMap domainVerificationStateMap, Computer computer) {
        DomainVerificationPersistence.ReadResult readFromXml = DomainVerificationPersistence.readFromXml(typedXmlPullParser);
        ArrayMap arrayMap = readFromXml.restored;
        arrayMap.putAll(readFromXml.active);
        synchronized (this.mLock) {
            for (int i = 0; i < arrayMap.size(); i++) {
                DomainVerificationPkgState domainVerificationPkgState = (DomainVerificationPkgState) arrayMap.valueAt(i);
                String packageName = domainVerificationPkgState.getPackageName();
                DomainVerificationPkgState domainVerificationPkgState2 = (DomainVerificationPkgState) domainVerificationStateMap.get(packageName);
                if (domainVerificationPkgState2 == null) {
                    domainVerificationPkgState2 = (DomainVerificationPkgState) this.mPendingPkgStates.get(packageName);
                }
                if (domainVerificationPkgState2 == null) {
                    domainVerificationPkgState2 = (DomainVerificationPkgState) this.mRestoredPkgStates.get(packageName);
                }
                if (domainVerificationPkgState2 != null) {
                    mergePkgState(domainVerificationPkgState2, domainVerificationPkgState, computer);
                } else {
                    ArrayMap stateMap = domainVerificationPkgState.getStateMap();
                    for (int size = stateMap.size() - 1; size >= 0; size--) {
                        Integer num = (Integer) stateMap.valueAt(size);
                        if (num != null) {
                            int intValue = num.intValue();
                            if (intValue != 1 && intValue != 5) {
                                stateMap.removeAt(size);
                            }
                            stateMap.setValueAt(size, 5);
                        }
                    }
                    this.mRestoredPkgStates.put(packageName, domainVerificationPkgState);
                }
            }
        }
    }

    public void mergePkgState(DomainVerificationPkgState domainVerificationPkgState, DomainVerificationPkgState domainVerificationPkgState2, Computer computer) {
        Integer num;
        PackageStateInternal packageStateInternal = computer.getPackageStateInternal(domainVerificationPkgState.getPackageName());
        AndroidPackageInternal pkg = packageStateInternal == null ? null : packageStateInternal.getPkg();
        Set emptySet = pkg == null ? Collections.emptySet() : this.mCollector.collectValidAutoVerifyDomains(pkg);
        ArrayMap stateMap = domainVerificationPkgState.getStateMap();
        ArrayMap stateMap2 = domainVerificationPkgState2.getStateMap();
        int size = stateMap2.size();
        for (int i = 0; i < size; i++) {
            String str = (String) stateMap2.keyAt(i);
            Integer num2 = (Integer) stateMap2.valueAt(i);
            if (emptySet.contains(str) && (((num = (Integer) stateMap.get(str)) == null || num.intValue() == 0) && (num2.intValue() == 1 || num2.intValue() == 5))) {
                stateMap.put(str, 5);
            }
        }
        SparseArray userStates = domainVerificationPkgState.getUserStates();
        SparseArray userStates2 = domainVerificationPkgState2.getUserStates();
        int size2 = userStates2.size();
        for (int i2 = 0; i2 < size2; i2++) {
            int keyAt = userStates2.keyAt(i2);
            DomainVerificationInternalUserState domainVerificationInternalUserState = (DomainVerificationInternalUserState) userStates2.valueAt(i2);
            if (domainVerificationInternalUserState != null) {
                ArraySet enabledHosts = domainVerificationInternalUserState.getEnabledHosts();
                DomainVerificationInternalUserState domainVerificationInternalUserState2 = (DomainVerificationInternalUserState) userStates.get(keyAt);
                boolean isLinkHandlingAllowed = domainVerificationInternalUserState.isLinkHandlingAllowed();
                if (domainVerificationInternalUserState2 == null) {
                    userStates.put(keyAt, new DomainVerificationInternalUserState(keyAt, enabledHosts, isLinkHandlingAllowed));
                } else {
                    domainVerificationInternalUserState2.addHosts(enabledHosts).setLinkHandlingAllowed(isLinkHandlingAllowed);
                }
            }
        }
    }

    public void removePackage(String str) {
        synchronized (this.mLock) {
            this.mPendingPkgStates.remove(str);
            this.mRestoredPkgStates.remove(str);
        }
    }

    public void removePackageForUser(String str, int i) {
        synchronized (this.mLock) {
            DomainVerificationPkgState domainVerificationPkgState = (DomainVerificationPkgState) this.mPendingPkgStates.get(str);
            if (domainVerificationPkgState != null) {
                domainVerificationPkgState.removeUser(i);
            }
            DomainVerificationPkgState domainVerificationPkgState2 = (DomainVerificationPkgState) this.mRestoredPkgStates.get(str);
            if (domainVerificationPkgState2 != null) {
                domainVerificationPkgState2.removeUser(i);
            }
        }
    }

    public void removeUser(int i) {
        synchronized (this.mLock) {
            int size = this.mPendingPkgStates.size();
            for (int i2 = 0; i2 < size; i2++) {
                ((DomainVerificationPkgState) this.mPendingPkgStates.valueAt(i2)).removeUser(i);
            }
            int size2 = this.mRestoredPkgStates.size();
            for (int i3 = 0; i3 < size2; i3++) {
                ((DomainVerificationPkgState) this.mRestoredPkgStates.valueAt(i3)).removeUser(i);
            }
        }
    }

    public DomainVerificationPkgState removePendingState(String str) {
        DomainVerificationPkgState domainVerificationPkgState;
        synchronized (this.mLock) {
            domainVerificationPkgState = (DomainVerificationPkgState) this.mPendingPkgStates.remove(str);
        }
        return domainVerificationPkgState;
    }

    public DomainVerificationPkgState removeRestoredState(String str) {
        DomainVerificationPkgState domainVerificationPkgState;
        synchronized (this.mLock) {
            domainVerificationPkgState = (DomainVerificationPkgState) this.mRestoredPkgStates.remove(str);
        }
        return domainVerificationPkgState;
    }
}
