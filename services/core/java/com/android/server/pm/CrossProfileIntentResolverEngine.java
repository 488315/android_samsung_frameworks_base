package com.android.server.pm;

import android.content.Context;
import android.content.Intent;
import android.content.pm.UserInfo;
import android.content.pm.UserProperties;
import android.util.SparseArray;
import com.android.internal.config.appcloning.AppCloningDeviceConfigHelper;
import com.android.server.LocalServices;
import com.android.server.pm.verify.domain.DomainVerificationManagerInternal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public final class CrossProfileIntentResolverEngine {
    public AppCloningDeviceConfigHelper mAppCloningDeviceConfigHelper;
    public final Context mContext;
    public final DefaultAppProvider mDefaultAppProvider;
    public final DomainVerificationManagerInternal mDomainVerificationManager;
    public final UserManagerService mUserManager;
    public final UserManagerInternal mUserManagerInternal = (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);

    public CrossProfileIntentResolverEngine(UserManagerService userManagerService, DomainVerificationManagerInternal domainVerificationManagerInternal, DefaultAppProvider defaultAppProvider, Context context) {
        this.mUserManager = userManagerService;
        this.mDomainVerificationManager = domainVerificationManagerInternal;
        this.mDefaultAppProvider = defaultAppProvider;
        this.mContext = context;
    }

    public static boolean canReachToInternal(Computer computer, Intent intent, String str, int i, int i2, Set set) {
        if (i == i2) {
            return true;
        }
        HashSet hashSet = (HashSet) set;
        hashSet.add(Integer.valueOf(i));
        List matchingCrossProfileIntentFilters = computer.getMatchingCrossProfileIntentFilters(intent, str, i);
        if (matchingCrossProfileIntentFilters != null) {
            for (int i3 = 0; i3 < matchingCrossProfileIntentFilters.size(); i3++) {
                CrossProfileIntentFilter crossProfileIntentFilter = (CrossProfileIntentFilter) matchingCrossProfileIntentFilters.get(i3);
                int i4 = crossProfileIntentFilter.mTargetUserId;
                if (i4 == i2) {
                    return true;
                }
                if (!hashSet.contains(Integer.valueOf(i4)) && (crossProfileIntentFilter.mFlags & 16) != 0) {
                    hashSet.add(Integer.valueOf(crossProfileIntentFilter.mTargetUserId));
                    if (canReachToInternal(computer, intent, str, crossProfileIntentFilter.mTargetUserId, i2, set)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static List resolveInfoFromCrossProfileDomainInfo(List list) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            arrayList.add(((CrossProfileDomainInfo) list.get(i)).mResolveInfo);
        }
        return arrayList;
    }

    public static boolean shouldSkipCurrentProfile(Computer computer, Intent intent, String str, int i) {
        List matchingCrossProfileIntentFilters = computer.getMatchingCrossProfileIntentFilters(intent, str, i);
        if (matchingCrossProfileIntentFilters != null) {
            for (int i2 = 0; i2 < matchingCrossProfileIntentFilters.size(); i2++) {
                if ((((CrossProfileIntentFilter) matchingCrossProfileIntentFilters.get(i2)).mFlags & 2) != 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x005a, code lost:
    
        if (r7.getEnableAppCloningBuildingBlocks() != false) goto L28;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.server.pm.CrossProfileResolver chooseCrossProfileResolver(com.android.server.pm.Computer r8, int r9, int r10, boolean r11, long r12) {
        /*
            r7 = this;
            boolean r9 = r7.isNoFilteringPropertyConfiguredForUser(r9)
            com.android.server.pm.UserManagerService r0 = r7.mUserManager
            if (r9 != 0) goto L1b
            boolean r9 = r7.isNoFilteringPropertyConfiguredForUser(r10)
            if (r9 == 0) goto Lf
            goto L1b
        Lf:
            com.android.server.pm.DefaultCrossProfileResolver r9 = new com.android.server.pm.DefaultCrossProfileResolver
            com.android.server.pm.resolution.ComponentResolverApi r8 = r8.getComponentResolver()
            com.android.server.pm.verify.domain.DomainVerificationManagerInternal r7 = r7.mDomainVerificationManager
            r9.<init>(r8, r0, r7)
            return r9
        L1b:
            com.android.internal.config.appcloning.AppCloningDeviceConfigHelper r9 = r7.mAppCloningDeviceConfigHelper
            if (r9 != 0) goto L27
            android.content.Context r9 = r7.mContext
            com.android.internal.config.appcloning.AppCloningDeviceConfigHelper r9 = com.android.internal.config.appcloning.AppCloningDeviceConfigHelper.getInstance(r9)
            r7.mAppCloningDeviceConfigHelper = r9
        L27:
            android.content.Context r9 = r7.mContext
            com.android.internal.config.appcloning.AppCloningDeviceConfigHelper r7 = r7.mAppCloningDeviceConfigHelper
            r1 = 536870912(0x20000000, double:2.652494739E-315)
            long r1 = r1 & r12
            r3 = 0
            int r10 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            r1 = 1
            r2 = 0
            if (r10 != 0) goto L44
            r5 = 17179869184(0x400000000, double:8.4879831639E-314)
            long r12 = r12 & r5
            int r10 = (r12 > r3 ? 1 : (r12 == r3 ? 0 : -1))
            if (r10 == 0) goto L42
            goto L44
        L42:
            r10 = r2
            goto L45
        L44:
            r10 = r1
        L45:
            long r12 = android.os.Binder.clearCallingIdentity()
            android.content.res.Resources r3 = r9.getResources()     // Catch: java.lang.Throwable -> L5d
            r4 = 17891685(0x1110165, float:2.6633294E-38)
            boolean r3 = r3.getBoolean(r4)     // Catch: java.lang.Throwable -> L5d
            if (r3 == 0) goto L5f
            boolean r7 = r7.getEnableAppCloningBuildingBlocks()     // Catch: java.lang.Throwable -> L5d
            if (r7 == 0) goto L5f
            goto L60
        L5d:
            r7 = move-exception
            goto L7d
        L5f:
            r1 = r2
        L60:
            android.os.Binder.restoreCallingIdentity(r12)
            if (r1 == 0) goto L7b
            if (r11 != 0) goto L71
            if (r10 == 0) goto L7b
            java.lang.String r7 = "android.permission.QUERY_CLONED_APPS"
            int r7 = r9.checkCallingOrSelfPermission(r7)
            if (r7 != 0) goto L7b
        L71:
            com.android.server.pm.NoFilteringResolver r7 = new com.android.server.pm.NoFilteringResolver
            com.android.server.pm.resolution.ComponentResolverApi r8 = r8.getComponentResolver()
            r7.<init>(r8, r0)
            return r7
        L7b:
            r7 = 0
            return r7
        L7d:
            android.os.Binder.restoreCallingIdentity(r12)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.CrossProfileIntentResolverEngine.chooseCrossProfileResolver(com.android.server.pm.Computer, int, int, boolean, long):com.android.server.pm.CrossProfileResolver");
    }

    public final List filterCrossProfileCandidatesWithDomainPreferredActivities(Computer computer, Intent intent, long j, SparseArray sparseArray, int i, int i2, boolean z) {
        ArrayList arrayList = new ArrayList();
        for (int i3 = 0; i3 < sparseArray.size(); i3++) {
            if (sparseArray.keyAt(i3) == -2) {
                arrayList.addAll((Collection) sparseArray.valueAt(i3));
            } else {
                CrossProfileResolver chooseCrossProfileResolver = chooseCrossProfileResolver(computer, i, sparseArray.keyAt(i3), z, j);
                if (chooseCrossProfileResolver != null) {
                    List list = (List) sparseArray.valueAt(i3);
                    sparseArray.keyAt(i3);
                    arrayList.addAll(chooseCrossProfileResolver.filterResolveInfoWithDomainPreferredActivity(i2, list));
                } else {
                    arrayList.addAll((Collection) sparseArray.valueAt(i3));
                }
            }
        }
        return resolveInfoFromCrossProfileDomainInfo(arrayList);
    }

    public final boolean isNoFilteringPropertyConfiguredForUser(int i) {
        UserProperties userProperties;
        UserManagerService userManagerService = this.mUserManager;
        userManagerService.checkQueryOrInteractPermissionIfCallerInOtherProfileGroup(i, "isProfile");
        return userManagerService.isProfileUnchecked(i) && (userProperties = this.mUserManagerInternal.getUserProperties(i)) != null && userProperties.getCrossProfileIntentResolutionStrategy() == 1;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.util.Set] */
    /* JADX WARN: Type inference failed for: r0v22, types: [java.util.HashSet] */
    /* JADX WARN: Type inference failed for: r0v7 */
    public final List resolveIntentInternal(Computer computer, Intent intent, String str, int i, int i2, long j, String str2, boolean z, boolean z2, Function function, Set set) {
        int i3;
        int i4;
        UserInfo profileParent;
        CrossProfileDomainInfo crossProfileDomainPreferredLpr;
        CrossProfileResolver chooseCrossProfileResolver;
        int i5;
        Set set2;
        SparseArray sparseArray;
        boolean z3;
        ?? r0 = set;
        if (r0 != 0) {
            r0.add(Integer.valueOf(i2));
        }
        ArrayList arrayList = new ArrayList();
        List matchingCrossProfileIntentFilters = computer.getMatchingCrossProfileIntentFilters(intent, str, i2);
        if (matchingCrossProfileIntentFilters == null) {
            i3 = i;
            i4 = i2;
        } else {
            if (!matchingCrossProfileIntentFilters.isEmpty()) {
                SparseArray sparseArray2 = new SparseArray();
                boolean z4 = false;
                for (int i6 = 0; i6 < matchingCrossProfileIntentFilters.size(); i6++) {
                    CrossProfileIntentFilter crossProfileIntentFilter = (CrossProfileIntentFilter) matchingCrossProfileIntentFilters.get(i6);
                    boolean contains = sparseArray2.contains(crossProfileIntentFilter.mTargetUserId);
                    int i7 = crossProfileIntentFilter.mTargetUserId;
                    if (!contains) {
                        sparseArray2.put(i7, new ArrayList());
                    }
                    ((List) sparseArray2.get(i7)).add(crossProfileIntentFilter);
                }
                if (r0 == 0) {
                    r0 = new HashSet();
                    r0.add(Integer.valueOf(i2));
                }
                Set set3 = r0;
                int i8 = 0;
                while (i8 < sparseArray2.size()) {
                    int keyAt = sparseArray2.keyAt(i8);
                    if (set3.contains(Integer.valueOf(keyAt)) || (chooseCrossProfileResolver = chooseCrossProfileResolver(computer, i2, keyAt, z2, j)) == null) {
                        i5 = i8;
                        set2 = set3;
                        z3 = z4;
                        sparseArray = sparseArray2;
                    } else {
                        i5 = i8;
                        set2 = set3;
                        SparseArray sparseArray3 = sparseArray2;
                        List resolveIntent = chooseCrossProfileResolver.resolveIntent(computer, intent, str, i2, keyAt, j, str2, (List) sparseArray2.valueAt(i8), z, function);
                        arrayList.addAll(resolveIntent);
                        set2.add(Integer.valueOf(keyAt));
                        int i9 = 0;
                        while (true) {
                            if (i9 >= ((List) sparseArray3.valueAt(i5)).size()) {
                                sparseArray = sparseArray3;
                                z3 = false;
                                break;
                            }
                            if ((((CrossProfileIntentFilter) ((List) sparseArray3.valueAt(i5)).get(i9)).mFlags & 16) != 0) {
                                ArrayList arrayList2 = (ArrayList) resolveIntent;
                                sparseArray = sparseArray3;
                                z3 = false;
                                arrayList.addAll(resolveIntentInternal(computer, intent, str, i, keyAt, j, str2, arrayList2.size() > 0 && ((CrossProfileDomainInfo) arrayList2.get(0)).mResolveInfo != null && ((CrossProfileDomainInfo) arrayList2.get(0)).mResolveInfo.priority >= 0, z2, function, set2));
                            } else {
                                i9++;
                            }
                        }
                    }
                    i8 = i5 + 1;
                    set3 = set2;
                    sparseArray2 = sparseArray;
                    z4 = z3;
                }
                return arrayList;
            }
            i3 = i;
            i4 = i2;
        }
        if (i3 == i4 && intent.hasWebURI() && (profileParent = computer.getProfileParent(i4)) != null && (crossProfileDomainPreferredLpr = computer.getCrossProfileDomainPreferredLpr(intent, str, j, i2, profileParent.id)) != null) {
            arrayList.add(crossProfileDomainPreferredLpr);
        }
        return arrayList;
    }
}
