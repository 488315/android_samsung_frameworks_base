package com.android.systemui.plugins.statusbar;

import android.service.notification.SnoozeCriterion;
import android.service.notification.StatusBarNotification;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import com.android.systemui.plugins.annotations.DependsOn;
import com.android.systemui.plugins.annotations.ProvidesInterface;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
@ProvidesInterface(version = 1)
@DependsOn(target = SnoozeOption.class)
/* loaded from: classes2.dex */
public interface NotificationSwipeActionHelper {
    public static final String ACTION = "com.android.systemui.action.PLUGIN_NOTIFICATION_SWIPE_ACTION";
    public static final int VERSION = 1;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
