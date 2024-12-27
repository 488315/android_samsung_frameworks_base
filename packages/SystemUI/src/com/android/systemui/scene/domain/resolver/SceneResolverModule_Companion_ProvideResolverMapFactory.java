package com.android.systemui.scene.domain.resolver;

import dagger.internal.Provider;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.MapsKt__MapsJVMKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SceneResolverModule_Companion_ProvideResolverMapFactory implements Provider {
    public final javax.inject.Provider resolverSetProvider;

    public SceneResolverModule_Companion_ProvideResolverMapFactory(javax.inject.Provider provider) {
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
            linkedHashMap.put(((SceneResolver) obj).getTargetFamily(), obj);
        }
        return linkedHashMap;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideResolverMap((Set) this.resolverSetProvider.get());
    }
}
