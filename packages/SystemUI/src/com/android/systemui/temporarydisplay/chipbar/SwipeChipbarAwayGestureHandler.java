package com.android.systemui.temporarydisplay.chipbar;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.statusbar.gesture.SwipeUpGestureHandler;
import com.android.systemui.statusbar.gesture.SwipeUpGestureLogger;
import com.android.systemui.util.ConvenienceExtensionsKt;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
