package android.view;

import android.util.AndroidRuntimeException;

/* compiled from: WindowManagerGlobal.java */
final class WindowLeaked extends AndroidRuntimeException {
    public WindowLeaked(String msg) {
        super(msg);
    }
}
