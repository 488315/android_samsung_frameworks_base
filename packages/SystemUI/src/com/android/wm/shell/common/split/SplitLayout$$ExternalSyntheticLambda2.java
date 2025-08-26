package com.android.wm.shell.common.split;

import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.animation.Interpolator;
import com.android.wm.shell.common.split.SplitWindowManager;
import com.android.wm.shell.splitscreen.StageCoordinator;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public final /* synthetic */ class SplitLayout$$ExternalSyntheticLambda2 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ SplitLayout$$ExternalSyntheticLambda2(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                ((StageCoordinator.AnonymousClass1) ((SplitWindowManager.ParentContainerCallbacks) obj2)).inflateOnStageRoot((OffscreenTouchZone) obj);
                break;
            default:
                SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) obj2;
                OffscreenTouchZone offscreenTouchZone = (OffscreenTouchZone) obj;
                Interpolator interpolator = SplitLayout.SHRINK_INTERPOLATOR;
                SurfaceControlViewHost surfaceControlViewHost = offscreenTouchZone.mViewHost;
                if (surfaceControlViewHost != null) {
                    surfaceControlViewHost.release();
                }
                SurfaceControl surfaceControl = offscreenTouchZone.mLeash;
                if (surfaceControl != null) {
                    transaction.remove(surfaceControl);
                    offscreenTouchZone.mLeash = null;
                    break;
                }
                break;
        }
    }
}
