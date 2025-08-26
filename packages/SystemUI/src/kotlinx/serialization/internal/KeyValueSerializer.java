package kotlinx.serialization.internal;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.serialization.KSerializer;
import kotlinx.serialization.encoding.AbstractEncoder;

/* loaded from: classes4.dex */
public abstract class KeyValueSerializer implements KSerializer {
    public final KSerializer keySerializer;
    public final KSerializer valueSerializer;

    public /* synthetic */ KeyValueSerializer(KSerializer kSerializer, KSerializer kSerializer2, DefaultConstructorMarker defaultConstructorMarker) {
        this(kSerializer, kSerializer2);
    }

    public abstract Object getKey(Object obj);

    public abstract Object getValue(Object obj);

    @Override // kotlinx.serialization.KSerializer
    public final void serialize(AbstractEncoder abstractEncoder, Object obj) {
        getDescriptor();
        abstractEncoder.encodeSerializableElement(getDescriptor(), 0, this.keySerializer, getKey(obj));
        abstractEncoder.encodeSerializableElement(getDescriptor(), 1, this.valueSerializer, getValue(obj));
        getDescriptor();
    }

    private KeyValueSerializer(KSerializer kSerializer, KSerializer kSerializer2) {
        this.keySerializer = kSerializer;
        this.valueSerializer = kSerializer2;
    }
}
