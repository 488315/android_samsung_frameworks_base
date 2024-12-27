package com.android.keyguard;

import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import com.android.systemui.plugins.clocks.ClockController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class ClockEventController$connectClock$9 implements View.OnAttachStateChangeListener {
    public final /* synthetic */ ClockController $clock;
    public Integer pastVisibility;
    public final /* synthetic */ ClockEventController this$0;

    public ClockEventController$connectClock$9(ClockController clockController, ClockEventController clockEventController) {
        this.$clock = clockController;
        this.this$0 = clockEventController;
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewAttachedToWindow(View view) {
        this.$clock.getEvents().onTimeFormatChanged(DateFormat.is24HourFormat(this.this$0.context));
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
