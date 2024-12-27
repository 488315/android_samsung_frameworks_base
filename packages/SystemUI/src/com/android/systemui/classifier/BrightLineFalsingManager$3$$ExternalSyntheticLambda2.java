package com.android.systemui.classifier;

import android.view.MotionEvent;
import com.android.systemui.classifier.BrightLineFalsingManager;
import java.util.function.Function;

public final /* synthetic */ class BrightLineFalsingManager$3$$ExternalSyntheticLambda2 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        MotionEvent motionEvent = (MotionEvent) obj;
        return new BrightLineFalsingManager.XYDt((int) motionEvent.getX(), (int) motionEvent.getY(), (int) (motionEvent.getEventTime() - motionEvent.getDownTime()));
    }
}
