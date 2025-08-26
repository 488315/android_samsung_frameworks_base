package kotlinx.serialization.internal;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.ClassBasedDeclarationContainer;
import kotlin.reflect.KClass;
import kotlinx.serialization.KSerializer;

/* loaded from: classes4.dex */
public final class ClassValueCache implements SerializerCache {
    public final ClassValueReferences classValue = new ClassValueReferences();
    public final Function1 compute;

    public ClassValueCache(Function1 function1) {
        this.compute = function1;
    }

    @Override // kotlinx.serialization.internal.SerializerCache
    public final KSerializer get(final KClass kClass) {
        MutableSoftReference mutableSoftReference = (MutableSoftReference) this.classValue.get(((ClassBasedDeclarationContainer) kClass).getJClass());
        Object orSetWithLock = mutableSoftReference.reference.get();
        if (orSetWithLock == null) {
            orSetWithLock = mutableSoftReference.getOrSetWithLock(new Function0() { // from class: kotlinx.serialization.internal.ClassValueCache$get$$inlined$getOrSet$1
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return new CacheEntry((KSerializer) this.this$0.compute.mo781invoke(kClass));
                }
            });
        }
        return ((CacheEntry) orSetWithLock).serializer;
    }
}
