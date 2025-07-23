package com.android.systemui.qs.tiles.base.ui.viewmodel;

import android.os.UserHandle;
import com.android.systemui.qs.tiles.base.domain.interactor.QSTileDataInteractor;
import com.android.systemui.qs.tiles.base.domain.model.DataUpdateTrigger;
import com.android.systemui.qs.tiles.base.shared.model.QSTilePolicy;
import com.android.systemui.util.kotlin.FlowKt;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.flow.CancellableFlow;
import kotlinx.coroutines.flow.CancellableFlowImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class QSTileViewModelImpl$createTileDataFlow$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ QSTileViewModelImpl this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl$createTileDataFlow$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ FlowCollector $$this$transformLatest;
        final /* synthetic */ UserHandle $user;
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ QSTileViewModelImpl this$0;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl$createTileDataFlow$1$1$1, reason: invalid class name and collision with other inner class name */
        final class C02671 extends SuspendLambda implements Function3 {
            /* synthetic */ Object L$0;
            int label;

            public C02671(Continuation continuation) {
                super(3, continuation);
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                C02671 c02671 = new C02671((Continuation) obj3);
                c02671.L$0 = obj;
                return c02671.invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return this.L$0;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(QSTileViewModelImpl qSTileViewModelImpl, UserHandle userHandle, FlowCollector flowCollector, Continuation continuation) {
            super(2, continuation);
            this.this$0 = qSTileViewModelImpl;
            this.$user = userHandle;
            this.$$this$transformLatest = flowCollector;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, this.$user, this.$$this$transformLatest, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
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
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                final QSTileViewModelImpl qSTileViewModelImpl = this.this$0;
                final UserHandle userHandle = this.$user;
                final SharedFlowImpl sharedFlowImpl = qSTileViewModelImpl.userInputs;
                final Flow flow = new Flow() { // from class: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl$filterFalseActions$$inlined$filter$1

                    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                    /* renamed from: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl$filterFalseActions$$inlined$filter$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;
                        public final /* synthetic */ QSTileViewModelImpl this$0;

                        /* renamed from: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl$filterFalseActions$$inlined$filter$1$2$1, reason: invalid class name */
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
                        public final java.lang.Object emit(java.lang.Object r10, kotlin.coroutines.Continuation r11) {
                            /*
                                r9 = this;
                                boolean r0 = r11 instanceof com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl$filterFalseActions$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r11
                                com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl$filterFalseActions$$inlined$filter$1$2$1 r0 = (com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl$filterFalseActions$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl$filterFalseActions$$inlined$filter$1$2$1 r0 = new com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl$filterFalseActions$$inlined$filter$1$2$1
                                r0.<init>(r11)
                            L18:
                                java.lang.Object r11 = r0.result
                                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                int r2 = r0.label
                                r3 = 1
                                if (r2 == 0) goto L2f
                                if (r2 != r3) goto L27
                                kotlin.ResultKt.throwOnFailure(r11)
                                goto L8e
                            L27:
                                java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
                                java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
                                r9.<init>(r10)
                                throw r9
                            L2f:
                                kotlin.ResultKt.throwOnFailure(r11)
                                r11 = r10
                                com.android.systemui.qs.tiles.base.shared.model.QSTileUserAction r11 = (com.android.systemui.qs.tiles.base.shared.model.QSTileUserAction) r11
                                boolean r2 = r11 instanceof com.android.systemui.qs.tiles.base.shared.model.QSTileUserAction.Click
                                com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl r4 = r9.this$0
                                if (r2 != 0) goto L51
                                boolean r2 = r11 instanceof com.android.systemui.qs.tiles.base.shared.model.QSTileUserAction.ToggleClick
                                if (r2 == 0) goto L40
                                goto L51
                            L40:
                                boolean r2 = r11 instanceof com.android.systemui.qs.tiles.base.shared.model.QSTileUserAction.LongClick
                                if (r2 == 0) goto L4b
                                com.android.systemui.plugins.FalsingManager r2 = r4.falsingManager
                                boolean r2 = r2.isFalseLongTap(r3)
                                goto L57
                            L4b:
                                kotlin.NoWhenBranchMatchedException r9 = new kotlin.NoWhenBranchMatchedException
                                r9.<init>()
                                throw r9
                            L51:
                                com.android.systemui.plugins.FalsingManager r2 = r4.falsingManager
                                boolean r2 = r2.isFalseTap(r3)
                            L57:
                                if (r2 == 0) goto L81
                                com.android.systemui.qs.tiles.base.shared.logging.QSTileLogger r5 = r4.qsTileLogger
                                com.android.systemui.qs.tiles.base.shared.model.QSTileConfig r4 = r4.config
                                com.android.systemui.qs.pipeline.shared.TileSpec r4 = r4.tileSpec
                                com.android.systemui.log.LogBuffer r5 = r5.getLogBuffer(r4)
                                java.lang.String r4 = com.android.systemui.qs.tiles.base.shared.logging.QSTileLogger.getLogTag(r4)
                                com.android.systemui.log.core.LogLevel r6 = com.android.systemui.log.core.LogLevel.DEBUG
                                com.android.systemui.qs.tiles.base.shared.logging.QSTileLogger$$ExternalSyntheticLambda0 r7 = new com.android.systemui.qs.tiles.base.shared.logging.QSTileLogger$$ExternalSyntheticLambda0
                                r8 = 8
                                r7.<init>(r8)
                                r8 = 0
                                com.android.systemui.log.core.LogMessage r4 = r5.obtain(r4, r6, r7, r8)
                                java.lang.String r11 = com.android.systemui.qs.tiles.base.shared.logging.QSTileLogger.toLogString(r11)
                                r6 = r4
                                com.android.systemui.log.LogMessageImpl r6 = (com.android.systemui.log.LogMessageImpl) r6
                                r6.str1 = r11
                                r5.commit(r4)
                            L81:
                                if (r2 != 0) goto L8e
                                r0.label = r3
                                kotlinx.coroutines.flow.FlowCollector r9 = r9.$this_unsafeFlow
                                java.lang.Object r9 = r9.emit(r10, r0)
                                if (r9 != r1) goto L8e
                                return r1
                            L8e:
                                kotlin.Unit r9 = kotlin.Unit.INSTANCE
                                return r9
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl$filterFalseActions$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                        Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, qSTileViewModelImpl), continuation);
                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                    }
                };
                final QSTilePolicy qSTilePolicy = qSTileViewModelImpl.config.policy;
                if (!(qSTilePolicy instanceof QSTilePolicy.NoRestrictions)) {
                    if (!(qSTilePolicy instanceof QSTilePolicy.Restricted)) {
                        throw new NoWhenBranchMatchedException();
                    }
                    flow = new Flow() { // from class: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl$filterByPolicy$lambda$8$$inlined$filter$1

                        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                        /* renamed from: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl$filterByPolicy$lambda$8$$inlined$filter$1$2, reason: invalid class name */
                        public final class AnonymousClass2 implements FlowCollector {
                            public final /* synthetic */ QSTilePolicy $policy$inlined;
                            public final /* synthetic */ FlowCollector $this_unsafeFlow;
                            public final /* synthetic */ UserHandle $user$inlined;
                            public final /* synthetic */ QSTileViewModelImpl this$0;

                            /* renamed from: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl$filterByPolicy$lambda$8$$inlined$filter$1$2$1, reason: invalid class name */
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

                            /* JADX WARN: Code restructure failed: missing block: B:26:0x0130, code lost:
                            
                                if (r9.emit(r1, r2) == r3) goto L45;
                             */
                            /* JADX WARN: Removed duplicated region for block: B:19:0x00c8  */
                            /* JADX WARN: Removed duplicated region for block: B:21:0x00de  */
                            /* JADX WARN: Removed duplicated region for block: B:23:0x0108  */
                            /* JADX WARN: Removed duplicated region for block: B:25:0x011e  */
                            /* JADX WARN: Removed duplicated region for block: B:28:0x010d  */
                            /* JADX WARN: Removed duplicated region for block: B:31:0x008b  */
                            /* JADX WARN: Removed duplicated region for block: B:34:0x011b  */
                            /* JADX WARN: Removed duplicated region for block: B:35:0x00ca  */
                            /* JADX WARN: Removed duplicated region for block: B:40:0x005c  */
                            /* JADX WARN: Removed duplicated region for block: B:8:0x0027  */
                            /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:33:0x00af -> B:17:0x00b8). Please report as a decompilation issue!!! */
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                                To view partially-correct code enable 'Show inconsistent code' option in preferences
                            */
                            public final java.lang.Object emit(java.lang.Object r18, kotlin.coroutines.Continuation r19) {
                                /*
                                    Method dump skipped, instructions count: 310
                                    To view this dump change 'Code comments level' option to 'DEBUG'
                                */
                                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl$filterByPolicy$lambda$8$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                            }
                        }

                        @Override // kotlinx.coroutines.flow.Flow
                        public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                            Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, qSTilePolicy, qSTileViewModelImpl, userHandle), continuation);
                            return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                        }
                    };
                }
                final Flow throttle = FlowKt.throttle(flow, 200L, qSTileViewModelImpl.systemClock);
                Flow flowOn = kotlinx.coroutines.flow.FlowKt.flowOn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new Flow() { // from class: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl$userInputFlow$$inlined$mapNotNull$1

                    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                    /* renamed from: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl$userInputFlow$$inlined$mapNotNull$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;
                        public final /* synthetic */ UserHandle $user$inlined;
                        public final /* synthetic */ QSTileViewModelImpl this$0;

                        /* renamed from: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl$userInputFlow$$inlined$mapNotNull$1$2$1, reason: invalid class name */
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

                        /* JADX WARN: Removed duplicated region for block: B:15:0x0030  */
                        /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final java.lang.Object emit(java.lang.Object r13, kotlin.coroutines.Continuation r14) {
                            /*
                                r12 = this;
                                boolean r0 = r14 instanceof com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl$userInputFlow$$inlined$mapNotNull$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r14
                                com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl$userInputFlow$$inlined$mapNotNull$1$2$1 r0 = (com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl$userInputFlow$$inlined$mapNotNull$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl$userInputFlow$$inlined$mapNotNull$1$2$1 r0 = new com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl$userInputFlow$$inlined$mapNotNull$1$2$1
                                r0.<init>(r14)
                            L18:
                                java.lang.Object r14 = r0.result
                                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                int r2 = r0.label
                                r3 = 1
                                if (r2 == 0) goto L30
                                if (r2 != r3) goto L28
                                kotlin.ResultKt.throwOnFailure(r14)
                                goto Lca
                            L28:
                                java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
                                java.lang.String r13 = "call to 'resume' before 'invoke' with coroutine"
                                r12.<init>(r13)
                                throw r12
                            L30:
                                kotlin.ResultKt.throwOnFailure(r14)
                                com.android.systemui.qs.tiles.base.shared.model.QSTileUserAction r13 = (com.android.systemui.qs.tiles.base.shared.model.QSTileUserAction) r13
                                com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl r14 = r12.this$0
                                kotlinx.coroutines.flow.ReadonlyStateFlow r2 = r14.state
                                kotlinx.coroutines.flow.StateFlow r2 = r2.$$delegate_0
                                java.util.List r2 = r2.getReplayCache()
                                java.lang.Object r2 = kotlin.collections.CollectionsKt___CollectionsKt.lastOrNull(r2)
                                com.android.systemui.qs.tiles.base.shared.model.QSTileState r2 = (com.android.systemui.qs.tiles.base.shared.model.QSTileState) r2
                                r4 = 0
                                if (r2 != 0) goto L4a
                                goto Lbd
                            L4a:
                                kotlinx.coroutines.flow.ReadonlyStateFlow r5 = r14.tileData
                                kotlinx.coroutines.flow.StateFlow r5 = r5.$$delegate_0
                                java.util.List r5 = r5.getReplayCache()
                                java.lang.Object r5 = kotlin.collections.CollectionsKt___CollectionsKt.lastOrNull(r5)
                                if (r5 != 0) goto L59
                                goto Lbd
                            L59:
                                com.android.systemui.qs.tiles.base.shared.model.QSTileConfig r6 = r14.config
                                com.android.systemui.qs.pipeline.shared.TileSpec r7 = r6.tileSpec
                                com.android.systemui.qs.tiles.base.shared.logging.QSTileLogger r8 = r14.qsTileLogger
                                com.android.systemui.log.LogBuffer r8 = r8.getLogBuffer(r7)
                                java.lang.String r7 = com.android.systemui.qs.tiles.base.shared.logging.QSTileLogger.getLogTag(r7)
                                com.android.systemui.log.core.LogLevel r9 = com.android.systemui.log.core.LogLevel.DEBUG
                                com.android.systemui.qs.tiles.base.shared.logging.QSTileLogger$$ExternalSyntheticLambda0 r10 = new com.android.systemui.qs.tiles.base.shared.logging.QSTileLogger$$ExternalSyntheticLambda0
                                r11 = 6
                                r10.<init>(r11)
                                com.android.systemui.log.core.LogMessage r4 = r8.obtain(r7, r9, r10, r4)
                                java.lang.String r7 = com.android.systemui.qs.tiles.base.shared.logging.QSTileLogger.toLogString(r13)
                                r9 = r4
                                com.android.systemui.log.LogMessageImpl r9 = (com.android.systemui.log.LogMessageImpl) r9
                                r9.str1 = r7
                                java.lang.String r2 = com.android.systemui.qs.tiles.base.shared.logging.QSTileLogger.toLogString(r2)
                                r9.str2 = r2
                                java.lang.String r2 = java.lang.String.valueOf(r5)
                                r7 = 50
                                java.lang.String r2 = kotlin.text.StringsKt___StringsKt.take(r7, r2)
                                r9.str3 = r2
                                r8.commit(r4)
                                com.android.systemui.qs.tiles.base.ui.analytics.QSTileAnalytics r14 = r14.qsTileAnalytics
                                com.android.internal.logging.UiEventLogger r14 = r14.uiEventLogger
                                boolean r2 = r13 instanceof com.android.systemui.qs.tiles.base.shared.model.QSTileUserAction.Click
                                if (r2 == 0) goto L9c
                                com.android.systemui.qs.QSEvent r2 = com.android.systemui.qs.QSEvent.QS_ACTION_CLICK
                                goto La9
                            L9c:
                                boolean r2 = r13 instanceof com.android.systemui.qs.tiles.base.shared.model.QSTileUserAction.ToggleClick
                                if (r2 == 0) goto La3
                                com.android.systemui.qs.QSEvent r2 = com.android.systemui.qs.QSEvent.QS_ACTION_SECONDARY_CLICK
                                goto La9
                            La3:
                                boolean r2 = r13 instanceof com.android.systemui.qs.tiles.base.shared.model.QSTileUserAction.LongClick
                                if (r2 == 0) goto Lcd
                                com.android.systemui.qs.QSEvent r2 = com.android.systemui.qs.QSEvent.QS_ACTION_LONG_PRESS
                            La9:
                                java.lang.String r4 = r6.metricsSpec
                                com.android.internal.logging.InstanceId r6 = r6.instanceId
                                r7 = 0
                                r14.logWithInstanceId(r2, r7, r4, r6)
                                com.android.systemui.qs.tiles.base.domain.model.DataUpdateTrigger$UserInput r4 = new com.android.systemui.qs.tiles.base.domain.model.DataUpdateTrigger$UserInput
                                com.android.systemui.qs.tiles.base.domain.model.QSTileInput r14 = new com.android.systemui.qs.tiles.base.domain.model.QSTileInput
                                android.os.UserHandle r2 = r12.$user$inlined
                                r14.<init>(r2, r13, r5)
                                r4.<init>(r14)
                            Lbd:
                                if (r4 == 0) goto Lca
                                r0.label = r3
                                kotlinx.coroutines.flow.FlowCollector r12 = r12.$this_unsafeFlow
                                java.lang.Object r12 = r12.emit(r4, r0)
                                if (r12 != r1) goto Lca
                                return r1
                            Lca:
                                kotlin.Unit r12 = kotlin.Unit.INSTANCE
                                return r12
                            Lcd:
                                kotlin.NoWhenBranchMatchedException r12 = new kotlin.NoWhenBranchMatchedException
                                r12.<init>()
                                throw r12
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl$userInputFlow$$inlined$mapNotNull$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                        Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, qSTileViewModelImpl, userHandle), continuation);
                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                    }
                }, new QSTileViewModelImpl$userInputFlow$2(qSTileViewModelImpl, null)), qSTileViewModelImpl.backgroundDispatcher);
                final SharedFlowImpl sharedFlowImpl2 = this.this$0.forceUpdates;
                Flow flowOn2 = kotlinx.coroutines.flow.FlowKt.flowOn(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new QSTileViewModelImpl$createTileDataFlow$1$1$updateTriggers$3(this.this$0, null), kotlinx.coroutines.flow.FlowKt.merge(flowOn, new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new Flow() { // from class: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl$createTileDataFlow$1$1$invokeSuspend$$inlined$map$1

                    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                    /* renamed from: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl$createTileDataFlow$1$1$invokeSuspend$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;

                        /* renamed from: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl$createTileDataFlow$1$1$invokeSuspend$$inlined$map$1$2$1, reason: invalid class name */
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
                                boolean r0 = r6 instanceof com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl$createTileDataFlow$1$1$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r6
                                com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl$createTileDataFlow$1$1$invokeSuspend$$inlined$map$1$2$1 r0 = (com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl$createTileDataFlow$1$1$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl$createTileDataFlow$1$1$invokeSuspend$$inlined$map$1$2$1 r0 = new com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl$createTileDataFlow$1$1$invokeSuspend$$inlined$map$1$2$1
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
                                com.android.systemui.qs.tiles.base.domain.model.DataUpdateTrigger$ForceUpdate r5 = com.android.systemui.qs.tiles.base.domain.model.DataUpdateTrigger.ForceUpdate.INSTANCE
                                r0.label = r3
                                kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                                java.lang.Object r4 = r4.emit(r5, r0)
                                if (r4 != r1) goto L41
                                return r1
                            L41:
                                kotlin.Unit r4 = kotlin.Unit.INSTANCE
                                return r4
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl$createTileDataFlow$1$1$invokeSuspend$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                        Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                    }
                }, new QSTileViewModelImpl$createTileDataFlow$1$1$updateTriggers$2(this.this$0, null)))), this.this$0.backgroundDispatcher);
                SharingStarted.Companion.getClass();
                ReadonlyStateFlow stateIn = kotlinx.coroutines.flow.FlowKt.stateIn(flowOn2, coroutineScope, SharingStarted.Companion.Eagerly, DataUpdateTrigger.InitialRequest.INSTANCE);
                Flow flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(((QSTileDataInteractor) this.this$0.tileDataInteractor.invoke()).tileData(this.$user, stateIn), stateIn, new C02671(null));
                if (!(flowKt__ZipKt$combine$$inlined$unsafeFlow$1 instanceof CancellableFlow)) {
                    flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new CancellableFlowImpl(flowKt__ZipKt$combine$$inlined$unsafeFlow$1);
                }
                Flow flowOn3 = kotlinx.coroutines.flow.FlowKt.flowOn((CancellableFlow) flowKt__ZipKt$combine$$inlined$unsafeFlow$1, this.this$0.backgroundDispatcher);
                final FlowCollector flowCollector = this.$$this$transformLatest;
                FlowCollector flowCollector2 = new FlowCollector() { // from class: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl.createTileDataFlow.1.1.2
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        Object emit = FlowCollector.this.emit(obj2, continuation);
                        return emit == CoroutineSingletons.COROUTINE_SUSPENDED ? emit : Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (flowOn3.collect(flowCollector2, this) == coroutineSingletons) {
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
    public QSTileViewModelImpl$createTileDataFlow$1(QSTileViewModelImpl qSTileViewModelImpl, Continuation continuation) {
        super(3, continuation);
        this.this$0 = qSTileViewModelImpl;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        QSTileViewModelImpl$createTileDataFlow$1 qSTileViewModelImpl$createTileDataFlow$1 = new QSTileViewModelImpl$createTileDataFlow$1(this.this$0, (Continuation) obj3);
        qSTileViewModelImpl$createTileDataFlow$1.L$0 = (FlowCollector) obj;
        qSTileViewModelImpl$createTileDataFlow$1.L$1 = (UserHandle) obj2;
        return qSTileViewModelImpl$createTileDataFlow$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, (UserHandle) this.L$1, flowCollector, null);
            this.L$0 = null;
            this.label = 1;
            if (CoroutineScopeKt.coroutineScope(anonymousClass1, this) == coroutineSingletons) {
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
