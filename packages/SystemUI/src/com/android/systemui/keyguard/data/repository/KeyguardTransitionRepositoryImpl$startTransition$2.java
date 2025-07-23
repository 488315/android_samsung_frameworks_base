package com.android.systemui.keyguard.data.repository;

import com.android.systemui.keyguard.shared.model.TransitionInfo;
import com.android.systemui.keyguard.shared.model.TransitionModeOnCanceled;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class KeyguardTransitionRepositoryImpl$startTransition$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ TransitionInfo $info;
    float F$0;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ KeyguardTransitionRepositoryImpl this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[TransitionModeOnCanceled.values().length];
            try {
                iArr[TransitionModeOnCanceled.LAST_VALUE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[TransitionModeOnCanceled.RESET.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[TransitionModeOnCanceled.REVERSE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardTransitionRepositoryImpl$startTransition$2(KeyguardTransitionRepositoryImpl keyguardTransitionRepositoryImpl, TransitionInfo transitionInfo, Continuation continuation) {
        super(2, continuation);
        this.this$0 = keyguardTransitionRepositoryImpl;
        this.$info = transitionInfo;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        KeyguardTransitionRepositoryImpl$startTransition$2 keyguardTransitionRepositoryImpl$startTransition$2 = new KeyguardTransitionRepositoryImpl$startTransition$2(this.this$0, this.$info, continuation);
        keyguardTransitionRepositoryImpl$startTransition$2.L$0 = obj;
        return keyguardTransitionRepositoryImpl$startTransition$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyguardTransitionRepositoryImpl$startTransition$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0116  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00be  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00cd  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00e3  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x00ea  */
    /* JADX WARN: Type inference failed for: r7v9, types: [android.animation.Animator$AnimatorListener, com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl$startTransition$2$2$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r14) {
        /*
            Method dump skipped, instructions count: 299
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl$startTransition$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
