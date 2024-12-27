package com.android.server.wm;

import android.graphics.Point;
import android.view.SurfaceControl;
import com.android.server.am.ActivityManagerService$$ExternalSyntheticOutline0;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class WindowState$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ WindowState f$0;

    public /* synthetic */ WindowState$$ExternalSyntheticLambda0(WindowState windowState, int i) {
        this.$r8$classId = i;
        this.f$0 = windowState;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        WindowState windowState = this.f$0;
        SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) obj;
        switch (i) {
            case 0:
                SeamlessRotator seamlessRotator = windowState.mPendingSeamlessRotate;
                if (seamlessRotator != null) {
                    seamlessRotator.finish(transaction, windowState);
                    windowState.mPendingSeamlessRotate = null;
                    windowState.getDisplayContent().mDisplayRotation.markForSeamlessRotation(windowState, false);
                    InsetsSourceProvider insetsSourceProvider = windowState.mControllableInsetProvider;
                    if (insetsSourceProvider != null) {
                        insetsSourceProvider.mSeamlessRotating = false;
                    }
                }
                windowState.updateSurfacePosition(transaction);
                break;
            case 1:
                SurfaceControl surfaceControl = windowState.mSurfaceControl;
                if (surfaceControl != null && surfaceControl.isValid() && !windowState.mSurfaceAnimator.hasLeash()) {
                    SurfaceControl surfaceControl2 = windowState.mSurfaceControl;
                    Point point = windowState.mSurfacePosition;
                    transaction.setPosition(surfaceControl2, point.x, point.y);
                    break;
                }
                break;
            default:
                windowState.getClass();
                StringBuilder sb = new StringBuilder("Clear waiting to handle resizing w=");
                sb.append(windowState);
                sb.append(", caller=");
                ActivityManagerService$$ExternalSyntheticOutline0.m(5, sb, "WindowManager");
                windowState.mWaitToHandleResizing = false;
                break;
        }
    }
}
