package android.inputmethodservice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.WindowManagerGlobal;
import android.view.inputmethod.Flags;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodSession;
import android.window.WindowProviderService;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* loaded from: classes2.dex */
public abstract class AbstractInputMethodService extends WindowProviderService implements KeyEvent.Callback {
    final KeyEvent.DispatcherState mDispatcherState = new KeyEvent.DispatcherState();
    private InputMethod mInputMethod;
    private InputMethodServiceInternal mInputMethodServiceInternal;
    protected boolean mIsPressBtnSIPOnOff;

    @Override // android.app.Service
    protected void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    }

    @Override // android.window.WindowProviderService, android.window.WindowProvider
    public final int getWindowType() {
        return 2011;
    }

    public abstract AbstractInputMethodImpl onCreateInputMethodInterface();

    public abstract AbstractInputMethodSessionImpl onCreateInputMethodSessionInterface();

    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        return false;
    }

    public boolean onShouldVerifyKeyEvent(KeyEvent keyEvent) {
        return false;
    }

    public boolean onTrackballEvent(MotionEvent motionEvent) {
        return false;
    }

    public final InputMethod getInputMethodInternal() {
        return this.mInputMethod;
    }

    public abstract class AbstractInputMethodImpl implements InputMethod {
        public AbstractInputMethodImpl() {
        }

        @Override // android.view.inputmethod.InputMethod
        public void createSession(InputMethod.SessionCallback sessionCallback) {
            sessionCallback.sessionCreated(AbstractInputMethodService.this.onCreateInputMethodSessionInterface());
        }

        @Override // android.view.inputmethod.InputMethod
        public void setSessionEnabled(InputMethodSession inputMethodSession, boolean z) {
            ((AbstractInputMethodSessionImpl) inputMethodSession).setEnabled(z);
        }

        @Override // android.view.inputmethod.InputMethod
        public void revokeSession(InputMethodSession inputMethodSession) {
            ((AbstractInputMethodSessionImpl) inputMethodSession).revokeSelf();
        }
    }

    public abstract class AbstractInputMethodSessionImpl implements InputMethodSession {
        boolean mEnabled = true;
        boolean mRevoked;

        public AbstractInputMethodSessionImpl() {
        }

        public boolean isEnabled() {
            return this.mEnabled;
        }

        public boolean isRevoked() {
            return this.mRevoked;
        }

        public void setEnabled(boolean z) {
            if (this.mRevoked) {
                return;
            }
            this.mEnabled = z;
        }

        public void revokeSelf() {
            this.mRevoked = true;
            this.mEnabled = false;
        }

        @Override // android.view.inputmethod.InputMethodSession
        public void dispatchKeyEvent(int i, KeyEvent keyEvent, InputMethodSession.EventCallback eventCallback) {
            AbstractInputMethodService abstractInputMethodService = AbstractInputMethodService.this;
            boolean zDispatch = keyEvent.dispatch(abstractInputMethodService, abstractInputMethodService.mDispatcherState, this);
            if (keyEvent.getKeyCode() == 1006 && keyEvent.getAction() == 0) {
                AbstractInputMethodService.this.mIsPressBtnSIPOnOff = true;
            }
            if (eventCallback != null) {
                eventCallback.finishedEvent(i, zDispatch);
            }
            if (!Flags.imeSwitcherRevamp() || zDispatch || keyEvent.getAction() != 0 || keyEvent.getUnicodeChar() <= 0 || AbstractInputMethodService.this.mInputMethodServiceInternal == null) {
                return;
            }
            AbstractInputMethodService.this.mInputMethodServiceInternal.notifyUserActionIfNecessary();
        }

        @Override // android.view.inputmethod.InputMethodSession
        public boolean onShouldVerifyKeyEvent(KeyEvent keyEvent) {
            return AbstractInputMethodService.this.onShouldVerifyKeyEvent(keyEvent);
        }

        @Override // android.view.inputmethod.InputMethodSession
        public void dispatchTrackballEvent(int i, MotionEvent motionEvent, InputMethodSession.EventCallback eventCallback) {
            boolean zOnTrackballEvent = AbstractInputMethodService.this.onTrackballEvent(motionEvent);
            if (eventCallback != null) {
                eventCallback.finishedEvent(i, zOnTrackballEvent);
            }
        }

        @Override // android.view.inputmethod.InputMethodSession
        public void dispatchGenericMotionEvent(int i, MotionEvent motionEvent, InputMethodSession.EventCallback eventCallback) {
            boolean zOnGenericMotionEvent = AbstractInputMethodService.this.onGenericMotionEvent(motionEvent);
            if (eventCallback != null) {
                eventCallback.finishedEvent(i, zOnGenericMotionEvent);
            }
        }
    }

    public KeyEvent.DispatcherState getKeyDispatcherState() {
        return this.mDispatcherState;
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        if (this.mInputMethod == null) {
            this.mInputMethod = onCreateInputMethodInterface();
        }
        if (this.mInputMethodServiceInternal == null) {
            this.mInputMethodServiceInternal = createInputMethodServiceInternal();
        }
        return new IInputMethodWrapper(this.mInputMethodServiceInternal, this.mInputMethod);
    }

    InputMethodServiceInternal createInputMethodServiceInternal() {
        return new InputMethodServiceInternal() { // from class: android.inputmethodservice.AbstractInputMethodService.1
            @Override // android.inputmethodservice.InputMethodServiceInternal
            public Context getContext() {
                return AbstractInputMethodService.this;
            }

            @Override // android.inputmethodservice.InputMethodServiceInternal
            public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
                AbstractInputMethodService.this.dump(fileDescriptor, printWriter, strArr);
            }
        };
    }

    @Override // android.window.WindowProviderService, android.window.WindowProvider
    public final Bundle getWindowContextOptions() {
        return super.getWindowContextOptions();
    }

    @Override // android.window.WindowProviderService
    public final int getInitialDisplayId() {
        try {
            int imeDisplayId = WindowManagerGlobal.getWindowManagerService().getImeDisplayId();
            Log.i("InputMethodService", "getInitialDisplayId: imeDisplayId=" + imeDisplayId);
            return imeDisplayId;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
