package com.android.server.wm;

import android.graphics.Point;
import android.graphics.Rect;
import android.os.Binder;
import android.os.IBinder;
import android.os.InputConstants;
import android.os.UserHandle;
import android.view.InputApplicationHandle;
import android.view.InputChannel;
import android.view.InputWindowHandle;
import android.view.MagnificationSpec;
import android.view.SurfaceControl;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class InputConsumerImpl implements IBinder.DeathRecipient {
    public final InputChannel mClientChannel;
    public final int mClientPid;
    public final UserHandle mClientUser;
    public final DisplayContent mDisplayContent;
    public final SurfaceControl mInputSurface;
    public final String mName;
    public boolean mNeedOneHandOpSpec;
    public final WindowManagerService mService;
    public final IBinder mToken;
    public final InputWindowHandle mWindowHandle;
    public final Rect mTmpClipRect = new Rect();
    public final Rect mTmpRect = new Rect();
    public final Point mOldPosition = new Point();
    public final Rect mOldWindowCrop = new Rect();

    public InputConsumerImpl(WindowManagerService windowManagerService, IBinder iBinder, String str, InputChannel inputChannel, int i, UserHandle userHandle, int i2, SurfaceControl.Transaction transaction) {
        this.mService = windowManagerService;
        this.mToken = iBinder;
        this.mName = str;
        this.mClientPid = i;
        this.mClientUser = userHandle;
        InputChannel createInputChannel = windowManagerService.mInputManager.createInputChannel(str);
        this.mClientChannel = createInputChannel;
        if (inputChannel != null) {
            createInputChannel.copyTo(inputChannel);
        }
        Binder binder = new Binder();
        long j = InputConstants.DEFAULT_DISPATCHING_TIMEOUT_MILLIS;
        InputWindowHandle inputWindowHandle = new InputWindowHandle(new InputApplicationHandle(binder, str, j), i2);
        this.mWindowHandle = inputWindowHandle;
        inputWindowHandle.name = str;
        inputWindowHandle.token = createInputChannel.getToken();
        inputWindowHandle.layoutParamsType = 2022;
        inputWindowHandle.dispatchingTimeoutMillis = j;
        inputWindowHandle.ownerPid = WindowManagerService.MY_PID;
        inputWindowHandle.ownerUid = WindowManagerService.MY_UID;
        inputWindowHandle.scaleFactor = 1.0f;
        inputWindowHandle.inputConfig = 4;
        SurfaceControl build = windowManagerService.makeSurfaceBuilder(windowManagerService.mRoot.getDisplayContent(i2).mSession).setContainerLayer().setName("Input Consumer " + str).setCallsite("InputConsumerImpl").build();
        this.mInputSurface = build;
        inputWindowHandle.setTrustedOverlay(transaction, build, true);
        this.mDisplayContent = windowManagerService.mRoot.getDisplayContent(i2);
    }

    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied() {
        synchronized (this.mService.mGlobalLock) {
            try {
                DisplayContent displayContent = this.mService.mRoot.getDisplayContent(this.mWindowHandle.displayId);
                if (displayContent == null) {
                    return;
                }
                displayContent.mInputMonitor.destroyInputConsumer(this.mToken);
                IBinder iBinder = this.mToken;
                if (iBinder != null) {
                    iBinder.unlinkToDeath(this, 0);
                }
            } finally {
            }
        }
    }

    public final void layout(SurfaceControl.Transaction transaction, Rect rect) {
        MagnificationSpec magnificationSpec;
        if (this.mNeedOneHandOpSpec && this.mDisplayContent.hasOneHandOpSpec() && (magnificationSpec = this.mDisplayContent.mMagnificationSpec) != null) {
            float f = rect.left;
            float f2 = magnificationSpec.scale;
            rect.offsetTo((int) (f * f2), (int) (rect.top * f2));
        }
        this.mTmpClipRect.set(0, 0, rect.width(), rect.height());
        if (this.mOldPosition.equals(rect.left, rect.top) && this.mOldWindowCrop.equals(this.mTmpClipRect)) {
            return;
        }
        if (!this.mNeedOneHandOpSpec || this.mDisplayContent.getOneHandOpPolicy() == null) {
            transaction.setPosition(this.mInputSurface, rect.left, rect.top);
        } else {
            DisplayContent displayContent = this.mDisplayContent;
            MagnificationSpec magnificationSpec2 = displayContent.mMagnificationSpec;
            if (!displayContent.getOneHandOpPolicy().mHasOneHandOpSpec || magnificationSpec2 == null) {
                transaction.setMatrix(this.mInputSurface, 1.0f, FullScreenMagnificationGestureHandler.MAX_SCALE, FullScreenMagnificationGestureHandler.MAX_SCALE, 1.0f);
                transaction.setPosition(this.mInputSurface, rect.left, rect.top);
            } else {
                SurfaceControl surfaceControl = this.mInputSurface;
                float f3 = magnificationSpec2.scale;
                transaction.setMatrix(surfaceControl, f3, FullScreenMagnificationGestureHandler.MAX_SCALE, FullScreenMagnificationGestureHandler.MAX_SCALE, f3);
                transaction.setPosition(this.mInputSurface, rect.left + magnificationSpec2.offsetX, rect.top + magnificationSpec2.offsetY);
            }
        }
        transaction.setWindowCrop(this.mInputSurface, this.mTmpClipRect);
        this.mOldPosition.set(rect.left, rect.top);
        this.mOldWindowCrop.set(this.mTmpClipRect);
    }
}
