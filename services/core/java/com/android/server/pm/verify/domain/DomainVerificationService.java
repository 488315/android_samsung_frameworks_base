package com.android.server.pm.verify.domain;

import android.content.Context;
import android.content.Intent;
import android.content.UriRelativeFilterGroup;
import android.content.UriRelativeFilterGroupParcel;
import android.content.pm.IntentFilterVerificationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.verify.domain.DomainOwner;
import android.content.pm.verify.domain.DomainSet;
import android.content.pm.verify.domain.DomainVerificationInfo;
import android.content.pm.verify.domain.DomainVerificationState;
import android.content.pm.verify.domain.DomainVerificationUserState;
import android.os.Binder;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.IndentingPrintWriter;
import android.util.PackageUtils;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseIntArray;
import com.android.internal.pm.parsing.pkg.AndroidPackageInternal;
import com.android.internal.pm.pkg.component.ParsedActivity;
import com.android.internal.util.CollectionUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.SystemConfig;
import com.android.server.SystemService;
import com.android.server.compat.PlatformCompat;
import com.android.server.pm.Computer;
import com.android.server.pm.ComputerLocked;
import com.android.server.pm.DomainVerificationConnection;
import com.android.server.pm.PackageSetting;
import com.android.server.pm.SettingsXml$ReadSectionImpl;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageStateInternal;
import com.android.server.pm.verify.domain.DomainVerificationLegacySettings;
import com.android.server.pm.verify.domain.models.DomainVerificationInternalUserState;
import com.android.server.pm.verify.domain.models.DomainVerificationPkgState;
import com.android.server.pm.verify.domain.models.DomainVerificationStateMap;
import com.android.server.pm.verify.domain.proxy.DomainVerificationProxy;
import com.android.server.pm.verify.domain.proxy.DomainVerificationProxyUnavailable;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class DomainVerificationService extends SystemService implements DomainVerificationManagerInternal {
    public final DomainVerificationStateMap mAttachedPkgStates;
    public boolean mCanSendBroadcasts;
    public final DomainVerificationCollector mCollector;
    public DomainVerificationConnection mConnection;
    public final DomainVerificationDebug mDebug;
    public final DomainVerificationEnforcer mEnforcer;
    public final DomainVerificationLegacySettings mLegacySettings;
    public final Object mLock;
    public final PlatformCompat mPlatformCompat;
    public DomainVerificationProxy mProxy;
    public final DomainVerificationSettings mSettings;
    public final DomainVerificationShell mShell;
    public final DomainVerificationManagerStub mStub;
    public final SystemConfig mSystemConfig;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class GetAttachedResult {
        public final int mErrorCode;
        public final DomainVerificationPkgState mPkgState;

        public GetAttachedResult(DomainVerificationPkgState domainVerificationPkgState, int i) {
            this.mPkgState = domainVerificationPkgState;
            this.mErrorCode = i;
        }
    }

    public DomainVerificationService(Context context, SystemConfig systemConfig, PlatformCompat platformCompat) {
        super(context);
        this.mAttachedPkgStates = new DomainVerificationStateMap();
        this.mLock = new Object();
        this.mStub = new DomainVerificationManagerStub(this);
        this.mProxy = new DomainVerificationProxyUnavailable();
        this.mSystemConfig = systemConfig;
        this.mPlatformCompat = platformCompat;
        DomainVerificationCollector domainVerificationCollector = new DomainVerificationCollector(platformCompat, systemConfig);
        this.mCollector = domainVerificationCollector;
        this.mSettings = new DomainVerificationSettings(domainVerificationCollector);
        this.mEnforcer = new DomainVerificationEnforcer(context);
        this.mDebug = new DomainVerificationDebug(domainVerificationCollector);
        this.mShell = new DomainVerificationShell(this);
        this.mLegacySettings = new DomainVerificationLegacySettings();
    }

    public static void addIfShouldBroadcastLocked(Collection collection, DomainVerificationPkgState domainVerificationPkgState, boolean z) {
        if (!z || !domainVerificationPkgState.mHasAutoVerifyDomains) {
            if (!domainVerificationPkgState.mHasAutoVerifyDomains) {
                return;
            }
            ArrayMap arrayMap = domainVerificationPkgState.mStateMap;
            int size = arrayMap.size();
            for (int i = 0; i < size; i++) {
                if (!DomainVerificationState.isDefault(((Integer) arrayMap.valueAt(i)).intValue())) {
                    return;
                }
            }
        }
        ((ArraySet) collection).add(domainVerificationPkgState.mPackageName);
    }

    public static void applyPreVerifiedState(ArrayMap arrayMap, ArraySet arraySet, DomainSet domainSet) {
        if (domainSet == null || arraySet.isEmpty()) {
            return;
        }
        for (String str : domainSet.getDomains()) {
            if (arraySet.contains(str) && !arrayMap.containsKey(str)) {
                arrayMap.put(str, 8);
            }
        }
    }

    public static void debugApproval(String str, Object obj, int i, boolean z, String str2) {
        Slog.d("DomainVerificationServiceApproval", str + " was " + (z ? "approved" : "denied") + " for " + obj + " for user " + i + ": " + str2);
    }

    public static void fillInfoMapForSamePackage(ArrayMap arrayMap, String str, int i) {
        int size = arrayMap.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (Objects.equals(str, ((ResolveInfo) arrayMap.keyAt(i2)).getComponentInfo().packageName)) {
                arrayMap.setValueAt(i2, Integer.valueOf(i));
            }
        }
    }

    public static int indexOfIntentFilterEntry(AndroidPackage androidPackage, ResolveInfo resolveInfo) {
        List activities = androidPackage.getActivities();
        int size = activities.size();
        for (int i = 0; i < size; i++) {
            if (Objects.equals(((ParsedActivity) activities.get(i)).getComponentName(), resolveInfo.getComponentInfo().getComponentName())) {
                return i;
            }
        }
        return -1;
    }

    public final void addPackage(PackageStateInternal packageStateInternal, DomainSet domainSet) {
        DomainVerificationPkgState domainVerificationPkgState;
        boolean z;
        ArraySet arraySet;
        DomainVerificationPkgState domainVerificationPkgState2;
        SparseIntArray sparseIntArray;
        UUID domainSetId = packageStateInternal.getDomainSetId();
        String packageName = packageStateInternal.getPackageName();
        DomainVerificationSettings domainVerificationSettings = this.mSettings;
        synchronized (domainVerificationSettings.mLock) {
            domainVerificationPkgState = (DomainVerificationPkgState) domainVerificationSettings.mPendingPkgStates.remove(packageName);
        }
        if (domainVerificationPkgState != null) {
            z = false;
        } else {
            DomainVerificationSettings domainVerificationSettings2 = this.mSettings;
            synchronized (domainVerificationSettings2.mLock) {
                domainVerificationPkgState = (DomainVerificationPkgState) domainVerificationSettings2.mRestoredPkgStates.remove(packageName);
            }
            if (domainVerificationPkgState == null || Objects.equals(domainVerificationPkgState.mBackupSignatureHash, PackageUtils.computeSignaturesSha256Digest(packageStateInternal.getSigningDetails().getSignatures()))) {
                z = true;
            } else {
                z = true;
                domainVerificationPkgState = null;
            }
        }
        AndroidPackage pkg = packageStateInternal.getPkg();
        ArraySet collectDomains = this.mCollector.collectDomains(pkg, true, true);
        boolean z2 = !collectDomains.isEmpty();
        boolean z3 = domainVerificationPkgState != null;
        if (z3) {
            domainVerificationPkgState2 = r7;
            arraySet = collectDomains;
            DomainVerificationPkgState domainVerificationPkgState3 = new DomainVerificationPkgState(domainVerificationPkgState.mPackageName, domainSetId, z2, domainVerificationPkgState.mStateMap, domainVerificationPkgState.mUserStates, null, new ArrayMap());
            domainVerificationPkgState2.mStateMap.retainAll(arraySet);
            ArraySet collectDomains2 = this.mCollector.collectDomains(pkg, false, true);
            SparseArray sparseArray = domainVerificationPkgState2.mUserStates;
            int size = sparseArray.size();
            for (int i = 0; i < size; i++) {
                ((DomainVerificationInternalUserState) sparseArray.valueAt(i)).mEnabledHosts.retainAll(collectDomains2);
            }
        } else {
            arraySet = collectDomains;
            domainVerificationPkgState2 = new DomainVerificationPkgState(packageName, domainSetId, z2, new ArrayMap(0), new SparseArray(0), null, new ArrayMap());
        }
        DomainVerificationPkgState domainVerificationPkgState4 = domainVerificationPkgState2;
        if (applyImmutableState(packageStateInternal, domainVerificationPkgState4.mStateMap, arraySet) && !z3) {
            DomainVerificationLegacySettings domainVerificationLegacySettings = this.mLegacySettings;
            synchronized (domainVerificationLegacySettings.mLock) {
                try {
                    DomainVerificationLegacySettings.LegacyState legacyState = (DomainVerificationLegacySettings.LegacyState) domainVerificationLegacySettings.mStates.get(packageName);
                    sparseIntArray = legacyState != null ? legacyState.mUserStates : null;
                } finally {
                }
            }
            int size2 = sparseIntArray == null ? 0 : sparseIntArray.size();
            ArraySet arraySet2 = null;
            for (int i2 = 0; i2 < size2; i2++) {
                int keyAt = sparseIntArray.keyAt(i2);
                if (sparseIntArray.valueAt(i2) == 2) {
                    if (arraySet2 == null) {
                        arraySet2 = this.mCollector.collectDomains(pkg, false, true);
                    }
                    domainVerificationPkgState4.getOrCreateUserState(keyAt).mEnabledHosts.addAll(arraySet2);
                }
            }
            IntentFilterVerificationInfo remove = this.mLegacySettings.remove(packageName);
            if (remove != null && remove.getStatus() == 2) {
                ArrayMap arrayMap = domainVerificationPkgState4.mStateMap;
                int size3 = arraySet.size();
                for (int i3 = 0; i3 < size3; i3++) {
                    arrayMap.put((String) arraySet.valueAt(i3), 4);
                }
            }
            applyPreVerifiedState(domainVerificationPkgState4.mStateMap, arraySet, domainSet);
        }
        synchronized (this.mLock) {
            this.mAttachedPkgStates.put(packageName, domainSetId, domainVerificationPkgState4);
        }
        if (z && z2) {
            Set singleton = Collections.singleton(packageName);
            if (this.mCanSendBroadcasts) {
                this.mProxy.sendBroadcastForPackages(singleton);
            }
        }
    }

    public final boolean applyImmutableState(PackageStateInternal packageStateInternal, ArrayMap arrayMap, ArraySet arraySet) {
        if (packageStateInternal.isSystem() && this.mSystemConfig.mLinkedApps.contains(packageStateInternal.getPackageName())) {
            int size = arraySet.size();
            for (int i = 0; i < size; i++) {
                arrayMap.put((String) arraySet.valueAt(i), 7);
            }
            return false;
        }
        for (int size2 = arrayMap.size() - 1; size2 >= 0; size2--) {
            if (((Integer) arrayMap.valueAt(size2)).intValue() == 7) {
                arrayMap.removeAt(size2);
            }
        }
        return true;
    }

    public final int approvalLevelForDomain(PackageStateInternal packageStateInternal, Intent intent, long j, int i) {
        String packageName = packageStateInternal.getPackageName();
        boolean z = (intent.getFlags() & 8) != 0;
        if (!DomainVerificationUtils.isDomainVerificationIntent(intent, j)) {
            if (z) {
                debugApproval(packageName, intent, i, false, "not valid intent");
            }
            return 0;
        }
        int approvalLevelForDomain = approvalLevelForDomain(packageStateInternal, intent.getData().getHost(), false, i, z, intent);
        if (z) {
            StringBuilder sb = new StringBuilder("Final approval level for ");
            sb.append(packageStateInternal.getPackageName());
            sb.append(" for host ");
            sb.append(intent.getData().getHost());
            sb.append(" is ");
            DeviceIdleController$$ExternalSyntheticOutline0.m(sb, approvalLevelForDomain, "DomainVerificationServiceApproval");
        }
        return approvalLevelForDomain;
    }

    /* JADX WARN: Code restructure failed: missing block: B:64:0x00c8, code lost:
    
        if (r8 != 4) goto L50;
     */
    /* JADX WARN: Removed duplicated region for block: B:10:0x01d4 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:13:0x01e0 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:15:0x01e2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int approvalLevelForDomain(com.android.server.pm.pkg.PackageStateInternal r20, final java.lang.String r21, boolean r22, int r23, boolean r24, java.lang.Object r25) {
        /*
            Method dump skipped, instructions count: 552
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.verify.domain.DomainVerificationService.approvalLevelForDomain(com.android.server.pm.pkg.PackageStateInternal, java.lang.String, boolean, int, boolean, java.lang.Object):int");
    }

    public final void clearPackageForUser(int i, String str) {
        synchronized (this.mLock) {
            try {
                DomainVerificationPkgState domainVerificationPkgState = (DomainVerificationPkgState) this.mAttachedPkgStates.mPackageNameMap.get(str);
                if (domainVerificationPkgState != null) {
                    domainVerificationPkgState.removeUser(i);
                }
                DomainVerificationSettings domainVerificationSettings = this.mSettings;
                synchronized (domainVerificationSettings.mLock) {
                    try {
                        DomainVerificationPkgState domainVerificationPkgState2 = (DomainVerificationPkgState) domainVerificationSettings.mPendingPkgStates.get(str);
                        if (domainVerificationPkgState2 != null) {
                            domainVerificationPkgState2.removeUser(i);
                        }
                        DomainVerificationPkgState domainVerificationPkgState3 = (DomainVerificationPkgState) domainVerificationSettings.mRestoredPkgStates.get(str);
                        if (domainVerificationPkgState3 != null) {
                            domainVerificationPkgState3.removeUser(i);
                        }
                    } finally {
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        this.mConnection.scheduleWriteSettings();
    }

    public final void clearUser(int i) {
        synchronized (this.mLock) {
            try {
                int size = this.mAttachedPkgStates.mPackageNameMap.size();
                for (int i2 = 0; i2 < size; i2++) {
                    ((DomainVerificationPkgState) this.mAttachedPkgStates.mPackageNameMap.valueAt(i2)).removeUser(i);
                }
                this.mSettings.removeUser(i);
            } catch (Throwable th) {
                throw th;
            }
        }
        this.mConnection.scheduleWriteSettings();
    }

    public final GetAttachedResult getAndValidateAttachedLocked(UUID uuid, Set set, boolean z, int i, Integer num, Computer computer) {
        if (uuid == null) {
            throw new IllegalArgumentException("domainSetId cannot be null");
        }
        DomainVerificationPkgState domainVerificationPkgState = (DomainVerificationPkgState) this.mAttachedPkgStates.mDomainSetIdMap.get(uuid);
        if (domainVerificationPkgState == null) {
            return new GetAttachedResult(null, 1);
        }
        String str = domainVerificationPkgState.mPackageName;
        if (num != null) {
            if (this.mConnection.mPm.snapshotComputer().filterAppAccess(i, num.intValue(), str, true)) {
                return new GetAttachedResult(null, 1);
            }
        }
        PackageSetting packageStateInternal = computer.getPackageStateInternal(str);
        if (packageStateInternal == null || packageStateInternal.pkg == null) {
            DomainVerificationUtils.throwPackageUnavailable(str);
            throw null;
        }
        if (CollectionUtils.isEmpty(set)) {
            throw new IllegalArgumentException("Provided domain set cannot be empty");
        }
        AndroidPackage androidPackage = packageStateInternal.pkg;
        DomainVerificationCollector domainVerificationCollector = this.mCollector;
        return set.retainAll(z ? domainVerificationCollector.collectDomains(androidPackage, true, true) : domainVerificationCollector.collectDomains(androidPackage, false, true)) ? new GetAttachedResult(null, 2) : new GetAttachedResult(domainVerificationPkgState, 0);
    }

    public final DomainVerificationCollector getCollector() {
        return this.mCollector;
    }

    public final DomainVerificationInfo getDomainVerificationInfo(String str) {
        DomainVerificationEnforcer domainVerificationEnforcer = this.mEnforcer;
        this.mConnection.getClass();
        domainVerificationEnforcer.assertApprovedQuerent(Binder.getCallingUid(), this.mProxy);
        Computer snapshotComputer = this.mConnection.mPm.snapshotComputer();
        synchronized (this.mLock) {
            try {
                PackageSetting packageStateInternal = snapshotComputer.getPackageStateInternal(str);
                AndroidPackage androidPackage = packageStateInternal == null ? null : packageStateInternal.pkg;
                if (androidPackage == null) {
                    DomainVerificationUtils.throwPackageUnavailable(str);
                    throw null;
                }
                DomainVerificationPkgState domainVerificationPkgState = (DomainVerificationPkgState) this.mAttachedPkgStates.mPackageNameMap.get(str);
                if (domainVerificationPkgState == null) {
                    return null;
                }
                ArrayMap arrayMap = new ArrayMap(domainVerificationPkgState.mStateMap);
                ArraySet collectDomains = this.mCollector.collectDomains(androidPackage, true, true);
                if (collectDomains.isEmpty()) {
                    return null;
                }
                int size = collectDomains.size();
                for (int i = 0; i < size; i++) {
                    arrayMap.putIfAbsent((String) collectDomains.valueAt(i), 0);
                }
                int size2 = arrayMap.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    arrayMap.setValueAt(i2, Integer.valueOf(DomainVerificationState.convertToInfoState(((Integer) arrayMap.valueAt(i2)).intValue())));
                }
                return new DomainVerificationInfo(domainVerificationPkgState.mId, str, arrayMap);
            } finally {
            }
        }
    }

    public final DomainVerificationUserState getDomainVerificationUserState(String str, int i) {
        boolean z;
        DomainVerificationEnforcer domainVerificationEnforcer = this.mEnforcer;
        this.mConnection.getClass();
        int callingUid = Binder.getCallingUid();
        this.mConnection.getClass();
        int callingUserId = UserHandle.getCallingUserId();
        if (callingUserId != i) {
            domainVerificationEnforcer.mContext.enforcePermission("android.permission.INTERACT_ACROSS_USERS", Binder.getCallingPid(), callingUid, "Caller is not allowed to edit other users");
        }
        if (!domainVerificationEnforcer.mCallback.mUmInternal.exists(callingUserId)) {
            throw new SecurityException(BinaryTransparencyService$$ExternalSyntheticOutline0.m(callingUserId, "User ", " does not exist"));
        }
        if (!domainVerificationEnforcer.mCallback.mUmInternal.exists(i)) {
            throw new SecurityException(BinaryTransparencyService$$ExternalSyntheticOutline0.m(i, "User ", " does not exist"));
        }
        if (!(!domainVerificationEnforcer.mCallback.mPm.snapshotComputer().filterAppAccess(callingUid, i, str, true))) {
            DomainVerificationUtils.throwPackageUnavailable(str);
            throw null;
        }
        Computer snapshotComputer = this.mConnection.mPm.snapshotComputer();
        synchronized (this.mLock) {
            try {
                PackageSetting packageStateInternal = snapshotComputer.getPackageStateInternal(str);
                AndroidPackage androidPackage = packageStateInternal == null ? null : packageStateInternal.pkg;
                if (androidPackage == null) {
                    DomainVerificationUtils.throwPackageUnavailable(str);
                    throw null;
                }
                DomainVerificationPkgState domainVerificationPkgState = (DomainVerificationPkgState) this.mAttachedPkgStates.mPackageNameMap.get(str);
                if (domainVerificationPkgState == null) {
                    return null;
                }
                ArraySet collectDomains = this.mCollector.collectDomains(androidPackage, false, true);
                int size = collectDomains.size();
                ArrayMap arrayMap = new ArrayMap(size);
                ArrayMap arrayMap2 = domainVerificationPkgState.mStateMap;
                DomainVerificationInternalUserState domainVerificationInternalUserState = (DomainVerificationInternalUserState) domainVerificationPkgState.mUserStates.get(i);
                Set emptySet = domainVerificationInternalUserState == null ? Collections.emptySet() : domainVerificationInternalUserState.mEnabledHosts;
                for (int i2 = 0; i2 < size; i2++) {
                    String str2 = (String) collectDomains.valueAt(i2);
                    Integer num = (Integer) arrayMap2.get(str2);
                    arrayMap.put(str2, Integer.valueOf((num == null || !DomainVerificationState.isVerified(num.intValue())) ? emptySet.contains(str2) ? 1 : 0 : 2));
                }
                if (domainVerificationInternalUserState != null && !domainVerificationInternalUserState.mLinkHandlingAllowed) {
                    z = false;
                    return new DomainVerificationUserState(domainVerificationPkgState.mId, str, UserHandle.of(i), z, arrayMap);
                }
                z = true;
                return new DomainVerificationUserState(domainVerificationPkgState.mId, str, UserHandle.of(i), z, arrayMap);
            } finally {
            }
        }
    }

    public final List getOwnersForDomain(String str, int i) {
        Objects.requireNonNull(str);
        this.mConnection.getClass();
        int callingUid = Binder.getCallingUid();
        this.mConnection.getClass();
        int callingUserId = UserHandle.getCallingUserId();
        DomainVerificationEnforcer domainVerificationEnforcer = this.mEnforcer;
        domainVerificationEnforcer.getClass();
        int callingPid = Binder.getCallingPid();
        if (callingUserId != i) {
            domainVerificationEnforcer.mContext.enforcePermission("android.permission.INTERACT_ACROSS_USERS", callingPid, callingUid, "Caller is not allowed to query other users");
        }
        domainVerificationEnforcer.mContext.enforcePermission("android.permission.QUERY_ALL_PACKAGES", callingPid, callingUid, BinaryTransparencyService$$ExternalSyntheticOutline0.m(callingUid, "Caller ", " does not hold android.permission.QUERY_ALL_PACKAGES"));
        domainVerificationEnforcer.mContext.enforcePermission("android.permission.UPDATE_DOMAIN_VERIFICATION_USER_SELECTION", callingPid, callingUid, "Caller is not allowed to query user selections");
        if (!domainVerificationEnforcer.mCallback.mUmInternal.exists(callingUserId)) {
            throw new SecurityException(BinaryTransparencyService$$ExternalSyntheticOutline0.m(callingUserId, "User ", " does not exist"));
        }
        if (!domainVerificationEnforcer.mCallback.mUmInternal.exists(i)) {
            throw new SecurityException(BinaryTransparencyService$$ExternalSyntheticOutline0.m(i, "User ", " does not exist"));
        }
        SparseArray ownersForDomainInternal = getOwnersForDomainInternal(i, this.mConnection.mPm.snapshotComputer(), str, false);
        if (ownersForDomainInternal.size() == 0) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        int size = ownersForDomainInternal.size();
        for (int i2 = 0; i2 < size; i2++) {
            boolean z = ownersForDomainInternal.keyAt(i2) <= 3;
            List list = (List) ownersForDomainInternal.valueAt(i2);
            int size2 = list.size();
            for (int i3 = 0; i3 < size2; i3++) {
                arrayList.add(new DomainOwner((String) list.get(i3), z));
            }
        }
        return arrayList;
    }

    public final SparseArray getOwnersForDomainInternal(final int i, final Computer computer, String str, boolean z) {
        int i2;
        SparseArray sparseArray = new SparseArray();
        synchronized (this.mLock) {
            try {
                int size = this.mAttachedPkgStates.mPackageNameMap.size();
                for (int i3 = 0; i3 < size; i3++) {
                    String str2 = ((DomainVerificationPkgState) this.mAttachedPkgStates.mPackageNameMap.valueAt(i3)).mPackageName;
                    PackageSetting packageStateInternal = computer.getPackageStateInternal(str2);
                    if (packageStateInternal != null) {
                        int approvalLevelForDomain = approvalLevelForDomain(packageStateInternal, str, z, i, false, str);
                        if (z || approvalLevelForDomain > 0) {
                            List list = (List) sparseArray.get(approvalLevelForDomain);
                            if (list == null) {
                                list = new ArrayList();
                                sparseArray.put(approvalLevelForDomain, list);
                            }
                            list.add(str2);
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        int size2 = sparseArray.size();
        if (size2 == 0) {
            return sparseArray;
        }
        for (i2 = 0; i2 < size2; i2++) {
            ((List) sparseArray.valueAt(i2)).sort(new Comparator() { // from class: com.android.server.pm.verify.domain.DomainVerificationService$$ExternalSyntheticLambda0
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    Computer computer2 = Computer.this;
                    int i4 = i;
                    String str3 = (String) obj;
                    String str4 = (String) obj2;
                    PackageSetting packageStateInternal2 = computer2.getPackageStateInternal(str3);
                    PackageSetting packageStateInternal3 = computer2.getPackageStateInternal(str4);
                    long firstInstallTimeMillis = packageStateInternal2 == null ? -1L : packageStateInternal2.getUserStateOrDefault(i4).getFirstInstallTimeMillis();
                    long firstInstallTimeMillis2 = packageStateInternal3 != null ? packageStateInternal3.getUserStateOrDefault(i4).getFirstInstallTimeMillis() : -1L;
                    return firstInstallTimeMillis != firstInstallTimeMillis2 ? (int) (firstInstallTimeMillis - firstInstallTimeMillis2) : str3.compareToIgnoreCase(str4);
                }
            });
        }
        return sparseArray;
    }

    public final Bundle getUriRelativeFilterGroups(String str, List list) {
        Bundle bundle = new Bundle();
        synchronized (this.mLock) {
            try {
                DomainVerificationPkgState domainVerificationPkgState = (DomainVerificationPkgState) this.mAttachedPkgStates.mPackageNameMap.get(str);
                if (domainVerificationPkgState != null) {
                    ArrayMap arrayMap = domainVerificationPkgState.mUriRelativeFilterGroupMap;
                    for (int i = 0; i < list.size(); i++) {
                        if (arrayMap.containsKey(list.get(i))) {
                            bundle.putParcelableList((String) list.get(i), UriRelativeFilterGroup.groupsToParcels((List) arrayMap.get(list.get(i))));
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return bundle;
    }

    public final void migrateState(PackageStateInternal packageStateInternal, PackageStateInternal packageStateInternal2, DomainSet domainSet) {
        DomainSet domainSet2;
        boolean z;
        int indexOfValue;
        String packageName = packageStateInternal2.getPackageName();
        synchronized (this.mLock) {
            try {
                UUID domainSetId = packageStateInternal.getDomainSetId();
                UUID domainSetId2 = packageStateInternal2.getDomainSetId();
                DomainVerificationStateMap domainVerificationStateMap = this.mAttachedPkgStates;
                Object remove = domainVerificationStateMap.mDomainSetIdMap.remove(domainSetId);
                if (remove != null && (indexOfValue = domainVerificationStateMap.mPackageNameMap.indexOfValue(remove)) >= 0) {
                    domainVerificationStateMap.mPackageNameMap.removeAt(indexOfValue);
                }
                DomainVerificationPkgState domainVerificationPkgState = (DomainVerificationPkgState) remove;
                AndroidPackageInternal pkg = packageStateInternal.getPkg();
                AndroidPackage pkg2 = packageStateInternal2.getPkg();
                ArrayMap arrayMap = new ArrayMap();
                SparseArray sparseArray = new SparseArray();
                if (domainVerificationPkgState != null && pkg != null && pkg2 != null) {
                    ArrayMap arrayMap2 = domainVerificationPkgState.mStateMap;
                    ArrayMap arrayMap3 = domainVerificationPkgState.mUriRelativeFilterGroupMap;
                    ArraySet collectDomains = this.mCollector.collectDomains(pkg2, true, true);
                    int size = collectDomains.size();
                    for (int i = 0; i < size; i++) {
                        String str = (String) collectDomains.valueAt(i);
                        Integer num = (Integer) arrayMap2.get(str);
                        if (num != null && DomainVerificationState.shouldMigrate(num.intValue())) {
                            arrayMap.put(str, num);
                        }
                    }
                    SparseArray sparseArray2 = domainVerificationPkgState.mUserStates;
                    int size2 = sparseArray2.size();
                    if (size2 > 0) {
                        ArraySet collectDomains2 = this.mCollector.collectDomains(pkg2, false, true);
                        int i2 = 0;
                        while (i2 < size2) {
                            int keyAt = sparseArray2.keyAt(i2);
                            DomainVerificationInternalUserState domainVerificationInternalUserState = (DomainVerificationInternalUserState) sparseArray2.valueAt(i2);
                            int i3 = size2;
                            ArraySet arraySet = new ArraySet(domainVerificationInternalUserState.mEnabledHosts);
                            arraySet.retainAll(collectDomains2);
                            sparseArray.put(keyAt, new DomainVerificationInternalUserState(keyAt, arraySet, domainVerificationInternalUserState.mLinkHandlingAllowed));
                            i2++;
                            sparseArray2 = sparseArray2;
                            size2 = i3;
                        }
                    }
                    boolean z2 = size > 0;
                    boolean applyImmutableState = applyImmutableState(packageStateInternal2, arrayMap, collectDomains);
                    if (z2 && applyImmutableState) {
                        domainSet2 = domainSet;
                        z = true;
                    } else {
                        domainSet2 = domainSet;
                        z = false;
                    }
                    applyPreVerifiedState(arrayMap, collectDomains, domainSet2);
                    this.mAttachedPkgStates.put(packageName, domainSetId2, new DomainVerificationPkgState(packageName, domainSetId2, z2, arrayMap, sparseArray, null, arrayMap3));
                    if (z) {
                        Set singleton = Collections.singleton(packageName);
                        if (this.mCanSendBroadcasts) {
                            this.mProxy.sendBroadcastForPackages(singleton);
                            return;
                        }
                        return;
                    }
                    return;
                }
                Slog.wtf("DomainVerificationService", "Invalid state nullability old state = " + domainVerificationPkgState + ", old pkgSetting = " + packageStateInternal + ", new pkgSetting = " + packageStateInternal2 + ", old pkg = " + pkg + ", new pkg = " + pkg2, new Exception());
                this.mAttachedPkgStates.put(packageName, domainSetId2, new DomainVerificationPkgState(packageName, domainSetId2, true, arrayMap, sparseArray, null, new ArrayMap()));
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        super.onBootPhase(i);
        if (!(this.mProxy instanceof DomainVerificationProxyUnavailable)) {
            if (i == 550) {
                this.mCanSendBroadcasts = true;
            } else {
                if (i != 1000) {
                    return;
                }
                verifyPackages(null, false);
            }
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        publishBinderService("domain_verification", this.mStub);
    }

    @Override // com.android.server.SystemService
    public final void onUserUnlocked(SystemService.TargetUser targetUser) {
        super.onUserUnlocked(targetUser);
        verifyPackages(null, false);
    }

    public final void printOwnersForDomain(IndentingPrintWriter indentingPrintWriter, Computer computer, Integer num, String str) {
        String str2;
        SparseArray sparseArray = new SparseArray();
        int i = -1;
        if (num == null || num.intValue() == -1) {
            for (int i2 : this.mConnection.mUmInternal.getUserIds()) {
                sparseArray.put(i2, getOwnersForDomainInternal(i2, computer, str, true));
            }
        } else {
            sparseArray.put(num.intValue(), getOwnersForDomainInternal(num.intValue(), computer, str, true));
        }
        this.mDebug.getClass();
        indentingPrintWriter.println(str + ":");
        indentingPrintWriter.increaseIndent();
        if (sparseArray.size() == 0) {
            indentingPrintWriter.println("none");
            indentingPrintWriter.decreaseIndent();
            return;
        }
        int size = sparseArray.size();
        int i3 = 0;
        while (i3 < size) {
            int keyAt = sparseArray.keyAt(i3);
            SparseArray sparseArray2 = (SparseArray) sparseArray.valueAt(i3);
            if (sparseArray2.size() != 0) {
                int size2 = sparseArray2.size();
                int i4 = 0;
                boolean z = false;
                while (i4 < size2) {
                    int keyAt2 = sparseArray2.keyAt(i4);
                    if (keyAt2 >= i) {
                        if (!z) {
                            indentingPrintWriter.println("User " + keyAt + ":");
                            indentingPrintWriter.increaseIndent();
                            z = true;
                        }
                        switch (keyAt2) {
                            case -4:
                                str2 = "NOT_INSTALLED";
                                break;
                            case -3:
                                str2 = "DISABLED";
                                break;
                            case -2:
                                str2 = "UNDECLARED";
                                break;
                            case -1:
                                str2 = "UNVERIFIED";
                                break;
                            case 0:
                                str2 = "NONE";
                                break;
                            case 1:
                                str2 = "LEGACY_ASK";
                                break;
                            case 2:
                                str2 = "LEGACY_ALWAYS";
                                break;
                            case 3:
                                str2 = "USER_SELECTION";
                                break;
                            case 4:
                                str2 = "VERIFIED";
                                break;
                            case 5:
                                str2 = "INSTANT_APP";
                                break;
                            default:
                                str2 = "UNKNOWN";
                                break;
                        }
                        List list = (List) sparseArray2.valueAt(i4);
                        indentingPrintWriter.println(str2 + "[" + keyAt2 + "]:");
                        indentingPrintWriter.increaseIndent();
                        if (list.size() == 0) {
                            indentingPrintWriter.println("none");
                            indentingPrintWriter.decreaseIndent();
                        } else {
                            int size3 = list.size();
                            for (int i5 = 0; i5 < size3; i5++) {
                                indentingPrintWriter.println((String) list.get(i5));
                            }
                            indentingPrintWriter.decreaseIndent();
                        }
                    }
                    i4++;
                    i = -1;
                }
                if (z) {
                    indentingPrintWriter.decreaseIndent();
                }
            }
            i3++;
            i = -1;
        }
        indentingPrintWriter.decreaseIndent();
    }

    public final void printOwnersForDomains(IndentingPrintWriter indentingPrintWriter, List list, Integer num) {
        DomainVerificationEnforcer domainVerificationEnforcer = this.mEnforcer;
        this.mConnection.getClass();
        domainVerificationEnforcer.assertApprovedQuerent(Binder.getCallingUid(), this.mProxy);
        Computer snapshotComputer = this.mConnection.mPm.snapshotComputer();
        synchronized (this.mLock) {
            try {
                ArrayList arrayList = (ArrayList) list;
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    printOwnersForDomain(indentingPrintWriter, snapshotComputer, num, (String) arrayList.get(i));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void printOwnersForPackage(IndentingPrintWriter indentingPrintWriter, Computer computer, Integer num, String str) {
        PackageSetting packageStateInternal = computer.getPackageStateInternal(str);
        AndroidPackage androidPackage = packageStateInternal == null ? null : packageStateInternal.pkg;
        if (androidPackage == null) {
            DomainVerificationUtils.throwPackageUnavailable(str);
            throw null;
        }
        ArraySet collectDomains = this.mCollector.collectDomains(androidPackage, false, true);
        int size = collectDomains.size();
        if (size == 0) {
            return;
        }
        indentingPrintWriter.println(str + ":");
        indentingPrintWriter.increaseIndent();
        for (int i = 0; i < size; i++) {
            printOwnersForDomain(indentingPrintWriter, computer, num, (String) collectDomains.valueAt(i));
        }
        indentingPrintWriter.decreaseIndent();
    }

    public final void printOwnersForPackage(IndentingPrintWriter indentingPrintWriter, String str, Integer num) {
        DomainVerificationEnforcer domainVerificationEnforcer = this.mEnforcer;
        this.mConnection.getClass();
        domainVerificationEnforcer.assertApprovedQuerent(Binder.getCallingUid(), this.mProxy);
        Computer snapshotComputer = this.mConnection.mPm.snapshotComputer();
        synchronized (this.mLock) {
            if (str == null) {
                int size = this.mAttachedPkgStates.mPackageNameMap.size();
                for (int i = 0; i < size; i++) {
                    try {
                        printOwnersForPackage(indentingPrintWriter, snapshotComputer, num, ((DomainVerificationPkgState) this.mAttachedPkgStates.mPackageNameMap.valueAt(i)).mPackageName);
                    } catch (PackageManager.NameNotFoundException unused) {
                    }
                }
            } else {
                printOwnersForPackage(indentingPrintWriter, snapshotComputer, num, str);
            }
        }
    }

    public final void printState(IndentingPrintWriter indentingPrintWriter, Computer computer, Integer num, String str) {
        DomainVerificationEnforcer domainVerificationEnforcer = this.mEnforcer;
        this.mConnection.getClass();
        domainVerificationEnforcer.assertApprovedQuerent(Binder.getCallingUid(), this.mProxy);
        synchronized (this.mLock) {
            this.mDebug.printState(indentingPrintWriter, str, num, computer, this.mAttachedPkgStates);
        }
    }

    public final List queryValidVerificationPackageNames() {
        DomainVerificationEnforcer domainVerificationEnforcer = this.mEnforcer;
        this.mConnection.getClass();
        domainVerificationEnforcer.assertApprovedVerifier(Binder.getCallingUid(), this.mProxy);
        ArrayList arrayList = new ArrayList();
        synchronized (this.mLock) {
            try {
                int size = this.mAttachedPkgStates.mPackageNameMap.size();
                for (int i = 0; i < size; i++) {
                    DomainVerificationPkgState domainVerificationPkgState = (DomainVerificationPkgState) this.mAttachedPkgStates.mPackageNameMap.valueAt(i);
                    if (domainVerificationPkgState.mHasAutoVerifyDomains) {
                        arrayList.add(domainVerificationPkgState.mPackageName);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    public final void readLegacySettings(TypedXmlPullParser typedXmlPullParser) {
        DomainVerificationLegacySettings domainVerificationLegacySettings = this.mLegacySettings;
        domainVerificationLegacySettings.getClass();
        SettingsXml$ReadSectionImpl settingsXml$ReadSectionImpl = new SettingsXml$ReadSectionImpl(typedXmlPullParser);
        settingsXml$ReadSectionImpl.children();
        while (settingsXml$ReadSectionImpl.moveToNextInternal(null)) {
            if ("user-states".equals(((TypedXmlPullParser) settingsXml$ReadSectionImpl.mParser).getName())) {
                String string = settingsXml$ReadSectionImpl.getString("packageName");
                synchronized (domainVerificationLegacySettings.mLock) {
                    try {
                        DomainVerificationLegacySettings.LegacyState orCreateStateLocked = domainVerificationLegacySettings.getOrCreateStateLocked(string);
                        settingsXml$ReadSectionImpl.children();
                        while (settingsXml$ReadSectionImpl.moveToNextInternal(null)) {
                            if ("user-state".equals(((TypedXmlPullParser) settingsXml$ReadSectionImpl.mParser).getName())) {
                                int i = settingsXml$ReadSectionImpl.getInt(-1, "userId");
                                int i2 = settingsXml$ReadSectionImpl.getInt(-1, LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
                                if (orCreateStateLocked.mUserStates == null) {
                                    orCreateStateLocked.mUserStates = new SparseIntArray(1);
                                }
                                orCreateStateLocked.mUserStates.put(i, i2);
                            }
                        }
                    } finally {
                    }
                }
            }
        }
    }

    public final void readSettings(ComputerLocked computerLocked, TypedXmlPullParser typedXmlPullParser) {
        synchronized (this.mLock) {
            this.mSettings.readSettings(typedXmlPullParser, this.mAttachedPkgStates, computerLocked);
        }
    }

    public final void removeUserStatesForDomain(DomainVerificationPkgState domainVerificationPkgState, String str) {
        SparseArray sparseArray = domainVerificationPkgState.mUserStates;
        synchronized (this.mLock) {
            try {
                int size = this.mAttachedPkgStates.mPackageNameMap.size();
                for (int i = 0; i < size; i++) {
                    SparseArray sparseArray2 = ((DomainVerificationPkgState) this.mAttachedPkgStates.mPackageNameMap.valueAt(i)).mUserStates;
                    int size2 = sparseArray2.size();
                    for (int i2 = 0; i2 < size2; i2++) {
                        DomainVerificationInternalUserState domainVerificationInternalUserState = (DomainVerificationInternalUserState) sparseArray.get(sparseArray2.keyAt(i2));
                        if (domainVerificationInternalUserState == null || domainVerificationInternalUserState.mLinkHandlingAllowed) {
                            ((DomainVerificationInternalUserState) sparseArray2.valueAt(i2)).mEnabledHosts.remove(str);
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void resetDomainState(ArrayMap arrayMap, PackageStateInternal packageStateInternal) {
        for (int size = arrayMap.size() - 1; size >= 0; size--) {
            Integer num = (Integer) arrayMap.valueAt(size);
            int intValue = num.intValue();
            if (intValue == 1 || intValue == 5 || num.intValue() >= 1024) {
                arrayMap.removeAt(size);
            }
        }
        applyImmutableState(packageStateInternal, arrayMap, this.mCollector.collectDomains(packageStateInternal.getPkg(), true, true));
    }

    public final int revokeOtherUserSelectionsLocked(DomainVerificationInternalUserState domainVerificationInternalUserState, int i, Set set, Computer computer) {
        DomainVerificationInternalUserState domainVerificationInternalUserState2;
        Pair create;
        int i2;
        int i3;
        boolean z;
        int i4;
        List list;
        ArrayMap arrayMap = new ArrayMap();
        Iterator it = set.iterator();
        while (true) {
            boolean hasNext = it.hasNext();
            DomainVerificationStateMap domainVerificationStateMap = this.mAttachedPkgStates;
            if (!hasNext) {
                int size = arrayMap.size();
                for (int i5 = 0; i5 < size; i5++) {
                    String str = (String) arrayMap.keyAt(i5);
                    List list2 = (List) arrayMap.valueAt(i5);
                    int size2 = list2.size();
                    for (int i6 = 0; i6 < size2; i6++) {
                        DomainVerificationPkgState domainVerificationPkgState = (DomainVerificationPkgState) domainVerificationStateMap.mPackageNameMap.get((String) list2.get(i6));
                        if (domainVerificationPkgState != null && (domainVerificationInternalUserState2 = (DomainVerificationInternalUserState) domainVerificationPkgState.mUserStates.get(i)) != null) {
                            domainVerificationInternalUserState2.mEnabledHosts.remove(str);
                        }
                    }
                }
                return 0;
            }
            String str2 = (String) it.next();
            if (!domainVerificationInternalUserState.mEnabledHosts.contains(str2)) {
                List emptyList = Collections.emptyList();
                int size3 = domainVerificationStateMap.mPackageNameMap.size();
                boolean z2 = true;
                List list3 = emptyList;
                int i7 = 1;
                int i8 = 0;
                while (i8 < size3) {
                    String str3 = ((DomainVerificationPkgState) domainVerificationStateMap.mPackageNameMap.valueAt(i8)).mPackageName;
                    PackageSetting packageStateInternal = computer.getPackageStateInternal(str3);
                    if (packageStateInternal == null) {
                        i2 = i8;
                        z = z2;
                        i3 = size3;
                        i4 = i7;
                        list = list3;
                    } else {
                        int i9 = i7;
                        i2 = i8;
                        List list4 = list3;
                        i3 = size3;
                        int approvalLevelForDomain = approvalLevelForDomain(packageStateInternal, str2, false, i, false, str2);
                        z = true;
                        if (approvalLevelForDomain < 1) {
                            list = list4;
                            i4 = i9;
                        } else {
                            i4 = i9;
                            if (approvalLevelForDomain > i4) {
                                list4.clear();
                                list3 = CollectionUtils.add(list4, str3);
                                i7 = approvalLevelForDomain;
                                i8 = i2 + 1;
                                z2 = z;
                                size3 = i3;
                            } else {
                                list = list4;
                                if (approvalLevelForDomain == i4) {
                                    list3 = CollectionUtils.add(list, str3);
                                    i7 = i4;
                                    i8 = i2 + 1;
                                    z2 = z;
                                    size3 = i3;
                                }
                            }
                        }
                    }
                    list3 = list;
                    i7 = i4;
                    i8 = i2 + 1;
                    z2 = z;
                    size3 = i3;
                }
                int i10 = i7;
                List list5 = list3;
                if (list5.isEmpty()) {
                    create = Pair.create(list5, 0);
                } else {
                    ArrayList arrayList = new ArrayList();
                    int size4 = list5.size();
                    long j = Long.MIN_VALUE;
                    for (int i11 = 0; i11 < size4; i11++) {
                        String str4 = (String) list5.get(i11);
                        PackageSetting packageStateInternal2 = computer.getPackageStateInternal(str4);
                        if (packageStateInternal2 != null) {
                            long firstInstallTimeMillis = packageStateInternal2.getUserStateOrDefault(i).getFirstInstallTimeMillis();
                            if (firstInstallTimeMillis > j) {
                                arrayList.clear();
                                arrayList.add(str4);
                                j = firstInstallTimeMillis;
                            } else if (firstInstallTimeMillis == j) {
                                arrayList.add(str4);
                            }
                        }
                    }
                    create = Pair.create(arrayList, Integer.valueOf(i10));
                }
                if (((Integer) create.second).intValue() > 3) {
                    return 3;
                }
                arrayMap.put(str2, (List) create.first);
            }
        }
    }

    public final void setConnection(DomainVerificationConnection domainVerificationConnection) {
        this.mConnection = domainVerificationConnection;
        this.mEnforcer.mCallback = domainVerificationConnection;
    }

    public final void setDomainVerificationLinkHandlingAllowed(String str, boolean z, int i) {
        DomainVerificationEnforcer domainVerificationEnforcer = this.mEnforcer;
        this.mConnection.getClass();
        int callingUid = Binder.getCallingUid();
        this.mConnection.getClass();
        if (!domainVerificationEnforcer.assertApprovedUserSelector(callingUid, UserHandle.getCallingUserId(), i, str)) {
            DomainVerificationUtils.throwPackageUnavailable(str);
            throw null;
        }
        synchronized (this.mLock) {
            DomainVerificationPkgState domainVerificationPkgState = (DomainVerificationPkgState) this.mAttachedPkgStates.mPackageNameMap.get(str);
            if (domainVerificationPkgState == null) {
                DomainVerificationUtils.throwPackageUnavailable(str);
                throw null;
            }
            domainVerificationPkgState.getOrCreateUserState(i).mLinkHandlingAllowed = z;
        }
        this.mConnection.scheduleWriteSettings();
    }

    public final void setDomainVerificationLinkHandlingAllowedInternal(int i, String str, boolean z) {
        DomainVerificationEnforcer domainVerificationEnforcer = this.mEnforcer;
        this.mConnection.getClass();
        int callingUid = Binder.getCallingUid();
        domainVerificationEnforcer.getClass();
        DomainVerificationEnforcer.assertInternal(callingUid);
        if (str == null) {
            synchronized (this.mLock) {
                try {
                    int size = this.mAttachedPkgStates.mPackageNameMap.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        DomainVerificationPkgState domainVerificationPkgState = (DomainVerificationPkgState) this.mAttachedPkgStates.mPackageNameMap.valueAt(i2);
                        if (i == -1) {
                            for (int i3 : this.mConnection.mUmInternal.getUserIds()) {
                                domainVerificationPkgState.getOrCreateUserState(i3).mLinkHandlingAllowed = z;
                            }
                        } else {
                            domainVerificationPkgState.getOrCreateUserState(i).mLinkHandlingAllowed = z;
                        }
                    }
                } finally {
                }
            }
        } else {
            synchronized (this.mLock) {
                try {
                    DomainVerificationPkgState domainVerificationPkgState2 = (DomainVerificationPkgState) this.mAttachedPkgStates.mPackageNameMap.get(str);
                    if (domainVerificationPkgState2 == null) {
                        DomainVerificationUtils.throwPackageUnavailable(str);
                        throw null;
                    }
                    if (i == -1) {
                        for (int i4 : this.mConnection.mUmInternal.getUserIds()) {
                            domainVerificationPkgState2.getOrCreateUserState(i4).mLinkHandlingAllowed = z;
                        }
                    } else {
                        domainVerificationPkgState2.getOrCreateUserState(i).mLinkHandlingAllowed = z;
                    }
                } finally {
                }
            }
        }
        this.mConnection.scheduleWriteSettings();
    }

    public final int setDomainVerificationStatusInternal(int i, UUID uuid, Set set, int i2) {
        this.mEnforcer.assertApprovedVerifier(i, this.mProxy);
        Computer snapshotComputer = this.mConnection.mPm.snapshotComputer();
        synchronized (this.mLock) {
            try {
                ArrayList arrayList = new ArrayList();
                GetAttachedResult andValidateAttachedLocked = getAndValidateAttachedLocked(uuid, set, true, i, null, snapshotComputer);
                int i3 = andValidateAttachedLocked.mErrorCode;
                if (i3 != 0) {
                    return i3;
                }
                DomainVerificationPkgState domainVerificationPkgState = andValidateAttachedLocked.mPkgState;
                ArrayMap arrayMap = domainVerificationPkgState.mStateMap;
                Iterator it = set.iterator();
                while (it.hasNext()) {
                    String str = (String) it.next();
                    Integer num = (Integer) arrayMap.get(str);
                    if (num == null || (num.intValue() != i2 && DomainVerificationState.isModifiable(num.intValue()))) {
                        if (DomainVerificationState.isVerified(i2) && (num == null || !DomainVerificationState.isVerified(num.intValue()))) {
                            arrayList.add(str);
                        }
                        arrayMap.put(str, Integer.valueOf(i2));
                    }
                }
                int size = arrayList.size();
                for (int i4 = 0; i4 < size; i4++) {
                    removeUserStatesForDomain(domainVerificationPkgState, (String) arrayList.get(i4));
                }
                this.mConnection.scheduleWriteSettings();
                return 0;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setDomainVerificationStatusInternal(String str, int i, ArraySet arraySet) {
        AndroidPackage androidPackage;
        AndroidPackage androidPackage2;
        DomainVerificationEnforcer domainVerificationEnforcer = this.mEnforcer;
        this.mConnection.getClass();
        int callingUid = Binder.getCallingUid();
        domainVerificationEnforcer.getClass();
        DomainVerificationEnforcer.assertInternal(callingUid);
        if (i != 0 && i != 1 && i != 2 && i != 3) {
            throw new IllegalArgumentException("State must be one of NO_RESPONSE, SUCCESS, APPROVED, or DENIED");
        }
        if (str == null) {
            Computer snapshotComputer = this.mConnection.mPm.snapshotComputer();
            synchronized (this.mLock) {
                try {
                    ArraySet arraySet2 = new ArraySet();
                    int size = this.mAttachedPkgStates.mPackageNameMap.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        DomainVerificationPkgState domainVerificationPkgState = (DomainVerificationPkgState) this.mAttachedPkgStates.mPackageNameMap.valueAt(i2);
                        PackageSetting packageStateInternal = snapshotComputer.getPackageStateInternal(domainVerificationPkgState.mPackageName);
                        if (packageStateInternal != null && (androidPackage2 = packageStateInternal.pkg) != null) {
                            arraySet2.clear();
                            ArraySet collectDomains = this.mCollector.collectDomains(androidPackage2, true, true);
                            if (arraySet == null) {
                                arraySet2.addAll(collectDomains);
                            } else {
                                arraySet2.addAll(arraySet);
                                arraySet2.retainAll(collectDomains);
                            }
                            ArrayMap arrayMap = domainVerificationPkgState.mStateMap;
                            int size2 = arraySet2.size();
                            for (int i3 = 0; i3 < size2; i3++) {
                                arrayMap.put((String) arraySet2.valueAt(i3), Integer.valueOf(i));
                            }
                        }
                    }
                } finally {
                }
            }
        } else {
            Computer snapshotComputer2 = this.mConnection.mPm.snapshotComputer();
            synchronized (this.mLock) {
                try {
                    DomainVerificationPkgState domainVerificationPkgState2 = (DomainVerificationPkgState) this.mAttachedPkgStates.mPackageNameMap.get(str);
                    ArraySet arraySet3 = null;
                    if (domainVerificationPkgState2 == null) {
                        DomainVerificationUtils.throwPackageUnavailable(str);
                        throw null;
                    }
                    PackageSetting packageStateInternal2 = snapshotComputer2.getPackageStateInternal(str);
                    if (packageStateInternal2 == null || (androidPackage = packageStateInternal2.pkg) == null) {
                        DomainVerificationUtils.throwPackageUnavailable(str);
                        throw null;
                    }
                    if (arraySet == null) {
                        arraySet = this.mCollector.collectDomains(androidPackage, true, true);
                    } else {
                        arraySet.retainAll(this.mCollector.collectDomains(androidPackage, true, true));
                    }
                    if (DomainVerificationState.isVerified(i)) {
                        arraySet3 = new ArraySet();
                        ArrayMap arrayMap2 = domainVerificationPkgState2.mStateMap;
                        int size3 = arraySet.size();
                        for (int i4 = 0; i4 < size3; i4++) {
                            String str2 = (String) arraySet.valueAt(i4);
                            Integer num = (Integer) arrayMap2.get(str2);
                            if (num == null || !DomainVerificationState.isVerified(num.intValue())) {
                                arraySet3.add(str2);
                            }
                        }
                    }
                    ArrayMap arrayMap3 = domainVerificationPkgState2.mStateMap;
                    int size4 = arraySet.size();
                    for (int i5 = 0; i5 < size4; i5++) {
                        arrayMap3.put((String) arraySet.valueAt(i5), Integer.valueOf(i));
                    }
                    if (arraySet3 != null) {
                        int size5 = arraySet3.size();
                        for (int i6 = 0; i6 < size5; i6++) {
                            removeUserStatesForDomain(domainVerificationPkgState2, (String) arraySet3.valueAt(i6));
                        }
                    }
                } finally {
                }
            }
        }
        this.mConnection.scheduleWriteSettings();
    }

    public final int setDomainVerificationUserSelection(UUID uuid, Set set, boolean z, int i) {
        int revokeOtherUserSelectionsLocked;
        this.mConnection.getClass();
        int callingUid = Binder.getCallingUid();
        DomainVerificationEnforcer domainVerificationEnforcer = this.mEnforcer;
        this.mConnection.getClass();
        if (!domainVerificationEnforcer.assertApprovedUserSelector(callingUid, UserHandle.getCallingUserId(), i, null)) {
            return 1;
        }
        Computer snapshotComputer = this.mConnection.mPm.snapshotComputer();
        synchronized (this.mLock) {
            try {
                GetAttachedResult andValidateAttachedLocked = getAndValidateAttachedLocked(uuid, set, false, callingUid, Integer.valueOf(i), snapshotComputer);
                int i2 = andValidateAttachedLocked.mErrorCode;
                if (i2 != 0) {
                    return i2;
                }
                DomainVerificationInternalUserState orCreateUserState = andValidateAttachedLocked.mPkgState.getOrCreateUserState(i);
                if (z && (revokeOtherUserSelectionsLocked = revokeOtherUserSelectionsLocked(orCreateUserState, i, set, snapshotComputer)) != 0) {
                    return revokeOtherUserSelectionsLocked;
                }
                if (z) {
                    orCreateUserState.mEnabledHosts.addAll(set);
                } else {
                    orCreateUserState.mEnabledHosts.removeAll(set);
                }
                this.mConnection.scheduleWriteSettings();
                return 0;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setDomainVerificationUserSelectionInternal(int i, String str, boolean z, ArraySet arraySet) {
        DomainVerificationEnforcer domainVerificationEnforcer = this.mEnforcer;
        this.mConnection.getClass();
        int callingUid = Binder.getCallingUid();
        domainVerificationEnforcer.getClass();
        DomainVerificationEnforcer.assertInternal(callingUid);
        Computer snapshotComputer = this.mConnection.mPm.snapshotComputer();
        synchronized (this.mLock) {
            try {
                DomainVerificationPkgState domainVerificationPkgState = (DomainVerificationPkgState) this.mAttachedPkgStates.mPackageNameMap.get(str);
                if (domainVerificationPkgState == null) {
                    DomainVerificationUtils.throwPackageUnavailable(str);
                    throw null;
                }
                PackageSetting packageStateInternal = snapshotComputer.getPackageStateInternal(str);
                AndroidPackage androidPackage = packageStateInternal == null ? null : packageStateInternal.pkg;
                if (androidPackage == null) {
                    DomainVerificationUtils.throwPackageUnavailable(str);
                    throw null;
                }
                if (arraySet == null) {
                    arraySet = this.mCollector.collectDomains(androidPackage, false, true);
                }
                arraySet.retainAll(this.mCollector.collectDomains(androidPackage, false, true));
                if (i == -1) {
                    for (int i2 : this.mConnection.mUmInternal.getUserIds()) {
                        DomainVerificationInternalUserState orCreateUserState = domainVerificationPkgState.getOrCreateUserState(i2);
                        revokeOtherUserSelectionsLocked(orCreateUserState, i2, arraySet, snapshotComputer);
                        if (z) {
                            orCreateUserState.mEnabledHosts.addAll((Collection) arraySet);
                        } else {
                            orCreateUserState.mEnabledHosts.removeAll((Collection<?>) arraySet);
                        }
                    }
                } else {
                    DomainVerificationInternalUserState orCreateUserState2 = domainVerificationPkgState.getOrCreateUserState(i);
                    revokeOtherUserSelectionsLocked(orCreateUserState2, i, arraySet, snapshotComputer);
                    if (z) {
                        orCreateUserState2.mEnabledHosts.addAll((Collection) arraySet);
                    } else {
                        orCreateUserState2.mEnabledHosts.removeAll((Collection<?>) arraySet);
                    }
                }
            } finally {
            }
        }
        this.mConnection.scheduleWriteSettings();
    }

    public final boolean setLegacyUserState(int i, int i2, String str) {
        boolean z;
        DomainVerificationEnforcer domainVerificationEnforcer = this.mEnforcer;
        this.mConnection.getClass();
        int callingUid = Binder.getCallingUid();
        this.mConnection.getClass();
        int callingUserId = UserHandle.getCallingUserId();
        domainVerificationEnforcer.mContext.enforcePermission("android.permission.SET_PREFERRED_APPLICATIONS", Binder.getCallingPid(), callingUid, "Caller is not allowed to edit user state");
        if (callingUserId != i && domainVerificationEnforcer.mContext.checkPermission("android.permission.INTERACT_ACROSS_USERS", Binder.getCallingPid(), callingUid) != 0) {
            z = false;
        } else {
            if (!domainVerificationEnforcer.mCallback.mUmInternal.exists(callingUserId)) {
                throw new SecurityException(BinaryTransparencyService$$ExternalSyntheticOutline0.m(callingUserId, "User ", " does not exist"));
            }
            if (!domainVerificationEnforcer.mCallback.mUmInternal.exists(i)) {
                throw new SecurityException(BinaryTransparencyService$$ExternalSyntheticOutline0.m(i, "User ", " does not exist"));
            }
            z = !domainVerificationEnforcer.mCallback.mPm.snapshotComputer().filterAppAccess(callingUid, i, str, true);
        }
        if (!z) {
            return false;
        }
        DomainVerificationLegacySettings domainVerificationLegacySettings = this.mLegacySettings;
        synchronized (domainVerificationLegacySettings.mLock) {
            DomainVerificationLegacySettings.LegacyState orCreateStateLocked = domainVerificationLegacySettings.getOrCreateStateLocked(str);
            if (orCreateStateLocked.mUserStates == null) {
                orCreateStateLocked.mUserStates = new SparseIntArray(1);
            }
            orCreateStateLocked.mUserStates.put(i, i2);
        }
        this.mConnection.scheduleWriteSettings();
        return true;
    }

    public final void setProxy(DomainVerificationProxy domainVerificationProxy) {
        this.mProxy = domainVerificationProxy;
    }

    public final void setUriRelativeFilterGroups(String str, Bundle bundle) {
        String str2;
        Context context = getContext();
        StringBuilder sb = new StringBuilder("Caller ");
        this.mConnection.getClass();
        sb.append(Binder.getCallingUid());
        sb.append(" does not hold android.permission.DOMAIN_VERIFICATION_AGENT");
        context.enforceCallingOrSelfPermission("android.permission.DOMAIN_VERIFICATION_AGENT", sb.toString());
        if (bundle.isEmpty()) {
            return;
        }
        synchronized (this.mLock) {
            try {
                DomainVerificationPkgState domainVerificationPkgState = (DomainVerificationPkgState) this.mAttachedPkgStates.mPackageNameMap.get(str);
                if (domainVerificationPkgState == null) {
                    DomainVerificationUtils.throwPackageUnavailable(str);
                    throw null;
                }
                ArrayMap arrayMap = domainVerificationPkgState.mUriRelativeFilterGroupMap;
                for (String str3 : bundle.keySet()) {
                    ThreadLocal threadLocal = DomainVerificationUtils.sCachedMatcher;
                    if (str3.length() <= 254 && !str3.equals("*")) {
                        int i = 0;
                        if (str3.charAt(0) != '*') {
                            str2 = str3;
                        } else if (str3.charAt(1) == '.') {
                            str2 = str3.substring(2);
                        }
                        int i2 = -1;
                        int i3 = 1;
                        while (true) {
                            if (i < str2.length()) {
                                char charAt = str2.charAt(i);
                                if (charAt == '.') {
                                    int i4 = (i - i2) - 1;
                                    if (i4 != 0 && i4 <= 63) {
                                        i3++;
                                        i2 = i;
                                        i++;
                                    }
                                } else {
                                    if ((charAt < 'a' || charAt > 'z') && ((charAt < 'A' || charAt > 'Z') && ((charAt < '0' || charAt > '9') && charAt != '-'))) {
                                        break;
                                    }
                                    i++;
                                }
                            } else {
                                int length = (str2.length() - i2) - 1;
                                if (length != 0 && length <= 63 && i3 > 1) {
                                    List parcelsToGroups = UriRelativeFilterGroup.parcelsToGroups(bundle.getParcelableArrayList(str3, UriRelativeFilterGroupParcel.class));
                                    if (parcelsToGroups != null && !parcelsToGroups.isEmpty()) {
                                        arrayMap.put(str3, parcelsToGroups);
                                    }
                                    arrayMap.remove(str3);
                                }
                            }
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void verifyPackages(List list, boolean z) {
        DomainVerificationEnforcer domainVerificationEnforcer = this.mEnforcer;
        this.mConnection.getClass();
        int callingUid = Binder.getCallingUid();
        domainVerificationEnforcer.getClass();
        DomainVerificationEnforcer.assertInternal(callingUid);
        ArraySet arraySet = new ArraySet();
        int i = 0;
        if (list == null) {
            synchronized (this.mLock) {
                try {
                    int size = this.mAttachedPkgStates.mPackageNameMap.size();
                    while (i < size) {
                        addIfShouldBroadcastLocked(arraySet, (DomainVerificationPkgState) this.mAttachedPkgStates.mPackageNameMap.valueAt(i), z);
                        i++;
                    }
                } finally {
                }
            }
        } else {
            synchronized (this.mLock) {
                try {
                    int size2 = list.size();
                    while (i < size2) {
                        DomainVerificationPkgState domainVerificationPkgState = (DomainVerificationPkgState) this.mAttachedPkgStates.mPackageNameMap.get((String) list.get(i));
                        if (domainVerificationPkgState != null) {
                            addIfShouldBroadcastLocked(arraySet, domainVerificationPkgState, z);
                        }
                        i++;
                    }
                } finally {
                }
            }
        }
        if (arraySet.isEmpty() || !this.mCanSendBroadcasts) {
            return;
        }
        this.mProxy.sendBroadcastForPackages(arraySet);
    }

    public final void writeSettings(Computer computer, TypedXmlSerializer typedXmlSerializer, boolean z, int i) {
        DomainVerificationService$$ExternalSyntheticLambda1 domainVerificationService$$ExternalSyntheticLambda1;
        synchronized (this.mLock) {
            if (z) {
                try {
                    domainVerificationService$$ExternalSyntheticLambda1 = new DomainVerificationService$$ExternalSyntheticLambda1(computer);
                } finally {
                }
            } else {
                domainVerificationService$$ExternalSyntheticLambda1 = null;
            }
            DomainVerificationService$$ExternalSyntheticLambda1 domainVerificationService$$ExternalSyntheticLambda12 = domainVerificationService$$ExternalSyntheticLambda1;
            DomainVerificationSettings domainVerificationSettings = this.mSettings;
            DomainVerificationStateMap domainVerificationStateMap = this.mAttachedPkgStates;
            synchronized (domainVerificationSettings.mLock) {
                DomainVerificationPersistence.writeToXml(typedXmlSerializer, domainVerificationStateMap, domainVerificationSettings.mPendingPkgStates, domainVerificationSettings.mRestoredPkgStates, i, domainVerificationService$$ExternalSyntheticLambda12);
            }
        }
        DomainVerificationLegacySettings domainVerificationLegacySettings = this.mLegacySettings;
        domainVerificationLegacySettings.getClass();
        SettingsXml$ReadSectionImpl settingsXml$ReadSectionImpl = new SettingsXml$ReadSectionImpl(typedXmlSerializer);
        try {
            settingsXml$ReadSectionImpl.startSection("domain-verifications-legacy");
            try {
                synchronized (domainVerificationLegacySettings.mLock) {
                    int size = domainVerificationLegacySettings.mStates.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        SparseIntArray sparseIntArray = ((DomainVerificationLegacySettings.LegacyState) domainVerificationLegacySettings.mStates.valueAt(i2)).mUserStates;
                        if (sparseIntArray != null) {
                            String str = (String) domainVerificationLegacySettings.mStates.keyAt(i2);
                            settingsXml$ReadSectionImpl.startSection("user-states");
                            settingsXml$ReadSectionImpl.attribute("packageName", str);
                            try {
                                int size2 = sparseIntArray.size();
                                for (int i3 = 0; i3 < size2; i3++) {
                                    int keyAt = sparseIntArray.keyAt(i3);
                                    int valueAt = sparseIntArray.valueAt(i3);
                                    settingsXml$ReadSectionImpl.startSection("user-state");
                                    settingsXml$ReadSectionImpl.attribute(keyAt, "userId");
                                    settingsXml$ReadSectionImpl.attribute(valueAt, LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
                                    settingsXml$ReadSectionImpl.close();
                                }
                                settingsXml$ReadSectionImpl.close();
                            } finally {
                            }
                        }
                    }
                }
                settingsXml$ReadSectionImpl.close();
                if (settingsXml$ReadSectionImpl.mDepthStack != null) {
                    while (!settingsXml$ReadSectionImpl.mDepthStack.isEmpty()) {
                        settingsXml$ReadSectionImpl.close();
                    }
                }
                typedXmlSerializer.flush();
            } finally {
            }
        } catch (Throwable th) {
            try {
                if (settingsXml$ReadSectionImpl.mDepthStack != null) {
                    while (!settingsXml$ReadSectionImpl.mDepthStack.isEmpty()) {
                        settingsXml$ReadSectionImpl.close();
                    }
                }
                typedXmlSerializer.flush();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }
}
