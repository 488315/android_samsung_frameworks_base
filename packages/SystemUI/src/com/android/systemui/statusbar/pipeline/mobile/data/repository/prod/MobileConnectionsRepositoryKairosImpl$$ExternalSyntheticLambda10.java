package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import android.content.Intent;
import com.android.systemui.kairos.CombineKt$$ExternalSyntheticLambda3;
import com.android.systemui.kairos.CombineKt$$ExternalSyntheticLambda8;
import com.android.systemui.kairos.State;
import com.android.systemui.kairos.StateInit;
import com.android.systemui.kairos.StateKt;
import com.android.systemui.kairos.internal.Init;
import com.android.systemui.kairos.util.WithPrev;
import com.android.systemui.statusbar.pipeline.mobile.data.model.SubscriptionModel;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos;
import com.android.systemui.statusbar.pipeline.shared.data.model.DefaultConnectionModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class MobileConnectionsRepositoryKairosImpl$$ExternalSyntheticLambda10 implements Function2 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ MobileConnectionsRepositoryKairosImpl$$ExternalSyntheticLambda10(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        switch (this.$r8$classId) {
            case 0:
                int i = MobileConnectionsRepositoryKairosImpl.$r8$clinit;
                return Boolean.valueOf(((DefaultConnectionModel) obj2).mobile.isDefault);
            case 1:
                Integer num = (Integer) obj2;
                int i2 = MobileConnectionsRepositoryKairosImpl.$r8$clinit;
                return Boolean.valueOf(num != null);
            case 2:
                int i3 = MobileConnectionsRepositoryKairosImpl.$r8$clinit;
                return Boolean.valueOf(((DefaultConnectionModel) obj2).isValidated);
            case 3:
                int i4 = MobileConnectionsRepositoryKairosImpl.$r8$clinit;
                return Boolean.valueOf(!((Collection) ((Pair) obj2).getSecond()).isEmpty());
            case 4:
                int i5 = MobileConnectionsRepositoryKairosImpl.$r8$clinit;
                List list = (List) obj2;
                int mapCapacity = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
                if (mapCapacity < 16) {
                    mapCapacity = 16;
                }
                LinkedHashMap linkedHashMap = new LinkedHashMap(mapCapacity);
                for (Object obj3 : list) {
                    linkedHashMap.put(Integer.valueOf(((SubscriptionModel) obj3).subscriptionId), obj3);
                }
                return linkedHashMap;
            case 5:
                int i6 = MobileConnectionsRepositoryKairosImpl.$r8$clinit;
                return (Integer) ((Pair) obj2).getFirst();
            case 6:
                int i7 = MobileConnectionsRepositoryKairosImpl.$r8$clinit;
                int intExtra = ((Intent) obj).getIntExtra("subscription", -1);
                Integer valueOf = Integer.valueOf(intExtra);
                if (intExtra != -1) {
                    return valueOf;
                }
                return null;
            case 7:
                int i8 = MobileConnectionsRepositoryKairosImpl.$r8$clinit;
                ((Intent) obj).getIntExtra("android.telephony.extra.SUBSCRIPTION_INDEX", -1);
                return Unit.INSTANCE;
            case 8:
                int i9 = MobileConnectionsRepositoryKairosImpl.$r8$clinit;
                return Unit.INSTANCE;
            case 9:
                WithPrev withPrev = (WithPrev) obj2;
                int i10 = MobileConnectionsRepositoryKairosImpl.$r8$clinit;
                Integer num2 = (Integer) withPrev.previousValue;
                Integer num3 = (Integer) withPrev.newValue;
                if (num2 == null || num3 == null) {
                    return null;
                }
                return new WithPrev(num2, num3);
            case 10:
                Map map = (Map) obj2;
                int i11 = MobileConnectionsRepositoryKairosImpl.$r8$clinit;
                LinkedHashMap linkedHashMap2 = new LinkedHashMap(MapsKt__MapsJVMKt.mapCapacity(map.size()));
                for (Map.Entry entry : map.entrySet()) {
                    linkedHashMap2.put(entry.getKey(), ((MobileConnectionRepositoryKairos) entry.getValue()).isInEcmMode());
                }
                Set<Map.Entry> entrySet = linkedHashMap2.entrySet();
                ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(entrySet, 10));
                for (Map.Entry entry2 : entrySet) {
                    final Object key = entry2.getKey();
                    arrayList.add(StateKt.map((State) entry2.getValue(), new Function2() { // from class: com.android.systemui.kairos.CombineKt$$ExternalSyntheticLambda7
                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj4, Object obj5) {
                            return new Pair(key, obj5);
                        }
                    }));
                }
                return StateKt.map(StateKt.map(new StateInit(new Init("combine", new CombineKt$$ExternalSyntheticLambda3((Iterable) arrayList))), new CombineKt$$ExternalSyntheticLambda8()), new MobileConnectionsRepositoryKairosImpl$$ExternalSyntheticLambda10(11));
            default:
                int i12 = MobileConnectionsRepositoryKairosImpl.$r8$clinit;
                Collection values = ((Map) obj2).values();
                if (!(values instanceof Collection) || !values.isEmpty()) {
                    Iterator it = values.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            if (((Boolean) it.next()).booleanValue()) {
                                r3 = true;
                            }
                        }
                    }
                }
                return Boolean.valueOf(r3);
        }
    }
}
