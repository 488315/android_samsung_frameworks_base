package kotlinx.serialization.internal;

/* loaded from: classes4.dex */
public abstract class CachingKt {
    public static final boolean useClassValue;

    static {
        boolean z;
        try {
            Class.forName("java.lang.ClassValue");
            z = true;
        } catch (Throwable unused) {
            z = false;
        }
        useClassValue = z;
    }
}
