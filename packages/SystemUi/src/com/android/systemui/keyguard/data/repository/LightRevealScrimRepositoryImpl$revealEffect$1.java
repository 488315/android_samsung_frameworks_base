package com.android.systemui.keyguard.data.repository;

import com.android.systemui.keyguard.shared.model.BiometricUnlockModel;
import com.android.systemui.statusbar.LightRevealEffect;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.keyguard.data.repository.LightRevealScrimRepositoryImpl$revealEffect$1", m277f = "LightRevealScrimRepository.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
public final class LightRevealScrimRepositoryImpl$revealEffect$1 extends SuspendLambda implements Function4 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ Object L$2;
    int label;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[BiometricUnlockModel.values().length];
            try {
                iArr[BiometricUnlockModel.WAKE_AND_UNLOCK.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[BiometricUnlockModel.WAKE_AND_UNLOCK_PULSING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[BiometricUnlockModel.WAKE_AND_UNLOCK_FROM_DREAM.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public LightRevealScrimRepositoryImpl$revealEffect$1(Continuation<? super LightRevealScrimRepositoryImpl$revealEffect$1> continuation) {
        super(4, continuation);
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        LightRevealScrimRepositoryImpl$revealEffect$1 lightRevealScrimRepositoryImpl$revealEffect$1 = new LightRevealScrimRepositoryImpl$revealEffect$1((Continuation) obj4);
        lightRevealScrimRepositoryImpl$revealEffect$1.L$0 = (BiometricUnlockModel) obj;
        lightRevealScrimRepositoryImpl$revealEffect$1.L$1 = (LightRevealEffect) obj2;
        lightRevealScrimRepositoryImpl$revealEffect$1.L$2 = (LightRevealEffect) obj3;
        return lightRevealScrimRepositoryImpl$revealEffect$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        BiometricUnlockModel biometricUnlockModel = (BiometricUnlockModel) this.L$0;
        LightRevealEffect lightRevealEffect = (LightRevealEffect) this.L$1;
        LightRevealEffect lightRevealEffect2 = (LightRevealEffect) this.L$2;
        int i = WhenMappings.$EnumSwitchMapping$0[biometricUnlockModel.ordinal()];
        if (i != 1 && i != 2 && i != 3) {
            lightRevealEffect = lightRevealEffect2;
        }
        return lightRevealEffect == null ? LightRevealScrimRepositoryKt.DEFAULT_REVEAL_EFFECT : lightRevealEffect;
    }
}
