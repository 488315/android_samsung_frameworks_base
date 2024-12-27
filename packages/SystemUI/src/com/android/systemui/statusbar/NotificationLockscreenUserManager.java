package com.android.systemui.statusbar;

import android.util.SparseArray;

public interface NotificationLockscreenUserManager {

    public interface UserChangedListener {
        default void onCurrentProfilesChanged(SparseArray sparseArray) {
        }

        default void onUserChanged(int i) {
        }

        default void onUserRemoved(int i) {
        }
    }
}
