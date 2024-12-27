package android.view.textclassifier;

import com.android.internal.util.Preconditions;

import sun.misc.Cleaner;

import java.util.Objects;
import java.util.function.Supplier;

final class TextClassificationSession implements TextClassifier {
    private static final String LOG_TAG = "TextClassificationSession";
    private final TextClassificationContext mClassificationContext;
    private final Cleaner mCleaner;
    private final TextClassifier mDelegate;
    private boolean mDestroyed;
    private final SelectionEventHelper mEventHelper;
    private final Object mLock = new Object();
    private final TextClassificationSessionId mSessionId = new TextClassificationSessionId();

    TextClassificationSession(TextClassificationContext context, TextClassifier delegate) {
        this.mClassificationContext = (TextClassificationContext) Objects.requireNonNull(context);
        this.mDelegate = (TextClassifier) Objects.requireNonNull(delegate);
        this.mEventHelper = new SelectionEventHelper(this.mSessionId, this.mClassificationContext);
        initializeRemoteSession();
        this.mCleaner =
                Cleaner.create(this, new CleanerRunnable(this.mEventHelper, this.mDelegate));
    }

    public /* synthetic */ TextSelection lambda$suggestSelection$0(TextSelection.Request request) {
        return this.mDelegate.suggestSelection(request);
    }

    @Override // android.view.textclassifier.TextClassifier
    public TextSelection suggestSelection(final TextSelection.Request request) {
        return (TextSelection)
                checkDestroyedAndRun(
                        new Supplier() { // from class:
                                         // android.view.textclassifier.TextClassificationSession$$ExternalSyntheticLambda5
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                TextSelection lambda$suggestSelection$0;
                                lambda$suggestSelection$0 =
                                        TextClassificationSession.this.lambda$suggestSelection$0(
                                                request);
                                return lambda$suggestSelection$0;
                            }
                        });
    }

    private void initializeRemoteSession() {
        if (this.mDelegate instanceof SystemTextClassifier) {
            ((SystemTextClassifier) this.mDelegate)
                    .initializeRemoteSession(this.mClassificationContext, this.mSessionId);
        }
    }

    public /* synthetic */ TextClassification lambda$classifyText$1(
            TextClassification.Request request) {
        return this.mDelegate.classifyText(request);
    }

    @Override // android.view.textclassifier.TextClassifier
    public TextClassification classifyText(final TextClassification.Request request) {
        return (TextClassification)
                checkDestroyedAndRun(
                        new Supplier() { // from class:
                                         // android.view.textclassifier.TextClassificationSession$$ExternalSyntheticLambda2
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                TextClassification lambda$classifyText$1;
                                lambda$classifyText$1 =
                                        TextClassificationSession.this.lambda$classifyText$1(
                                                request);
                                return lambda$classifyText$1;
                            }
                        });
    }

    public /* synthetic */ TextLinks lambda$generateLinks$2(TextLinks.Request request) {
        return this.mDelegate.generateLinks(request);
    }

    @Override // android.view.textclassifier.TextClassifier
    public TextLinks generateLinks(final TextLinks.Request request) {
        return (TextLinks)
                checkDestroyedAndRun(
                        new Supplier() { // from class:
                                         // android.view.textclassifier.TextClassificationSession$$ExternalSyntheticLambda4
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                TextLinks lambda$generateLinks$2;
                                lambda$generateLinks$2 =
                                        TextClassificationSession.this.lambda$generateLinks$2(
                                                request);
                                return lambda$generateLinks$2;
                            }
                        });
    }

    public /* synthetic */ ConversationActions lambda$suggestConversationActions$3(
            ConversationActions.Request request) {
        return this.mDelegate.suggestConversationActions(request);
    }

    @Override // android.view.textclassifier.TextClassifier
    public ConversationActions suggestConversationActions(
            final ConversationActions.Request request) {
        return (ConversationActions)
                checkDestroyedAndRun(
                        new Supplier() { // from class:
                                         // android.view.textclassifier.TextClassificationSession$$ExternalSyntheticLambda6
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                ConversationActions lambda$suggestConversationActions$3;
                                lambda$suggestConversationActions$3 =
                                        TextClassificationSession.this
                                                .lambda$suggestConversationActions$3(request);
                                return lambda$suggestConversationActions$3;
                            }
                        });
    }

    public /* synthetic */ TextLanguage lambda$detectLanguage$4(TextLanguage.Request request) {
        return this.mDelegate.detectLanguage(request);
    }

    @Override // android.view.textclassifier.TextClassifier
    public TextLanguage detectLanguage(final TextLanguage.Request request) {
        return (TextLanguage)
                checkDestroyedAndRun(
                        new Supplier() { // from class:
                                         // android.view.textclassifier.TextClassificationSession$$ExternalSyntheticLambda0
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                TextLanguage lambda$detectLanguage$4;
                                lambda$detectLanguage$4 =
                                        TextClassificationSession.this.lambda$detectLanguage$4(
                                                request);
                                return lambda$detectLanguage$4;
                            }
                        });
    }

    @Override // android.view.textclassifier.TextClassifier
    public int getMaxGenerateLinksTextLength() {
        final TextClassifier textClassifier = this.mDelegate;
        Objects.requireNonNull(textClassifier);
        return ((Integer)
                        checkDestroyedAndRun(
                                new Supplier() { // from class:
                                                 // android.view.textclassifier.TextClassificationSession$$ExternalSyntheticLambda7
                                    @Override // java.util.function.Supplier
                                    public final Object get() {
                                        return Integer.valueOf(
                                                TextClassifier.this
                                                        .getMaxGenerateLinksTextLength());
                                    }
                                }))
                .intValue();
    }

    @Override // android.view.textclassifier.TextClassifier
    public void onSelectionEvent(final SelectionEvent event) {
        checkDestroyedAndRun(
                new Supplier() { // from class:
                                 // android.view.textclassifier.TextClassificationSession$$ExternalSyntheticLambda3
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        Object lambda$onSelectionEvent$5;
                        lambda$onSelectionEvent$5 =
                                TextClassificationSession.this.lambda$onSelectionEvent$5(event);
                        return lambda$onSelectionEvent$5;
                    }
                });
    }

    public /* synthetic */ Object lambda$onSelectionEvent$5(SelectionEvent event) {
        try {
            if (this.mEventHelper.sanitizeEvent(event)) {
                this.mDelegate.onSelectionEvent(event);
                return null;
            }
            return null;
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error reporting text classifier selection event", e);
            return null;
        }
    }

    @Override // android.view.textclassifier.TextClassifier
    public void onTextClassifierEvent(final TextClassifierEvent event) {
        checkDestroyedAndRun(
                new Supplier() { // from class:
                                 // android.view.textclassifier.TextClassificationSession$$ExternalSyntheticLambda1
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        Object lambda$onTextClassifierEvent$6;
                        lambda$onTextClassifierEvent$6 =
                                TextClassificationSession.this.lambda$onTextClassifierEvent$6(
                                        event);
                        return lambda$onTextClassifierEvent$6;
                    }
                });
    }

    public /* synthetic */ Object lambda$onTextClassifierEvent$6(TextClassifierEvent event) {
        try {
            event.mHiddenTempSessionId = this.mSessionId;
            this.mDelegate.onTextClassifierEvent(event);
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

    private <T> T checkDestroyedAndRun(Supplier<T> responseSupplier) {
        if (!isDestroyed()) {
            T response = responseSupplier.get();
            synchronized (this.mLock) {
                if (!this.mDestroyed) {
                    return response;
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

        SelectionEventHelper(
                TextClassificationSessionId sessionId, TextClassificationContext context) {
            this.mSessionId = (TextClassificationSessionId) Objects.requireNonNull(sessionId);
            this.mContext = (TextClassificationContext) Objects.requireNonNull(context);
        }

        boolean sanitizeEvent(SelectionEvent event) {
            updateInvocationMethod(event);
            modifyAutoSelectionEventType(event);
            if (event.getEventType() != 1 && this.mStartEvent == null) {
                Log.d(
                        TextClassificationSession.LOG_TAG,
                        "Selection session not yet started. Ignoring event");
                return false;
            }
            long now = System.currentTimeMillis();
            switch (event.getEventType()) {
                case 1:
                    Preconditions.checkArgument(
                            event.getAbsoluteEnd() == event.getAbsoluteStart() + 1);
                    event.setSessionId(this.mSessionId);
                    this.mStartEvent = event;
                    break;
                case 2:
                    if (this.mPrevEvent != null
                            && this.mPrevEvent.getAbsoluteStart() == event.getAbsoluteStart()
                            && this.mPrevEvent.getAbsoluteEnd() == event.getAbsoluteEnd()) {
                        return false;
                    }
                    break;
                case 3:
                case 4:
                case 5:
                    this.mSmartEvent = event;
                    break;
                case 100:
                case 107:
                    if (this.mPrevEvent != null) {
                        event.setEntityType(this.mPrevEvent.getEntityType());
                        break;
                    }
                    break;
            }
            event.setEventTime(now);
            if (this.mStartEvent != null) {
                event.setSessionId(this.mStartEvent.getSessionId())
                        .setDurationSinceSessionStart(now - this.mStartEvent.getEventTime())
                        .setStart(event.getAbsoluteStart() - this.mStartEvent.getAbsoluteStart())
                        .setEnd(event.getAbsoluteEnd() - this.mStartEvent.getAbsoluteStart());
            }
            if (this.mSmartEvent != null) {
                event.setResultId(this.mSmartEvent.getResultId())
                        .setSmartStart(
                                this.mSmartEvent.getAbsoluteStart()
                                        - this.mStartEvent.getAbsoluteStart())
                        .setSmartEnd(
                                this.mSmartEvent.getAbsoluteEnd()
                                        - this.mStartEvent.getAbsoluteStart());
            }
            if (this.mPrevEvent != null) {
                event.setDurationSincePreviousEvent(now - this.mPrevEvent.getEventTime())
                        .setEventIndex(this.mPrevEvent.getEventIndex() + 1);
            }
            this.mPrevEvent = event;
            return true;
        }

        void endSession() {
            this.mPrevEvent = null;
            this.mSmartEvent = null;
            this.mStartEvent = null;
        }

        private void updateInvocationMethod(SelectionEvent event) {
            event.setTextClassificationSessionContext(this.mContext);
            if (event.getInvocationMethod() == 0) {
                event.setInvocationMethod(this.mInvocationMethod);
            } else {
                this.mInvocationMethod = event.getInvocationMethod();
            }
        }

        private void modifyAutoSelectionEventType(SelectionEvent event) {
            switch (event.getEventType()) {
                case 3:
                case 4:
                case 5:
                    if (SelectionSessionLogger.isPlatformLocalTextClassifierSmartSelection(
                            event.getResultId())) {
                        if (event.getAbsoluteEnd() - event.getAbsoluteStart() > 1) {
                            event.setEventType(4);
                            break;
                        } else {
                            event.setEventType(3);
                            break;
                        }
                    } else {
                        event.setEventType(5);
                        break;
                    }
            }
        }
    }

    private static class CleanerRunnable implements Runnable {
        private final TextClassifier mDelegate;
        private final SelectionEventHelper mEventHelper;

        CleanerRunnable(SelectionEventHelper eventHelper, TextClassifier delegate) {
            this.mEventHelper = (SelectionEventHelper) Objects.requireNonNull(eventHelper);
            this.mDelegate = (TextClassifier) Objects.requireNonNull(delegate);
        }

        @Override // java.lang.Runnable
        public void run() {
            this.mEventHelper.endSession();
            this.mDelegate.destroy();
        }
    }
}
