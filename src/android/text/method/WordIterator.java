package android.text.method;

import android.icu.lang.UCharacter;
import android.icu.text.BreakIterator;
import android.icu.util.ULocale;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.mtp.MtpConstants;
import android.text.CharSequenceCharacterIterator;
import android.text.Selection;
import android.text.TextUtils;
import java.util.Locale;

/* loaded from: classes4.dex */
public class WordIterator implements Selection.PositionIterator {
    private static final int WINDOW_WIDTH = 50;
    private CharSequence mCharSeq;
    private int mEnd;
    private final BreakIterator mIterator;
    private int mStart;

    public WordIterator() {
        this(Locale.getDefault());
    }

    public WordIterator(Locale locale) {
        this.mIterator = BreakIterator.getWordInstance(locale);
    }

    public WordIterator(ULocale uLocale) {
        this.mIterator = BreakIterator.getWordInstance(uLocale);
    }

    public void setCharSequence(CharSequence charSequence, int i, int i2) {
        if (i >= 0 && i2 <= charSequence.length()) {
            this.mCharSeq = charSequence;
            this.mStart = Math.max(0, i - 50);
            this.mEnd = Math.min(charSequence.length(), i2 + 50);
            this.mIterator.setText(new CharSequenceCharacterIterator(charSequence, this.mStart, this.mEnd));
            return;
        }
        throw new IndexOutOfBoundsException("input indexes are outside the CharSequence");
    }

    @Override // android.text.Selection.PositionIterator
    public int preceding(int i) {
        checkOffsetIsValid(i);
        do {
            i = this.mIterator.preceding(i);
            if (i == -1) {
                break;
            }
        } while (!isOnLetterOrDigit(i));
        return i;
    }

    @Override // android.text.Selection.PositionIterator
    public int following(int i) {
        checkOffsetIsValid(i);
        do {
            i = this.mIterator.following(i);
            if (i == -1) {
                break;
            }
        } while (!isAfterLetterOrDigit(i));
        return i;
    }

    public boolean isBoundary(int i) {
        checkOffsetIsValid(i);
        return this.mIterator.isBoundary(i);
    }

    public int nextBoundary(int i) {
        checkOffsetIsValid(i);
        return this.mIterator.following(i);
    }

    public int prevBoundary(int i) {
        checkOffsetIsValid(i);
        return this.mIterator.preceding(i);
    }

    public int getBeginning(int i) {
        return getBeginning(i, false);
    }

    public int getEnd(int i) {
        return getEnd(i, false);
    }

    public int getPrevWordBeginningOnTwoWordsBoundary(int i) {
        return getBeginning(i, true);
    }

    public int getNextWordEndOnTwoWordBoundary(int i) {
        return getEnd(i, true);
    }

    private int getBeginning(int i, boolean z) {
        checkOffsetIsValid(i);
        if (isOnLetterOrDigit(i)) {
            return (!this.mIterator.isBoundary(i) || (isAfterLetterOrDigit(i) && z)) ? this.mIterator.preceding(i) : i;
        }
        if (isAfterLetterOrDigit(i)) {
            return this.mIterator.preceding(i);
        }
        return -1;
    }

    private int getEnd(int i, boolean z) {
        checkOffsetIsValid(i);
        if (isAfterLetterOrDigit(i)) {
            return (!this.mIterator.isBoundary(i) || (isOnLetterOrDigit(i) && z)) ? this.mIterator.following(i) : i;
        }
        if (isOnLetterOrDigit(i)) {
            return this.mIterator.following(i);
        }
        return -1;
    }

    public int getPunctuationBeginning(int i) {
        checkOffsetIsValid(i);
        while (i != -1 && !isPunctuationStartBoundary(i)) {
            i = prevBoundary(i);
        }
        return i;
    }

    public int getPunctuationEnd(int i) {
        checkOffsetIsValid(i);
        while (i != -1 && !isPunctuationEndBoundary(i)) {
            i = nextBoundary(i);
        }
        return i;
    }

    public boolean isAfterPunctuation(int i) {
        if (this.mStart >= i || i > this.mEnd) {
            return false;
        }
        return TextUtils.isPunctuation(Character.codePointBefore(this.mCharSeq, i));
    }

    public boolean isOnPunctuation(int i) {
        if (this.mStart > i || i >= this.mEnd) {
            return false;
        }
        return TextUtils.isPunctuation(Character.codePointAt(this.mCharSeq, i));
    }

    public static boolean isMidWordPunctuation(Locale locale, int i) {
        int intPropertyValue = UCharacter.getIntPropertyValue(i, MtpConstants.OPERATION_GET_DEVICE_PROP_DESC);
        return intPropertyValue == 4 || intPropertyValue == 11 || intPropertyValue == 15;
    }

    private boolean isPunctuationStartBoundary(int i) {
        return isOnPunctuation(i) && !isAfterPunctuation(i);
    }

    private boolean isPunctuationEndBoundary(int i) {
        return !isOnPunctuation(i) && isAfterPunctuation(i);
    }

    private boolean isAfterLetterOrDigit(int i) {
        return this.mStart < i && i <= this.mEnd && Character.isLetterOrDigit(Character.codePointBefore(this.mCharSeq, i));
    }

    private boolean isOnLetterOrDigit(int i) {
        return this.mStart <= i && i < this.mEnd && Character.isLetterOrDigit(Character.codePointAt(this.mCharSeq, i));
    }

    private void checkOffsetIsValid(int i) {
        if (this.mStart > i || i > this.mEnd) {
            throw new IllegalArgumentException("Invalid offset: " + i + ". Valid range is [" + this.mStart + ", " + this.mEnd + NavigationBarInflaterView.SIZE_MOD_END);
        }
    }
}
