package com.android.server.inputmethod;

import android.os.IBinder;
import android.os.InputConstants;
import android.os.Process;
import android.view.InputApplicationHandle;
import android.view.InputChannel;
import android.view.InputWindowHandle;
import android.view.SurfaceControl;

import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class HandwritingEventReceiverSurface {
    public final InputChannel mClientChannel;
    public final SurfaceControl mInputSurface;
    public boolean mIsIntercepting;
    public final InputWindowHandle mWindowHandle;

    public HandwritingEventReceiverSurface(
            String str, int i, SurfaceControl surfaceControl, InputChannel inputChannel) {
        this.mClientChannel = inputChannel;
        this.mInputSurface = surfaceControl;
        long j = InputConstants.DEFAULT_DISPATCHING_TIMEOUT_MILLIS;
        InputWindowHandle inputWindowHandle =
                new InputWindowHandle(new InputApplicationHandle((IBinder) null, str, j), i);
        this.mWindowHandle = inputWindowHandle;
        inputWindowHandle.name = str;
        inputWindowHandle.token = inputChannel.getToken();
        inputWindowHandle.layoutParamsType = 2015;
        inputWindowHandle.dispatchingTimeoutMillis = j;
        inputWindowHandle.ownerPid = Process.myPid();
        inputWindowHandle.ownerUid = Process.myUid();
        inputWindowHandle.scaleFactor = 1.0f;
        inputWindowHandle.inputConfig = 49164;
        inputWindowHandle.replaceTouchableRegionWithCrop((SurfaceControl) null);
        SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
        inputWindowHandle.setTrustedOverlay(transaction, surfaceControl, true);
        transaction.setInputWindowInfo(surfaceControl, inputWindowHandle);
        transaction.setLayer(surfaceControl, 2);
        transaction.setPosition(
                surfaceControl,
                FullScreenMagnificationGestureHandler.MAX_SCALE,
                FullScreenMagnificationGestureHandler.MAX_SCALE);
        transaction.setCrop(surfaceControl, null);
        transaction.show(surfaceControl);
        transaction.apply();
        this.mIsIntercepting = false;
    }
}
