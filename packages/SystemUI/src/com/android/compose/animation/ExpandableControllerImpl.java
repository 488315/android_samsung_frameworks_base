package com.android.compose.animation;

import android.content.ComponentName;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.view.ViewRootImpl;
import android.window.WindowAnimationState;
import androidx.compose.foundation.BorderStroke;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.LayoutDirection;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.animation.ComposableControllerFactory;
import com.android.systemui.animation.DialogCuj;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.animation.Expandable;
import com.android.systemui.animation.TransitionAnimator;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class ExpandableControllerImpl {
    public ExpandableControllerImpl$activityController$1 activityControllerForDisposal;
    public final MutableState animatorState$delegate;
    public final BorderStroke borderStroke;
    public final MutableState boundsInComposeViewRoot$delegate;
    public final Function0 color;
    public final View composeViewRoot;
    public final long contentColor;
    public final MutableState currentComposeViewInOverlay$delegate;
    public DrawExpandableInOverlayNode currentNodeInOverlay;
    public final Density density;
    public final ExpandableControllerImpl$expandable$1 expandable;
    public final State isAnimating$delegate;
    public final Function0 isComposed;
    public final MutableState isDialogShowing$delegate;
    public final LayoutDirection layoutDirection;
    public final MutableState overlay$delegate;
    public final Shape shape;
    public final ComposableControllerFactory transitionControllerFactory;

    public /* synthetic */ ExpandableControllerImpl(Function0 function0, long j, Shape shape, BorderStroke borderStroke, View view, Density density, ComposableControllerFactory composableControllerFactory, LayoutDirection layoutDirection, Function0 function02, DefaultConstructorMarker defaultConstructorMarker) {
        this(function0, j, shape, borderStroke, view, density, composableControllerFactory, layoutDirection, function02);
    }

    /* JADX WARN: Type inference failed for: r1v6, types: [com.android.compose.animation.ExpandableControllerImpl$expandable$1] */
    private ExpandableControllerImpl(Function0 function0, long j, Shape shape, BorderStroke borderStroke, View view, Density density, ComposableControllerFactory composableControllerFactory, LayoutDirection layoutDirection, Function0 function02) {
        this.color = function0;
        this.contentColor = j;
        this.shape = shape;
        this.borderStroke = borderStroke;
        this.composeViewRoot = view;
        this.density = density;
        this.transitionControllerFactory = composableControllerFactory;
        this.layoutDirection = layoutDirection;
        this.isComposed = function02;
        this.animatorState$delegate = SnapshotStateKt.mutableStateOf$default(null);
        this.isDialogShowing$delegate = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE);
        this.overlay$delegate = SnapshotStateKt.mutableStateOf$default(null);
        this.currentComposeViewInOverlay$delegate = SnapshotStateKt.mutableStateOf$default(null);
        Rect.Companion.getClass();
        this.boundsInComposeViewRoot$delegate = SnapshotStateKt.mutableStateOf$default(Rect.Zero);
        this.expandable = new Expandable() { // from class: com.android.compose.animation.ExpandableControllerImpl$expandable$1
            @Override // com.android.systemui.animation.Expandable
            public final ActivityTransitionAnimator.Controller activityTransitionController(Integer num, ActivityTransitionAnimator.TransitionCookie transitionCookie, ComponentName componentName, Integer num2, boolean z) {
                ExpandableControllerImpl expandableControllerImpl = this.this$0;
                if (!((Boolean) expandableControllerImpl.isComposed.invoke()).booleanValue()) {
                    return null;
                }
                ExpandableControllerImpl$activityController$1 expandableControllerImpl$activityController$1 = new ExpandableControllerImpl$activityController$1(new ExpandableControllerImpl$transitionController$1(expandableControllerImpl), transitionCookie, componentName, num, num2, expandableControllerImpl);
                TransitionAnimator.Companion.getClass();
                if (z) {
                    expandableControllerImpl.activityControllerForDisposal = expandableControllerImpl$activityController$1;
                }
                return expandableControllerImpl$activityController$1;
            }

            @Override // com.android.systemui.animation.Expandable
            public final DialogTransitionAnimator.Controller dialogTransitionController(DialogCuj dialogCuj) {
                ExpandableControllerImpl expandableControllerImpl = this.this$0;
                if (!((Boolean) expandableControllerImpl.isComposed.invoke()).booleanValue()) {
                    return null;
                }
                expandableControllerImpl.getClass();
                return new DialogTransitionAnimator.Controller(dialogCuj) { // from class: com.android.compose.animation.ExpandableControllerImpl$dialogController$1
                    public final /* synthetic */ DialogCuj $cuj;
                    public final DialogCuj cuj;
                    public final ExpandableControllerImpl sourceIdentity;
                    public final ViewRootImpl viewRoot;

                    {
                        this.$cuj = dialogCuj;
                        this.viewRoot = this.this$0.composeViewRoot.getViewRootImpl();
                        this.sourceIdentity = this.this$0;
                        this.cuj = dialogCuj;
                    }

                    @Override // com.android.systemui.animation.DialogTransitionAnimator.Controller
                    public final TransitionAnimator.Controller createExitController() {
                        final ExpandableControllerImpl expandableControllerImpl2 = this.this$0;
                        expandableControllerImpl2.getClass();
                        final ExpandableControllerImpl$transitionController$1 expandableControllerImpl$transitionController$1 = new ExpandableControllerImpl$transitionController$1(expandableControllerImpl2);
                        return new TransitionAnimator.Controller(expandableControllerImpl2) { // from class: com.android.compose.animation.ExpandableControllerImpl$dialogController$1$createExitController$1
                            public final /* synthetic */ TransitionAnimator.Controller $$delegate_0;
                            public final /* synthetic */ ExpandableControllerImpl this$0;

                            {
                                this.this$0 = expandableControllerImpl2;
                                this.$$delegate_0 = this.$delegate;
                            }

                            @Override // com.android.systemui.animation.TransitionAnimator.Controller
                            public final TransitionAnimator.State createAnimatorState() {
                                return this.$$delegate_0.createAnimatorState();
                            }

                            @Override // com.android.systemui.animation.TransitionAnimator.Controller
                            public final View getOpeningWindowSyncView() {
                                return this.$$delegate_0.getOpeningWindowSyncView();
                            }

                            @Override // com.android.systemui.animation.TransitionAnimator.Controller
                            public final ViewGroup getTransitionContainer() {
                                return this.$$delegate_0.getTransitionContainer();
                            }

                            @Override // com.android.systemui.animation.TransitionAnimator.Controller
                            public final WindowAnimationState getWindowAnimatorState() {
                                return this.$$delegate_0.getWindowAnimatorState();
                            }

                            @Override // com.android.systemui.animation.TransitionAnimator.Controller
                            public final boolean isLaunching() {
                                return this.$$delegate_0.isLaunching();
                            }

                            @Override // com.android.systemui.animation.TransitionAnimator.Controller
                            public final void onTransitionAnimationEnd(boolean z) {
                                this.$delegate.onTransitionAnimationEnd(z);
                                ((SnapshotMutableStateImpl) this.this$0.isDialogShowing$delegate).setValue(Boolean.FALSE);
                            }

                            @Override // com.android.systemui.animation.TransitionAnimator.Controller
                            public final void onTransitionAnimationProgress(TransitionAnimator.State state, float f, float f2) {
                                this.$$delegate_0.onTransitionAnimationProgress(state, f, f2);
                            }

                            @Override // com.android.systemui.animation.TransitionAnimator.Controller
                            public final void onTransitionAnimationStart(boolean z) {
                                this.$$delegate_0.onTransitionAnimationStart(z);
                            }

                            @Override // com.android.systemui.animation.TransitionAnimator.Controller
                            public final void setTransitionContainer(ViewGroup viewGroup) {
                                this.$$delegate_0.setTransitionContainer(viewGroup);
                            }
                        };
                    }

                    @Override // com.android.systemui.animation.DialogTransitionAnimator.Controller
                    public final TransitionAnimator.Controller createTransitionController() {
                        final ExpandableControllerImpl expandableControllerImpl2 = this.this$0;
                        expandableControllerImpl2.getClass();
                        final ExpandableControllerImpl$transitionController$1 expandableControllerImpl$transitionController$1 = new ExpandableControllerImpl$transitionController$1(expandableControllerImpl2);
                        return new TransitionAnimator.Controller(expandableControllerImpl2) { // from class: com.android.compose.animation.ExpandableControllerImpl$dialogController$1$createTransitionController$1
                            public final /* synthetic */ TransitionAnimator.Controller $$delegate_0;
                            public final /* synthetic */ ExpandableControllerImpl this$0;

                            {
                                this.this$0 = expandableControllerImpl2;
                                this.$$delegate_0 = this.$delegate;
                            }

                            @Override // com.android.systemui.animation.TransitionAnimator.Controller
                            public final TransitionAnimator.State createAnimatorState() {
                                return this.$$delegate_0.createAnimatorState();
                            }

                            @Override // com.android.systemui.animation.TransitionAnimator.Controller
                            public final View getOpeningWindowSyncView() {
                                return this.$$delegate_0.getOpeningWindowSyncView();
                            }

                            @Override // com.android.systemui.animation.TransitionAnimator.Controller
                            public final ViewGroup getTransitionContainer() {
                                return this.$$delegate_0.getTransitionContainer();
                            }

                            @Override // com.android.systemui.animation.TransitionAnimator.Controller
                            public final WindowAnimationState getWindowAnimatorState() {
                                return this.$$delegate_0.getWindowAnimatorState();
                            }

                            @Override // com.android.systemui.animation.TransitionAnimator.Controller
                            public final boolean isLaunching() {
                                return this.$$delegate_0.isLaunching();
                            }

                            @Override // com.android.systemui.animation.TransitionAnimator.Controller
                            public final void onTransitionAnimationEnd(boolean z) {
                                this.$delegate.onTransitionAnimationEnd(z);
                                ((SnapshotMutableStateImpl) this.this$0.isDialogShowing$delegate).setValue(Boolean.TRUE);
                            }

                            @Override // com.android.systemui.animation.TransitionAnimator.Controller
                            public final void onTransitionAnimationProgress(TransitionAnimator.State state, float f, float f2) {
                                this.$$delegate_0.onTransitionAnimationProgress(state, f, f2);
                            }

                            @Override // com.android.systemui.animation.TransitionAnimator.Controller
                            public final void onTransitionAnimationStart(boolean z) {
                                this.$$delegate_0.onTransitionAnimationStart(z);
                            }

                            @Override // com.android.systemui.animation.TransitionAnimator.Controller
                            public final void setTransitionContainer(ViewGroup viewGroup) {
                                this.$$delegate_0.setTransitionContainer(viewGroup);
                            }
                        };
                    }

                    @Override // com.android.systemui.animation.DialogTransitionAnimator.Controller
                    public final DialogCuj getCuj() {
                        return this.cuj;
                    }

                    @Override // com.android.systemui.animation.DialogTransitionAnimator.Controller
                    public final Object getSourceIdentity() {
                        return this.sourceIdentity;
                    }

                    @Override // com.android.systemui.animation.DialogTransitionAnimator.Controller
                    public final ViewRootImpl getViewRoot() {
                        return this.viewRoot;
                    }

                    @Override // com.android.systemui.animation.DialogTransitionAnimator.Controller
                    public final InteractionJankMonitor.Configuration.Builder jankConfigurationBuilder() {
                        DialogCuj dialogCuj2 = this.$cuj;
                        if (dialogCuj2 == null) {
                            return null;
                        }
                        return InteractionJankMonitor.Configuration.Builder.withView(dialogCuj2.cujType, this.this$0.composeViewRoot);
                    }

                    @Override // com.android.systemui.animation.DialogTransitionAnimator.Controller
                    public final void onExitAnimationCancelled() {
                        ((SnapshotMutableStateImpl) this.this$0.isDialogShowing$delegate).setValue(Boolean.FALSE);
                    }

                    @Override // com.android.systemui.animation.DialogTransitionAnimator.Controller
                    public final boolean shouldAnimateExit() {
                        ExpandableControllerImpl expandableControllerImpl2 = this.this$0;
                        return ((Boolean) expandableControllerImpl2.isComposed.invoke()).booleanValue() && expandableControllerImpl2.composeViewRoot.isAttachedToWindow() && expandableControllerImpl2.composeViewRoot.isShown();
                    }

                    @Override // com.android.systemui.animation.DialogTransitionAnimator.Controller
                    public final void startDrawingInOverlayOf(ViewGroup viewGroup) {
                        ViewGroupOverlay overlay = viewGroup.getOverlay();
                        ExpandableControllerImpl expandableControllerImpl2 = this.this$0;
                        if (Intrinsics.areEqual(overlay, (ViewGroupOverlay) ((SnapshotMutableStateImpl) expandableControllerImpl2.overlay$delegate).getValue())) {
                            return;
                        }
                        ((SnapshotMutableStateImpl) expandableControllerImpl2.overlay$delegate).setValue(overlay);
                    }

                    @Override // com.android.systemui.animation.DialogTransitionAnimator.Controller
                    public final void stopDrawingInOverlay() {
                        ExpandableControllerImpl expandableControllerImpl2 = this.this$0;
                        if (((ViewGroupOverlay) ((SnapshotMutableStateImpl) expandableControllerImpl2.overlay$delegate).getValue()) != null) {
                            ((SnapshotMutableStateImpl) expandableControllerImpl2.overlay$delegate).setValue(null);
                        }
                    }
                };
            }
        };
        this.isAnimating$delegate = SnapshotStateKt.derivedStateOf(new Function0() { // from class: com.android.compose.animation.ExpandableControllerImpl$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ExpandableControllerImpl expandableControllerImpl = this.f$0;
                return Boolean.valueOf((((TransitionAnimator.State) ((SnapshotMutableStateImpl) expandableControllerImpl.animatorState$delegate).getValue()) == null || ((ViewGroupOverlay) ((SnapshotMutableStateImpl) expandableControllerImpl.overlay$delegate).getValue()) == null) ? false : true);
            }
        });
    }
}
