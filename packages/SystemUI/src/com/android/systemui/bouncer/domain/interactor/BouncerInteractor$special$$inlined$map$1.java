package com.android.systemui.bouncer.domain.interactor;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class BouncerInteractor$special$$inlined$map$1 implements Flow {
    public final /* synthetic */ Flow $this_unsafeTransform$inlined;
    public final /* synthetic */ BouncerInteractor this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.bouncer.domain.interactor.BouncerInteractor$special$$inlined$map$1$2, reason: invalid class name */
    public final class AnonymousClass2 implements FlowCollector {
        public final /* synthetic */ FlowCollector $this_unsafeFlow;
        public final /* synthetic */ BouncerInteractor this$0;

        /* renamed from: com.android.systemui.bouncer.domain.interactor.BouncerInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
        public final class AnonymousClass1 extends ContinuationImpl {
            Object L$0;
            int label;
            /* synthetic */ Object result;

            public AnonymousClass1(Continuation continuation) {
                super(continuation);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                this.result = obj;
                this.label |= Integer.MIN_VALUE;
                return AnonymousClass2.this.emit(null, this);
            }
        }

        public AnonymousClass2(FlowCollector flowCollector, BouncerInteractor bouncerInteractor) {
            this.$this_unsafeFlow = flowCollector;
            this.this$0 = bouncerInteractor;
        }

        /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
        /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
        @Override // kotlinx.coroutines.flow.FlowCollector
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object emit(java.lang.Object r6, kotlin.coroutines.Continuation r7) {
            /*
                r5 = this;
                boolean r0 = r7 instanceof com.android.systemui.bouncer.domain.interactor.BouncerInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                if (r0 == 0) goto L13
                r0 = r7
                com.android.systemui.bouncer.domain.interactor.BouncerInteractor$special$$inlined$map$1$2$1 r0 = (com.android.systemui.bouncer.domain.interactor.BouncerInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                int r1 = r0.label
                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                r3 = r1 & r2
                if (r3 == 0) goto L13
                int r1 = r1 - r2
                r0.label = r1
                goto L18
            L13:
                com.android.systemui.bouncer.domain.interactor.BouncerInteractor$special$$inlined$map$1$2$1 r0 = new com.android.systemui.bouncer.domain.interactor.BouncerInteractor$special$$inlined$map$1$2$1
                r0.<init>(r7)
            L18:
                java.lang.Object r7 = r0.result
                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                int r2 = r0.label
                r3 = 1
                if (r2 == 0) goto L2f
                if (r2 != r3) goto L27
                kotlin.ResultKt.throwOnFailure(r7)
                goto L6d
            L27:
                java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                r5.<init>(r6)
                throw r5
            L2f:
                kotlin.ResultKt.throwOnFailure(r7)
                com.android.systemui.authentication.shared.model.AuthenticationMethodModel r6 = (com.android.systemui.authentication.shared.model.AuthenticationMethodModel) r6
                com.android.systemui.authentication.shared.model.AuthenticationMethodModel$Sim r7 = com.android.systemui.authentication.shared.model.AuthenticationMethodModel.Sim.INSTANCE
                boolean r6 = kotlin.jvm.internal.Intrinsics.areEqual(r6, r7)
                r7 = 0
                if (r6 == 0) goto L3e
                goto L5e
            L3e:
                com.android.systemui.bouncer.domain.interactor.BouncerInteractor r6 = r5.this$0
                com.android.systemui.bouncer.data.repository.BouncerRepository r6 = r6.repository
                android.content.Context r2 = r6.applicationContext
                android.content.res.Resources r2 = r2.getResources()
                r4 = 2131034128(0x7f050010, float:1.7678765E38)
                boolean r2 = r2.getBoolean(r4)
                if (r2 == 0) goto L5e
                com.android.systemui.flags.ResourceBooleanFlag r2 = com.android.systemui.flags.Flags.FULL_SCREEN_USER_SWITCHER
                com.android.systemui.flags.FeatureFlagsClassic r6 = r6.flags
                com.android.systemui.flags.FeatureFlagsClassicRelease r6 = (com.android.systemui.flags.FeatureFlagsClassicRelease) r6
                boolean r6 = r6.isEnabled(r2)
                if (r6 == 0) goto L5e
                r7 = r3
            L5e:
                java.lang.Boolean r6 = java.lang.Boolean.valueOf(r7)
                r0.label = r3
                kotlinx.coroutines.flow.FlowCollector r5 = r5.$this_unsafeFlow
                java.lang.Object r5 = r5.emit(r6, r0)
                if (r5 != r1) goto L6d
                return r1
            L6d:
                kotlin.Unit r5 = kotlin.Unit.INSTANCE
                return r5
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.domain.interactor.BouncerInteractor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
        }
    }

    public BouncerInteractor$special$$inlined$map$1(Flow flow, BouncerInteractor bouncerInteractor) {
        this.$this_unsafeTransform$inlined = flow;
        this.this$0 = bouncerInteractor;
    }

    @Override // kotlinx.coroutines.flow.Flow
    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
        Object collect = this.$this_unsafeTransform$inlined.collect(new AnonymousClass2(flowCollector, this.this$0), continuation);
        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
    }
}
