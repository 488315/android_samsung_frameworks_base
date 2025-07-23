package kotlinx.serialization;

import java.util.List;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.ClassBasedDeclarationContainer;
import kotlin.reflect.KClass;
import kotlin.reflect.KType;
import kotlinx.serialization.internal.CachingKt;
import kotlinx.serialization.internal.ClassValueCache;
import kotlinx.serialization.internal.ClassValueParametrizedCache;
import kotlinx.serialization.internal.ConcurrentHashMapCache;
import kotlinx.serialization.internal.ConcurrentHashMapParametrizedCache;
import kotlinx.serialization.internal.KTypeWrapper;
import kotlinx.serialization.internal.NullableSerializer;
import kotlinx.serialization.internal.ParametrizedSerializerCache;
import kotlinx.serialization.internal.SerializerCache;
import kotlinx.serialization.modules.SerializersModuleKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class SerializersCacheKt {
    public static final ParametrizedSerializerCache PARAMETRIZED_SERIALIZERS_CACHE;
    public static final ParametrizedSerializerCache PARAMETRIZED_SERIALIZERS_CACHE_NULLABLE;
    public static final SerializerCache SERIALIZERS_CACHE;
    public static final SerializerCache SERIALIZERS_CACHE_NULLABLE;

    static {
        final int i = 0;
        Function1 function1 = new Function1() { // from class: kotlinx.serialization.SerializersCacheKt$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                KClass kClass = (KClass) obj;
                switch (i) {
                    case 0:
                        KSerializer serializerOrNull = SerializersKt.serializerOrNull(kClass);
                        if (serializerOrNull != null) {
                            return serializerOrNull;
                        }
                        if (((ClassBasedDeclarationContainer) kClass).getJClass().isInterface()) {
                            return new PolymorphicSerializer(kClass);
                        }
                        return null;
                    default:
                        KSerializer serializerOrNull2 = SerializersKt.serializerOrNull(kClass);
                        if (serializerOrNull2 == null) {
                            serializerOrNull2 = ((ClassBasedDeclarationContainer) kClass).getJClass().isInterface() ? new PolymorphicSerializer(kClass) : null;
                        }
                        if (serializerOrNull2 != null) {
                            return serializerOrNull2.getDescriptor().isNullable() ? serializerOrNull2 : new NullableSerializer(serializerOrNull2);
                        }
                        return null;
                }
            }
        };
        boolean z = CachingKt.useClassValue;
        SERIALIZERS_CACHE = z ? new ClassValueCache(function1) : new ConcurrentHashMapCache(function1);
        final int i2 = 1;
        Function1 function12 = new Function1() { // from class: kotlinx.serialization.SerializersCacheKt$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                KClass kClass = (KClass) obj;
                switch (i2) {
                    case 0:
                        KSerializer serializerOrNull = SerializersKt.serializerOrNull(kClass);
                        if (serializerOrNull != null) {
                            return serializerOrNull;
                        }
                        if (((ClassBasedDeclarationContainer) kClass).getJClass().isInterface()) {
                            return new PolymorphicSerializer(kClass);
                        }
                        return null;
                    default:
                        KSerializer serializerOrNull2 = SerializersKt.serializerOrNull(kClass);
                        if (serializerOrNull2 == null) {
                            serializerOrNull2 = ((ClassBasedDeclarationContainer) kClass).getJClass().isInterface() ? new PolymorphicSerializer(kClass) : null;
                        }
                        if (serializerOrNull2 != null) {
                            return serializerOrNull2.getDescriptor().isNullable() ? serializerOrNull2 : new NullableSerializer(serializerOrNull2);
                        }
                        return null;
                }
            }
        };
        SERIALIZERS_CACHE_NULLABLE = z ? new ClassValueCache(function12) : new ConcurrentHashMapCache(function12);
        final int i3 = 0;
        Function2 function2 = new Function2() { // from class: kotlinx.serialization.SerializersCacheKt$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                KClass kClass = (KClass) obj;
                final List list = (List) obj2;
                switch (i3) {
                    case 0:
                        List serializersForParameters = SerializersKt.serializersForParameters(SerializersModuleKt.EmptySerializersModule, list, true);
                        serializersForParameters.getClass();
                        final int i4 = 0;
                        return SerializersKt.parametrizedSerializerOrNull(kClass, serializersForParameters, new Function0() { // from class: kotlinx.serialization.SerializersCacheKt$$ExternalSyntheticLambda4
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                int i5 = i4;
                                List list2 = list;
                                switch (i5) {
                                }
                                return ((KTypeWrapper) ((KType) list2.get(0))).getClassifier();
                            }
                        });
                    default:
                        List serializersForParameters2 = SerializersKt.serializersForParameters(SerializersModuleKt.EmptySerializersModule, list, true);
                        serializersForParameters2.getClass();
                        final int i5 = 1;
                        KSerializer parametrizedSerializerOrNull = SerializersKt.parametrizedSerializerOrNull(kClass, serializersForParameters2, new Function0() { // from class: kotlinx.serialization.SerializersCacheKt$$ExternalSyntheticLambda4
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                int i52 = i5;
                                List list2 = list;
                                switch (i52) {
                                }
                                return ((KTypeWrapper) ((KType) list2.get(0))).getClassifier();
                            }
                        });
                        if (parametrizedSerializerOrNull != null) {
                            return parametrizedSerializerOrNull.getDescriptor().isNullable() ? parametrizedSerializerOrNull : new NullableSerializer(parametrizedSerializerOrNull);
                        }
                        return null;
                }
            }
        };
        PARAMETRIZED_SERIALIZERS_CACHE = z ? new ClassValueParametrizedCache(function2) : new ConcurrentHashMapParametrizedCache(function2);
        final int i4 = 1;
        Function2 function22 = new Function2() { // from class: kotlinx.serialization.SerializersCacheKt$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                KClass kClass = (KClass) obj;
                final List list = (List) obj2;
                switch (i4) {
                    case 0:
                        List serializersForParameters = SerializersKt.serializersForParameters(SerializersModuleKt.EmptySerializersModule, list, true);
                        serializersForParameters.getClass();
                        final int i42 = 0;
                        return SerializersKt.parametrizedSerializerOrNull(kClass, serializersForParameters, new Function0() { // from class: kotlinx.serialization.SerializersCacheKt$$ExternalSyntheticLambda4
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                int i52 = i42;
                                List list2 = list;
                                switch (i52) {
                                }
                                return ((KTypeWrapper) ((KType) list2.get(0))).getClassifier();
                            }
                        });
                    default:
                        List serializersForParameters2 = SerializersKt.serializersForParameters(SerializersModuleKt.EmptySerializersModule, list, true);
                        serializersForParameters2.getClass();
                        final int i5 = 1;
                        KSerializer parametrizedSerializerOrNull = SerializersKt.parametrizedSerializerOrNull(kClass, serializersForParameters2, new Function0() { // from class: kotlinx.serialization.SerializersCacheKt$$ExternalSyntheticLambda4
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                int i52 = i5;
                                List list2 = list;
                                switch (i52) {
                                }
                                return ((KTypeWrapper) ((KType) list2.get(0))).getClassifier();
                            }
                        });
                        if (parametrizedSerializerOrNull != null) {
                            return parametrizedSerializerOrNull.getDescriptor().isNullable() ? parametrizedSerializerOrNull : new NullableSerializer(parametrizedSerializerOrNull);
                        }
                        return null;
                }
            }
        };
        PARAMETRIZED_SERIALIZERS_CACHE_NULLABLE = z ? new ClassValueParametrizedCache(function22) : new ConcurrentHashMapParametrizedCache(function22);
    }
}
