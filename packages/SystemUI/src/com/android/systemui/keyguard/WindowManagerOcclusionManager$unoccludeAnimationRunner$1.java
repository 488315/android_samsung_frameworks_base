package com.android.systemui.keyguard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Matrix;
import android.os.RemoteException;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.IRemoteAnimationRunner;
import android.view.RemoteAnimationTarget;
import android.view.SyncRtSurfaceTransactionApplier;
import com.android.app.animation.Interpolators;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.keyguard.KeyguardViewController;
import com.android.systemui.keyguard.WindowManagerOcclusionManager;
import com.android.systemui.keyguard.data.repository.ShowWhenLockedActivityInfo;
import com.android.systemui.keyguard.domain.interactor.KeyguardOcclusionInteractor;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import java.util.concurrent.Executor;
import kotlin.collections.ArraysKt___ArraysKt;

public final class WindowManagerOcclusionManager$unoccludeAnimationRunner$1 extends IRemoteAnimationRunner.Stub {
    public final /* synthetic */ Executor $executor;
    public final /* synthetic */ WindowManagerOcclusionManager this$0;
    public ValueAnimator unoccludeAnimator;
    public final Matrix unoccludeMatrix = new Matrix();

    public WindowManagerOcclusionManager$unoccludeAnimationRunner$1(WindowManagerOcclusionManager windowManagerOcclusionManager, Executor executor) {
        this.this$0 = windowManagerOcclusionManager;
        this.$executor = executor;
    }

    public final void onAnimationCancelled() {
        WindowManagerOcclusionManager.Companion companion = WindowManagerOcclusionManager.Companion;
        companion.getClass();
        String str = WindowManagerOcclusionManager.TAG;
        android.util.Log.d(str, "unoccludeAnimationRunner#onAnimationCancelled");
        this.this$0.context.getMainExecutor().execute(new Runnable() { // from class: com.android.systemui.keyguard.WindowManagerOcclusionManager$unoccludeAnimationRunner$1$onAnimationCancelled$1
            @Override // java.lang.Runnable
            public final void run() {
                ValueAnimator valueAnimator = WindowManagerOcclusionManager$unoccludeAnimationRunner$1.this.unoccludeAnimator;
                if (valueAnimator != null) {
                    valueAnimator.cancel();
                }
            }
        });
        companion.getClass();
        android.util.Log.d(str, "Unocclude animation cancelled.");
        this.this$0.interactionJankMonitor.cancel(64);
    }

    public final void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, final IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
        WindowManagerOcclusionManager.Companion.getClass();
        String str = WindowManagerOcclusionManager.TAG;
        android.util.Log.d(str, "unoccludeAnimationRunner#onAnimationStart");
        WindowManagerOcclusionManager windowManagerOcclusionManager = this.this$0;
        final WindowManagerOcclusionManager windowManagerOcclusionManager2 = this.this$0;
        windowManagerOcclusionManager.unoccludeAnimationFinishedCallback = new IRemoteAnimationFinishedCallback.Stub() { // from class: com.android.systemui.keyguard.WindowManagerOcclusionManager$unoccludeAnimationRunner$1$onAnimationStart$1
            public final void onAnimationFinished() {
                IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback2 = iRemoteAnimationFinishedCallback;
                if (iRemoteAnimationFinishedCallback2 != null) {
                    iRemoteAnimationFinishedCallback2.onAnimationFinished();
                }
                windowManagerOcclusionManager2.unoccludeAnimationFinishedCallback = null;
            }
        };
        KeyguardOcclusionInteractor keyguardOcclusionInteractor = this.this$0.keyguardOcclusionInteractor;
        RemoteAnimationTarget remoteAnimationTarget = (RemoteAnimationTarget) ArraysKt___ArraysKt.firstOrNull(remoteAnimationTargetArr);
        keyguardOcclusionInteractor.repository.showWhenLockedActivityInfo.updateState(null, new ShowWhenLockedActivityInfo(false, remoteAnimationTarget != null ? remoteAnimationTarget.taskInfo : null));
        WindowManagerOcclusionManager windowManagerOcclusionManager3 = this.this$0;
        windowManagerOcclusionManager3.interactionJankMonitor.begin(InteractionJankMonitor.Configuration.Builder.withView(64, ((KeyguardViewController) windowManagerOcclusionManager3.keyguardViewController.get()).getViewRootImpl().getView()).setTag("UNOCCLUDE"));
        if (remoteAnimationTargetArr.length == 0) {
            android.util.Log.d(str, "No apps provided to unocclude runner; skipping animation and unoccluding.");
            IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback2 = this.this$0.unoccludeAnimationFinishedCallback;
            if (iRemoteAnimationFinishedCallback2 != null) {
                iRemoteAnimationFinishedCallback2.onAnimationFinished();
                return;
            }
            return;
        }
        final RemoteAnimationTarget remoteAnimationTarget2 = remoteAnimationTargetArr[0];
        final SyncRtSurfaceTransactionApplier syncRtSurfaceTransactionApplier = new SyncRtSurfaceTransactionApplier(((KeyguardViewController) this.this$0.keyguardViewController.get()).getViewRootImpl().getView());
        Executor executor = this.$executor;
        final WindowManagerOcclusionManager windowManagerOcclusionManager4 = this.this$0;
        executor.execute(new Runnable() { // from class: com.android.systemui.keyguard.WindowManagerOcclusionManager$unoccludeAnimationRunner$1$onAnimationStart$2
            @Override // java.lang.Runnable
            public final void run() {
                ValueAnimator valueAnimator = WindowManagerOcclusionManager$unoccludeAnimationRunner$1.this.unoccludeAnimator;
                if (valueAnimator != null) {
                    valueAnimator.cancel();
                }
                WindowManagerOcclusionManager$unoccludeAnimationRunner$1 windowManagerOcclusionManager$unoccludeAnimationRunner$1 = WindowManagerOcclusionManager$unoccludeAnimationRunner$1.this;
                ValueAnimator ofFloat = ValueAnimator.ofFloat(1.0f, 0.0f);
                final RemoteAnimationTarget remoteAnimationTarget3 = remoteAnimationTarget2;
                final WindowManagerOcclusionManager$unoccludeAnimationRunner$1 windowManagerOcclusionManager$unoccludeAnimationRunner$12 = WindowManagerOcclusionManager$unoccludeAnimationRunner$1.this;
                final WindowManagerOcclusionManager windowManagerOcclusionManager5 = windowManagerOcclusionManager4;
                final SyncRtSurfaceTransactionApplier syncRtSurfaceTransactionApplier2 = syncRtSurfaceTransactionApplier;
                ofFloat.setDuration(IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend);
                ofFloat.setInterpolator(Interpolators.TOUCH_RESPONSE);
                ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.keyguard.WindowManagerOcclusionManager$unoccludeAnimationRunner$1$onAnimationStart$2$1$1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                        float floatValue = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                        windowManagerOcclusionManager$unoccludeAnimationRunner$12.unoccludeMatrix.setTranslate(0.0f, (1.0f - floatValue) * remoteAnimationTarget3.screenSpaceBounds.height() * 0.1f);
                        syncRtSurfaceTransactionApplier2.scheduleApply(new SyncRtSurfaceTransactionApplier.SurfaceParams[]{new SyncRtSurfaceTransactionApplier.SurfaceParams.Builder(remoteAnimationTarget3.leash).withAlpha(floatValue).withMatrix(windowManagerOcclusionManager$unoccludeAnimationRunner$12.unoccludeMatrix).withCornerRadius(windowManagerOcclusionManager5.windowCornerRadius).build()});
                    }
                });
                ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.keyguard.WindowManagerOcclusionManager$unoccludeAnimationRunner$1$onAnimationStart$2$1$2
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        try {
                            IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback3 = WindowManagerOcclusionManager.this.unoccludeAnimationFinishedCallback;
                            if (iRemoteAnimationFinishedCallback3 != null) {
                                iRemoteAnimationFinishedCallback3.onAnimationFinished();
                            }
                            windowManagerOcclusionManager$unoccludeAnimationRunner$12.unoccludeAnimator = null;
                            WindowManagerOcclusionManager.this.interactionJankMonitor.end(64);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                });
                ofFloat.start();
                windowManagerOcclusionManager$unoccludeAnimationRunner$1.unoccludeAnimator = ofFloat;
            }
        });
    }
}
