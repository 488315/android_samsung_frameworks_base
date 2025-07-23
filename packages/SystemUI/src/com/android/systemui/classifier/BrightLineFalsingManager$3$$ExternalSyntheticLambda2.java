package com.android.systemui.classifier;

import android.view.MotionEvent;
import com.android.systemui.classifier.BrightLineFalsingManager;
import java.util.function.Function;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class BrightLineFalsingManager$3$$ExternalSyntheticLambda2 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        MotionEvent motionEvent = (MotionEvent) obj;
        return new BrightLineFalsingManager.XYDt((int) motionEvent.getX(), (int) motionEvent.getY(), (int) (motionEvent.getEventTime() - motionEvent.getDownTime()));
    }
}
