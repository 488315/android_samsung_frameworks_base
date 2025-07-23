package kotlinx.serialization.descriptors;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class PolymorphicKind extends SerialKind {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class OPEN extends PolymorphicKind {
        public static final OPEN INSTANCE = new OPEN();

        private OPEN() {
            super(null);
        }
    }

    public /* synthetic */ PolymorphicKind(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private PolymorphicKind() {
        super(null);
    }
}
