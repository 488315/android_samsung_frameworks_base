package android.inputmethodservice;

import android.content.Context;
import android.graphics.Rect;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.util.SparseArray;
import android.view.InputChannel;
import android.view.InputEvent;
import android.view.InputEventReceiver;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.CursorAnchorInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.Flags;
import android.view.inputmethod.InputMethodSession;
import com.android.internal.inputmethod.IInputMethodSession;
import com.android.internal.inputmethod.IRemoteInputConnection;
import com.android.internal.os.HandlerCaller;
import com.android.internal.os.SomeArgs;
import java.util.Objects;

/* loaded from: classes2.dex */
class IInputMethodSessionWrapper extends IInputMethodSession.Stub implements HandlerCaller.Callback {
    private static final int DO_APP_PRIVATE_COMMAND = 100;
    private static final int DO_DISPLAY_COMPLETIONS = 65;
    private static final int DO_FINISH_INPUT = 140;
    private static final int DO_FINISH_SESSION = 110;
    private static final int DO_INVALIDATE_INPUT = 150;
    private static final int DO_REMOVE_IME_SURFACE = 130;
    private static final int DO_UPDATE_CURSOR = 95;
    private static final int DO_UPDATE_CURSOR_ANCHOR_INFO = 99;
    private static final int DO_UPDATE_EXTRACTED_TEXT = 67;
    private static final int DO_UPDATE_SELECTION = 90;
    private static final int DO_VIEW_CLICKED = 115;
    private static final String TAG = "InputMethodWrapper";
    HandlerCaller mCaller;
    InputChannel mChannel;
    private final Context mContext;
    InputMethodSession mInputMethodSession;
    ImeInputEventReceiver mReceiver;

    public IInputMethodSessionWrapper(Context context, InputMethodSession inputMethodSession, InputChannel inputChannel) {
        this.mContext = context;
        this.mCaller = new HandlerCaller(context, null, this, true);
        this.mInputMethodSession = inputMethodSession;
        this.mChannel = inputChannel;
        if (inputChannel != null) {
            this.mReceiver = new ImeInputEventReceiver(inputChannel, context.getMainLooper());
        }
    }

    public InputMethodSession getInternalInputMethodSession() {
        return this.mInputMethodSession;
    }

    @Override // com.android.internal.os.HandlerCaller.Callback
    public void executeMessage(Message message) {
        SomeArgs someArgs;
        if (this.mInputMethodSession == null) {
            int i = message.what;
            if (i == 90 || i == 100) {
                someArgs = (SomeArgs) message.obj;
                return;
            }
            return;
        }
        int i2 = message.what;
        if (i2 == 65) {
            this.mInputMethodSession.displayCompletions((CompletionInfo[]) message.obj);
            return;
        }
        if (i2 == 67) {
            this.mInputMethodSession.updateExtractedText(message.arg1, (ExtractedText) message.obj);
            return;
        }
        if (i2 == 90) {
            someArgs = (SomeArgs) message.obj;
            this.mInputMethodSession.updateSelection(someArgs.argi1, someArgs.argi2, someArgs.argi3, someArgs.argi4, someArgs.argi5, someArgs.argi6);
            return;
        }
        if (i2 == 95) {
            this.mInputMethodSession.updateCursor((Rect) message.obj);
            return;
        }
        if (i2 == 110) {
            doFinishSession();
            return;
        }
        if (i2 == 115) {
            this.mInputMethodSession.viewClicked(message.arg1 == 1);
            return;
        }
        if (i2 == 130) {
            this.mInputMethodSession.removeImeSurface();
            return;
        }
        if (i2 == 140) {
            this.mInputMethodSession.finishInput();
            return;
        }
        if (i2 == 150) {
            someArgs = (SomeArgs) message.obj;
            try {
                this.mInputMethodSession.invalidateInputInternal((EditorInfo) someArgs.arg1, (IRemoteInputConnection) someArgs.arg2, message.arg1);
                return;
            } finally {
                someArgs.recycle();
            }
        }
        if (i2 == 99) {
            this.mInputMethodSession.updateCursorAnchorInfo((CursorAnchorInfo) message.obj);
            return;
        }
        if (i2 == 100) {
            someArgs = (SomeArgs) message.obj;
            this.mInputMethodSession.appPrivateCommand((String) someArgs.arg1, (Bundle) someArgs.arg2);
        } else {
            Log.w(TAG, "Unhandled message code: " + message.what);
        }
    }

    private void doFinishSession() {
        this.mInputMethodSession = null;
        ImeInputEventReceiver imeInputEventReceiver = this.mReceiver;
        if (imeInputEventReceiver != null) {
            imeInputEventReceiver.dispose();
            this.mReceiver = null;
        }
        InputChannel inputChannel = this.mChannel;
        if (inputChannel != null) {
            inputChannel.dispose();
            this.mChannel = null;
        }
    }

    @Override // com.android.internal.inputmethod.IInputMethodSession
    public void displayCompletions(CompletionInfo[] completionInfoArr) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(65, completionInfoArr));
    }

    @Override // com.android.internal.inputmethod.IInputMethodSession
    public void updateExtractedText(int i, ExtractedText extractedText) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageIO(67, i, extractedText));
    }

    @Override // com.android.internal.inputmethod.IInputMethodSession
    public void updateSelection(int i, int i2, int i3, int i4, int i5, int i6) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageIIIIII(90, i, i2, i3, i4, i5, i6));
    }

    @Override // com.android.internal.inputmethod.IInputMethodSession
    public void viewClicked(boolean z) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageI(115, z ? 1 : 0));
    }

    @Override // com.android.internal.inputmethod.IInputMethodSession
    public void removeImeSurface() {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessage(130));
    }

    @Override // com.android.internal.inputmethod.IInputMethodSession
    public void updateCursor(Rect rect) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(95, rect));
    }

    @Override // com.android.internal.inputmethod.IInputMethodSession
    public void updateCursorAnchorInfo(CursorAnchorInfo cursorAnchorInfo) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(99, cursorAnchorInfo));
    }

    @Override // com.android.internal.inputmethod.IInputMethodSession
    public void appPrivateCommand(String str, Bundle bundle) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageOO(100, str, bundle));
    }

    @Override // com.android.internal.inputmethod.IInputMethodSession
    public void finishSession() {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessage(110));
    }

    @Override // com.android.internal.inputmethod.IInputMethodSession
    public void invalidateInput(EditorInfo editorInfo, IRemoteInputConnection iRemoteInputConnection, int i) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageIOO(150, i, editorInfo, iRemoteInputConnection));
    }

    @Override // com.android.internal.inputmethod.IInputMethodSession
    public void finishInput() {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessage(140));
    }

    private final class ImeInputEventReceiver extends InputEventReceiver implements InputMethodSession.EventCallback {
        private static final long KEY_EVENT_ALLOW_PERIOD_MS = 100;
        private final SparseArray<InputEvent> mPendingEvents;

        public ImeInputEventReceiver(InputChannel inputChannel, Looper looper) {
            super(inputChannel, looper);
            this.mPendingEvents = new SparseArray<>();
        }

        @Override // android.view.InputEventReceiver
        public void onInputEvent(InputEvent inputEvent) {
            if (IInputMethodSessionWrapper.this.mInputMethodSession == null) {
                finishInputEvent(inputEvent, false);
                return;
            }
            boolean z = inputEvent instanceof KeyEvent;
            if (z) {
                KeyEvent keyEvent = (KeyEvent) inputEvent;
                if (needsVerification(keyEvent)) {
                    InputManager inputManager = (InputManager) IInputMethodSessionWrapper.this.mContext.getSystemService(InputManager.class);
                    Objects.requireNonNull(inputManager);
                    if (SystemClock.uptimeMillis() - keyEvent.getEventTime() >= KEY_EVENT_ALLOW_PERIOD_MS && inputManager.verifyInputEvent(keyEvent) == null) {
                        Log.w(IInputMethodSessionWrapper.TAG, "Unverified or Invalid KeyEvent injected into IME. Dropping " + keyEvent);
                        finishInputEvent(inputEvent, false);
                        return;
                    }
                }
            }
            int sequenceNumber = inputEvent.getSequenceNumber();
            this.mPendingEvents.put(sequenceNumber, inputEvent);
            if (z) {
                IInputMethodSessionWrapper.this.mInputMethodSession.dispatchKeyEvent(sequenceNumber, (KeyEvent) inputEvent, this);
                return;
            }
            MotionEvent motionEvent = (MotionEvent) inputEvent;
            if (motionEvent.isFromSource(4)) {
                IInputMethodSessionWrapper.this.mInputMethodSession.dispatchTrackballEvent(sequenceNumber, motionEvent, this);
            } else {
                IInputMethodSessionWrapper.this.mInputMethodSession.dispatchGenericMotionEvent(sequenceNumber, motionEvent, this);
            }
        }

        @Override // android.view.inputmethod.InputMethodSession.EventCallback
        public void finishedEvent(int i, boolean z) {
            int iIndexOfKey = this.mPendingEvents.indexOfKey(i);
            if (iIndexOfKey >= 0) {
                InputEvent inputEventValueAt = this.mPendingEvents.valueAt(iIndexOfKey);
                this.mPendingEvents.removeAt(iIndexOfKey);
                finishInputEvent(inputEventValueAt, z);
            }
        }

        private boolean hasKeyModifiers(KeyEvent keyEvent) {
            if (keyEvent.hasNoModifiers()) {
                return false;
            }
            return keyEvent.isCtrlPressed() || keyEvent.isAltPressed() || keyEvent.isFunctionPressed() || keyEvent.isMetaPressed();
        }

        private boolean needsVerification(KeyEvent keyEvent) {
            if (Flags.verifyKeyEvent()) {
                return hasKeyModifiers(keyEvent) || IInputMethodSessionWrapper.this.mInputMethodSession.onShouldVerifyKeyEvent(keyEvent);
            }
            return false;
        }
    }
}
