package kotlinx.serialization.internal;

import kotlin.Unit;
import kotlinx.serialization.KSerializer;
import kotlinx.serialization.descriptors.SerialDescriptor;
import kotlinx.serialization.encoding.AbstractEncoder;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class UnitSerializer implements KSerializer {
    public static final UnitSerializer INSTANCE = new UnitSerializer();
    public final /* synthetic */ ObjectSerializer $$delegate_0 = new ObjectSerializer("kotlin.Unit", Unit.INSTANCE);

    private UnitSerializer() {
    }

    @Override // kotlinx.serialization.KSerializer
    public final SerialDescriptor getDescriptor() {
        return this.$$delegate_0.getDescriptor();
    }

    @Override // kotlinx.serialization.KSerializer
    public final void serialize(AbstractEncoder abstractEncoder, Object obj) {
        this.$$delegate_0.serialize(abstractEncoder, (Unit) obj);
    }
}
