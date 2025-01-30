package com.android.systemui.dreams.touch;

import com.android.systemui.dreams.touch.DreamOverlayTouchMonitor;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class DreamOverlayTouchMonitor$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ DreamOverlayTouchMonitor$$ExternalSyntheticLambda1(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                for (DreamOverlayTouchMonitor.TouchSessionImpl touchSessionImpl = (DreamOverlayTouchMonitor.TouchSessionImpl) obj; touchSessionImpl != null; touchSessionImpl = touchSessionImpl.mPredecessor) {
                    DreamOverlayTouchMonitor.TouchSessionImpl.m1532$$Nest$monRemoved(touchSessionImpl);
                }
                break;
            default:
                DreamOverlayTouchMonitor.TouchSessionImpl.m1532$$Nest$monRemoved((DreamOverlayTouchMonitor.TouchSessionImpl) obj);
                break;
        }
    }
}
