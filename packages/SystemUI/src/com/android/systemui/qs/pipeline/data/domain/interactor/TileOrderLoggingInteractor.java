package com.android.systemui.qs.pipeline.data.domain.interactor;

import com.android.systemui.plugins.qs.SQSTile;
import com.android.systemui.qs.TileSALogHelper;
import com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor;
import com.android.systemui.qs.pipeline.domain.model.TileModel;
import com.android.systemui.util.SystemUIAnalytics;
import java.io.IOException;
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
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.StateFlow;

/* loaded from: classes2.dex */
public final class TileOrderLoggingInteractor {
    public final CoroutineDispatcher backgroundDispatcher;
    public final CurrentTilesInteractor qsTilesInteractor;
    public final CurrentTilesInteractor quickQsTilesInteractor;
    public final CoroutineScope scope;
    public final TileSALogHelper tileSALogHelper;

    /* renamed from: com.android.systemui.qs.pipeline.data.domain.interactor.TileOrderLoggingInteractor$start$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.qs.pipeline.data.domain.interactor.TileOrderLoggingInteractor$start$1$1, reason: invalid class name and collision with other inner class name */
        final class C03981 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ TileOrderLoggingInteractor this$0;

            /* renamed from: com.android.systemui.qs.pipeline.data.domain.interactor.TileOrderLoggingInteractor$start$1$1$1, reason: invalid class name and collision with other inner class name */
            final class C03991 extends SuspendLambda implements Function2 {
                /* synthetic */ Object L$0;
                int label;
                final /* synthetic */ TileOrderLoggingInteractor this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C03991(TileOrderLoggingInteractor tileOrderLoggingInteractor, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = tileOrderLoggingInteractor;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    C03991 c03991 = new C03991(this.this$0, continuation);
                    c03991.L$0 = obj;
                    return c03991;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C03991) create((List) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) throws IOException {
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
                    String strJoinToString$default = CollectionsKt___CollectionsKt.joinToString$default(CollectionsKt___CollectionsKt.filterNotNull(arrayList3), ",", null, null, null, 62);
                    TileSALogHelper tileSALogHelper = tileOrderLoggingInteractor.tileSALogHelper;
                    tileSALogHelper.editor.putString(SystemUIAnalytics.EID_2DEPTH_QUICK_BUTTON_ORDER, strJoinToString$default);
                    tileSALogHelper.editor.apply();
                    if (TileSALogHelper.LOGGING_DEBUG) {
                        SystemUIAnalytics.getCurrentScreenID();
                    }
                    return Unit.INSTANCE;
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C03981(TileOrderLoggingInteractor tileOrderLoggingInteractor, Continuation continuation) {
                super(2, continuation);
                this.this$0 = tileOrderLoggingInteractor;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C03981(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C03981) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    StateFlow currentTiles = this.this$0.qsTilesInteractor.getCurrentTiles();
                    C03991 c03991 = new C03991(this.this$0, null);
                    this.label = 1;
                    if (FlowKt.collectLatest(currentTiles, c03991, this) == coroutineSingletons) {
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

        /* renamed from: com.android.systemui.qs.pipeline.data.domain.interactor.TileOrderLoggingInteractor$start$1$2, reason: invalid class name */
        final class AnonymousClass2 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ TileOrderLoggingInteractor this$0;

            /* renamed from: com.android.systemui.qs.pipeline.data.domain.interactor.TileOrderLoggingInteractor$start$1$2$1, reason: invalid class name and collision with other inner class name */
            final class C04001 extends SuspendLambda implements Function2 {
                /* synthetic */ Object L$0;
                int label;
                final /* synthetic */ TileOrderLoggingInteractor this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C04001(TileOrderLoggingInteractor tileOrderLoggingInteractor, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = tileOrderLoggingInteractor;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    C04001 c04001 = new C04001(this.this$0, continuation);
                    c04001.L$0 = obj;
                    return c04001;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C04001) create((List) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) throws IOException {
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
                    String strJoinToString$default = CollectionsKt___CollectionsKt.joinToString$default(CollectionsKt___CollectionsKt.filterNotNull(arrayList3), ",", null, null, null, 62);
                    TileSALogHelper tileSALogHelper = tileOrderLoggingInteractor.tileSALogHelper;
                    tileSALogHelper.editor.putString(SystemUIAnalytics.EID_1DEPTH_QUICK_BUTTON_ORDER, strJoinToString$default);
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
                    C04001 c04001 = new C04001(this.this$0, null);
                    this.label = 1;
                    if (FlowKt.collectLatest(currentTiles, c04001, this) == coroutineSingletons) {
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

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = TileOrderLoggingInteractor.this.new AnonymousClass1(continuation);
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
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            TileOrderLoggingInteractor tileOrderLoggingInteractor = TileOrderLoggingInteractor.this;
            BuildersKt.launch$default(coroutineScope, tileOrderLoggingInteractor.backgroundDispatcher, null, new C03981(tileOrderLoggingInteractor, null), 2);
            TileOrderLoggingInteractor tileOrderLoggingInteractor2 = TileOrderLoggingInteractor.this;
            BuildersKt.launch$default(coroutineScope, tileOrderLoggingInteractor2.backgroundDispatcher, null, new AnonymousClass2(tileOrderLoggingInteractor2, null), 2);
            return Unit.INSTANCE;
        }
    }

    public TileOrderLoggingInteractor(CurrentTilesInteractor currentTilesInteractor, CurrentTilesInteractor currentTilesInteractor2, CoroutineDispatcher coroutineDispatcher, CoroutineScope coroutineScope, TileSALogHelper tileSALogHelper) {
        this.qsTilesInteractor = currentTilesInteractor;
        this.quickQsTilesInteractor = currentTilesInteractor2;
        this.backgroundDispatcher = coroutineDispatcher;
        this.scope = coroutineScope;
        this.tileSALogHelper = tileSALogHelper;
    }

    public final void start() {
        BuildersKt.launch$default(this.scope, null, null, new AnonymousClass1(null), 3);
    }
}
