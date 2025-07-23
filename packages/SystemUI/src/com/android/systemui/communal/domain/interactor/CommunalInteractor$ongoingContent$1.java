package com.android.systemui.communal.domain.interactor;

import com.android.systemui.communal.data.model.CommunalMediaModel;
import com.android.systemui.communal.data.model.CommunalSmartspaceTimer;
import com.android.systemui.communal.domain.interactor.CommunalInteractor;
import com.android.systemui.communal.domain.model.CommunalContentModel;
import com.android.systemui.communal.shared.model.CommunalContentSize;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt__MutableCollectionsJVMKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class CommunalInteractor$ongoingContent$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ boolean $isMediaHostVisible;
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ CommunalInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalInteractor$ongoingContent$1(boolean z, CommunalInteractor communalInteractor, Continuation continuation) {
        super(3, continuation);
        this.$isMediaHostVisible = z;
        this.this$0 = communalInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        CommunalInteractor$ongoingContent$1 communalInteractor$ongoingContent$1 = new CommunalInteractor$ongoingContent$1(this.$isMediaHostVisible, this.this$0, (Continuation) obj3);
        communalInteractor$ongoingContent$1.L$0 = (List) obj;
        communalInteractor$ongoingContent$1.L$1 = (CommunalMediaModel) obj2;
        return communalInteractor$ongoingContent$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        List list = (List) this.L$0;
        CommunalMediaModel communalMediaModel = (CommunalMediaModel) this.L$1;
        ArrayList arrayList = new ArrayList();
        List<CommunalSmartspaceTimer> list2 = list;
        ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
        for (CommunalSmartspaceTimer communalSmartspaceTimer : list2) {
            arrayList2.add(new CommunalContentModel.Smartspace(communalSmartspaceTimer.smartspaceTargetId, communalSmartspaceTimer.remoteViews, communalSmartspaceTimer.createdTimestampMillis, null, 8, null));
        }
        arrayList.addAll(arrayList2);
        if (this.$isMediaHostVisible && communalMediaModel.hasAnyMediaOrRecommendation) {
            arrayList.add(new CommunalContentModel.Umo(communalMediaModel.createdTimestampMillis, null, null, 6, null));
        }
        if (arrayList.size() > 1) {
            CollectionsKt__MutableCollectionsJVMKt.sortWith(arrayList, new Comparator() { // from class: com.android.systemui.communal.domain.interactor.CommunalInteractor$ongoingContent$1$invokeSuspend$$inlined$sortByDescending$1
                @Override // java.util.Comparator
                public final int compare(Object obj2, Object obj3) {
                    return ComparisonsKt__ComparisonsKt.compareValues(Long.valueOf(((CommunalContentModel.Ongoing) obj3).getCreatedTimestampMillis()), Long.valueOf(((CommunalContentModel.Ongoing) obj2).getCreatedTimestampMillis()));
                }
            });
        }
        CommunalInteractor communalInteractor = this.this$0;
        CommunalInteractor.Companion companion = CommunalInteractor.Companion;
        communalInteractor.getClass();
        ArrayList arrayList3 = new ArrayList();
        int span = CommunalContentSize.FixedSize.FULL.getSpan();
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj2 = arrayList.get(i);
            i++;
            CommunalContentModel.Ongoing ongoing = (CommunalContentModel.Ongoing) obj2;
            if (span < ongoing.getMinSize().getSpan()) {
                CommunalInteractor.resizeItems$resizeColumn(arrayList3);
                arrayList3.clear();
                span = CommunalContentSize.FixedSize.FULL.getSpan();
            }
            arrayList3.add(ongoing);
            span -= ongoing.getMinSize().getSpan();
        }
        CommunalInteractor.resizeItems$resizeColumn(arrayList3);
        return arrayList;
    }
}
