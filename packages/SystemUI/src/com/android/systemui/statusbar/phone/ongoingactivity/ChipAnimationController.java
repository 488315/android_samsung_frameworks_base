package com.android.systemui.statusbar.phone.ongoingactivity;

import android.content.res.Resources;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.transition.TransitionListenerAdapter;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import com.android.internal.dynamicanimation.animation.DynamicAnimation;
import com.android.internal.dynamicanimation.animation.SpringAnimation;
import com.android.internal.dynamicanimation.animation.SpringForce;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.CarrierTextManagerLogger$logCallbackSentFromUpdate$2$$ExternalSyntheticOutline0;
import com.android.settingslib.SecNotificationBlockManager$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.bixby2.controller.MWBixbyController$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.phone.NotificationIconAreaController;
import com.android.systemui.statusbar.phone.PhoneStatusBarView;
import com.android.systemui.statusbar.phone.TouchInterceptFrameLayout;
import com.android.systemui.statusbar.phone.ongoingactivity.animation.VisibilityTransition;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Ref$FloatRef;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.JobImpl;
import kotlinx.coroutines.JobKt;
import kotlinx.coroutines.MainCoroutineDispatcher;
import kotlinx.coroutines.internal.ContextScope;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.internal.Symbol;
import kotlinx.coroutines.scheduling.DefaultScheduler;
import kotlinx.coroutines.sync.MutexImpl;
import kotlinx.coroutines.sync.MutexKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ChipAnimationController {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Map animationMap;
    public final MutexImpl animationMutex;
    public final View clockView;
    public final ContextScope coroutineScope;
    public final NotificationIconAreaController notificationIconAreaController;
    public final View onGoingActivityChip;
    public final View onGoingCallChip;
    public final OngoingActivityController ongoingActivityController;
    public final PhoneStatusBarView statusBar;
    public final TouchInterceptFrameLayout touchInterceptCallChip;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public ChipAnimationController(PhoneStatusBarView phoneStatusBarView, NotificationIconAreaController notificationIconAreaController, OngoingActivityController ongoingActivityController) {
        this.statusBar = phoneStatusBarView;
        this.notificationIconAreaController = notificationIconAreaController;
        this.ongoingActivityController = ongoingActivityController;
        this.clockView = phoneStatusBarView.findViewById(R.id.clock);
        View findViewById = phoneStatusBarView.findViewById(R.id.ongoing_call_chip);
        this.onGoingCallChip = findViewById;
        this.touchInterceptCallChip = findViewById instanceof TouchInterceptFrameLayout ? (TouchInterceptFrameLayout) findViewById : null;
        this.onGoingActivityChip = phoneStatusBarView.findViewById(R.id.ongoing_activity_capsule);
        this.animationMap = new LinkedHashMap();
        Symbol symbol = MutexKt.NO_OWNER;
        this.animationMutex = new MutexImpl(false);
        DefaultScheduler defaultScheduler = Dispatchers.Default;
        this.coroutineScope = CoroutineScopeKt.CoroutineScope(MainDispatcherLoader.dispatcher.getImmediate());
    }

    public static final Object access$animateSpring(final ChipAnimationController chipAnimationController, final View view, final DynamicAnimation.ViewProperty viewProperty, float f, Continuation continuation) {
        chipAnimationController.getClass();
        final CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(continuation), 1);
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
                    int i = ChipAnimationController.$r8$clinit;
                    chipAnimationController2.getClass();
                    Log.d("{ChipAnimationController}", "animateSpring(" + viewProperty2 + ") End! V:" + ChipAnimationController.viewInfo(view2));
                    CancellableContinuation cancellableContinuation = CancellableContinuation.this;
                    int i2 = Result.$r8$clinit;
                    cancellableContinuation.resumeWith(Unit.INSTANCE);
                }
            }
        });
        springAnimation.start();
        cancellableContinuationImpl.invokeOnCancellation(new Function1() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.ChipAnimationController$animateSpring$2$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                String name = viewProperty.getName();
                ChipAnimationController chipAnimationController2 = chipAnimationController;
                View view2 = view;
                int i = ChipAnimationController.$r8$clinit;
                chipAnimationController2.getClass();
                MWBixbyController$$ExternalSyntheticOutline0.m("invokeOnCancellation animateSpring(", name, ") Cancelled! V:", ChipAnimationController.viewInfo(view2), "{ChipAnimationController}");
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
    public static final java.lang.Object access$cancelAnimation(com.android.systemui.statusbar.phone.ongoingactivity.ChipAnimationController r5, android.view.View r6, kotlin.coroutines.Continuation r7) {
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
            goto L8d
        L33:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L3b:
            kotlin.ResultKt.throwOnFailure(r7)
            java.lang.String r7 = viewInfo(r6)
            java.lang.String r2 = "cancelAnimation() v:"
            java.lang.String r7 = r2.concat(r7)
            java.lang.String r2 = "{ChipAnimationController}"
            android.util.Log.d(r2, r7)
            java.util.Map r7 = r5.animationMap
            java.util.LinkedHashMap r7 = (java.util.LinkedHashMap) r7
            java.lang.Object r7 = r7.get(r6)
            kotlinx.coroutines.Job r7 = (kotlinx.coroutines.Job) r7
            if (r7 == 0) goto L8d
            boolean r4 = r7.isActive()
            if (r4 == 0) goto L6d
            r0.L$0 = r5
            r0.L$1 = r6
            r0.label = r3
            java.lang.Object r7 = kotlinx.coroutines.JobKt.cancelAndJoin(r7, r0)
            if (r7 != r1) goto L8d
            goto L94
        L6d:
            java.lang.String r7 = viewInfo(r6)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "View:"
            r0.<init>(r1)
            r0.append(r7)
            java.lang.String r7 = " Animation already cancelled/completed !"
            r0.append(r7)
            java.lang.String r7 = r0.toString()
            int r7 = android.util.Log.d(r2, r7)
            java.lang.Integer r0 = new java.lang.Integer
            r0.<init>(r7)
        L8d:
            java.util.Map r5 = r5.animationMap
            r5.remove(r6)
            kotlin.Unit r1 = kotlin.Unit.INSTANCE
        L94:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.ongoingactivity.ChipAnimationController.access$cancelAnimation(com.android.systemui.statusbar.phone.ongoingactivity.ChipAnimationController, android.view.View, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public static final void access$handleOnGoingActivityChipAnimation(ChipAnimationController chipAnimationController) {
        View view = chipAnimationController.onGoingActivityChip;
        Log.d("{ChipAnimationController}", "handleOnGoingActivityChipAnimation() visible:" + (view != null ? Integer.valueOf(view.getVisibility()) : null));
        View view2 = chipAnimationController.onGoingActivityChip;
        if (view2 != null) {
            int visibility = view2.getVisibility();
            if (visibility == 4 || visibility == 8) {
                chipAnimationController.startAnimation(view2, 0, true);
                return;
            }
            Log.d("{ChipAnimationController}", "View:" + viewInfo(view2) + " Already Visible! alpha:" + view2.getAlpha() + " scaleX:" + view2.getScaleX() + " scaleY:" + view2.getScaleY());
            chipAnimationController.showView(view2);
        }
    }

    public static final void access$nudgeClockIfNeeded(ChipAnimationController chipAnimationController, View view) {
        chipAnimationController.getClass();
        Log.d("{ChipAnimationController}", "nudgeClockIfNeeded() view:".concat(viewInfo(view)));
        if (!view.equals(chipAnimationController.onGoingCallChip)) {
            if (!view.equals(chipAnimationController.onGoingActivityChip)) {
                return;
            }
            View view2 = chipAnimationController.onGoingCallChip;
            if (view2 != null && view2.getVisibility() == 0) {
                return;
            }
        }
        View view3 = chipAnimationController.clockView;
        if (view3 != null) {
            chipAnimationController.nudgeAnimation(view3, false);
        }
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

    public final void hideView(View view, int i) {
        SecNotificationBlockManager$$ExternalSyntheticOutline0.m(i, "hideView() id:", viewInfo(view), " state:", "{ChipAnimationController}");
        view.setAlpha(0.0f);
        view.setScaleX(0.0f);
        view.setScaleY(0.0f);
        view.setVisibility(i);
        this.notificationIconAreaController.setAnimationsEnabled(true);
    }

    public final void nudgeAnimation(final View view, boolean z) {
        Log.d("{ChipAnimationController}", "nudgeAnimation() view:".concat(viewInfo(view)));
        Resources resources = view.getResources();
        final Ref$FloatRef ref$FloatRef = new Ref$FloatRef();
        ref$FloatRef.element = TypedValue.applyDimension(1, 1.0f, resources.getDisplayMetrics());
        if (this.statusBar.getLayoutDirection() == 1) {
            ref$FloatRef.element = -ref$FloatRef.element;
        }
        SpringForce stiffness = new SpringForce().setDampingRatio(0.901f).setStiffness(z ? 800.0f : 400.0f);
        SpringAnimation springAnimation = new SpringAnimation(view, DynamicAnimation.TRANSLATION_X);
        springAnimation.setSpring(stiffness);
        springAnimation.animateToFinalPosition(-ref$FloatRef.element);
        final float f = 0.813f;
        final float f2 = 200.0f;
        springAnimation.addEndListener(new DynamicAnimation.OnAnimationEndListener() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.ChipAnimationController$nudgeAnimation$1
            public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z2, float f3, float f4) {
                SpringForce stiffness2 = new SpringForce().setDampingRatio(f).setStiffness(f2);
                SpringAnimation springAnimation2 = new SpringAnimation(view, DynamicAnimation.TRANSLATION_X);
                springAnimation2.setSpring(stiffness2);
                springAnimation2.animateToFinalPosition(ref$FloatRef.element);
            }
        });
    }

    public final void showView(View view) {
        Log.d("{ChipAnimationController}", "showView() id:".concat(viewInfo(view)));
        view.setAlpha(1.0f);
        view.setScaleX(1.0f);
        view.setScaleY(1.0f);
        view.setVisibility(0);
        this.notificationIconAreaController.setAnimationsEnabled(true);
        this.ongoingActivityController.startMarqueeAnimation();
    }

    public final void startAnimation(final View view, final int i, final boolean z) {
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(CarrierTextManagerLogger$logCallbackSentFromUpdate$2$$ExternalSyntheticOutline0.m("startAnimation() v:", viewInfo(view), " show:", " view_visibility:", z), view.getVisibility(), " required_state:", i, "{ChipAnimationController}");
        if ((!z && view.getVisibility() != 0) || (z && view.getVisibility() == 0)) {
            Log.i("{ChipAnimationController}", "startAnimation() Already hidden/Visible! Ignore Animation");
            if (!z && view.getVisibility() != i) {
                hideView(view, i);
                return;
            } else {
                if (z) {
                    showView(view);
                    return;
                }
                return;
            }
        }
        view.setVisibility(0);
        view.setAlpha(z ? 0.0f : 1.0f);
        view.setScaleX(z ? 0.0f : 1.0f);
        view.setScaleY(z ? 0.0f : 1.0f);
        JobImpl Job$default = JobKt.Job$default();
        Job$default.invokeOnCompletion(new Function1() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.ChipAnimationController$startAnimation$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("Job invokeOnCompletion() show:", "{ChipAnimationController}", z);
                if (z) {
                    ChipAnimationController chipAnimationController = this;
                    View view2 = view;
                    int i2 = ChipAnimationController.$r8$clinit;
                    chipAnimationController.showView(view2);
                } else {
                    ChipAnimationController chipAnimationController2 = this;
                    View view3 = view;
                    int i3 = i;
                    int i4 = ChipAnimationController.$r8$clinit;
                    chipAnimationController2.hideView(view3, i3);
                }
                return Unit.INSTANCE;
            }
        });
        DefaultScheduler defaultScheduler = Dispatchers.Default;
        MainCoroutineDispatcher mainCoroutineDispatcher = MainDispatcherLoader.dispatcher;
        mainCoroutineDispatcher.getClass();
        BuildersKt.launch$default(CoroutineScopeKt.CoroutineScope(CoroutineContext.DefaultImpls.plus(mainCoroutineDispatcher, Job$default)), null, null, new ChipAnimationController$startAnimation$2(this, view, z, i, null), 3);
        this.animationMap.put(view, Job$default);
    }

    public final void startChipTransitionAnimation(final boolean z) {
        Log.d("{ChipAnimationController}", "startChipTransitionAnimation() start! show:" + z);
        final TransitionSet transitionSet = new TransitionSet();
        transitionSet.addListener((Transition.TransitionListener) new TransitionListenerAdapter() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.ChipAnimationController$startChipTransitionAnimation$1
            @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
            public final void onTransitionCancel(Transition transition) {
                ChipAnimationController.this.notificationIconAreaController.setAnimationsEnabled(true);
            }

            @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
            public final void onTransitionEnd(Transition transition) {
                ChipAnimationController.this.notificationIconAreaController.setAnimationsEnabled(true);
            }
        });
        if (!z) {
            VisibilityTransition visibilityTransition = new VisibilityTransition();
            visibilityTransition.addTarget(R.id.ongoing_call_chip);
            transitionSet.addTransition(visibilityTransition);
        }
        View view = this.onGoingActivityChip;
        if (view != null && view.getVisibility() == 0) {
            ChangeBounds changeBounds = new ChangeBounds();
            changeBounds.setDuration(500L);
            changeBounds.addListener(new TransitionListenerAdapter() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.ChipAnimationController$startChipTransitionAnimation$2
                @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
                public final void onTransitionEnd(Transition transition) {
                    ChipAnimationController chipAnimationController;
                    View view2;
                    Log.d("{ChipAnimationController}", "ChangeBound transition End!");
                    if (z || this.onGoingActivityChip.getVisibility() != 0 || (view2 = (chipAnimationController = this).clockView) == null) {
                        return;
                    }
                    chipAnimationController.nudgeAnimation(view2, false);
                }
            });
            transitionSet.addTransition(changeBounds);
            VisibilityTransition visibilityTransition2 = new VisibilityTransition();
            visibilityTransition2.addTarget(R.id.capsule_item_noti_expanded_info);
            transitionSet.addTransition(visibilityTransition2);
        }
        transitionSet.setOrdering(0);
        PhoneStatusBarView phoneStatusBarView = this.statusBar;
        TransitionManager.endTransitions(phoneStatusBarView);
        transitionSet.addListener((Transition.TransitionListener) new TransitionListenerAdapter() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.ChipAnimationController$startChipTransitionAnimation$3
            @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
            public final void onTransitionEnd(Transition transition) {
                super.onTransitionEnd(transition);
                Log.d("{ChipAnimationController}", "onTransitionEnd()");
                transitionSet.removeListener((Transition.TransitionListener) this);
            }

            @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
            public final void onTransitionStart(Transition transition) {
                super.onTransitionStart(transition);
                Log.d("{ChipAnimationController}", "onTransitionStart()");
            }
        });
        this.notificationIconAreaController.setAnimationsEnabled(false);
        TransitionManager.endTransitions(phoneStatusBarView);
        TransitionManager.beginDelayedTransition(phoneStatusBarView, transitionSet);
        View view2 = this.onGoingCallChip;
        if (view2 != null) {
            view2.setVisibility(z ? 0 : 8);
        }
        this.ongoingActivityController.updateAdapter();
        View view3 = this.onGoingActivityChip;
        if (view3 != null) {
            view3.requestLayout();
        }
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("startChipTransitionAnimation() End! show:", "{ChipAnimationController}", z);
    }
}
