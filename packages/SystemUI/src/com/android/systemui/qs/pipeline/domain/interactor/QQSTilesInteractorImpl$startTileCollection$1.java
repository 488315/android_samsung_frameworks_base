package com.android.systemui.qs.pipeline.domain.interactor;

import android.content.pm.UserInfo;
import com.android.systemui.qs.pipeline.data.repository.TileSpecRepository;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.android.systemui.user.data.repository.UserRepositoryImpl$special$$inlined$map$2;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class QQSTilesInteractorImpl$startTileCollection$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ QQSTilesInteractorImpl this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.qs.pipeline.domain.interactor.QQSTilesInteractorImpl$startTileCollection$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ QQSTilesInteractorImpl this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(QQSTilesInteractorImpl qQSTilesInteractorImpl, Continuation continuation) {
            super(2, continuation);
            this.this$0 = qQSTilesInteractorImpl;
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
                final QQSTilesInteractorImpl qQSTilesInteractorImpl = this.this$0;
                UserRepositoryImpl$special$$inlined$map$2 userRepositoryImpl$special$$inlined$map$2 = ((UserRepositoryImpl) qQSTilesInteractorImpl.userRepository).selectedUserInfo;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.qs.pipeline.domain.interactor.QQSTilesInteractorImpl.startTileCollection.1.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        QQSTilesInteractorImpl qQSTilesInteractorImpl2 = QQSTilesInteractorImpl.this;
                        qQSTilesInteractorImpl2.currentUser.updateState(null, new Integer(((UserInfo) obj2).id));
                        qQSTilesInteractorImpl2._userContext.setValue(((UserTrackerImpl) qQSTilesInteractorImpl2.userTracker).getUserContext());
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
    /* renamed from: com.android.systemui.qs.pipeline.domain.interactor.QQSTilesInteractorImpl$startTileCollection$1$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ QQSTilesInteractorImpl this$0;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.qs.pipeline.domain.interactor.QQSTilesInteractorImpl$startTileCollection$1$2$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            final /* synthetic */ CoroutineScope $$this$launch;
            int I$0;
            /* synthetic */ Object L$0;
            Object L$1;
            Object L$2;
            Object L$3;
            Object L$4;
            Object L$5;
            boolean Z$0;
            int label;
            final /* synthetic */ QQSTilesInteractorImpl this$0;

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.qs.pipeline.domain.interactor.QQSTilesInteractorImpl$startTileCollection$1$2$1$5, reason: invalid class name */
            final class AnonymousClass5 extends SuspendLambda implements Function2 {
                int label;
                final /* synthetic */ QQSTilesInteractorImpl this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass5(QQSTilesInteractorImpl qQSTilesInteractorImpl, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = qQSTilesInteractorImpl;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass5(this.this$0, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((AnonymousClass5) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        QQSTilesInteractorImpl qQSTilesInteractorImpl = this.this$0;
                        TileSpecRepository tileSpecRepository = qQSTilesInteractorImpl.tileSpecRepository;
                        int intValue = ((Number) qQSTilesInteractorImpl.currentUser.getValue()).intValue();
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
            /* renamed from: com.android.systemui.qs.pipeline.domain.interactor.QQSTilesInteractorImpl$startTileCollection$1$2$1$6, reason: invalid class name */
            final class AnonymousClass6 extends SuspendLambda implements Function2 {
                final /* synthetic */ List<TileSpec> $resolvedSpecs;
                int label;
                final /* synthetic */ QQSTilesInteractorImpl this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                public AnonymousClass6(QQSTilesInteractorImpl qQSTilesInteractorImpl, List<? extends TileSpec> list, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = qQSTilesInteractorImpl;
                    this.$resolvedSpecs = list;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass6(this.this$0, this.$resolvedSpecs, continuation);
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
                        QQSTilesInteractorImpl qQSTilesInteractorImpl = this.this$0;
                        TileSpecRepository tileSpecRepository = qQSTilesInteractorImpl.tileSpecRepository;
                        int intValue = ((Number) qQSTilesInteractorImpl.currentUser.getValue()).intValue();
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
            public AnonymousClass1(QQSTilesInteractorImpl qQSTilesInteractorImpl, CoroutineScope coroutineScope, Continuation continuation) {
                super(2, continuation);
                this.this$0 = qQSTilesInteractorImpl;
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

            /* JADX WARN: Code restructure failed: missing block: B:30:0x0237, code lost:
            
                if (r5 == r1) goto L90;
             */
            /* JADX WARN: Code restructure failed: missing block: B:61:0x0261, code lost:
            
                if (r6 == r1) goto L90;
             */
            /* JADX WARN: Removed duplicated region for block: B:12:0x0164  */
            /* JADX WARN: Removed duplicated region for block: B:29:0x0220  */
            /* JADX WARN: Removed duplicated region for block: B:35:0x023f  */
            /* JADX WARN: Removed duplicated region for block: B:63:0x0277  */
            /* JADX WARN: Removed duplicated region for block: B:8:0x0269  */
            /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:60:0x0261 -> B:6:0x0264). Please report as a decompilation issue!!! */
            /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:61:0x0190 -> B:9:0x0270). Please report as a decompilation issue!!! */
            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final java.lang.Object invokeSuspend(java.lang.Object r19) {
                /*
                    Method dump skipped, instructions count: 943
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.pipeline.domain.interactor.QQSTilesInteractorImpl$startTileCollection$1.AnonymousClass2.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(QQSTilesInteractorImpl qQSTilesInteractorImpl, Continuation continuation) {
            super(2, continuation);
            this.this$0 = qQSTilesInteractorImpl;
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
                QQSTilesInteractorImpl qQSTilesInteractorImpl = this.this$0;
                Flow flow = qQSTilesInteractorImpl.userAndTiles;
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(qQSTilesInteractorImpl, coroutineScope, null);
                this.label = 1;
                if (FlowKt.collectLatest(flow, anonymousClass1, this) == coroutineSingletons) {
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
    public QQSTilesInteractorImpl$startTileCollection$1(QQSTilesInteractorImpl qQSTilesInteractorImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = qQSTilesInteractorImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        QQSTilesInteractorImpl$startTileCollection$1 qQSTilesInteractorImpl$startTileCollection$1 = new QQSTilesInteractorImpl$startTileCollection$1(this.this$0, continuation);
        qQSTilesInteractorImpl$startTileCollection$1.L$0 = obj;
        return qQSTilesInteractorImpl$startTileCollection$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((QQSTilesInteractorImpl$startTileCollection$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
        BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(this.this$0, null), 3);
        QQSTilesInteractorImpl qQSTilesInteractorImpl = this.this$0;
        BuildersKt.launch$default(coroutineScope, qQSTilesInteractorImpl.backgroundDispatcher, null, new AnonymousClass2(qQSTilesInteractorImpl, null), 2);
        return Unit.INSTANCE;
    }
}
