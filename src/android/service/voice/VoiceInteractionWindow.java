package android.service.voice;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.IBinder;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.WindowManager;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes3.dex */
final class VoiceInteractionWindow extends Dialog {
    private static final boolean DEBUG = false;
    private static final String TAG = "VoiceInteractionWindow";
    private final Rect mBounds;
    private final Callback mCallback;
    private final KeyEvent.DispatcherState mDispatcherState;
    private final int mGravity;
    private final KeyEvent.Callback mKeyEventCallback;
    private final String mName;
    private final boolean mTakesFocus;
    private int mWindowState;
    private final int mWindowType;

    interface Callback {
        void onBackPressed();
    }

    @Retention(RetentionPolicy.SOURCE)
    private @interface WindowState {
        public static final int DESTROYED = 4;
        public static final int REJECTED_AT_LEAST_ONCE = 3;
        public static final int SHOWN_AT_LEAST_ONCE = 2;
        public static final int TOKEN_PENDING = 0;
        public static final int TOKEN_SET = 1;
    }

    void setToken(IBinder iBinder) {
        int i = this.mWindowState;
        if (i == 0) {
            WindowManager.LayoutParams attributes = getWindow().getAttributes();
            attributes.token = iBinder;
            getWindow().setAttributes(attributes);
            updateWindowState(1);
            getWindow().getDecorView().setVisibility(4);
            show();
            return;
        }
        if (i == 1 || i == 2 || i == 3) {
            throw new IllegalStateException("setToken can be called only once");
        }
        if (i == 4) {
            Log.i(TAG, "Ignoring setToken() because window is already destroyed.");
        } else {
            throw new IllegalStateException("Unexpected state=" + this.mWindowState);
        }
    }

    VoiceInteractionWindow(Context context, String str, int i, Callback callback, KeyEvent.Callback callback2, KeyEvent.DispatcherState dispatcherState, int i2, int i3, boolean z) {
        super(context, i);
        this.mBounds = new Rect();
        this.mWindowState = 0;
        this.mName = str;
        this.mCallback = callback;
        this.mKeyEventCallback = callback2;
        this.mDispatcherState = dispatcherState;
        this.mWindowType = i2;
        this.mGravity = i3;
        this.mTakesFocus = z;
        initDockWindow();
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        this.mDispatcherState.reset();
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        getWindow().getDecorView().getHitRect(this.mBounds);
        if (motionEvent.isWithinBoundsNoHistory(this.mBounds.left, this.mBounds.top, this.mBounds.right - 1, this.mBounds.bottom - 1)) {
            return super.dispatchTouchEvent(motionEvent);
        }
        MotionEvent motionEventClampNoHistory = motionEvent.clampNoHistory(this.mBounds.left, this.mBounds.top, this.mBounds.right - 1, this.mBounds.bottom - 1);
        boolean zDispatchTouchEvent = super.dispatchTouchEvent(motionEventClampNoHistory);
        motionEventClampNoHistory.recycle();
        return zDispatchTouchEvent;
    }

    private void updateWidthHeight(WindowManager.LayoutParams layoutParams) {
        if (layoutParams.gravity == 48 || layoutParams.gravity == 80) {
            layoutParams.width = -1;
            layoutParams.height = -2;
        } else {
            layoutParams.width = -2;
            layoutParams.height = -1;
        }
    }

    @Override // android.app.Dialog, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        KeyEvent.Callback callback = this.mKeyEventCallback;
        if (callback == null || !callback.onKeyDown(i, keyEvent)) {
            return super.onKeyDown(i, keyEvent);
        }
        return true;
    }

    @Override // android.app.Dialog, android.view.KeyEvent.Callback
    public boolean onKeyLongPress(int i, KeyEvent keyEvent) {
        KeyEvent.Callback callback = this.mKeyEventCallback;
        if (callback == null || !callback.onKeyLongPress(i, keyEvent)) {
            return super.onKeyLongPress(i, keyEvent);
        }
        return true;
    }

    @Override // android.app.Dialog, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        KeyEvent.Callback callback = this.mKeyEventCallback;
        if (callback == null || !callback.onKeyUp(i, keyEvent)) {
            return super.onKeyUp(i, keyEvent);
        }
        return true;
    }

    @Override // android.app.Dialog, android.view.KeyEvent.Callback
    public boolean onKeyMultiple(int i, int i2, KeyEvent keyEvent) {
        KeyEvent.Callback callback = this.mKeyEventCallback;
        if (callback == null || !callback.onKeyMultiple(i, i2, keyEvent)) {
            return super.onKeyMultiple(i, i2, keyEvent);
        }
        return true;
    }

    @Override // android.app.Dialog
    public void onBackPressed() {
        Callback callback = this.mCallback;
        if (callback != null) {
            callback.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    private void initDockWindow() {
        int i;
        int i2;
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.type = this.mWindowType;
        attributes.setTitle(this.mName);
        attributes.gravity = this.mGravity;
        updateWidthHeight(attributes);
        getWindow().setAttributes(attributes);
        if (this.mTakesFocus) {
            i = 288;
            i2 = 298;
        } else {
            i2 = 266;
            i = 264;
        }
        getWindow().setFlags(i, i2);
    }

    @Override // android.app.Dialog
    public void show() {
        int i = this.mWindowState;
        if (i == 0) {
            throw new IllegalStateException("Window token is not set yet.");
        }
        if (i == 1 || i == 2) {
            try {
                super.show();
                updateWindowState(2);
                return;
            } catch (WindowManager.BadTokenException unused) {
                Log.i(TAG, "Probably the IME window token is already invalidated. show() does nothing.");
                updateWindowState(3);
                return;
            }
        }
        if (i == 3) {
            Log.i(TAG, "Not trying to call show() because it was already rejected once.");
        } else if (i == 4) {
            Log.i(TAG, "Ignoring show() because the window is already destroyed.");
        } else {
            throw new IllegalStateException("Unexpected state=" + this.mWindowState);
        }
    }

    private void updateWindowState(int i) {
        this.mWindowState = i;
    }

    private static String stateToString(int i) {
        if (i == 0) {
            return "TOKEN_PENDING";
        }
        if (i == 1) {
            return "TOKEN_SET";
        }
        if (i == 2) {
            return "SHOWN_AT_LEAST_ONCE";
        }
        if (i == 3) {
            return "REJECTED_AT_LEAST_ONCE";
        }
        if (i == 4) {
            return "DESTROYED";
        }
        throw new IllegalStateException("Unknown state=" + i);
    }
}
