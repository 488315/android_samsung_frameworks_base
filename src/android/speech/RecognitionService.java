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
import android.speech.IRecognitionService;
import android.util.Log;
import com.android.internal.util.function.pooled.PooledLambda;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
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
    private final Handler mHandler = new Handler() { // from class: android.speech.RecognitionService.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    StartListeningArgs startListeningArgs = (StartListeningArgs) message.obj;
                    RecognitionService.this.dispatchStartListening(startListeningArgs.mIntent, startListeningArgs.mListener, startListeningArgs.mAttributionSource);
                    break;
                case 2:
                    RecognitionService.this.dispatchStopListening((IRecognitionListener) message.obj);
                    break;
                case 3:
                    RecognitionService.this.dispatchCancel((IRecognitionListener) message.obj);
                    break;
                case 4:
                    RecognitionService.this.dispatchClearCallback((IRecognitionListener) message.obj);
                    break;
                case 5:
                    CheckRecognitionSupportArgs checkRecognitionSupportArgs = (CheckRecognitionSupportArgs) message.obj;
                    RecognitionService.this.dispatchCheckRecognitionSupport(checkRecognitionSupportArgs.mIntent, checkRecognitionSupportArgs.callback, checkRecognitionSupportArgs.mAttributionSource);
                    break;
                case 6:
                    ModelDownloadArgs modelDownloadArgs = (ModelDownloadArgs) message.obj;
                    RecognitionService.this.dispatchTriggerModelDownload(modelDownloadArgs.mIntent, modelDownloadArgs.mAttributionSource, modelDownloadArgs.mListener);
                    break;
            }
        }
    };

    public int getMaxConcurrentSessionsCount() {
        return 1;
    }

    public void onBindInternal() {
    }

    protected abstract void onCancel(Callback callback);

    protected abstract void onStartListening(Intent intent, Callback callback);

    protected abstract void onStopListening(Callback callback);

    public void onTriggerModelDownload(Intent intent) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:17:0x003c A[Catch: RemoteException -> 0x0085, TryCatch #0 {RemoteException -> 0x0085, blocks: (B:4:0x0010, B:6:0x001c, B:9:0x0027, B:11:0x002f, B:17:0x003c, B:19:0x0056, B:22:0x005c, B:24:0x0063, B:25:0x0075, B:28:0x007b), top: B:2:0x000e }] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0063 A[Catch: RemoteException -> 0x0085, TryCatch #0 {RemoteException -> 0x0085, blocks: (B:4:0x0010, B:6:0x001c, B:9:0x0027, B:11:0x002f, B:17:0x003c, B:19:0x0056, B:22:0x005c, B:24:0x0063, B:25:0x0075, B:28:0x007b), top: B:2:0x000e }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void dispatchStartListening(android.content.Intent r6, android.speech.IRecognitionListener r7, android.content.AttributionSource r8) {
        /*
            r5 = this;
            java.util.Map<android.os.IBinder, android.speech.RecognitionService$SessionState> r0 = r5.mSessions
            android.os.IBinder r1 = r7.asBinder()
            java.lang.Object r0 = r0.get(r1)
            android.speech.RecognitionService$SessionState r0 = (android.speech.RecognitionService.SessionState) r0
            java.lang.String r1 = "RecognitionService"
            if (r0 != 0) goto L7b
            java.util.Map<android.os.IBinder, android.speech.RecognitionService$SessionState> r2 = r5.mSessions     // Catch: android.os.RemoteException -> L85
            int r2 = r2.size()     // Catch: android.os.RemoteException -> L85
            int r3 = r5.getMaxConcurrentSessionsCount()     // Catch: android.os.RemoteException -> L85
            if (r2 < r3) goto L27
            r5 = 8
            r7.onError(r5)     // Catch: android.os.RemoteException -> L85
            java.lang.String r5 = "#startListening received when the service's capacity is full - ignoring this call."
            android.util.Log.i(r1, r5)     // Catch: android.os.RemoteException -> L85
            return
        L27:
            java.lang.String r2 = "android.speech.extra.AUDIO_SOURCE"
            boolean r2 = r6.hasExtra(r2)     // Catch: android.os.RemoteException -> L85
            if (r2 != 0) goto L38
            boolean r2 = r5.checkPermissionForPreflightNotHardDenied(r8)     // Catch: android.os.RemoteException -> L85
            if (r2 == 0) goto L36
            goto L38
        L36:
            r2 = 0
            goto L39
        L38:
            r2 = 1
        L39:
            r3 = 0
            if (r2 == 0) goto L54
            android.speech.RecognitionService$Callback r0 = new android.speech.RecognitionService$Callback     // Catch: android.os.RemoteException -> L85
            r0.<init>(r7, r8)     // Catch: android.os.RemoteException -> L85
            android.speech.RecognitionService$SessionState r8 = new android.speech.RecognitionService$SessionState     // Catch: android.os.RemoteException -> L85
            r8.<init>(r0)     // Catch: android.os.RemoteException -> L85
            java.util.Map<android.os.IBinder, android.speech.RecognitionService$SessionState> r3 = r5.mSessions     // Catch: android.os.RemoteException -> L85
            android.os.IBinder r4 = r7.asBinder()     // Catch: android.os.RemoteException -> L85
            r3.put(r4, r8)     // Catch: android.os.RemoteException -> L85
            r5.onStartListening(r6, r0)     // Catch: android.os.RemoteException -> L85
            r3 = r0
            r0 = r8
        L54:
            if (r2 == 0) goto L5c
            boolean r6 = r5.checkPermissionAndStartDataDelivery(r0)     // Catch: android.os.RemoteException -> L85
            if (r6 != 0) goto L8a
        L5c:
            r6 = 9
            r7.onError(r6)     // Catch: android.os.RemoteException -> L85
            if (r2 == 0) goto L75
            r5.onCancel(r3)     // Catch: android.os.RemoteException -> L85
            java.util.Map<android.os.IBinder, android.speech.RecognitionService$SessionState> r6 = r5.mSessions     // Catch: android.os.RemoteException -> L85
            android.os.IBinder r7 = r7.asBinder()     // Catch: android.os.RemoteException -> L85
            r6.remove(r7)     // Catch: android.os.RemoteException -> L85
            r5.finishDataDelivery(r0)     // Catch: android.os.RemoteException -> L85
            r0.reset()     // Catch: android.os.RemoteException -> L85
        L75:
            java.lang.String r5 = "#startListening received from a caller without permission android.permission.RECORD_AUDIO."
            android.util.Log.i(r1, r5)     // Catch: android.os.RemoteException -> L85
            return
        L7b:
            r5 = 5
            r7.onError(r5)     // Catch: android.os.RemoteException -> L85
            java.lang.String r5 = "#startListening received for a listener which is already in session - ignoring this call."
            android.util.Log.i(r1, r5)     // Catch: android.os.RemoteException -> L85
            return
        L85:
            java.lang.String r5 = "#onError call from #startListening failed."
            android.util.Log.d(r1, r5)
        L8a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.speech.RecognitionService.dispatchStartListening(android.content.Intent, android.speech.IRecognitionListener, android.content.AttributionSource):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchStopListening(IRecognitionListener iRecognitionListener) {
        SessionState sessionState = this.mSessions.get(iRecognitionListener.asBinder());
        if (sessionState == null) {
            try {
                iRecognitionListener.onError(5);
            } catch (RemoteException unused) {
                Log.d(TAG, "#onError call from #stopListening failed.");
            }
            Log.w(TAG, "#stopListening received for a listener which has not started a session - ignoring this call.");
            return;
        }
        onStopListening(sessionState.mCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchCancel(IRecognitionListener iRecognitionListener) {
        SessionState sessionState = this.mSessions.get(iRecognitionListener.asBinder());
        if (sessionState == null) {
            Log.w(TAG, "#cancel received for a listener which has not started a session - ignoring this call.");
        } else {
            onCancel(sessionState.mCallback);
            dispatchClearCallback(iRecognitionListener);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchClearCallback(IRecognitionListener iRecognitionListener) {
        SessionState remove = this.mSessions.remove(iRecognitionListener.asBinder());
        if (remove != null) {
            finishDataDelivery(remove);
            remove.reset();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchCheckRecognitionSupport(Intent intent, IRecognitionSupportCallback iRecognitionSupportCallback, AttributionSource attributionSource) {
        onCheckRecognitionSupport(intent, attributionSource, new SupportCallback(iRecognitionSupportCallback));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchTriggerModelDownload(Intent intent, AttributionSource attributionSource, final IModelDownloadListener iModelDownloadListener) {
        if (iModelDownloadListener == null) {
            onTriggerModelDownload(intent, attributionSource);
        } else {
            onTriggerModelDownload(intent, attributionSource, new ModelDownloadListener(this) { // from class: android.speech.RecognitionService.2
                private final Object mLock = new Object();
                private boolean mIsTerminated = false;

                @Override // android.speech.ModelDownloadListener
                public void onProgress(int i) {
                    synchronized (this.mLock) {
                        if (this.mIsTerminated) {
                            return;
                        }
                        try {
                            iModelDownloadListener.onProgress(i);
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
                            iModelDownloadListener.onSuccess();
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
                            iModelDownloadListener.onScheduled();
                        } catch (RemoteException e) {
                            throw e.rethrowFromSystemServer();
                        }
                    }
                }

                @Override // android.speech.ModelDownloadListener
                public void onError(int i) {
                    synchronized (this.mLock) {
                        if (this.mIsTerminated) {
                            return;
                        }
                        this.mIsTerminated = true;
                        try {
                            iModelDownloadListener.onError(i);
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

        public StartListeningArgs(Intent intent, IRecognitionListener iRecognitionListener, AttributionSource attributionSource) {
            this.mIntent = intent;
            this.mListener = iRecognitionListener;
            this.mAttributionSource = attributionSource;
        }
    }

    private static class CheckRecognitionSupportArgs {
        public final IRecognitionSupportCallback callback;
        public final AttributionSource mAttributionSource;
        public final Intent mIntent;

        private CheckRecognitionSupportArgs(Intent intent, IRecognitionSupportCallback iRecognitionSupportCallback, AttributionSource attributionSource) {
            this.mIntent = intent;
            this.callback = iRecognitionSupportCallback;
            this.mAttributionSource = attributionSource;
        }
    }

    private static class ModelDownloadArgs {
        final AttributionSource mAttributionSource;
        final Intent mIntent;
        final IModelDownloadListener mListener;

        private ModelDownloadArgs(Intent intent, AttributionSource attributionSource, IModelDownloadListener iModelDownloadListener) {
            this.mIntent = intent;
            this.mAttributionSource = attributionSource;
            this.mListener = iModelDownloadListener;
        }
    }

    public void onCheckRecognitionSupport(Intent intent, SupportCallback supportCallback) {
        supportCallback.onError(14);
    }

    public void onCheckRecognitionSupport(Intent intent, AttributionSource attributionSource, SupportCallback supportCallback) {
        onCheckRecognitionSupport(intent, supportCallback);
    }

    public void onTriggerModelDownload(Intent intent, AttributionSource attributionSource) {
        onTriggerModelDownload(intent);
    }

    public void onTriggerModelDownload(Intent intent, AttributionSource attributionSource, ModelDownloadListener modelDownloadListener) {
        modelDownloadListener.onError(15);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public Context createContext(ContextParams contextParams) {
        if (contextParams.getNextAttributionSource() != null) {
            if (this.mHandler.getLooper().equals(Looper.myLooper())) {
                handleAttributionContextCreation(contextParams.getNextAttributionSource());
            } else {
                this.mHandler.sendMessage(PooledLambda.obtainMessage(new Consumer() { // from class: android.speech.RecognitionService$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        RecognitionService.this.handleAttributionContextCreation((AttributionSource) obj);
                    }
                }, contextParams.getNextAttributionSource()));
            }
        }
        return super.createContext(contextParams);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleAttributionContextCreation(AttributionSource attributionSource) {
        Iterator<SessionState> it = this.mSessions.values().iterator();
        while (it.hasNext()) {
            Callback callback = it.next().mCallback;
            if (callback != null && callback.mCallingAttributionSource.equals(attributionSource)) {
                callback.mAttributionContextCreated = true;
            }
        }
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        onBindInternal();
        return this.mBinder;
    }

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

    public class Callback {
        private Context mAttributionContext;
        private boolean mAttributionContextCreated;
        private final AttributionSource mCallingAttributionSource;
        private final IRecognitionListener mListener;

        private Callback(IRecognitionListener iRecognitionListener, AttributionSource attributionSource) {
            this.mListener = iRecognitionListener;
            this.mCallingAttributionSource = attributionSource;
        }

        public void beginningOfSpeech() throws RemoteException {
            this.mListener.onBeginningOfSpeech();
        }

        public void bufferReceived(byte[] bArr) throws RemoteException {
            this.mListener.onBufferReceived(bArr);
        }

        public void endOfSpeech() throws RemoteException {
            this.mListener.onEndOfSpeech();
        }

        public void error(int i) throws RemoteException {
            Message.obtain(RecognitionService.this.mHandler, 4, this.mListener).sendToTarget();
            this.mListener.onError(i);
        }

        public void partialResults(Bundle bundle) throws RemoteException {
            this.mListener.onPartialResults(bundle);
        }

        public void readyForSpeech(Bundle bundle) throws RemoteException {
            this.mListener.onReadyForSpeech(bundle);
        }

        public void results(Bundle bundle) throws RemoteException {
            Message.obtain(RecognitionService.this.mHandler, 4, this.mListener).sendToTarget();
            this.mListener.onResults(bundle);
        }

        public void rmsChanged(float f) throws RemoteException {
            this.mListener.onRmsChanged(f);
        }

        public void segmentResults(Bundle bundle) throws RemoteException {
            this.mListener.onSegmentResults(bundle);
        }

        public void endOfSegmentedSession() throws RemoteException {
            Message.obtain(RecognitionService.this.mHandler, 4, this.mListener).sendToTarget();
            this.mListener.onEndOfSegmentedSession();
        }

        public void languageDetection(Bundle bundle) {
            try {
                this.mListener.onLanguageDetection(bundle);
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
                this.mAttributionContext = RecognitionService.this.createContext(new ContextParams.Builder().setNextAttributionSource(this.mCallingAttributionSource).build());
            }
            return this.mAttributionContext;
        }
    }

    public static class SupportCallback {
        private final IRecognitionSupportCallback mCallback;

        private SupportCallback(IRecognitionSupportCallback iRecognitionSupportCallback) {
            this.mCallback = iRecognitionSupportCallback;
        }

        public void onSupportResult(RecognitionSupport recognitionSupport) {
            try {
                this.mCallback.onSupportResult(recognitionSupport);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void onError(int i) {
            try {
                this.mCallback.onError(i);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    private static final class RecognitionServiceBinder extends IRecognitionService.Stub {
        private final WeakReference<RecognitionService> mServiceRef;

        public RecognitionServiceBinder(RecognitionService recognitionService) {
            this.mServiceRef = new WeakReference<>(recognitionService);
        }

        @Override // android.speech.IRecognitionService
        public void startListening(Intent intent, IRecognitionListener iRecognitionListener, AttributionSource attributionSource) {
            Objects.requireNonNull(attributionSource);
            attributionSource.enforceCallingUid();
            RecognitionService recognitionService = this.mServiceRef.get();
            if (recognitionService != null) {
                recognitionService.mHandler.sendMessage(Message.obtain(recognitionService.mHandler, 1, new StartListeningArgs(intent, iRecognitionListener, attributionSource)));
            }
        }

        @Override // android.speech.IRecognitionService
        public void stopListening(IRecognitionListener iRecognitionListener) {
            RecognitionService recognitionService = this.mServiceRef.get();
            if (recognitionService != null) {
                recognitionService.mHandler.sendMessage(Message.obtain(recognitionService.mHandler, 2, iRecognitionListener));
            }
        }

        @Override // android.speech.IRecognitionService
        public void cancel(IRecognitionListener iRecognitionListener, boolean z) {
            RecognitionService recognitionService = this.mServiceRef.get();
            if (recognitionService != null) {
                recognitionService.mHandler.sendMessage(Message.obtain(recognitionService.mHandler, 3, iRecognitionListener));
            }
        }

        @Override // android.speech.IRecognitionService
        public void checkRecognitionSupport(Intent intent, AttributionSource attributionSource, IRecognitionSupportCallback iRecognitionSupportCallback) {
            RecognitionService recognitionService = this.mServiceRef.get();
            if (recognitionService != null) {
                recognitionService.mHandler.sendMessage(Message.obtain(recognitionService.mHandler, 5, new CheckRecognitionSupportArgs(intent, iRecognitionSupportCallback, attributionSource)));
            }
        }

        @Override // android.speech.IRecognitionService
        public void triggerModelDownload(Intent intent, AttributionSource attributionSource, IModelDownloadListener iModelDownloadListener) {
            RecognitionService recognitionService = this.mServiceRef.get();
            if (recognitionService != null) {
                recognitionService.mHandler.sendMessage(Message.obtain(recognitionService.mHandler, 6, new ModelDownloadArgs(intent, attributionSource, iModelDownloadListener)));
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
        if (PermissionChecker.checkPermissionAndStartDataDelivery(this, Manifest.permission.RECORD_AUDIO, sessionState.mCallback.getAttributionContextForCaller().getAttributionSource(), null) == 0) {
            sessionState.mStartedDataDelivery = true;
        }
        return sessionState.mStartedDataDelivery;
    }

    private boolean checkPermissionForPreflightNotHardDenied(AttributionSource attributionSource) {
        int checkPermissionForPreflight = PermissionChecker.checkPermissionForPreflight(this, Manifest.permission.RECORD_AUDIO, attributionSource);
        return checkPermissionForPreflight == 0 || checkPermissionForPreflight == 1;
    }

    void finishDataDelivery(SessionState sessionState) {
        if (sessionState.mStartedDataDelivery) {
            sessionState.mStartedDataDelivery = false;
            PermissionChecker.finishDataDelivery(this, AppOpsManager.permissionToOp(Manifest.permission.RECORD_AUDIO), sessionState.mCallback.getAttributionContextForCaller().getAttributionSource());
        }
    }

    private static class SessionState {
        private Callback mCallback;
        private boolean mStartedDataDelivery;

        SessionState(Callback callback, boolean z) {
            this.mCallback = callback;
            this.mStartedDataDelivery = z;
        }

        SessionState(Callback callback) {
            this(callback, false);
        }

        void reset() {
            this.mCallback = null;
            this.mStartedDataDelivery = false;
        }
    }
}
