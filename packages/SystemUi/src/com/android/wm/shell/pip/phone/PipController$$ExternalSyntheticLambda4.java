package com.android.wm.shell.pip.phone;

import android.graphics.Rect;
import com.android.wm.shell.onehanded.OneHandedController;
import com.android.wm.shell.onehanded.OneHandedTransitionCallback;
import com.android.wm.shell.pip.phone.PipController;
import java.util.ArrayList;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class PipController$$ExternalSyntheticLambda4 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ PipController$$ExternalSyntheticLambda4(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                final PipController pipController = (PipController) this.f$0;
                pipController.getClass();
                ((ArrayList) ((OneHandedController) obj).mDisplayAreaOrganizer.mTransitionCallbacks).add(new OneHandedTransitionCallback() { // from class: com.android.wm.shell.pip.phone.PipController.5
                    @Override // com.android.wm.shell.onehanded.OneHandedTransitionCallback
                    public final void onStartFinished(Rect rect) {
                        PipController.this.mTouchHandler.mPipResizeGestureHandler.mOhmOffset = rect.top;
                    }

                    @Override // com.android.wm.shell.onehanded.OneHandedTransitionCallback
                    public final void onStopFinished(Rect rect) {
                        PipController.this.mTouchHandler.mPipResizeGestureHandler.mOhmOffset = rect.top;
                    }
                });
                break;
            default:
                ((PipController) obj).setPinnedStackAnimationListener(((PipController.IPipImpl) this.f$0).mPipAnimationListener);
                break;
        }
    }
}
