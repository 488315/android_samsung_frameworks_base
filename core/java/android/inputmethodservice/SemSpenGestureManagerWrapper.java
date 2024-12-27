package android.inputmethodservice;

import android.content.Context;
import android.util.Log;
import com.samsung.android.content.smartclip.SpenGestureManager;

/* loaded from: classes2.dex */
final class SemSpenGestureManagerWrapper {
    static SpenGestureManager mSpenGestureManager = null;

    SemSpenGestureManagerWrapper() {
    }

    static void notifyKeyboardClosedForAGIF(Context context) {
        if (mSpenGestureManager == null) {
            mSpenGestureManager = (SpenGestureManager) context.getSystemService(Context.SEM_SPEN_GESTURE_SERVICE);
        }
        Log.d("InputMethodService", "notifyKeyboardClosed called.");
        if (mSpenGestureManager != null) {
            mSpenGestureManager.notifyKeyboardClosed();
        }
    }
}
