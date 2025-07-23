package kotlinx.serialization.descriptors;

import kotlin.reflect.KClass;
import kotlinx.serialization.internal.SerialDescriptorForNullable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
