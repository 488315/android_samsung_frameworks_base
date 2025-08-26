package com.android.systemui.scene.domain.resolver;

import dagger.internal.Provider;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.MapsKt__MapsJVMKt;

/* loaded from: classes2.dex */
public final class SceneResolverModule_Companion_ProvideResolverMapFactory implements Provider {
    public final Provider resolverSetProvider;

    public SceneResolverModule_Companion_ProvideResolverMapFactory(Provider provider) {
        this.resolverSetProvider = provider;
    }

    public static Map provideResolverMap(Set set) {
        SceneResolverModule.Companion.getClass();
        Set set2 = set;
        int iMapCapacity = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(set2, 10));
        if (iMapCapacity < 16) {
            iMapCapacity = 16;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap(iMapCapacity);
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
