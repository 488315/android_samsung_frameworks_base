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

/* loaded from: classes4.dex */
public final class ConcurrentHashMapParametrizedCache implements ParametrizedSerializerCache {
    public final ConcurrentHashMap cache = new ConcurrentHashMap();
    public final Function2 compute;

    public ConcurrentHashMapParametrizedCache(Function2 function2) {
        this.compute = function2;
    }

    @Override // kotlinx.serialization.internal.ParametrizedSerializerCache
    /* renamed from: get-gIAlu-s */
    public final Object mo3486getgIAlus(KClass kClass, List list) {
        Object failure;
        Object objPutIfAbsent;
        ConcurrentHashMap concurrentHashMap = this.cache;
        Class jClass = ((ClassBasedDeclarationContainer) kClass).getJClass();
        Object parametrizedCacheEntry = concurrentHashMap.get(jClass);
        if (parametrizedCacheEntry == null && (objPutIfAbsent = concurrentHashMap.putIfAbsent(jClass, (parametrizedCacheEntry = new ParametrizedCacheEntry()))) != null) {
            parametrizedCacheEntry = objPutIfAbsent;
        }
        ParametrizedCacheEntry parametrizedCacheEntry2 = (ParametrizedCacheEntry) parametrizedCacheEntry;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(new KTypeWrapper((KType) it.next()));
        }
        ConcurrentHashMap concurrentHashMap2 = parametrizedCacheEntry2.serializers;
        Object obj = concurrentHashMap2.get(arrayList);
        if (obj == null) {
            try {
                int i = Result.$r8$clinit;
                failure = (KSerializer) this.compute.invoke(kClass, list);
            } catch (Throwable th) {
                int i2 = Result.$r8$clinit;
                failure = new Result.Failure(th);
            }
            Result resultM3440boximpl = Result.m3440boximpl(failure);
            Object objPutIfAbsent2 = concurrentHashMap2.putIfAbsent(arrayList, resultM3440boximpl);
            obj = objPutIfAbsent2 == null ? resultM3440boximpl : objPutIfAbsent2;
        }
        return ((Result) obj).m3442unboximpl();
    }
}
