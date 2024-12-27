package com.android.systemui.qs.panels.ui.viewmodel;

import com.android.systemui.qs.pipeline.domain.model.TileModel;
import java.util.ArrayList;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

final class TileGridViewModel$tileViewModels$1 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;

    public TileGridViewModel$tileViewModels$1(Continuation continuation) {
        super(2, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        TileGridViewModel$tileViewModels$1 tileGridViewModel$tileViewModels$1 = new TileGridViewModel$tileViewModels$1(continuation);
        tileGridViewModel$tileViewModels$1.L$0 = obj;
        return tileGridViewModel$tileViewModels$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((TileGridViewModel$tileViewModels$1) create((List) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        List<TileModel> list = (List) this.L$0;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        for (TileModel tileModel : list) {
            arrayList.add(new TileViewModel(tileModel.tile, tileModel.spec));
        }
        return arrayList;
    }
}
