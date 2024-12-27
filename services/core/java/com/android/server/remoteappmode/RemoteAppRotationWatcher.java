package com.android.server.remoteappmode;

import android.view.IRotationWatcher;

import com.samsung.android.remoteappmode.IRotationChangeListener;

public final class RemoteAppRotationWatcher extends IRotationWatcher.Stub {
    public IRotationChangeListener listener;
    public int mDisplayId;

    public final void onRotationChanged(int i) {
        IRotationChangeListener iRotationChangeListener = this.listener;
        if (iRotationChangeListener != null) {
            iRotationChangeListener.onRotationChanged(this.mDisplayId, i);
        }
    }
}
