package com.android.wm.shell.pip;

import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceControl;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import java.lang.ref.WeakReference;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class PipTaskOrganizer$$ExternalSyntheticLambda15 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PipTaskOrganizer f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ PipTaskOrganizer$$ExternalSyntheticLambda15(PipTaskOrganizer pipTaskOrganizer, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = pipTaskOrganizer;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                PipTaskOrganizer pipTaskOrganizer = this.f$0;
                Rect rect = (Rect) this.f$1;
                if (pipTaskOrganizer.mPipTransitionState.mState != 5) {
                    pipTaskOrganizer.finishResizeForMenu(rect);
                    pipTaskOrganizer.sendOnPipTransitionFinished(2);
                    break;
                } else {
                    Log.w("PipTaskOrganizer", "onFixedRotationFinished: failed to enter, reason=exiting_pip");
                    break;
                }
            case 1:
                PipTaskOrganizer pipTaskOrganizer2 = this.f$0;
                pipTaskOrganizer2.mMainExecutor.execute((PipTaskOrganizer$1$$ExternalSyntheticLambda2) this.f$1);
                break;
            default:
                PipTaskOrganizer pipTaskOrganizer3 = this.f$0;
                WeakReference weakReference = (WeakReference) this.f$1;
                int i = PipTaskOrganizer.EXTRA_CONTENT_OVERLAY_FADE_OUT_DELAY_MS;
                pipTaskOrganizer3.getClass();
                SurfaceControl surfaceControl = (SurfaceControl) weakReference.get();
                if (surfaceControl != null && surfaceControl.isValid() && surfaceControl == pipTaskOrganizer3.mPipOverlay) {
                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[3]) {
                        ProtoLogImpl_1771455215.w(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -4618800347912193311L, 0, String.valueOf(surfaceControl));
                    }
                    pipTaskOrganizer3.removeContentOverlay(surfaceControl, null);
                    break;
                }
                break;
        }
    }
}
