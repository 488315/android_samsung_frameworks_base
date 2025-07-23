package kotlin.enums;

import java.io.Serializable;
import java.lang.Enum;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class EnumEntriesSerializationProxy<E extends Enum<E>> implements Serializable {
    private static final long serialVersionUID = 0;
    private final Class<E> c;

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

    public EnumEntriesSerializationProxy(E[] eArr) {
        Class<E> cls = (Class<E>) eArr.getClass().getComponentType();
        cls.getClass();
        this.c = cls;
    }

    private final Object readResolve() {
        return new EnumEntriesList(this.c.getEnumConstants());
    }
}
