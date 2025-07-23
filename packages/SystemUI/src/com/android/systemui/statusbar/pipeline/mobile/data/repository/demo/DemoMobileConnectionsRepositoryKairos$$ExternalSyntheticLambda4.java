package com.android.systemui.statusbar.pipeline.mobile.data.repository.demo;

import com.android.keyguard.KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.kairos.BuildScope;
import com.android.systemui.kairos.EventsKt;
import com.android.systemui.kairos.FilterKt;
import com.android.systemui.kairos.FilterKt$$ExternalSyntheticLambda0;
import com.android.systemui.kairos.TransactionScope;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.log.table.TableLogBufferFactory;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.model.FakeNetworkEventModel;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.collections.SetsKt___SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class DemoMobileConnectionsRepositoryKairos$$ExternalSyntheticLambda4 implements Function2 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DemoMobileConnectionsRepositoryKairos f$0;

    public /* synthetic */ DemoMobileConnectionsRepositoryKairos$$ExternalSyntheticLambda4(int i, DemoMobileConnectionsRepositoryKairos demoMobileConnectionsRepositoryKairos) {
        this.$r8$classId = i;
        this.f$0 = demoMobileConnectionsRepositoryKairos;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        final int i = 1;
        final DemoMobileConnectionsRepositoryKairos demoMobileConnectionsRepositoryKairos = this.f$0;
        switch (this.$r8$classId) {
            case 0:
                int i2 = DemoMobileConnectionsRepositoryKairos.$r8$clinit;
                demoMobileConnectionsRepositoryKairos.getClass();
                Set<Map.Entry> entrySet = ((Map) obj2).entrySet();
                int mapCapacity = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(entrySet, 10));
                if (mapCapacity < 16) {
                    mapCapacity = 16;
                }
                LinkedHashMap linkedHashMap = new LinkedHashMap(mapCapacity);
                for (Map.Entry entry : entrySet) {
                    Pair pair = new Pair(entry.getValue(), entry.getKey());
                    linkedHashMap.put(pair.getFirst(), pair.getSecond());
                }
                return linkedHashMap;
            case 1:
                TransactionScope transactionScope = (TransactionScope) obj;
                FakeNetworkEventModel fakeNetworkEventModel = (FakeNetworkEventModel) obj2;
                int i3 = DemoMobileConnectionsRepositoryKairos.$r8$clinit;
                if (fakeNetworkEventModel == null) {
                    return null;
                }
                if (fakeNetworkEventModel instanceof FakeNetworkEventModel.Mobile) {
                    Integer num = ((FakeNetworkEventModel.Mobile) fakeNetworkEventModel).subId;
                    if (num == null) {
                        return null;
                    }
                    final int intValue = num.intValue();
                    final int i4 = 0;
                    return new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoMobileConnectionsRepositoryKairos$$ExternalSyntheticLambda22
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo779invoke(Object obj3) {
                            int i5 = intValue;
                            Set set = (Set) obj3;
                            switch (i4) {
                                case 0:
                                    int i6 = DemoMobileConnectionsRepositoryKairos.$r8$clinit;
                                    return SetsKt___SetsKt.plus(set, Integer.valueOf(i5));
                                default:
                                    int i7 = DemoMobileConnectionsRepositoryKairos.$r8$clinit;
                                    return SetsKt___SetsKt.minus(set, Integer.valueOf(i5));
                            }
                        }
                    };
                }
                if (!(fakeNetworkEventModel instanceof FakeNetworkEventModel.MobileDisabled)) {
                    throw new NoWhenBranchMatchedException();
                }
                Integer num2 = ((FakeNetworkEventModel.MobileDisabled) fakeNetworkEventModel).subId;
                if (num2 == null) {
                    Set set = (Set) transactionScope.sample(demoMobileConnectionsRepositoryKairos.activeMobileSubscriptions);
                    if (set.size() == 1) {
                        num2 = (Integer) CollectionsKt___CollectionsKt.first(set);
                    } else {
                        KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0.m("processDisabledMobileState: Unable to infer subscription to disable. Specify subId using '-e slot <subId>'. Known subIds: [", CollectionsKt___CollectionsKt.joinToString$default(set, ",", null, null, null, 62), "]", "DemoMobileConnectionsRepo");
                        num2 = null;
                    }
                }
                if (num2 == null) {
                    return null;
                }
                final int intValue2 = num2.intValue();
                return new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoMobileConnectionsRepositoryKairos$$ExternalSyntheticLambda22
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj3) {
                        int i5 = intValue2;
                        Set set2 = (Set) obj3;
                        switch (i) {
                            case 0:
                                int i6 = DemoMobileConnectionsRepositoryKairos.$r8$clinit;
                                return SetsKt___SetsKt.plus(set2, Integer.valueOf(i5));
                            default:
                                int i7 = DemoMobileConnectionsRepositoryKairos.$r8$clinit;
                                return SetsKt___SetsKt.minus(set2, Integer.valueOf(i5));
                        }
                    }
                };
            case 2:
                int i5 = DemoMobileConnectionsRepositoryKairos.$r8$clinit;
                final int intValue3 = ((Number) ((Map.Entry) obj2).getKey()).intValue();
                return new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoMobileConnectionsRepositoryKairos$$ExternalSyntheticLambda24
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj3) {
                        int i6 = DemoMobileConnectionsRepositoryKairos.$r8$clinit;
                        DemoMobileConnectionsRepositoryKairos$newRepo$1 demoMobileConnectionsRepositoryKairos$newRepo$1 = new DemoMobileConnectionsRepositoryKairos$newRepo$1(intValue3, demoMobileConnectionsRepositoryKairos);
                        DemoMobileConnectionsRepositoryKairos demoMobileConnectionsRepositoryKairos2 = demoMobileConnectionsRepositoryKairos$newRepo$1.this$0;
                        TableLogBufferFactory tableLogBufferFactory = demoMobileConnectionsRepositoryKairos2.logFactory;
                        StringBuilder sb = new StringBuilder("DemoMobileConnectionLog[");
                        int i7 = demoMobileConnectionsRepositoryKairos$newRepo$1.$subId;
                        sb.append(i7);
                        sb.append("]");
                        TableLogBuffer orCreate = tableLogBufferFactory.getOrCreate(100, sb.toString());
                        int i8 = demoMobileConnectionsRepositoryKairos$newRepo$1.$subId;
                        DemoMobileConnectionRepositoryKairos demoMobileConnectionRepositoryKairos = new DemoMobileConnectionRepositoryKairos(i7, orCreate, FilterKt.filterPresent(EventsKt.mapCheap(EventsKt.mapCheap(demoMobileConnectionsRepositoryKairos2.mobileEventsBySubId.get(Integer.valueOf(i8)), new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoMobileConnectionsRepositoryKairos$newRepo$1$create$$inlined$filterIsInstance$1
                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj4, Object obj5) {
                                if (!(obj5 instanceof FakeNetworkEventModel.Mobile)) {
                                    obj5 = null;
                                }
                                return (FakeNetworkEventModel.Mobile) obj5;
                            }
                        }), new FilterKt$$ExternalSyntheticLambda0())), EventsKt.mapNotNull(demoMobileConnectionsRepositoryKairos2.wifiEvents, new DemoMobileConnectionsRepositoryKairos$$ExternalSyntheticLambda2(10)), demoMobileConnectionsRepositoryKairos2.wifiEventsBySubId.get(Integer.valueOf(i8)), demoMobileConnectionsRepositoryKairos2.mobileMappingsReverseLookup);
                        demoMobileConnectionRepositoryKairos.activate((BuildScope) obj3);
                        return demoMobileConnectionRepositoryKairos;
                    }
                };
            default:
                TransactionScope transactionScope2 = (TransactionScope) obj;
                FakeNetworkEventModel fakeNetworkEventModel2 = (FakeNetworkEventModel) obj2;
                int i6 = DemoMobileConnectionsRepositoryKairos.$r8$clinit;
                if (fakeNetworkEventModel2 == null) {
                    return null;
                }
                Integer subId = fakeNetworkEventModel2.getSubId();
                if (subId == null) {
                    subId = (Integer) transactionScope2.sample(demoMobileConnectionsRepositoryKairos.lastSeenSubId);
                }
                if (subId != null) {
                    return new Pair(Integer.valueOf(subId.intValue()), fakeNetworkEventModel2);
                }
                return null;
        }
    }
}
