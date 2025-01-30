package com.samsung.android.desktopsystemui.sharedlib.system;

import android.app.AppGlobals;
import android.hardware.input.InputManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class InputServiceWrapper {
    private static final int MSG_MULTIFINGERGESTURE = 1;
    private static final String TAG = "[DS]InputServiceWrapper";
    private static final InputServiceWrapper sInstance = new InputServiceWrapper();
    private final Object mLock = new Object();
    private ArrayList<DeXMultiFingerGesture> mCallbacks = new ArrayList<>();
    private Handler mHandler = new HandlerC4572H(Looper.getMainLooper());
    private final InputManager.SemOnMultiFingerGestureListener mMultiFingerGestureListener = new InputManager.SemOnMultiFingerGestureListener() { // from class: com.samsung.android.desktopsystemui.sharedlib.system.InputServiceWrapper$$ExternalSyntheticLambda0
        public final void onMultiFingerGesture(int i, int i2) {
            InputServiceWrapper.this.lambda$new$0(i, i2);
        }
    };
    private final InputManager mInputManager = (InputManager) AppGlobals.getInitialApplication().getSystemService("input");

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.samsung.android.desktopsystemui.sharedlib.system.InputServiceWrapper$H */
    public final class HandlerC4572H extends Handler {
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what != 1) {
                return;
            }
            int i = message.arg1;
            int i2 = message.arg2;
            Iterator it = InputServiceWrapper.this.mCallbacks.iterator();
            while (it.hasNext()) {
                DeXMultiFingerGesture deXMultiFingerGesture = (DeXMultiFingerGesture) it.next();
                SuggestionsAdapter$$ExternalSyntheticOutline0.m12m("handleMessage onMultiFingerGesture , behavior = ", i, ", reserved = ", i2, InputServiceWrapper.TAG);
                deXMultiFingerGesture.DeXMultiFingerGesture(i, i2);
            }
        }

        private HandlerC4572H(Looper looper) {
            super(looper);
        }
    }

    private InputServiceWrapper() {
    }

    private void addRegisterMultiFingerGestureCallback() {
        this.mInputManager.semRegisterOnMultiFingerGestureListener(this.mMultiFingerGestureListener, this.mHandler);
    }

    public static InputServiceWrapper getInstance() {
        return sInstance;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(int i, int i2) {
        SuggestionsAdapter$$ExternalSyntheticOutline0.m12m("onMultiFingerGesture , behavior = ", i, ", reserved = ", i2, TAG);
        synchronized (this.mLock) {
            this.mHandler.removeMessages(1);
            this.mHandler.obtainMessage(1, i, i2, null).sendToTarget();
        }
    }

    private void removeUnregisterMultiFingerGestureCallback() {
        this.mInputManager.semUnregisterOnMultiFingerGestureListener(this.mMultiFingerGestureListener);
    }

    public void addCallback(DeXMultiFingerGesture deXMultiFingerGesture) {
        this.mCallbacks.add(deXMultiFingerGesture);
        addRegisterMultiFingerGestureCallback();
    }

    public void clearCallback() {
        this.mCallbacks.clear();
        removeUnregisterMultiFingerGestureCallback();
    }

    public void removeCallback(DeXMultiFingerGesture deXMultiFingerGesture) {
        this.mCallbacks.remove(deXMultiFingerGesture);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface DeXMultiFingerGesture {
        default void DeXMultiFingerGesture(int i, int i2) {
        }
    }
}
