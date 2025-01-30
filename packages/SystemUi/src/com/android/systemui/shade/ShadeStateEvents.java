package com.android.systemui.shade;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface ShadeStateEvents {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface ShadeStateEventsListener {
        default void onExpandImmediateChanged(boolean z) {
        }

        default void onLaunchingActivityChanged(boolean z) {
        }

        default void onPanelCollapsingChanged(boolean z) {
        }
    }
}
