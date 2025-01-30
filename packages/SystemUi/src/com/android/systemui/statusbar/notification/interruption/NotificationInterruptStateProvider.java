package com.android.systemui.statusbar.notification.interruption;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface NotificationInterruptStateProvider {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum FullScreenIntentDecision {
        NO_FSI_SHOW_STICKY_HUN(false),
        NO_FULL_SCREEN_INTENT(false),
        NO_FSI_SUPPRESSED_BY_DND(false),
        NO_FSI_SUPPRESSED_ONLY_BY_DND(false),
        NO_FSI_NOT_IMPORTANT_ENOUGH(false),
        NO_FSI_SUPPRESSIVE_GROUP_ALERT_BEHAVIOR(false),
        NO_FSI_SUPPRESSIVE_BUBBLE_METADATA(false),
        FSI_DEVICE_NOT_INTERACTIVE(true),
        FSI_DEVICE_IS_DREAMING(true),
        FSI_KEYGUARD_SHOWING(true),
        NO_FSI_EXPECTED_TO_HUN(false),
        NO_FSI_EXPECTED_TO_BRIEF(false),
        NO_FSI_NO_HUN_BY_PANEL_DISABLED(false),
        FSI_KEYGUARD_OCCLUDED(true),
        FSI_LOCKED_SHADE(true),
        NO_FSI_NO_HUN_OR_KEYGUARD(false),
        NO_FSI_SUSPENDED(false);

        public final boolean shouldLaunch;

        FullScreenIntentDecision(boolean z) {
            this.shouldLaunch = z;
        }
    }
}
