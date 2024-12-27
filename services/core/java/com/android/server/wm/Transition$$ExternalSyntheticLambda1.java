package com.android.server.wm;

import android.os.Bundle;
import android.os.IRemoteCallback;
import android.os.RemoteException;
import android.util.Slog;
import android.view.SurfaceControl;

import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class Transition$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ Transition$$ExternalSyntheticLambda1(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        WindowContainer windowContainer;
        switch (this.$r8$classId) {
            case 0:
                Transition.ChangeInfo changeInfo = (Transition.ChangeInfo) obj;
                boolean z = ChangeTransitionController.DISABLE_LEGACY_RESIZE_TRANSITION;
                try {
                    if (changeInfo.mChangeLeash != null
                            && (windowContainer = changeInfo.mContainer) != null
                            && windowContainer.getParent() != null) {
                        SurfaceControl.Transaction transaction =
                                (SurfaceControl.Transaction)
                                        windowContainer.mWmService.mTransactionFactory.get();
                        SurfaceControl leashSurface =
                                Transition.getLeashSurface(null, windowContainer);
                        SurfaceControl surfaceControl =
                                windowContainer.asDisplayContent() != null
                                        ? windowContainer.getSurfaceControl()
                                        : windowContainer.getParent().getSurfaceControl();
                        if (leashSurface != null && surfaceControl != null) {
                            transaction.reparent(leashSurface, surfaceControl);
                            transaction.setLayer(leashSurface, windowContainer.getLastLayer());
                            transaction.setCornerRadius(
                                    leashSurface, FullScreenMagnificationGestureHandler.MAX_SCALE);
                            transaction.setShadowRadius(
                                    leashSurface, FullScreenMagnificationGestureHandler.MAX_SCALE);
                            transaction.setAlpha(leashSurface, 1.0f);
                            transaction.apply();
                            transaction.close();
                            Slog.d(
                                    "ChangeTransitionController",
                                    "restoreFromChangeLeash. cause wc is not target for"
                                        + " transition.");
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
                break;
            default:
                try {
                    ((IRemoteCallback) obj).sendResult((Bundle) null);
                    break;
                } catch (RemoteException unused) {
                    return;
                }
        }
    }
}
