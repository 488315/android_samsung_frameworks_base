package android.view.inputmethod;

import android.graphics.Rect;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.RemoteException;
import android.util.Log;
import com.android.internal.inputmethod.IInputMethodSession;
import com.android.internal.inputmethod.IRemoteInputConnection;

/* loaded from: classes4.dex */
final class IInputMethodSessionInvoker {
    private static final String TAG = "InputMethodSessionWrapper";
    private static Handler sAsyncBinderEmulationHandler;
    private static final Object sAsyncBinderEmulationHandlerLock = new Object();
    private final Handler mCustomHandler;
    private final IInputMethodSession mSession;

    private IInputMethodSessionInvoker(IInputMethodSession iInputMethodSession, Handler handler) {
        this.mSession = iInputMethodSession;
        this.mCustomHandler = handler;
    }

    public static IInputMethodSessionInvoker createOrNull(IInputMethodSession iInputMethodSession) {
        Handler handler;
        if (iInputMethodSession == null || Binder.isProxy(iInputMethodSession)) {
            handler = null;
        } else {
            synchronized (sAsyncBinderEmulationHandlerLock) {
                if (sAsyncBinderEmulationHandler == null) {
                    HandlerThread handlerThread = new HandlerThread("IMM.binder-emu");
                    handlerThread.start();
                    sAsyncBinderEmulationHandler = Handler.createAsync(handlerThread.getLooper());
                }
                handler = sAsyncBinderEmulationHandler;
            }
        }
        if (iInputMethodSession != null) {
            return new IInputMethodSessionInvoker(iInputMethodSession, handler);
        }
        return null;
    }

    void finishInput() {
        Handler handler = this.mCustomHandler;
        if (handler == null) {
            finishInputInternal();
        } else {
            handler.post(new Runnable() { // from class: android.view.inputmethod.IInputMethodSessionInvoker$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    IInputMethodSessionInvoker.this.finishInputInternal();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finishInputInternal() {
        try {
            this.mSession.finishInput();
        } catch (RemoteException e) {
            Log.w(TAG, "IME died", e);
        }
    }

    void updateCursorAnchorInfo(final CursorAnchorInfo cursorAnchorInfo) {
        Handler handler = this.mCustomHandler;
        if (handler == null) {
            lambda$updateCursorAnchorInfo$0(cursorAnchorInfo);
        } else {
            handler.post(new Runnable() { // from class: android.view.inputmethod.IInputMethodSessionInvoker$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    IInputMethodSessionInvoker.this.lambda$updateCursorAnchorInfo$0(cursorAnchorInfo);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: updateCursorAnchorInfoInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$updateCursorAnchorInfo$0(CursorAnchorInfo cursorAnchorInfo) {
        try {
            this.mSession.updateCursorAnchorInfo(cursorAnchorInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "IME died", e);
        }
    }

    void displayCompletions(final CompletionInfo[] completionInfoArr) {
        Handler handler = this.mCustomHandler;
        if (handler == null) {
            lambda$displayCompletions$1(completionInfoArr);
        } else {
            handler.post(new Runnable() { // from class: android.view.inputmethod.IInputMethodSessionInvoker$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    IInputMethodSessionInvoker.this.lambda$displayCompletions$1(completionInfoArr);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: displayCompletionsInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$displayCompletions$1(CompletionInfo[] completionInfoArr) {
        try {
            this.mSession.displayCompletions(completionInfoArr);
        } catch (RemoteException e) {
            Log.w(TAG, "IME died", e);
        }
    }

    void updateExtractedTextSync(int i, ExtractedText extractedText) {
        lambda$updateExtractedText$2(i, extractedText);
    }

    void updateExtractedText(final int i, final ExtractedText extractedText) {
        Handler handler = this.mCustomHandler;
        if (handler == null) {
            lambda$updateExtractedText$2(i, extractedText);
        } else {
            handler.post(new Runnable() { // from class: android.view.inputmethod.IInputMethodSessionInvoker$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    IInputMethodSessionInvoker.this.lambda$updateExtractedText$2(i, extractedText);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: updateExtractedTextInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$updateExtractedText$2(int i, ExtractedText extractedText) {
        try {
            this.mSession.updateExtractedText(i, extractedText);
        } catch (RemoteException e) {
            Log.w(TAG, "IME died", e);
        }
    }

    void appPrivateCommand(final String str, final Bundle bundle) {
        Handler handler = this.mCustomHandler;
        if (handler == null) {
            lambda$appPrivateCommand$3(str, bundle);
        } else {
            handler.post(new Runnable() { // from class: android.view.inputmethod.IInputMethodSessionInvoker$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    IInputMethodSessionInvoker.this.lambda$appPrivateCommand$3(str, bundle);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: appPrivateCommandInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$appPrivateCommand$3(String str, Bundle bundle) {
        try {
            this.mSession.appPrivateCommand(str, bundle);
        } catch (RemoteException e) {
            Log.w(TAG, "IME died", e);
        }
    }

    void viewClicked(final boolean z) {
        Handler handler = this.mCustomHandler;
        if (handler == null) {
            lambda$viewClicked$4(z);
        } else {
            handler.post(new Runnable() { // from class: android.view.inputmethod.IInputMethodSessionInvoker$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    IInputMethodSessionInvoker.this.lambda$viewClicked$4(z);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: viewClickedInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$viewClicked$4(boolean z) {
        try {
            this.mSession.viewClicked(z);
        } catch (RemoteException e) {
            Log.w(TAG, "IME died", e);
        }
    }

    void updateCursor(final Rect rect) {
        Handler handler = this.mCustomHandler;
        if (handler == null) {
            lambda$updateCursor$5(rect);
        } else {
            handler.post(new Runnable() { // from class: android.view.inputmethod.IInputMethodSessionInvoker$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    IInputMethodSessionInvoker.this.lambda$updateCursor$5(rect);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: updateCursorInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$updateCursor$5(Rect rect) {
        try {
            this.mSession.updateCursor(rect);
        } catch (RemoteException e) {
            Log.w(TAG, "IME died", e);
        }
    }

    void updateSelection(final int i, final int i2, final int i3, final int i4, final int i5, final int i6) {
        Handler handler = this.mCustomHandler;
        if (handler == null) {
            lambda$updateSelection$6(i, i2, i3, i4, i5, i6);
        } else {
            handler.post(new Runnable() { // from class: android.view.inputmethod.IInputMethodSessionInvoker$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    IInputMethodSessionInvoker.this.lambda$updateSelection$6(i, i2, i3, i4, i5, i6);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: updateSelectionInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$updateSelection$6(int i, int i2, int i3, int i4, int i5, int i6) {
        try {
            this.mSession.updateSelection(i, i2, i3, i4, i5, i6);
        } catch (RemoteException e) {
            Log.w(TAG, "IME died", e);
        }
    }

    void invalidateInput(final EditorInfo editorInfo, final IRemoteInputConnection iRemoteInputConnection, final int i) {
        Handler handler = this.mCustomHandler;
        if (handler == null) {
            lambda$invalidateInput$7(editorInfo, iRemoteInputConnection, i);
        } else {
            handler.post(new Runnable() { // from class: android.view.inputmethod.IInputMethodSessionInvoker$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    IInputMethodSessionInvoker.this.lambda$invalidateInput$7(editorInfo, iRemoteInputConnection, i);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: invalidateInputInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$invalidateInput$7(EditorInfo editorInfo, IRemoteInputConnection iRemoteInputConnection, int i) {
        try {
            this.mSession.invalidateInput(editorInfo, iRemoteInputConnection, i);
        } catch (RemoteException e) {
            Log.w(TAG, "IME died", e);
        }
    }

    public String toString() {
        return this.mSession.toString();
    }
}
