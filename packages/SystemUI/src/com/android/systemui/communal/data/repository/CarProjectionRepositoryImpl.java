package com.android.systemui.communal.data.repository;

import android.app.UiModeManager;
import com.android.systemui.util.kotlin.FlowKt$emitOnStart$1;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CarProjectionRepositoryImpl implements CarProjectionRepository {
    public final CoroutineDispatcher bgDispatcher;
    public final Flow projectionActive;
    public final UiModeManager uiModeManager;

    public CarProjectionRepositoryImpl(UiModeManager uiModeManager, CoroutineDispatcher coroutineDispatcher) {
        this.uiModeManager = uiModeManager;
        this.bgDispatcher = coroutineDispatcher;
        final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new FlowKt$emitOnStart$1(null), FlowConflatedKt.conflatedCallbackFlow(new CarProjectionRepositoryImpl$projectionActive$1(this, null)));
        this.projectionActive = FlowKt.flowOn(new Flow() { // from class: com.android.systemui.communal.data.repository.CarProjectionRepositoryImpl$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.communal.data.repository.CarProjectionRepositoryImpl$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ CarProjectionRepositoryImpl this$0;

                /* renamed from: com.android.systemui.communal.data.repository.CarProjectionRepositoryImpl$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, CarProjectionRepositoryImpl carProjectionRepositoryImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = carProjectionRepositoryImpl;
                }

                /* JADX WARN: Code restructure failed: missing block: B:18:0x0062, code lost:
                
                    if (r6.emit(r8, r0) != r1) goto L23;
                 */
                /* JADX WARN: Removed duplicated region for block: B:20:0x003b  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r7, kotlin.coroutines.Continuation r8) {
                    /*
                        r6 = this;
                        boolean r0 = r8 instanceof com.android.systemui.communal.data.repository.CarProjectionRepositoryImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r8
                        com.android.systemui.communal.data.repository.CarProjectionRepositoryImpl$special$$inlined$map$1$2$1 r0 = (com.android.systemui.communal.data.repository.CarProjectionRepositoryImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.communal.data.repository.CarProjectionRepositoryImpl$special$$inlined$map$1$2$1 r0 = new com.android.systemui.communal.data.repository.CarProjectionRepositoryImpl$special$$inlined$map$1$2$1
                        r0.<init>(r8)
                    L18:
                        java.lang.Object r8 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 0
                        r4 = 2
                        r5 = 1
                        if (r2 == 0) goto L3b
                        if (r2 == r5) goto L33
                        if (r2 != r4) goto L2b
                        kotlin.ResultKt.throwOnFailure(r8)
                        goto L65
                    L2b:
                        java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                        java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                        r6.<init>(r7)
                        throw r6
                    L33:
                        java.lang.Object r6 = r0.L$0
                        kotlinx.coroutines.flow.FlowCollector r6 = (kotlinx.coroutines.flow.FlowCollector) r6
                        kotlin.ResultKt.throwOnFailure(r8)
                        goto L5a
                    L3b:
                        kotlin.ResultKt.throwOnFailure(r8)
                        kotlin.Unit r7 = (kotlin.Unit) r7
                        kotlinx.coroutines.flow.FlowCollector r7 = r6.$this_unsafeFlow
                        r0.L$0 = r7
                        r0.label = r5
                        com.android.systemui.communal.data.repository.CarProjectionRepositoryImpl r6 = r6.this$0
                        r6.getClass()
                        com.android.systemui.communal.data.repository.CarProjectionRepositoryImpl$isProjectionActive$2 r8 = new com.android.systemui.communal.data.repository.CarProjectionRepositoryImpl$isProjectionActive$2
                        r8.<init>(r6, r3)
                        kotlinx.coroutines.CoroutineDispatcher r6 = r6.bgDispatcher
                        java.lang.Object r8 = kotlinx.coroutines.BuildersKt.withContext(r6, r8, r0)
                        if (r8 != r1) goto L59
                        goto L64
                    L59:
                        r6 = r7
                    L5a:
                        r0.L$0 = r3
                        r0.label = r4
                        java.lang.Object r6 = r6.emit(r8, r0)
                        if (r6 != r1) goto L65
                    L64:
                        return r1
                    L65:
                        kotlin.Unit r6 = kotlin.Unit.INSTANCE
                        return r6
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.data.repository.CarProjectionRepositoryImpl$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineDispatcher);
    }
}
