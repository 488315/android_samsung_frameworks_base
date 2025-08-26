package com.android.systemui.shade;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.compose.ui.platform.ComposeView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleRegistry;
import com.android.systemui.R;
import com.android.systemui.ambient.touch.TouchMonitor;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.keyguard.shared.model.TransitionState;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.log.core.Logger;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.shade.GlanceableHubContainerController;
import com.android.systemui.shared.animation.UnfoldConstantTranslateAnimator;
import java.util.function.Consumer;
import kotlin.coroutines.EmptyCoroutineContext;

/* loaded from: classes3.dex */
public final /* synthetic */ class NotificationShadeWindowViewController$$ExternalSyntheticLambda6 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NotificationShadeWindowViewController f$0;

    public /* synthetic */ NotificationShadeWindowViewController$$ExternalSyntheticLambda6(NotificationShadeWindowViewController notificationShadeWindowViewController, int i) {
        this.$r8$classId = i;
        this.f$0 = notificationShadeWindowViewController;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        NotificationShadeWindowViewController notificationShadeWindowViewController = this.f$0;
        switch (i) {
            case 0:
                boolean zBooleanValue = ((Boolean) obj).booleanValue();
                GlanceableHubContainerController glanceableHubContainerController = notificationShadeWindowViewController.mGlanceableHubContainerController;
                if (!zBooleanValue) {
                    glanceableHubContainerController.getClass();
                    RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                    int i2 = SceneContainerFlag.$r8$clinit;
                    View view = glanceableHubContainerController.communalContainerView;
                    LifecycleRegistry lifecycleRegistry = glanceableHubContainerController.lifecycleRegistry;
                    if (view != null) {
                        ((ViewGroup) view.getParent()).removeView(view);
                        lifecycleRegistry.setCurrentState(Lifecycle.State.CREATED);
                        glanceableHubContainerController.communalContainerView = null;
                    }
                    GlanceableHubContainerController.CommunalWrapper communalWrapper = glanceableHubContainerController.communalContainerWrapper;
                    if (communalWrapper != null) {
                        ((ViewGroup) communalWrapper.getParent()).removeView(communalWrapper);
                        glanceableHubContainerController.communalContainerWrapper = null;
                    }
                    lifecycleRegistry.removeObserver(glanceableHubContainerController.touchLifecycleLogger);
                    TouchMonitor touchMonitor = glanceableHubContainerController.touchMonitor;
                    if (touchMonitor != null) {
                        touchMonitor.destroy();
                        glanceableHubContainerController.touchMonitor = null;
                    }
                    Logger.d$default(glanceableHubContainerController.logger, "Hub container disposed", null, 2, null);
                    break;
                } else {
                    NotificationShadeWindowView notificationShadeWindowView = notificationShadeWindowViewController.mView;
                    int iIndexOfChild = notificationShadeWindowView.indexOfChild(notificationShadeWindowView.findViewById(R.id.communal_ui_stub));
                    Context context = notificationShadeWindowView.getContext();
                    glanceableHubContainerController.getClass();
                    ComposeView composeView = new ComposeView(context, null, 0, 6, null);
                    RepeatWhenAttachedKt.repeatWhenAttached(composeView, EmptyCoroutineContext.INSTANCE, new GlanceableHubContainerController$initView$1$1(composeView, glanceableHubContainerController, null));
                    notificationShadeWindowView.addView(glanceableHubContainerController.initView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(composeView), iIndexOfChild);
                    break;
                }
            case 1:
                boolean z = ((TransitionStep) obj).transitionState == TransitionState.RUNNING;
                notificationShadeWindowViewController.mIsOcclusionTransitionRunning = z;
                notificationShadeWindowViewController.mShadeViewController.setIsOcclusionTransitionRunning(z);
                break;
            case 2:
                notificationShadeWindowViewController.setExpandAnimationRunning(((Boolean) obj).booleanValue());
                break;
            default:
                NotificationPanelUnfoldAnimationController notificationPanelUnfoldAnimationController = (NotificationPanelUnfoldAnimationController) obj;
                float dimensionPixelSize = notificationPanelUnfoldAnimationController.context.getResources().getDimensionPixelSize(R.dimen.notification_side_paddings);
                UnfoldConstantTranslateAnimator unfoldConstantTranslateAnimator = (UnfoldConstantTranslateAnimator) notificationPanelUnfoldAnimationController.translateAnimator$delegate.getValue();
                if (unfoldConstantTranslateAnimator.rootView == null) {
                    unfoldConstantTranslateAnimator.progressProvider.addCallback(unfoldConstantTranslateAnimator);
                }
                unfoldConstantTranslateAnimator.rootView = notificationShadeWindowViewController.mView;
                unfoldConstantTranslateAnimator.translationMax = dimensionPixelSize;
                break;
        }
    }
}
