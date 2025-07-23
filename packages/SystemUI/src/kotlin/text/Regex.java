package kotlin.text;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.sequences.GeneratorSequence;
import kotlin.text.Regex;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class Regex implements Serializable {
    public static final Companion Companion = new Companion(null);
    private Set<? extends RegexOption> _options;
    private final Pattern nativePattern;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class Serialized implements Serializable {
        private static final long serialVersionUID = 0;
        private final int flags;
        private final String pattern;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                    return Regex.this.find(charSequence2);
                }
            }, Regex$findAll$2.INSTANCE);
        }
        StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(0, "Start index out of bounds: ", ", input length: ");
        m.append(((String) charSequence).length());
        throw new IndexOutOfBoundsException(m.toString());
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
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public Regex(java.lang.String r2, kotlin.text.RegexOption r3) {
        /*
            r1 = this;
            kotlin.text.Regex$Companion r0 = kotlin.text.Regex.Companion
            int r3 = r3.getValue()
            r0.getClass()
            r0 = r3 & 2
            if (r0 == 0) goto Lf
            r3 = r3 | 64
        Lf:
            java.util.regex.Pattern r2 = java.util.regex.Pattern.compile(r2, r3)
            r1.<init>(r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.text.Regex.<init>(java.lang.String, kotlin.text.RegexOption):void");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public Regex(java.lang.String r4, java.util.Set<? extends kotlin.text.RegexOption> r5) {
        /*
            r3 = this;
            kotlin.text.Regex$Companion r0 = kotlin.text.Regex.Companion
            java.lang.Iterable r5 = (java.lang.Iterable) r5
            java.util.Iterator r5 = r5.iterator()
            r1 = 0
        L9:
            boolean r2 = r5.hasNext()
            if (r2 == 0) goto L1d
            java.lang.Object r2 = r5.next()
            kotlin.text.FlagEnum r2 = (kotlin.text.FlagEnum) r2
            kotlin.text.RegexOption r2 = (kotlin.text.RegexOption) r2
            int r2 = r2.getValue()
            r1 = r1 | r2
            goto L9
        L1d:
            r0.getClass()
            r5 = r1 & 2
            if (r5 == 0) goto L26
            r1 = r1 | 64
        L26:
            java.util.regex.Pattern r4 = java.util.regex.Pattern.compile(r4, r1)
            r3.<init>(r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.text.Regex.<init>(java.lang.String, java.util.Set):void");
    }
}
