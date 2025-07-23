package com.android.internal.inputmethod;

import android.view.KeyEvent;
import android.view.inputmethod.SurroundingText;
import android.view.inputmethod.TextAttribute;

/* loaded from: classes5.dex */
public final class RemoteAccessibilityInputConnection {
    private static final int MAX_WAIT_TIME_MILLIS = 2000;
    private static final String TAG = "RemoteA11yInputConnection";
    private final CancellationGroup mCancellationGroup;
    IRemoteAccessibilityInputConnectionInvoker mInvoker;

    public RemoteAccessibilityInputConnection(IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, CancellationGroup cancellationGroup) {
        this.mInvoker = IRemoteAccessibilityInputConnectionInvoker.create(iRemoteAccessibilityInputConnection);
        this.mCancellationGroup = cancellationGroup;
    }

    public RemoteAccessibilityInputConnection(RemoteAccessibilityInputConnection remoteAccessibilityInputConnection, int i) {
        this.mInvoker = remoteAccessibilityInputConnection.mInvoker.cloneWithSessionId(i);
        this.mCancellationGroup = remoteAccessibilityInputConnection.mCancellationGroup;
    }

    public boolean isSameConnection(IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection) {
        return this.mInvoker.isSameConnection(iRemoteAccessibilityInputConnection);
    }

    public void commitText(CharSequence charSequence, int i, TextAttribute textAttribute) {
        this.mInvoker.commitText(charSequence, i, textAttribute);
    }

    public void setSelection(int i, int i2) {
        this.mInvoker.setSelection(i, i2);
    }

    public SurroundingText getSurroundingText(int i, int i2, int i3) {
        if (this.mCancellationGroup.isCanceled()) {
            return null;
        }
        return (SurroundingText) CompletableFutureUtil.getResultOrNull(this.mInvoker.getSurroundingText(i, i2, i3), TAG, "getSurroundingText()", this.mCancellationGroup, 2000L);
    }

    public void deleteSurroundingText(int i, int i2) {
        this.mInvoker.deleteSurroundingText(i, i2);
    }

    public void sendKeyEvent(KeyEvent keyEvent) {
        this.mInvoker.sendKeyEvent(keyEvent);
    }

    public void performEditorAction(int i) {
        this.mInvoker.performEditorAction(i);
    }

    public void performContextMenuAction(int i) {
        this.mInvoker.performContextMenuAction(i);
    }

    public int getCursorCapsMode(int i) {
        if (this.mCancellationGroup.isCanceled()) {
            return 0;
        }
        return CompletableFutureUtil.getResultOrZero(this.mInvoker.getCursorCapsMode(i), TAG, "getCursorCapsMode()", this.mCancellationGroup, 2000L);
    }

    public void clearMetaKeyStates(int i) {
        this.mInvoker.clearMetaKeyStates(i);
    }
}
