package android.view;

import android.os.IBinder;

/* loaded from: classes4.dex */
public final class InputApplicationHandle {
    public final long dispatchingTimeoutMillis;
    public final String name;
    private long ptr;
    public final IBinder token;

    private native void nativeDispose();

    public InputApplicationHandle(IBinder iBinder, String str, long j) {
        this.token = iBinder;
        this.name = str;
        this.dispatchingTimeoutMillis = j;
    }

    public InputApplicationHandle(InputApplicationHandle inputApplicationHandle) {
        this.token = inputApplicationHandle.token;
        this.dispatchingTimeoutMillis = inputApplicationHandle.dispatchingTimeoutMillis;
        this.name = inputApplicationHandle.name;
    }

    protected void finalize() throws Throwable {
        try {
            nativeDispose();
        } finally {
            super.finalize();
        }
    }
}
