package android.app;

import android.util.AndroidRuntimeException;

final class ServiceConnectionLeaked extends AndroidRuntimeException {
    public ServiceConnectionLeaked(String msg) {
        super(msg);
    }
}
