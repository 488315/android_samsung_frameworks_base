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
                EnumSerializer enumSerializer = this.f$0;
                Object enumDescriptor = enumSerializer.overriddenDescriptor;
                if (enumDescriptor == 0) {
                    Enum[] enumArr2 = enumSerializer.values;
                    enumDescriptor = new EnumDescriptor(str, enumArr2.length);
                    for (Enum r0 : enumArr2) {
                        String strName = r0.name();
                        int i = enumDescriptor.added + 1;
                        enumDescriptor.added = i;
                        String[] strArr = enumDescriptor.names;
                        strArr[i] = strName;
                        enumDescriptor.elementsOptionality[i] = false;
                        enumDescriptor.propertiesAnnotations[i] = null;
                        if (i == enumDescriptor.elementsCount - 1) {
                            HashMap map = new HashMap();
                            int length = strArr.length;
                            for (int i2 = 0; i2 < length; i2++) {
                                map.put(strArr[i2], Integer.valueOf(i2));
                            }
                            enumDescriptor.indices = map;
                        }
                    }
                }
                return enumDescriptor;
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
        int iIndexOf = ArraysKt___ArraysKt.indexOf(enumArr, r5);
        if (iIndexOf != -1) {
            getDescriptor();
            abstractEncoder.encodeValue(Integer.valueOf(iIndexOf));
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
