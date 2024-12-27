package android.speech;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;

/* loaded from: classes3.dex */
class SpeechRecognizerImpl extends SpeechRecognizer {
    private static final boolean DBG = false;
    private static final int MSG_CANCEL = 3;
    private static final int MSG_CHANGE_LISTENER = 4;
    private static final int MSG_CHECK_RECOGNITION_SUPPORT = 6;
    private static final int MSG_DESTROY = 8;
    private static final int MSG_SET_TEMPORARY_ON_DEVICE_COMPONENT = 5;
    private static final int MSG_START = 1;
    private static final int MSG_STOP = 2;
    private static final int MSG_TRIGGER_MODEL_DOWNLOAD = 7;
    private static final String TAG = "SpeechRecognizer";
    private final IBinder mClientToken;
    private final Context mContext;
    private final Handler mHandler;
    private final InternalRecognitionListener mListener;
    private IRecognitionServiceManager mManagerService;
    private final boolean mOnDevice;
    private final Queue<Message> mPendingTasks;
    private IRecognitionService mService;
    private final ComponentName mServiceComponent;

    SpeechRecognizerImpl(Context context, ComponentName serviceComponent) {
        this(context, serviceComponent, false);
    }

    SpeechRecognizerImpl(Context context, boolean onDevice) {
        this(context, null, onDevice);
    }

    private SpeechRecognizerImpl(
            Context context, ComponentName serviceComponent, boolean onDevice) {
        this.mHandler =
                new Handler(
                        Looper
                                .getMainLooper()) { // from class:
                                                    // android.speech.SpeechRecognizerImpl.1
                    @Override // android.os.Handler
                    public void handleMessage(Message msg) {
                        switch (msg.what) {
                            case 1:
                                SpeechRecognizerImpl.this.handleStartListening((Intent) msg.obj);
                                break;
                            case 2:
                                SpeechRecognizerImpl.this.handleStopMessage();
                                break;
                            case 3:
                                SpeechRecognizerImpl.this.handleCancelMessage();
                                break;
                            case 4:
                                SpeechRecognizerImpl.this.handleChangeListener(
                                        (RecognitionListener) msg.obj);
                                break;
                            case 5:
                                SpeechRecognizerImpl.this.handleSetTemporaryComponent(
                                        (ComponentName) msg.obj);
                                break;
                            case 6:
                                CheckRecognitionSupportArgs args =
                                        (CheckRecognitionSupportArgs) msg.obj;
                                SpeechRecognizerImpl.this.handleCheckRecognitionSupport(
                                        args.mIntent, args.mCallbackExecutor, args.mCallback);
                                break;
                            case 7:
                                ModelDownloadListenerArgs modelDownloadListenerArgs =
                                        (ModelDownloadListenerArgs) msg.obj;
                                SpeechRecognizerImpl.this.handleTriggerModelDownload(
                                        modelDownloadListenerArgs.mIntent,
                                        modelDownloadListenerArgs.mExecutor,
                                        modelDownloadListenerArgs.mModelDownloadListener);
                                break;
                            case 8:
                                SpeechRecognizerImpl.this.handleDestroy();
                                break;
                        }
                    }
                };
        this.mPendingTasks = new LinkedBlockingQueue();
        this.mListener = new InternalRecognitionListener();
        this.mClientToken = new Binder();
        this.mContext = context;
        this.mServiceComponent = serviceComponent;
        this.mOnDevice = onDevice;
    }

    static SpeechRecognizerImpl lenientlyCreateOnDeviceSpeechRecognizer(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Context cannot be null");
        }
        checkIsCalledFromMainThread();
        return new SpeechRecognizerImpl(context, true);
    }

    @Override // android.speech.SpeechRecognizer
    public void setRecognitionListener(RecognitionListener listener) {
        checkIsCalledFromMainThread();
        if (this.mListener.mInternalListener == null) {
            handleChangeListener(listener);
        } else {
            putMessage(Message.obtain(this.mHandler, 4, listener));
        }
    }

    @Override // android.speech.SpeechRecognizer
    public void startListening(Intent recognizerIntent) {
        if (recognizerIntent == null) {
            throw new IllegalArgumentException("intent must not be null");
        }
        checkIsCalledFromMainThread();
        if (this.mService == null) {
            connectToSystemService();
        }
        putMessage(Message.obtain(this.mHandler, 1, recognizerIntent));
    }

    @Override // android.speech.SpeechRecognizer
    public void stopListening() {
        checkIsCalledFromMainThread();
        putMessage(Message.obtain(this.mHandler, 2));
    }

    @Override // android.speech.SpeechRecognizer
    public void cancel() {
        checkIsCalledFromMainThread();
        putMessage(Message.obtain(this.mHandler, 3));
    }

    @Override // android.speech.SpeechRecognizer
    public void checkRecognitionSupport(
            Intent recognizerIntent,
            Executor executor,
            RecognitionSupportCallback supportListener) {
        Objects.requireNonNull(recognizerIntent, "intent must not be null");
        Objects.requireNonNull(supportListener, "listener must not be null");
        if (this.mService == null) {
            connectToSystemService();
        }
        putMessage(
                Message.obtain(
                        this.mHandler,
                        6,
                        new CheckRecognitionSupportArgs(
                                recognizerIntent, executor, supportListener)));
    }

    @Override // android.speech.SpeechRecognizer
    public void triggerModelDownload(Intent intent) {
        Objects.requireNonNull(intent, "intent must not be null");
        if (this.mService == null) {
            connectToSystemService();
        }
        putMessage(
                Message.obtain(
                        this.mHandler,
                        7,
                        new ModelDownloadListenerArgs(intent, null, 0 == true ? 1 : 0)));
    }

    @Override // android.speech.SpeechRecognizer
    public void triggerModelDownload(
            Intent recognizerIntent, Executor executor, ModelDownloadListener listener) {
        Objects.requireNonNull(recognizerIntent, "intent must not be null");
        if (this.mService == null) {
            connectToSystemService();
        }
        putMessage(
                Message.obtain(
                        this.mHandler,
                        7,
                        new ModelDownloadListenerArgs(recognizerIntent, executor, listener)));
    }

    @Override // android.speech.SpeechRecognizer
    public void setTemporaryOnDeviceRecognizer(ComponentName componentName) {
        this.mHandler.sendMessage(Message.obtain(this.mHandler, 5, componentName));
    }

    static void checkIsCalledFromMainThread() {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new RuntimeException(
                    "SpeechRecognizer should be used only from the application's main thread");
        }
    }

    private void putMessage(Message msg) {
        if (this.mService == null) {
            this.mPendingTasks.offer(msg);
        } else {
            this.mHandler.sendMessage(msg);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleStartListening(Intent recognizerIntent) {
        if (!checkOpenConnection()) {
            return;
        }
        try {
            this.mService.startListening(
                    recognizerIntent, this.mListener, this.mContext.getAttributionSource());
        } catch (Exception e) {
            Log.e(TAG, "startListening() failed", e);
            this.mListener.onError(5);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleStopMessage() {
        if (!checkOpenConnection()) {
            return;
        }
        try {
            this.mService.stopListening(this.mListener);
        } catch (Exception e) {
            Log.e(TAG, "stopListening() failed", e);
            this.mListener.onError(5);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleCancelMessage() {
        if (!checkOpenConnection()) {
            return;
        }
        try {
            this.mService.cancel(this.mListener, false);
        } catch (Exception e) {
            Log.e(TAG, "cancel() failed", e);
            this.mListener.onError(5);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleSetTemporaryComponent(ComponentName componentName) {
        if (!maybeInitializeManagerService()) {
            return;
        }
        try {
            this.mManagerService.setTemporaryComponent(componentName);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleCheckRecognitionSupport(
            Intent recognizerIntent,
            Executor callbackExecutor,
            final RecognitionSupportCallback recognitionSupportCallback) {
        if (!maybeInitializeManagerService() || !checkOpenConnection()) {
            return;
        }
        try {
            this.mService.checkRecognitionSupport(
                    recognizerIntent,
                    this.mContext.getAttributionSource(),
                    new InternalSupportCallback(callbackExecutor, recognitionSupportCallback));
        } catch (Exception e) {
            Log.e(TAG, "checkRecognitionSupport() failed", e);
            callbackExecutor.execute(
                    new Runnable() { // from class:
                                     // android.speech.SpeechRecognizerImpl$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            RecognitionSupportCallback.this.onError(5);
                        }
                    });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleTriggerModelDownload(
            Intent recognizerIntent,
            Executor callbackExecutor,
            final ModelDownloadListener modelDownloadListener) {
        if (!maybeInitializeManagerService() || !checkOpenConnection()) {
            return;
        }
        if (modelDownloadListener == null) {
            try {
                this.mService.triggerModelDownload(
                        recognizerIntent, this.mContext.getAttributionSource(), null);
                return;
            } catch (Exception e) {
                Log.e(TAG, "triggerModelDownload() without a listener failed", e);
                this.mListener.onError(5);
                return;
            }
        }
        try {
            this.mService.triggerModelDownload(
                    recognizerIntent,
                    this.mContext.getAttributionSource(),
                    new InternalModelDownloadListener(callbackExecutor, modelDownloadListener));
        } catch (Exception e2) {
            Log.e(TAG, "triggerModelDownload() with a listener failed", e2);
            callbackExecutor.execute(
                    new Runnable() { // from class:
                                     // android.speech.SpeechRecognizerImpl$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            ModelDownloadListener.this.onError(5);
                        }
                    });
        }
    }

    private boolean checkOpenConnection() {
        if (this.mService != null && this.mService.asBinder().isBinderAlive()) {
            return true;
        }
        this.mListener.onError(5);
        Log.e(TAG, "not connected to the recognition service");
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleChangeListener(RecognitionListener listener) {
        this.mListener.mInternalListener = listener;
    }

    @Override // android.speech.SpeechRecognizer
    public void destroy() {
        putMessage(this.mHandler.obtainMessage(8));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDestroy() {
        if (this.mService != null) {
            try {
                this.mService.cancel(this.mListener, true);
            } catch (Exception e) {
            }
        }
        this.mService = null;
        this.mPendingTasks.clear();
        this.mListener.mInternalListener = null;
    }

    private void connectToSystemService() {
        if (!maybeInitializeManagerService()) {
            return;
        }
        ComponentName componentName = getSpeechRecognizerComponentName();
        if (!this.mOnDevice && componentName == null) {
            this.mListener.onError(5);
            return;
        }
        try {
            this.mManagerService.createSession(
                    componentName,
                    this.mClientToken,
                    this.mOnDevice,
                    new IRecognitionServiceManagerCallback
                            .Stub() { // from class: android.speech.SpeechRecognizerImpl.2
                        @Override // android.speech.IRecognitionServiceManagerCallback
                        public void onSuccess(IRecognitionService service) throws RemoteException {
                            SpeechRecognizerImpl.this.mService = service;
                            while (!SpeechRecognizerImpl.this.mPendingTasks.isEmpty()) {
                                SpeechRecognizerImpl.this.mHandler.sendMessage(
                                        (Message) SpeechRecognizerImpl.this.mPendingTasks.poll());
                            }
                        }

                        @Override // android.speech.IRecognitionServiceManagerCallback
                        public void onError(int errorCode) throws RemoteException {
                            Log.e(
                                    SpeechRecognizerImpl.TAG,
                                    "Bind to system recognition service failed with error "
                                            + errorCode);
                            SpeechRecognizerImpl.this.mListener.onError(errorCode);
                        }
                    });
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    private synchronized boolean maybeInitializeManagerService() {
        if (this.mManagerService != null) {
            return true;
        }
        IBinder service = ServiceManager.getService(Context.SPEECH_RECOGNITION_SERVICE);
        if (service == null && this.mOnDevice) {
            service = (IBinder) this.mContext.getSystemService(Context.SPEECH_RECOGNITION_SERVICE);
        }
        this.mManagerService = IRecognitionServiceManager.Stub.asInterface(service);
        if (this.mManagerService != null) {
            return true;
        }
        if (this.mListener != null) {
            this.mListener.onError(5);
        }
        return false;
    }

    private ComponentName getSpeechRecognizerComponentName() {
        if (this.mOnDevice) {
            return null;
        }
        if (this.mServiceComponent != null) {
            return this.mServiceComponent;
        }
        String serviceComponent =
                Settings.Secure.getString(
                        this.mContext.getContentResolver(),
                        Settings.Secure.VOICE_RECOGNITION_SERVICE);
        if (TextUtils.isEmpty(serviceComponent)) {
            Log.e(TAG, "no selected voice recognition service");
            this.mListener.onError(5);
            return null;
        }
        return ComponentName.unflattenFromString(serviceComponent);
    }

    private static class CheckRecognitionSupportArgs {
        final RecognitionSupportCallback mCallback;
        final Executor mCallbackExecutor;
        final Intent mIntent;

        private CheckRecognitionSupportArgs(
                Intent intent, Executor callbackExecutor, RecognitionSupportCallback callback) {
            this.mIntent = intent;
            this.mCallbackExecutor = callbackExecutor;
            this.mCallback = callback;
        }
    }

    private static class ModelDownloadListenerArgs {
        final Executor mExecutor;
        final Intent mIntent;
        final ModelDownloadListener mModelDownloadListener;

        private ModelDownloadListenerArgs(
                Intent intent, Executor executor, ModelDownloadListener modelDownloadListener) {
            this.mIntent = intent;
            this.mExecutor = executor;
            this.mModelDownloadListener = modelDownloadListener;
        }
    }

    private static class InternalRecognitionListener extends IRecognitionListener.Stub {
        private static final int MSG_BEGINNING_OF_SPEECH = 1;
        private static final int MSG_BUFFER_RECEIVED = 2;
        private static final int MSG_END_OF_SPEECH = 3;
        private static final int MSG_ERROR = 4;
        private static final int MSG_LANGUAGE_DETECTION = 12;
        private static final int MSG_ON_EVENT = 9;
        private static final int MSG_PARTIAL_RESULTS = 7;
        private static final int MSG_READY_FOR_SPEECH = 5;
        private static final int MSG_RESULTS = 6;
        private static final int MSG_RMS_CHANGED = 8;
        private static final int MSG_SEGMENT_END_SESSION = 11;
        private static final int MSG_SEGMENT_RESULTS = 10;
        private final Handler mInternalHandler;
        private RecognitionListener mInternalListener;

        private InternalRecognitionListener() {
            this.mInternalHandler =
                    new Handler(
                            Looper
                                    .getMainLooper()) { // from class:
                                                        // android.speech.SpeechRecognizerImpl.InternalRecognitionListener.1
                        @Override // android.os.Handler
                        public void handleMessage(Message msg) {
                            if (InternalRecognitionListener.this.mInternalListener == null) {}
                            switch (msg.what) {
                                case 1:
                                    InternalRecognitionListener.this.mInternalListener
                                            .onBeginningOfSpeech();
                                    break;
                                case 2:
                                    InternalRecognitionListener.this.mInternalListener
                                            .onBufferReceived((byte[]) msg.obj);
                                    break;
                                case 3:
                                    InternalRecognitionListener.this.mInternalListener
                                            .onEndOfSpeech();
                                    break;
                                case 4:
                                    InternalRecognitionListener.this.mInternalListener.onError(
                                            ((Integer) msg.obj).intValue());
                                    break;
                                case 5:
                                    InternalRecognitionListener.this.mInternalListener
                                            .onReadyForSpeech((Bundle) msg.obj);
                                    break;
                                case 6:
                                    InternalRecognitionListener.this.mInternalListener.onResults(
                                            (Bundle) msg.obj);
                                    break;
                                case 7:
                                    InternalRecognitionListener.this.mInternalListener
                                            .onPartialResults((Bundle) msg.obj);
                                    break;
                                case 8:
                                    InternalRecognitionListener.this.mInternalListener.onRmsChanged(
                                            ((Float) msg.obj).floatValue());
                                    break;
                                case 9:
                                    InternalRecognitionListener.this.mInternalListener.onEvent(
                                            msg.arg1, (Bundle) msg.obj);
                                    break;
                                case 10:
                                    InternalRecognitionListener.this.mInternalListener
                                            .onSegmentResults((Bundle) msg.obj);
                                    break;
                                case 11:
                                    InternalRecognitionListener.this.mInternalListener
                                            .onEndOfSegmentedSession();
                                    break;
                                case 12:
                                    InternalRecognitionListener.this.mInternalListener
                                            .onLanguageDetection((Bundle) msg.obj);
                                    break;
                            }
                        }
                    };
        }

        @Override // android.speech.IRecognitionListener
        public void onBeginningOfSpeech() {
            Message.obtain(this.mInternalHandler, 1).sendToTarget();
        }

        @Override // android.speech.IRecognitionListener
        public void onBufferReceived(byte[] buffer) {
            Message.obtain(this.mInternalHandler, 2, buffer).sendToTarget();
        }

        @Override // android.speech.IRecognitionListener
        public void onEndOfSpeech() {
            Message.obtain(this.mInternalHandler, 3).sendToTarget();
        }

        @Override // android.speech.IRecognitionListener
        public void onError(int error) {
            Message.obtain(this.mInternalHandler, 4, Integer.valueOf(error)).sendToTarget();
        }

        @Override // android.speech.IRecognitionListener
        public void onReadyForSpeech(Bundle noiseParams) {
            Message.obtain(this.mInternalHandler, 5, noiseParams).sendToTarget();
        }

        @Override // android.speech.IRecognitionListener
        public void onResults(Bundle results) {
            Message.obtain(this.mInternalHandler, 6, results).sendToTarget();
        }

        @Override // android.speech.IRecognitionListener
        public void onPartialResults(Bundle results) {
            Message.obtain(this.mInternalHandler, 7, results).sendToTarget();
        }

        @Override // android.speech.IRecognitionListener
        public void onRmsChanged(float rmsdB) {
            Message.obtain(this.mInternalHandler, 8, Float.valueOf(rmsdB)).sendToTarget();
        }

        @Override // android.speech.IRecognitionListener
        public void onSegmentResults(Bundle bundle) {
            Message.obtain(this.mInternalHandler, 10, bundle).sendToTarget();
        }

        @Override // android.speech.IRecognitionListener
        public void onEndOfSegmentedSession() {
            Message.obtain(this.mInternalHandler, 11).sendToTarget();
        }

        @Override // android.speech.IRecognitionListener
        public void onLanguageDetection(Bundle results) {
            Message.obtain(this.mInternalHandler, 12, results).sendToTarget();
        }

        @Override // android.speech.IRecognitionListener
        public void onEvent(int eventType, Bundle params) {
            Message.obtain(this.mInternalHandler, 9, eventType, eventType, params).sendToTarget();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class InternalSupportCallback extends IRecognitionSupportCallback.Stub {
        private final RecognitionSupportCallback mCallback;
        private final Executor mExecutor;

        private InternalSupportCallback(Executor executor, RecognitionSupportCallback callback) {
            this.mExecutor = executor;
            this.mCallback = callback;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSupportResult$0(
                RecognitionSupport recognitionSupport) {
            this.mCallback.onSupportResult(recognitionSupport);
        }

        @Override // android.speech.IRecognitionSupportCallback
        public void onSupportResult(final RecognitionSupport recognitionSupport)
                throws RemoteException {
            this.mExecutor.execute(
                    new Runnable() { // from class:
                                     // android.speech.SpeechRecognizerImpl$InternalSupportCallback$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            SpeechRecognizerImpl.InternalSupportCallback.this
                                    .lambda$onSupportResult$0(recognitionSupport);
                        }
                    });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onError$1(int errorCode) {
            this.mCallback.onError(errorCode);
        }

        @Override // android.speech.IRecognitionSupportCallback
        public void onError(final int errorCode) throws RemoteException {
            this.mExecutor.execute(
                    new Runnable() { // from class:
                                     // android.speech.SpeechRecognizerImpl$InternalSupportCallback$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            SpeechRecognizerImpl.InternalSupportCallback.this.lambda$onError$1(
                                    errorCode);
                        }
                    });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class InternalModelDownloadListener extends IModelDownloadListener.Stub {
        private final Executor mExecutor;
        private final ModelDownloadListener mModelDownloadListener;

        private InternalModelDownloadListener(
                Executor executor, ModelDownloadListener modelDownloadListener) {
            this.mExecutor = executor;
            this.mModelDownloadListener = modelDownloadListener;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onProgress$0(int completedPercent) {
            this.mModelDownloadListener.onProgress(completedPercent);
        }

        @Override // android.speech.IModelDownloadListener
        public void onProgress(final int completedPercent) throws RemoteException {
            this.mExecutor.execute(
                    new Runnable() { // from class:
                                     // android.speech.SpeechRecognizerImpl$InternalModelDownloadListener$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            SpeechRecognizerImpl.InternalModelDownloadListener.this
                                    .lambda$onProgress$0(completedPercent);
                        }
                    });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$1() {
            this.mModelDownloadListener.onSuccess();
        }

        @Override // android.speech.IModelDownloadListener
        public void onSuccess() throws RemoteException {
            this.mExecutor.execute(
                    new Runnable() { // from class:
                                     // android.speech.SpeechRecognizerImpl$InternalModelDownloadListener$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            SpeechRecognizerImpl.InternalModelDownloadListener.this
                                    .lambda$onSuccess$1();
                        }
                    });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onScheduled$2() {
            this.mModelDownloadListener.onScheduled();
        }

        @Override // android.speech.IModelDownloadListener
        public void onScheduled() throws RemoteException {
            this.mExecutor.execute(
                    new Runnable() { // from class:
                                     // android.speech.SpeechRecognizerImpl$InternalModelDownloadListener$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            SpeechRecognizerImpl.InternalModelDownloadListener.this
                                    .lambda$onScheduled$2();
                        }
                    });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onError$3(int error) {
            this.mModelDownloadListener.onError(error);
        }

        @Override // android.speech.IModelDownloadListener
        public void onError(final int error) throws RemoteException {
            this.mExecutor.execute(
                    new Runnable() { // from class:
                                     // android.speech.SpeechRecognizerImpl$InternalModelDownloadListener$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            SpeechRecognizerImpl.InternalModelDownloadListener.this
                                    .lambda$onError$3(error);
                        }
                    });
        }
    }
}
