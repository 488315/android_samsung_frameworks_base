package com.android.systemui.communal.ui.compose;

import androidx.compose.foundation.lazy.grid.LazyGridItemInfo;
import androidx.compose.foundation.lazy.grid.LazyGridMeasureResult;
import androidx.compose.foundation.lazy.grid.LazyGridMeasuredItem;
import androidx.compose.foundation.lazy.grid.LazyGridState;
import androidx.compose.runtime.MutableState;
import com.android.systemui.communal.domain.model.CommunalContentModel;
import com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel;
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

/* loaded from: classes2.dex */
final class CommunalHubKt$ObserveNewWidgetAddedEffect$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ List<CommunalContentModel> $communalContent;
    final /* synthetic */ MutableState<Boolean> $communalContentPending$delegate;
    final /* synthetic */ CoroutineScope $coroutineScope;
    final /* synthetic */ LazyGridState $gridState;
    final /* synthetic */ BaseCommunalViewModel $viewModel;
    final /* synthetic */ List<String> $widgetKeys;
    int label;

    /* renamed from: com.android.systemui.communal.ui.compose.CommunalHubKt$ObserveNewWidgetAddedEffect$1$1$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ LazyGridState $gridState;
        final /* synthetic */ int $indexOfFirstNewWidget;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(LazyGridState lazyGridState, int i, Continuation continuation) {
            super(2, continuation);
            this.$gridState = lazyGridState;
            this.$indexOfFirstNewWidget = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass2(this.$gridState, this.$indexOfFirstNewWidget, continuation);
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
                LazyGridState lazyGridState = this.$gridState;
                int i2 = this.$indexOfFirstNewWidget;
                this.label = 1;
                if (LazyGridState.animateScrollToItem$default(lazyGridState, i2, this) == coroutineSingletons) {
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
    /* JADX WARN: Multi-variable type inference failed */
    public CommunalHubKt$ObserveNewWidgetAddedEffect$1$1(List<? extends CommunalContentModel> list, List<String> list2, BaseCommunalViewModel baseCommunalViewModel, LazyGridState lazyGridState, CoroutineScope coroutineScope, MutableState<Boolean> mutableState, Continuation continuation) {
        super(2, continuation);
        this.$communalContent = list;
        this.$widgetKeys = list2;
        this.$viewModel = baseCommunalViewModel;
        this.$gridState = lazyGridState;
        this.$coroutineScope = coroutineScope;
        this.$communalContentPending$delegate = mutableState;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CommunalHubKt$ObserveNewWidgetAddedEffect$1$1(this.$communalContent, this.$widgetKeys, this.$viewModel, this.$gridState, this.$coroutineScope, this.$communalContentPending$delegate, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CommunalHubKt$ObserveNewWidgetAddedEffect$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        if (((Boolean) this.$communalContentPending$delegate.getValue()).booleanValue() && this.$communalContent.isEmpty()) {
            return Unit.INSTANCE;
        }
        List list = CollectionsKt___CollectionsKt.toList(this.$widgetKeys);
        List<CommunalContentModel> list2 = this.$communalContent;
        ArrayList arrayList = new ArrayList();
        for (Object obj2 : list2) {
            if (obj2 instanceof CommunalContentModel.WidgetContent.Widget) {
                arrayList.add(obj2);
            }
        }
        this.$widgetKeys.clear();
        List<String> list3 = this.$widgetKeys;
        ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
        int size = arrayList.size();
        int i = 0;
        int i2 = 0;
        while (i2 < size) {
            Object obj3 = arrayList.get(i2);
            i2++;
            arrayList2.add(((CommunalContentModel.WidgetContent.Widget) obj3).key);
        }
        list3.addAll(arrayList2);
        if (((Boolean) this.$communalContentPending$delegate.getValue()).booleanValue()) {
            this.$communalContentPending$delegate.setValue(Boolean.FALSE);
            return Unit.INSTANCE;
        }
        Iterator<String> it = this.$widgetKeys.iterator();
        while (true) {
            if (!it.hasNext()) {
                i = -1;
                break;
            }
            if (!list.contains(it.next())) {
                break;
            }
            i++;
        }
        if (i < 0) {
            return Unit.INSTANCE;
        }
        this.$viewModel.onNewWidgetAdded(((CommunalContentModel.WidgetContent.Widget) arrayList.get(i)).providerInfo);
        LazyGridItemInfo lazyGridItemInfo = (LazyGridItemInfo) CollectionsKt___CollectionsKt.lastOrNull(((LazyGridMeasureResult) this.$gridState.getLayoutInfo()).visibleItemsInfo);
        Integer num = lazyGridItemInfo != null ? new Integer(((LazyGridMeasuredItem) lazyGridItemInfo).index) : null;
        if (num != null && i > num.intValue()) {
            BuildersKt.launch$default(this.$coroutineScope, null, null, new AnonymousClass2(this.$gridState, i, null), 3);
        }
        return Unit.INSTANCE;
    }
}
