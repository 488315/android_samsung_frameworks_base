package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.domain.interactor.FromAodTransitionInteractor;
import com.android.systemui.keyguard.shared.model.BiometricUnlockMode;
import com.android.systemui.keyguard.shared.model.BiometricUnlockModel;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionModeOnCanceled;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.android.systemui.power.shared.model.WakefulnessModel;
import com.android.systemui.util.kotlin.Utils;
import kotlin.ResultKt;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* loaded from: classes2.dex */
final class FromAodTransitionInteractor$listenForAodToAwake$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ FromAodTransitionInteractor this$0;

    /* renamed from: com.android.systemui.keyguard.domain.interactor.FromAodTransitionInteractor$listenForAodToAwake$1$2, reason: invalid class name */
    public final class AnonymousClass2 implements FlowCollector {
        public final /* synthetic */ FromAodTransitionInteractor this$0;

        public AnonymousClass2(FromAodTransitionInteractor fromAodTransitionInteractor) {
            this.this$0 = fromAodTransitionInteractor;
        }

        /* JADX WARN: Code restructure failed: missing block: B:52:0x018a, code lost:
        
            if (com.android.systemui.keyguard.domain.interactor.TransitionInteractor.startTransitionTo$default(r9.this$0, r2, null, r4, "canWakeDirectlyToGone = true", r6, 2) == r0) goto L73;
         */
        /* JADX WARN: Code restructure failed: missing block: B:66:0x01ba, code lost:
        
            if (com.android.systemui.keyguard.domain.interactor.TransitionInteractor.startTransitionTo$default(r9.this$0, r2, null, r4, "listen for aod to awake", r6, 2) == r0) goto L73;
         */
        /* JADX WARN: Code restructure failed: missing block: B:72:0x01db, code lost:
        
            if (com.android.systemui.keyguard.domain.interactor.TransitionInteractor.startTransitionTo$default(r9.this$0, r2, null, null, "waking up and isOccluded=true", r6, 6) == r0) goto L73;
         */
        /* JADX WARN: Removed duplicated region for block: B:44:0x014a  */
        /* JADX WARN: Removed duplicated region for block: B:51:0x0171  */
        /* JADX WARN: Removed duplicated region for block: B:56:0x0190  */
        /* JADX WARN: Removed duplicated region for block: B:8:0x0014  */
        @Override // kotlinx.coroutines.flow.FlowCollector
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object emit(Triple triple, Continuation continuation) {
            FromAodTransitionInteractor$listenForAodToAwake$1$2$emit$1 fromAodTransitionInteractor$listenForAodToAwake$1$2$emit$1;
            boolean zBooleanValue;
            TransitionStep transitionStep;
            boolean z;
            BiometricUnlockMode biometricUnlockMode;
            AnonymousClass2 anonymousClass2;
            boolean z2;
            BiometricUnlockMode biometricUnlockMode2;
            if (continuation instanceof FromAodTransitionInteractor$listenForAodToAwake$1$2$emit$1) {
                fromAodTransitionInteractor$listenForAodToAwake$1$2$emit$1 = (FromAodTransitionInteractor$listenForAodToAwake$1$2$emit$1) continuation;
                int i = fromAodTransitionInteractor$listenForAodToAwake$1$2$emit$1.label;
                if ((i & Integer.MIN_VALUE) != 0) {
                    fromAodTransitionInteractor$listenForAodToAwake$1$2$emit$1.label = i - Integer.MIN_VALUE;
                } else {
                    fromAodTransitionInteractor$listenForAodToAwake$1$2$emit$1 = new FromAodTransitionInteractor$listenForAodToAwake$1$2$emit$1(this, continuation);
                }
            }
            FromAodTransitionInteractor$listenForAodToAwake$1$2$emit$1 fromAodTransitionInteractor$listenForAodToAwake$1$2$emit$12 = fromAodTransitionInteractor$listenForAodToAwake$1$2$emit$1;
            Object objMaybeHandleInsecurePowerGesture = fromAodTransitionInteractor$listenForAodToAwake$1$2$emit$12.result;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i2 = fromAodTransitionInteractor$listenForAodToAwake$1$2$emit$12.label;
            boolean z3 = false;
            if (i2 == 0) {
                ResultKt.throwOnFailure(objMaybeHandleInsecurePowerGesture);
                WakefulnessModel wakefulnessModel = (WakefulnessModel) triple.component1();
                TransitionStep transitionStep2 = (TransitionStep) triple.component2();
                boolean zBooleanValue2 = ((Boolean) triple.component3()).booleanValue();
                FromAodTransitionInteractor fromAodTransitionInteractor = this.this$0;
                boolean zBooleanValue3 = ((Boolean) fromAodTransitionInteractor.keyguardInteractor.isKeyguardOccluded.getValue()).booleanValue();
                KeyguardInteractor keyguardInteractor = fromAodTransitionInteractor.keyguardInteractor;
                BiometricUnlockMode biometricUnlockMode3 = ((BiometricUnlockModel) keyguardInteractor.biometricUnlockState.$$delegate_0.getValue()).mode;
                zBooleanValue = ((Boolean) keyguardInteractor.primaryBouncerShowing.$$delegate_0.getValue()).booleanValue();
                boolean zBooleanValue4 = ((Boolean) fromAodTransitionInteractor.communalSettingsInteractor.autoOpenEnabled.$$delegate_0.getValue()).booleanValue();
                fromAodTransitionInteractor$listenForAodToAwake$1$2$emit$12.L$0 = this;
                fromAodTransitionInteractor$listenForAodToAwake$1$2$emit$12.L$1 = wakefulnessModel;
                fromAodTransitionInteractor$listenForAodToAwake$1$2$emit$12.L$2 = transitionStep2;
                fromAodTransitionInteractor$listenForAodToAwake$1$2$emit$12.L$3 = biometricUnlockMode3;
                fromAodTransitionInteractor$listenForAodToAwake$1$2$emit$12.Z$0 = zBooleanValue2;
                fromAodTransitionInteractor$listenForAodToAwake$1$2$emit$12.Z$1 = zBooleanValue3;
                fromAodTransitionInteractor$listenForAodToAwake$1$2$emit$12.Z$2 = zBooleanValue;
                fromAodTransitionInteractor$listenForAodToAwake$1$2$emit$12.Z$3 = zBooleanValue4;
                fromAodTransitionInteractor$listenForAodToAwake$1$2$emit$12.label = 1;
                objMaybeHandleInsecurePowerGesture = fromAodTransitionInteractor.maybeHandleInsecurePowerGesture(fromAodTransitionInteractor$listenForAodToAwake$1$2$emit$12);
                if (objMaybeHandleInsecurePowerGesture != coroutineSingletons) {
                    transitionStep = transitionStep2;
                    z = zBooleanValue3;
                    biometricUnlockMode = biometricUnlockMode3;
                }
                return coroutineSingletons;
            }
            if (i2 != 1) {
                if (i2 != 2) {
                    if (i2 == 3) {
                        ResultKt.throwOnFailure(objMaybeHandleInsecurePowerGesture);
                        return Unit.INSTANCE;
                    }
                    if (i2 == 4) {
                        ResultKt.throwOnFailure(objMaybeHandleInsecurePowerGesture);
                        return Unit.INSTANCE;
                    }
                    if (i2 != 5) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(objMaybeHandleInsecurePowerGesture);
                    return Unit.INSTANCE;
                }
                boolean z4 = fromAodTransitionInteractor$listenForAodToAwake$1$2$emit$12.Z$2;
                z = fromAodTransitionInteractor$listenForAodToAwake$1$2$emit$12.Z$1;
                BiometricUnlockMode biometricUnlockMode4 = (BiometricUnlockMode) fromAodTransitionInteractor$listenForAodToAwake$1$2$emit$12.L$3;
                transitionStep = (TransitionStep) fromAodTransitionInteractor$listenForAodToAwake$1$2$emit$12.L$2;
                anonymousClass2 = (AnonymousClass2) fromAodTransitionInteractor$listenForAodToAwake$1$2$emit$12.L$0;
                ResultKt.throwOnFailure(objMaybeHandleInsecurePowerGesture);
                if (!((Boolean) objMaybeHandleInsecurePowerGesture).booleanValue()) {
                    BiometricUnlockMode.Companion.getClass();
                    if (!BiometricUnlockMode.wakeAndUnlockModes.contains(biometricUnlockMode4) && !z4) {
                        z2 = true;
                    }
                    FromAodTransitionInteractor fromAodTransitionInteractor2 = anonymousClass2.this$0;
                    FromAodTransitionInteractor.Companion companion = FromAodTransitionInteractor.Companion;
                    fromAodTransitionInteractor2.getClass();
                    BiometricUnlockMode.Companion companion2 = BiometricUnlockMode.Companion;
                    KeyguardInteractor keyguardInteractor2 = fromAodTransitionInteractor2.keyguardInteractor;
                    biometricUnlockMode2 = ((BiometricUnlockModel) keyguardInteractor2.biometricUnlockState.$$delegate_0.getValue()).mode;
                    companion2.getClass();
                    if (!BiometricUnlockMode.wakeAndUnlockModes.contains(biometricUnlockMode2) || (!((Boolean) keyguardInteractor2.isKeyguardShowing.getValue()).booleanValue() && ((Boolean) keyguardInteractor2.isKeyguardDismissible.getValue()).booleanValue())) {
                        z3 = true;
                    }
                    anonymousClass2.this$0.communalSettingsInteractor.isV2FlagEnabled();
                    if (!z3) {
                        KeyguardState keyguardState = KeyguardState.GONE;
                        TransitionModeOnCanceled transitionModeOnCanceled = TransitionModeOnCanceled.REVERSE;
                        fromAodTransitionInteractor$listenForAodToAwake$1$2$emit$12.L$0 = null;
                        fromAodTransitionInteractor$listenForAodToAwake$1$2$emit$12.L$1 = null;
                        fromAodTransitionInteractor$listenForAodToAwake$1$2$emit$12.L$2 = null;
                        fromAodTransitionInteractor$listenForAodToAwake$1$2$emit$12.L$3 = null;
                        fromAodTransitionInteractor$listenForAodToAwake$1$2$emit$12.label = 3;
                    } else {
                        if (!z2) {
                            if (z) {
                                KeyguardState keyguardState2 = KeyguardState.OCCLUDED;
                                fromAodTransitionInteractor$listenForAodToAwake$1$2$emit$12.L$0 = null;
                                fromAodTransitionInteractor$listenForAodToAwake$1$2$emit$12.L$1 = null;
                                fromAodTransitionInteractor$listenForAodToAwake$1$2$emit$12.L$2 = null;
                                fromAodTransitionInteractor$listenForAodToAwake$1$2$emit$12.L$3 = null;
                                fromAodTransitionInteractor$listenForAodToAwake$1$2$emit$12.label = 5;
                            }
                            return Unit.INSTANCE;
                        }
                        KeyguardState keyguardState3 = transitionStep.from;
                        KeyguardState keyguardState4 = KeyguardState.LOCKSCREEN;
                        TransitionModeOnCanceled transitionModeOnCanceled2 = keyguardState3 == keyguardState4 ? TransitionModeOnCanceled.REVERSE : keyguardState3 == KeyguardState.GONE ? TransitionModeOnCanceled.RESET : TransitionModeOnCanceled.LAST_VALUE;
                        fromAodTransitionInteractor$listenForAodToAwake$1$2$emit$12.L$0 = null;
                        fromAodTransitionInteractor$listenForAodToAwake$1$2$emit$12.L$1 = null;
                        fromAodTransitionInteractor$listenForAodToAwake$1$2$emit$12.L$2 = null;
                        fromAodTransitionInteractor$listenForAodToAwake$1$2$emit$12.L$3 = null;
                        fromAodTransitionInteractor$listenForAodToAwake$1$2$emit$12.label = 4;
                    }
                    return coroutineSingletons;
                }
                z2 = false;
                FromAodTransitionInteractor fromAodTransitionInteractor22 = anonymousClass2.this$0;
                FromAodTransitionInteractor.Companion companion3 = FromAodTransitionInteractor.Companion;
                fromAodTransitionInteractor22.getClass();
                BiometricUnlockMode.Companion companion22 = BiometricUnlockMode.Companion;
                KeyguardInteractor keyguardInteractor22 = fromAodTransitionInteractor22.keyguardInteractor;
                biometricUnlockMode2 = ((BiometricUnlockModel) keyguardInteractor22.biometricUnlockState.$$delegate_0.getValue()).mode;
                companion22.getClass();
                if (!BiometricUnlockMode.wakeAndUnlockModes.contains(biometricUnlockMode2)) {
                }
                z3 = true;
                anonymousClass2.this$0.communalSettingsInteractor.isV2FlagEnabled();
                if (!z3) {
                }
                return coroutineSingletons;
            }
            boolean z5 = fromAodTransitionInteractor$listenForAodToAwake$1$2$emit$12.Z$2;
            z = fromAodTransitionInteractor$listenForAodToAwake$1$2$emit$12.Z$1;
            biometricUnlockMode = (BiometricUnlockMode) fromAodTransitionInteractor$listenForAodToAwake$1$2$emit$12.L$3;
            transitionStep = (TransitionStep) fromAodTransitionInteractor$listenForAodToAwake$1$2$emit$12.L$2;
            AnonymousClass2 anonymousClass22 = (AnonymousClass2) fromAodTransitionInteractor$listenForAodToAwake$1$2$emit$12.L$0;
            ResultKt.throwOnFailure(objMaybeHandleInsecurePowerGesture);
            zBooleanValue = z5;
            this = anonymousClass22;
            if (!((Boolean) objMaybeHandleInsecurePowerGesture).booleanValue()) {
                if (!z) {
                    BiometricUnlockMode.Companion.getClass();
                    if (!BiometricUnlockMode.wakeAndUnlockModes.contains(biometricUnlockMode) && !zBooleanValue) {
                        anonymousClass2 = this;
                        z2 = true;
                        FromAodTransitionInteractor fromAodTransitionInteractor222 = anonymousClass2.this$0;
                        FromAodTransitionInteractor.Companion companion32 = FromAodTransitionInteractor.Companion;
                        fromAodTransitionInteractor222.getClass();
                        BiometricUnlockMode.Companion companion222 = BiometricUnlockMode.Companion;
                        KeyguardInteractor keyguardInteractor222 = fromAodTransitionInteractor222.keyguardInteractor;
                        biometricUnlockMode2 = ((BiometricUnlockModel) keyguardInteractor222.biometricUnlockState.$$delegate_0.getValue()).mode;
                        companion222.getClass();
                        if (!BiometricUnlockMode.wakeAndUnlockModes.contains(biometricUnlockMode2)) {
                        }
                        z3 = true;
                        anonymousClass2.this$0.communalSettingsInteractor.isV2FlagEnabled();
                        if (!z3) {
                        }
                        return coroutineSingletons;
                    }
                }
                anonymousClass2 = this;
                z2 = false;
                FromAodTransitionInteractor fromAodTransitionInteractor2222 = anonymousClass2.this$0;
                FromAodTransitionInteractor.Companion companion322 = FromAodTransitionInteractor.Companion;
                fromAodTransitionInteractor2222.getClass();
                BiometricUnlockMode.Companion companion2222 = BiometricUnlockMode.Companion;
                KeyguardInteractor keyguardInteractor2222 = fromAodTransitionInteractor2222.keyguardInteractor;
                biometricUnlockMode2 = ((BiometricUnlockModel) keyguardInteractor2222.biometricUnlockState.$$delegate_0.getValue()).mode;
                companion2222.getClass();
                if (!BiometricUnlockMode.wakeAndUnlockModes.contains(biometricUnlockMode2)) {
                }
                z3 = true;
                anonymousClass2.this$0.communalSettingsInteractor.isV2FlagEnabled();
                if (!z3) {
                }
                return coroutineSingletons;
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FromAodTransitionInteractor$listenForAodToAwake$1(FromAodTransitionInteractor fromAodTransitionInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = fromAodTransitionInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new FromAodTransitionInteractor$listenForAodToAwake$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FromAodTransitionInteractor$listenForAodToAwake$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Utils.Companion companion = Utils.Companion;
            FromAodTransitionInteractor fromAodTransitionInteractor = this.this$0;
            TransitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1 transitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1 = new TransitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1(FlowKt.debounce(fromAodTransitionInteractor.powerInteractor.detailedWakefulness, 50L), fromAodTransitionInteractor, new FromAodTransitionInteractor$listenForAodToAwake$1$$ExternalSyntheticLambda0());
            FromAodTransitionInteractor fromAodTransitionInteractor2 = this.this$0;
            Flow flowSample = companion.sample(transitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1, fromAodTransitionInteractor2.transitionInteractor.startedKeyguardTransitionStep, fromAodTransitionInteractor2.wakeToGoneInteractor.canWakeDirectlyToGone);
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.this$0);
            this.label = 1;
            if (flowSample.collect(anonymousClass2, this) == coroutineSingletons) {
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
