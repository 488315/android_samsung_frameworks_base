package com.android.server;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public abstract class ResetReasonCode {
    private static final Pattern WILDCARD_PATTERN = Pattern.compile(".*");
    public Pattern pattern = WILDCARD_PATTERN;

    public String addCauseContents() {
        return "";
    }

    public String addStackContents() {
        return "";
    }

    public String addSuffix() {
        return "";
    }

    public Pattern getPatternByReason() {
        return this.pattern;
    }

    public List addCauseStackFromContexts(List list) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("");
        arrayList.add("");
        return arrayList;
    }

    public Pattern getCurrentPattern() {
        return this.pattern;
    }
}
