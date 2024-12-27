package com.android.server.accessibility.magnification;

import android.view.MagnificationSpec;
import android.view.accessibility.MagnificationAnimationCallback;

import com.android.internal.util.function.TriConsumer;

import java.util.function.Consumer;

public final /* synthetic */
class FullScreenMagnificationController$DisplayMagnification$$ExternalSyntheticLambda2
        implements TriConsumer {
    public final /* synthetic */ int $r8$classId;

    public final void accept(Object obj, Object obj2, Object obj3) {
        switch (this.$r8$classId) {
            case 0:
                ((FullScreenMagnificationController.SpecAnimationBridge) obj)
                        .updateSentSpecMainThread(
                                (MagnificationSpec) obj2, (MagnificationAnimationCallback) obj3);
                return;
            default:
                FullScreenMagnificationController fullScreenMagnificationController =
                        (FullScreenMagnificationController) obj;
                final int intValue = ((Integer) obj2).intValue();
                final boolean booleanValue = ((Boolean) obj3).booleanValue();
                synchronized (fullScreenMagnificationController.mLock) {
                    fullScreenMagnificationController.mMagnificationInfoChangedCallbacks.forEach(
                            new Consumer() { // from class:
                                             // com.android.server.accessibility.magnification.FullScreenMagnificationController$$ExternalSyntheticLambda5
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj4) {
                                    ((FullScreenMagnificationController
                                                            .MagnificationInfoChangedCallback)
                                                    obj4)
                                            .onImeWindowVisibilityChanged(intValue, booleanValue);
                                }
                            });
                }
                return;
        }
    }
}
