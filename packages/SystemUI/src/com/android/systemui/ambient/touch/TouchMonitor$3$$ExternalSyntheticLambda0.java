package com.android.systemui.ambient.touch;

import com.android.systemui.ambient.touch.TouchMonitor;
import java.util.function.BiConsumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final /* synthetic */ class TouchMonitor$3$$ExternalSyntheticLambda0 implements BiConsumer {
    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        ((TouchHandler) obj).onSessionStart((TouchMonitor.TouchSessionImpl) obj2);
    }
}
