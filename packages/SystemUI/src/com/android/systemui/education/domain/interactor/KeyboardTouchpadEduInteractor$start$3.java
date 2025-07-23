package com.android.systemui.education.domain.interactor;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class KeyboardTouchpadEduInteractor$start$3 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ KeyboardTouchpadEduInteractor this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.education.domain.interactor.KeyboardTouchpadEduInteractor$start$3$1, reason: invalid class name */
    public final class AnonymousClass1 implements FlowCollector {
        public final /* synthetic */ KeyboardTouchpadEduInteractor this$0;

        public AnonymousClass1(KeyboardTouchpadEduInteractor keyboardTouchpadEduInteractor) {
            this.this$0 = keyboardTouchpadEduInteractor;
        }

        /* JADX WARN: Code restructure failed: missing block: B:22:0x0080, code lost:
        
            if (r5 == r1) goto L28;
         */
        /* JADX WARN: Code restructure failed: missing block: B:23:0x0082, code lost:
        
            return r1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:29:0x0058, code lost:
        
            if (r7 == r1) goto L28;
         */
        /* JADX WARN: Removed duplicated region for block: B:19:0x0061  */
        /* JADX WARN: Removed duplicated region for block: B:26:0x003a  */
        /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
        @Override // kotlinx.coroutines.flow.FlowCollector
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object emit(com.android.systemui.inputdevice.data.model.UserDeviceConnectionStatus r6, kotlin.coroutines.Continuation r7) {
            /*
                r5 = this;
                boolean r0 = r7 instanceof com.android.systemui.education.domain.interactor.KeyboardTouchpadEduInteractor$start$3$1$emit$1
                if (r0 == 0) goto L13
                r0 = r7
                com.android.systemui.education.domain.interactor.KeyboardTouchpadEduInteractor$start$3$1$emit$1 r0 = (com.android.systemui.education.domain.interactor.KeyboardTouchpadEduInteractor$start$3$1$emit$1) r0
                int r1 = r0.label
                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                r3 = r1 & r2
                if (r3 == 0) goto L13
                int r1 = r1 - r2
                r0.label = r1
                goto L18
            L13:
                com.android.systemui.education.domain.interactor.KeyboardTouchpadEduInteractor$start$3$1$emit$1 r0 = new com.android.systemui.education.domain.interactor.KeyboardTouchpadEduInteractor$start$3$1$emit$1
                r0.<init>(r5, r7)
            L18:
                java.lang.Object r7 = r0.result
                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                int r2 = r0.label
                r3 = 2
                r4 = 1
                if (r2 == 0) goto L3a
                if (r2 == r4) goto L32
                if (r2 != r3) goto L2a
                kotlin.ResultKt.throwOnFailure(r7)
                goto L83
            L2a:
                java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                r5.<init>(r6)
                throw r5
            L32:
                java.lang.Object r5 = r0.L$0
                com.android.systemui.education.domain.interactor.KeyboardTouchpadEduInteractor$start$3$1 r5 = (com.android.systemui.education.domain.interactor.KeyboardTouchpadEduInteractor$start$3.AnonymousClass1) r5
                kotlin.ResultKt.throwOnFailure(r7)
                goto L5b
            L3a:
                kotlin.ResultKt.throwOnFailure(r7)
                boolean r6 = r6.isConnected
                if (r6 == 0) goto L86
                com.android.systemui.education.domain.interactor.KeyboardTouchpadEduInteractor r6 = r5.this$0
                com.android.systemui.education.domain.interactor.ContextualEducationInteractor r6 = r6.contextualEducationInteractor
                r0.L$0 = r5
                r0.label = r4
                com.android.systemui.education.data.repository.ContextualEducationRepository r6 = r6.repository
                com.android.systemui.education.data.repository.UserContextualEducationRepository r6 = (com.android.systemui.education.data.repository.UserContextualEducationRepository) r6
                com.android.systemui.education.data.repository.UserContextualEducationRepository$readEduDeviceConnectionTime$$inlined$map$1 r7 = new com.android.systemui.education.data.repository.UserContextualEducationRepository$readEduDeviceConnectionTime$$inlined$map$1
                kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest r2 = r6.prefData
                r7.<init>(r2, r6)
                java.lang.Object r7 = kotlinx.coroutines.flow.FlowKt.first(r7, r0)
                if (r7 != r1) goto L5b
                goto L82
            L5b:
                com.android.systemui.education.data.model.EduDeviceConnectionTime r7 = (com.android.systemui.education.data.model.EduDeviceConnectionTime) r7
                java.time.Instant r6 = r7.keyboardFirstConnectionTime
                if (r6 != 0) goto L86
                com.android.systemui.education.domain.interactor.KeyboardTouchpadEduInteractor r5 = r5.this$0
                com.android.systemui.education.domain.interactor.ContextualEducationInteractor r5 = r5.contextualEducationInteractor
                r6 = 0
                r0.L$0 = r6
                r0.label = r3
                r5.getClass()
                com.android.systemui.education.domain.interactor.ContextualEducationInteractor$$ExternalSyntheticLambda1 r6 = new com.android.systemui.education.domain.interactor.ContextualEducationInteractor$$ExternalSyntheticLambda1
                r7 = 1
                r6.<init>(r5, r7)
                com.android.systemui.education.data.repository.ContextualEducationRepository r5 = r5.repository
                com.android.systemui.education.data.repository.UserContextualEducationRepository r5 = (com.android.systemui.education.data.repository.UserContextualEducationRepository) r5
                java.lang.Object r5 = r5.updateEduDeviceConnectionTime(r6, r0)
                if (r5 != r1) goto L7e
                goto L80
            L7e:
                kotlin.Unit r5 = kotlin.Unit.INSTANCE
            L80:
                if (r5 != r1) goto L83
            L82:
                return r1
            L83:
                kotlin.Unit r5 = kotlin.Unit.INSTANCE
                return r5
            L86:
                kotlin.Unit r5 = kotlin.Unit.INSTANCE
                return r5
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.education.domain.interactor.KeyboardTouchpadEduInteractor$start$3.AnonymousClass1.emit(com.android.systemui.inputdevice.data.model.UserDeviceConnectionStatus, kotlin.coroutines.Continuation):java.lang.Object");
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyboardTouchpadEduInteractor$start$3(KeyboardTouchpadEduInteractor keyboardTouchpadEduInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = keyboardTouchpadEduInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new KeyboardTouchpadEduInteractor$start$3(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyboardTouchpadEduInteractor$start$3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            KeyboardTouchpadEduInteractor keyboardTouchpadEduInteractor = this.this$0;
            Flow flow = keyboardTouchpadEduInteractor.userInputDeviceRepository.isAnyKeyboardConnectedForUser;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(keyboardTouchpadEduInteractor);
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
