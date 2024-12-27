package com.android.systemui.keyguard;

import android.content.Context;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.IRemoteAnimationRunner;
import android.view.RemoteAnimationTarget;
import android.view.ViewGroup;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.policy.ScreenDecorationsUtils;
import com.android.keyguard.KeyguardViewController;
import com.android.systemui.R;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.animation.TransitionAnimator;
import com.android.systemui.keyguard.data.repository.ShowWhenLockedActivityInfo;
import com.android.systemui.keyguard.domain.interactor.KeyguardOcclusionInteractor;
import com.android.systemui.keyguard.ui.viewmodel.DreamingToLockscreenTransitionViewModel;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import dagger.Lazy;
import java.util.concurrent.Executor;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class WindowManagerOcclusionManager {
    public static final Companion Companion = new Companion(null);
    public static final String TAG = "WindowManagerOcclusion";
    public final ActivityTransitionAnimator activityTransitionAnimator;
    public final Context context;
    public final InteractionJankMonitor interactionJankMonitor;
    public final KeyguardOcclusionInteractor keyguardOcclusionInteractor;
    public final Lazy keyguardViewController;
    public final WindowManagerOcclusionManager$occludeAnimationController$1 occludeAnimationController;
    public IRemoteAnimationFinishedCallback occludeAnimationFinishedCallback;
    public final WindowManagerOcclusionManager$occludeAnimationRunner$1 occludeAnimationRunner;
    public final int powerButtonY;
    public IRemoteAnimationFinishedCallback unoccludeAnimationFinishedCallback;
    public final WindowManagerOcclusionManager$unoccludeAnimationRunner$1 unoccludeAnimationRunner;
    public final float windowCornerRadius;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: Type inference failed for: r1v6, types: [com.android.systemui.keyguard.WindowManagerOcclusionManager$occludeAnimationController$1] */
    public WindowManagerOcclusionManager(KeyguardOcclusionInteractor keyguardOcclusionInteractor, ActivityTransitionAnimator activityTransitionAnimator, Lazy lazy, PowerInteractor powerInteractor, Context context, InteractionJankMonitor interactionJankMonitor, Executor executor, DreamingToLockscreenTransitionViewModel dreamingToLockscreenTransitionViewModel, KeyguardOcclusionInteractor keyguardOcclusionInteractor2) {
        this.keyguardOcclusionInteractor = keyguardOcclusionInteractor;
        this.activityTransitionAnimator = activityTransitionAnimator;
        this.keyguardViewController = lazy;
        this.context = context;
        this.interactionJankMonitor = interactionJankMonitor;
        this.powerButtonY = context.getResources().getDimensionPixelSize(R.dimen.physical_power_button_center_screen_location_y);
        this.windowCornerRadius = ScreenDecorationsUtils.getWindowCornerRadius(context);
        new IRemoteAnimationRunner.Stub() { // from class: com.android.systemui.keyguard.WindowManagerOcclusionManager$occludeAnimationRunner$1
            public final void onAnimationCancelled() {
                WindowManagerOcclusionManager.Companion.getClass();
                android.util.Log.d(WindowManagerOcclusionManager.TAG, "occludeAnimationRunner#onAnimationCancelled");
            }

            public final void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, final IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
                WindowManagerOcclusionManager.Companion.getClass();
                android.util.Log.d(WindowManagerOcclusionManager.TAG, "occludeAnimationRunner#onAnimationStart");
                WindowManagerOcclusionManager windowManagerOcclusionManager = WindowManagerOcclusionManager.this;
                final WindowManagerOcclusionManager windowManagerOcclusionManager2 = WindowManagerOcclusionManager.this;
                windowManagerOcclusionManager.occludeAnimationFinishedCallback = new IRemoteAnimationFinishedCallback.Stub() { // from class: com.android.systemui.keyguard.WindowManagerOcclusionManager$occludeAnimationRunner$1$onAnimationStart$1
                    public final void onAnimationFinished() {
                        IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback2 = iRemoteAnimationFinishedCallback;
                        if (iRemoteAnimationFinishedCallback2 != null) {
                            iRemoteAnimationFinishedCallback2.onAnimationFinished();
                        }
                        windowManagerOcclusionManager2.occludeAnimationFinishedCallback = null;
                    }
                };
                KeyguardOcclusionInteractor keyguardOcclusionInteractor3 = WindowManagerOcclusionManager.this.keyguardOcclusionInteractor;
                RemoteAnimationTarget remoteAnimationTarget = (RemoteAnimationTarget) ArraysKt___ArraysKt.firstOrNull(remoteAnimationTargetArr);
                keyguardOcclusionInteractor3.repository.showWhenLockedActivityInfo.updateState(null, new ShowWhenLockedActivityInfo(true, remoteAnimationTarget != null ? remoteAnimationTarget.taskInfo : null));
                WindowManagerOcclusionManager windowManagerOcclusionManager3 = WindowManagerOcclusionManager.this;
                windowManagerOcclusionManager3.activityTransitionAnimator.createRunner(windowManagerOcclusionManager3.occludeAnimationController).onAnimationStart(i, remoteAnimationTargetArr, remoteAnimationTargetArr2, remoteAnimationTargetArr3, WindowManagerOcclusionManager.this.occludeAnimationFinishedCallback);
            }
        };
        new WindowManagerOcclusionManager$unoccludeAnimationRunner$1(this, executor);
        this.occludeAnimationController = new ActivityTransitionAnimator.Controller() { // from class: com.android.systemui.keyguard.WindowManagerOcclusionManager$occludeAnimationController$1
            public final boolean isLaunching = true;

            @Override // com.android.systemui.animation.TransitionAnimator.Controller
            public final TransitionAnimator.State createAnimatorState() {
                int width = getTransitionContainer().getWidth();
                int height = getTransitionContainer().getHeight();
                WindowManagerOcclusionManager windowManagerOcclusionManager = WindowManagerOcclusionManager.this;
                if (((Boolean) windowManagerOcclusionManager.keyguardOcclusionInteractor.showWhenLockedActivityLaunchedFromPowerGesture.$$delegate_0.getValue()).booleanValue()) {
                    float f = width;
                    float f2 = windowManagerOcclusionManager.powerButtonY;
                    float f3 = (height / 3.0f) / 2.0f;
                    float f4 = windowManagerOcclusionManager.windowCornerRadius;
                    return new TransitionAnimator.State((int) (f2 - f3), (int) (f2 + f3), (int) (f - (f / 3.0f)), width, f4, f4);
                }
                float f5 = height;
                float f6 = f5 / 2.0f;
                float f7 = width;
                float f8 = f7 / 2.0f;
                float f9 = f5 - f6;
                int i = ((int) f9) / 2;
                float f10 = 2;
                float f11 = f7 - f8;
                float f12 = windowManagerOcclusionManager.windowCornerRadius;
                return new TransitionAnimator.State(i, (int) ((f9 / f10) + f6), ((int) f11) / 2, (int) ((f11 / f10) + f8), f12, f12);
            }

            @Override // com.android.systemui.animation.TransitionAnimator.Controller
            public final ViewGroup getTransitionContainer() {
                return (ViewGroup) ((KeyguardViewController) WindowManagerOcclusionManager.this.keyguardViewController.get()).getViewRootImpl().getView();
            }

            @Override // com.android.systemui.animation.TransitionAnimator.Controller
            public final boolean isLaunching() {
                return this.isLaunching;
            }

            @Override // com.android.systemui.animation.TransitionAnimator.Controller
            public final void setTransitionContainer(ViewGroup viewGroup) {
            }
        };
    }

    public static /* synthetic */ void getOccludeAnimationController$annotations() {
    }
}
