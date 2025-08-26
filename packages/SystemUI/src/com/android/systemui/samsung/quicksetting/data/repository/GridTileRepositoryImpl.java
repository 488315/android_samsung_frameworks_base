package com.android.systemui.samsung.quicksetting.data.repository;

import com.android.systemui.samsung.quicksetting.data.datasource.PreferenceDataSource;
import com.android.systemui.samsung.quicksetting.data.datasource.PreferenceDataSourceImpl;
import com.android.systemui.samsung.quicksetting.domain.repository.GridTileRepository;
import com.android.systemui.samsung.quicksetting.ui.panel.ScreenType;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$3;

/* loaded from: classes2.dex */
public final class GridTileRepositoryImpl implements GridTileRepository {
    public final PreferenceDataSource preferenceDataSource;

    /* renamed from: com.android.systemui.samsung.quicksetting.data.repository.GridTileRepositoryImpl$loadGridTiles$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return GridTileRepositoryImpl.this.loadGridTiles(null, this);
        }
    }

    public GridTileRepositoryImpl(PreferenceDataSource preferenceDataSource) {
        this.preferenceDataSource = preferenceDataSource;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object loadGridTiles(ScreenType screenType, ContinuationImpl continuationImpl) {
        AnonymousClass1 anonymousClass1;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuationImpl);
            }
        }
        Object objLoadGridTiles = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objLoadGridTiles);
            anonymousClass1.label = 1;
            objLoadGridTiles = ((PreferenceDataSourceImpl) this.preferenceDataSource).loadGridTiles(screenType, anonymousClass1);
            if (objLoadGridTiles == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(objLoadGridTiles);
        }
        return new FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$3((Iterable) objLoadGridTiles);
    }
}
