package android.app;

import android.util.AndroidRuntimeException;

/* compiled from: LoadedApk.java */
final class ServiceConnectionLeaked extends AndroidRuntimeException {
    public ServiceConnectionLeaked(String msg) {
        super(msg);
    }
}
