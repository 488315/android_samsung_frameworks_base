package com.android.systemui.deviceentry.domain.interactor;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DeviceEntryFaceAuthStatusInteractor$special$$inlined$transform$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Flow $this_transform;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ DeviceEntryFaceAuthStatusInteractor this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthStatusInteractor$special$$inlined$transform$1$1, reason: invalid class name */
    public final class AnonymousClass1 implements FlowCollector {
        public final /* synthetic */ FlowCollector $$this$flow;
        public final /* synthetic */ DeviceEntryFaceAuthStatusInteractor this$0;

        /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthStatusInteractor$special$$inlined$transform$1$1$1, reason: invalid class name and collision with other inner class name */
        public final class C00931 extends ContinuationImpl {
            int label;
            /* synthetic */ Object result;

            public C00931(Continuation continuation) {
                super(continuation);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                this.result = obj;
                this.label |= Integer.MIN_VALUE;
                return AnonymousClass1.this.emit(null, this);
            }
        }

        public AnonymousClass1(FlowCollector flowCollector, DeviceEntryFaceAuthStatusInteractor deviceEntryFaceAuthStatusInteractor) {
            this.this$0 = deviceEntryFaceAuthStatusInteractor;
            this.$$this$flow = flowCollector;
        }

        /* JADX WARN: Code restructure failed: missing block: B:29:0x007f, code lost:
        
            if (r8.emit(r9, r0) == r1) goto L33;
         */
        /* JADX WARN: Code restructure failed: missing block: B:30:0x008a, code lost:
        
            return r1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:32:0x0088, code lost:
        
            if (r8.emit(r9, r0) == r1) goto L33;
         */
        /* JADX WARN: Removed duplicated region for block: B:16:0x0033  */
        /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
        @Override // kotlinx.coroutines.flow.FlowCollector
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object emit(java.lang.Object r9, kotlin.coroutines.Continuation r10) {
            /*
                r8 = this;
                boolean r0 = r10 instanceof com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthStatusInteractor$special$$inlined$transform$1.AnonymousClass1.C00931
                if (r0 == 0) goto L13
                r0 = r10
                com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthStatusInteractor$special$$inlined$transform$1$1$1 r0 = (com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthStatusInteractor$special$$inlined$transform$1.AnonymousClass1.C00931) r0
                int r1 = r0.label
                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                r3 = r1 & r2
                if (r3 == 0) goto L13
                int r1 = r1 - r2
                r0.label = r1
                goto L18
            L13:
                com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthStatusInteractor$special$$inlined$transform$1$1$1 r0 = new com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthStatusInteractor$special$$inlined$transform$1$1$1
                r0.<init>(r10)
            L18:
                java.lang.Object r10 = r0.result
                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                int r2 = r0.label
                r3 = 2
                r4 = 1
                if (r2 == 0) goto L33
                if (r2 == r4) goto L2f
                if (r2 != r3) goto L27
                goto L2f
            L27:
                java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
                r8.<init>(r9)
                throw r8
            L2f:
                kotlin.ResultKt.throwOnFailure(r10)
                goto L8b
            L33:
                kotlin.ResultKt.throwOnFailure(r10)
                com.android.systemui.deviceentry.shared.model.FaceAuthenticationStatus r9 = (com.android.systemui.deviceentry.shared.model.FaceAuthenticationStatus) r9
                boolean r10 = r9 instanceof com.android.systemui.deviceentry.shared.model.AcquiredFaceAuthenticationStatus
                com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthStatusInteractor r2 = r8.this$0
                if (r10 == 0) goto L4e
                r10 = r9
                com.android.systemui.deviceentry.shared.model.AcquiredFaceAuthenticationStatus r10 = (com.android.systemui.deviceentry.shared.model.AcquiredFaceAuthenticationStatus) r10
                int r5 = r10.acquiredInfo
                r6 = 20
                if (r5 != r6) goto L4e
                com.android.systemui.biometrics.FaceHelpMessageDebouncer r5 = r2.faceHelpMessageDebouncer
                long r6 = r10.createdAt
                r5.startNewFaceAuthSession(r6)
            L4e:
                boolean r10 = r9 instanceof com.android.systemui.deviceentry.shared.model.HelpFaceAuthenticationStatus
                kotlinx.coroutines.flow.FlowCollector r8 = r8.$$this$flow
                if (r10 == 0) goto L82
                java.util.Set r10 = r2.faceAcquiredInfoIgnoreList
                com.android.systemui.deviceentry.shared.model.HelpFaceAuthenticationStatus r9 = (com.android.systemui.deviceentry.shared.model.HelpFaceAuthenticationStatus) r9
                int r3 = r9.msgId
                java.lang.Integer r5 = new java.lang.Integer
                r5.<init>(r3)
                boolean r10 = r10.contains(r5)
                com.android.systemui.biometrics.FaceHelpMessageDebouncer r2 = r2.faceHelpMessageDebouncer
                if (r10 != 0) goto L71
                java.util.List r10 = r2.helpFaceAuthStatuses
                java.util.ArrayList r10 = (java.util.ArrayList) r10
                r10.add(r9)
                java.util.Objects.toString(r9)
            L71:
                long r9 = r9.createdAt
                com.android.systemui.deviceentry.shared.model.HelpFaceAuthenticationStatus r9 = r2.getMessageToShow(r9)
                if (r9 == 0) goto L8b
                r0.label = r4
                java.lang.Object r8 = r8.emit(r9, r0)
                if (r8 != r1) goto L8b
                goto L8a
            L82:
                r0.label = r3
                java.lang.Object r8 = r8.emit(r9, r0)
                if (r8 != r1) goto L8b
            L8a:
                return r1
            L8b:
                kotlin.Unit r8 = kotlin.Unit.INSTANCE
                return r8
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthStatusInteractor$special$$inlined$transform$1.AnonymousClass1.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceEntryFaceAuthStatusInteractor$special$$inlined$transform$1(Flow flow, Continuation continuation, DeviceEntryFaceAuthStatusInteractor deviceEntryFaceAuthStatusInteractor) {
        super(2, continuation);
        this.$this_transform = flow;
        this.this$0 = deviceEntryFaceAuthStatusInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DeviceEntryFaceAuthStatusInteractor$special$$inlined$transform$1 deviceEntryFaceAuthStatusInteractor$special$$inlined$transform$1 = new DeviceEntryFaceAuthStatusInteractor$special$$inlined$transform$1(this.$this_transform, continuation, this.this$0);
        deviceEntryFaceAuthStatusInteractor$special$$inlined$transform$1.L$0 = obj;
        return deviceEntryFaceAuthStatusInteractor$special$$inlined$transform$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DeviceEntryFaceAuthStatusInteractor$special$$inlined$transform$1) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Flow flow = this.$this_transform;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(flowCollector, this.this$0);
            this.label = 1;
            if (flow.collect(anonymousClass1, this) == coroutineSingletons) {
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
