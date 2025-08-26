package kotlinx.serialization.internal;

import kotlin.time.Duration;
import kotlin.time.DurationUnit;
import kotlinx.serialization.KSerializer;
import kotlinx.serialization.descriptors.PrimitiveKind;
import kotlinx.serialization.descriptors.SerialDescriptor;
import kotlinx.serialization.encoding.AbstractEncoder;

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
        long jM3467unaryMinusUwyO8pc = j < 0 ? Duration.m3467unaryMinusUwyO8pc(j) : j;
        long jM3465toLongimpl = Duration.m3465toLongimpl(jM3467unaryMinusUwyO8pc, DurationUnit.HOURS);
        boolean z = false;
        int iM3465toLongimpl = Duration.m3460isInfiniteimpl(jM3467unaryMinusUwyO8pc) ? 0 : (int) (Duration.m3465toLongimpl(jM3467unaryMinusUwyO8pc, DurationUnit.MINUTES) % 60);
        int iM3465toLongimpl2 = Duration.m3460isInfiniteimpl(jM3467unaryMinusUwyO8pc) ? 0 : (int) (Duration.m3465toLongimpl(jM3467unaryMinusUwyO8pc, DurationUnit.SECONDS) % 60);
        int iM3458getNanosecondsComponentimpl = Duration.m3458getNanosecondsComponentimpl(jM3467unaryMinusUwyO8pc);
        if (Duration.m3460isInfiniteimpl(j)) {
            jM3465toLongimpl = 9999999999999L;
        }
        boolean z2 = jM3465toLongimpl != 0;
        boolean z3 = (iM3465toLongimpl2 == 0 && iM3458getNanosecondsComponentimpl == 0) ? false : true;
        if (iM3465toLongimpl != 0 || (z3 && z2)) {
            z = true;
        }
        if (z2) {
            sb.append(jM3465toLongimpl);
            sb.append('H');
        }
        if (z) {
            sb.append(iM3465toLongimpl);
            sb.append('M');
        }
        if (z3 || (!z2 && !z)) {
            Duration.m3453appendFractionalimpl(sb, iM3465toLongimpl2, iM3458getNanosecondsComponentimpl, 9, "S", true);
        }
        abstractEncoder.encodeString(sb.toString());
    }
}
