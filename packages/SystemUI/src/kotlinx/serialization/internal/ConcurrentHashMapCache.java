package kotlinx.serialization.internal;

import java.util.concurrent.ConcurrentHashMap;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.ClassBasedDeclarationContainer;
import kotlin.reflect.KClass;
import kotlinx.serialization.KSerializer;

/* loaded from: classes4.dex */
public final class ConcurrentHashMapCache implements SerializerCache {
    public final ConcurrentHashMap cache = new ConcurrentHashMap();
    public final Function1 compute;

    public ConcurrentHashMapCache(Function1 function1) {
        this.compute = function1;
    }

    @Override // kotlinx.serialization.internal.SerializerCache
    public final KSerializer get(KClass kClass) {
        Object objPutIfAbsent;
        ConcurrentHashMap concurrentHashMap = this.cache;
        Class jClass = ((ClassBasedDeclarationContainer) kClass).getJClass();
        Object cacheEntry = concurrentHashMap.get(jClass);
        if (cacheEntry == null && (objPutIfAbsent = concurrentHashMap.putIfAbsent(jClass, (cacheEntry = new CacheEntry((KSerializer) this.compute.mo781invoke(kClass))))) != null) {
            cacheEntry = objPutIfAbsent;
        }
        return ((CacheEntry) cacheEntry).serializer;
    }
}
