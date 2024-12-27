package android.view;

import android.util.AndroidRuntimeException;

final class WindowLeaked extends AndroidRuntimeException {
    public WindowLeaked(String msg) {
        super(msg);
    }
}
