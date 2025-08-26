package kotlinx.serialization.descriptors;

import kotlin.reflect.KClass;
import kotlinx.serialization.internal.SerialDescriptorForNullable;

/* loaded from: classes4.dex */
public abstract class ContextAwareKt {
    public static final KClass getCapturedKClass(SerialDescriptor serialDescriptor) {
        if (serialDescriptor instanceof ContextDescriptor) {
            return ((ContextDescriptor) serialDescriptor).kClass;
        }
        if (serialDescriptor instanceof SerialDescriptorForNullable) {
            return getCapturedKClass(((SerialDescriptorForNullable) serialDescriptor).original);
        }
        return null;
    }
}
