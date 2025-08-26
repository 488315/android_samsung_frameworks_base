package com.android.systemui.statusbar.pipeline.mobile.data.repository.demo;

import com.android.systemui.statusbar.pipeline.mobile.data.model.SubscriptionModel;
import com.android.systemui.statusbar.pipeline.wifi.data.repository.demo.model.FakeWifiEventModel;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import kotlin.Pair;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.jvm.functions.Function2;

/* loaded from: classes3.dex */
public final /* synthetic */ class DemoMobileConnectionsRepositoryKairos$$ExternalSyntheticLambda2 implements Function2 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ DemoMobileConnectionsRepositoryKairos$$ExternalSyntheticLambda2(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        switch (this.$r8$classId) {
            case 0:
                int i = DemoMobileConnectionsRepositoryKairos.$r8$clinit;
                SubscriptionModel subscriptionModel = (SubscriptionModel) CollectionsKt___CollectionsKt.firstOrNull((Collection) obj2);
                return Integer.valueOf(subscriptionModel != null ? subscriptionModel.subscriptionId : -1);
            case 1:
                int i2 = DemoMobileConnectionsRepositoryKairos.$r8$clinit;
                Set set = (Set) obj2;
                int iMapCapacity = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(set, 10));
                if (iMapCapacity < 16) {
                    iMapCapacity = 16;
                }
                LinkedHashMap linkedHashMap = new LinkedHashMap(iMapCapacity);
                for (Object obj3 : set) {
                    linkedHashMap.put(obj3, new SubscriptionModel(((Number) obj3).intValue(), false, false, null, "demo carrier", -1, false, false, 0, false, 972, null));
                }
                return linkedHashMap;
            case 2:
                int i3 = DemoMobileConnectionsRepositoryKairos.$r8$clinit;
                return ((Map) obj2).values();
            case 3:
                int i4 = DemoMobileConnectionsRepositoryKairos.$r8$clinit;
                return Integer.valueOf(((FakeWifiEventModel.CarrierMerged) obj2).subscriptionId);
            case 4:
                FakeWifiEventModel fakeWifiEventModel = (FakeWifiEventModel) obj2;
                int i5 = DemoMobileConnectionsRepositoryKairos.$r8$clinit;
                return Boolean.valueOf((fakeWifiEventModel instanceof FakeWifiEventModel.Wifi) || (fakeWifiEventModel instanceof FakeWifiEventModel.WifiDisabled));
            case 5:
                int i6 = DemoMobileConnectionsRepositoryKairos.$r8$clinit;
                return null;
            case 6:
                int i7 = DemoMobileConnectionsRepositoryKairos.$r8$clinit;
                return Integer.valueOf(((Number) ((Pair) obj2).getFirst()).intValue());
            case 7:
                int i8 = DemoMobileConnectionsRepositoryKairos.$r8$clinit;
                return Integer.valueOf(((FakeWifiEventModel.CarrierMerged) obj2).subscriptionId);
            case 8:
                Pair pair = (Pair) obj2;
                int i9 = DemoMobileConnectionsRepositoryKairos.$r8$clinit;
                return Collections.singletonMap(pair.getFirst(), pair.getSecond());
            case 9:
                int i10 = DemoMobileConnectionsRepositoryKairos.$r8$clinit;
                return Integer.valueOf(((FakeWifiEventModel.CarrierMerged) obj2).subscriptionId);
            default:
                FakeWifiEventModel fakeWifiEventModel2 = (FakeWifiEventModel) obj2;
                if (fakeWifiEventModel2 == null || (fakeWifiEventModel2 instanceof FakeWifiEventModel.CarrierMerged)) {
                    return null;
                }
                return fakeWifiEventModel2;
        }
    }
}
