package com.android.systemui.education.domain.interactor;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class KeyboardTouchpadEduInteractor$start$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ KeyboardTouchpadEduInteractor this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.education.domain.interactor.KeyboardTouchpadEduInteractor$start$1$2, reason: invalid class name */
    public final class AnonymousClass2 implements FlowCollector {
        public final /* synthetic */ KeyboardTouchpadEduInteractor this$0;

        public AnonymousClass2(KeyboardTouchpadEduInteractor keyboardTouchpadEduInteractor) {
            this.this$0 = keyboardTouchpadEduInteractor;
        }

        /* JADX WARN: Code restructure failed: missing block: B:47:0x0092, code lost:
        
            if (r0 == r6) goto L63;
         */
        /* JADX WARN: Code restructure failed: missing block: B:73:0x010b, code lost:
        
            if (r2 != r6) goto L64;
         */
        /* JADX WARN: Removed duplicated region for block: B:37:0x004c  */
        /* JADX WARN: Removed duplicated region for block: B:44:0x007b  */
        /* JADX WARN: Removed duplicated region for block: B:49:0x0099  */
        /* JADX WARN: Removed duplicated region for block: B:69:0x00dc  */
        /* JADX WARN: Removed duplicated region for block: B:72:0x0109  */
        /* JADX WARN: Removed duplicated region for block: B:74:0x00df  */
        /* JADX WARN: Removed duplicated region for block: B:8:0x0029  */
        @Override // kotlinx.coroutines.flow.FlowCollector
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object emit(com.android.systemui.education.data.model.GestureEduModel r18, kotlin.coroutines.Continuation r19) {
            /*
                Method dump skipped, instructions count: 338
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.education.domain.interactor.KeyboardTouchpadEduInteractor$start$1.AnonymousClass2.emit(com.android.systemui.education.data.model.GestureEduModel, kotlin.coroutines.Continuation):java.lang.Object");
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyboardTouchpadEduInteractor$start$1(KeyboardTouchpadEduInteractor keyboardTouchpadEduInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = keyboardTouchpadEduInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new KeyboardTouchpadEduInteractor$start$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyboardTouchpadEduInteractor$start$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            KeyboardTouchpadEduInteractor keyboardTouchpadEduInteractor = this.this$0;
            ChannelFlowTransformLatest transformLatest = FlowKt.transformLatest(keyboardTouchpadEduInteractor.contextualEducationInteractor.eduDeviceConnectionTimeFlow, new KeyboardTouchpadEduInteractor$start$1$invokeSuspend$$inlined$flatMapLatest$1(null, keyboardTouchpadEduInteractor));
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.this$0);
            this.label = 1;
            if (transformLatest.collect(anonymousClass2, this) == coroutineSingletons) {
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
