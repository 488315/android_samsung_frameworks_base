package com.android.systemui.ambient.touch;

import com.android.systemui.ambient.touch.TouchMonitor;
import java.util.Collection;
import java.util.function.Function;

/* loaded from: classes.dex */
public final /* synthetic */ class TouchMonitor$$ExternalSyntheticLambda7 implements Function {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return Integer.valueOf(((TouchMonitor.TouchSessionImpl) obj).hashCode());
            case 1:
                return ((Integer) obj).toString();
            case 2:
                return ((TouchMonitor.TouchSessionImpl) obj).mEventListeners;
            case 3:
                return ((Collection) obj).stream();
            default:
                return ((TouchMonitor.TouchSessionImpl) obj).mGestureListeners;
        }
    }
}
