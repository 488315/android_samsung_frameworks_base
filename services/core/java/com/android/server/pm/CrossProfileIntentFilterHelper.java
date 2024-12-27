package com.android.server.pm;

import android.content.Context;
import android.content.pm.UserInfo;
import android.content.pm.UserProperties;
import android.util.ArraySet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public final class CrossProfileIntentFilterHelper {
    public final Context mContext;
    public final PackageManagerTracedLock mLock;
    public final Settings mSettings;
    public final UserManagerInternal mUserManagerInternal;
    public final UserManagerService mUserManagerService;

    public CrossProfileIntentFilterHelper(
            Settings settings,
            UserManagerService userManagerService,
            PackageManagerTracedLock packageManagerTracedLock,
            UserManagerInternal userManagerInternal,
            Context context) {
        this.mSettings = settings;
        this.mUserManagerService = userManagerService;
        this.mLock = packageManagerTracedLock;
        this.mContext = context;
        this.mUserManagerInternal = userManagerInternal;
    }

    public final void clearCrossProfileIntentFilters(int i, String str, Integer num) {
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                CrossProfileIntentResolver editCrossProfileIntentResolverLPw =
                        this.mSettings.editCrossProfileIntentResolverLPw(i);
                Iterator it =
                        new ArraySet(
                                        Collections.unmodifiableSet(
                                                editCrossProfileIntentResolverLPw.mFilters))
                                .iterator();
                while (it.hasNext()) {
                    CrossProfileIntentFilter crossProfileIntentFilter =
                            (CrossProfileIntentFilter) it.next();
                    if (crossProfileIntentFilter.mOwnerPackage.equals(str)) {
                        if (num != null
                                && crossProfileIntentFilter.mTargetUserId != num.intValue()) {}
                        if (this.mUserManagerService.isCrossProfileIntentFilterAccessible(
                                i, crossProfileIntentFilter.mTargetUserId, false)) {
                            editCrossProfileIntentResolverLPw.removeFilter(
                                    (WatchedIntentFilter) crossProfileIntentFilter);
                        }
                    }
                }
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
    }

    public final void updateDefaultCrossProfileIntentFilter() {
        int profileParentId;
        int i;
        UserManagerInternal userManagerInternal = this.mUserManagerInternal;
        Iterator it = ((ArrayList) userManagerInternal.getUsers(false)).iterator();
        while (it.hasNext()) {
            UserInfo userInfo = (UserInfo) it.next();
            UserProperties userProperties = userManagerInternal.getUserProperties(userInfo.id);
            if (userProperties != null
                    && userProperties.getUpdateCrossProfileIntentFiltersOnOTA()
                    && (profileParentId = userManagerInternal.getProfileParentId(userInfo.id))
                            != (i = userInfo.id)) {
                clearCrossProfileIntentFilters(
                        i, this.mContext.getOpPackageName(), Integer.valueOf(profileParentId));
                clearCrossProfileIntentFilters(
                        profileParentId,
                        this.mContext.getOpPackageName(),
                        Integer.valueOf(userInfo.id));
                userManagerInternal.setDefaultCrossProfileIntentFilters(
                        profileParentId, userInfo.id);
            }
        }
    }
}
