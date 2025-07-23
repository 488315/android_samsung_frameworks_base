package com.android.systemui.ambient.touch;

import com.android.systemui.ambient.touch.TouchMonitor;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class TouchMonitor$$ExternalSyntheticLambda6 implements Consumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ TouchMonitor$$ExternalSyntheticLambda6(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        TouchMonitor.TouchSessionImpl touchSessionImpl = (TouchMonitor.TouchSessionImpl) obj;
        switch (this.$r8$classId) {
            case 0:
                break;
            default:
                TouchMonitor.TouchSessionImpl.m1010$$Nest$monRemoved(touchSessionImpl);
                return;
        }
        while (touchSessionImpl != null) {
            TouchMonitor.TouchSessionImpl.m1010$$Nest$monRemoved(touchSessionImpl);
            touchSessionImpl = touchSessionImpl.mPredecessor;
        }
    }
}
