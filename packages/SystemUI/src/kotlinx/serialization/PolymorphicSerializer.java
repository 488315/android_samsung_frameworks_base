package kotlinx.serialization;

import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
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
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.ClassReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.reflect.KClass;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.serialization.descriptors.ClassSerialDescriptorBuilder;
import kotlinx.serialization.descriptors.ContextDescriptor;
import kotlinx.serialization.descriptors.PolymorphicKind;
import kotlinx.serialization.descriptors.SerialDescriptor;
import kotlinx.serialization.descriptors.SerialDescriptorImpl;
import kotlinx.serialization.descriptors.SerialDescriptorsKt;
import kotlinx.serialization.descriptors.SerialKind;
import kotlinx.serialization.descriptors.StructureKind;
import kotlinx.serialization.internal.AbstractPolymorphicSerializer;
import kotlinx.serialization.internal.StringSerializer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class PolymorphicSerializer extends AbstractPolymorphicSerializer {
    public final List _annotations;
    public final KClass baseClass;
    public final Lazy descriptor$delegate;

    public PolymorphicSerializer(KClass kClass) {
        this.baseClass = kClass;
        this._annotations = EmptyList.INSTANCE;
        this.descriptor$delegate = LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.PUBLICATION, new Function0() { // from class: kotlinx.serialization.PolymorphicSerializer$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                final PolymorphicSerializer polymorphicSerializer = PolymorphicSerializer.this;
                return new ContextDescriptor(SerialDescriptorsKt.buildSerialDescriptor("kotlinx.serialization.Polymorphic", PolymorphicKind.OPEN.INSTANCE, new SerialDescriptor[0], new Function1() { // from class: kotlinx.serialization.PolymorphicSerializer$$ExternalSyntheticLambda1
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj) {
                        ClassSerialDescriptorBuilder classSerialDescriptorBuilder = (ClassSerialDescriptorBuilder) obj;
                        int i = StringCompanionObject.$r8$clinit;
                        StringSerializer.INSTANCE.getClass();
                        ClassSerialDescriptorBuilder.element$default(classSerialDescriptorBuilder, "type", StringSerializer.descriptor);
                        String m = ContentInViewNode$Request$$ExternalSyntheticOutline0.m("kotlinx.serialization.Polymorphic<", ((ClassReference) PolymorphicSerializer.this.baseClass).getSimpleName(), ">");
                        SerialKind.CONTEXTUAL contextual = SerialKind.CONTEXTUAL.INSTANCE;
                        SerialDescriptor[] serialDescriptorArr = new SerialDescriptor[0];
                        if (StringsKt__StringsKt.isBlank(m)) {
                            throw new IllegalArgumentException("Blank serial names are prohibited");
                        }
                        if (Intrinsics.areEqual(contextual, StructureKind.CLASS.INSTANCE)) {
                            throw new IllegalArgumentException("For StructureKind.CLASS please use 'buildClassSerialDescriptor' instead");
                        }
                        ClassSerialDescriptorBuilder classSerialDescriptorBuilder2 = new ClassSerialDescriptorBuilder(m);
                        Unit unit = Unit.INSTANCE;
                        ClassSerialDescriptorBuilder.element$default(classSerialDescriptorBuilder, "value", new SerialDescriptorImpl(m, contextual, ((ArrayList) classSerialDescriptorBuilder2.elementNames).size(), ArraysKt___ArraysKt.toList(serialDescriptorArr), classSerialDescriptorBuilder2));
                        return Unit.INSTANCE;
                    }
                }), polymorphicSerializer.baseClass);
            }
        });
    }

    @Override // kotlinx.serialization.internal.AbstractPolymorphicSerializer
    public final KClass getBaseClass() {
        return this.baseClass;
    }

    @Override // kotlinx.serialization.KSerializer
    public final SerialDescriptor getDescriptor() {
        return (SerialDescriptor) this.descriptor$delegate.getValue();
    }

    public final String toString() {
        return "kotlinx.serialization.PolymorphicSerializer(baseClass: " + this.baseClass + ")";
    }

    public PolymorphicSerializer(KClass kClass, Annotation[] annotationArr) {
        this(kClass);
        this._annotations = Arrays.asList(annotationArr);
    }
}
