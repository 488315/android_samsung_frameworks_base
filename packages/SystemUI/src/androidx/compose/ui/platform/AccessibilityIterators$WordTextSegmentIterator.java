package androidx.compose.ui.platform;

import java.text.BreakIterator;
import java.util.Locale;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AccessibilityIterators$WordTextSegmentIterator extends AccessibilityIterators$AbstractTextSegmentIterator {
    public static final Companion Companion = new Companion(null);
    public static AccessibilityIterators$WordTextSegmentIterator instance;
    public final BreakIterator impl;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public /* synthetic */ AccessibilityIterators$WordTextSegmentIterator(Locale locale, DefaultConstructorMarker defaultConstructorMarker) {
        this(locale);
    }

    @Override // androidx.compose.ui.platform.AccessibilityIterators$TextSegmentIterator
    public final int[] following(int i) {
        String str = this.text;
        if (str == null) {
            str = null;
        }
        if (str.length() > 0) {
            String str2 = this.text;
            if (str2 == null) {
                str2 = null;
            }
            if (i < str2.length()) {
                if (i < 0) {
                    i = 0;
                }
                while (!isLetterOrDigit(i) && (!isLetterOrDigit(i) || (i != 0 && isLetterOrDigit(i - 1)))) {
                    BreakIterator breakIterator = this.impl;
                    if (breakIterator == null) {
                        breakIterator = null;
                    }
                    i = breakIterator.following(i);
                    if (i == -1) {
                        break;
                    }
                }
                BreakIterator breakIterator2 = this.impl;
                if (breakIterator2 == null) {
                    breakIterator2 = null;
                }
                int following = breakIterator2.following(i);
                if (following != -1 && isEndBoundary$1(following)) {
                    return getRange(i, following);
                }
            }
        }
        return null;
    }

    public final boolean isEndBoundary$1(int i) {
        if (i <= 0 || !isLetterOrDigit(i - 1)) {
            return false;
        }
        String str = this.text;
        if (str == null) {
            str = null;
        }
        return i == str.length() || !isLetterOrDigit(i);
    }

    public final boolean isLetterOrDigit(int i) {
        if (i < 0) {
            return false;
        }
        String str = this.text;
        if (str == null) {
            str = null;
        }
        if (i >= str.length()) {
            return false;
        }
        String str2 = this.text;
        return Character.isLetterOrDigit((str2 != null ? str2 : null).codePointAt(i));
    }

    @Override // androidx.compose.ui.platform.AccessibilityIterators$TextSegmentIterator
    public final int[] preceding(int i) {
        String str = this.text;
        if (str == null) {
            str = null;
        }
        int length = str.length();
        if (length > 0 && i > 0) {
            if (i > length) {
                i = length;
            }
            while (i > 0 && !isLetterOrDigit(i - 1) && !isEndBoundary$1(i)) {
                BreakIterator breakIterator = this.impl;
                if (breakIterator == null) {
                    breakIterator = null;
                }
                i = breakIterator.preceding(i);
                if (i == -1) {
                    break;
                }
            }
            BreakIterator breakIterator2 = this.impl;
            if (breakIterator2 == null) {
                breakIterator2 = null;
            }
            int preceding = breakIterator2.preceding(i);
            if (preceding != -1 && isLetterOrDigit(preceding) && (preceding == 0 || !isLetterOrDigit(preceding - 1))) {
                return getRange(preceding, i);
            }
        }
        return null;
    }

    private AccessibilityIterators$WordTextSegmentIterator(Locale locale) {
        this.impl = BreakIterator.getWordInstance(locale);
    }
}
