package android.hardware.camera2.utils;

/* loaded from: classes2.dex */
public class UncheckedThrow {
    public static void throwAnyException(Exception exc) {
        throwAnyImpl(exc);
    }

    public static void throwAnyException(Throwable th) {
        throwAnyImpl(th);
    }

    private static <T extends Throwable> void throwAnyImpl(Throwable th) throws Throwable {
        throw th;
    }
}
