package com.android.systemui.keyguard.animator;

import android.util.Log;
import android.view.View;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import com.android.systemui.doze.AODParameters;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.power.shared.model.ScreenPowerState;
import com.android.systemui.statusbar.phone.DozeParameters;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__LimitKt$drop$$inlined$unsafeFlow$1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ScreenOnAffordanceViewController extends ViewAnimationController {
    public boolean initPivot;
    public final List restoreSpringAnimationList;
    public final List screenONAffordanceViews;
    public final List screenOnSpringAnimationList;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.keyguard.animator.ScreenOnAffordanceViewController$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ DozeParameters $dozeParameters;
        final /* synthetic */ KeyguardTouchAnimator $keyguardTouchAnimator;
        final /* synthetic */ PowerInteractor $powerInteractor;
        int label;
        final /* synthetic */ ScreenOnAffordanceViewController this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(PowerInteractor powerInteractor, ScreenOnAffordanceViewController screenOnAffordanceViewController, KeyguardTouchAnimator keyguardTouchAnimator, DozeParameters dozeParameters, Continuation continuation) {
            super(2, continuation);
            this.$powerInteractor = powerInteractor;
            this.this$0 = screenOnAffordanceViewController;
            this.$keyguardTouchAnimator = keyguardTouchAnimator;
            this.$dozeParameters = dozeParameters;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$powerInteractor, this.this$0, this.$keyguardTouchAnimator, this.$dozeParameters, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                FlowKt__LimitKt$drop$$inlined$unsafeFlow$1 drop = FlowKt.drop(this.$powerInteractor.screenPowerState);
                final ScreenOnAffordanceViewController screenOnAffordanceViewController = this.this$0;
                final KeyguardTouchAnimator keyguardTouchAnimator = this.$keyguardTouchAnimator;
                final DozeParameters dozeParameters = this.$dozeParameters;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.animator.ScreenOnAffordanceViewController.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        KeyguardTouchAnimator keyguardTouchAnimator2;
                        ScreenPowerState screenPowerState = (ScreenPowerState) obj2;
                        final ScreenOnAffordanceViewController screenOnAffordanceViewController2 = ScreenOnAffordanceViewController.this;
                        if (!screenOnAffordanceViewController2.initPivot) {
                            screenOnAffordanceViewController2.initPivot = true;
                            keyguardTouchAnimator.pivotViewController.setChildViewPivot$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
                        }
                        AODParameters aODParameters = dozeParameters.mAODParameters;
                        int i2 = 0;
                        boolean z = aODParameters != null && aODParameters.mDozeUiState;
                        Log.d("KeyguardTouchAnimator", "screenPowerState it=" + screenPowerState + " dozeUiState=" + z);
                        if (z) {
                            Log.d("KeyguardTouchAnimator", "screenPowerState Do not animation in dozeUiState");
                            return Unit.INSTANCE;
                        }
                        if (screenPowerState == ScreenPowerState.SCREEN_ON) {
                            Log.d("KeyguardTouchAnimator", "showScreenOnAffordanceAnimation");
                            List list = screenOnAffordanceViewController2.screenONAffordanceViews;
                            ArrayList arrayList = new ArrayList();
                            Iterator it = list.iterator();
                            while (true) {
                                boolean hasNext = it.hasNext();
                                keyguardTouchAnimator2 = screenOnAffordanceViewController2.keyguardTouchAnimator;
                                if (!hasNext) {
                                    break;
                                }
                                Object next = it.next();
                                if (keyguardTouchAnimator2.hasView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(((Number) next).intValue())) {
                                    arrayList.add(next);
                                }
                            }
                            ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
                            Iterator it2 = arrayList.iterator();
                            while (it2.hasNext()) {
                                arrayList2.add(keyguardTouchAnimator2.getView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(((Number) it2.next()).intValue()));
                            }
                            ArrayList arrayList3 = new ArrayList();
                            Iterator it3 = arrayList2.iterator();
                            while (it3.hasNext()) {
                                Object next2 = it3.next();
                                if (((View) next2).getVisibility() == 0) {
                                    arrayList3.add(next2);
                                }
                            }
                            Iterator it4 = arrayList3.iterator();
                            while (it4.hasNext()) {
                                Object next3 = it4.next();
                                int i3 = i2 + 1;
                                if (i2 < 0) {
                                    CollectionsKt__CollectionsKt.throwIndexOverflow();
                                    throw null;
                                }
                                View view = (View) next3;
                                SpringAnimation springAnimation = new SpringAnimation(view, DynamicAnimation.SCALE_X);
                                springAnimation.mSpring = ActionUpOrCancelHandler$$ExternalSyntheticOutline0.m(150.0f, 1.0f);
                                springAnimation.animateToFinalPosition(1.0f);
                                ((ArrayList) screenOnAffordanceViewController2.screenOnSpringAnimationList).add(springAnimation);
                                SpringAnimation springAnimation2 = new SpringAnimation(view, DynamicAnimation.SCALE_Y);
                                springAnimation2.mSpring = ActionUpOrCancelHandler$$ExternalSyntheticOutline0.m(150.0f, 1.0f);
                                springAnimation2.animateToFinalPosition(1.0f);
                                springAnimation2.addEndListener(new DynamicAnimation.OnAnimationEndListener() { // from class: com.android.systemui.keyguard.animator.ScreenOnAffordanceViewController$showScreenOnAffordanceAnimation$4$1
                                    @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationEndListener
                                    public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z2, float f, float f2) {
                                        KeyguardTouchSwipeCallback keyguardTouchSwipeCallback = ScreenOnAffordanceViewController.this.keyguardTouchAnimator.callback;
                                        if (keyguardTouchSwipeCallback == null) {
                                            keyguardTouchSwipeCallback = null;
                                        }
                                        if (keyguardTouchSwipeCallback != null) {
                                            keyguardTouchSwipeCallback.onScreenOnOffAnimationEnd();
                                        }
                                    }
                                });
                                ((ArrayList) screenOnAffordanceViewController2.screenOnSpringAnimationList).add(springAnimation2);
                                i2 = i3;
                            }
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (drop.collect(flowCollector, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

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

    public ScreenOnAffordanceViewController(KeyguardTouchAnimator keyguardTouchAnimator, PowerInteractor powerInteractor, CoroutineScope coroutineScope, DozeParameters dozeParameters) {
        super(keyguardTouchAnimator);
        this.screenONAffordanceViews = CollectionsKt__CollectionsKt.listOf(7, 1, 2, 4, 5, 6, 12, 9, 10);
        this.screenOnSpringAnimationList = new ArrayList();
        this.restoreSpringAnimationList = new ArrayList();
        BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(powerInteractor, this, keyguardTouchAnimator, dozeParameters, null), 3);
    }
}
