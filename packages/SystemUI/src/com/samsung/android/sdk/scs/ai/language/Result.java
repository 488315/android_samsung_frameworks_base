package com.samsung.android.sdk.scs.ai.language;

import android.os.Bundle;

/* loaded from: classes4.dex */
public class Result {
    public final String content;
    public final String modelAlias;
    public final String safetyAttribute;

    public Result(Bundle bundle) {
        this.content = bundle.getString("content");
        this.safetyAttribute = bundle.getString("safety");
        this.modelAlias = bundle.getString("model_alias");
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("content: ");
        String str = this.content;
        if (str == null) {
            str = "";
        }
        sb.append(str);
        sb.append(", safety attribute: ");
        String str2 = this.safetyAttribute;
        if (str2 == null) {
            str2 = "";
        }
        sb.append(str2);
        sb.append(", model alias: ");
        String str3 = this.modelAlias;
        sb.append(str3 != null ? str3 : "");
        return sb.toString();
    }
}
