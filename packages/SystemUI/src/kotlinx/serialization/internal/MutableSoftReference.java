package kotlinx.serialization.internal;

import java.lang.ref.SoftReference;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class MutableSoftReference {
    public volatile SoftReference reference = new SoftReference(null);

    public final synchronized Object getOrSetWithLock(Function0 function0) {
        Object obj = this.reference.get();
        if (obj != null) {
            return obj;
        }
        Object invoke = function0.invoke();
        this.reference = new SoftReference(invoke);
        return invoke;
    }
}
