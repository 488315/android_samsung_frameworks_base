package kotlinx.serialization;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.ClassBasedDeclarationContainer;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import kotlin.reflect.KType;
import kotlinx.serialization.internal.ArrayListSerializer;
import kotlinx.serialization.internal.HashMapSerializer;
import kotlinx.serialization.internal.HashSetSerializer;
import kotlinx.serialization.internal.LinkedHashMapSerializer;
import kotlinx.serialization.internal.LinkedHashSetSerializer;
import kotlinx.serialization.internal.MapEntrySerializer;
import kotlinx.serialization.internal.PairSerializer;
import kotlinx.serialization.internal.PlatformKt;
import kotlinx.serialization.internal.Platform_commonKt;
import kotlinx.serialization.internal.PrimitivesKt;
import kotlinx.serialization.internal.ReferenceArraySerializer;
import kotlinx.serialization.internal.TripleSerializer;
import kotlinx.serialization.modules.SerialModuleImpl;

/* loaded from: classes4.dex */
public abstract class SerializersKt {
    public static final KSerializer parametrizedSerializerOrNull(KClass kClass, List list, Function0 function0) {
        KSerializer arrayListSerializer;
        KSerializer referenceArraySerializer;
        if (Intrinsics.areEqual(kClass, Reflection.getOrCreateKotlinClass(Collection.class)) || Intrinsics.areEqual(kClass, Reflection.getOrCreateKotlinClass(List.class)) || Intrinsics.areEqual(kClass, Reflection.getOrCreateKotlinClass(List.class)) || Intrinsics.areEqual(kClass, Reflection.getOrCreateKotlinClass(ArrayList.class))) {
            arrayListSerializer = new ArrayListSerializer((KSerializer) ((ArrayList) list).get(0));
        } else if (Intrinsics.areEqual(kClass, Reflection.getOrCreateKotlinClass(HashSet.class))) {
            arrayListSerializer = new HashSetSerializer((KSerializer) ((ArrayList) list).get(0));
        } else if (Intrinsics.areEqual(kClass, Reflection.getOrCreateKotlinClass(Set.class)) || Intrinsics.areEqual(kClass, Reflection.getOrCreateKotlinClass(Set.class)) || Intrinsics.areEqual(kClass, Reflection.getOrCreateKotlinClass(LinkedHashSet.class))) {
            arrayListSerializer = new LinkedHashSetSerializer((KSerializer) ((ArrayList) list).get(0));
        } else if (Intrinsics.areEqual(kClass, Reflection.getOrCreateKotlinClass(HashMap.class))) {
            ArrayList arrayList = (ArrayList) list;
            arrayListSerializer = new HashMapSerializer((KSerializer) arrayList.get(0), (KSerializer) arrayList.get(1));
        } else if (Intrinsics.areEqual(kClass, Reflection.getOrCreateKotlinClass(Map.class)) || Intrinsics.areEqual(kClass, Reflection.getOrCreateKotlinClass(Map.class)) || Intrinsics.areEqual(kClass, Reflection.getOrCreateKotlinClass(LinkedHashMap.class))) {
            ArrayList arrayList2 = (ArrayList) list;
            arrayListSerializer = new LinkedHashMapSerializer((KSerializer) arrayList2.get(0), (KSerializer) arrayList2.get(1));
        } else {
            if (Intrinsics.areEqual(kClass, Reflection.getOrCreateKotlinClass(Map.Entry.class))) {
                ArrayList arrayList3 = (ArrayList) list;
                referenceArraySerializer = new MapEntrySerializer((KSerializer) arrayList3.get(0), (KSerializer) arrayList3.get(1));
            } else if (Intrinsics.areEqual(kClass, Reflection.getOrCreateKotlinClass(Pair.class))) {
                ArrayList arrayList4 = (ArrayList) list;
                referenceArraySerializer = new PairSerializer((KSerializer) arrayList4.get(0), (KSerializer) arrayList4.get(1));
            } else if (Intrinsics.areEqual(kClass, Reflection.getOrCreateKotlinClass(Triple.class))) {
                ArrayList arrayList5 = (ArrayList) list;
                arrayListSerializer = new TripleSerializer((KSerializer) arrayList5.get(0), (KSerializer) arrayList5.get(1), (KSerializer) arrayList5.get(2));
            } else if (((ClassBasedDeclarationContainer) kClass).getJClass().isArray()) {
                referenceArraySerializer = new ReferenceArraySerializer((KClass) function0.invoke(), (KSerializer) ((ArrayList) list).get(0));
            } else {
                arrayListSerializer = null;
            }
            arrayListSerializer = referenceArraySerializer;
        }
        if (arrayListSerializer != null) {
            return arrayListSerializer;
        }
        KSerializer[] kSerializerArr = (KSerializer[]) list.toArray(new KSerializer[0]);
        return PlatformKt.constructSerializerForGivenTypeArgs(kClass, (KSerializer[]) Arrays.copyOf(kSerializerArr, kSerializerArr.length));
    }

    public static final KSerializer serializer(SerialModuleImpl serialModuleImpl, KType kType) {
        KSerializer kSerializerSerializerByKTypeImpl$SerializersKt__SerializersKt = SerializersKt__SerializersKt.serializerByKTypeImpl$SerializersKt__SerializersKt(serialModuleImpl, kType, true);
        if (kSerializerSerializerByKTypeImpl$SerializersKt__SerializersKt != null) {
            return kSerializerSerializerByKTypeImpl$SerializersKt__SerializersKt;
        }
        Platform_commonKt.serializerNotRegistered(Platform_commonKt.kclass(kType));
        throw null;
    }

    public static final KSerializer serializerOrNull(KClass kClass) {
        KSerializer kSerializerConstructSerializerForGivenTypeArgs = PlatformKt.constructSerializerForGivenTypeArgs(kClass, new KSerializer[0]);
        return kSerializerConstructSerializerForGivenTypeArgs == null ? (KSerializer) PrimitivesKt.BUILTIN_SERIALIZERS.get(kClass) : kSerializerConstructSerializerForGivenTypeArgs;
    }

    public static final List serializersForParameters(SerialModuleImpl serialModuleImpl, List list, boolean z) {
        if (z) {
            List list2 = list;
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
            Iterator it = list2.iterator();
            while (it.hasNext()) {
                arrayList.add(serializer(serialModuleImpl, (KType) it.next()));
            }
            return arrayList;
        }
        List list3 = list;
        ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list3, 10));
        Iterator it2 = list3.iterator();
        while (it2.hasNext()) {
            KSerializer kSerializerSerializerByKTypeImpl$SerializersKt__SerializersKt = SerializersKt__SerializersKt.serializerByKTypeImpl$SerializersKt__SerializersKt(serialModuleImpl, (KType) it2.next(), false);
            if (kSerializerSerializerByKTypeImpl$SerializersKt__SerializersKt == null) {
                return null;
            }
            arrayList2.add(kSerializerSerializerByKTypeImpl$SerializersKt__SerializersKt);
        }
        return arrayList2;
    }

    public static final KSerializer serializer(KClass kClass) {
        KSerializer kSerializerSerializerOrNull = serializerOrNull(kClass);
        if (kSerializerSerializerOrNull != null) {
            return kSerializerSerializerOrNull;
        }
        Platform_commonKt.serializerNotRegistered(kClass);
        throw null;
    }
}
