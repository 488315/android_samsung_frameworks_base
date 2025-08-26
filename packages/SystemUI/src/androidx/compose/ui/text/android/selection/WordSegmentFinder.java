package androidx.compose.ui.text.android.selection;

/* loaded from: classes.dex */
public final class WordSegmentFinder implements SegmentFinder {
    public final CharSequence text;
    public final WordIterator wordIterator;

    public WordSegmentFinder(CharSequence charSequence, WordIterator wordIterator) {
        this.text = charSequence;
        this.wordIterator = wordIterator;
    }

    @Override // androidx.compose.ui.text.android.selection.SegmentFinder
    public final int nextEndBoundary(int i) {
        do {
            i = this.wordIterator.nextBoundary(i);
            if (i == -1) {
                return -1;
            }
        } while (Character.isWhitespace(this.text.charAt(i - 1)));
        return i;
    }

    @Override // androidx.compose.ui.text.android.selection.SegmentFinder
    public final int nextStartBoundary(int i) {
        do {
            i = this.wordIterator.nextBoundary(i);
            if (i == -1 || i == this.text.length()) {
                return -1;
            }
        } while (Character.isWhitespace(this.text.charAt(i)));
        return i;
    }

    @Override // androidx.compose.ui.text.android.selection.SegmentFinder
    public final int previousEndBoundary(int i) {
        do {
            i = this.wordIterator.prevBoundary(i);
            if (i == -1 || i == 0) {
                return -1;
            }
        } while (Character.isWhitespace(this.text.charAt(i - 1)));
        return i;
    }

    @Override // androidx.compose.ui.text.android.selection.SegmentFinder
    public final int previousStartBoundary(int i) {
        do {
            i = this.wordIterator.prevBoundary(i);
            if (i == -1) {
                return -1;
            }
        } while (Character.isWhitespace(this.text.charAt(i)));
        return i;
    }
}
