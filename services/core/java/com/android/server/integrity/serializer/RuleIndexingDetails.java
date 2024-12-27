package com.android.server.integrity.serializer;

public final class RuleIndexingDetails {
    public final int mIndexType;
    public final String mRuleKey;

    public RuleIndexingDetails() {
        this.mIndexType = 0;
        this.mRuleKey = "N/A";
    }

    public RuleIndexingDetails(int i, String str) {
        this.mIndexType = i;
        this.mRuleKey = str;
    }
}
