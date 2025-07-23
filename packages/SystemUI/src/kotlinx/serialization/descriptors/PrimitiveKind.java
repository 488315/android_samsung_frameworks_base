package kotlinx.serialization.descriptors;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class PrimitiveKind extends SerialKind {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class BOOLEAN extends PrimitiveKind {
        public static final BOOLEAN INSTANCE = new BOOLEAN();

        private BOOLEAN() {
            super(null);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class BYTE extends PrimitiveKind {
        public static final BYTE INSTANCE = new BYTE();

        private BYTE() {
            super(null);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class CHAR extends PrimitiveKind {
        public static final CHAR INSTANCE = new CHAR();

        private CHAR() {
            super(null);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class DOUBLE extends PrimitiveKind {
        public static final DOUBLE INSTANCE = new DOUBLE();

        private DOUBLE() {
            super(null);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class FLOAT extends PrimitiveKind {
        public static final FLOAT INSTANCE = new FLOAT();

        private FLOAT() {
            super(null);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class INT extends PrimitiveKind {
        public static final INT INSTANCE = new INT();

        private INT() {
            super(null);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class LONG extends PrimitiveKind {
        public static final LONG INSTANCE = new LONG();

        private LONG() {
            super(null);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class SHORT extends PrimitiveKind {
        public static final SHORT INSTANCE = new SHORT();

        private SHORT() {
            super(null);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class STRING extends PrimitiveKind {
        public static final STRING INSTANCE = new STRING();

        private STRING() {
            super(null);
        }
    }

    public /* synthetic */ PrimitiveKind(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private PrimitiveKind() {
        super(null);
    }
}
