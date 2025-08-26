package android.accessibilityservice;

import android.os.Handler;
import android.os.Looper;
import android.view.inputmethod.EditorInfo;
import com.android.internal.inputmethod.IAccessibilityInputMethodSession;
import com.android.internal.inputmethod.IRemoteAccessibilityInputConnection;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
final class AccessibilityInputMethodSessionWrapper extends IAccessibilityInputMethodSession.Stub {
    private final Handler mHandler;
    private final AtomicReference<AccessibilityInputMethodSession> mSessionRef;

    AccessibilityInputMethodSessionWrapper(Looper looper, AccessibilityInputMethodSession accessibilityInputMethodSession) {
        this.mSessionRef = new AtomicReference<>(accessibilityInputMethodSession);
        this.mHandler = Handler.createAsync(looper);
    }

    AccessibilityInputMethodSession getSession() {
        return this.mSessionRef.get();
    }

    @Override // com.android.internal.inputmethod.IAccessibilityInputMethodSession
    public void updateSelection(final int i, final int i2, final int i3, final int i4, final int i5, final int i6) {
        if (this.mHandler.getLooper().isCurrentThread()) {
            lambda$updateSelection$0(i, i2, i3, i4, i5, i6);
        } else {
            this.mHandler.post(new Runnable() { // from class: android.accessibilityservice.AccessibilityInputMethodSessionWrapper$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$updateSelection$0(i, i2, i3, i4, i5, i6);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: doUpdateSelection, reason: merged with bridge method [inline-methods] */
    public void lambda$updateSelection$0(int i, int i2, int i3, int i4, int i5, int i6) {
        AccessibilityInputMethodSession accessibilityInputMethodSession = this.mSessionRef.get();
        if (accessibilityInputMethodSession != null) {
            accessibilityInputMethodSession.updateSelection(i, i2, i3, i4, i5, i6);
        }
    }

    @Override // com.android.internal.inputmethod.IAccessibilityInputMethodSession
    public void finishInput() {
        if (this.mHandler.getLooper().isCurrentThread()) {
            doFinishInput();
        } else {
            this.mHandler.post(new Runnable() { // from class: android.accessibilityservice.AccessibilityInputMethodSessionWrapper$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.doFinishInput();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doFinishInput() {
        AccessibilityInputMethodSession accessibilityInputMethodSession = this.mSessionRef.get();
        if (accessibilityInputMethodSession != null) {
            accessibilityInputMethodSession.finishInput();
        }
    }

    @Override // com.android.internal.inputmethod.IAccessibilityInputMethodSession
    public void finishSession() {
        if (this.mHandler.getLooper().isCurrentThread()) {
            doFinishSession();
        } else {
            this.mHandler.post(new Runnable() { // from class: android.accessibilityservice.AccessibilityInputMethodSessionWrapper$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.doFinishSession();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doFinishSession() {
        this.mSessionRef.set(null);
    }

    @Override // com.android.internal.inputmethod.IAccessibilityInputMethodSession
    public void invalidateInput(final EditorInfo editorInfo, final IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, final int i) {
        if (this.mHandler.getLooper().isCurrentThread()) {
            lambda$invalidateInput$1(editorInfo, iRemoteAccessibilityInputConnection, i);
        } else {
            this.mHandler.post(new Runnable() { // from class: android.accessibilityservice.AccessibilityInputMethodSessionWrapper$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$invalidateInput$1(editorInfo, iRemoteAccessibilityInputConnection, i);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: doInvalidateInput, reason: merged with bridge method [inline-methods] */
    public void lambda$invalidateInput$1(EditorInfo editorInfo, IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, int i) {
        AccessibilityInputMethodSession accessibilityInputMethodSession = this.mSessionRef.get();
        if (accessibilityInputMethodSession != null) {
            accessibilityInputMethodSession.invalidateInput(editorInfo, iRemoteAccessibilityInputConnection, i);
        }
    }
}
