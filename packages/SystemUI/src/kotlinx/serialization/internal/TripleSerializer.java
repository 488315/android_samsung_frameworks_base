package kotlinx.serialization.internal;

import kotlin.Triple;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlinx.serialization.KSerializer;
import kotlinx.serialization.descriptors.ClassSerialDescriptorBuilder;
import kotlinx.serialization.descriptors.SerialDescriptor;
import kotlinx.serialization.descriptors.SerialDescriptorImpl;
import kotlinx.serialization.descriptors.SerialDescriptorsKt;
import kotlinx.serialization.encoding.AbstractEncoder;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class TripleSerializer implements KSerializer {
    public final KSerializer aSerializer;
    public final KSerializer bSerializer;
    public final KSerializer cSerializer;
    public final SerialDescriptorImpl descriptor = SerialDescriptorsKt.buildClassSerialDescriptor("kotlin.Triple", new SerialDescriptor[0], new Function1() { // from class: kotlinx.serialization.internal.TripleSerializer$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo779invoke(Object obj) {
            ClassSerialDescriptorBuilder classSerialDescriptorBuilder = (ClassSerialDescriptorBuilder) obj;
            TripleSerializer tripleSerializer = TripleSerializer.this;
            ClassSerialDescriptorBuilder.element$default(classSerialDescriptorBuilder, "first", tripleSerializer.aSerializer.getDescriptor());
            ClassSerialDescriptorBuilder.element$default(classSerialDescriptorBuilder, "second", tripleSerializer.bSerializer.getDescriptor());
            ClassSerialDescriptorBuilder.element$default(classSerialDescriptorBuilder, "third", tripleSerializer.cSerializer.getDescriptor());
            return Unit.INSTANCE;
        }
    });

    public TripleSerializer(KSerializer kSerializer, KSerializer kSerializer2, KSerializer kSerializer3) {
        this.aSerializer = kSerializer;
        this.bSerializer = kSerializer2;
        this.cSerializer = kSerializer3;
    }

    @Override // kotlinx.serialization.KSerializer
    public final SerialDescriptor getDescriptor() {
        return this.descriptor;
    }

    @Override // kotlinx.serialization.KSerializer
    public final void serialize(AbstractEncoder abstractEncoder, Object obj) {
        Triple triple = (Triple) obj;
        Object first = triple.getFirst();
        KSerializer kSerializer = this.aSerializer;
        SerialDescriptorImpl serialDescriptorImpl = this.descriptor;
        abstractEncoder.encodeSerializableElement(serialDescriptorImpl, 0, kSerializer, first);
        abstractEncoder.encodeSerializableElement(serialDescriptorImpl, 1, this.bSerializer, triple.getSecond());
        abstractEncoder.encodeSerializableElement(serialDescriptorImpl, 2, this.cSerializer, triple.getThird());
    }
}
