package com.android.systemui.shared.clocks;

import android.view.View;
import com.android.systemui.plugins.clocks.ClockAnimations;
import com.android.systemui.plugins.clocks.ClockEvents;
import com.android.systemui.plugins.clocks.ClockFaceEvents;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface SimpleClockLayerController {
    ClockAnimations getAnimations();

    ClockEvents getEvents();

    ClockFaceEvents getFaceEvents();

    View getView();

    void setOnViewBoundsChanged(Function1 function1);

    static /* synthetic */ void getFakeTimeMills$annotations() {
    }
}
