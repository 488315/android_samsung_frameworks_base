package kotlinx.serialization.internal;

import java.util.Arrays;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.serialization.descriptors.SerialDescriptor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class InlineClassDescriptor extends PluginGeneratedSerialDescriptor {
    public final boolean isInline;

    public InlineClassDescriptor(String str, GeneratedSerializer generatedSerializer) {
        super(str, generatedSerializer, 1);
        this.isInline = true;
    }

    @Override // kotlinx.serialization.internal.PluginGeneratedSerialDescriptor
    public final boolean equals(Object obj) {
        int i;
        if (this == obj) {
            return true;
        }
        if (obj instanceof InlineClassDescriptor) {
            SerialDescriptor serialDescriptor = (SerialDescriptor) obj;
            if (Intrinsics.areEqual(this.serialName, serialDescriptor.getSerialName())) {
                InlineClassDescriptor inlineClassDescriptor = (InlineClassDescriptor) obj;
                if (inlineClassDescriptor.isInline && Arrays.equals((SerialDescriptor[]) this.typeParameterDescriptors$delegate.getValue(), (SerialDescriptor[]) inlineClassDescriptor.typeParameterDescriptors$delegate.getValue())) {
                    int elementsCount = serialDescriptor.getElementsCount();
                    int i2 = this.elementsCount;
                    if (i2 == elementsCount) {
                        for (0; i < i2; i + 1) {
                            i = (Intrinsics.areEqual(getElementDescriptor(i).getSerialName(), serialDescriptor.getElementDescriptor(i).getSerialName()) && Intrinsics.areEqual(getElementDescriptor(i).getKind(), serialDescriptor.getElementDescriptor(i).getKind())) ? i + 1 : 0;
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override // kotlinx.serialization.internal.PluginGeneratedSerialDescriptor
    public final int hashCode() {
        return super.hashCode() * 31;
    }
}
