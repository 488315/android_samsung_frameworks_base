package com.android.systemui.qs.pipeline.data.domain.interactor;

import android.content.pm.UserInfo;
import android.util.Log;
import com.android.systemui.qs.pipeline.data.repository.RemovedTileSpecRepositoryImpl;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.android.systemui.user.data.repository.UserRepositoryImpl$special$$inlined$map$2;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;

/* loaded from: classes2.dex */
final class RemovedTilesInteractorImpl$startTileCollection$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ RemovedTilesInteractorImpl this$0;

    /* renamed from: com.android.systemui.qs.pipeline.data.domain.interactor.RemovedTilesInteractorImpl$startTileCollection$1$1, reason: invalid class name */
    public final class AnonymousClass1 implements FlowCollector {
        public final /* synthetic */ RemovedTilesInteractorImpl this$0;

        public AnonymousClass1(RemovedTilesInteractorImpl removedTilesInteractorImpl) {
            this.this$0 = removedTilesInteractorImpl;
        }

        /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
        @Override // kotlinx.coroutines.flow.FlowCollector
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object emit(UserInfo userInfo, Continuation continuation) throws Throwable {
            RemovedTilesInteractorImpl$startTileCollection$1$1$emit$1 removedTilesInteractorImpl$startTileCollection$1$1$emit$1;
            MutableStateFlow mutableStateFlow;
            if (continuation instanceof RemovedTilesInteractorImpl$startTileCollection$1$1$emit$1) {
                removedTilesInteractorImpl$startTileCollection$1$1$emit$1 = (RemovedTilesInteractorImpl$startTileCollection$1$1$emit$1) continuation;
                int i = removedTilesInteractorImpl$startTileCollection$1$1$emit$1.label;
                if ((i & Integer.MIN_VALUE) != 0) {
                    removedTilesInteractorImpl$startTileCollection$1$1$emit$1.label = i - Integer.MIN_VALUE;
                } else {
                    removedTilesInteractorImpl$startTileCollection$1$1$emit$1 = new RemovedTilesInteractorImpl$startTileCollection$1$1$emit$1(this, continuation);
                }
            }
            Object obj = removedTilesInteractorImpl$startTileCollection$1$1$emit$1.result;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i2 = removedTilesInteractorImpl$startTileCollection$1$1$emit$1.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                RemovedTilesInteractorImpl removedTilesInteractorImpl = this.this$0;
                int i3 = removedTilesInteractorImpl.userId;
                int i4 = userInfo.id;
                if (i3 != i4) {
                    removedTilesInteractorImpl.userId = i4;
                    StateFlowImpl stateFlowImpl = removedTilesInteractorImpl._removedTiles;
                    removedTilesInteractorImpl$startTileCollection$1$1$emit$1.L$0 = stateFlowImpl;
                    removedTilesInteractorImpl$startTileCollection$1$1$emit$1.label = 1;
                    Object objLoadTilesFromSettings = ((RemovedTileSpecRepositoryImpl) removedTilesInteractorImpl.removedTileSpecRepository).loadTilesFromSettings(i4, removedTilesInteractorImpl$startTileCollection$1$1$emit$1);
                    if (objLoadTilesFromSettings == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    obj = objLoadTilesFromSettings;
                    mutableStateFlow = stateFlowImpl;
                }
                return Unit.INSTANCE;
            }
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            mutableStateFlow = (MutableStateFlow) removedTilesInteractorImpl$startTileCollection$1$1$emit$1.L$0;
            ResultKt.throwOnFailure(obj);
            Log.d("RemovedTilesInteractor", "loadRemovedTiles  " + ((List) obj));
            mutableStateFlow.setValue(obj);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RemovedTilesInteractorImpl$startTileCollection$1(RemovedTilesInteractorImpl removedTilesInteractorImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = removedTilesInteractorImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new RemovedTilesInteractorImpl$startTileCollection$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((RemovedTilesInteractorImpl$startTileCollection$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            RemovedTilesInteractorImpl removedTilesInteractorImpl = this.this$0;
            UserRepositoryImpl$special$$inlined$map$2 userRepositoryImpl$special$$inlined$map$2 = ((UserRepositoryImpl) removedTilesInteractorImpl.userRepository).selectedUserInfo;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(removedTilesInteractorImpl);
            this.label = 1;
            if (userRepositoryImpl$special$$inlined$map$2.collect(anonymousClass1, this) == coroutineSingletons) {
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
