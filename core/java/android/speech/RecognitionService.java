package android.speech;

import android.Manifest;
import android.app.AppOpsManager;
import android.app.Service;
import android.content.AttributionSource;
import android.content.Context;
import android.content.ContextParams;
import android.content.Intent;
import android.content.PermissionChecker;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;

import com.android.internal.util.function.pooled.PooledLambda;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public abstract class RecognitionService extends Service {
    private static final boolean DBG = false;
    private static final int DEFAULT_MAX_CONCURRENT_SESSIONS_COUNT = 1;
    private static final int MSG_CANCEL = 3;
    private static final int MSG_CHECK_RECOGNITION_SUPPORT = 5;
    private static final int MSG_RESET = 4;
    private static final int MSG_START_LISTENING = 1;
    private static final int MSG_STOP_LISTENING = 2;
    private static final int MSG_TRIGGER_MODEL_DOWNLOAD = 6;
    public static final String SERVICE_INTERFACE = "android.speech.RecognitionService";
    public static final String SERVICE_META_DATA = "android.speech";
    private static final String TAG = "RecognitionService";
    private final Map<IBinder, SessionState> mSessions = new HashMap();
    private final RecognitionServiceBinder mBinder = new RecognitionServiceBinder(this);
    private final Handler mHandler =
            new Handler() { // from class: android.speech.RecognitionService.1
                @Override // android.os.Handler
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case 1:
                            StartListeningArgs args = (StartListeningArgs) msg.obj;
                            RecognitionService.this.dispatchStartListening(
                                    args.mIntent, args.mListener, args.mAttributionSource);
                            break;
                        case 2:
                            RecognitionService.this.dispatchStopListening(
                                    (IRecognitionListener) msg.obj);
                            break;
                        case 3:
                            RecognitionService.this.dispatchCancel((IRecognitionListener) msg.obj);
                            break;
                        case 4:
                            RecognitionService.this.dispatchClearCallback(
                                    (IRecognitionListener) msg.obj);
                            break;
                        case 5:
                            CheckRecognitionSupportArgs checkArgs =
                                    (CheckRecognitionSupportArgs) msg.obj;
                            RecognitionService.this.dispatchCheckRecognitionSupport(
                                    checkArgs.mIntent,
                                    checkArgs.callback,
                                    checkArgs.mAttributionSource);
                            break;
                        case 6:
                            ModelDownloadArgs modelDownloadArgs = (ModelDownloadArgs) msg.obj;
                            RecognitionService.this.dispatchTriggerModelDownload(
                                    modelDownloadArgs.mIntent,
                                    modelDownloadArgs.mAttributionSource,
                                    modelDownloadArgs.mListener);
                            break;
                    }
                }
            };

    protected abstract void onCancel(Callback callback);

    protected abstract void onStartListening(Intent intent, Callback callback);

    protected abstract void onStopListening(Callback callback);

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:16:0x003c A[Catch: RemoteException -> 0x0086, TryCatch #0 {RemoteException -> 0x0086, blocks: (B:4:0x0011, B:6:0x001d, B:9:0x0028, B:11:0x0030, B:16:0x003c, B:18:0x0057, B:22:0x005d, B:24:0x0064, B:25:0x0076, B:27:0x007c), top: B:2:0x000f }] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0064 A[Catch: RemoteException -> 0x0086, TryCatch #0 {RemoteException -> 0x0086, blocks: (B:4:0x0011, B:6:0x001d, B:9:0x0028, B:11:0x0030, B:16:0x003c, B:18:0x0057, B:22:0x005d, B:24:0x0064, B:25:0x0076, B:27:0x007c), top: B:2:0x000f }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void dispatchStartListening(
            android.content.Intent r7,
            android.speech.IRecognitionListener r8,
            android.content.AttributionSource r9) {
        /*
            r6 = this;
            r0 = 0
            java.util.Map<android.os.IBinder, android.speech.RecognitionService$SessionState> r1 = r6.mSessions
            android.os.IBinder r2 = r8.asBinder()
            java.lang.Object r1 = r1.get(r2)
            android.speech.RecognitionService$SessionState r1 = (android.speech.RecognitionService.SessionState) r1
            java.lang.String r2 = "RecognitionService"
            if (r1 != 0) goto L7c
            java.util.Map<android.os.IBinder, android.speech.RecognitionService$SessionState> r3 = r6.mSessions     // Catch: android.os.RemoteException -> L86
            int r3 = r3.size()     // Catch: android.os.RemoteException -> L86
            int r4 = r6.getMaxConcurrentSessionsCount()     // Catch: android.os.RemoteException -> L86
            if (r3 < r4) goto L28
            r3 = 8
            r8.onError(r3)     // Catch: android.os.RemoteException -> L86
            java.lang.String r3 = "#startListening received when the service's capacity is full - ignoring this call."
            android.util.Log.i(r2, r3)     // Catch: android.os.RemoteException -> L86
            return
        L28:
            java.lang.String r3 = "android.speech.extra.AUDIO_SOURCE"
            boolean r3 = r7.hasExtra(r3)     // Catch: android.os.RemoteException -> L86
            if (r3 != 0) goto L39
            boolean r3 = r6.checkPermissionForPreflightNotHardDenied(r9)     // Catch: android.os.RemoteException -> L86
            if (r3 == 0) goto L37
            goto L39
        L37:
            r3 = 0
            goto L3a
        L39:
            r3 = 1
        L3a:
            if (r3 == 0) goto L55
            android.speech.RecognitionService$Callback r4 = new android.speech.RecognitionService$Callback     // Catch: android.os.RemoteException -> L86
            r5 = 0
            r4.<init>(r8, r9)     // Catch: android.os.RemoteException -> L86
            r0 = r4
            android.speech.RecognitionService$SessionState r4 = new android.speech.RecognitionService$SessionState     // Catch: android.os.RemoteException -> L86
            r4.<init>(r0)     // Catch: android.os.RemoteException -> L86
            r1 = r4
            java.util.Map<android.os.IBinder, android.speech.RecognitionService$SessionState> r4 = r6.mSessions     // Catch: android.os.RemoteException -> L86
            android.os.IBinder r5 = r8.asBinder()     // Catch: android.os.RemoteException -> L86
            r4.put(r5, r1)     // Catch: android.os.RemoteException -> L86
            r6.onStartListening(r7, r0)     // Catch: android.os.RemoteException -> L86
        L55:
            if (r3 == 0) goto L5d
            boolean r4 = r6.checkPermissionAndStartDataDelivery(r1)     // Catch: android.os.RemoteException -> L86
            if (r4 != 0) goto L7b
        L5d:
            r4 = 9
            r8.onError(r4)     // Catch: android.os.RemoteException -> L86
            if (r3 == 0) goto L76
            r6.onCancel(r0)     // Catch: android.os.RemoteException -> L86
            java.util.Map<android.os.IBinder, android.speech.RecognitionService$SessionState> r4 = r6.mSessions     // Catch: android.os.RemoteException -> L86
            android.os.IBinder r5 = r8.asBinder()     // Catch: android.os.RemoteException -> L86
            r4.remove(r5)     // Catch: android.os.RemoteException -> L86
            r6.finishDataDelivery(r1)     // Catch: android.os.RemoteException -> L86
            r1.reset()     // Catch: android.os.RemoteException -> L86
        L76:
            java.lang.String r4 = "#startListening received from a caller without permission android.permission.RECORD_AUDIO."
            android.util.Log.i(r2, r4)     // Catch: android.os.RemoteException -> L86
        L7b:
            goto L85
        L7c:
            r3 = 5
            r8.onError(r3)     // Catch: android.os.RemoteException -> L86
            java.lang.String r3 = "#startListening received for a listener which is already in session - ignoring this call."
            android.util.Log.i(r2, r3)     // Catch: android.os.RemoteException -> L86
        L85:
            goto L8c
        L86:
            r3 = move-exception
            java.lang.String r4 = "#onError call from #startListening failed."
            android.util.Log.d(r2, r4)
        L8c:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " android.speech.RecognitionService.dispatchStartListening(android.content.Intent,"
                    + " android.speech.IRecognitionListener,"
                    + " android.content.AttributionSource):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchStopListening(IRecognitionListener listener) {
        SessionState sessionState = this.mSessions.get(listener.asBinder());
        if (sessionState == null) {
            try {
                listener.onError(5);
            } catch (RemoteException e) {
                Log.d(TAG, "#onError call from #stopListening failed.");
            }
            Log.w(
                    TAG,
                    "#stopListening received for a listener which has not started a session -"
                        + " ignoring this call.");
            return;
        }
        onStopListening(sessionState.mCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchCancel(IRecognitionListener listener) {
        SessionState sessionState = this.mSessions.get(listener.asBinder());
        if (sessionState == null) {
            Log.w(
                    TAG,
                    "#cancel received for a listener which has not started a session - ignoring"
                        + " this call.");
        } else {
            onCancel(sessionState.mCallback);
            dispatchClearCallback(listener);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchClearCallback(IRecognitionListener listener) {
        SessionState sessionState = this.mSessions.remove(listener.asBinder());
        if (sessionState != null) {
            finishDataDelivery(sessionState);
            sessionState.reset();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchCheckRecognitionSupport(
            Intent intent,
            IRecognitionSupportCallback callback,
            AttributionSource attributionSource) {
        onCheckRecognitionSupport(intent, attributionSource, new SupportCallback(callback));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchTriggerModelDownload(
            Intent intent,
            AttributionSource attributionSource,
            final IModelDownloadListener listener) {
        if (listener == null) {
            onTriggerModelDownload(intent, attributionSource);
        } else {
            onTriggerModelDownload(
                    intent,
                    attributionSource,
                    new ModelDownloadListener() { // from class: android.speech.RecognitionService.2
                        private final Object mLock = new Object();
                        private boolean mIsTerminated = false;

                        @Override // android.speech.ModelDownloadListener
                        public void onProgress(int completedPercent) {
                            synchronized (this.mLock) {
                                if (this.mIsTerminated) {
                                    return;
                                }
                                try {
                                    listener.onProgress(completedPercent);
                                } catch (RemoteException e) {
                                    throw e.rethrowFromSystemServer();
                                }
                            }
                        }

                        @Override // android.speech.ModelDownloadListener
                        public void onSuccess() {
                            synchronized (this.mLock) {
                                if (this.mIsTerminated) {
                                    return;
                                }
                                this.mIsTerminated = true;
                                try {
                                    listener.onSuccess();
                                } catch (RemoteException e) {
                                    throw e.rethrowFromSystemServer();
                                }
                            }
                        }

                        @Override // android.speech.ModelDownloadListener
                        public void onScheduled() {
                            synchronized (this.mLock) {
                                if (this.mIsTerminated) {
                                    return;
                                }
                                this.mIsTerminated = true;
                                try {
                                    listener.onScheduled();
                                } catch (RemoteException e) {
                                    throw e.rethrowFromSystemServer();
                                }
                            }
                        }

                        @Override // android.speech.ModelDownloadListener
                        public void onError(int error) {
                            synchronized (this.mLock) {
                                if (this.mIsTerminated) {
                                    return;
                                }
                                this.mIsTerminated = true;
                                try {
                                    listener.onError(error);
                                } catch (RemoteException e) {
                                    throw e.rethrowFromSystemServer();
                                }
                            }
                        }
                    });
        }
    }

    private static class StartListeningArgs {
        public final AttributionSource mAttributionSource;
        public final Intent mIntent;
        public final IRecognitionListener mListener;

        public StartListeningArgs(
                Intent intent, IRecognitionListener listener, AttributionSource attributionSource) {
            this.mIntent = intent;
            this.mListener = listener;
            this.mAttributionSource = attributionSource;
        }
    }

    private static class CheckRecognitionSupportArgs {
        public final IRecognitionSupportCallback callback;
        public final AttributionSource mAttributionSource;
        public final Intent mIntent;

        private CheckRecognitionSupportArgs(
                Intent intent,
                IRecognitionSupportCallback callback,
                AttributionSource attributionSource) {
            this.mIntent = intent;
            this.callback = callback;
            this.mAttributionSource = attributionSource;
        }
    }

    private static class ModelDownloadArgs {
        final AttributionSource mAttributionSource;
        final Intent mIntent;
        final IModelDownloadListener mListener;

        private ModelDownloadArgs(
                Intent intent,
                AttributionSource attributionSource,
                IModelDownloadListener listener) {
            this.mIntent = intent;
            this.mAttributionSource = attributionSource;
            this.mListener = listener;
        }
    }

    public void onCheckRecognitionSupport(
            Intent recognizerIntent, SupportCallback supportCallback) {
        supportCallback.onError(14);
    }

    public void onCheckRecognitionSupport(
            Intent recognizerIntent,
            AttributionSource attributionSource,
            SupportCallback supportCallback) {
        onCheckRecognitionSupport(recognizerIntent, supportCallback);
    }

    public void onTriggerModelDownload(Intent recognizerIntent) {}

    public void onTriggerModelDownload(
            Intent recognizerIntent, AttributionSource attributionSource) {
        onTriggerModelDownload(recognizerIntent);
    }

    public void onTriggerModelDownload(
            Intent recognizerIntent,
            AttributionSource attributionSource,
            ModelDownloadListener listener) {
        listener.onError(15);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public Context createContext(ContextParams contextParams) {
        if (contextParams.getNextAttributionSource() != null) {
            if (this.mHandler.getLooper().equals(Looper.myLooper())) {
                handleAttributionContextCreation(contextParams.getNextAttributionSource());
            } else {
                this.mHandler.sendMessage(
                        PooledLambda.obtainMessage(
                                new Consumer() { // from class:
                                                 // android.speech.RecognitionService$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        RecognitionService.this.handleAttributionContextCreation(
                                                (AttributionSource) obj);
                                    }
                                },
                                contextParams.getNextAttributionSource()));
            }
        }
        return super.createContext(contextParams);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleAttributionContextCreation(AttributionSource attributionSource) {
        for (SessionState sessionState : this.mSessions.values()) {
            Callback currentCallback = sessionState.mCallback;
            if (currentCallback != null
                    && currentCallback.mCallingAttributionSource.equals(attributionSource)) {
                currentCallback.mAttributionContextCreated = true;
            }
        }
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        onBindInternal();
        return this.mBinder;
    }

    public void onBindInternal() {}

    @Override // android.app.Service
    public void onDestroy() {
        for (SessionState sessionState : this.mSessions.values()) {
            finishDataDelivery(sessionState);
            sessionState.reset();
        }
        this.mSessions.clear();
        this.mBinder.clearReference();
        super.onDestroy();
    }

    public int getMaxConcurrentSessionsCount() {
        return 1;
    }

    public class Callback {
        private Context mAttributionContext;
        private boolean mAttributionContextCreated;
        private final AttributionSource mCallingAttributionSource;
        private final IRecognitionListener mListener;

        private Callback(IRecognitionListener listener, AttributionSource attributionSource) {
            this.mListener = listener;
            this.mCallingAttributionSource = attributionSource;
        }

        public void beginningOfSpeech() throws RemoteException {
            this.mListener.onBeginningOfSpeech();
        }

        public void bufferReceived(byte[] buffer) throws RemoteException {
            this.mListener.onBufferReceived(buffer);
        }

        public void endOfSpeech() throws RemoteException {
            this.mListener.onEndOfSpeech();
        }

        public void error(int error) throws RemoteException {
            Message.obtain(RecognitionService.this.mHandler, 4, this.mListener).sendToTarget();
            this.mListener.onError(error);
        }

        public void partialResults(Bundle partialResults) throws RemoteException {
            this.mListener.onPartialResults(partialResults);
        }

        public void readyForSpeech(Bundle params) throws RemoteException {
            this.mListener.onReadyForSpeech(params);
        }

        public void results(Bundle results) throws RemoteException {
            Message.obtain(RecognitionService.this.mHandler, 4, this.mListener).sendToTarget();
            this.mListener.onResults(results);
        }

        public void rmsChanged(float rmsdB) throws RemoteException {
            this.mListener.onRmsChanged(rmsdB);
        }

        public void segmentResults(Bundle results) throws RemoteException {
            this.mListener.onSegmentResults(results);
        }

        public void endOfSegmentedSession() throws RemoteException {
            Message.obtain(RecognitionService.this.mHandler, 4, this.mListener).sendToTarget();
            this.mListener.onEndOfSegmentedSession();
        }

        public void languageDetection(Bundle results) {
            try {
                this.mListener.onLanguageDetection(results);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public int getCallingUid() {
            return this.mCallingAttributionSource.getUid();
        }

        public AttributionSource getCallingAttributionSource() {
            return this.mCallingAttributionSource;
        }

        Context getAttributionContextForCaller() {
            if (this.mAttributionContext == null) {
                this.mAttributionContext =
                        RecognitionService.this.createContext(
                                new ContextParams.Builder()
                                        .setNextAttributionSource(this.mCallingAttributionSource)
                                        .build());
            }
            return this.mAttributionContext;
        }
    }

    public static class SupportCallback {
        private final IRecognitionSupportCallback mCallback;

        private SupportCallback(IRecognitionSupportCallback callback) {
            this.mCallback = callback;
        }

        public void onSupportResult(RecognitionSupport recognitionSupport) {
            try {
                this.mCallback.onSupportResult(recognitionSupport);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void onError(int errorCode) {
            try {
                this.mCallback.onError(errorCode);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    private static final class RecognitionServiceBinder extends IRecognitionService.Stub {
        private final WeakReference<RecognitionService> mServiceRef;

        public RecognitionServiceBinder(RecognitionService service) {
            this.mServiceRef = new WeakReference<>(service);
        }

        @Override // android.speech.IRecognitionService
        public void startListening(
                Intent recognizerIntent,
                IRecognitionListener listener,
                AttributionSource attributionSource) {
            Objects.requireNonNull(attributionSource);
            attributionSource.enforceCallingUid();
            RecognitionService service = this.mServiceRef.get();
            if (service != null) {
                service.mHandler.sendMessage(
                        Message.obtain(
                                service.mHandler,
                                1,
                                new StartListeningArgs(
                                        recognizerIntent, listener, attributionSource)));
            }
        }

        @Override // android.speech.IRecognitionService
        public void stopListening(IRecognitionListener listener) {
            RecognitionService service = this.mServiceRef.get();
            if (service != null) {
                service.mHandler.sendMessage(Message.obtain(service.mHandler, 2, listener));
            }
        }

        @Override // android.speech.IRecognitionService
        public void cancel(IRecognitionListener listener, boolean isShutdown) {
            RecognitionService service = this.mServiceRef.get();
            if (service != null) {
                service.mHandler.sendMessage(Message.obtain(service.mHandler, 3, listener));
            }
        }

        @Override // android.speech.IRecognitionService
        public void checkRecognitionSupport(
                Intent recognizerIntent,
                AttributionSource attributionSource,
                IRecognitionSupportCallback callback) {
            RecognitionService service = this.mServiceRef.get();
            if (service != null) {
                service.mHandler.sendMessage(
                        Message.obtain(
                                service.mHandler,
                                5,
                                new CheckRecognitionSupportArgs(
                                        recognizerIntent, callback, attributionSource)));
            }
        }

        @Override // android.speech.IRecognitionService
        public void triggerModelDownload(
                Intent recognizerIntent,
                AttributionSource attributionSource,
                IModelDownloadListener listener) {
            RecognitionService service = this.mServiceRef.get();
            if (service != null) {
                service.mHandler.sendMessage(
                        Message.obtain(
                                service.mHandler,
                                6,
                                new ModelDownloadArgs(
                                        recognizerIntent, attributionSource, listener)));
            }
        }

        public void clearReference() {
            this.mServiceRef.clear();
        }
    }

    private boolean checkPermissionAndStartDataDelivery(SessionState sessionState) {
        if (sessionState.mCallback.mAttributionContextCreated) {
            return true;
        }
        if (PermissionChecker.checkPermissionAndStartDataDelivery(
                        this,
                        Manifest.permission.RECORD_AUDIO,
                        sessionState
                                .mCallback
                                .getAttributionContextForCaller()
                                .getAttributionSource(),
                        null)
                == 0) {
            sessionState.mStartedDataDelivery = true;
        }
        return sessionState.mStartedDataDelivery;
    }

    private boolean checkPermissionForPreflightNotHardDenied(AttributionSource attributionSource) {
        int result =
                PermissionChecker.checkPermissionForPreflight(
                        this, Manifest.permission.RECORD_AUDIO, attributionSource);
        return result == 0 || result == 1;
    }

    void finishDataDelivery(SessionState sessionState) {
        if (sessionState.mStartedDataDelivery) {
            sessionState.mStartedDataDelivery = false;
            String op = AppOpsManager.permissionToOp(Manifest.permission.RECORD_AUDIO);
            PermissionChecker.finishDataDelivery(
                    this,
                    op,
                    sessionState.mCallback.getAttributionContextForCaller().getAttributionSource());
        }
    }

    private static class SessionState {
        private Callback mCallback;
        private boolean mStartedDataDelivery;

        SessionState(Callback callback, boolean startedDataDelivery) {
            this.mCallback = callback;
            this.mStartedDataDelivery = startedDataDelivery;
        }

        SessionState(Callback currentCallback) {
            this(currentCallback, false);
        }

        void reset() {
            this.mCallback = null;
            this.mStartedDataDelivery = false;
        }
    }
}
