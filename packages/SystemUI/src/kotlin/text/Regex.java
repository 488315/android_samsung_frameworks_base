package kotlin.text;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.sequences.GeneratorSequence;
import kotlin.text.Regex;

/* loaded from: classes4.dex */
public final class Regex implements Serializable {
    public static final Companion Companion = new Companion(null);
    private Set<? extends RegexOption> _options;
    private final Pattern nativePattern;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    final class Serialized implements Serializable {
        private static final long serialVersionUID = 0;
        private final int flags;
        private final String pattern;

        public final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
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

    public static GeneratorSequence findAll$default(final Regex regex, CharSequence charSequence) {
        final String str = (String) charSequence;
        if (str.length() >= 0) {
            return new GeneratorSequence(new Function0() { // from class: kotlin.text.Regex$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    CharSequence charSequence2 = str;
                    Regex.Companion companion = Regex.Companion;
                    return this.f$0.find(charSequence2);
                }
            }, Regex$findAll$2.INSTANCE);
        }
        StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(0, "Start index out of bounds: ", ", input length: ");
        sbM.append(((String) charSequence).length());
        throw new IndexOutOfBoundsException(sbM.toString());
    }

    private final Object writeReplace() {
        return new Serialized(this.nativePattern.pattern(), this.nativePattern.flags());
    }

    public final MatcherMatchResult find(CharSequence charSequence) {
        Matcher matcher = this.nativePattern.matcher(charSequence);
        if (matcher.find(0)) {
            return new MatcherMatchResult(matcher, charSequence);
        }
        return null;
    }

    public final MatcherMatchResult matchEntire(CharSequence charSequence) {
        Matcher matcher = this.nativePattern.matcher(charSequence);
        if (matcher.matches()) {
            return new MatcherMatchResult(matcher, charSequence);
        }
        return null;
    }

    public final boolean matches(CharSequence charSequence) {
        return this.nativePattern.matcher(charSequence).matches();
    }

    public final String replace(CharSequence charSequence, String str) {
        return this.nativePattern.matcher(charSequence).replaceAll(str);
    }

    public final List split(CharSequence charSequence) {
        String str;
        int iEnd = 0;
        StringsKt__StringsKt.requireNonNegativeLimit(0);
        Matcher matcher = this.nativePattern.matcher(charSequence);
        if (!matcher.find()) {
            return Collections.singletonList(charSequence.toString());
        }
        ArrayList arrayList = new ArrayList(10);
        do {
            str = (String) charSequence;
            arrayList.add(str.subSequence(iEnd, matcher.start()).toString());
            iEnd = matcher.end();
        } while (matcher.find());
        arrayList.add(str.subSequence(iEnd, str.length()).toString());
        return arrayList;
    }

    public final String toString() {
        return this.nativePattern.toString();
    }

    public Regex(String str) {
        this(Pattern.compile(str));
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public Regex(String str, RegexOption regexOption) {
        Companion companion = Companion;
        int value = regexOption.getValue();
        companion.getClass();
        this(Pattern.compile(str, (value & 2) != 0 ? value | 64 : value));
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public Regex(String str, Set<? extends RegexOption> set) {
        Companion companion = Companion;
        Iterator<T> it = set.iterator();
        int value = 0;
        while (it.hasNext()) {
            value |= ((RegexOption) ((FlagEnum) it.next())).getValue();
        }
        companion.getClass();
        this(Pattern.compile(str, (value & 2) != 0 ? value | 64 : value));
    }
}
