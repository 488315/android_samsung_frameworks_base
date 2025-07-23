package com.android.systemui.statusbar;

import android.util.SparseArray;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface NotificationLockscreenUserManager {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface NotificationStateChangedListener {
        void onNotificationStateChanged();
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface UserChangedListener {
        default void onCurrentProfilesChanged(SparseArray sparseArray) {
        }

        default void onUserChanged(int i) {
        }

        default void onUserRemoved(int i) {
        }
    }
}
