package android.service.textclassifier;

import android.Manifest;
import android.annotation.SystemApi;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Parcelable;
import android.os.RemoteException;
import android.service.textclassifier.ITextClassifierService;
import android.text.TextUtils;
import android.util.Slog;
import android.view.textclassifier.ConversationActions;
import android.view.textclassifier.SelectionEvent;
import android.view.textclassifier.TextClassification;
import android.view.textclassifier.TextClassificationContext;
import android.view.textclassifier.TextClassificationManager;
import android.view.textclassifier.TextClassificationSessionId;
import android.view.textclassifier.TextClassifier;
import android.view.textclassifier.TextClassifierEvent;
import android.view.textclassifier.TextLanguage;
import android.view.textclassifier.TextLinks;
import android.view.textclassifier.TextSelection;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SystemApi
/* loaded from: classes3.dex */
public abstract class TextClassifierService extends Service {
    public static final int CONNECTED = 0;
    public static final int DISCONNECTED = 1;
    private static final String KEY_RESULT = "key_result";
    private static final String LOG_TAG = "TextClassifierService";
    public static final String SERVICE_INTERFACE = "android.service.textclassifier.TextClassifierService";
    private final Handler mMainThreadHandler = new Handler(Looper.getMainLooper(), null, true);
    private final ExecutorService mSingleThreadExecutor = Executors.newSingleThreadExecutor();
    private final ITextClassifierService.Stub mBinder = new AnonymousClass1();

    public interface Callback<T> {
        void onFailure(CharSequence charSequence);

        void onSuccess(T t);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ConnectionState {
    }

    public abstract void onClassifyText(TextClassificationSessionId textClassificationSessionId, TextClassification.Request request, CancellationSignal cancellationSignal, Callback<TextClassification> callback);

    public void onConnected() {
    }

    public void onCreateTextClassificationSession(TextClassificationContext textClassificationContext, TextClassificationSessionId textClassificationSessionId) {
    }

    public void onDestroyTextClassificationSession(TextClassificationSessionId textClassificationSessionId) {
    }

    public void onDisconnected() {
    }

    public abstract void onGenerateLinks(TextClassificationSessionId textClassificationSessionId, TextLinks.Request request, CancellationSignal cancellationSignal, Callback<TextLinks> callback);

    @Deprecated
    public void onSelectionEvent(TextClassificationSessionId textClassificationSessionId, SelectionEvent selectionEvent) {
    }

    public abstract void onSuggestSelection(TextClassificationSessionId textClassificationSessionId, TextSelection.Request request, CancellationSignal cancellationSignal, Callback<TextSelection> callback);

    public void onTextClassifierEvent(TextClassificationSessionId textClassificationSessionId, TextClassifierEvent textClassifierEvent) {
    }

    /* renamed from: android.service.textclassifier.TextClassifierService$1, reason: invalid class name */
    class AnonymousClass1 extends ITextClassifierService.Stub {
        private final CancellationSignal mCancellationSignal = new CancellationSignal();

        AnonymousClass1() {
        }

        @Override // android.service.textclassifier.ITextClassifierService
        public void onSuggestSelection(final TextClassificationSessionId textClassificationSessionId, final TextSelection.Request request, final ITextClassifierCallback iTextClassifierCallback) {
            Objects.requireNonNull(request);
            Objects.requireNonNull(iTextClassifierCallback);
            TextClassifierService.this.mMainThreadHandler.post(new Runnable() { // from class: android.service.textclassifier.TextClassifierService$1$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onSuggestSelection$0(textClassificationSessionId, request, iTextClassifierCallback);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuggestSelection$0(TextClassificationSessionId textClassificationSessionId, TextSelection.Request request, ITextClassifierCallback iTextClassifierCallback) {
            TextClassifierService.this.onSuggestSelection(textClassificationSessionId, request, this.mCancellationSignal, new ProxyCallback(iTextClassifierCallback));
        }

        @Override // android.service.textclassifier.ITextClassifierService
        public void onClassifyText(final TextClassificationSessionId textClassificationSessionId, final TextClassification.Request request, final ITextClassifierCallback iTextClassifierCallback) {
            Objects.requireNonNull(request);
            Objects.requireNonNull(iTextClassifierCallback);
            TextClassifierService.this.mMainThreadHandler.post(new Runnable() { // from class: android.service.textclassifier.TextClassifierService$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onClassifyText$1(textClassificationSessionId, request, iTextClassifierCallback);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onClassifyText$1(TextClassificationSessionId textClassificationSessionId, TextClassification.Request request, ITextClassifierCallback iTextClassifierCallback) {
            TextClassifierService.this.onClassifyText(textClassificationSessionId, request, this.mCancellationSignal, new ProxyCallback(iTextClassifierCallback));
        }

        @Override // android.service.textclassifier.ITextClassifierService
        public void onGenerateLinks(final TextClassificationSessionId textClassificationSessionId, final TextLinks.Request request, final ITextClassifierCallback iTextClassifierCallback) {
            Objects.requireNonNull(request);
            Objects.requireNonNull(iTextClassifierCallback);
            TextClassifierService.this.mMainThreadHandler.post(new Runnable() { // from class: android.service.textclassifier.TextClassifierService$1$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onGenerateLinks$2(textClassificationSessionId, request, iTextClassifierCallback);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onGenerateLinks$2(TextClassificationSessionId textClassificationSessionId, TextLinks.Request request, ITextClassifierCallback iTextClassifierCallback) {
            TextClassifierService.this.onGenerateLinks(textClassificationSessionId, request, this.mCancellationSignal, new ProxyCallback(iTextClassifierCallback));
        }

        @Override // android.service.textclassifier.ITextClassifierService
        public void onSelectionEvent(final TextClassificationSessionId textClassificationSessionId, final SelectionEvent selectionEvent) {
            Objects.requireNonNull(selectionEvent);
            TextClassifierService.this.mMainThreadHandler.post(new Runnable() { // from class: android.service.textclassifier.TextClassifierService$1$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onSelectionEvent$3(textClassificationSessionId, selectionEvent);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSelectionEvent$3(TextClassificationSessionId textClassificationSessionId, SelectionEvent selectionEvent) {
            TextClassifierService.this.onSelectionEvent(textClassificationSessionId, selectionEvent);
        }

        @Override // android.service.textclassifier.ITextClassifierService
        public void onTextClassifierEvent(final TextClassificationSessionId textClassificationSessionId, final TextClassifierEvent textClassifierEvent) {
            Objects.requireNonNull(textClassifierEvent);
            TextClassifierService.this.mMainThreadHandler.post(new Runnable() { // from class: android.service.textclassifier.TextClassifierService$1$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onTextClassifierEvent$4(textClassificationSessionId, textClassifierEvent);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onTextClassifierEvent$4(TextClassificationSessionId textClassificationSessionId, TextClassifierEvent textClassifierEvent) {
            TextClassifierService.this.onTextClassifierEvent(textClassificationSessionId, textClassifierEvent);
        }

        @Override // android.service.textclassifier.ITextClassifierService
        public void onDetectLanguage(final TextClassificationSessionId textClassificationSessionId, final TextLanguage.Request request, final ITextClassifierCallback iTextClassifierCallback) {
            Objects.requireNonNull(request);
            Objects.requireNonNull(iTextClassifierCallback);
            TextClassifierService.this.mMainThreadHandler.post(new Runnable() { // from class: android.service.textclassifier.TextClassifierService$1$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onDetectLanguage$5(textClassificationSessionId, request, iTextClassifierCallback);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDetectLanguage$5(TextClassificationSessionId textClassificationSessionId, TextLanguage.Request request, ITextClassifierCallback iTextClassifierCallback) {
            TextClassifierService.this.onDetectLanguage(textClassificationSessionId, request, this.mCancellationSignal, new ProxyCallback(iTextClassifierCallback));
        }

        @Override // android.service.textclassifier.ITextClassifierService
        public void onSuggestConversationActions(final TextClassificationSessionId textClassificationSessionId, final ConversationActions.Request request, final ITextClassifierCallback iTextClassifierCallback) {
            Objects.requireNonNull(request);
            Objects.requireNonNull(iTextClassifierCallback);
            TextClassifierService.this.mMainThreadHandler.post(new Runnable() { // from class: android.service.textclassifier.TextClassifierService$1$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onSuggestConversationActions$6(textClassificationSessionId, request, iTextClassifierCallback);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuggestConversationActions$6(TextClassificationSessionId textClassificationSessionId, ConversationActions.Request request, ITextClassifierCallback iTextClassifierCallback) {
            TextClassifierService.this.onSuggestConversationActions(textClassificationSessionId, request, this.mCancellationSignal, new ProxyCallback(iTextClassifierCallback));
        }

        @Override // android.service.textclassifier.ITextClassifierService
        public void onCreateTextClassificationSession(final TextClassificationContext textClassificationContext, final TextClassificationSessionId textClassificationSessionId) {
            Objects.requireNonNull(textClassificationContext);
            Objects.requireNonNull(textClassificationSessionId);
            TextClassifierService.this.mMainThreadHandler.post(new Runnable() { // from class: android.service.textclassifier.TextClassifierService$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onCreateTextClassificationSession$7(textClassificationContext, textClassificationSessionId);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCreateTextClassificationSession$7(TextClassificationContext textClassificationContext, TextClassificationSessionId textClassificationSessionId) {
            TextClassifierService.this.onCreateTextClassificationSession(textClassificationContext, textClassificationSessionId);
        }

        @Override // android.service.textclassifier.ITextClassifierService
        public void onDestroyTextClassificationSession(final TextClassificationSessionId textClassificationSessionId) {
            TextClassifierService.this.mMainThreadHandler.post(new Runnable() { // from class: android.service.textclassifier.TextClassifierService$1$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onDestroyTextClassificationSession$8(textClassificationSessionId);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDestroyTextClassificationSession$8(TextClassificationSessionId textClassificationSessionId) {
            TextClassifierService.this.onDestroyTextClassificationSession(textClassificationSessionId);
        }

        @Override // android.service.textclassifier.ITextClassifierService
        public void onConnectedStateChanged(int i) {
            Runnable runnable;
            Handler handler = TextClassifierService.this.mMainThreadHandler;
            if (i == 0) {
                final TextClassifierService textClassifierService = TextClassifierService.this;
                runnable = new Runnable() { // from class: android.service.textclassifier.TextClassifierService$1$$ExternalSyntheticLambda8
                    @Override // java.lang.Runnable
                    public final void run() {
                        textClassifierService.onConnected();
                    }
                };
            } else {
                final TextClassifierService textClassifierService2 = TextClassifierService.this;
                runnable = new Runnable() { // from class: android.service.textclassifier.TextClassifierService$1$$ExternalSyntheticLambda9
                    @Override // java.lang.Runnable
                    public final void run() {
                        textClassifierService2.onDisconnected();
                    }
                };
            }
            handler.post(runnable);
        }
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            return this.mBinder;
        }
        return null;
    }

    @Override // android.app.Service
    public boolean onUnbind(Intent intent) {
        onDisconnected();
        return super.onUnbind(intent);
    }

    public void onDetectLanguage(TextClassificationSessionId textClassificationSessionId, final TextLanguage.Request request, CancellationSignal cancellationSignal, final Callback<TextLanguage> callback) {
        this.mSingleThreadExecutor.submit(new Runnable() { // from class: android.service.textclassifier.TextClassifierService$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onDetectLanguage$0(callback, request);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDetectLanguage$0(Callback callback, TextLanguage.Request request) {
        callback.onSuccess(getLocalTextClassifier().detectLanguage(request));
    }

    public void onSuggestConversationActions(TextClassificationSessionId textClassificationSessionId, final ConversationActions.Request request, CancellationSignal cancellationSignal, final Callback<ConversationActions> callback) {
        this.mSingleThreadExecutor.submit(new Runnable() { // from class: android.service.textclassifier.TextClassifierService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onSuggestConversationActions$1(callback, request);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSuggestConversationActions$1(Callback callback, ConversationActions.Request request) {
        callback.onSuccess(getLocalTextClassifier().suggestConversationActions(request));
    }

    @Deprecated
    public final TextClassifier getLocalTextClassifier() {
        return TextClassifier.NO_OP;
    }

    public static TextClassifier getDefaultTextClassifierImplementation(Context context) {
        String defaultTextClassifierPackageName = context.getPackageManager().getDefaultTextClassifierPackageName();
        if (TextUtils.isEmpty(defaultTextClassifierPackageName)) {
            return TextClassifier.NO_OP;
        }
        if (defaultTextClassifierPackageName.equals(context.getPackageName())) {
            throw new RuntimeException("The default text classifier itself should not call thegetDefaultTextClassifierImplementation() method.");
        }
        return ((TextClassificationManager) context.getSystemService(TextClassificationManager.class)).getTextClassifier(2);
    }

    public static <T extends Parcelable> T getResponse(Bundle bundle) {
        return (T) bundle.getParcelable(KEY_RESULT);
    }

    public static <T extends Parcelable> void putResponse(Bundle bundle, T t) {
        bundle.putParcelable(KEY_RESULT, t);
    }

    public static ComponentName getServiceComponentName(Context context, String str, int i) {
        ResolveInfo resolveInfoResolveService = context.getPackageManager().resolveService(new Intent(SERVICE_INTERFACE).setPackage(str), i);
        if (resolveInfoResolveService == null || resolveInfoResolveService.serviceInfo == null) {
            Slog.w(LOG_TAG, String.format("Package or service not found in package %s for user %d", str, Integer.valueOf(context.getUserId())));
            return null;
        }
        ServiceInfo serviceInfo = resolveInfoResolveService.serviceInfo;
        if (!Manifest.permission.BIND_TEXTCLASSIFIER_SERVICE.equals(serviceInfo.permission)) {
            Slog.w(LOG_TAG, String.format("Service %s should require %s permission. Found %s permission", serviceInfo.getComponentName(), Manifest.permission.BIND_TEXTCLASSIFIER_SERVICE, serviceInfo.permission));
            return null;
        }
        return serviceInfo.getComponentName();
    }

    private static final class ProxyCallback<T extends Parcelable> implements Callback<T> {
        private ITextClassifierCallback mTextClassifierCallback;

        private ProxyCallback(ITextClassifierCallback iTextClassifierCallback) {
            this.mTextClassifierCallback = (ITextClassifierCallback) Objects.requireNonNull(iTextClassifierCallback);
        }

        @Override // android.service.textclassifier.TextClassifierService.Callback
        public void onSuccess(T t) {
            try {
                Bundle bundle = new Bundle(1);
                bundle.putParcelable(TextClassifierService.KEY_RESULT, t);
                this.mTextClassifierCallback.onSuccess(bundle);
            } catch (RemoteException unused) {
                Slog.d(TextClassifierService.LOG_TAG, "Error calling callback");
            }
        }

        @Override // android.service.textclassifier.TextClassifierService.Callback
        public void onFailure(CharSequence charSequence) {
            try {
                Slog.w(TextClassifierService.LOG_TAG, "Request fail: " + ((Object) charSequence));
                this.mTextClassifierCallback.onFailure();
            } catch (RemoteException unused) {
                Slog.d(TextClassifierService.LOG_TAG, "Error calling callback");
            }
        }
    }
}
