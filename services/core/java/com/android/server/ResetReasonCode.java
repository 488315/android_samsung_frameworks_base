package com.android.server;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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
