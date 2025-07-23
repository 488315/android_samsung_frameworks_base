package android.view.inputmethod;

import android.graphics.RectF;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

/* loaded from: classes4.dex */
public interface InputConnection {
    public static final int CURSOR_UPDATE_FILTER_CHARACTER_BOUNDS = 8;
    public static final int CURSOR_UPDATE_FILTER_EDITOR_BOUNDS = 4;
    public static final int CURSOR_UPDATE_FILTER_INSERTION_MARKER = 16;
    public static final int CURSOR_UPDATE_FILTER_TEXT_APPEARANCE = 64;
    public static final int CURSOR_UPDATE_FILTER_VISIBLE_LINE_BOUNDS = 32;
    public static final int CURSOR_UPDATE_IMMEDIATE = 1;
    public static final int CURSOR_UPDATE_MONITOR = 2;
    public static final int GET_EXTRACTED_TEXT_MONITOR = 1;
    public static final int GET_TEXT_WITH_STYLES = 1;
    public static final int HANDWRITING_GESTURE_RESULT_CANCELLED = 4;
    public static final int HANDWRITING_GESTURE_RESULT_FAILED = 3;
    public static final int HANDWRITING_GESTURE_RESULT_FALLBACK = 5;
    public static final int HANDWRITING_GESTURE_RESULT_SUCCESS = 1;
    public static final int HANDWRITING_GESTURE_RESULT_UNKNOWN = 0;
    public static final int HANDWRITING_GESTURE_RESULT_UNSUPPORTED = 2;
    public static final int INPUT_CONTENT_GRANT_READ_URI_PERMISSION = 1;

    @Retention(RetentionPolicy.SOURCE)
    public @interface CursorUpdateFilter {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface CursorUpdateMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface GetTextType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface HandwritingGestureResult {
    }

    boolean beginBatchEdit();

    boolean clearMetaKeyStates(int i);

    void closeConnection();

    boolean commitCompletion(CompletionInfo completionInfo);

    boolean commitContent(InputContentInfo inputContentInfo, int i, Bundle bundle);

    boolean commitCorrection(CorrectionInfo correctionInfo);

    boolean commitText(CharSequence charSequence, int i);

    boolean deleteSurroundingText(int i, int i2);

    boolean deleteSurroundingTextInCodePoints(int i, int i2);

    boolean endBatchEdit();

    boolean finishComposingText();

    int getCursorCapsMode(int i);

    ExtractedText getExtractedText(ExtractedTextRequest extractedTextRequest, int i);

    Handler getHandler();

    CharSequence getSelectedText(int i);

    CharSequence getTextAfterCursor(int i, int i2);

    CharSequence getTextBeforeCursor(int i, int i2);

    boolean performContextMenuAction(int i);

    boolean performEditorAction(int i);

    boolean performPrivateCommand(String str, Bundle bundle);

    default boolean performSpellCheck() {
        return false;
    }

    default boolean previewHandwritingGesture(PreviewableHandwritingGesture previewableHandwritingGesture, CancellationSignal cancellationSignal) {
        return false;
    }

    boolean reportFullscreenMode(boolean z);

    boolean requestCursorUpdates(int i);

    boolean sendKeyEvent(KeyEvent keyEvent);

    boolean setComposingRegion(int i, int i2);

    boolean setComposingText(CharSequence charSequence, int i);

    default boolean setImeConsumesInput(boolean z) {
        return false;
    }

    boolean setSelection(int i, int i2);

    default TextSnapshot takeSnapshot() {
        return null;
    }

    default SurroundingText getSurroundingText(int i, int i2, int i3) {
        CharSequence textAfterCursor;
        Preconditions.checkArgumentNonnegative(i);
        Preconditions.checkArgumentNonnegative(i2);
        CharSequence textBeforeCursor = getTextBeforeCursor(i, i3);
        if (textBeforeCursor == null || (textAfterCursor = getTextAfterCursor(i2, i3)) == null) {
            return null;
        }
        CharSequence selectedText = getSelectedText(i3);
        if (selectedText == null) {
            selectedText = "";
        }
        return new SurroundingText(TextUtils.concat(textBeforeCursor, selectedText, textAfterCursor), textBeforeCursor.length(), textBeforeCursor.length() + selectedText.length(), -1);
    }

    default boolean setComposingText(CharSequence charSequence, int i, TextAttribute textAttribute) {
        return setComposingText(charSequence, i);
    }

    default boolean setComposingRegion(int i, int i2, TextAttribute textAttribute) {
        return setComposingRegion(i, i2);
    }

    default boolean commitText(CharSequence charSequence, int i, TextAttribute textAttribute) {
        return commitText(charSequence, i);
    }

    default void performHandwritingGesture(HandwritingGesture handwritingGesture, Executor executor, final IntConsumer intConsumer) {
        if (executor == null || intConsumer == null) {
            return;
        }
        executor.execute(new Runnable() { // from class: android.view.inputmethod.InputConnection$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                intConsumer.accept(2);
            }
        });
    }

    default boolean requestCursorUpdates(int i, int i2) {
        if (i2 == 0) {
            return requestCursorUpdates(i);
        }
        return false;
    }

    default void requestTextBoundsInfo(RectF rectF, Executor executor, final Consumer<TextBoundsInfoResult> consumer) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(consumer);
        executor.execute(new Runnable() { // from class: android.view.inputmethod.InputConnection$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                consumer.accept(new TextBoundsInfoResult(0));
            }
        });
    }

    default boolean replaceText(int i, int i2, CharSequence charSequence, int i3, TextAttribute textAttribute) {
        Preconditions.checkArgumentNonnegative(i);
        Preconditions.checkArgumentNonnegative(i2);
        beginBatchEdit();
        finishComposingText();
        setSelection(i, i2);
        commitText(charSequence, i3, textAttribute);
        endBatchEdit();
        return true;
    }
}
