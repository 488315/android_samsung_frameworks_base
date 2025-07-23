package com.android.systemui.qs.pipeline.domain.interactor;

import android.content.pm.UserInfo;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.ScRune;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.SecQSTileInstanceManager;
import com.android.systemui.qs.external.CustomTile;
import com.android.systemui.qs.pipeline.data.repository.TileSpecRepository;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.pipeline.shared.logging.QSPipelineLogger;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.android.systemui.user.data.repository.UserRepositoryImpl$special$$inlined$map$2;
import java.util.List;
import java.util.function.Consumer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class CurrentTilesInteractorImpl$startTileCollection$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ CurrentTilesInteractorImpl this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl$startTileCollection$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ CurrentTilesInteractorImpl this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(CurrentTilesInteractorImpl currentTilesInteractorImpl, Continuation continuation) {
            super(2, continuation);
            this.this$0 = currentTilesInteractorImpl;
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
                final CurrentTilesInteractorImpl currentTilesInteractorImpl = this.this$0;
                UserRepositoryImpl$special$$inlined$map$2 userRepositoryImpl$special$$inlined$map$2 = ((UserRepositoryImpl) currentTilesInteractorImpl.userRepository).selectedUserInfo;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl.startTileCollection.1.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        UserInfo userInfo = (UserInfo) obj2;
                        boolean z = ScRune.QUICK_MANAGE_MULTI_QSHOST;
                        CurrentTilesInteractorImpl currentTilesInteractorImpl2 = CurrentTilesInteractorImpl.this;
                        if (z) {
                            final SecQSTileInstanceManager secQSTileInstanceManager = currentTilesInteractorImpl2.tileInstanceManager;
                            final int i2 = userInfo.id;
                            if (secQSTileInstanceManager.mUserId != i2) {
                                secQSTileInstanceManager.mUserId = i2;
                                Log.i("SecQSTileInstanceManager", "onUserChanged to " + i2);
                                final ArrayMap arrayMap = new ArrayMap();
                                secQSTileInstanceManager.mTileInstances.keySet().stream().forEach(new Consumer() { // from class: com.android.systemui.qs.SecQSTileInstanceManager$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj3) {
                                        SecQSTileInstanceManager secQSTileInstanceManager2 = SecQSTileInstanceManager.this;
                                        ArrayMap arrayMap2 = arrayMap;
                                        int i3 = i2;
                                        TileSpec tileSpec = (TileSpec) obj3;
                                        if (secQSTileInstanceManager2.mTileInstances.get(tileSpec) instanceof CustomTile) {
                                            arrayMap2.put(tileSpec, new ArraySet((ArraySet) secQSTileInstanceManager2.mTileUsingHosts.get(tileSpec)));
                                        } else if (((QSHost) secQSTileInstanceManager2.mQSHost.get()).isBarTile(tileSpec.getSpec())) {
                                            ((QSTile) secQSTileInstanceManager2.mTileInstances.get(tileSpec)).userSwitch(i3);
                                        }
                                    }
                                });
                                for (int i3 = 0; i3 < arrayMap.size(); i3++) {
                                    final TileSpec tileSpec = (TileSpec) arrayMap.keyAt(i3);
                                    ((ArraySet) arrayMap.get(tileSpec)).stream().forEach(new Consumer() { // from class: com.android.systemui.qs.SecQSTileInstanceManager$$ExternalSyntheticLambda1
                                        @Override // java.util.function.Consumer
                                        public final void accept(Object obj3) {
                                            SecQSTileInstanceManager secQSTileInstanceManager2 = SecQSTileInstanceManager.this;
                                            TileSpec tileSpec2 = tileSpec;
                                            secQSTileInstanceManager2.releaseTileUsing(obj3, tileSpec2);
                                            secQSTileInstanceManager2.mQSPipelineLogger.logTileDestroyed(tileSpec2, QSPipelineLogger.TileDestroyedReason.RELEASE_CUSTOM_TILE_USER_CHANGED);
                                        }
                                    });
                                }
                            }
                        }
                        currentTilesInteractorImpl2.currentUser.updateState(null, new Integer(userInfo.id));
                        currentTilesInteractorImpl2._userContext.setValue(((UserTrackerImpl) currentTilesInteractorImpl2.userTracker).getUserContext());
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (userRepositoryImpl$special$$inlined$map$2.collect(flowCollector, this) == coroutineSingletons) {
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
    /* renamed from: com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl$startTileCollection$1$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ CurrentTilesInteractorImpl this$0;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl$startTileCollection$1$2$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            final /* synthetic */ CoroutineScope $$this$launch;
            int I$0;
            /* synthetic */ Object L$0;
            Object L$1;
            Object L$2;
            Object L$3;
            Object L$4;
            Object L$5;
            Object L$6;
            int label;
            final /* synthetic */ CurrentTilesInteractorImpl this$0;

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl$startTileCollection$1$2$1$1, reason: invalid class name and collision with other inner class name */
            final class C02571 extends SuspendLambda implements Function2 {
                final /* synthetic */ List<TileSpec> $fotalist;
                int label;
                final /* synthetic */ CurrentTilesInteractorImpl this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                public C02571(CurrentTilesInteractorImpl currentTilesInteractorImpl, List<? extends TileSpec> list, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = currentTilesInteractorImpl;
                    this.$fotalist = list;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C02571(this.this$0, this.$fotalist, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C02571) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        CurrentTilesInteractorImpl currentTilesInteractorImpl = this.this$0;
                        TileSpecRepository tileSpecRepository = currentTilesInteractorImpl.tileSpecRepository;
                        int intValue = ((Number) currentTilesInteractorImpl.currentUser.getValue()).intValue();
                        List<TileSpec> list = this.$fotalist;
                        this.label = 1;
                        if (tileSpecRepository.setTiles(intValue, list, this) == coroutineSingletons) {
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
            /* renamed from: com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl$startTileCollection$1$2$1$6, reason: invalid class name */
            final class AnonymousClass6 extends SuspendLambda implements Function2 {
                int label;
                final /* synthetic */ CurrentTilesInteractorImpl this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass6(CurrentTilesInteractorImpl currentTilesInteractorImpl, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = currentTilesInteractorImpl;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass6(this.this$0, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((AnonymousClass6) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        CurrentTilesInteractorImpl currentTilesInteractorImpl = this.this$0;
                        TileSpecRepository tileSpecRepository = currentTilesInteractorImpl.tileSpecRepository;
                        int intValue = ((Number) currentTilesInteractorImpl.currentUser.getValue()).intValue();
                        this.label = 1;
                        if (tileSpecRepository.prependDefault(intValue, this) == coroutineSingletons) {
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
            /* renamed from: com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl$startTileCollection$1$2$1$8, reason: invalid class name */
            final class AnonymousClass8 extends SuspendLambda implements Function2 {
                final /* synthetic */ List<TileSpec> $resolvedSpecs;
                int label;
                final /* synthetic */ CurrentTilesInteractorImpl this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                public AnonymousClass8(CurrentTilesInteractorImpl currentTilesInteractorImpl, List<? extends TileSpec> list, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = currentTilesInteractorImpl;
                    this.$resolvedSpecs = list;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass8(this.this$0, this.$resolvedSpecs, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((AnonymousClass8) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        CurrentTilesInteractorImpl currentTilesInteractorImpl = this.this$0;
                        TileSpecRepository tileSpecRepository = currentTilesInteractorImpl.tileSpecRepository;
                        int intValue = ((Number) currentTilesInteractorImpl.currentUser.getValue()).intValue();
                        List<TileSpec> list = this.$resolvedSpecs;
                        this.label = 1;
                        if (tileSpecRepository.setTiles(intValue, list, this) == coroutineSingletons) {
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
            public AnonymousClass1(CurrentTilesInteractorImpl currentTilesInteractorImpl, CoroutineScope coroutineScope, Continuation continuation) {
                super(2, continuation);
                this.this$0 = currentTilesInteractorImpl;
                this.$$this$launch = coroutineScope;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, this.$$this$launch, continuation);
                anonymousClass1.L$0 = obj;
                return anonymousClass1;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass1) create((DataWithUserChange) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            /* JADX WARN: Code restructure failed: missing block: B:36:0x0479, code lost:
            
                if (r1 == r2) goto L137;
             */
            /* JADX WARN: Code restructure failed: missing block: B:71:0x04a3, code lost:
            
                if (r5 == r2) goto L137;
             */
            /* JADX WARN: Removed duplicated region for block: B:13:0x0346  */
            /* JADX WARN: Removed duplicated region for block: B:35:0x0461  */
            /* JADX WARN: Removed duplicated region for block: B:41:0x0481  */
            /* JADX WARN: Removed duplicated region for block: B:73:0x04bb  */
            /* JADX WARN: Removed duplicated region for block: B:8:0x04aa  */
            /* JADX WARN: Type inference failed for: r10v0, types: [T, java.util.List] */
            /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:70:0x04a3 -> B:6:0x04a6). Please report as a decompilation issue!!! */
            /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:71:0x04b4 -> B:10:0x04b7). Please report as a decompilation issue!!! */
            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final java.lang.Object invokeSuspend(java.lang.Object r26) {
                /*
                    Method dump skipped, instructions count: 1595
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl$startTileCollection$1.AnonymousClass2.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(CurrentTilesInteractorImpl currentTilesInteractorImpl, Continuation continuation) {
            super(2, continuation);
            this.this$0 = currentTilesInteractorImpl;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.this$0, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
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
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                CurrentTilesInteractorImpl currentTilesInteractorImpl = this.this$0;
                FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = currentTilesInteractorImpl.refreshUserAndTiles;
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(currentTilesInteractorImpl, coroutineScope, null);
                this.label = 1;
                if (FlowKt.collectLatest(flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1, anonymousClass1, this) == coroutineSingletons) {
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
    public CurrentTilesInteractorImpl$startTileCollection$1(CurrentTilesInteractorImpl currentTilesInteractorImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = currentTilesInteractorImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        CurrentTilesInteractorImpl$startTileCollection$1 currentTilesInteractorImpl$startTileCollection$1 = new CurrentTilesInteractorImpl$startTileCollection$1(this.this$0, continuation);
        currentTilesInteractorImpl$startTileCollection$1.L$0 = obj;
        return currentTilesInteractorImpl$startTileCollection$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CurrentTilesInteractorImpl$startTileCollection$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
        CurrentTilesInteractorImpl currentTilesInteractorImpl = this.this$0;
        CoroutineTracingKt.launchTraced$default(coroutineScope, currentTilesInteractorImpl.backgroundDispatcher, null, new AnonymousClass2(currentTilesInteractorImpl, null), 5);
        return Unit.INSTANCE;
    }
}
