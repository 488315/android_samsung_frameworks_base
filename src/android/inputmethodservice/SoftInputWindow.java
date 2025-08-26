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

    SoftInputWindow(Context context, int i, KeyEvent.DispatcherState dispatcherState) {
        super(context, i);
        this.mWindowState = 0;
        this.mMinimizeFlag = false;
        this.mService = context;
        this.mDispatcherState = dispatcherState;
        this.mImm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        this.mDispatcherState.reset();
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
            } catch (WindowManager.BadTokenException | WindowManager.InvalidDisplayException unused) {
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

    void dismissForDestroyIfNecessary() {
        int i = this.mWindowState;
        if (i == 0 || i == 1) {
            updateWindowState(4);
            return;
        }
        if (i == 2) {
            try {
                getWindow().setWindowAnimations(0);
                dismiss();
            } catch (WindowManager.BadTokenException unused) {
                Log.i(TAG, "Probably the IME window token is already invalidated. No need to dismiss it.");
            }
            updateWindowState(4);
            return;
        }
        if (i == 3) {
            Log.i(TAG, "Not trying to dismiss the window because it is most likely unnecessary.");
            updateWindowState(4);
        } else {
            if (i == 4) {
                throw new IllegalStateException("dismissForDestroyIfNecessary can be called only once");
            }
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

    void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
        long jStart = protoOutputStream.start(j);
        protoOutputStream.write(1120986464262L, this.mWindowState);
        protoOutputStream.end(jStart);
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (this.mMinimizeFlag) {
            this.mMinimizeFlag = false;
            this.mImm.undoMinimizeSoftInput();
            return true;
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    public void setMinimizeFlag(boolean z) {
        this.mMinimizeFlag = z;
    }
}
