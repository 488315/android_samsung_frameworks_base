package com.android.systemui.ambient.touch;

import com.android.systemui.ambient.touch.TouchMonitor;
import java.util.function.Consumer;

public final /* synthetic */ class TouchMonitor$$ExternalSyntheticLambda3 implements Consumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ TouchMonitor$$ExternalSyntheticLambda3(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        TouchMonitor.TouchSessionImpl touchSessionImpl = (TouchMonitor.TouchSessionImpl) obj;
        switch (this.$r8$classId) {
            case 0:
                break;
            default:
                TouchMonitor.TouchSessionImpl.m884$$Nest$monRemoved(touchSessionImpl);
                return;
        }
        while (touchSessionImpl != null) {
            TouchMonitor.TouchSessionImpl.m884$$Nest$monRemoved(touchSessionImpl);
            touchSessionImpl = touchSessionImpl.mPredecessor;
        }
    }
}
