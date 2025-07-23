package androidx.compose.ui.text.input;

import android.R;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.CorrectionInfo;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputContentInfo;
import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import androidx.compose.ui.text.TextRange;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class RecordingInputConnection implements InputConnection {
    public final boolean autoCorrect;
    public int batchDepth;
    public int currentExtractedTextRequestToken;
    public final InputEventCallback2 eventCallback;
    public boolean extractedTextMonitorMode;
    public TextFieldValue mTextFieldValue;
    public final List editCommands = new ArrayList();
    public boolean isActive = true;

    public RecordingInputConnection(TextFieldValue textFieldValue, InputEventCallback2 inputEventCallback2, boolean z) {
        this.eventCallback = inputEventCallback2;
        this.autoCorrect = z;
        this.mTextFieldValue = textFieldValue;
    }

    public final void addEditCommandWithBatch(EditCommand editCommand) {
        this.batchDepth++;
        try {
            ((ArrayList) this.editCommands).add(editCommand);
        } finally {
            endBatchEditInternal();
        }
    }

    @Override // android.view.inputmethod.InputConnection
    public final boolean beginBatchEdit() {
        boolean z = this.isActive;
        if (!z) {
            return z;
        }
        this.batchDepth++;
        return true;
    }

    @Override // android.view.inputmethod.InputConnection
    public final boolean clearMetaKeyStates(int i) {
        boolean z = this.isActive;
        if (z) {
            return false;
        }
        return z;
    }

    @Override // android.view.inputmethod.InputConnection
    public final void closeConnection() {
        ((ArrayList) this.editCommands).clear();
        this.batchDepth = 0;
        this.isActive = false;
        TextInputServiceAndroid textInputServiceAndroid = ((TextInputServiceAndroid$createInputConnection$1) this.eventCallback).this$0;
        int size = ((ArrayList) textInputServiceAndroid.ics).size();
        for (int i = 0; i < size; i++) {
            if (Intrinsics.areEqual(((WeakReference) ((ArrayList) textInputServiceAndroid.ics).get(i)).get(), this)) {
                ((ArrayList) textInputServiceAndroid.ics).remove(i);
                return;
            }
        }
    }

    @Override // android.view.inputmethod.InputConnection
    public final boolean commitCompletion(CompletionInfo completionInfo) {
        boolean z = this.isActive;
        if (z) {
            return false;
        }
        return z;
    }

    @Override // android.view.inputmethod.InputConnection
    public final boolean commitContent(InputContentInfo inputContentInfo, int i, Bundle bundle) {
        boolean z = this.isActive;
        if (z) {
            return false;
        }
        return z;
    }

    @Override // android.view.inputmethod.InputConnection
    public final boolean commitCorrection(CorrectionInfo correctionInfo) {
        boolean z = this.isActive;
        return z ? this.autoCorrect : z;
    }

    @Override // android.view.inputmethod.InputConnection
    public final boolean commitText(CharSequence charSequence, int i) {
        boolean z = this.isActive;
        if (z) {
            addEditCommandWithBatch(new CommitTextCommand(String.valueOf(charSequence), i));
        }
        return z;
    }

    @Override // android.view.inputmethod.InputConnection
    public final boolean deleteSurroundingText(int i, int i2) {
        boolean z = this.isActive;
        if (!z) {
            return z;
        }
        addEditCommandWithBatch(new DeleteSurroundingTextCommand(i, i2));
        return true;
    }

    @Override // android.view.inputmethod.InputConnection
    public final boolean deleteSurroundingTextInCodePoints(int i, int i2) {
        boolean z = this.isActive;
        if (!z) {
            return z;
        }
        addEditCommandWithBatch(new DeleteSurroundingTextInCodePointsCommand(i, i2));
        return true;
    }

    @Override // android.view.inputmethod.InputConnection
    public final boolean endBatchEdit() {
        return endBatchEditInternal();
    }

    /* JADX WARN: Type inference failed for: r0v8, types: [kotlin.jvm.functions.Function1, kotlin.jvm.internal.Lambda] */
    public final boolean endBatchEditInternal() {
        int i = this.batchDepth - 1;
        this.batchDepth = i;
        if (i == 0 && !((ArrayList) this.editCommands).isEmpty()) {
            ((TextInputServiceAndroid$createInputConnection$1) this.eventCallback).this$0.onEditCommand.mo779invoke(new ArrayList(this.editCommands));
            ((ArrayList) this.editCommands).clear();
        }
        return this.batchDepth > 0;
    }

    @Override // android.view.inputmethod.InputConnection
    public final boolean finishComposingText() {
        boolean z = this.isActive;
        if (!z) {
            return z;
        }
        addEditCommandWithBatch(new FinishComposingTextCommand());
        return true;
    }

    @Override // android.view.inputmethod.InputConnection
    public final int getCursorCapsMode(int i) {
        TextFieldValue textFieldValue = this.mTextFieldValue;
        return TextUtils.getCapsMode(textFieldValue.annotatedString.text, TextRange.m750getMinimpl(textFieldValue.selection), i);
    }

    @Override // android.view.inputmethod.InputConnection
    public final ExtractedText getExtractedText(ExtractedTextRequest extractedTextRequest, int i) {
        boolean z = (i & 1) != 0;
        this.extractedTextMonitorMode = z;
        if (z) {
            this.currentExtractedTextRequestToken = extractedTextRequest != null ? extractedTextRequest.token : 0;
        }
        return InputState_androidKt.toExtractedText(this.mTextFieldValue);
    }

    @Override // android.view.inputmethod.InputConnection
    public final Handler getHandler() {
        return null;
    }

    @Override // android.view.inputmethod.InputConnection
    public final CharSequence getSelectedText(int i) {
        if (TextRange.m747getCollapsedimpl(this.mTextFieldValue.selection)) {
            return null;
        }
        return TextFieldValueKt.getSelectedText(this.mTextFieldValue).text;
    }

    @Override // android.view.inputmethod.InputConnection
    public final CharSequence getTextAfterCursor(int i, int i2) {
        return TextFieldValueKt.getTextAfterSelection(this.mTextFieldValue, i).text;
    }

    @Override // android.view.inputmethod.InputConnection
    public final CharSequence getTextBeforeCursor(int i, int i2) {
        return TextFieldValueKt.getTextBeforeSelection(this.mTextFieldValue, i).text;
    }

    @Override // android.view.inputmethod.InputConnection
    public final boolean performContextMenuAction(int i) {
        boolean z = this.isActive;
        if (z) {
            z = false;
            switch (i) {
                case R.id.selectAll:
                    addEditCommandWithBatch(new SetSelectionCommand(0, this.mTextFieldValue.annotatedString.text.length()));
                    break;
                case R.id.cut:
                    sendSynthesizedKeyEvent(IKnoxCustomManager.Stub.TRANSACTION_clearForcedDisplaySizeDensity);
                    return false;
                case R.id.copy:
                    sendSynthesizedKeyEvent(IKnoxCustomManager.Stub.TRANSACTION_startSmartView);
                    return false;
                case R.id.paste:
                    sendSynthesizedKeyEvent(IKnoxCustomManager.Stub.TRANSACTION_setForceSingleView);
                    return false;
                default:
                    return false;
            }
        }
        return z;
    }

    /* JADX WARN: Type inference failed for: r2v4, types: [kotlin.jvm.functions.Function1, kotlin.jvm.internal.Lambda] */
    @Override // android.view.inputmethod.InputConnection
    public final boolean performEditorAction(int i) {
        int i2;
        boolean z = this.isActive;
        if (!z) {
            return z;
        }
        if (i != 0) {
            switch (i) {
                case 2:
                    ImeAction.Companion.getClass();
                    i2 = ImeAction.Go;
                    break;
                case 3:
                    ImeAction.Companion.getClass();
                    i2 = ImeAction.Search;
                    break;
                case 4:
                    ImeAction.Companion.getClass();
                    i2 = ImeAction.Send;
                    break;
                case 5:
                    ImeAction.Companion.getClass();
                    i2 = ImeAction.Next;
                    break;
                case 6:
                    ImeAction.Companion.getClass();
                    i2 = ImeAction.Done;
                    break;
                case 7:
                    ImeAction.Companion.getClass();
                    i2 = ImeAction.Previous;
                    break;
                default:
                    RecordingInputConnection$$ExternalSyntheticOutline0.m(i, "IME sends unsupported Editor Action: ", "RecordingIC");
                    ImeAction.Companion.getClass();
                    i2 = ImeAction.Default;
                    break;
            }
        } else {
            ImeAction.Companion.getClass();
            i2 = ImeAction.Default;
        }
        ((TextInputServiceAndroid$createInputConnection$1) this.eventCallback).this$0.onImeActionPerformed.mo779invoke(ImeAction.m772boximpl(i2));
        return true;
    }

    @Override // android.view.inputmethod.InputConnection
    public final boolean performPrivateCommand(String str, Bundle bundle) {
        boolean z = this.isActive;
        if (z) {
            return true;
        }
        return z;
    }

    @Override // android.view.inputmethod.InputConnection
    public final boolean reportFullscreenMode(boolean z) {
        return false;
    }

    @Override // android.view.inputmethod.InputConnection
    public final boolean requestCursorUpdates(int i) {
        boolean z = this.isActive;
        if (!z) {
            return z;
        }
        boolean z2 = (i & 1) != 0;
        boolean z3 = (i & 2) != 0;
        boolean z4 = (i & 16) != 0;
        boolean z5 = (i & 8) != 0;
        boolean z6 = (i & 4) != 0;
        boolean z7 = (i & 32) != 0;
        if (!z4 && !z5 && !z6 && !z7) {
            z7 = true;
            z4 = true;
            z5 = true;
            z6 = true;
        }
        CursorAnchorInfoController cursorAnchorInfoController = ((TextInputServiceAndroid$createInputConnection$1) this.eventCallback).this$0.cursorAnchorInfoController;
        synchronized (cursorAnchorInfoController.lock) {
            try {
                cursorAnchorInfoController.includeInsertionMarker = z4;
                cursorAnchorInfoController.includeCharacterBounds = z5;
                cursorAnchorInfoController.includeEditorBounds = z6;
                cursorAnchorInfoController.includeLineBounds = z7;
                if (z2) {
                    cursorAnchorInfoController.hasPendingImmediateRequest = true;
                    if (cursorAnchorInfoController.textFieldValue != null) {
                        cursorAnchorInfoController.updateCursorAnchorInfo();
                    }
                }
                cursorAnchorInfoController.monitorEnabled = z3;
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
        return true;
    }

    @Override // android.view.inputmethod.InputConnection
    public final boolean sendKeyEvent(KeyEvent keyEvent) {
        boolean z = this.isActive;
        if (!z) {
            return z;
        }
        ((BaseInputConnection) ((TextInputServiceAndroid$createInputConnection$1) this.eventCallback).this$0.baseInputConnection$delegate.getValue()).sendKeyEvent(keyEvent);
        return true;
    }

    public final void sendSynthesizedKeyEvent(int i) {
        sendKeyEvent(new KeyEvent(0, i));
        sendKeyEvent(new KeyEvent(1, i));
    }

    @Override // android.view.inputmethod.InputConnection
    public final boolean setComposingRegion(int i, int i2) {
        boolean z = this.isActive;
        if (z) {
            addEditCommandWithBatch(new SetComposingRegionCommand(i, i2));
        }
        return z;
    }

    @Override // android.view.inputmethod.InputConnection
    public final boolean setComposingText(CharSequence charSequence, int i) {
        boolean z = this.isActive;
        if (z) {
            addEditCommandWithBatch(new SetComposingTextCommand(String.valueOf(charSequence), i));
        }
        return z;
    }

    @Override // android.view.inputmethod.InputConnection
    public final boolean setSelection(int i, int i2) {
        boolean z = this.isActive;
        if (!z) {
            return z;
        }
        addEditCommandWithBatch(new SetSelectionCommand(i, i2));
        return true;
    }
}
