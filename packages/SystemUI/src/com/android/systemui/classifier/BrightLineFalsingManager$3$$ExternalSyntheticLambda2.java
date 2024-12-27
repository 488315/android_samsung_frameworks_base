package com.android.systemui.classifier;

import android.view.MotionEvent;
import com.android.systemui.classifier.BrightLineFalsingManager;
import java.util.function.Function;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final /* synthetic */ class BrightLineFalsingManager$3$$ExternalSyntheticLambda2 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        MotionEvent motionEvent = (MotionEvent) obj;
        return new BrightLineFalsingManager.XYDt((int) motionEvent.getX(), (int) motionEvent.getY(), (int) (motionEvent.getEventTime() - motionEvent.getDownTime()));
    }
}
