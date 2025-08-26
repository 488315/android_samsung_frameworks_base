package android.view;

import android.os.Debug;
import android.util.Log;
import android.util.proto.ProtoOutputStream;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.inputmethod.Flags;
import android.view.inputmethod.InputMethodManager;
import com.android.internal.inputmethod.InputMethodDebug;

/* loaded from: classes4.dex */
public final class ImeFocusController {
    private static final boolean DEBUG = Debug.semIsProductDev();
    private static final String TAG = "ImeFocusController";
    private InputMethodManagerDelegate mDelegate;
    private boolean mHasImeFocus = false;
    private final ViewRootImpl mViewRootImpl;

    public interface InputMethodManagerDelegate {
        void onPostWindowGainedFocus(View view, WindowManager.LayoutParams layoutParams);

        void onPreWindowGainedFocus(ViewRootImpl viewRootImpl);

        void onScheduledCheckFocus(ViewRootImpl viewRootImpl);

        void onViewDetachedFromWindow(View view, ViewRootImpl viewRootImpl);

        void onViewFocusChanged(View view, boolean z);

        void onWindowDismissed(ViewRootImpl viewRootImpl);

        void onWindowLostFocus(ViewRootImpl viewRootImpl);
    }

    ImeFocusController(ViewRootImpl viewRootImpl) {
        this.mViewRootImpl = viewRootImpl;
    }

    private InputMethodManagerDelegate getImmDelegate() {
        if (this.mDelegate == null) {
            this.mDelegate = ((InputMethodManager) this.mViewRootImpl.mContext.getSystemService(InputMethodManager.class)).getDelegate();
        }
        return this.mDelegate;
    }

    void onMovedToDisplay() {
        this.mDelegate = null;
    }

    void onTraversal(boolean z, WindowManager.LayoutParams layoutParams) {
        boolean zMayUseInputMethod = WindowManager.LayoutParams.mayUseInputMethod(layoutParams.flags);
        if (!z || isInLocalFocusMode(layoutParams) || zMayUseInputMethod == this.mHasImeFocus) {
            return;
        }
        this.mHasImeFocus = zMayUseInputMethod;
        if (zMayUseInputMethod) {
            getImmDelegate().onPreWindowGainedFocus(this.mViewRootImpl);
            View viewFindFocus = this.mViewRootImpl.mView.findFocus();
            if (viewFindFocus == null) {
                viewFindFocus = this.mViewRootImpl.mView;
            }
            getImmDelegate().onPostWindowGainedFocus(viewFindFocus, layoutParams);
        }
    }

    void onPreWindowFocus(boolean z, WindowManager.LayoutParams layoutParams) {
        boolean zMayUseInputMethod = WindowManager.LayoutParams.mayUseInputMethod(layoutParams.flags);
        this.mHasImeFocus = zMayUseInputMethod;
        if (!z || !zMayUseInputMethod || isInLocalFocusMode(layoutParams)) {
            printLog("onPreWindowFocus: skipped", z);
            if (z) {
                return;
            }
            getImmDelegate().onWindowLostFocus(this.mViewRootImpl);
            return;
        }
        getImmDelegate().onPreWindowGainedFocus(this.mViewRootImpl);
    }

    void onPostWindowFocus(View view, boolean z, WindowManager.LayoutParams layoutParams) {
        if (!z || !this.mHasImeFocus || isInLocalFocusMode(layoutParams)) {
            printLog("onPostWindowFocus: skipped", z);
            return;
        }
        if (view == null) {
            view = this.mViewRootImpl.mView;
        }
        if (DEBUG) {
            Log.v(TAG, "onWindowFocus: " + view + " softInputMode=" + InputMethodDebug.softInputModeToString(layoutParams.softInputMode));
        }
        getImmDelegate().onPostWindowGainedFocus(view, layoutParams);
    }

    void onScheduledCheckFocus() {
        getImmDelegate().onScheduledCheckFocus(this.mViewRootImpl);
    }

    void onViewFocusChanged(View view, boolean z) {
        getImmDelegate().onViewFocusChanged(view, z);
    }

    void onViewDetachedFromWindow(View view) {
        getImmDelegate().onViewDetachedFromWindow(view, this.mViewRootImpl);
    }

    void onWindowDismissed() {
        getImmDelegate().onWindowDismissed(this.mViewRootImpl);
        this.mHasImeFocus = false;
    }

    private static boolean isInLocalFocusMode(WindowManager.LayoutParams layoutParams) {
        return (layoutParams.flags & 268435456) != 0;
    }

    int onProcessImeInputStage(Object obj, InputEvent inputEvent, WindowManager.LayoutParams layoutParams, InputMethodManager.FinishedInputEventCallback finishedInputEventCallback) {
        if (!this.mHasImeFocus || isInLocalFocusMode(layoutParams)) {
            return 0;
        }
        if (Flags.refactorInsetsController() && (inputEvent instanceof KeyEvent) && ((KeyEvent) inputEvent).getKeyCode() == 4) {
            InsetsController insetsController = this.mViewRootImpl.getInsetsController();
            if (insetsController.getAnimationType(WindowInsets.Type.ime()) == 1 || insetsController.isPredictiveBackImeHideAnimInProgress()) {
                return 0;
            }
        }
        InputMethodManager inputMethodManager = (InputMethodManager) this.mViewRootImpl.mContext.getSystemService(InputMethodManager.class);
        if (inputMethodManager == null) {
            return 0;
        }
        return inputMethodManager.dispatchInputEvent(inputEvent, obj, finishedInputEventCallback, this.mViewRootImpl.mHandler);
    }

    boolean hasImeFocus() {
        return this.mHasImeFocus;
    }

    void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
        long jStart = protoOutputStream.start(j);
        protoOutputStream.write(1133871366145L, this.mHasImeFocus);
        protoOutputStream.end(jStart);
    }

    private void printLog(String str, boolean z) {
        Log.i(TAG, str + " hasWindowFocus=" + z + " mHasImeFocus=" + this.mHasImeFocus);
    }
}
