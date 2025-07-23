package com.android.internal.inputmethod;

import android.graphics.RectF;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.text.Editable;
import android.text.Selection;
import android.text.method.KeyListener;
import android.util.proto.ProtoOutputStream;
import android.view.View;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.CorrectionInfo;
import android.view.inputmethod.DeleteGesture;
import android.view.inputmethod.DeleteRangeGesture;
import android.view.inputmethod.DumpableInputConnection;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.HandwritingGesture;
import android.view.inputmethod.InsertGesture;
import android.view.inputmethod.InsertModeGesture;
import android.view.inputmethod.JoinOrSplitGesture;
import android.view.inputmethod.PreviewableHandwritingGesture;
import android.view.inputmethod.RemoveSpaceGesture;
import android.view.inputmethod.SelectGesture;
import android.view.inputmethod.SelectRangeGesture;
import android.view.inputmethod.TextBoundsInfo;
import android.view.inputmethod.TextBoundsInfoResult;
import android.widget.TextView;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

/* loaded from: classes5.dex */
public final class EditableInputConnection extends BaseInputConnection implements DumpableInputConnection {
    private static final boolean DEBUG = false;
    private static final String TAG = "EditableInputConnection";
    private int mBatchEditNesting;
    private final TextView mTextView;

    public EditableInputConnection(TextView textView) {
        super((View) textView, true);
        this.mTextView = textView;
    }

    @Override // android.view.inputmethod.BaseInputConnection
    public Editable getEditable() {
        TextView textView = this.mTextView;
        if (textView != null) {
            return textView.getEditableText();
        }
        return null;
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public boolean beginBatchEdit() {
        synchronized (this) {
            if (this.mBatchEditNesting < 0) {
                return false;
            }
            this.mTextView.beginBatchEdit();
            this.mBatchEditNesting++;
            return true;
        }
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public boolean endBatchEdit() {
        synchronized (this) {
            if (this.mBatchEditNesting <= 0) {
                return false;
            }
            this.mTextView.endBatchEdit();
            int i = this.mBatchEditNesting - 1;
            this.mBatchEditNesting = i;
            return i > 0;
        }
    }

    @Override // android.view.inputmethod.BaseInputConnection
    public void endComposingRegionEditInternal() {
        this.mTextView.notifyContentCaptureTextChanged();
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public void closeConnection() {
        super.closeConnection();
        synchronized (this) {
            while (this.mBatchEditNesting > 0) {
                endBatchEdit();
            }
            this.mBatchEditNesting = -1;
        }
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public boolean clearMetaKeyStates(int i) {
        Editable editable = getEditable();
        if (editable == null) {
            return false;
        }
        KeyListener keyListener = this.mTextView.getKeyListener();
        if (keyListener == null) {
            return true;
        }
        try {
            keyListener.clearMetaKeyState(this.mTextView, editable, i);
            return true;
        } catch (AbstractMethodError unused) {
            return true;
        }
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public boolean commitCompletion(CompletionInfo completionInfo) {
        this.mTextView.beginBatchEdit();
        this.mTextView.onCommitCompletion(completionInfo);
        this.mTextView.endBatchEdit();
        return true;
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public boolean commitCorrection(CorrectionInfo correctionInfo) {
        this.mTextView.beginBatchEdit();
        this.mTextView.onCommitCorrection(correctionInfo);
        this.mTextView.endBatchEdit();
        return true;
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public boolean performEditorAction(int i) {
        this.mTextView.onEditorAction(i);
        return true;
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public boolean performContextMenuAction(int i) {
        this.mTextView.beginBatchEdit();
        this.mTextView.onTextContextMenuItem(i);
        this.mTextView.endBatchEdit();
        return true;
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public ExtractedText getExtractedText(ExtractedTextRequest extractedTextRequest, int i) {
        if (this.mTextView == null) {
            return null;
        }
        ExtractedText extractedText = new ExtractedText();
        if (!this.mTextView.extractText(extractedTextRequest, extractedText)) {
            return null;
        }
        if ((i & 1) != 0) {
            this.mTextView.setExtracting(extractedTextRequest);
        }
        return extractedText;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean performSpellCheck() {
        this.mTextView.onPerformSpellCheck();
        return true;
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public boolean performPrivateCommand(String str, Bundle bundle) {
        this.mTextView.onPrivateIMECommand(str, bundle);
        return true;
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public boolean commitText(CharSequence charSequence, int i) {
        TextView textView = this.mTextView;
        if (textView == null) {
            return super.commitText(charSequence, i);
        }
        textView.resetErrorChangedFlag();
        boolean commitText = super.commitText(charSequence, i);
        this.mTextView.hideErrorIfUnchanged();
        return commitText;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean requestCursorUpdates(int i, int i2) {
        return requestCursorUpdates(i | i2);
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public boolean requestCursorUpdates(int i) {
        if ((i & (-128)) != 0 || this.mIMM == null) {
            return false;
        }
        this.mIMM.setUpdateCursorAnchorInfoMode(i);
        TextView textView = this.mTextView;
        if (textView == null) {
            return true;
        }
        textView.onRequestCursorUpdatesInternal(i & 3, i & 124);
        return true;
    }

    @Override // android.view.inputmethod.InputConnection
    public void requestTextBoundsInfo(RectF rectF, Executor executor, final Consumer<TextBoundsInfoResult> consumer) {
        TextBoundsInfo textBoundsInfo = this.mTextView.getTextBoundsInfo(rectF);
        final TextBoundsInfoResult textBoundsInfoResult = new TextBoundsInfoResult(textBoundsInfo != null ? 1 : 2, textBoundsInfo);
        executor.execute(new Runnable() { // from class: com.android.internal.inputmethod.EditableInputConnection$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                consumer.accept(textBoundsInfoResult);
            }
        });
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean setImeConsumesInput(boolean z) {
        TextView textView = this.mTextView;
        if (textView == null) {
            return super.setImeConsumesInput(z);
        }
        textView.setImeConsumesInput(z);
        return true;
    }

    @Override // android.view.inputmethod.InputConnection
    public void performHandwritingGesture(HandwritingGesture handwritingGesture, Executor executor, final IntConsumer intConsumer) {
        final int performHandwritingInsertModeGesture;
        if (handwritingGesture instanceof SelectGesture) {
            performHandwritingInsertModeGesture = this.mTextView.performHandwritingSelectGesture((SelectGesture) handwritingGesture);
        } else if (handwritingGesture instanceof SelectRangeGesture) {
            performHandwritingInsertModeGesture = this.mTextView.performHandwritingSelectRangeGesture((SelectRangeGesture) handwritingGesture);
        } else if (handwritingGesture instanceof DeleteGesture) {
            performHandwritingInsertModeGesture = this.mTextView.performHandwritingDeleteGesture((DeleteGesture) handwritingGesture);
        } else if (handwritingGesture instanceof DeleteRangeGesture) {
            performHandwritingInsertModeGesture = this.mTextView.performHandwritingDeleteRangeGesture((DeleteRangeGesture) handwritingGesture);
        } else if (handwritingGesture instanceof InsertGesture) {
            performHandwritingInsertModeGesture = this.mTextView.performHandwritingInsertGesture((InsertGesture) handwritingGesture);
        } else if (handwritingGesture instanceof RemoveSpaceGesture) {
            performHandwritingInsertModeGesture = this.mTextView.performHandwritingRemoveSpaceGesture((RemoveSpaceGesture) handwritingGesture);
        } else if (handwritingGesture instanceof JoinOrSplitGesture) {
            performHandwritingInsertModeGesture = this.mTextView.performHandwritingJoinOrSplitGesture((JoinOrSplitGesture) handwritingGesture);
        } else {
            performHandwritingInsertModeGesture = handwritingGesture instanceof InsertModeGesture ? this.mTextView.performHandwritingInsertModeGesture((InsertModeGesture) handwritingGesture) : 2;
        }
        if (executor == null || intConsumer == null) {
            return;
        }
        executor.execute(new Runnable() { // from class: com.android.internal.inputmethod.EditableInputConnection$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                intConsumer.accept(performHandwritingInsertModeGesture);
            }
        });
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean previewHandwritingGesture(PreviewableHandwritingGesture previewableHandwritingGesture, CancellationSignal cancellationSignal) {
        return this.mTextView.previewHandwritingGesture(previewableHandwritingGesture, cancellationSignal);
    }

    @Override // android.view.inputmethod.DumpableInputConnection
    public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        Editable editable = getEditable();
        if (editable != null) {
            int selectionStart = Selection.getSelectionStart(editable);
            int selectionEnd = Selection.getSelectionEnd(editable);
            protoOutputStream.write(1120986464259L, selectionStart);
            protoOutputStream.write(1120986464260L, selectionEnd);
        }
        protoOutputStream.write(1120986464261L, getCursorCapsMode(0));
        protoOutputStream.end(start);
    }
}
