package com.android.wm.shell.transition;

import android.os.IBinder;
import android.view.SurfaceControl;
import com.android.wm.shell.transition.Transitions;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class DefaultTransitionHandler$$ExternalSyntheticLambda5 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DefaultTransitionHandler f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ Object f$2;
    public final /* synthetic */ Object f$3;

    public /* synthetic */ DefaultTransitionHandler$$ExternalSyntheticLambda5(DefaultTransitionHandler defaultTransitionHandler, Object obj, Object obj2, Object obj3, int i) {
        this.$r8$classId = i;
        this.f$0 = defaultTransitionHandler;
        this.f$1 = obj;
        this.f$2 = obj2;
        this.f$3 = obj3;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                DefaultTransitionHandler defaultTransitionHandler = this.f$0;
                WindowThumbnail windowThumbnail = (WindowThumbnail) this.f$1;
                SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) this.f$2;
                Runnable runnable = (Runnable) this.f$3;
                defaultTransitionHandler.getClass();
                SurfaceControl surfaceControl = windowThumbnail.mSurfaceControl;
                if (surfaceControl != null) {
                    transaction.remove(surfaceControl);
                    transaction.apply();
                    windowThumbnail.mSurfaceControl.release();
                    windowThumbnail.mSurfaceControl = null;
                }
                defaultTransitionHandler.mTransactionPool.release(transaction);
                runnable.run();
                break;
            case 1:
                DefaultTransitionHandler defaultTransitionHandler2 = this.f$0;
                WindowThumbnail windowThumbnail2 = (WindowThumbnail) this.f$1;
                SurfaceControl.Transaction transaction2 = (SurfaceControl.Transaction) this.f$2;
                Runnable runnable2 = (Runnable) this.f$3;
                defaultTransitionHandler2.getClass();
                SurfaceControl surfaceControl2 = windowThumbnail2.mSurfaceControl;
                if (surfaceControl2 != null) {
                    transaction2.remove(surfaceControl2);
                    transaction2.apply();
                    windowThumbnail2.mSurfaceControl.release();
                    windowThumbnail2.mSurfaceControl = null;
                }
                defaultTransitionHandler2.mTransactionPool.release(transaction2);
                runnable2.run();
                break;
            default:
                DefaultTransitionHandler defaultTransitionHandler3 = this.f$0;
                ArrayList arrayList = (ArrayList) this.f$1;
                IBinder iBinder = (IBinder) this.f$2;
                Transitions.TransitionFinishCallback transitionFinishCallback = (Transitions.TransitionFinishCallback) this.f$3;
                defaultTransitionHandler3.getClass();
                if (arrayList.isEmpty()) {
                    defaultTransitionHandler3.mAnimations.remove(iBinder);
                    transitionFinishCallback.onTransitionFinished(null, null);
                    break;
                }
                break;
        }
    }
}
