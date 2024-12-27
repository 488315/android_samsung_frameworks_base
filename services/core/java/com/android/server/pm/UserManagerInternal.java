package com.android.server.pm;

import android.content.pm.LauncherUserInfo;
import android.content.pm.UserInfo;
import android.content.pm.UserProperties;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DebugUtils;

import com.android.server.locksettings.LockSettingsService;

import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class UserManagerInternal {
    public static final int USER_ASSIGNMENT_RESULT_FAILURE = -1;
    public static final int USER_ASSIGNMENT_RESULT_SUCCESS_ALREADY_VISIBLE = 3;
    public static final int USER_ASSIGNMENT_RESULT_SUCCESS_INVISIBLE = 2;
    public static final int USER_ASSIGNMENT_RESULT_SUCCESS_VISIBLE = 1;
    public static final int USER_START_MODE_BACKGROUND = 2;
    public static final int USER_START_MODE_BACKGROUND_VISIBLE = 3;
    public static final int USER_START_MODE_FOREGROUND = 1;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface UserLifecycleListener {
        default void onUserCreated(UserInfo userInfo, Object obj) {}

        default void onUserRemoved(UserInfo userInfo) {}
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface UserRestrictionsListener {
        void onUserRestrictionsChanged(int i, Bundle bundle, Bundle bundle2);
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface UserVisibilityListener {
        void onUserVisibilityChanged(int i, boolean z);
    }

    public static String userStartModeToString(int i) {
        return DebugUtils.constantToString(UserManagerInternal.class, "USER_START_MODE_", i);
    }

    public abstract void addMaintenanceModeLifecycleListener(
            LockSettingsService.AnonymousClass1 anonymousClass1);

    public abstract void addUserLifecycleListener(UserLifecycleListener userLifecycleListener);

    public abstract void addUserRestrictionsListener(
            UserRestrictionsListener userRestrictionsListener);

    public abstract void addUserVisibilityListener(UserVisibilityListener userVisibilityListener);

    public abstract int assignUserToDisplayOnStart(int i, int i2, int i3, int i4);

    public abstract boolean clearAttributes(int i, int i2);

    public abstract UserInfo createUserEvenWhenDisallowed(
            String str, String str2, int i, String[] strArr, Object obj);

    public abstract boolean exists(int i);

    public abstract int getAttributes(int i);

    public abstract int getBootUser(boolean z);

    public abstract int getCommunalProfileId();

    public abstract LauncherUserInfo getLauncherUserInfo(int i);

    public abstract int getMainDisplayAssignedToUser(int i);

    public abstract int getMainUserId();

    public abstract int[] getProfileIds(int i, boolean z);

    public abstract int getProfileParentId(int i);

    public abstract int getUserAssignedToDisplay(int i);

    public abstract int[] getUserIds();

    public abstract UserInfo getUserInfo(int i);

    public abstract UserInfo[] getUserInfos();

    public abstract UserProperties getUserProperties(int i);

    public abstract boolean getUserRestriction(int i, String str);

    public abstract int[] getUserTypesForStatsd(int[] iArr);

    public abstract List getUsers(boolean z);

    public abstract boolean hasUserRestriction(String str, int i);

    public abstract boolean isDeviceManaged();

    public abstract boolean isProfileAccessible(int i, int i2, String str, boolean z);

    public abstract boolean isUserInitialized(int i);

    public abstract boolean isUserManaged(int i);

    public abstract boolean isUserRunning(int i);

    public abstract boolean isUserUnlocked(int i);

    public abstract boolean isUserUnlockingOrUnlocked(int i);

    public abstract boolean isUserVisible(int i);

    public abstract boolean isUserVisible(int i, int i2);

    public abstract void onEphemeralUserStop(int i);

    public abstract void onSystemUserVisibilityChanged();

    public abstract boolean removeUserEvenWhenDisallowed(int i);

    public abstract void removeUserLifecycleListener(
            PackageManagerShellCommand.AnonymousClass4 anonymousClass4);

    public abstract void removeUserState(int i);

    public abstract boolean setAttributes(int i, int i2);

    public abstract void setDefaultCrossProfileIntentFilters(int i, int i2);

    public abstract void setDeviceManaged(boolean z);

    public abstract void setDevicePolicyUserRestrictions(
            Bundle bundle, RestrictionsSet restrictionsSet);

    public abstract boolean setDualDarInfo(int i, int i2);

    public abstract void setForceEphemeralUsers(boolean z);

    public abstract void setUserIcon(int i, Bitmap bitmap);

    public abstract void setUserManaged(int i, boolean z);

    public abstract void setUserRestriction(int i, String str, boolean z);

    public abstract void setUserState(int i, int i2);

    public abstract boolean shouldIgnorePrepareStorageErrors(int i);

    public abstract void unassignUserFromDisplayOnStop(int i);
}
