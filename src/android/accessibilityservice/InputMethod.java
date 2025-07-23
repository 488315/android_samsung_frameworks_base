package android.accessibilityservice;

import android.os.RemoteException;
import android.os.Trace;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.SurroundingText;
import android.view.inputmethod.TextAttribute;
import com.android.internal.inputmethod.IAccessibilityInputMethodSessionCallback;
import com.android.internal.inputmethod.IRemoteAccessibilityInputConnection;
import com.android.internal.inputmethod.RemoteAccessibilityInputConnection;

/* loaded from: classes.dex */
public class InputMethod {
    private static final String LOG_TAG = "A11yInputMethod";
    private EditorInfo mInputEditorInfo;
    private boolean mInputStarted;
    private final AccessibilityService mService;
    private RemoteAccessibilityInputConnection mStartedInputConnection;

    public void onFinishInput() {
    }

    public void onStartInput(EditorInfo editorInfo, boolean z) {
    }

    public void onUpdateSelection(int i, int i2, int i3, int i4, int i5, int i6) {
    }

    public InputMethod(AccessibilityService accessibilityService) {
        this.mService = accessibilityService;
    }

    public final AccessibilityInputConnection getCurrentInputConnection() {
        if (this.mStartedInputConnection != null) {
            return new AccessibilityInputConnection(this, this.mStartedInputConnection);
        }
        return null;
    }

    public final boolean getCurrentInputStarted() {
        return this.mInputStarted;
    }

    public final EditorInfo getCurrentInputEditorInfo() {
        return this.mInputEditorInfo;
    }

    final void createImeSession(IAccessibilityInputMethodSessionCallback iAccessibilityInputMethodSessionCallback) {
        try {
            iAccessibilityInputMethodSessionCallback.sessionCreated(new AccessibilityInputMethodSessionWrapper(this.mService.getMainLooper(), new SessionImpl()), this.mService.getConnectionId());
        } catch (RemoteException unused) {
        }
    }

    final void startInput(RemoteAccessibilityInputConnection remoteAccessibilityInputConnection, EditorInfo editorInfo) {
        Log.v(LOG_TAG, "startInput(): editor=" + editorInfo);
        Trace.traceBegin(32L, "AccessibilityService.startInput");
        doStartInput(remoteAccessibilityInputConnection, editorInfo, false);
        Trace.traceEnd(32L);
    }

    final void restartInput(RemoteAccessibilityInputConnection remoteAccessibilityInputConnection, EditorInfo editorInfo) {
        Log.v(LOG_TAG, "restartInput(): editor=" + editorInfo);
        Trace.traceBegin(32L, "AccessibilityService.restartInput");
        doStartInput(remoteAccessibilityInputConnection, editorInfo, true);
        Trace.traceEnd(32L);
    }

    final void doStartInput(RemoteAccessibilityInputConnection remoteAccessibilityInputConnection, EditorInfo editorInfo, boolean z) {
        if ((remoteAccessibilityInputConnection == null || !z) && this.mInputStarted) {
            doFinishInput();
            if (remoteAccessibilityInputConnection == null) {
                return;
            }
        }
        this.mInputStarted = true;
        this.mStartedInputConnection = remoteAccessibilityInputConnection;
        this.mInputEditorInfo = editorInfo;
        Log.v(LOG_TAG, "CALL: onStartInput");
        onStartInput(editorInfo, z);
    }

    final void doFinishInput() {
        Log.v(LOG_TAG, "CALL: doFinishInput");
        if (this.mInputStarted) {
            Log.v(LOG_TAG, "CALL: onFinishInput");
            onFinishInput();
        }
        this.mInputStarted = false;
        this.mStartedInputConnection = null;
        this.mInputEditorInfo = null;
    }

    public final class AccessibilityInputConnection {
        private final RemoteAccessibilityInputConnection mIc;

        AccessibilityInputConnection(InputMethod inputMethod, RemoteAccessibilityInputConnection remoteAccessibilityInputConnection) {
            this.mIc = remoteAccessibilityInputConnection;
        }

        public void commitText(CharSequence charSequence, int i, TextAttribute textAttribute) {
            RemoteAccessibilityInputConnection remoteAccessibilityInputConnection = this.mIc;
            if (remoteAccessibilityInputConnection != null) {
                remoteAccessibilityInputConnection.commitText(charSequence, i, textAttribute);
            }
        }

        public void setSelection(int i, int i2) {
            RemoteAccessibilityInputConnection remoteAccessibilityInputConnection = this.mIc;
            if (remoteAccessibilityInputConnection != null) {
                remoteAccessibilityInputConnection.setSelection(i, i2);
            }
        }

        public SurroundingText getSurroundingText(int i, int i2, int i3) {
            RemoteAccessibilityInputConnection remoteAccessibilityInputConnection = this.mIc;
            if (remoteAccessibilityInputConnection != null) {
                return remoteAccessibilityInputConnection.getSurroundingText(i, i2, i3);
            }
            return null;
        }

        public void deleteSurroundingText(int i, int i2) {
            RemoteAccessibilityInputConnection remoteAccessibilityInputConnection = this.mIc;
            if (remoteAccessibilityInputConnection != null) {
                remoteAccessibilityInputConnection.deleteSurroundingText(i, i2);
            }
        }

        public void sendKeyEvent(KeyEvent keyEvent) {
            RemoteAccessibilityInputConnection remoteAccessibilityInputConnection = this.mIc;
            if (remoteAccessibilityInputConnection != null) {
                remoteAccessibilityInputConnection.sendKeyEvent(keyEvent);
            }
        }

        public void performEditorAction(int i) {
            RemoteAccessibilityInputConnection remoteAccessibilityInputConnection = this.mIc;
            if (remoteAccessibilityInputConnection != null) {
                remoteAccessibilityInputConnection.performEditorAction(i);
            }
        }

        public void performContextMenuAction(int i) {
            RemoteAccessibilityInputConnection remoteAccessibilityInputConnection = this.mIc;
            if (remoteAccessibilityInputConnection != null) {
                remoteAccessibilityInputConnection.performContextMenuAction(i);
            }
        }

        public int getCursorCapsMode(int i) {
            RemoteAccessibilityInputConnection remoteAccessibilityInputConnection = this.mIc;
            if (remoteAccessibilityInputConnection != null) {
                return remoteAccessibilityInputConnection.getCursorCapsMode(i);
            }
            return 0;
        }

        public void clearMetaKeyStates(int i) {
            RemoteAccessibilityInputConnection remoteAccessibilityInputConnection = this.mIc;
            if (remoteAccessibilityInputConnection != null) {
                remoteAccessibilityInputConnection.clearMetaKeyStates(i);
            }
        }
    }

    private final class SessionImpl implements AccessibilityInputMethodSession {
        boolean mEnabled;

        private SessionImpl() {
            this.mEnabled = true;
        }

        @Override // android.accessibilityservice.AccessibilityInputMethodSession
        public void setEnabled(boolean z) {
            this.mEnabled = z;
        }

        @Override // android.accessibilityservice.AccessibilityInputMethodSession
        public void finishInput() {
            if (this.mEnabled) {
                InputMethod.this.doFinishInput();
            }
        }

        @Override // android.accessibilityservice.AccessibilityInputMethodSession
        public void updateSelection(int i, int i2, int i3, int i4, int i5, int i6) {
            if (this.mEnabled) {
                InputMethod.this.onUpdateSelection(i2, i2, i3, i4, i5, i6);
            }
        }

        @Override // android.accessibilityservice.AccessibilityInputMethodSession
        public void invalidateInput(EditorInfo editorInfo, IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, int i) {
            if (this.mEnabled && InputMethod.this.mStartedInputConnection != null && InputMethod.this.mStartedInputConnection.isSameConnection(iRemoteAccessibilityInputConnection)) {
                editorInfo.makeCompatible(InputMethod.this.mService.getApplicationInfo().targetSdkVersion);
                InputMethod.this.restartInput(new RemoteAccessibilityInputConnection(InputMethod.this.mStartedInputConnection, i), editorInfo);
            }
        }
    }
}
