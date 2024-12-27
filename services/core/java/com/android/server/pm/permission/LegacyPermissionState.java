package com.android.server.pm.permission;

import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.util.ArrayMap;
import android.util.SparseArray;
import android.util.SparseBooleanArray;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Objects;

public final class LegacyPermissionState {
    public final SparseArray mUserStates = new SparseArray();
    public final SparseBooleanArray mMissing = new SparseBooleanArray();

    public final class PermissionState {
        public final int mFlags;
        public final boolean mGranted;
        public final String mName;
        public final boolean mRuntime;

        public PermissionState(PermissionState permissionState) {
            this.mName = permissionState.mName;
            this.mRuntime = permissionState.mRuntime;
            this.mGranted = permissionState.mGranted;
            this.mFlags = permissionState.mFlags;
        }

        public PermissionState(String str, boolean z, boolean z2, int i) {
            this.mName = str;
            this.mRuntime = z;
            this.mGranted = z2;
            this.mFlags = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || PermissionState.class != obj.getClass()) {
                return false;
            }
            PermissionState permissionState = (PermissionState) obj;
            return this.mRuntime == permissionState.mRuntime
                    && this.mGranted == permissionState.mGranted
                    && this.mFlags == permissionState.mFlags
                    && Objects.equals(this.mName, permissionState.mName);
        }

        public final int hashCode() {
            return Objects.hash(
                    this.mName,
                    Boolean.valueOf(this.mRuntime),
                    Boolean.valueOf(this.mGranted),
                    Integer.valueOf(this.mFlags));
        }
    }

    public final class UserState {
        public final ArrayMap mPermissionStates = new ArrayMap();

        public UserState() {}

        public UserState(UserState userState) {
            int size = userState.mPermissionStates.size();
            for (int i = 0; i < size; i++) {
                this.mPermissionStates.put(
                        (String) userState.mPermissionStates.keyAt(i),
                        new PermissionState(
                                (PermissionState) userState.mPermissionStates.valueAt(i)));
            }
        }
    }

    public static void checkUserId(int i) {
        if (i < 0) {
            throw new IllegalArgumentException(
                    VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Invalid user ID "));
        }
    }

    public final void copyFrom(LegacyPermissionState legacyPermissionState) {
        if (legacyPermissionState == this) {
            return;
        }
        this.mUserStates.clear();
        int size = legacyPermissionState.mUserStates.size();
        for (int i = 0; i < size; i++) {
            this.mUserStates.put(
                    legacyPermissionState.mUserStates.keyAt(i),
                    new UserState((UserState) legacyPermissionState.mUserStates.valueAt(i)));
        }
        this.mMissing.clear();
        int size2 = legacyPermissionState.mMissing.size();
        for (int i2 = 0; i2 < size2; i2++) {
            this.mMissing.put(
                    legacyPermissionState.mMissing.keyAt(i2),
                    legacyPermissionState.mMissing.valueAt(i2));
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || LegacyPermissionState.class != obj.getClass()) {
            return false;
        }
        LegacyPermissionState legacyPermissionState = (LegacyPermissionState) obj;
        int size = this.mUserStates.size();
        if (size != legacyPermissionState.mUserStates.size()) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            int keyAt = this.mUserStates.keyAt(i);
            if (!Objects.equals(
                    this.mUserStates.get(keyAt), legacyPermissionState.mUserStates.get(keyAt))) {
                return false;
            }
        }
        return Objects.equals(this.mMissing, legacyPermissionState.mMissing);
    }

    public final Collection getPermissionStates(int i) {
        checkUserId(i);
        UserState userState = (UserState) this.mUserStates.get(i);
        return userState == null
                ? Collections.emptyList()
                : Collections.unmodifiableCollection(userState.mPermissionStates.values());
    }

    public final boolean hasPermissionState(Collection collection) {
        int size = this.mUserStates.size();
        for (int i = 0; i < size; i++) {
            UserState userState = (UserState) this.mUserStates.valueAt(i);
            Iterator it = collection.iterator();
            while (it.hasNext()) {
                if (((PermissionState) userState.mPermissionStates.get((String) it.next()))
                        != null) {
                    return true;
                }
            }
        }
        return false;
    }

    public final void putPermissionState(PermissionState permissionState, int i) {
        checkUserId(i);
        UserState userState = (UserState) this.mUserStates.get(i);
        if (userState == null) {
            userState = new UserState();
            this.mUserStates.put(i, userState);
        }
        userState.mPermissionStates.put(permissionState.mName, permissionState);
    }

    public final void setMissing(int i, boolean z) {
        checkUserId(i);
        if (z) {
            this.mMissing.put(i, true);
        } else {
            this.mMissing.delete(i);
        }
    }
}
