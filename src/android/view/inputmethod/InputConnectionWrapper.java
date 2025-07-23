package android.view.inputmethod;

import android.graphics.RectF;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.view.KeyEvent;
import com.android.internal.util.Preconditions;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

/* loaded from: classes4.dex */
public class InputConnectionWrapper implements InputConnection {
    final boolean mMutable;
    private InputConnection mTarget;

    public InputConnectionWrapper(InputConnection inputConnection, boolean z) {
        this.mMutable = z;
        this.mTarget = inputConnection;
    }

    public void setTarget(InputConnection inputConnection) {
        if (this.mTarget != null && !this.mMutable) {
            throw new SecurityException("not mutable");
        }
        this.mTarget = inputConnection;
    }

    @Override // android.view.inputmethod.InputConnection
    public CharSequence getTextBeforeCursor(int i, int i2) {
        Preconditions.checkArgumentNonnegative(i);
        return this.mTarget.getTextBeforeCursor(i, i2);
    }

    @Override // android.view.inputmethod.InputConnection
    public CharSequence getTextAfterCursor(int i, int i2) {
        Preconditions.checkArgumentNonnegative(i);
        return this.mTarget.getTextAfterCursor(i, i2);
    }

    @Override // android.view.inputmethod.InputConnection
    public CharSequence getSelectedText(int i) {
        return this.mTarget.getSelectedText(i);
    }

    @Override // android.view.inputmethod.InputConnection
    public SurroundingText getSurroundingText(int i, int i2, int i3) {
        Preconditions.checkArgumentNonnegative(i);
        Preconditions.checkArgumentNonnegative(i2);
        return this.mTarget.getSurroundingText(i, i2, i3);
    }

    @Override // android.view.inputmethod.InputConnection
    public int getCursorCapsMode(int i) {
        return this.mTarget.getCursorCapsMode(i);
    }

    @Override // android.view.inputmethod.InputConnection
    public ExtractedText getExtractedText(ExtractedTextRequest extractedTextRequest, int i) {
        return this.mTarget.getExtractedText(extractedTextRequest, i);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean deleteSurroundingTextInCodePoints(int i, int i2) {
        return this.mTarget.deleteSurroundingTextInCodePoints(i, i2);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean deleteSurroundingText(int i, int i2) {
        return this.mTarget.deleteSurroundingText(i, i2);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean setComposingText(CharSequence charSequence, int i) {
        return this.mTarget.setComposingText(charSequence, i);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean setComposingText(CharSequence charSequence, int i, TextAttribute textAttribute) {
        return this.mTarget.setComposingText(charSequence, i, textAttribute);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean setComposingRegion(int i, int i2) {
        return this.mTarget.setComposingRegion(i, i2);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean setComposingRegion(int i, int i2, TextAttribute textAttribute) {
        return this.mTarget.setComposingRegion(i, i2, textAttribute);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean finishComposingText() {
        return this.mTarget.finishComposingText();
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean commitText(CharSequence charSequence, int i) {
        return this.mTarget.commitText(charSequence, i);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean commitText(CharSequence charSequence, int i, TextAttribute textAttribute) {
        return this.mTarget.commitText(charSequence, i, textAttribute);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean commitCompletion(CompletionInfo completionInfo) {
        return this.mTarget.commitCompletion(completionInfo);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean commitCorrection(CorrectionInfo correctionInfo) {
        return this.mTarget.commitCorrection(correctionInfo);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean setSelection(int i, int i2) {
        return this.mTarget.setSelection(i, i2);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean performEditorAction(int i) {
        return this.mTarget.performEditorAction(i);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean performContextMenuAction(int i) {
        return this.mTarget.performContextMenuAction(i);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean beginBatchEdit() {
        return this.mTarget.beginBatchEdit();
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean endBatchEdit() {
        return this.mTarget.endBatchEdit();
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean sendKeyEvent(KeyEvent keyEvent) {
        return this.mTarget.sendKeyEvent(keyEvent);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean clearMetaKeyStates(int i) {
        return this.mTarget.clearMetaKeyStates(i);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean reportFullscreenMode(boolean z) {
        return this.mTarget.reportFullscreenMode(z);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean performSpellCheck() {
        return this.mTarget.performSpellCheck();
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean performPrivateCommand(String str, Bundle bundle) {
        return this.mTarget.performPrivateCommand(str, bundle);
    }

    @Override // android.view.inputmethod.InputConnection
    public void performHandwritingGesture(HandwritingGesture handwritingGesture, Executor executor, IntConsumer intConsumer) {
        this.mTarget.performHandwritingGesture(handwritingGesture, executor, intConsumer);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean previewHandwritingGesture(PreviewableHandwritingGesture previewableHandwritingGesture, CancellationSignal cancellationSignal) {
        return this.mTarget.previewHandwritingGesture(previewableHandwritingGesture, cancellationSignal);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean requestCursorUpdates(int i) {
        return this.mTarget.requestCursorUpdates(i);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean requestCursorUpdates(int i, int i2) {
        return this.mTarget.requestCursorUpdates(i, i2);
    }

    @Override // android.view.inputmethod.InputConnection
    public void requestTextBoundsInfo(RectF rectF, Executor executor, Consumer<TextBoundsInfoResult> consumer) {
        this.mTarget.requestTextBoundsInfo(rectF, executor, consumer);
    }

    @Override // android.view.inputmethod.InputConnection
    public Handler getHandler() {
        return this.mTarget.getHandler();
    }

    @Override // android.view.inputmethod.InputConnection
    public void closeConnection() {
        this.mTarget.closeConnection();
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean commitContent(InputContentInfo inputContentInfo, int i, Bundle bundle) {
        return this.mTarget.commitContent(inputContentInfo, i, bundle);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean setImeConsumesInput(boolean z) {
        return this.mTarget.setImeConsumesInput(z);
    }

    @Override // android.view.inputmethod.InputConnection
    public TextSnapshot takeSnapshot() {
        return this.mTarget.takeSnapshot();
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean replaceText(int i, int i2, CharSequence charSequence, int i3, TextAttribute textAttribute) {
        return this.mTarget.replaceText(i, i2, charSequence, i3, textAttribute);
    }
}
