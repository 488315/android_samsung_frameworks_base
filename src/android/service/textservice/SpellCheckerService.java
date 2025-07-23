package android.service.textservice;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.text.TextUtils;
import android.text.method.WordIterator;
import android.view.textservice.SentenceSuggestionsInfo;
import android.view.textservice.SuggestionsInfo;
import android.view.textservice.TextInfo;
import com.android.internal.textservice.ISpellCheckerService;
import com.android.internal.textservice.ISpellCheckerServiceCallback;
import com.android.internal.textservice.ISpellCheckerSession;
import com.android.internal.textservice.ISpellCheckerSessionListener;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Locale;

/* loaded from: classes3.dex */
public abstract class SpellCheckerService extends Service {
    private static final boolean DBG = false;
    public static final String SERVICE_INTERFACE = "android.service.textservice.SpellCheckerService";
    private static final String TAG = "SpellCheckerService";
    private final SpellCheckerServiceBinder mBinder = new SpellCheckerServiceBinder(this);

    public abstract Session createSession();

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return this.mBinder;
    }

    public static abstract class Session {
        private InternalISpellCheckerSession mInternalSession;
        private volatile SentenceLevelAdapter mSentenceLevelAdapter;

        public void onCancel() {
        }

        public void onClose() {
        }

        public abstract void onCreate();

        public abstract SuggestionsInfo onGetSuggestions(TextInfo textInfo, int i);

        public final void setInternalISpellCheckerSession(InternalISpellCheckerSession internalISpellCheckerSession) {
            this.mInternalSession = internalISpellCheckerSession;
        }

        public SuggestionsInfo[] onGetSuggestionsMultiple(TextInfo[] textInfoArr, int i, boolean z) {
            int length = textInfoArr.length;
            SuggestionsInfo[] suggestionsInfoArr = new SuggestionsInfo[length];
            for (int i2 = 0; i2 < length; i2++) {
                SuggestionsInfo onGetSuggestions = onGetSuggestions(textInfoArr[i2], i);
                suggestionsInfoArr[i2] = onGetSuggestions;
                onGetSuggestions.setCookieAndSequence(textInfoArr[i2].getCookie(), textInfoArr[i2].getSequence());
            }
            return suggestionsInfoArr;
        }

        public SentenceSuggestionsInfo[] onGetSentenceSuggestionsMultiple(TextInfo[] textInfoArr, int i) {
            if (textInfoArr == null || textInfoArr.length == 0) {
                return SentenceLevelAdapter.EMPTY_SENTENCE_SUGGESTIONS_INFOS;
            }
            if (this.mSentenceLevelAdapter == null) {
                synchronized (this) {
                    if (this.mSentenceLevelAdapter == null) {
                        String locale = getLocale();
                        if (!TextUtils.isEmpty(locale)) {
                            this.mSentenceLevelAdapter = new SentenceLevelAdapter(new Locale(locale));
                        }
                    }
                }
            }
            if (this.mSentenceLevelAdapter == null) {
                return SentenceLevelAdapter.EMPTY_SENTENCE_SUGGESTIONS_INFOS;
            }
            int length = textInfoArr.length;
            SentenceSuggestionsInfo[] sentenceSuggestionsInfoArr = new SentenceSuggestionsInfo[length];
            for (int i2 = 0; i2 < length; i2++) {
                SentenceLevelAdapter.SentenceTextInfoParams splitWords = this.mSentenceLevelAdapter.getSplitWords(textInfoArr[i2]);
                ArrayList<SentenceLevelAdapter.SentenceWordItem> arrayList = splitWords.mItems;
                int size = arrayList.size();
                TextInfo[] textInfoArr2 = new TextInfo[size];
                for (int i3 = 0; i3 < size; i3++) {
                    textInfoArr2[i3] = arrayList.get(i3).mTextInfo;
                }
                sentenceSuggestionsInfoArr[i2] = SentenceLevelAdapter.reconstructSuggestions(splitWords, onGetSuggestionsMultiple(textInfoArr2, i, true));
            }
            return sentenceSuggestionsInfoArr;
        }

        public String getLocale() {
            return this.mInternalSession.getLocale();
        }

        public Bundle getBundle() {
            return this.mInternalSession.getBundle();
        }

        public int getSupportedAttributes() {
            return this.mInternalSession.getSupportedAttributes();
        }
    }

    private static class InternalISpellCheckerSession extends ISpellCheckerSession.Stub {
        private final Bundle mBundle;
        private ISpellCheckerSessionListener mListener;
        private final String mLocale;
        private final Session mSession;
        private final int mSupportedAttributes;

        public InternalISpellCheckerSession(String str, ISpellCheckerSessionListener iSpellCheckerSessionListener, Bundle bundle, Session session, int i) {
            this.mListener = iSpellCheckerSessionListener;
            this.mSession = session;
            this.mLocale = str;
            this.mBundle = bundle;
            this.mSupportedAttributes = i;
            session.setInternalISpellCheckerSession(this);
        }

        @Override // com.android.internal.textservice.ISpellCheckerSession
        public void onGetSuggestionsMultiple(TextInfo[] textInfoArr, int i, boolean z) {
            int threadPriority = Process.getThreadPriority(Process.myTid());
            try {
                Process.setThreadPriority(10);
                this.mListener.onGetSuggestions(this.mSession.onGetSuggestionsMultiple(textInfoArr, i, z));
            } catch (RemoteException unused) {
            } finally {
                Process.setThreadPriority(threadPriority);
            }
        }

        @Override // com.android.internal.textservice.ISpellCheckerSession
        public void onGetSentenceSuggestionsMultiple(TextInfo[] textInfoArr, int i) {
            try {
                this.mListener.onGetSentenceSuggestions(this.mSession.onGetSentenceSuggestionsMultiple(textInfoArr, i));
            } catch (RemoteException unused) {
            }
        }

        @Override // com.android.internal.textservice.ISpellCheckerSession
        public void onCancel() {
            int threadPriority = Process.getThreadPriority(Process.myTid());
            try {
                Process.setThreadPriority(10);
                this.mSession.onCancel();
            } finally {
                Process.setThreadPriority(threadPriority);
            }
        }

        @Override // com.android.internal.textservice.ISpellCheckerSession
        public void onClose() {
            int threadPriority = Process.getThreadPriority(Process.myTid());
            try {
                Process.setThreadPriority(10);
                this.mSession.onClose();
            } finally {
                Process.setThreadPriority(threadPriority);
                this.mListener = null;
            }
        }

        public String getLocale() {
            return this.mLocale;
        }

        public Bundle getBundle() {
            return this.mBundle;
        }

        public int getSupportedAttributes() {
            return this.mSupportedAttributes;
        }
    }

    private static class SpellCheckerServiceBinder extends ISpellCheckerService.Stub {
        private final WeakReference<SpellCheckerService> mInternalServiceRef;

        public SpellCheckerServiceBinder(SpellCheckerService spellCheckerService) {
            this.mInternalServiceRef = new WeakReference<>(spellCheckerService);
        }

        @Override // com.android.internal.textservice.ISpellCheckerService
        public void getISpellCheckerSession(String str, ISpellCheckerSessionListener iSpellCheckerSessionListener, Bundle bundle, int i, ISpellCheckerServiceCallback iSpellCheckerServiceCallback) {
            InternalISpellCheckerSession internalISpellCheckerSession;
            SpellCheckerService spellCheckerService = this.mInternalServiceRef.get();
            if (spellCheckerService == null) {
                internalISpellCheckerSession = null;
            } else {
                Session createSession = spellCheckerService.createSession();
                InternalISpellCheckerSession internalISpellCheckerSession2 = new InternalISpellCheckerSession(str, iSpellCheckerSessionListener, bundle, createSession, i);
                createSession.onCreate();
                internalISpellCheckerSession = internalISpellCheckerSession2;
            }
            try {
                iSpellCheckerServiceCallback.onSessionCreated(internalISpellCheckerSession);
            } catch (RemoteException unused) {
            }
        }
    }

    private static class SentenceLevelAdapter {
        public static final SentenceSuggestionsInfo[] EMPTY_SENTENCE_SUGGESTIONS_INFOS = new SentenceSuggestionsInfo[0];
        private static final SuggestionsInfo EMPTY_SUGGESTIONS_INFO = new SuggestionsInfo(0, null);
        private final WordIterator mWordIterator;

        public static class SentenceWordItem {
            public final int mLength;
            public final int mStart;
            public final TextInfo mTextInfo;

            public SentenceWordItem(TextInfo textInfo, int i, int i2) {
                this.mTextInfo = textInfo;
                this.mStart = i;
                this.mLength = i2 - i;
            }
        }

        public static class SentenceTextInfoParams {
            final ArrayList<SentenceWordItem> mItems;
            final TextInfo mOriginalTextInfo;
            final int mSize;

            public SentenceTextInfoParams(TextInfo textInfo, ArrayList<SentenceWordItem> arrayList) {
                this.mOriginalTextInfo = textInfo;
                this.mItems = arrayList;
                this.mSize = arrayList.size();
            }
        }

        public SentenceLevelAdapter(Locale locale) {
            this.mWordIterator = new WordIterator(locale);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public SentenceTextInfoParams getSplitWords(TextInfo textInfo) {
            WordIterator wordIterator = this.mWordIterator;
            String text = textInfo.getText();
            int cookie = textInfo.getCookie();
            int length = text.length();
            ArrayList arrayList = new ArrayList();
            wordIterator.setCharSequence(text, 0, text.length());
            int following = wordIterator.following(0);
            int i = following;
            int beginning = following == -1 ? -1 : wordIterator.getBeginning(following);
            while (beginning <= length && i != -1 && beginning != -1) {
                if (i >= 0 && i > beginning) {
                    CharSequence subSequence = text.subSequence(beginning, i);
                    arrayList.add(new SentenceWordItem(new TextInfo(subSequence, 0, subSequence.length(), cookie, subSequence.hashCode()), beginning, i));
                }
                i = wordIterator.following(i);
                if (i == -1) {
                    break;
                }
                beginning = wordIterator.getBeginning(i);
            }
            return new SentenceTextInfoParams(textInfo, arrayList);
        }

        public static SentenceSuggestionsInfo reconstructSuggestions(SentenceTextInfoParams sentenceTextInfoParams, SuggestionsInfo[] suggestionsInfoArr) {
            SuggestionsInfo suggestionsInfo;
            if (suggestionsInfoArr == null || suggestionsInfoArr.length == 0 || sentenceTextInfoParams == null) {
                return null;
            }
            int cookie = sentenceTextInfoParams.mOriginalTextInfo.getCookie();
            int sequence = sentenceTextInfoParams.mOriginalTextInfo.getSequence();
            int i = sentenceTextInfoParams.mSize;
            int[] iArr = new int[i];
            int[] iArr2 = new int[i];
            SuggestionsInfo[] suggestionsInfoArr2 = new SuggestionsInfo[i];
            for (int i2 = 0; i2 < i; i2++) {
                SentenceWordItem sentenceWordItem = sentenceTextInfoParams.mItems.get(i2);
                int i3 = 0;
                while (true) {
                    if (i3 >= suggestionsInfoArr.length) {
                        suggestionsInfo = null;
                        break;
                    }
                    suggestionsInfo = suggestionsInfoArr[i3];
                    if (suggestionsInfo != null && suggestionsInfo.getSequence() == sentenceWordItem.mTextInfo.getSequence()) {
                        suggestionsInfo.setCookieAndSequence(cookie, sequence);
                        break;
                    }
                    i3++;
                }
                iArr[i2] = sentenceWordItem.mStart;
                iArr2[i2] = sentenceWordItem.mLength;
                if (suggestionsInfo == null) {
                    suggestionsInfo = EMPTY_SUGGESTIONS_INFO;
                }
                suggestionsInfoArr2[i2] = suggestionsInfo;
            }
            return new SentenceSuggestionsInfo(suggestionsInfoArr2, iArr, iArr2);
        }
    }
}
