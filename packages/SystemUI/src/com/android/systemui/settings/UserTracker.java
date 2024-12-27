package com.android.systemui.settings;

import android.content.Context;
import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public interface UserTracker extends UserContextProvider {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
