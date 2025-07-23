package com.android.systemui.qs.tiles.base.ui.viewmodel;

import com.android.systemui.plugins.qs.QSTile;
import java.util.Iterator;
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
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class QSTileViewModelAdapter$setListening$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Object $client;
    int label;
    final /* synthetic */ QSTileViewModelAdapter this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelAdapter$setListening$1$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ QSTileViewModelAdapter this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(QSTileViewModelAdapter qSTileViewModelAdapter, Continuation continuation) {
            super(2, continuation);
            this.this$0 = qSTileViewModelAdapter;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.this$0, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((QSTile.AdapterState) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            QSTile.AdapterState adapterState = (QSTile.AdapterState) this.L$0;
            if (adapterState.copyTo(this.this$0.cachedState)) {
                Iterator it = this.this$0.callbacks.iterator();
                while (it.hasNext()) {
                    ((QSTile.Callback) it.next()).onStateChanged(adapterState);
                }
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public QSTileViewModelAdapter$setListening$1(QSTileViewModelAdapter qSTileViewModelAdapter, Object obj, Continuation continuation) {
        super(2, continuation);
        this.this$0 = qSTileViewModelAdapter;
        this.$client = obj;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new QSTileViewModelAdapter$setListening$1(this.this$0, this.$client, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((QSTileViewModelAdapter$setListening$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        if (this.this$0.listeningClients.add(this.$client) && this.this$0.listeningClients.size() == 1) {
            QSTileViewModelAdapter qSTileViewModelAdapter = this.this$0;
            final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(qSTileViewModelAdapter.qsTileViewModel.getState());
            final QSTileViewModelAdapter qSTileViewModelAdapter2 = this.this$0;
            qSTileViewModelAdapter.stateJob = FlowKt.launchIn(FlowKt.flowOn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new Flow() { // from class: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelAdapter$setListening$1$invokeSuspend$$inlined$map$1

                /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                /* renamed from: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelAdapter$setListening$1$invokeSuspend$$inlined$map$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;
                    public final /* synthetic */ QSTileViewModelAdapter this$0;

                    /* renamed from: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelAdapter$setListening$1$invokeSuspend$$inlined$map$1$2$1, reason: invalid class name */
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

                    public AnonymousClass2(FlowCollector flowCollector, QSTileViewModelAdapter qSTileViewModelAdapter) {
                        this.$this_unsafeFlow = flowCollector;
                        this.this$0 = qSTileViewModelAdapter;
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
                            boolean r0 = r7 instanceof com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelAdapter$setListening$1$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1
                            if (r0 == 0) goto L13
                            r0 = r7
                            com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelAdapter$setListening$1$invokeSuspend$$inlined$map$1$2$1 r0 = (com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelAdapter$setListening$1$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                            int r1 = r0.label
                            r2 = -2147483648(0xffffffff80000000, float:-0.0)
                            r3 = r1 & r2
                            if (r3 == 0) goto L13
                            int r1 = r1 - r2
                            r0.label = r1
                            goto L18
                        L13:
                            com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelAdapter$setListening$1$invokeSuspend$$inlined$map$1$2$1 r0 = new com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelAdapter$setListening$1$invokeSuspend$$inlined$map$1$2$1
                            r0.<init>(r7)
                        L18:
                            java.lang.Object r7 = r0.result
                            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                            int r2 = r0.label
                            r3 = 1
                            if (r2 == 0) goto L2f
                            if (r2 != r3) goto L27
                            kotlin.ResultKt.throwOnFailure(r7)
                            goto L56
                        L27:
                            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                            r5.<init>(r6)
                            throw r5
                        L2f:
                            kotlin.ResultKt.throwOnFailure(r7)
                            com.android.systemui.qs.tiles.base.shared.model.QSTileState r6 = (com.android.systemui.qs.tiles.base.shared.model.QSTileState) r6
                            com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelAdapter$Companion r7 = com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelAdapter.Companion
                            com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelAdapter r2 = r5.this$0
                            com.android.systemui.qs.QSHost r4 = r2.qsHost
                            android.content.Context r4 = r4.getContext()
                            com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModel r2 = r2.qsTileViewModel
                            com.android.systemui.qs.tiles.base.shared.model.QSTileConfig r2 = r2.getConfig()
                            r7.getClass()
                            com.android.systemui.plugins.qs.QSTile$AdapterState r6 = com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelAdapter.Companion.mapState(r4, r6, r2)
                            r0.label = r3
                            kotlinx.coroutines.flow.FlowCollector r5 = r5.$this_unsafeFlow
                            java.lang.Object r5 = r5.emit(r6, r0)
                            if (r5 != r1) goto L56
                            return r1
                        L56:
                            kotlin.Unit r5 = kotlin.Unit.INSTANCE
                            return r5
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelAdapter$setListening$1$invokeSuspend$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                    Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, qSTileViewModelAdapter2), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                }
            }, new AnonymousClass2(this.this$0, null)), this.this$0.uiBgDispatcher), this.this$0.applicationScope);
        }
        return Unit.INSTANCE;
    }
}
