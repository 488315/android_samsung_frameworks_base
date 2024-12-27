package com.android.systemui.ambient.touch;

import com.android.systemui.ambient.touch.TouchMonitor;
import java.util.function.BiConsumer;

public final /* synthetic */ class TouchMonitor$3$$ExternalSyntheticLambda0 implements BiConsumer {
    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        ((TouchHandler) obj).onSessionStart((TouchMonitor.TouchSessionImpl) obj2);
    }
}
