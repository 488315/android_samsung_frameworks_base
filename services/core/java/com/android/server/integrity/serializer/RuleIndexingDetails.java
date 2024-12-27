package com.android.server.integrity.serializer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
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
