package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class DeviceEntryBackgroundViewModel$alpha$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ KeyguardTransitionInteractor $keyguardTransitionInteractor;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[KeyguardState.values().length];
            try {
                iArr[KeyguardState.GLANCEABLE_HUB.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[KeyguardState.GONE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[KeyguardState.OCCLUDED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[KeyguardState.OFF.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[KeyguardState.DOZING.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[KeyguardState.DREAMING.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[KeyguardState.PRIMARY_BOUNCER.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[KeyguardState.AOD.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[KeyguardState.UNDEFINED.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr[KeyguardState.ALTERNATE_BOUNCER.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr[KeyguardState.LOCKSCREEN.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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

    /* JADX WARN: Code restructure failed: missing block: B:16:0x004d, code lost:
    
        if (r6.emit(r1, r5) == r0) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x005e, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x005c, code lost:
    
        if (r6.emit(r1, r5) == r0) goto L18;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r6) {
        /*
            r5 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r5.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L18
            if (r1 == r3) goto Lc
            if (r1 != r2) goto L10
        Lc:
            kotlin.ResultKt.throwOnFailure(r6)
            goto L5f
        L10:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L18:
            kotlin.ResultKt.throwOnFailure(r6)
            java.lang.Object r6 = r5.L$0
            kotlinx.coroutines.flow.FlowCollector r6 = (kotlinx.coroutines.flow.FlowCollector) r6
            com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor r1 = r5.$keyguardTransitionInteractor
            kotlinx.coroutines.flow.ReadonlyStateFlow r1 = r1.currentKeyguardState
            kotlinx.coroutines.flow.StateFlow r1 = r1.$$delegate_0
            java.util.List r1 = r1.getReplayCache()
            java.lang.Object r1 = kotlin.collections.CollectionsKt___CollectionsKt.last(r1)
            com.android.systemui.keyguard.shared.model.KeyguardState r1 = (com.android.systemui.keyguard.shared.model.KeyguardState) r1
            int[] r4 = com.android.systemui.keyguard.ui.viewmodel.DeviceEntryBackgroundViewModel$alpha$1$1.WhenMappings.$EnumSwitchMapping$0
            int r1 = r1.ordinal()
            r1 = r4[r1]
            switch(r1) {
                case 1: goto L50;
                case 2: goto L50;
                case 3: goto L50;
                case 4: goto L50;
                case 5: goto L50;
                case 6: goto L50;
                case 7: goto L50;
                case 8: goto L50;
                case 9: goto L50;
                case 10: goto L40;
                case 11: goto L40;
                default: goto L3a;
            }
        L3a:
            kotlin.NoWhenBranchMatchedException r5 = new kotlin.NoWhenBranchMatchedException
            r5.<init>()
            throw r5
        L40:
            java.lang.Float r1 = new java.lang.Float
            r3 = 1065353216(0x3f800000, float:1.0)
            r1.<init>(r3)
            r5.label = r2
            java.lang.Object r5 = r6.emit(r1, r5)
            if (r5 != r0) goto L5f
            goto L5e
        L50:
            java.lang.Float r1 = new java.lang.Float
            r2 = 0
            r1.<init>(r2)
            r5.label = r3
            java.lang.Object r5 = r6.emit(r1, r5)
            if (r5 != r0) goto L5f
        L5e:
            return r0
        L5f:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.viewmodel.DeviceEntryBackgroundViewModel$alpha$1$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
