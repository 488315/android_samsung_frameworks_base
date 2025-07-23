package com.android.systemui.qs.composefragment.viewmodel;

import androidx.compose.runtime.SnapshotStateKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.SafeFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class QSFragmentComposeViewModel$hydrateMediaDisappearParameters$2 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ QSFragmentComposeViewModel this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$hydrateMediaDisappearParameters$2$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ QSFragmentComposeViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(QSFragmentComposeViewModel qSFragmentComposeViewModel, Continuation continuation) {
            super(2, continuation);
            this.this$0 = qSFragmentComposeViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                SafeFlow snapshotFlow = SnapshotStateKt.snapshotFlow(new QSFragmentComposeViewModel$$ExternalSyntheticLambda0(this.this$0, 17));
                final QSFragmentComposeViewModel qSFragmentComposeViewModel = this.this$0;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel.hydrateMediaDisappearParameters.2.1.2
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        QSFragmentComposeViewModelKt.access$applyDisappearParameters(QSFragmentComposeViewModel.this.qqsMediaHost, ((Boolean) obj2).booleanValue());
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (snapshotFlow.collect(flowCollector, this) == coroutineSingletons) {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$hydrateMediaDisappearParameters$2$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ QSFragmentComposeViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(QSFragmentComposeViewModel qSFragmentComposeViewModel, Continuation continuation) {
            super(2, continuation);
            this.this$0 = qSFragmentComposeViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass2(this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                SafeFlow snapshotFlow = SnapshotStateKt.snapshotFlow(new QSFragmentComposeViewModel$$ExternalSyntheticLambda0(this.this$0, 18));
                final QSFragmentComposeViewModel qSFragmentComposeViewModel = this.this$0;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel.hydrateMediaDisappearParameters.2.2.2
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        QSFragmentComposeViewModelKt.access$applyDisappearParameters(QSFragmentComposeViewModel.this.qsMediaHost, ((Boolean) obj2).booleanValue());
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (snapshotFlow.collect(flowCollector, this) == coroutineSingletons) {
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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public QSFragmentComposeViewModel$hydrateMediaDisappearParameters$2(QSFragmentComposeViewModel qSFragmentComposeViewModel, Continuation continuation) {
        super(2, continuation);
        this.this$0 = qSFragmentComposeViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        QSFragmentComposeViewModel$hydrateMediaDisappearParameters$2 qSFragmentComposeViewModel$hydrateMediaDisappearParameters$2 = new QSFragmentComposeViewModel$hydrateMediaDisappearParameters$2(this.this$0, continuation);
        qSFragmentComposeViewModel$hydrateMediaDisappearParameters$2.L$0 = obj;
        return qSFragmentComposeViewModel$hydrateMediaDisappearParameters$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((QSFragmentComposeViewModel$hydrateMediaDisappearParameters$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass1(this.this$0, null), 7);
        return CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass2(this.this$0, null), 7);
    }
}
