package com.samsung.android.sdk.scs.p048ai.language;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class Result {
    public final String content;
    public final String safetyAttribute;

    public Result(String str, String str2) {
        this.content = str;
        this.safetyAttribute = str2;
    }

    public final String toString() {
        return "content: " + this.content + ", safety attribute: " + this.safetyAttribute;
    }
}
