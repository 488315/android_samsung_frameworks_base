package com.android.server.wm;

import android.os.IBinder;

/* loaded from: classes3.dex */
public interface ImeTargetChangeListener {
    void onImeInputTargetVisibilityChanged(IBinder iBinder, boolean z, boolean z2);

    void onImeTargetOverlayVisibilityChanged(IBinder iBinder, int i, boolean z, boolean z2);
}
