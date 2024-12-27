package com.android.systemui.communal.ui.compose;

import androidx.compose.foundation.lazy.grid.LazyGridMeasureResult;
import androidx.compose.foundation.lazy.grid.LazyGridState;
import androidx.compose.foundation.lazy.layout.LazyAnimateScrollKt;
import com.android.systemui.communal.domain.model.CommunalContentModel;
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

final class CommunalHubKt$ScrollOnUpdatedLiveContentEffect$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ List<CommunalContentModel> $communalContent;
    final /* synthetic */ CoroutineScope $coroutineScope;
    final /* synthetic */ LazyGridState $gridState;
    final /* synthetic */ List<String> $liveContentKeys;
    int label;

    /* renamed from: com.android.systemui.communal.ui.compose.CommunalHubKt$ScrollOnUpdatedLiveContentEffect$1$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        final /* synthetic */ LazyGridState $gridState;
        final /* synthetic */ int $indexOfFirstUpdatedContent;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(LazyGridState lazyGridState, int i, Continuation continuation) {
            super(2, continuation);
            this.$gridState = lazyGridState;
            this.$indexOfFirstUpdatedContent = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass3(this.$gridState, this.$indexOfFirstUpdatedContent, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                LazyGridState lazyGridState = this.$gridState;
                int i2 = this.$indexOfFirstUpdatedContent;
                this.label = 1;
                LazyGridState.Companion companion = LazyGridState.Companion;
                Object animateScrollToItem = LazyAnimateScrollKt.animateScrollToItem(i2, ((LazyGridMeasureResult) lazyGridState.layoutInfoState.getValue()).density, lazyGridState.animateScrollScope, 0, ((LazyGridMeasureResult) lazyGridState.layoutInfoState.getValue()).slotsPerLine * 100, this);
                if (animateScrollToItem != obj2) {
                    animateScrollToItem = Unit.INSTANCE;
                }
                if (animateScrollToItem == obj2) {
                    return obj2;
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
    /* JADX WARN: Multi-variable type inference failed */
    public CommunalHubKt$ScrollOnUpdatedLiveContentEffect$1(List<String> list, List<? extends CommunalContentModel> list2, LazyGridState lazyGridState, CoroutineScope coroutineScope, Continuation continuation) {
        super(2, continuation);
        this.$liveContentKeys = list;
        this.$communalContent = list2;
        this.$gridState = lazyGridState;
        this.$coroutineScope = coroutineScope;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CommunalHubKt$ScrollOnUpdatedLiveContentEffect$1(this.$liveContentKeys, this.$communalContent, this.$gridState, this.$coroutineScope, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CommunalHubKt$ScrollOnUpdatedLiveContentEffect$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        List list = CollectionsKt___CollectionsKt.toList(this.$liveContentKeys);
        this.$liveContentKeys.clear();
        List<String> list2 = this.$liveContentKeys;
        List<CommunalContentModel> list3 = this.$communalContent;
        ArrayList arrayList = new ArrayList();
        for (Object obj2 : list3) {
            CommunalContentModel communalContentModel = (CommunalContentModel) obj2;
            communalContentModel.getClass();
            if ((communalContentModel instanceof CommunalContentModel.Smartspace) || (communalContentModel instanceof CommunalContentModel.Umo)) {
                arrayList.add(obj2);
            }
        }
        ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            arrayList2.add(((CommunalContentModel) it.next()).getKey());
        }
        list2.addAll(arrayList2);
        Iterator<String> it2 = this.$liveContentKeys.iterator();
        int i = 0;
        while (true) {
            if (!it2.hasNext()) {
                i = -1;
                break;
            }
            if (!list.contains(it2.next())) {
                break;
            }
            i++;
        }
        if (i >= 0 && i < this.$gridState.scrollPosition.index$delegate.getIntValue()) {
            BuildersKt.launch$default(this.$coroutineScope, null, null, new AnonymousClass3(this.$gridState, i, null), 3);
        }
        return Unit.INSTANCE;
    }
}
