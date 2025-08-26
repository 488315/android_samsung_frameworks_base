package android.text.method;

import android.graphics.Paint;
import android.icu.lang.UCharacter;
import android.text.Editable;
import android.text.Emoji;
import android.text.Layout;
import android.text.NoCopySpan;
import android.text.Selection;
import android.text.Spanned;
import android.text.method.TextKeyListener;
import android.text.style.ReplacementSpan;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

/* loaded from: classes4.dex */
public abstract class BaseKeyListener extends MetaKeyKeyListener implements KeyListener {
    private static final int CARRIAGE_RETURN = 13;
    private static final int LINE_FEED = 10;
    static final Object OLD_SEL_START = new NoCopySpan.Concrete();
    static Paint sCachedPaint = null;
    private final Object mLock = new Object();

    public boolean backspace(View view, Editable editable, int i, KeyEvent keyEvent) {
        return backspaceOrForwardDelete(view, editable, i, keyEvent, false);
    }

    public boolean forwardDelete(View view, Editable editable, int i, KeyEvent keyEvent) {
        return backspaceOrForwardDelete(view, editable, i, keyEvent, true);
    }

    private static boolean isVariationSelector(int i) {
        return UCharacter.hasBinaryProperty(i, 36);
    }

    private static int adjustReplacementSpan(CharSequence charSequence, int i, boolean z) {
        if (!(charSequence instanceof Spanned)) {
            return i;
        }
        Spanned spanned = (Spanned) charSequence;
        ReplacementSpan[] replacementSpanArr = (ReplacementSpan[]) spanned.getSpans(i, i, ReplacementSpan.class);
        for (int i2 = 0; i2 < replacementSpanArr.length; i2++) {
            int spanStart = spanned.getSpanStart(replacementSpanArr[i2]);
            int spanEnd = spanned.getSpanEnd(replacementSpanArr[i2]);
            if (spanStart < i && spanEnd > i) {
                if (!z) {
                    spanStart = spanEnd;
                }
                i = spanStart;
            }
        }
        return i;
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x0152 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00ac A[PHI: r4 r5
      0x00ac: PHI (r4v7 int) = (r4v2 int), (r4v4 int), (r4v6 int), (r4v8 int) binds: [B:90:0x0144, B:42:0x00ab, B:31:0x0087, B:27:0x006d] A[DONT_GENERATE, DONT_INLINE]
      0x00ac: PHI (r5v5 int) = (r5v1 int), (r5v1 int), (r5v1 int), (r5v8 int) binds: [B:90:0x0144, B:42:0x00ab, B:31:0x0087, B:27:0x006d] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0118 A[PHI: r4
      0x0118: PHI (r4v12 int) = 
      (r4v2 int)
      (r4v1 int)
      (r4v3 int)
      (r4v1 int)
      (r4v1 int)
      (r4v1 int)
      (r4v1 int)
      (r4v1 int)
      (r4v1 int)
      (r4v1 int)
      (r4v1 int)
      (r4v1 int)
      (r4v1 int)
      (r4v1 int)
      (r4v13 int)
      (r4v15 int)
     binds: [B:93:0x014a, B:71:0x0114, B:72:0x0116, B:69:0x010c, B:63:0x00f5, B:60:0x00e9, B:50:0x00c4, B:45:0x00b3, B:47:0x00b9, B:37:0x009b, B:34:0x008f, B:26:0x006b, B:23:0x005f, B:19:0x0054, B:16:0x004a, B:17:0x004d] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0138 A[PHI: r4
      0x0138: PHI (r4v5 int) = (r4v2 int), (r4v6 int) binds: [B:84:0x0136, B:31:0x0087] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0150 A[ADDED_TO_REGION] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static int getOffsetForBackspaceKey(CharSequence charSequence, int i) {
        int iCharCount;
        int iCharCount2;
        int iCharCount3;
        if (i <= 1) {
            return 0;
        }
        int iCharCount4 = i;
        int i2 = 0;
        int iCharCount5 = 0;
        int iCharCount6 = 0;
        do {
            int iCodePointBefore = Character.codePointBefore(charSequence, iCharCount4);
            iCharCount4 -= Character.charCount(iCodePointBefore);
            switch (i2) {
                case 0:
                    iCharCount5 = Character.charCount(iCodePointBefore);
                    if (iCodePointBefore == 10) {
                        i2 = 1;
                    } else if (isVariationSelector(iCodePointBefore)) {
                        i2 = 6;
                    } else if (Emoji.isRegionalIndicatorSymbol(iCodePointBefore)) {
                        i2 = 10;
                    } else if (Emoji.isEmojiModifier(iCodePointBefore)) {
                        i2 = 4;
                    } else if (iCodePointBefore == Emoji.COMBINING_ENCLOSING_KEYCAP) {
                        i2 = 2;
                    } else if (Emoji.isEmoji(iCodePointBefore)) {
                        i2 = 7;
                    } else {
                        i2 = iCodePointBefore == Emoji.CANCEL_TAG ? 12 : 13;
                    }
                    if (iCharCount4 <= 0) {
                    }
                    return adjustReplacementSpan(charSequence, i - iCharCount5, true);
                case 1:
                    if (iCodePointBefore == 13) {
                        iCharCount5++;
                    }
                    if (iCharCount4 <= 0) {
                    }
                    return adjustReplacementSpan(charSequence, i - iCharCount5, true);
                case 2:
                    if (isVariationSelector(iCodePointBefore)) {
                        iCharCount6 = Character.charCount(iCodePointBefore);
                        i2 = 3;
                        if (iCharCount4 <= 0) {
                        }
                        return adjustReplacementSpan(charSequence, i - iCharCount5, true);
                    }
                    if (Emoji.isKeycapBase(iCodePointBefore)) {
                        iCharCount = Character.charCount(iCodePointBefore);
                        iCharCount5 += iCharCount;
                    }
                    if (iCharCount4 <= 0) {
                    }
                    return adjustReplacementSpan(charSequence, i - iCharCount5, true);
                case 3:
                    if (Emoji.isKeycapBase(iCodePointBefore)) {
                        iCharCount2 = Character.charCount(iCodePointBefore);
                        iCharCount = iCharCount2 + iCharCount6;
                        iCharCount5 += iCharCount;
                    }
                    if (iCharCount4 <= 0) {
                    }
                    return adjustReplacementSpan(charSequence, i - iCharCount5, true);
                case 4:
                    if (isVariationSelector(iCodePointBefore)) {
                        iCharCount6 = Character.charCount(iCodePointBefore);
                        i2 = 5;
                        if (iCharCount4 <= 0) {
                        }
                        return adjustReplacementSpan(charSequence, i - iCharCount5, true);
                    }
                    if (Emoji.isEmoji(iCodePointBefore)) {
                        iCharCount3 = Character.charCount(iCodePointBefore);
                        iCharCount5 += iCharCount3;
                        i2 = 7;
                        if (iCharCount4 <= 0) {
                        }
                        return adjustReplacementSpan(charSequence, i - iCharCount5, true);
                    }
                    if (Emoji.isEmojiModifierBase(iCodePointBefore)) {
                        iCharCount = Character.charCount(iCodePointBefore);
                        iCharCount5 += iCharCount;
                    }
                    if (iCharCount4 <= 0) {
                    }
                    return adjustReplacementSpan(charSequence, i - iCharCount5, true);
                case 5:
                    if (Emoji.isEmojiModifierBase(iCodePointBefore)) {
                        iCharCount2 = Character.charCount(iCodePointBefore);
                        iCharCount = iCharCount2 + iCharCount6;
                        iCharCount5 += iCharCount;
                    }
                    if (iCharCount4 <= 0) {
                    }
                    return adjustReplacementSpan(charSequence, i - iCharCount5, true);
                case 6:
                    if (Emoji.isEmoji(iCodePointBefore)) {
                        iCharCount3 = Character.charCount(iCodePointBefore);
                        iCharCount5 += iCharCount3;
                        i2 = 7;
                        if (iCharCount4 <= 0) {
                        }
                        return adjustReplacementSpan(charSequence, i - iCharCount5, true);
                    }
                    if (!isVariationSelector(iCodePointBefore) && UCharacter.getCombiningClass(iCodePointBefore) == 0) {
                        iCharCount = Character.charCount(iCodePointBefore);
                        iCharCount5 += iCharCount;
                    }
                    if (iCharCount4 <= 0) {
                    }
                    return adjustReplacementSpan(charSequence, i - iCharCount5, true);
                case 7:
                    if (iCodePointBefore == Emoji.ZERO_WIDTH_JOINER) {
                        i2 = 8;
                    }
                    if (iCharCount4 <= 0) {
                    }
                    return adjustReplacementSpan(charSequence, i - iCharCount5, true);
                case 8:
                    if (Emoji.isEmoji(iCodePointBefore)) {
                        iCharCount5 += Character.charCount(iCodePointBefore) + 1;
                        if (Emoji.isEmojiModifier(iCodePointBefore)) {
                        }
                        if (iCharCount4 <= 0) {
                        }
                        return adjustReplacementSpan(charSequence, i - iCharCount5, true);
                    }
                    if (isVariationSelector(iCodePointBefore)) {
                        iCharCount6 = Character.charCount(iCodePointBefore);
                        i2 = 9;
                    }
                    if (iCharCount4 <= 0) {
                    }
                    return adjustReplacementSpan(charSequence, i - iCharCount5, true);
                case 9:
                    if (Emoji.isEmoji(iCodePointBefore)) {
                        iCharCount5 += iCharCount6 + 1 + Character.charCount(iCodePointBefore);
                        iCharCount6 = 0;
                        i2 = 7;
                        if (iCharCount4 <= 0) {
                        }
                        return adjustReplacementSpan(charSequence, i - iCharCount5, true);
                    }
                    if (iCharCount4 <= 0) {
                    }
                    return adjustReplacementSpan(charSequence, i - iCharCount5, true);
                case 10:
                    if (Emoji.isRegionalIndicatorSymbol(iCodePointBefore)) {
                        iCharCount5 += 2;
                        i2 = 11;
                    }
                    if (iCharCount4 <= 0) {
                    }
                    return adjustReplacementSpan(charSequence, i - iCharCount5, true);
                case 11:
                    if (Emoji.isRegionalIndicatorSymbol(iCodePointBefore)) {
                        iCharCount5 -= 2;
                        i2 = 10;
                        if (iCharCount4 <= 0) {
                        }
                        return adjustReplacementSpan(charSequence, i - iCharCount5, true);
                    }
                    if (iCharCount4 <= 0) {
                    }
                    return adjustReplacementSpan(charSequence, i - iCharCount5, true);
                case 12:
                    if (!Emoji.isTagSpecChar(iCodePointBefore)) {
                        if (Emoji.isEmoji(iCodePointBefore)) {
                            iCharCount = Character.charCount(iCodePointBefore);
                            iCharCount5 += iCharCount;
                            if (iCharCount4 <= 0) {
                            }
                            return adjustReplacementSpan(charSequence, i - iCharCount5, true);
                        }
                        iCharCount5 = 2;
                        if (iCharCount4 <= 0) {
                        }
                        return adjustReplacementSpan(charSequence, i - iCharCount5, true);
                    }
                    iCharCount5 += 2;
                    if (iCharCount4 <= 0) {
                    }
                    return adjustReplacementSpan(charSequence, i - iCharCount5, true);
                default:
                    throw new IllegalArgumentException("state " + i2 + " is unknown");
            }
        } while (i2 != 13);
        return adjustReplacementSpan(charSequence, i - iCharCount5, true);
    }

    private static int getOffsetForForwardDeleteKey(CharSequence charSequence, int i, Paint paint) {
        int length = charSequence.length();
        return i >= length + (-1) ? length : adjustReplacementSpan(charSequence, paint.getTextRunCursor(charSequence, i, length, false, i, 0), false);
    }

    private boolean backspaceOrForwardDelete(View view, Editable editable, int i, KeyEvent keyEvent, boolean z) {
        int offsetForBackspaceKey;
        Paint paint;
        Paint paint2;
        if (!KeyEvent.metaStateHasNoModifiers(keyEvent.getMetaState() & (-28916))) {
            return false;
        }
        if (deleteSelection(view, editable)) {
            return true;
        }
        boolean z2 = (keyEvent.getMetaState() & 4096) != 0;
        boolean z3 = getMetaState(editable, 1, keyEvent) == 1;
        boolean z4 = getMetaState(editable, 2, keyEvent) == 1;
        if (z2) {
            if (z4 || z3) {
                return false;
            }
            return deleteUntilWordBoundary(view, editable, z);
        }
        if (z4 && deleteLineFromCursor(view, editable, z)) {
            return true;
        }
        int selectionEnd = Selection.getSelectionEnd(editable);
        if (z) {
            if (view instanceof TextView) {
                paint2 = ((TextView) view).getPaint();
            } else {
                synchronized (this.mLock) {
                    if (sCachedPaint == null) {
                        sCachedPaint = new Paint();
                    }
                    paint = sCachedPaint;
                }
                paint2 = paint;
            }
            offsetForBackspaceKey = getOffsetForForwardDeleteKey(editable, selectionEnd, paint2);
        } else {
            offsetForBackspaceKey = getOffsetForBackspaceKey(editable, selectionEnd);
        }
        if (selectionEnd == offsetForBackspaceKey) {
            return false;
        }
        editable.delete(Math.min(selectionEnd, offsetForBackspaceKey), Math.max(selectionEnd, offsetForBackspaceKey));
        return true;
    }

    private boolean deleteUntilWordBoundary(View view, Editable editable, boolean z) {
        int iFollowing;
        int selectionStart = Selection.getSelectionStart(editable);
        if (selectionStart != Selection.getSelectionEnd(editable)) {
            return false;
        }
        if ((!z && selectionStart == 0) || (z && selectionStart == editable.length())) {
            return false;
        }
        WordIterator wordIterator = view instanceof TextView ? ((TextView) view).getWordIterator() : null;
        if (wordIterator == null) {
            wordIterator = new WordIterator();
        }
        if (z) {
            wordIterator.setCharSequence(editable, selectionStart, editable.length());
            iFollowing = wordIterator.following(selectionStart);
            if (iFollowing == -1) {
                iFollowing = editable.length();
            }
        } else {
            wordIterator.setCharSequence(editable, 0, selectionStart);
            int iPreceding = wordIterator.preceding(selectionStart);
            if (iPreceding == -1) {
                iFollowing = selectionStart;
                selectionStart = 0;
            } else {
                iFollowing = selectionStart;
                selectionStart = iPreceding;
            }
        }
        editable.delete(selectionStart, iFollowing);
        return true;
    }

    private boolean deleteSelection(View view, Editable editable) {
        int selectionStart = Selection.getSelectionStart(editable);
        int selectionEnd = Selection.getSelectionEnd(editable);
        if (selectionEnd < selectionStart) {
            selectionEnd = selectionStart;
            selectionStart = selectionEnd;
        }
        if (selectionStart == selectionEnd) {
            return false;
        }
        editable.delete(selectionStart, selectionEnd);
        return true;
    }

    private boolean deleteLineFromCursor(View view, Editable editable, boolean z) {
        if (!(view instanceof TextView)) {
            return false;
        }
        int selectionStart = Selection.getSelectionStart(editable);
        int selectionEnd = Selection.getSelectionEnd(editable);
        if (selectionStart < selectionEnd) {
            selectionEnd = selectionStart;
            selectionStart = selectionEnd;
        }
        TextView textView = (TextView) view;
        Layout layout = textView.getLayout();
        if (layout == null || textView.isOffsetMappingAvailable()) {
            return false;
        }
        int lineForOffset = layout.getLineForOffset(Selection.getSelectionStart(editable));
        int lineStart = layout.getLineStart(lineForOffset);
        int lineEnd = layout.getLineEnd(lineForOffset);
        if (z) {
            editable.delete(selectionEnd, lineEnd);
            return true;
        }
        editable.delete(lineStart, selectionStart);
        return true;
    }

    /* renamed from: android.text.method.BaseKeyListener$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$android$text$method$TextKeyListener$Capitalize;

        static {
            int[] iArr = new int[TextKeyListener.Capitalize.values().length];
            $SwitchMap$android$text$method$TextKeyListener$Capitalize = iArr;
            try {
                iArr[TextKeyListener.Capitalize.CHARACTERS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$android$text$method$TextKeyListener$Capitalize[TextKeyListener.Capitalize.WORDS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$android$text$method$TextKeyListener$Capitalize[TextKeyListener.Capitalize.SENTENCES.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    static int makeTextContentType(TextKeyListener.Capitalize capitalize, boolean z) {
        int i = AnonymousClass1.$SwitchMap$android$text$method$TextKeyListener$Capitalize[capitalize.ordinal()];
        int i2 = 1;
        if (i == 1) {
            i2 = 4097;
        } else if (i == 2) {
            i2 = 8193;
        } else if (i == 3) {
            i2 = 16385;
        }
        return z ? 32768 | i2 : i2;
    }

    @Override // android.text.method.MetaKeyKeyListener, android.text.method.KeyListener
    public boolean onKeyDown(View view, Editable editable, int i, KeyEvent keyEvent) {
        boolean zBackspace;
        if (i == 67) {
            zBackspace = backspace(view, editable, i, keyEvent);
        } else {
            zBackspace = i != 112 ? false : forwardDelete(view, editable, i, keyEvent);
        }
        if (zBackspace) {
            adjustMetaAfterKeypress(editable);
            return true;
        }
        return super.onKeyDown(view, editable, i, keyEvent);
    }

    @Override // android.text.method.KeyListener
    public boolean onKeyOther(View view, Editable editable, KeyEvent keyEvent) {
        if (keyEvent.getAction() != 2 || keyEvent.getKeyCode() != 0) {
            return false;
        }
        int selectionStart = Selection.getSelectionStart(editable);
        int selectionEnd = Selection.getSelectionEnd(editable);
        if (selectionEnd < selectionStart) {
            selectionEnd = selectionStart;
            selectionStart = selectionEnd;
        }
        String characters = keyEvent.getCharacters();
        if (characters == null) {
            return false;
        }
        editable.replace(selectionStart, selectionEnd, characters);
        return true;
    }
}
