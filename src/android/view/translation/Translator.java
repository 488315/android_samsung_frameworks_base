package android.view.translation;

import android.content.Context;
import android.os.Binder;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.IBinder;
import android.os.ICancellationSignal;
import android.os.RemoteException;
import android.service.translation.ITranslationCallback;
import android.util.Log;
import android.view.translation.ITranslationDirectManager;
import android.view.translation.Translator;
import com.android.internal.os.IResultReceiver;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/* loaded from: classes4.dex */
public class Translator {
    public static final String EXTRA_SERVICE_BINDER = "binder";
    public static final String EXTRA_SESSION_ID = "sessionId";
    private static final String TAG = "Translator";
    private final Context mContext;
    private boolean mDestroyed;
    private ITranslationDirectManager mDirectServiceBinder;
    private final Handler mHandler;
    private int mId;
    private final Object mLock;
    private final TranslationManager mManager;
    private final ServiceBinderReceiver mServiceBinderReceiver;
    private ITranslationManager mSystemServerBinder;
    private final TranslationContext mTranslationContext;

    static class ServiceBinderReceiver extends IResultReceiver.Stub {
        private Consumer<Translator> mCallback;
        private final CountDownLatch mLatch = new CountDownLatch(1);
        private int mSessionId;
        private final Translator mTranslator;

        ServiceBinderReceiver(Translator translator, Consumer<Translator> consumer) {
            this.mTranslator = translator;
            this.mCallback = consumer;
        }

        ServiceBinderReceiver(Translator translator) {
            this.mTranslator = translator;
        }

        int getSessionStateResult() throws TimeoutException {
            try {
                if (!this.mLatch.await(60000L, TimeUnit.MILLISECONDS)) {
                    throw new TimeoutException("Session not created in 60000ms");
                }
                return this.mSessionId;
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
                throw new TimeoutException("Session not created because interrupted");
            }
        }

        @Override // com.android.internal.os.IResultReceiver
        public void send(int i, Bundle bundle) {
            IBinder iBinder = null;
            if (i == 2) {
                this.mLatch.countDown();
                Consumer<Translator> consumer = this.mCallback;
                if (consumer != null) {
                    consumer.accept(null);
                    return;
                }
                return;
            }
            if (bundle != null) {
                this.mSessionId = bundle.getInt("sessionId");
                iBinder = bundle.getBinder("binder");
                if (iBinder == null) {
                    Log.wtf(Translator.TAG, "No binder extra result");
                    return;
                }
            }
            this.mTranslator.setServiceBinder(iBinder);
            this.mLatch.countDown();
            Consumer<Translator> consumer2 = this.mCallback;
            if (consumer2 != null) {
                consumer2.accept(this.mTranslator);
            }
        }

        static final class TimeoutException extends Exception {
            private TimeoutException(String str) {
                super(str);
            }
        }
    }

    public Translator(Context context, TranslationContext translationContext, int i, TranslationManager translationManager, Handler handler, ITranslationManager iTranslationManager, Consumer<Translator> consumer) {
        this.mLock = new Object();
        this.mContext = context;
        this.mTranslationContext = translationContext;
        this.mId = i;
        this.mManager = translationManager;
        this.mHandler = handler;
        this.mSystemServerBinder = iTranslationManager;
        ServiceBinderReceiver serviceBinderReceiver = new ServiceBinderReceiver(this, consumer);
        this.mServiceBinderReceiver = serviceBinderReceiver;
        try {
            this.mSystemServerBinder.onSessionCreated(translationContext, this.mId, serviceBinderReceiver, context.getUserId());
        } catch (RemoteException e) {
            Log.w(TAG, "RemoteException calling startSession(): " + e);
        }
    }

    public Translator(Context context, TranslationContext translationContext, int i, TranslationManager translationManager, Handler handler, ITranslationManager iTranslationManager) {
        this.mLock = new Object();
        this.mContext = context;
        this.mTranslationContext = translationContext;
        this.mId = i;
        this.mManager = translationManager;
        this.mHandler = handler;
        this.mSystemServerBinder = iTranslationManager;
        this.mServiceBinderReceiver = new ServiceBinderReceiver(this);
    }

    void start() {
        try {
            this.mSystemServerBinder.onSessionCreated(this.mTranslationContext, this.mId, this.mServiceBinderReceiver, this.mContext.getUserId());
        } catch (RemoteException e) {
            Log.w(TAG, "RemoteException calling startSession(): " + e);
        }
    }

    boolean isSessionCreated() throws ServiceBinderReceiver.TimeoutException {
        return this.mServiceBinderReceiver.getSessionStateResult() > 0;
    }

    private int getNextRequestId() {
        return this.mManager.getAvailableRequestId().getAndIncrement();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setServiceBinder(IBinder iBinder) {
        synchronized (this.mLock) {
            if (this.mDirectServiceBinder != null) {
                return;
            }
            if (iBinder != null) {
                this.mDirectServiceBinder = ITranslationDirectManager.Stub.asInterface(iBinder);
            }
        }
    }

    public TranslationContext getTranslationContext() {
        return this.mTranslationContext;
    }

    public int getTranslatorId() {
        return this.mId;
    }

    public void dump(String str, PrintWriter printWriter) {
        printWriter.print(str);
        printWriter.print("translationContext: ");
        printWriter.println(this.mTranslationContext);
    }

    @Deprecated
    public void translate(TranslationRequest translationRequest, Executor executor, Consumer<TranslationResponse> consumer) {
        Objects.requireNonNull(translationRequest, "Translation request cannot be null");
        Objects.requireNonNull(executor, "Executor cannot be null");
        Objects.requireNonNull(consumer, "Callback cannot be null");
        if (isDestroyed()) {
            throw new IllegalStateException("This translator has been destroyed");
        }
        try {
            this.mDirectServiceBinder.onTranslationRequest(translationRequest, this.mId, CancellationSignal.createTransport(), new TranslationResponseCallbackImpl(consumer, executor));
        } catch (RemoteException e) {
            Log.w(TAG, "RemoteException calling requestTranslate(): " + e);
        }
    }

    public void translate(TranslationRequest translationRequest, CancellationSignal cancellationSignal, Executor executor, Consumer<TranslationResponse> consumer) {
        ICancellationSignal iCancellationSignal;
        Objects.requireNonNull(translationRequest, "Translation request cannot be null");
        Objects.requireNonNull(executor, "Executor cannot be null");
        Objects.requireNonNull(consumer, "Callback cannot be null");
        if (isDestroyed()) {
            throw new IllegalStateException("This translator has been destroyed");
        }
        if (cancellationSignal != null) {
            iCancellationSignal = CancellationSignal.createTransport();
            cancellationSignal.setRemote(iCancellationSignal);
        } else {
            iCancellationSignal = null;
        }
        try {
            this.mDirectServiceBinder.onTranslationRequest(translationRequest, this.mId, iCancellationSignal, new TranslationResponseCallbackImpl(consumer, executor));
        } catch (RemoteException e) {
            Log.w(TAG, "RemoteException calling requestTranslate(): " + e);
        }
    }

    public void destroy() {
        synchronized (this.mLock) {
            if (this.mDestroyed) {
                return;
            }
            this.mDestroyed = true;
            try {
                this.mDirectServiceBinder.onFinishTranslationSession(this.mId);
            } catch (RemoteException unused) {
                Log.w(TAG, "RemoteException calling onSessionFinished");
            }
            this.mDirectServiceBinder = null;
            this.mManager.removeTranslator(this.mId);
        }
    }

    public boolean isDestroyed() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mDestroyed;
        }
        return z;
    }

    public void requestUiTranslate(TranslationRequest translationRequest, Executor executor, Consumer<TranslationResponse> consumer) {
        if (this.mDirectServiceBinder == null) {
            Log.wtf(TAG, "Translator created without proper initialization.");
            return;
        }
        try {
            this.mDirectServiceBinder.onTranslationRequest(translationRequest, this.mId, CancellationSignal.createTransport(), new TranslationResponseCallbackImpl(consumer, executor));
        } catch (RemoteException unused) {
            Log.w(TAG, "RemoteException calling flushRequest");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class TranslationResponseCallbackImpl extends ITranslationCallback.Stub {
        private final Consumer<TranslationResponse> mCallback;
        private final Executor mExecutor;

        TranslationResponseCallbackImpl(Consumer<TranslationResponse> consumer, Executor executor) {
            this.mCallback = consumer;
            this.mExecutor = executor;
        }

        @Override // android.service.translation.ITranslationCallback
        public void onTranslationResponse(final TranslationResponse translationResponse) throws RemoteException {
            if (Log.isLoggable(UiTranslationManager.LOG_TAG, 3)) {
                Log.i(Translator.TAG, "onTranslationResponse called.");
            }
            Runnable runnable = new Runnable() { // from class: android.view.translation.Translator$TranslationResponseCallbackImpl$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    Translator.TranslationResponseCallbackImpl.this.lambda$onTranslationResponse$0(translationResponse);
                }
            };
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(runnable);
            } finally {
                restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onTranslationResponse$0(TranslationResponse translationResponse) {
            this.mCallback.accept(translationResponse);
        }
    }
}
