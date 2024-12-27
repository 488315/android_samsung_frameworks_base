package com.android.systemui.qs.pipeline.data.repository;

import com.android.systemui.qs.pipeline.data.model.RestoreData;
import com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

final class UserTileSpecRepository$tiles$2 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ UserTileSpecRepository this$0;

    public UserTileSpecRepository$tiles$2(UserTileSpecRepository userTileSpecRepository, Continuation continuation) {
        super(3, continuation);
        this.this$0 = userTileSpecRepository;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        UserTileSpecRepository$tiles$2 userTileSpecRepository$tiles$2 = new UserTileSpecRepository$tiles$2(this.this$0, (Continuation) obj3);
        userTileSpecRepository$tiles$2.L$0 = (List) obj;
        userTileSpecRepository$tiles$2.L$1 = (UserTileSpecRepository.RestoreTiles) obj2;
        return userTileSpecRepository$tiles$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        List list = (List) this.L$0;
        UserTileSpecRepository.RestoreTiles restoreTiles = (UserTileSpecRepository.RestoreTiles) this.L$1;
        restoreTiles.getClass();
        UserTileSpecRepository.Companion companion = UserTileSpecRepository.Companion;
        Set set = restoreTiles.currentAutoAdded;
        companion.getClass();
        RestoreData restoreData = restoreTiles.restoreData;
        ArrayList arrayList = new ArrayList(restoreData.restoredTiles);
        ArrayList arrayList2 = new ArrayList();
        for (Object obj2 : set) {
            if (!restoreData.restoredAutoAddedTiles.contains((TileSpec) obj2)) {
                arrayList2.add(obj2);
            }
        }
        ArrayList arrayList3 = new ArrayList();
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            Object next = it.next();
            TileSpec tileSpec = (TileSpec) next;
            if (list.contains(tileSpec) && !restoreData.restoredTiles.contains(tileSpec)) {
                arrayList3.add(next);
            }
        }
        ArrayList arrayList4 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList3, 10));
        Iterator it2 = arrayList3.iterator();
        while (it2.hasNext()) {
            TileSpec tileSpec2 = (TileSpec) it2.next();
            arrayList4.add(new Pair(tileSpec2, Integer.valueOf(list.indexOf(tileSpec2))));
        }
        int i = 0;
        for (Object obj3 : CollectionsKt___CollectionsKt.sortedWith(arrayList4, new Comparator() { // from class: com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository$Companion$reconcileTiles$$inlined$sortedBy$1
            @Override // java.util.Comparator
            public final int compare(Object obj4, Object obj5) {
                return ComparisonsKt__ComparisonsKt.compareValues((Integer) ((Pair) obj4).getSecond(), (Integer) ((Pair) obj5).getSecond());
            }
        })) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
                throw null;
            }
            Pair pair = (Pair) obj3;
            TileSpec tileSpec3 = (TileSpec) pair.component1();
            int intValue = ((Number) pair.component2()).intValue() + i;
            if (intValue > arrayList.size()) {
                arrayList.add(tileSpec3);
            } else {
                arrayList.add(intValue, tileSpec3);
            }
            i = i2;
        }
        UserTileSpecRepository userTileSpecRepository = this.this$0;
        if (!list.equals(arrayList)) {
            if (restoreTiles instanceof UserTileSpecRepository.RestoreTiles) {
                userTileSpecRepository.logger.logTilesRestoredAndReconciled(list, arrayList, userTileSpecRepository.userId);
            } else {
                userTileSpecRepository.logger.logProcessTileChange(restoreTiles, arrayList, userTileSpecRepository.userId);
            }
        }
        return CollectionsKt___CollectionsKt.distinct(arrayList);
    }
}
