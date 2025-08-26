package kotlinx.serialization.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Result;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.ClassBasedDeclarationContainer;
import kotlin.reflect.KClass;
import kotlin.reflect.KType;
import kotlinx.serialization.KSerializer;

/* loaded from: classes4.dex */
public final class ClassValueParametrizedCache implements ParametrizedSerializerCache {
    public final ClassValueReferences classValue = new ClassValueReferences();
    public final Function2 compute;

    public ClassValueParametrizedCache(Function2 function2) {
        this.compute = function2;
    }

    @Override // kotlinx.serialization.internal.ParametrizedSerializerCache
    /* renamed from: get-gIAlu-s, reason: not valid java name */
    public final Object mo3487getgIAlus(KClass kClass, List list) {
        Object failure;
        MutableSoftReference mutableSoftReference = (MutableSoftReference) this.classValue.get(((ClassBasedDeclarationContainer) kClass).getJClass());
        Object orSetWithLock = mutableSoftReference.reference.get();
        if (orSetWithLock == null) {
            orSetWithLock = mutableSoftReference.getOrSetWithLock(new Function0() { // from class: kotlinx.serialization.internal.ClassValueParametrizedCache$get-gIAlu-s$$inlined$getOrSet$1
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return new ParametrizedCacheEntry();
                }
            });
        }
        ParametrizedCacheEntry parametrizedCacheEntry = (ParametrizedCacheEntry) orSetWithLock;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(new KTypeWrapper((KType) it.next()));
        }
        ConcurrentHashMap concurrentHashMap = parametrizedCacheEntry.serializers;
        Object obj = concurrentHashMap.get(arrayList);
        if (obj == null) {
            try {
                int i = Result.$r8$clinit;
                failure = (KSerializer) this.compute.invoke(kClass, list);
            } catch (Throwable th) {
                int i2 = Result.$r8$clinit;
                failure = new Result.Failure(th);
            }
            Result resultM3441boximpl = Result.m3441boximpl(failure);
            Object objPutIfAbsent = concurrentHashMap.putIfAbsent(arrayList, resultM3441boximpl);
            obj = objPutIfAbsent == null ? resultM3441boximpl : objPutIfAbsent;
        }
        return ((Result) obj).m3443unboximpl();
    }
}
