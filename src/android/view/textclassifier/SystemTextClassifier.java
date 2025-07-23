package android.view.textclassifier;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.service.textclassifier.ITextClassifierCallback;
import android.service.textclassifier.ITextClassifierService;
import android.service.textclassifier.TextClassifierService;
import android.view.textclassifier.ConversationActions;
import android.view.textclassifier.TextClassification;
import android.view.textclassifier.TextClassificationContext;
import android.view.textclassifier.TextClassifier;
import android.view.textclassifier.TextLanguage;
import android.view.textclassifier.TextLinks;
import android.view.textclassifier.TextSelection;
import com.android.internal.util.IndentingPrintWriter;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public final class SystemTextClassifier implements TextClassifier {
    private static final String LOG_TAG = "androidtc";
    private TextClassificationSessionId mSessionId;
    private final TextClassificationConstants mSettings;
    private final SystemTextClassifierMetadata mSystemTcMetadata;
    private final ITextClassifierService mManagerService = ITextClassifierService.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.TEXT_CLASSIFICATION_SERVICE));
    private final TextClassifier mFallback = TextClassifier.NO_OP;

    public SystemTextClassifier(Context context, TextClassificationConstants textClassificationConstants, boolean z) throws ServiceManager.ServiceNotFoundException {
        this.mSettings = (TextClassificationConstants) Objects.requireNonNull(textClassificationConstants);
        this.mSystemTcMetadata = new SystemTextClassifierMetadata((String) Objects.requireNonNull(context.getOpPackageName()), context.getUserId(), z);
    }

    @Override // android.view.textclassifier.TextClassifier
    public TextSelection suggestSelection(TextSelection.Request request) {
        Objects.requireNonNull(request);
        TextClassifier.Utils.checkMainThread();
        try {
            request.setSystemTextClassifierMetadata(this.mSystemTcMetadata);
            BlockingCallback blockingCallback = new BlockingCallback("textselection", this.mSettings);
            this.mManagerService.onSuggestSelection(this.mSessionId, request, blockingCallback);
            TextSelection textSelection = (TextSelection) blockingCallback.get();
            if (textSelection != null) {
                return textSelection;
            }
        } catch (RemoteException e) {
            Log.e("androidtc", "Error suggesting selection for text. Using fallback.", e);
        }
        return this.mFallback.suggestSelection(request);
    }

    @Override // android.view.textclassifier.TextClassifier
    public TextClassification classifyText(TextClassification.Request request) {
        Objects.requireNonNull(request);
        TextClassifier.Utils.checkMainThread();
        try {
            request.setSystemTextClassifierMetadata(this.mSystemTcMetadata);
            BlockingCallback blockingCallback = new BlockingCallback(Context.TEXT_CLASSIFICATION_SERVICE, this.mSettings);
            this.mManagerService.onClassifyText(this.mSessionId, request, blockingCallback);
            TextClassification textClassification = (TextClassification) blockingCallback.get();
            if (textClassification != null) {
                return textClassification;
            }
        } catch (RemoteException e) {
            Log.e("androidtc", "Error classifying text. Using fallback.", e);
        }
        return this.mFallback.classifyText(request);
    }

    @Override // android.view.textclassifier.TextClassifier
    public TextLinks generateLinks(TextLinks.Request request) {
        Objects.requireNonNull(request);
        TextClassifier.Utils.checkMainThread();
        if (!TextClassifier.Utils.checkTextLength(request.getText(), getMaxGenerateLinksTextLength())) {
            return this.mFallback.generateLinks(request);
        }
        if (!this.mSettings.isSmartLinkifyEnabled() && request.isLegacyFallback()) {
            return TextClassifier.Utils.generateLegacyLinks(request);
        }
        try {
            request.setSystemTextClassifierMetadata(this.mSystemTcMetadata);
            BlockingCallback blockingCallback = new BlockingCallback("textlinks", this.mSettings);
            this.mManagerService.onGenerateLinks(this.mSessionId, request, blockingCallback);
            TextLinks textLinks = (TextLinks) blockingCallback.get();
            if (textLinks != null) {
                return textLinks;
            }
        } catch (RemoteException e) {
            Log.e("androidtc", "Error generating links. Using fallback.", e);
        }
        return this.mFallback.generateLinks(request);
    }

    @Override // android.view.textclassifier.TextClassifier
    public void onSelectionEvent(SelectionEvent selectionEvent) {
        Objects.requireNonNull(selectionEvent);
        TextClassifier.Utils.checkMainThread();
        try {
            selectionEvent.setSystemTextClassifierMetadata(this.mSystemTcMetadata);
            this.mManagerService.onSelectionEvent(this.mSessionId, selectionEvent);
        } catch (RemoteException e) {
            Log.e("androidtc", "Error reporting selection event.", e);
        }
    }

    @Override // android.view.textclassifier.TextClassifier
    public void onTextClassifierEvent(TextClassifierEvent textClassifierEvent) {
        TextClassificationContext eventContext;
        Objects.requireNonNull(textClassifierEvent);
        TextClassifier.Utils.checkMainThread();
        try {
            if (textClassifierEvent.getEventContext() == null) {
                eventContext = new TextClassificationContext.Builder(this.mSystemTcMetadata.getCallingPackageName(), "unknown").build();
            } else {
                eventContext = textClassifierEvent.getEventContext();
            }
            eventContext.setSystemTextClassifierMetadata(this.mSystemTcMetadata);
            textClassifierEvent.setEventContext(eventContext);
            this.mManagerService.onTextClassifierEvent(this.mSessionId, textClassifierEvent);
        } catch (RemoteException e) {
            Log.e("androidtc", "Error reporting textclassifier event.", e);
        }
    }

    @Override // android.view.textclassifier.TextClassifier
    public TextLanguage detectLanguage(TextLanguage.Request request) {
        Objects.requireNonNull(request);
        TextClassifier.Utils.checkMainThread();
        try {
            request.setSystemTextClassifierMetadata(this.mSystemTcMetadata);
            BlockingCallback blockingCallback = new BlockingCallback("textlanguage", this.mSettings);
            this.mManagerService.onDetectLanguage(this.mSessionId, request, blockingCallback);
            TextLanguage textLanguage = (TextLanguage) blockingCallback.get();
            if (textLanguage != null) {
                return textLanguage;
            }
        } catch (RemoteException e) {
            Log.e("androidtc", "Error detecting language.", e);
        }
        return this.mFallback.detectLanguage(request);
    }

    @Override // android.view.textclassifier.TextClassifier
    public ConversationActions suggestConversationActions(ConversationActions.Request request) {
        Objects.requireNonNull(request);
        TextClassifier.Utils.checkMainThread();
        try {
            request.setSystemTextClassifierMetadata(this.mSystemTcMetadata);
            BlockingCallback blockingCallback = new BlockingCallback("conversation-actions", this.mSettings);
            this.mManagerService.onSuggestConversationActions(this.mSessionId, request, blockingCallback);
            ConversationActions conversationActions = (ConversationActions) blockingCallback.get();
            if (conversationActions != null) {
                return conversationActions;
            }
        } catch (RemoteException e) {
            Log.e("androidtc", "Error reporting selection event.", e);
        }
        return this.mFallback.suggestConversationActions(request);
    }

    @Override // android.view.textclassifier.TextClassifier
    public int getMaxGenerateLinksTextLength() {
        return this.mSettings.getGenerateLinksMaxTextLength();
    }

    @Override // android.view.textclassifier.TextClassifier
    public void destroy() {
        try {
            TextClassificationSessionId textClassificationSessionId = this.mSessionId;
            if (textClassificationSessionId != null) {
                this.mManagerService.onDestroyTextClassificationSession(textClassificationSessionId);
            }
        } catch (RemoteException e) {
            Log.e("androidtc", "Error destroying classification session.", e);
        }
    }

    @Override // android.view.textclassifier.TextClassifier
    public void dump(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("SystemTextClassifier:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.printPair("mFallback", this.mFallback);
        indentingPrintWriter.printPair("mSessionId", this.mSessionId);
        indentingPrintWriter.printPair("mSystemTcMetadata", this.mSystemTcMetadata);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
    }

    void initializeRemoteSession(TextClassificationContext textClassificationContext, TextClassificationSessionId textClassificationSessionId) {
        this.mSessionId = (TextClassificationSessionId) Objects.requireNonNull(textClassificationSessionId);
        try {
            textClassificationContext.setSystemTextClassifierMetadata(this.mSystemTcMetadata);
            this.mManagerService.onCreateTextClassificationSession(textClassificationContext, this.mSessionId);
        } catch (RemoteException e) {
            Log.e("androidtc", "Error starting a new classification session.", e);
        }
    }

    private static final class BlockingCallback<T extends Parcelable> extends ITextClassifierCallback.Stub {
        private final ResponseReceiver<T> mReceiver;

        BlockingCallback(String str, TextClassificationConstants textClassificationConstants) {
            this.mReceiver = new ResponseReceiver<>(str, textClassificationConstants);
        }

        @Override // android.service.textclassifier.ITextClassifierCallback
        public void onSuccess(Bundle bundle) {
            this.mReceiver.onSuccess(TextClassifierService.getResponse(bundle));
        }

        @Override // android.service.textclassifier.ITextClassifierCallback
        public void onFailure() {
            this.mReceiver.onFailure();
        }

        public T get() {
            return this.mReceiver.get();
        }
    }

    private static final class ResponseReceiver<T> {
        private final CountDownLatch mLatch;
        private final String mName;
        private T mResponse;
        private final TextClassificationConstants mSettings;

        private ResponseReceiver(String str, TextClassificationConstants textClassificationConstants) {
            this.mLatch = new CountDownLatch(1);
            this.mName = str;
            this.mSettings = textClassificationConstants;
        }

        public void onSuccess(T t) {
            this.mResponse = t;
            this.mLatch.countDown();
        }

        public void onFailure() {
            Log.e("androidtc", "Request failed at " + this.mName, null);
            this.mLatch.countDown();
        }

        public T get() {
            if (Looper.myLooper() != Looper.getMainLooper()) {
                try {
                    if (!this.mLatch.await(this.mSettings.getSystemTextClassifierApiTimeoutInSecond(), TimeUnit.SECONDS)) {
                        Log.w("androidtc", "Timeout in ResponseReceiver.get(): " + this.mName);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    Log.e("androidtc", "Interrupted during ResponseReceiver.get(): " + this.mName, e);
                }
            }
            return this.mResponse;
        }
    }
}
