package kotlinx.serialization.descriptors;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class StructureKind extends SerialKind {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class CLASS extends StructureKind {
        public static final CLASS INSTANCE = new CLASS();

        private CLASS() {
            super(null);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class LIST extends StructureKind {
        public static final LIST INSTANCE = new LIST();

        private LIST() {
            super(null);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class MAP extends StructureKind {
        public static final MAP INSTANCE = new MAP();

        private MAP() {
            super(null);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class OBJECT extends StructureKind {
        public static final OBJECT INSTANCE = new OBJECT();

        private OBJECT() {
            super(null);
        }
    }

    public /* synthetic */ StructureKind(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private StructureKind() {
        super(null);
    }
}
