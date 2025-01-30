package com.android.server.inputmethod;

import android.hardware.input.InputManagerGlobal;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Slog;
import android.view.BatchedInputEventReceiver;
import android.view.Choreographer;
import android.view.InputChannel;
import android.view.InputEvent;
import android.view.InputEventReceiver;
import android.view.MotionEvent;
import android.view.SurfaceControl;
import com.android.server.LocalServices;
import com.android.server.input.InputManagerInternal;
import com.android.server.wm.WindowManagerInternal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.OptionalInt;

/* loaded from: classes2.dex */
public final class HandwritingModeController {
    public static final String TAG = "HandwritingModeController";
    public String mDelegatePackageName;
    public Handler mDelegationIdleTimeoutHandler;
    public Runnable mDelegationIdleTimeoutRunnable;
    public String mDelegatorPackageName;
    public ArrayList mHandwritingBuffer;
    public InputEventReceiver mHandwritingEventReceiver;
    public HandwritingEventReceiverSurface mHandwritingSurface;
    public Runnable mInkWindowInitRunnable;
    public final Looper mLooper;
    public boolean mRecordingGesture;
    public int mCurrentDisplayId = -1;
    public final InputManagerInternal mInputManagerInternal = (InputManagerInternal) LocalServices.getService(InputManagerInternal.class);
    public final WindowManagerInternal mWindowManagerInternal = (WindowManagerInternal) LocalServices.getService(WindowManagerInternal.class);
    public int mCurrentRequestId = 0;

    public HandwritingModeController(Looper looper, Runnable runnable) {
        this.mLooper = looper;
        this.mInkWindowInitRunnable = runnable;
    }

    public static boolean isStylusEvent(MotionEvent motionEvent) {
        if (!motionEvent.isFromSource(16386)) {
            return false;
        }
        int toolType = motionEvent.getToolType(0);
        return toolType == 2 || toolType == 4;
    }

    public void initializeHandwritingSpy(int i) {
        reset(i == this.mCurrentDisplayId);
        this.mCurrentDisplayId = i;
        if (this.mHandwritingBuffer == null) {
            this.mHandwritingBuffer = new ArrayList(getHandwritingBufferSize());
        }
        String str = "stylus-handwriting-event-receiver-" + i;
        InputChannel createInputChannel = this.mInputManagerInternal.createInputChannel(str);
        Objects.requireNonNull(createInputChannel, "Failed to create input channel");
        HandwritingEventReceiverSurface handwritingEventReceiverSurface = this.mHandwritingSurface;
        SurfaceControl surface = handwritingEventReceiverSurface != null ? handwritingEventReceiverSurface.getSurface() : this.mWindowManagerInternal.getHandwritingSurfaceForDisplay(i);
        if (surface == null) {
            Slog.e(TAG, "Failed to create input surface");
            return;
        }
        this.mHandwritingSurface = new HandwritingEventReceiverSurface(str, i, surface, createInputChannel);
        this.mHandwritingEventReceiver = new BatchedInputEventReceiver.SimpleBatchedInputEventReceiver(createInputChannel.dup(), this.mLooper, Choreographer.getInstance(), new BatchedInputEventReceiver.SimpleBatchedInputEventReceiver.InputEventListener() { // from class: com.android.server.inputmethod.HandwritingModeController$$ExternalSyntheticLambda2
            public final boolean onInputEvent(InputEvent inputEvent) {
                boolean onInputEvent;
                onInputEvent = HandwritingModeController.this.onInputEvent(inputEvent);
                return onInputEvent;
            }
        });
        this.mCurrentRequestId++;
    }

    public OptionalInt getCurrentRequestId() {
        if (this.mHandwritingSurface == null) {
            Slog.e(TAG, "Cannot get requestId: Handwriting was not initialized.");
            return OptionalInt.empty();
        }
        return OptionalInt.of(this.mCurrentRequestId);
    }

    public boolean isStylusGestureOngoing() {
        return this.mRecordingGesture;
    }

    public boolean hasOngoingStylusHandwritingSession() {
        HandwritingEventReceiverSurface handwritingEventReceiverSurface = this.mHandwritingSurface;
        return handwritingEventReceiverSurface != null && handwritingEventReceiverSurface.isIntercepting();
    }

    public void prepareStylusHandwritingDelegation(String str, String str2) {
        this.mDelegatePackageName = str;
        this.mDelegatorPackageName = str2;
        ArrayList arrayList = this.mHandwritingBuffer;
        if (arrayList == null) {
            this.mHandwritingBuffer = new ArrayList(getHandwritingBufferSize());
        } else {
            arrayList.ensureCapacity(getHandwritingBufferSize());
        }
        scheduleHandwritingDelegationTimeout();
    }

    public String getDelegatePackageName() {
        return this.mDelegatePackageName;
    }

    public String getDelegatorPackageName() {
        return this.mDelegatorPackageName;
    }

    public final void scheduleHandwritingDelegationTimeout() {
        Handler handler = this.mDelegationIdleTimeoutHandler;
        if (handler == null) {
            this.mDelegationIdleTimeoutHandler = new Handler(this.mLooper);
        } else {
            handler.removeCallbacks(this.mDelegationIdleTimeoutRunnable);
        }
        Runnable runnable = new Runnable() { // from class: com.android.server.inputmethod.HandwritingModeController$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                HandwritingModeController.this.lambda$scheduleHandwritingDelegationTimeout$0();
            }
        };
        this.mDelegationIdleTimeoutRunnable = runnable;
        this.mDelegationIdleTimeoutHandler.postDelayed(runnable, 3000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleHandwritingDelegationTimeout$0() {
        Slog.d(TAG, "Stylus handwriting delegation idle timed-out.");
        clearPendingHandwritingDelegation();
        ArrayList arrayList = this.mHandwritingBuffer;
        if (arrayList != null) {
            arrayList.forEach(new HandwritingModeController$$ExternalSyntheticLambda0());
            this.mHandwritingBuffer.clear();
            this.mHandwritingBuffer.trimToSize();
            this.mHandwritingBuffer.ensureCapacity(getHandwritingBufferSize());
        }
    }

    public final int getHandwritingBufferSize() {
        return (this.mDelegatePackageName == null || this.mDelegatorPackageName == null) ? 100 : 2000;
    }

    public void clearPendingHandwritingDelegation() {
        Handler handler = this.mDelegationIdleTimeoutHandler;
        if (handler != null) {
            handler.removeCallbacks(this.mDelegationIdleTimeoutRunnable);
            this.mDelegationIdleTimeoutHandler = null;
        }
        this.mDelegationIdleTimeoutRunnable = null;
        this.mDelegatorPackageName = null;
        this.mDelegatePackageName = null;
    }

    public HandwritingSession startHandwritingSession(int i, int i2, int i3, IBinder iBinder) {
        if (this.mHandwritingSurface == null) {
            Slog.e(TAG, "Cannot start handwriting session: Handwriting was not initialized.");
            return null;
        }
        if (i != this.mCurrentRequestId) {
            Slog.e(TAG, "Cannot start handwriting session: Invalid request id: " + i);
            return null;
        }
        if (!this.mRecordingGesture || this.mHandwritingBuffer.isEmpty()) {
            Slog.e(TAG, "Cannot start handwriting session: No stylus gesture is being recorded.");
            return null;
        }
        Objects.requireNonNull(this.mHandwritingEventReceiver, "Handwriting session was already transferred to IME.");
        MotionEvent motionEvent = (MotionEvent) this.mHandwritingBuffer.get(0);
        if (!this.mWindowManagerInternal.isPointInsideWindow(iBinder, this.mCurrentDisplayId, motionEvent.getRawX(), motionEvent.getRawY())) {
            Slog.e(TAG, "Cannot start handwriting session: Stylus gesture did not start inside the focused window.");
            return null;
        }
        InputManagerGlobal.getInstance().pilferPointers(this.mHandwritingSurface.getInputChannel().getToken());
        this.mHandwritingEventReceiver.dispose();
        this.mHandwritingEventReceiver = null;
        this.mRecordingGesture = false;
        if (this.mHandwritingSurface.isIntercepting()) {
            throw new IllegalStateException("Handwriting surface should not be already intercepting.");
        }
        this.mHandwritingSurface.startIntercepting(i2, i3);
        InputManagerGlobal.getInstance().setPointerIconType(1);
        return new HandwritingSession(this.mCurrentRequestId, this.mHandwritingSurface.getInputChannel(), this.mHandwritingBuffer);
    }

    public void reset() {
        reset(false);
    }

    public void setInkWindowInitializer(Runnable runnable) {
        this.mInkWindowInitRunnable = runnable;
    }

    public final void reset(boolean z) {
        InputEventReceiver inputEventReceiver = this.mHandwritingEventReceiver;
        if (inputEventReceiver != null) {
            inputEventReceiver.dispose();
            this.mHandwritingEventReceiver = null;
        }
        ArrayList arrayList = this.mHandwritingBuffer;
        if (arrayList != null) {
            arrayList.forEach(new HandwritingModeController$$ExternalSyntheticLambda0());
            this.mHandwritingBuffer.clear();
            if (!z) {
                this.mHandwritingBuffer = null;
            }
        }
        HandwritingEventReceiverSurface handwritingEventReceiverSurface = this.mHandwritingSurface;
        if (handwritingEventReceiverSurface != null) {
            handwritingEventReceiverSurface.getInputChannel().dispose();
            if (!z) {
                this.mHandwritingSurface.remove();
                this.mHandwritingSurface = null;
            }
        }
        clearPendingHandwritingDelegation();
        this.mRecordingGesture = false;
    }

    public final boolean onInputEvent(InputEvent inputEvent) {
        if (this.mHandwritingEventReceiver == null) {
            throw new IllegalStateException("Input Event should not be processed when IME has the spy channel.");
        }
        if (!(inputEvent instanceof MotionEvent)) {
            Slog.wtf(TAG, "Received non-motion event in stylus monitor.");
            return false;
        }
        MotionEvent motionEvent = (MotionEvent) inputEvent;
        if (!isStylusEvent(motionEvent)) {
            return false;
        }
        if (motionEvent.getDisplayId() != this.mCurrentDisplayId) {
            Slog.wtf(TAG, "Received stylus event associated with the incorrect display.");
            return false;
        }
        onStylusEvent(motionEvent);
        return true;
    }

    public final void onStylusEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (this.mInkWindowInitRunnable != null && (actionMasked == 9 || motionEvent.getAction() == 9)) {
            this.mInkWindowInitRunnable.run();
            this.mInkWindowInitRunnable = null;
        }
        if (TextUtils.isEmpty(this.mDelegatePackageName) && (actionMasked == 1 || actionMasked == 3)) {
            this.mRecordingGesture = false;
            this.mHandwritingBuffer.clear();
            return;
        }
        if (actionMasked == 0) {
            this.mRecordingGesture = true;
        }
        if (this.mRecordingGesture) {
            if (this.mHandwritingBuffer.size() >= getHandwritingBufferSize()) {
                this.mRecordingGesture = false;
            } else {
                this.mHandwritingBuffer.add(MotionEvent.obtain(motionEvent));
            }
        }
    }

    public final class HandwritingSession {
        public final InputChannel mHandwritingChannel;
        public final List mRecordedEvents;
        public final int mRequestId;

        public HandwritingSession(int i, InputChannel inputChannel, List list) {
            this.mRequestId = i;
            this.mHandwritingChannel = inputChannel;
            this.mRecordedEvents = list;
        }

        public int getRequestId() {
            return this.mRequestId;
        }

        public InputChannel getHandwritingChannel() {
            return this.mHandwritingChannel;
        }

        public List getRecordedEvents() {
            return this.mRecordedEvents;
        }
    }
}
