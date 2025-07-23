package com.android.wm.shell.transition;

import android.view.SurfaceControl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class DefaultTransitionHandler$$ExternalSyntheticLambda6 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DefaultTransitionHandler f$0;
    public final /* synthetic */ WindowThumbnail f$1;
    public final /* synthetic */ SurfaceControl.Transaction f$2;
    public final /* synthetic */ DefaultTransitionHandler$$ExternalSyntheticLambda2 f$3;

    public /* synthetic */ DefaultTransitionHandler$$ExternalSyntheticLambda6(DefaultTransitionHandler defaultTransitionHandler, WindowThumbnail windowThumbnail, SurfaceControl.Transaction transaction, DefaultTransitionHandler$$ExternalSyntheticLambda2 defaultTransitionHandler$$ExternalSyntheticLambda2, int i) {
        this.$r8$classId = i;
        this.f$0 = defaultTransitionHandler;
        this.f$1 = windowThumbnail;
        this.f$2 = transaction;
        this.f$3 = defaultTransitionHandler$$ExternalSyntheticLambda2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                DefaultTransitionHandler defaultTransitionHandler = this.f$0;
                WindowThumbnail windowThumbnail = this.f$1;
                SurfaceControl.Transaction transaction = this.f$2;
                DefaultTransitionHandler$$ExternalSyntheticLambda2 defaultTransitionHandler$$ExternalSyntheticLambda2 = this.f$3;
                defaultTransitionHandler.getClass();
                SurfaceControl surfaceControl = windowThumbnail.mSurfaceControl;
                if (surfaceControl != null) {
                    transaction.remove(surfaceControl);
                    transaction.apply();
                    windowThumbnail.mSurfaceControl.release();
                    windowThumbnail.mSurfaceControl = null;
                }
                defaultTransitionHandler.mTransactionPool.release(transaction);
                defaultTransitionHandler$$ExternalSyntheticLambda2.run();
                break;
            default:
                DefaultTransitionHandler defaultTransitionHandler2 = this.f$0;
                WindowThumbnail windowThumbnail2 = this.f$1;
                SurfaceControl.Transaction transaction2 = this.f$2;
                DefaultTransitionHandler$$ExternalSyntheticLambda2 defaultTransitionHandler$$ExternalSyntheticLambda22 = this.f$3;
                defaultTransitionHandler2.getClass();
                SurfaceControl surfaceControl2 = windowThumbnail2.mSurfaceControl;
                if (surfaceControl2 != null) {
                    transaction2.remove(surfaceControl2);
                    transaction2.apply();
                    windowThumbnail2.mSurfaceControl.release();
                    windowThumbnail2.mSurfaceControl = null;
                }
                defaultTransitionHandler2.mTransactionPool.release(transaction2);
                defaultTransitionHandler$$ExternalSyntheticLambda22.run();
                break;
        }
    }
}
