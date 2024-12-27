package com.android.server.pm;

import android.os.Handler;
import android.util.Dumpable;
import android.util.EventLog;
import android.util.IndentingPrintWriter;
import android.util.IntArray;
import android.util.Log;
import android.util.SparseIntArray;
import com.android.server.pm.UserManagerInternal;
import com.android.server.utils.Slogf;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class UserVisibilityMediator implements Dumpable {
    public static final boolean DBG = Log.isLoggable("UserVisibilityMediator", 3);
    static final int INITIAL_CURRENT_USER_ID = 0;
    public final SparseIntArray mExtraDisplaysAssignedToUsers;
    public final Handler mHandler;
    public final CopyOnWriteArrayList mListeners;
    public final List mStartedInvisibleProfileUserIds;
    public final SparseIntArray mStartedVisibleProfileGroupIds;
    public final SparseIntArray mUsersAssignedToDisplayOnStart;
    public final boolean mVisibleBackgroundUserOnDefaultDisplayEnabled;
    public final boolean mVisibleBackgroundUsersEnabled;
    public final Object mLock = new Object();
    public int mCurrentUserId = 0;

    public UserVisibilityMediator(boolean z, boolean z2, Handler handler) {
        SparseIntArray sparseIntArray = new SparseIntArray();
        this.mStartedVisibleProfileGroupIds = sparseIntArray;
        this.mListeners = new CopyOnWriteArrayList();
        this.mVisibleBackgroundUsersEnabled = z;
        if (z2 && !z) {
            throw new IllegalArgumentException("Cannot have visibleBackgroundUserOnDefaultDisplayEnabled without visibleBackgroundUsersOnDisplaysEnabled");
        }
        this.mVisibleBackgroundUserOnDefaultDisplayEnabled = z2;
        if (z) {
            this.mUsersAssignedToDisplayOnStart = new SparseIntArray();
            this.mExtraDisplaysAssignedToUsers = new SparseIntArray();
        } else {
            this.mUsersAssignedToDisplayOnStart = null;
            this.mExtraDisplaysAssignedToUsers = null;
        }
        boolean z3 = DBG;
        this.mStartedInvisibleProfileUserIds = z3 ? new ArrayList(4) : null;
        this.mHandler = handler;
        sparseIntArray.put(0, 0);
        if (z3) {
            Slogf.i("UserVisibilityMediator", "UserVisibilityMediator created with DBG on");
        }
    }

    public static void dumpSparseIntArray(IndentingPrintWriter indentingPrintWriter, SparseIntArray sparseIntArray, String str, String str2, String str3) {
        if (sparseIntArray == null) {
            indentingPrintWriter.print("No ");
            indentingPrintWriter.print(str);
            indentingPrintWriter.println(" mappings");
            return;
        }
        indentingPrintWriter.print("Number of ");
        indentingPrintWriter.print(str);
        indentingPrintWriter.print(" mappings: ");
        indentingPrintWriter.println(sparseIntArray.size());
        if (sparseIntArray.size() <= 0) {
            return;
        }
        indentingPrintWriter.increaseIndent();
        for (int i = 0; i < sparseIntArray.size(); i++) {
            indentingPrintWriter.print(str2);
            indentingPrintWriter.print(':');
            indentingPrintWriter.print(sparseIntArray.keyAt(i));
            indentingPrintWriter.print(" -> ");
            indentingPrintWriter.print(str3);
            indentingPrintWriter.print(':');
            indentingPrintWriter.println(sparseIntArray.valueAt(i));
        }
        indentingPrintWriter.decreaseIndent();
    }

    public static boolean isProfile(int i, int i2) {
        return (i2 == -10000 || i2 == i) ? false : true;
    }

    public final int canAssignUserToDisplayLocked(int i, int i2, int i3, int i4) {
        boolean z;
        if (i4 == 0) {
            if (this.mVisibleBackgroundUserOnDefaultDisplayEnabled && i3 == 3) {
                int userAssignedToDisplay = getUserAssignedToDisplay(0, false);
                if (userAssignedToDisplay != -10000 && userAssignedToDisplay != i2) {
                    Slogf.w("UserVisibilityMediator", "canAssignUserToDisplayLocked(): cannot start user %d visible on default display because user %d already did so", Integer.valueOf(i), Integer.valueOf(userAssignedToDisplay));
                    return -1;
                }
                z = true;
            } else {
                z = false;
            }
            if (!z && this.mVisibleBackgroundUsersEnabled && isProfile(i, i2)) {
                z = true;
            }
            if (!z) {
                if (DBG) {
                    Slogf.d("UserVisibilityMediator", "Ignoring mapping for default display for user %d starting as %s", Integer.valueOf(i), UserManagerInternal.userStartModeToString(i3));
                }
                return 2;
            }
        }
        if (i == 0) {
            Slogf.w("UserVisibilityMediator", "Cannot assign system user to secondary display (%d)", Integer.valueOf(i4));
            return -1;
        }
        if (i4 == -1) {
            Slogf.w("UserVisibilityMediator", "Cannot assign to INVALID_DISPLAY (%d)", Integer.valueOf(i4));
            return -1;
        }
        if (i == this.mCurrentUserId) {
            Slogf.w("UserVisibilityMediator", "Cannot assign current user (%d) to other displays", Integer.valueOf(i));
            return -1;
        }
        if (isProfile(i, i2)) {
            if (i4 != 0) {
                Slogf.w("UserVisibilityMediator", "Profile user can only be started in the default display");
                return -1;
            }
            if (DBG) {
                Slogf.d("UserVisibilityMediator", "Don't need to map profile user %d to default display", Integer.valueOf(i));
            }
            return 2;
        }
        if (this.mUsersAssignedToDisplayOnStart == null) {
            Slogf.wtf("UserVisibilityMediator", "canAssignUserToDisplayLocked(%d, %d, %d, %d) is trying to check mUsersAssignedToDisplayOnStart when it's not set", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4));
            return -1;
        }
        for (int i5 = 0; i5 < this.mUsersAssignedToDisplayOnStart.size(); i5++) {
            int keyAt = this.mUsersAssignedToDisplayOnStart.keyAt(i5);
            int valueAt = this.mUsersAssignedToDisplayOnStart.valueAt(i5);
            if (DBG) {
                Slogf.d("UserVisibilityMediator", "%d: assignedUserId=%d, assignedDisplayId=%d", Integer.valueOf(i5), Integer.valueOf(keyAt), Integer.valueOf(valueAt));
            }
            if (i4 == valueAt) {
                Slogf.w("UserVisibilityMediator", "Cannot assign user %d to display %d because such display is already assigned to user %d", Integer.valueOf(i), Integer.valueOf(i4), Integer.valueOf(keyAt));
                return -1;
            }
            if (i == keyAt) {
                Slogf.w("UserVisibilityMediator", "Cannot assign user %d to display %d because such user is as already assigned to display %d", Integer.valueOf(i), Integer.valueOf(i4), Integer.valueOf(keyAt));
                return -1;
            }
        }
        return 1;
    }

    public final void dispatchVisibilityChanged(IntArray intArray, IntArray intArray2) {
        CopyOnWriteArrayList copyOnWriteArrayList = this.mListeners;
        if (DBG) {
            Slogf.d("UserVisibilityMediator", "dispatchVisibilityChanged(): visibleUsersBefore=%s, visibleUsersAfter=%s, %d listeners (%s)", intArray, intArray2, Integer.valueOf(copyOnWriteArrayList.size()), copyOnWriteArrayList);
        }
        for (int i = 0; i < intArray.size(); i++) {
            int i2 = intArray.get(i);
            if (intArray2.indexOf(i2) == -1) {
                dispatchVisibilityChanged(copyOnWriteArrayList, i2, false);
            }
        }
        for (int i3 = 0; i3 < intArray2.size(); i3++) {
            int i4 = intArray2.get(i3);
            if (intArray.indexOf(i4) == -1) {
                dispatchVisibilityChanged(copyOnWriteArrayList, i4, true);
            }
        }
    }

    public final void dispatchVisibilityChanged(CopyOnWriteArrayList copyOnWriteArrayList, final int i, final boolean z) {
        EventLog.writeEvent(30091, Integer.valueOf(i), Integer.valueOf(z ? 1 : 0));
        if (DBG) {
            Slogf.d("UserVisibilityMediator", "dispatchVisibilityChanged(%d -> %b): sending to %d listeners", Integer.valueOf(i), Boolean.valueOf(z), Integer.valueOf(copyOnWriteArrayList.size()));
        }
        for (int i2 = 0; i2 < this.mListeners.size(); i2++) {
            final UserManagerInternal.UserVisibilityListener userVisibilityListener = (UserManagerInternal.UserVisibilityListener) this.mListeners.get(i2);
            this.mHandler.post(new Runnable() { // from class: com.android.server.pm.UserVisibilityMediator$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    UserManagerInternal.UserVisibilityListener.this.onUserVisibilityChanged(i, z);
                }
            });
        }
    }

    public final void dump(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("UserVisibilityMediator");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.print("DBG: ");
        indentingPrintWriter.println(DBG);
        synchronized (this.mLock) {
            try {
                indentingPrintWriter.print("Current user id: ");
                indentingPrintWriter.println(this.mCurrentUserId);
                indentingPrintWriter.print("Visible users: ");
                indentingPrintWriter.println(getVisibleUsers());
                dumpSparseIntArray(indentingPrintWriter, this.mStartedVisibleProfileGroupIds, "started visible user / profile group", "u", "pg");
                if (this.mStartedInvisibleProfileUserIds != null) {
                    indentingPrintWriter.print("Profiles started invisible: ");
                    indentingPrintWriter.println(this.mStartedInvisibleProfileUserIds);
                }
                indentingPrintWriter.print("Supports visible background users on displays: ");
                indentingPrintWriter.println(this.mVisibleBackgroundUsersEnabled);
                indentingPrintWriter.print("Supports visible background users on default display: ");
                indentingPrintWriter.println(this.mVisibleBackgroundUserOnDefaultDisplayEnabled);
                dumpSparseIntArray(indentingPrintWriter, this.mUsersAssignedToDisplayOnStart, "user / display", "u", "d");
                dumpSparseIntArray(indentingPrintWriter, this.mExtraDisplaysAssignedToUsers, "extra display / user", "d", "u");
                int size = this.mListeners.size();
                indentingPrintWriter.print("Number of listeners: ");
                indentingPrintWriter.println(size);
                if (size > 0) {
                    indentingPrintWriter.increaseIndent();
                    for (int i = 0; i < size; i++) {
                        indentingPrintWriter.print(i);
                        indentingPrintWriter.print(": ");
                        indentingPrintWriter.println(this.mListeners.get(i));
                    }
                    indentingPrintWriter.decreaseIndent();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        indentingPrintWriter.decreaseIndent();
    }

    @Override // android.util.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        if (printWriter instanceof IndentingPrintWriter) {
            dump((IndentingPrintWriter) printWriter);
        } else {
            dump(new IndentingPrintWriter(printWriter));
        }
    }

    public final int getMainDisplayAssignedToUser(int i) {
        int i2;
        int userAssignedToDisplay;
        if (!isCurrentUserOrRunningProfileOfCurrentUser(i)) {
            if (!this.mVisibleBackgroundUsersEnabled) {
                return -1;
            }
            synchronized (this.mLock) {
                i2 = this.mUsersAssignedToDisplayOnStart.get(i, -1);
            }
            return i2;
        }
        if (this.mVisibleBackgroundUserOnDefaultDisplayEnabled) {
            synchronized (this.mLock) {
                userAssignedToDisplay = getUserAssignedToDisplay(0, false);
            }
            if (userAssignedToDisplay != -10000) {
                if (DBG) {
                    Slogf.d("UserVisibilityMediator", "getMainDisplayAssignedToUser(%d): returning INVALID_DISPLAY for current user user %d was started on DEFAULT_DISPLAY", Integer.valueOf(i), Integer.valueOf(userAssignedToDisplay));
                }
                return -1;
            }
        }
        return 0;
    }

    public final int getUserAssignedToDisplay(int i, boolean z) {
        int i2;
        int i3;
        if (z && ((i == 0 && !this.mVisibleBackgroundUserOnDefaultDisplayEnabled) || !this.mVisibleBackgroundUsersEnabled)) {
            synchronized (this.mLock) {
                i3 = this.mCurrentUserId;
            }
            return i3;
        }
        synchronized (this.mLock) {
            for (int i4 = 0; i4 < this.mUsersAssignedToDisplayOnStart.size(); i4++) {
                try {
                    if (this.mUsersAssignedToDisplayOnStart.valueAt(i4) == i) {
                        int keyAt = this.mUsersAssignedToDisplayOnStart.keyAt(i4);
                        if (!isProfile(keyAt, this.mStartedVisibleProfileGroupIds.get(keyAt, -10000))) {
                            return keyAt;
                        }
                        if (DBG) {
                            Slogf.d("UserVisibilityMediator", "getUserAssignedToDisplay(%d): skipping user %d because it's a profile", Integer.valueOf(i), Integer.valueOf(keyAt));
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (!z) {
                if (DBG) {
                    Slogf.d("UserVisibilityMediator", "getUserAssignedToDisplay(%d): no user assigned to display, returning USER_NULL instead", Integer.valueOf(i));
                }
                return -10000;
            }
            synchronized (this.mLock) {
                i2 = this.mCurrentUserId;
            }
            if (DBG) {
                Slogf.d("UserVisibilityMediator", "getUserAssignedToDisplay(%d): no user assigned to display, returning current user (%d) instead", Integer.valueOf(i), Integer.valueOf(i2));
            }
            return i2;
        }
    }

    public final int getUserVisibilityOnStartLocked(int i, int i2, int i3, int i4) {
        int i5;
        if (i3 == 2 && i4 != 0) {
            Slogf.wtf("UserVisibilityMediator", "cannot start user (%d) as BACKGROUND_USER on secondary display (%d) (it should be BACKGROUND_USER_VISIBLE", Integer.valueOf(i), Integer.valueOf(i4));
            return -1;
        }
        boolean z = false;
        boolean z2 = i3 == 3;
        if (i4 == 0 && z2) {
            boolean z3 = this.mVisibleBackgroundUserOnDefaultDisplayEnabled;
            if (z3 && i != -10000 && (i5 = this.mCurrentUserId) != -10000 && i5 == i) {
                Slogf.wtf("UserVisibilityMediator", "trying to start current user (%d) visible in background on default display", Integer.valueOf(i));
                return 3;
            }
            if (!z3 && !isProfile(i, i2)) {
                Slogf.wtf("UserVisibilityMediator", "cannot start full user (%d) visible on default display", Integer.valueOf(i));
                return -1;
            }
        }
        boolean z4 = i3 == 1;
        if (i4 != 0) {
            if (z4) {
                Slogf.w("UserVisibilityMediator", "getUserVisibilityOnStartLocked(%d, %d, %s, %d) failed: cannot start foreground user on secondary display", Integer.valueOf(i), Integer.valueOf(i2), UserManagerInternal.userStartModeToString(i3), Integer.valueOf(i4));
                return -1;
            }
            if (!this.mVisibleBackgroundUsersEnabled) {
                Slogf.w("UserVisibilityMediator", "getUserVisibilityOnStartLocked(%d, %d, %s, %d) failed: called on device that doesn't support multiple users on multiple displays", Integer.valueOf(i), Integer.valueOf(i2), UserManagerInternal.userStartModeToString(i3), Integer.valueOf(i4));
                return -1;
            }
        }
        if (!isProfile(i, i2)) {
            SparseIntArray sparseIntArray = this.mUsersAssignedToDisplayOnStart;
            if (sparseIntArray != null) {
                if (sparseIntArray == null) {
                    Slogf.wtf("UserVisibilityMediator", "isUserAssignedToDisplayOnStartLocked(%d, %d): called when mUsersAssignedToDisplayOnStart is null", Integer.valueOf(i), Integer.valueOf(i4));
                } else if (i4 != -1 && sparseIntArray.get(i, -1) == i4) {
                    z = true;
                }
                if (z) {
                    if (DBG) {
                        Slogf.d("UserVisibilityMediator", "full user %d is already visible on display %d", Integer.valueOf(i), Integer.valueOf(i4));
                    }
                    return 3;
                }
            }
        } else {
            if (i4 != 0) {
                Slogf.w("UserVisibilityMediator", "canStartUserLocked(%d, %d, %s, %d) failed: cannot start profile user on secondary display", Integer.valueOf(i), Integer.valueOf(i2), UserManagerInternal.userStartModeToString(i3), Integer.valueOf(i4));
                return -1;
            }
            if (i3 == 1) {
                Slogf.w("UserVisibilityMediator", "startUser(%d, %d, %s, %d) failed: cannot start profile user in foreground", Integer.valueOf(i), Integer.valueOf(i2), UserManagerInternal.userStartModeToString(i3), Integer.valueOf(i4));
                return -1;
            }
            if (i3 == 2) {
                return 2;
            }
            if (i3 == 3) {
                if (i2 == -1 ? true : isUserVisible(i2, i4)) {
                    return 1;
                }
                Slogf.w("UserVisibilityMediator", "getUserVisibilityOnStartLocked(%d, %d, %s, %d) failed: cannot start profile user visible when its parent is not visible in that display", Integer.valueOf(i), Integer.valueOf(i2), UserManagerInternal.userStartModeToString(i3), Integer.valueOf(i4));
                return -1;
            }
        }
        return (z4 || i4 != 0 || (z2 && this.mVisibleBackgroundUserOnDefaultDisplayEnabled)) ? 1 : 2;
    }

    public final IntArray getVisibleUsers() {
        IntArray intArray = new IntArray();
        synchronized (this.mLock) {
            for (int i = 0; i < this.mStartedVisibleProfileGroupIds.size(); i++) {
                try {
                    int keyAt = this.mStartedVisibleProfileGroupIds.keyAt(i);
                    if (isUserVisible(keyAt)) {
                        intArray.add(keyAt);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        return intArray;
    }

    public final boolean isCurrentUserOrRunningProfileOfCurrentUser(int i) {
        synchronized (this.mLock) {
            if (i != -10000) {
                try {
                    int i2 = this.mCurrentUserId;
                    if (i2 != -10000) {
                        if (i2 == i) {
                            return true;
                        }
                        int i3 = this.mStartedVisibleProfileGroupIds.get(i, -10000);
                        return i3 == this.mCurrentUserId || i3 == -1;
                    }
                } finally {
                }
            }
            return false;
        }
    }

    public final boolean isUserVisible(int i) {
        int i2;
        boolean z = true;
        if (isCurrentUserOrRunningProfileOfCurrentUser(i)) {
            return true;
        }
        if (!this.mVisibleBackgroundUsersEnabled) {
            return false;
        }
        synchronized (this.mLock) {
            try {
                synchronized (this.mLock) {
                    i2 = this.mStartedVisibleProfileGroupIds.get(i, -10000);
                }
                if (isProfile(i, i2)) {
                    if (this.mUsersAssignedToDisplayOnStart.indexOfKey(i2) < 0) {
                        z = false;
                    }
                    return z;
                }
                if (this.mUsersAssignedToDisplayOnStart.indexOfKey(i) < 0) {
                    z = false;
                }
                return z;
            } finally {
            }
        }
    }

    public final boolean isUserVisible(int i, int i2) {
        int i3;
        if (i2 == -1) {
            return false;
        }
        boolean z = true;
        if (isCurrentUserOrRunningProfileOfCurrentUser(i) && (i2 == 0 || !this.mVisibleBackgroundUsersEnabled)) {
            return true;
        }
        if (!this.mVisibleBackgroundUsersEnabled) {
            if (DBG) {
                Slogf.d("UserVisibilityMediator", "isUserVisible(%d, %d): returning false as device does not support visible background users", Integer.valueOf(i), Integer.valueOf(i2));
            }
            return false;
        }
        synchronized (this.mLock) {
            try {
                synchronized (this.mLock) {
                    i3 = this.mStartedVisibleProfileGroupIds.get(i, -10000);
                }
                if (isProfile(i, i3)) {
                    if (this.mUsersAssignedToDisplayOnStart.get(i3, -1) != i2) {
                        z = this.mExtraDisplaysAssignedToUsers.get(i2, -10000) == i3;
                    }
                    return z;
                }
                if (this.mUsersAssignedToDisplayOnStart.get(i, -1) != i2) {
                    z = this.mExtraDisplaysAssignedToUsers.get(i2, -10000) == i;
                }
                return z;
            } finally {
            }
        }
    }

    public final void unassignUserFromAllDisplaysOnStopLocked(int i) {
        boolean z = DBG;
        if (z) {
            Slogf.d("UserVisibilityMediator", "Removing %d from mStartedVisibleProfileGroupIds (%s)", Integer.valueOf(i), this.mStartedVisibleProfileGroupIds);
        }
        this.mStartedVisibleProfileGroupIds.delete(i);
        if (this.mStartedInvisibleProfileUserIds != null) {
            Slogf.d("UserVisibilityMediator", "Removing %d from list of invisible profiles", Integer.valueOf(i));
            this.mStartedInvisibleProfileUserIds.remove(Integer.valueOf(i));
        }
        if (this.mVisibleBackgroundUsersEnabled) {
            if (z) {
                Slogf.d("UserVisibilityMediator", "Removing user %d from mUsersOnDisplaysMap (%s)", Integer.valueOf(i), this.mUsersAssignedToDisplayOnStart);
            }
            this.mUsersAssignedToDisplayOnStart.delete(i);
            for (int size = this.mExtraDisplaysAssignedToUsers.size() - 1; size >= 0; size--) {
                if (this.mExtraDisplaysAssignedToUsers.valueAt(size) == i) {
                    if (DBG) {
                        Slogf.d("UserVisibilityMediator", "Removing display %d from mExtraDisplaysAssignedToUsers (%s)", Integer.valueOf(this.mExtraDisplaysAssignedToUsers.keyAt(size)), this.mExtraDisplaysAssignedToUsers);
                    }
                    this.mExtraDisplaysAssignedToUsers.removeAt(size);
                }
            }
        }
    }
}
