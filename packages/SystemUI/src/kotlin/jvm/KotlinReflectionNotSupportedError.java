package kotlin.jvm;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class KotlinReflectionNotSupportedError extends Error {
    public KotlinReflectionNotSupportedError() {
        super("Kotlin reflection implementation is not found at runtime. Make sure you have kotlin-reflect.jar in the classpath");
    }

    public KotlinReflectionNotSupportedError(String str) {
        super(str);
    }

    public KotlinReflectionNotSupportedError(String str, Throwable th) {
        super(str, th);
    }

    public KotlinReflectionNotSupportedError(Throwable th) {
        super(th);
    }
}
