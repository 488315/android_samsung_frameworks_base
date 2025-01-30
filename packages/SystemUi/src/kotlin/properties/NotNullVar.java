package kotlin.properties;

import kotlin.jvm.internal.CallableReference;
import kotlin.reflect.KProperty;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class NotNullVar {
    public Object value;

    /* JADX WARN: Multi-variable type inference failed */
    public final Object getValue(KProperty kProperty) {
        Object obj = this.value;
        if (obj != null) {
            return obj;
        }
        throw new IllegalStateException("Property " + ((CallableReference) kProperty).getName() + " should be initialized before get.");
    }
}
