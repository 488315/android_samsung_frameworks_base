package com.android.systemui.settings;

import android.content.Context;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface UserTracker extends UserContextProvider {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Callback {
        default void onUserChanging(int i) {
        }

        default void onUserChanging(int i, Context context, CountDownLatch countDownLatch) {
            onUserChanging(i);
            countDownLatch.countDown();
        }

        default void onProfilesChanged(List list) {
        }

        default void onUserChanged(int i, Context context) {
        }
    }
}
