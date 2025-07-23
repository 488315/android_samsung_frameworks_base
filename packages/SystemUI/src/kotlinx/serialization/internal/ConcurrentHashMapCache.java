package kotlinx.serialization.internal;

import java.util.concurrent.ConcurrentHashMap;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.ClassBasedDeclarationContainer;
import kotlin.reflect.KClass;
import kotlinx.serialization.KSerializer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class ConcurrentHashMapCache implements SerializerCache {
    public final ConcurrentHashMap cache = new ConcurrentHashMap();
    public final Function1 compute;

    public ConcurrentHashMapCache(Function1 function1) {
        this.compute = function1;
    }

    @Override // kotlinx.serialization.internal.SerializerCache
    public final KSerializer get(KClass kClass) {
        Object putIfAbsent;
        ConcurrentHashMap concurrentHashMap = this.cache;
        Class jClass = ((ClassBasedDeclarationContainer) kClass).getJClass();
        Object obj = concurrentHashMap.get(jClass);
        if (obj == null && (putIfAbsent = concurrentHashMap.putIfAbsent(jClass, (obj = new CacheEntry((KSerializer) this.compute.mo779invoke(kClass))))) != null) {
            obj = putIfAbsent;
        }
        return ((CacheEntry) obj).serializer;
    }
}
