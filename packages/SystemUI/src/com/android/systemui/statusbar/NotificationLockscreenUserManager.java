package com.android.systemui.statusbar;

import android.util.SparseArray;

/* loaded from: classes3.dex */
public interface NotificationLockscreenUserManager {

    public interface NotificationStateChangedListener {
        void onNotificationStateChanged();
    }

    public interface UserChangedListener {
        default void onCurrentProfilesChanged(SparseArray sparseArray) {
        }

        default void onUserChanged(int i) {
        }

        default void onUserRemoved(int i) {
        }
    }
}
