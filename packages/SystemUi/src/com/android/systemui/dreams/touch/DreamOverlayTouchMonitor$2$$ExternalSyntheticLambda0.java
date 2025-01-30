package com.android.systemui.dreams.touch;

import com.android.systemui.dreams.touch.DreamTouchHandler;
import java.util.function.BiConsumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class DreamOverlayTouchMonitor$2$$ExternalSyntheticLambda0 implements BiConsumer {
    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        ((DreamTouchHandler) obj).onSessionStart((DreamTouchHandler.TouchSession) obj2);
    }
}
