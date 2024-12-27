package com.android.server;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class ResetReasonCode {
    private static final Pattern WILDCARD_PATTERN = Pattern.compile(".*");
    public Pattern pattern = WILDCARD_PATTERN;

    public String addCauseContents() {
        return "";
    }

    public List addCauseStackFromContexts(List list) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("");
        arrayList.add("");
        return arrayList;
    }

    public String addStackContents() {
        return "";
    }

    public String addSuffix() {
        return "";
    }

    public Pattern getCurrentPattern() {
        return this.pattern;
    }

    public Pattern getPatternByReason() {
        return this.pattern;
    }
}
