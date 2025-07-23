package com.android.systemui.qs.pipeline.domain.interactor;

import com.android.systemui.qs.pipeline.data.model.RestoreData;
import com.android.systemui.qs.pipeline.data.repository.AutoAddRepository;
import com.android.systemui.qs.pipeline.data.repository.AutoAddSettingRepository;
import com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__LimitKt$take$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__MergeKt;
import kotlinx.coroutines.flow.FlowKt__MergeKt$flatMapConcat$$inlined$map$1;
import kotlinx.coroutines.flow.FlowKt__MergeKt$flattenConcat$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class RestoreReconciliationInteractor$start$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ RestoreReconciliationInteractor this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.qs.pipeline.domain.interactor.RestoreReconciliationInteractor$start$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ RestoreReconciliationInteractor this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(RestoreReconciliationInteractor restoreReconciliationInteractor, Continuation continuation) {
            super(2, continuation);
            this.this$0 = restoreReconciliationInteractor;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((RestoreData) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            final RestoreData restoreData;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                RestoreData restoreData2 = (RestoreData) this.L$0;
                AutoAddRepository autoAddRepository = this.this$0.autoAddRepository;
                int i2 = restoreData2.userId;
                this.L$0 = restoreData2;
                this.label = 1;
                Object autoAddedTiles = ((AutoAddSettingRepository) autoAddRepository).autoAddedTiles(i2, this);
                if (autoAddedTiles == coroutineSingletons) {
                    return coroutineSingletons;
                }
                obj = autoAddedTiles;
                restoreData = restoreData2;
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                restoreData = (RestoreData) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            final FlowKt__LimitKt$take$$inlined$unsafeFlow$1 take = FlowKt.take((Flow) obj, 1);
            return new Flow() { // from class: com.android.systemui.qs.pipeline.domain.interactor.RestoreReconciliationInteractor$start$1$1$invokeSuspend$$inlined$map$1

                /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                /* renamed from: com.android.systemui.qs.pipeline.domain.interactor.RestoreReconciliationInteractor$start$1$1$invokeSuspend$$inlined$map$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ RestoreData $data$inlined;
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* renamed from: com.android.systemui.qs.pipeline.domain.interactor.RestoreReconciliationInteractor$start$1$1$invokeSuspend$$inlined$map$1$2$1, reason: invalid class name */
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

                    public AnonymousClass2(FlowCollector flowCollector, RestoreData restoreData) {
                        this.$this_unsafeFlow = flowCollector;
                        this.$data$inlined = restoreData;
                    }

                    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                        /*
                            r4 = this;
                            boolean r0 = r6 instanceof com.android.systemui.qs.pipeline.domain.interactor.RestoreReconciliationInteractor$start$1$1$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1
                            if (r0 == 0) goto L13
                            r0 = r6
                            com.android.systemui.qs.pipeline.domain.interactor.RestoreReconciliationInteractor$start$1$1$invokeSuspend$$inlined$map$1$2$1 r0 = (com.android.systemui.qs.pipeline.domain.interactor.RestoreReconciliationInteractor$start$1$1$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                            int r1 = r0.label
                            r2 = -2147483648(0xffffffff80000000, float:-0.0)
                            r3 = r1 & r2
                            if (r3 == 0) goto L13
                            int r1 = r1 - r2
                            r0.label = r1
                            goto L18
                        L13:
                            com.android.systemui.qs.pipeline.domain.interactor.RestoreReconciliationInteractor$start$1$1$invokeSuspend$$inlined$map$1$2$1 r0 = new com.android.systemui.qs.pipeline.domain.interactor.RestoreReconciliationInteractor$start$1$1$invokeSuspend$$inlined$map$1$2$1
                            r0.<init>(r6)
                        L18:
                            java.lang.Object r6 = r0.result
                            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                            int r2 = r0.label
                            r3 = 1
                            if (r2 == 0) goto L2f
                            if (r2 != r3) goto L27
                            kotlin.ResultKt.throwOnFailure(r6)
                            goto L46
                        L27:
                            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                            r4.<init>(r5)
                            throw r4
                        L2f:
                            kotlin.ResultKt.throwOnFailure(r6)
                            java.util.Set r5 = (java.util.Set) r5
                            kotlin.Pair r6 = new kotlin.Pair
                            com.android.systemui.qs.pipeline.data.model.RestoreData r2 = r4.$data$inlined
                            r6.<init>(r2, r5)
                            r0.label = r3
                            kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                            java.lang.Object r4 = r4.emit(r6, r0)
                            if (r4 != r1) goto L46
                            return r1
                        L46:
                            kotlin.Unit r4 = kotlin.Unit.INSTANCE
                            return r4
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.pipeline.domain.interactor.RestoreReconciliationInteractor$start$1$1$invokeSuspend$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                    Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, restoreData), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                }
            };
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.qs.pipeline.domain.interactor.RestoreReconciliationInteractor$start$1$2, reason: invalid class name */
    public final class AnonymousClass2 implements FlowCollector {
        public final /* synthetic */ RestoreReconciliationInteractor this$0;

        public AnonymousClass2(RestoreReconciliationInteractor restoreReconciliationInteractor) {
            this.this$0 = restoreReconciliationInteractor;
        }

        /* JADX WARN: Code restructure failed: missing block: B:26:0x0188, code lost:
        
            if (r13 == r1) goto L62;
         */
        /* JADX WARN: Code restructure failed: missing block: B:47:0x0125, code lost:
        
            if (r13 != r1) goto L44;
         */
        /* JADX WARN: Removed duplicated region for block: B:16:0x013b  */
        /* JADX WARN: Removed duplicated region for block: B:33:0x019f  */
        /* JADX WARN: Removed duplicated region for block: B:42:0x010b  */
        /* JADX WARN: Removed duplicated region for block: B:48:0x0123  */
        /* JADX WARN: Removed duplicated region for block: B:53:0x00a2  */
        /* JADX WARN: Removed duplicated region for block: B:56:0x00d6  */
        /* JADX WARN: Removed duplicated region for block: B:59:0x007f  */
        /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:26:0x0188 -> B:13:0x018b). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:55:0x00bf -> B:50:0x00c1). Please report as a decompilation issue!!! */
        @Override // kotlinx.coroutines.flow.FlowCollector
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object emit(kotlin.Pair r12, kotlin.coroutines.Continuation r13) {
            /*
                Method dump skipped, instructions count: 418
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.pipeline.domain.interactor.RestoreReconciliationInteractor$start$1.AnonymousClass2.emit(kotlin.Pair, kotlin.coroutines.Continuation):java.lang.Object");
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RestoreReconciliationInteractor$start$1(RestoreReconciliationInteractor restoreReconciliationInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = restoreReconciliationInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new RestoreReconciliationInteractor$start$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((RestoreReconciliationInteractor$start$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            RestoreReconciliationInteractor restoreReconciliationInteractor = this.this$0;
            FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1 = ((QSSettingsRestoredBroadcastRepository) restoreReconciliationInteractor.qsSettingsRestoredRepository).restoreData;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(restoreReconciliationInteractor, null);
            int i2 = FlowKt__MergeKt.$r8$clinit;
            FlowKt__MergeKt$flattenConcat$$inlined$unsafeFlow$1 flowKt__MergeKt$flattenConcat$$inlined$unsafeFlow$1 = new FlowKt__MergeKt$flattenConcat$$inlined$unsafeFlow$1(new FlowKt__MergeKt$flatMapConcat$$inlined$map$1(flowKt__TransformKt$onEach$$inlined$unsafeTransform$1, anonymousClass1));
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.this$0);
            this.label = 1;
            if (flowKt__MergeKt$flattenConcat$$inlined$unsafeFlow$1.collect(anonymousClass2, this) == coroutineSingletons) {
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
