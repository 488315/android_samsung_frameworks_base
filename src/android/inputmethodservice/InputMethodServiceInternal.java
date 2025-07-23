package android.inputmethodservice;

import android.content.Context;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputContentInfo;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* loaded from: classes2.dex */
interface InputMethodServiceInternal {
    default void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    }

    default void exposeContent(InputContentInfo inputContentInfo, InputConnection inputConnection) {
    }

    Context getContext();

    default boolean isServiceDestroyed() {
        return false;
    }

    default void notifyUserActionIfNecessary() {
    }

    default void triggerServiceDump(String str, byte[] bArr) {
    }
}
