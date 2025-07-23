package com.android.keyguard;

import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import com.android.systemui.plugins.clocks.ClockController;
import com.android.systemui.plugins.clocks.ClockEvents;
import com.android.systemui.settings.UserTrackerImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ClockEventController$connectClock$9 implements View.OnAttachStateChangeListener {
    public final /* synthetic */ ClockController $clock;
    public Integer pastVisibility;
    public final /* synthetic */ ClockEventController this$0;

    public ClockEventController$connectClock$9(ClockController clockController, ClockEventController clockEventController) {
        this.$clock = clockController;
        this.this$0 = clockEventController;
    }

    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.keyguard.ClockEventController$connectClock$9$onViewAttachedToWindow$1$1] */
    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewAttachedToWindow(View view) {
        boolean is24HourFormat;
        ClockEvents events = this.$clock.getEvents();
        is24HourFormat = DateFormat.is24HourFormat(r1.context, ((UserTrackerImpl) this.this$0.userTracker).getUserId());
        events.onTimeFormatChanged(is24HourFormat);
        ClockEventController clockEventController = this.this$0;
        final ViewGroup viewGroup = (ViewGroup) view.getParent();
        final ClockEventController clockEventController2 = this.this$0;
        this.pastVisibility = Integer.valueOf(viewGroup.getVisibility());
        clockEventController2.onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.android.keyguard.ClockEventController$connectClock$9$onViewAttachedToWindow$1$1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public final void onGlobalLayout() {
                int visibility = viewGroup.getVisibility();
                Integer num = this.pastVisibility;
                if (num != null && num.intValue() == visibility) {
                    return;
                }
                this.pastVisibility = Integer.valueOf(visibility);
                if (visibility == 0) {
                    clockEventController2.getClass();
                    clockEventController2.getClass();
                }
            }
        };
        viewGroup.getViewTreeObserver().addOnGlobalLayoutListener(clockEventController2.onGlobalLayoutListener);
        clockEventController.smallClockFrame = viewGroup;
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewDetachedFromWindow(View view) {
        ViewTreeObserver viewTreeObserver;
        ViewGroup viewGroup = this.this$0.smallClockFrame;
        if (viewGroup == null || (viewTreeObserver = viewGroup.getViewTreeObserver()) == null) {
            return;
        }
        viewTreeObserver.removeOnGlobalLayoutListener(this.this$0.onGlobalLayoutListener);
    }
}
