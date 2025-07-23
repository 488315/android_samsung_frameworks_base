package kotlinx.serialization.internal;

import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import java.util.Arrays;
import java.util.HashMap;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.functions.Function0;
import kotlinx.serialization.KSerializer;
import kotlinx.serialization.SerializationException;
import kotlinx.serialization.descriptors.SerialDescriptor;
import kotlinx.serialization.encoding.AbstractEncoder;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class EnumSerializer implements KSerializer {
    public final Lazy descriptor$delegate;
    public final SerialDescriptor overriddenDescriptor;
    public final Enum[] values;

    public EnumSerializer(final String str, Enum<Object>[] enumArr) {
        this.values = enumArr;
        this.descriptor$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: kotlinx.serialization.internal.EnumSerializer$$ExternalSyntheticLambda0
            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r1v0, types: [kotlinx.serialization.descriptors.SerialDescriptor] */
            /* JADX WARN: Type inference failed for: r1v2, types: [kotlinx.serialization.internal.EnumDescriptor, kotlinx.serialization.internal.PluginGeneratedSerialDescriptor] */
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                EnumSerializer enumSerializer = EnumSerializer.this;
                Object obj = enumSerializer.overriddenDescriptor;
                if (obj == 0) {
                    Enum[] enumArr2 = enumSerializer.values;
                    obj = new EnumDescriptor(str, enumArr2.length);
                    for (Enum r0 : enumArr2) {
                        String name = r0.name();
                        int i = obj.added + 1;
                        obj.added = i;
                        String[] strArr = obj.names;
                        strArr[i] = name;
                        obj.elementsOptionality[i] = false;
                        obj.propertiesAnnotations[i] = null;
                        if (i == obj.elementsCount - 1) {
                            HashMap hashMap = new HashMap();
                            int length = strArr.length;
                            for (int i2 = 0; i2 < length; i2++) {
                                hashMap.put(strArr[i2], Integer.valueOf(i2));
                            }
                            obj.indices = hashMap;
                        }
                    }
                }
                return obj;
            }
        });
    }

    @Override // kotlinx.serialization.KSerializer
    public final SerialDescriptor getDescriptor() {
        return (SerialDescriptor) this.descriptor$delegate.getValue();
    }

    @Override // kotlinx.serialization.KSerializer
    public final void serialize(AbstractEncoder abstractEncoder, Object obj) {
        Enum r5 = (Enum) obj;
        Enum[] enumArr = this.values;
        int indexOf = ArraysKt___ArraysKt.indexOf(enumArr, r5);
        if (indexOf != -1) {
            getDescriptor();
            abstractEncoder.encodeValue(Integer.valueOf(indexOf));
            return;
        }
        throw new SerializationException(r5 + " is not a valid enum " + getDescriptor().getSerialName() + ", must be one of " + Arrays.toString(enumArr));
    }

    public final String toString() {
        return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("kotlinx.serialization.internal.EnumSerializer<", getDescriptor().getSerialName(), ">");
    }

    public EnumSerializer(String str, Enum<Object>[] enumArr, SerialDescriptor serialDescriptor) {
        this(str, enumArr);
        this.overriddenDescriptor = serialDescriptor;
    }
}
