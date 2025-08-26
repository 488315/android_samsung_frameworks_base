package android.hardware.camera2.utils;

/* loaded from: classes2.dex */
public class UncheckedThrow {
    public static void throwAnyException(Exception exc) throws Throwable {
        throwAnyImpl(exc);
    }

    public static void throwAnyException(Throwable th) throws Throwable {
        throwAnyImpl(th);
    }

    private static <T extends Throwable> void throwAnyImpl(Throwable th) throws Throwable {
        throw th;
    }
}
