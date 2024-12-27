package com.android.server.permission.jarjar.kotlin.text;

import com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Regex implements Serializable {
    private Set _options;
    private final Pattern nativePattern;

    final class Serialized implements Serializable {
        private static final long serialVersionUID = 0;
        private final int flags;
        private final String pattern;

        public Serialized(String str, int i) {
            this.pattern = str;
            this.flags = i;
        }

        private final Object readResolve() {
            Pattern compile = Pattern.compile(this.pattern, this.flags);
            Intrinsics.checkNotNullExpressionValue("compile(...)", compile);
            return new Regex(compile);
        }
    }

    public Regex(Pattern pattern) {
        this.nativePattern = pattern;
    }

    private final Object writeReplace() {
        String pattern = this.nativePattern.pattern();
        Intrinsics.checkNotNullExpressionValue("pattern(...)", pattern);
        return new Serialized(pattern, this.nativePattern.flags());
    }

    public final List split(CharSequence charSequence) {
        String str;
        Intrinsics.checkNotNullParameter("input", charSequence);
        Matcher matcher = this.nativePattern.matcher(charSequence);
        if (!matcher.find()) {
            List singletonList = Collections.singletonList(charSequence.toString());
            Intrinsics.checkNotNullExpressionValue("singletonList(...)", singletonList);
            return singletonList;
        }
        ArrayList arrayList = new ArrayList(10);
        int i = 0;
        do {
            str = (String) charSequence;
            arrayList.add(str.subSequence(i, matcher.start()).toString());
            i = matcher.end();
        } while (matcher.find());
        arrayList.add(str.subSequence(i, str.length()).toString());
        return arrayList;
    }

    public final String toString() {
        String pattern = this.nativePattern.toString();
        Intrinsics.checkNotNullExpressionValue("toString(...)", pattern);
        return pattern;
    }
}
