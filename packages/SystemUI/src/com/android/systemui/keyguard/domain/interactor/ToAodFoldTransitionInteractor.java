package com.android.systemui.keyguard.domain.interactor;

import android.animation.ValueAnimator;
import android.view.ViewGroup;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository;
import com.android.systemui.plugins.clocks.ClockController;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.shade.ShadeFoldAnimator;
import com.android.systemui.unfold.FoldAodAnimationController$startAnimationRunnable$1;
import kotlin.NotImplementedError;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

public final class ToAodFoldTransitionInteractor {
    public static final String TAG;
    public final ToAodFoldTransitionInteractor$foldAnimator$1 foldAnimator;
    public final KeyguardClockInteractor keyguardClockInteractor;
    public final CoroutineDispatcher mainDispatcher;
    public final CoroutineScope mainScope;
    public NotificationPanelViewController.ShadeFoldAnimatorImpl parentAnimator;
    public final KeyguardTransitionInteractor transitionInteractor;
    public final KeyguardTransitionRepository transitionRepository;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        String simpleName = Reflection.getOrCreateKotlinClass(ToAodFoldTransitionInteractor.class).getSimpleName();
        Intrinsics.checkNotNull(simpleName);
        TAG = simpleName;
    }

    public ToAodFoldTransitionInteractor(KeyguardClockInteractor keyguardClockInteractor, KeyguardTransitionInteractor keyguardTransitionInteractor, KeyguardTransitionRepository keyguardTransitionRepository, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher) {
        this.keyguardClockInteractor = keyguardClockInteractor;
        this.transitionInteractor = keyguardTransitionInteractor;
        this.transitionRepository = keyguardTransitionRepository;
        this.mainScope = coroutineScope;
        this.mainDispatcher = coroutineDispatcher;
        new ShadeFoldAnimator() { // from class: com.android.systemui.keyguard.domain.interactor.ToAodFoldTransitionInteractor$foldAnimator$1
            @Override // com.android.systemui.shade.ShadeFoldAnimator
            public final void cancelFoldToAodAnimation() {
                NotificationPanelViewController.ShadeFoldAnimatorImpl shadeFoldAnimatorImpl = ToAodFoldTransitionInteractor.this.parentAnimator;
                if (shadeFoldAnimatorImpl != null) {
                    shadeFoldAnimatorImpl.cancelFoldToAodAnimation();
                }
            }

            @Override // com.android.systemui.shade.ShadeFoldAnimator
            public final ViewGroup getView() {
                throw new NotImplementedError("Deprecated. Do not call.");
            }

            @Override // com.android.systemui.shade.ShadeFoldAnimator
            public final void prepareFoldToAodAnimation() {
                String str = ToAodFoldTransitionInteractor.TAG;
                ToAodFoldTransitionInteractor toAodFoldTransitionInteractor = ToAodFoldTransitionInteractor.this;
                toAodFoldTransitionInteractor.getClass();
                BuildersKt.launch$default(toAodFoldTransitionInteractor.mainScope, toAodFoldTransitionInteractor.mainDispatcher, null, new ToAodFoldTransitionInteractor$forceToAod$1(toAodFoldTransitionInteractor, null), 2);
                NotificationPanelViewController.ShadeFoldAnimatorImpl shadeFoldAnimatorImpl = toAodFoldTransitionInteractor.parentAnimator;
                if (shadeFoldAnimatorImpl != null) {
                    shadeFoldAnimatorImpl.prepareFoldToAodAnimation();
                }
            }

            @Override // com.android.systemui.shade.ShadeFoldAnimator
            public final void startFoldToAodAnimation(FoldAodAnimationController$startAnimationRunnable$1.AnonymousClass1 anonymousClass1, FoldAodAnimationController$startAnimationRunnable$1.AnonymousClass2 anonymousClass2, FoldAodAnimationController$startAnimationRunnable$1.AnonymousClass3 anonymousClass3) {
                final ToAodFoldTransitionInteractor toAodFoldTransitionInteractor = ToAodFoldTransitionInteractor.this;
                NotificationPanelViewController.ShadeFoldAnimatorImpl shadeFoldAnimatorImpl = toAodFoldTransitionInteractor.parentAnimator;
                if (shadeFoldAnimatorImpl != null) {
                    shadeFoldAnimatorImpl.buildViewAnimator(anonymousClass1, anonymousClass2, anonymousClass3).setUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.keyguard.domain.interactor.ToAodFoldTransitionInteractor$foldAnimator$1$startFoldToAodAnimation$1$1
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            KeyguardClockInteractor keyguardClockInteractor2 = ToAodFoldTransitionInteractor.this.keyguardClockInteractor;
                            float animatedFraction = valueAnimator.getAnimatedFraction();
                            ClockController clockController = keyguardClockInteractor2.clock$receiver.clock;
                            if (clockController != null) {
                                clockController.getSmallClock().getAnimations().fold(animatedFraction);
                                clockController.getLargeClock().getAnimations().fold(animatedFraction);
                            }
                        }
                    }).start();
                }
            }
        };
    }
}
