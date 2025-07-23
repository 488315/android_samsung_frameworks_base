package com.android.systemui.scene.domain.resolver;

import dagger.internal.Provider;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.MapsKt__MapsJVMKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SceneResolverModule_Companion_ProvideResolverMapFactory implements Provider {
    public final Provider resolverSetProvider;

    public SceneResolverModule_Companion_ProvideResolverMapFactory(Provider provider) {
        this.resolverSetProvider = provider;
    }

    public static Map provideResolverMap(Set set) {
        SceneResolverModule.Companion.getClass();
        Set set2 = set;
        int mapCapacity = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(set2, 10));
        if (mapCapacity < 16) {
            mapCapacity = 16;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap(mapCapacity);
        for (Object obj : set2) {
            linkedHashMap.put(((HomeSceneFamilyResolver) ((SceneResolver) obj)).targetFamily, obj);
        }
        return linkedHashMap;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideResolverMap((Set) this.resolverSetProvider.get());
    }
}
