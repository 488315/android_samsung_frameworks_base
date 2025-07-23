package android.view;

import android.content.res.Configuration;
import android.view.ViewRootImpl;
import java.text.BreakIterator;
import java.util.Locale;

/* loaded from: classes4.dex */
public final class AccessibilityIterators {

    public interface TextSegmentIterator {
        int[] following(int i);

        int[] preceding(int i);
    }

    public static abstract class AbstractTextSegmentIterator implements TextSegmentIterator {
        private final int[] mSegment = new int[2];
        protected String mText;

        public void initialize(String str) {
            this.mText = str;
        }

        protected int[] getRange(int i, int i2) {
            if (i < 0 || i2 < 0 || i == i2) {
                return null;
            }
            int[] iArr = this.mSegment;
            iArr[0] = i;
            iArr[1] = i2;
            return iArr;
        }
    }

    static class CharacterTextSegmentIterator extends AbstractTextSegmentIterator implements ViewRootImpl.ConfigChangedCallback {
        private static CharacterTextSegmentIterator sInstance;
        protected BreakIterator mImpl;
        private Locale mLocale;

        public static CharacterTextSegmentIterator getInstance(Locale locale) {
            if (sInstance == null) {
                sInstance = new CharacterTextSegmentIterator(locale);
            }
            return sInstance;
        }

        private CharacterTextSegmentIterator(Locale locale) {
            this.mLocale = locale;
            onLocaleChanged(locale);
            ViewRootImpl.addConfigCallback(this);
        }

        @Override // android.view.AccessibilityIterators.AbstractTextSegmentIterator
        public void initialize(String str) {
            super.initialize(str);
            this.mImpl.setText(str);
        }

        @Override // android.view.AccessibilityIterators.TextSegmentIterator
        public int[] following(int i) {
            int length = this.mText.length();
            if (length <= 0 || i >= length) {
                return null;
            }
            if (i < 0) {
                i = 0;
            }
            while (!this.mImpl.isBoundary(i)) {
                i = this.mImpl.following(i);
                if (i == -1) {
                    return null;
                }
            }
            int following = this.mImpl.following(i);
            if (following == -1) {
                return null;
            }
            return getRange(i, following);
        }

        @Override // android.view.AccessibilityIterators.TextSegmentIterator
        public int[] preceding(int i) {
            int length = this.mText.length();
            if (length <= 0 || i <= 0) {
                return null;
            }
            if (i > length) {
                i = length;
            }
            while (!this.mImpl.isBoundary(i)) {
                i = this.mImpl.preceding(i);
                if (i == -1) {
                    return null;
                }
            }
            int preceding = this.mImpl.preceding(i);
            if (preceding == -1) {
                return null;
            }
            return getRange(preceding, i);
        }

        @Override // android.view.ViewRootImpl.ConfigChangedCallback
        public void onConfigurationChanged(Configuration configuration) {
            Locale locale = configuration.getLocales().get(0);
            if (locale == null || this.mLocale.equals(locale)) {
                return;
            }
            this.mLocale = locale;
            onLocaleChanged(locale);
        }

        protected void onLocaleChanged(Locale locale) {
            this.mImpl = BreakIterator.getCharacterInstance(locale);
        }
    }

    static class WordTextSegmentIterator extends CharacterTextSegmentIterator {
        private static WordTextSegmentIterator sInstance;

        public static WordTextSegmentIterator getInstance(Locale locale) {
            if (sInstance == null) {
                sInstance = new WordTextSegmentIterator(locale);
            }
            return sInstance;
        }

        private WordTextSegmentIterator(Locale locale) {
            super(locale);
        }

        @Override // android.view.AccessibilityIterators.CharacterTextSegmentIterator
        protected void onLocaleChanged(Locale locale) {
            this.mImpl = BreakIterator.getWordInstance(locale);
        }

        @Override // android.view.AccessibilityIterators.CharacterTextSegmentIterator, android.view.AccessibilityIterators.TextSegmentIterator
        public int[] following(int i) {
            if (this.mText.length() <= 0 || i >= this.mText.length()) {
                return null;
            }
            if (i < 0) {
                i = 0;
            }
            while (!isLetterOrDigit(i) && !isStartBoundary(i)) {
                i = this.mImpl.following(i);
                if (i == -1) {
                    return null;
                }
            }
            int following = this.mImpl.following(i);
            if (following == -1 || !isEndBoundary(following)) {
                return null;
            }
            return getRange(i, following);
        }

        @Override // android.view.AccessibilityIterators.CharacterTextSegmentIterator, android.view.AccessibilityIterators.TextSegmentIterator
        public int[] preceding(int i) {
            int length = this.mText.length();
            if (length <= 0 || i <= 0) {
                return null;
            }
            if (i > length) {
                i = length;
            }
            while (i > 0 && !isLetterOrDigit(i - 1) && !isEndBoundary(i)) {
                i = this.mImpl.preceding(i);
                if (i == -1) {
                    return null;
                }
            }
            int preceding = this.mImpl.preceding(i);
            if (preceding == -1 || !isStartBoundary(preceding)) {
                return null;
            }
            return getRange(preceding, i);
        }

        private boolean isStartBoundary(int i) {
            if (isLetterOrDigit(i)) {
                return i == 0 || !isLetterOrDigit(i - 1);
            }
            return false;
        }

        private boolean isEndBoundary(int i) {
            if (i <= 0 || !isLetterOrDigit(i - 1)) {
                return false;
            }
            return i == this.mText.length() || !isLetterOrDigit(i);
        }

        private boolean isLetterOrDigit(int i) {
            if (i < 0 || i >= this.mText.length()) {
                return false;
            }
            return Character.isLetterOrDigit(this.mText.codePointAt(i));
        }
    }

    static class ParagraphTextSegmentIterator extends AbstractTextSegmentIterator {
        private static ParagraphTextSegmentIterator sInstance;

        ParagraphTextSegmentIterator() {
        }

        public static ParagraphTextSegmentIterator getInstance() {
            if (sInstance == null) {
                sInstance = new ParagraphTextSegmentIterator();
            }
            return sInstance;
        }

        /* JADX WARN: Code restructure failed: missing block: B:18:0x0027, code lost:
        
            return null;
         */
        @Override // android.view.AccessibilityIterators.TextSegmentIterator
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public int[] following(int r5) {
            /*
                r4 = this;
                java.lang.String r0 = r4.mText
                int r0 = r0.length()
                r1 = 0
                if (r0 > 0) goto La
                return r1
            La:
                if (r5 < r0) goto Ld
                return r1
            Ld:
                if (r5 >= 0) goto L10
                r5 = 0
            L10:
                if (r5 >= r0) goto L25
                java.lang.String r2 = r4.mText
                char r2 = r2.charAt(r5)
                r3 = 10
                if (r2 != r3) goto L25
                boolean r2 = r4.isStartBoundary(r5)
                if (r2 != 0) goto L25
                int r5 = r5 + 1
                goto L10
            L25:
                if (r5 < r0) goto L28
                return r1
            L28:
                int r1 = r5 + 1
            L2a:
                if (r1 >= r0) goto L35
                boolean r2 = r4.isEndBoundary(r1)
                if (r2 != 0) goto L35
                int r1 = r1 + 1
                goto L2a
            L35:
                int[] r4 = r4.getRange(r5, r1)
                return r4
            */
            throw new UnsupportedOperationException("Method not decompiled: android.view.AccessibilityIterators.ParagraphTextSegmentIterator.following(int):int[]");
        }

        /* JADX WARN: Code restructure failed: missing block: B:18:0x0029, code lost:
        
            return null;
         */
        @Override // android.view.AccessibilityIterators.TextSegmentIterator
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public int[] preceding(int r4) {
            /*
                r3 = this;
                java.lang.String r0 = r3.mText
                int r0 = r0.length()
                r1 = 0
                if (r0 > 0) goto La
                return r1
            La:
                if (r4 > 0) goto Ld
                return r1
            Ld:
                if (r4 <= r0) goto L10
                r4 = r0
            L10:
                if (r4 <= 0) goto L27
                java.lang.String r0 = r3.mText
                int r2 = r4 + (-1)
                char r0 = r0.charAt(r2)
                r2 = 10
                if (r0 != r2) goto L27
                boolean r0 = r3.isEndBoundary(r4)
                if (r0 != 0) goto L27
                int r4 = r4 + (-1)
                goto L10
            L27:
                if (r4 > 0) goto L2a
                return r1
            L2a:
                int r0 = r4 + (-1)
            L2c:
                if (r0 <= 0) goto L37
                boolean r1 = r3.isStartBoundary(r0)
                if (r1 != 0) goto L37
                int r0 = r0 + (-1)
                goto L2c
            L37:
                int[] r3 = r3.getRange(r0, r4)
                return r3
            */
            throw new UnsupportedOperationException("Method not decompiled: android.view.AccessibilityIterators.ParagraphTextSegmentIterator.preceding(int):int[]");
        }

        private boolean isStartBoundary(int i) {
            if (this.mText.charAt(i) != '\n') {
                return i == 0 || this.mText.charAt(i - 1) == '\n';
            }
            return false;
        }

        private boolean isEndBoundary(int i) {
            if (i <= 0 || this.mText.charAt(i - 1) == '\n') {
                return false;
            }
            return i == this.mText.length() || this.mText.charAt(i) == '\n';
        }
    }
}
