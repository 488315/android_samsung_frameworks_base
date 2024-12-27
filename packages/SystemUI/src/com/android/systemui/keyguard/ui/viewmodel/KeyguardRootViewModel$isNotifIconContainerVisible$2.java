package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.statusbar.phone.ScreenOffAnimation;
import com.android.systemui.util.ui.AnimatedValue;
import com.android.systemui.util.ui.AnimatedValueKt$zip$1;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function6;

final class KeyguardRootViewModel$isNotifIconContainerVisible$2 extends SuspendLambda implements Function6 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    /* synthetic */ boolean Z$2;
    int label;
    final /* synthetic */ KeyguardRootViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardRootViewModel$isNotifIconContainerVisible$2(KeyguardRootViewModel keyguardRootViewModel, Continuation continuation) {
        super(6, continuation);
        this.this$0 = keyguardRootViewModel;
    }

    @Override // kotlin.jvm.functions.Function6
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        boolean booleanValue2 = ((Boolean) obj2).booleanValue();
        boolean booleanValue3 = ((Boolean) obj3).booleanValue();
        KeyguardRootViewModel$isNotifIconContainerVisible$2 keyguardRootViewModel$isNotifIconContainerVisible$2 = new KeyguardRootViewModel$isNotifIconContainerVisible$2(this.this$0, (Continuation) obj6);
        keyguardRootViewModel$isNotifIconContainerVisible$2.Z$0 = booleanValue;
        keyguardRootViewModel$isNotifIconContainerVisible$2.Z$1 = booleanValue2;
        keyguardRootViewModel$isNotifIconContainerVisible$2.Z$2 = booleanValue3;
        keyguardRootViewModel$isNotifIconContainerVisible$2.L$0 = (AnimatedValue) obj4;
        keyguardRootViewModel$isNotifIconContainerVisible$2.L$1 = (AnimatedValue) obj5;
        return keyguardRootViewModel$isNotifIconContainerVisible$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object value;
        Object value2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        boolean z2 = this.Z$1;
        boolean z3 = this.Z$2;
        AnimatedValue animatedValue = (AnimatedValue) this.L$0;
        AnimatedValue animatedValue2 = (AnimatedValue) this.L$1;
        if (!z) {
            if (!z2) {
                List list = this.this$0.screenOffAnimationController.animations;
                if (!(list instanceof Collection) || !((ArrayList) list).isEmpty()) {
                    Iterator it = ((ArrayList) list).iterator();
                    while (it.hasNext()) {
                        if (((ScreenOffAnimation) it.next()).shouldShowAodIconsWhenShade()) {
                        }
                    }
                }
            }
            boolean z4 = animatedValue instanceof AnimatedValue.Animating;
            if (z4) {
                value = ((AnimatedValue.Animating) animatedValue).getValue();
            } else {
                if (!(animatedValue instanceof AnimatedValue.NotAnimating)) {
                    throw new NoWhenBranchMatchedException();
                }
                value = ((AnimatedValue.NotAnimating) animatedValue).getValue();
            }
            boolean z5 = animatedValue2 instanceof AnimatedValue.Animating;
            if (z5) {
                value2 = ((AnimatedValue.Animating) animatedValue2).getValue();
            } else {
                if (!(animatedValue2 instanceof AnimatedValue.NotAnimating)) {
                    throw new NoWhenBranchMatchedException();
                }
                value2 = ((AnimatedValue.NotAnimating) animatedValue2).getValue();
            }
            boolean booleanValue = ((Boolean) value2).booleanValue();
            boolean booleanValue2 = ((Boolean) value).booleanValue();
            boolean z6 = true;
            if (!z3 && (booleanValue || !booleanValue2)) {
                z6 = false;
            }
            Boolean valueOf = Boolean.valueOf(z6);
            if (z4) {
                if (z5) {
                    return new AnimatedValue.Animating(valueOf, new AnimatedValueKt$zip$1(animatedValue, animatedValue2));
                }
                if (animatedValue2 instanceof AnimatedValue.NotAnimating) {
                    return new AnimatedValue.Animating(valueOf, ((AnimatedValue.Animating) animatedValue).getOnStopAnimating());
                }
                throw new NoWhenBranchMatchedException();
            }
            if (!(animatedValue instanceof AnimatedValue.NotAnimating)) {
                throw new NoWhenBranchMatchedException();
            }
            if (z5) {
                return new AnimatedValue.Animating(valueOf, ((AnimatedValue.Animating) animatedValue2).getOnStopAnimating());
            }
            if (animatedValue2 instanceof AnimatedValue.NotAnimating) {
                return new AnimatedValue.NotAnimating(valueOf);
            }
            throw new NoWhenBranchMatchedException();
        }
        return new AnimatedValue.NotAnimating(Boolean.FALSE);
    }
}
