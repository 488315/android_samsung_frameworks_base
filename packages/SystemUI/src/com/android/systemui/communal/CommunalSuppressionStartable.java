package com.android.systemui.communal;

import com.android.systemui.CoreStartable;
import com.android.systemui.communal.data.model.SuppressionReason;
import com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor;
import com.android.systemui.log.table.DiffableKt;
import com.android.systemui.log.table.TableLogBuffer;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.internal.CombineKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CommunalSuppressionStartable implements CoreStartable {
    public final CoroutineScope applicationScope;
    public final CoroutineDispatcher bgDispatcher;
    public final CommunalSettingsInteractor communalSettingsInteractor;
    public final Set suppressionFlows;
    public final TableLogBuffer tableLogBuffer;

    public CommunalSuppressionStartable(CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, Set<Flow> set, CommunalSettingsInteractor communalSettingsInteractor, TableLogBuffer tableLogBuffer) {
        this.applicationScope = coroutineScope;
        this.bgDispatcher = coroutineDispatcher;
        this.suppressionFlows = set;
        this.communalSettingsInteractor = communalSettingsInteractor;
        this.tableLogBuffer = tableLogBuffer;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        Flow flow;
        if (this.communalSettingsInteractor.isCommunalFlagEnabled()) {
            final Flow[] flowArr = (Flow[]) CollectionsKt___CollectionsKt.toList(this.suppressionFlows).toArray(new Flow[0]);
            flow = new Flow() { // from class: com.android.systemui.communal.CommunalSuppressionStartable$getSuppressionReasons$$inlined$combine$1

                /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                /* renamed from: com.android.systemui.communal.CommunalSuppressionStartable$getSuppressionReasons$$inlined$combine$1$3, reason: invalid class name */
                public final class AnonymousClass3 extends SuspendLambda implements Function3 {
                    private /* synthetic */ Object L$0;
                    /* synthetic */ Object L$1;
                    int label;

                    public AnonymousClass3(Continuation continuation) {
                        super(3, continuation);
                    }

                    @Override // kotlin.jvm.functions.Function3
                    public final Object invoke(Object obj, Object obj2, Object obj3) {
                        AnonymousClass3 anonymousClass3 = new AnonymousClass3((Continuation) obj3);
                        anonymousClass3.L$0 = (FlowCollector) obj;
                        anonymousClass3.L$1 = (Object[]) obj2;
                        return anonymousClass3.invokeSuspend(Unit.INSTANCE);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                        int i = this.label;
                        if (i == 0) {
                            ResultKt.throwOnFailure(obj);
                            FlowCollector flowCollector = (FlowCollector) this.L$0;
                            List filterNotNull = ArraysKt___ArraysKt.filterNotNull((SuppressionReason[]) ((Object[]) this.L$1));
                            this.label = 1;
                            if (flowCollector.emit(filterNotNull, this) == coroutineSingletons) {
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

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                    final Flow[] flowArr2 = flowArr;
                    Object combineInternal = CombineKt.combineInternal(flowArr2, new Function0() { // from class: com.android.systemui.communal.CommunalSuppressionStartable$getSuppressionReasons$$inlined$combine$1.2
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            return new SuppressionReason[flowArr2.length];
                        }
                    }, new AnonymousClass3(null), flowCollector, continuation);
                    return combineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? combineInternal : Unit.INSTANCE;
                }
            };
        } else {
            flow = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Collections.singletonList(SuppressionReason.ReasonFlagDisabled.INSTANCE));
        }
        FlowKt.launchIn(FlowKt.flowOn(DiffableKt.logDiffsForTable(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(flow, new CommunalSuppressionStartable$start$1(this, null)), this.tableLogBuffer, "", "suppressionReasons", EmptyList.INSTANCE), this.bgDispatcher), this.applicationScope);
    }
}
