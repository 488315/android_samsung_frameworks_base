package android.view.textservice;

import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import com.android.internal.textservice.ISpellCheckerSession;
import com.android.internal.textservice.ISpellCheckerSessionListener;
import com.android.internal.textservice.ITextServicesSessionListener;
import dalvik.system.CloseGuard;
import java.util.ArrayDeque;
import java.util.Locale;
import java.util.Queue;
import java.util.concurrent.Executor;

/* loaded from: classes4.dex */
public class SpellCheckerSession {
    private static final boolean DBG = false;
    private static final int MSG_ON_GET_SUGGESTION_MULTIPLE = 1;
    private static final int MSG_ON_GET_SUGGESTION_MULTIPLE_FOR_SENTENCE = 2;
    public static final String SERVICE_META_DATA = "android.view.textservice.scs";
    private static final String TAG = "SpellCheckerSession";
    private final Executor mExecutor;
    private final CloseGuard mGuard;
    private final InternalListener mInternalListener;
    private final SpellCheckerInfo mSpellCheckerInfo;
    private final SpellCheckerSessionListener mSpellCheckerSessionListener;
    private final SpellCheckerSessionListenerImpl mSpellCheckerSessionListenerImpl;
    private final TextServicesManager mTextServicesManager;

    public interface SpellCheckerSessionListener {
        void onGetSentenceSuggestions(SentenceSuggestionsInfo[] sentenceSuggestionsInfoArr);

        void onGetSuggestions(SuggestionsInfo[] suggestionsInfoArr);
    }

    public SpellCheckerSession(SpellCheckerInfo spellCheckerInfo, TextServicesManager textServicesManager, SpellCheckerSessionListener spellCheckerSessionListener, Executor executor) {
        CloseGuard closeGuard = CloseGuard.get();
        this.mGuard = closeGuard;
        if (spellCheckerInfo == null || spellCheckerSessionListener == null || textServicesManager == null) {
            throw null;
        }
        this.mSpellCheckerInfo = spellCheckerInfo;
        SpellCheckerSessionListenerImpl spellCheckerSessionListenerImpl = new SpellCheckerSessionListenerImpl(this);
        this.mSpellCheckerSessionListenerImpl = spellCheckerSessionListenerImpl;
        this.mInternalListener = new InternalListener(spellCheckerSessionListenerImpl);
        this.mTextServicesManager = textServicesManager;
        this.mSpellCheckerSessionListener = spellCheckerSessionListener;
        this.mExecutor = executor;
        closeGuard.open("finishSession");
    }

    public boolean isSessionDisconnected() {
        return this.mSpellCheckerSessionListenerImpl.isDisconnected();
    }

    public SpellCheckerInfo getSpellChecker() {
        return this.mSpellCheckerInfo;
    }

    public void cancel() {
        this.mSpellCheckerSessionListenerImpl.cancel();
    }

    public void close() {
        this.mGuard.close();
        this.mSpellCheckerSessionListenerImpl.close();
        this.mTextServicesManager.finishSpellCheckerService(this.mSpellCheckerSessionListenerImpl);
    }

    public void getSentenceSuggestions(TextInfo[] textInfoArr, int i) {
        InputMethodManager inputMethodManager = this.mTextServicesManager.getInputMethodManager();
        if (inputMethodManager != null && inputMethodManager.isInputMethodSuppressingSpellChecker()) {
            handleOnGetSentenceSuggestionsMultiple(new SentenceSuggestionsInfo[0]);
        } else {
            this.mSpellCheckerSessionListenerImpl.getSentenceSuggestionsMultiple(textInfoArr, i);
        }
    }

    @Deprecated
    public void getSuggestions(TextInfo textInfo, int i) {
        getSuggestions(new TextInfo[]{textInfo}, i, false);
    }

    @Deprecated
    public void getSuggestions(TextInfo[] textInfoArr, int i, boolean z) {
        InputMethodManager inputMethodManager = this.mTextServicesManager.getInputMethodManager();
        if (inputMethodManager != null && inputMethodManager.isInputMethodSuppressingSpellChecker()) {
            handleOnGetSuggestionsMultiple(new SuggestionsInfo[0]);
        } else {
            this.mSpellCheckerSessionListenerImpl.getSuggestionsMultiple(textInfoArr, i, z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleOnGetSuggestionsMultiple$0(SuggestionsInfo[] suggestionsInfoArr) {
        this.mSpellCheckerSessionListener.onGetSuggestions(suggestionsInfoArr);
    }

    void handleOnGetSuggestionsMultiple(final SuggestionsInfo[] suggestionsInfoArr) {
        this.mExecutor.execute(new Runnable() { // from class: android.view.textservice.SpellCheckerSession$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                SpellCheckerSession.this.lambda$handleOnGetSuggestionsMultiple$0(suggestionsInfoArr);
            }
        });
    }

    void handleOnGetSentenceSuggestionsMultiple(final SentenceSuggestionsInfo[] sentenceSuggestionsInfoArr) {
        this.mExecutor.execute(new Runnable() { // from class: android.view.textservice.SpellCheckerSession$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                SpellCheckerSession.this.lambda$handleOnGetSentenceSuggestionsMultiple$1(sentenceSuggestionsInfoArr);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleOnGetSentenceSuggestionsMultiple$1(SentenceSuggestionsInfo[] sentenceSuggestionsInfoArr) {
        this.mSpellCheckerSessionListener.onGetSentenceSuggestions(sentenceSuggestionsInfoArr);
    }

    private static final class SpellCheckerSessionListenerImpl extends ISpellCheckerSessionListener.Stub {
        private static final int STATE_CLOSED_AFTER_CONNECTION = 2;
        private static final int STATE_CLOSED_BEFORE_CONNECTION = 3;
        private static final int STATE_CONNECTED = 1;
        private static final int STATE_WAIT_CONNECTION = 0;
        private static final int TASK_CANCEL = 1;
        private static final int TASK_CLOSE = 3;
        private static final int TASK_GET_SUGGESTIONS_MULTIPLE = 2;
        private static final int TASK_GET_SUGGESTIONS_MULTIPLE_FOR_SENTENCE = 4;
        private Handler mAsyncHandler;
        private ISpellCheckerSession mISpellCheckerSession;
        private SpellCheckerSession mSpellCheckerSession;
        private HandlerThread mThread;
        private final Queue<SpellCheckerParams> mPendingTasks = new ArrayDeque();
        private int mState = 0;

        private static String taskToString(int i) {
            if (i == 1) {
                return "TASK_CANCEL";
            }
            if (i == 2) {
                return "TASK_GET_SUGGESTIONS_MULTIPLE";
            }
            if (i == 3) {
                return "TASK_CLOSE";
            }
            if (i == 4) {
                return "TASK_GET_SUGGESTIONS_MULTIPLE_FOR_SENTENCE";
            }
            return "Unexpected task=" + i;
        }

        private static String stateToString(int i) {
            if (i == 0) {
                return "STATE_WAIT_CONNECTION";
            }
            if (i == 1) {
                return "STATE_CONNECTED";
            }
            if (i == 2) {
                return "STATE_CLOSED_AFTER_CONNECTION";
            }
            if (i == 3) {
                return "STATE_CLOSED_BEFORE_CONNECTION";
            }
            return "Unexpected state=" + i;
        }

        SpellCheckerSessionListenerImpl(SpellCheckerSession spellCheckerSession) {
            this.mSpellCheckerSession = spellCheckerSession;
        }

        private static class SpellCheckerParams {
            public final boolean mSequentialWords;
            public ISpellCheckerSession mSession;
            public final int mSuggestionsLimit;
            public final TextInfo[] mTextInfos;
            public final int mWhat;

            public SpellCheckerParams(int i, TextInfo[] textInfoArr, int i2, boolean z) {
                this.mWhat = i;
                this.mTextInfos = textInfoArr;
                this.mSuggestionsLimit = i2;
                this.mSequentialWords = z;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void processTask(ISpellCheckerSession iSpellCheckerSession, SpellCheckerParams spellCheckerParams, boolean z) {
            if (z || this.mAsyncHandler == null) {
                int i = spellCheckerParams.mWhat;
                if (i == 1) {
                    try {
                        iSpellCheckerSession.onCancel();
                    } catch (RemoteException e) {
                        Log.e(SpellCheckerSession.TAG, "Failed to cancel " + e);
                    }
                } else if (i == 2) {
                    try {
                        iSpellCheckerSession.onGetSuggestionsMultiple(spellCheckerParams.mTextInfos, spellCheckerParams.mSuggestionsLimit, spellCheckerParams.mSequentialWords);
                    } catch (RemoteException e2) {
                        Log.e(SpellCheckerSession.TAG, "Failed to get suggestions " + e2);
                    }
                } else if (i == 3) {
                    try {
                        iSpellCheckerSession.onClose();
                    } catch (RemoteException e3) {
                        Log.e(SpellCheckerSession.TAG, "Failed to close " + e3);
                    }
                } else if (i == 4) {
                    try {
                        iSpellCheckerSession.onGetSentenceSuggestionsMultiple(spellCheckerParams.mTextInfos, spellCheckerParams.mSuggestionsLimit);
                    } catch (RemoteException e4) {
                        Log.e(SpellCheckerSession.TAG, "Failed to get suggestions " + e4);
                    }
                }
            } else {
                spellCheckerParams.mSession = iSpellCheckerSession;
                Handler handler = this.mAsyncHandler;
                handler.sendMessage(Message.obtain(handler, 1, spellCheckerParams));
            }
            if (spellCheckerParams.mWhat == 3) {
                synchronized (this) {
                    processCloseLocked();
                }
            }
        }

        private void processCloseLocked() {
            this.mISpellCheckerSession = null;
            HandlerThread handlerThread = this.mThread;
            if (handlerThread != null) {
                handlerThread.quit();
            }
            this.mSpellCheckerSession = null;
            this.mPendingTasks.clear();
            this.mThread = null;
            this.mAsyncHandler = null;
            int i = this.mState;
            if (i == 0) {
                this.mState = 3;
                return;
            }
            if (i == 1) {
                this.mState = 2;
                return;
            }
            Log.e(SpellCheckerSession.TAG, "processCloseLocked is called unexpectedly. mState=" + stateToString(this.mState));
        }

        public void onServiceConnected(ISpellCheckerSession iSpellCheckerSession) {
            synchronized (this) {
                int i = this.mState;
                if (i != 0) {
                    if (i != 3) {
                        Log.e(SpellCheckerSession.TAG, "ignoring onServiceConnected due to unexpected mState=" + stateToString(this.mState));
                        return;
                    }
                    return;
                }
                if (iSpellCheckerSession == null) {
                    Log.e(SpellCheckerSession.TAG, "ignoring onServiceConnected due to session=null");
                    return;
                }
                this.mISpellCheckerSession = iSpellCheckerSession;
                if ((iSpellCheckerSession.asBinder() instanceof Binder) && this.mThread == null) {
                    HandlerThread handlerThread = new HandlerThread(SpellCheckerSession.TAG, 10);
                    this.mThread = handlerThread;
                    handlerThread.start();
                    this.mAsyncHandler = new Handler(this.mThread.getLooper()) { // from class: android.view.textservice.SpellCheckerSession.SpellCheckerSessionListenerImpl.1
                        @Override // android.os.Handler
                        public void handleMessage(Message message) {
                            SpellCheckerParams spellCheckerParams = (SpellCheckerParams) message.obj;
                            SpellCheckerSessionListenerImpl.this.processTask(spellCheckerParams.mSession, spellCheckerParams, true);
                        }
                    };
                }
                this.mState = 1;
                while (!this.mPendingTasks.isEmpty()) {
                    processTask(iSpellCheckerSession, this.mPendingTasks.poll(), false);
                }
            }
        }

        public void cancel() {
            processOrEnqueueTask(new SpellCheckerParams(1, null, 0, false));
        }

        public void getSuggestionsMultiple(TextInfo[] textInfoArr, int i, boolean z) {
            processOrEnqueueTask(new SpellCheckerParams(2, textInfoArr, i, z));
        }

        public void getSentenceSuggestionsMultiple(TextInfo[] textInfoArr, int i) {
            processOrEnqueueTask(new SpellCheckerParams(4, textInfoArr, i, false));
        }

        public void close() {
            processOrEnqueueTask(new SpellCheckerParams(3, null, 0, false));
        }

        public boolean isDisconnected() {
            boolean z;
            synchronized (this) {
                z = true;
                if (this.mState == 1) {
                    z = false;
                }
            }
            return z;
        }

        private void processOrEnqueueTask(SpellCheckerParams spellCheckerParams) {
            int i;
            synchronized (this) {
                if (spellCheckerParams.mWhat == 3 && ((i = this.mState) == 2 || i == 3)) {
                    return;
                }
                int i2 = this.mState;
                if (i2 != 0 && i2 != 1) {
                    Log.e(SpellCheckerSession.TAG, "ignoring processOrEnqueueTask due to unexpected mState=" + stateToString(this.mState) + " scp.mWhat=" + taskToString(spellCheckerParams.mWhat));
                    return;
                }
                if (i2 == 0) {
                    if (spellCheckerParams.mWhat == 3) {
                        processCloseLocked();
                        return;
                    }
                    SpellCheckerParams spellCheckerParams2 = null;
                    if (spellCheckerParams.mWhat == 1) {
                        while (!this.mPendingTasks.isEmpty()) {
                            SpellCheckerParams poll = this.mPendingTasks.poll();
                            if (poll.mWhat == 3) {
                                spellCheckerParams2 = poll;
                            }
                        }
                    }
                    this.mPendingTasks.offer(spellCheckerParams);
                    if (spellCheckerParams2 != null) {
                        this.mPendingTasks.offer(spellCheckerParams2);
                    }
                    return;
                }
                processTask(this.mISpellCheckerSession, spellCheckerParams, false);
            }
        }

        @Override // com.android.internal.textservice.ISpellCheckerSessionListener
        public void onGetSuggestions(SuggestionsInfo[] suggestionsInfoArr) {
            SpellCheckerSession spellCheckerSession = getSpellCheckerSession();
            if (spellCheckerSession != null) {
                spellCheckerSession.handleOnGetSuggestionsMultiple(suggestionsInfoArr);
            }
        }

        @Override // com.android.internal.textservice.ISpellCheckerSessionListener
        public void onGetSentenceSuggestions(SentenceSuggestionsInfo[] sentenceSuggestionsInfoArr) {
            SpellCheckerSession spellCheckerSession = getSpellCheckerSession();
            if (spellCheckerSession != null) {
                spellCheckerSession.handleOnGetSentenceSuggestionsMultiple(sentenceSuggestionsInfoArr);
            }
        }

        private SpellCheckerSession getSpellCheckerSession() {
            SpellCheckerSession spellCheckerSession;
            synchronized (this) {
                spellCheckerSession = this.mSpellCheckerSession;
            }
            return spellCheckerSession;
        }
    }

    public static class SpellCheckerSessionParams {
        private final Bundle mExtras;
        private final Locale mLocale;
        private final boolean mShouldReferToSpellCheckerLanguageSettings;
        private final int mSupportedAttributes;

        private SpellCheckerSessionParams(Locale locale, boolean z, int i, Bundle bundle) {
            this.mLocale = locale;
            this.mShouldReferToSpellCheckerLanguageSettings = z;
            this.mSupportedAttributes = i;
            this.mExtras = bundle;
        }

        public Locale getLocale() {
            return this.mLocale;
        }

        public boolean shouldReferToSpellCheckerLanguageSettings() {
            return this.mShouldReferToSpellCheckerLanguageSettings;
        }

        public int getSupportedAttributes() {
            return this.mSupportedAttributes;
        }

        public Bundle getExtras() {
            return this.mExtras;
        }

        public static final class Builder {
            private Locale mLocale;
            private boolean mShouldReferToSpellCheckerLanguageSettings = false;
            private int mSupportedAttributes = 0;
            private Bundle mExtras = Bundle.EMPTY;

            public SpellCheckerSessionParams build() {
                if (this.mLocale == null && !this.mShouldReferToSpellCheckerLanguageSettings) {
                    throw new IllegalArgumentException("mLocale should not be null if  mShouldReferToSpellCheckerLanguageSettings is false.");
                }
                return new SpellCheckerSessionParams(this.mLocale, this.mShouldReferToSpellCheckerLanguageSettings, this.mSupportedAttributes, this.mExtras);
            }

            public Builder setLocale(Locale locale) {
                this.mLocale = locale;
                return this;
            }

            public Builder setShouldReferToSpellCheckerLanguageSettings(boolean z) {
                this.mShouldReferToSpellCheckerLanguageSettings = z;
                return this;
            }

            public Builder setSupportedAttributes(int i) {
                this.mSupportedAttributes = i;
                return this;
            }

            public Builder setExtras(Bundle bundle) {
                this.mExtras = bundle;
                return this;
            }
        }
    }

    private static final class InternalListener extends ITextServicesSessionListener.Stub {
        private final SpellCheckerSessionListenerImpl mParentSpellCheckerSessionListenerImpl;

        public InternalListener(SpellCheckerSessionListenerImpl spellCheckerSessionListenerImpl) {
            this.mParentSpellCheckerSessionListenerImpl = spellCheckerSessionListenerImpl;
        }

        @Override // com.android.internal.textservice.ITextServicesSessionListener
        public void onServiceConnected(ISpellCheckerSession iSpellCheckerSession) {
            this.mParentSpellCheckerSessionListenerImpl.onServiceConnected(iSpellCheckerSession);
        }
    }

    protected void finalize() throws Throwable {
        try {
            CloseGuard closeGuard = this.mGuard;
            if (closeGuard != null) {
                closeGuard.warnIfOpen();
                close();
            }
        } finally {
            super.finalize();
        }
    }

    public ITextServicesSessionListener getTextServicesSessionListener() {
        return this.mInternalListener;
    }

    public ISpellCheckerSessionListener getSpellCheckerSessionListener() {
        return this.mSpellCheckerSessionListenerImpl;
    }
}
