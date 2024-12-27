package com.android.systemui.ambient.touch;

import com.android.systemui.ambient.touch.TouchMonitor;
import java.util.Collection;
import java.util.function.Function;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class TouchMonitor$3$$ExternalSyntheticLambda1 implements Function {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return ((TouchMonitor.TouchSessionImpl) obj).mEventListeners;
            case 1:
                return ((Collection) obj).stream();
            default:
                return ((TouchMonitor.TouchSessionImpl) obj).mGestureListeners;
        }
    }
}
