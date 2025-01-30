package com.android.systemui.plugins.statusbar;

import com.android.systemui.plugins.annotations.DependsOn;
import com.android.systemui.plugins.annotations.ProvidesInterface;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@ProvidesInterface(version = 1)
@DependsOn(target = StateListener.class)
/* loaded from: classes2.dex */
public interface StatusBarStateController {
    public static final int VERSION = 1;

    void addCallback(StateListener stateListener);

    float getDozeAmount();

    int getState();

    boolean isDozing();

    boolean isDreaming();

    boolean isExpanded();

    boolean isPulsing();

    void removeCallback(StateListener stateListener);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @ProvidesInterface(version = 1)
    public interface StateListener {
        public static final int VERSION = 1;

        default void onDozingChanged(boolean z) {
        }

        default void onDreamingChanged(boolean z) {
        }

        default void onExpandedChanged(boolean z) {
        }

        default void onFullscreenStateChanged(boolean z) {
        }

        default void onPulsingChanged(boolean z) {
        }

        default void onStateChanged(int i) {
        }

        default void onUpcomingStateChanged(int i) {
        }

        default void onStatePostChange() {
        }

        default void onDozeAmountChanged(float f, float f2) {
        }

        default void onStatePreChange(int i, int i2) {
        }
    }
}
