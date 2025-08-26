package android.view.inputmethod;

import android.content.ClipData;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.MetaKeyKeyListener;
import android.util.Log;
import android.view.ContentInfo;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import com.android.internal.util.Preconditions;

/* loaded from: classes4.dex */
public class BaseInputConnection implements InputConnection {
    private static final boolean DEBUG = false;
    private static final String TAG = "BaseInputConnection";
    private Object[] mDefaultComposingSpans;
    Editable mEditable;
    final boolean mFallbackMode;
    protected final InputMethodManager mIMM;
    KeyCharacterMap mKeyCharacterMap;
    final View mTargetView;
    static final Object COMPOSING = new ComposingText();
    private static int INVALID_INDEX = -1;

    @Override // android.view.inputmethod.InputConnection
    public boolean beginBatchEdit() {
        return false;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean commitCompletion(CompletionInfo completionInfo) {
        return false;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean commitCorrection(CorrectionInfo correctionInfo) {
        return false;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean endBatchEdit() {
        return false;
    }

    public void endComposingRegionEditInternal() {
    }

    @Override // android.view.inputmethod.InputConnection
    public ExtractedText getExtractedText(ExtractedTextRequest extractedTextRequest, int i) {
        return null;
    }

    @Override // android.view.inputmethod.InputConnection
    public Handler getHandler() {
        return null;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean performContextMenuAction(int i) {
        return false;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean performPrivateCommand(String str, Bundle bundle) {
        return false;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean reportFullscreenMode(boolean z) {
        return true;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean requestCursorUpdates(int i) {
        return false;
    }

    BaseInputConnection(InputMethodManager inputMethodManager, boolean z) {
        this.mIMM = inputMethodManager;
        this.mTargetView = null;
        this.mFallbackMode = !z;
    }

    public BaseInputConnection(View view, boolean z) {
        this.mIMM = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        this.mTargetView = view;
        this.mFallbackMode = !z;
    }

    public static final void removeComposingSpans(Spannable spannable) {
        spannable.removeSpan(COMPOSING);
        Object[] spans = spannable.getSpans(0, spannable.length(), Object.class);
        if (spans != null) {
            for (int length = spans.length - 1; length >= 0; length--) {
                Object obj = spans[length];
                if ((spannable.getSpanFlags(obj) & 256) != 0) {
                    spannable.removeSpan(obj);
                }
            }
        }
    }

    public static void setComposingSpans(Spannable spannable) {
        setComposingSpans(spannable, 0, spannable.length());
    }

    public static void setComposingSpans(Spannable spannable, int i, int i2) {
        Object[] spans = spannable.getSpans(i, i2, Object.class);
        if (spans != null) {
            for (int length = spans.length - 1; length >= 0; length--) {
                Object obj = spans[length];
                if (obj == COMPOSING) {
                    spannable.removeSpan(obj);
                } else {
                    int spanFlags = spannable.getSpanFlags(obj);
                    if ((spanFlags & 307) != 289) {
                        spannable.setSpan(obj, spannable.getSpanStart(obj), spannable.getSpanEnd(obj), (spanFlags & (-52)) | 289);
                    }
                }
            }
        }
        spannable.setSpan(COMPOSING, i, i2, 289);
    }

    public static int getComposingSpanStart(Spannable spannable) {
        return spannable.getSpanStart(COMPOSING);
    }

    public static int getComposingSpanEnd(Spannable spannable) {
        return spannable.getSpanEnd(COMPOSING);
    }

    public Editable getEditable() {
        if (this.mEditable == null) {
            Editable editableNewEditable = Editable.Factory.getInstance().newEditable("");
            this.mEditable = editableNewEditable;
            Selection.setSelection(editableNewEditable, 0);
        }
        return this.mEditable;
    }

    @Override // android.view.inputmethod.InputConnection
    public void closeConnection() {
        finishComposingText();
        setImeConsumesInput(false);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean clearMetaKeyStates(int i) {
        Editable editable = getEditable();
        if (editable == null) {
            return false;
        }
        MetaKeyKeyListener.clearMetaKeyState(editable, i);
        return true;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean commitText(CharSequence charSequence, int i) {
        replaceText(charSequence, i, false);
        sendCurrentText();
        return true;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean deleteSurroundingText(int i, int i2) {
        Editable editable = getEditable();
        int i3 = 0;
        if (editable == null) {
            return false;
        }
        beginBatchEdit();
        int selectionStart = Selection.getSelectionStart(editable);
        int selectionEnd = Selection.getSelectionEnd(editable);
        if (selectionStart > selectionEnd) {
            selectionEnd = selectionStart;
            selectionStart = selectionEnd;
        }
        if (selectionStart == -1 || selectionEnd == -1) {
            endBatchEdit();
            return false;
        }
        int composingSpanStart = getComposingSpanStart(editable);
        int composingSpanEnd = getComposingSpanEnd(editable);
        if (composingSpanEnd < composingSpanStart) {
            composingSpanEnd = composingSpanStart;
            composingSpanStart = composingSpanEnd;
        }
        if (composingSpanStart != -1 && composingSpanEnd != -1) {
            if (composingSpanStart < selectionStart) {
                selectionStart = composingSpanStart;
            }
            if (composingSpanEnd > selectionEnd) {
                selectionEnd = composingSpanEnd;
            }
        }
        if (i > 0) {
            int i4 = selectionStart - i;
            if (i4 < 0) {
                i4 = 0;
            }
            int i5 = selectionStart - i4;
            if (selectionStart >= 0 && i5 > 0) {
                editable.delete(i4, selectionStart);
                i3 = i5;
            }
        }
        if (i2 > 0) {
            int i6 = selectionEnd - i3;
            int length = i2 + i6;
            if (length > editable.length()) {
                length = editable.length();
            }
            int i7 = length - i6;
            if (i6 >= 0 && i7 > 0) {
                editable.delete(i6, length);
            }
        }
        endBatchEdit();
        return true;
    }

    private static int findIndexBackward(CharSequence charSequence, int i, int i2) {
        int length = charSequence.length();
        if (i < 0 || length < i) {
            return INVALID_INDEX;
        }
        if (i2 < 0) {
            return INVALID_INDEX;
        }
        while (true) {
            boolean z = false;
            while (i2 != 0) {
                i--;
                if (i < 0) {
                    if (z) {
                        return INVALID_INDEX;
                    }
                    return 0;
                }
                char cCharAt = charSequence.charAt(i);
                if (z) {
                    if (!Character.isHighSurrogate(cCharAt)) {
                        return INVALID_INDEX;
                    }
                    i2--;
                } else if (!Character.isSurrogate(cCharAt)) {
                    i2--;
                } else {
                    if (Character.isHighSurrogate(cCharAt)) {
                        return INVALID_INDEX;
                    }
                    z = true;
                }
            }
            return i;
        }
    }

    private static int findIndexForward(CharSequence charSequence, int i, int i2) {
        int length = charSequence.length();
        if (i < 0 || length < i) {
            return INVALID_INDEX;
        }
        if (i2 < 0) {
            return INVALID_INDEX;
        }
        while (true) {
            boolean z = false;
            while (i2 != 0) {
                if (i >= length) {
                    return z ? INVALID_INDEX : length;
                }
                char cCharAt = charSequence.charAt(i);
                if (z) {
                    if (!Character.isLowSurrogate(cCharAt)) {
                        return INVALID_INDEX;
                    }
                    i2--;
                    i++;
                } else if (!Character.isSurrogate(cCharAt)) {
                    i2--;
                    i++;
                } else {
                    if (Character.isLowSurrogate(cCharAt)) {
                        return INVALID_INDEX;
                    }
                    i++;
                    z = true;
                }
            }
            return i;
        }
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean deleteSurroundingTextInCodePoints(int i, int i2) {
        int iFindIndexBackward;
        int iFindIndexForward;
        Editable editable = getEditable();
        if (editable == null) {
            return false;
        }
        beginBatchEdit();
        int selectionStart = Selection.getSelectionStart(editable);
        int selectionEnd = Selection.getSelectionEnd(editable);
        if (selectionStart > selectionEnd) {
            selectionEnd = selectionStart;
            selectionStart = selectionEnd;
        }
        int composingSpanStart = getComposingSpanStart(editable);
        int composingSpanEnd = getComposingSpanEnd(editable);
        if (composingSpanEnd < composingSpanStart) {
            composingSpanEnd = composingSpanStart;
            composingSpanStart = composingSpanEnd;
        }
        if (composingSpanStart != -1 && composingSpanEnd != -1) {
            if (composingSpanStart < selectionStart) {
                selectionStart = composingSpanStart;
            }
            if (composingSpanEnd > selectionEnd) {
                selectionEnd = composingSpanEnd;
            }
        }
        if (selectionStart >= 0 && selectionEnd >= 0 && (iFindIndexBackward = findIndexBackward(editable, selectionStart, Math.max(i, 0))) != INVALID_INDEX && (iFindIndexForward = findIndexForward(editable, selectionEnd, Math.max(i2, 0))) != INVALID_INDEX) {
            int i3 = selectionStart - iFindIndexBackward;
            if (i3 > 0) {
                editable.delete(iFindIndexBackward, selectionStart);
            }
            if (iFindIndexForward - selectionEnd > 0) {
                editable.delete(selectionEnd - i3, iFindIndexForward - i3);
            }
        }
        endBatchEdit();
        return true;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean finishComposingText() {
        Editable editable = getEditable();
        if (editable == null) {
            return true;
        }
        beginBatchEdit();
        removeComposingSpans(editable);
        sendCurrentText();
        endBatchEdit();
        endComposingRegionEditInternal();
        return true;
    }

    @Override // android.view.inputmethod.InputConnection
    public int getCursorCapsMode(int i) {
        Editable editable;
        if (this.mFallbackMode || (editable = getEditable()) == null) {
            return 0;
        }
        int selectionStart = Selection.getSelectionStart(editable);
        int selectionEnd = Selection.getSelectionEnd(editable);
        if (selectionStart > selectionEnd) {
            selectionStart = selectionEnd;
        }
        return TextUtils.getCapsMode(editable, selectionStart, i);
    }

    @Override // android.view.inputmethod.InputConnection
    public CharSequence getTextBeforeCursor(int i, int i2) {
        Preconditions.checkArgumentNonnegative(i);
        Editable editable = getEditable();
        if (editable == null) {
            return null;
        }
        int selectionStart = Selection.getSelectionStart(editable);
        int selectionEnd = Selection.getSelectionEnd(editable);
        if (selectionStart > selectionEnd) {
            selectionStart = selectionEnd;
        }
        if (selectionStart <= 0) {
            return "";
        }
        if (i > selectionStart) {
            i = selectionStart;
        }
        if ((i2 & 1) != 0) {
            return editable.subSequence(selectionStart - i, selectionStart);
        }
        return TextUtils.substring(editable, selectionStart - i, selectionStart);
    }

    @Override // android.view.inputmethod.InputConnection
    public CharSequence getSelectedText(int i) {
        Editable editable = getEditable();
        if (editable == null) {
            return null;
        }
        int selectionStart = Selection.getSelectionStart(editable);
        int selectionEnd = Selection.getSelectionEnd(editable);
        if (selectionStart > selectionEnd) {
            selectionEnd = selectionStart;
            selectionStart = selectionEnd;
        }
        if (selectionStart == selectionEnd || selectionStart < 0) {
            return null;
        }
        if ((i & 1) != 0) {
            return editable.subSequence(selectionStart, selectionEnd);
        }
        return TextUtils.substring(editable, selectionStart, selectionEnd);
    }

    @Override // android.view.inputmethod.InputConnection
    public CharSequence getTextAfterCursor(int i, int i2) {
        Preconditions.checkArgumentNonnegative(i);
        Editable editable = getEditable();
        if (editable == null) {
            return null;
        }
        int selectionStart = Selection.getSelectionStart(editable);
        int selectionEnd = Selection.getSelectionEnd(editable);
        if (selectionStart <= selectionEnd) {
            selectionStart = selectionEnd;
        }
        if (selectionStart < 0) {
            selectionStart = 0;
        }
        int iMin = (int) Math.min(selectionStart + i, editable.length());
        if ((i2 & 1) != 0) {
            return editable.subSequence(selectionStart, iMin);
        }
        return TextUtils.substring(editable, selectionStart, iMin);
    }

    @Override // android.view.inputmethod.InputConnection
    public SurroundingText getSurroundingText(int i, int i2, int i3) {
        CharSequence charSequenceSubstring;
        Preconditions.checkArgumentNonnegative(i);
        Preconditions.checkArgumentNonnegative(i2);
        Editable editable = getEditable();
        if (editable == null || this.mEditable == editable) {
            return super.getSurroundingText(i, i2, i3);
        }
        int selectionStart = Selection.getSelectionStart(editable);
        int selectionEnd = Selection.getSelectionEnd(editable);
        if (selectionStart < 0 || selectionEnd < 0) {
            return null;
        }
        if (selectionStart > selectionEnd) {
            selectionEnd = selectionStart;
            selectionStart = selectionEnd;
        }
        int iMax = Math.max(0, selectionStart - i);
        int iMin = (int) Math.min(selectionEnd + i2, editable.length());
        if ((i3 & 1) != 0) {
            charSequenceSubstring = editable.subSequence(iMax, iMin);
        } else {
            charSequenceSubstring = TextUtils.substring(editable, iMax, iMin);
        }
        return new SurroundingText(charSequenceSubstring, selectionStart - iMax, selectionEnd - iMax, iMax);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean performEditorAction(int i) {
        long jUptimeMillis = SystemClock.uptimeMillis();
        sendKeyEvent(new KeyEvent(jUptimeMillis, jUptimeMillis, 0, 66, 0, 0, -1, 0, 22));
        sendKeyEvent(new KeyEvent(SystemClock.uptimeMillis(), jUptimeMillis, 1, 66, 0, 0, -1, 0, 22));
        return true;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean setComposingText(CharSequence charSequence, int i) {
        replaceText(charSequence, i, true);
        return true;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean setComposingRegion(int i, int i2) {
        Editable editable = getEditable();
        if (editable == null) {
            return true;
        }
        beginBatchEdit();
        removeComposingSpans(editable);
        if (i > i2) {
            i2 = i;
            i = i2;
        }
        int length = editable.length();
        int i3 = 0;
        if (i < 0) {
            i = 0;
        }
        if (i2 < 0) {
            i2 = 0;
        }
        if (i > length) {
            i = length;
        }
        if (i2 <= length) {
            length = i2;
        }
        ensureDefaultComposingSpans();
        if (this.mDefaultComposingSpans != null) {
            while (true) {
                Object[] objArr = this.mDefaultComposingSpans;
                if (i3 >= objArr.length) {
                    break;
                }
                editable.setSpan(objArr[i3], i, length, 289);
                i3++;
            }
        }
        editable.setSpan(COMPOSING, i, length, 289);
        sendCurrentText();
        endBatchEdit();
        endComposingRegionEditInternal();
        return true;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean setSelection(int i, int i2) {
        Editable editable = getEditable();
        if (editable == null) {
            return false;
        }
        int length = editable.length();
        if (i <= length && i2 <= length && i >= 0 && i2 >= 0) {
            if (i == i2 && MetaKeyKeyListener.getMetaState(editable, 2048) != 0) {
                Selection.extendSelection(editable, i);
            } else {
                Selection.setSelection(editable, i, i2);
            }
        }
        return true;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean sendKeyEvent(KeyEvent keyEvent) {
        this.mIMM.dispatchKeyEventFromInputMethod(this.mTargetView, keyEvent);
        return false;
    }

    private void sendCurrentText() {
        Editable editable;
        int length;
        if (!this.mFallbackMode || (editable = getEditable()) == null || (length = editable.length()) == 0) {
            return;
        }
        if (length == 1) {
            if (this.mKeyCharacterMap == null) {
                this.mKeyCharacterMap = KeyCharacterMap.load(-1);
            }
            char[] cArr = new char[1];
            editable.getChars(0, 1, cArr, 0);
            KeyEvent[] events = this.mKeyCharacterMap.getEvents(cArr);
            if (events != null) {
                for (KeyEvent keyEvent : events) {
                    sendKeyEvent(keyEvent);
                }
                editable.clear();
                return;
            }
        }
        sendKeyEvent(new KeyEvent(SystemClock.uptimeMillis(), editable.toString(), -1, 0));
        editable.clear();
    }

    private void ensureDefaultComposingSpans() {
        Context fallbackContextFromServedView;
        if (this.mDefaultComposingSpans == null) {
            View view = this.mTargetView;
            if (view != null) {
                fallbackContextFromServedView = view.getContext();
            } else {
                fallbackContextFromServedView = this.mIMM.getFallbackContextFromServedView();
            }
            if (fallbackContextFromServedView != null) {
                TypedArray typedArrayObtainStyledAttributes = fallbackContextFromServedView.getTheme().obtainStyledAttributes(new int[]{16843312});
                CharSequence text = typedArrayObtainStyledAttributes.getText(0);
                typedArrayObtainStyledAttributes.recycle();
                if (text == null || !(text instanceof Spanned)) {
                    return;
                }
                this.mDefaultComposingSpans = ((Spanned) text).getSpans(0, text.length(), Object.class);
            }
        }
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean replaceText(int i, int i2, CharSequence charSequence, int i3, TextAttribute textAttribute) {
        int i4;
        int i5;
        Preconditions.checkArgumentNonnegative(i);
        Preconditions.checkArgumentNonnegative(i2);
        Editable editable = getEditable();
        if (editable == null) {
            return false;
        }
        beginBatchEdit();
        removeComposingSpans(editable);
        int length = editable.length();
        int iMin = Math.min(i, length);
        int iMin2 = Math.min(i2, length);
        if (iMin2 < iMin) {
            i5 = iMin;
            i4 = iMin2;
        } else {
            i4 = iMin;
            i5 = iMin2;
        }
        replaceTextInternal(i4, i5, charSequence, i3, false);
        endBatchEdit();
        return true;
    }

    private void replaceText(CharSequence charSequence, int i, boolean z) {
        BaseInputConnection baseInputConnection;
        CharSequence charSequence2;
        int i2;
        boolean z2;
        int i3;
        int i4;
        Editable editable = getEditable();
        if (editable == null) {
            return;
        }
        beginBatchEdit();
        int composingSpanStart = getComposingSpanStart(editable);
        int composingSpanEnd = getComposingSpanEnd(editable);
        if (composingSpanEnd < composingSpanStart) {
            composingSpanEnd = composingSpanStart;
            composingSpanStart = composingSpanEnd;
        }
        if (composingSpanStart != -1 && composingSpanEnd != -1) {
            removeComposingSpans(editable);
            charSequence2 = charSequence;
            i2 = i;
            z2 = z;
            i4 = composingSpanStart;
            i3 = composingSpanEnd;
            baseInputConnection = this;
        } else {
            int selectionStart = Selection.getSelectionStart(editable);
            int selectionEnd = Selection.getSelectionEnd(editable);
            if (selectionStart < 0) {
                selectionStart = 0;
            }
            if (selectionEnd < 0) {
                selectionEnd = 0;
            }
            baseInputConnection = this;
            charSequence2 = charSequence;
            i2 = i;
            z2 = z;
            if (selectionEnd < selectionStart) {
                i4 = selectionEnd;
                i3 = selectionStart;
            } else {
                i3 = selectionEnd;
                i4 = selectionStart;
            }
        }
        baseInputConnection.replaceTextInternal(i4, i3, charSequence2, i2, z2);
        baseInputConnection.endBatchEdit();
    }

    private void replaceTextInternal(int i, int i2, CharSequence charSequence, int i3, boolean z) {
        Spannable spannableStringBuilder;
        Editable editable = getEditable();
        if (editable == null) {
            return;
        }
        if (z) {
            if (!(charSequence instanceof Spannable)) {
                spannableStringBuilder = new SpannableStringBuilder(charSequence);
                ensureDefaultComposingSpans();
                if (this.mDefaultComposingSpans != null) {
                    int i4 = 0;
                    while (true) {
                        Object[] objArr = this.mDefaultComposingSpans;
                        if (i4 >= objArr.length) {
                            break;
                        }
                        spannableStringBuilder.setSpan(objArr[i4], 0, spannableStringBuilder.length(), 289);
                        i4++;
                    }
                }
                charSequence = spannableStringBuilder;
            } else {
                spannableStringBuilder = (Spannable) charSequence;
            }
            setComposingSpans(spannableStringBuilder);
        }
        int i5 = i3 > 0 ? (i2 - 1) + i3 : i3 + i;
        int length = i5 >= 0 ? i5 : 0;
        if (length > editable.length()) {
            length = editable.length();
        }
        Selection.setSelection(editable, length);
        editable.replace(i, i2, SemBaseInputConnectionUtil.convertAllBrackets(charSequence, length, editable, this.mTargetView));
        if (i3 == 0 && i == i2) {
            Selection.setSelection(editable, length);
        }
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean commitContent(InputContentInfo inputContentInfo, int i, Bundle bundle) {
        if (this.mTargetView == null) {
            return false;
        }
        inputContentInfo.getDescription();
        if (this.mTargetView.getReceiveContentMimeTypes() == null) {
            return false;
        }
        if ((i & 1) != 0) {
            try {
                inputContentInfo.requestPermission();
            } catch (Exception e) {
                Log.w(TAG, "Can't insert content from IME; requestPermission() failed", e);
                return false;
            }
        }
        return this.mTargetView.performReceiveContent(new ContentInfo.Builder(new ClipData(inputContentInfo.getDescription(), new ClipData.Item(inputContentInfo.getContentUri())), 2).setLinkUri(inputContentInfo.getLinkUri()).setExtras(bundle).setInputContentInfo(inputContentInfo).build()) == null;
    }

    @Override // android.view.inputmethod.InputConnection
    public TextSnapshot takeSnapshot() {
        Editable editable = getEditable();
        if (editable == null) {
            return null;
        }
        int composingSpanStart = getComposingSpanStart(editable);
        int composingSpanEnd = getComposingSpanEnd(editable);
        if (composingSpanEnd < composingSpanStart) {
            composingSpanStart = composingSpanEnd;
            composingSpanEnd = composingSpanStart;
        }
        SurroundingText surroundingText = getSurroundingText(1024, 1024, 1);
        if (surroundingText == null) {
            return null;
        }
        return new TextSnapshot(surroundingText, composingSpanStart, composingSpanEnd, getCursorCapsMode(28672));
    }
}
