package kotlinx.serialization.internal;

import kotlin.time.Duration;
import kotlin.time.DurationUnit;
import kotlinx.serialization.KSerializer;
import kotlinx.serialization.descriptors.PrimitiveKind;
import kotlinx.serialization.descriptors.SerialDescriptor;
import kotlinx.serialization.encoding.AbstractEncoder;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class DurationSerializer implements KSerializer {
    public static final DurationSerializer INSTANCE = new DurationSerializer();
    public static final PrimitiveSerialDescriptor descriptor = new PrimitiveSerialDescriptor("kotlin.time.Duration", PrimitiveKind.STRING.INSTANCE);

    private DurationSerializer() {
    }

    @Override // kotlinx.serialization.KSerializer
    public final SerialDescriptor getDescriptor() {
        return descriptor;
    }

    @Override // kotlinx.serialization.KSerializer
    public final void serialize(AbstractEncoder abstractEncoder, Object obj) {
        long j = ((Duration) obj).rawValue;
        Duration.Companion companion = Duration.Companion;
        StringBuilder sb = new StringBuilder();
        if (j < 0) {
            sb.append('-');
        }
        sb.append("PT");
        long m3447unaryMinusUwyO8pc = j < 0 ? Duration.m3447unaryMinusUwyO8pc(j) : j;
        long m3445toLongimpl = Duration.m3445toLongimpl(m3447unaryMinusUwyO8pc, DurationUnit.HOURS);
        boolean z = false;
        int m3445toLongimpl2 = Duration.m3440isInfiniteimpl(m3447unaryMinusUwyO8pc) ? 0 : (int) (Duration.m3445toLongimpl(m3447unaryMinusUwyO8pc, DurationUnit.MINUTES) % 60);
        int m3445toLongimpl3 = Duration.m3440isInfiniteimpl(m3447unaryMinusUwyO8pc) ? 0 : (int) (Duration.m3445toLongimpl(m3447unaryMinusUwyO8pc, DurationUnit.SECONDS) % 60);
        int m3438getNanosecondsComponentimpl = Duration.m3438getNanosecondsComponentimpl(m3447unaryMinusUwyO8pc);
        if (Duration.m3440isInfiniteimpl(j)) {
            m3445toLongimpl = 9999999999999L;
        }
        boolean z2 = m3445toLongimpl != 0;
        boolean z3 = (m3445toLongimpl3 == 0 && m3438getNanosecondsComponentimpl == 0) ? false : true;
        if (m3445toLongimpl2 != 0 || (z3 && z2)) {
            z = true;
        }
        if (z2) {
            sb.append(m3445toLongimpl);
            sb.append('H');
        }
        if (z) {
            sb.append(m3445toLongimpl2);
            sb.append('M');
        }
        if (z3 || (!z2 && !z)) {
            Duration.m3433appendFractionalimpl(sb, m3445toLongimpl3, m3438getNanosecondsComponentimpl, 9, "S", true);
        }
        abstractEncoder.encodeString(sb.toString());
    }
}
