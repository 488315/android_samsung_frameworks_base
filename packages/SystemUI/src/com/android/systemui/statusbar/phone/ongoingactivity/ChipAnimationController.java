package com.android.systemui.statusbar.phone.ongoingactivity;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.transition.TransitionListenerAdapter;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import com.android.internal.dynamicanimation.animation.DynamicAnimation;
import com.android.internal.dynamicanimation.animation.SpringAnimation;
import com.android.internal.dynamicanimation.animation.SpringForce;
import com.android.keyguard.KeyguardSecPinBasedInputViewController$$ExternalSyntheticOutline0;
import com.android.settingslib.SecNotificationBlockManager$$ExternalSyntheticOutline0;
import com.android.settingslib.volume.MediaSessions$H$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.statusbar.phone.NotificationIconAreaController;
import com.android.systemui.statusbar.phone.PhoneStatusBarView;
import com.android.systemui.statusbar.phone.TouchInterceptFrameLayout;
import com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView;
import com.android.systemui.statusbar.phone.ongoingactivity.animation.ViewPropertyCapture;
import com.android.systemui.statusbar.phone.ongoingactivity.animation.VisibilityTransition;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.JobImpl;
import kotlinx.coroutines.JobKt;
import kotlinx.coroutines.android.HandlerContext;
import kotlinx.coroutines.internal.ContextScope;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.internal.Symbol;
import kotlinx.coroutines.scheduling.DefaultScheduler;
import kotlinx.coroutines.sync.MutexImpl;
import kotlinx.coroutines.sync.MutexKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ChipAnimationController {
    public static final PathInterpolator AlPHA_INTERPOLATOR;
    public static final PathInterpolator CARD_TRANSITION_COLLAPSE_INTERPOLATOR;
    public static final PathInterpolator CARD_TRANSITION_EXPAND_INTERPOLATOR;
    public static final PathInterpolator CHANGE_BOUND_INTERPOLATOR;
    public final Map animationMap;
    public final MutexImpl animationMutex;
    public final View clockView;
    public final ContextScope coroutineScope;
    public View hideTransitionView;
    public final Function0 isLeftClockPosition;
    public final NotificationIconAreaController notificationIconAreaController;
    public final View onGoingActivityChip;
    public final View onGoingCallChip;
    public final OngoingActivityController ongoingActivityController;
    public final PhoneStatusBarView statusBar;
    public final TouchInterceptFrameLayout touchInterceptActivityChip;
    public final TouchInterceptFrameLayout touchInterceptCallChip;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        PathInterpolator pathInterpolator = new PathInterpolator(0.33f, 1.0f, 0.68f, 1.0f);
        AlPHA_INTERPOLATOR = pathInterpolator;
        CardStackView.Companion companion = CardStackView.Companion;
        companion.getClass();
        CARD_TRANSITION_EXPAND_INTERPOLATOR = CardStackView.expandRootInterpolator;
        companion.getClass();
        CARD_TRANSITION_COLLAPSE_INTERPOLATOR = CardStackView.collapseRootInterpolator;
        CHANGE_BOUND_INTERPOLATOR = pathInterpolator;
    }

    public ChipAnimationController(PhoneStatusBarView phoneStatusBarView, NotificationIconAreaController notificationIconAreaController, OngoingActivityController ongoingActivityController, Function0 function0) {
        this.statusBar = phoneStatusBarView;
        this.notificationIconAreaController = notificationIconAreaController;
        this.ongoingActivityController = ongoingActivityController;
        this.isLeftClockPosition = function0;
        this.clockView = phoneStatusBarView.findViewById(R.id.clock);
        View findViewById = phoneStatusBarView.findViewById(R.id.ongoing_call_chip);
        this.onGoingCallChip = findViewById;
        this.touchInterceptCallChip = findViewById instanceof TouchInterceptFrameLayout ? (TouchInterceptFrameLayout) findViewById : null;
        View findViewById2 = phoneStatusBarView.findViewById(R.id.ongoing_activity_capsule);
        this.onGoingActivityChip = findViewById2;
        this.touchInterceptActivityChip = findViewById2 instanceof TouchInterceptFrameLayout ? (TouchInterceptFrameLayout) findViewById2 : null;
        this.animationMap = new LinkedHashMap();
        Symbol symbol = MutexKt.NO_OWNER;
        this.animationMutex = new MutexImpl(false);
        DefaultScheduler defaultScheduler = Dispatchers.Default;
        this.coroutineScope = CoroutineScopeKt.CoroutineScope(MainDispatcherLoader.dispatcher.immediate);
    }

    public static final Object access$animateSpring(final ChipAnimationController chipAnimationController, final View view, final DynamicAnimation.ViewProperty viewProperty, float f, SuspendLambda suspendLambda) {
        chipAnimationController.getClass();
        final CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(suspendLambda), 1);
        cancellableContinuationImpl.initCancellability();
        final SpringAnimation springAnimation = new SpringAnimation(view, viewProperty, f);
        SpringForce springForce = new SpringForce(f);
        springForce.setDampingRatio(0.808f);
        springForce.setStiffness(75.0f);
        springAnimation.setSpring(springForce);
        springAnimation.addEndListener(new DynamicAnimation.OnAnimationEndListener() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.ChipAnimationController$animateSpring$2$springAnimation$1$2
            public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z, float f2, float f3) {
                if (CancellableContinuation.this.isActive()) {
                    DynamicAnimation.ViewProperty viewProperty2 = viewProperty;
                    ChipAnimationController chipAnimationController2 = chipAnimationController;
                    View view2 = view;
                    PathInterpolator pathInterpolator = ChipAnimationController.AlPHA_INTERPOLATOR;
                    chipAnimationController2.getClass();
                    Log.d("{ChipAnimationController}", "animateSpring(" + viewProperty2 + ") End! V:" + ChipAnimationController.viewInfo(view2));
                    CancellableContinuation cancellableContinuation = CancellableContinuation.this;
                    int i = Result.$r8$clinit;
                    cancellableContinuation.resumeWith(Unit.INSTANCE);
                }
            }
        });
        springAnimation.start();
        cancellableContinuationImpl.invokeOnCancellation(new Function1() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.ChipAnimationController$animateSpring$2$1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                String name = viewProperty.getName();
                View view2 = view;
                PathInterpolator pathInterpolator = ChipAnimationController.AlPHA_INTERPOLATOR;
                chipAnimationController.getClass();
                MediaSessions$H$$ExternalSyntheticOutline0.m("invokeOnCancellation animateSpring(", name, ") Cancelled! V:", ChipAnimationController.viewInfo(view2), "{ChipAnimationController}");
                springAnimation.cancel();
                return Unit.INSTANCE;
            }
        });
        Object result = cancellableContinuationImpl.getResult();
        return result == CoroutineSingletons.COROUTINE_SUSPENDED ? result : Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$cancelAnimation(com.android.systemui.statusbar.phone.ongoingactivity.ChipAnimationController r5, android.view.View r6, kotlin.coroutines.jvm.internal.ContinuationImpl r7) {
        /*
            r5.getClass()
            boolean r0 = r7 instanceof com.android.systemui.statusbar.phone.ongoingactivity.ChipAnimationController$cancelAnimation$1
            if (r0 == 0) goto L16
            r0 = r7
            com.android.systemui.statusbar.phone.ongoingactivity.ChipAnimationController$cancelAnimation$1 r0 = (com.android.systemui.statusbar.phone.ongoingactivity.ChipAnimationController$cancelAnimation$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L16
            int r1 = r1 - r2
            r0.label = r1
            goto L1b
        L16:
            com.android.systemui.statusbar.phone.ongoingactivity.ChipAnimationController$cancelAnimation$1 r0 = new com.android.systemui.statusbar.phone.ongoingactivity.ChipAnimationController$cancelAnimation$1
            r0.<init>(r5, r7)
        L1b:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L3b
            if (r2 != r3) goto L33
            java.lang.Object r5 = r0.L$1
            r6 = r5
            android.view.View r6 = (android.view.View) r6
            java.lang.Object r5 = r0.L$0
            com.android.systemui.statusbar.phone.ongoingactivity.ChipAnimationController r5 = (com.android.systemui.statusbar.phone.ongoingactivity.ChipAnimationController) r5
            kotlin.ResultKt.throwOnFailure(r7)
            goto L87
        L33:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L3b:
            kotlin.ResultKt.throwOnFailure(r7)
            java.lang.String r7 = viewInfo(r6)
            java.lang.String r2 = "cancelAnimation() v:"
            java.lang.String r4 = "{ChipAnimationController}"
            android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(r2, r7, r4)
            java.util.Map r7 = r5.animationMap
            java.util.LinkedHashMap r7 = (java.util.LinkedHashMap) r7
            java.lang.Object r7 = r7.get(r6)
            kotlinx.coroutines.Job r7 = (kotlinx.coroutines.Job) r7
            if (r7 == 0) goto L87
            boolean r2 = r7.isActive()
            if (r2 == 0) goto L69
            r0.L$0 = r5
            r0.L$1 = r6
            r0.label = r3
            java.lang.Object r7 = kotlinx.coroutines.JobKt.cancelAndJoin(r7, r0)
            if (r7 != r1) goto L87
            return r1
        L69:
            java.lang.String r7 = viewInfo(r6)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "View:"
            r0.<init>(r1)
            r0.append(r7)
            java.lang.String r7 = " Animation already cancelled/completed !"
            r0.append(r7)
            java.lang.String r7 = r0.toString()
            int r7 = android.util.Log.d(r4, r7)
            kotlin.coroutines.jvm.internal.Boxing.boxInt(r7)
        L87:
            java.util.Map r5 = r5.animationMap
            r5.remove(r6)
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.ongoingactivity.ChipAnimationController.access$cancelAnimation(com.android.systemui.statusbar.phone.ongoingactivity.ChipAnimationController, android.view.View, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    public static final void access$handleOnGoingActivityChipAnimation(ChipAnimationController chipAnimationController, boolean z) {
        View view = chipAnimationController.onGoingActivityChip;
        Log.d("{ChipAnimationController}", "handleOnGoingActivityChipAnimation() visible:" + (view != null ? Integer.valueOf(view.getVisibility()) : null) + " show:" + z);
        View view2 = chipAnimationController.onGoingActivityChip;
        if (view2 != null) {
            if (!z) {
                chipAnimationController.startChipTransitionAnimation(false, view2, 8, CHANGE_BOUND_INTERPOLATOR, null);
            } else {
                startAnimation$default(chipAnimationController, view2);
                chipAnimationController.startChipTransitionAnimation(true, view2, 0, CHANGE_BOUND_INTERPOLATOR, null);
            }
        }
    }

    public static final void access$handleOnGoingCallChipAnimation(ChipAnimationController chipAnimationController, boolean z) {
        View view = chipAnimationController.onGoingCallChip;
        if (view != null) {
            if (!z) {
                chipAnimationController.startChipTransitionAnimation(false, view, 8, CHANGE_BOUND_INTERPOLATOR, null);
                return;
            }
            startAnimation$default(chipAnimationController, view);
            chipAnimationController.startChipTransitionAnimation(true, view, 0, CHANGE_BOUND_INTERPOLATOR, null);
            chipAnimationController.ongoingActivityController.updateAdapter();
            View view2 = chipAnimationController.onGoingActivityChip;
            if (view2 != null) {
                view2.requestLayout();
            }
        }
    }

    public static void startAnimation$default(final ChipAnimationController chipAnimationController, final View view) {
        StringBuilder m888m = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m888m(view.getVisibility(), "startAnimation() v:", viewInfo(view), " show:true view_visibility:", " required_state:");
        m888m.append(0);
        Log.d("{ChipAnimationController}", m888m.toString());
        if (view.getVisibility() == 0) {
            Log.i("{ChipAnimationController}", "startAnimation() Already hidden/Visible! Ignore Animation");
            chipAnimationController.showView(view);
            return;
        }
        view.setVisibility(0);
        view.setAlpha(0.0f);
        view.setScaleX(0.0f);
        view.setScaleY(0.0f);
        JobImpl Job$default = JobKt.Job$default();
        Job$default.invokeOnCompletion(new Function1() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.ChipAnimationController$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                View view2 = view;
                PathInterpolator pathInterpolator = ChipAnimationController.AlPHA_INTERPOLATOR;
                Log.d("{ChipAnimationController}", "Job invokeOnCompletion() show:true");
                ChipAnimationController.this.showView(view2);
                return Unit.INSTANCE;
            }
        });
        DefaultScheduler defaultScheduler = Dispatchers.Default;
        HandlerContext handlerContext = MainDispatcherLoader.dispatcher;
        handlerContext.getClass();
        BuildersKt.launch$default(CoroutineScopeKt.CoroutineScope(CoroutineContext.DefaultImpls.plus(handlerContext, Job$default)), null, null, new ChipAnimationController$startAnimation$2(chipAnimationController, view, true, 0, null), 3);
        chipAnimationController.animationMap.put(view, Job$default);
    }

    public static String viewInfo(View view) {
        int id = view.getId();
        return id != -1 ? view.getResources().getResourceEntryName(id) : view.toString();
    }

    public final void animateChipHide(View view, int i, boolean z) {
        BuildersKt.launch$default(this.coroutineScope, null, null, new ChipAnimationController$animateChipHide$1(this, view, i, z, null), 3);
    }

    public final void animateChipShow(View view, boolean z) {
        this.notificationIconAreaController.setAnimationsEnabled(false);
        BuildersKt.launch$default(this.coroutineScope, null, null, new ChipAnimationController$animateChipShow$1(this, view, z, null), 3);
    }

    public final void clockViewNudgeAnimation() {
        final View view;
        if (!((Boolean) this.isLeftClockPosition.invoke()).booleanValue() || (view = this.clockView) == null) {
            return;
        }
        Log.d("{ChipAnimationController}", "nudgeAnimation() view:" + viewInfo(view));
        float applyDimension = TypedValue.applyDimension(1, 2.0f, view.getResources().getDisplayMetrics());
        if (this.statusBar.getLayoutDirection() == 1) {
            applyDimension = -applyDimension;
        }
        SpringForce stiffness = new SpringForce().setDampingRatio(0.85f).setStiffness(400.0f);
        SpringAnimation springAnimation = new SpringAnimation(view, DynamicAnimation.TRANSLATION_X);
        springAnimation.setSpring(stiffness);
        final float f = 0.45f;
        final float f2 = 100.0f;
        springAnimation.addEndListener(new DynamicAnimation.OnAnimationEndListener() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.ChipAnimationController$nudgeAnimation$1
            public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z, float f3, float f4) {
                SpringForce stiffness2 = new SpringForce().setDampingRatio(f).setStiffness(f2);
                SpringAnimation springAnimation2 = new SpringAnimation(view, DynamicAnimation.TRANSLATION_X);
                springAnimation2.setSpring(stiffness2);
                final View view2 = view;
                springAnimation2.addEndListener(new DynamicAnimation.OnAnimationEndListener() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.ChipAnimationController$nudgeAnimation$1.1
                    public final void onAnimationEnd(DynamicAnimation dynamicAnimation2, boolean z2, float f5, float f6) {
                        Log.d("{ChipAnimationController}", "nudgeAnimation final v.translationX= " + view2.getTranslationX());
                    }
                });
                springAnimation2.animateToFinalPosition(0.0f);
            }
        });
        springAnimation.animateToFinalPosition(-applyDimension);
    }

    public final void hideView(View view, int i) {
        SecNotificationBlockManager$$ExternalSyntheticOutline0.m(i, "hideView() id:", viewInfo(view), " state:", "{ChipAnimationController}");
        view.setAlpha(0.0f);
        view.setScaleX(0.0f);
        view.setScaleY(0.0f);
        view.setVisibility(i);
        this.notificationIconAreaController.setAnimationsEnabled(true);
    }

    public final void showView(View view) {
        Log.d("{ChipAnimationController}", "showView() id:" + viewInfo(view));
        view.setAlpha(1.0f);
        view.setScaleX(1.0f);
        view.setScaleY(1.0f);
        view.setVisibility(0);
        this.notificationIconAreaController.setAnimationsEnabled(true);
        this.ongoingActivityController.startMarqueeAnimation();
    }

    public final void startChipTransitionAnimation(final boolean z, final View view, int i, Interpolator interpolator, OngoingCardController$collapseAnimation$2 ongoingCardController$collapseAnimation$2) {
        View view2;
        Log.d("{ChipAnimationController}", "startChipTransitionAnimation() start! show:" + z);
        final TransitionSet transitionSet = new TransitionSet();
        if (ongoingCardController$collapseAnimation$2 != null) {
            transitionSet.addTransition(new ViewPropertyCapture(view, ongoingCardController$collapseAnimation$2));
        }
        if (!z) {
            VisibilityTransition visibilityTransition = new VisibilityTransition();
            visibilityTransition.addTarget(R.id.ongoing_call_chip);
            transitionSet.addTransition(visibilityTransition);
            this.hideTransitionView = view;
        }
        ChangeBounds changeBounds = new ChangeBounds();
        changeBounds.setDuration(z ? 600L : 500L);
        changeBounds.setInterpolator(interpolator);
        transitionSet.addTransition(changeBounds);
        if (Intrinsics.areEqual(view, this.onGoingCallChip) && (view2 = this.onGoingActivityChip) != null && view2.getVisibility() == 0) {
            VisibilityTransition visibilityTransition2 = new VisibilityTransition();
            visibilityTransition2.addTarget(R.id.capsule_item_noti_expanded_info);
            transitionSet.addTransition(visibilityTransition2);
        }
        transitionSet.setOrdering(0);
        PhoneStatusBarView phoneStatusBarView = this.statusBar;
        TransitionManager.endTransitions(phoneStatusBarView);
        transitionSet.addListener((Transition.TransitionListener) new TransitionListenerAdapter() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.ChipAnimationController$startChipTransitionAnimation$2
            @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
            public final void onTransitionCancel(Transition transition) {
                Log.d("{ChipAnimationController}", "onTransitionCancel()");
                transitionSet.removeListener((Transition.TransitionListener) this);
                ChipAnimationController chipAnimationController = this;
                TouchInterceptFrameLayout touchInterceptFrameLayout = chipAnimationController.touchInterceptActivityChip;
                if (touchInterceptFrameLayout != null) {
                    touchInterceptFrameLayout.isAnimationInProgress = false;
                }
                chipAnimationController.notificationIconAreaController.setAnimationsEnabled(true);
                this.hideTransitionView = null;
            }

            @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
            public final void onTransitionEnd(Transition transition) {
                View view3;
                Log.d("{ChipAnimationController}", "onTransitionEnd()");
                transitionSet.removeListener((Transition.TransitionListener) this);
                ChipAnimationController chipAnimationController = this;
                TouchInterceptFrameLayout touchInterceptFrameLayout = chipAnimationController.touchInterceptActivityChip;
                if (touchInterceptFrameLayout != null) {
                    touchInterceptFrameLayout.isAnimationInProgress = false;
                }
                chipAnimationController.notificationIconAreaController.setAnimationsEnabled(true);
                if (!z && Intrinsics.areEqual(view, this.onGoingCallChip) && (view3 = this.onGoingActivityChip) != null && view3.getVisibility() == 0) {
                    this.clockViewNudgeAnimation();
                }
                View view4 = view;
                KeyguardSecPinBasedInputViewController$$ExternalSyntheticOutline0.m(MutableObjectList$$ExternalSyntheticOutline0.m(view4.getLeft(), view4.getRight(), "Transition View() left:", " right:", " top:"), view4.getTop(), " bottom:", view4.getBottom(), "{ChipAnimationController}");
                this.hideTransitionView = null;
            }

            @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
            public final void onTransitionStart(Transition transition) {
                View view3;
                Log.d("{ChipAnimationController}", "onTransitionStart()");
                if (z) {
                    ChipAnimationController chipAnimationController = this;
                    View view4 = view;
                    PathInterpolator pathInterpolator = ChipAnimationController.AlPHA_INTERPOLATOR;
                    chipAnimationController.getClass();
                    MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("nudgeClockIfNeeded() view:", ChipAnimationController.viewInfo(view4), "{ChipAnimationController}");
                    if (!view4.equals(chipAnimationController.onGoingActivityChip) && !view4.equals(chipAnimationController.onGoingCallChip)) {
                        Log.d("{ChipAnimationController}", "nudgeClockIfNeeded() skip!");
                    } else if (view4.equals(chipAnimationController.onGoingActivityChip) && (view3 = chipAnimationController.onGoingCallChip) != null && view3.getVisibility() == 0) {
                        Log.d("{ChipAnimationController}", "nudgeClockIfNeeded() skip! call chip is visible");
                    } else {
                        chipAnimationController.clockViewNudgeAnimation();
                    }
                }
                TouchInterceptFrameLayout touchInterceptFrameLayout = this.touchInterceptActivityChip;
                if (touchInterceptFrameLayout != null) {
                    touchInterceptFrameLayout.isAnimationInProgress = true;
                }
            }
        });
        this.notificationIconAreaController.setAnimationsEnabled(false);
        TransitionManager.endTransitions(phoneStatusBarView);
        TransitionManager.beginDelayedTransition(phoneStatusBarView, transitionSet);
        view.setVisibility(i);
        Log.d("{ChipAnimationController}", "startChipTransitionAnimation() End! show:" + z);
    }
}
