package kotlinx.serialization.internal;

import java.util.Arrays;
import java.util.Iterator;
import kotlinx.serialization.descriptors.SerialDescriptor;
import kotlinx.serialization.descriptors.SerialDescriptorKt$elementDescriptors$1$1;
import kotlinx.serialization.descriptors.SerialDescriptorKt$special$$inlined$Iterable$1;
import kotlinx.serialization.descriptors.SerialKind;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class PluginGeneratedSerialDescriptorKt {
    public static final int hashCodeImpl(SerialDescriptor serialDescriptor, SerialDescriptor[] serialDescriptorArr) {
        int hashCode = (serialDescriptor.getSerialName().hashCode() * 31) + Arrays.hashCode(serialDescriptorArr);
        SerialDescriptorKt$special$$inlined$Iterable$1 serialDescriptorKt$special$$inlined$Iterable$1 = new SerialDescriptorKt$special$$inlined$Iterable$1(serialDescriptor);
        Iterator it = serialDescriptorKt$special$$inlined$Iterable$1.iterator();
        int i = 1;
        int i2 = 1;
        while (true) {
            SerialDescriptorKt$elementDescriptors$1$1 serialDescriptorKt$elementDescriptors$1$1 = (SerialDescriptorKt$elementDescriptors$1$1) it;
            int i3 = 0;
            if (!serialDescriptorKt$elementDescriptors$1$1.hasNext()) {
                break;
            }
            int i4 = i2 * 31;
            String serialName = ((SerialDescriptor) serialDescriptorKt$elementDescriptors$1$1.next()).getSerialName();
            if (serialName != null) {
                i3 = serialName.hashCode();
            }
            i2 = i4 + i3;
        }
        Iterator it2 = serialDescriptorKt$special$$inlined$Iterable$1.iterator();
        while (true) {
            SerialDescriptorKt$elementDescriptors$1$1 serialDescriptorKt$elementDescriptors$1$12 = (SerialDescriptorKt$elementDescriptors$1$1) it2;
            if (!serialDescriptorKt$elementDescriptors$1$12.hasNext()) {
                return (((hashCode * 31) + i2) * 31) + i;
            }
            int i5 = i * 31;
            SerialKind kind = ((SerialDescriptor) serialDescriptorKt$elementDescriptors$1$12.next()).getKind();
            i = i5 + (kind != null ? kind.hashCode() : 0);
        }
    }
}
