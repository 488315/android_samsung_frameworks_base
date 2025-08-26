package kotlinx.serialization.internal;

import java.lang.ref.SoftReference;
import kotlin.jvm.functions.Function0;

/* loaded from: classes4.dex */
public final class MutableSoftReference {
    public volatile SoftReference reference = new SoftReference(null);

    public final synchronized Object getOrSetWithLock(Function0 function0) {
        Object obj = this.reference.get();
        if (obj != null) {
            return obj;
        }
        Object objInvoke = function0.invoke();
        this.reference = new SoftReference(objInvoke);
        return objInvoke;
    }
}
