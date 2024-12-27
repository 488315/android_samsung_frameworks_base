package com.android.systemui.settings;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public interface DisplayTracker {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Callback {
        default void onDisplayAdded(int i) {
        }

        default void onDisplayChanged(int i) {
        }

        default void onDisplayRemoved(int i) {
        }
    }
}
