package com.android.server.enterprise.auditlog;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/* loaded from: classes2.dex */
public class Filter {
    public Matcher mMatcher;
    public Pattern mPattern;

    public boolean setFilter(String str) {
        try {
            this.mPattern = Pattern.compile(str);
            return true;
        } catch (PatternSyntaxException unused) {
            return false;
        }
    }

    public boolean filtering(String str) {
        Matcher matcher = this.mPattern.matcher(str);
        this.mMatcher = matcher;
        return matcher.find();
    }
}
