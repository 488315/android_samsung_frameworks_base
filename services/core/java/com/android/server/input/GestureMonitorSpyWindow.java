package com.android.server.input;

import android.os.IBinder;
import android.os.InputConstants;
import android.view.InputApplicationHandle;
import android.view.InputChannel;
import android.view.InputWindowHandle;
import android.view.SurfaceControl;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class GestureMonitorSpyWindow {
    public final InputChannel mClientChannel;
    public final SurfaceControl mInputSurface;
    public final IBinder mMonitorToken;
    public final InputWindowHandle mWindowHandle;

    public GestureMonitorSpyWindow(IBinder iBinder, String str, int i, int i2, int i3, SurfaceControl surfaceControl, InputChannel inputChannel) {
        this.mMonitorToken = iBinder;
        this.mClientChannel = inputChannel;
        this.mInputSurface = surfaceControl;
        long j = InputConstants.DEFAULT_DISPATCHING_TIMEOUT_MILLIS;
        InputWindowHandle inputWindowHandle = new InputWindowHandle(new InputApplicationHandle((IBinder) null, str, j), i);
        this.mWindowHandle = inputWindowHandle;
        inputWindowHandle.name = str;
        inputWindowHandle.token = inputChannel.getToken();
        inputWindowHandle.layoutParamsType = 2015;
        inputWindowHandle.dispatchingTimeoutMillis = j;
        inputWindowHandle.ownerPid = i2;
        inputWindowHandle.ownerUid = i3;
        inputWindowHandle.scaleFactor = 1.0f;
        inputWindowHandle.replaceTouchableRegionWithCrop((SurfaceControl) null);
        inputWindowHandle.inputConfig = 16388;
        SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
        inputWindowHandle.setTrustedOverlay(transaction, surfaceControl, true);
        transaction.setInputWindowInfo(surfaceControl, inputWindowHandle);
        transaction.setLayer(surfaceControl, 1);
        transaction.setPosition(surfaceControl, FullScreenMagnificationGestureHandler.MAX_SCALE, FullScreenMagnificationGestureHandler.MAX_SCALE);
        transaction.setCrop(surfaceControl, null);
        transaction.show(surfaceControl);
        transaction.apply();
    }

    public final String dump() {
        return "name='" + this.mWindowHandle.name + "', inputChannelToken=" + this.mClientChannel.getToken() + " displayId=" + this.mWindowHandle.displayId;
    }
}
