package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

final class DeviceEntryBackgroundViewModel$alpha$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ KeyguardTransitionInteractor $keyguardTransitionInteractor;
    private /* synthetic */ Object L$0;
    int label;

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[KeyguardState.values().length];
            try {
                iArr[KeyguardState.GLANCEABLE_HUB.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[KeyguardState.DREAMING_LOCKSCREEN_HOSTED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[KeyguardState.GONE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[KeyguardState.OCCLUDED.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[KeyguardState.OFF.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[KeyguardState.DOZING.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[KeyguardState.DREAMING.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[KeyguardState.PRIMARY_BOUNCER.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[KeyguardState.AOD.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr[KeyguardState.UNDEFINED.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr[KeyguardState.ALTERNATE_BOUNCER.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                iArr[KeyguardState.LOCKSCREEN.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public DeviceEntryBackgroundViewModel$alpha$1$1(KeyguardTransitionInteractor keyguardTransitionInteractor, Continuation continuation) {
        super(2, continuation);
        this.$keyguardTransitionInteractor = keyguardTransitionInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DeviceEntryBackgroundViewModel$alpha$1$1 deviceEntryBackgroundViewModel$alpha$1$1 = new DeviceEntryBackgroundViewModel$alpha$1$1(this.$keyguardTransitionInteractor, continuation);
        deviceEntryBackgroundViewModel$alpha$1$1.L$0 = obj;
        return deviceEntryBackgroundViewModel$alpha$1$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DeviceEntryBackgroundViewModel$alpha$1$1) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            switch (WhenMappings.$EnumSwitchMapping$0[((KeyguardState) CollectionsKt___CollectionsKt.last(this.$keyguardTransitionInteractor.currentKeyguardState.$$delegate_0.getReplayCache())).ordinal()]) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                    Float f = new Float(0.0f);
                    this.label = 1;
                    if (flowCollector.emit(f, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    break;
                case 11:
                case 12:
                    Float f2 = new Float(1.0f);
                    this.label = 2;
                    if (flowCollector.emit(f2, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    break;
            }
        } else {
            if (i != 1 && i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
