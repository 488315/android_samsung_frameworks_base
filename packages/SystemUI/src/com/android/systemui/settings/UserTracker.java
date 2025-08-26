package com.android.systemui.settings;

import android.content.Context;
import com.android.systemui.settings.UserTrackerImpl$handleBeforeUserSwitching$$inlined$notifySubscribers$1;
import java.util.List;

/* loaded from: classes3.dex */
public interface UserTracker extends UserContextProvider {

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
