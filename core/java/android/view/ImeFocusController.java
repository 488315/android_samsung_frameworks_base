package android.view;

import android.os.Debug;
import android.util.Log;
import android.util.proto.ProtoOutputStream;
import android.view.inputmethod.InputMethodManager;

import com.android.internal.inputmethod.InputMethodDebug;

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
            this.mDelegate =
                    ((InputMethodManager)
                                    this.mViewRootImpl.mContext.getSystemService(
                                            InputMethodManager.class))
                            .getDelegate();
        }
        return this.mDelegate;
    }

    void onMovedToDisplay() {
        this.mDelegate = null;
    }

    void onTraversal(boolean hasWindowFocus, WindowManager.LayoutParams windowAttribute) {
        boolean hasImeFocus = WindowManager.LayoutParams.mayUseInputMethod(windowAttribute.flags);
        if (!hasWindowFocus
                || isInLocalFocusMode(windowAttribute)
                || hasImeFocus == this.mHasImeFocus) {
            return;
        }
        this.mHasImeFocus = hasImeFocus;
        if (this.mHasImeFocus) {
            getImmDelegate().onPreWindowGainedFocus(this.mViewRootImpl);
            View focusedView = this.mViewRootImpl.mView.findFocus();
            View viewForWindowFocus = focusedView != null ? focusedView : this.mViewRootImpl.mView;
            getImmDelegate().onPostWindowGainedFocus(viewForWindowFocus, windowAttribute);
        }
    }

    void onPreWindowFocus(boolean hasWindowFocus, WindowManager.LayoutParams windowAttribute) {
        this.mHasImeFocus = WindowManager.LayoutParams.mayUseInputMethod(windowAttribute.flags);
        if (!hasWindowFocus || !this.mHasImeFocus || isInLocalFocusMode(windowAttribute)) {
            printLog("onPreWindowFocus: skipped", hasWindowFocus);
            if (!hasWindowFocus) {
                getImmDelegate().onWindowLostFocus(this.mViewRootImpl);
                return;
            }
            return;
        }
        getImmDelegate().onPreWindowGainedFocus(this.mViewRootImpl);
    }

    void onPostWindowFocus(
            View focusedView, boolean hasWindowFocus, WindowManager.LayoutParams windowAttribute) {
        if (!hasWindowFocus || !this.mHasImeFocus || isInLocalFocusMode(windowAttribute)) {
            printLog("onPostWindowFocus: skipped", hasWindowFocus);
            return;
        }
        View viewForWindowFocus = focusedView != null ? focusedView : this.mViewRootImpl.mView;
        if (DEBUG) {
            Log.v(
                    TAG,
                    "onWindowFocus: "
                            + viewForWindowFocus
                            + " softInputMode="
                            + InputMethodDebug.softInputModeToString(
                                    windowAttribute.softInputMode));
        }
        getImmDelegate().onPostWindowGainedFocus(viewForWindowFocus, windowAttribute);
    }

    void onScheduledCheckFocus() {
        getImmDelegate().onScheduledCheckFocus(this.mViewRootImpl);
    }

    void onViewFocusChanged(View view, boolean hasFocus) {
        getImmDelegate().onViewFocusChanged(view, hasFocus);
    }

    void onViewDetachedFromWindow(View view) {
        getImmDelegate().onViewDetachedFromWindow(view, this.mViewRootImpl);
    }

    void onWindowDismissed() {
        getImmDelegate().onWindowDismissed(this.mViewRootImpl);
        this.mHasImeFocus = false;
    }

    private static boolean isInLocalFocusMode(WindowManager.LayoutParams windowAttribute) {
        return (windowAttribute.flags & 268435456) != 0;
    }

    int onProcessImeInputStage(
            Object token,
            InputEvent event,
            WindowManager.LayoutParams windowAttribute,
            InputMethodManager.FinishedInputEventCallback callback) {
        InputMethodManager imm;
        if (!this.mHasImeFocus
                || isInLocalFocusMode(windowAttribute)
                || (imm =
                                (InputMethodManager)
                                        this.mViewRootImpl.mContext.getSystemService(
                                                InputMethodManager.class))
                        == null) {
            return 0;
        }
        return imm.dispatchInputEvent(event, token, callback, this.mViewRootImpl.mHandler);
    }

    boolean hasImeFocus() {
        return this.mHasImeFocus;
    }

    void dumpDebug(ProtoOutputStream proto, long fieldId) {
        long token = proto.start(fieldId);
        proto.write(1133871366145L, this.mHasImeFocus);
        proto.end(token);
    }

    private void printLog(String msg, boolean hasWindowFocus) {
        Log.i(
                TAG,
                msg + " hasWindowFocus=" + hasWindowFocus + " mHasImeFocus=" + this.mHasImeFocus);
    }
}
