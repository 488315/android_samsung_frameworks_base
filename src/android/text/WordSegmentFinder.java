package android.text;

import android.icu.util.ULocale;
import android.text.method.WordIterator;

/* loaded from: classes4.dex */
public class WordSegmentFinder extends SegmentFinder {
    private final CharSequence mText;
    private final WordIterator mWordIterator;

    public WordSegmentFinder(CharSequence charSequence, ULocale uLocale) {
        this.mText = charSequence;
        WordIterator wordIterator = new WordIterator(uLocale);
        this.mWordIterator = wordIterator;
        wordIterator.setCharSequence(charSequence, 0, charSequence.length());
    }

    public WordSegmentFinder(CharSequence charSequence, WordIterator wordIterator) {
        this.mText = charSequence;
        this.mWordIterator = wordIterator;
    }

    @Override // android.text.SegmentFinder
    public int previousStartBoundary(int i) {
        do {
            i = this.mWordIterator.prevBoundary(i);
            if (i == -1) {
                return -1;
            }
        } while (Character.isWhitespace(this.mText.charAt(i)));
        return i;
    }

    @Override // android.text.SegmentFinder
    public int previousEndBoundary(int i) {
        do {
            i = this.mWordIterator.prevBoundary(i);
            if (i == -1 || i == 0) {
                return -1;
            }
        } while (Character.isWhitespace(this.mText.charAt(i - 1)));
        return i;
    }

    @Override // android.text.SegmentFinder
    public int nextStartBoundary(int i) {
        do {
            i = this.mWordIterator.nextBoundary(i);
            if (i == -1 || i == this.mText.length()) {
                return -1;
            }
        } while (Character.isWhitespace(this.mText.charAt(i)));
        return i;
    }

    @Override // android.text.SegmentFinder
    public int nextEndBoundary(int i) {
        do {
            i = this.mWordIterator.nextBoundary(i);
            if (i == -1) {
                return -1;
            }
        } while (Character.isWhitespace(this.mText.charAt(i - 1)));
        return i;
    }
}
