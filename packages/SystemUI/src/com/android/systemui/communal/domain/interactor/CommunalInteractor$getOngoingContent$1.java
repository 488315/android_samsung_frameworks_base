package com.android.systemui.communal.domain.interactor;

import android.app.smartspace.SmartspaceTarget;
import android.widget.RemoteViews;
import com.android.systemui.communal.data.model.CommunalMediaModel;
import com.android.systemui.communal.domain.interactor.CommunalInteractor;
import com.android.systemui.communal.domain.model.CommunalContentModel;
import com.android.systemui.communal.shared.model.CommunalContentSize;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.AbstractCollection;
import kotlin.collections.AbstractList;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt__MutableCollectionsJVMKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

final class CommunalInteractor$getOngoingContent$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ boolean $mediaHostVisible;
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalInteractor$getOngoingContent$1(boolean z, Continuation continuation) {
        super(3, continuation);
        this.$mediaHostVisible = z;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        CommunalInteractor$getOngoingContent$1 communalInteractor$getOngoingContent$1 = new CommunalInteractor$getOngoingContent$1(this.$mediaHostVisible, (Continuation) obj3);
        communalInteractor$getOngoingContent$1.L$0 = (List) obj;
        communalInteractor$getOngoingContent$1.L$1 = (CommunalMediaModel) obj2;
        return communalInteractor$getOngoingContent$1.invokeSuspend(Unit.INSTANCE);
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
        List<SmartspaceTarget> list2 = list;
        ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
        for (SmartspaceTarget smartspaceTarget : list2) {
            String smartspaceTargetId = smartspaceTarget.getSmartspaceTargetId();
            RemoteViews remoteViews = smartspaceTarget.getRemoteViews();
            Intrinsics.checkNotNull(remoteViews);
            arrayList2.add(new CommunalContentModel.Smartspace(smartspaceTargetId, remoteViews, smartspaceTarget.getCreationTimeMillis(), null, 8, null));
        }
        arrayList.addAll(arrayList2);
        if (this.$mediaHostVisible && communalMediaModel.hasActiveMediaOrRecommendation) {
            arrayList.add(new CommunalContentModel.Umo(communalMediaModel.createdTimestampMillis, null, 2, null));
        }
        if (arrayList.size() > 1) {
            CollectionsKt__MutableCollectionsJVMKt.sortWith(arrayList, new Comparator() { // from class: com.android.systemui.communal.domain.interactor.CommunalInteractor$getOngoingContent$1$invokeSuspend$$inlined$sortByDescending$1
                @Override // java.util.Comparator
                public final int compare(Object obj2, Object obj3) {
                    return ComparisonsKt__ComparisonsKt.compareValues(Long.valueOf(((CommunalContentModel.Ongoing) obj3).getCreatedTimestampMillis()), Long.valueOf(((CommunalContentModel.Ongoing) obj2).getCreatedTimestampMillis()));
                }
            });
        }
        Iterator it = arrayList.iterator();
        int i = 0;
        while (it.hasNext()) {
            Object next = it.next();
            int i2 = i + 1;
            Object obj2 = null;
            if (i < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
                throw null;
            }
            CommunalContentModel.Ongoing ongoing = (CommunalContentModel.Ongoing) next;
            CommunalInteractor.Companion companion = CommunalInteractor.Companion;
            int size = arrayList.size();
            companion.getClass();
            Collection collection = CommunalContentSize.$ENTRIES;
            AbstractCollection abstractCollection = (AbstractCollection) collection;
            int size2 = size % abstractCollection.getSize();
            CommunalContentSize.Companion companion2 = CommunalContentSize.Companion;
            int span = CommunalContentSize.FULL.getSpan();
            if (i > size2 - 1) {
                size2 = abstractCollection.getSize();
            }
            int i3 = span / size2;
            companion2.getClass();
            Iterator it2 = ((AbstractList) collection).iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                Object next2 = it2.next();
                if (((CommunalContentSize) next2).getSpan() == i3) {
                    obj2 = next2;
                    break;
                }
            }
            CommunalContentSize communalContentSize = (CommunalContentSize) obj2;
            if (communalContentSize == null) {
                throw new Exception("Invalid span for communal content size");
            }
            ongoing.setSize(communalContentSize);
            i = i2;
        }
        return arrayList;
    }
}
