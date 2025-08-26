package com.android.systemui.scene.domain.interactor;

import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.scene.data.model.EmptyStack;
import com.android.systemui.scene.data.model.SceneStack;
import com.android.systemui.scene.data.model.StackedNodes;
import com.android.systemui.scene.shared.logger.SceneLogger;
import com.android.systemui.scene.shared.model.SceneContainerConfig;
import java.util.Collections;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes2.dex */
public final class SceneBackInteractor {
    public final StateFlowImpl _backStack;
    public final SceneBackInteractor$special$$inlined$map$1 backScene;
    public final ReadonlyStateFlow backStack;
    public final SceneLogger logger;
    public final SceneContainerConfig sceneContainerConfig;
    public final TableLogBuffer tableLogBuffer;

    /* JADX WARN: Type inference failed for: r5v3, types: [com.android.systemui.scene.domain.interactor.SceneBackInteractor$special$$inlined$map$1] */
    public SceneBackInteractor(SceneLogger sceneLogger, SceneContainerConfig sceneContainerConfig, TableLogBuffer tableLogBuffer) {
        List mutableList;
        this.logger = sceneLogger;
        this.sceneContainerConfig = sceneContainerConfig;
        this.tableLogBuffer = tableLogBuffer;
        SceneKey[] sceneKeyArr = new SceneKey[0];
        SceneStack sceneStack = EmptyStack.INSTANCE;
        if (sceneKeyArr.length == 0) {
            mutableList = EmptyList.INSTANCE;
        } else {
            mutableList = ArraysKt___ArraysKt.toMutableList(sceneKeyArr);
            Collections.reverse(mutableList);
        }
        int size = mutableList.size();
        int i = 0;
        while (i < size) {
            SceneStack stackedNodes = new StackedNodes((SceneKey) mutableList.get(i), sceneStack);
            i++;
            sceneStack = stackedNodes;
        }
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(sceneStack);
        this._backStack = stateFlowImplMutableStateFlow;
        final ReadonlyStateFlow readonlyStateFlowAsStateFlow = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
        this.backStack = readonlyStateFlowAsStateFlow;
        this.backScene = new Flow() { // from class: com.android.systemui.scene.domain.interactor.SceneBackInteractor$special$$inlined$map$1

            /* renamed from: com.android.systemui.scene.domain.interactor.SceneBackInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.scene.domain.interactor.SceneBackInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    SceneKey sceneKey;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        SceneStack sceneStack = (SceneStack) obj;
                        if (Intrinsics.areEqual(sceneStack, EmptyStack.INSTANCE)) {
                            sceneKey = null;
                        } else {
                            if (!(sceneStack instanceof StackedNodes)) {
                                throw new NoWhenBranchMatchedException();
                            }
                            sceneKey = ((StackedNodes) sceneStack).head;
                        }
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(sceneKey, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = readonlyStateFlowAsStateFlow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
    }
}
