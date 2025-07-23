package android.view.inputmethod;

import android.os.RemoteException;
import java.util.function.Consumer;

/* loaded from: classes4.dex */
public class InputMethodManagerGlobal {
    public static boolean isImeTraceAvailable() {
        return IInputMethodManagerGlobalInvoker.isAvailable();
    }

    public static void startProtoDump(byte[] bArr, int i, String str, Consumer<RemoteException> consumer) {
        IInputMethodManagerGlobalInvoker.startProtoDump(bArr, i, str, consumer);
    }

    public static void startImeTrace(Consumer<RemoteException> consumer) {
        IInputMethodManagerGlobalInvoker.startImeTrace(consumer);
    }

    public static void stopImeTrace(Consumer<RemoteException> consumer) {
        IInputMethodManagerGlobalInvoker.stopImeTrace(consumer);
    }

    public static boolean isImeTraceEnabled() {
        return IInputMethodManagerGlobalInvoker.isImeTraceEnabled();
    }

    public static void removeImeSurface(int i, Consumer<RemoteException> consumer) {
        IInputMethodManagerGlobalInvoker.removeImeSurface(i, consumer);
    }
}
