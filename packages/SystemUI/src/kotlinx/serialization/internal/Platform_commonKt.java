package kotlinx.serialization.internal;

import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import kotlin.jvm.internal.ClassReference;
import kotlin.reflect.KClass;
import kotlin.reflect.KType;
import kotlinx.serialization.SerializationException;
import kotlinx.serialization.descriptors.SerialDescriptor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class Platform_commonKt {
    public static final SerialDescriptor[] EMPTY_DESCRIPTOR_ARRAY = new SerialDescriptor[0];

    public static final Set cachedSerialNames(SerialDescriptor serialDescriptor) {
        if (serialDescriptor instanceof CachedNames) {
            return ((CachedNames) serialDescriptor).getSerialNames();
        }
        HashSet hashSet = new HashSet(serialDescriptor.getElementsCount());
        int elementsCount = serialDescriptor.getElementsCount();
        for (int i = 0; i < elementsCount; i++) {
            hashSet.add(serialDescriptor.getElementName(i));
        }
        return hashSet;
    }

    public static final SerialDescriptor[] compactArray(List list) {
        SerialDescriptor[] serialDescriptorArr;
        List list2 = list;
        if (list2 == null || list2.isEmpty()) {
            list = null;
        }
        return (list == null || (serialDescriptorArr = (SerialDescriptor[]) list.toArray(new SerialDescriptor[0])) == null) ? EMPTY_DESCRIPTOR_ARRAY : serialDescriptorArr;
    }

    public static final KClass kclass(KType kType) {
        KClass classifier = ((KTypeWrapper) kType).getClassifier();
        if (classifier instanceof KClass) {
            return classifier;
        }
        throw new IllegalArgumentException("Only KClass supported as classifier, got " + classifier);
    }

    public static final void serializerNotRegistered(KClass kClass) {
        String simpleName = ((ClassReference) kClass).getSimpleName();
        if (simpleName == null) {
            simpleName = "<local class name not available>";
        }
        throw new SerializationException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Serializer for class '", simpleName, "' is not found.\nPlease ensure that class is marked as '@Serializable' and that the serialization compiler plugin is applied.\n"));
    }
}
