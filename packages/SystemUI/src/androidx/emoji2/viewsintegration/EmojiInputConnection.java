package androidx.emoji2.viewsintegration;

import android.text.Editable;
import android.text.Selection;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.widget.TextView;
import androidx.emoji2.text.EmojiCompat;
import androidx.emoji2.text.EmojiSpan;

/* loaded from: classes.dex */
public final class EmojiInputConnection extends InputConnectionWrapper {
    public final EmojiCompatDeleteHelper mEmojiCompatDeleteHelper;
    public final TextView mTextView;

    public class EmojiCompatDeleteHelper {
        /* JADX WARN: Code restructure failed: missing block: B:31:0x0045, code lost:
        
            if (java.lang.Character.isHighSurrogate(r5) != false) goto L33;
         */
        /* JADX WARN: Code restructure failed: missing block: B:56:0x0082, code lost:
        
            if (java.lang.Character.isLowSurrogate(r5) != false) goto L58;
         */
        /* JADX WARN: Removed duplicated region for block: B:46:0x006c A[EDGE_INSN: B:92:0x006c->B:46:0x006c BREAK  A[LOOP:2: B:47:0x006e->B:58:0x0085], EDGE_INSN: B:93:0x006c->B:46:0x006c BREAK  A[LOOP:2: B:47:0x006e->B:58:0x0085, LOOP_LABEL: LOOP:2: B:47:0x006e->B:58:0x0085]] */
        /* JADX WARN: Removed duplicated region for block: B:67:0x00a2 A[ADDED_TO_REGION] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public static boolean handleDeleteSurroundingText(EmojiInputConnection emojiInputConnection, Editable editable, int i, int i2, boolean z) {
            int iMin;
            if (editable != null && i >= 0 && i2 >= 0) {
                int selectionStart = Selection.getSelectionStart(editable);
                int selectionEnd = Selection.getSelectionEnd(editable);
                if (selectionStart != -1 && selectionEnd != -1 && selectionStart == selectionEnd) {
                    if (z) {
                        int iMax = Math.max(i, 0);
                        int length = editable.length();
                        if (selectionStart < 0 || length < selectionStart || iMax < 0) {
                            selectionStart = -1;
                            int iMax2 = Math.max(i2, 0);
                            iMin = editable.length();
                            if (selectionEnd >= 0 || iMin < selectionEnd || iMax2 < 0) {
                                iMin = -1;
                                if (selectionStart != -1 && iMin != -1) {
                                }
                            } else {
                                loop2: while (true) {
                                    boolean z2 = false;
                                    while (true) {
                                        if (iMax2 == 0) {
                                            iMin = selectionEnd;
                                            break loop2;
                                        }
                                        if (selectionEnd >= iMin) {
                                            if (z2) {
                                                break;
                                            }
                                        } else {
                                            char cCharAt = editable.charAt(selectionEnd);
                                            if (z2) {
                                                break;
                                            }
                                            if (!Character.isSurrogate(cCharAt)) {
                                                iMax2--;
                                                selectionEnd++;
                                            } else {
                                                if (Character.isLowSurrogate(cCharAt)) {
                                                    break loop2;
                                                }
                                                selectionEnd++;
                                                z2 = true;
                                            }
                                        }
                                    }
                                    iMax2--;
                                    selectionEnd++;
                                }
                                iMin = -1;
                                if (selectionStart != -1) {
                                }
                            }
                        } else {
                            loop0: while (true) {
                                boolean z3 = false;
                                while (true) {
                                    if (iMax == 0) {
                                        break loop0;
                                    }
                                    selectionStart--;
                                    if (selectionStart >= 0) {
                                        char cCharAt2 = editable.charAt(selectionStart);
                                        if (z3) {
                                            break;
                                        }
                                        if (!Character.isSurrogate(cCharAt2)) {
                                            iMax--;
                                        } else {
                                            if (Character.isHighSurrogate(cCharAt2)) {
                                                break loop0;
                                            }
                                            z3 = true;
                                        }
                                    } else {
                                        if (z3) {
                                            break loop0;
                                        }
                                        selectionStart = 0;
                                    }
                                }
                                iMax--;
                            }
                            selectionStart = -1;
                            int iMax22 = Math.max(i2, 0);
                            iMin = editable.length();
                            if (selectionEnd >= 0) {
                                iMin = -1;
                                if (selectionStart != -1) {
                                }
                            }
                        }
                    } else {
                        selectionStart = Math.max(selectionStart - i, 0);
                        iMin = Math.min(selectionEnd + i2, editable.length());
                    }
                    EmojiSpan[] emojiSpanArr = (EmojiSpan[]) editable.getSpans(selectionStart, iMin, EmojiSpan.class);
                    if (emojiSpanArr != null && emojiSpanArr.length > 0) {
                        for (EmojiSpan emojiSpan : emojiSpanArr) {
                            int spanStart = editable.getSpanStart(emojiSpan);
                            int spanEnd = editable.getSpanEnd(emojiSpan);
                            selectionStart = Math.min(spanStart, selectionStart);
                            iMin = Math.max(spanEnd, iMin);
                        }
                        int iMax3 = Math.max(selectionStart, 0);
                        int iMin2 = Math.min(iMin, editable.length());
                        emojiInputConnection.beginBatchEdit();
                        editable.delete(iMax3, iMin2);
                        emojiInputConnection.endBatchEdit();
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public EmojiInputConnection(TextView textView, InputConnection inputConnection, EditorInfo editorInfo) {
        this(textView, inputConnection, editorInfo, new EmojiCompatDeleteHelper());
    }

    @Override // android.view.inputmethod.InputConnectionWrapper, android.view.inputmethod.InputConnection
    public final boolean deleteSurroundingText(int i, int i2) {
        EmojiCompatDeleteHelper emojiCompatDeleteHelper = this.mEmojiCompatDeleteHelper;
        Editable editableText = this.mTextView.getEditableText();
        emojiCompatDeleteHelper.getClass();
        return EmojiCompatDeleteHelper.handleDeleteSurroundingText(this, editableText, i, i2, false) || super.deleteSurroundingText(i, i2);
    }

    @Override // android.view.inputmethod.InputConnectionWrapper, android.view.inputmethod.InputConnection
    public final boolean deleteSurroundingTextInCodePoints(int i, int i2) {
        EmojiCompatDeleteHelper emojiCompatDeleteHelper = this.mEmojiCompatDeleteHelper;
        Editable editableText = this.mTextView.getEditableText();
        emojiCompatDeleteHelper.getClass();
        return EmojiCompatDeleteHelper.handleDeleteSurroundingText(this, editableText, i, i2, true) || super.deleteSurroundingTextInCodePoints(i, i2);
    }

    public EmojiInputConnection(TextView textView, InputConnection inputConnection, EditorInfo editorInfo, EmojiCompatDeleteHelper emojiCompatDeleteHelper) {
        super(inputConnection, false);
        this.mTextView = textView;
        this.mEmojiCompatDeleteHelper = emojiCompatDeleteHelper;
        emojiCompatDeleteHelper.getClass();
        if (EmojiCompat.isConfigured()) {
            EmojiCompat.get().updateEditorInfo(editorInfo);
        }
    }
}
