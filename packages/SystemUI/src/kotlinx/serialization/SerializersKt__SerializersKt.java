package kotlinx.serialization;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Result;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.ClassBasedDeclarationContainer;
import kotlin.reflect.KClass;
import kotlin.reflect.KType;
import kotlin.reflect.KTypeProjection;
import kotlinx.serialization.internal.KTypeWrapper;
import kotlinx.serialization.internal.NullableSerializer;
import kotlinx.serialization.internal.Platform_commonKt;
import kotlinx.serialization.modules.SerialModuleImpl;

/* loaded from: classes4.dex */
public abstract /* synthetic */ class SerializersKt__SerializersKt {
    /* JADX WARN: Removed duplicated region for block: B:16:0x0067  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00f3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final KSerializer serializerByKTypeImpl$SerializersKt__SerializersKt(SerialModuleImpl serialModuleImpl, KType kType, boolean z) {
        KSerializer kSerializer;
        KSerializer contextual;
        PolymorphicSerializer polymorphicSerializer;
        KClass kclass = Platform_commonKt.kclass(kType);
        KTypeWrapper kTypeWrapper = (KTypeWrapper) kType;
        boolean zIsMarkedNullable = kTypeWrapper.isMarkedNullable();
        List arguments = kTypeWrapper.getArguments();
        final ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arguments, 10));
        Iterator it = arguments.iterator();
        while (it.hasNext()) {
            KType kType2 = ((KTypeProjection) it.next()).type;
            if (kType2 == null) {
                throw new IllegalArgumentException(("Star projections in type arguments are not allowed, but had " + kType2).toString());
            }
            arrayList.add(kType2);
        }
        if (arrayList.isEmpty()) {
            if (!((ClassBasedDeclarationContainer) kclass).getJClass().isInterface() || serialModuleImpl.getContextual(kclass, EmptyList.INSTANCE) == null) {
                if (zIsMarkedNullable) {
                    kSerializer = SerializersCacheKt.SERIALIZERS_CACHE_NULLABLE.get(kclass);
                } else {
                    kSerializer = SerializersCacheKt.SERIALIZERS_CACHE.get(kclass);
                    if (kSerializer == null) {
                        kSerializer = null;
                    }
                }
            }
        } else if (!serialModuleImpl.hasInterfaceContextualSerializers) {
            Object objMo3487getgIAlus = !zIsMarkedNullable ? SerializersCacheKt.PARAMETRIZED_SERIALIZERS_CACHE.mo3487getgIAlus(kclass, arrayList) : SerializersCacheKt.PARAMETRIZED_SERIALIZERS_CACHE_NULLABLE.mo3487getgIAlus(kclass, arrayList);
            int i = Result.$r8$clinit;
            if (objMo3487getgIAlus instanceof Result.Failure) {
                objMo3487getgIAlus = null;
            }
            kSerializer = (KSerializer) objMo3487getgIAlus;
        }
        if (kSerializer != null) {
            return kSerializer;
        }
        if (arrayList.isEmpty()) {
            contextual = SerializersKt.serializerOrNull(kclass);
            if (contextual == null && (contextual = serialModuleImpl.getContextual(kclass, EmptyList.INSTANCE)) == null) {
                if (((ClassBasedDeclarationContainer) kclass).getJClass().isInterface()) {
                    polymorphicSerializer = new PolymorphicSerializer(kclass);
                    contextual = polymorphicSerializer;
                }
                contextual = null;
            }
            if (contextual != null) {
                return (!zIsMarkedNullable || contextual.getDescriptor().isNullable()) ? contextual : new NullableSerializer(contextual);
            }
        } else {
            List listSerializersForParameters = SerializersKt.serializersForParameters(serialModuleImpl, arrayList, z);
            if (listSerializersForParameters != null) {
                KSerializer kSerializerParametrizedSerializerOrNull = SerializersKt.parametrizedSerializerOrNull(kclass, listSerializersForParameters, new Function0() { // from class: kotlinx.serialization.SerializersKt__SerializersKt$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return ((KTypeWrapper) ((KType) arrayList.get(0))).getClassifier();
                    }
                });
                if (kSerializerParametrizedSerializerOrNull == null) {
                    contextual = serialModuleImpl.getContextual(kclass, listSerializersForParameters);
                    if (contextual == null) {
                        if (((ClassBasedDeclarationContainer) kclass).getJClass().isInterface()) {
                            polymorphicSerializer = new PolymorphicSerializer(kclass);
                            contextual = polymorphicSerializer;
                        }
                        contextual = null;
                    }
                } else {
                    contextual = kSerializerParametrizedSerializerOrNull;
                }
                if (contextual != null) {
                }
            }
        }
        return null;
    }
}
