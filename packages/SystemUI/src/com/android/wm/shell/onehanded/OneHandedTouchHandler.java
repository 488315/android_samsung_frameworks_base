package com.android.wm.shell.onehanded;

import android.graphics.Rect;
import android.hardware.input.InputManagerGlobal;
import android.os.Looper;
import android.view.InputChannel;
import android.view.InputEventReceiver;
import android.view.InputMonitor;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.onehanded.OneHandedTouchHandler.EventReceiver;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class OneHandedTouchHandler implements OneHandedTransitionCallback {
    InputEventReceiver mInputEventReceiver;
    InputMonitor mInputMonitor;
    public boolean mIsEnabled;
    public boolean mIsInOutsideRegion;
    public boolean mIsOnStopTransitioning;
    public final Rect mLastUpdatedBounds = new Rect();
    public final ShellExecutor mMainExecutor;
    public final OneHandedTimeoutHandler mTimeoutHandler;
    OneHandedTouchEventCallback mTouchEventCallback;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class EventReceiver extends InputEventReceiver {
        public EventReceiver(InputChannel inputChannel, Looper looper) {
            super(inputChannel, looper);
        }

        /* JADX WARN: Code restructure failed: missing block: B:12:0x0032, code lost:
        
            if (r1 != 3) goto L25;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onInputEvent(android.view.InputEvent r8) {
            /*
                r7 = this;
                com.android.wm.shell.onehanded.OneHandedTouchHandler r0 = com.android.wm.shell.onehanded.OneHandedTouchHandler.this
                r0.getClass()
                boolean r1 = r8 instanceof android.view.MotionEvent
                r2 = 1
                if (r1 == 0) goto L55
                r1 = r8
                android.view.MotionEvent r1 = (android.view.MotionEvent) r1
                r1.getX()
                float r3 = r1.getY()
                int r3 = java.lang.Math.round(r3)
                android.graphics.Rect r4 = r0.mLastUpdatedBounds
                int r4 = r4.top
                r5 = 0
                if (r3 >= r4) goto L21
                r3 = r2
                goto L22
            L21:
                r3 = r5
            L22:
                r0.mIsInOutsideRegion = r3
                int r1 = r1.getAction()
                com.android.wm.shell.onehanded.OneHandedTimeoutHandler r3 = r0.mTimeoutHandler
                if (r1 == 0) goto L4e
                r4 = 2
                if (r1 == r2) goto L35
                if (r1 == r4) goto L4e
                r6 = 3
                if (r1 == r6) goto L35
                goto L55
            L35:
                r3.resetTimer()
                boolean r1 = r0.mIsInOutsideRegion
                if (r1 == 0) goto L4b
                boolean r1 = r0.mIsOnStopTransitioning
                if (r1 != 0) goto L4b
                com.android.wm.shell.onehanded.OneHandedTouchHandler$OneHandedTouchEventCallback r1 = r0.mTouchEventCallback
                com.android.wm.shell.onehanded.OneHandedController$$ExternalSyntheticLambda11 r1 = (com.android.wm.shell.onehanded.OneHandedController$$ExternalSyntheticLambda11) r1
                com.android.wm.shell.onehanded.OneHandedController r1 = r1.f$0
                r1.stopOneHanded(r4)
                r0.mIsOnStopTransitioning = r2
            L4b:
                r0.mIsInOutsideRegion = r5
                goto L55
            L4e:
                boolean r0 = r0.mIsInOutsideRegion
                if (r0 != 0) goto L55
                r3.resetTimer()
            L55:
                r7.finishInputEvent(r8, r2)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.onehanded.OneHandedTouchHandler.EventReceiver.onInputEvent(android.view.InputEvent):void");
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
