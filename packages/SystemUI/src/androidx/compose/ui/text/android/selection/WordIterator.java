package androidx.compose.ui.text.android.selection;

import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.compose.ui.text.android.CharSequenceCharacterIterator;
import androidx.compose.ui.text.internal.InlineClassHelperKt;
import androidx.emoji2.text.EmojiCompat;
import java.lang.Character;
import java.text.BreakIterator;
import java.util.Locale;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class WordIterator {
    public static final Companion Companion = new Companion(null);
    public final CharSequence charSequence;
    public final int end;
    public final BreakIterator iterator;
    public final int start;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static boolean isPunctuation$ui_text_release(int i) {
            int type = Character.getType(i);
            return type == 23 || type == 20 || type == 22 || type == 30 || type == 29 || type == 24 || type == 21;
        }

        private Companion() {
        }
    }

    public WordIterator(CharSequence charSequence, int i, int i2, Locale locale) {
        this.charSequence = charSequence;
        if (!(i >= 0 && i <= charSequence.length())) {
            InlineClassHelperKt.throwIllegalArgumentException("input start index is outside the CharSequence");
        }
        if (!(i2 >= 0 && i2 <= charSequence.length())) {
            InlineClassHelperKt.throwIllegalArgumentException("input end index is outside the CharSequence");
        }
        BreakIterator wordInstance = BreakIterator.getWordInstance(locale);
        this.iterator = wordInstance;
        this.start = Math.max(0, i - 50);
        this.end = Math.min(charSequence.length(), i2 + 50);
        wordInstance.setText(new CharSequenceCharacterIterator(charSequence, i, i2));
    }

    public final void checkOffsetIsValid(int i) {
        boolean z = false;
        int i2 = this.start;
        int i3 = this.end;
        if (i <= i3 && i2 <= i) {
            z = true;
        }
        if (z) {
            return;
        }
        StringBuilder m = MutableObjectList$$ExternalSyntheticOutline0.m(i, i2, "Invalid offset: ", ". Valid range is [", " , ");
        m.append(i3);
        m.append(']');
        InlineClassHelperKt.throwIllegalArgumentException(m.toString());
    }

    public final boolean isAfterLetterOrDigitOrEmoji(int i) {
        int i2 = this.start + 1;
        if (i > this.end || i2 > i) {
            return false;
        }
        if (!Character.isLetterOrDigit(Character.codePointBefore(this.charSequence, i))) {
            int i3 = i - 1;
            if (!Character.isSurrogate(this.charSequence.charAt(i3))) {
                if (!EmojiCompat.isConfigured()) {
                    return false;
                }
                EmojiCompat emojiCompat = EmojiCompat.get();
                if (emojiCompat.getLoadState() != 1 || emojiCompat.getEmojiStart(this.charSequence, i3) == -1) {
                    return false;
                }
            }
        }
        return true;
    }

    public final boolean isAfterPunctuation(int i) {
        int i2 = this.start + 1;
        if (i > this.end || i2 > i) {
            return false;
        }
        int codePointBefore = Character.codePointBefore(this.charSequence, i);
        Companion.getClass();
        return Companion.isPunctuation$ui_text_release(codePointBefore);
    }

    public final boolean isBoundary(int i) {
        checkOffsetIsValid(i);
        if (!this.iterator.isBoundary(i)) {
            return false;
        }
        if (isOnLetterOrDigitOrEmoji(i) && isOnLetterOrDigitOrEmoji(i - 1) && isOnLetterOrDigitOrEmoji(i + 1)) {
            return false;
        }
        return i <= 0 || i >= this.charSequence.length() - 1 || !(isHiraganaKatakanaBoundary(i) || isHiraganaKatakanaBoundary(i + 1));
    }

    public final boolean isHiraganaKatakanaBoundary(int i) {
        int i2 = i - 1;
        Character.UnicodeBlock of = Character.UnicodeBlock.of(this.charSequence.charAt(i2));
        Character.UnicodeBlock unicodeBlock = Character.UnicodeBlock.HIRAGANA;
        if (Intrinsics.areEqual(of, unicodeBlock) && Intrinsics.areEqual(Character.UnicodeBlock.of(this.charSequence.charAt(i)), Character.UnicodeBlock.KATAKANA)) {
            return true;
        }
        return Intrinsics.areEqual(Character.UnicodeBlock.of(this.charSequence.charAt(i)), unicodeBlock) && Intrinsics.areEqual(Character.UnicodeBlock.of(this.charSequence.charAt(i2)), Character.UnicodeBlock.KATAKANA);
    }

    public final boolean isOnLetterOrDigitOrEmoji(int i) {
        if (i >= this.end || this.start > i) {
            return false;
        }
        if (!Character.isLetterOrDigit(Character.codePointAt(this.charSequence, i)) && !Character.isSurrogate(this.charSequence.charAt(i))) {
            if (!EmojiCompat.isConfigured()) {
                return false;
            }
            EmojiCompat emojiCompat = EmojiCompat.get();
            if (emojiCompat.getLoadState() != 1 || emojiCompat.getEmojiStart(this.charSequence, i) == -1) {
                return false;
            }
        }
        return true;
    }

    public final boolean isOnPunctuation(int i) {
        if (i >= this.end || this.start > i) {
            return false;
        }
        int codePointAt = Character.codePointAt(this.charSequence, i);
        Companion.getClass();
        return Companion.isPunctuation$ui_text_release(codePointAt);
    }

    public final int nextBoundary(int i) {
        checkOffsetIsValid(i);
        int following = this.iterator.following(i);
        return (isOnLetterOrDigitOrEmoji(following + (-1)) && isOnLetterOrDigitOrEmoji(following) && !isHiraganaKatakanaBoundary(following)) ? nextBoundary(following) : following;
    }

    public final int prevBoundary(int i) {
        checkOffsetIsValid(i);
        int preceding = this.iterator.preceding(i);
        return (isOnLetterOrDigitOrEmoji(preceding) && isAfterLetterOrDigitOrEmoji(preceding) && !isHiraganaKatakanaBoundary(preceding)) ? prevBoundary(preceding) : preceding;
    }
}
