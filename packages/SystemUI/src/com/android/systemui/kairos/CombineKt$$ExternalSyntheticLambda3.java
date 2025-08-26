package com.android.systemui.kairos;

import com.android.systemui.kairos.internal.Init;
import com.android.systemui.kairos.internal.NetworkScope;
import com.android.systemui.kairos.internal.PullNodesKt;
import com.android.systemui.kairos.internal.StateImpl;
import com.android.systemui.kairos.internal.StateImplKt;
import com.android.systemui.kairos.internal.StateSource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final /* synthetic */ class CombineKt$$ExternalSyntheticLambda3 implements Function1 {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ CombineKt$$ExternalSyntheticLambda3(Iterable iterable) {
        this.f$0 = iterable;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                Iterable iterable = (Iterable) obj2;
                ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(iterable, 10));
                ArrayList arrayList2 = (ArrayList) iterable;
                int size = arrayList2.size();
                int i2 = 0;
                while (i2 < size) {
                    Object obj3 = arrayList2.get(i2);
                    i2++;
                    arrayList.add(((State) obj3).getInit$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos());
                }
                int size2 = arrayList.size();
                Init init = new Init(null, new CombineKt$$ExternalSyntheticLambda3((List) arrayList));
                if (size2 > 0) {
                    return StateImplKt.zipStateList("combine", size2, init);
                }
                return new StateImpl("combine", "combine", PullNodesKt.neverImpl, new StateSource(EmptyList.INSTANCE));
            default:
                NetworkScope networkScope = (NetworkScope) obj;
                List list = (List) obj2;
                ArrayList arrayList3 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    arrayList3.add((StateImpl) ((Init) it.next()).connect(networkScope));
                }
                return arrayList3;
        }
    }

    public /* synthetic */ CombineKt$$ExternalSyntheticLambda3(List list) {
        this.f$0 = list;
    }
}
