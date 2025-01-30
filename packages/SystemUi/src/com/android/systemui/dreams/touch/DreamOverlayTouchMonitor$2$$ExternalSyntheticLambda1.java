package com.android.systemui.dreams.touch;

import com.android.systemui.dreams.touch.DreamOverlayTouchMonitor;
import java.util.Collection;
import java.util.function.Function;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class DreamOverlayTouchMonitor$2$$ExternalSyntheticLambda1 implements Function {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ DreamOverlayTouchMonitor$2$$ExternalSyntheticLambda1(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return ((DreamOverlayTouchMonitor.TouchSessionImpl) obj).mEventListeners;
            case 1:
                return ((Collection) obj).stream();
            case 2:
                return ((DreamOverlayTouchMonitor.TouchSessionImpl) obj).mGestureListeners;
            default:
                return ((Collection) obj).stream();
        }
    }
}
