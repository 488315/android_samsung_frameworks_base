package com.android.systemui.qs.tiles.base.viewmodel;

import android.os.UserHandle;
import com.android.systemui.qs.tiles.base.interactor.QSTileDataInteractor;
import com.android.systemui.qs.tiles.viewmodel.QSTilePolicy;
import com.android.systemui.util.kotlin.FlowKt;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.CancellableFlow;
import kotlinx.coroutines.flow.CancellableFlowImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class QSTileViewModelImpl$createTileDataFlow$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ QSTileViewModelImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public QSTileViewModelImpl$createTileDataFlow$$inlined$flatMapLatest$1(Continuation continuation, QSTileViewModelImpl qSTileViewModelImpl) {
        super(3, continuation);
        this.this$0 = qSTileViewModelImpl;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        QSTileViewModelImpl$createTileDataFlow$$inlined$flatMapLatest$1 qSTileViewModelImpl$createTileDataFlow$$inlined$flatMapLatest$1 = new QSTileViewModelImpl$createTileDataFlow$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0);
        qSTileViewModelImpl$createTileDataFlow$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        qSTileViewModelImpl$createTileDataFlow$$inlined$flatMapLatest$1.L$1 = obj2;
        return qSTileViewModelImpl$createTileDataFlow$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            final UserHandle userHandle = (UserHandle) this.L$1;
            final QSTileViewModelImpl qSTileViewModelImpl = this.this$0;
            final SharedFlowImpl sharedFlowImpl = qSTileViewModelImpl.userInputs;
            final Flow flow = new Flow() { // from class: com.android.systemui.qs.tiles.base.viewmodel.QSTileViewModelImpl$filterFalseActions$$inlined$filter$1

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.qs.tiles.base.viewmodel.QSTileViewModelImpl$filterFalseActions$$inlined$filter$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;
                    public final /* synthetic */ QSTileViewModelImpl this$0;

                    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                    /* renamed from: com.android.systemui.qs.tiles.base.viewmodel.QSTileViewModelImpl$filterFalseActions$$inlined$filter$1$2$1, reason: invalid class name */
                    public final class AnonymousClass1 extends ContinuationImpl {
                        Object L$0;
                        Object L$1;
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

                    public AnonymousClass2(FlowCollector flowCollector, QSTileViewModelImpl qSTileViewModelImpl) {
                        this.$this_unsafeFlow = flowCollector;
                        this.this$0 = qSTileViewModelImpl;
                    }

                    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object emit(java.lang.Object r7, kotlin.coroutines.Continuation r8) {
                        /*
                            r6 = this;
                            boolean r0 = r8 instanceof com.android.systemui.qs.tiles.base.viewmodel.QSTileViewModelImpl$filterFalseActions$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                            if (r0 == 0) goto L13
                            r0 = r8
                            com.android.systemui.qs.tiles.base.viewmodel.QSTileViewModelImpl$filterFalseActions$$inlined$filter$1$2$1 r0 = (com.android.systemui.qs.tiles.base.viewmodel.QSTileViewModelImpl$filterFalseActions$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                            int r1 = r0.label
                            r2 = -2147483648(0xffffffff80000000, float:-0.0)
                            r3 = r1 & r2
                            if (r3 == 0) goto L13
                            int r1 = r1 - r2
                            r0.label = r1
                            goto L18
                        L13:
                            com.android.systemui.qs.tiles.base.viewmodel.QSTileViewModelImpl$filterFalseActions$$inlined$filter$1$2$1 r0 = new com.android.systemui.qs.tiles.base.viewmodel.QSTileViewModelImpl$filterFalseActions$$inlined$filter$1$2$1
                            r0.<init>(r8)
                        L18:
                            java.lang.Object r8 = r0.result
                            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                            int r2 = r0.label
                            r3 = 1
                            if (r2 == 0) goto L2f
                            if (r2 != r3) goto L27
                            kotlin.ResultKt.throwOnFailure(r8)
                            goto L66
                        L27:
                            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                            r6.<init>(r7)
                            throw r6
                        L2f:
                            kotlin.ResultKt.throwOnFailure(r8)
                            r8 = r7
                            com.android.systemui.qs.tiles.viewmodel.QSTileUserAction r8 = (com.android.systemui.qs.tiles.viewmodel.QSTileUserAction) r8
                            boolean r2 = r8 instanceof com.android.systemui.qs.tiles.viewmodel.QSTileUserAction.Click
                            com.android.systemui.qs.tiles.base.viewmodel.QSTileViewModelImpl r4 = r6.this$0
                            if (r2 == 0) goto L42
                            com.android.systemui.plugins.FalsingManager r2 = r4.falsingManager
                            boolean r2 = r2.isFalseTap(r3)
                            goto L4c
                        L42:
                            boolean r2 = r8 instanceof com.android.systemui.qs.tiles.viewmodel.QSTileUserAction.LongClick
                            if (r2 == 0) goto L69
                            com.android.systemui.plugins.FalsingManager r2 = r4.falsingManager
                            boolean r2 = r2.isFalseLongTap(r3)
                        L4c:
                            if (r2 == 0) goto L57
                            com.android.systemui.qs.tiles.base.logging.QSTileLogger r5 = r4.qsTileLogger
                            com.android.systemui.qs.tiles.viewmodel.QSTileConfig r4 = r4.config
                            com.android.systemui.qs.pipeline.shared.TileSpec r4 = r4.tileSpec
                            r5.logUserActionRejectedByFalsing(r8, r4)
                        L57:
                            r8 = r2 ^ 1
                            if (r8 == 0) goto L66
                            r0.label = r3
                            kotlinx.coroutines.flow.FlowCollector r6 = r6.$this_unsafeFlow
                            java.lang.Object r6 = r6.emit(r7, r0)
                            if (r6 != r1) goto L66
                            return r1
                        L66:
                            kotlin.Unit r6 = kotlin.Unit.INSTANCE
                            return r6
                        L69:
                            kotlin.NoWhenBranchMatchedException r6 = new kotlin.NoWhenBranchMatchedException
                            r6.<init>()
                            throw r6
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tiles.base.viewmodel.QSTileViewModelImpl$filterFalseActions$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                    Object collect = Flow.this.collect(new AnonymousClass2(flowCollector2, qSTileViewModelImpl), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                }
            };
            final QSTilePolicy qSTilePolicy = qSTileViewModelImpl.config.policy;
            if (!(qSTilePolicy instanceof QSTilePolicy.NoRestrictions)) {
                if (!(qSTilePolicy instanceof QSTilePolicy.Restricted)) {
                    throw new NoWhenBranchMatchedException();
                }
                flow = new Flow() { // from class: com.android.systemui.qs.tiles.base.viewmodel.QSTileViewModelImpl$filterByPolicy$lambda$9$$inlined$filter$1

                    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                    /* renamed from: com.android.systemui.qs.tiles.base.viewmodel.QSTileViewModelImpl$filterByPolicy$lambda$9$$inlined$filter$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ QSTilePolicy $policy$inlined;
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;
                        public final /* synthetic */ UserHandle $user$inlined;
                        public final /* synthetic */ QSTileViewModelImpl this$0;

                        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                        /* renamed from: com.android.systemui.qs.tiles.base.viewmodel.QSTileViewModelImpl$filterByPolicy$lambda$9$$inlined$filter$1$2$1, reason: invalid class name */
                        public final class AnonymousClass1 extends ContinuationImpl {
                            Object L$0;
                            Object L$1;
                            Object L$2;
                            Object L$3;
                            Object L$4;
                            Object L$5;
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

                        public AnonymousClass2(FlowCollector flowCollector, QSTilePolicy qSTilePolicy, QSTileViewModelImpl qSTileViewModelImpl, UserHandle userHandle) {
                            this.$this_unsafeFlow = flowCollector;
                            this.$policy$inlined = qSTilePolicy;
                            this.this$0 = qSTileViewModelImpl;
                            this.$user$inlined = userHandle;
                        }

                        /* JADX WARN: Removed duplicated region for block: B:19:0x00bb  */
                        /* JADX WARN: Removed duplicated region for block: B:21:0x00d1  */
                        /* JADX WARN: Removed duplicated region for block: B:23:0x00de  */
                        /* JADX WARN: Removed duplicated region for block: B:25:0x00f2  */
                        /* JADX WARN: Removed duplicated region for block: B:28:0x00e3  */
                        /* JADX WARN: Removed duplicated region for block: B:31:0x0082  */
                        /* JADX WARN: Removed duplicated region for block: B:35:0x00ee  */
                        /* JADX WARN: Removed duplicated region for block: B:36:0x00bd  */
                        /* JADX WARN: Removed duplicated region for block: B:41:0x0054  */
                        /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
                        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:34:0x00a5 -> B:17:0x00ab). Please report as a decompilation issue!!! */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final java.lang.Object emit(java.lang.Object r13, kotlin.coroutines.Continuation r14) {
                            /*
                                Method dump skipped, instructions count: 267
                                To view this dump change 'Code comments level' option to 'DEBUG'
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tiles.base.viewmodel.QSTileViewModelImpl$filterByPolicy$lambda$9$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                        Object collect = Flow.this.collect(new AnonymousClass2(flowCollector2, qSTilePolicy, qSTileViewModelImpl, userHandle), continuation);
                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                    }
                };
            }
            final Flow throttle = FlowKt.throttle(flow, 200L, qSTileViewModelImpl.systemClock);
            Flow flowOn = kotlinx.coroutines.flow.FlowKt.flowOn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new Flow() { // from class: com.android.systemui.qs.tiles.base.viewmodel.QSTileViewModelImpl$userInputFlow$$inlined$mapNotNull$1

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.qs.tiles.base.viewmodel.QSTileViewModelImpl$userInputFlow$$inlined$mapNotNull$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;
                    public final /* synthetic */ UserHandle $user$inlined;
                    public final /* synthetic */ QSTileViewModelImpl this$0;

                    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                    /* renamed from: com.android.systemui.qs.tiles.base.viewmodel.QSTileViewModelImpl$userInputFlow$$inlined$mapNotNull$1$2$1, reason: invalid class name */
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

                    public AnonymousClass2(FlowCollector flowCollector, QSTileViewModelImpl qSTileViewModelImpl, UserHandle userHandle) {
                        this.$this_unsafeFlow = flowCollector;
                        this.this$0 = qSTileViewModelImpl;
                        this.$user$inlined = userHandle;
                    }

                    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object emit(java.lang.Object r9, kotlin.coroutines.Continuation r10) {
                        /*
                            r8 = this;
                            boolean r0 = r10 instanceof com.android.systemui.qs.tiles.base.viewmodel.QSTileViewModelImpl$userInputFlow$$inlined$mapNotNull$1.AnonymousClass2.AnonymousClass1
                            if (r0 == 0) goto L13
                            r0 = r10
                            com.android.systemui.qs.tiles.base.viewmodel.QSTileViewModelImpl$userInputFlow$$inlined$mapNotNull$1$2$1 r0 = (com.android.systemui.qs.tiles.base.viewmodel.QSTileViewModelImpl$userInputFlow$$inlined$mapNotNull$1.AnonymousClass2.AnonymousClass1) r0
                            int r1 = r0.label
                            r2 = -2147483648(0xffffffff80000000, float:-0.0)
                            r3 = r1 & r2
                            if (r3 == 0) goto L13
                            int r1 = r1 - r2
                            r0.label = r1
                            goto L18
                        L13:
                            com.android.systemui.qs.tiles.base.viewmodel.QSTileViewModelImpl$userInputFlow$$inlined$mapNotNull$1$2$1 r0 = new com.android.systemui.qs.tiles.base.viewmodel.QSTileViewModelImpl$userInputFlow$$inlined$mapNotNull$1$2$1
                            r0.<init>(r10)
                        L18:
                            java.lang.Object r10 = r0.result
                            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                            int r2 = r0.label
                            r3 = 1
                            if (r2 == 0) goto L2f
                            if (r2 != r3) goto L27
                            kotlin.ResultKt.throwOnFailure(r10)
                            goto L92
                        L27:
                            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
                            r8.<init>(r9)
                            throw r8
                        L2f:
                            kotlin.ResultKt.throwOnFailure(r10)
                            com.android.systemui.qs.tiles.viewmodel.QSTileUserAction r9 = (com.android.systemui.qs.tiles.viewmodel.QSTileUserAction) r9
                            com.android.systemui.qs.tiles.base.viewmodel.QSTileViewModelImpl r10 = r8.this$0
                            kotlinx.coroutines.flow.ReadonlySharedFlow r2 = r10.state
                            kotlinx.coroutines.flow.SharedFlow r2 = r2.$$delegate_0
                            java.util.List r2 = r2.getReplayCache()
                            java.lang.Object r2 = kotlin.collections.CollectionsKt___CollectionsKt.lastOrNull(r2)
                            com.android.systemui.qs.tiles.viewmodel.QSTileState r2 = (com.android.systemui.qs.tiles.viewmodel.QSTileState) r2
                            r4 = 0
                            if (r2 != 0) goto L48
                            goto L85
                        L48:
                            kotlinx.coroutines.flow.ReadonlySharedFlow r5 = r10.tileData
                            kotlinx.coroutines.flow.SharedFlow r5 = r5.$$delegate_0
                            java.util.List r5 = r5.getReplayCache()
                            java.lang.Object r5 = kotlin.collections.CollectionsKt___CollectionsKt.lastOrNull(r5)
                            if (r5 != 0) goto L57
                            goto L85
                        L57:
                            com.android.systemui.qs.tiles.viewmodel.QSTileConfig r4 = r10.config
                            com.android.systemui.qs.pipeline.shared.TileSpec r6 = r4.tileSpec
                            com.android.systemui.qs.tiles.base.logging.QSTileLogger r7 = r10.qsTileLogger
                            r7.logUserActionPipeline(r6, r9, r2, r5)
                            com.android.systemui.qs.tiles.base.analytics.QSTileAnalytics r10 = r10.qsTileAnalytics
                            com.android.internal.logging.UiEventLogger r10 = r10.uiEventLogger
                            boolean r2 = r9 instanceof com.android.systemui.qs.tiles.viewmodel.QSTileUserAction.Click
                            if (r2 == 0) goto L6b
                            com.android.systemui.qs.QSEvent r2 = com.android.systemui.qs.QSEvent.QS_ACTION_CLICK
                            goto L71
                        L6b:
                            boolean r2 = r9 instanceof com.android.systemui.qs.tiles.viewmodel.QSTileUserAction.LongClick
                            if (r2 == 0) goto L95
                            com.android.systemui.qs.QSEvent r2 = com.android.systemui.qs.QSEvent.QS_ACTION_LONG_PRESS
                        L71:
                            com.android.internal.logging.InstanceId r6 = r4.instanceId
                            r7 = 0
                            java.lang.String r4 = r4.metricsSpec
                            r10.logWithInstanceId(r2, r7, r4, r6)
                            com.android.systemui.qs.tiles.base.interactor.DataUpdateTrigger$UserInput r4 = new com.android.systemui.qs.tiles.base.interactor.DataUpdateTrigger$UserInput
                            com.android.systemui.qs.tiles.base.interactor.QSTileInput r10 = new com.android.systemui.qs.tiles.base.interactor.QSTileInput
                            android.os.UserHandle r2 = r8.$user$inlined
                            r10.<init>(r2, r9, r5)
                            r4.<init>(r10)
                        L85:
                            if (r4 == 0) goto L92
                            r0.label = r3
                            kotlinx.coroutines.flow.FlowCollector r8 = r8.$this_unsafeFlow
                            java.lang.Object r8 = r8.emit(r4, r0)
                            if (r8 != r1) goto L92
                            return r1
                        L92:
                            kotlin.Unit r8 = kotlin.Unit.INSTANCE
                            return r8
                        L95:
                            kotlin.NoWhenBranchMatchedException r8 = new kotlin.NoWhenBranchMatchedException
                            r8.<init>()
                            throw r8
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tiles.base.viewmodel.QSTileViewModelImpl$userInputFlow$$inlined$mapNotNull$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                    Object collect = Flow.this.collect(new AnonymousClass2(flowCollector2, qSTileViewModelImpl, userHandle), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                }
            }, new QSTileViewModelImpl$userInputFlow$2(qSTileViewModelImpl, null)), qSTileViewModelImpl.backgroundDispatcher);
            final SharedFlowImpl sharedFlowImpl2 = this.this$0.forceUpdates;
            ReadonlySharedFlow shareIn = kotlinx.coroutines.flow.FlowKt.shareIn(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new QSTileViewModelImpl$createTileDataFlow$1$updateTriggers$3(this.this$0, null), kotlinx.coroutines.flow.FlowKt.merge(flowOn, new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new Flow() { // from class: com.android.systemui.qs.tiles.base.viewmodel.QSTileViewModelImpl$createTileDataFlow$lambda$5$$inlined$map$1

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.qs.tiles.base.viewmodel.QSTileViewModelImpl$createTileDataFlow$lambda$5$$inlined$map$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                    /* renamed from: com.android.systemui.qs.tiles.base.viewmodel.QSTileViewModelImpl$createTileDataFlow$lambda$5$$inlined$map$1$2$1, reason: invalid class name */
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
                            boolean r0 = r6 instanceof com.android.systemui.qs.tiles.base.viewmodel.QSTileViewModelImpl$createTileDataFlow$lambda$5$$inlined$map$1.AnonymousClass2.AnonymousClass1
                            if (r0 == 0) goto L13
                            r0 = r6
                            com.android.systemui.qs.tiles.base.viewmodel.QSTileViewModelImpl$createTileDataFlow$lambda$5$$inlined$map$1$2$1 r0 = (com.android.systemui.qs.tiles.base.viewmodel.QSTileViewModelImpl$createTileDataFlow$lambda$5$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                            int r1 = r0.label
                            r2 = -2147483648(0xffffffff80000000, float:-0.0)
                            r3 = r1 & r2
                            if (r3 == 0) goto L13
                            int r1 = r1 - r2
                            r0.label = r1
                            goto L18
                        L13:
                            com.android.systemui.qs.tiles.base.viewmodel.QSTileViewModelImpl$createTileDataFlow$lambda$5$$inlined$map$1$2$1 r0 = new com.android.systemui.qs.tiles.base.viewmodel.QSTileViewModelImpl$createTileDataFlow$lambda$5$$inlined$map$1$2$1
                            r0.<init>(r6)
                        L18:
                            java.lang.Object r6 = r0.result
                            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                            int r2 = r0.label
                            r3 = 1
                            if (r2 == 0) goto L2f
                            if (r2 != r3) goto L27
                            kotlin.ResultKt.throwOnFailure(r6)
                            goto L41
                        L27:
                            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                            r4.<init>(r5)
                            throw r4
                        L2f:
                            kotlin.ResultKt.throwOnFailure(r6)
                            kotlin.Unit r5 = (kotlin.Unit) r5
                            com.android.systemui.qs.tiles.base.interactor.DataUpdateTrigger$ForceUpdate r5 = com.android.systemui.qs.tiles.base.interactor.DataUpdateTrigger.ForceUpdate.INSTANCE
                            r0.label = r3
                            kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                            java.lang.Object r4 = r4.emit(r5, r0)
                            if (r4 != r1) goto L41
                            return r1
                        L41:
                            kotlin.Unit r4 = kotlin.Unit.INSTANCE
                            return r4
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tiles.base.viewmodel.QSTileViewModelImpl$createTileDataFlow$lambda$5$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                    Object collect = Flow.this.collect(new AnonymousClass2(flowCollector2), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                }
            }, new QSTileViewModelImpl$createTileDataFlow$1$updateTriggers$2(this.this$0, null)))), this.this$0.tileScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), 0);
            Flow flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(((QSTileDataInteractor) this.this$0.tileDataInteractor.invoke()).tileData(userHandle, shareIn), shareIn, new QSTileViewModelImpl$createTileDataFlow$1$1(null));
            if (!(flowKt__ZipKt$combine$$inlined$unsafeFlow$1 instanceof CancellableFlow)) {
                flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new CancellableFlowImpl(flowKt__ZipKt$combine$$inlined$unsafeFlow$1);
            }
            Flow flowOn2 = kotlinx.coroutines.flow.FlowKt.flowOn(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, this.this$0.backgroundDispatcher);
            this.label = 1;
            if (kotlinx.coroutines.flow.FlowKt.emitAll(this, flowOn2, flowCollector) == coroutineSingletons) {
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
