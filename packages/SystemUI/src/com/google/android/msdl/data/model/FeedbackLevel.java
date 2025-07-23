package com.google.android.msdl.data.model;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class FeedbackLevel {
    public static final /* synthetic */ FeedbackLevel[] $VALUES;
    public static final FeedbackLevel DEFAULT;
    public static final FeedbackLevel EXPRESSIVE;
    public static final FeedbackLevel MINIMAL;

    static {
        FeedbackLevel feedbackLevel = new FeedbackLevel("NO_FEEDBACK", 0);
        FeedbackLevel feedbackLevel2 = new FeedbackLevel("MINIMAL", 1);
        MINIMAL = feedbackLevel2;
        FeedbackLevel feedbackLevel3 = new FeedbackLevel("DEFAULT", 2);
        DEFAULT = feedbackLevel3;
        FeedbackLevel feedbackLevel4 = new FeedbackLevel("EXPRESSIVE", 3);
        EXPRESSIVE = feedbackLevel4;
        FeedbackLevel[] feedbackLevelArr = {feedbackLevel, feedbackLevel2, feedbackLevel3, feedbackLevel4};
        $VALUES = feedbackLevelArr;
        EnumEntriesKt.enumEntries(feedbackLevelArr);
    }

    private FeedbackLevel(String str, int i) {
    }

    public static FeedbackLevel valueOf(String str) {
        return (FeedbackLevel) Enum.valueOf(FeedbackLevel.class, str);
    }

    public static FeedbackLevel[] values() {
        return (FeedbackLevel[]) $VALUES.clone();
    }
}
