package kotlinx.coroutines;

import kotlin.coroutines.AbstractCoroutineContextElement;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class CoroutineId extends AbstractCoroutineContextElement implements ThreadContextElement {
    public static final Key Key = new Key(null);

    /* renamed from: id */
    public final long f666id;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Key implements CoroutineContext.Key {
        private Key() {
        }

        public /* synthetic */ Key(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public CoroutineId(long j) {
        super(Key);
        this.f666id = j;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof CoroutineId) && this.f666id == ((CoroutineId) obj).f666id;
    }

    public final int hashCode() {
        return Long.hashCode(this.f666id);
    }

    public final String toString() {
        return "CoroutineId(" + this.f666id + ")";
    }

    public final Object updateThreadContext(CoroutineContext coroutineContext) {
        String str;
        CoroutineName coroutineName = (CoroutineName) coroutineContext.get(CoroutineName.Key);
        if (coroutineName == null || (str = coroutineName.name) == null) {
            str = "coroutine";
        }
        Thread currentThread = Thread.currentThread();
        String name = currentThread.getName();
        int lastIndexOf$default = StringsKt__StringsKt.lastIndexOf$default(name, " @", 6);
        if (lastIndexOf$default < 0) {
            lastIndexOf$default = name.length();
        }
        StringBuilder sb = new StringBuilder(str.length() + lastIndexOf$default + 10);
        sb.append(name.substring(0, lastIndexOf$default));
        sb.append(" @");
        sb.append(str);
        sb.append('#');
        sb.append(this.f666id);
        currentThread.setName(sb.toString());
        return name;
    }
}
