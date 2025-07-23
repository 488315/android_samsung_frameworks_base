package com.android.wm.shell.pip.phone;

import android.content.ComponentName;
import android.graphics.Rect;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.wm.shell.onehanded.OneHandedController;
import com.android.wm.shell.onehanded.OneHandedTransitionCallback;
import com.android.wm.shell.pip.PipTaskOrganizer;
import com.android.wm.shell.pip.PipTransitionState;
import com.android.wm.shell.pip.phone.PipController;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import java.util.ArrayList;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class PipController$$ExternalSyntheticLambda6 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ PipController$$ExternalSyntheticLambda6(int i, ComponentName componentName) {
        this.$r8$classId = 2;
        this.f$0 = componentName;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                final PipController pipController = (PipController) obj2;
                int i2 = PipController.$r8$clinit;
                ((ArrayList) ((OneHandedController) obj).mDisplayAreaOrganizer.mTransitionCallbacks).add(new OneHandedTransitionCallback() { // from class: com.android.wm.shell.pip.phone.PipController.5
                    public AnonymousClass5() {
                    }

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
            case 1:
                ((PipController) obj).setPinnedStackAnimationListener(((PipController.IPipImpl) obj2).mPipAnimationListener);
                break;
            default:
                ComponentName componentName = (ComponentName) obj2;
                int i3 = PipController.IPipImpl.$r8$clinit;
                PipTaskOrganizer pipTaskOrganizer = ((PipController) obj).mPipTaskOrganizer;
                PipTransitionState pipTransitionState = pipTaskOrganizer.mPipTransitionState;
                if (pipTransitionState.mInSwipePipToHomeTransition) {
                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[0]) {
                        ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -308783681684358284L, 0, String.valueOf(componentName));
                    }
                    pipTaskOrganizer.mPipTransitionController.sendOnPipTransitionCancelled$1(2);
                    pipTransitionState.mInSwipePipToHomeTransition = false;
                    pipTaskOrganizer.mPictureInPictureParams = null;
                    pipTransitionState.setTransitionState(0);
                    break;
                }
                break;
        }
    }

    public /* synthetic */ PipController$$ExternalSyntheticLambda6(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }
}
