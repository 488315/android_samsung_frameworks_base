package com.android.wm.shell.onehanded;

import android.graphics.Rect;
import android.hardware.input.InputManagerGlobal;
import android.os.Looper;
import android.view.InputChannel;
import android.view.InputEvent;
import android.view.InputEventReceiver;
import android.view.InputMonitor;
import android.view.MotionEvent;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.onehanded.OneHandedTouchHandler.EventReceiver;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class OneHandedTouchHandler implements OneHandedTransitionCallback {
    InputEventReceiver mInputEventReceiver;
    InputMonitor mInputMonitor;
    public boolean mIsEnabled;
    public boolean mIsInOutsideRegion;
    public boolean mIsOnStopTransitioning;
    public final Rect mLastUpdatedBounds = new Rect();
    public final ShellExecutor mMainExecutor;
    public final OneHandedTimeoutHandler mTimeoutHandler;
    OneHandedTouchEventCallback mTouchEventCallback;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class EventReceiver extends InputEventReceiver {
        public EventReceiver(InputChannel inputChannel, Looper looper) {
            super(inputChannel, looper);
        }

        /* JADX WARN: Code restructure failed: missing block: B:12:0x0032, code lost:
        
            if (r1 != 3) goto L25;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void onInputEvent(InputEvent inputEvent) {
            OneHandedTouchHandler oneHandedTouchHandler = OneHandedTouchHandler.this;
            oneHandedTouchHandler.getClass();
            if (inputEvent instanceof MotionEvent) {
                MotionEvent motionEvent = (MotionEvent) inputEvent;
                motionEvent.getX();
                oneHandedTouchHandler.mIsInOutsideRegion = Math.round(motionEvent.getY()) < oneHandedTouchHandler.mLastUpdatedBounds.top;
                int action = motionEvent.getAction();
                OneHandedTimeoutHandler oneHandedTimeoutHandler = oneHandedTouchHandler.mTimeoutHandler;
                if (action != 0) {
                    if (action != 1) {
                        if (action != 2) {
                        }
                    }
                    oneHandedTimeoutHandler.resetTimer();
                    if (oneHandedTouchHandler.mIsInOutsideRegion && !oneHandedTouchHandler.mIsOnStopTransitioning) {
                        ((OneHandedController$$ExternalSyntheticLambda4) oneHandedTouchHandler.mTouchEventCallback).f$0.stopOneHanded(2);
                        oneHandedTouchHandler.mIsOnStopTransitioning = true;
                    }
                    oneHandedTouchHandler.mIsInOutsideRegion = false;
                }
                if (!oneHandedTouchHandler.mIsInOutsideRegion) {
                    oneHandedTimeoutHandler.resetTimer();
                }
            }
            finishInputEvent(inputEvent, true);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface OneHandedTouchEventCallback {
    }

    public OneHandedTouchHandler(OneHandedTimeoutHandler oneHandedTimeoutHandler, ShellExecutor shellExecutor) {
        this.mTimeoutHandler = oneHandedTimeoutHandler;
        this.mMainExecutor = shellExecutor;
        updateIsEnabled();
    }

    @Override // com.android.wm.shell.onehanded.OneHandedTransitionCallback
    public final void onStartFinished(Rect rect) {
        this.mLastUpdatedBounds.set(rect);
    }

    @Override // com.android.wm.shell.onehanded.OneHandedTransitionCallback
    public final void onStopFinished(Rect rect) {
        this.mLastUpdatedBounds.set(rect);
        this.mIsOnStopTransitioning = false;
    }

    public final void updateIsEnabled() {
        InputEventReceiver inputEventReceiver = this.mInputEventReceiver;
        if (inputEventReceiver != null) {
            inputEventReceiver.dispose();
            this.mInputEventReceiver = null;
        }
        InputMonitor inputMonitor = this.mInputMonitor;
        if (inputMonitor != null) {
            inputMonitor.dispose();
            this.mInputMonitor = null;
        }
        if (this.mIsEnabled) {
            this.mInputMonitor = InputManagerGlobal.getInstance().monitorGestureInput("onehanded-touch", 0);
            try {
                this.mMainExecutor.executeBlocking(new Runnable() { // from class: com.android.wm.shell.onehanded.OneHandedTouchHandler$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        OneHandedTouchHandler oneHandedTouchHandler = OneHandedTouchHandler.this;
                        oneHandedTouchHandler.getClass();
                        oneHandedTouchHandler.mInputEventReceiver = oneHandedTouchHandler.new EventReceiver(oneHandedTouchHandler.mInputMonitor.getInputChannel(), Looper.myLooper());
                    }
                });
            } catch (InterruptedException e) {
                throw new RuntimeException("Failed to create input event receiver", e);
            }
        }
    }
}
