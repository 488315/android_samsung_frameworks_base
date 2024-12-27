package com.android.systemui.plugins.statusbar;

import android.service.notification.SnoozeCriterion;
import android.service.notification.StatusBarNotification;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import com.android.systemui.plugins.annotations.DependsOn;
import com.android.systemui.plugins.annotations.ProvidesInterface;

@ProvidesInterface(version = 1)
@DependsOn(target = SnoozeOption.class)
public interface NotificationSwipeActionHelper {
    public static final String ACTION = "com.android.systemui.action.PLUGIN_NOTIFICATION_SWIPE_ACTION";
    public static final int VERSION = 1;

    @ProvidesInterface(version = 2)
    public interface SnoozeOption {
        public static final int VERSION = 2;

        AccessibilityNodeInfo.AccessibilityAction getAccessibilityAction();

        CharSequence getConfirmation();

        CharSequence getDescription();

        int getMinutesToSnoozeFor();

        SnoozeCriterion getSnoozeCriterion();
    }

    void dismiss(View view, float f);

    float getMinDismissVelocity();

    boolean isDismissGesture(MotionEvent motionEvent);

    boolean isFalseGesture();

    void snapOpen(View view, int i, float f);

    void snooze(StatusBarNotification statusBarNotification, SnoozeOption snoozeOption);
}
