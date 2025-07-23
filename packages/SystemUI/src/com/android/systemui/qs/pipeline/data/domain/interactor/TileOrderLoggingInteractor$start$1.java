package com.android.systemui.qs.pipeline.data.domain.interactor;

import com.android.systemui.plugins.qs.SQSTile;
import com.android.systemui.qs.TileSALogHelper;
import com.android.systemui.qs.pipeline.domain.model.TileModel;
import com.android.systemui.util.SystemUIAnalytics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class TileOrderLoggingInteractor$start$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ TileOrderLoggingInteractor this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.qs.pipeline.data.domain.interactor.TileOrderLoggingInteractor$start$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ TileOrderLoggingInteractor this$0;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.qs.pipeline.data.domain.interactor.TileOrderLoggingInteractor$start$1$1$1, reason: invalid class name and collision with other inner class name */
        final class C02471 extends SuspendLambda implements Function2 {
            /* synthetic */ Object L$0;
            int label;
            final /* synthetic */ TileOrderLoggingInteractor this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C02471(TileOrderLoggingInteractor tileOrderLoggingInteractor, Continuation continuation) {
                super(2, continuation);
                this.this$0 = tileOrderLoggingInteractor;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C02471 c02471 = new C02471(this.this$0, continuation);
                c02471.L$0 = obj;
                return c02471;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C02471) create((List) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                List list = (List) this.L$0;
                TileOrderLoggingInteractor tileOrderLoggingInteractor = this.this$0;
                List list2 = list;
                ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
                Iterator it = list2.iterator();
                while (it.hasNext()) {
                    arrayList.add(((TileModel) it.next()).tile);
                }
                ArrayList arrayList2 = new ArrayList();
                int size = arrayList.size();
                int i = 0;
                int i2 = 0;
                while (i2 < size) {
                    Object obj2 = arrayList.get(i2);
                    i2++;
                    if (obj2 instanceof SQSTile) {
                        arrayList2.add(obj2);
                    }
                }
                ArrayList arrayList3 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList2, 10));
                int size2 = arrayList2.size();
                while (i < size2) {
                    Object obj3 = arrayList2.get(i);
                    i++;
                    arrayList3.add(((SQSTile) obj3).getTileMapKey());
                }
                String joinToString$default = CollectionsKt___CollectionsKt.joinToString$default(CollectionsKt___CollectionsKt.filterNotNull(arrayList3), ",", null, null, null, 62);
                TileSALogHelper tileSALogHelper = tileOrderLoggingInteractor.tileSALogHelper;
                tileSALogHelper.editor.putString(SystemUIAnalytics.EID_2DEPTH_QUICK_BUTTON_ORDER, joinToString$default);
                tileSALogHelper.editor.apply();
                if (TileSALogHelper.LOGGING_DEBUG) {
                    SystemUIAnalytics.getCurrentScreenID();
                }
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(TileOrderLoggingInteractor tileOrderLoggingInteractor, Continuation continuation) {
            super(2, continuation);
            this.this$0 = tileOrderLoggingInteractor;
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
                StateFlow currentTiles = this.this$0.qsTilesInteractor.getCurrentTiles();
                C02471 c02471 = new C02471(this.this$0, null);
                this.label = 1;
                if (FlowKt.collectLatest(currentTiles, c02471, this) == coroutineSingletons) {
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
    /* renamed from: com.android.systemui.qs.pipeline.data.domain.interactor.TileOrderLoggingInteractor$start$1$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ TileOrderLoggingInteractor this$0;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.qs.pipeline.data.domain.interactor.TileOrderLoggingInteractor$start$1$2$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            /* synthetic */ Object L$0;
            int label;
            final /* synthetic */ TileOrderLoggingInteractor this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(TileOrderLoggingInteractor tileOrderLoggingInteractor, Continuation continuation) {
                super(2, continuation);
                this.this$0 = tileOrderLoggingInteractor;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, continuation);
                anonymousClass1.L$0 = obj;
                return anonymousClass1;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass1) create((List) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                List list = (List) this.L$0;
                TileOrderLoggingInteractor tileOrderLoggingInteractor = this.this$0;
                List list2 = list;
                ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
                Iterator it = list2.iterator();
                while (it.hasNext()) {
                    arrayList.add(((TileModel) it.next()).tile);
                }
                ArrayList arrayList2 = new ArrayList();
                int size = arrayList.size();
                int i = 0;
                int i2 = 0;
                while (i2 < size) {
                    Object obj2 = arrayList.get(i2);
                    i2++;
                    if (obj2 instanceof SQSTile) {
                        arrayList2.add(obj2);
                    }
                }
                ArrayList arrayList3 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList2, 10));
                int size2 = arrayList2.size();
                while (i < size2) {
                    Object obj3 = arrayList2.get(i);
                    i++;
                    arrayList3.add(((SQSTile) obj3).getTileMapKey());
                }
                String joinToString$default = CollectionsKt___CollectionsKt.joinToString$default(CollectionsKt___CollectionsKt.filterNotNull(arrayList3), ",", null, null, null, 62);
                TileSALogHelper tileSALogHelper = tileOrderLoggingInteractor.tileSALogHelper;
                tileSALogHelper.editor.putString(SystemUIAnalytics.EID_1DEPTH_QUICK_BUTTON_ORDER, joinToString$default);
                tileSALogHelper.editor.apply();
                if (TileSALogHelper.LOGGING_DEBUG) {
                    SystemUIAnalytics.getCurrentScreenID();
                }
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(TileOrderLoggingInteractor tileOrderLoggingInteractor, Continuation continuation) {
            super(2, continuation);
            this.this$0 = tileOrderLoggingInteractor;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass2(this.this$0, continuation);
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
                StateFlow currentTiles = this.this$0.quickQsTilesInteractor.getCurrentTiles();
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, null);
                this.label = 1;
                if (FlowKt.collectLatest(currentTiles, anonymousClass1, this) == coroutineSingletons) {
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
    public TileOrderLoggingInteractor$start$1(TileOrderLoggingInteractor tileOrderLoggingInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = tileOrderLoggingInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        TileOrderLoggingInteractor$start$1 tileOrderLoggingInteractor$start$1 = new TileOrderLoggingInteractor$start$1(this.this$0, continuation);
        tileOrderLoggingInteractor$start$1.L$0 = obj;
        return tileOrderLoggingInteractor$start$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((TileOrderLoggingInteractor$start$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
        TileOrderLoggingInteractor tileOrderLoggingInteractor = this.this$0;
        BuildersKt.launch$default(coroutineScope, tileOrderLoggingInteractor.backgroundDispatcher, null, new AnonymousClass1(tileOrderLoggingInteractor, null), 2);
        TileOrderLoggingInteractor tileOrderLoggingInteractor2 = this.this$0;
        BuildersKt.launch$default(coroutineScope, tileOrderLoggingInteractor2.backgroundDispatcher, null, new AnonymousClass2(tileOrderLoggingInteractor2, null), 2);
        return Unit.INSTANCE;
    }
}
