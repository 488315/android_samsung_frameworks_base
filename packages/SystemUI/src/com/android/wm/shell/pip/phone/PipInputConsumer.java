package com.android.wm.shell.pip.phone;

import android.os.Binder;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.view.BatchedInputEventReceiver;
import android.view.Choreographer;
import android.view.IWindowManager;
import android.view.InputChannel;
import android.view.InputEvent;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.pip.phone.PipInputConsumer.InputEventReceiver;
import com.android.wm.shell.protolog.ShellProtoLogGroup;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class PipInputConsumer {
    public InputEventReceiver mInputEventReceiver;
    public PipController$$ExternalSyntheticLambda14 mListener;
    public final ShellExecutor mMainExecutor;
    public final String mName;
    public PipController$$ExternalSyntheticLambda14 mRegistrationListener;
    public final IBinder mToken = new Binder();
    public final IWindowManager mWindowManager;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class InputEventReceiver extends BatchedInputEventReceiver {
        public InputEventReceiver(InputChannel inputChannel, Looper looper, Choreographer choreographer) {
            super(inputChannel, looper, choreographer);
        }

        public final void onInputEvent(InputEvent inputEvent) {
            try {
                PipController$$ExternalSyntheticLambda14 pipController$$ExternalSyntheticLambda14 = PipInputConsumer.this.mListener;
                if (pipController$$ExternalSyntheticLambda14 != null) {
                    pipController$$ExternalSyntheticLambda14.onInputEvent(inputEvent);
                }
            } finally {
                finishInputEvent(inputEvent, true);
            }
        }
    }

    public PipInputConsumer(IWindowManager iWindowManager, String str, ShellExecutor shellExecutor) {
        this.mWindowManager = iWindowManager;
        this.mName = str;
        this.mMainExecutor = shellExecutor;
    }

    public final void registerInputConsumer() {
        if (this.mInputEventReceiver != null) {
            return;
        }
        final InputChannel inputChannel = new InputChannel();
        try {
            this.mWindowManager.destroyInputConsumer(this.mToken, 0);
            this.mWindowManager.createInputConsumer(this.mToken, this.mName, 0, inputChannel);
        } catch (RemoteException e) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[4]) {
                ProtoLogImpl_1771455215.e(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -678909581970427432L, 0, "PipInputConsumer", String.valueOf(e));
            }
        }
        this.mMainExecutor.execute(new Runnable() { // from class: com.android.wm.shell.pip.phone.PipInputConsumer$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                PipInputConsumer pipInputConsumer = PipInputConsumer.this;
                InputChannel inputChannel2 = inputChannel;
                pipInputConsumer.getClass();
                pipInputConsumer.mInputEventReceiver = pipInputConsumer.new InputEventReceiver(inputChannel2, Looper.myLooper(), Choreographer.getInstance());
                PipController$$ExternalSyntheticLambda14 pipController$$ExternalSyntheticLambda14 = pipInputConsumer.mRegistrationListener;
                if (pipController$$ExternalSyntheticLambda14 != null) {
                    pipController$$ExternalSyntheticLambda14.f$0.onRegistrationChanged(true);
                }
            }
        });
    }
}
