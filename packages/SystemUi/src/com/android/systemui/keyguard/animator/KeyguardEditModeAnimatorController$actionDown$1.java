package com.android.systemui.keyguard.animator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.provider.Settings;
import android.util.Log;
import com.android.systemui.keyguard.KeyguardEditModeControllerImpl;
import com.android.systemui.wallpaper.KeyguardWallpaperController;
import com.android.systemui.wallpaper.view.SystemUIWallpaperBase;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.Job;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.keyguard.animator.KeyguardEditModeAnimatorController$actionDown$1", m277f = "KeyguardEditModeAnimatorController.kt", m278l = {127, 135}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
final class KeyguardEditModeAnimatorController$actionDown$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ KeyguardEditModeAnimatorController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardEditModeAnimatorController$actionDown$1(KeyguardEditModeAnimatorController keyguardEditModeAnimatorController, Continuation<? super KeyguardEditModeAnimatorController$actionDown$1> continuation) {
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

    /* JADX WARN: Removed duplicated region for block: B:10:0x00c4  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0070  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x00be  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        CoroutineScope coroutineScope;
        CoroutineScope coroutineScope2;
        Job job;
        Job job2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope3 = (CoroutineScope) this.L$0;
            KeyguardEditModeAnimatorController keyguardEditModeAnimatorController = this.this$0;
            int i2 = KeyguardEditModeAnimatorController.$r8$clinit;
            if (keyguardEditModeAnimatorController.isNotSupportedAnimation()) {
                long j = Settings.System.getInt(this.this$0.getParentView().getContext().getContentResolver(), "keyguard_edit_mode_long_press_time", 800);
                this.L$0 = coroutineScope3;
                this.label = 1;
                if (DelayKt.delay(j, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                coroutineScope2 = coroutineScope3;
                job = (Job) coroutineScope2.getCoroutineContext().get(Job.Key);
                if (job == null ? job.isActive() : true) {
                }
            } else {
                SystemUIWallpaperBase systemUIWallpaperBase = ((KeyguardWallpaperController) this.this$0.keyguardWallpaper).mWallpaperView;
                if (systemUIWallpaperBase != null) {
                    systemUIWallpaperBase.updateDrawState(false);
                }
                this.L$0 = coroutineScope3;
                this.label = 2;
                if (DelayKt.delay(200L, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                coroutineScope = coroutineScope3;
                job2 = (Job) coroutineScope.getCoroutineContext().get(Job.Key);
                if (job2 != null ? job2.isActive() : true) {
                }
            }
        } else if (i == 1) {
            coroutineScope2 = (CoroutineScope) this.L$0;
            ResultKt.throwOnFailure(obj);
            job = (Job) coroutineScope2.getCoroutineContext().get(Job.Key);
            if (job == null ? job.isActive() : true) {
                ((KeyguardWallpaperController) this.this$0.keyguardWallpaper).setThumbnailVisibility(0);
                this.this$0.getParentView().performHapticFeedback(0);
                KeyguardEditModeAnimatorController keyguardEditModeAnimatorController2 = this.this$0;
                ((KeyguardEditModeControllerImpl) keyguardEditModeAnimatorController2.keyguardEditModeController).startEditActivity(keyguardEditModeAnimatorController2.getParentView().getContext(), true);
            }
        } else {
            if (i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            coroutineScope = (CoroutineScope) this.L$0;
            ResultKt.throwOnFailure(obj);
            job2 = (Job) coroutineScope.getCoroutineContext().get(Job.Key);
            if (job2 != null ? job2.isActive() : true) {
                final KeyguardEditModeAnimatorController keyguardEditModeAnimatorController3 = this.this$0;
                int i3 = KeyguardEditModeAnimatorController.$r8$clinit;
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
                        Ref$BooleanRef.this.element = true;
                        super.onAnimationCancel(animator);
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        super.onAnimationEnd(animator);
                        if (Ref$BooleanRef.this.element) {
                            return;
                        }
                        KeyguardEditModeAnimatorController keyguardEditModeAnimatorController4 = keyguardEditModeAnimatorController3;
                        int i4 = KeyguardEditModeAnimatorController.$r8$clinit;
                        keyguardEditModeAnimatorController4.animate(true);
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationStart(Animator animator) {
                        Ref$BooleanRef.this.element = false;
                        super.onAnimationStart(animator);
                    }
                });
                ArrayList arrayList = new ArrayList();
                for (Object obj2 : keyguardEditModeAnimatorController3.scaleViews) {
                    if (keyguardEditModeAnimatorController3.hasView(((Number) obj2).intValue())) {
                        arrayList.add(obj2);
                    }
                }
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    keyguardEditModeAnimatorController3.setViewScaleAnimation(animatorSet, keyguardEditModeAnimatorController3.getView(((Number) it.next()).intValue()), 0.975f, 600L, 0L);
                }
                animatorSet.start();
                keyguardEditModeAnimatorController3.touchDownAnimatorSet = animatorSet;
            }
        }
        return Unit.INSTANCE;
    }
}
