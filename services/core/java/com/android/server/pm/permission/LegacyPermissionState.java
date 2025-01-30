package com.android.server.pm.permission;

import android.util.ArrayMap;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class LegacyPermissionState {
    public final SparseArray mUserStates = new SparseArray();
    public final SparseBooleanArray mMissing = new SparseBooleanArray();

    public void copyFrom(LegacyPermissionState legacyPermissionState) {
        if (legacyPermissionState == this) {
            return;
        }
        this.mUserStates.clear();
        int size = legacyPermissionState.mUserStates.size();
        for (int i = 0; i < size; i++) {
            this.mUserStates.put(legacyPermissionState.mUserStates.keyAt(i), new UserState((UserState) legacyPermissionState.mUserStates.valueAt(i)));
        }
        this.mMissing.clear();
        int size2 = legacyPermissionState.mMissing.size();
        for (int i2 = 0; i2 < size2; i2++) {
            this.mMissing.put(legacyPermissionState.mMissing.keyAt(i2), legacyPermissionState.mMissing.valueAt(i2));
        }
    }

    public void reset() {
        this.mUserStates.clear();
        this.mMissing.clear();
    }

    public boolean equals(Object obj) {
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
            if (!Objects.equals(this.mUserStates.get(keyAt), legacyPermissionState.mUserStates.get(keyAt))) {
                return false;
            }
        }
        return Objects.equals(this.mMissing, legacyPermissionState.mMissing);
    }

    public PermissionState getPermissionState(String str, int i) {
        checkUserId(i);
        UserState userState = (UserState) this.mUserStates.get(i);
        if (userState == null) {
            return null;
        }
        return userState.getPermissionState(str);
    }

    public void putPermissionState(PermissionState permissionState, int i) {
        checkUserId(i);
        UserState userState = (UserState) this.mUserStates.get(i);
        if (userState == null) {
            userState = new UserState();
            this.mUserStates.put(i, userState);
        }
        userState.putPermissionState(permissionState);
    }

    public boolean hasPermissionState(Collection collection) {
        int size = this.mUserStates.size();
        for (int i = 0; i < size; i++) {
            UserState userState = (UserState) this.mUserStates.valueAt(i);
            Iterator it = collection.iterator();
            while (it.hasNext()) {
                if (userState.getPermissionState((String) it.next()) != null) {
                    return true;
                }
            }
        }
        return false;
    }

    public Collection getPermissionStates(int i) {
        checkUserId(i);
        UserState userState = (UserState) this.mUserStates.get(i);
        if (userState == null) {
            return Collections.emptyList();
        }
        return userState.getPermissionStates();
    }

    public boolean isMissing(int i) {
        checkUserId(i);
        return this.mMissing.get(i);
    }

    public void setMissing(boolean z, int i) {
        checkUserId(i);
        if (z) {
            this.mMissing.put(i, true);
        } else {
            this.mMissing.delete(i);
        }
    }

    public static void checkUserId(int i) {
        if (i >= 0) {
            return;
        }
        throw new IllegalArgumentException("Invalid user ID " + i);
    }

    public final class UserState {
        public final ArrayMap mPermissionStates = new ArrayMap();

        public UserState() {
        }

        public UserState(UserState userState) {
            int size = userState.mPermissionStates.size();
            for (int i = 0; i < size; i++) {
                this.mPermissionStates.put((String) userState.mPermissionStates.keyAt(i), new PermissionState((PermissionState) userState.mPermissionStates.valueAt(i)));
            }
        }

        public PermissionState getPermissionState(String str) {
            return (PermissionState) this.mPermissionStates.get(str);
        }

        public void putPermissionState(PermissionState permissionState) {
            this.mPermissionStates.put(permissionState.getName(), permissionState);
        }

        public Collection getPermissionStates() {
            return Collections.unmodifiableCollection(this.mPermissionStates.values());
        }
    }

    public final class PermissionState {
        public final int mFlags;
        public final boolean mGranted;
        public final String mName;
        public final boolean mRuntime;

        public PermissionState(String str, boolean z, boolean z2, int i) {
            this.mName = str;
            this.mRuntime = z;
            this.mGranted = z2;
            this.mFlags = i;
        }

        public PermissionState(PermissionState permissionState) {
            this.mName = permissionState.mName;
            this.mRuntime = permissionState.mRuntime;
            this.mGranted = permissionState.mGranted;
            this.mFlags = permissionState.mFlags;
        }

        public String getName() {
            return this.mName;
        }

        public boolean isRuntime() {
            return this.mRuntime;
        }

        public boolean isGranted() {
            return this.mGranted;
        }

        public int getFlags() {
            return this.mFlags;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || PermissionState.class != obj.getClass()) {
                return false;
            }
            PermissionState permissionState = (PermissionState) obj;
            return this.mRuntime == permissionState.mRuntime && this.mGranted == permissionState.mGranted && this.mFlags == permissionState.mFlags && Objects.equals(this.mName, permissionState.mName);
        }

        public int hashCode() {
            return Objects.hash(this.mName, Boolean.valueOf(this.mRuntime), Boolean.valueOf(this.mGranted), Integer.valueOf(this.mFlags));
        }
    }
}
