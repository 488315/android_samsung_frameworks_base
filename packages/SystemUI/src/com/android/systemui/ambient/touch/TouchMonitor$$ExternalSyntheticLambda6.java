package com.android.systemui.ambient.touch;

import com.android.systemui.ambient.touch.TouchMonitor;
import java.util.function.Consumer;

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
                TouchMonitor.TouchSessionImpl.m1012$$Nest$monRemoved(touchSessionImpl);
                return;
        }
        while (touchSessionImpl != null) {
            TouchMonitor.TouchSessionImpl.m1012$$Nest$monRemoved(touchSessionImpl);
            touchSessionImpl = touchSessionImpl.mPredecessor;
        }
    }
}
