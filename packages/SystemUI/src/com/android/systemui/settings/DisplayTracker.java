package com.android.systemui.settings;

/* loaded from: classes3.dex */
public interface DisplayTracker {

    public interface Callback {
        default void onDisplayAdded(int i) {
        }

        default void onDisplayChanged(int i) {
        }

        default void onDisplayRemoved(int i) {
        }
    }
}
