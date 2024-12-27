package android.view;

import android.content.res.Configuration;

import java.text.BreakIterator;
import java.util.Locale;

/* loaded from: classes4.dex */
public final class AccessibilityIterators {

    public interface TextSegmentIterator {
        int[] following(int i);

        int[] preceding(int i);
    }

    public abstract static class AbstractTextSegmentIterator implements TextSegmentIterator {
        private final int[] mSegment = new int[2];
        protected String mText;

        public void initialize(String text) {
            this.mText = text;
        }

        protected int[] getRange(int start, int end) {
            if (start < 0 || end < 0 || start == end) {
                return null;
            }
            this.mSegment[0] = start;
            this.mSegment[1] = end;
            return this.mSegment;
        }
    }

    static class CharacterTextSegmentIterator extends AbstractTextSegmentIterator
            implements ViewRootImpl.ConfigChangedCallback {
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
        public void initialize(String text) {
            super.initialize(text);
            this.mImpl.setText(text);
        }

        @Override // android.view.AccessibilityIterators.TextSegmentIterator
        public int[] following(int offset) {
            int textLegth = this.mText.length();
            if (textLegth <= 0 || offset >= textLegth) {
                return null;
            }
            int start = offset;
            if (start < 0) {
                start = 0;
            }
            while (!this.mImpl.isBoundary(start)) {
                start = this.mImpl.following(start);
                if (start == -1) {
                    return null;
                }
            }
            int end = this.mImpl.following(start);
            if (end == -1) {
                return null;
            }
            return getRange(start, end);
        }

        @Override // android.view.AccessibilityIterators.TextSegmentIterator
        public int[] preceding(int offset) {
            int textLegth = this.mText.length();
            if (textLegth <= 0 || offset <= 0) {
                return null;
            }
            int end = offset;
            if (end > textLegth) {
                end = textLegth;
            }
            while (!this.mImpl.isBoundary(end)) {
                end = this.mImpl.preceding(end);
                if (end == -1) {
                    return null;
                }
            }
            int start = this.mImpl.preceding(end);
            if (start == -1) {
                return null;
            }
            return getRange(start, end);
        }

        @Override // android.view.ViewRootImpl.ConfigChangedCallback
        public void onConfigurationChanged(Configuration globalConfig) {
            Locale locale = globalConfig.getLocales().get(0);
            if (locale != null && !this.mLocale.equals(locale)) {
                this.mLocale = locale;
                onLocaleChanged(locale);
            }
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

        @Override // android.view.AccessibilityIterators.CharacterTextSegmentIterator,
                  // android.view.AccessibilityIterators.TextSegmentIterator
        public int[] following(int offset) {
            int textLegth = this.mText.length();
            if (textLegth <= 0 || offset >= this.mText.length()) {
                return null;
            }
            int start = offset;
            if (start < 0) {
                start = 0;
            }
            while (!isLetterOrDigit(start) && !isStartBoundary(start)) {
                start = this.mImpl.following(start);
                if (start == -1) {
                    return null;
                }
            }
            int end = this.mImpl.following(start);
            if (end == -1 || !isEndBoundary(end)) {
                return null;
            }
            return getRange(start, end);
        }

        @Override // android.view.AccessibilityIterators.CharacterTextSegmentIterator,
                  // android.view.AccessibilityIterators.TextSegmentIterator
        public int[] preceding(int offset) {
            int textLegth = this.mText.length();
            if (textLegth <= 0 || offset <= 0) {
                return null;
            }
            int end = offset;
            if (end > textLegth) {
                end = textLegth;
            }
            while (end > 0 && !isLetterOrDigit(end - 1) && !isEndBoundary(end)) {
                end = this.mImpl.preceding(end);
                if (end == -1) {
                    return null;
                }
            }
            int start = this.mImpl.preceding(end);
            if (start == -1 || !isStartBoundary(start)) {
                return null;
            }
            return getRange(start, end);
        }

        private boolean isStartBoundary(int index) {
            return isLetterOrDigit(index) && (index == 0 || !isLetterOrDigit(index + (-1)));
        }

        private boolean isEndBoundary(int index) {
            return index > 0
                    && isLetterOrDigit(index + (-1))
                    && (index == this.mText.length() || !isLetterOrDigit(index));
        }

        private boolean isLetterOrDigit(int index) {
            if (index >= 0 && index < this.mText.length()) {
                int codePoint = this.mText.codePointAt(index);
                return Character.isLetterOrDigit(codePoint);
            }
            return false;
        }
    }

    static class ParagraphTextSegmentIterator extends AbstractTextSegmentIterator {
        private static ParagraphTextSegmentIterator sInstance;

        ParagraphTextSegmentIterator() {}

        public static ParagraphTextSegmentIterator getInstance() {
            if (sInstance == null) {
                sInstance = new ParagraphTextSegmentIterator();
            }
            return sInstance;
        }

        /* JADX WARN: Code restructure failed: missing block: B:19:0x0028, code lost:

           return null;
        */
        @Override // android.view.AccessibilityIterators.TextSegmentIterator
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public int[] following(int r6) {
            /*
                r5 = this;
                java.lang.String r0 = r5.mText
                int r0 = r0.length()
                r1 = 0
                if (r0 > 0) goto La
                return r1
            La:
                if (r6 < r0) goto Ld
                return r1
            Ld:
                r2 = r6
                if (r2 >= 0) goto L11
                r2 = 0
            L11:
                if (r2 >= r0) goto L26
                java.lang.String r3 = r5.mText
                char r3 = r3.charAt(r2)
                r4 = 10
                if (r3 != r4) goto L26
                boolean r3 = r5.isStartBoundary(r2)
                if (r3 != 0) goto L26
                int r2 = r2 + 1
                goto L11
            L26:
                if (r2 < r0) goto L29
                return r1
            L29:
                int r1 = r2 + 1
            L2b:
                if (r1 >= r0) goto L36
                boolean r3 = r5.isEndBoundary(r1)
                if (r3 != 0) goto L36
                int r1 = r1 + 1
                goto L2b
            L36:
                int[] r3 = r5.getRange(r2, r1)
                return r3
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " android.view.AccessibilityIterators.ParagraphTextSegmentIterator.following(int):int[]");
        }

        /* JADX WARN: Code restructure failed: missing block: B:19:0x002a, code lost:

           return null;
        */
        @Override // android.view.AccessibilityIterators.TextSegmentIterator
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public int[] preceding(int r6) {
            /*
                r5 = this;
                java.lang.String r0 = r5.mText
                int r0 = r0.length()
                r1 = 0
                if (r0 > 0) goto La
                return r1
            La:
                if (r6 > 0) goto Ld
                return r1
            Ld:
                r2 = r6
                if (r2 <= r0) goto L11
                r2 = r0
            L11:
                if (r2 <= 0) goto L28
                java.lang.String r3 = r5.mText
                int r4 = r2 + (-1)
                char r3 = r3.charAt(r4)
                r4 = 10
                if (r3 != r4) goto L28
                boolean r3 = r5.isEndBoundary(r2)
                if (r3 != 0) goto L28
                int r2 = r2 + (-1)
                goto L11
            L28:
                if (r2 > 0) goto L2b
                return r1
            L2b:
                int r1 = r2 + (-1)
            L2d:
                if (r1 <= 0) goto L38
                boolean r3 = r5.isStartBoundary(r1)
                if (r3 != 0) goto L38
                int r1 = r1 + (-1)
                goto L2d
            L38:
                int[] r3 = r5.getRange(r1, r2)
                return r3
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " android.view.AccessibilityIterators.ParagraphTextSegmentIterator.preceding(int):int[]");
        }

        private boolean isStartBoundary(int index) {
            return this.mText.charAt(index) != '\n'
                    && (index == 0 || this.mText.charAt(index + (-1)) == '\n');
        }

        private boolean isEndBoundary(int index) {
            return index > 0
                    && this.mText.charAt(index + (-1)) != '\n'
                    && (index == this.mText.length() || this.mText.charAt(index) == '\n');
        }
    }
}
