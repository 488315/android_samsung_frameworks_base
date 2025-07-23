package com.android.systemui.temporarydisplay.chipbar;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.statusbar.gesture.SwipeUpGestureHandler;
import com.android.systemui.statusbar.gesture.SwipeUpGestureLogger;
import com.android.systemui.util.ConvenienceExtensionsKt;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SwipeChipbarAwayGestureHandler extends SwipeUpGestureHandler {
    public Function0 viewFetcher;

    public SwipeChipbarAwayGestureHandler(Context context, DisplayTracker displayTracker, SwipeUpGestureLogger swipeUpGestureLogger) {
        super(context, displayTracker, swipeUpGestureLogger, "SwipeChipbarAway");
        this.viewFetcher = new Function0() { // from class: com.android.systemui.temporarydisplay.chipbar.SwipeChipbarAwayGestureHandler$viewFetcher$1
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return null;
            }
        };
    }

    @Override // com.android.systemui.statusbar.gesture.SwipeUpGestureHandler
    public final boolean startOfGestureIsWithinBounds(MotionEvent motionEvent) {
        View view = (View) this.viewFetcher.invoke();
        if (view == null) {
            return false;
        }
        return ((double) motionEvent.getY()) <= ((double) ConvenienceExtensionsKt.getBoundsOnScreen(view).bottom) * 1.5d;
    }
}
