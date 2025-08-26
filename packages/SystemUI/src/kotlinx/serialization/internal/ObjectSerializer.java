package kotlinx.serialization.internal;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.serialization.KSerializer;
import kotlinx.serialization.descriptors.ClassSerialDescriptorBuilder;
import kotlinx.serialization.descriptors.SerialDescriptor;
import kotlinx.serialization.descriptors.SerialDescriptorImpl;
import kotlinx.serialization.descriptors.StructureKind;
import kotlinx.serialization.encoding.AbstractEncoder;

/* loaded from: classes4.dex */
public final class ObjectSerializer implements KSerializer {
    public final List _annotations;
    public final Lazy descriptor$delegate;
    public final Object objectInstance;

    public ObjectSerializer(final String str, Object obj) {
        this.objectInstance = obj;
        this._annotations = EmptyList.INSTANCE;
        this.descriptor$delegate = LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.PUBLICATION, new Function0() { // from class: kotlinx.serialization.internal.ObjectSerializer$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                StructureKind.OBJECT object = StructureKind.OBJECT.INSTANCE;
                SerialDescriptor[] serialDescriptorArr = new SerialDescriptor[0];
                String str2 = str;
                if (StringsKt__StringsKt.isBlank(str2)) {
                    throw new IllegalArgumentException("Blank serial names are prohibited");
                }
                if (Intrinsics.areEqual(object, StructureKind.CLASS.INSTANCE)) {
                    throw new IllegalArgumentException("For StructureKind.CLASS please use 'buildClassSerialDescriptor' instead");
                }
                ClassSerialDescriptorBuilder classSerialDescriptorBuilder = new ClassSerialDescriptorBuilder(str2);
                Unit unit = Unit.INSTANCE;
                return new SerialDescriptorImpl(str2, object, ((ArrayList) classSerialDescriptorBuilder.elementNames).size(), ArraysKt___ArraysKt.toList(serialDescriptorArr), classSerialDescriptorBuilder);
            }
        });
    }

    @Override // kotlinx.serialization.KSerializer
    public final SerialDescriptor getDescriptor() {
        return (SerialDescriptor) this.descriptor$delegate.getValue();
    }

    @Override // kotlinx.serialization.KSerializer
    public final void serialize(AbstractEncoder abstractEncoder, Object obj) {
        getDescriptor();
        getDescriptor();
    }

    public ObjectSerializer(String str, Object obj, Annotation[] annotationArr) {
        this(str, obj);
        this._annotations = Arrays.asList(annotationArr);
    }
}
