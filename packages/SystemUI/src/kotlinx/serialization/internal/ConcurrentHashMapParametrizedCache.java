package kotlinx.serialization.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Result;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.ClassBasedDeclarationContainer;
import kotlin.reflect.KClass;
import kotlin.reflect.KType;
import kotlinx.serialization.KSerializer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class ConcurrentHashMapParametrizedCache implements ParametrizedSerializerCache {
    public final ConcurrentHashMap cache = new ConcurrentHashMap();
    public final Function2 compute;

    public ConcurrentHashMapParametrizedCache(Function2 function2) {
        this.compute = function2;
    }

    @Override // kotlinx.serialization.internal.ParametrizedSerializerCache
    /* renamed from: get-gIAlu-s */
    public final Object mo3467getgIAlus(KClass kClass, List list) {
        Object failure;
        Object putIfAbsent;
        ConcurrentHashMap concurrentHashMap = this.cache;
        Class jClass = ((ClassBasedDeclarationContainer) kClass).getJClass();
        Object obj = concurrentHashMap.get(jClass);
        if (obj == null && (putIfAbsent = concurrentHashMap.putIfAbsent(jClass, (obj = new ParametrizedCacheEntry()))) != null) {
            obj = putIfAbsent;
        }
        ParametrizedCacheEntry parametrizedCacheEntry = (ParametrizedCacheEntry) obj;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(new KTypeWrapper((KType) it.next()));
        }
        ConcurrentHashMap concurrentHashMap2 = parametrizedCacheEntry.serializers;
        Object obj2 = concurrentHashMap2.get(arrayList);
        if (obj2 == null) {
            try {
                int i = Result.$r8$clinit;
                failure = (KSerializer) this.compute.invoke(kClass, list);
            } catch (Throwable th) {
                int i2 = Result.$r8$clinit;
                failure = new Result.Failure(th);
            }
            Result m3421boximpl = Result.m3421boximpl(failure);
            Object putIfAbsent2 = concurrentHashMap2.putIfAbsent(arrayList, m3421boximpl);
            obj2 = putIfAbsent2 == null ? m3421boximpl : putIfAbsent2;
        }
        return ((Result) obj2).m3423unboximpl();
    }
}
