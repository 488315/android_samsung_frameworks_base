package android.view.textclassifier;

import android.view.textclassifier.ConversationActions;
import android.view.textclassifier.TextClassification;
import android.view.textclassifier.TextLanguage;
import android.view.textclassifier.TextLinks;
import android.view.textclassifier.TextSelection;
import com.android.internal.util.Preconditions;
import java.util.Objects;
import java.util.function.Supplier;
import sun.misc.Cleaner;

/* loaded from: classes4.dex */
final class TextClassificationSession implements TextClassifier {
    private static final String LOG_TAG = "TextClassificationSession";
    private final TextClassificationContext mClassificationContext;
    private final Cleaner mCleaner;
    private final TextClassifier mDelegate;
    private boolean mDestroyed;
    private final SelectionEventHelper mEventHelper;
    private final Object mLock = new Object();
    private final TextClassificationSessionId mSessionId;

    TextClassificationSession(TextClassificationContext textClassificationContext, TextClassifier textClassifier) {
        TextClassificationContext textClassificationContext2 = (TextClassificationContext) Objects.requireNonNull(textClassificationContext);
        this.mClassificationContext = textClassificationContext2;
        TextClassifier textClassifier2 = (TextClassifier) Objects.requireNonNull(textClassifier);
        this.mDelegate = textClassifier2;
        TextClassificationSessionId textClassificationSessionId = new TextClassificationSessionId();
        this.mSessionId = textClassificationSessionId;
        SelectionEventHelper selectionEventHelper = new SelectionEventHelper(textClassificationSessionId, textClassificationContext2);
        this.mEventHelper = selectionEventHelper;
        initializeRemoteSession();
        this.mCleaner = Cleaner.create(this, new CleanerRunnable(selectionEventHelper, textClassifier2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ TextSelection lambda$suggestSelection$0(TextSelection.Request request) {
        return this.mDelegate.suggestSelection(request);
    }

    @Override // android.view.textclassifier.TextClassifier
    public TextSelection suggestSelection(final TextSelection.Request request) {
        return (TextSelection) checkDestroyedAndRun(new Supplier() { // from class: android.view.textclassifier.TextClassificationSession$$ExternalSyntheticLambda5
            @Override // java.util.function.Supplier
            public final Object get() {
                TextSelection lambda$suggestSelection$0;
                lambda$suggestSelection$0 = TextClassificationSession.this.lambda$suggestSelection$0(request);
                return lambda$suggestSelection$0;
            }
        });
    }

    private void initializeRemoteSession() {
        TextClassifier textClassifier = this.mDelegate;
        if (textClassifier instanceof SystemTextClassifier) {
            ((SystemTextClassifier) textClassifier).initializeRemoteSession(this.mClassificationContext, this.mSessionId);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ TextClassification lambda$classifyText$1(TextClassification.Request request) {
        return this.mDelegate.classifyText(request);
    }

    @Override // android.view.textclassifier.TextClassifier
    public TextClassification classifyText(final TextClassification.Request request) {
        return (TextClassification) checkDestroyedAndRun(new Supplier() { // from class: android.view.textclassifier.TextClassificationSession$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final Object get() {
                TextClassification lambda$classifyText$1;
                lambda$classifyText$1 = TextClassificationSession.this.lambda$classifyText$1(request);
                return lambda$classifyText$1;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ TextLinks lambda$generateLinks$2(TextLinks.Request request) {
        return this.mDelegate.generateLinks(request);
    }

    @Override // android.view.textclassifier.TextClassifier
    public TextLinks generateLinks(final TextLinks.Request request) {
        return (TextLinks) checkDestroyedAndRun(new Supplier() { // from class: android.view.textclassifier.TextClassificationSession$$ExternalSyntheticLambda4
            @Override // java.util.function.Supplier
            public final Object get() {
                TextLinks lambda$generateLinks$2;
                lambda$generateLinks$2 = TextClassificationSession.this.lambda$generateLinks$2(request);
                return lambda$generateLinks$2;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ ConversationActions lambda$suggestConversationActions$3(ConversationActions.Request request) {
        return this.mDelegate.suggestConversationActions(request);
    }

    @Override // android.view.textclassifier.TextClassifier
    public ConversationActions suggestConversationActions(final ConversationActions.Request request) {
        return (ConversationActions) checkDestroyedAndRun(new Supplier() { // from class: android.view.textclassifier.TextClassificationSession$$ExternalSyntheticLambda6
            @Override // java.util.function.Supplier
            public final Object get() {
                ConversationActions lambda$suggestConversationActions$3;
                lambda$suggestConversationActions$3 = TextClassificationSession.this.lambda$suggestConversationActions$3(request);
                return lambda$suggestConversationActions$3;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ TextLanguage lambda$detectLanguage$4(TextLanguage.Request request) {
        return this.mDelegate.detectLanguage(request);
    }

    @Override // android.view.textclassifier.TextClassifier
    public TextLanguage detectLanguage(final TextLanguage.Request request) {
        return (TextLanguage) checkDestroyedAndRun(new Supplier() { // from class: android.view.textclassifier.TextClassificationSession$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                TextLanguage lambda$detectLanguage$4;
                lambda$detectLanguage$4 = TextClassificationSession.this.lambda$detectLanguage$4(request);
                return lambda$detectLanguage$4;
            }
        });
    }

    @Override // android.view.textclassifier.TextClassifier
    public int getMaxGenerateLinksTextLength() {
        final TextClassifier textClassifier = this.mDelegate;
        Objects.requireNonNull(textClassifier);
        return ((Integer) checkDestroyedAndRun(new Supplier() { // from class: android.view.textclassifier.TextClassificationSession$$ExternalSyntheticLambda7
            @Override // java.util.function.Supplier
            public final Object get() {
                return Integer.valueOf(TextClassifier.this.getMaxGenerateLinksTextLength());
            }
        })).intValue();
    }

    @Override // android.view.textclassifier.TextClassifier
    public void onSelectionEvent(final SelectionEvent selectionEvent) {
        checkDestroyedAndRun(new Supplier() { // from class: android.view.textclassifier.TextClassificationSession$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                Object lambda$onSelectionEvent$5;
                lambda$onSelectionEvent$5 = TextClassificationSession.this.lambda$onSelectionEvent$5(selectionEvent);
                return lambda$onSelectionEvent$5;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Object lambda$onSelectionEvent$5(SelectionEvent selectionEvent) {
        try {
            if (!this.mEventHelper.sanitizeEvent(selectionEvent)) {
                return null;
            }
            this.mDelegate.onSelectionEvent(selectionEvent);
            return null;
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error reporting text classifier selection event", e);
            return null;
        }
    }

    @Override // android.view.textclassifier.TextClassifier
    public void onTextClassifierEvent(final TextClassifierEvent textClassifierEvent) {
        checkDestroyedAndRun(new Supplier() { // from class: android.view.textclassifier.TextClassificationSession$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                Object lambda$onTextClassifierEvent$6;
                lambda$onTextClassifierEvent$6 = TextClassificationSession.this.lambda$onTextClassifierEvent$6(textClassifierEvent);
                return lambda$onTextClassifierEvent$6;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Object lambda$onTextClassifierEvent$6(TextClassifierEvent textClassifierEvent) {
        try {
            textClassifierEvent.mHiddenTempSessionId = this.mSessionId;
            this.mDelegate.onTextClassifierEvent(textClassifierEvent);
            return null;
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error reporting text classifier event", e);
            return null;
        }
    }

    @Override // android.view.textclassifier.TextClassifier
    public void destroy() {
        synchronized (this.mLock) {
            if (!this.mDestroyed) {
                this.mCleaner.clean();
                this.mDestroyed = true;
            }
        }
    }

    @Override // android.view.textclassifier.TextClassifier
    public boolean isDestroyed() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mDestroyed;
        }
        return z;
    }

    private <T> T checkDestroyedAndRun(Supplier<T> supplier) {
        if (!isDestroyed()) {
            T t = supplier.get();
            synchronized (this.mLock) {
                if (!this.mDestroyed) {
                    return t;
                }
            }
        }
        throw new IllegalStateException("This TextClassification session has been destroyed");
    }

    private static final class SelectionEventHelper {
        private final TextClassificationContext mContext;
        private int mInvocationMethod = 0;
        private SelectionEvent mPrevEvent;
        private final TextClassificationSessionId mSessionId;
        private SelectionEvent mSmartEvent;
        private SelectionEvent mStartEvent;

        SelectionEventHelper(TextClassificationSessionId textClassificationSessionId, TextClassificationContext textClassificationContext) {
            this.mSessionId = (TextClassificationSessionId) Objects.requireNonNull(textClassificationSessionId);
            this.mContext = (TextClassificationContext) Objects.requireNonNull(textClassificationContext);
        }

        boolean sanitizeEvent(SelectionEvent selectionEvent) {
            SelectionEvent selectionEvent2;
            updateInvocationMethod(selectionEvent);
            modifyAutoSelectionEventType(selectionEvent);
            if (selectionEvent.getEventType() != 1 && this.mStartEvent == null) {
                Log.d(TextClassificationSession.LOG_TAG, "Selection session not yet started. Ignoring event");
                return false;
            }
            long currentTimeMillis = System.currentTimeMillis();
            int eventType = selectionEvent.getEventType();
            if (eventType == 1) {
                Preconditions.checkArgument(selectionEvent.getAbsoluteEnd() == selectionEvent.getAbsoluteStart() + 1);
                selectionEvent.setSessionId(this.mSessionId);
                this.mStartEvent = selectionEvent;
            } else if (eventType == 2) {
                SelectionEvent selectionEvent3 = this.mPrevEvent;
                if (selectionEvent3 != null && selectionEvent3.getAbsoluteStart() == selectionEvent.getAbsoluteStart() && this.mPrevEvent.getAbsoluteEnd() == selectionEvent.getAbsoluteEnd()) {
                    return false;
                }
            } else if (eventType == 3 || eventType == 4 || eventType == 5) {
                this.mSmartEvent = selectionEvent;
            } else if ((eventType == 100 || eventType == 107) && (selectionEvent2 = this.mPrevEvent) != null) {
                selectionEvent.setEntityType(selectionEvent2.getEntityType());
            }
            selectionEvent.setEventTime(currentTimeMillis);
            SelectionEvent selectionEvent4 = this.mStartEvent;
            if (selectionEvent4 != null) {
                selectionEvent.setSessionId(selectionEvent4.getSessionId()).setDurationSinceSessionStart(currentTimeMillis - this.mStartEvent.getEventTime()).setStart(selectionEvent.getAbsoluteStart() - this.mStartEvent.getAbsoluteStart()).setEnd(selectionEvent.getAbsoluteEnd() - this.mStartEvent.getAbsoluteStart());
            }
            SelectionEvent selectionEvent5 = this.mSmartEvent;
            if (selectionEvent5 != null) {
                selectionEvent.setResultId(selectionEvent5.getResultId()).setSmartStart(this.mSmartEvent.getAbsoluteStart() - this.mStartEvent.getAbsoluteStart()).setSmartEnd(this.mSmartEvent.getAbsoluteEnd() - this.mStartEvent.getAbsoluteStart());
            }
            SelectionEvent selectionEvent6 = this.mPrevEvent;
            if (selectionEvent6 != null) {
                selectionEvent.setDurationSincePreviousEvent(currentTimeMillis - selectionEvent6.getEventTime()).setEventIndex(this.mPrevEvent.getEventIndex() + 1);
            }
            this.mPrevEvent = selectionEvent;
            return true;
        }

        void endSession() {
            this.mPrevEvent = null;
            this.mSmartEvent = null;
            this.mStartEvent = null;
        }

        private void updateInvocationMethod(SelectionEvent selectionEvent) {
            selectionEvent.setTextClassificationSessionContext(this.mContext);
            if (selectionEvent.getInvocationMethod() == 0) {
                selectionEvent.setInvocationMethod(this.mInvocationMethod);
            } else {
                this.mInvocationMethod = selectionEvent.getInvocationMethod();
            }
        }

        private void modifyAutoSelectionEventType(SelectionEvent selectionEvent) {
            int eventType = selectionEvent.getEventType();
            if (eventType == 3 || eventType == 4 || eventType == 5) {
                if (SelectionSessionLogger.isPlatformLocalTextClassifierSmartSelection(selectionEvent.getResultId())) {
                    if (selectionEvent.getAbsoluteEnd() - selectionEvent.getAbsoluteStart() > 1) {
                        selectionEvent.setEventType(4);
                        return;
                    } else {
                        selectionEvent.setEventType(3);
                        return;
                    }
                }
                selectionEvent.setEventType(5);
            }
        }
    }

    private static class CleanerRunnable implements Runnable {
        private final TextClassifier mDelegate;
        private final SelectionEventHelper mEventHelper;

        CleanerRunnable(SelectionEventHelper selectionEventHelper, TextClassifier textClassifier) {
            this.mEventHelper = (SelectionEventHelper) Objects.requireNonNull(selectionEventHelper);
            this.mDelegate = (TextClassifier) Objects.requireNonNull(textClassifier);
        }

        @Override // java.lang.Runnable
        public void run() {
            this.mEventHelper.endSession();
            this.mDelegate.destroy();
        }
    }
}
