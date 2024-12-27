package com.android.systemui.settings;

import android.content.Context;
import java.util.List;

public interface UserTracker extends UserContextProvider {

    public interface Callback {
        default void onUserChanging(int i) {
        }

        default void onUserChanging(int i, Context context, Runnable runnable) {
            onUserChanging(i);
            runnable.run();
        }

        default void onBeforeUserSwitching(int i) {
        }

        default void onProfilesChanged(List list) {
        }

        default void onUserChanged(int i, Context context) {
        }
    }
}
