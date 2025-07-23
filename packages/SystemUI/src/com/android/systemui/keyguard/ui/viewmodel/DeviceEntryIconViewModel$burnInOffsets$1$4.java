package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class DeviceEntryIconViewModel$burnInOffsets$1$4 extends SuspendLambda implements Function4 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ Object L$2;
    int label;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[KeyguardState.values().length];
            try {
                iArr[KeyguardState.ALTERNATE_BOUNCER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[KeyguardState.LOCKSCREEN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public DeviceEntryIconViewModel$burnInOffsets$1$4(Continuation continuation) {
        super(4, continuation);
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        DeviceEntryIconViewModel$burnInOffsets$1$4 deviceEntryIconViewModel$burnInOffsets$1$4 = new DeviceEntryIconViewModel$burnInOffsets$1$4((Continuation) obj4);
        deviceEntryIconViewModel$burnInOffsets$1$4.L$0 = (Pair) obj;
        deviceEntryIconViewModel$burnInOffsets$1$4.L$1 = (BurnInOffsets) obj2;
        deviceEntryIconViewModel$burnInOffsets$1$4.L$2 = (BurnInOffsets) obj3;
        return deviceEntryIconViewModel$burnInOffsets$1$4.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Pair pair = (Pair) this.L$0;
        BurnInOffsets burnInOffsets = (BurnInOffsets) this.L$1;
        BurnInOffsets burnInOffsets2 = (BurnInOffsets) this.L$2;
        TransitionStep transitionStep = (TransitionStep) pair.component1();
        boolean booleanValue = ((Boolean) pair.component2()).booleanValue();
        KeyguardState keyguardState = transitionStep.to;
        KeyguardState keyguardState2 = KeyguardState.AOD;
        KeyguardState keyguardState3 = transitionStep.from;
        if (keyguardState == keyguardState2) {
            int i = WhenMappings.$EnumSwitchMapping$0[keyguardState3.ordinal()];
            if (i != 1 && (i != 2 || booleanValue)) {
                return burnInOffsets2;
            }
        } else {
            if (keyguardState3 != keyguardState2) {
                return new BurnInOffsets(0, 0, 0.0f);
            }
            if (WhenMappings.$EnumSwitchMapping$0[keyguardState.ordinal()] != 2) {
                return new BurnInOffsets(0, 0, 0.0f);
            }
        }
        return burnInOffsets;
    }
}
