package android.widget;

import android.text.Editable;
import android.text.Selection;
import android.text.SpannedString;
import android.text.method.WordIterator;
import android.text.style.SpellCheckSpan;
import android.text.style.SuggestionSpan;
import android.util.Log;
import android.util.Range;
import android.view.textservice.SentenceSuggestionsInfo;
import android.view.textservice.SpellCheckerSession;
import android.view.textservice.SuggestionsInfo;
import android.view.textservice.TextInfo;
import android.view.textservice.TextServicesManager;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.GrowingArrayUtils;
import java.text.BreakIterator;
import java.util.Locale;

/* loaded from: classes5.dex */
public class SpellChecker implements SpellCheckerSession.SpellCheckerSessionListener {
    public static final int AVERAGE_WORD_LENGTH = 7;
    private static final boolean DBG = false;
    public static final int MAX_NUMBER_OF_WORDS = 50;
    private static final int MAX_SENTENCE_LENGTH = 350;
    private static final int SPELL_PAUSE_DURATION = 400;
    private static final String TAG = "SpellChecker";
    private static final int USE_SPAN_RANGE = -1;
    public static final int WORD_ITERATOR_INTERVAL = 350;
    final int mCookie;
    private Locale mCurrentLocale;
    private int[] mIds;
    private int mLength;
    private SentenceIteratorWrapper mSentenceIterator;
    private SpellCheckSpan[] mSpellCheckSpans;
    SpellCheckerSession mSpellCheckerSession;
    private Runnable mSpellRunnable;
    private TextServicesManager mTextServicesManager;
    private final TextView mTextView;
    private SpellParser[] mSpellParsers = new SpellParser[0];
    private int mSpanSequenceCounter = 0;

    private enum RemoveReason {
        REPLACE,
        OBSOLETE
    }

    public SpellChecker(TextView textView) {
        this.mTextView = textView;
        int[] newUnpaddedIntArray = ArrayUtils.newUnpaddedIntArray(1);
        this.mIds = newUnpaddedIntArray;
        this.mSpellCheckSpans = new SpellCheckSpan[newUnpaddedIntArray.length];
        setLocale(textView.getSpellCheckerLocale());
        this.mCookie = hashCode();
    }

    void resetSession() {
        closeSession();
        TextServicesManager textServicesManagerForUser = this.mTextView.getTextServicesManagerForUser();
        this.mTextServicesManager = textServicesManagerForUser;
        if (this.mCurrentLocale == null || textServicesManagerForUser == null || this.mTextView.length() == 0 || !this.mTextServicesManager.isSpellCheckerEnabled() || this.mTextServicesManager.getCurrentSpellCheckerSubtype(true) == null) {
            this.mSpellCheckerSession = null;
        } else {
            this.mSpellCheckerSession = this.mTextServicesManager.newSpellCheckerSession(new SpellCheckerSession.SpellCheckerSessionParams.Builder().setLocale(this.mCurrentLocale).setSupportedAttributes(27).build(), this.mTextView.getContext().getMainExecutor(), this);
        }
        for (int i = 0; i < this.mLength; i++) {
            this.mIds[i] = -1;
        }
        this.mLength = 0;
        TextView textView = this.mTextView;
        textView.removeMisspelledSpans((Editable) textView.getText());
    }

    private void setLocale(Locale locale) {
        this.mCurrentLocale = locale;
        resetSession();
        if (locale != null) {
            this.mSentenceIterator = new SentenceIteratorWrapper(BreakIterator.getSentenceInstance(locale));
        }
        this.mTextView.onLocaleChanged();
    }

    private boolean isSessionActive() {
        return this.mSpellCheckerSession != null;
    }

    public void closeSession() {
        SpellCheckerSession spellCheckerSession = this.mSpellCheckerSession;
        if (spellCheckerSession != null) {
            spellCheckerSession.close();
        }
        int length = this.mSpellParsers.length;
        for (int i = 0; i < length; i++) {
            this.mSpellParsers[i].stop();
        }
        Runnable runnable = this.mSpellRunnable;
        if (runnable != null) {
            this.mTextView.removeCallbacks(runnable);
        }
    }

    private int nextSpellCheckSpanIndex() {
        int i = 0;
        while (true) {
            int i2 = this.mLength;
            if (i < i2) {
                if (this.mIds[i] < 0) {
                    return i;
                }
                i++;
            } else {
                this.mIds = GrowingArrayUtils.append(this.mIds, i2, 0);
                this.mSpellCheckSpans = (SpellCheckSpan[]) GrowingArrayUtils.append(this.mSpellCheckSpans, this.mLength, new SpellCheckSpan());
                int i3 = this.mLength;
                this.mLength = i3 + 1;
                return i3;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addSpellCheckSpan(Editable editable, int i, int i2) {
        int nextSpellCheckSpanIndex = nextSpellCheckSpanIndex();
        SpellCheckSpan spellCheckSpan = this.mSpellCheckSpans[nextSpellCheckSpanIndex];
        editable.setSpan(spellCheckSpan, i, i2, 33);
        spellCheckSpan.setSpellCheckInProgress(false);
        int[] iArr = this.mIds;
        int i3 = this.mSpanSequenceCounter;
        this.mSpanSequenceCounter = i3 + 1;
        iArr[nextSpellCheckSpanIndex] = i3;
    }

    public void onSpellCheckSpanRemoved(SpellCheckSpan spellCheckSpan) {
        for (int i = 0; i < this.mLength; i++) {
            if (this.mSpellCheckSpans[i] == spellCheckSpan) {
                this.mIds[i] = -1;
                return;
            }
        }
    }

    public void onSelectionChanged() {
        spellCheck();
    }

    void onPerformSpellCheck() {
        spellCheck(0, this.mTextView.length(), true);
    }

    public void spellCheck(int i, int i2) {
        spellCheck(i, i2, false);
    }

    public void spellCheck(int i, int i2, boolean z) {
        Locale locale;
        Locale spellCheckerLocale = this.mTextView.getSpellCheckerLocale();
        boolean isSessionActive = isSessionActive();
        if (spellCheckerLocale == null || (locale = this.mCurrentLocale) == null || !locale.equals(spellCheckerLocale)) {
            setLocale(spellCheckerLocale);
            i2 = this.mTextView.getText().length();
            i = 0;
        } else {
            TextServicesManager textServicesManager = this.mTextServicesManager;
            if (isSessionActive != (textServicesManager != null && textServicesManager.isSpellCheckerEnabled())) {
                resetSession();
            }
        }
        if (isSessionActive) {
            int length = this.mSpellParsers.length;
            for (int i3 = 0; i3 < length; i3++) {
                SpellParser spellParser = this.mSpellParsers[i3];
                if (spellParser.isFinished()) {
                    spellParser.parse(i, i2, z);
                    return;
                }
            }
            SpellParser[] spellParserArr = new SpellParser[length + 1];
            System.arraycopy(this.mSpellParsers, 0, spellParserArr, 0, length);
            this.mSpellParsers = spellParserArr;
            SpellParser spellParser2 = new SpellParser();
            this.mSpellParsers[length] = spellParser2;
            spellParser2.parse(i, i2, z);
        }
    }

    private void spellCheck() {
        spellCheck(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void spellCheck(boolean z) {
        if (this.mSpellCheckerSession == null) {
            return;
        }
        Editable editable = (Editable) this.mTextView.getText();
        int selectionStart = Selection.getSelectionStart(editable);
        int selectionEnd = Selection.getSelectionEnd(editable);
        int i = this.mLength;
        TextInfo[] textInfoArr = new TextInfo[i];
        int i2 = 0;
        for (int i3 = 0; i3 < this.mLength; i3++) {
            SpellCheckSpan spellCheckSpan = this.mSpellCheckSpans[i3];
            if (this.mIds[i3] >= 0 && !spellCheckSpan.isSpellCheckInProgress()) {
                int spanStart = editable.getSpanStart(spellCheckSpan);
                int spanEnd = editable.getSpanEnd(spellCheckSpan);
                int i4 = spanEnd + 1;
                boolean z2 = !(selectionStart == i4 && WordIterator.isMidWordPunctuation(this.mCurrentLocale, Character.codePointBefore(editable, i4))) && (selectionEnd <= spanStart || selectionStart > spanEnd || (selectionStart == spanEnd && selectionStart > 0 && isSeparator(Character.codePointBefore(editable, selectionStart))));
                if (spanStart >= 0 && spanEnd > spanStart && (z || z2)) {
                    spellCheckSpan.setSpellCheckInProgress(true);
                    textInfoArr[i2] = new TextInfo(editable, spanStart, spanEnd, this.mCookie, this.mIds[i3]);
                    i2++;
                }
            }
        }
        if (i2 > 0) {
            if (i2 < i) {
                TextInfo[] textInfoArr2 = new TextInfo[i2];
                System.arraycopy(textInfoArr, 0, textInfoArr2, 0, i2);
                textInfoArr = textInfoArr2;
            }
            this.mSpellCheckerSession.getSentenceSuggestions(textInfoArr, 5);
        }
    }

    private static boolean isSeparator(int i) {
        return ((1 << Character.getType(i)) & 1634758656) != 0;
    }

    private SpellCheckSpan onGetSuggestionsInternal(SuggestionsInfo suggestionsInfo, int i, int i2) {
        if (suggestionsInfo != null && suggestionsInfo.getCookie() == this.mCookie) {
            Editable editable = (Editable) this.mTextView.getText();
            int sequence = suggestionsInfo.getSequence();
            int i3 = 0;
            while (i3 < this.mLength) {
                if (sequence == this.mIds[i3]) {
                    SpellCheckSpan spellCheckSpan = this.mSpellCheckSpans[i3];
                    int spanStart = editable.getSpanStart(spellCheckSpan);
                    if (spanStart < 0) {
                        return null;
                    }
                    int suggestionsAttributes = suggestionsInfo.getSuggestionsAttributes();
                    boolean z = (suggestionsAttributes & 1) > 0;
                    boolean z2 = (suggestionsAttributes & 2) > 0;
                    boolean z3 = (suggestionsAttributes & 8) > 0;
                    int i4 = spanStart + i;
                    int i5 = i4 + i2;
                    if (i5 <= editable.length()) {
                        if (!z && (z2 || z3)) {
                            createMisspelledSuggestionSpan(editable, suggestionsInfo, spellCheckSpan, i, i2);
                            return spellCheckSpan;
                        }
                        int i6 = i;
                        int spanEnd = editable.getSpanEnd(spellCheckSpan);
                        if (i6 == -1 || i2 == -1) {
                            i5 = spanEnd;
                            i4 = spanStart;
                        }
                        if (spanStart >= 0 && spanEnd > spanStart && i5 > i4) {
                            boolean isVisibleToAccessibility = this.mTextView.isVisibleToAccessibility();
                            SpannedString spannedString = isVisibleToAccessibility ? new SpannedString(editable) : null;
                            boolean removeErrorSuggestionSpan = removeErrorSuggestionSpan(editable, i4, i5, RemoveReason.OBSOLETE);
                            if (isVisibleToAccessibility && removeErrorSuggestionSpan) {
                                this.mTextView.sendAccessibilityEventTypeViewTextChanged(spannedString, i4, i5);
                            }
                        }
                    }
                    return spellCheckSpan;
                }
                i3++;
                suggestionsInfo = suggestionsInfo;
                i = i;
            }
        }
        return null;
    }

    private static boolean removeErrorSuggestionSpan(Editable editable, int i, int i2, RemoveReason removeReason) {
        boolean z = false;
        for (SuggestionSpan suggestionSpan : (SuggestionSpan[]) editable.getSpans(i, i2, SuggestionSpan.class)) {
            if (editable.getSpanStart(suggestionSpan) == i && editable.getSpanEnd(suggestionSpan) == i2 && (suggestionSpan.getFlags() & 10) != 0) {
                editable.removeSpan(suggestionSpan);
                z = true;
            }
        }
        return z;
    }

    @Override // android.view.textservice.SpellCheckerSession.SpellCheckerSessionListener
    public void onGetSuggestions(SuggestionsInfo[] suggestionsInfoArr) {
        Editable editable = (Editable) this.mTextView.getText();
        for (SuggestionsInfo suggestionsInfo : suggestionsInfoArr) {
            SpellCheckSpan onGetSuggestionsInternal = onGetSuggestionsInternal(suggestionsInfo, -1, -1);
            if (onGetSuggestionsInternal != null) {
                editable.removeSpan(onGetSuggestionsInternal);
            }
        }
        scheduleNewSpellCheck();
    }

    @Override // android.view.textservice.SpellCheckerSession.SpellCheckerSessionListener
    public void onGetSentenceSuggestions(SentenceSuggestionsInfo[] sentenceSuggestionsInfoArr) {
        Editable editable = (Editable) this.mTextView.getText();
        for (SentenceSuggestionsInfo sentenceSuggestionsInfo : sentenceSuggestionsInfoArr) {
            if (sentenceSuggestionsInfo != null) {
                SpellCheckSpan spellCheckSpan = null;
                for (int i = 0; i < sentenceSuggestionsInfo.getSuggestionsCount(); i++) {
                    SuggestionsInfo suggestionsInfoAt = sentenceSuggestionsInfo.getSuggestionsInfoAt(i);
                    if (suggestionsInfoAt != null) {
                        SpellCheckSpan onGetSuggestionsInternal = onGetSuggestionsInternal(suggestionsInfoAt, sentenceSuggestionsInfo.getOffsetAt(i), sentenceSuggestionsInfo.getLengthAt(i));
                        if (spellCheckSpan == null && onGetSuggestionsInternal != null) {
                            spellCheckSpan = onGetSuggestionsInternal;
                        }
                    }
                }
                if (spellCheckSpan != null) {
                    editable.removeSpan(spellCheckSpan);
                }
            }
        }
        scheduleNewSpellCheck();
    }

    private void scheduleNewSpellCheck() {
        Runnable runnable = this.mSpellRunnable;
        if (runnable == null) {
            this.mSpellRunnable = new Runnable() { // from class: android.widget.SpellChecker.1
                @Override // java.lang.Runnable
                public void run() {
                    int length = SpellChecker.this.mSpellParsers.length;
                    for (int i = 0; i < length; i++) {
                        SpellParser spellParser = SpellChecker.this.mSpellParsers[i];
                        if (!spellParser.isFinished()) {
                            spellParser.parse();
                            return;
                        }
                    }
                }
            };
        } else {
            this.mTextView.removeCallbacks(runnable);
        }
        this.mTextView.postDelayed(this.mSpellRunnable, 400L);
    }

    private void createMisspelledSuggestionSpan(Editable editable, SuggestionsInfo suggestionsInfo, SpellCheckSpan spellCheckSpan, int i, int i2) {
        String[] strArr;
        int spanStart = editable.getSpanStart(spellCheckSpan);
        int spanEnd = editable.getSpanEnd(spellCheckSpan);
        if (spanStart < 0 || spanEnd <= spanStart) {
            return;
        }
        if (i != -1 && i2 != -1) {
            spanStart += i;
            spanEnd = spanStart + i2;
        }
        int suggestionsCount = suggestionsInfo.getSuggestionsCount();
        if (suggestionsCount > 0) {
            strArr = new String[suggestionsCount];
            for (int i3 = 0; i3 < suggestionsCount; i3++) {
                strArr[i3] = suggestionsInfo.getSuggestionAt(i3);
            }
        } else {
            strArr = (String[]) ArrayUtils.emptyArray(String.class);
        }
        int suggestionsAttributes = suggestionsInfo.getSuggestionsAttributes();
        int i4 = (suggestionsAttributes & 16) == 0 ? 1 : 0;
        if ((suggestionsAttributes & 2) != 0) {
            i4 |= 2;
        }
        if ((suggestionsAttributes & 8) != 0) {
            i4 |= 8;
        }
        SuggestionSpan suggestionSpan = new SuggestionSpan(this.mTextView.getContext(), strArr, i4);
        boolean z = !removeErrorSuggestionSpan(editable, spanStart, spanEnd, RemoveReason.REPLACE) && this.mTextView.isVisibleToAccessibility();
        SpannedString spannedString = z ? new SpannedString(editable) : null;
        editable.setSpan(suggestionSpan, spanStart, spanEnd, 33);
        if (z) {
            this.mTextView.sendAccessibilityEventTypeViewTextChanged(spannedString, spanStart, spanEnd);
        }
        this.mTextView.invalidateRegion(spanStart, spanEnd, false);
    }

    private static class SentenceIteratorWrapper {
        private int mEndOffset;
        private BreakIterator mSentenceIterator;
        private int mStartOffset;

        SentenceIteratorWrapper(BreakIterator breakIterator) {
            this.mSentenceIterator = breakIterator;
        }

        public void setCharSequence(CharSequence charSequence, int i, int i2) {
            this.mStartOffset = Math.max(0, i);
            int min = Math.min(i2, charSequence.length());
            this.mEndOffset = min;
            this.mSentenceIterator.setText(charSequence.subSequence(this.mStartOffset, min).toString());
        }

        public int preceding(int i) {
            int preceding;
            int i2 = this.mStartOffset;
            if (i >= i2 && (preceding = this.mSentenceIterator.preceding(i - i2)) != -1) {
                return preceding + this.mStartOffset;
            }
            return -1;
        }

        public int following(int i) {
            int following;
            if (i <= this.mEndOffset && (following = this.mSentenceIterator.following(i - this.mStartOffset)) != -1) {
                return following + this.mStartOffset;
            }
            return -1;
        }

        public boolean isBoundary(int i) {
            int i2 = this.mStartOffset;
            if (i < i2 || i > this.mEndOffset) {
                return false;
            }
            return this.mSentenceIterator.isBoundary(i - i2);
        }
    }

    private class SpellParser {
        private boolean mForceCheckWhenEditingWord;
        private Object mRange;

        private SpellParser() {
            this.mRange = new Object();
        }

        public void parse(int i, int i2, boolean z) {
            this.mForceCheckWhenEditingWord = z;
            int length = SpellChecker.this.mTextView.length();
            if (i2 > length) {
                Log.w(SpellChecker.TAG, "Parse invalid region, from " + i + " to " + i2);
                i2 = length;
            }
            if (i2 > i) {
                setRangeSpan((Editable) SpellChecker.this.mTextView.getText(), i, i2);
                parse();
            }
        }

        public boolean isFinished() {
            return ((Editable) SpellChecker.this.mTextView.getText()).getSpanStart(this.mRange) < 0;
        }

        public void stop() {
            removeRangeSpan((Editable) SpellChecker.this.mTextView.getText());
            this.mForceCheckWhenEditingWord = false;
        }

        private void setRangeSpan(Editable editable, int i, int i2) {
            editable.setSpan(this.mRange, i, i2, 33);
        }

        private void removeRangeSpan(Editable editable) {
            editable.removeSpan(this.mRange);
        }

        public void parse() {
            Editable editable = (Editable) SpellChecker.this.mTextView.getText();
            int spanStart = editable.getSpanStart(this.mRange);
            int spanEnd = editable.getSpanEnd(this.mRange);
            Range detectSentenceBoundary = SpellChecker.this.detectSentenceBoundary(editable, spanStart, spanEnd);
            int intValue = ((Integer) detectSentenceBoundary.getLower()).intValue();
            int intValue2 = ((Integer) detectSentenceBoundary.getUpper()).intValue();
            if (intValue == intValue2) {
                stop();
                return;
            }
            boolean z = true;
            boolean z2 = intValue2 < spanEnd;
            int i = intValue;
            int i2 = 0;
            while (true) {
                if (i2 >= SpellChecker.this.mLength) {
                    break;
                }
                SpellCheckSpan spellCheckSpan = SpellChecker.this.mSpellCheckSpans[i2];
                if (SpellChecker.this.mIds[i2] >= 0 && !spellCheckSpan.isSpellCheckInProgress()) {
                    int spanStart2 = editable.getSpanStart(spellCheckSpan);
                    int spanEnd2 = editable.getSpanEnd(spellCheckSpan);
                    if (spanEnd2 >= i && intValue2 >= spanStart2) {
                        if (spanStart2 <= i && intValue2 <= spanEnd2) {
                            z = false;
                            break;
                        } else {
                            editable.removeSpan(spellCheckSpan);
                            i = Math.min(spanStart2, i);
                            intValue2 = Math.max(spanEnd2, intValue2);
                        }
                    }
                }
                i2++;
            }
            if (intValue2 <= i) {
                Log.w(SpellChecker.TAG, "Trying to spellcheck invalid region, from " + intValue + " to " + intValue2);
            } else if (z) {
                SpellChecker.this.addSpellCheckSpan(editable, i, intValue2);
            }
            if (z2 && intValue2 != -1 && intValue2 <= spanEnd) {
                setRangeSpan(editable, intValue2, spanEnd);
            } else {
                removeRangeSpan(editable);
            }
            SpellChecker.this.spellCheck(this.mForceCheckWhenEditingWord);
        }

        private <T> void removeSpansAt(Editable editable, int i, T[] tArr) {
            for (T t : tArr) {
                if (editable.getSpanStart(t) <= i && editable.getSpanEnd(t) >= i) {
                    editable.removeSpan(t);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Range<Integer> detectSentenceBoundary(CharSequence charSequence, int i, int i2) {
        int findSeparator = findSeparator(charSequence, Math.max(0, i - 350), Math.max(0, i - 700));
        int findSeparator2 = findSeparator(charSequence, Math.min(i + 700, i2), Math.min(i + 1050, charSequence.length()));
        this.mSentenceIterator.setCharSequence(charSequence, findSeparator, findSeparator2);
        int preceding = this.mSentenceIterator.isBoundary(i) ? i : this.mSentenceIterator.preceding(i);
        int following = this.mSentenceIterator.following(preceding);
        if (following != -1) {
            findSeparator2 = following;
        }
        if (findSeparator2 - preceding <= 350) {
            while (findSeparator2 < i2) {
                int following2 = this.mSentenceIterator.following(findSeparator2);
                if (following2 == -1 || following2 - preceding > 350) {
                    break;
                }
                findSeparator2 = following2;
            }
        } else if (findSeparator2 - i > 350) {
            findSeparator2 = findSeparator(charSequence, i + 350, findSeparator2);
            preceding = roundUpToWordStart(charSequence, i, preceding);
        } else {
            preceding = roundUpToWordStart(charSequence, findSeparator2 - 350, preceding);
        }
        return new Range<>(Integer.valueOf(preceding), Integer.valueOf(Math.max(preceding, findSeparator2)));
    }

    private int roundUpToWordStart(CharSequence charSequence, int i, int i2) {
        if (isSeparator(charSequence.charAt(i))) {
            return i;
        }
        int findSeparator = findSeparator(charSequence, i, i2);
        return findSeparator != i2 ? findSeparator + 1 : i2;
    }

    private static int findSeparator(CharSequence charSequence, int i, int i2) {
        int i3 = i < i2 ? 1 : -1;
        while (i != i2) {
            if (isSeparator(charSequence.charAt(i))) {
                return i;
            }
            i += i3;
        }
        return i2;
    }

    public static boolean haveWordBoundariesChanged(Editable editable, int i, int i2, int i3, int i4) {
        if (i4 != i && i3 != i2) {
            return true;
        }
        if (i4 == i && i < editable.length()) {
            return Character.isLetterOrDigit(Character.codePointAt(editable, i));
        }
        if (i3 != i2 || i2 <= 0) {
            return false;
        }
        return Character.isLetterOrDigit(Character.codePointBefore(editable, i2));
    }
}
