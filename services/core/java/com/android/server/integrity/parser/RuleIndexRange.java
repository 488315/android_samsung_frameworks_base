package com.android.server.integrity.parser;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class RuleIndexRange {
    public final int mEndIndex;
    public final int mStartIndex;

    public RuleIndexRange(int i, int i2) {
        this.mStartIndex = i;
        this.mEndIndex = i2;
    }

    public final boolean equals(Object obj) {
        RuleIndexRange ruleIndexRange = (RuleIndexRange) obj;
        if (this.mStartIndex == ruleIndexRange.mStartIndex) {
            if (this.mEndIndex == ruleIndexRange.mEndIndex) {
                return true;
            }
        }
        return false;
    }

    public final String toString() {
        return String.format(
                "Range{%d, %d}",
                Integer.valueOf(this.mStartIndex), Integer.valueOf(this.mEndIndex));
    }
}
