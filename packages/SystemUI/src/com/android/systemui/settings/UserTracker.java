package com.android.systemui.settings;

import android.content.Context;
import com.android.systemui.settings.UserTrackerImpl$handleBeforeUserSwitching$$inlined$notifySubscribers$1;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface UserTracker extends UserContextProvider {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Callback {
        default void onBeforeUserSwitching(int i) {
        }

        default void onUserChanging(int i) {
        }

        default void onBeforeUserSwitching(int i, UserTrackerImpl$handleBeforeUserSwitching$$inlined$notifySubscribers$1.AnonymousClass1 anonymousClass1) {
            onBeforeUserSwitching(i);
            anonymousClass1.run();
        }

        default void onUserChanging(int i, Context context, Runnable runnable) {
            onUserChanging(i);
            runnable.run();
        }

        default void onProfilesChanged(List list) {
        }

        default void onUserChanged(int i, Context context) {
        }
    }
}
