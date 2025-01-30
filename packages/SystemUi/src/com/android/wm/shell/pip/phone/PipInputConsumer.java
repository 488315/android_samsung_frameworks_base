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
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.protolog.ShellProtoLogCache;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.protolog.ShellProtoLogImpl;
import com.android.wm.shell.pip.phone.PipInputConsumer.InputEventReceiver;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PipInputConsumer {
    public InputEventReceiver mInputEventReceiver;
    public PipController$$ExternalSyntheticLambda9 mListener;
    public final ShellExecutor mMainExecutor;
    public final String mName;
    public PipController$$ExternalSyntheticLambda9 mRegistrationListener;
    public final IBinder mToken = new Binder();
    public final IWindowManager mWindowManager;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class InputEventReceiver extends BatchedInputEventReceiver {
        public InputEventReceiver(InputChannel inputChannel, Looper looper, Choreographer choreographer) {
            super(inputChannel, looper, choreographer);
        }

        public final void onInputEvent(InputEvent inputEvent) {
            try {
                PipController$$ExternalSyntheticLambda9 pipController$$ExternalSyntheticLambda9 = PipInputConsumer.this.mListener;
                if (pipController$$ExternalSyntheticLambda9 != null) {
                    pipController$$ExternalSyntheticLambda9.onInputEvent(inputEvent);
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
        String str = this.mName;
        IWindowManager iWindowManager = this.mWindowManager;
        if (this.mInputEventReceiver != null) {
            return;
        }
        final InputChannel inputChannel = new InputChannel();
        try {
            iWindowManager.destroyInputConsumer(str, 0);
            iWindowManager.createInputConsumer(this.mToken, str, 0, inputChannel);
        } catch (RemoteException e) {
            if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                ShellProtoLogImpl.m230e(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 842293805, 0, "%s: Failed to create input consumer, %s", "PipInputConsumer", String.valueOf(e));
            }
        }
        ((HandlerExecutor) this.mMainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.pip.phone.PipInputConsumer$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                PipInputConsumer pipInputConsumer = PipInputConsumer.this;
                InputChannel inputChannel2 = inputChannel;
                pipInputConsumer.getClass();
                pipInputConsumer.mInputEventReceiver = pipInputConsumer.new InputEventReceiver(inputChannel2, Looper.myLooper(), Choreographer.getInstance());
                PipController$$ExternalSyntheticLambda9 pipController$$ExternalSyntheticLambda9 = pipInputConsumer.mRegistrationListener;
                if (pipController$$ExternalSyntheticLambda9 != null) {
                    pipController$$ExternalSyntheticLambda9.f$0.onRegistrationChanged(true);
                }
            }
        });
    }
}
