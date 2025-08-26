package android.view.inputmethod;

import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.RemoteException;
import android.util.Log;
import com.android.internal.inputmethod.IAccessibilityInputMethodSession;
import com.android.internal.inputmethod.IRemoteAccessibilityInputConnection;

/* loaded from: classes4.dex */
final class IAccessibilityInputMethodSessionInvoker {
    private static final String TAG = "IAccessibilityInputMethodSessionInvoker";
    private static Handler sAsyncBinderEmulationHandler;
    private static final Object sAsyncBinderEmulationHandlerLock = new Object();
    private final Handler mCustomHandler;
    private final IAccessibilityInputMethodSession mSession;

    private IAccessibilityInputMethodSessionInvoker(IAccessibilityInputMethodSession iAccessibilityInputMethodSession, Handler handler) {
        this.mSession = iAccessibilityInputMethodSession;
        this.mCustomHandler = handler;
    }

    public static IAccessibilityInputMethodSessionInvoker createOrNull(IAccessibilityInputMethodSession iAccessibilityInputMethodSession) {
        Handler handler;
        if (iAccessibilityInputMethodSession == null || Binder.isProxy(iAccessibilityInputMethodSession)) {
            handler = null;
        } else {
            synchronized (sAsyncBinderEmulationHandlerLock) {
                if (sAsyncBinderEmulationHandler == null) {
                    HandlerThread handlerThread = new HandlerThread("IMM.IAIMS");
                    handlerThread.start();
                    sAsyncBinderEmulationHandler = Handler.createAsync(handlerThread.getLooper());
                }
                handler = sAsyncBinderEmulationHandler;
            }
        }
        if (iAccessibilityInputMethodSession == null) {
            return null;
        }
        return new IAccessibilityInputMethodSessionInvoker(iAccessibilityInputMethodSession, handler);
    }

    void finishInput() {
        Handler handler = this.mCustomHandler;
        if (handler == null) {
            finishInputInternal();
        } else {
            handler.post(new Runnable() { // from class: android.view.inputmethod.IAccessibilityInputMethodSessionInvoker$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.finishInputInternal();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finishInputInternal() {
        try {
            this.mSession.finishInput();
        } catch (RemoteException e) {
            Log.w(TAG, "A11yIME died", e);
        }
    }

    void updateSelection(final int i, final int i2, final int i3, final int i4, final int i5, final int i6) {
        Handler handler = this.mCustomHandler;
        if (handler == null) {
            lambda$updateSelection$0(i, i2, i3, i4, i5, i6);
        } else {
            handler.post(new Runnable() { // from class: android.view.inputmethod.IAccessibilityInputMethodSessionInvoker$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$updateSelection$0(i, i2, i3, i4, i5, i6);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: updateSelectionInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$updateSelection$0(int i, int i2, int i3, int i4, int i5, int i6) {
        try {
            this.mSession.updateSelection(i, i2, i3, i4, i5, i6);
        } catch (RemoteException e) {
            Log.w(TAG, "A11yIME died", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void invalidateInput(final EditorInfo editorInfo, final IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, final int i) {
        Handler handler = this.mCustomHandler;
        if (handler == null) {
            lambda$invalidateInput$1(editorInfo, iRemoteAccessibilityInputConnection, i);
        } else {
            handler.post(new Runnable() { // from class: android.view.inputmethod.IAccessibilityInputMethodSessionInvoker$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$invalidateInput$1(editorInfo, iRemoteAccessibilityInputConnection, i);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: invalidateInputInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$invalidateInput$1(EditorInfo editorInfo, IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, int i) {
        try {
            this.mSession.invalidateInput(editorInfo, iRemoteAccessibilityInputConnection, i);
        } catch (RemoteException e) {
            Log.w(TAG, "A11yIME died", e);
        }
    }
}
