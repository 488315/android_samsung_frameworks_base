package kotlinx.serialization.internal;

import java.util.Map;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlinx.serialization.KSerializer;
import kotlinx.serialization.descriptors.ClassSerialDescriptorBuilder;
import kotlinx.serialization.descriptors.SerialDescriptor;
import kotlinx.serialization.descriptors.SerialDescriptorImpl;
import kotlinx.serialization.descriptors.SerialDescriptorsKt;
import kotlinx.serialization.descriptors.StructureKind;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class MapEntrySerializer extends KeyValueSerializer {
    public final SerialDescriptorImpl descriptor;

    public MapEntrySerializer(final KSerializer kSerializer, final KSerializer kSerializer2) {
        super(kSerializer, kSerializer2, null);
        this.descriptor = SerialDescriptorsKt.buildSerialDescriptor("kotlin.collections.Map.Entry", StructureKind.MAP.INSTANCE, new SerialDescriptor[0], new Function1() { // from class: kotlinx.serialization.internal.MapEntrySerializer$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                ClassSerialDescriptorBuilder classSerialDescriptorBuilder = (ClassSerialDescriptorBuilder) obj;
                ClassSerialDescriptorBuilder.element$default(classSerialDescriptorBuilder, "key", KSerializer.this.getDescriptor());
                ClassSerialDescriptorBuilder.element$default(classSerialDescriptorBuilder, "value", kSerializer2.getDescriptor());
                return Unit.INSTANCE;
            }
        });
    }

    @Override // kotlinx.serialization.KSerializer
    public final SerialDescriptor getDescriptor() {
        return this.descriptor;
    }

    @Override // kotlinx.serialization.internal.KeyValueSerializer
    public final Object getKey(Object obj) {
        return ((Map.Entry) obj).getKey();
    }

    @Override // kotlinx.serialization.internal.KeyValueSerializer
    public final Object getValue(Object obj) {
        return ((Map.Entry) obj).getValue();
    }
}
