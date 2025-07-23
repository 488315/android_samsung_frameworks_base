package com.android.internal.inputmethod;

import android.os.RemoteException;
import android.view.KeyEvent;
import android.view.inputmethod.SurroundingText;
import android.view.inputmethod.TextAttribute;
import com.android.internal.infra.AndroidFuture;
import java.util.Objects;

/* loaded from: classes5.dex */
final class IRemoteAccessibilityInputConnectionInvoker {
    private final IRemoteAccessibilityInputConnection mConnection;
    private final int mSessionId;

    private IRemoteAccessibilityInputConnectionInvoker(IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, int i) {
        this.mConnection = iRemoteAccessibilityInputConnection;
        this.mSessionId = i;
    }

    public static IRemoteAccessibilityInputConnectionInvoker create(IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection) {
        Objects.requireNonNull(iRemoteAccessibilityInputConnection);
        return new IRemoteAccessibilityInputConnectionInvoker(iRemoteAccessibilityInputConnection, 0);
    }

    public IRemoteAccessibilityInputConnectionInvoker cloneWithSessionId(int i) {
        return new IRemoteAccessibilityInputConnectionInvoker(this.mConnection, i);
    }

    public boolean isSameConnection(IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection) {
        return iRemoteAccessibilityInputConnection != null && this.mConnection.asBinder() == iRemoteAccessibilityInputConnection.asBinder();
    }

    InputConnectionCommandHeader createHeader() {
        return new InputConnectionCommandHeader(this.mSessionId);
    }

    public void commitText(CharSequence charSequence, int i, TextAttribute textAttribute) {
        try {
            this.mConnection.commitText(createHeader(), charSequence, i, textAttribute);
        } catch (RemoteException unused) {
        }
    }

    public void setSelection(int i, int i2) {
        try {
            this.mConnection.setSelection(createHeader(), i, i2);
        } catch (RemoteException unused) {
        }
    }

    public AndroidFuture<SurroundingText> getSurroundingText(int i, int i2, int i3) {
        AndroidFuture<SurroundingText> androidFuture = new AndroidFuture<>();
        try {
            this.mConnection.getSurroundingText(createHeader(), i, i2, i3, androidFuture);
            return androidFuture;
        } catch (RemoteException e) {
            androidFuture.completeExceptionally(e);
            return androidFuture;
        }
    }

    public void deleteSurroundingText(int i, int i2) {
        try {
            this.mConnection.deleteSurroundingText(createHeader(), i, i2);
        } catch (RemoteException unused) {
        }
    }

    public void sendKeyEvent(KeyEvent keyEvent) {
        try {
            this.mConnection.sendKeyEvent(createHeader(), keyEvent);
        } catch (RemoteException unused) {
        }
    }

    public void performEditorAction(int i) {
        try {
            this.mConnection.performEditorAction(createHeader(), i);
        } catch (RemoteException unused) {
        }
    }

    public void performContextMenuAction(int i) {
        try {
            this.mConnection.performContextMenuAction(createHeader(), i);
        } catch (RemoteException unused) {
        }
    }

    public AndroidFuture<Integer> getCursorCapsMode(int i) {
        AndroidFuture<Integer> androidFuture = new AndroidFuture<>();
        try {
            this.mConnection.getCursorCapsMode(createHeader(), i, androidFuture);
            return androidFuture;
        } catch (RemoteException e) {
            androidFuture.completeExceptionally(e);
            return androidFuture;
        }
    }

    public void clearMetaKeyStates(int i) {
        try {
            this.mConnection.clearMetaKeyStates(createHeader(), i);
        } catch (RemoteException unused) {
        }
    }
}
