package com.android.server.inputmethod;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManagerInternal;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.hardware.input.InputManager;
import android.hardware.input.InputManagerGlobal;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Slog;
import android.view.BatchedInputEventReceiver;
import android.view.Choreographer;
import android.view.InputChannel;
import android.view.InputEvent;
import android.view.InputEventReceiver;
import android.view.InputWindowHandle;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.SurfaceControl;
import com.android.server.LocalServices;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.input.InputManagerService;
import com.android.server.inputmethod.InputMethodManagerService;
import com.android.server.pm.PackageManagerService;
import com.android.server.wm.WindowManagerInternal;
import com.android.text.flags.Flags;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.OptionalInt;

public final class HandwritingModeController {
    public final Context mContext;
    public String mDelegatePackageName;
    public boolean mDelegationConnectionlessFlow;
    public Handler mDelegationIdleTimeoutHandler;
    public HandwritingModeController$$ExternalSyntheticLambda0 mDelegationIdleTimeoutRunnable;
    public boolean mDelegatorFromDefaultHomePackage;
    public String mDelegatorPackageName;
    public final Runnable mDiscardDelegationTextRunnable;
    public ArrayList mHandwritingBuffer;
    public InputEventReceiver mHandwritingEventReceiver;
    public HandwritingEventReceiverSurface mHandwritingSurface;
    public Runnable mInkWindowInitRunnable;
    public final Looper mLooper;
    public boolean mRecordingGesture;
    public boolean mRecordingGestureAfterStylusUp;
    public int mCurrentDisplayId = -1;
    public final InputManagerService.LocalService mInputManagerInternal = (InputManagerService.LocalService) LocalServices.getService(InputManagerService.LocalService.class);
    public final WindowManagerInternal mWindowManagerInternal = (WindowManagerInternal) LocalServices.getService(WindowManagerInternal.class);
    public final PackageManagerInternal mPackageManagerInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
    public int mCurrentRequestId = 0;

    public final class HandwritingSession {
        public final InputChannel mHandwritingChannel;
        public final List mRecordedEvents;
        public final int mRequestId;

        public HandwritingSession(int i, InputChannel inputChannel, List list) {
            this.mRequestId = i;
            this.mHandwritingChannel = inputChannel;
            this.mRecordedEvents = list;
        }
    }

    public HandwritingModeController(Context context, Looper looper, InputMethodManagerService.InkWindowInitializer inkWindowInitializer, InputMethodManagerService$$ExternalSyntheticLambda1 inputMethodManagerService$$ExternalSyntheticLambda1) {
        this.mContext = context;
        this.mLooper = looper;
        this.mInkWindowInitRunnable = inkWindowInitializer;
        this.mDiscardDelegationTextRunnable = inputMethodManagerService$$ExternalSyntheticLambda1;
    }

    public final void clearPendingHandwritingDelegation() {
        Handler handler = this.mDelegationIdleTimeoutHandler;
        if (handler != null) {
            handler.removeCallbacks(this.mDelegationIdleTimeoutRunnable);
            this.mDelegationIdleTimeoutHandler = null;
        }
        this.mDelegationIdleTimeoutRunnable = null;
        this.mDelegatorPackageName = null;
        this.mDelegatePackageName = null;
        this.mDelegatorFromDefaultHomePackage = false;
        if (this.mDelegationConnectionlessFlow) {
            this.mDelegationConnectionlessFlow = false;
            this.mDiscardDelegationTextRunnable.run();
        }
    }

    public final OptionalInt getCurrentRequestId() {
        if (this.mHandwritingSurface != null) {
            return OptionalInt.of(this.mCurrentRequestId);
        }
        Slog.e("HandwritingModeController", "Cannot get requestId: Handwriting was not initialized.");
        return OptionalInt.empty();
    }

    public final int getHandwritingBufferSize() {
        return (this.mDelegatePackageName == null || this.mDelegatorPackageName == null) ? 100 : 2000;
    }

    public final void initializeHandwritingSpy(int i) {
        reset(i == this.mCurrentDisplayId);
        this.mCurrentDisplayId = i;
        if (this.mHandwritingBuffer == null) {
            this.mHandwritingBuffer = new ArrayList(getHandwritingBufferSize());
        }
        String m = VibrationParam$1$$ExternalSyntheticOutline0.m(i, "stylus-handwriting-event-receiver-");
        InputChannel createInputChannel = InputManagerService.this.createInputChannel(m);
        Objects.requireNonNull(createInputChannel, "Failed to create input channel");
        HandwritingEventReceiverSurface handwritingEventReceiverSurface = this.mHandwritingSurface;
        SurfaceControl handwritingSurfaceForDisplay = handwritingEventReceiverSurface != null ? handwritingEventReceiverSurface.mInputSurface : this.mWindowManagerInternal.getHandwritingSurfaceForDisplay(i);
        if (handwritingSurfaceForDisplay == null) {
            Slog.e("HandwritingModeController", "Failed to create input surface");
            return;
        }
        this.mHandwritingSurface = new HandwritingEventReceiverSurface(m, i, handwritingSurfaceForDisplay, createInputChannel);
        this.mHandwritingEventReceiver = new BatchedInputEventReceiver.SimpleBatchedInputEventReceiver(createInputChannel.dup(), this.mLooper, Choreographer.getInstance(), new BatchedInputEventReceiver.SimpleBatchedInputEventReceiver.InputEventListener() { // from class: com.android.server.inputmethod.HandwritingModeController$$ExternalSyntheticLambda2
            public final boolean onInputEvent(InputEvent inputEvent) {
                HandwritingModeController handwritingModeController = HandwritingModeController.this;
                if (handwritingModeController.mHandwritingEventReceiver == null) {
                    throw new IllegalStateException("Input Event should not be processed when IME has the spy channel.");
                }
                if (!(inputEvent instanceof MotionEvent)) {
                    Slog.wtf("HandwritingModeController", "Received non-motion event in stylus monitor.");
                    return false;
                }
                MotionEvent motionEvent = (MotionEvent) inputEvent;
                if (!motionEvent.isStylusPointer()) {
                    return false;
                }
                if (motionEvent.getDisplayId() != handwritingModeController.mCurrentDisplayId) {
                    Slog.wtf("HandwritingModeController", "Received stylus event associated with the incorrect display.");
                    return false;
                }
                int actionMasked = motionEvent.getActionMasked();
                if (handwritingModeController.mInkWindowInitRunnable != null && (actionMasked == 9 || motionEvent.getAction() == 9)) {
                    handwritingModeController.mInkWindowInitRunnable.run();
                    handwritingModeController.mInkWindowInitRunnable = null;
                } else if (!motionEvent.isHoverEvent()) {
                    if ((TextUtils.isEmpty(handwritingModeController.mDelegatePackageName) || handwritingModeController.mDelegationConnectionlessFlow) && (actionMasked == 1 || actionMasked == 3)) {
                        handwritingModeController.mRecordingGesture = false;
                        if (Flags.handwritingEndOfLineTap() && actionMasked == 1) {
                            handwritingModeController.mRecordingGestureAfterStylusUp = true;
                        } else {
                            handwritingModeController.mHandwritingBuffer.clear();
                        }
                    }
                    if (actionMasked == 0) {
                        if (handwritingModeController.mRecordingGestureAfterStylusUp) {
                            handwritingModeController.mHandwritingBuffer.clear();
                            handwritingModeController.mRecordingGestureAfterStylusUp = false;
                        }
                        handwritingModeController.mRecordingGesture = true;
                    }
                    if (handwritingModeController.mRecordingGesture || handwritingModeController.mRecordingGestureAfterStylusUp) {
                        if (handwritingModeController.mHandwritingBuffer.size() >= handwritingModeController.getHandwritingBufferSize()) {
                            handwritingModeController.mRecordingGesture = false;
                            if (handwritingModeController.mRecordingGestureAfterStylusUp) {
                                handwritingModeController.mHandwritingBuffer.clear();
                                handwritingModeController.mRecordingGestureAfterStylusUp = false;
                            }
                        } else {
                            handwritingModeController.mHandwritingBuffer.add(MotionEvent.obtain(motionEvent));
                        }
                    }
                }
                return true;
            }
        });
        this.mCurrentRequestId++;
    }

    public final boolean isStylusGestureOngoing() {
        if (this.mRecordingGestureAfterStylusUp && !this.mHandwritingBuffer.isEmpty()) {
            ArrayList arrayList = this.mHandwritingBuffer;
            MotionEvent motionEvent = (MotionEvent) arrayList.get(arrayList.size() - 1);
            if (motionEvent.getActionMasked() == 1) {
                return SystemClock.uptimeMillis() - motionEvent.getEventTime() < 200;
            }
        }
        return this.mRecordingGesture;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.server.inputmethod.HandwritingModeController$$ExternalSyntheticLambda0, java.lang.Runnable] */
    public final void prepareStylusHandwritingDelegation(int i, String str, String str2, boolean z) {
        ComponentName defaultHomeActivity;
        this.mDelegatePackageName = str;
        this.mDelegatorPackageName = str2;
        this.mDelegatorFromDefaultHomePackage = false;
        if (!str2.equals(str) && (defaultHomeActivity = ((PackageManagerService.PackageManagerInternalImpl) this.mPackageManagerInternal).mService.snapshotComputer().getDefaultHomeActivity(i)) != null) {
            this.mDelegatorFromDefaultHomePackage = str2.equals(defaultHomeActivity.getPackageName());
        }
        this.mDelegationConnectionlessFlow = z;
        if (!z) {
            ArrayList arrayList = this.mHandwritingBuffer;
            if (arrayList == null) {
                this.mHandwritingBuffer = new ArrayList(getHandwritingBufferSize());
            } else {
                arrayList.ensureCapacity(getHandwritingBufferSize());
            }
        }
        Handler handler = this.mDelegationIdleTimeoutHandler;
        if (handler == null) {
            this.mDelegationIdleTimeoutHandler = new Handler(this.mLooper);
        } else {
            handler.removeCallbacks(this.mDelegationIdleTimeoutRunnable);
        }
        ?? r2 = new Runnable() { // from class: com.android.server.inputmethod.HandwritingModeController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                HandwritingModeController handwritingModeController = HandwritingModeController.this;
                handwritingModeController.getClass();
                Slog.d("HandwritingModeController", "Stylus handwriting delegation idle timed-out.");
                handwritingModeController.clearPendingHandwritingDelegation();
                ArrayList arrayList2 = handwritingModeController.mHandwritingBuffer;
                if (arrayList2 != null) {
                    arrayList2.forEach(new HandwritingModeController$$ExternalSyntheticLambda1());
                    handwritingModeController.mHandwritingBuffer.clear();
                    handwritingModeController.mHandwritingBuffer.trimToSize();
                    handwritingModeController.mHandwritingBuffer.ensureCapacity(handwritingModeController.getHandwritingBufferSize());
                }
            }
        };
        this.mDelegationIdleTimeoutRunnable = r2;
        this.mDelegationIdleTimeoutHandler.postDelayed(r2, 3000L);
    }

    public final void reset(boolean z) {
        InputEventReceiver inputEventReceiver = this.mHandwritingEventReceiver;
        if (inputEventReceiver != null) {
            inputEventReceiver.dispose();
            this.mHandwritingEventReceiver = null;
        }
        ArrayList arrayList = this.mHandwritingBuffer;
        if (arrayList != null) {
            arrayList.forEach(new HandwritingModeController$$ExternalSyntheticLambda1());
            this.mHandwritingBuffer.clear();
            if (!z) {
                this.mHandwritingBuffer = null;
            }
        }
        HandwritingEventReceiverSurface handwritingEventReceiverSurface = this.mHandwritingSurface;
        if (handwritingEventReceiverSurface != null) {
            handwritingEventReceiverSurface.mClientChannel.dispose();
            if (!z) {
                HandwritingEventReceiverSurface handwritingEventReceiverSurface2 = this.mHandwritingSurface;
                handwritingEventReceiverSurface2.getClass();
                SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
                transaction.remove(handwritingEventReceiverSurface2.mInputSurface);
                transaction.apply();
                this.mHandwritingSurface = null;
            }
        }
        if (!this.mDelegationConnectionlessFlow) {
            clearPendingHandwritingDelegation();
        }
        this.mRecordingGesture = false;
        this.mRecordingGestureAfterStylusUp = false;
    }

    public final HandwritingSession startHandwritingSession(int i, int i2, int i3, IBinder iBinder) {
        clearPendingHandwritingDelegation();
        if (this.mHandwritingSurface == null) {
            Slog.e("HandwritingModeController", "Cannot start handwriting session: Handwriting was not initialized.");
            return null;
        }
        if (i != this.mCurrentRequestId) {
            NandswapManager$$ExternalSyntheticOutline0.m(i, "Cannot start handwriting session: Invalid request id: ", "HandwritingModeController");
            return null;
        }
        if (!isStylusGestureOngoing()) {
            Slog.e("HandwritingModeController", "Cannot start handwriting session: No stylus gesture is being recorded.");
            return null;
        }
        Objects.requireNonNull(this.mHandwritingEventReceiver, "Handwriting session was already transferred to IME.");
        MotionEvent motionEvent = (MotionEvent) this.mHandwritingBuffer.get(0);
        if (!this.mWindowManagerInternal.isPointInsideWindow(iBinder, this.mCurrentDisplayId, motionEvent.getRawX(), motionEvent.getRawY())) {
            Slog.e("HandwritingModeController", "Cannot start handwriting session: Stylus gesture did not start inside the focused window.");
            return null;
        }
        InputManagerGlobal.getInstance().pilferPointers(this.mHandwritingSurface.mClientChannel.getToken());
        this.mHandwritingEventReceiver.dispose();
        this.mHandwritingEventReceiver = null;
        this.mRecordingGesture = false;
        this.mRecordingGestureAfterStylusUp = false;
        HandwritingEventReceiverSurface handwritingEventReceiverSurface = this.mHandwritingSurface;
        if (handwritingEventReceiverSurface.mIsIntercepting) {
            throw new IllegalStateException("Handwriting surface should not be already intercepting.");
        }
        InputWindowHandle inputWindowHandle = handwritingEventReceiverSurface.mWindowHandle;
        inputWindowHandle.ownerPid = i2;
        inputWindowHandle.ownerUid = i3;
        inputWindowHandle.inputConfig &= -16385;
        new SurfaceControl.Transaction().setInputWindowInfo(handwritingEventReceiverSurface.mInputSurface, handwritingEventReceiverSurface.mWindowHandle).apply();
        handwritingEventReceiverSurface.mIsIntercepting = true;
        InputManager inputManager = (InputManager) this.mContext.getSystemService(InputManager.class);
        Objects.requireNonNull(inputManager);
        inputManager.setPointerIcon(PointerIcon.getSystemIcon(this.mContext, 1), motionEvent.getDisplayId(), motionEvent.getDeviceId(), motionEvent.getPointerId(0), this.mHandwritingSurface.mClientChannel.getToken());
        return new HandwritingSession(this.mCurrentRequestId, this.mHandwritingSurface.mClientChannel, this.mHandwritingBuffer);
    }
}
