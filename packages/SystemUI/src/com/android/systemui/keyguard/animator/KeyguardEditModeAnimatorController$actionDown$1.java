package com.android.systemui.keyguard.animator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.provider.Settings;
import android.util.Log;
import com.android.systemui.keyguard.KeyguardEditModeController;
import com.android.systemui.keyguard.KeyguardEditModeControllerImpl;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.DelayKt;

/* loaded from: classes2.dex */
final class KeyguardEditModeAnimatorController$actionDown$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ KeyguardEditModeAnimatorController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardEditModeAnimatorController$actionDown$1(KeyguardEditModeAnimatorController keyguardEditModeAnimatorController, Continuation continuation) {
        super(2, continuation);
        this.this$0 = keyguardEditModeAnimatorController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        KeyguardEditModeAnimatorController$actionDown$1 keyguardEditModeAnimatorController$actionDown$1 = new KeyguardEditModeAnimatorController$actionDown$1(this.this$0, continuation);
        keyguardEditModeAnimatorController$actionDown$1.L$0 = obj;
        return keyguardEditModeAnimatorController$actionDown$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyguardEditModeAnimatorController$actionDown$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x009d  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) throws IOException {
        CoroutineScope coroutineScope;
        CoroutineScope coroutineScope2;
        KeyguardTouchAnimator keyguardTouchAnimator;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        int i2 = 0;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope3 = (CoroutineScope) this.L$0;
            KeyguardEditModeAnimatorController keyguardEditModeAnimatorController = this.this$0;
            int i3 = KeyguardEditModeAnimatorController.$r8$clinit;
            if (keyguardEditModeAnimatorController.isNotSupportedAnimation()) {
                long j = Settings.System.getInt(this.this$0.keyguardTouchAnimator.getParentView$frameworks__base__packages__SystemUI__android_common__SystemUI_core().getContext().getContentResolver(), "keyguard_edit_mode_long_press_time", 800);
                this.L$0 = coroutineScope3;
                this.label = 1;
                if (DelayKt.delay(j, this) != coroutineSingletons) {
                    coroutineScope2 = coroutineScope3;
                    if (CoroutineScopeKt.isActive(coroutineScope2)) {
                    }
                }
            } else {
                this.this$0.keyguardWallpaper.getClass();
                this.L$0 = coroutineScope3;
                this.label = 2;
                if (DelayKt.delay(200L, this) != coroutineSingletons) {
                    coroutineScope = coroutineScope3;
                    if (CoroutineScopeKt.isActive(coroutineScope)) {
                    }
                }
            }
            return coroutineSingletons;
        }
        if (i == 1) {
            coroutineScope2 = (CoroutineScope) this.L$0;
            ResultKt.throwOnFailure(obj);
            if (CoroutineScopeKt.isActive(coroutineScope2)) {
                this.this$0.keyguardTouchAnimator.getParentView$frameworks__base__packages__SystemUI__android_common__SystemUI_core().performHapticFeedback(0);
                KeyguardEditModeAnimatorController keyguardEditModeAnimatorController2 = this.this$0;
                ((KeyguardEditModeControllerImpl) keyguardEditModeAnimatorController2.keyguardEditModeController).startEditActivity(keyguardEditModeAnimatorController2.keyguardTouchAnimator.getParentView$frameworks__base__packages__SystemUI__android_common__SystemUI_core().getContext(), true);
            }
        } else {
            if (i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            coroutineScope = (CoroutineScope) this.L$0;
            ResultKt.throwOnFailure(obj);
            if (CoroutineScopeKt.isActive(coroutineScope)) {
                final KeyguardEditModeAnimatorController keyguardEditModeAnimatorController3 = this.this$0;
                int i4 = KeyguardEditModeAnimatorController.$r8$clinit;
                keyguardEditModeAnimatorController3.getClass();
                Log.d("KeyguardEditModeAnimatorController", "animateTouchDown");
                if (keyguardEditModeAnimatorController3.touchDownAnimatorSet.isRunning()) {
                    keyguardEditModeAnimatorController3.touchDownAnimatorSet.cancel();
                }
                final Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.keyguard.animator.KeyguardEditModeAnimatorController$animateTouchDown$1$1
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationCancel(Animator animator) {
                        ref$BooleanRef.element = true;
                        super.onAnimationCancel(animator);
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        super.onAnimationEnd(animator);
                        if (ref$BooleanRef.element) {
                            return;
                        }
                        KeyguardEditModeAnimatorController keyguardEditModeAnimatorController4 = keyguardEditModeAnimatorController3;
                        int i5 = KeyguardEditModeAnimatorController.$r8$clinit;
                        keyguardEditModeAnimatorController4.animate(true);
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationStart(Animator animator) {
                        ref$BooleanRef.element = false;
                        super.onAnimationStart(animator);
                    }
                });
                final KeyguardEditModeControllerImpl keyguardEditModeControllerImpl = (KeyguardEditModeControllerImpl) keyguardEditModeAnimatorController3.keyguardEditModeController;
                keyguardEditModeControllerImpl.getClass();
                animatorSet.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.keyguard.KeyguardEditModeControllerImpl$touchDownAnimatorListener$1
                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationCancel(Animator animator) {
                        ArrayList arrayList = (ArrayList) keyguardEditModeControllerImpl.listeners;
                        int size = arrayList.size();
                        int i5 = 0;
                        while (i5 < size) {
                            Object obj2 = arrayList.get(i5);
                            i5++;
                            ((KeyguardEditModeController.Listener) obj2).onTouchDownCanceled();
                        }
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationStart(Animator animator) {
                        ArrayList arrayList = (ArrayList) keyguardEditModeControllerImpl.listeners;
                        int size = arrayList.size();
                        int i5 = 0;
                        while (i5 < size) {
                            Object obj2 = arrayList.get(i5);
                            i5++;
                            ((KeyguardEditModeController.Listener) obj2).onTouchDownStarted();
                        }
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationRepeat(Animator animator) {
                    }
                });
                List list = keyguardEditModeAnimatorController3.scaleViews;
                ArrayList arrayList = new ArrayList();
                Iterator it = list.iterator();
                while (true) {
                    boolean zHasNext = it.hasNext();
                    keyguardTouchAnimator = keyguardEditModeAnimatorController3.keyguardTouchAnimator;
                    if (!zHasNext) {
                        break;
                    }
                    Object next = it.next();
                    if (keyguardTouchAnimator.hasView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(((Number) next).intValue())) {
                        arrayList.add(next);
                    }
                }
                int size = arrayList.size();
                while (i2 < size) {
                    Object obj2 = arrayList.get(i2);
                    i2++;
                    keyguardEditModeAnimatorController3.setViewScaleAnimation(animatorSet, keyguardTouchAnimator.getView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(((Number) obj2).intValue()), 0.975f, 600L, 0L);
                }
                animatorSet.start();
                keyguardEditModeAnimatorController3.touchDownAnimatorSet = animatorSet;
                Log.d("KeyguardTouchAnimator", "LOCKUI_EDIT_MODE is started");
                this.this$0.keyguardTouchAnimator.lockscreenShadeTransitionController.isKeyguardAnimatorStarted = true;
            }
        }
        return Unit.INSTANCE;
    }
}
