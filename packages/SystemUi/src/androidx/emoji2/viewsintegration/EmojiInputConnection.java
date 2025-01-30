package androidx.emoji2.viewsintegration;

import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.widget.TextView;
import androidx.emoji2.text.EmojiCompat;
import androidx.emoji2.text.EmojiSpan;
import androidx.emoji2.text.flatbuffer.MetadataList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class EmojiInputConnection extends InputConnectionWrapper {
    public final EmojiCompatDeleteHelper mEmojiCompatDeleteHelper;
    public final TextView mTextView;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class EmojiCompatDeleteHelper {
        /* JADX WARN: Code restructure failed: missing block: B:37:0x0050, code lost:
        
            if (java.lang.Character.isHighSurrogate(r5) != false) goto L38;
         */
        /* JADX WARN: Code restructure failed: missing block: B:66:0x008d, code lost:
        
            if (java.lang.Character.isLowSurrogate(r5) != false) goto L64;
         */
        /* JADX WARN: Code restructure failed: missing block: B:70:0x0080, code lost:
        
            if (r11 != false) goto L72;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public static boolean handleDeleteSurroundingText(InputConnection inputConnection, Editable editable, int i, int i2, boolean z) {
            int min;
            Object obj = EmojiCompat.INSTANCE_LOCK;
            if (editable == null || inputConnection == null || i < 0 || i2 < 0) {
                return false;
            }
            int selectionStart = Selection.getSelectionStart(editable);
            int selectionEnd = Selection.getSelectionEnd(editable);
            if (selectionStart == -1 || selectionEnd == -1 || selectionStart != selectionEnd) {
                return false;
            }
            if (z) {
                int max = Math.max(i, 0);
                int length = editable.length();
                if (selectionStart >= 0 && length >= selectionStart && max >= 0) {
                    loop0: while (true) {
                        boolean z2 = false;
                        while (true) {
                            if (max == 0) {
                                break loop0;
                            }
                            selectionStart--;
                            if (selectionStart >= 0) {
                                char charAt = editable.charAt(selectionStart);
                                if (z2) {
                                    break;
                                }
                                if (!Character.isSurrogate(charAt)) {
                                    max--;
                                } else {
                                    if (Character.isHighSurrogate(charAt)) {
                                        break loop0;
                                    }
                                    z2 = true;
                                }
                            } else if (!z2) {
                                selectionStart = 0;
                            }
                        }
                        max--;
                    }
                }
                selectionStart = -1;
                int max2 = Math.max(i2, 0);
                min = editable.length();
                if (selectionEnd >= 0 && min >= selectionEnd && max2 >= 0) {
                    loop2: while (true) {
                        boolean z3 = false;
                        while (true) {
                            if (max2 == 0) {
                                min = selectionEnd;
                                break loop2;
                            }
                            if (selectionEnd < min) {
                                char charAt2 = editable.charAt(selectionEnd);
                                if (z3) {
                                    break;
                                }
                                if (!Character.isSurrogate(charAt2)) {
                                    max2--;
                                    selectionEnd++;
                                } else {
                                    if (Character.isLowSurrogate(charAt2)) {
                                        break loop2;
                                    }
                                    selectionEnd++;
                                    z3 = true;
                                }
                            }
                        }
                        max2--;
                        selectionEnd++;
                    }
                }
                min = -1;
                if (selectionStart == -1 || min == -1) {
                    return false;
                }
            } else {
                selectionStart = Math.max(selectionStart - i, 0);
                min = Math.min(selectionEnd + i2, editable.length());
            }
            EmojiSpan[] emojiSpanArr = (EmojiSpan[]) editable.getSpans(selectionStart, min, EmojiSpan.class);
            if (emojiSpanArr == null || emojiSpanArr.length <= 0) {
                return false;
            }
            for (EmojiSpan emojiSpan : emojiSpanArr) {
                int spanStart = editable.getSpanStart(emojiSpan);
                int spanEnd = editable.getSpanEnd(emojiSpan);
                selectionStart = Math.min(spanStart, selectionStart);
                min = Math.max(spanEnd, min);
            }
            int max3 = Math.max(selectionStart, 0);
            int min2 = Math.min(min, editable.length());
            inputConnection.beginBatchEdit();
            editable.delete(max3, min2);
            inputConnection.endBatchEdit();
            return true;
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
        if (EmojiCompat.sInstance != null) {
            EmojiCompat emojiCompat = EmojiCompat.get();
            if (!(emojiCompat.getLoadState() == 1) || editorInfo == null) {
                return;
            }
            if (editorInfo.extras == null) {
                editorInfo.extras = new Bundle();
            }
            EmojiCompat.CompatInternal19 compatInternal19 = emojiCompat.mHelper;
            compatInternal19.getClass();
            Bundle bundle = editorInfo.extras;
            MetadataList metadataList = compatInternal19.mMetadataRepo.mMetadataList;
            int __offset = metadataList.__offset(4);
            bundle.putInt("android.support.text.emoji.emojiCompat_metadataVersion", __offset != 0 ? metadataList.f170bb.getInt(__offset + metadataList.bb_pos) : 0);
            Bundle bundle2 = editorInfo.extras;
            compatInternal19.mEmojiCompat.getClass();
            bundle2.putBoolean("android.support.text.emoji.emojiCompat_replaceAll", false);
        }
    }
}
