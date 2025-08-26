package com.android.systemui.qs.pipeline.data.domain.interactor;

import android.os.Build;
import android.util.Log;
import com.android.systemui.qs.pipeline.data.repository.RemovedTileSpecRepository;
import com.android.systemui.qs.pipeline.data.repository.RemovedTileSpecRepositoryImpl;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.user.data.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes2.dex */
public final class RemovedTilesInteractorImpl implements RemovedTilesInteractor {
    public static final boolean DEBUG;
    public final StateFlowImpl _removedTiles;
    public final RemovedTileSpecRepository removedTileSpecRepository;
    public final ReadonlyStateFlow removedTiles;
    public final CoroutineScope scope;
    public int userId;
    public final UserRepository userRepository;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.qs.pipeline.data.domain.interactor.RemovedTilesInteractorImpl$resetRemovedTiles$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return RemovedTilesInteractorImpl.this.new AnonymousClass1(continuation);
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
                RemovedTilesInteractorImpl removedTilesInteractorImpl = RemovedTilesInteractorImpl.this;
                RemovedTileSpecRepository removedTileSpecRepository = removedTilesInteractorImpl.removedTileSpecRepository;
                int i2 = removedTilesInteractorImpl.userId;
                EmptyList emptyList = EmptyList.INSTANCE;
                this.label = 1;
                if (((RemovedTileSpecRepositoryImpl) removedTileSpecRepository).storeTiles(i2, emptyList, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.qs.pipeline.data.domain.interactor.RemovedTilesInteractorImpl$setChangedTiles$1, reason: invalid class name and case insensitive filesystem */
    final class C09821 extends SuspendLambda implements Function2 {
        final /* synthetic */ List<TileSpec> $result;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09821(List<TileSpec> list, Continuation continuation) {
            super(2, continuation);
            this.$result = list;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return RemovedTilesInteractorImpl.this.new C09821(this.$result, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C09821) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                RemovedTilesInteractorImpl removedTilesInteractorImpl = RemovedTilesInteractorImpl.this;
                RemovedTileSpecRepository removedTileSpecRepository = removedTilesInteractorImpl.removedTileSpecRepository;
                int i2 = removedTilesInteractorImpl.userId;
                List<TileSpec> list = this.$result;
                this.label = 1;
                if (((RemovedTileSpecRepositoryImpl) removedTileSpecRepository).storeTiles(i2, list, this) == coroutineSingletons) {
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

    static {
        new Companion(null);
        DEBUG = Build.IS_DEBUGGABLE;
    }

    public RemovedTilesInteractorImpl(CoroutineScope coroutineScope, RemovedTileSpecRepository removedTileSpecRepository, UserRepository userRepository) {
        this.scope = coroutineScope;
        this.removedTileSpecRepository = removedTileSpecRepository;
        this.userRepository = userRepository;
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(EmptyList.INSTANCE);
        this._removedTiles = stateFlowImplMutableStateFlow;
        this.removedTiles = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
        this.userId = -1;
        BuildersKt.launch$default(coroutineScope, null, null, new RemovedTilesInteractorImpl$startTileCollection$1(this, null), 3);
    }

    public final void resetRemovedTiles() {
        if (DEBUG) {
            Log.d("RemovedTilesInteractor", "resetRemovedTiles");
        }
        this._removedTiles.setValue(EmptyList.INSTANCE);
        BuildersKt.launch$default(this.scope, null, null, new AnonymousClass1(null), 3);
    }

    public final void setChangedTiles(List list, List list2) {
        StateFlowImpl stateFlowImpl = this._removedTiles;
        List list3 = (List) stateFlowImpl.getValue();
        ArrayList arrayList = new ArrayList();
        for (Object obj : list3) {
            if (!((ArrayList) list2).contains((TileSpec) obj)) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = new ArrayList(arrayList);
        ArrayList arrayList3 = new ArrayList();
        for (Object obj2 : list) {
            if (!((ArrayList) list2).contains((TileSpec) obj2)) {
                arrayList3.add(obj2);
            }
        }
        arrayList2.addAll(arrayList3);
        if (DEBUG) {
            Log.d("RemovedTilesInteractor", "RemovedTiles  " + list3 + "  to  " + arrayList2);
        }
        stateFlowImpl.updateState(null, arrayList2);
        BuildersKt.launch$default(this.scope, null, null, new C09821(arrayList2, null), 3);
    }
}
