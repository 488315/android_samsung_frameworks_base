package com.android.server.remoteappmode;

import android.view.IRotationWatcher;
import com.samsung.android.remoteappmode.IRotationChangeListener;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
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
