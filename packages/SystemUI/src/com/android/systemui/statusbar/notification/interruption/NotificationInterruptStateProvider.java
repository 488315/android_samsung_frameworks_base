package com.android.systemui.statusbar.notification.interruption;

public interface NotificationInterruptStateProvider {

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
        FSI_KEYGUARD_OCCLUDED(true),
        FSI_LOCKED_SHADE(true),
        NO_FSI_NO_HUN_OR_KEYGUARD(false),
        NO_FSI_SUSPENDED(false),
        FSI_NOT_PROVISIONED(true),
        FSI_USER_SETUP_INCOMPLETE(true);

        public final boolean shouldLaunch;

        FullScreenIntentDecision(boolean z) {
            this.shouldLaunch = z;
        }
    }
}
