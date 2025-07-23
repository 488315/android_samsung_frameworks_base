package kotlinx.serialization.descriptors;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class SerialKind {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class CONTEXTUAL extends SerialKind {
        public static final CONTEXTUAL INSTANCE = new CONTEXTUAL();

        private CONTEXTUAL() {
            super(null);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ENUM extends SerialKind {
        public static final ENUM INSTANCE = new ENUM();

        private ENUM() {
            super(null);
        }
    }

    public /* synthetic */ SerialKind(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public final int hashCode() {
        return toString().hashCode();
    }

    public final String toString() {
        String simpleName = Reflection.getOrCreateKotlinClass(getClass()).getSimpleName();
        simpleName.getClass();
        return simpleName;
    }

    private SerialKind() {
    }
}
