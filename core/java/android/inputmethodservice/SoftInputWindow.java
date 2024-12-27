package android.inputmethodservice;

import android.app.Dialog;
import android.content.Context;
import android.os.IBinder;
import android.util.Log;
import android.util.proto.ProtoOutputStream;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes2.dex */
final class SoftInputWindow extends Dialog {
    private static final boolean DEBUG = false;
    private static final String TAG = "SoftInputWindow";
    private final KeyEvent.DispatcherState mDispatcherState;
    InputMethodManager mImm;
    private boolean mMinimizeFlag;
    private final Context mService;
    private int mWindowState;

    @Retention(RetentionPolicy.SOURCE)
    private @interface WindowState {
        public static final int DESTROYED = 4;
        public static final int REJECTED_AT_LEAST_ONCE = 3;
        public static final int SHOWN_AT_LEAST_ONCE = 2;
        public static final int TOKEN_PENDING = 0;
        public static final int TOKEN_SET = 1;
    }

    @Override // android.app.Dialog
    protected boolean allowsRegisterDefaultOnBackInvokedCallback() {
        return false;
    }

    void setToken(IBinder token) {
        switch (this.mWindowState) {
            case 0:
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.token = token;
                getWindow().setAttributes(lp);
                updateWindowState(1);
                getWindow().getDecorView().setVisibility(4);
                show();
                return;
            case 1:
            case 2:
            case 3:
                throw new IllegalStateException("setToken can be called only once");
            case 4:
                Log.i(TAG, "Ignoring setToken() because window is already destroyed.");
                return;
            default:
                throw new IllegalStateException("Unexpected state=" + this.mWindowState);
        }
    }

    SoftInputWindow(Context service, int theme, KeyEvent.DispatcherState dispatcherState) {
        super(service, theme);
        this.mWindowState = 0;
        this.mMinimizeFlag = false;
        this.mService = service;
        this.mDispatcherState = dispatcherState;
        this.mImm = (InputMethodManager) service.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        this.mDispatcherState.reset();
    }

    @Override // android.app.Dialog
    public void show() {
        switch (this.mWindowState) {
            case 0:
                throw new IllegalStateException("Window token is not set yet.");
            case 1:
            case 2:
                try {
                    super.show();
                    updateWindowState(2);
                    return;
                } catch (WindowManager.BadTokenException
                        | WindowManager.InvalidDisplayException e) {
                    Log.i(
                            TAG,
                            "Probably the IME window token is already invalidated. show() does"
                                    + " nothing.");
                    updateWindowState(3);
                    return;
                }
            case 3:
                Log.i(TAG, "Not trying to call show() because it was already rejected once.");
                return;
            case 4:
                Log.i(TAG, "Ignoring show() because the window is already destroyed.");
                return;
            default:
                throw new IllegalStateException("Unexpected state=" + this.mWindowState);
        }
    }

    void dismissForDestroyIfNecessary() {
        switch (this.mWindowState) {
            case 0:
            case 1:
                updateWindowState(4);
                return;
            case 2:
                try {
                    getWindow().setWindowAnimations(0);
                    dismiss();
                } catch (WindowManager.BadTokenException e) {
                    Log.i(
                            TAG,
                            "Probably the IME window token is already invalidated. No need to"
                                    + " dismiss it.");
                }
                updateWindowState(4);
                return;
            case 3:
                Log.i(
                        TAG,
                        "Not trying to dismiss the window because it is most likely unnecessary.");
                updateWindowState(4);
                return;
            case 4:
                throw new IllegalStateException(
                        "dismissForDestroyIfNecessary can be called only once");
            default:
                throw new IllegalStateException("Unexpected state=" + this.mWindowState);
        }
    }

    private void updateWindowState(int newState) {
        this.mWindowState = newState;
    }

    private static String stateToString(int state) {
        switch (state) {
            case 0:
                return "TOKEN_PENDING";
            case 1:
                return "TOKEN_SET";
            case 2:
                return "SHOWN_AT_LEAST_ONCE";
            case 3:
                return "REJECTED_AT_LEAST_ONCE";
            case 4:
                return "DESTROYED";
            default:
                throw new IllegalStateException("Unknown state=" + state);
        }
    }

    void dumpDebug(ProtoOutputStream proto, long fieldId) {
        long token = proto.start(fieldId);
        proto.write(1120986464262L, this.mWindowState);
        proto.end(token);
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (this.mMinimizeFlag) {
            this.mMinimizeFlag = false;
            this.mImm.undoMinimizeSoftInput();
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }

    public void setMinimizeFlag(boolean flag) {
        this.mMinimizeFlag = flag;
    }
}
