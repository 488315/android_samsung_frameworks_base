package com.android.p038wm.shell.recents;

import android.util.Slog;
import android.window.PictureInPictureSurfaceTransaction;
import com.android.p038wm.shell.recents.RecentsTransitionHandler;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class RecentsTransitionHandler$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ RecentsTransitionHandler$$ExternalSyntheticLambda2(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                RecentsTransitionHandler recentsTransitionHandler = (RecentsTransitionHandler) this.f$0;
                String str = (String) this.f$1;
                recentsTransitionHandler.getClass();
                Slog.d("RecentsTransitionHandler", "onForceHideAnimationFinished: " + str + ", num_remains=" + recentsTransitionHandler.mForceHidingAnimators.size());
                break;
            default:
                RecentsTransitionHandler.RecentsController recentsController = (RecentsTransitionHandler.RecentsController) this.f$0;
                PictureInPictureSurfaceTransaction pictureInPictureSurfaceTransaction = (PictureInPictureSurfaceTransaction) this.f$1;
                if (recentsController.mFinishCB != null) {
                    recentsController.mPipTransaction = pictureInPictureSurfaceTransaction;
                    break;
                }
                break;
        }
    }
}
