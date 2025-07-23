package com.android.systemui.communal.ui.compose;

import androidx.compose.foundation.lazy.grid.LazyGridState;
import androidx.compose.runtime.MutableState;
import com.android.systemui.communal.domain.model.CommunalContentModel;
import java.util.ArrayList;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class CommunalHubKt$ScrollOnUpdatedLiveContentEffect$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ List<CommunalContentModel> $communalContent;
    final /* synthetic */ MutableState<Boolean> $communalContentPending$delegate;
    final /* synthetic */ LazyGridState $gridState;
    final /* synthetic */ List<String> $liveContentKeys;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public CommunalHubKt$ScrollOnUpdatedLiveContentEffect$1$1(List<? extends CommunalContentModel> list, List<String> list2, LazyGridState lazyGridState, MutableState<Boolean> mutableState, Continuation continuation) {
        super(2, continuation);
        this.$communalContent = list;
        this.$liveContentKeys = list2;
        this.$gridState = lazyGridState;
        this.$communalContentPending$delegate = mutableState;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CommunalHubKt$ScrollOnUpdatedLiveContentEffect$1$1(this.$communalContent, this.$liveContentKeys, this.$gridState, this.$communalContentPending$delegate, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CommunalHubKt$ScrollOnUpdatedLiveContentEffect$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            if (((Boolean) this.$communalContentPending$delegate.getValue()).booleanValue() && this.$communalContent.isEmpty()) {
                return Unit.INSTANCE;
            }
            List list = CollectionsKt___CollectionsKt.toList(this.$liveContentKeys);
            List<CommunalContentModel> list2 = this.$communalContent;
            ArrayList arrayList = new ArrayList();
            for (Object obj2 : list2) {
                CommunalContentModel communalContentModel = (CommunalContentModel) obj2;
                communalContentModel.getClass();
                if ((communalContentModel instanceof CommunalContentModel.Smartspace) || (communalContentModel instanceof CommunalContentModel.Umo)) {
                    arrayList.add(obj2);
                }
            }
            ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
            int size = arrayList.size();
            int i2 = 0;
            while (i2 < size) {
                Object obj3 = arrayList.get(i2);
                i2++;
                arrayList2.add(((CommunalContentModel) obj3).getKey());
            }
            this.$liveContentKeys.clear();
            this.$liveContentKeys.addAll(arrayList2);
            if (((Boolean) this.$communalContentPending$delegate.getValue()).booleanValue()) {
                this.$communalContentPending$delegate.setValue(Boolean.FALSE);
                return Unit.INSTANCE;
            }
            int size2 = arrayList2.size();
            int i3 = 0;
            int i4 = 0;
            while (true) {
                if (i4 >= size2) {
                    i3 = -1;
                    break;
                }
                Object obj4 = arrayList2.get(i4);
                i4++;
                if (!list.contains((String) obj4)) {
                    break;
                }
                i3++;
            }
            if (i3 >= 0 && i3 < this.$gridState.scrollPosition.getIndex()) {
                LazyGridState lazyGridState = this.$gridState;
                this.label = 1;
                if (lazyGridState.scrollToItem(i3, 0, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
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
