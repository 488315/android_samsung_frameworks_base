package com.samsung.android.desktopsystemui.sharedlib.system;

import android.os.Binder;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import android.view.BatchedInputEventReceiver;
import android.view.Choreographer;
import android.view.IWindowManager;
import android.view.InputChannel;
import android.view.InputEvent;
import android.view.WindowManagerGlobal;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.ArrayLinkedVariables$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardClockSwitchController$$ExternalSyntheticOutline0;
import java.io.PrintWriter;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class InputConsumerController {
    private static final String TAG = "[DS]InputConsumerController";
    private InputEventReceiver mInputEventReceiver;
    private InputListener mListener;
    private final String mName;
    private RegistrationListener mRegistrationListener;
    private final IBinder mToken = new Binder();
    private final IWindowManager mWindowManager;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class InputEventReceiver extends BatchedInputEventReceiver {
        public InputEventReceiver(InputChannel inputChannel, Looper looper, Choreographer choreographer) {
            super(inputChannel, looper, choreographer);
        }

        public void onInputEvent(InputEvent inputEvent) {
            try {
                boolean onInputEvent = InputConsumerController.this.mListener != null ? InputConsumerController.this.mListener.onInputEvent(inputEvent) : true;
            } finally {
                finishInputEvent(inputEvent, true);
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface InputListener {
        boolean onInputEvent(InputEvent inputEvent);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface RegistrationListener {
        void onRegistrationChanged(boolean z);
    }

    public InputConsumerController(IWindowManager iWindowManager, String str) {
        this.mWindowManager = iWindowManager;
        this.mName = str;
    }

    public static InputConsumerController getPipInputConsumer() {
        return new InputConsumerController(WindowManagerGlobal.getWindowManagerService(), "pip_input_consumer");
    }

    public static InputConsumerController getRecentsAnimationInputConsumer() {
        return new InputConsumerController(WindowManagerGlobal.getWindowManagerService(), "recents_animation_input_consumer");
    }

    public void dump(PrintWriter printWriter, String str) {
        String m14m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(str, "  ");
        StringBuilder m18m = ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(str);
        m18m.append(TAG);
        printWriter.println(m18m.toString());
        StringBuilder sb = new StringBuilder();
        sb.append(m14m);
        sb.append("registered=");
        KeyguardClockSwitchController$$ExternalSyntheticOutline0.m65m(sb, this.mInputEventReceiver != null, printWriter);
    }

    public boolean isRegistered() {
        return this.mInputEventReceiver != null;
    }

    public void registerInputConsumer() {
        registerInputConsumer(false);
    }

    public void setInputListener(InputListener inputListener) {
        this.mListener = inputListener;
    }

    public void setRegistrationListener(RegistrationListener registrationListener) {
        this.mRegistrationListener = registrationListener;
        if (registrationListener != null) {
            registrationListener.onRegistrationChanged(this.mInputEventReceiver != null);
        }
    }

    public void unregisterInputConsumer() {
        if (this.mInputEventReceiver != null) {
            try {
                this.mWindowManager.destroyInputConsumer(this.mName, 0);
            } catch (RemoteException e) {
                Log.e(TAG, "Failed to destroy input consumer", e);
            }
            this.mInputEventReceiver.dispose();
            this.mInputEventReceiver = null;
            RegistrationListener registrationListener = this.mRegistrationListener;
            if (registrationListener != null) {
                registrationListener.onRegistrationChanged(false);
            }
        }
    }

    public void registerInputConsumer(boolean z) {
        if (this.mInputEventReceiver == null) {
            InputChannel inputChannel = new InputChannel();
            try {
                this.mWindowManager.destroyInputConsumer(this.mName, 0);
                this.mWindowManager.createInputConsumer(this.mToken, this.mName, 0, inputChannel);
            } catch (RemoteException e) {
                Log.e(TAG, "Failed to create input consumer", e);
            } catch (IllegalStateException e2) {
                Log.e(TAG, "registerInputConsumer: IllegalStateException : ", e2);
                try {
                    this.mWindowManager.destroyInputConsumer(this.mName, 0);
                    this.mWindowManager.createInputConsumer(this.mToken, this.mName, 0, inputChannel);
                } catch (RemoteException e3) {
                    Log.e(TAG, "Failed to create input consumer in 2nd attempt ", e3);
                } catch (IllegalStateException e4) {
                    Log.e(TAG, "registerInputConsumer: IllegalStateException in 2nd attempt : ", e4);
                }
            }
            this.mInputEventReceiver = new InputEventReceiver(inputChannel, Looper.myLooper(), z ? Choreographer.getSfInstance() : Choreographer.getInstance());
            RegistrationListener registrationListener = this.mRegistrationListener;
            if (registrationListener != null) {
                registrationListener.onRegistrationChanged(true);
            }
        }
    }
}
