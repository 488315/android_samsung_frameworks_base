package android.text;

import java.text.CharacterIterator;

/* loaded from: classes4.dex */
public class CharSequenceCharacterIterator implements CharacterIterator {
    private final int mBeginIndex;
    private final CharSequence mCharSeq;
    private final int mEndIndex;
    private int mIndex;

    public CharSequenceCharacterIterator(CharSequence text, int start, int end) {
        this.mCharSeq = text;
        this.mIndex = start;
        this.mBeginIndex = start;
        this.mEndIndex = end;
    }

    @Override // java.text.CharacterIterator
    public char first() {
        this.mIndex = this.mBeginIndex;
        return current();
    }

    @Override // java.text.CharacterIterator
    public char last() {
        if (this.mBeginIndex == this.mEndIndex) {
            this.mIndex = this.mEndIndex;
            return (char) 65535;
        }
        this.mIndex = this.mEndIndex - 1;
        return this.mCharSeq.charAt(this.mIndex);
    }

    @Override // java.text.CharacterIterator
    public char current() {
        if (this.mIndex == this.mEndIndex) {
            return (char) 65535;
        }
        return this.mCharSeq.charAt(this.mIndex);
    }

    @Override // java.text.CharacterIterator
    public char next() {
        this.mIndex++;
        if (this.mIndex >= this.mEndIndex) {
            this.mIndex = this.mEndIndex;
            return (char) 65535;
        }
        return this.mCharSeq.charAt(this.mIndex);
    }

    @Override // java.text.CharacterIterator
    public char previous() {
        if (this.mIndex <= this.mBeginIndex) {
            return (char) 65535;
        }
        this.mIndex--;
        return this.mCharSeq.charAt(this.mIndex);
    }

    @Override // java.text.CharacterIterator
    public char setIndex(int position) {
        if (this.mBeginIndex <= position && position <= this.mEndIndex) {
            this.mIndex = position;
            return current();
        }
        throw new IllegalArgumentException("invalid position");
    }

    @Override // java.text.CharacterIterator
    public int getBeginIndex() {
        return this.mBeginIndex;
    }

    @Override // java.text.CharacterIterator
    public int getEndIndex() {
        return this.mEndIndex;
    }

    @Override // java.text.CharacterIterator
    public int getIndex() {
        return this.mIndex;
    }

    @Override // java.text.CharacterIterator
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
    }
}
