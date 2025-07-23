package com.android.systemui.statusbar.notification.interruption;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface NotificationInterruptStateProvider {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public enum FullScreenIntentDecision {
        NO_FSI_SHOW_STICKY_HUN(false),
        NO_FULL_SCREEN_INTENT(false),
        NO_FSI_SUPPRESSED_BY_DND(false),
        NO_FSI_SUPPRESSED_ONLY_BY_DND(false),
        NO_FSI_NOT_IMPORTANT_ENOUGH(false),
        NO_FSI_SUPPRESSIVE_GROUP_ALERT_BEHAVIOR(false),
        NO_FSI_SUPPRESSIVE_BUBBLE_METADATA(false),
        NO_FSI_SUPPRESSIVE_SILENT_NOTIFICATION(false),
        FSI_DEVICE_NOT_INTERACTIVE(true),
        FSI_DEVICE_IS_DREAMING(true),
        FSI_KEYGUARD_SHOWING(true),
        NO_FSI_EXPECTED_TO_HUN(false),
        NO_FSI_EXPECTED_TO_BRIEF(false),
        FSI_LARGE_COVER_SCREEN(true),
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
