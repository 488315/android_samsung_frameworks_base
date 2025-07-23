package kotlinx.serialization.descriptors;

import java.util.ArrayList;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.serialization.descriptors.StructureKind;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class SerialDescriptorsKt {
    public static final SerialDescriptorImpl buildClassSerialDescriptor(String str, SerialDescriptor[] serialDescriptorArr, Function1 function1) {
        if (StringsKt__StringsKt.isBlank(str)) {
            throw new IllegalArgumentException("Blank serial names are prohibited");
        }
        ClassSerialDescriptorBuilder classSerialDescriptorBuilder = new ClassSerialDescriptorBuilder(str);
        function1.mo779invoke(classSerialDescriptorBuilder);
        return new SerialDescriptorImpl(str, StructureKind.CLASS.INSTANCE, ((ArrayList) classSerialDescriptorBuilder.elementNames).size(), ArraysKt___ArraysKt.toList(serialDescriptorArr), classSerialDescriptorBuilder);
    }

    public static final SerialDescriptorImpl buildSerialDescriptor(String str, SerialKind serialKind, SerialDescriptor[] serialDescriptorArr, Function1 function1) {
        if (StringsKt__StringsKt.isBlank(str)) {
            throw new IllegalArgumentException("Blank serial names are prohibited");
        }
        if (Intrinsics.areEqual(serialKind, StructureKind.CLASS.INSTANCE)) {
            throw new IllegalArgumentException("For StructureKind.CLASS please use 'buildClassSerialDescriptor' instead");
        }
        ClassSerialDescriptorBuilder classSerialDescriptorBuilder = new ClassSerialDescriptorBuilder(str);
        function1.mo779invoke(classSerialDescriptorBuilder);
        return new SerialDescriptorImpl(str, serialKind, ((ArrayList) classSerialDescriptorBuilder.elementNames).size(), ArraysKt___ArraysKt.toList(serialDescriptorArr), classSerialDescriptorBuilder);
    }
}
