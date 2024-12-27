package com.android.server.am;

public final class AppErrorResult {
    public boolean mHasResult = false;
    public int mResult;

    public final void set(int i) {
        synchronized (this) {
            this.mHasResult = true;
            this.mResult = i;
            notifyAll();
        }
    }
}
