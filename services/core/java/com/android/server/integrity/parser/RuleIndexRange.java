package com.android.server.integrity.parser;

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
