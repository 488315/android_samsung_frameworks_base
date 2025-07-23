package com.android.wm.shell.pip2.phone;

import android.os.Binder;
import android.os.IBinder;
import android.os.Looper;
import android.view.BatchedInputEventReceiver;
import android.view.Choreographer;
import android.view.IWindowManager;
import android.view.InputChannel;
import android.view.InputEvent;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.pip.PipDisplayLayoutState;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class PipInputConsumer {
    public InputEventReceiver mInputEventReceiver;
    public PipTouchHandler$$ExternalSyntheticLambda10 mListener;
    public final ShellExecutor mMainExecutor;
    public final String mName;
    public final PipDisplayLayoutState mPipDisplayLayoutState;
    public PipTouchHandler$$ExternalSyntheticLambda10 mRegistrationListener;
    public final IBinder mToken = new Binder();
    public final IWindowManager mWindowManager;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class InputEventReceiver extends BatchedInputEventReceiver {
        public InputEventReceiver(InputChannel inputChannel, Looper looper, Choreographer choreographer) {
            super(inputChannel, looper, choreographer);
        }

        public final void onInputEvent(InputEvent inputEvent) {
            try {
                PipTouchHandler$$ExternalSyntheticLambda10 pipTouchHandler$$ExternalSyntheticLambda10 = PipInputConsumer.this.mListener;
                if (pipTouchHandler$$ExternalSyntheticLambda10 != null) {
                    pipTouchHandler$$ExternalSyntheticLambda10.onInputEvent(inputEvent);
                }
            } finally {
                finishInputEvent(inputEvent, true);
            }
        }
    }

    public PipInputConsumer(IWindowManager iWindowManager, String str, PipDisplayLayoutState pipDisplayLayoutState, ShellExecutor shellExecutor) {
        this.mWindowManager = iWindowManager;
        this.mName = str;
        this.mPipDisplayLayoutState = pipDisplayLayoutState;
        this.mMainExecutor = shellExecutor;
    }
}
