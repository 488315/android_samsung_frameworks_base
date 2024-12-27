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

    public InputMethod(AccessibilityService service) {
        this.mService = service;
    }

    public final AccessibilityInputConnection getCurrentInputConnection() {
        if (this.mStartedInputConnection != null) {
            return new AccessibilityInputConnection(this.mStartedInputConnection);
        }
        return null;
    }

    public final boolean getCurrentInputStarted() {
        return this.mInputStarted;
    }

    public final EditorInfo getCurrentInputEditorInfo() {
        return this.mInputEditorInfo;
    }

    public void onStartInput(EditorInfo attribute, boolean restarting) {}

    public void onFinishInput() {}

    public void onUpdateSelection(
            int oldSelStart,
            int oldSelEnd,
            int newSelStart,
            int newSelEnd,
            int candidatesStart,
            int candidatesEnd) {}

    final void createImeSession(IAccessibilityInputMethodSessionCallback callback) {
        AccessibilityInputMethodSessionWrapper wrapper =
                new AccessibilityInputMethodSessionWrapper(
                        this.mService.getMainLooper(), new SessionImpl());
        try {
            callback.sessionCreated(wrapper, this.mService.getConnectionId());
        } catch (RemoteException e) {
        }
    }

    final void startInput(RemoteAccessibilityInputConnection ic, EditorInfo attribute) {
        Log.v(LOG_TAG, "startInput(): editor=" + attribute);
        Trace.traceBegin(32L, "AccessibilityService.startInput");
        doStartInput(ic, attribute, false);
        Trace.traceEnd(32L);
    }

    final void restartInput(RemoteAccessibilityInputConnection ic, EditorInfo attribute) {
        Log.v(LOG_TAG, "restartInput(): editor=" + attribute);
        Trace.traceBegin(32L, "AccessibilityService.restartInput");
        doStartInput(ic, attribute, true);
        Trace.traceEnd(32L);
    }

    final void doStartInput(
            RemoteAccessibilityInputConnection ic, EditorInfo attribute, boolean restarting) {
        if ((ic == null || !restarting) && this.mInputStarted) {
            doFinishInput();
            if (ic == null) {
                return;
            }
        }
        this.mInputStarted = true;
        this.mStartedInputConnection = ic;
        this.mInputEditorInfo = attribute;
        Log.v(LOG_TAG, "CALL: onStartInput");
        onStartInput(attribute, restarting);
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

        AccessibilityInputConnection(RemoteAccessibilityInputConnection ic) {
            this.mIc = ic;
        }

        public void commitText(
                CharSequence text, int newCursorPosition, TextAttribute textAttribute) {
            if (this.mIc != null) {
                this.mIc.commitText(text, newCursorPosition, textAttribute);
            }
        }

        public void setSelection(int start, int end) {
            if (this.mIc != null) {
                this.mIc.setSelection(start, end);
            }
        }

        public SurroundingText getSurroundingText(int beforeLength, int afterLength, int flags) {
            if (this.mIc != null) {
                return this.mIc.getSurroundingText(beforeLength, afterLength, flags);
            }
            return null;
        }

        public void deleteSurroundingText(int beforeLength, int afterLength) {
            if (this.mIc != null) {
                this.mIc.deleteSurroundingText(beforeLength, afterLength);
            }
        }

        public void sendKeyEvent(KeyEvent event) {
            if (this.mIc != null) {
                this.mIc.sendKeyEvent(event);
            }
        }

        public void performEditorAction(int editorAction) {
            if (this.mIc != null) {
                this.mIc.performEditorAction(editorAction);
            }
        }

        public void performContextMenuAction(int id) {
            if (this.mIc != null) {
                this.mIc.performContextMenuAction(id);
            }
        }

        public int getCursorCapsMode(int reqModes) {
            if (this.mIc != null) {
                return this.mIc.getCursorCapsMode(reqModes);
            }
            return 0;
        }

        public void clearMetaKeyStates(int states) {
            if (this.mIc != null) {
                this.mIc.clearMetaKeyStates(states);
            }
        }
    }

    private final class SessionImpl implements AccessibilityInputMethodSession {
        boolean mEnabled;

        private SessionImpl() {
            this.mEnabled = true;
        }

        @Override // android.accessibilityservice.AccessibilityInputMethodSession
        public void setEnabled(boolean enabled) {
            this.mEnabled = enabled;
        }

        @Override // android.accessibilityservice.AccessibilityInputMethodSession
        public void finishInput() {
            if (this.mEnabled) {
                InputMethod.this.doFinishInput();
            }
        }

        @Override // android.accessibilityservice.AccessibilityInputMethodSession
        public void updateSelection(
                int oldSelStart,
                int oldSelEnd,
                int newSelStart,
                int newSelEnd,
                int candidatesStart,
                int candidatesEnd) {
            if (this.mEnabled) {
                InputMethod.this.onUpdateSelection(
                        oldSelEnd,
                        oldSelEnd,
                        newSelStart,
                        newSelEnd,
                        candidatesStart,
                        candidatesEnd);
            }
        }

        @Override // android.accessibilityservice.AccessibilityInputMethodSession
        public void invalidateInput(
                EditorInfo editorInfo,
                IRemoteAccessibilityInputConnection connection,
                int sessionId) {
            if (!this.mEnabled
                    || InputMethod.this.mStartedInputConnection == null
                    || !InputMethod.this.mStartedInputConnection.isSameConnection(connection)) {
                return;
            }
            editorInfo.makeCompatible(
                    InputMethod.this.mService.getApplicationInfo().targetSdkVersion);
            InputMethod.this.restartInput(
                    new RemoteAccessibilityInputConnection(
                            InputMethod.this.mStartedInputConnection, sessionId),
                    editorInfo);
        }
    }
}
