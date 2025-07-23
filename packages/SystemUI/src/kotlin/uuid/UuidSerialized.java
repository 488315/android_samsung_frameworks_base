package kotlin.uuid;

import java.io.Externalizable;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.uuid.Uuid;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
final class UuidSerialized implements Externalizable {
    private static final long serialVersionUID = 0;
    private long leastSignificantBits;
    private long mostSignificantBits;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public UuidSerialized(long j, long j2) {
        this.mostSignificantBits = j;
        this.leastSignificantBits = j2;
    }

    private final Object readResolve() {
        Uuid.Companion companion = Uuid.Companion;
        long j = this.mostSignificantBits;
        long j2 = this.leastSignificantBits;
        companion.getClass();
        return (j == 0 && j2 == 0) ? Uuid.NIL : new Uuid(j, j2);
    }

    @Override // java.io.Externalizable
    public final void readExternal(ObjectInput objectInput) {
        this.mostSignificantBits = objectInput.readLong();
        this.leastSignificantBits = objectInput.readLong();
    }

    @Override // java.io.Externalizable
    public final void writeExternal(ObjectOutput objectOutput) {
        objectOutput.writeLong(this.mostSignificantBits);
        objectOutput.writeLong(this.leastSignificantBits);
    }

    public UuidSerialized() {
        this(0L, 0L);
    }
}
