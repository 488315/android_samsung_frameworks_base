package com.android.systemui.qs.composefragment;

import androidx.compose.runtime.SnapshotStateKt;
import com.android.compose.animation.scene.MutableSceneTransitionLayoutState;
import com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SafeFlow;

/* loaded from: classes2.dex */
final class QSFragmentCompose$CollapsableQuickSettingsSTL$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ MutableSceneTransitionLayoutState $sceneState;
    int label;
    final /* synthetic */ QSFragmentCompose this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public QSFragmentCompose$CollapsableQuickSettingsSTL$1$1(MutableSceneTransitionLayoutState mutableSceneTransitionLayoutState, QSFragmentCompose qSFragmentCompose, Continuation continuation) {
        super(2, continuation);
        this.$sceneState = mutableSceneTransitionLayoutState;
        this.this$0 = qSFragmentCompose;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new QSFragmentCompose$CollapsableQuickSettingsSTL$1$1(this.$sceneState, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((QSFragmentCompose$CollapsableQuickSettingsSTL$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            MutableSceneTransitionLayoutState mutableSceneTransitionLayoutState = this.$sceneState;
            QSFragmentCompose qSFragmentCompose = this.this$0;
            QSFragmentComposeViewModel qSFragmentComposeViewModel = qSFragmentCompose.viewModel;
            if (qSFragmentComposeViewModel == null) {
                qSFragmentComposeViewModel = null;
            }
            ReadonlyStateFlow readonlyStateFlow = qSFragmentComposeViewModel.containerViewModel.editModeViewModel.isEditing;
            final SafeFlow safeFlowSnapshotFlow = SnapshotStateKt.snapshotFlow(new QSFragmentCompose$$ExternalSyntheticLambda0(qSFragmentCompose, 3));
            Flow flow = new Flow() { // from class: com.android.systemui.qs.composefragment.QSFragmentCompose$CollapsableQuickSettingsSTL$1$1$invokeSuspend$$inlined$map$1

                /* renamed from: com.android.systemui.qs.composefragment.QSFragmentCompose$CollapsableQuickSettingsSTL$1$1$invokeSuspend$$inlined$map$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* renamed from: com.android.systemui.qs.composefragment.QSFragmentCompose$CollapsableQuickSettingsSTL$1$1$invokeSuspend$$inlined$map$1$2$1, reason: invalid class name */
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
                            Float f = new Float(((QSFragmentComposeViewModel.QSExpansionState) obj).progress);
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(f, anonymousClass1) == coroutineSingletons) {
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
                    Object objCollect = safeFlowSnapshotFlow.collect(new AnonymousClass2(flowCollector), continuation);
                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                }
            };
            this.label = 1;
            QSFragmentComposeKt$instanceProvider$1 qSFragmentComposeKt$instanceProvider$1 = QSFragmentComposeKt.instanceProvider;
            Object objCoroutineScope = CoroutineScopeKt.coroutineScope(new QSFragmentComposeKt$synchronizeQsState$2(readonlyStateFlow, flow, mutableSceneTransitionLayoutState, null), this);
            if (objCoroutineScope != obj2) {
                objCoroutineScope = Unit.INSTANCE;
            }
            if (objCoroutineScope == obj2) {
                return obj2;
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
