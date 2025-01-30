package kotlin.text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class Regex implements Serializable {
    public static final Companion Companion = new Companion(null);
    private Set<? extends RegexOption> _options;
    private final Pattern nativePattern;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static final class Serialized implements Serializable {
        private static final long serialVersionUID = 0;
        private final int flags;
        private final String pattern;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }
        }

        static {
            new Companion(null);
        }

        public Serialized(String str, int i) {
            this.pattern = str;
            this.flags = i;
        }

        private final Object readResolve() {
            return new Regex(Pattern.compile(this.pattern, this.flags));
        }
    }

    public Regex(Pattern pattern) {
        this.nativePattern = pattern;
    }

    private final Object writeReplace() {
        return new Serialized(this.nativePattern.pattern(), this.nativePattern.flags());
    }

    public final boolean matches(CharSequence charSequence) {
        return this.nativePattern.matcher(charSequence).matches();
    }

    public final String replace(CharSequence charSequence) {
        return this.nativePattern.matcher(charSequence).replaceAll("");
    }

    public final List split(CharSequence charSequence) {
        String str;
        int i = 0;
        StringsKt__StringsKt.requireNonNegativeLimit(0);
        Matcher matcher = this.nativePattern.matcher(charSequence);
        if (!matcher.find()) {
            return Collections.singletonList(charSequence.toString());
        }
        ArrayList arrayList = new ArrayList(10);
        do {
            str = (String) charSequence;
            arrayList.add(str.subSequence(i, matcher.start()).toString());
            i = matcher.end();
        } while (matcher.find());
        arrayList.add(str.subSequence(i, str.length()).toString());
        return arrayList;
    }

    public final String toString() {
        return this.nativePattern.toString();
    }

    public Regex(String str) {
        this(Pattern.compile(str));
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public Regex(String str, RegexOption regexOption) {
        this(Pattern.compile(str, (r3 & 2) != 0 ? r3 | 64 : r3));
        Companion companion = Companion;
        int value = regexOption.getValue();
        companion.getClass();
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public Regex(String str, Set<? extends RegexOption> set) {
        this(Pattern.compile(str, (r1 & 2) != 0 ? r1 | 64 : r1));
        Companion companion = Companion;
        Iterator<T> it = set.iterator();
        int i = 0;
        while (it.hasNext()) {
            i |= ((RegexOption) ((FlagEnum) it.next())).getValue();
        }
        companion.getClass();
    }
}
