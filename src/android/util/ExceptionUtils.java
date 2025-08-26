package android.util;

import android.os.ParcelableException;
import com.android.internal.util.Preconditions;
import java.io.IOException;

/* loaded from: classes4.dex */
public class ExceptionUtils {
    public static RuntimeException wrap(IOException iOException) {
        throw new ParcelableException(iOException);
    }

    public static void maybeUnwrapIOException(RuntimeException runtimeException) throws Throwable {
        if (runtimeException instanceof ParcelableException) {
            ((ParcelableException) runtimeException).maybeRethrow(IOException.class);
        }
    }

    public static String getCompleteMessage(String str, Throwable th) {
        StringBuilder sb = new StringBuilder();
        if (str != null) {
            sb.append(str);
            sb.append(": ");
        }
        sb.append(th.getMessage());
        while (true) {
            th = th.getCause();
            if (th != null) {
                sb.append(": ");
                sb.append(th.getMessage());
            } else {
                return sb.toString();
            }
        }
    }

    public static String getCompleteMessage(Throwable th) {
        return getCompleteMessage(null, th);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: E extends java.lang.Throwable */
    public static <E extends Throwable> void propagateIfInstanceOf(Throwable th, Class<E> cls) throws Throwable {
        if (th != null && cls.isInstance(th)) {
            throw cls.cast(th);
        }
    }

    public static <E extends Exception> RuntimeException propagate(Throwable th, Class<E> cls) throws Exception {
        propagateIfInstanceOf(th, cls);
        return propagate(th);
    }

    public static RuntimeException propagate(Throwable th) throws Throwable {
        Preconditions.checkNotNull(th);
        propagateIfInstanceOf(th, Error.class);
        propagateIfInstanceOf(th, RuntimeException.class);
        throw new RuntimeException(th);
    }

    public static Throwable getRootCause(Throwable th) {
        while (th.getCause() != null) {
            th = th.getCause();
        }
        return th;
    }

    public static Throwable appendCause(Throwable th, Throwable th2) {
        if (th2 != null) {
            getRootCause(th).initCause(th2);
        }
        return th;
    }
}
