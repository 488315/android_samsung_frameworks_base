package android.inputmethodservice;

import android.graphics.RectF;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.CorrectionInfo;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.HandwritingGesture;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputContentInfo;
import android.view.inputmethod.PreviewableHandwritingGesture;
import android.view.inputmethod.SurroundingText;
import android.view.inputmethod.TextAttribute;
import android.view.inputmethod.TextBoundsInfoResult;
import com.android.internal.inputmethod.CancellationGroup;
import com.android.internal.inputmethod.CompletableFutureUtil;
import com.android.internal.inputmethod.IRemoteInputConnection;
import com.android.internal.inputmethod.ImeTracing;
import com.android.internal.inputmethod.InputConnectionProtoDumper;
import java.lang.ref.WeakReference;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

/* loaded from: classes2.dex */
final class RemoteInputConnection implements InputConnection {
    private static final int MAX_WAIT_TIME_MILLIS = 2000;
    private static final String TAG = "RemoteInputConnection";
    private final CancellationGroup mCancellationGroup;
    private final InputMethodServiceInternalHolder mImsInternal;
    private final IRemoteInputConnectionInvoker mInvoker;

    @Override // android.view.inputmethod.InputConnection
    public void closeConnection() {
    }

    @Override // android.view.inputmethod.InputConnection
    public Handler getHandler() {
        return null;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean reportFullscreenMode(boolean z) {
        return false;
    }

    private static final class InputMethodServiceInternalHolder {
        private final WeakReference<InputMethodServiceInternal> mServiceRef;

        private InputMethodServiceInternalHolder(WeakReference<InputMethodServiceInternal> weakReference) {
            this.mServiceRef = weakReference;
        }

        public InputMethodServiceInternal getAndWarnIfNull() {
            InputMethodServiceInternal inputMethodServiceInternal = this.mServiceRef.get();
            if (inputMethodServiceInternal == null) {
                Log.e(RemoteInputConnection.TAG, "InputMethodService is already destroyed.  InputConnection instances cannot be used beyond InputMethodService lifetime.", new Throwable());
            }
            return inputMethodServiceInternal;
        }
    }

    RemoteInputConnection(WeakReference<InputMethodServiceInternal> weakReference, IRemoteInputConnection iRemoteInputConnection, CancellationGroup cancellationGroup) {
        this.mImsInternal = new InputMethodServiceInternalHolder(weakReference);
        this.mInvoker = IRemoteInputConnectionInvoker.create(iRemoteInputConnection);
        this.mCancellationGroup = cancellationGroup;
    }

    public boolean isSameConnection(IRemoteInputConnection iRemoteInputConnection) {
        return this.mInvoker.isSameConnection(iRemoteInputConnection);
    }

    RemoteInputConnection(RemoteInputConnection remoteInputConnection, int i) {
        this.mImsInternal = remoteInputConnection.mImsInternal;
        this.mInvoker = remoteInputConnection.mInvoker.cloneWithSessionId(i);
        this.mCancellationGroup = remoteInputConnection.mCancellationGroup;
    }

    @Override // android.view.inputmethod.InputConnection
    public CharSequence getTextAfterCursor(int i, int i2) {
        if (i < 0) {
            Log.e(TAG, "length=" + i + " is invalid and always results in null result.");
        }
        if (this.mCancellationGroup.isCanceled()) {
            return null;
        }
        CharSequence charSequence = (CharSequence) CompletableFutureUtil.getResultOrNull(this.mInvoker.getTextAfterCursor(i, i2), TAG, "getTextAfterCursor()", this.mCancellationGroup, 2000L);
        InputMethodServiceInternal andWarnIfNull = this.mImsInternal.getAndWarnIfNull();
        if (andWarnIfNull != null && ImeTracing.getInstance().isEnabled()) {
            andWarnIfNull.triggerServiceDump("RemoteInputConnection#getTextAfterCursor", InputConnectionProtoDumper.buildGetTextAfterCursorProto(i, i2, charSequence));
        }
        return charSequence;
    }

    @Override // android.view.inputmethod.InputConnection
    public CharSequence getTextBeforeCursor(int i, int i2) {
        if (i < 0) {
            Log.e(TAG, "length=" + i + " is invalid and always results in null result.");
        }
        if (this.mCancellationGroup.isCanceled()) {
            return null;
        }
        CharSequence charSequence = (CharSequence) CompletableFutureUtil.getResultOrNull(this.mInvoker.getTextBeforeCursor(i, i2), TAG, "getTextBeforeCursor()", this.mCancellationGroup, 2000L);
        InputMethodServiceInternal andWarnIfNull = this.mImsInternal.getAndWarnIfNull();
        if (andWarnIfNull != null && ImeTracing.getInstance().isEnabled()) {
            andWarnIfNull.triggerServiceDump("RemoteInputConnection#getTextBeforeCursor", InputConnectionProtoDumper.buildGetTextBeforeCursorProto(i, i2, charSequence));
        }
        return charSequence;
    }

    @Override // android.view.inputmethod.InputConnection
    public CharSequence getSelectedText(int i) {
        if (this.mCancellationGroup.isCanceled()) {
            return null;
        }
        CharSequence charSequence = (CharSequence) CompletableFutureUtil.getResultOrNull(this.mInvoker.getSelectedText(i), TAG, "getSelectedText()", this.mCancellationGroup, 2000L);
        InputMethodServiceInternal andWarnIfNull = this.mImsInternal.getAndWarnIfNull();
        if (andWarnIfNull != null && ImeTracing.getInstance().isEnabled()) {
            andWarnIfNull.triggerServiceDump("RemoteInputConnection#getSelectedText", InputConnectionProtoDumper.buildGetSelectedTextProto(i, charSequence));
        }
        return charSequence;
    }

    @Override // android.view.inputmethod.InputConnection
    public SurroundingText getSurroundingText(int i, int i2, int i3) {
        if (i < 0) {
            Log.e(TAG, "beforeLength=" + i + " is invalid and always results in null result.");
        }
        if (i2 < 0) {
            Log.e(TAG, "afterLength=" + i2 + " is invalid and always results in null result.");
        }
        if (this.mCancellationGroup.isCanceled()) {
            return null;
        }
        SurroundingText surroundingText = (SurroundingText) CompletableFutureUtil.getResultOrNull(this.mInvoker.getSurroundingText(i, i2, i3), TAG, "getSurroundingText()", this.mCancellationGroup, 2000L);
        InputMethodServiceInternal andWarnIfNull = this.mImsInternal.getAndWarnIfNull();
        if (andWarnIfNull != null && ImeTracing.getInstance().isEnabled()) {
            andWarnIfNull.triggerServiceDump("RemoteInputConnection#getSurroundingText", InputConnectionProtoDumper.buildGetSurroundingTextProto(i, i2, i3, surroundingText));
        }
        return surroundingText;
    }

    @Override // android.view.inputmethod.InputConnection
    public int getCursorCapsMode(int i) {
        if (this.mCancellationGroup.isCanceled()) {
            return 0;
        }
        int resultOrZero = CompletableFutureUtil.getResultOrZero(this.mInvoker.getCursorCapsMode(i), TAG, "getCursorCapsMode()", this.mCancellationGroup, 2000L);
        InputMethodServiceInternal andWarnIfNull = this.mImsInternal.getAndWarnIfNull();
        if (andWarnIfNull != null && ImeTracing.getInstance().isEnabled()) {
            andWarnIfNull.triggerServiceDump("RemoteInputConnection#getCursorCapsMode", InputConnectionProtoDumper.buildGetCursorCapsModeProto(i, resultOrZero));
        }
        return resultOrZero;
    }

    @Override // android.view.inputmethod.InputConnection
    public ExtractedText getExtractedText(ExtractedTextRequest extractedTextRequest, int i) {
        if (this.mCancellationGroup.isCanceled()) {
            return null;
        }
        ExtractedText extractedText = (ExtractedText) CompletableFutureUtil.getResultOrNull(this.mInvoker.getExtractedText(extractedTextRequest, i), TAG, "getExtractedText()", this.mCancellationGroup, 2000L);
        InputMethodServiceInternal andWarnIfNull = this.mImsInternal.getAndWarnIfNull();
        if (andWarnIfNull != null && ImeTracing.getInstance().isEnabled()) {
            andWarnIfNull.triggerServiceDump("RemoteInputConnection#getExtractedText", InputConnectionProtoDumper.buildGetExtractedTextProto(extractedTextRequest, i, extractedText));
        }
        return extractedText;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean commitText(CharSequence charSequence, int i) {
        boolean zCommitText = this.mInvoker.commitText(charSequence, i);
        if (zCommitText) {
            notifyUserActionIfNecessary();
        }
        return zCommitText;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean commitText(CharSequence charSequence, int i, TextAttribute textAttribute) {
        boolean zCommitText = this.mInvoker.commitText(charSequence, i, textAttribute);
        if (zCommitText) {
            notifyUserActionIfNecessary();
        }
        return zCommitText;
    }

    private void notifyUserActionIfNecessary() {
        InputMethodServiceInternal andWarnIfNull = this.mImsInternal.getAndWarnIfNull();
        if (andWarnIfNull == null) {
            return;
        }
        andWarnIfNull.notifyUserActionIfNecessary();
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean commitCompletion(CompletionInfo completionInfo) {
        return this.mInvoker.commitCompletion(completionInfo);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean commitCorrection(CorrectionInfo correctionInfo) {
        return this.mInvoker.commitCorrection(correctionInfo);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean setSelection(int i, int i2) {
        return this.mInvoker.setSelection(i, i2);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean performEditorAction(int i) {
        return this.mInvoker.performEditorAction(i);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean performContextMenuAction(int i) {
        return this.mInvoker.performContextMenuAction(i);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean setComposingRegion(int i, int i2) {
        return this.mInvoker.setComposingRegion(i, i2);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean setComposingRegion(int i, int i2, TextAttribute textAttribute) {
        return this.mInvoker.setComposingRegion(i, i2, textAttribute);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean setComposingText(CharSequence charSequence, int i) {
        boolean composingText = this.mInvoker.setComposingText(charSequence, i);
        if (composingText) {
            notifyUserActionIfNecessary();
        }
        return composingText;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean setComposingText(CharSequence charSequence, int i, TextAttribute textAttribute) {
        boolean composingText = this.mInvoker.setComposingText(charSequence, i, textAttribute);
        if (composingText) {
            notifyUserActionIfNecessary();
        }
        return composingText;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean finishComposingText() {
        return this.mInvoker.finishComposingText();
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean beginBatchEdit() {
        return this.mInvoker.beginBatchEdit();
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean endBatchEdit() {
        return this.mInvoker.endBatchEdit();
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean sendKeyEvent(KeyEvent keyEvent) {
        boolean zSendKeyEvent = this.mInvoker.sendKeyEvent(keyEvent);
        if (zSendKeyEvent) {
            notifyUserActionIfNecessary();
        }
        return zSendKeyEvent;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean clearMetaKeyStates(int i) {
        return this.mInvoker.clearMetaKeyStates(i);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean deleteSurroundingText(int i, int i2) {
        return this.mInvoker.deleteSurroundingText(i, i2);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean deleteSurroundingTextInCodePoints(int i, int i2) {
        return this.mInvoker.deleteSurroundingTextInCodePoints(i, i2);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean performSpellCheck() {
        return this.mInvoker.performSpellCheck();
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean performPrivateCommand(String str, Bundle bundle) {
        return this.mInvoker.performPrivateCommand(str, bundle);
    }

    @Override // android.view.inputmethod.InputConnection
    public void performHandwritingGesture(HandwritingGesture handwritingGesture, Executor executor, IntConsumer intConsumer) {
        this.mInvoker.performHandwritingGesture(handwritingGesture, executor, intConsumer);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean previewHandwritingGesture(PreviewableHandwritingGesture previewableHandwritingGesture, CancellationSignal cancellationSignal) {
        if (cancellationSignal == null || !cancellationSignal.isCanceled()) {
            return this.mInvoker.previewHandwritingGesture(previewableHandwritingGesture, cancellationSignal);
        }
        return false;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean requestCursorUpdates(int i) {
        InputMethodServiceInternal andWarnIfNull;
        if (this.mCancellationGroup.isCanceled() || (andWarnIfNull = this.mImsInternal.getAndWarnIfNull()) == null) {
            return false;
        }
        return CompletableFutureUtil.getResultOrFalse(this.mInvoker.requestCursorUpdates(i, andWarnIfNull.getContext().getDisplayId()), TAG, "requestCursorUpdates()", this.mCancellationGroup, 2000L);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean requestCursorUpdates(int i, int i2) {
        InputMethodServiceInternal andWarnIfNull;
        if (this.mCancellationGroup.isCanceled() || (andWarnIfNull = this.mImsInternal.getAndWarnIfNull()) == null) {
            return false;
        }
        return CompletableFutureUtil.getResultOrFalse(this.mInvoker.requestCursorUpdates(i, i2, andWarnIfNull.getContext().getDisplayId()), TAG, "requestCursorUpdates()", this.mCancellationGroup, 2000L);
    }

    @Override // android.view.inputmethod.InputConnection
    public void requestTextBoundsInfo(RectF rectF, Executor executor, Consumer<TextBoundsInfoResult> consumer) {
        this.mInvoker.requestTextBoundsInfo(rectF, executor, consumer);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean commitContent(InputContentInfo inputContentInfo, int i, Bundle bundle) {
        if (this.mCancellationGroup.isCanceled()) {
            return false;
        }
        if ((i & 1) != 0) {
            InputMethodServiceInternal andWarnIfNull = this.mImsInternal.getAndWarnIfNull();
            if (andWarnIfNull == null) {
                return false;
            }
            andWarnIfNull.exposeContent(inputContentInfo, this);
        }
        return CompletableFutureUtil.getResultOrFalse(this.mInvoker.commitContent(inputContentInfo, i, bundle), TAG, "commitContent()", this.mCancellationGroup, 2000L);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean setImeConsumesInput(boolean z) {
        return this.mInvoker.setImeConsumesInput(z);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean replaceText(int i, int i2, CharSequence charSequence, int i3, TextAttribute textAttribute) {
        return this.mInvoker.replaceText(i, i2, charSequence, i3, textAttribute);
    }

    public String toString() {
        return "RemoteInputConnection{idHash=#" + Integer.toHexString(System.identityHashCode(this)) + "}";
    }
}
