package com.android.server.wm;

import android.view.InputChannel;
import android.view.InputEvent;
import android.view.InputEventReceiver;
import android.view.MotionEvent;
import android.view.WindowManagerPolicyConstants;

import com.android.server.UiThread;

import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class PointerEventDispatcher extends InputEventReceiver {
    public final ArrayList mListeners;
    public WindowManagerPolicyConstants.PointerEventListener[] mListenersArray;

    public PointerEventDispatcher(InputChannel inputChannel) {
        super(inputChannel, UiThread.getHandler().getLooper());
        this.mListeners = new ArrayList();
        this.mListenersArray = new WindowManagerPolicyConstants.PointerEventListener[0];
    }

    public final void dispose() {
        super.dispose();
        synchronized (this.mListeners) {
            this.mListeners.clear();
            this.mListenersArray = null;
        }
    }

    public final void onInputEvent(InputEvent inputEvent) {
        WindowManagerPolicyConstants.PointerEventListener[] pointerEventListenerArr;
        try {
            if ((inputEvent instanceof MotionEvent) && (inputEvent.getSource() & 2) != 0) {
                MotionEvent motionEvent = (MotionEvent) inputEvent;
                synchronized (this.mListeners) {
                    try {
                        if (this.mListenersArray == null) {
                            WindowManagerPolicyConstants.PointerEventListener[]
                                    pointerEventListenerArr2 =
                                            new WindowManagerPolicyConstants.PointerEventListener
                                                    [this.mListeners.size()];
                            this.mListenersArray = pointerEventListenerArr2;
                            this.mListeners.toArray(pointerEventListenerArr2);
                        }
                        pointerEventListenerArr = this.mListenersArray;
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                for (WindowManagerPolicyConstants.PointerEventListener pointerEventListener :
                        pointerEventListenerArr) {
                    pointerEventListener.onPointerEvent(motionEvent);
                }
            }
        } finally {
            finishInputEvent(inputEvent, false);
        }
    }
}
